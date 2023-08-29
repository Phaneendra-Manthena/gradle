package com.bhargo.user.actions;

/*
Created by nk
1. first time app install, at applist in hybrid case:
   create tables & download data from server and insert into local DB.
1.send local data
2.get server data and insert into local data
3. 1 & 2
*/

import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.bhargo.user.Java_Beans.API_InputParam_Bean;
import com.bhargo.user.Java_Beans.SyncFormData;
import com.bhargo.user.R;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppDetails;
import com.bhargo.user.pojos.AppDetailsAdvancedInput;
import com.bhargo.user.pojos.FileColAndIDPojo;
import com.bhargo.user.pojos.FilterColumns;
import com.bhargo.user.pojos.SubFormTableColumns;
import com.bhargo.user.pojos.TableNameAndColsFromDOPojo;
import com.bhargo.user.pojos.UpdateTablePojo;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.FileUploader;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bhargo.user.utils.XMLHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncData {
    private static final String TAG = "SyncData";
    private final Context context;
    String progressMsg = "";
    String syncFailResult, syncSucessResult;
   // String appName;
    String tableNameSync;
    ProgressDialog pd;
    ImproveDataBase improveDataBase;
    int syncType;
    ImproveHelper improveHelper;
    SyncFormData syncFormData;
    SyncDataListener syncDataListener;
    List<TableNameAndColsFromDOPojo> tableNameAndColsForFilesPojoList = new ArrayList<>();
    List<TableNameAndColsFromDOPojo> tableNameAndColsList = new ArrayList<>();
    AppDetails appDetails;
    List<FileColAndIDPojo> fileColAndIDPojos = new ArrayList<>();
    int sendingFilesCount = 0;
    SessionManager sessionManager;
    List<UpdateTablePojo> updateTablePojoList = new ArrayList<>();
    int firstTimeSendingInsertRecord = 0;
    GetServices getServices;
    List<List<String>> ll_senddataRecords = new ArrayList<>();
    int sendDataCount = 0, failedSendDataCount = 0;
    boolean failFlag = false;
    List<String> sendingIssues = new ArrayList<>();
    Handler handSendDataFiles = new Handler() {
        public void handleMessage(android.os.Message msg) {
            sendFilesToServer();
        }
    };
    Handler handSendDataMainAndSubFormTable = new Handler() {
        public void handleMessage(android.os.Message msg) {
            sendDataCount++;
            sendData();
        }
    };
    String xmlFetchData, xmlIndexPageColumnsOrderList, xmlIndexPageColumnsOrder, xmlLazyLoadingThreshold, xmlSubFormInMainForm="";
    boolean xmlIsLazyLoadingEnabled;
    public boolean isAutoNumbersAvaliable;
    public JSONArray jArrayAutoIncementControls = new JSONArray();
    private final LinkedHashMap<String, JSONArray> subFormsAutoNumberArraysMap = new LinkedHashMap<>();
    private final LinkedHashMap<String, Boolean> subFormsAutoNumberStatusMap = new LinkedHashMap<>();

    public SyncData(Context context, String appName, int syncType, SyncDataListener syncDataListener) {
        this.context = context;
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);
        sessionManager = new SessionManager(context);
        getServices = RetrofitUtils.getUserService();
        this.syncDataListener = syncDataListener;
        appDetails = improveDataBase.getAppDetails(appName);
        //1. send local data Only
        //2. get server data and insert into local DB
        //3. both 1 & 2
        if (appDetails != null) {
            tableNameSync=appDetails.getTableName();
            getTableNameAndColsFromxmlDF(appDetails.getTableName(), appDetails.getDesignFormat(),appDetails.getAppName());
            this.syncType = syncType;
            progressMsg = "Please Wait Data Sync...";
            syncFailResult = "";
            syncSucessResult = "";
            new SyncStart().execute();
        } else {
            failFlag = true;
            syncFailResult = "No Such AppName:" + appName + " Not Exist In Local DataBase.";
            closeProgressDialog();
            syncDataListener.onFailed(syncFailResult);
        }
    }
    public SyncData(Context context, SyncFormData syncFormData, SyncDataListener syncDataListener) {
        this.context = context;
        this.syncFormData = syncFormData;
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);
        sessionManager = new SessionManager(context);
        getServices = RetrofitUtils.getUserService();
        this.syncDataListener = syncDataListener;
        //0.Take Data From SynFormData and set syncType & appName
        this.tableNameSync = syncFormData.getTableName();
        appDetails = improveDataBase.getAppDetailsBasedOnTableName(tableNameSync);
        if (appDetails != null) {
            getTableNameAndColsFromxmlDF(appDetails.getTableName(), appDetails.getDesignFormat(),appDetails.getAppName());
            if (syncFormData.isEnableSendDataToServer() && syncFormData.isEnableRetrieveDataFromServer()) {
                this.syncType = 3;
            } else if (syncFormData.isEnableRetrieveDataFromServer()) {
                this.syncType = 2;
            } else {
                this.syncType = 1;
            }
            progressMsg = "Please Wait Data Sync...";
            syncFailResult = "";
            syncSucessResult = "";
            new SyncStart().execute();
        } else {
            failFlag = true;
            syncFailResult = "No Such TableName:" + tableNameSync + " Not Exist In Local DataBase.";
            closeProgressDialog();
            syncDataListener.onFailed(syncFailResult);
        }
    }

    private void sendLocalDataCase() {
        if (improveDataBase.tableExists(appDetails.getTableName())) {
            firstTimeSendingInsertRecord = 0;
            String tableName = appDetails.getTableName();
            String syncstatus_maintablecol = "Bhargo_syncstatus";
            sendingIssues.size();
            sendDataCount = 0;
            ll_senddataRecords = improveDataBase.getDataByQuery
                    ("select *  from " + tableName + " where " + syncstatus_maintablecol + "='0'");
            if (ll_senddataRecords.size() > 0) {
                progressMsg = "Please Wait! Data Sending...";
            }
            sendData();
        } else {
            if (syncType == 3) {
                downloadSyncDataFromServerCase();
            } else {
//                    failFlag = true;
//                    syncFailResult = "No Such Table:" + appDetails.getTableName() + " In Local DataBase.";
                closeProgressDialog();
                String msg = context.getString(R.string.no_data_available_to_sync);
                syncDataListener.onSuccess(msg);
            }
        }
    }

    private void keysDifferentInCreatedTable() {
        //keys not same
        //1. check data exists in table
        //2. if no drop table and re-create table
        //3. if yes copy that table, create new table and dump that old data
        if (improveDataBase.getCount(appDetails.getTableName()) == 0) {
            //2. if no drop table and re-create table
            improveDataBase.dropAndCreateTablesBasedOnConditions(appDetails, false);
        } else {
            try {
                improveDataBase.dropAndCreateTablesBasedOnConditions(appDetails, true);
            } catch (Exception e) {
                failFlag = true;
                syncFailResult = "Table Re-Creation Failed!" + e.getMessage();
                closeProgressDialog();
                syncDataListener.onFailed(syncFailResult);
            }
        }
    }

    private void downloadSyncDataFromServerCase() {
        try {
            progressMsg = "Please Wait! Data Fetching...";
            /*boolean keysSame = improveHelper.checkPrimaryKeysAsPerDataCollectionObj( appDetails, improveDataBase);
            if (keysSame) {
                improveDataBase.createTablesBasedOnConditions(appDetails);
            } else {
                keysDifferentInCreatedTable();
            }*/
            AppDetailsAdvancedInput appDetailsAdvancedInput = new AppDetailsAdvancedInput();
            appDetailsAdvancedInput.setFilterColumns(new ArrayList<>());
            appDetailsAdvancedInput.setOrgId(sessionManager.getOrgIdFromSession());
            appDetailsAdvancedInput.setCreatedBy(appDetails.getCreatedBy());
            appDetailsAdvancedInput.setPageName(appDetails.getAppName());
            appDetailsAdvancedInput.setUserId(sessionManager.getUserDataFromSession().getUserID());
            appDetailsAdvancedInput.setSubmittedUserPostID(sessionManager.getPostsFromSession());
            if (xmlFetchData != null) {
                appDetailsAdvancedInput.setFetchData(xmlFetchData);
            } else {
                appDetailsAdvancedInput.setFetchData("Login User Post");
            }
            if (syncFormData != null) {
                if (syncFormData.getRetrieveType() != null) {
                    appDetailsAdvancedInput.setFetchData(syncFormData.getRetrieveType());
                } else {
                    appDetailsAdvancedInput.setFetchData("Login User Post");
                }
                   /* if (syncFormData.getFilterColumns() != null) {
                        List<API_InputParam_Bean> l_filters = syncFormData.getFilterColumns();
                        JSONArray jsonArrayFilters = new JSONArray();
                        for (int i = 0; i < l_filters.size(); i++) {
                            API_InputParam_Bean api_inputParam_bean = l_filters.get(i);
                            if (api_inputParam_bean.isEnable()) {//only true case add in where condition
                                String colName = api_inputParam_bean.getInParam_Name();
                                String colVal = ImproveHelper.getValueFromGlobalObject(context, api_inputParam_bean.getInParam_Mapped_ID().trim());
                                String and_or = api_inputParam_bean.getInParam_and_or();
                                String operator = ImproveHelper.getOparator(api_inputParam_bean.getInParam_Operator());
                                JSONObject jsonObjFilters = new JSONObject();
                                jsonObjFilters.put("ColumnName", colName);
                                jsonObjFilters.put("Operator", operator);
                                jsonObjFilters.put("Value", colVal);
                                jsonObjFilters.put("Condition", and_or);
                                jsonObjFilters.put("ColumnType", "Others");
                                jsonObjFilters.put("NearBy", "");
                                jsonObjFilters.put("NoOfRec", "");
                                jsonObjFilters.put("CurrentGPS", "");
                                jsonArrayFilters.put(jsonObjFilters);
                            }
                        }
                        appDetailsAdvancedInput.setFilterColumns(jsonArrayFilters);
                    }*/
                if (syncFormData.getFilterColumns() != null) {
                    List<API_InputParam_Bean> l_filters = syncFormData.getFilterColumns();
                    List<FilterColumns> filterColumnsList = new ArrayList<>();
                    for (int i = 0; i < l_filters.size(); i++) {
                        API_InputParam_Bean api_inputParam_bean = l_filters.get(i);
                        if (api_inputParam_bean.isEnable()) {//only true case add in where condition
                            String colName = api_inputParam_bean.getInParam_Name();
                            String colVal = ImproveHelper.getValueFromGlobalObject(context, api_inputParam_bean.getInParam_Mapped_ID().trim());
                            String and_or = api_inputParam_bean.getInParam_and_or();
                            String operator = ImproveHelper.getOparator(api_inputParam_bean.getInParam_Operator());
                            FilterColumns filterColumns = new FilterColumns();
                            filterColumns.setColumnName(colName);
                            filterColumns.setOperator(operator);
                            filterColumns.setValue(colVal);
                            filterColumns.setCondition(and_or);
                            filterColumns.setColumnType("Others");
                            filterColumns.setNearBy("");
                            filterColumns.setNoOfRec("");
                            filterColumns.setCurrentGPS("");
                            filterColumnsList.add(filterColumns);
                        }
                    }
                    appDetailsAdvancedInput.setFilterColumns(filterColumnsList);
                }
            }
            if (xmlIndexPageColumnsOrderList != null && !xmlIndexPageColumnsOrderList.equalsIgnoreCase("")) {
                appDetailsAdvancedInput.setOrderbyStatus("True");
                appDetailsAdvancedInput.setOrderByColumns(xmlIndexPageColumnsOrderList);
                appDetailsAdvancedInput.setOrderByType(xmlIndexPageColumnsOrder);
            } else {
                appDetailsAdvancedInput.setOrderbyStatus("False");
                appDetailsAdvancedInput.setOrderByColumns("");
                appDetailsAdvancedInput.setOrderByType("");
            }

            if (xmlIsLazyLoadingEnabled) {
                appDetailsAdvancedInput.setLazyLoading("True");
                appDetailsAdvancedInput.setThreshold(xmlLazyLoadingThreshold);
                appDetailsAdvancedInput.setRange("1" + "-" + xmlLazyLoadingThreshold);
                //TODO  --- In future need to set order by columns in the below line
                appDetailsAdvancedInput.setLazyOrderKey("SELECT NULL");
            }

            if (xmlIsLazyLoadingEnabled && appDetailsAdvancedInput.getLazyOrderKey().contentEquals("")) {
                appDetailsAdvancedInput.setLazyOrderKey("SELECT NULL");
            }
            // showProgressDialog(context, context.getString(R.string.please_wait_sync));
            Call<ResponseBody> getAllAppNamesDataCall;
            getAllAppNamesDataCall = getServices.iGetAppDataOffline(sessionManager.getAuthorizationTokenId(),appDetailsAdvancedInput);
            getAllAppNamesDataCall.enqueue(new Callback<ResponseBody>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    String json = null;
                    try {
                        json = response.body().string();
                        Log.d(TAG, "appdata: " + json);
                        JSONObject responseObj = new JSONObject(json);
                        if (responseObj.getString("Status").contentEquals("200") && !responseObj.getString("Message").equalsIgnoreCase("No Data Present Now")) {
                            JSONArray jsonArray = new JSONArray((responseObj.getString("Data")));
                            JSONObject jobj = new JSONObject();
                            jobj.put("Data", jsonArray);
                            ImproveHelper.Controlflow("getAppDataOnline", "AppsList", "ViewData", jobj.toString());
                            if (jsonArray.length() == 0) {
                                closeProgressDialog();
                                //"No Records To Sync!"
                                syncSucessResult = "No Records To Sync!";
                                syncDataListener.onSuccess(syncSucessResult);
                            } else {
                                // ImproveHelper.showToast(OfflineHybridSynActivtiy.this, getString(R.string.data_recieved_successfully));
                                insertDataToDB(jsonArray);
                            }
                        } else {
                            failFlag = true;
                            closeProgressDialog();
                            syncFailResult = responseObj.getString("Message") + "\nPlease Try Again.";
                            syncDataListener.onFailed(syncFailResult);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        closeProgressDialog();
                        failFlag = true;
                        syncFailResult = e.getMessage() + "\nPlease Try Again.";
                        syncDataListener.onFailed(syncFailResult);
                    }
                    Log.d(TAG, "onResponse: " + json);
                }


                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    closeProgressDialog();
                    failFlag = true;
                    syncFailResult = t.getMessage() + "\nPlease Try Again.";
                    syncDataListener.onFailed(syncFailResult);
                }
            });

        } catch (Exception e) {
            failFlag = true;
            syncFailResult = e.getMessage();
            closeProgressDialog();
            syncDataListener.onFailed(syncFailResult);
        }


    }

    private void sendData() {
        if (sendDataCount < ll_senddataRecords.size()) {
            firstTimeSendingInsertRecord = 1;
            fileColAndIDPojos = improveDataBase.getFilePathFromTables(tableNameAndColsForFilesPojoList, ll_senddataRecords.get(sendDataCount).get(0));
            if (fileColAndIDPojos.size() > 0) {
                sendingFilesCount = 0;
                // showProgressDialog(context, "Please Wait! Files Sending...");
                sendFilesToServer();
            } else {
                // showProgressDialog(context, "Please Wait! Data Sending...");
                sendMainAndSubFromDataToServer();
            }
        } else {

            if (firstTimeSendingInsertRecord == 0) {
                if (syncType == 3) {
                    downloadSyncDataFromServerCase();
                } else {
                    closeProgressDialog();
                    syncSucessResult = "No Record's To Submit";
                    syncDataListener.onSuccess(syncSucessResult);
                }
            } else {
                //closeProgressDialog();
                if (sendingIssues.size() == 0) {
                    //Download
                    if (syncType == 3) {
                        downloadSyncDataFromServerCase();
                    } else {
                        closeProgressDialog();
                        syncSucessResult = "All Records Submitted Successfully";
                        syncDataListener.onSuccess(syncSucessResult);
                    }
                } else {
                    failFlag = true;
                    int totsuccess=ll_senddataRecords.size()-sendingIssues.size();
                    String sucessRecords = "Success Records:"+totsuccess+"\n";
                    String failedRecords = "Failed Records:"+sendingIssues.size()+"\n";
                    syncFailResult =sucessRecords+ failedRecords+ sendingIssues.toString();
                    closeProgressDialog();
                    syncDataListener.onFailed(syncFailResult);
                }

            }

        }
    }

    private void sendFilesToServer() {
        if (sendingFilesCount < fileColAndIDPojos.size()) {
            FileColAndIDPojo fileColAndIDPojo = fileColAndIDPojos.get(sendingFilesCount);
            String filePath = fileColAndIDPojo.getColVal();
            final String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
            String strUserId = sessionManager.getUserDataFromSession().getUserID();
            new FileUploader(context, fileName, strUserId, "SyncOperation", false, "BHARGO", new FileUploader.OnImageUploaded() {
                @Override
                public void response(String url) {
                    //update in db
                    if (url.contains("http")) {
                        improveDataBase.updateByValues(fileColAndIDPojo.getTableName(),
                                new String[]{fileColAndIDPojo.getColName()}, new String[]{url},
                                new String[]{"rowid"}, new String[]{fileColAndIDPojo.getRowId()});
                        //send next image in current record
                        sendingFilesCount++;
                        handSendDataFiles.sendEmptyMessage(0);
                    } else {
                        //send next record and skip current record because file sending failed
                        sendingIssues.add(sendDataCount + ")Record: File Sending Failed!" + "\n");
                        handSendDataMainAndSubFormTable.sendEmptyMessage(0);
                    }

                }
            }).execute(filePath);
        } else {
            // showProgressDialog(context, "Please Wait! Data Sending...");
            sendMainAndSubFromDataToServer();
        }
    }
    public static String replaceWithUnderscore(String str) {

        str = str.replace(" ", "_");

        return str;
    }
    private void sendMainAndSubFromDataToServer() {
        try {
            updateTablePojoList = new ArrayList<>();
            String tableName = appDetails.getTableName();
            List<String> ll_data = ll_senddataRecords.get(sendDataCount);
            //"Trans_Date"0,"Is_Active"1,"SerialNo"2,"OrganisationID"3,"CreatedUserID"4,"SubmittedUserID"5,
            // "DistributionID"6, "IMEI"7,"FormName"8,"SubFormName"9,"AppVersion"10,"SyncStatus"11, "UserID"12,
            // "PostID"13, "IsCheckList"14, "CheckListNames"15,"offlineJSON"
            boolean fromServer = false;
            JSONObject mainObject = new JSONObject();
            mainObject.put("OrgId", sessionManager.getOrgIdFromSession());
            mainObject.put("PageName", appDetails.getAppName());
//            mainObject.put("PageName",ll_data.get(8));
            mainObject.put("CreatedUserID", ll_data.get(4));
            mainObject.put("PostId", ll_data.get(13));
            mainObject.put("SubmittedUserPostID", ll_data.get(13));
            mainObject.put("SubmittedUserID", ll_data.get(5));
            mainObject.put("DistributionID", ll_data.get(6));
            mainObject.put("IMEI", ll_data.get(7));
            mainObject.put("IsCheckList", "false");
            mainObject.put("ChecklistNames", new JSONArray());//[]
            mainObject.put("IfautoincrementControls", String.valueOf(isAutoNumbersAvaliable));
//            mainObject.put("AutoIncrementControl", new JSONArray());//[]
            mainObject.put("OperationType", "");
            mainObject.put("TableName", tableName);
            mainObject.put("insertColumns", new JSONArray());//[]
            mainObject.put("UpdateColumns", new JSONArray());//[]
            mainObject.put("tableSettingsType", "Create New Table");
            /*mainObject.put("tableSettingsType", ll_data.get(17));
            mainObject.put("TypeofSubmission", ll_data.get(18));*/
            if (ll_data.get(16) != null && ll_data.get(16).equalsIgnoreCase("true")) {
                fromServer = true;
                mainObject.put("TransID", ll_data.get(0));
                mainObject.put("Action", "Update");
                mainObject.put("UserId", ll_data.get(4));
                mainObject.put("subFormInMainForm", !xmlSubFormInMainForm.equalsIgnoreCase(""));
            }
            //DataFields:MainTable
            String transID_MainTableVal = ll_data.get(0);

            TableNameAndColsFromDOPojo tableNameAndCols_mainTable = tableNameAndColsList.get(0);
            List<List<String>> l_data = improveDataBase.getTableColDataByCond(tableNameAndCols_mainTable.getTableName(),
                    "rowid," + tableNameAndCols_mainTable.getCols(),
                    new String[]{AppConstants.Trans_id}, new String[]{transID_MainTableVal});
            JSONArray dataFieldsArray = new JSONArray();
            String[] colNames = ("rowid," + tableNameAndCols_mainTable.getCols()).split(",");
            for (int i = 0; i < l_data.size(); i++) {
                List<String> colvalue = l_data.get(i);
                //For table update
                UpdateTablePojo updateTablePojo = new UpdateTablePojo();
                updateTablePojo.setTableName(tableNameAndCols_mainTable.getTableName());
                updateTablePojo.setRowidVal(colvalue.get(0));
                updateTablePojo.setColName1(AppConstants.Trans_id);
                updateTablePojo.setColVal1(colvalue.get(1));
                updateTablePojoList.add(updateTablePojo);
                //Close
                JSONObject jsonObject = new JSONObject();
                for (int j = 1; j < colvalue.size(); j++) {
                    if (!colNames[j].contains(AppConstants.Trans_id)) {
                        if(isAutoNumberControl(colNames[j])){
                            JSONObject jsonArrayAutoNumber = new JSONObject(ll_data.get(20));
                            jsonObject.put("AutoIncrementControl",jsonArrayAutoNumber);
                        }else{
                            jsonObject.put(colNames[j], colvalue.get(j));
                        }
                    }
                }
                dataFieldsArray.put(jsonObject);
            }
            mainObject.put("DataFields", dataFieldsArray);//[]
            //SubForm
            JSONArray subFormArray = new JSONArray();
            for (int i = 0; i < tableNameAndColsList.size(); i++) {
                TableNameAndColsFromDOPojo subformTab_cols = tableNameAndColsList.get(i);
                if (subformTab_cols.getType().equals("S") && improveDataBase.tableExists(subformTab_cols.getTableName())) {
                    String subFormTableName = subformTab_cols.getTableName();
                    String from_server_subformtablecol = "Bhargo_FromServer";
                    String is_active_subformtablecol = "Bhargo_Is_Active";
                    JSONObject subFormObj = new JSONObject();
                    subFormObj.put("SubFormName", subformTab_cols.getFormName());
                    subFormObj.put("TableName", subFormTableName);
                    subFormObj.put("IsCheckList", "false");
                    subFormObj.put("ChecklistNames", new JSONArray());//[]
                    subFormObj.put("DeleteRowStatus", "false");
                    subFormObj.put("DeleteRowIds", "");
                    subFormObj.put("AutoIncrementControl", new JSONArray());//[]
                    //DataFields:MainTable
                    List<List<String>> l_subformdata = improveDataBase.getTableColDataByCond(
                            subformTab_cols.getTableName(),
                            "rowid," + from_server_subformtablecol + "," + AppConstants.Trans_id + "," + is_active_subformtablecol + ", AutoNumberJson ," + subformTab_cols.getCols() ,
                            new String[]{AppConstants.Ref_Trans_id}, new String[]{transID_MainTableVal});

                    JSONArray dataFieldsSubFormArray = new JSONArray();
                    String[] subFormColNames = ("rowid," + from_server_subformtablecol + "," + AppConstants.Trans_id + "," + is_active_subformtablecol + ", AutoNumberJson ," + subformTab_cols.getCols()).split(",");
                    for (int x = 0; x < l_subformdata.size(); x++) {
                        List<String> subcolvalue = l_subformdata.get(x);
                        //For table update
                        UpdateTablePojo updateTablePojo = new UpdateTablePojo();
                        updateTablePojo.setTableName(subformTab_cols.getTableName());
                        updateTablePojo.setRowidVal(subcolvalue.get(0));
                        updateTablePojo.setColName1(AppConstants.Trans_id);
                        updateTablePojo.setColVal1(subcolvalue.get(2));
                        updateTablePojoList.add(updateTablePojo);
                        //Close
                        JSONObject jsonObject = new JSONObject();

                        for (int y = 1; y < subcolvalue.size(); y++) {
                            if (y == 1) {
                                //Skip FromServer
                            } else if (y == 2 || y == 4) {
                                //Skip trans_id (or) skip Autonumber Json
                            } else if (y == 3) {
                                //Is Active
                                if (subcolvalue.get(1) != null && subcolvalue.get(1).equals("true")) {
                                    jsonObject.put("Is_Active", subcolvalue.get(y));
                                }
                            } else if (subFormColNames[y].equalsIgnoreCase("Bhargo_Ref_TransID")) {
                                //skip ref_trans_id
                            } else if (isAutoNumberControl(subFormColNames[y])) {
                                //skip MainForm AutoControl in Subform
                            } else {
                                //remaining cols
                                if(isAutoNumberControl(subformTab_cols.getFormName(),subFormColNames[y])){
                                    JSONObject jsonArrayAutoNumber = new JSONObject(subcolvalue.get(4));
                                    jsonObject.put("AutoIncrementControl",jsonArrayAutoNumber);
                                }else{
                                    jsonObject.put(subFormColNames[y], subcolvalue.get(y));
                                }
                            }
                        }
                        if (subcolvalue.get(1) != null && subcolvalue.get(1).equals("true")) {
                            if (subcolvalue.get(2) != null && subcolvalue.get(2).startsWith("T")) {
                                jsonObject.put("TransID", subcolvalue.get(2));
                            } else {
                                jsonObject.put("TransID", "");
                            }
                            jsonObject.put("TransID", subcolvalue.get(2));
                        } else {
                            // jsonObject.put("TransID", "");
                        }
                        if(l_subformdata.size()>0){
                        dataFieldsSubFormArray.put(jsonObject);
                        }
                    }

                    subFormObj.put("MainFormAutoControlInSubform", String.valueOf(checkMainFormAutoNumberInSubform(subFormColNames)));//Mainform Autonumber in Subform

                    subFormObj.put("DataFields", dataFieldsSubFormArray);//[]
                    subFormObj.put("TypeofSubmission", "");
                    subFormObj.put("insertColumns", new JSONArray());//[]
                    subFormObj.put("IfautoincrementControls", String.valueOf(isSubFormAutoNumbersExists(subformTab_cols.getFormName())));
//                    subFormObj.put("AutoIncrementControl", new JSONArray());//[]
                    subFormObj.put("UpdateColumns", new JSONArray());//[]
                    subFormObj.put("tableSettingsType", "Create New Table");
                       subFormArray.put(subFormObj);
                }
            }
            mainObject.put("SubFormDataFields", subFormArray);//[]
            System.out.println("mainObject: " + mainObject);
            //send obj to server

            sendDataToServer(mainObject, fromServer);
        } catch (Exception e) {
            sendingIssues.add(sendDataCount + ")Record:" + e.getMessage());
            handSendDataMainAndSubFormTable.sendEmptyMessage(0);
        }
    }

    private boolean checkMainFormAutoNumberInSubform(String[] subFormColNames) {
        try {
            if (subFormColNames == null || subFormColNames.length == 0) {
                return false;
            }
            for (int i = 0; i < subFormColNames.length; i++) {
                String colName = subFormColNames[i];
                if (colName.equalsIgnoreCase(jArrayAutoIncementControls.getJSONObject(0).getString("ControlName"))) {
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;

    }

    private boolean isSubFormAutoNumbersExists(String formName) {
        boolean isSubFormAutoNumbersExists= false;
        if(subFormsAutoNumberStatusMap.size()>0){
            isSubFormAutoNumbersExists = subFormsAutoNumberStatusMap.get(formName);
        }
        return  isSubFormAutoNumbersExists;
    }


    private int getPositionOfColumn(String colName,String subFormName) {
        int pos = -1;
        try{
        for (int i = 0; i < subFormsAutoNumberArraysMap.get(subFormName).length(); i++) {
          JSONObject jsonObject = subFormsAutoNumberArraysMap.get(subFormName).getJSONObject(i);
          if(jsonObject.getString("ControlName").equalsIgnoreCase(colName)){
                  return i;
               }
        }
        }catch (Exception e){
            Log.d(TAG, "getPositionOfColumn: "+e.toString());
        }
        return pos;
    }

    public void sendDataToServer(JSONObject mainObject, boolean fromServer) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject) jsonParser.parse(mainObject.toString());
        Call<ResponseBody> call;
        if (fromServer) {
            call = getServices.sendEditFormData(sessionManager.getAuthorizationTokenId(),jo);
        } else {

            call = getServices.sendFormData_offline(sessionManager.getAuthorizationTokenId(),jo);
        }
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<ResponseBody> serverResponse, Response<ResponseBody> response) {
                try {
                    if (response.body() != null) {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        ImproveHelper.improveLog(TAG, "sendDataToServerResponse is : ", new Gson().toJson(response.body()));
                        String resultStatus = jsonObject.getString("Status");
                        String message = jsonObject.getString("Message");
                        String sno = jsonObject.has("Sno") ? jsonObject.getString("Sno") : "";
                        if (resultStatus.equalsIgnoreCase("200")) {
                            updateDBAndAppsList(sno, message);
                        } else if (resultStatus.equalsIgnoreCase("100")) {
                            sendingIssues.add(sendDataCount + ")Record:" + message + "\n");
                            handSendDataMainAndSubFormTable.sendEmptyMessage(0);
                        } else {
                            sendingIssues.add(sendDataCount + ")Record:" + message + "\n");
                            handSendDataMainAndSubFormTable.sendEmptyMessage(0);
                        }
                    } else {
                        sendingIssues.add(sendDataCount + ")Record:" + "server data is null" + "\n");
                        handSendDataMainAndSubFormTable.sendEmptyMessage(0);
                    }
                } catch (Exception e) {
                    Log.d(TAG, "onResponse: " + e);
                    sendingIssues.add(sendDataCount + ")Record:" + e.getMessage() + "\n");
                    handSendDataMainAndSubFormTable.sendEmptyMessage(0);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "sendDataToServerResponse: " + t);
                sendingIssues.add(sendDataCount + ")Record:" + t.getMessage() + "\n");
                handSendDataMainAndSubFormTable.sendEmptyMessage(0);
            }
        });
    }

    private void updateDBAndAppsList(String sno, String message) {
        //updateTablePojoList, //offlineHybridAppListPojoData,//selectedPostion
        //1.delete record in DB
        for (int i = 0; i < updateTablePojoList.size(); i++) {
            UpdateTablePojo updateTablePojo = updateTablePojoList.get(i);
            improveDataBase.deleteByValues(updateTablePojo.getTableName(),
                    new String[]{"rowid"}, new String[]{updateTablePojo.getRowidVal()});
        }

        handSendDataMainAndSubFormTable.sendEmptyMessage(0);
    }

    public void getTableNameAndColsFromxmlDF(String mainTableName, String xmlDesignFormat,String appName) {
        tableNameAndColsForFilesPojoList = new ArrayList<>();
        tableNameAndColsList = new ArrayList<>();
        HashMap<String,Node> subformNodes= new HashMap<>();
        try {
            String response = xmlDesignFormat;
            if (response.startsWith("N")) {
                response = response.substring(1);
            }
            response = response.replaceAll("&(?!amp;)", "&amp;");
            response = response.replaceAll("CNAME", "CDATA");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response)));
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getChildNodes().item(0).getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (nNode.getNodeName().trim().equalsIgnoreCase("FormControls")) {
                        List<String> fileCols_mainTable = new ArrayList<>();
                        NodeList ControleNodeList = nNode.getChildNodes();
                        for (int j = 0; j < ControleNodeList.getLength(); j++) {
                            Node ControlNode = ControleNodeList.item(j);
                            if (!ControlNode.getNodeName().contentEquals("RTL")) {
                                String controlType = ControlNode.getNodeName().trim();
                                String controlID = ((Element) ControlNode).getAttribute("controlName").trim();
                                //controlID & controlName both are same
                                if (controlType.equals(AppConstants.CONTROL_TYPE_CAMERA) ||
                                        controlType.equals(AppConstants.CONTROL_TYPE_FILE_BROWSING) ||
                                        controlType.equals(AppConstants.CONTROL_TYPE_VIDEO_RECORDING) ||
                                        controlType.equals(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                                    fileCols_mainTable.add(controlID);
                                } else if (controlType.equals(AppConstants.CONTROL_TYPE_SUBFORM)) {
                                    JSONArray jArrayAutoIncementControlsSubForm = new JSONArray();
                                    if (((Element) ControlNode).getElementsByTagName("SubFormControls").getLength() > 0) {
                                        List<String> subfrom_filecols_maintable = new ArrayList<>();
                                        Node subFormNode = ((Element) ControlNode).getElementsByTagName("SubFormControls").item(0);
                                        subformNodes.put(controlID,subFormNode);
                                        NodeList subFormControlNodeList = subFormNode.getChildNodes();
                                        for (int k = 0; k < subFormControlNodeList.getLength(); k++) {
                                            Node subFormControlNode = subFormControlNodeList.item(k);
                                            if (subFormControlNode.getNodeType() == Node.ELEMENT_NODE) {
                                                String innerControlType = subFormControlNode.getNodeName().trim();
                                                String innerControlID = ((Element) subFormControlNode).getAttribute("controlName").trim();
                                                //controlID & controlName both are same
                                                if (innerControlType.equals(AppConstants.CONTROL_TYPE_CAMERA) ||
                                                        innerControlType.equals(AppConstants.CONTROL_TYPE_FILE_BROWSING) ||
                                                        innerControlType.equals(AppConstants.CONTROL_TYPE_VIDEO_RECORDING) ||
                                                        innerControlType.equals(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                                                    subfrom_filecols_maintable.add(innerControlID);
                                                }else  if (innerControlType.equals(AppConstants.CONTROL_TYPE_AUTO_GENERATION)) {
                                                JSONObject jsonObject = new JSONObject();
                                                jsonObject.put("ControlName", ((Element) subFormControlNode).getAttribute("controlName").trim());
                                                NodeList ControlNodeList = subFormControlNode.getChildNodes();
                                                for (int n = 0; n < ControlNodeList.getLength(); n++) {
                                                    Node item = ControlNodeList.item(n);
                                                    if (item.getNodeName().equalsIgnoreCase("Prefix")) {
                                                        jsonObject.put("Prefix", ((Element) item).getAttribute("name").trim());
                                                    } else if (item.getNodeName().equalsIgnoreCase("Suffix")) {
                                                        jsonObject.put("Suffix", ((Element) item).getAttribute("name").trim());
                                                    } else if (item.getNodeName().equalsIgnoreCase("Suffix1")) {
                                                        jsonObject.put("Suffix1", ((Element) item).getAttribute("name").trim());
                                                    }
                                                    subFormsAutoNumberStatusMap.put(controlID, true);
                                                }
                                                    jArrayAutoIncementControlsSubForm.put(jsonObject);
                                                    subFormsAutoNumberArraysMap.put(controlID, jArrayAutoIncementControlsSubForm);
                                            }
                                            }
                                        }
                                        if (subfrom_filecols_maintable.size() > 0) {
                                            String columnsSeperated = TextUtils.join(",", subfrom_filecols_maintable);
                                            TableNameAndColsFromDOPojo tableNameAndColsForFilesPojo = new TableNameAndColsFromDOPojo();
                                            tableNameAndColsForFilesPojo.setFormName(controlID);
                                            tableNameAndColsForFilesPojo.setTableName(getSubformTableName(controlID));
                                            tableNameAndColsForFilesPojo.setCols(columnsSeperated);
                                            tableNameAndColsForFilesPojo.setType("S");
                                            tableNameAndColsForFilesPojoList.add(tableNameAndColsForFilesPojo);
                                        }
                                    }
                                } else if (controlType.equals(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {
                                    JSONArray jArrayAutoIncementControlsSubForm = new JSONArray();
                                    if (((Element) ControlNode).getElementsByTagName("GridFormControls").getLength() > 0) {
                                        List<String> subfrom_filecols_maintable = new ArrayList<>();
                                        Node subFormNode = ((Element) ControlNode).getElementsByTagName("GridFormControls").item(0);
                                        subformNodes.put(controlID,subFormNode);
                                        NodeList subFormControlNodeList = subFormNode.getChildNodes();
                                        for (int k = 0; k < subFormControlNodeList.getLength(); k++) {
                                            Node subFormControlNode = subFormControlNodeList.item(k);
                                            if (subFormControlNode.getNodeType() == Node.ELEMENT_NODE) {
                                                String innerControlType = subFormControlNode.getNodeName().trim();
                                                String innerControlID = ((Element) subFormControlNode).getAttribute("controlName").trim();
                                                //controlID & controlName both are same
                                                if (innerControlType.equals(AppConstants.CONTROL_TYPE_CAMERA) ||
                                                        innerControlType.equals(AppConstants.CONTROL_TYPE_FILE_BROWSING) ||
                                                        innerControlType.equals(AppConstants.CONTROL_TYPE_VIDEO_RECORDING) ||
                                                        innerControlType.equals(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                                                    subfrom_filecols_maintable.add(innerControlID);
                                                }else  if (innerControlType.equals(AppConstants.CONTROL_TYPE_AUTO_GENERATION)) {
                                                    JSONObject jsonObject = new JSONObject();
                                                    jsonObject.put("ControlName", ((Element) subFormControlNode).getAttribute("controlName").trim());
                                                    NodeList ControlNodeList = subFormControlNode.getChildNodes();
                                                    for (int n = 0; n < ControlNodeList.getLength(); n++) {
                                                        Node item = ControlNodeList.item(n);
                                                        if (item.getNodeName().equalsIgnoreCase("Prefix")) {
                                                            jsonObject.put("Prefix", ((Element) item).getAttribute("name").trim());
                                                        } else if (item.getNodeName().equalsIgnoreCase("Suffix")) {
                                                            jsonObject.put("Suffix", ((Element) item).getAttribute("name").trim());
                                                        } else if (item.getNodeName().equalsIgnoreCase("Suffix1")) {
                                                            jsonObject.put("Suffix1", ((Element) item).getAttribute("name").trim());
                                                        }
                                                        subFormsAutoNumberStatusMap.put(controlID, true);
                                                    }
                                                    jArrayAutoIncementControlsSubForm.put(jsonObject);
                                                    subFormsAutoNumberArraysMap.put(controlID, jArrayAutoIncementControlsSubForm);
                                                }
                                            }
                                        }
                                        if (subfrom_filecols_maintable.size() > 0) {
                                            String columnsSeperated = TextUtils.join(",", subfrom_filecols_maintable);
                                            TableNameAndColsFromDOPojo tableNameAndColsForFilesPojo = new TableNameAndColsFromDOPojo();
                                            tableNameAndColsForFilesPojo.setFormName(controlID);
                                            tableNameAndColsForFilesPojo.setTableName(getSubformTableName(controlID));
                                            tableNameAndColsForFilesPojo.setCols(columnsSeperated);
                                            tableNameAndColsForFilesPojo.setType("S");
                                            tableNameAndColsForFilesPojoList.add(tableNameAndColsForFilesPojo);
                                        }
                                    }
                                }  else if (controlType.equals(AppConstants.CONTROL_TYPE_SECTION)) {
                                    if (((Element) ControlNode).getElementsByTagName("SectionControls").getLength() > 0) {

                                        Node sectionNode = ((Element) ControlNode).getElementsByTagName("SectionControls").item(0);
                                        NodeList sectionControlNodeList = sectionNode.getChildNodes();
                                        for (int ks = 0; ks < sectionControlNodeList.getLength(); ks++) {
                                            Node sectionControlNode = sectionControlNodeList.item(ks);
                                            if (sectionControlNode.getNodeType() == Node.ELEMENT_NODE) {
                                                String sectionControlType = sectionControlNode.getNodeName().trim();
                                                String sectionControlID = ((Element) sectionControlNode).getAttribute("controlName").trim();
                                                //controlID & controlName both are same
                                                if (sectionControlType.equals(AppConstants.CONTROL_TYPE_CAMERA) ||
                                                        sectionControlType.equals(AppConstants.CONTROL_TYPE_FILE_BROWSING) ||
                                                        sectionControlType.equals(AppConstants.CONTROL_TYPE_VIDEO_RECORDING) ||
                                                        sectionControlType.equals(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                                                    fileCols_mainTable.add(sectionControlID);
                                                }else if (sectionControlType.equals(AppConstants.CONTROL_TYPE_SUBFORM)) {
                                                        JSONArray jArrayAutoIncementControlsSubForm = new JSONArray();
                                                        if (((Element) ControlNode).getElementsByTagName("SubFormControls").getLength() > 0) {
                                                            List<String> subfrom_filecols_maintable = new ArrayList<>();
                                                            Node subFormNode = ((Element) ControlNode).getElementsByTagName("SubFormControls").item(0);
                                                            subformNodes.put(controlID,subFormNode);
                                                            NodeList subFormControlNodeList = subFormNode.getChildNodes();
                                                            for (int k = 0; k < subFormControlNodeList.getLength(); k++) {
                                                                Node subFormControlNode = subFormControlNodeList.item(k);
                                                                if (subFormControlNode.getNodeType() == Node.ELEMENT_NODE) {
                                                                    String innerControlType = subFormControlNode.getNodeName().trim();
                                                                    String innerControlID = ((Element) subFormControlNode).getAttribute("controlName").trim();
                                                                    //controlID & controlName both are same
                                                                    if (innerControlType.equals(AppConstants.CONTROL_TYPE_CAMERA) ||
                                                                            innerControlType.equals(AppConstants.CONTROL_TYPE_FILE_BROWSING) ||
                                                                            innerControlType.equals(AppConstants.CONTROL_TYPE_VIDEO_RECORDING) ||
                                                                            innerControlType.equals(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                                                                        subfrom_filecols_maintable.add(innerControlID);
                                                                    }else  if (innerControlType.equals(AppConstants.CONTROL_TYPE_AUTO_GENERATION)) {
                                                                        JSONObject jsonObject = new JSONObject();
                                                                        jsonObject.put("ControlName", ((Element) subFormControlNode).getAttribute("controlName").trim());
                                                                        NodeList ControlNodeList = subFormControlNode.getChildNodes();
                                                                        for (int n = 0; n < ControlNodeList.getLength(); n++) {
                                                                            Node item = ControlNodeList.item(n);
                                                                            if (item.getNodeName().equalsIgnoreCase("Prefix")) {
                                                                                jsonObject.put("Prefix", ((Element) item).getAttribute("name").trim());
                                                                            } else if (item.getNodeName().equalsIgnoreCase("Suffix")) {
                                                                                jsonObject.put("Suffix", ((Element) item).getAttribute("name").trim());
                                                                            } else if (item.getNodeName().equalsIgnoreCase("Suffix1")) {
                                                                                jsonObject.put("Suffix1", ((Element) item).getAttribute("name").trim());
                                                                            }
                                                                            subFormsAutoNumberStatusMap.put(sectionControlID, true);
                                                                        }
                                                                        jArrayAutoIncementControlsSubForm.put(jsonObject);
                                                                        subFormsAutoNumberArraysMap.put(sectionControlID, jArrayAutoIncementControlsSubForm);
                                                                    }
                                                                }
                                                            }
                                                            if (subfrom_filecols_maintable.size() > 0) {
                                                                String columnsSeperated = TextUtils.join(",", subfrom_filecols_maintable);
                                                                TableNameAndColsFromDOPojo tableNameAndColsForFilesPojo = new TableNameAndColsFromDOPojo();
                                                                tableNameAndColsForFilesPojo.setFormName(sectionControlID);
                                                                tableNameAndColsForFilesPojo.setTableName(getSubformTableName(sectionControlID));
                                                                tableNameAndColsForFilesPojo.setCols(columnsSeperated);
                                                                tableNameAndColsForFilesPojo.setType("S");
                                                                tableNameAndColsForFilesPojoList.add(tableNameAndColsForFilesPojo);
                                                            }
                                                        }
                                                }else if (sectionControlType.equals(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {
                                                    JSONArray jArrayAutoIncementControlsSubForm = new JSONArray();
                                                    if (((Element) ControlNode).getElementsByTagName("GridFormControls").getLength() > 0) {
                                                        List<String> subfrom_filecols_maintable = new ArrayList<>();
                                                        Node subFormNode = ((Element) ControlNode).getElementsByTagName("GridFormControls").item(0);
                                                        subformNodes.put(controlID,subFormNode);
                                                        NodeList subFormControlNodeList = subFormNode.getChildNodes();
                                                        for (int k = 0; k < subFormControlNodeList.getLength(); k++) {
                                                            Node subFormControlNode = subFormControlNodeList.item(k);
                                                            if (subFormControlNode.getNodeType() == Node.ELEMENT_NODE) {
                                                                String innerControlType = subFormControlNode.getNodeName().trim();
                                                                String innerControlID = ((Element) subFormControlNode).getAttribute("controlName").trim();
                                                                //controlID & controlName both are same
                                                                if (innerControlType.equals(AppConstants.CONTROL_TYPE_CAMERA) ||
                                                                        innerControlType.equals(AppConstants.CONTROL_TYPE_FILE_BROWSING) ||
                                                                        innerControlType.equals(AppConstants.CONTROL_TYPE_VIDEO_RECORDING) ||
                                                                        innerControlType.equals(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                                                                    subfrom_filecols_maintable.add(innerControlID);
                                                                }else  if (innerControlType.equals(AppConstants.CONTROL_TYPE_AUTO_GENERATION)) {
                                                                    JSONObject jsonObject = new JSONObject();
                                                                    jsonObject.put("ControlName", ((Element) subFormControlNode).getAttribute("controlName").trim());
                                                                    NodeList ControlNodeList = subFormControlNode.getChildNodes();
                                                                    for (int n = 0; n < ControlNodeList.getLength(); n++) {
                                                                        Node item = ControlNodeList.item(n);
                                                                        if (item.getNodeName().equalsIgnoreCase("Prefix")) {
                                                                            jsonObject.put("Prefix", ((Element) item).getAttribute("name").trim());
                                                                        } else if (item.getNodeName().equalsIgnoreCase("Suffix")) {
                                                                            jsonObject.put("Suffix", ((Element) item).getAttribute("name").trim());
                                                                        } else if (item.getNodeName().equalsIgnoreCase("Suffix1")) {
                                                                            jsonObject.put("Suffix1", ((Element) item).getAttribute("name").trim());
                                                                        }
                                                                        subFormsAutoNumberStatusMap.put(sectionControlID, true);
                                                                    }
                                                                    jArrayAutoIncementControlsSubForm.put(jsonObject);
                                                                    subFormsAutoNumberArraysMap.put(sectionControlID, jArrayAutoIncementControlsSubForm);
                                                                }
                                                            }
                                                        }
                                                        if (subfrom_filecols_maintable.size() > 0) {
                                                            String columnsSeperated = TextUtils.join(",", subfrom_filecols_maintable);
                                                            TableNameAndColsFromDOPojo tableNameAndColsForFilesPojo = new TableNameAndColsFromDOPojo();
                                                            tableNameAndColsForFilesPojo.setFormName(sectionControlID);
                                                            tableNameAndColsForFilesPojo.setTableName(getSubformTableName(sectionControlID));
                                                            tableNameAndColsForFilesPojo.setCols(columnsSeperated);
                                                            tableNameAndColsForFilesPojo.setType("S");
                                                            tableNameAndColsForFilesPojoList.add(tableNameAndColsForFilesPojo);
                                                        }
                                                    }
                                                }else  if (sectionControlType.equals(AppConstants.CONTROL_TYPE_AUTO_GENERATION)) {
                                                    JSONObject jsonObject = new JSONObject();
                                                    jsonObject.put("ControlName", ((Element) sectionControlNode).getAttribute("controlName").trim());
                                                    NodeList ControlNodeList = sectionControlNode.getChildNodes();
                                                    for (int n = 0; n < ControlNodeList.getLength(); n++) {
                                                        Node item = ControlNodeList.item(n);
                                                        if (item.getNodeName().equalsIgnoreCase("Prefix")) {
                                                            jsonObject.put("Prefix", ((Element) item).getAttribute("name").trim());
                                                        } else if (item.getNodeName().equalsIgnoreCase("Suffix")) {
                                                            jsonObject.put("Suffix", ((Element) item).getAttribute("name").trim());
                                                        } else if (item.getNodeName().equalsIgnoreCase("Suffix1")) {
                                                            jsonObject.put("Suffix1", ((Element) item).getAttribute("name").trim());
                                                        }
                                                    }
                                                    jArrayAutoIncementControls.put(jsonObject);
                                                }
                                            }
                                        }
                                      /*  if (fileCols_mainTable.size() > 0) {
                                            String columnsSeperated = TextUtils.join(",", fileCols_mainTable);
                                            TableNameAndColsFromDOPojo tableNameAndColsForFilesPojo = new TableNameAndColsFromDOPojo();
                                            tableNameAndColsForFilesPojo.setTableName(mainTableName);
                                            tableNameAndColsForFilesPojo.setCols(columnsSeperated);
                                            tableNameAndColsForFilesPojo.setFormName(appName);
                                            tableNameAndColsForFilesPojo.setType("M");
                                            tableNameAndColsForFilesPojoList.add(tableNameAndColsForFilesPojo);
                                        }*/
                                    }

                                }else  if (controlType.equals(AppConstants.CONTROL_TYPE_AUTO_GENERATION)) {
                                    isAutoNumbersAvaliable = true;
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("ControlName", ((Element) ControlNode).getAttribute("controlName").trim());
                                    NodeList ControlNodeList = ControlNode.getChildNodes();
                                    for (int n = 0; n < ControlNodeList.getLength(); n++) {

                                        Node item = ControlNodeList.item(n);
                                        if (item.getNodeName().equalsIgnoreCase("Prefix")) {
                                            jsonObject.put("Prefix", ((Element) item).getAttribute("name").trim());
                                        } else if (item.getNodeName().equalsIgnoreCase("Suffix")) {
                                            jsonObject.put("Suffix", ((Element) item).getAttribute("name").trim());
                                        } else if (item.getNodeName().equalsIgnoreCase("Suffix1")) {
                                            jsonObject.put("Suffix1", ((Element) item).getAttribute("name").trim());
                                        }
//                                        jArrayAutoIncementControls.put(jsonObject);
                                    }
                                    jArrayAutoIncementControls.put(jsonObject);
                                }
                            }
                        }
                        //main Table With SubForm Cols
                        for (int s = 0; s < nList.getLength(); s++) {
                            Node nNode_ = nList.item(s);
                            if (nNode_.getNodeType() == Node.ELEMENT_NODE) {
                                if (nNode_.getNodeName().trim().equalsIgnoreCase("TableSettings")) {
                                    NodeList ControlNodeList_ = nNode_.getChildNodes();
                                    if (ControlNodeList_.getLength() > 0) {
                                        for (int j = 0; j < ControlNodeList_.getLength(); j++) {
                                            Node ControlNode_ = ControlNodeList_.item(j);
                                            if (ControlNode_.getNodeName().equalsIgnoreCase("MainTableSettings")) {
                                                if (((Element) ControlNode_).getAttribute("option").trim().equalsIgnoreCase("new")) {
                                                    xmlSubFormInMainForm = ((Element) ControlNode_).getAttribute("subFormInMainForm").trim();
                                                }
                                                if(xmlSubFormInMainForm!=null && !xmlSubFormInMainForm.equalsIgnoreCase("")){
                                                    Node subNode = subformNodes.get(xmlSubFormInMainForm);
                                                    List<String> listOfTableCols = getList_Table_Columns(ControlNode_);
                                                    if (listOfTableCols != null) {
                                                        NodeList subFormControlNodeList = subNode.getChildNodes();
                                                        for (int k = 0; k < subFormControlNodeList.getLength(); k++) {
                                                            Node subFormControlNode = subFormControlNodeList.item(k);
                                                            if (subFormControlNode.getNodeType() == Node.ELEMENT_NODE) {
                                                                String innerControlType = subFormControlNode.getNodeName().trim();
                                                                String innerControlID = ((Element) subFormControlNode).getAttribute("controlName").trim();
                                                                //controlID & controlName both are same
                                                                if (listOfTableCols.contains(innerControlID) && (innerControlType.equals(AppConstants.CONTROL_TYPE_CAMERA) ||
                                                                        innerControlType.equals(AppConstants.CONTROL_TYPE_FILE_BROWSING) ||
                                                                        innerControlType.equals(AppConstants.CONTROL_TYPE_VIDEO_RECORDING) ||
                                                                        innerControlType.equals(AppConstants.CONTROL_TYPE_VOICE_RECORDING))) {
                                                                    fileCols_mainTable.add(innerControlID);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (fileCols_mainTable.size() > 0) {
                            String columnsSeperated = TextUtils.join(",", fileCols_mainTable);
                            TableNameAndColsFromDOPojo tableNameAndColsForFilesPojo = new TableNameAndColsFromDOPojo();
                            tableNameAndColsForFilesPojo.setTableName(mainTableName);
                            tableNameAndColsForFilesPojo.setCols(columnsSeperated);
                            tableNameAndColsForFilesPojo.setFormName(appName);
                            tableNameAndColsForFilesPojo.setType("M");
                            tableNameAndColsForFilesPojoList.add(tableNameAndColsForFilesPojo);
                        }
                    } else if (nNode.getNodeName().trim().equalsIgnoreCase("TableSettings")) {
                        NodeList ControlNodeList = nNode.getChildNodes();
                        if (ControlNodeList.getLength() > 0) {
                            for (int j = 0; j < ControlNodeList.getLength(); j++) {
                                Node ControlNode = ControlNodeList.item(j);
                                if (ControlNode.getNodeName().equalsIgnoreCase("MainTableSettings")) {
                                    if (((Element) ControlNode).getAttribute("option").trim().equalsIgnoreCase("new")) {
                                        xmlSubFormInMainForm = ((Element) ControlNode).getAttribute("subFormInMainForm").trim();
                                    }
                                    List<String> listOfTableCols = getList_Table_Columns(ControlNode);
                                    if (listOfTableCols != null) {
                                        //Trans_ID
                                       /* if(listOfTableCols.contains(appName+"_Trans_Id")){
                                            for (int k = 0; k < listOfTableCols.size(); k++) {
                                                if(listOfTableCols.get(k).equals(appName+"_Trans_Id")){
                                                    listOfTableCols.set(k,mainTableName+"_Trans_ID");
                                                    break;
                                                }
                                            }
                                        }*/
                                        String maincolSeperated = TextUtils.join(",", listOfTableCols);
                                        TableNameAndColsFromDOPojo mainColsPojo = new TableNameAndColsFromDOPojo();
                                        mainColsPojo.setTableName(mainTableName);
                                        mainColsPojo.setFormName(appName);
                                        mainColsPojo.setCols(maincolSeperated);
                                        mainColsPojo.setType("M");
                                        tableNameAndColsList.add(mainColsPojo);
                                    }
                                } else if (ControlNode.getNodeName().equalsIgnoreCase("SubFormTableSettings")) {
                                    String subFormName = ((Element) ControlNode).getAttribute("name").trim();
                                    List<String> listOfTableCols = getList_Table_Columns(ControlNode);
                                    //SubForm Table cols
                                    if (listOfTableCols != null) {
                                        String subformcolSeperated = TextUtils.join(",", listOfTableCols);
                                        TableNameAndColsFromDOPojo tableNameAndColsForFilesPojo = new TableNameAndColsFromDOPojo();
                                        tableNameAndColsForFilesPojo.setFormName(subFormName);
                                        tableNameAndColsForFilesPojo.setTableName(getSubformTableName(subFormName));
                                        tableNameAndColsForFilesPojo.setCols(subformcolSeperated);
                                        tableNameAndColsForFilesPojo.setType("S");
                                        tableNameAndColsList.add(tableNameAndColsForFilesPojo);
                                    }
                                }
                            }
                        }
                    } else if (nNode.getNodeName().trim().equalsIgnoreCase("AdvanceManagement")) {
                        NodeList advanceManagementChildList = nNode.getChildNodes();
                        for (int advanceManagementCnt = 0; advanceManagementCnt < advanceManagementChildList.getLength(); advanceManagementCnt++) {
                            Element advanceManagementElement = (Element) advanceManagementChildList.item(advanceManagementCnt);

                            if (advanceManagementElement.getNodeName().equalsIgnoreCase("OrderByColumnsIndexPage")) {
                                xmlIndexPageColumnsOrderList = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                            }
                            if (advanceManagementElement.getNodeName().equalsIgnoreCase("Order")) {
                                String order = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                if (order != null && xmlIndexPageColumnsOrderList != null) {
                                    xmlIndexPageColumnsOrder = order;
                                }
                            }

                            if (advanceManagementElement.getNodeName().equals("LazyLoading")) {
                                String lazyLoading = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                xmlIsLazyLoadingEnabled = Boolean.parseBoolean(lazyLoading);
                            }

                            if (xmlIsLazyLoadingEnabled) {
                                if (advanceManagementElement.getNodeName().equals("LazyLoadingThreshold")) {
                                    xmlLazyLoadingThreshold = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                }
                            }

                            if (advanceManagementElement.getNodeName().equals("FetchData")) {
                                xmlFetchData = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                            }

                            /*if (advanceManagementElement.getNodeName().equals("EnableViewData")) {
                                String enableViewData = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                //AppObject.setEnableViewData(Boolean.parseBoolean(enableViewData));
                            }

                            if (advanceManagementElement.getNodeName().equalsIgnoreCase("OrderByColumnsViewPage")) {
                                String orderByColumns = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                if (orderByColumns != null && !orderByColumns.contentEquals("")) {
                                    List<String> orderByViewPage = new ArrayList<>();
                                    for (int j = 0; j < orderByColumns.split(",").length; j++) {
                                        orderByViewPage.add(orderByColumns.split(",")[j]);
                                    }
                                    //AppObject.setViewPageColumnsOrder(orderByViewPage);
                                }
                            }
                            if (advanceManagementElement.getNodeName().equals("EnableEditData")) {
                                String enableEditData = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                //AppObject.setEnableEditData(Boolean.parseBoolean(enableEditData));
                            }

                            if (advanceManagementElement.getNodeName().equals("EnableDeleteData")) {
                                String enableDeleteData = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                //AppObject.setEnableDeleteData(Boolean.parseBoolean(enableDeleteData));

                            }
                            if (advanceManagementElement.getNodeName().equals("AddNewRecord")) {
                                String addNewRecord = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                //AppObject.setAddNewRecord(Boolean.parseBoolean(addNewRecord));
                            }
                            if (advanceManagementElement.getNodeName().equals("AllowOnlyOneRecord")) {
                                String allowOnlyOneRecord = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                //AppObject.setAllowOnlyOneRecord(Boolean.parseBoolean(allowOnlyOneRecord));
                            }
                            if (advanceManagementElement.getNodeName().equals("CaptionForAdd")) {
                                String captionForAdd = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                //AppObject.setCaptionForAdd(captionForAdd);
                            }*/

                        }

                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error at getTableNameAndColsFromxmlDF In:" + TAG + e);
        }
        System.out.println("tableNameAndColsForFilesPojoList:" + tableNameAndColsForFilesPojoList);
        System.out.println("tableNameAndColsList:" + tableNameAndColsList);
    }

    private List<String> getList_Table_Columns(Node ControlNode) {
        List<String> list_Table_Columns = new ArrayList<>();
        if (((Element) ControlNode).getAttribute("option").trim().equalsIgnoreCase("new")) {
            if (ControlNode.getNodeType() == Node.ELEMENT_NODE) {
                Element TableColumnsElement = (Element) ControlNode;


                NodeList nNodeChildNodes = TableColumnsElement.getChildNodes();
                for (int inNodeChildNodes = 0; inNodeChildNodes < nNodeChildNodes.getLength(); inNodeChildNodes++) {
                    Node nNodeTableColumn = nNodeChildNodes.item(inNodeChildNodes);
                    if (nNodeChildNodes.item(inNodeChildNodes).getNodeType() == Node.ELEMENT_NODE) {
                        Element settingFieldsElement = (Element) nNodeChildNodes.item(inNodeChildNodes);
                        if (settingFieldsElement.getNodeName().equals("TableColumns")) {
                            NodeList settingsChildList = nNodeTableColumn.getChildNodes();
                            for (int settingsFieldsCnt = 0; settingsFieldsCnt < settingsChildList.getLength(); settingsFieldsCnt++) {
                                if (settingsChildList.item(settingsFieldsCnt).getNodeType() == Node.ELEMENT_NODE) {
                                    Element ColumnNameElement = (Element) settingsChildList.item(settingsFieldsCnt);
                                    if (ColumnNameElement.getNodeName().equals("ColumnName")) {
                                        String TableColumnName = XMLHelper.getCharacterDataFromElement(ColumnNameElement);
                                        list_Table_Columns.add(TableColumnName);
                                    }
                                }
                            }
                            break;
                        }
                    }
                }

            }
        }
        return list_Table_Columns;
    }

    private void showProgressDialog(Context context, String msg) {
        if (pd != null && pd.isShowing()) {
            pd.setMessage(msg);
        } else {
            pd = new ProgressDialog(context);
            // pd = CustomProgressDialog.ctor(this, msg);
            // pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            pd.setMessage(msg);
            pd.setCancelable(false);
            pd.show();
        }

    }

    private void closeProgressDialog() {
        try {
            if (pd != null)
                pd.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertDataToDB(JSONArray jsonArray) {
        try {
            String tableName = appDetails.getTableName();
            //INSERT INTO TABLE_NAME (column1, column2, column3,...columnN) VALUES (value1, value2, value3,...valueN)
            List<ContentValues> lcontentValues = new ArrayList<>();
            List<String> l_tabName = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject singleFormObj = jsonArray.getJSONObject(i);
                ContentValues mainvalues = new ContentValues();
                //mainvalues.put(tableName + "_SerialNo", "");//3
                mainvalues.put("Bhargo_fromServer", "true");//4
                mainvalues.put("Bhargo_OrganisationID", sessionManager.getOrgIdFromSession());//4
                mainvalues.put("Bhargo_CreatedUserID", appDetails.getCreatedBy());//5
                mainvalues.put("Bhargo_FormName", appDetails.getAppName());//9
                mainvalues.put("Bhargo_SubFormName", "");//10
                mainvalues.put("Bhargo_AppVersion", appDetails.getAppVersion());//11
                mainvalues.put("Bhargo_SyncStatus", "2");//12
                mainvalues.put("Bhargo_UserID", "");//13
                mainvalues.put("Bhargo_IsCheckList", "");//15
                mainvalues.put("Bhargo_CheckListNames", "");//16
                Iterator keys = singleFormObj.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    if (key.equals("Trans_ID")) {
                        //Trans_ID
                        mainvalues.put(AppConstants.Trans_id, singleFormObj.getString(key));//last col
                    } else if (key.equals("IS_Active")) {
                        //IS_Active
                        mainvalues.put("Bhargo_Is_Active", singleFormObj.getString(key));//2
                    } else if (key.equals("TransDate")) {
                        //TransDate
                        mainvalues.put("Bhargo_Trans_Date", singleFormObj.getString(key));//1
                    } else if (key.equals("SubmittedUserID")) {
                        //SubmittedUserID
                        mainvalues.put("Bhargo_SubmittedUserID", singleFormObj.getString(key));//1
                    } else if (key.equals("PostID") || key.equals("Post_ID")) {
                        //PostID
                        mainvalues.put("Bhargo_PostID", singleFormObj.getString(key));//1
                    } else if (key.equals("DistributionID")) {
                        //DistributionID
                        mainvalues.put("Bhargo_DistributionID", singleFormObj.getString(key));//1
                    } else if (key.equals("IMEI")) {
                        //IMEI
                        mainvalues.put("Bhargo_IMEI", singleFormObj.getString(key));//1
                    } else if (!key.equals("SubForm")) {
                        //Other Cols
                        mainvalues.put(key, singleFormObj.getString(key));//1
                    }
                }
                lcontentValues.add(mainvalues);
                l_tabName.add(tableName);

                if (singleFormObj.has("SubForm")) {
                    ////SubForm Data
                    JSONArray subFormData = singleFormObj.getJSONArray("SubForm");
                    for (int j = 0; j < subFormData.length(); j++) {
                        JSONObject subFormObj = subFormData.getJSONObject(j);
                        keys = subFormObj.keys();
                        while (keys.hasNext()) {
                            String subFormName = (String) keys.next();//subForm Name
                            String subFormTableName = tableName + "_" + subFormName;
                            JSONArray subformArray = subFormObj.getJSONArray(subFormName);
                            for (int k = 0; k < subformArray.length(); k++) {
                                JSONObject singleSubFormObj = subformArray.getJSONObject(k);
                                ContentValues subFormvalues = new ContentValues();
                                // subFormvalues.put(subFormTableName + "_SerialNo", "");//3
                                subFormvalues.put("Bhargo_fromServer", "true");//4
                                subFormvalues.put("Bhargo_OrganisationID", sessionManager.getOrgIdFromSession());//4
                                subFormvalues.put("Bhargo_CreatedUserID", appDetails.getCreatedBy());//5
                                subFormvalues.put("Bhargo_FormName", appDetails.getAppName());//9
                                subFormvalues.put("Bhargo_SubFormName", subFormName);//10
                                subFormvalues.put("Bhargo_AppVersion", appDetails.getAppVersion());//11
                                subFormvalues.put("Bhargo_SyncStatus", "2");//12
                                subFormvalues.put("Bhargo_UserID", "");//13
                                subFormvalues.put("Bhargo_IsCheckList", "");//15
                                subFormvalues.put("Bhargo_CheckListNames", "");//16
                                keys = singleSubFormObj.keys();
                                while (keys.hasNext()) {
                                    String key = (String) keys.next();
                                    if (key.equals("Trans_ID")) {
                                        //Trans_ID
                                        subFormvalues.put(AppConstants.Trans_id, singleSubFormObj.getString(key));//last col
                                    } else if (key.equals("IS_Active")) {
                                        //IS_Active
                                        subFormvalues.put("Bhargo_Is_Active", singleSubFormObj.getString(key));//2
                                    } else if (key.equals("TransDate")) {
                                        //TransDate
                                        subFormvalues.put("Bhargo_Trans_Date", singleSubFormObj.getString(key));//1
                                    } else if (key.contains("Ref_TransID")) {
                                        //Ref_TransID pending nk
                                        subFormvalues.put(AppConstants.Ref_Trans_id, singleSubFormObj.getString(key));//1
                                    } else if (key.equals("SubmittedUserID")) {
                                        //SubmittedUserID
                                        subFormvalues.put("Bhargo_SubmittedUserID", singleSubFormObj.getString(key));//1
                                    } else if (key.equals("PostID") || key.equals("Post_ID")) {
                                        //PostID
                                        subFormvalues.put("Bhargo_PostID", singleSubFormObj.getString(key));//1
                                    } else if (key.equals("DistributionID")) {
                                        //DistributionID
                                        subFormvalues.put("Bhargo_DistributionID", mainvalues.get("Bhargo_DistributionID").toString());//1);//1
                                    } else if (key.equals("IMEI")) {
                                        //IMEI
                                        subFormvalues.put("Bhargo_IMEI", mainvalues.get("Bhargo_IMEI").toString());//1
                                    } else {
                                        //Other Cols
                                        subFormvalues.put(key, singleSubFormObj.getString(key));
                                    }
                                }
                                lcontentValues.add(subFormvalues);
                                l_tabName.add(subFormTableName);
                            }
                        }

                    }
                }
            }
            String errorMsg = improveDataBase.bulkContentValues(lcontentValues, l_tabName);
            System.out.println("flag:" + errorMsg);
            if (errorMsg.equals("")) {
                closeProgressDialog();
                //Successfully Data Sync From Server
                syncSucessResult = "Successfully Data Sync From Server";
                syncDataListener.onSuccess(syncSucessResult);
            } else {
                failFlag = true;
                closeProgressDialog();
                syncFailResult = "Data Sync From Server Failed!\n\n" + errorMsg;
                syncDataListener.onFailed(syncFailResult);
            }
        } catch (Exception e) {
            failFlag = true;
            closeProgressDialog();
            syncFailResult = e.getMessage() + "\nData Sync From Server Failed! ";
            syncDataListener.onFailed(syncFailResult);
        }
    }

    public interface SyncDataListener {
        void onSuccess(String msg);

        void onFailed(String errorMessage);
    }

    private class SyncStart extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(context, progressMsg);
        }

        @Override
        protected synchronized Void doInBackground(Void... voids) {
            if (isNetworkStatusAvialable(context)) {

                if (syncType == 1) {
                    sendLocalDataCase();
                } else if (syncType == 2) {
                    downloadSyncDataFromServerCase();
                } else {//syncType==3
                    sendLocalDataCase();
                }
            } else {
                syncFailResult = context.getString(R.string.no_internet);
            }
            return null;
        }


        @Override
        protected synchronized void onPostExecute(Void result) {

            //
            if (syncFailResult != null && !syncFailResult.equals("") && !failFlag) {
                closeProgressDialog();
                syncDataListener.onFailed(syncFailResult);
            } else {
                // syncDataListener.onSuccess(syncSucessResult);
            }
            super.onPostExecute(result);
        }

    }

    private String getSubformTableName(String subFormName_new) {
        String tableName=null;
        for(SubFormTableColumns subFormTableColumns:appDetails.getSubFormDetails()){
            if(subFormTableColumns.getSubFormName().equalsIgnoreCase(subFormName_new)){
                return subFormTableColumns.getTableName();
            }
        }
        return tableName;
    }

    public boolean isAutoNumberControl(String control) {
        try {
            for (int i = 0; i < jArrayAutoIncementControls.length(); i++) {
                JSONObject jsonObject = jArrayAutoIncementControls.getJSONObject(i);
                if (control.equalsIgnoreCase(jsonObject.getString("ControlName"))) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "isAutoNumberControl: " + e.toString());
        }
        return false;
    }

    private boolean isAutoNumberControl(String subformName, String control) {
        try {
            JSONArray jsonArray = subFormsAutoNumberArraysMap.get(subformName);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (control.equalsIgnoreCase(jsonObject.getString("ControlName"))) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "isAutoNumberControl: " + e.toString());
        }
        return false;
    }

}
