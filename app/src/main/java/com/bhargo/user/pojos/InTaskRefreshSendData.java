package com.bhargo.user.pojos;

import java.util.List;

public class InTaskRefreshSendData {

    String DBNAME;
    String UserId;
    String PostId;
    List<RefreshTaskDetails> InOutTaskResponseList;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }


    public void setDBNAME(String DBNAME) {
        this.DBNAME = DBNAME;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }

    public String getDBNAME() {
        return DBNAME;
    }

    public List<RefreshTaskDetails> getInOutTaskResponseList() {
        return InOutTaskResponseList;
    }

    public void setInOutTaskResponseList(List<RefreshTaskDetails> inOutTaskResponseList) {
        InOutTaskResponseList = inOutTaskResponseList;
    }

    //    public List<RefreshTaskDetails> getTaskDts() {
//        return TaskDts;
//    }
//
//    public void setTaskDts(List<RefreshTaskDetails> taskDts) {
//        TaskDts = taskDts;
//    }

    //    public List<RefreshTaskDetails> getTaskDts() {
//        return TaskDts;
//    }
//
//    public void setTaskDts(List<RefreshTaskDetails> taskDts) {
//        TaskDts = taskDts;
//    }
}
