package com.bhargo.user.screens;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.GroupAdapter;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.interfaces.GroupClickListener;
import com.bhargo.user.pojos.firebase.Group;
import com.bhargo.user.pojos.firebase.GroupsListResponse;
import com.bhargo.user.screens.sesssionchat.SessionChatActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.FileUploaderCommunication;
import com.bhargo.user.utils.ImageSelectionActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.utils.ImageCaptureActivity.IMAGE_RESULT_CODE;

public class SearchChatUsers extends BaseActivity implements View.OnClickListener, GroupClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "SearchChatUsers";

    Context context;
    AutoCompleteTextView et_search_users;
    RecyclerView rv_groups;
    List<Group> groupList;
    GroupAdapter userAdapterGroups;
//    DatabaseReference mFirebaseDatabaseReferenceGroups;
    SessionManager sessionManager;

    RelativeLayout rl_groups;
    CustomTextView tv_no_records, tv_view_all_groups;

    int search_flag_people = 0;
    int search_flag_groups = 0;
    String search_text = null;

    ImproveDataBase improveDataBase;
    ImproveHelper improveHelper;

    ImageView ivsessionimage;
    CustomTextView tv_file_name;
    String iconpath = "NA";
    GetServices getServices;
    String userId;
    private boolean isImageClicked;
    BottomSheetBehavior sheetBehavior;
    NestedScrollView bottom_sheet;
    View mViewBg;

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppConstants.THEME.equalsIgnoreCase("THEME2")) {
            setTheme(R.style.AppTheme2);
        }else if (AppConstants.THEME.equalsIgnoreCase("THEME1")) {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_chat_users);

        initViews();

    }

    private void initViews() {

        context = this;

        sessionManager = new SessionManager(context);
        improveDataBase = new ImproveDataBase(context);
        improveHelper = new ImproveHelper(context);
//        mFirebaseDatabaseReferenceGroups = FirebaseDatabase.getInstance().getReference(sessionManager.getOrgIdFromSession()).child("Groups");

        initializeActionBar();
        enableBackNavigation(true);
        ib_settings.setVisibility(View.GONE);
        title.setText(getResources().getString(R.string.new_session));

        iv_circle_appIcon.setVisibility(View.GONE);
        iv_circle_appIcon.setImageDrawable(getDrawable(R.drawable.allapps));

        rl_groups = findViewById(R.id.rl_groups);
        tv_no_records = findViewById(R.id.tv_noRecords);

        et_search_users = findViewById(R.id.cet_usersSearch);

        tv_view_all_groups = findViewById(R.id.tv_view_all_groups);

        tv_view_all_groups.setOnClickListener(this);

        rv_groups = findViewById(R.id.rv_groups);
        rv_groups.setHasFixedSize(true);
        rv_groups.setNestedScrollingEnabled(false);
        rv_groups.setLayoutManager(new LinearLayoutManager(context));

        userId = sessionManager.getUserDataFromSession().getUserID();

        groupList = new ArrayList<>();

        userAdapterGroups = new GroupAdapter(context, groupList);
        rv_groups.setAdapter(userAdapterGroups);
        userAdapterGroups.setCustomClickListener(this);

        et_search_users.setOnItemClickListener(this);
        mViewBg = findViewById(R.id.bs_bg);
        bottom_sheet = findViewById(R.id.nsv_create_session);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                    mViewBg.setVisibility(View.GONE);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                mViewBg.setVisibility(View.VISIBLE); }
        });
        et_search_users.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("")) {
                    userAdapterGroups.updateUserData(groupList);
                }
            }
        });
        getServices = RetrofitUtils.getUserService();

        if (ImproveHelper.isNetworkStatusAvialable(context)) {
            getGroupsList();
        }

    }

    private void getGroupsList() {
        try {
            showProgressDialog(getString(R.string.loading_groups));
            Map<String, String> data = new HashMap<>();

            data.put("UserID", userId);
            data.put("OrgId", sessionManager.getOrgIdFromSession());

            Call<GroupsListResponse> getAllAppNamesDataCall = getServices.iGetAllGroupsList(sessionManager.getAuthorizationTokenId(),data);
            getAllAppNamesDataCall.enqueue(new Callback<GroupsListResponse>() {
                @Override
                public void onResponse(Call<GroupsListResponse> call, Response<GroupsListResponse> response) {
                    dismissProgressDialog();
                    if(!response.raw().message().equalsIgnoreCase("Unauthorized")){
                    if (response.body().getStatus().equalsIgnoreCase("200") && response.body().getMessage().equalsIgnoreCase("Success")) {
                        List<Group> groupList = response.body().getUserGroupsList();
                        if (groupList.size() > 0) {
                            improveDataBase.deleteGroupTable();
                            long result = improveDataBase.insertListIntoGroupTable(groupList, userId,sessionManager.getOrgIdFromSession());
                            if (result > 0) {
                                populateGroups();
                            }
                        } else {
                            rv_groups.setVisibility(View.GONE);
                        }
                    } else {
                        rv_groups.setVisibility(View.GONE);
                    }

                }else{
                    sessionManager.logoutUser();
                }}

                @Override
                public void onFailure(Call<GroupsListResponse> call, Throwable t) {
                    dismissProgressDialog();

                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getGroupsList", e);
        }
    }

    private void populateGroups() {
        try {
            groupList = improveDataBase.getGroupsList(userId, sessionManager.getPostsFromSession() ,sessionManager.getUserTypeIdsFromSession(),sessionManager.getLoginTypeIdFromSession(), "true",sessionManager.getOrgIdFromSession());
            if (groupList.size() > 0) {
                userAdapterGroups.updateUserData(groupList);
                listGroupsForSearch();
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "populateGroups", e);
        }
    }


   /* private void searchGroups() {
        Query query;

        if (search_flag_groups == 1) {
            query = mFirebaseDatabaseReferenceGroups.orderByChild("NameLowerCase").startAt(search_text.toLowerCase()).endAt(search_text.toLowerCase() + "\uf8ff").limitToFirst(2);
        } else {
            query = mFirebaseDatabaseReferenceGroups.orderByChild("NameLowerCase").startAt(search_text.toLowerCase()).endAt(search_text.toLowerCase() + "\uf8ff");
        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userListGroups.clear();
                if (dataSnapshot.exists()) {

                    rl_groups.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        UserDetails user = snapshot.getValue(UserDetails.class);
                        assert user != null;
                        if (user.getIsActive().equalsIgnoreCase("true")) {
                            userListGroups.add(user);
                        }
                    }
                    rv_groups.getRecycledViewPool().clear();
                    userAdapterGroups.updateUserData(userListGroups);

                } else {

                    rl_groups.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/


  /*  private void getFirstFiveGroups() {
        try {
            Query queryRef = mFirebaseDatabaseReferenceGroups.limitToFirst(5);
            queryRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        rl_groups.setVisibility(View.VISIBLE);

                        if (et_search_users.getText().toString().equals("")) {
                            userListGroups.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                UserDetails user = snapshot.getValue(UserDetails.class);
                                assert user != null;
                                if (user.getIsActive().equalsIgnoreCase("true")) {
                                    userListGroups.add(user);
                                }
                            }
                            userAdapterGroups.updateUserData(userListGroups);
                        }
                    } else {
                        rv_groups.setVisibility(View.GONE);

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
        }
    }*/

    private void getGroups() {

        showProgressDialog(getString(R.string.loading_groups));

        try {
            Map<String, String> data = new HashMap<>();
            data.put("UserId", sessionManager.getUserDataFromSession().getUserID());
            data.put("OrgId", sessionManager.getOrgIdFromSession());

            Call<GroupsListResponse> call = getServices.GetGroupsList(data);

            call.enqueue(new Callback<GroupsListResponse>() {
                @Override
                public void onResponse(Call<GroupsListResponse> call, Response<GroupsListResponse> response) {
                    dismissProgressDialog();
                    if (response != null) {
                        try {
                            if (response.body().getStatus().equalsIgnoreCase("200") && response.body().getMessage().equalsIgnoreCase("Success")) {
                                groupList = response.body().getGroupData();
                                userAdapterGroups.updateUserData(groupList);
                                listGroupsForSearch();
                            } else {
                                rv_groups.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GroupsListResponse> call, Throwable t) {
                    dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getGroups", e);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cb_start_chat:


//                    openChatActivity(userLists, "", "");

                break;


        }
    }

    private void openChatActivity(String sessionID, String sessionName, String sessionIcon, String groupName, String groupId) {
        try {
            Intent i = new Intent(context, SessionChatActivity.class);

            i.putExtra("SessionID", sessionID);
            i.putExtra("SessionName", sessionName);
            i.putExtra("SessionIcon", sessionIcon);
            i.putExtra("GroupName", groupName);
            i.putExtra("GroupId", groupId);
            i.putExtra("Write", true);
            i.putExtra("From", true);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(i);
            ((Activity) context).finish();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "openChatActivity", e);
        }
    }

    @Override
    public void onGroupIdClick(Context context, String group_icon, String id, String name) {
//        showSessionAlertDialog(name, id, group_icon);
        createSessionBottomSheet(name, id, group_icon);
    }
    private void createSessionBottomSheet(String groupName, String groupId, String group_icon) {
        try {
            NestedScrollView bottom_sheetPosts = findViewById(R.id.nsv_create_session);
            sheetBehavior = BottomSheetBehavior.from(bottom_sheetPosts);
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            mViewBg.setVisibility(View.VISIBLE);
            CustomEditText etSessionName = bottom_sheetPosts.findViewById(R.id.etSessionName);
            ivsessionimage = bottom_sheetPosts.findViewById(R.id.iv_Image);
            tv_file_name = bottom_sheetPosts.findViewById(R.id.tv_file_name);
            ImageView iv_Cancel = findViewById(R.id.iv_Cancel);
            CustomButton dialogCancel = bottom_sheetPosts.findViewById(R.id.btn_Cancel);
            CustomButton dialogCreate = bottom_sheetPosts.findViewById(R.id.btn_Create);
            iv_Cancel.setOnClickListener(v -> {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            });
            ivsessionimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideKeyboard(context, etSessionName);
                    startActivityForResult(new Intent(context, ImageSelectionActivity.class), IMAGE_RESULT_CODE);
                }
            });
            dialogCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewBg.setVisibility(View.GONE);
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }
            });
            dialogCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String sessionName = etSessionName.getText().toString();
                    if (sessionName.length() > 0) {
                        if (!iconpath.equalsIgnoreCase("NA")) {
                            dialog.dismiss();
                            if (iconpath.contains("http")) {
                                openSessionChat(sessionName, iconpath, groupName, groupId);
                            } else {
                                uploadFile(sessionName, iconpath, groupName, groupId);
                            }
                        } else {
                            toast(getString(R.string.enter_session_image), context);
                        }

                    } else {
                        toast(getString(R.string.enter_session_name), context);
                    }
                    mViewBg.setVisibility(View.GONE);
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "selectPostDialog", e);
        }


    }

    private void showSessionAlertDialog(String groupName, String groupId, String group_icon) {           // this dialog will show bidding amount and know confirmation
        try {
            // custom dialog
            final Dialog dialog = new Dialog(context, R.style.AlertDialogLight);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_session_name);

            CustomEditText etSessionName = dialog.findViewById(R.id.etSessionName);
            ivsessionimage = dialog.findViewById(R.id.ivsessionimage);

            ivsessionimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideKeyboard(context, etSessionName);
                    startActivityForResult(new Intent(context, ImageSelectionActivity.class), IMAGE_RESULT_CODE);
                }
            });
            CustomButton dialogCancel = dialog.findViewById(R.id.cb_cancel);
            // if button is clicked, close the custom dialog
            dialogCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();


                }
            });
            CustomButton dialogCreate = dialog.findViewById(R.id.cb_create);
            dialogCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String sessionName = etSessionName.getText().toString();
                    if (sessionName.length() > 0) {
                        if (!iconpath.equalsIgnoreCase("NA")) {
                            dialog.dismiss();
                            if (iconpath.contains("http")) {
                                openSessionChat(sessionName, iconpath, groupName, groupId);
                            } else {
                                uploadFile(sessionName, iconpath, groupName, groupId);
                            }
                        } else {
                            toast(getString(R.string.enter_session_image), context);
                        }

                    } else {
                        toast(getString(R.string.enter_session_name), context);
                    }

                }
            });

            dialog.show();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "showChannelAlertDialog", e);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {

            switch (requestCode) {

                case IMAGE_RESULT_CODE:

                    if (resultCode == RESULT_OK) {
                        isImageClicked = true;
                        iconpath = data.getStringExtra("image_path");
                        String fileName = iconpath.substring(iconpath.lastIndexOf("/") + 1);
                        if (!iconpath.contains("http")) {
                            Bitmap thumbnail = (BitmapFactory.decodeFile(data.getStringExtra("image_path")));
                            /*ivsessionimage.getLayoutParams().width = 200;
                            ivsessionimage.getLayoutParams().height = 200;

                            //For not to rotate Captured image
                            try {
                                ExifInterface ei = new ExifInterface(data.getStringExtra("image_path"));
                                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                        ExifInterface.ORIENTATION_UNDEFINED);
                                Bitmap rotatedBitmap = null;
                                switch (orientation) {

                                    case ExifInterface.ORIENTATION_ROTATE_90:
                                        rotatedBitmap = rotateImage(thumbnail, 90);
                                        break;

                                    case ExifInterface.ORIENTATION_ROTATE_180:
                                        rotatedBitmap = rotateImage(thumbnail, 180);
                                        break;

                                    case ExifInterface.ORIENTATION_ROTATE_270:
                                        rotatedBitmap = rotateImage(thumbnail, 270);
                                        break;

                                    case ExifInterface.ORIENTATION_NORMAL:
                                    default:
                                        rotatedBitmap = thumbnail;
                                }
                                ivsessionimage.setImageBitmap(rotatedBitmap);*/
                            ivsessionimage.setImageBitmap(thumbnail);
                            /*} catch (Exception e) {
                                Log.d(TAG, "onActivityResult: " + e.toString());
                            }*/
                        } else {
                            Glide.with(context).load(iconpath)
                                    .placeholder(R.drawable.round_rect_shape)
                                    .dontAnimate().into(ivsessionimage)
                                    .onLoadFailed(getDrawable(R.drawable.round_rect_shape));
                        }
                        if (isImageClicked) {
                            tv_file_name.setText(fileName);
                        }

                    } else {

                        Toast.makeText(context, "No image captured", Toast.LENGTH_LONG).show();
                    }
                    break;


            }
        }

    }

    public void openSessionChat(String sessionName, String iconpath, String groupName, String groupId) {

//        openChatActivity(sessionName, name, id, iconpath);
        createSession(sessionName, groupName, groupId, iconpath);


    }

    private void uploadFile(String sessionName, String iconpath, String name, String id) {
        try {
            showProgressDialog(getString(R.string.please_wait));

            String[] imgUrlSplit = iconpath.split("/");
            String strSDCardUrl = imgUrlSplit[imgUrlSplit.length - 1];
            String fileName = strSDCardUrl.replaceAll(" ", "_");
//        String fileName = strSDCardUrl.replaceAll("\\W", "_");
            new FileUploaderCommunication(this, fileName, "", "", false, "BHARGO", new FileUploaderCommunication.OnImageUploaded() {
                @Override
                public void response(String url) {

                    dismissProgressDialog();

                    String iconpathURL = url;
                    openSessionChat(sessionName, iconpathURL, name, id);

                }
            }).execute(iconpath);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "uploadFile", e);
        }
    }


    private void createSession(String sessionName, String groupName, String groupId, String iconpath) {
        showProgressDialog(getString(R.string.creating_session));
        try {
            Map<String, String> data = new HashMap<>();
            data.put("CreatedUserId", sessionManager.getUserDataFromSession().getUserID());
            data.put("OrgId", sessionManager.getOrgIdFromSession());
            data.put("SessionName", sessionName);
            data.put("SessionIcon", iconpath);
            data.put("GroupID", groupId);

            Call<GroupsListResponse> call = getServices.createSession(data);

            call.enqueue(new Callback<GroupsListResponse>() {
                @Override
                public void onResponse(Call<GroupsListResponse> call, Response<GroupsListResponse> response) {
                    dismissProgressDialog();
                    if (response != null) {
                        try {
                            if (response.body().getStatus().equalsIgnoreCase("200") && response.body().getMessage().equalsIgnoreCase("Success")) {
                                String sessionId = response.body().getSessionID();
                                openChatActivity(sessionId, sessionName, iconpath, groupName, groupId);
                            } else {
                                toast(response.body().getMessage(), context);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GroupsListResponse> call, Throwable t) {
                    dismissProgressDialog();
                }
            });

        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "createSession", e);
        }
    }

    private void listGroupsForSearch() {
        try {
            Log.d(TAG, "listGroupsForSearch: " + groupList.size());
            ArrayAdapter<Group> groupdataadapter = new ArrayAdapter<Group>(
                    context, R.layout.support_simple_spinner_dropdown_item,
                    groupList);
            groupdataadapter
                    .setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

            et_search_users.setThreshold(1);
            et_search_users.setAdapter(groupdataadapter);
//        et_search_users.setTextColor(Color.RED);


            et_search_users.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        // on focus off
                        String str = et_search_users.getText().toString();

                        ArrayAdapter<Group> surveydataadapter = new ArrayAdapter<Group>(
                                context,
                                android.R.layout.simple_spinner_item, groupList);
                        for (int i = 0; i < surveydataadapter.getCount(); i++) {
                            String temp = surveydataadapter.getItem(i).toString();
                            if (str.compareTo(temp) == 0) {
                                return;
                            }
                        }

                        et_search_users.setText("");
                    }
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "listGroupsForSearch", e);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String group_name = et_search_users.getText().toString();
        List<Group> temp = new ArrayList();
        for (Group d : groupList) {

            if (d.getGroupName().toLowerCase().equalsIgnoreCase(group_name)) {
                temp.add(d);
            }
        }
        //update recyclerview
        userAdapterGroups.updateUserData(temp);
        hideKeyboard(context, getCurrentFocus());
        et_search_users.clearFocus();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(context, BottomNavigationActivity.class);
                intent.putExtra("FromChatScreen", "FromChatScreen");
                startActivity(intent);


                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(context, BottomNavigationActivity.class);
        intent.putExtra("FromChatScreen", "FromChatScreen");
        startActivity(intent);


    }

}
