package com.bhargo.user.utils;

import static com.bhargo.user.utils.AppConstants.AUTH_TOKEN_ID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.bhargo.user.controls.advanced.LiveTracking;
import com.bhargo.user.pojos.PostsMasterModel;
import com.bhargo.user.pojos.RolesMasterModel;
import com.bhargo.user.pojos.UserData;
import com.bhargo.user.pojos.UserDetails;
import com.bhargo.user.pojos.UserPostDetails;
import com.bhargo.user.pojos.UserTypesMasterModel;
import com.bhargo.user.screens.LoginActivity;
import com.bhargo.user.screens.SplashScreenActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;

public class SessionManager {

    private static final String USER_ROLE="User_Role";
    private static final String USER_ID="User_ID";
    private static final String USER_NAME="User_Name";
    private static final String USER_MOBILE_NUMBER="User_MobileNo";
    private static final String USER_EMAIL="User_Email";
    private static final String USER_DESIGNATION="User_Desigination";
    private static final String USER_LOCATION="User_location";
    private static final String USER_DEPARTMENT="User_Department";
    private static final String USER_LEVEL="User_Level";
    private static final String USER_LOCATION_STRUCTURE="user_Location_structure";
    private static final String USER_ORG_ID="user_org_id";
    private static final String USER_DETAILS="user_details";
    private static final String USER_CHAT_ID="user_chat_id";
    private static final String CHILD_DESIGN_FORMAT="child_design_format";
    private static final String LOGIN_STATUS="login_status";


    private static final String DEVICE_ID="device_id";

    private static final String TOKEN_UPDATED_STATUS="token_id_status";
    private static final String LANGUAGE_CHANGED="LANGUAGE_CHANGED";
    private static final String ACTIVE_FRAGMENT="ACTIVE_FRAGMENT";

    private static final String USER_POST_DETAILS = "user_post_details";
    private static final String USER_POST_ID = "user_post_id";
    private static final String USER_POST_NAME = "user_post_name";
    private static final String USER_TYPE_IDS = "user_type_ids";
    private static final String USER_LOGIN_TYPE = "user_type";
    private static final String LOGIN_TYPE_ID = "login_type_id";
    private static final String CURRENT_APP_NAME = "CURRENT_APP_NAME";
    private static final String USER_REPORTING_POST_ID = "user_reporting_post_id";
    private static final String USER_REPORTING_POST_Department_ID = "user_reporting_post_Department_id";
    private static final String ROLE_MASTER = "role_master";
    private static final String USER_TYPE_MASTER = "user_type_mater";
    private static final String POST_MASTER = "post";

    private static final String CALLAPI_ResponSe_Data = "callapi_response_data";

    private static final String GROUPS = "0";

    UserData userDetails;
    List<UserPostDetails> userPostDetails;

    /*New Version start*/
    List<RolesMasterModel> rolesMasterList;
    List<UserTypesMasterModel> userTypesMasterList;
    List<PostsMasterModel> postsMasterList;

    /*New Version End*/



    public void setUserDetails(UserData userDetails) {
        this.userDetails = userDetails;
    }
    public void setRoleModelsList(List<RolesMasterModel> rolesMasterList) {
        this.rolesMasterList = rolesMasterList;
    }
    public void setUserTypesMasterList(List<UserTypesMasterModel> userTypesMasterList) {
        this.userTypesMasterList = userTypesMasterList;
    }
    public void setPostsMasterList(List<PostsMasterModel> postsMasterList) {
        this.postsMasterList = postsMasterList;
    }


    // Shared Preferences
    SharedPreferences sharedPreferences;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context ctx;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "ImproveUser";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "isloggedin";

    // Constructor
    public SessionManager(Context context) {
        this.ctx = context;
        if(ctx!=null) {
            sharedPreferences = ctx.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = sharedPreferences.edit();
            editor.apply();
        }
    }

    /**
     * LOGIN CHECK
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }


    /**
     * Check login method will check user login status If false it will redirect
     * user to login page
     */
    public void checkLogin() {
        // Check login status

        if (!this.isLoggedIn()) {

            Intent i = new Intent(ctx, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(i);
        }

    }


    /**
     * Clear session details
     */
    public void clearUserDetails() {
        AppConstants.GlobalObjects.setLogin_status("100");
        String orgID = getOrgIdFromSession();
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

//        UserDetails userDetails = new UserDetails();
//        userDetails.setUserId(AppConstants.DefultAPK_UserID);
        UserData userDetails = new UserData();
        userDetails.setUserID(AppConstants.DefultAPK_UserID);
//        createOrgSession(orgID);
//        createLoginSession(userTypesMasterModels);
    /*    // After logout redirect user to Login Activity
        Intent i = new Intent(ctx, LoginActivityOld.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Staring Login Activity
        ctx.startActivity(i);*/

    }
    /**
     * Logout User
     */
    public void logoutUser() {

        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();


        // After logout redirect user to Login Activity
        Intent i = new Intent(ctx, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Staring Login Activity
        ctx.startActivity(i);

    }

    public void logoutUserandExit() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        sharedPreferences = ctx.getSharedPreferences(PREF_NAME, PRIVATE_MODE);



        // After logout redirect user to Login Activity
        Intent i = new Intent(ctx, SplashScreenActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Staring Login Activity
        ctx.startActivity(i);

    }


    /**
     * Create RoleMaster session
     */

    public void createRoleMasterSession(List<RolesMasterModel> rolesMasterModels) {

        Gson gson = new Gson();
        String jsonRolesMasterDetails = gson.toJson(rolesMasterModels);
        editor.putString(ROLE_MASTER, jsonRolesMasterDetails);
        setRoleModelsList(rolesMasterModels);
        // commit changes
        editor.commit();
    }

    /**
     * Create UserType session
     */

    public void createUserTypeMasterSession(List<UserTypesMasterModel> userTypesMasterModels) {
        Gson gson = new Gson();
        String jsonUserTypesMasterDetails = gson.toJson(userTypesMasterModels);
        editor.putString(USER_TYPE_MASTER, jsonUserTypesMasterDetails);
        setUserTypesMasterList(userTypesMasterModels);
        // commit changes
        editor.commit();
    }

    /**
     * Create PostMaster session
     */
    public void createPostMasterSession(List<PostsMasterModel> postsMasterModels) {
        Gson gson = new Gson();
        String jsonPostsMasterDetails = gson.toJson(postsMasterModels);
        editor.putString(USER_POST_DETAILS, jsonPostsMasterDetails);
        setPostsMasterList(postsMasterModels);
        // commit changes
        editor.commit();
    }  /**
     * Create PostMaster session
     */


//    public void createLoginSession(List<UserTypesMasterModel> userTypesMasterModels) {
//        createUserTypeMasterSession(userTypesMasterModels);
//    }

    public void createLoginSession(UserData userDetails) {

        editor.putBoolean(IS_LOGIN, true);
        Gson gson = new Gson();
        String jsonUserDetails = gson.toJson(userDetails);
        editor.putString(USER_DETAILS, jsonUserDetails);

        setUserDetails(userDetails);

        // commit changes
        editor.commit();
    }



    /*Create Org Session*/

    public void createOrgSession(String orgId) {

        editor.putString(USER_ORG_ID, orgId);
        // commit changes
        editor.commit();
    }
 /*Create Authorization TokenId*/

    public void createAuthorizationTokenId(String orgId) {

        editor.putString(AUTH_TOKEN_ID, orgId);
        // commit changes
        editor.commit();
    }

    /*Create login status*/

    public void createloginstatusSession(String status) {

        editor.putString(LOGIN_STATUS, status);
        // commit changes
        editor.commit();
    }

    /*Create login status*/

    public void createloginUserMobileNumberSession(String mobileNo) {

        editor.putString(USER_MOBILE_NUMBER, mobileNo);
        // commit changes
        editor.commit();
    }

    /**
     * Create UserPost Details session
     */
    public void createUserPostDetailsSession(List<UserPostDetails> userPostDetailsList) {

        Gson gson = new Gson();
        String jsonUserDetails = gson.toJson(userPostDetailsList);
        editor.putString(USER_POST_DETAILS, jsonUserDetails);

        setUserPostDetails(userPostDetailsList);

        // commit changes
        editor.commit();
    }


    /**
     * Get Org Id
     */

    public String getOrgIdFromSession() {

        String strORG_ID = sharedPreferences.getString(USER_ORG_ID, "");

        // return user
        return strORG_ID;
    }
/**
     * Get Org Id
     */

    public String getAuthorizationTokenId() {

        String strAuthTokenId = sharedPreferences.getString(AUTH_TOKEN_ID, "");

        // return user
        return strAuthTokenId;
    }

    public String getLoginStatusSession() {

        String strLoginstatus = sharedPreferences.getString(LOGIN_STATUS, "");

        // return loginstatus
        return strLoginstatus;
    }


  /*Create Child design Session*/

    public void createChildFormDesignFormat(String designFormat) {

        editor.putString(CHILD_DESIGN_FORMAT, designFormat);
        // commit changes
        editor.apply();
    }



    /**
     * Get Child
     */

    public String getChildDesignFormat() {

        return sharedPreferences.getString(CHILD_DESIGN_FORMAT, "");
    }



    /*Create Device ID Session*/

    public void createDeviceIdSession(String orgId) {

        editor.putString(DEVICE_ID, orgId);
        // commit changes
        editor.apply();
    }



    /**
     * Get Device ID
     */

    public String getDeviceIdFromSession() {

        String strDevice_ID = sharedPreferences.getString(DEVICE_ID, "");

        // return user
        return strDevice_ID;
    }


    /**
     * Get stored session data
     */

    public UserDetails getUserDetailsFromSession() {

        Gson gson = new Gson();
        UserDetails userDetailsObj=null;
        if(sharedPreferences!=null) {
            String jsonUserDeatils = sharedPreferences.getString(USER_DETAILS, "");
             userDetailsObj = gson.fromJson(jsonUserDeatils, UserDetails.class);
            Log.d("UserDetailsFromSession", gson.toJson(userDetailsObj));
            // return user
        }
        return userDetailsObj;
    }
    /*Create Org Session*/
    public UserData getUserDataFromSession() {

        Gson gson = new Gson();
        UserData userDetailsObj=null;
        if(sharedPreferences!=null) {
            String jsonUserDeatils = sharedPreferences.getString(USER_DETAILS, "");
            userDetailsObj = gson.fromJson(jsonUserDeatils, UserData.class);
            Log.d("UserDetailsFromSession", gson.toJson(userDetailsObj));
            // return user
        }
        return userDetailsObj;
    }

    public void createUserChatID(String chat_id) {

        editor.putString(USER_CHAT_ID, chat_id);
        // commit changes
        editor.commit();
    }



    /**
     * Get Org Id
     */

    public String getUserChatID() {

        String strChatID = sharedPreferences.getString(USER_CHAT_ID, "");

        // return user
        return strChatID;
    }

    /*Create DEVICE_ID UPDATED Status Session*/

    public void createDeviceUpdated(String status) {

        editor.putString(TOKEN_UPDATED_STATUS, status);
        // commit changes
        editor.commit();
    }



    /**
     * Get Device Id Update Status
     */

    public String isDeviceIDUpdated() {

        String strTokenIDStatus = sharedPreferences.getString(TOKEN_UPDATED_STATUS, "no");

        // return user
        return strTokenIDStatus;
    }

    public void languageChanged(boolean bool){
        editor.putBoolean(LANGUAGE_CHANGED, bool);
        // commit changes
        editor.commit();
    }


    public boolean isLanguageChanged() {

        return sharedPreferences.getBoolean(LANGUAGE_CHANGED,false);
    }

    public void activeFragment(String frag){
        editor.putString(ACTIVE_FRAGMENT, frag);
        // commit changes
        editor.commit();
    }


    public String getActiveFragment() {

        return sharedPreferences.getString(ACTIVE_FRAGMENT,"I");
    }

    public void setUserPostDetails(List<UserPostDetails> userPostDetails) {
        this.userPostDetails = userPostDetails;
    }

    /*Create Post Session*/
    public void createPostsSession(String postId) {

        editor.putString(USER_POST_ID, postId);
        // commit changes
        editor.commit();
    }
    public void  setPostName(String postName) {

        editor.putString(USER_POST_NAME, postName);
        // commit changes
        editor.commit();
    }
    public void  setLoginTypeId(String userTypeIds) {

        editor.putString(LOGIN_TYPE_ID, userTypeIds);
        // commit changes
        editor.commit();
    }
    public void  setUserTypeIds(String userTypeIds) {

        editor.putString(USER_LOGIN_TYPE, userTypeIds);
        // commit changes
        editor.commit();
    }
    public void  setUserType(String userType) {

        editor.putString(USER_TYPE_IDS, userType);
        // commit changes
        editor.commit();
    }
    public String getUserTypeIdsFromSession() {

        String strUserTypeIds = sharedPreferences.getString(USER_LOGIN_TYPE, "");

        // return user
        return strUserTypeIds;
    }
    public String getUserTypeFromSession() {

        String strUserTypeIds = sharedPreferences.getString(USER_TYPE_IDS, "");

        // return user
        return strUserTypeIds;
    }
    public String getLoginTypeIdFromSession() {

        String strUserTypeIds = sharedPreferences.getString(LOGIN_TYPE_ID, "");

        // return user
        return strUserTypeIds;
    }

    /**
     * Get Org Id
     */

    public String getPostsFromSession() {

        String strPOSTS_ID = sharedPreferences.getString(USER_POST_ID, null);

        // return user
        return strPOSTS_ID;
    }



    public String getPostsFromSessionPostName() {

        String strPOSTS_ID = sharedPreferences.getString(USER_POST_NAME, "");

        // return com.rss.com
        return strPOSTS_ID;
    }

    public String getPostsSessionReportingPostID() {

        String strPOSTS_ID = sharedPreferences.getString(USER_REPORTING_POST_ID, "");

        // return com.rss.com
        return strPOSTS_ID;
    }

    public String getPostsSessionReportingPostDepartmentID() {

        String strPOSTS_ID = sharedPreferences.getString(USER_REPORTING_POST_Department_ID, "");

        // return com.rss.com
        return strPOSTS_ID;
    }

    public List<UserPostDetails> getUserPostDetailsFromSession() {

//        Gson gson = new Gson();
//        String jsonUserDeatils = sharedPreferences.getString(USER_POST_DETAILS, "");
//        List<UserPostDetails> userDetailsObj = (List<UserPostDetails>) gson.fromJson(jsonUserDeatils, UserPostDetails.class);

        // return user
//        return userDetailsObj;

        Gson gson = new Gson();
        String jsonUserDeatils = sharedPreferences.getString(USER_POST_DETAILS, "");
        Type type = new TypeToken<List<UserPostDetails>>() {}.getType();
        List<UserPostDetails> userDetailsObj = gson.fromJson(jsonUserDeatils, type);
        return userDetailsObj;
    }
    public List<UserTypesMasterModel> getUserTypesFromSession() {
        Gson gson = new Gson();
        String jsonUserTypeDeatils = sharedPreferences.getString(USER_TYPE_MASTER, "");
        Type type = new TypeToken<List<UserTypesMasterModel>>() {}.getType();
        List<UserTypesMasterModel> userTypeDetailsObj = gson.fromJson(jsonUserTypeDeatils, type);
        return userTypeDetailsObj;
    }

    public List<PostsMasterModel> getUserPostDetails() {

        Gson gson = new Gson();
        String jsonUserPostDetails = sharedPreferences.getString(USER_POST_DETAILS, "");
        Type type = new TypeToken<List<PostsMasterModel>>() {}.getType();
        List<PostsMasterModel> userPostDetailsObj = gson.fromJson(jsonUserPostDetails, type);
        return userPostDetailsObj;
    }
    /*Create Group Session*/

    public void createGroupsStatusSession(String status) {

        editor.putString(GROUPS, status);
        // commit changes
        editor.commit();
    }


    public String getGroupsStatus() {

        String groupStatus = sharedPreferences.getString(GROUPS, "0");

        // return com.rss.com
        return groupStatus;
    }

    LinkedHashMap<String, List<String>> OutputData;
    public void createCallAPIResponseData(String APIName,LinkedHashMap<String, List<String>> OutputData) {

        Gson gson = new Gson();
        String jsonUserDetails = gson.toJson(OutputData);
        editor.putString(APIName, jsonUserDetails);

        setOutputData(OutputData);

        // commit changes
        editor.commit();
    }

    public void setOutputData(LinkedHashMap<String, List<String>> outputData) {
        OutputData = outputData;
    }

    public LinkedHashMap<String, List<String>>  getCallAPIResponseData(String APIName) {


        Gson gson = new Gson();
        String jsonUserDeatils = sharedPreferences.getString(APIName, "");
        Type type = new TypeToken<LinkedHashMap<String, List<String>>>() {}.getType();
        LinkedHashMap<String, List<String>> OutputData = gson.fromJson(jsonUserDeatils, type);
        return OutputData;
    }

    public void setAppName(String appName){
        editor.putString(CURRENT_APP_NAME, appName);
        // commit changes
        editor.commit();
    }

    public String getAppName(){
        return sharedPreferences.getString(CURRENT_APP_NAME,"");
    }


    public void SaveLiveTrackingState(String AppName,String LivetrackControlName,Object LiveTrackingObject) {

        Gson gson = new Gson();
        String jsonObjectData = gson.toJson(LiveTrackingObject);
        editor.putString(AppName+"^"+LivetrackControlName, jsonObjectData);

        // commit changes
        boolean status =editor.commit();
        System.out.println("Commit status===="+status );
    }

    public LiveTracking GetLiveTrackObject(String AppName,String LivetrackControlName){
        Gson gson = new Gson();
        String jsonUserDeatils = sharedPreferences.getString(AppName+"^"+LivetrackControlName, "");
        LiveTracking LiveTrackingObj = gson.fromJson(jsonUserDeatils, LiveTracking.class);

        return LiveTrackingObj;
    }
    public String getLoginUserMobileNumber() {

        String strLoginUserMobileNumber = sharedPreferences.getString(USER_MOBILE_NUMBER, "");

        return strLoginUserMobileNumber;
    }

}
