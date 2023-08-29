package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class IndexPageDetails implements Serializable {
    private boolean isIndexPage;
    private Integer indexPageTemplateId;
    private  String PageTitle;
    private  String ProfileImage;
    private  String image;
    private  String Header;
    private  String SubHeader;
    List<String> descriptionList;
    private IndexPageUISettings  indexPageUISettings=new IndexPageUISettings();

    public boolean isIndexPage() {
        return isIndexPage;
    }

    public void setIndexPage(boolean indexPage) {
        isIndexPage = indexPage;
    }

    public Integer getIndexPageTemplateId() {
        return indexPageTemplateId;
    }

    public void setIndexPageTemplateId(Integer indexPageTemplateId) {
        this.indexPageTemplateId = indexPageTemplateId;
    }

    public String getPageTitle() {
        return PageTitle;
    }

    public void setPageTitle(String pageTitle) {
        PageTitle = pageTitle;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHeader() {
        return Header;
    }

    public void setHeader(String header) {
        Header = header;
    }

    public String getSubHeader() {
        return SubHeader;
    }

    public void setSubHeader(String subHeader) {
        SubHeader = subHeader;
    }

    public List<String> getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(List<String> descriptionList) {
        this.descriptionList = descriptionList;
    }

    public IndexPageUISettings getIndexPageUISettings() {
        return indexPageUISettings;
    }

    public void setIndexPageUISettings(IndexPageUISettings indexPageUISettings) {
        this.indexPageUISettings = indexPageUISettings;
    }
}
