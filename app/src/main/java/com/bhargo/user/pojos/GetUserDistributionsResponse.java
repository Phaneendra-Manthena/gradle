package com.bhargo.user.pojos;

import java.io.Serializable;

public class GetUserDistributionsResponse implements Serializable {

    String SNO;
    String DistributionId;
    String Organization_Id;
    String User_Id;
    String TopicName;
    String Is_Assessment;
    String StartDate;
    String StartTime;
    String EndDate;
    String EndTime;
    String ExamDuration;
    String NoOfAttempts;
    String DistributionDate;
    String StartDisplayDate;
    String StartDisplayTime;
    String EndDisplayDate;
    String EndDisplayTime;
    String NoOfUserAttempts;
    String FilesInfo;

    private String tQuestions;
    private String hQuestions;
    private String Is_Compexcity;
    private String lMarks;
    private String hMarks;
    private String mQuestions;
    private String Is_AllowComplexity;
    private String tMarks;
    private String AssessmentInfo;
    private String mMarks;
    private String lQuestions;
    private String DistributionStatus;
    private String PostID;
    private String TopicCoverImage;
    private String TopicDescription;
    private String FileCoverImage;
    private String TaskUpdationDate;



//    List<FilesInfoModel> FilesInfo;
//    List<QuestionsModel> Questions;

    public String getSNO() {
        return SNO;
    }

    public void setSNO(String SNO) {
        this.SNO = SNO;
    }

    public String getDistributionId() {
        return DistributionId;
    }

    public void setDistributionId(String distributionId) {
        DistributionId = distributionId;
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

    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String topicName) {
        TopicName = topicName;
    }

    public String getIs_Assessment() {
        return Is_Assessment;
    }

    public void setIs_Assessment(String is_Assessment) {
        Is_Assessment = is_Assessment;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getExamDuration() {
        return ExamDuration;
    }

    public void setExamDuration(String examDuration) {
        ExamDuration = examDuration;
    }

    public String getNoOfAttempts() {
        return NoOfAttempts;
    }

    public void setNoOfAttempts(String noOfAttempts) {
        NoOfAttempts = noOfAttempts;
    }

    public String getDistributionDate() {
        return DistributionDate;
    }

    public void setDistributionDate(String distributionDate) {
        DistributionDate = distributionDate;
    }

    public String getStartDisplayDate() {
        return StartDisplayDate;
    }

    public void setStartDisplayDate(String startDisplayDate) {
        StartDisplayDate = startDisplayDate;
    }

    public String getStartDisplayTime() {
        return StartDisplayTime;
    }

    public void setStartDisplayTime(String startDisplayTime) {
        StartDisplayTime = startDisplayTime;
    }

    public String getEndDisplayDate() {
        return EndDisplayDate;
    }

    public void setEndDisplayDate(String endDisplayDate) {
        EndDisplayDate = endDisplayDate;
    }

    public String getEndDisplayTime() {
        return EndDisplayTime;
    }

    public void setEndDisplayTime(String endDisplayTime) {
        EndDisplayTime = endDisplayTime;
    }

    public String getNoOfUserAttempts() {
        return NoOfUserAttempts;
    }

    public void setNoOfUserAttempts(String noOfUserAttempts) {
        NoOfUserAttempts = noOfUserAttempts;
    }

//    public List<FilesInfoModel> getFilesInfo() {
//        return FilesInfo;
//    }
//
//    public void setFilesInfo(List<FilesInfoModel> filesInfo) {
//        FilesInfo = filesInfo;
//    }

    public String getFilesInfo() {
        return FilesInfo;
    }

    public void setFilesInfo(String filesInfo) {
        FilesInfo = filesInfo;
    }

    public String gettQuestions() {
        return tQuestions;
    }

    public void settQuestions(String tQuestions) {
        this.tQuestions = tQuestions;
    }

    public String gethQuestions() {
        return hQuestions;
    }

    public void sethQuestions(String hQuestions) {
        this.hQuestions = hQuestions;
    }

    public String getIs_Compexcity() {
        return Is_Compexcity;
    }

    public void setIs_Compexcity(String is_Compexcity) {
        Is_Compexcity = is_Compexcity;
    }

    public String getlMarks() {
        return lMarks;
    }

    public void setlMarks(String lMarks) {
        this.lMarks = lMarks;
    }

    public String gethMarks() {
        return hMarks;
    }

    public void sethMarks(String hMarks) {
        this.hMarks = hMarks;
    }

    public String getmQuestions() {
        return mQuestions;
    }

    public void setmQuestions(String mQuestions) {
        this.mQuestions = mQuestions;
    }

    public String getIs_AllowComplexity() {
        return Is_AllowComplexity;
    }

    public void setIs_AllowComplexity(String is_AllowComplexity) {
        Is_AllowComplexity = is_AllowComplexity;
    }

    public String gettMarks() {
        return tMarks;
    }

    public void settMarks(String tMarks) {
        this.tMarks = tMarks;
    }

    public String getAssessmentInfo() {
        return AssessmentInfo;
    }

    public void setAssessmentInfo(String assessmentInfo) {
        AssessmentInfo = assessmentInfo;
    }

    public String getmMarks() {
        return mMarks;
    }

    public void setmMarks(String mMarks) {
        this.mMarks = mMarks;
    }

    public String getlQuestions() {
        return lQuestions;
    }

    public void setlQuestions(String lQuestions) {
        this.lQuestions = lQuestions;
    }

    public String getDistributionStatus() {
        return DistributionStatus;
    }

    public void setDistributionStatus(String distributionStatus) {
        DistributionStatus = distributionStatus;
    }

    public String getPostID() {
        return PostID;
    }

    public void setPostID(String postID) {
        PostID = postID;
    }

    public String getTopicCoverImage() {
        return TopicCoverImage;
    }

    public void setTopicCoverImage(String topicCoverImage) {
        TopicCoverImage = topicCoverImage;
    }

    public String getTopicDescription() {
        return TopicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        TopicDescription = topicDescription;
    }

    public String getFileCoverImage() {
        return FileCoverImage;
    }

    public void setFileCoverImage(String fileCoverImage) {
        FileCoverImage = fileCoverImage;
    }

    public String getTaskUpdationDate() {
        return TaskUpdationDate;
    }

    public void setTaskUpdationDate(String taskUpdationDate) {
        TaskUpdationDate = taskUpdationDate;
    }
}
