package com.bhargo.user.pojos;

public class PageNamesList {

     String PageName;
     String CreatedBy;
     String Datetime;
     String AppIcon;
    private boolean isChecked = false;

    public String getPageName() {
        return PageName;
    }

    public void setPageName(String pageName) {
        PageName = pageName;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getDatetime() {
        return Datetime;
    }

    public void setDatetime(String datetime) {
        Datetime = datetime;
    }

    public String getAppIcon() {
        return AppIcon;
    }

    public void setAppIcon(String appIcon) {
        AppIcon = appIcon;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
