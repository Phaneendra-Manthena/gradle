package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;
import static com.bhargo.user.utils.ImproveHelper.showSoftKeyBoard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

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

public class Password implements TextVariables, UIVariables {

    private static final String TAG = "Password";
    private final int PasswordTAG = 0;
    Context context;
    ControlObject controlObject;
    TextInputLayout til_password;
    CustomEditText tie_password;
    LinearLayout ll_tap_text;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    View view;
    ImproveHelper improveHelper;
    private LinearLayout linearLayout, ll_main_inside,ll_displayName,ll_control_ui,layout_control,ll_leftRightWeight;
    private CustomTextView tv_tapTextType, ct_alertPassword;
    private CustomTextView tv_displayName, tv_hint,ct_showText;
    private ImageView iv_mandatory, iv_textTypeImage;

    public Password(Context context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
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

            addPasswordStrip(context);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getPassword() {

        return linearLayout;
    }

    public void addPasswordStrip(final Context context) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = linflater.inflate(R.layout.control_password, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {

                    view = linflater.inflate(R.layout.layout_password_place_holder, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {

                    view = linflater.inflate(R.layout.layout_password_default, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {

                    view = linflater.inflate(R.layout.layout_password_rounded_rectangle, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("4")) {

                    view = linflater.inflate(R.layout.layout_password_rounded_rectangle, null);
                }  else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("5")) {

                    view = linflater.inflate(R.layout.layout_password_left_right, null);
                } else {
                    view = linflater.inflate(R.layout.layout_password_default, null);
                }
            } else {
                view = linflater.inflate(R.layout.layout_password_default, null);
            }

            view.setTag(PasswordTAG);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            layout_control = view.findViewById(R.id.layout_control);
            ll_leftRightWeight = view.findViewById(R.id.ll_leftRightWeight);
            ll_tap_text = view.findViewById(R.id.ll_tap_text);
            tv_tapTextType = view.findViewById(R.id.tv_tapTextType);
            ct_alertPassword = view.findViewById(R.id.ct_alertTypeText);
            tv_tapTextType.setText(R.string.tap_enter_password);

            ll_displayName = view.findViewById(R.id.ll_displayName);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);

            iv_textTypeImage = view.findViewById(R.id.iv_textTypeImage);
//            iv_textTypeImage.setVisibility(View.GONE);
            iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_password));

            til_password = view.findViewById(R.id.til_password);
            tie_password = view.findViewById(R.id.tie_password);
            ct_showText = view.findViewById(R.id.ct_showText);

//            setfocus();
            setControlValues();

            til_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus) {
                        if (ll_tap_text.isEnabled()) {
                            improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),false,ll_tap_text,view);
//                            ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.control_default_background));
                        }
                        if (til_password.getEditText().getText().toString().isEmpty()) {
                            ll_tap_text.setVisibility(View.VISIBLE);
                            til_password.setVisibility(View.VISIBLE);
                        }
                        if (controlObject.isOnFocusEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                if (SubformFlag) {
                                    AppConstants.SF_Container_position = SubformPos;
                                    AppConstants.Current_ClickorChangeTagName = SubformName;

                                }
                                AppConstants.GlobalObjects.setCurrent_GPS("");
                                ((MainActivity) context).FocusExist(view);
                            }
                        }
                        til_password.setVisibility(View.VISIBLE);
                    } else {
                        if (ll_tap_text.isEnabled()) {
                            improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),true,ll_tap_text,view);
//                            ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.control_active_background));
                        }
                    }
                }
            });
     tie_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
             if(hasFocus) {
                 improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),true,ll_tap_text,view);
                 if(ct_alertPassword.getVisibility() == View.VISIBLE){
                     til_password.setBoxStrokeColor(ContextCompat.getColor(context, R.color.delete_icon));
                 }else{
//                     improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),true,ll_tap_text,view);
//                     til_password.setBoxStrokeColor(ContextCompat.getColor(context, R.color.control_input_background_color));
                 }
             }else{
                 improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),false,ll_tap_text,view);
             }
         }
     });
     tie_password.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {

         }

         @Override
         public void onTextChanged(CharSequence cs, int i, int i1, int i2) {
//             if (tie_password.getText() != null && tie_password.getText().toString().trim().length() > 0) {
//                 controlObject.setText(""+cs);
//             }
             if (cs.length() > 0) {
                 controlObject.setText("" + cs);
             } else {
                 controlObject.setText(null);
             }

             if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                 if (AppConstants.EventCallsFrom == 1) {
                     AppConstants.EventFrom_subformOrNot = SubformFlag;
                     if (SubformFlag) {
                         AppConstants.SF_Container_position = SubformPos;
                         AppConstants.Current_ClickorChangeTagName = SubformName;

                     }
                     AppConstants.GlobalObjects.setCurrent_GPS("");
                     ((MainActivity) context).TextChange(tie_password);
                 }
             }
         }

         @Override
         public void afterTextChanged(Editable editable) {

         }
     });



//            ll_tap_text.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    setfocus();
//                }
//            });

            linearLayout.addView(view);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addPasswordStrip", e);
        }
    }

    public void setfocus() {
        try {
            til_password.setVisibility(View.VISIBLE);
            showSoftKeyBoard(context, til_password);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setfocus", e);
        }
    }
    public void requestFocus(){
        til_password.requestFocus();
    }

    public CustomEditText getCustomEditText() {

//        return ce_TextType;
        return tie_password;
    }

    @SuppressLint("WrongConstant")
    private void setControlValues() {
        try {
            if (controlObject.getDisplayNameAlignment() != null) {
                tv_tapTextType.setVisibility(View.GONE);

                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {
//                    til_password = view.findViewById(R.id.til_password);
//                    tie_password = view.findViewById(R.id.tie_password);
                    til_password.setTag(controlObject.getControlName());
                    tie_password.setTag(controlObject.getControlName());
                    tv_displayName.setVisibility(View.GONE);
                    til_password.setHint(controlObject.getDisplayName());

                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {

//                til_password.getEditText().setHint(controlObject.getDisplayName());

                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {

//                    ce_TextType.setText(controlObject.getDisplayName());
//                til_password.getEditText().setHint(controlObject.getDisplayName());

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
                ll_displayName.setVisibility(View.GONE);
            }

            if (controlObject.isEnableShowOrHideOption()){
                iv_textTypeImage.setVisibility(View.GONE);
                til_password.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
            }else{
                iv_textTypeImage.setVisibility(View.VISIBLE);
            }
            if (controlObject.getDefaultValue() != null ){
//                ll_tap_text.setVisibility(View.GONE);
                til_password.setVisibility(View.VISIBLE);
                til_password.getEditText().setText(controlObject.getDefaultValue());
                controlObject.setText(controlObject.getDefaultValue());

            }

            if (controlObject.isDisable()) {
                improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),!controlObject.isDisable(),ll_tap_text,view);
            }
            if (controlObject.getPasswordLength() != null){
                tie_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.parseInt(controlObject.getPasswordLength()))});
            }

            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    public CustomTextView setAlertPasswordView() {

        return ct_alertPassword;
    }

    public void Clear() {
        try {
            ll_tap_text.setVisibility(View.VISIBLE);
            til_password.setVisibility(View.GONE);
            til_password.getEditText().setText("");
            setTextValue("");
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "Clear", e);
        }
    }




    @Override
    public String getTextValue() {
        return tie_password.getText().toString();

    }

    @Override
    public void setTextValue(String value) {
        controlObject.setText(value);
        tie_password.setText(value);
    }

    @Override
    public boolean getVisibility() {
        return controlObject.isInvisible();
    }

    @Override
    public void setVisibility(boolean visible) {
        if (visible) {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
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
    /*mandatory,mandatoryErrorMessage*/
    public boolean isMandatory() {
        return controlObject.isNullAllowed();
    }

    public void setMandatory(boolean mandatory) {
        iv_mandatory.setVisibility(mandatory == true ? View.VISIBLE : View.GONE);
        controlObject.setNullAllowed(mandatory);
    }

    public String getMandatoryErrorMessage() {
        return controlObject.getMandatoryFieldError();
    }

    public void setMandatoryErrorMessage(String mandatoryErrorMessage) {
        controlObject.setMandatoryFieldError(mandatoryErrorMessage);
    }

    /*Options*/
    /*hideDisplayName,enableShowOrHideOption,enableEncryption
    invisible/visibility
     */
    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        if (hideDisplayName) {
            ll_displayName.setVisibility(View.GONE);
        }
    }

    public boolean isEnableShowOrHideOption() {
        return controlObject.isEnableShowOrHideOption();
    }

    @SuppressLint("WrongConstant")
    public void setEnableShowOrHideOption(boolean enableShowOrHideOption) {
        controlObject.setEnableShowOrHideOption(enableShowOrHideOption);
        if (enableShowOrHideOption){
            iv_textTypeImage.setVisibility(View.GONE);
            til_password.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        }else{
            iv_textTypeImage.setVisibility(View.VISIBLE);
        }

    }

    public boolean isEnableEncryption() {
        return controlObject.isEnableEncryption();
    }

    public void setEnableEncryption(boolean enableEncryption) {
        controlObject.setEnableEncryption(enableEncryption);
    }
    public void setEnableEncryptionType(String enableEncryptionType) {
        controlObject.setEncryptionType(enableEncryptionType);
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

    public CustomTextView getTv_displayName() {
        return tv_displayName;
    }

    public LinearLayout getLl_leftRightWeight() {
        return ll_leftRightWeight;
    }
    public LinearLayout getLl_displayName() {
        return ll_displayName;
    }

    public LinearLayout gettap_text() {

        return ll_tap_text;
    }
    public CustomEditText getTie_password() {

        return tie_password;
    }
    public TextInputLayout getTil_password() {

        return til_password;
    }
    public CustomTextView getTv_tapTextType() {

        return tv_tapTextType;
    }

    /*ControlUI SettingsLayouts End*/

    public ControlObject getControlObject() {
        return controlObject;
    }
    @Override
    public void showMessageBelowControl(String msg) {
        if (msg != null && !msg.isEmpty()) {
            ct_showText.setVisibility(View.VISIBLE);
            ct_showText.setText(msg);
        } else {
            ct_showText.setVisibility(View.GONE);
        }
    }
}
