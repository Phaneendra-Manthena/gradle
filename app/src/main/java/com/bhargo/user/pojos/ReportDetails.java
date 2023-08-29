package com.bhargo.user.pojos;

public class ReportDetails {

    String AppName;
    String Version;
    String App_Type;
    String Z_Status_flag;


    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getVersion() {
        return Version;
    }


    public String getApp_Type() {
        return App_Type;
    }

    public void setApp_Type(String app_Type) {
        App_Type = app_Type;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getZ_Status_flag() {
        return Z_Status_flag;
    }

    public void setZ_Status_flag(String z_Status_flag) {
        Z_Status_flag = z_Status_flag;
    }
}
