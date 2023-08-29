package com.bhargo.user.Expression.Interfaces;

public interface TimeFunctions
{

	public String CurrentTimeIn12HrFormate();
	public String CurrentTimeIn24HrFormate();
	public String getTimeIn12HrFormate(String TimeStamp);
	public String getTimeIn24HrFormate(String TimeStamp);
	public String getTimeDifference(String TimeStamp1,String TimeStamp2);
	public String getNextTime(String TimeStamp1,String AddTime);
	public String getPreviousTime(String TimeStamp1,String SubtractTime);
	public boolean IsGreaterThanBaseTime(String BaseTime,String TimeStamp);
	public String getTimeDuration(String TimeStamp1,String TimeStamp2,String DurationFormatHrorMinorSec);

}
