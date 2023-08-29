package com.bhargo.user.uisettings.pojos;

import java.io.Serializable;

public class ControlUIProperties implements Serializable {

    String fontColorHex;
    String tintColorHex;
    String fontSize;
    String fontStyle;
    String typeOfWidth;
    String customWidthInDP;
    String typeOfHeight;
    String customHeightInDP;
    String customImageFit;
    String customImageRadius;
    String customImageURL;

    public String getFontColorHex() {
        return fontColorHex;
    }

    public void setFontColorHex(String fontColorHex) {
        this.fontColorHex = fontColorHex;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getTintColorHex() {
        return tintColorHex;
    }

    public void setTintColorHex(String tintColorHex) {
        this.tintColorHex = tintColorHex;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    public String getTypeOfWidth() {
        return typeOfWidth;
    }

    public void setTypeOfWidth(String typeOfWidth) {
        this.typeOfWidth = typeOfWidth;
    }

    public String getCustomWidthInDP() {
        return customWidthInDP;
    }

    public void setCustomWidthInDP(String customWidthInDP) {
        this.customWidthInDP = customWidthInDP;
    }

    public String getTypeOfHeight() {
        return typeOfHeight;
    }

    public void setTypeOfHeight(String typeOfHeight) {
        this.typeOfHeight = typeOfHeight;
    }

    public String getCustomHeightInDP() {
        return customHeightInDP;
    }

    public void setCustomHeightInDP(String customHeightInDP) {
        this.customHeightInDP = customHeightInDP;
    }

    public String getCustomImageFit() {
        return customImageFit;
    }

    public void setCustomImageFit(String customImageFit) {
        this.customImageFit = customImageFit;
    }

    public String getCustomImageRadius() {
        return customImageRadius;
    }

    public void setCustomImageRadius(String customImageRadius) {
        this.customImageRadius = customImageRadius;
    }

    public String getCustomImageURL() {
        return customImageURL;
    }

    public void setCustomImageURL(String customImageURL) {
        this.customImageURL = customImageURL;
    }
}
