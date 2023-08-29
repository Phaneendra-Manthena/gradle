package com.bhargo.user.controls.standard;
/*Done*/

import static androidx.core.app.ShareCompat.getCallingActivity;
import static com.bhargo.user.utils.AppConstants.MATCH_PARENT;
import static com.bhargo.user.utils.AppConstants.REQUEST_CURRENT_LOCATION;
import static com.bhargo.user.utils.AppConstants.REQUEST_SPEECH_TO_TEXT;
import static com.bhargo.user.utils.AppConstants.SECTION_REQUEST_CURRENT_LOCATION;
import static com.bhargo.user.utils.AppConstants.SECTION_REQUEST_SPEECH_TO_TEXT;
import static com.bhargo.user.utils.AppConstants.SF_REQUEST_CURRENT_LOCATION;
import static com.bhargo.user.utils.AppConstants.SF_REQUEST_SPEECH_TO_TEXT;
import static com.bhargo.user.utils.ImproveHelper.pxToDPControlUI;
import static com.bhargo.user.utils.ImproveHelper.pxToDp;
import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;
import static com.bhargo.user.utils.ImproveHelper.showSoftKeyBoard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.bhargo.user.BuildConfig;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.adapters.GooglePlacesAutocompleteAdapter;
import com.bhargo.user.controls.variables.TextVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.AutoPlaceModel;
import com.bhargo.user.uisettings.pojos.ControlUIProperties;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.GPSActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextInput implements TextVariables, UIVariables {

    private static final String TAG = "TextInput";
    public static boolean isUI = false; // false is for defaultView
    public CustomEditText ce_TextType;
    public int visable;
    Activity context;
    ControlObject controlObject;
    LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    Typeface typeface;
    ImageView iv_mandatory, iv_textTypeImage, iv_locationIcon;
    LinearLayout ll_googlePlacesSearch, ll_tap_text_with_icon, ll_main_inside, ll_control_ui,layout_control,ll_leftRightWeight;
    AutoCompleteTextView autoCompleteTextView;
    InputFilter letterFilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (isCharAllowed(c)) // put your condition here
                    sb.append(c);
                else keepOriginal = false;
            }
            if (keepOriginal) return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }

        private boolean isCharAllowed(char c) {
            Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
            Matcher ms = ps.matcher(String.valueOf(c));
            return ms.matches();
        }
    };
    private final AdapterView.OnItemClickListener autocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            try {

                AutoPlaceModel item = (AutoPlaceModel) adapterView.getItemAtPosition(i);
                Log.d("Selected Location :", item.getPlaceName());

                if (!item.getPlaceName().equalsIgnoreCase("Footer")) {
                    autoCompleteTextView.setText(item.getPlaceName());
                    ce_TextType.setText(item.getPlaceName());
                    setTextValue(item.getPlaceName());
                } else {
                    autoCompleteTextView.setText("");
                    setTextValue("");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };
    ImproveHelper improveHelper;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName, strUIDisplayName;
    GooglePlacesAutocompleteAdapter adapter;
    PlacesClient placesClient;
    private CustomTextView tv_displayName, tv_hint, tv_tapTextType, ct_alertTypeTextInput,ct_showText;
    public LinearLayout linearLayout, ll_tap_text;
    //    private LinearLayout  ll_isEnable;
    public IntentIntegrator qr_bar_Scanner;
    private View rView;
    private LinearLayout ll_label;
    LinearLayout ll_displayName;
    private String apiKey;
    ControlUIProperties controlUIProperties;

    public TextInput(Activity context, String strUIDisplayName) {
        this.strUIDisplayName = strUIDisplayName;
    }

    public TextInput(Activity context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        improveHelper = new ImproveHelper(context);
//        typeface = Typeface.createFromAsset(context.getAssets(), "Poppins-Regular.otf");
        typeface = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_satoshi));

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

                }
            } else {

                rView = linflater.inflate(R.layout.layout_text_input_default, null);
            }

            autoCompleteTextView = rView.findViewById(R.id.auto);
            ll_main_inside = rView.findViewById(R.id.ll_main_inside);
            ll_control_ui = rView.findViewById(R.id.ll_control_ui);
            layout_control = rView.findViewById(R.id.layout_control);
            ll_leftRightWeight = rView.findViewById(R.id.ll_leftRightWeight);
            ll_tap_text_with_icon = rView.findViewById(R.id.ll_tap_text);
            ll_googlePlacesSearch = rView.findViewById(R.id.ll_googlePlacesSearch);
            ll_tap_text = rView.findViewById(R.id.ll_tap_text);
            tv_displayName = rView.findViewById(R.id.tv_displayName);
            if (strUIDisplayName != null && !strUIDisplayName.equalsIgnoreCase("")) {
                tv_displayName.setText(strUIDisplayName);
            }
            tv_hint = rView.findViewById(R.id.tv_hint);
            iv_mandatory = rView.findViewById(R.id.iv_mandatory);
            iv_textTypeImage = rView.findViewById(R.id.iv_textTypeImage);
            tv_tapTextType = rView.findViewById(R.id.tv_tapTextType);
            ce_TextType = rView.findViewById(R.id.ce_TextType);
            ct_alertTypeTextInput = rView.findViewById(R.id.ct_alertTypeText);
            ll_label = rView.findViewById(R.id.ll_label);
            ll_displayName = rView.findViewById(R.id.ll_displayName);
            ct_showText = rView.findViewById(R.id.ct_showText);

            ce_TextType.setInputType(InputType.TYPE_CLASS_TEXT);

            ll_tap_text.setVisibility(View.VISIBLE);

            setTvTapText(View.VISIBLE);

            /*Set Tags*/
            ll_main_inside.setTag(controlObject.getControlName());
            ll_control_ui.setTag(controlObject.getControlName());
            layout_control.setTag(controlObject.getControlName());
            ll_tap_text.setTag(controlObject.getControlName());
            tv_tapTextType.setTag(controlObject.getControlName());
            ce_TextType.setTag(controlObject.getControlName());
            iv_textTypeImage.setTag(controlObject.getControlName());


            ce_TextType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {

                    if (!hasFocus) {

                            // your code here
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
                                ((MainActivity) context).FocusExist(view);
                            }
                        }
                    }

                        if (ll_tap_text.isEnabled()) {
                            improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),hasFocus,ll_tap_text,rView);
//                        ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.control_active_background));
                        }

                }
            });
            tv_tapTextType.setText(R.string.tap_to_enter_text);
            tv_tapTextType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setfocus();
                }
            });
            setfocus();

//For Google Location Search
            apiKey = BuildConfig.API_KEY;

            /**
             * Initialize Places. For simplicity, the API key is hard-coded. In a production
             * environment we recommend using a secure mechanism to manage API keys.
             */

// Create a new Places client instance.
            if (apiKey.isEmpty()) {
                tv_tapTextType.setText(context.getString(R.string.error));
                return;
            }
            setControlValues();

            ce_TextType.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence cs, int i, int i1, int count) {
//                    if (ce_TextType.getText() != null && ce_TextType.getText().toString().trim().length() > 0) {
//                        controlObject.setText(""+cs);
//                    }else{
//                        controlObject.setText(null);
//                    }
                    if (cs.length() > 0) {
                        controlObject.setText("" + cs);
                    } else {
                        controlObject.setText(null);
                    }
                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag && (controlObject.getOnChangeEventObject().getActionWithOutConditionList().size() > 0 || controlObject.getOnChangeEventObject().getActionWithConditionList().size() > 0)) {
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
                        ct_alertTypeTextInput.setText("");
                        ct_alertTypeTextInput.setVisibility(View.GONE);
                    }
                }
            });
//            Validations Inprogress
//            ce_TextType.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence cs, int i, int i1, int count) {
//                    if(controlObject.isNullAllowed()) {
//                        if (controlObject.isEnableMaxCharacters() && cs.length() >= Integer.parseInt(controlObject.getMaxCharacters())) {
//                            ct_alertTypeTextInput.setVisibility(visable);
//                            ct_alertTypeTextInput.setText("Max character limit is " + String.valueOf(controlObject.getMaxCharacters()));
//                        } else if (controlObject.isEnableMinCharacters() && cs.length() < Integer.parseInt(controlObject.getMinCharacters())) {
//                            errorAlertTextColor();
//                        } else if (controlObject.isEnableMinCharacters() && cs.length() > Integer.parseInt(controlObject.getMinCharacters())) {
//                            controlObject.setText("" + cs);
//                        }else if(!controlObject.isEnableMinCharacters() || !controlObject.isEnableMaxCharacters() && cs.length() > 0){
//                            controlObject.setText("" + cs);
//                        }else{
//                            errorAlertTextColor();
//                        }
//                    }else if (ce_TextType.getText() != null && ce_TextType.getText().toString().trim().length() > 0) {
//                        controlObject.setText(""+cs);
//                    }
//                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag && (controlObject.getOnChangeEventObject().getActionWithOutConditionList().size() > 0 || controlObject.getOnChangeEventObject().getActionWithConditionList().size() > 0)) {
//                        if (ce_TextType.getText().toString().trim().length() > 0) {
//                            if (AppConstants.EventCallsFrom == 1) {
//                                AppConstants.EventFrom_subformOrNot = SubformFlag;
//                                if (SubformFlag) {
//                                    AppConstants.SF_Container_position = SubformPos;
//                                    AppConstants.Current_ClickorChangeTagName = SubformName;
//
//                                }
//
//                                AppConstants.GlobalObjects.setCurrent_GPS("");
//                                ((MainActivity) context).TextChange(ce_TextType);
//                            }
//                        }
//                    }
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                    if (!controlObject.isEnableMinCharacters() && !controlObject.isEnableMaxCharacters() && ce_TextType.getText().toString().trim().length() > 0) {
//                        ct_alertTypeTextInput.setText("");
//                        ct_alertTypeTextInput.setVisibility(View.GONE);
//                    }
//                }
//            });

/*          //

            ce_TextType.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence cs, int i, int i1, int i2) {

                    if (ce_TextType.getText() != null && ce_TextType.getText().toString().trim().length() > 0) {
                        controlObject.setText(""+cs);
                    }
                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag && (controlObject.getOnChangeEventObject().getActionWithOutConditionList().size() > 0 || controlObject.getOnChangeEventObject().getActionWithConditionList().size() > 0)) {
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
                        ct_alertTypeTextInput.setText("");
                        ct_alertTypeTextInput.setVisibility(View.GONE);
                    }
                }
            });*/

          /*  if(controlUIProperties != null){

                if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                    String strWidthType = controlUIProperties.getTypeOfWidth();
                    String strHeightType = controlUIProperties.getTypeOfHeight();
                    String strControlName = controlObject.getControlName();


                    android.view.ViewGroup.LayoutParams lpCtrlUI = ll_control_ui.getLayoutParams();
                    android.view.ViewGroup.LayoutParams lpLCtrl = layout_control.getLayoutParams();
                    android.view.ViewGroup.LayoutParams lpTapText = ll_tap_text.getLayoutParams();
                    android.view.ViewGroup.LayoutParams editText = ce_TextType.getLayoutParams();

                    if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {

                        lpCtrlUI.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                        lpCtrlUI.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                        lpLCtrl.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                        lpLCtrl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                        lpTapText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                        lpTapText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                        editText.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                        editText.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                    }else if(strWidthType.equalsIgnoreCase(AppConstants.MATCH_PARENT) && strHeightType.equalsIgnoreCase(AppConstants.MATCH_PARENT)) {
                        lpCtrlUI.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        lpCtrlUI.height = ViewGroup.LayoutParams.MATCH_PARENT;

                        lpLCtrl.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        lpLCtrl.height = ViewGroup.LayoutParams.MATCH_PARENT;

                        int heightFontMinus = AppConstants.uiLayoutPropertiesStatic.getLayoutHeightInPixel() - improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getFontSize()));
//                        int heightMatchParent = heightFontMinus-improveHelper.dpToPx(Integer.parseInt("20"));

                        lpTapText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        lpTapText.height = heightFontMinus;

                        editText.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        editText.height = heightFontMinus;

                    }
                    ll_control_ui.setLayoutParams(lpCtrlUI);
                    layout_control.setLayoutParams(lpLCtrl);
                    ll_tap_text.setLayoutParams(lpTapText);
                    ce_TextType.setLayoutParams(editText);
                }

                if(controlUIProperties.getFontSize() != null && !controlUIProperties.getFontSize().isEmpty()) {
                    tv_displayName.setTextSize(Float.parseFloat(controlUIProperties.getFontSize()));
                }
                if(controlUIProperties.getFontColorHex() != null && !controlUIProperties.getFontColorHex().isEmpty()) {
                    tv_displayName.setTextColor(Color.parseColor(controlUIProperties.getFontColorHex()));
                }
                if(controlUIProperties.getFontStyle() != null && !controlUIProperties.getFontStyle().isEmpty()) {
                    String style = controlUIProperties.getFontStyle();
                    if (style != null && style.equalsIgnoreCase("Bold")) {
                        Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_bold));
                        tv_displayName.setTypeface(typeface_bold);

                    } else if (style != null && style.equalsIgnoreCase("Italic")) {
                        Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_italic));
                        tv_displayName.setTypeface(typeface_italic);
                    }
                }
            }*/


            linearLayout.addView(rView);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initViews", e);
        }
    }
//    public void initView() {
//
//        linearLayout = new LinearLayout(context);
//        linearLayout.setTag(controlObject.getControlName());
////        Log.d("initViewTextInput: ", controlObject.getControlName());
//        qr_bar_Scanner = new IntentIntegrator(context);
//        ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
//        linearLayout.setLayoutParams(ImproveHelper.layout_params);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//
//        final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////        rView = linflater.inflate(R.layout.control_text_input_hint, null);
////        rView = linflater.inflate(R.layout.control_text_input_left, null);
//        rView = linflater.inflate(R.layout.control_text_input_left_iv_left, null);
//
//        ll_isEnable = rView.findViewById(R.id.ll_isEnable);
//        autoCompleteTextView = rView.findViewById(R.id.auto);
//        ll_tap_text_with_icon = rView.findViewById(R.id.ll_tap_text_with_icon);
//        ll_googlePlacesSearch = rView.findViewById(R.id.ll_googlePlacesSearch);
//        ll_tap_text = rView.findViewById(R.id.ll_tap_text);
//        tv_displayName = rView.findViewById(R.id.tv_displayName);
//        if (strUIDisplayName != null && !strUIDisplayName.equalsIgnoreCase("")) {
//            tv_displayName.setText(strUIDisplayName);
//
//
//
//        }
//        tv_hint = rView.findViewById(R.id.tv_hint);
//        iv_mandatory = rView.findViewById(R.id.iv_mandatory);
//        iv_textTypeImage = rView.findViewById(R.id.iv_textTypeImage);
//        tv_tapTextType = rView.findViewById(R.id.tv_tapTextType);
//        ce_TextType = rView.findViewById(R.id.ce_TextType);
//        ct_alertTypeTextInput = rView.findViewById(R.id.ct_alertTypeText);
//        ce_TextType.setInputType(InputType.TYPE_CLASS_TEXT);
//
//        ll_tap_text.setVisibility(View.VISIBLE);
////        tv_tapTextType.setVisibility(View.VISIBLE);
//
//        /*Set Tags*/
//        ll_tap_text.setTag(controlObject.getControlName());
//        tv_tapTextType.setTag(controlObject.getControlName());
//        ce_TextType.setTag(controlObject.getControlName());
//        iv_textTypeImage.setTag(controlObject.getControlName());
//
//        ce_TextType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//
//                if (!hasFocus) {
//                    if (ce_TextType.getText().toString().isEmpty()) {
//                        ll_tap_text.setVisibility(View.VISIBLE);
//                        ce_TextType.setVisibility(View.VISIBLE);
////                        tv_tapTextType.setVisibility(View.VISIBLE);
//                        if (controlObject.isGridControl()) {
//                            tv_tapTextType.setVisibility(View.GONE);
//                        }
//                    }
//
//                    if (controlObject.isOnFocusEventExists() && !AppConstants.Initialize_Flag) {
//                        if (AppConstants.EventCallsFrom == 1) {
//                            AppConstants.EventFrom_subformOrNot = SubformFlag;
//                            if (SubformFlag) {
//                                AppConstants.SF_Container_position = SubformPos;
//                                AppConstants.Current_ClickorChangeTagName = SubformName;
//
//                            }
//                            AppConstants.GlobalObjects.setCurrent_GPS("");
//                            ((MainActivity) context).FocusExist(view);
//                        }
//                    }
//                }
//            }
//        });
//
//        ce_TextType.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence cs, int i, int i1, int i2) {
//                if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
//                    if (ce_TextType.getText().toString().trim().length() > 0) {
//                        if (AppConstants.EventCallsFrom == 1) {
//                            AppConstants.EventFrom_subformOrNot = SubformFlag;
//                            if (SubformFlag) {
//                                AppConstants.SF_Container_position = SubformPos;
//                                AppConstants.Current_ClickorChangeTagName = SubformName;
//
//                            }
//
//                            AppConstants.GlobalObjects.setCurrent_GPS("");
//                            ((MainActivity) context).TextChange(ce_TextType);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (ce_TextType.getText().toString().trim().length() > 0) {
//                    ct_alertTypeTextInput.setText("");
//                    ct_alertTypeTextInput.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        tv_tapTextType.setText(R.string.tap_to_enter_text);
//        tv_tapTextType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                setfocus();
//            }
//        });
//
//
////For Google Location Search
//////
//        String apiKey = context.getString(R.string.google_android_places_api_key);
//
//        /**
//         * Initialize Places. For simplicity, the API key is hard-coded. In a production
//         * environment we recommend using a secure mechanism to manage API keys.
//         */
//       /* if (!Places.isInitialized()) {
//            Places.initialize(context, apiKey);
//        }*/
//
//// Create a new Places client instance.
//        if (apiKey.isEmpty()) {
//            tv_tapTextType.setText(context.getString(R.string.error));
//            return;
//        }
//
//        // Setup Places Client
//        if (!Places.isInitialized()) {
//            Places.initialize(context, apiKey);
//        }
//
//        placesClient = Places.createClient(context);
//
//        setControlValues();
//
//
//
//        linearLayout.addView(rView);
//
//    }

    /*private void setUiProperties() {

        ll_isEnable.setBackground(context.getDrawable(R.drawable.rounded_corners));
        ll_isEnable.setBackground(context.getDrawable(R.drawable.rectangle_box_divider_white));

        if ("left".equalsIgnoreCase("left")) {
            tv_displayName.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1);
            ll_tap_text.setLayoutParams(layoutParams);
            LinearLayout.LayoutParams layoutParams_displayName = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,0.4f);
            tv_tapTextType.setLayoutParams(layoutParams_displayName);
            LinearLayout.LayoutParams layoutParams_edittext = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,0.6f);
            ce_TextType.setLayoutParams(layoutParams_edittext);

        }

    }*/

    public void setfocus() {
        try {
            tv_tapTextType.setVisibility(View.GONE);
            if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().isEmpty()) {
                ce_TextType.setText(controlObject.getDefaultValue());
                setTextValue(controlObject.getDefaultValue());
            }if (controlObject.isGoogleLocationSearch()) {
                callAutoCmplete();
            } else {
                ce_TextType.setVisibility(View.VISIBLE);
            }


            showSoftKeyBoard(context, ce_TextType);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setfocus", e);
        }
    }

    public String getTextInputValue() { // edittext with get text
        String strToReturn = null;
        try {
            if (controlObject.isGoogleLocationSearch()) {
                strToReturn = autoCompleteTextView.getText().toString();
            } else {
                strToReturn = ce_TextType.getText().toString();
            }
            Log.d(TAG, "getTextInputValue: " + controlObject.getControlName());
            Log.d(TAG, "getTextInputValue: " + strToReturn);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getTextInputValue", e);
        }
        setTextValue(strToReturn);
        return strToReturn;
    }

    public CustomTextView setAlertTextInput() { // error alert

        return ct_alertTypeTextInput;
    }

    public LinearLayout getTextInputView() { // whole linear layout

        return linearLayout;
    }

    public LinearLayout getLl_tap_text() { // linear for taptext

        return ll_tap_text;
    }

    public CustomTextView gettap_text() { // taptext
//        CustomTextView view = new CustomTextView(context);
//        try {
//            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
//
//            } else {
//                view = tv_tapTextType;
//
//            }
//        } catch (Exception e) {
//            improveHelper.improveException(context, TAG, "gettap_text", e);
//        }
        return tv_tapTextType;
//        return view;
    }
/*
    public CustomTextView gettap_text() { // taptext
        CustomTextView view = new CustomTextView(context);
        try {
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {

            } else {
                view = tv_tapTextType;

            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "gettap_text", e);
        }
//        return tv_tapTextType;
        return view;
    }
*/

    public CustomEditText getCustomEditText() { // only edit text
        Log.d(TAG, "getCustomEditText: " + ce_TextType.getText().toString());
        return ce_TextType;
    }

    public ImageView getIv_textTypeImage() { // ImageView for icon

        return iv_textTypeImage;
    }

    public void setControlValues() {

        try {

            if (controlObject.isGoogleLocationSearch()) {
                if (!Places.isInitialized()) {


                    Places.initialize(context, apiKey);

                }

                placesClient = Places.createClient(context);
            }

  /*if (controlObject.getDisplayNameAlignment() != null
                && controlObject.getDisplayNameAlignment().equalsIgnoreCase(context.getString(R.string.top))
        && !controlObject.isGoogleLocationSearch()) {
            ll_googlePlacesSearch.setVisibility(View.VISIBLE);
            isGoogleSearchEnabledWithUI();
        } else*/
            if (controlObject.getDisplayNameAlignment() != null) {
                tv_tapTextType.setVisibility(View.GONE);
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {
                    TextInputLayout til_TextType = rView.findViewById(R.id.til_TextType);
                    til_TextType.setHint(controlObject.getDisplayName());
                    TextInputLayout til_task_name = rView.findViewById(R.id.til_task_name);
                    if (controlObject.isGoogleLocationSearch()) {
                        TextInputLayout til_act = rView.findViewById(R.id.til_act);
                        isGoogleSearchEnabledWithUI();
                        til_act.setHint(controlObject.getDisplayName());
//                    til_act.setHint("Search");
                        autoCompleteTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_google_location_search, 0);
                        autoCompleteTextView.setHint("");
                    } else {
                        if (ce_TextType.getText() != null) {
                            ce_TextType.getText().clear();
                        }

                    }
                    tv_displayName.setVisibility(View.GONE);
                    iv_textTypeImage.setVisibility(View.GONE);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {
                    if (controlObject.isGoogleLocationSearch()) {
                        isGoogleSearchEnabledWithUI();
                        autoCompleteTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_google_location_search, 0);
//                    autoCompleteTextView.setHint(controlObject.getDisplayName());
                    } else {

//                    ce_TextType.setHint(controlObject.getDisplayName());

                    }
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {
                    if (controlObject.isGoogleLocationSearch()) {
                        isGoogleSearchEnabledWithUI();
                        autoCompleteTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_google_location_search, 0);
//                    autoCompleteTextView.setHint(controlObject.getDisplayName());
                    } else {

//                    ce_TextType.setText(controlObject.getDisplayName());
//                    ce_TextType.setHint(controlObject.getDisplayName());
                    }
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("4")) {
                    if (controlObject.isGoogleLocationSearch()) {
                        isGoogleSearchEnabledWithUI();
//                    autoCompleteTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_current_location,0);
//                    autoCompleteTextView.setHint(controlObject.getDisplayName());
                    } else {

//                    ce_TextType.setText(controlObject.getDisplayName());
//                        ce_TextType.setHint(controlObject.getDisplayName());
                    }
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("5")) {
                    if (controlObject.isGoogleLocationSearch()) {
                        isGoogleSearchEnabledWithUI();
                        autoCompleteTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_google_location_search, 0);
//                    autoCompleteTextView.setHint(controlObject.getDisplayName());
                    } else {

//                    ce_TextType.setText(controlObject.getDisplayName());
//                        ce_TextType.setHint(controlObject.getDisplayName());
                    }
                }
            }

            if (controlObject.getIconAlignment() != null && !controlObject.getIconAlignment().isEmpty()) {

                Log.d("setControlValuesDN", controlObject.getIconAlignment());
            }


            if (controlObject.getDisplayName() != null && !isUI) {
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
            if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().isEmpty()) {
                ce_TextType.setText(controlObject.getDefaultValue());
                setTextValue(controlObject.getDefaultValue());
                if (controlObject.isGoogleLocationSearch()) {
                    setfocus();
                }
            }
            if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().isEmpty() && !isUI) {
                tv_tapTextType.setVisibility(View.GONE);
                iv_textTypeImage.setVisibility(View.GONE);
                ce_TextType.setVisibility(View.VISIBLE);
                ce_TextType.setText(controlObject.getDefaultValue());
                setTextValue(controlObject.getDefaultValue());
            }

            if (controlObject.getControlValue() != null && !isUI) {
                tv_tapTextType.setVisibility(View.GONE);
                ce_TextType.setText(controlObject.getControlValue());
                setTextValue(controlObject.getControlValue());
            }

            if (controlObject.isDisable() || controlObject.isReadOnly()) {

//                setViewDisable(rView, !controlObject.isDisable());
//                setViewDisableOrEnableDefault(context, ll_main_inside, false);
                if (controlObject.isGoogleLocationSearch()) {
                    improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), !controlObject.isDisable(), ll_googlePlacesSearch, rView);
                }else {
                    improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), !controlObject.isDisable(), ll_tap_text, rView);
                }
            }/*else{
                improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),!controlObject.isDisable(),ll_tap_text,rView);
            }*/

            if (controlObject.isInvisible()) {
                setVisibility(false);
            } else {
                setVisibility(true);
            }

            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            } else {
                ll_displayName.setVisibility(View.VISIBLE);
            }
            setDefaultValueForSearch();
//            errorAlertTextColor();
           /* if(controlObject.isEnableMinCharacters() && controlObject.isEnableMaxCharacters()){
                String strMinMax = "Min "+controlObject.getMinCharacters() + " character required , "+"Max "+controlObject.getMaxCharacters() + " character required.";
                errorAlertTextColor(strMinMax);
            }else if(controlObject.isEnableMinCharacters()){
                errorAlertTextColor("Minimum "+controlObject.getMinCharacters() + " character required.");
            }else if(controlObject.isEnableMaxCharacters()){
                errorAlertTextColor("Maximum "+controlObject.getMaxCharacters() + " character required.");
            }else{
                ct_alertTypeTextInput.setTextColor(null);
                ct_alertTypeTextInput.setVisibility(View.GONE);
            }*/

            if (controlObject.isAllowOnlyAlphabets()) {
                ce_TextType.setFilters(new InputFilter[]{letterFilter});
            }

            if (controlObject.isEnableMaxCharacters()) {
                ce_TextType.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.parseInt(controlObject.getMaxCharacters()))});
            }


            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
            if (AppConstants.DefultAPK_OrgID.equalsIgnoreCase("SELE20210719175221829") && controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("4")) {
                if (iv_textTypeImage.getVisibility() == View.VISIBLE) {
                    iv_textTypeImage.setVisibility(View.GONE);
                }
            } else {
                iv_textTypeImage.setVisibility(View.VISIBLE);
            }
            if (controlObject.isEnableMaskCharacters()) {
                ce_TextType.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }

            Log.d(TAG, "setControlValues: " + controlObject.getControlName() + ce_TextType.getText().toString());
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }

    }


    public void setDefaultValueForSearch() {
        try {
            if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().isEmpty()) {
                if(controlObject.isGoogleLocationSearch()) {
                    tv_tapTextType.setVisibility(View.GONE);

                    if (AppConstants.DefultAPK_OrgID.equalsIgnoreCase("SELE20210719175221829") && controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("4")) {
                        if (iv_textTypeImage.getVisibility() == View.VISIBLE) {
                            iv_textTypeImage.setVisibility(View.GONE);
                        }
                    } else {
                        iv_textTypeImage.setVisibility(View.VISIBLE);
                    }
                    ce_TextType.setVisibility(View.GONE);
                    autoCompleteTextView.setVisibility(View.VISIBLE);
                    autoCompleteTextView.setText(controlObject.getDefaultValue());
                }

            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setDefaultValueForSearch", e);
        }
    }

    public CustomTextView getTapTextType() {
        return tv_tapTextType;
    }

    public void getSpeechInput(boolean isDefaultValues, int requestCode) {
        try {
//        if (isDefaultValues) {
//            tv_tapTextType.setVisibility(View.GONE);
//        } else {
//            tv_tapTextType.setVisibility(View.GONE);
//        }
            tv_tapTextType.setVisibility(View.GONE);
            ce_TextType.setVisibility(View.VISIBLE);
//        ce_TextType.setEnabled(false);




            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");
//            if (intent.resolveActivity(context.getPackageManager()) != null) {
//            if (context.getCallingActivity() != null && context.getCallingActivity().getPackageName().equals("known")) {
                context.startActivityForResult(intent, requestCode);
//            }else{
//                Toast.makeText(context, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
//            }
//            } else {
//                Toast.makeText(context, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
//            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getSpeechInput", e);
        }
    }

    public void setLocationRetry(String lat, String lng) {
        try {
//        tv_tapTextType.setVisibility(View.VISIBLE);
            setTvTapText(View.VISIBLE);
            iv_textTypeImage.setVisibility(View.VISIBLE);
            iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_retry_black_24dp));
            iv_textTypeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                    if(controlObject.isSectionControl()){
                        tCurrentLocation(SECTION_REQUEST_CURRENT_LOCATION);
                    } else if(SubformFlag) {
                        context.startActivityForResult(new Intent(context, GPSActivity.class), SF_REQUEST_CURRENT_LOCATION);
                    }else{
                        context.startActivityForResult(new Intent(context, GPSActivity.class), REQUEST_CURRENT_LOCATION);
                    }
                }
            });

            tv_tapTextType.setText(lat + "," + lng);
            ce_TextType.setText(lat + "," + lng);
            setTextValue(lat + "," + lng);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setLocationRetry", e);
        }
    }

    public void setQRorBartext(String text) {
        try {
            Log.d(TAG, "setQRorBartextCheck: "+text);
        tv_tapTextType.setVisibility(View.VISIBLE);
//            setTvTapText(View.VISIBLE);
            iv_textTypeImage.setVisibility(View.VISIBLE);
            tv_tapTextType.setText(text);
            ce_TextType.setText(text);
            setTextValue(text);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setQRorBartext", e);
        }
    }

    public void googleSearchPlaces(View rView) {

        // Initialize the AutocompleteSupportFragment.
       /* AppCompatActivity activity = (AppCompatActivity) rView.getContext();
        autocompleteFragment = (AutocompleteSupportFragment)
                activity.getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment1);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
//        autocompleteFragment.setPlaceFields( Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                tv_tapTextType.setText(place.getName());
            }

            @Override
            public void onError(Status status) {
                //  Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });*/

    }

    public void Clear() {
        try {
            setTextValue("");
            tv_tapTextType.setVisibility(View.VISIBLE);
            setTvTapText(View.VISIBLE);
            ce_TextType.setVisibility(View.GONE);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                tv_tapTextType.setVisibility(View.GONE);
                ce_TextType.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "Clear", e);
        }
    }

    public void callAutoCmplete() {
        try {
            initAutoCompleteTextView();
            ll_tap_text_with_icon.setVisibility(View.GONE);
            ll_googlePlacesSearch.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "callAutoCmplete", e);
        }
    }

    /*Read QR and Bar Code*/
    public void tQRBarCode() {
        qr_bar_Scanner.initiateScan();
    }

    public void tCurrentLocation(int location) { //REQUEST_CURRENT_LOCATION,SF_REQUEST_CURRENT_LOCATION
        try {
            context.startActivityForResult(new Intent(context, GPSActivity.class), location);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "tCurrentLocation", e);
        }
    }

    public void initAutoCompleteTextView() {
        try {
            autoCompleteTextView.setThreshold(1);
            adapter = new GooglePlacesAutocompleteAdapter(context, R.layout.row_google_place);
            autoCompleteTextView.setAdapter(adapter);
            autoCompleteTextView.setOnItemClickListener(autocompleteClickListener);
            autoCompleteTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length() > 0){
                        controlObject.setText(""+s);
                    }else{
                        controlObject.setText(null);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initAutoCompleteTextView", e);
        }
    }

    public void uiBackGroundType(ControlObject controlObject, View rView) {
        LinearLayout ll_label = rView.findViewById(R.id.ll_label);
        ll_label.setVisibility(View.GONE);
        if (controlObject.getBackgroundType() != null) {

            if (controlObject.getBackgroundType().equalsIgnoreCase(context.getString(R.string.rectangle))) {
                if (controlObject.isGoogleLocationSearch()) {
                    ll_googlePlacesSearch.setBackground(context.getResources().getDrawable(R.drawable.rectangle_border_ui));

                    googleSearchEnabledWithUI(ll_label);
                } else {
                    ll_tap_text.setBackground(context.getResources().getDrawable(R.drawable.rectangle_border_ui));
                    ce_TextType.setBackgroundColor(context.getResources().getColor(R.color.transparent_color));
                    ce_TextType.setHint(controlObject.getDisplayName());
                }
            } else if (controlObject.getBackgroundType().equalsIgnoreCase(context.getString(R.string.rounded_rectangle))) {
                if (controlObject.isGoogleLocationSearch()) {
                    ll_googlePlacesSearch.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_border_ui));

                    googleSearchEnabledWithUI(ll_label);
                } else {
                    ll_tap_text.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_border_ui));
                    ce_TextType.setBackgroundColor(context.getResources().getColor(R.color.transparent_color));
                    ce_TextType.setHint(controlObject.getDisplayName());
                }

            } else if (controlObject.getBackgroundType().equalsIgnoreCase(context.getString(R.string.rectangle_rounded_corners))) {
                if (controlObject.isGoogleLocationSearch()) {
                    ll_googlePlacesSearch.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_ui));
                    googleSearchEnabledWithUI(ll_label);
                } else {
                    ll_tap_text.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_ui));
                    ce_TextType.setBackgroundColor(context.getResources().getColor(R.color.transparent_color));
                    ce_TextType.setHint(controlObject.getDisplayName());
                }
            }

        }
        if (controlObject.getIconAlignment() != null) {
            if (controlObject.getIconAlignment().equalsIgnoreCase(context.getString(R.string.icon_drawable_left))) {
                ce_TextType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_black_24dp, 0, 0, 0);
//                ce_TextType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_black_24dp, 0, 0, 0);
            } else if (controlObject.getIconAlignment().equalsIgnoreCase(context.getString(R.string.icon_drawable_right))) {
                ce_TextType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_person_black_24dp, 0);
//                ce_TextType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.exo_notification_small_icon, 0);
            }
            ce_TextType.setHint(controlObject.getDisplayName());
        }

    }


    public void googleSearchEnabledWithUI(LinearLayout ll_label) {
        try {
            ll_tap_text.setVisibility(View.GONE);
            ce_TextType.setVisibility(View.GONE);
            ll_label.setVisibility(View.GONE);
            tv_hint.setVisibility(View.GONE);
            tv_tapTextType.setVisibility(View.GONE);
            ll_tap_text_with_icon.setVisibility(View.GONE);
            autoCompleteTextView.setBackgroundColor(context.getResources().getColor(R.color.transparent_color));
            ll_googlePlacesSearch.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "googleSearchEnabledWithUI", e);
        }
    }

    public void isGoogleSearchEnabledWithUI() {
        try {
            setfocus();
            ll_tap_text.setVisibility(View.GONE);
            ce_TextType.setVisibility(View.GONE);
            if (ll_displayName != null && ll_displayName.getVisibility() == View.VISIBLE) {
                ll_displayName.setVisibility(View.GONE);
            }
            tv_tapTextType.setVisibility(View.GONE);
            tv_hint.setVisibility(View.GONE);
            ll_tap_text_with_icon.setVisibility(View.GONE);
            ll_googlePlacesSearch.setVisibility(View.VISIBLE);
            if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("4")) {
                iv_locationIcon = rView.findViewById(R.id.iv_locationIcon);
                iv_locationIcon.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "isGoogleSearchEnabledWithUI", e);
        }
    }

    public void setTvTapText(int visibility) {
        try {
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                tv_tapTextType.setVisibility(View.GONE);
            } else {
                tv_tapTextType.setVisibility(visibility);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setTvTapText", e);
        }
    }

    public LinearLayout getLl_main_inside() {
        return ll_main_inside;
    }


    @Override
    public String getTextValue() {
        return ce_TextType.getText().toString().trim();
    }

    @Override
    public void setTextValue(String value) {
        controlObject.setText(value);
        ce_TextType.setText(value);
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

    public void requestFocus(){
                    ce_TextType.requestFocus();
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

    /*DisplaySettings*/
    /*size,style,color*/

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

    /*Validations*/
    /*mandatory,mandatoryErrorMessage,uniqueField,uniqueFieldErrorMessage*/
    public boolean isMandatory() {
        return controlObject.isNullAllowed();
    }

    public void setMandatory(boolean mandatory) {
        ct_alertTypeTextInput.setVisibility(mandatory == true ? View.VISIBLE : View.GONE);
        controlObject.setNullAllowed(mandatory);
    }

    public String getMandatoryErrorMessage() {
        return controlObject.getMandatoryFieldError();
    }

    public void setMandatoryErrorMessage(String mandatoryErrorMessage) {
        ct_alertTypeTextInput.setText(mandatoryErrorMessage);
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

    /*General*/
    /*displayName,hint,defaultValue*/

    public String getDisplayName() {
        return controlObject.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        tv_displayName.setText(displayName);
        controlObject.setDisplayName(displayName);
//        MainActivity.getterPropertyValues(controlObject.getDisplayName(), PropertiesNames.DISPLAY_NAME);
    }

    public String getHint() {
        return controlObject.getHint();
    }

    public void setHint(String hint) {
        if (hint != null && !hint.isEmpty()) {
            tv_hint.setVisibility(View.VISIBLE);
            tv_hint.setText(hint);
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

    /*Options*/
    /*hideDisplayName,readFromBarCode,readFromQRCode,googleLocationSearch,voiceText,currentLocation,
    invisible/visibility,disable/enabled,enableMaskCharacters
     */
    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        ll_displayName.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
        controlObject.setHideDisplayName(hideDisplayName);
    }

    public boolean isReadFromBarcode() {
        return controlObject.isReadFromBarcode();
    }

    public void setReadFromBarcode(boolean readFromBarcode) {
        if (readFromBarcode) {
            getLl_tap_text().setVisibility(View.VISIBLE);
            gettap_text().setVisibility(View.VISIBLE);
            gettap_text().setText(R.string.tap_here_to_scan_bar_code);
            getIv_textTypeImage().setVisibility(View.VISIBLE);
            getIv_textTypeImage().setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_bar_code));

            gettap_text().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SubformFlag) {
                        AppConstants.SF_Container_position = SubformPos;
                        AppConstants.Current_ClickorChangeTagName = SubformName;
                    }else{
                        AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                    }
                    tQRBarCode();
                }
            });
            getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SubformFlag) {
                        AppConstants.SF_Container_position = SubformPos;
                        AppConstants.Current_ClickorChangeTagName = SubformName;
                    }else{
                        AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                    }
                    tQRBarCode();
                }
            });
        } else {
            getLl_tap_text().setVisibility(View.GONE);
            gettap_text().setVisibility(View.GONE);
            getIv_textTypeImage().setVisibility(View.GONE);
        }


        controlObject.setReadFromBarcode(readFromBarcode);
    }

    public boolean isReadFromQRCode() {
        return controlObject.isReadFromQRCode();
    }

    public void setReadFromQRCode(boolean readFromQRCode) {
        controlObject.setReadFromQRCode(readFromQRCode);
        if (readFromQRCode) {
            getLl_tap_text().setVisibility(View.VISIBLE);
            gettap_text().setVisibility(View.VISIBLE);
            gettap_text().setText(R.string.tap_here_to_scan_qr_code);
            getIv_textTypeImage().setVisibility(View.VISIBLE);
            getIv_textTypeImage().setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_qr_code));
//            gettap_text().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                    tQRBarCode();
//                }
//            });
//            getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                    tQRBarCode();
//                }
//            });
        } else {
            getLl_tap_text().setVisibility(View.GONE);
            gettap_text().setVisibility(View.GONE);
            getIv_textTypeImage().setVisibility(View.GONE);
        }

    }

    public boolean isGoogleLocationSearch() {
        return controlObject.isGoogleLocationSearch();
    }

    public void setGoogleLocationSearch(boolean googleLocationSearch) {
        if (googleLocationSearch) {
            getLl_tap_text().setVisibility(View.VISIBLE);
            gettap_text().setVisibility(View.VISIBLE);
            gettap_text().setText("Tap here to Search Location");
            getIv_textTypeImage().setVisibility(View.VISIBLE);
            getIv_textTypeImage().setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_google_location_search));

            setDefaultValueForSearch();

         /*   getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                    initAutoCompleteTextView();

                }
            });*/
        } else {
            getLl_tap_text().setVisibility(View.GONE);
            gettap_text().setVisibility(View.GONE);
            getIv_textTypeImage().setVisibility(View.GONE);
        }

        controlObject.setGoogleLocationSearch(googleLocationSearch);
    }

    public boolean isVoiceText() {
        return controlObject.isVoiceText();
    }

    public void setVoiceText(boolean voiceText) {
        if (voiceText) {
            getLl_tap_text().setVisibility(View.VISIBLE);
            gettap_text().setVisibility(View.VISIBLE);
            gettap_text().setText("Tap here voice to text");
            getIv_textTypeImage().setVisibility(View.VISIBLE);
            getIv_textTypeImage().setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_voice_input));
            getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                    if(controlObject.isSectionControl()){
                        getSpeechInput(false, SECTION_REQUEST_SPEECH_TO_TEXT);
                    }else if (SubformFlag) {
                        getSpeechInput(false, SF_REQUEST_SPEECH_TO_TEXT);
                    } else {
                        getSpeechInput(false, REQUEST_SPEECH_TO_TEXT);
                    }

                }
            });
            gettap_text().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                    if(controlObject.isSectionControl()){
                        getSpeechInput(false, SECTION_REQUEST_SPEECH_TO_TEXT);
                    }else if (SubformFlag) {
                        getSpeechInput(false, SF_REQUEST_SPEECH_TO_TEXT);
                    } else {
                        getSpeechInput(false, REQUEST_SPEECH_TO_TEXT);
                    }
                }
            });
        } else {
            getLl_tap_text().setVisibility(View.GONE);
            gettap_text().setVisibility(View.GONE);
            getIv_textTypeImage().setVisibility(View.GONE);
        }
        controlObject.setGoogleLocationSearch(voiceText);
    }

    public boolean isCurrentLocation() {
        return controlObject.isCurrentLocation();
    }

    public void setCurrentLocation(boolean currentLocation) {
        controlObject.setCurrentLocation(currentLocation);
        getIv_textTypeImage().setVisibility(View.VISIBLE);
        getIv_textTypeImage().setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_current_location));
        if(controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().isEmpty()){
            gettap_text().setVisibility(View.GONE);
        }else{
            getLl_tap_text().setVisibility(View.VISIBLE);
            gettap_text().setVisibility(View.VISIBLE);
            gettap_text().setText("Tap here to get Current Location");
        }
        if (currentLocation) {
            getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                    if(controlObject.isSectionControl()){
                        tCurrentLocation(SECTION_REQUEST_CURRENT_LOCATION);
                    }else if (SubformFlag) {
                        AppConstants.SF_Container_position = SubformPos;
                        AppConstants.Current_ClickorChangeTagName = SubformName;
                        tCurrentLocation(SF_REQUEST_CURRENT_LOCATION);
                    } else {
                        tCurrentLocation(REQUEST_CURRENT_LOCATION);
                    }
                    }

            });
            gettap_text().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                    if(controlObject.isSectionControl()){
                        tCurrentLocation(SECTION_REQUEST_CURRENT_LOCATION);
                    }else if (SubformFlag) {
                        AppConstants.SF_Container_position = SubformPos;
                        AppConstants.Current_ClickorChangeTagName = SubformName;
                        tCurrentLocation(SF_REQUEST_CURRENT_LOCATION);
                        } else {
                            tCurrentLocation(REQUEST_CURRENT_LOCATION);
                        }
                }
            });

        } else {
            getLl_tap_text().setVisibility(View.GONE);
            gettap_text().setVisibility(View.GONE);
            getIv_textTypeImage().setVisibility(View.GONE);
        }
//        controlObject.setCurrentLocation(currentLocation);
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
        controlObject.setDisable(!enabled);
//        setViewDisable(rView, !enabled);
//        setViewDisableOrEnableDefault(context, rView, enabled);
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),enabled,ll_tap_text,rView);
    }

    public boolean isEnableMaskCharacters() {
        return controlObject.isEnableMaskCharacters();
    }

    public void setEnableMaskCharacters(boolean enableMaskCharacters) {
        if (enableMaskCharacters) {
            if(tv_tapTextType != null && tv_tapTextType.getVisibility() ==  View.VISIBLE){

                tv_tapTextType.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
                ce_TextType.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        }else {
            ce_TextType.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        controlObject.setEnableMaskCharacters(enableMaskCharacters);
    }

    public void setEditValue(String value) {
        if (controlObject.isGoogleLocationSearch()) {
            autoCompleteTextView.setText(value);
            controlObject.setText(value);
            setfocus();
        } else {
//            getCustomEditText().setText(value);
            setTextValue(value);
        }
    }

    /*ControlUI SettingsLayouts Start */
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
    /*ControlUI SettingsLayouts End*/


    public ControlObject getControlObject(){
        return controlObject;
    }

    public LinearLayout getLl_googlePlacesSearch(){
        return ll_googlePlacesSearch;
    }

}
