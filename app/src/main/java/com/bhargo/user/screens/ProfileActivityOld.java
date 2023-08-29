package com.bhargo.user.screens;

import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.custom.SearchableSpinner;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.OTPData;
import com.bhargo.user.pojos.OTPModel;
import com.bhargo.user.pojos.OrgList;
import com.bhargo.user.pojos.PostsMasterModel;
import com.bhargo.user.pojos.UserTypesMasterModel;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.OTPUtils;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import nk.mobileapps.spinnerlib.SpinnerData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivityOld extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "ProfileActivityOld";
    public ImproveHelper improveHelper;
    Context context;
    LinearLayout main_profile_bg;
    CircleImageView iv_circle_Profile;
    CustomTextView tv_ProfileName, tv_ProfileDesignation, tv_ProfileRole, tv_ProfileDepartment, tv_ProfileEmail, tv_ProfilePhoneNo;
    CustomButton btn_logout;
    File file;
    nk.mobileapps.spinnerlib.SearchableSpinner sp_post;
    String firebaseURL = "https://improvecommunication-c08c9.firebaseio.com/";
    private SessionManager sessionManager;
    private View rootView;
    private ViewGroup viewGroup;
    private Animation animSlideUp;
    private SearchableSpinner orgSpinner;
    private ImproveDataBase improveDataBase;
    private String strOrgName, strSelectedPostId;
    private String i_OrgId, strFileName, strFileNameURL;
    private Bundle bundle;
    private GetServices getServices;
    private List<OrgList> orgListsAppsList;
    private ArrayAdapter adapterAllTypes;
    private ArrayList<SpinnerData> spinnerDataArrayListA;
    private List<OrgList> organisationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_check);

        initViews();

    }

    private void initViews() {

        context = ProfileActivityOld.this;
        initializeActionBar();
        title.setText(getString(R.string.profile));
        enableBackNavigation(true);
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);

        sessionManager = new SessionManager(context);
        getServices = RetrofitUtils.getUserService();

        main_profile_bg = findViewById(R.id.main_profile_bg);
        iv_circle_Profile = findViewById(R.id.iv_circle_Profile);
        tv_ProfileName = findViewById(R.id.tv_ProfileName);
        tv_ProfileDesignation = findViewById(R.id.tv_ProfileDesignation);
        tv_ProfileRole = findViewById(R.id.tv_ProfileRole);
        tv_ProfileDepartment = findViewById(R.id.tv_ProfileDepartment);
        tv_ProfileEmail = findViewById(R.id.tv_ProfileEmail);
        tv_ProfilePhoneNo = findViewById(R.id.tv_ProfilePhoneNo);

        btn_logout = findViewById(R.id.btn_logout);
        orgSpinner = findViewById(R.id.sp_ORG);
        sp_post = findViewById(R.id.sp_post);
        sp_post.setOnItemSelectedListener(this);

        animSlideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
//        iv_login_icon.setAnimation(animSlideUp);

        main_profile_bg.setAnimation(animSlideUp);


      /*  if (isNetworkStatusAvialable(context)) {
            getOrganisationsList();
        } else {
            loadOrganisationsFromLocalDB();
        }*/

        loadOrganisationsFromLocalDB();
//        orgSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                strOrgName = orgSpinner.getSelectedItem().toString();
//                i_OrgId = orgListsAppsList.get(i).getOrgID();
//                if (!sessionManager.getOrgIdFromSession().equalsIgnoreCase(i_OrgId)) {
//                    PrefManger.clearSharedPreferences(context);
//                    AppConstants.OrgChange = 1;
////                    mUserDetails(i_OrgId, viewGroup);
//                    OTPUtils otpUtils = new OTPUtils(ProfileActivityOld.this,sessionManager,null,TAG);
//                    otpUtils.mUserDetails();
//                } else {
//                    AppConstants.OrgChange = 0;
//                }
//                Log.d(TAG, "onItemSelectedORGList: " + i_OrgId);
//                if (i_OrgId != null && !i_OrgId.isEmpty()) {
//                    sessionManager.createOrgSession(i_OrgId);
//                    improveHelper.createImproveUserFolder("Improve_User/" + i_OrgId);
//
//                }
//
//                PrefManger.putSharedPreferencesString(context, AppConstants.SP_ORGANISATION_ID, i_OrgId);
//
//                sessionManager.createOrgSession(i_OrgId);
//
//                changeChatID();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//
//        });

        sp_post.setItems(loadPosts(0));
        /* new nk.mobileapps.spinnerlib.SearchableSpinner.SpinnerListener() {
            @Override
            public void onItemsSelected(View v, List<SpinnerData> items, int position) {
                if (sp_post.getSelectedId() != null) {
//                    strSelectedPost = sp_post.getSelectedName();
                    strSelectedPostId = sp_post.getSelectedId();
//                    Log.d(TAG, "onItemsSelectedPost: " + strSelectedPost);
//                    if (isNetworkStatusAvialable(context)) {
                    if (!sessionManager.getPostsFromSession().equalsIgnoreCase(strSelectedPostId)) {
                        AppConstants.PostChange = 1;
                    } else {
                        AppConstants.PostChange = 0;
                    }
                    sessionManager.createPostsSession(strSelectedPostId);
                    Log.d(TAG, "onSelectedPostId: " + strSelectedPostId);
//                        mPostsDetailsToServer(sessionManager.getOrgIdFromSession(), sessionManager.getPostsFromSession(), viewGroup);
//                    }

                }
            }
        });*/


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.app.AlertDialog alertDialog = new AlertDialog.Builder(context)
//set icon
//set title
                        .setTitle(R.string.want_to_logout)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                                if (AppConstants.DefultAPK) {
                                    AppConstants.DefultAPK_afterLoginPage_loaded = true;
                                    PrefManger.clearSharedPreferences(context);
                                    improveDataBase.deleteDatabaseTables(sessionManager.getUserDataFromSession().getUserID());
                                    sessionManager.logoutUser();
                                } else {
                                    AppConstants.GlobalObjects = null;
                                    PrefManger.clearSharedPreferences(context);
                                    improveDataBase.deleteDatabaseTables(sessionManager.getUserDataFromSession().getUserID());
                                    sessionManager.logoutUserandExit();
                                }
                            }
                        })
//set negative button
                        .setNegativeButton(R.string.d_no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked

                            }
                        })
                        .show();
            }
        });
        setProfileValues(sessionManager);
    }

    //    private void loadOrganisationsFromLocalDB() {
//        orgListsAppsList = improveDataBase.getDataFromOrganisationTable(sessionManager.getUserDetailsFromSession().getPhoneNo());
//        adapterAllTypes = new ArrayAdapter(context, android.R.layout.simple_spinner_item, orgListsAppsList);
//        orgSpinner.setAdapter(adapterAllTypes);
//        setSelectedOrganisation(orgListsAppsList);
//    }
    private void loadOrganisationsFromLocalDB() {
        orgListsAppsList = new ArrayList<>();
        OrgList orgList = new OrgList();
        orgList.setOrgName(sessionManager.getUserDataFromSession().getOrgName());
        orgList.setOrgID(sessionManager.getUserDataFromSession().getOrgName());
        orgListsAppsList.add(orgList);
        adapterAllTypes = new ArrayAdapter(context, android.R.layout.simple_spinner_item, orgListsAppsList);
        orgSpinner.setAdapter(adapterAllTypes);
        setSelectedOrganisation(orgListsAppsList);


    }

    private void changeChatID() {
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance(firebaseURL).getReference(sessionManager.getOrgIdFromSession());
        firebaseDatabase.child("Users").orderByChild("Mobile").equalTo(sessionManager.getUserDetailsFromSession().getPhoneNo()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        com.bhargo.user.pojos.firebase.UserDetails userDetails = snapshot.getValue(com.bhargo.user.pojos.firebase.UserDetails.class);
                        sessionManager.createUserChatID(userDetails.getID());
                        Log.d(TAG, "userchatid: " + userDetails.getID());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setSelectedOrganisation(List<OrgList> orgListsAppsList) {
        try {
            for (int i = 0; i < orgListsAppsList.size(); i++) {
                if (sessionManager.getUserDataFromSession().getOrgName().equalsIgnoreCase(orgListsAppsList.get(i).getOrgID())) {
                    orgSpinner.setSelection(i);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "setSelectedOrganisation", e);
        }

    }

//    public void setProfileValues(SessionManager sessionManager, ViewGroup rootView) {
    public void setProfileValues(SessionManager sessionManager) {
        try {
            if (!isNetworkStatusAvialable(context)) {
                if (sessionManager.getUserDataFromSession().getImagePath() != null) {
                    String[] str = sessionManager.getUserDataFromSession().getImagePath().split("/");
                    strFileNameURL = str[str.length - 1];
                    String strFilepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + "ProfileImage" + "/" + strFileNameURL;
                    File file = new File(strFilepath);
                    if (file.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(strFilepath);
                        iv_circle_Profile.setImageBitmap(myBitmap);
                    } else {
                        iv_circle_Profile.setImageDrawable(getResources().getDrawable(R.drawable.user));
                    }

//                    improveHelper.snackBarAlertFragment(context, rootView);
                }
            } else {
                if (sessionManager.getUserDataFromSession().getImagePath() != null) {
                    if (!sessionManager.getUserDataFromSession().getImagePath().equalsIgnoreCase("NA")) {
                        Glide.with(context).load(sessionManager.getUserDataFromSession().getImagePath())
                                .placeholder(R.drawable.user)
                                .into(iv_circle_Profile);
                        new DownloadProfileFromURLTask().execute(sessionManager.getUserDataFromSession().getImagePath());

                    } else {
                        iv_circle_Profile.setImageDrawable(getResources().getDrawable(R.drawable.user));
                    }
                } else {
                    iv_circle_Profile.setImageDrawable(getResources().getDrawable(R.drawable.user));
                }
            }
            tv_ProfileName.setText(sessionManager.getUserDataFromSession().getUserName());
//            tv_ProfileDesignation.setText(sessionManager.getUserDataFromSession().getDesignation());
//            tv_ProfileRole.setText(sessionManager.getUserDataFromSession().getRole());
//            tv_ProfileDepartment.setText(sessionManager.getUserDataFromSession().getDepartment());
//            tv_ProfileEmail.setText(sessionManager.getUserDataFromSession().getEmail());
//            tv_ProfilePhoneNo.setText(sessionManager.getUserDataFromSession().getPhoneNo());

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "setProfileValues", e);
        }

    }
/*
    public void setProfileValues(SessionManager sessionManager, ViewGroup rootView) {
        try {
            if (!isNetworkStatusAvialable(context)) {
                if (sessionManager.getUserDetailsFromSession().getProfilePIc() != null) {
                    String[] str = sessionManager.getUserDetailsFromSession().getProfilePIc().split("/");
                    strFileNameURL = str[str.length - 1];
                    String strFilepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + "ProfileImage" + "/" + strFileNameURL;
                    File file = new File(strFilepath);
                    if (file.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(strFilepath);
                        iv_circle_Profile.setImageBitmap(myBitmap);
                    } else {
                        iv_circle_Profile.setImageDrawable(getResources().getDrawable(R.drawable.user));
                    }

                    improveHelper.snackBarAlertFragment(context, rootView);
                }
            } else {
                if (sessionManager.getUserDetailsFromSession().getProfilePIc() != null) {
                    if (!sessionManager.getUserDetailsFromSession().getProfilePIc().equalsIgnoreCase("NA")) {
                        Glide.with(context).load(sessionManager.getUserDetailsFromSession().getProfilePIc())
                                .placeholder(R.drawable.user)
                                .into(iv_circle_Profile);
                        new DownloadProfileFromURLTask().execute(sessionManager.getUserDetailsFromSession().getProfilePIc());

                    } else {
                        iv_circle_Profile.setImageDrawable(getResources().getDrawable(R.drawable.user));
                    }
                } else {
                    iv_circle_Profile.setImageDrawable(getResources().getDrawable(R.drawable.user));
                }
            }
            tv_ProfileName.setText(sessionManager.getUserDetailsFromSession().getName());
            tv_ProfileDesignation.setText(sessionManager.getUserDetailsFromSession().getDesignation());
            tv_ProfileRole.setText(sessionManager.getUserDetailsFromSession().getRole());
            tv_ProfileDepartment.setText(sessionManager.getUserDetailsFromSession().getDepartment());
            tv_ProfileEmail.setText(sessionManager.getUserDetailsFromSession().getEmail());
            tv_ProfilePhoneNo.setText(sessionManager.getUserDetailsFromSession().getPhoneNo());

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "setProfileValues", e);
        }

    }
*/

//    public void mGetTokenId(String i_OrgId) {
//        try {
//            FirebaseMessaging.getInstance().getToken()
//                    .addOnCompleteListener(new OnCompleteListener<String>() {
//                        @Override
//                        public void onComplete(@NonNull Task<String> task) {
//                            if (!task.isSuccessful()) {
//                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                                return;
//                            }  // Get new FCM registration token
//
//                            String newToken = task.getResult();
//                            Log.d(TAG, "GetTokenId: " + newToken);
//
//                            DeviceIdSendData deviceIdSendData = new DeviceIdSendData();
//                            deviceIdSendData.setUserId(sessionManager.getUserDataFromSession().getUserID());
//                            deviceIdSendData.setOrgId(i_OrgId);
//                            deviceIdSendData.setDevcieID(newToken);
//
//
//                            if (newToken != null && !newToken.isEmpty()) {
//                                mDeviceIdSendToServerApi(deviceIdSendData);
//                            } else {
////                    improveHelper.dismissProgressDialog();
//                            }}
//                    });
//        } catch (Exception e) {
//            improveHelper.improveException(this, TAG, "mGetTokenId", e);
//        }
//
//    }

    /*Send device id to server*/
//    public void mDeviceIdSendToServerApi(DeviceIdSendData deviceIdSendData) {
//        try {
//            Map<String, String> dataMap = new HashMap<>();
//            dataMap.put("UserID", sessionManager.getUserDataFromSession().getUserID());
//            dataMap.put("DeviceID", sessionManager.getDeviceIdFromSession());
//            Call<DeviceIdResponse> deviceIdResponseCall = getServices.sendDeviceIdToServer(sessionManager.getAuthorizationTokenId(), dataMap);
//            deviceIdResponseCall.enqueue(new Callback<DeviceIdResponse>() {
//                @Override
//                public void onResponse(Call<DeviceIdResponse> call, Response<DeviceIdResponse> response) {
////                improveHelper.dismissProgressDialog();
//                    if (response.body() != null) {
//                        Log.d(TAG, "onResponseSuccess: " + response.body().getMessage());
//
//                        sessionManager.createDeviceIdSession(deviceIdSendData.getDeviceID());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<DeviceIdResponse> call, Throwable t) {
//
//                    Log.d(TAG, "onResponseFailDeviceId: " + t.getStackTrace());
////                improveHelper.dismissProgressDialog();
//                }
//            });
//        } catch (Exception e) {
//            ImproveHelper.improveException(this, TAG, "mDeviceIdSendToServerApi", e);
//        }
//
//    }

    /*User Details API*/
//    private void mUserDetails(String i_OrgId, ViewGroup viewGroup) {
//        try {
////        improveHelper.showProgressDialog(getString(R.string.please_wait));
//
//            UserDetailsData userDetailsData = new UserDetailsData();
//            userDetailsData.setMobileNo(sessionManager.getUserDetailsFromSession().getPhoneNo());
//            userDetailsData.setOrgId(i_OrgId);
//
////            final Call<UserDetailsModel> userDetailsCall = getServices.iUserDetails(userDetailsData);
//            final Call<UserDetailsModel> userDetailsCall = getServices.iUserDetails(sessionManager.getAuthorizationTokenId());
//
//            userDetailsCall.enqueue(new Callback<UserDetailsModel>() {
//                @Override
//                public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
//
//                    if (response.body() != null) {
//
//                        UserDetailsModel userDetailsModel = response.body();
//                        if (userDetailsModel.getStatus().equalsIgnoreCase("200")) {
//                            UserDetails userDetails = userDetailsModel.getUserDeatils();
//                            List<UserPostDetails> userPostDetails = userDetailsModel.getUserPostDetails();
//                            UserDetailsModel.ReportingUserDeatils reportingUserDetails = userDetailsModel.getReportingUserDeatils();
//                            String strUserId = userDetails.getUserId();
//                            Log.d("OTP_UserId", strUserId);
//                            /*Setting User Details Object into SharedPreferences*/
//                            Gson gson = new Gson();
//                            String jsonUserDeatils = gson.toJson(userDetails);
//                            Log.d(TAG, "ProfileOrgChangedUserdDetails: " + jsonUserDeatils);
//                            PrefManger.putSharedPreferencesString(context, SP_USER_DETAILS, jsonUserDeatils);
//
//                            String jsonReportingUserDetails = gson.toJson(reportingUserDetails);
//                            Log.d(TAG, "ProfileOrgChangedPostDetails: " + jsonReportingUserDetails);
//                            PrefManger.putSharedPreferencesString(context, SP_REPORTING_USER_DETAILS, jsonReportingUserDetails);
//                            sessionManager.createPostsSession(null);
//                            sessionManager.setPostName(null);
//                            createLoginSession(userDetails, userPostDetails);
//
//                            sp_post.setItems(loadPosts(1));
//                            setProfileValues(sessionManager, viewGroup);
//                        } else {
//                            ImproveHelper.showToast(context, "No user details found");
//                        }
//
//                    } else {
//                        ImproveHelper.showToast(context, "No user details found");
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<UserDetailsModel> call, Throwable t) {
////                improveHelper.dismissProgressDialog();
//                    Log.d(TAG, "onFailureUserDetailsData: " + t.toString());
//                }
//            });
//        } catch (Exception e) {
//            improveHelper.improveException(this, TAG, "mUserDetails", e);
//        }
//
//    }
//    private void mUserDetails(String authTokenId) {
//        try {
//
//            final Call<UserDetailsModel> userDetailsCall = getServices.iUserDetails(authTokenId);
//
//            userDetailsCall.enqueue(new Callback<UserDetailsModel>() {
//                @Override
//                public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
//
//                    if (response.body() != null) {
//
//                        UserDetailsModel userDetailsModel = response.body();
//                        if (userDetailsModel.getStatus().equalsIgnoreCase("200")) {
//                            /* New Version Start*/
//                            List<RolesMasterModel> rolesMasterList = userDetailsModel.getRolesMaster();
//                            List<UserTypesMasterModel> userTypesMasterList = userDetailsModel.getUserTypesMaster();
//                            List<PostsMasterModel> postsMasterList = userDetailsModel.getPostsMaster();
//
//                            Gson gson = new Gson();
//                            if (rolesMasterList != null) {
//                                String jsonRoleMasterDetails = gson.toJson(rolesMasterList);
//                                PrefManger.putSharedPreferencesString(context, SP_ROLE_MASTER_DETAILS, jsonRoleMasterDetails);
//                            }
//                            if (userTypesMasterList != null) {
//                                String jsonUserTypeMasterDetails = gson.toJson(userTypesMasterList);
//                                PrefManger.putSharedPreferencesString(context, SP_USER_TYPE_MASTER_DETAILS, jsonUserTypeMasterDetails);
//                            }
//                            if (postsMasterList != null) {
//                                String jsonPostMasterDetails = gson.toJson(postsMasterList);
//                                PrefManger.putSharedPreferencesString(context, SP_POST_MASTER_DETAILS, jsonPostMasterDetails);
//                            }
//                            if (userTypesMasterList != null && userTypesMasterList.size() > 0) {
//                                createUserDetailsSession(rolesMasterList, userTypesMasterList,postsMasterList);
//                            }else{
////                                dismissProgressDialog();
//                                ImproveHelper.showToast(context, "No user details found");
//                            }
//
//                            /* New Version End*/
//
////                            UserDetails userDetails = userDetailsModel.getUserDeatils();
////                            List<UserPostDetails> userPostDetails = userDetailsModel.getUserPostDetails();
////                            UserDetailsModel.ReportingUserDeatils reportingUserDetails = userDetailsModel.getReportingUserDeatils();
//////                    String strUserId = userDetails.getUserId();
//////                    Log.d("OTP_UserId", strUserId);
////                            /*Setting User Details Object into SharedPreferences*/
////                            Gson gson = new Gson();
////                            if (userDetails != null) {
////                                String jsonUserDeatils = gson.toJson(userDetails);
////                                PrefManger.putSharedPreferencesString(context, SP_USER_DETAILS, jsonUserDeatils);
////                            }
////                            if (userPostDetails != null) {
////                                String jsonUserPostDeatils = gson.toJson(userPostDetails);
////                                PrefManger.putSharedPreferencesString(context, SP_USER_POST_DETAILS, jsonUserPostDeatils);
////                            }
////                            if (reportingUserDetails != null) {
////                                String jsonReportingUserDetails = gson.toJson(reportingUserDetails);
////                                PrefManger.putSharedPreferencesString(context, SP_REPORTING_USER_DETAILS, jsonReportingUserDetails);
////                            }
////
////                            if (userDetails != null) {
//////                                createLoginSession(userDetails, userPostDetails, orgList);
////                                createLoginSession(userDetails, userPostDetails,null);
////                            }else{
////                                dismissProgressDialog();
////                                ImproveHelper.showToast(context, "No user details found");
////
////                            }
//                        } else {
//
////                            dismissProgressDialog();
//                            ImproveHelper.showToast(context, "No user details found");
//
//                        }
//
//                    } else {
////                        dismissProgressDialog();
//                        ImproveHelper.showToast(context, "No user details found");
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<UserDetailsModel> call, Throwable t) {
////                    dismissProgressDialog();
//                    ImproveHelper.showToast(context, t.toString());
//                }
//            });
//        } catch (Exception e) {
//            ImproveHelper.improveException(this, TAG, "mUserDetails", e);
////            dismissProgressDialog();
//        }
//    }
//
//    private void createUserDetailsSession(List<RolesMasterModel> rolesMasterModels, List<UserTypesMasterModel> userTypesMasterModels, List<PostsMasterModel> postsMasterModels) {
//        try {
//
//            improveHelper.createImproveUserFolder("Improve_User");
//            sessionManager.createLoginSession(userTypesMasterModels);
//            sessionManager.createRoleMasterSession(rolesMasterModels);
//            sessionManager.createPostMasterSession(postsMasterModels);
////            improveDataBase.insertIntOrganisationsTable(null, sessionManager.getUserDetailsFromSession().getPhoneNo());
//
//            /*Get Token Id*/
//            mGetTokenId("");
////        getGroupsList();
//        } catch (Exception e) {
//            dismissProgressDialog();
//            ImproveHelper.improveException(this, TAG, "createLoginSession", e);
//        }
//
//    }

/*
    private void createLoginSession(UserDetails userDetails, List<UserPostDetails> userPostDetailsList) {
        try {
            sessionManager.createLoginSession(userDetails);
            sessionManager.createUserPostDetailsSession(userPostDetailsList);
            */
    /*Send DeviceId To Server*//*

            mGetTokenId(sessionManager.getOrgIdFromSession());
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "mUserDetails", e);
        }

    }
*/

    public ArrayList<SpinnerData> loadPosts(int flag) {

        spinnerDataArrayListA = new ArrayList<>();

        try {
            List<PostsMasterModel> postsMasterModelList = sessionManager.getUserPostDetails();
            List<UserTypesMasterModel> userTypesMasterlist = sessionManager.getUserTypesFromSession();
            for (int i = 0; i < userTypesMasterlist.size(); i++) {
                if (!userTypesMasterlist.get(i).getID().equalsIgnoreCase("1")) {
                    PostsMasterModel postsMasterModel = new PostsMasterModel();
                    postsMasterModel.setID(userTypesMasterlist.get(i).getID());
                    postsMasterModel.setName(userTypesMasterlist.get(i).getName());
                    postsMasterModel.setUserID(userTypesMasterlist.get(i).getUserID());
                    postsMasterModel.setLoginTypeID(userTypesMasterlist.get(i).getLoginTypeID());
                    postsMasterModel.setUserType("NormalUser");
                    postsMasterModelList.add(postsMasterModel);
                }
            }
            if (sessionManager.getUserPostDetails() != null && sessionManager.getUserPostDetails().size() > 0) {

                SpinnerData spinnerDataDef = new SpinnerData();
                spinnerDataDef.setName("-- Select Post --");
                spinnerDataDef.setId("0");
                spinnerDataDef.setObject(postsMasterModelList);
                spinnerDataDef.setSelected(true);
                spinnerDataArrayListA.add(spinnerDataDef);
                for (int i = 0; i < postsMasterModelList.size(); i++) {
                    SpinnerData spinnerData = new SpinnerData();
                    spinnerData.setName(postsMasterModelList.get(i).getName());
                    Log.d(TAG, "loadPosts1: " + postsMasterModelList.get(i).getName());
                    Log.d(TAG, "loadPosts2: " + spinnerData.getName());
                    spinnerData.setId(postsMasterModelList.get(i).getID());
                    spinnerData.setObject(postsMasterModelList);
                    spinnerDataArrayListA.add(spinnerData);

                    if (flag == 0 && sessionManager.getPostsFromSession().equalsIgnoreCase(spinnerData.getId())) {
                        spinnerDataArrayListA.get(0).setSelected(false);
                        spinnerData.setSelected(true);
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "loadPosts", e);
        }

        return spinnerDataArrayListA;

    }
/*
    public ArrayList<SpinnerData> loadPosts(int flag) {

        spinnerDataArrayListA = new ArrayList<>();
        try {
            if (sessionManager.getUserPostDetailsFromSession() != null && sessionManager.getUserPostDetailsFromSession().size() > 0) {

                SpinnerData spinnerDataDef = new SpinnerData();
                spinnerDataDef.setName("-- Select Post --");
                spinnerDataDef.setId("0");
                spinnerDataDef.setObject(sessionManager.getUserPostDetailsFromSession());
                spinnerDataDef.setSelected(true);
                spinnerDataArrayListA.add(spinnerDataDef);
                for (int i = 0; i < sessionManager.getUserPostDetailsFromSession().size(); i++) {

                    SpinnerData spinnerData = new SpinnerData();
                    spinnerData.setName(sessionManager.getUserPostDetailsFromSession().get(i).getName());
                    Log.d(TAG, "loadPosts1: " + sessionManager.getUserPostDetailsFromSession().get(i).getName());
                    Log.d(TAG, "loadPosts2: " + spinnerData.getName());
                    spinnerData.setId(sessionManager.getUserPostDetailsFromSession().get(i).getPostID());
                    spinnerData.setObject(sessionManager.getUserPostDetailsFromSession());
                    spinnerDataArrayListA.add(spinnerData);

                    if (flag == 0 && sessionManager.getPostsFromSession().equalsIgnoreCase(spinnerData.getId())) {
                        spinnerDataArrayListA.get(0).setSelected(false);
                        spinnerData.setSelected(true);
                    }
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "loadPosts", e);
        }

        return spinnerDataArrayListA;

    }
*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (sp_post.getSelectedId() != null && !sp_post.getSelectedId().equalsIgnoreCase("0")) {
            strSelectedPostId = sp_post.getSelectedId();

            if (!sessionManager.getPostsFromSession().equalsIgnoreCase(strSelectedPostId)) {

                OTPUtils otpUtils = new OTPUtils(ProfileActivityOld.this, sessionManager, null, TAG,improveDataBase,-1,-1,null);
                otpUtils.mUserDetails();
                AppConstants.PostChange = 1;
                AppConstants.OrgChange =  1;
                sessionManager.createPostsSession(strSelectedPostId);
                sessionManager.setPostName(sp_post.getSelectedName());
                List<PostsMasterModel> object = (List<PostsMasterModel>) sp_post.getSelectedItem().getObject();
                sessionManager.setUserType(object.get(position-1).getUserType());
                sessionManager.setUserTypeIds(object.get(position-1).getID());
//                Log.d(TAG, "onItemSelectedObj: "+sp_post.getSelectedName()+"-"+object.get(position-1).getUserType());
                setProgressBarForRefresh(1);
                redirectToAppsList();

            } else {
                setProgressBarForRefresh(0);
                AppConstants.PostChange = 0;
            }
          /*  sessionManager.createPostsSession(strSelectedPostId);
            sessionManager.setPostName(sp_post.getSelectedName());*/
            Log.d(TAG, "onSelectedPostId: " + sp_post.getSelectedName());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getOrganisationsList() {
        try {
            OTPData otpData = new OTPData();
//            otpData.setMobileNo(sessionManager.getUserDataFromSession().getPhoneNo());
            showProgressDialog(getString(R.string.please_wait));

            Call<OTPModel> otpModelCall = getServices.getOrganisationsList(otpData);

            otpModelCall.enqueue(new Callback<OTPModel>() {
                @Override
                public void onResponse(Call<OTPModel> call, Response<OTPModel> response) {
                    dismissProgressDialog();
                    if (response.body() != null) {
                        organisationList = new ArrayList<>();
                        OTPModel otpModel = response.body();
                        if (otpModel.getMessage().equalsIgnoreCase("Success")) {
                            improveDataBase.deleteOrganisationData();
                            if (otpModel.getOrgList() != null && otpModel.getOrgList().size() > 0) {
                                for (OrgList orgList : otpModel.getOrgList()) {
//                                    if (orgList.getStatus().equalsIgnoreCase("Y")) {
                                    organisationList.add(orgList);
//                                    }
                                }
                                long count = improveDataBase.insertIntOrganisationsTable(organisationList, sessionManager.getUserDetailsFromSession().getPhoneNo());
                                if (count != -1) {
                                    loadOrganisationsFromLocalDB();
                                }
                            }

                        } else {

                            ImproveHelper.showToast(context, otpModel.getMessage());
                        }
                    } else {

                    }

                }

                @Override
                public void onFailure(Call<OTPModel> call, Throwable t) {
                    System.out.println("OTPOnResponseFailure: " + t);
                    dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "getOrganisationsList", e);
        }

    }

    public void redirectToAppsList() {
        try {
            BottomNavigationActivity.FROM_TAB = 0;
            Intent intent = new Intent(context, BottomNavigationActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "redirectToAppsList", e);
        }
    }

    public void setProgressBarForRefresh(int value) {
        try {
            AppConstants.PROGRESS_CHART = value;
            AppConstants.PROGRESS_APPS = value;
            AppConstants.PROGRESS_REPORT = value;
            AppConstants.PROGRESS_TASK = value;
            AppConstants.PROGRESS_E_LEARNING = value;
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "setProgressBarForRefresh", e);
        }

    }

    public class DownloadProfileFromURLTask extends AsyncTask<String, String, String> {

        /**
         * Downloading file in background thread
         */


        @SuppressLint("SdCardPath")
        @Override
        protected String doInBackground(String... f_url) {
            Log.i(TAG, "do in background" + f_url[0]);
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                String[] strsplit = f_url[0].split("/");
                strFileName = strsplit[strsplit.length - 1];

                file = new File(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + "ProfileImage");
                if (!file.exists()) {
                    file.mkdirs();
                }
//                file = new File(context.getExternalFilesDir(null),"/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats");
                /*file = new File(context.getExternalFilesDir(null),"/" +AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicTitle);
                if (!file.exists())
                    file.mkdirs();*/
                File outFile = new File(file, strFileName);
                // Output stream to write file
                OutputStream output = new FileOutputStream(outFile);

                byte[] data = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
//            mProgress.setProgress(Integer.parseInt(progress[0]));
//            progressBar = v.findViewById(R.id.circularProgressbar);
//            progressBar.setProgress(Integer.parseInt(progress[0]));
//            tv_progStatus.setText(progress[0]);
//            textProgress2.setText(progress[0]);
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @SuppressLint("SdCardPath")
        @Override
        protected void onPostExecute(String file_url) {
            Log.i(TAG, "on post excute!: " + file_url);

            // dismiss progressbars after the file was downloaded
//            ll_progressbar = v.findViewById(R.id.ll_progressbar);
//            ll_progressbar.setVisibility(View.GONE);

            // Displaying downloaded image into image view
            // Reading image path from sdcard
            Log.d(TAG, "doInBackgroundSet: " + file);

            if (file != null && file.exists()) {

//                ll_progressbar.setVisibility(View.GONE);

//                    ll_progressbar.setVisibility(View.GONE);
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                    if (filesInfoModelList.get(position).getFileNameExt().equalsIgnoreCase("jpg")) {
//                        imageView.setImageBitmap(myBitmap);
//                    }
//                ImproveHelper.showToastAlert(context, strFileName + "\n" + " Download successfully!");
                strFileNameURL = strFileName;
            }


//            notifyDataSetChanged();
        }
    }
}