package com.bhargo.user.pojos;

import java.util.List;

public class AssessmentAnswersData {

    String DBNAME;
    String DistributionId;
    String UserID;
    String TiemSpent;
    String DeviceID;
    String MobileDate;
    String AssessmentId;
    String PostID;
    List<AssessmentAnswer> Answers;

    public String getDBNAME() {
        return DBNAME;
    }

    public void setDBNAME(String DBNAME) {
        this.DBNAME = DBNAME;
    }

    public String getDistributionId() {
        return DistributionId;
    }

    public void setDistributionId(String distributionId) {
        DistributionId = distributionId;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getTiemSpent() {
        return TiemSpent;
    }

    public void setTiemSpent(String tiemSpent) {
        TiemSpent = tiemSpent;
    }

    public List<AssessmentAnswer> getAnswers() {
        return Answers;
    }

    public void setAnswers(List<AssessmentAnswer> answers) {
        Answers = answers;
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

    public String getAssessmentId() {
        return AssessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        AssessmentId = assessmentId;
    }

    public String getPostID() {
        return PostID;
    }

    public void setPostID(String postID) {
        PostID = postID;
    }
}
