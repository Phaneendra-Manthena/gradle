package com.bhargo.user.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bhargo.user.adapters.ELearningListAdapter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

// TODO: 06-12-2022 Sanjay

public class DownloadFilesFromURL {

    private static final String TAG = "DownloadFilesFromURL";
    Context context;
    SessionManager sessionManager;
    ELearningListAdapter.MyViewHolder holder;
    String fromTab;
    File cDir = null;
    DownloadFileListener downloadFileListener;
    String dataControlName;
    private final View v;
    private int position;
    private final String url;
    private String strFileName;
    private final String strTopicTitle;
    private String strFilePath;
    private File outFile;
    private RelativeLayout ll_progressbar;
    private ProgressBar progressBar;

    public DownloadFilesFromURL(Context context, String url, SessionManager sessionManager, View v, String fromTab, String strTopicTitle,
                                String dataControlName, DownloadFileListener downloadFileListener) {
        this.context = context;
        this.url = url;
        this.sessionManager = sessionManager;
        this.v = v;
        this.fromTab = fromTab;
        this.strTopicTitle = strTopicTitle;
        this.downloadFileListener = downloadFileListener;
        this.dataControlName = dataControlName;
        DownloadAsync downloadAsync = new DownloadAsync();
        downloadAsync.execute(url);
    }


    public interface DownloadFileListener {
        void onSuccess(File file, String dataControlName);

        void onFailed(String errorMessage);
    }

    private class DownloadAsync extends AsyncTask<String, String, String> {

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
                URLConnection connection = url.openConnection();
                connection.connect();
                // getting file length
                int lengthOfFile = connection.getContentLength();
                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                String[] strSplit = f_url[0].split("/");
                strFileName = strSplit[strSplit.length - 1];
//                File cDir = context.getExternalFilesDir(AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicTitle);
                if (!fromTab.isEmpty() && fromTab.equalsIgnoreCase("ELearningListActivityNew")) {
//                    cDir = context.getExternalFilesDir( context.getResources().getString(R.string.app_name)+ "/" +sessionManager.getOrgIdFromSession()+ "/" + AppConstants.E_LEARNING_FILES+ "/" + strTopicTitle);
                    cDir = context.getExternalFilesDir(sessionManager.getOrgIdFromSession() + "/" + AppConstants.E_LEARNING_FILES + "/" + strTopicTitle);
                }
                if (!fromTab.isEmpty() && (fromTab.equalsIgnoreCase("OTPUtils") || fromTab.equalsIgnoreCase("AppsListFragment"))) {
//                    cDir = context.getExternalFilesDir( context.getResources().getString(R.string.app_name)+ "/" +sessionManager.getOrgIdFromSession()+ "/" + AppConstants.E_LEARNING_FILES+ "/" + strTopicTitle);
                    cDir = context.getExternalFilesDir(sessionManager.getOrgIdFromSession() + "/");
                } else {
//                    cDir = context.getExternalFilesDir(context.getResources().getString(R.string.app_name)+ "/" +sessionManager.getOrgIdFromSession() + "/" + strTopicTitle);
                    cDir = context.getExternalFilesDir(AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicTitle);
                }
                outFile = new File(cDir.getAbsolutePath(), strFileName);
                // Output stream to write file
                OutputStream output = new FileOutputStream(outFile);

                byte[] data = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                outFile = null;
                Log.e("Error: ", e.getMessage());
            }

            if (outFile == null) {
                return null;
            } else {
                return outFile.getAbsolutePath();
            }
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
//            progressBar = v.findViewById(R.id.circularProgressbar);
//            progressBar.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @SuppressLint("SdCardPath")
        @Override
        protected void onPostExecute(String file_url) {
            Log.i(TAG, "on post excute!: " + file_url);
            Log.d(TAG, "doInBackgroundSet: " + outFile);

            /*if (outFile != null &&  outFile.exists()) {
                ImproveHelper.showToastAlert(context, strFileName + "\n"  + "\n" + " Download successfully!");
            }*/
            if (file_url == null) {
                downloadFileListener.onFailed("Failed To Download File. Try Again!");
            } else {
                downloadFileListener.onSuccess(new File(file_url), dataControlName);
            }
        }
    }
}
