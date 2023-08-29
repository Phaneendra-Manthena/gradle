package com.bhargo.user.Java_Beans;

import java.util.LinkedHashMap;

public class New_DataControl_Bean {

    public String dc_id;
    public String dc_value;
    public String dc_name;
    public String dc_dependency;//dependentControlName
    public LinkedHashMap<String,String> dc_DependencyValues=new LinkedHashMap<String,String>();
    public String dc_KeyId;
    public String dc_KeyName;


    public String getDc_id() {
        return dc_id;
    }

    public void setDc_id(String dc_id) {
        this.dc_id = dc_id;
    }

    public String getDc_value() {
        return dc_value;
    }

    public void setDc_value(String dc_value) {
        this.dc_value = dc_value;
    }

    public String getDc_name() {
        return dc_name;
    }

    public void setDc_name(String dc_name) {
        this.dc_name = dc_name;
    }

    public String getDc_dependency() {
        return dc_dependency;
    }

    public void setDc_dependency(String dc_dependency) {
        this.dc_dependency = dc_dependency;
    }


    public String toString(){

        return  dc_value;
    }

    public LinkedHashMap<String, String> getDc_DependencyValues() {
        return dc_DependencyValues;
    }

    public void setDc_DependencyValues(LinkedHashMap<String, String> dc_DependencyValues) {
        this.dc_DependencyValues = dc_DependencyValues;
    }

    public String getDc_KeyId() {
        return dc_KeyId;
    }

    public void setDc_KeyId(String dc_KeyId) {
        this.dc_KeyId = dc_KeyId;
    }

    public String getDc_KeyName() {
        return dc_KeyName;
    }

    public void setDc_KeyName(String dc_KeyName) {
        this.dc_KeyName = dc_KeyName;
    }
}
