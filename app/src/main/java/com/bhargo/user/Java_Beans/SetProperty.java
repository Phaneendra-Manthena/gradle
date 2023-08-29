package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class SetProperty implements Serializable {

    private String type;
    private String controlName;
    private String parentControlName;
    private String controlType;
    private List<Param> propertiesList;

    public SetProperty() {
    }

    public SetProperty(String controlName, List<Param> propertiesList, String parentControlName) {
        this.controlName = controlName;
        this.propertiesList = propertiesList;
        this.parentControlName = parentControlName;
    }

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public List<Param> getPropertiesList() {
        return propertiesList;
    }

    public void setPropertiesList(List<Param> propertiesList) {
        this.propertiesList = propertiesList;
    }

    public String getParentControlName() {
        return parentControlName;
    }

    public void setParentControlName(String parentControlName) {
        this.parentControlName = parentControlName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }
}
