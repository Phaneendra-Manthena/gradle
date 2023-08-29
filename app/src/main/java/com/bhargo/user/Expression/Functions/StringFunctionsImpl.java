package com.bhargo.user.Expression.Functions;

import com.bhargo.user.Expression.Interfaces.StringFunctions;

import java.util.List;

public class StringFunctionsImpl implements StringFunctions {
    public String stringConCat(String strOne, String strTwo) {
        return strOne.concat(strTwo);

    }

    public String stringCopy(String from, String to) {
        to = String.valueOf(from);

        return to;
    }

    public int stringLength(String strOne) {

        return strOne.trim().length();
    }

    public int stringPostion(String strOne, String strTwo) {

        return strOne.indexOf(strTwo);
    }

    public String stringReplace(String strOne, String oldStr, String newStr) {

        return strOne.replace(oldStr, newStr);
    }

    public String stringToUpper(String strOne) {

        return strOne.toUpperCase();
    }

    public String stringToLower(String strOne) {

        return strOne.toLowerCase();
    }

    public String stringReverse(String strOne) {

        StringBuilder sb = new StringBuilder(strOne);

        sb.reverse();

        return sb.toString();
    }

    public String stringRepeat(String strOne) {
        String repeated = new String(new char[3]).replace("\0", strOne);

        return repeated;
    }

    public String subStringInbetween(String strOne, int startIndex, int endIndex) {

        return strOne.substring(startIndex, endIndex);
    }

    public String subString(String strOne, int startIndex) {

        return strOne.substring(startIndex);
    }

    public String stringTrim(String strOne) {

        return strOne.trim();
    }

    public boolean stringStartsWith(String strOne, String prefix) {
        return strOne.startsWith(prefix);
    }

    public boolean stringEndsWith(String strOne, String prefix) {
        return strOne.endsWith(prefix);
    }

    public boolean stringCompare(String strOne, String strTwo) {

        return strOne.equals(strTwo);
    }

    public boolean stringCompareIgnoringCase(String strOne, String strTwo) {

        return strOne.equalsIgnoreCase(strTwo);
    }

    public boolean stringCompareWithListOfValues(List<String> ListOne, String strTwo) {

        return ListOne.contains(strTwo);
    }



    public String split(String str, String spiltStr) {
        String newsplitStr = "";
        for (int i = 0; i < spiltStr.length(); i++) {
            char c = spiltStr.charAt(i);
            if (Character.isDigit(c) || Character.isLetter(c)) {
                newsplitStr = newsplitStr + c;
            } else {
                newsplitStr = newsplitStr + "\\" + c;
            }
        }

        String returnStr = "";
        String[] sp = str.split(newsplitStr);
        for (int i = 0; i < sp.length; i++) {
            returnStr = returnStr + sp[i] + ",";
        }

        return returnStr.substring(0,returnStr.length()-1);
    }

    public String getStringFromCommaDelimiter(String strWithCommas, String index){
        String returnStr = "";
        String[] sp = strWithCommas.split("\\,");
        if(sp.length > Integer.parseInt(index)) {
                returnStr = sp[Integer.parseInt(index)];
        }
        return returnStr;
    }


}
