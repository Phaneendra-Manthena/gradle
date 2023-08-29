package com.bhargo.user.pojos;

import java.io.Serializable;
import java.util.List;

public class InsertAssessmentDetailsResponse implements Serializable {


    String Status;
    String Message;
    String AssessmentId;
    String NoOfUserAttempts = "0";
    String NoOfAttempts;
    List<QuestionsModel> Questions;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getAssessmentId() {
        return AssessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        AssessmentId = assessmentId;
    }

    public String getNoOfUserAttempts() {
        return NoOfUserAttempts;
    }

    public void setNoOfUserAttempts(String noOfUserAttempts) {
        NoOfUserAttempts = noOfUserAttempts;
    }

    public String getNoOfAttempts() {
        return NoOfAttempts;
    }

    public void setNoOfAttempts(String noOfAttempts) {
        NoOfAttempts = noOfAttempts;
    }

    public List<QuestionsModel> getQuestions() {
        return Questions;
    }

    public void setQuestions(List<QuestionsModel> questions) {
        Questions = questions;
    }
}
