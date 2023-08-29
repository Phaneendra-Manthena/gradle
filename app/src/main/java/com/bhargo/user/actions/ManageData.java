package com.bhargo.user.actions;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_NUMBER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CAMERA;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHECKBOX;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHECK_LIST;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DATA_CONTROL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DROP_DOWN;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_FILE_BROWSING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_GPS;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_GRID_CONTROL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_RADIO_BUTTON;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SUBFORM;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_RECORDING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VOICE_RECORDING;
import static com.bhargo.user.utils.AppConstants.Global_Single_Forms;
import static com.bhargo.user.utils.AppConstants.IS_MULTI_FORM;
import static com.bhargo.user.utils.AppConstants.Trans_id;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bhargo.user.Expression.ExpressionMainHelper;
import com.bhargo.user.Java_Beans.API_InputParam_Bean;
import com.bhargo.user.Java_Beans.API_OutputParam_Bean;
import com.bhargo.user.Java_Beans.ActionWithoutCondition_Bean;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.Java_Beans.ImageAdvanced_Mapped_Item;
import com.bhargo.user.Java_Beans.LanguageMapping;
import com.bhargo.user.Java_Beans.QueryFilterField_Bean;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.advanced.ChartControl;
import com.bhargo.user.controls.advanced.DataTableControl;
import com.bhargo.user.controls.advanced.DataViewer;
import com.bhargo.user.controls.advanced.GridControl;
import com.bhargo.user.controls.advanced.SectionControl;
import com.bhargo.user.controls.advanced.SubformView;
import com.bhargo.user.controls.standard.CalendarEventControl;
import com.bhargo.user.controls.standard.Camera;
import com.bhargo.user.controls.standard.FileBrowsing;
import com.bhargo.user.controls.standard.MapControl;
import com.bhargo.user.controls.standard.VideoRecording;
import com.bhargo.user.controls.standard.VoiceRecording;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppDetails;
import com.bhargo.user.pojos.DataViewerModelClass;
import com.bhargo.user.pojos.ServerCaseMD;
import com.bhargo.user.realm.JSONKeyValueType;
import com.bhargo.user.realm.RealmDBHelper;
import com.bhargo.user.realm.RealmTables;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ControlUtils;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageData {
    com.bhargo.user.interfaces.Callback callbackListener;
    ProgressDialog pd;
    String progressMsg = "Please Wait!";
    String manageDataFailResult, manageDataSucessResult;
    boolean failFlag = false;
    ActionWithoutCondition_Bean ActionObj;

    GetServices getServices;
    ImproveDataBase improveDataBase;
    ImproveHelper improveHelper;
    SessionManager sessionManager;
    List<List<ServerCaseMD>> ll_files_insert, ll_others_insert, ll_files_update, ll_others_update;
    List<JSONArray> ll_whereClause;
    int sendingFilesCount = 0;
    int sendingMultipleFileCount = 0;
    List<String> sendingFileIssues = new ArrayList<>();
    boolean serverCaseBoth = false;
    LinkedHashMap<String, ControlObject> map_DataForControlObj = new LinkedHashMap<String, ControlObject>();
    List<ServerCaseMD> l_files_update_temp;
    List<ServerCaseMD> l_files_insert_temp;
    boolean condetion;
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
    int fileCountHit = 0;
    boolean gpsContainInInParams = false;
    DataCollectionObject dataCollectionObject;
    LinkedHashMap<String, Object> List_ControlClassObjects;
    String Outputcolumns = "";
    String strAppName;
    public ManageData(Context context,
                      String strAppName,
                      ActionWithoutCondition_Bean ActionObj,
                      boolean condetion,
                      DataCollectionObject dataCollectionObject,
                      LinkedHashMap<String, Object> List_ControlClassObjects,
                      com.bhargo.user.interfaces.Callback callbackListener) {
        this.context = context;
        this.strAppName=strAppName;
        this.callbackListener = callbackListener;
        this.ActionObj = ActionObj;
        this.dataCollectionObject = dataCollectionObject;
        this.List_ControlClassObjects = List_ControlClassObjects;
        this.condetion = condetion;
        improveHelper = new ImproveHelper();
        improveDataBase = new ImproveDataBase(context);
        getServices = RetrofitUtils.getUserService();
        sessionManager = new SessionManager(context);
        //String android_device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        //System.out.println("android_device_id:"+android_device_id);
        new ManageDataStart().execute();
    }



    public static String replaceWithUnderscore(String str) {

        str = str.replace(" ", "_");

        return str;
    }

    private void init() {

        if (ActionObj.getDataBaseTableType().equalsIgnoreCase("Server")) {
            progressMsg = "Please Wait! Data Inserting Into Server Table...";
        } else if (ActionObj.getDataBaseTableType().equalsIgnoreCase("Mobile")) {
            progressMsg = "Please Wait! Data Inserting Into Local Table...";
        } else {
            progressMsg = "Please Wait! Data Inserting Into Local Table or Server Table...";
        }

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

    private void closeProgressDialog() {
        try {
            if (pd != null)
                pd.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private LinkedHashMap<String, List<String>> getDataSource() throws JSONException {
        map_DataForControlObj.clear();
        LinkedHashMap<String, List<String>> map_Data = new LinkedHashMap<String, List<String>>();
        String dataSource = ActionObj.getDML_DataSource_Value();
        String ValueType = dataSource.substring(4, dataSource.indexOf("."));
        String sourceName = dataSource.substring(4, dataSource.indexOf(")"));
        sourceName = sourceName.split("\\.")[1].toLowerCase();

        if (ValueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
            LinkedHashMap<String, JSONArray> autoLHM = MainActivity.getInstance().subFormsAutoNumberArraysMap;
            int totForms = 0;
            List<ControlObject> list_Controls = new ArrayList<>();
            if (sourceName.equalsIgnoreCase("grid_control")) {
                GridControl gridControl = (GridControl) MainActivity.getInstance().List_ControlClassObjects.get(sourceName);
                totForms = gridControl.getTableLayoutview().getChildCount();
                list_Controls = gridControl.controlObject.getSubFormControlList();
            } else {
                SubformView subview = (SubformView) MainActivity.getInstance().List_ControlClassObjects.get(sourceName);
                totForms = subview.ll_MainSubFormContainer.getChildCount();
                list_Controls = subview.controlObject.getSubFormControlList();
            }
            for (int i = 0; i < list_Controls.size(); i++) {
                ControlObject temp_controlObj = list_Controls.get(i);
                String keyName;
                if (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_RADIO_BUTTON)
                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DROP_DOWN)
                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECKBOX)
                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECK_LIST)
                        || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DATA_CONTROL)) {
                    List<String> Valuestr = ImproveHelper.getListOfValuesFromGlobalObject(context, dataSource.substring(0, dataSource.indexOf(")")) + "." + temp_controlObj.getControlName() + "_id_allrows)");
                    keyName = temp_controlObj.getControlName() + "_id";
                    map_Data.put(keyName, Valuestr);
                } else if (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_AUTO_NUMBER) || temp_controlObj.getControlType().contentEquals("AutoNumbers")) {
                    keyName = temp_controlObj.getControlName();
                    JSONObject autojson = autoLHM.get(sourceName).getJSONObject(0);
                    map_Data.put(keyName, getSubAndGridForm_autoNumbers(totForms, keyName, autojson));
                } else {
                    keyName = temp_controlObj.getControlName();
                    List<String> Valuestr = ImproveHelper.getListOfValuesFromGlobalObject(context, dataSource.substring(0, dataSource.indexOf(")")) + "." + temp_controlObj.getControlName() + "_allrows)");
                    map_Data.put(keyName, Valuestr);
                }
                if (!map_DataForControlObj.containsKey(keyName)) {
                    map_DataForControlObj.put(keyName, temp_controlObj);
                }

            }
        } else if (ValueType.equalsIgnoreCase(AppConstants.Global_API)) {
            map_Data = RealmDBHelper.getTableDataInLHM(context, sourceName);
        } else if (ValueType.equalsIgnoreCase(AppConstants.Global_GetData)) {
            map_Data = RealmDBHelper.getTableDataInLHM(context, sourceName);
        }
        return map_Data;
    }

    private List<String> getSubAndGridForm_autoNumbers(int totForms, String controlName, JSONObject autojson) throws JSONException {
        List<String> autonumberStrs = new ArrayList<>();
        String Suffix = (String) autojson.get("Suffix");
        String Prefix = (String) autojson.get("Prefix");
        String Suffix1 = (String) (autojson.has("Suffix1") ? autojson.get("Suffix1") : "");
        List<String> prefixStr = new ArrayList<>();
        List<String> suffix1Str = new ArrayList<>();

        if (Prefix.contains("(im:ControlName.")) {
            String fieldValue = ImproveHelper.getValueFromGlobalObject(context, Prefix);
            prefixStr.add(fieldValue);
        } else {
            prefixStr = ImproveHelper.getListOfValuesFromGlobalObject(context, Prefix);
        }
        if (Suffix1.contains("(im:ControlName.")) {
            String fieldValue = ImproveHelper.getValueFromGlobalObject(context, Suffix1);
            suffix1Str.add(fieldValue);
        } else {
            suffix1Str = ImproveHelper.getListOfValuesFromGlobalObject(context, Suffix1);
        }

        for (int i = 0; i < totForms; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ControlName", controlName);
            jsonObject.put("Suffix", Suffix);
            if (prefixStr.size() == 0) {
                jsonObject.put("Prefix", autojson.get("Prefix"));
            } else if (prefixStr.size() == 1) {
                jsonObject.put("Prefix", prefixStr.get(0));
            } else {
                jsonObject.put("Prefix", prefixStr.get(i));
            }
            if (suffix1Str.size() == 0) {
                jsonObject.put("Suffix1", autojson.get("Suffix1"));
            } else if (suffix1Str.size() == 1) {
                jsonObject.put("Suffix1", suffix1Str.get(0));
            } else {
                jsonObject.put("Suffix1", suffix1Str.get(i));
            }
            autonumberStrs.add(String.valueOf(jsonObject));
        }
        return autonumberStrs;
    }

    private void insertMultiContentValues(String tableNameMD, String trans_id, LinkedHashMap<String, List<String>> dataSourceLHM, List<Integer> updateSuccesIndex, boolean bothCase) throws JSONException {
        //All data as per DataSource
        String default_colName = "Bhargo";
        if (dataSourceLHM.size() > 0) {
            List<ContentValues> lcontentValues = new ArrayList<>();
            Set<String> resultSet = dataSourceLHM.keySet();
            String[] resultNames = resultSet.toArray(new String[resultSet.size()]);
            List<String> resultcolum = dataSourceLHM.get(resultNames[0]);
            String subFormAutoID = "";
            for (int row = 0; row < resultcolum.size(); row++) {
                ContentValues colvalCV = new ContentValues();
                //Table ColVal nk pending autoNumber

                for (int i = 0; i < ActionObj.getMainTableInsertFields().size(); i++) {
                    QueryFilterField_Bean insertFields = ActionObj.getMainTableInsertFields().get(i);
                    String columName = "";
                    if (insertFields.getField_Global_Value().trim().toLowerCase().startsWith("(im:")) {
                        columName = ImproveHelper.spilitandgetcolumnname(insertFields.getField_Global_Value().trim().toLowerCase());
                    } else {
                        columName = insertFields.getField_Global_Value().trim();
                    }
                    String inValue = "";
                    if (dataSourceLHM.containsKey(columName)) {
                        inValue = dataSourceLHM.get(columName).get(row);
                    } else {
                        inValue = ImproveHelper.getValueFromGlobalObject(context, insertFields.getField_Global_Value());
                    }
                    if (insertFields.isAutoNumber()) {
                        JSONObject autoObj = new JSONObject(inValue);
                        String[] autoKeyValues = MainActivity.getInstance().getAutoNumberKeyValuesManageData(autoObj, autoObj.getString("ControlName"), tableNameMD, subFormAutoID, insertFields.getField_Name());
                        subFormAutoID = autoKeyValues[1];
                        colvalCV.put(insertFields.getField_Name(), autoKeyValues[0]);//Prefix+Suffix+Suffix1
                        colvalCV.put("AutoIncrementVal", autoKeyValues[1]);//Suffix
                        colvalCV.put("AutoNumberJson", autoKeyValues[2]);//Json
                    } else {
                        colvalCV.put(insertFields.getField_Name(), inValue);
                    }

                }
                //Remaining ColValCV
                colvalCV.put(trans_id, ImproveHelper.getID(row + "_"));
                colvalCV.put(default_colName + "_Trans_Date", MainActivity.getInstance().getTransDateandTimeFromDevice());
                colvalCV.put(default_colName + "_Is_Active", "yes");
                colvalCV.put(default_colName + "_" + "OrganisationID", MainActivity.getInstance().strOrgId);
                colvalCV.put(default_colName + "_" + "CreatedUserID", MainActivity.getInstance().strCreatedBy);
                colvalCV.put(default_colName + "_" + "SubmittedUserID", MainActivity.getInstance().strUserId);
                colvalCV.put(default_colName + "_" + "DistributionID", MainActivity.getInstance().strDistributionId);
                colvalCV.put(default_colName + "_" + "IMEI", ImproveHelper.getMyDeviceId(context));
                colvalCV.put(default_colName + "_" + "FormName", MainActivity.getInstance().strAppName);
                colvalCV.put(default_colName + "_" + "SubFormName", "");
                colvalCV.put(default_colName + "_" + "AppVersion", MainActivity.getInstance().strAppVersion);
                colvalCV.put(default_colName + "_" + "SyncStatus", 0);
                colvalCV.put(default_colName + "_" + "UserID", sessionManager.getUserDataFromSession().getUserID());
                colvalCV.put(default_colName + "_" + "PostID", MainActivity.getInstance().strPostId);

                lcontentValues.add(colvalCV);
            }
            int totaleffect = lcontentValues.size();
            if (bothCase && updateSuccesIndex != null && updateSuccesIndex.size() > 0) {
                for (int i = 0; i < updateSuccesIndex.size(); i++) {
                    lcontentValues.remove(updateSuccesIndex.get(i));
                }
            }
            if (lcontentValues.size() > 0) {
                String result = improveDataBase.bulkContentValues(lcontentValues, tableNameMD);
                if (result.equals("")) {
                    if (bothCase) {
                        // successCase("Successfully Updated/Inserted!");
                        //successCase(getResponseData("200", "Success", "Updated/Inserted", totaleffect + ""));
                        successCase("Updated/Inserted","200");

                    } else {
                        //successCase("Successfully Inserted!");
                        //successCase(getResponseData("200", "Success", "Inserted", totaleffect + ""));
                        successCase("Inserted", "200");
                    }
                } else {
                    failedCase(new Throwable(getResponseData("100", "Failed", result, totaleffect + "")));
                }
            } else {
                failedCase(new Throwable(getResponseData("100", "Failed", "No Records!", totaleffect + "")));
            }
        } else {
            failedCase(new Throwable(getResponseData("100", "Failed", "No Records!", "0")));
        }
    }

    private String getResponseData(String statusCode, String msg, String detailedMsg, String effectedrows) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject detailsObj = new JSONObject();
            detailsObj.put("Detailedmessage", detailedMsg);
            detailsObj.put("Effectedrows", effectedrows);

            jsonObject.put("Status", statusCode);
            jsonObject.put("Message", msg);
            jsonObject.put("Details", detailsObj);
        } catch (Exception e) {
        }
        return jsonObject.toString();
    }

    private void updateMultiContentValues(String tableName, String trans_id, LinkedHashMap<String, List<String>> dataSourceLHM, String operationType) throws JSONException {
        //All data as per DataSource
        List<ContentValues> lupdateCV = new ArrayList<>();
        List<String> lwhereClauses = new ArrayList<>();
        if (dataSourceLHM.size() > 0) {

            Set<String> resultSet = dataSourceLHM.keySet();
            String[] resultNames = resultSet.toArray(new String[resultSet.size()]);
            List<String> resultcolum = dataSourceLHM.get(resultNames[0]);

            for (int row = 0; row < resultcolum.size(); row++) {
                ContentValues colvalCV = new ContentValues();
                //lupdateCV
                for (int i = 0; i < ActionObj.getMainTableUpdateFields().size(); i++) {
                    QueryFilterField_Bean updateFields = ActionObj.getMainTableUpdateFields().get(i);
                    String columName = "";
                    if (updateFields.getField_Global_Value().trim().toLowerCase().startsWith("(im:")) {
                        columName = ImproveHelper.spilitandgetcolumnname(updateFields.getField_Global_Value().trim().toLowerCase());
                    } else {
                        columName = updateFields.getField_Global_Value().trim();
                    }
                    String inValue = "";
                    if (dataSourceLHM.containsKey(columName)) {
                        inValue = dataSourceLHM.get(columName).get(row);
                    } else {
                        inValue = ImproveHelper.getValueFromGlobalObject(context, updateFields.getField_Global_Value());
                    }
                    colvalCV.put(updateFields.getField_Name(), inValue);
                }
                JSONArray jsonArrayFilters = new JSONArray();
                //lwhereClauses
                for (QueryFilterField_Bean filterValues : ActionObj.getMainTableWhereConditionFields()) {
                    if (!filterValues.isField_IsDeleted()) {
                        JSONObject jsonObjectMapExistingFilters = new JSONObject();
                        // String fieldValue = ImproveHelper.getValueFromGlobalObject(mainActivity, filterValues.getField_Global_Value());
                        String columName = "";
                        if (filterValues.getField_Global_Value().trim().toLowerCase().startsWith("(im:")) {
                            columName = ImproveHelper.spilitandgetcolumnname(filterValues.getField_Global_Value().trim().toLowerCase());
                        } else {
                            columName = filterValues.getField_Global_Value().trim();
                        }
                        String inValue = "";
                        if (dataSourceLHM.containsKey(columName)) {
                            inValue = dataSourceLHM.get(columName).get(row);
                        } else {
                            inValue = ImproveHelper.getValueFromGlobalObject(context, filterValues.getField_Global_Value());
                        }
                        jsonObjectMapExistingFilters.put("ColumnName", filterValues.getField_Name());
                        jsonObjectMapExistingFilters.put("Operator", getOperatorSymbol(filterValues.getField_Operator()));
                        jsonObjectMapExistingFilters.put("Value", inValue);
                        jsonObjectMapExistingFilters.put("Condition", filterValues.getField_and_or());
                        jsonObjectMapExistingFilters.put("ColumnType", "Others");
                        jsonObjectMapExistingFilters.put("NearBy", "");
                        jsonObjectMapExistingFilters.put("NoOfRec", "");
                        jsonObjectMapExistingFilters.put("CurrentGPS", "");
                        jsonArrayFilters.put(jsonObjectMapExistingFilters);
                    }
                }

                colvalCV.put("Bhargo" + "_" + "SyncStatus", 0);

                lupdateCV.add(colvalCV);
                lwhereClauses.add(getFilters(jsonArrayFilters));
            }
            if (lupdateCV.size() > 0) {
                List<Integer> updateSuccesIndex = new ArrayList<>();
                for (int i = 0; i < lupdateCV.size(); i++) {
                    String result = improveDataBase.updateByValuesForManageData(tableName, lupdateCV.get(i), lwhereClauses.get(i));
                    if (!result.equals("")) {
                        updateSuccesIndex.add(i);
                    }
                }
                if (updateSuccesIndex.size() == 0) {
                    //successCase("Successfully Updated!");
                   // successCase(getResponseData("200", "Success", "Updated!", lupdateCV.size() + ""));
                    successCase("Updated!","200");
                } else {
                    //run insert case
                    insertMultiContentValues(tableName, trans_id, dataSourceLHM, updateSuccesIndex, true);
                }

                /*String result = improveDataBase.multiUpdateByValuesForManageData(tableName, lupdateCV, lwhereClauses);
                if (result.equals("")) {
                    successCase("Successfully Updated!");
                } else {
                    if (operationType.equalsIgnoreCase("both")) {
                        //run insert case
                        insertMultiContentValues(tableName, trans_id, dataSourceLHM);
                    } else {
                        failedCase(result);
                    }
                }*/
            } else {
                failedCase(new Throwable("No Records!"));
            }
        } else {
            failedCase(new Throwable("No Records!"));
        }
    }

    private void manageDataIntoLocalTableSingle() throws JSONException {
        String tableName = ActionObj.getExistingTableName();
        if (improveDataBase.tableExists(tableName)) {
            AppDetails appDetails = improveDataBase.getAppDetailsBasedOnTableName(tableName);
            String trans_id = Trans_id;//replaceWithUnderscore(appDetails.getAppName()) + "_Trans_Id";
            //String trans_id = tableName + "_Trans_Id";
            if (ActionObj.getMapExistingType().equalsIgnoreCase("insert")) {
            /*ActionName = "insert"
  ActionType = "Manage Data"
  Active = true
  DataBaseTableType=Mobile
  connectionId = "238y589372895y38925y8358375"
  connectionName = "kabaddi"
  connectionSource = "Transcation"
  mainTableInsertFields = {
  Field_Global_Type = "ControlName"
  Field_Global_Value = "(im:ControlName.school_id)"
  Field_Name = "school_id"
  isNullAllowed = "YES"}
  manageDataActionType = "Wizard"
  mapExistingType = "Insert"
  existingTableName = "KABA00000001_School_Form_Offline"
  tableSettingsType = "Map existing table"
  shadow$_monitor_ = 0*/
                ContentValues colvalCV = getContentValues(tableName, "insert");
                String result = improveDataBase.insertintoTableForManageData(tableName, trans_id, colvalCV);
                if (result.equals("")) {
                    //successCase("Successfully Inserted!");
                    //successCase(getResponseData("200", "Success", "Inserted!", "1"));
                    successCase("Inserted!","200");
                } else {
                    //failedCase(result);
                    failedCase(new Throwable(getResponseData("100", "Failed", result, "0")));
                }
            } else if (ActionObj.getMapExistingType().equalsIgnoreCase("update")) {
            /*
ActionName = "update"
ActionType = "Manage Data"
Active = true
connectionId = "238y589372895y38925y8358375"
connectionName = "kabaddi"
connectionSource = "Transcation"
dataBaseTableType = "Mobile"
existingTableName = "KABA00000001_School_Form_Offline"
mainTableUpdateFields = {
  Field_Global_Value = "(im:ControlName.school_id)"
  Field_Name = "school_id"
  isNullAllowed = "YES"
 }
mainTableWhereConditionFields = {
  Field_Global_Value = "20"
  Field_Name = "school_id"
  Field_Operator = "Equals"
  Field_ValueType = "Others"
  Field_and_or = ""
  }
manageDataActionType = "Wizard"
mapExistingType = "Update"
tableSettingsType = "Map existing table"*/
                try {
                    ContentValues colvalCV = getContentValues(tableName, "update");
                    String whereClause = getWhereClause();
                    String result = improveDataBase.updateByValuesForManageData(tableName, colvalCV, whereClause);
                    if (result.equals("")) {
                        //successCase("Successfully Updated!");
                       //successCase(getResponseData("200", "Success", "Updated!", "1"));
                        successCase("Updated!","200");
                    } else {
                        //failedCase(result);
                        failedCase(new Throwable(getResponseData("100", "Failed", result, "0")));
                    }
                } catch (Exception e) {
                    //failedCase("Update Failed!\n" + e.getMessage());
                    failedCase(new Throwable(getResponseData("100", "Failed", "Update Failed!\n" + e.getMessage(), "0")));
                }
            } else {
                //both
                try {
                    ContentValues colvalCV = getContentValues(tableName, "update");
                    String whereClause = getWhereClause();
                    String result = improveDataBase.updateByValuesForManageData(tableName, colvalCV, whereClause);
                    if (result.equals("")) {
                        // successCase("Successfully Updated!");
                       // successCase(getResponseData("200", "Success", "Updated!", "1"));
                        successCase("Updated!", "200");
                    } else {
                        if (result.startsWith("Error_")) {
                            failedCase(new Throwable(result));
                        } else {
                            colvalCV = getContentValues(tableName, "insert");
                            result = improveDataBase.insertintoTableForManageData(tableName, trans_id, colvalCV);
                            if (result.equals("")) {
                                //successCase("Successfully Inserted!");
                                //successCase(getResponseData("200", "Success", "Inserted!", "1"));
                                successCase("Inserted!","200");
                            } else {
                                failedCase(new Throwable(result));
                            }
                        }
                    }
                } catch (Exception e) {
                    failedCase(new Throwable("Update Or Insert Failed!\n" + e.getMessage()));
                }
            }
        } else {
            failedCase(new Throwable("No Table In Local DataBase!"));
        }
    }

    private void manageDataIntoLocalTableMultiple() throws JSONException {
        String tableName = ActionObj.getExistingTableName();
        if (improveDataBase.tableExists(tableName)) {
            AppDetails appDetails = improveDataBase.getAppDetailsBasedOnTableName(tableName);
            String trans_id = Trans_id;//replaceWithUnderscore(appDetails.getAppName()) + "_Trans_Id";
            //String trans_id = tableName + "_Trans_Id";
            if (ActionObj.getMapExistingType().equalsIgnoreCase("insert")) {
                insertMultiContentValues(tableName, trans_id, getDataSource(), null, false);
            } else if (ActionObj.getMapExistingType().equalsIgnoreCase("update")) {
                try {
                    updateMultiContentValues(tableName, trans_id, getDataSource(), "update");
                } catch (Exception e) {
                    failedCase(new Throwable("Update Failed!\n" + e.getMessage()));
                }
            } else {
                //both
                try {
                    updateMultiContentValues(tableName, trans_id, getDataSource(), "both");
                } catch (Exception e) {
                    failedCase(new Throwable("Update Or Insert Failed!\n" + e.getMessage()));
                }
            }
        } else {
            failedCase(new Throwable("No Table In Local DataBase!"));
        }
    }

    private String getWhereClause() throws JSONException {
        JSONArray jsonArrayFilters = new JSONArray();
        for (QueryFilterField_Bean filterValues : ActionObj.getMainTableWhereConditionFields()) {
            if (!filterValues.isField_IsDeleted()) {
                JSONObject jsonObjectMapExistingFilters = new JSONObject();
                String fieldValue = ImproveHelper.getValueFromGlobalObject(context, filterValues.getField_Global_Value());
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
        return getFilters(jsonArrayFilters);
    }

    private void serverCaseMultiInsert(LinkedHashMap<String, List<String>> dataSourceLHM, String operateType) throws JSONException {
        //All data as per DataSource
        if (dataSourceLHM.size() > 0) {
            List<ContentValues> lcontentValues = new ArrayList<>();
            Set<String> resultSet = dataSourceLHM.keySet();
            String[] resultNames = resultSet.toArray(new String[resultSet.size()]);
            List<String> resultcolum = dataSourceLHM.get(resultNames[0]);

            for (int row = 0; row < resultcolum.size(); row++) {
                List<ServerCaseMD> temp_files = new ArrayList<>();
                List<ServerCaseMD> temp_others = new ArrayList<>();
                for (int i = 0; i < ActionObj.getMainTableInsertFields().size(); i++) {
                    QueryFilterField_Bean insertFields = ActionObj.getMainTableInsertFields().get(i);
                    String columName = "";
                    if (insertFields.getField_Global_Value().trim().toLowerCase().startsWith("(im:")) {
                        columName = ImproveHelper.spilitandgetcolumnname(insertFields.getField_Global_Value().trim().toLowerCase());
                    } else {
                        columName = insertFields.getField_Global_Value().trim();
                    }
                    String inValue = "";
                    ControlObject temp_controlObj = null;
                    if (dataSourceLHM.containsKey(columName)) {
                        inValue = dataSourceLHM.get(columName).get(row);
                        temp_controlObj = map_DataForControlObj.get(columName);
                    } else {
                        temp_controlObj = ImproveHelper.getControlObjFromGlobalObject(context, insertFields.getField_Global_Value());
                        inValue = ImproveHelper.getValueFromGlobalObject(context, insertFields.getField_Global_Value());
                        if (insertFields.isAutoNumber() && inValue.equalsIgnoreCase("")) {
                            String Prefix = temp_controlObj.getPreFixValue();
                            String Suffix1 = temp_controlObj.getSuffix1Value();
                            String Suffix = temp_controlObj.getSuffixValue();
                            String prefixStr = ImproveHelper.getValueFromGlobalObject(context, Prefix);
                            String suffix1Str = ImproveHelper.getValueFromGlobalObject(context, Suffix1);
                            JSONObject objauto = new JSONObject();
                            objauto.put("ControlName", temp_controlObj.getControlName());
                            objauto.put("Prefix", prefixStr);
                            objauto.put("Suffix", Suffix);
                            objauto.put("Suffix1", suffix1Str);
                            inValue = String.valueOf(objauto);
                        }
                    }
                    ServerCaseMD serverCaseMD = new ServerCaseMD();
                    serverCaseMD.setControlObject(temp_controlObj);
                    if (insertFields.isAutoNumber()) {
                        serverCaseMD.setColName("AutoIncrementControl");
                        JSONObject autoObj = new JSONObject(inValue);
                        autoObj.put("ControlName", insertFields.getField_Name());
                        serverCaseMD.setColValue(String.valueOf(autoObj));
                    } else {
                        serverCaseMD.setColName(insertFields.getField_Name());
                        serverCaseMD.setColValue(inValue);
                    }
                    serverCaseMD.setControlType(temp_controlObj.getControlType());
                    if (temp_controlObj != null && (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CAMERA) ||
                            temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_FILE_BROWSING) ||
                            temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_VOICE_RECORDING) ||
                            (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_VIDEO_RECORDING)))) {

                        serverCaseMD.setControlObject(temp_controlObj);

                        serverCaseMD.setControlName(temp_controlObj.getControlName());
                        temp_files.add(serverCaseMD);
                    } else {
                        temp_others.add(serverCaseMD);
                    }
                }
                ll_files_insert.add(temp_files);
                ll_others_insert.add(temp_others);
            }
            if (operateType.equals("both")) {
                //call update case also after insert case
                serverCaseMultipleUpdate(getDataSource(), operateType);
            } else {
                //Start Send
                startSendFiles(operateType);
            }
        } else {
            failedCase(new Throwable("No Records!"));
        }
    }

    private void serverCaseInsert() throws JSONException {
        List<ServerCaseMD> temp_files = new ArrayList<>();
        List<ServerCaseMD> temp_Others = new ArrayList<>();
        for (int i = 0; i < ActionObj.getMainTableInsertFields().size(); i++) {
            QueryFilterField_Bean insertFields = ActionObj.getMainTableInsertFields().get(i);
            ControlObject temp_controlObj = ImproveHelper.getControlObjFromGlobalObject(context, insertFields.getField_Global_Value());
            ServerCaseMD serverCaseMD = new ServerCaseMD();
            if (temp_controlObj != null) {
                serverCaseMD.setControlObject(temp_controlObj);
                serverCaseMD.setControlType(temp_controlObj.getControlType());
                serverCaseMD.setControlName(temp_controlObj.getControlName());
            }
            if (insertFields.isAutoNumber()) {
                JSONObject autojson = MainActivity.jArrayAutoIncementControls.getJSONObject(0);
                autojson.put("ControlName", insertFields.getField_Name());
               /* JSONObject jsonObject=new JSONObject();
                jsonObject.put("ControlName",insertFields.getField_Name());
                jsonObject.put("Prefix",autojson.get("Prefix"));
                jsonObject.put("Suffix",autojson.get("Suffix"));
                jsonObject.put("Suffix1",autojson.get("Suffix1"));*/
                //String fieldValue = ImproveHelper.getValueFromGlobalObject(mainActivity, insertFields.getField_Global_Value());
                serverCaseMD.setColName("AutoIncrementControl");
                serverCaseMD.setColValue(String.valueOf(autojson));
            } else {
                String fieldValue = ImproveHelper.getValueFromGlobalObject(context, insertFields.getField_Global_Value());
                serverCaseMD.setColName(insertFields.getField_Name());
                serverCaseMD.setColValue(fieldValue);
            }

            if (temp_controlObj != null && (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CAMERA) ||
                    temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_FILE_BROWSING) ||
                    temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_VOICE_RECORDING) ||
                    (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_VIDEO_RECORDING)))) {
                temp_files.add(serverCaseMD);
            } else {
                temp_Others.add(serverCaseMD);
            }
        }
        ll_files_insert.add(temp_files);
        ll_others_insert.add(temp_Others);
    }

    private void serverCaseMultipleUpdate(LinkedHashMap<String, List<String>> dataSourceLHM, String operateType) {
        //All data as per DataSource
        try {
            if (dataSourceLHM.size() > 0) {
                List<ContentValues> lcontentValues = new ArrayList<>();
                Set<String> resultSet = dataSourceLHM.keySet();
                String[] resultNames = resultSet.toArray(new String[resultSet.size()]);
                List<String> resultcolum = dataSourceLHM.get(resultNames[0]);

                for (int row = 0; row < resultcolum.size(); row++) {
                    List<ServerCaseMD> temp_files = new ArrayList<>();
                    List<ServerCaseMD> temp_others = new ArrayList<>();
                    for (int i = 0; i < ActionObj.getMainTableUpdateFields().size(); i++) {
                        QueryFilterField_Bean updateFields = ActionObj.getMainTableUpdateFields().get(i);
                        String columName = "";
                        if (updateFields.getField_Global_Value().trim().toLowerCase().startsWith("(im:")) {
                            columName = ImproveHelper.spilitandgetcolumnname(updateFields.getField_Global_Value().trim().toLowerCase());
                        } else {
                            columName = updateFields.getField_Global_Value().trim();
                        }
                        String inValue = "";
                        ControlObject temp_controlObj = null;
                        if (dataSourceLHM.containsKey(columName)) {
                            inValue = dataSourceLHM.get(columName).get(row);
                            temp_controlObj = map_DataForControlObj.get(columName);
                        } else {
                            temp_controlObj = ImproveHelper.getControlObjFromGlobalObject(context, updateFields.getField_Global_Value());
                            inValue = ImproveHelper.getValueFromGlobalObject(context, updateFields.getField_Global_Value());
                        }
                        ServerCaseMD serverCaseMD = new ServerCaseMD();
                        serverCaseMD.setColName(updateFields.getField_Name());
                        serverCaseMD.setColValue(inValue);
                        serverCaseMD.setControlObject(temp_controlObj);
                        if (temp_controlObj != null && (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CAMERA) ||
                                temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_FILE_BROWSING) ||
                                temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_VOICE_RECORDING) ||
                                (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_VIDEO_RECORDING)))) {

                            serverCaseMD.setControlObject(temp_controlObj);
                            serverCaseMD.setControlType(temp_controlObj.getControlType());
                            serverCaseMD.setControlName(temp_controlObj.getControlName());
                            temp_files.add(serverCaseMD);
                        } else {
                            temp_others.add(serverCaseMD);
                        }
                    }
                    ll_files_update.add(temp_files);
                    ll_others_update.add(temp_others);

                    JSONArray jsonArrayFilters = new JSONArray();
                    //lwhereClauses
                    for (QueryFilterField_Bean filterValues : ActionObj.getMainTableWhereConditionFields()) {
                        if (!filterValues.isField_IsDeleted()) {
                            JSONObject jsonObjectMapExistingFilters = new JSONObject();
                            // String fieldValue = ImproveHelper.getValueFromGlobalObject(mainActivity, filterValues.getField_Global_Value());
                            String columName = "";
                            if (filterValues.getField_Global_Value().trim().toLowerCase().startsWith("(im:")) {
                                columName = ImproveHelper.spilitandgetcolumnname(filterValues.getField_Global_Value().trim().toLowerCase());
                            } else {
                                columName = filterValues.getField_Global_Value().trim();
                            }
                            String inValue = "";
                            if (dataSourceLHM.containsKey(columName)) {
                                inValue = dataSourceLHM.get(columName).get(row);
                            } else {
                                inValue = ImproveHelper.getValueFromGlobalObject(context, filterValues.getField_Global_Value());
                            }
                            jsonObjectMapExistingFilters.put("ColumnName", filterValues.getField_Name());
                            jsonObjectMapExistingFilters.put("Operator", getOperatorSymbol(filterValues.getField_Operator()));
                            jsonObjectMapExistingFilters.put("Value", inValue);
                            jsonObjectMapExistingFilters.put("Condition", filterValues.getField_and_or());
                            jsonObjectMapExistingFilters.put("ColumnType", "Others");
                            jsonObjectMapExistingFilters.put("NearBy", "");
                            jsonObjectMapExistingFilters.put("NoOfRec", "");
                            jsonObjectMapExistingFilters.put("CurrentGPS", "");
                            jsonArrayFilters.put(jsonObjectMapExistingFilters);
                        }
                    }
                    ll_whereClause.add(jsonArrayFilters);
                }
                //Start Send update case or both
                startSendFiles(operateType);
            } else {
                failedCase(new Throwable("No Records!"));
            }
        } catch (Exception e) {
            failedCase(e);
        }

    }

    private void serverCaseUpdate(String operateType) {
        try {
            List<ServerCaseMD> temp_files = new ArrayList<>();
            List<ServerCaseMD> temp_others = new ArrayList<>();
            for (QueryFilterField_Bean setColumns : ActionObj.getMainTableUpdateFields()) {
                if (!setColumns.isField_IsDeleted()) {
                    ControlObject temp_controlObj = ImproveHelper.getControlObjFromGlobalObject(context, setColumns.getField_Global_Value());
                    String fieldValue = ImproveHelper.getValueFromGlobalObject(context, setColumns.getField_Global_Value());
                    ServerCaseMD serverCaseMD = new ServerCaseMD();
                    if (temp_controlObj != null) {
                        serverCaseMD.setControlObject(temp_controlObj);
                        serverCaseMD.setControlType(temp_controlObj.getControlType());
                        serverCaseMD.setControlName(temp_controlObj.getControlName());
                    }
                    serverCaseMD.setColName(setColumns.getField_Name());
                    serverCaseMD.setColValue(fieldValue);
                    if (temp_controlObj != null && (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CAMERA) ||
                            temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_FILE_BROWSING) ||
                            temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_VOICE_RECORDING) ||
                            (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_VIDEO_RECORDING)))) {
                        temp_files.add(serverCaseMD);
                    } else {
                        temp_others.add(serverCaseMD);
                    }
                }
            }
            ll_files_update.add(temp_files);
            ll_others_update.add(temp_others);

            JSONArray jsonArrayFilters = new JSONArray();
            //lwhereClauses
            for (QueryFilterField_Bean filterValues : ActionObj.getMainTableWhereConditionFields()) {
                JSONObject jsonObjectMapExistingFilters = new JSONObject();
                String fieldValue = ImproveHelper.getValueFromGlobalObject(context, filterValues.getField_Global_Value());

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
            ll_whereClause.add(jsonArrayFilters);
            startSendFiles(operateType);
        } catch (Exception e) {
            failedCase(e);
        }

    }

    private ContentValues getContentValues(String tableName, String operationType) throws JSONException {
        ContentValues colvalCV = new ContentValues();
        String tableNameDb = tableName;
        tableName = "Bhargo";
        colvalCV.put(tableName + "_Trans_Date", MainActivity.getInstance().getTransDateandTimeFromDevice());
        colvalCV.put(tableName + "_Is_Active", "yes");
        colvalCV.put(tableName + "_" + "OrganisationID", MainActivity.getInstance().strOrgId);
        colvalCV.put(tableName + "_" + "CreatedUserID", MainActivity.getInstance().strCreatedBy);
        colvalCV.put(tableName + "_" + "SubmittedUserID", MainActivity.getInstance().strUserId);
        colvalCV.put(tableName + "_" + "DistributionID", MainActivity.getInstance().strDistributionId);
        colvalCV.put(tableName + "_" + "IMEI", ImproveHelper.getMyDeviceId(context));
        colvalCV.put(tableName + "_" + "FormName", MainActivity.getInstance().strAppName);
        colvalCV.put(tableName + "_" + "SubFormName", "");
        colvalCV.put(tableName + "_" + "AppVersion", MainActivity.getInstance().strAppVersion);
        colvalCV.put(tableName + "_" + "SyncStatus", 0);
        colvalCV.put(tableName + "_" + "UserID", sessionManager.getUserDataFromSession().getUserID());
        colvalCV.put(tableName + "_" + "PostID", MainActivity.getInstance().strPostId);
        if (operationType.equalsIgnoreCase("insert")) {

            for (int i = 0; i < ActionObj.getMainTableInsertFields().size(); i++) {
                QueryFilterField_Bean insertFields = ActionObj.getMainTableInsertFields().get(i);
                if (insertFields.isAutoNumber()) {
                    JSONObject autoObj = MainActivity.jArrayAutoIncementControls.getJSONObject(0);
                    String[] autoKeyValues = MainActivity.getInstance().getAutoNumberKeyValues(autoObj, autoObj.getString("ControlName"), tableNameDb, "");
                    colvalCV.put(insertFields.getField_Name(), autoKeyValues[0]);//Prefix+Suffix+Suffix1
                    colvalCV.put("AutoIncrementVal", autoKeyValues[1]);//Suffix
                    colvalCV.put("AutoNumberJson", autoKeyValues[2]);//Json
                } else {
                    // ControlObject controlObject = ImproveHelper.getControlObjFromGlobalObject(mainActivity, insertFields.getField_Global_Value());
                    String fieldValue = ImproveHelper.getValueFromGlobalObject(context, insertFields.getField_Global_Value());
                    //String fieldValue = ImproveHelper.getValueFromGlobalObject(mainActivity, insertFields.getField_Global_Value());
                    colvalCV.put(insertFields.getField_Name(), fieldValue);
                }
            }


        } else if (operationType.equalsIgnoreCase("update")) {
            for (QueryFilterField_Bean setColumns : ActionObj.getMainTableUpdateFields()) {
                if (!setColumns.isField_IsDeleted()) {
                    String fieldValue = ImproveHelper.getValueFromGlobalObject(context, setColumns.getField_Global_Value());
                    colvalCV.put(setColumns.getField_Name(), fieldValue);
                }
            }
        }

        return colvalCV;

    }

    private String getFilters(JSONArray filterColumnsList) {
        StringBuilder filters = new StringBuilder();
        try {
            for (int i = 0; i < filterColumnsList.length(); i++) {
                JSONObject filterColumn = filterColumnsList.getJSONObject(i);
                filters.append(" ").append(filterColumn.get("ColumnName")).append(" ").append(filterColumn.get("Operator")).append(" ").append("'").append(filterColumn.get("Value")).append("'").append(" ").append(filterColumn.get("Condition"));
            }
        } catch (Exception e) {

        }
        return filters.toString();
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
            ImproveHelper.improveException(context, "ManageData", "getOperatorSymbol", e);
        }
        return operator;
    }

    private void successCase(String msg,String statusCode) {
        closeProgressDialog();
        if(statusCode.equals("200")){
            actionWiseSuccessMsg();
        }else{
            actionWiseFailureMsg();
        }

        callbackListener.onSuccess(msg);
    }

    private void actionWiseSuccessMsg(){
        if (ActionObj.isSuccessMessageIsEnable()) {
            ExpressionMainHelper ehelper = new ExpressionMainHelper();
            String Message = ehelper.ExpressionHelper(context, ActionObj.getMessage_Success());
            if (ActionObj.getMessage_SuccessDisplayType().equalsIgnoreCase("2")) {
                MainActivity.getInstance().ShowMessageDialogWithOk(context, Message, 2);
            } else {
                Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
            }
        } else if (!ActionObj.isSuccessMessageIsEnable()) {

        } else {
            //ImproveHelper.showToast(getApplicationContext(), "Data Recevied...");
        }
    }

    private void actionWiseFailureMsg(){
        if (ActionObj.isMessage_FailNoRecordsIsEnable()) {
            ExpressionMainHelper ehelper = new ExpressionMainHelper();
            String Message = ehelper.ExpressionHelper(context, ActionObj.getMessage_Fail());
            if (ActionObj.getMessage_FailNoRecordsDisplayType().equalsIgnoreCase("2")) {
                if (MainActivity.getInstance() != null)
                    MainActivity.getInstance().ShowMessageDialogWithOk(context, Message, 2);
            } else {
                Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
            }
        } else if (!ActionObj.isMessage_FailNoRecordsIsEnable()) {

        } else {
            ImproveHelper.showToast(context, "Your Transaction Failed  ...");
        }
    }

    private void failedCase(Throwable t) {
        closeProgressDialog();
        actionWiseFailureMsg();
        callbackListener.onFailure(t);
    }

    private void manageDataIntoServerTable() throws JSONException {
        ll_files_insert = new ArrayList<>();
        ll_others_insert = new ArrayList<>();
        ll_files_update = new ArrayList<>();
        ll_others_update = new ArrayList<>();
        ll_whereClause = new ArrayList<>();

        if (ActionObj.getDML_Input_Type().equalsIgnoreCase("multiple")) {
            if (ActionObj.getMapExistingType().equalsIgnoreCase("insert")) {
                serverCaseMultiInsert(getDataSource(), "insert");
            } else if (ActionObj.getMapExistingType().equalsIgnoreCase("update")) {
                serverCaseMultipleUpdate(getDataSource(), "update");
            } else {
                //both insert & update
                serverCaseMultiInsert(getDataSource(), "both");
            }
        } else {
            //single Case
            if (ActionObj.getMapExistingType().equalsIgnoreCase("insert")) {
                serverCaseInsert();
                startSendFiles("insert");
            } else if (ActionObj.getMapExistingType().equalsIgnoreCase("update")) {
                serverCaseUpdate("update");
            } else {
                serverCaseInsert();
                serverCaseUpdate("both");
            }
        }


    }

    private void startSendFiles(String type) {
        //internet check then saverequest
        if (type.equals("insert")) {
            if (ll_files_insert.size() > 0) {
                //first send file Paths
                sendingFilesCount = 0;
                sendingMultipleFileCount = 0;
                sendFilesToServer_insertForMultiple();
            } else {
                sendDataToServer_insert();
            }
        } else if (type.equals("update")) {
            if (ll_files_update.size() > 0) {
                //first send file Paths
                sendingFilesCount = 0;
                sendingMultipleFileCount = 0;
                sendFilesToServer_updateForMultiple();
            } else {
                sendDataToServer_update();
            }
        } else {
            //both
            sendingFilesCount = 0;
            sendingMultipleFileCount = 0;
            sendFilesToServer_insert_update();
        }

    }

    private void sendFilesToServer_updateForMultiple() {
        if (sendingMultipleFileCount < ll_files_update.size()) {
            sendingFilesCount = 0;
            l_files_update_temp = ll_files_update.get(sendingMultipleFileCount);
            sendFilesToServer_update();
        } else {
            sendDataToServer_update();
        }
    }

    private void sendFilesToServer_update() {
        if (sendingFilesCount < l_files_update_temp.size()) {
            ServerCaseMD serverCaseMD = l_files_update_temp.get(sendingFilesCount);
            String filePath = serverCaseMD.getColValue();
            if(filePath!=null && !filePath.equals("") && !filePath.startsWith("http")){
                final String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
                String strUserId = sessionManager.getUserDataFromSession().getUserID();
                if(isNetworkStatusAvialable(context)){
                    new FileUploader(context, fileName, strUserId, "SyncOperation", false, "BHARGO", new FileUploader.OnImageUploaded() {
                        @Override
                        public void response(String url) {
                            //update in pojo
                            if (url.contains("http")) {
                                l_files_update_temp.get(sendingFilesCount).setColValue(url);
                                ll_files_update.get(sendingMultipleFileCount).get(sendingFilesCount).setColValue(url);
                                //send next image in current record
                                sendingFilesCount++;
                                handSendDataFiles_update.sendEmptyMessage(0);
                            } else {
                                sendingFileIssues.add(url + " File Sending Failed!" + "\n");
                                failedCase(new Throwable(url + " File Sending Failed!"));
                            }

                        }
                    }).execute(filePath);
                }else{
                    //no internet : pre
                    if (ActionObj.getSaveOfflineType().equalsIgnoreCase("Request")) {
                        sendDataToServer();
                    }else{
                        failedCase(new Throwable(context.getString(R.string.no_internet)));
                    }
                }
            }else{
                //send next image in current record
                sendingFilesCount++;
                handSendDataFiles_update.sendEmptyMessage(0);
            }
        } else {
            sendingMultipleFileCount++;
            sendFilesToServer_updateForMultiple();
        }
    }

    private void sendFilesToServer_insert_update() {
        serverCaseBoth = true;
        sendFilesToServer_insertForMultiple();
    }

    private void sendFilesToServer_insertForMultiple() {
        if (sendingMultipleFileCount < ll_files_insert.size()) {
            sendingFilesCount = 0;
            l_files_insert_temp = ll_files_insert.get(sendingMultipleFileCount);
            sendFilesToServer_insert();
        } else {
            if (serverCaseBoth) { //both case insert done now update case start
                sendingFilesCount = 0;
                sendingMultipleFileCount = 0;
                serverCaseBoth = false;
                sendFilesToServer_updateForMultiple();
            } else {
                sendDataToServer_insert();
            }
        }
    }

    private void sendFilesToServer_insert() {
        if (sendingFilesCount < l_files_insert_temp.size()) {
            ServerCaseMD serverCaseMD = l_files_insert_temp.get(sendingFilesCount);
            String filePath = serverCaseMD.getColValue();
            if(filePath!=null && !filePath.equals("") && !filePath.startsWith("http")){
                final String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
                String strUserId = sessionManager.getUserDataFromSession().getUserID();
                if(isNetworkStatusAvialable(context)){
                    new FileUploader(context, fileName, strUserId, "SyncOperation", false, "BHARGO", new FileUploader.OnImageUploaded() {
                        @Override
                        public void response(String url) {
                            //update in pojo
                            if (url.contains("http")) {
                                l_files_insert_temp.get(sendingFilesCount).setColValue(url);
                                ll_files_insert.get(sendingMultipleFileCount).get(sendingFilesCount).setColValue(url);
                                //send next image in current record
                                sendingFilesCount++;
                                handSendDataFiles_insert.sendEmptyMessage(0);
                            } else {
                                sendingFileIssues.add(url + " File Sending Failed!" + "\n");
                                failedCase(new Throwable(url + " File Sending Failed!"));
                            }
                        }
                    }).execute(filePath);
                }else{
                    //no internet : prepare string for saveRequest
                    if (ActionObj.getSaveOfflineType().equalsIgnoreCase("Request")) {
                        sendDataToServer();
                    }else{
                        failedCase(new Throwable(context.getString(R.string.no_internet)));
                    }
                }

            }else{
                //send next image in current record
                sendingFilesCount++;
                handSendDataFiles_insert.sendEmptyMessage(0);
            }

        } else {
            sendingMultipleFileCount++;
            sendFilesToServer_insertForMultiple();
        }
    }

    private void sendDataToServer_update() {
        sendDataToServer();
    }

    private void sendDataToServer_insert() {
        sendDataToServer();
    }

    private void sendDataToServer() {
        try {

            JSONObject mainObject = new JSONObject();
            mainObject = new JSONObject();

            mainObject.put("ConnectionID", ActionObj.getConnectionId());
            mainObject.put("OrgId", MainActivity.getInstance().strOrgId);
            mainObject.put("PageName", MainActivity.getInstance().strAppName);
            mainObject.put("CreatedUserID", MainActivity.getInstance().strCreatedBy);
            mainObject.put("PostId", MainActivity.getInstance().strPostId);
            mainObject.put("SubmittedUserPostID", MainActivity.getInstance().strPostId);
            mainObject.put("SubmittedUserID", MainActivity.getInstance().strUserId);
            mainObject.put("DistributionID", MainActivity.getInstance().strDistributionId);
            mainObject.put("IMEI", ImproveHelper.getMyDeviceId(context));
            mainObject.put("OperationType", "");
            mainObject.put("TransID", "");
            mainObject.put("Autonumber_Mainform_and_subform", "false");
            mainObject.put("TableName", ActionObj.getExistingTableName());
            mainObject.put("TypeofSubmission", ActionObj.getMapExistingType());
            mainObject.put("IfautoincrementControls", "false");
            if (ActionObj.getMapExistingType().equalsIgnoreCase("Insert")) {
                JSONArray insertJsonArray = new JSONArray();
                for (int i = 0; i < ll_others_insert.size(); i++) {
                    JSONObject jsonObjectMapExistingInsert = new JSONObject();
                    List<ServerCaseMD> temp_other = ll_others_insert.get(i);
                    List<ServerCaseMD> temp_files = ll_files_insert.get(i);
                    boolean autoNumberKeyExist = false;
                    for (int j = 0; j < temp_other.size(); j++) {
                        if (temp_other.get(j).getControlObject().getControlType() != null &&
                                temp_other.get(j).getControlObject().getControlType().equalsIgnoreCase(CONTROL_TYPE_GPS)) {
                            gpsContainInInParams = true;
                        }
                        if (temp_other.get(j).getColName() != null && temp_other.get(j).getColName().equalsIgnoreCase("AutoIncrementControl")) {
                            autoNumberKeyExist = true;
                            jsonObjectMapExistingInsert.put(temp_other.get(j).getColName(), new JSONObject(temp_other.get(j).getColValue()));
                        } else {
                            jsonObjectMapExistingInsert.put(temp_other.get(j).getColName(), temp_other.get(j).getColValue());
                        }
                    }
                    if (!autoNumberKeyExist) {
                        jsonObjectMapExistingInsert.put("AutoIncrementControl", new JSONObject());
                    } else {
                        mainObject.put("IfautoincrementControls", "true");
                    }
                    for (int j = 0; j < temp_files.size(); j++) {
                        jsonObjectMapExistingInsert.put(temp_files.get(j).getColName(), temp_files.get(j).getColValue());
                    }
                    insertJsonArray.put(jsonObjectMapExistingInsert);
                }
                mainObject.put("insertColumns", insertJsonArray);
                mainObject.put("UpdateColumns", new JSONArray());
                Log.d("ManageData", "run: " + "Insert");
            } else if (ActionObj.getMapExistingType().equalsIgnoreCase("Update")) {
                JSONArray updateJsonArray = new JSONArray();
                for (int r = 0; r < ll_others_update.size(); r++) {
                    JSONObject jsonObjectMapExistingSetColumns = new JSONObject();

                    List<ServerCaseMD> temp_others = ll_others_update.get(r);
                    List<ServerCaseMD> temp_files = ll_files_update.get(r);
                    JSONArray temp_where = ll_whereClause.get(r);

                    for (int i = 0; i < temp_others.size(); i++) {
                        jsonObjectMapExistingSetColumns.put(temp_others.get(i).getColName(), temp_others.get(i).getColValue());
                    }
                    for (int i = 0; i < temp_files.size(); i++) {
                        jsonObjectMapExistingSetColumns.put(temp_files.get(i).getColName(), temp_files.get(i).getColValue());
                    }

                    JSONObject updateColumnsObject = new JSONObject();
                    updateColumnsObject.put("setColumns", jsonObjectMapExistingSetColumns);
                    updateColumnsObject.put("filterColumns", temp_where);
                    updateJsonArray.put(updateColumnsObject);
                }
                mainObject.put("insertColumns", new JSONArray());
                mainObject.put("UpdateColumns", updateJsonArray);
                Log.d("ManageData", "run: " + "UpdateInsert");
            } else if (ActionObj.getMapExistingType().equalsIgnoreCase("Insert or Update")) {
                //update
                JSONArray updateJsonArray = new JSONArray();
                for (int r = 0; r < ll_others_update.size(); r++) {
                    JSONObject jsonObjectMapExistingSetColumns = new JSONObject();

                    List<ServerCaseMD> temp_others = ll_others_update.get(r);
                    List<ServerCaseMD> temp_files = ll_files_update.get(r);
                    JSONArray temp_where = ll_whereClause.get(r);

                    for (int i = 0; i < temp_others.size(); i++) {
                        jsonObjectMapExistingSetColumns.put(temp_others.get(i).getColName(), temp_others.get(i).getColValue());
                    }
                    for (int i = 0; i < temp_files.size(); i++) {
                        jsonObjectMapExistingSetColumns.put(temp_files.get(i).getColName(), temp_files.get(i).getColValue());
                    }

                    JSONObject updateColumnsObject = new JSONObject();
                    updateColumnsObject.put("setColumns", jsonObjectMapExistingSetColumns);
                    updateColumnsObject.put("filterColumns", temp_where);
                    updateJsonArray.put(updateColumnsObject);
                }
                mainObject.put("UpdateColumns", updateJsonArray);
                //insert
                JSONArray insertJsonArray = new JSONArray();
                for (int i = 0; i < ll_others_insert.size(); i++) {
                    JSONObject jsonObjectMapExistingInsert = new JSONObject();
                    List<ServerCaseMD> temp_other = ll_others_insert.get(i);
                    List<ServerCaseMD> temp_files = ll_files_insert.get(i);
                    boolean autoNumberKeyExist = false;
                    for (int j = 0; j < temp_other.size(); j++) {
                        if (temp_other.get(j).getColName().equalsIgnoreCase("AutoIncrementControl")) {
                            autoNumberKeyExist = true;
                            jsonObjectMapExistingInsert.put(temp_other.get(j).getColName(), new JSONObject(temp_other.get(j).getColValue()));
                        } else {
                            jsonObjectMapExistingInsert.put(temp_other.get(j).getColName(), temp_other.get(j).getColValue());
                        }
                    }
                    if (!autoNumberKeyExist) {
                        jsonObjectMapExistingInsert.put("AutoIncrementControl", new JSONObject());
                    } else {
                        mainObject.put("IfautoincrementControls", "true");
                    }
                    for (int j = 0; j < temp_files.size(); j++) {
                        jsonObjectMapExistingInsert.put(temp_files.get(j).getColName(), temp_files.get(j).getColValue());
                    }
                    insertJsonArray.put(jsonObjectMapExistingInsert);
                }
                mainObject.put("insertColumns", insertJsonArray);
            }
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(mainObject.toString());
            if (isNetworkStatusAvialable(context)) {
                Call<String> call = getServices.GetManageDataWizardServerCase(sessionManager.getAuthorizationTokenId(), jo);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.e("Result: ", "" + response.body());
                        if (response.body() != null) {
                            //Saved Response in Global if Data key exist save in realmDB and bind to controls
                            setManageDataStatusInGlobal(response.body(), ActionObj);//Wizard Case
                           // processManageDataResponse(response.body());
                            //successCase(response.body());
                        } else {
                            failedCase(new Throwable(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        failedCase(t);
                    }
                });
            } else {
                saveRequestOffline(mainObject.toString());//Wizard Case
            }


        } catch (Exception e) {
            failedCase(e);
        }
    }

    private LinkedHashMap<String, List<String>> ConvertResponseToLMP(String response, LinkedHashMap<String, List<String>> OutputData) {
        List<JSONKeyValueType> cols = RealmDBHelper.getJsonKeyAndValues(response);
        for (int i = 0; i < cols.size(); i++) {
            JSONKeyValueType col = cols.get(i);
            if (col.getType() != null && col.getType().equalsIgnoreCase("JSONObject")) {
                ConvertResponseToLMP(col.getValue(), OutputData);
            } else if (col.getType() != null && col.getType().equalsIgnoreCase("JSONArray")) {
                ConvertResponseToLMP(col.getValue(), OutputData);
            } else {
                List<String> vals = new ArrayList<>();
                vals.add(col.getValue());
                OutputData.put(col.getKey().toLowerCase(), vals);
            }
        }
        return OutputData;
    }

    private void processManageDataResponse(String response,String statusMsg,String statusCode) {
        if (ActionObj.getResult_NoOfRecords().equalsIgnoreCase("none")) {
            successCase(statusMsg,statusCode);
        } else {
            if (ActionObj.getActionId() != null & !ActionObj.getActionId().equals("")) {
                //insert into realm with formName as TableName :ActionObj.getActionId()
                if (RealmDBHelper.existTable(context, ActionObj.getActionId())) {
                    RealmDBHelper.deleteTable(context, ActionObj.getActionId());
                }
                //RealmDBHelper.createTableFromAction(context, ActionObj.getActionId(), response);
                //RealmDBHelper.insertFromAction(context, ActionObj.getActionId(), response);
                 RealmDBHelper.createTableWithInsertFromAction(context, ActionObj.getActionId(), response);
                 RealmDBHelper.updateSaveOfflineTable(context, strAppName, ActionObj.getActionId(), ActionObj.getActionType(), "Offline");
            }
            Outputcolumns = "";
            String Outputcolumns_copy = "";
            for (int i = 0; i < ActionObj.getList_Form_OutParams().size(); i++) {
                if (ActionObj.getList_Form_OutParams().get(i).getOutParam_Mapped_ID() != null && !ActionObj.getList_Form_OutParams().get(i).getOutParam_Mapped_ID().equals("")) {
                    Outputcolumns = Outputcolumns + "," + ActionObj.getList_Form_OutParams().get(i).getOutParam_Mapped_ID();
                    Outputcolumns_copy = Outputcolumns_copy + ",[" + ActionObj.getList_Form_OutParams().get(i).getOutParam_Mapped_ID() + "]";
                } else if (ActionObj.getList_Form_OutParams().get(i).getList_OutParam_Languages() != null && ActionObj.getList_Form_OutParams().get(i).getList_OutParam_Languages().size() > 0) {
//                            String appLanguage = ImproveHelper.getLocale(context);
                    List<LanguageMapping> languageMappings = ActionObj.getList_Form_OutParams().get(i).getList_OutParam_Languages();
                    for (int j = 0; j < languageMappings.size(); j++) {
                        Outputcolumns = Outputcolumns + "," + languageMappings.get(j).getOutParam_Lang_Mapped();
                    }
                }
            }
            if (gpsContainInInParams) {
                Outputcolumns = Outputcolumns + "," + "DistanceInKM";
            }
            Outputcolumns = Outputcolumns.substring(Outputcolumns.indexOf(",") + 1);
            Outputcolumns_copy = Outputcolumns_copy.substring(Outputcolumns_copy.indexOf(",") + 1);
            Log.e("Result: ", "" + Outputcolumns.split("\\,").toString());
            LinkedHashMap<String, List<String>> OutputData = RealmDBHelper.getTableDataBaseOnDataTableColBeanInLinkedHashMap(context, ActionObj.getActionId(), ActionObj.getDataTableColumn_beanList(), ActionObj.getList_Form_OutParams());
            //LinkedHashMap<String, List<String>> OutputData = ConvertResponseToLMP(response, new LinkedHashMap<>());
            setDataToSingleOrMultpileControls(OutputData,statusMsg,statusCode);
        }

    }

    private void setDataToSingleOrMultpileControls(LinkedHashMap<String, List<String>> OutputData,String statusMsg,String statusCode) {
        MainActivity.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (OutputData != null && OutputData.size() != 0) {
                    ImproveHelper.improveLog(TAG, "CallFormFields_OutputData", new Gson().toJson(OutputData));
                    try {
                        JSONObject jobj = new JSONObject();
                        jobj.put("CallFormFields_Execution", "Success");
                        jobj.put("OutputData_Size", OutputData.size());
                        ImproveHelper.Controlflow("CallFormFields Execute", "Action", "CallFormFields", jobj.toString());
                    } catch (JSONException e) {
                        ImproveHelper.improveException(context, TAG, "CallFormFields Execute", e);
                    }
                    List<API_OutputParam_Bean> list_Form_OutParams = ActionObj.getList_Form_OutParams();
                    Log.e("Result: ", "" + list_Form_OutParams.toString());
                    if (ActionObj.getResult_NoOfRecords().equalsIgnoreCase("Single")) {

                        for (int i = 0; i < list_Form_OutParams.size(); i++) {
                            if (!list_Form_OutParams.get(i).isOutParam_Delete()) {
                                ControlUtils.setSingleValuetoControlFromCallAPIORGetData(context, OutputData, list_Form_OutParams.get(i),
                                        dataCollectionObject, List_ControlClassObjects);
                            }
                        }
                        successCase(statusMsg,statusCode);
                    } else if (ActionObj.getResult_NoOfRecords().equalsIgnoreCase("none")) {
                        successCase(statusMsg,statusCode);
                    } else {
                        System.out.println("ActionObj.getResult_DisplayType()==" + ActionObj.getResult_DisplayType());
                        String SelectedSubForm = ActionObj.getSelectedSubForm();
                        if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_SUBFORM)) {
                            SubformView subform = (SubformView) List_ControlClassObjects.get(SelectedSubForm);
                            subform.saveNewRowData(OutputData, list_Form_OutParams);
                            subform.loadSubFormData(ActionObj, MainActivity.getInstance().subFormMapControls, MainActivity.getInstance().subFormMappedValues);
                            subform.setCallbackListener(new com.bhargo.user.interfaces.Callback() {
                                @Override
                                public void onSuccess(Object result) {
                                    successCase(statusMsg,statusCode);
                                }

                                @Override
                                public void onFailure(Throwable throwable) {
                                    failedCase(throwable);
                                }
                            });
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_GRIDFORM)) {
                            GridControl gridForm = (GridControl) List_ControlClassObjects.get(SelectedSubForm);
                            gridForm.saveNewRowData(OutputData, list_Form_OutParams);
                            gridForm.loadGridControlData(ActionObj, MainActivity.getInstance().subFormMapControls, MainActivity.getInstance().subFormMappedValues);
                            gridForm.setCallbackListener(new com.bhargo.user.interfaces.Callback() {
                                @Override
                                public void onSuccess(Object result) {
                                    successCase(statusMsg,statusCode);
                                }

                                @Override
                                public void onFailure(Throwable throwable) {
                                    failedCase(throwable);
                                }
                            });
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_SECTION)) {
                            SectionControl sectionControl = (SectionControl) List_ControlClassObjects.get(SelectedSubForm);
                            ActionUitls.SetValuetoMultiControlInCallAPIFormUsedbyControlObject(context, 0, OutputData, list_Form_OutParams, sectionControl.controlObject.getSubFormControlList(), List_ControlClassObjects);
                            successCase(statusMsg,statusCode);
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_DATAVIEWER)) {
                            DataViewer dataViewer = (DataViewer) List_ControlClassObjects.get(SelectedSubForm);
                            dataViewer.setOutputData(OutputData);
                            dataViewer.setActionBean(ActionObj);
                            dataViewer.setResultFromGetDataManageDataAPIData();
                            successCase(statusMsg,statusCode);
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_LISTVIEW)) {
                            String cName = ActionObj.getResult_ListView_FilterMappedControl();
                            String mapControl = ActionObj.getResult_ListView_FilterItem();
                            String mapControlID = ActionObj.getResult_ListView_FilterItemID();
                            List<String> MappedValues = OutputData.get(mapControl.toLowerCase());
                            List<String> MappedValuesIDS = OutputData.get(mapControlID.toLowerCase());
                            Log.e("Result: ", "" + MappedValues.toString());
                            ActionUitls.SetMultipleValuesbyControlID(context, cName, MappedValues, MappedValuesIDS, dataCollectionObject, List_ControlClassObjects);
                            successCase(statusMsg,statusCode);

                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_IMAGEVIEW)) {
                            String cName = ActionObj.getResult_ListView_FilterMappedControl();
                            String mapControl = ActionObj.getResult_ListView_FilterItem();
                            List<String> MappedValues = OutputData.get(mapControl);
                            ActionUitls.SetMultipleValuestoImageControl(context, cName, MappedValues, dataCollectionObject, List_ControlClassObjects);
                            successCase(statusMsg,statusCode);

                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_MAPVIEW)) {
                            MapControl mapControl = (MapControl) List_ControlClassObjects.get(SelectedSubForm);
                            if (list_Form_OutParams.get(0).getOutParam_Mapped_ID() != null && list_Form_OutParams.get(0).getOutParam_Mapped_ID().length() > 0) {
                                API_OutputParam_Bean gpsBean = list_Form_OutParams.get(0);
                                List<String> MappedValues1 = OutputData.get(gpsBean.getOutParam_Mapped_ID().toLowerCase());
                                List<String> ConditionColumn = new ArrayList<>();
                                HashMap<String, List<String>> hash_popupdata = new LinkedHashMap<String, List<String>>();
                                String Operator = "";
                                MainActivity.getInstance().subFormMapControls.add(mapControl);
                                MainActivity.getInstance().subFormMappedValues.add(MappedValues1);
                                String DefultMarker = gpsBean.getOutParam_Marker_defultImage();
                                String RenderingType = gpsBean.getOutParam_Marker_RenderingType();
                                if (gpsBean.getOutParam_MarkerAdvanced_ConditionColumn() != null) {
                                    ConditionColumn = OutputData.get(gpsBean.getOutParam_MarkerAdvanced_ConditionColumn().toLowerCase());
                                    Operator = gpsBean.getOutParam_MarkerAdvanced_Operator();
                                }
                                if (gpsBean.getOutParam_Marker_popupData() != null && gpsBean.getOutParam_Marker_popupData().size() > 0) {
                                    for (int i = 0; i < gpsBean.getOutParam_Marker_popupData().size(); i++) {
                                        if (OutputData.containsKey(gpsBean.getOutParam_Marker_popupData().get(i).toLowerCase())) {
                                            hash_popupdata.put(gpsBean.getOutParam_Marker_popupData().get(i), OutputData.get(gpsBean.getOutParam_Marker_popupData().get(i).toLowerCase()));
                                        } else {
                                            hash_popupdata.put(gpsBean.getOutParam_Marker_popupData().get(i), RealmDBHelper.getColumnDataInList(context, ActionObj.getActionId(), gpsBean.getOutParam_Marker_popupData().get(i)));
                                        }
                                    }
                                }
                                boolean replace = ActionObj.getSv_Multiple_multi_assignType().equalsIgnoreCase("Replace");
                                if (gpsBean.getList_OutParam_MarkerAdvanced_Items() != null && gpsBean.getList_OutParam_MarkerAdvanced_Items().size() > 0) {
                                    List<ImageAdvanced_Mapped_Item> List_OutParam_MarkerAdvanced_Items = gpsBean.getList_OutParam_MarkerAdvanced_Items();

                                    mapControl.setMapMarkersDynamically(RenderingType, DefultMarker, MappedValues1, ConditionColumn, Operator, List_OutParam_MarkerAdvanced_Items, gpsBean.getOutParam_Marker_popupData(), hash_popupdata,replace);
                                } else {
                                    mapControl.setMapMarkersDynamically(RenderingType, DefultMarker, MappedValues1, null, null, null, gpsBean.getOutParam_Marker_popupData(), hash_popupdata,replace);
                                }

//                                        MapControl.setMapMarkersDynamically(RenderingType,DefultMarker,MappedValues1,null,null,null);

                            }

                            if (list_Form_OutParams.size() > 1 && list_Form_OutParams.get(1).getOutParam_Mapped_ID() != null) {
                                API_OutputParam_Bean transIDBean = list_Form_OutParams.get(1);
                                List<String> transIdsList = OutputData.get(transIDBean.getOutParam_Mapped_ID().toLowerCase());
                                mapControl.setTransIdsList(transIdsList);
                            }
                            successCase(statusMsg,statusCode);
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_CALENDARVIEW)) {
                            CalendarEventControl CalendarEventControl = (CalendarEventControl) List_ControlClassObjects.get(SelectedSubForm);

                            String MappedValue_Date = list_Form_OutParams.get(0).getOutParam_Mapped_ID();
                            String MappedValue_Message = list_Form_OutParams.get(1).getOutParam_Mapped_ID();
                            List<String> MappedValues_Date = OutputData.get(MappedValue_Date.toLowerCase());
                            List<String> MappedValues_Message = OutputData.get(MappedValue_Message.toLowerCase());

                            for (int x = 0; x < MappedValues_Date.size(); x++) {
                                CalendarEventControl.AddDateDynamically("Single", MappedValues_Date.get(x), MappedValues_Message.get(x));
                            }
                            successCase(statusMsg,statusCode);
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_CHART)) {
                            ChartControl chartControl = (ChartControl) List_ControlClassObjects.get(SelectedSubForm);
                            chartControl.setChartData(ActionObj, OutputData);
                            successCase(statusMsg,statusCode);
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_DATA_TABLE)) {
                            DataTableControl dataTableControl = (DataTableControl) List_ControlClassObjects.get(SelectedSubForm);
                            List<String> outColumns = new ArrayList<>();
                            outColumns.addAll(Arrays.asList(Outputcolumns.split("\\,")));
                            if (ActionObj.getMulti_DataType().equalsIgnoreCase("append")) {
                                LinkedHashMap<String, List<String>> datamap = dataTableControl.getExistingData(outColumns, OutputData);
                                dataTableControl.ClearData();
                                //nk realm :
                                //dataTableControl.setDataTableData(ActionObj, datamap, outColumns);
                                dataTableControl.setDataTableData(ActionObj, datamap);
                            } else if (ActionObj.getMulti_DataType().equalsIgnoreCase("Replace")) {
                                dataTableControl.ClearData();
                                //nk realm :
                                //dataTableControl.setDataTableData(ActionObj, datamap, outColumns);
                                dataTableControl.setDataTableData(ActionObj, OutputData);
                            }
                            successCase(statusMsg,statusCode);
                        }
                    }


                } else {
                 successCase(statusMsg,statusCode);
                }
            }
        });
    }


    private void saveRequestOffline(String sendingObj) {
        if (ActionObj.getSaveOfflineType().equalsIgnoreCase("Request")) {
            String AppName=strAppName.replace(" ","_");
            String ActionType = ActionObj.getActionType();//ManageData
            String QueryType = ActionObj.getManageDataActionType(); //"Wizard" OR "DML" OR ...
            String ActionID = ActionObj.getActionId();
            String ActionName = ActionObj.getActionName();
            String ExistingTableName = ActionObj.getExistingTableName();
            String TypeOfInput = ActionObj.getDML_Input_Type(); // "single" OR Multiple
            String SendingObj = sendingObj;
            String FilesControlNames = "";
            List<String> duplicateName = new ArrayList<>();
            if (QueryType.equals("Wizard")) {
                //ActionObj.getMapExistingType()
                TypeOfInput = ActionObj.getDML_Input_Type() + "(" + ActionObj.getMapExistingType() + ")";
                if (ll_files_insert.size() > 0) {
                    List<ServerCaseMD> temp_ = ll_files_insert.get(0);
                    for (int i = 0; i < temp_.size(); i++) {
                        ServerCaseMD serverCaseMD = temp_.get(i);
                        if (!duplicateName.contains(serverCaseMD.getColName())) {
                            FilesControlNames = FilesControlNames + serverCaseMD.getColName() + ",";
                        }
                    }
                }
                if (FilesControlNames.endsWith(",")) {
                    FilesControlNames = FilesControlNames.substring(0, FilesControlNames.length() - 1);
                }

                if (ll_files_update.size() > 0) {
                    List<ServerCaseMD> temp_ = ll_files_update.get(0);
                    for (int i = 0; i < temp_.size(); i++) {
                        ServerCaseMD serverCaseMD = temp_.get(i);
                        if (!duplicateName.contains(serverCaseMD.getColName())) {
                            FilesControlNames = FilesControlNames + serverCaseMD.getColName() + ",";
                        }
                    }
                }
                if (FilesControlNames.endsWith(",")) {
                    FilesControlNames = FilesControlNames.substring(0, FilesControlNames.length() - 1);
                }
            } else if (QueryType.equals("DML") || QueryType.equals("Group DML")) {
                TypeOfInput = ActionObj.getDML_Input_Type() == null ? "" : ActionObj.getDML_Input_Type();
                ExistingTableName = ActionObj.getSelect_FormName();
                for (int i = 0; i < inputFilesForDML.size(); i++) {
                    //String split[]=inputFilesForDML.get(i).split("|");
                    FilesControlNames = FilesControlNames + inputFilesForDML.get(i) + ",";
                }
                if (FilesControlNames.endsWith(",")) {
                    FilesControlNames = FilesControlNames.substring(0, FilesControlNames.length() - 1);
                }

            } else if (QueryType.equals("DirectSQL")) {
                TypeOfInput = ActionObj.getDML_Input_Type();
                if(TypeOfInput==null){
                    TypeOfInput="";
                }
            }

//ActionType, QueryType, ActionID, ActionName, ExistingTableName, TypeOfInput, FilesControlNames, TempCol, SendingObj
            long insertStatus = improveDataBase.insertintoTable(RealmTables.SaveRequestTable.TABLE_NAME_,
                    RealmTables.SaveRequestTable.cols,
                    new String[]{AppName,ActionType, QueryType, ActionID, ActionName, ExistingTableName, TypeOfInput, FilesControlNames, "", SendingObj});
            if (insertStatus > 0) {
                closeProgressDialog();
                callbackListener.onSuccess(context.getString(R.string.request_saved));
                //successCase(context.getString(R.string.request_saved));
            } else {
                failedCase(new Throwable("SaveRequest Failed!"));
            }
        } else {
            failedCase(new Throwable(context.getString(R.string.no_internet)));
        }
    }

    private void setManageDataStatusInGlobal(String response, ActionWithoutCondition_Bean ActionBean) {
        try {
            JSONObject jObj = new JSONObject(response);
            LinkedHashMap<String, LinkedHashMap<String, String>> tempManageData = new LinkedHashMap<>();
            if (AppConstants.GlobalObjects.getManageData_ResponseHashMap() != null) {
                tempManageData = AppConstants.GlobalObjects.getManageData_ResponseHashMap();
            }
            LinkedHashMap<String, String> temp_response = tempManageData.get(ActionBean.getActionId());
            if (temp_response == null) {
                temp_response = new LinkedHashMap<>();
            }
            //Status Code :Status
            temp_response.put("__Status Code".toLowerCase(), jObj.has("Status") ? jObj.getString("Status") : "");
            //Message :Message
            String statusMsg=jObj.has("Message") ? jObj.getString("Message") : "";
            temp_response.put("__Message".toLowerCase(), statusMsg);

            String detailsMsg="";
            if (jObj.has("Details")) {
                JSONObject detailsObj = jObj.getJSONObject("Details");
                //Detailed Message:Detailedmessage
                detailsMsg=detailsObj.has("Detailedmessage") ? detailsObj.getString("Detailedmessage") : "";
                temp_response.put("__Detailed Message".toLowerCase(),detailsMsg );
                //Records Count:Effectedrows
                temp_response.put("__Records Count".toLowerCase(), detailsObj.has("Effectedrows") ? detailsObj.getString("Effectedrows") : "");
            } else {
                //Detailed Message:Detailedmessage
                temp_response.put("__Detailed Message".toLowerCase(), "");
                //Records Count:Rowcount
                temp_response.put("__Records Count".toLowerCase(), "");
            }
            tempManageData.put(ActionBean.getActionId(), temp_response);
            AppConstants.GlobalObjects.setManageData_ResponseHashMap(tempManageData);
            System.out.println("Response:" + AppConstants.GlobalObjects.getManageData_ResponseHashMap());

            if(jObj.has("Data")){
                JSONArray jsonArrayData=jObj.getJSONArray("Data");
                if(jsonArrayData.length()==0){
                    if(jObj.getString("Status").equals("200")){
                        successCase(statusMsg,jObj.getString("Status"));
                    }else{
                        successCase(statusMsg+" "+detailsMsg,jObj.getString("Status"));
                    }
                }else{
                    if(jObj.getString("Status").equals("200")){
                        processManageDataResponse(response,statusMsg,jObj.getString("Status"));
                    }else{
                        processManageDataResponse(response,statusMsg+" "+detailsMsg,jObj.getString("Status"));
                    }
                }
            }else{
                if(jObj.getString("Status").equals("200")){
                    successCase(statusMsg,jObj.getString("Status"));
                }else{
                    successCase(statusMsg+" "+detailsMsg,jObj.getString("Status"));
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, "ManageData", "setManageDataStatusInGlobal", e);
            failedCase(e);
        }
    }

    private void execute() throws InterruptedException, JSONException {
        if (ActionObj.getManageDataActionType() != null) {
            if (ActionObj.getManageDataActionType().contentEquals("Wizard")) {
                init();
                if (MainActivity.getInstance().formValidated(MainActivity.getInstance().list_Control)) {
                    if (ActionObj.getDataBaseTableType().equalsIgnoreCase("Server")) {
                        manageDataIntoServerTable();
                    } else if (ActionObj.getDataBaseTableType().equalsIgnoreCase("Mobile")) {
                        if (ActionObj.getDML_Input_Type().equalsIgnoreCase("multiple")) {
                            manageDataIntoLocalTableMultiple();
                        } else {
                            manageDataIntoLocalTableSingle();
                        }
                    } else {
                        //both
                        if (improveDataBase.tableExists(ActionObj.getExistingTableName())) {
                            //mobile case
                            if (ActionObj.getDML_Input_Type().equalsIgnoreCase("multiple")) {
                                manageDataIntoLocalTableMultiple();
                            } else {
                                manageDataIntoLocalTableSingle();
                            }
                        } else {
                            //server Case
                            manageDataIntoServerTable();
                        }
                    }
                } else {
                    manageDataFailResult = "Form Validation Failed!";
                }
            } else if (ActionObj.getManageDataActionType().contentEquals("DML")) {
                if (condetion) {
                    final List<API_InputParam_Bean> list_input = ActionObj.getList_Form_InParams();
                    inputFilesForDML.clear();
                    Map<String, String> inputFiles = checkFilesandGetFiles(list_input);
                    if (isNetworkStatusAvialable(context) && inputFiles.size() > 0) {
                        uploadInputFilesandContinueDML(ActionObj, inputFiles, "DML");
                    } else {
                        CallDML(ActionObj, ActionObj.getActionType());
                    }

                   /* //old process
                    if (ImproveHelper.isNetworkStatusAvialable(context)) {
                        Map<String, String> data_sql = new HashMap<>();
                        if (AppConstants.DefultAPK) {
                            data_sql.put("Uid", AppConstants.GlobalObjects.getUser_ID());
                        } else {
                            data_sql.put("Uid", AppConstants.GlobalObjects.getUser_MobileNo());
                        }
//                                data_sql.put("Orgid", AppConstants.GlobalObjects.getOrg_Name());
                        data_sql.put("Qname", ActionObj.getSelect_FormName());
                        data_sql.put("Qtype", "DMLBulk");

                        Call<callsql_Data_Model> call_sql = getServices.getSampleResult(sessionManager.getAuthorizationTokenId(), data_sql);
                        call_sql.enqueue(new Callback<callsql_Data_Model>() {
                            @Override
                            public void onResponse(Call<callsql_Data_Model> call, Response<callsql_Data_Model> response) {
                                if (response.body() != null) {
                                    callsql_Data_Model callsql_Data_Model = response.body();
                                    String InParameters = callsql_Data_Model.getInParameters();
                                    String optParameters = callsql_Data_Model.getOptParameters();
                                    String OutParameters = callsql_Data_Model.getOutParameters();

                                    final List<API_InputParam_Bean> list_input = ActionObj.getList_Form_InParams();
                                    Map<String, String> inputFiles = checkFilesandGetFiles(list_input);
                                    if (inputFiles.size() > 0) {
                                        uploadInputFilesandContinueDML(ActionObj, OutParameters, inputFiles);
                                    } else {
                                        //CallDML(ActionObj, ActionObj.getActionType(), OutParameters);
                                        CallDML(ActionObj, ActionObj.getActionType());
                                    }
                                } else {
                                    failedCase(new Throwable(String.valueOf(response)));
                                }
                            }

                            @Override
                            public void onFailure(Call<callsql_Data_Model> call, Throwable t) {
                                failedCase(t);
                            }
                        });
                    } else {
                       //pending nk saveRequestOffline_();
                    }*/
                } else {
                    successCase("","200");
                }
            } else if (ActionObj.getManageDataActionType().contentEquals("Group DML")) {
                if (condetion) {

                    inputFilesForDML.clear();
                    final List<API_InputParam_Bean> list_input = ActionObj.getList_Form_InParams();
                    Map<String, String> inputFiles = getFilesForGroupDML(list_input);
                    if (isNetworkStatusAvialable(context) && inputFiles.size() > 0) {
                        uploadInputFilesandContinueDML(ActionObj, inputFiles, "GroupDML");
                        //uploadInputFilesandContinueGroupDML(ActionObj, "", inputFiles);
                    } else {
                        CallGroupDML(ActionObj, ActionObj.getActionType(), "");
                    }

                    //Old Code
                        /*Map<String, String> data_sql = new HashMap<>();
                        data_sql.put("Uid", GlobalObjects.getUser_ID());
                        data_sql.put("Orgid", AppConstants.GlobalObjects.getOrg_Name());
                        data_sql.put("Qname", ActionObj.getSelect_FormName());
                        data_sql.put("Qtype", "DMLGroup");

                        Call<callsql_Data_Model> call_sql = getServices.getSampleResult(sessionManager.getAuthorizationTokenId(), data_sql);
                        call_sql.enqueue(new Callback<callsql_Data_Model>() {
                            @Override
                            public void onResponse(Call<callsql_Data_Model> call, Response<callsql_Data_Model> response) {
                                System.out.println("response=====" + response);
                                callsql_Data_Model callsql_Data_Model = response.body();
                                String InParameters = callsql_Data_Model.getInParameters();
                                String OutParameters = callsql_Data_Model.getOutParameters();

                                final List<API_InputParam_Bean> list_input = ActionObj.getList_Form_InParams();
                                Map<String, String> inputFiles = getFilesForGroupDML(list_input);
                                if (inputFiles.size() > 0) {
                                    uploadInputFilesandContinueGroupDML(ActionObj, OutParameters, inputFiles);
                                } else {
                                    CallGroupDML(ActionObj, ActionObj.getActionType(), OutParameters);
                                }
                            }

                            @Override
                            public void onFailure(Call<callsql_Data_Model> call, Throwable t) {
                                System.out.println("Failure Responce");
                                failedCase(t);
                            }
                        });*/

                } else {
                    successCase("","200");
                }
            } else if (ActionObj.getManageDataActionType().contentEquals("DirectSQL")) {
                getDataDirectQuery(ActionObj);
            }
        } else {
            failedCase(new Throwable("ManageDataActionType Is Null"));
        }
    }

    public void getDataDirectQuery(ActionWithoutCondition_Bean ActionBean) {
        try {
            ExpressionMainHelper ehelper = new ExpressionMainHelper();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("OrgId", sessionManager.getOrgIdFromSession());
            //jsonObject.put("UserID", "Select");
//            jsonObject.put("QueryText", ActionBean.getDirectQueryString());
            if (ActionBean.getDirectQueryString() != null) {
                jsonObject.put("QueryText", ehelper.ExpressionHelper(context, ActionBean.getDirectQueryString()));
            } else {
                jsonObject.put("QueryText", ehelper.ExpressionHelper(context, ActionBean.getSqlNamesList().get(0)));
                //jsonObject.put("QueryText","delete from  school_registration where  school_id ='one,two'");
            }
            if (ActionBean.getActionType().contentEquals(AppConstants.MANAGE_DATA)) {
                jsonObject.put("QueryType", "DMLBulk");
            } else {
                jsonObject.put("QueryType", "Select");
            }

            jsonObject.put("DBConnectionSource", ActionBean.getConnectionSource());
            jsonObject.put("DBConnection", ActionBean.getConnectionId());
            if (isNetworkStatusAvialable(context)) {
                JsonObject jo = (JsonObject) JsonParser.parseString(jsonObject.toString());
                Call<String> call = getServices.GetDataDirectQuery(sessionManager.getAuthorizationTokenId(), jo);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.e("Result: ", "" + response.body());
                        if (response.body() != null) {
                            setManageDataStatusInGlobal(response.body(), ActionObj);//DirectQuery
                            //successCase(response.body());
                        } else {
                            failedCase(new Throwable(response.body()));
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
                        failedCase(t);
                    }
                });
            } else {
                saveRequestOffline(jsonObject.toString());//DirectSQL
            }


        } catch (Exception e) {
            failedCase(e);
        }
    }

    public void uploadInputFilesandContinueGroupDML(ActionWithoutCondition_Bean ActionObj, String OutParameters, Map<String, String> inputFiles) {
        inputFilesMap = inputFiles;
        if (inputFiles != null && inputFiles.size() > 0) {
            int filesCount = inputFiles.size();
            Set<String> inkeys = inputFiles.keySet();
            InkeyNames = inkeys.toArray(new String[inkeys.size()]);
            fileIndex = 0;
            if (!InkeyNames[fileIndex].contains("$")) {
                sendGroupDMLInParamFilesToServer(ActionObj, OutParameters, InkeyNames[fileIndex], inputFiles.get(InkeyNames[fileIndex]), fileIndex, filesCount);
            } else {
                sfFileIndex = 0;
                String entry = inputFilesMap.get(InkeyNames[fileIndex]);
                entries = entry.split("\\^");
                sendGroupDMLSFInParamFilesToServer(ActionObj, OutParameters, InkeyNames[fileIndex], entries[sfFileIndex], sfFileIndex, entries.length);

            }
        }
    }

    public void sendGroupDMLInParamFilesToServer(ActionWithoutCondition_Bean actionObj, String outParameters, String inputkey, String path, final int fileCount, final int filesCount) {
        try {
            String pageName = MainActivity.getInstance().strAppName.replace(" ", "_");
            String filePath = path;
            final String fileName = path.substring(filePath.lastIndexOf("/") + 1);
            final String finalControlName = inputkey;
            if (filePath.startsWith("http") || filePath.equals("")) {
                if (fileCount + 1 == inputFilesMap.size()) {
                    List<API_InputParam_Bean> list_input = actionObj.getList_Form_InParams();
                    for (int i = 0; i < list_input.size(); i++) {
                        if (inputFilesMap.containsKey(list_input.get(i).getInParam_Name())) {
                            list_input.get(i).setInParam_ExpressionValue(inputFilesMap.get(list_input.get(i).getInParam_Name()));
                            list_input.get(i).setInParam_ExpressionExists(true);
                            list_input.get(i).setInParam_Mapped_ID("");
                        }
                    }
                    CallGroupDML(actionObj, actionObj.getActionType(), outParameters);
                } else {
                    fileIndex++;
                    if (!InkeyNames[fileIndex].contains("$")) {
                        sendGroupDMLInParamFilesToServer(actionObj, outParameters, InkeyNames[fileIndex], inputFilesMap.get(InkeyNames[fileIndex]), fileIndex, filesCount);
                    } else {
                        sfFileIndex = 0;
                        String entry = inputFilesMap.get(InkeyNames[fileIndex]);
                        entries = entry.split("\\^");
                        sendGroupDMLSFInParamFilesToServer(actionObj, outParameters, InkeyNames[fileIndex], entries[sfFileIndex], sfFileIndex, entries.length);
                    }
                }
            } else {
                if(isNetworkStatusAvialable(context)){
                    new FileUploader(context, fileName, MainActivity.getInstance().strUserId, pageName, false, "BHARGO", new FileUploader.OnImageUploaded() {
                        @Override
                        public void response(String url) {
                            if (url != null && url.startsWith("http")) {
                                inputFilesMap.put(inputkey, url);
                                if (fileCount + 1 == inputFilesMap.size()) {
                                    List<API_InputParam_Bean> list_input = actionObj.getList_Form_InParams();
                                    for (int i = 0; i < list_input.size(); i++) {
                                        if (inputFilesMap.containsKey(list_input.get(i).getInParam_Name())) {
                                            list_input.get(i).setInParam_ExpressionValue(inputFilesMap.get(list_input.get(i).getInParam_Name()));
                                            list_input.get(i).setInParam_ExpressionExists(true);
                                            list_input.get(i).setInParam_Mapped_ID("");
                                        }
                                    }
                                    CallGroupDML(actionObj, actionObj.getActionType(), outParameters);
                                } else {
                                    fileIndex++;
                                    if (!InkeyNames[fileIndex].contains("$")) {
                                        sendGroupDMLInParamFilesToServer(actionObj, outParameters, InkeyNames[fileIndex], inputFilesMap.get(InkeyNames[fileIndex]), fileIndex, filesCount);
                                    } else {
                                        sfFileIndex = 0;
                                        String entry = inputFilesMap.get(InkeyNames[fileIndex]);
                                        entries = entry.split("\\^");
                                        sendGroupDMLSFInParamFilesToServer(actionObj, outParameters, InkeyNames[fileIndex], entries[sfFileIndex], sfFileIndex, entries.length);
                                    }
                                }
                            } else {
                                if (url == null) {
                                    failedCase(new Throwable("File upload Failed"));
                                } else {
                                    failedCase(new Throwable("File upload Failed\n" + url));
                                }
                            }
                        }
                    }).execute(filePath);
                }else{
                    //no internet : prepare string for saveRequest
                    if (ActionObj.getSaveOfflineType().equalsIgnoreCase("Request")) {
                        CallGroupDML(actionObj, actionObj.getActionType(), outParameters);
                    }else{
                        failedCase(new Throwable(context.getString(R.string.no_internet)));
                    }
                }

            }

        } catch (Exception e) {
            failedCase(e);
        }
    }

    public void sendGroupDMLSFInParamFilesToServer(ActionWithoutCondition_Bean actionObj, String outParameters, String inputkey, String path, final int fileCount, final int filesCount) {
        try {
            String pageName = MainActivity.getInstance().strAppName.replace(" ", "_");
            String filePath = path;
            final String fileName = path.substring(filePath.lastIndexOf("/") + 1);
            final String finalControlName = inputkey;
            if(isNetworkStatusAvialable(context)){
                new FileUploader(context, fileName, MainActivity.getInstance().strUserId, pageName, false, "BHARGO", new FileUploader.OnImageUploaded() {
                    @Override
                    public void response(String url) {
                        if (url != null && url.startsWith("http")) {
                            if (sfFileIndex == 0) {
                                inputFilesMap.put(inputkey, url);
                            } else {
                                String pUrl = inputFilesMap.get(inputkey);
                                inputFilesMap.put(inputkey, pUrl + "," + url);
                            }
                            if (sfFileIndex + 1 == filesCount) {
                                if (fileIndex + 1 == inputFilesMap.size()) {
                                    CallGroupDML(actionObj, actionObj.getActionType(), outParameters);
                                } else {
                                    fileIndex++;
                                    if (!InkeyNames[fileIndex].contains("$")) {
                                        sendGroupDMLInParamFilesToServer(actionObj, outParameters, InkeyNames[fileIndex], inputFilesMap.get(InkeyNames[fileIndex]), fileIndex, filesCount);
                                    } else {
                                        sfFileIndex = 0;
                                        String entry = inputFilesMap.get(InkeyNames[fileIndex]);
                                        entries = entry.split("\\^");
                                        sendGroupDMLSFInParamFilesToServer(actionObj, outParameters, InkeyNames[fileIndex], entries[sfFileIndex], sfFileIndex, entries.length);
                                    }
                                }
                            } else {
                                sfFileIndex++;
                                sendGroupDMLSFInParamFilesToServer(actionObj, outParameters, InkeyNames[fileIndex], entries[sfFileIndex], sfFileIndex, entries.length);
                            }
                        } else {
                            if (url == null) {
                                failedCase(new Throwable("File upload Failed"));
                            } else {
                                failedCase(new Throwable("File upload Failed\n" + url));
                            }
                        }
                    }
                }).execute(filePath);
            }else{
                //no internet : prepare string for saveRequest
                if (ActionObj.getSaveOfflineType().equalsIgnoreCase("Request")) {
                    CallGroupDML(actionObj, actionObj.getActionType(), outParameters);
                }else{
                    failedCase(new Throwable(context.getString(R.string.no_internet)));
                }
            }

        } catch (Exception e) {
            failedCase(e);
        }
    }

    public LinkedHashMap<String, List<String>> getDataSourceValuebyStatementWise(List<API_InputParam_Bean> list_input, String StatementName) {
        LinkedHashMap<String, List<String>> map_Data = new LinkedHashMap<String, List<String>>();
        String DataSource = "";

        API_InputParam_Bean Temp_inparams = null;
        for (int i = 0; i < list_input.size(); i++) {
            if (list_input.get(i).getInParam_GroupDML_StatementName().equalsIgnoreCase(StatementName)) {
                Temp_inparams = list_input.get(i);
                break;
            }
        }


        if (Temp_inparams.isInParam_GroupDML_DataSource_ExpressionExists()) {
            DataSource = Temp_inparams.getInParam_GroupDML_DataSource_ExpressionValue();
            ExpressionMainHelper ehelper = new ExpressionMainHelper();
            String finalValue = ehelper.ExpressionHelper(context, DataSource);
            map_Data = ActionUitls.ConvertGetvaluestoHashMap(context, finalValue, 1, DataSource);

        } else {
            DataSource = Temp_inparams.getInParam_GroupDML_DataSource_Value();//(im:PashuPaalak_Funds Description.SubControls.desprived_funds)
            String ValueType = "", sourceName = "", formName = "";
            if (IS_MULTI_FORM && !Global_Single_Forms.isEmpty()) {
                formName = DataSource.split(":")[1].split("\\.")[0];
                ValueType = DataSource.split(":")[1].split("\\.")[1];
                sourceName = DataSource.split(":")[1].split("\\.")[2];
                sourceName = sourceName.substring(0, sourceName.length() - 1).toLowerCase();

                if (ValueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
                    List<ControlObject> controls = Global_Single_Forms.get(formName.toLowerCase()).getControls_list();
                    List<ControlObject> list_Controls = new ArrayList<>();
                    for (int i = 0; i < controls.size(); i++) {
                        if (controls.get(i).getControlName().equalsIgnoreCase(sourceName)) {
                            list_Controls.addAll(controls.get(i).getSubFormControlList());
                        }
                    }

                    for (int i = 0; i < list_Controls.size(); i++) {
                        ControlObject temp_controlObj = list_Controls.get(i);
                        if ((temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CAMERA) ||
                                temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_FILE_BROWSING) ||
                                temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_VOICE_RECORDING) ||
                                (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_VIDEO_RECORDING)))) {
                            String sfCOntrol = temp_controlObj.getControlName();
                            String value = "";

                            if (!inputFilesMap.isEmpty()) {
                                Set<String> inkeys = inputFilesMap.keySet();
                                String[] InkeyNames = inkeys.toArray(new String[inkeys.size()]);
                                for (int j = 0; j < InkeyNames.length; j++) {
                                    if (InkeyNames[j].contains("$") && InkeyNames[j].endsWith("$" + sourceName + "$" + sfCOntrol)) {
                                        value = inputFilesMap.get(InkeyNames[j]);
                                        break;
                                    }

                                }
                                if (!value.contentEquals("")) {
                                    map_Data.put(temp_controlObj.getControlName(), Arrays.asList(value.split(",")));
                                }
                            }
                        } else {
                            if (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_RADIO_BUTTON)
                                    || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DROP_DOWN)
                                    || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECKBOX)
                                    || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECK_LIST)
                                    || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DATA_CONTROL)) {
                                List<String> Valuestr = ImproveHelper.getListOfValuesFromGlobalObject(context, DataSource.substring(0, DataSource.indexOf(")")) + "." + temp_controlObj.getControlName() + "_id_allrows)");
                                map_Data.put(temp_controlObj.getControlName() + "_id", Valuestr);
                            }
                            List<String> Valuestr = ImproveHelper.getListOfValuesFromGlobalObject(context, DataSource.substring(0, DataSource.indexOf(")")) + "." + temp_controlObj.getControlName() + "_allrows)");
                            map_Data.put(temp_controlObj.getControlName(), Valuestr);
                        }
                    }
                }
            } else {
                ValueType = DataSource.substring(4, DataSource.indexOf("."));
                sourceName = DataSource.substring(4, DataSource.indexOf(")"));
                sourceName = sourceName.split("\\.")[1].toLowerCase();

                if (ValueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
                    List<ControlObject> list_Controls = new ArrayList<>();
                    if (((MainActivity) context).List_ControlClassObjects.get(sourceName) instanceof SubformView) {
                        SubformView subview = (SubformView) ((MainActivity) context).List_ControlClassObjects.get(sourceName);
                        list_Controls = subview.controlObject.getSubFormControlList();
                    } else if (((MainActivity) context).List_ControlClassObjects.get(sourceName) instanceof GridControl) {
                        GridControl gridControl = (GridControl) ((MainActivity) context).List_ControlClassObjects.get(sourceName);
                        list_Controls = gridControl.controlObject.getSubFormControlList();
                    }


                    for (int i = 0; i < list_Controls.size(); i++) {
                        ControlObject temp_controlObj = list_Controls.get(i);

                        if (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_RADIO_BUTTON)
                                || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DROP_DOWN)
                                || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECKBOX)
                                || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECK_LIST)
                                || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DATA_CONTROL)) {
                            List<String> Valuestr = ImproveHelper.getListOfValuesFromGlobalObject(context, DataSource.substring(0, DataSource.indexOf(")")) + "." + temp_controlObj.getControlName() + "_id_allrows)");
                            map_Data.put(temp_controlObj.getControlName() + "_id", Valuestr);
                        }
                        List<String> Valuestr = ImproveHelper.getListOfValuesFromGlobalObject(context, DataSource.substring(0, DataSource.indexOf(")")) + "." + temp_controlObj.getControlName() + "_allrows)");
                        map_Data.put(temp_controlObj.getControlName(), Valuestr);
                    }
                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_API)) {

                    //map_Data = AppConstants.GlobalObjects.getAPIs_ListHash().get(sourceName);
                    //nk realm
                    map_Data = RealmDBHelper.getTableDataInLHM(context, sourceName);
                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_FormFields)) {
                    // map_Data = AppConstants.GlobalObjects.getForms_ListHash().get(sourceName);
                    //nk realm
                    map_Data = RealmDBHelper.getTableDataInLHM(context, sourceName);

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_Query)) {
                    //map_Data = AppConstants.GlobalObjects.getForms_ListHash().get(sourceName);
                    //nk realm
                    map_Data = RealmDBHelper.getTableDataInLHM(context, sourceName);
                }
            }

            /*DataSource = Temp_inparams.getInParam_GroupDML_DataSource_Value();

            String ValueType = DataSource.substring(4, DataSource.indexOf("."));
            String sourceName = DataSource.substring(4, DataSource.indexOf(")"));
            sourceName = sourceName.split("\\.")[1].toLowerCase();

            if (ValueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
                SubformView subview = (SubformView) ((MainActivity) context).List_ControlClassObjects.get(sourceName);
                List<ControlObject> list_Controls = subview.controlObject.getSubFormControlList();
                for (int i = 0; i < list_Controls.size(); i++) {
                    ControlObject temp_controlObj = list_Controls.get(i);
                    if (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_RADIO_BUTTON)
                            || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DROP_DOWN)
                            || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECKBOX)
                            || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECK_LIST)
                            || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DATA_CONTROL)) {
                        List<String> Valuestr = ImproveHelper.getListOfValuesFromGlobalObject(this, DataSource.substring(0, DataSource.indexOf(")")) + "." + temp_controlObj.getControlName() + "_id_allrows)");
                        map_Data.put(temp_controlObj.getControlName() + "_id", Valuestr);
                    }
                    List<String> Valuestr = ImproveHelper.getListOfValuesFromGlobalObject(this, DataSource.substring(0, DataSource.indexOf(")")) + "." + temp_controlObj.getControlName() + "_allrows)");
                    map_Data.put(temp_controlObj.getControlName(), Valuestr);
                }
            } else if (ValueType.equalsIgnoreCase(AppConstants.Global_API)) {

                //map_Data = AppConstants.GlobalObjects.getAPIs_ListHash().get(sourceName);
                //nk realm
                map_Data = RealmDBHelper.getTableDataInLHM(context, sourceName);
            } else if (ValueType.equalsIgnoreCase(AppConstants.Global_FormFields)) {
                // map_Data = AppConstants.GlobalObjects.getForms_ListHash().get(sourceName);
                //nk realm
                map_Data = RealmDBHelper.getTableDataInLHM(context, sourceName);

            } else if (ValueType.equalsIgnoreCase(AppConstants.Global_Query)) {
                //map_Data = AppConstants.GlobalObjects.getForms_ListHash().get(sourceName);
                //nk realm
                map_Data = RealmDBHelper.getTableDataInLHM(context, sourceName);
            }*/
        }

        return map_Data;
    }

    public void CallGroupDML(final ActionWithoutCondition_Bean ActionBean, String SQLorDML, String Outparameters) {
        try {
            String Select_FormName = ActionBean.getSelect_FormName();
            List<API_InputParam_Bean> list_input = ActionBean.getList_Form_InParams();
            List<API_OutputParam_Bean> list_output = ActionBean.getList_Form_OutParams();
            String Type = ActionBean.getDML_Input_Type();
            List<String> list_statements = ImproveHelper.getStatementsfromInParams(list_input);
            JSONArray Jarr_Allstatements = new JSONArray();

            for (int k = 0; k < list_statements.size(); k++) {
                JSONObject StatementWise_jsonobj = new JSONObject();
                StatementWise_jsonobj.put("StatementName", list_statements.get(k).split("\\|")[0]);


                if (list_statements.get(k).split("\\|")[1].equalsIgnoreCase("Single")) {
                    JSONArray jarr = new JSONArray();
                    JSONObject Row_jsonobj = new JSONObject();
                    Row_jsonobj.put("RowId", "1");
                    JSONArray single_jarr = new JSONArray();

                    for (int i = 0; i < list_input.size(); i++) {
                        if (list_input.get(i).getInParam_GroupDML_StatementName().equalsIgnoreCase(list_statements.get(k).split("\\|")[0])) {

                            JSONObject InputMap = new JSONObject();
                            String inValue = "";

                            if (list_input.get(i).isInParam_ExpressionExists()) {
                                ExpressionMainHelper ehelper = new ExpressionMainHelper();
                                if (list_input.get(i).getInParam_ExpressionValue().startsWith("http:")) {
                                    inValue = list_input.get(i).getInParam_ExpressionValue();
                                } else {
                                    inValue = ehelper.ExpressionHelper(context, list_input.get(i).getInParam_ExpressionValue());
                                }
                                if (inValue.startsWith("(")) {
                                    inValue = "";
                                }
                            } else {
                                if (list_input.get(i).getInParam_Mapped_ID().startsWith("http:")) {
                                    inValue = list_input.get(i).getInParam_ExpressionValue();
                                } else {
                                    inValue = ImproveHelper.getValueFromGlobalObject(context, list_input.get(i).getInParam_Mapped_ID());
                                }
                            }
                            InputMap.put("Key", list_input.get(i).getInParam_Name());
                            InputMap.put("Value", inValue);
                            single_jarr.put(InputMap);

                        }
                    }
                    Row_jsonobj.put("RowData", single_jarr);
                    jarr.put(Row_jsonobj);
                    StatementWise_jsonobj.put("InParameters", jarr);
                } else {
                    JSONArray jarr = new JSONArray();

                    LinkedHashMap<String, List<String>> map_Data = new LinkedHashMap<String, List<String>>();
                    map_Data = getDataSourceValuebyStatementWise(list_input, list_statements.get(k).split("\\|")[0]);

                    Set<String> resultSet = map_Data.keySet();
                    String[] resultNames = resultSet.toArray(new String[resultSet.size()]);

                    List<String> resultcolum = map_Data.get(resultNames[0]);

                    for (int x = 0; x < resultcolum.size(); x++) {
                        if (resultcolum.get(x).trim().length() > 0) {

                            JSONObject Row_jsonobj = new JSONObject();
                            Row_jsonobj.put("RowId", (x + 1));
                            JSONArray Data_jsonarray = new JSONArray();

                            for (int i = 0; i < list_input.size(); i++) {
                                if (list_input.get(i).getInParam_GroupDML_StatementName().equalsIgnoreCase(list_statements.get(k).split("\\|")[0])) {
                                    String inValue = "";//Global_SubControls
                                    String columName = "";
                                    if (list_input.get(i).getInParam_ExpressionValue().trim().toLowerCase().startsWith("(im:")) {
                                        columName = ImproveHelper.spilitandgetcolumnname(list_input.get(i).getInParam_ExpressionValue().trim().toLowerCase());
                                    } else {
                                        columName = list_input.get(i).getInParam_ExpressionValue().trim();
                                    }
                                    if (map_Data.containsKey(columName)) {
                                        inValue = map_Data.get(columName).get(x);
                                    } else {
                                        ExpressionMainHelper ehelper = new ExpressionMainHelper();
                                        inValue = ehelper.ExpressionHelper(context, list_input.get(i).getInParam_ExpressionValue());
                                        //inValue = ImproveHelper.getValueFromGlobalObject(context, list_input.get(i).getInParam_ExpressionValue().trim());

                                    }

                                    /*if (list_input.get(i).isInParam_ExpressionExists()) {
                                        ExpressionMainHelper ehelper = new ExpressionMainHelper();
                                        inValue = ehelper.ExpressionHelper(context, list_input.get(i).getInParam_ExpressionValue());
                                    } else {
//                                        String columName = "";
                                        if (list_input.get(i).getInParam_Mapped_ID().trim().toLowerCase().startsWith("(im:")) {
                                            columName = ImproveHelper.spilitandgetcolumnname(list_input.get(i).getInParam_Mapped_ID().trim().toLowerCase());
                                        } else {
                                            columName = list_input.get(i).getInParam_Mapped_ID().trim();
                                        }
                                        if (map_Data.containsKey(columName)) {
                                            inValue = map_Data.get(columName).get(x);
                                        } else {
                                            inValue = ImproveHelper.getValueFromGlobalObject(context, list_input.get(i).getInParam_Mapped_ID().trim());
                                        }
                                    }*/
                                    JSONObject Data_obj = new JSONObject();
                                    Data_obj.put("Key", list_input.get(i).getInParam_Name());
                                    Data_obj.put("Value", inValue);
                                    Data_jsonarray.put(Data_obj);
                                }
                            }
                            Row_jsonobj.put("RowData", Data_jsonarray);

                            jarr.put(Row_jsonobj);
                        }
                    }


                    StatementWise_jsonobj.put("InParameters", jarr);

                }

                Jarr_Allstatements.put(StatementWise_jsonobj);
            }

            if (isNetworkStatusAvialable(context)) {
                Map<String, String> dataJson = new HashMap<>();
                dataJson.put("OrgId", sessionManager.getOrgIdFromSession());
                dataJson.put("Uid", MainActivity.getInstance().strUserId);
                dataJson.put("Qname", Select_FormName);
                dataJson.put("Qtype", "DMLGroup");
                dataJson.put("InParameters", Jarr_Allstatements.toString());
                System.out.println("dataJson==" + dataJson);
                //dismissProgressDialog();
                ImproveHelper.improveLog(TAG, "GetSQLorDMLPOST", new Gson().toJson(dataJson));

                Call<String> call = getServices.GetSQLorDMLPOST(sessionManager.getAuthorizationTokenId(), dataJson);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.e("Result: ", "" + response.body());
                        if (response.body() != null) {
                            setManageDataStatusInGlobal(response.body(), ActionBean);//GroupDML
                            //processManageDataResponse(response.body());
                            //successCase(response.body());
                        } else {
                            failedCase(new Throwable(response.body()));
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
                        failedCase(t);
                    }
                });
            } else {
                saveRequestOffline(Jarr_Allstatements.toString());//GroupDML
            }


        } catch (Exception e) {
            failedCase(e);
        }
    }

    public Map<String, String> getFilesForGroupDML(List<API_InputParam_Bean> listInparams) {
        List<String> temp_Names = new ArrayList<>();
        DataCollectionObject dataCollectionObject = MainActivity.getInstance().dataCollectionObject;
        Map<String, String> inparamFiles = new HashMap<>();
        try {
            if (IS_MULTI_FORM && !Global_Single_Forms.isEmpty()) {
                Set<String> keySet = Global_Single_Forms.keySet();

                if (listInparams != null) {
                    for (int i = 0; i < listInparams.size(); i++) {
                        if (listInparams.get(i).getInParam_Mapped_ID().trim().startsWith("(im:") || listInparams.get(i).getInParam_ExpressionValue().trim().startsWith("(im:")) {
                            String ValueStr = listInparams.get(i).getInParam_Mapped_ID().trim().toLowerCase();
                            if (ValueStr.contentEquals("")) {
                                ValueStr = listInparams.get(i).getInParam_ExpressionValue().trim().toLowerCase(Locale.ROOT);
                            }
                            if (!ValueStr.startsWith("(im:systemvariables")) {
                                String formName = ValueStr.substring(4, ValueStr.indexOf("."));//(im:form 2.controlname.gender)
                                String tempStr = ValueStr.substring(4 + formName.length() + 1);
                                String ValueType = tempStr.substring(0, tempStr.indexOf("."));
                                ValueStr = ValueStr.substring(4 + formName.length() + 1, ValueStr.lastIndexOf(")"));
                                if (ValueType.equalsIgnoreCase(AppConstants.Global_ControlsOnForm)) {
                                    String ConditionValue = ValueStr.split("\\.")[1];

                                    DataCollectionObject object = Global_Single_Forms.get(formName);
                                    if (object == null && dataCollectionObject != null) {
                                        object = dataCollectionObject;
                                    }
                                    List<ControlObject> list_Controls = object.getControls_list();
                                    for (int x = 0; x < list_Controls.size(); x++) {
                                        ControlObject temp_controlObj = list_Controls.get(x);

                                        if (temp_controlObj.getControlName().equalsIgnoreCase(ConditionValue) &&
                                                (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CAMERA) ||
                                                        temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_FILE_BROWSING) ||
                                                        temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_VOICE_RECORDING) ||
                                                        (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_VIDEO_RECORDING)))) {
                                            String expValue = listInparams.get(i).getInParam_Mapped_ID().trim().toLowerCase();
                                            if (expValue.contentEquals("")) {
                                                expValue = listInparams.get(i).getInParam_ExpressionValue().trim().toLowerCase();
                                            }

                                            String inValue = ImproveHelper.getValueFromGlobalObject(context, expValue);
                                            if (!inValue.contentEquals("")) {
                                                //inparamFiles.put(ConditionValue, inValue);
                                                inparamFiles.put(listInparams.get(i).getInParam_Name(), inValue);
                                                if (!temp_Names.contains(listInparams.get(i).getInParam_Name())) {
                                                    temp_Names.add(listInparams.get(i).getInParam_Name());
                                                    //inputFilesForDML.add(listInparams.get(i).getInParam_Name() + "|" + i + "|M");
                                                    inputFilesForDML.add(listInparams.get(i).getInParam_Name() );
                                                }
                                            }
                                            break;
                                        }
                                    }

                                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
                                    String ConditionValue = ValueStr.split("\\.")[1];
                                    if (ConditionValue.endsWith("_processrow") || ConditionValue.endsWith("_allrows")) {
                                        ConditionValue = ConditionValue.substring(0, ConditionValue.lastIndexOf("_"));
                                    }
                                    DataCollectionObject object = Global_Single_Forms.get(formName);
                                    if (object == null && dataCollectionObject != null) {
                                        object = dataCollectionObject;
                                    }
                                    List<ControlObject> list_Controls = object.getControls_list();
                                    for (int j = 0; j < list_Controls.size(); j++) {
                                        if (list_Controls.get(j).getControlType().contentEquals(CONTROL_TYPE_SUBFORM) ||
                                                list_Controls.get(j).getControlType().contentEquals(CONTROL_TYPE_GRID_CONTROL)) {
                                            List<ControlObject> sub_list_Controls = list_Controls.get(j).getSubFormControlList();
                                            for (int k = 0; k < sub_list_Controls.size(); k++) {
                                                ControlObject temp_controlObj = sub_list_Controls.get(k);
                                                if (temp_controlObj.getControlName().equalsIgnoreCase(ConditionValue) &&
                                                        (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CAMERA) ||
                                                                temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_FILE_BROWSING) ||
                                                                temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_VOICE_RECORDING) ||
                                                                (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_VIDEO_RECORDING)))) {

                                                    String expValue = listInparams.get(i).getInParam_Mapped_ID().trim().toLowerCase();
                                                    if (expValue.contentEquals("")) {
                                                        expValue = listInparams.get(i).getInParam_ExpressionValue().trim().toLowerCase();
                                                    }
                                                    if (expValue.endsWith("_processrow)")) {
                                                        String sName = listInparams.get(i).getInParam_GroupDML_DataSource_Value().toLowerCase().split("\\.")[2];
                                                        sName = sName.substring(0, sName.indexOf(")"));
                                                        expValue = expValue.substring(0, expValue.lastIndexOf("_"));
                                                        expValue = expValue + "_allrows)";
                                                        String[] array = expValue.split("\\.");
                                                        expValue = array[0] + "." + array[1] + "." + sName + "." + array[2];
                                                    }

                                                    List<String> inValueList = ImproveHelper.getListOfValuesFromGlobalObject(context, expValue);
                                                    StringBuilder inValue = new StringBuilder();
                                                    if (inValueList != null) {
                                                        for (int l = 0; l < inValueList.size(); l++) {
                                                            if (l == 0) {
                                                                inValue.append(inValueList.get(l));
                                                            } else {
                                                                inValue.append("^").append(inValueList.get(l));
                                                            }

                                                        }
                                                    }

                                                    if (!inValue.toString().contentEquals("")) {
                                                        //inparamFiles.put(ConditionValue, inValue);
                                                        inparamFiles.put(listInparams.get(i).getInParam_Name() + "$" + list_Controls.get(j).getControlName() + "$" + temp_controlObj.getControlName(), inValue.toString());
                                                        if (!temp_Names.contains(listInparams.get(i).getInParam_Name())) {
                                                            temp_Names.add(listInparams.get(i).getInParam_Name());
                                                            //inputFilesForDML.add(listInparams.get(i).getInParam_Name() + "|" + i + "|S");
                                                            inputFilesForDML.add(listInparams.get(i).getInParam_Name());
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
            } else {
                inparamFiles = checkFilesandGetFiles(listInparams);
            }

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "checkFilesandGetFiles", e);
        }
        return inparamFiles;
    }

    public void uploadInputFilesandContinueDML(ActionWithoutCondition_Bean ActionObj, Map<String, String> inputFiles, String actionType) {
        try {
            FilesTM1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    MainActivity.getInstance().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (inputFiles != null && inputFiles.size() > 0) {
                                int filesCount = inputFiles.size();
                                Set<String> inkeys = inputFiles.keySet();
                                String[] InkeyNames = inkeys.toArray(new String[inkeys.size()]);
                                fileCountHit = 0;
                                sendInParamFilesToServer(fileCountHit, filesCount, inputFiles);

                            } else {
                                FilesTM2.start();
                                try {
                                    FilesTM2.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            });

            FilesTM1.start();
            FilesTM1.join();

            FilesTM2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    MainActivity.getInstance().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // CallDML(ActionObj, ActionObj.getActionType(), OutParameters);
                            if (actionType.equals("DML")) {
                                CallDML(ActionObj, ActionObj.getActionType());
                            } else {
                                CallGroupDML(ActionObj, ActionObj.getActionType(), "");
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            failedCase(e);
        }
    }

    public void CallDML(final ActionWithoutCondition_Bean ActionBean, String SQLorDML) {
        try {
            boolean executeflag = false;
            String Select_FormName = ActionBean.getSelect_FormName();
            List<API_InputParam_Bean> list_input = ActionBean.getList_Form_InParams();
            String Type = ActionBean.getDML_Input_Type();
            JSONArray jarr = new JSONArray();
            if (Type.equalsIgnoreCase("Single")) {
                executeflag = true;
                JSONObject Row_jsonobj = new JSONObject();
                Row_jsonobj.put("RowId", "1");
                JSONArray single_jarr = new JSONArray();
                for (int i = 0; i < list_input.size(); i++) {
                    if (list_input.get(i).isEnable()) {
                        JSONObject InputMap = new JSONObject();
                        String inValue = "";
                        if (list_input.get(i).isInParam_ExpressionExists()) {
                            ExpressionMainHelper ehelper = new ExpressionMainHelper();
                            inValue = ehelper.ExpressionHelper(context, list_input.get(i).getInParam_ExpressionValue());
                        } else {
                            inValue = ImproveHelper.getValueFromGlobalObject(context, list_input.get(i).getInParam_Mapped_ID());
                        }


                        InputMap.put("Key", list_input.get(i).getInParam_Name());
                        InputMap.put("Value", inValue);

                        single_jarr.put(InputMap);

                    }
                }
                Row_jsonobj.put("RowData", single_jarr);
                jarr.put(Row_jsonobj);
            } else {
                String DataSource = "";
                LinkedHashMap<String, List<String>> map_Data = new LinkedHashMap<String, List<String>>();

                if (ActionBean.isDML_DataSource_ExpressionExists()) {
                    DataSource = ActionBean.getDML_DataSource_ExpressionValue();
                    ExpressionMainHelper ehelper = new ExpressionMainHelper();
                    String finalValue = ehelper.ExpressionHelper(context, DataSource);
                    map_Data = ActionUitls.ConvertGetvaluestoHashMap(context, finalValue, 1, DataSource);

                } else {
                    DataSource = ActionBean.getDML_DataSource_Value();

                    String ValueType = DataSource.substring(4, DataSource.indexOf("."));
                    String sourceName = DataSource.substring(4, DataSource.indexOf(")"));
                    sourceName = sourceName.split("\\.")[1].toLowerCase();

                    if (ValueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
                        List<ControlObject> list_Controls = new ArrayList<>();
                        if (MainActivity.getInstance().List_ControlClassObjects.get(sourceName) instanceof GridControl) {
                            GridControl gridControl = (GridControl) MainActivity.getInstance().List_ControlClassObjects.get(sourceName);
                            //totForms = gridControl.ll_MainSubFormContainer.getChildCount();
                            list_Controls = gridControl.controlObject.getSubFormControlList();
                        } else {
                            SubformView subview = (SubformView) MainActivity.getInstance().List_ControlClassObjects.get(sourceName);
                            //totForms = subview.ll_MainSubFormContainer.getChildCount();
                            list_Controls = subview.controlObject.getSubFormControlList();
                        }
                        for (int i = 0; i < list_Controls.size(); i++) {
                            ControlObject temp_controlObj = list_Controls.get(i);
                            if (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_RADIO_BUTTON)
                                    || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DROP_DOWN)
                                    || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECKBOX)
                                    || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CHECK_LIST)
                                    || temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_DATA_CONTROL)) {
                                List<String> Valuestr = ImproveHelper.getListOfValuesFromGlobalObject(context, DataSource.substring(0, DataSource.indexOf(")")) + "." + temp_controlObj.getControlName() + "_id_allrows)");
                                map_Data.put(temp_controlObj.getControlName() + "_id", Valuestr);
                            }
                            List<String> Valuestr = ImproveHelper.getListOfValuesFromGlobalObject(context, DataSource.substring(0, DataSource.indexOf(")")) + "." + temp_controlObj.getControlName() + "_allrows)");
                            map_Data.put(temp_controlObj.getControlName(), Valuestr);
                        }
                    } else if (ValueType.equalsIgnoreCase(AppConstants.Global_API)) {

                        //map_Data = AppConstants.GlobalObjects.getAPIs_ListHash().get(sourceName);
                        //nk realm
                        map_Data = RealmDBHelper.getTableDataInLHM(context, sourceName);
                    } else if (ValueType.equalsIgnoreCase(AppConstants.Global_FormFields)) {
                        //map_Data = AppConstants.GlobalObjects.getForms_ListHash().get(sourceName);
                        //nk realm
                        map_Data = RealmDBHelper.getTableDataInLHM(context, sourceName);
                    } else if (ValueType.equalsIgnoreCase(AppConstants.Global_Query)) {
                        //map_Data = AppConstants.GlobalObjects.getForms_ListHash().get(sourceName);
                        //nk realm
                        map_Data = RealmDBHelper.getTableDataInLHM(context, sourceName);
                    }
                }
                Set<String> resultSet = map_Data.keySet();
                String[] resultNames = resultSet.toArray(new String[resultSet.size()]);

                List<String> resultcolum = map_Data.get(resultNames[0]);

                for (int x = 0; x < resultcolum.size(); x++) {
                    if (resultcolum.get(x).trim().length() > 0) {

                        executeflag = true;
                        JSONObject Row_jsonobj = new JSONObject();
                        Row_jsonobj.put("RowId", (x + 1));
                        JSONArray Data_jsonarray = new JSONArray();

                        for (int i = 0; i < list_input.size(); i++) {
                            if (list_input.get(i).isEnable()) {
                                String inValue = "";
                                String columName = "";
                                if (list_input.get(i).getInParam_ExpressionValue().trim().toLowerCase().startsWith("(im:")) {
                                    columName = ImproveHelper.spilitandgetcolumnname(list_input.get(i).getInParam_ExpressionValue().trim().toLowerCase());
                                } else {
                                    columName = list_input.get(i).getInParam_ExpressionValue().trim();
                                }
                                if (map_Data.containsKey(columName)) {
                                    inValue = map_Data.get(columName).get(x);
                                } else {
                                    ExpressionMainHelper ehelper = new ExpressionMainHelper();
                                    inValue = ehelper.ExpressionHelper(context, list_input.get(i).getInParam_ExpressionValue());
                                    //inValue = ImproveHelper.getValueFromGlobalObject(context, list_input.get(i).getInParam_ExpressionValue().trim());

                                }
                                /*if (list_input.get(i).isInParam_ExpressionExists()) {

                                    ExpressionMainHelper ehelper = new ExpressionMainHelper();
                                    inValue = ehelper.ExpressionHelper(context, list_input.get(i).getInParam_ExpressionValue());

                                } else {
                                    String columName = "";
                                    if (list_input.get(i).getInParam_Mapped_ID().trim().toLowerCase().startsWith("(im:")) {
                                        columName = ImproveHelper.spilitandgetcolumnname(list_input.get(i).getInParam_Mapped_ID().trim().toLowerCase());
                                    } else {
                                        columName = list_input.get(i).getInParam_Mapped_ID().trim();
                                    }
                                    if (map_Data.containsKey(columName)) {
                                        inValue = map_Data.get(columName).get(x);
                                    } else {
                                        inValue = ImproveHelper.getValueFromGlobalObject(context, list_input.get(i).getInParam_Mapped_ID().trim());
                                    }
                                }*/
                                JSONObject Data_obj = new JSONObject();
                                Data_obj.put("Key", list_input.get(i).getInParam_Name());
                                Data_obj.put("Value", inValue);
                                Data_jsonarray.put(Data_obj);
                            }
                        }
                        Row_jsonobj.put("RowData", Data_jsonarray);

                        jarr.put(Row_jsonobj);
                    }
                }
            }

            if (executeflag) {
                Map<String, String> dataJson = new HashMap<>();
                dataJson.put("OrgId", sessionManager.getOrgIdFromSession());
                dataJson.put("Uid", MainActivity.getInstance().strUserId);
                dataJson.put("Qname", Select_FormName);
                dataJson.put("InParameters", jarr.toString());
                if (SQLorDML.equalsIgnoreCase("Call SQL")) {
                    dataJson.put("Qtype", "Select");
                } else {
                    dataJson.put("Qtype", "DMLBulk");
                }
                ImproveHelper.improveLog(TAG, "GetSQLorDMLPOST", new Gson().toJson(dataJson));
                if (isNetworkStatusAvialable(context)) {
                    Call<String> call = getServices.GetSQLorDMLPOST(sessionManager.getAuthorizationTokenId(), dataJson);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.e("Result: ", "" + response.body());
                            if (response.body() != null) {
                                setManageDataStatusInGlobal(response.body(), ActionBean);//DML
                                //successCase(response.body());
                            } else {
                                failedCase(new Throwable(response.body()));
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
                            failedCase(t);
                        }
                    });
                } else {

                    saveRequestOffline(jarr.toString());//Saved DML
                }


            } else {
                successCase("","200");
            }

        } catch (Exception e) {
            failedCase(e);
            ImproveHelper.improveException(context, "ManageData", "CallDML", e);
        }
    }

    public void sendInParamFilesToServer(final int fileCount, final int filesCount, Map<String, String> inputFiles) {
        try {
            Set<String> inkeys = inputFiles.keySet();
            String[] InkeyNames = inkeys.toArray(new String[inkeys.size()]);
            String inputkey = InkeyNames[fileCount];
            String path = inputFiles.get(InkeyNames[fileCount]);
            String pageName = MainActivity.getInstance().strAppName.replace(" ", "_");
            String filePath = path;
            if (filePath.startsWith("http") || filePath.trim().equals("")) {
                if (fileCount + 1 == filesCount) {
                    FilesTM2.start();
                    try {
                        FilesTM2.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    fileCountHit++;
                    sendInParamFilesToServer(fileCountHit, filesCount, inputFiles);
                }
            } else {
                if(isNetworkStatusAvialable(context)){
                    final String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
                    finalControlName = inputkey;
                    if (inputkey.contains("|")) {
                        finalControlName = inputkey.split("\\|")[0];
                        subformName = inputkey.split("\\|")[1];
                        sfPos = Integer.parseInt(inputkey.split("\\|")[2]);
                    }

                    new FileUploader(context, fileName, MainActivity.getInstance().strUserId, pageName, false, "BHARGO", new FileUploader.OnImageUploaded() {
                        @Override
                        public void response(String url) {
                            if (url != null && url.startsWith("http")) {
                                if (subformName.contentEquals("")) {
                                    if (MainActivity.getInstance().List_ControlClassObjects.get(finalControlName) instanceof Camera) {
                                        Camera camera = (Camera) MainActivity.getInstance().List_ControlClassObjects.get(finalControlName);
                                        camera.getControlRealPath().setTag(url);
                                    } else if (MainActivity.getInstance().List_ControlClassObjects.get(finalControlName) instanceof FileBrowsing) {
                                        FileBrowsing fileBrowsing = (FileBrowsing) MainActivity.getInstance().List_ControlClassObjects.get(finalControlName);
                                        fileBrowsing.getControlRealPath().setTag(url);
                                    } else if (MainActivity.getInstance().List_ControlClassObjects.get(finalControlName) instanceof VoiceRecording) {
                                        VoiceRecording voiceRecording = (VoiceRecording) MainActivity.getInstance().List_ControlClassObjects.get(finalControlName);
                                        voiceRecording.getControlRealPath().setTag(url);
                                    } else if (MainActivity.getInstance().List_ControlClassObjects.get(finalControlName) instanceof VideoRecording) {
                                        VideoRecording videoRecording = (VideoRecording) MainActivity.getInstance().List_ControlClassObjects.get(finalControlName);
                                        videoRecording.getControlRealPath().setTag(url);
                                    }
                                } else {
                                    LinkedHashMap<String, Object> List_ControlClassObjects = ((MainActivity) context).List_ControlClassObjects;
                                    List<LinkedHashMap<String, Object>> SF_List_ControlClassObjects = new ArrayList<>();
                                    if (List_ControlClassObjects.get(subformName) instanceof SubformView) {
                                        SubformView subformView = (SubformView) List_ControlClassObjects.get(subformName);
                                        SF_List_ControlClassObjects = subformView.subform_List_ControlClassObjects;
                                    } else if (List_ControlClassObjects.get(subformName) instanceof GridControl) {
                                        GridControl gridControl = (GridControl) List_ControlClassObjects.get(subformName);
                                        SF_List_ControlClassObjects = gridControl.gridControl_List_ControlClassObjects;
                                    }
                                    if (SF_List_ControlClassObjects.size() > 0) {
                                        LinkedHashMap<String, Object> SF_ROW_List_ControlClassObjects = SF_List_ControlClassObjects.get(sfPos);
                                        Object object = SF_ROW_List_ControlClassObjects.get(finalControlName);
                                        if (object instanceof Camera) {
                                            Camera camera = (Camera) object;
                                            camera.getControlRealPath().setTag(url);
                                        } else if (object instanceof FileBrowsing) {
                                            FileBrowsing fileBrowsing = (FileBrowsing) object;
                                            fileBrowsing.getControlRealPath().setTag(url);
                                        } else if (object instanceof VoiceRecording) {
                                            VoiceRecording voiceRecording = (VoiceRecording) object;
                                            voiceRecording.getControlRealPath().setTag(url);
                                        } else if (object instanceof VideoRecording) {
                                            VideoRecording videoRecording = (VideoRecording) object;
                                            videoRecording.getControlRealPath().setTag(url);
                                        }
                                    }
                                }
                                if (fileCount + 1 == filesCount) {
                                    FilesTM2.start();
                                    try {
                                        FilesTM2.join();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    fileCountHit++;
                                    sendInParamFilesToServer(fileCountHit, filesCount, inputFiles);
                                }
                            } else {
                                if (url == null) {
                                    failedCase(new Throwable("File upload Failed"));
                                } else {
                                    failedCase(new Throwable("File upload Failed\n" + url));
                                }
                           /* if (fileCount + 1 == filesCount) {
                                FilesTM2.start();
                                try {
                                    FilesTM2.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                fileCountHit++;
                                sendInParamFilesToServer(fileCountHit, filesCount, inputFiles);
                            }*/
                            }
                        }
                    }).execute(filePath);
                }else {
                    //no internet : prepare string for saveRequest
                    if (ActionObj.getSaveOfflineType().equalsIgnoreCase("Request")) {
                        FilesTM2.start();
                        try {
                            FilesTM2.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        failedCase(new Throwable(context.getString(R.string.no_internet)));
                    }

                }

            }

        } catch (Exception e) {
            failedCase(e);
        }
    }


    public Map<String, String> checkFilesandGetFiles(List<API_InputParam_Bean> listInparams) {
        List<String> temp_Names = new ArrayList<>();
        Map<String, String> inparamFiles = new HashMap<>();
        boolean returnflag = false;
        try {
            if (listInparams.size() > 0) {
                for (int i = 0; i < listInparams.size(); i++) {
                    if (listInparams.get(i).getInParam_ExpressionValue().trim().startsWith("(im:")) {
                        String ValueStr = listInparams.get(i).getInParam_ExpressionValue().trim().toLowerCase();
                        String ValueType = ValueStr.substring(4, ValueStr.indexOf("."));
                        ValueStr = ValueStr.substring(4, ValueStr.lastIndexOf(")"));
                        if (ValueType.equalsIgnoreCase(AppConstants.Global_ControlsOnForm)) {
                            String ConditionValue = ValueStr.split("\\.")[1];

                            List<ControlObject> list_Controls = ((MainActivity) context).dataCollectionObject.getControls_list();
                            LinkedHashMap<String, Object> List_ControlClassObjects = ((MainActivity) context).List_ControlClassObjects;
                            LinearLayout linearLayout = ((MainActivity) context).linearLayout;

                            for (int x = 0; x < list_Controls.size(); x++) {
                                ControlObject temp_controlObj = list_Controls.get(x);

                                if (temp_controlObj.getControlName().equalsIgnoreCase(ConditionValue) &&
                                        (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_CAMERA) ||
                                                temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_FILE_BROWSING) ||
                                                temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_VOICE_RECORDING) ||
                                                (temp_controlObj.getControlType().contentEquals(CONTROL_TYPE_VIDEO_RECORDING)))) {
                                    returnflag = true;
                                    String inValue = ImproveHelper.getValueFromGlobalObject(context, listInparams.get(i).getInParam_ExpressionValue().trim());
                                    inparamFiles.put(ConditionValue, inValue);
                                    if (!temp_Names.contains(listInparams.get(i).getInParam_Name())) {
                                        temp_Names.add(listInparams.get(i).getInParam_Name());
                                        //inputFilesForDML.add(listInparams.get(i).getInParam_Name() + "|" + i + "|M");
                                        inputFilesForDML.add(listInparams.get(i).getInParam_Name());
                                    }

                                    /*
                                     * --------------------------------------------------------------------------
                                     * Method: spacing Parameters: MotionEvent Returns: float Description:
                                     * checks the spacing between the two fingers on touch
                                     * ----------------------------------------------------
                                     */
                                }
                            }
                        } else if (ValueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
                            String expression = "";
                            String SubformName = ValueStr.split("\\.")[1];
                            String ConditionValue = ValueStr.split("\\.")[2];
//                            ConditionValue = ImproveHelper.spilitandgetcolumnname(ValueStr);
                            if (ConditionValue.endsWith("_processrow") || ConditionValue.endsWith("_allrows") || ConditionValue.endsWith("_currentrow")) {
                                ConditionValue = ConditionValue.substring(0, ConditionValue.lastIndexOf("_"));
                            }
                            if (listInparams.get(i).getInParam_ExpressionValue().toLowerCase().endsWith("_allrows)")) {
                                expression = listInparams.get(i).getInParam_ExpressionValue().toLowerCase();
                                expression = expression.replace("_allrows)", "_currentrow)");
                            } else {
                                expression = listInparams.get(i).getInParam_ExpressionValue();
                            }
                            LinkedHashMap<String, Object> List_ControlClassObjects = ((MainActivity) context).List_ControlClassObjects;
                            List<LinkedHashMap<String, Object>> SF_List_ControlClassObjects = new ArrayList<>();
                            if (List_ControlClassObjects.get(SubformName) instanceof SubformView) {
                                SubformView subformView = (SubformView) List_ControlClassObjects.get(SubformName);
                                SF_List_ControlClassObjects = subformView.subform_List_ControlClassObjects;
                            } else if (List_ControlClassObjects.get(SubformName) instanceof GridControl) {
                                GridControl subformView = (GridControl) List_ControlClassObjects.get(SubformName);
                                SF_List_ControlClassObjects = subformView.gridControl_List_ControlClassObjects;
                            }
                            for (int j = 0; j < SF_List_ControlClassObjects.size(); j++) {
                                AppConstants.SF_Container_position = j;
                                String inValue = ImproveHelper.getValueFromGlobalObject(context, expression.trim());
                                LinkedHashMap<String, Object> SF_ROW_List_ControlClassObjects = SF_List_ControlClassObjects.get(j);
                                Object object = SF_ROW_List_ControlClassObjects.get(ConditionValue);
                                ControlObject controlObject = new ControlObject();
                                if (object instanceof Camera) {
                                    Camera camera = (Camera) object;
                                    camera.getControlRealPath().setTag(inValue);
                                    inparamFiles.put(ConditionValue + "|" + SubformName + "|" + j, inValue);
                                    if (!temp_Names.contains(listInparams.get(i).getInParam_Name())) {
                                        temp_Names.add(listInparams.get(i).getInParam_Name());
                                       // inputFilesForDML.add(listInparams.get(i).getInParam_Name() + "|" + i + "|S");
                                        inputFilesForDML.add(listInparams.get(i).getInParam_Name());
                                    }
                                } else if (object instanceof FileBrowsing) {
                                    FileBrowsing fileBrowsing = (FileBrowsing) object;
                                    fileBrowsing.getControlRealPath().setTag(inValue);
                                    inparamFiles.put(ConditionValue + "|" + SubformName + "|" + j, inValue);
                                    if (!temp_Names.contains(listInparams.get(i).getInParam_Name())) {
                                        temp_Names.add(listInparams.get(i).getInParam_Name());
                                       // inputFilesForDML.add(listInparams.get(i).getInParam_Name() + "|" + i + "|S");
                                        inputFilesForDML.add(listInparams.get(i).getInParam_Name() );
                                    }
                                } else if (object instanceof VoiceRecording) {
                                    VoiceRecording voiceRecording = (VoiceRecording) object;
                                    voiceRecording.getControlRealPath().setTag(inValue);
                                    inparamFiles.put(ConditionValue + "|" + SubformName + "|" + j, inValue);
                                    if (!temp_Names.contains(listInparams.get(i).getInParam_Name())) {
                                        temp_Names.add(listInparams.get(i).getInParam_Name());
                                       // inputFilesForDML.add(listInparams.get(i).getInParam_Name() + "|" + i + "|S");
                                        inputFilesForDML.add(listInparams.get(i).getInParam_Name());
                                    }
                                } else if (object instanceof VideoRecording) {
                                    VideoRecording videoRecording = (VideoRecording) object;
                                    videoRecording.getControlRealPath().setTag(inValue);
                                    inparamFiles.put(ConditionValue + "|" + SubformName + "|" + j, inValue);
                                    if (!temp_Names.contains(listInparams.get(i).getInParam_Name())) {
                                        temp_Names.add(listInparams.get(i).getInParam_Name());
                                        //inputFilesForDML.add(listInparams.get(i).getInParam_Name() + "|" + i + "|S");
                                        inputFilesForDML.add(listInparams.get(i).getInParam_Name() );
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, "ManageData", "checkFilesandGetFiles", e);
        }
        return inparamFiles;
    }

    private void LoadCallFormOfflineData(LinkedHashMap<String, List<String>> OutputData,
                                         ActionWithoutCondition_Bean ActionBean) {
        try {
            List<API_OutputParam_Bean> List_Form_OutParams = ActionBean.getList_Form_OutParams();
            //realm
            if (RealmDBHelper.existTable(context, ActionBean.getSelect_FormName())) {
                RealmDBHelper.deleteTable(context, ActionBean.getSelect_FormName());
            }
            RealmDBHelper.createTableWithLHM(context, ActionBean.getSelect_FormName(), OutputData);
            RealmDBHelper.insertFromWithLHM(context, ActionBean.getSelect_FormName(), OutputData);
            //RealmDBHelper.createTableWithInsertFromAction(context, ActionBean.getSelect_FormName(), response);
            if (ActionBean.getSaveOfflineType().equalsIgnoreCase("Response")) {
                RealmDBHelper.updateSaveOfflineTable(context, MainActivity.getInstance().strAppName, ActionBean.getSelect_FormName(), ActionBean.getActionType(), "Offline");
            } else {
                RealmDBHelper.updateSaveOfflineTable(context, MainActivity.getInstance().strAppName, ActionBean.getSelect_FormName(), ActionBean.getActionType(), "Online");
            }
            //realm
            if (ActionBean.getResult_NoOfRecords().equalsIgnoreCase("Single")) {
                for (int i = 0; i < List_Form_OutParams.size(); i++) {
                    if (!List_Form_OutParams.get(i).isOutParam_Delete()) {
                        ControlUtils.setSingleValuetoControlFromCallAPIORGetData(context, OutputData, List_Form_OutParams.get(i),
                                MainActivity.getInstance().dataCollectionObject, MainActivity.getInstance().List_ControlClassObjects);
                    }
                }
                successCase("","200");
            } else {
                String SelectedSubForm = ActionBean.getSelectedSubForm();
                if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_SUBFORM)) {
                    SubformView subform = (SubformView) MainActivity.getInstance().List_ControlClassObjects.get(SelectedSubForm);
                    List<String> MappedControlID = new ArrayList<String>();
                    List<String> MappedParamName = new ArrayList<String>();

                    for (int i = 0; i < List_Form_OutParams.size(); i++) {
                        if (!List_Form_OutParams.get(i).isOutParam_Delete()) {
                            MappedControlID.add(List_Form_OutParams.get(i).getOutParam_Mapped_ID().trim());
                            MappedParamName.add(List_Form_OutParams.get(i).getOutParam_Name());
                        }
                    }
                    List<String> MappedValues = OutputData.get(MappedControlID.get(0).toLowerCase());
                    subform.setiMinRows(MappedValues.size());

                    if (subform.iMaxRows <= MappedValues.size()) {
                        subform.setiMaxRows(MappedValues.size());
                    } else {
                        subform.setiMaxRows(Integer.parseInt(subform.controlObject.getMaximumRows()));
                    }

                    View view = subform.getSubFormView();
                    LinearLayout ll_MainSubFormContainer = view.findViewById(R.id.ll_MainSubFormContainer);

                    List<LinkedHashMap<String, Object>> subform_List_ControlClassObjects = subform.getList_ControlClassObjects();
                    if (!ActionBean.getMulti_DataType().equalsIgnoreCase("append")) {
                        ll_MainSubFormContainer.removeAllViews();
                        subform_List_ControlClassObjects.removeAll(subform_List_ControlClassObjects);
                    }
                    int totalrowsbeforeadding = subform.getList_ControlClassObjects().size();
                    for (int i = 0; i < MappedValues.size(); i++) {
                        subform.addInnerSubFormStrip(subform.getContext(), ll_MainSubFormContainer, true);
                        LinkedHashMap<String, Object> temp_object = subform.getList_ControlClassObjects().get(totalrowsbeforeadding + i);
                        ActionUitls.SetValuetoMultiControlInCallAPIFormUsedbyControlObject(context, i, OutputData, List_Form_OutParams, subform.controlObject.getSubFormControlList(), temp_object);
                    }
                } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_GRIDFORM)) {
                    GridControl subform = (GridControl) MainActivity.getInstance().List_ControlClassObjects.get(SelectedSubForm);
                    List<String> MappedControlID = new ArrayList<String>();
                    List<String> MappedParamName = new ArrayList<String>();

                    for (int i = 0; i < List_Form_OutParams.size(); i++) {
                        if (!List_Form_OutParams.get(i).isOutParam_Delete()) {
                            MappedControlID.add(List_Form_OutParams.get(i).getOutParam_Mapped_ID().trim());
                            MappedParamName.add(List_Form_OutParams.get(i).getOutParam_Name());
                        }
                    }
                    List<String> MappedValues = OutputData.get(MappedControlID.get(0).toLowerCase());
                    subform.setiMinRows(MappedValues.size());
                    if (subform.iMaxRows <= MappedValues.size()) {
                        subform.setiMaxRows(MappedValues.size());
                    } else {
                        subform.setiMaxRows(Integer.parseInt(subform.controlObject.getMaximumRows()));
                    }

                    View view = subform.getSubFormView();

                    LinearLayout ll_grid_view = view.findViewById(R.id.ll_grid_view);

                    subform.SubFormTAG = 0;
                    List<LinkedHashMap<String, Object>> subform_List_ControlClassObjects = subform.getList_ControlClassObjects();
                    if (!ActionBean.getMulti_DataType().equalsIgnoreCase("append")) {
                        ll_grid_view.removeAllViews();
                        subform_List_ControlClassObjects.removeAll(subform_List_ControlClassObjects);
                    }

                    int totalrowsbeforeadding = subform.getList_ControlClassObjects().size();

                    for (int i = 0; i < MappedValues.size(); i++) {
                        subform.addGridRow();
                        LinkedHashMap<String, Object> temp_object = subform.getList_ControlClassObjects().get(totalrowsbeforeadding + i);

                        ActionUitls.SetValuetoMultiControlInCallAPIFormUsedbyControlObject(context, i, OutputData, List_Form_OutParams, subform.controlObject.getSubFormControlList(), temp_object);
                    }

                } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_DATAVIEWER)) {
                    DataViewer DataViewer = (DataViewer) MainActivity.getInstance().List_ControlClassObjects.get(SelectedSubForm);
                    List<DataViewerModelClass> dataViewerModelClassList = new ArrayList<>();
                    DataViewer.setOutputData(OutputData);
                    List<String> Header_list = OutputData.get(ActionBean.getHeader_Mapped_item().toLowerCase());
                    List<String> SubHeader_list = new ArrayList<>();
                    List<String> DateandTime_list = new ArrayList<>();
                    List<String> ImagePath_list = new ArrayList<>();
                    List<String> ProfileImagePath_list = new ArrayList<>();
                    LinkedHashMap<String, List<String>> hash_Description = new LinkedHashMap<String, List<String>>();
                    List<String> Advance_ImageSource = new ArrayList<>();
                    List<String> Advance_ConditionColumn = new ArrayList<>();
//--EV--//
                    List<String> Distance_list = new ArrayList<>();
                    List<String> WorkingHours_list = new ArrayList<>();
                    List<String> ItemOne_list = new ArrayList<>();
                    List<String> ItemTwo_list = new ArrayList<>();
                    List<String> Rating_list = new ArrayList<>();
                    List<String> Source_Icons_list = new ArrayList<>();
                    List<String> Source_Name_list = new ArrayList<>();
                    List<String> Source_Time_list = new ArrayList<>();
                    List<String> News_Type_list = new ArrayList<>();
                    //--EV--//


                    if (ActionBean.getSubHeader_Mapped_item() != null && ActionBean.getSubHeader_Mapped_item().length() > 0) {
                        SubHeader_list = OutputData.get(ActionBean.getSubHeader_Mapped_item().toLowerCase());
                    }
                    if (ActionBean.getDateandTime_Mapped_item() != null && ActionBean.getDateandTime_Mapped_item().length() > 0) {
                        DateandTime_list = OutputData.get(ActionBean.getDateandTime_Mapped_item().toLowerCase());
                    }

                    if (ActionBean.getImage_Mapped_item() != null && ActionBean.getImage_Mapped_item().length() > 0) {
                        ImagePath_list = OutputData.get(ActionBean.getImage_Mapped_item().toLowerCase());
                    }
                    if (ActionBean.getList_ImageAdvanced_Items() != null && ActionBean.getList_ImageAdvanced_Items().size() > 0) {
                        if (ActionBean.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                            Advance_ImageSource = OutputData.get(ActionBean.getImageAdvanced_ImageSource().toLowerCase());
                        }
                        Advance_ConditionColumn = OutputData.get(ActionBean.getImageAdvanced_ConditionColumn().toLowerCase());
                    }

                    if (ActionBean.getProfileImage_Mapped_item() != null && ActionBean.getProfileImage_Mapped_item().length() > 0) {
                        ProfileImagePath_list = OutputData.get(ActionBean.getProfileImage_Mapped_item().toLowerCase());
                    }
                    if (ActionBean.getDescription_Mapped_item() != null && ActionBean.getDescription_Mapped_item().size() > 0) {
                        for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                            List<String> X_ItemList = OutputData.get(ActionBean.getDescription_Mapped_item().get(x).toLowerCase());
                            hash_Description.put(ActionBean.getDescription_Mapped_item().get(x), X_ItemList);
                        }
                    }
                    //--EV--//
                    if (ActionBean.getDistance() != null && ActionBean.getDistance().length() > 0) {
                        Distance_list = OutputData.get(ActionBean.getDistance().toLowerCase());
                    }
                    if (ActionBean.getWorking_hours() != null && ActionBean.getWorking_hours().length() > 0) {
                        WorkingHours_list = OutputData.get(ActionBean.getWorking_hours().toLowerCase());
                    }
                    if (ActionBean.getItem_one_count() != null && ActionBean.getItem_one_count().length() > 0) {
                        ItemOne_list = OutputData.get(ActionBean.getItem_one_count().toLowerCase());
                    }
                    if (ActionBean.getItem_two_count() != null && ActionBean.getItem_two_count().length() > 0) {
                        ItemTwo_list = OutputData.get(ActionBean.getItem_two_count().toLowerCase());
                    }
                    if (ActionBean.getRating() != null && ActionBean.getRating().length() > 0) {
                        Rating_list = OutputData.get(ActionBean.getRating().toLowerCase());
                    }

                    if (ActionBean.getSource_icon() != null && ActionBean.getSource_icon().length() > 0) {
                        Source_Icons_list = OutputData.get(ActionBean.getSource_icon().toLowerCase());
                    }
                    if (ActionBean.getSource_name() != null && ActionBean.getSource_name().length() > 0) {
                        Source_Name_list = OutputData.get(ActionBean.getSource_name().toLowerCase());
                    }
                    if (ActionBean.getSource_time() != null && ActionBean.getSource_time().length() > 0) {
                        Source_Time_list = OutputData.get(ActionBean.getSource_time().toLowerCase());
                    }
                    if (ActionBean.getNews_type() != null && ActionBean.getNews_type().length() > 0) {
                        News_Type_list = OutputData.get(ActionBean.getNews_type().toLowerCase());
                    }
                    //--EV--//

                    for (int i = 0; i < Header_list.size(); i++) {
                        DataViewerModelClass dmv = new DataViewerModelClass();
                        dmv.setHeading(Header_list.get(i));
                        List<String> Description = new ArrayList<String>();
                        switch (DataViewer.controlObject.getDataViewer_UI_Pattern()) {
                            case AppConstants.GridView_With_Image_2_Columns:
                                if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                    dmv.setImage_Path(ImagePath_list.get(i));
                                }
                                if (ActionBean.getList_ImageAdvanced_Items() != null && ActionBean.getList_ImageAdvanced_Items().size() > 0) {
                                    if (ActionBean.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                        dmv.setImage_Path(null);
                                        dmv.setAdvanceImage_Source(Advance_ImageSource.get(i));
                                    }
                                    dmv.setList_Image_Path(ActionBean.getList_ImageAdvanced_Items());
                                    dmv.setAdvanceImage_ConditionColumn(Advance_ConditionColumn.get(i));
                                }
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                            case AppConstants.GridView_With_Image_3_Columns:
                                dmv.setSubHeading(SubHeader_list.get(i));
                                if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                    dmv.setImage_Path(ImagePath_list.get(i));
                                }
                                if (ActionBean.getList_ImageAdvanced_Items() != null && ActionBean.getList_ImageAdvanced_Items().size() > 0) {
                                    if (ActionBean.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                        dmv.setImage_Path(null);
                                        dmv.setAdvanceImage_Source(Advance_ImageSource.get(i));
                                    }
                                    dmv.setList_Image_Path(ActionBean.getList_ImageAdvanced_Items());
                                    dmv.setAdvanceImage_ConditionColumn(Advance_ConditionColumn.get(i));
                                }
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                            case AppConstants.GridView_With_Image_2_Columns_call:
                                if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                    dmv.setImage_Path(ImagePath_list.get(i));
                                }
                                if (ActionBean.getList_ImageAdvanced_Items() != null && ActionBean.getList_ImageAdvanced_Items().size() > 0) {
                                    if (ActionBean.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                        dmv.setImage_Path(null);
                                        dmv.setAdvanceImage_Source(Advance_ImageSource.get(i));
                                    }
                                    dmv.setList_Image_Path(ActionBean.getList_ImageAdvanced_Items());
                                    dmv.setAdvanceImage_ConditionColumn(Advance_ConditionColumn.get(i));
                                }
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                            case AppConstants.GridView_With_Image_3_Columns_call:
                                dmv.setSubHeading(SubHeader_list.get(i));
                                if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                    dmv.setImage_Path(ImagePath_list.get(i));
                                }
                                if (ActionBean.getList_ImageAdvanced_Items() != null && ActionBean.getList_ImageAdvanced_Items().size() > 0) {
                                    if (ActionBean.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                        dmv.setImage_Path(null);
                                        dmv.setAdvanceImage_Source(Advance_ImageSource.get(i));
                                    }
                                    dmv.setList_Image_Path(ActionBean.getList_ImageAdvanced_Items());
                                    dmv.setAdvanceImage_ConditionColumn(Advance_ConditionColumn.get(i));
                                }
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                            case AppConstants.GridView_With_Video_2_Columns:
                                dmv.setVideo_Path(ImagePath_list.get(i));
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                            case AppConstants.GridView_With_Video_3_Columns:
                                dmv.setSubHeading(SubHeader_list.get(i));
                                dmv.setVideo_Path(ImagePath_list.get(i));
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                            case AppConstants.GridView_With_Video_2_Columns_call:
                                dmv.setVideo_Path(ImagePath_list.get(i));
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                            case AppConstants.GridView_With_Video_3_Columns_call:
                                dmv.setSubHeading(SubHeader_list.get(i));
                                dmv.setVideo_Path(ImagePath_list.get(i));
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                            case AppConstants.ListView_2_Columns:
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                            case AppConstants.ListView_With_Image_2_Columns:
                                dmv.setImage_Path(ImagePath_list.get(i));
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                            case AppConstants.ListView_With_Image_3_Columns:
                                dmv.setSubHeading(SubHeader_list.get(i));
                                dmv.setImage_Path(ImagePath_list.get(i));
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                            case AppConstants.Geo_Spatial_View:
                                if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                    dmv.setGpsValue(ImagePath_list.get(i));
                                }
                                dmv.setSubHeading(SubHeader_list.get(i));
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                            case AppConstants.TimeLine_View:
                                dmv.setDateandTime(DateandTime_list.get(i));
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                            case AppConstants.TimeLine_With_Photo_View:
                                if (ProfileImagePath_list != null && ProfileImagePath_list.size() != 0) {
                                    dmv.setProfileImage_Path(ProfileImagePath_list.get(i));
                                }

                                dmv.setDateandTime(DateandTime_list.get(i));
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                            case AppConstants.LinearView_With_Video:
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                            case AppConstants.BlogSpot_View:
                                if (ProfileImagePath_list != null && ProfileImagePath_list.size() != 0) {
                                    dmv.setProfileImage_Path(ProfileImagePath_list.get(i));
                                }
                                if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                    dmv.setImage_Path(ImagePath_list.get(i));
                                }
                                dmv.setSubHeading(SubHeader_list.get(i));
                                dmv.setImage_Path(ImagePath_list.get(i));
                                break;

                            case AppConstants.MapView_Item_Info:
                                dmv.setSubHeading(SubHeader_list.get(i));
                                dmv.setDistance(Distance_list.get(i));
                                dmv.setWorkingHours(WorkingHours_list.get(i));
                                dmv.setItemOneCount(ItemOne_list.get(i));
                                dmv.setItemTwoCount(ItemTwo_list.get(i));
                                break;
                            case AppConstants.EV_Dashboard_Design_One:
                            case AppConstants.EV_Dashboard_Design_Three:
                                dmv.setSubHeading(SubHeader_list.get(i));
                                if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                    dmv.setImage_Path(ImagePath_list.get(i));
                                }
                                if (ActionBean.getList_ImageAdvanced_Items() != null && ActionBean.getList_ImageAdvanced_Items().size() > 0) {
                                    if (ActionBean.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                        dmv.setImage_Path(null);
                                        dmv.setAdvanceImage_Source(Advance_ImageSource.get(i));
                                    }
                                    dmv.setList_Image_Path(ActionBean.getList_ImageAdvanced_Items());
                                    dmv.setAdvanceImage_ConditionColumn(Advance_ConditionColumn.get(i));
                                }
                                dmv.setRating(Rating_list.get(i));
                                break;
                            case AppConstants.EV_Dashboard_Design_Two:
                                if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                    dmv.setImage_Path(ImagePath_list.get(i));
                                }
                                if (ActionBean.getList_ImageAdvanced_Items() != null && ActionBean.getList_ImageAdvanced_Items().size() > 0) {
                                    if (ActionBean.getImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                                        dmv.setImage_Path(null);
                                        dmv.setAdvanceImage_Source(Advance_ImageSource.get(i));
                                    }
                                    dmv.setList_Image_Path(ActionBean.getList_ImageAdvanced_Items());
                                    dmv.setAdvanceImage_ConditionColumn(Advance_ConditionColumn.get(i));
                                }

                                break;
                            case AppConstants.EV_News_Design:
                                if (Source_Icons_list.size() > 0) {
                                    dmv.setSource_icon(Source_Icons_list.get(i));
                                    dmv.setSource_name(Source_Name_list.get(i));
                                    dmv.setSource_time(Source_Time_list.get(i));
                                }
                                dmv.setNews_type(News_Type_list.get(i));
                                if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                    dmv.setImage_Path(ImagePath_list.get(i));
                                }
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                            case AppConstants.EV_Notifications_Design:
                                dmv.setSubHeading(SubHeader_list.get(i));
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                            case AppConstants.EV_Dealers_Design:
                                dmv.setSubHeading(SubHeader_list.get(i));
                                dmv.setDistance(Distance_list.get(i));
                                dmv.setWorkingHours(WorkingHours_list.get(i));
                                if (ImagePath_list != null && ImagePath_list.size() != 0) {
                                    dmv.setImage_Path(ImagePath_list.get(i));
                                }
                                break;
                            case AppConstants.EV_Jobs_Design:
                                dmv.setSubHeading(SubHeader_list.get(i));
                                dmv.setSource_name(Source_Name_list.get(i));
                                dmv.setSource_time(Source_Time_list.get(i));
                                for (int x = 0; x < ActionBean.getDescription_Mapped_item().size(); x++) {
                                    List<String> xItem = hash_Description.get(ActionBean.getDescription_Mapped_item().get(x));
                                    Description.add(xItem.get(i));
                                }
                                dmv.setDescription(Description);
                                break;
                        }
                        dataViewerModelClassList.add(dmv);
                    }

                    DataViewer.SetDataViewerData(dataViewerModelClassList);

                } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_LISTVIEW)) {
                    String cName = ActionBean.getResult_ListView_FilterMappedControl();
                    String mapControl = ActionBean.getResult_ListView_FilterItem();

                    List<String> MappedValues = OutputData.get(mapControl);
                    List<String> MappedValuesIDS = OutputData.get(mapControl);
                    Log.e("Result: ", "" + MappedValues.toString());
                    ActionUitls.SetMultipleValuesbyControlID(context, cName, MappedValues, MappedValuesIDS,
                            MainActivity.getInstance().dataCollectionObject,
                            MainActivity.getInstance().List_ControlClassObjects);
                /*List<String> MappedValues = OutputData.get(MappedParamName.get(0).toLowerCase());
                SetMultipleValuesbyControlID(ActionBean.getResult_ListView_FilterItem(), MappedValues);*/
//                List<String> MappedValues = OutputData.get(ActionBean.getSelectedSubForm());
//                List<String> MappedValuesIDS = OutputData.get(ActionBean.getSelectedSubForm());
//                SetMultipleValuesbyControlID(ActionBean.getResult_ListView_FilterItem(), MappedValues, MappedValuesIDS);


                } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_MAPVIEW)) {
                    MapControl MapControl = (MapControl) MainActivity.getInstance().List_ControlClassObjects.get(SelectedSubForm);
                    if (List_Form_OutParams.get(0).getOutParam_Mapped_ID() != null && List_Form_OutParams.get(0).getOutParam_Mapped_ID().length() > 0) {
                        String MappedValue = List_Form_OutParams.get(0).getOutParam_Mapped_ID();
                        List<String> MappedValues = OutputData.get(MappedValue.toLowerCase());
                        String DefultMarker = List_Form_OutParams.get(0).getOutParam_Marker_defultImage();
                        MapControl.setMapPonitsDynamically(AppConstants.map_Multiple_Marker, MappedValues, DefultMarker);
                    } else {

                    }

                } else if (ActionBean.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_CALENDARVIEW)) {
                    CalendarEventControl CalendarEventControl = (CalendarEventControl) MainActivity.getInstance().List_ControlClassObjects.get(SelectedSubForm);

                    String MappedValue_Date = List_Form_OutParams.get(0).getOutParam_Mapped_ID();
                    String MappedValue_Message = List_Form_OutParams.get(1).getOutParam_Mapped_ID();
                    List<String> MappedValues_Date = OutputData.get(MappedValue_Date.toLowerCase());
                    List<String> MappedValues_Message = OutputData.get(MappedValue_Message.toLowerCase());

                    for (int x = 0; x < MappedValues_Date.size(); x++) {
                        CalendarEventControl.AddDateDynamically("Single", MappedValues_Date.get(x), MappedValues_Message.get(x));
                    }

                }
                successCase("","200");
            }
        } catch (Exception e) {
            failedCase(e);
            ImproveHelper.improveException(context, "ManageData", "LoadCallFormOfflineData", e);
        }
    }

    private class ManageDataStart extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(context, progressMsg);
        }

        @Override
        protected synchronized Void doInBackground(Void... voids) {

            try {
                ManageData.this.execute();
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

    Handler handSendDataFiles_insert = new Handler() {
        public void handleMessage(android.os.Message msg) {
            sendFilesToServer_insert();
        }
    };


    Handler handSendDataFiles_update = new Handler() {
        public void handleMessage(android.os.Message msg) {
            sendFilesToServer_update();
        }
    };


}
