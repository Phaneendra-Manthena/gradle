package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class MainContainerSettings implements Serializable {

    public int MainContainerTemplateId;
    public List<String> tableColumns;
    public MainContainerUISettings mainContainerUISettings;

    public int getMainContainerTemplateId() {
        return MainContainerTemplateId;
    }

    public void setMainContainerTemplateId(int mainContainerTemplateId) {
        MainContainerTemplateId = mainContainerTemplateId;
    }

    public List<String> getTableColumns() {
        return tableColumns;
    }

    public void setTableColumns(List<String> tableColumns) {
        this.tableColumns = tableColumns;
    }

    public MainContainerUISettings getMainContainerUISettings() {
        return mainContainerUISettings;
    }

    public void setMainContainerUISettings(MainContainerUISettings mainContainerUISettings) {
        this.mainContainerUISettings = mainContainerUISettings;
    }
}
