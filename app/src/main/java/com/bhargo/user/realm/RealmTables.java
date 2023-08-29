package com.bhargo.user.realm;

public class RealmTables {
    //Realm Table
    public static class SaveOffline {
        public static final String TABLE_NAME = "SaveOffline";
        public static final String AppName = "AppName";
        public static final String ActionID = "ActionID";
        public static final String ActionType = "ActionType";
        public static final String Response = "Response";

        public static final String[] cols = new String[]{AppName, ActionID, ActionType, Response};
    }

    //Realm Table

    public static class ControlKeys {
        public static final String TABLE_NAME = "ControlKeys";
        public static final String ControlName = "ControlName";
        public static final String KeyID = "KeyID";
        public static final String KeyName = "KeyName";
        public static final String[] cols = new String[]{ControlName, KeyID, KeyName};

    }

    //SQL Table
    public static class SaveRequestTable {
        public static final String TABLE_NAME_ = "SaveRequest";
        public static final String AppName = "AppName";
        public static final String ActionType = "ActionType";
        public static final String QueryType = "QueryType";////manageDataActionType = "Wizard"
        public static final String ActionID = "ActionID";
        public static final String ActionName = "ActionName";
        public static final String ExistingTableName = "ExistingTableName";//Group DML Names  //Table Name(insert/update)
        public static final String TypeOfInput = "TypeOfInput";//mapExistingType = "Insert",Type of Input
        public static final String FilesControlNames = "FilesControlNames";
        public static final String TempCol = "TempCol";
        public static final String SendingObj = "SendingObj";
        public static final String colsWithRowID="rowid,ActionType, QueryType, ActionID, ActionName, ExistingTableName, TypeOfInput,FilesControlNames, TempCol,SendingObj,AppName";
        public static final String[] cols = new String[]{AppName,ActionType, QueryType, ActionID, ActionName, ExistingTableName, TypeOfInput, FilesControlNames, TempCol, SendingObj};

    }

    public static class APIMapping {
        public static final String TABLE_NAME = "APIMapping";
        public static final String ActionID = "ActionID";
        public static final String ActionIDWithTableName = "ActionIDWithTableName";
        public static final String MapppingID = "MapppingID";
        public static final String[] cols = new String[]{ActionID,ActionIDWithTableName, MapppingID};

    }

    public static class APIModifyCol {
        public static final String TABLE_NAME = "APIModifyCol";
        public static final String ActionID = "ActionID";
        public static final String ColName = "ColName";
        public static final String ModifyColName = "ModifyColName";
        public static final String[] cols = new String[]{ActionID,ColName, ModifyColName};
    }

    public static class APIJsonArrayWithoutKey {
        public static final String TABLE_NAME = "APIJsonArrayWithoutKey";
        public static final String ActionID = "ActionID";
        public static final String ColName = "ColName";
        public static final String[] cols = new String[]{ActionID, ColName};
    }


}
