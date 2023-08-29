package com.bhargo.user.pojos;

public class InsertAssessmentDetailsData {

    String UserId;
    String PostID;
    String DBNAME;
    String DistributionId;
    String DeviceID;
    String MobileDate;
    String hQuestions;
    String mQuestions;
    String lQuestions;
    String tQuestions;
    String IsComplexity;
    String VersionNo;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

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

    public String gethQuestions() {
        return hQuestions;
    }

    public void sethQuestions(String hQuestions) {
        this.hQuestions = hQuestions;
    }

    public String getmQuestions() {
        return mQuestions;
    }

    public void setmQuestions(String mQuestions) {
        this.mQuestions = mQuestions;
    }

    public String getlQuestions() {
        return lQuestions;
    }

    public void setlQuestions(String lQuestions) {
        this.lQuestions = lQuestions;
    }

    public String gettQuestions() {
        return tQuestions;
    }

    public void settQuestions(String tQuestions) {
        this.tQuestions = tQuestions;
    }

    public String getIsComplexity() {
        return IsComplexity;
    }

    public void setIsComplexity(String isComplexity) {
        IsComplexity = isComplexity;
    }

    public String getPostID() {
        return PostID;
    }

    public void setPostID(String postID) {
        PostID = postID;
    }

    public String getVersionNo() {
        return VersionNo;
    }

    public void setVersionNo(String versionNo) {
        VersionNo = versionNo;
    }
}
