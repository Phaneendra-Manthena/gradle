package com.bhargo.user.pojos.firebase;


import java.util.List;

public class UsersAndGroupsList {

    private String Status;

    private String Message;

    private List<UserList> resultlist;

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

    public List<UserList> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<UserList> resultlist) {
        this.resultlist = resultlist;
    }
}
