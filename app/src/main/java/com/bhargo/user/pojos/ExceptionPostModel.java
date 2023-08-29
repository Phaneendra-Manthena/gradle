package com.bhargo.user.pojos;

public class ExceptionPostModel {

    private String UserId;
    private String OrgId;
    private String AppName;
    private String Activity;
    private String ExceptionType;
    private String ExceptionStack;
    private String PhoneModel;
    private final String loginType="Builder";

    public ExceptionPostModel(String userId, String orgId, String appName, String activity, String exceptionType, String exceptionStack, String phoneModel) {
        UserId = userId;
        OrgId = orgId;
        AppName = appName;
        Activity = activity;
        ExceptionType = exceptionType;
        ExceptionStack = exceptionStack;
        PhoneModel = phoneModel;
    }

    public String getLoginType() {
        return loginType;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public String getExceptionType() {
        return ExceptionType;
    }

    public void setExceptionType(String exceptionType) {
        ExceptionType = exceptionType;
    }

    public String getExceptionStack() {
        return ExceptionStack;
    }

    public void setExceptionStack(String exceptionStack) {
        ExceptionStack = exceptionStack;
    }

    public String getPhoneModel() {
        return PhoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        PhoneModel = phoneModel;
    }
}
