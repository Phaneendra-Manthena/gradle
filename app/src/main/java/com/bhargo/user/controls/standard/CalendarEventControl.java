package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.pxToDPControlUI;
import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.utils.DateUtils;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.CalendarEvent;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarEventControl implements UIVariables {

    private static final String TAG = "CalendarEventControl";
    public CalendarView calendarView;
    Activity context;
    ControlObject controlObject;
    LinearLayout linearLayout, ll_displayName, ll_main_inside, ll_control_ui, ll_tap_text;
    //    ConstraintLayout consLayout;
    ImageView iv_mandatory;
    String strSingleMessage, strMultipleMessage, SelectedDate = "", SelectedMessage = "";
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    ImproveHelper improveHelper;
    private View view;
    private int CalendarEventTAG;
    private CustomTextView tv_displayName, tv_hint,ct_showText;
    private int singleDay, singleMonth, singleYear, multipleDay, multipleMonth, multipleYear;

    //    public CalendarEventControl(Activity context) {
//
//        this.context = context;
//
//        inintViews();
//
//    }
    public CalendarEventControl(Activity context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {

        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        improveHelper = new ImproveHelper(context);
        inintViews();

    }

    private void inintViews() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            addCalendarEventStrip(context);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "inintViews", e);
        }
    }

    private void addCalendarEventStrip(Activity context) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = lInflater.inflate(R.layout.control_calendar_event, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                    view = linflater.inflate(R.layout.control_calendar_event_six, null);

                }
            } else {

                view = linflater.inflate(R.layout.control_calendar_event, null);
            }
            view.setTag(controlObject.getControlName());


            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            ll_tap_text = view.findViewById(R.id.ll_tap_text);
//            consLayout = view.findViewById(R.id.consLayout);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            ct_showText = view.findViewById(R.id.ct_showText);


//        List<EventDay> events = new ArrayList<>();
//        Calendar calendar = DateUtils.getCalendar();
//        calendar.set(Calendar.YEAR, 2020);
//        calendar.set(Calendar.MONTH, 4);
//        calendar.set(Calendar.DAY_OF_MONTH, 15);
//
//        events.add(new EventDay(calendar, R.drawable.bg_circle_calendar));

            calendarView = view.findViewById(R.id.calendarView);

            calendarView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);


            calendarView.setSelected(true);


//        calendarView.setEvents(events);
//        calendarView.setSelectedDates(getSelectedDays());

//        calendarView.setOnDayClickListener(new OnDayClickListener() {
//            @Override
//            public void onDayClick(EventDay eventDay) {
//                Log.d(TAG, "CalendarOnDayClick: " + eventDay.getCalendar().get(Calendar.DAY_OF_MONTH));
//                Log.d(TAG, "CalendarOnDayClick: " + eventDay.isEnabled());
//
//            }
//        });
//        Log.d(TAG, "addCalendarEventStrip: " + calendarView.getSelectedDates());
//        Log.d(TAG, "addCalendarEventStrip: "+calendarView.getCurrentPageDate().get(Calendar.MONTH));
            setCalendarEventValues(calendarView);


            linearLayout.addView(view);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "addCalendarEventStrip", e);
        }
    }

    public LinearLayout getCalendarEventLayout() {
        return linearLayout;
    }


    public void setCalendarEventValues(CalendarView calendarView) {
        try {
            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            } else {
                ll_displayName.setVisibility(View.GONE);
            }

            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            } else {
                ll_displayName.setVisibility(View.VISIBLE);
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

            if (controlObject.getCalendarEventType() != null && controlObject.getCalendarEventType().size() > 0) {
                calendarView.performClick();
                List<EventDay> events = new ArrayList<>();
//            List<Calendar> calendarList = new ArrayList<>();
                for (int i = 0; i < controlObject.getCalendarEventType().size(); i++) {
                    if (controlObject.getCalendarEventType().get(i).getEventType() != null) {


                        if (controlObject.getCalendarEventType().get(i).getEventType().equalsIgnoreCase("Single Date")) {
                            Log.d(TAG, "CalEvent_SingleDate: " + controlObject.getCalendarEventType().get(i).getEventDates());
                            Calendar calendar = Calendar.getInstance();
                            String[] strDateSplit = controlObject.getCalendarEventType().get(i).getEventDates().split("-");
                            if(strDateSplit[0].length()>2) {
                                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(strDateSplit[2]));
                                calendar.set(Calendar.MONTH, Integer.parseInt(strDateSplit[1]) - 1);
                                calendar.set(Calendar.YEAR, Integer.parseInt(strDateSplit[0]));
                                singleDay = Integer.parseInt(strDateSplit[2]);
                                singleMonth = Integer.parseInt(strDateSplit[1]) - 1;
                                singleYear = Integer.parseInt(strDateSplit[0]);
                            }else{
                                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(strDateSplit[0]));
                                calendar.set(Calendar.MONTH, Integer.parseInt(strDateSplit[1]) - 1);
                                calendar.set(Calendar.YEAR, Integer.parseInt(strDateSplit[2]));
                                singleDay = Integer.parseInt(strDateSplit[0]);
                                singleMonth = Integer.parseInt(strDateSplit[1]) - 1;
                                singleYear = Integer.parseInt(strDateSplit[2]);
                            }
//                        calendarList.add(calendar);
                            events.add(new EventDay(calendar, R.drawable.bg_circle_calendar));
                            strSingleMessage = controlObject.getCalendarEventType().get(i).getEventMessage();
                            Log.d(TAG, "SingleDateMessage: " + controlObject.getCalendarEventType().get(i).getEventMessage());
                        }


                        if (controlObject.getCalendarEventType().get(i).getEventType().equalsIgnoreCase("Multiple Dates")) {
                            Log.d(TAG, "CalEvent_MultiDates: " + controlObject.getCalendarEventType().get(i).getEventDates());
                            String[] strDateSplit = controlObject.getCalendarEventType().get(i).getEventDates().split("\\$");
                            Log.d(TAG, "DatesSplit: " + strDateSplit[0] + " , " + strDateSplit[1]);
//                        int daysCount = getCountOfDays(strDateSplit[0], strDateSplit[1]);
//                        Log.d(TAG, "DaysCount: " + daysCount);
                            List<String> stringListDates = datesBetween(strDateSplit[0], strDateSplit[1]);

                            List<EventDay> events2 = new ArrayList<>();
                            for (int j = 0; j < stringListDates.size(); j++) {
//                            Log.d(TAG, "stringListDates: " + stringListDates.get(j));
                                Calendar calendar2 = Calendar.getInstance();
                                String[] strSplitInstance = stringListDates.get(j).split("-");
                                Log.d(TAG, "MDatesSplit: " + strSplitInstance[0] + " , " + strSplitInstance[1] + " , " + strSplitInstance[2]);
                                if(strSplitInstance[0].length()>2) {
                                    calendar2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(strSplitInstance[2]));
                                    calendar2.set(Calendar.MONTH, Integer.parseInt(strSplitInstance[1]) - 1);
                                    calendar2.set(Calendar.YEAR, Integer.parseInt(strSplitInstance[0]));
                                    events.add(new EventDay(calendar2, R.drawable.bg_circle_multi_calendar));
                                    multipleDay = Integer.parseInt(strSplitInstance[2]);
                                    multipleMonth = Integer.parseInt(strSplitInstance[1]) - 1;
                                    multipleYear = Integer.parseInt(strSplitInstance[0]);
                                }else{
                                    calendar2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(strSplitInstance[0]));
                                    calendar2.set(Calendar.MONTH, Integer.parseInt(strSplitInstance[1]) - 1);
                                    calendar2.set(Calendar.YEAR, Integer.parseInt(strSplitInstance[2]));
                                    events.add(new EventDay(calendar2, R.drawable.bg_circle_multi_calendar));
                                    multipleDay = Integer.parseInt(strSplitInstance[0]);
                                    multipleMonth = Integer.parseInt(strSplitInstance[1]) - 1;
                                    multipleYear = Integer.parseInt(strSplitInstance[2]);
                                }
//                            calendarList.add(calendar2);
                            }

//                        calendarList.add();

                            strMultipleMessage = controlObject.getCalendarEventType().get(i).getEventMessage();
                            Log.d(TAG, "MultipleDateMessage: " + controlObject.getCalendarEventType().get(i).getEventMessage());
//                        calendarView.setEvents(events2);
//                        getSelectedDays(daysCount + 1);

                        }

                    }
//                calendarView.setSelectedDates(calendarList);
                    calendarView.setEvents(events);
                    calendarView.setOnDayClickListener(new OnDayClickListener() {
                        @Override
                        public void onDayClick(EventDay eventDay) {
                            SelectedDate = "";
                            SelectedMessage = "";

                            Calendar clickedDayCalendar = eventDay.getCalendar();
                            Log.d(TAG, "onDayClick: " + clickedDayCalendar.get(Calendar.DAY_OF_MONTH));

                            if (controlObject.getCalendarEventType() != null && controlObject.getCalendarEventType().size() > 0) {

                                List<EventDay> events = new ArrayList<>();
                                for (int i = 0; i < controlObject.getCalendarEventType().size(); i++) {
                                    if (controlObject.getCalendarEventType().get(i).getEventType() != null) {


                                        if (controlObject.getCalendarEventType().get(i).getEventType().equalsIgnoreCase("Single Date")) {
                                            Log.d(TAG, "CalEvent_SingleDate: " + controlObject.getCalendarEventType().get(i).getEventDates());
                                            Calendar calendar = Calendar.getInstance();
                                            String[] strDateSplit = controlObject.getCalendarEventType().get(i).getEventDates().split("-");
                                            if(strDateSplit[0].length()>2) {
                                                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(strDateSplit[2]));
                                                calendar.set(Calendar.MONTH, Integer.parseInt(strDateSplit[1]) - 1);
                                                calendar.set(Calendar.YEAR, Integer.parseInt(strDateSplit[0]));
                                                singleDay = Integer.parseInt(strDateSplit[2]);
                                                singleMonth = Integer.parseInt(strDateSplit[1]) - 1;
                                                singleYear = Integer.parseInt(strDateSplit[0]);
                                            }else{
                                                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(strDateSplit[0]));
                                                calendar.set(Calendar.MONTH, Integer.parseInt(strDateSplit[1]) - 1);
                                                calendar.set(Calendar.YEAR, Integer.parseInt(strDateSplit[2]));
                                                singleDay = Integer.parseInt(strDateSplit[0]);
                                                singleMonth = Integer.parseInt(strDateSplit[1]) - 1;
                                                singleYear = Integer.parseInt(strDateSplit[2]);
                                            }
                                            strSingleMessage = controlObject.getCalendarEventType().get(i).getEventMessage();
                                            SelectedDate = controlObject.getCalendarEventType().get(i).getEventDates();
                                            if (clickedDayCalendar.get(Calendar.DAY_OF_MONTH) == singleDay &&
                                                    clickedDayCalendar.get(Calendar.MONTH) == singleMonth &&
                                                    clickedDayCalendar.get(Calendar.YEAR) == singleYear) {

                                                SelectedMessage = strSingleMessage;
                                                calendarMessageAlertDialog(ImproveHelper.yyymmdd_To_ddmmyyy(controlObject.getCalendarEventType().get(i).getEventDates()), strSingleMessage);
//                                            ImproveHelper.showToastAlert(context, String.valueOf(strSingleMessage));
                                            }
                                        } else if (controlObject.getCalendarEventType().get(i).getEventType().equalsIgnoreCase("Multiple Dates")) {
                                            Log.d(TAG, "CalEvent_MultiDates: " + controlObject.getCalendarEventType().get(i).getEventDates());
                                            strMultipleMessage = controlObject.getCalendarEventType().get(i).getEventMessage();
                                            String[] strDateSplit = controlObject.getCalendarEventType().get(i).getEventDates().split("\\$");
                                            Log.d(TAG, "DatesSplit: " + strDateSplit[0] + " , " + strDateSplit[1]);
//                        int daysCount = getCountOfDays(strDateSplit[0], strDateSplit[1]);
//                        Log.d(TAG, "DaysCount: " + daysCount);
                                            List<String> stringListDates = datesBetween(strDateSplit[0], strDateSplit[1]);

                                            List<EventDay> events2 = new ArrayList<>();
                                            for (int j = 0; j < stringListDates.size(); j++) {
//                            Log.d(TAG, "stringListDates: " + stringListDates.get(j));
                                                Calendar calendar2 = Calendar.getInstance();
                                                String[] strSplitInstance = stringListDates.get(j).split("-");
                                                Log.d(TAG, "MDatesSplit: " + strSplitInstance[0] + " , " + strSplitInstance[1] + " , " + strSplitInstance[2]);
                                                /*calendar2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(strSplitInstance[0]));
                                                calendar2.set(Calendar.MONTH, Integer.parseInt(strSplitInstance[1]) - 1);
                                                calendar2.set(Calendar.YEAR, Integer.parseInt(strSplitInstance[2]));
*//*
                                                multipleDay = Integer.parseInt(strSplitInstance[0]);
                                                multipleMonth = Integer.parseInt(strSplitInstance[1]) - 1;
                                                multipleYear = Integer.parseInt(strSplitInstance[2]);
*//*
                                                multipleDay = Integer.parseInt(strSplitInstance[2]);
                                                multipleMonth = Integer.parseInt(strSplitInstance[1]) - 1;
                                                multipleYear = Integer.parseInt(strSplitInstance[0]);
                                                SelectedDate = stringListDates.get(j);*/
                                                if(strSplitInstance[0].length()>2) {
                                                    calendar2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(strSplitInstance[2]));
                                                    calendar2.set(Calendar.MONTH, Integer.parseInt(strSplitInstance[1]) - 1);
                                                    calendar2.set(Calendar.YEAR, Integer.parseInt(strSplitInstance[0]));
                                                    events.add(new EventDay(calendar2, R.drawable.bg_circle_multi_calendar));
                                                    multipleDay = Integer.parseInt(strSplitInstance[2]);
                                                    multipleMonth = Integer.parseInt(strSplitInstance[1]) - 1;
                                                    multipleYear = Integer.parseInt(strSplitInstance[0]);
                                                }else{
                                                    calendar2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(strSplitInstance[0]));
                                                    calendar2.set(Calendar.MONTH, Integer.parseInt(strSplitInstance[1]) - 1);
                                                    calendar2.set(Calendar.YEAR, Integer.parseInt(strSplitInstance[2]));
                                                    events.add(new EventDay(calendar2, R.drawable.bg_circle_multi_calendar));
                                                    multipleDay = Integer.parseInt(strSplitInstance[0]);
                                                    multipleMonth = Integer.parseInt(strSplitInstance[1]) - 1;
                                                    multipleYear = Integer.parseInt(strSplitInstance[2]);
                                                }
                                                if (clickedDayCalendar.get(Calendar.DAY_OF_MONTH) == multipleDay &&
                                                        clickedDayCalendar.get(Calendar.MONTH) == multipleMonth &&
                                                        clickedDayCalendar.get(Calendar.YEAR) == multipleYear) {
                                                    SelectedMessage = strMultipleMessage;
                                                    calendarMessageAlertDialog(ImproveHelper.yyymmdd_To_ddmmyyy(stringListDates.get(j)), strMultipleMessage);
                                                    //                                                ImproveHelper.showToastAlert(context, String.valueOf(strMultipleMessage));
                                                }
                                            }
                                        }
                                    }
                                }
                            }


                            if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.EventFrom_subformOrNot = SubformFlag;
                                    if (SubformFlag) {
                                        AppConstants.SF_Container_position = SubformPos;
                                        AppConstants.Current_ClickorChangeTagName = SubformName;

                                    }
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ClickEvent(view);
                                }
                            }
                        }
                    });
                }
                controlObject.setText(getEventDates());

            }

            setDisplaySettings(context, tv_displayName, controlObject);

        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setCalendarEventValues", e);
        }
    }

    public List<String> datesBetween(String bStartDate, String bEndDate) {

        // TODO Auto-generated method stub
        List<Date> dates = new ArrayList<Date>();
        List<String> betweenDates = new ArrayList<String>();

        String str_date = bStartDate;
        String end_date = bEndDate;
        try {
            DateFormat formatter;

            // formatter = new SimpleDateFormat("dd-MM-yyyy");
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            if(str_date.split("-")[0].length()<=2){
                formatter = new SimpleDateFormat("dd-MM-yyyy");
            }
            Date startDate = null;
            try {
                startDate = formatter.parse(str_date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Date endDate = null;
            try {
                endDate = formatter.parse(end_date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
            long endTime = endDate.getTime(); // create your endtime here, possibly using Calendar or Date
            long curTime = startDate.getTime();
            while (curTime <= endTime) {
                dates.add(new Date(curTime));
                curTime += interval;
            }
            for (int i = 0; i < dates.size(); i++) {
                Date lDate = dates.get(i);
                String ds = formatter.format(lDate);
                betweenDates.add(ds);
                System.out.println("BetweenDatesMethod: " + ds);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "datesBetween", e);
        }

        return betweenDates;
    }


    private List<Calendar> getSelectedDays(int size) {
        List<Calendar> calendars = new ArrayList<>();
        List<EventDay> events2 = new ArrayList<>();

        Calendar calendar2 = DateUtils.getCalendar();
//        calendar2.set(Calendar.DAY_OF_MONTH, );
//        calendar2.set(Calendar.MONTH, );
//        calendar2.set(Calendar.YEAR, );
        for (int i = 0; i < size; i++) {

            calendar2.add(Calendar.DAY_OF_MONTH, i);
            events2.add(new EventDay(calendar2, R.drawable.bg_circle_calendar));
            calendarView.setEvents(events2);
            calendars.add(calendar2);

        }

        return calendars;
    }


    public int getCountOfDays(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }


    /*Calendar todayCal = Calendar.getInstance();
    int todayYear = todayCal.get(Calendar.YEAR);
    int today = todayCal.get(Calendar.MONTH);
    int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
    */

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return (int) dayCount /*("" + (int) dayCount + " Days")*/;
    }


    @SuppressLint("WrongConstant")
    public void calendarMessageAlertDialog(String strSelected_Date, String strCalendarMessage) {
        try {
            Dialog messageAD = new Dialog(context);
            messageAD.requestWindowFeature(Window.FEATURE_NO_TITLE);
            messageAD.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
            messageAD.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            WindowManager.LayoutParams wmlp = messageAD.getWindow().getAttributes();
            wmlp.gravity = Gravity.CENTER;
//        wmlp.y = 210;

            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.calendar_message_alert_dialog, null);

            CustomTextView ctv_selected_Date = view.findViewById(R.id.ctv_selected_Date);
            CustomTextView ctv_calendar_Message = view.findViewById(R.id.ctv_calendar_Message);

            ctv_selected_Date.setText(strSelected_Date);
            ctv_calendar_Message.setText(strCalendarMessage);

            messageAD.setContentView(view);
            messageAD.show();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "calendarMessageAlertDialog", e);
        }

    }


    public void AddDateDynamically(String CalenderType, String Date, String Message) {
        try {
            List<CalendarEvent> list_CalendarEvent = controlObject.getCalendarEventType();

            CalendarEvent CalendarEvent = new CalendarEvent();
            if (CalenderType.equalsIgnoreCase("Single")) {
                CalendarEvent.setEventType("Single Date");
                String UpdatedDate ="";
                //String UpdatedDate = (Date.split("\\-")[2].substring(0, 2)) + "-" + Date.split("\\-")[1] + "-" + Date.split("\\-")[0];
//                UpdatedDate = (Date.split("\\-")[2]) + "-" + Date.split("\\-")[1] + "-" + Date.split("\\-")[0];
                if(Date.split("-")[0].length()<=2){
                    UpdatedDate = (Date.split("-")[0]) + "-" + Date.split("-")[1] + "-" + Date.split("-")[2];
                }else{
                    UpdatedDate = (Date.split("-")[2]) + "-" + Date.split("-")[1] + "-" + Date.split("-")[0];
                }
//            CalendarEvent.setEventDates(Date);
                CalendarEvent.setEventDates(UpdatedDate);
                CalendarEvent.setEventMessage(Message);
            } else {
                CalendarEvent.setEventType("Multiple Dates");
                String tempDate = "";
                String UpdatedDate = "";
                String[] strDateSplit = Date.split("\\$");
                for (int i = 0; i < strDateSplit.length; i++) {
                    if(strDateSplit[i].split("-")[0].length()<=2){
                        UpdatedDate = (strDateSplit[i].split("-")[0].substring(0, 2)) + "-" + strDateSplit[i].split("-")[1] + "-" + strDateSplit[i].split("-")[2];
                    }else {
                        UpdatedDate = (strDateSplit[i].split("-")[2].substring(0, 2)) + "-" + strDateSplit[i].split("-")[1] + "-" + strDateSplit[i].split("-")[0];
                    }
                    if (i == 0) {
                        tempDate = tempDate + UpdatedDate;
                    } else {
                        tempDate = tempDate + "$" + UpdatedDate;
                    }

                }
                if (!tempDate.contentEquals("")) {
                    CalendarEvent.setEventDates(tempDate);
                }
                CalendarEvent.setEventMessage(Message);
            }

            list_CalendarEvent.add(CalendarEvent);


            setCalendarEventValues(calendarView);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "AddDateDynamically", e);
        }
    }

    public String getSelectedDateandMessage() {
        return SelectedDate + "^" + SelectedMessage;
    }

    public String getSelectedMessage() {
        return SelectedMessage;
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

    public void setEnabled(boolean enabled) {
//        setViewDisable(view, !enabled);
        setViewDisableOrEnableDefault(context, view, enabled);
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

    public void setVisibilitySP(boolean visibility) {
        if (visibility) {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }else{
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);

        }
    }

    public void setText() {
        controlObject.setText(getEventDates());
    }

    public String getEventDates() {
        StringBuilder eventDates = new StringBuilder();
        for (int i = 0; i < controlObject.getCalendarEventType().size(); i++) {
            eventDates.append(eventDates).append(controlObject.getCalendarEventType().get(i).getEventDates()).append("^");
//            eventDates.append(eventDates.get(i)).append("^");
        }
        if (eventDates.toString().endsWith("^")) {
            eventDates = new StringBuilder(eventDates.substring(0, eventDates.length() - 1));
        }
        return eventDates.toString();
    }

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
            if(controlObject.getHint() != null && !controlObject.getHint().isEmpty()){
                tv_hint.setVisibility(View.VISIBLE);
            }else{
                tv_hint.setVisibility(View.GONE);
            }

        }
    }

    public void setWeekDays(Boolean valueOf) {
            controlObject.setWeekDays(valueOf);
    }

    /*ControlUi Settings start*/

    public LinearLayout getLl_main_inside() {
        return ll_main_inside;
    }

    public LinearLayout getLl_control_ui() {
        return ll_control_ui;
    }

    public LinearLayout getLl_tap_text() {
        return ll_tap_text;
    }

    public CustomTextView getTv_displayName() {
        return tv_displayName;
    }

    public CalendarView getCalendarView() {
        return calendarView;
    }
//    public ConstraintLayout getConstraintLayout(){
//        return consLayout;
//    }


    /*ControlUi Settings end*/
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

