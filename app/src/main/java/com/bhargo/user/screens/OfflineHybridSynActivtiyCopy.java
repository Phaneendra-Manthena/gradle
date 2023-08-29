package com.bhargo.user.screens;

import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.R;
import com.bhargo.user.adapters.OfflineHybridSynAdapter;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppDetails;
import com.bhargo.user.pojos.FileColAndIDPojo;
import com.bhargo.user.pojos.ForeignKey;
import com.bhargo.user.pojos.OfflineHybridAppListPojo;
import com.bhargo.user.pojos.SubFormTableColumns;
import com.bhargo.user.pojos.TableNameAndColsFromDOPojo;
import com.bhargo.user.pojos.UpdateTablePojo;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.FileUploader;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bhargo.user.actions.SyncData;
import com.bhargo.user.utils.XMLHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nk.bluefrog.library.utils.Helper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfflineHybridSynActivtiyCopy extends BaseActivity implements OfflineHybridSynAdapter.OfflineHybridSynAdapterListener {
    private final String TAG = "OfflineHybridSynActivtiy";
    RecyclerView rv_apps;
    CustomTextView ct_alNoRecords;
    GetServices getServices;
    Context context;
    Gson gson;
    ImproveHelper improveHelper;
    SessionManager sessionManager;
    ImproveDataBase improveDataBase;
    String pageName = "Offline Data Sync";
    OfflineHybridSynAdapter offlineHybridSynAdapter;
    List<OfflineHybridAppListPojo> offlineHybridAppListPojoList;
    List<FileColAndIDPojo> fileColAndIDPojos = new ArrayList<>();
    int sendingFilesCount = 0;
    String strUserId = "";
    String i_OrgId;
    List<TableNameAndColsFromDOPojo> tableNameAndColsForFilesPojoList = new ArrayList<>();
    List<TableNameAndColsFromDOPojo> tableNameAndColsList = new ArrayList<>();
    OfflineHybridAppListPojo selOfflineHybridAppListPojoData;
    int selectedPostion;
    List<UpdateTablePojo> updateTablePojoList = new ArrayList<>();
    DataCollectionObject dataCollectionObject;
    XMLHelper xmlHelper;
    List<List<String>> ll_senddataRecords = new ArrayList<>();
    int sendDataCount = 0, failedSendDataCount = 0;
    int firstTimeSendingInsertRecord = 0;
    List<String> sendingIssues = new ArrayList<>();
    Handler handSendDataMainAndSubFormTable = new Handler() {
        public void handleMessage(android.os.Message msg) {
            sendDataCount++;
            sendData();
        }
    };
    Handler handSendDataFiles = new Handler() {
        public void handleMessage(android.os.Message msg) {
            sendFilesToServer();
        }
    };
    EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_hybrid_syn_activtiy);
        context = OfflineHybridSynActivtiyCopy.this;
        gson = new Gson();
        improveHelper = new ImproveHelper(context);
        sessionManager = new SessionManager(context);
        improveDataBase = new ImproveDataBase(this);
        xmlHelper = new XMLHelper();
        offlineHybridAppListPojoList = new ArrayList<>();
        initializeActionBar();
        enableBackNavigation(true);
        ib_settings.setVisibility(View.GONE);
        iv_circle_appIcon.setVisibility(View.VISIBLE);
        if (pageName != null) {
            title.setText(pageName);
        } else {
            title.setText(getString(R.string.improve_user));
        }
        getServices = RetrofitUtils.getUserService();
        findViews();
    }

    private void findViews() {
        i_OrgId = sessionManager.getOrgIdFromSession();
        et_search = findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                offlineHybridSynAdapter.getFilter().filter(charSequence.toString().trim());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ct_alNoRecords = findViewById(R.id.ct_alNoRecords);
        rv_apps = findViewById(R.id.rv_apps);
        rv_apps.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_apps.setLayoutManager(layoutManager);
        offlineHybridSynAdapter = new OfflineHybridSynAdapter(offlineHybridAppListPojoList, this, ct_alNoRecords, this);
        rv_apps.setAdapter(offlineHybridSynAdapter);
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        List<List<String>> db_data;
        offlineHybridAppListPojoList.clear();
        db_data = improveDataBase.getTableColDataByORCond(ImproveDataBase.APPS_LIST_TABLE,
                "AppName,AppMode,TableName,DesignFormat,TableColumns,PrimaryKeys,ForeignKeys,CompositeKeys,SubformDetails,ApkVersion",
                new String[]{"AppMode", "AppMode"},
                new String[]{"Hybrid", "Offline"});

        if (db_data.size() > 0) {
            int totRecords = db_data.size() - 1;
            for (int i = totRecords; i >= 0; i--) {
                String tableName = db_data.get(i).get(2);
                if (tableName != null && !tableName.trim().equals("")) {
                    AppDetails appDetails = new AppDetails();
                    //AppName,AppMode,TableName,DesignFormat,TableColumns,PrimaryKeys,ForeignKeys,CompositeKeys,SubformDetails,ApkVersion
                    appDetails.setAppName(db_data.get(i).get(0));
                    appDetails.setAppMode(db_data.get(i).get(1));
                    appDetails.setTableName(db_data.get(i).get(2));
                    appDetails.setDesignFormat(db_data.get(i).get(3));
                    appDetails.setTableColumns(db_data.get(i).get(4));
                    appDetails.setPrimaryKey(db_data.get(i).get(5));
                    Type typeAppsForeignKey = new TypeToken<List<ForeignKey>>() {
                    }.getType();
                    List<ForeignKey> foreignKeyList = gson.fromJson(db_data.get(i).get(6), typeAppsForeignKey);
                    appDetails.setForeignKey(foreignKeyList);
                    appDetails.setCompositeKey(db_data.get(i).get(7));
                    Type typeApps = new TypeToken<List<SubFormTableColumns>>() {
                    }.getType();
                    List<SubFormTableColumns> subFormTableColumnsList = gson.fromJson(db_data.get(i).get(8), typeApps);
                    appDetails.setSubFormDetails(subFormTableColumnsList);
                    appDetails.setApkVersion(db_data.get(i).get(9));
                    if (improveDataBase.tableExists(tableName)) {
                        int count = improveDataBase.getCountByValue(tableName, tableName + "_SyncStatus", "0");
                        //if (count != 0) {
                        OfflineHybridAppListPojo offlineHybridAppListPojo = new OfflineHybridAppListPojo();
                        offlineHybridAppListPojo.setAppName(db_data.get(i).get(0));
                        offlineHybridAppListPojo.setAppMode(db_data.get(i).get(1));
                        offlineHybridAppListPojo.setTableName(db_data.get(i).get(2));
                        offlineHybridAppListPojo.setDesignFormat(db_data.get(i).get(3));
                        offlineHybridAppListPojo.setTableNameRecordsCount(count + "");
                        offlineHybridAppListPojo.setAppDetails(appDetails);
                        offlineHybridAppListPojoList.add(offlineHybridAppListPojo);
                        //   }
                    } else {
                        OfflineHybridAppListPojo offlineHybridAppListPojo = new OfflineHybridAppListPojo();
                        offlineHybridAppListPojo.setAppName(db_data.get(i).get(0));
                        offlineHybridAppListPojo.setAppMode(db_data.get(i).get(1));
                        offlineHybridAppListPojo.setTableName(db_data.get(i).get(2));
                        offlineHybridAppListPojo.setDesignFormat(db_data.get(i).get(3));
                        offlineHybridAppListPojo.setTableNameRecordsCount("-");
                        offlineHybridAppListPojo.setAppDetails(appDetails);
                        offlineHybridAppListPojoList.add(offlineHybridAppListPojo);
                    }
                }
            }
            if (offlineHybridAppListPojoList.size() > 0) {
                ct_alNoRecords.setVisibility(View.GONE);
            } else {
                ct_alNoRecords.setVisibility(View.VISIBLE);
            }
            offlineHybridSynAdapter.notifyDataSetChanged();
        } else {
            offlineHybridSynAdapter.notifyDataSetChanged();
            ct_alNoRecords.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSelectedItem(OfflineHybridAppListPojo offlineHybridAppListPojo, int selectedPostion) {
        //get file path like(image,video,audio etc) from maintable and subformtables
        //send to server and update url in maintable and subformtables
        //prepare sending string from maintable and subformtables and update trans id
        //1. Getting file cols from DataCollectionObject for singleForm with mainTable & subFormTable
        if (isNetworkStatusAvialable(context)) {

            selOfflineHybridAppListPojoData = offlineHybridAppListPojo;
            this.selectedPostion = selectedPostion;
            dataCollectionObject = xmlHelper.XML_To_DataCollectionObject(selOfflineHybridAppListPojoData.getDesignFormat());

            getTableNameAndColsFromDCObj(offlineHybridAppListPojo.getTableName());
            sendData();
        } else {
            ImproveHelper.my_showAlert(this, "", getString(R.string.no_internet), "2");
        }
    }

    @Override
    public void onSelectedDeleteItem(OfflineHybridAppListPojo offlineHybridAppListPojo, int selectedPostion) {

        Intent intent = new Intent(this, OfflineHybridDeleteActivity.class);
        intent.putExtra("SelectedAppDetails", offlineHybridAppListPojo);
        startActivity(intent);

    }

    @Override
    public void onSelectedSendItem(OfflineHybridAppListPojo offlineHybridAppListPojo, int selectedPostion) {
        if (isNetworkStatusAvialable(context)) {
            selOfflineHybridAppListPojoData = offlineHybridAppListPojo;
            this.selectedPostion = selectedPostion;
            dataCollectionObject = xmlHelper.XML_To_DataCollectionObject(selOfflineHybridAppListPojoData.getDesignFormat());
            callSyncDataWithType(3);
        } else {
            ImproveHelper.my_showAlert(this, "", getString(R.string.no_internet), "2");
        }
    }

    @Override
    public void onSelectedSyncItem(OfflineHybridAppListPojo offlineHybridAppListPojo, int selectedPostion) {
        //form sync from server
        //1.check tables exists or no
        selOfflineHybridAppListPojoData = offlineHybridAppListPojo;
        this.selectedPostion = selectedPostion;
        dataCollectionObject = xmlHelper.XML_To_DataCollectionObject(selOfflineHybridAppListPojoData.getDesignFormat());
        if (improveDataBase.tableExists(selOfflineHybridAppListPojoData.getTableName())) {
            //2. data sync
            //downloadSyncDataFromServer();
            callSyncDataWithType(2);
        } else {
            //1.a: create tables
            improveDataBase.createTablesBasedOnConditions(selOfflineHybridAppListPojoData.getAppDetails());
            //2. data sync
            //downloadSyncDataFromServer();
            callSyncDataWithType(2);

        }
    }

    private void callSyncDataWithType(int type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SyncData syncData = new SyncData(OfflineHybridSynActivtiyCopy.this, selOfflineHybridAppListPojoData.getAppName(),
                        type, new SyncData.SyncDataListener() {

                    @Override
                    public void onSuccess(String msg) {
                        ImproveHelper.confirmDialog(OfflineHybridSynActivtiyCopy.this, msg, "OK", "", new Helper.IL() {
                            @Override
                            public void onSuccess() {
                                loadData();
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        ImproveHelper.my_showAlert(OfflineHybridSynActivtiyCopy.this, "Sync Failed!", errorMessage, "2");
                    }
                });
            }
        });

    }

    private void sendData() {

        if (sendDataCount < ll_senddataRecords.size()) {
            firstTimeSendingInsertRecord = 1;
            fileColAndIDPojos = improveDataBase.getFilePathFromTables(tableNameAndColsForFilesPojoList, ll_senddataRecords.get(sendDataCount).get(0));
            if (fileColAndIDPojos.size() > 0) {
                sendingFilesCount = 0;
                showProgressDialog("Please Wait! Files Sending...");
                sendFilesToServer();
            } else {
                showProgressDialog("Please Wait! Data Sending...");
                sendMainAndSubFromDataToServer();
            }
        } else {
            dismissProgressDialog();
            if (sendingIssues.size() == 0) {
                ImproveHelper.confirmDialog(OfflineHybridSynActivtiyCopy.this, "All Records Submitted Successfully.", "OK", "", new Helper.IL() {
                    @Override
                    public void onSuccess() {
                        if (dataCollectionObject.getDataManagementOptions().isEnableViewData()) {
                            //Download
                            //downloadSyncDataFromServer();
                            callSyncDataWithType(2);
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            } else {
                //Failed To Send Record
                ImproveHelper.my_showAlert(context, "Sending Failed!", sendingIssues.toString(), "2");
            }
        }
    }

    private void sendMainAndSubFromDataToServer() {
        try {
            updateTablePojoList = new ArrayList<>();
            String tableName = selOfflineHybridAppListPojoData.getTableName();
            String syncstatus_maintablecol = tableName + "_syncstatus";

            List<String> ll_data = ll_senddataRecords.get(sendDataCount);
            //"Trans_Date"0,"Is_Active"1,"SerialNo"2,"OrganisationID"3,"CreatedUserID"4,"SubmittedUserID"5,
            // "DistributionID"6, "IMEI"7,"FormName"8,"SubFormName"9,"AppVersion"10,"SyncStatus"11, "UserID"12,
            // "PostID"13, "IsCheckList"14, "CheckListNames"15
            JSONObject mainObject = new JSONObject();
            boolean fromServer = false;
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
                    if (j == 1) {
                        // transid
                    } else {
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
//                    List<List<String>> ll_subformdata = improveDataBase.getDataByQuery("select * from " + subFormTableName + " where " + ref_trans_id_subformtablecolName + "='" + transID_MainTableVal + "' order by rowid asc limit 1");
                    //"Trans_Date"0,"Is_Active"1,"SerialNo"2,"OrganisationID"3,"CreatedUserID"4,"SubmittedUserID"5,
                    // "DistributionID"6, "IMEI"7,"FormName"8,"SubFormName"9,"AppVersion"10,"SyncStatus"11, "UserID"12,
                    // "PostID"13, "IsCheckList"14, "CheckListNames"15
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
                        if (subcolvalue.get(1) != null && subcolvalue.get(1).equals("true")) {
                            jsonObject.put("TransID", subcolvalue.get(2));
                        }

                        for (int y = 1; y < subcolvalue.size(); y++) {
                            if (y == 1) {
                                //Skip FromServer
                            } else if (y == 2) {
                                //Skip trans_id
                            } else if (y == 3) {
                                //Is Active
                                jsonObject.put("Is_Active", subcolvalue.get(y));
                            } else if (y == 4) {
                                //skip ref_trans_id
                            } else {
                                //remaining cols
                                jsonObject.put(subFormColNames[y], subcolvalue.get(y));
                            }
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
                        String sno = jsonObject.getString("Sno");
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
        //2.update view count in strip form 2 to 1
        int viewcount = Integer.parseInt(selOfflineHybridAppListPojoData.getTableNameRecordsCount());
        int updatedviewCount = viewcount - 1;
        selOfflineHybridAppListPojoData.setTableNameRecordsCount(updatedviewCount + "");
        if (updatedviewCount == 0) {
            //Remove view
            offlineHybridAppListPojoList.remove(selectedPostion);
            if (offlineHybridAppListPojoList.size() > 0) {
                ct_alNoRecords.setVisibility(View.GONE);
            } else {
                ct_alNoRecords.setVisibility(View.VISIBLE);
            }
            offlineHybridSynAdapter.notifyItemRemoved(selectedPostion);
        } else {
            //update view
            offlineHybridAppListPojoList.set(selectedPostion, selOfflineHybridAppListPojoData);
            offlineHybridSynAdapter.notifyItemChanged(selectedPostion);
        }
        handSendDataMainAndSubFormTable.sendEmptyMessage(0);
    }

    private void sendFilesToServer() {
        if (sendingFilesCount < fileColAndIDPojos.size()) {
            FileColAndIDPojo fileColAndIDPojo = fileColAndIDPojos.get(sendingFilesCount);
            String filePath = fileColAndIDPojo.getColVal();
            final String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
            strUserId = sessionManager.getUserDataFromSession().getUserID();
            new FileUploader(this, fileName, strUserId, pageName, false, "BHARGO", new FileUploader.OnImageUploaded() {
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
            showProgressDialog("Please Wait! Data Sending...");
            sendMainAndSubFromDataToServer();
        }
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
                    tableNameAndColsForFilesPojo.setTableName(mainTableName + "_" + controlObject.getControlID());
                    tableNameAndColsForFilesPojo.setCols(columnsSeperated);
                    tableNameAndColsForFilesPojo.setType("S");
                    tableNameAndColsForFilesPojoList.add(tableNameAndColsForFilesPojo);
                }
                //SubForm Table cols
                String subformcolSeperated = TextUtils.join(",", controlObject.getSubFormList_Table_Columns());
                TableNameAndColsFromDOPojo tableNameAndColsForFilesPojo = new TableNameAndColsFromDOPojo();
                tableNameAndColsForFilesPojo.setTableName(mainTableName + "_" + controlObject.getControlID());
                tableNameAndColsForFilesPojo.setCols(subformcolSeperated);
                tableNameAndColsForFilesPojo.setType("S");
                tableNameAndColsList.add(tableNameAndColsForFilesPojo);
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
        String maincolSeperated = TextUtils.join(",", dataCollectionObject.getList_Table_Columns());
        TableNameAndColsFromDOPojo mainColsPojo = new TableNameAndColsFromDOPojo();
        mainColsPojo.setTableName(mainTableName);
        mainColsPojo.setCols(maincolSeperated);
        mainColsPojo.setType("M");
        tableNameAndColsList.add(mainColsPojo);

        System.out.println("tableNameAndColsList: " + tableNameAndColsList);
        System.out.println("tableNameAndColsForFilesPojoList: " + tableNameAndColsForFilesPojoList);
    }

    //Already Code Exist in SyncData For Send Data To Server
/*  @Override
  public void onSelectedSendItem(OfflineHybridAppListPojo offlineHybridAppListPojo, int selectedPostion) {
      if (isNetworkStatusAvialable(context)) {

            selOfflineHybridAppListPojoData = offlineHybridAppListPojo;
            this.selectedPostion = selectedPostion;
            dataCollectionObject = xmlHelper.XML_To_DataCollectionObject(selOfflineHybridAppListPojoData.getDesignFormat());
            getTableNameAndColsFromDCObj(offlineHybridAppListPojo.getTableName());
            showProgressDialog("Please Wait! Data Send To Server...");
            String tableName = selOfflineHybridAppListPojoData.getTableName();
            String trans_id_maintablecol = tableName + "_Trans_Id";
            String syncstatus_maintablecol = tableName + "_syncstatus";
            sendingIssues.clear();
            sendDataCount = 0;
            ll_senddataRecords = improveDataBase.getDataByQuery
                    ("select *  from " + tableName + " where " + syncstatus_maintablecol + "='0'");

            sendData();


      } else {
          ImproveHelper.my_showAlert(this, "", getString(R.string.no_internet), "2");
      }
  } */

//Already Code Exist in SyncData For Data Download
  /*  private void downloadSyncDataFromServerNoUse_SyncDataClass() {
        //selOfflineHybridAppListPojoData
        if (isNetworkStatusAvialable(context)) {
            AppDetailsAdvancedInput appDetailsAdvancedInput = new AppDetailsAdvancedInput();
            appDetailsAdvancedInput.setOrgId(sessionManager.getOrgIdFromSession());
            appDetailsAdvancedInput.setPageName(dataCollectionObject.getApp_Name());
            appDetailsAdvancedInput.setUserId(sessionManager.getUserDataFromSession().getUserID());
            appDetailsAdvancedInput.setSubmittedUserPostID(sessionManager.getPostsFromSession());
            if (dataCollectionObject.getFetchData() != null) {
                appDetailsAdvancedInput.setFetchData(dataCollectionObject.getFetchData());
            } else {
                appDetailsAdvancedInput.setFetchData("Login User Post");
            }
            if (dataCollectionObject.getIndexPageColumnsOrderList() != null && !dataCollectionObject.getIndexPageColumnsOrderList().equalsIgnoreCase("")) {
                appDetailsAdvancedInput.setOrderbyStatus("True");
                appDetailsAdvancedInput.setOrderByColumns(dataCollectionObject.getIndexPageColumnsOrderList());
                appDetailsAdvancedInput.setOrderByType(dataCollectionObject.getIndexPageColumnsOrder());
            } else {
                appDetailsAdvancedInput.setOrderbyStatus("False");
                appDetailsAdvancedInput.setOrderByColumns("");
                appDetailsAdvancedInput.setOrderByType("");
            }

            if (dataCollectionObject.isLazyLoadingEnabled()) {
                appDetailsAdvancedInput.setLazyLoading("True");
                appDetailsAdvancedInput.setThreshold(dataCollectionObject.getLazyLoadingThreshold());
                appDetailsAdvancedInput.setRange("1" + "-" + dataCollectionObject.getLazyLoadingThreshold());
                //TODO  --- In future need to set order by columns in the below line
                appDetailsAdvancedInput.setLazyOrderKey("SELECT NULL");
            }

            if (dataCollectionObject.isLazyLoadingEnabled() && appDetailsAdvancedInput.getLazyOrderKey().contentEquals("")) {
                appDetailsAdvancedInput.setLazyOrderKey("SELECT NULL");
            }
            showProgressDialog(getString(R.string.please_wait_sync));
            Call<ResponseBody> getAllAppNamesDataCall;
            getAllAppNamesDataCall = getServices.iGetAppDataOffline(appDetailsAdvancedInput);
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
                                dismissProgressDialog();
                                ImproveHelper.my_showAlert(OfflineHybridSynActivtiy.this, "",
                                        "No Records To Sync!", "1");

                            } else {
                                // ImproveHelper.showToast(OfflineHybridSynActivtiy.this, getString(R.string.data_recieved_successfully));
                                insertDataToDB(jsonArray);
                            }
                        } else {
                            dismissProgressDialog();
                            ImproveHelper.my_showAlert(OfflineHybridSynActivtiy.this, "",
                                    responseObj.getString("Message") + "\nPlease Try Again.", "2");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        dismissProgressDialog();
                        ImproveHelper.my_showAlert(OfflineHybridSynActivtiy.this, "",
                                e + "\nPlease Try Again.", "2");
                    }
                    Log.d(TAG, "onResponse: " + json);
                }


                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    dismissProgressDialog();
                    ImproveHelper.my_showAlert(OfflineHybridSynActivtiy.this, "",
                            t.getMessage() + "\nPlease Try Again.", "2");
                }
            });

        } else {
            ImproveHelper.my_showAlert(this, "", getString(R.string.no_internet), "2");
        }

    }*/
  /*  private void insertDataToDB(JSONArray jsonArray) {
        try {
            String tableName = selOfflineHybridAppListPojoData.getTableName();
            //INSERT INTO TABLE_NAME (column1, column2, column3,...columnN) VALUES (value1, value2, value3,...valueN)
            List<ContentValues> lcontentValues = new ArrayList<>();
            List<String> l_tabName = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject singleFormObj = jsonArray.getJSONObject(i);
                ContentValues mainvalues = new ContentValues();
                //mainvalues.put(tableName + "_SerialNo", "");//3
                mainvalues.put(tableName + "_fromServer", true);//4
                mainvalues.put(tableName + "_OrganisationID", i_OrgId);//4
                mainvalues.put(tableName + "_CreatedUserID", selOfflineHybridAppListPojoData.getAppDetails().getCreatedBy());//5
                mainvalues.put(tableName + "_FormName", dataCollectionObject.getApp_Name());//9
                mainvalues.put(tableName + "_SubFormName", "");//10
                mainvalues.put(tableName + "_AppVersion", selOfflineHybridAppListPojoData.getAppDetails().getAppVersion());//11
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
                            String key = (String) keys.next();//subForm Name
                            String subFormTableName = tableName + "_" + key;
                            JSONArray subformArray = subFormObj.getJSONArray(key);
                            for (int k = 0; k < subformArray.length(); k++) {
                                JSONObject singleSubFormObj = subformArray.getJSONObject(k);
                                ContentValues subFormvalues = new ContentValues();
                                // subFormvalues.put(subFormTableName + "_SerialNo", "");//3
                                subFormvalues.put(subFormTableName + "_fromServer", true);//4
                                subFormvalues.put(subFormTableName + "_OrganisationID", i_OrgId);//4
                                subFormvalues.put(subFormTableName + "_CreatedUserID", selOfflineHybridAppListPojoData.getAppDetails().getCreatedBy());//5
                                subFormvalues.put(subFormTableName + "_FormName", dataCollectionObject.getApp_Name());//9
                                subFormvalues.put(subFormTableName + "_SubFormName", key);//10
                                subFormvalues.put(subFormTableName + "_AppVersion", selOfflineHybridAppListPojoData.getAppDetails().getAppVersion());//11
                                subFormvalues.put(subFormTableName + "_SyncStatus", "2");//12
                                subFormvalues.put(subFormTableName + "_UserID", "");//13
                                subFormvalues.put(subFormTableName + "_IsCheckList", "");//15
                                subFormvalues.put(subFormTableName + "_CheckListNames", "");//16
                                keys = singleSubFormObj.keys();
                                while (keys.hasNext()) {
                                    key = (String) keys.next();
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
                                        subFormvalues.put(subFormTableName + "_DistributionID", singleSubFormObj.getString(key));//1
                                    } else if (key.equals("IMEI")) {
                                        //IMEI
                                        subFormvalues.put(subFormTableName + "_IMEI", singleSubFormObj.getString(key));//1
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
                dismissProgressDialog();
                ImproveHelper.my_showAlert(this, "", "Successfully Data Sync From Server ", "3");
                loadData();
            } else {
                dismissProgressDialog();
                ImproveHelper.my_showAlert(this, "", "Data Sync From Server Failed!\n\n" + errorMsg, "2");
            }
        } catch (Exception e) {
            dismissProgressDialog();
            ImproveHelper.my_showAlert(this, "", e.getMessage() + "\nData Sync From Server Failed! ", "2");
        }
    }*/
}