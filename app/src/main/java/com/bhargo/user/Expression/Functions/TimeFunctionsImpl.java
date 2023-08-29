package com.bhargo.user.Expression.Functions;

import android.content.Context;

import com.bhargo.user.Expression.Interfaces.TimeFunctions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TimeFunctionsImpl implements TimeFunctions {


    private Context context;
    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    public String CurrentTimeIn12HrFormate() {
        String Time="";
        java.util.Calendar myCalendar = java.util.Calendar.getInstance();

        SimpleDateFormat sdfYMD = new SimpleDateFormat("hh:mm:ss aaa", Locale.US);
        Time = "\""+sdfYMD.format(myCalendar.getTime())+"\"";
        return Time;
    }

    @Override
    public String CurrentTimeIn24HrFormate() {
        String Time="";
        java.util.Calendar myCalendar = java.util.Calendar.getInstance();

        SimpleDateFormat sdfYMD = new SimpleDateFormat("HH:mm:ss", Locale.US);
        Time = "\""+sdfYMD.format(myCalendar.getTime())+"\"";
        return Time;
    }

    @Override
    public String getTimeIn12HrFormate(String TimeStamp) {
        String Time="";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm:ss aaa", Locale.US);

        Calendar c = Calendar.getInstance();

        try{
            //Setting the date to the given date
            c.setTime(sdf.parse(TimeStamp));


        Time = sdf12.format(c.getTime());
        }catch(ParseException e){
            e.printStackTrace();
        }
        return Time;
    }

    @Override
    public String getTimeIn24HrFormate(String TimeStamp) {
        String Time="";

        SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm:ss aaa", Locale.US);

        Calendar c = Calendar.getInstance();

        try{
            //Setting the date to the given date
            c.setTime(sdf12.parse(TimeStamp));

            Time = "\""+sdf24.format(c.getTime())+"\"";
        }catch(ParseException e){
            e.printStackTrace();
        }
        return Time;
    }

    @Override
    public String getTimeDifference(String TimeStamp1, String TimeStamp2) {
        String Time="";
        try{
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(sdf1.parse(TimeStamp1));
        c2.setTime(sdf2.parse(TimeStamp2));

            long mills = c1.getTime().getTime()- c2.getTime().getTime();

            int hours =(int) (mills/(1000 * 60 * 60));
            int mins = (int) (mills/(1000*60)) % 60;
            int sec = (int) (mills/(1000*60*60)) % (60*60);

            String diff = hours + ":" + mins;
            Time = "\""+hours+ ":" + mins+":"+sec+"\"";
        }catch(ParseException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Time;
    }

    @Override
    public String getNextTime(String TimeStamp1, String AddTime) {
        String Time="";
        try{
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(sdf1.parse(TimeStamp1));
            c2.setTime(sdf2.parse(AddTime));

            long mills = c1.getTime().getTime()+ c2.getTime().getTime();

            int hours =(int) (mills/(1000 * 60 * 60));
            int mins = (int) (mills/(1000*60)) % 60;
            int sec = (int) (mills/(1000*60*60)) % (60*60);

            String diff = hours + ":" + mins;
            Time = "\""+hours+ ":" + mins+":"+sec+"\"";
        }catch(ParseException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Time;
    }

    @Override
    public String getPreviousTime(String TimeStamp1, String SubtractTime) {
        String Time="";
        try{
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(sdf1.parse(TimeStamp1));
            c2.setTime(sdf2.parse(SubtractTime));

            long mills = c1.getTime().getTime()- c2.getTime().getTime();

            int hours =(int) (mills/(1000 * 60 * 60));
            int mins = (int) (mills/(1000*60)) % 60;
            int sec = (int) (mills/(1000*60*60)) % (60*60);

            String diff = hours + ":" + mins;
            Time = "\""+hours+ ":" + mins+":"+sec+"\"";
        }catch(ParseException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Time;
    }

    @Override
    public boolean IsGreaterThanBaseTime(String BaseTime, String TimeStamp) {
       boolean isgrather =false;
        try{
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(sdf1.parse(BaseTime));
            c2.setTime(sdf2.parse(TimeStamp));

            if(c1.getTime().getTime()>c2.getTime().getTime()){
                isgrather=true;
            }


        }catch(ParseException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isgrather;
    }

    @Override
    public String getTimeDuration(String TimeStamp1, String TimeStamp2, String DurationFormatHrorMinorSec) {
        String Time="";
        try{
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();

            c1.setTime(sdf1.parse(TimeStamp1));
            c2.setTime(sdf2.parse(TimeStamp2));

            Date date1 = sdf1.parse(TimeStamp1);
            Date  date2 = sdf2.parse(TimeStamp2);


            long diff_dates = date2.getTime() - date1.getTime();
            long diff_cals = c2.getTimeInMillis()- c1.getTimeInMillis();

            double seconds_date = diff_dates / 1000;
            double minutes_date = seconds_date / 60;
            double hours_date = minutes_date / 60;
            double days = hours_date / 24;

            double diffSeconds_cal = diff_cals / 1000;
            double diffMinutes_cal = diff_cals / (60 * 1000);
            double diffHours_cal = diff_cals / (60 * 60 * 1000);
            double diffDays_cal = diff_cals / (24 * 60 * 60 * 1000);


            if(DurationFormatHrorMinorSec.contains("s") ||DurationFormatHrorMinorSec.contains("S")){
                Time=seconds_date+"";
            }  else if(DurationFormatHrorMinorSec.contains("m")||DurationFormatHrorMinorSec.contains("M")){
                Time=minutes_date+"";
            }  else {
                Time=hours_date+"";
            }

        }catch(ParseException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Time;
    }
}
