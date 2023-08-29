package com.bhargo.user.utils;

public class ImproveDBTables {

    public static final String[] allTables = new String[]{SampleTable.TABLE_NAME};

    public static class SampleTable {
        public static final String TABLE_NAME = "SampleTable";
        public static final String ColName1 = "ColName1";
        public static final String ColName2 = "ColName2";

        public static final String[] cols = {ColName1, ColName2};
    }


}
