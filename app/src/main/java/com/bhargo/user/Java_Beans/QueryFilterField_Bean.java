package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class QueryFilterField_Bean implements Serializable {
    String Field_Name,Field_Operator, Field_ValueType, Field_Static_Value, Field_Global_Type, Field_Global_Value;
    boolean Field_IsDeleted;
    ControlObject Field_ControlObject;
    ControlObject Exist_Field_ControlObject;
    boolean Field_IsEditable,Field_IsMandatory,Field_IsVisible;
    String Field_and_or;
    private String distanceAround;
    private String nearBy;
    private String isNullAllowed;

    public boolean isAutoNumber() {
        return isAutoNumber;
    }

    public void setAutoNumber(boolean autoNumber) {
        isAutoNumber = autoNumber;
    }

    boolean isAutoNumber;

    public String getField_Name() {
        return Field_Name;
    }

    public void setField_Name(String field_Name) {
        Field_Name = field_Name;
    }

    public String getField_Operator() {
        return Field_Operator;
    }

    public void setField_Operator(String field_Operator) {
        Field_Operator = field_Operator;
    }

    public String getField_ValueType() {
        return Field_ValueType;
    }

    public void setField_ValueType(String field_ValueType) {
        Field_ValueType = field_ValueType;
    }

    public String getField_Static_Value() {
        return Field_Static_Value;
    }

    public void setField_Static_Value(String field_Static_Value) {
        Field_Static_Value = field_Static_Value;
    }

    public String getField_Global_Type() {
        return Field_Global_Type;
    }

    public void setField_Global_Type(String field_Global_Type) {
        Field_Global_Type = field_Global_Type;
    }

    public String getField_Global_Value() {
        return Field_Global_Value;
    }

    public void setField_Global_Value(String field_Global_Value) {
        Field_Global_Value = field_Global_Value;
    }

    public boolean isField_IsDeleted() {
        return Field_IsDeleted;
    }

    public void setField_IsDeleted(boolean field_IsDeleted) {
        Field_IsDeleted = field_IsDeleted;
    }

    public ControlObject getField_ControlObject() {
        return Field_ControlObject;
    }

    public void setField_ControlObject(ControlObject field_ControlObject) {
        Field_ControlObject = field_ControlObject;
    }

    public ControlObject getExist_Field_ControlObject() {
        return Exist_Field_ControlObject;
    }

    public void setExist_Field_ControlObject(ControlObject exist_Field_ControlObject) {
        Exist_Field_ControlObject = exist_Field_ControlObject;
    }


    public boolean isField_IsEditable() {
        return Field_IsEditable;
    }

    public void setField_IsEditable(boolean field_IsEditable) {
        Field_IsEditable = field_IsEditable;
    }

    public boolean isField_IsMandatory() {
        return Field_IsMandatory;
    }

    public void setField_IsMandatory(boolean field_IsMandatory) {
        Field_IsMandatory = field_IsMandatory;
    }

    public boolean isField_IsVisible() {
        return Field_IsVisible;
    }

    public void setField_IsVisible(boolean field_IsVisible) {
        Field_IsVisible = field_IsVisible;
    }

    public String getField_and_or() {
        return Field_and_or;
    }

    public void setField_and_or(String field_and_or) {
        Field_and_or = field_and_or;
    }

    public String getDistanceAround() {
        return distanceAround;
    }

    public void setDistanceAround(String distanceAround) {
        this.distanceAround = distanceAround;
    }

    public String getNearBy() {
        return nearBy;
    }

    public void setNearBy(String nearBy) {
        this.nearBy = nearBy;
    }

    public String getIsNullAllowed() {
        return isNullAllowed;
    }

    public void setIsNullAllowed(String isNullAllowed) {
        this.isNullAllowed = isNullAllowed;
    }
}
