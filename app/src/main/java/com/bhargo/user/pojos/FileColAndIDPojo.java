package com.bhargo.user.pojos;

import java.io.Serializable;

public class FileColAndIDPojo implements Serializable {

    String tableName;
    String rowId;
    String colName;
    String colVal;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColVal() {
        return colVal;
    }

    public void setColVal(String colVal) {
        this.colVal = colVal;
    }
}
