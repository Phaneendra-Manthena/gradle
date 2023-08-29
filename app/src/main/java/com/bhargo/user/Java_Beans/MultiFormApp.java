package com.bhargo.user.Java_Beans;

import android.view.View;

import com.bhargo.user.navigation.NavMenu;
import com.bhargo.user.pojos.FormNavigation;
import com.bhargo.user.utils.IndexList;

import java.util.LinkedHashMap;
import java.util.List;

public class MultiFormApp {
    private String appName;
    private String appDescription;
    private String appIconURL;
    private String appVersion="1";
    private String hitMode="first";
    private String home;
    private List<DataCollectionObject> formsList;
    private LinkedHashMap<String,String> innerFormsDesignMap;
    private IndexList<FormNavigation> navigationList = new IndexList<>();
    private NavMenu navMenu;
    private NavMenu bottomNavigation;
    private String createBy;
    private String distributionId;
    private String postId;
    private CustomToolBar customToolBar;
    private String homeIn;
    private int homeMenuPos=-1;

    private LinkedHashMap<String, View> navigationViewsMap = new LinkedHashMap<>();

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public List<DataCollectionObject> getFormsList() {
        return formsList;
    }

    public void setFormsList(List<DataCollectionObject> formsList) {
        this.formsList = formsList;
    }

    public String getAppIconURL() {
        return appIconURL;
    }

    public void setAppIconURL(String appIconURL) {
        this.appIconURL = appIconURL;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getHitMode() {
        return hitMode;
    }

    public void setHitMode(String hitMode) {
        this.hitMode = hitMode;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public LinkedHashMap<String, String> getInnerFormsDesignMap() {
        return innerFormsDesignMap;
    }

    public void setInnerFormsDesignMap(LinkedHashMap<String, String> innerFormsDesignMap) {
        this.innerFormsDesignMap = innerFormsDesignMap;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getDistributionId() {
        return distributionId;
    }

    public void setDistributionId(String distributionId) {
        this.distributionId = distributionId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public IndexList<FormNavigation> getNavigationList() {
        return navigationList;
    }

    public void setNavigationList(IndexList<FormNavigation> navigationList) {
        this.navigationList = navigationList;
    }

    public LinkedHashMap<String, View> getNavigationViewsMap() {
        return navigationViewsMap;
    }

    public void setNavigationViewsMap(LinkedHashMap<String, View> navigationViewsMap) {
        this.navigationViewsMap = navigationViewsMap;
    }

    public NavMenu getNavMenu() {
        return navMenu;
    }

    public void setNavMenu(NavMenu navMenu) {
        this.navMenu = navMenu;
    }

    public CustomToolBar getCustomToolBar() {
        return customToolBar;
    }

    public void setCustomToolBar(CustomToolBar customToolBar) {
        this.customToolBar = customToolBar;
    }

    public NavMenu getBottomNavigation() {
        return bottomNavigation;
    }

    public void setBottomNavigation(NavMenu bottomNavigation) {
        this.bottomNavigation = bottomNavigation;
    }

    public String getHomeIn() {
        return homeIn;
    }

    public void setHomeIn(String homeIn) {
        this.homeIn = homeIn;
    }

    public int getHomeMenuPos() {
        return homeMenuPos;
    }

    public void setHomeMenuPos(int homeMenuPos) {
        this.homeMenuPos = homeMenuPos;
    }
}
