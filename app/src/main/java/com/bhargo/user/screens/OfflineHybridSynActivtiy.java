package com.bhargo.user.screens;

import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.OfflineHybridSynAdapter;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppDetails;
import com.bhargo.user.pojos.ForeignKey;
import com.bhargo.user.pojos.OfflineHybridAppListPojo;
import com.bhargo.user.pojos.SubFormTableColumns;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bhargo.user.actions.SyncData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nk.bluefrog.library.utils.Helper;

public class OfflineHybridSynActivtiy extends BaseActivity implements OfflineHybridSynAdapter.OfflineHybridSynAdapterListener {
    private final String TAG = "OfflineHybridSynActivtiy";
    RecyclerView rv_apps;
    CustomTextView ct_alNoRecords;
    GetServices getServices;
    Context context;
    Gson gson;
    ImproveHelper improveHelper;
    SessionManager sessionManager;
    ImproveDataBase improveDataBase;
    String pageName = "Offline Data Sync";
    OfflineHybridSynAdapter offlineHybridSynAdapter;
    List<OfflineHybridAppListPojo> offlineHybridAppListPojoList;


    String i_OrgId;
    OfflineHybridAppListPojo selOfflineHybridAppListPojoData;
    int selectedPostion;


    EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_hybrid_syn_activtiy);
        context = OfflineHybridSynActivtiy.this;
        gson = new Gson();
        improveHelper = new ImproveHelper(context);
        sessionManager = new SessionManager(context);
        improveDataBase = new ImproveDataBase(this);
        offlineHybridAppListPojoList = new ArrayList<>();
        initializeActionBar();
        enableBackNavigation(true);
        setBackListener(new BackListener() {
            @Override
            public void onBackListener() {
                updateOfflineDataExistFlag();
            }
        });
        ib_settings.setVisibility(View.GONE);
        iv_circle_appIcon.setVisibility(View.VISIBLE);
        if (pageName != null) {
            title.setText(pageName);
        } else {
            title.setText(getString(R.string.improve_user));
        }
        getServices = RetrofitUtils.getUserService();
        findViews();
    }

    private void findViews() {
        i_OrgId = sessionManager.getOrgIdFromSession();
        et_search = findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                offlineHybridSynAdapter.getFilter().filter(charSequence.toString().trim());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ct_alNoRecords = findViewById(R.id.ct_alNoRecords);
        rv_apps = findViewById(R.id.rv_apps);
        rv_apps.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_apps.setLayoutManager(layoutManager);
        offlineHybridSynAdapter = new OfflineHybridSynAdapter(offlineHybridAppListPojoList, this, ct_alNoRecords, this);
        rv_apps.setAdapter(offlineHybridSynAdapter);
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        List<List<String>> db_data;
        offlineHybridAppListPojoList.clear();
        db_data = improveDataBase.getTableColDataByORCond(ImproveDataBase.APPS_LIST_TABLE,
                "AppName,AppMode,TableName,DesignFormat,TableColumns,PrimaryKeys,ForeignKeys,CompositeKeys,SubformDetails,ApkVersion,WORKSPACEName,NewRow",
                new String[]{"AppMode", "AppMode"},
                new String[]{"Hybrid", "Offline"});

        if (db_data.size() > 0) {
            int totRecords = db_data.size() - 1;
            for (int i = totRecords; i >= 0; i--) {
                String tableName = db_data.get(i).get(2);
                String workspaceName = db_data.get(i).get(10);
                String newRow = db_data.get(i).get(11);
                if (tableName != null && !tableName.trim().equals("") && (workspaceName != null && !workspaceName.trim().equals("") || newRow!=null && newRow.equalsIgnoreCase("Multi_SingleForm"))) {
                    AppDetails appDetails = new AppDetails();
                    //AppName,AppMode,TableName,DesignFormat,TableColumns,PrimaryKeys,ForeignKeys,CompositeKeys,SubformDetails,ApkVersion
                    appDetails.setAppName(db_data.get(i).get(0));
                    appDetails.setAppMode(db_data.get(i).get(1));
                    appDetails.setTableName(db_data.get(i).get(2));
                    appDetails.setDesignFormat(db_data.get(i).get(3));
                    appDetails.setTableColumns(db_data.get(i).get(4));
                    appDetails.setPrimaryKey(db_data.get(i).get(5));
                    Type typeAppsForeignKey = new TypeToken<List<ForeignKey>>() {
                    }.getType();
                    List<ForeignKey> foreignKeyList = gson.fromJson(db_data.get(i).get(6), typeAppsForeignKey);
                    appDetails.setForeignKey(foreignKeyList);
                    appDetails.setCompositeKey(db_data.get(i).get(7));
                    Type typeApps = new TypeToken<List<SubFormTableColumns>>() {
                    }.getType();
                    List<SubFormTableColumns> subFormTableColumnsList = gson.fromJson(db_data.get(i).get(8), typeApps);
                    appDetails.setSubFormDetails(subFormTableColumnsList);
                    appDetails.setApkVersion(db_data.get(i).get(9));
                    if (improveDataBase.tableExists(tableName)) {
                        int count = improveDataBase.getCountByValue(tableName, "Bhargo_SyncStatus", "0");
                        //if (count != 0) {
                        OfflineHybridAppListPojo offlineHybridAppListPojo = new OfflineHybridAppListPojo();
                        offlineHybridAppListPojo.setAppName(db_data.get(i).get(0));
                        offlineHybridAppListPojo.setAppMode(db_data.get(i).get(1));
                        offlineHybridAppListPojo.setTableName(db_data.get(i).get(2));
                        offlineHybridAppListPojo.setDesignFormat(db_data.get(i).get(3));
                        offlineHybridAppListPojo.setTableNameRecordsCount(count + "");
                        offlineHybridAppListPojo.setAppDetails(appDetails);
                        offlineHybridAppListPojoList.add(offlineHybridAppListPojo);
                        //   }
                    } else {
                        OfflineHybridAppListPojo offlineHybridAppListPojo = new OfflineHybridAppListPojo();
                        offlineHybridAppListPojo.setAppName(db_data.get(i).get(0));
                        offlineHybridAppListPojo.setAppMode(db_data.get(i).get(1));
                        offlineHybridAppListPojo.setTableName(db_data.get(i).get(2));
                        offlineHybridAppListPojo.setDesignFormat(db_data.get(i).get(3));
                        offlineHybridAppListPojo.setTableNameRecordsCount("-");
                        offlineHybridAppListPojo.setAppDetails(appDetails);
                        offlineHybridAppListPojoList.add(offlineHybridAppListPojo);
                    }
                }
            }
            if (offlineHybridAppListPojoList.size() > 0) {
                ct_alNoRecords.setVisibility(View.GONE);
            } else {
                ct_alNoRecords.setVisibility(View.VISIBLE);
            }
            offlineHybridSynAdapter.notifyDataSetChanged();
        } else {
            offlineHybridSynAdapter.notifyDataSetChanged();
            ct_alNoRecords.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSelectedItem(OfflineHybridAppListPojo offlineHybridAppListPojo, int selectedPostion) {
        //get file path like(image,video,audio etc) from maintable and subformtables
        //send to server and update url in maintable and subformtables
        //prepare sending string from maintable and subformtables and update trans id
        //1. Getting file cols from DataCollectionObject for singleForm with mainTable & subFormTable
        if (isNetworkStatusAvialable(context)) {
            selOfflineHybridAppListPojoData = offlineHybridAppListPojo;
            this.selectedPostion = selectedPostion;
        } else {
            ImproveHelper.my_showAlert(this, "", getString(R.string.no_internet), "2");
        }
    }

    @Override
    public void onSelectedDeleteItem(OfflineHybridAppListPojo offlineHybridAppListPojo, int selectedPostion) {

        Intent intent = new Intent(this, OfflineHybridDeleteActivity.class);
        intent.putExtra("SelectedAppDetails", offlineHybridAppListPojo);
        startActivity(intent);

    }

    @Override
    public void onSelectedSendItem(OfflineHybridAppListPojo offlineHybridAppListPojo, int selectedPostion) {
        if (isNetworkStatusAvialable(context)) {
            selOfflineHybridAppListPojoData = offlineHybridAppListPojo;
            this.selectedPostion = selectedPostion;
            callSyncDataWithType(3);
        } else {
            ImproveHelper.my_showAlert(this, "", getString(R.string.no_internet), "2");
        }
    }

    @Override
    public void onSelectedSyncItem(OfflineHybridAppListPojo offlineHybridAppListPojo, int selectedPostion) {
        //form sync from server
        //1.check tables exists or no
        selOfflineHybridAppListPojoData = offlineHybridAppListPojo;
        this.selectedPostion = selectedPostion;
        if (improveDataBase.tableExists(selOfflineHybridAppListPojoData.getTableName())) {
            //2. data sync
            //downloadSyncDataFromServer();
            callSyncDataWithType(2);
        } else {
            //1.a: create tables
            //improveDataBase.createTablesBasedOnConditions(selOfflineHybridAppListPojoData.getAppDetails());
            //2. data sync
            //downloadSyncDataFromServer();
            callSyncDataWithType(2);

        }
    }

    private void callSyncDataWithType(int type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SyncData syncData = new SyncData(OfflineHybridSynActivtiy.this, selOfflineHybridAppListPojoData.getAppName(),
                        type, new SyncData.SyncDataListener() {

                    @Override
                    public void onSuccess(String msg) {
                        ImproveHelper.confirmDialog(OfflineHybridSynActivtiy.this, msg, "OK", "", new Helper.IL() {
                            @Override
                            public void onSuccess() {
                                loadData();
                            }

                            @Override
                            public void onCancel() {
                                loadData();
                            }
                        });
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        ImproveHelper.confirmDialog(OfflineHybridSynActivtiy.this, errorMessage, "OK", "", new Helper.IL() {
                            @Override
                            public void onSuccess() {
                                loadData();
                            }

                            @Override
                            public void onCancel() {
                                loadData();
                            }
                        });
                       // ImproveHelper.my_showAlert(OfflineHybridSynActivtiy.this, "Sync Failed!", errorMessage, "2");
                    }
                });
            }
        });

    }

    private void updateOfflineDataExistFlag() {
        if (offlineHybridSynAdapter != null) {
            for (int i = 0; i < offlineHybridSynAdapter.getListFilter().size(); i++) {
                OfflineHybridAppListPojo obj = offlineHybridSynAdapter.getListFilter().get(i);
                String val = obj.getTableNameRecordsCount().trim();
                if (val.equals("-") || val.equals("0")) {
                    PrefManger.putSharedPreferencesBoolean(context,AppConstants.OfflineDataExistKey,false);
                } else {
                    PrefManger.putSharedPreferencesBoolean(context,AppConstants.OfflineDataExistKey,true);
                    break;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updateOfflineDataExistFlag();
    }
}