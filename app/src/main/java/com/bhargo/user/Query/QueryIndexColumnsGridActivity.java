package com.bhargo.user.Query;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.Create_Query_Object;
import com.bhargo.user.Java_Beans.QueryFilterField_Bean;
import com.bhargo.user.Java_Beans.QuerySelectField_Bean;
import com.bhargo.user.Java_Beans.Variable_Bean;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.advanced.Gps_Control;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.screens.LoadForm;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.QueryFormControlsValidation;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bhargo.user.utils.XMLHelper;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.utils.AppConstants.REQUEST_GPS_ENABLE;
import static com.bhargo.user.utils.AppConstants.currentMultiForm;
import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

public class QueryIndexColumnsGridActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, Gps_Control.LocCaptured {

    private static final String TAG = "QueryIndexColumnsActivi";
    private final List<String> gpsValues = new ArrayList<>();
    private final boolean gpsFlag = false;
    private final List<String> gpsStringListSubmit = new ArrayList<>();
    public TextView title;
    public ImageView ib_settings;
    public LatLngBounds.Builder builder;
    public boolean isMap_List = true;
    public List<Variable_Bean> list_Variables = new ArrayList<Variable_Bean>();
    public List<Variable_Bean> list_VariablesDataFromIntent = new ArrayList<Variable_Bean>();
    ArrayList<String> stringListControlType;
    ImproveHelper improveHelper;
    List<QueryFilterField_Bean> listOfQueryFilters;
    private String response;
    private JSONObject resObj;
    private List<String> List_Index_Columns;
    private List<QueryFilterField_Bean> List_Query_Columns;
    private List<QuerySelectField_Bean> List_Final_Columns;
    private Create_Query_Object create_query_object;
    private RecyclerView rvIndexColumns;
    private Context context;
    private IndexColumnsAdapter adapter;
    private String appName, strChildName;
    private Toolbar toolbar;
    private FrameLayout mapLayout;
    private SupportMapFragment mapFragment;
    private String gpsColumnName;
    private JSONArray outputArray;
    private GoogleMap gMap;
    private String refLat, refLng;
    private String strControlTypeView;
    private LinearLayout linearLayout;
    private boolean distanceShown;
    private LinearLayout ll_data;
    private LinearLayout ll_map;
    private LinearLayout bottom_layout;
    private RelativeLayout llIndexColumns;
    /*Filter Layout*/
    private LinearLayout ll_main_filters;
    private CustomButton cb_get_data;
    private ScrollView layout_filters;
    private ImageView iv_filter;
    private List<QuerySelectField_Bean> List_DisplayFields = new ArrayList<>();
    private List<ControlObject> listOfControls;
    private String strUserLocationStructure;
    private XMLHelper xmlHelper;
    private String designFormat;
    private String sourceType;
    private String sourceFormType;
    private String sorceForm;
    private String orgId, userId;
    private String distributionId;
    private String strChildForm;
    private ImproveDataBase improveDataBase;
    private GetServices getServices;
    private boolean skipQueryActivity;
    private boolean isGPSSingleFilter;
    private Animation animSlideUp;
    private Animation animSlideDown;
    private List<String> stringListSubmit = new ArrayList<>();
    private LoadForm loadForm;
    private String gpsControlName;
    private String gpsType;
    private String query_type = "";
    private boolean allFiltersOptional = true;
    private boolean appFromMultiform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Theme
        setBhargoTheme(this,AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_index_columns_new);

        improveHelper = new ImproveHelper(this);
        iv_filter = findViewById(R.id.iv_filter);
        context = QueryIndexColumnsGridActivity.this;
        appName = getIntent().getStringExtra("s_app_name");
        strChildName = getIntent().getStringExtra("s_childForm");
        if (getIntent() != null && getIntent().hasExtra("VariablesData")) {
            Bundle Variables = getIntent().getBundleExtra("VariablesData");
            list_VariablesDataFromIntent = (List<Variable_Bean>) Variables.getSerializable("Variables");
        }
        appFromMultiform = getIntent().getBooleanExtra("appInMultiform",false);

        initializeActionBar();
        initAnimations();
        initViews();
        showFilterViews();
        hideIndexViews();
        loadFilters();

        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (layout_filters.getVisibility() == View.VISIBLE) {
                        hideFilterViews();
                    } else if (layout_filters.getVisibility() == View.GONE && (listOfQueryFilters != null && listOfQueryFilters.size() > 0)) {
                        showFilterViews();
                    }
                    findViewById(R.id.ct_no_data).setVisibility(View.GONE);
                }catch(Exception e){
                    ImproveHelper.improveException(context, TAG, "filter onClick()", e);
                }
            }
        });
    }

    private void initViews() {
        try {
            /*Filter Views*/
            layout_filters = findViewById(R.id.layout_filters);
            ll_main_filters = findViewById(R.id.ll_main);
            cb_get_data = findViewById(R.id.cb_get_data);
            cb_get_data.setVisibility(View.GONE);

            /*Index Views*/
            llIndexColumns = findViewById(R.id.llIndexColumns);
            rvIndexColumns = findViewById(R.id.rvIndexColumns);
            ll_data = findViewById(R.id.ll_data);
            ll_map = findViewById(R.id.ll_map);
            bottom_layout = findViewById(R.id.bottom_layout);
            mapLayout = findViewById(R.id.mapLayout);
            mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.mapFragment);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "initViews", e);
        }

    }

    private void initAnimations() {
        try {
            animSlideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
            animSlideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "initAnimations", e);
        }
    }

    private void showFilterViews() {
        try {
            layout_filters.setVisibility(View.VISIBLE);
            layout_filters.startAnimation(animSlideDown);
            layout_filters.setAlpha(1);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "showFilterViews", e);
        }
    }

    private void showIndexViews() {
        try {
            rvIndexColumns.setVisibility(View.VISIBLE);
            mapLayout.setVisibility(View.GONE);
            bottom_layout.setVisibility(View.VISIBLE);
            llIndexColumns.setVisibility(View.VISIBLE);
            llIndexColumns.startAnimation(animSlideDown);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "showIndexViews", e);
        }
    }

    private void hideIndexViews() {
        try {
            llIndexColumns.startAnimation(animSlideDown);
            llIndexColumns.setVisibility(View.GONE);
            rvIndexColumns.setVisibility(View.GONE);
            mapLayout.setVisibility(View.GONE);
            bottom_layout.setVisibility(View.GONE);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "hideIndexViews", e);
        }
    }

    private void hideFilterViews() {
        try {
            layout_filters.startAnimation(animSlideDown);
            layout_filters.setVisibility(View.GONE);
            layout_filters.setAlpha(0);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "hideFilterViews", e);
        }
    }

    private void loadIndexViews() {
        try {
            List_Index_Columns = new ArrayList<>();
            List_Index_Columns = create_query_object.getList_Index_Columns();

            List_Query_Columns = new ArrayList<>();
            List_Query_Columns = create_query_object.getList_FormAPIQuery_FilterFields();
            List_Final_Columns = create_query_object.getList_Form_DisplayFields();

            stringListControlType = new ArrayList<>();
            if (List_Index_Columns != null && List_Index_Columns.size() > 0) {
                for (int i = 0; i < List_Index_Columns.size(); i++) {

                    ControlObject controlObject = getIndexControlObject(List_Index_Columns.get(i));
                    if (controlObject != null) {

                        if (controlObject.getControlType() != null && controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                            gpsColumnName = controlObject.getControlName();
                        }

                        /*all types of controls to arrayList for View Purpose*/
                        stringListControlType.add(controlObject.getControlType());
                    }
                }
            }

            if (stringListControlType.contains(AppConstants.CONTROL_TYPE_GPS)
                    && stringListControlType.contains(AppConstants.CONTROL_TYPE_CAMERA)) {
                //strControlTypeView = AppConstants.CONTROL_TYPE_GPS;
                strControlTypeView = AppConstants.INDEX_IMAGE_GPS_VIEW;

            } else if (stringListControlType.contains(AppConstants.CONTROL_TYPE_GPS)) {
                //strControlTypeView = AppConstants.CONTROL_TYPE_GPS;
                strControlTypeView = AppConstants.INDEX_GPS_VIEW;

            } else if (stringListControlType.contains(AppConstants.CONTROL_TYPE_CAMERA)) {
                //strControlTypeView = AppConstants.CONTROL_TYPE_CAMERA;
                strControlTypeView = AppConstants.INDEX_IMAGE_VIEW;

            } else {

                //strControlTypeView = "Normal";
                strControlTypeView = AppConstants.INDEX_NORMAL_VIEW;
            }

            for (int i = 0; i < List_Query_Columns.size(); i++) {
                QueryFilterField_Bean queryFilterField_bean = List_Query_Columns.get(i);
                ControlObject controlObject = new ControlObject();
                if (queryFilterField_bean.getExist_Field_ControlObject() != null) {
                    controlObject = queryFilterField_bean.getExist_Field_ControlObject();
                } else {
                    controlObject = queryFilterField_bean.getField_ControlObject();
                }

                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                    distanceShown = true;
                    break;
                }

            }

            try {

                resObj = new JSONObject(response);

                outputArray = resObj.getJSONArray("output");

                adapter = new IndexColumnsAdapter(outputArray, strControlTypeView);

                if (strControlTypeView.equalsIgnoreCase(AppConstants.INDEX_IMAGE_VIEW)
                        || strControlTypeView.equalsIgnoreCase(AppConstants.INDEX_IMAGE_GPS_VIEW)) {
                    rvIndexColumns.setLayoutManager(new GridLayoutManager(QueryIndexColumnsGridActivity.this, 2));
                } else {
                    rvIndexColumns.setLayoutManager(new LinearLayoutManager(context));
                }

                rvIndexColumns.setAdapter(adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            ll_data.setOnClickListener(view -> {

                isMap_List = true;
                initilizeList();

            });

            ll_map.setOnClickListener(view -> {
                try {

                    if (gpsValues != null && gpsValues.size() > 0) {
                        isMap_List = false;
                        initilizeMap();
                    } else {
                        isMap_List = false;
                        ImproveHelper.showToast(QueryIndexColumnsGridActivity.this, "No GPS Values");
                    }
                }catch (Exception e){
                    ImproveHelper.improveException(this, TAG, "map onClick()", e);
                }


            });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "loadIndexViews", e);
        }
    }

    private void loadFilters() {
        try {
            Intent getIntent = getIntent();
            if (getIntent != null) {

                strUserLocationStructure = getIntent.getStringExtra("s_user_location_Structure");
//                designFormat = getIntent().getStringExtra("s_design_format");
                designFormat = ImproveHelper.getDesignFormat(context);
                appName = getIntent.getStringExtra("s_app_name");
                orgId = getIntent.getStringExtra("s_org_id");
                distributionId = getIntent.getStringExtra("s_distribution_id");

                if (getIntent.getStringExtra("s_childForm") != null && (getIntent.getStringExtra("s_childForm").equalsIgnoreCase("ChildForm"))) {
                    strChildForm = getIntent.getStringExtra("s_childForm");
                }

                if (getIntent() != null && getIntent.hasExtra("VariablesData")) {
                    Bundle Variables = getIntent.getBundleExtra("VariablesData");
                    list_VariablesDataFromIntent = (List<Variable_Bean>) Variables.getSerializable("Variables");
                }
            }

            create_query_object = new Create_Query_Object();

            xmlHelper = new XMLHelper();

            Log.d(TAG, "DesignData: " + designFormat);
            create_query_object = xmlHelper.XML_To_QueryFormObject(designFormat, this);

            sourceFormType = create_query_object.getQuery_Source_Type();

            sourceType = create_query_object.getQuery_Source();

            sorceForm = create_query_object.getFormQuery_Name();

            List_DisplayFields = create_query_object.getList_Form_DisplayFields();

            if (create_query_object.getQuery_Title() != null) {

                title.setText(create_query_object.getQuery_Title());
            } else {
                title.setText(appName);
            }
            //List_Of_Variables_Initialising
            list_Variables = create_query_object.getList_Varibles();
            if (list_VariablesDataFromIntent.size() > 0) {
                for (int i = 0; i < list_VariablesDataFromIntent.size(); i++) {
                    for (int j = 0; j < list_Variables.size(); j++) {
                        if (list_Variables.get(j).getVariable_Name().equalsIgnoreCase(list_VariablesDataFromIntent.get(i).getVariable_Name())) {
                            list_Variables.set(j, list_VariablesDataFromIntent.get(i));
                        }
                    }
                }
            }

            improveDataBase = new ImproveDataBase(context);

            getServices = RetrofitUtils.getUserService();


            listOfControls = new ArrayList<>();

//        layout_filters.setAnimation(animSlideDown);

            cb_get_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (formValidated(listOfControls)) {
//                    hideFilterViews();
                            dismissProgressDialog();
                            showIndexViews();
                            if (create_query_object.isQuery_OffLine()) {
                                if (ImproveHelper.isNetworkStatusAvialable(context)) {
                                    getDataForQuery(dataForServerHit(), true);
                                } else {
                                    if (improveDataBase.getCountByValue(ImproveDataBase.QUERY_DATA_TABLE, ImproveDataBase.QUERY_STRING, dataForServerHit().get("columnstring")) > 0) {

                                        String json = "";

                                        List<List<String>> data = improveDataBase.getTableColDataByCond(ImproveDataBase.QUERY_DATA_TABLE
                                                , ImproveDataBase.QUERY_FETCHED_DATA_STRING
                                                , new String[]{ImproveDataBase.QUERY_STRING}
                                                , new String[]{dataForServerHit().get("columnstring")});

                                        if (data.size() > 0) {

                                        } else {
                                            ImproveHelper.showToast(context, getString(R.string.no_records));
                                        }


                                    } else {
                                        ImproveHelper.showToast(context, getString(R.string.no_records));
                                    }
                                }
                            } else {
                                if (ImproveHelper.isNetworkStatusAvialable(context)) {
                                    getDataForQuery(dataForServerHit(), true);
                                } else {
                                    ImproveHelper.showToast(context, getString(R.string.pls_check_network));
                                }
                            }
                        }
                    }catch(Exception e){
                        ImproveHelper.improveException(context, TAG, "Apply onClick()", e);
                    }
                }
            });

            listOfQueryFilters = create_query_object.getList_FormAPIQuery_FilterFields();

            if (listOfQueryFilters.size() > 0) {

                if (listOfQueryFilters.size() == 1
                        && ((listOfQueryFilters.get(0).getExist_Field_ControlObject() != null && listOfQueryFilters.get(0).getExist_Field_ControlObject()
                        .getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) || (listOfQueryFilters.get(0).getField_ControlObject() != null && listOfQueryFilters.get(0).getField_ControlObject()
                        .getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)))) {
                    cb_get_data.setVisibility(View.GONE);

                    ControlObject controlObject = listOfQueryFilters.get(0).getExist_Field_ControlObject();
                    controlObject.setOnChangeEventExists(false);
                    controlObject.setOnFocusEventExists(false);
                    controlObject.setLocationMode(AppConstants.LOCATION_MODE_NETWORK);
                    controlObject.setGpsType(AppConstants.Single_Point_GPS);

                    gpsControlName = controlObject.getControlName();
                    gpsType = controlObject.getGpsType();
                    setDefaultValue(controlObject);
                    listOfControls.add(controlObject);

                    loadForm = new LoadForm(this, ll_main_filters, true);

                    loadForm.load(listOfControls);

                    ll_main_filters.setVisibility(View.GONE);

                    query_type = "gps";

                    isGPSSingleFilter = true;

                    CustomTextView tapToStart = getTapToStart(ll_main_filters);

                    //showProgressDialog("getting location...");
                    tapToStart.performClick();

                } else if (listOfQueryFilters.size() == 1 && listOfQueryFilters.get(0).getField_Static_Value() != null) {

                    cb_get_data.setVisibility(View.GONE);

                    for (int i = 0; i < listOfQueryFilters.size(); i++) {

                        ControlObject controlObject = new ControlObject();

                        if (listOfQueryFilters.get(i).getExist_Field_ControlObject() != null) {

                            controlObject = listOfQueryFilters.get(i).getExist_Field_ControlObject();
                            controlObject.setOnChangeEventExists(false);
                            controlObject.setOnFocusEventExists(false);

                        } else {

                            controlObject = listOfQueryFilters.get(i).getField_ControlObject();
                            controlObject.setOnChangeEventExists(false);
                            controlObject.setOnFocusEventExists(false);

                        }
                        if(listOfQueryFilters.get(i).getField_Global_Type()!=null){
                            controlObject.setInvisible(true);
                        }
//                        setDefaultValue(controlObject);
                        controlObject.setDefaultValue(listOfQueryFilters.get(i).getField_Global_Value());
                        if(listOfQueryFilters.get(i).getField_Global_Value()==null){
                            controlObject.setDefaultValue(listOfQueryFilters.get(i).getField_Static_Value());
                        }
                        listOfControls.add(controlObject);

                        if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                            query_type = "gps";
                        }

                    }

                    loadForm = new LoadForm(this, ll_main_filters, true);
//
                    loadForm.load(listOfControls);

                    cb_get_data.performClick();

                } else {

                    cb_get_data.setVisibility(View.VISIBLE);

                    for (int i = 0; i < listOfQueryFilters.size(); i++) {

                        ControlObject controlObject = new ControlObject();

                        if (listOfQueryFilters.get(i).getExist_Field_ControlObject() != null) {

                            controlObject = listOfQueryFilters.get(i).getExist_Field_ControlObject();
                            controlObject.setOnChangeEventExists(false);
                            controlObject.setOnFocusEventExists(false);
                            if(listOfQueryFilters.get(i).getField_Global_Type()!=null&&listOfQueryFilters.get(i).getField_Global_Type().contentEquals("SystemVariables") || listOfQueryFilters.get(i).getField_ValueType().contentEquals("Static")){
                                controlObject.setInvisible(true);
                            }else{
                                controlObject.setInvisible(false);
                            }

                        } else {

                            controlObject = listOfQueryFilters.get(i).getField_ControlObject();
                            controlObject.setOnChangeEventExists(false);
                            controlObject.setOnFocusEventExists(false);

                            if(listOfQueryFilters.get(i).getField_Global_Type()!=null&&listOfQueryFilters.get(i).getField_Global_Type().contentEquals("SystemVariables")){
                                controlObject.setInvisible(true);
                            }else{
                                controlObject.setInvisible(false);
                            }

                        }
                      /*  if(listOfQueryFilters.get(i).getField_Global_Type()!=null){
                            controlObject.setInvisible(true);
                        }*/
                        //controlObject.setDefaultValue(listOfQueryFilters.get(i).getField_Global_Value());
                        //setDefaultValue(controlObject);
                        listOfControls.add(controlObject);

                        if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                            gpsControlName = controlObject.getControlName();
                            gpsType = controlObject.getGpsType();
                            query_type = "gps";
                        }

                    }

                    loadForm = new LoadForm(this, ll_main_filters, true);

                    loadForm.load(listOfControls);

                    isGPSSingleFilter = false;

                    if (query_type.equalsIgnoreCase("gps")) {
                        CustomTextView tapToStart = getTapToStart(ll_main_filters);

                        tapToStart.performClick();
                    }


                }

                for (int i = 0; i < listOfQueryFilters.size(); i++) {

                    QueryFilterField_Bean filterFieldBean = listOfQueryFilters.get(i);
                    if (filterFieldBean.isField_IsMandatory()) {
                        allFiltersOptional = false;
                        break;
                    }

                }

                if (allFiltersOptional&&!isGPSSingleFilter) {
                    hideFilterViews();
                    cb_get_data.performClick();
                }
            } else {


                cb_get_data.setVisibility(View.GONE);

                hideFilterViews();
                showIndexViews();

                if (ImproveHelper.isNetworkStatusAvialable(context)) {
                    getDataForQuery(dataForServerHit(), true);

                } else {

                    if (improveDataBase.getCountByValue(ImproveDataBase.QUERY_DATA_TABLE, ImproveDataBase.QUERY_STRING, dataForServerHit().get("columnstring")) > 0) {

                        String json = "";

                        List<List<String>> data = improveDataBase.getTableColDataByCond(ImproveDataBase.QUERY_DATA_TABLE
                                , ImproveDataBase.QUERY_FETCHED_DATA_STRING
                                , new String[]{ImproveDataBase.QUERY_STRING}
                                , new String[]{dataForServerHit().get("columnstring")});

                        if (data.size() > 0) {

                            json = data.get(0).get(0);

                            skipQueryActivity = cb_get_data.getVisibility() != View.VISIBLE;
                            response = json;

                        } else {
                            ImproveHelper.showToast(context, getString(R.string.no_records));
                        }


                    } else {
                        ImproveHelper.showToast(context, getString(R.string.no_records));
                    }
                }
            }

           /* if (formValidated(listOfControls)&&!isGPSSingleFilter) {
                cb_get_data.performClick();
                dismissProgressDialog();
            }*/

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "loadFilters", e);
        }
    }

    private void initilizeList() {
        try {
            rvIndexColumns.setVisibility(View.VISIBLE);
            mapLayout.setVisibility(View.GONE);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "initilizeList", e);
        }
    }

    private void initilizeMap() {
        try {
            rvIndexColumns.setVisibility(View.GONE);
            mapLayout.setVisibility(View.VISIBLE);
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "initilizeMap", e);
        }
    }

    public void initializeActionBar() {
        try {
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            title = toolbar.findViewById(R.id.toolbar_title);
            ib_settings = toolbar.findViewById(R.id.ib_settings);
            ib_done = toolbar.findViewById(R.id.ib_done);
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp);
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.red), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            }
            ib_settings.setVisibility(View.GONE);
//            title.setText(getIntent().getStringExtra("appName"));
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "initializeActionBar", e);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {

            gMap = googleMap;
            gMap.clear();
            gMap.getUiSettings().setZoomControlsEnabled(true);

            gMap.getUiSettings().setAllGesturesEnabled(true);

            gMap.getUiSettings().setMapToolbarEnabled(false);

            gMap.setOnMarkerClickListener(this);

            final LatLngBounds.Builder builder = new LatLngBounds.Builder();
            if (builder != null) {
//        builder = new LatLngBounds.Builder();

                for (int i = 0; i < gpsValues.size(); i++) {

                    String Lat = gpsValues.get(i).split("\\$")[0];
                    String Lon = gpsValues.get(i).split("\\$")[1];

                    refLat = Lat;
                    refLng = Lon;

                    LatLng latLng = new LatLng(Double.parseDouble(Lat), Double.parseDouble(Lon));

                    Marker marker = googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

                    dropPinEffect(marker);
                    marker.setTag(i);
                    builder.include(marker.getPosition());
                }


            }

            googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    try {
                        final LatLngBounds bounds = builder.build();
                        if (bounds != null) {
                            int padding = 100; // offset from edges of the map in pixels
                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                            gMap.moveCamera(cu);
                            gMap.animateCamera(cu);
                        }
                    }catch(Exception e){
                        ImproveHelper.improveException(context, TAG, "onMapLoaded", e);
                    }
                }
            });
        }catch(Exception e){
            ImproveHelper.improveException(this, TAG, "onMapReady()", e);
        }


    }

    private void dropPinEffect(final Marker marker) {
        try {
            final Handler handler = new Handler();
            final long start = SystemClock.uptimeMillis();
            final long duration = 3500;

            final Interpolator interpolator = new BounceInterpolator();

            handler.post(new Runnable() {
                @Override
                public void run() {
                    long elapsed = SystemClock.uptimeMillis() - start;
                    float t = Math.max(
                            1 - interpolator.getInterpolation((float) elapsed
                                    / duration), 0);
                    marker.setAnchor(0.5f, 1.0f + 14 * t);

                    if (t > 0.0) {
                        // Post again 15ms later.
                        handler.postDelayed(this, 15);
                    } else {
                        marker.showInfoWindow();

                    }
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "dropPinEffect", e);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {


        int position = Integer.parseInt(marker.getTag().toString());

        Intent in = new Intent(context, QueryDetailsActivity.class);

        try {
            in.putExtra("jsonObject", outputArray.getJSONObject(position).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        in.putExtra("create_query_object", create_query_object);

        in.putExtra("appName", appName);

        startActivity(in);

        return false;
    }


    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);

    }

    private ControlObject getIndexControlObject(String indexColumnName) {
        ControlObject controlObjectToReturn = new ControlObject();
        try {
            for (int i = 0; i < List_Final_Columns.size(); i++) {

                if (List_Final_Columns.get(i).getField_ControlObject().getControlName().equalsIgnoreCase(indexColumnName)) {
                    controlObjectToReturn = List_Final_Columns.get(i).getField_ControlObject();
                }

            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "getIndexControlObject", e);
        }
        return controlObjectToReturn;
    }


    private boolean formValidated(List<ControlObject> list_Control) {
        boolean flag = true;
        try {
            if (list_Control.size() > 0) {

                QueryFormControlsValidation controlsValidation = new QueryFormControlsValidation(context, loadForm.getList_ControlClassObjects(), null);

                for (int i = 0; i < list_Control.size(); i++) {
                    Log.d(TAG, "XmlHelperTextInput: " + list_Control.get(i).getControlType());

                    flag = controlsValidation.controlSubmitValidation(list_Control.get(i), false, null, null, "0", 0, -1);

                    if (!flag) {
                        break;
                    }
                }

                stringListSubmit = controlsValidation.getDataCollectionString();
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "formValidated", e);
        }
        return flag;
    }

    private void getDataForQuery(final Map<String, String> data, final boolean isFormOffline) {

        try {

            showProgressDialog("fetching results...");


            Call<ResponseBody> call = getServices.GetDataForQuery(data);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response_q) {

                    dismissProgressDialog();

                    if (response_q.body() != null) {
                        String json = null;
                        try {
                            json = response_q.body().string();
                            Log.d(TAG, "getDataForQueryResponse: " + json);
                            JSONObject responseObj = new JSONObject(json);

                            if (responseObj.getString("Status").contentEquals("200")) {

/*                                if (isFormOffline) {

                                    if (improveDataBase.getCountByValue(ImproveDataBase.QUERY_DATA_TABLE, ImproveDataBase.QUERY_STRING, data.get("columnstring")) > 0) {

                                        improveDataBase.deleteRows(context, ImproveDataBase.QUERY_DATA_TABLE, ImproveDataBase.QUERY_STRING, data.get("columnstring"));
                                    }

                                    improveDataBase.insertintoTable(ImproveDataBase.QUERY_DATA_TABLE
                                            , new String[]{ImproveDataBase.DB_ORG_ID, ImproveDataBase.QUERY_FORM_NAME, ImproveDataBase.QUERY_STRING, ImproveDataBase.QUERY_FETCHED_DATA_STRING}
                                            , new String[]{orgId, appName, data.get("columnstring"), json});

                                    List<List<String>> data1 = improveDataBase.getTableColDataByCond(ImproveDataBase.QUERY_DATA_TABLE
                                            , ImproveDataBase.QUERY_FETCHED_DATA_STRING
                                            , new String[]{ImproveDataBase.QUERY_STRING}
                                            , new String[]{data.get("columnstring")});

                                    if (data1.size() > 0) {

                                        json = data1.get(0).get(0);
                                        findViewById(R.id.ct_no_data).setVisibility(View.GONE);
                                    }
                                }*/

                                findViewById(R.id.ct_no_data).setVisibility(View.GONE);
                                skipQueryActivity = cb_get_data.getVisibility() != View.VISIBLE;
                                response = json;
                                if (!isGPSSingleFilter) {
                                    iv_filter.setVisibility(View.VISIBLE);
                                } else {
                                    iv_filter.setVisibility(View.GONE);
                                }
                           /* hideFilterViews();
                            showIndexViews();*/
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dismissProgressDialog();
                                        loadIndexViews();
                                    }
                                }, 300);
                                dismissProgressDialog();
                                /*loadIndexViews();*/


                            } else {
                                dismissProgressDialog();
                                hideIndexViews();
                                if (!isGPSSingleFilter) {
                                    iv_filter.setVisibility(View.VISIBLE);
                                } else {
                                    iv_filter.setVisibility(View.GONE);
                                }
                                findViewById(R.id.ct_no_data).setVisibility(View.VISIBLE);
                                findViewById(R.id.ct_no_data).setTag(responseObj.getString("Message"));

//                                ImproveHelper.showToast(context, responseObj.getString("Message"));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "onResponse: " + json);
                    } else {
                        dismissProgressDialog();
                        ImproveHelper.showToast(context, "Response is null..");
                    }


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    dismissProgressDialog();

                    ImproveHelper.showToast(context, getString(R.string.server_busy_try_again));

                }
            });


        } catch (Exception e) {
            dismissProgressDialog();
            ImproveHelper.improveException(this, TAG, "getDataForQuery", e);
        }


    }

    private Map<String, String> dataForServerHit() {
        try {

            JSONObject tableDetails = new JSONObject();
            tableDetails.put("pagename", appName);
            tableDetails.put("orgid", new SessionManager(this).getOrgIdFromSession());
            tableDetails.put("query_type", query_type);

            JSONObject columnDetails = new JSONObject();
            JSONObject gpsDetails = new JSONObject();
            for (int j = 0; j < stringListSubmit.size(); j++) {

                if (stringListSubmit.get(j).split("\\|")[0].contains("_Coordinates")) {
                    String cName = stringListSubmit.get(j).split("\\|")[0];
                    gpsDetails.put(stringListSubmit.get(j).split("\\|")[0].substring(0, cName.lastIndexOf("_")), stringListSubmit.get(j).split("\\|")[1]);

                    for (int i = 0; i < listOfControls.size(); i++) {

                        if (listOfControls.get(i).getControlName().equalsIgnoreCase(stringListSubmit.get(j).split("\\|")[0].substring(0, cName.lastIndexOf("_")))) {
                            gpsDetails.put("value", listOfControls.get(i).getDistanceAround());
                            gpsDetails.put("Nearby", listOfControls.get(i).getNearBy());
                        }
                    }

                } else if (stringListSubmit.get(j).split("\\|")[0].contains("_Type")) {

                } else {
                    columnDetails.put(stringListSubmit.get(j).split("\\|")[0], stringListSubmit.get(j).split("\\|")[1]);

                }
            }

            if (gpsDetails.names() == null && gpsStringListSubmit.size() > 0) {
                for (int i = 0; i < gpsStringListSubmit.size(); i++) {
                    if (gpsStringListSubmit.get(i).split("\\|")[0].contains("_Coordinates")) {
                        //gpsDetails.put(gpsStringListSubmit.get(i).split("\\|")[0].split("_")[0], gpsStringListSubmit.get(i).split("\\|")[1]);
                        String cName = gpsStringListSubmit.get(i).split("\\|")[0];
                        gpsDetails.put(gpsStringListSubmit.get(i).split("\\|")[0].substring(0, cName.lastIndexOf("_")), gpsStringListSubmit.get(i).split("\\|")[1]);
                        for (int j = 0; j < listOfControls.size(); j++) {

                            if (listOfControls.get(j).getControlName().equalsIgnoreCase(gpsStringListSubmit.get(i).split("\\|")[0].substring(0, cName.lastIndexOf("_")))) {
                                gpsDetails.put("value", listOfControls.get(j).getDistanceAround());
                                gpsDetails.put("Nearby", listOfControls.get(j).getNearBy());
                            }

                        }
                    }
                }
            }

            Map<String, String> data = new HashMap<>();
            data.put("Jasonstring", tableDetails.toString());
            data.put("columnstring", columnDetails.toString());
            data.put("gpsstring", gpsDetails.toString());
            data.put("LazyLoading", "False");
            data.put("Threshold", "10");
            data.put("Range", "1-10");
            data.put("LazyOrderKey", "SELECT NULL");

            return data;

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "dataForServerHit", e);
        }

        return new HashMap<>();
    }

    private CustomTextView getTapToStart(LinearLayout ll_main) {
        int index = 0;
        CustomTextView customTextView = null;
        try {
            for (int i = 0; i < ll_main.getChildCount(); i++) {
                if (ll_main.getChildAt(i).getTag() != null && ll_main.getChildAt(i).getTag().equals("GPS")) {
                    index = i;
                    break;
                }
            }
            LinearLayout ll1 = (LinearLayout) ll_main.getChildAt(index);
            View view = ll1.getChildAt(0);
            customTextView = view.findViewById(R.id.tv_tap_to_start);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "getTapToStart", e);
        }
        return customTextView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GPS_ENABLE) {
            getTapToStart(ll_main_filters).performClick();
        }
    }

    @Override
    public void onLocCaptured(String gpsData) {
        gpsStringListSubmit.add(gpsControlName + "_Coordinates" + "|" + gpsData);
        gpsStringListSubmit.add(gpsControlName + "_Type" + "|" + gpsType);
        if (isGPSSingleFilter) {
            cb_get_data.performClick();
            iv_filter.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* if(skipQueryActivity){
            finish();
        }*/
    }

    private void setDefaultValue(ControlObject controlObject) {
        try {
            if (list_VariablesDataFromIntent.size() > 0) {
                for (int i = 0; i < list_VariablesDataFromIntent.size(); i++) {
                    if (controlObject.getControlName().contentEquals(list_VariablesDataFromIntent.get(i).getVariable_Name())) {
                        controlObject.setDefaultValue(list_VariablesDataFromIntent.get(i).getVariable_singleValue());
                        break;
                    }

                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "setDefaultValue", e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(appFromMultiform){
                    //onBackPressed();
                    if (currentMultiForm != null) {
                        LinkedHashMap<String, String> innerFormsDesignMap = currentMultiForm.getInnerFormsDesignMap();
                        if (currentMultiForm.getHome() != null) {
                            for (LinkedHashMap.Entry<String, String> entry : innerFormsDesignMap.entrySet()) {

                                if (entry.getKey().contentEquals(currentMultiForm.getHome())) {
                                    /*currentMultiForm.getNavigationList().add(new FormNavigation(currentMultiForm.getHome(), true), currentMultiForm.getHome());*/
                                    Intent intent = new Intent(context, MainActivity.class);
                                    intent.putExtra("s_app_design", entry.getValue());
                                    intent.putExtra("s_app_version", currentMultiForm.getAppVersion());
                                    intent.putExtra("s_app_type", "Datacollection");
                                    intent.putExtra("s_org_id", new SessionManager(context).getOrgIdFromSession());
                                    intent.putExtra("s_app_name", currentMultiForm.getHome());
                                    intent.putExtra("s_created_by", currentMultiForm.getCreateBy());
                                    intent.putExtra("s_user_id", new SessionManager(context).getUserDetailsFromSession().getUserId());
                                    intent.putExtra("s_distribution_id", currentMultiForm.getDistributionId());
                                    intent.putExtra("s_user_post_id", currentMultiForm.getPostId());

                                    context.startActivity(intent);
                                    finish();

                                    break;
                                }

                            }

                        }
                    }
                }else{
                    super.onBackPressed();
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();                if(appFromMultiform){
            //onBackPressed();
            if (currentMultiForm != null) {
                LinkedHashMap<String, String> innerFormsDesignMap = currentMultiForm.getInnerFormsDesignMap();
                if (currentMultiForm.getHome() != null) {
                    for (LinkedHashMap.Entry<String, String> entry : innerFormsDesignMap.entrySet()) {

                        if (entry.getKey().contentEquals(currentMultiForm.getHome())) {
                            /*currentMultiForm.getNavigationList().add(new FormNavigation(currentMultiForm.getHome(), true), currentMultiForm.getHome());*/
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("s_app_design", entry.getValue());
                            intent.putExtra("s_app_version", currentMultiForm.getAppVersion());
                            intent.putExtra("s_app_type", "Datacollection");
                            intent.putExtra("s_org_id", new SessionManager(context).getOrgIdFromSession());
                            intent.putExtra("s_app_name", currentMultiForm.getHome());
                            intent.putExtra("s_created_by", currentMultiForm.getCreateBy());
                            intent.putExtra("s_user_id", new SessionManager(context).getUserDetailsFromSession().getUserId());
                            intent.putExtra("s_distribution_id", currentMultiForm.getDistributionId());
                            intent.putExtra("s_user_post_id", currentMultiForm.getPostId());

                            context.startActivity(intent);
                            finish();

                            break;
                        }

                    }

                }
            }
        }else {
            super.onBackPressed();
        }
    }

    private class IndexColumnsAdapter extends RecyclerView.Adapter<IndexColumnsAdapter.ViewHolder> {
        private final String strControlTypeViewA;
        private final JSONArray array;
        JSONObject object = null;
        int layout_indexColumn;
        private boolean showGpsView;

        public IndexColumnsAdapter(JSONArray array, String strControlTypeViewA) {

            this.array = array;
            this.strControlTypeViewA = strControlTypeViewA;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = null;

            switch (strControlTypeViewA) {
                case AppConstants.INDEX_IMAGE_VIEW:
                    view = LayoutInflater.from(context).inflate(R.layout.row_item_index_grid, parent, false);
                    layout_indexColumn = R.layout.index_column_grid;
                    break;
                case AppConstants.INDEX_IMAGE_GPS_VIEW:
                    view = LayoutInflater.from(context).inflate(R.layout.row_item_index_grid, parent, false);
                    layout_indexColumn = R.layout.index_column_grid;
                    showGpsView = true;
                    break;
                case AppConstants.INDEX_GPS_VIEW:
                    view = LayoutInflater.from(context).inflate(R.layout.row_item_index, parent, false);
                    layout_indexColumn = R.layout.index_column;
                    showGpsView = true;
                    break;
                case AppConstants.INDEX_NORMAL_VIEW:
                    view = LayoutInflater.from(context).inflate(R.layout.row_item_index, parent, false);
                    layout_indexColumn = R.layout.index_column;
                    break;

            }
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            try {

                object = array.getJSONObject(position);
                String distance = "";
                LinearLayout linearLayout = holder.llIndexColumns;
                linearLayout.removeAllViews();
                holder.ctv_distance.setVisibility(View.GONE);
                LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                for (int i = 0; i < object.names().length(); i++) {
                    Log.d(TAG, "onBindViewHolderNames: " + object.names().toString());
                    View view = lInflater.inflate(layout_indexColumn, null);
                    CustomTextView columnName = view.findViewById(R.id.columnName);
                    columnName.setVisibility(View.GONE);
                    CustomTextView columnValue = view.findViewById(R.id.columnValue);

                    if (gpsColumnName != null && object.names().getString(i).contains(gpsColumnName) && object.names().getString(i).contains("_Coordinates")) {

                        gpsValues.add(object.getString(object.names().getString(i)));

                    }


                    String objectName = object.names().getString(i);

                    if (objectName.equalsIgnoreCase("Trans_id")) {
                        columnValue.setVisibility(View.GONE);

                        columnValue.setText(object.getString(object.names().getString(i)));
                        linearLayout.addView(view);

                    } else if (objectName.equalsIgnoreCase("ZZNear_by_distance")) {

                        if (distanceShown) {
                            holder.ctv_distance.setVisibility(View.VISIBLE);
                            distance = object.getString(object.names().getString(i));
                            holder.ctv_distance.setText(distance + " Kms");
                            columnValue.setVisibility(View.GONE);
                        } else {
                            holder.ctv_distance.setVisibility(View.GONE);
                        }

                        columnValue.setText(object.getString(object.names().getString(i)));
                        linearLayout.addView(view);

                    } else {
                        if (List_Index_Columns.contains(objectName)) {

                            if (strControlTypeViewA.equalsIgnoreCase(AppConstants.INDEX_IMAGE_VIEW)
                                    || strControlTypeViewA.equalsIgnoreCase(AppConstants.INDEX_IMAGE_GPS_VIEW)) {

                                if (showGpsView) {
                                    bottom_layout.setVisibility(View.VISIBLE);
                                } else {
                                    bottom_layout.setVisibility(View.GONE);
                                }
                                if (List_Index_Columns.indexOf(objectName) == 0 && !object.getString(object.names().getString(i)).startsWith("http")) {

                                    holder.ctv_MainHead.setText(object.getString(object.names().getString(i)));

                                    columnValue.setVisibility(View.GONE);
                                } else if (object.getString(object.names().getString(i)).startsWith("http")) {
                                    holder.ctv_MainHead.setVisibility(View.GONE);
                                    columnValue.setVisibility(View.GONE);
                                }
                                if (object.getString(object.names().getString(i)).startsWith("http")) {
                                    Glide.with(view.getContext()).load(object.getString(object.names().getString(i)))
                                            .placeholder(R.drawable.icons_image_update)
                                            .dontAnimate().into(holder.iv_imageControl);
                                }
//                                columnValue.setVisibility(View.GONE);


                            } else if (strControlTypeViewA.equalsIgnoreCase(AppConstants.INDEX_GPS_VIEW)
                                    || strControlTypeViewA.equalsIgnoreCase(AppConstants.INDEX_NORMAL_VIEW)) {
                                if (showGpsView) {
                                    bottom_layout.setVisibility(View.VISIBLE);
                                } else {
                                    bottom_layout.setVisibility(View.GONE);
                                }
                                if (List_Index_Columns.indexOf(objectName) == 0) {
                                    holder.ctv_MainHead.setText(object.getString(object.names().getString(i)));
                                    columnValue.setVisibility(View.GONE);
                                }
                            }
                            columnValue.setText(object.getString(object.names().getString(i)));
                            linearLayout.addView(view);
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return array.length();
        }

        private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ImageView iv_imageControl;
            LinearLayout llIndexColumns, ll_queryCheckImage;
            CustomTextView ctv_MainHead, ctv_distance;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                llIndexColumns = itemView.findViewById(R.id.llIndexColumns);
                ll_queryCheckImage = itemView.findViewById(R.id.ll_queryCheckImage);
                if (strControlTypeViewA.equalsIgnoreCase(AppConstants.INDEX_GPS_VIEW)
                        || strControlTypeViewA.equalsIgnoreCase(AppConstants.INDEX_NORMAL_VIEW)) {
                    iv_imageControl = itemView.findViewById(R.id.iv_imageControl);
                    ctv_MainHead = itemView.findViewById(R.id.ctv_MainHead);
                    ctv_distance = itemView.findViewById(R.id.ctv_distance);
                    if (!showGpsView) {
                        ctv_MainHead = itemView.findViewById(R.id.ctv_MainHead);
                    }
                } else if (strControlTypeViewA.equalsIgnoreCase(AppConstants.INDEX_IMAGE_VIEW)
                        || strControlTypeViewA.equalsIgnoreCase(AppConstants.INDEX_IMAGE_GPS_VIEW)) {
                    iv_imageControl = itemView.findViewById(R.id.iv_imageControl);
                    ctv_MainHead = itemView.findViewById(R.id.ctv_MainHead);
                    ctv_distance = itemView.findViewById(R.id.ctv_distance);
                    iv_imageControl.setVisibility(View.VISIBLE);
                    ll_queryCheckImage.setVisibility(View.VISIBLE);
                    iv_imageControl.setOnClickListener(this);
                    ll_queryCheckImage.setOnClickListener(this);
                }


                ctv_MainHead.setOnClickListener(this);
                itemView.setOnClickListener(this);
                llIndexColumns.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
//                 Jay prem
                Log.d(TAG, "onBindViewHolderClick" + "onBindViewHolderClick");
                try {
                    object = array.getJSONObject(getAdapterPosition());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (strChildName != null && strChildName.equals("ChildForm")) {
                    Log.d(TAG, "onBindViewHolderClick" + "onBindViewHolderClickChild");
//                            Toast.makeText(getApplicationContext(),"Child",Toast.LENGTH_SHORT).show();

                    Intent in = new Intent(context, MainActivity.class);

                    in.putExtra("jsonObject", object.toString());

                    in.putExtra("create_query_object", create_query_object);

                    in.putExtra("appName", appName);

                    in.putExtra("s_childForm", strChildName);
                    in.putExtra("app_edit", "child");
                    in.putExtra("s_app_type", "child");

                    context.startActivity(in);

                } else {
                    Intent in = new Intent(context, QueryDetailsActivity.class);
                    Log.d(TAG, "onBindViewHolderClick" + "onBindViewHolderClickQuery");

                    in.putExtra("jsonObject", object.toString());

                    in.putExtra("create_query_object", create_query_object);

                    in.putExtra("appName", appName);

                    context.startActivity(in);

                }
            }
        }
    }
}
