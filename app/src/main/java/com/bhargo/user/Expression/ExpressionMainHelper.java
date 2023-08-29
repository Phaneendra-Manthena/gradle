/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bhargo.user.Expression;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ExpressionBuiler;
import com.bhargo.user.utils.ImproveHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Praveen
 */
public class ExpressionMainHelper {

    public static Context context;
    static String[] functionNames = new String[]{"Summation", "Multiplication", "Division", "Subtraction", "Percentage", "Power", "NthRoot", "Absolute",
            "Floor", "Round", "Ceil", "CallAge", "Days", "Months", "Year", "MonthlyEmi", "GeoDistance", "GeoFencing", "Maximum", "Minimum", /*"Count",*/ "Variance",
            "Mean", "Median", "Average","LengthOf","ToArray","addItem","removeItem","addItemAt","removeItemAt","concatenate","comparison","contains"
            , "ConCat", "Copy", "Length", "Position", "Replace", "ToUpper", "ToLower", "Reverse",
            "Repeat", "subString", "subString", "Trim", "StartsWith", "EndsWith", "Compare", "CompareIgnoringCase", "LISTCOMPARECASE","aadharCheck","getCurrentDate","getSize","getPositions","getListOfValuesByConditionEqual",
            "getListOfValuesByLessthanCondition","getListOfValuesByGreaterthanCondition","getListOfValuesByLessthanEqualCondition","getListOfValuesByGreaterthanEqualCondition","getListOfValuesByInBetweenCondition","getValues","getValueByPositions","getRandom","getRandomRange","getMultiColumnValues",
    "getDistance","isWithinDistance","isWithinBoundary","getNearbyValue","getNearbyValueWithinRange","getNearestDistance",
    "CurrentTimeIn12HrFormate","CurrentTimeIn24HrFormate","getTimeIn12HrFormate","getTimeIn24HrFormate","getTimeDifference",
            "getNextTime","getPreviousTime","IsGreaterThanBaseTime","getMultiColumnNearbyValue","getMultiColumnNearbyValueWithinRange",
            "addMonths","addSeconds","addMinutes","addHours","addDay","addBusinessDay","addWeeks","addYears","isDateGreaterthan",
            "isDateLessthan","split","daysBetween","getTimeDuration","Count","getPropertyValue","getStringFromCommaDelimiter","getColumnDataFromTable"};
    static String[] operatorNames = new String[]{AppConstants.Conditions_NotEquals,
            AppConstants.Conditions_GreaterThanEqualsTo,AppConstants.Conditions_LessThanEqualsTo, AppConstants.Conditions_GreaterThan,
            AppConstants.Conditions_lessThan, AppConstants.Conditions_Equals, "LOGICALAND", "LOGICALOR", "LOGICALNOT",
            AppConstants.Conditions_Contains,AppConstants.Conditions_StartsWith,AppConstants.Conditions_EndsWith,
            AppConstants.Conditions_IsNotNull,AppConstants.Conditions_IsNull};
    static String[] ar_operator = new String[]{"+", "-", "/", "*"};
    static String[] fun_brakets = new String[]{"(", ")", "[", "]", "{", "}"};
    static List<String> debug_string = new ArrayList<>();
    static int invalidFlag = 0;
    static int count = 0;
    static String expression = "2*3\"This is the sum of some values \" SUMMATION(5,3) + MULTIPLICATION(9,5,DIVISION(6,4)) ";
    static String innerExpression = "";
    static String resultOfFunction = "";
    static List<Integer> indexes = new ArrayList<>();
    static List<String> replaceIndexesValues = new ArrayList<>();
    static String rep_str = "";
    static int operatorExists = 0;
    static String expressionWithOperator = ""; // Operator Based expresssion 03032020
    static List<Integer> operatorIndexes = new ArrayList<>();

    static boolean parseFunctions() {

        int stringBody = 0;
        int sIndex = 0;
        String parsedString = "";
        char ch = ' ';
        int checkRecursive = expression.length();

        int i = 0;

        for (i = 0; i < expression.length(); i++) {

            ch = expression.charAt(i);

            if (ch == '"' && stringBody == 0) {
                stringBody = 1;
            } else if (ch == '"' && stringBody == 1) {
                stringBody = 0;
//            } else if (((charCheck(ch).equalsIgnoreCase("alphabet")||charCheck(ch).equalsIgnoreCase("digit")) && stringBody == 0) || (stringBody == 0 && ch == ' ')) {
            } else if (((charCheck(ch).equalsIgnoreCase("alphabet")) && stringBody == 0) || (stringBody == 0 && ch == ' ')) {
                parsedString += ch;

                if (checkFunction(parsedString)) {
                    sIndex = i + 1;

                    i = extractTotalFunction(i + 1, expression, parsedString);

                    if (isMultipleFunction(innerExpression)) {
//                        int c = 0;

                        for (String functionName : functionNames) {
//                            c = innerExpression.toLowerCase().indexOf(functionName.toLowerCase());
                            findSameFunction(innerExpression.toLowerCase(), functionName.toLowerCase());
//                            if (c > 0) {
//
//                            }
                        }

                        Collections.sort(indexes, Collections.reverseOrder());

                        int r = 0;

                        for (int j = 0; j < indexes.size(); j++) {

                            r = extractEvaluateFunction(indexes.get(j), innerExpression);
//                            innerExpression = innerExpression.replace(innerExpression.substring(0, r + 1), rep_str);
                            innerExpression = rep_str;
                            System.out.println("inner " + j + " :" + innerExpression);

                        }
                        indexes.clear();

                        String evf = evalFunc(parsedString + innerExpression);

                        expression = expression.replace(expression.substring(expression.indexOf(parsedString), i), evf);

                        innerExpression = "";
                        sIndex = 0;
                        parsedString = "";

                    } else {
                        String evf = evalFunc(parsedString + innerExpression);
                        expression = expression.replace(expression.substring(sIndex - (parsedString.length()), i), evf);

                        innerExpression = "";
                        sIndex = 0;
                        parsedString = "";
                    }
                    break;
                } else if (checkOperator(parsedString.trim()) && ch == ' ') {
                    parsedString = "";
                    operatorExists = 1;
                }
                else if(parsedString.equalsIgnoreCase("true")){
                    expression = expression.replace(expression.substring((i - (parsedString.length() - 1)), i + 1), "#");
                    parsedString = "";
                    break;
                } else if(parsedString.equalsIgnoreCase("false")){
                    expression = expression.replace(expression.substring((i - (parsedString.length() - 1)), i + 1), "~");
                    parsedString = "";
                    break;
                }

            }
        }
        return i != checkRecursive;
    }

   /* public static void main(String[] args) {

        findVariables(expression, "(im:");
        while (parseFunctions()) {
            parseFunctions();
        }

        if (operatorExists == 1) {
            
        } else {

            String executableExpression = "";
            String dup_expression = "";
            int stringBody = 0;

            char ch = ' ';

            for (int s = 0; s < expression.length(); s++) {

                ch = expression.charAt(s);

                if (ch == '"' && stringBody == 0) {
                    stringBody = 1;
                    if (!executableExpression.equalsIgnoreCase("")) {
                        //execute expression
                        String execute = "ex";
                        dup_expression += execute;
                        executableExpression = "";
                    }
                    dup_expression += ch;
                } else if (ch == '"' && stringBody == 1) {
                    dup_expression += ch;
                    stringBody = 0;
                } else if ((ch != '"') && stringBody == 0) {
                    executableExpression += ch;
                } else {
                    dup_expression += ch;
                }
            }

            if (!executableExpression.equalsIgnoreCase("")) {
                //execute expression
                String execute = "ex";
                dup_expression += execute;

            }

            expression = dup_expression;

        }
        expression = expression.replace("\"", "");

        System.out.println("Final Expression: " + expression);

    }*/

    static int extractEvaluateFunction(int k, String expr) {
        int eIndex = k;
        int b_count = 0;
        String evaluateFun = "";
        rep_str = expr;
        expr = expr.substring(k);

        for (int i = 0; i < expr.length(); i++) {

            evaluateFun += expr.charAt(i);

            if (expr.charAt(i) == '(') {
                b_count++;
            } else if (expr.charAt(i) == ')') {
                b_count--;
            }

            if (b_count == 0 && expr.charAt(i) == ')') {

//                innerExpression = expr.substring(loopId-funcName.length(), i);
//                innerExpression += ")";
                eIndex += (i + 1);
                break;
            }
        }

        resultOfFunction = evalFunc(evaluateFun);

        rep_str = rep_str.replace(rep_str.substring(k, eIndex), resultOfFunction);

//        replaceInnerExpressionString(k, eIndex, resultOfFunction);
        return eIndex;

    }

    static void replaceInnerExpressionString(int startIndex, int endIndex, String replacableString) {
        innerExpression = innerExpression.replace(innerExpression.substring(startIndex, endIndex), replacableString);
    }

    static String evalFunc(String evaluateFun) {
        ExpressionExecution Execution = new ExpressionExecution();
        return Execution.CheckMethodName(context, evaluateFun);
//        return "5";
    }

    static boolean isMultipleFunction(String inExp) {

        for (String functionName : functionNames) {
            if (inExp.toLowerCase().contains(functionName.toLowerCase()+"(")) {
                return true;
            }
        }
        return false;
    }

    public static void findVariables(String exp, String finder) {
//        int i = test.indexOf("(im:");
        String[] variablesPrefix = {"ControlName","submitresponse","Calenders", "SystemVariables",
                "GPSControl", "SubControls","Variables","RequestOfflineData","DataControls","ScanName","transactionaloffline","OfflineVariable"/*,"formfields","API","Query"*//*,"DataTableControls"*/};
        String findStr = finder;
        String formName = "";
        int lastIndex = 0;
        int count = 0;
        String subForm_allRows = "";

        for (int i = 0; i < variablesPrefix.length; i++) {
            lastIndex = 0;
//            while (lastIndex != -1) {
            while (lastIndex != -1) {

                if(lastIndex == 4){
                    int d = 0;
                }

                if(!variablesPrefix[i].contentEquals("SystemVariables")&&AppConstants.IS_MULTI_FORM &&
                        !(AppConstants.MULTI_FORM_TYPE.contentEquals(AppConstants.CALL_FORM_SINGLE_FORM)
                                ||AppConstants.MULTI_FORM_TYPE.contentEquals(AppConstants.CALL_FORM_SINGLE_DATA_MANAGEMENT))
                        &&!exp.contentEquals("")){
                    formName = exp.substring(4, exp.indexOf("."))+".";
                    lastIndex = exp.toLowerCase().indexOf((findStr +formName+ variablesPrefix[i]).toLowerCase(), lastIndex);
                }else {

                    lastIndex = exp.toLowerCase().indexOf((findStr + variablesPrefix[i]).toLowerCase(), lastIndex);
                }

                if ((findStr + variablesPrefix[i].toLowerCase()).equals("(im:subcontrols") && lastIndex != -1) {

                    for (int k = lastIndex; k < exp.length(); k++) {
                        if (exp.charAt(k) == ')') {
                            if (subForm_allRows.toLowerCase().endsWith("_allrows")
                                    || subForm_allRows.toLowerCase().endsWith("_checkedrows")
                                    || subForm_allRows.toLowerCase().endsWith("_uncheckedrows")||
                                    subForm_allRows.toLowerCase().endsWith("_allcolumns")) {
                                lastIndex = -1;
                                break;
                            }
                            break;
                        } else {
                            subForm_allRows += exp.charAt(k);
                        }

                    }


                } /*else if ((findStr + variablesPrefix[i].toLowerCase()).equals("(im:datatablecontrols") && lastIndex != -1) {

                    for (int k = lastIndex; k < exp.length(); k++) {
                        if (exp.charAt(k) == ')') {
                            if (subForm_allRows.toLowerCase().endsWith("_allrows")
                                    || subForm_allRows.toLowerCase().endsWith("_checkedrows")
                                    || subForm_allRows.toLowerCase().endsWith("_uncheckedrows")) {
                                lastIndex = -1;
                                break;
                            }
                            break;
                        } else {
                            subForm_allRows += exp.charAt(k);
                        }

                    }


                }*/

                if (lastIndex != -1) {
                    count++;
                    System.out.println(lastIndex);
                    parseVariables(lastIndex, exp.substring(lastIndex));
                    lastIndex += findStr.length();
                }
            }
        }
//        System.out.println(count);
    }

    public static void findVariables1(String exp, String finder) {
//        int i = test.indexOf("(im:");
        String[] variablesPrefix = {"ControlName","submitresponse","Calenders",
                "SystemVariables", "GPSControl", "SubControls","Variables","formfields",
                "API","Query","DML","offlinetables","getdata","managedata"/*"DataTableControls"*/};
        String findStr = finder;
        String formName = "";
        int lastIndex = 0;
        int count = 0;
        String subForm_allRows = "";

        for (int i = 0; i < variablesPrefix.length; i++) {
            lastIndex = 0;
//            while (lastIndex != -1) {
            while (lastIndex != -1) {

                if(lastIndex == 4){
                    int d = 0;
                }


                if(AppConstants.IS_MULTI_FORM && !AppConstants.MULTI_FORM_TYPE.contentEquals(AppConstants.CALL_FORM_SINGLE_FORM)&&!exp.contentEquals("")){
                    formName = exp.substring(4, exp.indexOf("."))+".";
                    lastIndex = exp.toLowerCase().indexOf((findStr +formName+ variablesPrefix[i]).toLowerCase(), lastIndex);
                }else {

                    lastIndex = exp.toLowerCase().indexOf((findStr + variablesPrefix[i]).toLowerCase(), lastIndex);
                }

                if ((findStr + variablesPrefix[i].toLowerCase()).equals("(im:subcontrols") && lastIndex != -1) {

                    for (int k = lastIndex; k < exp.length(); k++) {
                        if (exp.charAt(k) == ')') {
                            if (subForm_allRows.toLowerCase().endsWith("_allrows")
                                    || subForm_allRows.toLowerCase().endsWith("_checkedrows")
                                    || subForm_allRows.toLowerCase().endsWith("_uncheckedrows")||
                                    subForm_allRows.toLowerCase().endsWith("_allcolumns")) {
                                lastIndex = -1;
                                break;
                            }
                            break;
                        } else {
                            subForm_allRows += exp.charAt(k);
                        }

                    }


                } /*else if ((findStr + variablesPrefix[i].toLowerCase()).equals("(im:datatablecontrols") && lastIndex != -1) {

                    for (int k = lastIndex; k < exp.length(); k++) {
                        if (exp.charAt(k) == ')') {
                            if (subForm_allRows.toLowerCase().endsWith("_allrows")
                                    || subForm_allRows.toLowerCase().endsWith("_checkedrows")
                                    || subForm_allRows.toLowerCase().endsWith("_uncheckedrows")) {
                                lastIndex = -1;
                                break;
                            }
                            break;
                        } else {
                            subForm_allRows += exp.charAt(k);
                        }

                    }


                }*/

                if (lastIndex != -1) {
                    count++;
                    System.out.println(lastIndex);
                    parseVariables(lastIndex, exp.substring(lastIndex));
                    lastIndex += findStr.length();
                }
            }
        }
//        System.out.println(count);
    }

    public static boolean findExpressionHasOnlyOneVariable(String exp) {
        exp = exp.toLowerCase();
        int sIn = 0;
        int eIn = 0;

        if (exp.startsWith("(im:SubControls.".toLowerCase()) || exp.startsWith("(im:API.".toLowerCase()) ||
                exp.startsWith("(im:Query.".toLowerCase()) || exp.startsWith("(im:FormFields.".toLowerCase())||
                exp.startsWith("(im:SMS.".toLowerCase())||
                exp.startsWith("(im:Variables.Multiple.".toLowerCase())||
                exp.startsWith("(im:DataTableControls.".toLowerCase())||
                exp.startsWith("(im:DataViewerControls.".toLowerCase())||
                exp.startsWith("(im:RequestOfflineData.".toLowerCase())||
                exp.startsWith("(im:DataControls.".toLowerCase())) {
            sIn = exp.indexOf("(");
            eIn = exp.indexOf(")");
            return (eIn - sIn) + 1 == exp.length();
        }
        return false;
    }


    public static void findSameFunction(String exp, String finder) {
        String findStr = finder;
        int lastIndex = 0;

        while (lastIndex != -1) {

            lastIndex = exp.indexOf(findStr, lastIndex);

            if (lastIndex != -1) {
                indexes.add(lastIndex);
                lastIndex += findStr.length();
            }
        }
    }

    public static List<Integer> findString(String exp, String finder) {
        String findStr = finder;
        int lastIndex = 0;
        List<Integer> fString = new ArrayList<>();
        while (lastIndex != -1) {

            lastIndex = exp.indexOf(findStr, lastIndex);

            if (lastIndex != -1) {
                fString.add(lastIndex);
                lastIndex += findStr.length();
            }
        }
        return fString;
    }

    public static void parseVariables(int index, String expr) {
        String replacement = "";
        int endIndex = expr.indexOf(")");
        String result = expr.substring(0, endIndex);
        result += ")";
        replacement = evaluateVariable(result);
        replaceString(result, replacement);
    }

    public static String evaluateVariable(String var) {
       /* if (var.equalsIgnoreCase("(im:controlName.id)")) {
            return "2";
        } else if (var.equalsIgnoreCase("(im:controlName.id1)")) {
            return "4";
        } else {
            return "";
        }*/

        return ImproveHelper.getValueFromGlobalObject(context, var);
    }

    public static void replaceString(String source, String replace) {
        if(replace!=null) {
            expression = expression.toLowerCase().replace(source, replace);
        }else{
            expression = expression.toLowerCase().replace(source, "");
        }
        System.out.println(expression);
    }

    static int extractTotalFunction(int loopId, String expr, String funcName) {
        int b_count = 0;

        for (int i = loopId; i < expr.length(); i++) {

            if (b_count != 0 || (b_count != 1 && expr.charAt(i) != ')')) {
                innerExpression += expr.charAt(i);
            }

            if (expr.charAt(i) == '(') {
                b_count++;
            } else if (expr.charAt(i) == ')') {
                b_count--;
            }

            if (b_count == 0 && expr.charAt(i) == ')') {

//                innerExpression = expr.substring(loopId-funcName.length(), i);
//                innerExpression += ")";
                loopId = i + 1;
                break;
            }

        }
        return loopId;
    }

    static boolean checkFunction(String input) {
        List<String> list = Arrays.asList(functionNames);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.replaceAll(String::toUpperCase);
        }else{
            for (int i = 0; i <list.size() ; i++) {
                list.set(i,list.get(i).toUpperCase(Locale.ROOT));
            }
        }

        return list.contains(input.toUpperCase());
    }

    static String findFormat(char ch) {
        if (ch == '"') {
            return "text";
        } else if (charCheck(ch).equalsIgnoreCase("alphabet")) {
            return "fn";
        } else if (charCheck(ch).equalsIgnoreCase("digit")) {
            return "digit";
        } else if (ch == '+') {
            return "";
        }

        return "";
    }

    static String charCheck(char input_char) {
        // CHECKING FOR ALPHABET
        if ((input_char >= 65 && input_char <= 90)
                || (input_char >= 97 && input_char <= 122)) {
            return "alphabet";
        } // CHECKING FOR DIGITS
        else if (input_char >= 48 && input_char <= 57) {
            return "digit";
        } // OTHERWISE SPECIAL CHARACTER
        else {
            return "character";
        }
    }

    static int parseCharacters(String expressionString, int i, boolean restrict, String formalresult) {
        String result = "";
        for (int j = i; j < expressionString.length(); j++) {
            if (restrict && charCheck(expressionString.charAt(j)).equalsIgnoreCase("Other")) {
                invalidFlag = 1;
            }
            if (expressionString.charAt(j) != '"') {
                result = result + expressionString.charAt(j);
            } else {
                i = j;
                formalresult += result;
                if (restrict) {
                    String[] actualParameters = formalresult.split(",");
                    for (int p = 0; p < actualParameters.length; p++) {
                        debug_string.add(actualParameters[p]);
                        if (p != actualParameters.length - 1) {
                            debug_string.add(",");
                        }
                    }

                } else {
                    debug_string.add(result);
                }
                break;
            }

        }

        return i;
    }

    static boolean checkOperator(String input) {
        List<String> list = Arrays.asList(operatorNames);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.replaceAll(String::toUpperCase);
        }else{
            for (int i = 0; i <list.size() ; i++) {
                list.set(i,list.get(i).toUpperCase(Locale.ROOT));
            }
        }
//        int initialPosition = expression.indexOf(input);
//        int length1 = initialPosition + input.length();
//        System.out.println("checkoperator: " + expression.charAt(length1 + 1));
//        if(expression.charAt(length1 + 1) != ' ' ){
//            return false;
//        }

        return list.contains(input.toUpperCase());
    }

    static int parseDigits(String expressionString, int i) {
        String result = "";
        for (int j = i; j < expressionString.length(); j++) {
            if (j == expressionString.length() - 1) {
                i = j;
            }
            if (expressionString.charAt(j) != '"' && !(charCheck(expressionString.charAt(j)).equalsIgnoreCase("alphabet"))) {
                if (expressionString.charAt(j) != ' ') {
                    result = result + expressionString.charAt(j);
                }
            } else if (expressionString.charAt(j) == '+' || expressionString.charAt(j) == '-' || expressionString.charAt(j) == '*' || expressionString.charAt(j) == '/') {
                result = result + expressionString.charAt(j);
            } else {
                i = j;
                debug_string.add(result);
                break;
            }
        }
        System.out.println("Result: " + result);

        return i;
    }

    // operator execution 03032020
    static String getOperator(String op) {
        if(op.equalsIgnoreCase(AppConstants.Conditions_Equals)) {
            op = "==";
        }else if(op.equalsIgnoreCase(AppConstants.Conditions_NotEquals)) {
                op = "!=";
        }else if(op.equalsIgnoreCase(AppConstants.Conditions_GreaterThan)) {
                op = ">";
        }else if(op.equalsIgnoreCase(AppConstants.Conditions_lessThan)) {
                op = "<";
        }else if(op.equalsIgnoreCase(AppConstants.Conditions_GreaterThanEqualsTo)) {
                op = ">=";
        }else if(op.equalsIgnoreCase(AppConstants.Conditions_LessThanEqualsTo)) {
                op = "<=";
        }else if(op.equalsIgnoreCase("LOGICALAND")) {
                op = "&&";
        }else if(op.equalsIgnoreCase("LOGICALOR")) {
                op = "||";
        }else if(op.equalsIgnoreCase("LOGICALNOT")) {
                op = "!";
        }else if(op.equalsIgnoreCase(AppConstants.Conditions_Contains)) {
                op = "^";
        }else if(op.equalsIgnoreCase(AppConstants.Conditions_StartsWith)) {
                 op = "%&";
        }else if(op.equalsIgnoreCase(AppConstants.Conditions_EndsWith)) {
                op = "&%";
        }else if(op.equalsIgnoreCase(AppConstants.Conditions_IsNull)) {
                op = "^@";
        }else if(op.equalsIgnoreCase(AppConstants.Conditions_IsNotNull)) {
                  op = "!^@";
        }
        return op;
    }


    // for operator 03032020
    public static int findLastOpenbrakets(String exp, String finder) {
        int lastIndex = 0;
        int index = -1;
        while (lastIndex != -1) {

            lastIndex = exp.indexOf(finder, lastIndex);

            if (lastIndex != -1) {
                index = lastIndex;
//                operatorIndexes.add(lastIndex);
                lastIndex += finder.length();
            }
        }
        return index;
    }

    // Get inner expression for operator 03032020
    static String innerExpressionForOperator(String expr) {

        int bIndex = -1;
        int bEndIndex = 0;
        String inExpr = "";

        if (expr.equals("#") || expr.equals("~")) {
            return expr;
        }

        bIndex = findLastOpenbrakets(expr, "(");

        if (bIndex != -1) {
            // Extract the inner statement
            bEndIndex = bIndex;
            while (true) {
                if (expr.charAt(bEndIndex) != ')') {
                    inExpr += expr.charAt(bEndIndex);
                } else {
                    inExpr += expr.charAt(bEndIndex);
                    bEndIndex++;
                    break;
                }

                bEndIndex++;
            }
            System.out.println("bracket index is at: " + inExpr);

            // if inner statement has operator or string
            Pattern pattern = Pattern.compile(".*[a-zA-Z]+.*");
            Matcher matcher = pattern.matcher(inExpr);

            if (matcher.matches()) {
                // then send to findFirstOperator method
                expressionWithOperator = "";
                expr = expr.replace(expr.substring(bIndex, bIndex + inExpr.length()), findFirstOperator(inExpr));
            } else {
                inExpr = inExpr.replaceAll("\\(", "");
                inExpr = inExpr.replaceAll("\\)", "");
                ExpressionBuiler expressionBuiler = new ExpressionBuiler();
                String execute = "" + ExpressionBuiler.EvalExpOne(inExpr);
                expr = expr.replace(expr.substring(bIndex, bIndex + inExpr.length()), execute);
            }

            expr = innerExpressionForOperator(expr);

        } else {
            // Extract the inner statement

            if ("#".equals(expr) || "~".equals(expr)) {
            } else {
                expressionWithOperator = "";
                expr = findFirstOperator(expr);
            }

        }

        // else
        // do evaluation
        // replace the expression with return value
        //update the global expression with new expression
        // recursive method until brackets indexes get exhausted
        // return the expression
        return expr;
    }

    public static List<Integer> findOperator(String exp, String finder) {
        String findStr = finder;
        int lastIndex = 0;
        while (lastIndex != -1) {

            lastIndex = exp.indexOf(findStr, lastIndex);

            if (lastIndex != -1) {
                operatorIndexes.add(lastIndex);
                lastIndex += findStr.length();
            }
        }
        return operatorIndexes;
    }

    // If Operation exists functions 03-03-2020
    static String findFirstOperator(String oExpression) {
        oExpression = oExpression.replace("(", "");
        oExpression = oExpression.replace(")", "");
        List<Integer> ops = new ArrayList<>();
        operatorIndexes.clear();
        //find first operator in the string
        for (String op : operatorNames) {
            ops = findOperator(oExpression.toLowerCase(), op.toLowerCase());
        }

        Pattern pattern = Pattern.compile(".*[a-zA-Z]+.*");

        if (ops.size() > 0) {

            Collections.sort(ops);

            // split with the operator
            String s1 = oExpression.substring(0, ops.get(0));
            String s2 = oExpression.substring(ops.get(0));

            // find for strings in the left operand
            Matcher matcher = pattern.matcher(s1);

            // if string is not exists
            if (!matcher.matches()) {
                // evaluate expression
                s1 = s1.replaceAll("\\(", "");
                s1 = s1.replaceAll("\\)", "");
                if (!(s1.trim().equals("#") || (s1.trim().equals("~"))) && s1.trim().length() > 0) {
                    ExpressionBuiler expressionBuiler = new ExpressionBuiler();
                    String execute = "" + ExpressionBuiler.EvalExpOne(s1);
                    expressionWithOperator += execute;
                } else {
                    expressionWithOperator += s1;
                }


            } // else
            else {
                // split and eval expression
                if (!s1.trim().equals("#") && !s1.trim().equals("~")) {
                    expressionWithOperator += evalExpressionWithString(s1);
                } else {
                    expressionWithOperator += s1;
                }
                // check for s1 is true or false
            }

            // remove the operator from the second operand
            String[] removableOperator = s2.split(" ");
            int length = removableOperator[0].length();

            s2 = s2.substring(length);

            expressionWithOperator += " " + removableOperator[0] + " ";
            System.out.println("op: " + expressionWithOperator);
            // recursive method
            if (s2.length() > 0) {
                findFirstOperator(s2);
            }

            if (expressionWithOperator.equals("#") || expressionWithOperator.equals("~")) {
                return expressionWithOperator;
            }
            expressionWithOperator = exec(expressionWithOperator);
        } else {

            if (oExpression.trim().equals("#") || oExpression.trim().equals("~")) {
                expressionWithOperator += oExpression;
                System.out.println("op1: " + expressionWithOperator);
                return expressionWithOperator;
            }

            Matcher matcher = pattern.matcher(oExpression);
            if (!matcher.matches()) {
                // evaluate expression
                oExpression = oExpression.replaceAll("\\(", "");
                oExpression = oExpression.replaceAll("\\)", "");
                ExpressionBuiler expressionBuiler = new ExpressionBuiler();
                String execute = "" + ExpressionBuiler.EvalExpOne(oExpression);
                expressionWithOperator += execute;
            } // else
            else {
                // split and eval expression
                expressionWithOperator += evalExpressionWithString(oExpression);
            }
            System.out.println("op1: " + expressionWithOperator);
        }

        // return string
        return expressionWithOperator;
    }

    static String exec(String s) {

        String castString = "";
        for (String op : operatorNames) {
            s = s.toUpperCase().replace(op, getOperator(op));
        }
        Pattern pattern = Pattern.compile(".*[a-zA-Z]+.*");
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            for (int i = 0; i < s.length(); i++) {
                if (charCheck(s.charAt(i)).equalsIgnoreCase("alphabet") && !charCheck(s.charAt(i)).equalsIgnoreCase("#") && !charCheck(s.charAt(i)).equalsIgnoreCase("~")) {
                    castString += (int) s.charAt(i);
                } else {
                    castString += s.charAt(i);
                }
            }
            s = castString;
        }

        s = execOperator(s);

        return s;
    }

    static String execOperator(String opStr) {
        OperatorsBuilder operator = new OperatorsBuilder();
        String valuestr = OperatorsBuilder.bulidOperators(opStr);
        if (valuestr.equals("0.0")) {
            return "~";
        } else if (valuestr.equals("1.0")) {
            return "#";
        } else {
            return "";
        }
    }

    // split and eval expression for operator based expression 03032020
    static String evalExpressionWithString(String expr) {
        String executableExpression = "";
        String dup_expression = "";
        int stringBody = 0;

        char ch = ' ';

        for (int s = 0; s < expr.length(); s++) {

            ch = expr.charAt(s);

            if (ch == '"' && stringBody == 0) {
                stringBody = 1;
                if (!executableExpression.trim().equalsIgnoreCase("")) {
                    //execute expression

                    ExpressionBuiler expressionBuiler = new ExpressionBuiler();
                    String execute = "" + ExpressionBuiler.EvalExpOne(executableExpression);

//                    String execute = "ex";
                    dup_expression += execute;
                    executableExpression = "";
                }
                dup_expression += ch;
            } else if (ch == '"' && stringBody == 1) {
                dup_expression += ch;
                stringBody = 0;
            } else if ((ch != '"') && stringBody == 0) {
                executableExpression += ch;
            } else {
                dup_expression += ch;
            }
        }

        if (!executableExpression.trim().equalsIgnoreCase("")) {
            //execute expression
            ExpressionBuiler expressionBuiler = new ExpressionBuiler();
            String execute = "" + ExpressionBuiler.EvalExpOne(executableExpression);
//            String execute = "ex";
            dup_expression += execute;

        }

        expr = dup_expression.replaceAll("\"", "");
        return expr;
    }

    public String ExpressionHelper(Context context, String Exprestionstr) {
        try {
            ExpressionMainHelper.context = context;
            operatorExists = 0;
            System.out.println("Begining Expression: " + Exprestionstr);
//        Exprestionstr="(im:subcontrols.details.details_a) GREATERTHAN 100";
            expression = Exprestionstr;
            //expression = "LENGTH((IM:CONTROLNAME.DBT_ID)) EQUALS 13";
            //expression = "LENGTH("2371516487434") EQUALS 13";
            // expression = "addmonths(\"2021-05-27\",3)";
            if (findExpressionHasOnlyOneVariable(expression.trim())) {
                //execute
                List<String> valuesarr = ImproveHelper.getListOfValuesFromGlobalObject(context, expression);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    expression = String.join(",", valuesarr);
                }else{
                    expression = TextUtils.join(",", valuesarr);
                }
                return expression;
            }

            findVariables(Exprestionstr.toLowerCase(), "(im:".toLowerCase());

            while (parseFunctions()) {
                parseFunctions();
            }
            if (operatorExists == 1) {

                expression = expression.toLowerCase().replaceAll("true", "#");
                expression = expression.toLowerCase().replaceAll("false", "~");

                expression = innerExpressionForOperator(expression);
//            expressionWithOperator = "";
//            findFirstOperator(expression);//  03032020

//            expression = expressionWithOperator;
                if (expression.equals("#")) {
                    expression = "true";
                } else {
                    expression = "false";
                }

                System.out.println("expression with operator is: " + expression);
                return expression;

            } else {

                String executableExpression = "";
                String dup_expression = "";
                int stringBody = 0;

                char ch = ' ';

                for (int s = 0; s < expression.length(); s++) {

                    ch = expression.charAt(s);

                    if (ch == '"' && stringBody == 0) {
                        stringBody = 1;
                        if ((!executableExpression.equalsIgnoreCase("")) && !executableExpression.matches(".*[a-zA-Z]+.*") && (executableExpression.indexOf("$") == -1 || executableExpression.indexOf("^") == -1) && (!executableExpression.contains(","))) {
                            //execute expression

                            ExpressionBuiler expressionBuiler = new ExpressionBuiler();
                            String execute = "" + ExpressionBuiler.EvalExpOne(executableExpression);
                            dup_expression += execute;
                            executableExpression = "";


                        } else if ((!executableExpression.equalsIgnoreCase("")) && (executableExpression.matches(".*[a-zA-Z]+.*") || !(executableExpression.indexOf("$") == -1 || executableExpression.indexOf("^") == -1) || executableExpression.contains(","))) {
                            dup_expression += executableExpression;
                            executableExpression = "";
                        }
                        dup_expression += ch;
                    } else if (ch == '"' && stringBody == 1) {
                        dup_expression += ch;
                        stringBody = 0;
                    } else if ((ch != '"') && stringBody == 0) {
                        executableExpression += ch;
                    } else {
                        dup_expression += ch;
                    }
                }
//                executableExpression="GETPOSITIONS((IM:FORMFIELDS.POPULAR PLACEMENT LIST POPULAR PLACEMENT LIST SF.POPULAR_PLACEMENT_LIST_SF_STATUS),EQUALS,\"APPLY_AllRows\")";
                if ((!executableExpression.equalsIgnoreCase("")) && !executableExpression.matches(".*[a-zA-Z]+.*")
                        &&!executableExpression.matches(".*[0-9]+.*")
                        && (executableExpression.indexOf("$") == -1 || executableExpression.indexOf("^") == -1) && (!executableExpression.contains(","))) {
                    //execute expression
                    ExpressionBuiler expressionBuiler = new ExpressionBuiler();
                    String execute = "" + ExpressionBuiler.EvalExpOne(executableExpression);
//                String execute = "ex";
                    dup_expression += execute;

                } else if ((!executableExpression.equalsIgnoreCase("")) && (executableExpression.matches(".*[a-zA-Z]+.*")||
                        (executableExpression.matches(".*[0-9]+.*"))|| !(executableExpression.indexOf("$") == -1 || executableExpression.indexOf("^") == -1) || executableExpression.contains(","))) {
                    dup_expression += executableExpression;

                }
                expression = dup_expression;
                findVariables1(expression.toLowerCase(), "(im:".toLowerCase());


            }
            expression = expression.replace("\"", "");
            if (expression.endsWith(".0")) {
                expression = expression.substring(0, expression.length() - 2);
            }

            System.out.println("Final Expression: " + expression);
        }catch (Exception e){
            System.out.println("Error at expression is :"+e.toString());
            expression = expression.replace("\"", "");
            if (expression.endsWith(".0")) {
                expression = expression.substring(0, expression.length() - 2);
            }
        }
        if(expression.contentEquals("#")){
            expression="true";
        }else if(expression.contentEquals("~")){
            expression="false";
        }
        return expression;
    }
}
