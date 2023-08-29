package com.bhargo.user.Java_Beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class TableSettingSObject_Bean implements Serializable {

    private String primaryKey;
    private String primaryKey_Message;

    private List<ForeignKey_Bean> foreignKeys;

    private List<String> compositeKeys;


    private boolean replaceOnSameRow;
    private boolean rejectWithMessage;
    private String errorMessage;


    private String subFormInMainForm ="";
    public HashMap<String,String> mainFormInSubForm = new HashMap<String, String>();

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public List<ForeignKey_Bean> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(List<ForeignKey_Bean> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    public List<String> getCompositeKeys() {
        return compositeKeys;
    }

    public void setCompositeKeys(List<String> compositeKeys) {
        this.compositeKeys = compositeKeys;
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

    public HashMap<String, String> getMainFormInSubForm() {
        return mainFormInSubForm;
    }

    public void setMainFormInSubForm(HashMap<String, String> mainFormInSubForm) {
        this.mainFormInSubForm = mainFormInSubForm;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSubFormInMainForm() {
        return subFormInMainForm;
    }

    public void setSubFormInMainForm(String subFormInMainForm) {
        this.subFormInMainForm = subFormInMainForm;
    }

    public String getPrimaryKey_Message() {
        return primaryKey_Message;
    }

    public void setPrimaryKey_Message(String primaryKey_Message) {
        this.primaryKey_Message = primaryKey_Message;
    }
}
