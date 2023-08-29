package com.bhargo.user.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Java_Beans.DataManagementOptions;
import com.bhargo.user.Java_Beans.GlobalObjects;
import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppDetails;
import com.bhargo.user.pojos.AppDetailsResponse;
import com.bhargo.user.pojos.AppsInfoModel;
import com.bhargo.user.pojos.ContentInfoModel;
import com.bhargo.user.pojos.FormControls;
import com.bhargo.user.pojos.TaskAppDetailsData;
import com.bhargo.user.pojos.TaskTransDataModel;
import com.bhargo.user.screens.ELearningViewActivity;
import com.bhargo.user.screens.ViewDataActivity;
import com.bhargo.user.screens.WebLinkViewActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.DataCollection;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadAdapter extends RecyclerView.Adapter<CadAdapter.CadViewHolder> {

    private static final String TAG = "CadAdapter";
    SessionManager sessionManager;
    Context context;
    List<AppsInfoModel> appsInfoModelList;
    List<ContentInfoModel> contentInfoModelsList;

    //    List<TaskAppDataModelAC> taskAppDataModelACS;
//    List<TaskContentDataModelAC> taskContentDataModelAC;
    List<TaskTransDataModel> TaskTransData;
    ImproveHelper improveHelper;
    Dialog dialog;
    String strTypeOfList;
    GetServices getServices;
    ImproveDataBase improveDataBase;
    String DistrubutionID;

    public CadAdapter(Context context, List<TaskTransDataModel> TaskTransData, Dialog dialog, String DistrubutionID) {
        this.context = context;
        this.TaskTransData = TaskTransData;
        this.DistrubutionID = DistrubutionID;
        this.dialog = dialog;
        getServices = RetrofitUtils.getUserService();
        sessionManager = new SessionManager(context);
        strTypeOfList = "";
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);
    }

    public CadAdapter(Context context, Dialog dialog, List<AppsInfoModel> appsInfoModelList, String DistrubutionID) {
        this.context = context;
        this.appsInfoModelList = appsInfoModelList;
        this.DistrubutionID = DistrubutionID;
        this.dialog = dialog;
        sessionManager = new SessionManager(context);
        getServices = RetrofitUtils.getUserService();
        strTypeOfList = "Apps";
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);
    }

    public CadAdapter(List<ContentInfoModel> contentInfoModelsList, Context context, Dialog dialog, String DistrubutionID) {
        this.context = context;
        this.contentInfoModelsList = contentInfoModelsList;
        this.DistrubutionID = DistrubutionID;
        this.dialog = dialog;
        sessionManager = new SessionManager(context);
        getServices = RetrofitUtils.getUserService();
        strTypeOfList = "Content";
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);
    }

    @NonNull
    @Override
    public CadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        CadViewHolder vh = null;
        if (strTypeOfList.equalsIgnoreCase("Content")) {
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cad_rv_item_content, parent, false);
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elearning_list_item, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cad_rv_item, parent, false);
//            vh = new CadAdapter.CadViewHolder(v); // pass the view to View Holder
        }
        vh = new CadAdapter.CadViewHolder(v, strTypeOfList);
        // set the view's size, margins, paddings and layout_sample_app parameters

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CadAdapter.CadViewHolder holder, int position) {

        if (appsInfoModelList != null && appsInfoModelList.size() > 0) {
            Glide.with(context).load(appsInfoModelList.get(position).getACImagePath()).into(holder.iv_circle_tasks);
            holder.tvPersonName.setText(appsInfoModelList.get(position).getACName());
            holder.tvTasksId.setText(appsInfoModelList.get(position).getACNameACDes());
        } else if (contentInfoModelsList != null && contentInfoModelsList.size() > 0) {
//            Glide.with(context).load(taskContentDataModelAC.get(position).getACImagePath()).into(holder.iv_circle_tasks);
//            holder.iv_circle_tasks.setImageDrawable(context.getDrawable(R.drawable.icon_study));
//            holder.tvPersonName.setText(taskContentDataModelAC.get(position).getACName());
//            holder.tvTasksId.setText(taskContentDataModelAC.get(position).getACDes());
            holder.ll_elImage.setVisibility(View.VISIBLE);
            String[] strsplit = contentInfoModelsList.get(position).getDownloadUrl().split("/");
            String strFileNameExtension = strsplit[strsplit.length - 1];

            File cDir = context.getExternalFilesDir(AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + contentInfoModelsList.get(position).getACName());
            File appSpecificExternalDir = new File(cDir.getAbsolutePath(), strFileNameExtension);
            Log.d(TAG, "FileExitsAdapter: " + appSpecificExternalDir);
            if (appSpecificExternalDir.exists()) {
                holder.ll_progressbar.setVisibility(View.GONE);
            }

            switch (contentInfoModelsList.get(position).getACImagePath()) {

                case "jpg":
//                Glide.with(context).load(gudModel.getDownloadUrl()).dontAnimate().into(holder.iv_elImage);
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.jpg));
                    break;
                case "png":
//                Glide.with(context).load(gudModel.getDownloadUrl()).dontAnimate().into(holder.iv_elImage);
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.png));
                    break;
                case "mp3":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.mp3));
                    break;
                case "3gp":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.threegp));
                    break;
                case "mp4":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.mp4));
                    break;
                case "mov":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.mov));
                    break;
                case "avi":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.avi));
                    break;
                case "ogg":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.ogg));
                    break;
                case "webm":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.webm));
                    break;
                case "bmp":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.bmp));
                    break;
                case "gif":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.gif));
                    break;
                case "mpeg":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.mpeg));
                    break;
                case "ogv":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.ogv));
                    break;
                case "wmv":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.wmv));
                    break;
                case "youtube":
                    holder.ll_progressbar.setVisibility(View.GONE);
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.youtube));
                    break;
                case "pdf":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.pdf));
                    break;
                case "ppt":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.ppt));
                    break;
                case "pptx":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.pptx));
                    break;
                case "docx":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.docx));
                    break;
                case "txt":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.txt));
                    break;
                case "html":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.html));
                    break;
                case "js":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.js));
                    break;
                case "css":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.css));
                    break;
                case "zip":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.zip));
                    break;
                case "exe":
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.exe));
                    break;
                default:
                    holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.file));

            }

            holder.ctv_elName.setText(contentInfoModelsList.get(position).getACName());

        } else {
            holder.iv_circle_tasks.setImageDrawable(context.getDrawable(R.drawable.icon_individual));
            holder.tvPersonName.setText(TaskTransData.get(position).getName());
            holder.tvTasksId.setText(TaskTransData.get(position).getTaskID());
        }

    }

    @Override
    public int getItemCount() {
        if (appsInfoModelList != null && appsInfoModelList.size() > 0) {
            return appsInfoModelList.size();
        } else if (contentInfoModelsList != null && contentInfoModelsList.size() > 0) {
            return contentInfoModelsList.size();
        } else {
            return TaskTransData.size();
        }
    }


    private void mGetAppDetailsApi(String appName) {
        improveHelper.showProgressDialog(context.getString(R.string.please_wait));

        TaskAppDetailsData data = new TaskAppDetailsData();
        data.setOrgId(sessionManager.getOrgIdFromSession());
        data.setUserId(sessionManager.getUserDataFromSession().getUserID());
        data.setPageName(appName);
        Gson gson = new Gson();
        Log.d(TAG, "mGetAppDetailsApiParams: " + gson.toJson(data));
        Call<AppDetailsResponse> responseCall = getServices.getAppDetails(data);
        responseCall.enqueue(new Callback<AppDetailsResponse>() {
            @Override
            public void onResponse(Call<AppDetailsResponse> call, Response<AppDetailsResponse> response) {

                if (response.body() != null) {

                    List<AppDetails> taskAppDetails = response.body().getAppDetails();
                    if (taskAppDetails != null && taskAppDetails.size() > 0) {
//                        if(improveDataBase.doesTableExist(APPS_LIST_TABLE_IN_TASK) && !improveDataBase.isTableEmpty(APPS_LIST_TABLE_IN_TASK)){
//                            improveDataBase.deleteAllRowsInTable(APPS_LIST_TABLE_IN_TASK);
//                        }
                        improveDataBase.insertIntoInTaskAppsListTable(taskAppDetails, sessionManager.getOrgIdFromSession(),
                                sessionManager.getUserDataFromSession().getUserID());
                        AppDetails appDetails = improveDataBase.getAppDetailsInTask(sessionManager.getOrgIdFromSession(), appName,
                                sessionManager.getUserDataFromSession().getUserID());

                        openApp(appDetails);

//                        List<AppDetails> taskAppDetailsListDB = improveDataBase.getDataFromInTaskAppsListTable(sessionManager.getOrgIdFromSession(),
//                                sessionManager.getUserDataFromSession().getUserID(),sessionManager.getPostsFromSession());
//                        if (taskAppDetailsListDB != null && taskAppDetailsListDB.size() > 0) {
//                            for (int i = 0; i < taskAppDetailsListDB.size(); i++) {
//                                if (taskAppDetailsListDB.get(i).getAppName().trim().equalsIgnoreCase(appName.trim())) {
//                                    openApp(taskAppDetailsListDB.get(i));
//                                    break;
//                                }
//                            }
//                        }
                    } else {
                        ImproveHelper.showToastAlert(context, "App details not found!");
                    }
                }
            }

            @Override
            public void onFailure(Call<AppDetailsResponse> call, Throwable t) {
                improveHelper.dismissProgressDialog();
                Log.d(TAG, "onFailureTaskAppDetails: " + t.toString());
            }
        });
        improveHelper.dismissProgressDialog();
    }

    public void openApp(AppDetails appDetails) {
//        XMLHelper xmlHelper = new XMLHelper();
        //nk step
        System.out.println("=======Step6===============");
//        DataCollectionObject dataCollectionObject = xmlHelper.XML_To_DataCollectionObject(appDetails.getDesignFormat());
//        AppConstants.CurrentAppObject = dataCollectionObject;
      DataManagementOptions dataManagementOptions = DataCollection.getAdvanceManagement( appDetails.getDesignFormat());
        FormControls formControls = DataCollection.getControlList(appDetails.getDesignFormat());
        if (appDetails != null) {
            AppConstants.IS_FROM_IN_TASK_DETAILS_APPS_LIST = true;
            if (appDetails.getAppType().equalsIgnoreCase(AppConstants.WEB_Link)) {
                Intent intent = new Intent(context, WebLinkViewActivity.class);
                intent.putExtra("s_app_design", appDetails.getDesignFormat());
                intent.putExtra("s_app_name", appDetails.getAppName());
                intent.putExtra("FromNormalTask", "FromNormalTask");
                context.startActivity(intent);
            } else {
                if (AppConstants.GlobalObjects == null) {
                    AppConstants.GlobalObjects = new GlobalObjects();
                }
                Intent intent = new Intent(context, ViewDataActivity.class);
                intent.putExtra("formControls", formControls);
                intent.putExtra("dataManagementOptions", dataManagementOptions);
                intent.putExtra("s_app_details", appDetails);
                intent.putExtra("s_app_design", appDetails.getDesignFormat());
                intent.putExtra("s_app_version", appDetails.getAppVersion());
                intent.putExtra("s_app_type", appDetails.getAppType());
                intent.putExtra("s_org_id", sessionManager.getOrgIdFromSession());
                intent.putExtra("s_app_name", appDetails.getAppName());
                intent.putExtra("s_created_by", appDetails.getCreatedBy());
                intent.putExtra("s_user_id", sessionManager.getUserDataFromSession().getUserID());
                intent.putExtra("s_distribution_id", DistrubutionID);
                intent.putExtra("From_InTaskDetails", "From_InTaskDetails");
                intent.putExtra("app_edit", "New");
                context.startActivity(intent);
//                    intent.putExtra("s_user_location_Structure", sessionManager.getUserDetailsFromSession().getLocationStructure());
            }
        }
    }

    public void startAssessmentTimeForTopic() {
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        String setAssessmentSessionStart = sdf.format(new Date());
        PrefManger.putSharedPreferencesString(context, "AssessmentSessionStart", setAssessmentSessionStart);
        Log.d(TAG, "AssessmentSessionStart: " + setAssessmentSessionStart);
    }

    public class CadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_circle_tasks, iv_elImage;
        CustomTextView tvPersonName, tvTasksId, ctv_elName;
        RelativeLayout ll_elImage;
        RelativeLayout ll_progressbar;
        ProgressBar mProgress;

        //        public CadViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            iv_circle_tasks = itemView.findViewById(R.id.iv_circle_tasks);
//            tvPersonName = itemView.findViewById(R.id.tvPersonName);
//            tvTasksId = itemView.findViewById(R.id.tvTasksId);
//
//            itemView.setOnClickListener(this);
//
//        }

        public CadViewHolder(@NonNull View itemView, String typeOfList) {
            super(itemView);

            if (typeOfList.equalsIgnoreCase("Content")) {

                ll_elImage = itemView.findViewById(R.id.ll_elImage);
                ll_progressbar = itemView.findViewById(R.id.ll_progressbar);
                iv_elImage = itemView.findViewById(R.id.iv_elImage);
                ctv_elName = itemView.findViewById(R.id.ctv_elName);
                mProgress = itemView.findViewById(R.id.circularProgressbar);

            } else {

                iv_circle_tasks = itemView.findViewById(R.id.iv_circle_tasks);
                tvPersonName = itemView.findViewById(R.id.tvPersonName);
                tvTasksId = itemView.findViewById(R.id.tvTasksId);
                tvTasksId.setVisibility(View.GONE);

            }

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (strTypeOfList.equalsIgnoreCase("Apps")) {

                AppDetails appDetailsInTask = improveDataBase.getAppDetailsInTask(sessionManager.getOrgIdFromSession(), appsInfoModelList.get(getAdapterPosition()).getACName(), sessionManager.getUserDataFromSession().getUserID());
                if (appDetailsInTask != null && appDetailsInTask.getDesignFormat() != null && !appDetailsInTask.getDesignFormat().equalsIgnoreCase("")) {
                    openApp(appDetailsInTask);
                } else {
                    mGetAppDetailsApi(appsInfoModelList.get(getAdapterPosition()).getACName());
                }
                dialog.dismiss();
            } else if (strTypeOfList.equalsIgnoreCase("Content")) {

                startAssessmentTimeForTopic();

                if (contentInfoModelsList.get(getAdapterPosition()).getACImagePath().equalsIgnoreCase("youtube")) {
                    Intent intent = new Intent(context, ELearningViewActivity.class);
                    intent.putExtra("ELearning_Type", contentInfoModelsList.get(getAdapterPosition()).getACImagePath());
                    intent.putExtra("FileName", contentInfoModelsList.get(getAdapterPosition()).getACName());
                    intent.putExtra("ELearning_URL", contentInfoModelsList.get(getAdapterPosition()).getDownloadUrl());
                    intent.putExtra("ELearning_FilesInfoPosition", contentInfoModelsList.get(getAdapterPosition()));
                    intent.putExtra("FileNameExt", contentInfoModelsList.get(getAdapterPosition()).getACImagePath());
                    intent.putExtra("ELearning_FilesInfoList", (Serializable) contentInfoModelsList);
                    intent.putExtra("TopicName", contentInfoModelsList.get(getAdapterPosition()).getACName());
                    intent.putExtra("distributionIdView", DistrubutionID);
                    intent.putExtra("ParentID", contentInfoModelsList.get(getAdapterPosition()).getParent_Id());
                    intent.putExtra("Selected_Node_Id", contentInfoModelsList.get(getAdapterPosition()).getSelected_Node_Id());
                    intent.putExtra("CategoryFileID", contentInfoModelsList.get(getAdapterPosition()).getACId());
                    intent.putExtra("FromInTasksContent", "FromInTasksContent");

                    context.startActivity(intent);

                } else {

                    String[] strsplit = contentInfoModelsList.get(getAdapterPosition()).getDownloadUrl().split("/");
                    String strFileNameExtension = strsplit[strsplit.length - 1];

                    File cDir = context.getExternalFilesDir(AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + contentInfoModelsList.get(getAdapterPosition()).getACName());
                    File appSpecificExternalDir = new File(cDir.getAbsolutePath(), strFileNameExtension);
                    Log.d(TAG, "FileExitsAdapter: " + appSpecificExternalDir);
                    if (appSpecificExternalDir.exists()) {

                        Intent intent = new Intent(context, ELearningViewActivity.class);
                        intent.putExtra("ELearning_Type", contentInfoModelsList.get(getAdapterPosition()).getACImagePath());
                        intent.putExtra("FileName", contentInfoModelsList.get(getAdapterPosition()).getACName());
                        intent.putExtra("ELearning_URL", contentInfoModelsList.get(getAdapterPosition()).getDownloadUrl());
                        intent.putExtra("ELearning_FilesInfoPosition", contentInfoModelsList.get(getAdapterPosition()));
                        intent.putExtra("FileNameExt", contentInfoModelsList.get(getAdapterPosition()).getACImagePath());
                        intent.putExtra("ELearning_FilesInfoList", (Serializable) contentInfoModelsList);
                        intent.putExtra("TopicName", contentInfoModelsList.get(getAdapterPosition()).getACName());
                        intent.putExtra("distributionIdView", DistrubutionID);
                        intent.putExtra("ParentID", contentInfoModelsList.get(getAdapterPosition()).getParent_Id());
                        intent.putExtra("Selected_Node_Id", contentInfoModelsList.get(getAdapterPosition()).getSelected_Node_Id());
                        intent.putExtra("CategoryFileID", contentInfoModelsList.get(getAdapterPosition()).getACId());
                        intent.putExtra("FromInTasksContent", "FromInTasksContent");
                        context.startActivity(intent);
                    } else {
                        improveHelper.showProgressDialog("Please wait. . .");
                        DownloadFileFromURLTask fileFromURLTask = new DownloadFileFromURLTask(
                                v, getAdapterPosition(),
                                contentInfoModelsList.get(getAdapterPosition()).getACName(),
                                contentInfoModelsList.get(getAdapterPosition()).getACImagePath(),
                                contentInfoModelsList.get(getAdapterPosition()).getACName(),
                                contentInfoModelsList.get(getAdapterPosition()).getDownloadUrl(),
                                contentInfoModelsList.get(getAdapterPosition()),
                                contentInfoModelsList.get(getAdapterPosition()).getACImagePath(),
                                contentInfoModelsList,
                                contentInfoModelsList.get(getAdapterPosition()).getACName(),
                                DistrubutionID,
                                contentInfoModelsList.get(getAdapterPosition()).getParent_Id(),
                                contentInfoModelsList.get(getAdapterPosition()).getSelected_Node_Id(),
                                contentInfoModelsList.get(getAdapterPosition()).getACId()
                        );
                        fileFromURLTask.execute(contentInfoModelsList.get(getAdapterPosition()).getDownloadUrl());
                    }

                }


                dialog.dismiss();

            }
        }

        private class DownloadFileFromURLTask extends AsyncTask<String, String, String> {
            //        private Context context;
            private final View v;
            private final int position;
            private final String strTopicTitle;
            private final String ELearning_Type;
            private final String FileName;
            private final String ELearning_URL;
            private final ContentInfoModel ELearning_FilesInfoPosition;
            private final String FileNameExt;
            private final String TopicName;
            String DistrubutionID;
            ELearningListAdapter.MyViewHolder holder;
            List<ContentInfoModel> ELearning_FilesInfoList;
            private RelativeLayout ll_progressbar;
            //        private ImageView imageView;
            private ProgressBar progressBar;
            private String strFileName;
            private final String parentId;
            private final String selected_node_id;
            private final String category_id;
            private String strFilePath;
            private File outFile;


            public DownloadFileFromURLTask(View v, int position, String strTopicTitle,
                                           String ELearning_Type, String FileName,
                                           String ELearning_URL, ContentInfoModel ELearning_FilesInfoPosition,
                                           String FileNameExt, List<ContentInfoModel> ELearning_FilesInfoList, String TopicName, String TopicID,
                                           String parentId, String selected_node_id, String category_id) {
                this.v = v;
                this.position = position;
                this.strTopicTitle = strTopicTitle;
                this.ELearning_Type = ELearning_Type;
                this.FileName = FileName;
                this.ELearning_URL = ELearning_URL;
                this.ELearning_FilesInfoPosition = ELearning_FilesInfoPosition;
                this.FileNameExt = FileNameExt;
                this.ELearning_FilesInfoList = ELearning_FilesInfoList;
                this.TopicName = TopicName;
                this.DistrubutionID = DistrubutionID;
                this.parentId = parentId;
                this.selected_node_id = selected_node_id;
                this.category_id = category_id;

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

                    File cDir = context.getExternalFilesDir(AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicTitle);
                    outFile = new File(cDir.getAbsolutePath(), strFileName);
                    // Output stream to write file
                    OutputStream output = new FileOutputStream(outFile);

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
                progressBar = v.findViewById(R.id.circularProgressbar);
                progressBar.setProgress(Integer.parseInt(progress[0]));
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
                ll_progressbar = v.findViewById(R.id.ll_progressbar);
//            ll_progressbar.setVisibility(View.GONE);

                // Displaying downloaded image into image view
                // Reading image path from sdcard
                Log.d(TAG, "doInBackgroundSet: " + outFile);

                if (outFile.exists()) {

                    ll_progressbar.setVisibility(View.GONE);

//                    ll_progressbar.setVisibility(View.GONE);
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                    if (filesInfoModelList.get(position).getFileNameExt().equalsIgnoreCase("jpg")) {
//                        imageView.setImageBitmap(myBitmap);
//                    }
//                ImproveHelper.showToastAlert(context, strFileName + "\n" + " Download successfully!");
                    improveHelper.dismissProgressDialog();

                    startAssessmentTimeForTopic();

                    Intent intent = new Intent(context, ELearningViewActivity.class);
                    intent.putExtra("ELearning_Type", ELearning_Type);
                    intent.putExtra("FileName", strFileName);
                    intent.putExtra("ELearning_URL", ELearning_URL);
                    intent.putExtra("ELearning_FilesInfoPosition", ELearning_FilesInfoPosition);
                    intent.putExtra("FileNameExt", FileNameExt);
                    intent.putExtra("ELearning_FilesInfoList", (Serializable) ELearning_FilesInfoList);
                    intent.putExtra("TopicName", strTopicTitle);
                    intent.putExtra("distributionIdView", DistrubutionID);
                    intent.putExtra("ParentID", parentId);
                    intent.putExtra("Selected_Node_Id", selected_node_id);
                    intent.putExtra("CategoryFileID", category_id);
                    intent.putExtra("FromInTasksContent", "FromInTasksContent");

                    context.startActivity(intent);

                }
                }
            }
        }



}