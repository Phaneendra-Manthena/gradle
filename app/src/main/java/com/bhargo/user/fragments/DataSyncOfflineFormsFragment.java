package com.bhargo.user.fragments;

import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.actions.SyncData;
import com.bhargo.user.adapters.OfflineHybridSynAdapter;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppDetails;
import com.bhargo.user.pojos.ForeignKey;
import com.bhargo.user.pojos.OfflineHybridAppListPojo;
import com.bhargo.user.pojos.SubFormTableColumns;
import com.bhargo.user.screens.OfflineHybridDeleteActivity;
import com.bhargo.user.screens.OfflineHybridSynActivtiy;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import nk.bluefrog.library.utils.Helper;


public class DataSyncOfflineFormsFragment extends Fragment implements OfflineHybridSynAdapter.OfflineHybridSynAdapterListener {

    private static final String TAG = "DataSyncOfflineFormsFra";
    RecyclerView rv_apps;
    CustomTextView ct_alNoRecords;
    GetServices getServices;
    Context context;
    Gson gson;
    ImproveHelper improveHelper;
    SessionManager sessionManager;
    ImproveDataBase improveDataBase;
    OfflineHybridSynAdapter offlineHybridSynAdapter;
    List<OfflineHybridAppListPojo> offlineHybridAppListPojoList;
    String i_OrgId;
    OfflineHybridAppListPojo selOfflineHybridAppListPojoData;
    int selectedPostion;

    Thread TM2,TM1;
    EditText et_search;

    public DataSyncOfflineFormsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=null;
        try {
            // Inflate the layout for this fragment
             view = inflater.inflate(R.layout.fragment_data_sync_offline_forms, container, false);
            context = getActivity();
            gson = new Gson();
            improveHelper = new ImproveHelper(context);
            sessionManager = new SessionManager(context);
            improveDataBase = new ImproveDataBase(context);
            offlineHybridAppListPojoList = new ArrayList<>();
            getServices = RetrofitUtils.getUserService();
            findViews(view);

        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "onCreateView", e);
        }
        return view;
    }

    private void findViews(View view) {
        try {
            i_OrgId = sessionManager.getOrgIdFromSession();
            et_search = view.findViewById(R.id.et_search);
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
            ct_alNoRecords = view.findViewById(R.id.ct_alNoRecords);
            rv_apps = view.findViewById(R.id.rv_apps);
            rv_apps.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            rv_apps.setLayoutManager(layoutManager);
            offlineHybridSynAdapter = new OfflineHybridSynAdapter(offlineHybridAppListPojoList, getActivity(), ct_alNoRecords, this);
            rv_apps.setAdapter(offlineHybridSynAdapter);
            loadData();
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "findViews", e);

        }
    }

    private void loadData() {
        try {
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
                    if (tableName != null && !tableName.trim().equals("") && (workspaceName != null && !workspaceName.trim().equals("") || newRow != null && newRow.equalsIgnoreCase("Multi_SingleForm"))) {
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
                            offlineHybridAppListPojo.setTableNameRecordsCount("0");
                            offlineHybridAppListPojo.setAppDetails(appDetails);
                            offlineHybridAppListPojoList.add(offlineHybridAppListPojo);
                        }
                    }
                }


                List<OfflineHybridAppListPojo> originalList = new ArrayList<>(offlineHybridAppListPojoList);

                Collections.sort(offlineHybridAppListPojoList);

                boolean isSortingCompleted = offlineHybridAppListPojoList.equals(originalList);

                if (isSortingCompleted) {
                    if (offlineHybridAppListPojoList.size() > 0) {
                        ct_alNoRecords.setVisibility(View.GONE);
                    } else {
                        ct_alNoRecords.setVisibility(View.VISIBLE);
                    }
                    offlineHybridSynAdapter.notifyDataSetChanged();
                } else {
                    System.out.println("Sorting did not complete or resulted in a different order.");
                }


            } else {
                offlineHybridSynAdapter.notifyDataSetChanged();
                ct_alNoRecords.setVisibility(View.VISIBLE);
            }
        } catch (JsonSyntaxException e) {
            ImproveHelper.improveException(getActivity(), TAG, "loadData", e);

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
            ImproveHelper.my_showAlert(context, "", getString(R.string.no_internet), "2");
        }
    }

    @Override
    public void onSelectedDeleteItem(OfflineHybridAppListPojo offlineHybridAppListPojo, int selectedPostion) {

        Intent intent = new Intent(getActivity(), OfflineHybridDeleteActivity.class);
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
            ImproveHelper.my_showAlert(context, "", getString(R.string.no_internet), "2");
        }
    }

    @Override
    public void onSelectedSyncItem(OfflineHybridAppListPojo offlineHybridAppListPojo, int selectedPostion) {
        try {
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
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "onSelectedSyncItem", e);

        }
    }

    private void callSyncDataWithType(int type) {
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SyncData syncData = new SyncData(context, selOfflineHybridAppListPojoData.getAppName(),
                            type, new SyncData.SyncDataListener() {

                        @Override
                        public void onSuccess(String msg) {
                            ImproveHelper.confirmDialog(context, msg, "OK", "", new Helper.IL() {
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
                            ImproveHelper.confirmDialog(context, errorMessage, "OK", "", new Helper.IL() {
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
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "callSyncDataWithType", e);

        }

    }

    public void updateOfflineDataExistFlag() {
        try {
            if (offlineHybridSynAdapter != null) {
                for (int i = 0; i < offlineHybridSynAdapter.getListFilter().size(); i++) {
                    OfflineHybridAppListPojo obj = offlineHybridSynAdapter.getListFilter().get(i);
                    String val = obj.getTableNameRecordsCount().trim();
                    if (val.equals("-") || val.equals("0")) {
                        PrefManger.putSharedPreferencesBoolean(context, AppConstants.OfflineDataExistKey, false);
                    } else {
                        PrefManger.putSharedPreferencesBoolean(context, AppConstants.OfflineDataExistKey, true);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "updateOfflineDataExistFlag", e);

        }
    }


}