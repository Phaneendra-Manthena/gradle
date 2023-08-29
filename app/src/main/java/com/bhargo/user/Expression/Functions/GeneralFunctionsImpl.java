package com.bhargo.user.Expression.Functions;

import com.bhargo.user.Expression.Interfaces.GeneralFunctions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GeneralFunctionsImpl implements GeneralFunctions {

    static final double pi = 3.14159265358979323846;

    public static double PI = 3.14159265;
    public static double TWOPI = 2 * PI;

    public static double deg2rad(double deg) {
        return (deg * pi / 180);
    }

    public static double rad2deg(double rad) {
        return (rad * 180 / pi);
    }

    public static boolean coordinate_is_inside_polygon(double latitude, double longitude, ArrayList<Double> lat_array, ArrayList<Double> long_array) {
        int i;
        double angle = 0;
        double point1_lat;
        double point1_long;
        double point2_lat;
        double point2_long;
        int n = lat_array.size();

        for (i = 0; i < n; i++) {
            point1_lat = lat_array.get(i) - latitude;
            point1_long = long_array.get(i) - longitude;
            point2_lat = lat_array.get((i + 1) % n) - latitude;
            //you should have paid more attention in high school geometry.
            point2_long = long_array.get((i + 1) % n) - longitude;
            angle += Angle2D(point1_lat, point1_long, point2_lat, point2_long);
        }

        if (Math.abs(angle) < PI)
            return false;
        else
            return true;
    }

    public static double Angle2D(double y1, double x1, double y2, double x2) {
        double dtheta, theta1, theta2;

        theta1 = Math.atan2(y1, x1);
        theta2 = Math.atan2(y2, x2);
        dtheta = theta2 - theta1;
        while (dtheta > PI)
            dtheta -= TWOPI;
        while (dtheta < -PI)
            dtheta += TWOPI;

        return (dtheta);
    }

    public static boolean is_valid_gps_coordinate(double latitude, double longitude) {
        //This is a bonus function, it's unused, to reject invalid lat/longs.
        if (latitude > -90 && latitude < 90 && longitude > -180 && longitude < 180) {
            return true;
        }

        return false;
    }

    public String callage(Date birthDate) {
        String presentAge = "";

        int years = 0;
        int months = 0;
        int days = 0;

        //create calendar object for birth day
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthDate.getTime());

        //create calendar object for current day
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);

        //Get difference between years
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;

        //Get difference between months
        months = currMonth - birthMonth;

        //if month difference is in negative then reduce years by one
        //and calculate the number of months.
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }

        //Calculate the days
        if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                years++;
                months = 0;
            }
        }

        presentAge =years+"."+months ;
//        presentAge = days + "-" + months + "-" + years;

        return presentAge;
    }

    public String getDays(Date dateBefore, Date dateAfter) {
        String days = "";

        long difference = dateAfter.getTime() - dateBefore.getTime();
        float daysBetween = (difference / (1000 * 60 * 60 * 24));

        days = String.valueOf(daysBetween);

        return days;
    }

    public String getMonths(Date d1, Date d2) {
        String months = "";

        Calendar m_calendar = Calendar.getInstance();
        m_calendar.setTime(d1);
        int nMonth1 = 12 * m_calendar.get(Calendar.YEAR) + m_calendar.get(Calendar.MONTH);
        m_calendar.setTime(d2);
        int nMonth2 = 12 * m_calendar.get(Calendar.YEAR) + m_calendar.get(Calendar.MONTH);

        months = String.valueOf(Math.abs(nMonth2 - nMonth1));


        return months;
    }

    public String getYear(Date first, Date last) {
        String years = "";

        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }

        years = String.valueOf(diff);

        return years;
    }

    public Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public String getMonthlyEmi(double principal, double rate, double years) {
        String emiValue = "";

        rate = rate / (12 * 100);

        years = years * 12;

        double emi = (principal * rate * Math.pow(1 + rate, years)) / (Math.pow(1 + rate, years) - 1);

        emiValue = String.valueOf(emi);

        return emiValue;
    }

    public boolean GeoFencing(String currentPosition, String referencePosition, double radius) {

        double lat1 = Double.parseDouble(currentPosition.split("\\$")[0]);
        double lon1 = Double.parseDouble(currentPosition.split("\\$")[1]);

        double lat2 = Double.parseDouble(referencePosition.split("\\$")[0]);
        double lon2 = Double.parseDouble(referencePosition.split("\\$")[1]);

        double theta, dist;

        theta = lon1 - lon2;

        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);

        dist = rad2deg(dist);

        dist = dist * 60 * 1.1515;

        dist = dist * 1.609344; // in k.m

        dist = dist * 1000; // in meters

        //return (dist);

        if (dist <= radius) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isGeoFencingList(String currentPosition, List<String> referenceList) {
        boolean insideOutside;

        double lat = Double.parseDouble(currentPosition.split("\\$")[0]);
        double lon = Double.parseDouble(currentPosition.split("\\$")[1]);

        ArrayList<Double> lat_array = new ArrayList<Double>();
        ArrayList<Double> long_array = new ArrayList<Double>();

        for (String s : referenceList) {
            lat_array.add(Double.parseDouble(s.split("\\$")[0]));
            long_array.add(Double.parseDouble(s.split("\\$")[1]));
        }


        insideOutside = coordinate_is_inside_polygon(lat, lon, lat_array, long_array);


        return insideOutside;
    }

    //==================AadharValidation====

    // The multiplication table
    static int[][] d  = new int[][]
            {
                    {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {1, 2, 3, 4, 0, 6, 7, 8, 9, 5},
                    {2, 3, 4, 0, 1, 7, 8, 9, 5, 6},
                    {3, 4, 0, 1, 2, 8, 9, 5, 6, 7},
                    {4, 0, 1, 2, 3, 9, 5, 6, 7, 8},
                    {5, 9, 8, 7, 6, 0, 4, 3, 2, 1},
                    {6, 5, 9, 8, 7, 1, 0, 4, 3, 2},
                    {7, 6, 5, 9, 8, 2, 1, 0, 4, 3},
                    {8, 7, 6, 5, 9, 3, 2, 1, 0, 4},
                    {9, 8, 7, 6, 5, 4, 3, 2, 1, 0}
            };

    // The permutation table
    static int[][] p = new int[][]
            {
                    {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {1, 5, 7, 6, 2, 8, 3, 0, 9, 4},
                    {5, 8, 0, 3, 7, 9, 6, 1, 4, 2},
                    {8, 9, 1, 6, 0, 4, 3, 5, 2, 7},
                    {9, 4, 5, 3, 1, 2, 6, 8, 7, 0},
                    {4, 2, 8, 6, 5, 7, 3, 9, 0, 1},
                    {2, 7, 9, 3, 8, 0, 6, 4, 1, 5},
                    {7, 0, 4, 6, 9, 1, 3, 2, 5, 8}
            };

    // The inverse table
    static int[] inv = {0, 4, 3, 2, 1, 5, 6, 7, 8, 9};


    /*
     * For a given number generates a Verhoeff digit
     *
     */
    public static String generateVerhoeff(String num){

        int c = 0;
        int[] myArray = stringToReversedIntArray(num);

        for(int i = 0; i < myArray.length; i++)
        {
            c = d[c][p[((i + 1) % 8)] [myArray[i]]];
        }

        return Integer.toString(inv[c]);
    }






    /*
     * Converts a string to a reversed integer array.
     */
    private static int[] stringToReversedIntArray(String num){

        int[] myArray = new int[num.length()];

        for(int i = 0; i < num.length(); i++)
        {
            myArray[i] = Integer.parseInt(num.substring(i, i + 1));
        }

        myArray = reverse(myArray);

        return myArray;

    }

    /*
     * Reverses an int array
     */
    private static int[] reverse(int[] myArray)
    {
        int[] reversed = new int[myArray.length];

        for(int i = 0; i < myArray.length ; i++)
        {
            reversed[i] = myArray[myArray.length - (i + 1)];
        }

        return reversed;
    }

    /*
     * Validates that an entered number is Verhoeff compliant.
     * NB: Make sure the check digit is the last one.
     */

    public  boolean aadharCheck(String aadharno){

        int c = 0;
        int[] myArray = stringToReversedIntArray(aadharno);

        for (int i = 0; i < myArray.length; i++)
        {
            c = d[c][p[(i % 8)][myArray[i]]];
        }

        return (c == 0);
    }

    public String getCurrentDate(){
        String date;
        java.util.Calendar myCalendar = java.util.Calendar.getInstance();

        SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd HH:hh:ss", Locale.US);
        date = "\""+sdfYMD.format(myCalendar.getTime())+"\"";
        System.out.println("Date at currentdate fun==="+date);
        return date;
    }

    public int getSize(List<String> MyArray){
        int size=0;
        if(MyArray!=null){
            size=MyArray.size();
        }
        return size;
    }
}
