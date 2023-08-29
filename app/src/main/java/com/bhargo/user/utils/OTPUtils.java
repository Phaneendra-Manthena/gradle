package com.bhargo.user.utils;

import static com.bhargo.user.utils.AppConstants.SP_USER_DETAILS;
import static com.bhargo.user.utils.AppConstants.SP_USER_POST_DETAILS;
import static com.bhargo.user.utils.AppConstants.SP_USER_TYPE_MASTER_DETAILS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bhargo.user.Java_Beans.IfElseBlock_Bean;
import com.bhargo.user.MainActivity;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.DeviceIdResponse;
import com.bhargo.user.pojos.OTPModel;
import com.bhargo.user.pojos.PartialDataControlPathsModel;
import com.bhargo.user.pojos.PostsMasterModel;
import com.bhargo.user.pojos.UserData;
import com.bhargo.user.pojos.UserDetailsModel;
import com.bhargo.user.pojos.UserTypesMasterModel;
import com.bhargo.user.pojos.firebase.Group;
import com.bhargo.user.pojos.firebase.GroupsListResponse;
import com.bhargo.user.realm.RealmDBHelper;
import com.bhargo.user.screens.BottomNavigationActivity;
import com.bhargo.user.screens.ProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPUtils {

    private static final String TAG = "OTPUtils";
    Context context;
    GetServices getServices;
    Map<String, String> otpData;
    SessionManager sessionManager;
    String fromActivity;
    IfElseBlock_Bean IfElseBlock_Bean;
    ImproveDataBase improveDataBase;
    private int FromAction = 0;
    private int Executeindex = 0;
    private ImproveHelper improveHelper;

    public OTPUtils(Context context, SessionManager sessionManager, Map<String, String> otpData, String fromActivity, ImproveDataBase improveDataBase, int FromAction, int Executeindex, IfElseBlock_Bean IfElseBlock_Bean) {
        this.context = context;
        this.sessionManager = sessionManager;
        this.otpData = otpData;
        this.fromActivity = fromActivity;
        this.improveDataBase = improveDataBase;
        this.FromAction = FromAction;
        this.Executeindex = Executeindex;
        this.IfElseBlock_Bean = IfElseBlock_Bean;
//        mOtpAPI(otpData);
    }

    /*public  OTPUtils(Context context,SessionManager sessionManager,Map<String, String> otpData,String fromActivity) {
           this.context =  context;
           this.sessionManager =  sessionManager;
           this.otpData = otpData;
           this.fromActivity = fromActivity;
           this.FromAction = FromAction;
           this.Executeindex = Executeindex;
           this.IfElseBlock_Bean = IfElseBlock_Bean;
   //        mOtpAPI(otpData);
       }
   */
    public void mOtpAPI() {
        try {
            getServices = RetrofitUtils.getUserService();
//            showProgressDialog("Verifying OTP");

            Call<OTPModel> otpModelCall = getServices.iOTPVerification(otpData);

            otpModelCall.enqueue(new Callback<OTPModel>() {
                @Override
                public void onResponse(Call<OTPModel> call, Response<OTPModel> response) {

                    if (response.body() != null) {
                        OTPModel otpModel = response.body();
                        System.out.println("OTPOnResponseSuccess: " + otpModel.getMessage());

                        if (otpModel.getMessage().equalsIgnoreCase("Success")) {
//                            improveDataBase.deleteOrganisationData();
                            sessionManager.createAuthorizationTokenId("Bearer " + otpModel.getBearer());
                            mUserDetails();

                        } else {
//                            dismissProgressDialog();
//                            ImproveHelper.insertAccessLog(context,otpModel.getMessage(),"login");
                            ImproveHelper.showToast(context, otpModel.getMessage());
                            ImproveHelper.insertAccessLog(context,otpModel.getMessage(),"Invalid");
                        }
                    } else {
//                        dismissProgressDialog();
                    }

                }

                @Override
                public void onFailure(Call<OTPModel> call, Throwable t) {
                    System.out.println("OTPOnResponseFailure: " + t);
//                    dismissProgressDialog();
                    ImproveHelper.insertAccessLog(context,t.getMessage(),"login");
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "mOtpAPI", e);
//            dismissProgressDialog();
        }

    }

    public void socialLogin(String bearer,ImproveHelper improveHelperL){
        improveHelper = improveHelperL;
        sessionManager.createAuthorizationTokenId("Bearer " + bearer);
        mUserDetails();
    }

    /*User Details API*/
    public void mUserDetails() {
        try {
            if (getServices == null) {
                getServices = RetrofitUtils.getUserService();
            }
            if (sessionManager == null) {
                sessionManager = new SessionManager(context);
            }

            final Call<UserDetailsModel> userDetailsCall = getServices.iUserDetails(sessionManager.getAuthorizationTokenId());

            userDetailsCall.enqueue(new Callback<UserDetailsModel>() {
                @Override
                public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {

                    if (response.body() != null) {

                        UserDetailsModel userDetailsModel = response.body();
                        if (userDetailsModel.getStatus().equalsIgnoreCase("200")) {
                            /* New Version Start*/
                            if (fromActivity.equalsIgnoreCase("MainActivity.java")) {
                                sessionManager.createloginstatusSession("200");
                                AppConstants.GlobalObjects.setLogin_status("200");
                            }
                            UserData userDetails = userDetailsModel.getUserData();
                            sessionManager.createOrgSession(userDetails.getOrgName());
                            List<UserTypesMasterModel> userTypesMasterList = userDetailsModel.getUserUserTypes();
                            List<PostsMasterModel> postsMasterList = userDetailsModel.getUserPosts();
                            if (userDetails != null) {
                                Gson gson = new Gson();
                                if (userDetails != null) {
                                    String jsonUserDetails = gson.toJson(userDetails);
                                    PrefManger.putSharedPreferencesString(context, SP_USER_DETAILS, jsonUserDetails);
                                }
                                if (userTypesMasterList != null) {
                                    String jsonUserTypeMasterDetails = gson.toJson(userTypesMasterList);
                                    PrefManger.putSharedPreferencesString(context, SP_USER_TYPE_MASTER_DETAILS, jsonUserTypeMasterDetails);
                                }
                                System.out.println(TAG+" DataControls");
                                if (postsMasterList != null && postsMasterList.size() > 0) {
                                    String jsonPostMasterDetails = gson.toJson(postsMasterList);
                                    PrefManger.putSharedPreferencesString(context, SP_USER_POST_DETAILS, jsonPostMasterDetails);

                                    for (int i = 0; i < postsMasterList.size(); i++) {
                                        List<PartialDataControlPathsModel> partialDC = postsMasterList.get(i).getPartialDataControlPaths();
                                        if (partialDC != null && partialDC.size() > 0) {
                                            for (int j = 0; j < partialDC.size(); j++) {
                                                Log.d(TAG, "DataControls :onResponsePartial: " + partialDC.get(j).getTextFilePath());
                                                new DownloadFilesFromURL(context, partialDC.get(j).getTextFilePath(), sessionManager, null, TAG, "",partialDC.get(j).getDataControlName(), new DownloadFilesFromURL.DownloadFileListener() {

                                                    @Override
                                                    public void onSuccess(File file, String dataControlName) {
                                                        //nk realm filePath issue
                                                        System.out.println("DataControls filePath:" + file.getAbsolutePath());
                                                        loadTxtFileIntoRealm(file, dataControlName);
                                                    }

                                                    @Override
                                                    public void onFailed(String errorMessage) {
                                                        System.out.println("DataControls errorMessage:" + errorMessage);
                                                    }
                                                });
                                                //DataControls dataControls = new DataControls();
                                                //dataControls.setControlName(partialDC.get(j).getDataControlName());
                                                //dataControls.setTextFilePath(partialDC.get(j).getTextFilePath());
                                                //improveDataBase.insertIntoDataControlTable(dataControls, userDetails.getOrgName(), userDetails.getUserID());
//                                            improveDataBase.insertIntoNewDataControlTable(dataControls.get(i), strOrgId, sessionManager.getUserDataFromSession().getUserID());

                                            }
                                        }
                                    }


                                }

                                createLoginSession(userDetails, userTypesMasterList, postsMasterList);
//                                ImproveHelper.insertAccessLog(context,userDetailsModel.getMessage(),"login");
                            } else {
//                                dismissProgressDialog();
                                ImproveHelper.showToast(context, "No user details found");
                            }


                            /* New Version End*/

                        } else {
                            if (fromActivity.equalsIgnoreCase("MainActivity.java")) {
                                sessionManager.createloginstatusSession("100");
                                AppConstants.GlobalObjects.setLogin_status("100");
                                ImproveHelper.showToast(context, "No user details found");
                                MainActivity mainActivity = new MainActivity();
                                if (FromAction == 1) {
                                    Executeindex++;
                                    mainActivity.EventExecute();
                                } else {
                                    Executeindex++;
                                    mainActivity.checkandExecute(IfElseBlock_Bean);
                                }
                            } else {

                                ImproveHelper.showToast(context, response.body().getMessage());
                            }
                        }
                    } else {
                        ImproveHelper.showToast(context, "No user details found");
                    }

                }

                @Override
                public void onFailure(Call<UserDetailsModel> call, Throwable t) {
//                    dismissProgressDialog();
                    ImproveHelper.showToast(context, t.toString());
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "mUserDetails", e);
//            dismissProgressDialog();
        }
    }

    private void loadTxtFileIntoRealm(File file, String dataControlName) {
        String line = ImproveHelper.readTextFileFromSD(context, file.getAbsolutePath());
        //Check Whether dataControlName Table exist or no in Realm
        //if exist check row wise update or insert
        //if no createTableAndInsertDataFromDataControl
        if(RealmDBHelper.existTable(context,dataControlName)){
            //pending
            //RealmDBHelper.updateOrInsertDataFromDataControl(context, line);
        }else{
            RealmDBHelper.createTableAndInsertDataFromDataControl(context, line);
        }

    }

    /* Create Login Sessions*/
    public void createLoginSession(UserData userDetails, List<UserTypesMasterModel> userTypesMasterModels, List<PostsMasterModel> postsMasterModels) {
        try {
            improveHelper = new ImproveHelper(context);
            improveHelper.createImproveUserFolder("Improve_User");
            sessionManager.createLoginSession(userDetails);
            sessionManager.createUserTypeMasterSession(userTypesMasterModels);
            sessionManager.createPostMasterSession(postsMasterModels);
//            sessionManager.createOrgSession(sessionManager.getUserDataFromSession().getOrgName());
//            PrefManger.putSharedPreferencesString(context, AppConstants.SP_ORGANISATION_ID, AppConstants.DefultAPK_OrgID);
            /*Get Token Id*/
            mGetTokenId();
//            getGroupsList();
        } catch (Exception e) {
//            dismissProgressDialog();
            ImproveHelper.improveException(context, TAG, "createLoginSession", e);
        }

    }

    public void mGetTokenId() {
        try {

            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }  // Get new FCM registration token

                            String newToken = task.getResult();
                            Log.d(TAG, "GetTokenId: " + newToken);

//                            DeviceIdSendData deviceIdSendData = new DeviceIdSendData();
//                            deviceIdSendData.setUserID(sessionManager.getUserDataFromSession().getUserID());
//                            deviceIdSendData.setDeviceID(newToken);
//                            PrefManger.putSharedPreferencesString(context, AppConstants.Login_Device_Id, newToken);
                            sessionManager.createDeviceIdSession(newToken);
                            ImproveHelper.insertAccessLog(context,"Login SuccessFul","login");
                            /*Send device id to server*/
                            if (newToken != null && !newToken.isEmpty()) {
                                Map<String, String> dataMap = new HashMap<>();
                                dataMap.put("UserID", sessionManager.getUserDataFromSession().getUserID());
                                dataMap.put("DeviceID", sessionManager.getDeviceIdFromSession());
                                mDeviceIdSendToServerApi(dataMap);
//                                mDeviceIdSendToServerApi(deviceIdSendData);
                            } /*else {
                                dismissProgressDialog();
                            }*/
                        }
                    });

        } catch (Exception e) {
//            dismissProgressDialog();
            ImproveHelper.improveException(context, TAG, "mGetTokenId", e);
        }

    }

    /*Send device id to server*/
    public void mDeviceIdSendToServerApi(Map<String, String> deviceIdSendData) {
        if (getServices == null) {
            getServices = RetrofitUtils.getUserService();
        }
        try {
            Call<DeviceIdResponse> deviceIdResponseCall = getServices.sendDeviceIdToServer(sessionManager.getAuthorizationTokenId(), deviceIdSendData);

            deviceIdResponseCall.enqueue(new Callback<DeviceIdResponse>() {
                @Override
                public void onResponse(Call<DeviceIdResponse> call, Response<DeviceIdResponse> response) {

                    if (response.body() != null) {
                        Log.d(TAG, "onResponseSuccess: " + response.body().getMessage());
//uncomment
//                        updateTokenInFirebase();
                        if (fromActivity.equalsIgnoreCase("OTPActivity")|| fromActivity.equalsIgnoreCase("SocialLogin")) {
                            Intent intent = new Intent(context, BottomNavigationActivity.class);
                            context.startActivity(intent);
                        } else if (fromActivity.equalsIgnoreCase("ProfileActivity")) {
                            ProfileActivity profileActivity = new ProfileActivity();
                            profileActivity.setProfileData();
//                       nk merge     profileActivity.setProgressBarForRefresh(1);
//                            profileActivity.redirectToAppsList();
                        } else if (fromActivity.equalsIgnoreCase("MainActivity.java")) {
                            MainActivity mainActivity = new MainActivity();
                            mainActivity.updateTokenInFirebase();
                        }
                        Activity activity = (Activity) context;
                        activity.finish();
//                        dismissProgressDialog();
                    }
                    //dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<DeviceIdResponse> call, Throwable t) {
//                    dismissProgressDialog();
                    Log.d(TAG, "onResponseFail: " + t.getStackTrace());

                }
            });
        } catch (Exception e) {
//            dismissProgressDialog();
            ImproveHelper.improveException(context, TAG, "mDeviceIdSendToServerApi", e);
        }

    }

    private void getGroupsList() {
        try {
            Map<String, String> data = new HashMap<>();

            data.put("UserID", sessionManager.getUserDataFromSession().getUserID());
//            data.put("OrgId", sessionManager.getOrgIdFromSession());

            Call<GroupsListResponse> getAllAppNamesDataCall = getServices.iGetAllGroupsList(sessionManager.getAuthorizationTokenId(), data);
            getAllAppNamesDataCall.enqueue(new Callback<GroupsListResponse>() {
                @Override
                public void onResponse(Call<GroupsListResponse> call, Response<GroupsListResponse> response) {

                    if (response.body().getStatus().equalsIgnoreCase("200") && response.body().getMessage().equalsIgnoreCase("Success")) {
                        List<Group> groupList = response.body().getUserGroupsList();
                        if (groupList.size() > 0) {
                            improveDataBase.deleteGroupTable();
                            long result = improveDataBase.insertListIntoGroupTable(groupList, sessionManager.getUserDataFromSession().getUserID(), sessionManager.getUserDataFromSession().getOrgName());
                            if (result > 0) {
                                /*Get Token Id*/
                                mGetTokenId();
                            }
                        } else {
                            /*Get Token Id*/
                            mGetTokenId();
                        }
                    } else {
                        if (fromActivity.equalsIgnoreCase("MainActivity.java")) {
                            MainActivity mainActivity = new MainActivity();
                            if (FromAction == 1) {
                                Executeindex++;
                                mainActivity.EventExecute();
                            } else {
                                Executeindex++;
                                mainActivity.checkandExecute(IfElseBlock_Bean);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<GroupsListResponse> call, Throwable t) {
                    //dismissProgressDialog();
                    /*Get Token Id*/
                    mGetTokenId();
                }
            });
        } catch (Exception e) {

            ImproveHelper.improveException(context, TAG, "getGroupsList", e);
        }

    }


}
