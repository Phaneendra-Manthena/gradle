package com.bhargo.user.Expression.Interfaces;

public interface DateFunctions 
{

	public  boolean  isDateGreaterthan(String date1, String date2);
	public  boolean  isDateLessthan(String date1, String date2);
	 public  String  addBusinessDay(String date, int businessDays);
	 public  String  addDay(String date, int noOfDays);
	 public  String  addHours(String date, int noOfHours);
	 public  String addMinutes(String date, int noOfMinutes);
	 public  String  addMonths(String date, int noOfMonths);
	 public  String  addSeconds(String date, int noOfSeconds);
	 public  String   addWeeks(String date, int noOfWeeks);
	 public  String addYears(String date, int noOfYears);
	 public  String getDay(String date);
	 public  String getHour(String date);
	 public  String getMinute(String date);
	 public  String getMonth(String date);
	 public  String getSeconds(String date);
	 public  String getYear(String date);
	 public  int getDayOfYear(String date);
	 public  int getWeekOfYear(String date);
	 public  String  subDay(String date, int noOfDays);
	 public  String  subHours(String date, int noOfHours);
	 public  String  subMinutes(String date, int noOfMinutes);
	 public  String  subMonths(String date, int noOfMonths);
	 public  String  subSeconds(String date, int noOfSeconds);
	 public  String  subWeeks(String date, int noOfWeeks);
	 public  String  subYears(String date, int noOfYears);
	 
	 public  String  firstDayWeek(String date);
	 public  String  lastDayWeek(String date);
	 public   int  getWeekDay(String date);
	 public   int  monthsBetween(String dateOne, String dateTwo);
	 public   int  hoursBetween(String dateOne, String dateTwo);
	 public   int  yearsBetween(String dateOne, String dateTwo);
	 public   boolean  isDateValid(String dateToValidate, String dateFromat);	 
}

