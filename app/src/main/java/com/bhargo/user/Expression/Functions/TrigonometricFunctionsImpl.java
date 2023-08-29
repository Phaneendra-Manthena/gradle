package com.bhargo.user.Expression.Functions;

import com.bhargo.user.Expression.Interfaces.TrigonometricFunctions;

public class TrigonometricFunctionsImpl implements TrigonometricFunctions
{

	public double getSin(double a)
	{
		double sin=0;
		
		sin=Math.sin(a);
				
		return sin;
	}
	
	public double getCos(double a)
	{
		double cos=0;
		
		cos=Math.cos(a);
				
		return cos;
	}
	
	public double getTan(double a)
	{
		double tan=0;
		
		tan=Math.tan(a);
				
		return tan;
	}
	
	public double getArcSin(double a)
	{
		double sin=0;
		
		sin=Math.asin(a);
				
		return sin;
	}
	
	public double getArcCos(double a)
	{
		double cos=0;
		
		cos=Math.acos(a);
				
		return cos;
	}
	
	public double getArcTan(double a)
	{
		double tan=0;
		
		tan=Math.atan(a);
				
		return tan;
	}
	
	public double getSinHyp(double a)
	{
		double sin=0;
		
		sin=Math.sinh(a);
				
		return sin;
	}
	
	public double getCosHyp(double a)
	{
		double cos=0;
		
		cos=Math.cosh(a);
				
		return cos;
	}
	
	public double getTanHyp(double a)
	{
		double tan=0;
		
		tan=Math.tanh(a);
				
		return tan;
	}

	public double getLog(double a)
	{
		double log=0;
		
		log=Math.log(a);
				
		return log;
	}

	public double getLog10(double a)
	{
		double log10=0;
		
		log10=Math.log10(a);
				
		return log10;
	}
	
}



