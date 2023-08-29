package com.bhargo.user.pojos;

import java.io.Serializable;

public class TaskAppDataModel implements Serializable {

    String TaskAppID;
    String ACId;
    String ACName;
    String ACDes;
    String ACImagePath;

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

    //    String AppId;
//    String AppName;
//
//    public String getAppId() {
//        return AppId;
//    }
//
//    public void setAppId(String appId) {
//        AppId = appId;
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
