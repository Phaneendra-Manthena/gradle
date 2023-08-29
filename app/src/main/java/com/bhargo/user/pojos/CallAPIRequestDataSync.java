package com.bhargo.user.pojos;

import java.io.Serializable;

public class CallAPIRequestDataSync implements Serializable {

    String Sno;
    String APIName;
    public String OrgId;
    public String UserID;
    public String InputJSONData;


    public String getSno() {
        return Sno;
    }

    public void setSno(String sno) {
        Sno = sno;
    }

    public String getAPIName() {
        return APIName;
    }

    public void setAPIName(String APIName) {
        this.APIName = APIName;
    }

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getInputJSONData() {
        return InputJSONData;
    }

    public void setInputJSONData(String inputJSONData) {
        InputJSONData = inputJSONData;
    }
}
