package com.bhargo.user.pojos;

import com.bhargo.user.Java_Beans.ControlObject;

import java.io.Serializable;

public class ServerCaseMD implements Serializable {
    ControlObject controlObject;
    String controlType;
    String controlName;
    String colName;
    String colValue;

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    boolean isFile;

    public String getColValue() {
        return colValue;
    }

    public void setColValue(String colValue) {
        this.colValue = colValue;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public ControlObject getControlObject() {
        return controlObject;
    }

    public void setControlObject(ControlObject controlObject) {
        this.controlObject = controlObject;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }




}
