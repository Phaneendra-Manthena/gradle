package com.bhargo.user.screens;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.bhargo.user.adapters.DeployIndividualAdapter;
import com.bhargo.user.adapters.TaskDeploymentAdapter;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.ContentInfoModel;
import com.bhargo.user.pojos.CreateTaskResponse;
import com.bhargo.user.pojos.GetAppNamesForTaskModel;
import com.bhargo.user.pojos.OutTaskDataModel;
import com.bhargo.user.pojos.SpinnerAppsDataModel;
import com.bhargo.user.pojos.TaskCommentDetails;
import com.bhargo.user.pojos.TaskDepEmpDataModel;
import com.bhargo.user.pojos.TaskDepGroupDataModel;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.utils.AppConstants.editTaskAppDataModelsList;
import static com.bhargo.user.utils.AppConstants.editTaskContentDataModelsList;
import static com.bhargo.user.utils.AppConstants.editTaskGroupList;
import static com.bhargo.user.utils.AppConstants.editTaskIndividualList;


public class TaskDeploymentActivity extends BaseActivity {

    private static final String TAG = "TaskDeploymentActivity";
    public static int EDIT_STATUS_GROUP = 1;
    public static int EDIT_STATUS_EMP = 1;
    private final List<TaskDepGroupDataModel> taskGroupList = new ArrayList<>();
    private final List<TaskDepEmpDataModel> taskIndividualList = new ArrayList<>();
    private final List<TaskDepGroupDataModel> taskGroupListSelected = new ArrayList<>();
    private final List<TaskDepEmpDataModel> taskIndividualListSelected = new ArrayList<>();
    boolean isMultiClickable = true;
    Context context;
    CustomButton btn_previousD, btn_createTask;
    CustomTextView sp_group, sp_individual;
    RecyclerView rv_groups, rv_individuals;
    CustomTextView ct_noGroupSelect, ct_noIndividualSelect;
    GetServices getServices;
    ArrayList<String> arrayListSelectedItemsG = new ArrayList<>();
    ArrayList<String> arrayListSelectedItemsI = new ArrayList<>();
    SessionManager sessionManager;
    String strTaskGroupInfo, strTaskIndividualInfo;
    List<TaskCommentDetails> taskCommentDetailsListChanged = new ArrayList<>();
    private ImproveHelper improveHelper;

    private String strEditTask = null, strTaskId = null, strTaskName, strTaskDesc,
            strTaskStartDateReverse, strTaskEndDateReverse, strTaskStartDate,
            strTaskEndDate, strTaskPriority, strTaskPriorityTitle, strTaskAttemptedBy, strTaskAppsListJSONArray, strTaskContentJSONArray;
    private JSONObject jsonGroupList;
    private JSONArray jsonArrayGroup = new JSONArray();
    private JSONObject jsonIndividualList;
    private JSONArray jsonArrayIndividual = new JSONArray();
    private Dialog dialog;
    private TaskDeploymentAdapter taskDeploymentAdapter;
    private ImproveDataBase improveDataBase;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_deployment);

        context = TaskDeploymentActivity.this;

        sessionManager = new SessionManager(context);
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);
        getServices = RetrofitUtils.getUserService();

        sp_group = findViewById(R.id.sp_group);
        sp_individual = findViewById(R.id.sp_individual);
        rv_groups = findViewById(R.id.rv_groups);
        rv_individuals = findViewById(R.id.rv_individuals);
        ct_noGroupSelect = findViewById(R.id.ct_noGroupSelect);
        ct_noIndividualSelect = findViewById(R.id.ct_noIndividualSelect);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            strTaskId = getIntent().getStringExtra("TaskId");
            strTaskName = getIntent().getStringExtra("TaskName");
            strTaskDesc = getIntent().getStringExtra("TaskDesc");
            strTaskStartDateReverse = getIntent().getStringExtra("TaskStartDate");
            strTaskEndDateReverse = getIntent().getStringExtra("TaskEndDate");
            strTaskPriority = getIntent().getStringExtra("TaskPriority");
            strTaskPriorityTitle = getIntent().getStringExtra("TaskPriorityTitle");
            strTaskAttemptedBy = getIntent().getStringExtra("TaskAttemptedBy");
            strTaskAppsListJSONArray = getIntent().getStringExtra("TaskAppsListJSONArray");
            strTaskContentJSONArray = getIntent().getStringExtra("TaskContentJSONArray");
            strEditTask = getIntent().getStringExtra("EditTask");
            strTaskGroupInfo = getIntent().getStringExtra("TaskGroupInfo");
            strTaskIndividualInfo = getIntent().getStringExtra("TaskIndividualInfo");

            String[] strSplitStartDate = strTaskStartDateReverse.split("-");
            strTaskStartDate = strSplitStartDate[2] + "-" + strSplitStartDate[1] + "-" + strSplitStartDate[0];

            String[] strSplitEndDate = strTaskEndDateReverse.split("-");
            strTaskEndDate = strSplitEndDate[2] + "-" + strSplitEndDate[1] + "-" + strSplitEndDate[0];

        }

//        inflateDataInRecyclerView(editTaskGroupList, null);
//        inflateDataInRecyclerView(null, editTaskIndividualList);
        inflateDataInRecyclerView(new ArrayList<>(), new ArrayList<>());

        if (strEditTask != null) {

            if (strTaskGroupInfo != null && !strTaskGroupInfo.equalsIgnoreCase("[]") && !strTaskGroupInfo.isEmpty()) {
                EDIT_STATUS_GROUP = 0;
                Gson gson = new Gson();
                if (editTaskGroupList != null) {
                    editTaskGroupList.clear();
                    editTaskGroupList.addAll(Arrays.asList(gson.fromJson(strTaskGroupInfo, TaskDepGroupDataModel[].class)));
                    inflateDataInRecyclerView(editTaskGroupList, editTaskIndividualList);
                }

            } else {
                if (arrayListSelectedItemsG.size() > 0) {
                    rv_groups.setVisibility(View.VISIBLE);
                    ct_noGroupSelect.setVisibility(View.GONE);
                } else {

                    rv_individuals.setVisibility(View.GONE);
                    ct_noIndividualSelect.setVisibility(View.VISIBLE);
                }
            }
            if (strTaskIndividualInfo != null && !strTaskIndividualInfo.equalsIgnoreCase("[]")
                    && !strTaskIndividualInfo.isEmpty()) {
                EDIT_STATUS_EMP = 0;
                Gson gson = new Gson();
                if (editTaskIndividualList != null) {
                    editTaskIndividualList.clear();
                    editTaskIndividualList.addAll(Arrays.asList(gson.fromJson(strTaskIndividualInfo, TaskDepEmpDataModel[].class)));
                    inflateDataInRecyclerView(editTaskGroupList, editTaskIndividualList);
                }
            } else {
                if (arrayListSelectedItemsI.size() > 0) {
                    rv_individuals.setVisibility(View.VISIBLE);
                    ct_noIndividualSelect.setVisibility(View.GONE);
                } else {

                    rv_individuals.setVisibility(View.GONE);
                    ct_noIndividualSelect.setVisibility(View.VISIBLE);
                }
            }

        }

        initViews();

    }

    private void initViews() {
        try {
            initializeActionBar();
            title.setText(getResources().getString(R.string.to_whom));
            enableBackNavigation(true);
            iv_circle_appIcon.setVisibility(View.GONE);
            ib_settings.setVisibility(View.GONE);

            btn_previousD = findViewById(R.id.btn_previousD);
            btn_createTask = findViewById(R.id.btn_createTask);
            if (strEditTask != null) {
                btn_createTask.setText("Update");
            }
            LinearLayoutManager linearLayoutManager_g = new LinearLayoutManager(getApplicationContext());
            rv_groups.setLayoutManager(linearLayoutManager_g);
            LinearLayoutManager linearLayoutManager_i = new LinearLayoutManager(getApplicationContext());
            rv_individuals.setLayoutManager(linearLayoutManager_i);


            sp_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    List<TaskDepGroupDataModel> taskDepGroupDataSelected = new ArrayList<>();
                    if (taskDeploymentAdapter != null) {
                        taskDepGroupDataSelected = taskDeploymentAdapter.getGroupListFromAdapter();
                    }

                    if (taskDepGroupDataSelected != null && taskDepGroupDataSelected.size() > 0) {

                        for (int i = 0; i < taskGroupList.size(); i++) {
                            for (int j = 0; j < taskDepGroupDataSelected.size(); j++) {
                                if (taskGroupList.get(i).getGroupId().equalsIgnoreCase(taskDepGroupDataSelected.get(j).getGroupId())) {
                                    taskGroupList.get(i).setSelected(true);
                                }
                            }
                        }

                        customDialogAlert(context, "Group", taskGroupList, taskIndividualListSelected);

                    } else {
                        loadGroups();
                    }

                }
            });
            sp_individual.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<TaskDepEmpDataModel> taskDepEmpDataSelected = new ArrayList<>();
                    if (taskDeploymentAdapter != null) {
                        taskDepEmpDataSelected = taskDeploymentAdapter.getEmpListFromAdapter();
                    }

                    if (taskDepEmpDataSelected != null && taskDepEmpDataSelected.size() > 0) {

                        for (int i = 0; i < taskIndividualList.size(); i++) {
                            for (int j = 0; j < taskDepEmpDataSelected.size(); j++) {
                                if (taskIndividualList.get(i).getEmp_Id().equalsIgnoreCase(taskDepEmpDataSelected.get(j).getEmp_Id())
                                        && taskIndividualList.get(i).getPostId().equalsIgnoreCase(taskDepEmpDataSelected.get(j).getPostId())) {
                                    taskIndividualList.get(i).setSelected(true);
                                }
                            }
                        }

                        customDialogAlert(context, "Individual", taskGroupListSelected, taskIndividualList);

                    } else {

                        loadIndividuals();
                    }

                }
            });

            btn_previousD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            btn_createTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isMultiClickable) {
                        return;
                    }
                    if (isValidate()) {

                        prepareJson();

                    }

                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initViews", e);
        }

    }

    private void prepareJson() {

        if (sessionManager == null) {
            sessionManager = new SessionManager(context);
        }
        Log.d(TAG, "OutTaskDataS: " + new Gson().toJson(jsonArrayIndividual));
        ImproveHelper improveHelper = new ImproveHelper(context);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        String strDate = sdfDate.format(new Date());
        try {
            JSONArray jsonArrayAppsList = new JSONArray(strTaskAppsListJSONArray);
//            Log.d(TAG, "TaskPrepareJson: "+jsonArrayAppsList);
            JSONArray jsonArrayContentList = new JSONArray(strTaskContentJSONArray);
            //            Log.d(TAG, "TaskPrepareJson: "+jsonArrayContentList);
            JSONObject jsonObjectFinal = new JSONObject();
            JSONArray TaskDetails = new JSONArray();
            JSONObject jsonObjectMain = new JSONObject();
            jsonObjectMain.put("App", jsonArrayAppsList);
            jsonObjectMain.put("Content", jsonArrayContentList);
            jsonObjectMain.put("GroupDts", jsonArrayGroup);
            jsonObjectMain.put("EmpDts", jsonArrayIndividual);
            jsonObjectMain.put("UserId", sessionManager.getUserDataFromSession().getUserID());
            jsonObjectMain.put("OrgId", sessionManager.getOrgIdFromSession());
            jsonObjectMain.put("TaskName", strTaskName);
            jsonObjectMain.put("StartDate", strTaskStartDate);
            jsonObjectMain.put("EndDate", strTaskEndDate);
            jsonObjectMain.put("Priority", strTaskPriority);
            jsonObjectMain.put("TaskDesc", strTaskDesc);
            jsonObjectMain.put("MobileDate", ImproveHelper.currentTime());
            jsonObjectMain.put("IsSingleUser", strTaskAttemptedBy);
            jsonObjectMain.put("DeviceId", ImproveHelper.getMyDeviceId(context));
            jsonObjectMain.put("VersionNo", improveHelper.getAppVersionFromGradle());
            if (sessionManager.getPostsFromSession() != null && !sessionManager.getPostsFromSession().isEmpty()) {

                jsonObjectMain.put("PostId", sessionManager.getPostsFromSession());
            } else {
                ImproveHelper.showToast(context, "Post id required");
            }

            if (strEditTask != null) {

                jsonObjectMain.put("TaskId", strTaskId);

                OutTaskDataModel outTaskDataModel = improveDataBase.getDataFromOutTaskTableObject(strTaskId, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID()
                        , sessionManager.getPostsFromSession());
                if (outTaskDataModel != null) {
                    if (outTaskDataModel.getTaskName() != null && !outTaskDataModel.getTaskName().equalsIgnoreCase(strTaskName)) {
                        updatedCommentsPrepareObject(context, improveHelper, "Task Name ", outTaskDataModel.getTaskName(), strTaskName);
                    }
                    if (outTaskDataModel.getStartDate() != null && !outTaskDataModel.getStartDate().equalsIgnoreCase(strTaskStartDateReverse)) {
                        updatedCommentsPrepareObject(context, improveHelper, "Start Date ", outTaskDataModel.getStartDate(), strTaskStartDateReverse);
                    }

                    if (outTaskDataModel.getEndDate() != null && !outTaskDataModel.getEndDate().equalsIgnoreCase(strTaskEndDateReverse)) {
                        updatedCommentsPrepareObject(context, improveHelper, "End Date ", outTaskDataModel.getEndDate(), strTaskEndDateReverse);
                    }

                    if (outTaskDataModel.getPriorityId() != null && !outTaskDataModel.getPriorityId().equalsIgnoreCase(strTaskPriority)) {
                        updatedCommentsPrepareObject(context, improveHelper, "Priority ", outTaskDataModel.getPriority(), strTaskPriorityTitle);
                    }
                    if (outTaskDataModel.getIsSingleUser() != null && !outTaskDataModel.getIsSingleUser().equalsIgnoreCase(strTaskAttemptedBy)) {

                        String strTaskAttemptedByChanged = null;
                        String strIsSingleUserChanged = null;
                        if (outTaskDataModel.getIsSingleUser() != null && outTaskDataModel.getIsSingleUser().equalsIgnoreCase("0")) {
                            strIsSingleUserChanged = "All Users";
                        } else if (outTaskDataModel.getIsSingleUser() != null && outTaskDataModel.getIsSingleUser().equalsIgnoreCase("1")) {
                            strIsSingleUserChanged = "Any one user";
                        }
                        if (strTaskAttemptedBy.equalsIgnoreCase("0")) {
                            strTaskAttemptedByChanged = "All Users";
                        } else if (strTaskAttemptedBy.equalsIgnoreCase("1")) {
                            strTaskAttemptedByChanged = "Any one user";
                        }

                        updatedCommentsPrepareObject(context, improveHelper, "Task attempted by",
                                strIsSingleUserChanged, strTaskAttemptedByChanged);

                    }

                    if (outTaskDataModel.getTaskDesc() != null && !outTaskDataModel.getTaskDesc().equalsIgnoreCase(strTaskDesc)) {
                        updatedCommentsPrepareObject(context, improveHelper, "Task Description ", outTaskDataModel.getTaskDesc(), strTaskDesc);
                    }


                    // Apps Start
                    List<String> appsListAfterChanged = new ArrayList<>();
                    JSONArray jsonArrayPrepared = new JSONArray(strTaskAppsListJSONArray);

                    if (jsonArrayPrepared != null && jsonArrayPrepared.length() > 0) {

                        for (int i = 0; i < jsonArrayPrepared.length(); i++) {

                            JSONObject jsonObject = jsonArrayPrepared.getJSONObject(i);
                            String strAppID = jsonObject.getString("AppId");

                            appsListAfterChanged.add(strAppID);

                        }
                    }

                    Gson gSonApps = new Gson();
                    List<SpinnerAppsDataModel> appsListBeforeUpdate = Arrays.asList(gSonApps.fromJson(outTaskDataModel.getAppInfo(), SpinnerAppsDataModel[].class));

                    if (appsListBeforeUpdate != null && appsListAfterChanged != null
                            && appsListBeforeUpdate.size() > 0 && appsListAfterChanged.size() > 0) {

                        if ((appsListBeforeUpdate.size() > appsListAfterChanged.size())
                                || (appsListAfterChanged.size() > appsListBeforeUpdate.size())) {
                            updatedCommentsPrepareObject(context, improveHelper, "Some applications ", "are ", "");
                            Log.d(TAG, "prepareJsonAppsGraterLess: " + "prepareJsonAppsGraterLess");
                        } else {

                            List<String> stringListChangedApps = new ArrayList<>();
                            stringListChangedApps.clear();
                            for (int i = 0; i < appsListBeforeUpdate.size(); i++) {
                                stringListChangedApps.add(appsListBeforeUpdate.get(i).getACId());
                            }
                            if (!(stringListChangedApps.containsAll(appsListAfterChanged))
                                    || !(appsListAfterChanged.containsAll(stringListChangedApps))) {
                                updatedCommentsPrepareObject(context, improveHelper, "Some applications ", "are ", "");
                            }

                        }
                    }
                    // Apps End

                    // Content Start
                    List<String> contentListAfterChanged = new ArrayList<>();
                    JSONArray jsonArrayPreparedContent = new JSONArray(strTaskContentJSONArray);

                    if (jsonArrayPreparedContent != null && jsonArrayPreparedContent.length() > 0) {

                        for (int i = 0; i < jsonArrayPreparedContent.length(); i++) {

                            JSONObject jsonObject = jsonArrayPreparedContent.getJSONObject(i);
                            String strContentId = jsonObject.getString("ContentId");

                            contentListAfterChanged.add(strContentId);

                        }
                    }
                    Gson gSonContent = new Gson();
                    List<ContentInfoModel> contentListBeforeUpdate = Arrays.asList(gSonContent.fromJson(outTaskDataModel.getFilesInfo(), ContentInfoModel[].class));

                    if (contentListBeforeUpdate != null && contentListAfterChanged != null && contentListBeforeUpdate.size() > 0 && contentListAfterChanged.size() > 0) {

                        if ((contentListBeforeUpdate.size() > contentListAfterChanged.size())
                                || (contentListAfterChanged.size() > contentListBeforeUpdate.size())) {
                            updatedCommentsPrepareObject(context, improveHelper, "Some ", "Content (or) Files are ", "");
                        } else {
                            List<String> stringListChangedContent = new ArrayList<>();
                            stringListChangedContent.clear();
                            for (int i = 0; i < contentListBeforeUpdate.size(); i++) {
                                stringListChangedContent.add(contentListBeforeUpdate.get(i).getACId());
                            }
                            if (!(stringListChangedContent.containsAll(contentListAfterChanged))
                                    || !(contentListAfterChanged.containsAll(stringListChangedContent))) {
                                updatedCommentsPrepareObject(context, improveHelper, "Some applications ", "are ", "");
                            }

                        }
                    }
                    // Content End

                    // Group Start
                    List<String> groupListAfterChanged = new ArrayList<>();
                    JSONArray jsonArrayPreparedGroup = new JSONArray(jsonArrayGroup.toString());

                    if (jsonArrayPreparedGroup != null && jsonArrayPreparedGroup.length() > 0) {

                        for (int i = 0; i < jsonArrayPreparedGroup.length(); i++) {

                            JSONObject jsonObject = jsonArrayPreparedGroup.getJSONObject(i);
                            String strGroupId = jsonObject.getString("GroupId");

                            groupListAfterChanged.add(strGroupId);
                        }
                    }
                    Gson gSonGroup = new Gson();
                    List<TaskDepGroupDataModel> groupListBeforeUpdate = Arrays.asList(gSonGroup.fromJson(outTaskDataModel.getGroupInfo(), TaskDepGroupDataModel[].class));

                    if (groupListBeforeUpdate != null && groupListAfterChanged != null && groupListAfterChanged.size() > 0 && groupListAfterChanged.size() > 0) {


                        if ((groupListBeforeUpdate.size() > groupListAfterChanged.size())
                                || (groupListAfterChanged.size() > groupListBeforeUpdate.size())) {
                            updatedCommentsPrepareObject(context, improveHelper, "Group ", "are ", "");
                        } else {

                            List<String> stringListChangedGroup = new ArrayList<>();
                            stringListChangedGroup.clear();
                            for (int i = 0; i < groupListBeforeUpdate.size(); i++) {
                                stringListChangedGroup.add(groupListBeforeUpdate.get(i).getGroupId());
                            }
                            if (!(stringListChangedGroup.containsAll(groupListAfterChanged))
                                    || !(groupListAfterChanged.containsAll(stringListChangedGroup))) {
                                updatedCommentsPrepareObject(context, improveHelper, "Group ", "are ", "");
                            }
                        }

                    }

                    // Group End

                    // Individual Start

                    List<String> individualListAfterChanged = new ArrayList<>();
                    JSONArray jsonArrayPreparedIndividual = new JSONArray(jsonArrayIndividual.toString());

                    if (jsonArrayPreparedIndividual != null && jsonArrayPreparedIndividual.length() > 0) {

                        for (int i = 0; i < jsonArrayPreparedIndividual.length(); i++) {

                            JSONObject jsonObject = jsonArrayPreparedIndividual.getJSONObject(i);
                            String strEmpId = jsonObject.getString("EmpId");

                            individualListAfterChanged.add(strEmpId);
                        }
                    }

                    Gson gSonIndividual = new Gson();
                    List<TaskDepEmpDataModel> individualListBeforeUpdate = Arrays.asList(gSonIndividual.fromJson(outTaskDataModel.getEmpInfo(), TaskDepEmpDataModel[].class));

                    if (individualListBeforeUpdate != null && individualListAfterChanged != null && individualListBeforeUpdate.size() > 0 && individualListAfterChanged.size() > 0) {

                        if ((individualListBeforeUpdate.size() > individualListAfterChanged.size())
                                || (individualListAfterChanged.size() > individualListBeforeUpdate.size())) {
                            updatedCommentsPrepareObject(context, improveHelper, "Employees ", "are ", "");

                        } else {

                            List<String> stringListChangedIndividual = new ArrayList<>();
                            stringListChangedIndividual.clear();
                            for (int i = 0; i < individualListBeforeUpdate.size(); i++) {
                                stringListChangedIndividual.add(individualListBeforeUpdate.get(i).getEmp_Id());
                            }
                            if (!(stringListChangedIndividual.containsAll(individualListAfterChanged))
                                    || !(individualListAfterChanged.containsAll(stringListChangedIndividual))) {
                                updatedCommentsPrepareObject(context, improveHelper, "Employees ", "are ", "");
                            }

                        }

                    }
                    // Individual End


                    if (taskCommentDetailsListChanged != null && taskCommentDetailsListChanged.size() > 0) {

                        JSONArray jsonArray = new JSONArray();
                        for (int i = 0; i < taskCommentDetailsListChanged.size(); i++) {
                            TaskCommentDetails commentDetails = taskCommentDetailsListChanged.get(i);
                            JSONObject jsonObject = new JSONObject();

                            jsonObject.put("OrgId", commentDetails.getOrgId());
                            jsonObject.put("TaskId", commentDetails.getTaskId());
                            jsonObject.put("TaskComments", commentDetails.getTaskComments());
                            jsonObject.put("TaskStatusId", commentDetails.getTaskStatusId());
                            jsonObject.put("UserId", commentDetails.getUserId());
                            jsonObject.put("PostId", commentDetails.getPostId());
                            jsonObject.put("LocationCode", commentDetails.getLocationCode());
                            jsonObject.put("DepartmentId", commentDetails.getDepartmentId());
                            jsonObject.put("LocationCode", commentDetails.getLocationCode());
                            jsonObject.put("DesignationId", commentDetails.getDesignationId());
                            jsonObject.put("IsSelfComment", commentDetails.getIsSelfComment());
                            jsonObject.put("DeviceId", commentDetails.getDeviceId());
                            jsonObject.put("VersionNo", commentDetails.getVersionNo());
                            jsonObject.put("MobileDate", commentDetails.getMobileDate());
                            jsonArray.put(jsonObject);
                        }

                        jsonObjectMain.put("UserTaskComments", jsonArray);

                        Log.d(TAG, "prepareJsonChangedList: " + jsonArray.toString());
                    } else {
                        jsonObjectMain.put("UserTaskComments", "null");
                    }


                }


            } else {

                jsonObjectMain.put("TaskId", "");
                jsonObjectMain.put("UserTaskComments", "null");

            }
            TaskDetails.put(jsonObjectMain);

            jsonObjectFinal.put("TaskDetails", TaskDetails);
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonCreateTask = (JsonObject) jsonParser.parse(String.valueOf(jsonObjectFinal));
            Log.d(TAG, "PreparedJsonCreateTask: " + jsonCreateTask);
            if (ImproveHelper.isNetworkStatusAvialable(context)) {

                deploymentApi(jsonCreateTask);

            } else {

                improveDataBase.insertIntoDeploymentTable(strTaskId, strTaskName, strEditTask, jsonObjectMain.toString()
                        , sessionManager.getUserDataFromSession().getUserID(), sessionManager.getOrgIdFromSession(),
                        sessionManager.getPostsFromSession(), "NotUploaded");
                ImproveHelper.showToastAlert(context, "Data saved successfully");
                Intent intent = new Intent(context, BottomNavigationActivity.class);
                intent.putExtra("FromTaskDeployment", "FromTaskDeployment");
                startActivity(intent);
                clearAllLists();
                finish();

            }

        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "prepareJson", e);
        }
    }


    private void deploymentApi(JsonObject jsonCreateTask) {
        try {
            improveHelper.showProgressDialog("Please wait...");
            Call<CreateTaskResponse> createTaskResponseCall = getServices.createTask(jsonCreateTask);

            createTaskResponseCall.enqueue(new Callback<CreateTaskResponse>() {
                @Override
                public void onResponse(Call<CreateTaskResponse> call, Response<CreateTaskResponse> response) {

                    if (response.body() != null) {

                        CreateTaskResponse createTaskResponse = response.body();

                        if (createTaskResponse.getStatus() != null &&
                                createTaskResponse.getStatus().equalsIgnoreCase("200")) {


//                        editTaskAppDataModelsList.clear();
//                        editTaskContentDataModelsList.clear();
//                        editTaskGroupList.clear();
//                        editTaskIndividualList.clear();
                            clearAllLists();
                            AppConstants.IN_TASK_REFRESH_BOOLEAN = true;
                            AppConstants.OUT_TASK_REFRESH_BOOLEAN = true;
                            improveHelper.dismissProgressDialog();
                            ImproveHelper.showToastAlert(context, createTaskResponse.getMessage());
                            Intent intent = new Intent(context, BottomNavigationActivity.class);
                            intent.putExtra("FromTaskDeployment", "FromTaskDeployment");
                            startActivity(intent);
                            finish();
//                        waitingForThreeSeconds(createTaskResponse.getMessage()); // handler

                        } else {
                            improveHelper.dismissProgressDialog();
                            ImproveHelper.showToast(context, createTaskResponse.getMessage());
                        }
                    }
                    improveHelper.dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<CreateTaskResponse> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "onFailureCreateTask: " + t.toString());
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "deploymentApi", e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                taskDeployBackAlertDialog();
                finish();
                return true;
            default:
                return false;
        }
    }

    private void taskDeployBackAlertDialog() {
        new android.app.AlertDialog.Builder(context)
//set icon
//set title
                .setIcon(getApplicationContext().getResources().getDrawable(R.drawable.icon_bhargo_user_rounded))
                .setTitle(R.string.task_content)
//                .setMessage("")
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
        finish();
//        taskDeployBackAlertDialog();
    }

    private void loadGroups() {
        improveHelper.showProgressDialog(context.getString(R.string.please_wait));
        try {
            GetAppNamesForTaskModel dataModel = new GetAppNamesForTaskModel();
            dataModel.setOrgId(sessionManager.getOrgIdFromSession());
            dataModel.setUserId(sessionManager.getUserDataFromSession().getUserID());
//            dataModel.setUserId("BLUE0012");

            Call<ResponseBody> call = getServices.getTaskGroupList(dataModel);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    String json = null;
                    taskGroupList.clear();
                    try {
                        json = response.body().string();
                        improveHelper.dismissProgressDialog();
                        JSONObject responseObj = new JSONObject(json.trim());

                        JSONArray jsonArray_Group = responseObj.getJSONArray("GroupsList");

                        for (int i = 0; i < jsonArray_Group.length(); i++) {

                            JSONObject jsonObj = jsonArray_Group.getJSONObject(i);
                            TaskDepGroupDataModel taskDepGroupDataModel = new TaskDepGroupDataModel();
                            taskDepGroupDataModel.setGroupName(jsonObj.getString("GroupName") + "," + jsonObj.getString("GroupType"));
                            taskDepGroupDataModel.setGroupId(jsonObj.getString("GroupID"));
                            taskGroupList.add(taskDepGroupDataModel);
                        }
                        if (strEditTask != null) {
                            if (arrayListSelectedItemsG != null && arrayListSelectedItemsG.size() == 0 && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("0")) {
                                editTaskGroupList.clear();
                            } else {
                                if (taskGroupList != null && taskGroupList.size() > 0) {
                                    for (int i = 0; i < taskGroupList.size(); i++) {
                                        if (editTaskGroupList != null && editTaskGroupList.size() > 0) {
                                            for (int j = 0; j < editTaskGroupList.size(); j++) {
                                                if (taskGroupList.get(i).getGroupId().equalsIgnoreCase(editTaskGroupList.get(j).getGroupId())) {
                                                    taskGroupList.get(i).setSelected(true);
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                        if (taskGroupList != null && taskGroupList.size() > 0) {

                            customDialogAlert(context, "Group", taskGroupList, taskIndividualList);
                        } else {
                            ImproveHelper.showToast(context, "No Groups record Available!");
                        }

                    } catch (Exception e) {
                        improveHelper.dismissProgressDialog();
                        Log.d("TaskGroupException", e.toString());
                    }
                    improveHelper.dismissProgressDialog();
                    Log.d("TaskGroup", "onResponse: " + json);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    Log.d("TaskGroupFailure", t.toString());
                    if (t.toString().contains("Failed to connect to")) {
                        ImproveHelper.showToast(context, "Please check internet connection");
                    }
                }
            });


        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            improveHelper.improveException(context, TAG, "loadGroups", e);
        }


    }

    private void loadIndividuals() {

        improveHelper.showProgressDialog(context.getString(R.string.please_wait));
        try {
            Call<ResponseBody> call = getServices.GetEmpList(sessionManager.getOrgIdFromSession(),
                    sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession());

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    String json = null;
                    taskIndividualList.clear();
                    try {
                        json = response.body().string();
                        improveHelper.dismissProgressDialog();
                        JSONArray jsonArray_Group = new JSONArray(json.trim());
                        for (int i = 0; i < jsonArray_Group.length(); i++) {
                            JSONObject jsonObj = jsonArray_Group.getJSONObject(i);
                            TaskDepEmpDataModel taskDepEmpDataModel = new TaskDepEmpDataModel();
                            taskDepEmpDataModel.setEmpName(jsonObj.getString("EmpName") + "," + jsonObj.getString("Role") + "\n" + jsonObj.getString("PostName"));
                            taskDepEmpDataModel.setEmp_Id(jsonObj.getString("Emp_Id"));
                            taskDepEmpDataModel.setPhoneNo(jsonObj.getString("PhoneNo"));
                            taskDepEmpDataModel.setPostName(jsonObj.getString("PostName"));
                            taskDepEmpDataModel.setDesignationID(jsonObj.getString("DesignationName"));
                            taskDepEmpDataModel.setDesignationName(jsonObj.getString("DepartmentName"));
                            taskDepEmpDataModel.setPostId(jsonObj.getString("PostId"));
                            taskDepEmpDataModel.setEmpPostId(jsonObj.getString("EmpPostId"));
                            taskDepEmpDataModel.setDesignationID(jsonObj.getString("DesignationID"));
                            taskDepEmpDataModel.setDepartmentID(jsonObj.getString("DepartmentID"));
                            taskDepEmpDataModel.setLocationCode(jsonObj.getString("LocationCode"));
                            taskDepEmpDataModel.setImagePath(jsonObj.getString("ImagePath"));

                            taskIndividualList.add(taskDepEmpDataModel);
                        }

                        if (strEditTask != null) {

                            if (arrayListSelectedItemsI != null && arrayListSelectedItemsI.size() == 0 && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("0")) {
                                editTaskIndividualList.clear();
                            } else {

                                if (taskIndividualList != null && taskIndividualList.size() > 0) {

                                    for (int i = 0; i < taskIndividualList.size(); i++) {

                                        if (editTaskIndividualList != null && editTaskIndividualList.size() > 0) {
                                            for (int j = 0; j < editTaskIndividualList.size(); j++) {
                                                if (taskIndividualList.get(i).getPostId().equalsIgnoreCase(editTaskIndividualList.get(j).getPostId())
                                                        && taskIndividualList.get(i).getEmp_Id().equalsIgnoreCase(editTaskIndividualList.get(j).getEmp_Id())) {
                                                    taskIndividualList.get(i).setSelected(true);
                                                    Log.d(TAG, "onResponseIndividual: " + taskIndividualList.get(i).getEmpName() + " " + editTaskIndividualList.get(j).getEmpName());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            improveHelper.dismissProgressDialog();
                        }
                        if (taskIndividualList != null && taskIndividualList.size() > 0) {
                            customDialogAlert(context, "Individual", taskGroupList, taskIndividualList);
                        } else {
                            ImproveHelper.showToast(context, "No Employees record Available!");
                        }
                        improveHelper.dismissProgressDialog();
                    } catch (Exception e) {
                        improveHelper.dismissProgressDialog();
                        Log.d("TaskIndividualException", e.toString());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    Log.d("TaskIndividualFailure", t.toString());
                    if (t.toString().contains("Failed to connect to")) {
                        ImproveHelper.showToast(context, "Please check internet connection");
                    }
                }
            });


        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            improveHelper.improveException(context, TAG, "loadGroups", e);
        }
    }

    private boolean isValidate() {
        boolean flag = true;
        try {
            if ((arrayListSelectedItemsG != null && arrayListSelectedItemsG.size() == 0)
                    && (arrayListSelectedItemsI != null && arrayListSelectedItemsI.size() == 0)) {
                ImproveHelper.showToast(this, "Please Select Group Or Individual");
                flag = false;
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "isValidate", e);
        }
        return flag;
    }

    private void customDialogAlert(Context context, String msg, List<TaskDepGroupDataModel> taskDepGroupDataModelList,
                                   List<TaskDepEmpDataModel> taskDepEmpDataModelList) {
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

            if (msg.equalsIgnoreCase("Group")) {

                etv_cadTitle.setHint("Select Groups");

                taskDeploymentAdapter = new TaskDeploymentAdapter(context, taskDepGroupDataModelList, taskDepEmpDataModelList, true);

            } else if (msg.equalsIgnoreCase("Individual")) {

                etv_cadTitle.setHint("Select Employees");
                taskDeploymentAdapter = new TaskDeploymentAdapter(context, taskDepGroupDataModelList, taskDepEmpDataModelList, false);

            }

            rv_cad.setAdapter(taskDeploymentAdapter);
            taskDeploymentAdapter.notifyDataSetChanged();

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
                        if (msg.equalsIgnoreCase("Group")) {

                            taskDeploymentAdapter.filterGroups(taskGroupList);

                        } else if (msg.equalsIgnoreCase("Individual")) {
                            taskDeploymentAdapter.filterIndividuals(taskIndividualList);

                        }
                    }
                }
            });


            ctv_Ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (msg.equalsIgnoreCase("Group")) {

                        taskGroupListSelected.clear();
                        taskGroupListSelected.addAll(taskDeploymentAdapter.getGroupListFromAdapter());
                        if (taskGroupListSelected.size() == 0) {
                            arrayListSelectedItemsG.clear();
                        }

                        inflateDataInRecyclerView(taskGroupListSelected, null);

                    } else if (msg.equalsIgnoreCase("Individual")) {

                        taskIndividualListSelected.clear();
                        taskIndividualListSelected.addAll(taskDeploymentAdapter.getEmpListFromAdapter());
                        if (taskIndividualListSelected.size() == 0) {
                            arrayListSelectedItemsI.clear();
                        }

                        inflateDataInRecyclerView(null, taskIndividualListSelected);

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

    private void inflateDataInRecyclerView(List<TaskDepGroupDataModel> taskGroupListSelected, List<TaskDepEmpDataModel> taskIndividualListSelected) {

        try {
            if (taskGroupListSelected != null && taskGroupListSelected.size() > 0) {
                rv_groups.setAdapter(null);
                arrayListSelectedItemsG.clear();
                jsonArrayGroup = new JSONArray(new ArrayList<String>());
                if (EDIT_STATUS_GROUP == 0) {
                    for (int i = 0; i < taskGroupListSelected.size(); i++) {
                        jsonGroupList = new JSONObject();
                        try {
                            if (strEditTask != null) {
                                jsonGroupList.put("TaskGroupSharingID", strTaskId);
                                jsonGroupList.put("GroupId", taskGroupListSelected.get(i).getGroupId());
                            } else {
                                jsonGroupList.put("TaskGroupSharingID", "null");
                                jsonGroupList.put("GroupId", taskGroupListSelected.get(i).getGroupId());
                            }
//                        jsonGroupList.put("GroupName", editTaskGroupList.get(i).getGroupName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonArrayGroup.put(jsonGroupList);
                        arrayListSelectedItemsG.add(taskGroupListSelected.get(i).getGroupName());
                    }
//                EDIT_STATUS_GROUP = 1;
                } else {
                    for (int i = 0; i < taskGroupListSelected.size(); i++) {
                        if (taskGroupListSelected.get(i).isSelected()) {
                            jsonGroupList = new JSONObject();
                            try {
                                if (strEditTask != null) {
                                    jsonGroupList.put("TaskGroupSharingID", strTaskId);
                                    jsonGroupList.put("GroupId", taskGroupListSelected.get(i).getGroupId());
                                } else {
                                    jsonGroupList.put("TaskGroupSharingID", "null");
                                    jsonGroupList.put("GroupId", taskGroupListSelected.get(i).getGroupId());
                                }//                        jsonGroupList.put("GroupName", editTaskGroupList.get(i).getGroupName());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArrayGroup.put(jsonGroupList);
                            arrayListSelectedItemsG.add(taskGroupListSelected.get(i).getGroupName());
                        }
                    }

                }

                DeployGroupAdapter adapter = new DeployGroupAdapter(context, arrayListSelectedItemsG);
                rv_groups.setAdapter(adapter);
                if (arrayListSelectedItemsG.size() > 0) {
                    rv_groups.setVisibility(View.VISIBLE);
                    ct_noGroupSelect.setVisibility(View.GONE);
                } else {
                    rv_groups.setVisibility(View.GONE);
                    ct_noGroupSelect.setVisibility(View.VISIBLE);
                }
            } else {

                if (arrayListSelectedItemsG.size() > 0) {
                    rv_groups.setVisibility(View.VISIBLE);
                    ct_noGroupSelect.setVisibility(View.GONE);
                } else {
                    jsonArrayGroup = new JSONArray(new ArrayList<String>());
                    rv_groups.setVisibility(View.GONE);
                    ct_noGroupSelect.setVisibility(View.VISIBLE);
                }
            }

            if (taskIndividualListSelected != null && taskIndividualListSelected.size() > 0) {
                rv_individuals.setAdapter(null);
                arrayListSelectedItemsI.clear();
                jsonArrayIndividual = new JSONArray(new ArrayList<String>());
                if (EDIT_STATUS_EMP == 0) {
                    for (int i = 0; i < taskIndividualListSelected.size(); i++) {

                        jsonIndividualList = new JSONObject();
                        try {
                            if (strEditTask != null) {

                                jsonIndividualList.put("EmpId", taskIndividualListSelected.get(i).getEmp_Id());
                                jsonIndividualList.put("TaskIndividualSharingID", strTaskId);
                                jsonIndividualList.put("PostId", taskIndividualListSelected.get(i).getPostId());
                                jsonIndividualList.put("DesignationId", taskIndividualListSelected.get(i).getDesignationID());
                                jsonIndividualList.put("DepartmentId", taskIndividualListSelected.get(i).getDepartmentID());
                                jsonIndividualList.put("LocationCode", taskIndividualListSelected.get(i).getLocationCode());

                            } else {

                                jsonIndividualList.put("EmpId", taskIndividualListSelected.get(i).getEmp_Id());
                                jsonIndividualList.put("TaskIndividualSharingID", "null");
                                jsonIndividualList.put("PostId", taskIndividualListSelected.get(i).getPostId());
                                jsonIndividualList.put("DesignationId", taskIndividualListSelected.get(i).getDesignationID());
                                jsonIndividualList.put("DepartmentId", taskIndividualListSelected.get(i).getDepartmentID());
                                jsonIndividualList.put("LocationCode", taskIndividualListSelected.get(i).getLocationCode());
                            }
//                            jsonIndividualList.put("EmpName", selectedItems.get(i).getName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonArrayIndividual.put(jsonIndividualList);

                        arrayListSelectedItemsI.add(taskIndividualListSelected.get(i).getEmpName()
                                + "," + taskIndividualListSelected.get(i).getDesignationName()
                                + "-" + taskIndividualListSelected.get(i).getPostName());
//                arrayListSelectedItemsIIds.add(taskIndividualListSelected.get(i).getInfoID());

                    }

//                EDIT_STATUS_EMP = 1;
                } else {
                    for (int i = 0; i < taskIndividualListSelected.size(); i++) {
                        if (taskIndividualListSelected.get(i).isSelected()) {
                            jsonIndividualList = new JSONObject();
                            try {
                                if (strEditTask != null) {

                                    jsonIndividualList.put("EmpId", taskIndividualListSelected.get(i).getEmp_Id());
                                    jsonIndividualList.put("TaskIndividualSharingID", strTaskId);
                                    jsonIndividualList.put("PostId", taskIndividualListSelected.get(i).getPostId());
                                    jsonIndividualList.put("DesignationId", taskIndividualListSelected.get(i).getDesignationID());
                                    jsonIndividualList.put("DepartmentId", taskIndividualListSelected.get(i).getDepartmentID());
                                    jsonIndividualList.put("LocationCode", taskIndividualListSelected.get(i).getLocationCode());

                                } else {

                                    jsonIndividualList.put("EmpId", taskIndividualListSelected.get(i).getEmp_Id());
                                    jsonIndividualList.put("TaskIndividualSharingID", "null");
                                    jsonIndividualList.put("PostId", taskIndividualListSelected.get(i).getPostId());
                                    jsonIndividualList.put("DesignationId", taskIndividualListSelected.get(i).getDesignationID());
                                    jsonIndividualList.put("DepartmentId", taskIndividualListSelected.get(i).getDepartmentID());
                                    jsonIndividualList.put("LocationCode", taskIndividualListSelected.get(i).getLocationCode());

                                }
                                //                            jsonIndividualList.put("EmpName", editTaskIndividualList.get(i).getEmpName());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArrayIndividual.put(jsonIndividualList);

                            arrayListSelectedItemsI.add(taskIndividualListSelected.get(i).getEmpName()
                                    + "," + taskIndividualListSelected.get(i).getDesignationName()
                                    + " - " + taskIndividualListSelected.get(i).getPostName());
                        }
                    }
                }
                DeployIndividualAdapter adapter = new DeployIndividualAdapter(context, arrayListSelectedItemsI);
                rv_individuals.setAdapter(adapter);

                if (arrayListSelectedItemsI.size() > 0) {
                    rv_individuals.setVisibility(View.VISIBLE);
                    ct_noIndividualSelect.setVisibility(View.GONE);
                } else {
                    ct_noIndividualSelect.setVisibility(View.VISIBLE);
                    rv_individuals.setVisibility(View.GONE);
                }

            } else {

                if (arrayListSelectedItemsI.size() > 0) {
                    rv_individuals.setVisibility(View.VISIBLE);
                    ct_noIndividualSelect.setVisibility(View.GONE);
                } else {
                    jsonArrayIndividual = new JSONArray(new ArrayList<String>());
                    ct_noIndividualSelect.setVisibility(View.VISIBLE);
                    rv_individuals.setVisibility(View.GONE);
                }
            }
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "inflateDataInRecyclerView", e);
        }

    }

    public void filter(String text, String strFrom) {
        try {
            if (strFrom.equalsIgnoreCase("Group")) {

                List<TaskDepGroupDataModel> taskDepGroupDataModelsList = new ArrayList<>();

                for (TaskDepGroupDataModel taskDepGroupDataModel : taskGroupList) {
                    if (taskDepGroupDataModel.getGroupName().toLowerCase().contains(text.toLowerCase())) {
                        taskDepGroupDataModelsList.add(taskDepGroupDataModel);
                    }

                }
                taskDeploymentAdapter.filterGroups(taskDepGroupDataModelsList);
            } else { //Individual
                List<TaskDepEmpDataModel> taskDepEmpDataModelArrayList = new ArrayList<>();

                for (TaskDepEmpDataModel taskDepEmpDataModel : taskIndividualList) {
                    if (taskDepEmpDataModel.getEmpName().toLowerCase().contains(text.toLowerCase())) {
                        taskDepEmpDataModelArrayList.add(taskDepEmpDataModel);
                    }

                }
                taskDeploymentAdapter.filterIndividuals(taskDepEmpDataModelArrayList);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "filter", e);
        }

    }

    public void updatedCommentsPrepareObject(Context context, ImproveHelper improveHelper, String Name, String beforeChange, String afterChanged) {
        try {
            TaskCommentDetails data = new TaskCommentDetails();
            data.setOrgId(sessionManager.getOrgIdFromSession());
            data.setTaskId(strTaskId);
            data.setTaskComments(Name + " " + beforeChange + " updated " + afterChanged + ".");
            data.setTaskStatusId("4");
            data.setUserId(sessionManager.getUserDataFromSession().getUserID());
            data.setPostId(sessionManager.getPostsFromSession());
            data.setLocationCode(sessionManager.getUserDetailsFromSession().getLocationCode());
            data.setDepartmentId(sessionManager.getUserDetailsFromSession().getDepartmentID());
            data.setDesignationId(sessionManager.getUserDetailsFromSession().getDesignationID());
            data.setIsSelfComment("1");
            data.setDeviceId(ImproveHelper.getMyDeviceId(context));
            data.setVersionNo(improveHelper.getAppVersionFromGradle());
            data.setMobileDate(ImproveHelper.currentTime());

            taskCommentDetailsListChanged.add(data);
//        return taskCommentDetailsList;
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "updatedCommentsPrepareObject", e);
        }
    }

    public String removeCharFromString(String str) {
        try {
            if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == 'x') {
                str = str.substring(0, str.length() - 1);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "removeCharFromString", e);
        }

        return str;
    }

//    public void clearAllLists() {
//        if (editTaskAppDataModelsList != null) {
//            editTaskAppDataModelsList.clear();
//        }
//        if (editTaskContentDataModelsList != null) {
//            editTaskContentDataModelsList.clear();
//        }
//        if (editTaskGroupList != null) {
//            editTaskGroupList.clear();
//        }
//
//        if (editTaskIndividualList != null) {
//            editTaskIndividualList.clear();
//        }
//    }

    public void clearAllLists() {

        if (editTaskAppDataModelsList != null) {
            editTaskAppDataModelsList = new ArrayList<>();
        }
        if (editTaskContentDataModelsList != null) {
            editTaskContentDataModelsList = new ArrayList<>();
        }
        if (editTaskGroupList != null) {
            editTaskGroupList = new ArrayList<>();
        }

        if (editTaskIndividualList != null) {
            editTaskIndividualList = new ArrayList<>();
        }

    }

    class TaskHandler implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i <= 20; i++) {
                final int value = i;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        }
    }

}
