package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CallForm_Object implements Serializable {

    public String Form_Name,Form_Desc;
    public List<String> Form_FieldsNames_list,Form_FieldsDataType_list;

    public  CallForm_Object(String Form_Name,String Form_Desc,
                                String[] Form_FieldsNames_list,String[] Form_FieldsDataType_list){

        this.Form_Name=Form_Name;
        this.Form_Desc=Form_Desc;
        this.Form_FieldsNames_list=new ArrayList<String >();
        this.Form_FieldsNames_list= Arrays.asList(Form_FieldsNames_list);
        this.Form_FieldsDataType_list=new ArrayList<String >();
        this.Form_FieldsDataType_list=Arrays.asList(Form_FieldsDataType_list);

    }

    public String getForm_Name() {
        return Form_Name;
    }

    public void setForm_Name(String form_Name) {
        Form_Name = form_Name;
    }

    public String getForm_Desc() {
        return Form_Desc;
    }

    public void setForm_Desc(String form_Desc) {
        Form_Desc = form_Desc;
    }

    public List<String> getForm_FieldsNames_list() {
        return Form_FieldsNames_list;
    }

    public void setForm_FieldsNames_list(List<String> form_FieldsNames_list) {
        Form_FieldsNames_list = form_FieldsNames_list;
    }

    public List<String> getForm_FieldsDataType_list() {
        return Form_FieldsDataType_list;
    }

    public void setForm_FieldsDataType_list(List<String> form_FieldsDataType_list) {
        Form_FieldsDataType_list = form_FieldsDataType_list;
    }
}
