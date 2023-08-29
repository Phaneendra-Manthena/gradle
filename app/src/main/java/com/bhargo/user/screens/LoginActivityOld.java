package com.bhargo.user.screens;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.LoginData;
import com.bhargo.user.pojos.LoginModel;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.AppSignatureHashHelper;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.utils.AppConstants.SP_MOBILE_NO;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.showToast;
import static com.bhargo.user.utils.ImproveHelper.showToastAlert;

public class LoginActivityOld extends BaseActivity {
    private static final String TAG = "LoginActivityOld";
    private static final int MULTI_PERMISSIONS_REQUEST_CODE = 553;
    public static String strLoggedInMobileNo;
    Context context;
    LinearLayout ll_login;
    CustomButton cb_login_submit;
    CustomEditText ce_mobile_no;
    CustomTextView tv_appVersion;
    GetServices getServices;
    SessionManager sessionManager;
    private Animation animSlideUp;
    private ImageView iv_login_icon;
    private ImproveHelper improveHelper;
    private RelativeLayout linearLayout;
    private String strPageName;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        setContentView(R.layout.activity_login_check);

        context = this;
        improveHelper = new ImproveHelper(context);
        linearLayout = findViewById(R.id.rl_LoginMain);
        sessionManager = new SessionManager(context);

        if (sessionManager.isLoggedIn()) {
            try {
//            Intent intent = new Intent(context, AppsListActivity.class);
                System.out.println("DefultAPK_afterLoginPage_loaded flag===="+ AppConstants.DefultAPK_afterLoginPage_loaded);
                if(!AppConstants.DefultAPK_afterLoginPage.equalsIgnoreCase("Bhargo Home Page")) {
                    AppConstants.DefultAPK_afterLoginPage_loaded = true;
                }
                Intent intent = new Intent(context, BottomNavigationActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                improveHelper.improveException(this, TAG, "onCreate - isLoggedIn", e);
            }

        }

//        getServices = RetrofitClientInstance.getRetrofitInstance().create(GetServices.class);
        getServices = RetrofitUtils.getUserService();

        ce_mobile_no = findViewById(R.id.ce_mobile_no);
        iv_login_icon = findViewById(R.id.iv_login_icon);
        ll_login = findViewById(R.id.ll_login);
        cb_login_submit = findViewById(R.id.cb_login_submit);
        tv_appVersion = findViewById(R.id.tv_appVersion);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermissions();
        }

/*
        String ts = Context.TELEPHONY_SERVICE;
        TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(ts);
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        String imsi = mTelephonyMgr.getSubscriberId();
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        String imei = mTelephonyMgr.getDeviceId();
        Log.d("strChiguruboniaIMEI", imei);


        String strChigurubonia = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("strChigurubonia", "onCreate: strChigurubonia "+ strChigurubonia);
*/


        AppSignatureHashHelper appSignatureHashHelper = new AppSignatureHashHelper(this);

        // This code requires one time to get Hash keys do comment and share key
        Log.i("HashKey: ", appSignatureHashHelper.getAppSignatures().get(0));

        animSlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
//        iv_login_icon.setAnimation(animSlideUp);
        ll_login.setAnimation(animSlideUp);
        animSlideUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                iv_login_icon.setAnimation(animation);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        cb_login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_login_submit.setEnabled(false);
                try {
                    showProgressDialog("Progressing...");
                    if (!ce_mobile_no.getText().toString().equals("") || !ce_mobile_no.getText().toString().isEmpty() || ce_mobile_no.getText().toString().length() >= 10) {

                        if (isNetworkStatusAvialable(context)) {


                            LoginData loginData = new LoginData();
                            loginData.setMobileNo(ce_mobile_no.getText().toString());
//                            Call<LoginModel> call = getServices.iLogin(loginData);
                            Call<LoginModel> call = getServices.iLogin(null);

                            call.enqueue(new Callback<LoginModel>() {
                                @Override
                                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                                    dismissProgressDialog();
                                    cb_login_submit.setEnabled(true);
                                    if (response.body() != null) {

                                        LoginModel loginModel = response.body();
//                            Log.d("LoginonResponse", loginModel.getStrMobileNo());
                                        if (loginModel.getMessage().equals("Success")) {
                                            System.out.println("LoginOnResponseSuccess: " + loginModel.getMessage());
                                            strLoggedInMobileNo = ce_mobile_no.getText().toString();
                                            PrefManger.putSharedPreferencesString(LoginActivityOld.this, SP_MOBILE_NO, ce_mobile_no.getText().toString());
                                            showToastAlert(LoginActivityOld.this, loginModel.getMessage());
                                            startActivity(new Intent(LoginActivityOld.this, OTPActivity.class));
                                            finish();
                                        } else {
                                            showToast(LoginActivityOld.this, loginModel.getMessage());
                                            Log.d(TAG, "onResponse: " + loginModel.getMessage());
                                        }
                                    }else {
                                        showToast(LoginActivityOld.this, "Server error, response is null..");
                                    }
                                }

                                @Override
                                public void onFailure(Call<LoginModel> call, Throwable t) {
                                    dismissProgressDialog();
                                    cb_login_submit.setEnabled(true);
                                    System.out.println("LoginOnResponseFail" + t.toString());
                                    Toast.makeText(LoginActivityOld.this, "" + t, Toast.LENGTH_SHORT).show();

                                }
                            });

                        } else {
                            dismissProgressDialog();
                            cb_login_submit.setEnabled(true);
                            improveHelper.snackBarAlertActivities(context, linearLayout);
                        }
                    } else {
                        dismissProgressDialog();
                        cb_login_submit.setEnabled(true);
                        showToast(context, getString(R.string.enter_valid_mobile));
                    }
                } catch (Exception e) {
                    dismissProgressDialog();
                    cb_login_submit.setEnabled(true);
                    improveHelper.improveException(context, TAG, "cb_login_submit - button", e);
                }

            }

        });

        tv_appVersion.setText(AppConstants.APP_VERSION);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getPermissions() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MULTI_PERMISSIONS_REQUEST_CODE);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MULTI_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length == 6 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED
                    && grantResults[3] == PackageManager.PERMISSION_GRANTED
                    && grantResults[4] == PackageManager.PERMISSION_GRANTED
                    && grantResults[5] == PackageManager.PERMISSION_GRANTED

            ) {

                //Toast.makeText(this, "Record Audio permission granted", Toast.LENGTH_SHORT).show();

            } else {
//                Toast.makeText(this, "You must give permissions to use this app. App is exiting.", Toast.LENGTH_SHORT).show();
//                finishAffinity();
            }
        }

    }

}
