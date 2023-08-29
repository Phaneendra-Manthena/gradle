package com.bhargo.user.screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

import com.bhargo.user.R;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveHelper;

public class CallWebPageViewActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar progress;
    String weburl ="";
    private String strAppDesign;
    public String strAppName;
    ImproveHelper improveHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_link_view);

        improveHelper = new ImproveHelper(this);
        progress = findViewById(R.id.progressBar);
        initializeActionBar();
        enableBackNavigation(true);

        progress.setMax(100);
        webView=findViewById(R.id.webView);


        Intent getIntent = getIntent();

        if (getIntent != null) {
            weburl = getIntent.getStringExtra("WebLink");
        }

//        weburl="http://www.google.com";
        if(!weburl.startsWith("http")){
            weburl="http://"+weburl;
        }

        webView.setWebChromeClient(new MyWebViewClient());

        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setAllowFileAccess(true);

        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.setWebViewClient(new Callback());
        webView.getSettings().setJavaScriptEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(weburl);
        progress.setProgress(0);

    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private class MyWebViewClient extends WebChromeClient {


        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            setValue(newProgress);
            System.out.println("newProgress=="+newProgress);
            if(newProgress>99){
                progress.setVisibility(View.GONE);
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

    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                hideKeyboard(this, view);
                finish();
              /*  AlertDialog alertDialog = new AlertDialog.Builder(this)

                        .setTitle("Are you sure to Exit")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
                                dialogInterface.dismiss();
                            }
                        })
                        .show();*/
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {

        hideKeyboard(this, view);
        AlertDialog alertDialog = new AlertDialog.Builder(this)

                .setTitle("Are you sure to Exit")

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      finish();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        dialogInterface.dismiss();
                    }
                })
                .show();

    }

}
