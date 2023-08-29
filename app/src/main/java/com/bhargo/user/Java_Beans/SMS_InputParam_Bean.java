package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class SMS_InputParam_Bean implements Serializable {

    private boolean enable;
    private String InParam_Name;
    private String InParam_MappedControl_Type;
    private String InParam_Mapped_ID;
    private boolean InParam_ExpressionExists;
    private String InParam_ExpressionValue;
    private String InParam_Optional;
    private String InParam_Static;

    public String getInParam_Purpose() {
        return InParam_Purpose;
    }

    public void setInParam_Purpose(String inParam_Purpose) {
        InParam_Purpose = inParam_Purpose;
    }

    private String InParam_Purpose;
    private String InParam_InputMode;
    private String InParam_Operator;
    private String InParam_and_or;
    private ControlObject InParam_ControlObject;
    private boolean InParam_GPS;
    private String InParam_nearby_value;
    private String InParam_noofrecords;
    private String InParam_near_by_distance;
    private String InParam_near_by_records;
    private boolean isGPSControl;



    public SMS_InputParam_Bean(String InParam_Name, String InParam_MappedControl_Type, String InParam_Mapped_ID){
        this.InParam_Name=InParam_Name;
        this.InParam_MappedControl_Type=InParam_MappedControl_Type;
        this.InParam_Mapped_ID=InParam_Mapped_ID;
    }

    public String getInParam_Name() {
        return InParam_Name;
    }

    public void setInParam_Name(String inParam_Name) {
        InParam_Name = inParam_Name;
    }

    public String getInParam_MappedControl_Type() {
        return InParam_MappedControl_Type;
    }

    public void setInParam_MappedControl_Type(String inParam_MappedControl_Type) {
        InParam_MappedControl_Type = inParam_MappedControl_Type;
    }

    public String getInParam_Mapped_ID() {
        return InParam_Mapped_ID;
    }

    public void setInParam_Mapped_ID(String inParam_Mapped_ID) {
        InParam_Mapped_ID = inParam_Mapped_ID;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getInParam_Optional() {
        return InParam_Optional;
    }

    public void setInParam_Optional(String inParam_Optional) {
        InParam_Optional = inParam_Optional;
    }

    public String getInParam_Static() {
        return InParam_Static;
    }

    public void setInParam_Static(String inParam_Static) {
        InParam_Static = inParam_Static;
    }

    public boolean isInParam_ExpressionExists() {
        return InParam_ExpressionExists;
    }

    public void setInParam_ExpressionExists(boolean inParam_ExpressionExists) {
        InParam_ExpressionExists = inParam_ExpressionExists;
    }

    public String getInParam_ExpressionValue() {
        return InParam_ExpressionValue;
    }

    public void setInParam_ExpressionValue(String inParam_ExpressionValue) {
        InParam_ExpressionValue = inParam_ExpressionValue;
    }

    public String getInParam_InputMode() {
        return InParam_InputMode;
    }

    public void setInParam_InputMode(String inParam_InputMode) {
        InParam_InputMode = inParam_InputMode;
    }

    public String getInParam_Operator() {
        return InParam_Operator;
    }

    public void setInParam_Operator(String inParam_Operator) {
        InParam_Operator = inParam_Operator;
    }

    public String getInParam_and_or() {
        return InParam_and_or;
    }

    public void setInParam_and_or(String inParam_and_or) {
        InParam_and_or = inParam_and_or;
    }

    public ControlObject getInParam_ControlObject() {
        return InParam_ControlObject;
    }

    public void setInParam_ControlObject(ControlObject inParam_ControlObject) {
        InParam_ControlObject = inParam_ControlObject;
    }

    public boolean isInParam_GPS() {
        return InParam_GPS;
    }

    public void setInParam_GPS(boolean inParam_GPS) {
        InParam_GPS = inParam_GPS;
    }

    public String getInParam_nearby_value() {
        return InParam_nearby_value;
    }

    public void setInParam_nearby_value(String inParam_nearby_value) {
        InParam_nearby_value = inParam_nearby_value;
    }

    public String getInParam_noofrecords() {
        return InParam_noofrecords;
    }

    public void setInParam_noofrecords(String inParam_noofrecords) {
        InParam_noofrecords = inParam_noofrecords;
    }

    public String getInParam_near_by_distance() {
        return InParam_near_by_distance;
    }

    public void setInParam_near_by_distance(String inParam_near_by_distance) {
        InParam_near_by_distance = inParam_near_by_distance;
    }

    public String getInParam_near_by_records() {
        return InParam_near_by_records;
    }

    public void setInParam_near_by_records(String inParam_near_by_records) {
        InParam_near_by_records = inParam_near_by_records;
    }

    public boolean isGPSControl() {
        return isGPSControl;
    }

    public void setGPSControl(boolean GPSControl) {
        isGPSControl = GPSControl;
    }
}
