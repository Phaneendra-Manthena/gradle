package com.bhargo.user.pojos;

import java.io.Serializable;

public class FilterSubFormColumns implements Serializable {

    private String SubFormName;
    private String MainFormColumnName;
    private String SubFormColumnName;

    public String getSubFormName() {
        return SubFormName;
    }

    public void setSubFormName(String subFormName) {
        SubFormName = subFormName;
    }

    public String getMainFormColumnName() {
        return MainFormColumnName;
    }

    public void setMainFormColumnName(String mainFormColumnName) {
        MainFormColumnName = mainFormColumnName;
    }

    public String getSubFormColumnName() {
        return SubFormColumnName;
    }

    public void setSubFormColumnName(String subFormColumnName) {
        SubFormColumnName = subFormColumnName;
    }
}
