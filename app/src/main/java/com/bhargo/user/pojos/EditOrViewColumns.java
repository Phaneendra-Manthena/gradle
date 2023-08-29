package com.bhargo.user.pojos;

import java.io.Serializable;

public class EditOrViewColumns implements Serializable {

    private String columnType;
    private String formName;
    private String columnName;

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
