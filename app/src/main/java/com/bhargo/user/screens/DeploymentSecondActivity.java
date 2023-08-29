package com.bhargo.user.screens;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.DeployGroupAdapter;
import com.bhargo.user.adapters.DeployIndividualAdapter;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nk.mobileapps.spinnerlib.SearchableMultiSpinner;
import nk.mobileapps.spinnerlib.SpinnerData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeploymentSecondActivity extends BaseActivity implements SearchableMultiSpinner.SpinnerListener {

    private static final String TAG = "DeploymentSecondActivit";
    GetServices getServices;
    ArrayList<SpinnerData> spinnerDataArrayListG;
    ArrayList<SpinnerData> spinnerDataArrayListU;
    ArrayList<String> arrayListSelectedItemsG = new ArrayList<>();
    ArrayList<String> arrayListSelectedItemsI = new ArrayList<>();
    ArrayList<String> arrayListSelectedItemsIIds = new ArrayList<>();
    ArrayList<String> arrayListSelectedItemsGIds = new ArrayList<>();

    SearchableMultiSpinner sp_group, sp_individual;
    //    CustomEditText ceSelectedIndividuals,ceSelectedGroups;
    String sp_application_name;
    RecyclerView rv_individuals, rv_groups;
    CustomTextView ct_noGroupSelect, ct_noIndividualSelect;
    DatabaseReference mFirebaseDatabaseReference;
    ImproveHelper improveHelper;
    private String strArrayNamesI, strArrayNamesG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deployment_second);

        improveHelper = new ImproveHelper(this);
        initViews();

        if (getIntent() != null && getIntent().getStringExtra("AppNameDeploymentSecond") != null) {
            sp_application_name = getIntent().getStringExtra("AppNameDeploymentSecond");
            Log.d(TAG, "onCreateDeploySecond: " + sp_application_name);
        }

        sp_group = findViewById(R.id.sp_group);
        sp_individual = findViewById(R.id.sp_individual);

//        ceSelectedIndividuals = findViewById(R.id.ceSelectedIndividuals);
//        ceSelectedGroups = findViewById(R.id.ceSelectedGroups);

        rv_groups = findViewById(R.id.rv_groups);
        rv_individuals = findViewById(R.id.rv_individuals);
        ct_noGroupSelect = findViewById(R.id.ct_noGroupSelect);
        ct_noIndividualSelect = findViewById(R.id.ct_noIndividualSelect);

        LinearLayoutManager linearLayoutManager_g = new LinearLayoutManager(getApplicationContext());
        rv_groups.setLayoutManager(linearLayoutManager_g); // set Layou
        LinearLayoutManager linearLayoutManager_i = new LinearLayoutManager(getApplicationContext());
        rv_individuals.setLayoutManager(linearLayoutManager_i); // set Layou

        sp_group.setItems(loadGroups(), this);
        sp_individual.setItems(loadIndividuals(), this);

        if (arrayListSelectedItemsG.size() > 0) {
            rv_groups.setVisibility(View.VISIBLE);
            ct_noGroupSelect.setVisibility(View.GONE);
        } else {
            rv_groups.setVisibility(View.GONE);
            ct_noGroupSelect.setVisibility(View.VISIBLE);
        }
        if (arrayListSelectedItemsI.size() > 0) {
            rv_individuals.setVisibility(View.VISIBLE);
            ct_noIndividualSelect.setVisibility(View.GONE);
        } else {
            ct_noIndividualSelect.setVisibility(View.VISIBLE);
            rv_individuals.setVisibility(View.GONE);
        }

    }

    private void initViews() {
        try {
            getServices = RetrofitUtils.getUserService();
            initializeActionBar();
            title.setText("To Whom");
            ib_settings.setVisibility(View.GONE);
            iv_circle_appIcon.setVisibility(View.GONE);
            enableBackNavigation(true);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "initViews", e);
        }

    }

    private ArrayList<SpinnerData> loadGroups() {
        /*http://182.18.157.124/ImproveApplications/api/GroupManagement/GetGroupList
{"UserId": "BLUE0001","OrgId": "BLUE20191121152218618"}*/

        spinnerDataArrayListG = new ArrayList<>();

        try {
            Map<String, String> data = new HashMap<>();
            data.put("UserId", ImproveHelper.getUserDetails(this).getUserID());
            data.put("OrgId", PrefManger.getSharedPreferencesString(DeploymentSecondActivity.this, AppConstants.SP_ORGANISATION_ID, AppConstants.Default_OrgID));

            Call<ResponseBody> call = getServices.GetGroupList(data);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    String json = null;
                    try {
                        json = response.body().string();
                        JSONObject responseObj = new JSONObject(json.trim());

                        JSONArray jsonArray_Group = responseObj.getJSONArray("GroupsList");

                        for (int i = 0; i < jsonArray_Group.length(); i++) {
                            JSONObject jsonObj = jsonArray_Group.getJSONObject(i);
                            SpinnerData spinnerData = new SpinnerData();
                            spinnerData.setName(jsonObj.getString("GroupName"));
                            spinnerData.setId(jsonObj.getString("GroupID"));
                            spinnerData.setObject(jsonObj);
                            spinnerDataArrayListG.add(spinnerData);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Log.d("DeploymentSecond", "onResponse: " + json);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "loadGroups", e);
        }
        return spinnerDataArrayListG;
    }

    private ArrayList<SpinnerData> loadIndividuals() {

        spinnerDataArrayListU = new ArrayList<>();

        try {
            Map<String, String> data = new HashMap<>();
            data.put("UserId", ImproveHelper.getUserDetails(this).getUserID());
            data.put("OrgId", PrefManger.getSharedPreferencesString(DeploymentSecondActivity.this, AppConstants.SP_ORGANISATION_ID, AppConstants.Default_OrgID));

            Call<ResponseBody> call = getServices.GetUserList(data);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    String json = null;
                    try {
                        json = response.body().string();
                        JSONObject responseObj = new JSONObject(json.trim());

                        JSONArray jsonArray_Group = responseObj.getJSONArray("UserList");

                        for (int i = 0; i < jsonArray_Group.length(); i++) {
                            JSONObject jsonObj = jsonArray_Group.getJSONObject(i);
                            SpinnerData spinnerData = new SpinnerData();
                            spinnerData.setName(jsonObj.getString("EmpName") + "," + jsonObj.getString("Role"));
                            spinnerData.setId(jsonObj.getString("EmpID"));
                            spinnerData.setObject(jsonObj);
                            spinnerDataArrayListU.add(spinnerData);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Log.d("DeploymentSecond", "onResponse: " + json);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });


        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "loadIndividuals", e);
        }

        return spinnerDataArrayListU;
    }

    @Override
    public void onItemsSelected(View v, List<SpinnerData> items, List<SpinnerData> selectedItems) {

        if (v == sp_group) {
//            ceSelectedGroups.getText().clear();
            if (sp_group.getSelectedIds() != null) {
                if (selectedItems != null) {


                    for (int i = 0; i < selectedItems.size(); i++) {
//                        strArrayNamesG +=   selectedItems.get(i).getName();
                        arrayListSelectedItemsG.add(selectedItems.get(i).getName());
                        arrayListSelectedItemsGIds.add(selectedItems.get(i).getId());
                        DeployGroupAdapter adapter = new DeployGroupAdapter(DeploymentSecondActivity.this, arrayListSelectedItemsG);
                        rv_groups.setAdapter(adapter);

                        if (arrayListSelectedItemsG.size() > 0) {
                            rv_groups.setVisibility(View.VISIBLE);
                            ct_noGroupSelect.setVisibility(View.GONE);
                        } else {
                            rv_groups.setVisibility(View.GONE);
                            ct_noGroupSelect.setVisibility(View.VISIBLE);
                        }
                    }

                    sp_group.setItems(loadGroups(), this);
//                    ceSelectedGroups.setText(strArrayNames);
                }
            }
        } else if (v == sp_individual) {

//            ceSelectedIndividuals.getText().clear();
            if (sp_individual.getSelectedIds() != null) {
                if (selectedItems != null) {

                    arrayListSelectedItemsI.clear();
                    for (int i = 0; i < selectedItems.size(); i++) {
//                        strArrayNamesI +=   selectedItems.get(i).getName();// selected items appends to string

                        arrayListSelectedItemsI.add(selectedItems.get(i).getName());
                        arrayListSelectedItemsIIds.add(selectedItems.get(i).getId());

                        DeployIndividualAdapter adapter = new DeployIndividualAdapter(DeploymentSecondActivity.this, arrayListSelectedItemsI);
                        rv_individuals.setAdapter(adapter);
                        if (arrayListSelectedItemsI.size() > 0) {
                            rv_individuals.setVisibility(View.VISIBLE);
                            ct_noIndividualSelect.setVisibility(View.GONE);
                        } else {
                            ct_noIndividualSelect.setVisibility(View.VISIBLE);
                            rv_individuals.setVisibility(View.GONE);
                        }
                    }
                    sp_individual.setItems(loadIndividuals(), this);

//                    ceSelectedIndividuals.setText(strArrayNames);
                }
            }
        }
    }


    //     Validate and onclick
    private boolean isValidate() {
        boolean flag = true;
        try {
            if ((arrayListSelectedItemsG != null && arrayListSelectedItemsG.size() == 0)
                    && (arrayListSelectedItemsI != null && arrayListSelectedItemsI.size() == 0)) {
                ImproveHelper.showToast(this, "Please Select Group or Individuals");
                flag = false;
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "isValidate", e);
        }
        return flag;
    }
//    private boolean isValidate() {
//        boolean flag = true;
//
//        if (spinnerDataArrayListG.size()>0 && sp_group.getSelectedIds().size() == 0) {
//            ImproveHelper.showToast(this, "Please Select Group Info");
//            ImproveHelper.setFocus(sp_group);
//            flag = false;
//        } else if (sp_individual.getSelectedIds().size() == 0) {
//            ImproveHelper.showToast(this, "Please Select Individual Info");
//            ImproveHelper.setFocus(sp_individual);
//            flag = false;
//        }
//        return flag;
//    }

    public void onClick_SubmitDS(View view) {

        if (isValidate()) {
            try {
                /*{"UserId": "BLUE   0001","OrgId": "BLUE20191121152218618","PageName":"abc","GroupIDs":"1,2,3","AllEmps":"BLUE0001,BLUE0002","SelectedValue":"Datacollection"}*/
                Map<String, String> data = new HashMap<>();
                data.put("UserId", ImproveHelper.getUserDetails(this).getUserID());
                data.put("OrgId", PrefManger.getSharedPreferencesString(DeploymentSecondActivity.this, AppConstants.SP_ORGANISATION_ID, AppConstants.Default_OrgID));
                data.put("PageName", sp_application_name);
                data.put("GroupIDs", getIDsWithComma(arrayListSelectedItemsGIds));
                data.put("AllEmps", getIDsWithComma(arrayListSelectedItemsIIds));
                data.put("SelectedValue", PrefManger.getSharedPreferencesString(DeploymentSecondActivity.this, "DeployAppType", ""));
                Log.d(TAG, "onClick_SubmitDS: " + PrefManger.getSharedPreferencesString(DeploymentSecondActivity.this, "DeployAppType", ""));
//                data.put("GroupIDs", getIDsWithComma(sp_group));
//                data.put("AllEmps", getIDsWithComma(sp_individual));
//                data.put("SelectedValue", "Datacollection");

                Call<ResponseBody> call = getServices.InsertDeploymentData(data);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        String json = null;
                        try {
                            json = response.body().string();
                            JSONObject responseObj = new JSONObject(json.trim());

                            if (responseObj.getString("Status").trim().equals("200")) {

                                ImproveHelper.showToast(DeploymentSecondActivity.this, "Deployed successfully");
                                finish();

                            } else {
                                ImproveHelper.showToast(DeploymentSecondActivity.this, "Failed Please Try Again...");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            ImproveHelper.showToast(DeploymentSecondActivity.this, "Failed Please Try Again..." + e.getMessage().trim());
                        }

                        Log.d("DeploymentSecond", "onResponse: " + json);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        ImproveHelper.showToast(DeploymentSecondActivity.this, "Failed Please Try Again..." + t.getMessage().trim());

                    }
                });
            } catch (Exception e) {
                improveHelper.improveException(this, TAG, "onClick_SubmitDS", e);
            }
        }
    }


    private String getIDsWithComma(ArrayList<String> arrayListSelectedItems) {
        String comma = "";
        try {
            for (int i = 0; i < arrayListSelectedItems.size(); i++) {
                comma = comma + arrayListSelectedItems.get(i).trim() + ",";
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getIDsWithComma", e);
        }
        return comma.trim().equals("") ? "" : comma.substring(0, comma.length() - 1);

    }

    //    private String getIDsWithComma(SearchableMultiSpinner searchableMultiSpinner) {
//        String comma = "";
//        for (int i = 0; i < searchableMultiSpinner.getSelectedIds().size(); i++) {
//            comma = comma + searchableMultiSpinner.getSelectedIds().get(i).trim() + ",";
//        }
//
//        return comma.trim().equals("") ? "" : comma.substring(0, comma.length() - 1);
//
//    }


}
