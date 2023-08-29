package com.bhargo.user.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.CommentsAdapter;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.CommentsInfoModel;
import com.bhargo.user.pojos.CommentsResponse;
import com.bhargo.user.pojos.FaildCommentDetails;
import com.bhargo.user.pojos.InsertComments;
import com.bhargo.user.pojos.TaskCommentDetails;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.utils.ImproveDataBase.OUT_TASK_COMMENTS_TABLE;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;


public class OutTaskCommentsActivity extends BaseActivity {

    private static final String TAG = "OutTaskCommentsActivity";
    private final String strRbStatusId = null;
    Context context;
    SessionManager sessionManager;
    GetServices getServices;
    RecyclerView rv_comments;
    CustomEditText cet_Comment;
    ImageButton btn_send_comment;
    ImproveHelper improveHelper;
    ImproveDataBase improveDataBase;
    private String strOutTaskCreatedBy, strOutTaskId = null, strOutTaskName, strOutTaskDesc, strOutTaskStatus, strOutTaskDistributionDate;
    private LinearLayoutManager llCmts;
    private List<CommentsInfoModel> commentsInfoModelListDB;
    private CustomTextView tvTasks_name, tvTasksStatus, tvTasksDesc, tvTasksDate;
    private CommentsAdapter commentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_task_comments);

        context = this;
        sessionManager = new SessionManager(context);
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);
        getServices = RetrofitUtils.getUserService();
        initializeActionBar();
        enableBackNavigation(true);
        iv_circle_appIcon.setVisibility(View.GONE);

        cet_Comment = findViewById(R.id.cet_Comment);
        rv_comments = findViewById(R.id.rv_comments);
        cet_Comment = findViewById(R.id.cet_Comment);
        btn_send_comment = findViewById(R.id.btn_send_comment);
        tvTasks_name = findViewById(R.id.tvTasks_name);
        tvTasksStatus = findViewById(R.id.tvTasksStatus);
        tvTasksDesc = findViewById(R.id.tvTasksDesc);
        tvTasksDate = findViewById(R.id.tvTasksDate);

        /*Comments*/
        llCmts = new LinearLayoutManager(context);
        llCmts.setOrientation(LinearLayoutManager.VERTICAL);
        rv_comments.setLayoutManager(llCmts);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            strOutTaskCreatedBy = extras.getString("out_task_created_by");
            strOutTaskId = extras.getString("out_task_id");
            strOutTaskName = extras.getString("out_task_name");
            strOutTaskDesc = extras.getString("out_task_desc");
            strOutTaskStatus = extras.getString("out_task_status");
            strOutTaskDistributionDate = extras.getString("out_task_distributed_date");

            title.setText(strOutTaskName);
            tvTasks_name.setText(strOutTaskName);
            tvTasksDesc.setText(strOutTaskDesc);
            tvTasksStatus.setText(strOutTaskStatus);
            tvTasksDate.setText(strOutTaskDistributionDate);

        }

        if (isNetworkStatusAvialable(context)) {
            getAllOutTaskComments();
        } else {
            if (improveDataBase.doesTableExist(OUT_TASK_COMMENTS_TABLE)) {

                setCommentsFromDB();

            }
        }

        btn_send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommentApi();
            }
        });

    }


    public void getAllOutTaskComments() {
        try {
            improveHelper.showProgressDialog("Please wait...");

            Call<List<CommentsInfoModel>> listCall = getServices.getAllCommentsDetails(sessionManager.getOrgIdFromSession(),
                    sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession(), strOutTaskId);
            listCall.enqueue(new Callback<List<CommentsInfoModel>>() {
                @Override
                public void onResponse(Call<List<CommentsInfoModel>> call, Response<List<CommentsInfoModel>> response) {

                    if (response.body() != null) {
                        List<CommentsInfoModel> commentsInfoModelList = response.body();
                        if (commentsInfoModelList != null && commentsInfoModelList.size() > 0) {
                            if (improveDataBase.doesTableExist(OUT_TASK_COMMENTS_TABLE)) {
                                improveDataBase.deleteAllRowsInTable(OUT_TASK_COMMENTS_TABLE);
                            }
                            outTaskDefaultComment();
                            Collections.reverse(commentsInfoModelList);
                            improveDataBase.insertIntoOutTaskComments(commentsInfoModelList, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession(), strOutTaskId);
                        }
                    }
                    improveHelper.dismissProgressDialog();

                    setCommentsFromDB();

                }

                @Override
                public void onFailure(Call<List<CommentsInfoModel>> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "onFailureComments: " + t.toString());
                }
            });

        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getAllOutTaskComments", e);
        }

    }


    public void sendCommentApi() {
        try {
            improveHelper.showProgressDialog(context.getString(R.string.please_wait));

            TaskCommentDetails data = new TaskCommentDetails();
            data.setOrgId(sessionManager.getOrgIdFromSession());
            data.setTaskId(strOutTaskId);
            data.setTaskComments(cet_Comment.getText().toString());
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

            List<TaskCommentDetails> taskCommentDetailsList = new ArrayList<>();
            taskCommentDetailsList.add(data);
            InsertComments insertComments = new InsertComments();
            insertComments.setTaskCommentDetails(taskCommentDetailsList);
            if (!isNetworkStatusAvialable(context) && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("0")) {
                improveDataBase.insertIntoOutTaskSendCommentOffline(taskCommentDetailsList, "NotUploaded");
                improveHelper.dismissProgressDialog();
                cet_Comment.setText("");
                ImproveHelper.showToastAlert(context, "Comment saved successfully.");
            } else {
                Call<CommentsResponse> responseCall = getServices.insertCommentsDetails(insertComments);
                responseCall.enqueue(new Callback<CommentsResponse>() {
                    @Override
                    public void onResponse(Call<CommentsResponse> call, Response<CommentsResponse> response) {

                        if (response.body() != null) {

                            CommentsResponse commentsResponse = response.body();
                            if (commentsResponse != null && commentsResponse.getStatus().equalsIgnoreCase("200")) {
                                improveHelper.dismissProgressDialog();
                                List<FaildCommentDetails> faildCommentDetailsList = commentsResponse.getFaildCommentDetails();
                                if (faildCommentDetailsList != null && faildCommentDetailsList.size() > 0) {
                                    for (int i = 0; i < faildCommentDetailsList.size(); i++) {
                                        Log.d(TAG, "onResponse: " + faildCommentDetailsList.get(i).getTaskComments());
                                    }
                                }
                                Log.d(TAG, "onResponseComments: " + commentsResponse.getSuccessRecordCount());
                                Log.d(TAG, "onResponseComments: " + commentsResponse.getStatus());
                                Log.d(TAG, "onResponseComments: " + commentsResponse.getMessage());

                                ImproveHelper.showToastAlert(context, commentsResponse.getMessage());
                                cet_Comment.setText("");

                                getAllOutTaskComments();

                            } else if (commentsResponse != null && commentsResponse.getStatus().equalsIgnoreCase("300")) {
                                ImproveHelper.showToastAlert(context, commentsResponse.getMessage());
                                improveHelper.dismissProgressDialog();
                            } else {
                                improveHelper.dismissProgressDialog();
                            }
                        } else {
                            improveHelper.dismissProgressDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<CommentsResponse> call, Throwable t) {
                        improveHelper.dismissProgressDialog();
                        ImproveHelper.showToast(context, "Please check internet connection");
                        Log.d(TAG, "onFailureTaskAppDetails: " + t.toString());
                    }
                });
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "sendCommentApi", e);
        }

    }

    public void setCommentsFromDB() {
        try {
            commentsInfoModelListDB = improveDataBase.getDataFromOutTaskComments(sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession(), strOutTaskId);
            if (commentsInfoModelListDB != null && commentsInfoModelListDB.size() > 0) {
                rv_comments.setVisibility(View.VISIBLE);
                commentsAdapter = new CommentsAdapter(context, commentsInfoModelListDB, rv_comments, strOutTaskCreatedBy);
                rv_comments.setAdapter(commentsAdapter);
//            rv_comments.smoothScrollToPosition(commentsAdapter.getItemCount() - 1);
//            commentsAdapter.notifyDataSetChanged();

            } else {
                outTaskDefaultComment();
                setCommentsFromDB();
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "setCommentsFromDB", e);
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
            intent.putExtra("FromTaskDeployment", "FromTaskDeployment");
            startActivity(intent);
            finish();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "backClick", e);
        }

    }

    public void outTaskDefaultComment() {
        try {
            CommentsInfoModel commentsInfoModel = new CommentsInfoModel();
//        commentsInfoModel.setTaskId(strOutTaskId);
            commentsInfoModel.setTaskId("0");
            commentsInfoModel.setTaskStatus("None");
            commentsInfoModel.setTaskComments(strOutTaskDesc);
            commentsInfoModel.setUserId(sessionManager.getUserDataFromSession().getUserID());
            commentsInfoModel.setPostId(sessionManager.getPostsFromSession());
            commentsInfoModel.setLocationCode(sessionManager.getUserDetailsFromSession().getLocationCode());
            commentsInfoModel.setDepartmentID(sessionManager.getUserDetailsFromSession().getDepartmentID());
            commentsInfoModel.setDisplayDate(strOutTaskDistributionDate);
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
            improveDataBase.insertIntoOutTaskComments(commentsInfoModelList, sessionManager.getOrgIdFromSession(),
                    sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession(), strOutTaskId);
//        setCommentsFromDB();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "outTaskDefaultComment", e);
        }
    }
}