package com.bhargo.user.pojos;

import java.io.Serializable;

public class TaskDepGroupDataModel implements Serializable {

    String TaskGroupSharingID;
    String GroupId;
    String GroupName;

//    String GroupName;
//    String InfoID;
    boolean isSelected;

//    public String getGroupName() {
//        return GroupName;
//    }
//
//    public void setGroupName(String groupName) {
//        GroupName = groupName;
//    }
//
//    public String getInfoID() {
//        return InfoID;
//    }
//
//    public void setInfoID(String infoID) {
//        InfoID = infoID;
//    }


    public String getTaskGroupSharingID() {
        return TaskGroupSharingID;
    }

    public void setTaskGroupSharingID(String taskGroupSharingID) {
        TaskGroupSharingID = taskGroupSharingID;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
