package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

public class WebLinkObject implements Serializable {

    public String WebLink_Name,WebLink_DESC,WebLink_Url,WebLink_Icon,Hit_Mode="Hybrid",
    WebLink_TypeofParameter;
    private int WebLink_Version;
    LinkedHashMap<String,String> Hash_QueryStrings;
    List<String> List_Delimiters;
    private LinkedHashMap<String,String> translatedAppNames;
    private LinkedHashMap<String,String> translatedAppDescriptions;

    public String getWebLink_Name() {
        return WebLink_Name;
    }

    public void setWebLink_Name(String webLink_Name) {
        WebLink_Name = webLink_Name;
    }

    public String getWebLink_DESC() {
        return WebLink_DESC;
    }

    public void setWebLink_DESC(String webLink_DESC) {
        WebLink_DESC = webLink_DESC;
    }

    public String getWebLink_Url() {
        return WebLink_Url;
    }

    public void setWebLink_Url(String webLink_Url) {
        WebLink_Url = webLink_Url;
    }

    public String getWebLink_Icon() {
        return WebLink_Icon;
    }

    public void setWebLink_Icon(String webLink_Icon) {
        WebLink_Icon = webLink_Icon;
    }

    public String getHit_Mode() {
        return Hit_Mode;
    }

    public void setHit_Mode(String hit_Mode) {
        Hit_Mode = hit_Mode;
    }

    public String getWebLink_TypeofParameter() {
        return WebLink_TypeofParameter;
    }

    public void setWebLink_TypeofParameter(String webLink_TypeofParameter) {
        WebLink_TypeofParameter = webLink_TypeofParameter;
    }

    public int getWebLink_Version() {
        return WebLink_Version;
    }

    public void setWebLink_Version(int webLink_Version) {
        WebLink_Version = webLink_Version;
    }

    public LinkedHashMap<String, String> getHash_QueryStrings() {
        return Hash_QueryStrings;
    }

    public void setHash_QueryStrings(LinkedHashMap<String, String> hash_QueryStrings) {
        Hash_QueryStrings = hash_QueryStrings;
    }

    public List<String> getList_Delimiters() {
        return List_Delimiters;
    }

    public void setList_Delimiters(List<String> list_Delimiters) {
        List_Delimiters = list_Delimiters;
    }

    public LinkedHashMap<String, String> getTranslatedAppNames() {
        return translatedAppNames;
    }

    public void setTranslatedAppNames(LinkedHashMap<String, String> translatedAppNames) {
        this.translatedAppNames = translatedAppNames;
    }

    public LinkedHashMap<String, String> getTranslatedAppDescriptions() {
        return translatedAppDescriptions;
    }

    public void setTranslatedAppDescriptions(LinkedHashMap<String, String> translatedAppDescriptions) {
        this.translatedAppDescriptions = translatedAppDescriptions;
    }
}
