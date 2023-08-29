package com.bhargo.user.pojos;

import java.io.Serializable;
import java.util.List;

public class PostsMasterModel implements Serializable {

    String UserID;
    String LoginTypeID;
    String ID;
    String Name;
    List<PostSubLocationsModel> PostSubLocations;
    String userType = "Employee";
    String PostLocationTypeID;
    String PostLocatonLevel;
    String PostLocationLevelName;
    String ReportingPostID;
    String ReportingDepartmentID;
    String ManualReportingPostID;
    String ManualReportingPersonID;
   List<PartialDataControlPathsModel> PartialDataControlPaths;


    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getLoginTypeID() {
        return LoginTypeID;
    }

    public void setLoginTypeID(String loginTypeID) {
        LoginTypeID = loginTypeID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<PostSubLocationsModel> getPostSubLocations() {
        return PostSubLocations;
    }

    public void setPostSubLocations(List<PostSubLocationsModel> postSubLocations) {
        PostSubLocations = postSubLocations;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPostLocationTypeID() {
        return PostLocationTypeID;
    }

    public void setPostLocationTypeID(String postLocationTypeID) {
        PostLocationTypeID = postLocationTypeID;
    }

    public String getPostLocatonLevel() {
        return PostLocatonLevel;
    }

    public void setPostLocatonLevel(String postLocatonLevel) {
        PostLocatonLevel = postLocatonLevel;
    }

    public String getPostLocationLevelName() {
        return PostLocationLevelName;
    }

    public void setPostLocationLevelName(String postLocationLevelName) {
        PostLocationLevelName = postLocationLevelName;
    }

    public String getReportingPostID() {
        return ReportingPostID;
    }

    public void setReportingPostID(String reportingPostID) {
        ReportingPostID = reportingPostID;
    }

    public String getReportingDepartmentID() {
        return ReportingDepartmentID;
    }

    public void setReportingDepartmentID(String reportingDepartmentID) {
        ReportingDepartmentID = reportingDepartmentID;
    }

    public String getManualReportingPostID() {
        return ManualReportingPostID;
    }

    public void setManualReportingPostID(String manualReportingPostID) {
        ManualReportingPostID = manualReportingPostID;
    }

    public String getManualReportingPersonID() {
        return ManualReportingPersonID;
    }

    public void setManualReportingPersonID(String manualReportingPersonID) {
        ManualReportingPersonID = manualReportingPersonID;
    }

    public List<PartialDataControlPathsModel> getPartialDataControlPaths() {
        return PartialDataControlPaths;
    }

    public void setPartialDataControlPaths(List<PartialDataControlPathsModel> partialDataControlPaths) {
        PartialDataControlPaths = partialDataControlPaths;
    }
}
