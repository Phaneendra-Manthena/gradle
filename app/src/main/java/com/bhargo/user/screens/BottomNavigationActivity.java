package com.bhargo.user.screens;

import static com.bhargo.user.utils.AppConstants.FromFlashScreen;
import static com.bhargo.user.utils.AppConstants.FromNotification;
import static com.bhargo.user.utils.AppConstants.FromNotificationOnlyInTask;
import static com.bhargo.user.utils.AppConstants.Notification_PageName;
import static com.bhargo.user.utils.ImproveDataBase.DEPLOYMENT_TABLE;
import static com.bhargo.user.utils.ImproveDataBase.OUT_TASK_SEND_COMMENTS_OFFLINE_TABLE;
import static com.bhargo.user.utils.ImproveDataBase.SEND_COMMENTS_OFFLINE_TABLE;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.adapters.LanguageSettingsAdapter;
import com.bhargo.user.adapters.PostChangeAdapter;
import com.bhargo.user.adapters.PostSelectionAdapter;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.fragments.AppsListFragment;
import com.bhargo.user.fragments.InfoFragment;
import com.bhargo.user.fragments.ProfileFragment;
import com.bhargo.user.fragments.ReportsListFragment;
import com.bhargo.user.fragments.TasksFragment;
import com.bhargo.user.fragments.VideosFragment;
import com.bhargo.user.interfaces.AppsListener;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.notifications.NotificationsActivity;
import com.bhargo.user.pojos.CommentsResponse;
import com.bhargo.user.pojos.CreateTaskResponse;
import com.bhargo.user.pojos.FilesTimeSpentModel;
import com.bhargo.user.pojos.FirebaseSyncStatus;
import com.bhargo.user.pojos.InsertComments;
import com.bhargo.user.pojos.InsertUserFileVisitsModel;
import com.bhargo.user.pojos.InsertUserFileVisitsResponse;
import com.bhargo.user.pojos.InsertUserVisitsModel;
import com.bhargo.user.pojos.InsertUserVisitsResponse;
import com.bhargo.user.pojos.LanguageTypeModel;
import com.bhargo.user.pojos.PostsMasterModel;
import com.bhargo.user.pojos.TaskCommentDetails;
import com.bhargo.user.pojos.UserTypesMasterModel;
import com.bhargo.user.pojos.firebase.UserDetails;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomNavigationActivity extends AppCompatActivity implements PostSelectionAdapter.ItemClickListener, LanguageSettingsAdapter.ItemClickListenerL {

    private static final String TAG = "BottomNavigationActivit";
    public static CustomTextView ct_FragmentTitle;
    public static ImageView iv_tasksFilter, iv_appListRefresh, iv_appListSearch;
    public static int FROM_TAB = 0;
    static boolean isUserVisits = true;
    public ReportsListFragment reportsListFragment;
    //    public AppsListFragment appsListFragment;
    public AppsListFragment appsListFragment;
    public VideosFragment videosFragment;
    public TasksFragment tasksFragment;
    protected OnBackPressedListener onBackPressedListener;
    BottomNavigationView bottomNavigation;
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener;
    Context context;
    ImproveDataBase improveDataBase;
    ImageView iv_sync, iv_profile, iv_language, iv_notifications,iv_voice_input;
    InfoFragment infoFragment;
    androidx.appcompat.widget.Toolbar tb_hs;
    LinearLayout ll_OrgSpinnerData, ll_menuItems, ll_fragmentTitle;
    ImproveHelper improveHelper;
    SessionManager sessionManager;
    GetServices getServices;
    int reportsRefresh = 0;
    int appsRefresh = 0;
    int communicationRefresh = 0;
    int programsRefresh = 0;
    int libraryRefresh = 0;
    String firebaseURL1 = "https://improvecommunication-c08c9.firebaseio.com/";
    String firebaseURL = "https://bhargouser-default-rtdb.firebaseio.com/";
    ValueEventListener eventListener;
    String appLanguage = "en";
    String[] TabsData;
    RecyclerView bs_rvLanguage;
    LanguageSettingsAdapter languageSettingsAdapter;
    View mViewBg;
    BottomSheetBehavior sheetBehaviorPosts;
    private String strOrgName;
    private String i_OrgId;
    private AppsListener appsListener;
    private Bundle bundle;
    private String strAppsRefreshNotification;
    private String FromAction;
    private DatabaseReference firebaseDatabase;
    private Fragment currentFragment;
    private ProfileFragment profileFragment;
    private Dialog postAlertDialog;
    private PostSelectionAdapter postSelectionAdapter;
    private boolean fromCallAction;
    private boolean fromCallWindowAction;
    private LinearLayout ll_action_buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(null);
            setBhargoTheme(this,AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
        /*if (AppConstants.THEME.equalsIgnoreCase("THEME2")) {
            setTheme(R.style.AppTheme2);
        }else if (AppConstants.THEME.equalsIgnoreCase("THEME1")) {
            setTheme(R.style.AppTheme);
        }*/
//        AppConstants.IS_FORM_THEME =  true;

//        setTheme(setBhargoThemeNew(AppConstants.THEME,AppConstants.IS_FORM_THEME,AppConstants.FORM_THEME));

            setContentView(R.layout.activity_bottom_navigation_default);

            context = BottomNavigationActivity.this;
            appLanguage = ImproveHelper.getLocale(context);
            if (getIntent() != null && getIntent().hasExtra("FromCallWindowAction")) {
                fromCallWindowAction = getIntent().getBooleanExtra("FromCallWindowAction", false);
            }
            if (sessionManager == null) {
                sessionManager = new SessionManager(context);
            }
//        Log.d(TAG, "deviceid: " + sessionManager.getDeviceIdFromSession());
            improveHelper = new ImproveHelper(context);
            improveDataBase = new ImproveDataBase(context);
            getServices = RetrofitUtils.getUserService();
            bottomNavigation = findViewById(R.id.bottom_navigation);
            tb_hs = findViewById(R.id.tb_hs);
            setSupportActionBar(tb_hs);
            ll_action_buttons = findViewById(R.id.ll_action_buttons);
            iv_sync = findViewById(R.id.iv_sync);
            iv_profile = findViewById(R.id.iv_profile);
            iv_notifications = findViewById(R.id.iv_notifications);
            iv_appListRefresh = findViewById(R.id.iv_appListRefresh);
            iv_appListSearch = findViewById(R.id.iv_appListSearch);
            iv_tasksFilter = findViewById(R.id.iv_tasksFilter);
            iv_voice_input = findViewById(R.id.iv_vtt);

            iv_language = findViewById(R.id.iv_language);

            ll_OrgSpinnerData = findViewById(R.id.ll_OrgSpinnerData);
            ll_menuItems = findViewById(R.id.ll_menuItems);
            ll_fragmentTitle = findViewById(R.id.ll_fragmentTitle);
            ct_FragmentTitle = findViewById(R.id.ct_FragmentTitle);
//        statusChangeListener();
            NestedScrollView bottom_sheet = findViewById(R.id.bottom_sheet_ls);
            BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
            mViewBg = findViewById(R.id.bs_bg);
            sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                        mViewBg.setVisibility(View.GONE);
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    mViewBg.setVisibility(View.VISIBLE);
                }
            });
            bs_rvLanguage = bottom_sheet.findViewById(R.id.bs_rvLanguage);

            if (fromCallWindowAction) {
                ll_action_buttons.setVisibility(View.GONE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else {
                ll_action_buttons.setVisibility(View.VISIBLE);
            }


            /*======Checking_====*/
            TabsData = AppConstants.WINDOWS_AVAILABLE.split("\\|");
            if (appLanguage.contentEquals("hi")) {
                TabsData = AppConstants.WINDOWS_AVAILABLE_HINDI.split("\\|");
            }
            if (!TabsData[0].split("\\^")[1].equalsIgnoreCase("Yes")) {
                bottomNavigation.getMenu().removeItem(R.id.navigation_appsList);
            } else {
                bottomNavigation.getMenu().findItem(R.id.navigation_appsList).setTitle(TabsData[0].split("\\^")[2]);
            }

            if (!TabsData[1].split("\\^")[1].equalsIgnoreCase("Yes")) {
                bottomNavigation.getMenu().removeItem(R.id.navigation_info);
            } else {
                bottomNavigation.getMenu().findItem(R.id.navigation_info).setTitle(TabsData[1].split("\\^")[2]);

            }

            if (!TabsData[2].split("\\^")[1].equalsIgnoreCase("Yes")) {
                bottomNavigation.getMenu().removeItem(R.id.navigation_reports);
            } else {
                bottomNavigation.getMenu().findItem(R.id.navigation_reports).setTitle(TabsData[2].split("\\^")[2]);
            }

            if (!TabsData[3].split("\\^")[1].equalsIgnoreCase("Yes")) {
                bottomNavigation.getMenu().removeItem(R.id.navigation_tasks);
            } else {
                bottomNavigation.getMenu().findItem(R.id.navigation_tasks).setTitle(TabsData[3].split("\\^")[2]);
            }

            if (!TabsData[4].split("\\^")[1].equalsIgnoreCase("Yes")) {
                bottomNavigation.getMenu().removeItem(R.id.navigation_videos);
            } else {
                bottomNavigation.getMenu().findItem(R.id.navigation_videos).setTitle(TabsData[4].split("\\^")[2]);
            }


//        appsListener = (AppsListener)context;

            firebaseDatabase = FirebaseDatabase.getInstance(firebaseURL).getReference(sessionManager.getOrgIdFromSession());
            postAlertDialog = new Dialog(context, R.style.AlertDialogLight);

            // Deployment OffLine
            if (isNetworkStatusAvialable(context) && improveDataBase.doesTableExist(DEPLOYMENT_TABLE)
                    && !improveDataBase.isTableEmpty(DEPLOYMENT_TABLE)) {
                uploadDeploymentData();
            } else {
                improveHelper.dismissProgressDialog();
                Log.d(TAG, "onCreate: " + "NoTable or Data Exist");
            }
            // InTaskSendComments OffLine
            if (isNetworkStatusAvialable(context) && improveDataBase.doesTableExist(SEND_COMMENTS_OFFLINE_TABLE)
                    && !improveDataBase.isTableEmpty(SEND_COMMENTS_OFFLINE_TABLE)) {
                sendCommentOffLineApi();
            } else {
                improveHelper.dismissProgressDialog();
                Log.d(TAG, "onCreate: " + "NoTable or Data Exist");
            }
            // OutTaskSendComments OffLine
            if (isNetworkStatusAvialable(context) && improveDataBase.doesTableExist(OUT_TASK_SEND_COMMENTS_OFFLINE_TABLE)
                    && !improveDataBase.isTableEmpty(OUT_TASK_SEND_COMMENTS_OFFLINE_TABLE)) {
                outTaskSendCommentOffLineApi();
            } else {
                improveHelper.dismissProgressDialog();
                Log.d(TAG, "onCreate: " + "NoTable or Data Exist");
            }

            Intent intentNotification = getIntent();
            if (AppConstants.FromNotificationOnlyInTask != null && !AppConstants.FromNotificationOnlyInTask.equalsIgnoreCase("") && FromNotificationOnlyInTask.equalsIgnoreCase("OnlyInTask")
                    && intentNotification != null && intentNotification.getStringExtra("FromNormalTask") != null
                    && !intentNotification.getStringExtra("FromNormalTask").isEmpty()) {

                AppConstants.IN_TASK_REFRESH_BOOLEAN = true;
                AppConstants.TASK_REFRESH = "InTaskRefresh";
                bottomNavigation.setSelectedItemId(R.id.navigation_tasks);
            } else if (AppConstants.FromNotificationOnlyCommunication != null
                    && !AppConstants.FromNotificationOnlyCommunication.equalsIgnoreCase("") && AppConstants.FromNotificationOnlyCommunication.equalsIgnoreCase("OnlyCommunication")) {

                bottomNavigation.setSelectedItemId(R.id.navigation_info);
            }
            /*Search Apps List*/

            iv_appListSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(BottomNavigationActivity.this, SearchApps.class));
                }
            });
            /*Refresh Apps List*/
            iv_appListRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
    //                if (isNetworkStatusAvialable(context)) {
    //                Log.d(TAG, "onClick: "+getInternetSpeed(context));
                    if(improveHelper.isInternetAvailable(context)){
                        currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
                        if (currentFragment != null) {

                            if (currentFragment.getTag().equalsIgnoreCase("VideosFragment")) {

                                videosFragment = new VideosFragment(1);
                                openFragment(videosFragment, "VideosFragment");

                            } else if (currentFragment.getTag().equalsIgnoreCase("AppsListFragment")
                                    || currentFragment.getTag().equalsIgnoreCase("AppsListFragmentRefresh")) {

    //                            appsListFragment = new AppsListFragment(1);
                                appsListFragment = new AppsListFragment(1);
                                openFragment(appsListFragment, "AppsListFragmentRefresh");

                            } else if (currentFragment.getTag().equalsIgnoreCase("ReportsListFragment")
                                    || currentFragment.getTag().equalsIgnoreCase("ReportsListFragmentRefresh")) {

                                reportsListFragment = new ReportsListFragment(1);
                                openFragment(reportsListFragment, "ReportsListFragmentRefresh");

                            } else if (currentFragment.getTag().equalsIgnoreCase("TasksFragment")) {

                                tasksFragment = new TasksFragment("0", "1");
                                openFragment(tasksFragment, "TasksFragment");

                            } else {
                                Log.d(TAG, "onClickCheck: " + "onClickCheck");
                            }
                        }
                    } else {

                        improveHelper.snackBarAlertActivities(context, v);
                    }
                }
            });


            /*Sync*/
            iv_sync.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(BottomNavigationActivity.this, OfflineSyncActivity.class));
                }
            });
            /*Notifications*/
            iv_notifications.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(BottomNavigationActivity.this, NotificationsActivity.class));
                    //startActivity(new Intent(BottomNavigationActivity.this, AlertsActivity.class));
                }
            });
            /*Notifications*/
            /*Profile*/
            iv_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
                    if (currentFragment != null) {
                        if (currentFragment.getTag() != null && currentFragment.getTag().contentEquals("InfoFragment")) {
                            sessionManager.activeFragment("I");
                        } else if (currentFragment.getTag() != null && currentFragment.getTag().contentEquals("ReportsListFragment")) {
                            sessionManager.activeFragment("R");
                        } else if (currentFragment.getTag() != null && currentFragment.getTag().contentEquals("ReportsListFragmentRefresh")) {
                            sessionManager.activeFragment("R");
                        } else if (currentFragment.getTag() != null && currentFragment.getTag().contentEquals("AppsListFragment")) {
                            sessionManager.activeFragment("A");
                        } else if (currentFragment.getTag() != null && currentFragment.getTag().contentEquals("AppsListFragmentRefresh")) {
                            sessionManager.activeFragment("A");
                        }
                    }
                    startActivity(new Intent(BottomNavigationActivity.this, ProfileActivity.class));
                }
            });
//        androidx.core.view.ViewCompat.setNestedScrollingEnabled(bs_rvLanguage, true);
            List<LanguageTypeModel> languageTypeModelsListMain = new ArrayList<>();
            languageTypeModelsListMain = loadLanguageData();
            languageSettingsAdapter = new LanguageSettingsAdapter(context, languageTypeModelsListMain, sheetBehavior);
            bs_rvLanguage.setAdapter(languageSettingsAdapter);
            languageSettingsAdapter.notifyDataSetChanged();
            if (languageSettingsAdapter != null) {
    //            languageSettingsAdapter.setClickListenerL(this);
            }

            ImageView iv_Cancel = findViewById(R.id.iv_Cancel);
            iv_Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            });
            iv_language.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            });

//        iv_language.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(BottomNavigationActivity.this);
//                LayoutInflater inflater = BottomNavigationActivity.this.getLayoutInflater();
//                View view = inflater.inflate(R.layout.dialog_language, null);
//
//                view.setClipToOutline(true);
//
//                RadioGroup rg_language = view.findViewById(R.id.rg_language);
//                RadioButton rb_eng = view.findViewById(R.id.rb_eng);
//                RadioButton rb_tel = view.findViewById(R.id.rb_tel);
//                RadioButton rb_hin = view.findViewById(R.id.rb_hin);
//
//                Button cancel = view.findViewById(R.id.btn_cancel);
//
//                builder.setCancelable(false);
//                builder.setView(view);
//
//                AlertDialog dialog = builder.show();
//
//                if(appLanguage!=null){
//                    if(appLanguage.contentEquals("en")){
//                        rb_eng.setChecked(true);
//                    }else if(appLanguage.contentEquals("hi")){
//                        rb_hin.setChecked(true);
//                    }
//                }else{
//                    rb_eng.setChecked(true);
//                }
//
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//
//
//                rg_language.setOnCheckedChangeListener((group, checkedId) -> {
//                    currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
//                    if (checkedId == R.id.rb_eng) {
//                        ImproveHelper.saveLocale(BottomNavigationActivity.this, AppConstants.LANG_ENGLISH);
//                        ImproveHelper.changeLanguage(BottomNavigationActivity.this, AppConstants.LANG_ENGLISH);
//                    } else if (checkedId == R.id.rb_tel) {
//                        ImproveHelper.saveLocale(BottomNavigationActivity.this, AppConstants.LANG_TELUGU);
//                        ImproveHelper.changeLanguage(BottomNavigationActivity.this, AppConstants.LANG_TELUGU);
//                    } else if (checkedId == R.id.rb_hin) {
//                        ImproveHelper.saveLocale(BottomNavigationActivity.this, AppConstants.LANG_HINDI);
//                        ImproveHelper.changeLanguage(BottomNavigationActivity.this, AppConstants.LANG_HINDI);
//                    } else if (checkedId == R.id.rb_tamil) {
//                        ImproveHelper.saveLocale(BottomNavigationActivity.this, AppConstants.LANG_TAMIL);
//                        ImproveHelper.changeLanguage(BottomNavigationActivity.this, AppConstants.LANG_TAMIL);
//                    } else if (checkedId == R.id.rb_marathi) {
//                        ImproveHelper.saveLocale(BottomNavigationActivity.this, AppConstants.LANG_MARATHI);
//                        ImproveHelper.changeLanguage(BottomNavigationActivity.this, AppConstants.LANG_MARATHI);
//                    } else if (checkedId == R.id.rb_kannada) {
//                        ImproveHelper.saveLocale(BottomNavigationActivity.this, AppConstants.LANG_KANNADA);
//                        ImproveHelper.changeLanguage(BottomNavigationActivity.this, AppConstants.LANG_KANNADA);
//                    } else if (checkedId == R.id.rb_sinhala) {
//                        ImproveHelper.saveLocale(BottomNavigationActivity.this, AppConstants.LANG_SINHALA);
//                        ImproveHelper.changeLanguage(BottomNavigationActivity.this, AppConstants.LANG_SINHALA);
//                    }
//                    sessionManager.languageChanged(true);
//                    if (currentFragment.getTag() != null && currentFragment.getTag().contentEquals("InfoFragment")) {
//                        sessionManager.activeFragment("I");
//                    } else if (currentFragment.getTag() != null && currentFragment.getTag().contentEquals("ReportsListFragment")) {
//                        sessionManager.activeFragment("R");
//                    } else if (currentFragment.getTag() != null && currentFragment.getTag().contentEquals("ReportsListFragmentRefresh")) {
//                        sessionManager.activeFragment("R");
//                    } else if (currentFragment.getTag() != null && currentFragment.getTag().contentEquals("AppsListFragment")) {
//                        sessionManager.activeFragment("A");
//                    } else if (currentFragment.getTag() != null && currentFragment.getTag().contentEquals("AppsListFragmentRefresh")) {
//                        sessionManager.activeFragment("A");
//                    }
//                    dialog.dismiss();
//
//                    startActivity(new Intent(context, LoginActivityOld.class));
//                    finish();
//
//                });
//
//
//                dialog.show();
//            }
//        });

            iv_tasksFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    customDialogAlert(context);

                }
            });


            ll_fragmentTitle.setVisibility(View.VISIBLE);
            navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_info:
                            FROM_TAB = 1;

                            isUserVisits = true;
                            ll_OrgSpinnerData.setVisibility(View.GONE);
                            iv_sync.setVisibility(View.GONE);
                            iv_profile.setVisibility(View.GONE);
                            iv_notifications.setVisibility(View.GONE);
                            iv_appListSearch.setVisibility(View.GONE);
                            ll_fragmentTitle.setVisibility(View.VISIBLE);
                            iv_appListRefresh.setVisibility(View.GONE);
                            iv_tasksFilter.setVisibility(View.GONE);
                            iv_language.setVisibility(View.GONE);
                            String tagStr = null;
    //                        if (AppConstants.OrgChange == 1 || AppConstants.PostChange == 1) {
                            if (AppConstants.OrgChange == 1 && AppConstants.PROGRESS_CHART == 1) {
                                communicationRefresh = 1;
                                tagStr = "InfoFragmentRefresh";
                            } else {
                                communicationRefresh = 0;
                                tagStr = "InfoFragment";

                            }
                            infoFragment = new InfoFragment(communicationRefresh);
                            openFragment(infoFragment, tagStr);
                            return true;

                        case R.id.navigation_appsList:
                            FROM_TAB = 0;

                            isUserVisits = true;
                            String strTag = null;
                            if (AppConstants.OrgChange == 1 && AppConstants.PROGRESS_APPS == 1) {
                                appsRefresh = 1;
                                strTag = "AppsListFragmentRefresh";
                            } else {
                                appsRefresh = 0;
                                strTag = "AppsListFragment";
                            }

    //                        appsListFragment = new AppsListFragment(appsRefresh);
                            appsListFragment = new AppsListFragment(appsRefresh);
                            openFragment(appsListFragment, strTag);

                            iv_appListRefresh.setVisibility(View.VISIBLE);
                            ll_OrgSpinnerData.setVisibility(View.GONE);
                            iv_sync.setVisibility(View.VISIBLE);
                            iv_profile.setVisibility(View.GONE);
                            iv_notifications.setVisibility(View.GONE);
                            ll_fragmentTitle.setVisibility(View.VISIBLE);
                            iv_tasksFilter.setVisibility(View.GONE);
                            iv_language.setVisibility(View.VISIBLE);
                            iv_appListSearch.setVisibility(View.VISIBLE);
                            return true;

                        case R.id.navigation_tasks:
                            FROM_TAB = 3;

                            isUserVisits = true;
                            iv_language.setVisibility(View.GONE);
                            iv_tasksFilter.setVisibility(View.VISIBLE);
                            ll_OrgSpinnerData.setVisibility(View.GONE);
                            iv_sync.setVisibility(View.GONE);
                            iv_profile.setVisibility(View.GONE);
                            iv_notifications.setVisibility(View.VISIBLE);
                            iv_appListRefresh.setVisibility(View.VISIBLE);

                            ll_fragmentTitle.setVisibility(View.VISIBLE);
                            ct_FragmentTitle.setText(context.getResources().getString(R.string.tasks));

                            openFragment(new TasksFragment("0", String.valueOf(programsRefresh)), "TasksFragment");

                            return true;

                        case R.id.navigation_videos:
                            FROM_TAB = 4;

                            iv_language.setVisibility(View.GONE);
                            if (isUserVisits) {
                                mInsertUserVisitsApi();
                            }
                            iv_sync.setVisibility(View.GONE); // added
                            ll_OrgSpinnerData.setVisibility(View.GONE);
                            iv_sync.setVisibility(View.GONE);
                            iv_profile.setVisibility(View.GONE);
                            iv_notifications.setVisibility(View.VISIBLE);
                            iv_appListRefresh.setVisibility(View.VISIBLE);
                            ll_fragmentTitle.setVisibility(View.VISIBLE);
                            iv_tasksFilter.setVisibility(View.GONE);
                            ct_FragmentTitle.setText(context.getResources().getString(R.string.e_learning));

                            videosFragment = new VideosFragment(libraryRefresh);
                            openFragment(videosFragment, "VideosFragment");
                            return true;

                        case R.id.navigation_reports:
                            FROM_TAB = 2;

                            isUserVisits = true;
                            String strTagReports = null;
    //                        if (AppConstants.OrgChange == 1 || AppConstants.PostChange == 1) {
                            if (AppConstants.OrgChange == 1 && AppConstants.PROGRESS_REPORT == 1) {
                                reportsRefresh = 1;
                                strTagReports = "ReportsListFragmentRefresh";
                            } else {
                                reportsRefresh = 0;
                                strTagReports = "ReportsListFragment";
                            }

                            reportsListFragment = new ReportsListFragment(appsRefresh);
                            openFragment(reportsListFragment, strTagReports);

                            iv_appListRefresh.setVisibility(View.VISIBLE);
                            ll_OrgSpinnerData.setVisibility(View.GONE);
                            iv_sync.setVisibility(View.GONE);
                            iv_profile.setVisibility(View.GONE);
                            iv_notifications.setVisibility(View.VISIBLE);
                            ll_fragmentTitle.setVisibility(View.VISIBLE);
                            iv_tasksFilter.setVisibility(View.GONE);
                            iv_language.setVisibility(View.VISIBLE);
                            return true;
                    }
                    return false;
                }
            };
            bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
            if (sessionManager.isLanguageChanged()) {
                if (sessionManager.getActiveFragment().contentEquals("R")) {
                    if (AppConstants.OrgChange == 1) {
                        reportsListFragment = new ReportsListFragment(1);
                        openFragment(reportsListFragment, "ReportsListFragmentRefresh");
                    } else {
                        reportsListFragment = new ReportsListFragment(0);
                        openFragment(reportsListFragment, "ReportsListFragment");
                    }
                } else if (sessionManager.getActiveFragment().contentEquals("A")) {
                    if (AppConstants.OrgChange == 1) {
                        appsListFragment = new AppsListFragment(1);
                        openFragment(appsListFragment, "AppsListFragmentRefresh");
                    } else {
                        appsListFragment = new AppsListFragment(0);
                        openFragment(appsListFragment, "AppsListFragment");
                    }
                }
            }

            iv_voice_input.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        startActivityForResult(intent, AppConstants.REQUEST_SPEECH_TO_TEXT_INPUT);
                    } else {
                        Toast.makeText(context, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "mGetTokenId", e);
        }
    }

    public  String getInternetSpeed(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null || !info.isConnected()) {
                return "Not connected to the internet";
            }
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int speedMbps = wifiInfo.getLinkSpeed();
                return speedMbps + " Mbps";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                return  getActualMobileNetworkSpeed(context);
                }


        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "mGetTokenId", e);
        } return "Unknown network type";
    }
    public String getActualMobileNetworkSpeed(Context context){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://182.18.157.124/BHARGO_WEB_DemoDbConnection_V4.0/Login.html") // Replace with a URL to test the speed
                    .build();
            long start = System.nanoTime();
            okhttp3.Response response = client.newCall(request).execute();
            long end = System.nanoTime();
            long durationNs = end - start;
            long bytesReceived = response.body().contentLength();
            double speedMbps = (bytesReceived / (durationNs / 1000.0 / 1000.0)) * 8 / 1000000.0;
            return String.format("%.2f Mbps", speedMbps);
        } catch (IOException e) {
            ImproveHelper.improveException(this, TAG, "getActualMobileNetworkSpeed", e);
        }
        return  null;
    }
    private List<LanguageTypeModel> loadLanguageData() {
        List<LanguageTypeModel> languageTypeModelsList = new ArrayList<>();
        try {
            LanguageTypeModel languageTypeModel = new LanguageTypeModel();
            languageTypeModel.setLanguage(context.getString(R.string.english_l));
            languageTypeModel.setLanguageHint(context.getString(R.string.english));
            languageTypeModel.setSelected(true);
            LanguageTypeModel languageTypeModel1 = new LanguageTypeModel();
            languageTypeModel1.setLanguage("Hindi");
            languageTypeModel1.setLanguageHint(context.getString(R.string.hindi));
            languageTypeModel1.setSelected(false);
            LanguageTypeModel languageTypeModel2 = new LanguageTypeModel();
            languageTypeModel2.setLanguage("Telugu");
            languageTypeModel2.setLanguageHint(context.getString(R.string.telugu));
            languageTypeModel2.setSelected(false);
            LanguageTypeModel languageTypeModel3 = new LanguageTypeModel();
            languageTypeModel3.setLanguage("Tamil");
            languageTypeModel3.setLanguageHint(context.getString(R.string.tamil));
            languageTypeModel3.setSelected(false);
            LanguageTypeModel languageTypeModel4 = new LanguageTypeModel();
            languageTypeModel4.setLanguage("Marathi");
            languageTypeModel4.setLanguageHint(context.getString(R.string.marathi));
            languageTypeModel4.setSelected(false);
            LanguageTypeModel languageTypeModel5 = new LanguageTypeModel();
            languageTypeModel5.setLanguage("Kannada");
            languageTypeModel5.setLanguageHint(context.getString(R.string.kannada));
            languageTypeModel5.setSelected(false);
            LanguageTypeModel languageTypeModel6 = new LanguageTypeModel();
            languageTypeModel6.setLanguage("Sinhala");
            languageTypeModel6.setLanguageHint(context.getString(R.string.sinhala));
            languageTypeModel6.setSelected(false);

            languageTypeModelsList.add(languageTypeModel);
            languageTypeModelsList.add(languageTypeModel1);
            languageTypeModelsList.add(languageTypeModel2);
            languageTypeModelsList.add(languageTypeModel3);
            languageTypeModelsList.add(languageTypeModel4);
            languageTypeModelsList.add(languageTypeModel5);
            languageTypeModelsList.add(languageTypeModel6);

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "loadLanguageData", e);
        }
        return languageTypeModelsList;
    }


    public void openFragment(Fragment fragment, String strTag) {
        try {
            if (FROM_TAB == 0) {
                if (isNetworkStatusAvialable(context)) {
                    updateStatus("online");
                }
            }
            Bundle bundle = new Bundle();
            bundle.putBoolean("fromCallAction", fromCallAction);

            FragmentManager fragMan = getSupportFragmentManager();
            FragmentTransaction fragTrans = fragMan.beginTransaction();
            fragment.setArguments(bundle);
            fragTrans.replace(R.id.container, fragment, strTag);
            fragTrans.commit();
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "openFragment", e);
        }
    }

    @Override
    public void onBackPressed() {
        try {
            Intent intentA = getIntent();
            if (intentA != null && intentA.getStringExtra("FromAction") != null && !intentA.getStringExtra("FromAction").isEmpty()) {
                finish();
            } else {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.container);
                if (currentFragment.getTag() != null && currentFragment.getTag().equalsIgnoreCase("AppsListFragment")) {
                    BottomOnBackPressAlertDialog();


                }/*else if (currentFragment.getTag() != null && currentFragment.getTag().equalsIgnoreCase("AppsListFragment")
                ||currentFragment.getTag() != null && currentFragment.getTag().equalsIgnoreCase("AppsListFragmentRefresh")) {

            onBackPressedListener.onFragmentBackPress();// on Fragment Back Press

            *//*Default infoFragment visibilities*//*
            ll_OrgSpinnerData.setVisibility(View.GONE);
            ll_menuItems.setVisibility(View.VISIBLE);
            ll_fragmentTitle.setVisibility(View.VISIBLE);

            *//*Working start*//*
            iv_appListRefresh.setVisibility(View.GONE);
//            iv_appListRefresh.setVisibility(View.VISIBLE);
            bottomNavigation.getMenu().findItem(R.id.navigation_info).setChecked(true);

        }*/ else {

                    onBackPressedListener.onFragmentBackPress();// on Fragment Back Press

                    /*Default infoFragment visibilities*/
                    ll_OrgSpinnerData.setVisibility(View.GONE);
                    ll_menuItems.setVisibility(View.GONE);

                    iv_sync.setVisibility(View.VISIBLE);
                    iv_profile.setVisibility(View.GONE);
                    iv_notifications.setVisibility(View.VISIBLE);
                    ll_fragmentTitle.setVisibility(View.VISIBLE);
                    iv_tasksFilter.setVisibility(View.GONE);
                    iv_language.setVisibility(View.VISIBLE);

                    /*Working start*/
                    iv_appListRefresh.setVisibility(View.VISIBLE);
//            iv_appListRefresh.setVisibility(View.VISIBLE);
                    bottomNavigation.getMenu().findItem(R.id.navigation_appsList).setChecked(true);
                    FROM_TAB = 0;
                    /*Working End*/
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "onBackPressed", e);
        }

    }


    private void updateStatus2(String status) {
        try {
            firebaseDatabase = FirebaseDatabase.getInstance(firebaseURL).getReference(sessionManager.getOrgIdFromSession());
            firebaseDatabase.child("Users").
                    orderByChild("Mobile").equalTo(sessionManager.
                            getUserDetailsFromSession().getPhoneNo())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Log.d(TAG, "onCancelled: " + "yes");
                                HashMap<String, Object> statusMap = new HashMap<>();

                                statusMap.put("Status", status);
                                DatabaseReference mFirebaseDatabaseReference = null;
                                mFirebaseDatabaseReference = FirebaseDatabase.getInstance(firebaseURL).getReference(sessionManager.getOrgIdFromSession());
                                mFirebaseDatabaseReference.child("Users").child(sessionManager.getUserChatID()).updateChildren(statusMap).addOnFailureListener(new OnFailureListener() {
                                    //                                mFirebaseDatabaseReference.child("Users").child("-M5frwtYvhuvyAuI6h_G").updateChildren(statusMap).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e);
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "onFailure: " + "success");
                                    }
                                });
                            } else {

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "onCancelled: " + databaseError);
                        }

                    });

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "updateStatus2", e);
        }

    }

    private void updateStatus(String status) {
        /*try {
            firebaseDatabase = FirebaseDatabase.getInstance(firebaseURL).getReference(sessionManager.getOrgIdFromSession());
            firebaseDatabase.child("Users").
                    orderByChild("Mobile").equalTo(sessionManager.getUserDetailsFromSession().getPhoneNo())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Log.d(TAG, "onCancelled: " + "yes");
                                HashMap<String, Object> statusMap = new HashMap<>();

                                statusMap.put("Status", status);
                                DatabaseReference mFirebaseDatabaseReference = null;
                                mFirebaseDatabaseReference = FirebaseDatabase.getInstance(firebaseURL).getReference(sessionManager.getOrgIdFromSession());
                                mFirebaseDatabaseReference.child("Users").child(sessionManager.getUserChatID()).updateChildren(statusMap).addOnFailureListener(new OnFailureListener() {
                                    //                                mFirebaseDatabaseReference.child("Users").child("-M5frwtYvhuvyAuI6h_G").updateChildren(statusMap).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e);
                                        ImproveHelper.improveException(BottomNavigationActivity.this, TAG, "updateStatus", e);
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "onSuccess: " + "success");
                                    }
                                });
                            } else {

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "onCancelled: " + databaseError);
                            ImproveHelper.improveException(BottomNavigationActivity.this, TAG, "updateStatus",  databaseError.toException());
                        }

                    });

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "updateStatus", e);
        }*/

    }

    public void setAppsListener(AppsListener appsListener) {
        try {
            this.appsListener = appsListener;
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "setAppsListener", e);
        }
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        try {
            this.onBackPressedListener = onBackPressedListener;
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "setOnBackPressedListener", e);
        }

    }

    public void mInsertUserVisitsApi() {
        try {
            isUserVisits = false;
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String strDate = sdfDate.format(new Date());

            InsertUserVisitsModel insertUserVisitsModel = new InsertUserVisitsModel();

            insertUserVisitsModel.setUserID(sessionManager.getUserDataFromSession().getUserID());
            insertUserVisitsModel.setDBNAME(sessionManager.getOrgIdFromSession());
            insertUserVisitsModel.setDeviceID(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
            insertUserVisitsModel.setMobileDate(strDate);
            insertUserVisitsModel.setGPS("");
            insertUserVisitsModel.setIs_Visited_Through("1");
            GetServices getServices = RetrofitUtils.getUserService();

            Call<InsertUserVisitsResponse> responseCall = getServices.getInsertUserVisits(insertUserVisitsModel);

            responseCall.enqueue(new Callback<InsertUserVisitsResponse>() {
                @Override
                public void onResponse(Call<InsertUserVisitsResponse> call, Response<InsertUserVisitsResponse> response) {
                    if (response.body() != null) {
                        Log.d(TAG, "InsertUserVisitsResponse: " + response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<InsertUserVisitsResponse> call, Throwable t) {
                    Log.d(TAG, "InsertUserVisitsException: " + t.getMessage());
                    ImproveHelper.improveException(BottomNavigationActivity.this, TAG, "mInsertUserVisitsApi", (Exception) t);
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "mInsertUserVisitsApi", e);
        }

    }

    public void filesTimeSpentOffline() {
        try {
            List<InsertUserFileVisitsModel> insertUserFileVisitsModelsDB = new ArrayList<>();
            insertUserFileVisitsModelsDB = improveDataBase.getDataFromELearningFilesTimeSpent(sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());

            if (isNetworkStatusAvialable(BottomNavigationActivity.this) && insertUserFileVisitsModelsDB != null && insertUserFileVisitsModelsDB.size() > 0) {

//            improveDataBase.getDataFromELearningFilesTimeSpent(sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());

                if (insertUserFileVisitsModelsDB != null && insertUserFileVisitsModelsDB.size() > 0) {
                    for (int i = 0; i < insertUserFileVisitsModelsDB.size(); i++) {

                        if (insertUserFileVisitsModelsDB.get(i).getFilesOffLineStatus() != null && insertUserFileVisitsModelsDB.get(i).getFilesOffLineStatus().equalsIgnoreCase("NotUploaded")) {
                            Log.d(TAG, "OffLineFilesTimeSpent: " + insertUserFileVisitsModelsDB.get(i).getFilesOffLineStatus());
//                        Gson gson = new Gson();
//                        Log.d(TAG, "onResponseOfflineFileTimeSpentList: " + gson.toJson(insertUserFileVisitsModels));

                            FilesTimeSpentModel filesTimeSpentModel = new FilesTimeSpentModel();
                            filesTimeSpentModel.setUserFileVisitsList(insertUserFileVisitsModelsDB);

                            Call<InsertUserFileVisitsResponse> responseCall = getServices.getInsertUserFileVisits(filesTimeSpentModel);
                            responseCall.enqueue(new Callback<InsertUserFileVisitsResponse>() {
                                @Override
                                public void onResponse(Call<InsertUserFileVisitsResponse> call, Response<InsertUserFileVisitsResponse> response) {
                                    if (response.body() != null) {
                                        if (response.body().getMessage() != null && !response.body().getMessage().isEmpty() && response.body().getMessage().equalsIgnoreCase("Success")) {

//                                        List<FilesInfoTimeSpentModel> spentModels = response.body().getFilesInfo();
                                            List<String> spentModels = response.body().getFilesInfo();
                                            if (spentModels != null && spentModels.size() > 0) {
                                                for (int j = 0; j < spentModels.size(); j++) {

                                                    improveDataBase.deleteELearningTimeSpentRow(spentModels.get(j));

                                                }
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<InsertUserFileVisitsResponse> call, Throwable t) {
                                    Log.d(TAG, "onUserFileVisitsException: " + t.getMessage());
                                    ImproveHelper.improveException(BottomNavigationActivity.this, TAG, "filesTimeSpentOffline", (Exception) t);
                                }
                            });
                        }
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "filesTimeSpentOffline", e);
        }

    }

    @Override
    public void onResume() {
        try {
            super.onResume();
            if (!fromCallAction) {
                if (getIntent() != null && getIntent().hasExtra("FromAction")) {
                    fromCallAction = true;
                }

                loadPosts();

                Log.d(TAG, "onResumeFiles: " + "onResumeFilesAct");
                if (isNetworkStatusAvialable(context)) {
                    filesTimeSpentOffline();
                    updateStatus("online");
    //            checkForceLogout();
                }
            } else {
                improveHelper.dismissProgressDialog();
            }
      /*  openPostAlertDialog();
        Log.d(TAG, "onResumeFiles: " + "onResumeFilesAct");
        if (isNetworkStatusAvialable(context)) {
            filesTimeSpentOffline();
            updateStatus("online");
//            checkForceLogout();
        }*/
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "onResume", e);
        }
    }

    private void checkForceLogout() {
        try {
            firebaseDatabase = FirebaseDatabase.getInstance(firebaseURL).getReference(sessionManager.getOrgIdFromSession());
            eventListener = firebaseDatabase.child("Users").
                    orderByChild("Mobile").equalTo(sessionManager.
                            getUserDetailsFromSession().getPhoneNo())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                UserDetails userDetails = snapshot.getValue(UserDetails.class);
                                if (userDetails != null && userDetails.getLogout() == 1) {
                                   /* improveDataBase.deleteDatabaseTables(sessionManager.getUserDataFromSession().getUserID());
                                    sessionManager.logoutUser();*/
                                    Toast.makeText(context, "Logout User", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "onCancelled: " + databaseError);
                        }

                    });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "checkForceLogout", e);
        }
    }

    public void customDialogAlert(Context context) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.item_task_status);

            CustomTextView ct_Assigned = dialog.findViewById(R.id.ct_Assigned);
            CustomTextView ct_InProgress = dialog.findViewById(R.id.ct_InProgress);
            CustomTextView ct_Completed = dialog.findViewById(R.id.ct_Completed);
            CustomTextView ct_AllTasks = dialog.findViewById(R.id.ct_AllTasks);
            CustomTextView ct_Cancel = dialog.findViewById(R.id.ct_Cancel);

            ct_Assigned.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tasksStatusFilter("1");
                    dialog.dismiss();

                }
            });
            ct_InProgress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tasksStatusFilter("2");
                    dialog.dismiss();

                }
            });
            ct_Completed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tasksStatusFilter("3");
                    dialog.dismiss();

                }
            });
            ct_AllTasks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tasksStatusFilter("0");
                    dialog.dismiss();

                }
            });
            ct_Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();

                }
            });

            dialog.show();
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "customDialogAlert", e);
        }

    }

    public void tasksStatusFilter(String strTaskStatus) {
        try {
            isUserVisits = true;
            iv_tasksFilter.setVisibility(View.VISIBLE);
            ll_OrgSpinnerData.setVisibility(View.GONE);
            ll_menuItems.setVisibility(View.GONE);
            iv_appListRefresh.setVisibility(View.GONE);
            ll_fragmentTitle.setVisibility(View.VISIBLE);
            ct_FragmentTitle.setText(context.getResources().getString(R.string.tasks));

            AppConstants.IS_TASK_FILTER_SELECTED = true;
            openFragment(new TasksFragment(strTaskStatus, "0"), "TasksFragment");
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "tasksStatusFilter", e);
        }

    }

    @Override
    public void onItemClick(View view, int position) {

        try {
            sessionManager.createPostsSession(postSelectionAdapter.getItem(position));
            sessionManager.setPostName(postSelectionAdapter.getItemName(position));
            sessionManager.setUserType(postSelectionAdapter.getItemType(position));
            sessionManager.setUserTypeIds(postSelectionAdapter.getItemTypeId(position));
            sessionManager.setLoginTypeId(postSelectionAdapter.getItemLoginTypeId(position));
            postAlertDialog.dismiss();

            setFragment(FROM_TAB);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "onItemClick", e);
        }
    }

    private void openPostAlertDialog() {
        try {
            context = this;
//            SessionManager sessionManager = new SessionManager(context);
            //Check Posts
//            List<UserPostDetails> userPostDetailsList = sessionManager.getUserPostDetailsFromSession();


            List<PostsMasterModel> userPostDetailsList = sessionManager.getUserPostDetails();
            if (userPostDetailsList != null && userPostDetailsList.size() == 1) {
                if (AppConstants.DefultAPK && !AppConstants.DefultAPK_afterLoginPage.equalsIgnoreCase("Bhargo Home Page")) {
                    sessionManager.createOrgSession(AppConstants.DefultAPK_OrgID);

                    sessionManager.createPostsSession(userPostDetailsList.get(0).getID());
                    sessionManager.setPostName(userPostDetailsList.get(0).getName());
                    FROM_TAB = 0;
                    setFragment(FROM_TAB);
                } else {
                    sessionManager.createPostsSession(userPostDetailsList.get(0).getID());
                    sessionManager.setPostName(userPostDetailsList.get(0).getName());
                    setFragment(FROM_TAB);
                }
            } else if (userPostDetailsList != null && userPostDetailsList.size() == 0) {
                if (AppConstants.DefultAPK && !AppConstants.DefultAPK_afterLoginPage.equalsIgnoreCase("Bhargo Home Page")) {
                    sessionManager.createOrgSession(AppConstants.DefultAPK_OrgID);
                    sessionManager.createPostsSession(userPostDetailsList.get(0).getID());
                    sessionManager.setPostName(userPostDetailsList.get(0).getName());
                    FROM_TAB = 0;
                    setFragment(FROM_TAB);
                } else {
                    setFragment(FROM_TAB);
                }
            } else {
                if (AppConstants.DefultAPK && !AppConstants.DefultAPK_afterLoginPage.equalsIgnoreCase("Bhargo Home Page")) {
                    sessionManager.createOrgSession(AppConstants.DefultAPK_OrgID);
                    sessionManager.createPostsSession(userPostDetailsList.get(0).getID());
                    sessionManager.setPostName(userPostDetailsList.get(0).getName());
                    FROM_TAB = 0;
                    setFragment(FROM_TAB);
                } else {
                    if (sessionManager.getPostsFromSession() == null || !sessionManager.getPostsFromSession().equalsIgnoreCase("")) {
//                        selectPostDialog(userPostDetailsList);
                        selectPostBottomSheet(userPostDetailsList);
                    } else {
                        setFragment(FROM_TAB);
                    }
                }
            }

//          if(!AppConstants.DefultAPK_afterLoginPage.equalsIgnoreCase("Bhargo Home Page")){
//
//                sessionManager.createOrgSession(AppConstants.DefultAPK_OrgID);
////                sessionManager.createPostsSession(postSelectionAdapter.getItem(position));
////                sessionManager.setPostName(postSelectionAdapter.getItemName(position));
////                postAlertDialog.dismiss();
//
//                setFragment(FROM_TAB);
//            }

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "openPostAlertDialog", e);
        }

    }

    //    public void selectPostDialog(List<UserPostDetails> arrayListPost) {
//        try {
//            if (!postAlertDialog.isShowing()) {
//
//                postAlertDialog.setCancelable(false);
//                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                final View view = inflater.inflate(R.layout.post_selection_alert_dialog, null);
//
//                RecyclerView recyclerView = view.findViewById(R.id.postRecyclerView);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//                        linearLayoutManager.getOrientation());
//                recyclerView.addItemDecoration(dividerItemDecoration);
//                recyclerView.setLayoutManager(linearLayoutManager);
//                postSelectionAdapter = new PostSelectionAdapter(context, arrayListPost);
//                postSelectionAdapter.setClickListener(this);
//                recyclerView.setAdapter(postSelectionAdapter);
//                postAlertDialog.setContentView(view);
//                postAlertDialog.show();
//            }
//        } catch (Exception e) {
//            ImproveHelper.improveException(this, TAG, "selectPostDialog", e);
//        }
//
//    }
    public void selectPostBottomSheet(List<PostsMasterModel> arrayListPost) {
        try {
            NestedScrollView bottom_sheetPosts = findViewById(R.id.bottom_sheet_post);
            ImageView iv_Cancel = bottom_sheetPosts.findViewById(R.id.iv_Cancel);
            iv_Cancel.setVisibility(View.GONE);
            CustomButton btnChange = bottom_sheetPosts.findViewById(R.id.btn_Change);

            sheetBehaviorPosts = BottomSheetBehavior.from(bottom_sheetPosts);

            RecyclerView bs_rvPosts = bottom_sheetPosts.findViewById(R.id.bs_rvPosts);
            sheetBehaviorPosts.setState(BottomSheetBehavior.STATE_EXPANDED);
            mViewBg.setVisibility(View.VISIBLE);
//            List<Post> postList = new ArrayList<>();
            List<PostsMasterModel> postList = new ArrayList<>();
            PostChangeAdapter postChangeAdapter = new PostChangeAdapter(context, postList,
                    sheetBehaviorPosts,sessionManager);
            bs_rvPosts.setAdapter(postChangeAdapter);
            postChangeAdapter.updateList(sessionManager.getUserPostDetails());
            postChangeAdapter.setCustomClickListener(new PostChangeAdapter.ItemClickListenerPostChange() {
                @Override
                public void onCustomClick(int value, PostsMasterModel post) {
                    PostsMasterModel strSelectedPost = postChangeAdapter.getPostID();
                    sessionManager.createPostsSession(strSelectedPost.getID());
                    sessionManager.setPostName(strSelectedPost.getName());
                    sessionManager.setUserType(strSelectedPost.getUserType());
                    sessionManager.setUserTypeIds(strSelectedPost.getID());
                    sheetBehaviorPosts.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    mViewBg.setVisibility(View.GONE);
                    setFragment(FROM_TAB);
                }
            });
            btnChange.setOnClickListener(v -> {
                PostsMasterModel strSelectedPost = postChangeAdapter.getPostID();
                sessionManager.createPostsSession(strSelectedPost.getID());
                sessionManager.setPostName(strSelectedPost.getName());
                sessionManager.setUserType(strSelectedPost.getUserType());
                sessionManager.setUserTypeIds(strSelectedPost.getID());
                sheetBehaviorPosts.setState(BottomSheetBehavior.STATE_COLLAPSED);
                mViewBg.setVisibility(View.GONE);
                setFragment(FROM_TAB);
            });

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "selectPostBottomSheet", e);
        }

    }

    public void loadPosts() {
        try {
            if(sessionManager.getPostsFromSessionPostName() == null || sessionManager.getPostsFromSessionPostName().equalsIgnoreCase("")) {
                List<PostsMasterModel> postsMasterModelList = new ArrayList<>();
                postsMasterModelList = sessionManager.getUserPostDetails();
                List<UserTypesMasterModel> userTypesMasterlist = sessionManager.getUserTypesFromSession();
                for (int i = 0; i < userTypesMasterlist.size(); i++) {
                    if (!userTypesMasterlist.get(i).getID().equalsIgnoreCase("1")) {
                        PostsMasterModel postsMasterModel = new PostsMasterModel();
                        postsMasterModel.setID(userTypesMasterlist.get(i).getID());
                        postsMasterModel.setName(userTypesMasterlist.get(i).getName());
                        postsMasterModel.setUserID(userTypesMasterlist.get(i).getUserID());
                        postsMasterModel.setLoginTypeID(userTypesMasterlist.get(i).getID());
                        postsMasterModel.setUserType("NormalUser");
                        postsMasterModelList.add(postsMasterModel);

                    }
                }
                sessionManager.createPostMasterSession(postsMasterModelList);

                openPostAlertDialog();
            }else{
                setFragment(FROM_TAB);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "loadPosts", e);
        }

    }

    public void setFragment(int FROM_TAB) {
        try {
            Intent intent = getIntent();
            bottomNavigation.setVisibility(View.VISIBLE);
            /*======Checking_====*/
            TabsData = AppConstants.WINDOWS_AVAILABLE.split("\\|");
            if (appLanguage.contentEquals("hi")) {
                TabsData = AppConstants.WINDOWS_AVAILABLE_HINDI.split("\\|");
            }
            if (!TabsData[0].split("\\^")[1].equalsIgnoreCase("Yes")) {
                bottomNavigation.getMenu().removeItem(R.id.navigation_appsList);
            } else {
                bottomNavigation.getMenu().findItem(R.id.navigation_appsList).setTitle(TabsData[0].split("\\^")[2]);
            }

            if (!TabsData[1].split("\\^")[1].equalsIgnoreCase("Yes")) {

                bottomNavigation.getMenu().removeItem(R.id.navigation_info);
            } else {
                bottomNavigation.getMenu().findItem(R.id.navigation_info).setTitle(TabsData[1].split("\\^")[2]);

            }

            if (!TabsData[2].split("\\^")[1].equalsIgnoreCase("Yes")) {
                bottomNavigation.getMenu().removeItem(R.id.navigation_reports);
            } else {
                bottomNavigation.getMenu().findItem(R.id.navigation_reports).setTitle(TabsData[2].split("\\^")[2]);
            }

            if (!TabsData[3].split("\\^")[1].equalsIgnoreCase("Yes")) {
                bottomNavigation.getMenu().removeItem(R.id.navigation_tasks);
            } else {
                bottomNavigation.getMenu().findItem(R.id.navigation_tasks).setTitle(TabsData[3].split("\\^")[2]);
            }

            if (!TabsData[4].split("\\^")[1].equalsIgnoreCase("Yes")) {
                bottomNavigation.getMenu().removeItem(R.id.navigation_videos);
            } else {
                bottomNavigation.getMenu().findItem(R.id.navigation_videos).setTitle(TabsData[4].split("\\^")[2]);
            }

            /*DefultAPK*/
            if (!AppConstants.DefultAPK_afterLoginPage.equalsIgnoreCase("Bhargo Home Page") &&
                    AppConstants.DefultAPK_afterLoginPage_loaded) {
                AppConstants.DefultAPK_afterLoginPage_loaded = false;
                Intent intenta = new Intent(getApplicationContext(), MainActivity.class);
                intenta.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intenta.putExtra("app_edit", "New");
                intenta.putExtra(Notification_PageName, AppConstants.DefultAPK_afterLoginPage);
                intenta.putExtra(FromNotification, FromNotification);
                intenta.putExtra(FromFlashScreen, FromFlashScreen);

                startActivity(intenta);
                finish();
            }

            /*Working Start*/
            if (intent != null && intent.getStringExtra("NotifRefreshAppsList") != null && !intent.getStringExtra("NotifRefreshAppsList").isEmpty()) {
                strAppsRefreshNotification = intent.getStringExtra("NotifRefreshAppsList");

                iv_appListRefresh.setVisibility(View.VISIBLE);
                iv_language.setVisibility(View.VISIBLE);
                if (strAppsRefreshNotification.equals("1")) {
                    appsRefresh = 1;
                    bottomNavigation.setSelectedItemId(R.id.navigation_appsList);
                } else if (strAppsRefreshNotification.equals("0")) {
                    bottomNavigation.setSelectedItemId(R.id.navigation_appsList);
                    appsRefresh = 0;
                } else if (strAppsRefreshNotification.equals("2")) {
                    reportsRefresh = 0;
                    bottomNavigation.setSelectedItemId(R.id.navigation_reports);
                } else if (strAppsRefreshNotification.equals("3")) {
                    bottomNavigation.setSelectedItemId(R.id.navigation_reports);
                    reportsRefresh = 1;
                }
            } else if (intent != null && intent.getStringExtra("ELearning_NotificationBack") != null
                    && !intent.getStringExtra("ELearning_NotificationBack").isEmpty()) {
                if ("ELearning_NormalBack".equalsIgnoreCase(intent.getStringExtra("ELearning_NotificationBack"))) {
                    libraryRefresh = 0;
                } else {
                    libraryRefresh = 1;
                }
                bottomNavigation.setSelectedItemId(R.id.navigation_videos);
            } else if (intent != null && intent.getStringExtra("ELearningListDeleteTopic") != null && !intent.getStringExtra("ELearningListDeleteTopic").isEmpty()) {
                libraryRefresh = 1;
                bottomNavigation.setSelectedItemId(R.id.navigation_videos);
            } else if (sessionManager.isLanguageChanged() && sessionManager.getActiveFragment().contentEquals("P")) {

            } else if (sessionManager.isLanguageChanged() && sessionManager.getActiveFragment().contentEquals("A")) {
                sessionManager.languageChanged(false);
                iv_language.setVisibility(View.VISIBLE);
                iv_appListRefresh.setVisibility(View.VISIBLE);
                appsRefresh = 0;

                bottomNavigation.setSelectedItemId(R.id.navigation_appsList);
            } else if (intent != null && intent.getStringExtra("FromTaskDeployment") != null && !intent.getStringExtra("FromTaskDeployment").isEmpty()) {
                AppConstants.IN_TASK_REFRESH_BOOLEAN = true;
                AppConstants.TASK_REFRESH = "OutTaskRefresh";
                bottomNavigation.setSelectedItemId(R.id.navigation_tasks);

            } else if (intent != null && intent.getStringExtra("FromNormalTask") != null && !intent.getStringExtra("FromNormalTask").isEmpty()) {
                AppConstants.IN_TASK_REFRESH_BOOLEAN = true;
                AppConstants.TASK_REFRESH = "InTaskRefresh";
                bottomNavigation.setSelectedItemId(R.id.navigation_tasks);

            } else if (intent != null && intent.getStringExtra("FromChatScreen") != null && !intent.getStringExtra("FromChatScreen").isEmpty()) {
                bottomNavigation.setSelectedItemId(R.id.navigation_info);

            } else if (intent != null && intent.getStringExtra("FromAction") != null && !intent.getStringExtra("FromAction").isEmpty()) {
                FromAction = intent.getStringExtra("FromAction");
                if (FromAction.equalsIgnoreCase("1")) {
                    iv_language.setVisibility(View.VISIBLE);
                    bottomNavigation.setSelectedItemId(R.id.navigation_info);
                    bottomNavigation.setVisibility(View.GONE);
                } else if (FromAction.equalsIgnoreCase("2")) {
                    bottomNavigation.setSelectedItemId(R.id.navigation_appsList);
                    bottomNavigation.setVisibility(View.GONE);
                } else if (FromAction.equalsIgnoreCase("3")) {
                    bottomNavigation.setSelectedItemId(R.id.navigation_reports);
                    bottomNavigation.setVisibility(View.GONE);
                } else if (FromAction.equalsIgnoreCase("4")) {
                    bottomNavigation.getMenu().add(0, R.id.navigation_tasks, 4, "Tasks");
                    bottomNavigation.setSelectedItemId(R.id.navigation_tasks);
                    bottomNavigation.setVisibility(View.GONE);
                } else if (FromAction.equalsIgnoreCase("5")) {
                    bottomNavigation.setSelectedItemId(R.id.navigation_videos);
                    bottomNavigation.setVisibility(View.GONE);
                }else if (FromAction.equalsIgnoreCase("6")) {
                    iv_language.setVisibility(View.VISIBLE);
                    bottomNavigation.setSelectedItemId(R.id.navigation_appsList);
                    bottomNavigation.setVisibility(View.VISIBLE);
                }else if (FromAction.equalsIgnoreCase("7")) {
                    bottomNavigation.setSelectedItemId(R.id.navigation_info);
                }

            } else if (FROM_TAB == 0) {
                bottomNavigation.setSelectedItemId(R.id.navigation_appsList);
            } else if (FROM_TAB == 2) {
                bottomNavigation.setSelectedItemId(R.id.navigation_reports);
            } else if (FROM_TAB == 3) {
                bottomNavigation.setSelectedItemId(R.id.navigation_tasks);
            } else if (FROM_TAB == 4) {
                bottomNavigation.setSelectedItemId(R.id.navigation_videos);
            } else {
                FROM_TAB = 0;
                bottomNavigation.setSelectedItemId(R.id.navigation_appsList);
//                communicationRefresh = 0;
//                iv_language.setVisibility(View.VISIBLE);
//                bottomNavigation.setSelectedItemId(R.id.navigation_info);

            }


        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "setFragment", e);
        }

    }

    public void uploadDeploymentData() {
        try {
            if (ImproveHelper.isNetworkStatusAvialable(context)) {
                Log.d(TAG, "JSONCheckingUploaded1");
                List<String> strJsonDataList = improveDataBase.getDataFromDeployment(sessionManager.getOrgIdFromSession(),
                        sessionManager.getUserDataFromSession().getUserID(),
                        sessionManager.getPostsFromSession());
                if (strJsonDataList != null && strJsonDataList.size() > 0) {
                    Log.d(TAG, "JSONCheckingUploaded2");
                    JSONObject jsonObjectFinalDB = new JSONObject();
                    JSONArray TaskDetailsDB = new JSONArray();
                    JSONObject jsonObjectMainDB = new JSONObject();
                    for (int i = 0; i < strJsonDataList.size(); i++) {
                        try {
                            jsonObjectMainDB = new JSONObject(strJsonDataList.get(i));
                            Log.d(TAG, "JSONCheckingUploadedF" + i);
                        } catch (Exception e) {
                            Log.d(TAG, "JsonObjectListException: " + e);
                        }

                    }

                    try {
                        TaskDetailsDB.put(jsonObjectMainDB);
                        jsonObjectFinalDB.put("TaskDetails", TaskDetailsDB);
                        JsonParser jsonParserDB = new JsonParser();
                        JsonObject jsonCreateTaskDB = (JsonObject) jsonParserDB.parse(String.valueOf(jsonObjectFinalDB));
                        Log.d(TAG, "JSONCheckingUploaded3");

                        deploymentApi(jsonCreateTaskDB);

                    } catch (JSONException e) {
                        Log.d(TAG, "JsonObjectListExceptionUpload: " + e);
                    }

                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "uploadDeploymentData", e);
        }

    }

    private void deploymentApi(JsonObject jsonCreateTaskDB) {
        try {
            improveHelper.showProgressDialog("Please wait...");
            Call<CreateTaskResponse> createTaskResponseCall = getServices.createTask(jsonCreateTaskDB);

            createTaskResponseCall.enqueue(new Callback<CreateTaskResponse>() {
                @Override
                public void onResponse(Call<CreateTaskResponse> call, Response<CreateTaskResponse> response) {

                    if (response.body() != null) {

                        CreateTaskResponse createTaskResponse = response.body();

                        if (createTaskResponse.getStatus() != null && createTaskResponse.getStatus().equalsIgnoreCase("200")) {
                            Log.d(TAG, "JSONCheckingUploaded4");
                            improveHelper.dismissProgressDialog();
                            improveDataBase.deleteAllRowsInTable(DEPLOYMENT_TABLE);
                            AppConstants.OUT_TASK_REFRESH_BOOLEAN = true;

                        } else {
                            Log.d(TAG, "JSONCheckingUploaded4");
                            improveHelper.dismissProgressDialog();
                            ImproveHelper.showToast(context, createTaskResponse.getMessage());
                        }
                    }
                    improveHelper.dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<CreateTaskResponse> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "onFailureCreateTask: " + t);
                    ImproveHelper.improveException(BottomNavigationActivity.this, TAG, "deploymentApi", (Exception) t);
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "deploymentApi", e);
        }

    }

    private void sendCommentOffLineApi() {
        try {
            List<TaskCommentDetails> taskCommentDetailsList = new ArrayList<>();
            taskCommentDetailsList.clear();
            taskCommentDetailsList = improveDataBase.getDataFromSendCommentsOffLine(sessionManager.getOrgIdFromSession(),
                    sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession());
            InsertComments insertComments = new InsertComments();
            insertComments.setTaskCommentDetails(taskCommentDetailsList);
            improveHelper.showProgressDialog("Please wait...");
            Call<CommentsResponse> responseCall = getServices.insertCommentsDetails(insertComments);
            responseCall.enqueue(new Callback<CommentsResponse>() {
                @Override
                public void onResponse(Call<CommentsResponse> call, Response<CommentsResponse> response) {

                    if (response.body() != null) {

                        CommentsResponse commentsResponse = response.body();
                        if (commentsResponse != null && commentsResponse.getStatus().equalsIgnoreCase("200")) {

                            if (commentsResponse.getMessage().equalsIgnoreCase("Success")) {
                                improveHelper.dismissProgressDialog();
                                if (improveDataBase.doesTableExist(SEND_COMMENTS_OFFLINE_TABLE)
                                        && !improveDataBase.isTableEmpty(SEND_COMMENTS_OFFLINE_TABLE)) {
                                    improveDataBase.deleteAllRowsInTable(SEND_COMMENTS_OFFLINE_TABLE);
                                }
                            }
                        } else if (commentsResponse != null && commentsResponse.getStatus().equalsIgnoreCase("300")) {
                            improveHelper.dismissProgressDialog();
                            ImproveHelper.showToastAlert(context, commentsResponse.getMessage());

                        }

                    }
                    improveHelper.dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<CommentsResponse> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "onFailureTaskAppDetails: " + t);
                    ImproveHelper.improveException(BottomNavigationActivity.this, TAG, "sendCommentOffLineApi", (Exception) t);
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "sendCommentOffLineApi", e);
        }

    }

    private void outTaskSendCommentOffLineApi() {
        try {
            List<TaskCommentDetails> taskCommentDetailsList = new ArrayList<>();
            taskCommentDetailsList.clear();
            taskCommentDetailsList = improveDataBase.getDataFromOutTaskSendCommentsOffLine(sessionManager.getOrgIdFromSession(),
                    sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession());
            InsertComments insertComments = new InsertComments();
            insertComments.setTaskCommentDetails(taskCommentDetailsList);
            improveHelper.showProgressDialog("Please wait...");
            Call<CommentsResponse> responseCall = getServices.insertCommentsDetails(insertComments);
            responseCall.enqueue(new Callback<CommentsResponse>() {
                @Override
                public void onResponse(Call<CommentsResponse> call, Response<CommentsResponse> response) {

                    if (response.body() != null) {

                        CommentsResponse commentsResponse = response.body();
                        if (commentsResponse != null && commentsResponse.getStatus().equalsIgnoreCase("200")) {

                            if (commentsResponse.getMessage().equalsIgnoreCase("Success")) {

                                if (improveDataBase.doesTableExist(OUT_TASK_SEND_COMMENTS_OFFLINE_TABLE)
                                        && !improveDataBase.isTableEmpty(OUT_TASK_SEND_COMMENTS_OFFLINE_TABLE)) {
                                    improveDataBase.deleteAllRowsInTable(OUT_TASK_SEND_COMMENTS_OFFLINE_TABLE);
                                }
                            }

                        } else if (commentsResponse != null && commentsResponse.getStatus().equalsIgnoreCase("300")) {
                            ImproveHelper.showToastAlert(context, commentsResponse.getMessage());

                        }
                    }
                    improveHelper.dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<CommentsResponse> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "onFailureTaskAppDetails: " + t);
                    ImproveHelper.improveException(BottomNavigationActivity.this, TAG, "outTaskSendCommentOffLineApi", (Exception) t);
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "outTaskSendCommentOffLineApi", e);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        /*if (isNetworkStatusAvialable(context)) {
            updateStatus("offline");
        }*/

    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
            if (isNetworkStatusAvialable(context)) {
                updateStatus("offline");
         /*       if(sessionManager.isLoggedIn()){
                firebaseDatabase.child("Users").
                        orderByChild("Mobile").equalTo(sessionManager.
                        getUserDetailsFromSession().getPhoneNo()).removeEventListener(eventListener);}*/
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "onDestroy", e);
        }
    }

    @Override
    public void onItemClickL(View view, int position) {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        try {
            if (item.getItemId() == android.R.id.home) {
                onBackPressed();
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "onOptionsItemSelected", e);
        }
        return true;
    }

    public void BottomOnBackPressAlertDialog() {
        try {
            ImproveHelper.alertDialogWithRoundShapeMaterialTheme(context, getString(R.string.are_you_sure),
                    getString(R.string.yes), getString(R.string.d_no), new ImproveHelper.IL() {
                        @Override
                        public void onSuccess() {
                            FROM_TAB = 0;
                            if (isNetworkStatusAvialable(context)) {

                                updateStatus("offline");
                            }

                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancel() {

                        }
                    });

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "BottomOnBackPressAlertDialog", e);
        }
    }

    private void requestPermission() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                    startActivityForResult(intent, 2296);
                } catch (Exception e) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivityForResult(intent, 2296);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "requestPermission", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 2296) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        // perform action when allow permission success
                    } else {
                        Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }else if(requestCode==AppConstants.REQUEST_SPEECH_TO_TEXT_INPUT){
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> resultText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (resultText != null && resultText.size() > 0) {
                        Log.d(TAG, "REQUEST_SPEECH_TO_TEXT: " + resultText);
                        if (resultText.get(0) != null) {
//                            voiceInputService(resultText.get(0));
                            String tag = "AppsListFragment";
                            if (AppConstants.OrgChange == 1 && AppConstants.PROGRESS_APPS == 1) {
                                appsRefresh = 1;
                                tag = "AppsListFragmentRefresh";
                            } else {
                                appsRefresh = 0;
                                tag = "AppsListFragment";
                            }
                            AppsListFragment appsFragment = (AppsListFragment) getSupportFragmentManager().findFragmentByTag(tag);
                            Log.d(TAG, "REQUEST_SPEECH_TO_TEXT: " + resultText.get(0));
                            if(appsFragment!=null) {
                                appsFragment.voiceInputService(resultText.get(0));
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "onActivityResult", e);
        }
    }

    public interface OnBackPressedListener {
        void onFragmentBackPress();
    }


    public void statusChangeListener(){
        try {
            firebaseDatabase = FirebaseDatabase.getInstance(firebaseURL).getReference(sessionManager.getOrgIdFromSession());
            eventListener = firebaseDatabase.child("Users").child(sessionManager.getUserDataFromSession().getUserID())
    //        eventListener = firebaseDatabase.child("Users").orderByChild("Mobile").equalTo(sessionManager.
    //                        getUserDetailsFromSession().getPhoneNo())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                FirebaseSyncStatus firebaseSyncStatus = dataSnapshot.getValue(FirebaseSyncStatus.class);
                                if (firebaseSyncStatus != null && firebaseSyncStatus.isLogout()) {
    //                                logout();
                                    Toast.makeText(context, "Logout User", Toast.LENGTH_SHORT).show();
                                }
                        }



                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "onCancelled: " + databaseError);
                        }

                    });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "statusChangeListener", e);
        }


    }

    private void logout() {
        try {
            AppConstants.DefultAPK_afterLoginPage_loaded = true;
            PrefManger.clearSharedPreferences(context);
            improveDataBase.deleteDatabaseTables(sessionManager.getUserDataFromSession().getUserID());
            sessionManager.logoutUser();
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "logout", e);
        }

    }

}
