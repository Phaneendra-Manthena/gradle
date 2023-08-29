package com.bhargo.user.Query;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DYNAMIC_LABEL;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bhargo.user.Java_Beans.ActionWithoutCondition_Bean;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.Create_Query_Object;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.Java_Beans.DataManagementOptions;
import com.bhargo.user.Java_Beans.DataTableColumn_Bean;
import com.bhargo.user.Java_Beans.QuerySelectField_Bean;
import com.bhargo.user.Java_Beans.VisibilityManagementOptions;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.advanced.BarCode;
import com.bhargo.user.controls.advanced.DataTableControl;
import com.bhargo.user.controls.advanced.QRCode;
import com.bhargo.user.controls.standard.AudioPlayer;
import com.bhargo.user.controls.standard.DynamicLabel;
import com.bhargo.user.controls.standard.VideoPlayer;
import com.bhargo.user.controls.standard.ViewFileControl;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppDetailsAdvancedAction;
import com.bhargo.user.pojos.AppDetailsAdvancedInput;
import com.bhargo.user.pojos.DeviceIdResponse;
import com.bhargo.user.pojos.FormControls;
import com.bhargo.user.pojos.FormLevelTranslation;
import com.bhargo.user.pojos.SubFormTableColumns;
import com.bhargo.user.screens.BottomNavigationActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bhargo.user.utils.XMLHelper;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QueryDetailsActivity extends BaseActivity {
    private static final String TAG = "QueryDetailsActivity";
    private static final int BACK_TO_LIST_FLAG = 2002;
    private final String QRcodeDefaultValue = "123456789";
    public ImageView ib_settings;

    Random random = new Random();
    int randomPos = 0;
    ImproveHelper improveHelper;
    XMLHelper xmlHelper;
    boolean subFormControlsEnd = false;
    ImproveDataBase improveDataBase = new ImproveDataBase(this);
    GetServices getServices;
    ImageView iv_delete, iv_edit;
    LinearLayout ll_single_record;
    String status = "online";
    FormLevelTranslation formLevelTranslation;
    DataManagementOptions dataManagementOptions;
    FormControls formControls;
    String appLanguage = "en";
    SessionManager sessionManager;
    String mediaFile;
    private List<ControlObject> controlsList = new ArrayList<>();
    private List<ControlObject> gpsControlList = new ArrayList<>();
    private List<ControlObject> imageControlList = new ArrayList<>();
    private List<ControlObject> videoControlsList = new ArrayList<>();
    private List<ControlObject> audioControlsLIst = new ArrayList<>();
    private HashMap<String, List<List<ControlObject>>> subformControlsList = new HashMap<>();
    private List<ControlObject> allControlsList = new ArrayList<>();
    private LinearLayout llDetails;
    private List<AudioPlayer> audioPlayerList = new ArrayList<>();
    private JSONObject queryDetailsObject;
    private Create_Query_Object create_query_object = new Create_Query_Object();
    private Context context;
    private String appName;
    private String fromActivity;
    private ScrollView scrollView;
    private Toolbar toolbar;
    private JSONArray subFormsArray = new JSONArray();
    private DataCollectionObject dataCollectionObject;
    VisibilityManagementOptions visibilityManagementOptions;
    private String strPostId, strAppDesign, strAppVersion, strOrgId, strAppType, strAppName, strDisplayAppName,strAppIcon, strCreatedBy, strUserId, strDistributionId, strUserLocationStructure, strFrom_InTaskDetails;
AudioPlayer audioPlayer_;
    String tableName;
    List<SubFormTableColumns> subFormDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Theme
        setBhargoTheme(this,AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
        setContentView(R.layout.activity_query_details);
        context = QueryDetailsActivity.this;
        try {
            appName = getIntent().getStringExtra("appName");
            fromActivity = getIntent().getStringExtra("fromActivity");
//            dataCollectionObject = (DataCollectionObject) getIntent().getSerializableExtra("dataCollectionObject");
            dataManagementOptions = (DataManagementOptions) getIntent().getSerializableExtra("dataManagementOptions");
            visibilityManagementOptions = (VisibilityManagementOptions) getIntent().getSerializableExtra("visibilityManagementOptions");
            formControls = (FormControls) getIntent().getSerializableExtra("formControls");
            if (getIntent().hasExtra("s_form_translation")) {
                formLevelTranslation = (FormLevelTranslation) getIntent().getSerializableExtra("s_form_translation");
            }
//            strAppDesign = getIntent().getStringExtra("s_app_design");
            strAppVersion = getIntent().getStringExtra("s_app_version");
            strOrgId = getIntent().getStringExtra("s_org_id");
            strAppType = getIntent().getStringExtra("s_app_type");
            strAppName = getIntent().getStringExtra("s_app_name");
            strDisplayAppName = getIntent().getStringExtra("s_display_app_name");
            strAppIcon = getIntent().getStringExtra("s_app_icon");
            strCreatedBy = getIntent().getStringExtra("s_created_by");
            strUserId = getIntent().getStringExtra("s_user_id");
            strDistributionId = getIntent().getStringExtra("s_distribution_id");
            strUserLocationStructure = getIntent().getStringExtra("s_user_location_Structure");
            strFrom_InTaskDetails = getIntent().getStringExtra("From_InTaskDetails");
            strPostId = getIntent().getStringExtra("s_user_post_id");
            tableName =  getIntent().getStringExtra("tableName");
            String subFormData =  getIntent().getStringExtra("subFormDetails");
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<SubFormTableColumns>>(){}.getType();
            subFormDetails = gson.fromJson(subFormData, listType);
        } catch (Exception e) {

        }
        audioPlayerList = new ArrayList<>();
        appLanguage = ImproveHelper.getLocale(this);
        xmlHelper = new XMLHelper();
        improveHelper = new ImproveHelper(context);
        getServices = RetrofitUtils.getUserService();
        sessionManager = new SessionManager(context);
        initializeActionBar();
        enableBackNavigation(true);

        iv_circle_appIcon.setVisibility(View.VISIBLE);

        if (!isNetworkStatusAvialable(context)) {
            status = "offline";
        }
        llDetails = findViewById(R.id.llDetails);
        if (fromActivity != null && fromActivity.equalsIgnoreCase("ViewData")) {
            if (formLevelTranslation != null) {
                title.setText(strAppName);
                if (!appLanguage.contentEquals("en")) {
                    title.setText(formLevelTranslation.getTranslatedAppTitleMap().get(appLanguage));
                }
            } else {
                title.setText(strAppName);
            }
        } else {
            create_query_object = (Create_Query_Object) getIntent().getSerializableExtra("create_query_object");

            if (create_query_object.getQuery_Title() != null) {

                title.setText(create_query_object.getQuery_Title());
            } else {
                title.setText(appName);
            }

            if (!create_query_object.getQuery_FinalForm_Heading().equals("")) {
                title.setText(create_query_object.getQuery_FinalForm_Heading());
            }
        }
        loadAppIcon(strAppIcon);
        setTitleAccordingToLang();
        String strDesignFormat = improveDataBase.getDesignFormat(sessionManager.getOrgIdFromSession(), strAppName);
        dataCollectionObject = xmlHelper.XML_To_DataCollectionObject(strDesignFormat);
        if (getIntent() != null && getIntent().getStringExtra("jsonObject") != null) {
            try {
                queryDetailsObject = new JSONObject(getIntent().getStringExtra("jsonObject"));
                new PreviewDataASync().execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        scrollView = findViewById(R.id.scrollView);
        ll_single_record = findViewById(R.id.ll_single_record);
        iv_delete = findViewById(R.id.iv_delete);
        iv_edit = findViewById(R.id.iv_edit);
        if (dataManagementOptions != null && dataManagementOptions.getRecordInsertionType().equalsIgnoreCase("Single") && fromActivity != null && fromActivity.equalsIgnoreCase("ViewData")) {
            ll_single_record.setVisibility(View.VISIBLE);
            if (dataManagementOptions.isEnableEditData()) {
                iv_edit.setVisibility(View.VISIBLE);
            }
            if (dataManagementOptions.isEnableDeleteData()) {
                iv_delete.setVisibility(View.VISIBLE);
            }
        }
        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editRow(queryDetailsObject);
            }
        });

        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (dataManagementOptions != null && dataManagementOptions.getSubFormInMainForm().equalsIgnoreCase("")) {
                        deleteAlert(context, queryDetailsObject.getString("Trans_ID"), status, false);
                    } else {
                        deleteAlert(context, getTransIdsToDelete(queryDetailsObject), status, true);
                    }
                } catch (Exception e) {

                }
            }
        });


    }

    private void setTitleAccordingToLang() {/*
        if (!appLanguage.contentEquals("en")) {
            if (dataCollectionObject.getApp_Title() != null && !dataCollectionObject.getApp_Title().contentEquals("")) {
                title.setText(dataCollectionObject.getApp_Title());
                LinkedHashMap<String, String> headerMap = dataCollectionObject.getTranslatedAppTitleMap();
                if (headerMap != null && !appLanguage.contentEquals("en")) {
                    title.setText(headerMap.get(appLanguage));
                }
                if (title.getText().toString().trim().contentEquals("")) {
                    title.setText(dataCollectionObject.getApp_Name());
                    LinkedHashMap<String, String> headerMap1 = dataCollectionObject.getTranslatedAppNames();
                    if (headerMap1 != null && !appLanguage.contentEquals("en")) {
                        title.setText(headerMap1.get(appLanguage));
                    }
                }
            } else {
                if (dataCollectionObject.getApp_Name() != null) {
                    title.setText(dataCollectionObject.getApp_Name());
                    LinkedHashMap<String, String> headerMap = dataCollectionObject.getTranslatedAppNames();
                    if (headerMap != null && !appLanguage.contentEquals("en")) {
                        title.setText(headerMap.get(appLanguage));
                    }
                }
            }
        }*/
    }

    private ControlObject setDataAccordingToLang(ControlObject loadControlObject) {

        if (!appLanguage.contentEquals("en") && loadControlObject.getTranslationsMappingObject() != null) {
           /* LinkedHashMap<String,String> map = loadControlObject.getTranslationsMap();
            loadControlObject.setDisplayName(map.get(appLanguage));*/
            LinkedHashMap<String, ControlObject> map = loadControlObject.getTranslationsMappingObject();
            ControlObject langObject = map.get(appLanguage);
            if (langObject != null) {
                if (langObject.getDisplayName() != null && !langObject.getDisplayName().contentEquals("")) {
                    loadControlObject.setDisplayName(langObject.getDisplayName());
                }
                if (langObject.getHint() != null && !langObject.getHint().contentEquals("")) {
                    loadControlObject.setHint(langObject.getHint());
                }
                if (langObject.getMandatoryFieldError() != null && !langObject.getMandatoryFieldError().contentEquals("")) {
                    loadControlObject.setMandatoryFieldError(langObject.getMandatoryFieldError());
                }
                if (langObject.getUniqueFieldError() != null && !langObject.getUniqueFieldError().contentEquals("")) {
                    loadControlObject.setUniqueFieldError(langObject.getUniqueFieldError());
                }
                if (langObject.getUpperLimitErrorMesage() != null && !langObject.getUpperLimitErrorMesage().contentEquals("")) {
                    loadControlObject.setUpperLimitErrorMesage(langObject.getUpperLimitErrorMesage());
                }
                if (langObject.getLowerLimitErrorMesage() != null && !langObject.getLowerLimitErrorMesage().contentEquals("")) {
                    loadControlObject.setLowerLimitErrorMesage(langObject.getLowerLimitErrorMesage());
                }
                if (langObject.getCappingError() != null && !langObject.getCappingError().contentEquals("")) {
                    loadControlObject.setCappingError(langObject.getCappingError());
                }

                if (langObject.getMaxCharError() != null && !langObject.getMaxCharError().contentEquals("")) {
                    loadControlObject.setMaxCharError(langObject.getMaxCharError());
                }
                if (langObject.getMinCharError() != null && !langObject.getMinCharError().contentEquals("")) {
                    loadControlObject.setMinCharError(langObject.getMinCharError());
                }
                if (langObject.getMaxUploadError() != null && !langObject.getMaxUploadError().contentEquals("")) {
                    loadControlObject.setMaxUploadError(langObject.getMaxUploadError());
                }
                if (langObject.getMinUploadError() != null && !langObject.getMinUploadError().contentEquals("")) {
                    loadControlObject.setMinUploadError(langObject.getMinUploadError());
                }
                if (langObject.getBetweenStartAndEndDateError() != null && !langObject.getBetweenStartAndEndDateError().contentEquals("")) {
                    loadControlObject.setBetweenStartAndEndDateError(langObject.getBetweenStartAndEndDateError());
                }

                if (langObject.getBeforeCurrentDateError() != null && !langObject.getBeforeCurrentDateError().contentEquals("")) {
                    loadControlObject.setBeforeCurrentDateError(langObject.getBeforeCurrentDateError());
                }
                if (langObject.getAfterCurrentDateError() != null && !langObject.getAfterCurrentDateError().contentEquals("")) {
                    loadControlObject.setAfterCurrentDateError(langObject.getAfterCurrentDateError());
                }

                if (langObject.getMaximumDurationError() != null && !langObject.getMaximumDurationError().contentEquals("")) {
                    loadControlObject.setMaximumDurationError(langObject.getMaximumDurationError());
                }
                if (langObject.getMinimumDurationError() != null && !langObject.getMinimumDurationError().contentEquals("")) {
                    loadControlObject.setMinimumDurationError(langObject.getMinimumDurationError());
                }

                if (langObject.getMaxAmountError() != null && !langObject.getMaxAmountError().contentEquals("")) {
                    loadControlObject.setMaxAmountError(langObject.getMaxAmountError());
                }
                if (langObject.getMinAmountError() != null && !langObject.getMinAmountError().contentEquals("")) {
                    loadControlObject.setMinAmountError(langObject.getMinAmountError());
                }

                if (langObject.getMaximumRowsError() != null && !langObject.getMaximumRowsError().contentEquals("")) {
                    loadControlObject.setMaximumRowsError(langObject.getMaximumRowsError());
                }
                if (langObject.getMinimumRowsError() != null && !langObject.getMinimumRowsError().contentEquals("")) {
                    loadControlObject.setMinimumRowsError(langObject.getMinimumRowsError());
                }

                if (loadControlObject.getControlType().contentEquals(CONTROL_TYPE_DYNAMIC_LABEL) && loadControlObject.isMakeAsSection()) {
                    loadControlObject.setValue(langObject.getDisplayName());
                }


                if (langObject.getItemsList() != null && langObject.getItemsList().size() > 0) {
                    loadControlObject.setItemsList(langObject.getItemsList());

                    List<String> items = new ArrayList<>();
                    for (int i = 0; i < loadControlObject.getItemsList().size(); i++) {

                        items.add(loadControlObject.getItemsList().get(i).getValue());
                    }
                    loadControlObject.setItems(items);
                }
            }

        }
        return loadControlObject;
    }

    private String getTransIdsToDelete(JSONObject object) {
        StringBuilder trans_ids = new StringBuilder();
        try {
            JSONArray jsonArray = object.getJSONArray("SubForm").getJSONObject(0).getJSONArray(dataManagementOptions.getSubFormInMainForm());
            for (int i = 0; i < jsonArray.length(); i++) {
                trans_ids.append(",").append(jsonArray.getJSONObject(i).getString("Trans_ID"));
            }
        } catch (Exception e) {
            Log.d(TAG, "getTransIdsToDelete: " + e);
            ImproveHelper.improveException(context, TAG, "getTransIdsToDelete", e);
        }
        return trans_ids.toString().replaceFirst(",", "");
    }

    private String replaceSpecialCharacters(String controlValue) {

        return controlValue.replace("^", ",");
    }

    private ControlObject getControlObject(String columnName) {

        List<QuerySelectField_Bean> displayFields = create_query_object.getList_Form_DisplayFields();

        if (fromActivity != null && fromActivity.equalsIgnoreCase("ViewData")) {
            List<ControlObject> controlsList = dataCollectionObject.getControls_list();
            for (ControlObject controlObject : controlsList) {
                if (columnName.contains("_Coordinates")) {
                    columnName = columnName.substring(0, columnName.indexOf("_Coordinates"));
                }
                if (controlObject.getControlName().equalsIgnoreCase(columnName)) {
                    controlObject.setFieldDisplayFormat("Plain_Text");
                    controlObject.setBackGroundColor(getRandomColor());
                    controlObject.setTextHexColor("#FFFFFF");

                    return controlObject;
                }
            }
        } else {
            for (int i = 0; i < displayFields.size(); i++) {

                QuerySelectField_Bean displayField = displayFields.get(i);

                ControlObject controlObject = new ControlObject();
                controlObject = displayField.getField_ControlObject();
                controlObject.setFieldDisplayFormat(displayField.getField_Display_Formate());

                if (columnName.contains("_Coordinates")) {
                    columnName = columnName.substring(0, columnName.indexOf("_Coordinates"));
                }

                if (columnName.equals(controlObject.getControlName()) || controlObject.getControlName().contains("Coordinates")) {
                    if (controlObject.getFieldDisplayFormat() != null && controlObject.getFieldDisplayFormat().equalsIgnoreCase("Plain_Text")) {
                        controlObject.setBackGroundColor(getRandomColor());
                        controlObject.setTextHexColor("#FFFFFF");
                    }
                    return controlObject;
                }


            }
        }

        return null;
    }

    private ControlObject getSubFormControlObject(String columnName, String subFormName) {

        List<ControlObject> controlsList = dataCollectionObject.getControls_list();

        for (ControlObject subformControlObject : controlsList) {
            if (subformControlObject.getControlName().equalsIgnoreCase(subFormName)) {
                //subform name as section name

                List<ControlObject> subformControlObjectList = subformControlObject.getSubFormControlList();
                for (ControlObject controlObject : subformControlObjectList) {
                    if (columnName.contains("_Coordinates")) {
                        columnName = columnName.substring(0, columnName.indexOf("_Coordinates"));
                    }
                    if (controlObject.getControlName().equalsIgnoreCase(columnName)) {
                        controlObject.setFieldDisplayFormat("Plain_Text");
                        controlObject.setBackGroundColor(getRandomColor());
                        controlObject.setTextHexColor("#FFFFFF");
                        return controlObject;
                    }
                }
            }
        }
        return null;
    }

    private String getRandomColor() {
        String code = null;
        /*String ColorOne = "#16a085";
        String ColorTwo = "#27ae60";
        String ColorThree = "#2980b9";
        String ColorFour = "#8e44ad";
        String ColorFive = "#2c3e50";
        String ColorSix = "#f39c12";
        String ColorSeven = "#e74c3c";
        String ColorEight = "#AFB42B";
        String ColorNine = "#CE755C";
        String ColorTen = "#D43F3A";*/
        List<String> colorsList = new ArrayList<>();
        colorsList.add("#16a085");
        colorsList.add("#27ae60");
        colorsList.add("#2980b9");
        colorsList.add("#8e44ad");
        colorsList.add("#62769a");
        colorsList.add("#f39c12");
        colorsList.add("#e74c3c");
        colorsList.add("#AFB42B");
        colorsList.add("#CE755C");
        colorsList.add("#D43F3A");
        if (randomPos == colorsList.size()) {
            randomPos = 0;
        }
        code = colorsList.get(randomPos);
        randomPos++;
        return code;
    }

    public void stopAudioPlayer() {

        for (int i = 0; i < audioPlayerList.size(); i++) {
            AudioPlayer audioPlayer = audioPlayerList.get(i);
            audioPlayer.stopPlaying();
        }

    }

    @Override
    protected void onDestroy() {
        stopAudioPlayer();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //stopAudioPlayer();
        if (dataManagementOptions != null && dataManagementOptions.getRecordInsertionType().equalsIgnoreCase("Single") && fromActivity != null && fromActivity.equalsIgnoreCase("ViewData")) {
            openAppsList();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (dataManagementOptions != null && dataManagementOptions != null && dataManagementOptions.getRecordInsertionType().equalsIgnoreCase("Single") && fromActivity != null && fromActivity.equalsIgnoreCase("ViewData")) {
                    openAppsList();
                } else {
                    finish();
                }
                return true;
            default:
                return false;
        }
    }

    private void editRow(JSONObject jsonObject) {
        try {

            Intent intent = new Intent(context, MainActivity.class);

            intent.putExtra("jsonObject", jsonObject.toString());
            intent.putExtra("dataManagementOptions", dataManagementOptions);
            intent.putExtra("visibilityManagementOptions", visibilityManagementOptions);
            intent.putExtra("tableName",tableName);
            Gson gson = new Gson();
            intent.putExtra("subFormDetails",  gson.toJson(subFormDetails));
            intent.putExtra("Trans_ID", jsonObject.getString("Trans_ID"));
            intent.putExtra("appName", strAppName);
            intent.putExtra("s_childForm", "EditForm");
            intent.putExtra("s_app_version", strAppVersion);
            intent.putExtra("s_app_type", strAppType);
            intent.putExtra("s_org_id", strOrgId);
            intent.putExtra("s_app_name", strAppName);
            intent.putExtra("s_display_app_name", strDisplayAppName);
            intent.putExtra("s_app_icon", strAppIcon);
            intent.putExtra("s_created_by", strCreatedBy);
            intent.putExtra("s_user_id", strUserId);
            intent.putExtra("s_distribution_id", strDistributionId);
            intent.putExtra("s_user_location_Structure", strUserLocationStructure);
            intent.putExtra("app_edit", "Edit");
            intent.putExtra("from", AppConstants.TYPE_CALL_FORM);
            finish();


            startActivityForResult(intent, BACK_TO_LIST_FLAG);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getTransIdsToDelete", e);
        }
    }

    public void deleteAlert(Context context, String transId, String status, boolean subFormInMainForm) {
        try {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.are_you_sure_del)

                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what would happen when positive button is clicked
                            dialogInterface.dismiss();
                            deleteRow(transId, status, subFormInMainForm);
                        }
                    })

                    .setNegativeButton(R.string.d_no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what should happen when negative button is clicked
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "deleteAlert", e);
        }

    }

    private void deleteRow(String transId, String status, boolean subFormInMainForm) {
        try {
            if (status.equalsIgnoreCase("offline")) {
                boolean result_status = improveDataBase.deleteRowData(transId);
                if (result_status) {
//                    jsonArrayMain.remove(position);
//                    rv_apps.setAdapter(appsAdvancedAdapter);
//                    appsAdvancedAdapter.updateList(jsonArrayMain, status);
                    openAppsList();
                }
            } else {
                showProgressDialog(getString(R.string.please_wait));
                AppDetailsAdvancedAction appDetailsAdvancedAction = new AppDetailsAdvancedAction();
                appDetailsAdvancedAction.setOrgId(strOrgId);
                appDetailsAdvancedAction.setUserId(strUserId);
                appDetailsAdvancedAction.setPageName(strAppName);
                appDetailsAdvancedAction.setAction("Delete");
                appDetailsAdvancedAction.setTransID(transId);
                appDetailsAdvancedAction.setSubFormInMainForm(subFormInMainForm);
                Call<DeviceIdResponse> getAllAppNamesDataCall = getServices.iDeleteAppData(sessionManager.getAuthorizationTokenId(),appDetailsAdvancedAction);
                getAllAppNamesDataCall.enqueue(new Callback<DeviceIdResponse>() {
                    @Override
                    public void onResponse(Call<DeviceIdResponse> call, Response<DeviceIdResponse> response) {
                        dismissProgressDialog();
                        DeviceIdResponse responseData = response.body();
                        if (responseData.getStatus().equalsIgnoreCase("200")) {
                            Toast.makeText(context, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                            if (responseData.getMessage().equalsIgnoreCase("Success")) {
                                openAppsList();
//                                rv_apps.setAdapter(appsAdvancedAdapter);
//                                if (subFormInMainForm) {
//                                    newJsonArray.remove(position);
//                                    appsAdvancedAdapter.updateList(newJsonArray, status);
//                                } else {
//                                    jsonArray.remove(position);
//                                    appsAdvancedAdapter.updateList(jsonArray, status);
//                                }

                            } else {
                                Toast.makeText(context, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<DeviceIdResponse> call, Throwable t) {
                        dismissProgressDialog();
                    }
                });
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "deleteRow", e);
        }
    }

    private void openAppsList() {

        Intent intent = new Intent(context, BottomNavigationActivity.class);
        intent.putExtra("NotifRefreshAppsList", "0");
        startActivity(intent);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//            finish();
        AppDetailsAdvancedInput appDetailsAdvancedInput = new AppDetailsAdvancedInput();
        appDetailsAdvancedInput.setOrgId(strOrgId);
        appDetailsAdvancedInput.setPageName(strAppName);
        appDetailsAdvancedInput.setUserId(strUserId);
        appDetailsAdvancedInput.setSubmittedUserPostID(strPostId);
        if (dataManagementOptions.getFetchData() != null) {
            appDetailsAdvancedInput.setFetchData(dataManagementOptions.getFetchData());
        } else {
            appDetailsAdvancedInput.setFetchData("Login User Post");
        }
        appDetailsAdvancedInput.setOrderbyStatus("False");
        appDetailsAdvancedInput.setOrderByColumns("");
        appDetailsAdvancedInput.setOrderByType("");
        appDetailsAdvancedInput.setLazyLoading("True");
        appDetailsAdvancedInput.setThreshold("2");
        appDetailsAdvancedInput.setRange("1" + "-" + "2");
        //TODO  --- In future need to set order by columns in the below line
        appDetailsAdvancedInput.setLazyOrderKey("SELECT NULL");

        CheckFormHasData getRequest = new CheckFormHasData(appDetailsAdvancedInput);
        try {
            getRequest.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void loadAppIcon(String appIconPath) {

        try {
            String[] imgUrlSplit = appIconPath.split("/");
            String imgNameInPackage = imgUrlSplit[imgUrlSplit.length - 1].replaceAll(" ", "_");
            String replaceWithUnderscore = strAppName.replaceAll(" ", "_");
            Log.d(TAG, "loadAppIconAppNameQ: " + replaceWithUnderscore);
            String strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + replaceWithUnderscore;
            if (ImproveHelper.isFileExistsInExternalPackage(context, strSDCardUrl, imgNameInPackage)) {
                improveHelper.setImageFromPackageFolder(context, strSDCardUrl, imgNameInPackage, iv_circle_appIcon);
            } else {
                if (isNetworkStatusAvialable(context)) {
                    Glide.with(context).load(appIconPath).into(iv_circle_appIcon);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "loadAppIcon", e);
        }
    }

    public void setImageFromSDCard(String strImagePath) {

        File imgFile = new File(Environment.getExternalStorageDirectory(), strImagePath);
        Log.d(TAG, "setImageFromSDCard: " + imgFile);
        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            iv_circle_appIcon.setImageBitmap(myBitmap);

        }
    }

    private class PreviewDataASync extends AsyncTask<String, String, String> {


        public PreviewDataASync() {
            showProgressDialog("Please wait...");
        }

        /**
         * Downloading file in background thread
         */

        @Override
        protected String doInBackground(String... f_url) {
            new Handler(Looper.getMainLooper()).post(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                List<String> viewPageColumnsOrder = new ArrayList<>();
                                if (dataManagementOptions != null && dataManagementOptions.getViewPageColumnsOrder() != null) {
                                    viewPageColumnsOrder = dataManagementOptions.getViewPageColumnsOrder();
                                }
                                if (queryDetailsObject != null) {
                                    if (fromActivity != null && fromActivity.equalsIgnoreCase("ViewData") && viewPageColumnsOrder != null && viewPageColumnsOrder.size() > 0) {
                                        for (String controlName : viewPageColumnsOrder) {
                                            if (queryDetailsObject.has(controlName)) {
                                                String controlValue = queryDetailsObject.getString(controlName);

                                                if (!controlName.equalsIgnoreCase("Trans_id") &&
                                                        !controlName.contentEquals("SubForm") &&
                                                        !controlName.endsWith("Trans_ID") &&
                                                        !controlName.endsWith("_emp_id") &&
                                                        !controlName.endsWith("_postid") &&
                                                        !controlName.endsWith("_trans_date")) {

                                                    ControlObject controlObject = getControlObject(controlName);
                                                    if (controlObject != null) {
                                                        controlObject.setControlValue(controlValue);

                                                        if (controlObject.getControlType() != null) {

                                                            if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                                                                controlsList.add(controlObject);
                                                            } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE)) {
                                                                controlsList.add(controlObject);
                                                            } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                                                                controlsList.add(controlObject);
                                                            } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_RECORDING)) {
                                                                controlsList.add(controlObject);
                                                            } else {
                                                                controlsList.add(controlObject);
                                                            }
                                                        } else {
//                                                            controlsList.add(controlObject);
                                                        }
                                                    }
                                                }
                                            } else {
                                                JSONArray subFormArray = new JSONArray();
                                                if (queryDetailsObject.has("SubForm")) {
                                                    subFormArray = queryDetailsObject.getJSONArray("SubForm");
                                                    for (int i = 0; i < subFormArray.length(); i++) {
                                                        JSONObject subformJsonObject = subFormArray.getJSONObject(i);
                                                        Log.d(TAG, "onCreate1: " + subformJsonObject.has(controlName));
                                                        if (subformJsonObject.has(controlName)) {
                                                            List<List<ControlObject>> controlsListSF = new ArrayList<List<ControlObject>>();
                                                            List<List<ControlObject>> gpsControlListSF = new ArrayList<List<ControlObject>>();
                                                            List<List<ControlObject>> imageControlListSF = new ArrayList<List<ControlObject>>();
                                                            List<List<ControlObject>> videoControlsListSF = new ArrayList<List<ControlObject>>();
                                                            List<List<ControlObject>> audioControlsListSF = new ArrayList<List<ControlObject>>();


                                                            //--subform name--//
                                                            Log.d(TAG, "onCreate2: " + subformJsonObject.get(controlName));
                                                            JSONArray subformJsonArray = subformJsonObject.getJSONArray(controlName);
                                                            for (int j = 0; j < subformJsonArray.length(); j++) {

                                                                List<ControlObject> controlsListRow = new ArrayList<>();
                                                                List<ControlObject> gpsControlListRow = new ArrayList<>();
                                                                List<ControlObject> imageControlListRow = new ArrayList<>();
                                                                List<ControlObject> videoControlsListRow = new ArrayList<>();
                                                                List<ControlObject> audioControlsListRow = new ArrayList<>();

                                                                JSONObject jsonObject = subformJsonArray.getJSONObject(j);
                                                                for (int k = 0; k < jsonObject.names().length(); k++) {
                                                                    String paramName = jsonObject.names().getString(k);
                                                                    String paramValue = jsonObject.getString(paramName);
                                                                    if (!paramName.equalsIgnoreCase("Trans_id") &&
                                                                            !paramName.contentEquals("SubForm") &&
                                                                            !paramName.endsWith("Trans_ID") &&
                                                                            !paramName.endsWith("_emp_id") &&
                                                                            !paramName.endsWith("_postid") &&
                                                                            !paramName.endsWith("_trans_date")) {

                                                                        ControlObject controlObject = getSubFormControlObject(paramName, controlName);
                                                                        if (controlObject != null) {

                                                                            controlObject.setControlTitle(AppConstants.CONTROL_TYPE_SUBFORM);
                                                                            controlObject.setControlValue(paramValue);

                                                                            if (controlObject.getControlType() != null) {
                                                                                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                                                                                    controlsListRow.add(new ControlObject(controlObject));
                                                                                } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE)) {
                                                                                    controlsListRow.add(new ControlObject(controlObject));
                                                                                } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                                                                                    controlsListRow.add(new ControlObject(controlObject));
                                                                                } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_RECORDING)) {
                                                                                    controlsListRow.add(new ControlObject(controlObject));
                                                                                } else {
                                                                                    controlsListRow.add(new ControlObject(controlObject));
                                                                                }
                                                                            } else {
//                                                        controlsListRow.add(controlObject);
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                gpsControlListSF.add(gpsControlListRow);
                                                                imageControlListSF.add(imageControlListRow);
                                                                audioControlsListSF.add(audioControlsListRow);
                                                                videoControlsListSF.add(videoControlsListRow);
                                                                controlsListSF.add(controlsListRow);

                                                            }

                                                            subformControlsList.put(controlName, gpsControlListSF);
                                                            subformControlsList.put(controlName, imageControlListSF);
                                                            subformControlsList.put(controlName, audioControlsListSF);
                                                            subformControlsList.put(controlName, videoControlsListSF);
                                                            subformControlsList.put(controlName, controlsListSF);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (fromActivity != null && fromActivity.equalsIgnoreCase("ViewData")) {
                                        for (int i = 0; i < queryDetailsObject.names().length(); i++) {
                                            String controlName = queryDetailsObject.names().getString(i);
                                            String controlValue = queryDetailsObject.getString(controlName);

                                            if (!controlName.equalsIgnoreCase("Trans_id") &&
                                                    !controlName.contentEquals("SubForm") &&
                                                    !controlName.endsWith("Trans_ID") &&
                                                    !controlName.endsWith("_emp_id") &&
                                                    !controlName.endsWith("_postid") &&
                                                    !controlName.endsWith("_trans_date")) {

                                                ControlObject controlObject = getControlObject(controlName);
                                                if (controlObject != null && !controlObject.isInvisible()) {
                                                    if (controlObject.isEnableImageWithGps()) {
                                                        i++;
                                                    }
                                                    controlObject.setControlValue(controlValue);

                                                    if (controlObject.getControlType() != null) {

                                                        if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                                                            controlsList.add(controlObject);
                                                        } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE)) {
                                                            controlsList.add(controlObject);
                                                        } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                                                            controlsList.add(controlObject);
                                                        } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_RECORDING)) {
                                                            controlsList.add(controlObject);
                                                        } else {
                                                            controlsList.add(controlObject);
                                                        }
                                                    } else {
                                                        controlsList.add(controlObject);
                                                    }
                                                }
                                            } else if (controlName.contentEquals("SubForm")) {

                                                subFormsArray = new JSONArray();
                                                JSONArray subFormArray = new JSONArray();
                                                subFormArray = queryDetailsObject.getJSONArray("SubForm");
                                                for (int j = 0; j < subFormArray.length(); j++) {
                                                    JSONObject subformJsonObject = subFormArray.getJSONObject(j);
                                                    List<List<ControlObject>> controlsListSF = new ArrayList<List<ControlObject>>();
                                                    List<List<ControlObject>> gpsControlListSF = new ArrayList<List<ControlObject>>();
                                                    List<List<ControlObject>> imageControlListSF = new ArrayList<List<ControlObject>>();
                                                    List<List<ControlObject>> videoControlsListSF = new ArrayList<List<ControlObject>>();
                                                    List<List<ControlObject>> audioControlsListSF = new ArrayList<List<ControlObject>>();


                                                    String arrayParamName = subformJsonObject.names().getString(0);
                                                    JSONArray subformJsonArray = subformJsonObject.getJSONArray(arrayParamName);


//                                    JSONArray subformJsonArray = subformJsonObject.getJSONArray(controlName);
                                                    for (int k = 0; k < subformJsonArray.length(); k++) {

                                                        List<ControlObject> controlsListRow = new ArrayList<>();
                                                        List<ControlObject> gpsControlListRow = new ArrayList<>();
                                                        List<ControlObject> imageControlListRow = new ArrayList<>();
                                                        List<ControlObject> videoControlsListRow = new ArrayList<>();
                                                        List<ControlObject> audioControlsListRow = new ArrayList<>();

                                                        JSONObject jsonObject = subformJsonArray.getJSONObject(k);
                                                        for (int l = 0; l < jsonObject.names().length(); l++) {
                                                            String paramName = jsonObject.names().getString(l);
                                                            String paramValue = jsonObject.getString(paramName);
                                                            if (!paramName.equalsIgnoreCase("Trans_id") &&
                                                                    !paramName.contentEquals("SubForm") &&
                                                                    !paramName.endsWith("Trans_ID") &&
                                                                    !paramName.endsWith("_emp_id") &&
                                                                    !paramName.endsWith("_postid") &&
                                                                    !paramName.endsWith("_trans_date")) {

                                                                ControlObject controlObject = getSubFormControlObject(paramName, arrayParamName);
                                                                if (controlObject != null && !controlObject.isInvisible()) {

                                                                    controlObject.setControlTitle(AppConstants.CONTROL_TYPE_SUBFORM);
                                                                    controlObject.setControlValue(paramValue);

                                                                    if (controlObject.getControlType() != null) {
                                                                        if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS) || controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_MAP)) {
                                                                            controlsListRow.add(new ControlObject(controlObject));
                                                                        } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE) || controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_IMAGE)) {
                                                                            controlsListRow.add(new ControlObject(controlObject));
                                                                        } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VOICE_RECORDING) || controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_Video_Play)) {
                                                                            controlsListRow.add(new ControlObject(controlObject));
                                                                        } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_RECORDING) || controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_Audio_Play)) {
                                                                            controlsListRow.add(new ControlObject(controlObject));
                                                                        } else {
                                                                            controlsListRow.add(new ControlObject(controlObject));
                                                                        }
                                                                    } else {
//                                                        controlsListRow.add(controlObject);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        gpsControlListSF.add(gpsControlListRow);
                                                        imageControlListSF.add(imageControlListRow);
                                                        audioControlsListSF.add(audioControlsListRow);
                                                        videoControlsListSF.add(videoControlsListRow);
                                                        controlsListSF.add(controlsListRow);

                                                    }

                                                    subformControlsList.put(arrayParamName, gpsControlListSF);
                                                    subformControlsList.put(arrayParamName, imageControlListSF);
                                                    subformControlsList.put(arrayParamName, audioControlsListSF);
                                                    subformControlsList.put(arrayParamName, videoControlsListSF);
                                                    subformControlsList.put(arrayParamName, controlsListSF);

                                                }
                                            }
                                        }
                                    } else {
                                        for (int i = 0; i < queryDetailsObject.names().length(); i++) {
                                            String controlName = queryDetailsObject.names().getString(i);
                                            String controlValue = queryDetailsObject.getString(controlName);

                                            if (!controlName.equalsIgnoreCase("Trans_id") &&
                                                    !controlName.contentEquals("SubForm") &&
                                                    !controlName.endsWith("Trans_ID") &&
                                                    !controlName.endsWith("_emp_id") &&
                                                    !controlName.endsWith("_postid") &&
                                                    !controlName.endsWith("_trans_date")) {

                                                ControlObject controlObject = getControlObject(controlName);
                                                if (controlObject != null) {
                                                    controlObject.setControlValue(controlValue);

                                                    if (controlObject.getControlType() != null) {

                                                        if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)
                                                                || (controlObject.getFieldDisplayFormat() != null && controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_MAP))) {
                                                            gpsControlList.add(controlObject);
                                                        } else if ((controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE))
                                                                || (controlObject.getFieldDisplayFormat() != null && controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_IMAGE))) {
                                                            imageControlList.add(controlObject);
                                                        } else if ((controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VOICE_RECORDING))
                                                                || (controlObject.getFieldDisplayFormat() != null && controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_Video_Play))) {
                                                            audioControlsLIst.add(controlObject);
                                                        } else if ((controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_RECORDING))
                                                                || (controlObject.getFieldDisplayFormat() != null && controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_Audio_Play))) {
                                                            videoControlsList.add(controlObject);
                                                        } else {
                                                            controlsList.add(controlObject);
                                                        }
                                                    } else {
                                                        controlsList.add(controlObject);
                                                    }
                                                }
                                            } else if (controlName.contentEquals("SubForm")) {
                                                subFormsArray = new JSONArray();
                                                subFormsArray = queryDetailsObject.getJSONArray("SubForm");
                                            }
                                        }
                                    }

                                    allControlsList.addAll(gpsControlList);
                                    allControlsList.addAll(videoControlsList);
                                    allControlsList.addAll(imageControlList);
                                    allControlsList.addAll(audioControlsLIst);
                                    allControlsList.addAll(controlsList);

                                    llDetails.removeAllViews();

                                    for (int i = 0; i < allControlsList.size(); i++) {
                                        if (allControlsList.get(i).getControlValue() != null) {
                                            try {
                                                ControlObject co = setDataAccordingToLang(allControlsList.get(i));
                                                new AddViewAsync(co).execute();
                                            } catch (Exception e) {
                                                Log.getStackTraceString(e);
                                            }
                                        }
                                    }
                                    if (subformControlsList.size() > 0) {
                                        for (Map.Entry<String, List<List<ControlObject>>> entry : subformControlsList.entrySet()) {
                                            if (entry != null) {
                                                try {
                                                    new AddSubformViewAsync(entry.getKey(), entry.getValue()).execute();
                                                } catch (Exception e) {
                                                    Log.d(TAG, "onCreate: " + e);
                                                }
                                            }
                                        }
                                    }

                                    if (subFormsArray.length() > 0) {
                                        for (int i = 0; i < subFormsArray.length(); i++) {
                                            JSONObject sfMainObject = subFormsArray.getJSONObject(i);
                                            String arrayParamName = sfMainObject.names().getString(0);
                                            JSONArray jsonArray = sfMainObject.getJSONArray(arrayParamName);
                                            if (jsonArray.length() > 0) {
                                                ControlObject controlObject = new ControlObject();
                                                controlObject.setControlType(AppConstants.CONTROL_TYPE_DATA_TABLE);
                                                controlObject.setControlName(arrayParamName);
                                                controlObject.setDisplayName(arrayParamName);
//                                                controlObject.setDataTableRowHeight("60");
//                                                controlObject.setDataTableRowHeightWrapContent(false);
                                                DataTableControl dataTableControl = new DataTableControl(QueryDetailsActivity.this, controlObject, false, -1, "");
                                                llDetails.addView(dataTableControl.getDataTableLayout());
                                                ActionWithoutCondition_Bean actionWithoutCondition_bean = new ActionWithoutCondition_Bean();
                                                List<DataTableColumn_Bean> dataTableColumn_beans = new ArrayList<>();
                                                LinkedHashMap<String, List<String>> outputData = new LinkedHashMap<>();
                                                List<String> columnsList = new ArrayList<>();
                                                for (int j = 0; j < jsonArray.length(); j++) {
                                                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                                                    for (int k = 0; k < jsonObject.names().length(); k++) {
                                                        String paramName = jsonObject.names().getString(k);
                                                        String paramValue = jsonObject.getString(paramName);
                                                        if (!jsonObject.names().getString(k).endsWith("TransID") &&
                                                                !jsonObject.names().getString(k).endsWith("Trans_ID") &&
                                                                !jsonObject.names().getString(k).endsWith("_emp_id") &&
                                                                !jsonObject.names().getString(k).endsWith("_postid") &&
                                                                !jsonObject.names().getString(k).endsWith("_trans_date")) {//_emp_id,//_postid,//_trans_date
                                                            List<String> list = new ArrayList<>();
                                                            if (outputData.containsKey(paramName)) {
                                                                list = outputData.get(paramName);
                                                            }
                                                            list.add(paramValue);
                                                            outputData.put(paramName, list);
                                                        }
                                                    }
                                                }
                                                if (outputData.size() > 0) {
                                                    columnsList.addAll(outputData.keySet());
                                                    actionWithoutCondition_bean.setDataTableFixedWidthEnabled(columnsList.size() > 0 && columnsList.size() < 4);
                                                    for (int j = 0; j < columnsList.size(); j++) {
                                                        DataTableColumn_Bean column_bean = new DataTableColumn_Bean(columnsList.get(j), columnsList.get(j), "None", true, "140");
                                                        dataTableColumn_beans.add(column_bean);
                                                    }
                                                    actionWithoutCondition_bean.setDataTableColumn_beanList(dataTableColumn_beans);
                                                   //nk realm: dataTableControl.setDataTableData(actionWithoutCondition_bean, outputData, columnsList);
                                                    dataTableControl.setDataTableData(actionWithoutCondition_bean,outputData);
                                                }
                                            }
                                        }
                                    }
                                }


                            } catch (Exception e) {
                                Log.getStackTraceString(e);
                                e.printStackTrace();
                            }
                        }
                    });

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/

        @Override
        protected void onPostExecute(String file_url) {
//            dismissProgressDialog();

        }
    }

    private class AddSubformViewAsync extends AsyncTask<String, String, String> {
        String subformName;
        List<List<ControlObject>> controlObjectList;

        public AddSubformViewAsync(String subformName, List<List<ControlObject>> controlObjectList) {

            this.subformName = subformName;
            this.controlObjectList = controlObjectList;
        }

        @Override
        protected String doInBackground(String... strings) {

            new Handler(Looper.getMainLooper()).post(
                    new Runnable() {
                        @Override
                        public void run() {
                            LayoutInflater inflaterSubForm = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View subFormView = inflaterSubForm.inflate(R.layout.layout_subform_index_detailed, null);
                            CustomTextView subFormName = subFormView.findViewById(R.id.tv_item);
                            subFormName.setText(subformName);
                            subFormName.setCustomFont(context,getString(R.string.font_satoshi_bold));

                            LinearLayout subFormDataContainer= subFormView.findViewById(R.id.subFormDataContainer);

                            for (List<ControlObject> controlObjectRow : controlObjectList) {
                                LinearLayout subLinearLayoutRow = new LinearLayout(context);
                                subLinearLayoutRow.setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rectangle_subform_row));
                                LinearLayout.LayoutParams layoutParamsRow = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                layoutParamsRow.setMargins(5, 5, 5, 10);
                                subLinearLayoutRow.setPadding(15, 15, 15, 15);
                                subLinearLayoutRow.setLayoutParams(layoutParamsRow);
                                subLinearLayoutRow.setOrientation(LinearLayout.VERTICAL);

                                for (ControlObject controlObject : controlObjectRow) {
                                   LayoutInflater lInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                   View view = lInflater.inflate(R.layout.index_column, null);
//                                    final CustomTextView columnName = view.findViewById(R.id.columnName);
                                    CustomTextView columnValue = view.findViewById(R.id.columnValue);
                                    ImageView iv_call = view.findViewById(R.id.iv_call);
                                    iv_call.setVisibility(View.GONE);

//                                    LinearLayout llRow = view.findViewById(R.id.ll_row);
                                    ControlObject co = setDataAccordingToLang(controlObject);
//                                    columnName.setText(co.getDisplayName());
                                    columnValue.setText(co.getControlValue());


                                    if (co.getControlType() != null) {

                                        if (co.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {

                                            LayoutInflater gpsInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                            View gpsView = gpsInflater.inflate(R.layout.map_view, null);

                                            FrameLayout mapLayout = gpsView.findViewById(R.id.mapLayout);
                                            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);

                                            mapFragment.getMapAsync(new OnMapReadyCallback() {
                                                @Override
                                                public void onMapReady(final GoogleMap googleMap) {

                                                    googleMap.getUiSettings().setZoomControlsEnabled(true);

                                                    googleMap.getUiSettings().setAllGesturesEnabled(true);

                                                    googleMap.getUiSettings().setMapToolbarEnabled(false);

                                                    final LatLngBounds.Builder builder = new LatLngBounds.Builder();

                                                    String gpsString = co.getControlValue();
                                                    if (gpsString != null && !gpsString.trim().equalsIgnoreCase("")) {
                                                        Double lat = 0.0;
                                                        Double lng = 0.0;

                                                        if (gpsString.contains("^") && !gpsString.contains("$")) {
                                                            lat = Double.parseDouble(gpsString.split("\\^")[0]);
                                                            lng = Double.parseDouble(gpsString.split("\\^")[1]);
                                                        } else if (gpsString.contains("^") && gpsString.contains("$")) {

                                                        } else {
                                                            lat = Double.parseDouble(gpsString.split("\\$")[0]);
                                                            lng = Double.parseDouble(gpsString.split("\\$")[1]);

                                                        }

                                                        LatLng latLng = new LatLng(lat, lng);

                                                        Marker marker = googleMap.addMarker(new MarkerOptions()
                                                                .position(latLng)
                                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

                                                        builder.include(marker.getPosition());
                                                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

                                                    }

                                                }
                                            });

                                            columnValue.setText("");
                                            subLinearLayoutRow.addView(mapLayout);


                                        } else if (co.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE)
                                                || co.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA)) {

                                            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                            View imgView = inflater.inflate(R.layout.image_view, null);

                                            CustomTextView tv_displayName = imgView.findViewById(R.id.tv_displayName);
                                            tv_displayName.setText(co.getDisplayName());
                                            ImageView imageView = imgView.findViewById(R.id.imageView);

                                            if (co.getControlValue() != null && !co.getControlValue().equalsIgnoreCase("null")) {
                                                Glide.with(context).load(co.getControlValue()).into(imageView);

                                                columnValue.setText("");
                                                subLinearLayoutRow.addView(imgView);
                                            } else {
                                                imageView.setVisibility(View.GONE);
                                                columnValue.setText("Image not available");
                                            }


                                        } else if (co.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER)
                                                || co.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                                            co.setDisplayNameAlignment("9");
                                            co.setTextHexColor("#707b8a");
                                            AudioPlayer  audioPlayer_ = new AudioPlayer(QueryDetailsActivity.this, co, false, 0, "");
                                            columnValue.setText("");
                                            subLinearLayoutRow.addView(audioPlayer_.getAudioPlayerView());


                                        } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_FILE_BROWSING)) {
                                            co.setDisplayNameAlignment("9");
                                            co.setTextHexColor("#707b8a");
                                            ViewFileControl viewFileControl = new ViewFileControl(QueryDetailsActivity.this, co, false, 0, "");
                                            viewFileControl.setFileLink(co.getControlValue());
                                            columnValue.setText("");
                                            subLinearLayoutRow.addView(viewFileControl.getViewFileLayout());

                                        }  else if (co.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER)
                                                || co.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_RECORDING)) {

                                            co.setVideoData(co.getControlValue());
                                            controlObject.setTextHexColor("#707b8a");
                                            controlObject.setDisplayNameAlignment("9");
                                            VideoPlayer videoPlayer = new VideoPlayer(QueryDetailsActivity.this, co, false, 0, "");
                                            columnValue.setText("");
                                            subLinearLayoutRow.addView(videoPlayer.getVideoPlayerView());
                                        } else if (co.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_BAR_CODE)) {


                                            BarCode barCode = new BarCode(QueryDetailsActivity.this, co);

                                            columnValue.setText("");

                                            subLinearLayoutRow.addView(view);

                                            subLinearLayoutRow.addView(barCode.getBarCode());

                                        } else if (co.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_QR_CODE)) {


                                            QRCode qrCode = new QRCode(QueryDetailsActivity.this, co);

                                            columnValue.setText("");

                                            subLinearLayoutRow.addView(view);

                                            subLinearLayoutRow.addView(qrCode.getQRCode());

                                        } else if (co.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SIGNATURE)) {

                                            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            View imgView = inflater.inflate(R.layout.signature_view, null);
                                            CustomTextView tv_displayName = imgView.findViewById(R.id.tv_displayName);
                                            tv_displayName.setText(co.getDisplayName());
                                            ImageView imageView = imgView.findViewById(R.id.imageView);
                                            if (co.getControlValue() != null && !co.getControlValue().equalsIgnoreCase("null")) {
                                                Glide.with(context).load(co.getControlValue()).into(imageView);
                                                columnValue.setText("");
                                                subLinearLayoutRow.addView(view);
                                                subLinearLayoutRow.addView(imgView);
                                            } else {
                                                imageView.setVisibility(View.GONE);
                                                columnValue.setText("Signature not available");
                                                subLinearLayoutRow.addView(view);
                                            }
                                        } else {
                                            if (co.getFieldDisplayFormat() != null) {
                                                if (co.getFieldDisplayFormat().equalsIgnoreCase("Plain_Text")) {
                                                    if (co.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SUBFORM)) {
                                                    } else {
                                                        co.setDisplayNameAlignment("9");
                                                    }
                                                    if (co.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CALENDER)) {

                                                        String strCalendarValue = co.getControlValue();
                                                        if (strCalendarValue != null && !strCalendarValue.isEmpty()) {
                                                            co.setControlValue(strCalendarValue);
                                                        }
                                                    }
                                                    co.setControlType(AppConstants.CONTROL_TYPE_DYNAMIC_LABEL);
                                                    co.setValue(replaceSpecialCharacters(co.getControlValue()));
                                                    DynamicLabel dynamicLabel = new DynamicLabel(QueryDetailsActivity.this, co, false, -1, null);
//                                                    llRow.setVisibility(View.GONE);
                                                    subLinearLayoutRow.addView(dynamicLabel.getDynamicLabel());
                                                } else if (co.getFieldDisplayFormat().equalsIgnoreCase("QR_Code")) {

                                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                                    View imgView = inflater.inflate(R.layout.image_view, null);

                                                    ImageView imageView = imgView.findViewById(R.id.imageView);
                                                    QRCode.createQRCode(QRcodeDefaultValue, imageView);
                                                    if (!co.getControlValue().contentEquals("")) {
                                                        QRCode.createQRCode(co.getControlValue(), imageView);
                                                    } else {
                                                        ImproveHelper.showToast(context, "QRCode is empty");
                                                    }

                                                    columnValue.setText("");

                                                    subLinearLayoutRow.addView(view);

                                                    subLinearLayoutRow.addView(imgView);

                                                } else if (co.getFieldDisplayFormat().equalsIgnoreCase("BAR_Code")) {

                                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                                    View imgView = inflater.inflate(R.layout.image_view, null);

                                                    ImageView imageView = imgView.findViewById(R.id.imageView);

                                                    BarCode.createBarCode(QRcodeDefaultValue, imageView);
                                                    if (!co.getControlValue().contentEquals("")) {
                                                        BarCode.createBarCode(co.getControlValue(), imageView);
                                                    } else {
                                                        ImproveHelper.showToast(context, "BarCode is empty");
                                                    }

                                                    columnValue.setText("");

                                                    subLinearLayoutRow.addView(view);

                                                    subLinearLayoutRow.addView(imgView);

                                                } else if (co.getFieldDisplayFormat().equalsIgnoreCase("Phone_Number")) {

                                                    subLinearLayoutRow.addView(view);
                                                    iv_call.setVisibility(View.VISIBLE);
                                                    iv_call.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + columnValue.getText().toString().trim()));
                                                            startActivity(intent);
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                }
                                subFormDataContainer.addView(subLinearLayoutRow);
                            }
                            llDetails.addView(subFormView);
                        }
                    });
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dismissProgressDialog();
            scrollView.fullScroll(ScrollView.FOCUS_UP);
        }
    }

    private class AddViewAsync extends AsyncTask<String, String, String> {

        ControlObject controlObject;

        public AddViewAsync(ControlObject controlObject) {

            this.controlObject = controlObject;
        }

        /**
         * Downloading file in background thread
         */

        @Override
        protected String doInBackground(String... f_url) {
            new Handler(Looper.getMainLooper()).post(
                    new Runnable() {
                        @Override
                        public void run() {

                            LayoutInflater lInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                            View view = lInflater.inflate(R.layout.index_column, null);

                            final CustomTextView columnName = view.findViewById(R.id.columnName);
                            CustomTextView columnValue = view.findViewById(R.id.columnValue);
                            ImageView iv_call = view.findViewById(R.id.iv_call);
                            iv_call.setVisibility(View.GONE);

                            LinearLayout llRow = view.findViewById(R.id.ll_row);
                            columnName.setText(controlObject.getDisplayName());
                            columnValue.setText(controlObject.getControlValue());

                            if (fromActivity != null && fromActivity.equalsIgnoreCase("ViewData")) {
                                if (controlObject.getControlType() != null) {

                                    if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                                        LayoutInflater gpsInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View gpsView = gpsInflater.inflate(R.layout.map_view, null);
                                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);

                                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                                            @Override
                                            public void onMapReady(final GoogleMap googleMap) {

                                                googleMap.getUiSettings().setZoomControlsEnabled(true);

                                                googleMap.getUiSettings().setAllGesturesEnabled(true);

                                                googleMap.getUiSettings().setMapToolbarEnabled(false);

                                                final LatLngBounds.Builder builder = new LatLngBounds.Builder();

                                                String gpsString = controlObject.getControlValue();
                                                if (gpsString != null && !gpsString.trim().equalsIgnoreCase("")) {
                                                    Double lat = 0.0;
                                                    Double lng = 0.0;

                                                    if (gpsString.contains("^") && !gpsString.contains("$")) {
                                                        lat = Double.parseDouble(gpsString.split("\\^")[0]);
                                                        lng = Double.parseDouble(gpsString.split("\\^")[1]);
                                                    } else if (gpsString.contains("^") && gpsString.contains("$")) {

                                                    } else {
                                                        if (gpsString != null) {
                                                            lat = Double.parseDouble(gpsString.split("\\$")[0]);
                                                            lng = Double.parseDouble(gpsString.split("\\$")[1]);
                                                        } else {
                                                            lat = 0.0;
                                                            lng = 0.0;
                                                        }

                                                    }

                                                    LatLng latLng = new LatLng(lat, lng);

                                                    Marker marker = googleMap.addMarker(new MarkerOptions()
                                                            .position(latLng)
                                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

                                                    builder.include(marker.getPosition());
                                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

                                                }

                                            }
                                        });

                                        columnValue.setText("");

                                        //llDetails.addView(view);

                                        llDetails.addView(gpsView);


                                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE)
                                            || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA)) {

                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                        View imgView = inflater.inflate(R.layout.image_view, null);

                                        ImageView imageView = imgView.findViewById(R.id.imageView);
                                        CustomTextView tv_displayName = imgView.findViewById(R.id.tv_displayName);
                                        tv_displayName.setText(controlObject.getDisplayName());
                                        if (controlObject.getControlValue() != null && !controlObject.getControlValue().equalsIgnoreCase("null")) {
                                            Glide.with(context).load(controlObject.getControlValue()).into(imageView);

                                            columnValue.setText("");
                                            llDetails.addView(imgView);
                                        } else {
                                            imageView.setVisibility(View.GONE);
                                            columnValue.setText("Image not available");

                                        }


                                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER)
                                            || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                                        controlObject.setDisplayNameAlignment("9");
                                        controlObject.setTextHexColor("#707b8a");
                                        AudioPlayer audioPlayer_ = new AudioPlayer(QueryDetailsActivity.this, controlObject, false, 0, "");
                                        columnValue.setText("");
                                        llDetails.addView(audioPlayer_.getAudioPlayerView());
                                        audioPlayerList.add(audioPlayer_);
                                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_FILE_BROWSING)) {
                                        controlObject.setDisplayNameAlignment("9");
                                        controlObject.setTextHexColor("#707b8a");
                                        ViewFileControl viewFileControl = new ViewFileControl(QueryDetailsActivity.this, controlObject, false, 0, "");
                                        viewFileControl.setFileLink(controlObject.getControlValue());
                                        columnValue.setText("");
                                        llDetails.addView(viewFileControl.getViewFileLayout());

                                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER)
                                            || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_RECORDING)) {
                                        controlObject.setVideoData(controlObject.getControlValue());
                                        controlObject.setTextHexColor("#707b8a");
                                        controlObject.setDisplayNameAlignment("9");
                                        VideoPlayer videoPlayer = new VideoPlayer(QueryDetailsActivity.this, controlObject, false, 0, "");

                                        columnValue.setText("");

//                                        llDetails.addView(view);

                                        llDetails.addView(videoPlayer.getVideoPlayerView());


                                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_BAR_CODE)) {

                                        BarCode barCode = new BarCode(QueryDetailsActivity.this, controlObject);

                                        columnValue.setText("");

//                                        llDetails.addView(view);

                                        llDetails.addView(barCode.getBarCode());

                                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_QR_CODE)) {
                                        QRCode qrCode = new QRCode(QueryDetailsActivity.this, controlObject);

                                        columnValue.setText("");

//                                        llDetails.addView(view);

                                        llDetails.addView(qrCode.getQRCode());

                                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SIGNATURE)) {

                                        /*ImageView imageView = new ImageView(context);
                                        Glide.with(context).load(controlObject.getControlValue()).into(imageView);
                                        columnValue.setText("");
                                        llDetails.addView(imageView);*/
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        View imgView = inflater.inflate(R.layout.signature_view, null);
                                        ImageView imageView = imgView.findViewById(R.id.imageView);
                                        CustomTextView tv_displayName = imgView.findViewById(R.id.tv_displayName);
                                        tv_displayName.setText(controlObject.getDisplayName());
                                        if (controlObject.getControlValue() != null && !controlObject.getControlValue().equalsIgnoreCase("null")) {
                                            Glide.with(context).load(controlObject.getControlValue()).into(imageView);
                                            columnValue.setText("");
                                            llDetails.addView(imgView);
                                        } else {
                                            imageView.setVisibility(View.GONE);
                                            columnValue.setText("Image not available");
}

                                    } else if (controlObject.getFieldDisplayFormat() != null) {
                                        if (controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_TEXT)) {
                                            if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SUBFORM)) {
                                            } else {
                                                controlObject.setDisplayNameAlignment("9");
                                            }
                                            if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CALENDER)) {
                                                /*String[] defaultValue = controlObject.getControlValue().split("T");
                                                if (defaultValue[0].contains("-")) {
                                                    String enteredValue = parseDateToddMMyyyy(defaultValue[0]);
                                                    controlObject.setControlValue(enteredValue);
                                                } else {
                                                    controlObject.setControlValue(defaultValue[0]);
                                                }*/
                                                String strCalendarValue = controlObject.getControlValue();
                                                if (strCalendarValue != null && !strCalendarValue.isEmpty()) {
//                                                    if(strCalendarValue.length()>9 &&strCalendarValue.charAt(10) == 'T'){
//                                                        String[] defaultValue = strCalendarValue.split("T");
//                                                        String enteredValue = parseDateToddMMyyyy(defaultValue[0]);
//                                                        controlObject.setControlValue(enteredValue);
//                                                    }else{
                                                    controlObject.setControlValue(strCalendarValue);
//                                                    }
                                                }
                                            }

                                            controlObject.setControlType(AppConstants.CONTROL_TYPE_DYNAMIC_LABEL);
                                            controlObject.setValue(replaceSpecialCharacters(controlObject.getControlValue()));
                                            DynamicLabel dynamicLabel = new DynamicLabel(QueryDetailsActivity.this, controlObject, false, -1, null);
                                            llRow.setVisibility(View.GONE);

                                            llDetails.addView(dynamicLabel.getDynamicLabel());

                                        }
                                    }
                                }
                            } else {

                                if (controlObject.getFieldDisplayFormat() != null) {
                                    if (controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_TEXT)) {
                                        if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SUBFORM)) {
                                        } else {
                                            controlObject.setDisplayNameAlignment("6");
                                        }
                                        if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CALENDER)) {
                                           /* String[] defaultValue = controlObject.getControlValue().split("T");
                                            if (defaultValue[0].contains("-")) {
                                                String enteredValue = parseDateToddMMyyyy(defaultValue[0]);
                                                controlObject.setControlValue(enteredValue);
                                            } else {
                                                controlObject.setControlValue(defaultValue[0]);
                                            }*/
                                            String strCalendarValue = controlObject.getControlValue();
                                            if (strCalendarValue != null && !strCalendarValue.isEmpty()) {
//                                                if(strCalendarValue.length()>9 &&strCalendarValue.charAt(10) == 'T'){
//                                                    String[] defaultValue = strCalendarValue.split("T");
//                                                    String enteredValue = parseDateToddMMyyyy(defaultValue[0]);
//                                                    controlObject.setControlValue(enteredValue);
//                                                }else{
                                                controlObject.setControlValue(strCalendarValue);
//                                                }
                                            }
                                        }

                                        controlObject.setControlType(AppConstants.CONTROL_TYPE_DYNAMIC_LABEL);

                                        controlObject.setValue(replaceSpecialCharacters(controlObject.getControlValue()));
                                        DynamicLabel dynamicLabel = new DynamicLabel(QueryDetailsActivity.this, controlObject, false, -1, null);

                                        llRow.setVisibility(View.GONE);
                                        if (dynamicLabel.getDivider() != null) {
                                            View dividerView = dynamicLabel.getDivider();
                                            dividerView.setBackgroundColor(Color.parseColor("#22000000"));
                                        }
                                        llDetails.addView(dynamicLabel.getDynamicLabel());

                                    } else if (controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_QRCODE)) {

                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                        View imgView = inflater.inflate(R.layout.image_view, null);

                                        ImageView imageView = imgView.findViewById(R.id.imageView);
                                        QRCode.createQRCode(QRcodeDefaultValue, imageView);
                                        if (!controlObject.getControlValue().contentEquals("")) {
                                            QRCode.createQRCode(controlObject.getControlValue(), imageView);
                                        } else {
                                            ImproveHelper.showToast(context, "QRCode is empty");
                                        }

                                        columnValue.setText("");

//                                        llDetails.addView(view);

                                        llDetails.addView(imgView);

                                    } else if (controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_BARCODE)) {

                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                        View imgView = inflater.inflate(R.layout.image_view, null);

                                        ImageView imageView = imgView.findViewById(R.id.imageView);

                                        BarCode.createBarCode(QRcodeDefaultValue, imageView);
                                        if (!controlObject.getControlValue().contentEquals("")) {
                                            BarCode.createBarCode(controlObject.getControlValue(), imageView);
                                        } else {
                                            ImproveHelper.showToast(context, "BarCode is empty");
                                        }

                                        columnValue.setText("");

//                                        llDetails.addView(view);

                                        llDetails.addView(imgView);

                                    } else if (controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_PHONE)) {

//                                        llDetails.addView(view);
                                        iv_call.setVisibility(View.VISIBLE);
                                        iv_call.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + columnValue.getText().toString().trim()));
                                                startActivity(intent);
                                            }
                                        });
                                    } else if (controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_Video_Play)) {

                                        controlObject.setVideoData(controlObject.getControlValue());

                                        VideoPlayer videoPlayer = new VideoPlayer(QueryDetailsActivity.this, controlObject, false, 0, "");

                                        columnValue.setText("");

//                                        llDetails.addView(view);

                                        llDetails.addView(videoPlayer.getVideoPlayerView());

                                    } else if (controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_Audio_Play)) {

                                        AudioPlayer audioPlayer_ = new AudioPlayer(QueryDetailsActivity.this, controlObject, false, 0, "");

                                        columnValue.setText("");

//                                        llDetails.addView(view);

                                        llDetails.addView(audioPlayer_.getAudioPlayerView());

                                    } else if (controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_IMAGE)) {

                                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                        View imgView = inflater.inflate(R.layout.image_view, null);

                                        ImageView imageView = imgView.findViewById(R.id.imageView);

                                        if (controlObject.getControlValue() != null && !controlObject.getControlValue().equalsIgnoreCase("null")) {
                                            Glide.with(context).load(controlObject.getControlValue()).into(imageView);

                                            columnValue.setText("");
//                                            llDetails.addView(view);

                                            llDetails.addView(imgView);
                                        } else {
                                            imageView.setVisibility(View.GONE);
                                            columnValue.setText("Image not available");
//                                            llDetails.addView(view);

                                        }

                                    } else if (controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_URL)) {

                                    } else if (controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_MAP)) {

                                        LayoutInflater gpsInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                        View gpsView = lInflater.inflate(R.layout.map_view, null);

                                        FrameLayout mapLayout = gpsView.findViewById(R.id.mapLayout);
                                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);

                                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                                            @Override
                                            public void onMapReady(final GoogleMap googleMap) {

                                                googleMap.getUiSettings().setZoomControlsEnabled(true);

                                                googleMap.getUiSettings().setAllGesturesEnabled(true);

                                                googleMap.getUiSettings().setMapToolbarEnabled(false);

                                                final LatLngBounds.Builder builder = new LatLngBounds.Builder();

                                                String gpsString = controlObject.getControlValue();
                                                if (gpsString != null && !gpsString.trim().equalsIgnoreCase("")) {
                                                    Double lat = 0.0;
                                                    Double lng = 0.0;

                                                    if (gpsString.contains("^") && !gpsString.contains("$")) {
                                                        lat = Double.parseDouble(gpsString.split("\\^")[0]);
                                                        lng = Double.parseDouble(gpsString.split("\\^")[1]);
                                                    } else if (gpsString.contains("^") && gpsString.contains("$")) {

                                                    } else {
                                                        if (gpsString != null) {
                                                            lat = Double.parseDouble(gpsString.split("\\$")[0]);
                                                            lng = Double.parseDouble(gpsString.split("\\$")[1]);
                                                        } else {
                                                            lat = 0.0;
                                                            lng = 0.0;
                                                        }

                                                    }

                                                    LatLng latLng = new LatLng(lat, lng);

                                                    Marker marker = googleMap.addMarker(new MarkerOptions()
                                                            .position(latLng)
                                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

                                                    builder.include(marker.getPosition());
                                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

                                                }

                                            }
                                        });

                                        columnValue.setText("");

                                        llDetails.addView(mapLayout);

                                    } else if (controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_RATING)) {

                                    }
                                }
                            }

                        }
                    });

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/

        @Override
        protected void onPostExecute(String file_url) {
            if (subformControlsList.size() == 0) {

                dismissProgressDialog();
            }
            scrollView.fullScroll(ScrollView.FOCUS_UP);
        }
    }
/*
    private void previewData(JSONObject queryDetailsObject) {
        try {
            List<String> viewPageColumnsOrder = new ArrayList<>();
            if (dataManagementOptions != null && dataManagementOptions.getViewPageColumnsOrder() != null) {
                viewPageColumnsOrder = dataManagementOptions.getViewPageColumnsOrder();
            }
            if (queryDetailsObject != null) {
//                queryDetailsObject = new JSONObject(getIntent().getStringExtra("jsonObject"));
                if (fromActivity != null && fromActivity.equalsIgnoreCase("ViewData") && viewPageColumnsOrder != null && viewPageColumnsOrder.size() > 0) {
                    for (String controlName : viewPageColumnsOrder) {
                        if (queryDetailsObject.has(controlName)) {
                            String controlValue = queryDetailsObject.getString(controlName);

                            if (!controlName.equalsIgnoreCase("Trans_id") &&
                                    !controlName.contentEquals("SubForm") &&
                                    !controlName.endsWith("Trans_ID") &&
                                    !controlName.endsWith("_emp_id") &&
                                    !controlName.endsWith("_postid") &&
                                    !controlName.endsWith("_trans_date")) {

                                ControlObject controlObject = getControlObject(controlName);
                                if (controlObject != null) {
                                    controlObject.setControlValue(controlValue);

                                    if (controlObject.getControlType() != null) {

                                        if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                                            gpsControlList.add(controlObject);
                                        } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE)) {
                                            imageControlList.add(controlObject);
                                        } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                                            audioControlsLIst.add(controlObject);
                                        } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_RECORDING)) {
                                            videoControlsList.add(controlObject);
                                        } else {
                                            controlsList.add(controlObject);
                                        }
                                    } else {
                                        controlsList.add(controlObject);
                                    }
                                }
                            }
                        } else {
                            JSONArray subFormArray = new JSONArray();
                            if (queryDetailsObject.has("SubForm")) {
                                subFormArray = queryDetailsObject.getJSONArray("SubForm");
                                for (int i = 0; i < subFormArray.length(); i++) {
                                    JSONObject subformJsonObject = subFormArray.getJSONObject(i);
                                    Log.d(TAG, "onCreate1: " + subformJsonObject.has(controlName));
                                    if (subformJsonObject.has(controlName)) {
                                        List<List<ControlObject>> controlsListSF = new ArrayList<List<ControlObject>>();
                                        List<List<ControlObject>> gpsControlListSF = new ArrayList<List<ControlObject>>();
                                        List<List<ControlObject>> imageControlListSF = new ArrayList<List<ControlObject>>();
                                        List<List<ControlObject>> videoControlsListSF = new ArrayList<List<ControlObject>>();
                                        List<List<ControlObject>> audioControlsListSF = new ArrayList<List<ControlObject>>();


                                        //--subform name--//
                                        Log.d(TAG, "onCreate2: " + subformJsonObject.get(controlName));
                                        JSONArray subformJsonArray = subformJsonObject.getJSONArray(controlName);
                                        for (int j = 0; j < subformJsonArray.length(); j++) {

                                            List<ControlObject> controlsListRow = new ArrayList<>();
                                            List<ControlObject> gpsControlListRow = new ArrayList<>();
                                            List<ControlObject> imageControlListRow = new ArrayList<>();
                                            List<ControlObject> videoControlsListRow = new ArrayList<>();
                                            List<ControlObject> audioControlsListRow = new ArrayList<>();

                                            JSONObject jsonObject = subformJsonArray.getJSONObject(j);
                                            for (int k = 0; k < jsonObject.names().length(); k++) {
                                                String paramName = jsonObject.names().getString(k);
                                                String paramValue = jsonObject.getString(paramName);
                                                if (!paramName.equalsIgnoreCase("Trans_id") &&
                                                        !paramName.contentEquals("SubForm") &&
                                                        !paramName.endsWith("Trans_ID") &&
                                                        !paramName.endsWith("_emp_id") &&
                                                        !paramName.endsWith("_postid") &&
                                                        !paramName.endsWith("_trans_date")) {

                                                    ControlObject controlObject = getSubFormControlObject(paramName, controlName);
                                                    if (controlObject != null) {

                                                        controlObject.setControlTitle(AppConstants.CONTROL_TYPE_SUBFORM);
                                                        controlObject.setControlValue(paramValue);

                                                        if (controlObject.getControlType() != null) {
                                                            if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                                                                gpsControlListRow.add(new ControlObject(controlObject));
                                                            } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE)) {
                                                                imageControlListRow.add(new ControlObject(controlObject));
                                                            } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                                                                audioControlsListRow.add(new ControlObject(controlObject));
                                                            } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_RECORDING)) {
                                                                videoControlsListRow.add(new ControlObject(controlObject));
                                                            } else {
                                                                controlsListRow.add(new ControlObject(controlObject));
                                                            }
                                                        } else {
//                                                        controlsListRow.add(controlObject);
                                                        }
                                                    }
                                                }
                                            }
                                            gpsControlListSF.add(gpsControlListRow);
                                            imageControlListSF.add(imageControlListRow);
                                            audioControlsListSF.add(audioControlsListRow);
                                            videoControlsListSF.add(videoControlsListRow);
                                            controlsListSF.add(controlsListRow);

                                        }

                                        subformControlsList.put(controlName, gpsControlListSF);
                                        subformControlsList.put(controlName, imageControlListSF);
                                        subformControlsList.put(controlName, audioControlsListSF);
                                        subformControlsList.put(controlName, videoControlsListSF);
                                        subformControlsList.put(controlName, controlsListSF);
                                    }
                                }
                            }
                        }
                    }
                } else if (fromActivity != null && fromActivity.equalsIgnoreCase("ViewData")) {
                    for (int i = 0; i < queryDetailsObject.names().length(); i++) {
                        String controlName = queryDetailsObject.names().getString(i);
                        String controlValue = queryDetailsObject.getString(controlName);

                        if (!controlName.equalsIgnoreCase("Trans_id") &&
                                !controlName.contentEquals("SubForm") &&
                                !controlName.endsWith("Trans_ID") &&
                                !controlName.endsWith("_emp_id") &&
                                !controlName.endsWith("_postid") &&
                                !controlName.endsWith("_trans_date")) {

                            ControlObject controlObject = getControlObject(controlName);
                            if (controlObject != null && !controlObject.isInvisible()) {
                                if (controlObject.isEnableImageWithGps()) {
                                    i++;
                                }
                                controlObject.setControlValue(controlValue);

                                if (controlObject.getControlType() != null) {

                                    if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                                        gpsControlList.add(controlObject);
                                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE)) {
                                        imageControlList.add(controlObject);
                                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                                        audioControlsLIst.add(controlObject);
                                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_RECORDING)) {
                                        videoControlsList.add(controlObject);
                                    } else {
                                        controlsList.add(controlObject);
                                    }
                                } else {
                                    controlsList.add(controlObject);
                                }
                            }
                        } else if (controlName.contentEquals("SubForm")) {

                            subFormsArray = new JSONArray();
                            JSONArray subFormArray = new JSONArray();
                            subFormArray = queryDetailsObject.getJSONArray("SubForm");
                            for (int j = 0; j < subFormArray.length(); j++) {
                                JSONObject subformJsonObject = subFormArray.getJSONObject(j);
                                List<List<ControlObject>> controlsListSF = new ArrayList<List<ControlObject>>();
                                List<List<ControlObject>> gpsControlListSF = new ArrayList<List<ControlObject>>();
                                List<List<ControlObject>> imageControlListSF = new ArrayList<List<ControlObject>>();
                                List<List<ControlObject>> videoControlsListSF = new ArrayList<List<ControlObject>>();
                                List<List<ControlObject>> audioControlsListSF = new ArrayList<List<ControlObject>>();


                                String arrayParamName = subformJsonObject.names().getString(0);
                                JSONArray subformJsonArray = subformJsonObject.getJSONArray(arrayParamName);


//                                    JSONArray subformJsonArray = subformJsonObject.getJSONArray(controlName);
                                for (int k = 0; k < subformJsonArray.length(); k++) {

                                    List<ControlObject> controlsListRow = new ArrayList<>();
                                    List<ControlObject> gpsControlListRow = new ArrayList<>();
                                    List<ControlObject> imageControlListRow = new ArrayList<>();
                                    List<ControlObject> videoControlsListRow = new ArrayList<>();
                                    List<ControlObject> audioControlsListRow = new ArrayList<>();

                                    JSONObject jsonObject = subformJsonArray.getJSONObject(k);
                                    for (int l = 0; l < jsonObject.names().length(); l++) {
                                        String paramName = jsonObject.names().getString(l);
                                        String paramValue = jsonObject.getString(paramName);
                                        if (!paramName.equalsIgnoreCase("Trans_id") &&
                                                !paramName.contentEquals("SubForm") &&
                                                !paramName.endsWith("Trans_ID") &&
                                                !paramName.endsWith("_emp_id") &&
                                                !paramName.endsWith("_postid") &&
                                                !paramName.endsWith("_trans_date")) {

                                            ControlObject controlObject = getSubFormControlObject(paramName, arrayParamName);
                                            if (controlObject != null && !controlObject.isInvisible()) {

                                                controlObject.setControlTitle(AppConstants.CONTROL_TYPE_SUBFORM);
                                                controlObject.setControlValue(paramValue);

                                                if (controlObject.getControlType() != null) {
                                                    if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS) || controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_MAP)) {
                                                        gpsControlListRow.add(new ControlObject(controlObject));
                                                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE) || controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_IMAGE)) {
                                                        imageControlListRow.add(new ControlObject(controlObject));
                                                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VOICE_RECORDING) || controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_Video_Play)) {
                                                        audioControlsListRow.add(new ControlObject(controlObject));
                                                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_RECORDING) || controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_Audio_Play)) {
                                                        videoControlsListRow.add(new ControlObject(controlObject));
                                                    } else {
                                                        controlsListRow.add(new ControlObject(controlObject));
                                                    }
                                                } else {
//                                                        controlsListRow.add(controlObject);
                                                }
                                            }
                                        }
                                    }
                                    gpsControlListSF.add(gpsControlListRow);
                                    imageControlListSF.add(imageControlListRow);
                                    audioControlsListSF.add(audioControlsListRow);
                                    videoControlsListSF.add(videoControlsListRow);
                                    controlsListSF.add(controlsListRow);

                                }

                                subformControlsList.put(arrayParamName, gpsControlListSF);
                                subformControlsList.put(arrayParamName, imageControlListSF);
                                subformControlsList.put(arrayParamName, audioControlsListSF);
                                subformControlsList.put(arrayParamName, videoControlsListSF);
                                subformControlsList.put(arrayParamName, controlsListSF);

                            }
                        }
                    }
                } else {
                    for (int i = 0; i < queryDetailsObject.names().length(); i++) {
                        String controlName = queryDetailsObject.names().getString(i);
                        String controlValue = queryDetailsObject.getString(controlName);

                        if (!controlName.equalsIgnoreCase("Trans_id") &&
                                !controlName.contentEquals("SubForm") &&
                                !controlName.endsWith("Trans_ID") &&
                                !controlName.endsWith("_emp_id") &&
                                !controlName.endsWith("_postid") &&
                                !controlName.endsWith("_trans_date")) {

                            ControlObject controlObject = getControlObject(controlName);
                            if (controlObject != null) {
                                controlObject.setControlValue(controlValue);

                                if (controlObject.getControlType() != null) {

                                    if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)
                                            || (controlObject.getFieldDisplayFormat() != null && controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_MAP))) {
                                        gpsControlList.add(controlObject);
                                    } else if ((controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE))
                                            || (controlObject.getFieldDisplayFormat() != null && controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_IMAGE))) {
                                        imageControlList.add(controlObject);
                                    } else if ((controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VOICE_RECORDING))
                                            || (controlObject.getFieldDisplayFormat() != null && controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_Video_Play))) {
                                        audioControlsLIst.add(controlObject);
                                    } else if ((controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_RECORDING))
                                            || (controlObject.getFieldDisplayFormat() != null && controlObject.getFieldDisplayFormat().equalsIgnoreCase(AppConstants.QC_FORMAT_Audio_Play))) {
                                        videoControlsList.add(controlObject);
                                    } else {
                                        controlsList.add(controlObject);
                                    }
                                } else {
                                    controlsList.add(controlObject);
                                }
                            }
                        } else if (controlName.contentEquals("SubForm")) {
                            subFormsArray = new JSONArray();
                            subFormsArray = queryDetailsObject.getJSONArray("SubForm");
                        }
                    }
                }

                allControlsList.addAll(gpsControlList);
                allControlsList.addAll(videoControlsList);
                allControlsList.addAll(imageControlList);
                allControlsList.addAll(audioControlsLIst);
                allControlsList.addAll(controlsList);

                llDetails.removeAllViews();

                for (int i = 0; i < allControlsList.size(); i++) {
                    if (allControlsList.get(i).getControlValue() != null) {
                        try {
//                            addView(allControlsList.get(i));
                            new AddViewAsync(allControlsList.get(i)).execute();
                        } catch (Exception e) {
                        }
                    }
                }
                if (subformControlsList.size() > 0) {
                    for (Map.Entry<String, List<List<ControlObject>>> entry : subformControlsList.entrySet()) {
                        if (entry != null) {
                            try {
//                                addSubFormView(entry.getKey(), entry.getValue());
                                new AddSubformViewAsync(entry.getKey(), entry.getValue()).execute();
                            } catch (Exception e) {
                                Log.d(TAG, "onCreate: " + e.toString());
                            }
                        }
                    }
                }

                if (subFormsArray.length() > 0) {
                    for (int i = 0; i < subFormsArray.length(); i++) {
                        JSONObject sfMainObject = subFormsArray.getJSONObject(i);
                        String arrayParamName = sfMainObject.names().getString(0);
                        JSONArray jsonArray = sfMainObject.getJSONArray(arrayParamName);
                        if (jsonArray.length() > 0) {
                            ControlObject controlObject = new ControlObject();
                            controlObject.setControlType(AppConstants.CONTROL_TYPE_DATA_TABLE);
                            controlObject.setControlName(arrayParamName);
                            controlObject.setDisplayName(arrayParamName);
                            controlObject.setDataTableRowHeight("60");
                            controlObject.setDataTableRowHeightWrapContent(false);
                            DataTableControl dataTableControl = new DataTableControl(this, controlObject, false, -1, "");
                            llDetails.addView(dataTableControl.getDataTableLayout());
                            ActionWithoutCondition_Bean actionWithoutCondition_bean = new ActionWithoutCondition_Bean();
                            List<DataTableColumn_Bean> dataTableColumn_beans = new ArrayList<>();
                            LinkedHashMap<String, List<String>> outputData = new LinkedHashMap<>();
                            List<String> columnsList = new ArrayList<>();
                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(j);
                                for (int k = 0; k < jsonObject.names().length(); k++) {
                                    String paramName = jsonObject.names().getString(k);
                                    String paramValue = jsonObject.getString(paramName);
                                    if (!jsonObject.names().getString(k).endsWith("TransID") &&
                                            !jsonObject.names().getString(k).endsWith("Trans_ID") &&
                                            !jsonObject.names().getString(k).endsWith("_emp_id") &&
                                            !jsonObject.names().getString(k).endsWith("_postid") &&
                                            !jsonObject.names().getString(k).endsWith("_trans_date")) {//_emp_id,//_postid,//_trans_date
                                        List<String> list = new ArrayList<>();
                                        if (outputData.containsKey(paramName)) {
                                            list = outputData.get(paramName);
                                        }
                                        list.add(paramValue);
                                        outputData.put(paramName, list);
                                    }
                                }
                            }
                            if (outputData.size() > 0) {
                                columnsList.addAll(outputData.keySet());
                                actionWithoutCondition_bean.setDataTableFixedWidthEnabled(columnsList.size() > 0 && columnsList.size() < 4);
                                for (int j = 0; j < columnsList.size(); j++) {
                                    DataTableColumn_Bean column_bean = new DataTableColumn_Bean(columnsList.get(j), columnsList.get(j), "None", true, "140");
                                    dataTableColumn_beans.add(column_bean);
                                }
                                actionWithoutCondition_bean.setDataTableColumn_beanList(dataTableColumn_beans);
                                dataTableControl.setDataTableData(actionWithoutCondition_bean, outputData, columnsList);
                            }
                        }
                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
*/




/*
    private String getTransIdsToDelete(JSONObject object) {
        StringBuilder trans_ids = new StringBuilder();
        try {
            JSONArray jsonArray = object.getJSONArray("SubForm").getJSONObject(0).getJSONArray(dataManagementOptions.getSubFormInMainForm());
            for (int i = 0; i < jsonArray.length(); i++) {
                trans_ids.append(",").append(jsonArray.getJSONObject(i).getString("Trans_ID"));
            }
        } catch (Exception e) {
            Log.d(TAG, "getTransIdsToDelete: " + e.toString());
            improveHelper.improveException(context, TAG, "getTransIdsToDelete", e);
        }
        return trans_ids.toString().replaceFirst(",", "");
    }
*/


    class CheckFormHasData extends AsyncTask<String, Void, Boolean> {
        AppDetailsAdvancedInput getAllAppNamesData;
        Boolean result = false;

        public CheckFormHasData(AppDetailsAdvancedInput getAllAppNamesData) {
            this.getAllAppNamesData = getAllAppNamesData;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                if (dataManagementOptions.isLazyLoadingEnabled() && getAllAppNamesData.getLazyOrderKey().contentEquals("")) {
                    getAllAppNamesData.setLazyOrderKey("SELECT NULL");
                }
                showProgressDialog(context.getString(R.string.please_wait));
                Call<ResponseBody> getAllAppNamesDataCall = getServices.iGetAppDataOnline(sessionManager.getAuthorizationTokenId(),getAllAppNamesData);
                getAllAppNamesDataCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        dismissProgressDialog();
                        String json = null;
                        try {
                            json = response.body().string();
                            Log.d(TAG, "appdata: " + json);
                            JSONObject responseObj = new JSONObject(json);
                            if (responseObj.getString("Status").contentEquals("200") && !responseObj.getString("Message").equalsIgnoreCase("No Data Present Now")) {
                                JSONArray jsonArray = new JSONArray((responseObj.getString("Data")));
                                if (jsonArray.length() == 0) {
                                    result = false;
                                    finish();
                                } else {
                                    result = true;
                                    queryDetailsObject = new JSONObject();
                                    controlsList = new ArrayList<>();
                                    gpsControlList = new ArrayList<>();
                                    imageControlList = new ArrayList<>();
                                    videoControlsList = new ArrayList<>();
                                    audioControlsLIst = new ArrayList<>();
                                    subformControlsList = new HashMap<>();
                                    allControlsList = new ArrayList<>();
                                    queryDetailsObject = jsonArray.getJSONObject(0);
                                    new PreviewDataASync().execute();
                                }
                            } else {
                                result = false;
                                finish();
                            }
                        } catch (Exception e) {
                            result = false;
                            finish();
                        }
                        Log.d(TAG, "onResponse: " + json);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        dismissProgressDialog();
                        finish();
                    }
                });
            } catch (Exception e) {
                ImproveHelper.improveException(context, TAG, "getAppDataOnline", e);
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

        }
    }
}



