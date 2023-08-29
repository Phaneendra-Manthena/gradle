package com.bhargo.user.pojos;

public class AssessmentAnswer {


    private String QuestionID;
    private String AnswerId;
    private String Answer;
    private String Marks;

    public String getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(String questionID) {
        QuestionID = questionID;
    }

    public String getAnswerId() {

        return AnswerId;
    }

    public void setAnswerId(String answerId) {
        AnswerId = answerId;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getMarks() {
        return Marks;
    }

    public void setMarks(String marks) {
        Marks = marks;
    }
}
