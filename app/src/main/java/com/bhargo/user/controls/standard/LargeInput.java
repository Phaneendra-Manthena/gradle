package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;
import static com.bhargo.user.utils.ImproveHelper.showSoftKeyBoard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import net.dankito.richtexteditor.android.RichTextEditor;
import net.dankito.richtexteditor.android.toolbar.AllCommandsEditorToolbar;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class LargeInput implements TextVariables, UIVariables {

    private static final String TAG = "LargeInput";
    private final boolean editorMode;
    private final boolean htmlViewer;
    Context context;
    LinearLayout linearLayout;
    ControlObject controlObject;
    int SubformPos;
    String SubformName;
    ImproveHelper improveHelper;
    boolean SubformFlag = false;
    private CustomTextView tv_displayName, tv_hint, tv_tap, ct_alertTypeLargeInput,ct_showText;
    private ImageView iv_mandatory;
    private View rView;
    private CustomEditText ce_TextType;
    private LinearLayout  ll_main_inside,ll_editor, ll_label,ll_displayName,ll_tap_text,ll_control_ui,layout_control;
    private RichTextEditor editor;
    private AllCommandsEditorToolbar editorToolbar;


    public LargeInput(Context context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        improveHelper = new ImproveHelper(context);
        editorMode = controlObject.isHtmlEditorEnabled();
        htmlViewer = controlObject.isHtmlViewerEnabled();
        initView();
    }

    public void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);

            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {

                    rView = linflater.inflate(R.layout.layout_large_input_place_holder, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {

                    rView = linflater.inflate(R.layout.layout_large_input_default, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {

                    rView = linflater.inflate(R.layout.layout_large_input_rounded_rectangle, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("4")) {

                    rView = linflater.inflate(R.layout.layout_large_input_rounded_rectangle, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("5")) {

                    rView = linflater.inflate(R.layout.layout_large_input_left_right, null);
//                    rView = linflater.inflate(R.layout.control_text_large_input_six, null);
                }
            } else {

                rView = linflater.inflate(R.layout.layout_large_input_default, null);
            }
            ll_main_inside = rView.findViewById(R.id.ll_main_inside);
            ll_tap_text = rView.findViewById(R.id.ll_tap_text);
            ll_label = rView.findViewById(R.id.ll_label);
            ll_displayName = rView.findViewById(R.id.ll_displayName);
            ll_editor = rView.findViewById(R.id.ll_editor);
            ll_control_ui = rView.findViewById(R.id.ll_control_ui);
            layout_control = rView.findViewById(R.id.layout_control);
            editor = rView.findViewById(R.id.text_editor);
            editorToolbar = rView.findViewById(R.id.editorToolbar);
            editorToolbar.setEditor(editor);

            tv_displayName = rView.findViewById(R.id.tv_displayName);
            tv_hint = rView.findViewById(R.id.tv_hint);
            ct_alertTypeLargeInput = rView.findViewById(R.id.ct_alertTypeText);
            iv_mandatory = rView.findViewById(R.id.iv_mandatory);
            tv_tap = rView.findViewById(R.id.tv_tapTextType);
            ce_TextType = rView.findViewById(R.id.ce_TextType);
            ce_TextType.setTag(controlObject.getControlName());
            ct_showText = rView.findViewById(R.id.ct_showText);

            setTvTapText(View.VISIBLE);
            ll_editor.setVisibility(View.GONE);

            ce_TextType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus) {
                        if(ll_tap_text.isEnabled()){
                            improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),hasFocus,ll_tap_text,rView);
                        }
//                        ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_default_background));}
                        if (ce_TextType.getText().toString().isEmpty()) {
//                            tv_tap.setVisibility(View.VISIBLE);
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
                    }else{
                        if(ll_tap_text.isEnabled()){
                            improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),hasFocus,ll_tap_text,rView);
                        }
//                        ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_active_background));}
                    }
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
                        ct_alertTypeLargeInput.setText("");
                        ct_alertTypeLargeInput.setVisibility(View.GONE);
                    }
                }
            });*/

            tv_tap.setText(R.string.tap_to_enter_large_text);

            tv_tap.setOnClickListener(new View.OnClickListener() {
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
                        ct_alertTypeLargeInput.setText("");
                        ct_alertTypeLargeInput.setVisibility(View.GONE);
                    }
                }
            });

            linearLayout.addView(rView);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public void setfocus() {
        try {
            tv_tap.setVisibility(View.GONE);
            if (controlObject.isHtmlEditorEnabled()) {
                ll_editor.setVisibility(View.VISIBLE);
                editorToolbar.setVisibility(View.VISIBLE);
                ce_TextType.setVisibility(View.GONE);
                showSoftKeyBoard(context, editor);
            } else if (controlObject.isHtmlViewerEnabled()) {
                ll_editor.setVisibility(View.VISIBLE);
                editor.setEnabled(false);
                editor.setInputEnabled(false);
                editorToolbar.setVisibility(View.GONE);
                ce_TextType.setVisibility(View.GONE);
                showSoftKeyBoard(context, editor);
            } else {
                ll_editor.setVisibility(View.GONE);
                ce_TextType.setVisibility(View.VISIBLE);
                showSoftKeyBoard(context, ce_TextType);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setfocus", e);
        }
    }

    public LinearLayout getLargeInputView() {
        return linearLayout;
    }

    public CustomEditText getCustomEditText() {

        return ce_TextType;
    }

    public RichTextEditor getTextEditor() {

        return editor;
    }
    public String getTextEditorString() {
        return editor.getHtml().replaceAll("\u200B", "");
    }

    public LinearLayout getEditorLayout() {

        return ll_editor;

    }

    public AllCommandsEditorToolbar getEditorToolBar() {

        return editorToolbar;

    }

    private void setControlValues() {
        try {
            ll_tap_text.setTag(controlObject.getControlName());
            if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {
                TextInputLayout til_TextType= rView.findViewById(R.id.til_TextType);
                til_TextType.setHint(controlObject.getDisplayName());
                tv_displayName.setVisibility(View.GONE);
            }

            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            if (controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                tv_hint.setText(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }
            if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().isEmpty()) {
                tv_tap.setVisibility(View.GONE);
                if (controlObject.isHtmlEditorEnabled() || controlObject.isHtmlViewerEnabled()) {
                    ll_editor.setVisibility(View.VISIBLE);
                    ce_TextType.setVisibility(View.GONE);

                    editor.addLoadedListener(new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            editor.setHtml(controlObject.getDefaultValue());

                            return null;
                        }
                    });
                    controlObject.setText(getTextEditorString());
                } else {
                    ll_editor.setVisibility(View.GONE);
                    ce_TextType.setVisibility(View.VISIBLE);
                    ce_TextType.setText(controlObject.getDefaultValue());
                    setTextValue(controlObject.getDefaultValue());
                }
            }

            if (controlObject.isNullAllowed()) {
                iv_mandatory.setVisibility(View.VISIBLE);
            } else {
                iv_mandatory.setVisibility(View.GONE);
            }

            if (controlObject.isInvisible()) {
                linearLayout.setVisibility(View.GONE);
                setVisibility(false);
            } else {
                linearLayout.setVisibility(View.VISIBLE);
                setVisibility(true);
            }
            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            }
            if (controlObject.isEnableMaxCharacters()) {
                ce_TextType.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.parseInt(controlObject.getMaxCharacters()))});
            }
            if (controlObject.isHtmlViewerEnabled() && !controlObject.isHtmlEditorEnabled()) {
                editor.setEnabled(false);
                editor.setInputEnabled(false);
            }
            if (controlObject.isDisable()) {
                if (controlObject.isHtmlEditorEnabled() || controlObject.isHtmlViewerEnabled()) {
                    editor.setEnabled(false);
                    editor.setInputEnabled(false);
                    editor.setFocusable(false);
                    improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),!controlObject.isDisable(),ll_editor,rView);
                }else{
                    improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),!controlObject.isDisable(),ll_tap_text,rView);
                }
//                setViewDisable(rView, false);
//                setViewDisableOrEnableDefault(context,ll_main_inside, false);
//                improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),!controlObject.isDisable(),ll_tap_text,rView);
            }


            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }
    private void disableControl(boolean enabled) {
        ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_disable_background));
        tv_tap.setTextColor(ContextCompat.getColor(context,R.color.control_disable_text_color));
        ce_TextType.setTextColor(ContextCompat.getColor(context,R.color.control_disable_text_color));
        disableChild(ll_tap_text,enabled);
    }

    private void disableChild(View view,boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                if (child != null) {
                    disableChild(child,enabled);
                }
            }}
    }

    public void setDefaultValue(String value) {
        try {
            controlObject.setDefaultValue(value);
            if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().isEmpty()) {
                tv_tap.setVisibility(View.GONE);
                if (controlObject.isHtmlEditorEnabled() || controlObject.isHtmlViewerEnabled()) {
                    ll_editor.setVisibility(View.VISIBLE);
                    ce_TextType.setVisibility(View.GONE);
                    editor.addLoadedListener(() -> {
                        editor.setHtml(controlObject.getDefaultValue());
                        return null;
                    });
                    if (controlObject.isHtmlViewerEnabled()) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        editor.setLayoutParams(params);
                        editorToolbar.setVisibility(View.GONE);
                        editor.setVisibility(View.GONE);
                        editor.setEnabled(false);
                        editor.setInputEnabled(false);
                    }
                    controlObject.setText(getTextEditorString());
                } else {
                    ll_editor.setVisibility(View.GONE);
                    ce_TextType.setVisibility(View.VISIBLE);
                    ce_TextType.setText(value);
                    setTextValue(value);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setDefaultValue", e);
        }
    }

    public CustomTextView setAlertLargeInput() {

        return ct_alertTypeLargeInput;
    }

    public CustomTextView gettap_text() {

        return tv_tap;
    }

    public void Clear() {
        try {
            ce_TextType.setText("");
            setTextValue("");
//            tv_tap.setVisibility(View.VISIBLE);
            setTvTapText(View.VISIBLE);
            ce_TextType.setVisibility(View.GONE);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "Clear", e);
        }
    }

    public boolean isEditorModeEnabled() {
        return editorMode;
    }

    public boolean isHTMLViewerEnabled() {
        return htmlViewer;
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


    public void setTvTapText(int visibility) {
        try {
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                tv_tap.setVisibility(View.GONE);
            } else {
                tv_tap.setVisibility(visibility);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setTvTapText", e);
        }
    }

    @Override
    public String getTextValue() {

        controlObject.setText(ce_TextType.getText().toString());
        return ce_TextType.getText().toString();

    }

    public String getHtml() {
        return editor.getHtml().toString();
    }

    @Override
    public void setTextValue(String value) {
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
        if (controlObject.isHtmlViewerEnabled() && !controlObject.isHtmlEditorEnabled()) {
            editor.setEnabled(false);
            editor.setInputEnabled(false);
        }
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

        controlObject.setHtmlEditorEnabled(enabled);

        showHtmlEditorUI();

    }

    @Override
    public void enableHTMLViewer(boolean enabled) {
        controlObject.setHtmlViewerEnabled(enabled);
        if (enabled && controlObject.isHtmlEditorEnabled()) {
            showHtmlEditorUI();
        }else if(enabled){
            showHtmlUI();
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

    public String getDefaultValueFromCO() {
        return controlObject.getDefaultValue();
    }

    public void setDefaultValueInCO(String defaultValue) {
        setTextValue(defaultValue);
        controlObject.setDefaultValue(defaultValue);
    }

    /*Validations*/
    //doubt:uniqueField
    /*mandatory,mandatoryErrorMessage,uniqueField,uniqueFieldErrorMessage,
     , enableMaxCharacters,maxCharacters,maxCharError,enableMinCharacters,minCharacters,minCharError*/

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

    public boolean isEnableMaxCharacters() {
        return controlObject.isEnableMaxCharacters();
    }

    public void setEnableMaxCharacters(boolean enableMaxCharacters) {
        controlObject.setEnableMaxCharacters(enableMaxCharacters);
    }

    public String getMaxCharacters() {
        return controlObject.getMaxCharacters();
    }

    public void setMaxCharacters(String maxCharacters) {
        controlObject.setMaxCharacters(maxCharacters);
    }

    public String getMaxCharError() {
        return controlObject.getMaxCharError();
    }

    public void setMaxCharError(String maxCharError) {
        controlObject.setMaxCharError(maxCharError);
    }

    public boolean isEnableMinCharacters() {
        return controlObject.isEnableMinCharacters();
    }

    public void setEnableMinCharacters(boolean enableMinCharacters) {
        controlObject.setEnableMaxCharacters(enableMinCharacters);
    }

    public String getMinCharacters() {
        return controlObject.getMinCharacters();
    }

    public void setMinCharacters(String minCharacters) {
        controlObject.setMaxCharacters(minCharacters);
    }

    public String getMinCharError() {
        return controlObject.getMinCharError();
    }

    public void setMinCharError(String minCharError) {
        controlObject.setMinCharError(minCharError);
    }

    /*Options*/
    /*hideDisplayName
    invisible/visibility,disable/enabled
     */
    //not implemented:HtmlEditorEnabled,HtmlViewerEnabled
    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        ll_displayName.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
    }

    private void showHtmlUI() {
        ll_editor.setVisibility(View.VISIBLE);
        ce_TextType.setVisibility(View.GONE);
        editor.setHtml(controlObject.getDefaultValue());
        editor.setInputEnabled(false);
        controlObject.setText(getTextEditorString());
    }

    public void showHtmlEditorUI() {
        ll_editor.setVisibility(View.VISIBLE);
        ce_TextType.setVisibility(View.GONE);
        editor.setEnabled(true);
        editor.setInputEnabled(true);
        editor.addLoadedListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                editor.setHtml(controlObject.getDefaultValue());
                return null;
            }
        });
        controlObject.setText(getTextEditorString());
    }

    public LinearLayout getLl_tap_text() { // linear for taptext

        return ll_tap_text;
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
