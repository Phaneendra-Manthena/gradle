package com.bhargo.user.pojos;

import java.io.Serializable;
import java.util.List;

public class SendCommentData implements Serializable {

    List<TaskCommentDetailsModel> TaskCommentDetails;

    public List<TaskCommentDetailsModel> getTaskCommentDetails() {
        return TaskCommentDetails;
    }

    public void setTaskCommentDetails(List<TaskCommentDetailsModel> taskCommentDetails) {
        TaskCommentDetails = taskCommentDetails;
    }
}
