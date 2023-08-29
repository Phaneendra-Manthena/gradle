package com.bhargo.user.controls.advanced;

import static com.bhargo.user.utils.ImproveHelper.pxToDPControlUI;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bhargo.user.Expression.ExpressionMainHelper;
import com.bhargo.user.Java_Beans.AssignControl_Bean;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

public class ProgressControl implements UIVariables {

    private static final String TAG = "ProgressControl";
    TextView tv_actualvalue, tv_maxvalue;
    ProgressBar pb;
    LinearLayout ll_progress_main;
    View rView;
    private  Activity context;
    private  ControlObject controlObject;
    private  boolean subFormFlag;
    private  int subFormPos;
    private  String subFormName;
    private LinearLayout linearLayout,ll_main_inside,ll_displayName;
    CardView cv_all;
    private CustomTextView tv_displayName, tv_hint,ct_showText;

    public ProgressControl(Activity context, ControlObject controlObject, boolean subFormFlag, int subFormPos, String subFormName) {
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
                rView = lInflater.inflate(R.layout.control_progress, null);
            }
        } else {
            rView = lInflater.inflate(R.layout.control_progress, null);
        }
        ll_main_inside= rView.findViewById(R.id.ll_main_inside);
        ll_displayName= rView.findViewById(R.id.ll_displayName);
        cv_all= rView.findViewById(R.id.cv_all);
        tv_displayName = rView.findViewById(R.id.tv_displayName);
        tv_hint = rView.findViewById(R.id.tv_hint);

        ll_progress_main = rView.findViewById(R.id.ll_progress_main);
        tv_actualvalue = rView.findViewById(R.id.tv_actualvalue);
        tv_maxvalue = rView.findViewById(R.id.tv_maxvalue);
        pb = rView.findViewById(R.id.pb);
        ct_showText = rView.findViewById(R.id.ct_showText);
        ll_main_inside.setTag(controlObject.getControlName());
        ll_main_inside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.EventFrom_subformOrNot = subFormFlag;
                        if (subFormFlag) {
                            AppConstants.SF_Container_position = subFormPos;
                            AppConstants.Current_ClickorChangeTagName = subFormName;

                        }
                        AppConstants.GlobalObjects.setCurrent_GPS("");
                        ((MainActivity) context).ClickEvent(view);
                    }
                }
            }
        });

        setControlValues();
        linearLayout.addView(rView);
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
                ll_displayName.setVisibility(View.GONE);
//                tv_displayName.setVisibility(View.GONE);
//                tv_hint.setVisibility(View.GONE);
            }else{
                ll_displayName.setVisibility(View.VISIBLE);
            }
            //layout
            if (controlObject.getProgress_maxvalue() != null && !controlObject.getProgress_maxvalue().equals("")) {
                pb.setMax(Integer.parseInt(controlObject.getProgress_maxvalue()));
                tv_maxvalue.setText(controlObject.getProgress_maxvalue());
                pb.setProgress(0);
                tv_actualvalue.setText("0");
                controlObject.setControlValue(controlObject.getProgress_maxvalue());
            } else {
                ll_progress_main.setVisibility(View.GONE);
                tv_maxvalue.setText("");
                tv_actualvalue.setText("");
            }

            if (controlObject.getProgress_actualvalue() != null && !controlObject.getProgress_actualvalue().equals("")) {
                pb.setProgress(Integer.parseInt(controlObject.getProgress_actualvalue()));
                tv_actualvalue.setText(controlObject.getProgress_actualvalue());
                controlObject.setText(controlObject.getProgress_actualvalue());
                // int d=Integer.parseInt(controlObject.getProgress_actualvalue());
                //tv_actualvalue.setX(Float.valueOf(linearLayout.getWidth()+""));
            }

            if (controlObject.getProgress_maxvalue() != null && !controlObject.getProgress_maxvalue().equals("") &&
                    controlObject.getProgress_actualvalue() != null && !controlObject.getProgress_actualvalue().equals("") &&
                    controlObject.getProgress_maxvalue().equals(controlObject.getProgress_actualvalue())) {

                pb.setMax(Integer.parseInt(controlObject.getProgress_maxvalue()));
                tv_maxvalue.setText(controlObject.getProgress_maxvalue());

                pb.setProgress(Integer.parseInt(controlObject.getProgress_maxvalue()));
                tv_actualvalue.setText(controlObject.getProgress_maxvalue());
                controlObject.setText(controlObject.getProgress_maxvalue());
                controlObject.setControlValue(controlObject.getProgress_maxvalue());

            }

            if (controlObject.isHide_progress_actualvalue()) {
                tv_actualvalue.setVisibility(View.GONE);
            }
            if (controlObject.isHide_progress_maxvalue()) {
                tv_maxvalue.setVisibility(View.GONE);
            }

            if (controlObject.getProgressColorHex() != null && !controlObject.getProgressColorHex().equals("")) {
                pb.setProgressTintList(ColorStateList.valueOf(Color.parseColor(controlObject.getProgressColorHex())));
            }

        }catch (Exception e){
            Log.getStackTraceString(e);
        }

    }

    public void setControlValues(AssignControl_Bean assignControl_bean_) {

        ExpressionMainHelper ehelper = new ExpressionMainHelper();
        String maxValue="",actualValue="";
        if(assignControl_bean_.isTwoValue_expression1()){
             maxValue = ehelper.ExpressionHelper(context, assignControl_bean_.getTwoValue_value1());
        }else{
            maxValue=assignControl_bean_.getTwoValue_value1();
        }

        if(assignControl_bean_.isTwoValue_expression2()){
            actualValue = ehelper.ExpressionHelper(context, assignControl_bean_.getTwoValue_value2());
        }else{
            actualValue=assignControl_bean_.getTwoValue_value2();
        }

        if (maxValue != null && !maxValue.equals("")) {
            //max
            pb.setMax(Integer.parseInt(maxValue));
            tv_maxvalue.setText(maxValue);

            pb.setProgress(0);
            tv_actualvalue.setText("0");
            ll_progress_main.setVisibility(View.VISIBLE);
            controlObject.setControlValue(maxValue);
        } else {
            ll_progress_main.setVisibility(View.GONE);
            tv_maxvalue.setText("");
            tv_actualvalue.setText("");
        }

        if (actualValue != null && !actualValue.equals("")) {
            pb.setProgress(Integer.parseInt(actualValue));
            tv_actualvalue.setText(actualValue);
            controlObject.setText(actualValue);
            // int d=Integer.parseInt(controlObject.getProgress_actualvalue());
            //tv_actualvalue.setX(Float.valueOf(linearLayout.getWidth()+""));
        }

        if (maxValue != null && !maxValue.equals("") &&
                actualValue != null && !actualValue.equals("") &&
                maxValue.equals(actualValue)) {

            pb.setMax(Integer.parseInt(maxValue));
            tv_maxvalue.setText(maxValue);

            pb.setProgress(Integer.parseInt(maxValue));
            tv_actualvalue.setText(maxValue);
            controlObject.setText(maxValue);
            controlObject.setControlValue(maxValue);
        }

    }

    public LinearLayout getProgressLayout() {
        return linearLayout;
    }

    public boolean getVisibility() {
        controlObject.setInvisible(linearLayout.getVisibility() != View.VISIBLE);
        return controlObject.isInvisible();
    }


    public void setVisibility(boolean visibility) {

        if (visibility) {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        } else {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }
    }


    public boolean isEnabled() {

        return !controlObject.isDisable();
    }


    public void setEnabled(boolean enabled) {
//        setViewDisable(rView, !enabled);
        controlObject.setDisable(!enabled);
        setViewDisableOrEnableDefault(context,rView, enabled);
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

    /* Set Properties*/
    public void setDisplayName(String propertyText) {
        tv_displayName.setText(propertyText);
        controlObject.setDisplayName(propertyText);
    }

    public void setHint(String propertyText) {
        if (propertyText != null && !propertyText.isEmpty()) {
            tv_hint.setVisibility(View.VISIBLE);
            tv_hint.setText(propertyText);
            controlObject.setHint(propertyText);
        } else {
            tv_hint.setVisibility(View.GONE);
        }
    }

    public void setHideDisplayName(Boolean valueOf) {
        if (valueOf) {
            tv_displayName.setVisibility(View.GONE);
            tv_hint.setVisibility(View.GONE);
        }else{
            tv_displayName.setVisibility(View.VISIBLE);
            if(controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                tv_hint.setVisibility(View.VISIBLE);
            }else{
                tv_hint.setVisibility(View.GONE);
            }

        }
    }
    public void setVisibilitySP(boolean visibility) {
        if (visibility) {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }
    }
    public void setProgress_maxvalue(String propertyText) {
        pb.setMax(Integer.parseInt(propertyText));
        tv_maxvalue.setText(propertyText);

        pb.setProgress(0);
        tv_actualvalue.setText("0");
        ll_progress_main.setVisibility(View.VISIBLE);
        controlObject.setProgress_maxvalue(propertyText);
    }
    public void setProgress_actualvalue(String propertyText) {
        try {
            ll_progress_main.setVisibility(View.VISIBLE);
            pb.setProgress(Integer.parseInt(propertyText));
            tv_actualvalue.setText(propertyText);
            controlObject.setProgress_actualvalue(propertyText);
            if (controlObject.getProgress_maxvalue() != null && !controlObject.getProgress_maxvalue().equals("") &&
                    controlObject.getProgress_actualvalue() != null && !controlObject.getProgress_actualvalue().equals("") &&
                    controlObject.getProgress_maxvalue().equals(controlObject.getProgress_actualvalue())) {

                pb.setMax(Integer.parseInt(controlObject.getProgress_maxvalue()));
                tv_maxvalue.setText(controlObject.getProgress_maxvalue());

                pb.setProgress(Integer.parseInt(controlObject.getProgress_maxvalue()));
                tv_actualvalue.setText(controlObject.getProgress_maxvalue());
            }
        }catch (Exception e){
            Log.d(TAG, "setProgress_actualvalue: "+e.toString());
        }

    }
    public void setHide_progress_maxvalue(Boolean valueOf) {
        if (valueOf) {
            tv_maxvalue.setVisibility(View.GONE);
            controlObject.setHide_progress_maxvalue(valueOf);
        }
    }
    public void setHide_progress_actualvalue(Boolean valueOf) {
        if (valueOf) {
            tv_actualvalue.setVisibility(View.GONE);
            controlObject.setHide_progress_actualvalue(valueOf);
        }
    }
    public void setProgressColorHex(String propertyText) {
        pb.setProgressTintList(ColorStateList.valueOf(Color.parseColor(propertyText)));
        controlObject.setProgressColorHex(propertyText);
    }

    /* Control Ui Settings*/
    public LinearLayout getLl_main_inside() {

        return ll_main_inside;
    }
    public CustomTextView getTv_displayName() {

        return tv_displayName;
    }
    public CardView getCv_all() {

        return cv_all;
    }
    public ProgressBar getPb() {

        return pb;
    }
    public LinearLayout getLl_progress_main() {

        return ll_progress_main;
    }
    /* Control Ui Settings*/
    public ControlObject getControlObject() {
        return controlObject;
    }
    public void showMessageBelowControl(String msg) {
        if(msg != null && !msg.isEmpty()) {
            ct_showText.setVisibility(View.VISIBLE);
            ct_showText.setText(msg);
        }else{
            ct_showText.setVisibility(View.GONE);
        }
    }

}
