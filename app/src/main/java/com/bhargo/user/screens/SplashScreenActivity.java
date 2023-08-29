package com.bhargo.user.screens;

import static com.bhargo.user.utils.AppConstants.FromFlashScreen;
import static com.bhargo.user.utils.AppConstants.FromNotification;
import static com.bhargo.user.utils.AppConstants.FromNotificationEV;
import static com.bhargo.user.utils.AppConstants.Notification_PageName;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.DeviceIdSendData;
import com.bhargo.user.pojos.UserData;
import com.bhargo.user.realm.RealmDBHelper;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity";
    private static final int MULTI_PERMISSIONS_REQUEST_CODE = 553;
    Context context;
    CustomTextView tv_appversion;
    ImproveHelper improveHelper;
    SessionManager sessionManager;
    private String strPageName;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private static final String VERSION_CODE_KEY = "version_code";
    private AlertDialog updateDailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
        setBhargoTheme(this,AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
        /*if (AppConstants.THEME.equalsIgnoreCase("THEME2")) {
            setTheme(R.style.AppTheme2);
        }else if (AppConstants.THEME.equalsIgnoreCase("THEME1")) {
            setTheme(R.style.AppTheme);
        }*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        improveHelper = new ImproveHelper(this);
        RealmDBHelper.initializeRealm(this);


            FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        context = this;
        PrefManger.putSharedPreferencesBoolean(SplashScreenActivity.this, AppConstants.OneTimeForOfflineDataExistKey, true);

        tv_appversion = findViewById(R.id.tv_appversion);

        setAppVersion();
        tv_appversion.setText(getString(R.string.version_4_0));
        if (!AppConstants.DefultAPK) {
//            ivSplashBhargoTitle.setVisibility(View.GONE);
//            callAfterAnimation();
        }


        /*Fade*/
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        tv_appversion.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

//                if (getIntent().getExtras() != null) {
//                    for (String key : getIntent().getExtras().keySet()){
//                        String value = getIntent().getExtras().getString(key);
//                        Log.d(TAG, "Key: " + key + " Value: " + value);
//                    }}

                /*Intent intent = new Intent(SplashScreenActivity.this, LoginActivityOld.class);
                startActivity(intent);
*/
                if (isNetworkStatusAvialable(context)) {
//                    Intent intent = new Intent(SplashScreenActivity.this, PaymentGatewayOne.class);
//                    startActivity(intent);
//                    initRemoteConfig();
                    //uncomment
                    callAfterAnimation();
//                    startActivi1ty(new Intent(SplashScreenActivity.this, TestDVActivity.class));
                } else {
//                    Intent intent = new Intent(context, PaymentGatewayOne.class);
//                    startActivity(intent);
                    callAfterAnimation();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "onCreate", e);
        }
    }

    public void callAfterAnimation() {
        try {
            ImproveHelper.saveLocale(SplashScreenActivity.this, AppConstants.LANG_ENGLISH);
            ImproveHelper.changeLanguage(SplashScreenActivity.this, AppConstants.LANG_ENGLISH);
            if (AppConstants.DefultAPK_OrgID.equalsIgnoreCase("SELE20210719175221829")) {
    //        if (AppConstants.DefultAPK_OrgID.equalsIgnoreCase("NDLM20220804142648572")) {
                if (AppConstants.DefultAPK) {
    //                startActivity(new Intent(context, LoginActivityOld.class));
                    startActivity(new Intent(context, LoginActivity.class));
                    finish();
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getPermissions();
                } else {
                    sessionManager = new SessionManager(context);
                    if (sessionManager.isLoggedIn()) {
                        mGetTokenIdOnly();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("app_edit", "New");
                        intent.putExtra(Notification_PageName, AppConstants.DefultAPK_Startpage);
                        intent.putExtra(FromNotification, FromNotification);
                        intent.putExtra(FromFlashScreen, FromFlashScreen);

                        startActivity(intent);
                        finish();
                    } else {
    //                    UserDetails userDetails = new UserDetails();
    //                    userDetails.setUserId(AppConstants.DefultAPK_UserID);
                        UserData userDetails = new UserData();
                        userDetails.setUserID(AppConstants.DefultAPK_UserID);
                        PrefManger.putSharedPreferencesString(context, AppConstants.SP_ORGANISATION_ID, AppConstants.DefultAPK_OrgID);
                        sessionManager.createOrgSession(AppConstants.DefultAPK_OrgID);
                        sessionManager.createLoginSession(userDetails);

                        improveHelper.createImproveUserFolder("Improve_User");
                        mGetTokenId();
                    }
                }
            /*} else if (ImproveHelper.getLocale(SplashScreenActivity.this).contentEquals("")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenActivity.this);
                LayoutInflater inflater = SplashScreenActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_language, null);

                view.setClipToOutline(true);

                RadioGroup rg_language = view.findViewById(R.id.rg_language);
                RadioButton rb_eng = view.findViewById(R.id.rb_eng);
                RadioButton rb_tel = view.findViewById(R.id.rb_tel);
                RadioButton rb_hin = view.findViewById(R.id.rb_hin);

                Button cancel = view.findViewById(R.id.btn_cancel);
                cancel.setVisibility(View.GONE);

                builder.setCancelable(false);
                builder.setView(view);

                AlertDialog dialog = builder.show();

                cancel.setOnClickListener(v -> {
                    dialog.dismiss();
                });

                rg_language.setOnCheckedChangeListener((group, checkedId) -> {

                    if (checkedId == R.id.rb_eng) {
                        ImproveHelper.saveLocale(SplashScreenActivity.this, AppConstants.LANG_ENGLISH);
                        ImproveHelper.changeLanguage(SplashScreenActivity.this, AppConstants.LANG_ENGLISH);
                    } else if (checkedId == R.id.rb_tel) {
                        ImproveHelper.saveLocale(SplashScreenActivity.this, AppConstants.LANG_TELUGU);
                        ImproveHelper.changeLanguage(SplashScreenActivity.this, AppConstants.LANG_TELUGU);
                    } else if (checkedId == R.id.rb_hin) {
                        ImproveHelper.saveLocale(SplashScreenActivity.this, AppConstants.LANG_HINDI);
                        ImproveHelper.changeLanguage(SplashScreenActivity.this, AppConstants.LANG_HINDI);
                    } else if (checkedId == R.id.rb_tamil) {
                        ImproveHelper.saveLocale(SplashScreenActivity.this, AppConstants.LANG_TAMIL);
                        ImproveHelper.changeLanguage(SplashScreenActivity.this, AppConstants.LANG_TAMIL);
                    } else if (checkedId == R.id.rb_marathi) {
                        ImproveHelper.saveLocale(SplashScreenActivity.this, AppConstants.LANG_MARATHI);
                        ImproveHelper.changeLanguage(SplashScreenActivity.this, AppConstants.LANG_MARATHI);
                    } else if (checkedId == R.id.rb_kannada) {
                        ImproveHelper.saveLocale(SplashScreenActivity.this, AppConstants.LANG_KANNADA);
                        ImproveHelper.changeLanguage(SplashScreenActivity.this, AppConstants.LANG_KANNADA);
                    } else if (checkedId == R.id.rb_sinhala) {
                        ImproveHelper.saveLocale(SplashScreenActivity.this, AppConstants.LANG_SINHALA);
                        ImproveHelper.changeLanguage(SplashScreenActivity.this, AppConstants.LANG_SINHALA);
                    }
                    dialog.dismiss();


                    if (AppConstants.DefultAPK) {
    //                    startActivity(new Intent(context, LoginActivityOld.class));
                        startActivity(new Intent(context, LoginActivity.class));
                        finish();
                    } else {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getPermissions();
                        } else {
                            sessionManager = new SessionManager(context);
                            if (sessionManager.isLoggedIn()) {
                                mGetTokenIdOnly();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("app_edit", "New");
                                intent.putExtra(Notification_PageName, AppConstants.DefultAPK_Startpage);
                                intent.putExtra(FromNotification, FromNotification);
                                intent.putExtra(FromFlashScreen, FromFlashScreen);

                                startActivity(intent);
                                finish();
                            } else {
    //                            UserDetails userDetails = new UserDetails();
    //                            userDetails.setUserId(AppConstants.DefultAPK_UserID);
                                UserData userDetails = new UserData();
                                userDetails.setUserID(AppConstants.DefultAPK_UserID);
                                PrefManger.putSharedPreferencesString(context, AppConstants.SP_ORGANISATION_ID, AppConstants.DefultAPK_OrgID);
                                sessionManager.createOrgSession(AppConstants.DefultAPK_OrgID);
                                sessionManager.createLoginSession(userDetails);

                                improveHelper.createImproveUserFolder("Improve_User");
                                mGetTokenId();
                            }
                        }
                    }
                });


                dialog.show();*/


            } else {
                System.out.println("AppConstants.DefultAPK==" + AppConstants.DefultAPK);
                if (AppConstants.DefultAPK) {
                    ImproveHelper.changeLanguage(SplashScreenActivity.this, ImproveHelper.getLocale(SplashScreenActivity.this));
    //                startActivity(new Intent(context, LoginActivityOld.class));
                    startActivity(new Intent(context, LoginActivity.class));
                    finish();
                } else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getPermissions();
                    } else {
                        sessionManager = new SessionManager(context);
                        System.out.println("sessionManager.isLoggedIn()===" + sessionManager.isLoggedIn());
                        if (sessionManager.isLoggedIn()) {
                            mGetTokenIdOnly();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("app_edit", "New");
                            intent.putExtra(Notification_PageName, AppConstants.DefultAPK_Startpage);
                            intent.putExtra(FromNotification, FromNotification);
                            intent.putExtra(FromFlashScreen, FromFlashScreen);

                            startActivity(intent);
                            finish();
                        } else {
                            System.out.println();
    //                        UserDetails userDetails = new UserDetails();
    //                        userDetails.setUserId(AppConstants.DefultAPK_UserID);
                            UserData userDetails = new UserData();
                            userDetails.setUserID(AppConstants.DefultAPK_UserID);
                            sessionManager.createOrgSession(AppConstants.DefultAPK_OrgID);
                            sessionManager.createLoginSession(userDetails);

                            improveHelper.createImproveUserFolder("Improve_User");
                            mGetTokenId();
                        }
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "mGetTokenId", e);
        }
    }

    private void setAppVersion() {
        try {
            String result = "";

            try {
                result = context.getPackageManager()
                        .getPackageInfo(context.getPackageName(), 0)
                        .versionName;
                result = result.replaceAll("[a-zA-Z]|-", "");
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("Version Exception", e.getMessage());
            }

            if (result != null || !result.isEmpty()) {
                String version = "Version" + '\t' + result;
                tv_appversion.setText(version);
                AppConstants.APP_VERSION = version;
            }
            //appversion
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "setAppVersion", e);
        }

    }

    public void mGetTokenId() {
        try {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }  // Get new FCM registration token

                            String newToken = task.getResult();
                            Log.d(TAG, "GetTokenId: " + newToken);

                            DeviceIdSendData deviceIdSendData = new DeviceIdSendData();
                            deviceIdSendData.setUserID(AppConstants.DefultAPK_UserID);
                            deviceIdSendData.setOrgId(AppConstants.DefultAPK_OrgID);
                            deviceIdSendData.setDeviceID(newToken);
                            sessionManager.createDeviceIdSession(deviceIdSendData.getDeviceID());

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("app_edit", "New");
                            intent.putExtra(Notification_PageName, AppConstants.DefultAPK_Startpage);
                            intent.putExtra(FromNotification, FromNotification);
                            intent.putExtra(FromFlashScreen, FromFlashScreen);

                            startActivity(intent);
                            finish();
                        }
                    });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "mGetTokenId", e);
        }

    }

    public void mGetTokenIdOnly() {
        try {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }  // Get new FCM registration token

                            String newToken = task.getResult();
                            Log.d(TAG, "GetTokenId: " + newToken);

                            DeviceIdSendData deviceIdSendData = new DeviceIdSendData();
                            deviceIdSendData.setUserID(AppConstants.DefultAPK_UserID);
                            deviceIdSendData.setOrgId(AppConstants.DefultAPK_OrgID);
                            deviceIdSendData.setDeviceID(newToken);
                            sessionManager.createDeviceIdSession(deviceIdSendData.getDeviceID());

                        }
                    });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "mGetTokenIdOnly", e);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getPermissions() {
        try {
            // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
            // checking the build version since Context.checkSelfPermission(...) is only available
            // in Marshmallow
            // 2) Always check for permission (even if permission has already been granted)
            // since the user can revoke permissions at any time through Settings
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED

                    || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                // The permission is NOT already granted.
                // Check if the user has been asked about this permission already and denied
                // it. If so, we want to give more explanation about why the permission is needed.
                // Fire off an async request to actually get the permission
                // This will show the standard permission request dialog UI
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO,  Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        MULTI_PERMISSIONS_REQUEST_CODE);



            } else {
                sessionManager = new SessionManager(context);
                if (sessionManager.isLoggedIn()) {
                    mGetTokenIdOnly();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("app_edit", "New");
                    intent.putExtra(Notification_PageName, AppConstants.DefultAPK_Startpage);
                    intent.putExtra(FromNotification, FromNotification);
                    intent.putExtra(FromFlashScreen, FromFlashScreen);

                    startActivity(intent);
                    finish();
                } else {
    //                UserDetails userDetails = new UserDetails();
    //                userDetails.setUserId(AppConstants.DefultAPK_UserID);

                    UserData userDetails = new UserData();
                    userDetails.setUserID(AppConstants.DefultAPK_UserID);
                    sessionManager.createOrgSession(AppConstants.DefultAPK_OrgID);
                    sessionManager.createLoginSession(userDetails);

                    improveHelper.createImproveUserFolder("Improve_User");
                    mGetTokenId();

                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "getPermissions", e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        try {
            // Make sure it's our original READ_CONTACTS request
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == MULTI_PERMISSIONS_REQUEST_CODE) {
                Log.d(TAG, "onRequestPermissionsResult: "+"test");
                if (grantResults.length == 6 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED
                        && grantResults[4] == PackageManager.PERMISSION_GRANTED
                        && grantResults[5] == PackageManager.PERMISSION_GRANTED
                        ) {
                    sessionManager = new SessionManager(context);

                    if (sessionManager.isLoggedIn()) {
                        mGetTokenIdOnly();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("app_edit", "New");
                        intent.putExtra(Notification_PageName, AppConstants.DefultAPK_Startpage);
                        intent.putExtra(FromNotificationEV, FromNotificationEV);
                        intent.putExtra(FromFlashScreen, FromFlashScreen);

                        startActivity(intent);
                        finish();
                    } else {
    //                    UserDetails userDetails = new UserDetails();
    //                    userDetails.setUserId(AppConstants.DefultAPK_UserID);
                        UserData userDetails = new UserData();
                        userDetails.setUserID(AppConstants.DefultAPK_UserID);
                        sessionManager.createOrgSession(AppConstants.DefultAPK_OrgID);
                        sessionManager.createLoginSession(userDetails);

                        improveHelper.createImproveUserFolder("Improve_User");
                        mGetTokenId();
                    }

                    //Toast.makeText(this, "Record Audio permission granted", Toast.LENGTH_SHORT).show();

                } else {
    //                Toast.makeText(this, "You must give permissions to use this app. App is exiting.", Toast.LENGTH_SHORT).show();
    //                finishAffinity();
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "onRequestPermissionsResult", e);
        }

    }

    private void initRemoteConfig() {
        try {
            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

            //Setting the Default Map Value with the current app version code
            //default values are used for safety if on backend version_code is not set in remote config
            HashMap<String, Object> firebaseDefaultMap = new HashMap<>();
            firebaseDefaultMap.put(VERSION_CODE_KEY, getCurrentVersionCode());
            mFirebaseRemoteConfig.setDefaultsAsync(firebaseDefaultMap);

            //setMinimumFetchIntervalInSeconds to 0 during development to fast retrieve the values
            //in production set it to 12 which means checks for firebase remote config values for every 12 hours
            mFirebaseRemoteConfig.setConfigSettingsAsync(
                    new FirebaseRemoteConfigSettings.Builder()
                            .setMinimumFetchIntervalInSeconds(TimeUnit.HOURS.toSeconds(0))
                            .build());

            //Fetching remote firebase version_code value here
            mFirebaseRemoteConfig.fetch().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        //activate most recently fetch config value
                        mFirebaseRemoteConfig.activate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
                            @Override
                            public void onComplete(@NonNull Task<Boolean> task) {
                                if (task.isSuccessful()) {
                                    //calling function to check if new version is available or not
                                    final int latestAppVersion = (int) mFirebaseRemoteConfig.getDouble(VERSION_CODE_KEY);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            checkForUpdate(latestAppVersion);
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "initRemoteConfig", e);
        }
    }

    private void checkForUpdate(int latestAppVersion) {
        try {
            if (latestAppVersion > getCurrentVersionCode()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Update");
                builder.setMessage("New Version Available.\n" + "Please Update App");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToPlayStore();
                        updateDailog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateDailog.dismiss();
                    }
                });
                // create and show the alert dialog
                updateDailog = builder.create();
                updateDailog.show();
            } else {
                callAfterAnimation();
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "checkForUpdate", e);
        }
    }

    private int getCurrentVersionCode() {
        int versionCode = 1;
        try {

            try {
                final PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    versionCode = (int) pInfo.getLongVersionCode();
                } else {
                    versionCode = pInfo.versionCode;
                }
            } catch (PackageManager.NameNotFoundException e) {
                //log exception
            }

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "getCurrentVersionCode", e);
        }
        return versionCode;
    }

    private void goToPlayStore() {
        try {
            Toast.makeText(context, "New Version Available. Please Download", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "goToPlayStore", e);
        }
    }
}
