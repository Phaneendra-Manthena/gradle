package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import androidx.core.content.ContextCompat;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.RatingVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.hsalf.smileyrating.SmileyRating;


public class Rating implements RatingVariables, UIVariables {

    public static final String TAG = "RatingControl";
    private final int RatingTAG = 0;
    ImproveHelper improveHelper;
    Context context;
    ControlObject controlObject;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    private LinearLayout linearLayout, ll_layoutBackgroundColor, ll_rating, ll_main_inside, ll_control_ui, ll_tap_text, ll_main_inside_inside, ll_label, ll_displayName;
    private CustomTextView tv_displayName, tv_hint, ct_alertRating, ct_showText;
    private ImageView iv_mandatory;
    private View view;
    public RatingBar ratingBar;
    private SmileyRating smileRating;
    private String strRatingMain = "0.0";

    public Rating(Context context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
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

            addRatingStrip(context);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getRatingView() {

        return linearLayout;
    }

    public void addRatingStrip(final Context context) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = lInflater.inflate(R.layout.control_rating, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {

                    view = linflater.inflate(R.layout.control_rating_two, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {

                    view = linflater.inflate(R.layout.control_rating_three, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("5")) {

                    view = linflater.inflate(R.layout.control_rating_five, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                    view = linflater.inflate(R.layout.control_rating_six, null);
                    ll_layoutBackgroundColor = view.findViewById(R.id.ll_layoutBackgroundColor);

                }
            } else {

//                view = linflater.inflate(R.layout.control_rating, null);
                view = linflater.inflate(R.layout.layout_rating_default, null);
            }
            view.setTag(RatingTAG);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            ll_tap_text = view.findViewById(R.id.ll_tap_text);
            ll_label = view.findViewById(R.id.ll_label);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            ct_alertRating = view.findViewById(R.id.ct_alertTypeText);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            ll_rating = view.findViewById(R.id.ll_rating);

            ratingBar = view.findViewById(R.id.ratingStar);
            //change
            smileRating = view.findViewById(R.id.smile_rating);
            ct_showText = view.findViewById(R.id.ct_showText);

//            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
//            stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
//            stars.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
//            stars.getDrawable(1).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

            if (ll_tap_text != null) {
                ll_tap_text.setTag(controlObject.getControlType());
            }


            if (controlObject.getRatingType().equalsIgnoreCase("1") || controlObject.getRatingType().equalsIgnoreCase("Star")) {
                ratingBar.setNumStars(Integer.parseInt(controlObject.getRatingItemCount()));
                ratingBar.setTag(controlObject.getControlName());

            } else if (controlObject.getRatingType().equalsIgnoreCase("2") || controlObject.getRatingType().equalsIgnoreCase("Smiley")) {
                //change
                smileRating.setRating(Integer.parseInt(controlObject.getRatingItemCount()));
//                setSelectedEmoji(Integer.parseInt(controlObject.getRatingItemCount()));
                smileRating.setTag(controlObject.getControlName());
            }

            // Default Rating bar onchange listener
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    /*TypedValue typedValue = new TypedValue();
                    Resources.Theme theme = context.getTheme();
                    theme.resolveAttribute(R.attr.bhargo_color_one, typedValue, true);*/
                    if(!controlObject.isDisable()) {
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                if (SubformFlag) {
                                    AppConstants.SF_Container_position = SubformPos;
                                    AppConstants.Current_ClickorChangeTagName = SubformName;
                                }
                                AppConstants.GlobalObjects.setCurrent_GPS("");
                                ((MainActivity) context).ChangeEvent(ratingBar);
                            }
                        }

                        ct_alertRating.setText("");
                        ct_alertRating.setVisibility(View.GONE);
                        strRatingMain = String.valueOf(rating);
                        controlObject.setText(strRatingMain);
                        Log.d(TAG, "onRatingChanged: " + ratingBar.getNumStars() + "," + rating + "," + fromUser + "," + strRatingMain);
                    }
                }
            });

//             Emoji Rating bar listener
//            smileRating.setRateChangeListener(new EmojiRatingBar.OnRateChangeListener() {
//                @Override
//                public void onRateChanged(@NonNull RateStatus rateStatus) {
//
//                    if (rateStatus == RateStatus.EMPTY) {
//                        strRatingMain = String.valueOf(RateStatus.EMPTY);
//                    } else if (rateStatus == RateStatus.AWFUL) {
//                        strRatingMain = String.valueOf(RateStatus.AWFUL);
//                    } else if (rateStatus == RateStatus.BAD) {
//                        strRatingMain = String.valueOf(RateStatus.BAD);
//                    } else if (rateStatus == RateStatus.OKAY) {
//                        strRatingMain = String.valueOf(RateStatus.OKAY);
//                    } else if (rateStatus == RateStatus.GOOD) {
//                        strRatingMain = String.valueOf(RateStatus.GOOD);
//                    } else if (rateStatus == RateStatus.GREAT) {
//                        strRatingMain = String.valueOf(RateStatus.GREAT);
//                    }
//
//                }
//            });

            smileRating.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {
                @Override
                public void onSmileySelected(SmileyRating.Type type) {
                    if (type.equals(SmileyRating.Type.TERRIBLE)) {
                        strRatingMain = "1";
                        Log.i(TAG, "Terrible");
                    } else if (type.equals(SmileyRating.Type.BAD)) {
                        strRatingMain = "2";
                        Log.i(TAG, "Bad");
                    } else if (type.equals(SmileyRating.Type.OKAY)) {
                        strRatingMain = "3";
                        Log.i(TAG, "Okay");
                    } else if (type.equals(SmileyRating.Type.GOOD)) {
                        strRatingMain = "4";
                        Log.i(TAG, "Good");
                    } else if (type.equals(SmileyRating.Type.GREAT)) {
                        strRatingMain = "5";
                        Log.i(TAG, "Great");
                    }
                    Log.d(TAG, "onRatingChangedSmiley: " + smileRating.getSelectedSmiley() + "," + strRatingMain);
                    controlObject.setText(strRatingMain);
                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                        if (AppConstants.EventCallsFrom == 1) {
                            AppConstants.EventFrom_subformOrNot = SubformFlag;
                            if (SubformFlag) {
                                AppConstants.SF_Container_position = SubformPos;
                                AppConstants.Current_ClickorChangeTagName = SubformName;
                            }
                            AppConstants.GlobalObjects.setCurrent_GPS("");
                            ((MainActivity) context).ChangeEvent(smileRating);
                        }
                    }
                }
            });

//             SmileRating bar onchange listener
/*
            smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
                @Override
                public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                    // reselected is false when user selects different smiley that previously selected one
                    // true when the same smiley is selected.
                    // Except if it first time, then the value will be false.
                    switch (smiley) {
                        case SmileRating.BAD:
                            strRatingMain = String.valueOf(SmileRating.BAD);
                            Log.i(TAG, "Bad");
                            break;
                        case SmileRating.GOOD:
                            strRatingMain = String.valueOf(SmileRating.GOOD);
                            Log.i(TAG, "Good");
                            break;
                        case SmileRating.GREAT:
                            strRatingMain = String.valueOf(SmileRating.GREAT);
                            Log.i(TAG, "Great");
                            break;
                        case SmileRating.OKAY:
                            strRatingMain = String.valueOf(SmileRating.OKAY);
                            Log.i(TAG, "Okay");
                            break;
                        case SmileRating.TERRIBLE:
                            strRatingMain = String.valueOf(SmileRating.TERRIBLE);
                            Log.i(TAG, "Terrible");
                            break;
                    }
                    Log.d(TAG, "onRatingChangedSmiley: " + smileRating.getRating() + "," + smiley + "," + reselected);
                    controlObject.setText(strRatingMain);
                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                        if (AppConstants.EventCallsFrom == 1) {
                            AppConstants.EventFrom_subformOrNot = SubformFlag;
                            if (SubformFlag) {
                                AppConstants.SF_Container_position = SubformPos;
                                AppConstants.Current_ClickorChangeTagName = SubformName;
                            }
                            AppConstants.GlobalObjects.setCurrent_GPS("");
                            ((MainActivity) context).ChangeEvent(smileRating);
                        }
                    }
                }
            });
*/


            setControlValues();
            linearLayout.addView(view);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addRatingStrip", e);
        }
    }


    public void Clear() {
        try {
            //change
            smileRating.setRating(SmileyRating.Type.NONE);
//            smileRating.setCurrentRateStatus(RateStatus.EMPTY);
            ratingBar.setRating(RatingBar.NO_ID);
            controlObject.setText(null);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "Clear", e);
        }
    }

    private void setControlValues() {
        try {
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
            }
            if (controlObject.getRatingType() != null) {

                if (controlObject.getRatingType().equalsIgnoreCase("Smiley")) {
                    smileRating.setVisibility(View.VISIBLE);
                    ratingBar.setVisibility(View.GONE);
                } else {
                    ratingBar.setVisibility(View.VISIBLE);
                    smileRating.setVisibility(View.GONE);
//                setItemColor();
                }
            } else {
                ratingBar.setVisibility(View.VISIBLE);
//            setItemColor();
            }
            if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().equalsIgnoreCase("null") && !controlObject.getDefaultValue().isEmpty()) {
                if (controlObject.getRatingType().equalsIgnoreCase("Smiley")) {
                    //change
                    smileRating.setRating(Integer.parseInt(controlObject.getDefaultValue()));
//                    setSelectedEmoji(Integer.parseInt(controlObject.getDefaultValue()));
                } else {
                    ratingBar.setRating(Float.parseFloat(controlObject.getDefaultValue()));
                }
            }

            if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                ll_layoutBackgroundColor.setVisibility(View.GONE);
            }
/*
            if (controlObject.isDisableRatingCount()) {
//                setViewDisableOrEnableDefault(context,view, false);
                improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), !controlObject.isDisableRatingCount(), ll_tap_text, view);
            }
*/

            if (controlObject.isDisable()) {
//                setViewDisable(context,view, false);
//                setViewDisableOrEnableDefault(context,view, false);
                if (controlObject.getRatingType().equalsIgnoreCase("Smiley")) {
                    smileRating.disallowSelection(controlObject.isDisable());
                }
                improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), !controlObject.isDisable(), ll_tap_text, view);
            }

            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    private void setItemColor() {

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
//        stars.getDrawable(0).setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2).setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);

    }

    public String getTotalRating() {

        return strRatingMain;
    }

    public String getRatingValue() {

        if (!strRatingMain.contentEquals("0.0") || !strRatingMain.contentEquals("-1")) {
            return strRatingMain;
        } else {
            return null;
        }

    }

/*
    public String getTotalRating() {
        Log.d(TAG, "getTotalRating: " + ratingBar.getRating());
        String rating = "0.0";
        if (controlObject.getRatingType().equalsIgnoreCase("1")) {
            rating = String.valueOf(ratingBar.getRating());
        } else  if (controlObject.getRatingType().equalsIgnoreCase("2")){
            rating = String.valueOf(smileRating.getRating());
        }
        return rating;
    }
*/

    public CustomTextView setAlertRating() {

        return ct_alertRating;
    }

    public LinearLayout getLl_rating() {

        return ll_rating;
    }

    public void setRating(String value) {
        try {
            if (value != null && !value.isEmpty()) {
                if (controlObject.getRatingType().equalsIgnoreCase("Smiley")) {
                    if (!value.equalsIgnoreCase("0.0") || !value.equalsIgnoreCase("-1")) {
                        smileRating.setRating(Integer.parseInt(value));
//                        setSelectedEmoji(Integer.parseInt(value));
                    }
                } else {
                    ratingBar.setRating(Float.parseFloat(value));
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setRating", e);
        }
    }

    public String getRatingMain() {
        return strRatingMain;
    }

    public RatingBar getRatingBar() {
        return ratingBar;
    }

    public LinearLayout getLl_main_inside() {
        return ll_main_inside;
    }

    public LinearLayout getLl_control_ui() {
        return ll_control_ui;
    }

    @Override
    public String getSelectedRating() {
        String value = "";
        if (controlObject.getRatingType().equalsIgnoreCase("Smiley")) {
            value = "" + smileRating.getSelectedSmiley().getRating();
        } else {
            value = "" + ratingBar.getRating();
        }
        return null;
    }

    @Override
    public void setSelectedRating(String rating) {

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
//        setViewDisable(view, !enabled);
        controlObject.setDisable(!enabled);
//        setViewDisableOrEnableDefault(context,view, enabled);
       /* if (controlObject.getRatingType().equalsIgnoreCase("Smiley")) {
            smileRating.disallowSelection(!enabled);
        }
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), !controlObject.isDisable(), ll_tap_text, view);*/
        if (controlObject.getRatingType().equalsIgnoreCase("Smiley")) {
            smileRating.disallowSelection(controlObject.isDisable());
        }
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), enabled, ll_tap_text, view);

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
        iv_mandatory.setVisibility(mandatory ? View.VISIBLE : View.GONE);
        controlObject.setNullAllowed(mandatory);
    }

    public String getMandatoryErrorMessage() {
        return controlObject.getMandatoryFieldError();
    }

    public void setMandatoryErrorMessage(String mandatoryErrorMessage) {
        controlObject.setMandatoryFieldError(mandatoryErrorMessage);
    }

    /*Options*/
    /*hideDisplayName,disableRatingCount,selectRatingItemType,ratingType,customItemNames
    invisible/visibility,disable/enabled
     */

    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        ll_label.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
        if (hideDisplayName) {
            tv_hint.setVisibility(View.GONE);
        }

    }

    public boolean isDisableRatingCount() {
        return controlObject.isDisableRatingCount();
    }

    public void setDisableRatingCount(boolean disableRatingCount) {
        controlObject.setDisableRatingCount(disableRatingCount);
/*
        if (disableRatingCount) {
            improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), !controlObject.isDisableRatingCount(), ll_tap_text, view);
//            setViewDisable(view, false);
        }
*/
    }

  /*  public boolean isSelectRatingItemType() {
        return controlObject.isSelectRatingItemType();
    }

    public void setSelectRatingItemType(boolean selectRatingItemType) {
        controlObject.setSelectRatingItemType(selectRatingItemType);
    }*/

    public String getRatingType() {
        return controlObject.getRatingType();
    }

    public void setRatingType(String ratingType) {
        if (ratingType.equalsIgnoreCase("1") || ratingType.equalsIgnoreCase("Star")) {
            controlObject.setRatingType("Star");
            ratingBar.setVisibility(View.VISIBLE);
            smileRating.setVisibility(View.GONE);
            ratingBar.setNumStars(Integer.parseInt(controlObject.getRatingItemCount()));
            ratingBar.setTag(controlObject.getControlName());

        } else if (ratingType.equalsIgnoreCase("2") || ratingType.equalsIgnoreCase("Smiley")) {
            controlObject.setRatingType("Smiley");
            ratingBar.setVisibility(View.GONE);
            smileRating.setVisibility(View.VISIBLE);
//            setSelectedEmoji(Integer.parseInt(controlObject.getRatingItemCount()));
            smileRating.setRating(SmileyRating.Type.NONE);
            if(controlObject.isDisable()){
                smileRating.disallowSelection(true);
            }

        }


    }

    public boolean isCustomItemNames() {
        return controlObject.isCustomItemNames();
    }

    public void setCustomItemNames(boolean customItemNames) {
        controlObject.setCustomItemNames(customItemNames);
    }

    public void setRatingError() {
  /*  Drawable drawableReview = ratingBar.getProgressDrawable();
    drawableReview.setColorFilter(ContextCompat.getColor(context,R.color.control_error_color),
            PorterDuff.Mode.SRC_ATOP);*/
        int color = ContextCompat.getColor(context, R.color.white);
        int Seccolor = ContextCompat.getColor(context, R.color.control_error_color);
        ratingBar.setSecondaryProgressTintList(ColorStateList.valueOf(color));
        ratingBar.setProgressBackgroundTintList(ColorStateList.valueOf(Seccolor));

/*
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(!controlObject.isDisable()) {
                    ct_alertRating.setVisibility(View.GONE);
                    int color = ContextCompat.getColor(context, R.color.rating_default);
                    ratingBar.setProgressBackgroundTintList(ColorStateList.valueOf(color));
                    strRatingMain = String.valueOf(rating);
                    controlObject.setText(strRatingMain);
                }
            }
        });
*/


    }


    public CustomTextView getTv_displayName() {
        return tv_displayName;
    }


    public LinearLayout getLl_tap_text() { // linear for taptext

        return ll_tap_text;
    }

    public LinearLayout getLl_label() { // linear for taptext

        return ll_label;
    }

    //Change

//    public EmojiRatingBar getSmileRating() {
//
//        return smileRating;
//    }

    public SmileyRating getSmileRating() {

        return smileRating;
    }


    public ControlObject getControlObject() {
        return controlObject;
    }

    public String setSelectedSmiely(int count) {

        String strValue = "0.0";
        if (count == 1) {
            smileRating.setRating(SmileyRating.Type.TERRIBLE);
            strValue = "1";
        } else if (count == 2) {
            smileRating.setRating(SmileyRating.Type.BAD);
            strValue = "2";
        } else if (count == 3) {
            smileRating.setRating(SmileyRating.Type.OKAY);
            strValue = "3";
        } else if (count == 4) {
            smileRating.setRating(SmileyRating.Type.GOOD);
            strValue = "4";
        } else if (count == 5) {
            smileRating.setRating(SmileyRating.Type.GREAT);
            strValue = "5";
        } else {
            strValue = "0.0";
            smileRating.setRating(SmileyRating.Type.NONE);
        }
        controlObject.setText(strValue);

        return strValue;
    }

    public String getSelectedSmiely(SmileyRating.Type type) {

        if (type.equals(SmileyRating.Type.TERRIBLE)) {
            strRatingMain = "1";
            Log.i(TAG, "Terrible");
        } else if (type.equals(SmileyRating.Type.BAD)) {
            strRatingMain = "2";
            Log.i(TAG, "Bad");
        } else if (type.equals(SmileyRating.Type.OKAY)) {
            strRatingMain = "3";
            Log.i(TAG, "Okay");
        } else if (type.equals(SmileyRating.Type.GOOD)) {
            strRatingMain = "4";
            Log.i(TAG, "Good");
        } else if (type.equals(SmileyRating.Type.GREAT)) {
            strRatingMain = "5";
            Log.i(TAG, "Great");
        }
        controlObject.setText(strRatingMain);

        return strRatingMain;
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
