package com.bhargo.user.Expression;


import android.content.Context;
import android.util.Log;

import com.bhargo.user.Expression.Functions.ArithmeticFunctionsImpl;
import com.bhargo.user.Expression.Functions.CustomFunctionsImpl;
import com.bhargo.user.Expression.Functions.DateFunctionsImpl;
import com.bhargo.user.Expression.Functions.GeneralFunctionsImpl;
import com.bhargo.user.Expression.Functions.GetPropertiesFunctionImpl;
import com.bhargo.user.Expression.Functions.GroupFunctionsImpl;
import com.bhargo.user.Expression.Functions.SpatialFunctionsImpl;
import com.bhargo.user.Expression.Functions.StringFunctionsImpl;
import com.bhargo.user.Expression.Functions.TimeFunctionsImpl;
import com.bhargo.user.Expression.Functions.TrigonometricFunctionsImpl;
import com.bhargo.user.pojos.MultiColWithPoint;
import com.bhargo.user.pojos.PointWithDistance;
import com.bhargo.user.realm.RealmDBHelper;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class ExpressionExecution {

    public String CheckMethodName(Context context, String Exprestionstr) {
        String value = "";
        try {
            String Exprestion = Exprestionstr.trim().toLowerCase();
            ArithmeticFunctionsImpl Arithmetic_fun = new ArithmeticFunctionsImpl();
            GeneralFunctionsImpl General_fun = new GeneralFunctionsImpl();
            GroupFunctionsImpl Group_fun = new GroupFunctionsImpl();
            StringFunctionsImpl String_fun = new StringFunctionsImpl();
            TrigonometricFunctionsImpl Trigonometric_fun = new TrigonometricFunctionsImpl();
            DateFunctionsImpl Date_fun = new DateFunctionsImpl();
            CustomFunctionsImpl Custom_fun = new CustomFunctionsImpl();
            SpatialFunctionsImpl Spatial_fun = new SpatialFunctionsImpl();
            TimeFunctionsImpl Time_fun = new TimeFunctionsImpl();
            GetPropertiesFunctionImpl gpf = new GetPropertiesFunctionImpl();


            Custom_fun.setContext(context);
            if (Exprestion.startsWith(ExpressionConstants.Summation.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                for (int i = 0; i < list_values.length; i++) {
                    if (list_values[i].toLowerCase().startsWith("(im:subcontrols") && !list_values[i].toLowerCase().endsWith("_allrows)")) {
                        Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                    } else if (list_values[i].toLowerCase().startsWith("(im:datatablecontrols") && !list_values[i].toLowerCase().endsWith("_allrows)")) {
                        Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                    } else if (list_values[i].toLowerCase().startsWith("(im:")) {
                        Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[i]));
                    } else {
                        Values.add(list_values[i]);
                    }
                }

                value = "" + Arithmetic_fun.getSummation(Values);

            } else if (Exprestion.startsWith(ExpressionConstants.Multiplication.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                for (int i = 0; i < list_values.length; i++) {
//                if(list_values[i].toLowerCase().startsWith("(im:")) {
                    if (list_values[i].toLowerCase().startsWith("(im:subcontrol") && !list_values[i].toLowerCase().endsWith("_allrows)")) {
//                    Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
//                    Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(list_values[i]));
                        Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                    } else if (list_values[i].toLowerCase().startsWith("(im:")) {
                        Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[i]));
                    } else {
                        Values.add(list_values[i]);
                    }
                }
                value = "" + Arithmetic_fun.getMultiplication(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.Division.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }

                try {
                    value = "" + Arithmetic_fun.getDivision(Double.parseDouble(Value0.trim()), Double.parseDouble(Value1.trim()));
                } catch (Exception e) {
                    value = "0";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Subtraction.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }

                try {
                    value = "" + Arithmetic_fun.getSubtraction(Double.parseDouble(Value0.trim()), Double.parseDouble(Value1.trim()));
                } catch (Exception e) {
                    value = "0";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Percentage.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = "" + ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = "" + list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }

                try {
                    value = "" + Arithmetic_fun.getPercentage(Double.parseDouble(Value0.trim()), Double.parseDouble(Value1.trim()));
                } catch (Exception e) {
                    value = "" + 0.0;
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Power.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }

                try {
                    value = "" + Arithmetic_fun.getPower(Double.parseDouble(Value0.trim()), Double.parseDouble(Value1.trim()));
                } catch (Exception e) {
                    value = "" + 0.0;
                }
            } else if (Exprestion.startsWith(ExpressionConstants.NthRoot.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }

                try {
                    value = "" + Arithmetic_fun.getNthRoot(Double.parseDouble(Value0.trim()), Double.parseDouble(Value1.trim()));
                } catch (Exception e) {
                    value = "" + 0.0;
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Absolute.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String Value0 = "";

                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Value0 = Valuestr;
                }

                try {
                    value = "" + Arithmetic_fun.getAbsolute(Double.parseDouble(Value0.trim()));
                } catch (Exception e) {
                    value = "" + 0.0;
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Floor.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String Value0 = "";

                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Value0 = Valuestr;
                }

                try {
                    value = "" + Arithmetic_fun.getFloor(Double.parseDouble(Value0.trim()));
                } catch (Exception e) {
                    value = "" + 0.0;
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Round.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String Value0 = "";

                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Value0 = Valuestr;
                }

                try {
                    value = "" + Arithmetic_fun.getRound(Double.parseDouble(Value0.trim()));
                } catch (Exception e) {
                    value = "" + 0.0;
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Ceil.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String Value0 = "";

                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Value0 = Valuestr;
                }

                try {
                    value = "" + Arithmetic_fun.getCeil(Double.parseDouble(Value0.trim()));
                } catch (Exception e) {
                    value = "" + 0.0;
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Random.toLowerCase())) {
                try {
                    value = "" + Arithmetic_fun.getRandom();
                } catch (Exception e) {
                    value = "" + 0.0;
                }
            } else if (Exprestion.startsWith(ExpressionConstants.RandomRange.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = "" + ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = "" + list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }

                try {
                    value = "" + Arithmetic_fun.getRandomRange(Double.parseDouble(Value0.trim()), Double.parseDouble(Value1.trim()));
                } catch (Exception e) {
                    value = "" + 0.0;
                }
            } else if (Exprestion.toLowerCase().startsWith(ExpressionConstants.Age.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String Value0 = "";

                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Value0 = Valuestr;
                }

                try {
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = dateFormat.parse(Value0);
                    value = "" + General_fun.callage(date);

//                value =""+General_fun.callage(new Date(Integer.parseInt(Value0.split("\\-")[2]),(Integer.parseInt(Value0.split("\\-")[1]))-1,Integer.parseInt(Value0.split("\\-")[0])));
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Days.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }


                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date1 = dateFormat.parse(Value0);
                    Date date2 = dateFormat.parse(Value1);
                    value = "" + General_fun.getDays(date1, date2);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Months.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }


                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date1 = dateFormat.parse(Value0);
                    Date date2 = dateFormat.parse(Value1);

                    value = "" + General_fun.getMonths(date1, date2);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Year.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }


                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date1 = dateFormat.parse(Value0);
                    Date date2 = dateFormat.parse(Value1);
                    value = "" + General_fun.getYear(date1, date2);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.MonthlyEmi.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "", Value2 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }
                if (list_values[2].toLowerCase().startsWith("(im:")) {
                    Value2 = ImproveHelper.getValueFromGlobalObject(context, list_values[2]);
                } else {
                    Value2 = list_values[2];
                }

                try {
                    value = "" + General_fun.getMonthlyEmi(Double.parseDouble(Value0.trim()), Double.parseDouble(Value1.trim()), Double.parseDouble(Value2.trim()));
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.GeoFencing.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "", Value2 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    if (list_values[0].contains("\"")) {
                        Value0 = list_values[0].substring(list_values[0].indexOf("\"") + 1, list_values[0].lastIndexOf("\""));
                    } else {
                        Value0 = list_values[0];
                    }


                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    if (list_values[1].contains("\"")) {
                        Value1 = list_values[1].substring(list_values[1].indexOf("\"") + 1, list_values[1].lastIndexOf("\""));
                    } else {
                        Value1 = list_values[1];
                    }
                }
                if (list_values[2].toLowerCase().startsWith("(im:")) {
                    Value2 = ImproveHelper.getValueFromGlobalObject(context, list_values[2]);
                } else {
                    Value2 = list_values[2];
                }

                try {
                    value = "" + General_fun.GeoFencing(Value0.trim(), Value1.trim(), Double.parseDouble(Value2.trim()));
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.GeoFencingList.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "";


                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                List<String> Values = new ArrayList<String>();
                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    String[] arr = ImproveHelper.getValueFromGlobalObject(context, list_values[1]).split("|");
                    Values = Arrays.asList(arr);
                } else {
                    String[] arr = list_values[1].split("|");
                    Values = Arrays.asList(arr);
                }

                try {
                    value = "" + General_fun.isGeoFencingList(Value0.trim(), Values);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.AadharCheck.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "";


                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }


                try {
                    value = "" + General_fun.aadharCheck(Value0.trim());
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Maximum.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                for (int i = 0; i < list_values.length; i++) {
                    if (list_values[i].toLowerCase().startsWith("(im:subcontrols") && !list_values[i].toLowerCase().endsWith("_allrows)")) {
//                    Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                        Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                    } else if (list_values[i].toLowerCase().startsWith("(im:")) {
                        Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[i]));
                    } else {
                        Values.add(list_values[i]);
                    }
                }

                value = "" + Group_fun.getMaximum(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.Minimum.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                for (int i = 0; i < list_values.length; i++) {
                    if (list_values[i].toLowerCase().startsWith("(im:subcontrols") && !list_values[i].toLowerCase().endsWith("_allrows)")) {
//                    Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                        Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                    } else if (list_values[i].toLowerCase().startsWith("(im:")) {
                        Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[i]));
                    } else {
                        Values.add(list_values[i]);
                    }
                }

                value = "" + Group_fun.getMinimum(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.Count.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                if (Valuestr.trim().length() != 0) {
                    String[] list_values = Valuestr.split("\\,");
                    List<String> Values = new ArrayList<String>();
                    for (int i = 0; i < list_values.length; i++) {
                        if (list_values[i].toLowerCase().startsWith("(im:subcontrols") && !list_values[i].toLowerCase().endsWith("_allrows)")) {
//                    Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                            Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                        } else if (list_values[i].toLowerCase().startsWith("(im:")) {
                            Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[i]));
                        } else {
                            Values.add(list_values[i]);
                        }
                    }

                    value = "" + Group_fun.getCount(Values);
                } else {
                    value = "0";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Variance.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                for (int i = 0; i < list_values.length; i++) {
                    if (list_values[i].toLowerCase().startsWith("(im:subcontrols") && !list_values[i].toLowerCase().endsWith("_allrows)")) {
//                    Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                        Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                    } else if (list_values[i].toLowerCase().startsWith("(im:")) {
                        Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[i]));
                    } else {
                        Values.add(list_values[i]);
                    }
                }

                value = "" + Group_fun.getVariance(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.Mean.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                for (int i = 0; i < list_values.length; i++) {
                    if (list_values[i].toLowerCase().startsWith("(im:subcontrols") && !list_values[i].toLowerCase().endsWith("_allrows)")) {
//                    Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                        Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                    } else if (list_values[i].toLowerCase().startsWith("(im:")) {
                        Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[i]));
                    } else {
                        Values.add(list_values[i]);
                    }
                }

                value = "" + Group_fun.getMean(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.Median.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                for (int i = 0; i < list_values.length; i++) {
                    if (list_values[i].toLowerCase().startsWith("(im:subcontrols") && !list_values[i].toLowerCase().endsWith("_allrows)")) {
//                    Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                        Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                    } else if (list_values[i].toLowerCase().startsWith("(im:")) {
                        Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[i]));
                    } else {
                        Values.add(list_values[i]);
                    }
                }

                value = "" + Group_fun.getMedian(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.Average.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                for (int i = 0; i < list_values.length; i++) {
                    if (list_values[i].toLowerCase().startsWith("(im:subcontrols") && !list_values[i].toLowerCase().endsWith("_allrows)")) {
//                    Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                        Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                    } else if (list_values[i].toLowerCase().startsWith("(im:")) {
                        Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[i]));
                    } else {
                        Values.add(list_values[i]);
                    }
                }

                value = "" + Group_fun.getAverage(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.LengthOf.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                for (int i = 0; i < list_values.length; i++) {
                    if (list_values[i].toLowerCase().startsWith("(im:subcontrols") && !list_values[i].toLowerCase().endsWith("_allrows)")) {
//                    Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                        Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                    } else if (list_values[i].toLowerCase().startsWith("(im:")) {
                        Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[i]));
                    } else {
                        Values.add(list_values[i]);
                    }
                }

                value = "" + Group_fun.getLengthOf(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.ToArray.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                String Value1 = "";
                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[0]));
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[0]));
                } else {
                    Values.add(list_values[0]);
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }


                value = "" + Group_fun.ToArray(Values, Value1);
            } else if (Exprestion.startsWith(ExpressionConstants.addItem.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                String Value1 = "";
                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[0]));
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[0]));
                } else {
                    Values.add(list_values[0]);
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }


                value = "" + Group_fun.addItem(Values, Value1);
            } else if (Exprestion.startsWith(ExpressionConstants.removeItem.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                String Value1 = "";
                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[0]));
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[0]));
                } else {
                    Values.add(list_values[0]);
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }


                value = "" + Group_fun.removeItem(Values, Value1);
            } else if (Exprestion.startsWith(ExpressionConstants.addItemAt.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                String Value1 = "";
                int Value2;
                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[0]));
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[0]));
                } else {
                    Values.add(list_values[0]);
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }
                if (list_values[2].toLowerCase().startsWith("(im:")) {
                    Value2 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[2]));
                } else {
                    Value2 = Integer.parseInt(list_values[2]);
                }

                value = "" + Group_fun.addItemAt(Values, Value1, Value2);
            } else if (Exprestion.startsWith(ExpressionConstants.removeItemAt.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                int Value2;
                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[0]));
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[0]));
                } else {
                    Values.add(list_values[0]);
                }


                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value2 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    Value2 = Integer.parseInt(list_values[1]);
                }

                value = "" + Group_fun.removeItemAt(Values, Value2);
            } else if (Exprestion.startsWith(ExpressionConstants.contains.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                String Value1 = "";
                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[0]));
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[0]));
                } else {
                    Values.add(list_values[0]);
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }


                value = "" + Group_fun.contains(Values, Value1);
            } else if (Exprestion.startsWith(ExpressionConstants.concatenate.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                List<String> Values2 = new ArrayList<String>();
                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[0]));
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[0]));
                } else {
                    Values.add(list_values[0]);
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    Values2.add(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Values2.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[1]));
                } else {
                    Values2.add(list_values[1]);
                }

                value = "" + Group_fun.concatenate(Values, Values2);
            } else if (Exprestion.startsWith(ExpressionConstants.comparison.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                List<String> Values2 = new ArrayList<String>();
                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[0]));
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[0]));
                } else {
                    Values.add(list_values[0]);
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    Values2.add(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Values2.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[1]));
                } else {
                    Values2.add(list_values[1]);
                }

                value = "" + Group_fun.comparison(Values, Values2);
            } else if (Exprestion.startsWith(ExpressionConstants.ConCat.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }


                try {
                    value = "" + String_fun.stringConCat(Value0.trim(), Value1.trim());
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Copy.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }


                try {
                    value = "" + String_fun.stringCopy(Value0.trim(), Value1.trim());
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Length.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String Value0 = "";

                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Value0 = Valuestr;
                }
                try {
                    value = "" + String_fun.stringLength(Value0.trim());
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Postion.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }


                try {
                    value = "" + String_fun.stringPostion(Value0.trim(), Value1.trim());
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Replace.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "", Value2 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    Value1 = list_values[1];
                }
                if (list_values[2].toLowerCase().startsWith("(im:")) {
                    Value2 = ImproveHelper.getValueFromGlobalObject(context, list_values[2]);
                } else {
                    Value2 = list_values[2];
                }

                try {
                    value = "" + String_fun.stringReplace(Value0.trim(), Value1.trim(), Value2.trim());
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.ToUpper.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String Value0 = "";

                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Value0 = Valuestr;
                }
                try {
                    value = "" + String_fun.stringToUpper(Value0.trim());
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.ToLower.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String Value0 = "";

                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Value0 = Valuestr;
                }
                try {
                    value = "" + String_fun.stringToLower(Value0.trim());
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Reverse.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String Value0 = "";

                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Value0 = Valuestr;
                }
                try {
                    value = "" + String_fun.stringReverse(Value0.trim());
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Repeat.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String Value0 = "";

                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Value0 = Valuestr;
                }
                try {
                    value = "" + String_fun.stringRepeat(Value0.trim());
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.subStringInbetween.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "";
                int value1 = 0, value2 = 0;

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    value1 = Integer.parseInt(list_values[1]);
                }

                if (list_values[2].toLowerCase().startsWith("(im:")) {
                    value2 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[2]));
                } else {
                    value2 = Integer.parseInt(list_values[2]);
                }

                try {
                    value = "" + String_fun.subStringInbetween(Value0.trim(), value1, value2);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.subString.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "";
                int value1 = 0;

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    value1 = Integer.parseInt(list_values[1]);
                }


                try {
                    value = "" + String_fun.subString(Value0.trim(), value1);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Trim.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String Value0 = "";

                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Value0 = Valuestr;
                }
                try {
                    value = "" + String_fun.stringTrim(Value0.trim());
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.StartsWith.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    value1 = list_values[1];
                }


                try {
                    value = "" + String_fun.stringStartsWith(Value0.trim(), value1);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.EndsWith.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    value1 = list_values[1];
                }
                try {
                    value = "" + String_fun.stringEndsWith(Value0.trim(), value1);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Compare.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    value1 = list_values[1];
                }
                try {
                    value = "" + String_fun.stringCompare(Value0.trim(), value1);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.CompareIgnoringCase.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    value1 = list_values[1];
                }
                try {
                    value = "" + String_fun.stringCompareIgnoringCase(Value0.trim(), value1);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.ListCompareCase.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String value1 = "";
                List<String> value0 = new ArrayList<String>();

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    value0 = ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[0]);
                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    value1 = list_values[1];
                }
                try {
                    value = "" + String_fun.stringCompareWithListOfValues(value0, value1);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Sin.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                Double Value0 = 0.0;
                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = Double.parseDouble(ImproveHelper.getValueFromGlobalObject(context, Valuestr));
                } else {
                    Value0 = Double.parseDouble(Valuestr);
                }

                try {
                    value = "" + Trigonometric_fun.getSin(Value0);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Cos.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                Double Value0 = 0.0;
                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = Double.parseDouble(ImproveHelper.getValueFromGlobalObject(context, Valuestr));
                } else {
                    Value0 = Double.parseDouble(Valuestr);
                }

                try {
                    value = "" + Trigonometric_fun.getCos(Value0);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Tan.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                Double Value0 = 0.0;
                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = Double.parseDouble(ImproveHelper.getValueFromGlobalObject(context, Valuestr));
                } else {
                    Value0 = Double.parseDouble(Valuestr);
                }

                try {
                    value = "" + Trigonometric_fun.getTan(Value0);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.ArcSin.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                Double Value0 = 0.0;
                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = Double.parseDouble(ImproveHelper.getValueFromGlobalObject(context, Valuestr));
                } else {
                    Value0 = Double.parseDouble(Valuestr);
                }

                try {
                    value = "" + Trigonometric_fun.getArcSin(Value0);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.ArcCos.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                Double Value0 = 0.0;
                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = Double.parseDouble(ImproveHelper.getValueFromGlobalObject(context, Valuestr));
                } else {
                    Value0 = Double.parseDouble(Valuestr);
                }

                try {
                    value = "" + Trigonometric_fun.getArcCos(Value0);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.ArcTan.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                Double Value0 = 0.0;
                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = Double.parseDouble(ImproveHelper.getValueFromGlobalObject(context, Valuestr));
                } else {
                    Value0 = Double.parseDouble(Valuestr);
                }

                try {
                    value = "" + Trigonometric_fun.getArcTan(Value0);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.SinHyp.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                Double Value0 = 0.0;
                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = Double.parseDouble(ImproveHelper.getValueFromGlobalObject(context, Valuestr));
                } else {
                    Value0 = Double.parseDouble(Valuestr);
                }

                try {
                    value = "" + Trigonometric_fun.getSinHyp(Value0);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.CosHyp.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                Double Value0 = 0.0;
                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = Double.parseDouble(ImproveHelper.getValueFromGlobalObject(context, Valuestr));
                } else {
                    Value0 = Double.parseDouble(Valuestr);
                }

                try {
                    value = "" + Trigonometric_fun.getCosHyp(Value0);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.TanHyp.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                Double Value0 = 0.0;
                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = Double.parseDouble(ImproveHelper.getValueFromGlobalObject(context, Valuestr));
                } else {
                    Value0 = Double.parseDouble(Valuestr);
                }

                try {
                    value = "" + Trigonometric_fun.getTanHyp(Value0);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Log1.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                Double Value0 = 0.0;
                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = Double.parseDouble(ImproveHelper.getValueFromGlobalObject(context, Valuestr));
                } else {
                    Value0 = Double.parseDouble(Valuestr);
                }

                try {
                    value = "" + Trigonometric_fun.getLog(Value0);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.Log10.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                Double Value0 = 0.0;
                if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Value0 = Double.parseDouble(ImproveHelper.getValueFromGlobalObject(context, Valuestr));
                } else {
                    Value0 = Double.parseDouble(Valuestr);
                }

                try {
                    value = "" + Trigonometric_fun.getLog10(Value0);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.isDateGreaterthan.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "", value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    value1 = list_values[1];
                }

                value = "" + Date_fun.isDateGreaterthan(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.isDateLessthan.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "", value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    value1 = list_values[1];
                }

                value = "" + Date_fun.isDateLessthan(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.addBusinessDay.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                int value1 = 0;

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    value1 = Integer.parseInt(list_values[1]);
                }

                value = "" + Date_fun.addBusinessDay(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.addDay.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                int value1 = 0;

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    value1 = Integer.parseInt(list_values[1]);
                }

                value = "" + Date_fun.addDay(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.addHours.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                int value1 = 0;

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    value1 = Integer.parseInt(list_values[1]);
                }

                value = "" + Date_fun.addHours(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.addMinutes.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                int value1 = 0;

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    value1 = Integer.parseInt(list_values[1]);
                }

                value = "" + Date_fun.addMinutes(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.addMonths.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                int value1 = 0;

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    value1 = Integer.parseInt(list_values[1]);
                }

                value = "" + Date_fun.addMonths(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.addSeconds.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                int value1 = 0;

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    value1 = Integer.parseInt(list_values[1]);
                }

                value = "" + Date_fun.addSeconds(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.addWeeks.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                int value1 = 0;

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    value1 = Integer.parseInt(list_values[1]);
                }

                value = "" + Date_fun.addWeeks(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.addYears.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                int value1 = 0;

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    value1 = Integer.parseInt(list_values[1]);
                }

                value = "" + Date_fun.addYears(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.getDay.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));

                String Values = "";

                if (Valuestr.toLowerCase().startsWith("(im:subcontrols") && !Valuestr.toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Values = Valuestr;
                }

                value = "" + Date_fun.getDay(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.getHour.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));

                String Values = "";

                if (Valuestr.toLowerCase().startsWith("(im:subcontrols") && !Valuestr.toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Values = Valuestr;
                }

                value = "" + Date_fun.getHour(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.getMinute.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));

                String Values = "";

                if (Valuestr.toLowerCase().startsWith("(im:subcontrols") && !Valuestr.toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Values = Valuestr;
                }

                value = "" + Date_fun.getMinute(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.getMonth.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));

                String Values = "";

                if (Valuestr.toLowerCase().startsWith("(im:subcontrols") && !Valuestr.toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Values = Valuestr;
                }

                value = "" + Date_fun.getMonth(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.getSeconds.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));

                String Values = "";

                if (Valuestr.toLowerCase().startsWith("(im:subcontrols") && !Valuestr.toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Values = Valuestr;
                }

                value = "" + Date_fun.getSeconds(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.getYear.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));

                String Values = "";

                if (Valuestr.toLowerCase().startsWith("(im:subcontrols") && !Valuestr.toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Values = Valuestr;
                }

                value = "" + Date_fun.getYear(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.getDayOfYear.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));

                String Values = "";

                if (Valuestr.toLowerCase().startsWith("(im:subcontrols") && !Valuestr.toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Values = Valuestr;
                }

                value = "" + Date_fun.getDayOfYear(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.getWeekOfYear.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));

                String Values = "";

                if (Valuestr.toLowerCase().startsWith("(im:subcontrols") && !Valuestr.toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Values = Valuestr;
                }

                value = "" + Date_fun.getWeekOfYear(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.subDay.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                int value1 = 0;

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    value1 = Integer.parseInt(list_values[1]);
                }

                value = "" + Date_fun.subDay(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.subHours.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                int value1 = 0;

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    value1 = Integer.parseInt(list_values[1]);
                }

                value = "" + Date_fun.subHours(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.subMinutes.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                int value1 = 0;

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    value1 = Integer.parseInt(list_values[1]);
                }

                value = "" + Date_fun.subMinutes(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.subMonths.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                int value1 = 0;

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    value1 = Integer.parseInt(list_values[1]);
                }

                value = "" + Date_fun.subMonths(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.subSeconds.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                int value1 = 0;

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    value1 = Integer.parseInt(list_values[1]);
                }

                value = "" + Date_fun.subSeconds(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.subWeeks.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                int value1 = 0;

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    value1 = Integer.parseInt(list_values[1]);
                }

                value = "" + Date_fun.subWeeks(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.subYears.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                int value1 = 0;

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = Integer.parseInt(ImproveHelper.getValueFromGlobalObject(context, list_values[1]));
                } else {
                    value1 = Integer.parseInt(list_values[1]);
                }

                value = "" + Date_fun.subYears(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.firstDayWeek.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));

                String Values = "";

                if (Valuestr.toLowerCase().startsWith("(im:subcontrols") && !Valuestr.toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Values = Valuestr;
                }

                value = "" + Date_fun.firstDayWeek(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.lastDayWeek.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));

                String Values = "";

                if (Valuestr.toLowerCase().startsWith("(im:subcontrols") && !Valuestr.toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Values = Valuestr;
                }

                value = "" + Date_fun.lastDayWeek(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.getWeekDay.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));

                String Values = "";

                if (Valuestr.toLowerCase().startsWith("(im:subcontrols") && !Valuestr.toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else if (Valuestr.toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, Valuestr);
                } else {
                    Values = Valuestr;
                }

                value = "" + Date_fun.getWeekDay(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.monthsBetween.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                String value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    value1 = list_values[1];
                }

                value = "" + Date_fun.monthsBetween(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.hoursBetween.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                String value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    value1 = list_values[1];
                }

                value = "" + Date_fun.hoursBetween(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.yearsBetween.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                String value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    value1 = list_values[1];
                }

                value = "" + Date_fun.yearsBetween(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.isDateValid.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "";
                String value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    value1 = list_values[1];
                }

                value = "" + Date_fun.isDateValid(Values, value1);
            } else if (Exprestion.toLowerCase().startsWith(ExpressionConstants.getCurrentDate.toLowerCase())) {


                try {
                    value = "" + General_fun.getCurrentDate();

                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.toLowerCase().startsWith(ExpressionConstants.getSize.toLowerCase())) {

                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<String>();
                for (int i = 0; i < list_values.length; i++) {
                    if (list_values[i].toLowerCase().startsWith("(im:subcontrols") && !list_values[i].toLowerCase().endsWith("_allrows)")) {
                        Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                    } else if (list_values[i].toLowerCase().startsWith("(im:")) {
                        Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[i]));
                    } else {
                        Values.add(list_values[i]);
                    }
                }

                value = "" + General_fun.getSize(Values);
            } else if (Exprestion.startsWith(ExpressionConstants.getPostions.toLowerCase())) {
                //nk test
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");

                List<Integer> list_postions = Custom_fun.getPostions(list_values[0], list_values[1].toUpperCase(), list_values[2]);
                for (int i = 0; i < list_postions.size(); i++) {
                    value = value + "," + list_postions.get(i);
                }

                if (value.startsWith(",")) {
                    value = value.substring(1);
                }

            } else if (Exprestion.startsWith(ExpressionConstants.getValues.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");

                value = "" + Custom_fun.getValues(list_values[0], list_values[1], list_values[2], Valuestr.endsWith(",") ? "" : list_values[3].toUpperCase());

            } else if (Exprestion.startsWith(ExpressionConstants.getColumnDataFromTable.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                value = Valuestr;
                String[] list_values = Valuestr.split("\\,");
                value = "" + Custom_fun.getColumnDataFromTable(list_values[0], list_values[1], value.substring(value.indexOf("filters")));

            } else if (Exprestion.startsWith(ExpressionConstants.getMultiColumnValues.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                System.out.println("Valuestr===" + Valuestr);
                String[] list_values = Valuestr.split("\\,");
//                value = "" + Custom_fun.getValues(list_values[0], list_values[1], list_values[2], list_values[3].toUpperCase());
                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    String[] returnControl_List = list_values[0].substring(4, list_values[0].lastIndexOf(")")).split("\\.");
                    if (returnControl_List[2].toLowerCase().endsWith("_allcolumns")) {
                        LinkedHashMap<String, List<String>> map_APIData = new LinkedHashMap<String, List<String>>();

                        if (returnControl_List[0].equalsIgnoreCase(AppConstants.Global_API)) {
                            // map_APIData = AppConstants.GlobalObjects.getAPIs_ListHash().get(returnControl_List[1]);
                            //nk realm
                            map_APIData = RealmDBHelper.getTableDataInLHM(context, returnControl_List[1]);
                        } else if (returnControl_List[0].equalsIgnoreCase(AppConstants.Global_GetData)) {
                            // map_APIData = AppConstants.GlobalObjects.getForms_ListHash().get(returnControl_List[1]);
                            map_APIData = RealmDBHelper.getTableDataInLHM(context, returnControl_List[1]);
                        } else if (returnControl_List[0].equalsIgnoreCase(AppConstants.Global_Query)) {
                            //map_APIData = AppConstants.GlobalObjects.getForms_ListHash().get(returnControl_List[1]);
                            //nk realm
                            map_APIData = RealmDBHelper.getTableDataInLHM(context, returnControl_List[1]);
                        } else if (returnControl_List[0].equalsIgnoreCase(AppConstants.Global_SubControls)) {
                            map_APIData = ImproveHelper.getsubformValues(context, returnControl_List[1]);
                        }

                        Set<String> apiSet = map_APIData.keySet();
                        String[] apiNames = apiSet.toArray(new String[apiSet.size()]);
                        for (int i = 0; i < apiNames.length; i++) {
//                            String controlname = "(im:" + returnControl_List[0] + "." + returnControl_List[1] + "." + apiNames[i] + "_allrows)";
                            String controlname = "(im:" + returnControl_List[0] + "." + returnControl_List[1] + "." + apiNames[i] + ")";
                            value = value + "#" + apiNames[i] + "|" + Custom_fun.getValues(controlname, list_values[1], list_values[2], list_values[3].toUpperCase());
                        }
                        value = value.substring(value.indexOf("#") + 1);

                    } else {
                        value = "" + Custom_fun.getValues(list_values[0], list_values[1], list_values[2], list_values[3].toUpperCase());
                    }
                } else {
                    value = "" + Custom_fun.getValues(list_values[0], list_values[1], list_values[2], list_values[3].toUpperCase());
                }
            } else if (Exprestion.startsWith(ExpressionConstants.getValueByPositions.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                List<String> Values = new ArrayList<>();
                for (int i = 1; i < list_values.length; i++) {
                    if (list_values[i].toLowerCase().startsWith("(im:subcontrols") && !list_values[i].toLowerCase().endsWith("_allrows)")) {
                        Values.add(ImproveHelper.getValueFromGlobalObject(context, list_values[i]));
                    } else if (list_values[i].toLowerCase().startsWith("(im:")) {
                        Values.addAll(ImproveHelper.getListOfValuesFromGlobalObject(context, list_values[i]));
                    } else {
                        Values.add(list_values[i]);
                    }
                }


                value = "" + Custom_fun.getValueByPositions(list_values[0], Values);

            } else if (Exprestion.startsWith(ExpressionConstants.getListOfValuesByConditionEqual.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");

                value = "" + Custom_fun.getListOfValuesByConditionEqual(list_values[0], list_values[1], list_values[2]);

            } else if (Exprestion.startsWith(ExpressionConstants.getListOfValuesByLessthanCondition.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");

                value = "" + Custom_fun.getListOfValuesByLessthanCondition(list_values[0], list_values[1], list_values[2]);

            } else if (Exprestion.startsWith(ExpressionConstants.getListOfValuesByGreaterthanCondition.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");

                value = "" + Custom_fun.getListOfValuesByGreaterthanCondition(list_values[0], list_values[1], list_values[2]);

            } else if (Exprestion.startsWith(ExpressionConstants.getListOfValuesByLessthanEqualCondition.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");

                value = "" + Custom_fun.getListOfValuesByLessthanEqualCondition(list_values[0], list_values[1], list_values[2]);

            } else if (Exprestion.startsWith(ExpressionConstants.getListOfValuesByGreaterthanEqualCondition.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");

                value = "" + Custom_fun.getListOfValuesByGreaterthanEqualCondition(list_values[0], list_values[1], list_values[2]);

            } else if (Exprestion.startsWith(ExpressionConstants.getListOfValuesByInBetweenCondition.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");

                value = "" + Custom_fun.getListOfValuesByInBetweenCondition(list_values[0], list_values[1], list_values[2], list_values[3]);

            } else if (Exprestion.startsWith(ExpressionConstants.getDistance.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "", Value2 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    if (list_values[0].contains("\"")) {
                        Value0 = list_values[0].substring(list_values[0].indexOf("\"") + 1, list_values[0].lastIndexOf("\""));
                    } else {
                        Value0 = list_values[0];
                    }

                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    if (list_values[1].contains("\"")) {
                        Value1 = list_values[1].substring(list_values[1].indexOf("\"") + 1, list_values[1].lastIndexOf("\""));
                    } else {
                        Value1 = list_values[1];
                    }
                }

                try {
                    value = "" + Spatial_fun.getDistance(Value0.trim(), Value1.trim());
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.isWithinDistance.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "", Value2 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    if (list_values[0].contains("\"")) {
                        Value0 = list_values[0].substring(list_values[0].indexOf("\"") + 1, list_values[0].lastIndexOf("\""));
                    } else {
                        Value0 = list_values[0];
                    }


                }

                List<String> Values = new ArrayList<String>();
                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    String[] arr = ImproveHelper.getValueFromGlobalObject(context, list_values[1]).split("\\|");
                    Values = Arrays.asList(arr);
                } else {
                    String[] arr = list_values[1].split("\\|");
                    Values = Arrays.asList(arr);
                }

                if (list_values[2].toLowerCase().startsWith("(im:")) {
                    Value2 = ImproveHelper.getValueFromGlobalObject(context, list_values[2]);
                } else {
                    Value2 = list_values[2];
                }

                try {
                    value = "" + Spatial_fun.isWithinDistance(Value0.trim(), Values, Double.parseDouble(Value2.trim()));
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.isWithinBoundary.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "";


                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Value0 = list_values[0];
                }

                List<String> Values = new ArrayList<String>();
                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    String[] arr = ImproveHelper.getValueFromGlobalObject(context, list_values[1]).split("\\|");
                    Values = Arrays.asList(arr);
                } else {
                    String[] arr = list_values[1].split("\\|");
                    Values = Arrays.asList(arr);
                }

                try {
                    value = "" + Spatial_fun.isWithinBoundary(Value0.trim(), Values);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.getNearbyValue.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "", Value2 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    if (list_values[0].contains("\"")) {
                        Value0 = list_values[0].substring(list_values[0].indexOf("\"") + 1, list_values[0].lastIndexOf("\""));
                    } else {
                        Value0 = list_values[0];
                    }

                }

                List<String> Values = new ArrayList<String>();
                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    String[] arr = ImproveHelper.getValueFromGlobalObject(context, list_values[1]).split("\\|");
                    Values = Arrays.asList(arr);
                } else {
                    String[] arr = list_values[1].split("\\|");
                    Values = Arrays.asList(arr);
                }

                if (list_values[2].toLowerCase().startsWith("(im:")) {
                    Value2 = ImproveHelper.getValueFromGlobalObject(context, list_values[2]);
                } else {
                    Value2 = list_values[2];
                }
                if (Value2.trim().trim().length() == 0) {
                    Value2 = "100";
                }

                try {
                    value = "" + String.join(",", Spatial_fun.getNearbyValue(Value0.trim(), Values, Double.parseDouble(Value2.trim())));

                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.getMultiColumnNearbyValue.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String ValueA = "", Value0 = "", Value1 = "", Value2 = "";

                ValueA = list_values[0];

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    if (list_values[1].contains("\"")) {
                        Value0 = list_values[1].substring(list_values[0].indexOf("\"") + 1, list_values[0].lastIndexOf("\""));
                    } else {
                        Value0 = list_values[1];
                    }

                }

                List<String> Values = new ArrayList<String>();
                if (list_values[2].toLowerCase().startsWith("(im:")) {
                    String[] arr = ImproveHelper.getValueFromGlobalObject(context, list_values[2]).split("\\|");
                    Values = Arrays.asList(arr);
                } else {
                    String[] arr = list_values[2].split("\\|");
                    Values = Arrays.asList(arr);
                }

                if (list_values[3].toLowerCase().startsWith("(im:")) {
                    Value2 = ImproveHelper.getValueFromGlobalObject(context, list_values[2]);
                } else {
                    Value2 = list_values[3];
                }
                if (Value2.trim().trim().length() == 0) {
                    Value2 = "100";
                }

                try {
                    //value = "" + String.join(",", Spatial_fun.getMultiColumnNearbyValue(ValueA, Value0.trim(), Values, Double.parseDouble(Value2.trim())));

                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.getNearbyValueWithinRange.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "", Value2 = "", Value3 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    if (list_values[0].contains("\"")) {
                        Value0 = list_values[0].substring(list_values[0].indexOf("\"") + 1, list_values[0].lastIndexOf("\""));
                    } else {
                        Value0 = list_values[0];
                    }

                }

                List<String> Values = new ArrayList<String>();
                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    String[] arr = ImproveHelper.getValueFromGlobalObject(context, list_values[1]).split("\\,");
                    Values = Arrays.asList(arr);
                } else {
                    String[] arr = list_values[1].split("\\|");
                    Values = Arrays.asList(arr);
                }

                if (list_values[2].toLowerCase().startsWith("(im:")) {
                    Value2 = ImproveHelper.getValueFromGlobalObject(context, list_values[2]);
                } else {
                    Value2 = list_values[2];
                }

                if (list_values[3].toLowerCase().startsWith("(im:")) {
                    Value3 = ImproveHelper.getValueFromGlobalObject(context, list_values[3]);
                } else {
                    Value3 = list_values[3];
                }

                try {
                    //value = "" + String.join(",", Spatial_fun.getNearbyValueWithinRange(Value0.trim(), Values, Double.parseDouble("5"),Integer.parseInt("2")));
                    value = "" + String.join(",", Spatial_fun.getNearbyValueWithinRange(Value0.trim(), Values, Double.parseDouble(Value2), Integer.parseInt(Value3)));

                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.getmulticolumnnearbyvaluewithinrange.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String ValueSelectCols = "", ValueCurrentPoint = "", ValueListOfPoints = "", ValueNoOfRecords = "", ValueRange = "";
                ValueSelectCols = list_values[0];
                ValueListOfPoints = list_values[2];
                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    ValueCurrentPoint = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    if (list_values[1].contains("\"")) {
                        ValueCurrentPoint = list_values[1].substring(list_values[1].indexOf("\"") + 1, list_values[1].lastIndexOf("\""));
                    } else {
                        ValueCurrentPoint = list_values[1];
                    }
                }
                if (list_values[3].toLowerCase().startsWith("(im:")) {
                    ValueNoOfRecords = ImproveHelper.getValueFromGlobalObject(context, list_values[3]);
                } else {
                    if(list_values[3].startsWith("") && list_values[3].endsWith("")){
                        list_values[3]=list_values[3].substring(1,list_values[3].length()-1);
                    }
                    ValueNoOfRecords = list_values[3];
                }
                if (list_values[4].toLowerCase().startsWith("(im:")) {
                    ValueRange = ImproveHelper.getValueFromGlobalObject(context, list_values[3]);
                } else {
                    if(list_values[4].startsWith("") && list_values[4].endsWith("")){
                        list_values[4]=list_values[4].substring(1,list_values[4].length()-1);
                    }
                    ValueRange = list_values[4];
                }
                //SelectedCols & ListOfPonits
                if (ValueSelectCols.substring(0, ValueSelectCols.lastIndexOf(".")).equals(ValueListOfPoints.substring(0, ValueListOfPoints.lastIndexOf(".")))) {
                    String[] returnSelCols = ValueSelectCols.substring(4, ValueSelectCols.lastIndexOf(")")).split("\\.");
                    String[] returnListOfPoints = ValueListOfPoints.substring(4, ValueListOfPoints.lastIndexOf(")")).split("\\.");
                    String tableName = returnSelCols[1];
                    boolean allCols = returnSelCols[2].toLowerCase().endsWith("_allcolumns") ? true : false;
                    String selColName = null;
                    String selPointsColName = null;
                    if (returnSelCols[0].equalsIgnoreCase(AppConstants.Global_API) && returnListOfPoints[0].equalsIgnoreCase(AppConstants.Global_API)) {
                        List<String> outputPaths = new ArrayList<>();
                        if (!allCols) {
                            outputPaths = RealmDBHelper.getDataInRealResults(context, returnSelCols[1] + "_OutputPaths", "Path",
                                    new String[]{"KeyName"}, new String[]{returnSelCols[2]});
                        }
                        List<String> outputPaths1 = RealmDBHelper.getDataInRealResults(context, returnListOfPoints[1] + "_OutputPaths", "Path",
                                new String[]{"KeyName"}, new String[]{returnListOfPoints[2]});

                        if (outputPaths.size() > 0 && outputPaths1.size() > 0) {
                            //particular col & listofpoints col
                            List<String> tableNames = new ArrayList<>();
                            String outputTemp = outputPaths.get(0);
                            String temp = outputTemp.substring(0, outputTemp.lastIndexOf("/")).replace("/", "_");
                            String return_tableName = returnSelCols[1].substring(0, 9) + "_" + temp;
                            tableNames.add(return_tableName);
                            String return_colName = outputTemp.substring(outputTemp.lastIndexOf("/") + 1);
                            return_colName = return_colName.startsWith("@") ? return_colName.substring(1) : return_colName;
                            //
                            outputTemp = outputPaths1.get(0);
                            temp = outputTemp.substring(0, outputTemp.lastIndexOf("/")).replace("/", "_");
                            String search_tableName = returnListOfPoints[1].substring(0, 9) + "_" + temp;
                            tableNames.add(search_tableName);
                            String search_colName = outputTemp.substring(outputTemp.lastIndexOf("/") + 1);
                            search_colName = search_colName.startsWith("@") ? search_colName.substring(1) : search_colName;
                            tableName = getTableName(tableNames);
                            selColName = return_colName;
                            selPointsColName = search_colName;
                        } else {
                            //allcols & listofPoints col
                            String outputTemp = outputPaths1.get(0);
                            String temp = outputTemp.substring(0, outputTemp.lastIndexOf("/")).replace("/", "_");
                            String search_tableName = returnListOfPoints[1].substring(0, 9) + "_" + temp;
                            String search_colName = outputTemp.substring(outputTemp.lastIndexOf("/") + 1);
                            search_colName = search_colName.startsWith("@") ? search_colName.substring(1) : search_colName;
                            tableName = search_tableName;
                            selPointsColName = search_colName;
                        }
                    } else if (returnSelCols[0].equalsIgnoreCase(AppConstants.Global_GetData) && returnListOfPoints[0].equalsIgnoreCase(AppConstants.Global_GetData)) {
                        selColName = returnSelCols[2].toLowerCase();
                        selPointsColName = returnListOfPoints[2].toLowerCase();
                    } else if (returnSelCols[0].equalsIgnoreCase(AppConstants.Global_Query) && returnListOfPoints[0].equalsIgnoreCase(AppConstants.Global_Query)) {

                    }
                    try {
                        MultiColWithPoint multiColWithPoint = RealmDBHelper.getTableDataInMultiColWithPoint(context, tableName, selColName, allCols, selPointsColName);
                        List<PointWithDistance> listofPoints = Spatial_fun.getMultiColumnNearbyValueWithinRange(
                                ValueSelectCols, ValueCurrentPoint.trim(), multiColWithPoint.getListOfPoints(), Double.parseDouble(ValueNoOfRecords.trim()),
                                Integer.parseInt(ValueRange));
                        List<String> filterColData=new ArrayList<>();
                        for (int i = 0; i < listofPoints.size(); i++) {
                            filterColData.add(String.join("|", multiColWithPoint.getMap_pointtoCols().get(listofPoints.get(i).getPoint()))+"|"+listofPoints.get(i).getDistance());
                        }
                        value = "" + String.join(",", filterColData);
                        //value = "" + String.join(",", Spatial_fun.getMultiColumnNearbyValueWithinRange(ValueA,Value0.trim(), Values, Double.parseDouble(Value2.trim()),Integer.parseInt(Value3)));

                    } catch (Exception e) {
                        value = "";
                    }
                } else {
                    //Different DataSource
                }


               /* List<String> Values = new ArrayList<String>();
                if (list_values[2].toLowerCase().startsWith("(im:")) {
                    String[] arr = ImproveHelper.getValueFromGlobalObject(context, list_values[2]).split("\\,");
                    Values = Arrays.asList(arr);
                } else {
                    String[] arr = list_values[2].split("\\|");
                    Values = Arrays.asList(arr);
                }*/



            } else if (Exprestion.startsWith(ExpressionConstants.getNearestDistance.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    if (list_values[0].contains("\"")) {
                        Value0 = list_values[0].substring(list_values[0].indexOf("\"") + 1, list_values[0].lastIndexOf("\""));
                    } else {
                        Value0 = list_values[0];
                    }

                }

                List<String> Values = new ArrayList<String>();
                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    String[] arr = ImproveHelper.getValueFromGlobalObject(context, list_values[1]).split("\\|");
                    Values = Arrays.asList(arr);
                } else {
                    String[] arr = list_values[1].split("\\|");
                    Values = Arrays.asList(arr);
                }

                try {
                    value = "" + Spatial_fun.getNearestDistance(Value0.trim(), Values);

                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.CurrentTimeIn12HrFormate.toLowerCase())) {
                try {
                    value = "" + Time_fun.CurrentTimeIn12HrFormate();
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.CurrentTimeIn24HrFormate.toLowerCase())) {
                try {
                    value = "" + Time_fun.CurrentTimeIn24HrFormate();
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.getTimeIn12HrFormate.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    if (list_values[0].contains("\"")) {
                        Value0 = list_values[0].substring(list_values[0].indexOf("\"") + 1, list_values[0].lastIndexOf("\""));
                    } else {
                        Value0 = list_values[0];
                    }

                }
                try {
                    value = "" + Time_fun.getTimeIn12HrFormate(Value0);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.getTimeIn24HrFormate.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    if (list_values[0].contains("\"")) {
                        Value0 = list_values[0].substring(list_values[0].indexOf("\"") + 1, list_values[0].lastIndexOf("\""));
                    } else {
                        Value0 = list_values[0];
                    }

                }
                try {
                    value = "" + Time_fun.getTimeIn24HrFormate(Value0);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.getTimeDifference.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    if (list_values[0].contains("\"")) {
                        Value0 = list_values[0].substring(list_values[0].indexOf("\"") + 1, list_values[0].lastIndexOf("\""));
                    } else {
                        Value0 = list_values[0];
                    }

                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    if (list_values[1].contains("\"")) {
                        Value1 = list_values[1].substring(list_values[1].indexOf("\"") + 1, list_values[1].lastIndexOf("\""));
                    } else {
                        Value1 = list_values[1];
                    }

                }
                try {
                    value = "" + Time_fun.getTimeDifference(Value0, Value1);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.getNextTime.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    if (list_values[0].contains("\"")) {
                        Value0 = list_values[0].substring(list_values[0].indexOf("\"") + 1, list_values[0].lastIndexOf("\""));
                    } else {
                        Value0 = list_values[0];
                    }

                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    if (list_values[1].contains("\"")) {
                        Value1 = list_values[1].substring(list_values[1].indexOf("\"") + 1, list_values[1].lastIndexOf("\""));
                    } else {
                        Value1 = list_values[1];
                    }

                }
                try {
                    value = "" + Time_fun.getNextTime(Value0, Value1);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.getPreviousTime.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    if (list_values[0].contains("\"")) {
                        Value0 = list_values[0].substring(list_values[0].indexOf("\"") + 1, list_values[0].lastIndexOf("\""));
                    } else {
                        Value0 = list_values[0];
                    }

                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    if (list_values[1].contains("\"")) {
                        Value1 = list_values[1].substring(list_values[1].indexOf("\"") + 1, list_values[1].lastIndexOf("\""));
                    } else {
                        Value1 = list_values[1];
                    }

                }
                try {
                    value = "" + Time_fun.getPreviousTime(Value0, Value1);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.IsGreaterThanBaseTime.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Value0 = "", Value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Value0 = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    if (list_values[0].contains("\"")) {
                        Value0 = list_values[0].substring(list_values[0].indexOf("\"") + 1, list_values[0].lastIndexOf("\""));
                    } else {
                        Value0 = list_values[0];
                    }

                }

                if (list_values[1].toLowerCase().startsWith("(im:")) {
                    Value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    if (list_values[1].contains("\"")) {
                        Value1 = list_values[1].substring(list_values[1].indexOf("\"") + 1, list_values[1].lastIndexOf("\""));
                    } else {
                        Value1 = list_values[1];
                    }

                }
                try {
                    value = "" + Time_fun.IsGreaterThanBaseTime(Value0, Value1);
                } catch (Exception e) {
                    value = "";
                }
            } else if (Exprestion.startsWith(ExpressionConstants.daysBetween.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "", value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    value1 = list_values[1];
                }

                value = "" + Date_fun.daysBetween(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.split.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String Values = "", value1 = "";

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    Values = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    value1 = list_values[1];
                }

                value = "" + String_fun.split(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.stringWithCommas.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String strCommas = Valuestr.substring(0, (Valuestr.length() - 4));
                String strQuotes = Valuestr.substring((Valuestr.length() - 3));
                String strLQ = strQuotes.substring(0, (strQuotes.length() - 1));
                String strIndex = strLQ.substring(1);
                Log.d("CMCommaString: ", strCommas + "-" + strIndex);
//                value = "" + String_fun.getStringFromCommaDelimiter(strCommas, strIndex);

//                String[] list_values = Valuestr.split("\\,");
                String Values = "", value1 = "";

                if (strCommas.toLowerCase().startsWith("(im:subcontrols") && !strCommas.toLowerCase().endsWith("_allrows)")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, strCommas);
                } else if (strCommas.toLowerCase().startsWith("(im:")) {
                    Values = ImproveHelper.getValueFromGlobalObject(context, strCommas);
                } else {
                    Values = strCommas;
                }

                if (strIndex.toLowerCase().startsWith("(im:subcontrols") && !strIndex.toLowerCase().endsWith("_allrows)")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, strIndex);
                } else if (strIndex.toLowerCase().startsWith("(im:")) {
                    value1 = ImproveHelper.getValueFromGlobalObject(context, strIndex);
                } else {
                    value1 = strIndex;
                }
//
                value = "" + String_fun.getStringFromCommaDelimiter(Values, value1);
            } else if (Exprestion.startsWith(ExpressionConstants.getTimeDuration.toLowerCase())) {
                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                String ValueF = "", valueT = "", durationFormat = "H";

                if (list_values[0].toLowerCase().startsWith("(im:subcontrols") && !list_values[0].toLowerCase().endsWith("_allrows)")) {
                    ValueF = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else if (list_values[0].toLowerCase().startsWith("(im:")) {
                    ValueF = ImproveHelper.getValueFromGlobalObject(context, list_values[0]);
                } else {
                    ValueF = list_values[0];
                }

                if (list_values[1].toLowerCase().startsWith("(im:subcontrols") && !list_values[1].toLowerCase().endsWith("_allrows)")) {
                    valueT = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else if (list_values[1].toLowerCase().startsWith("(im:")) {
                    valueT = ImproveHelper.getValueFromGlobalObject(context, list_values[1]);
                } else {
                    valueT = list_values[1];
                }

                if (list_values[2].toLowerCase().startsWith("(im:subcontrols") && !list_values[2].toLowerCase().endsWith("_allrows)")) {
                    durationFormat = ImproveHelper.getValueFromGlobalObject(context, list_values[2]);
                } else if (list_values[2].toLowerCase().startsWith("(im:")) {
                    durationFormat = ImproveHelper.getValueFromGlobalObject(context, list_values[2]);
                } else {
                    durationFormat = list_values[2];
                }

                value = "" + Time_fun.getTimeDuration(ValueF, valueT, durationFormat);
            } else if (Exprestion.startsWith(ExpressionConstants.GET_PROPERTIES.toLowerCase())) {

                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String[] list_values = Valuestr.split("\\,");
                value = "" + gpf.getPropertyValue(list_values[0], list_values[1]); // (controlName,PropertyName)
            } else if (Exprestion.startsWith(ExpressionConstants.Encrypt.toLowerCase())) {
//                String Valuestr = Exprestion.substring(Exprestion.indexOf("(") + 1, Exprestion.lastIndexOf(")"));
                String Value0 = Exprestion.substring(Exprestion.indexOf("(") + 2, Exprestion.lastIndexOf("\","));
                String ValueType = Exprestion.substring(Exprestion.indexOf("\",") + 2, Exprestion.lastIndexOf(")"));

                try {
                    value = "" + ImproveHelper.encryptData(Value0.trim(), ValueType);
                } catch (Exception e) {
                    value = "";
                }
            } else {
                value = Exprestion;
            }

        } catch (Exception E) {
            System.out.println("Error at ");
        }
        return value;
    }

    private String getTableName(List<String> l_tableNames) {
        String tableName = l_tableNames.get(0);
        for (int i = 1; i < l_tableNames.size(); i++) {
            if (l_tableNames.get(i).contains(tableName)) {
                tableName = l_tableNames.get(i);
            }
        }

        return tableName;

    }

}
