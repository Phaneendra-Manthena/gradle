package com.bhargo.user.screens;

import static com.bhargo.user.utils.AppConstants.SP_MOBILE_NO;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;
import static com.bhargo.user.utils.ImproveHelper.showToast;
import static com.bhargo.user.utils.ImproveHelper.showToastAlert;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bhargo.user.R;
import com.bhargo.user.auth.FacebookSign;
import com.bhargo.user.auth.GoogleSign;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.LoginModel;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.AppSignatureHashHelper;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.OTPUtils;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private static final int MULTI_PERMISSIONS_REQUEST_CODE = 553;
    public static String strLoggedInMobileNo;
    Context context;
    LinearLayout ll_login;
    CustomButton cb_login_submit;
    CustomEditText ce_mobile_no;
    CustomTextView tv_appVersion;
    GetServices getServices;
    SessionManager sessionManager;
    GoogleSign googleSign; // Google sign-in
    FacebookSign facebookSign; // Facebook sign-in
    private Animation animSlideUp;
    private ImageView iv_login_icon;
    private ImproveHelper improveHelper;
    private LinearLayout linearLayout;
    private String strPageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setBhargoTheme(this,AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
        /*if (AppConstants.THEME.equalsIgnoreCase("THEME2")) {
            setTheme(R.style.AppTheme2);
        }else if (AppConstants.THEME.equalsIgnoreCase("THEME1")) {
            setTheme(R.style.AppTheme);
        }*/
            setContentView(R.layout.activity_login_default);
            context = this;
            socialLogins();

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

            }else{

                getServices = RetrofitUtils.getUserService();

                ce_mobile_no = findViewById(R.id.ce_mobile_no);
                tv_appVersion = findViewById(R.id.tv_appVersion);
                iv_login_icon = findViewById(R.id.iv_login_icon);

                cb_login_submit = findViewById(R.id.cb_login_submit);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getPermissions();
                }

                AppSignatureHashHelper appSignatureHashHelper = new AppSignatureHashHelper(this);

                // This code requires one time to get Hash keys do comment and share key
                Log.i("HashKey: ", appSignatureHashHelper.getAppSignatures().get(0));


                cb_login_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cb_login_submit.setEnabled(false);
                        try {
                            showProgressDialog("Progressing...");
                            if (!ce_mobile_no.getText().toString().equals("") && !ce_mobile_no.getText().toString().isEmpty() && ce_mobile_no.getText().toString().length() >= 10) {

                                if (isNetworkStatusAvialable(context)) {
    //                            LoginData loginData = new LoginData();
    //                            loginData.setMobileNo(ce_mobile_no.getText().toString());
                                    Map<String, String> loginData =  new HashMap<>();
                                    loginData.put("MobileNo",ce_mobile_no.getText().toString());
                                    Call<LoginModel> call = getServices.iLogin(loginData);
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
                                                    PrefManger.putSharedPreferencesString(LoginActivity.this, SP_MOBILE_NO, ce_mobile_no.getText().toString());
                                                    showToastAlert(LoginActivity.this, loginModel.getMessage());
                                                    startActivity(new Intent(LoginActivity.this, OTPActivity.class));
                                                    finish();
                                                } else {
                                                    showToast(LoginActivity.this, loginModel.getMessage());
                                                    Log.d(TAG, "onResponse: " + loginModel.getMessage());
                                                }
                                            }else {
                                                showToast(LoginActivity.this, "Server error, response is null..");
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<LoginModel> call, Throwable t) {
                                            dismissProgressDialog();
                                            cb_login_submit.setEnabled(true);
                                            System.out.println("LoginOnResponseFail" + t.toString());
                                            Toast.makeText(LoginActivity.this, "" + t, Toast.LENGTH_SHORT).show();
                                            improveHelper.improveException(context, TAG, "cb_login_submit - button", (Exception) t);

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
                setAppVersion();
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "onCreate", e);
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
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "onRequestPermissionsResult", e);
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
                tv_appVersion.setText(version);
                AppConstants.APP_VERSION = version;
            }
            //appversion
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "setAppVersion", e);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            googleSign.resultGoogleLogin(requestCode, resultCode, data); // result
            facebookSign.resultFaceLogin(requestCode, resultCode, data); // result
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "onActivityResult", e);
        }

    }

    public void onClick_FaceBook(View view) {
        try {
            facebookSign.signIn();
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "onClick_FaceBook", e);
        }
    }

    public void onClick_Gmail(View view) {
        try {
            googleSign.signIn();
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "onClick_Gmail", e);
        }
    }

    private void socialLogins() {
        try {
            googleSign = new GoogleSign(this, new GoogleSign.InfoLoginGoogleCallback() {
                @Override
                public void getInfoLoginGoogle(GoogleSignInAccount account) {
                    Log.w(TAG, "Name " + account.getDisplayName());
                    Log.v(TAG, "Email " + account.getEmail());
                    Log.v(TAG, "Photo " + account.getPhotoUrl());
                    try {
                        JSONObject serviceResponse = new JSONObject();
                        serviceResponse.put("Status", "200");
                        JSONObject serviceData = new JSONObject();
                        serviceData.put("id", account.getId());
                        serviceData.put("email", account.getEmail());
                        serviceData.put("verified_email", "true");
                        serviceData.put("name", account.getDisplayName());
                        serviceData.put("given_name", account.getGivenName());
                        serviceData.put("family_name", account.getFamilyName());
                        serviceData.put("picture", account.getPhotoUrl());
                        serviceData.put("locale", "");
                        serviceResponse.put("ServiceData", serviceData);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("SourceType", "External");
                        jsonObject.put("Source", "google");

                        jsonObject.put("ServiceResponse", serviceResponse.toString());
                        jsonObject.put("ImageName", account.getPhotoUrl());
                        jsonObject.put("Name", account.getDisplayName());
                        jsonObject.put("Email", account.getEmail());
                        jsonObject.put("SourceUniqueID", account.getId());
                        JSONArray userTypes = new JSONArray();
                        JSONObject userTypeID = new JSONObject();
                        userTypeID.put("UserTypeID", "2");
                        userTypes.put(userTypeID);
                        jsonObject.put("UserTypes", userTypes);
                        jsonObject.put("Roles", new JSONArray());
                        serverHitForSocialLogin(jsonObject);
                    } catch (Exception e) {
                        showToastAlert(context, e.getMessage());
                    }


                    //showToastAlert(context, account.getDisplayName() + "\n" + account.getEmail());
                    //startActivity(new Intent(LoginActivity.this, GoogleSignOut.class));
                }

                @Override
                public void connectionFailedApiClient(ConnectionResult connectionResult) {
                    Log.e(TAG, "Connection Failed API " + connectionResult.getErrorMessage());
                }

                @Override
                public void loginFailed(String failedMsg) {
                    showToastAlert(context, "Google Sign In failed\n" + failedMsg);
                }
            });
            facebookSign = new FacebookSign(this, new FacebookSign.InfoLoginFaceCallback() {
                @Override
                public void getInfoFace(String id, String nome, String email, String foto, LoginResult loginResults) {
                    showToastAlert(context, nome + "\n" + email);
                }

                @Override
                public void cancelLoginFace() {
                    showToastAlert(context, "Facebook Login Cancel");
                }

                @Override
                public void erroLoginFace(FacebookException e) {
                    showToastAlert(context, "Facebook login failed!\n" + e.getMessage());
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "mGetTokenId", e);
        }
    }

    public void serverHitForSocialLogin(JSONObject sendingObj) {
        try {
            Gson gson = new Gson();
            Log.d(TAG, "sendDataTest: " + gson.toJson(sendingObj));
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(sendingObj.toString());
            improveHelper.showProgressDialog("Please Wait...");
            Call<String> call = getServices.socialLogin(jo);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> serverResponse, Response<String> response) {

                    try {
                        if (response.body() != null) {
                            JSONObject jsonObject =  new JSONObject(response.body());
                            String resultStatus = jsonObject.getString("Status");
                            String message = jsonObject.getString("Message");
                            String bearer = jsonObject.getString("Bearer");
                            if (resultStatus.equalsIgnoreCase("200")) {
                                JSONObject loginDetailsObj = jsonObject.getJSONObject("CurrentLoginDetails");
                                String UserID = loginDetailsObj.getString("UserID");
                                String PhoneNo = loginDetailsObj.getString("PhoneNo");
                                String Password = loginDetailsObj.getString("Password");

                                OTPUtils otpUtils = new OTPUtils(context, sessionManager, null, "SocialLogin", new ImproveDataBase(context), -1, -1, null);
                                otpUtils.socialLogin(bearer,improveHelper);

                            } else {
                                improveHelper.dismissProgressDialog();
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                logoutFromGoogle();
                            }
                        } else {
                            improveHelper.dismissProgressDialog();
                            Toast.makeText(context, "server data is null", Toast.LENGTH_SHORT).show();
                            logoutFromGoogle();
                        }
                    } catch (Exception e) {
                        improveHelper.dismissProgressDialog();
                        e.printStackTrace();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        logoutFromGoogle();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    ImproveHelper.improveException(LoginActivity.this, TAG, "serverHitForSocialLogin", (Exception) t);
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    //logout form social login if failed
                    logoutFromGoogle();
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "serverHitForSocialLogin", e);
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            //logout form social login if failed
            logoutFromGoogle();
        }
    }

    private void logoutFromGoogle() {
        try {
            //Way 1
            googleSign.mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "Sign Out", Toast.LENGTH_LONG).show();
                }
            });
            //Way 2
                /*Auth.GoogleSignInApi.signOut(googleSign.mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                if (status.isSuccess()){
                                 Toast.makeText(getApplicationContext(),"Sign Out", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(GoogleSignOut.this, LoginActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Session not close", Toast.LENGTH_LONG).show();
                                }
                            }
                        });*/
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "logoutFromGoogle", e);
        }
    }
}