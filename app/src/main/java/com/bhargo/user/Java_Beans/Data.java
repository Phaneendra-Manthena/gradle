package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class Data  implements Serializable {

    public String pageTitle;
    public String image;
    public String header;
    public String subHeader;
    public List<String> descriptionList;
    public String cardOneImage;
    public String cardOneLable;
    public String cardOneValue;
    public String cardTwoImage;
    public String cardTwoLable;
    public String cardTwoValue;
    public String ProfileImage;
    public String Date;
    public String Title;

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSubHeader() {
        return subHeader;
    }

    public void setSubHeader(String subHeader) {
        this.subHeader = subHeader;
    }

    public List<String> getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(List<String> descriptionList) {
        this.descriptionList = descriptionList;
    }

    public String getCardOneImage() {
        return cardOneImage;
    }

    public void setCardOneImage(String cardOneImage) {
        this.cardOneImage = cardOneImage;
    }

    public String getCardOneLable() {
        return cardOneLable;
    }

    public void setCardOneLable(String cardOneLable) {
        this.cardOneLable = cardOneLable;
    }

    public String getCardOneValue() {
        return cardOneValue;
    }

    public void setCardOneValue(String cardOneValue) {
        this.cardOneValue = cardOneValue;
    }

    public String getCardTwoImage() {
        return cardTwoImage;
    }

    public void setCardTwoImage(String cardTwoImage) {
        this.cardTwoImage = cardTwoImage;
    }

    public String getCardTwoLable() {
        return cardTwoLable;
    }

    public void setCardTwoLable(String cardTwoLable) {
        this.cardTwoLable = cardTwoLable;
    }

    public String getCardTwoValue() {
        return cardTwoValue;
    }

    public void setCardTwoValue(String cardTwoValue) {
        this.cardTwoValue = cardTwoValue;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
