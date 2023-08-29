package com.bhargo.user.ui_form;

import java.io.Serializable;
import java.util.HashMap;

public class PrimaryLayoutModelClass implements Serializable {

    String subFormName;
    String aliasName;
    String standardOverlap;
    String parentLayoutId;
    String screenType;
    String backgroundType;
    int backgroundColor;
    String backgroundColorHex;
    String gradientType;
    String gradientOneColorHex;
    String gradientTwoColorHex;
    int gradientOneBackgroundColor;
    int gradientTwoBackgroundColor;
//    Float gradientCornerRadius = 0f;
    int gradientCornerRadius = 0;
    String backgroundImage;
    boolean isDefaultColor;

    int stroke = 0;
    String strokeColor = "#FFFFFF";
    int paddingLeft = 0;
    int paddingRight = 0;
    int paddingTop = 0;
    int paddingBottom = 0;

    int marginLeft = 0;
    int marginRight = 0;
    int marginTop = 0;
    int marginBottom = 0;

    String primaryLayoutAlignment;

    HashMap<Integer, SubLayoutsModelClass> subLayoutsModelClassHashMap;


    public String getSubFormName() {
        return subFormName;
    }

    public void setSubFormName(String subFormName) {
        this.subFormName = subFormName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getStandardOverlap() {
        return standardOverlap;
    }

    public void setStandardOverlap(String standardOverlap) {
        this.standardOverlap = standardOverlap;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundColorHex() {
        return backgroundColorHex;
    }

    public void setBackgroundColorHex(String backgroundColorHex) {
        this.backgroundColorHex = backgroundColorHex;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }


    public String getParentLayoutId() {
        return parentLayoutId;
    }

    public void setParentLayoutId(String parentLayoutId) {
        this.parentLayoutId = parentLayoutId;
    }

    public String getBackgroundType() {
        return backgroundType;
    }

    public void setBackgroundType(String backgroundType) {
        this.backgroundType = backgroundType;
    }

    public HashMap<Integer, SubLayoutsModelClass> getSubLayoutsModelClassHashMap() {
        return subLayoutsModelClassHashMap;
    }

    public void setSubLayoutsModelClassHashMap(HashMap<Integer, SubLayoutsModelClass> subLayoutsModelClassHashMap) {
        this.subLayoutsModelClassHashMap = subLayoutsModelClassHashMap;
    }

    public boolean isDefaultColor() {
        return isDefaultColor;
    }

    public void setDefaultColor(boolean defaultColor) {
        isDefaultColor = defaultColor;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

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

    public String getPrimaryLayoutAlignment() {
        return primaryLayoutAlignment;
    }

    public void setPrimaryLayoutAlignment(String primaryLayoutAlignment) {
        this.primaryLayoutAlignment = primaryLayoutAlignment;
    }

    public String getGradientType() {
        return gradientType;
    }

    public void setGradientType(String gradientType) {
        this.gradientType = gradientType;
    }

    public String getGradientOneColorHex() {
        return gradientOneColorHex;
    }

    public void setGradientOneColorHex(String gradientOneColorHex) {
        this.gradientOneColorHex = gradientOneColorHex;
    }

    public String getGradientTwoColorHex() {
        return gradientTwoColorHex;
    }

    public void setGradientTwoColorHex(String gradientTwoColorHex) {
        this.gradientTwoColorHex = gradientTwoColorHex;
    }

    public int getGradientOneBackgroundColor() {
        return gradientOneBackgroundColor;
    }

    public void setGradientOneBackgroundColor(int gradientOneBackgroundColor) {
        this.gradientOneBackgroundColor = gradientOneBackgroundColor;
    }

    public int getGradientTwoBackgroundColor() {
        return gradientTwoBackgroundColor;
    }

    public void setGradientTwoBackgroundColor(int gradientTwoBackgroundColor) {
        this.gradientTwoBackgroundColor = gradientTwoBackgroundColor;
    }

//    public Float getGradientCornerRadius() {
//        return gradientCornerRadius;
//    }
//
//    public void setGradientCornerRadius(Float gradientCornerRadius) {
//        this.gradientCornerRadius = gradientCornerRadius;
//    }

    public int getGradientCornerRadius() {
        return gradientCornerRadius;
    }

    public void setGradientCornerRadius(int gradientCornerRadius) {
        this.gradientCornerRadius = gradientCornerRadius;
    }

    public int getStroke() {
        return stroke;
    }

    public void setStroke(int stroke) {
        this.stroke = stroke;
    }

    public String getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(String strokeColor) {
        this.strokeColor = strokeColor;
    }


}
