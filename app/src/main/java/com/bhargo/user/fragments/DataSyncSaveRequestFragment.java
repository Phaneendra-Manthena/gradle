package com.bhargo.user.fragments;

import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.showToastRunOnUI;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.actions.SyncSaveRequest;
import com.bhargo.user.adapters.OfflineSaveRequestSynAdapter;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.Callback;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.OfflineSaveRequestPojo;
import com.bhargo.user.realm.RealmTables;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DataSyncSaveRequestFragment extends Fragment implements OfflineSaveRequestSynAdapter.OfflineSaveRequestSynAdapterListener {

    private static final String TAG = "DataSyncSaveRequestFrag";
    RecyclerView rv_apps;
    CustomTextView ct_alNoRecords;
    GetServices getServices;
    Context context;
    Gson gson;
    ImproveHelper improveHelper;
    SessionManager sessionManager;
    ImproveDataBase improveDataBase;
    List<OfflineSaveRequestPojo> offlineSaveRequestPojoList;
    OfflineSaveRequestSynAdapter offlineSaveRequestSynAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= null;
        try {
            // Inflate the layout for this fragment
            view= inflater.inflate(R.layout.fragment_data_sync_save_request, container, false);
            context = getActivity();
            gson = new Gson();
            improveHelper = new ImproveHelper(context);
            sessionManager = new SessionManager(context);
            improveDataBase = new ImproveDataBase(context);
            offlineSaveRequestPojoList = new ArrayList<>();
            getServices = RetrofitUtils.getUserService();
            findViews(view);

        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "onCreateView", e);

        }
        return view;
    }

    private void findViews(View view) {
        try {
            ct_alNoRecords = view.findViewById(R.id.ct_alNoRecords);
            rv_apps = view.findViewById(R.id.rv_apps);
            rv_apps.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            rv_apps.setLayoutManager(layoutManager);
            offlineSaveRequestSynAdapter = new OfflineSaveRequestSynAdapter(offlineSaveRequestPojoList, getActivity(), ct_alNoRecords, this);
            rv_apps.setAdapter(offlineSaveRequestSynAdapter);
            loadData();
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "findViews", e);

        }
    }

    private void loadData() {
        try {
            List<List<String>> db_data;
            offlineSaveRequestPojoList.clear();
            db_data = improveDataBase.getTableColData(RealmTables.SaveRequestTable.TABLE_NAME_,
                    RealmTables.SaveRequestTable.colsWithRowID);

            if (db_data.size() > 0) {
                for (int i = 0; i < db_data.size(); i++) {
                    OfflineSaveRequestPojo offlineSaveRequestPojo = new OfflineSaveRequestPojo();
                    offlineSaveRequestPojo.setRowID(db_data.get(i).get(0));
                    offlineSaveRequestPojo.setActionType(db_data.get(i).get(1));
                    offlineSaveRequestPojo.setQueryType(db_data.get(i).get(2));
                    offlineSaveRequestPojo.setActionID(db_data.get(i).get(3));
                    offlineSaveRequestPojo.setActionName(db_data.get(i).get(4));
                    offlineSaveRequestPojo.setExistingTableName(db_data.get(i).get(5));
                    offlineSaveRequestPojo.setTypeOfInput(db_data.get(i).get(6));
                    offlineSaveRequestPojo.setFilesControlNames(db_data.get(i).get(7));
                    offlineSaveRequestPojo.setTempCol(db_data.get(i).get(8));
                    offlineSaveRequestPojo.setSendingObj(db_data.get(i).get(9));
                    offlineSaveRequestPojo.setAppName(db_data.get(i).get(10));
                    offlineSaveRequestPojoList.add(offlineSaveRequestPojo);
                }
                if (offlineSaveRequestPojoList.size() > 0) {
                    ct_alNoRecords.setVisibility(View.GONE);
                } else {
                    ct_alNoRecords.setVisibility(View.VISIBLE);
                }
                offlineSaveRequestSynAdapter.notifyDataSetChanged();
            } else {
                offlineSaveRequestSynAdapter.notifyDataSetChanged();
                ct_alNoRecords.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "loadData", e);

        }
    }

    @Override
    public void onSelectedItem(OfflineSaveRequestPojo offlineSaveRequestPojo, int selectedPostion) {

    }

    @Override
    public void onSelectedDeleteItem(OfflineSaveRequestPojo offlineSaveRequestPojo, int selectedPostion) {
        try {
            improveDataBase.deleteByValues(RealmTables.SaveRequestTable.TABLE_NAME_,
                    new String[]{"rowid"},
                    new String[]{offlineSaveRequestPojo.getRowID()});
            offlineSaveRequestPojoList.remove(selectedPostion);
            if (offlineSaveRequestPojoList.size() > 0) {
                ct_alNoRecords.setVisibility(View.GONE);
            } else {
                ct_alNoRecords.setVisibility(View.VISIBLE);
            }
            offlineSaveRequestSynAdapter.notifyItemRemoved(selectedPostion);
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "onSelectedDeleteItem", e);

        }
    }

    @Override
    public void onSelectedSendItem(OfflineSaveRequestPojo offlineSaveRequestPojo, int selectedPostion) {
        try {
            if (isNetworkStatusAvialable(context)) {
                SyncSaveRequest syncSaveRequest = new SyncSaveRequest(context, offlineSaveRequestPojo, new Callback() {
                    @Override
                    public void onSuccess(Object result) {
                        //delete record in DB and list
                        improveDataBase.deleteByValues(RealmTables.SaveRequestTable.TABLE_NAME_,
                                new String[]{"rowid"},
                                new String[]{offlineSaveRequestPojo.getRowID()});
                        offlineSaveRequestPojoList.remove(selectedPostion);
                        if (offlineSaveRequestPojoList.size() > 0) {
                            ct_alNoRecords.setVisibility(View.GONE);
                        } else {
                            ct_alNoRecords.setVisibility(View.VISIBLE);
                        }
                        offlineSaveRequestSynAdapter.notifyItemRemoved(selectedPostion);
                        showToastRunOnUI(getActivity(), result.toString());
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        showToastRunOnUI(getActivity(), throwable.getMessage());
                    }
                });
            } else {
                ImproveHelper.my_showAlert(context, "", getString(R.string.no_internet), "2");
            }
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "onSelectedSendItem", e);

        }
    }
}