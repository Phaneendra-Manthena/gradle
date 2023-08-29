package com.bhargo.user.screens;

import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.Java_Beans.DataManagementOptions;
import com.bhargo.user.Java_Beans.DetailedPageHeaderSettings;
import com.bhargo.user.Java_Beans.DetailedPageUISettings;
import com.bhargo.user.Java_Beans.MainContainerSettings;
import com.bhargo.user.Java_Beans.MainContainerUISettings;
import com.bhargo.user.Java_Beans.QuerySelectField_Bean;
import com.bhargo.user.Java_Beans.SubContainerBodySettings;
import com.bhargo.user.Java_Beans.SubContainerHeaderSettings;
import com.bhargo.user.Java_Beans.SubContainerSettings;
import com.bhargo.user.Java_Beans.VisibilityManagementOptions;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.adapters.DetailedPageMainContainerAdapter;
import com.bhargo.user.controls.advanced.BarCode;
import com.bhargo.user.controls.advanced.QRCode;
import com.bhargo.user.controls.standard.AudioPlayer;
import com.bhargo.user.controls.standard.VideoPlayer;
import com.bhargo.user.controls.standard.ViewFileControl;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppDetailsAdvancedAction;
import com.bhargo.user.pojos.DetailedPageData;
import com.bhargo.user.pojos.DeviceIdResponse;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.RoundishImageViewAllCorners;
import com.bhargo.user.utils.SessionManager;
import com.bhargo.user.utils.XMLHelper;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedPageActivity extends BaseActivity {
    private static final String TAG = "DetailedPageActivity";
    Context context;
    private static final int BACK_TO_LIST_FLAG = 2002;
    ImproveHelper improveHelper;
    XMLHelper xmlHelper;
    GetServices getServices;
    String networkStatus = "online";
    String appLanguage = "en";
    SessionManager sessionManager;

    ImproveDataBase improveDataBase;
    private DataCollectionObject dataCollectionObject;
    VisibilityManagementOptions visibilityManagementOptions;
    DataManagementOptions dataManagementOptions;
    DetailedPageData detailedPageData;
    JSONObject appDataObj;
    private List<AudioPlayer> audioPlayerList = new ArrayList<>();
    LinearLayout ll_subform_container;
    int subContainerCount = 0;
    String subFormDisplayName = null;
    ImageView iv_delete, iv_edit;
    LinearLayout ll_single_record;
    LinearLayout layout_header;
    FrameLayout framelayout_header;
    LinearLayout MainLinearLayout;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Theme
        setBhargoTheme(this, AppConstants.THEME, AppConstants.IS_FORM_THEME, "");
        context = DetailedPageActivity.this;
        try {
            detailedPageData = (DetailedPageData) getIntent().getSerializableExtra("DetailedPageData");
            dataManagementOptions = detailedPageData.getDataManagementOptions();
            if (dataManagementOptions.getDetailedPageDetails().getDetailedPageTemplateId() == 1) {
                setContentView(R.layout.layout_detailed_page_style_default);
                layout_header = findViewById(R.id.layout_header);
            } else if (dataManagementOptions.getDetailedPageDetails().getDetailedPageTemplateId() == 2) {
                setContentView(R.layout.layout_detailed_page_style_one);
                layout_header = findViewById(R.id.layout_header);
            } else if (dataManagementOptions.getDetailedPageDetails().getDetailedPageTemplateId() == 3) {
                setContentView(R.layout.layout_detailed_page_style_two);
                framelayout_header = findViewById(R.id.framelayout_header);
            } else {
                setContentView(R.layout.layout_detailed_page_style_three);
            }
            initializeActionBar();
            enableBackNavigation(true);
            iv_circle_appIcon.setVisibility(View.VISIBLE);
            if (!isNetworkStatusAvialable(context)) {
                networkStatus = "offline";
            }
            setPageTitle();
        } catch (Exception e) {
            Log.d(TAG, "onCreate: " + e.toString());
        }

        initViews();
    }

    private void setPageTitle() {
        if (dataManagementOptions.getDetailedPageDetails().getDetailedPageHeaderSettings().isHeaderLayout() &&
                dataManagementOptions.getDetailedPageDetails().getDetailedPageHeaderSettings().getData().getPageTitle() != null) {
            title.setText(dataManagementOptions.getDetailedPageDetails().getDetailedPageHeaderSettings().getData().getPageTitle());
        } else if (dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().getDefaultPageTitle() != null) {
            title.setText(dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().getDefaultPageTitle());
        } else if (detailedPageData.getDisplayAppName() != null) {
            title.setText(detailedPageData.getDisplayAppName());
        } else {
            title.setText(detailedPageData.getAppName());
        }
    }

    private void initViews() {

        try {
            appLanguage = ImproveHelper.getLocale(this);
            xmlHelper = new XMLHelper();
            improveDataBase = new ImproveDataBase(context);
            improveHelper = new ImproveHelper(context);
            getServices = RetrofitUtils.getUserService();
            sessionManager = new SessionManager(context);

            appDataObj = new JSONObject(detailedPageData.getJsonObject());
            scrollView = findViewById(R.id.scrollView);
            ll_subform_container = findViewById(R.id.ll_subform_container);
            ll_single_record = findViewById(R.id.ll_single_record);
            iv_delete = findViewById(R.id.iv_delete);
            iv_edit = findViewById(R.id.iv_edit);
            loadAppIcon(detailedPageData.getDisplayIcon());
            String strDesignFormat = improveDataBase.getDesignFormat(sessionManager.getOrgIdFromSession(), detailedPageData.getAppName());
            dataCollectionObject = xmlHelper.XML_To_DataCollectionObject(strDesignFormat);

            if (dataManagementOptions != null && dataManagementOptions.getRecordInsertionType().equalsIgnoreCase("Single")) {
                ll_single_record.setVisibility(View.VISIBLE);
                if (dataManagementOptions.isEnableEditData()) {
                    iv_edit.setVisibility(View.VISIBLE);
                }
                if (dataManagementOptions.isEnableDeleteData()) {
                    iv_delete.setVisibility(View.VISIBLE);
                }
            }
            iv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editRow(appDataObj);
                }
            });

            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (dataManagementOptions != null && dataManagementOptions.getSubFormInMainForm().equalsIgnoreCase("")) {
                            deleteAlert(context, appDataObj.getString("Trans_ID"), networkStatus, false);
                        } else {
                            deleteAlert(context, getTransIdsToDelete(appDataObj), networkStatus, true);
                        }
                    } catch (Exception e) {

                    }
                }
            });

            new ShowDataAsync().execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getTransIdsToDelete(JSONObject object) {
        StringBuilder trans_ids = new StringBuilder();
        try {
            JSONArray jsonArray = object.getJSONArray("SubForm").getJSONObject(0).getJSONArray(dataManagementOptions.getSubFormInMainForm());
            for (int i = 0; i < jsonArray.length(); i++) {
                trans_ids.append(",").append(jsonArray.getJSONObject(i).getString("Trans_ID"));
            }
        } catch (Exception e) {
            Log.d(TAG, "getTransIdsToDelete: " + e);
            ImproveHelper.improveException(context, TAG, "getTransIdsToDelete", e);
        }
        return trans_ids.toString().replaceFirst(",", "");
    }

    public void deleteAlert(Context context, String transId, String status, boolean subFormInMainForm) {
        try {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.are_you_sure_del)

                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what would happen when positive button is clicked
                            dialogInterface.dismiss();
                            deleteRow(transId, status, subFormInMainForm);
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
            ImproveHelper.improveException(context, TAG, "deleteAlert", e);
        }

    }

    private void deleteRow(String transId, String status, boolean subFormInMainForm) {
        try {
            if (status.equalsIgnoreCase("offline")) {
                boolean result_status = improveDataBase.deleteRowData(transId);
                if (result_status) {
                    openAppsList();
                }
            } else {
                showProgressDialog(getString(R.string.please_wait));
                AppDetailsAdvancedAction appDetailsAdvancedAction = new AppDetailsAdvancedAction();
                appDetailsAdvancedAction.setOrgId(sessionManager.getOrgIdFromSession());
                appDetailsAdvancedAction.setUserId(detailedPageData.getUserID());
                appDetailsAdvancedAction.setPageName(detailedPageData.getAppName());
                appDetailsAdvancedAction.setAction("Delete");
                appDetailsAdvancedAction.setTransID(transId);
                appDetailsAdvancedAction.setSubFormInMainForm(subFormInMainForm);
                Call<DeviceIdResponse> getAllAppNamesDataCall = getServices.iDeleteAppData(sessionManager.getAuthorizationTokenId(), appDetailsAdvancedAction);
                getAllAppNamesDataCall.enqueue(new Callback<DeviceIdResponse>() {
                    @Override
                    public void onResponse(Call<DeviceIdResponse> call, Response<DeviceIdResponse> response) {
                        dismissProgressDialog();
                        DeviceIdResponse responseData = response.body();
                        if (responseData.getStatus().equalsIgnoreCase("200")) {
                            Toast.makeText(context, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                            if (responseData.getMessage().equalsIgnoreCase("Success")) {
                                openAppsList();

                            } else {
                                Toast.makeText(context, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, responseData.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<DeviceIdResponse> call, Throwable t) {
                        dismissProgressDialog();
                    }
                });
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "deleteRow", e);
        }
    }

    private void openAppsList() {

        Intent intent = new Intent(context, BottomNavigationActivity.class);
        intent.putExtra("NotifRefreshAppsList", "0");
        startActivity(intent);
        finish();

    }

    public void loadAppIcon(String appIconPath) {

        try {
            String[] imgUrlSplit = appIconPath.split("/");
            String imgNameInPackage = imgUrlSplit[imgUrlSplit.length - 1].replaceAll(" ", "_");
            String replaceWithUnderscore = detailedPageData.getAppName().replaceAll(" ", "_");
            Log.d(TAG, "loadAppIconAppNameQ: " + replaceWithUnderscore);
            String strSDCardUrl = "Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + replaceWithUnderscore;
            if (ImproveHelper.isFileExistsInExternalPackage(context, strSDCardUrl, imgNameInPackage)) {
                improveHelper.setImageFromPackageFolder(context, strSDCardUrl, imgNameInPackage, iv_circle_appIcon);
            } else {
                if (isNetworkStatusAvialable(context)) {
                    Glide.with(context).load(appIconPath).into(iv_circle_appIcon);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "loadAppIcon", e);
        }
    }

    public void setImageFromSDCard(String strImagePath) {

        File imgFile = new File(Environment.getExternalStorageDirectory(), strImagePath);
        Log.d(TAG, "setImageFromSDCard: " + imgFile);
        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            iv_circle_appIcon.setImageBitmap(myBitmap);

        }
    }

    public void setProperties(View view, LinearLayout ll_mainform_container, int mainContainerStyle, MainContainerUISettings mainContainerUISettings, CustomTextView tvDisplayName, CustomTextView tvValue) {

        switch (mainContainerStyle) {
            case 1:
                tvDisplayName.setTextSize(mainContainerUISettings.getLabelFontSize());
                tvDisplayName.setTextColor(Color.parseColor(mainContainerUISettings.getLabelFontColor()));
                tvValue.setTextSize(mainContainerUISettings.getValueFontSize());
                tvValue.setTextColor(Color.parseColor(mainContainerUISettings.getValueFontColor()));
                break;
            case 2:
                ImageView iv_divider = findViewById(R.id.iv_divider);
                tvDisplayName.setTextSize(mainContainerUISettings.getLabelFontSize());
                tvDisplayName.setTextColor(Color.parseColor(mainContainerUISettings.getLabelFontColor()));
                tvValue.setTextSize(mainContainerUISettings.getValueFontSize());
                tvValue.setTextColor(Color.parseColor(mainContainerUISettings.getValueFontColor()));
                if (mainContainerUISettings.getBorderStyle().equalsIgnoreCase("Dash")) {//dash
                    GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.dash_line_bg);
                    drawable.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()));
                    iv_divider.setBackground(drawable);
                } else if (mainContainerUISettings.getBorderStyle().equalsIgnoreCase("Dotted")) {//dotted
                    GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.dotted_line_bg);
                    drawable.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()));
                    iv_divider.setBackground(drawable);
                } else {//Solid
                    iv_divider.setColorFilter(Color.parseColor(mainContainerUISettings.getBorderColor()));
                }

                break;
            case 3:
                LinearLayout layout_item_bg_three = findViewById(R.id.layout_item_bg);
                tvDisplayName.setTextSize(mainContainerUISettings.getLabelFontSize());
                tvDisplayName.setTextColor(Color.parseColor(mainContainerUISettings.getLabelFontColor()));
                tvValue.setTextSize(mainContainerUISettings.getValueFontSize());
                tvValue.setTextColor(Color.parseColor(mainContainerUISettings.getValueFontColor()));
                if (mainContainerUISettings.getBorderStyle().equalsIgnoreCase("Dash")) {//dash
                    GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_bg_dash_border_radius_5);
                    drawable.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()));
                    drawable.setColor(Color.parseColor(mainContainerUISettings.getBackgroundColor()));
                    layout_item_bg_three.setBackground(drawable);
                } else if (mainContainerUISettings.getBorderStyle().equalsIgnoreCase("Dotted")) {//dotted
                    GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_bg_dotted_border_radius_5);
                    drawable.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()));
                    drawable.setColor(Color.parseColor(mainContainerUISettings.getBackgroundColor()));
                    layout_item_bg_three.setBackground(drawable);
                } else {//solid
                    GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_bg_radius_5);
                    drawable.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()));
                    drawable.setColor(Color.parseColor(mainContainerUISettings.getBackgroundColor()));
                    layout_item_bg_three.setBackground(drawable);
                }
                break;
            case 4:
                LinearLayout layout_item_bg_four = findViewById(R.id.layout_item_bg);
                tvDisplayName.setTextSize(mainContainerUISettings.getLabelFontSize());
                tvDisplayName.setTextColor(Color.parseColor(mainContainerUISettings.getLabelFontColor()));
                if (mainContainerUISettings.getAlignment().equalsIgnoreCase("LEFT")) {
                    tvDisplayName.setGravity(Gravity.START);
                    tvValue.setGravity(Gravity.START);
                } else {
                    tvDisplayName.setGravity(Gravity.CENTER);
                    tvValue.setGravity(Gravity.CENTER);
                }
                tvValue.setTextSize(mainContainerUISettings.getValueFontSize());
                tvValue.setTextColor(Color.parseColor(mainContainerUISettings.getValueFontColor()));
                if (mainContainerUISettings.getBorderStyle().equalsIgnoreCase("Dash")) {//dash
                    GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_bg_dash_border_radius_5);
                    drawable.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()));
                    drawable.setColor(Color.parseColor(mainContainerUISettings.getBackgroundColor()));
                    layout_item_bg_four.setBackground(drawable);
                } else if (mainContainerUISettings.getBorderStyle().equalsIgnoreCase("Dotted")) {//dotted
                    GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_bg_dotted_border_radius_5);
                    drawable.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()));
                    drawable.setColor(Color.parseColor(mainContainerUISettings.getBackgroundColor()));
                    layout_item_bg_four.setBackground(drawable);
                } else {//Solid
                    GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_bg_radius_5);
                    drawable.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()));
                    drawable.setColor(Color.parseColor(mainContainerUISettings.getBackgroundColor()));
                    layout_item_bg_four.setBackground(drawable);
                }
                break;
            case 5:
                LinearLayout layout_item_bg_five = findViewById(R.id.layout_item_bg);
                LinearLayout layout_active_bg = findViewById(R.id.layout_active_bg);
                tvDisplayName.setTextSize(mainContainerUISettings.getLabelFontSize());
                tvDisplayName.setTextColor(Color.parseColor(mainContainerUISettings.getLabelFontColor()));
                tvValue.setTextSize(mainContainerUISettings.getValueFontSize());
                tvValue.setTextColor(Color.parseColor(mainContainerUISettings.getValueFontColor()));
                GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_bg_radius_5);
                drawable.setColor(Color.parseColor(mainContainerUISettings.getBackgroundColor()));
                layout_item_bg_five.setBackground(drawable);
                GradientDrawable drawableActiveBG = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_bg_radius_5_primary_color);
                drawableActiveBG.setColor(Color.parseColor(mainContainerUISettings.getActiveColor()));
                layout_active_bg.setBackground(drawableActiveBG);
                break;
        }

    }

    public class ShowDataAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            improveHelper.showProgressDialog("Loading...Please wait.");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (dataManagementOptions.getDetailedPageDetails().getDetailedPageTemplateId() == 4) {
                        setBlogTemplateData(dataManagementOptions);
                    } else {
                        if (dataManagementOptions.getDetailedPageDetails().getDetailedPageHeaderSettings().isHeaderLayout()) {
                            setHeaderContainerData(dataManagementOptions.getDetailedPageDetails().getDetailedPageHeaderSettings());//Header
                        } else {
                            if (dataManagementOptions.getDetailedPageDetails().getDetailedPageTemplateId() == 1 ||
                                    dataManagementOptions.getDetailedPageDetails().getDetailedPageTemplateId() == 2) {
                                layout_header.setVisibility(View.GONE);
                            } else {
                                framelayout_header.setVisibility(View.GONE);
                            }
                        }


                        if (dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().isLeftLayout()) {
                            if (dataManagementOptions.getDetailedPageDetails().getDetailedPageTemplateId() == 1) {
                                setRightLayoutData(dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().getMainContainerSettings().getTableColumns());//Maincontainer
                            } else {
                                if (dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().getMainContainerSettings().getTableColumns() != null) {
                                    setMainContainerData(dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().getMainContainerSettings());//Maincontainer
                                }
                            }

                            if (dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().getSubContainerSettings().size() > 0) {
                                List<SubContainerSettings> subContainerSettingsList = dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().getSubContainerSettings();
                                subContainerCount = 0;
                                for (SubContainerSettings subContainerSettings : subContainerSettingsList) {
                                    setSubContainerData(subContainerSettings);//SubContainer
                                }
                            }
                        }

                        if (dataManagementOptions.getDetailedPageDetails().getBodySettings().getRightLayoutSettings().getRightLayout()) {
                            setRightLayoutData(dataManagementOptions.getDetailedPageDetails().getBodySettings().getRightLayoutSettings().getTableColumns());//Maincontainer
                        }

                    }

                }

            });

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                scrollView.getChildAt(0).setFocusable(View.FOCUSABLE);
            }
            scrollView.getChildAt(0).setFocusableInTouchMode(true);
            scrollView.getChildAt(0).requestFocus();
            improveHelper.dismissProgressDialog();
        }
    }

    private void setBlogTemplateData(DataManagementOptions dataManagementOptions) {
        if (dataManagementOptions.getDetailedPageDetails().getDetailedPageHeaderSettings().isHeaderLayout()) {
            setHeaderContainerData(dataManagementOptions.getDetailedPageDetails().getDetailedPageHeaderSettings());//Header
        }
    }

    private void setRightLayoutData(List<String> tableColumns) {
        try {
            LinearLayout ll_media_container = findViewById(R.id.ll_media_container);
            List<ControlObject> controlsList = new ArrayList<>();
            try {
                for (String columnName : tableColumns) {
                    ControlObject controlObject = getControlObject(columnName);
                    if (controlObject != null) {
                        String controlValue = appDataObj.getString(columnName);
                        controlObject.setControlValue(controlValue);

                        if (controlObject.getControlType() != null) {

                            if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                                controlsList.add(controlObject);
                            } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE)) {
                                controlsList.add(controlObject);
                            } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                                controlsList.add(controlObject);
                            } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_RECORDING)) {
                                controlsList.add(controlObject);
                            } else {
                                controlsList.add(controlObject);
                            }
                        }
                    }
                }
                addViewToMediaContainer(ll_media_container, controlsList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addViewToMediaContainer(LinearLayout ll_media_container, List<ControlObject> controlsList) {
        try {
            for (ControlObject controlObject : controlsList) {
                if (controlObject.getControlType() != null) {
                    if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                        LayoutInflater gpsInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View gpsView = gpsInflater.inflate(R.layout.map_view, null);
                        CustomTextView tv_displayName = gpsView.findViewById(R.id.tv_displayName);
                        tv_displayName.setText(controlObject.getDisplayName());
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(final GoogleMap googleMap) {

                                googleMap.getUiSettings().setZoomControlsEnabled(true);

                                googleMap.getUiSettings().setAllGesturesEnabled(true);

                                googleMap.getUiSettings().setMapToolbarEnabled(false);

                                final LatLngBounds.Builder builder = new LatLngBounds.Builder();

                                String gpsString = controlObject.getControlValue();
                                if (gpsString != null && !gpsString.trim().equalsIgnoreCase("")) {
                                    Double lat = 0.0;
                                    Double lng = 0.0;

                                    if (gpsString.contains("^") && !gpsString.contains("$")) {
                                        lat = Double.parseDouble(gpsString.split("\\^")[0]);
                                        lng = Double.parseDouble(gpsString.split("\\^")[1]);
                                    } else if (gpsString.contains("^") && gpsString.contains("$")) {

                                    } else {
                                        if (gpsString != null) {
                                            lat = Double.parseDouble(gpsString.split("\\$")[0]);
                                            lng = Double.parseDouble(gpsString.split("\\$")[1]);
                                        } else {
                                            lat = 0.0;
                                            lng = 0.0;
                                        }

                                    }

                                    LatLng latLng = new LatLng(lat, lng);

                                    Marker marker = googleMap.addMarker(new MarkerOptions()
                                            .position(latLng)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

                                    builder.include(marker.getPosition());
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

                                }

                            }
                        });
                        ll_media_container.addView(gpsView);
                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE)
                            || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA)) {

                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        View imgView = inflater.inflate(R.layout.image_view, null);

                        ImageView imageView = imgView.findViewById(R.id.imageView);
                        CustomTextView tv_displayName = imgView.findViewById(R.id.tv_displayName);
                        tv_displayName.setText(controlObject.getDisplayName());
                        if (controlObject.getControlValue() != null && !controlObject.getControlValue().equalsIgnoreCase("null") && controlObject.getControlValue().startsWith("http")) {
                            Glide.with(context).load(controlObject.getControlValue()).into(imageView);
                            ll_media_container.addView(imgView);
                        } else if (controlObject.getControlValue() != null && !controlObject.getControlValue().equalsIgnoreCase("null")) {
                            setImageFromSDCard( controlObject.getControlValue(),imageView);
                            ll_media_container.addView(imgView);
                        } else {
                            imageView.setVisibility(View.GONE);
                        }


                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER)
                            || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                        controlObject.setDisplayNameAlignment("9");
                        controlObject.setTextHexColor("#707b8a");
                        AudioPlayer audioPlayer_ = new AudioPlayer(context, controlObject, false, 0, "");
                        ll_media_container.addView(audioPlayer_.getAudioPlayerView());
                        audioPlayerList.add(audioPlayer_);
                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_FILE_BROWSING)) {
                        controlObject.setDisplayNameAlignment("9");
                        controlObject.setTextHexColor("#707b8a");
                        ViewFileControl viewFileControl = new ViewFileControl(DetailedPageActivity.this, controlObject, false, 0, "");
                        viewFileControl.setFileLink(controlObject.getControlValue());
                        ll_media_container.addView(viewFileControl.getViewFileLayout());

                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER)
                            || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_RECORDING)) {
                        controlObject.setVideoData(controlObject.getControlValue());
                        controlObject.setTextHexColor("#707b8a");
                        controlObject.setDisplayNameAlignment("9");
                        VideoPlayer videoPlayer = new VideoPlayer(DetailedPageActivity.this, controlObject, false, 0, "");
                        ll_media_container.addView(videoPlayer.getVideoPlayerView());
                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_BAR_CODE)) {
                        BarCode barCode = new BarCode(DetailedPageActivity.this, controlObject);
                        ll_media_container.addView(barCode.getBarCode());

                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_QR_CODE)) {
                        QRCode qrCode = new QRCode(DetailedPageActivity.this, controlObject);
                        ll_media_container.addView(qrCode.getQRCode());
                    } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SIGNATURE)) {
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View imgView = inflater.inflate(R.layout.signature_view, null);
                        ImageView imageView = imgView.findViewById(R.id.imageView);
                        CustomTextView tv_displayName = imgView.findViewById(R.id.tv_displayName);
                        tv_displayName.setText(controlObject.getDisplayName());
                        if (controlObject.getControlValue() != null && !controlObject.getControlValue().equalsIgnoreCase("null")) {
                            Glide.with(context).load(controlObject.getControlValue()).into(imageView);
                            ll_media_container.addView(imgView);
                        } else {
                            imageView.setVisibility(View.GONE);
                        }
                    } else {


                        LayoutInflater lInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = lInflater.inflate(R.layout.layout_detailed_page_main_container_item_one, null);

                        CustomTextView tv_display_name = view.findViewById(R.id.tv_displayName);
                        CustomTextView tv_value = view.findViewById(R.id.tv_value);

                        tv_display_name.setText(controlObject.getControlName());
                        tv_value.setText(controlObject.getControlValue());
                        tv_display_name.setTextColor(ContextCompat.getColor(context, R.color.divider2));
                        tv_value.setTextColor(ContextCompat.getColor(context, R.color.black));
                        ll_media_container.addView(view);
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "addViewToMediaContainer: "+e.toString());
        }
    }

    private void setMainContainerData(MainContainerSettings mainContainerSettings) {
        try {
            LinearLayout ll_mainform_container = findViewById(R.id.ll_mainform_container);
            GradientDrawable drawable = (GradientDrawable) ll_mainform_container.getBackground();
            drawable.setColor(ContextCompat.getColor(context, R.color.white));
            LinearLayout ll_media_container = findViewById(R.id.ll_media_container);
            List<ControlObject> controlsList = new ArrayList<>();

            try {
                for (String columnName : mainContainerSettings.getTableColumns()) {
                    ControlObject controlObject = getControlObject(columnName);
                    if (controlObject != null) {
                        if (controlObject.getControlType() != null) {
                            if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                                controlsList.add(controlObject);
                            } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE)) {
                                controlsList.add(controlObject);
                            } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                                controlsList.add(controlObject);
                            } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_RECORDING)) {
                                controlsList.add(controlObject);
                            } else {
                                controlsList.add(controlObject);
                            }
                        }
                    }
                }
                addViewToMainContainerRV(ll_mainform_container, ll_media_container, controlsList, mainContainerSettings);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setSubContainerData(SubContainerSettings subContainerSettings) {
        try {
            String controlName = subContainerSettings.getSubControlName();
            HashMap<String, List<List<ControlObject>>> subformControlsList = new HashMap<>();
            JSONArray subFormArray = new JSONArray();
            if (appDataObj.has("SubForm")) {
                subFormArray = appDataObj.getJSONArray("SubForm");
                for (int i = 0; i < subFormArray.length(); i++) {
                    JSONObject subformJsonObject = subFormArray.getJSONObject(i);
                    Log.d(TAG, "onCreate1: " + subformJsonObject.has(controlName));
                    if (subformJsonObject.has(controlName)) {
                        List<List<ControlObject>> controlsListSF = new ArrayList<List<ControlObject>>();
                        List<List<ControlObject>> gpsControlListSF = new ArrayList<List<ControlObject>>();
                        List<List<ControlObject>> imageControlListSF = new ArrayList<List<ControlObject>>();
                        List<List<ControlObject>> videoControlsListSF = new ArrayList<List<ControlObject>>();
                        List<List<ControlObject>> audioControlsListSF = new ArrayList<List<ControlObject>>();
                        JSONArray subformJsonArray = subformJsonObject.getJSONArray(controlName);
                        for (int j = 0; j < subformJsonArray.length(); j++) {

                            List<ControlObject> controlsListRow = new ArrayList<>();
                            List<ControlObject> gpsControlListRow = new ArrayList<>();
                            List<ControlObject> imageControlListRow = new ArrayList<>();
                            List<ControlObject> videoControlsListRow = new ArrayList<>();
                            List<ControlObject> audioControlsListRow = new ArrayList<>();

                            JSONObject jsonObject = subformJsonArray.getJSONObject(j);
                            for (String columnName : subContainerSettings.getSubContainerTableColumns()) {
                                String paramName = columnName;
                                String paramValue = jsonObject.has(paramName) ? jsonObject.getString(paramName) : "column not found";
                                if (!paramValue.equalsIgnoreCase("column not found")) {
                                    if (!paramName.equalsIgnoreCase("Trans_id") &&
                                            !paramName.contentEquals("SubForm") &&
                                            !paramName.endsWith("Trans_ID") &&
                                            !paramName.endsWith("_emp_id") &&
                                            !paramName.endsWith("_postid") &&
                                            !paramName.endsWith("_trans_date")) {

                                        ControlObject controlObject = getSubFormControlObject(paramName, controlName);
                                        if (controlObject != null && subContainerSettings.getSubContainerTableColumns().contains(controlObject.getControlName())) {

                                            controlObject.setControlTitle(AppConstants.CONTROL_TYPE_SUBFORM);
                                            controlObject.setControlValue(paramValue);

                                            if (controlObject.getControlType() != null) {
                                                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                                                    controlsListRow.add(new ControlObject(controlObject));
                                                } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE)) {
                                                    controlsListRow.add(new ControlObject(controlObject));
                                                } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VOICE_RECORDING)) {
                                                    controlsListRow.add(new ControlObject(controlObject));
                                                } else if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER) || controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_RECORDING)) {
                                                    controlsListRow.add(new ControlObject(controlObject));
                                                } else {
                                                    controlsListRow.add(new ControlObject(controlObject));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            gpsControlListSF.add(gpsControlListRow);
                            imageControlListSF.add(imageControlListRow);
                            audioControlsListSF.add(audioControlsListRow);
                            videoControlsListSF.add(videoControlsListRow);
                            controlsListSF.add(controlsListRow);
                        }

                        subformControlsList.put(controlName, gpsControlListSF);
                        subformControlsList.put(controlName, imageControlListSF);
                        subformControlsList.put(controlName, audioControlsListSF);
                        subformControlsList.put(controlName, videoControlsListSF);
                        subformControlsList.put(controlName, controlsListSF);
                    }
                }
            }

            if (subformControlsList.size() > 0) {
                showSubContainerData(subContainerSettings, subformControlsList);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showSubContainerData(SubContainerSettings subContainerSettings, HashMap<String, List<List<ControlObject>>> subformControlsList) {
        for (Map.Entry<String, List<List<ControlObject>>> entry : subformControlsList.entrySet()) {
            if (entry != null) {
                try {
                    HorizontalScrollView horizontalScrollView = new HorizontalScrollView(context);

                    LinearLayout layoutMain = new LinearLayout(context);
                    layoutMain.setBackgroundColor(ContextCompat.getColor(context, R.color.fbutton_color_clouds));
                    layoutMain.setPadding(10, 10, 10, 10);

                    LinearLayout ll_table = new LinearLayout(context);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.setMargins(5, 15, 5, 15);
                    ll_table.setLayoutParams(layoutParams);
                    ll_table.setOrientation(LinearLayout.VERTICAL);
                    TableLayout tl_header = new TableLayout(context);

                    setHeaderData(ll_table, tl_header, subContainerSettings, entry.getKey(), entry.getValue());
                    setBodyData(ll_table, tl_header, subContainerSettings, entry.getKey(), entry.getValue());

                    layoutMain.addView(ll_table);
                    horizontalScrollView.addView(layoutMain);
                    CustomTextView subFormName = new CustomTextView(context);
                    subFormName.setText(subFormDisplayName);
                    Typeface tf = Typeface.createFromAsset(getAssets(), "Satoshi-Bold.otf");
                    subFormName.setTypeface(tf);
                    subFormName.setTextSize(18);
                    subFormName.setPadding(5, 5, 5, 5);
                    ll_subform_container.addView(subFormName);
                    ll_subform_container.addView(horizontalScrollView);
                    View viewForSeperation = new View(context);
                    LinearLayout.LayoutParams layoutParamsView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 20);
                    viewForSeperation.setLayoutParams(layoutParamsView);
                    viewForSeperation.setBackgroundColor(ContextCompat.getColor(context, R.color.apps_list_bg));
                    ll_subform_container.addView(viewForSeperation);
                } catch (Exception e) {
                    Log.d(TAG, "onCreate: " + e);
                }
            }
        }
    }

    private ControlObject getControlObject1(String columnName) {
        try {
            String tempColumnName = columnName;
            List<ControlObject> controlsList = dataCollectionObject.getControls_list();
            for (ControlObject controlObject : controlsList) {
                if (columnName.contains("_Coordinates")) {
                    columnName = columnName.substring(0, columnName.indexOf("_Coordinates"));
                }
                if (controlObject.getControlName().equalsIgnoreCase(columnName)) {
                    controlObject.setControlValue(appDataObj.getString(tempColumnName));
                    return controlObject;
                } else if ((controlObject.getControlName() + "_id").equalsIgnoreCase(columnName) || (controlObject.getControlName() + "id").equalsIgnoreCase(columnName)) {
                    ControlObject controlObject1 = getControlObjectForIdColumns(columnName, controlObject.getControlName(), controlObject.getControlType());
                    return controlObject1;
                }
            }
        } catch (JSONException e) {
            Log.d(TAG, "getControlObject: " + e.toString());
        }
        return null;
    }

    private ControlObject getControlObject(String columnName) {
        try {
            String tempColumnName = columnName;
            List<ControlObject> controlsList = dataCollectionObject.getControls_list();
            for (ControlObject controlObject : controlsList) {
                if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SECTION)) {
                    List<ControlObject> controlsListSection = controlObject.getSubFormControlList();
                    for (ControlObject controlObjectSection : controlsListSection) {
                        if (columnName.contains("_Coordinates")) {
                            columnName = columnName.substring(0, columnName.indexOf("_Coordinates"));
                        }
                        if (controlObjectSection.getControlName().equalsIgnoreCase(columnName)) {
                            controlObjectSection.setControlValue(appDataObj.getString(tempColumnName));
                            return controlObjectSection;
                        } else if ((controlObjectSection.getControlName() + "_id").equalsIgnoreCase(columnName) || (controlObjectSection.getControlName() + "id").equalsIgnoreCase(columnName)) {
                            ControlObject controlObject_id = getControlObjectForIdColumns(columnName, controlObjectSection.getControlName(), controlObjectSection.getControlType());
                            return controlObject_id;
                        }
                    }
                } else {
                    if (columnName.contains("_Coordinates")) {
                        columnName = columnName.substring(0, columnName.indexOf("_Coordinates"));
                    }
                    if (controlObject.getControlName().equalsIgnoreCase(columnName)) {
                        controlObject.setControlValue(appDataObj.getString(tempColumnName));
                        return controlObject;
                    } else if ((controlObject.getControlName() + "_id").equalsIgnoreCase(columnName) || (controlObject.getControlName() + "id").equalsIgnoreCase(columnName)) {
                        ControlObject controlObject_id = getControlObjectForIdColumns(columnName, controlObject.getControlName(), controlObject.getControlType());
                        return controlObject_id;
                    }
                }
            }
        } catch (JSONException e) {
            Log.d(TAG, "getControlObject: " + e.toString());
        }
        return null;
    }

    private ControlObject getSubFormControlObject1(String columnName, String subFormName) {

        try {
            String tempColumnName = columnName;
            List<ControlObject> controlsList = dataCollectionObject.getControls_list();

            for (ControlObject subformControlObject : controlsList) {
                if (subformControlObject.getControlName().equalsIgnoreCase(subFormName)) {
                    subFormDisplayName = subformControlObject.getDisplayName();
                    List<ControlObject> subformControlObjectList = subformControlObject.getSubFormControlList();
                    for (ControlObject controlObject : subformControlObjectList) {
                        if (columnName.contains("_Coordinates")) {
                            columnName = columnName.substring(0, columnName.indexOf("_Coordinates"));
                        }
                        if (controlObject.getControlName().equalsIgnoreCase(columnName)) {
                            controlObject.setControlValue(appDataObj.getString(tempColumnName));
                            return controlObject;
                        } else if ((controlObject.getControlName() + "_id").equalsIgnoreCase(columnName) || (controlObject.getControlName() + "id").equalsIgnoreCase(columnName)) {
                            ControlObject controlObject1 = getControlObjectForIdColumnsOfSubformAndGrid(columnName, controlObject.getControlName(), controlObject.getControlType());
                            return controlObject1;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "getControlObject: " + e.toString());
        }
        return null;
    }

    private ControlObject getSubFormControlObject(String columnName, String subFormName) {

        try {
            String tempColumnName = columnName;
            List<ControlObject> controlsList = dataCollectionObject.getControls_list();

            for (ControlObject subformControlObject : controlsList) {
                if (subformControlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SECTION)) {
                    List<ControlObject> controlsListSection = subformControlObject.getSubFormControlList();
                    for (ControlObject controlObjectSection : controlsListSection) {
                        if (controlObjectSection.getControlName().equalsIgnoreCase(subFormName)) {
                            subFormDisplayName = controlObjectSection.getDisplayName();
                            List<ControlObject> subformControlObjectList = controlObjectSection.getSubFormControlList();
                            for (ControlObject controlObject : subformControlObjectList) {
                                if (columnName.contains("_Coordinates")) {
                                    columnName = columnName.substring(0, columnName.indexOf("_Coordinates"));
                                }
                                if (controlObject.getControlName().equalsIgnoreCase(columnName)) {
                                    return controlObject;
                                } else if ((controlObject.getControlName() + "_id").equalsIgnoreCase(columnName) || (controlObject.getControlName() + "id").equalsIgnoreCase(columnName)) {
                                    ControlObject controlObject1 = getControlObjectForIdColumnsOfSubformAndGrid(columnName, controlObject.getControlName(), controlObject.getControlType());
                                    return controlObject1;
                                }
                            }
                        }
                    }
                } else {
                    if (subformControlObject.getControlName().equalsIgnoreCase(subFormName)) {
                        subFormDisplayName = subformControlObject.getDisplayName();
                        List<ControlObject> subformControlObjectList = subformControlObject.getSubFormControlList();
                        for (ControlObject controlObject : subformControlObjectList) {
                            if (columnName.contains("_Coordinates")) {
                                columnName = columnName.substring(0, columnName.indexOf("_Coordinates"));
                            }
                            if (controlObject.getControlName().equalsIgnoreCase(columnName)) {
                                return controlObject;
                            } else if ((controlObject.getControlName() + "_id").equalsIgnoreCase(columnName) || (controlObject.getControlName() + "id").equalsIgnoreCase(columnName)) {
                                ControlObject controlObject1 = getControlObjectForIdColumnsOfSubformAndGrid(columnName, controlObject.getControlName(), controlObject.getControlType());
                                return controlObject1;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "getControlObject: " + e.toString());
        }
        return null;
    }

    private void addViewToMainContainerRV(LinearLayout ll_mainform_container, LinearLayout ll_media_container, List<ControlObject> controlsList, MainContainerSettings mainContainerSettings) {

        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_grey));
        if (mainContainerSettings.getMainContainerTemplateId() == 4) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
            gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(gridLayoutManager);
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
        }

        DetailedPageMainContainerAdapter detailedPageMainContainerAdapter = new DetailedPageMainContainerAdapter(context, mainContainerSettings.getMainContainerTemplateId(), mainContainerSettings.getMainContainerUISettings(), controlsList);
        recyclerView.setAdapter(detailedPageMainContainerAdapter);
        ll_mainform_container.addView(recyclerView);

    }


    private void setHeaderContainerData(DetailedPageHeaderSettings detailedPageHeaderSettings) {
        try {
            DetailedPageUISettings detailedPageUISettings = detailedPageHeaderSettings.getDetailedPageUISettings();
//            LinearLayout layout_header_style_one = findViewById(R.id.layout_header);
            if (dataManagementOptions.getDetailedPageDetails().getDetailedPageTemplateId() == 3){
                framelayout_header.setVisibility(View.VISIBLE);
            }else{
            layout_header.setVisibility(View.VISIBLE);}
            ImageView iv_image = findViewById(R.id.iv_image);
            CustomTextView tv_header = findViewById(R.id.tv_header);
            CustomTextView tv_subheader = findViewById(R.id.tv_subheader);
            if(detailedPageHeaderSettings.getData().getImage()!=null){
            Glide.with(context).load(appDataObj.getString(detailedPageHeaderSettings.getData().getImage())).into(iv_image);}
            tv_header.setText(appDataObj.getString(detailedPageHeaderSettings.getData().getHeader()));
            tv_subheader.setText(appDataObj.getString(detailedPageHeaderSettings.getData().getSubHeader()));

            tv_header.setTextSize(Integer.parseInt(detailedPageUISettings.getHeaderFontSize()));
            tv_header.setTextColor(Color.parseColor(detailedPageUISettings.getHeaderFontColor()));
//            tv_header.setTextColor(Color.parseColor("#374a66"));
            setFontStyle(detailedPageUISettings.getHeaderFontStyle(), tv_header);

            tv_subheader.setTextSize(Integer.parseInt(detailedPageUISettings.getSubHeaderFontSize()));
            tv_subheader.setTextColor(Color.parseColor(detailedPageUISettings.getSubHeaderFontColor()));
//            tv_subheader.setTextColor(Color.parseColor("#374a66"));
            setFontStyle(detailedPageUISettings.getSubHeaderFontStyle(), tv_subheader);

            if (dataManagementOptions.getDetailedPageDetails().getDetailedPageTemplateId() == 1 ||
                    dataManagementOptions.getDetailedPageDetails().getDetailedPageTemplateId() == 2) {
                GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_corners_default_white_bg);
                drawable.setColor(Color.parseColor(detailedPageUISettings.getBackgroundColor()));
                layout_header.setBackground(drawable);
            } else if (dataManagementOptions.getDetailedPageDetails().getDetailedPageTemplateId() == 3) {

                RoundishImageViewAllCorners roundishImageViewAllCorners = findViewById(R.id.iv_bg_card);
                roundishImageViewAllCorners.setColorFilter(Color.parseColor(detailedPageUISettings.getBackgroundColor()));

                LinearLayout layout_card_one = findViewById(R.id.layout_card_one);
                LinearLayout layout_card_two = findViewById(R.id.layout_card_two);
                ImageView iv_card_one_image = findViewById(R.id.iv_card_one_image);
                CustomTextView tv_card_one_label = findViewById(R.id.tv_card_one_label);
                CustomTextView tv_card_one_value = findViewById(R.id.tv_card_one_value);
                ImageView iv_card_two_image = findViewById(R.id.iv_card_two_image);
                CustomTextView tv_card_two_label = findViewById(R.id.tv_card_two_label);
                CustomTextView tv_card_two_value = findViewById(R.id.tv_card_two_value);

                Glide.with(context).load(appDataObj.getString(detailedPageHeaderSettings.getData().getCardOneImage())).into(iv_card_one_image);
                Glide.with(context).load(appDataObj.getString(detailedPageHeaderSettings.getData().getCardTwoImage())).into(iv_card_two_image);
                tv_card_one_label.setText(appDataObj.getString(detailedPageHeaderSettings.getData().getCardOneLable()));
                tv_card_one_value.setText(appDataObj.getString(detailedPageHeaderSettings.getData().getCardOneValue()));
                tv_card_two_label.setText(appDataObj.getString(detailedPageHeaderSettings.getData().getCardTwoLable()));
                tv_card_two_value.setText(appDataObj.getString(detailedPageHeaderSettings.getData().getCardTwoValue()));

                tv_card_one_label.setTextSize(Integer.parseInt(detailedPageUISettings.getCardOneLabelFontSize()));
                tv_card_one_label.setTextColor(Color.parseColor(detailedPageUISettings.getCardOneLabelFontColor()));

                tv_card_one_value.setTextSize(Integer.parseInt(detailedPageUISettings.getCardOneValueFontSize()));
                tv_card_one_value.setTextColor(Color.parseColor(detailedPageUISettings.getCardOneValueFontColor()));

                tv_card_two_label.setTextSize(Integer.parseInt(detailedPageUISettings.getCardTwoLabelFontSize()));
                tv_card_two_label.setTextColor(Color.parseColor(detailedPageUISettings.getCardTwoLabelFontColor()));

                tv_card_two_value.setTextSize(Integer.parseInt(detailedPageUISettings.getCardTwoValueFontSize()));
                tv_card_two_value.setTextColor(Color.parseColor(detailedPageUISettings.getCardTwoValueFontColor()));

                GradientDrawable drawablec1 = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_corners_default_white_bg);
                drawablec1.setColor(Color.parseColor(detailedPageUISettings.getCardOneBackgroundColor()));
                layout_card_one.setBackground(drawablec1);

                GradientDrawable drawablec2 = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_corners_default_white_bg);
                drawablec2.setColor(Color.parseColor(detailedPageUISettings.getCardTwoBackgroundColor()));
                layout_card_two.setBackground(drawablec2);

            } else {//Blog style

                ImageView iv_profile_image = findViewById(R.id.iv_profile_image);
                CustomTextView tv_title = findViewById(R.id.tv_title);
                CustomTextView tv_date = findViewById(R.id.tv_date);
                CustomTextView tv_description = findViewById(R.id.tv_description);
                Glide.with(context).load(appDataObj.getString(detailedPageHeaderSettings.getData().getProfileImage())).into(iv_profile_image);
                tv_title.setText(appDataObj.getString(detailedPageHeaderSettings.getData().getTitle()));
                tv_date.setText(appDataObj.getString(detailedPageHeaderSettings.getData().getDate()));

                if (detailedPageHeaderSettings.getData().getDescriptionList() != null && detailedPageHeaderSettings.getData().getDescriptionList().size() > 0) {
                    List<String> list_description = detailedPageHeaderSettings.getData().getDescriptionList();
                    String concatenatedDesc = "";
                    for (int i = 0; i < list_description.size(); i++) {
                        concatenatedDesc += appDataObj.getString(list_description.get(i));
                        if (i < list_description.size() - 1) {
                            concatenatedDesc += " ";
                        }
                    }
                    tv_description.setText(concatenatedDesc);

                    tv_description.setTextSize(Integer.parseInt(detailedPageUISettings.getDescriptionFontSize()));
                    tv_description.setTextColor(Color.parseColor(detailedPageUISettings.getDescriptionFontColor()));
//                    tv_description.setTextColor(Color.parseColor("#374a66"));
                    setFontStyle(detailedPageUISettings.getDescriptionFontStyle(), tv_header);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setFontStyle(String style, TextView textView) {
        if (style.equalsIgnoreCase("Bold")) {
            Typeface tf = Typeface.createFromAsset(getAssets(), "Satoshi-Bold.otf");
            textView.setTypeface(tf);
        } else if (style.equalsIgnoreCase("Italic")) {
            Typeface tf = Typeface.createFromAsset(getAssets(), "Satoshi-Italic.otf");
            textView.setTypeface(tf);
        }
    }

    private void setHeaderData(LinearLayout ll_table, TableLayout tl_header, SubContainerSettings subContainerSettings, String subformName, List<List<ControlObject>> controlObjectList) {
        try {
            int headerNamesSize = controlObjectList.get(0).size();
            //Add header cols to tablelayout
            TableRow tr_header = new TableRow(context);
            tr_header.setTag(-1);
            //add headers
            for (int dataTableCol = 0, colIndex = 0; dataTableCol < controlObjectList.get(0).size(); dataTableCol++) {
                ControlObject controlObject = controlObjectList.get(0).get(dataTableCol);
                String headerName = controlObject.getDisplayName();
                LayoutInflater header_lInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View header_view = header_lInflater.inflate(R.layout.item_data_table_inner_control_header, null);
                CustomTextView tv_header = header_view.findViewById(R.id.tv_label);
                FrameLayout framelayout = header_view.findViewById(R.id.framelayout);
                tv_header.setText(headerName);

                setHeaderUISettings(tv_header, subContainerSettings.getSubContainerHeaderSettings());
                applyHeaderTemplateUI(framelayout, ll_table, tv_header, true, 0, colIndex, headerNamesSize, subContainerSettings.getSubContainerTemplateId(), subContainerSettings.getSubContainerHeaderSettings(), tr_header);
                tr_header.addView(header_view);
                setHeightAndWidth(header_view, framelayout, subContainerSettings.getSubContainerHeaderSettings().getHeight(), subContainerSettings.getSubContainerHeaderSettings().getWidth());

                colIndex++;
            }
            tl_header.addView(tr_header);
            ll_table.addView(tl_header);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setHeightAndWidth(View view, FrameLayout frameLayout, int height, int width) {
        try {//set Height and Width
            TableRow.LayoutParams tr_params = new TableRow.LayoutParams();
            ViewGroup.MarginLayoutParams params =
                    (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int screenWidth = displayMetrics.widthPixels;
            //Control obj wise width & height for all header cells
            //col width
            int colWidthSize = width;
            params.width = pxToDP(colWidthSize);
            tr_params.width = pxToDP(colWidthSize);

            //col height
            int colHeightSize = height;
            params.height = pxToDP(colHeightSize);
            tr_params.height = pxToDP(colHeightSize);
            frameLayout.setLayoutParams(tr_params);
            view.setLayoutParams(params);

            view.requestLayout();
        } catch (Exception e) {
            Log.d(TAG, "setHeaderUISettings: " + e.toString());
        }
    }

    private void applyHeaderTemplateUI(FrameLayout frameLayout, LinearLayout ll_table, CustomTextView textView, boolean headerRow, int rowIndex, int colIndex, int headerNamesSize, int subContainerTemplateID, SubContainerHeaderSettings subContainerHeaderSettings, TableRow tr_header) {

//      ll_table.setPadding(5, 5, 5, 5);
        ll_table.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_default_transparent_bg));



        /*No Border*/
        ll_table.setPadding(0, 0, 0, 0);
        frameLayout.setBackgroundResource(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, 0);
        textView.setLayoutParams(layoutParams);


        /*Header Row*/
        if (rowIndex == 0 && colIndex == 0 && headerRow) { // header 0 pos
            if (subContainerTemplateID == 3) {
                textView.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_left_top_bottom_transparent_bg));
            } else {
                textView.setBackground(context.getResources().getDrawable(R.drawable.only_top_left_rounded_corners_default_gray_bg));

            }
            onlyHeaderColor(textView, subContainerHeaderSettings, subContainerTemplateID, "first");
        } else if (rowIndex == 0 && colIndex == headerNamesSize - 1 && headerRow) { //header last pos
            if (subContainerTemplateID == 3) {
                textView.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_right_top_bottom_transparent_bg));
            } else {
                textView.setBackgroundResource(R.drawable.only_top_right_rounded_corners_default_gray_bg);
            }

            onlyHeaderColor(textView, subContainerHeaderSettings, subContainerTemplateID, "last");
        }
        /*Header Row*/


    }

    private void applyBodyTemplateUI(FrameLayout frameLayout, LinearLayout ll_table, CustomTextView textView, boolean headerRow, int rowIndex, int colIndex, int colCount, int subContainerTemplateID, SubContainerBodySettings subContainerBodySettings, int rowsCount) {

        try {
//            ll_table.setPadding(5, 5, 5, 5);
            ll_table.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_default_transparent_bg));

            if (subContainerTemplateID == 1) {
                /*No Border*/
                ll_table.setPadding(0, 0, 0, 0);
                frameLayout.setBackgroundResource(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                textView.setLayoutParams(layoutParams);
            } else if (subContainerTemplateID == 2) {

                GradientDrawable drawable = (GradientDrawable) ll_table.getBackground();
                drawable.setColor(Color.parseColor(subContainerBodySettings.getBorderColor()));
                ll_table.setPadding(0, 0, 0, 0);

               /* frameLayout.setBackgroundResource(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 5);
                textView.setLayoutParams(layoutParams);*/

                if (rowIndex == rowsCount - 1) {
                    frameLayout.setBackgroundResource(0);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 2);
                    textView.setLayoutParams(layoutParams);
                }
            } else if (subContainerTemplateID == 3) {
                //            GradientDrawable drawable = (GradientDrawable) ll_table.getBackground();
                //            drawable.setColor(ContextCompat.getColor(context, R.color.white));
                ll_table.setPadding(0, 0, 0, 0);

                if (rowIndex == rowsCount - 1) {
                    frameLayout.setBackgroundResource(0);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 10, 0, 0);
                    textView.setLayoutParams(layoutParams);
                } else {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.setMargins(0, 10, 0, 0);
                    textView.setLayoutParams(layoutParams);
                }
            }

            if (subContainerTemplateID == 3) {
                if (colIndex == 0) {
                    textView.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_left_top_bottom_transparent_bg));

                    onlyBodyColor(textView, rowIndex, subContainerBodySettings, subContainerTemplateID);

                } else if (colIndex == colCount - 1) {
                    textView.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_right_top_bottom_transparent_bg));

                    onlyBodyColor(textView, rowIndex, subContainerBodySettings, subContainerTemplateID);
                }
            } else {
                /*Body Row*/
                if (rowIndex == rowsCount - 1) { // LastRow()
                    if (colIndex == 0) {
                        textView.setBackground(context.getResources().getDrawable(R.drawable.only_bottom_left_rounded_corners_default_gray_bg));

                        onlyBodyColor(textView, rowIndex, subContainerBodySettings, subContainerTemplateID);

                    } else if (colIndex == colCount - 1) {
                        textView.setBackground(context.getResources().getDrawable(R.drawable.only_bottom_right_rounded_corners_default_gray_bg));

                        onlyBodyColor(textView, rowIndex, subContainerBodySettings, subContainerTemplateID);
                    }
                    /*else if (subContainerTemplateID == 1) {
                        onlyBodyColor(textView, rowIndex, subContainerBodySettings, subContainerTemplateID);
                        }*/

                } else {
//                    onlyBodyColor(textView, rowIndex, subContainerBodySettings, subContainerTemplateID);
                }
                /*Body Row*/
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }


    }

    private void onlyBodyColor(CustomTextView textView, int rowIndex, SubContainerBodySettings subContainerBodySettings, int subContainerTemplateID) {
        try {
            GradientDrawable drawable = (GradientDrawable) textView.getBackground();
            if (subContainerBodySettings.getBackgroundcolorType() != null) {
                if (subContainerBodySettings.getBackgroundcolorType().equalsIgnoreCase("Single Color") || subContainerBodySettings.getBackgroundcolorType().equalsIgnoreCase("Single")) {
                    if (subContainerBodySettings.getBackgroundcolorOne() != null) {
                        drawable.setColor(Color.parseColor(subContainerBodySettings.getBackgroundcolorOne()));
                    }
                } else {
                    if (rowIndex % 2 == 0) {
                        if (subContainerBodySettings.getBackgroundcolorOne() != null) {
                            drawable.setColor(Color.parseColor(subContainerBodySettings.getBackgroundcolorOne()));
                        }
                    } else {
                        if (subContainerBodySettings.getBackgroundcolorTwo() != null) {
                            drawable.setColor(Color.parseColor(subContainerBodySettings.getBackgroundcolorTwo()));
                        }
                    }
                }
            }
            if (subContainerTemplateID == 2) {
                drawable.setColor(ContextCompat.getColor(context, R.color.white));
            } else if (subContainerTemplateID == 3) {
                drawable.setColor(Color.parseColor(subContainerBodySettings.getBackgroundcolorOne()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void onlyHeaderColor(CustomTextView textView, SubContainerHeaderSettings subContainerHeaderSettings, int subContainerTemplateID, String position) {
        GradientDrawable drawable = (GradientDrawable) textView.getBackground();
        if (subContainerHeaderSettings.getBackgroundcolorType().equalsIgnoreCase("Single")) {
            if (subContainerHeaderSettings.getBackgroundcolorOne() != null) {
                drawable.setColor(Color.parseColor(subContainerHeaderSettings.getBackgroundcolorOne()));
            }
        } else {
            //Gradient colors

//           int[] colors = {Color.parseColor(subContainerHeaderSettings.getBackgroundcolorOne())
//                    ,Color.parseColor(subContainerHeaderSettings.getBackgroundcolorTwo())};
//            drawable.setColors(colors);
            setBackground(textView, subContainerHeaderSettings, subContainerTemplateID, position);
        }

    }

    public static void setBackground(View v, SubContainerHeaderSettings subContainerHeaderSettings, int subContainerTemplateID, String position) {

        if (subContainerTemplateID == 3) {
            if (position.equalsIgnoreCase("first")) {
                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{Color.parseColor(subContainerHeaderSettings.getBackgroundcolorOne()),
                                Color.parseColor(subContainerHeaderSettings.getBackgroundcolorTwo())});
                gd.setShape(GradientDrawable.RECTANGLE);
                gd.setCornerRadii(new float[]{30, 30, 0, 0, 0, 0, 30, 30});
                v.setBackground(gd);
            } else {
                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{Color.parseColor(subContainerHeaderSettings.getBackgroundcolorOne()),
                                Color.parseColor(subContainerHeaderSettings.getBackgroundcolorTwo())});
                gd.setShape(GradientDrawable.RECTANGLE);
                gd.setCornerRadii(new float[]{0, 0, 30, 30, 30, 30, 0, 0});
                v.setBackground(gd);
            }
        } else {
            if (position.equalsIgnoreCase("first")) {
                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{Color.parseColor(subContainerHeaderSettings.getBackgroundcolorOne()),
                                Color.parseColor(subContainerHeaderSettings.getBackgroundcolorTwo())});
                gd.setShape(GradientDrawable.RECTANGLE);
                gd.setCornerRadii(new float[]{30, 30, 0, 0, 0, 0, 0, 0});
                v.setBackground(gd);
            } else {
                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{Color.parseColor(subContainerHeaderSettings.getBackgroundcolorOne()),
                                Color.parseColor(subContainerHeaderSettings.getBackgroundcolorTwo())});
                gd.setShape(GradientDrawable.RECTANGLE);
                gd.setCornerRadii(new float[]{0, 0, 30, 30, 0, 0, 0, 0});
                v.setBackground(gd);
            }
        }
    }

    private void setHeaderUISettings(CustomTextView tv_header, SubContainerHeaderSettings subContainerHeaderSettings) {


        //col text size
        if (subContainerHeaderSettings.getTextSize() != 0) {
            tv_header.setTextSize(subContainerHeaderSettings.getTextSize());
        }
        //col text style
        String style = subContainerHeaderSettings.getTextStyle();
        if (style != null && style.equalsIgnoreCase("Bold")) {
            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
            tv_header.setTypeface(typeface_bold);
        } else if (style != null && style.equalsIgnoreCase("Italic")) {
            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
            tv_header.setTypeface(typeface_italic);
        }
        //col text color
        if (subContainerHeaderSettings.getTextColor() != null) {
            tv_header.setTextColor(Color.parseColor(subContainerHeaderSettings.getTextColor()));
        }
        //col text alignment
        if (subContainerHeaderSettings.getAlignment() != null) {
            if (subContainerHeaderSettings.getAlignment().equalsIgnoreCase("Left")) {
                tv_header.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            } else if (subContainerHeaderSettings.getAlignment().equalsIgnoreCase("Center")) {
                tv_header.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
            } else {
                tv_header.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            }
        }
        //col color
        if (subContainerHeaderSettings.getBackgroundcolorType().equalsIgnoreCase("Single")) {
            if (subContainerHeaderSettings.getBackgroundcolorOne() != null) {
                tv_header.setBackgroundColor(Color.parseColor(subContainerHeaderSettings.getBackgroundcolorOne()));
            }
        } else {//set Gradient Color
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[]{Color.parseColor(subContainerHeaderSettings.getBackgroundcolorOne()),
                            Color.parseColor(subContainerHeaderSettings.getBackgroundcolorTwo())});
            tv_header.setBackground(gd);

        }


    }

    private void setBodyUISettings(CustomTextView tv_body, SubContainerBodySettings subContainerBodySettings, int templateID, int rowIndex) {


        try {
            //col text size
            if (subContainerBodySettings.getTextSize() != 0) {
                tv_body.setTextSize(subContainerBodySettings.getTextSize());
            }
            //col text style
            String style = subContainerBodySettings.getTextStyle();
            if (style != null && style.equalsIgnoreCase("Bold")) {
                Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
                tv_body.setTypeface(typeface_bold);
            } else if (style != null && style.equalsIgnoreCase("Italic")) {
                Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
                tv_body.setTypeface(typeface_italic);
            }
            //col text color
            if (subContainerBodySettings.getTextColor() != null) {
                tv_body.setTextColor(Color.parseColor(subContainerBodySettings.getTextColor()));
            }
            //col text alignment
            if (subContainerBodySettings.getAlignment() != null) {
                if (subContainerBodySettings.getAlignment().equalsIgnoreCase("Left")) {
                    tv_body.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                } else if (subContainerBodySettings.getAlignment().equalsIgnoreCase("Center")) {
                    tv_body.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                } else {
                    tv_body.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
                }
            }
            //col color
            if (subContainerBodySettings.getBackgroundcolorType() != null) {
                if (subContainerBodySettings.getBackgroundcolorType().equalsIgnoreCase("Single")) {
                    if (subContainerBodySettings.getBackgroundcolorOne() != null) {
                        tv_body.setBackgroundColor(Color.parseColor(subContainerBodySettings.getBackgroundcolorOne()));
                    }
                } else {//set Alternate Color
                    if (rowIndex % 2 == 0) {
                        if (subContainerBodySettings.getBackgroundcolorOne() != null) {
                            tv_body.setBackgroundColor(Color.parseColor(subContainerBodySettings.getBackgroundcolorOne()));
                        }
                    } else {
                        if (subContainerBodySettings.getBackgroundcolorOne() != null) {
                            tv_body.setBackgroundColor(Color.parseColor(subContainerBodySettings.getBackgroundcolorTwo()));
                        }
                    }
                }
            } else {
                tv_body.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            }
            if (templateID == 3) {
                tv_body.setBackgroundColor(Color.parseColor(subContainerBodySettings.getBackgroundcolorOne()));
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }


    }

    public int pxToDP(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, getResources().getDisplayMetrics());
    }

    private void setBodyData(LinearLayout ll_table, TableLayout tl_header, SubContainerSettings subContainerSettings, String key, List<List<ControlObject>> controlObjectList) {
        try {
            TableLayout tl_body = new TableLayout(context);
            tl_body.removeAllViews();
            int rowsCount = controlObjectList.size();
            for (int rowIndex = 0; rowIndex < controlObjectList.size(); rowIndex++) {
                TableRow tr_body = new TableRow(context);
                for (int dataTableCol = 0, colIndex = 0; dataTableCol < controlObjectList.get(rowIndex).size(); dataTableCol++) {
                    ControlObject controlObject = controlObjectList.get(rowIndex).get(dataTableCol);
                    String rowData = controlObject.getControlValue();
                    int columnsCount = controlObjectList.get(rowIndex).size();
                    setRowData(tl_header, ll_table, dataTableCol, colIndex, rowIndex, tr_body, rowData, subContainerSettings, rowsCount, columnsCount);
                    colIndex++;
                }
                tl_body.addView(tr_body);
            }
            ll_table.addView(tl_body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRowData(TableLayout tl_header, LinearLayout ll_table, int dataTableColl, int colIndex, int rowIndex, TableRow tr_body, String rowData, SubContainerSettings subContainerSettings, int rowsCount, int colCount) {
        try {
            LayoutInflater lInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View body_view = lInflater.inflate(R.layout.item_data_table_inner_control, null);
            tr_body.setTag(body_view);
            FrameLayout framelayout = body_view.findViewById(R.id.framelayout);
            CustomTextView textView = body_view.findViewById(R.id.ll_control_view);
            textView.setId(colIndex);
            textView.setTag(String.valueOf(rowIndex));
            String value = String.valueOf(rowData);
            textView.setText(value);

            setBodyUISettings(textView, subContainerSettings.getSubContainerBodySettings(), subContainerSettings.getSubContainerTemplateId(), rowIndex);
            applyBodyTemplateUI(framelayout, ll_table, textView, false, rowIndex, colIndex, colCount, subContainerSettings.getSubContainerTemplateId(), subContainerSettings.getSubContainerBodySettings(), rowsCount);
            tr_body.addView(body_view);
            setBodyHeightAndWidth(tl_header, body_view, colIndex, framelayout, subContainerSettings.getSubContainerBodySettings().getHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBodyHeightAndWidth(TableLayout tl_header, View view, int colIndex, FrameLayout frameLayout, int height) {

        try {
            TableRow.LayoutParams tr_params = new TableRow.LayoutParams();
            ViewGroup.MarginLayoutParams params =
                    (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            //row width
            int width = viewWidth(((TableRow) tl_header.getChildAt(0)).getChildAt(colIndex));
            params.width = width;
            tr_params.width = width;

            //row height
            int rowHeightSize = height;
            params.height = pxToDP(rowHeightSize);
            tr_params.height = pxToDP(rowHeightSize);

            frameLayout.setLayoutParams(tr_params);
            view.setLayoutParams(params);
            view.requestLayout();
        } catch (Exception e) {
            ImproveHelper.improveException(context, "DataTable", "setRowWidthHeight", e);
        }

    }

    private int viewWidth(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }

    public void stopAudioPlayer() {

        for (int i = 0; i < audioPlayerList.size(); i++) {
            AudioPlayer audioPlayer = audioPlayerList.get(i);
            audioPlayer.stopPlaying();
        }

    }

    @Override
    protected void onDestroy() {
        stopAudioPlayer();
        super.onDestroy();
    }


    private ControlObject getControlObjectForIdColumns(String columnName, String controlName, String controlType) {
        try {
            String tempColumnName = "";
            ControlObject controlObjectOther = new ControlObject();
            controlObjectOther.setDisplayName(columnName);
            if (columnName.contains("_id")) {
                tempColumnName = columnName.substring(0, columnName.indexOf("_id"));
            } else if (columnName.contains("id")) {
                tempColumnName = columnName.substring(0, columnName.indexOf("id"));
            }
            if (controlName.equalsIgnoreCase(tempColumnName)) {
                controlObjectOther.setControlType(controlType);
                controlObjectOther.setControlValue(appDataObj.getString(tempColumnName));
                return controlObjectOther;
            }
        } catch (Exception e) {
            Log.d(TAG, "getControlObject: " + e.toString());
        }
        return null;
    }
    private ControlObject getControlObjectForIdColumnsOfSubformAndGrid(String columnName, String controlName, String controlType) {
        try {
            String tempColumnName = "";
            ControlObject controlObjectOther = new ControlObject();
            controlObjectOther.setDisplayName(columnName);
            if (columnName.contains("_id")) {
                tempColumnName = columnName.substring(0, columnName.indexOf("_id"));
            } else if (columnName.contains("id")) {
                tempColumnName = columnName.substring(0, columnName.indexOf("id"));
            }
            if (controlName.equalsIgnoreCase(tempColumnName)) {
                controlObjectOther.setControlType(controlType);
                return controlObjectOther;
            }
        } catch (Exception e) {
            Log.d(TAG, "getControlObject: " + e.toString());
        }
        return null;
    }


    private void editRow(JSONObject jsonObject) {
        try {

            Intent intent = new Intent(context, MainActivity.class);

            intent.putExtra("jsonObject", jsonObject.toString());
            intent.putExtra("dataManagementOptions", dataManagementOptions);
            intent.putExtra("visibilityManagementOptions", visibilityManagementOptions);
            intent.putExtra("tableName", detailedPageData.getTableName());
            Gson gson = new Gson();
            intent.putExtra("subFormDetails", gson.toJson(detailedPageData.getSubFormDetails()));
            intent.putExtra("Trans_ID", jsonObject.getString("Trans_ID"));
            intent.putExtra("appName", detailedPageData.getAppName());
            intent.putExtra("s_childForm", "EditForm");
            intent.putExtra("s_app_version", detailedPageData.getAppVersion());
            intent.putExtra("s_app_type", detailedPageData.getAppType());
            intent.putExtra("s_org_id", sessionManager.getOrgIdFromSession());
            intent.putExtra("s_app_name", detailedPageData.getAppName());
            intent.putExtra("s_display_app_name", detailedPageData.getDisplayAppName());
            intent.putExtra("s_app_icon", detailedPageData.getDisplayIcon());
            intent.putExtra("s_created_by", detailedPageData.getCreatedBy());
            intent.putExtra("s_user_id", detailedPageData.getUserID());
            intent.putExtra("s_distribution_id", detailedPageData.getDistributionID());
            intent.putExtra("s_user_location_Structure", detailedPageData.getUserLocationStructure());
            intent.putExtra("app_edit", "Edit");
            intent.putExtra("from", AppConstants.TYPE_CALL_FORM);
            finish();


            startActivityForResult(intent, BACK_TO_LIST_FLAG);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getTransIdsToDelete", e);
        }
    }
    public void setImageFromSDCard(String strImagePath,ImageView imageView) {
        File imgFile = new File(strImagePath);
        Log.d(TAG, "setImageFromSDCard: " + imgFile);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }
    }
}