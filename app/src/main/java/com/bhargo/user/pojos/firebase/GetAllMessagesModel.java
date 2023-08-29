package com.bhargo.user.pojos.firebase;

import java.util.List;

public class GetAllMessagesModel {

    String Status;
    String Message;
    List<Notification> TopicsData;

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

    public List<Notification> getTopicsData() {
        return TopicsData;
    }

    public void setTopicsData(List<Notification> topicsData) {
        TopicsData = topicsData;
    }
}
