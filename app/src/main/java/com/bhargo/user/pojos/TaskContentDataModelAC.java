package com.bhargo.user.pojos;

import java.io.Serializable;

public class TaskContentDataModelAC implements Serializable {

    String ACId;
    String ACName;
    String ACDes;
    String ACImagePath;
    String Organization_Id;
    String User_Id;
    String Parent_Id;
    String Selected_Node_Id;
    String IsPrivatePublic;
    String SelectedFileName;
    String FileNameExt;
    String DownloadUrl;

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

    public String getOrganization_Id() {
        return Organization_Id;
    }

    public void setOrganization_Id(String organization_Id) {
        Organization_Id = organization_Id;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
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

    public String getIsPrivatePublic() {
        return IsPrivatePublic;
    }

    public void setIsPrivatePublic(String isPrivatePublic) {
        IsPrivatePublic = isPrivatePublic;
    }

    public String getSelectedFileName() {
        return SelectedFileName;
    }

    public void setSelectedFileName(String selectedFileName) {
        SelectedFileName = selectedFileName;
    }

    public String getFileNameExt() {
        return FileNameExt;
    }

    public void setFileNameExt(String fileNameExt) {
        FileNameExt = fileNameExt;
    }

    public String getDownloadUrl() {
        return DownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        DownloadUrl = downloadUrl;
    }
}
