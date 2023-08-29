package com.bhargo.user.Expression;

import java.util.ArrayList;
import java.util.List;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;
import net.objecthunter.exp4j.operator.Operator;

public class OperatorsBuilder 
{
	
 public static String bulidOperators(String relatonalFormula)
 {
 	relatonalFormula = relatonalFormula.replace("#","1.0");
	 relatonalFormula = relatonalFormula.replace("~","0.0");
	 String booleanResult="";
	 
	 Operator gteq = new Operator(">=",2, true, Operator.PRECEDENCE_ADDITION - 1) 
	 {
	        @Override
	        public double apply(double[] values) 
	        {
	            if(values[0] >= values[1]) 
	            {
	                return 1d;
	                
	            } else 
	            {
	                return 0d;
	            }
	        }
	  };
	  
	  Operator gt = new Operator(">",2, true, Operator.PRECEDENCE_ADDITION - 1) 
	  {

		        @Override
		        public double apply(double[] values) {
		            if (values[0] > values[1]) {
		                return 1d;
		            } else {
		                return 0d;
		            }
		        }
	  };
	    
	   Operator lteq = new Operator("<=",2, true, Operator.PRECEDENCE_ADDITION - 1) 
	   {
	        @Override
	        public double apply(double[] values) 
	        {
	            if (values[0] <= values[1]) 
	            {
	                return 1d;
	                
	            } else 
	            {
	                return 0d;
	            }
	        }
	    };
	    
	   Operator lt = new Operator("<",2, true, Operator.PRECEDENCE_ADDITION - 1) 
	   {

	        @Override
	        public double apply(double[] values) 
	        {
	            if(values[0] < values[1]) 
	            {
	                return 1d;
	                
	            }else 
	            {
	                return 0d;
	            }
	        }
	    };
	    
	    Operator eq = new Operator("==",2, true, Operator.PRECEDENCE_ADDITION - 1) 
	    {

	        @Override
	        public double apply(double[] values) 
	        {
	            if(values[0] == values[1]) 
	            {
	                return 1d;
	                
	            }else 
	            {
	                return 0d;
	            }
	        }
	    };
	    
	    
	    Operator neq = new Operator("!=",2, true, Operator.PRECEDENCE_ADDITION - 1) 
	    {
	        @Override
	        public double apply(double[] values) 
	        {
	            if(values[0] != values[1]) 
	            {
	               return 1d;
	                
	            } else 
	            {
	               return 0d;
	            }
	        }
	    };
	    
	    
	    Operator logicAnd = new Operator("&&",2, true, Operator.PRECEDENCE_ADDITION - 1) 
	    {
	        @Override
	        public double apply(double[] values) 
	        {
	           double result=0d;
	           
	        	if(values[0]==1 && values[1]==1)
	        	{
	        		result= 1d;	
	        	   
	        	}else if(values[0]==1 && values[1]==0)
	        	{
	        		result= 0d;	
		           
		        }else if(values[0]==0 && values[1]==1)
	        	{
		        	result= 0d;
			           
			    }else if(values[0]==0 && values[1]==0)
	        	{
			    	result= 0d;
			    }
	        	 
	            return result;
	        }
	    };
	    
	    
	    Operator logicOr = new Operator("||",2, true, Operator.PRECEDENCE_ADDITION - 1) 
	    {
	        @Override
	        public double apply(double[] values) 
	        {
	           double result=0d;
	           
	        	if(values[0]==1 || values[1]==1)
	        	{
	        		result= 1d;	
	        	   
	        	}else if(values[0]==1 || values[1]==0)
	        	{
	        		result= 1d;	
		           
		        }else if(values[0]==0 || values[1]==1)
	        	{
		        	result= 1d;
			           
			    }else if(values[0]==0 || values[1]==0)
	        	{
			    	result= 0d;
			    }
	        	 
	            return result;
	        }
	    };
	    
	    
	    Operator logicNot = new Operator("!",1, true, Operator.PRECEDENCE_ADDITION - 1) 
	    {
	        @Override
	        public double apply(double[] values) 
	        {
	        	double result=0d;
	        	 
	            if(values[0] ==1) 
	            {
	            	result= 0d;
	              
	            } 
	            
	            if(values[0] ==0) 
	            {
	            	result= 1d;
	                
	            } 
	            
	            return result;
	        }
	    };

	 Operator logicContains = new Operator("^",1, true, Operator.PRECEDENCE_ADDITION - 1)
	 {
		 @Override
		 public double apply(double[] values)
		 {

			 if(values[0] < values[1])
			 {
				 return 1d;

			 }else
			 {
				 return 0d;
			 }

		 }
	 };
	    
	    
	    List<Operator> operatorsList=new ArrayList<Operator>();
		
		operatorsList.add(gteq);
		operatorsList.add(lteq);
		operatorsList.add(gt);
		operatorsList.add(lt);
		operatorsList.add(eq);
		operatorsList.add(neq);
		operatorsList.add(logicAnd);
		operatorsList.add(logicOr);
		operatorsList.add(logicNot);
	 	operatorsList.add(logicContains);
	    
	    Expression e = new ExpressionBuilder(relatonalFormula)
			    		.operator(operatorsList)
			            .build();
	  
	  ValidationResult res = e.validate();
	  
	  System.out.println("validation-->"+res.isValid());
	  
	  if(res.isValid())
      {
		  
      }else
      {
    	  
      }
	 
	  booleanResult= String.valueOf(e.evaluate());
	
	  System.out.println("result--->"+ booleanResult);
	  
	  System.out.println("boolean result--->"+ booleanResult.substring(0,1));
	 
	  return booleanResult;
 }
	
	public static void main(String args[])
	{	
	    // String relatonalFormula="12*12+500 < 23";
		
		 String relatonalFormula="!(1==2 && 2==3 && 4==5)";
	    
		 bulidOperators(relatonalFormula);
		
	}

}

