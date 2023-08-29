package com.bhargo.user.pojos;

import java.io.Serializable;

public class FilterColumns implements Serializable {
    
    private  String ColumnName;
    private  String Operator;
    private  String Value;
    private  String Condition;
    private  String ColumnType;
    private  String NearBy;
    private  String NoOfRec;
    private  String CurrentGPS;

    public String getColumnName() {
        return ColumnName;
    }

    public void setColumnName(String columnName) {
        ColumnName = columnName;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    public String getColumnType() {
        return ColumnType;
    }

    public void setColumnType(String columnType) {
        ColumnType = columnType;
    }

    public String getNearBy() {
        return NearBy;
    }

    public void setNearBy(String nearBy) {
        NearBy = nearBy;
    }

    public String getNoOfRec() {
        return NoOfRec;
    }

    public void setNoOfRec(String noOfRec) {
        NoOfRec = noOfRec;
    }

    public String getCurrentGPS() {
        return CurrentGPS;
    }

    public void setCurrentGPS(String currentGPS) {
        CurrentGPS = currentGPS;
    }
}
