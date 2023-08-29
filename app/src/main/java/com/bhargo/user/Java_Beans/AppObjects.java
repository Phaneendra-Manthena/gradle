package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.List;

public class AppObjects implements Serializable {

    private String App_ID, App_Desc, App_Icon, App_Created_UserID, App_Created_UserName, App_Created_Date,
            App_Type, App_Icon_Url, APP_DayName;
    private boolean App_Active;
    private List<String> App_Distributed_Users;
    private List<String> App_Permission_Developers;

    public AppObjects(String App_ID, String App_Desc, String App_Type) {
        this.App_ID = App_ID;
        this.App_Desc = App_Desc;
        this.App_Type = App_Type;
    }

    public AppObjects(String APP_DayName) {
        this.APP_DayName = APP_DayName;

    }

    public String getApp_ID() {
        return App_ID;
    }

    public void setApp_ID(String App_ID) {
        App_ID = App_ID;
    }

    public String getApp_Desc() {
        return App_Desc;
    }

    public void setApp_Desc(String App_Desc) {
        App_Desc = App_Desc;
    }

    public String getApp_Icon() {
        return App_Icon;
    }

    public void setApp_Icon(String App_Icon) {
        App_Icon = App_Icon;
    }

    public String getApp_Created_UserID() {
        return App_Created_UserID;
    }

    public void setApp_Created_UserID(String App_Created_UserID) {
        App_Created_UserID = App_Created_UserID;
    }

    public String getApp_Created_UserName() {
        return App_Created_UserName;
    }

    public void setApp_Created_UserName(String App_Created_UserName) {
        App_Created_UserName = App_Created_UserName;
    }

    public String getApp_Created_Date() {
        return App_Created_Date;
    }

    public void setApp_Created_Date(String App_Created_Date) {
        App_Created_Date = App_Created_Date;
    }

    public String getApp_Type() {
        return App_Type;
    }

    public void setApp_Type(String App_Type) {
        App_Type = App_Type;
    }

    public String getApp_Icon_Url() {
        return App_Icon_Url;
    }

    public void setApp_Icon_Url(String App_Icon_Url) {
        App_Icon_Url = App_Icon_Url;
    }

    public boolean isApp_Active() {
        return App_Active;
    }

    public void setApp_Active(boolean App_Active) {
        App_Active = App_Active;
    }

    public List<String> getApp_Distributed_Users() {
        return App_Distributed_Users;
    }

    public void setApp_Distributed_Users(List<String> App_Distributed_Users) {
        App_Distributed_Users = App_Distributed_Users;
    }

    public List<String> getApp_Permission_Developers() {
        return App_Permission_Developers;
    }

    public void setApp_Permission_Developers(List<String> App_Permission_Developers) {
        App_Permission_Developers = App_Permission_Developers;
    }

    public String getAPP_DayName() {
        return APP_DayName;
    }

    public void setAPP_DayName(String APP_DayName) {
        this.APP_DayName = APP_DayName;
    }
}
