package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Java_Beans.AppObjects;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.adapters.OnlyDayNameAdapter;
import com.bhargo.user.controls.variables.DateVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.ItemClickListener;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.google.android.material.textfield.TextInputLayout;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import nk.bluefrog.library.utils.Helper;

public class Calendar implements ItemClickListener, UIVariables, DateVariables {

    public static final String[] MONTH_NAMES = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static final String[] MONTH_FULL_NAMES = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};
    private static final String TAG = "Calendar";
    private final int calendarTAG = 0;
    Context context;
    ControlObject controlObject;
    LinearLayout linearLayout;
    CustomEditText ce_TextType;
    boolean isCalendarDisplayType;
    ArrayList<AppObjects> dayNamesArrayList = new ArrayList<AppObjects>();
    SimpleDateFormat sdfDMY = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE", Locale.getDefault());
            Date date = new Date(selectedYear, selectedMonth, selectedDay - 1);
            String dayOfWeek = simpledateformat.format(date);

        }
    };
    CustomTextView ct_alertTypeCalender;
    ImageView iv_textTypeImage;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    ImproveHelper improveHelper;
    private ImageView cameraImageView;
    private java.util.Calendar myCalendar;
    private String strSelectedDate;
    private CustomTextView tv_tapTextType;
    private CustomTextView tv_displayName, tv_hint, ct_showText;
    private ImageView iv_mandatory;
    private Dialog onlyDayAlertDialog;
    private TextView tv_selectedName, tv_DayCancel, tv_DayOk;
    private View view;
    private LinearLayout ll_textInput, ll_tap_text, ll_label, ll_displayName, ll_main_inside, ll_control_ui, layout_control;
    private String finalDateWithServerFormat;
    /*Date Picker*/
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(java.util.Calendar.YEAR, year);

            myCalendar.set(java.util.Calendar.MONTH, monthOfYear);

            myCalendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);

            updateDateLabel();
        }
    };

    public Calendar(Context context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
        this.context = context;
        this.controlObject = controlObject;

        this.SubformFlag = SubformFlag;


        this.SubformName = SubformName;
        improveHelper = new ImproveHelper(context);
        initView();
    }

    public void initView() {
        try {
            myCalendar = java.util.Calendar.getInstance();
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            dayNamesArrayList.add(new AppObjects("Sunday"));
            dayNamesArrayList.add(new AppObjects("Monday"));
            dayNamesArrayList.add(new AppObjects("Tuesday"));
            dayNamesArrayList.add(new AppObjects("Wednesday"));
            dayNamesArrayList.add(new AppObjects("Thursday"));
            dayNamesArrayList.add(new AppObjects("Friday"));
            dayNamesArrayList.add(new AppObjects("Saturday"));

            addCalendarStrip(context);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getCalnderView() {
        return linearLayout;
    }

    public static String getDateFormatInYYYYMMDD(String date) {

            if (date == null)
                return date;
            String[] temp = null;
            if (date.contains("/")) {
                temp = date.split("\\/");
            } else if (date.contains("-")) {
                temp = date.split("\\-");
            } else {
//            temp = new String[Integer.parseInt(date)] ;
                return date;
            }
            if (temp[0].length() > 2) {
                return Integer.parseInt(temp[0]) + "-" + (Integer.parseInt(temp[1])) + "-" + Integer.parseInt(temp[2]);
            } else {
                return Integer.parseInt(temp[2]) + "-" + (Integer.parseInt(temp[1])) + "-" + Integer.parseInt(temp[0]);
            }
    }

    /*Date selection Onclick*/
    public void mMyDateSelection() {
        try {
      /*  if(ce_TextType.getText()!=null&&!ce_TextType.getText().toString().contentEquals("")){

        }*/
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DatePickerDialog, date,
                    myCalendar.get(java.util.Calendar.YEAR),
                    myCalendar.get(java.util.Calendar.MONTH),
                    myCalendar.get(java.util.Calendar.DAY_OF_MONTH)


            );
//            datePickerDialog.getDatePicker().getTouchables().get(0).performClick();/*Only years Display in andoird*/
            if (controlObject.isEnableAfterCurrentDate()) {
                myCalendar = java.util.Calendar.getInstance();
                datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis()); // Disable previous dates except current date
            }
            if (controlObject.isEnableBeforeCurrentDate()) {
                myCalendar = java.util.Calendar.getInstance();
                datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis()); // Disable previous dates except current date
            }

            if (controlObject.isEnableBetweenStartAndEndDate()) {
                if (controlObject.getStartDate() != null && controlObject.getStartDate().contains("-")) {
                    Log.d(TAG, "mMyDateSelectionStartDate: "+controlObject.getStartDate());
                    java.util.Calendar inst = java.util.Calendar.getInstance();
                    inst.set(Integer.parseInt(controlObject.getStartDate().split("-")[2])
                            , Integer.parseInt(controlObject.getStartDate().split("-")[1])
                            , Integer.parseInt(controlObject.getStartDate().split("-")[0]));
                    ;
//                    datePickerDialog.getDatePicker().setMinDate(inst.getTimeInMillis());
                    datePickerDialog.getDatePicker().setMinDate(convertDateToMilliseconds(controlObject.getStartDate()));
                }

                if (controlObject.getEndDate() != null && controlObject.getEndDate().contains("-")) {
                    Log.d(TAG, "mMyDateSelectionEndDate: "+controlObject.getEndDate());
                    java.util.Calendar inst = java.util.Calendar.getInstance();
                    inst.set(Integer.parseInt(controlObject.getEndDate().split("-")[2])
                            , Integer.parseInt(controlObject.getEndDate().split("-")[1])
                            , Integer.parseInt(controlObject.getEndDate().split("-")[0]));
//                    datePickerDialog.getDatePicker().setMaxDate(inst.getTimeInMillis());
                    datePickerDialog.getDatePicker().setMaxDate(convertDateToMilliseconds(controlObject.getEndDate()));
                }
            }

            datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Apply", datePickerDialog);

            datePickerDialog.show();
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "mMyDateSelection", e);
        }
    }

    private void updateDateLabel() {
        try {
//        SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
//        SimpleDateFormat sdfYMD = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

            SimpleDateFormat sdfYMDDisplay = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            ce_TextType.setVisibility(View.VISIBLE);
            strSelectedDate = sdfYMDDisplay.format(myCalendar.getTime());
            ce_TextType.setText(strSelectedDate);
//            ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_active_background));
            improveHelper.controlFocusBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), true, ll_tap_text, view);
            ct_alertTypeCalender.setText("");
            ct_alertTypeCalender.setVisibility(View.GONE);
            finalDateWithServerFormat = sdfYMD.format(myCalendar.getTime());
            setSelectedDate(strSelectedDate);

            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                if (AppConstants.EventCallsFrom == 1) {
                    AppConstants.EventFrom_subformOrNot = SubformFlag;
                    if (SubformFlag) {
                        AppConstants.SF_Container_position = SubformPos;
                        AppConstants.Current_ClickorChangeTagName = SubformName;
                    }
                    AppConstants.GlobalObjects.setCurrent_GPS("");
                    ((MainActivity) context).ChangeEvent(ce_TextType);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "updateDateLabel", e);
        }
    }

    public String getFinalDateWithServerFormat() {
        return finalDateWithServerFormat;
    }

    public void setFinalDateWithServerFormat(String date) {
        SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        if (date.contains("T")) {
            finalDateWithServerFormat = date.split("T")[0];
        }

    }


    public void addCalendarStrip(final Context context) {
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

            view.setTag(calendarTAG);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            layout_control = view.findViewById(R.id.layout_control);
            ll_textInput = view.findViewById(R.id.ll_tap_text);
            ll_label = view.findViewById(R.id.ll_label);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            ll_textInput.setTag(controlObject.getControlName());
            ll_tap_text = view.findViewById(R.id.ll_tap_text);
            tv_tapTextType = view.findViewById(R.id.tv_tapTextType);
            ce_TextType = view.findViewById(R.id.ce_TextType);
            ce_TextType.setEnabled(false);
            ce_TextType.setTag(controlObject.getControlName());
            tv_tapTextType.setText("Tap to select date");
            tv_tapTextType.setVisibility(View.GONE);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            ct_alertTypeCalender = view.findViewById(R.id.ct_alertTypeText);

            iv_textTypeImage = view.findViewById(R.id.iv_textTypeImage);
            iv_textTypeImage.setVisibility(View.VISIBLE);
            iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_calender));
            ll_textInput.setVisibility(View.VISIBLE);
            ce_TextType.setVisibility(View.VISIBLE);
            ct_showText = view.findViewById(R.id.ct_showText);

            iv_textTypeImage.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {

                    calendarClickOptions(controlObject);
//                    ce_TextType.setFocusable(true);

//                if (controlObject.isOnChangeEventExists()&&!AppConstants.Initialize_Flag) {
//                    if (AppConstants.EventCallsFrom == 1) {
//                        ((MainActivity) context).ChangeEvent(v);
//                    }
//                }
                }
            });

            ce_TextType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calendarClickOptions(controlObject);
                }
            });

            ce_TextType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    improveHelper.controlFocusBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), hasFocus, ll_tap_text, view);
                }
            });


//        ce_TextType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (controlObject.isGetYearFromSelection()) {
//                    onlyYear();
//                } else if (controlObject.isGetMonthFromSelection()) {
//                    onlyMonth();
//                } else if (controlObject.isGetDayFromSelection()) {
//                    onlyDayName();
//                } else if (controlObject.isGetYearFromSelection() && controlObject.isGetMonthFromSelection()
//                        && controlObject.isGetDayFromSelection() && controlObject.isGetDateFromSelection()) {
//                    mMyDateSelection();
//                } else {
//                    mMyDateSelection();
//                }
//
//
//                if (controlObject.isOnChangeEventExists()) {
//                    if (AppConstants.EventCallsFrom == 1) {
//                        ((MainActivity) context).ChangeEvent(v);
//                    }
//                }
//            }
//        });

            setControlValues();
            linearLayout.addView(view);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addCalendarStrip", e);
        }
    }

    public void Clear() {
        try {
            ll_textInput.setVisibility(View.VISIBLE);
            ce_TextType.setVisibility(View.VISIBLE);
            ce_TextType.setText("");
            ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.control_default_background));
            setSelectedDate("");
            ct_alertTypeCalender.setText("");
            ct_alertTypeCalender.setVisibility(View.GONE);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "Clear", e);
        }
    }

    private void setControlValues() {
        try {
            if (controlObject.getDisplayNameAlignment() != null) {
                tv_tapTextType.setVisibility(View.GONE);

                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("1")) {
                    ce_TextType.setEnabled(true);
                    tv_displayName.setVisibility(View.GONE);
                    iv_textTypeImage.setVisibility(View.GONE);
                    TextInputLayout til_task_name = view.findViewById(R.id.til_TextType);
                    til_task_name.setHint(controlObject.getDisplayName());
                    ce_TextType.setText("");
//                    ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_default_background));
                    setSelectedDate("");
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {
//                    ce_TextType.setHint(controlObject.getDisplayName());
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {
//                    ce_TextType.setText(controlObject.getDisplayName());
//                    ce_TextType.setHint(controlObject.getDisplayName());
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

            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            } else {
                ll_displayName.setVisibility(View.VISIBLE);
            }

            if (controlObject.isEnableBetweenStartAndEndDate()) {
                Log.d("CalendarValidators", controlObject.getStartDate() + "," + controlObject.getEndDate() + "," +
                        controlObject.getBetweenStartAndEndDateError());
            }
            if (controlObject.isEnableBeforeCurrentDate()) {
                Log.d("CalendarValidators", controlObject.getBeforeCurrentDateError());
            }
            if (controlObject.isEnableAfterCurrentDate()) {
                Log.d("CalendarValidators", controlObject.getAfterCurrentDateError());
            }
            if (controlObject.isReadOnly()) {
//                setViewDisable(view, false);
//                setViewDisableOrEnableDefault(context,ll_main_inside, false);
                improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), !controlObject.isReadOnly(), ll_tap_text, view);
            }

            if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().isEmpty()) {
                String[] defaultValue = controlObject.getDefaultValue().split("T");
                ce_TextType.setText(defaultValue[0]);
                improveHelper.controlFocusBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), true, ll_tap_text, view);
//                ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_active_background));
                setSelectedDate(defaultValue[0]);
//            ce_TextType.setText(controlObject.getDefaultValue());
                ct_alertTypeCalender.setText("");
                ct_alertTypeCalender.setVisibility(View.GONE);
                Date dateFormats = new Date();

                SimpleDateFormat getFormatSelected = null;
                if (controlObject.isEnableCurrentDateAsDefault() && controlObject.isGetYearFromSelection()) {
                    getFormatSelected = new SimpleDateFormat("yyyy");
                    String currentYear = getFormatSelected.format(dateFormats);
                    ce_TextType.setText(currentYear);
                    finalDateWithServerFormat = currentYear;
                    setSelectedDate(finalDateWithServerFormat);
                } else if (controlObject.isEnableCurrentDateAsDefault() && controlObject.isGetMonthFromSelection()) {
                    getFormatSelected = new SimpleDateFormat("MM");
                    String strMonth = getFormatSelected.format(dateFormats);
                    ce_TextType.setText(strMonth);
                    finalDateWithServerFormat = strMonth;
                    setSelectedDate(finalDateWithServerFormat);
                } else if (controlObject.isEnableCurrentDateAsDefault() && controlObject.isGetDayFromSelection()) {
                    getFormatSelected = new SimpleDateFormat("dd");
                    String strDay = getFormatSelected.format(dateFormats);
                    ce_TextType.setText(strDay);
                    finalDateWithServerFormat = strDay;
                    setSelectedDate(finalDateWithServerFormat);
                } else {

                    if (controlObject.isEnableCurrentDateAsDefault()) {

                        ce_TextType.setText(sdfDMY.format(java.util.Calendar.getInstance().getTime()));
                        setSelectedDate(sdfDMY.format(java.util.Calendar.getInstance().getTime()));
                        defaultValue[0] = sdfDMY.format(java.util.Calendar.getInstance().getTime());
                    }

                    String splitDate = defaultValue[0];
                    if (splitDate.contains("-")) {
                        if (splitDate.split("-")[0].length() != 4) {
                            String date = splitDate.split("-")[0];
                            String month = splitDate.split("-")[1];
                            String year = splitDate.split("-")[2];

                            myCalendar.set(java.util.Calendar.YEAR, Integer.parseInt(year));

                            myCalendar.set(java.util.Calendar.MONTH, Integer.parseInt(month) - 1);

                            myCalendar.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(date));


                            SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                            finalDateWithServerFormat = sdfYMD.format(myCalendar.getTime());
                        } else {
                            String date = splitDate.split("-")[2];
                            String month = splitDate.split("-")[1];
                            String year = splitDate.split("-")[0];

                            myCalendar.set(java.util.Calendar.YEAR, Integer.parseInt(year));

                            myCalendar.set(java.util.Calendar.MONTH, Integer.parseInt(month) - 1);

                            myCalendar.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(date));


                            SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                            finalDateWithServerFormat = sdfYMD.format(myCalendar.getTime());
                        }

                    } else if (splitDate.contains("/")) {
                        if (splitDate.split("/")[0].length() != 4) {
                            String date = splitDate.split("/")[0];
                            String month = splitDate.split("/")[1];
                            String year = splitDate.split("/")[2];
                            myCalendar.set(java.util.Calendar.YEAR, Integer.parseInt(year));

                            myCalendar.set(java.util.Calendar.MONTH, Integer.parseInt(month) - 1);

                            myCalendar.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(date));


                            SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                            finalDateWithServerFormat = sdfYMD.format(myCalendar.getTime());


                        } else {
                            String date = splitDate.split("/")[2];
                            String month = splitDate.split("/")[1];
                            String year = splitDate.split("/")[0];

                            myCalendar.set(java.util.Calendar.YEAR, Integer.parseInt(year));

                            myCalendar.set(java.util.Calendar.MONTH, Integer.parseInt(month) - 1);

                            myCalendar.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(date));

                            SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                            finalDateWithServerFormat = sdfYMD.format(myCalendar.getTime());
                        }
                    }
                }
                controlObject.setSelectedDate(finalDateWithServerFormat);
            }


            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    private void calendarClickOptions(ControlObject controlObject) {
        if (controlObject.isGetYearFromSelection() && controlObject.getDefaultValue() != null) { //
            onlyYear();
        } else if (controlObject.isGetMonthFromSelection() && controlObject.getDefaultValue() != null) {
            onlyMonth();
        } else if (controlObject.isGetDayFromSelection() && controlObject.getDefaultValue() != null) {
            onlyDayName();
        } else if (controlObject.isGetYearFromSelection() && controlObject.isGetMonthFromSelection()
                && controlObject.isGetDayFromSelection() && controlObject.isGetDateFromSelection() && controlObject.getDefaultValue() != null) {
            mMyDateSelection();
        } else if (controlObject.isGetYearFromSelection()) {
            onlyYear();
        } else if (controlObject.isGetMonthFromSelection()) {
            onlyMonth();
        } else if (controlObject.isGetDayFromSelection()) {
            onlyDayName();
        } else if (controlObject.isGetYearFromSelection() && controlObject.isGetMonthFromSelection()
                && controlObject.isGetDayFromSelection() && controlObject.isGetDateFromSelection()) {
            mMyDateSelection();
        } else {
            mMyDateSelection();
        }

    }

    public void onlyMonth() {

        int minMonth = 0;
        int maxMoth = 11;
        try {
            MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(context, new MonthPickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(int selectedMonth, int selectedYear) {
                    Log.d("onlyMonthLOG:", String.valueOf(MONTH_FULL_NAMES[selectedMonth]));
                    finalDateWithServerFormat = String.valueOf(MONTH_FULL_NAMES[selectedMonth]);
                    ce_TextType.setText(String.valueOf(MONTH_FULL_NAMES[selectedMonth]));
//                    ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_active_background));
                    improveHelper.controlFocusBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), true, ll_tap_text, view);
                    setSelectedDate(String.valueOf(MONTH_FULL_NAMES[selectedMonth]));
                    ct_alertTypeCalender.setText("");
                    ct_alertTypeCalender.setVisibility(View.GONE);
                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                        if (AppConstants.EventCallsFrom == 1) {
                            AppConstants.EventFrom_subformOrNot = SubformFlag;
                            if (SubformFlag) {
                                AppConstants.SF_Container_position = SubformPos;
                                AppConstants.Current_ClickorChangeTagName = SubformName;

                            }
                            AppConstants.GlobalObjects.setCurrent_GPS("");
                            ((MainActivity) context).ChangeEvent(ce_TextType);
                        }
                    }

                }
            }, /* activated number in year */ myCalendar.get(java.util.Calendar.YEAR), myCalendar.get(java.util.Calendar.MONTH));

            if (controlObject.isEnableAfterCurrentDate()) {
                minMonth = myCalendar.get(java.util.Calendar.MONTH);
            }
            if (controlObject.isEnableBeforeCurrentDate()) {
                maxMoth = myCalendar.get(java.util.Calendar.MONTH);
            }
            builder.showMonthOnly()
                    .setMonthRange(minMonth, maxMoth)
                    .build()
                    .show();
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "onlyMonth", e);
        }
    }

    public void onlyYear() {
        int minYear = 1000;
        int maxYear = 3000;
        try {
            MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(context, new MonthPickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(int selectedMonth, int selectedYear) {
                    Log.d("onlyYearLOG:", String.valueOf(selectedYear));
                    finalDateWithServerFormat = String.valueOf(selectedYear);
                    ce_TextType.setText(String.valueOf(selectedYear));
//                    ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_active_background));
                    improveHelper.controlFocusBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), true, ll_tap_text, view);
                    setSelectedDate(String.valueOf(selectedYear));
                    ct_alertTypeCalender.setText("");
                    ct_alertTypeCalender.setVisibility(View.GONE);
                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                        if (AppConstants.EventCallsFrom == 1) {
                            AppConstants.EventFrom_subformOrNot = SubformFlag;
                            if (SubformFlag) {
                                AppConstants.SF_Container_position = SubformPos;
                                AppConstants.Current_ClickorChangeTagName = SubformName;

                            }
                            AppConstants.GlobalObjects.setCurrent_GPS("");
                            ((MainActivity) context).ChangeEvent(ce_TextType);
                        }
                    }
                }
            }, myCalendar.get(java.util.Calendar.YEAR), 0);

            if (controlObject.isEnableAfterCurrentDate()) {
                minYear = myCalendar.get(java.util.Calendar.YEAR);
            }
            if (controlObject.isEnableBeforeCurrentDate()) {
                maxYear = myCalendar.get(java.util.Calendar.YEAR);
            }

            builder.showYearOnly()
                    .setYearRange(minYear, maxYear)
                    .build()
                    .show();
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "onlyYear", e);
        }
    }

    public void onlyDayName() {
        try {
            onlyDayAlertDialog = new Dialog(context, R.style.AlertDialogLight);

            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.only_day_alert_dialog, null);

            RecyclerView onlyDayRecyclerView = view.findViewById(R.id.onlyDayRecyclerView);
            tv_selectedName = view.findViewById(R.id.tv_selectedName);
            tv_DayCancel = view.findViewById(R.id.tv_DayCancel);
            tv_DayOk = view.findViewById(R.id.tv_DayOk);

            // set a LinearLayoutManager with default orientation
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            onlyDayRecyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

            OnlyDayNameAdapter onlyDayNameAdapter = new OnlyDayNameAdapter(context, dayNamesArrayList);

            onlyDayRecyclerView.setAdapter(onlyDayNameAdapter);
            onlyDayNameAdapter.setCustomClickListener(this);

            tv_DayOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                SimpleDateFormat sdfDay = new SimpleDateFormat("dd", Locale.US);
                    Log.d("onCustomClickOK:", tv_selectedName.getText().toString());
                    if (!tv_selectedName.getText().toString().equalsIgnoreCase("Days")) {
                        ce_TextType.setText(tv_selectedName.getText().toString());
//                        ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_active_background));
                        improveHelper.controlFocusBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), true, ll_tap_text, view);
                        finalDateWithServerFormat = tv_selectedName.getText().toString();
                        setSelectedDate(tv_selectedName.getText().toString());
                        ct_alertTypeCalender.setText("");
                        ct_alertTypeCalender.setVisibility(View.GONE);
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                if (SubformFlag) {
                                    AppConstants.SF_Container_position = SubformPos;
                                    AppConstants.Current_ClickorChangeTagName = SubformName;

                                }
                                AppConstants.GlobalObjects.setCurrent_GPS("");
                                ((MainActivity) context).ChangeEvent(ce_TextType);
                            }
                        }
                    }
                    onlyDayAlertDialog.dismiss();

                }
            });
            tv_DayCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onlyDayAlertDialog.dismiss();
                }
            });
            onlyDayAlertDialog.setContentView(view);
            onlyDayAlertDialog.show();
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "onlyDayName", e);
        }
    }


    @Override
    public void onCustomClick(Context context, View view, int position, String s) {
        Log.d("CalendaronCustomClick:", dayNamesArrayList.get(position).getAPP_DayName());
        tv_selectedName.setText(dayNamesArrayList.get(position).getAPP_DayName());
    }

    public String getCalendarValue() {

        return ce_TextType.getText().toString();
    }

    public CustomTextView setAlertCalendar() {

        return ct_alertTypeCalender;
    }

    public CustomTextView getTv_tapTextType() {

        return tv_tapTextType;
    }

    public CustomEditText getCe_TextType() {

        return ce_TextType;
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

    @Override
    public String getSelectedDate() {
        return controlObject.getSelectedDate();
    }

    @Override
    public void setSelectedDate(String date) {
       /* if(date==null ){
            strSelectedDate = sdfDMY.format(myCalendar.getTime());
            ce_TextType.setText(strSelectedDate);
            finalDateWithServerFormat = getDateFormatInYYYYMMDD(strSelectedDate);
        } else if(date.trim().equals("")){
            strSelectedDate = sdfDMY.format(myCalendar.getTime());
            ce_TextType.setText(strSelectedDate);
            finalDateWithServerFormat = getDateFormatInYYYYMMDD(strSelectedDate);
        }else {
            strSelectedDate = sdfDMY.format(myCalendar.getTime());
            ce_TextType.setText(strSelectedDate);
//            ce_TextType.setText(date);
            finalDateWithServerFormat = getDateFormatInYYYYMMDD(date);
        }*/
//        ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.control_active_background));
        if(date!=null && !date.isEmpty()){
            ce_TextType.setText(""+date);
            finalDateWithServerFormat = getDateFormatInYYYYMMDD(date);
        }
        improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),true,ll_tap_text,view);
        controlObject.setSelectedDate(finalDateWithServerFormat);
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

        return !controlObject.isReadOnly();
    }

    @Override
    public void setEnabled(boolean enabled) {
//        setViewDisable(view, !enabled);
//        controlObject.setDisable(!enabled);
        controlObject.setReadOnly(!enabled);
//        setViewDisableOrEnableDefault(context,view, enabled);
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),enabled,ll_tap_text,view);
//        improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), enabled, ll_tap_text, view);
    }

    @Override
    public void clean() {
        Clear();
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

    /*General*/
    /*displayName,hint,defaultValue,enableCurrentDateAsDefault*/

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

    public String getDefaultValue() {
        return controlObject.getDefaultValue();
    }

    public void setDefaultValue(String defaultValue) {
        setSelectedDate(defaultValue);
        controlObject.setDefaultValue(defaultValue);
    }

    public boolean getEnableCurrentDateAsDefault() {
        return controlObject.isEnableCurrentDateAsDefault();
    }

    public void setEnableCurrentDateAsDefault(boolean enableCurrentDateAsDefault) {
        controlObject.setEnableCurrentDateAsDefault(enableCurrentDateAsDefault);
    }

    /*Validations*/
    /*mandatory,mandatoryErrorMessage,uniqueField,uniqueFieldErrorMessage
    enableBetweenStartAndEndDate,startDate,endDate,enableBeforeCurrentDate,enableAfterCurrentDate*/
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

    public boolean isEnableBetweenStartAndEndDate() {
        return controlObject.isEnableBetweenStartAndEndDate();
    }

    public void setEnableBetweenStartAndEndDate(boolean enableBetweenStartAndEndDate) {
        controlObject.setEnableBetweenStartAndEndDate(enableBetweenStartAndEndDate);
    }

    public String getStartDate() {
        return controlObject.getStartDate();
    }

    public void setStartDate(String startDate) {
        controlObject.setStartDate(startDate);
    }

    public String getEndDate() {
        return controlObject.getEndDate();
    }

    public void setEndDate(String endDate) {
        controlObject.setEndDate(endDate);
    }

    public boolean isEnableBeforeCurrentDate() {
        return controlObject.isEnableBeforeCurrentDate();
    }

    public void setEnableBeforeCurrentDate(boolean enableBeforeCurrentDate) {
        controlObject.setEnableBeforeCurrentDate(enableBeforeCurrentDate);
    }

    public boolean isEnableAfterCurrentDate() {
        return controlObject.isEnableAfterCurrentDate();
    }

    public void setEnableAfterCurrentDate(boolean enableAfterCurrentDate) {
        controlObject.setEnableAfterCurrentDate(enableAfterCurrentDate);
    }

    /*Options*/
    /*hideDisplayName,getYearFromSelection,getMonthFromSelection,getDayFromSelection
    invisible/visibility,disable/enabled
     */

    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        ll_displayName.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
    }

    public boolean isGetYearFromSelection() {
        return controlObject.isGetYearFromSelection();
    }

    public void setGetYearFromSelection(boolean getYearFromSelection) {
        controlObject.setGetYearFromSelection(getYearFromSelection);

    }

    public boolean isGetMonthFromSelection() {
        return controlObject.isGetMonthFromSelection();
    }

    public void setGetMonthFromSelection(boolean getMonthFromSelection) {
        controlObject.setGetMonthFromSelection(getMonthFromSelection);

    }

    public boolean isGetDayFromSelection() {
        return controlObject.isGetDayFromSelection();
    }

    public void setGetDayFromSelection(boolean getDayFromSelection) {
        controlObject.setGetDayFromSelection(getDayFromSelection);

    }

    public boolean isGetDateFromSelection() {
        return controlObject.isGetDateFromSelection();
    }

    public void setGetDateFromSelection(boolean getDateFromSelection) {
        controlObject.setGetDateFromSelection(getDateFromSelection);
//        mMyDateSelection();
    }

    public LinearLayout getLl_tap_text() { // linear for taptext

        return ll_tap_text;
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

    /*ControlUI SettingsLayouts End*/

    public ControlObject getControlObject(){
        return controlObject;
    }

    public long convertDateToMilliseconds(String date)
    {
        //String date_ = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
            return timeInMilliseconds;
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }

}
