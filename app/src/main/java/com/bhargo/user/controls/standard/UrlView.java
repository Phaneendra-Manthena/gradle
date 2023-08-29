package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisable;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.controls.variables.UrlLinkVariables;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.google.android.material.textfield.TextInputLayout;

public class UrlView implements UrlLinkVariables, UIVariables {

    private static final String TAG = "URLView";
    private final int URLViewTAG = 0;
    Context context;
    CustomEditText customEditText;
    ControlObject controlObject;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    View view;
    ImproveHelper improveHelper;
    private LinearLayout linearLayout,ll_displayName,ll_main_inside,ll_control_ui,layout_control,ll_tap_text,ll_leftRightWeight;
    private CustomTextView tv_tapTextType, ct_TextType;
    private CustomEditText ce_TextType;
    private CustomTextView tv_displayName, tv_hint,ct_showText;
    private ImageView iv_textTypeImage, iv_mandatory;
    private CustomTextView ct_alertURLView;
    private String url;
    TextInputLayout til_task_name;

    public UrlView(Context context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
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
            addURLViewStrip(context);

//        TextView textView = new TextView(context);
//        textView.setText("URL");
//        customEditText = new CustomEditText(context);
//        customEditText.setInputType(InputType.TYPE_CLASS_TEXT);
//        customEditText.setHint("Enter URL");
//        //        textView.setText(controlObject.getDisplayName());
//        //        customEditText.setTextSize(Float.parseFloat(controlObject.getTextSize()));
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.addView(textView);
//        linearLayout.addView(customEditText);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getURL() {

        return linearLayout;
    }

    public void addURLViewStrip(final Context context) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View view = lInflater.inflate(R.layout.control_url, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {

                    view = linflater.inflate(R.layout.layout_url_place_holder, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {

                    view = linflater.inflate(R.layout.layout_url_rectangle, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {

                    view = linflater.inflate(R.layout.layout_url_rounded_rectangle, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("5")) {

                    view = linflater.inflate(R.layout.layout_url_left_right, null);
                }
            } else {

//                view = linflater.inflate(R.layout.control_url, null);
                view = linflater.inflate(R.layout.layout_url_default, null);
            }
            view.setTag(URLViewTAG);

            ll_tap_text = view.findViewById(R.id.ll_tap_text);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            layout_control = view.findViewById(R.id.layout_control);
            ll_leftRightWeight = view.findViewById(R.id.ll_leftRightWeight);
            tv_tapTextType = view.findViewById(R.id.tv_tapTextType);
            iv_textTypeImage = view.findViewById(R.id.iv_textTypeImage);
            ce_TextType = view.findViewById(R.id.ce_TextType);
            tv_tapTextType.setTag(controlObject.getControlName());
            til_task_name = view.findViewById(R.id.til_task_name);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            ct_alertURLView = view.findViewById(R.id.ct_alertTypeText);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            ct_showText = view.findViewById(R.id.ct_showText);

            ll_tap_text.setVisibility(View.VISIBLE);
            tv_tapTextType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openURL();

                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                        if (AppConstants.EventCallsFrom == 1) {
                            AppConstants.EventFrom_subformOrNot = SubformFlag;
                            if (SubformFlag) {
                                AppConstants.SF_Container_position = SubformPos;
                                AppConstants.Current_ClickorChangeTagName = SubformName;

                            }
                            AppConstants.GlobalObjects.setCurrent_GPS("");
                            ((MainActivity) context).ChangeEvent(v);
                        }
                    }
                }
            });
            ce_TextType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openURL();

                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                        if (AppConstants.EventCallsFrom == 1) {
                            AppConstants.EventFrom_subformOrNot = SubformFlag;
                            if (SubformFlag) {
                                AppConstants.SF_Container_position = SubformPos;
                                AppConstants.Current_ClickorChangeTagName = SubformName;

                            }
                            AppConstants.GlobalObjects.setCurrent_GPS("");
                            ((MainActivity) context).ChangeEvent(v);
                        }
                    }
                }
            });


            setControlValues();

            linearLayout.addView(view);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addURLViewStrip", e);
        }

    }

    private void openURL() {
        try {
            if (url != null && !url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(browserIntent);
            } else {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(browserIntent);
            }
            controlObject.setText(url);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "openURL", e);
        }
    }


    private void setControlValues() {
        try {
            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            if (controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                tv_hint.setText(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }
            if (controlObject.isNullAllowed()) {
                iv_mandatory.setVisibility(View.VISIBLE);
            } else {
                iv_mandatory.setVisibility(View.GONE);
            }

            if(controlObject.isHideDisplayName()){
                ll_displayName.setVisibility(View.GONE);
            }


            if (controlObject.getUrl() != null && !controlObject.getUrl().isEmpty() && !controlObject.isDisableClick()) {
                url = controlObject.getUrl();
                tv_tapTextType.setText(controlObject.getUrl());
                controlObject.setText(getURLViewStringValue());
                if (controlObject.isHideURL() && controlObject.getUrlPlaceholderText() != null) {
                    tv_tapTextType.setText(controlObject.getUrlPlaceholderText());
                    controlObject.setText(url);
                }

                tv_tapTextType.setTextColor(Color.BLUE);

//            url = "http://www.google.com";
//            tv_tapTextType.setText(url);
                if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                    if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {
                        TextInputLayout til_TextType = view.findViewById(R.id.til_TextType);
                        til_TextType.setHint(controlObject.getDisplayName());
                        ce_TextType.setText(controlObject.getUrl());
                        controlObject.setText(getURLViewStringValue());
                        }
                        iv_textTypeImage.setVisibility(View.GONE);
                    }
/*
                    if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {
                        tv_displayName.setVisibility(View.GONE);
                        ce_TextType.setText(controlObject.getUrl());
                        ce_TextType.setTextColor(ContextCompat.getColor(context,R.color.control_radio_button_selected));
                        if(til_task_name != null) {
                            til_task_name.setHint(controlObject.getDisplayName());
                        }
                    }

                }
*/
            } else if (controlObject.getUrl() != null && !controlObject.getUrl().isEmpty() && controlObject.isDisableClick()) {
                if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                    if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {
                        TextInputLayout til_TextType = view.findViewById(R.id.til_TextType);
                        til_TextType.setHint(controlObject.getDisplayName());
                        ce_TextType.setText(controlObject.getUrl());
                        controlObject.setText(getURLViewStringValue());
                    }
                }
                tv_tapTextType.setText(controlObject.getUrl());
                controlObject.setText(getURLViewStringValue());
                setViewDisable(tv_tapTextType, false);
            } else {
//                iv_textTypeImage.setVisibility(View.VISIBLE);
                tv_tapTextType.setText("No URL");
                //setViewDisable(tv_tapTextType, false);
            }


            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    public String getURLViewStringValue() {
        String strUrl = "";
        if(tv_tapTextType.getVisibility()  == View.VISIBLE) {
            Log.d(TAG, "getURLViewStringValue: " + tv_tapTextType.getText().toString());
            strUrl = tv_tapTextType.getText().toString();
        }else if(ce_TextType.getVisibility() == View.VISIBLE){
            strUrl = ce_TextType.getText().toString();
        }
        return strUrl;
    }

    public CustomTextView setAlertURLView() {

        return ct_alertURLView;
    }

    public void setText(String value) {

        controlObject.setUrl(value);
        controlObject.setText(value);
        setControlValues();
        tv_tapTextType.setEnabled(true);
        ce_TextType.setEnabled(true);

        tv_tapTextType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openURL();

                if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                        if (SubformFlag) {
                            AppConstants.SF_Container_position = SubformPos;
                            AppConstants.Current_ClickorChangeTagName = SubformName;

                        }
                        AppConstants.GlobalObjects.setCurrent_GPS("");
                        ((MainActivity) context).ChangeEvent(v);
                    }
                }
            }
        });
        ce_TextType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openURL();

                if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                        if (SubformFlag) {
                            AppConstants.SF_Container_position = SubformPos;
                            AppConstants.Current_ClickorChangeTagName = SubformName;

                        }
                        AppConstants.GlobalObjects.setCurrent_GPS("");
                        ((MainActivity) context).ChangeEvent(v);
                    }
                }
            }
        });

    }

    @Override
    public String getURLLink() {
        return controlObject.getUrl();
    }

    @Override
    public void setURLLink(String urllink) {
        setText(urllink);

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
//        setViewDisableOrEnableDefault(context,view, enabled);
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),enabled,ll_tap_text,view);
    }

    @Override
    public void clean() {

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
    /*displayName,hint,url*/

    public String getDisplayName() {
        return controlObject.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        tv_displayName.setText(displayName);
        ce_TextType.setHint(displayName);
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

    public String getUrl() {
        return controlObject.getUrl();
    }

    public void setUrl(String url) {
        controlObject.setUrl(url);
    }

    /*Options*/
    /*hideDisplayName,disableClick,hideURL
    invisible/visibility,
     */
    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        if(hideDisplayName) {
            ll_displayName.setVisibility(View.GONE);
        }
    }

    public boolean isDisableClick() {
        return controlObject.isDisableClick();
    }

    public void setDisableClick(boolean disableClick) {
        controlObject.setDisableClick(disableClick);
        if (controlObject.getUrl() != null && !controlObject.getUrl().isEmpty() && controlObject.isDisableClick()) {
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {
                    if(til_task_name != null) {
                        til_task_name.setHint(controlObject.getDisplayName());
                    }
                    ce_TextType.setText(controlObject.getUrl());
                    controlObject.setText(getURLViewStringValue());
                }
            }
            tv_tapTextType.setText(controlObject.getUrl());
            controlObject.setText(getURLViewStringValue());
            if(disableClick) {
                setViewDisable(tv_tapTextType, false);
            }
        } else {
            tv_tapTextType.setText("No URL");
        }
    }

    public boolean isHideURL() {
        return controlObject.isHideURL();
    }

    public void setHideURL(boolean hideURL) {
        controlObject.setHideURL(hideURL);
        if (hideURL)
            tv_tapTextType.setText(controlObject.getUrlPlaceholderText());
        controlObject.setText(getURLViewStringValue());
    }

    public String getUrlPlaceholderText() {
        return controlObject.getUrlPlaceholderText();
    }

    public void setUrlPlaceholderText(String urlPlaceholderText) {
        controlObject.setUrlPlaceholderText(urlPlaceholderText);
        /*if (controlObject.isHideURL() && controlObject.getUrlPlaceholderText() != null) {
            tv_tapTextType.setText(controlObject.getUrlPlaceholderText());
            controlObject.setText(url);
        }*/
        if (controlObject.isHideURL() && controlObject.getUrlPlaceholderText() != null) {
            ce_TextType.setVisibility(View.GONE);
            tv_tapTextType.setVisibility(View.VISIBLE);
            tv_tapTextType.setText(controlObject.getUrlPlaceholderText());
            controlObject.setText(getURLViewStringValue());
        }else{
            tv_tapTextType.setVisibility(View.GONE);
        }
    }

    /*ControlUI SettingsLayouts Start */
    public LinearLayout getLl_main_inside() {
        return ll_main_inside;
    }
    public LinearLayout getLl_control_ui() {
        return ll_control_ui;
    }
    public LinearLayout getLayout_control() {
        return layout_control;
    }
    public LinearLayout getLl_leftRightWeight() {
        return ll_leftRightWeight;
    }
    public LinearLayout getLl_displayName() {
        return ll_displayName;
    }
    public LinearLayout getLl_tap_text() {
        return ll_tap_text;
    }

    public CustomTextView getTv_displayName() {
        return tv_displayName;
    }
    public CustomTextView getTv_tapTextType() {
        return tv_tapTextType;
    }
    public CustomEditText getCe_TextType() {
        return ce_TextType;
    }

    /*ControlUI SettingsLayouts End*/

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
