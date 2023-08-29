package com.bhargo.user.Expression.Functions;

import com.bhargo.user.Expression.Interfaces.DateFunctions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFunctionsImpl  implements DateFunctions
{

	 public   String  addBusinessDay(String date,int businessDays)
	 {
		 
		 	// Given Date in String format
			//String oldDate = "2017-01-29";
		 
		 	int reminder=businessDays%5;
		 	int coefficent=businessDays/5;
		 	
		 	businessDays=coefficent*7+reminder;
		 	
		 	System.out.println("coefficent: "+coefficent);
		 	System.out.println("reminder: "+reminder);
			System.out.println("Date before Addition: "+date);
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			}catch(ParseException e){
				e.printStackTrace();
			 }
			   
			// Number of Days to add
			c.add(Calendar.DAY_OF_MONTH, businessDays);  
			//Date after adding the days to the given date
			String newDate = sdf.format(c.getTime());  
			//Displaying the new Date after addition of Days
			System.out.println("Date after Addition: "+newDate);
			
			return newDate;
		 
	 }
	
	 public   String  addDay(String date,int noOfDays)
	 {
		 
		 	// Given Date in String format
			//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			}catch(ParseException e){
				e.printStackTrace();
			 }
			
			// Number of Days to add
			c.add(Calendar.DAY_OF_MONTH, noOfDays);  
			//Date after adding the days to the given date
			String newDate = sdf.format(c.getTime());  
			//Displaying the new Date after addition of Days
			System.out.println("Date after Addition: "+newDate);
			
			return newDate;
		 
	 }
	 
	 public  String  addHours(String date,int noOfHours)
	 {
			// Given Date in String format
			//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			}catch(ParseException e){
				e.printStackTrace();
			}
			   
			// Number of Days to add
			c.add(Calendar.HOUR_OF_DAY, noOfHours);  
			//Date after adding the days to the given date
			String newDate = sdf.format(c.getTime());  
			//Displaying the new Date after addition of Days
			System.out.println("Date after Addition: "+newDate);
			
			return newDate;
		 
	 }
	 
	 public  String  addMinutes(String date,int noOfMinutes)
	 {
			// Given Date in String format
			//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			}catch(ParseException e){
				e.printStackTrace();
			}
			   
			// Number of Days to add
			c.add(Calendar.MINUTE, noOfMinutes);  
			//Date after adding the days to the given date
			String newDate = sdf.format(c.getTime());  
			//Displaying the new Date after addition of Days
			System.out.println("Date after Addition: "+newDate);
			
			return newDate;
		 
	 }
	 
	 
	 public  String  addMonths(String date,int noOfMonths)
	 {
		// Given Date in String format
//					String oldDate = "2017-01-29";


		 if(date.startsWith("\"")) {
			 date = date.substring(1, date.length() - 1);//(need to add)
		 }
				 	
					//Specifying date format that matches the given date
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Calendar c = Calendar.getInstance();
					
					try{
					   //Setting the date to the given date
					   c.setTime(sdf.parse(date));
					}catch(ParseException e){
						e.printStackTrace();
					}

					// Number of Days to add
					c.add(Calendar.MONTH, noOfMonths);  
					//Date after adding the days to the given date
					String newDate = sdf.format(c.getTime());  
					//Displaying the new Date after addition of Days
					System.out.println("Date after Addition: "+newDate);

					newDate = "\""+newDate+"\"";
					return newDate;
		 
	 }
	 
	 
	 public  String  addSeconds(String date,int noOfSeconds)
	 {
		// Given Date in String format
			//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			}catch(ParseException e){
				e.printStackTrace();
			}
			   
			// Number of Days to add
			c.add(Calendar.SECOND, noOfSeconds);  
			//Date after adding the days to the given date
			String newDate = sdf.format(c.getTime());  
			//Displaying the new Date after addition of Days
			System.out.println("Date after Addition: "+newDate);
			
			return newDate;
		 
		 
	 }
	 
	 public  String  addWeeks(String date,int noOfWeeks)
	 {
		// Given Date in String format
			//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			}catch(ParseException e){
				e.printStackTrace();
			}
			   
			// Number of Days to add
			c.add(Calendar.WEEK_OF_YEAR, noOfWeeks);  
			//Date after adding the days to the given date
			String newDate = sdf.format(c.getTime());  
			//Displaying the new Date after addition of Days
			System.out.println("Date after Addition: "+newDate);
			
			return newDate;
		 
		 
	 }
	 
	 public  String  addYears(String date,int noOfYears)
	 {
		// Given Date in String format
			//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			}catch(ParseException e){
				e.printStackTrace();
			}
			   
			// Number of Days to add
			c.add(Calendar.YEAR, noOfYears);  
			//Date after adding the days to the given date
			
			
			
			String newDate = sdf.format(c.getTime());  
			//Displaying the new Date after addition of Days
			System.out.println("Date after Addition: "+newDate);
			
			return newDate;
		 
	 }
	 
	 public  String getDay(String date)
	 { 
		 			// Given Date in String format
					//String oldDate = "2017-01-29";
				 	
					//Specifying date format that matches the given date
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
					SimpleDateFormat sdfReq = new SimpleDateFormat("dd-MM-yyyy");
					
					Date d=null;
					try {
						d=sdf.parse(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					String newDate=sdfReq.format(d);
					
				// System.out.println(newDate);
				
				return newDate;
		 
	 }
	 
	 
	 public  String getHour(String date)
	 { 
		 			// Given Date in String format
					//String oldDate = "2017-01-29";
				 	
					//Specifying date format that matches the given date
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
				SimpleDateFormat sdfReq = new SimpleDateFormat("hh");
					
					Date d=null;
					try {
						d=sdf.parse(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					String newDate=sdfReq.format(d);
					
				 System.out.println(newDate);
				
				return newDate;
		 
	 }
	 
	 
	 public  String getMinute(String date)
	 { 
		 			// Given Date in String format
					//String oldDate = "2017-01-29";
				 	
					//Specifying date format that matches the given date
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
				SimpleDateFormat sdfReq = new SimpleDateFormat("mm");
					
					Date d=null;
					try {
						d=sdf.parse(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					String newDate=sdfReq.format(d);
					
				 System.out.println(newDate);
				
				return newDate;
		 
	 }
	 
	 public  String getMonth(String date)
	 { 
		 			// Given Date in String format
					//String oldDate = "2017-01-29";
				 	
					//Specifying date format that matches the given date
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
				SimpleDateFormat sdfReq = new SimpleDateFormat("MM");
					
					Date d=null;
					try {
						d=sdf.parse(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					String newDate=sdfReq.format(d);
					
				 System.out.println(newDate);
				
				return newDate;
		 
	 }
	 
	 public  String getSeconds(String date)
	 { 
		 			// Given Date in String format
					//String oldDate = "2017-01-29";
				 	
					//Specifying date format that matches the given date
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
				SimpleDateFormat sdfReq = new SimpleDateFormat("ss");
					
					Date d=null;
					try {
						d=sdf.parse(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					String newDate=sdfReq.format(d);
					
				 System.out.println(newDate);
				
				return newDate;
		 
	 }
	 
	 public  String getYear(String date)
	 { 
		 			// Given Date in String format
					//String oldDate = "2017-01-29";
				 	
					//Specifying date format that matches the given date
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
				SimpleDateFormat sdfReq = new SimpleDateFormat("yyyy");
					
					Date d=null;
					try {
						d=sdf.parse(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					String newDate=sdfReq.format(d);
					
				 System.out.println(newDate);
				
				return newDate;
		 
	 }
	 
	 
	 public  int  getDayOfYear(String date)
	 {
		// Given Date in String format
			//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			}catch(ParseException e){
				e.printStackTrace();
			}
			   
			System.out.println(c.get(Calendar.DAY_OF_YEAR));
			
			int dayOfYear=c.get(Calendar.DAY_OF_YEAR);
			
			return dayOfYear;
		 
		 
	 }
	 
	 public  int  getWeekOfYear(String date)
	 {
		// Given Date in String format
			//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			}catch(ParseException e){
				e.printStackTrace();
			}
			   
			System.out.println(c.get(Calendar.WEEK_OF_YEAR));
			
			int dayOfYear=c.get(Calendar.WEEK_OF_YEAR);
			
			return dayOfYear;
		 
		 
	 }
	 
	 
	 public  String  subDay(String date,int noOfDays)
	 {
		 
		 	// Given Date in String format
			//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			}catch(ParseException e){
				e.printStackTrace();
			 }
			
			// Number of Days to add
			c.add(Calendar.DAY_OF_MONTH, -noOfDays);  
			//Date after adding the days to the given date
			String newDate = sdf.format(c.getTime());  
			//Displaying the new Date after addition of Days
			System.out.println("Date after Addition: "+newDate);
			
			return newDate;
		 
	 }
	 
	 public  String  subHours(String date,int noOfHours)
	 {
			// Given Date in String format
			//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			}catch(ParseException e){
				e.printStackTrace();
			}
			   
			// Number of Days to add
			c.add(Calendar.HOUR_OF_DAY, -noOfHours);  
			//Date after adding the days to the given date
			String newDate = sdf.format(c.getTime());  
			//Displaying the new Date after addition of Days
			System.out.println("Date after Addition: "+newDate);
			
			return newDate;
		 
	 }
	 
	 public  String  subMinutes(String date,int noOfMinutes)
	 {
			// Given Date in String format
			//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			}catch(ParseException e){
				e.printStackTrace();
			}
			   
			// Number of Days to add
			c.add(Calendar.MINUTE, -noOfMinutes);  
			//Date after adding the days to the given date
			String newDate = sdf.format(c.getTime());  
			//Displaying the new Date after addition of Days
			System.out.println("Date after Addition: "+newDate);
			
			return newDate;
		 
	 }
	 
	 
	 public  String  subMonths(String date,int noOfMonths)
	 {
		// Given Date in String format
					//String oldDate = "2017-01-29";
				 	
					//Specifying date format that matches the given date
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
					Calendar c = Calendar.getInstance();
					
					try{
					   //Setting the date to the given date
					   c.setTime(sdf.parse(date));
					}catch(ParseException e){
						e.printStackTrace();
					}
					   
					// Number of Days to add
					c.add(Calendar.MONTH, -noOfMonths);  
					//Date after adding the days to the given date
					String newDate = sdf.format(c.getTime());  
					//Displaying the new Date after addition of Days
					System.out.println("Date after Addition: "+newDate);
					
					return newDate;
		 
	 }
	 
	 
	 public  String  subSeconds(String date,int noOfSeconds)
	 {
		// Given Date in String format
			//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			}catch(ParseException e){
				e.printStackTrace();
			}
			   
			// Number of Days to add
			c.add(Calendar.SECOND, -noOfSeconds);  
			//Date after adding the days to the given date
			String newDate = sdf.format(c.getTime());  
			//Displaying the new Date after addition of Days
			System.out.println("Date after Addition: "+newDate);
			
			return newDate;
		 
		 
	 }
	 
	 public  String  subWeeks(String date,int noOfWeeks)
	 {
		// Given Date in String format
			//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			}catch(ParseException e){
				e.printStackTrace();
			}
			   
			// Number of Days to add
			c.add(Calendar.WEEK_OF_YEAR, -noOfWeeks);  
			//Date after adding the days to the given date
			String newDate = sdf.format(c.getTime());  
			//Displaying the new Date after addition of Days
			System.out.println("Date after Addition: "+newDate);
			
			return newDate;
		 
		 
	 }
	 
	 public  String  subYears(String date,int noOfYears)
	 {
		// Given Date in String format
			//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			
			try
			{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			   
			}catch(ParseException e)
			{
				e.printStackTrace();
			}
			   
			// Number of Days to add
			c.add(Calendar.YEAR, -noOfYears);  
			//Date after adding the days to the given date
			
			String newDate = sdf.format(c.getTime());  
			//Displaying the new Date after addition of Days
			System.out.println("Date after Addition: "+newDate);
			
			return newDate;
		 
	 }
	 
	 
	 public  String  firstDayWeek(String date)
	 {
		 
		 	// Given Date in String format
			//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			}catch(ParseException e){
				e.printStackTrace();
			 }
			
			
			 c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			
			String newDate = sdf.format(c.getTime());  
			//Displaying the new Date after addition of Days
			System.out.println("Date after Addition: "+newDate);
			
			return newDate;
		 
	 }
	 
	 public  String  lastDayWeek(String date)
	 {
		 
		 	// Given Date in String format
			//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			}catch(ParseException e){
				e.printStackTrace();
			 }
			
			
			 c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			 
			 for (int i = 0; i <6; i++) 
			 {
		         c.add(Calendar.DATE, 1);
		     }
			 
			
			String newDate = sdf.format(c.getTime());  
			//Displaying the new Date after addition of Days
			System.out.println("Date after Addition: "+newDate);
			
			return newDate;
		 
	 }
	 
	 public   int  getWeekDay(String date)
	 {
		// Given Date in String format
		//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(date));
			}catch(ParseException e){
				e.printStackTrace();
			}
			   
			System.out.println("weeek-->"+c.get(Calendar.DAY_OF_WEEK));
			
			int dayOfYear=c.get(Calendar.DAY_OF_WEEK);
			
			return dayOfYear;
		 
		 
	 }
	 
	 
	 public   int  hoursBetween(String dateOne,String dateTwo)
	 {
		// Given Date in String format
		//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(dateOne));
			   c2.setTime(sdf.parse(dateTwo));
			}catch(ParseException e){
				e.printStackTrace();
			}
			   
			System.out.println("weeek-->"+(c.get(Calendar.HOUR)-c2.get(Calendar.HOUR)));
			
			int hours=c.get(Calendar.HOUR)-c2.get(Calendar.HOUR);
			
			return hours;
		 
		 
	 }
	 
	 public   int  monthsBetween(String dateOne,String dateTwo)
	 {
		// Given Date in String format
		//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(dateOne));
			   c2.setTime(sdf.parse(dateTwo));
			}catch(ParseException e){
				e.printStackTrace();
			}
			   
			System.out.println("weeek-->"+(c.get(Calendar.MONTH)-c2.get(Calendar.MONTH)));
			
			int months=c.get(Calendar.MONTH)-c2.get(Calendar.MONTH);
			
			return months;
		 
		 
	 }
	 
	 
	 public   int  yearsBetween(String dateOne,String dateTwo)
	 {
		// Given Date in String format
		//String oldDate = "2017-01-29";
		 	
			//Specifying date format that matches the given date
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			Calendar c = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			
			try{
			   //Setting the date to the given date
			   c.setTime(sdf.parse(dateOne));
			   c2.setTime(sdf.parse(dateTwo));
			}catch(ParseException e)
			{
				e.printStackTrace();
			}
			   
			System.out.println("weeek-->"+(c.get(Calendar.YEAR)-c2.get(Calendar.YEAR)));
			
			int years=c.get(Calendar.YEAR)-c2.get(Calendar.YEAR);
			
			return years;
		 
	 }
	 
	 public   boolean  isDateValid(String dateToValidate, String dateFromat)
	 {
		 
		 if(dateToValidate == null){
				return false;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
			sdf.setLenient(false);
			
			try {
				
				//if not valid, it will throw ParseException
				Date date = sdf.parse(dateToValidate);
				System.out.println(date);
			
			} catch (ParseException e) {
				
				e.printStackTrace();
				return false;
			}
			
			return true;
		 
	 }
	 
	 public static void main(String args[])
	 { 
		 String date = "2-03-2020 12:23:233";
		 String date2 = "2-03-2020 12:23:23";
		 String format = "dd-MM-yyyy hh:mm:ss";
		 
		 int businessDays=13;
		 int noOfHours=2;
		 int noOfMinutes=2;
		 int noOfMonths=4;
		 int noOfSeconds=4;
		 int noOfWeeks=5;
		 int noOfYears=3;
		 
		// addBusinessDay(date,businessDays);
		// addHours(date,noOfHours);
		// addMinutes(date,noOfMinutes);
		 //addMonths(date,noOfMonths);
		// addSeconds(date,noOfSeconds); 
		 // addWeeks(date,noOfWeeks);
		 //addYears(date,noOfYears);
		 //getDay(date);
		//getHour(date);
		//getDayOfYear(date);
		 
		// test();
		 
		// lastDayWeek(date);
		 
		 
		 //getWeekDay(date);
		// monthsBetween(date,date2);
		 
		 
		// System.out.println(isDateValid(date,format));
	 }

	public   boolean  isDateGreaterthan(String date1,String date2)
	{
		boolean returnflag=false;
		try{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date d1 = sdf.parse(date1);
		Date d2 = sdf.parse(date2);

		if(d1.after(d2)){
			returnflag=true;
		}
		}
		catch(ParseException ex){
			ex.printStackTrace();
		}

		return returnflag;

	}

	public   boolean  isDateLessthan(String date1,String date2)
	{
		boolean returnflag=false;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date d1 = sdf.parse(date1);
			Date d2 = sdf.parse(date2);

			if(d1.before(d2)){
				returnflag=true;
			}
		}
		catch(ParseException ex){
			ex.printStackTrace();
		}

		return returnflag;

	}

	public   String  daysBetween(String inputDate1,String inputDate2)
	{
		String noofdays="0";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date d1 = sdf.parse(inputDate1);
			Date d2 = sdf.parse(inputDate2);

			// getting milliseconds for both dates
			long date1InMs = d1.getTime();
			long date2InMs = d2.getTime();

			// getting the diff between two dates.
			long timeDiff = 0;
			if(date1InMs > date2InMs) {
				timeDiff = date1InMs - date2InMs;
			} else {
				timeDiff = date2InMs - date1InMs;
			}

			// converting diff into days
			int daysDiff = (int) (timeDiff / (1000 * 60 * 60* 24));
			noofdays= String.valueOf(daysDiff);

			// print diff in days
			System.out.println("No of days diff is : "+daysDiff);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return noofdays;

	}



	
}
