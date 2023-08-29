package com.bhargo.user.controls.data_controls;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.bhargo.user.Expression.ExpressionMainHelper;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.New_DataControl_Bean;
import com.bhargo.user.Java_Beans.Param;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.DataControls;
import com.bhargo.user.pojos.PostSubLocationsModel;
import com.bhargo.user.pojos.PostsMasterModel;
import com.bhargo.user.pojos.SublocationsModel;
import com.bhargo.user.pojos.UserPostDetails;
import com.bhargo.user.realm.RealmDBHelper;
import com.bhargo.user.realm.RealmTables;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.PropertiesNames;
import com.bhargo.user.utils.SessionManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmResults;
import nk.mobileapps.spinnerlib.SearchableSpinner;
import nk.mobileapps.spinnerlib.SpinnerData;

//import com.bhargo.user.MainActivity_Updated;


public class DataControl {

    private static final String TAG = "DataControl";
    private final int DropDownTAG = 0;
    public ImproveHelper improveHelper;
    Context context;
    ControlObject controlObject;
   public SearchableSpinner searchableSpinner;
    List<String> ddItems;
    CustomEditText ce_other;
    List<New_DataControl_Bean> dataControl_beans;
    String locationStructure;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    SessionManager sessionManager;
    //ControlObject controlObject_cur;
    //String DataKeyName = "";
    ControlObject dependent_ControlObj;
    String dependent_ControlNameKey, dependent_DataControlName;
    View view;
    private LinearLayout linearLayout, ll_tap_text;
    private CustomTextView tv_tapTextType;
    private CustomTextView tv_displayName, tv_hint, ct_alertDataControl,ct_showText;
    private ImageView iv_mandatory;
    private GetServices getServices;

    public DataControl(Context context, ControlObject controlObject, String locationStructure,
                       boolean SubformFlag, int SubformPos, String SubformName) {
        this.context = context;
        this.controlObject = controlObject;
        this.locationStructure = locationStructure;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;

        improveHelper = new ImproveHelper(context);
        initView();

    }

    public ControlObject getControlObject() {
        return controlObject;
    }

    public void initView() {

        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            ddItems = new ArrayList<>();
            dataControl_beans = new ArrayList<>();
            addDataControlStrip(context);

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initViews", e);
        }
    }

    public LinearLayout getDataControl() {

        return linearLayout;
    }

    public SearchableSpinner getSpinner() {

        return searchableSpinner;
    }

    public void addDataControlStrip(final Context context) {
        try {
            final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (controlObject != null && controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("5")) {

                view = lInflater.inflate(R.layout.layout_drop_down_left_right, null);
//                  view = lInflater.inflate(R.layout.layout_drop_down_default, null);
            } else {
//                view = lInflater.inflate(R.layout.control_datacontrol, null);
                view = lInflater.inflate(R.layout.layout_drop_down_default, null);
            }

            view.setTag(DropDownTAG);

            ll_tap_text = view.findViewById(R.id.ll_tap_text);
            final LinearLayout ll_spinner = view.findViewById(R.id.ll_spinner);
//        tv_tapTextType = view.findViewById(R.id.tv_tapTextType);
            searchableSpinner = view.findViewById(R.id.searchableSpinner_main);
            searchableSpinner.setTag(controlObject.getControlName());
            tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            ce_other = view.findViewById(R.id.ce_otherchoice);
            ce_other.setTag(controlObject.getControlName());
            ct_alertDataControl = view.findViewById(R.id.ct_alertTypeText);
            ct_showText = view.findViewById(R.id.ct_showText);
            ce_other.setTag(controlObject.getControlName());
            searchableSpinner.setItems(new ArrayList<SpinnerData>());//State state_131
            AppConstants.SEARCHABLE_SPINNER_LIST.put(controlObject.getControlName() + SubformPos, searchableSpinner);
            AppConstants.DATACONTROL_CO_LIST.put(controlObject.getControlName() + SubformPos, controlObject);
            if (AppConstants.DCN_CN_List.containsKey(controlObject.getDataControlName().toLowerCase())) {
                List<String> dcn_list = AppConstants.DCN_CN_List.get(controlObject.getDataControlName().toLowerCase());
                if (!dcn_list.contains(controlObject.getControlName())) {
                    dcn_list.add(controlObject.getControlName());
                    AppConstants.DCN_CN_List.put(controlObject.getDataControlName().toLowerCase(), dcn_list);
                }
            }else{
                List<String> dcn_list = new ArrayList<>();
                dcn_list.add(controlObject.getControlName());
                AppConstants.DCN_CN_List.put(controlObject.getDataControlName().toLowerCase(), dcn_list);
            }
            if (controlObject.getDataControlStatus().equalsIgnoreCase(AppConstants.SP_DATA_CONTROLS_STATUS_DEPENDENT)) {
                Log.d(TAG, "dc_name: " + controlObject.getControlName());//DependentControl state: district,district1
                if (AppConstants.DC_DEPENDENT_LIST.containsKey(controlObject.getDependentControl().toLowerCase())) {
                    Log.d(TAG, "dc_name_dp: " + controlObject.getDependentControl());
                    List<String> dc_list = AppConstants.DC_DEPENDENT_LIST.get(controlObject.getDependentControl().toLowerCase());
                    Log.d(TAG, "addDropDownStrip: " + dc_list);
                    if (!dc_list.contains(controlObject.getControlName())) {
                        dc_list.add(controlObject.getControlName());
                        AppConstants.DC_DEPENDENT_LIST.put(controlObject.getDependentControl().toLowerCase(), dc_list);
                    }
                } else {
                    Log.d(TAG, "dc_name_dp_f: " + controlObject.getDependentControl());
                    List<String> dlist = new ArrayList<>();
                    dlist.add(controlObject.getControlName());
                    AppConstants.DC_DEPENDENT_LIST.put(controlObject.getDependentControl().toLowerCase(), dlist);
                }
            }

            searchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    RaiseListner(parent);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            setControlValues();
            linearLayout.addView(view);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addDataControlStrip", e);
        }

    }

    public void RaiseListner(View parent) {
        try {
//            ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.control_active_background));
            System.out.println("controlObject===" + controlObject.getControlName());
            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                if (AppConstants.EventCallsFrom == 1) {
                    AppConstants.EventFrom_subformOrNot = SubformFlag;
                    if (SubformFlag) {
                        AppConstants.SF_Container_position = SubformPos;
                        AppConstants.Current_ClickorChangeTagName = SubformName;
                    }
                    AppConstants.GlobalObjects.setCurrent_GPS("");
                    if(parent!=null){
                        if (AppConstants.IS_MULTI_FORM) {
                            ((MainActivity) context).ChangeEvent(parent);
                        } else {
                            ((MainActivity) context).ChangeEvent(parent);
                        }
                    }

                }
            }
            String selectedItem = searchableSpinner.getSelectedName();
            String selected_id = searchableSpinner.getSelectedId();
            Log.d("selected_id", selected_id);
            if(searchableSpinner.isSelected()) {
                controlObject.setItemSelected(searchableSpinner.isSelected());
                controlObject.setText(getSelectedDropDownItemName());
                controlObject.setControlValue(getSelectedDropDownItemId());
            }


            AppConstants.DC_SELCTED_ID.put(controlObject.getControlName(), selected_id);

            if (selectedItem.equalsIgnoreCase(context.getString(R.string.other))) {
                ce_other.setVisibility(View.VISIBLE);
            } else if ((controlObject.isEnablePostLocationBinding() ||
                    controlObject.isEnableUserControlBinding()) &&
                    ce_other.getText().toString().length() > 0) {
                ce_other.setVisibility(View.VISIBLE);
            } else {
                ce_other.setText("");
                ce_other.setVisibility(View.GONE);
            }
            ImproveDataBase improveDataBase = new ImproveDataBase(context);
            sessionManager = new SessionManager(context);

            List<String> dependent_sps = AppConstants.DC_DEPENDENT_LIST.get(controlObject.getControlName().toLowerCase());
            if (dependent_sps != null && dependent_sps.size() > 0) {
                for (int dp_count = 0; dp_count < dependent_sps.size(); dp_count++) {
                    dependent_ControlNameKey = dependent_sps.get(dp_count);
                    dependent_ControlObj = AppConstants.DATACONTROL_CO_LIST.get(dependent_sps.get(dp_count) + SubformPos);
                    dependent_DataControlName = dependent_ControlObj.getDataControlName();
                    DataControls dataControls = improveDataBase.getDataControlByUsingName(dependent_DataControlName, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
                   /* if (!dataControls.getDataControlType().equalsIgnoreCase("Partial")) {*/
                        if (!ImproveHelper.getLocale(context).equalsIgnoreCase("") && !ImproveHelper.getLocale(context).equalsIgnoreCase("en")) {
                            dependent_DataControlName = dependent_DataControlName + "_" + ImproveHelper.getLocale(context);
                        }
                        SearchableSpinner dependent_SearchableSpinner = AppConstants.SEARCHABLE_SPINNER_LIST.get(dependent_sps.get(dp_count) + SubformPos);
                        //nk realm:List<New_DataControl_Bean> dataControl_bean = AppConstants.NEW_DATACONTROL_BEAN_LIST.get(DataKeyName);
                        if (!dependent_ControlObj.isEnableUserControlBinding() && !dependent_ControlObj.isEnablePostLocationBinding()) {
                            //nk realm:setDependentItems(controlObject_cur, selected_searchableSpinner, dataControl_bean, controlObject.getControlName());
                            setDependentItems(dependent_ControlObj, dependent_SearchableSpinner, controlObject.getDataControlName(),controlObject.getControlName());
                            refreshDependentDatControls(dependent_sps.get(dp_count));
                        } else if (dependent_SearchableSpinner.isEnabled()) {
                            //nk realm:setDependentItems(controlObject_cur, selected_searchableSpinner, dataControl_bean, controlObject.getControlName());
                            setDependentItems(dependent_ControlObj, dependent_SearchableSpinner,  controlObject.getDataControlName(), controlObject.getControlName());
                            refreshDependentDatControls(dependent_sps.get(dp_count));
                        }
                    /*} else {
//                        improveHelper = new ImproveHelper(context);
                        improveHelper.showProgressDialog(context.getResources().getString(R.string.loading_apps));
                        if (isNetworkStatusAvialable(context)) {
                            getServices = RetrofitUtils.getUserService();

                            Map<String, String> data = new HashMap<>();
                            data.put("OrgId", AppConstants.GlobalObjects.getOrg_Name());
                            data.put("UserId", GlobalObjects.getUser_ID());
                            data.put("DownloadDataControlName", dependent_DataControlName);
                            data.put("DataControlID", selected_id);

                            Call<DataControlsAndApis> call = getServices.iGetDataControlsPartial(data);
                            call.enqueue(new Callback<DataControlsAndApis>() {
                                @Override
                                public void onResponse(Call<DataControlsAndApis> call, Response<DataControlsAndApis> response) {
                                    DataControlsAndApis DataControlsAndApis = response.body();
                                    if(DataControlsAndApis!=null){
                                        List<DataControls> list_DataControls = DataControlsAndApis.getDataControls();
                                        DataControls dataControls = list_DataControls.get(0);
                                        System.out.println("dataControls==" + dataControls);
                                        System.out.println("Image Path==" + dataControls.getTextFilePath());
                                        startDownloadDataControl(dataControls, dataControls.getTextFilePath().trim(), "", 0, 0);
                                    }else{
                                        improveHelper.dismissProgressDialog();
                                    }

                                }

                                @Override
                                public void onFailure(Call<DataControlsAndApis> call, Throwable t) {
                                    improveHelper.dismissProgressDialog();
                                }
                            });

                        } else {
                            improveHelper.snackBarAlertFragment(context, linearLayout);
                            improveHelper.dismissProgressDialog();
                        }
                    }*/
                    if (dp_count == dependent_sps.size()) {
                        AppConstants.DataControl_Edit_Flag = false;
                    }

                }
            }

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "RaiseListner", e);
        }

    }

    private void startDownloadDataControl(DataControls dataControls, String filePath, String appName, int flag, int refreshFlag) {
        try {
            boolean resultDelete = false;
            String[] imgUrlSplit = filePath.split("/");
            String strSDCardUrl = null;


            if (flag == 1) {
                strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/";
            } else {
                strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/";
            }

            if (!isFileExists(strSDCardUrl, imgUrlSplit[imgUrlSplit.length - 1])) {
                DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(context, appName, strSDCardUrl, new DownloadFileListener() {
                    @Override
                    public void onSuccess(File file) {
                        String line = ImproveHelper.readTextFileFromSD(context, file.getAbsolutePath());
                        System.out.println("===realm: String===" + line);
                        System.out.println("===realm: status===" + dataControls.getControlName());
                        System.out.println("===realm: status===" + dataControls.getControlStatus());

                        if (dataControls.getControlStatus().equalsIgnoreCase("Update")) {
                            String refRespDCUpdate = dataControls.getControlName();
                            RealmDBHelper.deleteTable(context, dataControls.getControlName());
                            RealmDBHelper.createTableAndInsertDataFromDataControl(context, line);
                        } else if (dataControls.getControlStatus().equalsIgnoreCase("Deleted")) {
                            String refRespDCDelete = dataControls.getControlName();
                            RealmDBHelper.deleteTable(context, dataControls.getControlName());
                        } else if (dataControls.getControlStatus().equalsIgnoreCase("New")) { // newly added files
                            RealmDBHelper.createTableAndInsertDataFromDataControl(context, line);
                        }
                        // loadItems(controlObject_cur);
                        LoadPartialControlData();
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        ImproveHelper.showToast(context, errorMessage);
                    }
                });
                fromURLTask.execute(filePath);
            } else {
//            if (resultDelete) {
                DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(context, appName, strSDCardUrl, new DownloadFileListener() {
                    @Override
                    public void onSuccess(File file) {
                        String line = ImproveHelper.readTextFileFromSD(context, file.getAbsolutePath());
                        System.out.println("===realm: String===" + line);
                        System.out.println("===realm: status===" + dataControls.getControlName());
                        System.out.println("===realm: status===" + dataControls.getControlStatus());

                        if (dataControls.getControlStatus().equalsIgnoreCase("Update")) {
                            String refRespDCUpdate = dataControls.getControlName();
                            RealmDBHelper.deleteTable(context, dataControls.getControlName());
                            RealmDBHelper.createTableAndInsertDataFromDataControl(context, line);
                        } else if (dataControls.getControlStatus().equalsIgnoreCase("Deleted")) {
                            String refRespDCDelete = dataControls.getControlName();
                            RealmDBHelper.deleteTable(context, dataControls.getControlName());
                        } else if (dataControls.getControlStatus().equalsIgnoreCase("New")) { // newly added files
                            RealmDBHelper.createTableAndInsertDataFromDataControl(context, line);
                        }
                        //loadItems(controlObject_cur);
                        LoadPartialControlData();
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        ImproveHelper.showToast(context, errorMessage);
                    }
                });
                fromURLTask.execute(filePath);
//            }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "startDownloadDataControl", e);
        }

    }

    private boolean isFileExists(String sdcardUrl, String filename) {
//            File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
//            return folder1.exists();
        boolean isFileExists = false;
        try {
            File cDir = context.getExternalFilesDir(sdcardUrl);
            File appSpecificExternalDir = new File(cDir.getAbsolutePath(), filename);
            isFileExists = appSpecificExternalDir.exists();
            Log.d(TAG, "FileExitsDir: " + appSpecificExternalDir);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "isFileExists", e);
        }
        return isFileExists;
    }

    private void refreshDependentDatControls(String s) {
        try {
            List<String> dependent_sps = AppConstants.DC_DEPENDENT_LIST.get(s.toLowerCase());
            if (dependent_sps != null && dependent_sps.size() > 0) {
                for (int dp_count = 0; dp_count < dependent_sps.size(); dp_count++) {
                    SearchableSpinner selected_searchableSpinner = AppConstants.SEARCHABLE_SPINNER_LIST.get(dependent_sps.get(dp_count) + SubformPos);
                    ControlObject selected_controlObj = AppConstants.DATACONTROL_CO_LIST.get(dependent_sps.get(dp_count) + SubformPos);
                    if (selected_searchableSpinner != null) {
                        selected_searchableSpinner.setItems(new ArrayList<SpinnerData>());
                        if (!selected_controlObj.isEnablePostLocationBinding()) {
                            refreshDependentDatControls(dependent_sps.get(dp_count));
                        }
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "refreshDependentDatControls", e);
        }
    }

    private void setControlValues() {
        try {
            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            if (controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                tv_hint.setText(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }
            if (controlObject.isNullAllowed()) {
                iv_mandatory.setVisibility(View.VISIBLE);
                Log.d("UniqueErrorMandatoryDD", controlObject.getMandatoryFieldError());
            } else {
                iv_mandatory.setVisibility(View.GONE);
            }
            if (controlObject.isInvisible()) {
                linearLayout.setVisibility(View.GONE);
               /* LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(0, 0);
                linearLayout.setLayoutParams(ll_params);*/
            } else {
                linearLayout.setVisibility(View.VISIBLE);
            }
            if(controlObject.isDisable()){
            improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),!controlObject.isDisable(),ll_tap_text,view);}
//        setItems();
            setDisplaySettings(context, tv_displayName, controlObject);
            loadItems_realm();

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    private void getRealmDataWithOutDependent() {
        dataControl_beans = new ArrayList<>();
        ImproveDataBase improveDataBase = new ImproveDataBase(context);
        DataControls DataControls = improveDataBase.getDataControls(controlObject.getDataControlName());
        //Get Keys
        DynamicRealm dynamicRealm=RealmDBHelper.getDynamicRealm();
        DynamicRealmObject keysData = dynamicRealm.where(RealmTables.ControlKeys.TABLE_NAME).equalTo(RealmTables.ControlKeys.ControlName, DataControls.getControlName()).findFirst();
        RealmResults<DynamicRealmObject> controlData = dynamicRealm.where(DataControls.getControlName()).findAll();
        if (keysData != null) {
            String ControlName = keysData.getString(RealmTables.ControlKeys.ControlName);
            String KeyID = keysData.getString(RealmTables.ControlKeys.KeyID);
            String KeyName = keysData.getString(RealmTables.ControlKeys.KeyName);
            /*if (!ImproveHelper.getLocale(context).equals("")&&!ImproveHelper.getLocale(context).equalsIgnoreCase("en")) {
                KeyName = KeyName + "_" + ImproveHelper.getLocale(context);
            }*/
            for (int i = 0; i < controlData.size(); i++) {
                DynamicRealmObject dro = controlData.get(i);
                New_DataControl_Bean dataControl_bean = new New_DataControl_Bean();
                dataControl_bean.setDc_id(dro.getString(KeyID));
                dataControl_bean.setDc_value(dro.getString(KeyName));
                dataControl_bean.setDc_name(ControlName);
                dataControl_bean.setDc_KeyId(KeyID);
                dataControl_bean.setDc_KeyName(KeyName);
                dataControl_beans.add(dataControl_bean);
            }
        }
        dynamicRealm.close();
    }

    private void loadItems_realm() {
        try {
            //nk realm
            /*Gson gson = new Gson();

            String jsonDataControlItemsGet = PrefManger.getSharedPreferencesString(context, controlObject.getControlName(), "");

            Type type = new TypeToken<List<New_DataControl_Bean>>() {
            }.getType();
            dataControl_beans = gson.fromJson(jsonDataControlItemsGet, type);

            String KeyName = controlObject.getControlName();
            if (!ImproveHelper.getLocale(context).equalsIgnoreCase("en")) {
                KeyName = KeyName + "_" + ImproveHelper.getLocale(context);
            }
            AppConstants.NEW_DATACONTROL_BEAN_LIST.put(KeyName, dataControl_beans);
*///nk realm
            if (!controlObject.isEnableUserControlBinding() && !controlObject.isEnablePostLocationBinding()) {

                if (controlObject.getDataControlStatus().equalsIgnoreCase(AppConstants.SP_DATA_CONTROLS_STATUS_INDEPENDENT)) {
                    //no dependent
                    //nk realm
                    getRealmDataWithOutDependent();
                    setItems();
                }

                if (controlObject.getDataControlStatus().equalsIgnoreCase(AppConstants.SP_DATA_CONTROLS_STATUS_DEPENDENT) &&
                        !controlObject.isEnableUserControlBinding() && !controlObject.isEnablePostLocationBinding()) {

                    if (controlObject.getDependentControl() != null && controlObject.getDependentControl().trim().length() != 0) {
                        ControlObject depControlObj = AppConstants.DATACONTROL_CO_LIST.get(controlObject.getDependentControl() + SubformPos);
                        if (!checkDependentControlAvailableorNot(controlObject.getDependentControl())) {
                            //nk realm:dataControl_beans = AppConstants.NEW_DATACONTROL_BEAN_LIST.get(controlObject.getControlName());
                            dataControl_beans = RealmDBHelper.getTableDataWithBean(context, controlObject.getDataControlName());// null;//nk pending AppConstants.NEW_DATACONTROL_BEAN_LIST.get(controlObject.getControlName());
                            setItems();
                        } else if (depControlObj != null && (depControlObj.isEnableUserControlBinding() || depControlObj.isEnablePostLocationBinding())) {
                            //nk realm:List<New_DataControl_Bean> dataControl_bean = AppConstants.NEW_DATACONTROL_BEAN_LIST.get(controlObject.getControlName());
                            //nk realm
                            //nk realm:searchableSpinner.setItems(loadDependentDataControlSpinnerData(dataControl_bean, controlObject.getDependentControl(), controlObject));
                            searchableSpinner.setItems(loadDependentDataControlSpinnerData(depControlObj.getDataControlName(), controlObject.getDependentControl(), controlObject));
                        }
                    }

                }

            } else if (controlObject.getControlName().equalsIgnoreCase("User")) {

                SpinnerData spinnerData = new SpinnerData();
                spinnerData.setName(AppConstants.GlobalObjects.getReporter_Name());
                spinnerData.setId(AppConstants.GlobalObjects.getReporter_ID());
                List<SpinnerData> spinnerDataList = new ArrayList<>();
                spinnerDataList.add(spinnerData);
                searchableSpinner.setItems(spinnerDataList);
                searchableSpinner.setItemID(AppConstants.GlobalObjects.getReporter_ID());

                ImproveHelper.setViewDisable(searchableSpinner, false);
            } else {
                String temp_id = "";
                String temp_name = "";
                List<PostSubLocationsModel> remainingdependingControls = new ArrayList<>();

//            String[] tempLocationStructure = locationStructure.split(",");
                if (controlObject.isEnableUserControlBinding()) {
                    List<SublocationsModel> Sublocations = AppConstants.GlobalObjects.getSublocations();
                    for (int i = 0; i < Sublocations.size(); i++) {
                        if (Sublocations.get(i).getControlName().equalsIgnoreCase(controlObject.getDataControlName())) {
                            temp_id = Sublocations.get(i).getValue();
                            temp_name = Sublocations.get(i).getText();
                            break;
                        }
                    }
                } else if (controlObject.isEnablePostLocationBinding()) {
                    SessionManager sessionManager = new SessionManager(context);
                    List<UserPostDetails> postDetails_ = sessionManager.getUserPostDetailsFromSession();
                    List<PostsMasterModel> postDetails = sessionManager.getUserPostDetails();
                    String postId = sessionManager.getPostsFromSession();
                    if (postDetails != null && postDetails.size() > 0) {
                        for (int i = 0; i < postDetails.size(); i++) {
                            PostsMasterModel userPostDetails = postDetails.get(i);
                            if (!postId.contentEquals("") && userPostDetails.getID().contentEquals(postId)) {
                                List<PostSubLocationsModel> Sublocations = userPostDetails.getPostSubLocations();
                                remainingdependingControls = Sublocations;
                                for (int j = 0; j < Sublocations.size(); j++) {
                                    if (Sublocations.get(j).getControlName().equalsIgnoreCase(controlObject.getDataControlName())) {
                                        temp_id = Sublocations.get(j).getValue();
                                        temp_name = Sublocations.get(j).getText();
                                        remainingdependingControls.remove(j);
                                        break;
                                    }
                                }
                            }

                        }
                    }
                }

                if (temp_name.trim().equals("") || temp_id.trim().equals("")) {
                    searchableSpinner.setItems(new ArrayList<>());
                } else {
                    SpinnerData spinnerData = new SpinnerData();
                    spinnerData.setName(temp_name);
                    spinnerData.setId(temp_id);
                    List<SpinnerData> spinnerDataList = new ArrayList<>();
                    spinnerDataList.add(spinnerData);
                    searchableSpinner.setItems(spinnerDataList);
                    searchableSpinner.setItemID(temp_id);
                }


                if (!temp_id.equalsIgnoreCase("")) {
                    ce_other.setText(temp_name);
                    ce_other.setEnabled(false);
                    ce_other.setVisibility(View.VISIBLE);
                    searchableSpinner.setVisibility(View.INVISIBLE);
                    LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(0, 0);
                    searchableSpinner.setLayoutParams(ll_params);
                    ImproveHelper.setViewDisable(searchableSpinner, false);
                }

                List<String> dependent_sps = AppConstants.DC_DEPENDENT_LIST.get(controlObject.getControlName());
                if (dependent_sps != null && dependent_sps.size() > 0) {
                    for (int dp_count = 0; dp_count < dependent_sps.size(); dp_count++) {
                        SearchableSpinner selected_searchableSpinner = AppConstants.SEARCHABLE_SPINNER_LIST.get(dependent_sps.get(dp_count) + SubformPos);
                        //nk realm: List<New_DataControl_Bean> dataControl_bean = AppConstants.NEW_DATACONTROL_BEAN_LIST.get(dependent_sps.get(dp_count));
                        ControlObject controlObject_cur = AppConstants.DATACONTROL_CO_LIST.get(dependent_sps.get(dp_count) + SubformPos);
                        //nk realm:setDependentItems(controlObject_cur, selected_searchableSpinner, dataControl_bean, controlObject.getControlName());
                        setDependentItems(controlObject_cur, selected_searchableSpinner, dependent_sps.get(dp_count), controlObject.getControlName());

                        refreshDependentDatControls(dependent_sps.get(dp_count));

                    }
                }

                if (controlObject.isEnablePostLocationBinding()) {
                    //set enablePostlocation binding for all depending control from low to high
                    for (int i = 0; i < remainingdependingControls.size(); i++) {
                        PostSubLocationsModel postSubLocationsModel = remainingdependingControls.get(i);
                        temp_id = postSubLocationsModel.getValue();
                        temp_name = postSubLocationsModel.getText();
                        if (!temp_name.trim().equals("") && !temp_id.trim().equals("")) {
                            String postDataControlName = postSubLocationsModel.getControlName();
                            List<String> controlNames=AppConstants.DCN_CN_List.get(postDataControlName.toLowerCase());
                            for (int cns = 0; cns < controlNames.size(); cns++) {
                                SearchableSpinner selected_searchableSpinner = AppConstants.SEARCHABLE_SPINNER_LIST.get(controlNames.get(cns) + SubformPos);
                                ControlObject selected_controlObj = AppConstants.DATACONTROL_CO_LIST.get(controlNames.get(cns) + SubformPos);
                                //selected_controlObj.setEnablePostLocationBinding(true);
                                //AppConstants.DATACONTROL_CO_LIST.put(controlName + SubformPos, selected_controlObj);
                                SpinnerData spinnerData = new SpinnerData();
                                spinnerData.setName(temp_name);
                                spinnerData.setId(temp_id);
                                List<SpinnerData> spinnerDataList = new ArrayList<>();
                                spinnerDataList.add(spinnerData);

                                List<String> dependentControl = AppConstants.DC_DEPENDENT_LIST.get(selected_controlObj.getControlName().toLowerCase());
                                boolean listenerUnEnable = false;
                                for (int j = 0; j < remainingdependingControls.size(); j++) {
                                    if (remainingdependingControls.get(j).getControlName().equalsIgnoreCase(dependentControl.get(0))) {
                                        listenerUnEnable = true;
                                        break;
                                    }
                                }
                                if (listenerUnEnable) {
                                    selected_searchableSpinner.setListener(null);
                                    selected_searchableSpinner.setOnItemSelectedListener(null);
                                }
                                selected_searchableSpinner.setItems(spinnerDataList);
                                selected_searchableSpinner.setItemID(temp_id);
                            }
                            //selected_searchableSpinner.setEnabled(false);
                        }
                    }
                }
            }

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "loadItems", e);
        }
    }

    public boolean checkDependentControlAvailableorNot(String DependentControlName) {
        boolean retflag = false;
        try {
            List<ControlObject> list_Control = new ArrayList<>();
            if (SubformFlag) {
                List<ControlObject> Sub_control = new ArrayList<>();
                if (AppConstants.IS_MULTI_FORM) {
                    Sub_control = ((MainActivity) context).subFormControlObjects;
                } else {
                    Sub_control = ((MainActivity) context).subFormControlObjects;
                }
                for (int i = 0; i < Sub_control.size(); i++) {
                    if (Sub_control.get(i).getControlName().equalsIgnoreCase(SubformName)) {
                        list_Control = Sub_control.get(i).getSubFormControlList();
                        break;
                    }
                }
            } else {
                if (AppConstants.IS_MULTI_FORM) {
                    list_Control = ((MainActivity) context).list_Control;
                } else {
                    list_Control = ((MainActivity) context).list_Control;
                }
            }

            for (int i = 0; i < list_Control.size(); i++) {
                if (list_Control.get(i).getControlName().equalsIgnoreCase(DependentControlName)) {
                    retflag = true;
                    break;
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "checkDependentControlAvailableorNot", e);
        }
        return retflag;
    }

    private void setItems() {
        try {
            if (dataControl_beans != null && dataControl_beans.size() > 0) {
                Log.d(TAG, "loadDataControlSpinnerData: " + dataControl_beans.size());
                searchableSpinner.setItems(loadDataControlSpinnerData(), new SearchableSpinner.SpinnerListener() {
                    @Override
                    public void onItemsSelected(View v, List<SpinnerData> items, int position) {
                        if (searchableSpinner.getSelectedId() != null) {
//                            RaiseListner(v);//no use
                        }
                    }
                });

            }
            if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().equals("")) {
                searchableSpinner.setItemID(controlObject.getDefaultValue());
                AppConstants.DC_SELCTED_ID.put(controlObject.getControlName(), controlObject.getDefaultValue());

            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setItems", e);
        }
    }

    private List<SpinnerData> loadDataControlSpinnerData() {


        List<SpinnerData> spinnerDataList = new ArrayList<>();
        try {
            for (int count = 0; count < dataControl_beans.size(); count++) {
                SpinnerData spinnerData = new SpinnerData();
                spinnerData.setId(dataControl_beans.get(count).getDc_id());
                spinnerData.setName(dataControl_beans.get(count).getDc_value());
                spinnerDataList.add(spinnerData);
            }

            if (controlObject.isEnableAllowOtherChoice()) {
                SpinnerData spinnerData = new SpinnerData();
                spinnerData.setId(String.valueOf(dataControl_beans.size()));
                spinnerData.setName(context.getString(R.string.other));
                spinnerDataList.add(spinnerData);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "loadDataControlSpinnerData", e);
        }
        return spinnerDataList;
    }

    private void setDependentItems(ControlObject dependent_controlObject, SearchableSpinner dependent_searchableSpinner, String curr_dataControlName,String curr_ControlName) {
        try {
            dependent_searchableSpinner.setItems(loadDependentDataControlSpinnerData( curr_dataControlName,curr_ControlName, dependent_controlObject), new SearchableSpinner.SpinnerListener() {
                @Override
                public void onItemsSelected(View v, List<SpinnerData> items, int position) {

                }
            });
            if (AppConstants.DataControl_Edit_Flag) {
                if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().equals("")) {
                    Log.d(TAG, "setDependentItems: " + "changed");
                    dependent_searchableSpinner.setItemID(dependent_controlObject.getDefaultValue());
                    AppConstants.DC_SELCTED_ID.put(controlObject.getControlName(), dependent_controlObject.getDefaultValue());
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setDependentItems", e);
        }
    }

    private List<SpinnerData> loadDependentDataControlSpinnerData(String curr_dataControlName,String curr_ControlName, ControlObject dependent_controlObject) {

        List<SpinnerData> spinnerDataList = new ArrayList<>();
        try {
            LinkedHashMap<String, String> NameWithSelectedID = new LinkedHashMap<String, String>();
            NameWithSelectedID = getAllFilterDataControlsIDs(curr_ControlName,curr_dataControlName, NameWithSelectedID);
            List<New_DataControl_Bean> dataControl_bean = RealmDBHelper.getTableDataWithBean(context, dependent_controlObject.getDataControlName(), NameWithSelectedID);

            for (int count = 0; count < dataControl_bean.size(); count++) {
                SpinnerData spinnerData = new SpinnerData();
                spinnerData.setId(dataControl_bean.get(count).getDc_id());
                spinnerData.setName(dataControl_bean.get(count).getDc_value());
                spinnerDataList.add(spinnerData);
            }

            if (controlObject.isEnableAllowOtherChoice()) {
                SpinnerData spinnerData = new SpinnerData();
                spinnerData.setId(String.valueOf(dataControl_bean.size()));
                spinnerData.setName(context.getString(R.string.other));
                spinnerDataList.add(spinnerData);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "loadDependentDataControlSpinnerData", e);
        }
        return spinnerDataList;
    }

    public boolean CheckDataControlCodetion(New_DataControl_Bean dataControl_bean, LinkedHashMap<String, String> NameWithSelectedID) {
        boolean returnval = true;
        try {
            LinkedHashMap<String, String> dc_DependencyValues = dataControl_bean.getDc_DependencyValues();
            Set<String> DependencyValues = dc_DependencyValues.keySet();
            String[] DependencyNames = DependencyValues.toArray(new String[DependencyValues.size()]);
            for (int i = 0; i < DependencyNames.length; i++) {
                if (NameWithSelectedID.containsKey(DependencyNames[i].toUpperCase())) {
                    if (NameWithSelectedID.get(DependencyNames[i].toUpperCase()) != null
                            && !NameWithSelectedID.get(DependencyNames[i].toUpperCase()).equalsIgnoreCase(dc_DependencyValues.get(DependencyNames[i]))) {
                        returnval = false;
                        break;
                    }
                }
//            else{
//                returnval=false;
//                break;
//            }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "CheckDataControlCodetion", e);
        }
        return returnval;
    }

    public LinkedHashMap<String, String> getAllFilterDataControlsIDs(String selectedControlName,String selectedDataControlName,
                                                                     LinkedHashMap<String, String> NameWithSelectedID) {
        LinkedHashMap<String, String> NameWithSelectedIDToReturn = new LinkedHashMap<>();
        NameWithSelectedIDToReturn.clear();
        try {
            if (AppConstants.DATACONTROL_CO_LIST.containsKey(selectedControlName.toLowerCase() + SubformPos)) {
                ControlObject SelectedObj = AppConstants.DATACONTROL_CO_LIST.get(selectedControlName.toLowerCase() + SubformPos);
                SearchableSpinner selectedSpn = AppConstants.SEARCHABLE_SPINNER_LIST.get(selectedControlName.toLowerCase() + SubformPos);
                //nk realm
                // List<New_DataControl_Bean> temp_dataControl_bean = RealmDBHelper.getTableDataWithBean(context,KeyName);//AppConstants.NEW_DATACONTROL_BEAN_LIST.get(KeyName);
                String keyID = RealmDBHelper.getControlKeyID(context, selectedDataControlName);
                //NameWithSelectedID.put((temp_dataControl_bean.get(0).getDc_KeyId()).toUpperCase(), selectedSpn.getSelectedId());
                NameWithSelectedID.put(keyID, selectedSpn.getSelectedId());

                if (SelectedObj.getDataControlStatus().equalsIgnoreCase(AppConstants.SP_DATA_CONTROLS_STATUS_DEPENDENT)) {
                    String dataControlName = SelectedObj.getDependentControl();
                    SelectedObj = AppConstants.DATACONTROL_CO_LIST.get(dataControlName.toLowerCase() + SubformPos);
                    if(SelectedObj!=null){
                        NameWithSelectedID = getAllFilterDataControlsIDs(SelectedObj.getControlName(),SelectedObj.getDataControlName(), NameWithSelectedID);
                        NameWithSelectedIDToReturn = NameWithSelectedID;
                    }else{
                        NameWithSelectedIDToReturn = NameWithSelectedID;
                    }
                } else {
                    NameWithSelectedIDToReturn = NameWithSelectedID;
                }
            } else {
                NameWithSelectedIDToReturn = NameWithSelectedID;
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getAllFilterDataControlsIDs", e);
        }
        return NameWithSelectedIDToReturn;
    }

    public CustomTextView setAlertDataControl() {

        return ct_alertDataControl;
    }

    public boolean dropdownItemIsSelected() {

        return searchableSpinner.isSelected();
    }

    public String getSelectedDropDownItemId() {


        String selectedItem = null;
        try {
            String selectedtext = searchableSpinner.getSelectedId();

            if (selectedtext != null && selectedtext.equalsIgnoreCase(context.getString(R.string.other)) && !ce_other.getText().toString().isEmpty()) {

                selectedItem = ce_other.getText().toString();

            } else if (selectedtext != null) {
                selectedItem = selectedtext;
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getSelectedDropDownItemId", e);
        }
        return selectedItem;
    }

    public String getSelectedDropDownItemName() {


        String selectedItem = null;
        try {
            String selectedtext = searchableSpinner.getSelectedName();

            if (selectedtext != null && selectedtext.equalsIgnoreCase(context.getString(R.string.other)) && !ce_other.getText().toString().isEmpty()) {

                selectedItem = ce_other.getText().toString();

            } else if (selectedtext != null) {
                selectedItem = selectedtext;
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getSelectedDropDownItemName", e);
        }
        return selectedItem;
    }

    private void loadItems(ControlObject controlObject) {

        try {
            String textFile = "DC_" + controlObject.getDataControlName() + "_" + searchableSpinner.getSelectedId() + ".txt";

            String line = ImproveHelper.readTextFileFromSD(context, textFile, sessionManager.getOrgIdFromSession());

            if (!line.equals("")) {

                try {
                    JSONObject jobj_textfile = new JSONObject(line);
                    String ControlName = jobj_textfile.getString("ControlName");
                    String KeyID = jobj_textfile.getString("KeyID");
                    String KeyName = jobj_textfile.getString("KeyName");
                    String keyName1 = jobj_textfile.getString("KeyName");
                    JSONArray jarr_Data = jobj_textfile.getJSONArray("Data");
                    List<New_DataControl_Bean> dataControlBeanList = new ArrayList<>();
                    if (!ImproveHelper.getLocale(context).equalsIgnoreCase("en")) {
                        KeyName = KeyName + "_" + ImproveHelper.getLocale(context);
                    }
                    for (int i = 0; i < jarr_Data.length(); i++) {

                        New_DataControl_Bean dataControl_bean = new New_DataControl_Bean();
                        dataControl_bean.setDc_id(jarr_Data.getJSONObject(i).getString(KeyID));
                        dataControl_bean.setDc_value(jarr_Data.getJSONObject(i).getString(KeyName));
                        dataControl_bean.setDc_name(ControlName);
                        dataControl_bean.setDc_KeyId(KeyID);
                        dataControl_bean.setDc_KeyName(KeyName);

                        if (!controlObject.getDependentControl().isEmpty()) {
                            LinkedHashMap<String, String> depen_hash = new LinkedHashMap<String, String>();
                            Iterator<String> dataobj_keys = jarr_Data.getJSONObject(i).keys();

                            while (dataobj_keys.hasNext()) {
                                String tempkey = dataobj_keys.next();
                                if (!KeyID.equalsIgnoreCase(tempkey) && !keyName1.equalsIgnoreCase(tempkey)) {
                                    if (!tempkey.startsWith(keyName1 + "_")) {
                                        depen_hash.put(tempkey, jarr_Data.getJSONObject(i).getString(tempkey));
                                    }
                                }
                            }

                            dataControl_bean.setDc_DependencyValues(depen_hash);

                            dataControl_bean.setDc_dependency(controlObject.getDependentControl());

                        }
                        dataControlBeanList.add(dataControl_bean);
                    }

                    Gson gson = new Gson();

                    String jsonDataControlItemsGet = PrefManger.getSharedPreferencesString(context, controlObject.getControlName(), "");


                    Log.d(TAG, "loadItems_in: " + "IN");
                    String jsonDataControlItems2 = gson.toJson(dataControlBeanList);
                    PrefManger.putSharedPreferencesString(context, controlObject.getControlName(), jsonDataControlItems2);

                } catch (JSONException E) {
                    System.out.println("Error==" + E);
                }

            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "loadItems", e);
        }
    }

    public void LoadPartialControlData() {
        try {
            //nk realm
            /*Gson gson = new Gson();

            String jsonDataControlItemsGet = PrefManger.getSharedPreferencesString(context, DataKeyName, "");

            Type type = new TypeToken<List<New_DataControl_Bean>>() {
            }.getType();
            dataControl_beans = gson.fromJson(jsonDataControlItemsGet, type);

            String KeyName = DataKeyName;
            if (!ImproveHelper.getLocale(context).equalsIgnoreCase("en")) {
                KeyName = KeyName + "_" + ImproveHelper.getLocale(context);
            }
            AppConstants.NEW_DATACONTROL_BEAN_LIST.put(KeyName, dataControl_beans);
*/

         /*   String depnspnName = dependent_ControlNameKey;
            if (!ImproveHelper.getLocale(context).equalsIgnoreCase("en")) {
                DataKeyName = DataKeyName + "_" + ImproveHelper.getLocale(context);
            }*/
            SearchableSpinner dependent_searchableSpinner = AppConstants.SEARCHABLE_SPINNER_LIST.get(dependent_ControlNameKey + SubformPos);
            //nk realm:List<New_DataControl_Bean> dataControl_bean = AppConstants.NEW_DATACONTROL_BEAN_LIST.get(DataKeyName);
            if (!dependent_ControlObj.isEnableUserControlBinding() && !dependent_ControlObj.isEnablePostLocationBinding()) {
                //nk realm:setDependentItems(controlObject_cur, selected_searchableSpinner, dataControl_bean, controlObject.getControlName());
                setDependentItems(dependent_ControlObj, dependent_searchableSpinner, controlObject.getDataControlName(), controlObject.getControlName());
                refreshDependentDatControls(dependent_ControlNameKey);
            } else if (dependent_searchableSpinner.isEnabled()) {
                //nk realm:setDependentItems(controlObject_cur, selected_searchableSpinner, dataControl_bean, controlObject.getControlName());
                setDependentItems(dependent_ControlObj, dependent_searchableSpinner, controlObject.getDataControlName(), controlObject.getControlName());
                refreshDependentDatControls(dependent_ControlNameKey);
            }
            improveHelper.dismissProgressDialog();
        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            ImproveHelper.improveException(context, TAG, "LoadPartialControlData", e);
        }
    }

    public void clear() {
        searchableSpinner.clearSelections();
        controlObject.setText(null);
        controlObject.setControlValue(null);
    }

    public void setItemId(String id) {

        searchableSpinner.setItemID(id);
        if(controlObject.isInvisible()){
            RaiseListner(searchableSpinner);
        }
    }

    public void setEditValue(HashMap<String, String> controlValue, int subFormPos) {
        this.SubformPos = subFormPos;
        if (!controlValue.get("Value_id").contentEquals("")) {
            SearchableSpinner searchableSpinner = getSpinner();
            searchableSpinner.setItemID(controlValue.get("Value_id"));
        }
    }


    public void setEnabled(boolean enabled) {
//        setViewDisable(rView, !enabled);
        controlObject.setDisable(!enabled);
//        setViewDisableOrEnableDefault(context, view, enabled);
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),enabled,ll_tap_text,view);
    }

    public LinearLayout getLl_tap_text() { // linear for taptext

        return ll_tap_text;
    }

    public void setVisibility(boolean visible) {
        if (visible) {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        } else {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }
    }

    public interface DataControlListener {
        void onItemsSelected(View v, List<SpinnerData> items, int position);
    }

    public interface DownloadFileListener {
        void onSuccess(File file);

        void onFailed(String errorMessage);
    }

    public class DownloadFileFromURLTask extends AsyncTask<String, String, String> {

        String strFileName, strAppName, strSDCardUrl;
        File file, saveFilePath;
        Context context;
        DownloadFileListener downloadFileListener;


        public DownloadFileFromURLTask(Context context, String strAppName, String strSDCardUrl, DownloadFileListener downloadFileListener) {
            this.context = context;
            this.strAppName = strAppName;
            this.strSDCardUrl = strSDCardUrl;
            this.downloadFileListener = downloadFileListener;

        }

        /**
         * Downloading file in background thread
         */


        @SuppressLint("SdCardPath")
        @Override
        protected String doInBackground(String... f_url) {
            Log.i(TAG, "do in background" + f_url[0]);
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                String[] strsplit = f_url[0].split("/");
                strFileName = strsplit[strsplit.length - 1];

//                String strSeparatePaths = AppConstants.API_NAME_CHANGE +"/"+strAppName.replaceAll(" ", "_")+"/";
                File cDir = context.getExternalFilesDir(strSDCardUrl);
                saveFilePath = new File(cDir.getAbsolutePath(), strFileName.replaceAll(" ", "_"));
                Log.d(TAG, "AppsListFilesSave: " + saveFilePath);
//                outFile = new File(file, strFileName);
                // Output stream to write file
                OutputStream output = new FileOutputStream(saveFilePath);

                byte[] data = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();


            } catch (Exception e) {
                saveFilePath = null;
                Log.e("Error: ", e.getMessage());
            }
            if (saveFilePath == null) {
                return null;
            } else {
                return saveFilePath.getAbsolutePath();
            }
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @SuppressLint("SdCardPath")
        @Override
        protected void onPostExecute(String file_url) {
            Log.i(TAG, "on post execute!: " + file_url);
            Log.d(TAG, "onPostExecuteAppsList: " + strFileName);
            if (file_url == null) {
                downloadFileListener.onFailed("Failed To Download File. Try Again!");
            } else {
                downloadFileListener.onSuccess(new File(file_url));
            }
            //loadItems(controlObject_cur);
            //LoadPartialControlData();
        }
    }

    public void dataControlSetPropertiesAction(List<Param> propertiesListMain){
        try{
            List<Param> propertiesList = new ArrayList<>();
            String propertyText = "";
            ExpressionMainHelper expressionMainHelper = new ExpressionMainHelper();
            propertiesList = propertiesListMain;
            if (propertiesList != null && propertiesList.size() > 0) {
                for (int i = 0; i < propertiesList.size(); i++) {
                    Param property = propertiesList.get(i);
                    if (property.getName().contentEquals("ExpressionBuilder")) {
                        propertyText = expressionMainHelper.ExpressionHelper(context, property.getText());
                    } else {
                        propertyText = property.getText();
                    }
                    if (!propertyText.contentEquals("")) {
//                        Log.d(TAG, "DataTableSetPropertiesAction: " + property.getValue() + " - " + propertyText);
                        if (property.getValue().contentEquals(PropertiesNames.DISPLAY_NAME)) {
                            tv_displayName.setText(propertyText);
                            controlObject.setDisplayName(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.HINT)) {
                            if (property.getValue() != null && !property.getValue().isEmpty()) {
                                tv_hint.setVisibility(View.VISIBLE);
                                tv_hint.setText(property.getValue());
                            } else {
                                tv_hint.setVisibility(View.GONE);
                            }
                            controlObject.setHint(property.getValue());
                        } else if (property.getValue().contentEquals(PropertiesNames.INVISIBLE)) {
                            controlObject.setInvisible(Boolean.parseBoolean(propertyText));
                        } else if (property.getValue().contentEquals(PropertiesNames.HIDE_DISPLAY_NAME)) {
                            controlObject.setHideDisplayName(Boolean.parseBoolean(propertyText));
                        }  else if (property.getValue().contentEquals(PropertiesNames.READ_ONLY)) {
                            setEnabled(!Boolean.parseBoolean(propertyText));
                        } else if (property.getValue().contentEquals(PropertiesNames.FONT_SIZE)) {
                            controlObject.setTextSize(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.FONT_COLOR)) {
                            controlObject.setTextColor(Color.parseColor(propertyText));
                            controlObject.setTextHexColor(propertyText);
                        } else if (property.getValue().contentEquals(PropertiesNames.FONT_STYLE)) {
                            controlObject.setTextStyle(propertyText);
                        }else if (property.getValue().contentEquals(PropertiesNames.USER_CONTROL_BINDING)) {
                            controlObject.setEnableUserControlBinding(Boolean.parseBoolean(propertyText));
                        }else if (property.getValue().contentEquals(PropertiesNames.POST_LOCATION_BINDING)) {
                            controlObject.setEnablePostLocationBinding(Boolean.parseBoolean(propertyText));
                        }
                    }
                }
                setDisplaySettings(context, tv_displayName, controlObject);

                loadItems_realm();
            }


        }catch (Exception e){
            Log.d(TAG, "dataControlSetPropertiesAction: "+e.toString());
        }

    }
    public String setStyleFromIndex(String propertyText) {

        if (propertyText.equalsIgnoreCase("0")) {
            propertyText = "Bold";
        } else if (propertyText.equalsIgnoreCase("1")) {
            propertyText = "Italic";
        }
        return propertyText;
    }
    public void showMessageBelowControl(String msg) {
        if(msg != null && !msg.isEmpty()) {
            ct_showText.setVisibility(View.VISIBLE);
            ct_showText.setText(msg);
        }else{
            ct_showText.setVisibility(View.GONE);
        }
    }


}

