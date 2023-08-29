package com.bhargo.user.pojos;

import java.io.Serializable;

public class UpdateTablePojo implements Serializable {

    String tableName;
    String rowidVal;
    String colName1;
    String colVal1;
    String colName2;
    String colVal2;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRowidVal() {
        return rowidVal;
    }

    public void setRowidVal(String rowidVal) {
        this.rowidVal = rowidVal;
    }

    public String getColName1() {
        return colName1;
    }

    public void setColName1(String colName1) {
        this.colName1 = colName1;
    }

    public String getColVal1() {
        return colVal1;
    }

    public void setColVal1(String colVal1) {
        this.colVal1 = colVal1;
    }

    public String getColName2() {
        return colName2;
    }

    public void setColName2(String colName2) {
        this.colName2 = colName2;
    }

    public String getColVal2() {
        return colVal2;
    }

    public void setColVal2(String colVal2) {
        this.colVal2 = colVal2;
    }

}
