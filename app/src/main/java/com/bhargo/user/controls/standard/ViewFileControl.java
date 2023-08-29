package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.controls.variables.ViewFileVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.FileDownloadFromURL;
import com.bhargo.user.utils.ImproveHelper;

import java.io.File;

public class ViewFileControl implements View.OnTouchListener, ViewFileVariables, UIVariables {

    private static final String TAG = "ViewFileControl";
    private final Activity context;
    private final ControlObject controlObject;
    private final boolean subFormFlag;
    private final int subFormPos;
    private final String subFormName;
    TextView tv_filelink;
    ImageView iv_viewfile;
    View rView;
    boolean download = false;
    WebView webView;
    FrameLayout flWebView;
    CardView cv_all;
    private LinearLayout linearLayout, ll_main_inside,ll_displayName,ll_tap_text,ll_control_ui;
    private CustomTextView tv_displayName, tv_hint,ct_showText;

    public ViewFileControl(Activity context, ControlObject controlObject, boolean subFormFlag, int subFormPos, String subFormName) {
        this.context = context;
        this.controlObject = controlObject;
        this.subFormFlag = subFormFlag;
        this.subFormPos = subFormPos;
        this.subFormName = subFormName;
        initViews();
    }

    private void initViews() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                    rView = lInflater.inflate(R.layout.control_view_file_preview, null);
                }else  if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {
                    rView = lInflater.inflate(R.layout.control_view_file_preview_nine, null);
                } else {
//                rView = lInflater.inflate(R.layout.control_viewfile, null);
                    rView = lInflater.inflate(R.layout.layout_viewfile_default, null);
                }
            } else {
//                rView = lInflater.inflate(R.layout.control_viewfile, null);
                rView = lInflater.inflate(R.layout.layout_viewfile_default, null);
            }
            cv_all = rView.findViewById(R.id.cv_all);
            ll_main_inside = rView.findViewById(R.id.ll_main_inside);
            tv_displayName = rView.findViewById(R.id.tv_displayName);
            ll_displayName = rView.findViewById(R.id.ll_displayName);
            tv_hint = rView.findViewById(R.id.tv_hint);
            ll_tap_text = rView.findViewById(R.id.ll_tap_text);
            ll_control_ui = rView.findViewById(R.id.ll_control_ui);


            flWebView = rView.findViewById(R.id.flWebView);
            webView = rView.findViewById(R.id.webView);
            tv_filelink = rView.findViewById(R.id.tv_filelink);
            iv_viewfile = rView.findViewById(R.id.iv_viewfile);
            ct_showText = rView.findViewById(R.id.ct_showText);
            webView.setOnTouchListener(this);
            webView.setBackgroundColor(0);
            webView.getSettings().setUseWideViewPort(true);
            flWebView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tv_filelink.getTag().toString().trim().length() == 0) {
                        ImproveHelper.showToast(context, "No Link To View File!");
                    } else {
                        if(tv_filelink != null) {
                            viewFileFromUrl(tv_filelink.getTag().toString().trim());
                        }
                    }

                }
            });

            iv_viewfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tv_filelink != null && tv_filelink.getTag().toString().trim().length() == 0) {
                        ImproveHelper.showToast(context, "No Link To View File!");
                    } else {
                        viewFileFromUrl(tv_filelink.getTag().toString().trim());
                    }

                }
            });


            setControlValues();
            linearLayout.addView(rView);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initViews", e);
        }
    }

    private void viewFileFromUrl(String filelink) {
//        filelink= "http://182.18.157.124/ImproveUploadFiles/UploadFiles/check_map_existing/20220224130811_VID_20220224_130434.mp4";
        File fileStorage = ImproveHelper.createFolder(context, "ImproveUserFiles/Download");
        String[] urlSplit = filelink.split("/");
        String fileName = urlSplit[urlSplit.length - 1];//Create file name by picking download file name from URL
        File outputFile = new File(fileStorage, fileName);//Create Output file in Main File
        if (!download) {
            outputFile = new File(context.getExternalCacheDir(), fileName);
            Log.d(TAG, "viewFileFromUrl: " + outputFile);
        }
        if (outputFile.exists()) {
            openFile(outputFile);
        } else {
//            if(filelink.contains(".doc")){
//                filelink = "http://docs.google.com/gview?embedded=true&url=" + filelink;
//            }


            FileDownloadFromURL fileDownloadFromURL = new FileDownloadFromURL(context, download, filelink, new FileDownloadFromURL.FileDownloadListener() {
                @Override
                public void onSuccess(File file) {
                    openFile(file);
                }

                @Override
                public void onFailed(String errorMessage) {
                    ImproveHelper.showToast(context, "Download Failed To View File\n" + errorMessage);
                }
            });
        }


        //ImproveHelper.startFileDownload(context,filelink);
    }


    private void openFile(File file) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);

            //File file = new File(context.getExternalFilesDir(null), filepath);
            String filepath = file.getAbsolutePath();
            Uri uri = FileProvider.getUriForFile(context, "com.bhargo.user.fileprovider", file);
            if (filepath.contains(".doc") || filepath.contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (filepath.contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (filepath.contains(".ppt") || filepath.contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (filepath.contains(".xls") || filepath.contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (filepath.contains(".zip") || filepath.contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav");
            } else if (filepath.contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (filepath.contains(".wav") || filepath.contains(".mp3") || filepath.contains(".WMA") || filepath.contains(".ogg")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (filepath.contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (filepath.contains(".PNG") || filepath.contains(".JPEG") || filepath.contains(".jpg") || filepath.contains(".jpeg") || filepath.contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (filepath.contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (filepath.contains(".3gp") || filepath.contains(".mpg") || filepath.contains(".mpeg") || filepath.contains(".mpe") || filepath.contains(".mp4") || filepath.contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                //if you want you can also define the intent type for any other file

                //additionally use else clause below, to manage other unknown extensions
                //in this case, Android will show all applications installed on the device
                //so you can choose which application to use
                intent.setDataAndType(uri, "*/*");
            }
            context.startActivity(intent);
        } catch (Exception e) {
            ImproveHelper.improveException(context, "TAG", "openFile", e);
            ImproveHelper.showToast(context, "Failed To View File! Please try again...\n" + e);
        }
    }

    private void setControlValues() {
        try {
            if(ll_tap_text != null) {
                ll_tap_text.setTag(controlObject.getControlType());
            }
            tv_displayName.setText(controlObject.getDisplayName());
            if (controlObject.getHint() == null || controlObject.getHint().contentEquals("")) {
                tv_hint.setVisibility(View.GONE);
            } else {
                tv_hint.setVisibility(View.VISIBLE);
                tv_hint.setText(controlObject.getHint());
            }

            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            }
            //layout
            if (controlObject.getFilelink() != null) {
                tv_filelink.setText(controlObject.getFilelink());
                setFileLink(controlObject.getFilelink());
            } else {
                tv_filelink.setText("");
            }

            if (controlObject.isHide_filelink()) {
                tv_filelink.setVisibility(View.GONE);
            }

            if (controlObject.isDownloadFile()) {
                download = true;
            }

            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        }catch (Exception e){
            Log.getStackTraceString(e);
        }
    }

    private void loadFileInWebView(String fileURL) {
        if (fileURL.contains(".pdf") || fileURL.contains(".doc")) {
            webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + fileURL);
        } else {
            webView.loadUrl(fileURL);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);
//        webView.setWebViewClient(new WebViewClient());
    }

    private void showPdfFile(String fileURL) {
        webView.invalidate();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + fileURL);
        webView.setWebViewClient(new WebViewClient() {
            boolean checkOnPageStartedCalled = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                checkOnPageStartedCalled = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (checkOnPageStartedCalled) {
//                    webView.loadUrl(removePdfTopIcon);
                } else {
                    showPdfFile(fileURL);
                }
            }
        });
    }

    public LinearLayout getViewFileLayout() {
        return linearLayout;
    }


    public WebView getViewFileWebView() {
        return webView;
    }

    @Override
    public boolean getVisibility() {
        controlObject.setInvisible(linearLayout.getVisibility() != View.VISIBLE);
        return controlObject.isInvisible();
    }

    @Override
    public void setVisibility(boolean visibility) {

        if (visibility) {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        } else {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }
    }

    @Override
    public boolean isEnabled() {

        return !controlObject.isDisable();
    }

    @Override
    public void setEnabled(boolean enabled) {
//        setViewDisable(rView, !enabled);
        controlObject.setDisable(!enabled);
        setViewDisableOrEnableDefault(context,rView, enabled);
    }

    @Override
    public void clean() {

    }

    public String getFileLink() {
        return tv_filelink.getTag().toString();

    }

    public void setFileLink(String value) {
        String fileName = value.substring(value.lastIndexOf("/") + 1);
        tv_filelink.setText(fileName);
        tv_filelink.setTag(value);
        String fileURL = tv_filelink.getTag().toString().trim();
        if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
            if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6") || controlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {
                if (fileURL.contains(".pdf") || fileURL.contains(".doc")) {
                    showPdfFile(fileURL);
                } else {
                    loadFileInWebView(fileURL);
                }
            }
        }else{
            if (fileURL.contains(".pdf") || fileURL.contains(".doc")) {
                showPdfFile(fileURL);
            } else {
                loadFileInWebView(fileURL);
            }
//            showPdfFile(tv_filelink.getTag().toString().trim());
        }
        controlObject.setText(value);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.webView && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (tv_filelink.getTag().toString().trim().length() == 0) {
                ImproveHelper.showToast(context, "No Link To View File!");
            } else {
                viewFileFromUrl(tv_filelink.getTag().toString().trim());
            }

        }
        return false;
    }

    @Override
    public String getTextSize() {
        return controlObject.getTextSize();
    }

    @Override
    public void setTextSize(String size) {
        if (size != null) {
            controlObject.setTextSize(size);
            tv_displayName.setTextSize(Float.parseFloat(size));
        }

    }

    @Override
    public String getTextStyle() {
        return controlObject.getTextStyle();
    }

    @Override
    public void setTextStyle(String style) {
        if (style != null && style.equalsIgnoreCase("Bold")) {
            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
            tv_displayName.setTypeface(typeface_bold);
            controlObject.setTextStyle(style);
        } else if (style != null && style.equalsIgnoreCase("Italic")) {
            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
            tv_displayName.setTypeface(typeface_italic);
            controlObject.setTextStyle(style);
        }
    }

    @Override
    public String getTextColor() {
        return controlObject.getTextHexColor();
    }

    @Override
    public void setTextColor(String color) {
        if (color != null && !color.equalsIgnoreCase("")) {
            controlObject.setTextHexColor(color);
            controlObject.setTextColor(Color.parseColor(color));
            tv_displayName.setTextColor(Color.parseColor(color));
        }
    }

    /*General*/
    /*displayName,hint,filelink*/

    public String getDisplayName() {
        return controlObject.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        tv_displayName.setText(displayName);
        controlObject.setDisplayName(displayName);
    }

    public String getHint() {
        return controlObject.getHint();
    }

    public void setHint(String hint) {
        if (hint != null && !hint.isEmpty()) {
            tv_hint.setVisibility(View.VISIBLE);
            tv_hint.setText(hint);
        } else {
            tv_hint.setVisibility(View.GONE);
        }
        controlObject.setHint(hint);
    }

    public String getFilelink() {
        return controlObject.getFilelink();
    }

    public void setFilelink(String filelink) {
        controlObject.setFilelink(filelink);
    }

    /*Options*/
    /*hideDisplayName,hide_filelink,downloadFile
     */
    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        if (hideDisplayName) {
            ll_displayName.setVisibility(View.GONE);
        } else {
            ll_displayName.setVisibility(View.VISIBLE);
        }
        controlObject.setHideDisplayName(hideDisplayName);
    }

    public boolean isHide_filelink() {
        return controlObject.isHide_filelink();
    }

    public void setHide_filelink(boolean hide_filelink) {
        if (hide_filelink) {
            tv_filelink.setVisibility(View.GONE);
        } else {
            tv_filelink.setVisibility(View.VISIBLE);
        }
        controlObject.setHide_filelink(hide_filelink);
    }

    public boolean isDownloadFile() {
        return controlObject.isDownloadFile();
    }

    public void setDownloadFile(boolean downloadFile) {
        if (downloadFile) {
            download = true;
        } else {
            download = false;
        }
        controlObject.setDownloadFile(downloadFile);
    }

    public LinearLayout getViewFileMainLayout() {
        return ll_main_inside;
    }
    public CustomTextView getTv_displayName() {
        return tv_displayName;
    }
    public LinearLayout getLl_control_ui() {
        return ll_control_ui;
    }
    public LinearLayout getLl_tap_text() {
        return ll_tap_text;
    }
    public FrameLayout getViewFileFrameLayout() {
        return flWebView;
    }
    public WebView getWebView() {
        return webView;
    }
    public CardView getCv_all() {
        return cv_all;
    }
    public ControlObject getControlObject() {
        return controlObject;
    }
    @Override
    public void showMessageBelowControl(String msg) {
        if(msg != null && !msg.isEmpty()) {
            ct_showText.setVisibility(View.VISIBLE);
            ct_showText.setText(msg);
        }else{
            ct_showText.setVisibility(View.GONE);
        }
    }

}
