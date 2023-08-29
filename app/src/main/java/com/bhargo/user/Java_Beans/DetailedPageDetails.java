package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class DetailedPageDetails implements Serializable {
    private boolean isDetailPage;
    private Integer detailedPageTemplateId;

    DetailedPageHeaderSettings detailedPageHeaderSettings=new DetailedPageHeaderSettings();
    public BodySettings BodySettings = new BodySettings();

    public boolean isDetailPage() {
        return isDetailPage;
    }

    public void setDetailPage(boolean detailPage) {
        isDetailPage = detailPage;
    }

    public Integer getDetailedPageTemplateId() {
        return detailedPageTemplateId;
    }

    public void setDetailedPageTemplateId(Integer detailedPageTemplateId) {
        this.detailedPageTemplateId = detailedPageTemplateId;
    }

    public DetailedPageHeaderSettings getDetailedPageHeaderSettings() {
        return detailedPageHeaderSettings;
    }

    public void setDetailedPageHeaderSettings(DetailedPageHeaderSettings detailedPageHeaderSettings) {
        this.detailedPageHeaderSettings = detailedPageHeaderSettings;
    }

    public com.bhargo.user.Java_Beans.BodySettings getBodySettings() {
        return BodySettings;
    }

    public void setBodySettings(com.bhargo.user.Java_Beans.BodySettings bodySettings) {
        BodySettings = bodySettings;
    }
}
