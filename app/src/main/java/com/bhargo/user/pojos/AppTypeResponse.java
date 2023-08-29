package com.bhargo.user.pojos;

import java.util.List;

public class AppTypeResponse {

     List<PageNamesList> PageNames;
     String Status;
     String Message;


    public List<PageNamesList> getPageNames() {
        return PageNames;
    }

    public void setPageNames(List<PageNamesList> pageNames) {
        PageNames = pageNames;
    }

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

}
