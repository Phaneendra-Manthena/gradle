package com.bhargo.user.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhargo.user.R;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nk.mobileapps.spinnerlib.SearchableMultiSpinner;
import nk.mobileapps.spinnerlib.SearchableSpinner;
import nk.mobileapps.spinnerlib.SpinnerData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeploymentActivity extends BaseActivity implements SearchableSpinner.SpinnerListener, SearchableMultiSpinner.SpinnerListener {

    private static final String TAG = "DeploymentActivity";
    Context context;
    ImageView iv_type;
    SearchableSpinner sp_type, sp_application_name;
    SearchableMultiSpinner sp_group, sp_individual;
    GetServices getServices;
    ArrayList<SpinnerData> spinnerDataArrayListG;
    ArrayList<SpinnerData> spinnerDataArrayListU;
    ArrayList<SpinnerData> spinnerDataArrayListAppNames;
    String type, id, name;
    SessionManager sessionManager;
    ImproveHelper improveHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deployment);

        improveHelper = new ImproveHelper(this);
        Intent getIntent = getIntent();

        if (getIntent != null) {

            type = getIntent.getStringExtra("type");
            id = getIntent.getStringExtra("id");
            name = getIntent.getStringExtra("name");
        }

        initViews();

    }

    private void initViews() {
        try {
            context = this;
            getServices = RetrofitUtils.getUserService();
            initializeActionBar();
            title.setText("Deployment");
            ib_settings.setVisibility(View.GONE);
            enableBackNavigation(true);

            sessionManager = new SessionManager(context);

            iv_circle_appIcon.setImageDrawable(getDrawable(R.drawable.icon_bhargo_user));

            //findViewById()
            iv_type = findViewById(R.id.iv_type);
            sp_type = findViewById(R.id.sp_type);
            sp_application_name = findViewById(R.id.sp_application_name);
            sp_group = findViewById(R.id.sp_group);
            sp_individual = findViewById(R.id.sp_individual);
            sp_type.setItems(loadTypes(), this);
            sp_application_name.setItems(new ArrayList<SpinnerData>(), this);
            sp_group.setItems(loadGroups(), this);
            sp_individual.setItems(loadIndividuals(), this);

            ((TextView) findViewById(R.id.ctv_createdby)).setText("CreatedBy :" + sessionManager.getUserDetailsFromSession().getName());

            if (type.equalsIgnoreCase("I")) {
                sp_individual.setVisibility(View.VISIBLE);
                sp_group.setVisibility(View.GONE);

                String[] defaultItem = new String[1];
                defaultItem[0] = "" + id;

                sp_individual.setItemIDs(defaultItem);

            } else {
                sp_individual.setVisibility(View.GONE);
                sp_group.setVisibility(View.VISIBLE);

                String[] defaultItem = new String[1];
                defaultItem[0] = "" + id;

                sp_group.setItemIDs(defaultItem);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "DeploymentActivity", e);
        }

    }


        private ArrayList<SpinnerData> loadTypes () {
            ArrayList<SpinnerData> spinnerDataArrayList = new ArrayList<>();


            String[] types = {"Data Collection", "Query Form", "Child Form", "Module", "GIS Report", "Dashboard", "Reports"};
            String[] types_id = {"Datacollection", "QueryForm", "ChildForm", "Module", "GISReport", "DashBoard", "Reports"};
            int[] res_types = {R.drawable.type_data_collection, R.drawable.type_query_form, R.drawable.type_childfrom,
                    R.drawable.type_module, R.drawable.type_gis, R.drawable.type_dashboard, R.drawable.type_reports};
            for (int i = 0; i < types.length; i++) {
                SpinnerData spinnerData = new SpinnerData();
                spinnerData.setName(types[i]);
                spinnerData.setId(types_id[i]);
                spinnerData.setObject(res_types[i]);
                spinnerDataArrayList.add(spinnerData);
            }

            return spinnerDataArrayList;
        }

        private ArrayList<SpinnerData> loadNames (String selectedValue){
            spinnerDataArrayListAppNames = new ArrayList<>();


            try {
                Map<String, String> data = new HashMap<>();
                data.put("UserId", ImproveHelper.getUserDetails(this).getUserID());
                data.put("OrgId", PrefManger.getSharedPreferencesString(DeploymentActivity.this, AppConstants.SP_ORGANISATION_ID, AppConstants.Default_OrgID));


                data.put("SelectedValue", selectedValue);

                Call<ResponseBody> call = getServices.BindPageNames(data);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        String json = null;
                        try {
                            json = response.body().string();
                            JSONObject responseObj = new JSONObject(json.trim());

                            JSONArray jsonArray_Group = responseObj.getJSONArray("PageNames");

                            for (int i = 0; i < jsonArray_Group.length(); i++) {
                                JSONObject jsonObj = jsonArray_Group.getJSONObject(i);
                                SpinnerData spinnerData = new SpinnerData();
                                spinnerData.setName(jsonObj.getString("PageName"));
                                spinnerData.setId((i + 1) + "");
                                spinnerData.setObject(jsonObj);
                                spinnerDataArrayListAppNames.add(spinnerData);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Log.d("DeploymentActivity", "onResponse: " + json);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

            return spinnerDataArrayListAppNames;
        }

        private ArrayList<SpinnerData> loadGroups () {
        /*http://182.18.157.124/ImproveApplications/api/GroupManagement/GetGroupList
{"UserId": "BLUE0001","OrgId": "BLUE20191121152218618"}*/
            spinnerDataArrayListG = new ArrayList<>();


            try {
                Map<String, String> data = new HashMap<>();
                data.put("UserId", ImproveHelper.getUserDetails(this).getUserID());
                data.put("OrgId", PrefManger.getSharedPreferencesString(DeploymentActivity.this, AppConstants.SP_ORGANISATION_ID, AppConstants.Default_OrgID));


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

                        Log.d("DeploymentActivity", "onResponse: " + json);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }


            return spinnerDataArrayListG;
        }

        private ArrayList<SpinnerData> loadIndividuals () {
            spinnerDataArrayListU = new ArrayList<>();


            try {
                Map<String, String> data = new HashMap<>();
                data.put("UserId", ImproveHelper.getUserDetails(this).getUserID());
                data.put("OrgId", PrefManger.getSharedPreferencesString(DeploymentActivity.this, AppConstants.SP_ORGANISATION_ID, AppConstants.Default_OrgID));


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

                        Log.d("DeploymentActivity", "onResponse: " + json);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

            return spinnerDataArrayListU;
        }

        @Override
        public void onBackPressed () {

            finish();
        }

        @Override
        public void onItemsSelected (View v, List < SpinnerData > items,int position){
            if (v == sp_type) {
                if (sp_type.getSelectedId() != null) {
//                ((TextView) findViewById(R.id.ctv_createdby)).setText("");
                    iv_type.setImageResource((Integer) sp_type.getSelectedItem().getObject());
                    switch ((Integer) sp_type.getSelectedItem().getObject()) {

                        case R.drawable.type_data_collection:
                            sp_application_name.setItems(loadNames("Datacollection"), this);
                            break;
                        case R.drawable.type_query_form:
                            sp_application_name.setItems(loadNames("QueryForm"), this);
                            break;
                        case R.drawable.type_childfrom:
                            sp_application_name.setItems(loadNames("ChildForm"), this);
                            break;
                        case R.drawable.type_module:
                            sp_application_name.setItems(loadNames("Module"), this);
                            break;
                        case R.drawable.type_gis:
                            sp_application_name.setItems(loadNames("GISReport"), this);
                            break;
                        case R.drawable.type_dashboard:
                            sp_application_name.setItems(loadNames("DashBoard"), this);
                            break;
                        case R.drawable.type_reports:
                            sp_application_name.setItems(loadNames("Reports"), this);
                            break;
                        default:

                            break;
                    }
                } else {
//                ((TextView) findViewById(R.id.ctv_createdby)).setText("");
                }
            } else if (v == sp_application_name) {
                if (sp_application_name.getSelectedId() != null) {
                    try {
                        JSONObject object = (JSONObject) sp_application_name.getSelectedItem().getObject();
//                    ((TextView) findViewById(R.id.ctv_createdby)).setText("CreatedBy :" + object.getString("CreatedBy"));
                    } catch (Exception e) {
                    }
                }

            }

        }

        private String getIDsWithComma (SearchableMultiSpinner searchableMultiSpinner){
            String comma = "";
            for (int i = 0; i < searchableMultiSpinner.getSelectedIds().size(); i++) {
                comma = comma + searchableMultiSpinner.getSelectedIds().get(i).trim() + ",";
            }

            return comma.trim().equals("") ? "" : comma.substring(0, comma.length() - 1);

        }

        public void onClick_Submit (View view){
            if (isValidate()) {
                try {
                    /*{"UserId": "BLUE0001","OrgId": "BLUE20191121152218618","PageName":"abc","GroupIDs":"1,2,3","AllEmps":"BLUE0001,BLUE0002","SelectedValue":"Datacollection"}*/
                    Map<String, String> data = new HashMap<>();
                    data.put("UserId", ImproveHelper.getUserDetails(this).getUserID());
                    data.put("OrgId", PrefManger.getSharedPreferencesString(DeploymentActivity.this, AppConstants.SP_ORGANISATION_ID, AppConstants.Default_OrgID));


                    data.put("PageName", sp_application_name.getSelectedName());
                    data.put("GroupIDs", getIDsWithComma(sp_group));
                    data.put("AllEmps", getIDsWithComma(sp_individual));
                    data.put("SelectedValue", sp_type.getSelectedId());

                    Call<ResponseBody> call = getServices.InsertDeploymentData(data);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            String json = null;
                            try {
                                json = response.body().string();
                                JSONObject responseObj = new JSONObject(json.trim());

                                if (responseObj.getString("Status").trim().equals("200")) {
                                    ImproveHelper.showToast(DeploymentActivity.this, "Deployment done success!");
                                    finish();
                                } else {
                                    ImproveHelper.showToast(DeploymentActivity.this, "Failed Please Try Again...");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                ImproveHelper.showToast(DeploymentActivity.this, "Failed Please Try Again..." + e.getMessage().trim());
                            }

                            Log.d("DeploymentActivity", "onResponse: " + json);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            ImproveHelper.showToast(DeploymentActivity.this, "Failed Please Try Again..." + t.getMessage().trim());

                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                    ImproveHelper.showToast(DeploymentActivity.this, "Failed Please Try Again..." + e.getMessage().trim());
                }
            }
        }

        private boolean isValidate () {
            boolean flag = true;

            if (sp_type.getSelectedId() == null) {
                ImproveHelper.showToast(this, "Please Select Application Type");
                ImproveHelper.setFocus(sp_type);
                flag = false;
            } else if (sp_application_name.getSelectedId() == null) {
                ImproveHelper.showToast(this, "Please Select Application Name");
                ImproveHelper.setFocus(sp_application_name);
                flag = false;
            } else if (spinnerDataArrayListG.size() > 0 && sp_group.getSelectedIds().size() == 0 && sp_group.getVisibility() == View.VISIBLE) {
                ImproveHelper.showToast(this, "Please Select Group Info");
                ImproveHelper.setFocus(sp_group);
                flag = false;
            } else if (sp_individual.getSelectedIds().size() == 0 && sp_individual.getVisibility() == View.VISIBLE) {
                ImproveHelper.showToast(this, "Please Select Individual Info");
                ImproveHelper.setFocus(sp_individual);
                flag = false;
            }
            return flag;
        }

        @Override
        public void onItemsSelected (View
        v, List < SpinnerData > items, List < SpinnerData > selectedItems){
            if (v == sp_group) {

            } else if (v == sp_individual) {

            }
        }
    }
