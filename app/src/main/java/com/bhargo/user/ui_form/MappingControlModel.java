package com.bhargo.user.ui_form;

import java.io.Serializable;

public class MappingControlModel implements Serializable {
//bhavani
    String ControlName;
    String ControlType;

    public MappingControlModel() {
    }

    public MappingControlModel(String controlName, String controlType) {
        ControlName = controlName;
        ControlType = controlType;
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
}
