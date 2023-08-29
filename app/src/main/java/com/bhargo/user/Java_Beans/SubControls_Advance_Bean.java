package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class SubControls_Advance_Bean implements Serializable {

    private String SubformControlName;
    private String ControlName;
    private boolean enable;
    private String valueType;
    private String ValueExpression;


    public String getControlName() {
        return ControlName;
    }

    public void setControlName(String controlName) {
        ControlName = controlName;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getValueExpression() {
        return ValueExpression;
    }

    public void setValueExpression(String valueExpression) {
        ValueExpression = valueExpression;
    }

    public String getSubformControlName() {
        return SubformControlName;
    }

    public void setSubformControlName(String subformControlName) {
        SubformControlName = subformControlName;
    }
}
