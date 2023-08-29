package com.bhargo.user.fragments;


import static com.bhargo.user.screens.BottomNavigationActivity.iv_appListRefresh;
import static com.bhargo.user.utils.AppConstants.PERMISSION_STORAGE_CODE;
import static com.bhargo.user.utils.AppConstants.SP_REPORTING_USER_DETAILS;
import static com.bhargo.user.utils.AppConstants.SP_USER_DETAILS;
import static com.bhargo.user.utils.AppConstants.SP_USER_POST_DETAILS;
import static com.bhargo.user.utils.AppConstants.show_msg;
import static com.bhargo.user.utils.ImproveHelper.confirmDialog;
import static com.bhargo.user.utils.ImproveHelper.isFileExistsInExternalPackage;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Java_Beans.GlobalObjects;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.adapters.AppsAdapter;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetChatGPTService;
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
import com.bhargo.user.pojos.PostSubLocationsModel;
import com.bhargo.user.pojos.PostsMasterModel;
import com.bhargo.user.pojos.RefreshMain;
import com.bhargo.user.pojos.RefreshService;
import com.bhargo.user.pojos.ReportsMArr;
import com.bhargo.user.pojos.UserData;
import com.bhargo.user.pojos.UserDetailsModel;
import com.bhargo.user.realm.RealmDBHelper;
import com.bhargo.user.screens.BottomNavigationActivity;
import com.bhargo.user.screens.OfflineHybridSynActivtiy;
import com.bhargo.user.screens.ProfileActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.DownloadFilesFromURL;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bhargo.user.utils.SetEditDataToControlValues;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import io.realm.mongodb.App;
import nk.bluefrog.library.utils.Helper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppsListFragment extends Fragment {

    private static final String TAG = "AppsListFragment";
    public static String DEFAULT_EXTERNAL_DIR;
    private final String[] spArrayValues = {"ORG1", "ORG2", "ORG3", "ORG4", "ORG5"};
    public ImproveHelper improveHelper;
    RecyclerView rv_apps;
    GetServices getServices;
    Context context;
    AppsAdapter appsAdapter;
    CustomTextView ct_alNoRecords;
    SessionManager sessionManager;
    ImproveDataBase improveDataBase;
    long downloaId;
    DownloadManager downloadManagerDataControl, downloadManagerFiles;
    //    CustomEditText cet_appsSearch;
    int isRefresh = 0;
    DatabaseReference mFirebaseDatabaseReference;
    String displayAs = "Application";
    RelativeLayout rl_help_desk;
    List<DataControlsAndApis.DataControlDetails> DataControlDetailsFiles;
    List<DataControls> dataControlsListMain;
    private LinearLayout rl_AppsListMain;
    private String strOrgName;
    private GetAllAppModel getAllAppModel;
    private String strOrgId, str_refresh, str_refresh_Org_id;
    private final Runnable refreshDataControlsRunnable = new Runnable() {
        @Override
        public void run() {
            // improveHelper.showProgressDialog("Please Wait! DataControls Loading...");
            Toast.makeText(context, "DataControls Loading Into Realm! Please Wait..." + AppConstants.dataControlRunnableFlag, Toast.LENGTH_SHORT).show();
            refreshDataControls(DataControlDetailsFiles);
            AppConstants.dataControlRunnableFlag = false;
            // improveHelper.dismissProgressDialog();
            Toast.makeText(context, "DataControls Loading Done!" + AppConstants.dataControlRunnableFlag, Toast.LENGTH_SHORT).show();
        }
    };
    private View rootView;
    //    private List<AppDetails> appDetailsList;
    private ViewGroup viewGroupContainer;
    private GetAllAppNamesData getAllAppNamesData;
    private List<AppDetails> apiDetailsList;
    private final Runnable dataControlsRunnable = new Runnable() {
        @Override
        public void run() {
            //improveHelper.showProgressDialog("Please Wait! DataControls Loading...");
            Toast.makeText(context, "DataControls Loading Into Realm! Please Wait..." + AppConstants.dataControlRunnableFlag, Toast.LENGTH_SHORT).show();
            //new Handler().postDelayed(this, 20000);
//            createAppFolderAndDownloadDataControlsNK(DataControlDetailsFiles, true);
            createAppFolderAndDownloadDataControls(dataControlsListMain, true);
            AppConstants.dataControlRunnableFlag = false;
            //improveHelper.dismissProgressDialog();
            Toast.makeText(context, "DataControls Loading Done!" + AppConstants.dataControlRunnableFlag, Toast.LENGTH_SHORT).show();
        }
    };
    private String strAppsRefreshNotification;

    public AppsListFragment() {
        // Required empty public constructor
    }

    public AppsListFragment(int refresh) {
        // Required empty public constructor
        isRefresh = refresh;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            setBhargoTheme(getActivity(),AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
//        getContext().getTheme().applyStyle(setBhargoThemeNew(AppConstants.THEME,AppConstants.IS_FORM_THEME,AppConstants.FORM_THEME), true);
//        if (AppConstants.THEME.equalsIgnoreCase("THEME2")) {
//        }else   if (AppConstants.THEME.equalsIgnoreCase("THEME1")) {
//            getContext().getTheme().applyStyle(R.style.AppTheme, true);
//        }
            // Inflate the layout for this fragment
            rootView = inflater.inflate(R.layout.fragment_apps_list_default, container, false);
            viewGroupContainer = container;
//        BottomNavigationActivity.ct_FragmentTitle.setText(getActivity().getResources().getString(R.string.apps_list));
            String[] TabsData = AppConstants.WINDOWS_AVAILABLE.split("\\|");
            BottomNavigationActivity.ct_FragmentTitle.setText(TabsData[0].split("\\^")[2]);

//        ((BottomNavigationActivity) getActivity()).setOnBackPressedListener(this);
            context = getActivity();
            improveHelper = new ImproveHelper(getActivity());
            improveDataBase = new ImproveDataBase(getActivity());
            if (sessionManager == null) {
                sessionManager = new SessionManager(getActivity());
            }
            getAllAppModel = new GetAllAppModel();
            getAllAppNamesData = new GetAllAppNamesData();
//        Log.d(TAG, "onCreate2: " + sessionManager.getUserDataFromSession().getUserId());
//        Log.d(TAG, "getOrgIdFromSession: " + sessionManager.getOrgIdFromSession());
            strOrgId = sessionManager.getUserDataFromSession().getOrgName();
            mFirebaseDatabaseReference = FirebaseDatabase.getInstance("https://bhargocommunication-8c36e.firebaseio.com/").getReference(sessionManager.getUserDataFromSession().getOrgName());
//        mFirebaseDatabaseReference = FirebaseDatabase.getInstance("https://improvecommunication-c08c9.firebaseio.com").getReference(sessionManager.getOrgIdFromSession());
            //Create Table dynamically
            //improveDataBase.createUserTable("APP_TABLE");

            getServices = RetrofitUtils.getUserService();

            ct_alNoRecords = rootView.findViewById(R.id.ct_alNoRecords);
            rv_apps = rootView.findViewById(R.id.rv_apps);
            rl_AppsListMain = rootView.findViewById(R.id.rl_AppsListMain);
//        cet_appsSearch = rootView.findViewById(R.id.cet_appsSearch);
            rl_help_desk = rootView.findViewById(R.id.rl_help_desk);
            CardView cv_helpdesk = rootView.findViewById(R.id.cv_helpdesk);
            if (!sessionManager.getOrgIdFromSession().equalsIgnoreCase("UTTA20220521155701093")) {
                cv_helpdesk.setVisibility(View.GONE);
                rl_help_desk.setVisibility(View.GONE);
            }
            LinearLayout profile_layout = rootView.findViewById(R.id.profile_layout);
            ImageView iv_profile = rootView.findViewById(R.id.iv_profile);
            CustomTextView tv_name = rootView.findViewById(R.id.tv_name);
            CustomTextView tv_mobile_no = rootView.findViewById(R.id.tv_mobile_no);
            CustomTextView tv_designation = rootView.findViewById(R.id.tv_designation);
            CustomTextView tv_location = rootView.findViewById(R.id.tv_location);
            tv_name.setText(sessionManager.getUserDataFromSession().getUserName());
//        tv_mobile_no.setText(sessionManager.getUserDataFromSession().getPhoneNo());
            tv_designation.setText(sessionManager.getPostsFromSessionPostName());
            Log.d("BAuthokey", sessionManager.getAuthorizationTokenId());
            if (sessionManager.getUserPostDetails() != null && sessionManager.getUserTypeFromSession().equalsIgnoreCase("Employee")) {
                String user_location = improveHelper.getLocationLevel(sessionManager.getUserPostDetails(), sessionManager.getPostsFromSession());
                tv_location.setText(user_location);
            } else {
                tv_location.setVisibility(View.GONE);
                tv_location.setText("-");
            }
            if (isNetworkStatusAvialable(context)) {
                Glide.with(context).load(sessionManager.getUserDataFromSession().getImagePath()).placeholder(ContextCompat.getDrawable(context, R.drawable.icon_user_profile_image)).into(iv_profile);
            }

            profile_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sessionManager.activeFragment("A");
                    startActivity(new Intent(context, ProfileActivity.class));
                }
            });

            loadGlobalObject(strOrgId);

      /*  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_apps.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView*/
            rv_apps.setLayoutManager(new GridLayoutManager(context, 2)); // set LayoutManager to RecyclerView

            appsAdapter = new AppsAdapter(getActivity(), new ArrayList<AppDetails>(), true, false);

            improveHelper.showProgressDialog(getActivity().getResources().getString(R.string.loading_apps));


//        if (strOrgId != null) {
            Log.d(TAG, "onCreateViewORGId: " + strOrgId);
            getAllAppNamesData.setUserId(sessionManager.getUserDataFromSession().getUserID());
//        improveDataBase.deleteAppsListData(sessionManager.getUserDataFromSession().getOrgName(), sessionManager.getUserDataFromSession().getUserID());
//        orgListApiOnlineOffLine(strOrgId, sessionManager.getPostsFromSession());
            if (isRefresh == 0) {
                orgListApiOnlineOffLine(strOrgId, sessionManager.getPostsFromSession());
            } else {
    //                orgListApiOnlineOffLine(strOrgId, sessionManager.getPostsFromSession());

                refreshCheckingAPi();
//                mAppsListAPI(getAllAppNamesData);
            }


            firebaseAppsListener();
            if (isNetworkStatusAvialable(context)) {
                checkOfflineDataExist();
            }

        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "onCreateView", e);
        } return rootView;
    }

    private void checkOfflineDataExist() {
        try {
            boolean offlineDataExistFlag = PrefManger.getSharedPreferencesBoolean(context, AppConstants.OfflineDataExistKey, false);
            boolean oneTimeOfflineDataExistFlag = PrefManger.getSharedPreferencesBoolean(context, AppConstants.OneTimeForOfflineDataExistKey, false);

            if (oneTimeOfflineDataExistFlag && offlineDataExistFlag) {
                PrefManger.putSharedPreferencesBoolean(context, AppConstants.OneTimeForOfflineDataExistKey, false);
                confirmDialog(context, getString(R.string.synalertmsg), getString(R.string.syncofflineData), getString(R.string.cancel), new Helper.IL() {
                    @Override
                    public void onSuccess() {
                        startActivity(new Intent(context, OfflineHybridSynActivtiy.class));
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "checkOfflineDataExist", e);

        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void orgListApiOnlineOffLine(String strOrgId, String strPostId) {
        try {

//        List<AppDetails> appDetailsList = improveDataBase.getDataFromAppsListTable(strOrgId, sessionManager.getUserDataFromSession().getUserId(), strPostId,displayAs);
            List<AppDetails> appDetailsList = improveDataBase.getDataFromAppsListTable(strOrgId, sessionManager.getUserDataFromSession().getUserID(), sessionManager.getUserTypeIdsFromSession(), strPostId, displayAs, sessionManager.getUserTypeIdsFromSession());
            //createTableWithFalseCase(strPostId);
            if (appDetailsList != null && appDetailsList.size() > 0) {
                apiDetailsList = appDetailsList;
                ct_alNoRecords.setVisibility(View.GONE);
                rv_apps.setVisibility(View.VISIBLE);
//              cet_appsSearch.setVisibility(View.VISIBLE); // change
//                cet_appsSearch.setVisibility(View.GONE);
                appsAdapter = new AppsAdapter(getActivity(), appDetailsList, true, false);
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
//                    cet_appsSearch.setVisibility(View.GONE);
                    rv_apps.setVisibility(View.GONE);

                }
            }


        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            Log.d(TAG, "orgListApiOnlineOffLine: " + e);
            ImproveHelper.improveException(getActivity(), TAG, "orgListApiOnlineOffLine", e);
        }
    }

    private void createTableWithFalseCase1(String strPostId) {
        List<AppDetails> appDetailsListFalse = improveDataBase.getDataFromAppsListTableWithFalseCase(strOrgId, sessionManager.getUserDataFromSession().getUserID(), strPostId, displayAs);
        if (appDetailsListFalse != null && appDetailsListFalse.size() > 0) {
            for (int i = 0; i < appDetailsListFalse.size(); i++) {
                if (appDetailsListFalse.get(i).getAppMode() != null && !appDetailsListFalse.get(i).getAppMode().equalsIgnoreCase("Online")) {
                    improveDataBase.createTablesBasedOnConditions(appDetailsListFalse.get(i));
                }
            }
            appDetailsListFalse = null;
        }
    }


    private void getDataControlListOld(final GetAllAppNamesData getAllAppNamesData) {
        try {
            Call<DataControlsAndApis> getAllAppNamesDataCall = getServices.iGetDataControlsList(sessionManager.getAuthorizationTokenId(), getAllAppNamesData);
            getAllAppNamesDataCall.enqueue(new Callback<DataControlsAndApis>() {

                @Override
                public void onResponse(Call<DataControlsAndApis> call, Response<DataControlsAndApis> response) {

                    List<APIDetails> apiDetailsList = new ArrayList<>();
                    if (response.body().getAPIDetails() != null) {
                        apiDetailsList = response.body().getAPIDetails();
                        if (apiDetailsList != null && apiDetailsList.size() > 0) {
                            improveDataBase.deleteAPINamesData();
                            improveDataBase.insertIntoAPINamesTable(apiDetailsList, strOrgId, sessionManager.getUserDataFromSession().getUserID());
                        }
                    }
                    if (response.body() != null && response.body().getDataControls() != null) {
                        List<DataControls> dataControls = response.body().getDataControls();
                        if (dataControls != null && dataControls.size() != 0) {

//                            semiOffLineCase(dataControls);


                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (getActivity() != null && getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//Ask Permission
                                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                    requestPermissions(permissions, PERMISSION_STORAGE_CODE);
                                } else {
//                                    GetPostBaseDataControls();
//                                    GetAllPostBaseDataControls(dataControls);
//                            createAppFolderAndDownloadDataControls(dataControls);
                                }
                            } else {
//                                GetPostBaseDataControls();
//                                GetAllPostBaseDataControls(dataControls);
//                        createAppFolderAndDownloadDataControls(dataControls);
                            }

                        }
                    } else {
//                    loadAppsListDB();
                        Log.d(TAG, "mAppsListAPIInput " + new Gson().toJson(getAllAppNamesData));
                        mAppsListAPI(getAllAppNamesData);

                    }
                }

                @Override
                public void onFailure(Call<DataControlsAndApis> call, Throwable t) {
//                improveHelper.dismissProgressDialog();
                    improveHelper.dismissProgressDialog();
                    ImproveHelper.improveException(getActivity(), TAG, "getDataControlList", (Exception) t);
                }
            });
        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            ImproveHelper.improveException(getActivity(), TAG, "getDataControlList", e);
        }
    }

    private void getDataControlList(final GetAllAppNamesData getAllAppNamesData) {
        try {
            Call<DataControlsAndApis> getAllAppNamesDataCall = getServices.iGetDataControlsList(sessionManager.getAuthorizationTokenId(), getAllAppNamesData);
            getAllAppNamesDataCall.enqueue(new Callback<DataControlsAndApis>() {

                @Override
                public void onResponse(Call<DataControlsAndApis> call, Response<DataControlsAndApis> response) {

                    List<APIDetails> apiDetailsList = new ArrayList<>();
                    if (response.body().getAPIDetails() != null) {
                        apiDetailsList = response.body().getAPIDetails();
                        if (apiDetailsList != null && apiDetailsList.size() > 0) {
                            improveDataBase.deleteAPINamesData();
                            improveDataBase.insertIntoAPINamesTable(apiDetailsList, strOrgId, sessionManager.getUserDataFromSession().getUserID());
                        }
                    }
                    if (response.body() != null && response.body().getDataControls() != null) {
                        List<DataControls> dataControls = response.body().getDataControls();
                        if (dataControls != null && dataControls.size() != 0) {
                            dataControlsListMain = dataControls;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (getActivity() != null && getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//Ask Permission
                                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                    requestPermissions(permissions, PERMISSION_STORAGE_CODE);
                                } else {
                                    // Sanjay
                                    //nk realmF
                                    //createAppFolderAndDownloadDataControls(DataControlDetails,true);
                                    //new Handler().postDelayed(dataControlsRunnable, 60000);
                                    AppConstants.dataControlRunnableFlag = true;
                                    dataControlsRunnable.run();
                                    for (int i = 0; i < dataControls.size(); i++) {
                                        improveDataBase.insertIntoDataControlTable(dataControls.get(i), strOrgId, sessionManager.getUserDataFromSession().getUserID());
//                                            improveDataBase.insertIntoNewDataControlTable(dataControls.get(i), strOrgId, sessionManager.getUserDataFromSession().getUserID());
                                    }
                                }
                            } else { //sanjay
                                //nk realm
                                //createAppFolderAndDownloadDataControls(DataControlDetails,true);
                                //new Handler().postDelayed(dataControlsRunnable, 60000);
                                AppConstants.dataControlRunnableFlag = true;
                                dataControlsRunnable.run();
                                for (int i = 0; i < dataControls.size(); i++) {
                                    improveDataBase.insertIntoDataControlTable(dataControls.get(i), strOrgId, sessionManager.getUserDataFromSession().getUserID());
//                                            improveDataBase.insertIntoNewDataControlTable(dataControls.get(i), strOrgId, sessionManager.getUserDataFromSession().getUserID());
                                }
                            }
                        } else {
                            Log.d(TAG, "mAppsListAPIInput " + new Gson().toJson(getAllAppNamesData));
                            mAppsListAPI(getAllAppNamesData);
                        }
                    }
                }

                @Override
                public void onFailure(Call<DataControlsAndApis> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    ImproveHelper.improveException(getActivity(), TAG, "getDataControlList", (Exception) t);
                }
            });
        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            ImproveHelper.improveException(getActivity(), TAG, "getDataControlList", e);
        }
    }

    //    List<PostSubLocationsModel> Sublocations=new ArrayList<>();
    public void GetPostBaseDataControls() {
        try {
            JSONObject DataControlsObj = new JSONObject();
            DataControlsObj.put("UserID", sessionManager.getUserDataFromSession().getUserID());
            DataControlsObj.put("PostID", sessionManager.getPostsFromSession());
            DataControlsObj.put("DataControlDetails", new JSONArray());
            List<PostSubLocationsModel> Sublocations = new ArrayList<>();
            SessionManager sessionManager = new SessionManager(context);
            List<PostsMasterModel> postDetails = sessionManager.getUserPostDetails();
            String postId = sessionManager.getPostsFromSession();
            if (postDetails != null && postDetails.size() > 0) {
                for (int i = 0; i < postDetails.size(); i++) {
                    PostsMasterModel userPostDetails = postDetails.get(i);
                    if (postId != null && !postId.contentEquals("") && userPostDetails != null && userPostDetails.getID().contentEquals(postId)) {
                        Sublocations = userPostDetails.getPostSubLocations();
                    }
                }
            }
            if (Sublocations != null) {
                Gson GsonParser1 = new Gson();
                String element1 = GsonParser1.toJson(Sublocations, new TypeToken<ArrayList<PostSubLocationsModel>>() {
                }.getType());
                JSONArray SublocationsArr = new JSONArray(element1);
                DataControlsObj.put("PostSubLocations", SublocationsArr);
            }
            JsonParser jsonParser = new JsonParser();
            JsonObject jo_DataControlsObj = (JsonObject) jsonParser.parse(DataControlsObj.toString());
            System.out.println("DataControls Start FirstTime:");
            Call<DataControlsAndApis> getAllDataControls = getServices.GetMasterControlsData(sessionManager.getAuthorizationTokenId(), jo_DataControlsObj);
            getAllDataControls.enqueue(new Callback<DataControlsAndApis>() {
                @Override
                public void onResponse(Call<DataControlsAndApis> call, Response<DataControlsAndApis> response) {
                    if (response.body() != null && response.body().getDataControlDetails() != null && response.body().getDataControlDetails().size() > 0) {

                        List<DataControlsAndApis.DataControlDetails> DataControlDetails = response.body().getDataControlDetails();
                        DataControlDetailsFiles = DataControlDetails;
                        AppConstants.dataControlRunnableFlag = true;
                        //nk realm
                        //createAppFolderAndDownloadDataControls(DataControlDetails,true);
                        //new Handler().postDelayed(dataControlsRunnable, 60000);
                        dataControlsRunnable.run();
                        if (DataControlDetails != null) {
                            for (int i = 0; i < DataControlDetails.size(); i++) {
                                improveDataBase.insertIntoNewDataControlTable(DataControlDetails.get(i), strOrgId, sessionManager.getUserDataFromSession().getUserID());
                            }
                        }
                    } else {
                        mAppsListAPI(getAllAppNamesData);
                    }
                }

                @Override
                public void onFailure(Call<DataControlsAndApis> call, Throwable t) {

                    improveHelper.dismissProgressDialog();
                    ImproveHelper.improveException(getActivity(), TAG, "GetPostBaseDataControls", (Exception) t);
                }
            });

        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            ImproveHelper.improveException(getActivity(), TAG, "GetPostBaseDataControls", e);
        }

    }


    private void mAppsListAPI(final GetAllAppNamesData getAllAppNamesData) {
        try {
            Call<GetAllAppModel> getAllAppNamesDataCall = getServices.iGetAllAppsList(sessionManager.getAuthorizationTokenId(), getAllAppNamesData);
            getAllAppNamesDataCall.enqueue(new Callback<GetAllAppModel>() {
                @Override
                public void onResponse(Call<GetAllAppModel> call, Response<GetAllAppModel> response) {

                    if (response.body() != null) {
                        getAllAppModel = response.body();
                        String strNewVersionToOldVersion;
                        if (getAllAppModel.getAppDetails() != null && getAllAppModel.getAppDetails().size() > 0) {
//                            cet_appsSearch.setVisibility(View.GONE);
                            ct_alNoRecords.setVisibility(View.GONE);
                            rv_apps.setVisibility(View.VISIBLE);
                            iv_appListRefresh.setVisibility(View.VISIBLE);

//                            appsAdapter = new AppsAdapter(getActivity(), getAllAppModel.getAppDetails(), true, false);
//                            rv_apps.setAdapter(appsAdapter);

                            improveDataBase.deleteAppsListData(sessionManager.getUserDataFromSession().getOrgName(), sessionManager.getUserDataFromSession().getUserID());

                            improveDataBase.insertIntoAppsListTable(getAllAppModel.getAppDetails(), sessionManager.getUserDataFromSession().getOrgName(), sessionManager.getUserDataFromSession().getUserID());
                            for (int i = 0; i < getAllAppModel.getAppDetails().size(); i++) {
                                if (getAllAppModel.getAppDetails().get(i).getAppMode() != null && !getAllAppModel.getAppDetails().get(i).getAppMode().equalsIgnoreCase("Online")){
                                    for (int j = 0; j < getAllAppModel.getAppDetails().get(i).getWorkSpaceAppsList().size(); j++) {
                                        if (getAllAppModel.getAppDetails().get(i).getWorkSpaceAppsList().get(j).getTableSettingsType()!=null && getAllAppModel.getAppDetails().get(i).getWorkSpaceAppsList().get(j).getTableSettingsType().equalsIgnoreCase("Create New Table")) {
                                            improveDataBase.createTablesBasedOnConditions(getAllAppModel.getAppDetails().get(i).getWorkSpaceAppsList().get(j));
                                        }
                                    }
                                }
                            }
                            apiDetailsList = improveDataBase.getDataFromAppsListTable(strOrgId, sessionManager.getUserDataFromSession().getUserID(), sessionManager.getUserTypeIdsFromSession(), sessionManager.getPostsFromSession(), displayAs, sessionManager.getUserTypeIdsFromSession());

                            if (apiDetailsList != null && apiDetailsList.size() > 0) {
                                appsAdapter = new AppsAdapter(getActivity(), apiDetailsList, true, false);
                                rv_apps.setAdapter(appsAdapter);
                            }
                            updateFirebaseStatus();
//                    appsAdapter.notifyDataSetChanged();

                            // working
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (getActivity() != null && getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
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
//                            cet_appsSearch.setVisibility(View.GONE);
                            rv_apps.setVisibility(View.GONE);
                            iv_appListRefresh.setVisibility(View.GONE);

                        }
                    } else {

                        improveHelper.dismissProgressDialog();
                        ct_alNoRecords.setVisibility(View.VISIBLE);
//                        cet_appsSearch.setVisibility(View.GONE);
                        rv_apps.setVisibility(View.GONE);
                        iv_appListRefresh.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onFailure(Call<GetAllAppModel> call, Throwable t) {
                    System.out.println("Error at getapps ==" + t);
                    improveHelper.dismissProgressDialog();
                    ImproveHelper.improveException(getActivity(), TAG, "mAppsListAPI", (Exception) t);
                }
            });
        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            ImproveHelper.improveException(getActivity(), TAG, "mAppsListAPI", e);
        }
    }

    private void createAppFolderAndDownloadDataControls(List<DataControls> dataControlsList, boolean realmFlag) {
        try {
            if (dataControlsList != null) {
                for (int count = 0; count < dataControlsList.size(); count++) {
                    String filePath = dataControlsList.get(count).getTextFilePath().trim();
                    if (filePath != null && !filePath.isEmpty()) {
                        String[] imgUrlSplit = filePath.split("/");
                        String itemNameInPackage = imgUrlSplit[imgUrlSplit.length - 1].replaceAll(" ", "_");
                        if (!ImproveHelper.isFileExistsInExternalPackage(context, sessionManager.getOrgIdFromSession() + "/", itemNameInPackage)) {
                            Log.d(TAG, "createAppDDone: " + itemNameInPackage);
                            new DownloadFilesFromURL(context, filePath, sessionManager, null, TAG, "",  dataControlsList.get(count).getControlName(), new DownloadFilesFromURL.DownloadFileListener() {

                                @Override
                                public void onSuccess(File file, String dataControlName) {
                                    loadTxtFileIntoRealm(file, dataControlName);
                                }

                                @Override
                                public void onFailed(String errorMessage) {

                                }
                            });
                        } else {
                            Log.d(TAG, "createAppDDTwo: " + itemNameInPackage);
                        }

//                        startDownload(filePath, "", 0, true, dataControlsList.get(count));
                    }
                }
            }
            mAppsListAPI(getAllAppNamesData);
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "createAppFolderAndDownloadDataControls", e);
        }
    }

    public void createAppFolderAndDownloadFiles(List<AppDetails> appDetails) {

        try {
            if(appDetails != null && appDetails.size() > 0) {
                for (int count = 0; count < appDetails.size(); count++) {
                    if (appDetails.get(count).getWorkSpaceAppsList() != null && appDetails.get(count).getWorkSpaceAppsList().size() > 0) {
                        for (int i = 0; i < appDetails.get(count).getWorkSpaceAppsList().size(); i++) {
                            AppDetails.WorkSpaceAppsList workSpaceAppsList = appDetails.get(count).getWorkSpaceAppsList().get(i);
                            if (workSpaceAppsList.getAppIcon() != null && !workSpaceAppsList.getAppIcon().equalsIgnoreCase("")) {
                                String appName = workSpaceAppsList.getAppName().replaceAll(" ", "_");
                                String appIcon = null;
                                if (workSpaceAppsList.getDisplayIcon() != null && !workSpaceAppsList.getDisplayIcon().isEmpty()) {
                                    appIcon = workSpaceAppsList.getDisplayIcon().trim();
                                } else {
                                    appIcon = workSpaceAppsList.getAppIcon().trim();
                                }

                                if (workSpaceAppsList.getDownloadURls() != null && !workSpaceAppsList.getDownloadURls().isEmpty()) {
                                    String[] imgUrlSplit = workSpaceAppsList.getDownloadURls().split(",");
                                    for (int urlcount = 0; urlcount < imgUrlSplit.length; urlcount++) {
                                        startDownload(imgUrlSplit[urlcount].trim(), appName, 1, false, null);
                                    }
                                }
                                if (!appIcon.equalsIgnoreCase("NA")) {
                                    startDownload(appIcon, appName, 1, false, null);
                                }
                            }

                        }

                    } else {
                        String appName = appDetails.get(count).getAppName().replaceAll(" ", "_");
                        Log.d(TAG, "createAppFolderAndDownloadFiles: " + "First - " + appName);
                        if (appDetails.get(count).getAppIcon() != null && !appDetails.get(count).getAppIcon().equalsIgnoreCase("")) {

                            String appIcon = appDetails.get(count).getAppIcon().trim();
                            if (appDetails.get(count).getDownloadURls() != null && !appDetails.get(count).getDownloadURls().isEmpty()) {
                                String[] imgUrlSplit = appDetails.get(count).getDownloadURls().split(",");
                                for (int urlcount = 0; urlcount < imgUrlSplit.length; urlcount++) {
                                    startDownload(imgUrlSplit[urlcount].trim(), appName, 1, false, null);
                                }
                            }
                            if (!appIcon.equalsIgnoreCase("NA")) {
                                startDownload(appIcon, appName, 1, false, null);
                            }
                        }

                    }




                    /*Menu Icons folder and icons download*/
                    /*MenuControl Deprecated In Bhargo Start*/
//                if (appDetails.get(count).getDesignFormat() != null) {
//                    String designStringMenu = appDetails.get(count).getDesignFormat();
////                    XMLHelper xmlHelper = new XMLHelper();
////                if (designStringMenu.contains("MenuControl")) {
//                    //nk step
//                    System.out.println("=======Step7===============");
////                    DataCollectionObject dataCollectionObject = xmlHelper.XML_To_DataCollectionObject(designStringMenu);
//                    DataCollectionObject dataCollectionObject = AppConstants.CurrentAppObject;
////                    String strAppMode = dataCollectionObject.getApp_Mode();
//
////                    File rootMenu = new File(Environment.getExternalStorageDirectory(),
////                            "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "MenuIcons/");
////                    if (!rootMenu.exists()) {
////                        rootMenu.mkdirs();
////                    }
//
////                    String strSDCardPath = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "MenuIcons/";
//
//                    List<ControlObject> controlObjectList = dataCollectionObject.getControls_list();
//                    for (int i = 0; i < controlObjectList.size(); i++) {
//
//                        if (controlObjectList.get(i).getControlType().equalsIgnoreCase("MenuControl") && controlObjectList.get(i).getTypeOfButton().equalsIgnoreCase("Icon")) {
//
//                            if (controlObjectList.get(i).getMenuControlObjectList() != null && controlObjectList.get(i).getMenuControlObjectList().size() > 0) {
//                                for (int j = 0; j < controlObjectList.get(i).getMenuControlObjectList().size(); j++) {
//
//                                    ControlObject strMenuItem = controlObjectList.get(i).getMenuControlObjectList().get(j);
//                                    String[] imgUrlSplit = strMenuItem.getIconPath().split("/");
//                                    String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "MenuIcons/";
//                                    Log.d(TAG, "MenuIconsUrls: " + strMenuItem.getIconPath() + " - " + strSDCardUrl + imgUrlSplit[imgUrlSplit.length - 1] + " - " + appName);
//
//                                    DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl);
//                                    fromURLTask.execute(strMenuItem.getIconPath().trim());
//
////                                    downloadDataControl(strMenuItem.getIconPath(), strSDCardUrl + imgUrlSplit[imgUrlSplit.length - 1]);
//
//                                }
//                            }
//                        }
//
//                        if (controlObjectList.get(i).getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_BUTTON)
//                                && controlObjectList.get(i).getTypeOfButton() != null && !controlObjectList.get(i).getTypeOfButton().equalsIgnoreCase("Button")) {
//
//                            if (controlObjectList.get(i).getIconPath() != null) {
//
//                                String[] imgUrlSplit = controlObjectList.get(i).getIconPath().split("/");
//                                String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "ButtonIcons/";
//
//                                DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl);
//                                fromURLTask.execute(controlObjectList.get(i).getIconPath().trim());
//
//                            }
//                        }
//
//                        if (controlObjectList.get(i).getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SUBFORM)) {
//                            List<ControlObject> subFormControlList = controlObjectList.get(i).getSubFormControlList();
//                            if (subFormControlList != null && subFormControlList.size() > 0) {
//                                for (int j = 0; j < subFormControlList.size(); j++) {
//                                    if (subFormControlList.get(j).getControlType().equalsIgnoreCase("MenuControl") && subFormControlList.get(j).getTypeOfButton().equalsIgnoreCase("Icon")) {
//
//                                        if (subFormControlList.get(j).getMenuControlObjectList() != null && subFormControlList.get(j).getMenuControlObjectList().size() > 0) {
//                                            for (int k = 0; k < subFormControlList.get(j).getMenuControlObjectList().size(); j++) {
//
//                                                ControlObject strMenuItem = subFormControlList.get(j).getMenuControlObjectList().get(k);
//                                                String[] imgUrlSplit = strMenuItem.getIconPath().split("/");
//                                                String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "MenuIcons/";
//                                                Log.d(TAG, "MenuIconsUrls: " + strMenuItem.getIconPath() + " - " + strSDCardUrl + imgUrlSplit[imgUrlSplit.length - 1] + " - " + appName);
//
//                                                DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl);
//                                                fromURLTask.execute(strMenuItem.getIconPath().trim());
//
////                                    downloadDataControl(strMenuItem.getIconPath(), strSDCardUrl + imgUrlSplit[imgUrlSplit.length - 1]);
//
//                                            }
//                                        }
//                                    }
//
//                                    if (subFormControlList.get(j).getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_BUTTON)
//                                            && subFormControlList.get(j).getTypeOfButton() != null && !subFormControlList.get(j).getTypeOfButton().equalsIgnoreCase("Button")) {
//
//                                        if (subFormControlList.get(j).getIconPath() != null) {
//                                            Log.d(TAG, "createAppFolderAndDownloadFilesPath: "+subFormControlList.get(j).getIconPath());
//                                            String[] imgUrlSplit = subFormControlList.get(j).getIconPath().split("/");
//                                            String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "ButtonIcons/";
//
//                                            DownloadFileFromURLTask fromURLTasktn = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl);
//                                            fromURLTasktn.execute(subFormControlList.get(j).getIconPath().trim());
//                                        }
//                                    }
//
//                                }
//                            }
//                        }
////
//                    }
//                }
                    /*Menu Icons folder and icons download End*/

                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "createAppFolderAndDownloadFiles", e);
        }
    }
    public void createAppFolderAndDownloadFilesCopy(List<AppDetails> appDetails) {

        try {
            for (int count = 0; count < appDetails.size(); count++) {
                String appName = appDetails.get(count).getAppName().replaceAll(" ", "_");
                Log.d(TAG, "createAppFolderAndDownloadFiles: " + "First - " + appName);
                /*Menu Icons folder and icons download*/
                /*MenuControl Deprecated In Bhargo Start*/
//                if (appDetails.get(count).getDesignFormat() != null) {
//                    String designStringMenu = appDetails.get(count).getDesignFormat();
////                    XMLHelper xmlHelper = new XMLHelper();
////                if (designStringMenu.contains("MenuControl")) {
//                    //nk step
//                    System.out.println("=======Step7===============");
////                    DataCollectionObject dataCollectionObject = xmlHelper.XML_To_DataCollectionObject(designStringMenu);
//                    DataCollectionObject dataCollectionObject = AppConstants.CurrentAppObject;
////                    String strAppMode = dataCollectionObject.getApp_Mode();
//
////                    File rootMenu = new File(Environment.getExternalStorageDirectory(),
////                            "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "MenuIcons/");
////                    if (!rootMenu.exists()) {
////                        rootMenu.mkdirs();
////                    }
//
////                    String strSDCardPath = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "MenuIcons/";
//
//                    List<ControlObject> controlObjectList = dataCollectionObject.getControls_list();
//                    for (int i = 0; i < controlObjectList.size(); i++) {
//
//                        if (controlObjectList.get(i).getControlType().equalsIgnoreCase("MenuControl") && controlObjectList.get(i).getTypeOfButton().equalsIgnoreCase("Icon")) {
//
//                            if (controlObjectList.get(i).getMenuControlObjectList() != null && controlObjectList.get(i).getMenuControlObjectList().size() > 0) {
//                                for (int j = 0; j < controlObjectList.get(i).getMenuControlObjectList().size(); j++) {
//
//                                    ControlObject strMenuItem = controlObjectList.get(i).getMenuControlObjectList().get(j);
//                                    String[] imgUrlSplit = strMenuItem.getIconPath().split("/");
//                                    String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "MenuIcons/";
//                                    Log.d(TAG, "MenuIconsUrls: " + strMenuItem.getIconPath() + " - " + strSDCardUrl + imgUrlSplit[imgUrlSplit.length - 1] + " - " + appName);
//
//                                    DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl);
//                                    fromURLTask.execute(strMenuItem.getIconPath().trim());
//
////                                    downloadDataControl(strMenuItem.getIconPath(), strSDCardUrl + imgUrlSplit[imgUrlSplit.length - 1]);
//
//                                }
//                            }
//                        }
//
//                        if (controlObjectList.get(i).getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_BUTTON)
//                                && controlObjectList.get(i).getTypeOfButton() != null && !controlObjectList.get(i).getTypeOfButton().equalsIgnoreCase("Button")) {
//
//                            if (controlObjectList.get(i).getIconPath() != null) {
//
//                                String[] imgUrlSplit = controlObjectList.get(i).getIconPath().split("/");
//                                String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "ButtonIcons/";
//
//                                DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl);
//                                fromURLTask.execute(controlObjectList.get(i).getIconPath().trim());
//
//                            }
//                        }
//
//                        if (controlObjectList.get(i).getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SUBFORM)) {
//                            List<ControlObject> subFormControlList = controlObjectList.get(i).getSubFormControlList();
//                            if (subFormControlList != null && subFormControlList.size() > 0) {
//                                for (int j = 0; j < subFormControlList.size(); j++) {
//                                    if (subFormControlList.get(j).getControlType().equalsIgnoreCase("MenuControl") && subFormControlList.get(j).getTypeOfButton().equalsIgnoreCase("Icon")) {
//
//                                        if (subFormControlList.get(j).getMenuControlObjectList() != null && subFormControlList.get(j).getMenuControlObjectList().size() > 0) {
//                                            for (int k = 0; k < subFormControlList.get(j).getMenuControlObjectList().size(); j++) {
//
//                                                ControlObject strMenuItem = subFormControlList.get(j).getMenuControlObjectList().get(k);
//                                                String[] imgUrlSplit = strMenuItem.getIconPath().split("/");
//                                                String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "MenuIcons/";
//                                                Log.d(TAG, "MenuIconsUrls: " + strMenuItem.getIconPath() + " - " + strSDCardUrl + imgUrlSplit[imgUrlSplit.length - 1] + " - " + appName);
//
//                                                DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl);
//                                                fromURLTask.execute(strMenuItem.getIconPath().trim());
//
////                                    downloadDataControl(strMenuItem.getIconPath(), strSDCardUrl + imgUrlSplit[imgUrlSplit.length - 1]);
//
//                                            }
//                                        }
//                                    }
//
//                                    if (subFormControlList.get(j).getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_BUTTON)
//                                            && subFormControlList.get(j).getTypeOfButton() != null && !subFormControlList.get(j).getTypeOfButton().equalsIgnoreCase("Button")) {
//
//                                        if (subFormControlList.get(j).getIconPath() != null) {
//                                            Log.d(TAG, "createAppFolderAndDownloadFilesPath: "+subFormControlList.get(j).getIconPath());
//                                            String[] imgUrlSplit = subFormControlList.get(j).getIconPath().split("/");
//                                            String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + "ButtonIcons/";
//
//                                            DownloadFileFromURLTask fromURLTasktn = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl);
//                                            fromURLTasktn.execute(subFormControlList.get(j).getIconPath().trim());
//                                        }
//                                    }
//
//                                }
//                            }
//                        }
////
//                    }
//                }
                /*Menu Icons folder and icons download End*/

                if (appDetails.get(count).getAppIcon() != null && !appDetails.get(count).getAppIcon().equalsIgnoreCase("")) {

                    String appIcon = appDetails.get(count).getAppIcon().trim();
                    if (appDetails.get(count).getDownloadURls() != null && !appDetails.get(count).getDownloadURls().isEmpty()) {
                        String[] imgUrlSplit = appDetails.get(count).getDownloadURls().split(",");
                        for (int urlcount = 0; urlcount < imgUrlSplit.length; urlcount++) {
                            startDownload(imgUrlSplit[urlcount].trim(), appName, 1, false, null);
                        }
                    }
                    if (!appIcon.equalsIgnoreCase("NA")) {
                        startDownload(appIcon, appName, 1, false, null);
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "createAppFolderAndDownloadFiles", e);
        }
    }


/*
    private void startDownloadDataControl(String filePath, String appName, int flag, int refreshFlag) {
        try {
            boolean resultDelete = false;
            String[] imgUrlSplit = filePath.split("/");
            String imgNameInPackage = imgUrlSplit[imgUrlSplit.length - 1].replaceAll(" ", "_");
            String strSDCardUrl = null;

      */
/*  if (flag == 1) {
            strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + imgUrlSplit[imgUrlSplit.length - 1];
        } else {
            strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + imgUrlSplit[imgUrlSplit.length - 1];
        }
*/
    /*


            if (flag == 1) {
                strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/";
            } else {
                strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/";
            }

            if (!ImproveHelper.isFileExistsInExternalPackage(context,strSDCardUrl,imgNameInPackage)) {

//            downloadFile(filePath, strSDCardUrl);

                DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl);
                fromURLTask.execute(filePath);
            } else {
            resultDelete = deleteFileifExists(strSDCardUrl, imgUrlSplit[imgUrlSplit.length - 1]);

                if (resultDelete) {

//                downloadFile(filePath, strSDCardUrl);

                    DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl);
                    fromURLTask.execute(filePath);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "startDownloadDataControl", e);
        }

    }
*/

//    private void startDownloadDataControl_2(DataControls dataControls, String filePath, String appName, int flag, int refreshFlag) {
//
//        boolean resultDelete = false;
//        String[] imgUrlSplit = filePath.split("/");
//        String strSDCardUrl = null;
//
//        if (flag == 1) {
//            strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/" + imgUrlSplit[imgUrlSplit.length - 1];
//        } else {
//            strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + imgUrlSplit[imgUrlSplit.length - 1];
//        }
//
//        DataControls dataControlsFromDB = improveDataBase.getDataControlVersion(strOrgId, dataControls.getControlName(), sessionManager.getUserDataFromSession().getUserId());
//        if (dataControlsFromDB.getVersion() != null) {
//            if (dataControlsFromDB.getVersion().equalsIgnoreCase(dataControls.getVersion())) {
//                if (!isFileExists(strSDCardUrl)) {
//                    downloadDataControl(filePath, strSDCardUrl);
//                }
//            } else {
//                improveDataBase.deleteDataControlListData(strOrgId, dataControls.getControlName());
//                if (refreshFlag != 1) {
//                    improveDataBase.insertIntoDataControlTable(dataControls, strOrgId, sessionManager.getUserDataFromSession().getUserId());
//                }
//                if (isFileExists(strSDCardUrl)) {
//                    resultDelete = deleteFileifExists(strSDCardUrl);
//                } else {
//                    resultDelete = true;
//                }
//                if (resultDelete) {
//
//                    downloadDataControl(filePath, strSDCardUrl);
//
//                }
//            }
//        } else {
//            if (refreshFlag != 1) {
//                improveDataBase.insertIntoDataControlTable(dataControls, strOrgId, sessionManager.getUserDataFromSession().getUserId());
//            }
//            if (isFileExists(strSDCardUrl)) {
//
//                resultDelete = deleteFileifExists(strSDCardUrl);
//            } else {
//                resultDelete = true;
//            }
//            if (resultDelete) {
//
//                downloadDataControl(filePath, strSDCardUrl);
//            }
//        }
//
//    }

//    public void downloadDataControl(String filePath, String strSDCardUrl) {
//        try {
//            if (!filePath.contains(" ") && filePath.contains("http")) {
//                try {
//                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(filePath));
//                    if (request != null) {
////                    request.setVisibleInDownloadsUi(false);
//                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
////                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
////                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                            request.setDestinationInExternalFilesDir(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath(), strSDCardUrl);
//                        } else {
//                            request.setDestinationInExternalPublicDir("", strSDCardUrl);
//                        }
//
//                        DownloadManager downloadManagerFiles = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
//                        downloadManagerFiles.enqueue(request);
////                    long downloadId = downloadManagerFiles.enqueue(request);
//
////                    Log.d(TAG, "startDownload: " + downloadManagerFiles);
//
//                /*downloadManagerDataControl = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
//                downloaId = downloadManagerDataControl.enqueue(request);
//
//                Log.d(TAG, "startDownload: " + downloadManagerDataControl);*/
//                    }
//                } catch (Exception e) {
//                    Log.d(TAG, "downloadDataControlException: " + e.getMessage());
//                }
//            }
//        } catch (Exception e) {
//            ImproveHelper.improveException(getActivity(), TAG, "downloadDataControl", e);
//        }
//    }

    /* loadGlobalObject*/
    public void loadGlobalObject(String orgID) {
        try {
            Log.d("BAuthokey", sessionManager.getAuthorizationTokenId());
            GlobalObjects Gobj = new GlobalObjects();
            Gson gson = new Gson();

            String jsonUserDeatils = PrefManger.getSharedPreferencesString(getActivity(), SP_USER_DETAILS, "");
            UserData userDetailsObj = gson.fromJson(jsonUserDeatils, UserData.class);

            String jsonUserPostDetails = PrefManger.getSharedPreferencesString(getActivity(), SP_USER_POST_DETAILS, "");
//        List<PostsMasterModel> userPostDetailsObj = gson.fromJson(jsonUserPostDetails, PostsMasterModel.class);
            if (jsonUserPostDetails != null && !jsonUserPostDetails.isEmpty()) {
                Type collectionType = new TypeToken<Collection<PostsMasterModel>>() {
                }.getType();
                List<PostsMasterModel> upd = gson.fromJson(jsonUserPostDetails, collectionType);
                if (upd != null && upd.size() > 0) {
                    Log.d(TAG, "loadGlobalObjectPostName: " + upd.get(0).getName());
                }
            }

            String jsonReportingUserDeatils = PrefManger.getSharedPreferencesString(getActivity(), SP_REPORTING_USER_DETAILS, "");
            UserDetailsModel.ReportingUserDeatils reportingUserDetailsObj = gson.fromJson(jsonReportingUserDeatils, UserDetailsModel.ReportingUserDeatils.class);
            Gobj.setAppLanguage(ImproveHelper.getLocale(getActivity()));
            if (userDetailsObj != null) {
//                Gobj.setUser_Role(userDetailsObj.getRole());
                Gobj.setUser_ID(userDetailsObj.getUserID());
                Gobj.setUser_Name(userDetailsObj.getUserName());
                Gobj.setUser_MobileNo(userDetailsObj.getPhoneNo());
                Gobj.setUser_Email(userDetailsObj.getEmail());
                Gobj.setUser_type(sessionManager.getUserTypeFromSession());
                Gobj.setUser_type_id(sessionManager.getUserTypeIdsFromSession());
//                Gobj.setUser_Desigination(userDetailsObj.getDesignation());
//                Gobj.setUser_Department(userDetailsObj.getDepartment());
//                Gobj.setUser_location(userDetailsObj.getLocationCode());
//                Gobj.setUser_location_name(userDetailsObj.getLocationCodeName());
//                Gobj.setLocatonLevel(userDetailsObj.getLocatonLevel());
//                Gobj.setSublocations(userDetailsObj.getSublocations());
                Gobj.setUser_PostID(sessionManager.getPostsFromSession());
                Gobj.setUser_PostName(sessionManager.getPostsFromSessionPostName());
                if (jsonUserPostDetails != null && !jsonUserPostDetails.contentEquals("")) {
                    JSONArray postArray = new JSONArray(jsonUserPostDetails);
                    for (int i = 0; i < postArray.length(); i++) {
//                        String postId = postArray.getJSONObject(i).getString("PostID");
                        String postId = postArray.getJSONObject(i).getString("ID");
                        String strPostLocationLevel = postArray.getJSONObject(i).getString("PostLocatonLevel");
                        String strPostLocationLevelName = postArray.getJSONObject(i).getString("PostLocationLevelName");
                        String strReportingPostID = postArray.getJSONObject(i).getString("ReportingPostID");
                        String strReportingDepartmentID = postArray.getJSONObject(i).getString("ReportingDepartmentID");
                        String strManualReportingPostID = postArray.getJSONObject(i).getString("ManualReportingPostID");
                        String strManualReportingPersonID = postArray.getJSONObject(i).getString("ManualReportingPersonID");
                        if (postId.equalsIgnoreCase(sessionManager.getPostsFromSession())) {
                            Gobj.setLocatonLevel(strPostLocationLevel);
                            Gobj.setUser_Post_Location_Name(strPostLocationLevelName);
                            Gobj.setReporter_ID(strReportingPostID);
                            Gobj.setUser_Department(strReportingDepartmentID);
                            Gobj.setManualReportingPostID(strManualReportingPostID);
                            Gobj.setStrManualReportingPersonID(strManualReportingPersonID);

                           /* if (!PrefManger.getSharedPreferencesString(context, "User location Name", "").contentEquals("")) {
                                Gobj.setUser_location_name(PrefManger.getSharedPreferencesString(context, "User location Name", ""));
                            }
                            if (!PrefManger.getSharedPreferencesString(context, AppConstants.User_Name, "").contentEquals("")) {
                                Gobj.setUser_Name(PrefManger.getSharedPreferencesString(context, AppConstants.User_Name, ""));
                            }*/
                        }
                        if (postId.contentEquals(sessionManager.getPostsFromSession())) {
                            JSONArray subLocations = postArray.getJSONObject(i).getJSONArray("PostSubLocations");
                            for (int j = 0; j < subLocations.length(); j++) {
                                if (j == subLocations.length() - 1) {
                                    Gobj.setUser_Post_Location_Name(subLocations.getJSONObject(j).getString("Text"));
                                    Gobj.setUser_Post_Location(subLocations.getJSONObject(j).getString("Value"));
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
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
            ImproveHelper.improveException(getActivity(), TAG, "loadGlobalObject", e);
        }
    }
    private void loadTxtFileIntoRealm(File file, String dataControlName) {
        try {
            System.out.println("file :"+file.getAbsolutePath());
            String line = ImproveHelper.readTextFileFromSD(context, file.getAbsolutePath());
            System.out.println("file Data :"+line);
            RealmDBHelper.createTableAndInsertDataFromDataControl(context, line);
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "loadTxtFileIntoRealm", e);

        }
       /* if (status.equalsIgnoreCase("Update") || status.equalsIgnoreCase("U")) {
            String refRespDCUpdate = dataControlDetails.getDataControlName();
            RealmDBHelper.deleteTable(context, dataControlDetails.getDataControlName());
            RealmDBHelper.createTableAndInsertDataFromDataControl(context, line);
        } else if (dataControlDetails.getStatus().equalsIgnoreCase("Deleted")) {
            String refRespDCDelete = dataControlDetails.getDataControlName();
            RealmDBHelper.deleteTable(context, dataControlDetails.getDataControlName());
        } else if (dataControlDetails.getStatus().equalsIgnoreCase("New")) { // newly added files
            RealmDBHelper.createTableAndInsertDataFromDataControl(context, line);
        }*/
    }

    private void loadTxtFileIntoRealm(File file, DataControlsAndApis.DataControlDetails dataControlDetails) {
        try {
            System.out.println("DataControls Start EndTime:" + dataControlDetails.getDataControlName());
            String line = ImproveHelper.readTextFileFromSD(context, file.getAbsolutePath());
            System.out.println("===realm: String===" + line);
            System.out.println("===realm: Table Name===" + dataControlDetails.getDataControlName());
            System.out.println("===realm: status===" + dataControlDetails.getStatus());
            if (dataControlDetails.getStatus().equalsIgnoreCase("Update")) {
                String refRespDCUpdate = dataControlDetails.getDataControlName();
                RealmDBHelper.deleteTable(context, dataControlDetails.getDataControlName());
                RealmDBHelper.createTableAndInsertDataFromDataControl(context, line);
            } else if (dataControlDetails.getStatus().equalsIgnoreCase("Deleted")) {
                String refRespDCDelete = dataControlDetails.getDataControlName();
                RealmDBHelper.deleteTable(context, dataControlDetails.getDataControlName());
            } else if (dataControlDetails.getStatus().equalsIgnoreCase("New")) { // newly added files
                RealmDBHelper.createTableAndInsertDataFromDataControl(context, line);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "loadTxtFileIntoRealm", e);

        }
    }

    private void startDownload(String filePath, String appName, int flag, boolean realmFlag, DataControls dataControlDetails) {
        try {
            boolean resultDelete = false;
            String[] imgUrlSplit = filePath.split("/");
            String itemNameInPackage = imgUrlSplit[imgUrlSplit.length - 1].replaceAll(" ", "_");
            String strSDCardUrl = null;
            if (flag == 1) { // Apps
                strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + appName + "/";
            } else { // DataControls
                strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/";
//                strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/";
            }
            if (!isFileExistsInExternalPackage(context, strSDCardUrl, itemNameInPackage)) {
                if (realmFlag) {
                    DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl, flag, new DownloadFileListener() {
                        @Override
                        public void onSuccess(File file) {
//                            loadTxtFileIntoRealm(file, dataControlsList);
                        }

                        @Override
                        public void onFailed(String errorMessage) {

                        }
                    });
                    fromURLTask.execute(filePath);
                } else {
                    DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl, flag);
                    fromURLTask.execute(filePath);
/*
                    DownloadFileFromURLTask fromURLTask1 = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl, flag, new DownloadFileListener() {
                        @Override
                        public void onSuccess(File file) {
                            Log.d(TAG, "onSuccessAppIconDownload: "+file.getPath());

                        }

                        @Override
                        public void onFailed(String errorMessage) {
                            Log.d(TAG, "onFailedAppIconDownload: "+errorMessage);
                        }
                    });
                    fromURLTask1.execute(filePath);
*/
                }
//              //nk realm:
                /*DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl, flag);
                fromURLTask.execute(filePath);*/
            } else {
                resultDelete = deleteFileifExists(strSDCardUrl, itemNameInPackage);
//
                if (resultDelete) {
                    if (realmFlag) {
                        DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl, flag, new DownloadFileListener() {
                            @Override
                            public void onSuccess(File file) {
//                                loadTxtFileIntoRealm(file, dataControlDetails);
                            }

                            @Override
                            public void onFailed(String errorMessage) {

                            }
                        });
                        fromURLTask.execute(filePath);
                    } else {
                        DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl, flag, new DownloadFileListener() {
                            @Override
                            public void onSuccess(File file) {

                            }

                            @Override
                            public void onFailed(String errorMessage) {

                            }
                        });
                        fromURLTask.execute(filePath);
                    }
                    //nk realm:
                    /*DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask(getActivity(), appName, strSDCardUrl, flag);
                    fromURLTask.execute(filePath);*/
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "startDownloadException: " + e);
            ImproveHelper.improveException(getActivity(), TAG, "startDownload", e);
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

    private boolean deleteFileifExists(String sdcardUrl, String filename) {
        boolean isFiledToDeleted = false;
        try {
            try {
                File cDir = getActivity().getExternalFilesDir(sdcardUrl);
                File appSpecificExternalDir = new File(cDir.getAbsolutePath(), filename);
                Log.d(TAG, "FileExitsDelelte: " + appSpecificExternalDir);
                isFiledToDeleted = appSpecificExternalDir.delete();
            } catch (Exception e) {
                ImproveHelper.improveException(getActivity(), TAG, "deleteFileifExists", e);
            }

        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "deleteFileifExists", e);
        }
        return isFiledToDeleted;

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
            ImproveHelper.improveException(getActivity(), TAG, "filter", e);
        }
    }

    private void mAppsListRefreshAPI(final RefreshMain refreshMain) {
        try {
            Call<RefreshService> refreshServiceCall = getServices.getRefreshService(sessionManager.getAuthorizationTokenId(), refreshMain);

            refreshServiceCall.enqueue(new Callback<RefreshService>() {
                @Override
                public void onResponse(Call<RefreshService> call, Response<RefreshService> response) {
                try {

                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("200")) {
//                        Gson gson = new Gson();
//                        String jsonObject = gson.toJson(response.body());
//                        Log.d(TAG, "onRefreshJSON: " + jsonObject);

                            //AppsList
                            if (response.body().getPageName() != null && response.body().getPageName().size() > 0) {

                                List<AppDetails> appDetailsListResp = response.body().getPageName();
                                if (appDetailsListResp != null && appDetailsListResp.size() > 0) {
                                    for (int i = 0; i < appDetailsListResp.size(); i++) {
                                        //db creation at refresh time pending nk
                                        if (appDetailsListResp.get(i).getAppMode() != null && !appDetailsListResp.get(i).getAppMode().equalsIgnoreCase("Online") && appDetailsListResp.get(i).getTableSettingsType() !=null && appDetailsListResp.get(i).getTableSettingsType().equalsIgnoreCase("Create New Table")) {

                                            for (int j = 0; j < appDetailsListResp.get(i).getWorkSpaceAppsList().size(); j++) {
                                                improveDataBase.createTablesBasedOnConditions(appDetailsListResp.get(i).getWorkSpaceAppsList().get(j));
                                            }
                                        }
                                        if (appDetailsListResp.get(i).getZ_Status_flag().equalsIgnoreCase("Update")) {
                                            String refRespAppName = appDetailsListResp.get(i).getAppName();
                                            List<AppDetails> appDetailsListUpdate = new ArrayList<>();
                                            if (!appDetailsListResp.get(i).getAppType().equalsIgnoreCase("WorkSpace") && !appDetailsListResp.get(i).getWorkspaceAs().equalsIgnoreCase("Individual")) {
                                                appDetailsListUpdate.add(appDetailsListResp.get(i));
                                                improveDataBase.updateAppsList(appDetailsListResp.get(i), refRespAppName, strOrgId, sessionManager.getUserDataFromSession().getUserID(),"");
                                                createFolderPermissions(appDetailsListUpdate);
                                            } else if (appDetailsListResp.get(i).getAppType().equalsIgnoreCase("WorkSpace") &&
                                                    appDetailsListResp.get(i).getWorkSpaceAppsList().size() > 0) {
                                                List<AppDetails.WorkSpaceAppsList> workspaceListUpdate = appDetailsListResp.get(i).getWorkSpaceAppsList();
                                                appDetailsListUpdate.add(appDetailsListResp.get(i));
                                                for (int j = 0; j < workspaceListUpdate.size(); j++) {

//                                                    improveDataBase.updateAppsList(appDetailsListResp.get(i), workspaceListUpdate.get(j).getAppName().split("\\^")[0], strOrgId, sessionManager.getUserDataFromSession().getUserID(),appDetailsListResp.get(i).getAppName());
                                                    improveDataBase.updateAppsListWorkSpace(appDetailsListResp.get(i),workspaceListUpdate.get(j), workspaceListUpdate.get(j).getAppName().split("\\^")[0], strOrgId, sessionManager.getUserDataFromSession().getUserID(),appDetailsListResp.get(i).getAppName());
                                                    createFolderPermissions(appDetailsListUpdate);
                                                }


                                            }
                                        } else if (appDetailsListResp.get(i).getZ_Status_flag().equalsIgnoreCase("Delete")) {

                                            String refRespAppNameDelete = appDetailsListResp.get(i).getAppName();

                                            if (appDetailsListResp.get(i).getAppType()!=null && !appDetailsListResp.get(i).getAppType().equalsIgnoreCase("") && !appDetailsListResp.get(i).getAppType().equalsIgnoreCase("WorkSpace")) {
                                                improveDataBase.updateAppsList(appDetailsListResp.get(i), refRespAppNameDelete, strOrgId, sessionManager.getUserDataFromSession().getUserID(),"");

                                            } else if (appDetailsListResp.get(i).getWorkSpaceAppsList() != null && appDetailsListResp.get(i).getWorkSpaceAppsList().size() > 0) {
                                                List<AppDetails.WorkSpaceAppsList> workspaceListUpdate = appDetailsListResp.get(i).getWorkSpaceAppsList();
                                                for (int j = 0; j < workspaceListUpdate.size(); j++) {
//                                                    improveDataBase.deleteWorkspaceAppsList(workspaceListUpdate.get(j).getAppName());
                                                    improveDataBase.deleteWorkspaceApp(workspaceListUpdate.get(j).getAppName(),workspaceListUpdate.get(j).getDistrubutionID());

                                                }
                                            }
                                        } else if (appDetailsListResp.get(i).getZ_Status_flag().equalsIgnoreCase("Insert")) { // newly added files
                                            List<AppDetails> appDetailsListInsert = new ArrayList<>();
                                            appDetailsListInsert.add(appDetailsListResp.get(i));
                                            if (!appDetailsListResp.get(i).getAppType().equalsIgnoreCase("WorkSpace")) {
                                                improveDataBase.insertIntoAppsListTable(appDetailsListInsert, strOrgId, sessionManager.getUserDataFromSession().getUserID());
                                                createFolderPermissions(appDetailsListInsert);
                                            } else if (appDetailsListResp.get(i).getAppType().equalsIgnoreCase("WorkSpace") &&
                                                    appDetailsListResp.get(i).getWorkSpaceAppsList().size() > 0) {
                                                improveDataBase.deleteWorkspaceApp(appDetailsListResp.get(i).getAppName(), appDetailsListResp.get(i).getDistrubutionID());//Except Workspace
                                                improveDataBase.deleteWorkspaceAppsListNew(appDetailsListResp.get(i).getAppName(), appDetailsListResp.get(i).getDistrubutionID(), appDetailsListResp.get(i).getWorkSpaceAppsList());//Workspace and apps inside workspace
                                                improveDataBase.insertIntoAppsListTable(appDetailsListInsert, strOrgId, sessionManager.getUserDataFromSession().getUserID());
                                                createFolderPermissions(appDetailsListInsert);
                                            }
                                        }
                                    }
                                }
                            }

                            /*if (response.body().getDatacontrol() != null && response.body().getDatacontrol().size() > 0) {

                        List<DataControls> dataControlsResp = response.body().getDatacontrol();
                        if (dataControlsResp != null && dataControlsResp.size() > 0) {
                            for (int i = 0; i < dataControlsResp.size(); i++) {
                                Log.d(TAG, "dataControlsRespCheck: "+dataControlsResp.get(i).getControlName()+" Rstatus "+dataControlsResp.get(i).getZ_Status_flag());
                                if (dataControlsResp.get(i).getZ_Status_flag().equalsIgnoreCase("Update")) {
                                    String refRespDCUpdate = dataControlsResp.get(i).getControlName();
                                    improveDataBase.updateDataControlsList(dataControlsResp.get(i), refRespDCUpdate, strOrgId, sessionManager.getUserDataFromSession().getUserID());
                                    String filePath = dataControlsResp.get(i).getTextFilePath().trim();
                                    startDownload( filePath, "", 0);
//                                    startDownloadDataControl( filePath, "", 0, 1);
                                } else if (dataControlsResp.get(i).getZ_Status_flag().equalsIgnoreCase("Deleted")) {
                                    String refRespDCDelete = dataControlsResp.get(i).getControlName();
                                    String strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/";
                                    deleteFileifExists(strSDCardUrl, "DC_"+dataControlsResp.get(i).getControlName()+".txt");
                                    improveDataBase.updateDataControlsList(dataControlsResp.get(i), refRespDCDelete, strOrgId, sessionManager.getUserDataFromSession().getUserID());
                                } else if (dataControlsResp.get(i).getZ_Status_flag().equalsIgnoreCase("Insert")) { // newly added files
                                    improveDataBase.insertIntoDataControlTable(dataControlsResp.get(i), strOrgId, sessionManager.getUserDataFromSession().getUserID());
                                    String filePath = dataControlsResp.get(i).getTextFilePath().trim();
                                    startDownload( filePath, "", 0);
//                                    startDownloadDataControl( filePath, "", 0, 1);

                                }
                            }
                        }
                    }*/

//                         API Names
                            if (response.body().getApiname() != null && response.body().getApiname().size() > 0) {

                                List<APIDetails> apiNamesListResp = response.body().getApiname();
                                if (apiNamesListResp != null && apiNamesListResp.size() > 0) {
                                    for (int i = 0; i < apiNamesListResp.size(); i++) {

//                                    APIDetails apiNamesList = improveDataBase.getDataFromAPINamesRefresh(refResp, strOrgId, sessionManager.getUserDataFromSession().getUserId());

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
                                        //db creation at refresh time
                                        if (dashboardListResp.get(i).getAppMode() != null && !dashboardListResp.get(i).getAppMode().equalsIgnoreCase("Online")) {
                                            improveDataBase.createTablesBasedOnConditions(dashboardListResp.get(i));
                                        }

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
                                        //db creation at refresh time
                                        if (reportsListResp.get(i).getAppMode() != null && !reportsListResp.get(i).getAppMode().equalsIgnoreCase("Online")) {
                                            improveDataBase.createTablesBasedOnConditions(reportsListResp.get(i));
                                        }
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

                            UpdatePostBaseDataControls();

//                    loadAppsAfterRefresh();
//                    updateFirebaseStatus();
//                    AppConstants.PROGRESS_APPS = 0;

                        } else {
                            ImproveHelper.showToast(context, response.body().Message);
                            loadAppsAfterRefresh();
                            improveHelper.dismissProgressDialog();
                        }
                    } else {
                        loadAppsAfterRefresh();
                        improveHelper.dismissProgressDialog();
                    }
                } catch (Exception e) {
                    improveHelper.dismissProgressDialog();
                    ImproveHelper.improveException(getActivity(), TAG, "mAppsListRefreshAPI_onResponseRefreshException", e);
                }
                }

                @Override
                public void onFailure(Call<RefreshService> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "onResponseRefreshFail: " + t);
                    ImproveHelper.improveException(getActivity(), TAG, "mAppsListRefreshAPI", (Exception) t);

                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "mAppsListRefreshAPI", e);
        }
    }

    private void refreshDataControls(List<DataControlsAndApis.DataControlDetails> DataControlDetails) {
        try {
            for (int i = 0; i < DataControlDetails.size(); i++) {
                if (DataControlDetails.get(i).getStatus().equalsIgnoreCase("Update")) {
                    String refRespDCUpdate = DataControlDetails.get(i).getDataControlName();
                    improveDataBase.updateNewDataControlsList(DataControlDetails.get(i), refRespDCUpdate, strOrgId, sessionManager.getUserDataFromSession().getUserID());
                    String filePath = DataControlDetails.get(i).getTextFilePath().trim();
    //                startDownload(filePath, "", 0, true, DataControlDetails.get(i));
    //                                startDownloadDataControl(filePath, "", 0, 1);
                } else if (DataControlDetails.get(i).getStatus().equalsIgnoreCase("Deleted")) {
                    String refRespDCDelete = DataControlDetails.get(i).getDataControlName();
                    improveDataBase.updateNewDataControlsList(DataControlDetails.get(i), refRespDCDelete, strOrgId, sessionManager.getUserDataFromSession().getUserID());
                } else if (DataControlDetails.get(i).getStatus().equalsIgnoreCase("New")) { // newly added files
                    improveDataBase.insertIntoNewDataControlTable(DataControlDetails.get(i), strOrgId, sessionManager.getUserDataFromSession().getUserID());
                    String filePath = DataControlDetails.get(i).getTextFilePath().trim();
    //                startDownload(filePath, "", 0, true, DataControlDetails.get(i));
    //                                startDownloadDataControl(filePath, "", 0, 1);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "refreshDataControls", e);

        }
    }

    public void UpdatePostBaseDataControls() {
        try {
            List<DataControls> appDataControlsList = improveDataBase.getDataControlsList(strOrgId, sessionManager.getUserDataFromSession().getUserID());
            JSONArray OldDataControlArr = new JSONArray();

            for (int i = 0; i < appDataControlsList.size(); i++) {


                JSONObject oldData = new JSONObject();
                oldData.put("DataControlName", appDataControlsList.get(i).getControlName());
                oldData.put("Version", appDataControlsList.get(i).getVersion());
                oldData.put("AccessingType", appDataControlsList.get(i).getAccessibility());

                OldDataControlArr.put(oldData);
            }

            JSONObject DataControlsObj = new JSONObject();
            DataControlsObj.put("OrgID", strOrgId);
            DataControlsObj.put("UserID", sessionManager.getUserDataFromSession().getUserID());
            DataControlsObj.put("PostID", sessionManager.getPostsFromSession());


            DataControlsObj.put("DataControlDetails", OldDataControlArr);


            List<PostSubLocationsModel> Sublocations = new ArrayList<>();
            SessionManager sessionManager = new SessionManager(context);
            List<PostsMasterModel> postDetails = sessionManager.getUserPostDetails();
            String postId = sessionManager.getPostsFromSession();
            if (postDetails != null && postDetails.size() > 0) {
                for (int i = 0; i < postDetails.size(); i++) {
                    PostsMasterModel userPostDetails = postDetails.get(i);
                    if (!postId.contentEquals("") && userPostDetails.getID().contentEquals(postId)) {
                        Sublocations = userPostDetails.getPostSubLocations();
                    }
                }
            }
            if (Sublocations != null) {
                Gson GsonParser1 = new Gson();
                String element1 = GsonParser1.toJson(Sublocations,
                        new TypeToken<ArrayList<PostSubLocationsModel>>() {
                        }.getType());
                JSONArray SublocationsArr = new JSONArray(element1);
                DataControlsObj.put("PostSubLocations", SublocationsArr);
            }
            JsonParser jsonParser = new JsonParser();
            JsonObject jo_DataControlsObj = (JsonObject) jsonParser.parse(DataControlsObj.toString());

            Call<DataControlsAndApis> getAllDataControls = getServices.GetMasterControlsData(sessionManager.getAuthorizationTokenId(), jo_DataControlsObj);
            getAllDataControls.enqueue(new Callback<DataControlsAndApis>() {
                @Override
                public void onResponse(Call<DataControlsAndApis> call, Response<DataControlsAndApis> response) {

                    List<DataControlsAndApis.DataControlDetails> DataControlDetails = response.body().getDataControlDetails();

                   /* createAppFolderAndDownloadDataControls(DataControlDetails);

                    for (int i = 0; i < DataControlDetails.size() ; i++) {

                        improveDataBase.insertIntoNewDataControlTable(DataControlDetails.get(i), strOrgId, sessionManager.getUserDataFromSession().getUserId());
                    }*/


                    // DataControls refresh
                    if (DataControlDetails != null && DataControlDetails.size() > 0) {
                        DataControlDetailsFiles = DataControlDetails;
                        AppConstants.dataControlRunnableFlag = true;
                        refreshDataControlsRunnable.run();
                        //refreshDataControls(DataControlDetails);
                    }

                    loadAppsAfterRefresh();
                    updateFirebaseStatus();
                    AppConstants.PROGRESS_APPS = 0;

                }

                @Override
                public void onFailure(Call<DataControlsAndApis> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    ImproveHelper.improveException(getActivity(), TAG, "UpdatePostBaseDataControls", (Exception) t);
                }
            });

        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            ImproveHelper.improveException(getActivity(), TAG, "UpdatePostBaseDataControls", e);
        }

    }


    public void loadAppsAfterRefresh() {
        try {
            apiDetailsList = improveDataBase.getDataFromAppsListTable(strOrgId, sessionManager.getUserDataFromSession().getUserID(), sessionManager.getUserTypeIdsFromSession(), sessionManager.getPostsFromSession(), displayAs, sessionManager.getUserTypeIdsFromSession());
            if (apiDetailsList != null && apiDetailsList.size() > 0) {
//            cet_appsSearch.setVisibility(View.VISIBLE); // change
//                cet_appsSearch.setVisibility(View.GONE);
                ct_alNoRecords.setVisibility(View.GONE);
                rv_apps.setVisibility(View.VISIBLE);
                appsAdapter = new AppsAdapter(getActivity(), apiDetailsList, true, false);
                rv_apps.setAdapter(appsAdapter);
                appsAdapter.notifyDataSetChanged();
                improveHelper.dismissProgressDialog();
            } else {
                improveHelper.dismissProgressDialog();
//                cet_appsSearch.setVisibility(View.GONE);
                ct_alNoRecords.setVisibility(View.VISIBLE);
                rv_apps.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "loadAppsAfterRefresh", e);
        }
    }

    /*public void refreshCheckingAPiChanges() {
        try {
            List<AppDetails> appDetailsList_QueryNotDistributed = improveDataBase.getDataFromAppsListTableNotDistributed(strOrgId, sessionManager.getUserDataFromSession().getUserID());
            List<AppDetails> appDetailsList = improveDataBase.getDataFromAppsListTableRefresh(strOrgId, sessionManager.getUserDataFromSession().getUserID(), "", displayAs);
            List<DataControls> appDataControlsList = improveDataBase.getDataControlsList(strOrgId, sessionManager.getUserDataFromSession().getUserID());
            List<APIDetails> apiNamesList = improveDataBase.getDataFromAPINames(strOrgId, sessionManager.getUserDataFromSession().getUserID());

            RefreshMain refreshMain = new RefreshMain();
            refreshMain.setUserID(sessionManager.getUserDataFromSession().getUserID());
            refreshMain.setOrgId(strOrgId);
//            refreshMain.setPostID(sessionManager.getPostsFromSession());

            // QueryNot Distributed
            List<AppDetailsMArr> appDetailsMArrArrayListQ = new ArrayList<>();
            for (int i = 0; i < appDetailsList_QueryNotDistributed.size(); i++) {
                AppDetailsMArr appDetailsMArrQ = new AppDetailsMArr();
                appDetailsMArrQ.setPagename(appDetailsList_QueryNotDistributed.get(i).getAppName());
                appDetailsMArrQ.setVersion(appDetailsList_QueryNotDistributed.get(i).getAppVersion());
                appDetailsMArrArrayListQ.add(appDetailsMArrQ);
            }
            refreshMain.setQueryFormArr(appDetailsMArrArrayListQ);

            // All Apps datacollection and child...
            List<AppDetailsMArr> appDetailsMArrArrayList = new ArrayList<>();
            for (int i = 0; i < appDetailsList.size(); i++) {

                if (appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.DATA_COLLECTION)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.AUTO_REPORTS)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.QUERY_FORM)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.CHILD_FORM)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.CHILD_FORM_NEW)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.WEB_Link)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.REPORT)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.MULTI_FORM)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.ML)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.WORKSPACE)
                ) {
                    Log.d(TAG, "refreshSendAppName: " + appDetailsList.get(i).getAppName());
                    AppDetailsMArr appDetailsMArr = new AppDetailsMArr();
                    appDetailsMArr.setPagename(appDetailsList.get(i).getAppName());
                    appDetailsMArr.setVersion(appDetailsList.get(i).getAppVersion());
                    appDetailsMArr.setAppType(appDetailsList.get(i).getAppType());
                    appDetailsMArr.setDid(appDetailsList.get(i).getDistrubutionID());
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

            *//* App Refresh APi*//*
            mAppsListRefreshAPI(refreshMain);

//        } catch (JSONException e) {

//            e.printStackTrace();
//        }
        } catch (Exception e) {
            Log.getStackTraceString(e);
            ImproveHelper.improveException(getActivity(), TAG, "refreshCheckingAPi", e);
        }
    }*/

    public void refreshCheckingAPi() {
        try {
            List<AppDetails> appDetailsList_QueryNotDistributed = improveDataBase.getDataFromAppsListTableNotDistributed(strOrgId, sessionManager.getUserDataFromSession().getUserID());
            List<AppDetails> appDetailsList = improveDataBase.getDataFromAppsListTableRefresh(strOrgId, sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession(), displayAs, sessionManager.getUserTypeIdsFromSession());
            List<DataControls> appDataControlsList = improveDataBase.getDataControlsList(strOrgId, sessionManager.getUserDataFromSession().getUserID());
            List<APIDetails> apiNamesList = improveDataBase.getDataFromAPINames(strOrgId, sessionManager.getUserDataFromSession().getUserID());

            RefreshMain refreshMain = new RefreshMain();
            refreshMain.setUserID(sessionManager.getUserDataFromSession().getUserID());
            refreshMain.setOrgId(strOrgId);
            refreshMain.setPostID(sessionManager.getPostsFromSession());
//            refreshMain.setOrgId(sessionManager.getPostsFromSession());

            // QueryNot Distributed
            List<AppDetailsMArr> appDetailsMArrArrayListQ = new ArrayList<>();
            for (int i = 0; i < appDetailsList_QueryNotDistributed.size(); i++) {
                AppDetailsMArr appDetailsMArrQ = new AppDetailsMArr();
                appDetailsMArrQ.setPagename(appDetailsList_QueryNotDistributed.get(i).getAppName());
                appDetailsMArrQ.setVersion(appDetailsList_QueryNotDistributed.get(i).getAppVersion());
                appDetailsMArrArrayListQ.add(appDetailsMArrQ);
            }
            refreshMain.setQueryFormArr(appDetailsMArrArrayListQ);

            // All Apps datacollection and child...
            List<AppDetailsMArr> appDetailsMArrArrayList = new ArrayList<>();
            for (int i = 0; i < appDetailsList.size(); i++) {

                if (appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.DATA_COLLECTION)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.AUTO_REPORTS)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.QUERY_FORM)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.CHILD_FORM)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.CHILD_FORM_NEW)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.WEB_Link)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.REPORT)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.MULTI_FORM)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.ML)
                        || appDetailsList.get(i).getAppType().equalsIgnoreCase(AppConstants.WORKSPACE)
                ) {
                    Log.d(TAG, "refreshSendAppName: " + appDetailsList.get(i).getAppName());
                    AppDetailsMArr appDetailsMArr = new AppDetailsMArr();
                    appDetailsMArr.setPagename(appDetailsList.get(i).getAppName());
                    appDetailsMArr.setVersion(appDetailsList.get(i).getAppVersion());
                    appDetailsMArr.setSharingVersion(appDetailsList.get(i).getSharingVersion());
                    appDetailsMArr.setDid(appDetailsList.get(i).getDistrubutionID());
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
//                dataControlsMArr.setVersion("13");

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

//        } catch (JSONException e) {

//            e.printStackTrace();
//        }
        } catch (Exception e) {
            Log.getStackTraceString(e);
            ImproveHelper.improveException(getActivity(), TAG, "refreshCheckingAPi", e);
        }
    }

    public void createFolderPermissions(List<AppDetails> appDetailsList) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getActivity() != null && getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
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
            ImproveHelper.improveException(getActivity(), TAG, "createFolderPermissions", e);
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
//                            mAppsListAPI(getAllAppNamesData);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, "onCancelledAppStatus: " + databaseError);
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "firebaseAppsListener", e);

        }
    }

    public void updateFirebaseStatus() {
        try {
            context = getActivity();
            sessionManager = new SessionManager(context);
            mFirebaseDatabaseReference = FirebaseDatabase.getInstance("https://improvecommunication-c08c9.firebaseio.com").getReference(sessionManager.getOrgIdFromSession());
            mFirebaseDatabaseReference.child("Users").
                    orderByChild("Mobile").equalTo(sessionManager.
                            getUserDataFromSession().getPhoneNo())
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
            ImproveHelper.improveException(getActivity(), TAG, "updateFirebaseStatus", e);
        }
    }

    //    public void updateFirebaseStatus() {
//        context = getActivity();
//        sessionManager = new SessionManager(context);
//        mFirebaseDatabaseReference = FirebaseDatabase.getInstance("https://bhargocommunication-8c36e.firebaseio.com").getReference(sessionManager.getOrgIdFromSession());
//        mFirebaseDatabaseReference.child("Users").
//                orderByChild("Mobile").equalTo(sessionManager.
//                getUserDataFromSession().getPhoneNo())
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
    public interface DownloadFileListener {
        void onSuccess(File file);

        void onFailed(String errorMessage);
    }

    public class DownloadFileFromURLTask extends AsyncTask<String, String, String> {

        String strFileName, strFileNameUnderscore, strAppName, strSDCardUrl;
        File file, saveFilePath;
        Context context;
        int flag = 0;
        DownloadFileListener downloadFileListener;

        public DownloadFileFromURLTask(Context context, String strAppName, String strSDCardUrl, int flag, DownloadFileListener downloadFileListener) {
            this.context = context;
            this.strAppName = strAppName;
            this.strSDCardUrl = strSDCardUrl;
            this.flag = flag;
            this.downloadFileListener = downloadFileListener;
        }
        public DownloadFileFromURLTask(Context context, String strAppName, String strSDCardUrl, int flag) {
            this.context = context;
            this.strAppName = strAppName;
            this.strSDCardUrl = strSDCardUrl;
            this.flag = flag;

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
                /*if (flag != 1 && ((strFileName.contentEquals("DepartmentMaster.txt"))
                        || (strFileName.contentEquals("DesignationMaster.txt"))
                        || (strFileName.contentEquals("LocationMaster.txt"))
                        || (strFileName.contentEquals("PostingMaster.txt"))
                )) {*/
                if (flag == 1) { // Apps
                    strFileName = strFileNameUnderscore;
                }else { // DataControls
                    strFileName = "DC_" + strFileNameUnderscore;
                }
//                }
//                String strSeparatePaths = AppConstants.API_NAME_CHANGE +"/"+strAppName.replaceAll(" ", "_")+"/";
                File cDir = context.getExternalFilesDir(strSDCardUrl);
                saveFilePath = new File(cDir.getAbsolutePath(), strFileName);
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
                saveFilePath = null;
                Log.e("Error: ", e.getMessage());
            }

            if (saveFilePath == null) {
                return null;
            } else {
                return saveFilePath.getAbsolutePath();
            }
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
            Log.d(TAG, "onPostExecuteAppsList: " + strFileName);
            if (file_url == null && downloadFileListener != null) {
                downloadFileListener.onFailed("Failed To Download File. Try Again!");
            } else {
                if(downloadFileListener != null) {
                    downloadFileListener.onSuccess(new File(file_url));
                }
            }
        }
    }

    private String voiceContent;
    public void voiceInputService(String content){
        voiceContent = content;
        try {
            if(appsAdapter!=null&&appsAdapter.getData().size()>0) {
                List<AppDetails> appList = appsAdapter.getData();
//            showProgressDialog("Getting Data");
//            String content = " Content: My name is Kumar aged 30 and mobile number, email id are 9959950913 and abc@gmail.com respectively. I am male studying in Class 1";
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Form names List : ");
                for (int i = 0; i <appList.size() ; i++) {
                    if(i!=0){
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(appList.get(i).getAppName());
                }
                String note = "  Note : Now Select one form from the list which the content describes. Result should be json with single key result  without explanation. If nothing matches revert result as none";
                stringBuilder.append(" Content: ").append(content).append(note);
                GetChatGPTService getChatGPTService = RetrofitUtils.getChatGPTService();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("model", "gpt-3.5-turbo");
                JSONArray jsonArray = new JSONArray();
                JSONObject object = new JSONObject();
                object.put("role", "user");
                object.put("content", stringBuilder.toString());
                jsonArray.put(object);
                jsonObject.put("messages", jsonArray);
                JsonObject jo = (JsonObject) JsonParser.parseString(jsonObject.toString());
                String token = "Bearer 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";
                Call<ResponseBody> call = getChatGPTService.chatGptService(token, jo);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.body() != null) {
                            JSONObject resp = null;
                            try {
                                resp = new JSONObject(response.body().string());
                                Log.d("resultFormName: ",resp.toString());
                                String content = resp.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
                                JSONObject contentObj = new JSONObject(content);
                                Log.d("resultFormName: ",contentObj.toString());
                                String formName = contentObj.getString("result");
                                if(formName.equalsIgnoreCase("none")){
                                    ImproveHelper.showToast(getActivity(),"No Match Found ...");
                                }else {
                                    for (int i = 0; i < appList.size(); i++) {
                                        if (appList.get(i).getAppName().contentEquals(formName)) {
                                            appList.get(i).setAdapterPosition(i);
                                            appList.get(i).setVoiceToTextContent(voiceContent);
                                            appsAdapter.openApp(appList.get(i));
                                        }

                                    }
                                }
                            } catch (JSONException | IOException e) {
                                //dismissProgressDialog();
                                ImproveHelper.showToast(getActivity(),"No Match Found ...");
                                e.printStackTrace();
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //dismissProgressDialog();
                        Log.d("resultFormName: ",t.getMessage());
                        ImproveHelper.showToast(getActivity(),t.getMessage());
                    }
                });
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

}
