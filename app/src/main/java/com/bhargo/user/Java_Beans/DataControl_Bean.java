package com.bhargo.user.Java_Beans;

public class DataControl_Bean {

    public String dc_id;
    public String dc_value;
    public String dc_name;
    public String dc_dependency;


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

}
