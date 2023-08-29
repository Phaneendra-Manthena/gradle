package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class RemoveControlItem implements Serializable {
    private String controlName;
    private String controlType;
    private String controlValue;
    private boolean controlExpression;
    private String controlRowType;
    private String controlRowValue;
    private boolean controlRowExpression;

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

    public String getControlValue() {
        return controlValue;
    }

    public void setControlValue(String controlValue) {
        this.controlValue = controlValue;
    }

    public boolean getControlExpression() {
        return controlExpression;
    }

    public void setControlExpression(boolean controlExpression) {
        this.controlExpression = controlExpression;
    }

    public String getControlRowType() {
        return controlRowType;
    }

    public void setControlRowType(String controlRowType) {
        this.controlRowType = controlRowType;
    }

    public String getControlRowValue() {
        return controlRowValue;
    }

    public void setControlRowValue(String controlRowValue) {
        this.controlRowValue = controlRowValue;
    }

    public boolean getControlRowExpression() {
        return controlRowExpression;
    }

    public void setControlRowExpression(boolean controlRowExpression) {
        this.controlRowExpression = controlRowExpression;
    }
}
