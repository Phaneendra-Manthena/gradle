package com.bhargo.user.Expression.Interfaces;

import java.util.List;

public interface CustomFunctions
{

	public String getValues(String ReturnColumnName,String SearchColumnName,String SearchValue,String SearchOperators);
	public String getListOfValuesByConditionEqual(String ValuesFromColumnName,String SearchColumnName,String SearchValue);

	public String getListOfValuesByLessthanCondition(String ValuesFromColumnName,String SearchColumnName,String SearchValue);
	public String getListOfValuesByGreaterthanCondition(String ValuesFromColumnName,String SearchColumnName,String SearchValue);
	public String getListOfValuesByLessthanEqualCondition(String ValuesFromColumnName,String SearchColumnName,String SearchValue);
	public String getListOfValuesByGreaterthanEqualCondition(String ValuesFromColumnName,String SearchColumnName,String SearchValue);
	public String getListOfValuesByInBetweenCondition(String ValuesFromColumnName,String SearchColumnName,String SearchValueFrom,String SearchValueTo);

	public String getListOfNamesByPositions(String SearchColumnName,int Positions);

	public List<Integer> getPostions(String SearchColumnName, String SearchValue,String SearchOperator);

	public String getValueByPositions(String ReturnColumnName,List<String> list_Postions);


}
