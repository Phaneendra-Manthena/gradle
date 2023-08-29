package com.bhargo.user.Java_Beans;

import java.util.ArrayList;

public class UserAndAppData {

  public String OrgId;
    public  String PageName;
    public String CreatedUserID;
    public String SubmittedUserID;
    public String IMEI;
    public String DistributionID;
    public String OperationType;
    public String TransID;
    public String TableName;
    public ArrayList<AllControls> DataFields;

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    public String getPageName() {
        return PageName;
    }

    public void setPageName(String pageName) {
        PageName = pageName;
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

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getDistributionID() {
        return DistributionID;
    }

    public void setDistributionID(String distributionID) {
        DistributionID = distributionID;
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

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public ArrayList<AllControls> getDataFields() {
        return DataFields;
    }

    public void setDataFields(ArrayList<AllControls> dataFields) {
        DataFields = dataFields;
    }
}
