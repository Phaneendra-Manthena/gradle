package com.bhargo.user.pojos;

public class GetOfflineData {


    public String Sno;
    public String app_name;
    public String OrgId;
    public String CreatedUserID;
    public String SubmittedUserID;
    public String DistributionID;
    public String IMEI;
    public String AppVersion;
    public String OperationType;
    public String TransID;

    public String prepared_json_string;
    public String prepared_files_json_string;
    public String prepared_json_string_subform;
    public String prepared_files_json_string_subform;
    public String offline_json;
    public String sync_status;
    public String rec_count;

    public String getSno() {
        return Sno;
    }

    public void setSno(String sno) {
        Sno = sno;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    public String getCreatedUserID() {
        return CreatedUserID;
    }

    public void setCreatedUserID(String createdUserID) {
        CreatedUserID = createdUserID;
    }

    public String getSubmittedUserID() {
        return SubmittedUserID;
    }

    public void setSubmittedUserID(String submittedUserID) {
        SubmittedUserID = submittedUserID;
    }

    public String getDistributionID() {
        return DistributionID;
    }

    public void setDistributionID(String distributionID) {
        DistributionID = distributionID;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getOperationType() {
        return OperationType;
    }

    public void setOperationType(String operationType) {
        OperationType = operationType;
    }

    public String getTransID() {
        return TransID;
    }

    public void setTransID(String transID) {
        TransID = transID;
    }

    public String getPrepared_json_string() {
        return prepared_json_string;
    }

    public void setPrepared_json_string(String prepared_json_string) {
        this.prepared_json_string = prepared_json_string;
    }

    public String getPrepared_files_json_string() {
        return prepared_files_json_string;
    }

    public void setPrepared_files_json_string(String prepared_files_json_string) {
        this.prepared_files_json_string = prepared_files_json_string;
    }

    public String getPrepared_json_string_subform() {
        return prepared_json_string_subform;
    }

    public void setPrepared_json_string_subform(String prepared_json_string_subform) {
        this.prepared_json_string_subform = prepared_json_string_subform;
    }

    public String getPrepared_files_json_string_subform() {
        return prepared_files_json_string_subform;
    }

    public void setPrepared_files_json_string_subform(String prepared_files_json_string_subform) {
        this.prepared_files_json_string_subform = prepared_files_json_string_subform;
    }

    public String getSync_status() {
        return sync_status;
    }

    public void setSync_status(String sync_status) {
        this.sync_status = sync_status;
    }

    public String getRec_count() {
        return rec_count;
    }

    public void setRec_count(String rec_count) {
        this.rec_count = rec_count;
    }

    public String getOffline_json() {
        return offline_json;
    }

    public void setOffline_json(String offline_json) {
        this.offline_json = offline_json;
    }
}
