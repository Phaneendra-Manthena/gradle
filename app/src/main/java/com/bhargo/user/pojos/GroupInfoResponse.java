package com.bhargo.user.pojos;

public class GroupInfoResponse {

    public String Status;
    public String Message;
    public ServiceResultModel ServiceResult;

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

    public ServiceResultModel getServiceResult() {
        return ServiceResult;
    }

    public void setServiceResult(ServiceResultModel serviceResult) {
        ServiceResult = serviceResult;
    }
}
