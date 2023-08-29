package com.bhargo.user.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.FilesInfoModel;
import com.bhargo.user.pojos.GetUserDistributionsResponse;
import com.bhargo.user.screens.ELearningListActivity;
import com.bhargo.user.screens.ELearningListActivityNew;
import com.bhargo.user.screens.ELearningViewActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.MyViewHolder> {

    private static final String TAG = "VideosAdapter";
    public JSONArray jsonArray;
    //    public FilesInfoModel filesInfoModel;
    public List<FilesInfoModel> filesInfoModel;
    SessionManager sessionManager;
    ImproveHelper improveHelper;
    Context context;
    List<GetUserDistributionsResponse> gudList;
    Gson gson = new Gson();
    private GetUserDistributionsResponse gudModel;
    private boolean fromCallAction;

    public boolean isFromCallAction() {
        return fromCallAction;
    }

    public void setFromCallAction(boolean fromCallAction) {
        this.fromCallAction = fromCallAction;
    }

    public VideosAdapter(Context context, List<GetUserDistributionsResponse> gudList) {
        this.context = context;
        this.gudList = gudList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.videos_list_item, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.videos_list_item_new, parent, false);
        // set the view's size, margins, paddings and layout_sample_app parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder


        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

//        gudModel = gudList.get(position);
//        setTopicImageFromDB(position,holder);
        if (ImproveHelper.isNetworkStatusAvialable(context)) {
            if (gudList.get(position).getTopicCoverImage() != null) {
                Glide.with(context).load(gudList.get(position).getTopicCoverImage())
                        .dontAnimate()
                        .placeholder(context.getResources()
                                .getDrawable(R.drawable.rounded_corners))
                        .into(holder.iv_circle_assess);
            } else {
                holder.iv_circle_assess.setImageDrawable(context.getDrawable(R.drawable.default_topic_image));
            }
        } else {
            sessionManager = new SessionManager(context);
            String appName = gudList.get(position).getTopicName();
            String replaceWithUnderscore = appName.replaceAll(" ", "_");
            String[] imgUrlSplit = gudList.get(position).getTopicCoverImage().split("/");
            String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + replaceWithUnderscore;
            setImageFromSDCard(holder, strSDCardUrl, imgUrlSplit[imgUrlSplit.length - 1]);

        }
        holder.tvAssess_name.setText(gudList.get(position).getTopicName());
        if (gudList.get(position).getTopicDescription() != null
                && gudList.get(position).getTopicDescription().equalsIgnoreCase("")) {
            holder.tvAssessDesc.setVisibility(View.GONE);
        } else {
            holder.tvAssessDesc.setText(gudList.get(position).getTopicDescription());
        }

        holder.tvAssessStatus.setText(gudList.get(position).getIs_Assessment());

    }

    @Override
    public int getItemCount() {
        return gudList.size();
    }


    public void updateList(List<GetUserDistributionsResponse> list) {
        gudList = list;
        notifyDataSetChanged();
    }

    public void setImageFromSDCard(MyViewHolder holder, String strImagePath, String filename) {

//        File imgFile = new File(Environment.getExternalStorageDirectory(), strImagePath);
//        File imgFile = new File(Environment.getExternalStorageDirectory() + strImagePath);
        File cDir = context.getExternalFilesDir(strImagePath);
        File appSpecificExternalDir = new File(cDir.getAbsolutePath(), filename);
        Log.d(TAG, "FileExitsAdapter: " + appSpecificExternalDir);
        if (appSpecificExternalDir.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(appSpecificExternalDir.getAbsolutePath());

            holder.iv_circle_assess.setImageBitmap(myBitmap);

        }
    }

    public void setTopicImageFromDB(int position, MyViewHolder holder) {
        if (gudList.get(position).getTopicCoverImage() != null) {
            String appName = gudList.get(position).getTopicName();
            String replaceWithUnderscore = appName.replaceAll(" ", "_");
            String[] imgUrlSplit = gudList.get(position).getTopicCoverImage().split("/");
            String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + replaceWithUnderscore;
            setImageFromSDCard(holder, strSDCardUrl, imgUrlSplit[imgUrlSplit.length - 1]);
        } else {
            Log.d(TAG, "setTopicImageFromDB: " + "No Image");
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_circle_assess;
        CustomTextView tvAssess_name, tvAssessDesc, tvAssessStatus;

        public MyViewHolder(View itemView) {
            super(itemView);

            iv_circle_assess = itemView.findViewById(R.id.iv_circle_assess);
            tvAssess_name = itemView.findViewById(R.id.tvAssess_name);
            tvAssessDesc = itemView.findViewById(R.id.tvAssessDesc);
            tvAssessStatus = itemView.findViewById(R.id.tvAssessStatus);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            sessionManager = new SessionManager(context);
            improveHelper = new ImproveHelper(context);

            filesInfoModel = Arrays.asList(gson.fromJson(gudList.get(getAdapterPosition()).getFilesInfo(), FilesInfoModel[].class));
            if (filesInfoModel != null && filesInfoModel.size() == 1 && gudList.get(getAdapterPosition()).getIs_Assessment().equalsIgnoreCase("No")) {
                improveHelper.showProgressDialog("Please wait...");

                String[] strsplit = filesInfoModel.get(0).getDownloadUrl().split("/");
                String strFileNameExtension = strsplit[strsplit.length - 1];

                File cDir = context.getExternalFilesDir(AppConstants.API_NAME_CHANGE + "/"
                        + sessionManager.getOrgIdFromSession()
                        + "/" + gudList.get(getAdapterPosition()).getTopicName().replaceAll(" ", "_"));
                File appSpecificExternalDir = new File(cDir.getAbsolutePath(), strFileNameExtension);
                Log.d(TAG, "FileExitsAdapter: " + appSpecificExternalDir);
                if (appSpecificExternalDir.exists()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String setAssessmentSessionStart = sdf.format(new Date());
                    PrefManger.putSharedPreferencesString(context, "AssessmentSessionStart", setAssessmentSessionStart);
                    Log.d(TAG, "AssessmentSessionStart: " + setAssessmentSessionStart);

                    if (filesInfoModel.get(0).getFileNameExt().equalsIgnoreCase("youtube")) {
                        if (ImproveHelper.isNetworkStatusAvialable(context)) {
                            Intent intent = new Intent(context, ELearningViewActivity.class);
                            intent.putExtra("ELearning_Type", filesInfoModel.get(0).getFileNameExt());
                            intent.putExtra("FileName", filesInfoModel.get(0).getSelectedFileName());
                            intent.putExtra("ELearning_URL", filesInfoModel.get(0).getDownloadUrl());
                            intent.putExtra("ELearning_FilesInfoPosition", filesInfoModel.get(0));
                            intent.putExtra("FileNameExt", filesInfoModel.get(0).getFileNameExt());
                            intent.putExtra("ELearning_FilesInfoList", (Serializable) filesInfoModel);
                            intent.putExtra("TopicName", gudList.get(getAdapterPosition()).getTopicName());
                            intent.putExtra("distributionIdView", gudList.get(getAdapterPosition()).getDistributionId());
                            intent.putExtra("ParentID", filesInfoModel.get(0).getParent_Id());
                            intent.putExtra("Selected_Node_Id", filesInfoModel.get(0).getSelected_Node_Id());
                            intent.putExtra("CategoryFileID", filesInfoModel.get(0).getCategoryFileID());
                            intent.putExtra("Direct", "Direct");
                            if(fromCallAction){
                                intent.putExtra("Direct", "fromCallAction");
                            }
                            improveHelper.dismissProgressDialog();
                            context.startActivity(intent);
                        } else {
                            improveHelper.dismissProgressDialog();
                            ImproveHelper.showToast(context, "Internet connection needed for this file");
                        }
                    } else {
                        if (filesInfoModel.get(0).getFileNameExt().equalsIgnoreCase("file")) {
                            if (ImproveHelper.isNetworkStatusAvialable(context)) {
                                Intent intent = new Intent(context, ELearningViewActivity.class);
                                intent.putExtra("ELearning_Type", filesInfoModel.get(0).getFileNameExt());
                                intent.putExtra("FileName", filesInfoModel.get(0).getSelectedFileName());
                                intent.putExtra("ELearning_URL", filesInfoModel.get(0).getDownloadUrl());
                                intent.putExtra("ELearning_FilesInfoPosition", filesInfoModel.get(0));
                                intent.putExtra("FileNameExt", filesInfoModel.get(0).getFileNameExt());
                                intent.putExtra("ELearning_FilesInfoList", filesInfoModel.get(0));
                                intent.putExtra("TopicName", gudList.get(getAdapterPosition()).getTopicName());
                                intent.putExtra("distributionIdView", gudList.get(getAdapterPosition()).getDistributionId());
                                intent.putExtra("ParentID", filesInfoModel.get(0).getParent_Id());
                                intent.putExtra("Selected_Node_Id", filesInfoModel.get(0).getSelected_Node_Id());
                                intent.putExtra("CategoryFileID", filesInfoModel.get(0).getCategoryFileID());
                                intent.putExtra("Direct", "Direct");
                                if(fromCallAction){
                                    intent.putExtra("Direct", "fromCallAction");
                                }
                                improveHelper.dismissProgressDialog();
                                context.startActivity(intent);
                            } else {
                                improveHelper.dismissProgressDialog();
                                ImproveHelper.showToast(context, "Internet connection needed for this file");
                            }

                        } else {
                            Intent intent = new Intent(context, ELearningViewActivity.class);
                            intent.putExtra("ELearning_Type", filesInfoModel.get(0).getFileNameExt());
                            intent.putExtra("FileName", filesInfoModel.get(0).getSelectedFileName());
                            intent.putExtra("ELearning_URL", filesInfoModel.get(0).getDownloadUrl());
                            intent.putExtra("ELearning_FilesInfoPosition", filesInfoModel.get(0));
                            intent.putExtra("FileNameExt", filesInfoModel.get(0).getFileNameExt());
                            intent.putExtra("ELearning_FilesInfoList", filesInfoModel.get(0));
                            intent.putExtra("TopicName", gudList.get(getAdapterPosition()).getTopicName());
                            intent.putExtra("distributionIdView", gudList.get(getAdapterPosition()).getDistributionId());
                            intent.putExtra("ParentID", filesInfoModel.get(0).getParent_Id());
                            intent.putExtra("Selected_Node_Id", filesInfoModel.get(0).getSelected_Node_Id());
                            intent.putExtra("CategoryFileID", filesInfoModel.get(0).getCategoryFileID());
                            intent.putExtra("Direct", "Direct");
                            if(fromCallAction){
                                intent.putExtra("Direct", "fromCallAction");
                            }
                            improveHelper.dismissProgressDialog();
                            context.startActivity(intent);
                        }
                    }
                } else {
                    if (ImproveHelper.isNetworkStatusAvialable(context)) {
                        DownloadFileFromURLTask downloadFileFromURLTask = new DownloadFileFromURLTask(v, getAdapterPosition(), gudList.get(getAdapterPosition()).getTopicName().replaceAll(" ", "_"));
                        downloadFileFromURLTask.execute(filesInfoModel.get(0).getDownloadUrl());
//                        downloadFileFromURLTask.execute("http://182.18.157.124/BhargoUploadFiles/Bhargo_BhargoInnovations/BHAR00000001/Elearning/ContentCoverPageFiles/download_(2)_20230421171138662.jpg");
                    } else {
                        improveHelper.dismissProgressDialog();
                        ImproveHelper.showToast(context, "Please check internet connection ");
                    }
                }
            } else {
                Intent intent = new Intent(context, ELearningListActivity.class);
//                Intent intent = new Intent(context, ELearningListActivityNew.class);
                intent.putExtra("DistributionId", gudList.get(getAdapterPosition()).getDistributionId());
                intent.putExtra("ExamDuration", gudList.get(getAdapterPosition()).getExamDuration());
                intent.putExtra("FilesInfoModelList", (Serializable) filesInfoModel);
//            intent.putExtra("FilesInfoModelList", gudList.get(getAdapterPosition()).getFilesInfo());
                intent.putExtra("EL_TopicName", gudList.get(getAdapterPosition()).getTopicName());

                intent.putExtra("Is_Assessment", gudList.get(getAdapterPosition()).getIs_Assessment());
                intent.putExtra("NoOfAttempts", gudList.get(getAdapterPosition()).getNoOfAttempts());
                intent.putExtra("NoOfUserAttempts", gudList.get(getAdapterPosition()).getNoOfUserAttempts());
                intent.putExtra("hQuestions", gudList.get(getAdapterPosition()).gethQuestions());
                intent.putExtra("mQuestions", gudList.get(getAdapterPosition()).getmQuestions());
                intent.putExtra("lQuestions", gudList.get(getAdapterPosition()).getlQuestions());
                intent.putExtra("tQuestions", gudList.get(getAdapterPosition()).gettQuestions());
                intent.putExtra("Is_Compexcity", gudList.get(getAdapterPosition()).getIs_Compexcity());
                intent.putExtra("StartDate", gudList.get(getAdapterPosition()).getStartDate());
                intent.putExtra("StartDisplayTime", gudList.get(getAdapterPosition()).getStartDisplayTime());
                intent.putExtra("EndDate", gudList.get(getAdapterPosition()).getEndDate());
                intent.putExtra("EndDisplayTime", gudList.get(getAdapterPosition()).getEndDisplayTime());
                intent.putExtra("StartTime", gudList.get(getAdapterPosition()).getStartTime());
                intent.putExtra("EndTime", gudList.get(getAdapterPosition()).getEndTime());
                intent.putExtra("GetUserDistributionsList", (Serializable) gudList);
                if(fromCallAction){
                    intent.putExtra("Direct", "fromCallAction");
                }
                improveHelper.dismissProgressDialog();
                context.startActivity(intent);

            }
        }
    }

    private class DownloadFileFromURLTask extends AsyncTask<String, String, String> {
        //        private Context context;
        private final View v;
        private final int position;
        private final String strTopicTitle;
        ELearningListAdapter.MyViewHolder holder;
        //        private ImageView imageView;
        private ProgressBar progressBar;
        private String strFileName;
        private String strFilePath;
        private File outFile;

        public DownloadFileFromURLTask(View v, int position, String strTopicTitle) {
            this.v = v;
            this.position = position;
            this.strTopicTitle = strTopicTitle;

        }

        /**
         * Downloading file in background thread
         */


        @SuppressLint("SdCardPath")
        @Override
        protected String doInBackground(String... f_url) {
            Log.i(TAG, "do in background " + f_url[0]);
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

//                File appSpecificExternalDir = new File(cDir.getAbsolutePath(), strFileName);
//                Log.d(TAG, "FileExitsAdapter: " + appSpecificExternalDir);

//                file = new File(
//                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicTitle);
//                if (!file.exists()) {
//                    file.mkdirs();
//                }
//                file = new File(context.getExternalFilesDir(null),"/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats");
                /*file = new File(context.getExternalFilesDir(null),"/" +AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicTitle);
                if (!file.exists())
                    file.mkdirs();*/
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

////            mProgress.setProgress(Integer.parseInt(progress[0]));
//            progressBar = v.findViewById(R.id.circularProgressbar);
//            progressBar.setProgress(Integer.parseInt(progress[0]));
////            tv_progStatus.setText(progress[0]);
////          textProgress2.setText(progress[0]);


        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @SuppressLint("SdCardPath")
        @Override
        protected void onPostExecute(String file_url) {
            Log.i(TAG, "on post excute!: " + file_url);

            // dismiss progressbars after the file was downloaded
//            ll_progressbar.setVisibility(View.GONE);

            // Displaying downloaded image into image view
            // Reading image path from sdcard
            Log.d(TAG, "doInBackgroundSet: " + outFile);
            improveHelper.dismissProgressDialog();
            if (outFile != null && outFile.exists()) {
//                    ll_progressbar.setVisibility(View.GONE);
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                    if (filesInfoModelList.get(position).getFileNameExt().equalsIgnoreCase("jpg")) {
//                        imageView.setImageBitmap(myBitmap);
//                    }
//                ImproveHelper.showToastAlert(context, strFileName + "\n" + " Download successfully!");
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String setAssessmentSessionStart = sdf.format(new Date());
                PrefManger.putSharedPreferencesString(context, "AssessmentSessionStart", setAssessmentSessionStart);
                Log.d(TAG, "AssessmentSessionStart: " + setAssessmentSessionStart);
                Intent intent = new Intent(context, ELearningViewActivity.class);
                if (filesInfoModel.get(0).getFileNameExt().equalsIgnoreCase("youtube")) {

                    intent.putExtra("ELearning_Type", filesInfoModel.get(0).getFileNameExt());
                    intent.putExtra("FileName", filesInfoModel.get(0).getSelectedFileName());
                    intent.putExtra("ELearning_URL", filesInfoModel.get(0).getDownloadUrl());
                    intent.putExtra("ELearning_FilesInfoPosition", filesInfoModel.get(0));
                    intent.putExtra("FileNameExt", filesInfoModel.get(0).getFileNameExt());
                    intent.putExtra("ELearning_FilesInfoList", (Serializable) filesInfoModel);
                    intent.putExtra("TopicName", strTopicTitle);
                    intent.putExtra("distributionIdView", gudList.get(position).getDistributionId());
                    intent.putExtra("ParentID", filesInfoModel.get(0).getParent_Id());
                    intent.putExtra("Selected_Node_Id", filesInfoModel.get(0).getSelected_Node_Id());
                    intent.putExtra("CategoryFileID", filesInfoModel.get(0).getCategoryFileID());
                    intent.putExtra("Direct", "Direct");
                    if(fromCallAction){
                        intent.putExtra("Direct", "fromCallAction");
                    }
                } else {
                    intent.putExtra("ELearning_Type", filesInfoModel.get(0).getFileNameExt());
                    intent.putExtra("FileName", filesInfoModel.get(0).getSelectedFileName());
                    intent.putExtra("ELearning_URL", filesInfoModel.get(0).getDownloadUrl());
                    intent.putExtra("ELearning_FilesInfoPosition", filesInfoModel.get(0));
                    intent.putExtra("FileNameExt", filesInfoModel.get(0).getFileNameExt());
                    intent.putExtra("ELearning_FilesInfoList", filesInfoModel.get(0));
                    intent.putExtra("TopicName", strTopicTitle);
                    intent.putExtra("distributionIdView", gudList.get(position).getDistributionId());
                    intent.putExtra("ParentID", filesInfoModel.get(0).getParent_Id());
                    intent.putExtra("Selected_Node_Id", filesInfoModel.get(0).getSelected_Node_Id());
                    intent.putExtra("CategoryFileID", filesInfoModel.get(0).getCategoryFileID());
                    intent.putExtra("Direct", "Direct");
                    if(fromCallAction){
                        intent.putExtra("Direct", "fromCallAction");
                    }
                }


                context.startActivity(intent);
            }


//            notifyDataSetChanged();
        }
    }
}
