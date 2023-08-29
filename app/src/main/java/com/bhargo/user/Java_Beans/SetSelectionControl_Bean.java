package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class SetSelectionControl_Bean implements Serializable {

    private String controlName;
    private String parentControlName;
    private String parentControlType;
    private boolean enabled;
    private String selectedId;
    private String selectedValue;
    private boolean expressionExists;
    private boolean globalValueSelected;
    private boolean advancedValueSelected;
    private String controlType;


    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public String getParentControlName() {
        return parentControlName;
    }

    public void setParentControlName(String parentControlName) {
        this.parentControlName = parentControlName;
    }

    public String getParentControlType() {
        return parentControlType;
    }

    public void setParentControlType(String parentControlType) {
        this.parentControlType = parentControlType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(String selectedId) {
        this.selectedId = selectedId;
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

    public boolean isGlobalValueSelected() {
        return globalValueSelected;
    }

    public void setGlobalValueSelected(boolean globalValueSelected) {
        this.globalValueSelected = globalValueSelected;
    }

    public boolean isAdvancedValueSelected() {
        return advancedValueSelected;
    }

    public void setAdvancedValueSelected(boolean advancedValueSelected) {
        this.advancedValueSelected = advancedValueSelected;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }
}
