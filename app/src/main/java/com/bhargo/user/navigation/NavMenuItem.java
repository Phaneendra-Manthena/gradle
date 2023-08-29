package com.bhargo.user.navigation;

import com.bhargo.user.Java_Beans.Control_EventObject;

import java.io.Serializable;
import java.util.List;

public class NavMenuItem implements Serializable {

    private String menuIcon;
    private String displayName;
    private String tagName;
    private String textSize;
    private String textStyle;
    private String textHexColor;
    private int textColor;
    private Control_EventObject onClickEventObject;
    private boolean onClickEventExists;
    private List<NavMenuItem> subMenuItems;
    private int itemPos;
    private int parentPos;
    private String toolBarItemDisplayType;
    private boolean containsSubMenu=true;// True for NavigationMenu False for Toolbar;
    private boolean bottomNavigationItem;


    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTextSize() {
        return textSize;
    }

    public void setTextSize(String textSize) {
        this.textSize = textSize;
    }

    public String getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(String textStyle) {
        this.textStyle = textStyle;
    }

    public String getTextHexColor() {
        return textHexColor;
    }

    public void setTextHexColor(String textHexColor) {
        this.textHexColor = textHexColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public Control_EventObject getOnClickEventObject() {
        return onClickEventObject;
    }

    public void setOnClickEventObject(Control_EventObject onClickEventObject) {
        this.onClickEventObject = onClickEventObject;
    }

    public boolean isOnClickEventExists() {
        return onClickEventExists;
    }

    public void setOnClickEventExists(boolean onClickEventExists) {
        this.onClickEventExists = onClickEventExists;
    }

    public List<NavMenuItem> getSubMenuItems() {
        return subMenuItems;
    }

    public void setSubMenuItems(List<NavMenuItem> subMenuItems) {
        this.subMenuItems = subMenuItems;
    }

    public int getItemPos() {
        return itemPos;
    }

    public void setItemPos(int itemPos) {
        this.itemPos = itemPos;
    }

    public int getParentPos() {
        return parentPos;
    }

    public void setParentPos(int parentPos) {
        this.parentPos = parentPos;
    }

    public String getToolBarItemDisplayType() {
        return toolBarItemDisplayType;
    }

    public void setToolBarItemDisplayType(String toolBarItemDisplayType) {
        this.toolBarItemDisplayType = toolBarItemDisplayType;
    }

    public boolean isContainsSubMenu() {
        return containsSubMenu;
    }

    public void setContainsSubMenu(boolean containsSubMenu) {
        this.containsSubMenu = containsSubMenu;
    }

    public boolean isBottomNavigationItem() {
        return bottomNavigationItem;
    }

    public void setBottomNavigationItem(boolean bottomNavigationItem) {
        this.bottomNavigationItem = bottomNavigationItem;
    }
}
