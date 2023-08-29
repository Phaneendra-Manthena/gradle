package com.bhargo.user.screens;

import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.PostChangeAdapter;
import com.bhargo.user.auth.GoogleSign;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.PostsMasterModel;
import com.bhargo.user.pojos.UserTypesMasterModel;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import nk.mobileapps.spinnerlib.SpinnerData;

public class ProfileActivity extends BaseActivity implements PostChangeAdapter.ItemClickListenerPostChange {
    private static final String TAG = "BaseActivity";
    public ImproveHelper improveHelper;
    Context context;
    CircleImageView iv_circle_Profile;
    CustomTextView tv_ProfileName, tv_ProfileDesignation, tv_ProfileEmail, tv_ProfilePhoneNo, tv_ProfilePost, tv_ProfileLocation;
    CustomButton btn_logout, btn_ChangePost, btnChange;
    ImageView iv_Cancel;
    FrameLayout Parentlayout;
    File file;
    RecyclerView bs_rvPosts;
    PostChangeAdapter postChangeAdapter;
    BottomSheetBehavior sheetBehavior;
    NestedScrollView bottom_sheet;
    View mViewBg;
    private SessionManager sessionManager;
    private ImproveDataBase improveDataBase;
    private GetServices getServices;
    private String strFileNameURL, strFileName;
    private ArrayList<SpinnerData> spinnerDataArrayListA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Theme
        setBhargoTheme(this,AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
        /*if (AppConstants.THEME.equalsIgnoreCase("THEME2")) {
            setTheme(R.style.AppTheme2);
        }else if (AppConstants.THEME.equalsIgnoreCase("THEME1")) {
            setTheme(R.style.AppTheme);
        }*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initViews();
    }

    private void initViews() {
        context = this;
        initializeActionBar();
        title.setText(getString(R.string.profile));
        enableBackNavigation(true);
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);
        sessionManager = new SessionManager(context);
        getServices = RetrofitUtils.getUserService();
        iv_circle_Profile = findViewById(R.id.iv_circle_Profile);
        tv_ProfileName = findViewById(R.id.tv_ProfileName);
        tv_ProfileDesignation = findViewById(R.id.tv_ProfileDesignation);
        tv_ProfileEmail = findViewById(R.id.tv_ProfileEmail);
        tv_ProfileLocation = findViewById(R.id.tv_ProfileLocation);
        tv_ProfilePhoneNo = findViewById(R.id.tv_ProfilePhoneNo);
        tv_ProfilePost = findViewById(R.id.tv_ProfilePost);
        btn_ChangePost = findViewById(R.id.btn_ChangePost);
        btn_logout = findViewById(R.id.btn_logout);
        iv_Cancel = findViewById(R.id.iv_Cancel);
        btnChange = findViewById(R.id.btn_Change);

        bottom_sheet = findViewById(R.id.bottom_sheet_post);
        mViewBg = findViewById(R.id.bs_bg);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
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
        bs_rvPosts = bottom_sheet.findViewById(R.id.bs_rvPosts);

        List<PostsMasterModel> postList = new ArrayList<>();
        postChangeAdapter = new PostChangeAdapter(context, postList, sheetBehavior,sessionManager);
        bs_rvPosts.setAdapter(postChangeAdapter);
        postChangeAdapter.setCustomClickListener(ProfileActivity.this);

        btn_logout.setOnClickListener(v -> {
            logoutUser();
        });
        btnChange.setOnClickListener(v -> {
            PostsMasterModel strSelectedPost = postChangeAdapter.getPostID();
            if (strSelectedPost != null && !sessionManager.getPostsFromSession().equalsIgnoreCase(strSelectedPost.getID())) {
                AppConstants.OrgChange =  1;
                AppConstants.PostChange = 1;
                sessionManager.createPostsSession(strSelectedPost.getID());
                sessionManager.setPostName(strSelectedPost.getName());
                sessionManager.setUserType(strSelectedPost.getUserType());
                sessionManager.setUserTypeIds(strSelectedPost.getLoginTypeID());
                setProgressBarForRefresh(1);
                redirectToAppsList();
            } else {
                setProgressBarForRefresh(0);
                AppConstants.PostChange = 0;
            }
        });
        iv_Cancel.setOnClickListener(v -> {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        });
        btn_ChangePost.setOnClickListener(v -> changePost());
        setProfileData();
    }

    private void redirectToAppsList() {
        try {
            BottomNavigationActivity.FROM_TAB = 0;
            Intent intent = new Intent(context, BottomNavigationActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "redirectToAppsList", e);
        }
    }

    public void setProgressBarForRefresh(int value) {
        try {
            AppConstants.PROGRESS_CHART = value;
            AppConstants.PROGRESS_APPS = value;
            AppConstants.PROGRESS_REPORT = value;
            AppConstants.PROGRESS_TASK = value;
            AppConstants.PROGRESS_E_LEARNING = value;
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "setProgressBarForRefresh", e);
        }

    }

    private void changePost() {
        try {
            NestedScrollView bottom_sheetPosts = findViewById(R.id.bottom_sheet_post);
            ImageView iv_Cancel = bottom_sheetPosts.findViewById(R.id.iv_Cancel);
            CustomButton btnChange = bottom_sheetPosts.findViewById(R.id.btn_Change);

            sheetBehavior = BottomSheetBehavior.from(bottom_sheetPosts);
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            RecyclerView bs_rvPosts = bottom_sheetPosts.findViewById(R.id.bs_rvPosts);
            mViewBg.setVisibility(View.VISIBLE);
//            List<Post> postList = new ArrayList<>();
            List<PostsMasterModel> postList = new ArrayList<>();
//            postList = loadPosts();
            PostChangeAdapter postChangeAdapter = new PostChangeAdapter(context,postList,sheetBehavior,sessionManager);
            bs_rvPosts.setAdapter(postChangeAdapter);
            postChangeAdapter.updateList(sessionManager.getUserPostDetails());
            postChangeAdapter.setCustomClickListener(ProfileActivity.this);

            iv_Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            });
            btnChange.setOnClickListener(v -> {
//                OTPUtils otpUtils = new OTPUtils(ProfileActivity.this, sessionManager, null, TAG,improveDataBase,-1,-1,null);
//                otpUtils.mUserDetails();
                PostsMasterModel strSelectedPost = postChangeAdapter.getPostID();
                if(strSelectedPost != null) {
                    sessionManager.createPostsSession(strSelectedPost.getID());
                    sessionManager.setPostName(strSelectedPost.getName());
                    sessionManager.setUserType(strSelectedPost.getUserType());
                    sessionManager.setUserTypeIds(strSelectedPost.getID());
                }
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                mViewBg.setVisibility(View.GONE);
                AppConstants.OrgChange = 1;
                setProgressBarForRefresh(1);
                redirectToAppsList();
            });

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "selectPostDialog", e);
        }

//        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

    }

    private void logoutUser() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.AlertDialogTheme);
        final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = linflater.inflate(R.layout.custom_alert_dialog, null);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.alertMessage)).setText(R.string.want_to_logout);
        ((Button) view.findViewById(R.id.buttonYes)).setText(R.string.yes);
        ((Button) view.findViewById(R.id.buttonNo)).setText(R.string.no);
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (AppConstants.DefultAPK) {
                    AppConstants.DefultAPK_afterLoginPage_loaded = true;
                    PrefManger.clearSharedPreferences(context);
                    improveDataBase.deleteDatabaseTables(sessionManager.getUserDataFromSession().getUserID());
                    sessionManager.logoutUser();
                } else {
                    AppConstants.GlobalObjects = null;
                    PrefManger.clearSharedPreferences(context);
                    improveDataBase.deleteDatabaseTables(sessionManager.getUserDataFromSession().getUserID());
                    sessionManager.logoutUserandExit();
                }
                ImproveHelper.insertAccessLog(context,"Logout","Logout");
                //Google logout
                GoogleSign googleSign=new GoogleSign(ProfileActivity.this,null);
                googleSign.signOut();

            }
        });
        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    public void setProfileData() {
        try {
            if (!isNetworkStatusAvialable(context)) {
                if(sessionManager == null){
                    sessionManager = new SessionManager(context);
                }
                if (sessionManager.getUserDataFromSession().getImagePath() != null) {
                    String[] str = sessionManager.getUserDataFromSession().getImagePath().split("/");
                    strFileNameURL = str[str.length - 1];
                    String strFilepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + "ProfileImage" + "/" + strFileNameURL;
                    File file = new File(strFilepath);
                    if (file.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(strFilepath);
                        iv_circle_Profile.setImageBitmap(myBitmap);
                    } else {
                        iv_circle_Profile.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_user));
                    }

                    improveHelper.snackBarAlertActivities(context);
                }
            } else {
                if (sessionManager.getUserDataFromSession().getImagePath() != null) {
                    if (!sessionManager.getUserDataFromSession().getImagePath().equalsIgnoreCase("NA")) {
                        Glide.with(context).load(sessionManager.getUserDataFromSession().getImagePath())
                                .placeholder(R.drawable.ic_icon_user)
                                .into(iv_circle_Profile);
                        new DownloadProfileFromURLTask().execute(sessionManager.getUserDataFromSession().getImagePath());

                    } else {
                        iv_circle_Profile.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_user));
                    }
                } else {
                    iv_circle_Profile.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_user));
                }
            }
            tv_ProfileName.setText(sessionManager.getUserDataFromSession().getUserName());
            tv_ProfilePost.setText(sessionManager.getPostsFromSessionPostName());
            tv_ProfileEmail.setText(sessionManager.getUserDataFromSession().getEmail());
            tv_ProfilePhoneNo.setText(sessionManager.getUserDataFromSession().getPhoneNo());

            String user_location = improveHelper.getLocationLevelForUserProfile(sessionManager.getUserPostDetails(), sessionManager.getPostsFromSession());

            tv_ProfileLocation.setText(user_location);
//            OTPUtils otpUtils = new OTPUtils(ProfileActivity.this, sessionManager, null, TAG,improveDataBase,-1,-1,null);
//            otpUtils.mUserDetails();
//            postChangeAdapter.updateList(loadPosts());
//            mUserDetails(sessionManager.getOrgIdFromSession());


        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "setProfileValues", e);
        }
    }

    /*User Details API*/
//    private void mUserDetails(String i_OrgId) {
//        try {
//
//            UserDetailsData userDetailsData = new UserDetailsData();
//            userDetailsData.setMobileNo(sessionManager.getUserDataFromSession().getPhoneNo());
//            userDetailsData.setOrgId(i_OrgId);
//
//            final Call<UserDetailsModel> userDetailsCall = getServices.iUserDetails(userDetailsData);
//
//            userDetailsCall.enqueue(new Callback<UserDetailsModel>() {
//                @Override
//                public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
//
//                    if (response.body() != null) {
//
//                        UserDetailsModel userDetailsModel = response.body();
//                        if (userDetailsModel.getStatus().equalsIgnoreCase("200")) {
//                            UserDetails userDetails = userDetailsModel.getUserDeatils();
//                            List<UserPostDetails> userPostDetails = userDetailsModel.getUserPostDetails();
//                            UserDetailsModel.ReportingUserDeatils reportingUserDetails = userDetailsModel.getReportingUserDeatils();
//                            String strUserId = userDetails.getUserId();
//                            /*Setting User Details Object into SharedPreferences*/
//                            Gson gson = new Gson();
//                            String jsonUserDeatils = gson.toJson(userDetails);
//                            PrefManger.putSharedPreferencesString(context, SP_USER_DETAILS, jsonUserDeatils);
//                            String jsonReportingUserDetails = gson.toJson(reportingUserDetails);
//                            PrefManger.putSharedPreferencesString(context, SP_REPORTING_USER_DETAILS, jsonReportingUserDetails);
//                            sessionManager.createUserPostDetailsSession(userPostDetails);
//                            postChangeAdapter.updateList(loadPosts());
//
//                        } else {
//                            ImproveHelper.showToast(context, "No user details found");
//                        }
//
//                    } else {
//                        ImproveHelper.showToast(context, "No user details found");
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<UserDetailsModel> call, Throwable t) {
//                    Log.d(TAG, "onFailureUserDetailsData: " + t);
//                }
//            });
//        } catch (Exception e) {
//            ImproveHelper.improveException(this, TAG, "mUserDetails", e);
//        }
//
//    }
    public List<PostsMasterModel> loadPosts() {
        List<PostsMasterModel> postsMasterModelList = null;
        try {
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
/*
            if (sessionManager.getUserPostDetails() != null && sessionManager.getUserPostDetails().size() > 0) {

                SpinnerData spinnerDataDef = new SpinnerData();
                spinnerDataDef.setName("-- Select Post --");
                spinnerDataDef.setId("0");
                spinnerDataDef.setObject(postsMasterModelList);
                spinnerDataDef.setSelected(true);
                spinnerDataArrayListA.add(spinnerDataDef);
                for (int i = 0; i < postsMasterModelList.size(); i++) {
                    SpinnerData spinnerData = new SpinnerData();
                    spinnerData.setName(postsMasterModelList.get(i).getName());
                    Log.d(TAG, "loadPosts1: " + postsMasterModelList.get(i).getName());
                    Log.d(TAG, "loadPosts2: " + spinnerData.getName());
                    spinnerData.setId(postsMasterModelList.get(i).getID());
                    spinnerData.setObject(postsMasterModelList);
                    spinnerDataArrayListA.add(spinnerData);

                    if (flag == 0 && sessionManager.getPostsFromSession().equalsIgnoreCase(spinnerData.getId())) {
                        spinnerDataArrayListA.get(0).setSelected(false);
                        spinnerData.setSelected(true);
                    }
                }
            }
*/

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "loadPosts", e);
        }

        return postsMasterModelList;

    }

    @Override
    public void onCustomClick(int value,PostsMasterModel post) {
        setProgressBarForRefresh(value);
        if (value == 1) {
            AppConstants.OrgChange =  1;
            AppConstants.PostChange = 1;
            sessionManager.createPostsSession(post.getID());
            sessionManager.setPostName(post.getName());
            sessionManager.setUserType(post.getUserType());
            sessionManager.setUserTypeIds(post.getLoginTypeID());
            redirectToAppsList();
        } else {
            AppConstants.PostChange = 0;
        }
    }
//    private List<Post> loadPosts() {
//        List<Post> postList = new ArrayList<>();
//        try {
//            if (sessionManager.getUserPostDetailsFromSession() != null && sessionManager.getUserPostDetailsFromSession().size() > 0) {
//                for (int i = 0; i < sessionManager.getUserPostDetailsFromSession().size(); i++) {
//                    Post post = new Post();
//                    post.setPostId(sessionManager.getUserPostDetailsFromSession().get(i).getPostID());
//                    post.setPostName(sessionManager.getUserPostDetailsFromSession().get(i).getName());
//                    post.setSelected(false);
//                    if (sessionManager.getPostsFromSession().equalsIgnoreCase(post.getPostId())) {
//                        post.setSelected(false);
//                    }
//                    postList.add(post);
//                }
//            }
//        } catch (Exception e) {
//            ImproveHelper.improveException(this, TAG, "loadPosts", e);
//        }
//
//        return postList;
//    }

    public class DownloadProfileFromURLTask extends AsyncTask<String, String, String> {

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

                file = new File(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + "ProfileImage");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File outFile = new File(file, strFileName);
                // Output stream to write file
                OutputStream output = new FileOutputStream(outFile);

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
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @SuppressLint("SdCardPath")
        @Override
        protected void onPostExecute(String file_url) {
            if (file != null && file.exists()) {
                strFileNameURL = strFileName;
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                    navigateBack();
                return true;
            default:
                return false;
        }
    }
    @Override
    public void onBackPressed() {
        navigateBack();

    }
    public void navigateBack() {
        try {
            hideKeyboard(ProfileActivity.this, view);
            ImproveHelper.alertDialogWithRoundShapeMaterialTheme(ProfileActivity.this, getString(R.string.are_you_sure),
                    getString(R.string.yes), getString(R.string.d_no), new ImproveHelper.IL() {
                        @Override
                        public void onSuccess() {
                            BottomNavigationActivity.FROM_TAB = 0;
                            finish();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "mainActivityOnBackPressAlertDialog", e);
        }
    }

}