package com.bhargo.user.pojos;

import android.graphics.drawable.Drawable;

import java.util.List;

public class GridImagesModel {

    Drawable drawable;
    String strName;
    public String Status;
    public String Message;
    public List<AppIconModel> AppIcon;


    public GridImagesModel(Drawable drawable, String strName) {
        this.drawable = drawable;
        this.strName = strName;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<AppIconModel> getAppIcon() {
        return AppIcon;
    }

    public void setAppIcon(List<AppIconModel> appIcon) {
        AppIcon = appIcon;
    }
}
