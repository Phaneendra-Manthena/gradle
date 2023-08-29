package com.bhargo.user.fragments;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CAMERA;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_FILE_BROWSING;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Java_Beans.DataManagementOptions;
import com.bhargo.user.MainActivity;
import com.bhargo.user.Query.QueryDetailsActivity;
import com.bhargo.user.R;
import com.bhargo.user.adapters.AppsAdvancedAdapter;
import com.bhargo.user.adapters.AppsAdvancedAdapterCopy;
import com.bhargo.user.custom.CustomButton;
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
import com.bhargo.user.pojos.GetAllAppModel;
import com.bhargo.user.pojos.OfflineDataTransaction;
import com.bhargo.user.pojos.SubControl;
import com.bhargo.user.pojos.SubFormTableColumns;
import com.bhargo.user.screens.DetailedPageActivity;
import com.bhargo.user.screens.ViewDataActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseFragment;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewDataFragment extends BaseFragment implements ItemClickListenerAdvanced, View.OnClickListener, TextWatcher {

    private static final String TAG = "ViewDataActivity";
    private static final int BACK_TO_LIST_FLAG = 2002;
    RecyclerView rv_apps;
    ImageView iv_appListRefresh;
    GetServices getServices;
    Context context;
    AppsAdvancedAdapter appsAdvancedAdapter;
    RelativeLayout rl_AppsListMain;
    CustomButton cb_add_row;
    FloatingActionButton fb_add_row;
    CustomTextView ct_alNoRecords;
    ImproveHelper improveHelper;
    SessionManager sessionManager;
    ImproveDataBase improveDataBase;
//    DataCollectionObject dataCollectionObject;
DataManagementOptions dataManagementOptions;
    FormControls formControls;
    JSONArray jsonArray;
    JSONArray jsonArrayMain;
    List<JSONArray> jsonArrayList;
    List<String> List_Columns;
    List<Control> List_ControlTypes;
    boolean image = false;
    private View rootView;
    private ViewGroup viewGroup;
    private GetAllAppModel getAllAppModel;
    private String strPostId, strAppDesign, strAppVersion, strOrgId, strAppType, strAppName,strDisplayAppName, strCreatedBy, strUserId, strDistributionId, strUserLocationStructure, strFrom_InTaskDetails;
    private boolean isResume;
    private String callerFormName;
    private String callerFormType;
    private AppDetails appDetailsList;
    private Intent getIntent;
    List<String> IndexValueToCompare;
    JSONArray newJsonArray = new JSONArray();
    AutoCompleteTextView actv_search;
    List<String> subFormColumns = new ArrayList<>();
    List<String> viewColumnsList;
    String tableName;
    String appMode;
    List<SubFormTableColumns> subFormDetails;

    public ViewDataFragment() {

    }

    public ViewDataFragment(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_apps_list_advanced, container, false);
        viewGroup = container;
        improveHelper = new ImproveHelper(getActivity());
        initViews();

        return rootView;

    }

    private void initViews() {
        try {
            context = getActivity();
            improveDataBase = new ImproveDataBase(context);
            getIntent = getActivity().getIntent();
            if (getIntent != null) {

                if (getIntent.hasExtra("s_resume")) {
                    isResume = getIntent.getBooleanExtra("s_resume", false);
                    callerFormName = getIntent.getStringExtra("caller_form_name");
                    callerFormType = getIntent.getStringExtra("form_type");
                }
//                dataCollectionObject = (DataCollectionObject) getIntent.getSerializableExtra("dataCollectionObject");
                dataManagementOptions = (DataManagementOptions) getIntent.getSerializableExtra("dataManagementOptions");
                formControls = (FormControls) getIntent.getSerializableExtra("formControls");
                appMode = getIntent.getStringExtra("s_app_mode");
//                strAppDesign = getIntent.getStringExtra("s_app_design");
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


            sessionManager = new SessionManager(context);
            getServices = RetrofitUtils.getUserService();


            Toolbar toolbar = rootView.findViewById(R.id.toolbar);
            toolbar.setVisibility(View.GONE);
            ct_alNoRecords = rootView.findViewById(R.id.ct_alNoRecords);
            rv_apps = rootView.findViewById(R.id.rv_apps);
            rl_AppsListMain = rootView.findViewById(R.id.rl_AppsListMain);
            iv_appListRefresh = rootView.findViewById(R.id.iv_appListRefresh);
            cb_add_row = rootView.findViewById(R.id.cb_add_row);
            actv_search = rootView.findViewById(R.id.actv_search);
            cb_add_row.setOnClickListener(this);
            actv_search.addTextChangedListener(this);
            appDetailsList = improveDataBase.getAppDetails(strOrgId, strAppName, sessionManager.getUserDataFromSession().getUserID());

            if (dataManagementOptions.isEnableViewData() || dataManagementOptions.isEnableEditData() || dataManagementOptions.isEnableDeleteData()) {
//                tableName = strCreatedBy + "_" + ImproveHelper.getTableNameWithOutSpace(strAppName);
                if(dataManagementOptions.getRecordInsertionType().equalsIgnoreCase("None")){
                    cb_add_row.setVisibility(View.GONE);
                }
                List_Columns = new ArrayList<>();
                List_ControlTypes = new ArrayList<>();
                List_Columns = dataManagementOptions.getList_Table_Columns();
                List_ControlTypes = formControls.getMainFormControlsList();
                if (dataManagementOptions.getCaptionForAdd() != null && !dataManagementOptions.getCaptionForAdd().isEmpty()) {
                    cb_add_row.setText(dataManagementOptions.getCaptionForAdd());
                }
                /*if (List_ControlTypes.contains(AppConstants.CONTROL_TYPE_CAMERA) || List_ControlTypes.contains(AppConstants.CONTROL_TYPE_FILE_BROWSING)) {
                    image = true;
                }*/
                //SubForm Columns For SubForm in MainForm
                if (!dataManagementOptions.getSubFormInMainForm().equalsIgnoreCase("")) {
                    subFormColumns = getSubFormColumns();
                }

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                rv_apps.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

                getViewColumnsAndShowInList();
//        appsAdvancedAdapter = new AppsAdvancedAdapter(context, jsonArray, List_Columns, List_ControlTypes, true, true, image,status);
//                appsAdvancedAdapter = new AppsAdvancedAdapter(context, jsonArray, List_Columns, List_ControlTypes, dataCollectionObject.isEnableEditData(), dataCollectionObject.isEnableDeleteData(), image, status, strAppName, dataCollectionObject.getPreviewColumns());
//                rv_apps.setAdapter(appsAdvancedAdapter);
//                appsAdvancedAdapter.setCustomClickListener(ViewDataFragment.this);

                getAppDataMain();
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "initViews", e);
        }
    }

   /* private void getViewColumnsAndShowInList() {
        List<String> viewColumnsList = new ArrayList<>();
        viewColumnsList.add("column_one_holder");
        viewColumnsList.add("column_two_holder");
        viewColumnsList.add("image_control_holder");
        List<String> addedControlTypes = new ArrayList<>();
        for (String columnName : List_Columns) {
            String controlType = DataCollectionObject.Control_Types.get(columnName);
            if (controlType != null && !checkImageOrFileControlExists(addedControlTypes) &&(controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA) || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_FILE_BROWSING))) {
                viewColumnsList.set(2,columnName);
                addedControlTypes.add(controlType);
            }else if (controlType != null && !checkControlExists("column_one",addedControlTypes) && checkPreviewColumns(columnName)){
                viewColumnsList.set(0,columnName);
                addedControlTypes.add("column_one");
            }else if (controlType != null && !checkControlExists("column_two",addedControlTypes) && checkPreviewColumns(columnName)){
                viewColumnsList.set(1,columnName);
                addedControlTypes.add("column_two");
            }
            if(checkListSize(viewColumnsList)){
                break;
            }
        }
        if(dataCollectionObject.getPreviewColumns()!=null && !viewColumnsList.get(0).equalsIgnoreCase(dataCollectionObject.getPreviewColumns().get(0))){
            Collections.swap(viewColumnsList, 0, 1);
        }
        String filetype=null;
        if(addedControlTypes.contains(CONTROL_TYPE_CAMERA)){
            filetype= CONTROL_TYPE_CAMERA;
        }else if(addedControlTypes.contains(CONTROL_TYPE_FILE_BROWSING)) {
            filetype = CONTROL_TYPE_FILE_BROWSING;
        }
        appsAdvancedAdapter = new AppsAdvancedAdapter(context, jsonArray, viewColumnsList, List_ControlTypes, dataCollectionObject.isEnableEditData(), dataCollectionObject.isEnableDeleteData(), image, status, strAppName, filetype);
        rv_apps.setAdapter(appsAdvancedAdapter);
        appsAdvancedAdapter.setCustomClickListener(ViewDataFragment.this);

    }*/

    private void getViewColumnsAndShowInList() {

        appsAdvancedAdapter = new AppsAdvancedAdapter(context, jsonArray, strAppName,  tableName, appMode,dataManagementOptions);
        rv_apps.setAdapter(appsAdvancedAdapter);
        appsAdvancedAdapter.setCustomClickListener(ViewDataFragment.this);


    }
    private boolean  getImagePreviewColumn(String columnName) {

        for(EditOrViewColumns editOrViewColumns:dataManagementOptions.getPreviewColumns()){
            if(editOrViewColumns.getColumnName().equalsIgnoreCase(columnName) && editOrViewColumns.getColumnType()!=null && editOrViewColumns.getColumnType().equalsIgnoreCase(getString(R.string.image))){
                return true;
            }
        }
        return false;
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


    public void getAppDataMain() {
        try {
            if (jsonArray.length() == 0) {
                ct_alNoRecords.setVisibility(View.VISIBLE);
                rv_apps.setVisibility(View.GONE);
            } else {
                ct_alNoRecords.setVisibility(View.GONE);
                rv_apps.setVisibility(View.VISIBLE);
                rv_apps.setAdapter(appsAdvancedAdapter);
                appsAdvancedAdapter.updateList(jsonArray);
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "getAppDataMain", e);
        }
    }


    @Override
    public void onCustomClick(Context context, View view, int position, String typeOfAction, String status) {
        JSONObject object;
        try {
            if (status.equalsIgnoreCase("0") || status.equalsIgnoreCase("2")) {
                object = jsonArrayMain.getJSONObject(position);
            } else {
                object = jsonArray.getJSONObject(position);
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
                viewRow(object,status);
            }*/
        } catch (Exception e) {
            Log.d(TAG, "onCustomClick: " + e.toString());
        }
    }

    private void editRow(JSONObject jsonObject, String status) {
        try {

            Intent intent = new Intent(context, MainActivity.class);

            intent.putExtra("jsonObject", jsonObject.toString());

            intent.putExtra("dataManagementOptions", dataManagementOptions);
            intent.putExtra("tableName",tableName);
            Gson gson = new Gson();
            intent.putExtra("subFormDetails",  gson.toJson(subFormDetails));
            intent.putExtra("Trans_id", jsonObject.getString("Trans_ID"));
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

            if (getIntent != null && getIntent.hasExtra("From_InTaskDetails")) {
                intent.putExtra("From_InTaskDetails", "From_InTaskDetails");
            }

//            startActivity(intent);
            startActivityForResult(intent, BACK_TO_LIST_FLAG);
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "editRow", e);
        }

    }

    public void deleteAlert(Context context, int position, String transId, String status, boolean subFormInMainForm, JSONObject object) {
        try {
     /*   try {
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

            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
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
            if (alertDialog.getWindow() != null){
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            alertDialog.show();
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "deleteAlert", e);

        }

    }

    private void deleteRow(int position, String transId, String status, boolean subFormInMainForm, JSONObject object) {
        try {
            if (status.equalsIgnoreCase("0")) {
               deleteOfflineData(object,position);
            } else if (isNetworkStatusAvialable(context)) {
                improveHelper.showProgressDialog(getString(R.string.please_wait));
                AppDetailsAdvancedAction appDetailsAdvancedAction = new AppDetailsAdvancedAction();
                appDetailsAdvancedAction.setOrgId(strOrgId);
                appDetailsAdvancedAction.setUserId(strUserId);
                appDetailsAdvancedAction.setPageName(strAppName);
                appDetailsAdvancedAction.setAction("Delete");
                appDetailsAdvancedAction.setTransID(transId);
                Call<DeviceIdResponse> getAllAppNamesDataCall = getServices.iDeleteAppData(sessionManager.getAuthorizationTokenId(),appDetailsAdvancedAction);
                getAllAppNamesDataCall.enqueue(new Callback<DeviceIdResponse>() {
                    @Override
                    public void onResponse(Call<DeviceIdResponse> call, Response<DeviceIdResponse> response) {
                        improveHelper.dismissProgressDialog();
                        DeviceIdResponse responseData = response.body();
                        if (responseData.getStatus().equalsIgnoreCase("200")) {
                            Toast.makeText(context, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                            if (responseData.getMessage().equalsIgnoreCase("Success")) {
                                //**For Hybrid case Delete Record in Local DB also**//
                                if (status.equalsIgnoreCase("2")) {
                                    deleteOfflineData(object,position);
                                }
                                //**For Hybrid case Delete Record in Local DB also**//
                                jsonArray.remove(position);
                                rv_apps.setAdapter(appsAdvancedAdapter);
                                appsAdvancedAdapter.updateList(jsonArray);

                            } else {
                                Toast.makeText(context, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<DeviceIdResponse> call, Throwable t) {
                        improveHelper.dismissProgressDialog();
                        improveHelper.improveException(getActivity(), TAG, "deleteRow", (Exception) t);
                    }
                });
            }else{
                improveHelper.snackBarAlertActivities(context, rl_AppsListMain);
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "deleteRow", e);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        switch (requestCode) {


        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_add_row:

                addNewRow();

                break;
        }
    }

    private void addNewRow() {
try{
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("app_edit", "New");
//        intent.putExtra("s_app_design", strAppDesign);
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
    intent.putExtra("tableName",tableName);
    Gson gson = new Gson();
    intent.putExtra("subFormDetails",  gson.toJson(subFormDetails));

        startActivityForResult(intent, BACK_TO_LIST_FLAG);
} catch (Exception e) {
    improveHelper.improveException(getActivity(), TAG, "addNewRow", e);
}
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        getAppData(null,false);
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
                detailedPageData.setFormLevelTranslation(null);

                intent.putExtra("DetailedPageData",(Serializable) detailedPageData);

                startActivityForResult(intent, 0);
            }else{
                Toast.makeText(context, "Detailed Page disabled", Toast.LENGTH_SHORT).show();
            }} catch (Exception e) {
            improveHelper.improveException(context, TAG, "viewRow", e);
        }
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
      /*  if(s.length()>0){
            String searchText = s.toString();
            getAppData(searchText,true);
        }else{
            getAppData(null,false);
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
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "afterTextChanged", e);

        }
    }

    private String getSearchColumns(List<String> previewColumns) {
        StringBuilder value = new StringBuilder();
        try {
            for (int i = 0; i < previewColumns.size(); i++) {
                value.append(",").append(previewColumns.get(i));
            }
        } catch (Exception e) {
        }
        String valueStr= "";
        if(value.length()>1){
            valueStr = value.substring(1);
        }
        return valueStr;

    }

    AppDetailsAdvancedInput appDetailsAdvancedInput;

    public void getAppData(String searchText,boolean forSearch) {

        try {
            if (isNetworkStatusAvialable(context) && (appMode != null && appMode.equalsIgnoreCase("Online"))) {
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

                if(forSearch){
                    appDetailsAdvancedInput.setSearchKeyword(searchText);
                    String previewColumns = getSearchColumns(viewColumnsList);
                    appDetailsAdvancedInput.setSearchColumns(previewColumns);
                }
                getAppDataOnline(appDetailsAdvancedInput,forSearch);


            } else {

                getOfflineData();

                improveHelper.snackBarAlertActivities(context, rl_AppsListMain);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getAppData", e);
        }
    }

    private void getAppDataOnline(final AppDetailsAdvancedInput getAllAppNamesData,boolean forSearch) {
        try {
            if (dataManagementOptions.isLazyLoadingEnabled() && getAllAppNamesData.getLazyOrderKey().contentEquals("")) {
                getAllAppNamesData.setLazyOrderKey("SELECT NULL");
            }
            improveHelper.showProgressDialog(getString(R.string.please_wait));
            Call<ResponseBody> getAllAppNamesDataCall;
            if(forSearch){
                getAllAppNamesDataCall = getServices.iGetAppDataOnlineForSearch(sessionManager.getAuthorizationTokenId(),getAllAppNamesData);
            }else{
                getAllAppNamesDataCall = getServices.iGetAppDataOnline(sessionManager.getAuthorizationTokenId(),getAllAppNamesData);
            }
            getAllAppNamesDataCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    improveHelper.dismissProgressDialog();
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
                            } else if (dataManagementOptions.getRecordInsertionType().equalsIgnoreCase("Single")) {
                                viewRow(jsonArray.getJSONObject(0));
                            } else {
                                AppConstants.hasData = true;
                                ct_alNoRecords.setVisibility(View.GONE);
                                rv_apps.setVisibility(View.VISIBLE);
                                rv_apps.setAdapter(appsAdvancedAdapter);
                                if (dataManagementOptions.getSubFormInMainForm().equalsIgnoreCase("")) {
                                    appsAdvancedAdapter.updateList(jsonArray);
                                } else {
                                    getDistinctData(jsonArray);
                                }

                            }

                        } else {
                            AppConstants.hasData = false;
                            ct_alNoRecords.setVisibility(View.VISIBLE);
                            rv_apps.setVisibility(View.GONE);
                        }

                    } catch (Exception e) {
                        improveHelper.improveException(context, TAG, "getAppDataOnline", e);
                        ImproveHelper.showToast(context, e.toString());
                        ct_alNoRecords.setVisibility(View.VISIBLE);
                        rv_apps.setVisibility(View.GONE);
                    }

                    Log.d(TAG, "onResponse: " + json);
                }


                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    improveHelper.improveException(context, TAG, "getAppDataOnline", (Exception) t);
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getAppDataOnline", e);
        }
    }

    private void getDistinctData(JSONArray jsonArray) {

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
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getDistinctData", e);
        }

       /* }else{
            Toast.makeText(context, "Column not found", Toast.LENGTH_SHORT).show();
        }*/
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
    private boolean checkNextObject(JSONObject object) {
        boolean res = false;
        try {
            StringBuilder indexValues = new StringBuilder();

                List<String> indexColumns = new ArrayList<>();
//                indexColumns = dataManagementOptions.getIndexPageColumns();

                for (int j = 1; j < object.names().length(); j++) {
                    if (indexColumns.contains(object.names().getString(j))) {
                        indexValues.append(object.getString(object.names().getString(j)));
                    }
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

    public void deleteOfflineData(JSONObject object,int rowPosition){
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
//                                String tableName = object.getString("CreatedUserID") + "_" + replaceWithUnderscore(object.getString("FormName"));
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
//                                                String subFormTableName = tableName + "_" + replaceWithUnderscore(currentKey);
                                                String subFormTableName = getSubformTableName(currentKey);
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
                                        String errorMsg = offlineDataTransactionStatus.getSubFormName() + " deletion failed\n"+offlineDataTransaction.getErrorMessage();
                                        alertDialogError(errorMsg);
                                    } else {
                                        dismissProgressDialog();
                                        jsonArrayMain.remove(rowPosition);
                                        rv_apps.setAdapter(appsAdvancedAdapter);
                                        appsAdvancedAdapter.updateList(jsonArrayMain);
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
        }catch (Exception e){
            ImproveHelper.improveException(getActivity(), TAG, "deleteOfflineData", e);

        }
    }


    private String getFilters(List<FilterColumns> filterColumnsList) {
        StringBuilder filters = new StringBuilder();
        for (FilterColumns filterColumn : filterColumnsList) {
            filters.append(" ").append(filterColumn.getColumnName()).append(" ").append(filterColumn.getOperator()).append(" ").append("'").append(filterColumn.getValue()).append("'").append(" ").append(filterColumn.getCondition());
        }
        return filters.toString();
    }

}



