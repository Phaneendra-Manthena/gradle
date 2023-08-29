package com.bhargo.user.pojos;

import android.content.ContentValues;

public class OfflineDataTransaction {

    ContentValues contentValues;
    ContentValues subFormContentValuesUpdate;
    String subFormName;
    String tableName;
    String transIDColumn;
    String transIDValue;
    String mainFormTransID;
    String mainFormTable;
    String mainFormTransIDColumn;
    String errorMessage;
    private boolean replaceOnSameRow;
    private boolean rejectWithMessage;
    private String compositeKeyErrorMessage;
    private String subFormTableSettingsType;
    private String subFormTableMapExistingType;

    public ContentValues getContentValues() {
        return contentValues;
    }

    public void setContentValues(ContentValues contentValues) {
        this.contentValues = contentValues;
    }

    public ContentValues getSubFormContentValuesUpdate() {
        return subFormContentValuesUpdate;
    }

    public void setSubFormContentValuesUpdate(ContentValues subFormContentValuesUpdate) {
        this.subFormContentValuesUpdate = subFormContentValuesUpdate;
    }

    public String getSubFormName() {
        return subFormName;
    }

    public void setSubFormName(String subFormName) {
        this.subFormName = subFormName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTransIDColumn() {
        return transIDColumn;
    }

    public void setTransIDColumn(String transIDColumn) {
        this.transIDColumn = transIDColumn;
    }

    public String getTransIDValue() {
        return transIDValue;
    }

    public void setTransIDValue(String transIDValue) {
        this.transIDValue = transIDValue;
    }

    public String getMainFormTransID() {
        return mainFormTransID;
    }

    public void setMainFormTransID(String mainFormTransID) {
        this.mainFormTransID = mainFormTransID;
    }

    public String getMainFormTable() {
        return mainFormTable;
    }

    public void setMainFormTable(String mainFormTable) {
        this.mainFormTable = mainFormTable;
    }

    public String getMainFormTransIDColumn() {
        return mainFormTransIDColumn;
    }

    public void setMainFormTransIDColumn(String mainFormTransIDColumn) {
        this.mainFormTransIDColumn = mainFormTransIDColumn;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isReplaceOnSameRow() {
        return replaceOnSameRow;
    }

    public void setReplaceOnSameRow(boolean replaceOnSameRow) {
        this.replaceOnSameRow = replaceOnSameRow;
    }

    public boolean isRejectWithMessage() {
        return rejectWithMessage;
    }

    public void setRejectWithMessage(boolean rejectWithMessage) {
        this.rejectWithMessage = rejectWithMessage;
    }

    public String getCompositeKeyErrorMessage() {
        return compositeKeyErrorMessage;
    }

    public void setCompositeKeyErrorMessage(String compositeKeyErrorMessage) {
        this.compositeKeyErrorMessage = compositeKeyErrorMessage;
    }

    public String getSubFormTableSettingsType() {
        return subFormTableSettingsType;
    }

    public void setSubFormTableSettingsType(String subFormTableSettingsType) {
        this.subFormTableSettingsType = subFormTableSettingsType;
    }

    public String getSubFormTableMapExistingType() {
        return subFormTableMapExistingType;
    }

    public void setSubFormTableMapExistingType(String subFormTableMapExistingType) {
        this.subFormTableMapExistingType = subFormTableMapExistingType;
    }
}
