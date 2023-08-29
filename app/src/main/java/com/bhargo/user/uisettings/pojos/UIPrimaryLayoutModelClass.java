package com.bhargo.user.uisettings.pojos;

import java.io.Serializable;
import java.util.List;

public class UIPrimaryLayoutModelClass implements Serializable {

    String subformName;
    String sectionName;
    String primaryLayoutAliasName;
    String screenType;
    UILayoutProperties primaryLayoutProperties;
    List<LayoutProperties> layoutPropertiesList;

    public String getSubformName() {
        return subformName;
    }

    public void setSubformName(String subformName) {
        this.subformName = subformName;
    }

    public String getPrimaryLayoutAliasName() {
        return primaryLayoutAliasName;
    }

    public void setPrimaryLayoutAliasName(String primaryLayoutAliasName) {
        this.primaryLayoutAliasName = primaryLayoutAliasName;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public UILayoutProperties getPrimaryLayoutProperties() {
        return primaryLayoutProperties;
    }

    public void setPrimaryLayoutProperties(UILayoutProperties primaryLayoutProperties) {
        this.primaryLayoutProperties = primaryLayoutProperties;
    }

    public List<LayoutProperties> getLayoutPropertiesList() {
        return layoutPropertiesList;
    }

    public void setLayoutPropertiesList(List<LayoutProperties> layoutPropertiesList) {
        this.layoutPropertiesList = layoutPropertiesList;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
