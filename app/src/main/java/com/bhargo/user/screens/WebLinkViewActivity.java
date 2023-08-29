package com.bhargo.user.screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bhargo.user.Expression.ExpressionMainHelper;
import com.bhargo.user.Java_Beans.WebLinkObject;
import com.bhargo.user.R;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.MimeTypes;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.XMLHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class WebLinkViewActivity extends BaseActivity {

    private static final String TAG = "WebLinkViewActivity";
    public String strAppName, FromNormalTask;
    String weburl = "";
    XMLHelper xmlHelper;
    WebLinkObject WebLinkObject;
    ImproveHelper improveHelper;
    private WebView webView;
    private ProgressBar progress;
    private String strAppDesign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_link_view);
        progress = findViewById(R.id.progressBar);
        initializeActionBar();
        enableBackNavigation(true);
        improveHelper = new ImproveHelper(this);

        progress.setMax(100);
        webView = findViewById(R.id.webView);
        webView.setWebChromeClient(new MyWebViewClient());

        Intent getIntent = getIntent();
        xmlHelper = new XMLHelper();

        if (getIntent != null) {
            strAppDesign = getIntent.getStringExtra("s_app_design");
        }
        WebLinkObject = xmlHelper.XML_To_WebLinkObject(strAppDesign.trim());


        weburl = WebLinkObject.getWebLink_Url();
        if (WebLinkObject.getWebLink_TypeofParameter().equalsIgnoreCase(getResources().getString(R.string.query_string))) {
            weburl = weburl + "?" + getQueryString();
        } else if (WebLinkObject.getWebLink_TypeofParameter().equalsIgnoreCase(getResources().getString(R.string.delimiter))) {
            weburl = weburl + "/" + getdelimiterString();
        }

        System.out.println("weburl==="+weburl);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(weburl);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Log.d(TAG, "onDownloadStartListener: " + url);
                if (url.startsWith("data:")) {  //when url is base64 encoded data
                    String ext = MimeTypes.getDefaultExt(mimetype);
                    createAndSaveFileFromBase64Url(url, ext);
                    return;
                }
            }
        });
        progress.setProgress(0);

        FromNormalTask = getIntent.getStringExtra("FromNormalTask");
        strAppName = getIntent.getStringExtra("s_app_name");
        if (strAppName != null) {
            title.setText(strAppName);
        }

    }

    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }

    public String getQueryString() {
        String Data = "";
        try {
            if (WebLinkObject.getHash_QueryStrings() != null && WebLinkObject.getHash_QueryStrings().size() > 0) {
                LinkedHashMap<String, String> hash_qs = WebLinkObject.getHash_QueryStrings();
                Set<String> QuerySet = hash_qs.keySet();
                String[] QueryNames = QuerySet.toArray(new String[QuerySet.size()]);
                for (int i = 0; i < QueryNames.length; i++) {
                    ExpressionMainHelper ehelper = new ExpressionMainHelper();
                    String Expvalue = ehelper.ExpressionHelper(this, hash_qs.get(QueryNames[i]));
                    Data = Data + "&" + QueryNames[i] + "=" + Expvalue;
                }

            }
            if (Data.startsWith("&")) {
                Data = Data.substring(1);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getQueryString", e);
        }
        return Data;
    }

    public String getdelimiterString() {
        String Data = "";
        try {
            if (WebLinkObject.getHash_QueryStrings() != null && WebLinkObject.getHash_QueryStrings().size() > 0) {
                List<String> list_D = WebLinkObject.getList_Delimiters();
                for (int i = 0; i < list_D.size(); i++) {
                    ExpressionMainHelper ehelper = new ExpressionMainHelper();
                    String Expvalue = ehelper.ExpressionHelper(this, list_D.get(i));
                    Data = Data + "/" + Expvalue;
                }

            }
            if (Data.startsWith("/")) {
                Data = Data.substring(1);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getdelimiterString", e);
        }

        return Data;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                hideKeyboard(this, view);

                Intent intent = new Intent(WebLinkViewActivity.this, BottomNavigationActivity.class);
                if (FromNormalTask != null && !FromNormalTask.isEmpty()) {
                    intent.putExtra("FromNormalTask", "FromNormalTask");
                } else {
                    if (PrefManger.getSharedPreferencesString(WebLinkViewActivity.this, AppConstants.Notification_Back_Press, "") != null) {
                        String onBackFrom = PrefManger.getSharedPreferencesString(WebLinkViewActivity.this, AppConstants.Notification_Back_Press, "");
                        if (onBackFrom.equalsIgnoreCase("0")) {
                            intent.putExtra("NotifRefreshAppsList", "0");
                        } else {
                            intent.putExtra("NotifRefreshAppsList", "0");
                        }
                    }
                }
                startActivity(intent);
//                AlertDialog alertDialog = new AlertDialog.Builder(this)
//
//                        .setTitle("Are you sure to Exit")
//
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
////                                finish();
//
//                            }
//                        })
//
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //set what should happen when negative button is clicked
//                                dialogInterface.dismiss();
//                            }
//                        })
//                        .show();
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
//                      finish();
                        Intent intent = new Intent(WebLinkViewActivity.this, BottomNavigationActivity.class);
                        if (FromNormalTask != null && !FromNormalTask.isEmpty()) {
                            intent.putExtra("FromNormalTask", "FromNormalTask");
                        } else {
                            if (PrefManger.getSharedPreferencesString(WebLinkViewActivity.this, AppConstants.Notification_Back_Press, "") != null) {
                                String onBackFrom = PrefManger.getSharedPreferencesString(WebLinkViewActivity.this, AppConstants.Notification_Back_Press, "");
                                if (onBackFrom.equalsIgnoreCase("0")) {
                                    intent.putExtra("NotifRefreshAppsList", "0");
                                } else {
                                    intent.putExtra("NotifRefreshAppsList", "0");
                                }
                            }
                        }
                        startActivity(intent);
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

    public String createAndSaveFileFromBase64Url(String url, String extension) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String filetype = url.substring(url.indexOf("/") + 1, url.indexOf(";"));
        String filename = System.currentTimeMillis() + "." + extension;
        File file = new File(path, filename);
        try {
            if (!path.exists())
                path.mkdirs();
            if (!file.exists())
                file.createNewFile();

            String base64EncodedString = url.substring(url.indexOf(",") + 1);
            byte[] decodedBytes = Base64.decode(base64EncodedString, Base64.DEFAULT);
            OutputStream os = new FileOutputStream(file);
            os.write(decodedBytes);
            os.close();

            //Tell the media scanner about the new file so that it is immediately available to the user.
            MediaScannerConnection.scanFile(this,
                    new String[]{file.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });


            Toast.makeText(getApplicationContext(), "file_downloaded:" + Uri.fromFile(file), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.w("ExternalStorage", "Error writing " + file, e);
            Toast.makeText(getApplicationContext(), "error_downloading", Toast.LENGTH_LONG).show();
            improveHelper.improveException(this, TAG, "createAndSaveFileFromBase64Url", e);
        }

        return file.toString();
    }

    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            setValue(newProgress);
            System.out.println("newProgress==" + newProgress);
            if (newProgress > 99) {
                progress.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }

    }


}
