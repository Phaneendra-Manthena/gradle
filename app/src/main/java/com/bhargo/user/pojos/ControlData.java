package com.bhargo.user.pojos;

import java.io.Serializable;

public class ControlData implements Serializable {


    String controlValue;
    String controlType;
    String controlName;

    public String getGpsCoordinates() {
        return gpsCoordinates;
    }

    public void setGpsCoordinates(String gpsCoordinates) {
        this.gpsCoordinates = gpsCoordinates;
    }

    public String getGpsType() {
        return gpsType;
    }

    public void setGpsType(String gpsType) {
        this.gpsType = gpsType;
    }

    String gpsCoordinates,gpsType;

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public String getControlValue() {
        return controlValue;
    }

    public void setControlValue(String controlValue) {
        this.controlValue = controlValue;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }


}
