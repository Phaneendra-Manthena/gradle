package com.bhargo.user.pojos;

import java.io.Serializable;

public class OfflineSaveRequestPojo implements Serializable {

    String RowID;

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    String AppName;
    String ActionType;// = "ActionType";
    String QueryType;// = "QueryType";////manageDataActionType = "Wizard"
    String ActionID;// = "ActionID";
    String ActionName;// = "ActionName";
    String ExistingTableName ;//= "ExistingTableName";//Group DML Names  //Table Name(insert/update)
    String TypeOfInput ;//= "TypeOfInput";//mapExistingType = "Insert",Type of Input
    String FilesControlNames;// = "FilesControlNames";
    String TempCol;// = "TempCol";
    String SendingObj;// = "SendingObj";

    public String getRowID() {
        return RowID;
    }

    public void setRowID(String rowID) {
        RowID = rowID;
    }

    public String getActionType() {
        return ActionType;
    }

    public void setActionType(String actionType) {
        ActionType = actionType;
    }

    public String getQueryType() {
        return QueryType;
    }

    public void setQueryType(String queryType) {
        QueryType = queryType;
    }

    public String getActionID() {
        return ActionID;
    }

    public void setActionID(String actionID) {
        ActionID = actionID;
    }

    public String getActionName() {
        return ActionName;
    }

    public void setActionName(String actionName) {
        ActionName = actionName;
    }

    public String getExistingTableName() {
        return ExistingTableName;
    }

    public void setExistingTableName(String existingTableName) {
        ExistingTableName = existingTableName;
    }

    public String getTypeOfInput() {
        return TypeOfInput;
    }

    public void setTypeOfInput(String typeOfInput) {
        TypeOfInput = typeOfInput;
    }

    public String getFilesControlNames() {
        return FilesControlNames;
    }

    public void setFilesControlNames(String filesControlNames) {
        FilesControlNames = filesControlNames;
    }

    public String getTempCol() {
        return TempCol;
    }

    public void setTempCol(String tempCol) {
        TempCol = tempCol;
    }

    public String getSendingObj() {
        return SendingObj;
    }

    public void setSendingObj(String sendingObj) {
        SendingObj = sendingObj;
    }


}
