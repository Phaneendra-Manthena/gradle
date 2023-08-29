package com.bhargo.user.pojos;

import java.io.Serializable;
import java.util.List;

public class SubFormTableColumns implements Serializable {
    private String TableSettingsType;
    private String SubFormName;
    private String TableName;
    private String TableColumns;
    private String PrimaryKey;
  private List<ForeignKey> ForeignKey;
    private String CompositeKey;

    public String getTableSettingsType() {
        return TableSettingsType;
    }

    public void setTableSettingsType(String tableSettingsType) {
        TableSettingsType = tableSettingsType;
    }

    public String getSubFormName() {
        return SubFormName;
    }

    public void setSubFormName(String subFormName) {
        SubFormName = subFormName;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getTableColumns() {
        return TableColumns;
    }

    public void setTableColumns(String tableColumns) {
        TableColumns = tableColumns;
    }

    public String getPrimaryKey() {
        return PrimaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        PrimaryKey = primaryKey;
    }

    public List<ForeignKey> getForeignKey() {
        return ForeignKey;
    }

    public void setForeignKey(List<ForeignKey> foreignKey) {
        ForeignKey = foreignKey;
    }

    public String getCompositeKey() {
        return CompositeKey;
    }

    public void setCompositeKey(String compositeKey) {
        CompositeKey = compositeKey;
    }


}
