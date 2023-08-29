package com.bhargo.user.pojos;

public class GetTaskTransIDSendData {

    String UserId;
    String OrgId;
    String TaskId;

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

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String taskId) {
        TaskId = taskId;
    }
}
