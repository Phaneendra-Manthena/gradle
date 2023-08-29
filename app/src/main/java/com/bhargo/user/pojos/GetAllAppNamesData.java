package com.bhargo.user.pojos;

public class GetAllAppNamesData {

    String UserId;
    String OrgId;
    String AppType;


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    public String getAppType() {
        return AppType;
    }

    public void setAppType(String appType) {
        AppType = appType;
    }
}
