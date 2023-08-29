package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class QuerySelectField_Bean implements Serializable {

    String Field_Name,Field_Display_Formate;
    boolean Field_isDeleted;
    boolean Field_Enabled;
    ControlObject Field_ControlObject;

    public String getField_Name() {
        return Field_Name;
    }

    public void setField_Name(String field_Name) {
        Field_Name = field_Name;
    }

    public String getField_Display_Formate() {
        return Field_Display_Formate;
    }

    public void setField_Display_Formate(String field_Display_Formate) {
        Field_Display_Formate = field_Display_Formate;
    }

    public boolean isField_isDeleted() {
        return Field_isDeleted;
    }

    public void setField_isDeleted(boolean field_isDeleted) {
        Field_isDeleted = field_isDeleted;
    }

    public ControlObject getField_ControlObject() {
        return Field_ControlObject;
    }

    public void setField_ControlObject(ControlObject field_ControlObject) {
        Field_ControlObject = field_ControlObject;
    }

    public boolean isField_Enabled() {
        return Field_Enabled;
    }

    public void setField_Enabled(boolean field_Enabled) {
        Field_Enabled = field_Enabled;
    }
}
