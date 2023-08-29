package com.bhargo.user.pojos;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class OfflineDataSync {

    public String Sno;
    public String app_name;
    public String OrgId;
    public String CreatedUserID;
    public String SubmittedUserID;
    public String SubmittedUserPostID;
    public String DistributionID;
    public String IMEI;
    public String AppVersion;
    public String OperationType;
    public String TransID;

    public String TableName;
    public String TableSettingsType;
    public String MapExistingType;
    public String HasMapExisting;
    public String insertFields;
    public String updateFields;
    public String filterFields;
    public String subFormMapExistingData;

    public String prepared_json_string;
    public String prepared_files_json_string;
    public String prepared_json_string_subform;
    public String prepared_files_json_string_subform;
    public String sync_status;
    public String offline_json;
    public String rec_count;
    public String isCheckList;
    public String checklistNames;

    public String Sync_Type;//App or Request or Response
    public CallAPIRequestDataSync CallAPIRequest;

    public HashMap<String,String> list_Request=new LinkedHashMap<>();


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

    public String getSync_Type() {
        return Sync_Type;
    }

    public void setSync_Type(String sync_Type) {
        Sync_Type = sync_Type;
    }

    public CallAPIRequestDataSync getCallAPIRequest() {
        return CallAPIRequest;
    }

    public void setCallAPIRequest(CallAPIRequestDataSync callAPIRequest) {
        CallAPIRequest = callAPIRequest;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getTableSettingsType() {
        return TableSettingsType;
    }

    public void setTableSettingsType(String tableSettingsType) {
        TableSettingsType = tableSettingsType;
    }

    public String getMapExistingType() {
        return MapExistingType;
    }

    public void setMapExistingType(String mapExistingType) {
        MapExistingType = mapExistingType;
    }

    public String getOffline_json() {
        return offline_json;
    }

    public void setOffline_json(String offline_json) {
        this.offline_json = offline_json;
    }

    public String getHasMapExisting() {
        return HasMapExisting;
    }

    public void setHasMapExisting(String hasMapExisting) {
        HasMapExisting = hasMapExisting;
    }

    public String getInsertFields() {
        return insertFields;
    }

    public void setInsertFields(String insertFields) {
        this.insertFields = insertFields;
    }

    public String getUpdateFields() {
        return updateFields;
    }

    public void setUpdateFields(String updateFields) {
        this.updateFields = updateFields;
    }

    public String getFilterFields() {
        return filterFields;
    }

    public void setFilterFields(String filterFields) {
        this.filterFields = filterFields;
    }

    public String getSubFormMapExistingData() {
        return subFormMapExistingData;
    }

    public void setSubFormMapExistingData(String subFormMapExistingData) {
        this.subFormMapExistingData = subFormMapExistingData;
    }

    public HashMap<String, String> getList_Request() {
        return list_Request;
    }

    public void setList_Request(HashMap<String, String> list_Request) {
        this.list_Request = list_Request;
    }

    public String getSubmittedUserPostID() {
        return SubmittedUserPostID;
    }

    public void setSubmittedUserPostID(String submittedUserPostID) {
        SubmittedUserPostID = submittedUserPostID;
    }

    public String getIsCheckList() {
        return isCheckList;
    }

    public void setIsCheckList(String isCheckList) {
        this.isCheckList = isCheckList;
    }

    public String getChecklistNames() {
        return checklistNames;
    }

    public void setChecklistNames(String checklistNames) {
        this.checklistNames = checklistNames;
    }
}
