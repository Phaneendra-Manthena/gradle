package com.bhargo.user.Query;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.Create_Query_Object;
import com.bhargo.user.Java_Beans.GetAPIDetails_Bean;
import com.bhargo.user.Java_Beans.QueryFilterField_Bean;
import com.bhargo.user.Java_Beans.QuerySelectField_Bean;
import com.bhargo.user.Java_Beans.Variable_Bean;
import com.bhargo.user.R;
import com.bhargo.user.controls.advanced.Gps_Control;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.screens.BottomNavigationActivity;
import com.bhargo.user.screens.LoadForm;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.QueryFormControlsValidation;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SK_RestCall_WCF;
import com.bhargo.user.utils.SK_RestCall_WebAPI;
import com.bhargo.user.utils.SK_ServiceCall;
import com.bhargo.user.utils.SK_WebAPI_interpreterQueryBased;
import com.bhargo.user.utils.SK_WebAPI_interpreter;
import com.bhargo.user.utils.SessionManager;
import com.bhargo.user.utils.XMLHelper;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.utils.AppConstants.GlobalObjects;
import static com.bhargo.user.utils.AppConstants.REQUEST_GPS_ENABLE;
import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;


public class QueryGetDataActivity extends BaseActivity implements Gps_Control.LocCaptured {

    public static final String TAG = "QueryGetDataActivity";
    private static final int FILE_BROWSER_RESULT_CODE = 554;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 666;
    public TextView title;
    public ImageView ib_settings;
    String query_type = "";
    ProgressDialog Sk_serviceDialog;
    SK_ServiceCall sk_soapobj;
    SK_RestCall_WCF sk_Restobj_WCF;
    SK_RestCall_WebAPI sk_Restobj;
    SK_WebAPI_interpreterQueryBased sk_Rest_interpreterQuery;
    SK_WebAPI_interpreter sk_Rest_interpreter;
    private String designFormat;
    private Create_Query_Object create_query_object;
    private XMLHelper xmlHelper;
    private LinearLayout ll_main;
    private List<ControlObject> listOfControls;
    private String strUserLocationStructure;
    private String appName, strChildForm;
    private String orgId, userId;
    private String distributionId;
    private List<String> stringListSubmit = new ArrayList<>();
    private final List<String> gpsStringListSubmit = new ArrayList<>();
    private final List<String> stringListGPS = new ArrayList<>();
    private Context context;
    private CustomButton cb_get_data;
    private GetServices getServices;
    private LoadForm loadForm;
    private ImproveDataBase improveDataBase;
    private String sourceType;
    private String sourceFormType;
    private String sorceForm;
    private List<QuerySelectField_Bean> List_DisplayFields = new ArrayList<>();
    private Toolbar toolbar;
    private String gpsControlName;
    private String gpsType;
    private boolean skipQueryActivity;
    private boolean isGPSSingleFilter;
    //private LinkedHashMap<String,>

    public  List<Variable_Bean> list_Variables=new ArrayList<Variable_Bean>();
    public  List<Variable_Bean> list_VariablesDataFromIntent=new ArrayList<Variable_Bean>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Theme
        setBhargoTheme(this,AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_get_data);
        initializeActionBar();

        Intent getIntent = getIntent();
        if (getIntent != null) {

            strUserLocationStructure = getIntent.getStringExtra("s_user_location_Structure");
            designFormat = getIntent().getStringExtra("s_design_format");
            appName = getIntent.getStringExtra("s_app_name");
            orgId = getIntent.getStringExtra("s_org_id");
            distributionId = getIntent.getStringExtra("s_distribution_id");

            if (getIntent.getStringExtra("s_childForm") != null && (getIntent.getStringExtra("s_childForm").equalsIgnoreCase("ChildForm"))) {
                strChildForm = getIntent.getStringExtra("s_childForm");
            }

            if(getIntent() != null && getIntent.hasExtra("VariablesData")) {
                Bundle Variables=getIntent.getBundleExtra("VariablesData");
                list_VariablesDataFromIntent=(List<Variable_Bean>) Variables.getSerializable("Variables");
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
        list_Variables=create_query_object.getList_Varibles();
        if(list_VariablesDataFromIntent.size()>0){
            for (int i = 0; i <list_VariablesDataFromIntent.size() ; i++) {
                for (int j = 0; j <list_Variables.size() ; j++) {
                    if(list_Variables.get(j).getVariable_Name().equalsIgnoreCase(list_VariablesDataFromIntent.get(i).getVariable_Name())){
                        list_Variables.set(j,list_VariablesDataFromIntent.get(i));
                    }
                }
            }
        }


        initViews();

    }

    private void initViews() {

        context = QueryGetDataActivity.this;

        improveDataBase = new ImproveDataBase(context);

        getServices = RetrofitUtils.getUserService();

        ll_main = findViewById(R.id.ll_main);

        cb_get_data = findViewById(R.id.cb_get_data);

        cb_get_data.setVisibility(View.GONE);

        listOfControls = new ArrayList<>();

        cb_get_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (formValidated(listOfControls)) {

                    if (sourceFormType.equalsIgnoreCase("API")) {
                        Map<String, String> data = new HashMap<>();
                        data.put("OrgId", AppConstants.GlobalObjects.getOrg_Name());
                        data.put("loginID", GlobalObjects.getUser_ID());
                        data.put("APIName", sorceForm);

                        Call<GetAPIDetails_Bean> call = getServices.GetAPIDetailsNew(data);
//                        Call<GetAPIDetails_Bean> call = getServices.GetAPIDetailsNew(AppConstants.GlobalObjects.getOrg_Name(),GlobalObjects.getUser_ID(),sorceForm);

                        call.enqueue(new Callback<GetAPIDetails_Bean>() {
                            @Override
                            public void onResponse(Call<GetAPIDetails_Bean> call, Response<GetAPIDetails_Bean> response) {
                                GetAPIDetails_Bean GetAPIDetails = response.body();
                                GetAPIDetails_Bean.APIDetails APIDetails = GetAPIDetails.getServiceData();
                                if (GetAPIDetails.getStatus().equalsIgnoreCase("$200-")) {

                                    Map<String, String> columnMap = new HashMap<>();
                                    Map<String, String> HeaderMap = new HashMap<>();
                                    JSONObject columnDetails = new JSONObject();

                                    try {
                                        for (int j = 0; j < stringListSubmit.size(); j++) {

                                            if (stringListSubmit.get(j).split("\\|")[0].contains("_Coordinates")) {

                                                columnDetails.put(stringListSubmit.get(j).split("\\|")[0].split("_")[0], stringListSubmit.get(j).split("\\|")[1]);
                                                columnMap.put(stringListSubmit.get(j).split("\\|")[0].split("_")[0], stringListSubmit.get(j).split("\\|")[1]);

                                                for (int i = 0; i < listOfControls.size(); i++) {

                                                    if (listOfControls.get(i).getControlName().equalsIgnoreCase(stringListSubmit.get(j).split("\\|")[0].split("_")[0])) {
                                                        columnDetails.put("value", listOfControls.get(i).getDistanceAround());
                                                        columnDetails.put("Nearby", listOfControls.get(i).getNearBy());

                                                        columnMap.put("value", listOfControls.get(i).getDistanceAround());
                                                        columnMap.put("Nearby", listOfControls.get(i).getNearBy());
                                                    }

                                                }


                                            } else if (stringListSubmit.get(j).split("\\|")[0].contains("_Type")) {

                                            } else {
                                                columnDetails.put(stringListSubmit.get(j).split("\\|")[0], stringListSubmit.get(j).split("\\|")[1]);
                                                columnMap.put(stringListSubmit.get(j).split("\\|")[0], stringListSubmit.get(j).split("\\|")[1]);

                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    Sk_serviceDialog = new ProgressDialog(QueryGetDataActivity.this);
                                    Sk_serviceDialog.setMessage("Please Wait...  Loading " + APIDetails.getServiceName() + "...");
                                    Sk_serviceDialog.setCancelable(false);
                                    Sk_serviceDialog.setOnDismissListener(new API_DilogDismiss(APIDetails, APIDetails.getServiceType().trim(), APIDetails.getServiceResult(), true,
                                            APIDetails.getServiceSource()));
                                    Sk_serviceDialog.show();
                                    String ServiceURl = "";
                                    if (APIDetails.getServiceSource().equalsIgnoreCase("Service Based")) {

                                        if (APIDetails.getServiceCallsAt().equalsIgnoreCase("Server")) {

                                            try {
                                                JSONObject objectMapper = new JSONObject(columnMap);

                                                Map<String, String> ServerMap = new HashMap<>();
                                                ServerMap.put("orgID", GlobalObjects.getOrg_Name());
                                                ServerMap.put("loginID", GlobalObjects.getUser_ID());
                                                ServerMap.put("stringURL", APIDetails.getServiceURl());
                                                ServerMap.put("serviceType", APIDetails.getServiceType());
                                                ServerMap.put("serviceMethod", APIDetails.getMethodName());
                                                ServerMap.put("inputParameters", objectMapper.toString());
                                                ServerMap.put("authenticationParameters", "");
                                                ServerMap.put("outputType", APIDetails.getOutputType());
                                                ServerMap.put("serviceSource", "1");
                                                ServerMap.put("methodType", APIDetails.getMethodType());
                                                sk_Rest_interpreter = new SK_WebAPI_interpreter();
                                                sk_Rest_interpreter.CallSoap_Service(false,APIDetails.getNameSpace(), ServerMap,
                                                        ImproveHelper.getOutputParams(APIDetails.getSuccessCaseDetails()),
                                                        APIDetails.getOutputType(), APIDetails.getServiceResult(), Sk_serviceDialog,
                                                        ImproveHelper.getOutputParamswithPaths(APIDetails.getSuccessCaseDetails()),
                                                        ImproveHelper.gethdOutputSaveFlow(APIDetails.getSuccessCaseDetails()),
                                                        APIDetails.getServiceSource(),APIDetails.getQueryType(),"");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } else {

                                            ServiceURl = APIDetails.getServiceURl();

                                            if (APIDetails.getServiceType().trim().equalsIgnoreCase("Soap Web Service")) {

                                                sk_soapobj = new SK_ServiceCall();

                                                sk_soapobj.CallSoap_Service(ServiceURl, APIDetails.getMethodName(), APIDetails.getNameSpace(), columnMap,
                                                        Arrays.asList(APIDetails.getOutputParameters().split("\\,")), APIDetails.getOutputType(), APIDetails.getServiceResult(), Sk_serviceDialog,
                                                        null,"","","");

                                            } else if (APIDetails.getServiceType().trim().equalsIgnoreCase("WCF Service")) {
                                                sk_Restobj_WCF = new SK_RestCall_WCF();

                                                sk_Restobj_WCF.CallSoap_Service(ServiceURl, APIDetails.getNameSpace(), columnMap,
                                                        Arrays.asList(APIDetails.getOutputParameters().split("\\,")), APIDetails.getOutputType(),
                                                        APIDetails.getServiceResult(), Sk_serviceDialog, APIDetails.getMethodType(),null,"","","","");

                                            } else {

                                                sk_Restobj = new SK_RestCall_WebAPI();

                                                sk_Restobj.CallSoap_Service(ServiceURl, APIDetails.getNameSpace(), columnMap,null,
                                                        Arrays.asList(APIDetails.getOutputParameters().split("\\,")), APIDetails.getOutputType(),
                                                        APIDetails.getServiceResult(), Sk_serviceDialog, APIDetails.getMethodType(),null,"","","",null);

                                            }

                                        }

                                    } else if (APIDetails.getServiceSource().equalsIgnoreCase("Query Based")) {

                                        try {

                                            JSONArray Jarr = new JSONArray();
                                            for (String key : columnMap.keySet()) {
                                                try {
                                                    JSONObject objectMapper = new JSONObject();
                                                    objectMapper.put("Key", key);
                                                    objectMapper.put("Value", columnMap.get(key));
                                                    Jarr.put(objectMapper);
                                                } catch (JSONException e) {
                                                    System.out.println("Error At Query Based i events " + e.toString());
                                                }
                                            }

                                            Map<String, String> ServerMap = new HashMap<>();
                                            ServerMap.put("orgID", GlobalObjects.getOrg_Name());
                                            ServerMap.put("loginID", GlobalObjects.getUser_ID());
                                            ServerMap.put("queryName", APIDetails.getMethodName());
                                            ServerMap.put("serviceSource", "2");
                                            ServerMap.put("serviceType", APIDetails.getServiceType());
                                            ServerMap.put("inputParameters", Jarr.toString());
                                            ServerMap.put("outputType", APIDetails.getOutputType());

                                            sk_Rest_interpreterQuery = new SK_WebAPI_interpreterQueryBased();
                                            sk_Rest_interpreterQuery.CallSoap_Service(APIDetails.getNameSpace(), ServerMap,
                                                    Arrays.asList(APIDetails.getOutputParameters().split("\\,")), APIDetails.getOutputType(), APIDetails.getServiceResult(), Sk_serviceDialog,"");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            }

                            @Override
                            public void onFailure(Call<GetAPIDetails_Bean> call, Throwable t) {

                            }
                        });

                    }
                    else {
                        if (create_query_object.isQuery_OffLine()) {

                            if (ImproveHelper.isNetworkStatusAvialable(context)) {

                                if (sourceType.equalsIgnoreCase("APIOrQueryBased")) {
                                    getDataForQueryBased(dataForServerHitQueryBased(), true);
                                } else {
                                    getDataForQuery(dataForServerHit(), true);
                                }


                            } else {

                                if (sourceType.equalsIgnoreCase("APIOrQueryBased")) {
                                    if (improveDataBase.getCountByValue(ImproveDataBase.QUERY_DATA_TABLE, ImproveDataBase.QUERY_STRING, dataForServerHitQueryBased().get("paramstr")) > 0) {
                                        String json = "";

                                        List<List<String>> data = improveDataBase.getTableColDataByCond(ImproveDataBase.QUERY_DATA_TABLE
                                                , ImproveDataBase.QUERY_FETCHED_DATA_STRING
                                                , new String[]{ImproveDataBase.QUERY_STRING}
                                                , new String[]{dataForServerHit().get("paramstr")});

                                        if (data.size() > 0) {

                                            json = data.get(0).get(0);

                                            skipQueryActivity = cb_get_data.getVisibility() != View.VISIBLE;

//                                            Intent in = new Intent(context, QueryIndexColumnsActivity.class);
                                            Intent in = new Intent(context, QueryIndexColumnsGridActivity.class);
                                            in.putExtra("response", json);
                                            in.putExtra("create_query_object", create_query_object);
                                            in.putExtra("appName", appName);
                                            in.putExtra("s_childForm", strChildForm);


                                            startActivity(in);
//                                        finish();

                                        } else {
                                            ImproveHelper.showToast(QueryGetDataActivity.this, getString(R.string.no_records));
                                        }
                                    } else {
                                        ImproveHelper.showToast(QueryGetDataActivity.this, getString(R.string.no_records));
                                    }
                                }
                                else {
                                    if (improveDataBase.getCountByValue(ImproveDataBase.QUERY_DATA_TABLE, ImproveDataBase.QUERY_STRING, dataForServerHit().get("columnstring")) > 0) {

                                        String json = "";

                                        List<List<String>> data = improveDataBase.getTableColDataByCond(ImproveDataBase.QUERY_DATA_TABLE
                                                , ImproveDataBase.QUERY_FETCHED_DATA_STRING
                                                , new String[]{ImproveDataBase.QUERY_STRING}
                                                , new String[]{dataForServerHit().get("columnstring")});

                                        if (data.size() > 0) {

                                            json = data.get(0).get(0);

                                            skipQueryActivity = cb_get_data.getVisibility() != View.VISIBLE;
//                                            Intent in = new Intent(context, QueryIndexColumnsActivity.class);
                                            Intent in = new Intent(context, QueryIndexColumnsGridActivity.class);
                                            in.putExtra("response", json);
                                            in.putExtra("create_query_object", create_query_object);
                                            in.putExtra("appName", appName);
                                            in.putExtra("s_childForm", strChildForm);


                                            startActivity(in);
//                                        finish();

                                        } else {
                                            ImproveHelper.showToast(QueryGetDataActivity.this, getString(R.string.no_records));
                                        }


                                    } else {
                                        ImproveHelper.showToast(QueryGetDataActivity.this, getString(R.string.no_records));
                                    }
                                }


                            }

                        }
                        else {

                            if (ImproveHelper.isNetworkStatusAvialable(context)) {
                                if (sourceType.equalsIgnoreCase("APIOrQueryBased")) {
                                    getDataForQueryBased(dataForServerHitQueryBased(), true);
                                } else {
                                    getDataForQuery(dataForServerHit(), true);
                                }
                                //getDataForQuery(dataForServerHit(),false);
                            } else {
                                ImproveHelper.showToast(QueryGetDataActivity.this, getString(R.string.pls_check_network));
                            }

                        }
                    }


                }


            }
        });

        List<QueryFilterField_Bean> listOfQueryFilters = create_query_object.getList_FormAPIQuery_FilterFields();

        if (listOfQueryFilters.size() > 0) {

            if(listOfQueryFilters.size()==1
                    && ((listOfQueryFilters.get(0).getExist_Field_ControlObject()!=null && listOfQueryFilters.get(0).getExist_Field_ControlObject()
                    .getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS))||(listOfQueryFilters.get(0).getField_ControlObject()!=null && listOfQueryFilters.get(0).getField_ControlObject()
                    .getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)))){
                cb_get_data.setVisibility(View.GONE);

                ControlObject controlObject = listOfQueryFilters.get(0).getExist_Field_ControlObject();
                controlObject.setOnChangeEventExists(false);
                controlObject.setOnFocusEventExists(false);
                controlObject.setLocationMode(AppConstants.LOCATION_MODE_NETWORK);
                controlObject.setGpsType(AppConstants.Single_Point_GPS);

                gpsControlName = controlObject.getControlName();
                gpsType = controlObject.getGpsType();
                listOfControls.add(controlObject);

                loadForm = new LoadForm(this, ll_main,true);

                loadForm.load(listOfControls);

                ll_main.setVisibility(View.GONE);

                query_type = "gps";

                isGPSSingleFilter = true;

                CustomTextView tapToStart =getTapToStart(ll_main);

                //showProgressDialog("getting location...");
                tapToStart.performClick();

            }else if(listOfQueryFilters.size()==1 && listOfQueryFilters.get(0).getField_Static_Value()!=null){

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

                    listOfControls.add(controlObject);

                    if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                        query_type = "gps";
                    }

                }

                loadForm = new LoadForm(this, ll_main,true);

                loadForm.load(listOfControls);

                cb_get_data.performClick();

            }else {

                cb_get_data.setVisibility(View.VISIBLE);

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

                    listOfControls.add(controlObject);

                    if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                        gpsControlName = controlObject.getControlName();
                        gpsType = controlObject.getGpsType();
                        query_type = "gps";
                    }

                }

                loadForm = new LoadForm(this, ll_main,true);

                loadForm.load(listOfControls);

                isGPSSingleFilter = false;

                if(query_type.equalsIgnoreCase("gps")){
                    CustomTextView tapToStart =getTapToStart(ll_main);

                    tapToStart.performClick();
                }


            }
        }
        else {


            cb_get_data.setVisibility(View.GONE);

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

//                        Intent in = new Intent(context, QueryIndexColumnsActivity.class);
                        Intent in = new Intent(context, QueryIndexColumnsGridActivity.class);
                        in.putExtra("response", json);
                        in.putExtra("create_query_object", create_query_object);
                        in.putExtra("appName", appName);
                        in.putExtra("s_childForm", strChildForm);

                        startActivity(in);
//                        finish();

                    } else {
                        ImproveHelper.showToast(QueryGetDataActivity.this, getString(R.string.no_records));
                    }


                } else {
                    ImproveHelper.showToast(QueryGetDataActivity.this, getString(R.string.no_records));
                }
            }
        }

    }



    private Map<String, String> dataForServerHitQueryBased() {
        try {

            JSONObject tableDetails = new JSONObject();
            tableDetails.put("queryname", sorceForm);
            tableDetails.put("orgid", new SessionManager(this).getOrgIdFromSession());
            tableDetails.put("query_type", query_type);

            JSONObject columnDetails = new JSONObject();
            JSONArray paramsArray = new JSONArray();

            for (int i = 0; i < List_DisplayFields.size(); i++) {

                QuerySelectField_Bean displayFied = List_DisplayFields.get(i);

                columnDetails.put("C" + (i + 1), displayFied.getField_Name());


            }

            for (int j = 0; j < stringListSubmit.size(); j++) {

                JSONObject paramsObj = new JSONObject();

                paramsObj.put("Key", stringListSubmit.get(j).split("\\|")[0]);
                paramsObj.put("Value", stringListSubmit.get(j).split("\\|")[1]);

                paramsArray.put(paramsObj);


            }


            Map<String, String> data = new HashMap<>();
            data.put("Jasonstring", tableDetails.toString());
            data.put("columnstring", columnDetails.toString());
            data.put("paramstr", paramsArray.toString());


            return data;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
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
                    gpsDetails.put(stringListSubmit.get(j).split("\\|")[0].substring(0,cName.lastIndexOf("_")), stringListSubmit.get(j).split("\\|")[1]);

                    for (int i = 0; i < listOfControls.size(); i++) {

                        if (listOfControls.get(i).getControlName().equalsIgnoreCase(stringListSubmit.get(j).split("\\|")[0].substring(0,cName.lastIndexOf("_")))) {
                            gpsDetails.put("value", listOfControls.get(i).getDistanceAround());
                            gpsDetails.put("Nearby", listOfControls.get(i).getNearBy());
                        }

                    }


                } else if (stringListSubmit.get(j).split("\\|")[0].contains("_Type")) {

                } else {
                    columnDetails.put(stringListSubmit.get(j).split("\\|")[0], stringListSubmit.get(j).split("\\|")[1]);

                }
            }

            if(gpsDetails.names()==null&&gpsStringListSubmit.size()>0){
                for (int i = 0; i <gpsStringListSubmit.size() ; i++) {
                    if (gpsStringListSubmit.get(i).split("\\|")[0].contains("_Coordinates")) {
                        //gpsDetails.put(gpsStringListSubmit.get(i).split("\\|")[0].split("_")[0], gpsStringListSubmit.get(i).split("\\|")[1]);
                        String cName = gpsStringListSubmit.get(i).split("\\|")[0];
                        gpsDetails.put(gpsStringListSubmit.get(i).split("\\|")[0].substring(0,cName.lastIndexOf("_")), gpsStringListSubmit.get(i).split("\\|")[1]);
                        for (int j = 0; j < listOfControls.size(); j++) {

                            if (listOfControls.get(j).getControlName().equalsIgnoreCase(gpsStringListSubmit.get(i).split("\\|")[0].substring(0,cName.lastIndexOf("_")))) {
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


            return data;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    private void getDataForQueryBased(final Map<String, String> data, final boolean isFormOffline) {
        try {
            showProgressDialog("fetching results...");


            Call<ResponseBody> call = getServices.GetDataForQueryBased(data);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    dismissProgressDialog();
                    String json = null;
                    try {
                        json = response.body().string();
                        Log.d(TAG, "getDataForQueryBasedResponse: " + json);
                        JSONObject responseObj = new JSONObject(json);

                        if (responseObj.getString("Status").contentEquals("200")) {

                            if (isFormOffline) {

                                if (improveDataBase.getCountByValue(ImproveDataBase.QUERY_DATA_TABLE, ImproveDataBase.QUERY_STRING, data.get("columnstring")) > 0) {

                                    improveDataBase.deleteRows(QueryGetDataActivity.this, ImproveDataBase.QUERY_DATA_TABLE, ImproveDataBase.QUERY_STRING, data.get("columnstring"));
                                }


                                improveDataBase.insertintoTable(ImproveDataBase.QUERY_DATA_TABLE
                                        , new String[]{ImproveDataBase.DB_ORG_ID, ImproveDataBase.QUERY_FORM_NAME, ImproveDataBase.QUERY_STRING, ImproveDataBase.QUERY_FETCHED_DATA_STRING}
                                        , new String[]{orgId, appName, data.get("paramstr"), json});

                                List<List<String>> data1 = improveDataBase.getTableColDataByCond(ImproveDataBase.QUERY_DATA_TABLE
                                        , ImproveDataBase.QUERY_FETCHED_DATA_STRING
                                        , new String[]{ImproveDataBase.QUERY_STRING}
                                        , new String[]{data.get("paramstr")});

                                if (data1.size() > 0) {

                                    json = data1.get(0).get(0);
                                }


                            }

                            skipQueryActivity = cb_get_data.getVisibility() != View.VISIBLE;

//                            Intent in = new Intent(context, QueryIndexColumnsActivity.class);
                            Intent in = new Intent(context, QueryIndexColumnsGridActivity.class);
                            in.putExtra("response", json);
                            in.putExtra("create_query_object", create_query_object);
                            in.putExtra("appName", appName);
                            in.putExtra("s_childForm", strChildForm);

                            startActivity(in);
//                            finish();

                        } else {

                            ImproveHelper.showToast(context, responseObj.getString("Message"));

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Log.d(TAG, "onResponse: " + json);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    ImproveHelper.showToast(context, getString(R.string.server_busy_try_again));

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDataForQuery(final Map<String, String> data, final boolean isFormOffline) {

        try {

            showProgressDialog("fetching results...");


            Call<ResponseBody> call = getServices.GetDataForQuery(data);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    dismissProgressDialog();

                    String json = null;
                    try {
                        json = response.body().string();
                        Log.d(TAG, "getDataForQueryResponse: " + json);
                        JSONObject responseObj = new JSONObject(json);

                        if (responseObj.getString("Status").contentEquals("200")) {

                            if (isFormOffline) {

                                if (improveDataBase.getCountByValue(ImproveDataBase.QUERY_DATA_TABLE, ImproveDataBase.QUERY_STRING, data.get("columnstring")) > 0) {

                                    improveDataBase.deleteRows(QueryGetDataActivity.this, ImproveDataBase.QUERY_DATA_TABLE, ImproveDataBase.QUERY_STRING, data.get("columnstring"));
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
                                }
                            }


                            skipQueryActivity = cb_get_data.getVisibility() != View.VISIBLE;

//                            Intent in = new Intent(context, QueryIndexColumnsActivity.class);
                            Intent in = new Intent(context, QueryIndexColumnsGridActivity.class);
                            in.putExtra("response", json);
                            in.putExtra("create_query_object", create_query_object);
                            in.putExtra("appName", appName);
                            in.putExtra("s_childForm", strChildForm);


                            startActivity(in);
//                            finish();

                        } else {

                            ImproveHelper.showToast(context, responseObj.getString("Message"));
                            finish();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Log.d(TAG, "onResponse: " + json);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    ImproveHelper.showToast(context, getString(R.string.server_busy_try_again));

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private boolean formValidated(List<ControlObject> list_Control) {
        boolean flag = true;

        QueryFormControlsValidation controlsValidation = new QueryFormControlsValidation(context, loadForm.getList_ControlClassObjects(),null);

        for (int i = 0; i < list_Control.size(); i++) {
            Log.d(TAG, "XmlHelperTextInput: " + list_Control.get(i).getControlType());

            flag = controlsValidation.controlSubmitValidation(list_Control.get(i), false, null, null, "0", 0, -1);

            if (!flag) {
                break;
            }
        }

        stringListSubmit = controlsValidation.getDataCollectionString();

        return flag;
    }

    public void initializeActionBar() {

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

    }


    public void onSearchCalled() {
        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields).setCountry("IN")
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    private void convertOuputData(LinkedHashMap<String, List<String>> outputData) {


        List<String> keyList = new ArrayList<>();
        List<List<String>> valueList = new ArrayList<>();

        JSONObject mainObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        try {
            for (Map.Entry<String, List<String>> entry : outputData.entrySet()) {
                String key = entry.getKey();
                List<String> values = entry.getValue();

                keyList.add(key);

                valueList.add(values);
            }

            for (int i = 0; i < valueList.get(0).size(); i++) {

                JSONObject jsonObject = new JSONObject();

                for (int j = 0; j < keyList.size(); j++) {

                    jsonObject.put(keyList.get(j), valueList.get(j).get(i));

                }

                jsonArray.put(jsonObject);

            }

            mainObject.put("output", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        skipQueryActivity = cb_get_data.getVisibility() != View.VISIBLE;

//        Intent in = new Intent(context, QueryIndexColumnsActivity.class);
        Intent in = new Intent(context, QueryIndexColumnsGridActivity.class);
        in.putExtra("response", mainObject.toString());
        in.putExtra("create_query_object", create_query_object);
        in.putExtra("appName", appName);
        in.putExtra("s_childForm", strChildForm);
        startActivity(in);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(QueryGetDataActivity.this, BottomNavigationActivity.class);
        if (PrefManger.getSharedPreferencesString(QueryGetDataActivity.this, AppConstants.Notification_Back_Press, "") != null) {
            String onBackFrom = PrefManger.getSharedPreferencesString(QueryGetDataActivity.this, AppConstants.Notification_Back_Press, "");
            Log.d("NotificationBPM", onBackFrom);
            if (onBackFrom.equalsIgnoreCase("1")) {
                intent.putExtra("NotifRefreshAppsList", "1");
            } else if (onBackFrom.equalsIgnoreCase("0")) {
                intent.putExtra("NotifRefreshAppsList", "0");
            } else {
                intent.putExtra("NotifRefreshAppsList", "0");
            }
        }
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                onSearchCalled();
                return true;
            case android.R.id.home:
                hideKeyboard(QueryGetDataActivity.this, view);
                finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onLocCaptured(String gpsData) {
        gpsStringListSubmit.add(gpsControlName + "_Coordinates" + "|" + gpsData);
        gpsStringListSubmit.add(gpsControlName + "_Type" + "|" + gpsType);
        if(isGPSSingleFilter) {
            cb_get_data.performClick();
        }
    }


    class API_DilogDismiss implements DialogInterface.OnDismissListener {
        String ServiceType, ServiceResult, ServiceSource;
        boolean Single;
        GetAPIDetails_Bean.APIDetails APIDetails;

        public API_DilogDismiss(GetAPIDetails_Bean.APIDetails APIDetails, String ServiceType, String ServiceResult,
                                boolean Single, String ServiceSource) {
            this.ServiceType = ServiceType;
            this.ServiceResult = ServiceResult;
            this.Single = Single;
            this.ServiceSource = ServiceSource;
            this.APIDetails = APIDetails;
        }

        @Override
        public void onDismiss(DialogInterface dialogInterface) {
            try {

                LinkedHashMap<String, List<String>> OutputData;

                if (ServiceSource.equalsIgnoreCase("Service Based")) {
                    if (APIDetails.getServiceCallsAt().equalsIgnoreCase("Server")) {
                        OutputData = sk_Rest_interpreter.OutputData;
                    } else {
                        if (ServiceType.trim().equalsIgnoreCase("Soap Web Service")) {
                            OutputData = sk_soapobj.OutputData;
                        } else if (ServiceType.trim().equalsIgnoreCase("WCF Service")) {
                            OutputData = sk_Restobj_WCF.OutputData;
                        } else {
                            OutputData = sk_Restobj.OutputData;
                        }
                    }

                } else {
                    OutputData = sk_Rest_interpreterQuery.OutputData;
                }

                convertOuputData(OutputData);

                dismissProgressDialog();
            } catch (Exception E) {
                System.out.println("Error===" + E.toString());
            }

        }
    }

    private CustomTextView getTapToStart(LinearLayout ll_main){

        int index =0;

        for (int i = 0; i <ll_main.getChildCount() ; i++) {

            if(ll_main.getChildAt(i).getTag()!=null&&ll_main.getChildAt(i).getTag().equals("GPS")){
                index = i;
                break;
            }

        }

        LinearLayout ll1 = (LinearLayout) ll_main.getChildAt(index);
        View view  = ll1.getChildAt(0);
        CustomTextView tv_tap_to_start = view.findViewById(R.id.tv_tap_to_start);


        return tv_tap_to_start;

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(skipQueryActivity){
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_GPS_ENABLE){

            getTapToStart(ll_main).performClick();
        }
    }
}
