package com.bhargo.user.Java_Beans;

import java.io.Serializable;

public class DetailedPageHeaderSettings implements Serializable {

    public Data data;
    public boolean isHeaderLayout;
    DetailedPageUISettings detailedPageUISettings =new DetailedPageUISettings();

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public boolean isHeaderLayout() {
        return isHeaderLayout;
    }

    public void setHeaderLayout(boolean headerLayout) {
        isHeaderLayout = headerLayout;
    }

    public DetailedPageUISettings getDetailedPageUISettings() {
        return detailedPageUISettings;
    }

    public void setDetailedPageUISettings(DetailedPageUISettings detailedPageUISettings) {
        this.detailedPageUISettings = detailedPageUISettings;
    }
}
