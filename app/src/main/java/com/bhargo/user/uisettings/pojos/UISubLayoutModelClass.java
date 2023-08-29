package com.bhargo.user.uisettings.pojos;

public class UISubLayoutModelClass {

    String subLayoutAliasName;
    String position;
    String subLayoutHeight;
    String subLayoutWidth;
    boolean isControlAdded;


    public String getSubLayoutAliasName() {
        return subLayoutAliasName;
    }

    public void setSubLayoutAliasName(String subLayoutAliasName) {
        this.subLayoutAliasName = subLayoutAliasName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSubLayoutHeight() {
        return subLayoutHeight;
    }

    public void setSubLayoutHeight(String subLayoutHeight) {
        this.subLayoutHeight = subLayoutHeight;
    }

    public String getSubLayoutWidth() {
        return subLayoutWidth;
    }

    public void setSubLayoutWidth(String subLayoutWidth) {
        this.subLayoutWidth = subLayoutWidth;
    }

    public boolean isControlAdded() {
        return isControlAdded;
    }

    public void setControlAdded(boolean controlAdded) {
        isControlAdded = controlAdded;
    }
}
