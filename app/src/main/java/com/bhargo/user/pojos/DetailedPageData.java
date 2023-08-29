package com.bhargo.user.pojos;

import com.bhargo.user.Java_Beans.DataManagementOptions;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class DetailedPageData implements Serializable {


    private String jsonObject;
    private DataManagementOptions dataManagementOptions;
    private String tableName;
    private List<SubFormTableColumns> subFormDetails;
    private String TransID;
    private String appName;
    private String childForm;
    private String appVersion;
    private String appType;
    private String appIcon;
    private String displayAppName;
    private String displayIcon;
    private String createdBy;
    private String userID;
    private String userPostID;
    private String distributionID;
    private String userLocationStructure;
    private String appEdit;
    private String fromActivity;
    private String s_app_icon;
    private FormLevelTranslation formLevelTranslation;

    public String getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }

    public DataManagementOptions getDataManagementOptions() {
        return dataManagementOptions;
    }

    public void setDataManagementOptions(DataManagementOptions dataManagementOptions) {
        this.dataManagementOptions = dataManagementOptions;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<SubFormTableColumns> getSubFormDetails() {
        return subFormDetails;
    }

    public void setSubFormDetails(List<SubFormTableColumns> subFormDetails) {
        this.subFormDetails = subFormDetails;
    }

    public String getTransID() {
        return TransID;
    }

    public void setTransID(String transID) {
        TransID = transID;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getChildForm() {
        return childForm;
    }

    public void setChildForm(String childForm) {
        this.childForm = childForm;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getDisplayAppName() {
        return displayAppName;
    }

    public void setDisplayAppName(String displayAppName) {
        this.displayAppName = displayAppName;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public void setDisplayIcon(String displayIcon) {
        this.displayIcon = displayIcon;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPostID() {
        return userPostID;
    }

    public void setUserPostID(String userPostID) {
        this.userPostID = userPostID;
    }

    public String getDistributionID() {
        return distributionID;
    }

    public void setDistributionID(String distributionID) {
        this.distributionID = distributionID;
    }

    public String getUserLocationStructure() {
        return userLocationStructure;
    }

    public void setUserLocationStructure(String userLocationStructure) {
        this.userLocationStructure = userLocationStructure;
    }

    public String getAppEdit() {
        return appEdit;
    }

    public void setAppEdit(String appEdit) {
        this.appEdit = appEdit;
    }

    public String getFromActivity() {
        return fromActivity;
    }

    public void setFromActivity(String fromActivity) {
        this.fromActivity = fromActivity;
    }

    public String getS_app_icon() {
        return s_app_icon;
    }

    public void setS_app_icon(String s_app_icon) {
        this.s_app_icon = s_app_icon;
    }

    public FormLevelTranslation getFormLevelTranslation() {
        return formLevelTranslation;
    }

    public void setFormLevelTranslation(FormLevelTranslation formLevelTranslation) {
        this.formLevelTranslation = formLevelTranslation;
    }
}
