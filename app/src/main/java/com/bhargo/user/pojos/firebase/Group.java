package com.bhargo.user.pojos.firebase;

public class Group {

    private String GroupID;
    private String GroupName;
    private String GroupIcon;
    private String GroupType;
    private String DependentID;
    private String UserType;
    private String UserID;
    private String Read;
    private String Write;
    private String Status;

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String groupID) {
        GroupID = groupID;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getGroupIcon() {
        return GroupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        GroupIcon = groupIcon;
    }

    public String getGroupType() {
        return GroupType;
    }

    public void setGroupType(String groupType) {
        GroupType = groupType;
    }

    @Override
    public String toString() {

        return GroupName;
    }

    public String getDependentID() {
        return DependentID;
    }

    public void setDependentID(String dependentID) {
        DependentID = dependentID;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getRead() {
        return Read;
    }

    public void setRead(String read) {
        Read = read;
    }

    public String getWrite() {
        return Write;
    }

    public void setWrite(String write) {
        Write = write;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
