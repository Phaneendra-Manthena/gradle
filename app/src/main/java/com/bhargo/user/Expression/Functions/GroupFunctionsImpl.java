package com.bhargo.user.Expression.Functions;

import com.bhargo.user.Expression.Interfaces.GroupFunctions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupFunctionsImpl  implements GroupFunctions
{

	public  double getMaximum(List<String> list)
	{
			String max;
		 
			max=Collections.max(list);
		 		 
		 return Double.parseDouble(max);
		
	}
	
	public  double getMinimum(List<String> list)
	{
		String min;
		 
		min=Collections.min(list);
		
		return Double.parseDouble(min);
			
	}
	
	public  double getCount(List<String> list)
	{
		double cnt;
		 
		cnt=list.size();
		
		return cnt;
					
	}
	
	public  double getVariance(List<String> list)
	{
		double variance=0;
		 
		double mean=getMean(list);
		
		double sqDiff = 0; 
                
        for(String values:list)
		{	 
        	sqDiff+= (Double.parseDouble(values)-mean)*(Double.parseDouble(values)-mean);
		}
        
        variance=sqDiff/list.size();
		
		return variance;
		
	}
	
	public double getMean(List<String> list)
	{
		 double sum=0;
		 
		 for(String values:list)
		 {			 
			 sum+=Double.parseDouble(values);
		 }
		
        double mean = 0;
        mean = sum / (list.size() * 1.0);
        return mean;
    }
	
    public double getMedian(List<String> list)
    {
        int middle = list.size()/2;
 
        if (list.size() % 2 == 1) {
        	
            return  Double.parseDouble(list.get(middle));
        } else {
        	
           return (Double.parseDouble(list.get(middle-1)) + Double.parseDouble(list.get(middle))) / 2.0;
        }
    }
	
    public  double getAverage(List<String> list)
    {
    	double sum=0;
		 
		 for(String values:list)
		 {			 
			 sum+=Double.parseDouble(values);
		 }
		
        double mean = 0;
        mean = sum / (list.size() * 1.0);
        return mean;
    	
    }




    //sizeOf(byte.class)
    
    public int getSizeOf(Class dataType)
	{
		
		if (dataType == null) { throw new NullPointerException(); } 
		if (dataType == byte.class || dataType == Byte.class) { return Byte.SIZE; } 
		if (dataType == short.class || dataType == Short.class) { return Short.SIZE; } 
		if (dataType == char.class || dataType == Character.class) { return Character.SIZE; } 
		if (dataType == int.class || dataType == Integer.class) { return Integer.SIZE; } 
		if (dataType == long.class || dataType == Long.class) { return Long.SIZE; } 
		if (dataType == float.class || dataType == Float.class) { return Float.SIZE; } 
		if (dataType == double.class || dataType == Double.class) { return Double.SIZE; } return 4; // default for 32-bit memory pointer }
	}

	public  int getLengthOf(List<String> list)
	{
		return list.size();
	}

	public boolean ToArray(List<String> list,String value)
	{
		boolean returnflag=false;

		return list.contains(value);
	}

	public String addItem(List<String> list,String value)
	{
		String finalValue="";
		list.add(value);

		finalValue = String.join(",", list);
		return finalValue;
	}

	public String removeItem(List<String> list,String value)
	{
		String finalValue="";
		list.remove(value);

		finalValue = String.join(",", list);
		return finalValue;
	}

	public String addItemAt(List<String> list,String value,int pos)
	{
		String finalValue="";
		List<String> newlist=new ArrayList<>();
		for (int i = 0; i <list.size() ; i++) {
			if(i!=pos){
				newlist.add(list.get(i));
			}else{
				newlist.add(value);
				newlist.add(list.get(i));
			}
		}

		finalValue = String.join(",", newlist);
		return finalValue;
	}

	public String removeItemAt(List<String> list,int pos)
	{
		String finalValue="";
		List<String> newlist=new ArrayList<>();
		for (int i = 0; i <list.size() ; i++) {
			if(i!=pos){
				newlist.add(list.get(i));
			}
		}

		finalValue = String.join(",", newlist);
		return finalValue;
	}

	public boolean contains(List<String> list,String value)
	{
		boolean returnflag=false;

		return list.contains(value);
	}

	public String concatenate(List<String> list1,List<String> list2)
	{
		List<String> newlist=new ArrayList<>();
		newlist.addAll(list1);
		newlist.addAll(list2);

		return String.join(",", newlist);
	}

	public boolean comparison(List<String> list1,List<String> list2)
	{
		boolean returnflag=true;
		if(list1.size()!=list2.size()){
			returnflag=false;
		}else {
			for (int i = 0; i < list1.size(); i++) {
				if(!list1.get(i).equalsIgnoreCase(list2.get(i))){
					returnflag=false;
					break;
				}
			}
		}

		return returnflag;
	}
}
