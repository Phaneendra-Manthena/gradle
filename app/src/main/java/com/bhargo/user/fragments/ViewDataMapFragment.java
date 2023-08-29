package com.bhargo.user.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.Java_Beans.DataManagementOptions;
import com.bhargo.user.MainActivity;
import com.bhargo.user.Query.QueryDetailsActivity;
import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.interfaces.ItemClickListenerAdvanced;
import com.bhargo.user.pojos.AppDetails;
import com.bhargo.user.pojos.AppDetailsAdvancedAction;
import com.bhargo.user.pojos.Control;
import com.bhargo.user.pojos.DetailedPageData;
import com.bhargo.user.pojos.DeviceIdResponse;
import com.bhargo.user.pojos.FormControls;
import com.bhargo.user.pojos.GetAllAppModel;
import com.bhargo.user.pojos.SubFormTableColumns;
import com.bhargo.user.screens.DetailedPageActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseFragment;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewDataMapFragment extends BaseFragment implements ItemClickListenerAdvanced, View.OnClickListener, OnMapReadyCallback, GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener {

    private static final String TAG = "ViewDataMapFragment";
    private static final int BACK_TO_LIST_FLAG = 2002;
    public GoogleMap mGoogleMap;
    JSONArray jsonArray;
    GetServices getServices;
    Context context;
    RelativeLayout rl_AppsListMain;
    FloatingActionButton fb_add_row;
    CustomTextView ct_alNoRecords;
    ImproveHelper improveHelper;
    SessionManager sessionManager;
    ImproveDataBase improveDataBase;
    DataCollectionObject dataCollectionObject;
    JSONArray jsonArrayMain;
    List<JSONArray> jsonArrayList;
    List<String> List_Columns;
    List<Control> List_ControlTypes;
    boolean image = false;
    String status = "online";
    private View rootView;
    private ViewGroup viewGroup;
    private GetAllAppModel getAllAppModel;
    private String strPostId, strAppDesign, strAppVersion, strOrgId, strAppType, strAppName,strDisplayAppName, strCreatedBy, strUserId, strDistributionId, strUserLocationStructure, strFrom_InTaskDetails;
    private boolean isResume;
    private String callerFormName;
    private String callerFormType;
    private AppDetails appDetailsList;
    private Intent getIntent;
    DataManagementOptions dataManagementOptions;
    FormControls formControls;
    String tableName;
    String appMode;
    List<SubFormTableColumns> subFormDetails;

    public ViewDataMapFragment() {

    }

    public ViewDataMapFragment(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_view_map, container, false);
        viewGroup = container;
        improveHelper = new ImproveHelper(getActivity());

        initViews();
        return rootView;

    }


    private void initViews() {
        try {
            context = getActivity();
            improveDataBase = new ImproveDataBase(context);
            getIntent = getActivity().getIntent();
            if (getIntent != null) {

                if (getIntent.hasExtra("s_resume")) {
                    isResume = getIntent.getBooleanExtra("s_resume", false);
                    callerFormName = getIntent.getStringExtra("caller_form_name");
                    callerFormType = getIntent.getStringExtra("form_type");
                }
//                dataCollectionObject = (DataCollectionObject) getIntent.getSerializableExtra("dataCollectionObject");
                dataManagementOptions = (DataManagementOptions) getIntent.getSerializableExtra("dataManagementOptions");
                formControls = (FormControls) getIntent.getSerializableExtra("formControls");
                appMode = getIntent.getStringExtra("s_app_mode");

//                strAppDesign = getIntent.getStringExtra("s_app_design");
                strAppVersion = getIntent.getStringExtra("s_app_version");
                strOrgId = getIntent.getStringExtra("s_org_id");
                strAppType = getIntent.getStringExtra("s_app_type");
                strAppName = getIntent.getStringExtra("s_app_name");
                strDisplayAppName = getIntent.getStringExtra("s_display_app_name");
                strCreatedBy = getIntent.getStringExtra("s_created_by");
                strUserId = getIntent.getStringExtra("s_user_id");
                strDistributionId = getIntent.getStringExtra("s_distribution_id");
                strUserLocationStructure = getIntent.getStringExtra("s_user_location_Structure");
                strFrom_InTaskDetails = getIntent.getStringExtra("From_InTaskDetails");
                strPostId = getIntent.getStringExtra("s_user_post_id");
                tableName =  getIntent.getStringExtra("tableName");
                String subFormData =  getIntent.getStringExtra("subFormDetails");
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<SubFormTableColumns>>(){}.getType();
                subFormDetails = gson.fromJson(subFormData, listType);
            }


            sessionManager = new SessionManager(context);
            getServices = RetrofitUtils.getUserService();

            ct_alNoRecords = rootView.findViewById(R.id.ct_alNoRecords);

            rl_AppsListMain = rootView.findViewById(R.id.rl_AppsListMain);

            fb_add_row = rootView.findViewById(R.id.fb_add_row);

            fb_add_row.setOnClickListener(this);
            appDetailsList = improveDataBase.getAppDetails(strOrgId, strAppName, sessionManager.getUserDataFromSession().getUserID());


            if (dataManagementOptions.isEnableViewData() || dataManagementOptions.isEnableEditData() || dataManagementOptions.isEnableDeleteData()) {
                if(dataManagementOptions.getRecordInsertionType().equalsIgnoreCase("None")){
                    fb_add_row.setVisibility(View.GONE);
                }
                List_Columns = new ArrayList<>();
                List_ControlTypes = new ArrayList<>();
                List_Columns = dataManagementOptions.getList_Table_Columns();
                List_ControlTypes = formControls.getMainFormControlsList();
               /* if (dataManagementOptions.getCaptionForAdd() != null && !dataManagementOptions.getCaptionForAdd().isEmpty()) {
                    cb_add_row.setText(dataManagementOptions.getCaptionForAdd());
                }*/
              /*  if (List_ControlTypes.contains(AppConstants.CONTROL_TYPE_CAMERA) || List_ControlTypes.contains(AppConstants.CONTROL_TYPE_FILE_BROWSING)) {
                    image = true;
                }*/
            }
            getAppData();
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "initViews", e);
        }

    }

    public void getAppData() {
        try {
            if (jsonArray.length() == 0) {
                ct_alNoRecords.setVisibility(View.VISIBLE);

            } else {
                ct_alNoRecords.setVisibility(View.GONE);
                Log.d(TAG, "getAppData: " + "test");
                showOnMap();
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "getAppData", e);
        }
    }

    private void showOnMap() {
        try {
            for (int position = 0; position < jsonArray.length(); position++) {

                JSONObject object = jsonArray.getJSONObject(position);

                List<String> points = new ArrayList<>();
                String GPS_type = null;
                for (int i = 0; i < object.names().length(); i++) {
//                    String controlName = getControlNameForGPS(object.names().getString(i));
                    String controlName = object.names().getString(i);
                    String controlType = getControlType(controlName);
                    if (controlType != null && List_Columns.contains(controlName) && ((controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) || (controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_MAP)))) {
                        String latLong = object.getString(object.names().getString(i));
                        GPS_type = object.getString(object.names().getString(i + 1));
                        Log.d(TAG, "showOnMap: " + latLong);
                        points.add(latLong);
                        //For Polygon Test
//                        points.add("18.11103$83.41528^18.11092$83.41651^18.11057$83.41831^18.10999$83.41901^18.10900$83.41868^18.10811$83.41847^18.10834$83.41658^18.10882$83.41489^18.10993$83.41499");
                        //For Multipoint Test
                       /* points.add("18.11093$83.41675");
                        points.add("18.11113$83.41311");
                        points.add("18.10880$83.42057");*/
//For Two Points line Test
                        //points.add("18.11103$83.41528^18.11092$83.41651");
                        //For Multi Points line Test
//                        points.add("18.11103$83.41528^18.11092$83.41651^18.11057$83.41831^18.10999$83.41901");
                        // For Four Points Square Test
//                        points.add("18.11108$83.41550^18.11114$83.41866^18.10813$83.41861^18.10895$83.41472^18.11108$83.41550");
//                        points.add("18.11108$83.41550^18.11114$83.41866^18.10813$83.41861^18.10895$83.41472");
                    }

                }
if(GPS_type!=null && points.size()>0){
                setMapPointsDynamically(GPS_type, points);}
            }
        } catch (Exception e) {
            Log.d(TAG, "showOnMap: " + e.toString());
            improveHelper.improveException(getActivity(), TAG, "getAppData", e);
        }

    }

    private String getControlNameForGPS(String stringControlName) {
        String controlName = stringControlName;
        try {
            if (controlName.contains("_Coordinates")) {
                controlName = controlName.replace("_Coordinates", "");
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "getControlNameForGPS", e);
        }
        return controlName;
    }


    @Override
    public void onCustomClick(Context context, View view, int position, String typeOfAction, String status) {
        JSONObject object;
        try {
            if (status.equalsIgnoreCase("offline")) {
                object = jsonArrayMain.getJSONObject(position);
            } else {
                object = jsonArray.getJSONObject(position);
            }


            if (typeOfAction.equalsIgnoreCase("View")) {

                viewRow(object);

            } else if (typeOfAction.equalsIgnoreCase("Edit")) {

                editRow(object, status);

            } else if (typeOfAction.equalsIgnoreCase("Delete")) {


                deleteAlert(context, position, object.getString("Trans_ID"), status);
            }
            /*else {
                viewRow(object, status);
            }*/
        } catch (Exception e) {
            Log.d(TAG, "onCustomClick: " + e.toString());
        }
    }
    private void viewRow(JSONObject jsonObject) {
        try {
            if(dataManagementOptions.getDetailedPageDetails().isDetailPage()){
                Intent intent = new Intent(context, DetailedPageActivity.class);

                DetailedPageData detailedPageData = new DetailedPageData();
                detailedPageData.setJsonObject(jsonObject.toString());
                detailedPageData.setDataManagementOptions(dataManagementOptions);
                detailedPageData.setAppName(strAppName);
                detailedPageData.setTableName(tableName);
                detailedPageData.setSubFormDetails(subFormDetails);
                detailedPageData.setAppVersion(strAppVersion);
                detailedPageData.setAppType(strAppType);
                detailedPageData.setDisplayAppName(strDisplayAppName);
                detailedPageData.setDisplayIcon(getIntent.getStringExtra("s_app_icon"));
                detailedPageData.setCreatedBy(strCreatedBy);
                detailedPageData.setUserID(strUserId);
                detailedPageData.setDistributionID(strDistributionId);
                detailedPageData.setUserLocationStructure(strUserLocationStructure);
                detailedPageData.setUserPostID(strPostId);
                detailedPageData.setFromActivity("ViewData");
                detailedPageData.setAppIcon(getIntent.getStringExtra("s_app_icon"));
                detailedPageData.setFormLevelTranslation(null);

                intent.putExtra("DetailedPageData",(Serializable) detailedPageData);

                startActivityForResult(intent, 0);
            }else{
                Toast.makeText(context, "Detailed Page disabled", Toast.LENGTH_SHORT).show();
            }} catch (Exception e) {
            improveHelper.improveException(context, TAG, "viewRow", e);
        }
    }

    private void viewRow1(JSONObject jsonObject, String status) {
        try {

            Intent intent = new Intent(context, QueryDetailsActivity.class);

            intent.putExtra("jsonObject", jsonObject.toString());

            intent.putExtra("dataCollectionObject", dataCollectionObject);

            intent.putExtra("Trans_id", jsonObject.getString("Trans_ID"));
            intent.putExtra("appName", strAppName);
            intent.putExtra("s_childForm", "EditForm");
            intent.putExtra("s_app_version", strAppVersion);
            intent.putExtra("s_app_type", strAppType);
            intent.putExtra("s_org_id", strOrgId);
            intent.putExtra("s_app_name", strAppName);
            intent.putExtra("s_display_app_name", strDisplayAppName);
            intent.putExtra("s_created_by", strCreatedBy);
            intent.putExtra("s_user_id", strUserId);
            intent.putExtra("s_distribution_id", strDistributionId);
            intent.putExtra("s_user_location_Structure", strUserLocationStructure);
            intent.putExtra("s_user_post_id", strPostId);
            intent.putExtra("app_edit", "Edit");
            intent.putExtra("fromActivity", "ViewData");
            intent.putExtra("tableName",tableName);
            Gson gson = new Gson();
            intent.putExtra("subFormDetails",  gson.toJson(subFormDetails));
//            startActivity(intent);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "viewRow", e);
        }

    }

    private void editRow(JSONObject jsonObject, String status) {
        try {

            Intent intent = new Intent(context, MainActivity.class);

            intent.putExtra("jsonObject", jsonObject.toString());

            intent.putExtra("create_query_object", dataCollectionObject);

            intent.putExtra("Trans_id", jsonObject.getString("Trans_ID"));
            intent.putExtra("appName", strAppName);
            intent.putExtra("s_childForm", "EditForm");
            intent.putExtra("s_app_version", strAppVersion);
            intent.putExtra("s_app_type", strAppType);
            intent.putExtra("s_org_id", strOrgId);
            intent.putExtra("s_app_name", strAppName);
            intent.putExtra("s_display_app_name", strDisplayAppName);
            intent.putExtra("s_created_by", strCreatedBy);
            intent.putExtra("s_user_id", strUserId);
            intent.putExtra("s_distribution_id", strDistributionId);
            intent.putExtra("s_user_location_Structure", strUserLocationStructure);
            intent.putExtra("app_edit", "Edit");
            intent.putExtra("tableName",tableName);
            Gson gson = new Gson();
            intent.putExtra("subFormDetails",  gson.toJson(subFormDetails));
            if (getIntent != null && getIntent.hasExtra("From_InTaskDetails")) {
                intent.putExtra("From_InTaskDetails", "From_InTaskDetails");
            }

//            startActivity(intent);
            startActivityForResult(intent, BACK_TO_LIST_FLAG);
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "editRow", e);
        }

    }

    public void deleteAlert(Context context, int position, String transId, String status) {
    /*    try {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.are_you_sure_del)

                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what would happen when positive button is clicked
                            dialogInterface.dismiss();
                            deleteRow(position, transId, status);
                        }
                    })

                    .setNegativeButton(R.string.d_no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what should happen when negative button is clicked
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "deleteAlert", e);
        }*/

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.AlertDialogTheme);
        final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = linflater.inflate(R.layout.custom_alert_dialog, null);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.alertMessage)).setText(R.string.are_you_sure_del);
        ((Button) view.findViewById(R.id.buttonYes)).setText(R.string.yes);
        ((Button) view.findViewById(R.id.buttonNo)).setText(R.string.no);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRow(position, transId, status);
                alertDialog.dismiss();

            }
        });
        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void deleteRow(int position, String transId, String status) {
        try {
            if (status.equalsIgnoreCase("offline")) {
                boolean result_status = improveDataBase.deleteRowData(transId);
                if (result_status) {
                    jsonArrayMain.remove(position);

                }
            } else {
                improveHelper.showProgressDialog(getString(R.string.please_wait));
                AppDetailsAdvancedAction appDetailsAdvancedAction = new AppDetailsAdvancedAction();
                appDetailsAdvancedAction.setOrgId(strOrgId);
                appDetailsAdvancedAction.setUserId(strUserId);
                appDetailsAdvancedAction.setPageName(strAppName);
                appDetailsAdvancedAction.setAction("Delete");
                appDetailsAdvancedAction.setTransID(transId);
                Call<DeviceIdResponse> getAllAppNamesDataCall = getServices.iDeleteAppData(sessionManager.getAuthorizationTokenId(),appDetailsAdvancedAction);
                getAllAppNamesDataCall.enqueue(new Callback<DeviceIdResponse>() {
                    @Override
                    public void onResponse(Call<DeviceIdResponse> call, Response<DeviceIdResponse> response) {
                        improveHelper.dismissProgressDialog();
                        DeviceIdResponse responseData = response.body();
                        if (responseData.getStatus().equalsIgnoreCase("200")) {
                            Toast.makeText(context, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                            if (responseData.getMessage().equalsIgnoreCase("Success")) {
                                jsonArray.remove(position);
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<DeviceIdResponse> call, Throwable t) {
                        improveHelper.dismissProgressDialog();
                    }
                });
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "deleteRow", e);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        switch (requestCode) {


        }
    }

    private boolean isFileExists(String filename) {

        File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
        return folder1.exists();


    }

    private boolean deleteFileifExists(String filename) {

        File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
        return folder1.delete();


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_add_row:

                addNewRow();

                break;
        }
    }

    private void addNewRow() {
        try {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("app_edit", "New");
//            intent.putExtra("s_app_design", strAppDesign);
            intent.putExtra("s_app_version", strAppVersion);
            intent.putExtra("s_app_type", strAppType);
            intent.putExtra("s_org_id", strOrgId);
            intent.putExtra("s_app_name", strAppName);
            intent.putExtra("s_display_app_name", strDisplayAppName);
            intent.putExtra("s_created_by", strCreatedBy);
            intent.putExtra("s_user_id", strUserId);
            intent.putExtra("s_distribution_id", strDistributionId);
            intent.putExtra("s_user_location_Structure", strUserLocationStructure);
            intent.putExtra("s_user_post_id", strPostId);
            if (getIntent.hasExtra("from")) {
                intent.putExtra("from", "CALL_FROM");
            }
            if (getIntent != null && getIntent.hasExtra("From_InTaskDetails")) {
                intent.putExtra("From_InTaskDetails", "From_InTaskDetails");
            }

            if (getIntent.hasExtra("s_resume")) {
                intent.putExtra("s_resume", isResume);
                intent.putExtra("caller_form_name", callerFormName);
                intent.putExtra("form_type", callerFormType);
            }

            if (getIntent.hasExtra("VariablesData")) {
                intent.putExtra("VariablesData", getIntent.getBundleExtra("VariablesData"));
            }
            if (getIntent.hasExtra("exit_to_menu")) {
                intent.putExtra("exit_to_menu", getIntent.getBooleanExtra("exit_to_menu", false));
            }
            if (getIntent.hasExtra("keep_session")) {

                intent.putExtra("keep_session", getIntent.getBooleanExtra("keep_session", false));
            }

            if (getIntent.hasExtra("jsonObject")) {
                intent.putExtra("jsonObject", getIntent.getStringExtra("jsonObject"));
                intent.putExtra("s_childForm", "EditForm");
            }
            intent.putExtra("tableName",tableName);
            Gson gson = new Gson();
            intent.putExtra("subFormDetails",  gson.toJson(subFormDetails));

            startActivityForResult(intent, BACK_TO_LIST_FLAG);
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "addNewRow", e);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        getAppData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (getActivity() != null) {
           /* SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                    .findFragmentById(R.id.map);*/
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
        }
    }





    public void setMapPointsDynamically(String ViewType, List<String> Points) {
        try{
        final LatLngBounds.Builder builder = new LatLngBounds.Builder();

        switch (ViewType) {
            case AppConstants.Single_Point_GPS:

                for (int i = 0; i < Points.size(); i++) {
                    String[] loc = Points.get(i).split("\\$");

                    LatLng temp_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));


                    if (temp_location.latitude != 0.0 && temp_location.longitude != 0.0) {
/*
                        if (controlObject.getMapIcon() != null && !controlObject.getMapIcon().equalsIgnoreCase("Default") && !controlObject.getMapIcon().trim().equalsIgnoreCase("")) {
                            Glide.with(context)
                                    .asBitmap()
                                    .load(controlObject.getMapIcon())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                            Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                                                    .position(temp_location)
                                                    .icon(BitmapDescriptorFactory.fromBitmap(resource))
                                                    .title("Location")
                                                    .snippet(temp_location.latitude + "," + temp_location.longitude));
//
                                            builder.include(marker.getPosition());
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {
                                        }
                                    });
                        } else {*/


                        mGoogleMap.addMarker(new MarkerOptions()
                                .position(temp_location)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.gps))
                                .title("Location " + i)
                                .snippet(temp_location.latitude + "," + temp_location.longitude));
//                        }
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp_location, 8));
                    }
                }
                break;
            case AppConstants.Two_points_line:
            case AppConstants.Multi_points_line:
            case AppConstants.Four_points_square:
                /*for (int i = 0; i < Points.size(); i++) {
                    String[] locations = Points.get(i).split("\\^");
                    for (int j = 0; j < locations.length - 1; j++) {
                        String[] loc = locations[j].split("\\$");
                        String[] loc1 = locations[j + 1].split("\\$");
                        LatLng temp_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
                        LatLng temp_location1 = new LatLng(Double.parseDouble(loc1[0]), Double.parseDouble(loc1[1]));
                        if (temp_location.latitude != 0.0 && temp_location.longitude != 0.0) {

                            mGoogleMap.addPolyline(new PolylineOptions()
                                    .add(temp_location, temp_location1)
                                    .width(5));
                            //.color(getColor(R.color.lightBlue)));

                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp_location, 16));
                        }
                    }
                }*/
                for (int i = 0; i < Points.size(); i++) {

                    if (ViewType.equalsIgnoreCase(AppConstants.Four_points_square)) {
                        String[] locationsTemp = Points.get(i).split("\\^");
                        String pointsTemp = Points.get(i) + "^" + locationsTemp[0];
                        Points.clear();
                        Points.add(pointsTemp);
                    }
                    String[] locations = Points.get(i).split("\\^");
                    for (int j = 0; j < locations.length - 1; j++) {
                        String[] loc = locations[j].split("\\$");
                        String[] loc1 = locations[j + 1].split("\\$");
                        LatLng temp_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
                        LatLng temp_location1 = new LatLng(Double.parseDouble(loc1[0]), Double.parseDouble(loc1[1]));
                        if (temp_location.latitude != 0.0 && temp_location.longitude != 0.0) {

                            mGoogleMap.addPolyline(new PolylineOptions()
                                    .add(temp_location, temp_location1)
                                    .width(5));
                            //.color(getColor(R.color.lightBlue)));

                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp_location, 16));
                        }
                    }
                }
                break;
            case AppConstants.map_Polygon:
                for (int i = 0; i < Points.size(); i++) {
                    String[] locations = Points.get(i).split("\\^");
                    List<LatLng> list_poly = new ArrayList<LatLng>();

                    for (int j = 0; j < locations.length; j++) {
                        String[] loc = locations[j].split("\\$");
                        LatLng temp_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
                        list_poly.add(temp_location);
                    }
                    if (list_poly.size() > 1) {
                        mGoogleMap.addPolygon(new PolygonOptions()
                                .clickable(true)
                                .addAll(list_poly));
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(list_poly.get(list_poly.size() - 1), 16));
                    }

                }
                break;
            default:
                break;
        }


        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
       /* if(controlObject.isZoomControl()) {
            mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        }else{*/
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
//        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.resetMinMaxZoomPreference();

//        mapFrag.performContextClick();

        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "setMapPonitsDynamically", e);
        }
    }

    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(context.getApplicationContext());
        mGoogleMap = googleMap;
        //asdf
       *//* if (mGoogleMap != null) {
            mGoogleMap.clear();
        }*//*
     *//*if (controlObject.getMapView() != null) {

            if (controlObject.getMapView().equalsIgnoreCase("Satellite")) {
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            } else if (controlObject.getMapView().equalsIgnoreCase("Roads")) {
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }

        }*//*
    }*/

    @Override
    public void onPolygonClick(Polygon polygon) {

    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mGoogleMap = googleMap;
        if (mGoogleMap != null) {
            mGoogleMap.clear();
        }
//        getAppData();
        initViews();
    }


    private String getControlType(String columnName) {

        try {
            if (columnName.contains("_Coordinates")) {
                columnName = columnName.replace("_Coordinates", "");
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "getControlNameForGPS", e);
        }
        for(Control controlData:formControls.getMainFormControlsList()){
            if (controlData.getControlName().equalsIgnoreCase(columnName)){
                return controlData.getControlType();
            }
        }
        return  null;}

    private String getSubformTableName(String subFormName_new) {
        String tableName=null;
        for(SubFormTableColumns subFormTableColumns:subFormDetails){
            if(subFormTableColumns.getSubFormName().equalsIgnoreCase(subFormName_new)){
                return subFormTableColumns.getTableName();
            }
        }
        return tableName;
    }
}



