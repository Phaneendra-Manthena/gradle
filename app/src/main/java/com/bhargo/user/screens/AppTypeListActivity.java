package com.bhargo.user.screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.AppsTypeListAdapter;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppTypeResponse;
import com.bhargo.user.pojos.PageNamesList;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppTypeListActivity extends BaseActivity implements AppsTypeListAdapter.IClickListenerApp, View.OnClickListener {

    private static final String TAG = "AppTypeListActivity";
    public static String appName = null;
    Context context;
    RecyclerView rv_appTypeList;
    String strAppType;
    GetServices getServices;
    CustomTextView tv_noRecords;
    CustomEditText cet_appsSearch;
    String type, id, name;
    String userType, userID;
    CustomButton cb_deploy;
    ImproveHelper improveHelper;
    private AppsTypeListAdapter appsTypeListAdapter;
    private List<PageNamesList> pageNamesLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_type_list);

        context = this;
        improveHelper = new ImproveHelper(this);
        initViews();
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getStringExtra("type");
            id = intent.getStringExtra("id");
            name = intent.getStringExtra("name");
        }

        rv_appTypeList = findViewById(R.id.rv_appTypeList);
        tv_noRecords = findViewById(R.id.tv_noRecords);
        cet_appsSearch = findViewById(R.id.cet_appsSearch);
        cb_deploy = findViewById(R.id.cb_deploy);
        cb_deploy.setOnClickListener(this);
// set a LinearLayoutManager with default orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_appTypeList.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

        if (getIntent() != null && getIntent().getStringExtra("AppType") != null) {
            strAppType = getIntent().getStringExtra("AppType");
            title.setText(strAppType);
            mAppTypeListApi(strAppType);

            Log.d(TAG, "onCreateAppType: " + strAppType);
        }


        cet_appsSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // filter your list from your input
                filter(s.toString());

            }
        });


    }

    private void initViews() {
        try {
            getServices = RetrofitUtils.getUserService();
            initializeActionBar();
            ib_settings.setVisibility(View.GONE);
            enableBackNavigation(true);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "initViews", e);
        }
    }


    private void mAppTypeListApi(final String strAppType) {
//        AppsAdapter appsAdapter = new AppsAdapter(AppsListActivity.this, getAllAppModel.getAppDetails());
//        rv_apps.setAdapter(appsAdapter);
//        appsAdapter.setCustomClickListener(AppsListActivity.this);

        try {
            Map<String, String> data = new HashMap<>();
            data.put("UserId", ImproveHelper.getUserDetails(this).getUserID());
            data.put("OrgId", PrefManger.getSharedPreferencesString(AppTypeListActivity.this, AppConstants.SP_ORGANISATION_ID, AppConstants.Default_OrgID));
            data.put("SelectedValue", strAppType);
            Log.d(TAG, "mAppTypeListApi: " + data);
            Call<AppTypeResponse> call = getServices.BindPageNamesList(data);
            call.enqueue(new Callback<AppTypeResponse>() {
                @Override
                public void onResponse(Call<AppTypeResponse> call, Response<AppTypeResponse> response) {

                    String json = null;
                    try {
                        if (response.body() != null) {
                            AppTypeResponse appTypeResponse = response.body();
                            if (appTypeResponse.getStatus().equals("200")) {
                                PrefManger.putSharedPreferencesString(AppTypeListActivity.this, "DeployAppType", strAppType);
                                if (appTypeResponse.getPageNames().size() > 0) {
                                    pageNamesLists = appTypeResponse.getPageNames();
                                    appsTypeListAdapter = new AppsTypeListAdapter(AppTypeListActivity.this, appTypeResponse.getPageNames(), type, id);
                                    appsTypeListAdapter.setIClickListener(AppTypeListActivity.this);
                                    rv_appTypeList.setAdapter(appsTypeListAdapter);
                                    tv_noRecords.setVisibility(View.GONE);
                                } else {
                                    tv_noRecords.setVisibility(View.VISIBLE);
                                }
                            } else {
                                tv_noRecords.setVisibility(View.VISIBLE);
                                ImproveHelper.showToast(AppTypeListActivity.this, appTypeResponse.getMessage());
                            }
                            json = response.body().toString();
                            JSONObject responseObj = new JSONObject(json.trim());
                            Log.d(TAG, "onResponseAppType: " + responseObj.toString());
                        } else {
                            tv_noRecords.setVisibility(View.VISIBLE);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Log.d("DeploymentActivity", "onResponse: " + json);

                }

                @Override
                public void onFailure(Call<AppTypeResponse> call, Throwable t) {

                }
            });
//            call.enqueue(new Callback<AppTypeResponse>() {
//                @Override
//                public void onResponse(Call<AppTypeResponse> call, Response<AppTypeResponse> response) {
//
//                    String json = null;
//                    try {
//                        json = response.body().string();
//                        JSONObject responseObj = new JSONObject(json.trim());
//                        Log.d(TAG, "onResponseAppType: "+responseObj.toString());
//
//                        JSONArray jsonArray_Group = responseObj.getJSONArray("PageNames");
//
//                        for (int i = 0; i < jsonArray_Group.length(); i++) {
//                            JSONObject jsonObj = jsonArray_Group.getJSONObject(i);
//
//                            String pageName = jsonObj.getString("PageName");
////                            AppsTypeListAdapter appsTypeListAdapter = new AppsTypeListAdapter(AppTypeListActivity.this,pageName);
////                            rv_appTypeList.setAdapter(appsTypeListAdapter);
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    Log.d("DeploymentActivity", "onResponse: " + json);
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                }
//            });


        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "mAppTypeListApi", e);
        }

    }

    public void filter(String text) {
        try {
            if (!text.isEmpty()) {
                List<PageNamesList> temp = new ArrayList();
                if (pageNamesLists != null) {
                    for (PageNamesList d : pageNamesLists) {
                        //or use .equal(text) with you want equal match
                        //use .toLowerCase() for better matches
                        if (d.getPageName().toLowerCase().contains(text.toLowerCase())) {
                            temp.add(d);
                        }
                    }
                }
                //Update recyclerView
                appsTypeListAdapter.updateList(temp);
            } else {
                List<PageNamesList> newList = new ArrayList<>();
                appsTypeListAdapter.updateList(newList);
                appsTypeListAdapter.updateList(pageNamesLists);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "filter", e);
        }

    }

    @Override
    public void onItemClickedAPP(String app_name, String type, String id) {

        Log.d(TAG, "onItemClickedAPP: " + app_name);
        appName = app_name;
        this.userType = type;
        this.userID = id;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_deploy:

                deploy();
                break;
        }
    }

    private void deploy() {

        if (appName != null) {
            try {
                /*{"UserId": "BLUE   0001","OrgId": "BLUE20191121152218618","PageName":"abc","GroupIDs":"1,2,3","AllEmps":"BLUE0001,BLUE0002","SelectedValue":"Datacollection"}*/
                Map<String, String> data = new HashMap<>();
                data.put("UserId", ImproveHelper.getUserDetails(this).getUserID());
                data.put("OrgId", PrefManger.getSharedPreferencesString(context, AppConstants.SP_ORGANISATION_ID, AppConstants.Default_OrgID));
                data.put("PageName", appName);
                if (userType.equalsIgnoreCase("G")) {
                    data.put("GroupIDs", userID);
                    data.put("AllEmps", null);
                } else {
                    data.put("GroupIDs", null);
                    data.put("AllEmps", userID);
                }


                data.put("SelectedValue", PrefManger.getSharedPreferencesString(context, "DeployAppType", ""));
                Log.d(TAG, "onClick_SubmitDS: " + PrefManger.getSharedPreferencesString(context, "DeployAppType", ""));
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
                                ImproveHelper.showToast(context, "Deployed successfully");
//                                finish();
                                backToChat();

                            } else {
                                ImproveHelper.showToast(context, "Failed Please Try Again...");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            ImproveHelper.showToast(context, "Failed Please Try Again..." + e.getMessage().trim());
                        }

                        Log.d("DeploymentSecond", "onResponse: " + json);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        ImproveHelper.showToast(context, "Failed Please Try Again..." + t.getMessage().trim());

                    }
                });


            } catch (Exception e) {
                improveHelper.improveException(this, TAG, "deploy", e);
            }
        }
    }

    private void backToChat() {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("AppName", appName);
            bundle.putString("AppType", strAppType);

            Intent returnIntent = new Intent();
            returnIntent.putExtras(bundle);

            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "backToChat", e);
        }
    }
}
