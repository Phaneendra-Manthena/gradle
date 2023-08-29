package com.bhargo.user.pojos;

import java.io.Serializable;

public class AppsInfoModel implements Serializable {

    String TaskAppID; // distributionId in app details object
    String ACName; //// AppName in app details object
    String ACNameACDes; //// Description in app details object
    String ACImagePath; // AppIcon in app details object

    public String getTaskAppID() {
        return TaskAppID;
    }

    public void setTaskAppID(String taskAppID) {
        TaskAppID = taskAppID;
    }

    public String getACName() {
        return ACName;
    }

    public void setACName(String ACName) {
        this.ACName = ACName;
    }

    public String getACNameACDes() {
        return ACNameACDes;
    }

    public void setACNameACDes(String ACNameACDes) {
        this.ACNameACDes = ACNameACDes;
    }

    public String getACImagePath() {
        return ACImagePath;
    }

    public void setACImagePath(String ACImagePath) {
        this.ACImagePath = ACImagePath;
    }
}
