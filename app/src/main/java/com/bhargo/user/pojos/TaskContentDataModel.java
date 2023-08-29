package com.bhargo.user.pojos;

import java.io.Serializable;

public class TaskContentDataModel implements Serializable {

    String TaskContentID;
    String ACId;
    String ACName;
    String ACDes;
    String ACImagePath;

    public String getTaskContentID() {
        return TaskContentID;
    }

    public void setTaskContentID(String taskContentID) {
        TaskContentID = taskContentID;
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

    //    String ContentID;
//    String ContentName;
//
//    public String getContentID() {
//        return ContentID;
//    }
//
//    public void setContentID(String contentID) {
//        ContentID = contentID;
//    }
//
//    public String getContentName() {
//        return ContentName;
//    }
//
//    public void setContentName(String contentName) {
//        ContentName = contentName;
//    }
}
