package com.bhargo.user.pojos;

import java.io.Serializable;

public class TableNameAndColsFromDOPojo implements Serializable {


    private String formName;
    private String tableName;
    private String cols;
    private String type;//mainformtable(M)/subfromtable(S)

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCols() {
        return cols;
    }

    public void setCols(String cols) {
        this.cols = cols;
    }


}
