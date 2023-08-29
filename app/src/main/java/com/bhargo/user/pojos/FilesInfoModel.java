package com.bhargo.user.pojos;

import java.io.Serializable;

public class FilesInfoModel implements Serializable {

    String FFDistributeId;
    String CategoryFileID;
    String Parent_Id;
    String Selected_Node_Id;
    String SelectedFileName;
    String FileNameExt;
    String FilePath;
    String IsPrivatePublic;
    String DownloadUrl;
    String FileDescription;
    String FileCoverImage;

    public String getFFDistributeId() {
        return FFDistributeId;
    }

    public void setFFDistributeId(String FFDistributeId) {
        this.FFDistributeId = FFDistributeId;
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

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getIsPrivatePublic() {
        return IsPrivatePublic;
    }

    public void setIsPrivatePublic(String isPrivatePublic) {
        IsPrivatePublic = isPrivatePublic;
    }

    public String getDownloadUrl() {
        return DownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        DownloadUrl = downloadUrl;
    }

    public String getFileDescription() {
        return FileDescription;
    }

    public void setFileDescription(String fileDescription) {
        FileDescription = fileDescription;
    }

    public String getFileCoverImage() {
        return FileCoverImage;
    }

    public void setFileCoverImage(String fileCoverImage) {
        FileCoverImage = fileCoverImage;
    }
}
