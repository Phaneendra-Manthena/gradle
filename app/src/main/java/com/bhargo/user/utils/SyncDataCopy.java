package com.bhargo.user.utils;

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
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.Java_Beans.SyncFormData;
import com.bhargo.user.R;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppDetails;
import com.bhargo.user.pojos.AppDetailsAdvancedInput;
import com.bhargo.user.pojos.FileColAndIDPojo;
import com.bhargo.user.pojos.FilterColumns;
import com.bhargo.user.pojos.TableNameAndColsFromDOPojo;
import com.bhargo.user.pojos.UpdateTablePojo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncDataCopy {
    private static final String TAG = "SyncData";
    private final Context context;
    String progressMsg = "";
    String syncFailResult, syncSucessResult;
    String appName;
    ProgressDialog pd;
    ImproveDataBase improveDataBase;
    int syncType;
    ImproveHelper improveHelper;
    SyncFormData syncFormData;
    SyncDataListener syncDataListener;
    DataCollectionObject dataCollectionObject;
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

    public SyncDataCopy(Context context, String appName, int syncType, SyncDataListener syncDataListener) {
        this.context = context;
        this.appName = appName;
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);
        sessionManager = new SessionManager(context);
        getServices = RetrofitUtils.getUserService();
        this.syncDataListener = syncDataListener;
        //1. send local data Only
        //2. get server data and insert into local DB
        //3. both 1 & 2
        this.syncType = syncType;
        progressMsg = "Please Wait Data Sync...";
       /* if (syncType == 1) {
            progressMsg = "Please Wait! Data Sending...";
        } else if (syncType == 2) {
            progressMsg = "Please Wait! Data Fetching...";
        } else {//syncType==3
            progressMsg = "Please Wait! Data Send & Sync To Local DB...";
        }*/
        syncFailResult = "";
        syncSucessResult = "";
        // testTablePK();
        new SyncStart().execute();
    }


    public SyncDataCopy(Context context, SyncFormData syncFormData, SyncDataListener syncDataListener) {
        this.context = context;
        this.syncFormData = syncFormData;
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);
        sessionManager = new SessionManager(context);
        getServices = RetrofitUtils.getUserService();
        this.syncDataListener = syncDataListener;
        //0.Take Data From SynFormData and set syncType & appName
        this.appName = syncFormData.getFormName();
        if (syncFormData.isEnableSendDataToServer() && syncFormData.isEnableRetrieveDataFromServer()) {
            this.syncType = 3;
        } else if (syncFormData.isEnableRetrieveDataFromServer()) {
            this.syncType = 2;
        } else {
            this.syncType = 1;
        }

        //1. send local data Only
        //2. get server data and insert into local DB
        //3. both 1 & 2
        progressMsg = "Please Wait Data Sync...";
       /* if (syncType == 1) {
            progressMsg = "Please Wait...";
        } else if (syncType == 2) {
            progressMsg = "Please Wait! Data Fetching...";
        } else {//syncType==3
            progressMsg = "Please Wait! Data Send & Sync To Local DB...";
        }*/
        syncFailResult = "";
        syncSucessResult = "";
        new SyncStart().execute();
    }


    private void sendLocalDataCase() {
        appDetails = improveDataBase.getAppDetails(appName);
        if (appDetails != null) {
            if (improveDataBase.tableExists(appDetails.getTableName())) {
                XMLHelper xmlHelper = new XMLHelper();
                if (dataCollectionObject == null) {
                    dataCollectionObject = xmlHelper.XML_To_DataCollectionObject(appDetails.getDesignFormat());
                } else if (!dataCollectionObject.getApp_Name().equals(appName)) {
                    dataCollectionObject = xmlHelper.XML_To_DataCollectionObject(appDetails.getDesignFormat());
                }
                getTableNameAndColsFromDCObj(appDetails.getTableName());
                // showProgressDialog(context, "Please Wait! Data Send To Server...");
                firstTimeSendingInsertRecord = 0;
                String tableName = appDetails.getTableName();
                String trans_id_maintablecol = tableName + "_Trans_Id";
                String syncstatus_maintablecol = tableName + "_syncstatus";
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
        } else {
            failFlag = true;
            syncFailResult = "No Such AppName:" + appName + " Not Exist In Local DataBase.";
            closeProgressDialog();
            syncDataListener.onFailed(syncFailResult);
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
            appDetails = improveDataBase.getAppDetails(appName);
            if (appDetails != null) {
                XMLHelper xmlHelper = new XMLHelper();
                //nk step
                System.out.println("=======Step14===============");
                dataCollectionObject = xmlHelper.XML_To_DataCollectionObject(appDetails.getDesignFormat());
                boolean keysSame = improveHelper.checkPrimaryKeysAsPerDataCollectionObj(appDetails, improveDataBase);
                if (keysSame) {
                    improveDataBase.createTablesBasedOnConditions(appDetails);
                } else {
                    keysDifferentInCreatedTable();
                }
                getTableNameAndColsFromDCObj(appDetails.getTableName());
                AppDetailsAdvancedInput appDetailsAdvancedInput = new AppDetailsAdvancedInput();
                appDetailsAdvancedInput.setFilterColumns(new ArrayList<>());
                appDetailsAdvancedInput.setOrgId(sessionManager.getOrgIdFromSession());
                appDetailsAdvancedInput.setPageName(dataCollectionObject.getApp_Name());
                appDetailsAdvancedInput.setUserId(sessionManager.getUserDataFromSession().getUserID());
                appDetailsAdvancedInput.setSubmittedUserPostID(sessionManager.getPostsFromSession());
                if (dataCollectionObject.getDataManagementOptions().getFetchData() != null) {
                    appDetailsAdvancedInput.setFetchData(dataCollectionObject.getDataManagementOptions().getFetchData());
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
                if (dataCollectionObject.getDataManagementOptions().getIndexPageColumnsOrder() != null && !dataCollectionObject.getDataManagementOptions().getIndexPageColumnsOrder().equalsIgnoreCase("")) {
                    appDetailsAdvancedInput.setOrderbyStatus("True");
                    appDetailsAdvancedInput.setOrderByColumns(dataCollectionObject.getDataManagementOptions().getIndexPageColumnsOrder());
                    appDetailsAdvancedInput.setOrderByType(dataCollectionObject.getDataManagementOptions().getIndexPageColumnsOrder());
                } else {
                    appDetailsAdvancedInput.setOrderbyStatus("False");
                    appDetailsAdvancedInput.setOrderByColumns("");
                    appDetailsAdvancedInput.setOrderByType("");
                }

                if (dataCollectionObject.getDataManagementOptions().isLazyLoadingEnabled()) {
                    appDetailsAdvancedInput.setLazyLoading("True");
                    appDetailsAdvancedInput.setThreshold(dataCollectionObject.getDataManagementOptions().getLazyLoadingThreshold());
                    appDetailsAdvancedInput.setRange("1" + "-" + dataCollectionObject.getDataManagementOptions().getLazyLoadingThreshold());
                    //TODO  --- In future need to set order by columns in the below line
                    appDetailsAdvancedInput.setLazyOrderKey("SELECT NULL");
                }

                if (dataCollectionObject.getDataManagementOptions().isLazyLoadingEnabled() && appDetailsAdvancedInput.getLazyOrderKey().contentEquals("")) {
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
            } else {
                failFlag = true;
                syncFailResult = "No Such AppName:" + appName + " Not Exist In Local DataBase.";
                closeProgressDialog();
                syncDataListener.onFailed(syncFailResult);
            }
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
                    syncFailResult = sendingIssues.toString();
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
                    }
                    //send next image
                    sendingFilesCount++;
                    handSendDataFiles.sendEmptyMessage(0);
                }
            }).execute(filePath);
        } else {
            // showProgressDialog(context, "Please Wait! Data Sending...");
            sendMainAndSubFromDataToServer();
        }
    }

    private void sendMainAndSubFromDataToServer() {
        try {
            updateTablePojoList = new ArrayList<>();
            String tableName = appDetails.getTableName();
            String syncstatus_maintablecol = tableName + "_syncstatus";

            List<String> ll_data = ll_senddataRecords.get(sendDataCount);
            //"Trans_Date"0,"Is_Active"1,"SerialNo"2,"OrganisationID"3,"CreatedUserID"4,"SubmittedUserID"5,
            // "DistributionID"6, "IMEI"7,"FormName"8,"SubFormName"9,"AppVersion"10,"SyncStatus"11, "UserID"12,
            // "PostID"13, "IsCheckList"14, "CheckListNames"15,"offlineJSON"
            boolean fromServer = false;
            JSONObject mainObject = new JSONObject();
            mainObject.put("OrgId", sessionManager.getOrgIdFromSession());
            mainObject.put("PageName", dataCollectionObject.getApp_Name());
            mainObject.put("CreatedUserID", ll_data.get(4));
            mainObject.put("PostId", ll_data.get(13));
            mainObject.put("SubmittedUserPostID", ll_data.get(13));
            mainObject.put("SubmittedUserID", ll_data.get(5));
            mainObject.put("DistributionID", ll_data.get(6));
            mainObject.put("IMEI", ll_data.get(7));
            mainObject.put("IsCheckList", "false");
            mainObject.put("ChecklistNames", "[]");//[]
            mainObject.put("IfautoincrementControls", "false");
            mainObject.put("AutoIncrementControl", "[]");//[]
            mainObject.put("OperationType", "");
            mainObject.put("TableName", tableName);
            mainObject.put("TypeofSubmission", "");
            mainObject.put("insertColumns", "[]");//[]
            mainObject.put("UpdateColumns", "[]");//[]
            mainObject.put("tableSettingsType", "Create New Table");
            if (ll_data.get(16) != null && ll_data.get(16).equalsIgnoreCase("true")) {
                fromServer = true;
                mainObject.put("TransID", ll_data.get(0));
                mainObject.put("Action", "Update");
                mainObject.put("UserId", ll_data.get(4));
                mainObject.put("subFormInMainForm", !dataCollectionObject.getTableSettingsObject().getSubFormInMainForm().equalsIgnoreCase(""));
            }
            //DataFields:MainTable
            String transID_MainTableVal = ll_data.get(0);
            String trans_id_maintablecol = tableName + "_Trans_Id";
            TableNameAndColsFromDOPojo tableNameAndCols_mainTable = tableNameAndColsList.get(tableNameAndColsList.size() - 1);
            List<List<String>> l_data = improveDataBase.getTableColDataByCond(tableNameAndCols_mainTable.getTableName(),
                    "rowid," + tableNameAndCols_mainTable.getCols(),
                    new String[]{trans_id_maintablecol}, new String[]{transID_MainTableVal});
            JSONArray dataFieldsArray = new JSONArray();
            String[] colNames = ("rowid," + tableNameAndCols_mainTable.getCols()).split(",");
            for (int i = 0; i < l_data.size(); i++) {
                List<String> colvalue = l_data.get(i);
                //For table update
                UpdateTablePojo updateTablePojo = new UpdateTablePojo();
                updateTablePojo.setTableName(tableNameAndCols_mainTable.getTableName());
                updateTablePojo.setRowidVal(colvalue.get(0));
                updateTablePojo.setColName1(trans_id_maintablecol);
                updateTablePojo.setColVal1(colvalue.get(1));
                updateTablePojoList.add(updateTablePojo);
                //Close
                JSONObject jsonObject = new JSONObject();
                for (int j = 1; j < colvalue.size(); j++) {
                    if (!colNames[j].contains(trans_id_maintablecol)) {

                        jsonObject.put(colNames[j], colvalue.get(j));

                    }
                }
                dataFieldsArray.put(jsonObject);
            }
            mainObject.put("DataFields", dataFieldsArray);//[]
            //SubForm
            JSONArray subFormArray = new JSONArray();
            for (int i = 0; i < tableNameAndColsList.size(); i++) {
                TableNameAndColsFromDOPojo subformTab_cols = tableNameAndColsList.get(i);
                if (subformTab_cols.getType().equals("S")) {
                    String subFormTableName = subformTab_cols.getTableName();
                    String ref_trans_id_subformtablecolName = subFormTableName + "_Ref_TransID";
                    String trans_id_subformtablecol = subFormTableName + "_Trans_Id";
                    String from_server_subformtablecol = subFormTableName + "_FromServer";
                    String is_active_subformtablecol = subFormTableName + "_Is_Active";
                   /* List<List<String>> ll_subformdata = improveDataBase.getDataByQuery
                            ("select * from " + subFormTableName + " where " + ref_trans_id_subformtablecolName + "='" + transID_MainTableVal + "' order by rowid asc limit 1");
                    *///"Trans_Date"0,"Is_Active"1,"SerialNo"2,"OrganisationID"3,"CreatedUserID"4,"SubmittedUserID"5,
                    // "DistributionID"6, "IMEI"7,"FormName"8,"SubFormName"9,"AppVersion"10,"SyncStatus"11, "UserID"12,
                    // "PostID"13, "IsCheckList"14, "CheckListNames"15,"offlineJSON"
                    JSONObject subFormObj = new JSONObject();
                    subFormObj.put("SubFormName", subformTab_cols.getFormName());
                    subFormObj.put("TableName", subFormTableName);
                    subFormObj.put("IsCheckList", "false");
                    subFormObj.put("ChecklistNames", "[]");//[]
                    subFormObj.put("DeleteRowStatus", "false");
                    subFormObj.put("DeleteRowIds", "");
                    //DataFields:MainTable
                    List<List<String>> l_subformdata = improveDataBase.getTableColDataByCond(
                            subformTab_cols.getTableName(),
                            "rowid," + from_server_subformtablecol + "," + trans_id_subformtablecol + "," + is_active_subformtablecol + "," + subformTab_cols.getCols(),
                            new String[]{ref_trans_id_subformtablecolName}, new String[]{transID_MainTableVal});
                    JSONArray dataFieldsSubFormArray = new JSONArray();
                    String[] subFormColNames = ("rowid," + from_server_subformtablecol + "," + trans_id_subformtablecol + "," + is_active_subformtablecol + "," + subformTab_cols.getCols()).split(",");
                    for (int x = 0; x < l_subformdata.size(); x++) {
                        List<String> subcolvalue = l_subformdata.get(x);
                        //For table update
                        UpdateTablePojo updateTablePojo = new UpdateTablePojo();
                        updateTablePojo.setTableName(subformTab_cols.getTableName());
                        updateTablePojo.setRowidVal(subcolvalue.get(0));
                        updateTablePojo.setColName1(trans_id_subformtablecol);
                        updateTablePojo.setColVal1(subcolvalue.get(2));
                        updateTablePojoList.add(updateTablePojo);
                        //Close
                        JSONObject jsonObject = new JSONObject();

                        for (int y = 1; y < subcolvalue.size(); y++) {
                            if (y == 1) {
                                //Skip FromServer
                            } else if (y == 2) {
                                //Skip trans_id
                            } else if (y == 3) {
                                //Is Active
                                if (subcolvalue.get(1) != null && subcolvalue.get(1).equals("true")) {
                                    jsonObject.put("Is_Active", subcolvalue.get(y));
                                }
                            } else if (y == 4) {
                                //skip ref_trans_id
                            } else {
                                //remaining cols
                                jsonObject.put(subFormColNames[y], subcolvalue.get(y));
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
                        dataFieldsSubFormArray.put(jsonObject);
                    }
                    subFormObj.put("DataFields", dataFieldsSubFormArray);//[]
                    subFormObj.put("TypeofSubmission", "");
                    subFormObj.put("insertColumns", "[]");//[]
                    subFormObj.put("IfautoincrementControls", "false");
                    subFormObj.put("AutoIncrementControl", "[]");//[]
                    subFormObj.put("UpdateColumns", "[]");//[]
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

    private void getTableNameAndColsFromDCObj(String mainTableName) {
        tableNameAndColsForFilesPojoList = new ArrayList<>();
        tableNameAndColsList = new ArrayList<>();
        List<String> fileCols_mainTable = new ArrayList<>();
        for (int i = 0; i < dataCollectionObject.getControls_list().size(); i++) {
            ControlObject controlObject = dataCollectionObject.getControls_list().get(i);
            if (controlObject.getControlType().equals(AppConstants.CONTROL_TYPE_CAMERA) ||
                    controlObject.getControlType().equals(AppConstants.CONTROL_TYPE_FILE_BROWSING) ||
                    controlObject.getControlType().equals(AppConstants.CONTROL_TYPE_VIDEO_RECORDING) ||
                    controlObject.getControlType().equals(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                fileCols_mainTable.add(controlObject.getControlID());
            } else if (controlObject.getControlType().equals(AppConstants.CONTROL_TYPE_SUBFORM)) {
                List<String> subfrom_filecols_maintable = new ArrayList<>();
                List<String> subfrom_cols_maintable = new ArrayList<>();
                for (int j = 0; j < controlObject.getSubFormControlList().size(); j++) {
                    ControlObject subform_controlObject = controlObject.getSubFormControlList().get(j);
                    if (subform_controlObject.getControlType().equals(AppConstants.CONTROL_TYPE_CAMERA) ||
                            subform_controlObject.getControlType().equals(AppConstants.CONTROL_TYPE_FILE_BROWSING) ||
                            subform_controlObject.getControlType().equals(AppConstants.CONTROL_TYPE_VIDEO_RECORDING) ||
                            subform_controlObject.getControlType().equals(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                        subfrom_filecols_maintable.add(subform_controlObject.getControlID());
                    }
                }
                if (subfrom_filecols_maintable.size() > 0) {
                    String columnsSeperated = TextUtils.join(",", subfrom_filecols_maintable);
                    TableNameAndColsFromDOPojo tableNameAndColsForFilesPojo = new TableNameAndColsFromDOPojo();
                    tableNameAndColsForFilesPojo.setFormName(controlObject.getControlName());
                    tableNameAndColsForFilesPojo.setTableName(mainTableName + "_" + controlObject.getControlID());
                    tableNameAndColsForFilesPojo.setCols(columnsSeperated);
                    tableNameAndColsForFilesPojo.setType("S");
                    tableNameAndColsForFilesPojoList.add(tableNameAndColsForFilesPojo);
                }
                //SubForm Table cols
                if (controlObject.getSubFormList_Table_Columns() != null) {
                    String subformcolSeperated = TextUtils.join(",", controlObject.getSubFormList_Table_Columns());
                    TableNameAndColsFromDOPojo tableNameAndColsForFilesPojo = new TableNameAndColsFromDOPojo();
                    tableNameAndColsForFilesPojo.setFormName(controlObject.getControlName());
                    tableNameAndColsForFilesPojo.setTableName(mainTableName + "_" + controlObject.getControlID());
                    tableNameAndColsForFilesPojo.setCols(subformcolSeperated);
                    tableNameAndColsForFilesPojo.setType("S");
                    tableNameAndColsList.add(tableNameAndColsForFilesPojo);
                }
            }
        }


        if (fileCols_mainTable.size() > 0) {
            String columnsSeperated = TextUtils.join(",", fileCols_mainTable);
            TableNameAndColsFromDOPojo tableNameAndColsForFilesPojo = new TableNameAndColsFromDOPojo();
            tableNameAndColsForFilesPojo.setTableName(mainTableName);
            tableNameAndColsForFilesPojo.setCols(columnsSeperated);
            tableNameAndColsForFilesPojo.setType("M");
            tableNameAndColsForFilesPojoList.add(tableNameAndColsForFilesPojo);
        }
        //Main Table Cols
        if (dataCollectionObject.getList_Table_Columns() != null) {
            String maincolSeperated = TextUtils.join(",", dataCollectionObject.getList_Table_Columns());
            TableNameAndColsFromDOPojo mainColsPojo = new TableNameAndColsFromDOPojo();
            mainColsPojo.setTableName(mainTableName);
            mainColsPojo.setCols(maincolSeperated);
            mainColsPojo.setType("M");
            tableNameAndColsList.add(mainColsPojo);

            System.out.println("tableNameAndColsList: " + tableNameAndColsList);
            System.out.println("tableNameAndColsForFilesPojoList: " + tableNameAndColsForFilesPojoList);
        }

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
                mainvalues.put(tableName + "_fromServer", "true");//4
                mainvalues.put(tableName + "_OrganisationID", sessionManager.getOrgIdFromSession());//4
                mainvalues.put(tableName + "_CreatedUserID", appDetails.getCreatedBy());//5
                mainvalues.put(tableName + "_FormName", appDetails.getAppName());//9
                mainvalues.put(tableName + "_SubFormName", "");//10
                mainvalues.put(tableName + "_AppVersion", appDetails.getAppVersion());//11
                mainvalues.put(tableName + "_SyncStatus", "2");//12
                mainvalues.put(tableName + "_UserID", "");//13
                mainvalues.put(tableName + "_IsCheckList", "");//15
                mainvalues.put(tableName + "_CheckListNames", "");//16
                Iterator keys = singleFormObj.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    if (key.equals("Trans_ID")) {
                        //Trans_ID
                        mainvalues.put(tableName + "_Trans_Id", singleFormObj.getString(key));//last col
                    } else if (key.equals("IS_Active")) {
                        //IS_Active
                        mainvalues.put(tableName + "_Is_Active", singleFormObj.getString(key));//2
                    } else if (key.equals("TransDate")) {
                        //TransDate
                        mainvalues.put(tableName + "_Trans_Date", singleFormObj.getString(key));//1
                    } else if (key.equals("SubmittedUserID")) {
                        //SubmittedUserID
                        mainvalues.put(tableName + "_SubmittedUserID", singleFormObj.getString(key));//1
                    } else if (key.equals("PostID")) {
                        //PostID
                        mainvalues.put(tableName + "_PostID", singleFormObj.getString(key));//1
                    } else if (key.equals("DistributionID")) {
                        //DistributionID
                        mainvalues.put(tableName + "_DistributionID", singleFormObj.getString(key));//1
                    } else if (key.equals("IMEI")) {
                        //IMEI
                        mainvalues.put(tableName + "_IMEI", singleFormObj.getString(key));//1
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
                                subFormvalues.put(subFormTableName + "_fromServer", "true");//4
                                subFormvalues.put(subFormTableName + "_OrganisationID", sessionManager.getOrgIdFromSession());//4
                                subFormvalues.put(subFormTableName + "_CreatedUserID", appDetails.getCreatedBy());//5
                                subFormvalues.put(subFormTableName + "_FormName", appDetails.getAppName());//9
                                subFormvalues.put(subFormTableName + "_SubFormName", subFormName);//10
                                subFormvalues.put(subFormTableName + "_AppVersion", appDetails.getAppVersion());//11
                                subFormvalues.put(subFormTableName + "_SyncStatus", "2");//12
                                subFormvalues.put(subFormTableName + "_UserID", "");//13
                                subFormvalues.put(subFormTableName + "_IsCheckList", "");//15
                                subFormvalues.put(subFormTableName + "_CheckListNames", "");//16
                                keys = singleSubFormObj.keys();
                                while (keys.hasNext()) {
                                    String key = (String) keys.next();
                                    if (key.equals("Trans_ID")) {
                                        //Trans_ID
                                        subFormvalues.put(subFormTableName + "_Trans_Id", singleSubFormObj.getString(key));//last col
                                    } else if (key.equals("IS_Active")) {
                                        //IS_Active
                                        subFormvalues.put(subFormTableName + "_Is_Active", singleSubFormObj.getString(key));//2
                                    } else if (key.equals("TransDate")) {
                                        //TransDate
                                        subFormvalues.put(subFormTableName + "_Trans_Date", singleSubFormObj.getString(key));//1
                                    } else if (key.contains("Ref_TransID")) {
                                        //Ref_TransID
                                        subFormvalues.put(subFormTableName + "_Ref_TransID", singleSubFormObj.getString(key));//1
                                    } else if (key.equals("SubmittedUserID")) {
                                        //SubmittedUserID
                                        subFormvalues.put(subFormTableName + "_SubmittedUserID", singleSubFormObj.getString(key));//1
                                    } else if (key.equals("PostID")) {
                                        //PostID
                                        subFormvalues.put(subFormTableName + "_PostID", singleSubFormObj.getString(key));//1
                                    } else if (key.equals("DistributionID")) {
                                        //DistributionID
                                        subFormvalues.put(subFormTableName + "_DistributionID", mainvalues.get(tableName + "_DistributionID").toString());//1);//1
                                    } else if (key.equals("IMEI")) {
                                        //IMEI
                                        subFormvalues.put(subFormTableName + "_IMEI", mainvalues.get(tableName + "_IMEI").toString());//1
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


}
