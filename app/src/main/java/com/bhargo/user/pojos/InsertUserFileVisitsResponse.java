package com.bhargo.user.pojos;

import java.util.List;

public class InsertUserFileVisitsResponse {

    String Status;
    String Message;
    List<String> FilesInfo;

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

    public List<String> getFilesInfo() {
        return FilesInfo;
    }

    public void setFilesInfo(List<String> filesInfo) {
        FilesInfo = filesInfo;
    }
}
