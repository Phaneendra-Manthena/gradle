package com.bhargo.user.controls.standard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.TextVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;
import static com.bhargo.user.utils.ImproveHelper.showSoftKeyBoard;

import androidx.core.content.ContextCompat;

public class Phone implements TextVariables, UIVariables {

    private static final String TAG = "Phone";
    Activity context;
    ControlObject controlObject;
    CustomEditText customEditText, ce_TextType;
    LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    Typeface typeface;
    LinearLayout ll_main_inside,ll_tap_text,ll_label,ll_displayName,ll_control_ui,layout_control;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    ImproveHelper improveHelper;
    private LinearLayout linearLayout;
    private CustomTextView tv_displayName, tv_hint, tv_tapTextType, ct_alertTypePhone,ct_showText;
    private ImageView iv_mandatory, iv_textTypeImage;
    private IntentIntegrator qr_bar_Scanner;
    private View rView;

    public Phone(Activity context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        typeface = Typeface.createFromAsset(context.getAssets(), "Poppins-Regular.otf");
        improveHelper = new ImproveHelper(context);
        initView();

    }

    public void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(layout_params);

            qr_bar_Scanner = new IntentIntegrator(context);

            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        rView = linflater.inflate(R.layout.control_text_input, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {

                    rView = linflater.inflate(R.layout.layout_text_input_place_holder, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {

                    rView = linflater.inflate(R.layout.layout_text_input_default, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {

                    rView = linflater.inflate(R.layout.layout_text_input_rounded_rectangle, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("4")) {

                    rView = linflater.inflate(R.layout.layout_text_input_rounded_rectangle, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("5")) {

                    rView = linflater.inflate(R.layout.layout_text_input_left_right, null);

                } else {

                    rView = linflater.inflate(R.layout.layout_text_input_default, null);

                } } else {

                rView = linflater.inflate(R.layout.layout_text_input_default, null);
            }

            ll_main_inside = rView.findViewById(R.id.ll_main_inside);
            ll_tap_text = rView.findViewById(R.id.ll_tap_text);
            ll_control_ui = rView.findViewById(R.id.ll_control_ui);
            layout_control = rView.findViewById(R.id.layout_control);
            tv_displayName = rView.findViewById(R.id.tv_displayName);
            tv_hint = rView.findViewById(R.id.tv_hint);
            iv_mandatory = rView.findViewById(R.id.iv_mandatory);
            iv_textTypeImage = rView.findViewById(R.id.iv_textTypeImage);
            tv_tapTextType = rView.findViewById(R.id.tv_tapTextType);
            ct_alertTypePhone = rView.findViewById(R.id.ct_alertTypeText);
            ce_TextType = rView.findViewById(R.id.ce_TextType);
            ct_showText = rView.findViewById(R.id.ct_showText);
            ce_TextType.setTag(controlObject.getControlName());
            ll_label = rView.findViewById(R.id.ll_label);
            ll_displayName = rView.findViewById(R.id.ll_displayName);
            ce_TextType.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
            tv_tapTextType.setTag(controlObject.getControlName());
            iv_textTypeImage.setTag(controlObject.getControlName());

            setTvTapText(View.VISIBLE);
            ce_TextType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus) {
                        if(ll_tap_text.isEnabled()){
                            improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),hasFocus,ll_tap_text,rView);
                        }
//                        ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_default_background));}
                        if (ce_TextType.getText().toString().isEmpty()) {
                            ll_tap_text.setVisibility(View.VISIBLE);
                            ce_TextType.setVisibility(View.VISIBLE);
                            if (controlObject.isGridControl()) {
                                tv_tapTextType.setVisibility(View.GONE);
                            }

                        }
                        if (controlObject.isOnFocusEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                if (SubformFlag) {
                                    AppConstants.SF_Container_position = SubformPos;
                                    AppConstants.Current_ClickorChangeTagName = SubformName;

                                }
                                AppConstants.GlobalObjects.setCurrent_GPS("");
                                ((MainActivity) context).FocusExist(v);
                            }
                        }
                        ce_TextType.setVisibility(View.VISIBLE);
                    }else {
                        if(ll_tap_text.isEnabled()){
                            improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),hasFocus,ll_tap_text,rView);
//                       ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_active_background));
                    }}
                }
            });

            /*ce_TextType.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence cs, int i, int i1, int i2) {

                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {

                        if (ce_TextType.getText().toString().trim().length() > 0) {
                            if (AppConstants.EventCallsFrom == 1) {
                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                if (SubformFlag) {
                                    AppConstants.SF_Container_position = SubformPos;
                                    AppConstants.Current_ClickorChangeTagName = SubformName;

                                }
                                AppConstants.GlobalObjects.setCurrent_GPS("");
                                ((MainActivity) context).TextChange(ce_TextType);
                            }
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (ce_TextType.getText().toString().trim().length() > 0) {
                        ct_alertTypePhone.setText("");
                        ct_alertTypePhone.setVisibility(View.GONE);
                    }
                }
            });*/

            tv_tapTextType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setfocus();

                }
            });
            setfocus();
            setControlValues(rView);

            ce_TextType.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence cs, int i, int i1, int i2) {
                    if (cs.length() > 0) {
                        controlObject.setText("" + cs);
                    } else {
                        controlObject.setText(null);
                    }
                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {

                        if (ce_TextType.getText().toString().trim().length() > 0) {
                            if (AppConstants.EventCallsFrom == 1) {
                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                if (SubformFlag) {
                                    AppConstants.SF_Container_position = SubformPos;
                                    AppConstants.Current_ClickorChangeTagName = SubformName;

                                }
                                AppConstants.GlobalObjects.setCurrent_GPS("");
                                ((MainActivity) context).TextChange(ce_TextType);
                            }
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (ce_TextType.getText().toString().trim().length() > 0) {
                        ct_alertTypePhone.setText("");
                        ct_alertTypePhone.setVisibility(View.GONE);
                        if( !(ce_TextType.getText().toString().trim().startsWith("6")||
                                ce_TextType.getText().toString().trim().startsWith("7")||
                                ce_TextType.getText().toString().trim().startsWith("8")||ce_TextType.getText().toString().trim().startsWith("9"))){
                            ce_TextType.getText().clear();
                        }
                    }
                }
            });
            linearLayout.addView(rView);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public void setfocus() {
        try {
            tv_tapTextType.setVisibility(View.GONE);
            ce_TextType.setVisibility(View.VISIBLE);

            showSoftKeyBoard(context, ce_TextType);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setfocus", e);
        }
    }

    public CustomEditText getCustomEditText() {

        return ce_TextType;
    }

    public LinearLayout getPhoneView() {
        return linearLayout;
    }

    private void setControlValues(View rView) {
        try {
            ll_tap_text.setTag(controlObject.getControlName());
            ll_control_ui.setTag(controlObject.getControlName());
            layout_control.setTag(controlObject.getControlName());
            if (controlObject.getDisplayNameAlignment() != null) {
                iv_textTypeImage.setVisibility(View.VISIBLE);
                iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.icons_phone_drawable));
//                ce_TextType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_phone_24, 0);
                tv_tapTextType.setVisibility(View.GONE);
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {
                    TextInputLayout til_TextType = rView.findViewById(R.id.til_TextType);

                    til_TextType.setHint(controlObject.getDisplayName());
                    tv_hint.setVisibility(View.GONE);
                    ce_TextType.setText("");
                    setTextValue("");
                    tv_displayName.setVisibility(View.GONE);
                    iv_textTypeImage.setVisibility(View.GONE);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {

//                ce_TextType.setHint(controlObject.getDisplayName());


                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {

//                    ce_TextType.setText(controlObject.getDisplayName());
//                ce_TextType.setHint(controlObject.getDisplayName());

                }
            }


            if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().isEmpty()) {

                tv_tapTextType.setVisibility(View.GONE);
                iv_textTypeImage.setVisibility(View.VISIBLE);
                ce_TextType.setVisibility(View.VISIBLE);
                ce_TextType.setText(controlObject.getDefaultValue());
                setTextValue(controlObject.getDefaultValue());
            }

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
            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            } else {
                ll_displayName.setVisibility(View.VISIBLE);
            }
            if (controlObject.isEnableUpperLimit()) {
                Log.d("NumericValidators", controlObject.isEnableUpperLimit() + "-" +
                        controlObject.getUpperLimit() + "-" + controlObject.getUpperLimitErrorMesage());
            }
            if (controlObject.isEnableLowerLimit()) {
                Log.d("NumericValidators", controlObject.isEnableLowerLimit() + "-" +
                        controlObject.getLowerLimit() + "-" + controlObject.getLowerLimitErrorMesage());
            }

            if (controlObject.isEnableCappingDigits()) {
                Log.d("NumericValidators", controlObject.isEnableCappingDigits() + "-" +
                        controlObject.getCappingDigits() + "-" + controlObject.getCappingError());
            }
            if (controlObject.isReadFromBarcode() && controlObject.getDefaultValue() != null) {
                tv_tapTextType.setVisibility(View.GONE);
                ce_TextType.setVisibility(View.VISIBLE);
                iv_textTypeImage.setVisibility(View.VISIBLE);
                iv_textTypeImage.setVisibility(View.VISIBLE);
                iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_icon_bar_code));
                iv_textTypeImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!SubformFlag){
                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();}
                        qr_bar_Scanner.initiateScan();
                    }
                });
            } else if (controlObject.isReadFromQRCode() && controlObject.getDefaultValue() != null) {
                tv_tapTextType.setVisibility(View.GONE);
                ce_TextType.setVisibility(View.VISIBLE);
                iv_textTypeImage.setVisibility(View.VISIBLE);
                iv_textTypeImage.setVisibility(View.VISIBLE);
                iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_icon_qr_code));
                iv_textTypeImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!SubformFlag){
                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();}
                        qr_bar_Scanner.initiateScan();
                    }
                });
            } else if (controlObject.isReadFromBarcode()) {
                tv_tapTextType.setText(R.string.tap_to_scan_bar_code);
                iv_textTypeImage.setVisibility(View.VISIBLE);
                iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_icon_bar_code));
                tv_tapTextType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!SubformFlag){
                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();}
                        qr_bar_Scanner.initiateScan();
                    }
                });
            } else if (controlObject.isReadFromQRCode()) {
                tv_tapTextType.setText(R.string.tap_to_scan_qr_code);
                iv_textTypeImage.setVisibility(View.VISIBLE);
                iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_icon_qr_code));
                tv_tapTextType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!SubformFlag){
                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();}
                        qr_bar_Scanner.initiateScan();
                    }
                });
            }
            if(controlObject.isEnableCappingDigits()){
                ce_TextType.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.parseInt(controlObject.getCappingDigits()))});
            }
            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
            if (controlObject.isDisable()) {

//                setViewDisable(rView, false);
//                setViewDisableOrEnableDefault(context,ll_main_inside, false);
                improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),!controlObject.isDisable(),ll_tap_text,rView);
            }

        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }



    public String getPhoneValue() {

        return ce_TextType.getText().toString();
    }

    public CustomTextView setAlertPhone() {

        return ct_alertTypePhone;
    }


    public CustomTextView gettap_text() {

        return tv_tapTextType;
    }

    public void Clear() {
        try {
            ce_TextType.setText("");
            setTextValue("");
//            tv_tapTextType.setVisibility(View.VISIBLE);
            setTvTapText(View.VISIBLE);
            ce_TextType.setVisibility(View.GONE);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "Clear", e);
        }
    }
    public void setTvTapText(int visibility) {
        try {
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                tv_tapTextType.setVisibility(View.VISIBLE);
                ll_tap_text.setVisibility(View.VISIBLE);
            } else {
                ll_tap_text.setVisibility(View.VISIBLE);
                tv_tapTextType.setVisibility(visibility);
                tv_tapTextType.setText(R.string.tap_to_enter_phone);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setTvTapText", e);
        }
    }

    @Override
    public String getTextValue() {

        controlObject.setText(ce_TextType.getText().toString());
        return ce_TextType.getText().toString();

    }

    @Override
    public void setTextValue(String value) {
        ce_TextType.setText(value);
        controlObject.setText(value);
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

        return controlObject.isDisable();
    }

    @Override
    public void setEnabled(boolean enabled) {
//        setViewDisable(rView, !enabled);
        controlObject.setDisable(!enabled);
//        setViewDisableOrEnableDefault(context,rView, enabled);
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),enabled,ll_tap_text,rView);
    }

    @Override
    public void clean() {
        Clear();
    }

    @Override
    public void enableHTMLEditor(boolean enabled) {

    }

    @Override
    public void enableHTMLViewer(boolean enabled) {

    }

    @Override
    public boolean isHTMLViewerEnabled() {
        return false;
    }

    @Override
    public boolean isHTMLEditorEnabled() {
        return false;
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
    /*displayName,hint,defaultValue*/

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

    public String getDefaultValue() {
        return controlObject.getDefaultValue();
    }

    public void setDefaultValue(String defaultValue) {
        controlObject.setDefaultValue(defaultValue);
        setTextValue(defaultValue);
    }

    /*Validations*/
    /*mandatory,mandatoryErrorMessage,uniqueField,uniqueFieldErrorMessage,cappingDigits,cappingDigitsValue*/
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

    public boolean isUniqueField() {
        return controlObject.isUniqueField();
    }

    public void setUniqueField(boolean uniqueField) {
        controlObject.setUniqueField(uniqueField);
    }

    public String getUniqueFieldErrorMessage() {
        return controlObject.getUniqueFieldError();
    }

    public void setUniqueFieldErrorMessage(String uniqueFieldErrorMessage) {
        controlObject.setUniqueFieldError(uniqueFieldErrorMessage);
    }

    public boolean isEnableCappingDigits() {
        return controlObject.isEnableCappingDigits();
    }

    public void setEnableCappingDigits(boolean enableCappingDigits) {
        controlObject.setEnableCappingDigits(enableCappingDigits);
    }

    public String getCappingError() {
        return controlObject.getCappingError();
    }

    public void setCappingError(String cappingError) {
        controlObject.setCappingError(cappingError);
    }

    /*Options*/
    /*hideDisplayName,readFromBarCode,readFromQRCode,
    invisible/visibility,disable/enabled
     */
    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        ll_displayName.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
    }

    public boolean isReadFromBarcode() {
        return controlObject.isReadFromBarcode();
    }

    public void setReadFromBarcode(boolean readFromBarcode) {
        if (readFromBarcode) {
            tv_tapTextType.setVisibility(View.VISIBLE);
            tv_tapTextType.setText(R.string.tap_to_scan_bar_code);
            iv_textTypeImage.setVisibility(View.VISIBLE);
            iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_icon_bar_code));
            iv_textTypeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SubformFlag) {
                        AppConstants.SF_Container_position = SubformPos;
                        AppConstants.Current_ClickorChangeTagName = SubformName;
                    }else{
                        AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                    }
                    qr_bar_Scanner.initiateScan();
                }
            });
            tv_tapTextType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SubformFlag) {
                        AppConstants.SF_Container_position = SubformPos;
                        AppConstants.Current_ClickorChangeTagName = SubformName;
                    }else{
                        AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                    }
                    qr_bar_Scanner.initiateScan();
                }
            });
        } else {
            tv_tapTextType.setVisibility(View.GONE);
            iv_textTypeImage.setVisibility(View.GONE);
        }

        controlObject.setReadFromBarcode(readFromBarcode);
    }

    public boolean isReadFromQRCode() {
        return controlObject.isReadFromQRCode();
    }

    public void setReadFromQRCode(boolean readFromQRCode) {
        if (readFromQRCode) {
            tv_tapTextType.setVisibility(View.VISIBLE);
            iv_textTypeImage.setVisibility(View.VISIBLE);
            tv_tapTextType.setText(R.string.tap_to_scan_qr_code);
            iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_icon_qr_code));
            iv_textTypeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SubformFlag) {
                        AppConstants.SF_Container_position = SubformPos;
                        AppConstants.Current_ClickorChangeTagName = SubformName;
                    }else{
                        AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                    }
                    qr_bar_Scanner.initiateScan();
                }
            });


            tv_tapTextType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SubformFlag) {
                        AppConstants.SF_Container_position = SubformPos;
                        AppConstants.Current_ClickorChangeTagName = SubformName;
                    }else{
                        AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                    }
                    qr_bar_Scanner.initiateScan();
                }
            });
        } else {
            tv_tapTextType.setVisibility(View.GONE);
            iv_textTypeImage.setVisibility(View.GONE);
        }
        controlObject.setReadFromQRCode(readFromQRCode);
    }
    public LinearLayout getLl_tap_text() { // linear for taptext

        return ll_tap_text;
    }

    public ImageView getIv_textTypeImage() { // ImageView for icon

        return iv_textTypeImage;
    }
    public void setQRorBartext(String text) {
        try {
//        tv_tapTextType.setVisibility(View.VISIBLE);
            setTvTapText(View.VISIBLE);
            iv_textTypeImage.setVisibility(View.VISIBLE);
            tv_tapTextType.setText(text);
            ce_TextType.setText(text);
            setTextValue(text);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setQRorBartext", e);
        }
    }




    /*Read QR and Bar Code*/
    public void tQRBarCode() {
        qr_bar_Scanner.initiateScan();
    }

    /*ControlUI SettingsLayouts Start */
    public LinearLayout getLl_main_inside(){
        return ll_main_inside;
    }
    public LinearLayout getLl_control_ui() {
        return ll_control_ui;
    }
    public LinearLayout getLayout_control() {
        return layout_control;
    }

    public CustomTextView getTv_displayName() {
        return tv_displayName;
    }





    /*ControlUI SettingsLayouts End*/

    public ControlObject getControlObject(){
        return controlObject;
    }
    public void requestFocus(){
        ce_TextType.requestFocus();
    }
}
