package com.bhargo.user.navigation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NavMenu implements Serializable {

    private String navIcon;
    private List<NavMenuItem> menuItemList;
    private String displayTypeInWeb;
    private String displayTypeInMobile;
    private boolean enableCustomBackground;
    private String bgColor;
    private List<Integer> tempDrawablesList=new ArrayList<>();

    public String getNavIcon() {
        return navIcon;
    }

    public void setNavIcon(String navIcon) {
        this.navIcon = navIcon;
    }

    public List<NavMenuItem> getMenuItemList() {
        return menuItemList;
    }

    public void setMenuItemList(List<NavMenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }

    public String getDisplayTypeInWeb() {
        return displayTypeInWeb;
    }

    public void setDisplayTypeInWeb(String displayTypeInWeb) {
        this.displayTypeInWeb = displayTypeInWeb;
    }

    public String getDisplayTypeInMobile() {
        return displayTypeInMobile;
    }

    public void setDisplayTypeInMobile(String displayTypeInMobile) {
        this.displayTypeInMobile = displayTypeInMobile;
    }

    public boolean isEnableCustomBackground() {
        return enableCustomBackground;
    }

    public void setEnableCustomBackground(boolean enableCustomBackground) {
        this.enableCustomBackground = enableCustomBackground;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public List<Integer> getTempDrawablesList() {
        return tempDrawablesList;
    }

    public void setTempDrawablesList(List<Integer> tempDrawablesList) {
        this.tempDrawablesList = tempDrawablesList;
    }
}
