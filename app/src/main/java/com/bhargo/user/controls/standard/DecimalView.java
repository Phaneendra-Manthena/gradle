package com.bhargo.user.controls.standard;

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
import com.bhargo.user.utils.DecimalDigitsInputFilter;
import com.bhargo.user.utils.ImproveHelper;
import com.google.android.material.textfield.TextInputLayout;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;
import static com.bhargo.user.utils.ImproveHelper.showSoftKeyBoard;

import androidx.core.content.ContextCompat;

public class DecimalView implements TextVariables, UIVariables {

    private static final String TAG = "DecimalView";
    private final int DecimalViewTAG = 0;
    Context context;
    CustomEditText customEditText;
    ControlObject controlObject;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    ImproveHelper improveHelper;
    private LinearLayout linearLayout,ll_main_inside,ll_tap_text,ll_displayName,ll_control_ui,layout_control,ll_leftRightWeight;
    private CustomTextView tv_tapTextType;
    private CustomEditText ce_TextType;
    private CustomTextView tv_displayName, tv_hint, ct_alertDecimal,ct_showText;
    private ImageView iv_mandatory,iv_textTypeImage;
    private View view;
    TextInputLayout til_TextType;

    public DecimalView(Context context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
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

            addDecimalViewStrip(context);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getDecimal() {

        return linearLayout;
    }

    public void addDecimalViewStrip(final Context context) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {

                    view = linflater.inflate(R.layout.layout_text_input_place_holder, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {

                    view = linflater.inflate(R.layout.layout_text_input_default, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {

                    view = linflater.inflate(R.layout.layout_text_input_rounded_rectangle, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("4")) {

                    view = linflater.inflate(R.layout.layout_text_input_rounded_rectangle, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("5")) {

                    view = linflater.inflate(R.layout.layout_text_input_left_right, null);

                } else {

                    view = linflater.inflate(R.layout.layout_text_input_default, null);

                }
            } else {

                view = linflater.inflate(R.layout.layout_text_input_default, null);
            }
            view.setTag(DecimalViewTAG);


            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            layout_control = view.findViewById(R.id.layout_control);
            ll_leftRightWeight = view.findViewById(R.id.ll_leftRightWeight);
            ll_tap_text = view.findViewById(R.id.ll_tap_text);
            tv_tapTextType = view.findViewById(R.id.tv_tapTextType);
            ce_TextType = view.findViewById(R.id.ce_TextType);
            ce_TextType.setTag(controlObject.getControlName());
            tv_tapTextType.setText(R.string.tap_to_enter_decimals);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            ct_alertDecimal = view.findViewById(R.id.ct_alertTypeText);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            iv_textTypeImage = view.findViewById(R.id.iv_textTypeImage);
            ct_showText = view.findViewById(R.id.ct_showText);

            tv_tapTextType.setVisibility(View.VISIBLE);

            ce_TextType.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
//        ce_TextType.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

            ImageView iv_textTypeImage = view.findViewById(R.id.iv_textTypeImage);
            iv_textTypeImage.setVisibility(View.VISIBLE);
            iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_icon_decimal));
//        iv_textTypeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.icons_percentage_drawable));

            ce_TextType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus) {
                        if(ll_tap_text.isEnabled() && ct_alertDecimal.getVisibility()!=View.VISIBLE){
                            improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),false,ll_tap_text,view);
                        }
//                            ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_default_background));}
                        if (ce_TextType.getText().toString().isEmpty()) {
                            ll_tap_text.setVisibility(View.VISIBLE);
                            ce_TextType.setVisibility(View.VISIBLE);
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
                    }else{
                        if(ll_tap_text.isEnabled()){
                        ct_alertDecimal.setVisibility(View.GONE);
                            improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),true,ll_tap_text,view);
//                        ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_active_background));
                    }}
                }
            });

           /* ce_TextType.addTextChangedListener(new TextWatcher() {
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
                        ct_alertDecimal.setText("");
                        ct_alertDecimal.setVisibility(View.GONE);
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
            setControlValues();

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
                        ct_alertDecimal.setText("");
                        ct_alertDecimal.setVisibility(View.GONE);
                    }
                }
            });

            linearLayout.addView(view);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "addDecimalViewStrip", e);
        }
    }

    public void setfocus() {
        try {
            tv_tapTextType.setVisibility(View.GONE);
            ce_TextType.setVisibility(View.VISIBLE);
            if (controlObject.getDefaultValue() != null) {

                ce_TextType.setText(controlObject.getDefaultValue());
                setTextValue(controlObject.getDefaultValue());

            }
            showSoftKeyBoard(context, ce_TextType);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setfocus", e);
        }
    }

    public void Clear() {
        try {
            ll_tap_text.setVisibility(View.VISIBLE);
            ce_TextType.setVisibility(View.GONE);
            ce_TextType.getText().clear();
            setTextValue("");
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "Clear", e);
        }
    }

    public CustomEditText getCustomEditText() {

        return ce_TextType;
    }

    private void setControlValues() {
        try {
            ll_tap_text.setTag(controlObject.getControlName());
            if (controlObject.getDisplayNameAlignment() != null) {
                tv_tapTextType.setVisibility(View.GONE);

                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {
                    til_TextType = view.findViewById(R.id.til_TextType);
                    til_TextType.setHint(controlObject.getDisplayName());
                    ce_TextType.setText("");
                    setTextValue("");
                    tv_displayName.setVisibility(View.GONE);
                    iv_textTypeImage.setVisibility(View.GONE);

                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {

//                    ce_TextType.setHint(controlObject.getDisplayName());


                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {

//                    ce_TextType.setText(controlObject.getDisplayName());
//                    ce_TextType.setHint(controlObject.getDisplayName());

                }
            }
            if (controlObject.getDisplayNameAlignment() != null) {
                tv_tapTextType.setVisibility(View.GONE);

                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {
                    TextInputLayout til_TextType = view.findViewById(R.id.til_TextType);


                    til_TextType.setHint(controlObject.getDisplayName());
                    ce_TextType.setText("");
                    setTextValue("");
                    tv_displayName.setVisibility(View.GONE);
                    iv_textTypeImage.setVisibility(View.GONE);

                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {

                    ce_TextType.setHint(controlObject.getDisplayName());


                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {

//                    ce_TextType.setText(controlObject.getDisplayName());
                    ce_TextType.setHint(controlObject.getDisplayName());

                }
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
            if (controlObject.getCharactersAfterDecimal() != null) {
                int decimalLimit = Integer.parseInt(controlObject.getCharactersAfterDecimal());
                ce_TextType.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(decimalLimit)});
            }
            if(controlObject.isHideDisplayName()){
                ll_displayName.setVisibility(View.GONE);
            }
            if (controlObject.isDisable()) {

//                setViewDisable(view, false);
//                setViewDisableOrEnableDefault(context,ll_main_inside, false);
                improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),!controlObject.isDisable(),ll_tap_text,view);
            }

            if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().contentEquals("")) {
                tv_tapTextType.setVisibility(View.GONE);
                ce_TextType.setVisibility(View.VISIBLE);
                ce_TextType.setText(controlObject.getDefaultValue());
                setTextValue(controlObject.getDefaultValue());
            }

            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    public CustomTextView setAlertDecimalView() {

        return ct_alertDecimal;
    }


    public CustomTextView gettap_text() {

        return tv_tapTextType;
    }

    @Override
    public String getTextValue() {
        return ce_TextType.getText().toString();

    }

    @Override
    public void setTextValue(String value) {
        controlObject.setText(value);
        ce_TextType.setText(value);
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
        controlObject.setDisable(enabled);
//        setViewDisable(view, !enabled);
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
    /*displayName,hint,defaultValue*/

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

    public String getDefaultValue() {
        return controlObject.getDefaultValue();
    }

    public void setDefaultValue(String defaultValue) {
        setTextValue(defaultValue);
        controlObject.setDefaultValue(defaultValue);
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
    /*hideDisplayName,enableDecimalCharacters,charactersAfterDecimal
    invisible/visibility,disable/enabled
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

    public boolean isEnableDecimalCharacters() {
        return controlObject.isEnableDecimalCharacters();
    }

    public void setEnableDecimalCharacters(boolean enableDecimalCharacters) {
        controlObject.setEnableDecimalCharacters(enableDecimalCharacters);
//        if(enableDecimalCharacters){
//            int decimalLimit = Integer.parseInt(controlObject.getCharactersAfterDecimal());
//            ce_TextType.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(decimalLimit)});
//        }
    }

    public String getCharactersAfterDecimal() {
        return controlObject.getCharactersAfterDecimal();
    }

    public void setCharactersAfterDecimal(String charactersAfterDecimal) {
        controlObject.setCharactersAfterDecimal(charactersAfterDecimal);
        if(controlObject.isEnableDecimalCharacters()){
            int decimalLimit = Integer.parseInt(charactersAfterDecimal);
            ce_TextType.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(decimalLimit)});
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

    public CustomTextView getTv_displayName() {
        return tv_displayName;
    }

    public LinearLayout getLl_leftRightWeight() {
        return ll_leftRightWeight;
    }
    public LinearLayout getLl_displayName() {
        return ll_displayName;
    }

    public LinearLayout getLl_tap_text() { // linear for taptext
        return ll_tap_text;
    }

    /*ControlUI SettingsLayouts End*/
    public String getDecimalValue() {
        return ce_TextType.getText().toString();
    }
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
    public void requestFocus(){
        ce_TextType.requestFocus();
    }
}
