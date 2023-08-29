package com.bhargo.user.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.UsersAdapter;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.firebase.GroupDetails;
import com.bhargo.user.pojos.firebase.Token;
import com.bhargo.user.pojos.firebase.UserDetails;
import com.bhargo.user.pojos.firebase.UserList;
import com.bhargo.user.pojos.firebase.UsersAndGroups;
import com.bhargo.user.pojos.firebase.UsersAndGroupsList;
import com.bhargo.user.screens.groupchat.GroupChatActivity;
import com.bhargo.user.screens.onetoonechat.ChatActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

public class UsersListForChatActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "UsersListForChatActivit";
    Context context;
    LinearLayout rl_AppsListMain;
    ImproveHelper improveHelper;
    UsersAdapter usersAdapter;
    GetServices getServices;
    SessionManager sessionManager;
    DatabaseReference firebaseDatabase;
    CustomButton cb_start_chat;
    Thread t1, t2;
    String newToken = null;
    RecyclerView rv_users;
    CustomTextView tv_nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Theme
        setBhargoTheme(this,AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
        /*if (AppConstants.THEME.equalsIgnoreCase("THEME2")) {
            setTheme(R.style.AppTheme2);
        }else if (AppConstants.THEME.equalsIgnoreCase("THEME1")) {
            setTheme(R.style.AppTheme);
        }*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list_for_chat);


        initViews();
    }

    private void initViews() {

        context = this;
        improveHelper = new ImproveHelper(context);
        getServices = RetrofitUtils.getUserService();
        sessionManager = new SessionManager(context);

        firebaseDatabase = FirebaseDatabase.getInstance("https://bhargocommunication-8c36e.firebaseio.com").getReference("ORG_2");
        firebaseDatabase.keepSynced(true);

        initializeActionBar();
        enableBackNavigation(true);
        ib_settings.setVisibility(View.GONE);
        title.setText(getResources().getString(R.string.startchat));
        iv_circle_appIcon.setVisibility(View.VISIBLE);
        iv_circle_appIcon.setImageDrawable(getResources().getDrawable(R.drawable.allapps));


        CustomEditText cet_usersSearch = findViewById(R.id.cet_usersSearch);
        rl_AppsListMain = findViewById(R.id.rl_AppsListMain);
        rv_users = findViewById(R.id.rv_users);
        tv_nodata = findViewById(R.id.ct_alNoData);
        cb_start_chat = findViewById(R.id.cb_start_chat);
        cb_start_chat.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rv_users.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        List<UserList> newList = new ArrayList<>();
        usersAdapter = new UsersAdapter(context, newList);
        rv_users.setAdapter(usersAdapter);


        cet_usersSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // filter your list from your input
                if (s.toString().length() > 1) {

                    getUsersAndGroupsList(s.toString(), rv_users, tv_nodata);
                }
            }
        });

        if (isNetworkStatusAvialable(context)) {

//            insertUserDataIntoFirebase();
        } else {

            improveHelper.snackBarAlertActivities(context, rl_AppsListMain);
        }
    }

    private void getUsersByGroupID(String type, String name, String id, final RecyclerView rv_users, final CustomTextView tv_nodata) {
        try {
            improveHelper.showProgressDialog(context.getString(R.string.please_wait));

            UsersAndGroups usersAndGroups = new UsersAndGroups();
            usersAndGroups.setOrgId(sessionManager.getOrgIdFromSession());
            usersAndGroups.setGroupId(id);

            Call<UsersAndGroupsList> otpModelCall = getServices.getUsersByGroupID(usersAndGroups);

            otpModelCall.enqueue(new Callback<UsersAndGroupsList>() {
                @Override
                public void onResponse(Call<UsersAndGroupsList> call, Response<UsersAndGroupsList> response) {


                    if (response.body() != null) {
                        improveHelper.dismissProgressDialog();
                        UsersAndGroupsList usersAndGroupsLists = response.body();

                        if (usersAndGroupsLists.getMessage().equalsIgnoreCase("Success")) {
                            tv_nodata.setVisibility(View.GONE);
                            rv_users.setVisibility(View.VISIBLE);

                            List<UserList> newList = new ArrayList<>();
                            newList = usersAndGroupsLists.getResultlist();
                            getSelectedUsers(newList, name, id, type);

                        } else {
                            rv_users.setVisibility(View.GONE);
                            tv_nodata.setVisibility(View.VISIBLE);
                        }


                    } else {
                        improveHelper.dismissProgressDialog();
                    }

                }

                @Override
                public void onFailure(Call<UsersAndGroupsList> call, Throwable t) {
                    System.out.println("ResponseFailure: " + t.toString());
                    improveHelper.dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            improveHelper.improveException(context, TAG, "getUsersByGroupID", e);
        }
    }


    private void getUsersAndGroupsList(String name, final RecyclerView rv_users, final CustomTextView tv_nodata) {
        try {
            improveHelper.showProgressDialog(context.getString(R.string.please_wait));

            UsersAndGroups usersAndGroups = new UsersAndGroups();
            usersAndGroups.setOrgId(sessionManager.getOrgIdFromSession());
            usersAndGroups.setUserId(sessionManager.getUserDataFromSession().getUserID());
            usersAndGroups.setEmpName(name);

            Call<UsersAndGroupsList> otpModelCall = getServices.getUsersAndGroupsListBySearch(usersAndGroups);

            otpModelCall.enqueue(new Callback<UsersAndGroupsList>() {
                @Override
                public void onResponse(Call<UsersAndGroupsList> call, Response<UsersAndGroupsList> response) {


                    if (response.body() != null) {
                        improveHelper.dismissProgressDialog();
                        UsersAndGroupsList usersAndGroupsLists = response.body();

                        if (usersAndGroupsLists.getMessage().equalsIgnoreCase("Success")) {
                            tv_nodata.setVisibility(View.GONE);
                            rv_users.setVisibility(View.VISIBLE);

                            List<UserList> newList = new ArrayList<>();
                            usersAdapter.updateList(newList);
                            usersAdapter.updateList(usersAndGroupsLists.getResultlist());

                        } else {
                            rv_users.setVisibility(View.GONE);
                            tv_nodata.setVisibility(View.VISIBLE);
                        }


                    } else {
                        improveHelper.dismissProgressDialog();
                    }

                }

                @Override
                public void onFailure(Call<UsersAndGroupsList> call, Throwable t) {
                    System.out.println("ResponseFailure: " + t.toString());
                    improveHelper.dismissProgressDialog();
                }
            });

        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            improveHelper.improveException(context, TAG, "getUsersAndGroupsList", e);
        }

    }

    private void insertUserDataIntoFirebase() {

        firebaseDatabase.child("Users").orderByChild("Mobile").equalTo(sessionManager.getUserDetailsFromSession().getPhoneNo()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()) {


                    Log.d(TAG, "onDataChange: " + "ne");
                    String newId = firebaseDatabase.push().getKey();

                    UserDetails userDetails = new UserDetails();

                    userDetails.setDesignation(sessionManager.getUserDetailsFromSession().getDesignation());
                    userDetails.setID(newId);
                    userDetails.setMobile(sessionManager.getUserDetailsFromSession().getPhoneNo());
                    userDetails.setName(sessionManager.getUserDetailsFromSession().getName());
                    userDetails.setRole(sessionManager.getUserDetailsFromSession().getRole());
                    firebaseDatabase.child("Users").child(newId).setValue(userDetails);


                } else {


                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UserDetails userDetails = snapshot.getValue(UserDetails.class);
                        sessionManager.createUserChatID(userDetails.getID());
                        Log.d(TAG, "userchatid: "+userDetails.getID());
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.d(TAG, "onCancelled: " + databaseError.getMessage());

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cb_start_chat:

                if (usersAdapter.getSelected().size() > 0) {

                    List<UserList> userLists = usersAdapter.getSelected();

                    Log.d(TAG, "onClicks: " + userLists.size());
                    Log.d(TAG, "onClickd: " + userLists);
                    if (userLists.get(0).getType().equalsIgnoreCase("I")) {
                        getSelectedUsers(userLists, userLists.get(0).getName(), userLists.get(0).getID(), userLists.get(0).getType());
                    } else {
                        firebaseDatabase.child("Groups").child(userLists.get(0).getID()).child(sessionManager.getUserChatID()).setValue(1);
                        getUsersByGroupID(userLists.get(0).getType(), userLists.get(0).getName(), userLists.get(0).getID(), rv_users, tv_nodata);
                    }
                } else {
                    Toast.makeText(context, "Please Select a Person or a Group", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void getSelectedUsers(List<UserList> userLists, String name, String id, String type) {

        try {
            List<UserDetails> chatUsersList = new ArrayList<>();
            t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            for (int i = 0; i < userLists.size(); i++) {


                                final int pos = i;

                                firebaseDatabase.child("Users").orderByChild("Mobile").equalTo(userLists.get(pos).getPhoneNo()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.exists()) {

                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                                UserDetails userDetails = snapshot.getValue(UserDetails.class);

                                                GroupDetails details = new GroupDetails();
                                                details.setISin(1);
//                                        firebaseDatabase.child("Users").child(newId).child(id).setValue(1);
                                                firebaseDatabase.child("Groups").child(id).child(userDetails.getID()).setValue(1);

                                                updateToken();

                                                chatUsersList.add(userDetails);

                                            }

                                        } else {
                                            Log.d(TAG, "onDataChange: " + "ne");
                                            String newId = firebaseDatabase.push().getKey();

                                            UserDetails userDetails = new UserDetails();

                                            userDetails.setDesignation(userLists.get(pos).getDesignation());
                                            userDetails.setUserid(userLists.get(pos).getID());
                                            userDetails.setID(newId);
                                            userDetails.setMobile(userLists.get(pos).getPhoneNo());
                                            userDetails.setName(userLists.get(pos).getName());
                                            userDetails.setRole(userLists.get(pos).getRole());
                                            firebaseDatabase.child("Users").child(newId).setValue(userDetails);

                                            updateToken();

                                            chatUsersList.add(userDetails);


                                            GroupDetails details = new GroupDetails();
                                            details.setISin(1);

//                                        firebaseDatabase.child("Users").child(newId).child(id).setValue(1);
                                            firebaseDatabase.child("Groups").child(id).child(userDetails.getID()).setValue(1);
                                        }
                                        startChartThread(userLists, chatUsersList);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                        Log.d(TAG, "onCancelled: " + databaseError.getMessage());

                                    }
                                });


                            }


                        }
                    });
                }
            });

            t1.start();
            t1.join();


            t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (chatUsersList.size() > 0) {
                                openChatActivity(chatUsersList, name, id, type);
                            }

                        }
                    });
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getSelectedUsers", e);
        }
    }

    private void openChatActivity(List<UserDetails> chatUsersList, String name, String id, String type) {
        try {
            if (chatUsersList.size() > 1) {
                Intent i = new Intent(context, GroupChatActivity.class);

                i.putExtra("UsersList", (Serializable) chatUsersList);
                i.putExtra("GroupName", name);
                i.putExtra("GroupId", id);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(i);

            } else {
                Intent i = new Intent(context, ChatActivity.class);

                i.putExtra("UsersList", (Serializable) chatUsersList);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(i);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "openChatActivity", e);
        }
    }

    public void startChartThread(List<UserList> userLists, List<UserDetails> chatUsersList) {
        try {
            if (userLists.size() == chatUsersList.size()) {
                if (chatUsersList.size() > 0) {

                    try {
                        t2.start();
                        t2.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "startChartThread", e);
        }
    }


    private void updateToken() {
        try {
            Token token = new Token(sessionManager.getDeviceIdFromSession());
            firebaseDatabase.child("Tokens").child(sessionManager.getUserChatID()).setValue(token).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "response: " + "Success");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Write failed
                    // ...

                    Log.d(TAG, "response: " + e.toString());
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "updateToken", e);
        }
    }

//    public String mGetTokenId() {
//
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this,new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//
//                 newToken = instanceIdResult.getToken();
//
//
//
//
//
//            }
//        });
//
//       newToken = "abc";
//
//        return newToken;
//    }


}
