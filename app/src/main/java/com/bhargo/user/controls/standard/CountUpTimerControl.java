package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bhargo.user.Expression.ExpressionMainHelper;
import com.bhargo.user.Java_Beans.AssignControl_Bean;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.TimerVaiables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;

import java.util.Timer;
import java.util.TimerTask;

public class CountUpTimerControl implements TimerVaiables, UIVariables {

    private final Activity context;
    private final ControlObject controlObject;
    private final boolean subFormFlag;
    private final int subFormPos;
    private final String subFormName;
    CustomTextView tv_hr, tv_min, tv_sec, ctv_dots1, ctv_dots2;
    LinearLayout ll_displayName, ll_main_inside,ll_control_ui,ll_tap_text;
    LinearLayout ll_countuptimer_main;
    View rView;
    String strTimerFormatOptions = "hh:mm:ss";
    Timer timerup;
    TimerTask timerTask;
    long donetime = 0;
    long giventime = 0;
    boolean timerStartedStatus = false;
    boolean withaction_timerstart = true;
    String secondsEditText, minutesEditText, hoursEditText;
    private LinearLayout linearLayout;
    private CustomTextView tv_displayName, tv_hint,ct_showText;

    public CountUpTimerControl(Activity context, ControlObject controlObject, boolean subFormFlag, int subFormPos, String subFormName) {
        this.context = context;
        this.controlObject = controlObject;
        this.subFormFlag = subFormFlag;
        this.subFormPos = subFormPos;
        this.subFormName = subFormName;
        initViews();
    }

    private void initViews() {

        linearLayout = new LinearLayout(context);
        linearLayout.setTag(controlObject.getControlName());
        ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
        linearLayout.setLayoutParams(ImproveHelper.layout_params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        View rView = lInflater.inflate(R.layout.control_chart, null);
        if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
            if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                rView = lInflater.inflate(R.layout.layout_countuptimer_progress, null);
            } else {
                rView = lInflater.inflate(R.layout.control_countuptimer_default, null);
            }
        } else {
//            rView = lInflater.inflate(R.layout.control_countuptimer, null);
            rView = lInflater.inflate(R.layout.control_countuptimer_default, null);
        }
        //bts

        ll_main_inside = rView.findViewById(R.id.ll_main_inside);
        ll_control_ui = rView.findViewById(R.id.ll_control_ui);
        ll_tap_text = rView.findViewById(R.id.ll_tap_text);
        ll_displayName = rView.findViewById(R.id.ll_displayName);
        tv_displayName = rView.findViewById(R.id.tv_displayName);
        tv_hint = rView.findViewById(R.id.tv_hint);

        ll_countuptimer_main = rView.findViewById(R.id.ll_countuptimer_main);
        tv_hr = rView.findViewById(R.id.tv_hr);
        tv_min = rView.findViewById(R.id.tv_min);
        tv_sec = rView.findViewById(R.id.tv_sec);

        ctv_dots1 = rView.findViewById(R.id.ctv_dots1);
        ctv_dots2 = rView.findViewById(R.id.ctv_dots2);
        ct_showText = rView.findViewById(R.id.ct_showText);


        linearLayout.addView(rView);
        setControlValues();

    }

    private void setControlValues() {
        timerup = new Timer();
        tv_displayName.setText(controlObject.getDisplayName());
        if (controlObject.getHint() == null || controlObject.getHint().contentEquals("")) {
            tv_hint.setVisibility(View.GONE);
        } else {
            tv_hint.setVisibility(View.VISIBLE);
            tv_hint.setText(controlObject.getHint());
        }

        if (controlObject.isHideDisplayName()) {
            tv_displayName.setVisibility(View.GONE);
            tv_hint.setVisibility(View.GONE);
        }
        //layout
        if (controlObject.isHideDisplayName()) {
            ll_displayName.setVisibility(View.GONE);
        } else {
            ll_displayName.setVisibility(View.VISIBLE);
        }

        if (controlObject.isInvisible()) {
            ll_main_inside.setVisibility(View.GONE);
        } else {
            ll_main_inside.setVisibility(View.VISIBLE);
        }

        strTimerFormatOptions = controlObject.getTimerFormatOptions();

        if (strTimerFormatOptions.equals("ss")) {
            tv_hr.setVisibility(View.GONE);
            ctv_dots1.setVisibility(View.GONE);
            tv_min.setVisibility(View.GONE);
            ctv_dots2.setVisibility(View.GONE);
        } else if (strTimerFormatOptions.equals("mm:ss")) {
            tv_hr.setVisibility(View.GONE);
            ctv_dots1.setVisibility(View.GONE);
        } else {
            //hh:mm:ss

        }



       /* if (controlObject.getTimerColorHex() != null && !controlObject.getTimerColorHex().equals("")) {
            tv_hr.setTextColor(ColorStateList.valueOf(Color.parseColor(controlObject.getProgressColorHex())));
            tv_min.setTextColor(ColorStateList.valueOf(Color.parseColor(controlObject.getProgressColorHex())));
            tv_sec.setTextColor(ColorStateList.valueOf(Color.parseColor(controlObject.getProgressColorHex())));
        }*/

        if (controlObject.getTimer_sec() != null && !controlObject.getTimer_sec().equals("")) {
            secondsEditText = controlObject.getTimer_sec();
        } else {
            secondsEditText = "0";
        }
        if (controlObject.getTimer_min() != null && !controlObject.getTimer_min().equals("")) {
            minutesEditText = controlObject.getTimer_min();
        } else {
            minutesEditText = "0";
        }
        if (controlObject.getTimer_hr() != null && !controlObject.getTimer_hr().equals("")) {
            hoursEditText = controlObject.getTimer_hr();
        } else {
            hoursEditText = "0";
        }
        if (controlObject.isTimerAutoStart()) {
            withaction_timerstart = false;
            timerStartedStatus = true;

            startTimerUp();
        }

        setDisplaySettings(context, tv_displayName, controlObject);
        setTextSize(controlObject.getTextSize());
        setTextColor(controlObject.getTextHexColor());
        setTextStyle(controlObject.getTextStyle());
        if(controlObject.getTimerColorHex()!=null){
            String color=controlObject.getTimerColorHex();
            tv_hr.setTextColor(Color.parseColor(color));
            tv_min.setTextColor(Color.parseColor(color));
            tv_sec.setTextColor(Color.parseColor(color));
            ctv_dots1.setTextColor(Color.parseColor(color));
            ctv_dots2.setTextColor(Color.parseColor(color));
        }
    }


    private void startTimerUp() {
        onClickEventBeforeTimerStart();
        giventime += Integer.parseInt(secondsEditText);
        giventime += Integer.parseInt(minutesEditText) * 60;
        giventime += Integer.parseInt(hoursEditText) * 60 * 60;

        donetime = donetime + giventime;
        startCountupTimer();
    }

    private void startCountupTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        donetime++;
                        updateTimeDone();
                    }
                });
            }

        };
        timerup.scheduleAtFixedRate(timerTask, 0, 1000);
        // stopTimerup("Count up Stop");
    }


    private void updateTimeDone() {
        long totalmillsec = donetime;
        int rounded = Math.round(totalmillsec);
        int secondsDone = ((rounded % 86400) % 3600) % 60;
        int minutesDone = ((rounded % 86400) % 3600) / 60;
        int hoursDone = ((rounded % 86400) / 3600);
        if (strTimerFormatOptions.equals("ss")) {
            tv_sec.setText(String.format("%02d", totalmillsec));
            setTimerValue("0:" + "0:" + tv_sec.getText().toString().trim());
        } else if (strTimerFormatOptions.equals("mm:ss")) {
            minutesDone = ((rounded % 86400)) / 60;
            tv_min.setText(String.format("%02d", minutesDone));
            tv_sec.setText(String.format("%02d", secondsDone));
            setTimerValue("0:" + tv_min.getText().toString().trim() + ":" + tv_sec.getText().toString().trim());

        } else {
            tv_hr.setText(String.format("%02d", hoursDone));
            tv_min.setText(String.format("%02d", minutesDone));
            tv_sec.setText(String.format("%02d", secondsDone));
            setTimerValue(tv_hr.getText().toString().trim() + ":" + tv_min.getText().toString().trim() + ":" + tv_sec.getText().toString().trim());
        }

    }

    private void onClickEventBeforeTimerStart() {
        ll_countuptimer_main.setTag(controlObject.getControlName());
        if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
            if (AppConstants.EventCallsFrom == 1) {
                AppConstants.EventFrom_subformOrNot = subFormFlag;
                if (subFormFlag) {
                    AppConstants.SF_Container_position = subFormPos;
                    AppConstants.Current_ClickorChangeTagName = subFormName;

                }
                AppConstants.GlobalObjects.setCurrent_GPS("");
                ((MainActivity) context).ClickEvent(ll_countuptimer_main);
            }
        }
    }


    public void startTimer(String timerOption) {
        if (timerOption.equals("Start")) {
            if (withaction_timerstart) {
                ImproveHelper.showToast(context, "Timer Starting...");
                withaction_timerstart = false;
                timerStartedStatus = true;

                startTimerUp();
            } else {
                if (timerStartedStatus) {
                    ImproveHelper.showToast(context, "Timer Running...");
                } else {
                    ImproveHelper.showToast(context, "Timer Stopped!");
                }

            }
        } else if (timerOption.equals("Pause")) {
            if (timerStartedStatus) {
                timerStartedStatus = false;
                //pause.setText("Resume");
                timerTask.cancel();
                ImproveHelper.showToast(context, "Count Up Timer Paused!");
            } else {
                timerStartedStatus = true;
                //pause.setText("Pause");
                ImproveHelper.showToast(context, "Count Up Timer Resumed!");
                startCountupTimer();//time

            }
        } else if (timerOption.equals("Stop")) {
            timerTask.cancel();
            timerStartedStatus = false;
            ImproveHelper.showToast(context, "Count Up Timer Cancelled!");
            //stopTimerup("Count up cancelled");
        }


    }


    public void setControlValues(AssignControl_Bean assignControl_bean_) {

        ExpressionMainHelper ehelper = new ExpressionMainHelper();

        if (assignControl_bean_.isTwoValue_expression1()) {
            hoursEditText = ehelper.ExpressionHelper(context, assignControl_bean_.getTwoValue_value1());
        } else {
            hoursEditText = assignControl_bean_.getTwoValue_value1();
        }

        if (assignControl_bean_.isTwoValue_expression2()) {
            minutesEditText = ehelper.ExpressionHelper(context, assignControl_bean_.getTwoValue_value2());
        } else {
            minutesEditText = assignControl_bean_.getTwoValue_value2();
        }

        if (assignControl_bean_.isTwoValue_expression3()) {
            secondsEditText = ehelper.ExpressionHelper(context, assignControl_bean_.getTwoValue_value3());
        } else {
            secondsEditText = assignControl_bean_.getTwoValue_value3();
        }

        if (hoursEditText == null || hoursEditText.equals("")) {
            hoursEditText = "0";
        }
        if (minutesEditText == null || minutesEditText.equals("")) {
            minutesEditText = "0";
        }
        if (secondsEditText == null || secondsEditText.equals("")) {
            secondsEditText = "0";
        }
        if (controlObject.isTimerAutoStart()) {
            withaction_timerstart = false;
            timerStartedStatus = true;

            startTimerUp();
        }

    }

    public LinearLayout getCountUpTimerLayout() {
        return linearLayout;
    }

    @Override
    public String getTimerValue() {
        return controlObject.getText();
    }

    @Override
    public void setTimerValue(String timerValue) {

        controlObject.setText(timerValue);
    }


    @Override
    public boolean getVisibility() {
        controlObject.setInvisible(linearLayout.getVisibility() != View.VISIBLE);
        return controlObject.isInvisible();
    }

    @Override
    public void setVisibility(boolean visibility) {

        if (visibility) {
            linearLayout.setVisibility(View.GONE);
            ll_main_inside.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            ll_main_inside.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);

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
        //Clear();
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
    /*displayName,hint,timer_hr,timer_min,timer_sec*/

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

    public String getTimer_hr() {
        return controlObject.getTimer_hr();
    }

    public void setTimer_hr(String timer_hr) {
        if (timer_hr != null && !timer_hr.equals(""))
            hoursEditText = (timer_hr);
        controlObject.setTimer_hr(timer_hr);
    }

    public String getTimer_min() {
        return controlObject.getTimer_min();
    }

    public void setTimer_min(String timer_min) {
        if (timer_min != null && !timer_min.equals(""))
            minutesEditText = timer_min;
        controlObject.setTimer_min(timer_min);
    }

    public String getTimer_sec() {
        return controlObject.getTimer_sec();
    }

    public void setTimer_sec(String timer_sec) {
        if (timer_sec != null && !timer_sec.equals(""))
            secondsEditText = timer_sec;
        controlObject.setTimer_sec(timer_sec);
    }

    /*Options*/
    /*hideDisplayName,timerFormatOptions,timerAutoStart
    invisible/visibility
     */
    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        if (hideDisplayName) {
            ll_displayName.setVisibility(View.GONE);
        } else {
            ll_displayName.setVisibility(View.VISIBLE);
        }

    }

    public String getTimerFormatOptions() {
        return controlObject.getTimerFormatOptions();
    }

    public void setTimerFormatOptions(String timerFormatOptions) {
        controlObject.setTimerFormatOptions(timerFormatOptions);
        if (timerFormatOptions.equals("ss")) {
            tv_hr.setVisibility(View.GONE);
            ctv_dots1.setVisibility(View.GONE);
            tv_min.setVisibility(View.GONE);
            ctv_dots2.setVisibility(View.GONE);
            tv_sec.setVisibility(View.VISIBLE);
        } else if (timerFormatOptions.equals("mm:ss")) {
            tv_hr.setVisibility(View.GONE);
            ctv_dots1.setVisibility(View.GONE);
            tv_min.setVisibility(View.VISIBLE);
            ctv_dots2.setVisibility(View.VISIBLE);
            tv_sec.setVisibility(View.VISIBLE);
        } else { //hh:mm:ss
            tv_hr.setVisibility(View.VISIBLE);
            ctv_dots1.setVisibility(View.VISIBLE);
            tv_min.setVisibility(View.VISIBLE);
            ctv_dots2.setVisibility(View.VISIBLE);
            tv_sec.setVisibility(View.VISIBLE);
        }
        updateTimeDone();

    }

    public boolean isTimerAutoStart() {
        return controlObject.isTimerAutoStart();
    }

    public void setTimerAutoStart(boolean timerAutoStart) {
        controlObject.setTimerAutoStart(timerAutoStart);
        if(timerTask!=null){
            timerTask.cancel();
            timerStartedStatus = false;
        }
        if(timerAutoStart){
            withaction_timerstart = false;
            timerStartedStatus = true;
            startTimerUp();
        }else{
            timerStartedStatus = false;
            if(timerTask!=null) {
                timerTask.cancel();
            }
            ImproveHelper.showToast(context, "AutoStart Stopped!");
        }


    }


    public String getTimerColor() {
        return controlObject.getTimerColorHex();
    }

    public ControlObject getControlObject() {
        return controlObject;
    }
    public void setTimerColor(String color) {
        if (color != null && !color.equalsIgnoreCase("")) {
            controlObject.setTimerColorHex(color);
            controlObject.setTimerColor(Color.parseColor(color));
            tv_hr.setTextColor(Color.parseColor(color));
            tv_min.setTextColor(Color.parseColor(color));
            tv_sec.setTextColor(Color.parseColor(color));
            ctv_dots1.setTextColor(Color.parseColor(color));
            ctv_dots2.setTextColor(Color.parseColor(color));
        }
    }

    public String getTime(){
        return "";
    }
    public LinearLayout getLl_main_inside() {

        return ll_main_inside;
    }

    public LinearLayout getLl_control_ui() {

        return ll_control_ui;
    }
    public CustomTextView getTv_displayName() {

        return tv_displayName;
    }
    public LinearLayout getLl_tap_text() { // linear for taptext

        return ll_tap_text;
    }
    public String getValue(){
        return tv_hr.getText()+":"+tv_min.getText()+":"+tv_sec.getText();
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
