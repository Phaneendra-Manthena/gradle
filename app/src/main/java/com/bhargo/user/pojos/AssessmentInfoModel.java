package com.bhargo.user.pojos;

import java.io.Serializable;

public class AssessmentInfoModel implements Serializable {

    String UserId;
    String AssessmentId;
    String Assessment;
    String AssessmentDate;
    String NoOfMinutsTimeSpent;
    String TotalMarks;
    String MarksObtained;
    String Results;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAssessmentId() {
        return AssessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        AssessmentId = assessmentId;
    }

    public String getAssessment() {
        return Assessment;
    }

    public void setAssessment(String assessment) {
        Assessment = assessment;
    }

    public String getAssessmentDate() {
        return AssessmentDate;
    }

    public void setAssessmentDate(String assessmentDate) {
        AssessmentDate = assessmentDate;
    }

    public String getNoOfMinutsTimeSpent() {
        return NoOfMinutsTimeSpent;
    }

    public void setNoOfMinutsTimeSpent(String noOfMinutsTimeSpent) {
        NoOfMinutsTimeSpent = noOfMinutsTimeSpent;
    }

    public String getTotalMarks() {
        return TotalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        TotalMarks = totalMarks;
    }

    public String getMarksObtained() {
        return MarksObtained;
    }

    public void setMarksObtained(String marksObtained) {
        MarksObtained = marksObtained;
    }

    public String getResults() {
        return Results;
    }

    public void setResults(String results) {
        Results = results;
    }
}
