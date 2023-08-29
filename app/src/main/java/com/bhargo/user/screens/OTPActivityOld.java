package com.bhargo.user.screens;

import static com.bhargo.user.screens.LoginActivityOld.strLoggedInMobileNo;
import static com.bhargo.user.utils.AppConstants.SP_MOBILE_NO;
import static com.bhargo.user.utils.AppConstants.SP_REPORTING_USER_DETAILS;
import static com.bhargo.user.utils.AppConstants.SP_USER_DETAILS;
import static com.bhargo.user.utils.AppConstants.SP_USER_POST_DETAILS;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.showToast;
import static com.bhargo.user.utils.ImproveHelper.showToastAlert;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.LoginData;
import com.bhargo.user.pojos.LoginModel;
import com.bhargo.user.pojos.OTPData;
import com.bhargo.user.pojos.OTPModel;
import com.bhargo.user.pojos.OrgList;
import com.bhargo.user.pojos.UserDetails;
import com.bhargo.user.pojos.UserDetailsData;
import com.bhargo.user.pojos.UserDetailsModel;
import com.bhargo.user.pojos.UserPostDetails;
import com.bhargo.user.pojos.firebase.Group;
import com.bhargo.user.pojos.firebase.GroupsListResponse;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SMSReceiver;
import com.bhargo.user.utils.SessionManager;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivityOld extends BaseActivity {

    private static final String TAG = "OTPActivityOld";
    private static final int SMS_CONSENT_REQUEST = 2;
    private final BroadcastReceiver smsVerificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
                Bundle extras = intent.getExtras();
                Status smsRetrieverStatus = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

                switch (smsRetrieverStatus.getStatusCode()) {
                    case CommonStatusCodes.SUCCESS:
                        // Get consent intent
                        Intent consentIntent = extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
                        try {
                            // Start activity to show consent dialog to user, activity must be started in
                            // 5 minutes, otherwise you'll receive another TIMEOUT intent
                            startActivityForResult(consentIntent, SMS_CONSENT_REQUEST);
                        } catch (ActivityNotFoundException e) {
                            // Handle the exception ...
                        }
                        break;
                    case CommonStatusCodes.TIMEOUT:
                        // Time out occurred, handle the error.
                        break;
                }
            }
        }
    };
    Context context;
    GetServices getServices;
    SmsRetrieverClient client;
    SessionManager sessionManager;
    ImproveHelper improveHelper;
    RelativeLayout rl_OTPMain;
    ImageView iv_otpBack;
    CustomTextView ctv_resend_OTP;
    LinearLayout ll_otp;
    ImproveDataBase improveDataBase;
    DatabaseReference firebaseDatabase;
    String firebaseURL = "https://improvecommunication-c08c9.firebaseio.com/";
    private CustomEditText CustomEditText1, CustomEditText2, CustomEditText3, CustomEditText4, CustomEditText5, CustomEditText6;
    private CustomEditText[] CustomEditTexts;
    private CustomButton cb_otp_submit;
    private SMSReceiver smsReceiver;
    private OTPData otpData;
    private Animation animSlideUp;
    /*OTP Verification API*/
    private List<OrgList> organisationList;
    private int orgCount;
    /*New*/
    private boolean isRSS;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

//        initializeActionBar();
//        toolBarViews();
        context = this;
        sessionManager = new SessionManager(context);
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);
        rl_OTPMain = findViewById(R.id.rl_OTPMain);
        ctv_resend_OTP = findViewById(R.id.ctv_resend_OTP);
        ll_otp = findViewById(R.id.ll_otp);
        iv_otpBack = findViewById(R.id.iv_otpBack);


        animSlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
//        iv_login_icon.setAnimation(animSlideUp);
        ll_otp.setAnimation(animSlideUp);


//        startSMSListener();

        getServices = RetrofitUtils.getUserService();

        CustomEditText1 = findViewById(R.id.ce_otp1);
        CustomEditText2 = findViewById(R.id.ce_otp2);
        CustomEditText3 = findViewById(R.id.ce_otp3);
        CustomEditText4 = findViewById(R.id.ce_otp4);
        CustomEditText5 = findViewById(R.id.ce_otp5);
        CustomEditText6 = findViewById(R.id.ce_otp6);
        CustomEditTexts = new CustomEditText[]{CustomEditText1, CustomEditText2, CustomEditText3, CustomEditText4, CustomEditText5, CustomEditText6};

        CustomEditText1.addTextChangedListener(new PinTextWatcher(0));
        CustomEditText2.addTextChangedListener(new PinTextWatcher(1));
        CustomEditText3.addTextChangedListener(new PinTextWatcher(2));
        CustomEditText4.addTextChangedListener(new PinTextWatcher(3));
        CustomEditText5.addTextChangedListener(new PinTextWatcher(4));
        CustomEditText6.addTextChangedListener(new PinTextWatcher(5));

        CustomEditText1.setOnKeyListener(new PinOnKeyListener(0));
        CustomEditText2.setOnKeyListener(new PinOnKeyListener(1));
        CustomEditText3.setOnKeyListener(new PinOnKeyListener(2));
        CustomEditText4.setOnKeyListener(new PinOnKeyListener(3));
        CustomEditText5.setOnKeyListener(new PinOnKeyListener(4));
        CustomEditText6.setOnKeyListener(new PinOnKeyListener(5));

        otpData = new OTPData();
        otpData.setMobileNo(strLoggedInMobileNo);

        cb_otp_submit = findViewById(R.id.cb_otp_submit);
        cb_otp_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTotalOTP = CustomEditText1.getText().toString() +
                        CustomEditText2.getText().toString() +
                        CustomEditText3.getText().toString() +
                        CustomEditText4.getText().toString() +
                        CustomEditText5.getText().toString() +
                        CustomEditText6.getText().toString();

                otpData.setOTP(strTotalOTP);
                Log.d(TAG, "OTPonClick: " + strTotalOTP.length());
                if (strTotalOTP.length() == 6 && otpData.getMobileNo().length() >= 10) {
                    Log.d("OTPonClick: ", strTotalOTP);

                    /*OTP API*/
                    if (isNetworkStatusAvialable(context)) {
                        mOtpAPI(otpData);
                    } else {
                        improveHelper.snackBarAlertActivities(context, v);
//                        Toast.makeText(context, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    ImproveHelper.showToast(OTPActivityOld.this, getString(R.string.pls_enter_valid_otp));
                }
//

            }
        });

        ctv_resend_OTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*OTP API*/
                if (isNetworkStatusAvialable(context)) {
                    resendOTP_API();
                } else {
                    improveHelper.snackBarAlertActivities(context, v);
//                        Toast.makeText(context, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });

        iv_otpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OTPActivityOld.this, LoginActivityOld.class);
                startActivity(intent);
                finish();
            }
        });
//        IntentFilter intentFilter = new IntentFilter();
//            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
//            this.registerReceiver(smsReceiver, intentFilter);
//
        Task<Void> task = SmsRetriever.getClient(context).startSmsUserConsent(null /* or null */);
        task.addOnCompleteListener(OTPActivityOld.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "onCompleteCheck: " + "Done");
                }
            }
        });
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        this.registerReceiver(smsVerificationReceiver, intentFilter);
    }

    private void mOtpAPI(OTPData otpData) {
        try {
            showProgressDialog("Verifying OTP");

//            Call<OTPModel> otpModelCall = getServices.iOTPVerification(otpData);
            Call<OTPModel> otpModelCall = getServices.iOTPVerification(null);

            otpModelCall.enqueue(new Callback<OTPModel>() {
                @Override
                public void onResponse(Call<OTPModel> call, Response<OTPModel> response) {

                    if (response.body() != null) {
                        organisationList = new ArrayList<>();
                        OTPModel otpModel = response.body();
                        System.out.println("OTPOnResponseSuccess: " + otpModel.getMessage());

                        if (otpModel.getMessage().equalsIgnoreCase("Success")) {
                            improveDataBase.deleteOrganisationData();
                            if (otpModel.getOrgList() != null && otpModel.getOrgList().size() > 0) {

                                for (OrgList orgList : otpModel.getOrgList()) {
//                                    if (orgList.getStatus().equalsIgnoreCase("Y")) {
                                    organisationList.add(orgList);
//                                    }
                                }
//                             organisationList= otpModel.getOrgList();
                                if (!AppConstants.DefultAPK_afterLoginPage.equalsIgnoreCase("Bhargo Home Page")) {
                                    for (int i = 0; i < organisationList.size(); i++) {
                                        if (organisationList.get(i).getOrgID().equalsIgnoreCase(AppConstants.DefultAPK_OrgID)) {
                                            orgCount = i;
                                            saveDefaultOrg(organisationList.get(i));
                                            break;
                                        }
                                    }
                                } else {
                                    saveDefaultOrg(organisationList.get(orgCount));
                                }
//                            sessionManager.createOrgSession(otpModel.getOrgList().get(0).getOrgID());
                            }
                            mUserDetails(organisationList);

                        } else {
                            dismissProgressDialog();
                            ImproveHelper.showToast(OTPActivityOld.this, otpModel.getMessage());
                        }
                    } else {
                        dismissProgressDialog();
                    }

                }

                @Override
                public void onFailure(Call<OTPModel> call, Throwable t) {
                    System.out.println("OTPOnResponseFailure: " + t);
                    dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "mOtpAPI", e);
            dismissProgressDialog();
        }

    }

    private void saveDefaultOrg(OrgList orgList) {
        try {
            PrefManger.putSharedPreferencesString(context, AppConstants.SP_ORGANISATION_ID, AppConstants.DefultAPK_OrgID);
//            sessionManager.createOrgSession(orgList.getOrgID());
            sessionManager.createOrgSession("BLUE20210529101300593");
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "saveDefaultOrg", e);
        }

    }

    private void mOtpAPI1(OTPData otpData) {
        showProgressDialog("Verifying OTP");
//        Call<OTPModel> otpModelCall = getServices.iOTPVerification(otpData);
        Call<OTPModel> otpModelCall = getServices.iOTPVerification(null);
        otpModelCall.enqueue(new Callback<OTPModel>() {
            @Override
            public void onResponse(Call<OTPModel> call, Response<OTPModel> response) {

                if (response.body() != null) {
                    OTPModel otpModel = response.body();
                    System.out.println("OTPOnResponseSuccess: " + otpModel.getMessage());
                    if (otpModel.getMessage().equalsIgnoreCase("Success")) {
                        if (otpModel.getOrgList() != null && otpModel.getOrgList().size() > 0) {
                            for (int i = 0; i < otpModel.getOrgList().size(); i++) {
                                if (!(otpModel.getOrgList().get(i).getOrgID().contentEquals("BFMT20200720123821110") || otpModel.getOrgList().get(i).getOrgID().contentEquals("SRHU20200803191807746"))) {
                                    otpModel.getOrgList().remove(i);
                                    i--;
                                }
                            }
                        }
                        if (otpModel.getOrgList() != null && otpModel.getOrgList().size() > 0) {
                            improveDataBase.deleteOrganisationData();
                            for (int i = 0; i < otpModel.getOrgList().size(); i++) {
                                if ((otpModel.getOrgList().get(i).getOrgID().contentEquals("SRHU20200803191807746"))) {
                                    sessionManager.createOrgSession(otpModel.getOrgList().get(0).getOrgID());
                                    isRSS = true;
                                    break;
                                }
                            }
                            if (!isRSS) {
                                sessionManager.createOrgSession(otpModel.getOrgList().get(0).getOrgID());
                            }
                            mUserDetails(otpModel.getOrgList());
                        }
                        dismissProgressDialog();
                    } else {
                        dismissProgressDialog();
                        ImproveHelper.showToast(OTPActivityOld.this, otpModel.getMessage());
                    }
                } else {
                    dismissProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<OTPModel> call, Throwable t) {
                System.out.println("OTPOnResponseFailure: " + t);
                dismissProgressDialog();
            }
        });
    }

    /*User Details API*/
    private void mUserDetails(final List<OrgList> orgList) {
        try {
            UserDetailsData userDetailsData = new UserDetailsData();
            userDetailsData.setMobileNo(PrefManger.getSharedPreferencesString(this, SP_MOBILE_NO, ""));
//        userDetailsData.setMobileNo(Default_MobileNo);
            userDetailsData.setOrgId(sessionManager.getOrgIdFromSession());

//            final Call<UserDetailsModel> userDetailsCall = getServices.iUserDetails(userDetailsData);
            final Call<UserDetailsModel> userDetailsCall = getServices.iUserDetails("");

            userDetailsCall.enqueue(new Callback<UserDetailsModel>() {
                @Override
                public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {

                    if (response.body() != null) {

                        UserDetailsModel userDetailsModel = response.body();
                        if (userDetailsModel.getStatus().equalsIgnoreCase("200")) {
                            UserDetails userDetails = userDetailsModel.getUserDeatils();
                            List<UserPostDetails> userPostDetails = userDetailsModel.getUserPostDetails();
                            UserDetailsModel.ReportingUserDeatils reportingUserDetails = userDetailsModel.getReportingUserDeatils();
//                    String strUserId = userDetails.getUserId();
//                    Log.d("OTP_UserId", strUserId);
                            /*Setting User Details Object into SharedPreferences*/
                            Gson gson = new Gson();
                            if (userDetails != null) {
                                String jsonUserDeatils = gson.toJson(userDetails);
                                PrefManger.putSharedPreferencesString(context, SP_USER_DETAILS, jsonUserDeatils);
                            }
                            if (userPostDetails != null) {
                                String jsonUserPostDeatils = gson.toJson(userPostDetails);
                                PrefManger.putSharedPreferencesString(context, SP_USER_POST_DETAILS, jsonUserPostDeatils);
                            }
                            if (reportingUserDetails != null) {
                                String jsonReportingUserDetails = gson.toJson(reportingUserDetails);
                                PrefManger.putSharedPreferencesString(context, SP_REPORTING_USER_DETAILS, jsonReportingUserDetails);
                            }

                            if (userDetails != null) {
//                                createLoginSession(userDetails, userPostDetails, orgList);
                            }else{
                                dismissProgressDialog();
                                ImproveHelper.showToast(context, "No user details found");

                            }
                        } else {

//                        ImproveHelper.showToast(context, response.body().getMessage());
                            orgCount++;
                            if (orgCount < organisationList.size()) {
                                saveDefaultOrg(organisationList.get(orgCount));
                                mUserDetails(organisationList);

                            } else {
                                dismissProgressDialog();
                                ImproveHelper.showToast(context, "No user details found");
                            }
                        }

                    } else {
                        dismissProgressDialog();
                        ImproveHelper.showToast(context, "No user details found");
                    }

                }

                @Override
                public void onFailure(Call<UserDetailsModel> call, Throwable t) {
                    dismissProgressDialog();
                    ImproveHelper.showToast(context, t.toString());
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "mUserDetails", e);
            dismissProgressDialog();
        }
    }

/*
    private void createLoginSession(UserDetails userDetails, List<UserPostDetails> userPostDetailsList, List<OrgList> orgList) {
        try {
            improveHelper.createImproveUserFolder("Improve_User");
            sessionManager.createLoginSession(userDetails);
            sessionManager.createUserPostDetailsSession(userPostDetailsList);
            improveDataBase.insertIntOrganisationsTable(orgList, sessionManager.getUserDetailsFromSession().getPhoneNo());

            */
/*Get Token Id*//*


            getGroupsList();
        } catch (Exception e) {
            dismissProgressDialog();
            ImproveHelper.improveException(this, TAG, "createLoginSession", e);
        }

    }
*/

//    private void startSMSListener() {
//        try {
//            smsReceiver = new SMSReceiver();
//            smsReceiver.setOTPListener(this);
//
//            IntentFilter intentFilter = new IntentFilter();
//            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
//            this.registerReceiver(smsReceiver, intentFilter);
//
//            SmsRetrieverClient client = SmsRetriever.getClient(this);
//
//            Task<Void> task = client.startSmsRetriever();
//            task.addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    // API successfully started
//                }
//            });
//
//            task.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    // Fail to start API
//                }
//            });
//        } catch (Exception e) {
//            improveHelper.improveException(this, TAG, "startSMSListener", e);
//        }
//
//    }

//    @Override
//    public void onOTPReceived(String otp) {
//
//        showToastAlert(context, "OTP Received: " + otp);
//        String strOtp = parseCode(otp);
//        setOTPChars(strOtp);
//        if (smsReceiver != null) {
//            unregisterReceiver(smsReceiver);
//            smsReceiver = null;
//        }
//    }

    private void getGroupsList() {
        try {
            Map<String, String> data = new HashMap<>();
            String userId = ImproveHelper.getUserDetails(context).getUserID();
            data.put("UserID", ImproveHelper.getUserDetails(context).getUserID());
            data.put("OrgId", sessionManager.getOrgIdFromSession());

//            Call<GroupsListResponse> getAllAppNamesDataCall = getServices.iGetAllGroupsList(data);
            Call<GroupsListResponse> getAllAppNamesDataCall = getServices.iGetAllGroupsList("",data);
            getAllAppNamesDataCall.enqueue(new Callback<GroupsListResponse>() {
                @Override
                public void onResponse(Call<GroupsListResponse> call, Response<GroupsListResponse> response) {

                    if (response.body().getStatus().equalsIgnoreCase("200") && response.body().getMessage().equalsIgnoreCase("Success")) {
                        List<Group> groupList = response.body().getUserGroupsList();
                        if (groupList.size() > 0) {
                            improveDataBase.deleteGroupTable();
                            long result = improveDataBase.insertListIntoGroupTable(groupList, userId, sessionManager.getOrgIdFromSession());
                            if (result > 0) {
                                /*Get Token Id*/
//                                mGetTokenId();
                            }
                        } else {
                            /*Get Token Id*/
//                            mGetTokenId();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GroupsListResponse> call, Throwable t) {
                    //dismissProgressDialog();
                    /*Get Token Id*/
//                    mGetTokenId();
                }
            });
        } catch (Exception e) {
            dismissProgressDialog();
            ImproveHelper.improveException(this, TAG, "getGroupsList", e);
        }

    }

//    @Override
//    public void onOTPTimeOut() {
//        showToastAlert(context, "OTP Time out");
//    }
//
//    @Override
//    public void onOTPReceivedError(String error) {
//        showToast(context, error);
//    }

    private void setOTPChars(String strOtp) {
        try {

            CustomEditText1.setText(String.valueOf(strOtp.charAt(0)));
            CustomEditText2.setText(String.valueOf(strOtp.charAt(1)));
            CustomEditText3.setText(String.valueOf(strOtp.charAt(2)));
            CustomEditText4.setText(String.valueOf(strOtp.charAt(3)));
            CustomEditText5.setText(String.valueOf(strOtp.charAt(4)));
            CustomEditText6.setText(String.valueOf(strOtp.charAt(5)));
            cb_otp_submit.performClick();
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "setOTPChars", e);
        }

    }

//    private void showToast(String msg) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//    }

//    private void showToast(String msg) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (smsVerificationReceiver != null) {
            unregisterReceiver(smsVerificationReceiver);
        }
    }

    private String parseCode(String message) {
        String code = "";
        try {
            Pattern p = Pattern.compile("\\b\\d{6}\\b");
            Matcher m = p.matcher(message);
            while (m.find()) {
                code = m.group(0);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "parseCode", e);
        }
        return code;
    }

    public void toolBarViews() {
//        initializeActionBar();
        title.setText(getResources().getString(R.string.profile));
        enableBackNavigation(true);
        ib_settings.setVisibility(View.GONE);
    }

    public void resendOTP_API() {
        try {
            LoginData loginData = new LoginData();
            loginData.setMobileNo(strLoggedInMobileNo);
//            Call<LoginModel> call = getServices.iLogin(loginData);
            Call<LoginModel> call = getServices.iLogin(null);

            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    LoginModel loginModel = response.body();
                    System.out.println("LoginOnResponseSuccess: " + loginModel.getMessage());
//                            Log.d("LoginonResponse", loginModel.getStrMobileNo());
//                strLoggedInMobileNo = ce_mobile_no.getText().toString();
                    if (loginModel.getMessage().equalsIgnoreCase("Success")) {
                        PrefManger.putSharedPreferencesString(OTPActivityOld.this, SP_MOBILE_NO, strLoggedInMobileNo);
                        showToastAlert(OTPActivityOld.this, loginModel.getMessage());
                    } else {
                        showToast(OTPActivityOld.this, loginModel.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    System.out.println("LoginOnResponseFail" + t);
                    Toast.makeText(OTPActivityOld.this, "" + t, Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "resendOTP_API", e);
        }

    }

//    public void mGetTokenId() {
//        try {
//
//            FirebaseMessaging.getInstance().getToken()
//                    .addOnCompleteListener(new OnCompleteListener<String>() {
//                        @Override
//                        public void onComplete(@NonNull Task<String> task) {
//                            if (!task.isSuccessful()) {
//                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                                return;
//                            }  // Get new FCM registration token
//
//                            String newToken = task.getResult();
//                            Log.d(TAG, "GetTokenId: " + newToken);
//
//                            DeviceIdSendData deviceIdSendData = new DeviceIdSendData();
//                            deviceIdSendData.setUserId(sessionManager.getUserDataFromSession().getUserID());
//                            deviceIdSendData.setOrgId(sessionManager.getOrgIdFromSession());
//                            deviceIdSendData.setDevcieID(newToken);
//                            PrefManger.putSharedPreferencesString(context, AppConstants.Login_Device_Id, newToken);
//                            sessionManager.createDeviceIdSession(deviceIdSendData.getDevcieID());
//                            /*Send device id to server*/
//                            if (newToken != null && !newToken.isEmpty()) {
//                                mDeviceIdSendToServerApi(deviceIdSendData);
//                            } /*else {
//                                dismissProgressDialog();
//                            }*/
//                        }
//                    });
//
//        } catch (Exception e) {
//            dismissProgressDialog();
//            ImproveHelper.improveException(this, TAG, "mGetTokenId", e);
//        }
//
//    }

    /*Send device id to server*/
//    public void mDeviceIdSendToServerApi(DeviceIdSendData deviceIdSendData) {
//        try {
//            Call<DeviceIdResponse> deviceIdResponseCall = getServices.sendDeviceIdToServer("",null);
//
//            deviceIdResponseCall.enqueue(new Callback<DeviceIdResponse>() {
//                @Override
//                public void onResponse(Call<DeviceIdResponse> call, Response<DeviceIdResponse> response) {
//
//                    if (response.body() != null) {
//                        Log.d(TAG, "onResponseSuccess: " + response.body().getMessage());
//
//                        updateTokenInFirebase();
//                    }
//                    //dismissProgressDialog();
//                }
//
//                @Override
//                public void onFailure(Call<DeviceIdResponse> call, Throwable t) {
//                    dismissProgressDialog();
//                    Log.d(TAG, "onResponseFail: " + t.getStackTrace());
//
//                }
//            });
//        } catch (Exception e) {
//            dismissProgressDialog();
//            ImproveHelper.improveException(this, TAG, "mDeviceIdSendToServerApi", e);
//        }
//
//    }

//    private void updateTokenInFirebase() {
//        try {
//            firebaseDatabase = FirebaseDatabase.getInstance(firebaseURL).getReference(sessionManager.getOrgIdFromSession());
//            firebaseDatabase.child("Users").orderByChild("Mobile").equalTo(sessionManager.getUserDetailsFromSession().getPhoneNo()).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                    if (!dataSnapshot.exists()) {
//                        Intent intent = new Intent(context, BottomNavigationActivity.class);
//                        startActivity(intent);
//                        finish();
//                        dismissProgressDialog();
//
//                    } else {
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            com.bhargo.user.pojos.firebase.UserDetails userDetails = snapshot.getValue(com.bhargo.user.pojos.firebase.UserDetails.class);
//                            sessionManager.createUserChatID(userDetails.getID());
//                            Log.d(TAG, "userchatid: " + userDetails.getID());
//                        }
//
//                        updateToken();
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    dismissProgressDialog();
//                }
//            });
//        } catch (Exception e) {
//            dismissProgressDialog();
//            ImproveHelper.improveException(this, TAG, "updateTokenInFirebase", e);
//        }
//
//    }

//    private void updateToken() {
//        try {
//            if (sessionManager.isDeviceIDUpdated() != null && sessionManager.isDeviceIDUpdated().equalsIgnoreCase("no")) {
//
//                sessionManager.createDeviceUpdated("yes");
//
//                Token token = new Token(sessionManager.getDeviceIdFromSession());
//                firebaseDatabase.child("Tokens").child(sessionManager.getUserChatID()).setValue(token).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "response: " + "Sucess");
//                        dismissProgressDialog();
//                        Intent intent = new Intent(context, BottomNavigationActivity.class);
//                        startActivity(intent);
//                        finish();
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Write failed
//                        // ...
//                        dismissProgressDialog();
//                        Log.d(TAG, "response: " + e);
//                    }
//                });
//            }
//        } catch (Exception e) {
//            ImproveHelper.improveException(this, TAG, "updateToken", e);
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // ...
            case SMS_CONSENT_REQUEST:
                if (resultCode == RESULT_OK) {
                    // Get SMS message content
                    String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                    // Extract one-time code from the message and complete verification
                    // `sms` contains the entire text of the SMS message, so you will need
                    // to parse the string.
                    String oneTimeCode = parseOneTimeCode(message);
                    setOTPChars(oneTimeCode);

                    // define this function

                    // send one time code to the server
                } else {
                    // Consent canceled, handle the error ...
                }
                break;
        }
    }

    private String parseOneTimeCode(String message) {
        return message.replaceAll("[^0-9]", "");
    }

    public class PinTextWatcher implements TextWatcher {

        private final int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;

            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == CustomEditTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newTypedString = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {

            String text = newTypedString;

            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

            CustomEditTexts[currentIndex].removeTextChangedListener(this);
            CustomEditTexts[currentIndex].setText(text);
            CustomEditTexts[currentIndex].setSelection(text.length());
            CustomEditTexts[currentIndex].addTextChangedListener(this);

            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            try {
                if (!isLast)
                    CustomEditTexts[currentIndex + 1].requestFocus();

                if (isAllEditTextsFilled() && isLast) { // isLast is optional
                    CustomEditTexts[currentIndex].clearFocus();
                    hideKeyboard();
                }
            } catch (Exception e) {
                ImproveHelper.improveException(context, TAG, "moveToNext", e);
            }

        }

        private void moveToPrevious() {
            try {
                if (!isFirst)
                    CustomEditTexts[currentIndex - 1].requestFocus();
            } catch (Exception e) {
                ImproveHelper.improveException(context, TAG, "moveToPrevious", e);
            }

        }

        private boolean isAllEditTextsFilled() {
            boolean isAllTextsFilled = true;
            try {
                for (CustomEditText editText : CustomEditTexts)
                    if (editText.getText().toString().trim().length() == 0)
                        isAllTextsFilled = false;
            } catch (Exception e) {
                ImproveHelper.improveException(context, TAG, "isAllEditTextsFilled", e);
            }
            return isAllTextsFilled;
        }

        private void hideKeyboard() {
            try {
                if (getCurrentFocus() != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            } catch (Exception e) {
                ImproveHelper.improveException(context, TAG, "hideKeyboard", e);
            }
        }
    }

    public class PinOnKeyListener implements View.OnKeyListener {

        private final int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (CustomEditTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    CustomEditTexts[currentIndex - 1].requestFocus();
            }
            return false;
        }

    }
}
