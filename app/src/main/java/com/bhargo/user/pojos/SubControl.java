package com.bhargo.user.pojos;

import java.io.Serializable;
import java.util.List;

public class SubControl implements Serializable {

    private String subFormName;
    private List<Control> subformControlsList;

    public String getSubFormName() {
        return subFormName;
    }

    public void setSubFormName(String subFormName) {
        this.subFormName = subFormName;
    }

    public List<Control> getSubformControlsList() {
        return subformControlsList;
    }

    public void setSubformControlsList(List<Control> subformControlsList) {
        this.subformControlsList = subformControlsList;
    }
}
