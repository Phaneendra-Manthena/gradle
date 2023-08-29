package com.bhargo.user.pojos;

import java.io.Serializable;

public class AnswersModel implements Serializable {

    public String AnswerID;
    public String Answer;

    public String getAnswerID() {
        return AnswerID;
    }

    public void setAnswerID(String answerID) {
        AnswerID = answerID;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
