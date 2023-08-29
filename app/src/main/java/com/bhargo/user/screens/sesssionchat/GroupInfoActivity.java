package com.bhargo.user.screens.sesssionchat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.GroupInfoResponse;
import com.bhargo.user.pojos.ServiceResultModel;
import com.bhargo.user.screens.BottomNavigationActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupInfoActivity extends BaseActivity {

    private static final String TAG = "GroupInfoActivity";
    String groupName = null;
    String sessionIcon = null;
    String groupId = null;
    String sessionId = null;
    String sessionName = null;
    CustomTextView tv_SessionName, tv_created_by, tvGroupName, tvNoOfMembers, tvGroupDescription;
    ImageView iv_SessionIcon;
    LinearLayout layoutGroupMembers;
    Context context;
    GetServices getServices;
    ImproveHelper improveHelper;
    SessionManager sessionManager;
    ImproveDataBase improveDataBase;

    String weburl = "";
    String  userId, orgId;
    private WebView webView;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppConstants.THEME.equalsIgnoreCase("THEME2")) {
            setTheme(R.style.AppTheme2);
        }else if (AppConstants.THEME.equalsIgnoreCase("THEME1")) {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);



        context = GroupInfoActivity.this;
        improveHelper = new ImproveHelper(context);
        getServices = RetrofitUtils.getUserService();
        sessionManager = new SessionManager(context);
        improveDataBase = new ImproveDataBase(context);
        try {
            Intent intentData = getIntent();
            if (intentData != null) {
                groupName = intentData.getStringExtra("GroupName");
                groupId = intentData.getStringExtra("GroupId");
                sessionIcon = intentData.getStringExtra("SessionIcon");
                sessionId = intentData.getStringExtra("SessionID");
                sessionName = intentData.getStringExtra("SessionName");
                Log.d(TAG, "initViews: " + sessionId + "," + groupId);
            }
        } catch (Exception e) {
            Log.d(TAG, "onCreate: " + e.toString());
        }

        initViews();

    }

    private void initViews() {
        try {
            initializeActionBar();

            title.setVisibility(View.GONE);
            subTitle.setVisibility(View.GONE);
            ib_settings.setVisibility(View.GONE);

            iv_SessionIcon = findViewById(R.id.iv_SessionIcon);
            tv_SessionName = findViewById(R.id.tvSessionName);
            tv_created_by = findViewById(R.id.tvCreatedBy);
            tvGroupName = findViewById(R.id.tvGroupName);
            tvNoOfMembers = findViewById(R.id.tvNoOfMembers);
            tvGroupDescription = findViewById(R.id.tvGroupDescription);
            progress = findViewById(R.id.progressBar);
            layoutGroupMembers = findViewById(R.id.layout_groupmembers);

            layoutGroupMembers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ImproveHelper.isNetworkStatusAvialable(context)) {
                        Intent intent = new Intent(context, GroupMembersListActivity.class);
                        intent.putExtra("GroupName", groupName);
                        intent.putExtra("GroupId", groupId);
                        startActivity(intent);

                    } else {
                        Toast.makeText(context, getString(R.string.pls_check_network), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            /*layout_delete_session.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ImproveHelper.isNetworkStatusAvialable(context)) {

                        improveHelper.alertDialogWithMaterialTheme(context, "Are you sure to delete this Session?", "Yes",
                                "No", new ImproveHelper.IL() {
                                    @Override
                                    public void onSuccess() {
                                        deleteSession();
                                    }

                                    @Override
                                    public void onCancel() {

                                    }
                                });
                    } else {
                        Toast.makeText(context, getString(R.string.pls_check_network), Toast.LENGTH_SHORT).show();
                    }

                }
            });*/

            Glide.with(context).load(sessionIcon).into(iv_SessionIcon);
            tv_SessionName.setText(sessionName);
            tvGroupName.setText(groupName);

            getGroupInfoApi();
            showGroupMembers();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "initViews", e);
        }
    }

    private void showGroupMembers() {

        progress.setMax(100);
        webView = findViewById(R.id.webView);
        webView.clearCache(true);
        webView.clearFormData();
        webView.clearHistory();
        webView.setWebChromeClient(new MyWebViewClient());

        sessionManager = new SessionManager(context);

        userId = sessionManager.getUserDataFromSession().getUserID();
        orgId = sessionManager.getOrgIdFromSession();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        String extensionUrl = "&userID=" + userId + "&groupID=" + groupId;
        weburl = RetrofitUtils.BASE_URL + AppConstants.GROUP_INFO_METHOD + "/GroupManagement/GMDetails.html?" + extensionUrl;
        webView.loadUrl(weburl);
        progress.setProgress(0);
        showProgressDialog(getString(R.string.please_wait));


    }

    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }
    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            setValue(newProgress);
            System.out.println("newProgress==" + newProgress);
            if (newProgress > 99) {
                progress.setVisibility(View.GONE);
                dismissProgressDialog();
            }
            super.onProgressChanged(view, newProgress);
        }


        //For Android API < 11 (3.0 OS)
        public void openFileChooser(ValueCallback<Uri> valueCallback) {

        }

        //For Android API >= 11 (3.0 OS)
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {

        }

        //For Android API >= 21 (5.0 OS)
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {

            return true;
        }


    }
    private void getGroupInfoApi() {
        try {
            improveHelper.showProgressDialog(getString(R.string.please_wait));

//        GroupInfoSendData detailsData = new GroupInfoSendData();
//        detailsData.setOrgId(sessionManager.getOrgIdFromSession());
//        detailsData.setGroupID(groupId);
//        detailsData.setSessionID(sessionId);
//        Gson gson = new Gson();
//        Log.d(TAG, "getGroupInfoApi: "+gson.toJson(detailsData));
            Call<GroupInfoResponse> call = getServices.getGroupInfoDetails(sessionManager.getAuthorizationTokenId(),sessionManager.getOrgIdFromSession(), groupId, "sessionId");

            call.enqueue(new Callback<GroupInfoResponse>() {
                @Override
                public void onResponse(Call<GroupInfoResponse> call, Response<GroupInfoResponse> response) {

                    if (response.body() != null) {
                        GroupInfoResponse groupInfoResponse = response.body();
                        if (groupInfoResponse != null) {
                            if (groupInfoResponse.getStatus() != null && groupInfoResponse.getStatus().equalsIgnoreCase("200")) {
//                            ImproveHelper.showToast(context, groupInfoResponse.getMessage());
                                setDataToViews(groupInfoResponse.getServiceResult());
                            } else {
                                ImproveHelper.showToast(context, groupInfoResponse.getMessage());
                            }
                        }
                    } else {
                        Log.d(TAG, "onResponse: No Response");
                    }
                    improveHelper.dismissProgressDialog();
                }

                @Override
                public void onFailure(Call<GroupInfoResponse> call, Throwable t) {
                    Log.d(TAG, "onFailureAssessment: " + t.getMessage());
                    improveHelper.dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getGroupInfoApi", e);
        }

    }

    private void setDataToViews(ServiceResultModel serviceResult) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            DateFormat formatter1 = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
            String createdDate = null;
            try {
                createdDate = formatter1.format(Objects.requireNonNull(formatter.parse(serviceResult.getSessionCreatedDate())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String creadtedBy = serviceResult.getSessionCreatedBy() + ", " + createdDate;
            tv_created_by.setText(creadtedBy);
            tvNoOfMembers.setText(serviceResult.getGroupMembersCount());
            tvGroupDescription.setText(serviceResult.getGroupDescription());
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "setDataToViews", e);
        }

    }

    private void deleteSession() {
        try {
            improveHelper.showProgressDialog(getString(R.string.please_wait));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("OrgId", sessionManager.getOrgIdFromSession());
            jsonObject.put("CreatedUserID", sessionManager.getUserDataFromSession().getUserID());
            jsonObject.put("SessionID", sessionId);
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(jsonObject.toString());

            Call<ResponseBody> call = getServices.deleteSession(jo);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    improveHelper.dismissProgressDialog();
                    try {
                        if (response.body() != null) {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            String resultStatus = jsonObject.getString("Status");
                            String message = jsonObject.getString("Message");
                            if (resultStatus != null && resultStatus.equalsIgnoreCase("200")) {
                                deleteSessionHistoryinDB();
                            } else {
                                ImproveHelper.showToast(context, message);
                            }
                        } else {
                            Toast.makeText(GroupInfoActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(GroupInfoActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d(TAG, "onFailureAssessment: " + t.getMessage());
                    improveHelper.dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getGroupInfoApi", e);
        }

    }

    private void deleteSessionHistoryinDB() {

        long res =improveDataBase.deleteSessionNotifications(sessionId, groupId, sessionManager.getOrgIdFromSession());
        if(res>0){
            Intent intent = new Intent(context, BottomNavigationActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.deleteSession:
                if (ImproveHelper.isNetworkStatusAvialable(context)) {

                    improveHelper.alertDialogWithMaterialTheme(context, "Are you sure to delete this Session?", "Yes",
                            "No", new ImproveHelper.IL() {
                                @Override
                                public void onSuccess() {
                                    deleteSession();
                                }

                                @Override
                                public void onCancel() {

                                }
                            });
                } else {
                    Toast.makeText(context, getString(R.string.pls_check_network), Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

   /* @Override
    public void onCreateOptionsMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.group_info_options, menu);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.group_info_options, menu);
        return super.onCreateOptionsMenu(menu);
    }
}