package com.bhargo.user.realm;

import java.io.Serializable;
import java.util.List;
//

public class JSONTableColsVals implements Serializable {

    String actionId;
    String tableName;
    String mappingTableName;
    List<String> colNames;
    List<String> colValues;
    List<String> colTypes;

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getMappingTableName() {
        return mappingTableName;
    }

    public void setMappingTableName(String mappingTableName) {
        this.mappingTableName = mappingTableName;
    }

    public List<String> getColValues() {
        return colValues;
    }

    public void setColValues(List<String> colValues) {
        this.colValues = colValues;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getColNames() {
        return colNames;
    }

    public void setColNames(List<String> colNames) {
        this.colNames = colNames;
    }


    public List<String> getColTypes() {
        return colTypes;
    }

    public void setColTypes(List<String> colTypes) {
        this.colTypes = colTypes;
    }


}
