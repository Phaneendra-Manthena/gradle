package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by SantoshB on 30/10/2019.
 */

public class GO_API_Object implements Serializable {

    private String API_Name,API_MethodName,API_Type,API_OutPutType,API_ResultType;
    private LinkedHashMap<String,String> API_Input_Data;
    private LinkedHashMap<String,List<String>> API_Output_Data;

    public String getAPI_Name() {
        return API_Name;
    }

    public void setAPI_Name(String API_Name) {
        this.API_Name = API_Name;
    }

    public String getAPI_MethodName() {
        return API_MethodName;
    }

    public void setAPI_MethodName(String API_MethodName) {
        this.API_MethodName = API_MethodName;
    }

    public String getAPI_Type() {
        return API_Type;
    }

    public void setAPI_Type(String API_Type) {
        this.API_Type = API_Type;
    }

    public String getAPI_OutPutType() {
        return API_OutPutType;
    }

    public void setAPI_OutPutType(String API_OutPutType) {
        this.API_OutPutType = API_OutPutType;
    }

    public String getAPI_ResultType() {
        return API_ResultType;
    }

    public void setAPI_ResultType(String API_ResultType) {
        this.API_ResultType = API_ResultType;
    }

    public LinkedHashMap<String, String> getAPI_Input_Data() {
        return API_Input_Data;
    }

    public void setAPI_Input_Data(LinkedHashMap<String, String> API_Input_Data) {
        this.API_Input_Data = API_Input_Data;
    }

    public LinkedHashMap<String, List<String>> getAPI_Output_Data() {
        return API_Output_Data;
    }

    public void setAPI_Output_Data(LinkedHashMap<String, List<String>> API_Output_Data) {
        this.API_Output_Data = API_Output_Data;
    }


}
