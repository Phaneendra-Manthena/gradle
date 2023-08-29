package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class SubContainerHeaderSettings implements Serializable {

    public int width;
    public int height;
    public String textStyle;
    public int textSize;
    public String textColor;
    public String alignment;
    public String backgroundcolorType;
    public String backgroundcolorOne;
    public String backgroundcolorTwo;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(String textStyle) {
        this.textStyle = textStyle;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public String getBackgroundcolorType() {
        return backgroundcolorType;
    }

    public void setBackgroundcolorType(String backgroundcolorType) {
        this.backgroundcolorType = backgroundcolorType;
    }

    public String getBackgroundcolorOne() {
        return backgroundcolorOne;
    }

    public void setBackgroundcolorOne(String backgroundcolorOne) {
        this.backgroundcolorOne = backgroundcolorOne;
    }

    public String getBackgroundcolorTwo() {
        return backgroundcolorTwo;
    }

    public void setBackgroundcolorTwo(String backgroundcolorTwo) {
        this.backgroundcolorTwo = backgroundcolorTwo;
    }
}
