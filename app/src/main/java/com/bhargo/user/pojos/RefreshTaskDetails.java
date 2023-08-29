package com.bhargo.user.pojos;

import java.io.Serializable;

public class RefreshTaskDetails implements Serializable {

    String TaskId;
    String TaskDate;

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String taskId) {
        TaskId = taskId;
    }

    public String getTaskDate() {
        return TaskDate;
    }

    public void setTaskDate(String taskDate) {
        TaskDate = taskDate;
    }

    //    public String getTaskVerNo() {
//        return TaskVerNo;
//    }
//
//    public void setTaskVerNo(String taskVerNo) {
//        TaskVerNo = taskVerNo;
//    }
}
