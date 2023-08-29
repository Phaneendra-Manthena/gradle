package com.bhargo.user.pojos;

import java.util.List;

public class GetAllAppModel {

    String Status;
    String Message;
    List<AppDetails> AppDetails;

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

    public List<AppDetails> getAppDetails() {
        return AppDetails;
    }

    public void setAppDetails(List<AppDetails> appDetails) {
        AppDetails = appDetails;
    }


}
