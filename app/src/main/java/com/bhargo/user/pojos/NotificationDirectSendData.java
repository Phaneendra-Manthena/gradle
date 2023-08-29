package com.bhargo.user.pojos;

import java.util.List;

public class NotificationDirectSendData {

    String OrgId;
    String UserId;
    String PageName;
    public List<DataControlsMArr> DataControlsArr;


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

    public List<DataControlsMArr> getDataControlsArr() {
        return DataControlsArr;
    }

    public void setDataControlsArr(List<DataControlsMArr> dataControlsArr) {
        DataControlsArr = dataControlsArr;
    }
}
