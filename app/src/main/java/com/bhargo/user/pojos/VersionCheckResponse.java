package com.bhargo.user.pojos;

public class VersionCheckResponse {

    public String Status;
    public String Message;
    public String ApkVersion;

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

    public String getApkVersion() {
        return ApkVersion;
    }

    public void setApkVersion(String apkVersion) {
        ApkVersion = apkVersion;
    }
}
