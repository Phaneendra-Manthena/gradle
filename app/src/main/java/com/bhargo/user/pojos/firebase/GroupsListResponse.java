package com.bhargo.user.pojos.firebase;

import java.util.List;

public class GroupsListResponse {

    private String Status;
    private String Message;
    private String SessionID;
    private List<Group> GroupData;
    private List<Group> UserGroupsList;


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<Group> getGroupData() {
        return GroupData;
    }

    public void setGroupData(List<Group> groupData) {
        GroupData = groupData;
    }

    public String getSessionID() {
        return SessionID;
    }

    public void setSessionID(String sessionID) {
        SessionID = sessionID;
    }

    public List<Group> getUserGroupsList() {
        return UserGroupsList;
    }

    public void setUserGroupsList(List<Group> userGroupsList) {
        UserGroupsList = userGroupsList;
    }
}
