package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class SyncFormData implements Serializable {

    private String syncType;
    private String formName;
    private String tableName;
    private boolean enableRetrieveDataFromServer;
    private boolean enableSendDataToServer;
    private String retrieveType; // "Post Based" / "Post Location Based" / "Entire Table Data"/ "Filter Based"
    private List<API_InputParam_Bean> filterColumns;// if retrieveType is filter based

    public String getSyncType() {
        return syncType;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
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

    public boolean isEnableRetrieveDataFromServer() {
        return enableRetrieveDataFromServer;
    }

    public void setEnableRetrieveDataFromServer(boolean enableRetrieveDataFromServer) {
        this.enableRetrieveDataFromServer = enableRetrieveDataFromServer;
    }

    public boolean isEnableSendDataToServer() {
        return enableSendDataToServer;
    }

    public void setEnableSendDataToServer(boolean enableSendDataToServer) {
        this.enableSendDataToServer = enableSendDataToServer;
    }

    public String getRetrieveType() {
        return retrieveType;
    }

    public void setRetrieveType(String retrieveType) {
        this.retrieveType = retrieveType;
    }

    public List<API_InputParam_Bean> getFilterColumns() {
        return filterColumns;
    }

    public void setFilterColumns(List<API_InputParam_Bean> filterColumns) {
        this.filterColumns = filterColumns;
    }
}