package com.bhargo.user.pojos;

public class AppDetailsAdvancedAction {

    String OrgId;
    String UserId;
    String PageName;
    String Action;
    String Delete;
    String TransID;
    boolean subFormInMainForm;

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

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getDelete() {
        return Delete;
    }

    public void setDelete(String delete) {
        Delete = delete;
    }

    public String getTransID() {
        return TransID;
    }

    public void setTransID(String transID) {
        TransID = transID;
    }

    public boolean isSubFormInMainForm() {
        return subFormInMainForm;
    }

    public void setSubFormInMainForm(boolean subFormInMainForm) {
        this.subFormInMainForm = subFormInMainForm;
    }
}
