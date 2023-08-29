package com.bhargo.user.pojos;

import java.io.Serializable;

public class ContentInfoModel implements Serializable {

    String TaskContentID;
    String ACId;
    String ACName;
    String ACDes;
    String ACImagePath;
    String DownloadUrl;
    String CategoryFileID = "";
    String Parent_Id;
    String Selected_Node_Id;
    boolean isSelected;

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

    public String getDownloadUrl() {
        return DownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        DownloadUrl = downloadUrl;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCategoryFileID() {
        return CategoryFileID;
    }

    public void setCategoryFileID(String categoryFileID) {
        CategoryFileID = categoryFileID;
    }

    public String getParent_Id() {
        return Parent_Id;
    }

    public void setParent_Id(String parent_Id) {
        Parent_Id = parent_Id;
    }

    public String getSelected_Node_Id() {
        return Selected_Node_Id;
    }

    public void setSelected_Node_Id(String selected_Node_Id) {
        Selected_Node_Id = selected_Node_Id;
    }
}
