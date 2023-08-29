package com.bhargo.user.adapters;

import android.annotation.SuppressLint;
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

import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.FilesInfoModel;
import com.bhargo.user.screens.ELearningViewActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ELearningListAdapter extends RecyclerView.Adapter<ELearningListAdapter.MyViewHolder> {

    private static final String TAG = "ELearningListAdapter";
    Context context;
    List<FilesInfoModel> filesInfoModelList;
    String strELearningType = "", strEL_ViewTitle = "";
    GetServices getServices;
    String distributionId = "";
    List<View> viewArrayList = new ArrayList<View>();
    SessionManager sessionManager;
    String strTopicTitle;
    private int adapterPosition;
    ImproveHelper improveHelper;


    public ELearningListAdapter(Context context, List<FilesInfoModel> filesInfoModelList, String distributionId, String strTopicTitle) {
        this.context = context;
        this.filesInfoModelList = filesInfoModelList;
        this.distributionId = distributionId;
        this.strTopicTitle = strTopicTitle;
        sessionManager = new SessionManager(context);
        improveHelper =  new ImproveHelper(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elearning_list_item, parent, false);
        // set the view's size, margins, paddings and layout_sample_app parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;

    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        FilesInfoModel gudModel = filesInfoModelList.get(position);
//        Log.d(TAG, "onBindViewHolderCheckExt: " + gudModel.getFileNameExt());

        holder.ll_elImage.setVisibility(View.VISIBLE);

        String[] strsplit = filesInfoModelList.get(position).getDownloadUrl().split("/");
        String strFileNameExtension = strsplit[strsplit.length - 1];

        File cDir = context.getExternalFilesDir(AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicTitle);
        File appSpecificExternalDir = new File(cDir.getAbsolutePath(), strFileNameExtension);
        Log.d(TAG, "FileExitsAdapter: " + appSpecificExternalDir);
        if (appSpecificExternalDir.exists()) {
            holder.ll_progressbar.setVisibility(View.GONE);
        }else {
            holder.ll_progressbar.setVisibility(View.VISIBLE);
        }

/*
        File imgFile = new File(Environment.getExternalStorageDirectory() + "/" + AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicTitle + "/" + strFileNameExtension);
//        File imgFile = new File(context.getExternalFilesDir(null), "/" + AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicTitle + "/" + strFileNameExtension);
        if (imgFile.exists()) {
//            String strSDCardUrlExists = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + strTopicTitle + "/" + strFileNameExtension;
            String strSDCardUrlExists = "/" + AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicTitle + "/" + strFileNameExtension;
            if (ImproveHelper.isFileExists(strSDCardUrlExists)) {
                holder.ll_progressbar.setVisibility(View.GONE);
            }
        } else {
            holder.ll_progressbar.setVisibility(View.VISIBLE);
        }
*/


//        switch (filesInfoModelList.get(position).getFileNameExt()) {
//
//            case "jpg":
////                Glide.with(context).load(gudModel.getDownloadUrl()).dontAnimate().into(holder.iv_elImage);
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.jpg));
//                break;
//            case "png":
////                Glide.with(context).load(gudModel.getDownloadUrl()).dontAnimate().into(holder.iv_elImage);
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.png));
//                break;
//            case "mp3":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.mp3));
//                break;
//            case "3gp":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.threegp));
//                break;
//            case "mp4":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.mp4));
//                break;
//            case "mov":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.mov));
//                break;
//            case "avi":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.avi));
//                break;
//            case "ogg":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.ogg));
//                break;
//            case "webm":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.webm));
//                break;
//            case "bmp":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.bmp));
//                break;
//            case "gif":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.gif));
//                break;
//            case "mpeg":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.mpeg));
//                break;
//            case "ogv":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.ogv));
//                break;
//            case "wmv":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.wmv));
//                break;
//            case "youtube":
//                holder.ll_progressbar.setVisibility(View.GONE);
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.youtube));
//                break;
//            case "pdf":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.pdf));
//                break;
//            case "ppt":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.ppt));
//                break;
//            case "pptx":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.pptx));
//                break;
//            case "docx":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.docx));
//                break;
//            case "txt":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.txt));
//                break;
//            case "html":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.html));
//                break;
//            case "js":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.js));
//                break;
//            case "css":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.css));
//                break;
//            case "zip":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.zip));
//                break;
//            case "exe":
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.exe));
//                break;
//            default:
//                holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.file));
//
//        }
//        Glide.with(context).load(filesInfoModelList.get(position).getFileCoverImage())
//        Glide.with(context).load("http://182.18.157.124/BhargoUploadFiles/Bhargo_BhargoInnovations/BHAR00000001/Elearning/ContentCoverPageFiles/download_(2)_20230421171138662.jpg")
        Glide.with(context).load(filesInfoModelList.get(position).getFileCoverImage())
                .dontAnimate()
                .placeholder(context.getResources()
                        .getDrawable(R.drawable.round_rect_shape_ds))
                .into(holder.iv_elImage);

        holder.ctv_elName.setText(filesInfoModelList.get(position).getSelectedFileName());
        if (filesInfoModelList.get(position).getFileNameExt().equalsIgnoreCase("youtube")) {
            holder.ll_progressbar.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return filesInfoModelList.size();
    }


    public void updateList(List<FilesInfoModel> list) {
        filesInfoModelList = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout ll_elImage;
        RelativeLayout ll_progressbar;
        ImageView iv_elImage;
        CustomTextView ctv_elName;
        ProgressBar mProgress;

        public MyViewHolder(View itemView) {
            super(itemView);

            ll_elImage = itemView.findViewById(R.id.ll_elImage);
            ll_progressbar = itemView.findViewById(R.id.ll_progressbar);
            iv_elImage = itemView.findViewById(R.id.iv_elImage);
            ctv_elName = itemView.findViewById(R.id.ctv_elName);
            mProgress = itemView.findViewById(R.id.circularProgressbar);

            itemView.setOnClickListener(this);
//            ll_progressbar.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String setAssessmentSessionStart = sdf.format(new Date());
            PrefManger.putSharedPreferencesString(context, "AssessmentSessionStart", setAssessmentSessionStart);
            Log.d(TAG, "AssessmentSessionStart: " + setAssessmentSessionStart);
            if (filesInfoModelList.get(getAdapterPosition()).getFileNameExt().equalsIgnoreCase("youtube")) {

                Intent intent = new Intent(context, ELearningViewActivity.class);
//
                intent.putExtra("ELearning_Type", filesInfoModelList.get(getAdapterPosition()).getFileNameExt());
                intent.putExtra("FileName", filesInfoModelList.get(getAdapterPosition()).getSelectedFileName());
                intent.putExtra("ELearning_URL", filesInfoModelList.get(getAdapterPosition()).getDownloadUrl());
                intent.putExtra("ELearning_FilesInfoPosition", filesInfoModelList.get(getAdapterPosition()));
                intent.putExtra("FileNameExt", filesInfoModelList.get(getAdapterPosition()).getFileNameExt());
                intent.putExtra("ELearning_FilesInfoList", (Serializable) filesInfoModelList);
                intent.putExtra("TopicName", strTopicTitle);
                intent.putExtra("distributionIdView", distributionId);
                intent.putExtra("ParentID", filesInfoModelList.get(getAdapterPosition()).getParent_Id());
                intent.putExtra("Selected_Node_Id", filesInfoModelList.get(getAdapterPosition()).getSelected_Node_Id());
                intent.putExtra("CategoryFileID", filesInfoModelList.get(getAdapterPosition()).getCategoryFileID());

                context.startActivity(intent);

            } else if (ll_progressbar.getVisibility() == View.GONE) {

                Intent intent = new Intent(context, ELearningViewActivity.class);
                intent.putExtra("ELearning_Type", filesInfoModelList.get(getAdapterPosition()).getFileNameExt());
                intent.putExtra("FileName", filesInfoModelList.get(getAdapterPosition()).getSelectedFileName());
                intent.putExtra("ELearning_URL", filesInfoModelList.get(getAdapterPosition()).getDownloadUrl());
                intent.putExtra("ELearning_FilesInfoPosition", filesInfoModelList.get(getAdapterPosition()));
                intent.putExtra("FileNameExt", filesInfoModelList.get(getAdapterPosition()).getFileNameExt());
                intent.putExtra("ELearning_FilesInfoList", (Serializable) filesInfoModelList);
                intent.putExtra("TopicName", strTopicTitle);
                intent.putExtra("distributionIdView", distributionId);
                intent.putExtra("ParentID", filesInfoModelList.get(getAdapterPosition()).getParent_Id());
                intent.putExtra("Selected_Node_Id", filesInfoModelList.get(getAdapterPosition()).getSelected_Node_Id());
                intent.putExtra("CategoryFileID", filesInfoModelList.get(getAdapterPosition()).getCategoryFileID());

                context.startActivity(intent);

            } else {
                viewArrayList.add(v);
//                DownloadFileFromURLTask fileFromURLTask = new DownloadFileFromURLTask(context, v, ll_progressbar, iv_elImage, mProgress, getAdapterPosition(),holder);
                if(ImproveHelper.isNetworkStatusAvialable(context)) {
                    DownloadFileFromURLTask fileFromURLTask = new DownloadFileFromURLTask(v, getAdapterPosition(), strTopicTitle);
                    fileFromURLTask.execute(filesInfoModelList.get(getAdapterPosition()).getDownloadUrl());
//                    fileFromURLTask.execute("http://182.18.157.124/BhargoUploadFiles/Bhargo_BhargoInnovations/BHAR00000001/Elearning/ContentCoverPageFiles/download_(2)_20230421171138662.jpg");
                }else{
                    ImproveHelper.showToast(context,"Please check internet connection ");
                }
            }
        }
    }

    private class DownloadFileFromURLTask extends AsyncTask<String, String, String> {
        MyViewHolder holder;
        //        private Context context;
        private View v;
        private RelativeLayout ll_progressbar;
        //        private ImageView imageView;
        private ProgressBar progressBar;
        private int position;
        private String strFileName;
        private String strTopicTitle, strFilePath;
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

            if (outFile != null &&  outFile.exists()) {

                ll_progressbar.setVisibility(View.GONE);

//                    ll_progressbar.setVisibility(View.GONE);
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                    if (filesInfoModelList.get(position).getFileNameExt().equalsIgnoreCase("jpg")) {
//                        imageView.setImageBitmap(myBitmap);
//                    }
                ImproveHelper.showToastAlert(context, strFileName + "\n" + " Download successfully!");

            }


//            notifyDataSetChanged();
        }
    }
}
