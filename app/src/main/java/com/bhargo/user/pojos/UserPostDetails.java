package com.bhargo.user.pojos;

import java.util.List;

public class UserPostDetails {

    private String PostID;
    private String Name;
    private String ReportingPostID;
    private String ReportingDepartmentID;
    private String ManualReportingPostID;
    private String ManualReportingPersonID;
    private String PostLocatonLevel;
    private String PostLocationLevelName;
    private String SessionStatus;
    List<PostSubLocationsModel> PostSubLocations;

    public String getPostID() {
        return PostID;
    }

    public void setPostID(String postID) {
        PostID = postID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public List<PostSubLocationsModel> getPostSubLocations() {
        return PostSubLocations;
    }

    public void setPostSubLocations(List<PostSubLocationsModel> postSubLocations) {
        PostSubLocations = postSubLocations;
    }

    public String getSessionStatus() {
        return SessionStatus;
    }

    public void setSessionStatus(String sessionStatus) {
        SessionStatus = sessionStatus;
    }
}
