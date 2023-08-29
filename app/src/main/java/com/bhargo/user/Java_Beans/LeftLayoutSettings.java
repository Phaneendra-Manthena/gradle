package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class LeftLayoutSettings implements Serializable {

    public String defaultPageTitle;
    public boolean isLeftLayout;
    public MainContainerSettings MainContainerSettings=new MainContainerSettings();
    public List<SubContainerSettings> SubContainerSettings;

    public String getDefaultPageTitle() {
        return defaultPageTitle;
    }

    public void setDefaultPageTitle(String defaultPageTitle) {
        this.defaultPageTitle = defaultPageTitle;
    }

    public boolean isLeftLayout() {
        return isLeftLayout;
    }

    public void setLeftLayout(boolean leftLayout) {
        isLeftLayout = leftLayout;
    }

    public com.bhargo.user.Java_Beans.MainContainerSettings getMainContainerSettings() {
        return MainContainerSettings;
    }

    public void setMainContainerSettings(com.bhargo.user.Java_Beans.MainContainerSettings mainContainerSettings) {
        MainContainerSettings = mainContainerSettings;
    }

    public List<com.bhargo.user.Java_Beans.SubContainerSettings> getSubContainerSettings() {
        return SubContainerSettings;
    }

    public void setSubContainerSettings(List<com.bhargo.user.Java_Beans.SubContainerSettings> subContainerSettings) {
        SubContainerSettings = subContainerSettings;
    }
}
