package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class PopUpManagementAction implements Serializable {

    private String popUpControlType;
    private String selectedSection;

    public String getPopUpControlType() {
        return popUpControlType;
    }

    public void setPopUpControlType(String popUpControlType) {
        this.popUpControlType = popUpControlType;
    }

    public String getSelectedSection() {
        return selectedSection;
    }

    public void setSelectedSection(String selectedSection) {
        this.selectedSection = selectedSection;
    }
}
