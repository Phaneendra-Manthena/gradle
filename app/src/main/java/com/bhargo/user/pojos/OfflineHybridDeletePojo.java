package com.bhargo.user.pojos;

import java.io.Serializable;

public class OfflineHybridDeletePojo implements Serializable {

    String colName1, colName2, colName3;
    String colValue1, colValue2, colValue3;
    String imagePath;
    String transIDColVal;
    String transIDColName;
    String tableName;
    String rowID;

    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }

    public String getColName1() {
        return colName1;
    }

    public void setColName1(String colName1) {
        this.colName1 = colName1;
    }

    public String getColName2() {
        return colName2;
    }

    public void setColName2(String colName2) {
        this.colName2 = colName2;
    }

    public String getColName3() {
        return colName3;
    }

    public void setColName3(String colName3) {
        this.colName3 = colName3;
    }

    public String getColValue1() {
        return colValue1;
    }

    public void setColValue1(String colValue1) {
        this.colValue1 = colValue1;
    }

    public String getColValue2() {
        return colValue2;
    }

    public void setColValue2(String colValue2) {
        this.colValue2 = colValue2;
    }

    public String getColValue3() {
        return colValue3;
    }

    public void setColValue3(String colValue3) {
        this.colValue3 = colValue3;
    }

    public String getTransIDColVal() {
        return transIDColVal;
    }

    public void setTransIDColVal(String transIDColVal) {
        this.transIDColVal = transIDColVal;
    }

    public String getTransIDColName() {
        return transIDColName;
    }

    public void setTransIDColName(String transIDColName) {
        this.transIDColName = transIDColName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


}
