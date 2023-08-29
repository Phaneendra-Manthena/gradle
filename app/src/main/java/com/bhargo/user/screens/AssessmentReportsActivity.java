package com.bhargo.user.screens;

import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.AssessmentReportsAdapter;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AssessmentInfoModel;
import com.bhargo.user.pojos.AssessmentReportResponseMain;
import com.bhargo.user.pojos.GetUserAssessmentReportsResponse;
import com.bhargo.user.pojos.GetUserDistributionsResponse;
import com.bhargo.user.pojos.UserAssessmentReportRequest;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssessmentReportsActivity extends BaseActivity {

    private static final String TAG = "AssessmentReportsActivi";
    public RecyclerView rv_reports;
    public Context context;
    CustomTextView ct_alNoRecords, tvAssessment_StartDate, tvAssessment_EndDate, tvAssessment_Duration, tvAssessment_Attempts;
    GetServices getServices;
    SessionManager sessionManager;
    String distributionId;
    ImproveHelper improveHelper;
    CustomButton btn_download_certificate;
    ImproveDataBase improveDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Theme
        setBhargoTheme(this, AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_reports);

        context = AssessmentReportsActivity.this;
        getServices = RetrofitUtils.getUserService();
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);
        improveHelper.showProgressDialog(getResources().getString(R.string.please_wait));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            distributionId = extras.getString("DistributionId");
        }

        initViews(context);

        if (ImproveHelper.isNetworkStatusAvialable(context)) {
            assessmentReportsApi();
        }

    }

    private void assessmentReportsApi() {
        try {
            UserAssessmentReportRequest reportRequest = new UserAssessmentReportRequest();
            reportRequest.setUserId(sessionManager.getUserDataFromSession().getUserID());
            reportRequest.setDistributionId(distributionId);
            reportRequest.setPostId(sessionManager.getPostsFromSession());

            Call<AssessmentReportResponseMain> call = getServices.getUserAssessmentReports(reportRequest);
//            Call<List<GetUserAssessmentReportsResponse>> call = getServices.getUserAssessmentReports(sessionManager.getUserDataFromSession().getUserID(), sessionManager.getOrgIdFromSession(), distributionId, sessionManager.getPostsFromSession());

            call.enqueue(new Callback<AssessmentReportResponseMain>() {
                @Override
                public void onResponse(Call<AssessmentReportResponseMain> call, Response<AssessmentReportResponseMain> response) {
                    if (response.body() != null) {

                        Gson gson = new Gson();
                        AssessmentReportResponseMain responseMain = response.body();
                        if (responseMain != null && responseMain.getStatus() != null && responseMain.getStatus().equalsIgnoreCase("200")) {
                            List<GetUserAssessmentReportsResponse> responseList = responseMain.getUserAssessmentReportDetails();
                            Log.d(TAG, "onResponse: " + gson.toJson(responseList));
                            if (responseList != null && responseList.size() > 0) {

                                if (!responseList.get(0).getDownloadCertificate().equalsIgnoreCase("0")
                                        && responseList.get(0).getDownloadCertificate().startsWith("http://")) {
                                    btn_download_certificate.setVisibility(View.VISIBLE);
                                } else {
                                    btn_download_certificate.setVisibility(View.GONE);
                                }
                                GetUserDistributionsResponse gudsDB = improveDataBase.getELearningData(distributionId, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
//                        int iUserAttemptsLeft = Integer.parseInt(gudsDB.getNoOfUserAttempts()) - Integer.parseInt(responseList.get(0).getNoOfUserAttempts());

                                int iUserAttemptsLeft = Integer.parseInt(responseList.get(0).getNoOfAttempts()) - Integer.parseInt(responseList.get(0).getNoOfUserAttempts());

                                tvAssessment_StartDate.setText(responseList.get(0).getStartDateTime());
                                tvAssessment_EndDate.setText(responseList.get(0).getEndDateTime());
                                tvAssessment_Duration.setText(responseList.get(0).getExamDuration() + " Mins");
                                if (responseList.get(0).getNoOfAttempts().equalsIgnoreCase("0")) {
                                    tvAssessment_Attempts.setText(responseList.get(0).getNoOfUserAttempts());
                                } else {
                                    tvAssessment_Attempts.setText(iUserAttemptsLeft + " left out of " + Integer.parseInt(responseList.get(0).getNoOfAttempts()));
                                }
                                // AssessmentInfo
                                if (responseList.get(0) != null && responseList.get(0).getAssessmentInfo() != null) {
                                    Log.d(TAG, "onResponseGetAssessment: " + responseList.get(0).getAssessmentInfo());
                                    List<AssessmentInfoModel> assessmentInfoList = Arrays.asList(gson.fromJson(responseList.get(0).getAssessmentInfo(), AssessmentInfoModel[].class));
                                    if (assessmentInfoList != null && assessmentInfoList.size() > 0) {
                                        rv_reports.setVisibility(View.VISIBLE);
                                        ct_alNoRecords.setVisibility(View.GONE);
                                        AssessmentReportsAdapter assessmentReportsAdapter = new AssessmentReportsAdapter(context, assessmentInfoList);
                                        rv_reports.setAdapter(assessmentReportsAdapter);
                                        assessmentReportsAdapter.notifyDataSetChanged();
                                    } else {
                                        rv_reports.setVisibility(View.GONE);
                                        ct_alNoRecords.setVisibility(View.VISIBLE);
                                    }
                                }

                                btn_download_certificate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

//                                String[] strsplit = responseList.get(0).getDownloadCertificate().split("/");
//                                String strFileNameExtension = strsplit[strsplit.length - 1];
//                                String strSDCardUrlExists = "/" + AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + responseList.get(0).getTopicName() + "/" + "BhargoCertificate" + "/" + strFileNameExtension;
//                                if (ImproveHelper.isFileExists(strSDCardUrlExists)) {
//                                    ImproveHelper.showToastAlert(context, strFileNameExtension + "\n" + " Already downloaded.");
//                                } else {

                                        if (ImproveHelper.isNetworkStatusAvialable(context)) {
                                            improveHelper.showProgressDialog("Certificate downloading...");
                                            DownloadFileFromURLTask urlTask = new DownloadFileFromURLTask(improveHelper, responseList.get(0).getTopicName());
                                            urlTask.execute(responseList.get(0).getDownloadCertificate());
                                        } else {
                                            improveHelper.snackBarAlertActivities(context, v);
                                        }
//                                }
                                    }
                                });

                            }
                        }else{
                            ImproveHelper.showToast(context,responseMain.getStatus());
                        }
                    }
                    improveHelper.dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<AssessmentReportResponseMain> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.toString());
                    improveHelper.dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "assessmentReportsApi", e);
        }
    }

    public void initViews(Context context) {
        try {
            initializeActionBar();
            title.setText(getResources().getString(R.string.reports));
            enableBackNavigation(true);
            iv_circle_appIcon.setVisibility(View.GONE);
            ib_settings.setVisibility(View.GONE);

            sessionManager = new SessionManager(context);
            getServices = RetrofitUtils.getUserService();

            rv_reports = findViewById(R.id.rv_reports);
            ct_alNoRecords = findViewById(R.id.ct_alNoRecords);
            tvAssessment_StartDate = findViewById(R.id.tvAssessment_StartDate);
            tvAssessment_EndDate = findViewById(R.id.tvAssessment_EndDate);
            tvAssessment_Duration = findViewById(R.id.tvAssessment_Duration);
            tvAssessment_Attempts = findViewById(R.id.tvAssessment_Attempts);
            btn_download_certificate = findViewById(R.id.btn_download_certificate);

            LinearLayoutManager llm = new LinearLayoutManager(context);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rv_reports.setLayoutManager(llm);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "initViews", e);
        }

    }

    public class DownloadFileFromURLTask extends AsyncTask<String, String, String> {

        String strFileName;
        String strFileNameUnderscore;
        String strTopicName;
        File file, outFile;
        ImproveHelper improveHelper;

        public DownloadFileFromURLTask(ImproveHelper improveHelper, String strTopicName) {
            this.improveHelper = improveHelper;
            this.strTopicName = strTopicName;
        }

        /**
         * Downloading file in background thread
         */


        @SuppressLint("SdCardPath")
        @Override
        protected String doInBackground(String... f_url) {
            Log.i(TAG, "do in background" + f_url[0]);
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                String[] strsplit = f_url[0].split("/");
                strFileName = strsplit[strsplit.length - 1];
                strFileNameUnderscore = strFileName.replaceAll(" ", "_");
                String strSDCardUrl = "Improve_User" + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicName + "/" + "BhargoCertificate";
                File cDir = context.getExternalFilesDir(strSDCardUrl);
                File saveFilePath = new File(cDir.getAbsolutePath(), strFileNameUnderscore);
                // Output stream to write file
                OutputStream output = new FileOutputStream(saveFilePath);


/*
                String[] strsplit = f_url[0].split("/");
                strFileName = strsplit[strsplit.length - 1];

                file = new File(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicName + "/" + "BhargoCertificate");
                if (!file.exists()) {
                    file.mkdirs();
                }
//                file = new File(context.getExternalFilesDir(null),"/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats");
                */
/*file = new File(context.getExternalFilesDir(null),"/" +AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicTitle);
                if (!file.exists())
                    file.mkdirs();*//*

                outFile = new File(file, strFileName);
                // Output stream to write file
                OutputStream output = new FileOutputStream(outFile);
*/

                byte[] data = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();


            } catch (Exception e) {
                improveHelper.dismissProgressDialog();
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
//            mProgress.setProgress(Integer.parseInt(progress[0]));
//            progressBar = v.findViewById(R.id.circularProgressbar);
//            progressBar.setProgress(Integer.parseInt(progress[0]));
//            tv_progStatus.setText(progress[0]);
//            textProgress2.setText(progress[0]);
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @SuppressLint("SdCardPath")
        @Override
        protected void onPostExecute(String file_url) {
            Log.i(TAG, "on post excute!: " + file_url);

            // dismiss progressbars after the file was downloaded
//            ll_progressbar = v.findViewById(R.id.ll_progressbar);
//            ll_progressbar.setVisibility(View.GONE);

            // Displaying downloaded image into image view
            // Reading image path from sdcard
//            Log.d(TAG, "doInBackgroundSet: " + file);

         /*   String strSDCardUrlExists = "/" + AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicName + "/" + "BhargoCertificate" + "/" + strFileName;
            if (ImproveHelper.isFileExists(strSDCardUrlExists)) {
                improveHelper.dismissProgressDialog();
                ImproveHelper.showToastAlert(context, strFileName + "\n" + " Already exists");
            }else{
                improveHelper.dismissProgressDialog();
                ImproveHelper.showToastAlert(context, strFileName + "\n" + " Download successfully!");
            }*/


            improveHelper.dismissProgressDialog();
//            ImproveHelper.showToastAlert(context, strFileName + "\n" + " Download successfully!");

            Intent intent = new Intent(context, CertificateDisplayActivity.class);
            intent.putExtra("TopicName", strTopicName);
            intent.putExtra("FileName", strFileName);

            startActivity(intent);

               /*
                try {
                  int pageIndex = 0;
                    openRenderer(getApplicationContext(), strFileNameURl);
                    showPage(pageIndex);

                }catch (Exception e){
                    Log.d(TAG, "onPostExecutePDFException: "+e.toString());
                }*/

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

    private void onBackPressAlertDialog() {
        try {
            ImproveHelper.alertDialogWithRoundShapeMaterialTheme(AssessmentReportsActivity.this, getString(R.string.are_you_sure),
                    getString(R.string.yes), getString(R.string.no), new ImproveHelper.IL() {
                        @Override
                        public void onSuccess() {
                            finish();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "onBackPressAlertDialog", e);
        }

    }


}

