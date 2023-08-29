package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class DataTableColumn_Bean implements Serializable {

    private String columnName;
    private String headerName;
    private String footerLabel;
    private String footerFunction;
    private boolean isColumnEnabled;
    private String headerId;
    private ColumnSettings settings;
    private boolean settingsEdited = false;

    public DataTableColumn_Bean() {
    }

    public DataTableColumn_Bean(String columnName, String headerName, String footerFunction, boolean isColumnEnabled, String columnWidth) {
        this.columnName = columnName;
        this.headerName = headerName;
        this.footerFunction = footerFunction;
        this.isColumnEnabled = isColumnEnabled;

    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getFooterFunction() {
        return footerFunction;
    }

    public void setFooterFunction(String footerFunction) {
        this.footerFunction = footerFunction;
    }

    public boolean isColumnEnabled() {
        return isColumnEnabled;
    }

    public void setColumnEnabled(boolean columnEnabled) {
        isColumnEnabled = columnEnabled;
    }


    public String getHeaderId() {
        return headerId;
    }

    public void setHeaderId(String headerId) {
        this.headerId = headerId;
    }


    public ColumnSettings getSettings() {
        return settings;
    }

    public void setSettings(ColumnSettings settings) {
        this.settings = settings;
    }

    public String getFooterLabel() {
        return footerLabel;
    }

    public void setFooterLabel(String footerLabel) {
        this.footerLabel = footerLabel;
    }

    public boolean isSettingsEdited() {
        return settingsEdited;
    }

    public void setSettingsEdited(boolean settingsEdited) {
        this.settingsEdited = settingsEdited;
    }
}
