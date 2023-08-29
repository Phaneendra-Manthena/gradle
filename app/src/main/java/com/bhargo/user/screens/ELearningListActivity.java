package com.bhargo.user.screens;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.ELearningListAdapter;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AssessmentReportResponseMain;
import com.bhargo.user.pojos.ELGetUserDistributionsRefreshResponse;
import com.bhargo.user.pojos.EL_GetUserDistributions;
import com.bhargo.user.pojos.FilesInfoModel;
import com.bhargo.user.pojos.GetUserAssessmentReportsResponse;
import com.bhargo.user.pojos.GetUserDistributionsResponse;
import com.bhargo.user.pojos.InsertAssessmentDetailsData;
import com.bhargo.user.pojos.InsertAssessmentDetailsResponse;
import com.bhargo.user.pojos.RefreshELearning;
import com.bhargo.user.pojos.UserAssessmentReportRequest;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.fragments.VideosFragment.GetStringArray;
import static com.bhargo.user.utils.AppConstants.FromNotification;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

public class ELearningListActivity extends BaseActivity {

    private static final String TAG = "ELearningListActivity";
    public String KEY_UPDATE_URL = "https://play.google.com/store/apps/details?id=com.bhargo.bameti_training";
    Context context;
    List<FilesInfoModel> filesInfoModels = new ArrayList<FilesInfoModel>();
    List<GetUserDistributionsResponse> GetUserDistributionsList = new ArrayList<GetUserDistributionsResponse>();
    RecyclerView rv_eLearningList;
    ELearningListAdapter learningListAdapter;
    CustomTextView ct_alNoRecords;
    CustomButton btn_startAssessment;
    String strFromNotification = "", distributionId = null, strExamDuration = "", StartDate = "", StartDisplayTime = "", EndDisplayTime = "", EndDate = "", StartTime = "", EndTime = "";
    GetServices getServices;
    ImproveHelper improveHelper;
    SessionManager sessionManager;
    String hQuestions, mQuestions, lQuestions, tQuestions, Is_Compexcity;
    int NoOfAttempts;
    ImproveDataBase improveDataBase;
    private String strTopicTitle;
    private String isAssessment;
    private int NoOfUserAttempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_learning_list);

        initializeActionBar();
        enableBackNavigation(true);
        context = ELearningListActivity.this;
        sessionManager = new SessionManager(context);
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);

        ib_settings.setVisibility(View.GONE);
        iv_circle_appIcon.setVisibility(View.GONE);
        iv_assessment_reports.setVisibility(View.VISIBLE);

        getServices = RetrofitUtils.getUserService();

        rv_eLearningList = findViewById(R.id.rv_eLearningList);
        ct_alNoRecords = findViewById(R.id.ct_alNoRecords);
        btn_startAssessment = findViewById(R.id.btn_startAssessment);

//        LinearLayoutManager llm = new LinearLayoutManager(context);
//        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rv_eLearningList.setLayoutManager(new GridLayoutManager(this, 2));
//        rv_eLearningList.setLayoutManager(llm);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            distributionId = extras.getString("DistributionId");
            strFromNotification = extras.getString(FromNotification);
            strExamDuration = extras.getString("ExamDuration");
            strTopicTitle = extras.getString("EL_TopicName");
            title.setText(strTopicTitle);
            hQuestions = extras.getString("hQuestions");
            mQuestions = extras.getString("mQuestions");
            lQuestions = extras.getString("lQuestions");
            tQuestions = extras.getString("tQuestions");
            Is_Compexcity = extras.getString("Is_Compexcity");
            isAssessment = extras.getString("Is_Assessment");

            StartDate = extras.getString("StartDate");
            StartDisplayTime = extras.getString("StartDisplayTime");
            EndDate = extras.getString("EndDate");
            EndDisplayTime = extras.getString("EndDisplayTime");
            EndDisplayTime = extras.getString("EndDisplayTime");
            StartTime = extras.getString("StartTime");
            EndTime = extras.getString("EndTime");
            if (isAssessment.equalsIgnoreCase("No")) {
                iv_assessment_reports.setVisibility(View.GONE);
            }

        }

        if (ImproveHelper.isNetworkStatusAvialable(context)) {
            assessmentReportsApi();
            improveHelper.dismissProgressDialog();
        }

        if (strFromNotification != null && !strFromNotification.isEmpty()) {
            refreshGetUsersList(true);
        } else {
            refreshGetUsersList(false);
            GetUserDistributionsResponse gudsDB = improveDataBase.getELearningData(distributionId, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
            if (gudsDB != null) {
                String strCount = gudsDB.getNoOfUserAttempts();
                if(!strCount.equalsIgnoreCase("")) {
                    NoOfUserAttempts = Integer.parseInt(gudsDB.getNoOfUserAttempts());
                    NoOfAttempts = Integer.parseInt(extras.getString("NoOfAttempts"));
                    Log.d(TAG, "StartDisplayDate: " + StartDate + " StartDisplayTime " + StartDisplayTime
                            + " EndDisplayDate " + EndDate + " EndDisplayTime " + EndDisplayTime);

                    if (isAssessment.equalsIgnoreCase("Yes") && NoOfAttempts == 0) {
                        btn_startAssessment.setVisibility(View.VISIBLE);
                    } else if (isAssessment.equalsIgnoreCase("Yes") && NoOfUserAttempts == NoOfAttempts) {
                        btn_startAssessment.setVisibility(View.GONE);
                    } else if (isAssessment.equalsIgnoreCase("No")) {
                        btn_startAssessment.setVisibility(View.GONE);
                    } else {
                        Log.d(TAG, "AttemptsCheck: " + NoOfAttempts + "-" + NoOfUserAttempts);
                    }
                    filesInfoModels = (List<FilesInfoModel>) getIntent().getSerializableExtra("FilesInfoModelList");
                    GetUserDistributionsList = (List<GetUserDistributionsResponse>) getIntent().getSerializableExtra("GetUserDistributionsList");

                    learningListAdapter = new ELearningListAdapter(context, filesInfoModels, distributionId, strTopicTitle);
                    rv_eLearningList.setAdapter(learningListAdapter);
                    learningListAdapter.notifyDataSetChanged();
                }
            }
        }
        btn_startAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkStatusAvialable(ELearningListActivity.this)) {
//                    versionCheckAPi("FromButton");
                    assessmentClick();
                } else {
                    improveHelper.snackBarAlertActivities(context, v);
                }
            }
        });

        iv_assessment_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkStatusAvialable(ELearningListActivity.this)) {
                    Intent intent = new Intent(ELearningListActivity.this, AssessmentReportsActivity.class);
                    intent.putExtra("DistributionId", distributionId);
                    startActivity(intent);
                } else {
                    improveHelper.snackBarAlertActivities(context, v);
                }
            }
        });
    }

    public void alertDialogNoAssessment(String displayMsg) {

        try {
            String msg = displayMsg;
            ImproveHelper.adWithRoundShapeMaterialThemeAssessmentDetails(ELearningListActivity.this, msg,
                    getString(R.string.ok), new ImproveHelper.IL() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onCancel() {

                        }
                    });

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "alertDialogNoAssessment", e);
        }

        /*try {
            String msg = displayMsg;

            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);

            builder.setMessage(msg)
                    .setCancelable(false)
                    .setIcon(getApplicationContext().getResources().getDrawable(R.drawable.icon_bhargo_user))
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });


            //Creating dialog box
            androidx.appcompat.app.AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Assessment");
            alert.setIcon(getApplicationContext().getResources().getDrawable(R.drawable.icon_bhargo_user));
            alert.show();
            ImproveHelper.setTextColorAlertDialog(this,alert);
//            Button buttonNegative = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
//            buttonNegative.setTextColor(ContextCompat.getColor(this, R.color.black));

        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "alertDialogNoAssessment", e);
        }*/

    }

    public void alertDialogStartAssessment(String displayMsg) {
        try {
            String msg = displayMsg;
            ImproveHelper.alertDialogWithRoundShapeMaterialTheme(ELearningListActivity.this, displayMsg,
                    getString(R.string.yes), getString(R.string.no), new ImproveHelper.IL() {
                        @Override
                        public void onSuccess() {
//                            Intent intent = new Intent(ELearningListActivity.this, AssessmentActivity.class);
//                        Intent intent = new Intent(ELearningListActivity.this, SurveyActivity.class);
                            if (ImproveHelper.isNetworkStatusAvialable(context)) {
                                getAssessmentData();
                            } else {
                                Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancel() {

                        }
                    });

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "assessmentAlertDialog", e);
        }
/*
        try {
            String msg = displayMsg;

            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);

            builder.setMessage(msg)
                    .setCancelable(false)
                    .setIcon(getApplicationContext().getResources().getDrawable(R.drawable.icon_bhargo_user))
                    .setNegativeButton(R.string.d_no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

//                        Intent intent = new Intent(ELearningListActivity.this, AssessmentActivity.class);
//                        Intent intent = new Intent(ELearningListActivity.this, SurveyActivity.class);
                            if (ImproveHelper.isNetworkStatusAvialable(context)) {
                                getAssessmentData();
                            } else {
                                Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });


            //Creating dialog box
            androidx.appcompat.app.AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Assessment");
            alert.setIcon(getApplicationContext().getResources().getDrawable(R.drawable.icon_bhargo_user));
            alert.show();
            ImproveHelper.setTextColorAlertDialog(this,alert);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "alertDialogStartAssessment", e);
        }
*/
    }

    private void getAssessmentData() {
        try {
            improveHelper.showProgressDialog(getString(R.string.please_wait));
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
            String strDate = sdfDate.format(new Date());

            InsertAssessmentDetailsData detailsData = new InsertAssessmentDetailsData();
            detailsData.setUserId(sessionManager.getUserDataFromSession().getUserID());
            detailsData.setPostID(sessionManager.getPostsFromSession());
            detailsData.setDBNAME(sessionManager.getOrgIdFromSession());
            detailsData.setDistributionId(distributionId);
            detailsData.setDeviceID(ImproveHelper.getMyDeviceId(ELearningListActivity.this));
            detailsData.setMobileDate(strDate);
            detailsData.sethQuestions(hQuestions);
            detailsData.setmQuestions(mQuestions);
            detailsData.setlQuestions(lQuestions);
            detailsData.settQuestions(tQuestions);
            detailsData.setIsComplexity(Is_Compexcity);
            detailsData.setVersionNo(improveHelper.getAppVersionFromGradle());
            Gson gson = new Gson();
            Log.d(TAG, "getAssessmentData: " + gson.toJson(detailsData));
            Call<InsertAssessmentDetailsResponse> call = getServices.getAssessmentQuestionsData(detailsData);
//        Call<List<QuestionsModel>> call = getServices.getAssessmentQuestionsData(sessionManager.getUserDataFromSession().getUserID(), sessionManager.getOrgIdFromSession(), distributionId, hQuestions, mQuestions, lQuestions, tQuestions, Is_Compexcity);
            call.enqueue(new Callback<InsertAssessmentDetailsResponse>() {
                @Override
                public void onResponse(Call<InsertAssessmentDetailsResponse> call, Response<InsertAssessmentDetailsResponse> response) {
                    improveHelper.dismissProgressDialog();

                    if (response.body() != null) {

                        InsertAssessmentDetailsResponse formDataResponse = response.body();
                        if (formDataResponse != null && !formDataResponse.getStatus().equalsIgnoreCase("100")) {
                            String strNUA =  formDataResponse.getNoOfUserAttempts();
                            if(strNUA.equalsIgnoreCase("")){
                                strNUA = "0";
                            }
                            if (formDataResponse.getStatus().equalsIgnoreCase("400")) {
                                ImproveHelper.showToast(ELearningListActivity.this, formDataResponse.getMessage());
                                Intent intent = new Intent(ELearningListActivity.this, BottomNavigationActivity.class);
                                intent.putExtra("ELearningListDeleteTopic", "1");
                                startActivity(intent);
                                finish();
                            } else if (isAssessment.equalsIgnoreCase("Yes") && NoOfAttempts == 0) {
                                Intent intent = new Intent(context, AssessmentListActivity.class);
                                intent.putExtra("AssessmentQuestionsMain", (Serializable) formDataResponse.getQuestions());
                                intent.putExtra("DistributionId", distributionId);
                                intent.putExtra("ExamDuration", strExamDuration);
                                intent.putExtra("NoOfAttempts", NoOfAttempts);
                                intent.putExtra("AssessmentId", formDataResponse.getAssessmentId());
                                startActivity(intent);
//                            } else if ((formDataResponse.getNoOfUserAttempts() == null) || (Integer.parseInt(formDataResponse.getNoOfUserAttempts()) < NoOfAttempts)) {
                            } else if ((strNUA == null) || (Integer.parseInt(strNUA) < NoOfAttempts)) {
                                Intent intent = new Intent(context, AssessmentListActivity.class);
                                intent.putExtra("AssessmentQuestionsMain", (Serializable) formDataResponse.getQuestions());
                                intent.putExtra("DistributionId", distributionId);
                                intent.putExtra("ExamDuration", strExamDuration);
                                intent.putExtra("NoOfAttempts", NoOfAttempts);
                                intent.putExtra("AssessmentId", formDataResponse.getAssessmentId());
                                startActivity(intent);
//                            finish();
                            } else if (strExamDuration != null || (strExamDuration.equalsIgnoreCase("") || strExamDuration.equalsIgnoreCase("0"))
                                    || (NoOfAttempts == 0)) {
                                Intent intent = new Intent(context, AssessmentListActivity.class);
                                intent.putExtra("AssessmentQuestionsMain", (Serializable) formDataResponse.getQuestions());
                                intent.putExtra("DistributionId", distributionId);
                                intent.putExtra("ExamDuration", "");
                                intent.putExtra("NoOfAttempts", NoOfAttempts);
                                intent.putExtra("AssessmentId", formDataResponse.getAssessmentId());
                                startActivity(intent);
                            } else {
                                btn_startAssessment.setVisibility(View.GONE);
                                ImproveHelper.showToast(ELearningListActivity.this, "Attempts limit reached!");
//                            Intent intent = new Intent(ELearningListActivity.this, BottomNavigationActivity.class);
//                            intent.putExtra("ELearning_NotificationBack", "ELearning_NotificationBack");
//                            startActivity(intent);
//                            finish();
                            }
                        }else{
                            improveHelper.dismissProgressDialog();
                            ImproveHelper.showToast(context,formDataResponse.getMessage());
//                            Log.d(TAG, "onResponseAssessment: " + "No response");
                        }
                    } else {
                        improveHelper.dismissProgressDialog();
//                        Log.d(TAG, "onResponseAssessment: " + "No response");
                    }
                }

                @Override
                public void onFailure(Call<InsertAssessmentDetailsResponse> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
//                    Log.d(TAG, "onFailureAssessment: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            improveHelper.improveException(this, TAG, "getAssessmentData", e);
        }

    }

    /*ELearning Refresh API*/
//    private void mELearningRefreshApiNotification(String strDistributionId, String distributionIdNotification) {
    private void mELearningRefreshApiNotification(RefreshELearning refreshELearning, String distributionIdNotification, boolean isFromNotification) {
        try {
            Call<ELGetUserDistributionsRefreshResponse> listCall = getServices.getUserDistributionsRefresh(refreshELearning);
//                getServices.getUserDistributionsRefresh(sessionManager.getUserDataFromSession().getUserID(), sessionManager.getOrgIdFromSession(), refreshELearningList);
//                getServices.getUserDistributionsRefresh(sessionManager.getUserDataFromSession().getUserID(), sessionManager.getOrgIdFromSession(), strDistributionId);
            listCall.enqueue(new Callback<ELGetUserDistributionsRefreshResponse>() {
                @Override
                public void onResponse(Call<ELGetUserDistributionsRefreshResponse> call, Response<ELGetUserDistributionsRefreshResponse> response) {
                    if (response.body() != null) {
                        improveHelper.showProgressDialog("Please wait loading. . .");
                        List<GetUserDistributionsResponse> gudMainResponseListR = new ArrayList<>();
                        gudMainResponseListR.clear();
                        gudMainResponseListR = response.body().getNewRegistrations();

                        if (gudMainResponseListR != null && gudMainResponseListR.size() > 0) {

//                        improveDataBase.insertIntoELearningTable(gudMainResponseListR, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
                            for (int i = 0; i < gudMainResponseListR.size(); i++) {

                                if (gudMainResponseListR.get(i).getDistributionStatus().equalsIgnoreCase("2")) { //Update
                                    String resDistributionId = gudMainResponseListR.get(i).getDistributionId();
                                    List<GetUserDistributionsResponse> responsesListUpdate = new ArrayList<>();
                                    responsesListUpdate.add(gudMainResponseListR.get(i));
                                    improveDataBase.UpdateELearningTable(responsesListUpdate.get(i), resDistributionId, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
                                } else if (gudMainResponseListR.get(i).getDistributionStatus().equalsIgnoreCase("0")) { //Deleted or Inactive
                                    String resDistributionId = gudMainResponseListR.get(i).getDistributionId();
                                    improveDataBase.UpdateELearningTable(gudMainResponseListR.get(i), resDistributionId, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
                                } else if (gudMainResponseListR.get(i).getDistributionStatus().equalsIgnoreCase("1")) { // New or Active
                                    List<GetUserDistributionsResponse> responseListNew = new ArrayList<>();
                                    responseListNew.add(gudMainResponseListR.get(i));
                                    improveDataBase.insertIntoELearningTable(responseListNew, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
                                }
                            }
//                        ImproveHelper.showToastAlert(ELearningListActivity.this, "E-Learning Updated.");
                        }
                    }
//                if (isFromNotification) {
                    loadAfterRefresh(distributionIdNotification);
//                }
                    improveHelper.dismissProgressDialog();

                }

                @Override
                public void onFailure(Call<ELGetUserDistributionsRefreshResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.toString());
                    improveHelper.dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "mELearningRefreshApiNotification", e);
        }

    }

    private void loadAfterRefresh(String distributionIdNotification) {
        try {
            List<FilesInfoModel> filesInfoModels = new ArrayList<>();
            Gson gson = new Gson();

            GetUserDistributionsResponse distributionsResponseDB = new GetUserDistributionsResponse();
            distributionsResponseDB = improveDataBase.getSingleDataFromE_LearningTable(distributionIdNotification, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());

            filesInfoModels = Arrays.asList(gson.fromJson(distributionsResponseDB.getFilesInfo(), FilesInfoModel[].class));
            strTopicTitle = distributionsResponseDB.getTopicName();
            title.setText(strTopicTitle);
            distributionId = distributionsResponseDB.getDistributionId();
            strExamDuration = distributionsResponseDB.getExamDuration();
            hQuestions = distributionsResponseDB.gethQuestions();
            mQuestions = distributionsResponseDB.getmQuestions();
            lQuestions = distributionsResponseDB.getlQuestions();
            tQuestions = distributionsResponseDB.gettQuestions();
            tQuestions = distributionsResponseDB.gettQuestions();
            Is_Compexcity = distributionsResponseDB.getIs_Compexcity();
            isAssessment = distributionsResponseDB.getIs_Assessment();
            NoOfAttempts = Integer.parseInt(distributionsResponseDB.getNoOfAttempts());
            if(!distributionsResponseDB.getNoOfUserAttempts().equalsIgnoreCase("")) {
                NoOfUserAttempts = Integer.parseInt(distributionsResponseDB.getNoOfUserAttempts());
            }else{
                NoOfUserAttempts = 0;
            }
            StartDate = distributionsResponseDB.getStartDate();
            StartDisplayTime = distributionsResponseDB.getStartDisplayTime();
            EndDate = distributionsResponseDB.getEndDate();
            EndDisplayTime = distributionsResponseDB.getEndDisplayTime();
            StartTime = distributionsResponseDB.getStartTime();
            EndTime = distributionsResponseDB.getEndTime();
            GetUserDistributionsList = improveDataBase.getDataFromE_LearningTable(sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession(),sessionManager.getUserTypeIdsFromSession());
            Log.d(TAG, "StartDisplayDate: " + StartDate + " StartDisplayTime " + StartDisplayTime + " EndDisplayDate " + EndDate + " EndDisplayTime " + EndDisplayTime);
            Log.d(TAG, "loadAfterRefresh: " + NoOfAttempts + "-" + NoOfUserAttempts);

            if (isAssessment.equalsIgnoreCase("Yes") && NoOfAttempts == 0) {
                btn_startAssessment.setVisibility(View.VISIBLE);
            } else if (isAssessment.equalsIgnoreCase("Yes") && NoOfUserAttempts == NoOfAttempts) {
                btn_startAssessment.setVisibility(View.GONE);
            } else if (isAssessment.equalsIgnoreCase("No")) {
                btn_startAssessment.setVisibility(View.GONE);
            }

            learningListAdapter = new ELearningListAdapter(context, filesInfoModels, distributionIdNotification, strTopicTitle);
            rv_eLearningList.setAdapter(learningListAdapter);
            learningListAdapter.notifyDataSetChanged();
            improveHelper.dismissProgressDialog();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "loadAfterRefresh", e);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressAlertDialog();
                //finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {

        onBackPressAlertDialog();

    }


    @Override
    protected void onResume() {
        super.onResume();
//        if (isNetworkStatusAvialable(this)) {
//            versionCheckAPi("");
//        }
    }


    public void alertDialog() {
        try {
            android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(ELearningListActivity.this);
            dialog.setTitle("");
            dialog.setMessage("Please update the application in Google Play Store");
            dialog.setPositiveButton(
                    "YES",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            final String appPackageName = getPackageName();
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }
                    });
            dialog.setNegativeButton(
                    "NO",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(
                                DialogInterface dialog,
                                int which) {
                            dialog.cancel();
//                        finish();
                        }
                    });
            android.app.AlertDialog dialogConfirm = dialog.create();
            // show alert
            dialogConfirm.show();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "alertDialog", e);
        }

        return;
    }

    public void onBackPressAlertDialog() {
        hideKeyboard(ELearningListActivity.this, view);
        try {
            ImproveHelper.alertDialogWithRoundShapeMaterialTheme(ELearningListActivity.this, getString(R.string.are_you_sure),
                    getString(R.string.yes), getString(R.string.no), new ImproveHelper.IL() {
                        @Override
                        public void onSuccess() {
                            Intent intent = new Intent(ELearningListActivity.this, BottomNavigationActivity.class);
                            if (strFromNotification != null && !strFromNotification.isEmpty()) {
                                intent.putExtra("ELearning_NotificationBack", "ELearning_NotificationBack");
                                startActivity(intent);
//                            finish();
                            } else {
                                intent.putExtra("ELearning_NotificationBack", "ELearning_NormalBack");
//                                intent.putExtra("ELearning_NotificationBack", "");
                                startActivity(intent);
                            }
                            finish();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "onBackPressAlertDialog", e);
        }

        /*try {

            AlertDialog alertDialog = new AlertDialog.Builder(this)

                    .setTitle(R.string.are_you_sure)

                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what would happen when positive button is clicked
                            Intent intent = new Intent(ELearningListActivity.this, BottomNavigationActivity.class);
                            if (strFromNotification != null && !strFromNotification.isEmpty()) {
                                intent.putExtra("ELearning_NotificationBack", "ELearning_NotificationBack");
                                startActivity(intent);
//                            finish();
                            } else {
                                intent.putExtra("ELearning_NotificationBack", "ELearning_NormalBack");
                                startActivity(intent);
                            }
                            finish();
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
            ImproveHelper.setTextColorAlertDialog(this,alertDialog);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "onBackPressAlertDialog", e);
        }*/

    }

    public void assessmentClick() {
        try {
            if (!StartDate.isEmpty() && !StartDisplayTime.isEmpty() && !EndDate.isEmpty() && !EndDisplayTime.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
                sdf.setLenient(false);
                String strCurrentDate = sdf.format(new Date());
                Date d1 = sdf.parse(StartDate + " " + StartDisplayTime);
                Date d2 = sdf.parse(strCurrentDate);
                Date d3 = sdf.parse(EndDate + " " + EndDisplayTime);

                if (d2.compareTo(d1) >= 0) {
                    if (d2.compareTo(d3) <= 0) {
                        Log.d("DatesComparison", strCurrentDate + " is in between " + StartDate + " and " + EndDate);
//                    btn_startAssessment.setVisibility(View.VISIBLE);
                        alertDialogStartAssessment("Do you want to start assessment ?");
                    } else {
                        alertDialogNoAssessment("Assessment will start on " + StartDate + " at " + StartTime + " .\n Ends on " + EndDate + " at " + EndTime);
//                            btn_startAssessment.setVisibility(View.GONE);
                        Log.d("DatesComparison", StartDate + " is NOT in between " + StartDate + " and " + EndDate);
                    }
                } else {
//                        btn_startAssessment.setVisibility(View.GONE);
                    alertDialogNoAssessment("Assessment will start on " + StartDate + " at " + StartTime + " .\n Ends on " + EndDate + " at " + EndTime);
                    Log.d("DatesComparison_Check", StartDate + " is NOT in between " + StartDate + " and " + EndDate);
                }

            } else if (!StartDate.isEmpty() && StartDisplayTime.isEmpty() && !EndDate.isEmpty() && EndDisplayTime.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                sdf.setLenient(false);
                String strCurrentDate = sdf.format(new Date());
                Date d1 = sdf.parse(StartDate);
                Date d2 = sdf.parse(strCurrentDate);
                Date d3 = sdf.parse(EndDate);

                if (d2.compareTo(d1) >= 0) {
                    if (d2.compareTo(d3) <= 0) {
                        Log.d("DatesComparison", strCurrentDate + " is in between " + StartDate + " and " + EndDate);
//                    btn_startAssessment.setVisibility(View.VISIBLE);
                        alertDialogStartAssessment("Do you want to start assessment ?");
                    } else {
                        alertDialogNoAssessment("Assessment will start on " + StartDate + " .\n Ends on " + EndDate);
//                            btn_startAssessment.setVisibility(View.GONE);
//                                Log.d("DatesComparison", StartDate + " is NOT in between " + StartDate + " and " + EndDate);
                    }
                } else {
//                        btn_startAssessment.setVisibility(View.GONE);
                    alertDialogNoAssessment("Assessment will start on " + StartDate + " .\n Ends on " + EndDate);
//                            Log.d("DatesComparison_Check", StartDate + " is NOT in between " + StartDate + " and " + EndDate);
                }

            } else if (!StartDate.isEmpty() && StartDisplayTime.isEmpty()) {
                SimpleDateFormat sdfDateNoTime = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                sdfDateNoTime.setLenient(false);
                String strCurrentDate = sdfDateNoTime.format(new Date());
                Date currentDate = sdfDateNoTime.parse(strCurrentDate);
                Date specifiedDate = sdfDateNoTime.parse(StartDate);
                if (currentDate.before(specifiedDate)) {
                    alertDialogNoAssessment("Assessment will start on " + StartDate);
                } else {
                    alertDialogStartAssessment("Do you want to start assessment ?");
                }
            } else if (!EndDate.isEmpty() && EndTime.equalsIgnoreCase("")) {
                SimpleDateFormat sdfDateNoTime = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                sdfDateNoTime.setLenient(false);
                String strCurrentDate = sdfDateNoTime.format(new Date());
                Date currentDate = sdfDateNoTime.parse(strCurrentDate);
                Date specifiedDate = sdfDateNoTime.parse(EndDate);
                if (currentDate.after(specifiedDate)) {
                    alertDialogNoAssessment("Assessment is ended on " + EndDate);
                } else {
                    alertDialogStartAssessment("Do you want to start assessment ?");
                }
            } else if (!StartDate.isEmpty() && (EndDate.equalsIgnoreCase("") || EndDate.isEmpty())) {
                SimpleDateFormat sdfDateNoTime = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                sdfDateNoTime.setLenient(false);
                String strCurrentDate = sdfDateNoTime.format(new Date());
                Date currentDate = sdfDateNoTime.parse(strCurrentDate);
                Date specifiedDate = sdfDateNoTime.parse(StartDate);
                if (currentDate.before(specifiedDate)) {
                    alertDialogNoAssessment("Assessment will start on " + StartDate);
                } else {
                    alertDialogStartAssessment("Do you want to start assessment ?");
                }
            } else if ((StartDate.equalsIgnoreCase("") || StartDisplayTime.isEmpty()) && (!EndDate.isEmpty() && !EndDisplayTime.isEmpty())) {
                SimpleDateFormat sdfDateNoTime = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                sdfDateNoTime.setLenient(false);
                String strCurrentDate = sdfDateNoTime.format(new Date());
                Date currentDate = sdfDateNoTime.parse(strCurrentDate);
                Date specifiedDate = sdfDateNoTime.parse(EndDate);
                if (currentDate.after(specifiedDate)) {
                    alertDialogNoAssessment("Assessment is ended on " + EndDate);
                } else {
                    alertDialogStartAssessment("Do you want to start assessment ?");
                }
            } else if ((StartDate.equalsIgnoreCase("") || StartDate.isEmpty()) && !EndDate.isEmpty()) {
                SimpleDateFormat sdfDateNoTime = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                sdfDateNoTime.setLenient(false);
                String strCurrentDate = sdfDateNoTime.format(new Date());
                Date currentDate = sdfDateNoTime.parse(strCurrentDate);
                Date specifiedDate = sdfDateNoTime.parse(EndDate);
                if (currentDate.after(specifiedDate)) {
                    alertDialogNoAssessment("Assessment is ended on " + EndDate);
                } else {
                    alertDialogStartAssessment("Do you want to start assessment ?");
                }
            } else if ((StartDate.isEmpty() && StartDisplayTime.isEmpty() && EndDate.isEmpty() && EndDisplayTime.isEmpty())
                    || (StartDate.isEmpty() && StartDisplayTime.isEmpty())
                    || (EndDate.isEmpty() && EndDisplayTime.isEmpty())) {
                alertDialogStartAssessment("Do you want to start assessment ?");
            } else if (strExamDuration.equalsIgnoreCase("0")) {
                alertDialogStartAssessment("Do you want to start assessment ?");
            } else if (isAssessment.equalsIgnoreCase("Yes") && NoOfAttempts == 0) {
                alertDialogStartAssessment("Do you want to start assessment ?");
            } else if (isAssessment.equalsIgnoreCase("Yes")) {
                alertDialogStartAssessment("Do you want to start assessment ?");
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "assessmentClick", e);
        }

    }

    public void refreshGetUsersList(boolean isFromNotifications) {
        try {
            String distributionIdsDB = "";

            List<GetUserDistributionsResponse> distributionsResponseList = improveDataBase.getDataFromE_LearningTable(sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession(),sessionManager.getUserTypeIdsFromSession());

            List<EL_GetUserDistributions> responses = new ArrayList<>();

            String separator = "";
            List<String> stringListDistIds = new ArrayList<>();
            for (int i = 0; i < distributionsResponseList.size(); i++) {
                stringListDistIds.add(distributionsResponseList.get(i).getDistributionId());
                Log.d(TAG, "mRefreshApiEL: " + distributionsResponseList.get(i).getDistributionId());
            }
            if (stringListDistIds != null && stringListDistIds.size() > 0) {
                String[] strArrayCheck = GetStringArray(stringListDistIds);
                distributionIdsDB = TextUtils.join(",", strArrayCheck);
                separator = ",";
            }
            Log.d(TAG, "onCreateEL_Notification: " + distributionIdsDB);

            for (int i = 0; i < distributionsResponseList.size(); i++) {
                EL_GetUserDistributions distributions = new EL_GetUserDistributions();
                distributions.setDistributionId(distributionsResponseList.get(i).getDistributionId());
                distributions.setDistributionDate(distributionsResponseList.get(i).getTaskUpdationDate());
                responses.add(distributions);
            }

            RefreshELearning refreshELearning = new RefreshELearning();
            refreshELearning.setUserId(sessionManager.getUserDataFromSession().getUserID());
            refreshELearning.setPostId(sessionManager.getPostsFromSession());
            refreshELearning.setGetUserDistributionsResponseList(responses);
            Gson gson = new Gson();
            String jsonObjectRefresh = gson.toJson(refreshELearning);
            Log.d(TAG, "RefreshELearning: " + jsonObjectRefresh);
            mELearningRefreshApiNotification(refreshELearning, distributionId, isFromNotifications);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "refreshGetUsersList", e);
        }

    }

    private void assessmentReportsApi() {
        try {
            improveHelper.showProgressDialog("Please wait...");
            UserAssessmentReportRequest reportRequest = new UserAssessmentReportRequest();
            reportRequest.setUserId(sessionManager.getUserDataFromSession().getUserID());
            reportRequest.setDistributionId(distributionId);
            reportRequest.setPostId(sessionManager.getPostsFromSession());

            Log.d(TAG, "assessmentReportsApi: " + sessionManager.getUserDataFromSession().getUserID() + "," +
                    sessionManager.getOrgIdFromSession() + "," + distributionId + "," + sessionManager.getPostsFromSession());
            Call<AssessmentReportResponseMain> call = getServices.getUserAssessmentReports(reportRequest);
//            Call<List<GetUserAssessmentReportsResponse>> call = getServices.getUserAssessmentReports(sessionManager.getUserDataFromSession().getUserID(), sessionManager.getOrgIdFromSession(), distributionId, sessionManager.getPostsFromSession());

            call.enqueue(new Callback<AssessmentReportResponseMain>() {
                @Override
                public void onResponse(Call<AssessmentReportResponseMain> call, Response<AssessmentReportResponseMain> response) {
                    Log.d(TAG, "assessmentReportsApi1: " + "Check1");
                    if (response.body() != null) {
                        Log.d(TAG, "assessmentReportsApi2: " + "Check2");
                        Gson gson = new Gson();
                        AssessmentReportResponseMain responseMain = response.body();
                        if (responseMain.getStatus() != null && responseMain.getStatus().equalsIgnoreCase("200")) {
                            List<GetUserAssessmentReportsResponse> responseList = responseMain.getUserAssessmentReportDetails();
                            Log.d(TAG, "assessmentReportsResponse: " + gson.toJson(responseList));
                            if (responseList != null && responseList.size() > 0) {
                                Log.d(TAG, "assessmentReportsApi3: " + "Check3");
                                NoOfUserAttempts = Integer.parseInt(responseList.get(0).getNoOfUserAttempts());
                                NoOfAttempts = Integer.parseInt(responseList.get(0).getNoOfAttempts());
                                Log.d(TAG, "NoOfUserAttemptsReport: " + responseList.get(0).getNoOfUserAttempts());
                                Log.d(TAG, "NoOfAttemptsReport: " + responseList.get(0).getNoOfAttempts());
                                if (responseList.get(0).getNoOfAttempts().equalsIgnoreCase("0")) {
                                    btn_startAssessment.setVisibility(View.VISIBLE);
                                } else if (responseList.get(0).getNoOfUserAttempts().equalsIgnoreCase(responseList.get(0).getNoOfAttempts())) {
                                    btn_startAssessment.setVisibility(View.GONE);
                                }
                            }
                        }else{
                            ImproveHelper.showToast(context,responseMain.getStatus());
                        }
                    }
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "assessmentReportsApi4: " + "Check4");
                }

                @Override
                public void onFailure(Call<AssessmentReportResponseMain> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.toString());
                    Log.d(TAG, "assessmentReportsApi5: " + "Check5");
                    improveHelper.dismissProgressDialog();
                }
            });/*            Call<List<GetUserAssessmentReportsResponse>> call = getServices.getUserAssessmentReports(reportRequest);
//            Call<List<GetUserAssessmentReportsResponse>> call = getServices.getUserAssessmentReports(sessionManager.getUserDataFromSession().getUserID(), sessionManager.getOrgIdFromSession(), distributionId, sessionManager.getPostsFromSession());

            call.enqueue(new Callback<List<GetUserAssessmentReportsResponse>>() {
                @Override
                public void onResponse(Call<List<GetUserAssessmentReportsResponse>> call, Response<List<GetUserAssessmentReportsResponse>> response) {
                    Log.d(TAG, "assessmentReportsApi1: " + "Check1");
                    if (response.body() != null) {
                        Log.d(TAG, "assessmentReportsApi2: " + "Check2");
                        Gson gson = new Gson();
                        List<GetUserAssessmentReportsResponse> responseList = response.body();
                        Log.d(TAG, "assessmentReportsResponse: " + gson.toJson(responseList));
                        if (responseList != null && responseList.size() > 0) {
                            Log.d(TAG, "assessmentReportsApi3: " + "Check3");
                            NoOfUserAttempts = Integer.parseInt(responseList.get(0).getNoOfUserAttempts());
                            NoOfAttempts = Integer.parseInt(responseList.get(0).getNoOfAttempts());
                            Log.d(TAG, "NoOfUserAttemptsReport: " + responseList.get(0).getNoOfUserAttempts());
                            Log.d(TAG, "NoOfAttemptsReport: " + responseList.get(0).getNoOfAttempts());
                            if (responseList.get(0).getNoOfAttempts().equalsIgnoreCase("0")) {
                                btn_startAssessment.setVisibility(View.VISIBLE);
                            } else if (responseList.get(0).getNoOfUserAttempts().equalsIgnoreCase(responseList.get(0).getNoOfAttempts())) {
                                btn_startAssessment.setVisibility(View.GONE);
                            }
                        }
                    }
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "assessmentReportsApi4: " + "Check4");
                }

                @Override
                public void onFailure(Call<List<GetUserAssessmentReportsResponse>> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.toString());
                    Log.d(TAG, "assessmentReportsApi5: " + "Check5");
                    improveHelper.dismissProgressDialog();
                }
            });*/
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "assessmentReportsApi", e);
        }
    }
}
