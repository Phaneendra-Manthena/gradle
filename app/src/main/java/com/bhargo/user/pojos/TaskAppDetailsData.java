package com.bhargo.user.pojos;

import java.io.Serializable;

public class TaskAppDetailsData implements Serializable {

    public String OrgId;
    public String UserId;
    public String PageName;

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPageName() {
        return PageName;
    }

    public void setPageName(String pageName) {
        PageName = pageName;
    }
}
