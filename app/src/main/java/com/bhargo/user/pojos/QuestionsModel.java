package com.bhargo.user.pojos;

import java.io.Serializable;


public class QuestionsModel implements Serializable {


    String QuestionID;
    String Question;
    String Marks;
    String QuestionComplexityId;
    String QuestionComplexity;
    String QuestionTypeId;
    String QuestionType;

    String Answers;
    String NoOfUserAttempts;
    String ParagraphImageId;
    String Explanation;
    String QuestionParagraphId;
    String QuestionParagraph;
    String MatchTheFollowing;


    public String getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(String questionID) {
        QuestionID = questionID;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getMarks() {
        return Marks;
    }

    public void setMarks(String marks) {
        Marks = marks;
    }

    public String getQuestionComplexityId() {
        return QuestionComplexityId;
    }

    public void setQuestionComplexityId(String questionComplexityId) {
        QuestionComplexityId = questionComplexityId;
    }

    public String getQuestionComplexity() {
        return QuestionComplexity;
    }

    public void setQuestionComplexity(String questionComplexity) {
        QuestionComplexity = questionComplexity;
    }

    public String getQuestionTypeId() {
        return QuestionTypeId;
    }

    public void setQuestionTypeId(String questionTypeId) {
        QuestionTypeId = questionTypeId;
    }

    public String getQuestionType() {
        return QuestionType;
    }

    public void setQuestionType(String questionType) {
        QuestionType = questionType;
    }

    public String getAnswers() {
        return Answers;
    }

    public void setAnswers(String answers) {
        Answers = answers;
    }

    public String getNoOfUserAttempts() {
        return NoOfUserAttempts;
    }

    public void setNoOfUserAttempts(String noOfUserAttempts) {
        NoOfUserAttempts = noOfUserAttempts;
    }

    public String getQuestionParagraphId() {
        return QuestionParagraphId;
    }

    public void setQuestionParagraphId(String questionParagraphId) {
        QuestionParagraphId = questionParagraphId;
    }

    public String getParagraphImageId() {
        return ParagraphImageId;
    }

    public void setParagraphImageId(String paragraphImageId) {
        ParagraphImageId = paragraphImageId;
    }

    public String getExplanation() {
        return Explanation;
    }

    public void setExplanation(String explanation) {
        Explanation = explanation;
    }

    public String getQuestionParagraph() {
        return QuestionParagraph;
    }

    public void setQuestionParagraph(String questionParagraph) {
        QuestionParagraph = questionParagraph;
    }

    public String getMatchTheFollowing() {
        return MatchTheFollowing;
    }

    public void setMatchTheFollowing(String matchTheFollowing) {
        MatchTheFollowing = matchTheFollowing;
    }
}
