package com.bhargo.user.pojos;

import java.util.List;

public class NotificationDataResponse {

    String Status;
    String Message;
    List<AppDetails> AppDetails;
    public List<DataControls> datacontrol;

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

    public List<com.bhargo.user.pojos.AppDetails> getAppDetails() {
        return AppDetails;
    }

    public void setAppDetails(List<com.bhargo.user.pojos.AppDetails> appDetails) {
        AppDetails = appDetails;
    }

    public List<DataControls> getDatacontrol() {
        return datacontrol;
    }

    public void setDatacontrol(List<DataControls> datacontrol) {
        this.datacontrol = datacontrol;
    }
}
