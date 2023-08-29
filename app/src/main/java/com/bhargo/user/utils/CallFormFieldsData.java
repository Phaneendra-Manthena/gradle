package com.bhargo.user.utils;

import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.bhargo.user.Java_Beans.ActionWithoutCondition_Bean;
import com.bhargo.user.R;
import com.bhargo.user.interfaces.GetServices;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallFormFieldsData{

    ActionWithoutCondition_Bean ActionBean;
    JsonObject inputJson;
    private OnResponse onResponse;
    Context context;
    String appMode;
    String orgId;
    String tableName;
    private GetServices getServices;
    ImproveDataBase improveDataBase;
    ImproveHelper improveHelper;
    SessionManager sessionManager;
    String formFieldsResponse=null;
    JSONArray filters = new JSONArray();

    public interface OnResponse {
        void response(String response);
        void failure(String msg);
    }

    public CallFormFieldsData(Context context,String orgId,String tableName,JSONArray filters , JsonObject inputJson, String appMode,
                              ActionWithoutCondition_Bean ActionBean,OnResponse onResponse) {
        this.context = context;
        this.inputJson = inputJson;
        this.onResponse = onResponse;
        this.orgId = orgId;
        this.tableName = tableName;
        this.filters = filters;
        this.appMode = appMode;
        this.ActionBean=ActionBean;
        improveHelper = new ImproveHelper();
        improveDataBase = new ImproveDataBase(context);
        getServices = RetrofitUtils.getUserService();
        sessionManager = new SessionManager(context);
        //showProgressDialog(context, "Please Wait! Loading...");
        getData();
    }

    private void getData() {
        {
            formFieldsResponse=null;
            JSONObject jsonObject = new JSONObject();
            if(appMode==null||appMode.contentEquals("")){
                appMode = "online";
            }
            if(ActionBean!=null && ActionBean.getDataBaseTableType()!=null && ActionBean.getDataBaseTableType().equalsIgnoreCase("Mobile")){
                appMode="Mobile";
            }
            if(ActionBean!=null && ActionBean.getDataBaseTableType()!=null && ActionBean.getDataBaseTableType().equalsIgnoreCase("Both")){
               if(improveDataBase.tableExists(tableName)){
                   appMode="Mobile";
               }
            }
             if (appMode.equalsIgnoreCase("online")||appMode.equalsIgnoreCase("Hybrid")) {
                 if (isNetworkStatusAvialable(context)) {
                     Call<String> call = getServices.GetFormData1(sessionManager.getAuthorizationTokenId(),inputJson);
                     call.enqueue(new Callback<String>() {
                         @Override
                         public void onResponse(Call<String> call, Response<String> response) {
                             Log.e("Result: ", "" + response.body());
                             //closeProgressDialog();
                             if (response.body() != null) {
                                 formFieldsResponse = response.body();
                                 if (formFieldsResponse != null) {
                                     onResponse.response(formFieldsResponse);
                                 }
                             }
                         }

                         @Override
                         public void onFailure(Call<String> call, Throwable t) {
                             //formFieldsResponse = "failed";
                             onResponse.failure(t.getMessage());
                         }
                     });
                 }else{
                     onResponse.failure(context.getString(R.string.no_internet_available));
                 }

            } else {
                  String OrderByColumns = inputJson.get("OrderByColumns").getAsString();
                  String Order =  inputJson.get("Order").getAsString();
                formFieldsResponse =  improveDataBase.getCallFormFieldsDataWithFilters(orgId,tableName,filters,OrderByColumns,Order);
//                formFieldsResponse =  improveDataBase.getNearestLocations(orgId,tableName,filters,OrderByColumns,Order);
                 //closeProgressDialog();
                 if (formFieldsResponse != null) {
                    onResponse.response(formFieldsResponse);
                }
            }
        }
    }

}
