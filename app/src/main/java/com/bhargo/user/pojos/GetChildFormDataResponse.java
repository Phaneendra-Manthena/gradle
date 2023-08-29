package com.bhargo.user.pojos;

import java.util.List;

public class GetChildFormDataResponse {
    String Status;
    String Message;
    List<AppDetails> output;

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

    public List<AppDetails> getOutput() {
        return output;
    }

    public void setOutput(List<AppDetails> output) {
        this.output = output;
    }
}
