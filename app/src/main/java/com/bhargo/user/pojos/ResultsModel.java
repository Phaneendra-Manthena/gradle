package com.bhargo.user.pojos;

public class ResultsModel {

    String AssessmentId;
    String TotalMarks;
    String Marks;
    String Minumum_Marks;
    String Result;

    public String getAssessmentId() {
        return AssessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        AssessmentId = assessmentId;
    }

    public String getTotalMarks() {
        return TotalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        TotalMarks = totalMarks;
    }

    public String getMarks() {
        return Marks;
    }

    public void setMarks(String marks) {
        Marks = marks;
    }

    public String getMinumum_Marks() {
        return Minumum_Marks;
    }

    public void setMinumum_Marks(String minumum_Marks) {
        Minumum_Marks = minumum_Marks;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }
}
