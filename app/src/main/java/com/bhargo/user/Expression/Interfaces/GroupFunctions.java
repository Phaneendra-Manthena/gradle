package com.bhargo.user.Expression.Interfaces;

import java.util.List;

public interface GroupFunctions 
{

	public  double getMaximum(List<String> list);
	public  double getMinimum(List<String> list);
	public  double getCount(List<String> list);
	public  double getVariance(List<String> list);
	public  double getMean(List<String> list);
	public  double getMedian(List<String> list);
	public  double getAverage(List<String> list);
	public  int getSizeOf(Class dataType);
	public  int getLengthOf(List<String> list);
	public  boolean ToArray(List<String> list,String value);
	public  String addItem(List<String> list,String value);
	public  String removeItem(List<String> list,String value);
	public  String addItemAt(List<String> list,String value,int pos);
	public  String removeItemAt(List<String> list,int pos);
	public  boolean contains(List<String> list,String value);
	public  String concatenate(List<String> list1,List<String> list2);
	public  boolean comparison(List<String> list1,List<String> list2);
}


