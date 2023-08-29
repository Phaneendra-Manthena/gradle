package com.bhargo.user.screens.sesssionchat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.bhargo.user.R;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;

public class GroupMembersListActivity extends BaseActivity {


    private static final String TAG = "GroupMembersListActivity";
    String weburl = "";
    String groupName, groupId, userId, orgId;
    SessionManager sessionManager;
    Context context;
    ImproveHelper improveHelper;
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
        setContentView(R.layout.activity_group_members_list);

        context = this;
        improveHelper = new ImproveHelper(this);

        try {
            Intent intentData = getIntent();
            if (intentData != null) {

                groupName = intentData.getStringExtra("GroupName");
                groupId = intentData.getStringExtra("GroupId");

            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "onCreate", e);
        }
        progress = findViewById(R.id.progressBar);
        initializeActionBar();
        enableBackNavigation(true);

        title.setText(groupName);

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

//        String url = "http://182.18.157.124/improve/WebForms/DashBoardBuilder/DashBoardView.html?PageName=";

        String extensionUrl = "orgID=" + orgId + "&userID=" + userId + "&groupID=" + groupId;
//        weburl = RetrofitUtils.BASE_URL + "improve_V3.0" + "/WebForms/GroupManagement/GMDetails.html?" + extensionUrl;
        weburl = RetrofitUtils.BASE_URL + AppConstants.GROUP_INFO_METHOD + "/WebForms/GroupManagement/GMDetails.html?" + extensionUrl;
        webView.loadUrl(weburl);
        progress.setProgress(0);
        showProgressDialog(getString(R.string.please_wait));


    }

    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {

        finish();
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

}