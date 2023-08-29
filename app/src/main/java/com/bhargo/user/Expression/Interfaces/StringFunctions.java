package com.bhargo.user.Expression.Interfaces;

import java.util.List;

public interface StringFunctions
{
	public String stringConCat(String strOne, String strTwo);
	public String stringCopy(String from, String to);
	public int stringLength(String strOne);
	//public String stringStringNCat();
	//public String stringStringNCmp();
	//public String stringStringCtrRepy();
	 public int stringPostion(String strOne, String strTwo);
	 public String stringReplace(String strOne, String oldStr, String newStr);
	 public String stringToUpper(String strOne);
	 public String stringToLower(String strOne);
	 public String stringReverse(String strOne);
	 public String stringRepeat(String strOne);
	 public String subStringInbetween(String strOne, int startIndex, int endIndex);
	 public String subString(String strOne, int startIndex);
	 public String stringTrim(String strOne);
	 public boolean stringStartsWith(String strOne, String prefix);
	 public boolean stringEndsWith(String strOne, String prefix);
	 public boolean stringCompare(String strOne, String strTwo);
	 public boolean stringCompareIgnoringCase(String strOne, String strTwo);
	public boolean stringCompareWithListOfValues(List<String> ListOne, String strTwo);
		
}
