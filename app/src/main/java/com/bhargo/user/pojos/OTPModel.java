package com.bhargo.user.pojos;

import java.util.List;

public class OTPModel {


    String Status;
    String Message;
    String Bearer;
    List<OrgList> OrgList;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getBearer() {
        return Bearer;
    }

    public void setBearer(String bearer) {
        Bearer = bearer;
    }

    public List<OrgList> getOrgList() {
        return OrgList;
    }

    public void setOrgList(List<OrgList> orgList) {
        OrgList = orgList;
    }


}
