package com.bhargo.user.adapters;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CAMERA;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_GPS;
import static com.bhargo.user.utils.AppConstants.CurrentAppObject;
import static com.bhargo.user.utils.AppConstants.Current_onChangeEventObject;
import static com.bhargo.user.utils.AppConstants.MULTI_FORM;
import static com.bhargo.user.utils.AppConstants.WEB_VIEW;
import static com.bhargo.user.utils.AppConstants.WEB_VIEW_DATA;
import static com.bhargo.user.utils.AppConstants.currentMultiForm;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.showToast;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.Java_Beans.Control_EventObject;
import com.bhargo.user.Java_Beans.Create_Query_Object;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.Java_Beans.DataManagementOptions;
import com.bhargo.user.Java_Beans.MultiFormApp;
import com.bhargo.user.Java_Beans.WebLinkObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.Query.QueryDetailsActivity;
import com.bhargo.user.Query.QueryIndexColumnsGridActivity;

import com.bhargo.user.controls.advanced.GridControl;
import com.bhargo.user.controls.advanced.SubformView;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.interfaces.ItemClickListener;
import com.bhargo.user.pojos.AppDetails;
import com.bhargo.user.pojos.AppDetailsAdvancedInput;
import com.bhargo.user.pojos.CallFormDataResponse;
import com.bhargo.user.pojos.Control;
import com.bhargo.user.pojos.FormControls;
import com.bhargo.user.pojos.FormLevelTranslation;
import com.bhargo.user.pojos.FormNavigation;
import com.bhargo.user.pojos.GetChildFormDataResponse;
import com.bhargo.user.realm.RealmDBHelper;
import com.bhargo.user.realm.RealmTables;
import com.bhargo.user.screens.ML_Testing_Activity;
import com.bhargo.user.screens.ML_Training_Activity;
import com.bhargo.user.screens.SubAppsListActivity;
import com.bhargo.user.screens.ViewDataActivity;
import com.bhargo.user.screens.ViewDataWithMapActivity;
import com.bhargo.user.screens.ViewMapAndDataActivity;
import com.bhargo.user.screens.WebLinkViewActivity;
import com.bhargo.user.uisettings.pojos.UILayoutProperties;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.DataCollection;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bhargo.user.actions.SyncData;
import com.bhargo.user.utils.XMLHelper;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nk.bluefrog.library.utils.Helper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchAppsAdapter extends RecyclerView.Adapter<SearchAppsAdapter.MyViewHolder> {

    private static final String TAG = "AppsAdapter";
    private final GetServices getServices;
    private final ImproveDataBase improveDataBase;
    public boolean isMultiClickable = true;
    public ProgressDialog progressDialog;
    List<AppDetails> AppsList;
    Context context1;
    Activity context;
    SessionManager sessionManager;
    LinkedHashMap<String, String> appNames;
    LinkedHashMap<String, String> appDescs;
    XMLHelper xmlHelper;
//    DataCollectionObject dataCollectionObject;
    DataManagementOptions dataManagementOptions;
    boolean isFromApps;
    boolean isFromWorkspaceapp;
    ImproveHelper improveHelper;
    private ItemClickListener clickListener;
    private String appLanguage = "en";
    FormControls formControls;
    Gson gson;

    public SearchAppsAdapter(Activity context, List<AppDetails> AppsList, boolean isFromApps, boolean isFromWorkspaceapp) {
        this.context = context;
        this.AppsList = AppsList;
        this.isFromApps = isFromApps;
        getServices = RetrofitUtils.getUserService();
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);
        sessionManager = new SessionManager(context);
        appLanguage = ImproveHelper.getLocale(context);
        xmlHelper = new XMLHelper();
        this.isFromWorkspaceapp = isFromWorkspaceapp;
        gson = new Gson();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layouta
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.apps_list_item_grid, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.apps_list_item_grid_search, parent, false);
        // set the view's size, margins, paddings and layout_sample_app parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder

        return vh;
    }

    public void setCustomClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        final AppDetails AppsDetails = AppsList.get(position);

//        holder.name.setText(AppsDetails.getAppName());
        if (AppsDetails.getDisplayAppName() != null && !AppsDetails.getDisplayAppName().contentEquals("")) {
            holder.name.setText(AppsDetails.getDisplayAppName());
        } else {
            holder.name.setText(AppsDetails.getAppName());
        }
        holder.desc.setText(AppsDetails.getDescription());
//        holder.iv_circle.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_auto_mobiles));

        if (AppsDetails.getAppType().contentEquals(AppConstants.WEB_Link)) {
            WebLinkObject webLinkObject = new XMLHelper().XML_To_WebLinkObject(AppsDetails.getDesignFormat());
            appNames = webLinkObject.getTranslatedAppNames();
            appDescs = webLinkObject.getTranslatedAppDescriptions();

        } else if (AppsDetails.getAppType().equalsIgnoreCase("QueryForm")||AppsDetails.getAppType().equalsIgnoreCase("Query")) {
            Create_Query_Object create_query_object = new XMLHelper().XML_To_QueryFormObject(AppsDetails.getDesignFormat(),context);
            appNames = create_query_object.getTranslatedAppNamesMap();
            appDescs = create_query_object.getTranslatedAppDescriptionsMap();

        } else if (isFromApps && AppsDetails.getDisplayAs() != null && AppsDetails.getDisplayAs().equalsIgnoreCase("Application")) {
            Log.d(TAG, "onBindViewHolderAppsAdapter: " + AppsList.get(position).getAppName() + "," + AppsList.get(position).getDisplayAs());
            //nk step
            System.out.println("=======Step4===============");
            String designFormat= improveDataBase.getTableColDataByCond(ImproveDataBase.APPS_LIST_TABLE,"DesignFormat",new String[]{"AppName"},new String[]{AppsDetails.getAppName()}).get(0).get(0);
            FormLevelTranslation formLevelTranslation = DataCollection.getFormLevelRTL(designFormat);
            appNames = formLevelTranslation.getTranslatedAppNames();
            appDescs = formLevelTranslation.getTranslatedAppDescriptions();
            AppsDetails.setFormLevelTranslation(formLevelTranslation);

        } else if (!isFromApps && AppsDetails.getDisplayAs() != null && AppsDetails.getDisplayAs().equalsIgnoreCase("Report")) {

            Log.d(TAG, "onBindViewHolderReports: " + AppsList.get(position).getAppName() + "," + AppsList.get(position).getDisplayAs());
            //nk step
            System.out.println("=======Step4===============");
            String designFormat= improveDataBase.getTableColDataByCond(ImproveDataBase.APPS_LIST_TABLE,"DesignFormat",new String[]{"AppName"},new String[]{AppsDetails.getAppName()}).get(0).get(0);
            FormLevelTranslation formLevelTranslation = DataCollection.getFormLevelRTL(designFormat);
            appNames = formLevelTranslation.getTranslatedAppNames();
            appDescs = formLevelTranslation.getTranslatedAppDescriptions();
            AppsDetails.setFormLevelTranslation(formLevelTranslation);
        }

        if (!appLanguage.contentEquals("en") && !appLanguage.equalsIgnoreCase("")) {
            if (appNames != null && appNames.size() > 0) {
                holder.name.setText(appNames.get(appLanguage));
            } else {
                holder.name.setText(AppsDetails.getDisplayAppName());
            }
            if (appDescs != null && appDescs.size() > 0) {
                holder.desc.setText(appDescs.get(appLanguage));
            } else {
                holder.desc.setText(AppsDetails.getDescription());
            }
        } else {
            if (AppsDetails.getDisplayAppName() != null && !AppsDetails.getDisplayAppName().contentEquals("")) {
                holder.name.setText(AppsDetails.getDisplayAppName());
            } else {
                holder.name.setText(AppsDetails.getAppName());
            }
            holder.desc.setText(AppsDetails.getDescription());
        }

        Log.d(TAG, "onBindViewHolderAllAppsAdapter: " + AppsList.get(position).getAppName() + "," + AppsList.get(position).getDisplayAppName() + "," + AppsList.get(position).getDisplayAs());
        holder.tvAppMLType.setVisibility(View.GONE);
        if (AppsList.get(position).getAppType().equalsIgnoreCase(AppConstants.REPORTS)) {
            holder.iv_circle.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_reports));
        } else if (AppsList.get(position).getAppType().equalsIgnoreCase(AppConstants.DASHBOARD)) {
            holder.iv_circle.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_dashboard));
        } else if (AppsList.get(position).getAppType().equalsIgnoreCase(AppConstants.ML)) {
            if (!AppsList.get(position).getAppIcon().equalsIgnoreCase("NA")) {
             loadFormIcon(AppsDetails,holder);
//                loadFormIconFromNetWork(AppsDetails,holder);

            } else {
                Glide.with(context).clear(holder.iv_circle);
                // remove the placeholder (optional); read comments below
                holder.iv_circle.setImageDrawable(context.getResources().getDrawable(R.drawable.round_rect_shape));
            }

            if (AppsList.get(position).getTraining() != null && AppsList.get(position).getTraining().equalsIgnoreCase("true")) {
                holder.tvAppMLType.setText("Training");
                holder.tvAppMLType.setVisibility(View.VISIBLE);
            } else if (AppsList.get(position).getTesting() != null && AppsList.get(position).getTesting().equalsIgnoreCase("true")) {
                holder.tvAppMLType.setText("Testing");
                holder.tvAppMLType.setVisibility(View.VISIBLE);
            } else {
                holder.tvAppMLType.setVisibility(View.GONE);
            }

        } else if (AppsList.get(position).getAppIcon() != null) {

            if (!AppsList.get(position).getAppIcon().equalsIgnoreCase("NA")) {
                loadFormIcon(AppsDetails,holder);
//                loadFormIconFromNetWork(AppsDetails,holder);

/*
                if (isNetworkStatusAvialable(context)) {
                    Glide.with(context).load(AppsDetails.getAppIcon()).placeholder(context.getResources().getDrawable(R.drawable.allapps)).into(holder.iv_circle);
                } else {
                    String appName = AppsDetails.getAppName();
                    String replaceWithUnderscore = appName.replaceAll(" ", "_");
                    String[] imgUrlSplit = AppsDetails.getAppIcon().split("/");
                    String imgNameInPackage = imgUrlSplit[imgUrlSplit.length - 1].replaceAll(" ", "_");
                    String strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + replaceWithUnderscore;
//                    setImageFromSDCard(holder, strSDCardUrl, imgUrlSplit[imgUrlSplit.length - 1]);
                    improveHelper.setImageFromPackageFolder(context,strSDCardUrl,imgNameInPackage,holder.iv_circle);
                }
*/
            } else {
                Glide.with(context).clear(holder.iv_circle);
                // remove the placeholder (optional); read comments below
                holder.iv_circle.setImageDrawable(context.getResources().getDrawable(R.drawable.round_rect_shape));
            }
        }

    }

    private void loadFormIcon(AppDetails appDetails,MyViewHolder holder) {
        String appName = appDetails.getAppName();
        String replaceWithUnderscore = appName.replaceAll(" ", "_");
        String[] imgUrlSplit = appDetails.getAppIcon().split("/");
        String imgNameInPackage = imgUrlSplit[imgUrlSplit.length - 1].replaceAll(" ", "_");
        String strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + replaceWithUnderscore;

        if(ImproveHelper.isFileExistsInExternalPackage(context,strSDCardUrl,imgNameInPackage)){
//            Log.d(TAG, "loadFormIconSD: "+imgNameInPackage);
            improveHelper.setImageFromPackageFolder(context,strSDCardUrl,imgNameInPackage,holder.iv_circle);
        }else{
            if (isNetworkStatusAvialable(context)) {
                Glide.with(context).load(appDetails.getAppIcon()).placeholder(context.getResources().getDrawable(R.drawable.api)).into(holder.iv_circle);
            }
        }
    }
    private void loadFormIconFromNetWork(AppDetails appDetails,MyViewHolder holder) {
        if (isNetworkStatusAvialable(context)) {
            Glide.with(context).load(appDetails.getAppIcon()).placeholder(context.getResources().getDrawable(R.drawable.api)).into(holder.iv_circle);
        }else {
            String appName = appDetails.getAppName();
            String replaceWithUnderscore = appName.replaceAll(" ", "_");
            String[] imgUrlSplit = appDetails.getAppIcon().split("/");
            String imgNameInPackage = imgUrlSplit[imgUrlSplit.length - 1].replaceAll(" ", "_");
            String strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + replaceWithUnderscore;

            if (ImproveHelper.isFileExistsInExternalPackage(context, strSDCardUrl, imgNameInPackage)) {
                Log.d(TAG, "loadFormIconSD: " + imgNameInPackage);
                improveHelper.setImageFromPackageFolder(context, strSDCardUrl, imgNameInPackage, holder.iv_circle);
            }
        }
    }

    @Override
    public int getItemCount() {
        return AppsList.size();
    }

//    public void setImageFromSDCard(MyViewHolder holder, String strImagePath, String filename) {
//
////        File imgFile = new File(Environment.getExternalStorageDirectory(), strImagePath);
////        File imgFile = new File(Environment.getExternalStorageDirectory() + strImagePath);
//        File cDir = context.getExternalFilesDir(strImagePath);
//        File appSpecificExternalDir = new File(cDir.getAbsolutePath(), filename);
//        Log.d(TAG, "FileExitsAdapter: " + appSpecificExternalDir);
//        if (appSpecificExternalDir.exists()) {
//
//            Bitmap myBitmap = BitmapFactory.decodeFile(appSpecificExternalDir.getAbsolutePath());
//
//            holder.iv_circle.setImageBitmap(myBitmap);
//
//        }
//    }

/*
//    public void setImageFromSDCard(MyViewHolder holder, String strImagePath) {
//
//        File imgFile = new File(Environment.getExternalStorageDirectory(), strImagePath);
//
//        if (imgFile.exists()) {
//
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//
//            holder.iv_circle.setImageBitmap(myBitmap);
//
//        }
//    }
*/

    public void updateList(List<AppDetails> list) {
        AppsList = list;
        notifyDataSetChanged();
    }

    private void prepareChildFormJsonData(Context context, int getAdapterPosition, String designFormat) {

        XMLHelper xmlHelper = new XMLHelper();
        DataCollectionObject dataCollectionObject = xmlHelper.XML_To_ChildFormObject(designFormat);
        /*Getting BaseQueryName from the xmlParser*/
        String strParseQueryName = dataCollectionObject.getApp_QueryBase();
        PrefManger.putSharedPreferencesString(context, AppConstants.SP_CHILD_FORM_QUERY_NAME, strParseQueryName);

        Log.d(TAG, "ChildDataQueryName: " + dataCollectionObject.getApp_QueryBase());
        JSONObject jObjChildForm = new JSONObject();
        try {
            jObjChildForm.put("queryname", strParseQueryName);
            jObjChildForm.put("orgid", sessionManager.getOrgIdFromSession());
            jObjChildForm.put("userid", sessionManager.getUserDataFromSession().getUserID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("Jasonstring", jObjChildForm.toString());
        Log.d(TAG, "prepareChildFormJsonData: " + stringStringMap);
        // check if the queryname is existed or not
//        if exists
//        get from local db

        AppDetails appDetailsDbList = improveDataBase.getAppDetailsRefresh
                (sessionManager.getOrgIdFromSession(), strParseQueryName, sessionManager.getUserDataFromSession().getUserID());

        if (appDetailsDbList != null && appDetailsDbList.getDesignFormat() != null) {

            String strChildDesignFormat = appDetailsDbList.getDesignFormat();

            intentData(context, strParseQueryName, strChildDesignFormat);
            Log.d(TAG, "PreparedParseQCD: " + strParseQueryName + "\n" + strChildDesignFormat);

        } else {
//        else server senario
            /* Get Child Form Data Api*/
            Log.d(TAG, "PreparedParseQCD: " + "NoQuery In Database");
            mGetChildFormData(context, stringStringMap, strParseQueryName, getAdapterPosition);
        }
//        mGetChildFormData(context, stringStringMap, strParseQueryName, getAdapterPosition);
    }

    /* Get Child Form Data Api*/

    private void mGetChildFormData(final Context context, Map<String, String> stringStringMap, final String QueryName, final int position) {

        Call<GetChildFormDataResponse> appDetailsCallQ = getServices.getChildFormData(stringStringMap);

        appDetailsCallQ.enqueue(new Callback<GetChildFormDataResponse>() {
            @Override
            public void onResponse(Call<GetChildFormDataResponse> call, Response<GetChildFormDataResponse> response) {

                if (response.body() != null) {
                    Gson gson = new Gson();
                    String strJson = gson.toJson(response.body());
                    Log.d(TAG, "onResponseGetChildFormData: " + strJson);
                    if (response.body().getOutput() != null) {
                        List<AppDetails> getAllAppModelListQ = response.body().getOutput();

                        if (getAllAppModelListQ != null && getAllAppModelListQ.size() > 0) {

                            improveDataBase.insertIntoAppsListTable(getAllAppModelListQ, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());

                            intentData(context, QueryName, getAllAppModelListQ.get(0).getDesignFormat());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetChildFormDataResponse> call, Throwable t) {
                Log.d(TAG, "onResponseGetChildFormException: " + t);
            }
        });

    }

    public void intentData(Context context, String QueryName, String strChildDesignFormat) {

//        Intent intent = new Intent(context, QueryGetDataActivity.class);
        Intent intent = new Intent(context, QueryIndexColumnsGridActivity.class);
        intent.putExtra("s_childForm", "ChildForm");
        intent.putExtra("s_app_name", QueryName);
//        intent.putExtra("s_design_format", strChildDesignFormat);
        ImproveHelper.saveDesignFormat(context,strChildDesignFormat);
//        intent.putExtra("s_user_location_Structure", AppConstants.GlobalObjects.getUser_Location_structure());
        intent.putExtra("s_org_id", sessionManager.getOrgIdFromSession());
        intent.putExtra("s_user_id", sessionManager.getUserDataFromSession().getUserID());
        if (isFromWorkspaceapp) {
            intent.putExtra("from", "Workspace");
        }
        context.startActivity(intent);
    }

    private boolean hasGPS(String strDesignFormat) {

        formControls = DataCollection.getControlList(strDesignFormat);
        boolean hasGPS = false;
        for (Control controlObject : formControls.getMainFormControlsList()) {
            if (controlObject.getControlType() != null && (controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_GPS)
                    || (controlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_CAMERA) || controlObject.isEnableImageWithGps()))) {
                hasGPS = true;
                break;
            }
        }
        return hasGPS;
    }

    public void showProgressDialog(String msg) {
        try {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(context);
                // pd = CustomProgressDialog.ctor(this, msg);
                // pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                progressDialog.setMessage(msg);
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void openActivity(boolean result, int adapterPosition) {
        String designFormat = ImproveHelper.getDesignFormat(context);
        FormLevelTranslation formLevelTranslation = DataCollection.getFormLevelRTL(designFormat);
        Intent intent = null;
        if (result) {
            if (hasGPS(designFormat)) {
                intent = new Intent(context, ViewMapAndDataActivity.class);
            } else {
                intent = new Intent(context, ViewDataActivity.class);
            }
        } else {
            intent = new Intent(context, MainActivity.class);
            intent.putExtra("app_edit", "New");
        }
        intent.putExtra("tableName",  AppsList.get(adapterPosition).getTableName());
        intent.putExtra("subFormDetails",  gson.toJson(AppsList.get(adapterPosition).getSubFormDetails()));
        intent.putExtra("formControls", formControls);
        intent.putExtra("dataManagementOptions", dataManagementOptions);
        intent.putExtra("s_app_mode", AppsList.get(adapterPosition).getAppMode());
        intent.putExtra("s_app_version", AppsList.get(adapterPosition).getAppVersion());
        intent.putExtra("s_app_icon", AppsList.get(adapterPosition).getAppIcon());
        if (AppsList.get(adapterPosition).getDisplayAs().equalsIgnoreCase("Report")) {
            intent.putExtra("s_app_type", "Auto Reports");
        } else {
            intent.putExtra("s_app_type", AppsList.get(adapterPosition).getAppType());
        }
        intent.putExtra("s_org_id", sessionManager.getOrgIdFromSession());
        intent.putExtra("s_app_name", AppsList.get(adapterPosition).getAppName());
        intent.putExtra("s_app_title", formLevelTranslation.getTranslatedAppTitleMap());
        intent.putExtra("s_created_by", AppsList.get(adapterPosition).getCreatedBy());
        intent.putExtra("s_user_id", sessionManager.getUserDataFromSession().getUserID());
        intent.putExtra("s_distribution_id", AppsList.get(adapterPosition).getDistrubutionID());
//      intent.putExtra("s_user_location_Structure", AppConstants.GlobalObjects.getUser_Location_structure());
        intent.putExtra("s_form_translation",AppsList.get(adapterPosition).getFormLevelTranslation());
        intent.putExtra("s_user_post_id", sessionManager.getPostsFromSession());
        if (isFromWorkspaceapp) {
            intent.putExtra("from", "Workspace");
        }
        context.startActivity(intent);
    }

    private void openDetailedView(JSONObject jsonObject, int adapterPosition) {
        try {

            Intent intent = new Intent(context, QueryDetailsActivity.class);

            intent.putExtra("jsonObject", jsonObject.toString());

//            intent.putExtra("dataCollectionObject", dataCollectionObject);
            intent.putExtra("dataManagementOptions", dataManagementOptions);

            intent.putExtra("Trans_id", jsonObject.getString("Trans_ID"));
            intent.putExtra("appName", AppsList.get(adapterPosition).getAppName());
            intent.putExtra("s_childForm", "EditForm");
            intent.putExtra("s_app_version", AppsList.get(adapterPosition).getAppVersion());
            intent.putExtra("s_app_type", AppsList.get(adapterPosition).getAppType());
            intent.putExtra("s_org_id", sessionManager.getOrgIdFromSession());
            intent.putExtra("s_app_name", AppsList.get(adapterPosition).getAppName());
            intent.putExtra("s_app_icon", AppsList.get(adapterPosition).getAppIcon());
            intent.putExtra("s_created_by", AppsList.get(adapterPosition).getCreatedBy());
            intent.putExtra("s_user_id", sessionManager.getUserDataFromSession().getUserID());
            intent.putExtra("s_distribution_id", AppsList.get(adapterPosition).getDistrubutionID());
//            intent.putExtra("s_user_location_Structure", strUserLocationStructure);
            intent.putExtra("s_form_translation",AppsList.get(adapterPosition).getFormLevelTranslation());
            intent.putExtra("s_user_post_id", sessionManager.getPostsFromSession());
            intent.putExtra("app_edit", "Edit");
            intent.putExtra("fromActivity", "ViewData");

            ((Activity) context).startActivityForResult(intent, 0);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "viewRow", e);
        }
    }

   /* private void getOfflineData1(String tableName, AppDetailsAdvancedInput appDetailsAdvancedInput, int adapterPosition) {

        JSONArray jsonArray = new JSONArray();
        String status = "0";
        boolean result = false;
        try {
            List<JSONArray> jsonArrayList = new ArrayList<>();
            JSONArray jsonArrayMain = new JSONArray();
            String trans_id = appDetailsAdvancedInput.getUserId() + "_" + ImproveHelper.replaceWithUnderscore(appDetailsAdvancedInput.getPageName()) + "_Trans_Id";
            String query = "select " + trans_id + ",OrganisationID,FormName,CreatedUserID,SubmittedUserID,DistributionID,IMEI,AppVersion,SyncStatus,offlineJSON from " + tableName + " WHERE OrganisationID ='" + appDetailsAdvancedInput.getOrgId() + "' and  FormName ='" + appDetailsAdvancedInput.getPageName() + "' and SyncStatus = '" + status + "' and UserID = '" + appDetailsAdvancedInput.getUserId() + "'";
            List<GetOfflineData> offlineDataSyncList = improveDataBase.getOfflineDataFromFormTable(query, "");

            for (GetOfflineData offlineDataSync : offlineDataSyncList) {

                JSONObject jsonObject = new JSONObject(offlineDataSync.getOffline_json());

                JSONArray jsonArraySub = new JSONArray(jsonObject.getString("DataFields"));
                final JSONObject object = jsonArraySub.getJSONObject(0);
                object.put("Trans_ID", offlineDataSync.getSno());
                jsonArray = new JSONArray();
                jsonArray.put(object);
                jsonArrayList.add(jsonArray);


            }
            for (JSONArray jsonArray1 : jsonArrayList) {
                jsonArrayMain.put(jsonArray1.get(0));
            }
            Log.d(TAG, "getOfflineData: " + jsonArrayMain);
            if (jsonArray.length() == 0) {
                result = false;
                openActivity(result, adapterPosition);
            } else {
                result = true;
                if (dataCollectionObject.isAllowOnlyOneRecord()) {
                    openDetailedView(jsonArray.getJSONObject(0), adapterPosition);
                } else {
                    openActivity(result, adapterPosition);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getOfflineData", e);
        }

    }*/

    /* private void getOfflineData2(String tableName, AppDetailsAdvancedInput appDetailsAdvancedInput, int adapterPosition) {

         JSONArray jsonArray = new JSONArray();
         String status = "0";
         boolean result = false;
         try {
             List<JSONArray> jsonArrayList = new ArrayList<>();
             JSONArray jsonArrayMain = new JSONArray();
             String trans_id = appDetailsAdvancedInput.getUserId() + "_" + ImproveHelper.replaceWithUnderscore(appDetailsAdvancedInput.getPageName()) + "_Trans_Id";
             String tableColumns = "";
             for (String columnName : dataCollectionObject.getList_Table_Columns()) {
                 tableColumns = tableColumns + columnName + ",";
             }
             tableColumns = tableColumns.substring(0, tableColumns.length() - 1) + " ";
 //            String query = "select "+trans_id+",OrganisationID,FormName,CreatedUserID,SubmittedUserID,DistributionID,IMEI,AppVersion,SyncStatus,offlineJSON from " + tableName + " WHERE OrganisationID ='" + appDetailsAdvancedInput.getOrgId() + "' and  FormName ='" + appDetailsAdvancedInput.getPageName() + "' and SyncStatus = '" + status + "' and UserID = '" + appDetailsAdvancedInput.getUserId() + "'";
             String query = "select " + trans_id + ",OrganisationID,FormName,CreatedUserID,SubmittedUserID,DistributionID,IMEI,AppVersion,SyncStatus,offlineJSON," + tableColumns + " from " + tableName + " WHERE OrganisationID ='" + appDetailsAdvancedInput.getOrgId() + "' and  FormName ='" + appDetailsAdvancedInput.getPageName() + "' and SyncStatus = '" + status + "' and UserID = '" + appDetailsAdvancedInput.getUserId() + "'";
             List<GetOfflineData> offlineDataSyncList = improveDataBase.getOfflineDataFromFormTable(query, tableColumns);

             for (GetOfflineData offlineDataSync : offlineDataSyncList) {

                 JSONObject jsonObject = new JSONObject(offlineDataSync.getOffline_json());

                 JSONArray jsonArraySub = new JSONArray(jsonObject.getString("DataFields"));
                 final JSONObject object = jsonArraySub.getJSONObject(0);
                 object.put("Trans_ID", offlineDataSync.getSno());
                 jsonArray = new JSONArray();
                 jsonArray.put(object);
                 jsonArrayList.add(jsonArray);


             }
             for (JSONArray jsonArray1 : jsonArrayList) {
                 jsonArrayMain.put(jsonArray1.get(0));
             }
             Log.d(TAG, "getOfflineData: " + jsonArrayMain);
             if (jsonArray.length() == 0) {
                 result = false;
                 openActivity(result, adapterPosition);
             } else {
                 result = true;
                 if (dataCollectionObject.isAllowOnlyOneRecord()) {
                     openDetailedView(jsonArray.getJSONObject(0), adapterPosition);
                 } else {
                     openActivity(result, adapterPosition);
                 }
             }
         } catch (Exception e) {
             ImproveHelper.improveException(context, TAG, "getOfflineData", e);
         }

     }*/
    private void getOfflineData(String tableName, AppDetailsAdvancedInput appDetailsAdvancedInput, int adapterPosition) {

        JSONArray jsonArray = new JSONArray();
        String status = "0";
        boolean result = false;
        int type = 3;
        String filters = null;
        try {
            List<JSONArray> jsonArrayList = new ArrayList<>();
            JSONArray jsonArrayMain = new JSONArray();
            jsonArray = new JSONArray();
            String trans_id = appDetailsAdvancedInput.getUserId() + "_" + ImproveHelper.replaceWithUnderscore(appDetailsAdvancedInput.getPageName()) + "_Trans_Id";
//            jsonArray = improveDataBase.getOfflineDataFromFormTableJSON(appDetailsAdvancedInput.getOrgId(), appDetailsAdvancedInput.getPageName(), "0", "2", appDetailsAdvancedInput.getUserId(), tableName, trans_id);
            jsonArray = improveDataBase.getOfflineDataWithFiltersLimit(appDetailsAdvancedInput.getOrgId(), appDetailsAdvancedInput.getPageName(), "0", "2", appDetailsAdvancedInput.getUserId(), appDetailsAdvancedInput.getPostId(), tableName, trans_id, filters, type,2);
            jsonArrayList.add(jsonArray);
            /* for (JSONArray jsonArray1 : jsonArrayList) {
              //  jsonArrayMain.put(jsonArray1.get(0));
            }*/
            Log.d(TAG, "getOfflineData: " + jsonArrayMain);
            if (jsonArray.length() == 0) {
                result = false;
                openActivity(result, adapterPosition);
            } else {
                result = true;
                if (dataManagementOptions.getRecordInsertionType().equalsIgnoreCase("Single")) {
                    openDetailedView(jsonArray.getJSONObject(0), adapterPosition);
                } else {
                    openActivity(result, adapterPosition);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getOfflineData", e);
        }

    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //        TextView name;
        CustomTextView name, desc, tvAppMLType;// init the item view's
        ImageView iv_options, iv_circle;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = itemView.findViewById(R.id.tvAppname);
            desc = itemView.findViewById(R.id.tvAppDesc);
            tvAppMLType = itemView.findViewById(R.id.tvAppMLType);
            iv_options = itemView.findViewById(R.id.iv_options);
            iv_circle = itemView.findViewById(R.id.iv_circle);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                sessionManager = new SessionManager(context);
//            AppConstants.GlobalObjects=new GlobalObjects();
                AppConstants.CurrentAppObject = new DataCollectionObject();
                if (!isMultiClickable) {
                    return;
                }
                PrefManger.putSharedPreferencesString(context, AppConstants.Notification_Back_Press, "0");
//            Log.d("NotificationBPA", PrefManger.getSharedPreferencesString(context, AppConstants.Notification_Back_Press, ""));


//            if (AppsList.get(getAdapterPosition()).getAppType() != null
//                    && (AppsList.get(getAdapterPosition()).getAppType().equalsIgnoreCase("Datacollection") || AppsList.get(getAdapterPosition()).getAppType().equalsIgnoreCase("Auto Reports"))) {
//                Intent intent = new Intent(context, ML_Testing_Activity.class);
//                intent.putExtra("s_app_design","Pen,Pencil,Eraser");
//                intent.putExtra("s_app_name", "Test ML1");
//                context.startActivity(intent);
//            }else
                AppConstants.KEEP_SESSION_VIEW_MAP.clear();
                AppConstants.KEEP_SESSION_LAYOUT_MAP.clear();
                AppConstants.HOME_CLICKED = false;

                //nk realm
                if (RealmDBHelper.existTable(context, RealmTables.SaveOffline.TABLE_NAME)) {
                    List<List<String>> lresult = RealmDBHelper.getDataInRealResults(context, RealmTables.SaveOffline.TABLE_NAME,
                            new String[]{RealmTables.SaveOffline.ActionID,RealmTables.SaveOffline.ActionType},
                            new String[]{RealmTables.SaveOffline.Response}, new String[]{"Online"});
                    if (lresult != null) {
                        for (int i = 0; i < lresult.size(); i++) {
                            List<String> result_=lresult.get(i);
                            if(result_.get(1).equals("callApi")){
                                RealmDBHelper.removeAPIMappingTableBasedOnActionID(context, result_.get(0));
                                List<String> tableNames=RealmDBHelper.getTableNames(context,result_.get(0));
                                RealmDBHelper.deleteTables(context, tableNames);
                            }else {
                                RealmDBHelper.deleteTable(context, result_.get(0));
                            }
                        }
                    }
                }
                if (AppConstants.GlobalObjects != null) {
                    //AppConstants.GlobalObjects.getForms_ListHash().clear();
                    //AppConstants.GlobalObjects.getAPIs_ListHash().clear();
                    //AppConstants.GlobalObjects.getQuerys_ListHash().clear();
                    //AppConstants.GlobalObjects.getAllQueriesList_Hash().clear();
                    //AppConstants.GlobalObjects.getAllFormsList_Hash().clear();
                    //AppConstants.GlobalObjects.getSubControls_List().clear();

            }
            SubformView.sfFileString.clear();
            SubformView.sfString.clear();
            GridControl.sfString.clear();
            GridControl.sfFileString.clear();
            CurrentAppObject = new DataCollectionObject();
            Current_onChangeEventObject = new Control_EventObject();
            AppConstants.Current_onFocusEventObject = new Control_EventObject();
            AppConstants.Current_onSubmitClickObject = new Control_EventObject();
            AppConstants.Current_onLoadEventObject = new Control_EventObject();
            AppConstants.Global_Controls_Variables.clear();
            AppConstants.Global_Single_Forms.clear();
            AppConstants.Global_Layouts.clear();
            AppConstants.Global_Variable_Beans.clear();
            AppConstants.LAYOUT_KEEP_SESSION.clear();
            AppConstants.KEEP_SESSION_OBJECT.clear();
            AppConstants.uiLayoutPropertiesStatic = new UILayoutProperties();
            try {
                JSONObject jobj = new JSONObject();
                jobj.put("AppName", AppsList.get(getAdapterPosition()).getAppName());
                jobj.put("AppType", AppsList.get(getAdapterPosition()).getAppType());
                ImproveHelper.Controlflow("AppClick", "AppsList", "AppsList", jobj.toString());
            } catch (JSONException e) {
                ImproveHelper.improveException(context, TAG, "AppClick", e);
            }
            ImproveHelper.saveDesignFormat(context,improveDataBase.getDesignFormat(AppsList.get(getAdapterPosition()).getAppName()));
            if (AppsList.get(getAdapterPosition()).getAppType().equalsIgnoreCase(AppConstants.ML)) {
                if (AppsList.get(getAdapterPosition()).getTraining() != null && AppsList.get(getAdapterPosition()).getTraining().equalsIgnoreCase("true")) {
                    Intent intent = new Intent(context, ML_Training_Activity.class);
                    intent.putExtra("s_app_name", AppsList.get(getAdapterPosition()).getAppName());
                    intent.putExtra("s_user_id", sessionManager.getUserDataFromSession().getUserID());
                    intent.putExtra("AppIcon", AppsList.get(getAdapterPosition()).getAppIcon());
                    if (isFromWorkspaceapp) {
                        intent.putExtra("from", "Workspace");
                    }

                    context.startActivity(intent);
                } else if (AppsList.get(getAdapterPosition()).getTesting() != null && AppsList.get(getAdapterPosition()).getTesting().equalsIgnoreCase("true")) {
                    Intent intent = new Intent(context, ML_Testing_Activity.class);
                    intent.putExtra("s_app_name", AppsList.get(getAdapterPosition()).getAppName());
                    intent.putExtra("s_user_id", sessionManager.getUserDataFromSession().getUserID());
                    intent.putExtra("AppIcon", AppsList.get(getAdapterPosition()).getAppIcon());
                    if (isFromWorkspaceapp) {
                        intent.putExtra("from", "Workspace");
                    }
                    context.startActivity(intent);
                }
            } else if (AppsList.get(getAdapterPosition()).getAppType() != null
                    && (AppsList.get(getAdapterPosition()).getAppType().equalsIgnoreCase("Datacollection")
                    || AppsList.get(getAdapterPosition()).getAppType().equalsIgnoreCase("Auto Reports"))) {

                AppConstants.IS_MULTI_FORM = false;
                currentMultiForm = null;
                AppDetails appDetails = AppsList.get(getAdapterPosition());
                //ImproveHelper.saveDesignFormat(context,improveDataBase.getDesignFormat(appDetails.getAppName()));
                dataManagementOptions = DataCollection.getAdvanceManagement(ImproveHelper.getDesignFormat(context));

                if (appDetails.getAppMode() != null && appDetails.getAppMode().equalsIgnoreCase("Online")) {
                    startMoveToSingleFormScreen(appDetails);
                } else {
                    if(improveDataBase.checkTableWithRecords(appDetails)){
                        //pending for subform
                        boolean keysSame = true;// improveHelper.checkPrimaryKeysAsPerDataCollectionObj(appDetails, improveDataBase);
                        //boolean keysSame=true;
                        if (isNetworkStatusAvialable(context)) {
                            if (appDetails.getAppMode() != null && appDetails.getAppMode().equalsIgnoreCase("Hybrid")) {
                                SyncData syncData = new SyncData(context, appDetails.getAppName(), 1, new SyncData.SyncDataListener() {
                                    @Override
                                    public void onSuccess(String msg) {
                                        startMoveToSingleFormScreen(appDetails);
                                        //checkTableConstraints(keysSame, appDetails);
                                    }
                                    @Override
                                    public void onFailed(String errorMessage) {
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ImproveHelper.confirmDialog(context, "Sync Failed!", errorMessage + "\n\nDo You Want to Continue Process?", "Continue", "Cancel", new Helper.IL() {
                                                    @Override
                                                    public void onSuccess() {
                                                        startMoveToSingleFormScreen(appDetails);
                                                        //checkTableConstraints(keysSame, appDetails);
                                                    }

                                                    @Override
                                                    public void onCancel() {

                                                    }
                                                });
                                            }
                                        });

                                        // ImproveHelper.my_showAlert(context, "Sync Failed!", errorMessage, "2");
                                    }
                                });
                            } else {
                                startMoveToSingleFormScreen(appDetails);
                               // checkTableConstraints(keysSame, appDetails);
                            }
                        } else {
                            startMoveToSingleFormScreen(appDetails);
                           // checkTableConstraints(keysSame, appDetails);
                        }
                    }else{
                        startMoveToSingleFormScreen(appDetails);
                       // checkTableConstraints(true, appDetails);
                    }
                }
//--check form has data--//


                } else if (AppsList.get(getAdapterPosition()).getAppType() != null
                        && (AppsList.get(getAdapterPosition()).getAppType().equalsIgnoreCase("QueryForm")||AppsList.get(getAdapterPosition()).getAppType().equalsIgnoreCase("Query"))) {

//                Intent intent = new Intent(context, QueryGetDataActivity.class);
                Intent intent = new Intent(context, QueryIndexColumnsGridActivity.class);
                intent.putExtra("s_design_format", ImproveHelper.getDesignFormat(context));
                intent.putExtra("s_app_name", AppsList.get(getAdapterPosition()).getAppName());
//                intent.putExtra("s_user_location_Structure", AppConstants.GlobalObjects.getUser_Location_structure());
                    intent.putExtra("s_org_id", sessionManager.getOrgIdFromSession());
                    if (isFromWorkspaceapp) {
                        intent.putExtra("from", "Workspace");
                    }
                    context.startActivity(intent);

                } else if (AppsList.get(getAdapterPosition()).getAppType() != null
                        && (AppsList.get(getAdapterPosition()).getAppType().equalsIgnoreCase("CHILD") ||
                        AppsList.get(getAdapterPosition()).getAppType().equalsIgnoreCase("ChildForm"))) {
//            } else if ((AppsList.get(getAdapterPosition()).getNewRow().equalsIgnoreCase("CHILD"))) {
                    if (isNetworkStatusAvialable(context)) {
                        String strChildCreatedBy = AppsList.get(getAdapterPosition()).getCreatedBy();
                        String strChildDistributionId = AppsList.get(getAdapterPosition()).getDistrubutionID();
                        String strChildAppName = AppsList.get(getAdapterPosition()).getAppName();

                        PrefManger.putSharedPreferencesString(context, AppConstants.SP_CHILD_FORM_APP_NAME, strChildAppName);
                        PrefManger.putSharedPreferencesString(context, AppConstants.SP_CHILD_FORM_CREATED_BY_ID, strChildCreatedBy);
                        PrefManger.putSharedPreferencesString(context, AppConstants.SP_CHILD_FORM_DISTRIBUTION_ID, strChildDistributionId);

                    sessionManager.createChildFormDesignFormat(improveHelper.getDesignFormat(context));

                    prepareChildFormJsonData(context, getAdapterPosition(), improveHelper.getDesignFormat(context));

                    } else {
                        showToast(context, context.getString(R.string.no_internet));
                    }
                } else if (AppsList.get(getAdapterPosition()).getAppType().equalsIgnoreCase(AppConstants.DASHBOARD)) {
                    Log.d(TAG, "AppsAdapterOnClickAppType: " + AppsList.get(getAdapterPosition()).getAppType());
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra(WEB_VIEW, AppConstants.DASHBOARD);
                    intent.putExtra(WEB_VIEW_DATA, AppConstants.MAIN_WEB_LINK + AppsList.get(getAdapterPosition()).getAppName().replaceAll(" ", "%20")
                            + "&OrgID=" + sessionManager.getOrgIdFromSession() + "&UserID=" + sessionManager.getUserDataFromSession().getUserID());
                    Log.d(TAG, "onClickAdapterD: " + AppConstants.MAIN_WEB_LINK + AppsList.get(getAdapterPosition()).getAppName()
                            + "&OrgID=" + sessionManager.getOrgIdFromSession() + "&UserID=" + sessionManager.getUserDataFromSession().getUserID());
                    if (isFromWorkspaceapp) {
                        intent.putExtra("from", "Workspace");
                    }
                    context.startActivity(intent);
                } else if (AppsList.get(getAdapterPosition()).getAppType().equalsIgnoreCase(AppConstants.REPORTS)) {
                    Log.d(TAG, "AppsAdapterOnClickAppType: " + AppsList.get(getAdapterPosition()).getAppType());
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra(WEB_VIEW, AppConstants.REPORTS);
//                intent.putExtra(WEB_VIEW_DATA, "https://www.google.com/");
                intent.putExtra(WEB_VIEW_DATA, AppConstants.MAIN_WEB_LINK + AppsList.get(getAdapterPosition()).getAppName()
                        + "&OrgID=" + sessionManager.getOrgIdFromSession() + "&UserID=" + sessionManager.getUserDataFromSession().getUserID());
                Log.d(TAG, "onClickAdapterR: " + AppConstants.MAIN_WEB_LINK + AppsList.get(getAdapterPosition()).getAppName()
                        + "&OrgID=" + sessionManager.getOrgIdFromSession() + "&UserID=" + sessionManager.getUserDataFromSession().getUserID());
                if (isFromWorkspaceapp) {
                    intent.putExtra("from", "Workspace");
                }
                context.startActivity(intent);
            } else if (AppsList.get(getAdapterPosition()).getAppType().equalsIgnoreCase(AppConstants.WEB_Link)) {
                Intent intent = new Intent(context, WebLinkViewActivity.class);
                intent.putExtra("s_app_name", AppsList.get(getAdapterPosition()).getAppName());
                if (isFromWorkspaceapp) {
                    intent.putExtra("from", "Workspace");
                }
                context.startActivity(intent);
            } else if (AppsList.get(getAdapterPosition()).getAppType() != null
                    && AppsList.get(getAdapterPosition()).getAppType().equalsIgnoreCase(MULTI_FORM)) {
                AppConstants.IS_MULTI_FORM = true;
                AppConstants.BOTTOM_NAV_POS = -1;
                MultiFormApp multiFormApp = new XMLHelper().XML_MultiFormApp(ImproveHelper.getDesignFormat(context));
                multiFormApp.setAppVersion(AppsList.get(getAdapterPosition()).getAppVersion());
                multiFormApp.setCreateBy(AppsList.get(getAdapterPosition()).getCreatedBy());
                multiFormApp.setDistributionId(AppsList.get(getAdapterPosition()).getDistrubutionID());
                multiFormApp.setPostId(AppsList.get(getAdapterPosition()).getPostID());

                    AppConstants.currentMultiForm = multiFormApp;
                    Log.d("multiformapp", AppsList.get(getAdapterPosition()).getAppType());
                    LinkedHashMap<String, String> innerFormsDesignMap = multiFormApp.getInnerFormsDesignMap();


                    if (multiFormApp.getHome() != null && innerFormsDesignMap != null) {
                        for (LinkedHashMap.Entry<String, String> entry : innerFormsDesignMap.entrySet()) {

                        if (entry.getKey().contentEquals(multiFormApp.getHome())) {
                            currentMultiForm.getNavigationList().add(new FormNavigation(currentMultiForm.getHome(), true), currentMultiForm.getHome());
                            AppsList.get(getAdapterPosition()).setAppName(entry.getKey());
                            navigateToSingleForm(AppsList.get(getAdapterPosition()));
                            break;
                        }

                        }
                        String homeIn = multiFormApp.getHomeIn();
                        int pos = multiFormApp.getHomeMenuPos();
                        if (homeIn != null && (homeIn.contentEquals(AppConstants.BOTTOM_NAVIGATION) || homeIn.contentEquals(AppConstants.NAVIGATION_MENU)) && pos != -1) {
                            String formName = multiFormApp.getHome();
                            getDesignAndNavigate(formName);
                        }
                    } else {
                        String homeIn = multiFormApp.getHomeIn();
                        int pos = multiFormApp.getHomeMenuPos();
                        if (homeIn != null && homeIn.contentEquals(AppConstants.BOTTOM_NAVIGATION) && pos != -1) {
                            String formName = multiFormApp.getHome();
                            getDesignAndNavigate(formName);
                        }
                    }

                } else if (AppsList.get(getAdapterPosition()).getAppType().equalsIgnoreCase(AppConstants.WORKSPACE)) {

                    Intent intent = new Intent(context, SubAppsListActivity.class);
                    intent.putExtra("WorkspaceName", AppsList.get(getAdapterPosition()).getAppName());
                    intent.putExtra("AppIcon", AppsList.get(getAdapterPosition()).getAppIcon());
                    context.startActivity(intent);

                } else {
                    Log.d(TAG, "AppsAdapterOnClickAppType: " + AppsList.get(getAdapterPosition()).getAppType());
                }
            }catch(Exception e){
                e.printStackTrace();
            }

//            if (clickListener != null)
//                clickListener.onCustomClick(context,v, getAdapterPosition(), null);

        }

        private void checkTableConstraints(boolean keysSame, AppDetails appDetails) {
            if (keysSame) {
                //improveDataBase.createTablesBasedOnConditions(appDetails);
                startMoveToSingleFormScreen(appDetails);
            } else {
                //keys not same
                //1. check data exists in table
                //2. if no drop table and re-create table
                //3. if yes copy that table, create new table and dump that old data
                if (improveDataBase.getCount(appDetails.getTableName()) == 0) {
                    //2. if no drop table and re-create table
                    improveDataBase.dropAndCreateTablesBasedOnConditions(appDetails, false);
                    startMoveToSingleFormScreen(appDetails);
                } else {
                    try {
                        improveDataBase.dropAndCreateTablesBasedOnConditions(appDetails, true);
                        startMoveToSingleFormScreen(appDetails);
                    } catch (Exception e) {
                        improveDataBase.getDataByQuery(improveDataBase.getDropTableQuery(appDetails.getTableName()));
                        for (int i = 0; i < appDetails.getSubFormDetails().size(); i++) {
                            improveDataBase.getDataByQuery(improveDataBase.getDropTableQuery(appDetails.getSubFormDetails().get(0).getTableName()));
                        }
                        ImproveHelper.confirmDialog(context, "Table Re-Creation/Sync Failed!", e.getMessage() + "\n Please Delete That Records To Process Next", "Ok", "", new Helper.IL() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                    }
                }
            }
        }


        private void startMoveToSingleFormScreen(AppDetails appDetails) {

            Intent intent = null;
            if (dataManagementOptions != null && (dataManagementOptions.isEnableViewData() || dataManagementOptions.isEnableEditData() || dataManagementOptions.isEnableDeleteData())) {
//--check form has data--//
                boolean result = false;
                AppDetailsAdvancedInput appDetailsAdvancedInput = new AppDetailsAdvancedInput();
                appDetailsAdvancedInput.setOrgId(sessionManager.getOrgIdFromSession());
                appDetailsAdvancedInput.setPageName(appDetails.getAppName());
                appDetailsAdvancedInput.setUserId(sessionManager.getUserDataFromSession().getUserID());
                appDetailsAdvancedInput.setPostId(sessionManager.getPostsFromSession());
                appDetailsAdvancedInput.setSubmittedUserPostID(sessionManager.getPostsFromSession());
                if (dataManagementOptions.getFetchData() != null) {
                    appDetailsAdvancedInput.setFetchData(dataManagementOptions.getFetchData());
                } else {
                    appDetailsAdvancedInput.setFetchData("Login User Post");
                }
                appDetailsAdvancedInput.setOrderbyStatus("False");
                appDetailsAdvancedInput.setOrderByColumns("");
                appDetailsAdvancedInput.setOrderByType("");
                appDetailsAdvancedInput.setLazyLoading("True");
                appDetailsAdvancedInput.setThreshold("2");
                appDetailsAdvancedInput.setRange("1" + "-" + "2");
                appDetailsAdvancedInput.setFilterColumns(new ArrayList<>());
                //TODO  --- In future need to set order by columns in the below line
                appDetailsAdvancedInput.setLazyOrderKey("SELECT NULL");
                if (isNetworkStatusAvialable(context) && (appDetails.getAppMode() != null && appDetails.getAppMode().equalsIgnoreCase("Online"))) {
                    CheckFormHasData getRequest = new CheckFormHasData(appDetailsAdvancedInput, getAdapterPosition());
                    getRequest.execute();

                } else {
                    String tableName = appDetails.getCreatedBy() + "_" + ImproveHelper.getTableNameWithOutSpace(appDetails.getAppName());
                    getOfflineData(tableName, appDetailsAdvancedInput, getAdapterPosition());

                }

            } else {
                System.out.println("========movetonextactivity");
                intent = new Intent(context, MainActivity.class);
                intent.putExtra("app_edit", "New");
                intent.putExtra("dataManagementOptions", dataManagementOptions);
                intent.putExtra("tableName",  appDetails.getTableName());
                intent.putExtra("subFormDetails",  gson.toJson(appDetails.getSubFormDetails()));
                intent.putExtra("s_app_icon", appDetails.getAppIcon());
                intent.putExtra("s_app_version", appDetails.getAppVersion());
                if (appDetails.getDisplayAs().equalsIgnoreCase("Report")) {
                    intent.putExtra("s_app_type", "Auto Reports");
                } else {

                    intent.putExtra("s_app_type", appDetails.getAppType());
                }
                intent.putExtra("s_org_id", sessionManager.getOrgIdFromSession());
                intent.putExtra("s_app_name", appDetails.getAppName());
                intent.putExtra("s_created_by", appDetails.getCreatedBy());
                intent.putExtra("s_user_id", sessionManager.getUserDataFromSession().getUserID());
                intent.putExtra("s_distribution_id", appDetails.getDistrubutionID());
//                intent.putExtra("s_user_location_Structure", AppConstants.GlobalObjects.getUser_Location_structure());
                intent.putExtra("s_user_post_id",sessionManager.getPostsFromSession());
                if (isFromWorkspaceapp) {
                    intent.putExtra("from", "Workspace");
                }
                context.startActivity(intent);
            }

        }


        private void navigateToSingleForm( AppDetails appDetails) {


//            Intent intent = new Intent(context, ViewDataActivity.class);
            Intent intent;
          /*  if (hasGPS(dataCollectionObject)) {
                intent = new Intent(context, ViewDataWithMapActivity.class);
            } else {
                intent = new Intent(context, ViewDataActivity.class);
            }*/
            intent = new Intent(context, MainActivity.class);
//            intent.putExtra("s_app_design", designFormat);
            intent.putExtra("s_app_mode", appDetails.getAppMode());
            intent.putExtra("s_app_version", appDetails.getAppVersion());
            intent.putExtra("s_app_type", "Datacollection");
            intent.putExtra("s_org_id", sessionManager.getOrgIdFromSession());
            intent.putExtra("s_app_name", appDetails.getAppName());
            intent.putExtra("s_app_icon", appDetails.getAppIcon());
            intent.putExtra("s_created_by", appDetails.getCreatedBy());
            intent.putExtra("s_user_id", sessionManager.getUserDataFromSession().getUserID());
            intent.putExtra("s_distribution_id", appDetails.getDistrubutionID());
            intent.putExtra("s_user_post_id", sessionManager.getPostsFromSession());
            if (isFromWorkspaceapp) {
                intent.putExtra("from", "Workspace");
            }
            context.startActivity(intent);

        }

        private void getDesignAndNavigate(String pageName) {
            try {
                ImproveDataBase improveDataBase = new ImproveDataBase(context);
                Map<String, String> data = new HashMap<>();
                try {
                    data.put("PageName", pageName);
                    data.put("OrgId", sessionManager.getOrgIdFromSession());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                GetServices getServices = RetrofitUtils.getUserService();

                Call<CallFormDataResponse> call = getServices.getDesignfromCallform(sessionManager.getAuthorizationTokenId(),data);
                call.enqueue(new Callback<CallFormDataResponse>() {
                    @Override
                    public void onResponse(Call<CallFormDataResponse> call, Response<CallFormDataResponse> response) {

                        if (response.body() != null && response.body().getStatus().contentEquals("200")) {
                            if (response.body().getAppDetails() != null && response.body().getAppDetails().size() > 0) {
                                AppDetails otherFormDetails = response.body().getAppDetails().get(0);
                                List<AppDetails> appsList = new ArrayList<AppDetails>();
                                otherFormDetails.setVisibileIn("Both");
                                appsList.add(otherFormDetails);
                                improveDataBase.insertIntoAppsListTable(appsList, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
                                ImproveHelper.saveDesignFormat(context,otherFormDetails.getDesignFormat());
                          /*      AppDetails appDetails = new AppDetails();
                                appDetails.setAppVersion(otherFormDetails.getAppVersion());

                                appDetails.setCreatedBy(otherFormDetails.getCreatedBy());
                                appDetails.setPostID(otherFormDetails.getPostID());
                                appDetails.setDistrubutionID(otherFormDetails.getDistrubutionID());*/

                                navigateToSingleForm(otherFormDetails);


                          /*   navigationModel.setAppVersion(otherFormDetails.getAppVersion());
                            navigationModel.setAppVersion(otherFormDetails.getAppType());
                            navigationModel.setCreatedBy(otherFormDetails.getCreatedBy());
                            navigationModel.setDistributionId(otherFormDetails.getDistrubutionID());
                            navigationModel.setPostId(otherFormDetails.getPostID());
                            if (navigationModel.getFormType().contentEquals("REPORT_FORMP")) {
                                navigationModel.setFormType("REPORT_FORMP");
                            } else if (navigationModel.getFormType().contentEquals("WORK_FLOW_FORM")) {
                                navigationModel.setFormType("WORK_FLOW_FORM");

                            }

                            if (!navigationModel.getFormType().contentEquals("WORK_FLOW_FORM")) {
                                navigateToNext();
                            } else {
                                queryName = XMLHelper.getBaseQueryName(navigationModel.getDesignFormat());
                                getDesignAndNavigate(queryName);
                            }*/

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<CallFormDataResponse> call, Throwable t) {
                        Log.d("CallFormAction", "Callform faild responce: " + t);
                    }
                });

            } catch (Exception e) {
                ImproveHelper.improveException(context, TAG, "getDesignAndNavigate", e);
            }
        }
    }

    /*  private void getOfflineData(String tableName,AppDetailsAdvancedInput appDetailsAdvancedInput ,int adapterPosition) {

          JSONArray jsonArray=new JSONArray();
         String  offlinestatus = "0";
         String  onlinestatus = "2";
          boolean result = false;
          try {
              List<JSONArray> jsonArrayList = new ArrayList<>();
              JSONArray   jsonArrayMain = new JSONArray();
              jsonArray = new JSONArray();
              String trans_id = appDetailsAdvancedInput.getUserId() + "_" + improveHelper.replaceWithUnderscore(appDetailsAdvancedInput.getPageName()) + "_Trans_Id";
              jsonArray = improveDataBase.getOfflineDataFromFormTableJSON(appDetailsAdvancedInput.getOrgId(), appDetailsAdvancedInput.getPageName(), offlinestatus,onlinestatus, appDetailsAdvancedInput.getUserId(),tableName,trans_id);
              jsonArrayList.add(jsonArray);
             *//* for (JSONArray jsonArray1 : jsonArrayList) {
              //  jsonArrayMain.put(jsonArray1.get(0));
            }*//*
            Log.d(TAG, "getOfflineData: " + jsonArrayMain.toString());
            if (jsonArray.length() == 0) {
                result = false;
                openActivity(result, adapterPosition);
            } else {
                result = true;
                if (dataCollectionObject.isAllowOnlyOneRecord()) {
                    openDetailedView(jsonArray.getJSONObject(0), adapterPosition);
                } else {
                    openActivity(result, adapterPosition);
                }
            }
        }  catch (Exception e) {
        improveHelper.improveException(context, TAG, "getOfflineData", e);
    }

    }
*/
    class CheckFormHasData extends AsyncTask<String, Void, Boolean> {
        int adapterPosition = 0;
        AppDetailsAdvancedInput getAllAppNamesData;
        Boolean result = false;

        public CheckFormHasData(AppDetailsAdvancedInput getAllAppNamesData, int adapterPosition) {
            this.getAllAppNamesData = getAllAppNamesData;
            this.adapterPosition = adapterPosition;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            Log.d(TAG, "doInBackground: "+"test");
            if (dataManagementOptions.isLazyLoadingEnabled() && getAllAppNamesData.getLazyOrderKey().contentEquals("")) {
                getAllAppNamesData.setLazyOrderKey("SELECT NULL");
            }
            showProgressDialog(context.getString(R.string.please_wait));
//            Call<ResponseBody> getAllAppNamesDataCall = getServices.iGetAppDataOnline(getAllAppNamesData);
            Call<ResponseBody> getAllAppNamesDataCall = getServices.iGetAppDataOffline(sessionManager.getAuthorizationTokenId(),getAllAppNamesData);
            getAllAppNamesDataCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    dismissProgressDialog();
                    String json = null;
                    try {
                        json = response.body().string();
                        Log.d(TAG, "appdata: " + json);
                        JSONObject responseObj = new JSONObject(json);
                        if (responseObj.getString("Status").contentEquals("200") && !responseObj.getString("Message").equalsIgnoreCase("No Data Present Now")) {

                            JSONArray jsonArray = new JSONArray((responseObj.getString("Data")));

                            if (jsonArray.length() == 0) {
                                result = false;
                                openActivity(result, adapterPosition);
                            } else {
                                result = true;
                                if (dataManagementOptions.getRecordInsertionType().equalsIgnoreCase("Single")) {
                                    openDetailedView(jsonArray.getJSONObject(0), adapterPosition);
                                } else {
                                    openActivity(result, adapterPosition);
                                }
                            }

                        } else {
                            result = false;
                            openActivity(result, adapterPosition);
                        }

                    } catch (Exception e) {
                        result = false;
                        openActivity(result, adapterPosition);
                    }

                    Log.d(TAG, "onResponse: " + json);
                }


                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    dismissProgressDialog();
                }
            });

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

        }
    }


}
