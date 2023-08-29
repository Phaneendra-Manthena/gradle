package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class CallForm_ParamMapping_Bean implements Serializable {

    private String Param_Name;
    private String Param_MapType;
    private String Param_StaticValue;
    private String Param_GlobalObj_Type;
    private String Param_GlobalObj_MappedID;


    public String getParam_Name() {
        return Param_Name;
    }

    public void setParam_Name(String param_Name) {
        Param_Name = param_Name;
    }

    public String getParam_MapType() {
        return Param_MapType;
    }

    public void setParam_MapType(String param_MapType) {
        Param_MapType = param_MapType;
    }

    public String getParam_StaticValue() {
        return Param_StaticValue;
    }

    public void setParam_StaticValue(String param_StaticValue) {
        Param_StaticValue = param_StaticValue;
    }

    public String getParam_GlobalObj_Type() {
        return Param_GlobalObj_Type;
    }

    public void setParam_GlobalObj_Type(String param_GlobalObj_Type) {
        Param_GlobalObj_Type = param_GlobalObj_Type;
    }

    public String getParam_GlobalObj_MappedID() {
        return Param_GlobalObj_MappedID;
    }

    public void setParam_GlobalObj_MappedID(String param_GlobalObj_MappedID) {
        Param_GlobalObj_MappedID = param_GlobalObj_MappedID;
    }
}
