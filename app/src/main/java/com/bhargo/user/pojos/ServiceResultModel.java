package com.bhargo.user.pojos;

public class ServiceResultModel {

    private String GroupDescription;
    private String GroupMembersCount;
    private String SessionCreatedBy;
    private String SessionCreatedDate;

    public String getGroupDescription() {
        return GroupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        GroupDescription = groupDescription;
    }

    public String getGroupMembersCount() {
        return GroupMembersCount;
    }

    public void setGroupMembersCount(String groupMembersCount) {
        GroupMembersCount = groupMembersCount;
    }

    public String getSessionCreatedBy() {
        return SessionCreatedBy;
    }

    public void setSessionCreatedBy(String sessionCreatedBy) {
        SessionCreatedBy = sessionCreatedBy;
    }

    public String getSessionCreatedDate() {
        return SessionCreatedDate;
    }

    public void setSessionCreatedDate(String sessionCreatedDate) {
        SessionCreatedDate = sessionCreatedDate;
    }
}
