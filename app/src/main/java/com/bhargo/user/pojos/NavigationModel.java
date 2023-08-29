package com.bhargo.user.pojos;

import com.bhargo.user.Java_Beans.DataManagementOptions;
import com.bhargo.user.Java_Beans.Variable_Bean;
import com.bhargo.user.Java_Beans.VisibilityManagementOptions;

import java.util.List;

public class NavigationModel {

    private String formName;
    private String appVersion;
    private String orgId;
    private String createdBy;
    private String userId;
    private String distributionId;
    private String postId;
    private String designFormat;
    private String appType;
    private String appIcon;
    private boolean resume=true;
    private String strUserLocationStructure;
    private List<Variable_Bean> list_variables;
    private String callerFormName;
    private String formType;
    private boolean isCloseParentEnabled;
    private boolean closeAllFormsEnabled;
    private boolean keepSessionEnabled;
    private boolean goToHomeEnabled;
    private boolean appInMultiForm;
    private DataManagementOptions dataManagementOptions;
    private VisibilityManagementOptions visibilityManagementOptions;

    public NavigationModel() { }

    public NavigationModel(String formName, String appVersion, String orgId, String createdBy, String userId, String distributionId, String postId,String designFormat,
                           String appType, boolean resume, String strUserLocationStructure, List<Variable_Bean> list_variables,String callerFormName,String formType,boolean goToHomeEnabled) {

        this.formName = formName;
        this.appVersion = appVersion;
        this.orgId = orgId;
        this.createdBy = createdBy;
        this.userId = userId;
        this.distributionId = distributionId;
        this.postId = postId;
        this.designFormat = designFormat;
        this.appType = appType;
        this.resume = resume;
        this.strUserLocationStructure = strUserLocationStructure;
        this.list_variables = list_variables;
        this.callerFormName = callerFormName;
        this.formType = formType;
        this.goToHomeEnabled = goToHomeEnabled;

    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDistributionId() {
        return distributionId;
    }

    public void setDistributionId(String distributionId) {
        this.distributionId = distributionId;
    }

    public String getDesignFormat() {
        return designFormat;
    }

    public void setDesignFormat(String designFormat) {
        this.designFormat = designFormat;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public boolean isResume() {
        return resume;
    }


    public void setResume(Boolean resume) {
        this.resume = resume;
    }

    public String getStrUserLocationStructure() {
        return strUserLocationStructure;
    }

    public void setStrUserLocationStructure(String strUserLocationStructure) {
        this.strUserLocationStructure = strUserLocationStructure;
    }

    public List<Variable_Bean> getList_variables() {
        return list_variables;
    }

    public void setList_variables(List<Variable_Bean> list_variables) {
        this.list_variables = list_variables;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setResume(boolean resume) {
        this.resume = resume;
    }

    public String getCallerFormName() {
        return callerFormName;
    }

    public void setCallerFormName(String callerFormName) {
        this.callerFormName = callerFormName;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public boolean isCloseParentEnabled() {
        return isCloseParentEnabled;
    }

    public void setCloseParentEnabled(boolean closeParentEnabled) {
        isCloseParentEnabled = closeParentEnabled;
    }

    public boolean isCloseAllFormsEnabled() {
        return closeAllFormsEnabled;
    }

    public void setCloseAllFormsEnabled(boolean closeAllFormsEnabled) {
        this.closeAllFormsEnabled = closeAllFormsEnabled;
    }

    public boolean isKeepSessionEnabled() {
        return keepSessionEnabled;
    }

    public void setKeepSessionEnabled(boolean keepSessionEnabled) {
        this.keepSessionEnabled = keepSessionEnabled;
    }

    public boolean isGoToHomeEnabled() {
        return goToHomeEnabled;
    }

    public void setGoToHomeEnabled(boolean goToHomeEnabled) {
        this.goToHomeEnabled = goToHomeEnabled;
    }

    public boolean isAppInMultiForm() {
        return appInMultiForm;
    }

    public void setAppInMultiForm(boolean appInMultiForm) {
        this.appInMultiForm = appInMultiForm;
    }

    public DataManagementOptions getDataManagementOptions() {
        return dataManagementOptions;
    }

    public void setDataManagementOptions(DataManagementOptions dataManagementOptions) {
        this.dataManagementOptions = dataManagementOptions;
    }

    public VisibilityManagementOptions getVisibilityManagementOptions() {
        return visibilityManagementOptions;
    }

    public void setVisibilityManagementOptions(VisibilityManagementOptions visibilityManagementOptions) {
        this.visibilityManagementOptions = visibilityManagementOptions;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }
}
