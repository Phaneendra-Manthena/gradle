package com.bhargo.user.uisettings.pojos;

import java.io.Serializable;

public class UILayoutProperties implements Serializable {

    int height;
    int width;
    int marginLeft;
    int marginRight;
    int marginTop;
    int marginBottom;
    int PaddingLeft;
    int PaddingTop;
    int PaddingRight;
    int PaddingBottom;
    String aliasName;
    String orientation;
    boolean overflow;
    String insideAlignment;
    String controlVerticalAlignment;
    String controlHorizontalAlignment;
    String backgroundType;
    String backGroundImage;
    String colorType;
    String backGroundColorHex;
    String backGroundColor;
    String gradientOneColorHex;
    String gradientOneColor;
    String gradientTwoColorHex;
    String gradientTwoColor;
    String borderColorHex;
    String borderColor;
    String borderRadius;
    String borderStroke;
    String gradientType;
    String wrap_or_dp;
    String widthFixedVariable;
    boolean isHideDefaultToolbar;

    int layoutWidthInPixel;
    int layoutHeightInPixel;

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    public int getPaddingLeft() {
        return PaddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        PaddingLeft = paddingLeft;
    }

    public int getPaddingTop() {
        return PaddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        PaddingTop = paddingTop;
    }

    public int getPaddingRight() {
        return PaddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        PaddingRight = paddingRight;
    }

    public int getPaddingBottom() {
        return PaddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        PaddingBottom = paddingBottom;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public boolean isOverflow() {
        return overflow;
    }

    public void setOverflow(boolean overflow) {
        this.overflow = overflow;
    }

    public String getInsideAlignment() {
        return insideAlignment;
    }

    public void setInsideAlignment(String insideAlignment) {
        this.insideAlignment = insideAlignment;
    }

    public String getBackgroundType() {
        return backgroundType;
    }

    public void setBackgroundType(String backgroundType) {
        this.backgroundType = backgroundType;
    }

    public String getBackGroundImage() {
        return backGroundImage;
    }

    public void setBackGroundImage(String backGroundImage) {
        this.backGroundImage = backGroundImage;
    }

    public String getBackGroundColorHex() {
        return backGroundColorHex;
    }

    public void setBackGroundColorHex(String backGroundColorHex) {
        this.backGroundColorHex = backGroundColorHex;
    }

    public String getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(String backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(String borderRadius) {
        this.borderRadius = borderRadius;
    }

    public String getBorderColorHex() {
        return borderColorHex;
    }

    public void setBorderColorHex(String borderColorHex) {
        this.borderColorHex = borderColorHex;
    }

    public String getBorderStroke() {
        return borderStroke;
    }

    public void setBorderStroke(String borderStroke) {
        this.borderStroke = borderStroke;
    }

    public String getGradientType() {
        return gradientType;
    }

    public void setGradientType(String gradientType) {
        this.gradientType = gradientType;
    }

    public String getColorType() {
        return colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    public String getGradientOneColorHex() {
        return gradientOneColorHex;
    }

    public void setGradientOneColorHex(String gradientOneColorHex) {
        this.gradientOneColorHex = gradientOneColorHex;
    }

    public String getGradientOneColor() {
        return gradientOneColor;
    }

    public void setGradientOneColor(String gradientOneColor) {
        this.gradientOneColor = gradientOneColor;
    }

    public String getGradientTwoColorHex() {
        return gradientTwoColorHex;
    }

    public void setGradientTwoColorHex(String gradientTwoColorHex) {
        this.gradientTwoColorHex = gradientTwoColorHex;
    }

    public String getGradientTwoColor() {
        return gradientTwoColor;
    }

    public void setGradientTwoColor(String gradientTwoColor) {
        this.gradientTwoColor = gradientTwoColor;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getWrap_or_dp() {
        return wrap_or_dp;
    }

    public void setWrap_or_dp(String wrap_or_dp) {
        this.wrap_or_dp = wrap_or_dp;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }


    public String getControlVerticalAlignment() {
        return controlVerticalAlignment;
    }

    public void setControlVerticalAlignment(String controlVerticalAlignment) {
        this.controlVerticalAlignment = controlVerticalAlignment;
    }

    public String getControlHorizontalAlignment() {
        return controlHorizontalAlignment;
    }

    public void setControlHorizontalAlignment(String controlHorizontalAlignment) {
        this.controlHorizontalAlignment = controlHorizontalAlignment;
    }

    public String getWidthFixedVariable() {
        return widthFixedVariable;
    }

    public void setWidthFixedVariable(String widthFixedVariable) {
        this.widthFixedVariable = widthFixedVariable;
    }

    public boolean isHideDefaultToolbar() {
        return isHideDefaultToolbar;
    }

    public void setHideDefaultToolbar(boolean hideDefaultToolbar) {
        isHideDefaultToolbar = hideDefaultToolbar;
    }

    public int getLayoutWidthInPixel() {
        return layoutWidthInPixel;
    }

    public void setLayoutWidthInPixel(int layoutWidthInPixel) {
        this.layoutWidthInPixel = layoutWidthInPixel;
    }

    public int getLayoutHeightInPixel() {
        return layoutHeightInPixel;
    }

    public void setLayoutHeightInPixel(int layoutHeightInPixel) {
        this.layoutHeightInPixel = layoutHeightInPixel;
    }
}
