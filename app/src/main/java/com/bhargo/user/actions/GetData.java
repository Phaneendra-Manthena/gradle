package com.bhargo.user.actions;


import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_GPS;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.showToastRunOnUI;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.bhargo.user.Expression.ExpressionMainHelper;
import com.bhargo.user.Java_Beans.API_InputParam_Bean;
import com.bhargo.user.Java_Beans.API_OutputParam_Bean;
import com.bhargo.user.Java_Beans.ActionWithoutCondition_Bean;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.Java_Beans.ImageAdvanced_Mapped_Item;
import com.bhargo.user.Java_Beans.LanguageMapping;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.advanced.ChartControl;
import com.bhargo.user.controls.advanced.DataTableControl;
import com.bhargo.user.controls.advanced.DataViewer;
import com.bhargo.user.controls.advanced.GridControl;
import com.bhargo.user.controls.advanced.SectionControl;
import com.bhargo.user.controls.advanced.SubformView;
import com.bhargo.user.controls.standard.CalendarEventControl;
import com.bhargo.user.controls.standard.MapControl;
import com.bhargo.user.interfaces.Callback;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.DataViewerModelClass;
import com.bhargo.user.realm.RealmDBHelper;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.CallFormFieldsData;
import com.bhargo.user.utils.ControlUtils;
import com.bhargo.user.utils.ImproveHelper;
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
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class GetData {

    String TAG = "GetData";

    Callback callbackListener;
    ActionWithoutCondition_Bean ActionObj;
    Context context;
    String inparam_current_location = "";
    SessionManager sessionManager;
    LinkedHashMap<String, Object> List_ControlClassObjects;
    String strAppName;
    DataCollectionObject dataCollectionObject;
    GetServices getServices;
    String Outputcolumns = "";
    private boolean gpsContainInInParams;

    public GetData(Context context, SessionManager sessionManager, String strAppName,
                   LinkedHashMap<String, Object> List_ControlClassObjects, DataCollectionObject dataCollectionObject,
                   ActionWithoutCondition_Bean ActionObj, GetServices getServices, Callback callbackListener) {
        this.context = context;
        this.getServices = getServices;
        this.sessionManager = sessionManager;
        this.List_ControlClassObjects = List_ControlClassObjects;
        this.strAppName = strAppName;
        this.callbackListener = callbackListener;
        this.dataCollectionObject = dataCollectionObject;
        this.ActionObj = ActionObj;
        new GetDataStart().execute();
    }

    public void setInparam_current_location(String inparam_current_location) {
        this.inparam_current_location = inparam_current_location;
    }

    private void execute() {

        if (ActionObj.getGetDataActionType() != null) {
            if (ActionObj.getGetDataActionType().contentEquals("WizardControl") || ActionObj.getGetDataActionType().contentEquals("Wizard")) {
                boolean actionContainGPS = false;
                String Select_FormName = ActionObj.getSelect_FormName();
                String Select_TableName = ActionObj.getSelect_FormTableName();

                List<API_InputParam_Bean> list_input = ActionObj.getList_Form_InParams();
                for (int i = 0; i < list_input.size(); i++) {
                    if (list_input.get(i).isEnable()) {

                        if (list_input.get(i).getInParam_Mapped_ID().contentEquals(CONTROL_TYPE_GPS)) {
                            actionContainGPS = true;
                        }
                    }
                }
                List<API_OutputParam_Bean> list_Formout = ActionObj.getList_Form_OutParams();
                for (int i = 0; i < list_Formout.size(); i++) {
                    if (list_Formout.get(i).getOutParam_Mapped_Expression() != null && list_Formout.get(i).getOutParam_Mapped_Expression().toLowerCase().contains("(im:" + AppConstants.Global_GPSControl.toLowerCase())) {
                        actionContainGPS = true;
                        break;
                    }
                }
                //actionContainGPS=true;
                if (actionContainGPS) {
                   closeProgressDialog();
                    MainActivity.getInstance().callLocationHelper(new Callback() {
                        @Override
                        public void onSuccess(Object result) {
                            setInparam_current_location(result.toString());
                            showProgressDialog(context,context.getResources().getString(
                                    R.string.please_wait_serverhit));
                            CallFormFields();
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            callbackListener.onFailure(throwable);
                        }
                    });
                } else {
                    CallFormFields();
                }
            } else if (ActionObj.getGetDataActionType().contentEquals("DirectQuery")) {
                if (ImproveHelper.isNetworkStatusAvialable(context)) {
                    getDataDirectQuery(ActionObj);
                } else {
                    failureCase("No Internet.");
                }
            } else {
                if (ImproveHelper.isNetworkStatusAvialable(context)) {
                    CallSQLorDML(ActionObj, ActionObj.getGetDataActionType());
                } else {
                    failureCase("No Internet.");
                }
            }
        } else {
            failureCase("GetDataActionType() Is Null");
        }
    }

    private void failureCase(String errormsg) {
        closeProgressDialog();
        if (ActionObj.isMessage_FailNoRecordsIsEnable()) {
            ExpressionMainHelper ehelper = new ExpressionMainHelper();
            String Message = ehelper.ExpressionHelper(context, ActionObj.getMessage_Fail());
            if (ActionObj.getMessage_FailNoRecordsDisplayType().equalsIgnoreCase("2")) {
                if (MainActivity.getInstance() != null)
                    MainActivity.getInstance().ShowMessageDialogWithOk(context, Message, 2);
            } else {
                showToastRunOnUI(MainActivity.getInstance(),Message);
            }
        } else if (!ActionObj.isMessage_FailNoRecordsIsEnable()) {

        } else {
            showToastRunOnUI(MainActivity.getInstance(),"Your Transaction Failed  ...");
        }
        callbackListener.onFailure(new Throwable(errormsg));
    }

    private void CallSQLorDML(final ActionWithoutCondition_Bean ActionBean, String SQLorDML) {
        try {
            Outputcolumns = "";
            String Select_FormName = ActionBean.getSelect_FormName();
            List<API_InputParam_Bean> list_input = ActionBean.getList_Form_InParams();
//            ActionBean.setList_Form_OutParams(filterOutParams(ActionBean.getList_Form_OutParams()));
            List<API_OutputParam_Bean> list_output = ActionBean.getList_Form_OutParams();

            JSONArray jarr = new JSONArray();
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

                    if (list_input.get(i).getInParam_Optional().equalsIgnoreCase("No")) {
                        InputMap.put("Key", list_input.get(i).getInParam_Name());
                        InputMap.put("Value", inValue);
                        jarr.put(InputMap);
                    } else if (list_input.get(i).getInParam_Optional().equalsIgnoreCase("Yes") && inValue != null && !inValue.equalsIgnoreCase("")) {
                        InputMap.put("Key", list_input.get(i).getInParam_Name());
                        InputMap.put("Key", list_input.get(i).getInParam_Name());
                        if (inValue.contains(",")) {
                            String[] splitComma = inValue.split(",");
                            inValue = splitComma[1];
                        }
                        InputMap.put("Value", inValue);
                        jarr.put(InputMap);
                    }
                }
            }


            for (int i = 0; i < list_output.size(); i++) {
//                if (!list_output.get(i).isOutParam_Delete()) {
//                    if (!Outputcolumns.contains("," + list_output.get(i).getOutParam_Mapped_ID())) {
                Outputcolumns = Outputcolumns + "," + list_output.get(i).getOutParam_Mapped_ID();
//                    }
//                }
            }

            Outputcolumns = Outputcolumns.substring(Outputcolumns.indexOf(",") + 1);
            System.out.println("Outputcolumns before===" + Outputcolumns);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Qname", Select_FormName);
            jsonObject.put("InParameters", jarr.toString());
            if (SQLorDML.equalsIgnoreCase("Call SQL") || SQLorDML.equalsIgnoreCase("SavedQuery")) {
                jsonObject.put("Qtype", "Select");
            } else {
                jsonObject.put("Qtype", "DML");
            }
            jsonObject.put("LazyLoading", "False");
            jsonObject.put("Range", "0-2");
            ImproveHelper.improveLog(TAG, "CallSQLorDML_inputs", jsonObject.toString());
            JsonObject jo = (JsonObject) JsonParser.parseString(jsonObject.toString());

            Call<String> call = getServices.GetSQLorDML(sessionManager.getAuthorizationTokenId(), jo);
            call.enqueue(new retrofit2.Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("Result: ", "" + response.body());
                    processGetDataResponse(response.body());
                    /*if (response.body() != null) {
                        try {
                            LinkedHashMap<String, List<String>> OutputData = null;
                            if (SQLorDML.equalsIgnoreCase("Call SQL") || SQLorDML.equalsIgnoreCase("SavedQuery")) {
                                OutputData = ActionUitls.Convert_JSONForSQL(context, response.body(), Outputcolumns.split("\\,"));
                            } else {
                                OutputData = ActionUitls.Convert_JSONForDML(context, response.body(), Outputcolumns.split("\\,"));
                            }
                            //nk realm:
                            String tableName = ActionBean.getActionId();
                            if (RealmDBHelper.existTable(context, tableName)) {
                                RealmDBHelper.deleteTable(context, tableName);
                            }
                            RealmDBHelper.createTableWithLHM(context, tableName, OutputData);
                            RealmDBHelper.insertFromWithLHM(context, tableName, OutputData);
                            //RealmDBHelper.createTableWithInsertFromAction(context, ActionBean.getSelect_FormName(), response);
                            if (ActionBean.getSaveOfflineType().equalsIgnoreCase("Response")) {
                                RealmDBHelper.updateSaveOfflineTable(context, strAppName, tableName, ActionBean.getActionType(), "Offline");
                            } else {
                                RealmDBHelper.updateSaveOfflineTable(context, strAppName, tableName, ActionBean.getActionType(), "Online");
                            }
                            setGetDataStatusInGlobal(response.body(), ActionObj);
                            //nk realm
                            setDataToSingleOrMultpileControls(OutputData);
                        } catch (Exception e) {
                            failureCase(e.getMessage());
                        }
                    } else {
                        if (ActionBean.isMessage_FailNoRecordsIsEnable()) {
                            ExpressionMainHelper ehelper = new ExpressionMainHelper();
                            String Message = ehelper.ExpressionHelper(context, ActionBean.getMessage_Fail());
                            if (ActionBean.getMessage_FailNoRecordsDisplayType().equalsIgnoreCase("2")) {
                                if (MainActivity.getInstance() != null) {
                                    MainActivity.getInstance().ShowMessageDialogWithOk(context, Message, 2);
                                }
                            } else {
                                Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
                            }
                        } else if (!ActionBean.isMessage_FailNoRecordsIsEnable()) {

                        } else {
                            ImproveHelper.showToast(context, "No Data Found...");
                        }
                        failureCase(response.toString());
                    }*/
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
                    failureCase(t.getMessage());
                }
            });

        } catch (Exception e) {
            closeProgressDialog();
            ImproveHelper.improveException(context, TAG, "CallSQLorDML", e);
            failureCase(e.getMessage());
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
            }
            if (ActionBean.getActionType().contentEquals(AppConstants.MANAGE_DATA)) {
                jsonObject.put("QueryType", "DMLBulk");
            } else {
                jsonObject.put("QueryType", "Select");
            }
            jsonObject.put("DBConnectionSource", ActionBean.getConnectionSource());
            jsonObject.put("DBConnection", ActionBean.getConnectionId());
            JsonObject jo = (JsonObject) JsonParser.parseString(jsonObject.toString());

            Call<String> call = getServices.GetDataDirectQuery(sessionManager.getAuthorizationTokenId(), jo);
            call.enqueue(new retrofit2.Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("Result: ", "" + response.body());
                    if(response.code()==401){
                        failureCase(response.message());
                    }else{
                        processGetDataResponse(response.body());
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
                    failureCase(t.getMessage());
                }
            });

        } catch (Exception e) {
            closeProgressDialog();
            ImproveHelper.improveException(context, TAG, "getDataDirectQuery", e);
            failureCase(e.getMessage());
        }
    }

    private void CallFormFields() {

        try {
            String Outputcolumns = "", Outputcolumns_copy = "";
            String Select_TableName = "";
            if (ActionObj.getSelect_FormTableName() == null && ActionObj.getSelect_FormTableName() == "") {
                Select_TableName = ActionObj.getSelect_FormName();
            } else {
                Select_TableName = ActionObj.getSelect_FormTableName();
            }

            List<API_InputParam_Bean> list_input = ActionObj.getList_Form_InParams();
            List<API_InputParam_Bean> list_input_enabled = new ArrayList<>();
            List<API_OutputParam_Bean> list_output = ActionObj.getList_Form_OutParams();

            JSONArray jarr = new JSONArray();
            for (int i = 0; i < list_input.size(); i++) {
                if (list_input.get(i).isEnable()) {
                    list_input_enabled.add(list_input.get(i));
                }
            }

            for (int i = 0; i < list_input_enabled.size(); i++) {
                JSONObject InputMap = new JSONObject();
                String inValue = ImproveHelper.getValueFromGlobalObject(context, list_input_enabled.get(i).getInParam_ExpressionValue());
               /* if (inValue.contentEquals("") && list_input_enabled.get(i).isInParam_ExpressionExists()) {
                    ExpressionMainHelper expressionMainHelper = new ExpressionMainHelper();
                    inValue = expressionMainHelper.ExpressionHelper(context, list_input_enabled.get(i).getInParam_ExpressionValue());
                }*/

                InputMap.put("ColumnName", list_input_enabled.get(i).getInParam_Name());
                InputMap.put("Oparator", ImproveHelper.getOparator(list_input_enabled.get(i).getInParam_Operator()));
                InputMap.put("Value", inValue);

                if (i != list_input_enabled.size() - 1) {
                    if (list_input_enabled.get(i).getInParam_and_or() != null) {
                        InputMap.put("Condition", list_input_enabled.get(i).getInParam_and_or()+" ");
                    } else {
                        InputMap.put("Condition", "");
                    }
                } else {
                    InputMap.put("Condition", "");
                }
                if (list_input_enabled.get(i).getInParam_Mapped_ID().contentEquals(CONTROL_TYPE_GPS)) {
                    InputMap.put("Oparator", "");
                    InputMap.put("ColumnType", "GPS");
                    if (list_input_enabled.get(i).getInParam_near_by_distance() != null) {
                        InputMap.put("NearBy", list_input_enabled.get(i).getInParam_near_by_distance());
                    } else {
                        InputMap.put("NearBy", "");
                    }
                    if (list_input_enabled.get(i).getInParam_near_by_records() != null) {
                        InputMap.put("NoOfRec", list_input_enabled.get(i).getInParam_near_by_records());
                    } else {
                        InputMap.put("NoOfRec", "");
                    }

                    InputMap.put("Value", "");

                    InputMap.put("CurrentGPS", inparam_current_location);
                    gpsContainInInParams = true;
                } else {
                    InputMap.put("NearBy", "");
                    InputMap.put("NoOfRec", "");
                    InputMap.put("CurrentGPS", "");

                }

                if (list_input_enabled.get(i).getInParam_Optional().equalsIgnoreCase("true")) {
                    if (inValue != null && !inValue.trim().equals("")) {
                        jarr.put(InputMap);
                    }
                } else {
                    jarr.put(InputMap);
                }
            }
            //remove last item Condition to empty
            if (jarr.length() > 0) {
                jarr.getJSONObject(jarr.length() - 1).put("Condition", "");
            }

            for (int i = 0; i < list_input.size(); i++) {
                Outputcolumns = Outputcolumns + "," + list_input.get(i).getInParam_Name();
                Outputcolumns_copy = Outputcolumns_copy + ",[" + list_input.get(i).getInParam_Name() + "]";
            }
            if (gpsContainInInParams) {
                Outputcolumns = Outputcolumns + "," + "DistanceInKM";
            }
            Outputcolumns = Outputcolumns.substring(Outputcolumns.indexOf(",") + 1);
            Outputcolumns_copy = Outputcolumns_copy.substring(Outputcolumns_copy.indexOf(",") + 1);

            System.out.println("Outputcolumns=====" + Outputcolumns);

            JSONObject dataJson = new JSONObject();
//            dataJson.put("OrgId", sessionManager.getOrgIdFromSession());
//            dataJson.put("PageName", ActionBean.getSelect_FormName());
//            dataJson.put("PageName", Select_FormName);
            dataJson.put("PageName", Select_TableName);
            dataJson.put("InputKeys", jarr);
            dataJson.put("OrderByColumns", ActionObj.getOrderByColumns());
            dataJson.put("Order", ActionObj.getOrder());
            dataJson.put("OutPut", Outputcolumns_copy);
            dataJson.put("LazyLoading", "False");
            if (ActionObj.getResult_DisplayType() != null &&
                    !ActionObj.getResult_DisplayType().contentEquals("") &&
                    ActionObj.getResult_DisplayType().contentEquals("DataViewer")) {
                String dataViewerName = ActionObj.getSelectedSubForm();
                DataViewer dataViewer = (DataViewer) List_ControlClassObjects.get(dataViewerName);
                ControlObject controlObject = dataViewer.controlObject;
                if (controlObject.isLazyLoadingEnabled()) {
                    dataJson.put("LazyLoading", "True");
                    dataJson.put("Threshold", controlObject.getThreshold());
                    int min = controlObject.getCurrentMaxPosition() + 1;
                    int max = min + Integer.parseInt(controlObject.getThreshold()) - 1;
                    dataJson.put("Range", min + "-" + max);
                    dataJson.put("LazyOrderKey", ActionObj.getOrderByColumns());
                    if (ActionObj.getOrderByColumns().contentEquals("")) {
                        dataJson.put("LazyOrderKey", "SELECT NULL");
                    }
                    dataViewer.setLazyLoadingObject(dataJson);
                    controlObject.setCurrentMaxPosition(max);
                    dataViewer.setActionBean(ActionObj);
                }
            }
            dataJson.put("DBConnectionSource", ActionObj.getConnectionSource());
            dataJson.put("DBConnection", ActionObj.getConnectionId());

            Map<String, String> maindata = new HashMap<>();
            maindata.put("Data", dataJson.toString());

            System.out.println("dataJson==" + dataJson);
            ImproveHelper.improveLog(TAG, "CallFormFields_Inputs", new Gson().toJson(jarr));
            ImproveHelper.improveLog(TAG, "CallFormFields_Outputs", new Gson().toJson(Outputcolumns_copy));

            ImproveHelper.improveLog(TAG, "CallFormFields ", new Gson().toJson(maindata));
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(dataJson.toString());
            new CallFormFieldsData(context, sessionManager.getOrgIdFromSession(), Select_TableName, jarr, jo, ActionObj.getSelect_FormAppMode(), ActionObj, new CallFormFieldsData.OnResponse() {

                @Override
                public void response(String response) {
                    Log.e("Result GetData: ", "" + response);
                    System.out.println("Get Data:"+response);
                    processGetDataResponse(response);
                }

                @Override
                public void failure(String msg) {
                    failureCase(msg);
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "CallFormFields", e);
            failureCase(e.getMessage());
        }
    }

    private void processGetDataResponse(String response) {
        if (response != null && !response.equalsIgnoreCase("failed")) {
            if (ActionObj.getSelect_FormName() == null) {
                ActionObj.setSelect_FormName(ActionObj.getSelect_FormTableName());
            }
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = new JSONObject(response);
                System.out.println("Get Data jsonObject:"+jsonObject);
                if (jsonObject.get("Status").equals("200")) {
                    if (ActionObj.getActionId() != null & !ActionObj.getActionId().equals("")) {
                        //insert into realm with formName as TableName :ActionObj.getActionId()
                        if (RealmDBHelper.existTable(context, ActionObj.getActionId())) {
                            RealmDBHelper.deleteTable(context, ActionObj.getActionId());
                        }
                        RealmDBHelper.createTableFromAction(context, ActionObj.getActionId(), response);
                        RealmDBHelper.insertFromAction(context, ActionObj.getActionId(), response);
                       // RealmDBHelper.createTableWithInsertFromAction(context, ActionObj.getActionId(), response);
                        if (ActionObj.getSaveOfflineType().equalsIgnoreCase("Response")) {
                            RealmDBHelper.updateSaveOfflineTable(context, strAppName, ActionObj.getActionId(), ActionObj.getActionType(), "Offline");
                        } else {
                            RealmDBHelper.updateSaveOfflineTable(context, strAppName, ActionObj.getActionId(), ActionObj.getActionType(), "Online");
                        }
                        setGetDataStatusInGlobal(response, ActionObj);
                    }
                    System.out.println("Get Data Realm Done");
                    String Outputcolumns = "", Outputcolumns_copy = "";
                    for (int i = 0; i < ActionObj.getList_Form_OutParams().size(); i++) {
                        if (ActionObj.getList_Form_OutParams().get(i).getOutParam_Mapped_ID() != null && !ActionObj.getList_Form_OutParams().get(i).getOutParam_Mapped_ID().equals("")) {
                            Outputcolumns = Outputcolumns + "," + ActionObj.getList_Form_OutParams().get(i).getOutParam_Mapped_ID();
                            Outputcolumns_copy = Outputcolumns_copy + ",[" + ActionObj.getList_Form_OutParams().get(i).getOutParam_Mapped_ID() + "]";
                        }else if(ActionObj.getList_Form_OutParams().get(i).getList_OutParam_Languages()!=null && ActionObj.getList_Form_OutParams().get(i).getList_OutParam_Languages().size()>0){
//                            String appLanguage = ImproveHelper.getLocale(context);
                            List<LanguageMapping> languageMappings = ActionObj.getList_Form_OutParams().get(i).getList_OutParam_Languages();
                            for (int j = 0; j <languageMappings.size() ; j++) {
                                Outputcolumns = Outputcolumns+","+languageMappings.get(j).getOutParam_Lang_Mapped();
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
                    System.out.println("Get Data: Data setting to control");
                    setDataToSingleOrMultpileControls(OutputData);
                } else {
                    setGetDataStatusInGlobal(response, ActionObj);
                    failureCase(jsonObject.get("Message").toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
                failureCase(e.getMessage());
            }
        } else {
            setGetDataStatusInGlobal(response,ActionObj);
            failureCase("Response: " + response);
        }
    }

    private void setDataToSingleOrMultpileControls(LinkedHashMap<String, List<String>> OutputData) {
        closeProgressDialog();
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
                    if (ActionObj.getSaveOfflineType().equalsIgnoreCase("Response")) {
                        SessionManager sessionManager = new SessionManager(context);
                        sessionManager.createCallAPIResponseData(strAppName + "_" + ActionObj.getActionId(), OutputData);
                    }

                    System.out.println("ActionObj.getSelect_FormName()==" + ActionObj.getSelect_FormName());
                    //setFormParamstoGlobalObjects(ActionObj.getSelect_FormName(), OutputData);

                    if (ActionObj.getResult_NoOfRecords().equalsIgnoreCase("Single")) {

                        for (int i = 0; i < list_Form_OutParams.size(); i++) {
                            if (!list_Form_OutParams.get(i).isOutParam_Delete()) {
                                ControlUtils.setSingleValuetoControlFromCallAPIORGetData(context, OutputData, list_Form_OutParams.get(i),
                                        dataCollectionObject, List_ControlClassObjects);
                            }
                        }
                        successMessageIsEnable("");
                    } else if (ActionObj.getResult_NoOfRecords().equalsIgnoreCase("none")) {
                        successMessageIsEnable("Success");
                    } else {
                        System.out.println("ActionObj.getResult_DisplayType()==" + ActionObj.getResult_DisplayType());
                        String SelectedSubForm = ActionObj.getSelectedSubForm();
                        if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_SUBFORM)) {
                            SubformView subform = (SubformView) List_ControlClassObjects.get(SelectedSubForm);
                            subform.saveNewRowData(OutputData, list_Form_OutParams);
                            subform.loadSubFormData(ActionObj, MainActivity.getInstance().subFormMapControls, MainActivity.getInstance().subFormMappedValues);
                            subform.setCallbackListener(new Callback() {
                                @Override
                                public void onSuccess(Object result) {
                                    successMessageIsEnable((String) result);
                                }

                                @Override
                                public void onFailure(Throwable throwable) {
                                    successMessageIsEnable(throwable.toString());
                                }
                            });
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_GRIDFORM)) {
                            GridControl gridForm = (GridControl) List_ControlClassObjects.get(SelectedSubForm);
                            gridForm.saveNewRowData(OutputData, list_Form_OutParams);
                            gridForm.loadGridControlData(ActionObj, MainActivity.getInstance().subFormMapControls, MainActivity.getInstance().subFormMappedValues);
                            gridForm.setCallbackListener(new Callback() {
                                @Override
                                public void onSuccess(Object result) {
                                    successMessageIsEnable((String) result);
                                }

                                @Override
                                public void onFailure(Throwable throwable) {
                                    successMessageIsEnable(throwable.toString());
                                }
                            });
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_SECTION)) {
                            SectionControl sectionControl = (SectionControl) List_ControlClassObjects.get(SelectedSubForm);
                            ActionUitls.SetValuetoMultiControlInCallAPIFormUsedbyControlObject(context, 0, OutputData, list_Form_OutParams, sectionControl.controlObject.getSubFormControlList(), List_ControlClassObjects);
                            successMessageIsEnable("Success");
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_DATAVIEWER)) {
                            DataViewer dataViewer = (DataViewer) List_ControlClassObjects.get(SelectedSubForm);
                            dataViewer.setOutputData(OutputData);
                            dataViewer.setActionBean(ActionObj);
                            dataViewer.setResultFromGetDataManageDataAPIData();
                            successMessageIsEnable("Success");
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_LISTVIEW)) {
                            String cName = ActionObj.getResult_ListView_FilterMappedControl();
                            String mapControl = ActionObj.getResult_ListView_FilterItem();
                            String mapControlID = ActionObj.getResult_ListView_FilterItemID();
                            List<String> MappedValues = OutputData.get(mapControl.toLowerCase());
                            List<String> MappedValuesIDS = OutputData.get(mapControlID.toLowerCase());
                            Log.e("Result: ", "" + MappedValues.toString());
                            ActionUitls.SetMultipleValuesbyControlID(context, cName, MappedValues, MappedValuesIDS, dataCollectionObject, List_ControlClassObjects);
                            successMessageIsEnable("Success");

                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_IMAGEVIEW)) {
                            String cName = ActionObj.getResult_ListView_FilterMappedControl();
                            String mapControl = ActionObj.getResult_ListView_FilterItem();
                            List<String> MappedValues = OutputData.get(mapControl);
                            ActionUitls.SetMultipleValuestoImageControl(context, cName, MappedValues, dataCollectionObject, List_ControlClassObjects);
                            successMessageIsEnable("Success");

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

                                boolean replace;
                                if(ActionObj.getSv_Multiple_multi_assignType()!=null) {
                                    replace = ActionObj.getSv_Multiple_multi_assignType().equalsIgnoreCase("Replace");
                                }else{
                                    replace= ActionObj.getMulti_DataType().equalsIgnoreCase("Replace");
                                }

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
                            successMessageIsEnable("Success");
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_CALENDARVIEW)) {
                            CalendarEventControl CalendarEventControl = (CalendarEventControl) List_ControlClassObjects.get(SelectedSubForm);

                            String MappedValue_Date = list_Form_OutParams.get(0).getOutParam_Mapped_ID();
                            String MappedValue_Message = list_Form_OutParams.get(1).getOutParam_Mapped_ID();
                            List<String> MappedValues_Date = OutputData.get(MappedValue_Date.toLowerCase());
                            List<String> MappedValues_Message = OutputData.get(MappedValue_Message.toLowerCase());

                            for (int x = 0; x < MappedValues_Date.size(); x++) {
                                CalendarEventControl.AddDateDynamically("Single", MappedValues_Date.get(x), MappedValues_Message.get(x));
                            }
                            successMessageIsEnable("Success");
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_CHART)) {
                            ChartControl chartControl = (ChartControl) List_ControlClassObjects.get(SelectedSubForm);
                            chartControl.setChartData(ActionObj, OutputData);
                            successMessageIsEnable("Success");
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
                            successMessageIsEnable("Success");
                        }
                    }


                }
                else {
                    try {
                        JSONObject jobj = new JSONObject();
                        jobj.put("CallFormFields_Execution", "Success");
                        jobj.put("OutputData_Size", OutputData.size());
                        ImproveHelper.Controlflow("CallFormFields Execute", "Action", "CallFormFields", jobj.toString());
                    } catch (JSONException e) {
                        ImproveHelper.improveException(context, TAG, "CallFormFields Execute", e);
                    }
                    //Clear object when no Data returns
                    //LinkedHashMap<String, List<String>> NoData = new LinkedHashMap<>();
                    //setFormParamstoGlobalObjects(ActionObj.getSelect_FormName(), NoData);
                    //nk realm: clear data
                    if (RealmDBHelper.existTable(context, ActionObj.getSelect_FormName())) {
                        RealmDBHelper.deleteTable(context, ActionObj.getSelect_FormName());
                    }
                    List<API_OutputParam_Bean> list_Form_OutParams = ActionObj.getList_Form_OutParams();
                    if (ActionObj.getResult_NoOfRecords().equalsIgnoreCase("None")) {

                    } else if (ActionObj.getResult_NoOfRecords().equalsIgnoreCase("Single")) {
                        for (int i = 0; i < list_Form_OutParams.size(); i++) {
                            if (!list_Form_OutParams.get(i).isOutParam_Delete()) {
                                ActionUitls.SetNoDatatoControlForCallAPIorForm(context, list_Form_OutParams.get(i),
                                        dataCollectionObject, List_ControlClassObjects);
                            }
                        }
                    } else {
                        String SelectedSubForm = ActionObj.getSelectedSubForm();
                        if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_SUBFORM)) {
                            SubformView subform = (SubformView) List_ControlClassObjects.get(SelectedSubForm);
                            if (!ActionObj.getMulti_DataType().equalsIgnoreCase("append")) {
                                subform.setiMinRows(0);
                                subform.setiMaxRows(Integer.parseInt(subform.controlObject.getMaximumRows()));
                                View view = subform.getSubFormView();
                                LinearLayout ll_MainSubFormContainer = view.findViewById(R.id.ll_MainSubFormContainer);
                                ll_MainSubFormContainer.removeAllViews();
                                List<LinkedHashMap<String, Object>> subform_List_ControlClassObjects = subform.getList_ControlClassObjects();
                                subform_List_ControlClassObjects.removeAll(subform_List_ControlClassObjects);
                            }
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_GRIDFORM)) {
                            GridControl subform = (GridControl) List_ControlClassObjects.get(SelectedSubForm);
                            View view = subform.getSubFormView();

                            if (!ActionObj.getMulti_DataType().equalsIgnoreCase("append")) {
                                TableLayout ll_grid_view = view.findViewById(R.id.ll_grid_view);

                                subform.SubFormTAG = 0;
                                List<LinkedHashMap<String, Object>> subform_List_ControlClassObjects = subform.getList_ControlClassObjects();

                                subform.setiMinRows(0);
                                subform.setiMaxRows(Integer.parseInt(subform.controlObject.getMaximumRows()));
                                ll_grid_view.removeAllViews();
                                subform_List_ControlClassObjects.removeAll(subform_List_ControlClassObjects);
                            }
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_DATAVIEWER)) {
                            DataViewer DataViewer = (DataViewer) List_ControlClassObjects.get(SelectedSubForm);
                            DataViewer.setOutputData(OutputData);
                            List<DataViewerModelClass> dataViewerModelClassList = new ArrayList<>();
                            if (ActionObj.getMulti_DataType().equalsIgnoreCase("append")) {
                                DataViewerModelClass dmv = new DataViewerModelClass();
                                dataViewerModelClassList.add(dmv);
                            }
                            DataViewer.SetDataViewerData(dataViewerModelClassList);

                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_LISTVIEW)) {
                            String cName = ActionObj.getResult_ListView_FilterMappedControl();
                            ActionUitls.SetMultipleValuesbyControlID(context, cName, null, null, dataCollectionObject, List_ControlClassObjects);
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_MAPVIEW)) {
                            MapControl MapControl = (MapControl) List_ControlClassObjects.get(SelectedSubForm);
                            if (MapControl.getGoogleMap() != null) {
                                MapControl.getGoogleMap().clear();
                            }
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_CALENDARVIEW)) {

                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_CHART)) {
                        } else if (ActionObj.getResult_DisplayType().equalsIgnoreCase(AppConstants.DISPLAY_TYPE_DATA_TABLE)) {
                            DataTableControl dataTableControl = (DataTableControl) List_ControlClassObjects.get(SelectedSubForm);
                            dataTableControl.ClearData();
                        }
                    }

                    if (ActionObj.isMessage_SuccessNoRecordsIsEnable()) {
                        ExpressionMainHelper ehelper = new ExpressionMainHelper();
                        String Message = ehelper.ExpressionHelper(context, ActionObj.getMessage_SuccessNoRecords());
                        if (ActionObj.getMessage_SuccessNoRecordsDisplayType().equalsIgnoreCase("2")) {
                            MainActivity.getInstance().ShowMessageDialogWithOk(context, Message, 2);
                        } else {
//                                    Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        ImproveHelper.showToast(context, "No Records Found...");
                    }
                    callbackListener.onFailure(new Throwable("OutputData : " + OutputData));
                }
            }
        });

    }

    private void successMessageIsEnable(String msg) {
        if (ActionObj.isSuccessMessageIsEnable()) {
            ExpressionMainHelper ehelper = new ExpressionMainHelper();
            String Message = ehelper.ExpressionHelper(context, ActionObj.getMessage_Success());
            if (ActionObj.getMessage_SuccessDisplayType().equalsIgnoreCase("2")) {
                MainActivity.getInstance().ShowMessageDialogWithOk(context, Message, 2);
            } else {
                ImproveHelper.showToastRunOnUI( MainActivity.getInstance(),Message);
            }
        } else if (!ActionObj.isSuccessMessageIsEnable()) {

        } else {
            //ImproveHelper.showToast(getApplicationContext(), "Data Recevied...");
        }

        callbackListener.onSuccess(msg);
    }

    private void setGetDataStatusInGlobal(String response, ActionWithoutCondition_Bean ActionBean) {
        try {
            JSONObject jObj = new JSONObject(response);
            LinkedHashMap<String, LinkedHashMap<String, String>> tempGetData = new LinkedHashMap<>();
            if (AppConstants.GlobalObjects.getGetData_ResponseHashMap() != null) {
                tempGetData = AppConstants.GlobalObjects.getGetData_ResponseHashMap();
            }
            LinkedHashMap<String, String> temp_response = tempGetData.get(ActionBean.getActionId());
            if (temp_response == null) {
                temp_response = new LinkedHashMap<>();
            }
            //Status Code :Status
            temp_response.put("__Status Code".toLowerCase(), jObj.has("Status") ? jObj.getString("Status") : "");
            //Message :Message
            temp_response.put("__Message".toLowerCase(), jObj.has("Message") ? jObj.getString("Message") : "");

            if (jObj.has("Details")) {
                JSONObject detailsObj = jObj.getJSONObject("Details");
                //Detailed Message:Detailedmessage
                temp_response.put("__Detailed Message".toLowerCase(), detailsObj.has("Detailedmessage") ? detailsObj.getString("Detailedmessage") : "");
                //Records Count:Rowcount
                temp_response.put("__Records Count".toLowerCase(), detailsObj.has("Rowcount") ? detailsObj.getString("Rowcount") : "");
            } else {
                //Detailed Message:Detailedmessage
                temp_response.put("__Detailed Message".toLowerCase(), "");
                //Records Count:Rowcount
                temp_response.put("__Records Count".toLowerCase(), "");
            }
            tempGetData.put(ActionBean.getActionId(), temp_response);
            AppConstants.GlobalObjects.setGetData_ResponseHashMap(tempGetData);
            System.out.println("Response:" + AppConstants.GlobalObjects.getGetData_ResponseHashMap());
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setGetDataStatusInGlobal", e);
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
       ProgressDialog pd;
       private void closeProgressDialog() {
           try {
               if (pd != null)
                   pd.dismiss();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
    private class GetDataStart extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(context,context.getResources().getString(
                    R.string.please_wait_serverhit));
        }

        @Override
        protected synchronized Void doInBackground(Void... voids) {
            GetData.this.execute();
            return null;
        }


        @Override
        protected synchronized void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

    }
}
