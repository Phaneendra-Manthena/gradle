package com.bhargo.user.interfaces;

import com.bhargo.user.Java_Beans.GetAPIDetails_Bean;
import com.bhargo.user.pojos.AppDetailsAdvancedAction;
import com.bhargo.user.pojos.AppDetailsAdvancedInput;
import com.bhargo.user.pojos.AppDetailsResponse;
import com.bhargo.user.pojos.AppIconData;
import com.bhargo.user.pojos.AppTypeResponse;
import com.bhargo.user.pojos.AssessmentAnswersData;
import com.bhargo.user.pojos.AssessmentReportResponseMain;
import com.bhargo.user.pojos.CallFormDataResponse;
import com.bhargo.user.pojos.CommentsInfoModel;
import com.bhargo.user.pojos.CommentsResponse;
import com.bhargo.user.pojos.CreateTaskResponse;
import com.bhargo.user.pojos.DataControlsAndApis;
import com.bhargo.user.pojos.DeviceIdResponse;
import com.bhargo.user.pojos.DeviceIdSendData;
import com.bhargo.user.pojos.ELGetUserDistributionRequestData;
import com.bhargo.user.pojos.ELGetUserDistributionsRefreshResponse;
import com.bhargo.user.pojos.ELGetUserDistributionsResponse;
import com.bhargo.user.pojos.EditTaskResponse;
import com.bhargo.user.pojos.ExceptionPostModel;
import com.bhargo.user.pojos.FilesTimeSpentModel;
import com.bhargo.user.pojos.FormDataResponse;
import com.bhargo.user.pojos.GetAllAppModel;
import com.bhargo.user.pojos.GetAllAppNamesData;
import com.bhargo.user.pojos.GetAppNamesForTaskModel;
import com.bhargo.user.pojos.GetChildFormDataResponse;
import com.bhargo.user.pojos.GetDesignDetailsData;
import com.bhargo.user.pojos.GetDesignDetailsModel;
import com.bhargo.user.pojos.GetTaskTransIDResponse;
import com.bhargo.user.pojos.GetTaskTransIDSendData;
import com.bhargo.user.pojos.GetTasksInvDtsData;
import com.bhargo.user.pojos.GetTasksInvDtsResponse;
import com.bhargo.user.pojos.GetUserAssessmentReportsResponse;
import com.bhargo.user.pojos.GetUserDistributionsResponse;
import com.bhargo.user.pojos.GridImagesModel;
import com.bhargo.user.pojos.GroupInfoResponse;
import com.bhargo.user.pojos.GroupsListModel;
import com.bhargo.user.pojos.InTaskDataModel;
import com.bhargo.user.pojos.InTaskRefreshSendData;
import com.bhargo.user.pojos.InsertAssessmentDetailsData;
import com.bhargo.user.pojos.InsertAssessmentDetailsResponse;
import com.bhargo.user.pojos.InsertComments;
import com.bhargo.user.pojos.InsertUserFileVisitsResponse;
import com.bhargo.user.pojos.InsertUserVisitsModel;
import com.bhargo.user.pojos.InsertUserVisitsResponse;
import com.bhargo.user.pojos.LoginModel;
import com.bhargo.user.pojos.NotificationDataResponse;
import com.bhargo.user.pojos.NotificationDirectSendData;
import com.bhargo.user.pojos.OTPData;
import com.bhargo.user.pojos.OTPModel;
import com.bhargo.user.pojos.OrgLanguages;
import com.bhargo.user.pojos.OutTaskDataModel;
import com.bhargo.user.pojos.OutTaskRefreshSendData;
import com.bhargo.user.pojos.RefreshELearning;
import com.bhargo.user.pojos.RefreshMain;
import com.bhargo.user.pojos.RefreshService;
import com.bhargo.user.pojos.TaskAppDetailsData;
import com.bhargo.user.pojos.UserAssessmentReportRequest;
import com.bhargo.user.pojos.UserDetailsData;
import com.bhargo.user.pojos.UserDetailsModel;
import com.bhargo.user.pojos.VersionCheckDataSend;
import com.bhargo.user.pojos.VersionCheckResponse;
import com.bhargo.user.pojos.callsql_Data_Model;
import com.bhargo.user.pojos.firebase.GetAllMessagesModel;
import com.bhargo.user.pojos.firebase.GroupsListResponse;
import com.bhargo.user.pojos.firebase.UsersAndGroups;
import com.bhargo.user.pojos.firebase.UsersAndGroupsList;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.RetrofitUtils;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GetServices {

    String API_PATH = AppConstants.API_NAME_CHANGE + "/V1.0/APIBuilderServices/APIServiceExecutionMethod";
    String FILE_UPLOAD_URL = RetrofitUtils.BASE_URL + AppConstants.FILE_UPLOAD_METHOD + "/FileUpload.aspx";// Bhargo
    String FILE_UPLOAD_URL_ML = RetrofitUtils.BASE_URL + "/improve_v3.2/MLFileUpload.aspx";
    String FILE_UPLOAD_URL_ML_TEST = RetrofitUtils.BASE_URL + "/improve_v3.2/MLTrainTest.aspx";
    String FILE_UPLOAD_URL_COMM = RetrofitUtils.BASE_URL + "/improve_v3.2/FileUploadForCommunication.aspx";

//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/SendMobileNo/")
//    Call<LoginModel> iLogin(@Body LoginData jLogin);

    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/SendOTPToBhargoUser/")
    Call<LoginModel> iLogin(@QueryMap Map<String, String> data);

//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/OTPVerification/")
//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/VerifyBhargoUser/")
//    Call<OTPModel> iOTPVerification(@Body OTPData jOTP);

    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/VerifyBhargoUser/")
    Call<OTPModel> iOTPVerification(@QueryMap Map<String,String> data);

  /*  @POST(AppConstants.API_NAME_CHANGE +"/V1.0/SubmitData/SubmitData")
    Call<FormDataResponse> sendFormData(@Body JsonObject formdata);*/

   /* @POST(AppConstants.API_NAME_CHANGE +"/V1.0/SubmitDataWithSubFormTbl/SubmitData")
    Call<FormDataResponse> sendFormData(@Body JsonObject formdata);*/

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/GetOrgList/")
    Call<OTPModel> getOrganisationsList(@Body OTPData jOTP);

//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/UserDetails/")
    //    Call<UserDetailsModel> iUserDetails(@Body UserDetailsData jOTP);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/GetUserDetails/")
    Call<UserDetailsModel> iUserDetails(@Header("Authorization") String token);

    //    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/GetDataAndAPIControls/")
//    Call<DataControlsAndApis> iGetDataControlsList(@Body GetAllAppNamesData jOTP);
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/GetDataAndAPIControls/")
    Call<DataControlsAndApis> iGetDataControlsList(@Header("Authorization") String token,@Body GetAllAppNamesData getAllAppNamesData);

    //    @POST(AppConstants.API_NAME_CHANGE +"/V1.0/LoginService/GetDistrubutedUserApps/")
//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/GetDistrubuteduserApps_updated/")
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/GetDistrubutedUserApps_byDisplayName/")
    Call<GetAllAppModel> iGetAllAppsList(@Header("Authorization") String token,@Body GetAllAppNamesData jOTP);
/*    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/GetDistrubutedUserApps_byDisplayNameProject/")
    Call<GetAllAppModel> iGetAllAppsList(@Header("Authorization") String token,@Body GetAllAppNamesData jOTP);*/

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/AppCreation/GetDesignDetails/")
    Call<GetDesignDetailsModel> iGetDesignDetails(@Body GetDesignDetailsData jOTP);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitDataWithSubFormTblWithTableSettings/SubmitData")
    Call<FormDataResponse> sendFormData_offline1(@Body JsonObject formdata);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitDataWithSubFormTblWithTableSettings/SubmitData")
    Call<ResponseBody> sendFormData_offline(@Header("Authorization") String token,@Body JsonObject formdata);

//    @GET(AppConstants.API_NAME_CHANGE +"/V1.0/APIBuilderServices/GetDetailsOfAPIServiceMethod")
//    Call<GetAPIDetails_Bean> GetAPIDetailsNew(@Query("OrgId") String OrgId,@Query("loginID") String loginID,@Query("APIName") String APIName);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitDataWithSubFormTblWithTableSettings/SubmitData")
    Call<ResponseBody> sendFormData(@Header("Authorization") String token,@Body JsonObject formdata);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitData/LTSubmitData")
    Call<FormDataResponse> sendLiveTrackingData(@Body JsonObject formdata);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/AppCreation/GetAPIDetails")
    Call<GetAPIDetails_Bean> GetAPIDetails(@Body Map<String, String> data);

    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/APIBuilderServices/GetDetailsOfAPIServiceMethod")
    Call<GetAPIDetails_Bean> GetAPIDetailsNew(@QueryMap Map<String, String> data);

   /* @GET(AppConstants.API_NAME_CHANGE + "/V1.0/APIBuilderServices/GetDetailsOfAPIServiceMethod")
    Call<String> GetAPIDetailsNew1(@Header("Authorization") String token,@QueryMap Map<String, String> data);
    */@GET(AppConstants.API_NAME_CHANGE + "/V1.0/APIBuilder/GetAPIDetails")
    Call<String> GetAPIDetailsNew1(@Header("Authorization") String token,@QueryMap Map<String, String> data);
    //http://182.18.157.124/BhargoAPI/V1.0/SMSGateway/SMSGatewayExecution
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/SMSGateway/SMSGatewayExecution")
    Call<String> sendSMSGatewayExecution(@Header("Authorization") String token,@Body JsonObject smsdata);

    //http://bsms.entrolabs.com/v3/api.php?username=entrolabs&apikey=13da8d76191878ba498a&senderid=entrol&templateid=1707165944947466118&mobile=9885549002&message=Your OTP for user verification is: 9876 ENTRO LABS
    @POST("{input}")
    Call<String> sendSMSGatewayExecutionPHP(@Path("input") String input,@QueryMap Map<String, String> data);


    //    @POST(AppConstants.API_NAME_CHANGE +"/V1.0/SubmitDataForApplications/formdata_New")
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitDataForApplications/formdata_New_Updated")
    Call<String> GetFormData(@QueryMap Map<String, String> data);

    //    @POST(AppConstants.API_NAME_CHANGE +"/V1.0/SubmitDataForApplications/formdata_New")
   /* @POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitDataForApplications/formdata_New_Updated_V1")
    Call<String> GetFormData1(@Header("Authorization") String token, @Body JsonObject formdata);
    */
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitDataWithSubFormTblWithTableSettings/formdata_New_Updated_V1")
    Call<String> GetFormData1(@Header("Authorization") String token,@Body JsonObject formdata);
    //http://182.18.157.124/Bhargo_V4.0_Demo_DbConnections/V1.0/SubmitDataWithSubFormTblWithTableSettings/ManageData

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitDataWithSubFormTblWithTableSettings/ManageData")
    Call<String> GetManageDataWizardServerCase(@Header("Authorization") String token,@Body JsonObject jsonObj);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/QueryBuilderServices/GetExecutionResult")
    Call<String> GetSQLorDML(@Header("Authorization") String token,@Body JsonObject formdata);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/QueryBuilderServices/GetExecutionResult")
    Call<String> GetSQLorDMLPOST(@Header("Authorization") String token,@Body Map<String, String> data);

    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/SMSGateway/SendMessage")
    Call<String> SendSMS(@QueryMap Map<String, String> data);

    //    @POST(AppConstants.API_NAME_CHANGE +"/V1.0/SendMails/SendMail")
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/EMailGateway/SendEMailExecution")
    Call<String> SendEmail(@Header("Authorization") String token,@Body Map<String, String> data);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/GetQueryData/Formquery")
    Call<ResponseBody> GetDataForQuery(@QueryMap Map<String, String> data);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/GetQueryData/Formquery_querytype")
    Call<ResponseBody> GetDataForQueryBased(@QueryMap Map<String, String> data);

    //=========Dynamically_Pass_Methods==========
    @Headers("Content-Type: text/html")
    @GET("{input}")
    Call<String> getServiceData(@Path("input") String input, @QueryMap Map<String, String> options,@Header("Authorization") String token);

    @Headers("Content-Type: text/html")
    @GET("{input}")
    Call<String> getServiceDataNew(@Path("input") String input, @QueryMap Map<String, String> options,@HeaderMap() Map<String,String> headerMap);

    @POST("{input}")
//    Call<String> getServiceDataForPost(@Path(value = "input", encoded = true) String input, @Body Map<String, String> options);
    Call<String> getServiceDataForPost(@Path("input") String input, @Body Map<String, String> options,@Header("Authorization") String token);

    @POST("{input}")
//    Call<String> getServiceDataForPost(@Path(value = "input", encoded = true) String input, @Body Map<String, String> options);
    Call<String> getServiceDataForPost1(@Path("input") String input, @QueryMap Map<String, String> optionsA, @Body Map<String, String> options,@Header("Authorization") String token);

    @POST("{input}")
    Call<String> getServiceDataForPostWithHeaders(@Path("input") String input, @Body Map<String, String> options, @HeaderMap Map<String, String> headers);

    @POST("{input}")
    Call<String> getServiceDataForPostDataObject(@Path("input") String input, @FieldMap Map<String, String> options);

    /*Refresh appsList Api*/
//    @POST(AppConstants.API_NAME_CHANGE +"/V1.0/LoginService/RefreshService_User/"

//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/RefreshService_User_PostID_Updated/")
//@POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/RefreshService_User_Updated/")
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/RefreshService_User_PostID_Updated")
    Call<RefreshService> getRefreshService(@Header("Authorization") String token,@Body RefreshMain mapRefreshData);

    /*Refresh appsList Api*/
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/GroupManagement/GetGroup_user_List")
    Call<UsersAndGroupsList> getUsersAndGroupsListBySearch(@Body UsersAndGroups usersAndGroups);

    /*Delete Session*/
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/Communication/Delete_Session")
    Call<ResponseBody> deleteSession(@Body JsonObject formdata);

    /*Refresh appsList Api*/
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/GroupManagement/GetGroupEmpDetails")
    Call<UsersAndGroupsList> getUsersByGroupID(@Body UsersAndGroups usersAndGroups);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/GetQueryData/getchildformdata")
    Call<GetChildFormDataResponse> getChildFormData(@QueryMap Map<String, String> data);

    //    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/GetDeviceID")
//    Call<DeviceIdResponse> sendDeviceIdToServer(@Body DeviceIdSendData data);
    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/InsertUserDeviceInfo")
    Call<DeviceIdResponse> sendDeviceIdToServer(@Header("Authorization") String token, @QueryMap Map<String, String> data);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/AppCreationNew/GetDesignDetailsFromNotifications")
    Call<NotificationDataResponse> getDesignFromNotification(@Body NotificationDirectSendData data);
    /*Deployment*/

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/GroupManagement/GetGroupList")
    Call<ResponseBody> GetGroupList(@Body Map<String, String> data);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/UserManagement/GetUserList")
    Call<ResponseBody> GetUserList(@Body Map<String, String> data);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/Deployment/InsertDeploymentData")
    Call<ResponseBody> InsertDeploymentData(@Body Map<String, String> data);

    /*Deployment*/
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/Deployment/BindPageNames")
    Call<AppTypeResponse> BindPageNamesList(@Body Map<String, String> data);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/Deployment/BindPageNames")
    Call<ResponseBody> BindPageNames(@Body Map<String, String> data);

    /*  *//*Editstart*//*
    @POST(AppConstants.API_NAME_CHANGE +"/V1.0/SubmitedDataUpdate/GetSubmitedDataForEdit/")
    Call<ResponseBody> iGetAppDataOnline(@Body AppDetailsAdvancedInput appDetailsAdvancedInput);*/
    /*Editstart*/
    /*@POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitedDataUpdate/GetSubmitedDataForEdit_Update")
    Call<ResponseBody> iGetAppDataOnline(@Body AppDetailsAdvancedInput appDetailsAdvancedInput);
//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitDataWithSubFormTblWithTableSettings/GetSubmitedDataForEdit_Update")
    */@POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitDataWithSubFormTblWithTableSettings/GetSubmitedDataForEdit_Update_Offline")
    Call<ResponseBody> iGetAppDataOnline(@Header("Authorization") String token,@Body AppDetailsAdvancedInput appDetailsAdvancedInput);

    /*Edit_Update_Offline*/
   /* @POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitedDataUpdate/GetSubmitedDataForEdit_Update_Offline")
    Call<ResponseBody> iGetAppDataOffline(@Header("Authorization") String token,@Body AppDetailsAdvancedInput appDetailsAdvancedInput);
    */
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitDataWithSubFormTblWithTableSettings/GetSubmitedDataForEdit_Update_Offline")
    Call<ResponseBody> iGetAppDataOffline(@Header("Authorization") String token,@Body AppDetailsAdvancedInput appDetailsAdvancedInput);

    /*For Search*/
    /*@POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitedDataUpdate/GetSubmitedDataForEdit_Update_Search")
    Call<ResponseBody> iGetAppDataOnlineForSearch(@Body AppDetailsAdvancedInput appDetailsAdvancedInput);
    */@POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitDataWithSubFormTblWithTableSettings/GetSubmitedDataForEdit_Update_Search")
    Call<ResponseBody> iGetAppDataOnlineForSearch(@Header("Authorization") String token,@Body AppDetailsAdvancedInput appDetailsAdvancedInput);

   /* @POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitedDataUpdate/DataDeleteorUpdate/")
    Call<DeviceIdResponse> iDeleteAppData(@Body AppDetailsAdvancedAction appDetailsAdvancedAction);
   */ @POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitDataWithSubFormTblWithTableSettings/DataDeleteorUpdate/")
    Call<DeviceIdResponse> iDeleteAppData(@Header("Authorization") String token,@Body AppDetailsAdvancedAction appDetailsAdvancedAction);

    //    Call<List<GetUserDistributionsResponse>> getUserDistributionsResponseCall(@Query("UserId") String UserId, @Query("DBNAME") String DBNAME);

   /* @POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitedDataUpdate/DataDeleteorUpdate/")
    Call<ResponseBody> sendEditFormData(@Header("Authorization") String token,@Body JsonObject formdata);
    */@POST(AppConstants.API_NAME_CHANGE + "/V1.0/SubmitDataWithSubFormTblWithTableSettings/DataDeleteorUpdate/")
    Call<ResponseBody> sendEditFormData(@Header("Authorization") String token,@Body JsonObject formdata);

    /*Editend*/
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/Icons_App")
    Call<GridImagesModel> getDefaultAppIcons(@Header("Authorization") String token,@Body AppIconData data);

    //    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/AppCreationNewWithSubFromLiveTracking/GetDesignDetailsForCallForm")
   /* @POST(AppConstants.API_NAME_CHANGE + "/V1.0/AppCreationNewWithSubFromLiveTracking/GetDesignDetailsForCallForm_Updated")
    Call<CallFormDataResponse> getDesignfromCallform(@Header("Authorization") String token,@Body Map<String, String> formdata);
   */ @POST(AppConstants.API_NAME_CHANGE + "/V1.0/AppCreationWithTableSettings/GetDesignDetailsForCallForm_Updated")
    Call<CallFormDataResponse> getDesignfromCallform(@Header("Authorization") String token,@Body Map<String, String> formdata);

//    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/GetGroupDetailsByUserId")
    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/GetGroupDetailsByUserId")
    Call<List<GroupsListModel>> getUserInGroupList(@Query("UserId") String UserId, @Query("DBNAME") String DBNAME);

//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/GetUserDistributions")
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/GetUserDistributions")
    Call<ELGetUserDistributionsResponse> getUserDistributionsResponseCall(@Body ELGetUserDistributionRequestData data);
//    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/GetUserDistributions")
//    Call<List<GetUserDistributionsResponse>> getUserDistributionsResponseCall(@Query("UserId") String UserId, @Query("DBNAME") String DBNAME, @Query("PostId") String PostId);

//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/InsertUserFileVisits")
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/InsertUserFileVisits")
    Call<InsertUserFileVisitsResponse> getInsertUserFileVisits(@Body FilesTimeSpentModel data);

//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/InsertUserVisits")
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/InsertUserVisits")
    Call<InsertUserVisitsResponse> getInsertUserVisits(@Body InsertUserVisitsModel data);

//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/InsertAssessmentDetailes")
//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/InsertAssessmentDetailes")
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/InsertAssessmentDetailes")
    Call<InsertAssessmentDetailsResponse> getAssessmentQuestionsData(@Body InsertAssessmentDetailsData data);

//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/InsertAssessmentAnswerDetailes")
//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/InsertAssessmentAnswerDetailes")
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/InsertAssessmentAnswerDetailes")
    Call<FormDataResponse> sendAssessmentAnswersData(@Body AssessmentAnswersData formdata);

//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/GetUserNewDistributions")
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/GetUserNewDistributions")
    Call<ELGetUserDistributionsRefreshResponse> getUserDistributionsRefresh(@Body RefreshELearning refreshELearning);

    //    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/GetUserAssessmentReport")
//    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/GetUserAssessmentReport")
//    Call<List<GetUserAssessmentReportsResponse>> getUserAssessmentReports(@Query("UserId") String UserId, @Query("DBNAME") String DBNAME, @Query("DistributionId") String DistributionId, @Query("PostId") String PostId);

//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/GetUserAssessmentReport")
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/ElearningArticlesService/GetUserAssessmentReport")
    Call<AssessmentReportResponseMain> getUserAssessmentReports(@Body UserAssessmentReportRequest report);

    @FormUrlEncoded
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/AppCreation/FileuploadMethodbase64")
    Call<JSONObject> sendFileasString(@Field("file_name") String file_name, @Field("file_type") String file_type, @Field("file_base64") String file_base64);
//    Call<TasksPriorityResponse> getTaskPriority(@Query("OrgId") String OrgId);

    /*Create Task Applications List*/


    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/GetApkVersion")
    Call<VersionCheckResponse> getVersionCheck(@Body VersionCheckDataSend data);

    /*In Tasks*/
//    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/Task/GetInTasksDts")
//    Call<InTasksResponse> getInTasks(@Query("UserId") String UserId, @Query("DBNAME") String OrgId, @Query("tskStatus") String tskStatus); /*In Tasks*/
    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/TaskManagmentService/GetAllMobileInTasks")
    Call<List<InTaskDataModel>> getInTasks(@Query("UserId") String UserId, @Query("DBNAME") String OrgId); /*In Tasks*/

    /*Out Tasks*/
//    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/Task/GetOutTasksDts")
//    Call<OutTasksResponse> getOutTasks(@Query("UserId") String UserId, @Query("DBNAME") String OrgId);
    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/TaskManagmentService/GetAllMobileOutTasks")
    Call<List<OutTaskDataModel>> getOutTasks(@Query("UserId") String UserId, @Query("DBNAME") String OrgId);
//    Call<ResponseBody> getTaskIndividualList(@Body GetAppNamesForTaskModel data);

    /*Tasks Priority*/
    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/Task/GetTaskPriority")
    Call<ResponseBody> getTaskPriority(@Query("OrgId") String OrgId);
//    Call<CreateTaskResponse> createTask(@Body JsonArray data);
//    Call<CreateTaskResponse> createTask(@Body JSONArray data);

//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/Task/InsertTaskDetails")
//    Call<CreateTaskResponse> createTask(@Body JsonObject data);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/TaskCreationSevice/GetAppNamesForTask")
    Call<ResponseBody> getAppNamesForTask(@Body GetAppNamesForTaskModel data);

    /*Create Task Content List*/
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/TaskManagmentService/GetContantData")
    Call<ResponseBody> getContentForTask(@Body GetAppNamesForTaskModel data);
//    Call<CreateTaskResponse> editTask(@Body JsonArray data);
//    Call<CreateTaskResponse> editTask(@Body JSONArray data);

    /*Create Task Group List*/
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/TaskManagmentService/GetGroupList")
    Call<ResponseBody> getTaskGroupList(@Body GetAppNamesForTaskModel data);

    /*Create Task Individuals List*/
//    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/UserManagement/GetUserList")
    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/TaskManagmentService/GetEmpList")
    Call<ResponseBody> GetEmpList(@Query("DBNAME") String DBNAME, @Query("UserId") String UserId, @Query("PostId") String PostId);

    /*Create Task*/
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/TaskManagmentService/InsertMobileTaskCreation")
    Call<CreateTaskResponse> createTask(@Body JsonObject data);

    /*Edit Task*/
    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/Task/GetTasksDts")
    Call<EditTaskResponse> getTaskDetailsEdit(@Query("OrgId") String OrgId, @Query("UserId") String UserId, @Query("TaskId") String TaskId);

    /*Update Task Details*/
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/Task/UpdateTaskDetails")
    Call<CreateTaskResponse> editTask(@Body JsonObject data);

    /*Task Modify Get trans Id*/
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/Task/GetTaskTransID")
    Call<GetTaskTransIDResponse> getTaskTransID(@Body GetTaskTransIDSendData data);
//    Call<InTaskDataModelResponse> getInTaskRefreshData(@Body InTaskRefreshSendData data);

    /*Task Modify Get trans Id*/
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/Task/GetTasksInvDts")
    Call<GetTasksInvDtsResponse> getTasksInvDts(@Body GetTasksInvDtsData data);
//    @POST(AppConstants.API_NAME_CHANGE +"/V1.0/Task/RefreshOutTasksDts")
//    Call<OutTaskDataModelResponse> getOutTaskRefreshData(@Body OutTaskRefreshSendData data);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/GetAppDetails_Task")
    Call<AppDetailsResponse> getAppDetails(@Body TaskAppDetailsData data);

    //    @POST(AppConstants.API_NAME_CHANGE +"/V1.0/Task/InsertTaskCmtDetails")
//    Call<CommentsResponse> insertCommentsDetails(@Body SendCommentData data);
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/TaskManagmentService/InsertMobileTaskCommentDetails")
    Call<CommentsResponse> insertCommentsDetails(@Body InsertComments data);

    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/TaskManagmentService/GetAllMobileTaskComments")
    Call<List<CommentsInfoModel>> getAllCommentsDetails(@Query("DBNAME") String OrgId, @Query("UserId") String UserId, @Query("PostId") String postId, @Query("TaskId") String TaskId);

    //    @POST(AppConstants.API_NAME_CHANGE +"/V1.0/TaskManagmentService/RefreshInTaskDetails")
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/TaskManagmentService/RefreshInTaskDetails")
    Call<List<InTaskDataModel>> getInTaskRefreshData(@Body InTaskRefreshSendData data);

    //    @POST(AppConstants.API_NAME_CHANGE +"/V1.0/Task/RefreshInTasksDts")
//    Call<InTaskDataModelResponse> getInTaskRefreshData(@Body InTaskRefreshSendData data);
//
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/TaskManagmentService/RefreshOutTaskDetails")
    Call<List<OutTaskDataModel>> getOutTaskRefreshData(@Body OutTaskRefreshSendData data);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/Communication/GetGroupData")
    Call<GroupsListResponse> GetGroupsList(@Body Map<String, String> data);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/Communication/InsertSession")
    Call<GroupsListResponse> createSession(@Body Map<String, String> data);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/Communication/GetOfflineData")
    Call<GetAllMessagesModel> iGetAllMessagesList(@Header("Authorization") String token,@Body Map<String, String> data);

    /*Update Task Details*/
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/Communication/InsertTopic")
    Call<GroupsListResponse> sendTopicMessageToServer(@Body Map<String, String> data);

    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/GroupManagement/GetGroupDatalist")
//    Call<GroupsListResponse> iGetAllGroupsList(@Header("Authorization") String token,@Body Map<String, String> data);
    Call<GroupsListResponse> iGetAllGroupsList(@Header("Authorization") String token,@QueryMap Map<String, String> data);

/*
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/GroupManagement/GetGroupDatalist")
    Call<GroupsListResponse> iGetAllGroupsList(@Body Map<String, String> data);
*/

    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/GroupManagement/GetGroupInformation")
    Call<GroupInfoResponse> getGroupInfoDetails(@Header("Authorization") String token,@Query("OrgId") String OrgId, @Query("GroupID") String GroupID, @Query("SessionID") String SessionID);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/DeviceLogout")
    Call<GroupsListResponse> logoutUser(@Body DeviceIdSendData data);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/UserManagement/GetSearchUserList_UserControl")
    Call<ResponseBody> getUserList(@Body Map<String, String> data);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/GetSemiOfflinefiles/")
    Call<DataControlsAndApis> iGetDataControlsPartial(@Body Map<String, String> data);

    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/TaskManagmentService/GetTaskAttempts")
    Call<ResponseBody> getTaskAttempts(@Query("UserId") String UserId, @Query("DBNAME") String DBNAME, @Query("PostId") String PostId, @Query("TaskId") String TaskId);

    //    @GET(AppConstants.API_NAME_CHANGE +"/V1.0/Deployment/SendNotifications_New")
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/AppDeployment/SendNotifications_NewUpdated")
    Call<String> sendNotification(@QueryMap Map<String, String> data,@Header("Authorization") String token);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/MLTraining/InsertMLTrainng")
    Call<FormDataResponse> InsertMLTrainng(@Body JsonObject formdata);

    @POST(AppConstants.API_NAME_CHANGE + "/LoginService/GetLanguages")
    Call<OrgLanguages> getLanguages(@Body UserDetailsData jOTP);

    @GET(AppConstants.API_NAME_CHANGE + "/V1.0/QueryBuilderServices/GetSampleResult")
    Call<callsql_Data_Model> getSampleResult(@Header("Authorization") String token,@QueryMap Map<String, String> data);

    @POST(AppConstants.API_NAME_CHANGE + "/SubmitDataWithSubFromTableSettingUpdate/ExceptionSubmitData")
    Call<ResponseBody> exceptionService(@Body ExceptionPostModel exceptionPostModel);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/BhargoMasterData/GetMasterAndCustomDataControlsData/")
    Call<DataControlsAndApis> GetMasterControlsData(@Header("Authorization") String token,@Body JsonObject Data);

    //With Data Management Options
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/LoginService/GetDistrubutedUserApps_byDisplayName/")
    Call<GetAllAppModel> iGetAllAppsListWithDMO(@Body GetAllAppNamesData jOTP);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/QueryBuilderServices/GetDirectQueryExecutionResult")
    Call<String> GetDataDirectQuery(@Header("Authorization") String token,@Body JsonObject formdata);

    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/UserManagementServices/InsertExternalUser")
    Call<String> socialLogin(@Body JsonObject formdata);
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/logstashlog/InsertAccessLog")
    Call<ResponseBody> insertAccessLog(@Body JsonObject data);
    @POST(AppConstants.API_NAME_CHANGE + "/V1.0/logstashlog/InsertErrorLog")
    Call<ResponseBody> insertErrorLog(@Body JsonObject data);
}
