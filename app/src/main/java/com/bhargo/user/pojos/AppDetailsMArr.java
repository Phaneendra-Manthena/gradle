package com.bhargo.user.pojos;

public class AppDetailsMArr {


    public String pagename;

    public String version;

//    public String AppType;
    public String did;
    public String SharingVersion;


    public String getPagename() {
        return pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

  /*  public String getAppType() {
        return AppType;
    }

    public void setAppType(String appType) {
        AppType = appType;
    }*/

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getSharingVersion() {
        return SharingVersion;
    }

    public void setSharingVersion(String sharingVersion) {
        SharingVersion = sharingVersion;
    }
}
