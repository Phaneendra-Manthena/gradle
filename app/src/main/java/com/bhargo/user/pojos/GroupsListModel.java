package com.bhargo.user.pojos;

public class GroupsListModel {

    String UserID;
    String GroupID;
    String GroupName;
//   String GroupID;
//    String GroupName;
//    String CreatedBy;
//    String GroupType;
//    String TrDate;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

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
}
