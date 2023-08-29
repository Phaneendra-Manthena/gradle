package com.bhargo.user.pojos;

import java.util.List;

public class GetGroupListResponse {

    String Status;
    String Message;
    List<GroupsListModel> GroupsList;

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

    public List<GroupsListModel> getGroupsList() {
        return GroupsList;
    }

    public void setGroupsList(List<GroupsListModel> groupsList) {
        GroupsList = groupsList;
    }
}
