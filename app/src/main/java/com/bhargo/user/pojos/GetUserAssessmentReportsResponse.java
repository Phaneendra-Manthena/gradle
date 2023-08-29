package com.bhargo.user.pojos;

public class GetUserAssessmentReportsResponse {

    String DistributionId;
    String TopicName;
    String Is_Assessment;
    String StartDateTime;
    String EndDateTime;
    String ExamDuration;
    String NoOfAttempts;
    String NoOfUserAttempts;
    String AssessmentInfo;
    String DownloadCertificate;

    public String getDistributionId() {
        return DistributionId;
    }

    public void setDistributionId(String distributionId) {
        DistributionId = distributionId;
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

    public String getStartDateTime() {
        return StartDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        StartDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return EndDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        EndDateTime = endDateTime;
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

    public String getNoOfUserAttempts() {
        return NoOfUserAttempts;
    }

    public void setNoOfUserAttempts(String noOfUserAttempts) {
        NoOfUserAttempts = noOfUserAttempts;
    }

    public String getAssessmentInfo() {
        return AssessmentInfo;
    }

    public void setAssessmentInfo(String assessmentInfo) {
        AssessmentInfo = assessmentInfo;
    }

    public String getDownloadCertificate() {
        return DownloadCertificate;
    }

    public void setDownloadCertificate(String downloadCertificate) {
        DownloadCertificate = downloadCertificate;
    }
}
