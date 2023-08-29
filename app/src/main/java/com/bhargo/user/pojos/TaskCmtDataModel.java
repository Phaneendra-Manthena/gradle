package com.bhargo.user.pojos;

import java.io.Serializable;

public class TaskCmtDataModel implements Serializable {

    String EmpImPath;
    String EmpName;
    String OrgID;
    String PhoneNo;
    String RNo;
    String TaskComments;
    String TaskID;
    String TaskInDate;
    String TaskName;
    String TaskStatus;
    String TaskStatusID;
    String UserId;

    public String getEmpImPath() {
        return EmpImPath;
    }

    public void setEmpImPath(String empImPath) {
        EmpImPath = empImPath;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public String getOrgID() {
        return OrgID;
    }

    public void setOrgID(String orgID) {
        OrgID = orgID;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getRNo() {
        return RNo;
    }

    public void setRNo(String RNo) {
        this.RNo = RNo;
    }

    public String getTaskComments() {
        return TaskComments;
    }

    public void setTaskComments(String taskComments) {
        TaskComments = taskComments;
    }

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String taskID) {
        TaskID = taskID;
    }

    public String getTaskInDate() {
        return TaskInDate;
    }

    public void setTaskInDate(String taskInDate) {
        TaskInDate = taskInDate;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public String getTaskStatus() {
        return TaskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        TaskStatus = taskStatus;
    }

    public String getTaskStatusID() {
        return TaskStatusID;
    }

    public void setTaskStatusID(String taskStatusID) {
        TaskStatusID = taskStatusID;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
