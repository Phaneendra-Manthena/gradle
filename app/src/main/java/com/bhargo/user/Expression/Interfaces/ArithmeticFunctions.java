package com.bhargo.user.Expression.Interfaces;

import java.util.List;

public interface ArithmeticFunctions 
{

  public  double getSummation(List<String> list);	
  public double getMultiplication(List<String> list);	
  public double getDivision(double a, double b);
  public double getSubtraction(double a, double b);
  public double getPercentage(double a, double b);
  public double getPower(double a, double b);
  public double getNthRoot(double a, double b);
  public double getAbsolute(double a);
  public double getFloor(double a);
  public double getRound(double a);
  public double getCeil(double a);
    
  
}

