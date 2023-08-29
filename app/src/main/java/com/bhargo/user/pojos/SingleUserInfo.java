package com.bhargo.user.pojos;

import java.io.Serializable;

public class SingleUserInfo implements Serializable {

    String TaskID;
    String EmpID;
    String PostID;
    String IsSelfComment;

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String taskID) {
        TaskID = taskID;
    }

    public String getEmpID() {
        return EmpID;
    }

    public void setEmpID(String empID) {
        EmpID = empID;
    }

    public String getPostID() {
        return PostID;
    }

    public void setPostID(String postID) {
        PostID = postID;
    }

    public String getIsSelfComment() {
        return IsSelfComment;
    }

    public void setIsSelfComment(String isSelfComment) {
        IsSelfComment = isSelfComment;
    }
}
