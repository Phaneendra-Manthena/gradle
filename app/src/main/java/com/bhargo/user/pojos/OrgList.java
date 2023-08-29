package com.bhargo.user.pojos;

public class OrgList {

    String OrgName;
    String OrgID;
    String Status;

    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String orgName) {
        OrgName = orgName;
    }

    public String getOrgID() {
        return OrgID;
    }

    public void setOrgID(String orgID) {
        OrgID = orgID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String toString(){

        return  OrgName;
    }
}