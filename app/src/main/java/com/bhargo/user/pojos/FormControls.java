package com.bhargo.user.pojos;

import java.io.Serializable;
import java.util.List;

public class FormControls implements Serializable {

    private List<Control> mainFormControlsList;
    private List<SubControl> subFormControlsList;

    public List<Control> getMainFormControlsList() {
        return mainFormControlsList;
    }

    public void setMainFormControlsList(List<Control> mainFormControlsList) {
        this.mainFormControlsList = mainFormControlsList;
    }

    public List<SubControl> getSubFormControlsList() {
        return subFormControlsList;
    }

    public void setSubFormControlsList(List<SubControl> subFormControlsList) {
        this.subFormControlsList = subFormControlsList;
    }
}
