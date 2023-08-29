package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.core.content.ContextCompat;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.TimeVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Time implements UIVariables, TimeVariables {

    private static final String TAG = "TimeControl";
    public View rView;
    public CustomEditText ce_TextType, et_timeDisplayTwo;
    ImproveHelper improveHelper;
    Context context;
    LinearLayout linearLayout,ll_tap_text,ll_main_inside,ll_displayName,ll_control_ui,layout_control,ll_leftRightWeight;
    ControlObject controlObject;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName, str_12_or_24Hrs = null;
    String strTimeFormat = "12:00 Hrs";
    String strTimeFormatOptions = "hh:mm:ss a";
    private CustomTextView tv_displayName, tv_hint, ct_alertTypeText,tv_tapTextType,ct_showText;
    private ImageView iv_mandatory, iv_textTypeImage;


    public Time(Context context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {

        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        improveHelper = new ImproveHelper(context);
        initView();

    }

    private void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);

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

            ll_tap_text = rView.findViewById(R.id.ll_tap_text);
            tv_tapTextType = rView.findViewById(R.id.tv_tapTextType);
            ll_main_inside = rView.findViewById(R.id.ll_main_inside);
            tv_displayName = rView.findViewById(R.id.tv_displayName);
            ll_displayName = rView.findViewById(R.id.ll_displayName);
            ll_control_ui = rView.findViewById(R.id.ll_control_ui);
            layout_control = rView.findViewById(R.id.layout_control);
            ll_leftRightWeight = rView.findViewById(R.id.ll_leftRightWeight);
            tv_hint = rView.findViewById(R.id.tv_hint);
            ce_TextType = rView.findViewById(R.id.ce_TextType);
            ce_TextType.setEnabled(false);
            iv_textTypeImage = rView.findViewById(R.id.iv_textTypeImage);

            ct_alertTypeText = rView.findViewById(R.id.ct_alertTypeText);
            iv_mandatory = rView.findViewById(R.id.iv_mandatory);
            ct_showText = rView.findViewById(R.id.ct_showText);

            tv_tapTextType.setVisibility(View.GONE);
            iv_textTypeImage.setVisibility(View.VISIBLE);
            iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_icon_time));
            setControlValues(rView);

            iv_textTypeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ct_alertTypeText.setVisibility(View.GONE);
                    timePicker(ce_TextType, strTimeFormatOptions);
                }
            });
            ce_TextType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ct_alertTypeText.setVisibility(View.GONE);
                    timePicker(ce_TextType, strTimeFormatOptions);
                }
            });

            ce_TextType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus){
                        improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),false,ll_tap_text,rView);
                    }else{
                        improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),true,ll_tap_text,rView);
                    }
                }
            });




            linearLayout.addView(rView);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initView", e);
        }
    }

    private void setControlValues(View rView) {
        try {
            if (controlObject.getDisplayNameAlignment() != null) {

                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {
                    TextInputLayout til_TextType = rView.findViewById(R.id.til_TextType);
                    til_TextType.setHint(controlObject.getDisplayName());
                    ce_TextType.setText("");
                    setSelectedTime("");
                    ce_TextType.setEnabled(true);
                    tv_displayName.setVisibility(View.GONE);
                    iv_textTypeImage.setVisibility(View.GONE);


                }
            }
            if (controlObject != null) {
                if (controlObject.getDisplayName() != null && !controlObject.getDisplayName().equalsIgnoreCase("")) {
                    tv_displayName.setText(controlObject.getDisplayName());
                }
                if (controlObject.getHint() != null && !controlObject.getHint().equalsIgnoreCase("")) {
                    tv_hint.setText(controlObject.getHint());
                }else{
                    tv_hint.setVisibility(View.GONE);
                }

                if (controlObject.isHideDisplayName()) {
                    if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()
                            && controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {

                        TextInputLayout til_task_name = rView.findViewById(R.id.til_task_name);
                        til_task_name.setHint("");
                        tv_hint.setVisibility(View.GONE);
                    } else {
                        ll_displayName.setVisibility(View.GONE);
                    }
                }
                if (controlObject.isTimeFormat()) {
                    strTimeFormat = controlObject.getTimeFormat();
                }
                if (controlObject.isMandatoryTime()) {
                    iv_mandatory.setVisibility(View.VISIBLE);
                }
                if (controlObject.isInvisible()) {
                    setVisibility(false);
                }
                strTimeFormatOptions = controlObject.getTimeFormatOptions();
                if (controlObject.isCurrentTime()) {
                    iv_textTypeImage.setVisibility(View.GONE);
                    ce_TextType.setVisibility(View.VISIBLE);
                    ce_TextType.setText(ImproveHelper.getCurrentTime(strTimeFormatOptions));
                    setSelectedTime(ImproveHelper.getCurrentTime(strTimeFormatOptions));
                }

//            if (controlObject.isTimeFormatOptions()) {

//            }
                if (controlObject.isBeforeCurrentTime()) {
                    iv_textTypeImage.setVisibility(View.VISIBLE);
                    is24HoursFormat();
//                ct_alertTypeTextOne.setText(controlObject.getBeforeCurrentTimeErrorMessage());
                }

                if (controlObject.isAfterCurrentTime()) {
                    iv_textTypeImage.setVisibility(View.VISIBLE);
                    is24HoursFormat();
//                ct_alertTypeTextOne.setText(controlObject.getAfterCurrentTimeErrorMessage());
                }

                if (controlObject.isBetweenStartEndTime()) {
                    is24HoursFormat();
//                ct_alertTypeTextOne.setText(controlObject.getBetweenStartEndTimeErrorMessage());
                }

                if (controlObject.getMandatoryTimeErrorMessage() != null && !controlObject.getMandatoryTimeErrorMessage().isEmpty()) {
                    ct_alertTypeText.setText(controlObject.getMandatoryTimeErrorMessage());
                }
                if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().isEmpty()) {
                    ce_TextType.setText(controlObject.getDefaultValue());
                    setSelectedTime(controlObject.getDefaultValue());
                }

                setTags();
            }
            if (controlObject.isReadOnly()) {
                improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),false,ll_tap_text,rView);
            }

            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    public boolean is24HoursFormat() {

        boolean is24Hours = false;
        try {
            if (controlObject != null) {
                if (controlObject.getTimeFormat() != null && !controlObject.getTimeFormat().equalsIgnoreCase("")) {
                    if (controlObject.getTimeFormat().equalsIgnoreCase("24:00 Hrs")) {
                        is24Hours = true;
                    }
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "is24HoursFormat", e);
        }
        return is24Hours;
    }

    public LinearLayout getTimeControlLayout() {
        return linearLayout;
    }

    private void setTags() {
        ll_tap_text.setTag(controlObject.getControlName());
        iv_textTypeImage.setTag(controlObject.getControlName());
    }

    public CustomTextView getTimeErrorMessageText() {
        return ct_alertTypeText;
    }

    public CustomEditText getEt_timeDisplayOne() {
        return ce_TextType;
    }


    public String getEditTextTimeValue() { // edittext with get text

        return ce_TextType.getText().toString();

    }


    public void timePicker(CustomEditText customEditText, String strTimeFormatOptions) {
        try {
            ce_TextType.setVisibility(View.VISIBLE);
            int mYear, mMonth, mDay, mHour, mMinute, mSeconds;
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            boolean is24Hours = is24HoursFormat();
            Log.d(TAG, "timePickerTimeFormats: " + strTimeFormatOptions + " - " + is24Hours);
            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(context,R.style.DatePickerDialog,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            cal.set(Calendar.MINUTE, minute);

//                        Format formatter = new SimpleDateFormat("hh:mm:ss a");
                            Format formatter = null;
                            if (is24Hours) {
                                formatter = new SimpleDateFormat(strTimeFormatOptions.replaceAll("hh", "HH"), Locale.getDefault());
                            } else {
                                formatter = new SimpleDateFormat(strTimeFormatOptions, Locale.getDefault());
                            }
//                        String strFormatTime = String.format("%02d:%02d", hourOfDay, minute);
//                        customEditText.setText(dateString+" "+strFormatTime);
                            customEditText.getText().clear();
                            tv_tapTextType.setVisibility(View.GONE);
                            customEditText.setText(formatter.format(cal.getTime()));
                            setSelectedTime(formatter.format(cal.getTime()));
//                            improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),true,ll_tap_text,rView);


                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                if (customEditText.getText().toString().trim().length() > 0) {
                                    if (AppConstants.EventCallsFrom == 1) {
                                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                                        if (SubformFlag) {
                                            AppConstants.SF_Container_position = SubformPos;
                                            AppConstants.Current_ClickorChangeTagName = SubformName;

                                        }

                                        AppConstants.GlobalObjects.setCurrent_GPS("");
                                        ((MainActivity) context).TextChange(customEditText);
                                    }
                                }
                            }
                        }
                    }, mHour, mMinute, is24Hours);

            timePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Apply", timePickerDialog);
            timePickerDialog.show();


        } catch (Exception e) {
            Log.d(TAG, "timePickerException: "+e.toString());
            improveHelper.improveException(context, TAG, "timePicker", e);
        }
    }

    public void setTimeData(String strDate) {
        ce_TextType.setVisibility(View.VISIBLE);
        ce_TextType.setText(strDate);
        setSelectedTime(strDate);
    }

    @Override
    public String getSelectedTime() {
        return controlObject.getSelectedTime();
    }

    @Override
    public void setSelectedTime(String time) {
        ce_TextType.setText(time);
        controlObject.setSelectedTime(time);
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

    /*Validations*/
    /*mandatory,mandatoryErrorMessage,
    betweenStartEndTime,betweenStartTime,betweenEndTime,betweenStartEndTimeErrorMessage,
    beforeCurrentTime,beforeCurrentTimeErrorMessage,afterCurrentTime,afterCurrentTimeErrorMessage*/
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

    public boolean isBetweenStartEndTime() {
        return controlObject.isBetweenStartEndTime();
    }

    public void setBetweenStartEndTime(boolean betweenStartEndTime) {
        controlObject.setBetweenStartEndTime(betweenStartEndTime);
    }

    public String getBetweenStartTime() {
        return controlObject.getBetweenStartTime();
    }

    public void setBetweenStartTime(String betweenStartTime) {
        controlObject.setBetweenStartTime(betweenStartTime);
    }

    public String getBetweenEndTime() {
        return controlObject.getBetweenEndTime();
    }

    public void setBetweenEndTime(String betweenEndTime) {
        controlObject.setBetweenEndTime(betweenEndTime);
    }
    public String getBetweenStartEndTimeErrorMessage() {
        return controlObject.getBetweenStartEndTimeErrorMessage();
    }

    public void setBetweenStartEndTimeErrorMessage(String betweenStartEndTimeErrorMessage) {
        controlObject.setBetweenStartEndTimeErrorMessage(betweenStartEndTimeErrorMessage);
    }

    public boolean isBeforeCurrentTime() {
        return controlObject.isBeforeCurrentTime();
    }

    public void setBeforeCurrentTime(boolean beforeCurrentTime) {
        controlObject.setBeforeCurrentTime(beforeCurrentTime);
    }

    public String getBeforeCurrentTimeErrorMessage() {
        return controlObject.getBeforeCurrentTimeErrorMessage();
    }

    public void setBeforeCurrentTimeErrorMessage(String beforeCurrentTimeErrorMessage) {
        controlObject.setBeforeCurrentTimeErrorMessage(beforeCurrentTimeErrorMessage);
    }

    public boolean isEnableAfterCurrentDate() {
        return controlObject.isEnableAfterCurrentDate();
    }

    public void setAfterCurrentTime(boolean afterCurrentTime) {
        controlObject.setAfterCurrentTime(afterCurrentTime);
    }

    public String getAfterCurrentTimeErrorMessage() {
        return controlObject.getAfterCurrentTimeErrorMessage();
    }

    public void setAfterCurrentTimeErrorMessage(String afterCurrentTimeErrorMessage) {
        controlObject.setAfterCurrentTimeErrorMessage(afterCurrentTimeErrorMessage);
    }

    /*Options*/
    /*hideDisplayName,currentTime,timeFormat,strTimeFormat,timeFormatOptions,strTimeFormatOptions
    invisible/visibility,disable/enabled
     */
    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        if(hideDisplayName){
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()
                    && controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {

                TextInputLayout til_task_name = rView.findViewById(R.id.til_task_name);
                til_task_name.setHint("");
                tv_hint.setVisibility(View.GONE);
            } else {
                ll_displayName.setVisibility(View.GONE);
            }
        }
    }

    public boolean isCurrentTime() {
        return controlObject.isCurrentTime();
    }

    public void setCurrentTime(boolean currentTime) {
//        if(currentTime){
            iv_textTypeImage.setVisibility(View.GONE);
            ce_TextType.setVisibility(View.VISIBLE);
            if(is24HoursFormat()){
                setSelectedTime(ImproveHelper.getCurrentTime(strTimeFormatOptions.replaceAll("hh", "HH")));
            }else{
                setSelectedTime(ImproveHelper.getCurrentTime(strTimeFormatOptions));
            }
            controlObject.setCurrentTime(currentTime);
//        }
    }

    public boolean isTimeFormat() {
        return controlObject.isTimeFormat();
    }

    public void setTimeFormat(boolean timeFormat) {
        controlObject.setTimeFormat(timeFormat);
        if(timeFormat){
            strTimeFormat = controlObject.getTimeFormat();
        }
    }

    public String getTimeFormat() {
        return controlObject.getTimeFormat();
    }

    public void setTimeFormat(String strTimeFormat) {
        if(strTimeFormat.equalsIgnoreCase("24:00 Hrs") || strTimeFormat.equalsIgnoreCase("1")){
            strTimeFormat = "24:00 Hrs";
        }else{
            strTimeFormat = "12:00 Hrs";
        }
        controlObject.setTimeFormat(strTimeFormat);
//        is24HoursFormat();
    }

    public boolean isTimeFormatOptions() {
        return controlObject.isTimeFormatOptions();
    }

    public void setTimeFormatOptions(boolean timeFormatOptions) {
        controlObject.setTimeFormatOptions(timeFormatOptions);
//        if(timeFormatOptions){
//            strTimeFormatOptions = controlObject.getTimeFormatOptions();
//        }
    }

    public String getTimeFormatOptions() {
        return controlObject.getTimeFormatOptions();
    }

    public void setTimeFormatOptions(String strTimeFormatOptionsVal) {
//        if(strTimeFormatOptionsVal.equalsIgnoreCase("0")){
//            strTimeFormatOptionsVal = "hh:mm";
//        }else if(strTimeFormatOptionsVal.equalsIgnoreCase("1")){
//            strTimeFormatOptionsVal = "hh:mm a";
//        }else if(strTimeFormatOptionsVal.equalsIgnoreCase("2")){
//            strTimeFormatOptionsVal = "hh:mm:ss";
//        }else if(strTimeFormatOptionsVal.equalsIgnoreCase("3")){
//            strTimeFormatOptionsVal = "hh:mm:ss a";
//        }
        controlObject.setTimeFormatOptions(strTimeFormatOptionsVal);
        strTimeFormatOptions = strTimeFormatOptionsVal;
        if(controlObject.isCurrentTime()) {
            setCurrentTime(true);
        }
    }
    public LinearLayout getLl_tap_text() { // linear for taptext

        return ll_tap_text;
    }
    public CustomTextView getTv_tapTextType() {

        return tv_tapTextType;
    }

    public CustomEditText getCe_TextType() {

        return ce_TextType;
    }

    /*ControlUI SettingsLayouts Start */
    public LinearLayout getLl_control_ui() {
        return ll_control_ui;
    }
    public LinearLayout getLl_main_inside() {
        return ll_main_inside;
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
    public CustomEditText getCustomEditText() { // only edit text
        return ce_TextType;
    }

    public void clear() {
        try {
            ce_TextType.setText("");
            controlObject.setText("");
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "Clear", e);
        }

    }
    public void setDefaultValue(String defaultValue) {
        setSelectedTime(defaultValue);
        controlObject.setDefaultValue(defaultValue);
    }


    /*ControlUI SettingsLayouts End*/
    public ControlObject getControlObject() {
        return controlObject;
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

}
