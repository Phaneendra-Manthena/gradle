package com.bhargo.user.Java_Beans;


import java.io.Serializable;

public class GridColumnSettings implements Serializable {
    /*ControlId>202212261846521854e942e2d586630</ControlId>
<ControlName>grid_control_text_input</ControlName>
<ControlWidth>10</ControlWidth>
<ControlColor>#ffffff</ControlColor>
<EnabelSorting>false</EnabelSorting>*/
    private String controlWidth = "120";
    private String controlColor = "#ffffff";
    private String controlName, controlId;
    private boolean enableSorting;

    public String getControlWidth() {
        return controlWidth;
    }

    public void setControlWidth(String controlWidth) {
        this.controlWidth = controlWidth;
    }

    public String getControlColor() {
        return controlColor;
    }

    public void setControlColor(String controlColor) {
        this.controlColor = controlColor;
    }

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public String getControlId() {
        return controlId;
    }

    public void setControlId(String controlId) {
        this.controlId = controlId;
    }

    public boolean isEnableSorting() {
        return enableSorting;
    }

    public void setEnableSorting(boolean enableSorting) {
        this.enableSorting = enableSorting;
    }


}