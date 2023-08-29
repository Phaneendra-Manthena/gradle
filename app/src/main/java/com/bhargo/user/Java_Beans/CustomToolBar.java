package com.bhargo.user.Java_Beans;



import com.bhargo.user.navigation.NavMenuItem;

import java.io.Serializable;
import java.util.List;

public class CustomToolBar implements Serializable {

    private boolean enableIcon;
    private boolean enableTitle;
    private boolean enableActionItems;
    private boolean enableCustomBackground;
    private List<NavMenuItem> items;
    private boolean fromEdit;
    private String bgColor;
    private String iconTint;
    private String titleColor;
    private String titleTextSize;
    private String titleTextStyle;

    public boolean isEnableIcon() {
        return enableIcon;
    }

    public void setEnableIcon(boolean enableIcon) {
        this.enableIcon = enableIcon;
    }

    public boolean isEnableTitle() {
        return enableTitle;
    }

    public void setEnableTitle(boolean enableTitle) {
        this.enableTitle = enableTitle;
    }

    public boolean isEnableActionItems() {
        return enableActionItems;
    }

    public void setEnableActionItems(boolean enableActionItems) {
        this.enableActionItems = enableActionItems;
    }

    public List<NavMenuItem> getItems() {
        return items;
    }

    public void setItems(List<NavMenuItem> items) {
        this.items = items;
    }

    public boolean isFromEdit() {
        return fromEdit;
    }

    public void setFromEdit(boolean fromEdit) {
        this.fromEdit = fromEdit;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getIconTint() {
        return iconTint;
    }

    public void setIconTint(String iconTint) {
        this.iconTint = iconTint;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public String getTitleTextSize() {
        return titleTextSize;
    }

    public void setTitleTextSize(String titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    public String getTitleTextStyle() {
        return titleTextStyle;
    }

    public void setTitleTextStyle(String titleTextStyle) {
        this.titleTextStyle = titleTextStyle;
    }

    public boolean isEnableCustomBackground() {
        return enableCustomBackground;
    }

    public void setEnableCustomBackground(boolean enableCustomBackground) {
        this.enableCustomBackground = enableCustomBackground;
    }
}
