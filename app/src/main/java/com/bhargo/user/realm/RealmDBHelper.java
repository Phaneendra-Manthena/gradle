package com.bhargo.user.realm;

import android.content.Context;

import com.bhargo.user.Java_Beans.API_OutputParam_Bean;
import com.bhargo.user.Java_Beans.AssignControl_Bean;
import com.bhargo.user.Java_Beans.DataTableColumn_Bean;
import com.bhargo.user.Java_Beans.LanguageMapping;
import com.bhargo.user.Java_Beans.New_DataControl_Bean;
import com.bhargo.user.Java_Beans.SearchItems;
import com.bhargo.user.pojos.DataControls;
import com.bhargo.user.pojos.MultiColWithPoint;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObjectSchema;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.RealmSchema;
import io.realm.Sort;

public class RealmDBHelper {

    private static final String TAG = "RealmDBHelper";

    public static void initializeRealm(Context context) {
        // on below line we are
        // initializing our realm database.
        Realm.init(context);

        // on below line we are setting realm configuration
        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .name("BhargoUser_Realm.db")
                        .schemaVersion(1)
                        // below line is to allow write
                        // data to database on ui thread.
                        .allowWritesOnUiThread(true)
                        .allowQueriesOnUiThread(true)
                        // below line is to delete realm
                        // if migration is needed.
                        .deleteRealmIfMigrationNeeded()
                        // at last we are calling a method to build.
                        .build();
        // on below line we are setting
        // configuration to our realm database.

        Realm.setDefaultConfiguration(config);
    }

    public static DynamicRealm getDynamicRealm() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("BhargoUser_DynRealm.db")
                .schemaVersion(1)
                .allowWritesOnUiThread(true)
                .allowQueriesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                //.inMemory()
                .build();
        //DynamicRealm dynamicRealm= DynamicRealm.getInstance(realmConfig);
        DynamicRealm dynamicRealm = DynamicRealm.getInstance(Realm.getDefaultConfiguration());
        return dynamicRealm;
    }

    public static boolean existTable(Context context, String tableName) {
        boolean flag = false;
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            if (dynrealm.getSchema().contains(tableName)) {
                flag = true;
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return flag;
    }

    public static void deleteTables(Context context, List<String> tableNames) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    for (int i = 0; i < tableNames.size(); i++) {
                        if (realm.getSchema().contains(tableNames.get(i))) {
                            realm.getSchema().remove(tableNames.get(i));
                        }
                    }
                }
            });

        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }

    public static void deleteTable(Context context, String tableName) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {

                    realm.getSchema().remove(tableName);
                }
            });

        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }
  /*  public static MultiColWithPoint getTableDataInMultiColWithPoint(Context context, String tableName, String selColName, boolean allCols, String pointColName) {
        DynamicRealm dynrealm = null;
        MultiColWithPoint multiColWithPoint = new MultiColWithPoint();
        try {
            dynrealm = getDynamicRealm();
            if (existTable(context, tableName, dynrealm)) {

                RealmResults<DynamicRealmObject> realmResults = dynrealm.where(tableName).findAll();
                if (realmResults.size() > 0) {
                    List<String> listOfPoints = new ArrayList<>();
                    LinkedHashMap<String, List<String>> map_pointtoCols = new LinkedHashMap<String, List<String>>();
                    if (allCols) {
                        String[] colNames = dynrealm.getSchema().get(tableName).getFieldNames().toArray(new String[0]);
                        for (int i = 0; i < realmResults.size(); i++) {
                            String pointColVal=realmResults.get(i).getString(pointColName);
                            List<String> selColValL=new ArrayList<>();
                            for (int cols = 0; cols < colNames.length; cols++) {
                                selColValL.add(realmResults.get(i).getString(colNames[cols]));
                            }
                            String[] RowPositions = pointColVal.split("\\^");
                            for (int j = 0; j < RowPositions.length; j++) {
                                listOfPoints.add(RowPositions[j]);
                                map_pointtoCols.put(RowPositions[j],selColValL);
                            }
                        }
                    } else {
                        for (int i = 0; i < realmResults.size(); i++) {
                            List<String> selColValL=new ArrayList<>();
                            String selColVal=realmResults.get(i).getString(selColName);
                            selColValL.add(selColVal);
                            String pointColVal=realmResults.get(i).getString(pointColName);
                            String[] RowPositions = pointColVal.split("\\^");
                            for (int j = 0; j < RowPositions.length; j++) {
                                listOfPoints.add(RowPositions[j]);
                                map_pointtoCols.put(RowPositions[j],selColValL);
                            }
                        }
                    }
                    multiColWithPoint.setMap_pointtoCols(map_pointtoCols);
                    multiColWithPoint.setListOfPoints(listOfPoints);
                }
            } else {
                multiColWithPoint.setListOfPoints(new ArrayList<>());
                multiColWithPoint.setMap_pointtoCols(new LinkedHashMap<>());
                ImproveHelper.showToast(context, "No Table!");
            }
        } catch (Exception e) {
            multiColWithPoint.setListOfPoints(new ArrayList<>());
            multiColWithPoint.setMap_pointtoCols(new LinkedHashMap<>());
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return multiColWithPoint;
    }*/

    public static void removeAPIMappingTableBasedOnActionID(Context context, String actionID) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    try {
                        RealmResults<DynamicRealmObject> realmResults = realm.where(RealmTables.APIMapping.TABLE_NAME).
                                equalTo(RealmTables.APIMapping.ActionID, actionID).findAll();
                        for (int i = 0; i < realmResults.size(); i++) {
                            String mappingTableName = realmResults.get(i).getString(RealmTables.APIMapping.MapppingID);
                            if (realm.getSchema().contains(mappingTableName)) {
                                realm.getSchema().remove(mappingTableName);
                            }
                        }
                        realmResults.deleteAllFromRealm();
                    } catch (Exception e) {
                        ImproveHelper.showToast(context, e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }

    public static void createTableWithInsertForAPIJsonArrayWithoutKey(Context context, String actionID, String colName) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    try {
                        //APIJsonArrayWithoutKey Table
                        //create table if not exist
                        if (!realm.getSchema().contains(RealmTables.APIJsonArrayWithoutKey.TABLE_NAME)) {
                            RealmObjectSchema tableSchema = realm.getSchema().create(RealmTables.APIJsonArrayWithoutKey.TABLE_NAME);
                            for (int j = 0; j < RealmTables.APIJsonArrayWithoutKey.cols.length; j++) {
                                tableSchema.addField(RealmTables.APIJsonArrayWithoutKey.cols[j], String.class, FieldAttribute.REQUIRED);
                            }
                        }
                        ////before insert check colName exist or no
                        RealmResults<DynamicRealmObject> check = realm.where(RealmTables.APIJsonArrayWithoutKey.TABLE_NAME).
                                equalTo(RealmTables.APIJsonArrayWithoutKey.ColName, colName).findAll();
                        if (check.size() == 0) {
                            DynamicRealmObject dynamicRealmObject_apiMap = realm.createObject(RealmTables.APIJsonArrayWithoutKey.TABLE_NAME);
                            dynamicRealmObject_apiMap.set(RealmTables.APIJsonArrayWithoutKey.ActionID, actionID);
                            dynamicRealmObject_apiMap.set(RealmTables.APIJsonArrayWithoutKey.ColName, colName);
                        }
                    } catch (Exception e) {
                        ImproveHelper.showToast(context, e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }


    public static void createTableWithInsertForAPIModifyCol(Context context, String actionID, String colName, String modifyColName) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    try {
                        //APIModifyCol Table
                        //create table if not exist
                        if (!realm.getSchema().contains(RealmTables.APIModifyCol.TABLE_NAME)) {
                            RealmObjectSchema tableSchema = realm.getSchema().create(RealmTables.APIModifyCol.TABLE_NAME);
                            for (int j = 0; j < RealmTables.APIModifyCol.cols.length; j++) {
                                tableSchema.addField(RealmTables.APIModifyCol.cols[j], String.class, FieldAttribute.REQUIRED);
                            }
                        }

                        //insert
                        DynamicRealmObject dynamicRealmObject_apiMap = realm.createObject(RealmTables.APIModifyCol.TABLE_NAME);
                        dynamicRealmObject_apiMap.set(RealmTables.APIModifyCol.ActionID, actionID);
                        dynamicRealmObject_apiMap.set(RealmTables.APIModifyCol.ColName, colName);
                        dynamicRealmObject_apiMap.set(RealmTables.APIModifyCol.ModifyColName, modifyColName);

                    } catch (Exception e) {
                        ImproveHelper.showToast(context, e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }


    public static void createTableWithInsertFromCallApiActionAndAPIMappingInsert(Context context, List<JSONTableColsVals> jsonTableColsVals) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    try {
                        for (int i = 0; i < jsonTableColsVals.size(); i++) {
                            JSONTableColsVals objTableData = jsonTableColsVals.get(i);
                            /*//create table with Mapping ID
                            if (!realm.getSchema().contains(objTableData.getTableName())) {
                                RealmObjectSchema tableSchema = realm.getSchema().create(objTableData.getTableName());
                                for (int j = 0; j < objTableData.getColNames().size(); j++) {
                                    tableSchema.addField(objTableData.getColNames().get(j), String.class, FieldAttribute.REQUIRED);
                                }
                            }
                            //insert
                            DynamicRealmObject dynamicRealmObjecttest = realm.createObject(objTableData.getTableName());
                            for (int k = 0; k < objTableData.getColNames().size(); k++) {
                                dynamicRealmObjecttest.set(objTableData.getColNames().get(k), objTableData.getColValues().get(k));
                            }*/
                            //create table with Mapping ID
                            if (!realm.getSchema().contains(objTableData.getMappingTableName())) {
                                RealmObjectSchema tableSchema = realm.getSchema().create(objTableData.getMappingTableName());
                                for (int j = 0; j < objTableData.getColNames().size(); j++) {
                                    tableSchema.addField(objTableData.getColNames().get(j), String.class, FieldAttribute.REQUIRED);
                                }
                            }
                            //insert
                            DynamicRealmObject dynamicRealmObject = realm.createObject(objTableData.getMappingTableName());
                            for (int k = 0; k < objTableData.getColNames().size(); k++) {
                                dynamicRealmObject.set(objTableData.getColNames().get(k), objTableData.getColValues().get(k));
                            }
                            //APIMapping Table
                            //create table if not exist
                            if (!realm.getSchema().contains(RealmTables.APIMapping.TABLE_NAME)) {
                                RealmObjectSchema tableSchema = realm.getSchema().create(RealmTables.APIMapping.TABLE_NAME);
                                for (int j = 0; j < RealmTables.APIMapping.cols.length; j++) {
                                    tableSchema.addField(RealmTables.APIMapping.cols[j], String.class, FieldAttribute.REQUIRED);
                                }
                            }
                            ////before insert check mappingid exist or no
                            RealmResults<DynamicRealmObject> check = realm.where(RealmTables.APIMapping.TABLE_NAME).
                                    equalTo(RealmTables.APIMapping.MapppingID, objTableData.getMappingTableName()).findAll();
                            if (check.size() == 0) {
                                DynamicRealmObject dynamicRealmObject_apiMap = realm.createObject(RealmTables.APIMapping.TABLE_NAME);
                                dynamicRealmObject_apiMap.set(RealmTables.APIMapping.ActionID, objTableData.getActionId());
                                dynamicRealmObject_apiMap.set(RealmTables.APIMapping.ActionIDWithTableName, objTableData.getTableName());
                                dynamicRealmObject_apiMap.set(RealmTables.APIMapping.MapppingID, objTableData.getMappingTableName());
                            }

                        }
                    } catch (Exception e) {
                        ImproveHelper.showToast(context, e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }


    public static void createTableWithInsertFromCallApiAction(Context context, List<JSONTableColsVals> jsonTableColsVals) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    try {
                        for (int i = 0; i < jsonTableColsVals.size(); i++) {
                            JSONTableColsVals objTableData = jsonTableColsVals.get(i);
                            //create table
                            if (!realm.getSchema().contains(objTableData.getTableName())) {
                                RealmObjectSchema tableSchema = realm.getSchema().create(objTableData.getTableName());
                                for (int j = 0; j < objTableData.getColNames().size(); j++) {
                                    tableSchema.addField(objTableData.getColNames().get(j), String.class, FieldAttribute.REQUIRED);
                                }
                            }
                            //insert
                            DynamicRealmObject dynamicRealmObject = realm.createObject(objTableData.getTableName());
                            for (int k = 0; k < objTableData.getColNames().size(); k++) {
                                dynamicRealmObject.set(objTableData.getColNames().get(k), objTableData.getColValues().get(k));
                            }
                        }
                    } catch (Exception e) {
                        ImproveHelper.showToast(context, e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }


    public static void updateOrInsertDataFromDataControl(Context context, String jsonString) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransactionAsync(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    try {
                        //{"ControlName":"DepartmentMaster","KeyID":"ID","KeyName":"Name","Data":[{"ID":1,"Name":"test","Name_hi":null,"Name_te":null},{"ID":2,"Name":"rr","Name_hi":null,"Name_te":null},{"ID":3,"Name":"uyg","Name_hi":null,"Name_te":null},{"ID":4,"Name":"vkhlb","Name_hi":null,"Name_te":null}]}
                        //{"ControlName":"Naveen","KeyID":"NaveenID","KeyName":"NaveenName","Data":[]}
                        JSONObject jsonObject = new JSONObject(jsonString);
                        String tableName = jsonObject.getString("ControlName");
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        if (jsonArray.length() > 0) {
                            //get cols name and type
                            List<JSONKeyValueType> cols = getJsonKeyAndValues(jsonArray.getJSONObject(0).toString());
                            RealmSchema schema = realm.getSchema();
                            if (schema.contains(tableName)) {
                                //insert
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject insertData = jsonArray.getJSONObject(j);
                                    DynamicRealmObject dynamicRealmObject = realm.createObject(tableName);
                                    for (int k = 0; k < cols.size(); k++) {
                                        JSONKeyValueType col = cols.get(k);
                                        dynamicRealmObject.set(col.getKey(), insertData.getString(col.getKey()));
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        ImproveHelper.showToast(context, e.getMessage());
                    }
                }
            }, new DynamicRealm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    //ImproveHelper.showToast(context, "Table Created !");
                }
            }, new DynamicRealm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    ImproveHelper.showToast(context, error.getMessage());
                }
            });
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
//            System.out.println("DataControls Start EndTime:" + jsonString.substring(0, 100));
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }


    public static void createTableSchema(Context context, String tableName, String[] cols) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransactionAsync(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    try {
                        RealmObjectSchema tableSchema = realm.getSchema().create(tableName);
                        for (int j = 0; j < cols.length; j++) {
                            tableSchema.addField(cols[j], String.class, FieldAttribute.REQUIRED);
                        }
                    } catch (Exception e) {
                        ImproveHelper.showToast(context, e.getMessage());
                    }
                }
            }, new DynamicRealm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    //ImproveHelper.showToast(context, "Table Created !");
                }
            }, new DynamicRealm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    ImproveHelper.showToast(context, error.getMessage());
                }
            });
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }

    }

    public static void createTableAndInsertDataFromDataControl(Context context, String jsonString) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransactionAsync(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    try {
                        //{"ControlName":"DepartmentMaster","KeyID":"ID","KeyName":"Name","Data":[{"ID":1,"Name":"test","Name_hi":null,"Name_te":null},{"ID":2,"Name":"rr","Name_hi":null,"Name_te":null},{"ID":3,"Name":"uyg","Name_hi":null,"Name_te":null},{"ID":4,"Name":"vkhlb","Name_hi":null,"Name_te":null}]}
                        //{"ControlName":"Naveen","KeyID":"NaveenID","KeyName":"NaveenName","Data":[]}
                        JSONObject jsonObject = new JSONObject(jsonString);
                        String tableName = jsonObject.getString("ControlName");
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        if (jsonArray.length() > 0) {
                            insertControlKeys(realm, RealmTables.ControlKeys.cols,
                                    new String[]{jsonObject.getString("ControlName"),
                                            jsonObject.getString("KeyID"),
                                            jsonObject.getString("KeyName")});
                            //get cols name and type
                            List<JSONKeyValueType> cols = getJsonKeyAndValues(jsonArray.getJSONObject(0).toString());
                            RealmSchema schema = realm.getSchema();
                            System.out.println("nk DataControl TableName :" + tableName);
                            if (!schema.contains(tableName)) {
                                //create table
                                RealmObjectSchema tableSchema = schema.create(tableName);
                                for (int j = 0; j < cols.size(); j++) {
                                    JSONKeyValueType col = cols.get(j);
                                    tableSchema.addField(col.getKey(), String.class, FieldAttribute.REQUIRED);
                                }
                                //insert
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject insertData = jsonArray.getJSONObject(j);
                                    DynamicRealmObject dynamicRealmObject = realm.createObject(tableName);
                                    for (int k = 0; k < cols.size(); k++) {
                                        JSONKeyValueType col = cols.get(k);
                                        dynamicRealmObject.set(col.getKey(), insertData.getString(col.getKey()));
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        ImproveHelper.showToast(context, e.getMessage());
                    }
                }
            }, new DynamicRealm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    //ImproveHelper.showToast(context, "Table Created !");
                }
            }, new DynamicRealm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    ImproveHelper.showToast(context, error.getMessage());
                }
            });
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
//            System.out.println("DataControls Start EndTime:" + jsonString.substring(0, 100));
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }

    public static RealmResults<DynamicRealmObject> getTableData(Context context, DynamicRealm dynrealm, String tableName) {
        RealmResults<DynamicRealmObject> realmResults = null;
        try {
            dynrealm = getDynamicRealm();
            realmResults = dynrealm.where(tableName).findAll();
            dynrealm.close();
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        }
        return realmResults;
    }

    public static RealmResults<DynamicRealmObject> getTableData(Context context, String tableName) {
        DynamicRealm dynrealm = null;
        RealmResults<DynamicRealmObject> realmResults = null;
        try {
            dynrealm = getDynamicRealm();
            realmResults = dynrealm.where(tableName).findAll();
            dynrealm.close();
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } /*finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }*/
        return realmResults;
    }

    private static void insertControlKeys(DynamicRealm realm, String[] cols, String[] vals) {
        if (realm.getSchema().contains(RealmTables.ControlKeys.TABLE_NAME)) {
            DynamicRealmObject checkControlKey = realm.where(RealmTables.ControlKeys.TABLE_NAME).equalTo(RealmTables.ControlKeys.ControlName, vals[0]).findFirst();
            //insert
            if (checkControlKey == null) {
                DynamicRealmObject dynamicRealmObject = realm.createObject(RealmTables.ControlKeys.TABLE_NAME);
                for (int k = 0; k < cols.length; k++) {
                    dynamicRealmObject.set(cols[k], vals[k]);
                }
            }
        } else {
            //create and insert
            RealmObjectSchema tableSchema = realm.getSchema().create(RealmTables.ControlKeys.TABLE_NAME);
            for (int j = 0; j < cols.length; j++) {
                tableSchema.addField(cols[j], String.class, FieldAttribute.REQUIRED);
            }
            //insert
            DynamicRealmObject dynamicRealmObject = realm.createObject(RealmTables.ControlKeys.TABLE_NAME);
            for (int k = 0; k < cols.length; k++) {
                dynamicRealmObject.set(cols[k], vals[k]);
            }
        }
    }

    public static String getSingleColDataWithComma(Context context, String tableName, String colName) {
        DynamicRealm dynrealm = null;
        String result = "";
        try {
            dynrealm = getDynamicRealm();
            RealmResults<DynamicRealmObject> realmResults = dynrealm.where(tableName).findAll();
            if (realmResults.size() > 0) {
                for (int i = 0; i < realmResults.size(); i++) {
                    result = result + "," + realmResults.get(i).getString(colName);
                }
                result = result.substring(1);
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return result;


    }
   /* public static void insertFromString(Context context, String tableName,String[] colNames, String data) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    try {
                        //insert
                        String[] spiltWithComma=data.split("\\,");
                        for (int i = 0; i < spiltWithComma.length; i++) {
                            String[] spiltWithPipe=spiltWithComma[i].split("\\|");
                            DynamicRealmObject dynamicRealmObject = realm.createObject(tableName);
                            for (int k = 0; k < colNames.length; k++) {
                                dynamicRealmObject.set(colNames[k], spiltWithPipe[k]);
                            }
                        }
                    } catch (Exception e) {
                        ImproveHelper.showToast(context, e.getMessage());
                    }
                }
            });


        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }*/

    /*public static void createTableFromStringArray(Context context, String tableName, String[] colNames) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    try {
                        //create table
                        RealmObjectSchema tableSchema = realm.getSchema().create(tableName);
                        for (int j = 0; j < colNames.length; j++) {
                            tableSchema.addField(colNames[j], String.class, FieldAttribute.REQUIRED);
                        }
                    } catch (Exception e) {
                        ImproveHelper.showToast(context, e.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }*/


    public static void updateSaveOfflineTable(Context context, String appName, String actionID,
                                              String actionType, String response) {
        //response:Offline/Online
        if (existTable(context, RealmTables.SaveOffline.TABLE_NAME)) {
            DynamicRealmObject dro = getDataWithCond(context, RealmTables.SaveOffline.TABLE_NAME,
                    new String[]{RealmTables.SaveOffline.AppName, RealmTables.SaveOffline.ActionID, RealmTables.SaveOffline.ActionType},
                    new String[]{appName, actionID, actionType});
            if (dro == null) {
                insert(context, RealmTables.SaveOffline.TABLE_NAME,
                        new String[]{RealmTables.SaveOffline.AppName, RealmTables.SaveOffline.ActionID, RealmTables.SaveOffline.ActionType, RealmTables.SaveOffline.Response},
                        new String[]{appName, actionID, actionType, response});
            } else {
                update(context, RealmTables.SaveOffline.TABLE_NAME,
                        new String[]{RealmTables.SaveOffline.AppName, RealmTables.SaveOffline.ActionID, RealmTables.SaveOffline.ActionType},
                        new String[]{appName, actionID, actionType},
                        new String[]{RealmTables.SaveOffline.Response},
                        new String[]{response});
            }
        } else {
            createTable(context, RealmTables.SaveOffline.TABLE_NAME,
                    RealmTables.SaveOffline.cols);
            insert(context, RealmTables.SaveOffline.TABLE_NAME,
                    new String[]{RealmTables.SaveOffline.AppName, RealmTables.SaveOffline.ActionID, RealmTables.SaveOffline.ActionType, RealmTables.SaveOffline.Response},
                    new String[]{appName, actionID, actionType, response});
        }
    }

    public static List<String> getTableNames(Context context, String relativeTableName) {
        List<String> tableNames = new ArrayList<>();
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            Iterator<RealmObjectSchema> iterator = dynrealm.getSchema().getAll().iterator();
            while (iterator.hasNext()) {
                RealmObjectSchema realmObjectSchema = iterator.next();
                if (realmObjectSchema.getClassName().startsWith(relativeTableName)) {
                    tableNames.add(realmObjectSchema.getClassName());
                }
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return tableNames;
    }

    public static boolean existTable(Context context, String tableName, DynamicRealm dynrealm) {
        boolean flag = dynrealm.getSchema().contains(tableName);
        return flag;
    }

    public static List<List<String>> getDataInListOfList(Context context, String tableName, String condition, String whereColName, String whereColValue) {
        DynamicRealm dynrealm = null;
        List<List<String>> result = new ArrayList<>();
        try {
            dynrealm = getDynamicRealm();
            if (existTable(context, tableName, dynrealm)) {
                String[] colNames = dynrealm.getSchema().get(tableName).getFieldNames().toArray(new String[0]);
                RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
                if (condition.equalsIgnoreCase(AppConstants.Conditions_Equals)) {
                    query.equalTo(whereColName, whereColValue);
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_NotEquals)) {
                    query.notEqualTo(whereColName, whereColValue);
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_lessThan)) {
                    query.lessThan(whereColName, Long.parseLong(whereColValue));
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_GreaterThan)) {
                    query.greaterThan(whereColName, Double.parseDouble(whereColValue));
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_LessThanEqualsTo)) {
                    query.lessThanOrEqualTo(whereColName, Long.parseLong(whereColValue));
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_GreaterThanEqualsTo)) {
                    query.greaterThanOrEqualTo(whereColName, Double.parseDouble(whereColValue));
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_Contains)) {
                    query.contains(whereColName, whereColValue);
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_StartsWith)) {
                    query.beginsWith(whereColName, whereColValue);
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_EndsWith)) {
                    query.endsWith(whereColName, whereColValue);
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_IsNull)) {
                    query.isNull(whereColName);
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_IsNotNull)) {
                    query.isNotNull(whereColName);
                } else if (condition.equalsIgnoreCase("InBetween")) {
                    query.between(whereColName, Double.parseDouble(whereColValue), Double.parseDouble(""));
                }
                RealmResults<DynamicRealmObject> realmResults = query.findAll();
                RealmResults<DynamicRealmObject> tempRealmResult = realmResults;
                if (condition.equalsIgnoreCase(AppConstants.Conditions_NearestStringMatch)) {
                    for (int row = 0; row < realmResults.size(); row++) {
                        String db_whereColVal = realmResults.get(row).getString(whereColName);
                        int per = ImproveHelper.lock_match(whereColValue.toLowerCase(), db_whereColVal);
                        if (per == 0) {
                            tempRealmResult.remove(realmResults.get(row));
                        }
                    }
                    tempRealmResult.sort(whereColName);

                }
                for (int row = 0; row < tempRealmResult.size(); row++) {
                    List<String> stringList = new ArrayList<>();
                    for (int col = 0; col < colNames.length; col++) {
                        stringList.add(tempRealmResult.get(col).getString(colNames[col]));
                    }
                    result.add(stringList);
                }
            } else {
                ImproveHelper.showToast(context, "Error: No Table!");
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, "Error: " + e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return result;
    }


    public static List<String> getSingleColDataInList(Context context, String tableName, String returnCol, String condition, String whereColName, String whereColValue, String whereColValueTo) {
        DynamicRealm dynrealm = null;
        List<String> result = new ArrayList<>();
        try {
            dynrealm = getDynamicRealm();
            if (existTable(context, tableName, dynrealm)) {
                RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
                if (condition.equalsIgnoreCase(AppConstants.Conditions_Equals)) {
                    query.equalTo(whereColName, whereColValue);
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_NotEquals)) {
                    query.notEqualTo(whereColName, whereColValue);
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_lessThan)) {
                    query.lessThan(whereColName, Long.parseLong(whereColValue));
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_GreaterThan)) {
                    query.greaterThan(whereColName, Double.parseDouble(whereColValue));
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_LessThanEqualsTo)) {
                    query.lessThanOrEqualTo(whereColName, Long.parseLong(whereColValue));
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_GreaterThanEqualsTo)) {
                    query.greaterThanOrEqualTo(whereColName, Double.parseDouble(whereColValue));
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_Contains)) {
                    query.contains(whereColName, whereColValue);
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_StartsWith)) {
                    query.beginsWith(whereColName, whereColValue);
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_EndsWith)) {
                    query.endsWith(whereColName, whereColValue);
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_IsNull)) {
                    query.isNull(whereColName);
                } else if (condition.equalsIgnoreCase(AppConstants.Conditions_IsNotNull)) {
                    query.isNotNull(whereColName);
                } else if (condition.equalsIgnoreCase("InBetween")) {
                    query.between(whereColName, Double.parseDouble(whereColValue), Double.parseDouble(whereColValueTo));
                }
                RealmResults<DynamicRealmObject> realmResults = query.findAll();
                if (condition.equalsIgnoreCase(AppConstants.Conditions_NearestStringMatch)) {
                    List<SearchItems> searchItems = new ArrayList<>();
                    for (int i = 0; i < realmResults.size(); i++) {
                        String db_retureColVal = realmResults.get(i).getString(returnCol);
                        String db_whereColVal = realmResults.get(i).getString(whereColName);
                        int per = ImproveHelper.lock_match(whereColValue.toLowerCase(), db_whereColVal);
                        if (per > 0) {
                            searchItems.add(new SearchItems(per, db_retureColVal, i));
                        }
                    }
                    Collections.sort(searchItems);
                    if (searchItems.size() > 0) {
                        for (int i = 0; i < searchItems.size(); i++) {
                            result.add(searchItems.get(i).getSearchResult());
                        }
                    }
                } else {
                    for (int i = 0; i < realmResults.size(); i++) {
                        result.add(realmResults.get(i).getString(returnCol));
                    }
                }
            } else {
                ImproveHelper.showToast(context, "No Table!");
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return result;
    }

    public static List<String> getSingleColDataInList(Context context, String tableName, String colName) {
        DynamicRealm dynrealm = null;
        List<String> result = new ArrayList<>();
        try {
            dynrealm = getDynamicRealm();
            if (existTable(context, tableName, dynrealm)) {
                RealmResults<DynamicRealmObject> realmResults = dynrealm.where(tableName).findAll();
                if (realmResults.size() > 0) {
                    for (int i = 0; i < realmResults.size(); i++) {
                        result.add(realmResults.get(i).getString(colName));
                    }
                }
            } else {
                ImproveHelper.showToast(context, "No Table!");
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return result;
    }

    public static MultiColWithPoint getTableDataInMultiColWithPoint(Context context, String tableName, String selColName, boolean allCols, String pointColName) {
        DynamicRealm dynrealm = null;
        MultiColWithPoint multiColWithPoint = new MultiColWithPoint();
        try {
            dynrealm = getDynamicRealm();
            if (existTable(context, tableName, dynrealm)) {

                RealmResults<DynamicRealmObject> realmResults = dynrealm.where(tableName).findAll();
                if (realmResults.size() > 0) {
                    List<String> listOfPoints = new ArrayList<>();
                    LinkedHashMap<String, List<String>> map_pointtoCols = new LinkedHashMap<String, List<String>>();
                    if (allCols) {
                        String[] colNames = dynrealm.getSchema().get(tableName).getFieldNames().toArray(new String[0]);
                        for (int i = 0; i < realmResults.size(); i++) {
                            String pointColVal = realmResults.get(i).getString(pointColName);
                            List<String> selColValL = new ArrayList<>();
                            for (int cols = 0; cols < colNames.length; cols++) {
                                selColValL.add(realmResults.get(i).getString(colNames[cols]));
                            }
                            String[] RowPositions = pointColVal.split("\\^");
                            for (int j = 0; j < RowPositions.length; j++) {
                                listOfPoints.add(RowPositions[j]);
                                map_pointtoCols.put(RowPositions[j], selColValL);
                            }
                        }
                    } else {
                        for (int i = 0; i < realmResults.size(); i++) {
                            List<String> selColValL = new ArrayList<>();
                            String selColVal = realmResults.get(i).getString(selColName);
                            selColValL.add(selColVal);
                            String pointColVal = realmResults.get(i).getString(pointColName);
                            String[] RowPositions = pointColVal.split("\\^");
                            for (int j = 0; j < RowPositions.length; j++) {
                                listOfPoints.add(RowPositions[j]);
                                map_pointtoCols.put(RowPositions[j], selColValL);
                            }
                        }
                    }
                    multiColWithPoint.setMap_pointtoCols(map_pointtoCols);
                    multiColWithPoint.setListOfPoints(listOfPoints);
                }
            } else {
                multiColWithPoint.setListOfPoints(new ArrayList<>());
                multiColWithPoint.setMap_pointtoCols(new LinkedHashMap<>());
                ImproveHelper.showToast(context, "No Table!");
            }
        } catch (Exception e) {
            multiColWithPoint.setListOfPoints(new ArrayList<>());
            multiColWithPoint.setMap_pointtoCols(new LinkedHashMap<>());
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return multiColWithPoint;
    }

    public static List<JSONKeyValueType> getJsonKeyAndValues(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            List<JSONKeyValueType> jsonKeyValueTypeList = new ArrayList<>();
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Object obj = jsonObject.get(key);

                JSONKeyValueType jsonKeyValueType = new JSONKeyValueType();
                jsonKeyValueType.setKey(key);
                jsonKeyValueType.setValue(String.valueOf(obj));

                if (obj instanceof Integer) {
                    jsonKeyValueType.setType("Integer");
                } else if (obj instanceof Long) {
                    jsonKeyValueType.setType("Long");
                } else if (obj instanceof Double) {
                    jsonKeyValueType.setType("Double");
                } else if (obj instanceof Boolean) {
                    jsonKeyValueType.setType("Boolean");
                } else if (obj instanceof String) {
                    jsonKeyValueType.setType("String");
                } else if (obj instanceof JSONObject) {
                    jsonKeyValueType.setType("JSONObject");
                } else if (obj instanceof JSONArray) {
                    jsonKeyValueType.setType("JSONArray");
                    try {
                        ((JSONArray) obj).getJSONObject(0);
                        jsonKeyValueType.setStrArray(false);
                    } catch (Exception e) {
                        //"Uint":["1","2","3"]
                        //Value U 1 at 0 of type java.lang.String cannot be converted to JSONObject
                        jsonKeyValueType.setStrArray(true);
                    }
                }
                jsonKeyValueTypeList.add(jsonKeyValueType);
            }
            return jsonKeyValueTypeList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<New_DataControl_Bean> getTableDataWithBean(Context context, String tableName, LinkedHashMap<String, String> NameWithSelectedID) {
        List<New_DataControl_Bean> tempData = new ArrayList<>();
        ImproveDataBase improveDataBase = new ImproveDataBase(context);
        DataControls DataControls = improveDataBase.getDataControls(tableName);
        //Get Keys
        DynamicRealm dynRealm = RealmDBHelper.getDynamicRealm();
        dynRealm.executeTransaction(new DynamicRealm.Transaction() {
            @Override
            public void execute(DynamicRealm realm) {
                DynamicRealmObject keysData = realm.where(RealmTables.ControlKeys.TABLE_NAME).equalTo(RealmTables.ControlKeys.ControlName, DataControls.getControlName()).findFirst();
                RealmQuery<DynamicRealmObject> query = realm.where(DataControls.getControlName());
                Set<String> DependencyValues = NameWithSelectedID.keySet();
                String[] DependencyNames = DependencyValues.toArray(new String[DependencyValues.size()]);
                for (int i = 0; i < DependencyNames.length; i++) {
                    if (NameWithSelectedID.get(DependencyNames[i]) != null && !NameWithSelectedID.get(DependencyNames[i]).equals("null"))
                        query.equalTo(DependencyNames[i], NameWithSelectedID.get(DependencyNames[i]));
                }
                RealmResults<DynamicRealmObject> controlData = query.findAll();

                if (keysData != null) {
                    String ControlName = keysData.getString(RealmTables.ControlKeys.ControlName);
                    String KeyID = keysData.getString(RealmTables.ControlKeys.KeyID);
                    String KeyName = keysData.getString(RealmTables.ControlKeys.KeyName);
                    if (!ImproveHelper.getLocale(context).equalsIgnoreCase("") && !ImproveHelper.getLocale(context).equalsIgnoreCase("en")) {
                        KeyName = KeyName + "_" + ImproveHelper.getLocale(context);
                    }
                    for (int i = 0; i < controlData.size(); i++) {
                        DynamicRealmObject dro = controlData.get(i);
                        New_DataControl_Bean dataControl_bean = new New_DataControl_Bean();
                        dataControl_bean.setDc_id(dro.getString(KeyID));
                        dataControl_bean.setDc_value(dro.getString(KeyName));
                        dataControl_bean.setDc_name(ControlName);
                        dataControl_bean.setDc_KeyId(KeyID);
                        dataControl_bean.setDc_KeyName(KeyName);
                        if (DataControls.getDependentControl() != null && !DataControls.getDependentControl().isEmpty()) {
                            dataControl_bean.setDc_dependency(DataControls.getDependentControl());
                        }
                        tempData.add(dataControl_bean);
                    }
                }
            }
        });

        dynRealm.close();
        return tempData;
    }


    public static List<New_DataControl_Bean> getTableDataWithBean(Context context, String tableName) {
        List<New_DataControl_Bean> tempData = new ArrayList<>();
        ImproveDataBase improveDataBase = new ImproveDataBase(context);
        DataControls DataControls = improveDataBase.getDataControls(tableName);
        //Get Keys
        DynamicRealm dynamicRealm = RealmDBHelper.getDynamicRealm();
        DynamicRealmObject keysData = dynamicRealm.where(RealmTables.ControlKeys.TABLE_NAME).equalTo(RealmTables.ControlKeys.ControlName, DataControls.getControlName()).findFirst();
        RealmResults<DynamicRealmObject> controlData = dynamicRealm.
                where(DataControls.getControlName()).findAll();
        if (keysData != null) {
            String ControlName = keysData.getString(RealmTables.ControlKeys.ControlName);
            String KeyID = keysData.getString(RealmTables.ControlKeys.KeyID);
            String KeyName = keysData.getString(RealmTables.ControlKeys.KeyName);
            if (!ImproveHelper.getLocale(context).equalsIgnoreCase("") && !ImproveHelper.getLocale(context).equalsIgnoreCase("en")) {
                KeyName = KeyName + "_" + ImproveHelper.getLocale(context);
            }
            for (int i = 0; i < controlData.size(); i++) {
                DynamicRealmObject dro = controlData.get(i);
                New_DataControl_Bean dataControl_bean = new New_DataControl_Bean();
                dataControl_bean.setDc_id(dro.getString(KeyID));
                dataControl_bean.setDc_value(dro.getString(KeyName));
                dataControl_bean.setDc_name(ControlName);
                dataControl_bean.setDc_KeyId(KeyID);
                dataControl_bean.setDc_KeyName(KeyName);
                if (DataControls.getDependentControl() != null && !DataControls.getDependentControl().isEmpty()) {
                    dataControl_bean.setDc_dependency(DataControls.getDependentControl());
                }
                tempData.add(dataControl_bean);
            }
        }
        dynamicRealm.close();
        return tempData;
    }

    public static String getControlKeyID(Context context, String controlName) {
        String keyid = null;
        //Get Keys
        DynamicRealm dynamicRealm = RealmDBHelper.getDynamicRealm();
        DynamicRealmObject keysData = dynamicRealm.where(RealmTables.ControlKeys.TABLE_NAME).equalTo(RealmTables.ControlKeys.ControlName, controlName).findFirst();
        if (keysData != null) {
            String ControlName = keysData.getString(RealmTables.ControlKeys.ControlName);
            String KeyID = keyid = keysData.getString(RealmTables.ControlKeys.KeyID);
            String KeyName = keysData.getString(RealmTables.ControlKeys.KeyName);
            if (!ImproveHelper.getLocale(context).equalsIgnoreCase("en")) {
                KeyName = KeyName + "_" + ImproveHelper.getLocale(context);
            }
        }
        dynamicRealm.close();
        return keyid;
    }

    public static boolean isAPIJsonArrayWithoutKeyExist(Context context,String actionID,String colName) {
        boolean flag = false;
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            if (dynrealm.getSchema().contains(RealmTables.APIJsonArrayWithoutKey.TABLE_NAME)) {
                RealmQuery<DynamicRealmObject> query = dynrealm.where(RealmTables.APIJsonArrayWithoutKey.TABLE_NAME);
                query.equalTo(RealmTables.APIJsonArrayWithoutKey.ActionID, actionID);
                query.equalTo(RealmTables.APIJsonArrayWithoutKey.ColName, colName);
                if (query.findFirst() != null) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return flag;
    }

    public static boolean isModifyColNameExist(Context context,String colName) {
        boolean flag = false;
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            if (dynrealm.getSchema().contains(RealmTables.APIModifyCol.TABLE_NAME)) {
                RealmQuery<DynamicRealmObject> query = dynrealm.where(RealmTables.APIModifyCol.TABLE_NAME);
                query.equalTo(RealmTables.APIModifyCol.ModifyColName, colName);
                if (query.findFirst() != null) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return flag;
    }
    public static boolean isDataExist(Context context, String tableName, String[] cols, String[] vals) {
        boolean flag = false;
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
            for (int i = 0; i < cols.length; i++) {
                query.equalTo(cols[i], vals[i]);
            }
            if (query.findFirst() != null) {
                flag = true;
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return flag;
    }

    public static Set<String> getColNames(Context context, String tableName) {
        Set<String> stringSet = null;
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            stringSet = dynrealm.getSchema().get(tableName).getFieldNames();
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return stringSet;
    }

    public static boolean rowDataDeleteSingle(Context context, String tableName,
                                              String wherecol, String whereVal) {
        boolean delFlag = false;
        List<String> list = new ArrayList<>();
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            if (existTable(context, tableName, dynrealm)) {

                RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
                query.equalTo(wherecol, whereVal);
                RealmResults<DynamicRealmObject> rowsToDelete = query.findAll();
                dynrealm.beginTransaction();
                delFlag = rowsToDelete.deleteAllFromRealm();
                dynrealm.commitTransaction();
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return delFlag;
    }

    public static boolean rowDataDeleteMultiWithSingleCol(Context context, String tableName,
                                                          String wherecol, List<String> whereVals) {
        boolean multiDelFlag = false;
        List<String> list = new ArrayList<>();
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            if (existTable(context, tableName, dynrealm)) {

                RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
                for (int i = 0; i < whereVals.size(); i++) {
                    query.equalTo(wherecol, whereVals.get(i));
                }
                RealmResults<DynamicRealmObject> rowsToDelete = query.findAll();
                dynrealm.beginTransaction();
                multiDelFlag = rowsToDelete.deleteAllFromRealm();
                dynrealm.commitTransaction();
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return multiDelFlag;
    }

    public static boolean rowDataDeleteMulti(Context context, String tableName,
                                             String[] wherecols, String[] whereVals) {
        boolean multiDelFlag = false;
        List<String> list = new ArrayList<>();
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            if (existTable(context, tableName, dynrealm)) {

                RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
                for (int i = 0; i < wherecols.length; i++) {
                    query.equalTo(wherecols[i], whereVals[i]);
                }
                RealmResults<DynamicRealmObject> rowsToDelete = query.findAll();
                dynrealm.beginTransaction();
                multiDelFlag = rowsToDelete.deleteAllFromRealm();
                dynrealm.commitTransaction();
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return multiDelFlag;
    }

    public static List<List<String>> getTableData(Context context, String tableName, String[] colNames,
                                                  String wherecols, String whereVals) {
        List<List<String>> llist = new ArrayList<>();
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.beginTransaction();
            RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
            query.equalTo(wherecols, whereVals);
            RealmResults<DynamicRealmObject> drealObj = query.findAll();

            for (int i = 0; i < drealObj.size(); i++) {
                List<String> list = new ArrayList<>();
                for (int j = 0; j < colNames.length; j++) {
                    list.add(drealObj.get(i).getString(colNames[j]));
                }
                llist.add(list);
            }

            dynrealm.commitTransaction();
            dynrealm.close();
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        }
        return llist;
    }

    public static List<String> getTableData(Context context, String tableName, String col,
                                            String wherecols, String whereVals) {
        List<String> list = new ArrayList<>();
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            if (existTable(context, tableName, dynrealm)) {
                dynrealm.beginTransaction();
                RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
                query.equalTo(wherecols, whereVals);
                RealmResults<DynamicRealmObject> drealObj = query.findAll();
                for (int i = 0; i < drealObj.size(); i++) {
                    list.add(drealObj.get(i).getString(col));
                }
                dynrealm.commitTransaction();
            }
            dynrealm.close();
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        }
        return list;
    }

    public static LinkedHashMap<String,String> getAPIOutputDataInLMP(Context context, String tableName) {
        LinkedHashMap<String,String> stringStringLinkedHashMap = new LinkedHashMap<>();
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            if (existTable(context, tableName, dynrealm)) {
                dynrealm.beginTransaction();
                RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);

                RealmResults<DynamicRealmObject> drealObj = query.findAll();
                for (int i = 0; i < drealObj.size(); i++) {
                    stringStringLinkedHashMap.put(drealObj.get(i).getString("KeyName"),drealObj.get(i).getString("Path"));
                }
                dynrealm.commitTransaction();
            }
            dynrealm.close();
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        }
        return stringStringLinkedHashMap;
    }

    public static List<String> getDataInRealResults(Context context, String tableName, String col,
                                                    String[] wherecols, String[] whereVals) {
        List<String> list = new ArrayList<>();
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            if (existTable(context, tableName, dynrealm)) {
                dynrealm.beginTransaction();
                RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
                for (int i = 0; i < wherecols.length; i++) {
                    query.equalTo(wherecols[i], whereVals[i]);
                }
                RealmResults<DynamicRealmObject> drealObj = query.findAll();
                for (int i = 0; i < drealObj.size(); i++) {
                    list.add(drealObj.get(i).getString(col));
                }
                dynrealm.commitTransaction();
            }
            dynrealm.close();
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        }
        return list;
    }

    public static List<List<String>> getDataInRealResults(Context context, String tableName, String[] colNames,
                                                          String[] wherecols, String[] whereVals) {
        List<List<String>> llist = new ArrayList<>();
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.beginTransaction();
            RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
            for (int i = 0; i < wherecols.length; i++) {
                query.equalTo(wherecols[i], whereVals[i]);
            }
            RealmResults<DynamicRealmObject> drealObj = query.findAll();

            for (int i = 0; i < drealObj.size(); i++) {
                List<String> list = new ArrayList<>();
                for (int j = 0; j < colNames.length; j++) {
                    list.add(drealObj.get(i).getString(colNames[j]));
                }
                llist.add(list);
            }

            dynrealm.commitTransaction();
            dynrealm.close();
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        }
        return llist;
    }

    public static List<List<String>> getTableDataUsingLike(Context context, String tableName, String[] colNames,
                                                           String[] wherecols, String[] whereVals) {
        List<List<String>> llist = new ArrayList<>();
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.beginTransaction();
            RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
            for (int i = 0; i < wherecols.length; i++) {
                query.equalTo(wherecols[i], whereVals[i]);
            }
            RealmResults<DynamicRealmObject> drealObj = query.findAll();

            for (int i = 0; i < drealObj.size(); i++) {
                List<String> list = new ArrayList<>();
                for (int j = 0; j < colNames.length; j++) {
                    list.add(drealObj.get(i).getString(colNames[j]));
                }
                llist.add(list);
            }

            dynrealm.commitTransaction();
            dynrealm.close();
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        }
        return llist;
    }

    public static DynamicRealmObject getDataWithCondNew(Context context, String tableName, String[] cols, String[] vals) {

        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
            for (int i = 0; i < cols.length; i++) {
                query.equalTo(cols[i], vals[i]);
            }
            return query.findFirst();
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
            return null;
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }

    public static DynamicRealmObject getDataWithCond(Context context, String tableName, String[] cols, String[] vals) {
        DynamicRealmObject drealObj = null;
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
            for (int i = 0; i < cols.length; i++) {
                query.equalTo(cols[i], vals[i]);
            }
            drealObj = query.findFirst();
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return drealObj;
    }

    public static int getCount(Context context, String tableName) {
        int count = 0;
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
            count = query.findAll().size();
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return count;
    }

    public static int getCount(Context context, String tableName, String[] cols, String[] vals) {
        int count = 0;
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
            for (int i = 0; i < cols.length; i++) {
                query.equalTo(cols[i], vals[i]);
            }
            count = query.findAll().size();
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return count;
    }

    public static void createTable(Context context, String tableName, String[] cols) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    RealmObjectSchema tableSchema = realm.getSchema().create(tableName);
                    for (int j = 0; j < cols.length; j++) {
                        tableSchema.addField(cols[j], String.class, FieldAttribute.REQUIRED);
                    }
                }
            });
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }

    public static void update(Context context, String tableName, String[] cols, String[] vals, String[] setCols, String[] setVals) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    RealmQuery<DynamicRealmObject> query = realm.where(tableName);
                    for (int i = 0; i < cols.length; i++) {
                        query.equalTo(cols[i], vals[i]);
                    }
                    DynamicRealmObject drealObj = query.findFirst();
                    for (int i = 0; i < setCols.length; i++) {
                        drealObj.set(setCols[i], setVals[i]);
                    }
                }
            });
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }

    public static void insert(Context context, String tableName, String[] cols, String[] vals) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    DynamicRealmObject dynamicRealmObject = realm.createObject(tableName);
                    for (int k = 0; k < cols.length; k++) {
                        dynamicRealmObject.set(cols[k], vals[k]);
                    }
                }
            });
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }

    public static void insertFromWithLHM(Context context, String tableName, LinkedHashMap<String, List<String>> OutputData) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    try {
                        //get cols name and type
                        Set<String> DependencyValues = OutputData.keySet();
                        String[] cols = DependencyValues.toArray(new String[DependencyValues.size()]);
                        int rows = OutputData.get(cols[0].toLowerCase()) != null ? OutputData.get(cols[0].toLowerCase()).size() : OutputData.get(cols[0]).size();
                        //insert
                        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
                            DynamicRealmObject dynamicRealmObject = realm.createObject(tableName);
                            for (int k = 0; k < cols.length; k++) {
                                String rowData = OutputData.get(cols[k].toLowerCase()).get(rowIndex).trim();
                                dynamicRealmObject.set(cols[k], rowData);
                            }
                        }
                    } catch (Exception e) {
                        ImproveHelper.showToast(context, e.getMessage());
                    }
                }
            });


        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }

    public static void createTableWithLHM(Context context, String tableName, LinkedHashMap<String, List<String>> OutputData) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    try {
                        //get cols name
                        Set<String> DependencyValues = OutputData.keySet();
                        String[] cols = DependencyValues.toArray(new String[DependencyValues.size()]);
                        //create table
                        RealmObjectSchema tableSchema = realm.getSchema().create(tableName);
                        for (int j = 0; j < cols.length; j++) {
                            tableSchema.addField(cols[j], String.class, FieldAttribute.REQUIRED);
                        }
                    } catch (Exception e) {
                        ImproveHelper.showToast(context, e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }

    public static void createTableFromStringArray(Context context, String tableName, String[] colNames) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    try {
                        //create table
                        RealmObjectSchema tableSchema = realm.getSchema().create(tableName);
                        for (int j = 0; j < colNames.length; j++) {
                            tableSchema.addField(colNames[j], String.class, FieldAttribute.REQUIRED);
                        }
                    } catch (Exception e) {
                        ImproveHelper.showToast(context, e.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }

    public static void createTableFromJSONArray(Context context, String tableName, String jsonString) {
        DynamicRealm dynrealm = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            if (jsonArray.length() > 0) {
                dynrealm = getDynamicRealm();
                dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                    @Override
                    public void execute(DynamicRealm realm) {
                        try {
                            //get cols name and type
                            List<JSONKeyValueType> cols = getJsonKeyAndValues(jsonArray.getJSONObject(0).toString());
                            //create table
                            RealmObjectSchema tableSchema = realm.getSchema().create(tableName);
                            for (int j = 0; j < cols.size(); j++) {
                                JSONKeyValueType col = cols.get(j);
                                tableSchema.addField(col.getKey(), String.class, FieldAttribute.REQUIRED);
                            }
                        } catch (Exception e) {
                            ImproveHelper.showToast(context, e.getMessage());
                        }
                    }
                });
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }


    public static void createTableFromMultiDimensionalVariable(Context context, String tableName, String jsonString) {
        DynamicRealm dynrealm = null;
        try {
            JSONObject jMainObj = new JSONObject(jsonString);
            if (jMainObj.getString("Status").equalsIgnoreCase("200")) {
                JSONArray jsonArray = jMainObj.has("FormData") ? jMainObj.getJSONArray("FormData") : jMainObj.getJSONArray("Data");
                if (jsonArray.length() > 0) {
                    dynrealm = getDynamicRealm();
                    dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                        @Override
                        public void execute(DynamicRealm realm) {
                            try {
                                //get cols name and type
                                List<JSONKeyValueType> cols = getJsonKeyAndValues(jsonArray.getJSONObject(0).toString());
                                //create table
                                RealmObjectSchema tableSchema = realm.getSchema().create(tableName);
                                for (int j = 0; j < cols.size(); j++) {
                                    JSONKeyValueType col = cols.get(j);
                                    tableSchema.addField(col.getKey(), String.class, FieldAttribute.REQUIRED);
                                }
                            } catch (Exception e) {
                                ImproveHelper.showToast(context, e.getMessage());
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }


    public static void createTableFromAction(Context context, String tableName, String jsonString) {
        DynamicRealm dynrealm = null;
        try {
            JSONObject jMainObj = new JSONObject(jsonString);
            if (jMainObj.getString("Status").equalsIgnoreCase("200")) {
                JSONArray jsonArray = jMainObj.has("FormData") ? jMainObj.getJSONArray("FormData") : jMainObj.getJSONArray("Data");
                if (jsonArray.length() > 0) {
                    dynrealm = getDynamicRealm();
                    dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                        @Override
                        public void execute(DynamicRealm realm) {
                            try {
                                //get cols name and type
                                List<JSONKeyValueType> cols = getJsonKeyAndValues(jsonArray.getJSONObject(0).toString());
                                //create table
                                RealmObjectSchema tableSchema = realm.getSchema().create(tableName);
                                for (int j = 0; j < cols.size(); j++) {
                                    JSONKeyValueType col = cols.get(j);
                                    tableSchema.addField(col.getKey(), String.class, FieldAttribute.REQUIRED);
                                }
                            } catch (Exception e) {
                                ImproveHelper.showToast(context, e.getMessage());
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }

    public static void insertFromString(Context context, String tableName, String[] colNames, String data) {
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                @Override
                public void execute(DynamicRealm realm) {
                    try {
                        //insert  Bhargo_Sno|1,2#Bhargo_Emp_Id|BHAR00000004,BHAR00000004
                        String[] spiltWithHash= data.split("#");
                        int rows = spiltWithHash[0].split("\\|")[1].split(",").length;
                        for (int i = 0; i <rows ; i++) {
                            DynamicRealmObject dynamicRealmObject = realm.createObject(tableName);
                            for (int k = 0; k < spiltWithHash.length; k++) {
                                String[] spiltWithPipe = spiltWithHash[k].split("\\|");
                                String [] colValues = spiltWithPipe[1].split(",");
                                if(colValues.length!=0&&colValues.length<=rows) {
                                    dynamicRealmObject.set(spiltWithPipe[0], spiltWithPipe[1].split(",")[i]);
                                }else{
                                    dynamicRealmObject.set(spiltWithPipe[0], "");
                                }
                            }
                        }
                        /*for (int i = 0; i < spiltWithHash.length; i++) {
                            String[] spiltWithPipe = spiltWithHash[i].split("\\|");
                            DynamicRealmObject dynamicRealmObject = realm.createObject(tableName);
                            for (int k = 0; k < colNames.length; k++) {
                                String[] spiltWithComma= spiltWithPipe[1].split(",");
                                for (int j = 0; j <spiltWithComma.length ; j++) {
                                    dynamicRealmObject.set(colNames[k], spiltWithComma[j]);
                                }

                            }
                        }*/
                    } catch (Exception e) {
                        ImproveHelper.showToast(context, e.getMessage());
                    }
                }
            });


        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }

    public static void insertFromJSONArray(Context context, String tableName, String jsonString) {
        DynamicRealm dynrealm = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            if (jsonArray.length() > 0) {
                dynrealm = getDynamicRealm();
                dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                    @Override
                    public void execute(DynamicRealm realm) {
                        try {
                            //get cols name and type
                            List<JSONKeyValueType> cols = getJsonKeyAndValues(jsonArray.getJSONObject(0).toString());
                            //insert
                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject insertData = jsonArray.getJSONObject(j);
                                DynamicRealmObject dynamicRealmObject = realm.createObject(tableName);
                                for (int k = 0; k < cols.size(); k++) {
                                    JSONKeyValueType col = cols.get(k);
                                    dynamicRealmObject.set(col.getKey(), insertData.has(col.getKey()) ? insertData.getString(col.getKey()) : "");
                                }
                            }
                        } catch (Exception e) {
                            ImproveHelper.showToast(context, e.getMessage());
                        }
                    }
                });
            }

        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }

    public static void insertFromAction(Context context, String tableName, String jsonString) {
        DynamicRealm dynrealm = null;
        try {
            JSONObject jMainObj = new JSONObject(jsonString);
            if (jMainObj.getString("Status").equalsIgnoreCase("200")) {
                //JSONArray jsonArray = jMainObj.getJSONArray("FormData");
                JSONArray jsonArray = jMainObj.has("FormData") ? jMainObj.getJSONArray("FormData") : jMainObj.getJSONArray("Data");
                if (jsonArray.length() > 0) {
                    dynrealm = getDynamicRealm();
                    dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                        @Override
                        public void execute(DynamicRealm realm) {
                            try {
                                //get cols name and type
                                List<JSONKeyValueType> cols = getJsonKeyAndValues(jsonArray.getJSONObject(0).toString());
                                //insert
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject insertData = jsonArray.getJSONObject(j);
                                    DynamicRealmObject dynamicRealmObject = realm.createObject(tableName);
                                    for (int k = 0; k < cols.size(); k++) {
                                        JSONKeyValueType col = cols.get(k);
                                        dynamicRealmObject.set(col.getKey(), insertData.has(col.getKey()) ? insertData.getString(col.getKey()) : "");
                                    }
                                }
                            } catch (Exception e) {
                                ImproveHelper.showToast(context, e.getMessage());
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }


    public static void createTableWithInsertFromAction(Context context, String tableName, String jsonString) {
        DynamicRealm dynrealm = null;
        try {
            JSONObject jMainObj = new JSONObject(jsonString);
            if (jMainObj.getString("Status").equalsIgnoreCase("200")) {
                JSONArray jsonArray = jMainObj.has("FormData") ? jMainObj.getJSONArray("FormData") : jMainObj.getJSONArray("Data");
                if (jsonArray.length() > 0) {
                    dynrealm = getDynamicRealm();
                    dynrealm.executeTransaction(new DynamicRealm.Transaction() {
                        @Override
                        public void execute(DynamicRealm realm) {
                            try {
                                //get cols name and type
                                List<JSONKeyValueType> cols = getJsonKeyAndValues(jsonArray.getJSONObject(0).toString());
                                //create table
                                RealmObjectSchema tableSchema = realm.getSchema().create(tableName);
                                for (int j = 0; j < cols.size(); j++) {
                                    JSONKeyValueType col = cols.get(j);
                                    tableSchema.addField(col.getKey().toLowerCase(), String.class, FieldAttribute.REQUIRED);
                                }
                                //insert
                                // realm.beginTransaction();
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject insertData = jsonArray.getJSONObject(j);
                                    DynamicRealmObject dynamicRealmObject = realm.createObject(tableName);
                                    for (int k = 0; k < cols.size(); k++) {
                                        JSONKeyValueType col = cols.get(k);
                                        dynamicRealmObject.set(col.getKey().toLowerCase(), insertData.getString(col.getKey()));
                                    }
                                }
                                // realm.commitTransaction();
                            } catch (Exception e) {
                                ImproveHelper.showToast(context, e.getMessage());
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
    }

    private static void createTableWithInsert(DynamicRealm realm, String tableName, List<JSONKeyValueType> cols, JSONArray data) throws JSONException {
        //create table
        RealmObjectSchema tableSchema = realm.getSchema().create(tableName);
        for (int j = 0; j < cols.size(); j++) {
            JSONKeyValueType col = cols.get(j);
            tableSchema.addField(col.getKey(), String.class, FieldAttribute.REQUIRED);
        }
        //insert
        for (int j = 0; j < data.length(); j++) {
            JSONObject insertData = data.getJSONObject(j);
            DynamicRealmObject dynamicRealmObject = realm.createObject(tableName);
            for (int k = 0; k < cols.size(); k++) {
                JSONKeyValueType col = cols.get(k);
                dynamicRealmObject.set(col.getKey(), insertData.getString(col.getKey()));
            }
        }
    }

    public static String getTableDataInJSONArray(Context context, String tableName) {
        String jsonArray = "[]";
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            if (existTable(context, tableName, dynrealm)) {
                RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
                RealmResults<DynamicRealmObject> drealObj = query.findAll();
                jsonArray = drealObj.asJSON();
            }
        } catch (Exception e) {
            jsonArray = "[]";
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return jsonArray;
    }


    public static LinkedHashMap<String, List<String>> getAPIDataInLHMBasedOnAssignControl_Bean(Context context,
                                                                        String tableName,
                                                                        List<String> colNames, List<AssignControl_Bean> assignControl_beans) {
        LinkedHashMap<String, List<String>> dataMap = new LinkedHashMap<>();
        //List<List<String>> llData = new ArrayList<>();
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            if (existTable(context, tableName, dynrealm)) {
                //String[] colNames = dynrealm.getSchema().get(tableName).getFieldNames().toArray(new String[0]);
                RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
                RealmResults<DynamicRealmObject> drealObj = query.findAll();
                for (int col = 0; col < colNames.size(); col++) {
                    String hashKey = assignControl_beans.get(col).getControlValue().trim();
                    List<String> collist = new ArrayList<>();
                    for (int row = 0; row < drealObj.size(); row++) {
                        DynamicRealmObject dynamicRealmObject = drealObj.get(row);
                        String data = "";
                        try {
                            data = dynamicRealmObject.getString(colNames.get(col));
                        } catch (Exception e) {
                        }
                        collist.add(data);
                    }
                    if (!dataMap.containsKey(hashKey)) {
                        dataMap.put(hashKey, collist);
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return dataMap;
    }

    public static LinkedHashMap<String, List<String>> getTableDataInLHM(Context context,
                                                                        String tableName,
                                                                        List<String> colNames, List<API_OutputParam_Bean> List_API_OutParams) {
        LinkedHashMap<String, List<String>> dataMap = new LinkedHashMap<>();
        //List<List<String>> llData = new ArrayList<>();
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            if (existTable(context, tableName, dynrealm)) {
                //String[] colNames = dynrealm.getSchema().get(tableName).getFieldNames().toArray(new String[0]);
                RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
                RealmResults<DynamicRealmObject> drealObj = query.findAll();
                for (int col = 0; col < colNames.size(); col++) {
                    String hashKey = List_API_OutParams.get(col).getOutParam_Mapped_ID().trim();
                    List<String> collist = new ArrayList<>();
                    for (int row = 0; row < drealObj.size(); row++) {
                        DynamicRealmObject dynamicRealmObject = drealObj.get(row);
                        String data = "";
                        try {
                            data = dynamicRealmObject.getString(colNames.get(col));
                        } catch (Exception e) {
                        }
                        collist.add(data);
                    }
                    if (!dataMap.containsKey(hashKey)) {
                        dataMap.put(hashKey, collist);
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return dataMap;
    }


    public static LinkedHashMap<String, List<String>> getTableDataInLHM(Context context, String tableName) {
        LinkedHashMap<String, List<String>> dataMap = new LinkedHashMap<>();
        //List<List<String>> llData = new ArrayList<>();
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            if (existTable(context, tableName, dynrealm)) {
                String[] colNames = dynrealm.getSchema().get(tableName).getFieldNames().toArray(new String[0]);
                RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
                RealmResults<DynamicRealmObject> drealObj = query.findAll();
                for (int col = 0; col < colNames.length; col++) {
                    List<String> collist = new ArrayList<>();
                    for (int row = 0; row < drealObj.size(); row++) {
                        DynamicRealmObject dynamicRealmObject = drealObj.get(row);
                        String data = "";
                        try {
                            data = dynamicRealmObject.getString(colNames[col]);
                        } catch (Exception e) {
                        }
                        collist.add(data);
                    }
                    if (!dataMap.containsKey(colNames[col])) {
                        dataMap.put(colNames[col], collist);
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return dataMap;
    }

    public static LinkedHashMap<String, List<String>> getTableDataBaseOnDataTableColBeanInLinkedHashMap(Context context, String tableName, List<DataTableColumn_Bean> beanList, List<API_OutputParam_Bean> api_outputParam_beans) {
        LinkedHashMap<String, List<String>> dataMap = new LinkedHashMap<>();
        //List<List<String>> llData = new ArrayList<>();
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            if (existTable(context, tableName, dynrealm)) {
                RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
                RealmResults<DynamicRealmObject> drealObj = query.findAll();
                if (beanList.size() > 0) {
                    for (int col = 0; col < beanList.size(); col++) {
                        boolean isEnabled = beanList.get(col).isColumnEnabled();
                        if (isEnabled) {
                            List<String> collist = new ArrayList<>();
                            for (int row = 0; row < drealObj.size(); row++) {
                                DynamicRealmObject dynamicRealmObject = drealObj.get(row);
                                String data = "";
                                try {
                                    data = dynamicRealmObject.getString(beanList.get(col).getColumnName());
                                } catch (Exception e) {
                                }
                                collist.add(data);
                            }
                            if (!dataMap.containsKey(beanList.get(col).getColumnName().toLowerCase())) {
                                dataMap.put(beanList.get(col).getColumnName().toLowerCase(), collist);
                            }
                        }
                    }
                } else {
                    if (drealObj.size() > 0) {
                        if (api_outputParam_beans.size() > 0) {
                            for (int col = 0; col < api_outputParam_beans.size(); col++) {
                                if (api_outputParam_beans.get(col).getOutParam_Mapped_ID() != null && !api_outputParam_beans.get(col).getOutParam_Mapped_ID().equals("")) {
                                    String colName = api_outputParam_beans.get(col).getOutParam_Mapped_ID();
                                    List<String> collist = new ArrayList<>();
                                    for (int row = 0; row < drealObj.size(); row++) {
                                        DynamicRealmObject dynamicRealmObject = drealObj.get(row);
                                        String data = "";
                                        try {
                                            data = dynamicRealmObject.getString(colName);
                                        } catch (Exception e) {
                                        }
                                        collist.add(data);
                                    }
                                    if (!dataMap.containsKey(colName.toLowerCase())) {
                                        dataMap.put(colName.toLowerCase(), collist);
                                    }
                                } else if (api_outputParam_beans.get(col).getList_OutParam_Languages() != null && api_outputParam_beans.get(col).getList_OutParam_Languages().size() > 0) {
//                            String appLanguage = ImproveHelper.getLocale(context);
                                    List<LanguageMapping> languageMappings = api_outputParam_beans.get(col).getList_OutParam_Languages();
                                    for (int j = 0; j < languageMappings.size(); j++) {
                                        String colName = languageMappings.get(j).getOutParam_Lang_Mapped();
                                        List<String> collist = new ArrayList<>();
                                        for (int row = 0; row < drealObj.size(); row++) {
                                            DynamicRealmObject dynamicRealmObject = drealObj.get(row);
                                            String data = "";
                                            try {
                                                data = dynamicRealmObject.getString(colName);
                                            } catch (Exception e) {
                                            }
                                            collist.add(data);
                                        }
                                        if (!dataMap.containsKey(colName.toLowerCase())) {
                                            dataMap.put(colName.toLowerCase(), collist);
                                        }
                                    }
                                }
                            }
                        } else {
                            if (query.findFirst().getFieldNames() != null) {
                                String[] fieldNames = query.findFirst().getFieldNames();
                                JSONArray jsonArray = new JSONArray(drealObj.asJSON());
                                for (String name : fieldNames) {
                                    List<String> colList = new ArrayList<>();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        colList.add(jsonArray.getJSONObject(j).getString(name));
                                    }
                                    if (!dataMap.containsKey(name.toLowerCase())) {
                                        dataMap.put(name.toLowerCase(), colList);
                                    }

                                }

                            }

                        }
                    /*String cols[]=drealObj.get(0).getFieldNames();
                    for (int col = 0; col < cols.length; col++) {
                        List<String> collist = new ArrayList<>();
                        for (int row = 0; row < drealObj.size(); row++) {
                            DynamicRealmObject dynamicRealmObject = drealObj.get(row);
                            String data = "";
                            try {
                                data = dynamicRealmObject.getString(cols[col]);
                            } catch (Exception e) {
                            }
                            collist.add(data);
                        }
                        if (!dataMap.containsKey(cols[col])) {
                            dataMap.put(cols[col], collist);
                        }
                    }*/
                    }
                }
            }

        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return dataMap;
    }

    public static List<String> getColumnDataInList(Context context, String tableName, String columnName) {
        List<String> colValList = new ArrayList<>();
        DynamicRealm dynrealm = null;
        dynrealm = getDynamicRealm();
        try {
            RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
            RealmResults<DynamicRealmObject> drealObj = query.findAll();
            String[] cols = drealObj.get(0).getFieldNames();
            for (int col = 0; col < cols.length; col++) {
                for (int row = 0; row < drealObj.size(); row++) {
                    DynamicRealmObject dynamicRealmObject = drealObj.get(row);
                    String data = "";
                    try {
                        if (cols[col].toLowerCase().contentEquals(columnName.toLowerCase())) {
                            data = dynamicRealmObject.getString(cols[col]);
                            colValList.add(data);
                        }

                    } catch (Exception e) {
                    }

                }


            }

        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return colValList;
    }


    public static LinkedHashMap<String, List<String>> getTableDataBaseOnDataTableColBeanWithSortInLinkedHashMap(Context context, String tableName,
                                                                                                                List<DataTableColumn_Bean> beanList, String colName, String sortingType) {
        LinkedHashMap<String, List<String>> dataMap = new LinkedHashMap<>();
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            RealmResults<DynamicRealmObject> drealObj = null;
            RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
            if (sortingType.equals("Asc")) {
                drealObj = dynrealm.where(tableName).sort(colName, Sort.ASCENDING).findAll();
            } else {
                drealObj = dynrealm.where(tableName).sort(colName, Sort.DESCENDING).findAll();
            }
            for (int col = 0; col < beanList.size(); col++) {
                boolean isEnabled = beanList.get(col).isColumnEnabled();
                if (isEnabled) {
                    List<String> collist = new ArrayList<>();
                    for (int row = 0; row < drealObj.size(); row++) {
                        DynamicRealmObject dynamicRealmObject = drealObj.get(row);
                        String data = "";
                        try {
                            data = dynamicRealmObject.getString(beanList.get(col).getColumnName());
                        } catch (Exception e) {
                        }
                        collist.add(data);
                    }
                    if (!dataMap.containsKey(beanList.get(col).getColumnName())) {
                        dataMap.put(beanList.get(col).getColumnName(), collist);
                    }
                }
            }

        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return dataMap;
    }

    public static List<List<String>> getTableDataBaseOnDataTableColBean(Context context, String tableName, List<DataTableColumn_Bean> beanList) {
        List<List<String>> llData = new ArrayList<>();
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
            RealmResults<DynamicRealmObject> drealObj = query.findAll();
            for (int i = 0; i < drealObj.size(); i++) {
                List<String> list = new ArrayList<>();
                for (int j = 0; j < beanList.size(); j++) {
                    boolean isEnabled = beanList.get(j).isColumnEnabled();
                    if (isEnabled) {
                        DynamicRealmObject dynamicRealmObject = drealObj.get(i);
                        String data = "";
                        try {
                            data = dynamicRealmObject.getString(beanList.get(j).getColumnName());
                        } catch (Exception e) {
                        }
                        list.add(data);
                    }
                }
                llData.add(list);
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return llData;
    }


    public static float getTotalFromParticularCol(Context context, String tableName,
                                                  String colName) {

        String regex = "^[0-9]*[.]?[0-9]*$";
        float total = 0.0f;
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
            RealmResults<DynamicRealmObject> drealObj = query.findAll();
            for (int i = 0; i < drealObj.size(); i++) {
                String data = "0";
                try {
                    data = drealObj.get(i).getString(colName);
                } catch (Exception e) {
                    data = "0";
                }
                if (data.matches(regex)) {
                    total = total + Float.parseFloat(data);
                }
            }
            //val = drealObj.sum(colName).floatValue();
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return total;
    }

    public static List<List<String>> getTableDataBaseOnDataTableColBeanWithSort(Context context, String tableName,
                                                                                List<DataTableColumn_Bean> beanList, String colName, String sortingType) {
        List<List<String>> llData = new ArrayList<>();
        DynamicRealm dynrealm = null;
        try {
            dynrealm = getDynamicRealm();
            RealmResults<DynamicRealmObject> drealObj = null;
            RealmQuery<DynamicRealmObject> query = dynrealm.where(tableName);
            if (sortingType.equals("Asc")) {
                drealObj = dynrealm.where(tableName).sort(colName, Sort.ASCENDING).findAll();
            } else {
                drealObj = dynrealm.where(tableName).sort(colName, Sort.DESCENDING).findAll();
            }
            for (int i = 0; i < drealObj.size(); i++) {
                List<String> list = new ArrayList<>();
                for (int j = 0; j < beanList.size(); j++) {
                    boolean isEnabled = beanList.get(j).isColumnEnabled();
                    if (isEnabled) {
                        DynamicRealmObject dynamicRealmObject = drealObj.get(i);
                        String data = "";
                        try {
                            data = dynamicRealmObject.getString(beanList.get(j).getColumnName());
                        } catch (Exception e) {
                        }
                        list.add(data);
                    }
                }
                llData.add(list);
            }
        } catch (Exception e) {
            ImproveHelper.showToast(context, e.getMessage());
        } finally {
            if (dynrealm != null) {
                dynrealm.close();
            }
        }
        return llData;
    }


}
