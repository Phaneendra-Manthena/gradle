package com.bhargo.user.pojos;

import com.bhargo.user.Java_Beans.QueryFilterField_Bean;

import org.json.JSONArray;

import java.util.List;

public class SubformMapExistingData {

    private String subformName;
    private String tableSettingsType;
    private String existingTableName;
    private String mapExistingType;
    private JSONArray subFormDataFields;
    private List<QueryFilterField_Bean> subFormInsertFields;
    private List<QueryFilterField_Bean> subFormUpdateFields;
    private List<QueryFilterField_Bean> subFormFilterFields;

    public String getSubformName() {
        return subformName;
    }

    public void setSubformName(String subformName) {
        this.subformName = subformName;
    }

    public String getTableSettingsType() {
        return tableSettingsType;
    }

    public void setTableSettingsType(String tableSettingsType) {
        this.tableSettingsType = tableSettingsType;
    }

    public String getExistingTableName() {
        return existingTableName;
    }

    public void setExistingTableName(String existingTableName) {
        this.existingTableName = existingTableName;
    }

    public String getMapExistingType() {
        return mapExistingType;
    }

    public void setMapExistingType(String mapExistingType) {
        this.mapExistingType = mapExistingType;
    }

    public JSONArray getSubFormDataFields() {
        return subFormDataFields;
    }

    public void setSubFormDataFields(JSONArray subFormDataFields) {
        this.subFormDataFields = subFormDataFields;
    }

    public List<QueryFilterField_Bean> getSubFormInsertFields() {
        return subFormInsertFields;
    }

    public void setSubFormInsertFields(List<QueryFilterField_Bean> subFormInsertFields) {
        this.subFormInsertFields = subFormInsertFields;
    }

    public List<QueryFilterField_Bean> getSubFormUpdateFields() {
        return subFormUpdateFields;
    }

    public void setSubFormUpdateFields(List<QueryFilterField_Bean> subFormUpdateFields) {
        this.subFormUpdateFields = subFormUpdateFields;
    }

    public List<QueryFilterField_Bean> getSubFormFilterFields() {
        return subFormFilterFields;
    }

    public void setSubFormFilterFields(List<QueryFilterField_Bean> subFormFilterFields) {
        this.subFormFilterFields = subFormFilterFields;
    }
}
