package com.bhargo.user.Expression.Functions;

import com.bhargo.user.Expression.Interfaces.ArithmeticFunctions;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class ArithmeticFunctionsImpl implements ArithmeticFunctions
{

	 public double getSummation(List<String> list)
	 {
		 double sum=0;
		 
		 for(String values:list)
		 {
			 if(values.length()>0) {
				 sum += Double.parseDouble(values);
			 }
		 }
		 		 
		 return sum;
	 }
	 
	 public double getMultiplication(List<String> list)
	 {
		 double mul=1.0;
		 
		 for(String values:list)
		 {
		 	if(values.length()>0) {
				mul *= Double.parseDouble(values);
			}
		 }
		 		 
		 return mul;
	 }
	 
	 
	 public double getDivision(double a,double b)
	 {
		 double div=0;
		 	 
		 div=(a/b);
		 
		 return div;
	 }
	 
	  public double getSubtraction(double a,double b)
	  {
		double div=0;
			 
		div=(a-b);
			 
		return div;
	  }
	 
	  public double getPercentage(double a,double b)
	  {
		  	double per=0;
			 
		  	per=(a/b)*100;
				 
			return per;
		  		  
	  }
	  
	 public double getPower(double a,double b)
	 {
		double power=0;
		
		power=Math.pow(a,b);
				
		return power;
	 }
	 
	 public double getNthRoot(double A,double N)
	 {
		 
		 // intially guessing a random number between 
	        // 0 and 9 
	        double xPre = Math.random() % 10; 
	      
	        // smaller eps, denotes more accuracy 
	        double eps = 0.001; 
	      
	        // initializing difference between two 
	        // roots by INT_MAX 
	        double delX = 2147483647; 
	      
	        // xK denotes current value of x 
	        double xK = 0.0; 
	      
	        // loop untill we reach desired accuracy 
	        while (delX > eps) 
	        { 
	            // calculating current value from previous 
	            // value by newton's method 
	            xK = ((N - 1.0) * xPre + 
	            (double)A / Math.pow(xPre, N - 1)) / (double)N; 
	            delX = Math.abs(xK - xPre); 
	            xPre = xK; 
	        } 
	      
	        return xK; 
	 }
	 
	 public double getAbsolute(double a)
	 {
		 double abs=0;
			
		 abs=Math.abs(a);
					
		return abs;
		
		 
	 }
	 
	 
	 public double getFloor(double a)
	 {
		 double floor=0;
			
		 floor=Math.floor(a);
					
		return floor;
		
		 
	 }
	 
	 public double getRound(double a)
	 {
		 double round=0;
			
		 round=Math.round(a);
					
		return round;
		
		 
	 }
	 
	 public double getCeil(double a)
	 {
		 double ceil=0;
			
		 ceil=Math.ceil(a); 
					
		return ceil;
		
		 
	 }
	public int getRandom()
	{
		int random=0;

		// random= Math.random();

		Random objGenerator = new Random();

		int randomNumber = objGenerator.nextInt(10000);

		return randomNumber;


	}

	public double getRandomRange(double min, double max)
	{

		String  roudDecmalPoints="0.00";

		final DecimalFormat df = new DecimalFormat(roudDecmalPoints);


		if (min > max || (max - min + 1 > Double.MAX_VALUE)) {
			throw new IllegalArgumentException("Invalid range");
		}

		/* return new Random().nextInt(max - min + 1) + min;*/



		/* if (min >= max) {
	            throw new IllegalArgumentException("max must be greater than min");
	        }

	        return (double)(Math.random() * ((max - min) + 1.0)) + min;*/

		Random r = new Random();

		double result= min + r.nextDouble() * (max - min);

		// System.out.println("salary : " + df.format(result));



		return Double.valueOf(df.format(result));


	}

}
