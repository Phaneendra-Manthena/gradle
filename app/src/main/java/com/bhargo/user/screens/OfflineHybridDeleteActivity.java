package com.bhargo.user.screens;

/*nagendra*/

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.R;
import com.bhargo.user.adapters.OfflineHybridDeleteAdapter;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.ColNameAndValuePojo;
import com.bhargo.user.pojos.OfflineHybridAppListPojo;
import com.bhargo.user.pojos.OfflineHybridDeletePojo;
import com.bhargo.user.pojos.TableNameAndColsFromDOPojo;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.SessionManager;
import com.bhargo.user.utils.XMLHelper;

import java.util.ArrayList;
import java.util.List;

public class OfflineHybridDeleteActivity extends BaseActivity implements OfflineHybridDeleteAdapter.OfflineHybridDeleteListener {

    AutoCompleteTextView actv_search;
    RecyclerView rv_apps;
    CustomTextView ct_alNoRecords;
    SessionManager sessionManager;
    ImproveDataBase improveDataBase;
    OfflineHybridAppListPojo offlineHybridAppListPojo;
    String pageName = "Offline Data Sync List";
    OfflineHybridDeleteAdapter offlineHybridDeleteAdapter;
    List<OfflineHybridDeletePojo> ll_deletedata = new ArrayList<>();
    DataCollectionObject dataCollectionObject;
    XMLHelper xmlHelper;
    List<TableNameAndColsFromDOPojo> tableNameAndColsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_hybrid_delete);
        offlineHybridAppListPojo = (OfflineHybridAppListPojo) getIntent().getExtras().getSerializable("SelectedAppDetails");
        xmlHelper = new XMLHelper();
        sessionManager = new SessionManager(this);
        improveDataBase = new ImproveDataBase(this);

        initializeActionBar();
        enableBackNavigation(true);
        ib_settings.setVisibility(View.GONE);
        iv_circle_appIcon.setVisibility(View.VISIBLE);
        if (pageName != null) {
            title.setText(offlineHybridAppListPojo.getAppName() + " Record List");
            //subTitle.setText(pageName);
        } else {
            title.setText(getString(R.string.improve_user));
        }
        findViews();
    }

    private void findViews() {
        actv_search = findViewById(R.id.actv_search);
        ct_alNoRecords = findViewById(R.id.ct_alNoRecords);
        rv_apps = findViewById(R.id.rv_apps);
        rv_apps.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_apps.setLayoutManager(layoutManager);
        offlineHybridDeleteAdapter = new OfflineHybridDeleteAdapter(ll_deletedata, this, ct_alNoRecords, this);
        rv_apps.setAdapter(offlineHybridDeleteAdapter);
        loadData();
    }

    private void loadData() {
        ll_deletedata.clear();
        //nk step
        System.out.println("=======Step9===============");
        dataCollectionObject = xmlHelper.XML_To_DataCollectionObject(offlineHybridAppListPojo.getDesignFormat());
        String maincolSeperated = TextUtils.join(",", dataCollectionObject.getList_Table_Columns());

        List<List<ColNameAndValuePojo>> ll_colNameAndValue = improveDataBase.getTableColNameAndValueByCond(offlineHybridAppListPojo.getTableName(), "rowid,"+maincolSeperated,
                new String[]{offlineHybridAppListPojo.getTableName()+"_SyncStatus"},new String[]{"0"});

        if (ll_colNameAndValue.size() > 0) {
            for (int i = 0; i < ll_colNameAndValue.size(); i++) {
                List<ColNameAndValuePojo> l_cols = ll_colNameAndValue.get(i);
                OfflineHybridDeletePojo offlineHybridDeletePojo = new OfflineHybridDeletePojo();
                offlineHybridDeletePojo.setTableName(offlineHybridAppListPojo.getTableName());
                for (int j = 0; j < l_cols.size(); j++) {
                    ColNameAndValuePojo colVal = l_cols.get(j);
                    if (j == 0) {
                        offlineHybridDeletePojo.setRowID(colVal.getColValue());
                    }else if (j == 1) {
                        offlineHybridDeletePojo.setTransIDColName(colVal.getColName());
                        offlineHybridDeletePojo.setTransIDColVal(colVal.getColValue());
                    }else  if (j == 2) {
                        offlineHybridDeletePojo.setColName1(colVal.getColName());
                        offlineHybridDeletePojo.setColValue1(colVal.getColValue());
                    } else if (j == 3) {
                        offlineHybridDeletePojo.setColName2(colVal.getColName());
                        offlineHybridDeletePojo.setColValue2(colVal.getColValue());
                    } else if (j == 4) {
                        offlineHybridDeletePojo.setColName3(colVal.getColName());
                        offlineHybridDeletePojo.setColValue3(colVal.getColValue());
                    }
                }
                ll_deletedata.add(offlineHybridDeletePojo);
            }
        }

        if (ll_deletedata.size() > 0) {
            ct_alNoRecords.setVisibility(View.GONE);
            offlineHybridDeleteAdapter.notifyDataSetChanged();
        } else {
            offlineHybridDeleteAdapter.notifyDataSetChanged();
            ct_alNoRecords.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onSelectedDeleteItem(OfflineHybridDeletePojo selectedRecord, int selectedPostion) {
        getSubFormTableNameAndColsFromDCObj(selectedRecord.getTableName());
        improveDataBase.deleteByValues(selectedRecord.getTableName(),
                new String[]{selectedRecord.getTransIDColName()},new String[]{selectedRecord.getTransIDColVal()});
         for (int i = 0; i < tableNameAndColsList.size(); i++) {
            TableNameAndColsFromDOPojo tableNameAndColsFromDOPojo=tableNameAndColsList.get(i);
            if(tableNameAndColsFromDOPojo.getType().equals("S")){
                improveDataBase.deleteByValues(tableNameAndColsFromDOPojo.getTableName(),
                        new String[]{tableNameAndColsFromDOPojo.getTableName()+"_Ref_TransID"},new String[]{selectedRecord.getTransIDColVal()});
            }
        }

        ll_deletedata.remove(selectedPostion);
        if (ll_deletedata.size() > 0) {
            ct_alNoRecords.setVisibility(View.GONE);
        } else {
            ct_alNoRecords.setVisibility(View.VISIBLE);
        }
        offlineHybridDeleteAdapter.notifyItemRemoved(selectedPostion);
        ImproveHelper.my_showAlert(OfflineHybridDeleteActivity.this,"",getString(R.string.data_deleted_successfully),"3");
    }

    private void getSubFormTableNameAndColsFromDCObj(String mainTableName) {
        tableNameAndColsList = new ArrayList<>();

        for (int i = 0; i < dataCollectionObject.getControls_list().size(); i++) {
            ControlObject controlObject = dataCollectionObject.getControls_list().get(i);
           if (controlObject.getControlType().equals(AppConstants.CONTROL_TYPE_SUBFORM)) {
                //SubForm Table cols
                String subformcolSeperated = TextUtils.join(",", controlObject.getSubFormList_Table_Columns());
                TableNameAndColsFromDOPojo tableNameAndColsForFilesPojo = new TableNameAndColsFromDOPojo();
                tableNameAndColsForFilesPojo.setTableName(mainTableName + "_" + controlObject.getControlID());
                tableNameAndColsForFilesPojo.setCols(subformcolSeperated);
                tableNameAndColsForFilesPojo.setType("S");
                tableNameAndColsList.add(tableNameAndColsForFilesPojo);
            }
        }

        System.out.println("tableNameAndColsList: " + tableNameAndColsList);
    }
}