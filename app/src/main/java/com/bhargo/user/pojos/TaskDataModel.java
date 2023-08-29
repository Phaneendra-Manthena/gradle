package com.bhargo.user.pojos;

import java.io.Serializable;

public class TaskDataModel implements Serializable {

    String TaskID;
    String TaskName;
    String TaskDesc;
    String PriorityId;
    String StartDate;
    String EndDate;
    String UserId;

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String taskID) {
        TaskID = taskID;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public String getTaskDesc() {
        return TaskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        TaskDesc = taskDesc;
    }

    public String getPriorityId() {
        return PriorityId;
    }

    public void setPriorityId(String priorityId) {
        PriorityId = priorityId;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
