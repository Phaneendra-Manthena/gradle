package com.bhargo.user.pojos;

import java.io.Serializable;

public class TaskDataModelAC implements Serializable {

    String StartDate;
    String EndDate;
    String IsActive;
    String OrgId;
    String Priority;
    String PriorityId;
    String TaskDesc;
    String TaskID;
    String TaskName;
    String UserId;

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

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getPriorityId() {
        return PriorityId;
    }

    public void setPriorityId(String priorityId) {
        PriorityId = priorityId;
    }

    public String getTaskDesc() {
        return TaskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        TaskDesc = taskDesc;
    }

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

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
