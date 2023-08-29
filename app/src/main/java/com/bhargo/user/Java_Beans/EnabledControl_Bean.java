package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class EnabledControl_Bean implements Serializable {

    private String controlName;
    private String selectedValue;
    private boolean expressionExists;
    private String valueFieldsMappedType;
    private String valueFieldsMappedId;

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public boolean isExpressionExists() {
        return expressionExists;
    }

    public void setExpressionExists(boolean expressionExists) {
        this.expressionExists = expressionExists;
    }

    public String getValueFieldsMappedType() {
        return valueFieldsMappedType;
    }

    public void setValueFieldsMappedType(String valueFieldsMappedType) {
        this.valueFieldsMappedType = valueFieldsMappedType;
    }

    public String getValueFieldsMappedId() {
        return valueFieldsMappedId;
    }

    public void setValueFieldsMappedId(String valueFieldsMappedId) {
        this.valueFieldsMappedId = valueFieldsMappedId;
    }

}
