package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class ForeignKey_Bean implements Serializable {

    private String table;
    private String column;
    private String control;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }
}
