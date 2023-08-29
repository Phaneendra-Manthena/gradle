package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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

public class CountDownTimerControl implements TimerVaiables, UIVariables {

    private final Activity context;
    private final ControlObject controlObject;
    private final boolean subFormFlag;
    private final int subFormPos;
    private final String subFormName;
    CustomTextView tv_hr, tv_min, tv_sec, ctv_dots1, ctv_dots2;
    LinearLayout ll_displayName, ll_tap_text,ll_main_inside,ll_control_ui;
    LinearLayout ll_countdowntimer_main;
    View rView;
    ProgressBar progressBarCircle;
    String strTimerFormatOptions = "hh:mm:ss";
    String secondsEditText, minutesEditText, hoursEditText;
    /* LinearLayout ll_bts;
     Button start_button,pause_button,reset_button,end_button;*/
    boolean timerStatus = false;
    boolean isPaused = false;
    int startTime;
    int hoursLeft, minutesLeft, secondsLeft;
    int totalSecondsLeft;
    boolean withaction_timerstart = true;
    private LinearLayout linearLayout;
    private CustomTextView tv_displayName, tv_hint,ct_showText;
    private boolean mCountingDown;
    private CountDownTimer timer;

    public CountDownTimerControl(Activity context, ControlObject controlObject, boolean subFormFlag, int subFormPos, String subFormName) {
        this.context = context;
        this.controlObject = controlObject;
        this.subFormFlag = subFormFlag;
        this.subFormPos = subFormPos;
        this.subFormName = subFormName;
        initViews();
    }

    private void initViews() {
        mCountingDown = false;
        linearLayout = new LinearLayout(context);
        linearLayout.setTag(controlObject.getControlName());
        ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
        linearLayout.setLayoutParams(ImproveHelper.layout_params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        View rView = lInflater.inflate(R.layout.control_chart, null);
        if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
            if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                rView = lInflater.inflate(R.layout.layout_countdowntimer_progress, null);
                progressBarCircle = rView.findViewById(R.id.progressBarCircle);
            } else {
                rView = lInflater.inflate(R.layout.control_countdowntimer_default, null);
            }
        } else {
//            rView = lInflater.inflate(R.layout.control_countdowntimer, null);
            rView = lInflater.inflate(R.layout.control_countdowntimer_default, null);
        }
        //bts
        /* ll_bts= rView.findViewById(R.id.ll_bts);
         start_button= rView.findViewById(R.id.start_button);
        pause_button= rView.findViewById(R.id.pause_button);
        reset_button= rView.findViewById(R.id.reset_button);
        end_button= rView.findViewById(R.id.end_button);*/
        ll_main_inside = rView.findViewById(R.id.ll_main_inside);
        ll_control_ui = rView.findViewById(R.id.ll_control_ui);
        ll_displayName = rView.findViewById(R.id.ll_displayName);
        tv_displayName = rView.findViewById(R.id.tv_displayName);
        tv_hint = rView.findViewById(R.id.tv_hint);

        ll_tap_text = rView.findViewById(R.id.ll_tap_text);
        ll_countdowntimer_main = rView.findViewById(R.id.ll_countdowntimer_main);
        tv_hr = rView.findViewById(R.id.tv_hr);
        tv_min = rView.findViewById(R.id.tv_min);
        tv_sec = rView.findViewById(R.id.tv_sec);

        ctv_dots1 = rView.findViewById(R.id.ctv_dots1);
        ctv_dots2 = rView.findViewById(R.id.ctv_dots2);
        ct_showText = rView.findViewById(R.id.ct_showText);

        ll_tap_text.setTag(controlObject.getControlType());
        linearLayout.addView(rView);
        setControlValues();

    }

    private void setControlValues() {
        try {
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
                linearLayout.setVisibility(View.GONE);
            } else {
                linearLayout.setVisibility(View.VISIBLE);
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
                setSecondsEditText(controlObject.getTimer_sec());
            } else {
                setSecondsEditText("0");
            }
            if (controlObject.getTimer_min() != null && !controlObject.getTimer_min().equals("")) {
                setMinutesEditText(controlObject.getTimer_min());
            } else {
                setMinutesEditText("0");
            }
            if (controlObject.getTimer_hr() != null && !controlObject.getTimer_hr().equals("")) {
                setHoursEditText(controlObject.getTimer_hr());
            } else {
                setHoursEditText("0");
            }
            if (controlObject.getTimerColorHex() != null) {
                String color = controlObject.getTimerColorHex();
                tv_hr.setTextColor(Color.parseColor(color));
                tv_min.setTextColor(Color.parseColor(color));
                tv_sec.setTextColor(Color.parseColor(color));
                ctv_dots1.setTextColor(Color.parseColor(color));
                ctv_dots2.setTextColor(Color.parseColor(color));
            }
            if (controlObject.isTimerAutoStart()) {
                //ll_bts.setVisibility(View.GONE);
                withaction_timerstart = false;
                startTimer();
            } else {
          /*  ll_bts.setVisibility(View.GONE);
            start_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll_bts.setVisibility(View.GONE);
                    startTimer(secondsEditText,minutesEditText,hoursEditText);
                }
            });*/
            }

            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        }catch (Exception e){
            Log.getStackTraceString(e);
        }
    }

    private void startTimer() {


        startTime = 0;
        startTime += Integer.parseInt(getSecondsEditText()) * 1000;
        startTime += Integer.parseInt(getMinutesEditText()) * 60 * 1000;
        startTime += Integer.parseInt(getHoursEditText()) * 60 * 60 * 1000;


        startCountDownTimer(startTime);
    }

    private void startCountDownTimer(int millis) {
        if (progressBarCircle != null) {
            progressBarCircle.setMax(millis / 1000);
        }
        timer = new CountDownTimer(millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerStatus = true;
                updateTimeRemaining(millisUntilFinished);
                if (progressBarCircle != null)
                    progressBarCircle.setProgress((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                timerStatus = false;
                finishTimer("Count down complete");
            }
        }.start();
        //Timer timers = new Timer("MetronomeTimer", true);
        /*TimerTask tone = new TimerTask() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    public void run() {
                        System.out.println("1 min");
                        ImproveHelper.showToast(context,"1 min");
                    }
                });

            }
        };
        timers.scheduleAtFixedRate(tone, 60000, 60000); // every 500 ms*/
    }

    private void updateTimeRemaining(long millisUntilFinished) {
        totalSecondsLeft = (int) millisUntilFinished / 1000;
        hoursLeft = totalSecondsLeft / 3600;
        minutesLeft = (totalSecondsLeft % 3600) / 60;
        secondsLeft = totalSecondsLeft % 60;

        updateTimeFormat();

    }

    private void updateTimeFormat() {
        if (strTimerFormatOptions.equals("ss")) {
            tv_sec.setText(String.format("%02d", totalSecondsLeft));
            setTimerValue("0:" + "0:" + tv_sec.getText().toString().trim());

        } else if (strTimerFormatOptions.equals("mm:ss")) {
            minutesLeft = totalSecondsLeft / 60;

            tv_min.setText(String.format("%02d", minutesLeft));
            tv_sec.setText(String.format("%02d", secondsLeft));
            setTimerValue("0:" + tv_min.getText().toString().trim() + ":" + tv_sec.getText().toString().trim());

        } else {
            tv_hr.setText(String.format("%02d", hoursLeft));
            tv_min.setText(String.format("%02d", minutesLeft));
            tv_sec.setText(String.format("%02d", secondsLeft));
            setTimerValue(tv_hr.getText().toString().trim() + ":" + tv_min.getText().toString().trim() + ":" + tv_sec.getText().toString().trim());

        }
    }

    private void finishTimer(String message) {


        ll_countdowntimer_main.setTag(controlObject.getControlName());
        if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
            if (AppConstants.EventCallsFrom == 1) {
                AppConstants.EventFrom_subformOrNot = subFormFlag;
                if (subFormFlag) {
                    AppConstants.SF_Container_position = subFormPos;
                    AppConstants.Current_ClickorChangeTagName = subFormName;

                }
                AppConstants.GlobalObjects.setCurrent_GPS("");
                ((MainActivity) context).ClickEvent(ll_countdowntimer_main);
            }
        }

        setProgressBarValues();
    }

    public void startTimer(String timerOption) {
        if (timerOption.equals("Start")) {
            if (withaction_timerstart) {
                ImproveHelper.showToast(context, "Timer Starting...");
                withaction_timerstart = false;

                startTimer();
            } else {
                if (timerStatus) {
                    ImproveHelper.showToast(context, "Timer Running...");
                } else {
                    ImproveHelper.showToast(context, "Timer Stopped!");
                }

            }
        } else if (timerOption.equals("Pause")) {
            isPaused = !isPaused;
            if (isPaused) {
                //pause.setText("Resume");
                timer.cancel();
                ImproveHelper.showToast(context, "Count Down Timer Paused!");
            } else {
                //pause.setText("Pause");
                ImproveHelper.showToast(context, "Count Down Timer Resumed!");
                startCountDownTimer(totalSecondsLeft * 1000);

            }
        } else if (timerOption.equals("Stop")) {
            timer.cancel();
            timerStatus = false;
            ImproveHelper.showToast(context, "Count Down Timer Cancelled!");
            finishTimer("Count down cancelled");
        }


    }

    private void setProgressBarValues() {

        if (progressBarCircle != null) {
            progressBarCircle.setMax(startTime / 1000);
            //progressBarCircle.setProgress((int) startTime / 1000);
        }
    }

    public void setControlValues(AssignControl_Bean assignControl_bean_) {

        ExpressionMainHelper ehelper = new ExpressionMainHelper();

        if (assignControl_bean_.isTwoValue_expression1()) {
            setHoursEditText(ehelper.ExpressionHelper(context, assignControl_bean_.getTwoValue_value1()));
        } else {
            setHoursEditText(assignControl_bean_.getTwoValue_value1());
        }

        if (assignControl_bean_.isTwoValue_expression2()) {
            setMinutesEditText(ehelper.ExpressionHelper(context, assignControl_bean_.getTwoValue_value2()));
        } else {
            setMinutesEditText(assignControl_bean_.getTwoValue_value2());
        }

        if (assignControl_bean_.isTwoValue_expression3()) {
            setSecondsEditText(ehelper.ExpressionHelper(context, assignControl_bean_.getTwoValue_value3()));
        } else {
            setSecondsEditText(assignControl_bean_.getTwoValue_value3());
        }

        if (getHoursEditText() == null || getHoursEditText().equals("")) {
            setHoursEditText("0");
        }
        if (getMinutesEditText() == null || getMinutesEditText().equals("")) {
            setMinutesEditText("0");
        }
        if (getSecondsEditText() == null || getSecondsEditText().equals("")) {
            setSecondsEditText("0");
        }
        if (controlObject.isTimerAutoStart()) {
            withaction_timerstart = false;
            //ll_bts.setVisibility(View.GONE);
            startTimer();
        }/*else{
            //ll_bts.setVisibility(View.VISIBLE);
            String finalMinutesEditText = minutesEditText;
            String finalHoursEditText = hoursEditText;
            String finalSecondsEditText = secondsEditText;
            start_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll_bts.setVisibility(View.GONE);
                    startTimer(finalSecondsEditText, finalMinutesEditText, finalHoursEditText);
                }
            });
        }*/

    }
    public LinearLayout getLl_tap_text() { // linear for taptext

        return ll_tap_text;
    }

    public LinearLayout getCountDownTimerLayout() {
        return linearLayout;
    }

    public String getSecondsEditText() {
        return secondsEditText;
    }

    public void setSecondsEditText(String secondsEditText) {
        this.secondsEditText = secondsEditText;
    }

    public String getMinutesEditText() {
        return minutesEditText;
    }

    public void setMinutesEditText(String minutesEditText) {
        this.minutesEditText = minutesEditText;
    }

    public String getHoursEditText() {
        return hoursEditText;
    }

    public void setHoursEditText(String hoursEditText) {
        this.hoursEditText = hoursEditText;
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
//            ll_main_inside.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
//            ll_main_inside.setVisibility(View.VISIBLE);
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
        setHoursEditText(timer_hr);
        controlObject.setTimer_hr(timer_hr);
    }

    public String getTimer_min() {
        return controlObject.getTimer_min();
    }

    public void setTimer_min(String timer_min) {
        setMinutesEditText(timer_min);
        controlObject.setTimer_min(timer_min);
    }

    public String getTimer_sec() {
        return controlObject.getTimer_sec();
    }

    public void setTimer_sec(String timer_sec) {
        setSecondsEditText(timer_sec);
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
        if (hideDisplayName) {
            ll_displayName.setVisibility(View.GONE);
            tv_hint.setVisibility(View.GONE);
        } else {
            ll_displayName.setVisibility(View.VISIBLE);
            tv_hint.setVisibility(View.VISIBLE);
        }
        controlObject.setHideDisplayName(hideDisplayName);
    }

    public String getTimerFormatOptions() {
        return controlObject.getTimerFormatOptions();
    }

    public void setTimerFormatOptions(String timerFormatOptions) {
/*        if (timerFormatOptions.trim().equals("2")) {
            strTimerFormatOptions = "ss";
        } else if (timerFormatOptions.trim().equals("1")) {
            strTimerFormatOptions = "mm:ss";
        } else {
            strTimerFormatOptions = "hh:mm:ss";
        }*/


        if (timerFormatOptions.equals("ss")) {
            tv_hr.setVisibility(View.GONE);
            ctv_dots1.setVisibility(View.GONE);
            tv_min.setVisibility(View.GONE);
            ctv_dots2.setVisibility(View.GONE);
        } else if (timerFormatOptions.equals("mm:ss")) {
            tv_hr.setVisibility(View.GONE);
            ctv_dots1.setVisibility(View.GONE);
        } else {//hh:mm:ss
            tv_hr.setVisibility(View.VISIBLE);
            ctv_dots1.setVisibility(View.VISIBLE);
            tv_min.setVisibility(View.VISIBLE);
            ctv_dots2.setVisibility(View.VISIBLE);
            tv_sec.setVisibility(View.VISIBLE);
        }
        updateTimeFormat();
        controlObject.setTimerFormatOptions(timerFormatOptions);
    }
    public void setTimerFormatOptionsOld(String timerFormatOptions) {
        if (timerFormatOptions.trim().equals("2")) {
            strTimerFormatOptions = "ss";
        } else if (timerFormatOptions.trim().equals("1")) {
            strTimerFormatOptions = "mm:ss";
        } else {
            strTimerFormatOptions = "hh:mm:ss";
        }


        if (strTimerFormatOptions.equals("ss")) {
            tv_hr.setVisibility(View.GONE);
            ctv_dots1.setVisibility(View.GONE);
            tv_min.setVisibility(View.GONE);
            ctv_dots2.setVisibility(View.GONE);
        } else if (strTimerFormatOptions.equals("mm:ss")) {
            tv_hr.setVisibility(View.GONE);
            ctv_dots1.setVisibility(View.GONE);
        } else {//hh:mm:ss
            tv_hr.setVisibility(View.VISIBLE);
            ctv_dots1.setVisibility(View.VISIBLE);
            tv_min.setVisibility(View.VISIBLE);
            ctv_dots2.setVisibility(View.VISIBLE);
            tv_sec.setVisibility(View.VISIBLE);
        }
        updateTimeFormat();
        controlObject.setTimerFormatOptions(timerFormatOptions);
    }

    public boolean isTimerAutoStart() {
        return controlObject.isTimerAutoStart();
    }

    public void setTimerAutoStart(boolean timerAutoStart) {
        controlObject.setTimerAutoStart(timerAutoStart);
        if (timerAutoStart) {
            if (timer != null) {
                timer.cancel();
            }
            withaction_timerstart = false;
            startTimer();
        } else {
            if (timer != null)
                timer.cancel();
        }
    }

    public String getTimerColor() {
        return controlObject.getTimerColorHex();
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

    public ControlObject getControlObject() {
        return controlObject;
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
