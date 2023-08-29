package com.bhargo.user.pojos;

import java.io.Serializable;

public class ColNameAndValuePojo implements Serializable {

    String colName;

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColValue() {
        return colValue;
    }

    public void setColValue(String colValue) {
        this.colValue = colValue;
    }

    String colValue;
}
