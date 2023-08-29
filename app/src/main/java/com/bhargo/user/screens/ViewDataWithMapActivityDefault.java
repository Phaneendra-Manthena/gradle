package com.bhargo.user.screens;

import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.bhargo.user.Expression.ExpressionMainHelper;
import com.bhargo.user.Java_Beans.API_InputParam_Bean;
import com.bhargo.user.Java_Beans.DataManagementOptions;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.adapters.ViewDataWithMapPagerAdapter;
import com.bhargo.user.fragments.ViewDataFragment;
import com.bhargo.user.fragments.ViewDataMapFragment;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppDetails;
import com.bhargo.user.pojos.AppDetailsAdvancedInput;
import com.bhargo.user.pojos.FilterColumns;
import com.bhargo.user.pojos.FormControls;
import com.bhargo.user.pojos.GetAllAppModel;
import com.bhargo.user.pojos.SubControl;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bhargo.user.utils.XMLHelper;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDataWithMapActivityDefault extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ViewDataWithMapActivity";

    private static final int BACK_TO_LIST_FLAG = 2002;

    GetServices getServices;
    Context context;
    ImproveHelper improveHelper;
    SessionManager sessionManager;
    ImproveDataBase improveDataBase = new ImproveDataBase(this);
//    DataCollectionObject dataCollectionObject;
DataManagementOptions dataManagementOptions;
    FormControls formControls;
    XMLHelper xmlHelper;
    JSONArray jsonArray;
    JSONArray jsonArrayMain;
    List<JSONArray> jsonArrayList;
    List<String> List_Columns;
    List<String> List_ControlTypes;
    boolean image = false;
    String status = "online";
    CoordinatorLayout mainLayout;
    LinearLayout bottom_layout;
    FrameLayout container;
    List<String> IndexValueToCompare;
    JSONArray newJsonArray = new JSONArray();
    List<String> subFormColumns = new ArrayList<>();
    private GetAllAppModel getAllAppModel;
    private String strPostId, strAppDesign, strAppVersion, strOrgId, strAppType, strAppName,strDisplayAppName, strCreatedBy, strUserId, strDistributionId, strUserLocationStructure, strFrom_InTaskDetails;
    private boolean isResume;
    private String callerFormName;
    private String callerFormType;
    private AppDetails appDetailsList;
    private Intent getIntent;
    String tableName;
    String appMode;

    ViewPager2 myViewPager2;
    ViewDataWithMapPagerAdapter viewDataWithMapPagerAdapter;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data_with_map_default);

        getIntent = getIntent();

        if (getIntent != null) {

            if (getIntent.hasExtra("s_resume")) {
                isResume = getIntent.getBooleanExtra("s_resume", false);
                callerFormName = getIntent.getStringExtra("caller_form_name");
                callerFormType = getIntent.getStringExtra("form_type");
            }
//            dataCollectionObject = (DataCollectionObject) getIntent.getSerializableExtra("dataCollectionObject");
            dataManagementOptions = (DataManagementOptions) getIntent.getSerializableExtra("dataManagementOptions");
            formControls = (FormControls) getIntent.getSerializableExtra("formControls");
            appMode = getIntent.getStringExtra("s_app_mode");
//            strAppDesign = getIntent.getStringExtra("s_app_design");
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
        }


        initViews();
    }

    private void initViews() {
        context = this;
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(this);
        sessionManager = new SessionManager(context);
        initializeActionBar();
        if(dataManagementOptions.getIndexPageDetails().getPageTitle()!=null){
            title.setText(dataManagementOptions.getIndexPageDetails().getPageTitle());
        } else {
            title.setText(strDisplayAppName);
        }
        enableBackNavigation(true);
        iv_circle_appIcon.setVisibility(View.VISIBLE);

        getServices = RetrofitUtils.getUserService();


        bottom_layout = findViewById(R.id.bottom_layout);
        container = findViewById(R.id.container);

        tabLayout = findViewById(R.id.tab_layout);
        myViewPager2 = findViewById(R.id.view_pager);
        viewDataWithMapPagerAdapter = new ViewDataWithMapPagerAdapter(getSupportFragmentManager(), getLifecycle());

        if(dataManagementOptions.getIndexPageDetails().getPageTitle()!=null){
            title.setText(dataManagementOptions.getIndexPageDetails().getPageTitle());
        } else {
            title.setText(strDisplayAppName);
        }
        ib_done.setVisibility(View.GONE);
        ib_settings.setVisibility(View.GONE);


        appDetailsList = improveDataBase.getAppDetails(strOrgId, strAppName, sessionManager.getUserDataFromSession().getUserID());
        loadAppIcon(appDetailsList.getAppIcon());

        if (dataManagementOptions.isEnableViewData() || dataManagementOptions.isEnableEditData() || dataManagementOptions.isEnableDeleteData()) {

            tableName = strCreatedBy + "_" + ImproveHelper.getTableNameWithOutSpace(strAppName);
            getAppData();
        } else {
            addNewRow();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(context, BottomNavigationActivity.class);
        if (PrefManger.getSharedPreferencesString(context, AppConstants.Notification_Back_Press, "") != null) {
            String onBackFrom = PrefManger.getSharedPreferencesString(context, AppConstants.Notification_Back_Press, "");
            Log.d("NotificationBPM", onBackFrom);
            if (onBackFrom.equalsIgnoreCase("1")) {
                intent.putExtra("NotifRefreshAppsList", "1");
            } else if (onBackFrom.equalsIgnoreCase("0")) {
                intent.putExtra("NotifRefreshAppsList", "0");
            } else {
                intent.putExtra("NotifRefreshAppsList", "0");
            }
        }
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:


                Intent intent = new Intent(context, BottomNavigationActivity.class);
                if (PrefManger.getSharedPreferencesString(context, AppConstants.Notification_Back_Press, "") != null) {
                    String onBackFrom = PrefManger.getSharedPreferencesString(context, AppConstants.Notification_Back_Press, "");
                    Log.d("NotificationBPM", onBackFrom);
                    if (onBackFrom.equalsIgnoreCase("1")) {
                        intent.putExtra("NotifRefreshAppsList", "1");
                    } else if (onBackFrom.equalsIgnoreCase("0")) {
                        intent.putExtra("NotifRefreshAppsList", "0");
                    } else {
                        intent.putExtra("NotifRefreshAppsList", "0");
                    }
                }
                startActivity(intent);

                finish();

                return true;
            default:
                return false;
        }
    }
    public void loadAppIcon(String appIconPath) {

        try {
            String[] imgUrlSplit = appIconPath.split("/");
            String imgNameInPackage = imgUrlSplit[imgUrlSplit.length - 1].replaceAll(" ", "_");
            String replaceWithUnderscore = strAppName.replaceAll(" ", "_");
            Log.d(TAG, "loadAppIconAppNameVDQ: "+replaceWithUnderscore);
            String strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + replaceWithUnderscore;
            if(ImproveHelper.isFileExistsInExternalPackage(context,strSDCardUrl,imgNameInPackage)) {
//                    Log.d(TAG, "isFileExistsInExternalPackage: "+"Yes");
                improveHelper.setImageFromPackageFolder(context, strSDCardUrl, imgNameInPackage, iv_circle_appIcon);
            }else{
//                    Log.d(TAG, "isFileExistsInExternalPackage: "+"NO");
                if (isNetworkStatusAvialable(context)) {
                    Glide.with(context).load(appIconPath).into(iv_circle_appIcon);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "loadAppIcon", e);
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

                    improveHelper.snackBarAlertActivities(context, bottom_layout);
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "loadAppIcon", e);
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
            improveHelper.improveException(this, TAG, "setImageFromSDCard", e);
        }
    }
    AppDetailsAdvancedInput appDetailsAdvancedInput;
    public void getAppData() {
        try {

                 appDetailsAdvancedInput = new AppDetailsAdvancedInput();

                appDetailsAdvancedInput.setOrgId(strOrgId);
                appDetailsAdvancedInput.setPageName(strAppName);
                appDetailsAdvancedInput.setUserId(sessionManager.getUserDataFromSession().getUserID());
                appDetailsAdvancedInput.setSubmittedUserPostID(sessionManager.getPostsFromSession());
                if (dataManagementOptions.getFetchData() != null) {
                    appDetailsAdvancedInput.setFetchData(dataManagementOptions.getFetchData());
                } else {
                    appDetailsAdvancedInput.setFetchData("Login User Post");
                }
                if(dataManagementOptions.getIndexPageColumnsOrder()!=null && !dataManagementOptions.getIndexPageColumnsOrder().equalsIgnoreCase("")){
                    appDetailsAdvancedInput.setOrderbyStatus("True");
                    appDetailsAdvancedInput.setOrderByColumns(dataManagementOptions.getIndexPageColumnsOrder());
                    appDetailsAdvancedInput.setOrderByType(dataManagementOptions.getOrder());
                }else{
                    appDetailsAdvancedInput.setOrderbyStatus("False");
                    appDetailsAdvancedInput.setOrderByColumns("");
                    appDetailsAdvancedInput.setOrderByType("");
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
            if (isNetworkStatusAvialable(context) && (appMode != null && appMode.equalsIgnoreCase("Online"))) {
                getAppDataOnline(appDetailsAdvancedInput);
            } else {
                getOfflineData();
//                improveHelper.snackBarAlertActivities(context, mainLayout);
            }

        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getAppData", e);
        }
    }


    private void getAppDataOnline(final AppDetailsAdvancedInput getAllAppNamesData) {
        try {
            showProgressDialog(getString(R.string.please_wait));
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
                            jsonArray = new JSONArray((responseObj.getString("Data")));
                            if (jsonArray.length() == 0) {
                                addNewRow();
                            } else {
//                            setViewPager(jsonArray);
                                if (dataManagementOptions.getSubFormInMainForm().equalsIgnoreCase("")) {
                                    showDataFragment(jsonArray, 0);
                                } else {
                                    getDistinctData(jsonArray, status);
                                }
                            }
                        } else {
                            AppConstants.hasData = false;
                            addNewRow();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ImproveHelper.showToast(context, e.toString());
                    }

                    Log.d(TAG, "onResponse: " + json);
                }


                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getAppDataOnline", e);
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
            intent.putExtra("s_created_by", strCreatedBy);
            intent.putExtra("s_user_id", strUserId);
            intent.putExtra("s_distribution_id", strDistributionId);
            intent.putExtra("s_user_location_Structure", strUserLocationStructure);
            intent.putExtra("s_user_post_id", strPostId);
            if (getIntent.hasExtra("from")) {
                intent.putExtra("from", "CALL_FROM");
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


            startActivityForResult(intent, BACK_TO_LIST_FLAG);
            finish();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "addNewRow", e);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        getAppData();
    }


    public void getOfflineData() {

        String  offlinestatus = "0";
        String  onlinestatus = "2";
        try {
         /*   jsonArray = new JSONArray();
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
           /* for (ControlObject controlObject : dataCollectionObject.getControls_list()) {
                if (controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_SUBFORM) || controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_GRID_CONTROL)) {
                    subFormNames.add(controlObject.getControlName());
                }
            }*/
            for(SubControl subControl:formControls.getSubFormControlsList()){
                subFormNames.add(subControl.getSubFormName());
            }
            if (subFormNames.size() > 0) {
                JSONArray jsonArraySubForm = new JSONArray();
                for (int i = 0; i < jsonArrayMain.length(); i++) {
                    jsonArraySubForm = new JSONArray();
                    JSONObject jsonObjectSubFormRows = new JSONObject();
                    for (String subformName : subFormNames) {
                        jsonObjectSubFormRows = new JSONObject();
                        String tablename = strCreatedBy + "_" + improveHelper.replaceWithUnderscore(strAppName) + "_" + improveHelper.replaceWithUnderscore(subformName);
                        String is_active = "Bhargo_Is_Active";
                        JSONArray jsonArraySubFormRows = improveDataBase.getOfflineDataFromSubFormTableJSON(strOrgId, strAppName, "0", strUserId, tablename, AppConstants.Trans_id, AppConstants.Ref_Trans_id, jsonArrayMain.getJSONObject(i).getString("Trans_ID"),is_active);
                        jsonObjectSubFormRows.put(subformName, jsonArraySubFormRows);
                        jsonArraySubForm.put(jsonObjectSubFormRows);
                        JSONObject jsonObjectMainFormRow = jsonArrayMain.getJSONObject(i);
                        jsonObjectMainFormRow.put("SubForm", jsonArraySubForm);
                    }
                }
            }
            //**subform data**//
            Log.d(TAG, "getOfflineData: " + jsonArrayMain.toString());
            if (jsonArrayMain.length() != 0) {
                showDataFragment(jsonArrayMain, 0);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getOfflineData", e);
        }
    }

    private void showDataFragment1(JSONArray jsonArray, int position) {
        try {
            ViewDataFragment viewDataFragment = new ViewDataFragment(jsonArray);
            ViewDataMapFragment viewDataMapFragment = new ViewDataMapFragment(jsonArray);
            if (position == 0) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, viewDataFragment).commit();
            } else {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, viewDataMapFragment).commit();
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "showDataFragment", e);
        }
    }
    private void showDataFragment(JSONArray jsonArray, int position) {
        try {
            viewDataWithMapPagerAdapter.addFragment(new ViewDataFragment(jsonArray));
            viewDataWithMapPagerAdapter.addFragment(new ViewDataMapFragment(jsonArray));
            myViewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
            myViewPager2.setAdapter(viewDataWithMapPagerAdapter);
            new TabLayoutMediator(tabLayout, myViewPager2,
                    new TabLayoutMediator.TabConfigurationStrategy() {
                        @Override
                        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                            if(position==0){

                                tab.setText(getString(R.string.data));
                            }else{

                                tab.setText(getString(R.string.map));
                            }
                        }
                    }).attach();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "showDataFragment", e);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_data:
                showDataFragment(jsonArray, 0);
                break;
            case R.id.ll_map:
                showDataFragment(jsonArray, 1);
                break;
        }
    }

    private void getDistinctData(JSONArray jsonArray, String status) {

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
            showDataFragment(newJsonArray, 0);
        } catch (Exception e) {
            Log.d(TAG, "getDistinctData1: " + e.toString());
            improveHelper.improveException(this, TAG, "getDistinctData", e);
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
            improveHelper.improveException(this, TAG, "checkNextObject", e);
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
            improveHelper.improveException(this, TAG, "getFirstIndexValue", e);
        }
        return String.valueOf(indexValues);
    }

    private String getFilters(List<FilterColumns> filterColumnsList) {
        StringBuilder filters = new StringBuilder();
        for (FilterColumns filterColumn : filterColumnsList) {
            filters.append(" ").append(filterColumn.getColumnName()).append(" ").append(filterColumn.getOperator()).append(" ").append("'").append(filterColumn.getValue()).append("'").append(" ").append(filterColumn.getCondition());
        }
        return filters.toString();
    }
}