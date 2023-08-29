package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class MainContainerUISettings implements Serializable {

    int labelFontSize;
    String labelFontColor;
    int valueFontSize;
    String valueFontColor;
    String borderStyle;
    String borderColor;
    String backgroundColor;
    String activeColor;
    String alignment;

    public int getLabelFontSize() {
        return labelFontSize;
    }

    public void setLabelFontSize(int labelFontSize) {
        this.labelFontSize = labelFontSize;
    }

    public String getLabelFontColor() {
        return labelFontColor;
    }

    public void setLabelFontColor(String labelFontColor) {
        this.labelFontColor = labelFontColor;
    }

    public int getValueFontSize() {
        return valueFontSize;
    }

    public void setValueFontSize(int valueFontSize) {
        this.valueFontSize = valueFontSize;
    }

    public String getValueFontColor() {
        return valueFontColor;
    }

    public void setValueFontColor(String valueFontColor) {
        this.valueFontColor = valueFontColor;
    }

    public String getBorderStyle() {
        return borderStyle;
    }

    public void setBorderStyle(String borderStyle) {
        this.borderStyle = borderStyle;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getActiveColor() {
        return activeColor;
    }

    public void setActiveColor(String activeColor) {
        this.activeColor = activeColor;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }
}
