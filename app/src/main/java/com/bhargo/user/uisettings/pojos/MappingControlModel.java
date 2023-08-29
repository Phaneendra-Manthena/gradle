package com.bhargo.user.uisettings.pojos;

import java.io.Serializable;

public class MappingControlModel implements Serializable {

    String ControlName;
    String ControlType;
    String displayName;
    boolean isControlFromToolBar = false;
    ControlUIProperties controlUIProperties;

    public MappingControlModel() {
    }



    public String getControlName() {
        return ControlName;
    }

    public void setControlName(String controlName) {
        ControlName = controlName;
    }

    public String getControlType() {
        return ControlType;
    }

    public void setControlType(String controlType) {
        ControlType = controlType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public boolean isControlFromToolBar() {
        return isControlFromToolBar;
    }

    public void setControlFromToolBar(boolean controlFromToolBar) {
        isControlFromToolBar = controlFromToolBar;
    }

    public ControlUIProperties getControlUIProperties() {
        return controlUIProperties;
    }

    public void setControlUIProperties(ControlUIProperties controlUIProperties) {
        this.controlUIProperties = controlUIProperties;
    }
}
