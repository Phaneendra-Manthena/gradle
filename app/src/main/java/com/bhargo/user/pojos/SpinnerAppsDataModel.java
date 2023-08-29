package com.bhargo.user.pojos;

import java.io.Serializable;

public class SpinnerAppsDataModel implements Serializable {

    String TaskAppID;
    String ACId;
    String ACName;
    String ACDes;
    String ACImagePath;
    boolean isSelected;
    boolean isFilteredColumn;

    public String getTaskAppID() {
        return TaskAppID;
    }

    public void setTaskAppID(String taskAppID) {
        TaskAppID = taskAppID;
    }

    public String getACId() {
        return ACId;
    }

    public void setACId(String ACId) {
        this.ACId = ACId;
    }

    public String getACName() {
        return ACName;
    }

    public void setACName(String ACName) {
        this.ACName = ACName;
    }

    public String getACDes() {
        return ACDes;
    }

    public void setACDes(String ACDes) {
        this.ACDes = ACDes;
    }

    public String getACImagePath() {
        return ACImagePath;
    }

    public void setACImagePath(String ACImagePath) {
        this.ACImagePath = ACImagePath;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isFilteredColumn() {
        return isFilteredColumn;
    }

    public void setFilteredColumn(boolean filteredColumn) {
        isFilteredColumn = filteredColumn;
    }

    //    String AppID;
//    String AppName;
//
//    public String getAppID() {
//        return AppID;
//    }
//
//    public void setAppID(String appID) {
//        AppID = appID;
//    }
//
//    public String getAppName() {
//        return AppName;
//    }
//
//    public void setAppName(String appName) {
//        AppName = appName;
//    }
}
