package com.bhargo.user.controls.standard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.PathVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.FileDownloadFromURL;
import com.bhargo.user.utils.ImproveHelper;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisable;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.List;

public class FileBrowsingCopy implements PathVariables, UIVariables {
    private static final String TAG = "FileBrowsing";
    private final int iFileBrowserTAG = 0;
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout linearLayout, layout_selected_file,ll_label;
    Context context;
    private ControlObject controlObject;
    CustomTextView ce_TextType;
    ImproveHelper improveHelper;
    private CustomTextView tv_displayName, tv_hint, ct_alertTypeFileBrowser;
    private ImageView iv_mandatory, iv_unselect_file;
    private CustomTextView tv_tapTextType;
    private View view;
    private String strSettingPath;
    private int position;
    private ImageView iv_textTypeImage;
    AlertDialog alertDialog1;
    public FileBrowsingCopy(Context context, ControlObject controlObject) {
        this.context = context;
        this.controlObject = controlObject;
        improveHelper = new ImproveHelper(context);
        initView();

    }

    public void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            addFileBrowserStrip();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initView", e);
        }

    }

    public void addFileBrowserStrip() {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = lInflater.inflate(R.layout.control_text_input, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {

                    view = linflater.inflate(R.layout.control_text_input_hint, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {

                    view = linflater.inflate(R.layout.control_text_input_rectangle, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {

                    view = linflater.inflate(R.layout.control_text_input_rectangle_rounded_corners, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("5")) {

                    view = linflater.inflate(R.layout.control_text_input_left_right, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                    view = linflater.inflate(R.layout.control_file_browser_six, null);
                    ce_TextType = view.findViewById(R.id.ce_TextType);
                    iv_unselect_file = view.findViewById(R.id.iv_unselect_file);
                    layout_selected_file = view.findViewById(R.id.layout_selected_file);
                    iv_unselect_file.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            layout_selected_file.setVisibility(View.GONE);
                        }
                    });
                }
            } else {
                view = linflater.inflate(R.layout.control_text_input, null);
            }
            view.setTag(iFileBrowserTAG);

//        LinearLayout ll_textInput = view.findViewById(R.id.ll_textInput);
            tv_tapTextType = view.findViewById(R.id.tv_tapTextType);
            tv_tapTextType.setText(context.getString(R.string.tap_here_to_select_file));
            tv_tapTextType.setTag(controlObject.getControlName());
            tv_displayName = view.findViewById(R.id.tv_displayName);
            ct_alertTypeFileBrowser = view.findViewById(R.id.ct_alertTypeText);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            ll_label = view.findViewById(R.id.ll_label);

            iv_textTypeImage = view.findViewById(R.id.iv_textTypeImage);
            iv_textTypeImage.setTag(controlObject.getControlName());
//        iv_textTypeImage.setVisibility(View.VISIBLE);
            setIvTapImage(View.VISIBLE);
            iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_folder_open_black_24dp));
//        tv_tapTextType.setVisibility(View.VISIBLE);
            setTvTapText(View.VISIBLE);

            setControlValues();

            linearLayout.addView(view);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "addFileBrowserStrip", e);
        }
    }

    public CustomTextView getTapTextView() {

        return tv_tapTextType;
    }

    public LinearLayout getFileBrowsingView() {
        return linearLayout;
    }

    public void setFileBrowsing(String strPath) {
        try {
            tv_displayName.setTag(strPath);
            iv_textTypeImage.setImageDrawable(context.getDrawable(R.drawable.file));
            iv_textTypeImage.setVisibility(View.VISIBLE);
            int filesize = ImproveHelper.getFileSize(strPath);
            String[] imgUrlSplit = strPath.split("/");
            String strSDCardUrl = imgUrlSplit[imgUrlSplit.length - 1];
            String fileName = strSDCardUrl.replaceAll(" ", "_");

            if (controlObject.isEnableMaxUploadSize() && (filesize > (Integer.parseInt(controlObject.getMaxUploadSize())) * 1000)) {
                Toast.makeText(context, controlObject.getMaxUploadError(), Toast.LENGTH_SHORT).show();
            } else if (controlObject.isEnableMinUploadSize() && (filesize < (Integer.parseInt(controlObject.getMinUploadSize())) * 1000)) {
                Toast.makeText(context, controlObject.getMinUploadError(), Toast.LENGTH_SHORT).show();
            } else {
                if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                    if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                        layout_selected_file.setVisibility(View.VISIBLE);

                        tv_tapTextType.setText(fileName);

                    }ce_TextType.setText(fileName);
                } else {
                    tv_tapTextType.setText(fileName);
                }
                strSettingPath = strPath;
            }
            setPath(strPath);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setFileBrowsing", e);
        }


    }

    public String getFileBrowsingPath() {
        return strSettingPath;
    }

    private void setControlValues() {
        try {
            if (controlObject.getDisplayNameAlignment() != null) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                    tv_tapTextType.setText(context.getString(R.string.tap_here_to_select_file));
                }
            }
            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            if (controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                tv_hint.setVisibility(View.VISIBLE);
                tv_hint.setText(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }
            if (controlObject.isNullAllowed()) {
                iv_mandatory.setVisibility(View.VISIBLE);
            } else {
                iv_mandatory.setVisibility(View.GONE);
            }

            if (controlObject.isHideDisplayName()) {
                ll_label.setVisibility(View.GONE);
            } else {
                ll_label.setVisibility(View.VISIBLE);
            }

            if (controlObject.isInvisible()) {
                linearLayout.setVisibility(View.GONE);
            } else {
                linearLayout.setVisibility(View.VISIBLE);
            }
//            if (controlObject.isEnableExtensions()) {
//                Log.d("FbUserExtensionCheck", controlObject.getFileExtensionError());
//            }
            if (controlObject.isDisable()) {
                setViewDisable(view, false);
            }

            if (controlObject.isEnableMaxUploadSize()) {
                Log.d("ControlFileGET", controlObject.isEnableMaxUploadSize() + "," + controlObject.getMaxUploadSize()
                        + "," + controlObject.getMaxUploadError());
            }
            if (controlObject.isEnableMinUploadSize()) {
                Log.d("ControlFileGET", controlObject.isEnableMinUploadSize() + "," + controlObject.getMinUploadSize()
                        + "," + controlObject.getMinUploadError());
            }
            if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().isEmpty()
                    && !controlObject.getDefaultValue().equalsIgnoreCase("File not found")) {
                setFileBrowsing(controlObject.getDefaultValue());

            }

            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setControlValues", e);
        }

    }

    public CustomTextView setAlertFileBrowser() {

        return ct_alertTypeFileBrowser;
    }

    public CustomTextView getControlRealPath() {
        return tv_displayName;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ImageView getFileBrowseImage() {
        return iv_textTypeImage;
    }

    public void setIvTapImage(int visibility) {
        try {
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
//            tv_tapTextType.setVisibility(visibility);
            } else {
                tv_tapTextType.setVisibility(visibility);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setIvTapImage", e);
        }
    }

    public void setTvTapText(int visibility) {
        try {
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
//            tv_tapTextType.setVisibility(visibility);
            } else {
                tv_tapTextType.setVisibility(visibility);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setTvTapText", e);
        }
    }

    @Override
    public String getPath() {
        return controlObject.getImagePath();
    }

    @Override
    public void setPath(String path) {
        controlObject.setImagePath(path);

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
//        setViewDisable(view, !enabled);
        controlObject.setDisable(!enabled);
        setViewDisableOrEnableDefault(context,view, enabled);
    }

    @Override
    public void clean() {
        if(ce_TextType!=null) {
            ce_TextType.setText("");
        }
        if(layout_selected_file!=null) {
            layout_selected_file.setVisibility(View.VISIBLE);
        }
        if(tv_tapTextType!=null) {
            tv_tapTextType.setVisibility(View.VISIBLE);
            tv_tapTextType.setText(context.getString(R.string.tap_here_to_select_file));
        }
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
    /*displayName,hint*/

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

    /*Validations*/
    /*mandatory,mandatoryErrorMessage,
     enableMaxUploadSize,maxUploadSize,maxUploadError,enableMinUploadSize,minUploadSize,minUploadError
     enableExtensions,enabledExtensions,fileExtensionError */
    public boolean isMandatory() {
        return controlObject.isNullAllowed();
    }

    public void setMandatory(boolean mandatory) {
        iv_mandatory.setVisibility(mandatory ? View.VISIBLE : View.GONE);
        controlObject.setNullAllowed(mandatory);
    }

    public String getMandatoryErrorMessage() {
        return controlObject.getMandatoryFieldError();
    }

    public void setMandatoryErrorMessage(String mandatoryErrorMessage) {
        controlObject.setMandatoryFieldError(mandatoryErrorMessage);
    }

    public boolean isEnableMaxUploadSize() {
        return controlObject.isEnableMaxUploadSize();
    }

    public void setEnableMaxUploadSize(boolean enableMaxUploadSize) {
        controlObject.setEnableMaxUploadSize(enableMaxUploadSize);
    }

    public String getMaxUploadSize() {
        return controlObject.getMaxUploadSize();
    }

    public void setMaxUploadSize(String maxUploadSize) {
        controlObject.setMaxUploadSize(maxUploadSize);
    }

    public String getMaxUploadError() {
        return controlObject.getMaxUploadError();
    }

    public void setMaxUploadError(String maxUploadError) {
        controlObject.setMaxUploadError(maxUploadError);
    }

    public boolean isEnableMinUploadSize() {
        return controlObject.isEnableMinUploadSize();
    }

    public void setEnableMinUploadSize(boolean enableMinUploadSize) {
        controlObject.setEnableMinUploadSize(enableMinUploadSize);
    }

    public String getMinUploadSize() {
        return controlObject.getMinUploadSize();
    }

    public void setMinUploadSize(String minUploadSize) {
        controlObject.setMinUploadSize(minUploadSize);
    }

    public String getMinUploadError() {
        return controlObject.getMinUploadError();
    }

    public void setMinUploadError(String minUploadError) {
        controlObject.setMinUploadError(minUploadError);
    }

    public boolean isEnableExtensions() {
        return controlObject.isEnableExtensions();
    }

    public void setEnableExtensions(boolean enableExtensions) {
        controlObject.setEnableExtensions(enableExtensions);
    }

    public List<String> getEnabledExtensions() {
        return controlObject.getEnabledExtensions();
    }

    public void setEnabledExtensions(List<String> enabledExtensions) {
        controlObject.setExtensionsListNames(enabledExtensions);
        clean();

    }

    public String getFileExtensionError() {
        return controlObject.getFileExtensionError();
    }

    public void setFileExtensionError(String fileExtensionError) {
        controlObject.setFileExtensionError(fileExtensionError);
    }

    /*Options*/
    /*hideDisplayName,enableScan
    invisible/visibility,disable/enabled
     */

    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        ll_label.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
    }

    public boolean isEnableScan() {
        return controlObject.isEnableScan();
    }

    public void setEnableScan(boolean enableScan) {
        controlObject.setEnableScan(enableScan);
    }

    public void onClick(View v,int FILE_BROWSER_RESULT_CODE){

        if(controlObject.isOnChangeEventExists()){
            if (AppConstants.EventCallsFrom == 1) {
                AppConstants.GlobalObjects.setCurrent_GPS("");
                ((MainActivity)context).ChangeEvent(v);
            }
        }
        ct_alertTypeFileBrowser.setVisibility(View.GONE);
        ct_alertTypeFileBrowser.setText("");
        if (controlObject.isEnableScan()) {
            ((MainActivity)context).openDocumentScanner();
        }else{

            if((tv_displayName.getTag()!=null)){
            selectionAlertDialog(controlObject,FILE_BROWSER_RESULT_CODE);
            }else{
                            ((MainActivity)context).showFileChooser(controlObject,FILE_BROWSER_RESULT_CODE);
            }
        }

    }

    public void selectionAlertDialog(ControlObject controlObject,int FILE_BROWSER_RESULT_CODE){
        String[] values = {" Select File "," Open File "," Delete File "};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                context, R.layout.custom_text_view, values);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Your Choice");
        builder.setSingleChoiceItems(adapter,-1,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item)
                {
                    case 0:
                        ((MainActivity)context).showFileChooser(controlObject,FILE_BROWSER_RESULT_CODE);
                        break;
                    case 1:
                        openFile();
                        break;
                    case 2:
                        unselectFile();
                        break;

                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

    }

    private void unselectFile() {
        tv_displayName.setTag(null);
        iv_textTypeImage.setVisibility(View.GONE);
        tv_tapTextType.setText(context.getString(R.string.tap_here_to_select_file));
    }

    public void openFile() {
     String fileURL = tv_displayName.getTag().toString();
     if(fileURL.startsWith("http")){
         FileDownloadFromURL fileDownloadFromURL = new FileDownloadFromURL(context, false, fileURL, new FileDownloadFromURL.FileDownloadListener() {
             @Override
             public void onSuccess(File file) {
                 String filepath = file.getAbsolutePath();
                 improveHelper.openFile(filepath,context);}

             @Override
             public void onFailed(String errorMessage) {
                 ImproveHelper.showToast(context, "Download Failed To View File\n" + errorMessage);
             }
         });
     }else{
        improveHelper.openFile(fileURL,context);}

        }

    @Override
    public void showMessageBelowControl(String msg) {
        if(msg != null && !msg.isEmpty()) {
//            ct_showText.setVisibility(View.VISIBLE);
//            ct_showText.setText(msg);
        }else{
//            ct_showText.setVisibility(View.GONE);
        }
    }

}


