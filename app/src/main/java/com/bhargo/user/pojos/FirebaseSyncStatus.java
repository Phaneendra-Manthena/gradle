package com.bhargo.user.pojos;

import com.google.firebase.database.PropertyName;

public class FirebaseSyncStatus {

    private boolean DataControls;
    private boolean ELearning;
    private boolean Logout;
    private boolean Apps;
    private boolean Refresh;
    private String Mobile;


    public FirebaseSyncStatus() {
    }

    public boolean isDataControls() {
        return DataControls;
    }

    public void setDataControls(boolean dataControls) {
        DataControls = dataControls;
    }

    public boolean isELearning() {
        return ELearning;
    }

    public void setELearning(boolean ELearning) {
        this.ELearning = ELearning;
    }

    public boolean isLogout() {
        return Logout;
    }

    public void setLogout(boolean logout) {
        Logout = logout;
    }

    public boolean isApps() {
        return Apps;
    }

    public void setApps(boolean apps) {
        Apps = apps;
    }

    public boolean isRefresh() {
        return Refresh;
    }

    public void setRefresh(boolean refresh) {
        Refresh = refresh;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }
}
