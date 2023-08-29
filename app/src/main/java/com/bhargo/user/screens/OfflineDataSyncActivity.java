package com.bhargo.user.screens;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Java_Beans.API_OutputParam_Bean;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.GetAPIDetails_Bean;
import com.bhargo.user.Java_Beans.QueryFilterField_Bean;
import com.bhargo.user.R;
import com.bhargo.user.adapters.OfflineSyncAppsListAdapter;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.interfaces.ItemClickListener;
import com.bhargo.user.pojos.CallAPIRequestDataSync;
import com.bhargo.user.pojos.FormDataResponse;
import com.bhargo.user.pojos.GetAllAppModel;
import com.bhargo.user.pojos.OfflineDataSync;
import com.bhargo.user.pojos.SubformMapExistingData;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.FileUploader;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SK_RestCall_WCF;
import com.bhargo.user.utils.SK_RestCall_WebAPI;
import com.bhargo.user.utils.SK_ServiceCall;
import com.bhargo.user.utils.SK_WebAPI_interpreter;
import com.bhargo.user.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.utils.AppConstants.GlobalObjects;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

public class OfflineDataSyncActivity extends BaseActivity implements ItemClickListener {

    private static final String TAG = "AppsListActivity";

    RecyclerView rv_apps;
    GetServices getServices;
    Context context;
    OfflineSyncAppsListAdapter appsAdapter;
    RelativeLayout rl_AppsListMain;

    CustomTextView ct_alNoRecords;
    ImproveHelper improveHelper;

    SessionManager sessionManager;
    ImproveDataBase improveDataBase = new ImproveDataBase(this);
    List<OfflineDataSync> appDetailsList;
    Thread t1, t2, t3;
    JSONObject mainObject;
    List<String> stringListSubmit = new ArrayList<>();
    List<HashMap<String, String>> stringListFiles = new ArrayList<>();
    List<List<List<String>>> subFormStringList = new ArrayList<>();
    List<List<List<HashMap<String, String>>>> subFormFilesList = new ArrayList<>();
    List<HashMap<String, String>> tempList;
    String strOrgId = "";
    String strAppName = "";
    String strCreatedBy = "";
    String strUserId = "";
    String strPostId = "";
    String strDistributionId = "";
    int offlineRecordCount = 0;
    List<OfflineDataSync> offlineDataSyncList = new ArrayList<>();
    String pageName = "Offline Data Sync";
    Gson gson;
    ProgressDialog Sk_serviceDialog;
    SK_WebAPI_interpreter sk_Rest_interpreter;
    SK_WebAPI_interpreter sk_Rest_interpreterQuery;
    SK_ServiceCall sk_soapobj;
    SK_RestCall_WebAPI sk_Restobj;
    SK_RestCall_WCF sk_Restobj_WCF;
    private String strOrgName;
    private GetAllAppModel getAllAppModel;
    private String i_OrgId;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sync_offline);

        context = OfflineDataSyncActivity.this;
        gson = new Gson();
        improveHelper = new ImproveHelper(context);
        sessionManager = new SessionManager(context);
        initializeActionBar();
        enableBackNavigation(true);
        ib_settings.setVisibility(View.GONE);
        iv_circle_appIcon.setVisibility(View.VISIBLE);

        if (pageName != null) {
            title.setText(pageName);
        } else {
            title.setText(getString(R.string.improve_user));
        }

        showProgressDialog(getString(R.string.please_wait));

        getServices = RetrofitUtils.getUserService();


        ct_alNoRecords = findViewById(R.id.ct_alNoRecords);
        rv_apps = findViewById(R.id.rv_apps);
        rl_AppsListMain = findViewById(R.id.rl_AppsListMain);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_apps.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        i_OrgId = sessionManager.getOrgIdFromSession();
        getAppsDataOffLine(i_OrgId);


    }

    public void getAppsDataOffLine(String i_OrgId) {

        try {
            appDetailsList = new ArrayList<>();
            String status = "0";
            appDetailsList = improveDataBase.getDataFromSubmitFormListTable(i_OrgId, status, sessionManager.getUserDataFromSession().getUserID());

            appDetailsList.addAll(improveDataBase.getDataFromCallAPI_RequestTable(i_OrgId, sessionManager.getUserDataFromSession().getUserID()));

            if (appDetailsList.size() > 0) {
                dismissProgressDialog();
                ct_alNoRecords.setVisibility(View.GONE);
                rv_apps.setVisibility(View.VISIBLE);
                appsAdapter = new OfflineSyncAppsListAdapter(OfflineDataSyncActivity.this, appDetailsList);
                rv_apps.setAdapter(appsAdapter);
                appsAdapter.setCustomClickListener(OfflineDataSyncActivity.this);
            } else {
                dismissProgressDialog();
                ct_alNoRecords.setVisibility(View.VISIBLE);
                rv_apps.setVisibility(View.GONE);
            }




        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getAppsDataOffLine", e);
        }

    }

    @SuppressLint("NewApi")
    @Override
    public void onCustomClick(Context context, View view, int position, String orgId) {
        try {
            if (appDetailsList.get(position).getSync_Type() == null || appDetailsList.get(position).getSync_Type().equalsIgnoreCase("App")) {
                String formStringTemp = appDetailsList.get(position).getPrepared_json_string();
                String formStringFilesTemp = appDetailsList.get(position).getPrepared_files_json_string();
                String subformStringTemp = appDetailsList.get(position).getPrepared_json_string_subform();
                String subformStringFilesTemp = appDetailsList.get(position).getPrepared_files_json_string_subform();
                if ((formStringTemp != null && !formStringTemp.isEmpty()) || (formStringFilesTemp != null && !formStringFilesTemp.isEmpty()) || (subformStringTemp != null && !subformStringTemp.isEmpty()) || (subformStringFilesTemp != null && !subformStringFilesTemp.isEmpty())) {

                    if (isNetworkStatusAvialable(context)) {

                        improveHelper.showProgressDialog(getString(R.string.synchronizing_data));
                        offlineDataSyncList = improveDataBase.getAppDataFromSubmitFormListTable(i_OrgId, appDetailsList.get(position).getApp_name(), "0", sessionManager.getUserDataFromSession().getUserID());

                        offlineRecordCount = 0;


                        sendOfflineData(offlineDataSyncList.get(offlineRecordCount));

                    } else {

                        improveHelper.snackBarAlertActivities(context, rl_AppsListMain);
                    }


                } else {
                    Toast.makeText(context, getString(R.string.data_not_found), Toast.LENGTH_SHORT).show();
                }
            } else if (appDetailsList.get(position).getSync_Type().equalsIgnoreCase("Request")) {
                if (isNetworkStatusAvialable(context)) {
                    CallAPIRequestDataSync CallAPIRequest = appDetailsList.get(position).getCallAPIRequest();
                    offlineDataSyncList = improveDataBase.getDataFromCallAPI_RequestTableByAPIName(CallAPIRequest.getOrgId(), CallAPIRequest.UserID, CallAPIRequest.getAPIName());
                    offlineRecordCount = 0;

                    SendCallAPIOfflineRequest(offlineDataSyncList.get(offlineRecordCount).CallAPIRequest);
                } else {
                    improveHelper.snackBarAlertActivities(context, rl_AppsListMain);
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "onCustomClick", e);
        }

    }

    private void SendCallAPIOfflineRequest(CallAPIRequestDataSync CallAPIRequestDataSync) {

        try {
            String Sno = CallAPIRequestDataSync.getSno();
            String APIName = CallAPIRequestDataSync.getAPIName();
            String OrgId = CallAPIRequestDataSync.getOrgId();
            String loginID = CallAPIRequestDataSync.getUserID();
            String InputJSON = CallAPIRequestDataSync.getInputJSONData();


            Map<String, String> data = new HashMap<>();
            data.put("OrgId", OrgId);
            data.put("loginID", loginID);
            data.put("APIName", APIName);

            Call<String> call = getServices.GetAPIDetailsNew1(sessionManager.getAuthorizationTokenId(),data);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {


                    try {
                        JSONObject Jobj = new JSONObject(response.body());


                        GetAPIDetails_Bean getAPIDetails = new GetAPIDetails_Bean();

                        if (Jobj.getString("Status").equalsIgnoreCase("$200-")) {
                            getAPIDetails.setStatus(Jobj.getString("Status"));
                            JSONObject JS_ServiceData = Jobj.getJSONObject("ServiceData");
                            GetAPIDetails_Bean.APIDetails ServiceData = getAPIDetails.NewAPIDetails();

                            ServiceData.setServiceName(JS_ServiceData.getString("ServiceName"));
                            ServiceData.setServiceDesc(JS_ServiceData.getString("ServiceDesc"));
                            ServiceData.setServiceType(JS_ServiceData.getString("ServiceType"));
                            ServiceData.setServiceSource(JS_ServiceData.getString("ServiceSource"));
                            ServiceData.setServiceCallsAt(JS_ServiceData.getString("ServiceCallsAt"));
                            ServiceData.setServiceResult(JS_ServiceData.getString("ServiceResult"));
                            ServiceData.setServiceURl(JS_ServiceData.getString("ServiceURL"));
                            ServiceData.setOutputType(JS_ServiceData.getString("OutputType"));
                            ServiceData.setMethodName(JS_ServiceData.getString("serviceMethod"));
                            ServiceData.setNameSpace(JS_ServiceData.getString("NameSpace"));
                            ServiceData.setMethodType(JS_ServiceData.getString("MethodType"));
                            ServiceData.setQueryType(JS_ServiceData.getString("QueryType"));
                            ServiceData.setSuccessCaseDetails(JS_ServiceData.getJSONObject("SuccessCaseDetails").toString());
                            getAPIDetails.setServiceData(ServiceData);

                            Map<String, String> InputMap = new HashMap<>();
                            Map<String, String> HeaderMap = new HashMap<>();

                            System.out.println("InputJSON.toString()=========" + InputJSON);

                            InputMap = new Gson().fromJson(InputJSON, HashMap.class);


                            GetAPIDetails_Bean.APIDetails APIDetails = getAPIDetails.getServiceData();
                            Sk_serviceDialog = new ProgressDialog(OfflineDataSyncActivity.this);
                            Sk_serviceDialog.setMessage("Please Wait...  Loading " + APIDetails.getServiceName() + "...");
                            Sk_serviceDialog.setCancelable(false);

                            Sk_serviceDialog.setOnDismissListener(new API_DilogDismiss(Sno));
                            Sk_serviceDialog.show();

                            String ServiceURl = "";
                            if (APIDetails.getServiceSource().equalsIgnoreCase("Service Based")) {
                                if (APIDetails.getServiceCallsAt().equalsIgnoreCase("Server")) {

                                    try {

                                        Map<String, String> ServerMap = new HashMap<>();
                                        ServerMap.put("serviceSource", "1");
                                        ServerMap.put("queryName", "QB");
                                        ServerMap.put("loginID", GlobalObjects.getUser_ID());
                                        ServerMap.put("stringURL", APIDetails.getServiceURl());
                                        ServerMap.put("outputType", APIDetails.getOutputType());
                                        ServerMap.put("serviceMethod", APIDetails.getMethodName());
                                        ServerMap.put("serviceType", APIDetails.getServiceType());
                                        ServerMap.put("methodType", APIDetails.getMethodType());
                                        ServerMap.put("inputParameters", InputJSON);


                                        sk_Rest_interpreter = new SK_WebAPI_interpreter();
                                        sk_Rest_interpreter.CallSoap_Service(false, APIDetails.getNameSpace(), ServerMap,
                                                ImproveHelper.getOutputParams(APIDetails.getSuccessCaseDetails()),
                                                APIDetails.getOutputType(), APIDetails.getServiceResult(), Sk_serviceDialog,
                                                ImproveHelper.getOutputParamswithPaths(APIDetails.getSuccessCaseDetails()),
                                                ImproveHelper.gethdOutputSaveFlow(APIDetails.getSuccessCaseDetails()),
                                                APIDetails.getServiceSource(), APIDetails.getQueryType(),sessionManager.getAuthorizationTokenId());

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    ServiceURl = APIDetails.getServiceURl();

                                    if (APIDetails.getServiceType().trim().equalsIgnoreCase("Soap Web Service")) {
                                        sk_soapobj = new SK_ServiceCall();
                                        sk_soapobj.CallSoap_Service(ServiceURl, APIDetails.getMethodName(), APIDetails.getNameSpace(), InputMap,
                                                ImproveHelper.getOutputParams(APIDetails.getSuccessCaseDetails()), APIDetails.getOutputType(), APIDetails.getServiceResult(),
                                                Sk_serviceDialog,
                                                ImproveHelper.getOutputParamswithPaths(APIDetails.getSuccessCaseDetails()),
                                                ImproveHelper.gethdOutputSaveFlow(APIDetails.getSuccessCaseDetails()),
                                                APIDetails.getServiceSource(), APIDetails.getQueryType());
                                    } else if (APIDetails.getServiceType().trim().equalsIgnoreCase("WCF Service")) {
                                        sk_Restobj_WCF = new SK_RestCall_WCF();
                                        sk_Restobj_WCF.CallSoap_Service(ServiceURl, APIDetails.getNameSpace(), InputMap,
                                                ImproveHelper.getOutputParams(APIDetails.getSuccessCaseDetails()), APIDetails.getOutputType(),
                                                APIDetails.getServiceResult(), Sk_serviceDialog, APIDetails.getMethodType(),
                                                ImproveHelper.getOutputParamswithPaths(APIDetails.getSuccessCaseDetails()),
                                                ImproveHelper.gethdOutputSaveFlow(APIDetails.getSuccessCaseDetails()),
                                                APIDetails.getServiceSource(), APIDetails.getQueryType(),sessionManager.getAuthorizationTokenId());
                                    } else {
                                        sk_Restobj = new SK_RestCall_WebAPI();
                                        sk_Restobj.CallSoap_Service(ServiceURl, APIDetails.getNameSpace(), InputMap,null,
                                                ImproveHelper.getOutputParams(APIDetails.getSuccessCaseDetails()), APIDetails.getOutputType(),
                                                APIDetails.getServiceResult(), Sk_serviceDialog, APIDetails.getMethodType(),
                                                ImproveHelper.getOutputParamswithPaths(APIDetails.getSuccessCaseDetails()),
                                                ImproveHelper.gethdOutputSaveFlow(APIDetails.getSuccessCaseDetails()),
                                                APIDetails.getServiceSource(), APIDetails.getQueryType(),null);
                                    }
                                }
                            } else if (APIDetails.getServiceSource().equalsIgnoreCase("Query Based")) {
                                try {

                                    JSONArray Jarr = new JSONArray();


                                    Map<String, String> ServerMap = new HashMap<>();
                                    ServerMap.put("serviceSource", "2");
                                    ServerMap.put("queryName", "QB");
                                    ServerMap.put("orgID", AppConstants.GlobalObjects.getOrg_Name());
                                    ServerMap.put("loginID", GlobalObjects.getUser_ID());
                                    ServerMap.put("stringURL", APIDetails.getServiceURl());
                                    ServerMap.put("outputType", APIDetails.getOutputType());
                                    ServerMap.put("serviceMethod", APIDetails.getMethodName());
                                    ServerMap.put("serviceType", APIDetails.getServiceType());
                                    ServerMap.put("methodType", APIDetails.getMethodType());
                                    ServerMap.put("inputParameters", InputJSON);

                                    if (APIDetails.getQueryType().equalsIgnoreCase("DML")) {
                                        ServerMap.put("queryType", APIDetails.getQueryType());
                                    } else {
                                        ServerMap.put("queryType", "Select");
                                    }

                                    sk_Rest_interpreterQuery = new SK_WebAPI_interpreter();
                                    sk_Rest_interpreterQuery.CallSoap_Service(false, APIDetails.getNameSpace(), ServerMap,
                                            ImproveHelper.getOutputParams(APIDetails.getSuccessCaseDetails()),
                                            APIDetails.getOutputType(), APIDetails.getServiceResult(), Sk_serviceDialog,
                                            ImproveHelper.getOutputParamswithPaths(APIDetails.getSuccessCaseDetails()),
                                            ImproveHelper.gethdOutputSaveFlow(APIDetails.getSuccessCaseDetails()),
                                            APIDetails.getServiceSource(), APIDetails.getQueryType(),sessionManager.getAuthorizationTokenId());

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException E) {

                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "SendCallAPIOfflineRequest", e);
        }

    }


    private void sendOfflineData(OfflineDataSync offlineDataSync) {

        try {
            strPostId = offlineDataSync.getSubmittedUserPostID();
            stringListSubmit = new ArrayList<>();
            stringListFiles = new ArrayList<>();

            String formString = offlineDataSync.getPrepared_json_string();
            String formStringFiles = offlineDataSync.getPrepared_files_json_string();
            String subformString = offlineDataSync.getPrepared_json_string_subform();
            String subformStringFiles = offlineDataSync.getPrepared_files_json_string_subform();

            // Form String

            if (formString != null && !formString.isEmpty()) {
                String tempStringSubmit = formString.substring(1, formString.length() - 1);

                for (int i = 0; i < tempStringSubmit.split(",").length; i++) {

                    if (!tempStringSubmit.split(",")[i].isEmpty()) {
                        stringListSubmit.add(tempStringSubmit.split(",")[i].trim());
                    }

                }
            }

// Form Files
            if (formStringFiles != null && !formStringFiles.isEmpty()) {
                JsonParser jsonParserForm = new JsonParser();
                JsonArray jsonArrayForm = (JsonArray) jsonParserForm.parse(formStringFiles);

                for (int i3 = 0; i3 < jsonArrayForm.size(); i3++) {


                    try {

                        JSONObject object = new JSONObject(jsonArrayForm.get(i3).toString());

                        for (int i4 = 0; i4 < object.names().length(); i4++) {

                            HashMap<String, String> tempMap = new HashMap<>();

                            tempMap.put(object.names().getString(i4).trim(), object.getString(object.names().getString(i4)));


                            Log.d(TAG, "innermap: " + tempMap);
                            if (tempMap.size() > 0) {
                                stringListFiles.add(tempMap);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                Log.d(TAG, "stringfiles: " + stringListFiles);
            }

            // Sub Form String
            try {


                String[] temp1 = subformString.split("], \\[\\[");

                for (int i = 0; i < temp1.length; i++) {
                    List<List<String>> ssSub = new ArrayList<>();
                    String[] temp2 = temp1[i].split("],");

                    for (int j = 0; j < temp2.length; j++) {


                        String tempStringSubFormFiles = temp2[j].replace("[", "");
                        String tempStringSubformFilesFinal = tempStringSubFormFiles.replace("]", "");
                        String[] tempStringSubformFilesFinalString = tempStringSubformFilesFinal.split(",");
                        List<String> ss = new ArrayList<>();
                        for (int k = 0; k < tempStringSubformFilesFinalString.length; k++) {

                            if (!tempStringSubformFilesFinalString[k].isEmpty()) {
                                ss.add(tempStringSubformFilesFinalString[k].trim());
                            }
                        }

                        if (ss != null && ss.size() > 0) {
                            ssSub.add(ss);
                        }
                    }
                    if (ssSub != null && ssSub.size() > 0) {
                        subFormStringList.add(ssSub);
                    }
                }


            } catch (Exception e) {
                Log.d(TAG, "exceptionZ_string: " + e.toString());
            }


            try {
                if (!subformStringFiles.isEmpty()) {

                    JsonParser jsonParser = new JsonParser();
                    JsonArray jsonArray1 = (JsonArray) jsonParser.parse(subformStringFiles);

                    for (int i1 = 0; i1 < jsonArray1.size(); i1++) {


                        JsonArray jsonArray2 = (JsonArray) jsonParser.parse(jsonArray1.get(i1).toString());
                        List<List<HashMap<String, String>>> rows = new ArrayList<>();
                        for (int i2 = 0; i2 < jsonArray2.size(); i2++) {


                            JsonArray jsonArray3 = (JsonArray) jsonParser.parse(jsonArray2.get(i2).toString());

                            if (jsonArray3.size() > 0) {
                                List<HashMap<String, String>> controls = new ArrayList<>();
                                for (int i3 = 0; i3 < jsonArray3.size(); i3++) {


                                    try {

                                        JSONObject object = new JSONObject(jsonArray3.get(i3).toString());

                                        for (int i4 = 0; i4 < object.names().length(); i4++) {

                                            HashMap<String, String> tempMap = new HashMap<>();

                                            tempMap.put(object.names().getString(i4).trim(), object.getString(object.names().getString(i4)));


                                            Log.d(TAG, "innermap: " + tempMap);

                                            controls.add(tempMap);

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                rows.add(controls);

                            }


                        }
                        if (rows.size() > 0) {
                            subFormFilesList.add(rows);
                        }

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d(TAG, "subFormStringList: " + subFormStringList);
            Log.d(TAG, "subFormFilesList_: " + subFormFilesList);
            Log.d(TAG, "FormString: " + stringListSubmit);
            Log.d(TAG, "FormFiles: " + stringListFiles);

            tempList = new ArrayList<>();

            for (int i = 0; i < subFormFilesList.size(); i++) {
                Log.d(TAG, "subFormFilesList_&_: " + subFormFilesList);

                for (int j = 0; j < subFormFilesList.get(i).size(); j++) {

                    for (int k = 0; k < subFormFilesList.get(i).get(j).size(); k++) {

                        //HashMap<String,String> tempMap = new HashMap<>();

                        tempList.add(subFormFilesList.get(i).get(j).get(k));

                    }

                }

            }


            try {
                Log.d(TAG, "FormString1: " + stringListSubmit);
                if (offlineDataSync.getHasMapExisting().equalsIgnoreCase("no")) {
                    sendData(offlineDataSync);
                } else {
                    sendDataMapExisting(offlineDataSync);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "sendOfflineData", e);
        }

    }

    private void refreshAppsList() {
        try {
            appDetailsList = new ArrayList<>();
            String status = "0";
            appDetailsList = improveDataBase.getDataFromSubmitFormListTable(i_OrgId, status, sessionManager.getUserDataFromSession().getUserID());
            if (appDetailsList.size() > 0) {
                appsAdapter.updateData(appDetailsList);
            } else {


                ct_alNoRecords.setVisibility(View.VISIBLE);
                rv_apps.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "refreshAppsList", e);
        }

    }

    private void sendDataMapExisting(final OfflineDataSync offlineDataSync) throws InterruptedException {
        try {
            if (isNetworkStatusAvialable(context)) {

                t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (stringListFiles != null && stringListFiles.size() > 0) {
                                    int filesCount = stringListFiles.size();
                                    for (int fileCount = 0; fileCount < stringListFiles.size(); fileCount++) {

                                        sendFilesToServer(stringListFiles.get(fileCount), fileCount, filesCount);

                                    }
                                } else {
                                    t3.start();
                                    try {
                                        t3.join();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
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

                              /*  try {

                                    mainObject = new JSONObject();

                                    mainObject.put("sno", offlineDataSync.getSno());
                                    mainObject.put("OrgId", offlineDataSync.getOrgId());
                                    mainObject.put("PageName", offlineDataSync.getApp_name());
                                    mainObject.put("CreatedUserID", offlineDataSync.getCreatedUserID());
                                    mainObject.put("SubmittedUserID", offlineDataSync.getSubmittedUserID());
                                    mainObject.put("SubmittedUserPostID", strPostId);
                                    mainObject.put("PostId", strPostId);
                                    mainObject.put("DistributionID", offlineDataSync.getDistributionID());
                                    mainObject.put("IMEI", offlineDataSync.getIMEI());
                                    mainObject.put("OperationType", "");
                                    mainObject.put("TransID", "");

//Auto Increment Control
                               *//* mainObject.put("IfautoincrementControls", "false");
                                JSONArray jsonArrayAuto = new JSONArray();
                                mainObject.put("AutoIncrementControl", jsonArrayAuto);*//*
                                    insertCheckListValueinMainObject(offlineDataSync);
                                    insertAutoNumberValueinMainObject(offlineDataSync);

                                    if (offlineDataSync.getTableSettingsType() == null) {
                                        mainObject.put("TableName", offlineDataSync.getCreatedUserID() + "_" + ImproveHelper.getTableNameWithOutSpace(offlineDataSync.getApp_name()));
                                    } else if (offlineDataSync.getTableSettingsType().equalsIgnoreCase("Create New Table")) {
                                        mainObject.put("TableName", offlineDataSync.getCreatedUserID() + "_" + ImproveHelper.getTableNameWithOutSpace(offlineDataSync.getApp_name()));
                                    } else if (offlineDataSync.getTableSettingsType().equalsIgnoreCase("Map existing table")) {
                                        mainObject.put("TableName", offlineDataSync.getTableName());
                                    }

                                    mainObject.put("TypeofSubmission", "");
                                    mainObject.put("insertColumns", new JSONArray());
                                    mainObject.put("UpdateColumns", new JSONArray());
                                    mainObject.put("tableSettingsType", "Create New Table");

                             *//*   int maxSize = 0;
                                if (subFormStringList.size() > 0) {
                                    for (int i = 0; i < subFormStringList.size(); i++) {

                                        if (subFormStringList.get(i).size() > maxSize) {

                                            maxSize = subFormStringList.get(i).size();
                                        }

                                    }
                                }

                                if (maxSize == 0) {
                                    maxSize = 1;
                                }*//*

                                    JSONArray array = new JSONArray();
                                    JSONArray arraySubform = new JSONArray();
//                                for (int i = 0; i < maxSize; i++) {

                                    JSONObject jsonObject = new JSONObject();
                                    if (stringListSubmit.size() > 0) {
                                        for (int j = 0; j < stringListSubmit.size(); j++) {
                                            String[] strData = stringListSubmit.get(j).split("\\|");
                                            if (strData.length > 1) {
                                                jsonObject.put(strData[0], strData[1]);
                                            } else {
                                                jsonObject.put(strData[0], "");
                                            }
                                        }
                                    }
                                    //map existing
                                    JSONObject jsonObjectMapExistingInsert = new JSONObject();
                                    JSONObject jsonObjectMapExistingSetColumns = new JSONObject();
                                    JSONArray jsonArrayFilters = new JSONArray();
                                    if (offlineDataSync.getTableSettingsType() != null) {
                                        if (offlineDataSync.getTableSettingsType().equalsIgnoreCase("Map existing table")) {
                                            if (offlineDataSync.getMapExistingType().equalsIgnoreCase("Insert")) {
                                                List<QueryFilterField_Bean> MainTableInsertFields = gson.fromJson(offlineDataSync.getInsertFields(), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                                                }.getType());
                                                for (QueryFilterField_Bean insertFields : MainTableInsertFields) {
//                                            String fieldValue = ImproveHelper.getValueFromGlobalObject(context, insertFields.getField_Global_Value());
                                                    String fieldValue = getValueFromOfflineJson(offlineDataSync, insertFields.getField_Name(), "Insert", stringListSubmit, insertFields.getField_Global_Value());
                                                    jsonObjectMapExistingInsert.put(insertFields.getField_Name(), fieldValue);
                                                }
                                            } else if (offlineDataSync.getMapExistingType().equalsIgnoreCase("Update")) {
                                                List<QueryFilterField_Bean> MainTableUpdateFields = gson.fromJson(offlineDataSync.getUpdateFields(), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                                                }.getType());
                                                for (QueryFilterField_Bean setColumns : MainTableUpdateFields) {
                                                    if (!setColumns.isField_IsDeleted()) {
//                                                String fieldValue = ImproveHelper.getValueFromGlobalObject(context, setColumns.getField_Global_Value());
                                                        String fieldValue = getValueFromOfflineJson(offlineDataSync, setColumns.getField_Name(), "Update", stringListSubmit, setColumns.getField_Global_Value());
                                                        jsonObjectMapExistingSetColumns.put(setColumns.getField_Name(), fieldValue);
                                                    }
                                                }
                                                List<QueryFilterField_Bean> MainTableFilterFields = gson.fromJson(offlineDataSync.getFilterFields(), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                                                }.getType());
                                                for (QueryFilterField_Bean filterValues : MainTableFilterFields) {
                                                    if (!filterValues.isField_IsDeleted()) {
                                                        JSONObject jsonObjectMapExistingFilters = new JSONObject();
//                                                String fieldValue = ImproveHelper.getValueFromGlobalObject(context, filterValues.getField_Global_Value());
                                                        String fieldValue = getValueFromOfflineJson(offlineDataSync, filterValues.getField_Name(), "Filter", stringListSubmit, filterValues.getField_Global_Value());
                                                        jsonObjectMapExistingFilters.put("ColumnName", filterValues.getField_Name());
                                                        jsonObjectMapExistingFilters.put("Operator", getOperatorSymbol(filterValues.getField_Operator()));
                                                        jsonObjectMapExistingFilters.put("Value", fieldValue);
                                                        jsonObjectMapExistingFilters.put("Condition", filterValues.getField_and_or());
                                                        jsonObjectMapExistingFilters.put("ColumnType", "Others");
                                                        jsonObjectMapExistingFilters.put("NearBy", "");
                                                        jsonObjectMapExistingFilters.put("NoOfRec", "");
                                                        jsonObjectMapExistingFilters.put("CurrentGPS", "");
                                                        jsonArrayFilters.put(jsonObjectMapExistingFilters);
                                                    }
                                                }
                                            } else if (offlineDataSync.getMapExistingType().equalsIgnoreCase("Insert / Update")) {
                                                List<QueryFilterField_Bean> MainTableUpdateFields = gson.fromJson(offlineDataSync.getUpdateFields(), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                                                }.getType());
                                                for (QueryFilterField_Bean setColumns : MainTableUpdateFields) {
                                                    if (!setColumns.isField_IsDeleted()) {
//                                                    String fieldValue = ImproveHelper.getValueFromGlobalObject(context, setColumns.getField_Global_Value());
                                                        String fieldValue = getValueFromOfflineJson(offlineDataSync, setColumns.getField_Name(), "Update", stringListSubmit, setColumns.getField_Global_Value());
                                                        jsonObjectMapExistingSetColumns.put(setColumns.getField_Name(), fieldValue);
                                                    }
                                                }
                                                List<QueryFilterField_Bean> MainTableFilterFields = gson.fromJson(offlineDataSync.getFilterFields(), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                                                }.getType());
                                                for (QueryFilterField_Bean filterValues : MainTableFilterFields) {
                                                    if (!filterValues.isField_IsDeleted()) {
                                                        JSONObject jsonObjectMapExistingFilters = new JSONObject();
//                                                    String fieldValue = ImproveHelper.getValueFromGlobalObject(context, filterValues.getField_Global_Value());
                                                        String fieldValue = getValueFromOfflineJson(offlineDataSync, filterValues.getField_Name(), "Filter", stringListSubmit, filterValues.getField_Global_Value());
                                                        jsonObjectMapExistingFilters.put("ColumnName", filterValues.getField_Name());
                                                        jsonObjectMapExistingFilters.put("Operator", getOperatorSymbol(filterValues.getField_Operator()));
                                                        jsonObjectMapExistingFilters.put("Value", fieldValue);
                                                        jsonObjectMapExistingFilters.put("Condition", filterValues.getField_and_or());
                                                        jsonObjectMapExistingFilters.put("ColumnType", "Others");
                                                        jsonObjectMapExistingFilters.put("NearBy", "");
                                                        jsonObjectMapExistingFilters.put("NoOfRec", "");
                                                        jsonObjectMapExistingFilters.put("CurrentGPS", "");
                                                        jsonArrayFilters.put(jsonObjectMapExistingFilters);
                                                    }
                                                }
                                                List<QueryFilterField_Bean> MainTableInsertFields = gson.fromJson(offlineDataSync.getInsertFields(), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                                                }.getType());
                                                for (QueryFilterField_Bean insertFields : MainTableInsertFields) {
//                                            String fieldValue = ImproveHelper.getValueFromGlobalObject(context, insertFields.getField_Global_Value());
                                                    String fieldValue = getValueFromOfflineJson(offlineDataSync, insertFields.getField_Name(), "Insert", stringListSubmit, insertFields.getField_Global_Value());
                                                    jsonObjectMapExistingInsert.put(insertFields.getField_Name(), fieldValue);
                                                }
                                            }
                                        }


                                        mainObject.put("tableSettingsType", offlineDataSync.getTableSettingsType());

                                        if (offlineDataSync.getTableSettingsType().equalsIgnoreCase("Create New Table")) {
                                            array.put(jsonObject);
                                            mainObject.put("TypeofSubmission", "");
                                            mainObject.put("DataFields", array);
                                            mainObject.put("insertColumns", new JSONArray());
                                            mainObject.put("UpdateColumns", new JSONArray());

                                        } else if (offlineDataSync.getTableSettingsType().equalsIgnoreCase("Map existing table")) {
                                            mainObject.put("TypeofSubmission", offlineDataSync.getMapExistingType());
                                            mainObject.put("DataFields", new JSONArray());
                                            if (offlineDataSync.getMapExistingType().equalsIgnoreCase("Insert")) {
//
                                                JSONArray insertJsonArray = new JSONArray();
                                                insertJsonArray.put(jsonObjectMapExistingInsert);
                                                mainObject.put("insertColumns", insertJsonArray);
                                                mainObject.put("UpdateColumns", new JSONArray());
                                                Log.d(TAG, "run: " + "Insert");
                                            } else if (offlineDataSync.getMapExistingType().equalsIgnoreCase("Update")) {
                                                mainObject.put("insertColumns", new JSONArray());
//
                                                JSONArray updateJsonArray = new JSONArray();
                                                JSONObject updateColumnsObject = new JSONObject();
                                                updateColumnsObject.put("setColumns", jsonObjectMapExistingSetColumns);
                                                updateColumnsObject.put("filterColumns", jsonArrayFilters);
                                                updateJsonArray.put(updateColumnsObject);
                                                mainObject.put("UpdateColumns", updateJsonArray);
                                                Log.d(TAG, "run: " + "UpdateInsert");
                                            } else if (offlineDataSync.getMapExistingType().equalsIgnoreCase("Insert / Update")) {

                                                JSONArray insertJsonArray = new JSONArray();
                                                insertJsonArray.put(jsonObjectMapExistingInsert);
                                                mainObject.put("insertColumns", insertJsonArray);
//
                                                JSONArray updateJsonArray = new JSONArray();
                                                JSONObject updateColumnsObject = new JSONObject();
                                                updateColumnsObject.put("setColumns", jsonObjectMapExistingSetColumns);
                                                updateColumnsObject.put("filterColumns", jsonArrayFilters);
                                                updateJsonArray.put(updateColumnsObject);
                                                mainObject.put("UpdateColumns", updateJsonArray);
                                                Log.d(TAG, "run: " + "UpdateInsert");
                                            }
                                        }
                                    } else {
                                        mainObject.put("tableSettingsType", "Create New Table");
                                        array.put(jsonObject);
                                        mainObject.put("TypeofSubmission", "");
                                        mainObject.put("DataFields", array);
                                        mainObject.put("insertColumns", new JSONArray());

                                        mainObject.put("UpdateColumns", new JSONArray());
                                    }
//                                }

                                    List<SubformMapExistingData> subFormMapExistingData = new ArrayList<>();
                                    subFormMapExistingData = getSubFormMapExistingData(offlineDataSync.getOffline_json());

                                    //Map existing subform
                                    for (int i = 0; i < subFormMapExistingData.size(); i++) {
                                        if (subFormMapExistingData.get(i).getTableSettingsType() != null) {
                                            if (subFormMapExistingData.get(i).getTableSettingsType().equalsIgnoreCase("Create New Table")) {
                                                JSONObject subformJson = new JSONObject();
//                                        String subFormName = subFormStringList.get(i).get(0).get(0).split("\\|")[1];
                                                String subFormName = subFormStringList.get(i).get(0).get(0).split("\\|")[0];
                                                String subFormName_New = subFormName.substring(0, subFormName.length() - 2);
                                                subformJson.put("SubFormName", subFormName_New);

                                                subformJson.put("TableName", subFormMapExistingData.get(i).getExistingTableName());
                                                JSONArray subFormdataFieldsArray = new JSONArray();
                                                for (int j = 0; j < subFormStringList.get(i).size(); j++) {
                                                    JSONObject row = new JSONObject();
                                                    for (int k = 1; k < subFormStringList.get(i).get(j).size(); k++) {
                                                        String[] splittedString = subFormStringList.get(i).get(j).get(k).split("\\|");
                                                        if (splittedString.length > 1) {
                                                            String columnName = splittedString[0];
                                                            String columnValue = splittedString[1];
                                                            row.put(columnName, columnValue);
                                                        } else {
                                                            String columnName = splittedString[0];
                                                            String columnValue = "";
                                                            row.put(columnName, columnValue);
                                                        }

                                                    }
                                                    subFormdataFieldsArray.put(row);
                                                    subformJson.put("tableSettingsType", subFormMapExistingData.get(i).getTableSettingsType());
                                                    subformJson.put("TypeofSubmission", "");
                                                    subformJson.put("DataFields", subFormdataFieldsArray);
                                                    subformJson.put("insertColumns", new JSONArray());
                                                    subformJson.put("UpdateColumns", new JSONArray());
                                                    arraySubform.put(subformJson);
                                                }
                                                mainObject.put("SubFormDataFields", arraySubform);

                                            } else {

                                                JSONObject subformJson = new JSONObject();
                                                subformJson.put("SubFormName", subFormMapExistingData.get(i).getSubformName());
                                                subformJson.put("TableName", subFormMapExistingData.get(i).getExistingTableName());
                                                subformJson.put("tableSettingsType", subFormMapExistingData.get(i).getTableSettingsType());
                                                subformJson.put("TypeofSubmission", subFormMapExistingData.get(i).getMapExistingType());

                                                if (subFormMapExistingData.get(i).getMapExistingType().equalsIgnoreCase("Insert")) {
                                                    JSONArray insertJsonArray = new JSONArray();
                                                    for (int j = 0; j < subFormStringList.get(i).size(); j++) {
                                                        JSONObject jsonObjectMapExistingInsertsf = new JSONObject();
                                                        JSONObject jsonObjectMapExistingSetColumnssf = new JSONObject();
                                                        JSONArray jsonArrayFilterssf = new JSONArray();


                                                        for (QueryFilterField_Bean insertFields : subFormMapExistingData.get(i).getSubFormInsertFields()) {
//                                                        String fieldValue = ImproveHelper.getValueFromGlobalObject(context, insertFields.getField_Global_Value());
                                                            String fieldValue = getSubFormValueFromOfflineJson(offlineDataSync, insertFields.getField_Name(), "Insert", subFormStringList, insertFields.getField_Global_Value(), i);
                                                            jsonObjectMapExistingInsertsf.put(insertFields.getField_Name(), fieldValue);
                                                            Log.d(TAG, "senddata1: " + fieldValue);
                                                        }

                                                        subformJson.put("DataFields", new JSONArray());
                                                        insertJsonArray.put(jsonObjectMapExistingInsertsf);
                                                        subformJson.put("insertColumns", insertJsonArray);
                                                        subformJson.put("UpdateColumns", new JSONArray());

                                                    }
                                                    arraySubform.put(subformJson);

                                                    mainObject.put("SubFormDataFields", arraySubform);
                                                } else if (subFormMapExistingData.get(i).getMapExistingType().equalsIgnoreCase("Update")) {

                                                    JSONArray updateJsonArray = new JSONArray();

                                                    for (int j = 0; j < subFormStringList.get(i).size(); j++) {

                                                        JSONObject jsonObjectMapExistingSetColumnssf = new JSONObject();
                                                        JSONArray jsonArrayFilterssf = new JSONArray();
                                                        for (QueryFilterField_Bean setColumns : subFormMapExistingData.get(i).getSubFormUpdateFields()) {
                                                            if (!setColumns.isField_IsDeleted()) {
//                                                            String fieldValue = ImproveHelper.getValueFromGlobalObject(context, setColumns.getField_Global_Value());
                                                                String fieldValue = getSubFormValueFromOfflineJson(offlineDataSync, setColumns.getField_Name(), "Update", subFormStringList, setColumns.getField_Global_Value(), i);
                                                                jsonObjectMapExistingSetColumnssf.put(setColumns.getField_Name(), fieldValue);
                                                            }
                                                        }
                                                        for (QueryFilterField_Bean filterValues : subFormMapExistingData.get(i).getSubFormFilterFields()) {
                                                            if (!filterValues.isField_IsDeleted()) {
                                                                JSONObject jsonObjectMapExistingFilters = new JSONObject();
//                                                            String fieldValue = ImproveHelper.getValueFromGlobalObject(context, filterValues.getField_Global_Value());
                                                                String fieldValue = getSubFormValueFromOfflineJson(offlineDataSync, filterValues.getField_Name(), "Filter", subFormStringList, filterValues.getField_Global_Value(), i);
                                                                jsonObjectMapExistingFilters.put("ColumnName", filterValues.getField_Name());
                                                                jsonObjectMapExistingFilters.put("Operator", getOperatorSymbol(filterValues.getField_Operator()));
                                                                jsonObjectMapExistingFilters.put("Value", fieldValue);
                                                                jsonObjectMapExistingFilters.put("Condition", filterValues.getField_and_or());
                                                                jsonObjectMapExistingFilters.put("ColumnType", "Others");
                                                                jsonObjectMapExistingFilters.put("NearBy", "");
                                                                jsonObjectMapExistingFilters.put("NoOfRec", "");
                                                                jsonObjectMapExistingFilters.put("CurrentGPS", "");
                                                                jsonArrayFilterssf.put(jsonObjectMapExistingFilters);
                                                            }
                                                        }


                                                        subformJson.put("DataFields", new JSONArray());
                                                        subformJson.put("insertColumns", new JSONArray());

                                                        JSONObject updateColumnsObject = new JSONObject();
                                                        updateColumnsObject.put("setColumns", jsonObjectMapExistingSetColumnssf);
                                                        updateColumnsObject.put("filterColumns", jsonArrayFilterssf);
                                                        updateJsonArray.put(updateColumnsObject);
                                                        subformJson.put("UpdateColumns", updateJsonArray);
                                                    }
                                                    arraySubform.put(subformJson);
                                                    mainObject.put("SubFormDataFields", arraySubform);
                                                } else if (subFormMapExistingData.get(i).getMapExistingType().equalsIgnoreCase("Insert / Update")) {
                                                    JSONArray insertJsonArray = new JSONArray();
                                                    JSONArray updateJsonArray = new JSONArray();
                                                    for (int j = 0; j < subFormStringList.get(i).size(); j++) {
                                                        JSONObject jsonObjectMapExistingInsertsf = new JSONObject();
                                                        JSONObject jsonObjectMapExistingSetColumnssf = new JSONObject();
                                                        JSONArray jsonArrayFilterssf = new JSONArray();
                                                        for (QueryFilterField_Bean setColumns : subFormMapExistingData.get(i).getSubFormUpdateFields()) {
                                                            if (!setColumns.isField_IsDeleted()) {
//                                                            String fieldValue = ImproveHelper.getValueFromGlobalObject(context, setColumns.getField_Global_Value());
                                                                String fieldValue = getSubFormValueFromOfflineJson(offlineDataSync, setColumns.getField_Name(), "Update", subFormStringList, setColumns.getField_Global_Value(), i);
                                                                jsonObjectMapExistingSetColumnssf.put(setColumns.getField_Name(), fieldValue);
                                                            }
                                                        }
                                                        for (QueryFilterField_Bean filterValues : subFormMapExistingData.get(i).getSubFormFilterFields()) {
                                                            if (!filterValues.isField_IsDeleted()) {
                                                                JSONObject jsonObjectMapExistingFilters = new JSONObject();
//                                                            String fieldValue = ImproveHelper.getValueFromGlobalObject(context, filterValues.getField_Global_Value());
                                                                String fieldValue = getSubFormValueFromOfflineJson(offlineDataSync, filterValues.getField_Name(), "Filter", subFormStringList, filterValues.getField_Global_Value(), i);
                                                                jsonObjectMapExistingFilters.put("ColumnName", filterValues.getField_Name());
                                                                jsonObjectMapExistingFilters.put("Operator", getOperatorSymbol(filterValues.getField_Operator()));
                                                                jsonObjectMapExistingFilters.put("Value", fieldValue);
                                                                jsonObjectMapExistingFilters.put("Condition", filterValues.getField_and_or());
                                                                jsonObjectMapExistingFilters.put("ColumnType", "Others");
                                                                jsonObjectMapExistingFilters.put("NearBy", "");
                                                                jsonObjectMapExistingFilters.put("NoOfRec", "");
                                                                jsonObjectMapExistingFilters.put("CurrentGPS", "");
                                                                jsonArrayFilterssf.put(jsonObjectMapExistingFilters);
                                                            }
                                                        }

                                                        for (QueryFilterField_Bean insertFields : subFormMapExistingData.get(i).getSubFormInsertFields()) {
//                                                        String fieldValue = ImproveHelper.getValueFromGlobalObject(context, insertFields.getField_Global_Value());
                                                            String fieldValue = getSubFormValueFromOfflineJson(offlineDataSync, insertFields.getField_Name(), "Insert", subFormStringList, insertFields.getField_Global_Value(), i);
                                                            jsonObjectMapExistingInsertsf.put(insertFields.getField_Name(), fieldValue);
                                                        }


                                                        subformJson.put("DataFields", new JSONArray());

                                                        insertJsonArray.put(jsonObjectMapExistingInsertsf);
                                                        subformJson.put("insertColumns", insertJsonArray);

                                                        JSONObject updateColumnsObject = new JSONObject();
                                                        updateColumnsObject.put("setColumns", jsonObjectMapExistingSetColumnssf);
                                                        updateColumnsObject.put("filterColumns", jsonArrayFilterssf);
                                                        updateJsonArray.put(updateColumnsObject);
                                                        subformJson.put("UpdateColumns", updateJsonArray);


                                                    }
                                                    arraySubform.put(subformJson);
                                                    mainObject.put("SubFormDataFields", arraySubform);
                                                }
                                            }
                                        } else {
                                            JSONObject subformJson = new JSONObject();
//                                        String subFormName = subFormStringList.get(i).get(0).get(0).split("\\|")[1];
                                            String subFormName = subFormStringList.get(i).get(0).get(0).split("\\|")[0];
                                            String subFormName_New = subFormName.substring(0, subFormName.length() - 2);
                                            subformJson.put("SubFormName", subFormName_New);

                                            subformJson.put("TableName", strCreatedBy + "_" + ImproveHelper.getTableNameWithOutSpace(strAppName) + "_" + subFormName_New);
                                            JSONArray subFormdataFieldsArray = new JSONArray();
                                            for (int j = 0; j < subFormStringList.get(i).size(); j++) {
                                                JSONObject row = new JSONObject();
                                                for (int k = 1; k < subFormStringList.get(i).get(j).size(); k++) {
                                                    String[] splittedString = subFormStringList.get(i).get(j).get(k).split("\\|");
                                                    if (splittedString.length > 1) {
                                                        String columnName = splittedString[0];
                                                        String columnValue = splittedString[1];
                                                        row.put(columnName, columnValue);
                                                    } else {
                                                        String columnName = splittedString[0];
                                                        String columnValue = "";
                                                        row.put(columnName, columnValue);
                                                    }

                                                }
                                                subFormdataFieldsArray.put(row);
                                                subformJson.put("tableSettingsType", subFormMapExistingData.get(i).getTableSettingsType());
                                                subformJson.put("TypeofSubmission", "");
                                                subformJson.put("DataFields", subFormdataFieldsArray);
                                                subformJson.put("insertColumns", new JSONArray());
                                                subformJson.put("UpdateColumns", new JSONArray());
                                                arraySubform.put(subformJson);
                                            }
                                            mainObject.put("SubFormDataFields", arraySubform);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace(); }*/
                                try {
                                    mainObject = new JSONObject(offlineDataSync.getOffline_json());
                                    mainObject.put("sno", offlineDataSync.getSno());
                                    mainObject.put("OrgId", offlineDataSync.getOrgId());
                                    mainObject.put("PageName", offlineDataSync.getApp_name());
                                    mainObject.put("CreatedUserID", offlineDataSync.getCreatedUserID());
                                    mainObject.put("SubmittedUserID", offlineDataSync.getSubmittedUserID());
                                    mainObject.put("SubmittedUserPostID", strPostId);
                                    mainObject.put("PostId", strPostId);
                                    mainObject.put("DistributionID", offlineDataSync.getDistributionID());
                                    mainObject.put("IMEI", offlineDataSync.getIMEI());
                                    mainObject.put("OperationType", "");
                                    mainObject.put("TransID", "");

                                    if (offlineDataSync.getTableSettingsType() == null) {
                                        mainObject.put("TableName", offlineDataSync.getCreatedUserID() + "_" + ImproveHelper.getTableNameWithOutSpace(offlineDataSync.getApp_name()));
                                    } else if (offlineDataSync.getTableSettingsType().equalsIgnoreCase("Create New Table")) {
                                        mainObject.put("TableName", offlineDataSync.getCreatedUserID() + "_" + ImproveHelper.getTableNameWithOutSpace(offlineDataSync.getApp_name()));
                                    } else if (offlineDataSync.getTableSettingsType().equalsIgnoreCase("Map existing table")) {
                                        mainObject.put("TableName", offlineDataSync.getTableName());
                                    }

                                    mainObject.put("TypeofSubmission", "");
                                    mainObject.put("insertColumns", new JSONArray());
                                    mainObject.put("UpdateColumns", new JSONArray());
                                    mainObject.put("tableSettingsType", "Create New Table");

                             /*   int maxSize = 0;
                                    if (subFormStringList.size() > 0) {
                                        for (int i = 0; i < subFormStringList.size(); i++) {

                                            if (subFormStringList.get(i).size() > maxSize) {

                                                maxSize = subFormStringList.get(i).size();
                                            }

                                        }
                                    }

                                    if (maxSize == 0) {
                                        maxSize = 1;
                                    }*/

                                    JSONArray array = new JSONArray();
                                    JSONArray arraySubform = new JSONArray();
//                                for (int i = 0; i < maxSize; i++) {

                                    JSONObject jsonObject = new JSONObject();
                                    if (stringListSubmit.size() > 0) {
                                        for (int j = 0; j < stringListSubmit.size(); j++) {
                                            String[] strData = stringListSubmit.get(j).split("\\|");
                                            if (strData.length > 1) {
                                                jsonObject.put(strData[0], strData[1]);
                                            } else {
                                                jsonObject.put(strData[0], "");
                                            }
                                        }
                                    }
                                    //map existing
                                    JSONObject jsonObjectMapExistingInsert = new JSONObject();
                                    JSONObject jsonObjectMapExistingSetColumns = new JSONObject();
                                    JSONArray jsonArrayFilters = new JSONArray();
                                    if (offlineDataSync.getTableSettingsType() != null) {
                                        if (offlineDataSync.getTableSettingsType().equalsIgnoreCase("Map existing table")) {
                                            if (offlineDataSync.getMapExistingType().equalsIgnoreCase("Insert")) {
                                                List<QueryFilterField_Bean> MainTableInsertFields = gson.fromJson(offlineDataSync.getInsertFields(), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                                                }.getType());
                                                for (QueryFilterField_Bean insertFields : MainTableInsertFields) {
//                                            String fieldValue = ImproveHelper.getValueFromGlobalObject(context, insertFields.getField_Global_Value());
                                                    String fieldValue = getValueFromOfflineJson(offlineDataSync, insertFields.getField_Name(), "Insert", stringListSubmit, insertFields.getField_Global_Value());
                                                    jsonObjectMapExistingInsert.put(insertFields.getField_Name(), fieldValue);
                                                }
                                            } else if (offlineDataSync.getMapExistingType().equalsIgnoreCase("Update")) {
                                                List<QueryFilterField_Bean> MainTableUpdateFields = gson.fromJson(offlineDataSync.getUpdateFields(), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                                                }.getType());
                                                for (QueryFilterField_Bean setColumns : MainTableUpdateFields) {
                                                    if (!setColumns.isField_IsDeleted()) {
//                                                String fieldValue = ImproveHelper.getValueFromGlobalObject(context, setColumns.getField_Global_Value());
                                                        String fieldValue = getValueFromOfflineJson(offlineDataSync, setColumns.getField_Name(), "Update", stringListSubmit, setColumns.getField_Global_Value());
                                                        jsonObjectMapExistingSetColumns.put(setColumns.getField_Name(), fieldValue);
                                                    }
                                                }
                                                List<QueryFilterField_Bean> MainTableFilterFields = gson.fromJson(offlineDataSync.getFilterFields(), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                                                }.getType());
                                                for (QueryFilterField_Bean filterValues : MainTableFilterFields) {
                                                    if (!filterValues.isField_IsDeleted()) {
                                                        JSONObject jsonObjectMapExistingFilters = new JSONObject();
//                                                String fieldValue = ImproveHelper.getValueFromGlobalObject(context, filterValues.getField_Global_Value());
                                                        String fieldValue = getValueFromOfflineJson(offlineDataSync, filterValues.getField_Name(), "Filter", stringListSubmit, filterValues.getField_Global_Value());
                                                        jsonObjectMapExistingFilters.put("ColumnName", filterValues.getField_Name());
                                                        jsonObjectMapExistingFilters.put("Operator", getOperatorSymbol(filterValues.getField_Operator()));
                                                        jsonObjectMapExistingFilters.put("Value", fieldValue);
                                                        jsonObjectMapExistingFilters.put("Condition", filterValues.getField_and_or());
                                                        jsonObjectMapExistingFilters.put("ColumnType", "Others");
                                                        jsonObjectMapExistingFilters.put("NearBy", "");
                                                        jsonObjectMapExistingFilters.put("NoOfRec", "");
                                                        jsonObjectMapExistingFilters.put("CurrentGPS", "");
                                                        jsonArrayFilters.put(jsonObjectMapExistingFilters);
                                                    }
                                                }
                                            } else if (offlineDataSync.getMapExistingType().equalsIgnoreCase("Insert / Update")) {
                                                List<QueryFilterField_Bean> MainTableUpdateFields = gson.fromJson(offlineDataSync.getUpdateFields(), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                                                }.getType());
                                                for (QueryFilterField_Bean setColumns : MainTableUpdateFields) {
                                                    if (!setColumns.isField_IsDeleted()) {
//                                                    String fieldValue = ImproveHelper.getValueFromGlobalObject(context, setColumns.getField_Global_Value());
                                                        String fieldValue = getValueFromOfflineJson(offlineDataSync, setColumns.getField_Name(), "Update", stringListSubmit, setColumns.getField_Global_Value());
                                                        jsonObjectMapExistingSetColumns.put(setColumns.getField_Name(), fieldValue);
                                                    }
                                                }
                                                List<QueryFilterField_Bean> MainTableFilterFields = gson.fromJson(offlineDataSync.getFilterFields(), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                                                }.getType());
                                                for (QueryFilterField_Bean filterValues : MainTableFilterFields) {
                                                    if (!filterValues.isField_IsDeleted()) {
                                                        JSONObject jsonObjectMapExistingFilters = new JSONObject();
//                                                    String fieldValue = ImproveHelper.getValueFromGlobalObject(context, filterValues.getField_Global_Value());
                                                        String fieldValue = getValueFromOfflineJson(offlineDataSync, filterValues.getField_Name(), "Filter", stringListSubmit, filterValues.getField_Global_Value());
                                                        jsonObjectMapExistingFilters.put("ColumnName", filterValues.getField_Name());
                                                        jsonObjectMapExistingFilters.put("Operator", getOperatorSymbol(filterValues.getField_Operator()));
                                                        jsonObjectMapExistingFilters.put("Value", fieldValue);
                                                        jsonObjectMapExistingFilters.put("Condition", filterValues.getField_and_or());
                                                        jsonObjectMapExistingFilters.put("ColumnType", "Others");
                                                        jsonObjectMapExistingFilters.put("NearBy", "");
                                                        jsonObjectMapExistingFilters.put("NoOfRec", "");
                                                        jsonObjectMapExistingFilters.put("CurrentGPS", "");
                                                        jsonArrayFilters.put(jsonObjectMapExistingFilters);
                                                    }
                                                }
                                                List<QueryFilterField_Bean> MainTableInsertFields = gson.fromJson(offlineDataSync.getInsertFields(), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                                                }.getType());
                                                for (QueryFilterField_Bean insertFields : MainTableInsertFields) {
//                                            String fieldValue = ImproveHelper.getValueFromGlobalObject(context, insertFields.getField_Global_Value());
                                                    String fieldValue = getValueFromOfflineJson(offlineDataSync, insertFields.getField_Name(), "Insert", stringListSubmit, insertFields.getField_Global_Value());
                                                    jsonObjectMapExistingInsert.put(insertFields.getField_Name(), fieldValue);
                                                }
                                            }
                                        }


                                        mainObject.put("tableSettingsType", offlineDataSync.getTableSettingsType());

                                        if (offlineDataSync.getTableSettingsType().equalsIgnoreCase("Create New Table")) {
                                            array.put(jsonObject);
                                            mainObject.put("TypeofSubmission", "");
                                            mainObject.put("DataFields", array);
                                            mainObject.put("insertColumns", new JSONArray());
                                            mainObject.put("UpdateColumns", new JSONArray());

                                        } else if (offlineDataSync.getTableSettingsType().equalsIgnoreCase("Map existing table")) {
                                            mainObject.put("TypeofSubmission", offlineDataSync.getMapExistingType());
                                            mainObject.put("DataFields", new JSONArray());
                                            if (offlineDataSync.getMapExistingType().equalsIgnoreCase("Insert")) {
//
                                                JSONArray insertJsonArray = new JSONArray();
                                                insertJsonArray.put(jsonObjectMapExistingInsert);
                                                mainObject.put("insertColumns", insertJsonArray);
                                                mainObject.put("UpdateColumns", new JSONArray());
                                                Log.d(TAG, "run: " + "Insert");
                                            } else if (offlineDataSync.getMapExistingType().equalsIgnoreCase("Update")) {
                                                mainObject.put("insertColumns", new JSONArray());
//
                                                JSONArray updateJsonArray = new JSONArray();
                                                JSONObject updateColumnsObject = new JSONObject();
                                                updateColumnsObject.put("setColumns", jsonObjectMapExistingSetColumns);
                                                updateColumnsObject.put("filterColumns", jsonArrayFilters);
                                                updateJsonArray.put(updateColumnsObject);
                                                mainObject.put("UpdateColumns", updateJsonArray);
                                                Log.d(TAG, "run: " + "UpdateInsert");
                                            } else if (offlineDataSync.getMapExistingType().equalsIgnoreCase("Insert / Update")) {

                                                JSONArray insertJsonArray = new JSONArray();
                                                insertJsonArray.put(jsonObjectMapExistingInsert);
                                                mainObject.put("insertColumns", insertJsonArray);
//
                                                JSONArray updateJsonArray = new JSONArray();
                                                JSONObject updateColumnsObject = new JSONObject();
                                                updateColumnsObject.put("setColumns", jsonObjectMapExistingSetColumns);
                                                updateColumnsObject.put("filterColumns", jsonArrayFilters);
                                                updateJsonArray.put(updateColumnsObject);
                                                mainObject.put("UpdateColumns", updateJsonArray);
                                                Log.d(TAG, "run: " + "UpdateInsert");
                                            }
                                        }
                                    } else {
                                        mainObject.put("tableSettingsType", "Create New Table");
                                        array.put(jsonObject);
                                        mainObject.put("TypeofSubmission", "");
                                        mainObject.put("DataFields", array);
                                        mainObject.put("insertColumns", new JSONArray());

                                        mainObject.put("UpdateColumns", new JSONArray());
                                    }
//                                }

                                    List<SubformMapExistingData> subFormMapExistingData = new ArrayList<>();
                                    subFormMapExistingData = getSubFormMapExistingData(offlineDataSync.getOffline_json());

                                    //Map existing subform
                                    for (int i = 0; i < subFormMapExistingData.size(); i++) {
                                        if (subFormMapExistingData.get(i).getTableSettingsType() != null) {
                                            if (subFormMapExistingData.get(i).getTableSettingsType().equalsIgnoreCase("Create New Table")) {
                                                JSONObject subformJson = new JSONObject();
//                                        String subFormName = subFormStringList.get(i).get(0).get(0).split("\\|")[1];
                                                String subFormName = subFormStringList.get(i).get(0).get(0).split("\\|")[0];
                                                String subFormName_New = subFormName.substring(0, subFormName.length() - 2);
                                                subformJson.put("SubFormName", subFormName_New);
                                                subformJson.put("TableName", subFormMapExistingData.get(i).getExistingTableName());
                                                insertAutoNumberValueinSubformObject(offlineDataSync,subformJson,subFormName_New);
                                                insertCheckListValueinSubformObject(offlineDataSync,subformJson,subFormName_New);
                                                JSONArray subFormdataFieldsArray = new JSONArray();
                                                for (int j = 0; j < subFormStringList.get(i).size(); j++) {
                                                    JSONObject row = new JSONObject();
                                                    for (int k = 1; k < subFormStringList.get(i).get(j).size(); k++) {
                                                        String[] splittedString = subFormStringList.get(i).get(j).get(k).split("\\|");
                                                        if (splittedString.length > 1) {
                                                            String columnName = splittedString[0];
                                                            String columnValue = splittedString[1];
                                                            row.put(columnName, columnValue);
                                                        } else {
                                                            String columnName = splittedString[0];
                                                            String columnValue = "";
                                                            row.put(columnName, columnValue);
                                                        }

                                                    }
                                                    subFormdataFieldsArray.put(row);
                                                    subformJson.put("tableSettingsType", subFormMapExistingData.get(i).getTableSettingsType());
                                                    subformJson.put("TypeofSubmission", "");
                                                    subformJson.put("DataFields", subFormdataFieldsArray);
                                                    subformJson.put("insertColumns", new JSONArray());
                                                    subformJson.put("UpdateColumns", new JSONArray());
                                                    arraySubform.put(subformJson);
                                                }
                                                mainObject.put("SubFormDataFields", arraySubform);

                                            } else {

                                                JSONObject subformJson = new JSONObject();
                                                subformJson.put("SubFormName", subFormMapExistingData.get(i).getSubformName());
                                                subformJson.put("TableName", subFormMapExistingData.get(i).getExistingTableName());
                                                insertAutoNumberValueinSubformObject(offlineDataSync,subformJson,subFormMapExistingData.get(i).getSubformName());
                                                insertCheckListValueinSubformObject(offlineDataSync,subformJson,subFormMapExistingData.get(i).getSubformName());
                                                subformJson.put("tableSettingsType", subFormMapExistingData.get(i).getTableSettingsType());
                                                subformJson.put("TypeofSubmission", subFormMapExistingData.get(i).getMapExistingType());

                                                if (subFormMapExistingData.get(i).getMapExistingType().equalsIgnoreCase("Insert")) {
                                                    JSONArray insertJsonArray = new JSONArray();
                                                    for (int j = 0; j < subFormStringList.get(i).size(); j++) {
                                                        JSONObject jsonObjectMapExistingInsertsf = new JSONObject();
                                                         for (QueryFilterField_Bean insertFields : subFormMapExistingData.get(i).getSubFormInsertFields()) {
                                                            String fieldValue = getSubFormValueFromOfflineJson(offlineDataSync, insertFields.getField_Name(), "Insert", subFormStringList, insertFields.getField_Global_Value(), i);
                                                            jsonObjectMapExistingInsertsf.put(insertFields.getField_Name(), fieldValue);
                                                            Log.d(TAG, "senddata1: " + fieldValue);
                                                        }

                                                        subformJson.put("DataFields", new JSONArray());
                                                        insertJsonArray.put(jsonObjectMapExistingInsertsf);
                                                        subformJson.put("insertColumns", insertJsonArray);
                                                        subformJson.put("UpdateColumns", new JSONArray());

                                                    }
                                                    arraySubform.put(subformJson);

                                                    mainObject.put("SubFormDataFields", arraySubform);
                                                } else if (subFormMapExistingData.get(i).getMapExistingType().equalsIgnoreCase("Update")) {

                                                    JSONArray updateJsonArray = new JSONArray();

                                                    for (int j = 0; j < subFormStringList.get(i).size(); j++) {

                                                        JSONObject jsonObjectMapExistingSetColumnssf = new JSONObject();
                                                        JSONArray jsonArrayFilterssf = new JSONArray();
                                                        for (QueryFilterField_Bean setColumns : subFormMapExistingData.get(i).getSubFormUpdateFields()) {
                                                            if (!setColumns.isField_IsDeleted()) {
                                                                String fieldValue = getSubFormValueFromOfflineJson(offlineDataSync, setColumns.getField_Name(), "Update", subFormStringList, setColumns.getField_Global_Value(), i);
                                                                jsonObjectMapExistingSetColumnssf.put(setColumns.getField_Name(), fieldValue);
                                                            }
                                                        }
                                                        for (QueryFilterField_Bean filterValues : subFormMapExistingData.get(i).getSubFormFilterFields()) {
                                                            if (!filterValues.isField_IsDeleted()) {
                                                                JSONObject jsonObjectMapExistingFilters = new JSONObject();
                                                                String fieldValue = getSubFormValueFromOfflineJson(offlineDataSync, filterValues.getField_Name(), "Filter", subFormStringList, filterValues.getField_Global_Value(), i);
                                                                jsonObjectMapExistingFilters.put("ColumnName", filterValues.getField_Name());
                                                                jsonObjectMapExistingFilters.put("Operator", getOperatorSymbol(filterValues.getField_Operator()));
                                                                jsonObjectMapExistingFilters.put("Value", fieldValue);
                                                                jsonObjectMapExistingFilters.put("Condition", filterValues.getField_and_or());
                                                                jsonObjectMapExistingFilters.put("ColumnType", "Others");
                                                                jsonObjectMapExistingFilters.put("NearBy", "");
                                                                jsonObjectMapExistingFilters.put("NoOfRec", "");
                                                                jsonObjectMapExistingFilters.put("CurrentGPS", "");
                                                                jsonArrayFilterssf.put(jsonObjectMapExistingFilters);
                                                            }
                                                        }


                                                        subformJson.put("DataFields", new JSONArray());
                                                        subformJson.put("insertColumns", new JSONArray());

                                                        JSONObject updateColumnsObject = new JSONObject();
                                                        updateColumnsObject.put("setColumns", jsonObjectMapExistingSetColumnssf);
                                                        updateColumnsObject.put("filterColumns", jsonArrayFilterssf);
                                                        updateJsonArray.put(updateColumnsObject);
                                                        subformJson.put("UpdateColumns", updateJsonArray);
                                                    }
                                                    arraySubform.put(subformJson);
                                                    mainObject.put("SubFormDataFields", arraySubform);
                                                } else if (subFormMapExistingData.get(i).getMapExistingType().equalsIgnoreCase("Insert / Update")) {
                                                    JSONArray insertJsonArray = new JSONArray();
                                                    JSONArray updateJsonArray = new JSONArray();
                                                    for (int j = 0; j < subFormStringList.get(i).size(); j++) {
                                                        JSONObject jsonObjectMapExistingInsertsf = new JSONObject();
                                                        JSONObject jsonObjectMapExistingSetColumnssf = new JSONObject();
                                                        JSONArray jsonArrayFilterssf = new JSONArray();
                                                        for (QueryFilterField_Bean setColumns : subFormMapExistingData.get(i).getSubFormUpdateFields()) {
                                                            if (!setColumns.isField_IsDeleted()) {
                                                                String fieldValue = getSubFormValueFromOfflineJson(offlineDataSync, setColumns.getField_Name(), "Update", subFormStringList, setColumns.getField_Global_Value(), i);
                                                                jsonObjectMapExistingSetColumnssf.put(setColumns.getField_Name(), fieldValue);
                                                            }
                                                        }
                                                        for (QueryFilterField_Bean filterValues : subFormMapExistingData.get(i).getSubFormFilterFields()) {
                                                            if (!filterValues.isField_IsDeleted()) {
                                                                JSONObject jsonObjectMapExistingFilters = new JSONObject();
                                                                String fieldValue = getSubFormValueFromOfflineJson(offlineDataSync, filterValues.getField_Name(), "Filter", subFormStringList, filterValues.getField_Global_Value(), i);
                                                                jsonObjectMapExistingFilters.put("ColumnName", filterValues.getField_Name());
                                                                jsonObjectMapExistingFilters.put("Operator", getOperatorSymbol(filterValues.getField_Operator()));
                                                                jsonObjectMapExistingFilters.put("Value", fieldValue);
                                                                jsonObjectMapExistingFilters.put("Condition", filterValues.getField_and_or());
                                                                jsonObjectMapExistingFilters.put("ColumnType", "Others");
                                                                jsonObjectMapExistingFilters.put("NearBy", "");
                                                                jsonObjectMapExistingFilters.put("NoOfRec", "");
                                                                jsonObjectMapExistingFilters.put("CurrentGPS", "");
                                                                jsonArrayFilterssf.put(jsonObjectMapExistingFilters);
                                                            }
                                                        }

                                                        for (QueryFilterField_Bean insertFields : subFormMapExistingData.get(i).getSubFormInsertFields()) {
                                                            String fieldValue = getSubFormValueFromOfflineJson(offlineDataSync, insertFields.getField_Name(), "Insert", subFormStringList, insertFields.getField_Global_Value(), i);
                                                            jsonObjectMapExistingInsertsf.put(insertFields.getField_Name(), fieldValue);
                                                        }


                                                        subformJson.put("DataFields", new JSONArray());

                                                        insertJsonArray.put(jsonObjectMapExistingInsertsf);
                                                        subformJson.put("insertColumns", insertJsonArray);

                                                        JSONObject updateColumnsObject = new JSONObject();
                                                        updateColumnsObject.put("setColumns", jsonObjectMapExistingSetColumnssf);
                                                        updateColumnsObject.put("filterColumns", jsonArrayFilterssf);
                                                        updateJsonArray.put(updateColumnsObject);
                                                        subformJson.put("UpdateColumns", updateJsonArray);


                                                    }
                                                    arraySubform.put(subformJson);
                                                    mainObject.put("SubFormDataFields", arraySubform);
                                                }
                                            }
                                        } else {
                                            JSONObject subformJson = new JSONObject();
                                            String subFormName = subFormStringList.get(i).get(0).get(0).split("\\|")[0];
                                            String subFormName_New = subFormName.substring(0, subFormName.length() - 2);
                                            subformJson.put("SubFormName", subFormName_New);
                                            subformJson.put("TableName", strCreatedBy + "_" + ImproveHelper.getTableNameWithOutSpace(strAppName) + "_" + subFormName_New);
                                            JSONArray subFormdataFieldsArray = new JSONArray();
                                            for (int j = 0; j < subFormStringList.get(i).size(); j++) {
                                                JSONObject row = new JSONObject();
                                                for (int k = 1; k < subFormStringList.get(i).get(j).size(); k++) {
                                                    String[] splittedString = subFormStringList.get(i).get(j).get(k).split("\\|");
                                                    if (splittedString.length > 1) {
                                                        String columnName = splittedString[0];
                                                        String columnValue = splittedString[1];
                                                        row.put(columnName, columnValue);
                                                    } else {
                                                        String columnName = splittedString[0];
                                                        String columnValue = "";
                                                        row.put(columnName, columnValue);
                                                    }

                                                }
                                                subFormdataFieldsArray.put(row);
                                                subformJson.put("tableSettingsType", subFormMapExistingData.get(i).getTableSettingsType());
                                                subformJson.put("TypeofSubmission", "");
                                                subformJson.put("DataFields", subFormdataFieldsArray);
                                                subformJson.put("insertColumns", new JSONArray());
                                                subformJson.put("UpdateColumns", new JSONArray());
                                                arraySubform.put(subformJson);
                                            }
                                            mainObject.put("SubFormDataFields", arraySubform);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                if (mainObject != null) {
                                    sendDataToServer(mainObject);
                                }
                            }
                        });
                    }
                });


                t3 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (tempList != null && tempList.size() > 0) {

                                    int filesCount = tempList.size();
                                    int index = 0;


                                    sendSubFormFilesToServer(tempList.get(index), index, filesCount);


                                } else {
                                    t2.start();

                                    try {
                                        t2.join();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                });
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "sendDataMapExisting", e);
        }

    }

    private JSONArray getChecklistNames1(String checklistNames) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(checklistNames);
        }catch (Exception e){
            
        }
return  jsonArray;
    }



    private List<ControlObject> getSFTableSettingsData(String offline_json) {
        List<ControlObject> sfObjects = new ArrayList<>();
        return sfObjects;
    }


    private void sendData(final OfflineDataSync offlineDataSync) throws InterruptedException {
        try {
            if (isNetworkStatusAvialable(context)) {

                t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (stringListFiles != null && stringListFiles.size() > 0) {
                                    int filesCount = stringListFiles.size();
                                    for (int fileCount = 0; fileCount < stringListFiles.size(); fileCount++) {

                                        sendFilesToServer(stringListFiles.get(fileCount), fileCount, filesCount);

                                    }
                                } else {
                                    t3.start();
                                    try {
                                        t3.join();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
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

                                /*try {

                                    mainObject = new JSONObject();

                                    mainObject.put("sno", offlineDataSync.getSno());
                                    mainObject.put("OrgId", offlineDataSync.getOrgId());
                                    mainObject.put("PageName", offlineDataSync.getApp_name());
                                    mainObject.put("CreatedUserID", offlineDataSync.getCreatedUserID());
                                    mainObject.put("SubmittedUserID", offlineDataSync.getSubmittedUserID());
                                    mainObject.put("SubmittedUserPostID", strPostId);
                                    mainObject.put("PostId", strPostId);
                                    mainObject.put("DistributionID", offlineDataSync.getDistributionID());
                                    mainObject.put("IMEI", offlineDataSync.getIMEI());
                                    mainObject.put("OperationType", "");
                                    mainObject.put("TransID", "");
                                    mainObject.put("TableName", offlineDataSync.getCreatedUserID() + "_" + ImproveHelper.getTableNameWithOutSpace(offlineDataSync.getApp_name()));
                                    mainObject.put("TypeofSubmission", "");
                                    mainObject.put("insertColumns", new JSONArray());
                                    mainObject.put("UpdateColumns", new JSONArray());
                                    mainObject.put("tableSettingsType", "Create New Table");
                                    insertCheckListValueinMainObject(offlineDataSync);
                                    insertAutoNumberValueinMainObject(offlineDataSync);

                                    int maxSize = 0;
                                    if (subFormStringList.size() > 0) {
                                        for (int i = 0; i < subFormStringList.size(); i++) {

                                            if (subFormStringList.get(i).size() > maxSize) {

                                                maxSize = subFormStringList.get(i).size();
                                            }

                                        }
                                    }

                                    if (maxSize == 0) {
                                        maxSize = 1;
                                    }

                                    JSONArray array = new JSONArray();
                                    JSONArray arraySubform = new JSONArray();
                                    for (int i = 0; i < maxSize; i++) {

                                        JSONObject jsonObject = new JSONObject();
                                        if (stringListSubmit.size() > 0) {
                                            for (int j = 0; j < stringListSubmit.size(); j++) {

                                                jsonObject.put(stringListSubmit.get(j).split("\\|")[0], stringListSubmit.get(j).split("\\|")[1]);

                                            }
                                        }
                                        array.put(jsonObject);
                                    }
                                    Log.d(TAG, "run: ," + subFormStringList);
                                    for (int l = 0; l < subFormStringList.size(); l++) {
                                        JSONObject subformJson = new JSONObject();
                                        String subFormName = subFormStringList.get(l).get(0).get(0).split("\\|")[0];
                                        String subFormName_New = subFormName.substring(0, subFormName.length() - 2);
                                        subformJson.put("SubFormName", subFormName_New);
                                        subformJson.put("TableName", offlineDataSync.getCreatedUserID() + "_" + ImproveHelper.getTableNameWithOutSpace(offlineDataSync.getApp_name()) + "_" + subFormName_New);
                                        JSONArray subFormdatFieldsArray = new JSONArray();
                                        for (int j = 0; j < subFormStringList.get(l).size(); j++) {
                                            JSONObject row = new JSONObject();
                                            for (int k = 1; k < subFormStringList.get(l).get(j).size(); k++) {

                                                String columnName = subFormStringList.get(l).get(j).get(k).split("\\|")[0];
                                                String columnValue = subFormStringList.get(l).get(j).get(k).split("\\|")[1];
                                                row.put(columnName, columnValue);
                                            }
                                            subFormdatFieldsArray.put(row);
                                        }
                                        subformJson.put("DataFields", subFormdatFieldsArray);
                                        subformJson.put("TypeofSubmission", "");
                                        subformJson.put("insertColumns", new JSONArray());
                                        subformJson.put("UpdateColumns", new JSONArray());
                                        subformJson.put("tableSettingsType", "Create New Table");
                                        arraySubform.put(subformJson);
                                    }

                                    mainObject.put("DataFields", array);
                                    mainObject.put("SubFormDataFields", arraySubform);

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }*/
                                try {
                                    mainObject = new JSONObject(offlineDataSync.getOffline_json());

                                    mainObject.put("sno", offlineDataSync.getSno());
                                    mainObject.put("OrgId", offlineDataSync.getOrgId());
                                    mainObject.put("PageName", offlineDataSync.getApp_name());
                                    mainObject.put("CreatedUserID", offlineDataSync.getCreatedUserID());
                                    mainObject.put("SubmittedUserID", offlineDataSync.getSubmittedUserID());
                                    mainObject.put("SubmittedUserPostID", strPostId);
                                    mainObject.put("PostId", strPostId);
                                    mainObject.put("DistributionID", offlineDataSync.getDistributionID());
                                    mainObject.put("IMEI", offlineDataSync.getIMEI());
                                    mainObject.put("OperationType", "");
                                    mainObject.put("TransID", "");
                                    mainObject.put("TableName", offlineDataSync.getCreatedUserID() + "_" + ImproveHelper.getTableNameWithOutSpace(offlineDataSync.getApp_name()));
                                    mainObject.put("TypeofSubmission", "");
                                    mainObject.put("insertColumns", new JSONArray());
                                    mainObject.put("UpdateColumns", new JSONArray());
                                    mainObject.put("tableSettingsType", "Create New Table");
                                    int maxSize = 0;
                                    if (subFormStringList.size() > 0) {
                                        for (int i = 0; i < subFormStringList.size(); i++) {

                                            if (subFormStringList.get(i).size() > maxSize) {

                                                maxSize = subFormStringList.get(i).size();
                                            }

                                        }
                                    }

                                    if (maxSize == 0) {
                                        maxSize = 1;
                                    }

                                    JSONArray array = new JSONArray();
                                    JSONArray arraySubform = new JSONArray();
                                    for (int i = 0; i < maxSize; i++) {

                                        JSONObject jsonObject = new JSONObject();
                                        if (stringListSubmit.size() > 0) {
                                            for (int j = 0; j < stringListSubmit.size(); j++) {

                                                jsonObject.put(stringListSubmit.get(j).split("\\|")[0], stringListSubmit.get(j).split("\\|")[1]);

                                            }
                                        }
                                        array.put(jsonObject);
                                    }
                                    Log.d(TAG, "run: ," + subFormStringList);
                                    for (int l = 0; l < subFormStringList.size(); l++) {
                                        JSONObject subformJson = new JSONObject();
                                        String subFormName = subFormStringList.get(l).get(0).get(0).split("\\|")[0];
                                        String subFormName_New = subFormName.substring(0, subFormName.length() - 2);
                                        subformJson.put("SubFormName", subFormName_New);
                                        subformJson.put("TableName", offlineDataSync.getCreatedUserID() + "_" + ImproveHelper.getTableNameWithOutSpace(offlineDataSync.getApp_name()) + "_" + subFormName_New);
                                        insertAutoNumberValueinSubformObject(offlineDataSync,subformJson,subFormName_New);
                                        insertCheckListValueinSubformObject(offlineDataSync,subformJson,subFormName_New);
                                        JSONArray subFormdatFieldsArray = new JSONArray();
                                        for (int j = 0; j < subFormStringList.get(l).size(); j++) {
                                            JSONObject row = new JSONObject();
                                            for (int k = 1; k < subFormStringList.get(l).get(j).size(); k++) {

                                                String columnName = subFormStringList.get(l).get(j).get(k).split("\\|")[0];
                                                String columnValue = subFormStringList.get(l).get(j).get(k).split("\\|")[1];
                                                row.put(columnName, columnValue);
                                            }
                                            subFormdatFieldsArray.put(row);
                                        }
                                        subformJson.put("DataFields", subFormdatFieldsArray);
                                        subformJson.put("TypeofSubmission", "");
                                        subformJson.put("insertColumns", new JSONArray());
                                        subformJson.put("UpdateColumns", new JSONArray());
                                        subformJson.put("tableSettingsType", "Create New Table");

                                        arraySubform.put(subformJson);
                                    }

                                    mainObject.put("DataFields", array);
                                    mainObject.put("SubFormDataFields", arraySubform);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if (mainObject != null) {
                                    sendDataToServer(mainObject);
                                }
                            }
                        });
                    }
                });


                t3 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (tempList != null && tempList.size() > 0) {

                                    int filesCount = tempList.size();
                                    int index = 0;


                                    sendSubFormFilesToServer(tempList.get(index), index, filesCount);


                                } else {
                                    t2.start();

                                    try {
                                        t2.join();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                });
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "sendData", e);
        }

    }
    private void insertAutoNumberValueinSubformObject(OfflineDataSync offlineDataSync,JSONObject jsonObject,String subFormName) {
        try {
            JSONArray jsonArrayAuto = new JSONArray();
            JSONObject OfflineJson = new JSONObject(offlineDataSync.getOffline_json());
            JSONArray jsonArraySubForm = new JSONArray();
            jsonArraySubForm = OfflineJson.getJSONArray("SubFormDataFields");
            for (int i = 0; i < jsonArraySubForm.length(); i++) {
                JSONObject jsonObjectSubForm = new JSONObject();
                jsonObjectSubForm = jsonArraySubForm.getJSONObject(i);
                if(subFormName.equalsIgnoreCase(jsonObjectSubForm.getString("SubFormName"))){
                    String IfautoincrementControls = jsonObjectSubForm.getString("IfautoincrementControls");
                    jsonObject.put("IfautoincrementControls", IfautoincrementControls);
                    if (IfautoincrementControls.equalsIgnoreCase("true")) {
                        jsonObject.put("AutoIncrementControl", jsonObjectSubForm.getJSONArray("AutoIncrementControl"));
                    } else {
                        jsonObject.put("AutoIncrementControl", jsonArrayAuto);
                    }
                    break;
                }
            }

        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "insertAutoNumberValueinMainObject", e);
        }
    }
    private void insertAutoNumberValueinMainObject(OfflineDataSync offlineDataSync) {
        try {
            JSONArray jsonArrayAuto = new JSONArray();
            JSONObject OfflineJson = new JSONObject(offlineDataSync.getOffline_json());
            String IfautoincrementControls = OfflineJson.getString("IfautoincrementControls");
            mainObject.put("IfautoincrementControls", IfautoincrementControls);
            if (IfautoincrementControls.equalsIgnoreCase("true")) {
                mainObject.put("AutoIncrementControl", OfflineJson.getJSONArray("AutoIncrementControl"));
            } else {
                mainObject.put("AutoIncrementControl", jsonArrayAuto);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "insertAutoNumberValueinMainObject", e);
        }
    }
    private void insertCheckListValueinMainObject(OfflineDataSync offlineDataSync) {
        try {
            JSONArray jsonArrayCheckList = new JSONArray();
            JSONObject OfflineJson = new JSONObject(offlineDataSync.getOffline_json());
            String IsCheckList = OfflineJson.getString("IsCheckList");
            mainObject.put("IsCheckList", IsCheckList);
            if (IsCheckList.equalsIgnoreCase("true")) {
                mainObject.put("ChecklistNames", OfflineJson.getJSONArray("ChecklistNames"));
            } else {
                mainObject.put("ChecklistNames", jsonArrayCheckList);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "insertCheckListValueinMainObject", e);
        }
    }

    private void insertCheckListValueinSubformObject(OfflineDataSync offlineDataSync,JSONObject jsonObject,String subFormName) {

        try {
            JSONArray jsonArrayCheckList = new JSONArray();
            JSONObject OfflineJson = new JSONObject(offlineDataSync.getOffline_json());
            JSONArray jsonArraySubForm = new JSONArray();
            jsonArraySubForm = OfflineJson.getJSONArray("SubFormDataFields");
            for (int i = 0; i < jsonArraySubForm.length(); i++) {
                JSONObject jsonObjectSubForm = new JSONObject();
                jsonObjectSubForm = jsonArraySubForm.getJSONObject(i);
                if(subFormName.equalsIgnoreCase(jsonObjectSubForm.getString("SubFormName"))){
                    String IsCheckList = jsonObjectSubForm.getString("IsCheckList");
                    jsonObject.put("IsCheckList", IsCheckList);
                    if (IsCheckList.equalsIgnoreCase("true")) {
                        jsonObject.put("ChecklistNames", jsonObjectSubForm.getJSONArray("ChecklistNames"));
                    } else {
                        jsonObject.put("ChecklistNames", jsonArrayCheckList);
                    }
                    break;
                }
            }

        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "insertAutoNumberValueinMainObject", e);
        }

        try {
            JSONArray jsonArrayCheckList = new JSONArray();
            JSONObject OfflineJson = new JSONObject(offlineDataSync.getOffline_json());
            String IsCheckList = OfflineJson.getString("IsCheckList");
            mainObject.put("IsCheckList", IsCheckList);
            if (IsCheckList.equalsIgnoreCase("true")) {
                mainObject.put("ChecklistNames", OfflineJson.getJSONArray("ChecklistNames"));
            } else {
                mainObject.put("ChecklistNames", jsonArrayCheckList);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "insertCheckListValueinMainObject", e);
        }
    }

    public void sendFilesToServer(final HashMap<String, String> path, final int fileCount,
                                  final int filesCount) {
        try {
            String controlName = null;
            Log.d(TAG, "FormString3: " + stringListSubmit);
            for (String key : path.keySet()) {
                System.out.println(key);
                controlName = key;
            }
            String filePath = path.get(controlName);
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

            final String finalControlName = controlName;
            String pageName = strAppName.replace(" ", "_");
            new FileUploader(this, fileName, strUserId, pageName, false, "BHARGO", new FileUploader.OnImageUploaded() {
                @Override
                public void response(String url) {


                    Log.d(TAG, "FormString4: " + stringListSubmit);
                    stringListSubmit.add(finalControlName + "|" + url);

                    if (fileCount + 1 == filesCount) {
                        t3.start();
                        try {
                            t3.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                }
            }).execute(filePath);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "sendFilesToServer", e);
        }
    }


    public void sendSubFormFilesToServer(final HashMap<String, String> path,
                                         final int fileCount, final int filesCount) {
        try {
            Log.d(TAG, "sFormStringList: " + subFormStringList);


            String controlName = null;

            for (String key : path.keySet()) {
                System.out.println(key);
                controlName = key;
            }
            String filePath = path.get(controlName);
            final String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);


            final String finalControlName = controlName;
            String pageName = strAppName.replace(" ", "_");
            new FileUploader(this, fileName, strUserId, pageName, false, "BHARGO", new FileUploader.OnImageUploaded() {
                @Override
                public void response(String url) {


                    String controlName = finalControlName.split("\\$")[0];

                    int subFormId = Integer.parseInt(finalControlName.split("\\$")[1]);

                    int subFormRowId = Integer.parseInt(finalControlName.split("\\$")[2]);

                    int controlPos = Integer.parseInt(finalControlName.split("\\$")[3]);

                    HashMap<String, String> map = new HashMap<>();

                    map.put(controlName, url);


//                subFormFilesList.get(subFormId).get(subFormRowId).get(controlPos).put(finalControlName, url);

//                subFormStringList.get(0).get(subFormRowId).add(controlName + "|" + url);

                    if (subFormStringList.size() > 1) {
                        subFormStringList.get(subFormId).get(subFormRowId).add(controlName + "|" + url);
                    } else {
                        subFormStringList.get(0).get(subFormRowId).add(controlName + "|" + url);
                    }


                    int index = fileCount;


                    //stringListSubmit.add(finalControlName + "|" + url);

                    if (fileCount + 1 == filesCount) {

                        t2.start();
                        try {
                            t2.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        index++;
                        sendSubFormFilesToServer(tempList.get(index), index, filesCount);
                    }

                }
            }).execute(filePath);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "sendSubFormFilesToServer", e);
        }
    }

    public void sendDataToServer(JSONObject mainObject) {
        try {
            Gson gson = new Gson();

            Log.d(TAG, "sendDataTest: " + gson.toJson(mainObject));

            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(mainObject.toString());

            Call<FormDataResponse> call = getServices.sendFormData_offline1(jo);
//        Call<ServerResponse> call = getServices.sendFormData(formData1);
            call.enqueue(new Callback<FormDataResponse>() {
                @Override
                public void onResponse(Call<FormDataResponse> serverResponse, Response<FormDataResponse> response) {

                    stringListSubmit.clear();
                    stringListFiles.clear();
                    improveHelper.dismissProgressDialog();
                   try {

                        if (response.body() != null) {


                            Log.d(TAG, "sendDataToServerResponse: " + response.body().getMessage());
//
                            String resultStatus = response.body().getStatus();
                            if (resultStatus.equalsIgnoreCase("200")) {
//                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                updateAppsList(response.body().getSno(), response.body().getMessage());
                            } else if (resultStatus.equalsIgnoreCase("100")) {
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(context, "server data is null", Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<FormDataResponse> call, Throwable t) {
                    Log.d(TAG, "sendDataToServerResponse: " + t.toString());
                    improveHelper.dismissProgressDialog();
                    stringListSubmit.clear();
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "sendDataToServer", e);
        }

    }

    private void updateAppsList(String sno, String message) {
        try {
            improveDataBase.updateFormSubmitDataStatus(sno, sessionManager.getUserDataFromSession().getUserID());

            if (offlineDataSyncList.size() - 1 != offlineRecordCount) {
                offlineRecordCount++;

                sendOfflineData(offlineDataSyncList.get(offlineRecordCount));
            } else if (offlineDataSyncList.size() - 1 == offlineRecordCount) {
                improveHelper.dismissProgressDialog();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                refreshAppsList();

            }

        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "updateAppsList", e);
        }

    }

    private String getOperatorSymbol(String field_operator) {

        String operator = field_operator;

        try {
            switch (field_operator) {
                case AppConstants.OPERATOR_LESS_THAN:
                    operator = "<";
                    break;
                case AppConstants.OPERATOR_GREATER_THAN:
                    operator = ">";
                    break;
                case AppConstants.OPERATOR_LESS_THAN_EQUALS:
                    operator = "<=";
                    break;
                case AppConstants.OPERATOR_GREATER_THAN_EQUALS:
                    operator = ">=";
                    break;
                case AppConstants.OPERATOR_EQUALS:
                    operator = "=";
                    break;
                case AppConstants.OPERATOR_NOT_EQUALS:
                    operator = "!=";
                    break;
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getOperatorSymbol", e);
        }

        return operator;
    }

    private String getValueFromOfflineJson(OfflineDataSync offlineDataSync, String field_name, String mapExistingType, List<String> stringListSubmit, String currentControl) {
        String controlValue = null;

        try {
            JSONObject OfflineJson = new JSONObject(offlineDataSync.getOffline_json());
            if (mapExistingType.equalsIgnoreCase("Insert")) {
                JSONArray insertFields = OfflineJson.getJSONArray("insertColumns");
                JSONObject insertField = insertFields.getJSONObject(0);
                controlValue = insertField.getString(field_name);
            } else if (mapExistingType.equalsIgnoreCase("Update")) {
                JSONArray UpdateColumns = OfflineJson.getJSONArray("UpdateColumns");
                for (int i = 0; i < UpdateColumns.length(); i++) {
                    JSONObject updateColumn = UpdateColumns.getJSONObject(i);
                    JSONObject setColumns = updateColumn.getJSONObject("setColumns");
                    controlValue = setColumns.getString(field_name);
                    if (!controlValue.equalsIgnoreCase("")) {
                        break;
                    }
                }
            } else if (mapExistingType.equalsIgnoreCase("Filter")) {
                JSONArray UpdateColumns = OfflineJson.getJSONArray("UpdateColumns");
                for (int i = 0; i < UpdateColumns.length(); i++) {
                    JSONObject updateColumn = UpdateColumns.getJSONObject(i);
                    JSONArray filterColumns = updateColumn.getJSONArray("filterColumns");
                    for (int j = 0; j < filterColumns.length(); j++) {
                        JSONObject filterColumn = filterColumns.getJSONObject(i);
                        String field_name_str = filterColumn.getString("ColumnName");
                        if (field_name_str.equalsIgnoreCase(field_name)) {
                            controlValue = filterColumn.getString("Value");
                            if (!controlValue.equalsIgnoreCase("")) {
                                break;
                            }
                        }
                    }
                    if (!controlValue.equalsIgnoreCase("")) {
                        break;
                    }
                }
            }
//        } catch (Exception e) {
//            Log.d(TAG, "getValueFromOfflineJson: " + e.toString());
//        }

            if (controlValue.equalsIgnoreCase("")) {

                for (int j = 0; j < stringListSubmit.size(); j++) {
                    String[] strControlNamenValue = stringListSubmit.get(j).split("\\|");
                    if (strControlNamenValue[0].equalsIgnoreCase(getControlName(currentControl))) {
                        controlValue = strControlNamenValue[1];
                        break;
                    }

                }
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getValueFromOfflineJson", e);
        }

        return controlValue;
    }

    private String getSubFormValueFromOfflineJson(OfflineDataSync offlineDataSync, String field_name, String mapExistingType, List<List<List<String>>> subFormStringList, String currentControl, int subFormPosition) {
        String controlValue = null;

        try {

            JSONObject offlineJsonSubsorm = new JSONObject(offlineDataSync.getOffline_json());
            JSONArray jsonArray = offlineJsonSubsorm.getJSONArray("SubFormDataFields");
//            for (int k = 0; k < jsonArray.length(); k++) {
            JSONObject OfflineJson = jsonArray.getJSONObject(subFormPosition);
            if (mapExistingType.equalsIgnoreCase("Insert")) {
                JSONArray insertFields = OfflineJson.getJSONArray("insertColumns");
                JSONObject insertField = insertFields.getJSONObject(0);
                controlValue = insertField.getString(field_name);
            } else if (mapExistingType.equalsIgnoreCase("Update")) {
                JSONArray UpdateColumns = OfflineJson.getJSONArray("UpdateColumns");
                for (int i = 0; i < UpdateColumns.length(); i++) {
                    JSONObject updateColumn = UpdateColumns.getJSONObject(i);
                    JSONObject setColumns = updateColumn.getJSONObject("setColumns");
                    controlValue = setColumns.getString(field_name);
                    if (!controlValue.equalsIgnoreCase("")) {
                        break;
                    }
                }
            } else if (mapExistingType.equalsIgnoreCase("Filter")) {
                JSONArray UpdateColumns = OfflineJson.getJSONArray("UpdateColumns");
                for (int i = 0; i < UpdateColumns.length(); i++) {
                    JSONObject updateColumn = UpdateColumns.getJSONObject(i);
                    JSONArray filterColumns = updateColumn.getJSONArray("filterColumns");
                    for (int j = 0; j < filterColumns.length(); j++) {
                        JSONObject filterColumn = filterColumns.getJSONObject(i);
                        String field_name_str = filterColumn.getString("ColumnName");
                        if (field_name_str.equalsIgnoreCase(field_name)) {
                            controlValue = filterColumn.getString("Value");
                            if (!controlValue.equalsIgnoreCase("")) {
                                break;
                            }
                        }
                    }
                    if (!controlValue.equalsIgnoreCase("")) {
                        break;
                    }
                }
            }

//        } catch (Exception e) {
//            Log.d(TAG, "getSubFormValueFromOfflineJson: " + e.toString());
//        }

            if (controlValue.equalsIgnoreCase("")) {

                for (int i = 0; i < subFormStringList.size(); i++) {
                    for (int j = 0; j < subFormStringList.get(i).size(); j++) {
                        for (int k = 1; k < subFormStringList.get(i).get(j).size(); k++) {
                            String[] strControlNamenValue = subFormStringList.get(i).get(j).get(k).split("\\|");
                            if (strControlNamenValue[0].equalsIgnoreCase(getControlName(currentControl))) {
                                controlValue = strControlNamenValue[1];
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getSubFormValueFromOfflineJson", e);
        }

        return controlValue;
    }

    public List<SubformMapExistingData> getSubFormMapExistingData(String offlineJson) {
        List<SubformMapExistingData> subFormMapExisitngDataList = new ArrayList<>();
        try {
            JSONObject offlineJsonSubsorm = new JSONObject(offlineJson);
            JSONArray jsonArray = offlineJsonSubsorm.getJSONArray("SubFormDataFields");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subFormJson = jsonArray.getJSONObject(i);
                SubformMapExistingData subformMapExistingData = new SubformMapExistingData();
                subformMapExistingData.setSubformName(subFormJson.getString("SubFormName"));
                subformMapExistingData.setTableSettingsType(subFormJson.getString("tableSettingsType"));
                subformMapExistingData.setExistingTableName(subFormJson.getString("TableName"));
                subformMapExistingData.setMapExistingType(subFormJson.getString("TypeofSubmission"));

                if (subFormJson.has("tableSettingsType") && subFormJson.getString("tableSettingsType") == null) {
                    subformMapExistingData.setSubFormDataFields(subFormJson.getJSONArray("DataFields"));
                    subformMapExistingData.setSubFormInsertFields(null);
                    subformMapExistingData.setSubFormUpdateFields(null);
                    subFormMapExisitngDataList.add(subformMapExistingData);
                } else if (subFormJson.getString("tableSettingsType").equalsIgnoreCase("Create New Table")) {
                    subformMapExistingData.setSubFormDataFields(subFormJson.getJSONArray("DataFields"));
                    subformMapExistingData.setSubFormInsertFields(null);
                    subformMapExistingData.setSubFormUpdateFields(null);
                    subFormMapExisitngDataList.add(subformMapExistingData);
                } else if (subFormJson.getString("tableSettingsType").equalsIgnoreCase("Map existing table")) {
                    if (subFormJson.getString("TypeofSubmission").equalsIgnoreCase("Insert")) {
                      /*  JSONArray insertArray = subFormJson.getJSONArray("insertColumns");
                        List<QueryFilterField_Bean> insertQueryFilterField_beans = new ArrayList<>();
                        for (int j = 0; j < insertArray.length(); j++) {
                            JSONObject insertJsonObject = insertArray.getJSONObject(j);
                            Iterator<String> keys = insertJsonObject.keys();
                            while (keys.hasNext()) {
                                QueryFilterField_Bean insertBean = new QueryFilterField_Bean();
                                String key = keys.next();
                                insertBean.setField_Name(key);
                                insertBean.setField_Global_Value(insertJsonObject.getString(key));
                                insertQueryFilterField_beans.add(insertBean);
                            }
                        }
                        subformMapExistingData.setSubFormInsertFields(insertQueryFilterField_beans);
                        subFormMapExisitngDataList.add(subformMapExistingData);*/

                        List<QueryFilterField_Bean> subFormInsertFields = gson.fromJson(subFormJson.getString("insertFields"), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                        }.getType());
                        subformMapExistingData.setSubFormInsertFields(subFormInsertFields);
                        subFormMapExisitngDataList.add(subformMapExistingData);
                    } else if (subFormJson.getString("TypeofSubmission").equalsIgnoreCase("Update")) {
                      /*  JSONArray updateArray = subFormJson.getJSONArray("UpdateColumns");
                        List<QueryFilterField_Bean> updateQueryFilterField_beans = new ArrayList<>();
                        List<QueryFilterField_Bean> filterQueryFilterField_beans = new ArrayList<>();
                        for (int j = 0; j < updateArray.length(); j++) {
                            JSONObject updateJsonObject = updateArray.getJSONObject(j);
                            JSONObject setColumns = updateJsonObject.getJSONObject("setColumns");
                            Iterator<String> keys = setColumns.keys();
                            while (keys.hasNext()) {
                                QueryFilterField_Bean updateBean = new QueryFilterField_Bean();
                                String key = keys.next();
                                updateBean.setField_Name(key);
                                updateBean.setField_Global_Value(setColumns.getString(key));
                                updateQueryFilterField_beans.add(updateBean);
                            }

                            JSONArray filterColumns = updateJsonObject.getJSONArray("filterColumns");
                            for (int k = 0; k < filterColumns.length(); k++) {
                                QueryFilterField_Bean filterBean = new QueryFilterField_Bean();
                                JSONObject filterColumn = filterColumns.getJSONObject(k);
                                filterBean.setField_Name(filterColumn.getString("ColumnName"));
                                filterBean.setField_Operator(filterColumn.getString("Operator"));
                                filterBean.setField_Global_Value(filterColumn.getString("Value"));
                                filterBean.setField_and_or(filterColumn.getString("Condition"));
                                filterQueryFilterField_beans.add(filterBean);
                            }
                        }*/
                        List<QueryFilterField_Bean> subFormUpdateFields = gson.fromJson(subFormJson.getString("updateFields"), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                        }.getType());
                        List<QueryFilterField_Bean> subFormFilterFields = gson.fromJson(subFormJson.getString("filterFields"), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                        }.getType());
                        subformMapExistingData.setSubFormUpdateFields(subFormUpdateFields);
                        subformMapExistingData.setSubFormFilterFields(subFormFilterFields);
                        subFormMapExisitngDataList.add(subformMapExistingData);
                    } else if (subFormJson.getString("TypeofSubmission").equalsIgnoreCase("Insert / Update")) {
                        /*JSONArray updateArray = subFormJson.getJSONArray("UpdateColumns");
                        List<QueryFilterField_Bean> updateQueryFilterField_beans = new ArrayList<>();
                        List<QueryFilterField_Bean> filterQueryFilterField_beans = new ArrayList<>();
                        for (int j = 0; j < updateArray.length(); j++) {
                            JSONObject updateJsonObject = updateArray.getJSONObject(j);
                            JSONObject setColumns = updateJsonObject.getJSONObject("setColumns");
                            Iterator<String> keys = setColumns.keys();
                            while (keys.hasNext()) {
                                QueryFilterField_Bean updateBean = new QueryFilterField_Bean();
                                String key = keys.next();
                                updateBean.setField_Name(key);
                                updateBean.setField_Global_Value(setColumns.getString(key));
                                updateQueryFilterField_beans.add(updateBean);
                            }

                            JSONArray filterColumns = updateJsonObject.getJSONArray("filterColumns");
                            for (int k = 0; k < filterColumns.length(); k++) {
                                QueryFilterField_Bean filterBean = new QueryFilterField_Bean();
                                JSONObject filterColumn = filterColumns.getJSONObject(k);
                                filterBean.setField_Name(filterColumn.getString("ColumnName"));
                                filterBean.setField_Operator(filterColumn.getString("Operator"));
                                filterBean.setField_Global_Value(filterColumn.getString("Value"));
                                filterBean.setField_and_or(filterColumn.getString("Condition"));
                                filterQueryFilterField_beans.add(filterBean);
                            }
                        }*/
                        List<QueryFilterField_Bean> subFormUpdateFields = gson.fromJson(subFormJson.getString("updateFields"), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                        }.getType());
                        List<QueryFilterField_Bean> subFormFilterFields = gson.fromJson(subFormJson.getString("filterFields"), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                        }.getType());

                        subformMapExistingData.setSubFormUpdateFields(subFormUpdateFields);
                        subformMapExistingData.setSubFormFilterFields(subFormFilterFields);

                        /*JSONArray insertArray = subFormJson.getJSONArray("insertColumns");

                        List<QueryFilterField_Bean> insertQueryFilterField_beans = new ArrayList<>();
                        for (int j = 0; j < insertArray.length(); j++) {
                            JSONObject insertJsonObject = insertArray.getJSONObject(j);
                            Iterator<String> keys = insertJsonObject.keys();
                            while (keys.hasNext()) {
                                QueryFilterField_Bean insertBean = new QueryFilterField_Bean();
                                String key = keys.next();
                                insertBean.setField_Name(key);
                                insertBean.setField_Global_Value(insertJsonObject.getString(key));
                                insertQueryFilterField_beans.add(insertBean);
                            }
                        }*/
                        List<QueryFilterField_Bean> subFormInsertFields = gson.fromJson(subFormJson.getString("insertFields"), new TypeToken<ArrayList<QueryFilterField_Bean>>() {
                        }.getType());
                        subformMapExistingData.setSubFormInsertFields(subFormInsertFields);
                        subFormMapExisitngDataList.add(subformMapExistingData);
                    }
                }
            }

        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getSubFormMapExistingData", e);
        }
        return subFormMapExisitngDataList;
    }

    public String getControlName(String globalVariableOutput) {
        String controlName = globalVariableOutput;
        try {
            if (globalVariableOutput.toLowerCase().contains("(im:")) {
                String ControlType = globalVariableOutput.substring(globalVariableOutput.indexOf(":") + 1, globalVariableOutput.indexOf("."));
                if (ControlType.equalsIgnoreCase("ControlName")) {
                    controlName = globalVariableOutput.substring(globalVariableOutput.indexOf(".") + 1, globalVariableOutput.indexOf(")"));
                } else if (ControlType.equalsIgnoreCase("SubControls")) {
                    int first = globalVariableOutput.indexOf(".") + 1;
                    String controlName_ = globalVariableOutput.substring(globalVariableOutput.indexOf(".", first + 1), globalVariableOutput.indexOf(")"));
                    controlName = controlName_.replaceFirst(".", "");
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getControlName", e);
        }

        return controlName;
    }

    class API_DilogDismiss implements DialogInterface.OnDismissListener {
        String ServiceType, ServiceResult, ServiceSource, Sno;
        boolean Single;
        List<API_OutputParam_Bean> List_API_OutParams;
        GetAPIDetails_Bean.APIDetails APIDetails;

        public API_DilogDismiss(String Sno) {
            this.Sno = Sno;
        }

        @Override
        public void onDismiss(DialogInterface dialogInterface) {
            try {

                improveDataBase.deleteCallAPIRequestRowData(Sno);
                dismissProgressDialog();

                if (offlineDataSyncList.size() - 1 != offlineRecordCount) {
                    offlineRecordCount++;
                    SendCallAPIOfflineRequest(offlineDataSyncList.get(offlineRecordCount).CallAPIRequest);
                } else if (offlineDataSyncList.size() - 1 == offlineRecordCount) {
                    improveHelper.dismissProgressDialog();
                    Toast.makeText(context, "Data Submitted successfully..", Toast.LENGTH_SHORT).show();
                    refreshAppsList();

                }


            } catch (Exception E) {
                System.out.println("Error===" + E.toString());
                ImproveHelper.showToast(getApplicationContext(), "Server Error.");
            }
        }
    }

    public static class ArrayUtil
    {
        public  static ArrayList<Object> convert(JSONArray jArr)
        {
            ArrayList<Object> list = new ArrayList<Object>();
            try {
                for (int i=0, l=jArr.length(); i<l; i++){
                    list.add(jArr.get(i));
                }
            } catch (JSONException e) {}

            return list;
        }

        public  JSONArray convert(Collection<Object> list)
        {
            return new JSONArray(list);
        }

    }
}



//