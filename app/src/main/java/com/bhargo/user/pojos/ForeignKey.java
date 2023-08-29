package com.bhargo.user.pojos;

import java.io.Serializable;

public class ForeignKey implements Serializable {

    private String ForeignKey;
    private String TableName;
    private String ColumnName;

    public String getForeignKey() {
        return ForeignKey;
    }

    public void setForeignKey(String foreignKey) {
        ForeignKey = foreignKey;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getColumnName() {
        return ColumnName;
    }

    public void setColumnName(String columnName) {
        ColumnName = columnName;
    }
}
