package com.bhargo.user.utils;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CAMERA;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_GPS;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bhargo.user.Expression.ExpressionMainHelper;
import com.bhargo.user.Java_Beans.API_InputParam_Bean;
import com.bhargo.user.Java_Beans.DataManagementOptions;
import com.bhargo.user.Java_Beans.Variable_Bean;
import com.bhargo.user.MainActivity;
import com.bhargo.user.Query.QueryDetailsActivity;
import com.bhargo.user.Query.QueryIndexColumnsGridActivity;
import com.bhargo.user.R;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppDetails;
import com.bhargo.user.pojos.AppDetailsAdvancedInput;
import com.bhargo.user.pojos.CallFormDataResponse;
import com.bhargo.user.pojos.Control;
import com.bhargo.user.pojos.DetailedPageData;
import com.bhargo.user.pojos.FilterColumns;
import com.bhargo.user.pojos.FormControls;
import com.bhargo.user.pojos.FormNavigation;
import com.bhargo.user.pojos.NavigationModel;
import com.bhargo.user.pojos.SubControl;
import com.bhargo.user.screens.DetailedPageActivity;
import com.bhargo.user.screens.ViewDataActivity;
import com.bhargo.user.screens.ViewDataWithMapActivity;
import com.bhargo.user.screens.ViewMapAndDataActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CallFormAction {

    private static final String TAG = "CallFormAction";
    private final Context context;
    ImproveHelper improveHelper;
    String queryName = "";
    private NavigationModel navigationModel;
    private boolean saveThisActivitySession;
    private View activityView;
    private String currentAppName;
    private FrameLayout frameLayout;
    private String strChildAppName;
    private String strChildCreatedBy;
    private String strChildDistributionId;
    private ImproveDataBase improveDataBase;
    private AppDetails appDetails;
    SessionManager sessionManager;
    Thread checkFormdataThread,nextIntentThread;
    JSONArray jsonArray = new JSONArray();
    boolean result;
    List<String> subFormColumns = new ArrayList<>();
    List<String> IndexValueToCompare;
    JSONArray  newJsonArray;
    List<Control> mainFormControls;
    List<SubControl> subFormControls;
    FormControls formControls;
    String subFormInMainFormValue;
    List<String>table_columns;
    private String inparam_current_location = "";

    public String getInparam_current_location() {
        return inparam_current_location;
    }

    public void setInparam_current_location(String inparam_current_location) {
        this.inparam_current_location = inparam_current_location;
    }

    public CallFormAction(Context context) {
        this.context = context;
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);
        sessionManager = new SessionManager(context);
    }


    public void navigateToNext(NavigationModel navigationModel) {
        try {
            this.navigationModel = navigationModel;
            if (ImproveHelper.isNetworkStatusAvialable(context)) {
                getDesignAndNavigate();
            }else{
                appDetails = improveDataBase.getAppDetails(sessionManager.getOrgIdFromSession(), navigationModel.getFormName());
                if(appDetails!=null) {
                    formControls = DataCollection.getControlList(appDetails.getDesignFormat());
                    mainFormControls = formControls.getMainFormControlsList();
                    subFormControls = formControls.getSubFormControlsList();
                    ImproveHelper.saveDesignFormat(context, appDetails.getDesignFormat());
                    if (navigationModel.getDataManagementOptions() == null) {
                        navigationModel.setDataManagementOptions(DataCollection.getAdvanceManagement(appDetails.getDesignFormat()));
                    }
                    if (appDetails != null) {
                        navigateOptions(appDetails);
                    }
                }else{
                    ((MainActivity) context).dismissProgressDialog();
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "navigateToNext", e);
        }
    }
    private void getDesignAndNavigate() {
        try {
            ImproveDataBase improveDataBase = new ImproveDataBase(context);
            Map<String, String> data = new HashMap<>();
            try {
                data.put("PageName", navigationModel.getFormName());
                data.put("OrgId", navigationModel.getOrgId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            GetServices getServices = RetrofitUtils.getUserService();

            Call<CallFormDataResponse> call = getServices.getDesignfromCallform(sessionManager.getAuthorizationTokenId(),data);
            call.enqueue(new Callback<CallFormDataResponse>() {
                @Override
                public void onResponse(Call<CallFormDataResponse> call, Response<CallFormDataResponse> response) {

                    if (response.body() != null && response.body().getStatus().contentEquals("200")) {
                        if (response.body().getAppDetails() != null && response.body().getAppDetails().size() > 0) {
                           appDetails = response.body().getAppDetails().get(0);
                            List<AppDetails> appsList = new ArrayList<AppDetails>();
                            appDetails.setVisibileIn("Both");
                            appsList.add(appDetails);
                            formControls = DataCollection.getControlList(appDetails.getDesignFormat());
                            mainFormControls = formControls.getMainFormControlsList();
                            subFormControls = formControls.getSubFormControlsList();
                            ImproveHelper.saveDesignFormat(context,appDetails.getDesignFormat());
                            if(improveDataBase.checkAppExists(appDetails.getAppName())){
                                appDetails.setZ_Status_flag("Update");
                                improveDataBase.updateAppsList(appDetails,appDetails.getAppName(), navigationModel.getOrgId(), navigationModel.getUserId(),appDetails.getAppName());
                            }else {
                                improveDataBase.insertIntoAppsListTable(appsList, navigationModel.getOrgId(), navigationModel.getUserId());
                            }if(navigationModel.getDataManagementOptions()==null){
                                navigationModel.setDataManagementOptions(DataCollection.getAdvanceManagement(appDetails.getDesignFormat()));
                            }
                            navigateOptions(appDetails);


                        }else {
                            ((MainActivity) context).dismissProgressDialog();
                        }
                    } else {
                        ((MainActivity) context).dismissProgressDialog();
                    }

                }

                @Override
                public void onFailure(Call<CallFormDataResponse> call, Throwable t) {
                    ((MainActivity) context).dismissProgressDialog();
                    Log.d("CallFormAction", "Callform failed responce: " + t.toString());
                }
            });

        } catch (Exception e) {
            ((MainActivity) context).dismissProgressDialog();
            improveHelper.improveException(context, TAG, "getDesignAndNavigate", e);
        }
    }

    private void navigateOptions(AppDetails appDetails) {
        navigationModel.setAppIcon(appDetails.getAppIcon());
        navigationModel.setDesignFormat(appDetails.getDesignFormat());
        navigationModel.setAppVersion(appDetails.getAppVersion());
        navigationModel.setAppVersion(appDetails.getAppType());
        navigationModel.setCreatedBy(appDetails.getCreatedBy());
        navigationModel.setDistributionId(appDetails.getDistrubutionID());
        if (navigationModel.getFormType().contentEquals("REPORT_FORMP")) {
            navigationModel.setFormType("REPORT_FORMP");
        } else if (navigationModel.getFormType().contentEquals("WORK_FLOW_FORM")) {
            navigationModel.setFormType("WORK_FLOW_FORM");
            strChildAppName = appDetails.getAppName();
            strChildCreatedBy = appDetails.getCreatedBy();
            strChildDistributionId = appDetails.getDistrubutionID();

        }

        if (!navigationModel.getFormType().contentEquals("WORK_FLOW_FORM")) {
            navigateToNext(appDetails);
        } else {
            queryName = XMLHelper.getBaseQueryName(navigationModel.getDesignFormat());
            getDesignAndNavigate(queryName);
        }
    }

    private void getDesignAndNavigate(String queryName) {
        try {
            ImproveDataBase improveDataBase = new ImproveDataBase(context);
            Map<String, String> data = new HashMap<>();
            try {
                data.put("PageName", queryName);
                data.put("OrgId", navigationModel.getOrgId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            GetServices getServices = RetrofitUtils.getUserService();

            Call<CallFormDataResponse> call = getServices.getDesignfromCallform(sessionManager.getAuthorizationTokenId(),data);
            call.enqueue(new Callback<CallFormDataResponse>() {
                @Override
                public void onResponse(Call<CallFormDataResponse> call, Response<CallFormDataResponse> response) {

                    if (response.body() != null && response.body().getStatus().contentEquals("200")) {
                        if (response.body().getAppDetails() != null && response.body().getAppDetails().size() > 0) {
                            AppDetails otherFormDetails = response.body().getAppDetails().get(0);
                            List<AppDetails> appsList = new ArrayList<AppDetails>();
                            otherFormDetails.setVisibileIn("Both");
                            appsList.add(otherFormDetails);
                            if(improveDataBase.checkAppExists(appDetails.getAppName())){
                                appDetails.setZ_Status_flag("Update");
                                improveDataBase.updateAppsList(appDetails,appDetails.getAppName(), navigationModel.getOrgId(), navigationModel.getUserId(),appDetails.getAppName());
                            }else {
                                improveDataBase.insertIntoAppsListTable(appsList, navigationModel.getOrgId(), navigationModel.getUserId());
                            }
                            ImproveHelper.saveDesignFormat(context,otherFormDetails.getDesignFormat());
                            navigationModel.setDesignFormat(otherFormDetails.getDesignFormat());
                            navigationModel.setAppVersion(otherFormDetails.getAppVersion());
                            navigationModel.setAppType(otherFormDetails.getAppType());
                            navigationModel.setCreatedBy(otherFormDetails.getCreatedBy());
                            navigationModel.setDistributionId(otherFormDetails.getDistrubutionID());
                            if (navigationModel.getFormType().contentEquals("REPORT_FORMP")) {
                                navigationModel.setFormType("REPORT_FORMP");
                            } else if (navigationModel.getFormType().contentEquals("WORK_FLOW_FORM")) {
                                navigationModel.setFormType("WORK_FLOW_FORM");

                            }

                            navigateToNext(otherFormDetails);


                        }
                    } else {
                        ((MainActivity) context).dismissProgressDialog();
                    }

                }

                @Override
                public void onFailure(Call<CallFormDataResponse> call, Throwable t) {
                    ((MainActivity) context).dismissProgressDialog();
                    Log.d("CallFormAction", "Callform faild responce: " + t.toString());
                }
            });
        } catch (Exception e) {
            ((MainActivity) context).dismissProgressDialog();
            improveHelper.improveException(context, TAG, "getDesignAndNavigate - queryName", e);
        }

    }


    public boolean dataManagementObjectIssue;
    public void navigateToNext(AppDetails otherFormDetails) {
        try {

            FormNavigation formNavigation = new FormNavigation(navigationModel.getFormName(), true);
            if (AppConstants.currentMultiForm != null) {
                AppConstants.currentMultiForm.getNavigationList().add(formNavigation, navigationModel.getFormName());
            }

            Intent intent = new Intent(context, ViewDataActivity.class);
            intent.putExtra("tableName",otherFormDetails.getTableName());
            if (AppConstants.currentMultiForm != null) {
                intent = new Intent(context, MainActivity.class);
                intent.putExtra("app_edit", "New");
                intent.putExtra("tableName",otherFormDetails.getTableName());

            }
            if (navigationModel.getFormType().contentEquals("REPORT_FORMP")) {
                intent = new Intent(context, QueryIndexColumnsGridActivity.class);
                intent.putExtra("s_design_format", navigationModel.getDesignFormat());
            }
            if (navigationModel.getFormType().contentEquals("WORK_FLOW_FORM")) {
                intent = new Intent(context, QueryIndexColumnsGridActivity.class);
                intent.putExtra("s_childForm", "ChildForm");
                intent.putExtra("s_app_name", queryName);
                intent.putExtra("s_design_format", navigationModel.getDesignFormat());

                PrefManger.putSharedPreferencesString(context, AppConstants.SP_CHILD_FORM_APP_NAME, strChildAppName);
                PrefManger.putSharedPreferencesString(context, AppConstants.SP_CHILD_FORM_CREATED_BY_ID, strChildCreatedBy);
                PrefManger.putSharedPreferencesString(context, AppConstants.SP_CHILD_FORM_DISTRIBUTION_ID, strChildDistributionId);
            } else if (navigationModel.getFormType().contentEquals(AppConstants.CALL_FORM_MULTI_FORM)) {
                intent = new Intent(context, MainActivity.class);
                intent.putExtra("app_edit", "New");//tablename pending nk
                intent.putExtra("tableName",otherFormDetails.getTableName());
                AppConstants.IS_MULTI_FORM = true;
                AppConstants.currentMultiForm = new XMLHelper().XML_MultiFormApp(navigationModel.getDesignFormat());
                AppConstants.currentMultiForm.setAppVersion(navigationModel.getAppVersion());
                AppConstants.currentMultiForm.setCreateBy(navigationModel.getCreatedBy());
                AppConstants.currentMultiForm.setDistributionId(navigationModel.getDistributionId());
                AppConstants.currentMultiForm.setPostId(navigationModel.getPostId());

                LinkedHashMap<String, String> innerFormsDesignMap = AppConstants.currentMultiForm.getInnerFormsDesignMap();

                if (AppConstants.currentMultiForm.getHome() != null) {
                    for (LinkedHashMap.Entry<String, String> entry : innerFormsDesignMap.entrySet()) {

                        if (entry.getKey().contentEquals(AppConstants.currentMultiForm.getHome())) {
                            AppConstants.currentMultiForm.getNavigationList().add(new FormNavigation(AppConstants.currentMultiForm.getHome(), true), AppConstants.currentMultiForm.getHome());
                            navigationModel.setFormName(entry.getKey());
                            navigationModel.setDesignFormat(entry.getValue());
                            ImproveHelper.saveDesignFormat(context, entry.getValue());
                            AppConstants.MULTI_FORM_TYPE = AppConstants.CALL_FORM_CURRENT_MULTI_FORM_INNER;
                            break;
                        }
                    }
                    String homeIn = AppConstants.currentMultiForm.getHomeIn();
                    int pos = AppConstants.currentMultiForm.getHomeMenuPos();
                    if (homeIn != null && (homeIn.contentEquals(AppConstants.BOTTOM_NAVIGATION)
                            || homeIn.contentEquals(AppConstants.NAVIGATION_MENU)) && pos != -1) {
                        String formName = AppConstants.currentMultiForm.getHome();
                        AppConstants.MULTI_FORM_TYPE = AppConstants.CALL_FORM_SINGLE_FORM;
                        navigationModel.setFormType(AppConstants.CALL_FORM_SINGLE_FORM);
                        getDesignAndNavigate(formName);
                    }
                }else{
                    String homeIn = AppConstants.currentMultiForm.getHomeIn();
                    int pos = AppConstants.currentMultiForm.getHomeMenuPos();
                    if (homeIn != null && (homeIn.contentEquals(AppConstants.BOTTOM_NAVIGATION) || homeIn.contentEquals(AppConstants.NAVIGATION_MENU)) && pos != -1) {
                        String formName = AppConstants.currentMultiForm.getHome();
                        AppConstants.MULTI_FORM_TYPE = AppConstants.CALL_FORM_SINGLE_FORM;
                        getDesignAndNavigate(formName);
                    }
                }
//                intent.putExtra("s_design_format", navigationModel.getDesignFormat());
            } else if (navigationModel.getFormType().contentEquals(AppConstants.CALL_FORM_SINGLE_DATA_MANAGEMENT)) {
                intent = new Intent(context, ViewDataActivity.class);
                //nk step
                System.out.println("=======Step11===============");

                if (navigationModel.getDataManagementOptions() != null && (navigationModel.getDataManagementOptions().isEnableViewData() || 
                        navigationModel.getDataManagementOptions().isEnableEditData() ||
                        navigationModel.getDataManagementOptions().isEnableDeleteData())) {
                    boolean result = false;

                    AppDetailsAdvancedInput appDetailsAdvancedInput = new AppDetailsAdvancedInput();
                    appDetailsAdvancedInput.setOrgId(sessionManager.getOrgIdFromSession());
                    appDetailsAdvancedInput.setPageName(navigationModel.getFormName());
                    appDetailsAdvancedInput.setUserId(sessionManager.getUserDataFromSession().getUserID());
                    appDetailsAdvancedInput.setPostId(sessionManager.getPostsFromSession());
                    appDetailsAdvancedInput.setSubmittedUserPostID(sessionManager.getPostsFromSession());
                    if (navigationModel.getDataManagementOptions().getFetchData() != null) {
                        appDetailsAdvancedInput.setFetchData(navigationModel.getDataManagementOptions().getFetchData());
                    } else {
                        appDetailsAdvancedInput.setFetchData("Login User Post");
                    }
                    appDetailsAdvancedInput.setOrderbyStatus("False");
                    appDetailsAdvancedInput.setOrderByColumns("");
                    appDetailsAdvancedInput.setOrderByType("");
                    appDetailsAdvancedInput.setLazyLoading("False");
                    appDetailsAdvancedInput.setThreshold("2");
                    appDetailsAdvancedInput.setRange("1" + "-" + "1000");
                    //TODO  --- In future need to set order by columns in the below line
                    /*appDetailsAdvancedInput.setLazyOrderKey("SELECT NULL");*/

                    if (navigationModel.getDataManagementOptions().isLazyLoadingEnabled() && appDetailsAdvancedInput.getLazyOrderKey().contentEquals("")) {
                        appDetailsAdvancedInput.setLazyOrderKey("SELECT NULL");
                    }

                    if (navigationModel.getDataManagementOptions().getFilterColumns() != null) {
                        List<FilterColumns> filterColumnsList = new ArrayList<>();
                        for (int i = 0; i <navigationModel.getDataManagementOptions().getFilterColumns().size(); i++) {
                            API_InputParam_Bean param_bean = navigationModel.getDataManagementOptions().getFilterColumns().get(i);
                            if(param_bean.isEnable()){
                            FilterColumns filterColumn = new FilterColumns();
                            filterColumn.setColumnName(param_bean.getInParam_Name());
                            filterColumn.setColumnType("Others");
                            filterColumn.setOperator(ImproveHelper.getOparator(param_bean.getInParam_Operator()));
                            filterColumn.setCondition(param_bean.getInParam_and_or());
                            filterColumn.setCurrentGPS("");
                            filterColumn.setNearBy("");
                            filterColumn.setNoOfRec("");
                            ExpressionMainHelper expressionMainHelper = new ExpressionMainHelper();
                            String expression = "";
                            if (param_bean.getInParam_ExpressionValue() != null) {
                                expression = param_bean.getInParam_ExpressionValue();
                            } else {
                                expression = param_bean.getInParam_Mapped_ID();
                            }
                            String value = expressionMainHelper.ExpressionHelper(context, expression);
                            param_bean.setInParam_Temp_Value(value);
                            filterColumn.setValue(value);
                            if (param_bean.getInParam_Mapped_ID().contentEquals(CONTROL_TYPE_GPS)) {
                                filterColumn.setOperator("");
                                filterColumn.setColumnType("GPS");
                                if (param_bean.getInParam_near_by_distance() != null) {
                                    filterColumn.setNearBy(param_bean.getInParam_near_by_distance());
                                } else {
                                    filterColumn.setNearBy("");
                                }
                                if (param_bean.getInParam_near_by_records() != null) {
                                    filterColumn.setNoOfRec(param_bean.getInParam_near_by_records());
                                } else {
                                    filterColumn.setNoOfRec("");
                                }

                                filterColumn.setValue("");

                                filterColumn.setCurrentGPS(inparam_current_location);
                                param_bean.setCurrentGps(inparam_current_location);
                                //gpsContainInInParams = true;
                            } else {
                                filterColumn.setNearBy("");
                                filterColumn.setNoOfRec("");
                                filterColumn.setCurrentGPS("");

                            }
                            filterColumnsList.add(filterColumn);
                        }
                        }
                        appDetailsAdvancedInput.setFilterColumns(filterColumnsList);
                    }


                      /*  CheckFormHasData getRequest = new CheckFormHasData(appDetailsAdvancedInput, 0);
                        getRequest.execute().get();*/
                    if(navigationModel.getDataManagementOptions()!=null && navigationModel.getDataManagementOptions().getSubFormInMainForm()!=null){
                        subFormInMainFormValue = navigationModel.getDataManagementOptions().getSubFormInMainForm();
                    }else {
                        subFormInMainFormValue = DataCollection.getSubFormInMainForm(appDetails.getDesignFormat());
                    }
                    checkFormHasData(appDetailsAdvancedInput,otherFormDetails);
                }else{
                    dataManagementObjectIssue = true;
                    intent = new Intent(context, MainActivity.class);
                    intent.putExtra("app_edit", "New");
//                    intent.putExtra("tableName",otherFormDetails.getTableName());
                }
            }
            intent.putExtra("tableName",otherFormDetails.getTableName());
            Gson gson = new Gson();
            intent.putExtra("subFormDetails",  gson.toJson(otherFormDetails.getSubFormDetails()));
            intent.putExtra("s_app_version", navigationModel.getAppVersion());
            intent.putExtra("s_app_type", navigationModel.getAppType());
            intent.putExtra("s_org_id", navigationModel.getOrgId());
            intent.putExtra("s_app_name", navigationModel.getFormName());
            if (navigationModel.getFormType().contentEquals("WORK_FLOW_FORM")) {
                intent.putExtra("s_app_name", queryName);
            }
            intent.putExtra("s_app_icon", navigationModel.getAppIcon());
            intent.putExtra("s_created_by", navigationModel.getCreatedBy());
            intent.putExtra("s_user_id", navigationModel.getUserId());
            intent.putExtra("s_distribution_id", navigationModel.getDistributionId());
            intent.putExtra("s_user_post_id", navigationModel.getPostId());
            intent.putExtra("s_resume", navigationModel.isResume());
            intent.putExtra("caller_form_name", navigationModel.getCallerFormName());
            intent.putExtra("form_type", navigationModel.getFormType());
            intent.putExtra("from", "CALL_FORM");
            intent.putExtra("exit_to_menu", navigationModel.isCloseAllFormsEnabled());
            intent.putExtra("keep_session", navigationModel.isKeepSessionEnabled());
            intent.putExtra("go_to_home", navigationModel.isGoToHomeEnabled());
            if (navigationModel.isKeepSessionEnabled()) {
                intent.putExtra("keep_session", navigationModel.isKeepSessionEnabled());
                if (AppConstants.KEEP_SESSION_OBJECT.containsKey(navigationModel.getFormName())) {
                    intent.putExtra("jsonObject", AppConstants.KEEP_SESSION_OBJECT.get(navigationModel.getFormName()).toString());
                }
            }
            List<Variable_Bean> list_variables = navigationModel.getList_variables();
            if (list_variables != null && list_variables.size() > 0) {
                Bundle Variables = new Bundle();
                Variables.putSerializable("Variables", (Serializable) list_variables);
                intent.putExtra("VariablesData", Variables);
            }
            if (navigationModel.isAppInMultiForm()) {
                intent.putExtra("appInMultiform", true);
            } else {
                intent.putExtra("appInMultiform", false);
            }
//            context.startActivity(intent);
            if (!navigationModel.getFormType().contentEquals(AppConstants.CALL_FORM_SINGLE_DATA_MANAGEMENT)||
                    (navigationModel.getFormType().contentEquals(AppConstants.CALL_FORM_SINGLE_DATA_MANAGEMENT)&&dataManagementObjectIssue)) {

                if (navigationModel.getFormType().contentEquals(AppConstants.CALL_FORM_MULTI_FORM)) {
                    String homeIn = AppConstants.currentMultiForm.getHomeIn();
                    int pos = AppConstants.currentMultiForm.getHomeMenuPos();
                    if (homeIn != null && (homeIn.contentEquals(AppConstants.BOTTOM_NAVIGATION)
                            || homeIn.contentEquals(AppConstants.NAVIGATION_MENU)) && pos != -1) {

                    }else{
                        context.startActivity(intent);
                    }

                }else {
                    context.startActivity(intent);
                }
            }else{
                dataManagementObjectIssue = false;
            }
            if (navigationModel.isCloseParentEnabled()) {
                ((Activity) context).finish();
            }
            if (navigationModel.isCloseAllFormsEnabled()) {
                ((Activity) context).finish();
                ((Activity) context).finishAffinity();
            }
            if (navigationModel.isGoToHomeEnabled()) {
                ((Activity) context).finish();
            }

            if (isSaveThisActivitySession()) {
                AppConstants.KEEP_SESSION_VIEW_MAP.put(currentAppName, activityView);
                AppConstants.KEEP_SESSION_LAYOUT_MAP.put(currentAppName, frameLayout);
                ((Activity) context).finish();
            }
            ((MainActivity) context).dismissProgressDialog();
        } catch (Exception e) {
            ((MainActivity) context).dismissProgressDialog();
            improveHelper.improveException(context, TAG, "navigateToNext", e);
        }
    }

    public boolean isSaveThisActivitySession() {
        return saveThisActivitySession;
    }

    public void setSaveThisActivitySession(boolean saveThisActivitySession) {
        this.saveThisActivitySession = saveThisActivitySession;
    }

    public View getActivityView() {
        return activityView;
    }

    public void setActivityView(View activityView) {
        this.activityView = activityView;
    }

    public String getCurrentAppName() {
        return currentAppName;
    }

    public void setCurrentAppName(String currentAppName) {
        this.currentAppName = currentAppName;
    }

    public FrameLayout getFrameLayout() {
        return frameLayout;
    }

    public void setFrameLayout(FrameLayout frameLayout) {
        this.frameLayout = frameLayout;
    }




    public void openActivity(boolean result,AppDetailsAdvancedInput appDetailsAdvancedInput) {
        Intent intent = null;
        boolean openIntent = true;
        if (result) {
            if (hasGPS() && navigationModel.getDataManagementOptions().getIndexPageDetails().getIndexPageTemplateId()==6) {
                intent = new Intent(context, ViewMapAndDataActivity.class);
            } else {
                intent = new Intent(context, ViewDataActivity.class);
            }
        } else if (!navigationModel.getDataManagementOptions().getRecordInsertionType().equalsIgnoreCase("None")) {
            intent = new Intent(context, MainActivity.class);
            intent.putExtra("app_edit", "New");
            intent.putExtra("tableName",appDetails.getTableName());
        } else {
            openIntent = false;
        }
        if (openIntent) {
            intent.putExtra("tableName",appDetails.getTableName());
            Gson gson = new Gson();
            intent.putExtra("subFormDetails",  gson.toJson(appDetails.getSubFormDetails()));
            intent.putExtra("jsonArray", jsonArray.toString());
            intent.putExtra("s_app_version", navigationModel.getAppVersion());
            intent.putExtra("s_app_type", navigationModel.getAppType());
            intent.putExtra("s_app_icon", navigationModel.getAppIcon());
            intent.putExtra("s_app_mode", appDetails.getAppMode());
            if (AppConstants.currentMultiForm != null) {
                intent.putExtra("s_app_type", "MultiForm");
            }
            intent.putExtra("s_org_id", sessionManager.getOrgIdFromSession());
            intent.putExtra("s_app_name", navigationModel.getFormName());
            intent.putExtra("s_created_by", navigationModel.getCreatedBy());
            intent.putExtra("s_user_id", sessionManager.getUserDataFromSession().getUserID());
            intent.putExtra("s_distribution_id", navigationModel.getDistributionId());
            intent.putExtra("s_user_post_id", navigationModel.getPostId());
            intent.putExtra("form_type", navigationModel.getFormType());
            intent.putExtra("from", "CALL_FORM");
            intent.putExtra("exit_to_menu", navigationModel.isCloseAllFormsEnabled());
            intent.putExtra("keep_session", navigationModel.isKeepSessionEnabled());
            intent.putExtra("go_to_home", navigationModel.isGoToHomeEnabled());
            DataManagementOptions dataManagementOptions = navigationModel.getDataManagementOptions();
            intent.putExtra("dataManagementOptions", dataManagementOptions);
            intent.putExtra("visibilityManagementOptions", navigationModel.getVisibilityManagementOptions());
            intent.putExtra("formControls",   formControls);
            List<Variable_Bean> list_variables = navigationModel.getList_variables();
            if (list_variables != null && list_variables.size() > 0) {
                Bundle Variables = new Bundle();
                Variables.putSerializable("Variables", (Serializable) list_variables);
                intent.putExtra("VariablesData", Variables);
            }

            if (appDetailsAdvancedInput.getFilterColumns() != null && appDetailsAdvancedInput.getFilterColumns().size() > 0) {
                Bundle filters = new Bundle();
                filters.putSerializable("filters", (Serializable) appDetailsAdvancedInput.getFilterColumns());
                intent.putExtra("filtersData", filters);
            }
            ((MainActivity) context).dismissProgressDialog();
            context.startActivity(intent);
            if (navigationModel.isCloseParentEnabled()) {
                ((Activity) context).finish();
            }
            if (navigationModel.isCloseAllFormsEnabled()) {
                ((Activity) context).finish();
                ((Activity) context).finishAffinity();
            }
            if (navigationModel.isGoToHomeEnabled()) {
                ((Activity) context).finish();
            }
        } else {
            improveHelper.my_showAlert(context, "Error info!", "Add New Record disabled", "2");
        }
    }

    private boolean hasGPS() {
        boolean hasGPS = false;
        String controlName = null;
        for (Control control :mainFormControls) {
            if (control.getControlType() != null && (control.getControlType().equalsIgnoreCase(CONTROL_TYPE_GPS) ||
                    control.isEnableImageWithGps())) {
                hasGPS = true;
                controlName = control.getControlName()+"_Coordinates";
                break;
            }
        }
        if(hasGPS){
            if(table_columns.contains(controlName)){
                hasGPS=true;
            }else{
                hasGPS = false;
            }
        }
        return hasGPS;
    }
    private void openDetailedView(JSONObject jsonObject) {
        try {
            DataManagementOptions dataManagementOptions = navigationModel.getDataManagementOptions();
            if(dataManagementOptions.getDetailedPageDetails().isDetailPage()){
                Intent intent = new Intent(context, DetailedPageActivity.class);

                DetailedPageData detailedPageData = new DetailedPageData();
                detailedPageData.setJsonObject(jsonObject.toString());
                detailedPageData.setDataManagementOptions(dataManagementOptions);
                detailedPageData.setAppName(navigationModel.getFormName());
                detailedPageData.setTableName(appDetails.getTableName());
                detailedPageData.setSubFormDetails(appDetails.getSubFormDetails());
                detailedPageData.setAppVersion(navigationModel.getAppVersion());
                detailedPageData.setAppType( navigationModel.getAppType());
                detailedPageData.setDisplayAppName(navigationModel.getFormName());
                detailedPageData.setDisplayIcon(navigationModel.getAppIcon());
                detailedPageData.setCreatedBy(navigationModel.getCreatedBy());
                detailedPageData.setUserID( sessionManager.getUserDataFromSession().getUserID());
                detailedPageData.setDistributionID(navigationModel.getDistributionId());
                detailedPageData.setUserLocationStructure("");
                detailedPageData.setUserPostID(navigationModel.getPostId());
                detailedPageData.setFromActivity("ViewData");
                detailedPageData.setAppIcon( navigationModel.getAppIcon());
                detailedPageData.setFormLevelTranslation(appDetails.getFormLevelTranslation());

                intent.putExtra("DetailedPageData",(Serializable) detailedPageData);

                intent.putExtra("from", "CALL_FORM");
                intent.putExtra("exit_to_menu", navigationModel.isCloseAllFormsEnabled());
                intent.putExtra("keep_session", navigationModel.isKeepSessionEnabled());
                intent.putExtra("go_to_home", navigationModel.isGoToHomeEnabled());
                ((MainActivity)context).dismissProgressDialog();
                ((Activity) context).startActivityForResult(intent, 0);
            }else{
                Toast.makeText(context, "Detailed Page disabled", Toast.LENGTH_SHORT).show();
            }} catch (Exception e) {
            improveHelper.improveException(context, TAG, "viewRow", e);
        }
    }
    private void openDetailedViewCopy(JSONObject jsonObject) {
        try {

            Intent intent = new Intent(context, DetailedPageActivity.class);

            intent.putExtra("jsonObject", jsonObject.toString());


            intent.putExtra("Trans_ID", jsonObject.getString("Trans_ID"));
            intent.putExtra("appName", navigationModel.getFormName());
            intent.putExtra("s_childForm", "EditForm");
            intent.putExtra("s_app_version", navigationModel.getAppVersion());
            intent.putExtra("s_app_type", navigationModel.getAppType());
            intent.putExtra("s_org_id", sessionManager.getOrgIdFromSession());
            intent.putExtra("s_app_name", navigationModel.getFormName());
            intent.putExtra("s_app_icon", navigationModel.getAppIcon());
            intent.putExtra("s_created_by", navigationModel.getCreatedBy());
            intent.putExtra("s_user_id", sessionManager.getUserDataFromSession().getUserID());
            intent.putExtra("s_distribution_id", navigationModel.getDistributionId());
//            intent.putExtra("s_user_location_Structure", strUserLocationStructure);
            intent.putExtra("s_user_post_id", sessionManager.getPostsFromSession());
            intent.putExtra("app_edit", "Edit");
            intent.putExtra("fromActivity", "ViewData");
            intent.putExtra("from", "CALL_FORM");
            intent.putExtra("exit_to_menu", navigationModel.isCloseAllFormsEnabled());
            intent.putExtra("keep_session", navigationModel.isKeepSessionEnabled());
            intent.putExtra("go_to_home", navigationModel.isGoToHomeEnabled());

            intent.putExtra("tableName",appDetails.getTableName());
            Gson gson = new Gson();
            intent.putExtra("subFormDetails",  gson.toJson(appDetails.getSubFormDetails()));
            intent.putExtra("s_app_mode", appDetails.getAppMode());
            DataManagementOptions dataManagementOptions = navigationModel.getDataManagementOptions();
            intent.putExtra("dataManagementOptions", dataManagementOptions);
            intent.putExtra("visibilityManagementOptions", navigationModel.getVisibilityManagementOptions());
            intent.putExtra("formControls",   formControls);
            List<Variable_Bean> list_variables = navigationModel.getList_variables();
            if (list_variables != null && list_variables.size() > 0) {
                Bundle Variables = new Bundle();
                Variables.putSerializable("Variables", (Serializable) list_variables);
                intent.putExtra("VariablesData", Variables);
            }
            intent.putExtra("form_type", navigationModel.getFormType());

            ((MainActivity) context).dismissProgressDialog();

            ((Activity) context).startActivityForResult(intent, 0);
        } catch (Exception e) {
            ((MainActivity) context).dismissProgressDialog();
            improveHelper.improveException(context, TAG, "viewRow", e);
        }
    }

    private void editRow(JSONObject jsonObject) {
        try {

            Intent intent = new Intent(context, MainActivity.class);

            intent.putExtra("jsonObject", jsonObject.toString());


            intent.putExtra("Trans_ID", jsonObject.getString("Trans_ID"));
            intent.putExtra("appName", navigationModel.getFormName());
            intent.putExtra("s_childForm", "EditForm");
            intent.putExtra("s_app_version", navigationModel.getAppVersion());
            intent.putExtra("s_app_type", navigationModel.getAppType());
            intent.putExtra("s_org_id", sessionManager.getOrgIdFromSession());
            intent.putExtra("s_app_name", navigationModel.getFormName());
            intent.putExtra("s_created_by", navigationModel.getCreatedBy());
            intent.putExtra("s_user_id", sessionManager.getUserDataFromSession().getUserID());
            intent.putExtra("s_distribution_id", navigationModel.getDistributionId());
            intent.putExtra("s_user_location_Structure", "");
            intent.putExtra("app_edit", "Edit");
            List<Variable_Bean> list_variables = navigationModel.getList_variables();
            if (list_variables != null && list_variables.size() > 0) {
                Bundle Variables = new Bundle();
                Variables.putSerializable("Variables", (Serializable) list_variables);
                intent.putExtra("VariablesData", Variables);
            }
            intent.putExtra("tableName",appDetails.getTableName());
            Gson gson = new Gson();
            intent.putExtra("subFormDetails",  gson.toJson(appDetails.getSubFormDetails()));
            intent.putExtra("s_app_icon", navigationModel.getAppIcon());
            intent.putExtra("s_app_mode", appDetails.getAppMode());
            intent.putExtra("s_user_post_id", navigationModel.getPostId());
            intent.putExtra("form_type", navigationModel.getFormType());
            DataManagementOptions dataManagementOptions = navigationModel.getDataManagementOptions();
            intent.putExtra("dataManagementOptions", dataManagementOptions);
            intent.putExtra("visibilityManagementOptions", navigationModel.getVisibilityManagementOptions());
            intent.putExtra("formControls",   formControls);

            intent.putExtra("from", "CALL_FORM");
            intent.putExtra("exit_to_menu", navigationModel.isCloseAllFormsEnabled());
            intent.putExtra("keep_session", navigationModel.isKeepSessionEnabled());
            intent.putExtra("go_to_home", navigationModel.isGoToHomeEnabled());
            ((MainActivity)context).dismissProgressDialog();
            ((Activity) context).startActivityForResult(intent, 0);
//            startActivityForResult(intent, BACK_TO_LIST_FLAG);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getTransIdsToDelete", e);
        }

    }
    private JSONArray getOfflineData(String tableName, AppDetailsAdvancedInput appDetailsAdvancedInput) {

        JSONArray jsonArray = new JSONArray();
        String status = "0";
        boolean result = false;
        int type = 3;
        String filters = null;
        try {
            JSONArray jsonArrayMain = new JSONArray();
            jsonArray = new JSONArray();
            String trans_id = tableName + "_Trans_Id";
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
            jsonArray = improveDataBase.getOfflineDataWithFiltersLimit(appDetailsAdvancedInput.getOrgId(), appDetailsAdvancedInput.getPageName(), "0", "2", appDetailsAdvancedInput.getUserId(), appDetailsAdvancedInput.getPostId(), tableName, trans_id, filters, type,2);

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getOfflineData", e);
        }
        return jsonArray;
    }

    private String getFilters(List<FilterColumns> filterColumnsList) {
        StringBuilder filters = new StringBuilder();
        for (FilterColumns filterColumn : filterColumnsList) {
            filters.append(" ").append(filterColumn.getColumnName()).append(" ").append(filterColumn.getOperator()).append(" ").append("'").append(filterColumn.getValue()).append("'").append(" ").append(filterColumn.getCondition());
        }
        String filterValue =  filters.toString();
        return filterValue;
    }

    public void checkFormHasData(AppDetailsAdvancedInput appDetailsAdvancedInput,AppDetails otherFormDetails) {
    jsonArray  = new JSONArray();
        final AppDetails[] callFormDesign = {otherFormDetails};
    result = false;
        //SubForm Columns For SubForm in MainForm
        if (!subFormInMainFormValue.equalsIgnoreCase("")) {
            subFormColumns = getSubFormColumns();
        }
//    improveHelper.showProgressDialog("Please wait...!");
        try {
            checkFormdataThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (isNetworkStatusAvialable(context) && (appDetails.getAppMode() != null && appDetails.getAppMode().equalsIgnoreCase("Online"))) {
                                    GetServices getServices = RetrofitUtils.getUserService();
//                Call<ResponseBody> getAllAppNamesDataCall = getServices.iGetAppDataOnline(getAllAppNamesData);
                                    Call<ResponseBody> getAllAppNamesDataCall = getServices.iGetAppDataOffline(sessionManager.getAuthorizationTokenId(),appDetailsAdvancedInput);
                                    getAllAppNamesDataCall.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            String json = null;
                                            try {
                                                json = response.body().string();
                                                Log.d(TAG, "appdata: " + json);
                                                JSONObject responseObj = new JSONObject(json);
                                                if (responseObj.getString("Status").contentEquals("200") && !responseObj.getString("Message").equalsIgnoreCase("Data Not Found")) {
                                                    jsonArray = new JSONArray((responseObj.getString("Data")));
                                                    nextIntentThread.start();
                                                    nextIntentThread.join();
                                                } else {
                                                    result = false;
                                                    improveHelper.dismissProgressDialog();
                                                    Toast.makeText(context, responseObj.getString("Message"), Toast.LENGTH_SHORT).show();
                                                    nextIntentThread.start();
                                                    nextIntentThread.join();
//                                openActivity(result,adapterPosition);
                                                }
                                            } catch (Exception e) {
                                                result = false;
//                            openActivity(result,adapterPosition);
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                        }
                                    });} else {
                                    if(callFormDesign[0] ==null){
                                        callFormDesign[0] = improveDataBase.getAppDetails(sessionManager.getOrgIdFromSession(), appDetailsAdvancedInput.getPageName());
                                    }
                                    if(callFormDesign[0] !=null){
                                       /* jsonArray = getOfflineData(appDetails.getTableName(), appDetailsAdvancedInput);
                                        nextIntentThread.start();
                                        nextIntentThread.join();*/
                                  getOfflineDataWithSubForm(callFormDesign[0],appDetailsAdvancedInput);
                                }else{
                                        ((MainActivity) context).dismissProgressDialog();
                                    }
                                }
                            } catch (Exception e) {
                                ((MainActivity) context).dismissProgressDialog();
                                improveHelper.improveException(context, TAG, "checkformhasData", e);
                            }
                        }
                    });
                }
            });

            checkFormdataThread.start();
            checkFormdataThread.join();

            nextIntentThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                ((MainActivity) context).dismissProgressDialog();
                            if (jsonArray.length() == 0) {
                                result = false;
                                openActivity(result,appDetailsAdvancedInput);
                            } else {
                                result = true;
                                if(navigationModel.getDataManagementOptions()!=null && navigationModel.getDataManagementOptions().getList_Table_Columns()!=null){
                                    table_columns = navigationModel.getDataManagementOptions().getList_Table_Columns();
                                }else {
                                    table_columns = DataCollection.getList_Table_Columns(appDetails.getDesignFormat());
                                    navigationModel.getDataManagementOptions().setList_Table_Columns(table_columns);
                                }
                                if(!subFormInMainFormValue.equalsIgnoreCase("")){
                                    checkAndOpenActivity(appDetailsAdvancedInput);
                                }else{
                                    if ((jsonArray.length() == 1 && navigationModel.getDataManagementOptions().isSkipIndexPage())
                                        || (navigationModel.getDataManagementOptions().getRecordInsertionType()!=null&&navigationModel.getDataManagementOptions().getRecordInsertionType().equalsIgnoreCase("Single"))) {
                                    if (navigationModel.getDataManagementOptions().isEnableEditData()) {
                                        editRow(jsonArray.getJSONObject(0));
                                    } else {
                                        openDetailedView(jsonArray.getJSONObject(0));
                                    }
                                } else {
                                    openActivity(result,appDetailsAdvancedInput);
                                }
                                }
                            }   } catch (Exception e) {
                                ((MainActivity) context).dismissProgressDialog();
                                improveHelper.improveException(context, TAG, "checkformhasData", e);
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "uploadInputFilesandContinueDML", e);
        }
    }

    private void checkAndOpenActivity(AppDetailsAdvancedInput appDetailsAdvancedInput) {
try {

    IndexValueToCompare = new ArrayList<>();
    newJsonArray = new JSONArray();

        try {
            JSONObject firstObject = jsonArray.getJSONObject(0);
            JSONObject jsonObjectFirst = new JSONObject();
            jsonObjectFirst.put("Trans_ID", firstObject.getString("Trans_ID"));

            for (String subFormColumn : subFormColumns) {
                if (table_columns.contains(subFormColumn)) {
                    jsonObjectFirst.put(subFormColumn, firstObject.getString(subFormColumn));
                }
            }
//            jsonObjectFirst.put("objective_objective_type", firstObject.getString("objective_objective_type"));
//            jsonObjectFirst.put("objective_objective_typeid", firstObject.getString("objective_objective_typeid"));

            JSONArray jsonArrayRowsFirst = new JSONArray();
            jsonArrayRowsFirst.put(jsonObjectFirst);

            JSONObject jsonObjectSubFormFirst = new JSONObject();
            jsonObjectSubFormFirst.put(subFormInMainFormValue, jsonArrayRowsFirst);

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
                    jsonObject.put("objective_objective_type", object.getString("objective_objective_type"));
                    jsonObject.put("objective_objective_typeid", object.getString("objective_objective_typeid"));
                    for (String subFormColumn : subFormColumns) {
                        if (table_columns.contains(subFormColumn)) {
                            jsonObject.put(subFormColumn, object.getString(subFormColumn));
                        }
                    }

                    JSONArray jsonArrayRows = new JSONArray();
                    jsonArrayRows.put(jsonObject);

                    JSONObject jsonObjectSubForm = new JSONObject();
                    jsonObjectSubForm.put(subFormInMainFormValue, jsonArrayRows);

                    JSONArray jsonArraySubForm = new JSONArray();
                    jsonArraySubForm.put(jsonObjectSubForm);

                    object.put("SubForm", jsonArraySubForm);
                    //mainform
                    newJsonArray.put(object);
                }
            }
            if ((newJsonArray.length() == 1 && navigationModel.getDataManagementOptions().isSkipIndexPage())
                    || navigationModel.getDataManagementOptions().getRecordInsertionType().equalsIgnoreCase("Single")) {
                if (navigationModel.getDataManagementOptions().isEnableEditData()) {
                    editRow(newJsonArray.getJSONObject(0));
                } else {
                    openDetailedView(newJsonArray.getJSONObject(0));
                }
            } else {
                openActivity(result,appDetailsAdvancedInput);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getDistinctData", e);
        }
        }catch (Exception e){

}
    }

    private void getOfflineDataWithSubForm(AppDetails appDetails,AppDetailsAdvancedInput appDetailsAdvancedInput) {
        try {
            jsonArray = getOfflineData(appDetails.getTableName(), appDetailsAdvancedInput);
            //**subform data**//
            List<String> subFormNames = new ArrayList<>();
           /* for (ControlObject controlObject : dataCollectionObject.getControls_list()) {
                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SUBFORM) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {
                    subFormNames.add(controlObject.getControlName());
                }
            }*/
            if(subFormControls!= null && subFormControls.size()>0){
            for(SubControl subControl:subFormControls){
                subFormNames.add(subControl.getSubFormName());
            }
            if (subFormNames.size() > 0) {
                JSONArray jsonArraySubForm = new JSONArray();
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonArraySubForm = new JSONArray();
                    JSONObject jsonObjectSubFormRows = new JSONObject();
                    for (String subformName : subFormNames) {
                        jsonObjectSubFormRows = new JSONObject();
                        String tablename = appDetails.getTableName() + "_" + improveHelper.replaceWithUnderscore(subformName);
                        String ref_trans_id = tablename + "_Ref_TransID";
                        String sf_trans_id = tablename + "_Trans_Id";
                        String is_active = tablename + "_Is_Active";
                        JSONArray jsonArraySubFormRows = improveDataBase.getOfflineDataFromSubFormTableJSON("", "", "0", "", tablename, sf_trans_id, ref_trans_id, jsonArray.getJSONObject(i).getString(AppConstants.Trans_id), is_active);
                        jsonObjectSubFormRows.put(subformName, jsonArraySubFormRows);
                        jsonArraySubForm.put(jsonObjectSubFormRows);
                        JSONObject jsonObjectMainFormRow = jsonArray.getJSONObject(i);
                        jsonObjectMainFormRow.put("SubForm", jsonArraySubForm);
                    }
                }
            }}
            nextIntentThread.start();
            nextIntentThread.join();
        }catch (Exception e){
            Log.d(TAG, "getOfflineDataWithSubForm:"+Log.getStackTraceString(e));
        }
    }

    public List<String> getSubFormColumns() {
        List<String> subFormColumns = new ArrayList<>();
        try {
            if(subFormControls!= null && subFormControls.size()>0){
            for (SubControl subControl : subFormControls) {
                    if (subControl.getSubFormName().equalsIgnoreCase(subFormInMainFormValue)) {
                        for (Control control : subControl.getSubformControlsList()) {
                            subFormColumns.add(control.getControlName());
                            if (control.getControlType().contentEquals(AppConstants.CONTROL_TYPE_RADIO_BUTTON)
                                    || control.getControlType().contentEquals(AppConstants.CONTROL_TYPE_DROP_DOWN)
                                    || control.getControlType().contentEquals(AppConstants.CONTROL_TYPE_CHECKBOX)
                                    || control.getControlType().contentEquals(AppConstants.CONTROL_TYPE_CHECK_LIST)
                                    || control.getControlType().contentEquals(AppConstants.CONTROL_TYPE_USER)
                                    || control.getControlType().contentEquals(AppConstants.CONTROL_TYPE_POST)) {
                                subFormColumns.add(control.getControlName() + "id");
                            } else if (control.getControlType().contentEquals(AppConstants.CONTROL_TYPE_GPS)) {
                                subFormColumns.remove(control.getControlName());
                                subFormColumns.add(control.getControlName() + "_Coordinates");
                                subFormColumns.add(control.getControlName() + "_type");
                            } else if (control.getControlType().contentEquals(AppConstants.CONTROL_TYPE_DATA)) {
                                subFormColumns.add(control.getControlName() + "_id");
                            } else if (control.getControlType().contentEquals(CONTROL_TYPE_CAMERA) && control.isEnableImageWithGps()) {
                                subFormColumns.add(control.getControlName() + "_Coordinates");
                                subFormColumns.add(control.getControlName() + "_type");
                            }
                        }
                    }
                
            }}
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getSubFormColumns", e);
        }

        return subFormColumns;
    }

    private boolean checkNextObject(JSONObject object) {
        boolean res = false;
        try {
            StringBuilder indexValues = new StringBuilder();
            try {
                List<String> indexColumns = new ArrayList<>();
//                indexColumns = navigationModel.getDataManagementOptions().getIndexPageColumns();

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
                    if (table_columns.contains(subFormColumn)) {
                        jsonObjectNewRow.put(subFormColumn, object.getString(subFormColumn));
                    }
                }
                int position = IndexValueToCompare.indexOf(String.valueOf(indexValues));
                JSONObject jsonObject1 = newJsonArray.getJSONObject(position);
                JSONArray jsonArray1 = jsonObject1.getJSONArray("SubForm").getJSONObject(0).getJSONArray(subFormInMainFormValue);
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
//            indexColumns = navigationModel.getDataManagementOptions().getIndexPageColumns();
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
}
