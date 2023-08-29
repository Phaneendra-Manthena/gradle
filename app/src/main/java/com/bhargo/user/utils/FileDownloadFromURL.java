package com.bhargo.user.utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhargo.user.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nk
 */
public class FileDownloadFromURL {

    private static final String TAG = "FileDownloadFromURL";
    private final Context context;
    FileDownloadListener fileDownloadListener;
    String fileName = "";
    boolean download = false;
    private ProgressDialog pd;
    private String urlLink = "";


    public FileDownloadFromURL(Context context, boolean download, String urlLink, FileDownloadListener fileDownloadListener) {
        this.context = context;
        this.urlLink = urlLink;
        this.fileDownloadListener = fileDownloadListener;
        String[] urlSplit = urlLink.split("/");
        fileName = urlSplit[urlSplit.length - 1];//Create file name by picking download file name from URL
        Log.e(TAG, fileName);
        this.download = download;
        //Start Downloading Task
        new FileDownload().execute();
    }

    private void showProgressDialog(Context context, String msg) {
        try {
            pd = new ProgressDialog(context);
            // pd = CustomProgressDialog.ctor(this, msg);
            // pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            pd.setMessage(msg);
            pd.setCancelable(false);
            pd.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeProgressDialog() {
        try {
            if (pd != null)
                pd.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showToast(Context context, String message) {

        LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View layout = LayoutInflater.from(context).inflate(R.layout.message_toast, null);
        layout.setLayoutParams(ll_params);
        TextView text = layout.findViewById(R.id.text);
        text.setText(String.format("%s   ", message));
        Toast toast = new Toast(context);

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }


    public interface FileDownloadListener {
        void onSuccess(File file);

        void onFailed(String errorMessage);
    }


    private class FileDownload extends AsyncTask<Void, Void, Void> {

        File fileStorage = null;
        File outputFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(context, "File Downloading...");
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (outputFile != null) {
                    closeProgressDialog();
                    if (outputFile.length() > 0) {
//                        showToast(context, "File Download Completed.");//If Download completed then change button text
                        fileDownloadListener.onSuccess(outputFile);
                    } else {
                        fileDownloadListener.onFailed("Failed To Download File. Try Again!");
                    }

                } else {
                    closeProgressDialog();
                    fileDownloadListener.onFailed("Failed To Download File. Try Again!");
                    Log.e(TAG, "Download Failed");

                }
            } catch (Exception e) {
                e.printStackTrace();
                closeProgressDialog();
                fileDownloadListener.onFailed("Failed To Download File. Try Again!");
            }
            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL url = new URL(urlLink);//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                            + " " + c.getResponseMessage());

                }

                //Set Folder Path
                //fileStorage = new File(Environment.getExternalStorageDirectory() + "/ImproveUserFiles/Download");
                if (download) {
                    fileStorage = ImproveHelper.createFolder(context, "ImproveUserFiles/Download");
                    outputFile = new File(fileStorage, fileName);//Create Output file in Main File
                } else {
                    outputFile = new File(context.getExternalCacheDir() + File.separator + fileName);
                }
                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    Log.e(TAG, "File Created");
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                InputStream is = c.getInputStream();//Get InputStream for connection

                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }

                //Close all connection after doing task
                fos.close();
                is.close();

            } catch (Exception e) {

                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
                Log.e(TAG, "Download Error Exception " + e.getMessage());
            }

            return null;
        }
    }


}