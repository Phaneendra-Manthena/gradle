package com.bhargo.user.screens;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.adapters.AppsAdapter;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppDetails;
import com.bhargo.user.pojos.GetAllAppModel;
import com.bhargo.user.pojos.GetAllAppNamesData;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.R;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bhargo.user.utils.XMLHelper;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.screens.BottomNavigationActivity.iv_appListRefresh;
import static com.bhargo.user.utils.AppConstants.PERMISSION_STORAGE_CODE;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

public class SubAppsListActivity extends BaseActivity {

    private static final String TAG = "SubAppsListActivity";
    Context context;
    RecyclerView rv_apps;
    AppsAdapter appsAdapter;
    public ImproveHelper improveHelper;
    SessionManager sessionManager;
    ImproveDataBase improveDataBase;
    String displayAs = "Application";
    private List<AppDetails> apiDetailsList;
    private GetAllAppNamesData getAllAppNamesData;
    GetServices getServices;
    private GetAllAppModel getAllAppModel;
    DatabaseReference mFirebaseDatabaseReference;
    String appIconPath = null,strAppName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_apps_list);
        improveHelper = new ImproveHelper(this);
        improveHelper.showProgressDialog(getResources().getString(R.string.loading_apps));
        sessionManager = new SessionManager(this);
        improveDataBase = new ImproveDataBase(this);
        getAllAppNamesData = new GetAllAppNamesData();
        getServices = RetrofitUtils.getUserService();

        context = this;
        initializeActionBar();
        enableBackNavigation(true);

        getAllAppNamesData.setOrgId(sessionManager.getOrgIdFromSession());
        getAllAppNamesData.setUserId(sessionManager.getUserDataFromSession().getUserID());
        getAllAppNamesData.setAppType("User");

        rv_apps = findViewById(R.id.rv_apps);
        rv_apps.setLayoutManager(new GridLayoutManager(context, 2));
        appsAdapter = new AppsAdapter(this, new ArrayList<AppDetails>(), true,true);

        strAppName=getIntent().getStringExtra("WorkspaceName");
        title.setText(getIntent().getStringExtra("WorkspaceName"));
        appIconPath =getIntent().getStringExtra("AppIcon");
        loadAppIcon(appIconPath);

        ListAppsOnlineOffLineByWorkspace(sessionManager.getOrgIdFromSession(),sessionManager.getPostsFromSession(),
                getIntent().getStringExtra("WorkspaceName"));
    }

    public void loadAppIcon(String appIcon) {

        try {
            if (appIcon != null) {

                if (isNetworkStatusAvialable(context)) {
                    Glide.with(context).load(appIcon).into(iv_circle_appIcon);
                } else {

                    String[] imgUrlSplit = appIcon.split("/");
                    String replaceWithUnderscore = strAppName.replaceAll(" ", "_");

                    String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + replaceWithUnderscore + "/" + imgUrlSplit[imgUrlSplit.length - 1];
                    Log.d(TAG, "onCreateSDCardPathCheck: " + strSDCardUrl);

                    setImageFromSDCard(strSDCardUrl);
                    improveHelper.snackBarAlertActivities(context, rv_apps);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "loadAppIcon", e);
        }
    }

    public void setImageFromSDCard(String strImagePath) {

        File imgFile = new File(Environment.getExternalStorageDirectory(), strImagePath);
        Log.d(TAG, "setImageFromSDCard: " + imgFile);
        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            iv_circle_appIcon.setImageBitmap(myBitmap);

        }
    }

    private void ListAppsOnlineOffLineByWorkspace(String strOrgId, String strPostId,String WorkspaceName) {
        try {
            List<AppDetails> appDetailsList = improveDataBase.getDataFromAppsListTableWithinWorkspace(strOrgId, sessionManager.getUserDataFromSession().getUserID(),
                    strPostId, displayAs,WorkspaceName);

            if (appDetailsList != null && appDetailsList.size() > 0) {
                apiDetailsList = appDetailsList;

                rv_apps.setVisibility(View.VISIBLE);
                appsAdapter = new AppsAdapter(this, appDetailsList, true,true);
                rv_apps.setAdapter(appsAdapter);
                improveHelper.dismissProgressDialog();

                Log.d(TAG, "ListAppsOnlineOffLineByWorkspace: " + "Apps FromDB based and Workspace is: "+WorkspaceName);
            }else {
                if (isNetworkStatusAvialable(this)) {

                    mAppsListAPI(getAllAppNamesData, WorkspaceName);

                } else {
                    improveHelper.snackBarAlertFragment(this, rv_apps);
                    improveHelper.dismissProgressDialog();
                    rv_apps.setVisibility(View.GONE);

                }

            }

        } catch (Exception e) {
        improveHelper.improveException(this, TAG, "ListAppsOnlineOffLineByWorkspace", e);
        }
    }


    private void mAppsListAPI(final GetAllAppNamesData getAllAppNamesData,String WorkspaceName) {
        try {
            Call<GetAllAppModel> getAllAppNamesDataCall = getServices.iGetAllAppsList(sessionManager.getAuthorizationTokenId(),getAllAppNamesData);
            getAllAppNamesDataCall.enqueue(new Callback<GetAllAppModel>() {
                @Override
                public void onResponse(Call<GetAllAppModel> call, Response<GetAllAppModel> response) {

                    if (response.body() != null) {
                        getAllAppModel = response.body();
                        String strNewVersionToOldVersion;
                        if (getAllAppModel.getAppDetails() != null && getAllAppModel.getAppDetails().size() > 0) {
                            rv_apps.setVisibility(View.VISIBLE);
                            iv_appListRefresh.setVisibility(View.VISIBLE);

                            improveDataBase.deleteAppsListData(getAllAppNamesData.getOrgId(), sessionManager.getUserDataFromSession().getUserID());

                            improveDataBase.insertIntoAppsListTable(getAllAppModel.getAppDetails(), getAllAppNamesData.getOrgId(), sessionManager.getUserDataFromSession().getUserID());

                            apiDetailsList= improveDataBase.getDataFromAppsListTableWithinWorkspace(getAllAppNamesData.getOrgId(), sessionManager.getUserDataFromSession().getUserID(),sessionManager.getPostsFromSession(),displayAs,WorkspaceName);

                            if (apiDetailsList != null && apiDetailsList.size() > 0) {

                                appsAdapter = new AppsAdapter(SubAppsListActivity.this, apiDetailsList, true,true);

                                rv_apps.setAdapter(appsAdapter);

                            }
                            updateFirebaseStatus();
//                    appsAdapter.notifyDataSetChanged();

                            // working
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//Ask Permission
                                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                    requestPermissions(permissions, PERMISSION_STORAGE_CODE);
                                } else {
                                    createAppFolderAndDownloadFiles(getAllAppModel.getAppDetails());
                                }
                            } else {
                                createAppFolderAndDownloadFiles(getAllAppModel.getAppDetails());
                            }

                            improveHelper.dismissProgressDialog();

                        } else {

//                        ImproveHelper.showToast(getActivity(), getAllAppModel.getMessage());
                            improveHelper.dismissProgressDialog();
                            rv_apps.setVisibility(View.GONE);
                            iv_appListRefresh.setVisibility(View.GONE);

                        }
                    } else {

                        improveHelper.dismissProgressDialog();
                        rv_apps.setVisibility(View.GONE);
                        iv_appListRefresh.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onFailure(Call<GetAllAppModel> call, Throwable t) {
                    System.out.println("Error at getapps =="+t.toString());
                    improveHelper.dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "mAppsListAPI", e);
        }
    }

    public void updateFirebaseStatus() {
        try {
            sessionManager = new SessionManager(context);
            mFirebaseDatabaseReference = FirebaseDatabase.getInstance("https://bhargocommunication-8c36e.firebaseio.com").getReference(sessionManager.getOrgIdFromSession());
            mFirebaseDatabaseReference.child("Users").
                    orderByChild("Mobile").equalTo(sessionManager.
                    getUserDetailsFromSession().getPhoneNo())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                HashMap<String, Object> statusMap = new HashMap<>();
                                statusMap.put("AppStatus", 0);
                                mFirebaseDatabaseReference.child("Users").child(sessionManager.getUserChatID()).updateChildren(statusMap);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "updateFirebaseStatus", e);
        }
    }

    public void createAppFolderAndDownloadFiles(List<AppDetails> appDetails) {
        try {
            for (int count = 0; count < appDetails.size(); count++) {
                String appNameForDB = appDetails.get(count).getAppName();
                String appName = appDetails.get(count).getAppName().replaceAll(" ", "_");

                /*Menu Icons folder and icons download*/

//            if(count == 95){

                Log.d(TAG, "AppsCount: " + count);
                if (appDetails.get(count).getDesignFormat() != null) {
                    String designStringMenu = appDetails.get(count).getDesignFormat();
                    XMLHelper xmlHelper = new XMLHelper();
//                if (designStringMenu.contains("MenuControl")) {
                    //nk step
                    System.out.println("=======Step10===============");
                    DataCollectionObject dataCollectionObject = xmlHelper.XML_To_DataCollectionObject(designStringMenu);
                    String strAppMode = dataCollectionObject.getApp_Mode();

//                    File rootMenu = new File(Environment.getExternalStorageDirectory(),
//                            "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "MenuIcons/");
//                    if (!rootMenu.exists()) {
//                        rootMenu.mkdirs();
//                    }

//                    String strSDCardPath = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "MenuIcons/";

                    List<ControlObject> controlObjectList = dataCollectionObject.getControls_list();
                    for (int i = 0; i < controlObjectList.size(); i++) {

                        if (controlObjectList.get(i).getControlType().equalsIgnoreCase("MenuControl") && controlObjectList.get(i).getTypeOfButton().equalsIgnoreCase("Icon")) {

                            if (controlObjectList.get(i).getMenuControlObjectList() != null && controlObjectList.get(i).getMenuControlObjectList().size() > 0) {
                                for (int j = 0; j < controlObjectList.get(i).getMenuControlObjectList().size(); j++) {

                                    ControlObject strMenuItem = controlObjectList.get(i).getMenuControlObjectList().get(j);
                                    String[] imgUrlSplit = strMenuItem.getIconPath().split("/");
                                    String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "MenuIcons/";
                                    Log.d(TAG, "MenuIconsUrls: " + strMenuItem.getIconPath() + " - " + strSDCardUrl + imgUrlSplit[imgUrlSplit.length - 1] + " - " + appName);

                                    DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(context, appName, strSDCardUrl);
                                    fromURLTask.execute(strMenuItem.getIconPath());

//                                    downloadDataControl(strMenuItem.getIconPath(), strSDCardUrl + imgUrlSplit[imgUrlSplit.length - 1]);

                                }
                            }
                        }

                        if (controlObjectList.get(i).getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_BUTTON) && controlObjectList.get(i).getTypeOfButton() != null && !controlObjectList.get(i).getTypeOfButton().equalsIgnoreCase("Button")) {

                            if (controlObjectList.get(i).getIconPath() != null) {

                                String[] imgUrlSplit = controlObjectList.get(i).getIconPath().split("/");
                                String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "ButtonIcons/";

                                DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(context, appName, strSDCardUrl);
                                fromURLTask.execute(controlObjectList.get(i).getIconPath());

                            }
                        }


//                    }
                    }
                }
                /*Menu Icons folder and icons download End*/

//            File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName);
//
//            if (!root.exists()) {
//                root.mkdirs();
//            }
                if (appDetails.get(count).getAppIcon() != null && !appDetails.get(count).getAppIcon().equalsIgnoreCase("")) {

                    String appIcon = appDetails.get(count).getAppIcon().trim();
                    if (appDetails.get(count).getDownloadURls() != null && !appDetails.get(count).getDownloadURls().isEmpty()) {
                        String[] imgUrlSplit = appDetails.get(count).getDownloadURls().split(",");
                        for (int urlcount = 0; urlcount < imgUrlSplit.length; urlcount++) {
                            startDownload(appDetails.get(count), imgUrlSplit[urlcount].trim(), appName, appNameForDB, 1);
                        }
                        if (!appIcon.equalsIgnoreCase("NA")) {
                            startDownload(appDetails.get(count), appIcon, appName, appNameForDB, 1);
                        }
                    } else {
                        if (!appIcon.equalsIgnoreCase("NA")) {

                            startDownload(appDetails.get(count), appIcon, appName, appNameForDB, 1);
                        }
//                }
                    }

//            String filePath ="http://182.18.157.124/ImproveUpload/DesignXMLFile/Employee data.xml";

                }
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "createAppFolderAndDownloadFiles", e);
        }
    }

    private void startDownload(AppDetails appDetails, String filePath, String appName, String appNameForDB, int flag) {
        try {
            boolean resultDelete = false;
            String[] imgUrlSplit = filePath.split("/");
            String strSDCardUrl = null;
      /*  if (flag == 1) {
            strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + imgUrlSplit[imgUrlSplit.length - 1];
        } else {
            strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + imgUrlSplit[imgUrlSplit.length - 1];
        }*/
            if (flag == 1) {
                strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/";
            } else {
                strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/";
            }

            if (!isFileExists(strSDCardUrl, imgUrlSplit[imgUrlSplit.length - 1])) {

//            downloadFile(filePath, strSDCardUrl);
                DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(context, appName, strSDCardUrl);
                fromURLTask.execute(filePath);
            } else {
                resultDelete = deleteFileifExists(strSDCardUrl, imgUrlSplit[imgUrlSplit.length - 1]);
//
                if (resultDelete) {
//
//                downloadFile(filePath, strSDCardUrl);
                    DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(context, appName, strSDCardUrl);
                    fromURLTask.execute(filePath);
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "startDownload", e);
        }
    }

    private boolean isFileExists(String sdcardUrl, String filename) {
//            File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
//            return folder1.exists();
        boolean isFileExists = false;
        try {
            File cDir = context.getExternalFilesDir(sdcardUrl);
            File appSpecificExternalDir = new File(cDir.getAbsolutePath(), filename);
            Log.d(TAG, "FileExits: " + appSpecificExternalDir);
            isFileExists = appSpecificExternalDir.exists();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "isFileExists", e);
        }
        return isFileExists;

    }

    private boolean deleteFileifExists(String sdcardUrl, String filename) {

        boolean isFiledToDeleted = false;
        try {
            File cDir = context.getExternalFilesDir(sdcardUrl);
            File appSpecificExternalDir = new File(cDir.getAbsolutePath(), filename);
            Log.d(TAG, "FileExitsDelelte: " + appSpecificExternalDir);
            isFiledToDeleted = appSpecificExternalDir.delete();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "deleteFileifExists", e);
        }
        return isFiledToDeleted;


//            File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
//            return folder1.delete();

    }

    public class DownloadFileFromURLTask extends AsyncTask<String, String, String> {

        String strFileName, strAppName, strSDCardUrl;
        File file, saveFilePath;
        Context context;


        public DownloadFileFromURLTask(Context context, String strAppName, String strSDCardUrl) {
            this.context = context;
            this.strAppName = strAppName;
            this.strSDCardUrl = strSDCardUrl;

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

//                String strSeparatePaths = AppConstants.API_NAME_CHANGE +"/"+strAppName.replaceAll(" ", "_")+"/";
                File cDir = context.getExternalFilesDir(strSDCardUrl);
                saveFilePath = new File(cDir.getAbsolutePath(), strFileName.replaceAll(" ", "_"));
                Log.d(TAG, "AppsListFilesSave: " + saveFilePath);
//                outFile = new File(file, strFileName);
                // Output stream to write file
                OutputStream output = new FileOutputStream(saveFilePath);

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

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @SuppressLint("SdCardPath")
        @Override
        protected void onPostExecute(String file_url) {
            Log.i(TAG, "on post execute!: " + file_url);

            // dismiss progressbars after the file was downloaded
//            if (saveFilePath.exists()) {
            Log.d(TAG, "onPostExecuteAppsList: " + strFileName);
//                ImproveHelper.showToastAlert(context, strFileName + "\n" + " Download successfully!");
//            }
            /*else {
//                ImproveHelper.showToastAlert(context, "No File Exist");
            }*/


        }
    }
}