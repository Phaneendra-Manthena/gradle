package com.bhargo.user.pojos;

import java.io.Serializable;
import java.util.List;

public class CommentsResponse implements Serializable {

    String Message;
    String Status;
    String SuccessRecordCount;
    List<FaildCommentDetails> FaildCommentDetails;
//    List<TaskCmtDataModel> TaskCmtData;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSuccessRecordCount() {
        return SuccessRecordCount;
    }

    public void setSuccessRecordCount(String successRecordCount) {
        SuccessRecordCount = successRecordCount;
    }

    public List<com.bhargo.user.pojos.FaildCommentDetails> getFaildCommentDetails() {
        return FaildCommentDetails;
    }

    public void setFaildCommentDetails(List<com.bhargo.user.pojos.FaildCommentDetails> faildCommentDetails) {
        FaildCommentDetails = faildCommentDetails;
    }

    //    public List<TaskCmtDataModel> getTaskCmtData() {
//        return TaskCmtData;
//    }
//
//    public void setTaskCmtData(List<TaskCmtDataModel> taskCmtData) {
//        TaskCmtData = taskCmtData;
//    }
}
