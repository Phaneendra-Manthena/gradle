package com.bhargo.user.Java_Beans;


import java.io.Serializable;

public class ColumnSettings implements Serializable {
    private String columnWidth="120";
    private String displayType="TEXT";
    private boolean wrapContent;
    private boolean enableSorting;
    private DisplaySettings headerSettings;
    private DisplaySettings bodySettings;
    private DisplaySettings footerSettings;

    public String getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(String columnWidth) {
        this.columnWidth = columnWidth;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public boolean isWrapContent() {
        return wrapContent;
    }

    public void setWrapContent(boolean wrapContent) {
        this.wrapContent = wrapContent;
    }

    public boolean isEnableSorting() {
        return enableSorting;
    }

    public void setEnableSorting(boolean enableSorting) {
        this.enableSorting = enableSorting;
    }

    public DisplaySettings getHeaderSettings() {
        return headerSettings;
    }

    public void setHeaderSettings(DisplaySettings headerSettings) {
        this.headerSettings = headerSettings;
    }

    public DisplaySettings getBodySettings() {
        return bodySettings;
    }

    public void setBodySettings(DisplaySettings bodySettings) {
        this.bodySettings = bodySettings;
    }

    public DisplaySettings getFooterSettings() {
        return footerSettings;
    }

    public void setFooterSettings(DisplaySettings footerSettings) {
        this.footerSettings = footerSettings;
    }
}