package com.bhargo.user.utils;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ExpressionBuiler {

	public static double EvalExpOne(String formula)
	{
		
		//String formula="3 * sin(300) - 2 / (200 - 2)";
		
		// formula="3 * 100 - 2 / (200 - 2)";
		
		Expression e = new ExpressionBuilder(formula)
//	    .variables("x", "y")
	    .build();
	   // .setVariable("x", 200)
	    //.setVariable("y", 300);
		
		double result = e.evaluate();

	System.out.println("result-->"+result);

		return result;
	}
	

public static void main(String args[])
{	
	
	EvalExpOne("400 *  2 / (200 - 2)");
	
}

	
}
