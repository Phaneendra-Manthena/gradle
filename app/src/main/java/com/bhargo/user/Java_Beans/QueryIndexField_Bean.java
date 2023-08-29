package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class QueryIndexField_Bean implements Serializable {

    private String displayName;
    private String controlName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }
}
