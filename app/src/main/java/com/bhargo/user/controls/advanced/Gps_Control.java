package com.bhargo.user.controls.advanced;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.controls.variables.GpsVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.bhargo.user.utils.AppConstants.REQUEST_GPS_ENABLE;
import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisable;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

public class Gps_Control implements OnMapReadyCallback, UIVariables, GpsVariables {

    private static final String TAG = "GPS_Control";
    private final int GPSTAG = 0;
    private final boolean fromQueryFilter;
    public String gpsData = "";
    //====================================================
    public long maxTime = 20000;
    List<LatLng> latLngList = new ArrayList<>();
    Activity context;
    private LinearLayout ll_displayName,ll_gps_main;

    public ControlObject getControlObject() {
        return controlObject;
    }

    ControlObject controlObject;
    ImageView iv_stop_tracking;
    RelativeLayout rl_tap_to_capture_gps_bts;
    LinearLayout linearLayout, layout_map, layout_showmap, layout_clear,layout_control,ll_tap_text;
    CardView cv_all;
    GoogleMap mGoogleMap;
    MapView mapFrag;
    CustomTextView tv_tap_to_start, tv_acccuracy;
    int status = 0;
    ProgressBar progressBar;
    //boolean start_tracking = false;
    boolean stop_progressbar = false;
    FrameLayout ll_mapview;
    String delimeter = "$";
    String GPS_Mode = "", GPS_acuuracy = "", GPS_Type = "Single point GPS", interval_Type = "", interval = "500";
    String providerType = LocationManager.GPS_PROVIDER;
    LocationManager locationManager = null;
    MyLocationListener mlistener;
    ImproveHelper improveHelper;
    private View strip_view;
    private CustomTextView tv_displayName, tv_hint, ct_alertGps, tv_retry,ct_showText;
    private ImageView iv_mandatory;
    private LocCaptured locCaptured;
    private CountDownTimer countDownTimer;
    private boolean isFirst = true;
    private boolean isGridControl;
    private ProgressDialog progressDialog;
    private float accuracy;

    public Gps_Control(Activity context, ControlObject controlObject) {
        this.context = context;
        this.controlObject = controlObject;
        fromQueryFilter = false;
        GPS_Mode = controlObject.getLocationMode();
        GPS_Type = controlObject.getGpsType();
        improveHelper = new ImproveHelper(context);

        if (GPS_Mode.equalsIgnoreCase(AppConstants.LOCATION_MODE_SATELLITE)) {
            GPS_acuuracy = controlObject.getAccuracy();
        } else {
            GPS_acuuracy = "10";
        }
        if (controlObject.getGpsType().equalsIgnoreCase(AppConstants.Multi_points_line) ||
                controlObject.getGpsType().equalsIgnoreCase(AppConstants.Polygon) ||
                controlObject.getGpsType().equalsIgnoreCase(AppConstants.Vehicle_Tracking)) {
            interval_Type = controlObject.getTypeOfInterval();
            if (interval_Type.equalsIgnoreCase(AppConstants.Interval_distance)) {
                interval = controlObject.getDistanceInMeters();
            } else if (interval_Type.equalsIgnoreCase(AppConstants.Interval_time)) {
                interval = controlObject.getTimeInMinutes();
            } else {
                interval = "";
            }
        }
        setGPS_Mode(GPS_Mode, GPS_acuuracy);
        initView();
        Log.d(TAG, "GPS_Control: " + controlObject.getControlName());
    }

    public Gps_Control(Activity context, ControlObject controlObject, boolean fromQueryFilter) {
        this.context = context;
        this.controlObject = controlObject;
        this.fromQueryFilter = fromQueryFilter;
        locCaptured = (LocCaptured) context;
        GPS_Mode = controlObject.getLocationMode();
        GPS_Type = controlObject.getGpsType();
        improveHelper = new ImproveHelper(context);
        if (GPS_Mode.equalsIgnoreCase(AppConstants.LOCATION_MODE_SATELLITE)) {
            GPS_acuuracy = controlObject.getAccuracy();
        } else {
            GPS_acuuracy = "10";
        }
        if (controlObject.getGpsType().equalsIgnoreCase(AppConstants.Multi_points_line) ||
                controlObject.getGpsType().equalsIgnoreCase(AppConstants.Polygon) ||
                controlObject.getGpsType().equalsIgnoreCase(AppConstants.Vehicle_Tracking)) {
            interval_Type = controlObject.getTypeOfInterval();
            if (interval_Type.equalsIgnoreCase(AppConstants.Interval_distance)) {
                interval = controlObject.getDistanceInMeters();
            } else if (interval_Type.equalsIgnoreCase(AppConstants.Interval_time)) {
                interval = controlObject.getTimeInMinutes();
            } else {
                interval = "";
            }
        }
        setGPS_Mode(GPS_Mode, GPS_acuuracy);
        initView();
    }

    public void setGPS_Mode(String GPS_Mode, String GPS_acuuracy) {
        try {
            this.GPS_Mode = GPS_Mode;
            if (GPS_Mode.equalsIgnoreCase(AppConstants.LOCATION_MODE_NETWORK)) {
                providerType = LocationManager.NETWORK_PROVIDER;
                this.GPS_acuuracy = "0";
            } else if (GPS_Mode.equalsIgnoreCase(AppConstants.LOCATION_MODE_SATELLITE)) {
                providerType = LocationManager.GPS_PROVIDER;
                this.GPS_acuuracy = GPS_acuuracy;
            } else {
                providerType = LocationManager.GPS_PROVIDER;
                this.GPS_acuuracy = "0";
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setGPS_Mode", e);
        }
    }

    public void setGPS_Type(String GPS_Type, String interval_Type, String interval) {
        try {
            this.GPS_Type = GPS_Type;
            if (GPS_Type.equalsIgnoreCase(AppConstants.Multi_points_line) ||
                    GPS_Type.equalsIgnoreCase(AppConstants.Polygon) ||
                    GPS_Type.equalsIgnoreCase(AppConstants.Vehicle_Tracking)) {
                this.interval_Type = interval_Type;
                this.interval = interval;

            } else {
                this.interval_Type = "";
                this.interval = "500";
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setGPS_Type", e);
        }

    }

    public String getGPS_Type() {
        return GPS_Type;
    }

    private void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            addGPSStrip();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initView", e);
        }

    }

    private void addGPSStrip() {
        try {
            final LayoutInflater ifLinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        strip_view = ifLinflater.inflate(R.layout.control_gps_update, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                    strip_view = ifLinflater.inflate(R.layout.control_gps_update_six, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {

                    strip_view = ifLinflater.inflate(R.layout.control_gps_update_seven, null);
                }else {
                    strip_view = ifLinflater.inflate(R.layout.control_gps_update, null);
                }
            } else {
                strip_view = ifLinflater.inflate(R.layout.control_gps_update, null);
            }
            strip_view.setTag(GPSTAG);

            ll_displayName = strip_view.findViewById(R.id.ll_displayName);
            ll_gps_main = strip_view.findViewById(R.id.ll_gps_main);
            rl_tap_to_capture_gps_bts = strip_view.findViewById(R.id.rl_tap_to_capture_gps_bts);
            tv_tap_to_start = strip_view.findViewById(R.id.tv_tap_to_start);
            tv_tap_to_start.setTag(controlObject.getControlName());
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                    tv_tap_to_start.setCompoundDrawablePadding(10);
                    tv_tap_to_start.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getDrawable(R.drawable.gps), null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
                    tv_tap_to_start.setCompoundDrawablesWithIntrinsicBounds(context.getDrawable(R.drawable.gps), null, null, null);
                    tv_tap_to_start.setCompoundDrawablePadding(10);
                }
            }
            tv_retry = strip_view.findViewById(R.id.tv_retry);
            progressBar = strip_view.findViewById(R.id.progress_horizontal);
            tv_retry.setTag(controlObject.getControlName());
            iv_stop_tracking = strip_view.findViewById(R.id.iv_stop_tracking);
            ll_mapview = strip_view.findViewById(R.id.ll_mapview);
            iv_stop_tracking.setTag(controlObject.getControlName());

            cv_all = strip_view.findViewById(R.id.cv_all);
            layout_control = strip_view.findViewById(R.id.layout_control);
            ll_tap_text = strip_view.findViewById(R.id.ll_tap_text);
            layout_map = strip_view.findViewById(R.id.layout_map);
            tv_acccuracy = strip_view.findViewById(R.id.tv_accuracy);
            ct_showText = strip_view.findViewById(R.id.ct_showText);
            mapFrag = strip_view.findViewById(R.id.map);
            if (mapFrag != null) {
                mapFrag.onCreate(null);
                mapFrag.getMapAsync(this);
            }

            layout_showmap = strip_view.findViewById(R.id.layout_showmap);
            layout_clear = strip_view.findViewById(R.id.layout_clear);

            tv_displayName = strip_view.findViewById(R.id.tv_displayName);
            tv_hint = strip_view.findViewById(R.id.tv_hint);
            iv_mandatory = strip_view.findViewById(R.id.iv_mandatory);
            ct_alertGps = strip_view.findViewById(R.id.ct_alertTypeText);

            clearAll();

            tv_tap_to_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!controlObject.isDisable()) {
                        startCaptureGPS();
                    }
                }
            });

            tv_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startCaptureGPS();
                }
            });

            iv_stop_tracking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (iv_stop_tracking.getVisibility() == View.VISIBLE) {
                        rl_tap_to_capture_gps_bts.setVisibility(View.GONE);
                        iv_stop_tracking.setVisibility(View.GONE);
                    }
                }
            });

            layout_clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearAll();
                }
            });

            layout_showmap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MapRefreshDynamicaly();

                }
            });

            if(controlObject.isHideDisplayName()){
                ll_displayName.setVisibility(View.GONE);
            }
            if (controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                tv_hint.setText(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }


            if (controlObject.isDisable()) {
//                disableView(strip_view);
//                setViewDisable(strip_view, !controlObject.isDisable());
//                setViewDisableOrEnableDefault(context,strip_view, controlObject.isDisable());
                improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),!controlObject.isDisable(),layout_control,strip_view);
            }

            linearLayout.addView(strip_view);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "addGPSStrip", e);
        }

    }

    public void MapRefreshDynamicaly() {
        try {
            if (getLatLngList().size() > 0) {
                Log.d("LaunchGoogleMap",""+getLatLngList().get(0).latitude+"-"+ getLatLngList().get(0).longitude);
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", getLatLngList().get(0).latitude, getLatLngList().get(0).longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                context.startActivity(intent);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "MapRefreshDynamicaly", e);
        }

    }

    public LinearLayout getControlGPSView() {
//        linearLayout.setTag("GPS");
        return linearLayout;
    }

    public String getGPSString(){
        String value="";
        for (int i = 0; i < latLngList.size(); i++) {
            LatLng latlang = latLngList.get(i);
            value = value + " ^" + latlang.latitude + "$" + latlang.longitude;
        }
        if(!value.equals("")){
            value = value.substring(value.indexOf("^") + 1);
        }

        return value;

    }


    public List<LatLng> getLatLngList() {
        return latLngList;
    }

    public void setLatLngList(List<LatLng> latLngList) {
        this.latLngList = latLngList;
    }

    public void addLatLngToList(LatLng latLng) {
        latLngList.add(latLng);
    }

    public void setMapView(String gpsString, String accuracy) {
        try {
            stopprogress();

            String[] loc = gpsString.split("\\$");
            LatLng current_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
            //current_location = new LatLng(-33.920455, 18.466941);

            addLatLngToList(current_location);

            mGoogleMap.addMarker(new MarkerOptions()
                    .position(current_location)
                    .title("Point " + getLatLngList().size())
                    .snippet(current_location.latitude + "|" + current_location.longitude));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_location, 15));
            mapFrag.invalidate();
            if (accuracy != null) {
                String str_accuracy = "Accuracy: " + accuracy;
                tv_acccuracy.setText(str_accuracy);
            }
            layout_map.setVisibility(View.VISIBLE);

            sucess();
            controlObject.setLatLngListItems(getLatLngList());
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setMapView", e);
        }


    }

    private void sucess() {
        // Sucess
        try {
            if (GPS_Type.equals(AppConstants.Single_Point_GPS)) {
                rl_tap_to_capture_gps_bts.setVisibility(View.GONE);
//            startCaptureGPS();
            } else if (GPS_Type.equals(AppConstants.Two_points_line)) {
                if (getLatLngList().size() == 2) {
                    rl_tap_to_capture_gps_bts.setVisibility(View.GONE);
                } else {
                    rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
                    tv_retry.setVisibility(View.GONE);
                    tv_tap_to_start.setText(R.string.tap_second_point);
                }
            } else if (GPS_Type.equals(AppConstants.Multi_points_line)) {
//            rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
//            tv_retry.setVisibility(View.GONE);
//            tv_tap_to_start.setText("Tap here to Capture Next Location");

                rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
                tv_retry.setVisibility(View.GONE);

                tv_tap_to_start.setText(R.string.capturing_next_point);
                tv_tap_to_start.setEnabled(false);
                if (iv_stop_tracking.getVisibility() == View.VISIBLE) {
                    startCaptureGPS();

                } else {
                    rl_tap_to_capture_gps_bts.setVisibility(View.GONE);
                    iv_stop_tracking.setVisibility(View.GONE);
                }
            } else if (GPS_Type.equals(AppConstants.Four_points_square)) {
                if (getLatLngList().size() == 4) {
                    rl_tap_to_capture_gps_bts.setVisibility(View.GONE);
                } else if (getLatLngList().size() == 3) {
                    rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
                    tv_retry.setVisibility(View.GONE);
                    tv_tap_to_start.setText(R.string.capture_fourth_point);
                } else if (getLatLngList().size() == 2) {
                    rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
                    tv_retry.setVisibility(View.GONE);
                    tv_tap_to_start.setText(R.string.cap_third_point);
                } else {
                    rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
                    tv_retry.setVisibility(View.GONE);
                    tv_tap_to_start.setText(R.string.capture_second_point);
                }
            } else if (GPS_Type.equals(AppConstants.Polygon)) {
//            rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
//            tv_retry.setVisibility(View.GONE);
//            tv_tap_to_start.setText("Tap here to Capture Next Location");

                rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
                tv_retry.setVisibility(View.GONE);

                tv_tap_to_start.setText(R.string.capture_next_point);
                tv_tap_to_start.setEnabled(false);
                if (iv_stop_tracking.getVisibility() == View.VISIBLE) {
                    startCaptureGPS();

                } else {
                    rl_tap_to_capture_gps_bts.setVisibility(View.GONE);
                    iv_stop_tracking.setVisibility(View.GONE);
                }
            } else if (GPS_Type.equals(AppConstants.Vehicle_Tracking)) {
                rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
                tv_retry.setVisibility(View.GONE);

//            tv_tap_to_start.setText("Vehicle Tracking... ");
                tv_tap_to_start.setText("Capturing coordinates ");
                tv_tap_to_start.setEnabled(false);
                if (iv_stop_tracking.getVisibility() == View.VISIBLE) {
                    startCaptureGPS();

                } else {
                    rl_tap_to_capture_gps_bts.setVisibility(View.GONE);
                    iv_stop_tracking.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "success", e);
        }

    }

    public void clearAll() {
        try {
            latLngList = new ArrayList<>();
            rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
            tv_tap_to_start.setVisibility(View.VISIBLE);
            iv_stop_tracking.setVisibility(View.GONE);
            tv_retry.setVisibility(View.GONE);

            layout_map.setVisibility(View.GONE);
            ct_alertGps.setVisibility(View.GONE);
            layout_clear.setTag(controlObject.getControlName());
            tv_tap_to_start.setText(R.string.tap_to_get_current_loc);
            tv_tap_to_start.setTag(controlObject.getControlName());

            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            if(controlObject.isHideDisplayName()){
                ll_displayName.setVisibility(View.GONE);
            }
            if (controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                tv_hint.setText(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }
            if (controlObject.isNullAllowed()) {
                iv_mandatory.setVisibility(View.VISIBLE);
            } else {
                iv_mandatory.setVisibility(View.GONE);
            }
            if (mGoogleMap != null) {
                mGoogleMap.clear();
            }
/*            if (controlObject.isDisable()) {
                tv_tap_to_start.setEnabled(false);
                disableView(strip_view);
            } else {
                tv_tap_to_start.setEnabled(true);

            }*/
            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());

            controlObject.setText(null);
            controlObject.setLatLngListItems(null);
//        if(controlObject.getControlValue() != null){
//            Log.d(TAG, "GPSGetControlValue: "+controlObject.getControlValue());
//            setControlValuesMap(controlObject.getControlValue());
//        }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "clearAll", e);
        }
    }

    private void startCaptureGPS() {
        try {
            if (isGPSON()) {
                AppConstants.Current_ClickorChangeTagName = controlObject.getControlName();

                tv_tap_to_start.setText(context.getString(R.string.finding_location));
                if (fromQueryFilter) {
                    showProgressDialog("Finding Location...");
                } else {
                    showprogress(strip_view);
                }
                gpsFinding();
                if (GPS_Type.equals(AppConstants.Multi_points_line) ||
                        GPS_Type.equals(AppConstants.Polygon) ||
                        GPS_Type.equals(AppConstants.Vehicle_Tracking)) {
//                tv_tap_to_start.setText("Vehicle Tracking... ");
                    tv_tap_to_start.setText("Capturing coordinates ");
                    iv_stop_tracking.setVisibility(View.VISIBLE);
                }

            } else {
                confirmGPS();
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "startCaptureGPS", e);
        }

    }

    private boolean isGPSON() {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (GPS_Mode.equalsIgnoreCase(AppConstants.LOCATION_MODE_NETWORK)) {
            return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } else {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
    }

    private void confirmGPS() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getString(R.string.enable_location));
            builder.setPositiveButton(context.getString(R.string.enable), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(context, "Enable GPS", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivityForResult(intent, REQUEST_GPS_ENABLE);
                }
            });
            builder.setCancelable(false);
            builder.show();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "confirmGPS", e);
        }

    }

    public void setStatusToTextView(String status) {

        progressBar.setVisibility(View.GONE);
        tv_tap_to_start.setText(status);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(context.getApplicationContext());
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);

        if (controlObject.getControlValue() != null && !controlObject.getControlValue().trim().contentEquals("")) {
            setGPS_Type(controlObject.getGpsType(), "", "");
            Log.d(TAG, "GPSGetControlValue: " + controlObject.getControlValue());
            showprogress(strip_view);
            setControlValuesMap(controlObject.getControlValue());
        }
        Log.d(TAG, "GPS_Control_map: " + controlObject.getControlName());
    }

    private void showprogress(View view) {
        try {
            final Handler handler = new Handler();

            progressBar.setVisibility(View.VISIBLE);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (status < 100) {
                        status += 1;
                        try {
                            if (stop_progressbar) {
                                Thread.interrupted();
                                status = 100;
                            } else {
                                Thread.sleep(600);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                progressBar.setProgress(status);


                                if (status == 100) {

                                }
                            }
                        });
                    }
                }
            }).start();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "showprogress", e);
        }
    }

    private void stopprogress() {
        try {
            stop_progressbar = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            }, 1000);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "stopprogress", e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void gpsFailedCase() {
        try {
            tv_tap_to_start.setText("Retry Again To Get Location...");
            stop_progressbar = false;
            status = 0;
            if (progressBar.isAnimating()) {
                progressBar.setVisibility(View.GONE);
            }
            if (GPS_Type.equals(AppConstants.Single_Point_GPS)) {
                rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
                tv_retry.setVisibility(View.VISIBLE);
                iv_stop_tracking.setVisibility(View.GONE);
            } else if (GPS_Type.equals(AppConstants.Two_points_line)) {
                if (getLatLngList().size() == 0) {
                    rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
                    tv_retry.setVisibility(View.VISIBLE);
                } else {
                    rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
                    tv_retry.setVisibility(View.VISIBLE);
                }
            } else if (GPS_Type.equals(AppConstants.Multi_points_line)) {
//            rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
//            tv_retry.setVisibility(View.VISIBLE);

                rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
                tv_retry.setVisibility(View.GONE);
                iv_stop_tracking.setVisibility(View.VISIBLE);
                tv_tap_to_start.setText("Capturing Next Point... ");
                tv_tap_to_start.setEnabled(false);
                if (iv_stop_tracking.getVisibility() == View.VISIBLE) {
                    startCaptureGPS();
                } else {
                    rl_tap_to_capture_gps_bts.setVisibility(View.GONE);
                    iv_stop_tracking.setVisibility(View.GONE);
                }
            } else if (GPS_Type.equals(AppConstants.Four_points_square)) {
                if (getLatLngList().size() == 0) {
                    rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
                    tv_retry.setVisibility(View.VISIBLE);
                    tv_tap_to_start.setText("Retry Again To Capture 1'st Point");
                } else if (getLatLngList().size() == 1) {
                    rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
                    tv_retry.setVisibility(View.VISIBLE);
                    tv_tap_to_start.setText("Retry Again To Capture 2'nd Point");
                } else if (getLatLngList().size() == 2) {
                    rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
                    tv_retry.setVisibility(View.VISIBLE);
                    tv_tap_to_start.setText("Retry Again To Capture 3'rd Point");
                } else {
                    rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
                    tv_retry.setVisibility(View.VISIBLE);
                    tv_tap_to_start.setText("Retry Again To Capture 4th Point");
                }
            } else if (GPS_Type.equals(AppConstants.Polygon)) {
//            rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
//            tv_retry.setVisibility(View.VISIBLE);
                rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
                tv_retry.setVisibility(View.GONE);
                iv_stop_tracking.setVisibility(View.VISIBLE);
                tv_tap_to_start.setText("Capturing Next Point... ");
                tv_tap_to_start.setEnabled(false);
                if (iv_stop_tracking.getVisibility() == View.VISIBLE) {
                    startCaptureGPS();
                } else {
                    rl_tap_to_capture_gps_bts.setVisibility(View.GONE);
                    iv_stop_tracking.setVisibility(View.GONE);
                }
            } else if (GPS_Type.equals(AppConstants.Vehicle_Tracking)) {
                rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
                tv_retry.setVisibility(View.GONE);
                iv_stop_tracking.setVisibility(View.VISIBLE);
//            tv_tap_to_start.setText("Vehicle Tracking... ");
                tv_tap_to_start.setText("Capturing coordinates ");
                tv_tap_to_start.setEnabled(false);
                if (iv_stop_tracking.getVisibility() == View.VISIBLE) {
                    startCaptureGPS();
                } else {
                    rl_tap_to_capture_gps_bts.setVisibility(View.GONE);
                    iv_stop_tracking.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "gpsFailedCase", e);
        }
    }

    public void retryLocation() {
        stop_progressbar = false;
        status = 0;
        layout_map.setVisibility(View.GONE);

        rl_tap_to_capture_gps_bts.setVisibility(View.VISIBLE);
        tv_tap_to_start.setText(context.getString(R.string.finding_location));

        showprogress(strip_view);


    }

    public void gpsFinding() {
        try {
            initialiseTimer(maxTime, 1000);
            turnGPSOn();
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            mlistener = new MyLocationListener();
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (GPS_Type.equalsIgnoreCase(AppConstants.Multi_points_line) ||
                    GPS_Type.equalsIgnoreCase(AppConstants.Polygon) ||
                    GPS_Type.equalsIgnoreCase(AppConstants.Vehicle_Tracking)) {
                if (interval_Type.equalsIgnoreCase(AppConstants.Interval_distance)) {
                    locationManager.requestLocationUpdates(providerType, 0, Integer.parseInt(interval.trim()), mlistener);
                } else if (interval_Type.equalsIgnoreCase(AppConstants.Interval_time)) {
                    locationManager.requestLocationUpdates(providerType, (Integer.parseInt(interval.trim()) * 60) * 1000, 0, mlistener);
                } else {
                    locationManager.requestLocationUpdates(providerType, 500, 0, mlistener);
                }

            } else {
                locationManager.requestLocationUpdates(providerType, 500, 0, mlistener);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "gpsFinding", e);
        }
    }


    private void initialiseTimer(long totalTime, long interval) {
        try {
            countDownTimer = new CountDownTimer(totalTime, interval) {

                public void onTick(long millisUntilFinished) {
//                tvCountDown.setText("Using " + providerType.toUpperCase() + " Countdown : " + (millisUntilFinished / 1000));
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                public void onFinish() {
                    if (TextUtils.isEmpty(gpsData)) {
                        if (isFirst) {
                            isFirst = false;
                            if (mlistener != null)
                                locationManager.removeUpdates(mlistener);

                            if (isSimSupport()) {
                                if (GPS_Mode.equalsIgnoreCase(AppConstants.LOCATION_MODE_HYBRID)) {
                                    providerType = LocationManager.NETWORK_PROVIDER;
                                    gpsFinding();
                                } else {
                                    Toast.makeText(context, "unable to get location", Toast.LENGTH_SHORT).show();
                                    gpsFailedCase();
                                }
                            } else {
                                Toast.makeText(context, "sim_card_not_available", Toast.LENGTH_SHORT).show();
                                gpsFailedCase();
                            }
                        } else {
                            Toast.makeText(context, "unable_to_get_location", Toast.LENGTH_SHORT).show();
                            gpsFailedCase();
                        }

                    }
                }

            }.start();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initialiseTimer", e);
        }
    }

    public boolean isSimSupport() {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  //gets the current TelephonyManager
        return !(tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT);

    }


    private void turnGPSOn() {
        try {
            String provider = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (provider!=null && !provider.contains("gps")) { // if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings",
                        "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                context.sendBroadcast(poke);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "turnGPSOn", e);
        }
    }

    private void turnGPSOff() {
        try {
            String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

            if (provider!=null && provider.contains("gps")) { // if gps is enabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings",
                        "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                context.sendBroadcast(poke);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "turnGPSOff", e);
        }
    }

    public CustomTextView setAlertGPS() {

        return ct_alertGps;
    }


    public void setControlValuesMap(String gpsCoordinate) {
        try {

            String[] gpsCoordinatesSplit = gpsCoordinate.split("\\^");
            for (int i = 0; i < gpsCoordinatesSplit.length; i++) {
                setMapView(gpsCoordinatesSplit[i], "");
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setControlValuesMap", e);
        }
    }

    public void showProgressDialog(String msg) {
        try {
            progressDialog = new ProgressDialog(context);
            // pd = CustomProgressDialog.ctor(this, msg);
            // pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(false);
            progressDialog.show();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "showProgressDialog", e);
        }
    }

    public void dismissProgressDialog() {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public boolean isFromQueryFilter() {
        return fromQueryFilter;
    }

    public CustomTextView getTv_tap_to_start() {
        return tv_tap_to_start;
    }

    public void disableView(View view) {
        try {
            LinearLayout layout = view.findViewById(R.id.ll_gps_main);
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                child.setEnabled(false);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "disableView", e);
        }

    }

    @Override
    public String getTextSize() {
        return controlObject.getTextSize();
    }

    @Override
    public void setTextSize(String size) {
        if (size != null) {
            controlObject.setTextSize(size);
            tv_displayName.setTextSize(Float.parseFloat(size));
        }

    }

    @Override
    public String getTextStyle() {
        return controlObject.getTextStyle();
    }

    @Override
    public void setTextStyle(String style) {
        if (style != null && style.equalsIgnoreCase("Bold")) {
            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
            tv_displayName.setTypeface(typeface_bold);
            controlObject.setTextStyle(style);
        } else if (style != null && style.equalsIgnoreCase("Italic")) {
            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
            tv_displayName.setTypeface(typeface_italic);
            controlObject.setTextStyle(style);
        }
    }

    @Override
    public String getTextColor() {
        return controlObject.getTextHexColor();
    }

    @Override
    public void setTextColor(String color) {
        if (color != null && !color.equalsIgnoreCase("")) {
            controlObject.setTextHexColor(color);
            controlObject.setTextColor(Color.parseColor(color));
            tv_displayName.setTextColor(Color.parseColor(color));
        }
    }

    @Override
    public String getGpsPoint() {
        return null;
    }

    @Override
    public void setGpsPoint(String gpsPoint) {

    }

    @Override
    public String getLatitude() {
        return null;
    }

    @Override
    public void setLatitude(String latitude) {

    }

    @Override
    public String getLongitude() {
        return null;
    }

    @Override
    public void setLongitude(String longitude) {

    }

    @Override
    public boolean getVisibility() {
        return false;
    }

    @Override
    public void setVisibility(boolean visibility) {
        if (visibility) {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        } else {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }
    }


    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {
//        setViewDisable(strip_view, !enabled);
//        setViewDisableOrEnableDefault(context,strip_view, enabled);
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),enabled,layout_control,strip_view);
    }

    @Override
    public void clean() {

    }

    public interface LocCaptured {
        void onLocCaptured(String gpsData);
    }

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {

            try {
                if (location != null) {

//                    boolean isMock = (Build.VERSION.SDK_INT >= 18 && location.isFromMockProvider());
                    boolean isMock = false;
                    if (isMock) {
                        clearAll();
                        locationManager.removeUpdates(mlistener);
                        turnGPSOff();
                        countDownTimer.cancel();
                        dismissProgressDialog();
                        ImproveHelper.showToast(context, "Fake GPS Point Will Not Accept.\nPlease Stop Fake GPS Application ");
                        //  setFailResult();
                        //   finish();
                    } else {
                        //                    if (location.getAccuracy() <= accuracy) {

                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        accuracy = location.getAccuracy();
                        float accuracylevel = Float.parseFloat(GPS_acuuracy);

                        if (GPS_Mode.equalsIgnoreCase(AppConstants.LOCATION_MODE_SATELLITE)) {
                            if (accuracy <= accuracylevel) {
                                locationManager.removeUpdates(mlistener);
                                gpsData = location.getLatitude() + delimeter + location.getLongitude();
                            }
                        } else {
                            locationManager.removeUpdates(mlistener);
                            gpsData = location.getLatitude() + delimeter + location.getLongitude();
                        }

//                    AppConstants.GlobalObjects.setCurrent_GPS(gpsData);

                        turnGPSOff();
                        countDownTimer.cancel();
                        if (fromQueryFilter) {
                            //stopprogress();
                            dismissProgressDialog();
                            locCaptured.onLocCaptured(gpsData);
                        } else if (controlObject.isGridControl()) {
                       /* stopprogress();
                        tv_tap_to_start.setText(gpsData);*/
                            setMapView(gpsData, "" + accuracy);
                        } else {
                            if (gpsData != null && !gpsData.contentEquals("")) {
                                setMapView(gpsData, "" + accuracy);
                            }
                        }


                    }
                    // }


                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setMapPointsDynamically(String ViewType, List<String> Points, String DefultMarker) {
        try {
            tv_tap_to_start.setVisibility(View.GONE);
            final LatLngBounds.Builder builder = new LatLngBounds.Builder();

            switch (ViewType) {
                case "Single point GPS":
                case AppConstants.map_Multiple_Marker:
                    for (int i = 0; i < Points.size(); i++) {
                        String[] loc = Points.get(i).split("\\$");
//                    String[] locA = Points.get(Points.size()-2).split("\\$");
                        LatLng temp_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
                        addLatLngToList(temp_location);
//                    LatLng temp_location1 = new LatLng(Double.parseDouble(locA[0]), Double.parseDouble(locA[1]));

                        if (temp_location.latitude != 0.0 && temp_location.longitude != 0.0) {
                            System.out.println("controlObject.getMapIcon() ===" + controlObject.getMapIcon());

                            if (DefultMarker != null) {
                                Glide.with(context)
                                        .asBitmap()
                                        .load(DefultMarker)
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
                            } else if (controlObject.getMapIcon() != null && !controlObject.getMapIcon().equalsIgnoreCase("Default") && !controlObject.getMapIcon().trim().equalsIgnoreCase("")) {
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
                            } else {
                                System.out.println("defult image ");
                                mGoogleMap.addMarker(new MarkerOptions()
                                        .position(temp_location)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                                        .title("Location " + i)
                                        .snippet(temp_location.latitude + "," + temp_location.longitude));
                            }
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp_location, 8));
                        }
                    }
                    break;
                case AppConstants.map_Multiple_Polylines:
                    for (int i = 0; i < Points.size(); i++) {
                        String[] locations = Points.get(i).split("\\^");
                        for (int j = 0; j < locations.length - 1; j++) {
                            String[] loc = locations[j].split("\\$");
                            String[] loc1 = locations[j + 1].split("\\$");
                            LatLng temp_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
                            LatLng temp_location1 = new LatLng(Double.parseDouble(loc1[0]), Double.parseDouble(loc1[1]));
                            addLatLngToList(temp_location);
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
                            addLatLngToList(temp_location);
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
            controlObject.setLatLngListItems(getLatLngList());
            layout_map.setVisibility(View.VISIBLE);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            if (controlObject.isZoomControl()) {
                mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
                mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            } else {
                mGoogleMap.getUiSettings().setZoomGesturesEnabled(false);
                mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
            }
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
            //mGoogleMap.resetMinMaxZoomPreference();

            mapFrag.performContextClick();

        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setMapPonitsDynamically", e);
        }
    }


    /* Setter properties*/

    public String getDisplayName() {
        return controlObject.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        tv_displayName.setText(displayName);
        controlObject.setDisplayName(displayName);
    }

    public String getHint() {
        return controlObject.getHint();
    }

    public void setHint(String hint) {
        if (hint != null && !hint.isEmpty()) {
            tv_hint.setVisibility(View.VISIBLE);
            tv_hint.setText(hint);
        } else {
            tv_hint.setVisibility(View.GONE);
        }
        controlObject.setHint(hint);
    }
    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        tv_displayName.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
        tv_hint.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
    }

    public boolean isInvisible() {
        return controlObject.isInvisible();
    }

    public void setInvisible(boolean invisible) {
        controlObject.setInvisible(invisible);
        if(invisible){
            linearLayout.setVisibility(View.GONE);
        }
    }

    public boolean isDisable() {
        return controlObject.isDisable();
    }

    public void setDisable(boolean disable) {
        controlObject.setDisable(disable);
//        if(disable){
//            setViewDisable(strip_view, !disable);
//        }

        improveHelper.controlEnableDisableBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),!disable,ll_gps_main,strip_view);
    }



    public String getLocationMode() {
        return controlObject.getLocationMode();
    }

    public void setLocationMode(String locationMode) {
        controlObject.setLocationMode(locationMode);
        GPS_Mode = locationMode;
    }

    public String getAccuracy() {
        return controlObject.getAccuracy();
    }

    public void setAccuracy(String accuracyValue) {
        controlObject.setAccuracy(accuracyValue);
        accuracy = Float.parseFloat(accuracyValue);
    }
    public String getGpsType() {
        return controlObject.getGpsType();
    }

    public void setGpsType(String gpsType) {
        controlObject.setGpsType(gpsType);
        GPS_Type = gpsType;
    }

    public String getTypeOfInterval() {
        return controlObject.getTypeOfInterval();
    }

    public void setTypeOfInterval(String typeOfInterval) {
        controlObject.setTypeOfInterval(typeOfInterval);
        interval_Type = typeOfInterval;
    }

    public String getDistanceInMeters() {
        return controlObject.getDistanceInMeters();
    }

    public void setDistanceInMeters(String distanceInMeters) {
        controlObject.setDistanceInMeters(distanceInMeters);
        interval = distanceInMeters;
    }

    public String getTimeInMinutes() {
        return controlObject.getTimeInMinutes();
    }

    public void setTimeInMinutes(String timeInMinutes) {
        controlObject.setTimeInMinutes(timeInMinutes);
        interval = timeInMinutes;
    }


    public boolean isEnableLocationFormatting() {
        return controlObject.isEnableLocationFormatting();
    }

    public void setEnableLocationFormatting(boolean enableLocationFormatting) {
        controlObject.setEnableLocationFormatting(enableLocationFormatting);
    }

    public String getLocationFormat() {
        return controlObject.getLocationFormat();
    }

    public void setLocationFormat(String locationFormat) {
        controlObject.setLocationFormat(locationFormat);
    }

    public boolean isEnableSavingLatitudeAndLongitudeInSeparateColumns() {
        return controlObject.isEnableSavingLatitudeAndLongitudeInSeparateColumns();
    }

    public void setEnableSavingLatitudeAndLongitudeInSeparateColumns(boolean latLngInSeparateColumns) {
        controlObject.setEnableSavingLatitudeAndLongitudeInSeparateColumns(latLngInSeparateColumns);
    }

    public boolean isShowMap() {
        return controlObject.isShowMap();
    }

    public void setShowMap(boolean showMap) {
        controlObject.setShowMap(showMap);
    }
    /* Setter properties*/

    public LinearLayout getLl_gps_main(){
        return ll_gps_main;
    }

    public CustomTextView getTv_displayName(){
        return tv_displayName;
    }
    public RelativeLayout getRl_tap_to_capture_gps_bts(){
        return rl_tap_to_capture_gps_bts;
    }
    public FrameLayout getMapView() {

        return ll_mapview;
    }
    public LinearLayout getLayout_map() {

        return layout_map;
    }
    public LinearLayout getLayout_control() {

        return layout_control;
    }
//    public LinearLayout getLl_tap_text() {
//
//        return ll_tap_text;
//    }
    public MapView getMapFrag() {

        return mapFrag;
    }
    public CardView getCv_all() {

        return cv_all;
    }
    @Override
    public void showMessageBelowControl(String msg) {
        if(msg != null && !msg.isEmpty()) {
            ct_showText.setVisibility(View.VISIBLE);
            ct_showText.setText(msg);
        }else{
            ct_showText.setVisibility(View.GONE);
        }
    }

}
