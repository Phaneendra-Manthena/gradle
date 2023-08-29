package com.bhargo.user.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.OfflineSyncAppsListAdapter;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.custom.SearchableSpinner;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.GetAllAppModel;
import com.bhargo.user.pojos.OfflineDataSync;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineDataSyncFragment extends Fragment {

    private static final String TAG = "AppsListActivity";

    RecyclerView rv_apps;
    GetServices getServices;

    OfflineSyncAppsListAdapter appsAdapter;
    RelativeLayout rl_AppsListMain;

    SearchableSpinner orgSpinner;
    CustomTextView ct_alNoRecords;
    ImproveHelper improveHelper;

    SessionManager sessionManager;
    ImproveDataBase improveDataBase;
    List<OfflineDataSync> appDetailsList;
    private String strOrgName;
    private GetAllAppModel getAllAppModel;
    private String i_OrgId;
    private ProgressDialog progressDialog;


    public OfflineDataSyncFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=null;
        try {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_offline_data_sync, container, false);

            improveHelper = new ImproveHelper(getActivity());
            improveDataBase = new ImproveDataBase(getActivity());

            sessionManager = new SessionManager(getActivity());

            Log.d(TAG, "onCreate2: " + sessionManager.getUserDataFromSession().getUserID());

            showProgressDialog(getString(R.string.please_wait));

            getServices = RetrofitUtils.getUserService();
//        orgSpinner = view.findViewById(R.id.sp_ORG);

            ct_alNoRecords = view.findViewById(R.id.ct_alNoRecords);
            rv_apps = view.findViewById(R.id.rv_apps);
//        rl_AppsListMain = view.findViewById(R.id.rl_AppsListMain);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            rv_apps.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

//        final List<OrgList> orgListsAppsList = improveDataBase.getDataFromOrganisationTable();
//
//        ArrayAdapter adapterAllTypes = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, orgListsAppsList);
//        orgSpinner.setAdapter(adapterAllTypes);
//        orgSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                strOrgName = orgSpinner.getSelectedItem().toString();
//                i_OrgId = orgListsAppsList.get(i).getOrgID();
//
//                PrefManger.putSharedPreferencesString(getActivity(), AppConstants.SP_ORGANISATION_ID, String.valueOf(i_OrgId));
//
//                Toast.makeText(getActivity(), strOrgName, Toast.LENGTH_SHORT).show();
//
//                getAppsDataOffLine(i_OrgId);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//
//
//        });
//


            getAppsDataOffLine(sessionManager.getOrgIdFromSession());


        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "onCreateView", e);
        }
        return view;
    }


    public void getAppsDataOffLine(String i_OrgId) {
        try {
            appDetailsList = new ArrayList<>();
            String status = "0";
            appDetailsList = improveDataBase.getDataFromSubmitFormListTable(i_OrgId, status, sessionManager.getUserDataFromSession().getUserID());

            if (appDetailsList.size() > 0) {
                dismissProgressDialog();
                ct_alNoRecords.setVisibility(View.GONE);
                rv_apps.setVisibility(View.VISIBLE);
                appsAdapter = new OfflineSyncAppsListAdapter(getActivity(), appDetailsList);
                rv_apps.setAdapter(appsAdapter);
//            appsAdapter.setCustomClickListener(getActivity());
            } else {
                dismissProgressDialog();
                ct_alNoRecords.setVisibility(View.VISIBLE);
                rv_apps.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "getAppsDataOffLine", e);
        }
    }

    public void showProgressDialog(String msg) {
        try {
            progressDialog = new ProgressDialog(getActivity());
            // pd = CustomProgressDialog.ctor(this, msg);
            // pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(false);
            progressDialog.show();
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "showProgressDialog", e);
        }
    }

    public void dismissProgressDialog() {
        try {
            if (progressDialog.isShowing() && progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "dismissProgressDialog", e);
        }
    }

}
