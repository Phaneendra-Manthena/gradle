package com.bhargo.user.uisettings.pojos;



import java.io.Serializable;
import java.util.List;

public class LayoutProperties implements Serializable {

    int position;
    String layoutName;
    UILayoutProperties uiLayoutProperties;
    List<LayoutProperties> subLayoutPropertiesList;
    MappingControlModel layoutControl;
    boolean addLayout=true;
    boolean addControl=true;
    String layoutPropertiesType;
    String wrap_or_dp;
    boolean isLayoutToolBar= false;


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<LayoutProperties> getSubLayoutPropertiesList() {
        return subLayoutPropertiesList;
    }

    public void setSubLayoutPropertiesList(List<LayoutProperties> subLayoutPropertiesList) {
        this.subLayoutPropertiesList = subLayoutPropertiesList;
    }

    public MappingControlModel getLayoutControl() {
        return layoutControl;
    }

    public void setLayoutControl(MappingControlModel layoutControl) {
        this.layoutControl = layoutControl;
    }

    public UILayoutProperties getUiLayoutProperties() {
        return uiLayoutProperties;
    }

    public void setUiLayoutProperties(UILayoutProperties uiLayoutProperties) {
        this.uiLayoutProperties = uiLayoutProperties;
    }

    public String getLayoutName() {
        return layoutName;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }

    public boolean isAddLayout() {
        return addLayout;
    }

    public void setAddLayout(boolean addLayout) {
        this.addLayout = addLayout;
    }

    public boolean isAddControl() {
        return addControl;
    }

    public void setAddControl(boolean addControl) {
        this.addControl = addControl;
    }

    public String getLayoutPropertiesType() {
        return layoutPropertiesType;
    }

    public void setLayoutPropertiesType(String layoutPropertiesType) {
        this.layoutPropertiesType = layoutPropertiesType;
    }

    public String getWrap_or_dp() {
        return wrap_or_dp;
    }

    public void setWrap_or_dp(String wrap_or_dp) {
        this.wrap_or_dp = wrap_or_dp;
    }

    public boolean isLayoutToolBar() {
        return isLayoutToolBar;
    }

    public void setLayoutToolBar(boolean layoutToolBar) {
        isLayoutToolBar = layoutToolBar;
    }

}
