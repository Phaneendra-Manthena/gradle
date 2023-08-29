package com.bhargo.user.pojos;

public class InsertUserFileVisitsModel {

    String UserID;
    String DBNAME;
    String Parent_Id;
    String Selected_Node_Id;
    String CategoryFileID;
    String DistributionId;
    String DeviceID;
    String MobileDate;
    String StartTime;
    String EndTime;
    String GPS;
    String Is_Visited_Through;
    String filesOffLineStatus;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getDBNAME() {
        return DBNAME;
    }

    public void setDBNAME(String DBNAME) {
        this.DBNAME = DBNAME;
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

    public String getCategoryFileID() {
        return CategoryFileID;
    }

    public void setCategoryFileID(String categoryFileID) {
        CategoryFileID = categoryFileID;
    }

    public String getDistributionId() {
        return DistributionId;
    }

    public void setDistributionId(String distributionId) {
        DistributionId = distributionId;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getMobileDate() {
        return MobileDate;
    }

    public void setMobileDate(String mobileDate) {
        MobileDate = mobileDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getGPS() {
        return GPS;
    }

    public void setGPS(String GPS) {
        this.GPS = GPS;
    }

    public String getIs_Visited_Through() {
        return Is_Visited_Through;
    }

    public void setIs_Visited_Through(String is_Visited_Through) {
        Is_Visited_Through = is_Visited_Through;
    }

    public String getFilesOffLineStatus() {
        return filesOffLineStatus;
    }

    public void setFilesOffLineStatus(String filesOffLineStatus) {
        this.filesOffLineStatus = filesOffLineStatus;
    }
}
