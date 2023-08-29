package com.bhargo.user.pojos;

import java.io.Serializable;

public class OfflineHybridAppListPojo implements Serializable, Comparable<OfflineHybridAppListPojo> {


    private String AppName;
    private String AppMode;
    private String TableName;
    private String TableNameRecordsCount;
    private String DesignFormat;
    private AppDetails appDetails;//only some keys adding for creating tables

    @Override
    public int compareTo(OfflineHybridAppListPojo offlineHybridAppListPojo) {

        return Integer.compare(Integer.valueOf(offlineHybridAppListPojo.TableNameRecordsCount), Integer.valueOf(this.TableNameRecordsCount));
    }

    public AppDetails getAppDetails() {
        return appDetails;
    }

    public void setAppDetails(AppDetails appDetails) {
        this.appDetails = appDetails;
    }

    public String getDesignFormat() {
        return DesignFormat;
    }

    public void setDesignFormat(String designFormat) {
        DesignFormat = designFormat;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getAppMode() {
        return AppMode;
    }

    public void setAppMode(String appMode) {
        AppMode = appMode;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getTableNameRecordsCount() {
        return TableNameRecordsCount;
    }

    public void setTableNameRecordsCount(String tableNameRecordsCount) {
        TableNameRecordsCount = tableNameRecordsCount;
    }

}
