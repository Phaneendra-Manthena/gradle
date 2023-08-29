package com.bhargo.user.pojos;

public class GetDesignDetailsModel {

    String Status;
    String Message;
    String DesignFormat;
    String XMLFilePath;

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

    public String getDesignFormat() {
        return DesignFormat;
    }

    public void setDesignFormat(String designFormat) {
        DesignFormat = designFormat;
    }

    public String getXMLFilePath() {
        return XMLFilePath;
    }

    public void setXMLFilePath(String XMLFilePath) {
        this.XMLFilePath = XMLFilePath;
    }
}
