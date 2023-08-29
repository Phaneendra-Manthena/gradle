package com.bhargo.user.Expression.Interfaces;

import java.util.Date;
import java.util.List;

public interface GeneralFunctions 
{

	 public  String callage(Date birthDate);
	 public  String getDays(Date dateBefore, Date dateAfter);
	 public  String getMonths(Date dateBefore, Date dateAfter);
	 public  String getYear(Date dateBefore, Date dateAfter);
	 public  String getMonthlyEmi(double principal, double rate, double years);
	 public  boolean GeoFencing(String currentPosition, String referencePosition, double radious); // gps point should be sent lat and long separated by (comma),
	 public  boolean isGeoFencingList(String currentPosition, List<String> referenceList); // gps point should be sent lat and long separated by (comma),

	public boolean aadharCheck(String aadharno);

	public String getCurrentDate();
	public int getSize(List<String> MyArray);
}
