package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisable;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Html;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.TextVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.google.zxing.integration.android.IntentIntegrator;

import net.dankito.richtexteditor.android.RichTextEditor;
import net.dankito.richtexteditor.android.toolbar.AllCommandsEditorToolbar;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class DynamicLabel implements TextVariables, UIVariables {
    private static final String TAG = "DynamicLabel";
    private final boolean htmlViewer;
    Activity context;
    CustomTextView customTextView;
    ControlObject controlObject;
    ImproveHelper improveHelper;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    private LinearLayout linearLayout, ll_editor, ll_displayName;
    private CustomTextView tv_displayName, tv_dynamicLabel, tv_hint, ct_showText;
    private ImageView iv_mandatory;
    private View view;
    private LinearLayout ll_main_inside, sectionLayout, ll_layoutBackgroundColor, ll_label, ll_control_ui, layout_control, ll_tap_text, ll_leftRightWeight;
    private IntentIntegrator qr_bar_Scanner;
    private RichTextEditor editor;
    private AllCommandsEditorToolbar editorToolbar;
    private CardView cardView;
    private View divider;


    private Object tag;
    private String strDynamicText = "";
    private String strUnderLineHexColor = "#000000";

    public DynamicLabel(Activity context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        htmlViewer = controlObject.isHtmlViewerEnabled();
        improveHelper = new ImproveHelper(context);

        initView();

    }

    public void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            qr_bar_Scanner = new IntentIntegrator(context);
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {

                    view = linflater.inflate(R.layout.layout_dynamic_label_rectangle, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {

                    view = linflater.inflate(R.layout.layout_dynamic_label_rounded_rectangle, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("5")) {

                    view = linflater.inflate(R.layout.layout_dynamic_label_left_right, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                    view = linflater.inflate(R.layout.layout_dynamic_label_six, null);
                    divider = view.findViewById(R.id.vDisplayDivider);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
                    // view = linflater.inflate(R.layout.layout_dynamic_label_default, null);
                    view = linflater.inflate(R.layout.layout_dynamic_label_seven, null);
//                    divider = view.findViewById(R.id.vDisplayDivider);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {
//                    view = linflater.inflate(R.layout.control_dynamic_label, null);
                    view = linflater.inflate(R.layout.control_dynamic_label_nine, null);
//                    divider = view.findViewById(R.id.vDisplayDivider);
                } else {

//                    view = linflater.inflate(R.layout.control_dynamic_label, null);
                    view = linflater.inflate(R.layout.layout_dynamic_label_default, null);
                }
            } else {

//                view = linflater.inflate(R.layout.control_dynamic_label, null);
                view = linflater.inflate(R.layout.layout_dynamic_label_default, null);
            }
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_layoutBackgroundColor = view.findViewById(R.id.ll_layoutBackgroundColor);
            ll_label = view.findViewById(R.id.ll_label);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            layout_control = view.findViewById(R.id.layout_control);
            ll_tap_text = view.findViewById(R.id.ll_tap_text);
            ll_leftRightWeight = view.findViewById(R.id.ll_leftRightWeight);
            cardView = view.findViewById(R.id.card);
            sectionLayout = view.findViewById(R.id.sectionLayout);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_dynamicLabel = view.findViewById(R.id.tv_dynamicLabel);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            ll_editor = view.findViewById(R.id.ll_editor);
            editor = view.findViewById(R.id.text_editor);
            editorToolbar = view.findViewById(R.id.editorToolbar);
            ct_showText = view.findViewById(R.id.ct_showText);

            editorToolbar.setEditor(editor);
            editor.setEditorFontSize(16);
            tv_dynamicLabel.setTag(controlObject.getControlName());
            if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
                tv_dynamicLabel.setTag(controlObject.getControlName());
                ll_displayName.setVisibility(View.GONE);
            }
            tv_dynamicLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                        if (AppConstants.EventCallsFrom == 1) {
                            AppConstants.EventFrom_subformOrNot = SubformFlag;
                            if (SubformFlag) {
                                AppConstants.SF_Container_position = SubformPos;
                                AppConstants.Current_ClickorChangeTagName = SubformName;
                            }
                            if (AppConstants.GlobalObjects != null) {
                                AppConstants.GlobalObjects.setCurrent_GPS("");
                            }
                            ((MainActivity) context).ClickEvent(view);
                        }
                    }
                }
            });

            setControlValues();
            linearLayout.addView(view);
//        #039be5

            setDynamicLabelDesignSix();
/*        if (cardView != null && controlObject.getBackGroundColor() != null && !controlObject.getBackGroundColor().isEmpty()) {
            if (controlObject.getBackGroundColor() != null && !controlObject.getBackGroundColor().isEmpty()) {
                cardView.setCardBackgroundColor(Color.parseColor(controlObject.getBackGroundColor()));
                tv_dynamicLabel.setTextColor(Color.parseColor(controlObject.getTextHexColor()));
            }
        }else if ((controlObject.getBackGroundColor() != null && !controlObject.getBackGroundColor().isEmpty()) ) {

                ll_main_inside.setBackgroundColor(Color.parseColor(controlObject.getBackGroundColor()));
                tv_dynamicLabel.setTextColor(Color.parseColor(controlObject.getTextHexColor()));

        }*/

            if (controlObject != null && controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
                ll_main_inside.setGravity(Gravity.CENTER);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }


    }

    public LinearLayout getDynamicLabel() {

        return linearLayout;
    }

    private void setControlValues() {
        try {


            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }

            if (controlObject.getValue() != null) {
                if (controlObject.isHtmlViewerEnabled()) {
                    sectionLayout.setVisibility(View.GONE);
                    ll_editor.setVisibility(View.VISIBLE);
                    editor.setInputEnabled(false);
                    editor.addLoadedListener(new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            editor.setHtml(controlObject.getValue());
                            return null;
                        }
                    });
                } else {
                    strDynamicText = controlObject.getValue();

                    if (controlObject.isEnableMaskCharacters()) {
                        maskCharactersPartialOrFull();
                    }
                    tv_dynamicLabel.setText(strDynamicText);
                    setTextValue(strDynamicText);
                    ll_editor.setVisibility(View.GONE);
                    sectionLayout.setVisibility(View.VISIBLE);
                    setDynamicLabelDesignSix();
//                tv_dynamicLabel.setTextColor(ContextCompat.getColor(context, R.color.black));
                }
            }

            if (controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                tv_hint.setVisibility(View.VISIBLE);
                tv_hint.setText(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }
            if (controlObject.isDisable()) {
                setViewDisable(view, false);
            }
            if (controlObject.isMakeAsSection()) {

                tv_displayName.setVisibility(View.GONE);
                ll_main_inside.setPadding(0, 0, 0, 0);
                tv_dynamicLabel.setText(controlObject.getValue());
                setTextValue(strDynamicText);
                sectionLayout.setVisibility(View.VISIBLE);
                sectionLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.dullGray));
                setDynamicLabelDesignSix();
                setDisplaySettings(context, tv_dynamicLabel, controlObject);

                if (controlObject.isHtmlViewerEnabled()) {

                }

            }
            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            }
            if (controlObject.isReadFromBarcode()) {
//                qr_bar_Scanner.initiateScan();
            }
           /* if (controlObject.isReadFromQRCode()) {
//                qr_bar_Scanner.initiateScan();
            }
            if (controlObject.isEnableUnicode()) {
                Log.d("DynamicUnicodeFormat", controlObject.getUnicodeFormatId());
            }*/

            if (controlObject.isLayoutBackGroundEnable()) {

                if (controlObject.isHtmlViewerEnabled()) {
                    ll_editor.setBackgroundColor(Color.parseColor(controlObject.getLayoutBackGroundColor()));
                } else {
//                    sectionLayout.setBackgroundColor(Color.parseColor(controlObject.getLayoutBackGroundColor()));
                }
            }
            if (controlObject.getStrikeText()) {
                tv_dynamicLabel.setPaintFlags(tv_dynamicLabel.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }

            underLineText(controlObject.getValue()); // UnderLine Text

            if (controlObject.getControlName().equalsIgnoreCase("bhargo_title")) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
                ll_main_inside.setPadding(0, 25, 0, 0);
                ll_main_inside.setLayoutParams(layoutParams);
                ll_main_inside.setGravity(Gravity.CENTER_VERTICAL);
            }
            if (controlObject.getDisplayNameAlignment() == null) {
                setDisplaySettings(context, tv_displayName, controlObject);
            } else if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {
                setDisplaySettings(context, tv_displayName, controlObject);
            }

           /* setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());*/

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
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
        if (msg != null && !msg.isEmpty()) {
            ct_showText.setVisibility(View.VISIBLE);
            ct_showText.setText(msg);
        } else {
            ct_showText.setVisibility(View.GONE);
        }
    }

    public RichTextEditor getTextViewer() {

        return editor;
    }

    public LinearLayout getEditorLayout() {

        return ll_editor;

    }

    public AllCommandsEditorToolbar getEditorToolBar() {

        return editorToolbar;

    }

    public void setDynamicLabelDesignSix() {
        try {
            if (controlObject != null && controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                if (ll_layoutBackgroundColor != null) {
                    ll_layoutBackgroundColor.setVisibility(View.GONE);
                }
                if (controlObject.getBackGroundColor() != null && !controlObject.getBackGroundColor().isEmpty()) {
                    if (controlObject.getBackGroundColor() != null && !controlObject.getBackGroundColor().isEmpty()) {
//                        cardView.setCardBackgroundColor(Color.parseColor(controlObject.getBackGroundColor()));
                        tv_dynamicLabel.setTextColor(Color.parseColor(controlObject.getTextHexColor()));
//                        setTextColor(controlObject.getTextHexColor());
                    }
                }
                if (controlObject.getTextAlignment() != null) {
                    if (controlObject.getTextAlignment().equalsIgnoreCase(context.getString(R.string.left))) {
                        tv_dynamicLabel.setGravity(Gravity.START);
                    } else {
                        tv_dynamicLabel.setGravity(Gravity.END);
                    }
                } else {
                    tv_dynamicLabel.setTextColor(ContextCompat.getColor(context, R.color.black));
                    tv_dynamicLabel.setGravity(Gravity.END);
                }
            } else if (controlObject != null && controlObject.getDisplayNameAlignment() != null) {
                tv_dynamicLabel.setTextColor(Color.parseColor(controlObject.getTextHexColor()));
                tv_dynamicLabel.setText(strDynamicText);
//                setTextColor(String.valueOf(ContextCompat.getColor(context, R.color.black)));
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setDynamicLabelDesignSix", e);
        }
    }
/*
    public void setDynamicLabelDesignSix() {
        try {
            if (controlObject != null &&
                    controlObject.getDisplayNameAlignment() != null &&
                    controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                ll_layoutBackgroundColor.setVisibility(View.GONE);
                if (cardView != null && controlObject.getBackGroundColor() != null && !controlObject.getBackGroundColor().isEmpty()) {
                    if (controlObject.getBackGroundColor() != null && !controlObject.getBackGroundColor().isEmpty()) {
                        cardView.setCardBackgroundColor(Color.parseColor(controlObject.getBackGroundColor()));
                        tv_dynamicLabel.setTextColor(Color.parseColor(controlObject.getTextHexColor()));
                        setTextColor(controlObject.getTextHexColor());
                    }
                }
                if (controlObject.getTextAlignment() != null) {
                    if (controlObject.getTextAlignment().equalsIgnoreCase(context.getString(R.string.left))) {
                        tv_dynamicLabel.setGravity(Gravity.START);
                    }
                }
            } else if (controlObject != null &&
                    controlObject.getDisplayNameAlignment() != null &&
                    !controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
//                tv_dynamicLabel.setTextColor(ContextCompat.getColor(context, R.color.black));
//                setTextColor(String.valueOf(ContextCompat.getColor(context, R.color.black)));
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setDynamicLabelDesignSix", e);
        }
    }
*/

    public LinearLayout getLl_main_inside() {
        return ll_main_inside;
    }

    public View getDivider() {
        return divider;
    }

    @Override
    public String getTextValue() {
        return tv_dynamicLabel.getText().toString();

    }

    @Override
    public void setTextValue(String value) {
        controlObject.setValue(value);
        controlObject.setText(value);
        if (tv_dynamicLabel != null) {
            tv_dynamicLabel.setText(value);
        }
    }

    public String getHtml() {
        return editor.getHtml();

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
        controlObject.setDisable(!enabled);
//        setViewDisable(view, !enabled);
        setViewDisableOrEnableDefault(context, view, enabled);
    }

    @Override
    public void clean() {

        //tv_dynamicLabel.setText("");
    }

    @Override
    public void enableHTMLEditor(boolean enabled) {

    }

    @Override
    public void enableHTMLViewer(boolean enabled) {

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
            strUnderLineHexColor = color;
        }
    }

    public String getDisplayName() {
        return controlObject.getDisplayName();
    }

    /*General*/
    /*displayName,hint,value*/

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

    public String getValue() {
        return controlObject.getValue();
    }

    public void setValue(String value) {
        try {
            controlObject.setValue(value);
            controlObject.setText(value);
            tv_displayName.setText(controlObject.getDisplayName());
            if (controlObject.getValue() != null && !controlObject.getValue().isEmpty()) {
                if (controlObject.isHtmlViewerEnabled()) {
                    if (controlObject.isMakeAsSection() && controlObject.isLayoutBackGroundEnable()) {
                        ll_editor.setBackgroundColor(Color.parseColor(controlObject.getLayoutBackGroundColor()));
                        editor.setBackgroundColor(Color.parseColor(controlObject.getLayoutBackGroundColor()));
                        ll_main_inside.setBackgroundColor(Color.parseColor(controlObject.getLayoutBackGroundColor()));
                        ll_layoutBackgroundColor.setBackgroundColor(Color.parseColor(controlObject.getLayoutBackGroundColor()));
                        ll_label.setBackgroundColor(Color.parseColor(controlObject.getLayoutBackGroundColor()));
                        cardView.setBackgroundColor(Color.parseColor(controlObject.getLayoutBackGroundColor()));

                    }
                    ll_editor.setVisibility(View.VISIBLE);
                    sectionLayout.setVisibility(View.GONE);
                    editor.addLoadedListener(new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            editor.setHtml(controlObject.getValue());
                            return null;
                        }
                    });
                    setViewDisable(view, false);
                } else {
                    setTextValue(controlObject.getValue());
                    tv_dynamicLabel.setText(controlObject.getValue());
                    ll_editor.setVisibility(View.GONE);
                    sectionLayout.setVisibility(View.VISIBLE);
                    setDynamicLabelDesignSix();
//                tv_dynamicLabel.setTextColor(ContextCompat.getColor(context, R.color.black));
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setValue", e);
        }
    }

   /* public void setValue(String value) {
        setTextValue(value);
        controlObject.setValue(value);
    }*/

    /*Options*/
    /*hideDisplayName,htmlViewerEnabled,makeAsSection,enableDisplayAsBarCode,enableDisplayAsQRCode
    enableUnicode,unicodeFormat,unicodeFormatId,enableMaskCharacters,maskCharacterType(Partial/Full),noOfCharactersToMask
    maskCharacterDirection(First/Last),strikeText,layoutBackGroundEnable,layoutBackGroundColor
    invisible/visibility
     */
    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        ll_displayName.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
        if (ll_layoutBackgroundColor != null) {
            ll_layoutBackgroundColor.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
        }

    }

    public boolean isHtmlViewerEnabled() {
        return controlObject.isHtmlViewerEnabled();
    }

    public void setHtmlViewerEnabled(boolean htmlViewerEnabled) {
        controlObject.setHtmlViewerEnabled(htmlViewerEnabled);
        if (htmlViewerEnabled) {
            ll_editor.setVisibility(View.VISIBLE);

            sectionLayout.setVisibility(View.GONE);

            editor.addLoadedListener(new Function0<Unit>() {
                @Override
                public Unit invoke() {
                    editor.setHtml(controlObject.getValue());
                    return null;
                }
            });
            editor.setInputEnabled(false);
            ll_editor.setEnabled(false);
        } else {
            tv_dynamicLabel.setText(controlObject.getValue());
            setTextValue(controlObject.getValue());
            ll_editor.setVisibility(View.GONE);
            sectionLayout.setVisibility(View.VISIBLE);
            setDynamicLabelDesignSix();
        }
    }

    public boolean isMakeAsSection() {
        return controlObject.isMakeAsSection();
    }

    public void setMakeAsSection(boolean makeAsSection) {
        controlObject.setMakeAsSection(makeAsSection);
        if (makeAsSection) {
            tv_displayName.setVisibility(View.GONE);
            ll_main_inside.setPadding(0, 0, 0, 0);
            tv_dynamicLabel.setText(controlObject.getValue());
            setTextValue(controlObject.getValue());
            sectionLayout.setVisibility(View.VISIBLE);
            sectionLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.dullGray));
            setDynamicLabelDesignSix();
            setDisplaySettings(context, tv_dynamicLabel, controlObject);

        }

//            ll_main_inside.setPadding(0, 0, 0, 0);
//            ll_layoutBackgroundColor.setVisibility(View.GONE);
//            tv_dynamicLabel.setText(controlObject.getValue());
//            setTextValue(controlObject.getValue());
//            sectionLayout.setVisibility(View.VISIBLE);
//            sectionLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.dullGray));
//            setDynamicLabelDesignSix();
//            setDisplaySettings(context, tv_dynamicLabel, controlObject);
//        }
    }

    public boolean isEnableDisplayAsBarCode() {
        return controlObject.isEnableDisplayAsBarCode();
    }

    public void setEnableDisplayAsBarCode(boolean enableDisplayAsBarCode) {

        controlObject.setEnableDisplayAsBarCode(enableDisplayAsBarCode);
    }

    public boolean isEnableDisplayAsQRCode() {
        return controlObject.isEnableDisplayAsQRCode();
    }

    public void setEnableDisplayAsQRCode(boolean enableDisplayAsQRCode) {
        controlObject.setEnableDisplayAsQRCode(enableDisplayAsQRCode);
    }

    public boolean isEnableUnicode() {
        return controlObject.isEnableUnicode();
    }

    public void setEnableUnicode(boolean enableUnicode) {
        controlObject.setEnableUnicode(enableUnicode);
    }

    public String getUnicodeFormat() {
        return controlObject.getUnicodeFormat();
    }

    public void setUnicodeFormat(String unicodeFormat) {

        controlObject.setUnicodeFormat(unicodeFormat);
    }

    public String getUnicodeFormatId() {
        return controlObject.getUnicodeFormatId();
    }

    public void setUnicodeFormatId(String unicodeFormatId) {
        controlObject.setUnicodeFormatId(unicodeFormatId);
    }

    public boolean isEnableMaskCharacters() {
        return controlObject.isEnableMaskCharacters();
    }

    public void setEnableMaskCharacters(boolean enableMaskCharacters) {
        controlObject.setEnableMaskCharacters(enableMaskCharacters);


    }

    public String getMaskCharacterType() {
        return controlObject.getMaskCharacterType();
    }

    public void setMaskCharacterType(String maskCharacterType) {
        controlObject.setMaskCharacterType(maskCharacterType);
        maskCharactersPartialOrFull();
    }

    public String getNoOfCharactersToMask() {
        return controlObject.getNoOfCharactersToMask();
    }

    public void setNoOfCharactersToMask(String noOfCharactersToMask) {
        controlObject.setNoOfCharactersToMask(noOfCharactersToMask);
    }

    public String getMaskCharacterDirection() {
        return controlObject.getMaskCharacterDirection();
    }

    public void setMaskCharacterDirection(String maskCharacterDirection) {
        controlObject.setMaskCharacterDirection(maskCharacterDirection);
        maskCharactersPartialOrFull();
    }

    public boolean getStrikeText() {
        return controlObject.getStrikeText();
    }

    public void setStrikeText(boolean strikeText) {
        controlObject.setStrikeText(strikeText);
        tv_dynamicLabel.setPaintFlags(tv_dynamicLabel.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public void setUnderLineText(boolean strikeText) {
        controlObject.setUnderLineText(strikeText);
        underLineText(controlObject.getValue()); // UnderLine Text
    }

    public boolean isLayoutBackGroundEnable() {
        return controlObject.isLayoutBackGroundEnable();
    }

    public void setLayoutBackGroundEnable(boolean layoutBackGroundEnable) {
        controlObject.setLayoutBackGroundEnable(layoutBackGroundEnable);
    }

    public String getLayoutBackGroundColor() {
        return controlObject.getLayoutBackGroundColor();
    }

    public void setLayoutBackGroundColor(String layoutBackGroundColor) {
        controlObject.setLayoutBackGroundColor(layoutBackGroundColor);
        if (controlObject.isHtmlViewerEnabled()) {
            ll_editor.setBackgroundColor(Color.parseColor(layoutBackGroundColor));
        } else {
            sectionLayout.setBackgroundColor(Color.parseColor(layoutBackGroundColor));
        }
    }

    /*Mask required Characters*/
    private static String maskString(String strText, int start, int end, char maskChar) throws Exception {

        if (strText == null || strText.equals("")) return "";

        if (start < 0) start = 0;

        if (end > strText.length()) end = strText.length();

        if (start > end) throw new Exception("End index cannot be greater than start index");

        int maskLength = end - start;

        if (maskLength == 0) return strText;

        StringBuilder sbMaskString = new StringBuilder(maskLength);

        for (int i = 0; i < maskLength; i++) {
            sbMaskString.append(maskChar);
        }

        return strText.substring(0, start) + sbMaskString + strText.substring(start + maskLength);
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    /*ControlUI SettingsLayouts Start */
    public LinearLayout getLl_control_ui() {
        return ll_control_ui;
    }

    public LinearLayout getLayout_control() {
        return layout_control;
    }

    public LinearLayout getSectionLayout() {
        return sectionLayout;
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

    public CustomTextView getValueView() {
        return tv_dynamicLabel;
    }

    /*ControlUI SettingsLayouts End*/
    public ControlObject getControlObject() {
        return controlObject;
    }

    /* Masking Characters except given input number  */
    public String maskStringFL(int anInt, String input, boolean isFirstChars) {
        String firstFourChars = "";
        if (input.equalsIgnoreCase("") || input.isEmpty() || input.length() < anInt) {
            return "";
        }
        int length = input.length();
        StringBuilder maskedString = new StringBuilder();
        if (isFirstChars) { // Masking except first anInt chars

            if (length <= anInt) {
                return input; // No characters to mask
            }
            maskedString.append(input, 0, anInt); // Append the first characters

            for (int i = anInt; i < length; i++) {
                maskedString.append('*'); // Masking character
            }
        } else { // Masking except Last anInt chars
            for (int i = 0; i < length - anInt; i++) {
                maskedString.append('*'); // Masking character
            }
            maskedString.append(input.substring(length - anInt)); // Append the last characters
        }


        return maskedString.toString();
    }

    public String maskStringFLOld(int anInt, String input, boolean isFirstChars) {
        String firstFourChars = "";
        if (input.equalsIgnoreCase("") || input.isEmpty() || input.length() < anInt) {
            return "";
        }
        if (isFirstChars) {
            firstFourChars = input.substring(0, anInt);
        } else {
            firstFourChars = input.substring(input.length() - anInt);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < anInt; i++) {
            sb.append("*");
        }
        String newStr = input.replaceFirst(firstFourChars, sb.toString());
        return newStr;
    }


    public void maskCharactersPartialOrFull() {

        if (controlObject.getMaskCharacterType() != null) {
            if (controlObject.getMaskCharacterType().equalsIgnoreCase("Partial")) {
                if (controlObject.getNoOfCharactersToMask() != null && !controlObject.getNoOfCharactersToMask().isEmpty()) {
                    int noOfMaskChars = Integer.parseInt(controlObject.getNoOfCharactersToMask());
                    if (controlObject.getMaskCharacterDirection() != null && !controlObject.getMaskCharacterDirection().isEmpty()) {
                        if (controlObject.getMaskCharacterDirection().equalsIgnoreCase("First")) {
                            strDynamicText = maskStringFL(noOfMaskChars, controlObject.getValue(), true);
                        } else {
                            strDynamicText = maskStringFL(noOfMaskChars, controlObject.getValue(), false);
                        }
                        tv_dynamicLabel.setText(strDynamicText);
                    }
                }
            } else if (controlObject.getMaskCharacterType().equalsIgnoreCase("Full")) {
                tv_dynamicLabel.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            underLineText(strDynamicText);
        }
    }

    /* UnderLine Text*/
    public void underLineText(String textToUnderLine) {
        if (controlObject.getUnderLineText()) {
//            Log.d(TAG, "underLineTextHC: "+controlObject.getTextHexColor());
//            Log.d(TAG, "underLineTextHC: "+strUnderLineHexColor);
//            tv_dynamicLabel.setText(Html.fromHtml("<font color="+getUnderLineTextColor()+">  <u>" + textToUnderLine + "</u>  </font>"));
            SpannableString mySpannableString = new SpannableString(textToUnderLine);
            mySpannableString.setSpan(new UnderlineSpan(), 0, mySpannableString.length(), 0);
            tv_dynamicLabel.setText(mySpannableString);
        }

    }

    public void setUnderLineTextColor(String color){
        strUnderLineHexColor = color;
    }

public String getUnderLineTextColor(){
    return strUnderLineHexColor;
}
}