package com.bhargo.user.pojos;

import java.io.Serializable;

public class Control implements Serializable {

    private String controlName;
    private String controlType;
    private boolean enableImageWithGps;

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public boolean isEnableImageWithGps() {
        return enableImageWithGps;
    }

    public void setEnableImageWithGps(boolean enableImageWithGps) {
        this.enableImageWithGps = enableImageWithGps;
    }
}
