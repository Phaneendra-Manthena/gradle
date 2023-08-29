package com.bhargo.user.pojos;

public class GetTasksInvDtsData {
    String UserId;
    String OrgId;
    String TaskTransID;
    String TaskId;
    String CmtCount;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    public String getTaskTransID() {
        return TaskTransID;
    }

    public void setTaskTransID(String taskTransID) {
        TaskTransID = taskTransID;
    }

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String taskId) {
        TaskId = taskId;
    }

    public String getCmtCount() {
        return CmtCount;
    }

    public void setCmtCount(String cmtCount) {
        CmtCount = cmtCount;
    }
}
