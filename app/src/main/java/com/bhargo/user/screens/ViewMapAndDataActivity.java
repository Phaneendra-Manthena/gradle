package com.bhargo.user.screens;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CAMERA;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_FILE_BROWSING;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.replaceWithUnderscore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhargo.user.Expression.ExpressionMainHelper;
import com.bhargo.user.Java_Beans.API_InputParam_Bean;
import com.bhargo.user.Java_Beans.DataManagementOptions;
import com.bhargo.user.MainActivity;
import com.bhargo.user.Query.QueryDetailsActivity;
import com.bhargo.user.R;
import com.bhargo.user.adapters.AppsAdvancedAdapter;
import com.bhargo.user.adapters.AppsAdvancedAdapterCopy;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.interfaces.ItemClickListenerAdvanced;
import com.bhargo.user.pojos.AppDetails;
import com.bhargo.user.pojos.AppDetailsAdvancedAction;
import com.bhargo.user.pojos.AppDetailsAdvancedInput;
import com.bhargo.user.pojos.Control;
import com.bhargo.user.pojos.DetailedPageData;
import com.bhargo.user.pojos.DeviceIdResponse;
import com.bhargo.user.pojos.EditOrViewColumns;
import com.bhargo.user.pojos.FilterColumns;
import com.bhargo.user.pojos.FormControls;
import com.bhargo.user.pojos.FormLevelTranslation;
import com.bhargo.user.pojos.GetAllAppModel;
import com.bhargo.user.pojos.OfflineDataTransaction;
import com.bhargo.user.pojos.SubControl;
import com.bhargo.user.pojos.SubFormTableColumns;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMapAndDataActivity extends BaseActivity implements ItemClickListenerAdvanced, View.OnClickListener, TextWatcher, OnMapReadyCallback, GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener {

    private static final String TAG = "ViewDataActivity";
    private static final int BACK_TO_LIST_FLAG = 2002;

    RecyclerView rv_apps;
    ImageView iv_appListRefresh;
    GetServices getServices;
    Context context;
    AppsAdvancedAdapter appsAdvancedAdapter;

    RelativeLayout rl_AppsListMain;
    FloatingActionButton fb_add_row;

    CustomTextView ct_alNoRecords;
    ImproveHelper improveHelper;

    SessionManager sessionManager;
    ImproveDataBase improveDataBase = new ImproveDataBase(this);
    DataManagementOptions dataManagementOptions;
    FormControls formControls;
    FormLevelTranslation formLevelTranslation;
    JSONArray jsonArray;
    JSONArray jsonArrayMain;
    List<JSONArray> jsonArrayList;
    List<String> List_Columns;
    List<Control> List_ControlTypes;
    boolean image = false;
    List<String> IndexValueToCompare;
    JSONArray newJsonArray;
    List<String> subFormColumns = new ArrayList<>();
    private GetAllAppModel getAllAppModel;
    private String strPostId, strAppDesign, strAppVersion, strOrgId, strAppType, strAppName,strDisplayAppName, strCreatedBy, strUserId, strDistributionId, strUserLocationStructure, strFrom_InTaskDetails;
    private boolean isResume;
    private String callerFormName;
    private String callerFormType;
    private AppDetails appDetailsList;
    private Intent getIntent;
    AutoCompleteTextView actv_search;
    List<String> viewColumnsList;
    String tableName;
    String appMode;
    String appLanguage = "en";
    SupportMapFragment mapFragment;
    GoogleMap mGoogleMap;
    List<SubFormTableColumns> subFormDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map_and_data);

        context = this;
        appLanguage = ImproveHelper.getLocale(context);
        initializeActionBar();
        enableBackNavigation(true);
        ib_settings.setVisibility(View.GONE);
        iv_circle_appIcon.setVisibility(View.VISIBLE);
        getIntent = getIntent();

        if (getIntent != null) {

            if (getIntent.hasExtra("s_resume")) {
                isResume = getIntent.getBooleanExtra("s_resume", false);
                callerFormName = getIntent.getStringExtra("caller_form_name");
                callerFormType = getIntent.getStringExtra("form_type");
                AppConstants.MULTI_FORM_TYPE = callerFormType;
            }
            if (getIntent().hasExtra("form_type")) {
                callerFormType = getIntent.getStringExtra("form_type");
                AppConstants.MULTI_FORM_TYPE = callerFormType;
            }
            dataManagementOptions = (DataManagementOptions) getIntent.getSerializableExtra("dataManagementOptions");
            if (getIntent.hasExtra("s_form_translation")) {
                formLevelTranslation = (FormLevelTranslation) getIntent.getSerializableExtra("s_form_translation");
            }
            formControls = (FormControls) getIntent.getSerializableExtra("formControls");
            appMode = getIntent.getStringExtra("s_app_mode");
            strAppVersion = getIntent.getStringExtra("s_app_version");
            strOrgId = getIntent.getStringExtra("s_org_id");
            strAppType = getIntent.getStringExtra("s_app_type");
            strAppName = getIntent.getStringExtra("s_app_name");
            strDisplayAppName = getIntent.getStringExtra("s_display_app_name");
            strCreatedBy = getIntent.getStringExtra("s_created_by");
            strUserId = getIntent.getStringExtra("s_user_id");
            strDistributionId = getIntent.getStringExtra("s_distribution_id");
            strUserLocationStructure = getIntent.getStringExtra("s_user_location_Structure");
            strFrom_InTaskDetails = getIntent.getStringExtra("From_InTaskDetails");
            strPostId = getIntent.getStringExtra("s_user_post_id");
            tableName =  getIntent.getStringExtra("tableName");
            String subFormData =  getIntent.getStringExtra("subFormDetails");
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<SubFormTableColumns>>(){}.getType();
            subFormDetails = gson.fromJson(subFormData, listType);
        }

        improveHelper = new ImproveHelper(context);
        sessionManager = new SessionManager(context);
        getServices = RetrofitUtils.getUserService();

        actv_search = findViewById(R.id.actv_search);
        ct_alNoRecords = findViewById(R.id.ct_alNoRecords);
        rv_apps = findViewById(R.id.rv_apps);
        rl_AppsListMain = findViewById(R.id.rl_AppsListMain);
        iv_appListRefresh = findViewById(R.id.iv_appListRefresh);
        iv_sync = findViewById(R.id.iv_sync);
        fb_add_row = findViewById(R.id.fb_add_row);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);



        setAppTitle();
        ib_settings.setVisibility(View.GONE);

        fb_add_row.setOnClickListener(this);
        actv_search.addTextChangedListener(this);
        jsonArray = new JSONArray();
        jsonArrayMain = new JSONArray();
        newJsonArray = new JSONArray();
        appDetailsList = improveDataBase.getAppDetails(strOrgId, strAppName, sessionManager.getUserDataFromSession().getUserID());
        if (appDetailsList == null) {
            if (getIntent.hasExtra("From_InTaskDetails")) {
                appDetailsList = (AppDetails) getIntent().getSerializableExtra("s_app_details");
            }
        }
        if (dataManagementOptions != null && (dataManagementOptions.isEnableViewData() || dataManagementOptions.isEnableEditData() || dataManagementOptions.isEnableDeleteData())) {
            tableName = strCreatedBy + "_" + ImproveHelper.getTableNameWithOutSpace(strAppName);
            if(dataManagementOptions.getRecordInsertionType().equalsIgnoreCase("None")){
                fb_add_row.setVisibility(View.GONE);
            }
            if (dataManagementOptions.isLazyLoadingEnabled()) {
                setOnScrollListener(rv_apps);
            }
            List_Columns = new ArrayList<>();
            List_ControlTypes = new ArrayList<>();
            List_Columns = dataManagementOptions.getList_Table_Columns();
            List_ControlTypes = formControls.getMainFormControlsList();
            //SubForm Columns For SubForm in MainForm
            if (!dataManagementOptions.getSubFormInMainForm().equalsIgnoreCase("")) {
                subFormColumns = getSubFormColumns();
            }

            rv_apps.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
           /* LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            rv_apps.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView*/

         /*   GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
            gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rv_apps.setLayoutManager(gridLayoutManager);*/

            getViewColumnsAndShowInList();

            if (appDetailsList != null && appDetailsList.getAppIcon() != null) {
                loadAppIcon(appDetailsList.getAppIcon());
            }
            getAppData();
        } else {
            addNewRow();
        }
    }

    private void setAppTitle() {
        if(dataManagementOptions.getIndexPageDetails().getPageTitle()!=null){
            title.setText(dataManagementOptions.getIndexPageDetails().getPageTitle());
        } else {
            title.setText(strDisplayAppName);
        }
    }

    private void getViewColumnsAndShowInList() {

        appsAdvancedAdapter = new AppsAdvancedAdapter(context, jsonArray, strAppName,  tableName, appMode,dataManagementOptions);
        rv_apps.setAdapter(appsAdvancedAdapter);
        appsAdvancedAdapter.setCustomClickListener(ViewMapAndDataActivity.this);

    }
    private boolean  getImagePreviewColumn(String columnName) {
if(dataManagementOptions.getPreviewColumns()!=null){
        for(EditOrViewColumns editOrViewColumns:dataManagementOptions.getPreviewColumns()){
            if(editOrViewColumns.getColumnName().equalsIgnoreCase(columnName) && editOrViewColumns.getColumnType()!=null && editOrViewColumns.getColumnType().equalsIgnoreCase(getString(R.string.image))){
                return true;
            }
        }}
        return false;
    }
    private String getControlType(String columnName) {

        for (Control controlData : formControls.getMainFormControlsList()) {
            if (controlData.getControlName().equalsIgnoreCase(columnName)) {
                return controlData.getControlType();
            }
        }
        return null;
    }

    private String getControlTypeForMap(String columnName) {

        try {
            if (columnName.contains("_Coordinates")) {
                columnName = columnName.replace("_Coordinates", "");
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getControlNameForGPS", e);
        }
        for (Control controlData : formControls.getMainFormControlsList()) {
            if (controlData.getControlName().equalsIgnoreCase(columnName)) {
                return controlData.getControlType();
            }
        }
        return null;
    }

    public int getIndex(String columnName) {
        int i = 0;
        if (dataManagementOptions.getPreviewColumns() != null && dataManagementOptions.getPreviewColumns().size() > 0) {
            for(int j=0;j<dataManagementOptions.getPreviewColumns().size();j++){
                if(dataManagementOptions.getPreviewColumns().get(j).getColumnName().equalsIgnoreCase(columnName)){
                    i=j;
                    break;
                }
            }
        }
        return i;
    }

    private boolean checkPreviewColumns(String columnName) {

        if (dataManagementOptions.getPreviewColumns() == null || dataManagementOptions.getPreviewColumns().size() == 0) {
            return true;
        } else if (dataManagementOptions.getPreviewColumns() != null && checkColumnExistsInPreviewColumns(columnName)) {
            return true;
        } else {
            return false;
        }
    }
    private boolean checkColumnExistsInPreviewColumns(String columnName) {
        for(EditOrViewColumns editOrViewColumns:dataManagementOptions.getPreviewColumns()){
            if(editOrViewColumns.getColumnName().equalsIgnoreCase(columnName)){
                return true;
            }
        }
        return false;
    }

    private boolean checkListSize(List<String> viewColumnsList) {
        if (image) {
            if (viewColumnsList.contains("column_one") && viewColumnsList.contains("column_two") && viewColumnsList.contains("column_three") && (viewColumnsList.contains(CONTROL_TYPE_CAMERA) || viewColumnsList.contains(CONTROL_TYPE_FILE_BROWSING))) {
                return true;
            } else {
                return false;
            }
        } else {
            if (viewColumnsList.contains("column_one") && viewColumnsList.contains("column_two") && viewColumnsList.contains("column_three")) {
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean checkImageOrFileControlExists(List<String> addedControlTypes) {
        if (addedControlTypes.contains(AppConstants.CONTROL_TYPE_CAMERA) || (addedControlTypes.contains(AppConstants.CONTROL_TYPE_FILE_BROWSING))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkControlExists(String controlType, List<String> viewColumnsList) {
        if (viewColumnsList.contains(controlType)) {
            return true;
        } else {
            return false;
        }
    }

    boolean isLoading;

    private void setOnScrollListener(RecyclerView rv_apps) {


        rv_apps.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastCompletelyVisibleItemPosition = ((LinearLayoutManager) (recyclerView.getLayoutManager())).findLastCompletelyVisibleItemPosition();
                System.out.println("lastCompletelyVisibleItemPosition==" + lastCompletelyVisibleItemPosition);
                if (lastCompletelyVisibleItemPosition == appsAdvancedAdapter.getItemCount() - 1 && !isLoading) {
                    appDetailsAdvancedInput.setThreshold(dataManagementOptions.getLazyLoadingThreshold());
                    int offset_value = 0;
                    int limit=appsAdvancedAdapter.getItemCount() + Integer.parseInt(dataManagementOptions.getLazyLoadingThreshold());
                    if (isNetworkStatusAvialable(context) && (appMode != null && appMode.equalsIgnoreCase("Online"))){
                        offset_value= appsAdvancedAdapter.getItemCount()+1;
                    }else{
                        offset_value= appsAdvancedAdapter.getItemCount();
                    }
                    appDetailsAdvancedInput.setRange(offset_value + "-" + limit);
                    appsAdvancedAdapter.updateEmptyObject();
                    isLoading = true;
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        appsAdvancedAdapter.removeEmptyObject();
                        loadMore(appDetailsAdvancedInput);
                    }, 2000);

                }
            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

             /*   if(dy>0) {

                    int lastCompletelyVisibleItemPosition = ((LinearLayoutManager) (recyclerView.getLayoutManager())).findLastCompletelyVisibleItemPosition();
                    System.out.println("lastCompletelyVisibleItemPosition==" + lastCompletelyVisibleItemPosition);
                    if (lastCompletelyVisibleItemPosition == appsAdvancedAdapter.getItemCount() - 1) {
                        appDetailsAdvancedInput.setThreshold(dataCollectionObject.getLazyLoadingThreshold());
                        appDetailsAdvancedInput.setRange((appsAdvancedAdapter.getItemCount() + 1) + "-" + (appsAdvancedAdapter.getItemCount() + dataCollectionObject.getLazyLoadingThreshold()));
                        loadMore(appDetailsAdvancedInput);
                    }
                }*/
            }
        });

    }

    AppDetailsAdvancedInput appDetailsAdvancedInput;

    public void getAppData() {

        try {

            appDetailsAdvancedInput = new AppDetailsAdvancedInput();

            appDetailsAdvancedInput.setOrgId(strOrgId);
            appDetailsAdvancedInput.setPageName(strAppName);
            appDetailsAdvancedInput.setUserId(sessionManager.getUserDataFromSession().getUserID());
            appDetailsAdvancedInput.setPostId(sessionManager.getPostsFromSession());
            appDetailsAdvancedInput.setSubmittedUserPostID(sessionManager.getPostsFromSession());
            if (dataManagementOptions.getFetchData() != null) {
                appDetailsAdvancedInput.setFetchData(dataManagementOptions.getFetchData());
            } else {
                appDetailsAdvancedInput.setFetchData("Login User Post");
            }
            if (dataManagementOptions.getIndexPageColumnsOrder() != null && !dataManagementOptions.getIndexPageColumnsOrder().equalsIgnoreCase("")) {
                appDetailsAdvancedInput.setOrderbyStatus("True");
                appDetailsAdvancedInput.setOrderByColumns(dataManagementOptions.getIndexPageColumnsOrder());
                appDetailsAdvancedInput.setOrderByType(dataManagementOptions.getOrder());
            } else {
                appDetailsAdvancedInput.setOrderbyStatus("False");
                appDetailsAdvancedInput.setOrderByColumns("");
                appDetailsAdvancedInput.setOrderByType("");
            }

            if (dataManagementOptions.isLazyLoadingEnabled()) {
                appDetailsAdvancedInput.setLazyLoading("True");
                appDetailsAdvancedInput.setThreshold(dataManagementOptions.getLazyLoadingThreshold());
                appDetailsAdvancedInput.setRange("1" + "-" + dataManagementOptions.getLazyLoadingThreshold());
                //TODO  --- In future need to set order by columns in the below line
                appDetailsAdvancedInput.setLazyOrderKey("SELECT NULL");
            }
            if (dataManagementOptions.getFilterColumns() != null) {
                List<FilterColumns> filterColumnsList = new ArrayList<>();
                for (int i = 0; i < dataManagementOptions.getFilterColumns().size(); i++) {
                    API_InputParam_Bean param_bean = dataManagementOptions.getFilterColumns().get(i);
                    FilterColumns filterColumn = new FilterColumns();
                    filterColumn.setColumnName(param_bean.getInParam_Name());
                    filterColumn.setColumnType("Others");
                    filterColumn.setOperator(ImproveHelper.getOparator(param_bean.getInParam_Operator()));
                    filterColumn.setCondition(param_bean.getInParam_and_or());
                    filterColumn.setCurrentGPS("");
                    filterColumn.setNearBy("");
                    filterColumn.setNoOfRec("");
                    ExpressionMainHelper expressionMainHelper = new ExpressionMainHelper();
                        /*String expression ="";
                        if(param_bean.getInParam_ExpressionValue()!=null){
                            expression = param_bean.getInParam_ExpressionValue();
                        }else{
                            expression = param_bean.getInParam_Mapped_ID();
                        }*/
                    filterColumn.setValue(param_bean.getInParam_Temp_Value());
                    filterColumnsList.add(filterColumn);
                }
                appDetailsAdvancedInput.setFilterColumns(filterColumnsList);
            }

            addFilterSubFormColumns();

            if (isNetworkStatusAvialable(context) && (appMode != null && appMode.equalsIgnoreCase("Online"))) {
                getAppDataOnline(appDetailsAdvancedInput, false);
            } else {
                getOfflineData();
                if (!isNetworkStatusAvialable(context)) {
                    improveHelper.snackBarAlertActivities(context, rl_AppsListMain);
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getAppData", e);
        }
    }

    private void addFilterSubFormColumns() {

//        if(dataCollectionObject.getFilterSubFormColumnsList()!=null && dataCollectionObject.getFilterSubFormColumnsList().size()>0){
//            appDetailsAdvancedInput.setFilterSubFormColumns(dataCollectionObject.getFilterSubFormColumnsList());
//        }else{
//            appDetailsAdvancedInput.setFilterSubFormColumns(new ArrayList<>());
//        }
    }

    private void loadMore(AppDetailsAdvancedInput getAllAppNamesData) {
        try {
            if (isNetworkStatusAvialable(context) && (appMode != null && appMode.equalsIgnoreCase("Online"))) {
            Call<ResponseBody> getAllAppNamesDataCall = getServices.iGetAppDataOnline(sessionManager.getAuthorizationTokenId(),getAllAppNamesData);
            getAllAppNamesDataCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    dismissProgressDialog();
                    String json = null;
                    try {
                        json = response.body().string();
                        Log.d(TAG, "appdata: " + json);
                        JSONObject responseObj = new JSONObject(json);

                        if (responseObj.getString("Status").contentEquals("200")) {
                            JSONArray lazyLoadingArray = new JSONArray();

                            lazyLoadingArray = new JSONArray((responseObj.getString("Data")));
                            try {
                                JSONObject jobj = new JSONObject();
                                jobj.put("Data", lazyLoadingArray);
                                ImproveHelper.Controlflow("getAppDataOnline", "AppsList", "ViewData", jobj.toString());
                            } catch (JSONException e) {
                                ImproveHelper.improveException(context, TAG, "getAppDataOnline", e);
                            }

                            if (lazyLoadingArray.length() == 0) {

                            } else {
                                appsAdvancedAdapter.updateList(lazyLoadingArray, appsAdvancedAdapter.getItemCount());
                                isLoading = false;

                            }
                        } else {

                            ImproveHelper.showToast(context, responseObj.getString("Message"));

                        }

                    } catch (Exception e) {
                        e.printStackTrace();

                        ct_alNoRecords.setVisibility(View.VISIBLE);
                        rv_apps.setVisibility(View.GONE);
                    }

                    Log.d(TAG, "onResponse: " + json);
                }


                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    dismissProgressDialog();
                }
            });}else{
                loadMoreOfflineData();
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getAppDataOnline", e);
        }
    }

    public void loadMoreOfflineData() {

        String offlinestatus = "0";
        String onlinestatus = "2";
        try {
            int type = 3;
            String filters = null;
            if (appDetailsAdvancedInput.getFetchData().equalsIgnoreCase(AppConstants.RETRIEVE_TYPE_ENTIRE_TABLE_DATA)) {
                type = 3;
            } else if (appDetailsAdvancedInput.getFetchData().equalsIgnoreCase(AppConstants.RETRIEVE_TYPE_POST_BASED)) {
                type = 1;
            } else if (appDetailsAdvancedInput.getFetchData().equalsIgnoreCase(AppConstants.RETRIEVE_TYPE_POST_LOCATION_BASED)) {
                type = 2;
            } else if (appDetailsAdvancedInput.getFetchData().equalsIgnoreCase(AppConstants.RETRIEVE_TYPE_FILTER_BASED)) {
                type = 4;
                filters = getFilters(appDetailsAdvancedInput.getFilterColumns());
            }
            JSONArray jsonArray = new JSONArray();
            jsonArray = improveDataBase.getOfflineDataWithFilters(strOrgId, strAppName, offlinestatus, onlinestatus, strUserId, strPostId, tableName, AppConstants.Trans_id, filters, type,appDetailsAdvancedInput);
            //**subform data**//
            List<String> subFormNames = new ArrayList<>();
            for (SubControl subControl : formControls.getSubFormControlsList()) {
                subFormNames.add(subControl.getSubFormName());
            }
            if (subFormNames.size() > 0) {
                JSONArray jsonArraySubForm = new JSONArray();
                for (int i = 0; i < jsonArrayMain.length(); i++) {
                    jsonArraySubForm = new JSONArray();
                    JSONObject jsonObjectSubFormRows = new JSONObject();
                    for (String subformName : subFormNames) {
                        jsonObjectSubFormRows = new JSONObject();
                        String tablename = getSubformTableName(subformName);
                        String is_active = "Bhargo_Is_Active";
                        JSONArray jsonArraySubFormRows = improveDataBase.getOfflineDataFromSubFormTableJSON(strOrgId, strAppName, "0", strUserId, tablename, AppConstants.Trans_id, AppConstants.Ref_Trans_id, jsonArrayMain.getJSONObject(i).getString("Trans_ID"), is_active);
                        jsonObjectSubFormRows.put(subformName, jsonArraySubFormRows);
                        jsonArraySubForm.put(jsonObjectSubFormRows);
                        JSONObject jsonObjectMainFormRow = jsonArrayMain.getJSONObject(i);
                        jsonObjectMainFormRow.put("SubForm", jsonArraySubForm);
                    }


                }
            }
            //**subform data**//

            Log.d(TAG, "getOfflineData: " + jsonArrayMain.toString());
            if (jsonArray.length() != 0) {
                isLoading = false;
                AppConstants.hasData = true;
                if (dataManagementOptions.getSubFormInMainForm().equalsIgnoreCase("")) {
                    appsAdvancedAdapter.updateList(jsonArray, appsAdvancedAdapter.getItemCount());
                } else {
                    getDistinctData(jsonArray);
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getOfflineData", e);
        }

    }
    private void getAppDataOnline(final AppDetailsAdvancedInput getAllAppNamesData, boolean forSearch) {
        try {
            if (dataManagementOptions.isLazyLoadingEnabled() && getAllAppNamesData.getLazyOrderKey().contentEquals("")) {
                getAllAppNamesData.setLazyOrderKey("SELECT NULL");
            }
            showProgressDialog(getString(R.string.please_wait));
            Call<ResponseBody> getAllAppNamesDataCall;
            if (forSearch) {
                getAllAppNamesDataCall = getServices.iGetAppDataOnlineForSearch(sessionManager.getAuthorizationTokenId(),getAllAppNamesData);
            } else {
//                getAllAppNamesDataCall = getServices.iGetAppDataOnline(getAllAppNamesData);
                getAllAppNamesDataCall = getServices.iGetAppDataOffline(sessionManager.getAuthorizationTokenId(),getAllAppNamesData);
            }
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

                            jsonArray = new JSONArray((responseObj.getString("Data")));
                            try {
                                JSONObject jobj = new JSONObject();
                                jobj.put("Data", jsonArray);
                                ImproveHelper.Controlflow("getAppDataOnline", "AppsList", "ViewData", jobj.toString());
                            } catch (JSONException e) {
                                ImproveHelper.improveException(context, TAG, "getAppDataOnline", e);
                            }

                            if (jsonArray.length() == 0) {
                                AppConstants.hasData = false;
                                ct_alNoRecords.setVisibility(View.VISIBLE);
                                rv_apps.setVisibility(View.GONE);
                                addNewRow();
                            } else if (dataManagementOptions.getRecordInsertionType().equalsIgnoreCase("Single")) {
                                viewRow(jsonArray.getJSONObject(0));
                            } else {
                                loadMap();
                                AppConstants.hasData = true;
                                ct_alNoRecords.setVisibility(View.GONE);
                                rv_apps.setVisibility(View.VISIBLE);
                                rv_apps.setAdapter(appsAdvancedAdapter);
                                if (dataManagementOptions.getSubFormInMainForm().equalsIgnoreCase("")) {
                                    appsAdvancedAdapter.updateList(jsonArray);
                                    scrollToPositionZero();

                                } else {
                                    getDistinctData(jsonArray);
                                }

                            }

                        } else {
                            AppConstants.hasData = false;
                            ct_alNoRecords.setVisibility(View.VISIBLE);
                            rv_apps.setVisibility(View.GONE);
                            addNewRow();
//                            ImproveHelper.showToast(context, responseObj.getString("Message"));

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        ImproveHelper.showToast(context, e.toString());
                        ct_alNoRecords.setVisibility(View.VISIBLE);
                        rv_apps.setVisibility(View.GONE);
                    }

                    Log.d(TAG, "onResponse: " + json);
                }


                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getAppDataOnline", e);
        }
    }

    private void loadMap() {
        mapFragment.getMapAsync(this);
    }

    private void scrollToPositionZero() {
        int TIME_OUT = 1000;
        boolean run = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (run == true) {
                    rv_apps.smoothScrollToPosition(0);
                }
            }
        }, TIME_OUT);
    }

    private void getDistinctData(JSONArray jsonArray) {
//        if (checkJsonHasAllIndexColumns(jsonArray)) {
        IndexValueToCompare = new ArrayList<>();
        newJsonArray = new JSONArray();
        try {
            JSONObject firstObject = jsonArray.getJSONObject(0);
            JSONObject jsonObjectFirst = new JSONObject();
            jsonObjectFirst.put("Trans_ID", firstObject.getString("Trans_ID"));

            for (String subFormColumn : subFormColumns) {
                if (dataManagementOptions.getList_Table_Columns().contains(subFormColumn)) {
                    jsonObjectFirst.put(subFormColumn, firstObject.getString(subFormColumn));
                }
            }
//            jsonObjectFirst.put("objective_objective_type", firstObject.getString("objective_objective_type"));
//            jsonObjectFirst.put("objective_objective_typeid", firstObject.getString("objective_objective_typeid"));

            JSONArray jsonArrayRowsFirst = new JSONArray();
            jsonArrayRowsFirst.put(jsonObjectFirst);

            JSONObject jsonObjectSubFormFirst = new JSONObject();
            jsonObjectSubFormFirst.put(dataManagementOptions.getSubFormInMainForm(), jsonArrayRowsFirst);

            JSONArray jsonArraySubFormFirst = new JSONArray();
            jsonArraySubFormFirst.put(jsonObjectSubFormFirst);

            firstObject.put("SubForm", jsonArraySubFormFirst);
            newJsonArray.put(firstObject);
            IndexValueToCompare.add(getFirstIndexValue(jsonArray.getJSONObject(0)));
            for (int i = 1; i < jsonArray.length(); i++) {
                final JSONObject object = jsonArray.getJSONObject(i);
                if (checkNextObject(object)) {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Trans_ID", object.getString("Trans_ID"));
                   /* jsonObject.put("objective_objective_type", object.getString("objective_objective_type"));
                    jsonObject.put("objective_objective_typeid", object.getString("objective_objective_typeid"));*/
                    for (String subFormColumn : subFormColumns) {
                        if (dataManagementOptions.getList_Table_Columns().contains(subFormColumn)) {
                            jsonObject.put(subFormColumn, object.getString(subFormColumn));
                        }
                    }

                    JSONArray jsonArrayRows = new JSONArray();
                    jsonArrayRows.put(jsonObject);

                    JSONObject jsonObjectSubForm = new JSONObject();
                    jsonObjectSubForm.put(dataManagementOptions.getSubFormInMainForm(), jsonArrayRows);

                    JSONArray jsonArraySubForm = new JSONArray();
                    jsonArraySubForm.put(jsonObjectSubForm);

                    object.put("SubForm", jsonArraySubForm);
                    //mainform
                    newJsonArray.put(object);
                }
            }
            appsAdvancedAdapter.updateList(newJsonArray);
            rv_apps.scrollToPosition(0);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getDistinctData", e);
        }
    }


    private boolean checkNextObject(JSONObject object) {
        boolean res = false;
        try {
            StringBuilder indexValues = new StringBuilder();
            try {
                List<String> indexColumns = new ArrayList<>();
//                indexColumns = dataManagementOptions.getIndexPageColumns();

                for (int j = 1; j < object.names().length(); j++) {
                    if (indexColumns.contains(object.names().getString(j))) {
                        indexValues.append(object.getString(object.names().getString(j)));
                    }
                }
            } catch (Exception e) {
                Log.d(TAG, "getDistinctData2: " + e.toString());
            }
            if (!IndexValueToCompare.contains(String.valueOf(indexValues))) {
                IndexValueToCompare.add(String.valueOf(indexValues));
                res = true;
            } else {
                //for subform string
                JSONObject jsonObjectNewRow = new JSONObject();
                jsonObjectNewRow.put("Trans_ID", object.getString("Trans_ID"));
                /*jsonObjectNewRow.put("objective_objective_type", object.getString("objective_objective_type"));
                jsonObjectNewRow.put("objective_objective_typeid", object.getString("objective_objective_typeid"));*/
                for (String subFormColumn : subFormColumns) {
                    if (dataManagementOptions.getList_Table_Columns().contains(subFormColumn)) {
                        jsonObjectNewRow.put(subFormColumn, object.getString(subFormColumn));
                    }
                }
                int position = IndexValueToCompare.indexOf(String.valueOf(indexValues));
                JSONObject jsonObject1 = newJsonArray.getJSONObject(position);
                JSONArray jsonArray1 = jsonObject1.getJSONArray("SubForm").getJSONObject(0).getJSONArray(dataManagementOptions.getSubFormInMainForm());
                jsonArray1.put(jsonObjectNewRow);
            }


        } catch (Exception e) {
            Log.d(TAG, "getDistinctData3: " + e.toString());
            improveHelper.improveException(context, TAG, "checkNextObject", e);
        }

        return res;
    }

    private String getFirstIndexValue(JSONObject object) {
        StringBuilder indexValues = new StringBuilder();
        try {
            List<String> indexColumns = new ArrayList<>();
//            indexColumns = dataManagementOptions.getIndexPageColumns();
            for (int j = 1; j < object.names().length(); j++) {
                if (indexColumns.contains(object.names().getString(j))) {
                    indexValues.append(object.getString(object.names().getString(j)));
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "getDistinctData4: " + e.toString());
            improveHelper.improveException(context, TAG, "getFirstIndexValue", e);
        }
        return String.valueOf(indexValues);
    }

    @Override
    public void onCustomClick(Context context, View view, int position, String typeOfAction, String status) {
        JSONObject object;
        try {
            if (dataManagementOptions.getSubFormInMainForm().equalsIgnoreCase("")) {
                if (status.equalsIgnoreCase("0") || status.equalsIgnoreCase("2")) {
                    object = jsonArrayMain.getJSONObject(position);
                } else {
                    JSONArray jsonArray = appsAdvancedAdapter.getData();
                    object = jsonArray.getJSONObject(position);
                }
            } else {
                object = newJsonArray.getJSONObject(position);
            }

            if (typeOfAction.equalsIgnoreCase("View")) {

                viewRow(object);

            } else if (typeOfAction.equalsIgnoreCase("Edit")) {

                editRow(object, status);

            } else if (typeOfAction.equalsIgnoreCase("Delete")) {
                if (dataManagementOptions.getSubFormInMainForm().equalsIgnoreCase("")) {
                    deleteAlert(context, position, object.getString("Trans_ID"), status, false, object);
                } else {
                    deleteAlert(context, position, getTransIdsToDelete(object), status, true, object);
                }
            }
            /*else {
                viewRow(object, status);
            }*/
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "onCustomClick", e);
        }

    }

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

    private void editRow(JSONObject jsonObject, String status) {
        try {
            AppConstants.hasData = true;
            Intent intent = new Intent(context, MainActivity.class);

            intent.putExtra("jsonObject", jsonObject.toString());

//            intent.putExtra("create_query_object",(Serializable) dataCollectionObject);

            intent.putExtra("Trans_ID", jsonObject.getString("Trans_ID"));
            intent.putExtra("appName", strAppName);
            intent.putExtra("s_childForm", "EditForm");
            intent.putExtra("s_app_version", strAppVersion);
            intent.putExtra("s_app_type", strAppType);
            intent.putExtra("s_org_id", strOrgId);
            intent.putExtra("s_app_name", strAppName);
            intent.putExtra("s_display_app_name", strDisplayAppName);
            intent.putExtra("s_created_by", strCreatedBy);
            intent.putExtra("s_user_id", strUserId);
            intent.putExtra("s_distribution_id", strDistributionId);
            intent.putExtra("s_user_location_Structure", strUserLocationStructure);
            intent.putExtra("app_edit", "Edit");
            if (getIntent.hasExtra("form_type")) {
                intent.putExtra("form_type", callerFormType);
            }
            if (getIntent.hasExtra("from")) {
                intent.putExtra("from", AppConstants.TYPE_CALL_FORM);
            }
            if (getIntent != null && getIntent.hasExtra("From_InTaskDetails")) {
                intent.putExtra("From_InTaskDetails", "From_InTaskDetails");
            }
            intent.putExtra("tableName",tableName);
//            startActivity(intent);
            startActivityForResult(intent, BACK_TO_LIST_FLAG);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getTransIdsToDelete", e);
        }

    }

    public void deleteAlert(Context context, int position, String transId, String status, boolean subFormInMainForm, JSONObject object) {
       /* try {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.are_you_sure_del)

                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what would happen when positive button is clicked
                            dialogInterface.dismiss();
                            deleteRow(position, transId, status, subFormInMainForm, object);
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
            improveHelper.improveException(context, TAG, "deleteAlert", e);
        }*/

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.AlertDialogTheme);
        final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = linflater.inflate(R.layout.custom_alert_dialog, null);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.alertMessage)).setText(R.string.are_you_sure_del);
        ((Button) view.findViewById(R.id.buttonYes)).setText(R.string.yes);
        ((Button) view.findViewById(R.id.buttonNo)).setText(R.string.no);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRow(position, transId, status, subFormInMainForm, object);
                alertDialog.dismiss();

            }
        });
        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

    }

    private void deleteRow(int position, String transId, String status, boolean subFormInMainForm, JSONObject object) {
        try {
            if (status.equalsIgnoreCase("0")) {
                deleteOfflineData(object, position);
            } else if (isNetworkStatusAvialable(context)) {
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
                                //**For Hybrid case Delete Record in Local DB also**//
                                if (status.equalsIgnoreCase("2")) {
                                    deleteOfflineData(object, position);
                                }
                                //**For Hybrid case Delete Record in Local DB also**//
                                rv_apps.setAdapter(appsAdvancedAdapter);
                                if (subFormInMainForm) {
                                    newJsonArray.remove(position);
                                    appsAdvancedAdapter.updateList(newJsonArray);
                                    rv_apps.scrollToPosition(0);
                                } else {
                                    jsonArray.remove(position);
                                    appsAdvancedAdapter.updateList(jsonArray);
                                    rv_apps.scrollToPosition(0);
                                }

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
            } else {
                improveHelper.snackBarAlertActivities(context, rl_AppsListMain);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "deleteRow", e);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        switch (requestCode) {


        }
    }

    private boolean isFileExists(String filename) {

        File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
        return folder1.exists();


    }

    private boolean deleteFileifExists(String filename) {

        File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
        return folder1.delete();


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fb_add_row:
                addNewRow();
                break;
        }
    }

    private void addNewRow() {
        try {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("app_edit", "New");
//            intent.putExtra("s_app_design", strAppDesign);
            intent.putExtra("s_app_version", strAppVersion);
            intent.putExtra("s_app_type", strAppType);
            intent.putExtra("s_org_id", strOrgId);
            intent.putExtra("s_app_name", strAppName);
            intent.putExtra("s_display_app_name", strDisplayAppName);
            intent.putExtra("s_created_by", strCreatedBy);
            intent.putExtra("s_user_id", strUserId);
            intent.putExtra("s_distribution_id", strDistributionId);
            intent.putExtra("s_user_location_Structure", strUserLocationStructure);
            intent.putExtra("s_user_post_id", strPostId);
            if (getIntent.hasExtra("s_app_icon")) {
                intent.putExtra("s_app_icon", getIntent.getStringExtra("s_app_icon"));
            }
            if (getIntent.hasExtra("from")) {
                intent.putExtra("from", AppConstants.TYPE_CALL_FORM);
            }
            if (getIntent != null && getIntent.hasExtra("From_InTaskDetails")) {
                intent.putExtra("From_InTaskDetails", "From_InTaskDetails");
            }

            if (getIntent.hasExtra("s_resume")) {
                intent.putExtra("s_resume", isResume);
                intent.putExtra("caller_form_name", callerFormName);
                intent.putExtra("form_type", callerFormType);
            }

            if (getIntent.hasExtra("VariablesData")) {
                intent.putExtra("VariablesData", getIntent.getBundleExtra("VariablesData"));
            }
            if (getIntent.hasExtra("exit_to_menu")) {
                intent.putExtra("exit_to_menu", getIntent.getBooleanExtra("exit_to_menu", false));
            }
            if (getIntent.hasExtra("keep_session")) {

                intent.putExtra("keep_session", getIntent.getBooleanExtra("keep_session", false));
            }

            if (getIntent.hasExtra("jsonObject")) {
                intent.putExtra("jsonObject", getIntent.getStringExtra("jsonObject"));
                intent.putExtra("s_childForm", "EditForm");
            }

            intent.putExtra("tableName",tableName);
            startActivityForResult(intent, BACK_TO_LIST_FLAG);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "addNewRow", e);
        }
    }

/*
    public void loadAppIcon(String appIcon) {
        try {
            if (appIcon != null) {

                if (isNetworkStatusAvialable(context)) {
                    Glide.with(context).load(appIcon).into(iv_circle_appIcon);
                } else {

                    String[] imgUrlSplit = appIcon.split("/");
                    String replaceWithUnderscore = strAppName.replaceAll(" ", "_");

                    String strSDCardUrl = "/Improve_User/" + replaceWithUnderscore + "/" + imgUrlSplit[imgUrlSplit.length - 1];
                    Log.d(TAG, "onCreateSDCardPathCheck: " + strSDCardUrl);

                    setImageFromSDCard(strSDCardUrl);

                    improveHelper.snackBarAlertActivities(context, rl_AppsListMain);
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "loadAppIcon", e);
        }

    }
*/

    public void setImageFromSDCard(String strImagePath) {
        try {
            File imgFile = new File(Environment.getExternalStorageDirectory(), strImagePath);
            Log.d(TAG, "setImageFromSDCard: " + imgFile);
            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                iv_circle_appIcon.setImageBitmap(myBitmap);

            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setImageFromSDCard", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if ((requestCode == BACK_TO_LIST_FLAG && resultCode == RESULT_CANCELED) || !AppConstants.hasData) {
        if ((requestCode == BACK_TO_LIST_FLAG && resultCode == RESULT_CANCELED) && !AppConstants.hasData) {
            finish();
        } else {
            getAppData();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (getIntent().hasExtra("from") && getIntent().getStringExtra("from").contentEquals(AppConstants.TYPE_CALL_FORM) && (getIntent().hasExtra("exit_to_menu") && !getIntent().getBooleanExtra("exit_to_menu", false))) {
            finish();
        } else {

            Intent intent = new Intent(context, BottomNavigationActivity.class);
            if (PrefManger.getSharedPreferencesString(context, AppConstants.Notification_Back_Press, "") != null) {
                String onBackFrom = PrefManger.getSharedPreferencesString(context, AppConstants.Notification_Back_Press, "");
                Log.d("NotificationBPM", onBackFrom);
                if (strAppType.equalsIgnoreCase("Auto Reports")) {
                    if (onBackFrom.equalsIgnoreCase("3")) {
                        intent.putExtra("NotifRefreshAppsList", "3");
                    } else if (onBackFrom.equalsIgnoreCase("2")) {
                        intent.putExtra("NotifRefreshAppsList", "2");
                    } else {
                        intent.putExtra("NotifRefreshAppsList", "2");
                    }
                } else {
                    if (onBackFrom.equalsIgnoreCase("1")) {
                        intent.putExtra("NotifRefreshAppsList", "1");
                    } else if (onBackFrom.equalsIgnoreCase("0")) {
                        intent.putExtra("NotifRefreshAppsList", "0");
                    } else {
                        intent.putExtra("NotifRefreshAppsList", "0");
                    }
                }
            }
            if (strAppType.equalsIgnoreCase("MultiForm")) {
                finish();
            } else {
                startActivity(intent);

                finish();
            }
        }
//        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                if (getIntent().hasExtra("from") && getIntent().getStringExtra("from").contentEquals(AppConstants.TYPE_CALL_FORM) && (getIntent().hasExtra("exit_to_menu") && !getIntent().getBooleanExtra("exit_to_menu", false))) {
                    finish();
                } else {
                    Intent intent = new Intent(context, BottomNavigationActivity.class);
                    if (PrefManger.getSharedPreferencesString(context, AppConstants.Notification_Back_Press, "") != null) {
                        String onBackFrom = PrefManger.getSharedPreferencesString(context, AppConstants.Notification_Back_Press, "");
                        Log.d("NotificationBPM", onBackFrom);
                        if (strAppType.equalsIgnoreCase("Auto Reports")) {
                            if (onBackFrom.equalsIgnoreCase("3")) {
                                intent.putExtra("NotifRefreshAppsList", "3");
                            } else if (onBackFrom.equalsIgnoreCase("2")) {
                                intent.putExtra("NotifRefreshAppsList", "2");
                            } else {
                                intent.putExtra("NotifRefreshAppsList", "2");
                            }
                        } else {
                            if (onBackFrom.equalsIgnoreCase("1")) {
                                intent.putExtra("NotifRefreshAppsList", "1");
                            } else if (onBackFrom.equalsIgnoreCase("0")) {
                                intent.putExtra("NotifRefreshAppsList", "0");
                            } else {
                                intent.putExtra("NotifRefreshAppsList", "0");
                            }
                        }
                    }

                    if (strAppType.equalsIgnoreCase("MultiForm")) {
                        finish();
                    } else {
                        startActivity(intent);

                        finish();
                    }
                }

                return true;
            default:
                return false;
        }
    }
    private void viewRow(JSONObject jsonObject) {
        try {
            if(dataManagementOptions.getDetailedPageDetails().isDetailPage()){
                Intent intent = new Intent(context, DetailedPageActivity.class);

                DetailedPageData detailedPageData = new DetailedPageData();
                detailedPageData.setJsonObject(jsonObject.toString());
                detailedPageData.setDataManagementOptions(dataManagementOptions);
                detailedPageData.setAppName(strAppName);
                detailedPageData.setTableName(tableName);
                detailedPageData.setSubFormDetails(subFormDetails);
                detailedPageData.setAppVersion(strAppVersion);
                detailedPageData.setAppType(strAppType);
                detailedPageData.setDisplayAppName(strDisplayAppName);
                detailedPageData.setDisplayIcon(getIntent.getStringExtra("s_app_icon"));
                detailedPageData.setCreatedBy(strCreatedBy);
                detailedPageData.setUserID(strUserId);
                detailedPageData.setDistributionID(strDistributionId);
                detailedPageData.setUserLocationStructure(strUserLocationStructure);
                detailedPageData.setUserPostID(strPostId);
                detailedPageData.setFromActivity("ViewData");
                detailedPageData.setAppIcon(getIntent.getStringExtra("s_app_icon"));
                detailedPageData.setFormLevelTranslation(formLevelTranslation);

                intent.putExtra("DetailedPageData",(Serializable) detailedPageData);

                startActivityForResult(intent, 0);
            }else{
                Toast.makeText(context, "Detailed Page disabled", Toast.LENGTH_SHORT).show();
            }} catch (Exception e) {
            improveHelper.improveException(context, TAG, "viewRow", e);
        }
    }

    private void viewRow1(JSONObject jsonObject) {
        try {

            Intent intent = new Intent(context, QueryDetailsActivity.class);

            intent.putExtra("jsonObject", jsonObject.toString());

//            intent.putExtra("dataCollectionObject", dataCollectionObject);
            intent.putExtra("dataManagementOptions", dataManagementOptions);

            intent.putExtra("Trans_ID", jsonObject.getString("Trans_ID"));
            intent.putExtra("appName", strAppName);
            intent.putExtra("s_childForm", "EditForm");
            intent.putExtra("s_app_version", strAppVersion);
            intent.putExtra("s_app_type", strAppType);
            intent.putExtra("s_org_id", strOrgId);
            intent.putExtra("s_app_name", strAppName);
            intent.putExtra("s_display_app_name", strDisplayAppName);
            intent.putExtra("s_created_by", strCreatedBy);
            intent.putExtra("s_user_id", strUserId);
            intent.putExtra("s_distribution_id", strDistributionId);
            intent.putExtra("s_user_location_Structure", strUserLocationStructure);
            intent.putExtra("s_user_post_id", strPostId);
            intent.putExtra("app_edit", "Edit");
            intent.putExtra("fromActivity", "ViewData");

//            startActivity(intent);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "viewRow", e);
        }
    }


    public void getOfflineData() {

        String offlinestatus = "0";
        String onlinestatus = "2";
        try {
           /* jsonArray = new JSONArray();
            jsonArrayMain = new JSONArray();
            String trans_id = tableName + "_Trans_Id";
            jsonArrayMain = improveDataBase.getOfflineDataFromFormTableJSON(strOrgId, strAppName, offlinestatus,onlinestatus, strUserId, tableName, trans_id);*/
            int type = 3;
            String filters = null;
            jsonArrayMain = new JSONArray();
            if (appDetailsAdvancedInput.getFetchData().equalsIgnoreCase(AppConstants.RETRIEVE_TYPE_ENTIRE_TABLE_DATA)) {
                type = 3;
            } else if (appDetailsAdvancedInput.getFetchData().equalsIgnoreCase(AppConstants.RETRIEVE_TYPE_POST_BASED)) {
                type = 1;
            } else if (appDetailsAdvancedInput.getFetchData().equalsIgnoreCase(AppConstants.RETRIEVE_TYPE_POST_LOCATION_BASED)) {
                type = 2;
            } else if (appDetailsAdvancedInput.getFetchData().equalsIgnoreCase(AppConstants.RETRIEVE_TYPE_FILTER_BASED)) {
                type = 4;
                filters = getFilters(appDetailsAdvancedInput.getFilterColumns());
            }
            jsonArrayMain = improveDataBase.getOfflineDataWithFilters(strOrgId, strAppName, offlinestatus, onlinestatus, strUserId, strPostId, tableName, AppConstants.Trans_id, filters, type,appDetailsAdvancedInput);
            //**subform data**//
            List<String> subFormNames = new ArrayList<>();
            /*for (ControlObject controlObject : dataCollectionObject.getControls_list()) {
                if (controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_SUBFORM) || controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_GRID_CONTROL)) {
                    subFormNames.add(controlObject.getControlName());
                }
            }*/
            for (SubControl subControl : formControls.getSubFormControlsList()) {
                subFormNames.add(subControl.getSubFormName());
            }
            if (subFormNames.size() > 0) {
                JSONArray jsonArraySubForm = new JSONArray();
                for (int i = 0; i < jsonArrayMain.length(); i++) {
                    jsonArraySubForm = new JSONArray();
                    JSONObject jsonObjectSubFormRows = new JSONObject();
                    for (String subformName : subFormNames) {
                        jsonObjectSubFormRows = new JSONObject();
                        String tablename = getSubformTableName(subformName);
                        String is_active = "Bhargo_Is_Active";
                        JSONArray jsonArraySubFormRows = improveDataBase.getOfflineDataFromSubFormTableJSON(strOrgId, strAppName, "0", strUserId, tablename, AppConstants.Trans_id, AppConstants.Ref_Trans_id, jsonArrayMain.getJSONObject(i).getString("Trans_ID"), is_active);
                        jsonObjectSubFormRows.put(subformName, jsonArraySubFormRows);
                        jsonArraySubForm.put(jsonObjectSubFormRows);
                        JSONObject jsonObjectMainFormRow = jsonArrayMain.getJSONObject(i);
                        jsonObjectMainFormRow.put("SubForm", jsonArraySubForm);
                    }


                }
            }
            //**subform data**//

            Log.d(TAG, "getOfflineData: " + jsonArrayMain.toString());
            if (jsonArrayMain.length() == 0) {
                AppConstants.hasData = false;
                ct_alNoRecords.setVisibility(View.VISIBLE);
                rv_apps.setVisibility(View.GONE);
                addNewRow();
            } else {
                AppConstants.hasData = true;
                ct_alNoRecords.setVisibility(View.GONE);
                rv_apps.setVisibility(View.VISIBLE);
                rv_apps.setAdapter(appsAdvancedAdapter);
//                appsAdvancedAdapter.updateList(jsonArrayMain);
                if (dataManagementOptions.getSubFormInMainForm().equalsIgnoreCase("")) {
                    appsAdvancedAdapter.updateList(jsonArrayMain);
                } else {
                    getDistinctData(jsonArrayMain);
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getOfflineData", e);
        }

    }
    private String getSubformTableName(String subFormName_new) {
        String tableName=null;
        for(SubFormTableColumns subFormTableColumns:subFormDetails){
            if(subFormTableColumns.getSubFormName().equalsIgnoreCase(subFormName_new)){
                return subFormTableColumns.getTableName();
            }
        }
        return tableName;
    }
    public List<String> getSubFormColumns() {
        List<String> subFormColumns = new ArrayList<>();
        try {
            List<SubControl> list_Control = formControls.getSubFormControlsList();
            for (SubControl subControl : list_Control) {
                if (subControl.getSubFormName().equalsIgnoreCase(dataManagementOptions.getSubFormInMainForm())) {
                    for (Control subcontrolObject : subControl.getSubformControlsList()) {
                        subFormColumns.add(subcontrolObject.getControlName());
                        if (subcontrolObject.getControlType().contentEquals(AppConstants.CONTROL_TYPE_RADIO_BUTTON)
                                || subcontrolObject.getControlType().contentEquals(AppConstants.CONTROL_TYPE_DROP_DOWN)
                                || subcontrolObject.getControlType().contentEquals(AppConstants.CONTROL_TYPE_CHECKBOX)
                                || subcontrolObject.getControlType().contentEquals(AppConstants.CONTROL_TYPE_CHECK_LIST)
                                || subcontrolObject.getControlType().contentEquals(AppConstants.CONTROL_TYPE_USER)
                                || subcontrolObject.getControlType().contentEquals(AppConstants.CONTROL_TYPE_POST)) {
                            subFormColumns.add(subcontrolObject.getControlName() + "id");
                        } else if (subcontrolObject.getControlType().contentEquals(AppConstants.CONTROL_TYPE_GPS)) {
                            subFormColumns.remove(subcontrolObject.getControlName());
                            subFormColumns.add(subcontrolObject.getControlName() + "_Coordinates");
                            subFormColumns.add(subcontrolObject.getControlName() + "_type");
                        } else if (subcontrolObject.getControlType().contentEquals(AppConstants.CONTROL_TYPE_DATA)) {
                            subFormColumns.add(subcontrolObject.getControlName() + "_id");
                        } else if (subcontrolObject.getControlType().contentEquals(CONTROL_TYPE_CAMERA) && subcontrolObject.isEnableImageWithGps()) {
                            subFormColumns.add(subcontrolObject.getControlName() + "_Coordinates");
                            subFormColumns.add(subcontrolObject.getControlName() + "_type");
                        }
                    }
                }

            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getSubFormColumns", e);
        }

        return subFormColumns;
    }
   /* public List<String> getSubFormColumns() {
        List<String> subFormColumns = new ArrayList<>();
        try {
            List<ControlObject> list_Control = dataCollectionObject.getControls_list();
            for (ControlObject controlObject : list_Control) {
                if (controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_SUBFORM) || controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_GRID_CONTROL)) {
                    if (controlObject.getControlName().equalsIgnoreCase(dataManagementOptions.getSubFormInMainForm())) {
                        for (ControlObject subcontrolObject : controlObject.getSubFormControlList()) {
                            subFormColumns.add(subcontrolObject.getControlName());
                            if (subcontrolObject.getControlType().contentEquals(AppConstants.CONTROL_TYPE_RADIO_BUTTON)
                                    || subcontrolObject.getControlType().contentEquals(AppConstants.CONTROL_TYPE_DROP_DOWN)
                                    || subcontrolObject.getControlType().contentEquals(AppConstants.CONTROL_TYPE_CHECKBOX)
                                    || subcontrolObject.getControlType().contentEquals(AppConstants.CONTROL_TYPE_CHECK_LIST)
                                    || subcontrolObject.getControlType().contentEquals(AppConstants.CONTROL_TYPE_USER)
                                    || subcontrolObject.getControlType().contentEquals(AppConstants.CONTROL_TYPE_POST)) {
                                subFormColumns.add(subcontrolObject.getControlName() + "id");
                            } else if (subcontrolObject.getControlType().contentEquals(AppConstants.CONTROL_TYPE_GPS)) {
                                subFormColumns.remove(subcontrolObject.getControlName());
                                subFormColumns.add(subcontrolObject.getControlName() + "_Coordinates");
                                subFormColumns.add(subcontrolObject.getControlName() + "_type");
                            } else if (subcontrolObject.getControlType().contentEquals(AppConstants.CONTROL_TYPE_DATA)) {
                                subFormColumns.add(subcontrolObject.getControlName() + "_id");
                            } else if (subcontrolObject.getControlType().contentEquals(CONTROL_TYPE_CAMERA) && subcontrolObject.isEnableImageWithGps()) {
                                subFormColumns.add(subcontrolObject.getControlName() + "_Coordinates");
                                subFormColumns.add(subcontrolObject.getControlName() + "_type");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getSubFormColumns", e);
        }

        return subFormColumns;
    }*/


    private String getSearchColumns(List<String> previewColumns) {
        StringBuilder value = new StringBuilder();
        try {
            for (int i = 0; i < previewColumns.size(); i++) {
                value.append(",").append(previewColumns.get(i));
            }
        } catch (Exception e) {
        }
        String valueStr = "";
        if (value.length() > 1) {
            valueStr = value.substring(1);
        }
        return valueStr;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        /*if(dataCollectionObject != null && dataCollectionObject.isLazyLoadingEnabled()){
            if(s.length()>0){
                appDetailsAdvancedInput.setSearchKeyword(s.toString());
                String previewColumns = getSearchColumns(viewColumnsList);
                appDetailsAdvancedInput.setSearchColumns(previewColumns);
                getAppDataOnline(appDetailsAdvancedInput,true);
            }else{
                getAppDataOnline(appDetailsAdvancedInput,false);
            }
        }else if (appsAdvancedAdapter != null){
            appsAdvancedAdapter.getFilter().filter(s.toString());
        }*/
        if (s.length() > 0) {
            if (dataManagementOptions != null && dataManagementOptions.isLazyLoadingEnabled()) {

                appDetailsAdvancedInput.setSearchKeyword(s.toString());
                String previewColumns = getSearchColumns(viewColumnsList);
                appDetailsAdvancedInput.setSearchColumns(previewColumns);
                getAppDataOnline(appDetailsAdvancedInput, true);

            } else if (appsAdvancedAdapter != null) {
                appsAdvancedAdapter.getFilter().filter(s.toString());
            }
        }

    }


    public void deleteOfflineData(JSONObject object, int rowPosition) {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                List<OfflineDataTransaction> offlineDataTransactionList = new ArrayList<>();
                                String transIDValue = object.getString("Trans_ID");
                                OfflineDataTransaction offlineDataTransaction = new OfflineDataTransaction();
                                offlineDataTransaction.setContentValues(null);
                                offlineDataTransaction.setSubFormName(strAppName);
                                offlineDataTransaction.setTableName(tableName);
                                offlineDataTransaction.setTransIDColumn(AppConstants.Trans_id);
                                offlineDataTransaction.setTransIDValue(transIDValue);
                                offlineDataTransaction.setMainFormTransID(transIDValue);
                                offlineDataTransaction.setMainFormTable(tableName);
                                offlineDataTransaction.setMainFormTransIDColumn(AppConstants.Trans_id);
                                offlineDataTransactionList.add(offlineDataTransaction);
                                if (object.has("SubForm")) {
                                    JSONArray subformJsonArray = object.getJSONArray("SubForm");
                                    for (int i = 0; i < subformJsonArray.length(); i++) {
                                        JSONObject objectTemp = subformJsonArray.optJSONObject(i);
                                        Iterator<String> iterator = objectTemp.keys();
                                        while (iterator.hasNext()) {
                                            String currentKey = iterator.next();
                                            JSONArray subformJson = subformJsonArray.getJSONObject(i).getJSONArray(currentKey);

                                            if (subformJson.length() > 0) {
                                                String subFormTableName = tableName + "_" + replaceWithUnderscore(currentKey);
                                                offlineDataTransaction = new OfflineDataTransaction();
                                                offlineDataTransaction.setContentValues(null);
                                                offlineDataTransaction.setSubFormName(currentKey);
                                                offlineDataTransaction.setTableName(subFormTableName);
                                                offlineDataTransaction.setTransIDColumn(AppConstants.Trans_id);
                                                offlineDataTransaction.setTransIDValue(transIDValue);
                                                offlineDataTransaction.setMainFormTransID(transIDValue);
                                                offlineDataTransaction.setMainFormTable(tableName);
                                                offlineDataTransaction.setMainFormTransIDColumn(AppConstants.Trans_id);
                                                offlineDataTransactionList.add(offlineDataTransaction);
                                            }
                                        }
                                    }
                                }

                                if (offlineDataTransactionList.size() > 0) {
                                    OfflineDataTransaction offlineDataTransactionStatus = improveDataBase.deleteFormData(offlineDataTransactionList);
                                    if (offlineDataTransactionStatus.getErrorMessage() != null) {
                                        dismissProgressDialog();
                                        String errorMsg = offlineDataTransactionStatus.getSubFormName() + " deletion failed\n" + offlineDataTransaction.getErrorMessage();
                                        alertDialogError(errorMsg);
                                    } else {
                                        dismissProgressDialog();
                                        jsonArrayMain.remove(rowPosition);
                                        rv_apps.setAdapter(appsAdvancedAdapter);
                                        appsAdvancedAdapter.updateList(jsonArrayMain);
                                        rv_apps.scrollToPosition(0);
                                        Toast.makeText(context, getString(R.string.data_deleted_successfully), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (Exception e) {
                                Log.d(TAG, "run: " + e.toString());
                            }
                        }
                    });
                }
            });
            thread.start();
            thread.join();
        } catch (Exception e) {
            Log.d(TAG, "deleteOfflineData: " + e.toString());
        }
    }

    private String getFilters(List<FilterColumns> filterColumnsList) {
        StringBuilder filters = new StringBuilder();
        for (FilterColumns filterColumn : filterColumnsList) {
            filters.append(" ").append(filterColumn.getColumnName()).append(" ").append(filterColumn.getOperator()).append(" ").append("'").append(filterColumn.getValue()).append("'").append(" ").append(filterColumn.getCondition());
        }
        return filters.toString();
    }

    public void loadAppIcon(String appIconPath) {

        try {
            String[] imgUrlSplit = appIconPath.split("/");
            String imgNameInPackage = imgUrlSplit[imgUrlSplit.length - 1].replaceAll(" ", "_");
            String replaceWithUnderscore = strAppName.replaceAll(" ", "_");
            Log.d(TAG, "loadAppIconAppNameV: " + replaceWithUnderscore);
            String strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + replaceWithUnderscore;
            if (ImproveHelper.isFileExistsInExternalPackage(context, strSDCardUrl, imgNameInPackage)) {
//                    Log.d(TAG, "isFileExistsInExternalPackage: "+"Yes");
                improveHelper.setImageFromPackageFolder(context, strSDCardUrl, imgNameInPackage, iv_circle_appIcon);
            } else {
//                    Log.d(TAG, "isFileExistsInExternalPackage: "+"NO");
                if (isNetworkStatusAvialable(context)) {
                    Glide.with(context).load(appIconPath).into(iv_circle_appIcon);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "loadAppIcon", e);
        }
    }

    private void showOnMap() {
        try {
            for (int position = 0; position < jsonArray.length(); position++) {
                JSONObject object = jsonArray.getJSONObject(position);
                List<String> points = new ArrayList<>();
                String GPS_type = null;
                for (int i = 0; i < object.names().length(); i++) {
//                    String controlName = getControlNameForGPS(object.names().getString(i));
                    String controlName = object.names().getString(i);
                    String controlType = getControlTypeForMap(controlName);
                    if (controlType != null && List_Columns.contains(controlName) && ((controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) || (controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_MAP)))) {
                        String latLong = object.getString(object.names().getString(i));
                        GPS_type = object.getString(object.names().getString(i + 1));
                        Log.d(TAG, "showOnMap: " + latLong);
                        points.add(latLong);
                    }
                }
                setMapPointsDynamically(GPS_type, points);
            }
        } catch (Exception e) {
            Log.d(TAG, "showOnMap: " + e.toString());
            ImproveHelper.improveException(context, TAG, "getAppData", e);
        }
    }


    public void setMapPointsDynamically(String ViewType, List<String> Points) {
        try {
            final LatLngBounds.Builder builder = new LatLngBounds.Builder();
            switch (ViewType) {
                case AppConstants.Single_Point_GPS:
                    for (int i = 0; i < Points.size(); i++) {
                        String[] loc = Points.get(i).split("\\$");
                        LatLng temp_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
                        if (temp_location.latitude != 0.0 && temp_location.longitude != 0.0) {
                            mGoogleMap.addMarker(new MarkerOptions()
                                    .position(temp_location)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.gps))
                                    .title("Location " + i)
                                    .snippet(temp_location.latitude + "," + temp_location.longitude));
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp_location, 8));
                        }
                    }
                    break;
                case AppConstants.Two_points_line:
                case AppConstants.Multi_points_line:
                case AppConstants.Four_points_square:
                    for (int i = 0; i < Points.size(); i++) {

                        if (ViewType.equalsIgnoreCase(AppConstants.Four_points_square)) {
                            String[] locationsTemp = Points.get(i).split("\\^");
                            String pointsTemp = Points.get(i) + "^" + locationsTemp[0];
                            Points.clear();
                            Points.add(pointsTemp);
                        }
                        String[] locations = Points.get(i).split("\\^");
                        for (int j = 0; j < locations.length - 1; j++) {
                            String[] loc = locations[j].split("\\$");
                            String[] loc1 = locations[j + 1].split("\\$");
                            LatLng temp_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
                            LatLng temp_location1 = new LatLng(Double.parseDouble(loc1[0]), Double.parseDouble(loc1[1]));
                            if (temp_location.latitude != 0.0 && temp_location.longitude != 0.0) {

                                mGoogleMap.addPolyline(new PolylineOptions()
                                        .add(temp_location, temp_location1)
                                        .width(5));
                                //.color(getColor(R.color.lightBlue)));

                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp_location, 16));
                            }
                        }
                    }
                    break;
                case AppConstants.map_Polygon:
                    for (int i = 0; i < Points.size(); i++) {
                        String[] locations = Points.get(i).split("\\^");
                        List<LatLng> list_poly = new ArrayList<LatLng>();

                        for (int j = 0; j < locations.length; j++) {
                            String[] loc = locations[j].split("\\$");
                            LatLng temp_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
                            list_poly.add(temp_location);
                        }
                        if (list_poly.size() > 1) {
                            mGoogleMap.addPolygon(new PolygonOptions()
                                    .clickable(true)
                                    .addAll(list_poly));
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(list_poly.get(list_poly.size() - 1), 16));
                        }

                    }
                    break;
                default:
                    break;
            }


            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
//        }
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.resetMinMaxZoomPreference();

        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setMapPonitsDynamically", e);
        }
    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            MapsInitializer.initialize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mGoogleMap = googleMap;
        if (mGoogleMap != null) {
            mGoogleMap.clear();
        }
        if (jsonArray.length() != 0) {
            showOnMap();
        }
    }
}