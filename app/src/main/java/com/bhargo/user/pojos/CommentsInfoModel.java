package com.bhargo.user.pojos;

import java.io.Serializable;

public class CommentsInfoModel implements Serializable {

    String TaskCommentID;
    String TaskComments;
    String TaskStatusID;
    String TaskStatus;
    String UserId;
    String PostId;
    String LocationCode;
    String DepartmentID;
    String DisplayDate;
    String EmpPostId;
    String EmpName;
    String PostName;
    String DesignationName;
    String DepartmentName;
    String PhoneNo;
    String EmpEmail;
    String ImagePath;
    String TaskId;


//    List<SubLocationsList> SubLocations;

    public String getTaskCommentID() {
        return TaskCommentID;
    }

    public void setTaskCommentID(String taskCommentID) {
        TaskCommentID = taskCommentID;
    }

    public String getTaskComments() {
        return TaskComments;
    }

    public void setTaskComments(String taskComments) {
        TaskComments = taskComments;
    }

    public String getTaskStatusID() {
        return TaskStatusID;
    }

    public void setTaskStatusID(String taskStatusID) {
        TaskStatusID = taskStatusID;
    }

    public String getTaskStatus() {
        return TaskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        TaskStatus = taskStatus;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }

    public String getLocationCode() {
        return LocationCode;
    }

    public void setLocationCode(String locationCode) {
        LocationCode = locationCode;
    }

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String departmentID) {
        DepartmentID = departmentID;
    }

    public String getDisplayDate() {
        return DisplayDate;
    }

    public void setDisplayDate(String displayDate) {
        DisplayDate = displayDate;
    }

    public String getEmpPostId() {
        return EmpPostId;
    }

    public void setEmpPostId(String empPostId) {
        EmpPostId = empPostId;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public String getPostName() {
        return PostName;
    }

    public void setPostName(String postName) {
        PostName = postName;
    }

    public String getDesignationName() {
        return DesignationName;
    }

    public void setDesignationName(String designationName) {
        DesignationName = designationName;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getEmpEmail() {
        return EmpEmail;
    }

    public void setEmpEmail(String empEmail) {
        EmpEmail = empEmail;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String taskId) {
        TaskId = taskId;
    }


    //    public List<SubLocationsList> getSubLocations() {
//        return SubLocations;
//    }
//
//    public void setSubLocations(List<SubLocationsList> subLocations) {
//        SubLocations = subLocations;
//    }

    public class SubLocationsList implements Serializable {

        String ControlName;
        String Value;
        String Text;

        public String getControlName() {
            return ControlName;
        }

        public void setControlName(String controlName) {
            ControlName = controlName;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String value) {
            Value = value;
        }

        public String getText() {
            return Text;
        }

        public void setText(String text) {
            Text = text;
        }
    }
}
