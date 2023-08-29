package com.bhargo.user.actions;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.OfflineSaveRequestPojo;
import com.bhargo.user.pojos.RequestSaveFilePojo;
import com.bhargo.user.realm.RealmTables;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.FileUploader;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncSaveRequest {
    com.bhargo.user.interfaces.Callback callbackListener;
    ProgressDialog pd;
    String progressMsg = "Please Wait! Sending SaveRequest...";
    String manageDataFailResult, manageDataSucessResult;
    boolean failFlag = false;


    GetServices getServices;
    ImproveDataBase improveDataBase;
    ImproveHelper improveHelper;
    SessionManager sessionManager;


    List<String> sendingFileIssues = new ArrayList<>();


    Context context;
    Thread FilesTM1, FilesTM2;
    String TAG = "ManageData";
    int fileIndex = 0;
    String[] InkeyNames = new String[0];
    Map<String, String> inputFilesMap = new HashMap<>();
    int sfFileIndex = 0;
    String[] entries = new String[0];
    String subformName = "";
    int sfPos = -1;
    String finalControlName;
    List<String> inputFilesForDML = new ArrayList<>();
    OfflineSaveRequestPojo offlineSaveRequestPojo;
    List<OfflineSaveRequestPojo> lofflineSaveRequestPojo;
    List<String> sendingIssues = new ArrayList<>();
    List<String> multiSendingIssues = new ArrayList<>();
    List<RequestSaveFilePojo> insertFiles = new ArrayList<>();
    List<RequestSaveFilePojo> updateFiles = new ArrayList<>();
    int sendingFilesCount = 0;
    boolean isMultiRecords = false;
    int multiRecordCount = 0;
    Handler handMultiRequests = new Handler() {
        public void handleMessage(android.os.Message msg) {
            try {
                nextSavedRequestForMultiCase();
            } catch (JSONException e) {
                e.printStackTrace();
                checkMultiRequestFailCase(e.getMessage());
                //failedCase(e);
            }
        }
    };

    public SyncSaveRequest(Context context, OfflineSaveRequestPojo offlineSaveRequestPojo,
                           com.bhargo.user.interfaces.Callback callbackListener) {
        this.context = context;
        this.callbackListener = callbackListener;
        this.offlineSaveRequestPojo = offlineSaveRequestPojo;
        isMultiRecords = false;
        improveHelper = new ImproveHelper();
        improveDataBase = new ImproveDataBase(context);
        getServices = RetrofitUtils.getUserService();
        sessionManager = new SessionManager(context);
        new SaveRequest().execute();
    }

    public SyncSaveRequest(Context context, List<OfflineSaveRequestPojo> lofflineSaveRequestPojo,
                           com.bhargo.user.interfaces.Callback callbackListener) {
        this.context = context;
        this.callbackListener = callbackListener;
        this.lofflineSaveRequestPojo = lofflineSaveRequestPojo;
        isMultiRecords = true;
        improveHelper = new ImproveHelper();
        improveDataBase = new ImproveDataBase(context);
        getServices = RetrofitUtils.getUserService();
        sessionManager = new SessionManager(context);
        new SaveRequest().execute();
    }

    public static String replaceWithUnderscore(String str) {
        str = str.replace(" ", "_");
        return str;
    }

    private void showProgressDialog(Context context, String msg) {
        if (pd != null && pd.isShowing()) {
            pd.setMessage(msg);
        } else {
            pd = new ProgressDialog(context);
            pd.setMessage(msg);
            pd.setCancelable(false);
            pd.show();
        }
    }
    Handler handInsertSendDataFiles = new Handler() {
        public void handleMessage(android.os.Message msg) {
            try {
                sendInsertFilesToServer();
            } catch (JSONException e) {
                e.printStackTrace();
                checkMultiRequestFailCase(e.getMessage());
                //failedCase(e);
                ///??? Stop Sending if any exception occur while sending files
            }
        }
    };

    private void closeProgressDialog() {
        try {
            if (pd != null)
                pd.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void successCase(String msg) {
        closeProgressDialog();
        callbackListener.onSuccess(msg);
    }

    private void failedCase(Throwable t) {
        closeProgressDialog();
        callbackListener.onFailure(t);
    }

    private void setManageDataStatusInGlobal(String response, String actionID) {
        try {
            JSONObject jObj = new JSONObject(response);
            LinkedHashMap<String, LinkedHashMap<String, String>> tempManageData = new LinkedHashMap<>();
            if (AppConstants.GlobalObjects.getManageData_ResponseHashMap() != null) {
                tempManageData = AppConstants.GlobalObjects.getManageData_ResponseHashMap();
            }
            LinkedHashMap<String, String> temp_response = tempManageData.get(actionID);
            if (temp_response == null) {
                temp_response = new LinkedHashMap<>();
            }
            //Status Code :Status
            temp_response.put("__Status Code".toLowerCase(), jObj.has("Status") ? jObj.getString("Status") : "");
            //Message :Message
            temp_response.put("__Message".toLowerCase(), jObj.has("Message") ? jObj.getString("Message") : "");
            String detailMsg = "";
            if (jObj.has("Details")) {
                JSONObject detailsObj = jObj.getJSONObject("Details");
                //Detailed Message:Detailedmessage
                detailMsg = detailsObj.has("Detailedmessage") ? detailsObj.getString("Detailedmessage") : "";
                temp_response.put("__Detailed Message".toLowerCase(), detailMsg);
                //Records Count:Effectedrows
                temp_response.put("__Records Count".toLowerCase(), detailsObj.has("Effectedrows") ? detailsObj.getString("Effectedrows") : "");
            } else {
                //Detailed Message:Detailedmessage
                temp_response.put("__Detailed Message".toLowerCase(), "");
                //Records Count:Rowcount
                temp_response.put("__Records Count".toLowerCase(), "");
            }
            tempManageData.put(actionID, temp_response);
            AppConstants.GlobalObjects.setManageData_ResponseHashMap(tempManageData);
            System.out.println("Response:" + AppConstants.GlobalObjects.getManageData_ResponseHashMap());
            if (jObj.getString("Status").equals("200")) {
                if(isMultiRecords){
                    //delete send save request in db after that continue for next request
                    deleteSaveRequest();
                }else{
                    successCase("Save Request Submitted Successfully");
                }
            } else {
                checkMultiRequestFailCase(jObj.getString("Message") + " " + detailMsg);
                //failedCase(new Throwable(jObj.getString("Message")+" "+detailMsg));
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, "ManageData", "setManageDataStatusInGlobal", e);
            checkMultiRequestFailCase(e.getMessage());
            //failedCase(e);
        }
    }
    private void deleteSaveRequest(){
        improveDataBase.deleteByValues(RealmTables.SaveRequestTable.TABLE_NAME_,
                new String[]{"rowid"},
                new String[]{offlineSaveRequestPojo.getRowID()});
        handMultiRequests.sendEmptyMessage(0);
    }
    Handler handUpdateSendDataFiles = new Handler() {
        public void handleMessage(android.os.Message msg) {
            try {
                sendUpdateFilesToServer();
            } catch (JSONException e) {
                e.printStackTrace();
                ///??? Stop Sending if any exception occur while sending files
                checkMultiRequestFailCase(e.getMessage());
                //failedCase(e);
            }
        }
    };

    private void execute() throws InterruptedException, JSONException {
        //check files exist or no, if exist files first
        // After that send obj with files urls
        sendingIssues.clear();
        if (isMultiRecords) {
            multiRecordCount = 0;
            offlineSaveRequestPojo = lofflineSaveRequestPojo.get(multiRecordCount);
        }
        processRequest();
    }

    private void processRequest() throws JSONException {
        if (offlineSaveRequestPojo.getQueryType().contentEquals("Wizard")) {
            wizardCase();
        } else if (offlineSaveRequestPojo.getQueryType().contentEquals("DML")) {
            savedDMLCase();
        } else if (offlineSaveRequestPojo.getQueryType().contentEquals("Group DML")) {
            groupDMLCase();
        } else if (offlineSaveRequestPojo.getQueryType().contentEquals("DirectSQL")) {
            directSqlCase();
        }
    }

    private void nextSavedRequestForMultiCase() throws JSONException {
        multiRecordCount++;
        if (multiRecordCount < lofflineSaveRequestPojo.size()) {
            offlineSaveRequestPojo = lofflineSaveRequestPojo.get(multiRecordCount);
            processRequest();
        } else {
            //check all savedrequests send or no
            if (multiSendingIssues.size() > 0) {
                failedCase(new Throwable(multiSendingIssues.toString()));
            } else {
                successCase("All Saved Requests Send Successfully!");
            }

        }

    }

    private void groupDMLCase() throws JSONException {
        //if file exists ,first send file after that submit string with update files urls
        insertFiles.clear();
        String[] file_controlNames_ = offlineSaveRequestPojo.getFilesControlNames().equals("") ? new String[0] : offlineSaveRequestPojo.getFilesControlNames().split(",");
        JSONArray arrayObj_ = new JSONArray(offlineSaveRequestPojo.getSendingObj());
        if (file_controlNames_.length == 0) {
            sendToServerGroupDMLCase();
        } else {
            for (int i_ = 0; i_ < arrayObj_.length(); i_++) {
                JSONObject jobj = arrayObj_.getJSONObject(i_);
                JSONArray inParameters = jobj.getJSONArray("InParameters");
                for (int j_ = 0; j_ < inParameters.length(); j_++) {
                    JSONObject ijobj = inParameters.getJSONObject(j_);
                    JSONArray rowData = ijobj.getJSONArray("RowData");
                    for (int files = 0; files < file_controlNames_.length; files++) {
                        String[] spiltFile = file_controlNames_[files].split("\\|");
                        String fileControlName = spiltFile[0];
                        for (int k_ = 0; k_ < rowData.length(); k_++) {
                            JSONObject kobj = rowData.getJSONObject(k_);
                            String key = kobj.getString("Key");
                            String value = kobj.getString("Value");
                            if (fileControlName.equals(key)) {
                                if (value != null && !value.equals("") && !value.startsWith("http")) {
                                    //store: i,j,k,path
                                    RequestSaveFilePojo fPojo = new RequestSaveFilePojo();
                                    fPojo.setFilePath(value);
                                    fPojo.setJarryPos(i_);
                                    fPojo.setSubJarryPos(j_);
                                    fPojo.setInnerSubJarryPos(k_);
                                    fPojo.setKey(key);
                                    insertFiles.add(fPojo);
                                }
                                break;
                            }
                        }
                    }
                }
            }

            //send files to server
            if (insertFiles.size() > 0) {
                sendingFilesCount = 0;
                sendInsertFilesToServer();
            } else {
                sendToServerGroupDMLCase();
            }
        }
    }

    private void sendToServerGroupDMLCase() throws JSONException {
        JSONArray jsonArray = new JSONArray(offlineSaveRequestPojo.getSendingObj());
        //files urls updating in sendingObj
        for (int i = 0; i < insertFiles.size(); i++) {
            RequestSaveFilePojo rsf = insertFiles.get(i);
            if (rsf.getServerFilePath() != null && rsf.getServerFilePath().startsWith("http")) {
                jsonArray.getJSONObject(rsf.getJarryPos()).getJSONArray("InParameters")
                        .getJSONObject(rsf.getSubJarryPos()).getJSONArray("RowData")
                        .getJSONObject(rsf.getInnerSubJarryPos()).put("Value", rsf.getServerFilePath());
            }
        }
        Map<String, String> dataJson = new HashMap<>();
        dataJson.put("OrgId", sessionManager.getOrgIdFromSession());
        dataJson.put("Uid", sessionManager.getUserTypeIdsFromSession());
        dataJson.put("Qname", offlineSaveRequestPojo.getExistingTableName());
        dataJson.put("Qtype", "DMLGroup");
        dataJson.put("InParameters", jsonArray.toString());
        System.out.println("dataJson==" + dataJson);
        //dismissProgressDialog();
        ImproveHelper.improveLog(TAG, "GetSQLorDMLPOST", new Gson().toJson(dataJson));

        Call<String> call = getServices.GetSQLorDMLPOST(sessionManager.getAuthorizationTokenId(), dataJson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("Result: ", "" + response.body());
                if (response.body() != null) {
                    setManageDataStatusInGlobal(response.body(), offlineSaveRequestPojo.getActionID());//GroupDML
                    //successCase("Save Request Submitted Successfully");
                } else {
                    checkMultiRequestFailCase(response.body());
                    //failedCase(new Throwable(response.body()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                try {
                    JSONObject jobj = new JSONObject();
                    jobj.put("CallDML_Execution", "Faild");
                    jobj.put("OutputData_Size", t.toString());

                    ImproveHelper.Controlflow("CallDML Execute", "Action", "CallDML", jobj.toString());
                } catch (JSONException e) {
                    ImproveHelper.improveException(context, TAG, "CallDML Execute", e);
                }
                checkMultiRequestFailCase(t.getMessage());
                //failedCase(t);
            }
        });
    }

    private void savedDMLCase() throws JSONException {
        //if file exists ,first send file after that submit string with update files urls
        //Sample Str
        //@school_photo|7|M
        //[{"RowId":"1","RowData":[{"Key":"@Bhargo_Emp_Id","Value":"BHAR00000004"},{"Key":"@Bhargo_PostID","Value":"1_1_1_01"},{"Key":"@school_id","Value":"123"},{"Key":"@school_name","Value":"Test School"},{"Key":"@phone","Value":""},{"Key":"@email","Value":"test@gmail.com"},{"Key":"@address","Value":""},{"Key":"@school_photo","Value":"\/storage\/emulated\/0\/Pictures\/Pollution control board Vijayawada IMAGES\/IMG_20230707_133420.jpg"},{"Key":"@school_location_Coordinates","Value":"37.4220936$-122.083922"},{"Key":"@school_location_Type","Value":"Single point GPS"}]}]
        //@school_photo|7|S
        //[{"RowId":1,"RowData":[{"Key":"@Bhargo_Emp_Id","Value":"BHAR00000004"},{"Key":"@Bhargo_PostID","Value":"1_1_1_01"},{"Key":"@school_id","Value":"424"},{"Key":"@school_name","Value":"testt"},{"Key":"@phone","Value":""},{"Key":"@email","Value":"test@gmail.com"},{"Key":"@address","Value":"address"},{"Key":"@school_photo","Value":"\/storage\/emulated\/0\/Pictures\/Pollution control board Vijayawada IMAGES\/IMG_20230707_133500.jpg"},{"Key":"@school_location_Coordinates","Value":""},{"Key":"@school_location_Type","Value":""}]},{"RowId":2,"RowData":[{"Key":"@Bhargo_Emp_Id","Value":"BHAR00000004"},{"Key":"@Bhargo_PostID","Value":"1_1_1_01"},{"Key":"@school_id","Value":"434"},{"Key":"@school_name","Value":"test"},{"Key":"@phone","Value":""},{"Key":"@email","Value":"test@gmail.com"},{"Key":"@address","Value":"address"},{"Key":"@school_photo","Value":"\/storage\/emulated\/0\/Pictures\/Pollution control board Vijayawada IMAGES\/IMG_20230707_133524.jpg"},{"Key":"@school_location_Coordinates","Value":""},{"Key":"@school_location_Type","Value":""}]}]
        insertFiles.clear();
        String[] file_controlNames = offlineSaveRequestPojo.getFilesControlNames().split(",");

        JSONArray arrayObj = new JSONArray(offlineSaveRequestPojo.getSendingObj());
        if (file_controlNames.length == 0) {
            sendToServerSavedDMLCase();
        } else {
            for (int i = 0; i < arrayObj.length(); i++) {
                JSONObject jobj = arrayObj.getJSONObject(i);
                JSONArray jarrayRowData = jobj.getJSONArray("RowData");
                for (int files = 0; files < file_controlNames.length; files++) {
                    String[] spiltFile = file_controlNames[files].split("\\|");
                    String fileControlName = spiltFile[0];
                    for (int j = 0; j < jarrayRowData.length(); j++) {
                        JSONObject mobj = jarrayRowData.getJSONObject(j);
                        String key = mobj.getString("Key");
                        String value = mobj.getString("Value");
                        if (fileControlName.equals(key)) {
                            //store: i,j,path
                            RequestSaveFilePojo fPojo = new RequestSaveFilePojo();
                            fPojo.setFilePath(value);
                            fPojo.setJarryPos(i);
                            fPojo.setSubJarryPos(j);
                            fPojo.setKey(key);
                            insertFiles.add(fPojo);
                            break;
                        }
                    }
                }
            }

            //send files to server
            if (insertFiles.size() > 0) {
                sendingFilesCount = 0;
                sendInsertFilesToServer();
            } else {
                sendToServerSavedDMLCase();
            }
        }
    }

    private void sendToServerSavedDMLCase() throws JSONException {
        JSONArray jsonArray = new JSONArray(offlineSaveRequestPojo.getSendingObj());
        //files urls updating in sendingObj
        for (int i = 0; i < insertFiles.size(); i++) {
            RequestSaveFilePojo rsf = insertFiles.get(i);
            if (rsf.getServerFilePath() != null && rsf.getServerFilePath().startsWith("http")) {
                jsonArray.getJSONObject(rsf.getJarryPos()).getJSONArray("RowData").getJSONObject(rsf.getSubJarryPos()).put("Value", rsf.getServerFilePath());
            }
        }
        Map<String, String> dataJson = new HashMap<>();
        dataJson.put("OrgId", sessionManager.getOrgIdFromSession());
        dataJson.put("Uid", sessionManager.getUserTypeIdsFromSession());
        dataJson.put("Qname", offlineSaveRequestPojo.getExistingTableName());
        dataJson.put("InParameters", jsonArray.toString());
        dataJson.put("Qtype", "DMLBulk");

        ImproveHelper.improveLog(TAG, "GetSQLorDMLPOST", new Gson().toJson(dataJson));

        Call<String> call = getServices.GetSQLorDMLPOST(sessionManager.getAuthorizationTokenId(), dataJson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("Result: ", "" + response.body());
                if (response.body() != null) {
                    setManageDataStatusInGlobal(response.body(), offlineSaveRequestPojo.getActionID());//DML
                    // successCase("Save Request Submitted Successfully");
                } else {
                    checkMultiRequestFailCase(response.body());
                    //failedCase(new Throwable(response.body()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                try {
                    JSONObject jobj = new JSONObject();
                    jobj.put("CallDML_Execution", "Faild");
                    jobj.put("OutputData_Size", t.toString());
                    ImproveHelper.Controlflow("CallDML Execute", "Action", "CallDML", jobj.toString());
                } catch (JSONException e) {
                    ImproveHelper.improveException(context, TAG, "CallDML Execute", e);
                }
                checkMultiRequestFailCase(t.getMessage());
                //failedCase(t);
            }
        });

    }

    private void directSqlCase() {
        JsonObject jo = (JsonObject) JsonParser.parseString(offlineSaveRequestPojo.getSendingObj());
        Call<String> call = getServices.GetDataDirectQuery(sessionManager.getAuthorizationTokenId(), jo);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("Result: ", "" + response.body());
                if (response.body() != null) {
                    setManageDataStatusInGlobal(response.body(), offlineSaveRequestPojo.getActionID());//DirectQuery
                    //successCase("Save Request Submitted Successfully");
                } else {
                    checkMultiRequestFailCase(response.body());
                    //failedCase(new Throwable(response.body()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailureCheck: " + t);
                try {
                    JSONObject jobj = new JSONObject();
                    jobj.put("CallSQL_Execution", "Faild");
                    jobj.put("OutputData_Size", t.toString());

                    ImproveHelper.Controlflow("CallSQL Execute", "Action", "CallSQL", jobj.toString());
                } catch (JSONException e) {
                    ImproveHelper.improveException(context, TAG, "CallSQL Execute", e);
                }
                checkMultiRequestFailCase(t.getMessage());
                //failedCase(t);
            }
        });
    }

    private void wizardCase() throws JSONException {
        insertFiles.clear();
        updateFiles.clear();
        String[] file_controlNames = offlineSaveRequestPojo.getFilesControlNames().split(",");
        JSONObject sendingObj = new JSONObject(offlineSaveRequestPojo.getSendingObj());
        if (file_controlNames.length == 0) {
            //sendObj to server
            sendToServerWizardCase();
        } else {
            //first send image file and update image in sending obj, after that sending obj to server
            JSONArray insertColumns = sendingObj.getJSONArray("insertColumns");
            JSONArray UpdateColumns = sendingObj.getJSONArray("UpdateColumns");
            //insert statements
            for (int i = 0; i < insertColumns.length(); i++) {
                JSONObject tempObj = insertColumns.getJSONObject(i);
                for (int f = 0; f < file_controlNames.length; f++) {
                    if (tempObj.has(file_controlNames[f])) {
                        String filePath = tempObj.getString(file_controlNames[f]);
                        if (filePath != null && !filePath.trim().equals("") && !filePath.trim().equals("http")) {
                            RequestSaveFilePojo rsfile = new RequestSaveFilePojo();
                            rsfile.setJarryPos(i);
                            rsfile.setKey(file_controlNames[f]);
                            rsfile.setFilePath(filePath);
                            insertFiles.add(rsfile);
                        }
                    }
                }
            }
            //update statements
            for (int i = 0; i < UpdateColumns.length(); i++) {
                JSONObject tempObj = UpdateColumns.getJSONObject(i).getJSONObject("setColumns");
                for (int f = 0; f < file_controlNames.length; f++) {
                    if (tempObj.has(file_controlNames[f])) {
                        String filePath = tempObj.getString(file_controlNames[f]);
                        if (filePath != null && !filePath.trim().equals("") && !filePath.trim().equals("http")) {
                            RequestSaveFilePojo rsfile = new RequestSaveFilePojo();
                            rsfile.setJarryPos(i);
                            rsfile.setKey(file_controlNames[f]);
                            rsfile.setFilePath(filePath);
                            updateFiles.add(rsfile);
                        }
                    }
                }
            }
            //send files to server
            if (insertFiles.size() > 0) {
                sendingFilesCount = 0;
                sendInsertFilesToServer();
            } else if (updateFiles.size() > 0) {
                sendingFilesCount = 0;
                sendUpdateFilesToServer();
            } else {
                sendToServerWizardCase();
            }
        }
    }

    private void sendInsertFilesToServer() throws JSONException {
        if (sendingFilesCount < insertFiles.size()) {
            RequestSaveFilePojo requestSaveFilePojo = insertFiles.get(sendingFilesCount);
            String filePath = requestSaveFilePojo.getFilePath();
            if(filePath!=null && !filePath.startsWith("http") && !filePath.equals("")){
                final String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
                String strUserId = sessionManager.getUserDataFromSession().getUserID();
                new FileUploader(context, fileName, strUserId, "SyncOperation", false, "BHARGO", new FileUploader.OnImageUploaded() {
                    @Override
                    public void response(String url) {
                        //update in db
                        if (url.contains("http")) {
                            requestSaveFilePojo.setServerFilePath(url);
                            insertFiles.set(sendingFilesCount, requestSaveFilePojo);
                            //send next image in current record
                            sendingFilesCount++;
                            handInsertSendDataFiles.sendEmptyMessage(0);
                        } else {
                            //??? Stop sending or continue if file failed ???
                            sendingIssues.add(sendingFilesCount + " Insert Record: File Sending Failed!" + "\n");
                            checkMultiRequestFailCase("File Sending Failed! " + url);
                            //failedCase(new Throwable("File Sending Failed! " + url));
                        /*//send next image in current record
                        sendingFilesCount++;
                        handInsertSendDataFiles.sendEmptyMessage(0);*/
                        }
                    }
                }).execute(filePath);
            }else{
                //send next image in current record
                sendingFilesCount++;
                handInsertSendDataFiles.sendEmptyMessage(0);
            }
        } else {
            if (offlineSaveRequestPojo.getQueryType().contentEquals("DML")) {
                sendToServerSavedDMLCase();
            } else if (offlineSaveRequestPojo.getQueryType().contentEquals("Group DML")) {
                sendToServerGroupDMLCase();
            } else {
                if (updateFiles.size() > 0) {
                    sendingFilesCount = 0;
                    sendUpdateFilesToServer();
                } else {
                    sendToServerWizardCase();
                }
            }
        }
    }

    private void sendUpdateFilesToServer() throws JSONException {
        if (sendingFilesCount < updateFiles.size()) {
            RequestSaveFilePojo requestSaveFilePojo = updateFiles.get(sendingFilesCount);
            String filePath = requestSaveFilePojo.getFilePath();
            if(filePath!=null && !filePath.startsWith("http") && !filePath.equals("")){
                final String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
                String strUserId = sessionManager.getUserDataFromSession().getUserID();
                new FileUploader(context, fileName, strUserId, "SyncOperation", false, "BHARGO", new FileUploader.OnImageUploaded() {
                    @Override
                    public void response(String url) {
                        //update in db
                        if (url.contains("http")) {
                            requestSaveFilePojo.setServerFilePath(url);
                            updateFiles.set(sendingFilesCount, requestSaveFilePojo);
                            //send next image in current record
                            sendingFilesCount++;
                            handUpdateSendDataFiles.sendEmptyMessage(0);
                        } else {
                            //??? Stop sending or continue if file failed ???
                            sendingIssues.add(sendingFilesCount + ")Update Record: File Sending Failed!" + "\n");
                            checkMultiRequestFailCase("File Sending Failed! " + url);
                            //failedCase(new Throwable("File Sending Failed! " + url));
                        /*//send next image in current record
                        sendingFilesCount++;
                        handUpdateSendDataFiles.sendEmptyMessage(0);*/
                        }
                    }
                }).execute(filePath);
            }else {
                //send next image in current record
                sendingFilesCount++;
                handUpdateSendDataFiles.sendEmptyMessage(0);
            }
        } else {
            sendToServerWizardCase();
        }
    }

    private void sendToServerWizardCase() throws JSONException {
        JSONObject sendingObj = new JSONObject(offlineSaveRequestPojo.getSendingObj());
        //files urls updating in sendingObj
        for (int i = 0; i < insertFiles.size(); i++) {
            RequestSaveFilePojo rsf = insertFiles.get(i);
            if (rsf.getServerFilePath() != null && rsf.getServerFilePath().startsWith("http")) {
                sendingObj.getJSONArray("insertColumns").getJSONObject(rsf.getJarryPos()).put(rsf.getKey(), rsf.getServerFilePath());
            }
        }
        for (int i = 0; i < updateFiles.size(); i++) {
            RequestSaveFilePojo rsf = updateFiles.get(i);
            if (rsf.getServerFilePath() != null && rsf.getServerFilePath().startsWith("http")) {
                sendingObj.getJSONArray("UpdateColumns").getJSONObject(rsf.getJarryPos()).getJSONObject("setColumns").put(rsf.getKey(), rsf.getServerFilePath());
            }
        }
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject) jsonParser.parse(sendingObj.toString());
        Call<String> call = getServices.GetManageDataWizardServerCase(sessionManager.getAuthorizationTokenId(), jo);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("Result: ", "" + response.body());
                if (response.body() != null) {
                    setManageDataStatusInGlobal(response.body(), offlineSaveRequestPojo.getActionID());//WizardCase
                } else {
                    checkMultiRequestFailCase(response.body());
                    //failedCase(new Throwable(response.body()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                checkMultiRequestFailCase(t.toString());
                //failedCase(t);
            }
        });
    }

    private void checkMultiRequestFailCase(String errorMsg) {
        if (isMultiRecords) {
            multiSendingIssues.add(multiRecordCount + "@" + errorMsg);
            //skip current request and process next request
            handMultiRequests.sendEmptyMessage(0);
        } else {
            failedCase(new Throwable(errorMsg));
        }
    }

    private class SaveRequest extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(context, progressMsg);
        }

        @Override
        protected synchronized Void doInBackground(Void... voids) {

            try {
                SyncSaveRequest.this.execute();
            } catch (Exception e) {
                manageDataFailResult = e.getMessage();
            }
            return null;
        }


        @Override
        protected synchronized void onPostExecute(Void result) {

            //
            if (manageDataFailResult != null && !manageDataFailResult.equals("") && !failFlag) {
                failedCase(new Throwable(manageDataFailResult));
            } else {
                // syncDataListener.onSuccess(syncSucessResult);
            }
            super.onPostExecute(result);
        }

    }






}
