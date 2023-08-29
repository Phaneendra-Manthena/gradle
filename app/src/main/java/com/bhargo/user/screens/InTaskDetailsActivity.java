package com.bhargo.user.screens;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.CadAdapter;
import com.bhargo.user.adapters.CommentsAdapter;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomRadioButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppsInfoModel;
import com.bhargo.user.pojos.CommentsInfoModel;
import com.bhargo.user.pojos.CommentsResponse;
import com.bhargo.user.pojos.ContentInfoModel;
import com.bhargo.user.pojos.InTaskDataModel;
import com.bhargo.user.pojos.InTaskRefreshSendData;
import com.bhargo.user.pojos.InsertComments;
import com.bhargo.user.pojos.RefreshTaskDetails;
import com.bhargo.user.pojos.SingleUserInfo;
import com.bhargo.user.pojos.TaskCmtDataModel;
import com.bhargo.user.pojos.TaskCommentDetails;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.utils.ImproveDataBase.SEND_COMMENTS_OFFLINE_TABLE;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;


public class InTaskDetailsActivity extends BaseActivity {

    private static final String TAG = "InTaskDetailsActivity";
    private final String strCommentCount = "0";
    Context context;
    //    String strTaskModify, strInTaskId, strTaskName, strStartTime, strEndTime, strPriority,
//            strUserTaskId, strUpUserId, strTaskTransId, strTaskDeploymentId, strInfoId,strTaskStatusId;
    String strInTaskCreatedBy, strInTaskId, strInTaskStatusId, strInTaskIsSingleUser, strInTaskSingleUserStatus, strInTaskName, strInTaskDesc, strInTaskDistributionDate, strInTaskStartTime, strInTaskEndTime, strInTaskPriority,
            strInTaskContentList, strInTaskStatus, strInTaskComments, strInTaskTotalInProgress, strInTaskTotalCompleted, strInTaskSingleUserInfo, strInTaskDistributionID;
    List<AppsInfoModel> appsInfoModelList;
    List<ContentInfoModel> contentInfoModelList;
    List<CommentsInfoModel> commentsInfoModelList = new ArrayList<>();
    List<CommentsInfoModel> commentsInfoModelListDB;
    //    List<TaskAppDataModelAC> taskAppDataModelAC;
//    List<TaskContentDataModelAC> taskContentDataModelAC;
    List<TaskCmtDataModel> taskCmtDataModelAC;
    SessionManager sessionManager;
    ImproveHelper improveHelper;
    CustomTextView ctv_StartTime, ctv_EndTime, ctv_priority, appsAlert, contentAlert;
    GetServices getServices;
    RecyclerView rv_comments;
    CommentsAdapter commentsAdapter;
    ImageButton btn_send_comment;
    CustomEditText cet_Comment;
    RadioGroup rg_TaskStatus;
    CustomRadioButton rb_inProgress, rb_complete;
    ImproveDataBase improveDataBase;
    LinearLayout ll_taskStatus;
    RelativeLayout ll_comment_box;
    boolean isRefresh = false;
    TaskCommentDetails sendCommentsOffLineDB;
    private String strRbStatusId = null;
    private LinearLayoutManager llCmts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_task_details);

        context = this;

        sessionManager = new SessionManager(context);
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);

        AppConstants.IS_SINGLE_USER = false;

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            try {
                strInTaskCreatedBy = extras.getString("in_task_created_by");
                strInTaskId = extras.getString("in_task_id");
                AppConstants.C_IN_TASK_ID = strInTaskId;
                strInTaskStatusId = extras.getString("in_task_status_id");
                strInTaskIsSingleUser = extras.getString("in_task_is_single_user");
                strInTaskSingleUserStatus = extras.getString("in_task_single_user_status");
                strInTaskName = extras.getString("in_task_name");
                strInTaskDesc = extras.getString("in_task_desc");
                strInTaskDistributionDate = extras.getString("in_task_distribution_date");
                strInTaskStartTime = extras.getString("in_task_start_time");
                strInTaskEndTime = extras.getString("in_task_end_time");
                strInTaskPriority = extras.getString("in_tasks_priority");
                appsInfoModelList = (List<AppsInfoModel>) getIntent().getSerializableExtra("in_task_app_list");
                contentInfoModelList = (List<ContentInfoModel>) getIntent().getSerializableExtra("in_task_content_list");
                commentsInfoModelList = (List<CommentsInfoModel>) getIntent().getSerializableExtra("in_task_comments");
                strInTaskStatus = extras.getString("in_task_task_status");
                strInTaskTotalInProgress = extras.getString("in_task_total_in_progress");
                strInTaskTotalCompleted = extras.getString("in_task_total_completed");
                strInTaskSingleUserInfo = extras.getString("in_task_single_user_info");
                strInTaskDistributionID = extras.getString("in_task_distribution_id");
                AppConstants.C_IN_TASK_DID = strInTaskDistributionID;

            } catch (Exception e) {
                improveHelper.improveException(this, TAG, "onCreate", e);
            }

        }

        initViews();

    }


    public void initViews() {
        try {
            initializeActionBar();
            enableBackNavigation(true);
            getServices = RetrofitUtils.getUserService();
            iv_circle_appIcon.setVisibility(View.GONE);
            title.setText(strInTaskName);
            ctv_StartTime = findViewById(R.id.ctv_StartTime);
            ctv_EndTime = findViewById(R.id.ctv_EndTime);
            ctv_priority = findViewById(R.id.ctv_priority);
            appsAlert = findViewById(R.id.appsAlert);
            contentAlert = findViewById(R.id.contentAlert);
            rv_comments = findViewById(R.id.rv_comments);
            cet_Comment = findViewById(R.id.cet_Comment);
            btn_send_comment = findViewById(R.id.btn_send_comment);
            rg_TaskStatus = findViewById(R.id.rg_TaskStatus);
            rb_inProgress = findViewById(R.id.rb_inProgress);
            rb_complete = findViewById(R.id.rb_complete);
            ll_comment_box = findViewById(R.id.ll_comment_box);
            ll_taskStatus = findViewById(R.id.ll_taskStatus);

            ctv_StartTime.setText(strInTaskStartTime);
            ctv_EndTime.setText(strInTaskEndTime);
            ctv_priority.setText(strInTaskPriority);

            /*Comments*/
            llCmts = new LinearLayoutManager(context);
            llCmts.setOrientation(LinearLayoutManager.VERTICAL);
            rv_comments.setLayoutManager(llCmts);

            if (improveHelper.isGivenDateIsBeforeOrLessThanCurrent(strInTaskStartTime)) {
                btn_send_comment.setVisibility(View.GONE);
            } else {
                btn_send_comment.setVisibility(View.VISIBLE);
            }

            // SingleUser senario

/*
            if (strInTaskIsSingleUser.equalsIgnoreCase("1") && strInTaskSingleUserStatus.equalsIgnoreCase("Any one user")) {
                SingleUserInfo singleUserInfoDB = null;
                if (improveDataBase.doesTableExist(ImproveDataBase.TASK_ATTEMPTED_BY_ELSE_TABLE)) {
                    singleUserInfoDB = improveDataBase.getSingleObjectFromTaskAttemptedByElse(strInTaskId, sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession());
                    if (singleUserInfoDB != null && !singleUserInfoDB.isFirstTime() && singleUserInfoDB.getPostID() != null && singleUserInfoDB.getPostID().equalsIgnoreCase(sessionManager.getPostsFromSession())) {
                        AppConstants.IS_SINGLE_USER = true;
                        enableStatusComment();

                    } else {
                        disableStatusComment();
                    }
                }

            }
*/


            if (strInTaskIsSingleUser.equalsIgnoreCase("1") && strInTaskSingleUserStatus.equalsIgnoreCase("Any one user")) {
                if (strInTaskSingleUserInfo != null && !strInTaskSingleUserInfo.equalsIgnoreCase("[]")) {
                    Gson gson = new Gson();
                    List<SingleUserInfo> singleUserInfoList = Arrays.asList(gson.fromJson(strInTaskSingleUserInfo, SingleUserInfo[].class));

                    if (singleUserInfoList.get(0).getEmpID().equalsIgnoreCase(sessionManager.getUserDataFromSession().getUserID())
                            && singleUserInfoList.get(0).getPostID().equalsIgnoreCase(sessionManager.getPostsFromSession())
                            && singleUserInfoList.get(0).getTaskID().equalsIgnoreCase(strInTaskId)) {
                        AppConstants.IS_SINGLE_USER = true;
                        enableStatusComment();
                    } else {
                        disableStatusComment();
                    }
                }

            }


            // Getting data from SendCommentsOffLine
            List<TaskCommentDetails> taskCommentDetailsList = new ArrayList<>();
            taskCommentDetailsList.clear();
            if (improveDataBase.doesTableExist(SEND_COMMENTS_OFFLINE_TABLE) && !improveDataBase.isTableEmpty(SEND_COMMENTS_OFFLINE_TABLE)) {
                taskCommentDetailsList = improveDataBase.getDataFromSendCommentsOffLine(sessionManager.getOrgIdFromSession(),
                        sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession());
                for (int i = 0; i < taskCommentDetailsList.size(); i++) {
                    if (taskCommentDetailsList.get(i).getTaskStatusId().equalsIgnoreCase("3") && taskCommentDetailsList.get(i).getTaskId().equalsIgnoreCase(strInTaskId)) {
                        disableStatusComment();
                        break;
                    }
                }
            }

            // send comment offline complete status checking
            if (improveDataBase.doesTableExist(SEND_COMMENTS_OFFLINE_TABLE) && !improveDataBase.isTableEmpty(SEND_COMMENTS_OFFLINE_TABLE)) {

                sendCommentsOffLineDB = improveDataBase.getSingleObjectFromSendCommentsOffLine(strInTaskId, "3", sessionManager.getOrgIdFromSession(),
                        sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession());
                if (sendCommentsOffLineDB != null && sendCommentsOffLineDB.getTaskStatusId().equalsIgnoreCase("3")) {
                    disableStatusComment();
                }
            }


            if (isNetworkStatusAvialable(context)) {
                getAllInTaskComments();
            } else {
                if (improveDataBase.doesTableExist(ImproveDataBase.IN_TASK_COMMENTS_TABLE)) {
                    setCommentsFromDB();
                }
            }

            if (strInTaskStatusId.equalsIgnoreCase("3") || improveHelper.isGivenDateIsBeforeOrLessThanCurrent(strInTaskStartTime)
                    || improveHelper.isGivenDateIsAfterOrGraterThanCurrent(strInTaskEndTime)) {
                disableStatusComment();
            }

            appsAlert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (appsInfoModelList != null && appsInfoModelList.size() > 0) {
                        customDialogAlert(context, "Apps", appsInfoModelList, null);
                    } else {
                        ImproveHelper.showToast(context, "No Application records found");
                    }
                }
            });

            contentAlert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (contentInfoModelList != null && contentInfoModelList.size() > 0) {
                        customDialogAlert(context, "Content", null, contentInfoModelList);
                    } else {
                        ImproveHelper.showToast(context, "No Content records found");
                    }
                }
            });

            rg_TaskStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    int selectedId = group.getCheckedRadioButtonId();
                    CustomRadioButton rbStatusId = findViewById(selectedId);
                    Log.d(TAG, "onCheckedChangedTask: " + rbStatusId.getId());
                    Log.d(TAG, "onCheckedChangedTask: " + rbStatusId.getText());

                    if (rbStatusId.getText().toString().equalsIgnoreCase("Inprogress")) {
                        strRbStatusId = "2";
                    } else if (rbStatusId.getText().toString().equalsIgnoreCase("Completed")) {
                        strRbStatusId = "3";
                    } else {
                        strRbStatusId = null;
                    }
                }
            });

            btn_send_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (strRbStatusId == null
                            && strRbStatusId.equalsIgnoreCase("")) {

                        ImproveHelper.showToast(context, "Please select status.");

                    } else if (cet_Comment.getText().toString().isEmpty() || cet_Comment.getText().toString().length() == 0) {
                        ImproveHelper.showToast(context, "Please enter comment");
                    } else {

                        if (strRbStatusId.equalsIgnoreCase("3")) {
                            if (improveDataBase.doesTableExist(SEND_COMMENTS_OFFLINE_TABLE)
                                    && !improveDataBase.isTableEmpty(SEND_COMMENTS_OFFLINE_TABLE)) {
                                sendCommentsOffLineDB = improveDataBase.getSingleObjectFromSendCommentsOffLine(strInTaskId, "3", sessionManager.getOrgIdFromSession(),
                                        sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession());
                                if (sendCommentsOffLineDB != null &&
                                        sendCommentsOffLineDB.getTaskStatusId().equalsIgnoreCase("3")) {
                                    ImproveHelper.showToastAlert(context, "Already Completed.");
                                    disableStatusComment();
                                } else {
                                    taskCompletedStatusAlertDialog();
                                }
                            } else {
                                taskCompletedStatusAlertDialog();
                            }
                        } else {
                            sendCommentApi();
                        }
                    }
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "initViews", e);
        }

    }

    public void customDialogAlert(Context context, String msg, List<AppsInfoModel> appsInfoModelList, List<ContentInfoModel> contentInfoModelList) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.cad_recyclerview_layout);

            CustomTextView tv_cadTitle = dialog.findViewById(R.id.tv_cadTitle);
            RecyclerView rv_cad = dialog.findViewById(R.id.rv_cad);
            CadAdapter cadAdapter = null;

            LinearLayoutManager llm = new LinearLayoutManager(context);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rv_cad.setLayoutManager(llm);

            if (msg.equalsIgnoreCase("Apps")) {

                tv_cadTitle.setText("Applications");
                cadAdapter = new CadAdapter(context, dialog, appsInfoModelList, strInTaskDistributionID);

            } else if (msg.equalsIgnoreCase("Content")) {

                tv_cadTitle.setText("Content");
                cadAdapter = new CadAdapter(contentInfoModelList, context, dialog, strInTaskDistributionID);

            }

            rv_cad.setAdapter(cadAdapter);
            cadAdapter.notifyDataSetChanged();

            dialog.show();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "customDialogAlert", e);
        }

    }

    private void sendCommentApi() {
        try {
            if (strRbStatusId != null) {

                improveHelper.showProgressDialog(context.getString(R.string.please_wait));

                TaskCommentDetails data = new TaskCommentDetails();
                data.setOrgId(sessionManager.getOrgIdFromSession());
                data.setTaskId(strInTaskId);
                data.setTaskComments(cet_Comment.getText().toString());
                data.setTaskStatusId(strRbStatusId);
                data.setUserId(sessionManager.getUserDataFromSession().getUserID());
                data.setPostId(sessionManager.getPostsFromSession());
                data.setLocationCode(sessionManager.getUserDetailsFromSession().getLocationCode());
                data.setDepartmentId(sessionManager.getUserDetailsFromSession().getDepartmentID());
                data.setDesignationId(sessionManager.getUserDetailsFromSession().getDesignationID());
                data.setIsSelfComment("0");
                data.setDeviceId(ImproveHelper.getMyDeviceId(context));
                data.setVersionNo(improveHelper.getAppVersionFromGradle());
                data.setMobileDate(ImproveHelper.currentTime());

                List<TaskCommentDetails> taskCommentDetailsList = new ArrayList<>();
                taskCommentDetailsList.add(data);
                InsertComments insertComments = new InsertComments();
                insertComments.setTaskCommentDetails(taskCommentDetailsList);
                if (!isNetworkStatusAvialable(InTaskDetailsActivity.this) && strInTaskIsSingleUser.equalsIgnoreCase("0")) {
                    improveDataBase.insertIntoSendCommentOffline(taskCommentDetailsList, "Offline");
                    cet_Comment.setText("");
                    improveHelper.dismissProgressDialog();
                    ImproveHelper.showToastAlert(context, "Comment saved successfully.");
                    if (strRbStatusId != null && !strRbStatusId.equalsIgnoreCase("") && strRbStatusId.equalsIgnoreCase("3")) {
                        disableStatusComment();
                    }
                } else {
                    Log.d(TAG, "sendCommentApiCheckT: " + new Gson().toJson(insertComments));
                    if (isNetworkStatusAvialable(context)) {
                        Call<CommentsResponse> responseCall = getServices.insertCommentsDetails(insertComments);
                        responseCall.enqueue(new Callback<CommentsResponse>() {
                            @Override
                            public void onResponse(Call<CommentsResponse> call, Response<CommentsResponse> response) {

                                if (response.body() != null) {
                                    if (response.body().getStatus().equalsIgnoreCase("100")) {
                                        ImproveHelper.showToast(context, response.body().getMessage());
                                    } else {
                                        CommentsResponse commentsResponse = response.body();
                                        if (commentsResponse != null && commentsResponse.getStatus().equalsIgnoreCase("200")) {
                                            mPrepareRefreshData();
                                            improveHelper.dismissProgressDialog();
                                            if (commentsResponse.getMessage().equalsIgnoreCase("Success")) {
                                                if (strRbStatusId.equalsIgnoreCase("3")) {
                                                    disableStatusComment();
                                                }
                                            }
                                            cet_Comment.setText("");
                                            if (isNetworkStatusAvialable(context)) {
                                                getAllInTaskComments();
                                            } else {
                                                setCommentsFromDB();
                                            }

//                                setCommentsFromDB();

                                        } else if (commentsResponse != null && commentsResponse.getStatus().equalsIgnoreCase("300")) {
                                            getTaskAttemptsByOtherPostApi(context, sessionManager.getUserDataFromSession().getUserID(),
                                                    sessionManager.getOrgIdFromSession(), sessionManager.getPostsFromSession(), strInTaskId, commentsResponse.getMessage());
//                                        disableStatusComment();
//                                        ImproveHelper.showToastAlert(context, commentsResponse.getMessage());
//                                        AppConstants.IN_TASK_REFRESH_BOOLEAN = true;
//                                        Intent intent = new Intent(context, BottomNavigationActivity.class);
//                                        intent.putExtra("FromNormalTask", "FromNormalTask");
//                                        startActivity(intent);
//                                        finish();
                                        } else if (commentsResponse != null && commentsResponse.getStatus().equalsIgnoreCase("400")) {
                                            improveHelper.dismissProgressDialog();
                                            ImproveHelper.showToastAlert(context, commentsResponse.getMessage());
                                            disableStatusComment();
                                            AppConstants.IN_TASK_REFRESH_BOOLEAN = true;
                                            Intent intent = new Intent(context, BottomNavigationActivity.class);
                                            intent.putExtra("FromNormalTask", "FromNormalTask");
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            improveHelper.dismissProgressDialog();
                                        }
                                    }
                                }
                                improveHelper.dismissProgressDialog();
                            }

                            @Override
                            public void onFailure(Call<CommentsResponse> call, Throwable t) {
                                improveHelper.dismissProgressDialog();

                                Log.d(TAG, "onFailureTaskAppDetails: " + t.toString());
                            }
                        });
                    } else {
                        ImproveHelper.showToast(context, "Please check internet connection ");
                    }
                }

            } else {
                ImproveHelper.showToast(context, "Please select status.");
            }

            improveHelper.dismissProgressDialog();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "sendCommentApi", e);
        }

    }

    public void getAllInTaskComments() {
        try {
            improveHelper.showProgressDialog("Please wait...");
            Call<List<CommentsInfoModel>> listCall = getServices.getAllCommentsDetails(sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(),
                    sessionManager.getPostsFromSession(), strInTaskId);
            listCall.enqueue(new Callback<List<CommentsInfoModel>>() {
                @Override
                public void onResponse(Call<List<CommentsInfoModel>> call, Response<List<CommentsInfoModel>> response) {

                    if (response.body() != null) {
                        List<CommentsInfoModel> commentsInfoModelList = response.body();
                        if (commentsInfoModelList != null && commentsInfoModelList.size() > 0) {

                            if (improveDataBase.doesTableExist(ImproveDataBase.IN_TASK_COMMENTS_TABLE)) {
                                improveDataBase.deleteAllRowsInTable(ImproveDataBase.IN_TASK_COMMENTS_TABLE);
                            }
                            inTaskDefaultComment();
                            Collections.reverse(commentsInfoModelList);
                            improveDataBase.insertIntoInTaskComments(commentsInfoModelList, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession(), strInTaskId);

                        }
                    }
                    improveHelper.dismissProgressDialog();
                    setCommentsFromDB();

                }

                @Override
                public void onFailure(Call<List<CommentsInfoModel>> call, Throwable t) {
                    Log.d(TAG, "onFailureComments: " + t.toString());
                    improveHelper.dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getAllInTaskComments", e);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                backClick();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {

        backClick();
    }

    public void backClick() {
        try {
            AppConstants.IN_TASK_REFRESH_BOOLEAN = true;
            Intent intent = new Intent(context, BottomNavigationActivity.class);
            intent.putExtra("FromNormalTask", "FromNormalTask");
            startActivity(intent);
            finish();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "backClick", e);
        }

    }


    public void disableStatusComment() {
        try {
            ll_taskStatus.setVisibility(View.GONE);
            ll_comment_box.setVisibility(View.GONE);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "disableStatusComment", e);
        }

    }

    public void enableStatusComment() {
        try {
            ll_taskStatus.setVisibility(View.VISIBLE);
            ll_comment_box.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "enableStatusComment", e);
        }

    }

    public void setCommentsFromDB() {
        try {
            commentsInfoModelListDB = improveDataBase.getDataFromInTaskComments(sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession(), strInTaskId);
            if (commentsInfoModelListDB != null && commentsInfoModelListDB.size() > 0) {
                rv_comments.setVisibility(View.VISIBLE);
                commentsAdapter = new CommentsAdapter(context, commentsInfoModelListDB, rv_comments, strInTaskCreatedBy);
                rv_comments.setAdapter(commentsAdapter);
//            rv_comments.smoothScrollToPosition(commentsAdapter.getItemCount() - 1);
//            commentsAdapter.notifyDataSetChanged();

            } else {
                inTaskDefaultComment();
                setCommentsFromDB();
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "setCommentsFromDB", e);
        }
    }

    private void taskCompletedStatusAlertDialog() {
        try {
            new android.app.AlertDialog.Builder(context)
//set icon
//set title
                    .setIcon(getApplicationContext().getResources().getDrawable(R.drawable.icon_bhargo_user_rounded))
                    .setTitle("Are you sure complete task ?")
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what would happen when positive button is clicked
                            sendCommentApi();
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
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "taskCompletedStatusAlertDialog", e);
        }

    }

    public void inTaskDefaultComment() {
        try {
            CommentsInfoModel commentsInfoModel = new CommentsInfoModel();
//        commentsInfoModel.setTaskId(strInTaskId);
            commentsInfoModel.setTaskId("0");
            commentsInfoModel.setTaskStatus("None");
            commentsInfoModel.setTaskStatus("None");
            commentsInfoModel.setTaskComments(strInTaskDesc);
            commentsInfoModel.setUserId(sessionManager.getUserDataFromSession().getUserID());
            commentsInfoModel.setPostId(sessionManager.getPostsFromSession());
            commentsInfoModel.setLocationCode(sessionManager.getUserDetailsFromSession().getLocationCode());
            commentsInfoModel.setDepartmentID(sessionManager.getUserDetailsFromSession().getDepartmentID());
            commentsInfoModel.setDisplayDate(strInTaskDistributionDate);
            commentsInfoModel.setEmpPostId(sessionManager.getPostsFromSession());
            commentsInfoModel.setEmpName(sessionManager.getUserDetailsFromSession().getName());
            commentsInfoModel.setPostName(sessionManager.getUserDetailsFromSession().getRole());
            commentsInfoModel.setDesignationName(sessionManager.getUserDetailsFromSession().getDesignation());
            commentsInfoModel.setDepartmentName(sessionManager.getUserDetailsFromSession().getDepartment());
            commentsInfoModel.setPhoneNo(sessionManager.getUserDetailsFromSession().getPhoneNo());
            commentsInfoModel.setEmpEmail(sessionManager.getUserDetailsFromSession().getEmail());
            commentsInfoModel.setImagePath(null);
            List<CommentsInfoModel> commentsInfoModelList = new ArrayList<>();
            commentsInfoModelList.add(commentsInfoModel);
            improveDataBase.insertIntoInTaskComments(commentsInfoModelList, sessionManager.getOrgIdFromSession(),
                    sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession(), strInTaskId);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "inTaskDefaultComment", e);
        }


    }


    public void mPrepareRefreshData() { // for single user task

//        ImproveHelper.showToastAlert(getActivity(), "InTask refresh in progress");
        List<InTaskDataModel> modelsListData = improveDataBase.getDataFromInTaskTable(sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(), "0", "");

        List<RefreshTaskDetails> refreshTaskDetailsList = new ArrayList<>();
        try {

            for (InTaskDataModel inTaskDataModel : modelsListData) {

                RefreshTaskDetails refreshTaskDetails = new RefreshTaskDetails();
                refreshTaskDetails.setTaskId(inTaskDataModel.getTaskID());
                refreshTaskDetails.setTaskDate(inTaskDataModel.getDistributionDate());
                refreshTaskDetailsList.add(refreshTaskDetails);

            }

            InTaskRefreshSendData inTaskRefreshSendData = new InTaskRefreshSendData();
            inTaskRefreshSendData.setDBNAME(sessionManager.getOrgIdFromSession());
            inTaskRefreshSendData.setUserId(sessionManager.getUserDataFromSession().getUserID());
            inTaskRefreshSendData.setPostId(sessionManager.getPostsFromSession());
            inTaskRefreshSendData.setInOutTaskResponseList(refreshTaskDetailsList);

            Gson gson = new Gson();
            Log.d(TAG, "mPrepareRefreshDataInTask: " + gson.toJson(inTaskRefreshSendData));

            mInTaskRefreshApi(inTaskRefreshSendData);

        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "mPrepareRefreshData", e);
        }
    }

    private void mInTaskRefreshApi(InTaskRefreshSendData data) {
        try {
            Call<List<InTaskDataModel>> responseCall = getServices.getInTaskRefreshData(data);
            responseCall.enqueue(new Callback<List<InTaskDataModel>>() {
                @Override
                public void onResponse(Call<List<InTaskDataModel>> call, Response<List<InTaskDataModel>> response) {
                    if (response.body() != null) {

                        List<InTaskDataModel> inTaskDataModelsList = response.body();

                        if (inTaskDataModelsList != null && inTaskDataModelsList.size() > 0) {

                            for (InTaskDataModel inTaskDataModel : inTaskDataModelsList) {
                                if (inTaskDataModel.getDistributionStatus().equalsIgnoreCase("1")) {
                                    improveDataBase.insertIntoInTaskTable(inTaskDataModelsList, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
                                } else if (inTaskDataModel.getDistributionStatus().equalsIgnoreCase("2") ||
                                        inTaskDataModel.getDistributionStatus().equalsIgnoreCase("0")) {
                                    improveDataBase.UpdateInTaskTable(inTaskDataModel, inTaskDataModel.getTaskID(), sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession());
                                }
                            }

                        }
                    }
                    improveHelper.dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<List<InTaskDataModel>> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "onFailureInTaskRefresh: " + t.toString());

                }
            });
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "mInTaskRefreshApi", e);
        }

    }

    public void getTaskAttemptsByOtherPostApi(Context context, String userId, String orgId, String postId, String taskId, String strSomeOneElse) {
        try {
            Log.d(TAG, "getTaskAttemptsByOtherPostApi: " + "UserId" + userId + " " + "orgId" + orgId + " " + "taskId" + "" + taskId);
            ImproveHelper improveHelper = new ImproveHelper(context);
            improveHelper.showProgressDialog("Please wait...!");
            GetServices getServices = RetrofitUtils.getUserService();
            Call<ResponseBody> responseCall = getServices.getTaskAttempts(userId, orgId, postId, taskId);
            responseCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String json = null;
                    try {
                        json = response.body().string();
                        JSONArray jsonArray = new JSONArray(json.trim());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            AppConstants.TASK_ATTEMPTS_BY = jsonObject.getString("Attempts");
                            Log.d(TAG, "onResponseAttempts: " + jsonObject.getString("Attempts"));
                            if (jsonObject.has("SingleUserInfo")) {

                                String strSingleUserInfo = jsonObject.getString("SingleUserInfo");
//                            Gson gson = new Gson();
//                            List<SingleUserInfo> seSingleUserInfoList = new ArrayList<>();
//                            seSingleUserInfoList.clear();
//                            seSingleUserInfoList = Arrays.asList(gson.fromJson(strSingleUserInfo, SingleUserInfo[].class));

//                            improveDataBase.insertIntoTaskTaskAttemptedByElse(seSingleUserInfoList,true);
                                improveDataBase.UpdateInTaskTableSingleUserInfo(strInTaskId, sessionManager.getOrgIdFromSession(),
                                        sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession(), strSingleUserInfo);
//                            Log.d(TAG, "onResponseSomeOneElse: "+gson.toJson(seSingleUserInfoList));
//                            [{"EmpID":"SRUS00011642","IsSelfComment":"0","PostID":"7_1_3_45","TaskID":"TSK000000000015"}]
                            }

                            disableStatusComment();
                            ImproveHelper.showToastAlert(context, strSomeOneElse);
                            AppConstants.IN_TASK_REFRESH_BOOLEAN = true;
                            Intent intent = new Intent(context, BottomNavigationActivity.class);
                            intent.putExtra("FromNormalTask", "FromNormalTask");
                            startActivity(intent);
                            finish();

                        }

                        improveHelper.dismissProgressDialog();


                    } catch (Exception e) {
                        improveHelper.dismissProgressDialog();
                        Log.d(TAG, "onResponse: " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "onResponseFailureTaskAttempt: " + t.toString());
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getTaskAttemptsByOtherPostApi", e);
        }

    }


}
