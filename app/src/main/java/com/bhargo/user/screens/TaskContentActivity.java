package com.bhargo.user.screens;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.DeployGroupAdapter;
import com.bhargo.user.adapters.TaskContentCustomAdapter;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.ContentInfoModel;
import com.bhargo.user.pojos.GetAppNamesForTaskModel;
import com.bhargo.user.pojos.SpinnerAppsDataModel;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.utils.AppConstants.editTaskAppDataModelsList;
import static com.bhargo.user.utils.AppConstants.editTaskContentDataModelsList;


public class TaskContentActivity extends BaseActivity {

    private static final String TAG = "TaskContentActivity";
    public static int EDIT_STATUS_APPS = 1;
    public static int EDIT_STATUS_CONTENT = 1;
    private final List<SpinnerAppsDataModel> appsInfoDataList = new ArrayList<>();
    private final List<ContentInfoModel> contentInfoModelList = new ArrayList<>();
    List<SpinnerAppsDataModel> appsInfoModelListSelected = new ArrayList<>();
    List<ContentInfoModel> contentInfoModelsSelected = new ArrayList<>();
    TaskContentCustomAdapter cadAdapter;
    CustomTextView sp_applications, sp_content;
    CustomButton btn_previousContent, btn_nextContent;
    Context context;
    RecyclerView rv_applications, rv_content;
    CustomTextView ct_noAppSelect, ct_noContentSelect;
    GetServices getServices;
    SessionManager sessionManager;
    ArrayList<String> arrayListSelectedItemsA = new ArrayList<>();
    ArrayList<String> arrayListSelectedItemsC = new ArrayList<>();
    Dialog dialog;
    ImproveHelper improveHelper;
    private String strTaskEdit, strTaskId = null, strTaskName, strTaskDesc, strTaskStartDate, strTaskEndDate,
            strTaskPriority, strTaskPriorityTitle, strTaskAttemptedBy, strTaskAppsInfo, strTaskContentInfo, strTaskGroupInfo, strTaskIndividualInfo;
    private JSONObject jsonAppsList;
    private JSONArray jsonArrayApps = new JSONArray();
    private JSONObject jsonContentList;
    private JSONArray jsonArrayContent = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_content);

        context = TaskContentActivity.this;
        sessionManager = new SessionManager(context);
        improveHelper = new ImproveHelper(context);
        getServices = RetrofitUtils.getUserService();

//        sp_applications = (MultiSelectSpinner) findViewById(R.id.sp_applications);
//        sp_applications = (MultiSelectionSpinner) findViewById(R.id.sp_applications);
        sp_applications = findViewById(R.id.sp_applications);
        sp_content = findViewById(R.id.sp_content);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            strTaskEdit = getIntent().getStringExtra("TaskEdit");
            strTaskId = getIntent().getStringExtra("TaskId");
            strTaskName = getIntent().getStringExtra("TaskName");
            strTaskDesc = getIntent().getStringExtra("TaskDesc");
            strTaskStartDate = getIntent().getStringExtra("TaskStartDate");
            strTaskEndDate = getIntent().getStringExtra("TaskEndDate");
            strTaskPriority = getIntent().getStringExtra("TaskPriority");
            strTaskPriorityTitle = getIntent().getStringExtra("TaskPriorityTitle");
            strTaskAttemptedBy = getIntent().getStringExtra("TaskAttemptedBy");
            strTaskAppsInfo = getIntent().getStringExtra("TaskAppsInfo");
            strTaskContentInfo = getIntent().getStringExtra("TaskContentInfo");
            strTaskGroupInfo = getIntent().getStringExtra("TaskGroupInfo");
            strTaskIndividualInfo = getIntent().getStringExtra("TaskIndividualInfo");

        }

        initViews(context);

    }


    private void initViews(Context context) {
        try {
            initializeActionBar();
            title.setText(getResources().getString(R.string.task_content));
            enableBackNavigation(true);
            iv_circle_appIcon.setVisibility(View.GONE);
            ib_settings.setVisibility(View.GONE);

            rv_applications = findViewById(R.id.rv_applications);
            rv_content = findViewById(R.id.rv_content);

            ct_noAppSelect = findViewById(R.id.ct_noAppSelect);
            ct_noContentSelect = findViewById(R.id.ct_noContentSelect);

            btn_previousContent = findViewById(R.id.btn_previousContent);
            btn_nextContent = findViewById(R.id.btn_nextContent);

            LinearLayoutManager linearLayoutManager_a = new LinearLayoutManager(getApplicationContext());
            rv_applications.setLayoutManager(linearLayoutManager_a); // set Layout
            LinearLayoutManager linearLayoutManager_c = new LinearLayoutManager(getApplicationContext());
            rv_content.setLayoutManager(linearLayoutManager_c); // set Layout


//        inflateDataInRecyclerView(new ArrayList<>(),new ArrayList<>());

//        inflateDataInRecyclerView(editTaskAppDataModelsList, new ArrayList<>());
//        inflateDataInRecyclerView(editTaskAppDataModelsList, editTaskContentDataModelsList);
            inflateDataInRecyclerView(new ArrayList<>(), new ArrayList<>());
            if (strTaskEdit != null) {
                inflateDataInRecyclerView(editTaskAppDataModelsList, editTaskContentDataModelsList);

                if (strTaskAppsInfo != null && !strTaskAppsInfo.equalsIgnoreCase("") && !strTaskAppsInfo.equalsIgnoreCase("[]")) {
                    EDIT_STATUS_APPS = 0;
                    Gson gson = new Gson();
                    if (editTaskAppDataModelsList != null) {
                        editTaskAppDataModelsList.clear();
                        editTaskAppDataModelsList.addAll(Arrays.asList(gson.fromJson(strTaskAppsInfo, SpinnerAppsDataModel[].class)));
                        inflateDataInRecyclerView(editTaskAppDataModelsList, editTaskContentDataModelsList);
                    }
                } else {
                    if (arrayListSelectedItemsA.size() > 0) {
                        rv_applications.setVisibility(View.VISIBLE);
                        ct_noAppSelect.setVisibility(View.GONE);
                    } else {
                        rv_applications.setVisibility(View.GONE);
                        ct_noAppSelect.setVisibility(View.VISIBLE);
                    }
                }
                if (strTaskContentInfo != null && !strTaskContentInfo.equalsIgnoreCase("") && !strTaskContentInfo.equalsIgnoreCase("[]")) {
                    EDIT_STATUS_CONTENT = 0;
                    Gson gson = new Gson();
                    if (editTaskContentDataModelsList != null) {
                        editTaskContentDataModelsList.clear();
                        editTaskContentDataModelsList.addAll(Arrays.asList(gson.fromJson(strTaskContentInfo, ContentInfoModel[].class)));
                        inflateDataInRecyclerView(editTaskAppDataModelsList, editTaskContentDataModelsList);
                    }
                } else {
                    if (arrayListSelectedItemsC.size() > 0) {
                        rv_content.setVisibility(View.VISIBLE);
                        ct_noContentSelect.setVisibility(View.GONE);
                    } else {
                        rv_content.setVisibility(View.GONE);
                        ct_noContentSelect.setVisibility(View.VISIBLE);
                    }
                }
            }

            sp_applications.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    List<SpinnerAppsDataModel> appsInfoModelListSelected = new ArrayList<>();
                    if (cadAdapter != null) {
                        appsInfoModelListSelected = cadAdapter.getAppsInfoModelList();
                    }
                    if (appsInfoModelListSelected != null && appsInfoModelListSelected.size() > 0) {

                        for (int i = 0; i < appsInfoDataList.size(); i++) {
                            for (int j = 0; j < appsInfoModelListSelected.size(); j++) {
                                if (appsInfoDataList.get(i).getACId().equalsIgnoreCase(appsInfoModelListSelected.get(j).getACId())) {
                                    appsInfoDataList.get(i).setSelected(true);
                                }
                            }
                        }

                        customDialogAlert(context, "Apps", appsInfoDataList, contentInfoModelsSelected);

                    } else {

                        loadApplications();

                    }
                }
            });

            sp_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<ContentInfoModel> contentInfoModelsSelected = new ArrayList<>();
                    if (cadAdapter != null) {
                        contentInfoModelsSelected = cadAdapter.getContentInfoModelList();
                    }
                    if (contentInfoModelsSelected != null && contentInfoModelsSelected.size() > 0) {

                        for (int i = 0; i < contentInfoModelList.size(); i++) {
                            for (int j = 0; j < contentInfoModelsSelected.size(); j++) {
                                if (contentInfoModelList.get(i).getACId().equalsIgnoreCase(contentInfoModelsSelected.get(j).getACId())) {
                                    contentInfoModelList.get(i).setSelected(true);
                                }
                            }
                        }

                        customDialogAlert(context, "Content", appsInfoModelListSelected, contentInfoModelList);

                    } else {
                        loadContent();
                    }
                }
            });


            btn_previousContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            btn_nextContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                if (isValidate()) {

                    Intent intent = new Intent(context, TaskDeploymentActivity.class);
                    intent.putExtra("TaskId", strTaskId);
                    intent.putExtra("TaskName", strTaskName);
                    intent.putExtra("TaskDesc", strTaskDesc);
                    intent.putExtra("TaskStartDate", strTaskStartDate);
                    intent.putExtra("TaskEndDate", strTaskEndDate);
                    intent.putExtra("TaskPriority", strTaskPriority);
                    intent.putExtra("TaskPriorityTitle", strTaskPriorityTitle);
                    intent.putExtra("TaskAttemptedBy", strTaskAttemptedBy);

                    if (jsonArrayApps != null) {
                        intent.putExtra("TaskAppsListJSONArray", jsonArrayApps.toString());
                    }
                    if (jsonArrayContent != null) {
                        intent.putExtra("TaskContentJSONArray", jsonArrayContent.toString());
                    }
                    if (strTaskEdit != null) {

                        intent.putExtra("EditTask", "EditTask");
                        intent.putExtra("TaskGroupInfo", strTaskGroupInfo);
                        intent.putExtra("TaskIndividualInfo", strTaskIndividualInfo);

                    }

                    startActivity(intent);
//                }
                }
            });

        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initViews", e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                taskContentBackAlertDialog();
                finish();
                return true;
            default:
                return false;
        }
    }

    private void taskContentBackAlertDialog() {
//set icon
//set title
//                .setIcon(getApplicationContext().getResources().getDrawable(R.drawable.icon_bhargo_user))
//                .setTitle("Task Content")
//                .setMessage("Are you sure to Exit ?")
        new android.app.AlertDialog.Builder(context)
                .setIcon(getApplicationContext().getResources().getDrawable(R.drawable.icon_bhargo_user_rounded))
                .setTitle("Task Content")
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked

                        finish();
                    }
                })
//set negative button
                .setNegativeButton(R.string.d_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {

//        taskContentBackAlertDialog();
        finish();
    }


    public void loadApplications() {
        try {
            improveHelper.showProgressDialog(context.getString(R.string.please_wait));
            GetAppNamesForTaskModel dataModel = new GetAppNamesForTaskModel();
            dataModel.setOrgId(sessionManager.getOrgIdFromSession());
            dataModel.setUserId(sessionManager.getUserDataFromSession().getUserID());

            Call<ResponseBody> call = getServices.getAppNamesForTask(dataModel);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    String json = null;
                    appsInfoDataList.clear();
                    try {
                        json = response.body().string();
                        improveHelper.dismissProgressDialog();
                        Log.d("TaskAppsList", "onResponse: " + json);
                        JSONObject responseObj = new JSONObject(json.trim());

                        JSONArray jsonArray_Group = responseObj.getJSONArray("AppNames");

                        for (int i = 0; i < jsonArray_Group.length(); i++) {
                            JSONObject jsonObj = jsonArray_Group.getJSONObject(i);

                            SpinnerAppsDataModel spinnerAppsDataModel = new SpinnerAppsDataModel();
                            spinnerAppsDataModel.setACId(jsonObj.getString("AppID"));
                            spinnerAppsDataModel.setACName(jsonObj.getString("AppName"));
                            appsInfoDataList.add(spinnerAppsDataModel);

                        }

                        if (strTaskEdit != null) {
                            if (arrayListSelectedItemsA != null && arrayListSelectedItemsA.size() == 0 && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("0")) {
                                editTaskAppDataModelsList.clear();
                            } else {

                                if (appsInfoDataList != null && appsInfoDataList.size() > 0) {
                                    for (int i = 0; i < appsInfoDataList.size(); i++) {
                                        if (editTaskAppDataModelsList != null && editTaskAppDataModelsList.size() > 0) {
                                            for (int j = 0; j < editTaskAppDataModelsList.size(); j++) {
                                                if (appsInfoDataList.get(i).getACId().equalsIgnoreCase(editTaskAppDataModelsList.get(j).getACId())) {
                                                    appsInfoDataList.get(i).setSelected(true);
                                                    Log.d(TAG, "AppsListItemMatched " + appsInfoDataList.get(i).getACName());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (appsInfoDataList != null && appsInfoDataList.size() > 0) {
                            customDialogAlert(context, "Apps", appsInfoDataList, contentInfoModelList);
                        } else {
                            ImproveHelper.showToast(context, "No application record available!");
                        }
                    } catch (Exception e) {
                        improveHelper.dismissProgressDialog();
                        Log.d("TaskAppsListException", e.toString());
                    }
                    improveHelper.dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "TaskAppsListOnFailure: " + t.toString());
                    if (t.toString().contains("Failed to connect to")) {
                        ImproveHelper.showToast(context, "Please check internet connection");
                    }
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initViews", e);
        }

    }


    public void loadContent() {
        try {
            improveHelper.showProgressDialog(context.getString(R.string.please_wait));
            GetAppNamesForTaskModel dataModel = new GetAppNamesForTaskModel();
            dataModel.setOrgId(sessionManager.getOrgIdFromSession());
            dataModel.setUserId(sessionManager.getUserDataFromSession().getUserID());
            if (sessionManager.getUserDetailsFromSession().getRole().equalsIgnoreCase("Admin")) {
                dataModel.setCase("3");
            } else if (sessionManager.getUserDetailsFromSession().getRole().equalsIgnoreCase("Resource Manger")) {
                dataModel.setCase("2");
            } else {
                dataModel.setCase("1");
            }
            Call<ResponseBody> call = getServices.getContentForTask(dataModel);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    String json = null;
                    contentInfoModelList.clear();
                    try {
                        json = response.body().string();
                        improveHelper.dismissProgressDialog();
                        JSONObject responseObj = new JSONObject(json.trim());
                        String strStatus = responseObj.getString("Status");
                        if (strStatus.equalsIgnoreCase("200")) {
                            JSONArray jsonArray_Group = responseObj.getJSONArray("ContentData");

                            for (int i = 0; i < jsonArray_Group.length(); i++) {

                                JSONObject jsonObj = jsonArray_Group.getJSONObject(i);
                                ContentInfoModel spinnerData = new ContentInfoModel();
                                spinnerData.setACName(jsonObj.getString("SelectedFileName"));
                                spinnerData.setACId(jsonObj.getString("CategoryFileID"));
                                contentInfoModelList.add(spinnerData);

                            }
                        }
                        if (strTaskEdit != null) {

                            if (arrayListSelectedItemsC != null && arrayListSelectedItemsC.size() == 0 && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("0")) {
                                editTaskContentDataModelsList.clear();
                            } else {

                                if (contentInfoModelList != null && contentInfoModelList.size() > 0) {
                                    for (int i = 0; i < contentInfoModelList.size(); i++) {
                                        if (editTaskContentDataModelsList != null && editTaskContentDataModelsList.size() > 0) {
                                            for (int j = 0; j < editTaskContentDataModelsList.size(); j++) {
                                                if (contentInfoModelList.get(i).getACId().equalsIgnoreCase(editTaskContentDataModelsList.get(j).getACId())) {
                                                    contentInfoModelList.get(i).setSelected(true);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (contentInfoModelList != null && contentInfoModelList.size() > 0) {
                            customDialogAlert(context, "Content", appsInfoDataList, contentInfoModelList);
                        } else {
                            ImproveHelper.showToast(context, "No Content record Available!");
                        }
                    } catch (Exception e) {
                        improveHelper.dismissProgressDialog();
                        Log.d("TaskContentException", e.toString());
                    }
                    improveHelper.dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "TaskContentOnFailure: " + t.toString());
                    if (t.toString().contains("Failed to connect to")) {
                        ImproveHelper.showToast(context, "Please check internet connection");
                    }
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "loadContent", e);
        }

    }


    private boolean isValidate() {
        boolean flag = true;
        try {
            if ((arrayListSelectedItemsA != null && arrayListSelectedItemsA.size() == 0)
                    && (arrayListSelectedItemsC != null && arrayListSelectedItemsC.size() == 0)) {
                ImproveHelper.showToast(this, "Please Select Application Or Content");
                flag = false;
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "isValidate", e);
        }

        return flag;
    }


    private void customDialogAlert(Context context, String msg, List<SpinnerAppsDataModel> appsInfoModelList,
                                   List<ContentInfoModel> contentInfoModelList) {
        try {
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false); // alert out side click  dismiss disable
            dialog.setContentView(R.layout.task_cad_recyclerview_layout);

            CustomEditText etv_cadTitle = dialog.findViewById(R.id.etv_cadTitle);
            CustomTextView ctv_Ok = dialog.findViewById(R.id.ctv_Ok);
            CustomTextView ctv_cancel = dialog.findViewById(R.id.ctv_cancel);
            RecyclerView rv_cad = dialog.findViewById(R.id.rv_cad);

            LinearLayoutManager llm = new LinearLayoutManager(context);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rv_cad.setLayoutManager(llm);

            if (msg.equalsIgnoreCase("Apps")) {

                etv_cadTitle.setHint("Select Applications");

                cadAdapter = new TaskContentCustomAdapter(context, appsInfoModelList, contentInfoModelList, true);

            } else if (msg.equalsIgnoreCase("Content")) {

                etv_cadTitle.setHint("Select Content");
                cadAdapter = new TaskContentCustomAdapter(context, appsInfoModelList, contentInfoModelList, false);

            }

            etv_cadTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString() != null && !s.toString().equalsIgnoreCase("") && !s.toString().isEmpty()) {
                        filter(s.toString(), msg);
                    } else {
                        if (msg.equalsIgnoreCase("Apps")) {
                            cadAdapter.filterListApps(appsInfoModelList);
                        } else if (msg.equalsIgnoreCase("Content")) {
                            cadAdapter.filterListContent(contentInfoModelList);
                        }
                    }
                }
            });

            rv_cad.setAdapter(cadAdapter);
            cadAdapter.notifyDataSetChanged();

            ctv_Ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (msg.equalsIgnoreCase("Apps")) {

                        appsInfoModelListSelected.clear();
                        appsInfoModelListSelected.addAll(cadAdapter.getAppsInfoModelList());
                        if (appsInfoModelListSelected.size() == 0) {
                            arrayListSelectedItemsA.clear();
                        }
                        inflateDataInRecyclerView(appsInfoModelListSelected, contentInfoModelsSelected);

                    } else if (msg.equalsIgnoreCase("Content")) {
//                } else {

                        contentInfoModelsSelected.clear();
                        contentInfoModelsSelected.addAll(cadAdapter.getContentInfoModelList());
                        if (contentInfoModelsSelected.size() == 0) {
                            arrayListSelectedItemsC.clear();
                        }
                        inflateDataInRecyclerView(appsInfoModelListSelected, contentInfoModelsSelected);
                    }
                    dialog.dismiss();

                }
            });
            ctv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "customDialogAlert", e);
        }

    }

    public void inflateDataInRecyclerView(List<SpinnerAppsDataModel> editTaskAppDataModelsList, List<ContentInfoModel> editTaskContentDataModelsList) {
        try {
            if (editTaskAppDataModelsList != null && editTaskAppDataModelsList.size() > 0) {
                rv_applications.setAdapter(null);
                arrayListSelectedItemsA.clear();
                jsonArrayApps = new JSONArray(new ArrayList<String>());
                if (EDIT_STATUS_APPS == 0) {
                    for (int i = 0; i < editTaskAppDataModelsList.size(); i++) {

                        jsonAppsList = new JSONObject();
                        try {

                            jsonAppsList.put("AppId", editTaskAppDataModelsList.get(i).getACId());
                            jsonAppsList.put("TaskAppID", strTaskId);
                            Log.d(TAG, "inflateDataInRecyclerView: " + editTaskAppDataModelsList.get(i).getACName());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonArrayApps.put(jsonAppsList);
                        arrayListSelectedItemsA.add(editTaskAppDataModelsList.get(i).getACName());
                    }
//                EDIT_STATUS_APPS = 1;
                } else if (EDIT_STATUS_APPS == 1) {

                    for (int i = 0; i < editTaskAppDataModelsList.size(); i++) {
                        if (editTaskAppDataModelsList.get(i).isSelected()) {
                            jsonAppsList = new JSONObject();
                            try {

                                jsonAppsList.put("AppId", editTaskAppDataModelsList.get(i).getACId());
                                jsonAppsList.put("TaskAppID", strTaskId);
                                Log.d(TAG, "inflateDataInRecyclerView: " + editTaskAppDataModelsList.get(i).getACName());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArrayApps.put(jsonAppsList);

                            arrayListSelectedItemsA.add(editTaskAppDataModelsList.get(i).getACName());
                        }
                    }
                }


                DeployGroupAdapter adapter = new DeployGroupAdapter(context, ImproveHelper.removeDuplicates(arrayListSelectedItemsA), "TaskApps");
                rv_applications.setAdapter(adapter);

                if (arrayListSelectedItemsA.size() > 0) {
                    rv_applications.setVisibility(View.VISIBLE);
                    ct_noAppSelect.setVisibility(View.GONE);
                } else {
                    jsonArrayApps = new JSONArray(new ArrayList<String>());
                    rv_applications.setVisibility(View.GONE);
                    ct_noAppSelect.setVisibility(View.VISIBLE);
                }

            } else {

                if (arrayListSelectedItemsA.size() > 0) {
                    rv_applications.setVisibility(View.VISIBLE);
                    ct_noAppSelect.setVisibility(View.GONE);
                } else {
                    jsonArrayApps = new JSONArray(new ArrayList<String>());
                    rv_applications.setVisibility(View.GONE);
                    ct_noAppSelect.setVisibility(View.VISIBLE);
                }
            }

            if (editTaskContentDataModelsList != null && editTaskContentDataModelsList.size() > 0) {
                rv_content.setAdapter(null);
                arrayListSelectedItemsC.clear();
                jsonArrayContent = new JSONArray(new ArrayList<String>());
                if (EDIT_STATUS_CONTENT == 0) {

                    for (int i = 0; i < editTaskContentDataModelsList.size(); i++) {

                        jsonContentList = new JSONObject();
                        try {
                            jsonContentList.put("ContentId", editTaskContentDataModelsList.get(i).getACId());
                            jsonContentList.put("TaskContentID", strTaskId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        jsonArrayContent.put(jsonContentList);

                        arrayListSelectedItemsC.add(editTaskContentDataModelsList.get(i).getACName());

                    }

//                EDIT_STATUS_CONTENT = 1;
                } else if (EDIT_STATUS_CONTENT == 1) {
                    for (int i = 0; i < editTaskContentDataModelsList.size(); i++) {
                        if (editTaskContentDataModelsList.get(i).isSelected()) {
                            jsonContentList = new JSONObject();
                            try {
                                jsonContentList.put("ContentId", editTaskContentDataModelsList.get(i).getACId());
                                jsonContentList.put("TaskContentID", strTaskId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            jsonArrayContent.put(jsonContentList);

                            arrayListSelectedItemsC.add(editTaskContentDataModelsList.get(i).getACName());

                        }
                    }

                }


                DeployGroupAdapter adapter = new DeployGroupAdapter(context, ImproveHelper.removeDuplicates(arrayListSelectedItemsC), "TaskContent");
                rv_content.setAdapter(adapter);

                if (arrayListSelectedItemsC.size() > 0) {
                    rv_content.setVisibility(View.VISIBLE);
                    ct_noContentSelect.setVisibility(View.GONE);
                } else {
                    jsonArrayContent = new JSONArray(new ArrayList<String>());
                    rv_content.setVisibility(View.GONE);
                    ct_noContentSelect.setVisibility(View.VISIBLE);
                }
            } else {

                if (arrayListSelectedItemsC.size() > 0) {
                    rv_content.setVisibility(View.VISIBLE);
                    ct_noContentSelect.setVisibility(View.GONE);
                } else {
                    jsonArrayContent = new JSONArray(new ArrayList<String>());
                    rv_content.setVisibility(View.GONE);
                    ct_noContentSelect.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "inflateDataInRecyclerView", e);
        }

    }

    private void filter(String text, String strFromApps) {
        //new array list that will hold the filtered data
        try {
            if (strFromApps.equalsIgnoreCase("Apps")) {
                List<SpinnerAppsDataModel> filteredNames = new ArrayList<>();

                for (SpinnerAppsDataModel appsDataModel : appsInfoDataList) {
                    if (appsDataModel.getACName().toLowerCase().contains(text.toLowerCase())) {
                        //adding the element to filtered list
                        filteredNames.add(appsDataModel);
                    }
                }

                cadAdapter.filterListApps(filteredNames);
            } else {
                List<ContentInfoModel> filteredNames = new ArrayList<>();

                //looping through existing elements
                for (ContentInfoModel contentInfoModel : contentInfoModelList) {
                    //if the existing elements contains the search input
                    if (contentInfoModel.getACName().toLowerCase().contains(text.toLowerCase())) {
                        //adding the element to filtered list
                        filteredNames.add(contentInfoModel);
                    }
                }

                //calling a method of the adapter class and passing the filtered list
                cadAdapter.filterListContent(filteredNames);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "filter", e);
        }

    }

}

