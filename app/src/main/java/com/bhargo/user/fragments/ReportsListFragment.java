package com.bhargo.user.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Java_Beans.GlobalObjects;
import com.bhargo.user.R;
import com.bhargo.user.adapters.AppsAdapter;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.APIDetails;
import com.bhargo.user.pojos.APIDetailsMArr;
import com.bhargo.user.pojos.AppDetails;
import com.bhargo.user.pojos.AppDetailsMArr;
import com.bhargo.user.pojos.DashBoardArr;
import com.bhargo.user.pojos.DataControls;
import com.bhargo.user.pojos.DataControlsAndApis;
import com.bhargo.user.pojos.DataControlsMArr;
import com.bhargo.user.pojos.GetAllAppModel;
import com.bhargo.user.pojos.GetAllAppNamesData;
import com.bhargo.user.pojos.RefreshMain;
import com.bhargo.user.pojos.RefreshService;
import com.bhargo.user.pojos.ReportsMArr;
import com.bhargo.user.pojos.UserDetails;
import com.bhargo.user.pojos.UserDetailsModel;
import com.bhargo.user.pojos.UserPostDetails;
import com.bhargo.user.screens.BottomNavigationActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.screens.BottomNavigationActivity.iv_appListRefresh;
import static com.bhargo.user.utils.AppConstants.PERMISSION_STORAGE_CODE;
import static com.bhargo.user.utils.AppConstants.SP_REPORTING_USER_DETAILS;
import static com.bhargo.user.utils.AppConstants.SP_USER_DETAILS;
import static com.bhargo.user.utils.AppConstants.SP_USER_POST_DETAILS;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsListFragment extends Fragment implements BottomNavigationActivity.OnBackPressedListener {

    private static final String TAG = "ReportsListFragment";
    public ImproveHelper improveHelper;
    RecyclerView rv_apps;
    GetServices getServices;
    Context context;
    AppsAdapter appsAdapter;
    CustomTextView ct_alNoRecords;
    SessionManager sessionManager;
    ImproveDataBase improveDataBase;
    CustomEditText cet_appsSearch;
    int isRefresh = 0;
    DatabaseReference mFirebaseDatabaseReference;
    String displayAs = "Report";
    private RelativeLayout rl_AppsListMain;
    private GetAllAppModel getAllAppModel;
    private String strOrgId;
    private View rootView;
    private ViewGroup viewGroupContainer;
    private GetAllAppNamesData getAllAppNamesData;
    private List<AppDetails> apiDetailsList;

    public ReportsListFragment() {
        // Required empty public constructor
    }

    public ReportsListFragment(int refresh) {
        // Required empty public constructor
        isRefresh = refresh;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            //Theme
            setBhargoTheme(getActivity(),AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
            // Inflate the layout for this fragment
            rootView = inflater.inflate(R.layout.fragment_apps_list, container, false);
            viewGroupContainer = container;
//        BottomNavigationActivity.ct_FragmentTitle.setText(getActivity().getResources().getString(R.string.reports));
            String[] TabsData=AppConstants.WINDOWS_AVAILABLE.split("\\|");
            BottomNavigationActivity.ct_FragmentTitle.setText(TabsData[2].split("\\^")[2]);

            ((BottomNavigationActivity) getActivity()).setOnBackPressedListener(this);
            context = getActivity();
            improveHelper = new ImproveHelper(getActivity());
            improveDataBase = new ImproveDataBase(getActivity());
            sessionManager = new SessionManager(getActivity());
            getAllAppModel = new GetAllAppModel();
            getAllAppNamesData = new GetAllAppNamesData();
            Log.d(TAG, "onCreate2: " + sessionManager.getUserDataFromSession().getUserID());
            Log.d(TAG, "getOrgIdFromSession: " + sessionManager.getOrgIdFromSession());
            strOrgId = sessionManager.getOrgIdFromSession();
            mFirebaseDatabaseReference = FirebaseDatabase.getInstance("https://bhargocommunication-8c36e.firebaseio.com/").getReference(sessionManager.getOrgIdFromSession());
//        mFirebaseDatabaseReference = FirebaseDatabase.getInstance("https://improvecommunication-c08c9.firebaseio.com").getReference(sessionManager.getOrgIdFromSession());
            //Create Table dynamically
            //improveDataBase.createUserTable("APP_TABLE");

            getServices = RetrofitUtils.getUserService();

            ct_alNoRecords = rootView.findViewById(R.id.ct_alNoRecords);
            rv_apps = rootView.findViewById(R.id.rv_apps);
            rl_AppsListMain = rootView.findViewById(R.id.rl_AppsListMain);
            cet_appsSearch = rootView.findViewById(R.id.cet_appsSearch);
            cet_appsSearch.setVisibility(View.GONE);
            CardView cv_profile = rootView.findViewById(R.id.cv_profile);
            cv_profile.setBackgroundResource(R.drawable.profile_gradient);
            ImageView iv_profile = rootView.findViewById(R.id.iv_profile);
            CustomTextView tv_name = rootView.findViewById(R.id.tv_name);
            CustomTextView tv_mobile_no = rootView.findViewById(R.id.tv_mobile_no);
            CustomTextView tv_designation = rootView.findViewById(R.id.tv_designation);
            CustomTextView tv_location = rootView.findViewById(R.id.tv_location);
            tv_name.setText(sessionManager.getUserDetailsFromSession().getName());
            tv_mobile_no.setText(sessionManager.getUserDetailsFromSession().getPhoneNo());
            tv_designation.setText(sessionManager.getPostsFromSessionPostName());
//        String user_location = improveHelper.getLocationLevel(sessionManager.getUserPostDetailsFromSession(), sessionManager.getPostsFromSession());
//        tv_location.setText(user_location);
            if (isNetworkStatusAvialable(context)) {
                Glide.with(context).load(sessionManager.getUserDetailsFromSession().getProfilePIc()).placeholder(ContextCompat.getDrawable(context, R.drawable.icon_user_profile_image)).into(iv_profile);
            }

            loadGlobalObject(strOrgId);

        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_apps.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView*/
            rv_apps.setLayoutManager(new GridLayoutManager(context, 2));

            appsAdapter = new AppsAdapter(getActivity(), new ArrayList<AppDetails>(), false,false);

            improveHelper.showProgressDialog(getActivity().getResources().getString(R.string.loading_reports));

            if (strOrgId != null) {
                Log.d(TAG, "onCreateViewORGId: " + strOrgId);
                getAllAppNamesData.setOrgId(strOrgId);
    //            getAllAppNamesData.setUserID(ImproveHelper.getUserDetails(getActivity()).getUserId());
                getAllAppNamesData.setUserId(sessionManager.getUserDataFromSession().getUserID());
                getAllAppNamesData.setAppType("User");
                if (isRefresh == 0) {
                    orgListApiOnlineOffLine(strOrgId, sessionManager.getPostsFromSession());
                } else {
                    refreshCheckingAPi();
                }
            }

            cet_appsSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    // filter your list from your input
                    filter(s.toString());
                }
            });

            firebaseAppsListener();


        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "onCreateView", e);

        }
        return rootView;
    }


    private void orgListApiOnlineOffLine(String strOrgId, String strPostId) {
        try {
            List<AppDetails> appDetailsList = improveDataBase.getDataFromAppsListTable(strOrgId, sessionManager.getUserDataFromSession().getUserID(), sessionManager.getUserTypeIdsFromSession(), strPostId, displayAs,sessionManager.getUserTypeIdsFromSession());

            if (appDetailsList != null && appDetailsList.size() > 0) {
                apiDetailsList = appDetailsList;
                ct_alNoRecords.setVisibility(View.GONE);
                rv_apps.setVisibility(View.VISIBLE);
//            cet_appsSearch.setVisibility(View.VISIBLE);
                cet_appsSearch.setVisibility(View.GONE); // changed

                appsAdapter = new AppsAdapter(getActivity(), appDetailsList, false,false);
                rv_apps.setAdapter(appsAdapter);
                improveHelper.dismissProgressDialog();
                Log.d(TAG, "orgListApiOnlineOffLine: " + "FirstTime FromDB");
            } else {
                if (isNetworkStatusAvialable(getActivity())) {
                    getDataControlList(getAllAppNamesData);
                } else {
                    improveHelper.snackBarAlertFragment(getActivity(), viewGroupContainer);
                    improveHelper.dismissProgressDialog();
                    ct_alNoRecords.setVisibility(View.VISIBLE);
                    cet_appsSearch.setVisibility(View.GONE);
                    rv_apps.setVisibility(View.GONE);

                }
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "orgListApiOnlineOffLine", e);
        }
    }


    private void getDataControlList(final GetAllAppNamesData getAllAppNamesData) {
        try {
            Call<DataControlsAndApis> getAllAppNamesDataCall = getServices.iGetDataControlsList(sessionManager.getAuthorizationTokenId(),getAllAppNamesData);
            getAllAppNamesDataCall.enqueue(new Callback<DataControlsAndApis>() {

                @Override
                public void onResponse(Call<DataControlsAndApis> call, Response<DataControlsAndApis> response) {


                    if(response.body() != null && response.body().getAPIDetails() != null) {
                        List<APIDetails> apiDetailsList = new ArrayList<>();
                        apiDetailsList = response.body().getAPIDetails();
                        if (apiDetailsList != null && apiDetailsList.size() > 0) {
                            improveDataBase.deleteAPINamesData();
                            improveDataBase.insertIntoAPINamesTable(apiDetailsList, strOrgId, sessionManager.getUserDataFromSession().getUserID());
                        }
                    }

                    if(response.body() != null && response.body().getDataControls() != null) {
                        List<DataControls> dataControls = response.body().getDataControls();

                        if (dataControls != null && dataControls.size() != 0) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//Ask Permission
                                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                    requestPermissions(permissions, PERMISSION_STORAGE_CODE);
                                } else {
                                    createAppFolderAndDownloadDataControls(dataControls);
                                }
                            } else {
                                createAppFolderAndDownloadDataControls(dataControls);
                            }
                            for (int i = 0; i < dataControls.size(); i++) {

                                improveDataBase.insertIntoDataControlTable(dataControls.get(i), strOrgId, sessionManager.getUserDataFromSession().getUserID());
                            }
                        } else {
                            mAppsListAPI(getAllAppNamesData);
                        }
                    }else {
                        mAppsListAPI(getAllAppNamesData);
                    }

                }

                @Override
                public void onFailure(Call<DataControlsAndApis> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    improveHelper.improveException(getActivity(), TAG, "getDataControlList", (Exception) t);
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "getDataControlList", e);
        }
    }


    private void mAppsListAPI(final GetAllAppNamesData getAllAppNamesData) {
        try {
            Call<GetAllAppModel> getAllAppNamesDataCall = getServices.iGetAllAppsList(sessionManager.getAuthorizationTokenId(),getAllAppNamesData);
            getAllAppNamesDataCall.enqueue(new Callback<GetAllAppModel>() {
                @Override
                public void onResponse(Call<GetAllAppModel> call, Response<GetAllAppModel> response) {

                    if (response.body() != null) {
//                    ImproveHelper.showToast(getActivity(), getAllAppModel.getMessage());
                        getAllAppModel = response.body();
                        String strNewVersionToOldVersion;
                        if (getAllAppModel.getAppDetails() != null && getAllAppModel.getAppDetails().size() > 0) {
//                    improveHelper.dismissProgressDialog();
//                        cet_appsSearch.setVisibility(View.VISIBLE); // changed
                            cet_appsSearch.setVisibility(View.GONE);
                            ct_alNoRecords.setVisibility(View.GONE);
                            rv_apps.setVisibility(View.VISIBLE);
                            iv_appListRefresh.setVisibility(View.VISIBLE);

                            improveDataBase.deleteAppsListData(getAllAppNamesData.getOrgId(), sessionManager.getUserDataFromSession().getUserID());

                            improveDataBase.insertIntoAppsListTable(getAllAppModel.getAppDetails(), getAllAppNamesData.getOrgId(), sessionManager.getUserDataFromSession().getUserID());

//                    appDetailsList = getAllAppModel.getAppDetails();
                            apiDetailsList = improveDataBase.getDataFromAppsListTable(strOrgId, sessionManager.getUserDataFromSession().getUserID(), sessionManager.getUserTypeIdsFromSession(), sessionManager.getPostsFromSession(), displayAs,sessionManager.getUserTypeIdsFromSession());

                            if (apiDetailsList != null && apiDetailsList.size() > 0) {
                                appsAdapter = new AppsAdapter(getActivity(), apiDetailsList, false,false);

                                rv_apps.setAdapter(appsAdapter);

                            } else {
                                improveHelper.dismissProgressDialog();
                                ct_alNoRecords.setVisibility(View.VISIBLE);
                                cet_appsSearch.setVisibility(View.GONE);
                                rv_apps.setVisibility(View.GONE);
                                iv_appListRefresh.setVisibility(View.GONE);
                            }
                            updateFirebaseStatus();
//                    appsAdapter.notifyDataSetChanged();

                            // working
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
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
                            ct_alNoRecords.setVisibility(View.VISIBLE);
                            cet_appsSearch.setVisibility(View.GONE);
                            rv_apps.setVisibility(View.GONE);
                            iv_appListRefresh.setVisibility(View.GONE);

                        }
                    } else {

                        improveHelper.dismissProgressDialog();
                        ct_alNoRecords.setVisibility(View.VISIBLE);
                        cet_appsSearch.setVisibility(View.GONE);
                        rv_apps.setVisibility(View.GONE);
                        iv_appListRefresh.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onFailure(Call<GetAllAppModel> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "mAppsListAPI", e);
        }
    }

    private void createAppFolderAndDownloadDataControls(List<DataControls> dataControlsList) {

        try {
            for (int count = 0; count < dataControlsList.size(); count++) {

                String filePath = dataControlsList.get(count).getTextFilePath().trim();

                if (filePath != null && !filePath.isEmpty()) {
// check download
                    startDownloadDataControl(dataControlsList.get(count), filePath, "", 0, 0);

                }
            }
            mAppsListAPI(getAllAppNamesData);
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "createAppFolderAndDownloadDataControls", e);
        }
    }


    public void createAppFolderAndDownloadFiles(List<AppDetails> appDetails) {
        try {
            for (int count = 0; count < appDetails.size(); count++) {
                String appNameForDB = appDetails.get(count).getAppName();
                String appName = appDetails.get(count).getAppName().replaceAll(" ", "_");


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
            improveHelper.improveException(getActivity(), TAG, "createAppFolderAndDownloadFiles", e);
        }
    }


    public void loadGlobalObject(String orgID) {
        try {
            GlobalObjects Gobj = new GlobalObjects();
            Gson gson = new Gson();

            String jsonUserDeatils = PrefManger.getSharedPreferencesString(getActivity(), SP_USER_DETAILS, "");
            UserDetails userDetailsObj = gson.fromJson(jsonUserDeatils, UserDetails.class);

            String jsonUserPostDetails = PrefManger.getSharedPreferencesString(getActivity(), SP_USER_POST_DETAILS, "");
//        List<UserPostDetails> userPostDetailsObj = gson.fromJson(jsonUserPostDetails, UserPostDetails.class);
            if (jsonUserPostDetails != null && !jsonUserPostDetails.isEmpty()) {
                Type collectionType = new TypeToken<Collection<UserPostDetails>>() {
                }.getType();
                List<UserPostDetails> upd = gson.fromJson(jsonUserPostDetails, collectionType);
                if (upd != null && upd.size() > 0) {
                    Log.d(TAG, "loadGlobalObjectPostName: " + upd.get(0).getName());
                }
            }

            String jsonReportingUserDeatils = PrefManger.getSharedPreferencesString(getActivity(), SP_REPORTING_USER_DETAILS, "");
            UserDetailsModel.ReportingUserDeatils reportingUserDetailsObj = gson.fromJson(jsonReportingUserDeatils, UserDetailsModel.ReportingUserDeatils.class);
            Gobj.setAppLanguage(ImproveHelper.getLocale(getActivity()));
            if (userDetailsObj != null) {
                Gobj.setUser_Role(userDetailsObj.getRole());
                Gobj.setUser_ID(userDetailsObj.getUserId());
                Gobj.setUser_Name(userDetailsObj.getName());
                Gobj.setUser_MobileNo(userDetailsObj.getPhoneNo());
                Gobj.setUser_Email(userDetailsObj.getEmail());
                Gobj.setUser_Desigination(userDetailsObj.getDesignation());
                Gobj.setUser_Department(userDetailsObj.getDepartment());
                Gobj.setUser_location(userDetailsObj.getLocationLevel());
                Gobj.setLocatonLevel(userDetailsObj.getLocatonLevel());
                Gobj.setSublocations(userDetailsObj.getSublocations());
                Gobj.setUser_PostID(sessionManager.getPostsFromSession());
                Gobj.setUser_PostName(sessionManager.getPostsFromSessionPostName());
                Gobj.setReporting_PostID(sessionManager.getPostsSessionReportingPostID());
                Gobj.setReporting_PostDepartmentID(sessionManager.getPostsSessionReportingPostDepartmentID());
            }
            if (reportingUserDetailsObj != null) {
                Gobj.setReporter_Role(reportingUserDetailsObj.getRole());
                Gobj.setReporter_ID(reportingUserDetailsObj.getUserId());
                Gobj.setReporter_Name(reportingUserDetailsObj.getName());
                Gobj.setReporter_MobileNo(reportingUserDetailsObj.getPhoneNo());
                Gobj.setReporter_Email(reportingUserDetailsObj.getEmail());
                Gobj.setReporter_Desigination(reportingUserDetailsObj.getDesignation());
                Gobj.setReporter_Department(reportingUserDetailsObj.getDepartment());
                Gobj.setReporter_Level(reportingUserDetailsObj.getLocationLevel());
            }

            AppConstants.GlobalObjects = Gobj;
            AppConstants.GlobalObjects.setOrg_Name(orgID);
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "loadGlobalObject", e);
        }
    }

    private void startDownloadDataControl(DataControls dataControls, String filePath, String appName, int flag, int refreshFlag) {
        try {
            boolean resultDelete = false;
            String[] imgUrlSplit = filePath.split("/");
            String strSDCardUrl = null;

            if (flag == 1) {
                strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/";
            } else {
                strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/";
            }

            if (!isFileExists(strSDCardUrl, imgUrlSplit[imgUrlSplit.length - 1])) {


                DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl);
                fromURLTask.execute(filePath);
            } else {
                resultDelete = deleteFileifExists(strSDCardUrl, imgUrlSplit[imgUrlSplit.length - 1]);
                if (resultDelete) {


                    DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl);
                    fromURLTask.execute(filePath);
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "startDownloadDataControl", e);
        }
    }

    public void downloadDataControl(String filePath, String strSDCardUrl) {
        try {
            if (!filePath.contains(" ") && filePath.contains("http")) {
                try {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(filePath));
                    if (request != null) {
//                    request.setVisibleInDownloadsUi(false);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
//                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            request.setDestinationInExternalFilesDir(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath(), strSDCardUrl);
                        } else {
                            request.setDestinationInExternalPublicDir("", strSDCardUrl);
                        }

                        DownloadManager downloadManagerFiles = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                        downloadManagerFiles.enqueue(request);
//                    long downloadId = downloadManagerFiles.enqueue(request);

//                    Log.d(TAG, "startDownload: " + downloadManagerFiles);

                /*downloadManagerDataControl = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                downloaId = downloadManagerDataControl.enqueue(request);

                Log.d(TAG, "startDownload: " + downloadManagerDataControl);*/
                    }
                } catch (Exception e) {
                    Log.d(TAG, "downloadDataControlException: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "downloadDataControl", e);
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
                DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl);
                fromURLTask.execute(filePath);
            } else {
                resultDelete = deleteFileifExists(strSDCardUrl, imgUrlSplit[imgUrlSplit.length - 1]);
//
                if (resultDelete) {
//
//                downloadFile(filePath, strSDCardUrl);
                    DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl);
                    fromURLTask.execute(filePath);
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "startDownload", e);
        }
    }


    public void downloadFile(String filePath, String strSDCardUrl) {
        try {
//        DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(strSDCardUrl);
//        fromURLTask.execute(filePath);

            if (!filePath.contains(" ") && filePath.contains("http")) {
                try {
                    DownloadManager downloadManagerFiles = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(filePath));
                    if (request != null) {
//                request.setVisibleInDownloadsUi(false);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);

//                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//                request.setTitle(getActivity().getResources().getString(R.string.app_name));
//                request.setDescription("Downloading Files...");
//                    request.allowScanningByMediaScanner();

//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//            request.setDestinationInExternalFilesDir(getContext(), Environment.getExternalStorageDirectory().getAbsolutePath(), strSDCardUrl);
//            request.setDestinationInExternalPublicDir("", strSDCardUrl);
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            request.setDestinationInExternalFilesDir(getContext(), Environment.getExternalStorageDirectory().getAbsolutePath(), strSDCardUrl);

                        } else {
                            request.setDestinationInExternalPublicDir("", strSDCardUrl);

                        }

                        downloadManagerFiles.enqueue(request);
//                downloaId = downloadManagerFiles.enqueue(request);

//                Log.d(TAG, "startDownload: " + downloadManagerFiles);

                    }
                } catch (Exception e) {
                    Log.d(TAG, "downloadFileException: " + e.toString());
                }

            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "downloadFile", e);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {


        try {
            switch (requestCode) {

                case PERMISSION_STORAGE_CODE: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        createAppFolderAndDownloadFiles(getAllAppModel.getAppDetails());
                    } else {
                        Toast.makeText(getActivity(), "Storage Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "onRequestPermissionsResult", e);

        }
    }

    private boolean isFileExists(String sdcardUrl, String filename) {
//            File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
//            return folder1.exists();
        boolean isExists = false;
        try {
            File cDir = getActivity().getExternalFilesDir(sdcardUrl);
            File appSpecificExternalDir = new File(cDir.getAbsolutePath(), filename);
            Log.d(TAG, "FileExits: " + appSpecificExternalDir);
            isExists = appSpecificExternalDir.exists();
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "isFileExists", e);
        }
        return isExists;
    }

    private boolean deleteFileifExists(String sdcardUrl, String filename) {

        boolean isDeleteFile = false;
        try {
            File cDir = getActivity().getExternalFilesDir(sdcardUrl);
            File appSpecificExternalDir = new File(cDir.getAbsolutePath(), filename);
            Log.d(TAG, "FileExitsDelelte: " + appSpecificExternalDir);
            isDeleteFile = appSpecificExternalDir.delete();
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "deleteFileifExists", e);
        }
        return isDeleteFile;

    }


    @Override
    public void onFragmentBackPress() {
        ImproveHelper.replaceFragment(rootView, new AppsListFragment(), "AppsListFragment");
    }

    public void filter(String text) {
        try {
            if (!text.isEmpty()) {
                List<AppDetails> temp = new ArrayList();
                if (apiDetailsList != null) {
                    for (AppDetails d : apiDetailsList) {
                        //or use .equal(text) with you want equal match
                        //use .toLowerCase() for better matches
                        if (d.getAppName().toLowerCase().contains(text.toLowerCase())) {
                            temp.add(d);
                        }
                    }
                }
                //Update recyclerView
                appsAdapter.updateList(temp);
            } else {
                List<AppDetails> newList = new ArrayList<>();
                appsAdapter.updateList(newList);
                appsAdapter.updateList(apiDetailsList);
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "filter", e);
        }
    }

    private void loadAppsListDB() {

//        improveHelper.showProgressDialog(getActivity().getResources().getString(R.string.please_wait));
        if (isRefresh == 0) {
            apiDetailsList = improveDataBase.getDataFromAppsListTable(strOrgId, sessionManager.getUserDataFromSession().getUserID(), sessionManager.getUserTypeIdsFromSession(), sessionManager.getPostsFromSession(), displayAs,sessionManager.getUserTypeIdsFromSession());
            if (apiDetailsList.size() == 0) {
                /*Apps List API*/
                mAppsListAPI(getAllAppNamesData);
            } else {
//                cet_appsSearch.setVisibility(View.VISIBLE);
                cet_appsSearch.setVisibility(View.GONE); // changed
                ct_alNoRecords.setVisibility(View.GONE);
                rv_apps.setVisibility(View.VISIBLE);
                appsAdapter = new AppsAdapter(getActivity(), apiDetailsList, false,false);
                rv_apps.setAdapter(appsAdapter);
                improveHelper.dismissProgressDialog();
            }

        } else {

//            {"UserID":"GCBF1","OrgId":"GCBF","AppNames":[{"pagename":"","version",""},{"pagename":"","version",""}],
//                "DataControls":[{"controlname":"","version",""},{"controlname":"","version",""}]
//                "APINames":[{"apiname":"","version",""},{"apiname":"","version",""}]}

        }
    }

    private void mAppsListRefreshAPI(final RefreshMain refreshMain) {
        try {
            Call<RefreshService> refreshServiceCall = getServices.getRefreshService(sessionManager.getAuthorizationTokenId(),refreshMain);

            refreshServiceCall.enqueue(new Callback<RefreshService>() {
                @Override
                public void onResponse(Call<RefreshService> call, Response<RefreshService> response) {
//                try {

                    if (response.body() != null) {

//                    Gson gson = new Gson();
//                    String jsonObject = gson.toJson(response.body());
//                    Log.d(TAG, "onRefreshJSON: " + jsonObject);

                        //AppsList
                        if (response.body().getPageName() != null && response.body().getPageName().size() > 0) {

                            List<AppDetails> appDetailsListResp = response.body().getPageName();
                            if (appDetailsListResp != null && appDetailsListResp.size() > 0) {
                                for (int i = 0; i < appDetailsListResp.size(); i++) {

                                    if (appDetailsListResp.get(i).getZ_Status_flag().equalsIgnoreCase("Update")) {
                                        String refRespAppName = appDetailsListResp.get(i).getAppName();
                                        List<AppDetails> appDetailsListUpdate = new ArrayList<>();
                                        appDetailsListUpdate.add(appDetailsListResp.get(i));
                                        improveDataBase.updateAppsList(appDetailsListResp.get(i), refRespAppName, strOrgId, sessionManager.getUserDataFromSession().getUserID(),"");
                                        createFolderPermissions(appDetailsListUpdate);
                                    } else if (appDetailsListResp.get(i).getZ_Status_flag().equalsIgnoreCase("Deleted")) {
                                        String refRespAppNameDelete = appDetailsListResp.get(i).getAppName();
                                        improveDataBase.updateAppsList(appDetailsListResp.get(i), refRespAppNameDelete, strOrgId, sessionManager.getUserDataFromSession().getUserID(),"");
                                    } else if (appDetailsListResp.get(i).getZ_Status_flag().equalsIgnoreCase("Insert")) { // newly added files
                                        List<AppDetails> appDetailsListInsert = new ArrayList<>();
                                        appDetailsListInsert.add(appDetailsListResp.get(i));
                                        improveDataBase.insertIntoAppsListTable(appDetailsListInsert, strOrgId, sessionManager.getUserDataFromSession().getUserID());
                                        createFolderPermissions(appDetailsListInsert);
                                    }
                                }
                            }
                        }

                        // DataControls
                        if (response.body().getDatacontrol() != null && response.body().getDatacontrol().size() > 0) {

                            List<DataControls> dataControlsResp = response.body().getDatacontrol();
                            if (dataControlsResp != null && dataControlsResp.size() > 0) {
                                for (int i = 0; i < dataControlsResp.size(); i++) {

                                    if (dataControlsResp.get(i).getZ_Status_flag().equalsIgnoreCase("Update")) {
                                        String refRespDCUpdate = dataControlsResp.get(i).getControlName();
                                        improveDataBase.updateDataControlsList(dataControlsResp.get(i), refRespDCUpdate, strOrgId, sessionManager.getUserDataFromSession().getUserID());
                                        String filePath = dataControlsResp.get(i).getTextFilePath().trim();
                                        startDownloadDataControl(dataControlsResp.get(i), filePath, "", 0, 1);
                                    } else if (dataControlsResp.get(i).getZ_Status_flag().equalsIgnoreCase("Deleted")) {
                                        String refRespDCDelete = dataControlsResp.get(i).getControlName();
                                        improveDataBase.updateDataControlsList(dataControlsResp.get(i), refRespDCDelete, strOrgId, sessionManager.getUserDataFromSession().getUserID());
                                    } else if (dataControlsResp.get(i).getZ_Status_flag().equalsIgnoreCase("Insert")) { // newly added files
                                        improveDataBase.insertIntoDataControlTable(dataControlsResp.get(i), strOrgId, sessionManager.getUserDataFromSession().getUserID());
                                        String filePath = dataControlsResp.get(i).getTextFilePath().trim();
                                        startDownloadDataControl(dataControlsResp.get(i), filePath, "", 0, 1);

                                    }
                                }
                            }
                        }

                        // API Names
                        if (response.body().getApiname() != null && response.body().getApiname().size() > 0) {

                            List<APIDetails> apiNamesListResp = response.body().getApiname();
                            if (apiNamesListResp != null && apiNamesListResp.size() > 0) {
                                for (int i = 0; i < apiNamesListResp.size(); i++) {

//                                    APIDetails apiNamesList = improveDataBase.getDataFromAPINamesRefresh(refResp, strOrgId, sessionManager.getUserDataFromSession().getUserID());

                                    if (apiNamesListResp.get(i).getZ_Status_flag().equalsIgnoreCase("Update")) {
                                        String refRespAPIUpdate = apiNamesListResp.get(i).getServiceName();
                                        improveDataBase.updateFormApiNames(apiNamesListResp.get(i), refRespAPIUpdate, strOrgId, sessionManager.getUserDataFromSession().getUserID());
                                    } else if (apiNamesListResp.get(i).getZ_Status_flag().equalsIgnoreCase("Deleted")) {
                                        String refRespDCDelete = apiNamesListResp.get(i).getServiceName();
                                        improveDataBase.updateFormApiNames(apiNamesListResp.get(i), refRespDCDelete, strOrgId, sessionManager.getUserDataFromSession().getUserID());
                                    } else if (apiNamesListResp.get(i).getZ_Status_flag().equalsIgnoreCase("Insert")) { // newly added files
                                        List<APIDetails> apiDetailsList = new ArrayList<>();
                                        apiDetailsList.add(apiNamesListResp.get(i));
                                        improveDataBase.insertIntoAPINamesTable(apiDetailsList, strOrgId, sessionManager.getUserDataFromSession().getUserID());
                                    }
                                }
                            }
                        }

                        // DashBoard
                        if (response.body().getDashBoard() != null && response.body().getDashBoard().size() > 0) {

                            List<AppDetails> dashboardListResp = response.body().getReport();
                            if (dashboardListResp != null && dashboardListResp.size() > 0) {
                                for (int i = 0; i < dashboardListResp.size(); i++) {

                                    if (dashboardListResp.get(i).getZ_Status_flag().equalsIgnoreCase("Update")) {
                                        String refRespAppName = dashboardListResp.get(i).getAppName();
                                        List<AppDetails> dashBoardListUpdate = new ArrayList<>();
                                        dashBoardListUpdate.add(dashboardListResp.get(i));
                                        improveDataBase.updateAppsList(dashBoardListUpdate.get(i), refRespAppName, strOrgId, sessionManager.getUserDataFromSession().getUserID(),"");
//                                        createFolderPermissions(dashBoardListUpdate);
                                    } else if (dashboardListResp.get(i).getZ_Status_flag().equalsIgnoreCase("Deleted")) {
                                        String refRespAppName = dashboardListResp.get(i).getAppName();
                                        improveDataBase.updateAppsList(dashboardListResp.get(i), refRespAppName, strOrgId, sessionManager.getUserDataFromSession().getUserID(),"");
                                    } else if (dashboardListResp.get(i).getZ_Status_flag().equalsIgnoreCase("Insert")) { // newly added files
                                        List<AppDetails> dashBoardListInsert = new ArrayList<>();
                                        dashBoardListInsert.add(dashboardListResp.get(i));
                                        improveDataBase.insertIntoAppsListTable(dashBoardListInsert, strOrgId, sessionManager.getUserDataFromSession().getUserID());
//                                        createFolderPermissions(dashBoardListInsert);
                                    }
                                }
                            }
                        }

                        // Reports
                        if (response.body().getReport() != null && response.body().getReport().size() > 0) {

                            List<AppDetails> reportsListResp = response.body().getReport();
                            if (reportsListResp != null && reportsListResp.size() > 0) {
                                for (int i = 0; i < reportsListResp.size(); i++) {

                                    if (reportsListResp.get(i).getZ_Status_flag().equalsIgnoreCase("Update")) {
                                        String refRespAppName = reportsListResp.get(i).getAppName();
                                        List<AppDetails> reportsListUpdate = new ArrayList<>();
                                        reportsListUpdate.add(reportsListResp.get(i));
                                        improveDataBase.updateAppsList(reportsListUpdate.get(i), refRespAppName, strOrgId, sessionManager.getUserDataFromSession().getUserID(),"");
//                                    createFolderPermissions(reportsListUpdate);
                                    } else if (reportsListResp.get(i).getZ_Status_flag().equalsIgnoreCase("Deleted")) {
                                        String refRespAppName = reportsListResp.get(i).getAppName();
                                        improveDataBase.updateAppsList(reportsListResp.get(i), refRespAppName, strOrgId, sessionManager.getUserDataFromSession().getUserID(),"");
                                    } else if (reportsListResp.get(i).getZ_Status_flag().equalsIgnoreCase("Insert")) { // newly added files
                                        List<AppDetails> reportsListInsert = new ArrayList<>();
                                        reportsListInsert.add(reportsListResp.get(i));
                                        improveDataBase.insertIntoAppsListTable(reportsListInsert, strOrgId, sessionManager.getUserDataFromSession().getUserID());
//                                    createFolderPermissions(reportsListInsert);
                                    }
                                }
                            }
                        }

                        loadAppsAfterRefresh();
                        updateFirebaseStatus();
                        AppConstants.PROGRESS_REPORT = 0;
                    } else {
                        improveHelper.dismissProgressDialog();
                        Log.d(TAG, "onResponseRefresh: " + "No Response");
                    }
                /*} catch (Exception e) {
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "onResponseRefreshException: " + e);
                }*/
                }

                @Override
                public void onFailure(Call<RefreshService> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "onResponseRefreshFail: " + t.toString());
                    improveHelper.improveException(getActivity(), TAG, "mAppsListRefreshAPI", (Exception) t);
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "mAppsListRefreshAPI", e);
        }
    }

    public void loadAppsAfterRefresh() {
        try {
            apiDetailsList = improveDataBase.getDataFromAppsListTable(strOrgId, sessionManager.getUserDataFromSession().getUserID(), sessionManager.getUserTypeIdsFromSession(), sessionManager.getPostsFromSession(), displayAs,sessionManager.getUserTypeIdsFromSession());
            if (apiDetailsList != null && apiDetailsList.size() > 0) {
//            cet_appsSearch.setVisibility(View.VISIBLE);
                cet_appsSearch.setVisibility(View.GONE); // changed
                ct_alNoRecords.setVisibility(View.GONE);
                rv_apps.setVisibility(View.VISIBLE);
                appsAdapter = new AppsAdapter(getActivity(), apiDetailsList, false,false);
                rv_apps.setAdapter(appsAdapter);
                appsAdapter.notifyDataSetChanged();
                improveHelper.dismissProgressDialog();
            } else {
                improveHelper.dismissProgressDialog();
                cet_appsSearch.setVisibility(View.GONE);
                ct_alNoRecords.setVisibility(View.VISIBLE);
                rv_apps.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "loadAppsAfterRefresh", e);
        }
    }

    public void refreshCheckingAPi() {
        try {
            List<AppDetails> appDetailsList_QueryNotDistributed = improveDataBase.getDataFromAppsListTableNotDistributed(strOrgId, sessionManager.getUserDataFromSession().getUserID());
            List<AppDetails> appDetailsList = improveDataBase.getDataFromAppsListTableRefresh(strOrgId, sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession(), displayAs,sessionManager.getUserTypeIdsFromSession());
            List<DataControls> appDataControlsList = improveDataBase.getDataControlsList(strOrgId, sessionManager.getUserDataFromSession().getUserID());
            List<APIDetails> apiNamesList = improveDataBase.getDataFromAPINames(strOrgId, sessionManager.getUserDataFromSession().getUserID());


            RefreshMain refreshMain = new RefreshMain();
            refreshMain.setUserID(sessionManager.getUserDataFromSession().getUserID());
            refreshMain.setOrgId(strOrgId);

            // QueryNot Distributed
            List<AppDetailsMArr> appDetailsMArrArrayListQ = new ArrayList<>();
            for (int i = 0; i < appDetailsList_QueryNotDistributed.size(); i++) {
                AppDetailsMArr appDetailsMArrQ = new AppDetailsMArr();
                appDetailsMArrQ.setPagename(appDetailsList_QueryNotDistributed.get(i).getAppName());
                appDetailsMArrQ.setVersion(appDetailsList_QueryNotDistributed.get(i).getAppVersion());
                appDetailsMArrArrayListQ.add(appDetailsMArrQ);
            }
            refreshMain.setQueryFormArr(appDetailsMArrArrayListQ);

            // All Apps Apps datacollection and child
            List<AppDetailsMArr> appDetailsMArrArrayList = new ArrayList<>();
            for (int i = 0; i < appDetailsList.size(); i++) {

                if (appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.DATA_COLLECTION)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.AUTO_REPORTS)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.QUERY_FORM)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.CHILD_FORM)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.WEB_Link)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.REPORT)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.MULTI_FORM)) {
                    Log.d(TAG, "refreshSendAppName: " + appDetailsList.get(i).getAppName());
                    AppDetailsMArr appDetailsMArr = new AppDetailsMArr();
                    appDetailsMArr.setPagename(appDetailsList.get(i).getAppName());
                    appDetailsMArr.setVersion(appDetailsList.get(i).getAppVersion());
                    appDetailsMArrArrayList.add(appDetailsMArr);
                }
            }
            refreshMain.setAppArr(appDetailsMArrArrayList);

            // Data controls
            List<DataControlsMArr> dataControlsMArrArrayList = new ArrayList<>();
            for (int i = 0; i < appDataControlsList.size(); i++) {
                Log.d(TAG, "refreshSendDCName: " + appDataControlsList.get(i).getControlName());
                DataControlsMArr dataControlsMArr = new DataControlsMArr();
                dataControlsMArr.setControl_name(appDataControlsList.get(i).getControlName());
                dataControlsMArr.setVersion(appDataControlsList.get(i).getVersion());
                dataControlsMArrArrayList.add(dataControlsMArr);
            }

            refreshMain.setDataControlsArr(dataControlsMArrArrayList);

            //API
            List<APIDetailsMArr> apiDetailsMArrArrayList = new ArrayList<>();
            for (int i = 0; i < apiNamesList.size(); i++) {
                Log.d(TAG, "refreshSendAPiName: " + apiNamesList.get(i).getServiceName());
                APIDetailsMArr apiDetailsMArr = new APIDetailsMArr();
                apiDetailsMArr.setServiceName(apiNamesList.get(i).getServiceName());
                apiDetailsMArr.setVersion(apiNamesList.get(i).getVersion());
                apiDetailsMArrArrayList.add(apiDetailsMArr);
            }

            refreshMain.setAPIArr(apiDetailsMArrArrayList);

            //DashBoard
            List<DashBoardArr> dashBoardArrs = new ArrayList<>();
            refreshMain.setDashBoardArr(dashBoardArrs);

            // Reports
            List<ReportsMArr> reportsListArrs = new ArrayList<>();
            refreshMain.setReportArr(reportsListArrs);

            Gson gson = new Gson();
            String jsonObjectRefresh = gson.toJson(refreshMain);
            Log.d(TAG, "SendRefreshData: " + jsonObjectRefresh);

            /* App Refresh APi*/
            mAppsListRefreshAPI(refreshMain);
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "refreshCheckingAPi", e);
        }
    }

    public void createFolderPermissions(List<AppDetails> appDetailsList) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//Ask Permission
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions, PERMISSION_STORAGE_CODE);
                } else {

//                                                mPrepareUpdatedAppsList(appDetailsList);                                       //check download
//                                        createAppFolderAndDownloadFiles(appDetailsList);
                    createAppFolderAndDownloadFiles(appDetailsList);
                }
            } else {
                // check download
//                                            mPrepareUpdatedAppsList(appDetailsListResp);
                createAppFolderAndDownloadFiles(appDetailsList);
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "createFolderPermissions", e);
        }
    }

    private void firebaseAppsListener() {
        try {
            mFirebaseDatabaseReference.child("Users").child(sessionManager.getUserChatID()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        com.bhargo.user.pojos.firebase.UserDetails userDetails = dataSnapshot.getValue(com.bhargo.user.pojos.firebase.UserDetails.class);
//                    String status = userDetails.getStatus();

                        int statusApp = userDetails.getAppStatus();

                        if (statusApp != 0 && statusApp == 1) {
//                        Toast.makeText(getActivity(),"Update",Toast.LENGTH_SHORT).show();
                            refreshCheckingAPi();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, "onCancelledAppStatus: " + databaseError.toString());
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "firebaseAppsListener", e);
        }
    }

    public void updateFirebaseStatus() {
        try{
        context = getActivity();
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
            improveHelper.improveException(getActivity(), TAG, "updateFirebaseStatus", e);
        }
    }
//    public void updateFirebaseStatus() {
//        context = getActivity();
//        sessionManager = new SessionManager(context);
//        mFirebaseDatabaseReference = FirebaseDatabase.getInstance("https://bhargocommunication-8c36e.firebaseio.com").getReference(sessionManager.getOrgIdFromSession());
//        mFirebaseDatabaseReference.child("Users").
//                orderByChild("Mobile").equalTo(sessionManager.
//                getUserDetailsFromSession().getPhoneNo())
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()) {
//                            HashMap<String, Object> statusMap = new HashMap<>();
//                            statusMap.put("AppStatus", 0);
//                            mFirebaseDatabaseReference.child("Users").child(sessionManager.getUserChatID()).updateChildren(statusMap);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//    }

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
