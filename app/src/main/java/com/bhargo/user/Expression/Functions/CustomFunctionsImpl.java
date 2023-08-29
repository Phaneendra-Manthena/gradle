package com.bhargo.user.Expression.Functions;

import android.content.Context;
import android.util.Log;

import com.bhargo.user.Expression.Interfaces.CustomFunctions;
import com.bhargo.user.Java_Beans.SearchItems;
import com.bhargo.user.realm.RealmDBHelper;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomFunctionsImpl implements CustomFunctions {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public String getValues(String ReturnColumnName, String SearchColumnName, String SearchOperators, String SearchValue) {
        String finalValue = "";
        List<String> list_Values = new ArrayList<String>();
        try {

            if (SearchValue.contains("\"")) {
                SearchValue = SearchValue.replace("\"", "");
            }

            boolean resultSetSame = false;
            if (ReturnColumnName.startsWith("(im:getdata") && SearchColumnName.startsWith("(im:getdata")) {
                String returnColTableName = ReturnColumnName.split("\\.")[1];
                String searchColTableName = SearchColumnName.split("\\.")[1];
                if (returnColTableName.equals(searchColTableName)) {
                    resultSetSame = true;
                }
            }else if (ReturnColumnName.startsWith("(im:api") && SearchColumnName.startsWith("(im:api")) {
                String returnColTableName = ReturnColumnName.split("\\.")[1];
                String searchColTableName = SearchColumnName.split("\\.")[1];
                if (returnColTableName.equals(searchColTableName)) {
                    resultSetSame = true;
                }
            }
            if (resultSetSame) {
                finalValue= getValuesFromResultSet(ReturnColumnName,SearchColumnName,SearchOperators,SearchValue);
            } else {
                List<Integer> list_Postions = getPostions(SearchColumnName, SearchValue, SearchOperators, "");
                System.out.println("list_Postions===" + list_Postions.size());
                System.out.println("list_Postions==" + list_Postions);

                if (list_Postions.size() > 0) {
                    list_Values = getListOfValuesByUsingListOfPostions(ReturnColumnName, list_Postions);
                    finalValue = String.join(",", list_Values);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception at getValues==" + e);
        }
        return finalValue;

    }

    private String getValuesFromResultSet(String ReturnColumnName, String SearchColumnName, String SearchOperators, String SearchValue) {
        List<String> valueList = new ArrayList<String>();
        if (ReturnColumnName.startsWith("(im:getdata") && SearchColumnName.startsWith("(im:getdata")) {
            String tableName_returnCol = ReturnColumnName.split("\\.")[1];
            String conditionValue_returnCol = ReturnColumnName.split("\\.")[2];
            conditionValue_returnCol = conditionValue_returnCol.substring(0, conditionValue_returnCol.indexOf(")"));

            String tableName_searchCol = SearchColumnName.split("\\.")[1];
            String conditionValue_searchCol = SearchColumnName.split("\\.")[2];
            conditionValue_searchCol = conditionValue_searchCol.substring(0, conditionValue_searchCol.indexOf(")"));
            //Context context, String tableName, String returnCol,String condition,String whereColName,String whereColValue,String whereColValueTo
            valueList = RealmDBHelper.getSingleColDataInList(context, tableName_returnCol, conditionValue_returnCol,SearchOperators,conditionValue_searchCol,SearchValue,"");

        }else if (ReturnColumnName.startsWith("(im:api") && SearchColumnName.startsWith("(im:api")) {
            String tableName_returnCol = ReturnColumnName.split("\\.")[1];
            String conditionValue_returnCol = ReturnColumnName.split("\\.")[2];
            conditionValue_returnCol = conditionValue_returnCol.substring(0, conditionValue_returnCol.indexOf(")"));
            List<String>outputPaths=RealmDBHelper.getDataInRealResults(context,tableName_returnCol+"_OutputPaths","Path",
                    new String[]{"KeyName"},new String[]{conditionValue_returnCol});


            String tableName_searchCol = SearchColumnName.split("\\.")[1];
            String conditionValue_searchCol = SearchColumnName.split("\\.")[2];
            conditionValue_searchCol = conditionValue_searchCol.substring(0, conditionValue_searchCol.indexOf(")"));
            List<String>outputPaths1=RealmDBHelper.getDataInRealResults(context,tableName_searchCol+"_OutputPaths","Path",
                    new String[]{"KeyName"},new String[]{conditionValue_searchCol});

            if(outputPaths.size()>0 && outputPaths1.size()>0){
                List<String> tableNames=new ArrayList<>();

                String outputTemp=outputPaths.get(0);
                String temp = outputTemp.substring(0, outputTemp.lastIndexOf("/")).replace("/", "_");
                String return_tableName = tableName_returnCol.substring(0, 9) + "_" + temp;
                tableNames.add(return_tableName);
                String return_colName = outputTemp.substring(outputTemp.lastIndexOf("/") + 1);
                return_colName = return_colName.startsWith("@") ? return_colName.substring(1) : return_colName;
                //
                outputTemp=outputPaths1.get(0);
                temp = outputTemp.substring(0, outputTemp.lastIndexOf("/")).replace("/", "_");
                String search_tableName = tableName_searchCol.substring(0, 9) + "_" + temp;
                tableNames.add(search_tableName);
                String search_colName = outputTemp.substring(outputTemp.lastIndexOf("/") + 1);
                search_colName = search_colName.startsWith("@") ? search_colName.substring(1) : search_colName;
                String tableName=getTableName(tableNames);
                valueList = RealmDBHelper.getSingleColDataInList(context,
                        tableName, return_colName,SearchOperators,search_colName,SearchValue,"");

            }
        }



        return String.join(",", valueList);
    }



    private String getTableName(List<String> l_tableNames) {
        String tableName = l_tableNames.get(0);
        for (int i = 1; i < l_tableNames.size(); i++) {
            if (l_tableNames.get(i).contains(tableName)) {
                tableName = l_tableNames.get(i);
            }
        }

        return tableName;

    }

    // While Mering process
    public String getColumnDataFromTable(String tableName, String columnName, String filters) {
        String value = "";
        ImproveDataBase improveDataBase = new ImproveDataBase(context);
        String exactTableName = improveDataBase.getExactTableName(tableName);
        value = improveDataBase.getColumnData(exactTableName, columnName, filters);
        return value;
    }

    public String getListOfValuesByConditionEqual(String ValuesFromColumnName, String SearchColumnName, String SearchValue) {
        String finalValue = "";
        List<String> list_Values = new ArrayList<String>();
        try {

            SearchValue = SearchValue.replace("\"", "");
            List<Integer> list_Postions = getPostions(SearchColumnName, SearchValue, "Equal", "");

            if (list_Postions.size() > 0) {
                list_Values = getListOfValuesByUsingListOfPostions(ValuesFromColumnName, list_Postions);
                finalValue = String.join(",", list_Values);
            }

        } catch (Exception e) {
            System.out.println("Exception at getListOfValuesByConditionEqual==" + e);
        }
        return finalValue;

    }

    public List<Integer> getPostions(String SearchColumnName, String SearchOperator, String SearchValue) {
        List<String> valueList = new ArrayList<String>();
        List<Integer> List_pos = new ArrayList<Integer>();
        String ValueType = "", ValueStr = "", ConditionValue = "", value = "";
        SearchValue = ImproveHelper.getValueFromGlobalObject(context, SearchValue);
        try {
            ValueStr = SearchColumnName;
            if (ValueStr.startsWith("(im:")) {
                ValueType = ValueStr.substring(4, ValueStr.indexOf("."));
                if (ValueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
                    String SubformName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
//                    LinkedHashMap<String, List<String>> map_SubformData = AppConstants.GlobalObjects.getSubControls_List().get(SubformName);


//                    valueList = map_SubformData.get(ConditionValue);
                    valueList = ImproveHelper.getListOfValuesFromGlobalObject(context, SearchColumnName);
                    for (int i = 0; i < valueList.size(); i++) {
                        switch (SearchOperator) {
//                            case "EQUALSTO":
                            case "equals":
                                if (valueList.get(i).equalsIgnoreCase(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "NOTEQUALSTO":
                                if (!valueList.get(i).equalsIgnoreCase(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "LESSTHAN":
                                if (Double.parseDouble(valueList.get(i)) < Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "GREATERTHAN":
                                if (Double.parseDouble(valueList.get(i)) > Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "LESSTHANOREQUALSTO":
                                if (Double.parseDouble(valueList.get(i)) <= Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "GREATERTHANOREQUALSTO":
                                if (Double.parseDouble(valueList.get(i)) >= Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                        }

                    }


                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_API)) {
                    String APIName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    List<String>outputPaths=RealmDBHelper.getDataInRealResults(context,APIName+"_OutputPaths","Path",
                            new String[]{"KeyName"},new String[]{ConditionValue});
                    if(outputPaths.size()>0){

                        String outputTemp=outputPaths.get(0);
                        String temp = outputTemp.substring(0, outputTemp.lastIndexOf("/")).replace("/", "_");
                        String tableName = APIName.substring(0, 9) + "_" + temp;
                        String colName = outputTemp.substring(outputTemp.lastIndexOf("/") + 1);
                        colName = colName.startsWith("@") ? colName.substring(1) : colName;

                        valueList = RealmDBHelper.getSingleColDataInList(context, tableName, colName);

                    }

                    for (int i = 0; i < valueList.size(); i++) {
                        switch (SearchOperator) {
                            case "EQUALS":
                                if (valueList.get(i).equalsIgnoreCase(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "NOTEQUALS":
                                if (!valueList.get(i).equalsIgnoreCase(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "LESSTHAN":
                                if (Double.parseDouble(valueList.get(i)) < Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "GREATERTHAN":
                                if (Double.parseDouble(valueList.get(i)) > Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "LESSTHANOREQUALSTO":
                                if (Double.parseDouble(valueList.get(i)) <= Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "GREATERTHANOREQUALSTO":
                                if (Double.parseDouble(valueList.get(i)) >= Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                        }
                    }


                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_FormFields)) {
                    String FormName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    /*LinkedHashMap<String, List<String>> map_FormData = AppConstants.GlobalObjects.getForms_ListHash().get(FormName);
                    valueList = map_FormData.get(ConditionValue);*/
                    //nk realm
                    valueList = RealmDBHelper.getSingleColDataInList(context, FormName, ConditionValue);
                    for (int i = 0; i < valueList.size(); i++) {
                        switch (SearchOperator) {
                            case "EQUALSTO":
                                if (valueList.get(i).equalsIgnoreCase(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "NOTEQUALSTO":
                                if (!valueList.get(i).equalsIgnoreCase(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "LESSTHAN":
                                if (Double.parseDouble(valueList.get(i)) < Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "GREATERTHAN":
                                if (Double.parseDouble(valueList.get(i)) > Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "LESSTHANOREQUALSTO":
                                if (Double.parseDouble(valueList.get(i)) <= Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "GREATERTHANOREQUALSTO":
                                if (Double.parseDouble(valueList.get(i)) >= Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                        }
                    }

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_Query)) {
                    String QueryName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    //nk realm
                    valueList = RealmDBHelper.getSingleColDataInList(context, QueryName, ConditionValue);
                   /* LinkedHashMap<String, List<String>> map_QueryData = AppConstants.GlobalObjects.getQuerys_ListHash().get(QueryName);
                    valueList = map_QueryData.get(ConditionValue);*/
                    for (int i = 0; i < valueList.size(); i++) {
                        switch (SearchOperator) {
                            case "EQUALSTO":
                                if (valueList.get(i).equalsIgnoreCase(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "NOTEQUALSTO":
                                if (!valueList.get(i).equalsIgnoreCase(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "LESSTHAN":
                                if (Double.parseDouble(valueList.get(i)) < Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "GREATERTHAN":
                                if (Double.parseDouble(valueList.get(i)) > Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "LESSTHANOREQUALSTO":
                                if (Double.parseDouble(valueList.get(i)) <= Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "GREATERTHANOREQUALSTO":
                                if (Double.parseDouble(valueList.get(i)) >= Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                        }
                    }

                }else if (ValueType.equalsIgnoreCase(AppConstants.Global_GetData)) {
                    String FormName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    // valueList =RealmDBHelper.getSingleColDataInList(context,FormName,SearchOperator,ConditionValue,SearchValue);
                    valueList = RealmDBHelper.getSingleColDataInList(context, FormName, ConditionValue);
                    for (int i = 0; i < valueList.size(); i++) {
                        switch (SearchOperator.toUpperCase()) {
                            case "EQUALS":
                                if (valueList.get(i).equalsIgnoreCase(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "NOTEQUALS":
                                if (!valueList.get(i).equalsIgnoreCase(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "LESSTHAN":
                                if (Double.parseDouble(valueList.get(i)) < Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "GREATERTHAN":
                                if (Double.parseDouble(valueList.get(i)) > Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "LESSTHANOREQUALSTO":
                                if (Double.parseDouble(valueList.get(i)) <= Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                            case "GREATERTHANOREQUALSTO":
                                if (Double.parseDouble(valueList.get(i)) >= Double.parseDouble(SearchValue)) {
                                    List_pos.add(i);
                                }
                                break;
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("Exception at getPostions==" + e);
        }

        return List_pos;
    }


//    public String getListOfNamesByPositions(String SearchColumnName,int Positions){
//        List<String> valueList = new ArrayList<String>();
//        String ValueType="",ValueStr="",ConditionValue = "",value="";
//        try {
//            ValueStr=SearchColumnName;
//            if (ValueStr.startsWith("(im:")) {
//                ValueType = ValueStr.substring(4, ValueStr.indexOf("."));
//                if (ValueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
//                    String SubformName = ValueStr.split("\\.")[1];
//                    ConditionValue = ValueStr.split("\\.")[2];
//                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
//                    LinkedHashMap<String, List<String>> map_SubformData = AppConstants.GlobalObjects.getSubControls_List().get(SubformName);
//
//
//                    valueList = map_SubformData.get(ConditionValue);
//
//
//                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_API)) {
//                    String APIName = ValueStr.split("\\.")[1];
//                    ConditionValue = ValueStr.split("\\.")[2];
//                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
//                    LinkedHashMap<String, List<String>> map_APIData = AppConstants.GlobalObjects.getAPIs_ListHash().get(APIName);
//
//
//                    valueList = map_APIData.get(ConditionValue);
//
//
//                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_FormFields)) {
//                    String FormName = ValueStr.split("\\.")[1];
//                    ConditionValue = ValueStr.split("\\.")[2];
//                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
//                    LinkedHashMap<String, List<String>> map_FormData = AppConstants.GlobalObjects.getForms_ListHash().get(FormName);
//
//                    valueList = map_FormData.get(ConditionValue);
//
//                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_Query)) {
//                    String QueryName = ValueStr.split("\\.")[1];
//                    ConditionValue = ValueStr.split("\\.")[2];
//                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
//                    LinkedHashMap<String, List<String>> map_QueryData = AppConstants.GlobalObjects.getQuerys_ListHash().get(QueryName);
//
//                    valueList = map_QueryData.get(ConditionValue);
//
//                }
//            }
//
//            if(valueList.size()>=Positions) {
//                value = valueList.get(Positions);
//            }
//
//        }catch (Exception e){
//            System.out.println("Exception at getListOfNamesByPositions=="+e.toString());
//        }
//        return value;
//
//    }


    public List<Integer> getPostions(String SearchColumnName, String SearchValue, String Condition, String SearchValueTo) {
        List<Integer> list_postions = new ArrayList<Integer>();
        try {
            String ValueType = "", ValueStr = "", ConditionValue = "";
            List<String> valueList = new ArrayList<String>();

            ValueStr = SearchColumnName;
            if (ValueStr.startsWith("(im:")) {
                ValueType = ValueStr.substring(4, ValueStr.indexOf("."));
                if (ValueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
                    String SubformName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
//                LinkedHashMap<String, List<String>> map_SubformData = AppConstants.GlobalObjects.getSubControls_List().get(SubformName);


//                valueList = map_SubformData.get(ConditionValue);

                    valueList = ImproveHelper.getListOfValuesFromGlobalObject(context, SearchColumnName);


                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_API)) {
                    String APIName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    //nk realm
                    valueList = RealmDBHelper.getSingleColDataInList(context, APIName, ConditionValue);
                   /* LinkedHashMap<String, List<String>> map_APIData = AppConstants.GlobalObjects.getAPIs_ListHash().get(APIName);
                    valueList = map_APIData.get(ConditionValue);*/


                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_FormFields)) {
                    String FormName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));


                    /*LinkedHashMap<String, List<String>> map_FormData = AppConstants.GlobalObjects.getForms_ListHash().get(FormName);
                    valueList = map_FormData.get(ConditionValue);*/
                    //nk realm
                    valueList = RealmDBHelper.getSingleColDataInList(context, FormName, ConditionValue);
                    System.out.println("valueList===" + valueList.size());

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_Query)) {
                    String QueryName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));

                    valueList = RealmDBHelper.getSingleColDataInList(context, QueryName, ConditionValue);
                    //nk realm
                    /*  LinkedHashMap<String, List<String>> map_QueryData = AppConstants.GlobalObjects.getQuerys_ListHash().get(QueryName);
                    valueList = map_QueryData.get(ConditionValue);*/

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_RequestOfflineData)) {
                    String APIName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    //nk realm
                    valueList = RealmDBHelper.getSingleColDataInList(context, APIName, ConditionValue);
                   /* LinkedHashMap<String, List<String>> map_APIData = AppConstants.GlobalObjects.getAPIsRequestOfflineData_ListHash().get(APIName);
                    valueList = map_APIData.get(ConditionValue);*/
                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_DataControls)) {
                    String APIName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    valueList = RealmDBHelper.getSingleColDataInList(context, APIName, ConditionValue);
                    //nk realm
                    /*LinkedHashMap<String, List<String>> map_APIData = AppConstants.GlobalObjects.getDataControls_ListHash().get(APIName);
                    valueList = map_APIData.get(ConditionValue);*/
                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_OfflineTable)) {
                    String tableName = ValueStr.split("\\.")[1];
                    Log.e("FormNameInGlobal", tableName);
                    String columnName = ValueStr.split("\\.")[2];
                    columnName = columnName.substring(0, columnName.length() - 1);
//                    Log.e("ColumnName", columnName);
                    valueList = ImproveHelper.getValueListFromOfflineTable(context, tableName, columnName);

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_GetData)) {
                    String FormName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));

                    valueList = RealmDBHelper.getSingleColDataInList(context, FormName, ConditionValue);


                }

                SearchValue = ImproveHelper.getValueFromGlobalObject(context, SearchValue);

                List<SearchItems> searchItems = new ArrayList<>();

                for (int i = 0; i < valueList.size(); i++) {
                    if (Condition.equalsIgnoreCase(AppConstants.Conditions_Equals)) {

                        if (valueList.get(i).equalsIgnoreCase(SearchValue)) {
                            list_postions.add(i);
                        }
                    } else if (Condition.equalsIgnoreCase(AppConstants.Conditions_NotEquals)) {

                        if (!valueList.get(i).equalsIgnoreCase(SearchValue)) {
                            list_postions.add(i);
                        }
                    } else if (Condition.equalsIgnoreCase(AppConstants.Conditions_lessThan)) {
                        if (Double.parseDouble(valueList.get(i)) < Double.parseDouble(SearchValue)) {
                            list_postions.add(i);
                        }
                    } else if (Condition.equalsIgnoreCase(AppConstants.Conditions_GreaterThan)) {
                        if (Double.parseDouble(valueList.get(i)) > Double.parseDouble(SearchValue)) {
                            list_postions.add(i);
                        }
                    } else if (Condition.equalsIgnoreCase(AppConstants.Conditions_LessThanEqualsTo)) {
                        if (Double.parseDouble(valueList.get(i)) <= Double.parseDouble(SearchValue)) {
                            list_postions.add(i);
                        }
                    } else if (Condition.equalsIgnoreCase(AppConstants.Conditions_GreaterThanEqualsTo)) {
                        if (Double.parseDouble(valueList.get(i)) >= Double.parseDouble(SearchValue)) {
                            list_postions.add(i);
                        }
                    } else if (Condition.equalsIgnoreCase(AppConstants.Conditions_Contains)) {
                        if (valueList.get(i).toUpperCase().contains(SearchValue.toUpperCase())) {
                            list_postions.add(i);
                        }
                    } else if (Condition.equalsIgnoreCase(AppConstants.Conditions_StartsWith)) {
                        if (valueList.get(i).toUpperCase().startsWith(SearchValue.toUpperCase())) {
                            list_postions.add(i);
                        }
                    } else if (Condition.equalsIgnoreCase(AppConstants.Conditions_EndsWith)) {
                        if (valueList.get(i).toUpperCase().endsWith(SearchValue.toUpperCase())) {
                            list_postions.add(i);
                        }
                    } else if (Condition.equalsIgnoreCase(AppConstants.Conditions_IsNull)) {
                        if (valueList.get(i) == null) {
                            list_postions.add(i);
                        }
                    } else if (Condition.equalsIgnoreCase(AppConstants.Conditions_IsNotNull)) {
                        if (valueList.get(i) != null) {
                            list_postions.add(i);
                        }
                    } else if (Condition.equalsIgnoreCase("InBetween")) {
                        if (Double.parseDouble(valueList.get(i)) > Double.parseDouble(SearchValue) &&
                                Double.parseDouble(valueList.get(i)) < Double.parseDouble(SearchValueTo)) {
                            list_postions.add(i);
                        }
                    } else if (Condition.equalsIgnoreCase(AppConstants.Conditions_NearestStringMatch)) {

                        int per = ImproveHelper.lock_match(SearchValue.toLowerCase(), valueList.get(i).toLowerCase());
                        if (per > 0) {
                            searchItems.add(new SearchItems(per, valueList.get(i), i));
                        }
                    }
                }
                if (Condition.equalsIgnoreCase(AppConstants.Conditions_NearestStringMatch)) {
                    Collections.sort(searchItems);
                    if (searchItems.size() > 0) {
                        list_postions.add(searchItems.get(0).getPostions());
                    }
                }

            }

        } catch (Exception E) {
            System.out.println("Error at getpostions==" + E);
        }
        return list_postions;
    }


    public List<String> getListOfValuesByUsingListOfPostions(String ValuesFromColumnName, List<Integer> list_Postions) {
        List<String> list_Values = new ArrayList<String>();
        String ValueType = "", ValueStr = "", ConditionValue = "";
        List<String> valueList = new ArrayList<String>();

        ValueStr = ValuesFromColumnName;

        if (ValueStr.startsWith("(im:")) {
            ValueType = ValueStr.substring(4, ValueStr.indexOf("."));
            if (ValueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
                String SubformName = ValueStr.split("\\.")[1];
                ConditionValue = ValueStr.split("\\.")[2];
                ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                //LinkedHashMap<String, List<String>> map_SubformData = AppConstants.GlobalObjects.getSubControls_List().get(SubformName);


//                valueList = map_SubformData.get(ConditionValue);

                valueList = ImproveHelper.getListOfValuesFromGlobalObject(context, ValuesFromColumnName);

            } else if (ValueType.equalsIgnoreCase(AppConstants.Global_API)) {
                String APIName = ValueStr.split("\\.")[1];
                ConditionValue = ValueStr.split("\\.")[2];
                ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                //valueList = RealmDBHelper.getSingleColDataInList(context, APIName, ConditionValue);
                List<String>outputPaths=RealmDBHelper.getDataInRealResults(context,APIName+"_OutputPaths","Path",
                        new String[]{"KeyName"},new String[]{ConditionValue});
                if(outputPaths.size()>0){

                    String outputTemp=outputPaths.get(0);
                    String temp = outputTemp.substring(0, outputTemp.lastIndexOf("/")).replace("/", "_");
                    String tableName = APIName.substring(0, 9) + "_" + temp;
                    String colName = outputTemp.substring(outputTemp.lastIndexOf("/") + 1);
                    colName = colName.startsWith("@") ? colName.substring(1) : colName;

                    valueList = RealmDBHelper.getSingleColDataInList(context, tableName, colName);

                }

            } else if (ValueType.equalsIgnoreCase(AppConstants.Global_FormFields)) {
                String FormName = ValueStr.split("\\.")[1];
                ConditionValue = ValueStr.split("\\.")[2];
                ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                /*LinkedHashMap<String, List<String>> map_FormData = AppConstants.GlobalObjects.getForms_ListHash().get(FormName);
                valueList = map_FormData.get(ConditionValue);*/
                //nk realm
                valueList = RealmDBHelper.getSingleColDataInList(context, FormName, ConditionValue);

            } else if (ValueType.equalsIgnoreCase(AppConstants.Global_Query)) {
                String QueryName = ValueStr.split("\\.")[1];
                ConditionValue = ValueStr.split("\\.")[2];
                ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                //LinkedHashMap<String, List<String>> map_QueryData = AppConstants.GlobalObjects.getQuerys_ListHash().get(QueryName);
                //valueList = map_QueryData.get(ConditionValue);
                //nk realm
                valueList = RealmDBHelper.getSingleColDataInList(context, QueryName, ConditionValue);

            } else if (ValueType.equalsIgnoreCase(AppConstants.Global_RequestOfflineData)) {
                String APIName = ValueStr.split("\\.")[1];
                ConditionValue = ValueStr.split("\\.")[2];
                ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                //nk realm
                if (!ConditionValue.toLowerCase().endsWith("_allcolumns")) {
                    valueList = RealmDBHelper.getSingleColDataInList(context, APIName, ConditionValue);
                }
                //nk realm
                /*LinkedHashMap<String, List<String>> map_APIData = AppConstants.GlobalObjects.getAPIsRequestOfflineData_ListHash().get(APIName);
                if(!ConditionValue.toLowerCase().endsWith("_allcolumns")) {
                    valueList = map_APIData.get(ConditionValue);
                }*/

            } else if (ValueType.equalsIgnoreCase(AppConstants.Global_DataControls)) {
                String APIName = ValueStr.split("\\.")[1];
                ConditionValue = ValueStr.split("\\.")[2];
                ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));

                //nk realm
                if (!ConditionValue.toLowerCase().endsWith("_allcolumns")) {
                    valueList = RealmDBHelper.getSingleColDataInList(context, APIName, ConditionValue);
                }
                //nk realm
               /* LinkedHashMap<String, List<String>> map_APIData = AppConstants.GlobalObjects.getDataControls_ListHash().get(APIName);
                if (!ConditionValue.toLowerCase().endsWith("_allcolumns")) {
                    valueList = map_APIData.get(ConditionValue);
                }*/

            } else if (ValueType.equalsIgnoreCase(AppConstants.Global_OfflineTable)) {
                String tableName = ValueStr.split("\\.")[1];
                Log.e("FormNameInGlobal", tableName);
                String columnName = ValueStr.split("\\.")[2];
                Log.e("ColumnName", columnName);
                columnName = columnName.substring(0, columnName.length() - 1);

                valueList = ImproveHelper.getValueListFromOfflineTable(context, tableName, columnName);

            } else if (ValueType.equalsIgnoreCase(AppConstants.Global_GetData)) {
                String FormName = ValueStr.split("\\.")[1];
                ConditionValue = ValueStr.split("\\.")[2];
                ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                valueList = RealmDBHelper.getSingleColDataInList(context, FormName, ConditionValue);

            }


            for (int i = 0; i < list_Postions.size(); i++) {
                list_Values.add(valueList.get(list_Postions.get(i)));
            }

        }

        return list_Values;
    }


    public String getListOfValuesByLessthanCondition(String ValuesFromColumnName, String SearchColumnName, String SearchValue) {
        String finalValue = "";
        List<String> list_Values = new ArrayList<String>();
        try {

            List<Integer> list_Postions = getPostions(SearchColumnName, SearchValue, "Lessthan", "");

            list_Values = getListOfValuesByUsingListOfPostions(ValuesFromColumnName, list_Postions);
            finalValue = String.join(",", list_Values);

        } catch (Exception e) {
            System.out.println("Exception at getListOfValuesByLessthanCondition==" + e);
        }
        return finalValue;

    }

    public String getListOfValuesByGreaterthanCondition(String ValuesFromColumnName, String SearchColumnName, String SearchValue) {
        String finalValue = "";
        List<String> list_Values = new ArrayList<String>();
        try {

            List<Integer> list_Postions = getPostions(SearchColumnName, SearchValue, "Greaterthan", "");

            list_Values = getListOfValuesByUsingListOfPostions(ValuesFromColumnName, list_Postions);
            finalValue = String.join(",", list_Values);

        } catch (Exception e) {
            System.out.println("Exception at getListOfValuesByGreaterthanCondition==" + e);
        }
        return finalValue;

    }

    public String getListOfValuesByLessthanEqualCondition(String ValuesFromColumnName, String SearchColumnName, String SearchValue) {
        String finalValue = "";
        List<String> list_Values = new ArrayList<String>();
        try {

            List<Integer> list_Postions = getPostions(SearchColumnName, SearchValue, "LessthanEqual", "");

            list_Values = getListOfValuesByUsingListOfPostions(ValuesFromColumnName, list_Postions);
            finalValue = String.join(",", list_Values);

        } catch (Exception e) {
            System.out.println("Exception at getListOfValuesByLessthanEqualCondition==" + e);
        }
        return finalValue;

    }

    public String getListOfValuesByGreaterthanEqualCondition(String ValuesFromColumnName, String SearchColumnName, String SearchValue) {
        String finalValue = "";
        List<String> list_Values = new ArrayList<String>();
        try {

            List<Integer> list_Postions = getPostions(SearchColumnName, SearchValue, "GreaterthanEqual", "");

            list_Values = getListOfValuesByUsingListOfPostions(ValuesFromColumnName, list_Postions);
            finalValue = String.join(",", list_Values);

        } catch (Exception e) {
            System.out.println("Exception at getListOfValuesByGreaterthanEqualCondition==" + e);
        }
        return finalValue;

    }

    @Override
    public String getListOfValuesByInBetweenCondition(String ValuesFromColumnName, String SearchColumnName, String SearchValueFrom, String SearchValueTo) {
        String finalValue = "";
        List<String> list_Values = new ArrayList<String>();
        try {

            List<Integer> list_Postions = getListOfPostions(SearchColumnName, SearchValueFrom, "InBetween", SearchValueTo);

            list_Values = getListOfValuesByUsingListOfPostions(ValuesFromColumnName, list_Postions);
            finalValue = String.join(",", list_Values);

        } catch (Exception e) {
            System.out.println("Exception at getListOfValuesByInBetweenCondition==" + e);
        }
        return finalValue;
    }


    public String getValueByPositions(String ReturnColumnName, List<String> list_Postions) {
        String finalValue = "";
        List<Integer> list_Values = new ArrayList<>();
        List<String> list_StrValues = new ArrayList<>();
        for (String s : list_Postions) list_Values.add(Integer.valueOf(s));
        try {


            list_StrValues = getListOfValuesByUsingListOfPostions(ReturnColumnName, list_Values);
            finalValue = String.join(",", list_StrValues);

        } catch (Exception e) {
            System.out.println("Exception at getListOfValuesByInBetweenCondition==" + e);
        }
        return finalValue;

    }

    public String getListOfNamesByPositions(String SearchColumnName, int Positions) {
        List<String> valueList = new ArrayList<String>();
        String ValueType = "", ValueStr = "", ConditionValue = "", value = "";
        try {
            ValueStr = SearchColumnName;
            if (ValueStr.startsWith("(im:")) {
                ValueType = ValueStr.substring(4, ValueStr.indexOf("."));
                if (ValueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
                    String SubformName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    //nk realm:
                    /*LinkedHashMap<String, List<String>> map_SubformData = AppConstants.GlobalObjects.getSubControls_List().get(SubformName);
                    valueList = map_SubformData.get(ConditionValue);*/
                    valueList = RealmDBHelper.getSingleColDataInList(context, SubformName, ConditionValue);

                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_API)) {
                    String APIName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));

                    valueList = RealmDBHelper.getSingleColDataInList(context, APIName, ConditionValue);
                    //nk realm
                    /* LinkedHashMap<String, List<String>> map_APIData = AppConstants.GlobalObjects.getAPIs_ListHash().get(APIName);
                    valueList = map_APIData.get(ConditionValue);*/


                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_FormFields)) {
                    String FormName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                  /*  LinkedHashMap<String, List<String>> map_FormData = AppConstants.GlobalObjects.getForms_ListHash().get(FormName);
                    valueList = map_FormData.get(ConditionValue);*/
                    //nk realm
                    valueList = RealmDBHelper.getSingleColDataInList(context, FormName, ConditionValue);
                } else if (ValueType.equalsIgnoreCase(AppConstants.Global_Query)) {
                    String QueryName = ValueStr.split("\\.")[1];
                    ConditionValue = ValueStr.split("\\.")[2];
                    ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                    valueList = RealmDBHelper.getSingleColDataInList(context, QueryName, ConditionValue);
                    //nk realm
                    /* LinkedHashMap<String, List<String>> map_QueryData = AppConstants.GlobalObjects.getQuerys_ListHash().get(QueryName);
                    valueList = map_QueryData.get(ConditionValue);*/

                }
            }

            if (valueList.size() >= Positions) {
                value = valueList.get(Positions);
            }

        } catch (Exception e) {
            System.out.println("Exception at getListOfNamesByPositions==" + e);
        }
        return value;

    }

    public List<Integer> getListOfPostions(String SearchColumnName, String SearchValue, String SearchValueTo, String Condition) {
        List<Integer> list_postions = new ArrayList<Integer>();
        String ValueType = "", ValueStr = "", ConditionValue = "";
        List<String> valueList = new ArrayList<String>();

        ValueStr = SearchColumnName;
        if (ValueStr.startsWith("(im:")) {
            ValueType = ValueStr.substring(4, ValueStr.indexOf("."));
            if (ValueType.equalsIgnoreCase(AppConstants.Global_SubControls)) {
                String SubformName = ValueStr.split("\\.")[1];
                ConditionValue = ValueStr.split("\\.")[2];
                ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
//                LinkedHashMap<String, List<String>> map_SubformData = AppConstants.GlobalObjects.getSubControls_List().get(SubformName);


//                valueList = map_SubformData.get(ConditionValue);

                valueList = ImproveHelper.getListOfValuesFromGlobalObject(context, SearchColumnName);


            } else if (ValueType.equalsIgnoreCase(AppConstants.Global_API)) {
                String APIName = ValueStr.split("\\.")[1];
                ConditionValue = ValueStr.split("\\.")[2];
                ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                valueList = RealmDBHelper.getSingleColDataInList(context, APIName, ConditionValue);
                //nk realm
                /* LinkedHashMap<String, List<String>> map_APIData = AppConstants.GlobalObjects.getAPIs_ListHash().get(APIName);
                valueList = map_APIData.get(ConditionValue);*/


            } else if (ValueType.equalsIgnoreCase(AppConstants.Global_FormFields)) {
                String FormName = ValueStr.split("\\.")[1];
                ConditionValue = ValueStr.split("\\.")[2];
                ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
               /* LinkedHashMap<String, List<String>> map_FormData = AppConstants.GlobalObjects.getForms_ListHash().get(FormName);
                valueList = map_FormData.get(ConditionValue);*/
                //nk realm
                valueList = RealmDBHelper.getSingleColDataInList(context, FormName, ConditionValue);
            } else if (ValueType.equalsIgnoreCase(AppConstants.Global_Query)) {
                String QueryName = ValueStr.split("\\.")[1];
                ConditionValue = ValueStr.split("\\.")[2];
                ConditionValue = ConditionValue.substring(0, ConditionValue.indexOf(")"));
                valueList = RealmDBHelper.getSingleColDataInList(context, QueryName, ConditionValue);
                //nk realm
                /*LinkedHashMap<String, List<String>> map_QueryData = AppConstants.GlobalObjects.getQuerys_ListHash().get(QueryName);
                valueList = map_QueryData.get(ConditionValue);*/

            }

            SearchValue = ImproveHelper.getValueFromGlobalObject(context, SearchValue);


            for (int i = 0; i < valueList.size(); i++) {
                switch (Condition) {
                    case "Equal":
                        if (valueList.get(i).equalsIgnoreCase(SearchValue)) {
                            list_postions.add(i);
                        }
                        break;
                    case "Lessthan":
                        if (Double.parseDouble(valueList.get(i)) < Double.parseDouble(SearchValue)) {
                            list_postions.add(i);
                        }
                        break;
                    case "Greaterthan":
                        if (Double.parseDouble(valueList.get(i)) > Double.parseDouble(SearchValue)) {
                            list_postions.add(i);
                        }
                        break;
                    case "LessthanEqual":
                        if (Double.parseDouble(valueList.get(i)) <= Double.parseDouble(SearchValue)) {
                            list_postions.add(i);
                        }
                        break;
                    case "GreaterthanEqual":
                        if (Double.parseDouble(valueList.get(i)) >= Double.parseDouble(SearchValue)) {
                            list_postions.add(i);
                        }
                        break;
                    case "InBetween":
                        if (Double.parseDouble(valueList.get(i)) > Double.parseDouble(SearchValue) &&
                                Double.parseDouble(valueList.get(i)) < Double.parseDouble(SearchValueTo)) {
                            list_postions.add(i);
                        }
                        break;
                }
            }

        }


        return list_postions;
    }


}
