package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.AppConstants.REQUEST_GPS_ENABLE;
import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.ImageAdvanced_Mapped_Item;
import com.bhargo.user.Java_Beans.Item;
import com.bhargo.user.Java_Beans.RenderingType;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
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
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MapControl extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, GoogleMap.OnPolygonClickListener {


    private static final String TAG = "MapControl";
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(20);
    private final int MapControlTAG = 0;
    public String gpsData = "";
    public long maxTime = 20000;
    public GoogleMap mGoogleMap;
    Activity context;
    LatLng current_location;
    ControlObject controlObject;
    LinearLayout linearLayout, ll_mapView_main, ll_label, ll_main_inside, ll_control_ui;
    MapView mapFrag;
    //SupportMapFragment gMap;
    String GPS_Mode = AppConstants.LOCATION_MODE_HYBRID, GPS_acuuracy = "10", interval_Type = "", interval = "500";
    String providerType = LocationManager.GPS_PROVIDER;
    String delimeter = "$";
    boolean stop_progressbar = false;
    ProgressBar progressBar;
    LocationManager locationManager = null;
    MapControl.MyLocationListener mlistener;
    String SelectedGPS = "";
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    ImproveHelper improveHelper;
    int i = 0;
    LatLng temp_location;
    LinearLayout ll_displayName;
    boolean isUIFormNeeded = false;
    CardView cv_all;
    int mapPointsIndex = 0;
    List<String> mapPoints = new ArrayList<>();
    String renderingType = null;
    View activityView;
    private CustomTextView tv_displayName, tv_hint, tv_retry,ct_showText;
    private boolean isFirst = true;
    private String mapViewType;
    //    private MapControl.LocCaptured locCaptured;
    private int status = 0;
    //    public MapControl(Activity context, ControlObject controlObject) {
//        this.context = context;
//        this.controlObject = controlObject;
//
//        initViews();
//
//    }
    private View view;
    private CountDownTimer countDownTimer;
    private ProgressDialog progressDialog;
    private List<PatternItem> PATTERN_POLYLINE_DOTTED;
    private List<String> transIdsList;
    private float accuracy;

    public MapControl(Activity context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName, boolean isUIFormNeeded) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        this.isUIFormNeeded = isUIFormNeeded;
        improveHelper = new ImproveHelper(context);

        if (controlObject.getMapViewType() != null) {

            mapViewType = controlObject.getMapViewType();
        }

//        locCaptured=(MapControl.LocCaptured)context;
        initViews();

    }

    public MapControl(Activity context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName, boolean isUIFormNeeded, View activityView) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        this.isUIFormNeeded = isUIFormNeeded;
        this.activityView = activityView;
        improveHelper = new ImproveHelper(context);

        if (controlObject.getMapViewType() != null) {

            mapViewType = controlObject.getMapViewType();
        }

//        locCaptured=(MapControl.LocCaptured)context;
        initViews();

    }


    private void initViews() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            if (!isUIFormNeeded) {
                linearLayout.setLayoutParams(ImproveHelper.layout_params);
            }
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            addMapControlStrip(context);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initViews", e);
        }
    }

    private void addMapControlStrip(Activity context) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = lInflater.inflate(R.layout.control_map, null);
/*            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                    view = linflater.inflate(R.layout.control_map_six, null);
                }
            } else {*/

//                view = linflater.inflate(R.layout.control_map, null);
            view = linflater.inflate(R.layout.control_map_default, null);
//            }
            view.setTag(controlObject.getControlName());

//        GPS_Mode = AppConstants.LOCATION_MODE_HYBRID;

            tv_displayName = view.findViewById(R.id.tv_displayName);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            tv_hint.setVisibility(View.GONE);
            tv_retry = view.findViewById(R.id.tv_retry);
            ll_mapView_main = view.findViewById(R.id.ll_mapView_main);
            cv_all = view.findViewById(R.id.cv_all);
            ll_label = view.findViewById(R.id.ll_label);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            ct_showText = view.findViewById(R.id.ct_showText);
//            if(!isUIFormNeeded){
//                cv_all.setUseCompatPadding(true);
//            }

            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            }
            if (controlObject.getHint() != null) {
                tv_hint.setVisibility(View.VISIBLE);
                tv_hint.setText(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }
          /*  if (controlObject.getMapIcon() != null && !controlObject.getMapIcon().startsWith("http")) {
                controlObject.setMapIcon("https://www.gstatic.com/webp/gallery/1.jpg");

            }*/
            if (controlObject.getBaseMap() != null) {
                mapFrag = view.findViewById(R.id.map);
                mapFrag.onEnterAmbient(null);

            /*gMap = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.gMap);
            if (gMap != null) {
                gMap.onCreate(null);
                gMap.getMapAsync(this);
            }*/

                if (controlObject.isShowCurrentLocation()) {
                    startCaptureGPS();
                } else {
                    ll_mapView_main.setVisibility(View.VISIBLE);
                }
                if (controlObject.getMapHeight() != null && !controlObject.getMapHeight().isEmpty()) {
// Gets the layout params that will allow you to resize the layout
                    ViewGroup.LayoutParams params = ll_mapView_main.getLayoutParams();
// Changes the height and width to the specified *pixels*
//                    params.height = Integer.parseInt(controlObject.getMapHeight());
                    params.height = improveHelper.dpToPx(Integer.parseInt(controlObject.getMapHeight())); // in dp
//                params.height = 1000;

                    ll_mapView_main.setLayoutParams(params);
//                }

                    Log.d(TAG, "addMapControlStripHeight: " + controlObject.getMapHeight());
                }


                if (mapFrag != null) {
                    mapFrag.onCreate(null);
                    mapFrag.getMapAsync(this);
                }
            }


            tv_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startCaptureGPS();
                }
            });

            setDisplaySettings(context, tv_displayName, controlObject);

/*
            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams)  ll_control_ui.getLayoutParams();
            lpCtrlUI.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            lpCtrlUI.height = LinearLayout.LayoutParams.WRAP_CONTENT;

            LinearLayout.LayoutParams lpMapView = (LinearLayout.LayoutParams) mapFrag.getLayoutParams();
            lpMapView.width = pxToDPControlUI(Integer.parseInt("300"));
            lpMapView.height = pxToDPControlUI(Integer.parseInt("300"));
            ll_control_ui.setLayoutParams(lpCtrlUI);
            mapFrag.setLayoutParams(lpMapView);


            LinearLayout.LayoutParams lpCtrlUI = (LinearLayout.LayoutParams)  ll_control_ui.getLayoutParams();
            lpCtrlUI.width = LinearLayout.LayoutParams.MATCH_PARENT;
            lpCtrlUI.height = LinearLayout.LayoutParams.MATCH_PARENT;

            LinearLayout.LayoutParams lpMapView = (LinearLayout.LayoutParams) mapFrag.getLayoutParams();
            lpMapView.width = LinearLayout.LayoutParams.MATCH_PARENT;
            lpMapView.height = LinearLayout.LayoutParams.MATCH_PARENT;
            ll_control_ui.setLayoutParams(lpCtrlUI);
            mapFrag.setLayoutParams(lpMapView);

*/

            linearLayout.addView(view);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addMapControlStrip", e);
        }
    }

    public void setMap(MapView mapFrag) {
        mapFrag.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(context.getApplicationContext());
        mGoogleMap = googleMap;
        //asdf
        if (mGoogleMap != null) {
            mGoogleMap.clear();
        }
        if (controlObject.getMapView() != null) {

            if (controlObject.getMapView().equalsIgnoreCase("Satellite")) {
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            } else if (controlObject.getMapView().equalsIgnoreCase("Roads")) {
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }

        }
        // To load default map to India
        LatLng latLng = new LatLng(21.1613484, 78.932422);
//        mGoogleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(latLng, 0) );
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        mapViewTypesCoordinates();
//        mGoogleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(latLng, 4.0f) );
//        setMapView(controlObject.getRenderingTypeList().get(0).getItem().toString(),""+accuracy);
/*
        if (mapViewType != null && !mapViewType.isEmpty() && controlObject.getRenderingTypeList() != null && controlObject.getRenderingTypeList().size() > 0) {
            List<String> points = new ArrayList<>();
            String checkedNamesList = "";
            points = controlObject.getRenderingTypeList().get(0).getItem();
            if (mapViewType.equalsIgnoreCase(AppConstants.map_Multiple_Marker)) {
            } else if (mapViewType.equalsIgnoreCase(AppConstants.map_Multiple_Polylines) || mapViewType.equalsIgnoreCase(AppConstants.map_Polygon)) {
                for (int i = 0; i < points.size(); i++) {
                    checkedNamesList += points.get(i) + "^";
                }
                if (checkedNamesList != null && checkedNamesList.length() > 0 && checkedNamesList.charAt(checkedNamesList.length() - 1) == '^') {
                    checkedNamesList = checkedNamesList.substring(0, checkedNamesList.length() - 1);
                }
                points.add(checkedNamesList);
            } */
/*else if (mapViewType.equalsIgnoreCase(AppConstants.map_Polygon)) {

            }*//*

            setMapPonitsDynamically(mapViewType, points, controlObject.getMapIcon());
        }
*/

        //Sanjay
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                    Double dClickLat = latLng.latitude;
                    Double dClickLng = latLng.longitude;
                    SelectedGPS = dClickLat + "$" + dClickLng;
                    Log.d(TAG, "onMapClick: " + dClickLat + "," + dClickLng);
//                        if (dClickLat != 0.0 && dClickLng != 0.0) {
//                            AppConstants.MAP_MARKER_POSITION = Integer.parseInt(dClickLat+","+dClickLng);
//                        }
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                        if (SubformFlag) {
                            AppConstants.SF_Container_position = SubformPos;
                            AppConstants.Current_ClickorChangeTagName = SubformName;
                        }
                        ((MainActivity) context).ClickEvent(view);
                    }
                }

            }
        });


    }

    public LinearLayout getMapControlLayout() {
        return linearLayout;

    }

    public LinearLayout ll_mapView_main() {
        return ll_mapView_main;

    }


    private void startCaptureGPS() {
        try {
            if (isGPSON()) {
//            AppConstants.Current_ClickorChangeTagName = controlObject.getControlName();

                showProgress(view);

                gpsFinding();

            } else {
                confirmGPS();
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "startCaptureGPS", e);
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
            ImproveHelper.improveException(context, TAG, "confirmGPS", e);
        }
    }

    private void showProgress(View view) {
        try {
            final Handler handler = new Handler();
            progressBar = view.findViewById(R.id.progress_horizontal);
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
            ImproveHelper.improveException(context, TAG, "showProgress", e);
        }
    }

    public void gpsFinding() {
        try {
            initialiseTimer(maxTime, 1000);
            turnGPSOn();
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            mlistener = new MapControl.MyLocationListener();
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            locationManager.requestLocationUpdates(providerType, 500, 0, mlistener);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "gpsFinding", e);
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
                            if (mlistener != null) locationManager.removeUpdates(mlistener);

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
            ImproveHelper.improveException(context, TAG, "initialiseTimer", e);
        }
    }

    private void turnGPSOn() {
        try {
            String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (!provider.contains("gps")) { // if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                context.sendBroadcast(poke);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "turnGPSOn", e);
        }
    }

    public boolean isSimSupport() {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  //gets the current TelephonyManager
        return !(tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void gpsFailedCase() {
        try {
            tv_retry.setText("Retry Again To Get Location...");
            stop_progressbar = false;
            status = 0;
            if (progressBar.isAnimating()) {
                progressBar.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "gpsFailedCase", e);
        }
    }

    private void turnGPSOff() {
        try {
            String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

            if (provider.contains("gps")) { // if gps is enabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                context.sendBroadcast(poke);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "turnGPSOff", e);
        }
    }

    public void setMapView(String gpsString, String accuracy) {
        try {
            stopprogress();
            mapPoints.add(gpsString);
            String[] loc = gpsString.split("\\$");
            current_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));

//        LatLng current_location_2 = new LatLng(-33.920455, 18.466941);
//        LatLng current_location_2 = new LatLng(17.7084867, 83.3000947);
//            LatLng current_location_2 = new LatLng(17.735028, 83.318174); //SSS Bar
//            LatLng current_location_3 = new LatLng(17.7362538, 83.3204615); //maddilapalem
//            LatLng current_location_4 = new LatLng(17.7413811, 83.3249393); //iskathota

/*
        List<LatLng> latLngList = new ArrayList<>();
        latLngList.add(new LatLng(current_location.latitude, current_location.longitude));
        latLngList.add(new LatLng(current_location_2.latitude, current_location_2.longitude));
        latLngList.add(new LatLng(current_location_3.latitude, current_location_3.longitude));
        latLngList.add(new LatLng(current_location_4.latitude, current_location_4.longitude));

*/
            if (controlObject.isShowCurrentLocation()) {

                if (controlObject.getMapIcon() != null && !controlObject.getMapIcon().equalsIgnoreCase("Default")) {
                    Log.d(TAG, "setMapViewIcon: " + controlObject.getImagePath());
                    Glide.with(context).asBitmap().load(controlObject.getMapIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(new CustomTarget<Bitmap>(84, 84) {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                            if (resource != null) {
                                mGoogleMap.addMarker(new MarkerOptions().position(current_location).icon(BitmapDescriptorFactory.fromBitmap(resource)));
                            }
//
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
                    if(current_location!=null){
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_location, 15));
                    }

                } else { // default marker
                    mGoogleMap.addMarker(new MarkerOptions().position(current_location));
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_location, 15));
                }

            }

            if (controlObject.getMapIcon() != null && controlObject.getMapIcon().startsWith("http")) {
                Log.d(TAG, "setMapViewIcon: " + controlObject.getMapIcon());
                Glide.with(context).asBitmap().load(controlObject.getMapIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(new CustomTarget<Bitmap>(84, 84) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        if (resource != null) {
                            mGoogleMap.addMarker(new MarkerOptions().position(current_location).icon(BitmapDescriptorFactory.fromBitmap(resource)));
                        }
//
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_location, 15));
            }

/*
        Glide.with(context).load(typeOfButtonModel.get(position).getIconPath())
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .dontAnimate().into(holder.icon);
*/

//            setMapPonitsDynamically();

            List<String> pointsLocationList = new ArrayList<>();
            if (controlObject.getRenderingTypeList() != null && controlObject.getRenderingTypeList().size() > 0) {

                List<RenderingType> renderingTypesList = controlObject.getRenderingTypeList();
                String strRenderTypes = "";
                for (int i = 0; i < renderingTypesList.size(); i++) {

                    if (renderingTypesList.get(i).getType() != null) {
                        strRenderTypes = renderingTypesList.get(i).getType();
                        Log.d(TAG, "setMapViewRenderPoint: " + strRenderTypes);
                    }
//                if (renderingTypesList.get(i).getType().equalsIgnoreCase("Point")) {
                    Log.d(TAG, "setMapViewRenderItems: " + renderingTypesList.get(i).getItem());
                    pointsLocationList = renderingTypesList.get(i).getItem();
                    mapPoints = pointsLocationList;
//                }
                }


                for (int i = 0; i < pointsLocationList.size(); i++) {
                    String[] strPoints = null;
                    if (pointsLocationList.get(i).contains("$")) {
                        strPoints = pointsLocationList.get(i).split("\\$");
                        Log.d(TAG, "setMapViewLocationSplit: " + strPoints[0] + "-" + strPoints[1]);
                    } else {
                        strPoints = pointsLocationList.get(i).split("\\,");
                        Log.d(TAG, "setMapViewLocationSplit: " + strPoints[0] + "-" + strPoints[1]);
                    }


                    if (strPoints[0] != null && strPoints[1] != null) {

                        LatLng latLngLocations = new LatLng(Double.parseDouble(strPoints[0]), Double.parseDouble(strPoints[1]));

                        if (controlObject.getMapIcon() != null && !controlObject.getMapIcon().equalsIgnoreCase("Default")) {
                            Glide.with(context).asBitmap().load(controlObject.getMapIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(new CustomTarget<Bitmap>(84, 84) {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                    mGoogleMap.addMarker(new MarkerOptions().position(latLngLocations).icon(BitmapDescriptorFactory.fromBitmap(resource)));

                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                }
                            });
                        } else { // default marker
                            mGoogleMap.addMarker(new MarkerOptions().position(latLngLocations));
                        }


                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngLocations, 15));
                    }

                }

            }

    /*
        if (controlObject.getMapIcon() != null) {
            Glide.with(context)
                    .asBitmap()
                    .load(controlObject.getMapIcon())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                            mGoogleMap.addMarker(new MarkerOptions()
                                    .position(current_location)
                                    .icon(BitmapDescriptorFactory.fromBitmap(resource))

                                    .snippet(current_location.latitude + "," + current_location.longitude));
//
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_location, 15));
        } else { // default marker
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(current_location)
                    .title("Current Location")
                    .snippet(current_location.latitude + "," + current_location.longitude));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_location, 15));
        }
*/

   /*     mGoogleMap.addMarker(new MarkerOptions()
                .position(current_location)
                .title("Current Location")
                .snippet(current_location.latitude + "," + current_location.longitude));
        mGoogleMap.addMarker(new MarkerOptions()
                .position(current_location_2)
                .title("Location2")
                .snippet(current_location_2.latitude + "," + current_location_2.longitude));
        mGoogleMap.addMarker(new MarkerOptions()
                .position(current_location_3)
                .title("Location3")
                .snippet(current_location_3.latitude + "," + current_location_3.longitude));
        mGoogleMap.addMarker(new MarkerOptions()
                .position(current_location_4)
                .title("Location4")
                .snippet(current_location_4.latitude + "," + current_location_4.longitude));
   */

//        mGoogleMap.setMyLocationEnabled(true);

       /* //PolyLines working
        mGoogleMap.addPolyline(new PolylineOptions()
                .add(current_location, current_location_2)
                .width(5)
                .color(Color.RED));
        mGoogleMap.addPolyline(new PolylineOptions()
                .add(current_location, current_location_3)
                .width(5)
                .color(Color.RED));
        mGoogleMap.addPolyline(new PolylineOptions()
                .add(current_location, current_location_4)
                .width(5)
                .color(Color.RED));*/


            if (accuracy != null) {
                String str_accuracy = "Accuracy: " + accuracy;
//            tv_acccuracy.setText(str_accuracy);
            }

            mGoogleMap.setOnPolylineClickListener(this);
//        Polyline line = mGoogleMap.addPolyline(new PolylineOptions()
//                .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
//                .width(5)
//                .color(Color.RED));

            ll_mapView_main.setVisibility(View.VISIBLE);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            if (controlObject.isZoomControl()) {
                mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
                mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
                mGoogleMap.getUiSettings().setScrollGesturesEnabled(true);

            } else {
                mGoogleMap.getUiSettings().setZoomGesturesEnabled(false);
                mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
                mGoogleMap.getUiSettings().setScrollGesturesEnabled(false);
            }
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.resetMinMaxZoomPreference();
            PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);

//            mapFrag.performClick();
//            ll_mapView_main.performClick();

            controlObject.setText(getMapPoints());
            mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {

                    if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                        Double dClickLat = latLng.latitude;
                        Double dClickLng = latLng.longitude;
                        SelectedGPS = dClickLat + "$" + dClickLng;

                        Log.d(TAG, "onMapClick: " + dClickLat + "," + dClickLng);

//                        new MarkerOptions().position(current_location);
//                        if (marker.getTag() != null) {
//                            AppConstants.MAP_MARKER_POSITION = (Integer) marker.getTag();
//                        }
                        if (AppConstants.EventCallsFrom == 1) {
                            AppConstants.EventFrom_subformOrNot = SubformFlag;
                            if (SubformFlag) {
                                AppConstants.SF_Container_position = SubformPos;
                                AppConstants.Current_ClickorChangeTagName = SubformName;
                            }
                            ((MainActivity) context).ClickEvent(view);
                        }
                    }


                }
            });

            openBottomSheetOnMarkerClick(null, null, -1);
//        sucess();
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setMapView", e);
        }
    }

    private void stopprogress() {
        try {
            stop_progressbar = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(progressBar!=null){
                    progressBar.setVisibility(View.GONE);}
                }
            }, 1000);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "stopprogress", e);
        }
    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }

    @Override
    public void onPolylineClick(Polyline polyline) {

        if ((polyline.getPattern() == null) || (!polyline.getPattern().contains(DOT))) {
            polyline.setPattern(PATTERN_POLYLINE_DOTTED);
        } else {
            // The default pattern is a solid stroke.
            polyline.setPattern(null);
        }

        Toast.makeText(context, "Route type " + polyline.getTag().toString(), Toast.LENGTH_SHORT).show();

    }

    public void setMapPonitsDynamically(String ViewType, List<String> Points, String DefultMarker) {
        try {
            mapPoints = Points;
            final LatLngBounds.Builder builder = new LatLngBounds.Builder();

            switch (ViewType) {
                case AppConstants.map_Multiple_Marker:
                    for (int i = 0; i < Points.size(); i++) {
                        String[] loc = Points.get(i).split("\\$");
//                    String[] locA = Points.get(Points.size()-2).split("\\$");
                        LatLng temp_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));

//                    LatLng temp_location1 = new LatLng(Double.parseDouble(locA[0]), Double.parseDouble(locA[1]));

                        if (temp_location.latitude != 0.0 && temp_location.longitude != 0.0) {
                            System.out.println("controlObject.getMapIcon() ===" + controlObject.getMapIcon());

                            if (DefultMarker != null) {
                                Glide.with(context).asBitmap().load(DefultMarker).diskCacheStrategy(DiskCacheStrategy.ALL).into(new CustomTarget<Bitmap>(84, 84) {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(temp_location).icon(BitmapDescriptorFactory.fromBitmap(resource)));
//                                                marker.setTag(i);
//
                                        builder.include(marker.getPosition());
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                    }
                                });
                            } else if (controlObject.getMapIcon() != null && !controlObject.getMapIcon().equalsIgnoreCase("Default") && !controlObject.getMapIcon().trim().equalsIgnoreCase("")) {
                                Glide.with(context).asBitmap().load(controlObject.getMapIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(new CustomTarget<Bitmap>(84, 84) {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(temp_location).icon(BitmapDescriptorFactory.fromBitmap(resource)));
//                                                marker.setTag(i);
//
                                        builder.include(marker.getPosition());
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                    }
                                });
                            } else {
                                System.out.println("defult image ");
                                mGoogleMap.addMarker(new MarkerOptions().position(temp_location).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Location " + i));
                            }
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp_location, 8));
                        }
                    }
                    openBottomSheetOnMarkerClick(Points, null, -1);
                    break;
                case AppConstants.map_Multiple_Polylines:
                    for (int i = 0; i < Points.size(); i++) {
                        String[] locations = null;
                        if(Points.get(i).contains(",")){
                            locations = Points.get(i).split("\\,");
                        }else {
                            locations = Points.get(i).split("\\^");
                        }
                        for (int j = 0; j < locations.length - 1; j++) {
                            String[] loc = locations[j].split("\\$");
                            String[] loc1 = locations[j + 1].split("\\$");
                            LatLng temp_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
                            LatLng temp_location1 = new LatLng(Double.parseDouble(loc1[0]), Double.parseDouble(loc1[1]));
                            if (temp_location.latitude != 0.0 && temp_location.longitude != 0.0) {


                                if (DefultMarker != null) {
                                    Glide.with(context).asBitmap().load(DefultMarker).diskCacheStrategy(DiskCacheStrategy.ALL).into(new CustomTarget<Bitmap>(84, 84) {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(temp_location).icon(BitmapDescriptorFactory.fromBitmap(resource)));
//                                                marker.setTag(i);
//
                                            builder.include(marker.getPosition());
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {
                                        }
                                    });
                                    Glide.with(context).asBitmap().load(DefultMarker).diskCacheStrategy(DiskCacheStrategy.ALL).into(new CustomTarget<Bitmap>(84, 84) {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(temp_location1).icon(BitmapDescriptorFactory.fromBitmap(resource)));
//                                                marker.setTag(i);
//
                                            builder.include(marker.getPosition());
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {
                                        }
                                    });
                                } else if (controlObject.getMapIcon() != null && !controlObject.getMapIcon().equalsIgnoreCase("Default") && !controlObject.getMapIcon().trim().equalsIgnoreCase("")) {
                                    Glide.with(context).asBitmap().load(controlObject.getMapIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(new CustomTarget<Bitmap>(84, 84) {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(temp_location).icon(BitmapDescriptorFactory.fromBitmap(resource)));
//                                                marker.setTag(i);
//
                                            builder.include(marker.getPosition());
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {
                                        }
                                    });
                                    Glide.with(context).asBitmap().load(controlObject.getMapIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(new CustomTarget<Bitmap>(84, 84) {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(temp_location1).icon(BitmapDescriptorFactory.fromBitmap(resource)));
//                                                marker.setTag(i);
//
                                            builder.include(marker.getPosition());
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {
                                        }
                                    });
                                } else {
                                    System.out.println("defult image ");
                                    mGoogleMap.addMarker(new MarkerOptions().position(temp_location).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Location " + i));
                                    mGoogleMap.addMarker(new MarkerOptions().position(temp_location1).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Location " + i));
                                }
                                mGoogleMap.addPolyline(new PolylineOptions().add(temp_location, temp_location1).width(5));
                                //.color(getColor(R.color.lightBlue)));

                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp_location, 16));
                            }
                        }
                    }
                    openBottomSheetOnMarkerClick(Points, null, -1);
                    break;
                case AppConstants.map_Polygon:
                    for (int i = 0; i < Points.size(); i++) {
                        String[] locations = null;
                        if(Points.get(i).contains("\\^")){
                            locations = Points.get(i).split("\\^");
                        }else if(Points.get(i).contains(",")){
                            locations = Points.get(i).split("\\,");
                        }
//                        String[] locations = Points.get(i).split("\\^");
                        List<LatLng> list_poly = new ArrayList<LatLng>();

                        for (int j = 0; j < locations.length; j++) {
                            String[] loc = locations[j].split("\\$");
                            LatLng temp_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
                            list_poly.add(temp_location);
                        }
                        if (list_poly.size() > 1) {
                            mGoogleMap.addPolygon(new PolygonOptions().clickable(true).addAll(list_poly));
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(list_poly.get(list_poly.size() - 1), 16));
                        }

                    }
                    openBottomSheetOnMarkerClick(Points, null, -1);
                    break;
                default:
                    break;
            }

            ll_mapView_main.setVisibility(View.VISIBLE);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            if (controlObject.isZoomControl()) {
                mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
                mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
                mGoogleMap.getUiSettings().setAllGesturesEnabled(true);

            } else {
                mGoogleMap.getUiSettings().setZoomGesturesEnabled(false);
                mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
            }
            mGoogleMap.setMyLocationEnabled(true);
            //mGoogleMap.resetMinMaxZoomPreference();

            mapFrag.performContextClick();
            mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                    if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                        Double dClickLat = latLng.latitude;
                        Double dClickLng = latLng.longitude;
                        SelectedGPS = dClickLat + "$" + dClickLng;
                        Log.d(TAG, "onMapClick: " + dClickLat + "," + dClickLng);
//                        if (dClickLat != 0.0 && dClickLng != 0.0) {
//                            AppConstants.MAP_MARKER_POSITION = Integer.parseInt(dClickLat+","+dClickLng);
//                        }
                        if (AppConstants.EventCallsFrom == 1) {
                            AppConstants.EventFrom_subformOrNot = SubformFlag;
                            if (SubformFlag) {
                                AppConstants.SF_Container_position = SubformPos;
                                AppConstants.Current_ClickorChangeTagName = SubformName;
                            }
                            ((MainActivity) context).ClickEvent(view);
                        }
                    }

                }
            });

            controlObject.setText(getMapPoints());
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setMapPonitsDynamically", e);
        }
    }

    public void clear() {
        mGoogleMap.clear();
    }

    public String getMapViewType() {
        return mapViewType;
    }

    public void setMapViewType(String propertyText) {
            controlObject.setMapViewType(propertyText);
            mapViewType = propertyText;
    }
    public void setRenderingTypeList(List<RenderingType> renderingTypeList) {
            controlObject.setRenderingTypeList(renderingTypeList);
            gpsData = renderingTypeList.get(0).getItem().get(0);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapFrag.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapFrag.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mapFrag.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapFrag.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapFrag.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapFrag.onStart();
    }

    public GoogleMap getGoogleMap() {
        return mGoogleMap;
    }

    /*public void setMapMarkersDynamically(String RenderingType, String DefultMarkerImagePath, List<String> Points,
                                         List<String> ConditionColumn, String Operator,
                                         final List<ImageAdvanced_Mapped_Item> MarkerAdvanced_Items,
                                         List<String> PopupData,
                                         HashMap<String, List<String>> hash_popupdata) {
        try {
            final LatLngBounds.Builder builder = new LatLngBounds.Builder();

            switch (RenderingType) {
                case AppConstants.map_Multiple_Marker:
                    mGoogleMap.clear();
                    for (i = 0; i < Points.size(); i++) {
//                    for (i = 0; i < 4; i++) {
                        String Point = Points.get(i);
                        if (Points.get(i).contains("^")) {
                            Point = Points.get(i).substring(0, Points.get(i).indexOf("^"));
                        }
                        String[] loc = Point.split("\\$");
                        if (loc.length > 1) {
                            temp_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
                        } else {
                            temp_location = new LatLng(0.0, 0.0);
                        }

                        if (temp_location.latitude != 0.0 && temp_location.longitude != 0.0) {
                            if (MarkerAdvanced_Items != null && MarkerAdvanced_Items.size() > 0) {
                                boolean Matchflag = false;
                                for (int j = 0; j < MarkerAdvanced_Items.size(); j++) {
                                    if (Operator.equalsIgnoreCase(AppConstants.Conditions_Equals)) {
                                        if (ConditionColumn.get(i).equalsIgnoreCase(MarkerAdvanced_Items.get(j).getImageAdvanced_Value())) {
                                            setMapImage(MarkerAdvanced_Items.get(j).getImageAdvanced_ImagePath(), DefultMarkerImagePath, temp_location, builder, PopupData, hash_popupdata, i);
                                            Matchflag = true;
                                            break;
                                        }
                                    } else if (Operator.equalsIgnoreCase(AppConstants.Conditions_NotEquals)) {
                                        if (!ConditionColumn.get(i).equalsIgnoreCase(MarkerAdvanced_Items.get(j).getImageAdvanced_Value())) {
                                            setMapImage(MarkerAdvanced_Items.get(j).getImageAdvanced_ImagePath(), DefultMarkerImagePath, temp_location, builder, PopupData, hash_popupdata, i);
                                            Matchflag = true;
                                            break;
                                        }
                                    } else if (Operator.equalsIgnoreCase(AppConstants.Conditions_lessThan)) {

                                    } else if (Operator.equalsIgnoreCase(AppConstants.Conditions_GreaterThan)) {

                                    }
                                }
                                if (!Matchflag) {
                                    setMapImage(null, DefultMarkerImagePath, temp_location, builder, PopupData, hash_popupdata, i);
                                }
                            } else if (DefultMarkerImagePath != null) {
                                Glide.with(context)
                                        .asBitmap()
                                        .load(DefultMarkerImagePath)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new CustomTarget<Bitmap>(84,84) {
                                            @Override
                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                Log.d(TAG, "onResourceReady_test: "+temp_location);
                                                Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                                                        .position(temp_location)
                                                        .icon(BitmapDescriptorFactory.fromBitmap(resource))

                                                        .snippet(temp_location.latitude + "," + temp_location.longitude));
//
                                                marker.setTag(i);
                                                if (PopupData != null && PopupData.size() > 0) {
                                                    SanCustomInfoWindowAdapter customInfoWindowAdapter = new SanCustomInfoWindowAdapter(context, PopupData, hash_popupdata, i);
                                                    mGoogleMap.setInfoWindowAdapter(customInfoWindowAdapter);
//                                                marker.showInfoWindow();
                                                }
                                                mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                                    @Override
                                                    public void onInfoWindowClick(Marker marker) {
                                                        ImproveHelper.showToast(context, marker.getPosition().latitude + "," + marker.getPosition().longitude);
                                                        SelectedGPS = marker.getPosition().latitude + "," + marker.getPosition().longitude;
                                                        if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                                                            if (AppConstants.EventCallsFrom == 1) {
                                                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                                                if (SubformFlag) {
                                                                    AppConstants.SF_Container_position = SubformPos;
                                                                    AppConstants.Current_ClickorChangeTagName = SubformName;

                                                                }
                                                                AppConstants.GlobalObjects.setCurrent_GPS("");
                                                                ((MainActivity) context).ClickEvent(view);
                                                            }
                                                        }
                                                    }
                                                });

                                                builder.include(marker.getPosition());
                                            }

                                            @Override
                                            public void onLoadCleared(@Nullable Drawable placeholder) {
                                            }
                                        });
                            } else if (controlObject.getMapIcon() != null &&
                                    !controlObject.getMapIcon().equalsIgnoreCase("Default") &&
                                    !controlObject.getMapIcon().trim().equalsIgnoreCase("")) {
                                Glide.with(context)
                                        .asBitmap()
                                        .load(controlObject.getMapIcon())
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new CustomTarget<Bitmap>(84,84) {
                                            @Override
                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                                Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                                                        .position(temp_location)
                                                        .icon(BitmapDescriptorFactory.fromBitmap(resource))

                                                        .snippet(temp_location.latitude + "," + temp_location.longitude));
//
                                                marker.setTag(i);

                                                if (PopupData != null && PopupData.size() > 0) {
                                                    SanCustomInfoWindowAdapter customInfoWindowAdapter = new SanCustomInfoWindowAdapter(context, PopupData, hash_popupdata, i);
                                                    mGoogleMap.setInfoWindowAdapter(customInfoWindowAdapter);
//                                                marker.showInfoWindow();
                                                }
                                                mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                                    @Override
                                                    public void onInfoWindowClick(Marker marker) {
                                                        ImproveHelper.showToast(context, marker.getPosition().latitude + "," + marker.getPosition().longitude);
                                                        SelectedGPS = marker.getPosition().latitude + "," + marker.getPosition().longitude;
                                                        if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                                                            if (AppConstants.EventCallsFrom == 1) {
                                                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                                                if (SubformFlag) {
                                                                    AppConstants.SF_Container_position = SubformPos;
                                                                    AppConstants.Current_ClickorChangeTagName = SubformName;

                                                                }
                                                                AppConstants.GlobalObjects.setCurrent_GPS("");
                                                                ((MainActivity) context).ClickEvent(view);
                                                            }
                                                        }
                                                    }
                                                });
                                                builder.include(marker.getPosition());
                                            }

                                            @Override
                                            public void onLoadCleared(@Nullable Drawable placeholder) {
                                            }
                                        });
                            } else {

                                Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                                        .position(temp_location)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                                        .title("Location " + i)
                                        .snippet(temp_location.latitude + "," + temp_location.longitude));
                                marker.setTag(i);

                                if (PopupData != null && PopupData.size() > 0) {
                                    SanCustomInfoWindowAdapter customInfoWindowAdapter = new SanCustomInfoWindowAdapter(context, PopupData, hash_popupdata, i);
                                    mGoogleMap.setInfoWindowAdapter(customInfoWindowAdapter);
//                                marker.showInfoWindow();
                                }
                                mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                    @Override
                                    public void onInfoWindowClick(Marker marker) {
                                        ImproveHelper.showToast(context, (hash_popupdata.get(PopupData.get(0))).get(Integer.parseInt(marker.getTag().toString())));
                                        SelectedGPS = marker.getPosition().latitude + "," + marker.getPosition().longitude;
                                        if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                                            if (AppConstants.EventCallsFrom == 1) {
                                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                                if (SubformFlag) {
                                                    AppConstants.SF_Container_position = SubformPos;
                                                    AppConstants.Current_ClickorChangeTagName = SubformName;

                                                }
                                                AppConstants.GlobalObjects.setCurrent_GPS("");
                                                ((MainActivity) context).ClickEvent(view);
                                            }
                                        }
                                    }
                                });
                                builder.include(marker.getPosition());
                            }
//                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp_location, 16));
//                            mapFrag.onResume();

                        }
                    }
                    String[] first_point = Points.get(0).split("\\$");
                    LatLng first_location = new LatLng(Double.parseDouble(first_point[0]), Double.parseDouble(first_point[1]));
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom( first_location, 16));
                    mapFrag.onResume();
                    break;
                case AppConstants.map_Multiple_Polylines:
                    mGoogleMap.clear();
                    for (int i = 0; i < Points.size(); i++) {
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
                                mapFrag.onResume();
                            }
                        }
                    }
                    break;
                case AppConstants.map_Polygon:
                    mGoogleMap.clear();
                    try {
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
                                        .strokeColor(Color.BLUE)
                                        .addAll(list_poly));
                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(list_poly.get(list_poly.size() - 1), 16));
                                mapFrag.onResume();
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }


            ll_mapView_main.setVisibility(View.VISIBLE);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            if (controlObject.isZoomControl()) {
                mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
                mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            } else {
                mGoogleMap.getUiSettings().setZoomGesturesEnabled(false);
                mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
            }
            mGoogleMap.setMyLocationEnabled(true);
            //mGoogleMap.resetMinMaxZoomPreference();

            mapFrag.performContextClick();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setMapMarkersDynamically", e);
        }
    }*/
    public void setMapMarkersDynamically(String RenderingType, String DefultMarkerImagePath,
                                         List<String> Points, List<String> ConditionColumn,
                                         String Operator, final List<ImageAdvanced_Mapped_Item> MarkerAdvanced_Items,
                                         List<String> PopupData, HashMap<String, List<String>> hash_popupdata,boolean replace) {
        try {

            mapPoints = Points;
            for (int j = 0; j <mapPoints.size() ; j++) {
                if(mapPoints.get(j).contentEquals("")){
                    mapPoints.remove(j);
                    j--;
                }
                if(!mapPoints.get(j).contains("$")){
                    mapPoints.remove(j);
                    j--;
                }


            }
            if(replace) {
                mGoogleMap.clear();
            }
            switch (RenderingType) {
                case AppConstants.map_Multiple_Marker:
                    mapPointsIndex = 0;
                    final LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    placeMultipleMarkersOnMap(builder, RenderingType, DefultMarkerImagePath, Points, ConditionColumn, Operator, MarkerAdvanced_Items, PopupData, hash_popupdata);
                    break;
                case AppConstants.map_Multiple_Polylines:
//                    mGoogleMap.clear();
                    String RenderingType_Line = "Point";
                    String DefultMarkerImagePath_Line = DefultMarkerImagePath;
                    List<String> ConditionColumn_Line = ConditionColumn;
                    String Operator_Line = Operator;
                    List<ImageAdvanced_Mapped_Item> MarkerAdvanced_Items_Line = MarkerAdvanced_Items;
                    List<String> PopupData_Line = PopupData;
                    List<String> Points_Line = new ArrayList<>();
                    HashMap<String, List<String>> hash_popupdata_Line = new HashMap<>();
                    for (int i = 0; i < Points.size(); i++) {
                        String[] locations = Points.get(i).split("\\^");
                        for (int j = 0; j < locations.length; j++) {
                            Points_Line.add(locations[j]);
                            for (int k = 0; k < PopupData_Line.size(); k++) {
                                List<String> temp = hash_popupdata.get(PopupData_Line.get(k));//i pos
                                if (hash_popupdata_Line.containsKey(PopupData_Line.get(k))) {
                                    List<String> temp_ = hash_popupdata_Line.get(PopupData_Line.get(k));
                                    temp_.add(temp.get(i));
                                    hash_popupdata_Line.put(PopupData_Line.get(k), temp_);
                                } else {
                                    List<String> temp_ = new ArrayList<>();
                                    temp_.add(temp.get(i));
                                    hash_popupdata_Line.put(PopupData_Line.get(k), temp_);
                                }
                            }
                        }
                        for (int j = 0; j < locations.length - 1; j++) {
                            String[] loc = locations[j].split("\\$");
                            String[] loc1 = locations[j + 1].split("\\$");
                            LatLng temp_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
                            LatLng temp_location1 = new LatLng(Double.parseDouble(loc1[0]), Double.parseDouble(loc1[1]));
                            if (temp_location.latitude != 0.0 && temp_location.longitude != 0.0) {
                                mGoogleMap.addPolyline(new PolylineOptions().add(temp_location, temp_location1).width(5));
                                //.color(getColor(R.color.lightBlue)));
                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp_location, 16));
                                mapFrag.onResume();
                            }
                        }
                    }
                    //Line Markers
                    mapPointsIndex = 0;
                    final LatLngBounds.Builder builder_Line = new LatLngBounds.Builder();
                    placeMultipleMarkersOnMap(builder_Line, RenderingType_Line, DefultMarkerImagePath_Line,
                            Points_Line, ConditionColumn_Line, Operator_Line, MarkerAdvanced_Items_Line,
                            PopupData_Line, hash_popupdata_Line);

                    break;
                case AppConstants.map_Polygon:
//                    mGoogleMap.clear();
                    try {
                        for (int i = 0; i < Points.size(); i++) {
                            String[] locations = Points.get(i).split("\\^");
                            List<LatLng> list_poly = new ArrayList<LatLng>();

                            for (int j = 0; j < locations.length; j++) {
                                String[] loc = locations[j].split("\\$");
                                LatLng temp_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
                                list_poly.add(temp_location);
                            }
                            if (list_poly.size() > 1) {
                                mGoogleMap.addPolygon(new PolygonOptions().clickable(true).strokeColor(Color.BLUE).addAll(list_poly));
                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(list_poly.get(list_poly.size() - 1), 16));
                                mapFrag.onResume();
                            }

                        }
                        mapPointsIndex = 0;
                        final LatLngBounds.Builder builder1 = new LatLngBounds.Builder();
                        placeMultipleMarkersOnMap(builder1, RenderingType, DefultMarkerImagePath, Points, ConditionColumn, Operator, MarkerAdvanced_Items, PopupData, hash_popupdata);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }

            controlObject.setText(getMapPoints());
            ll_mapView_main.setVisibility(View.VISIBLE);
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
            ImproveHelper.improveException(context, TAG, "setMapMarkersDynamically", e);
        }
    }

    private void placeLineMarkersOnMap(LatLngBounds.Builder builder, String DefultMarkerImagePath,
                                       String[] loc,
                                       List<String> ConditionColumn, String Operator, final List<ImageAdvanced_Mapped_Item> MarkerAdvanced_Items, List<String> PopupData, HashMap<String, List<String>> hash_popupdata) {

        if (loc.length > 1) {
            temp_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
        } else {
            temp_location = new LatLng(0.0, 0.0);
        }

        if (temp_location.latitude != 0.0 && temp_location.longitude != 0.0) {
            if (MarkerAdvanced_Items != null && MarkerAdvanced_Items.size() > 0) {
                boolean Matchflag = false;
                for (int j = 0; j < MarkerAdvanced_Items.size(); j++) {
                    if (Operator.equalsIgnoreCase(AppConstants.Conditions_Equals)) {
                        if (ConditionColumn.get(i).equalsIgnoreCase(MarkerAdvanced_Items.get(j).getImageAdvanced_Value())) {
                            setMapImage(MarkerAdvanced_Items.get(j).getImageAdvanced_ImagePath(), DefultMarkerImagePath, temp_location, builder, PopupData, hash_popupdata, i);
                            Matchflag = true;
                            break;
                        }
                    } else if (Operator.equalsIgnoreCase(AppConstants.Conditions_NotEquals)) {
                        if (!ConditionColumn.get(i).equalsIgnoreCase(MarkerAdvanced_Items.get(j).getImageAdvanced_Value())) {
                            setMapImage(MarkerAdvanced_Items.get(j).getImageAdvanced_ImagePath(), DefultMarkerImagePath, temp_location, builder, PopupData, hash_popupdata, i);
                            Matchflag = true;
                            break;
                        }
                    } else if (Operator.equalsIgnoreCase(AppConstants.Conditions_lessThan)) {

                    } else if (Operator.equalsIgnoreCase(AppConstants.Conditions_GreaterThan)) {

                    }
                }
                if (!Matchflag) {
                    setMapImage(null, DefultMarkerImagePath, temp_location, builder, PopupData, hash_popupdata, i);
                }
            } else if (DefultMarkerImagePath != null) {
                Glide.with(context).asBitmap().load(DefultMarkerImagePath).diskCacheStrategy(DiskCacheStrategy.ALL).into(new CustomTarget<Bitmap>(84, 84) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Log.d(TAG, "onResourceReady_test: " + temp_location);
                        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(temp_location).icon(BitmapDescriptorFactory.fromBitmap(resource)));
//
                        marker.setTag(mapPointsIndex);
                        if (PopupData != null && PopupData.size() > 0) {
                            openBottomSheetOnMarkerClick(PopupData, hash_popupdata, i);
                                   /* SanCustomInfoWindowAdapter customInfoWindowAdapter = new SanCustomInfoWindowAdapter(context, PopupData, hash_popupdata, i);
                                    mGoogleMap.setInfoWindowAdapter(customInfoWindowAdapter);*/
//                                                marker.showInfoWindow();
                        }
                        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                ImproveHelper.showToast(context, marker.getPosition().latitude + "," + marker.getPosition().longitude);
                                SelectedGPS = marker.getPosition().latitude + "," + marker.getPosition().longitude;
                                if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                                    if (marker.getTag() != null) {
                                        AppConstants.MAP_MARKER_POSITION = (Integer) marker.getTag();
                                    }
                                    if (AppConstants.EventCallsFrom == 1) {
                                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                                        if (SubformFlag) {
                                            AppConstants.SF_Container_position = SubformPos;
                                            AppConstants.Current_ClickorChangeTagName = SubformName;

                                        }
                                        ((MainActivity) context).ClickEvent(view);
                                    }
                                }
                            }
                        });

                        builder.include(marker.getPosition());
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
            } else if (controlObject.getMapIcon() != null && !controlObject.getMapIcon().equalsIgnoreCase("Default") && !controlObject.getMapIcon().trim().equalsIgnoreCase("")) {
                Glide.with(context).asBitmap().load(controlObject.getMapIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(new CustomTarget<Bitmap>(84, 84) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(temp_location).icon(BitmapDescriptorFactory.fromBitmap(resource)));
//
                        marker.setTag(mapPointsIndex);

                        if (PopupData != null && PopupData.size() > 0) {
                            openBottomSheetOnMarkerClick(PopupData, hash_popupdata, i);
/*
                                    SanCustomInfoWindowAdapter customInfoWindowAdapter = new SanCustomInfoWindowAdapter(context, PopupData, hash_popupdata, i);
                                    mGoogleMap.setInfoWindowAdapter(customInfoWindowAdapter);
*/
//                                                marker.showInfoWindow();
                        }
                        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                ImproveHelper.showToast(context, marker.getPosition().latitude + "," + marker.getPosition().longitude);
                                SelectedGPS = marker.getPosition().latitude + "," + marker.getPosition().longitude;
                                if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                                    if (marker.getTag() != null) {
                                        AppConstants.MAP_MARKER_POSITION = (Integer) marker.getTag();
                                    }
                                    if (AppConstants.EventCallsFrom == 1) {
                                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                                        if (SubformFlag) {
                                            AppConstants.SF_Container_position = SubformPos;
                                            AppConstants.Current_ClickorChangeTagName = SubformName;

                                        }
                                        AppConstants.GlobalObjects.setCurrent_GPS("");
                                        ((MainActivity) context).ClickEvent(view);
                                    }
                                }
                            }
                        });
                        builder.include(marker.getPosition());

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
            } else {

                Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(temp_location).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Location " + mapPointsIndex));
                marker.setTag(mapPointsIndex);

                if (PopupData != null && PopupData.size() > 0) {
                    openBottomSheetOnMarkerClick(PopupData, hash_popupdata, i);
/*
                    SanCustomInfoWindowAdapter customInfoWindowAdapter = new SanCustomInfoWindowAdapter(context, PopupData, hash_popupdata, i);
                    mGoogleMap.setInfoWindowAdapter(customInfoWindowAdapter);
*/
//                                marker.showInfoWindow();
                }
                mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        ImproveHelper.showToast(context, (hash_popupdata.get(PopupData.get(0))).get(Integer.parseInt(marker.getTag().toString())));
                        SelectedGPS = marker.getPosition().latitude + "," + marker.getPosition().longitude;
                        if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                            if (marker.getTag() != null) {
                                AppConstants.MAP_MARKER_POSITION = (Integer) marker.getTag();
                            }
                            if (AppConstants.EventCallsFrom == 1) {
                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                if (SubformFlag) {
                                    AppConstants.SF_Container_position = SubformPos;
                                    AppConstants.Current_ClickorChangeTagName = SubformName;

                                }
                                AppConstants.GlobalObjects.setCurrent_GPS("");
                                ((MainActivity) context).ClickEvent(view);
                            }
                        }
                    }
                });
                builder.include(marker.getPosition());

            }
        }

        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                    Double dClickLat = latLng.latitude;
                    Double dClickLng = latLng.longitude;
                    SelectedGPS = dClickLat + "$" + dClickLng;
                    Log.d(TAG, "onMapClick: " + dClickLat + "," + dClickLng);
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                        if (SubformFlag) {
                            AppConstants.SF_Container_position = SubformPos;
                            AppConstants.Current_ClickorChangeTagName = SubformName;
                        }
                        ((MainActivity) context).ClickEvent(view);
                    }
                }

            }
        });
    }


    private void placeMultipleMarkersOnMap(LatLngBounds.Builder builder, String RenderingType, String DefultMarkerImagePath, List<String> Points, List<String> ConditionColumn, String Operator, final List<ImageAdvanced_Mapped_Item> MarkerAdvanced_Items, List<String> PopupData, HashMap<String, List<String>> hash_popupdata) {

        mapPoints = Points;
        String Point = Points.get(mapPointsIndex);
        if (Points.get(mapPointsIndex).contains("^")) {
            Point = Points.get(mapPointsIndex).substring(0, Points.get(mapPointsIndex).indexOf("^"));
        }
        String[] loc = Point.split("\\$");
        if (loc.length > 1) {
            temp_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
        } else {
            temp_location = new LatLng(0.0, 0.0);
        }

        if (temp_location.latitude != 0.0 && temp_location.longitude != 0.0) {
            if (MarkerAdvanced_Items != null && MarkerAdvanced_Items.size() > 0) {
                boolean Matchflag = false;
                for (int j = 0; j < MarkerAdvanced_Items.size(); j++) {
                    if (Operator.equalsIgnoreCase(AppConstants.Conditions_Equals)) {
                        if (ConditionColumn.get(i).equalsIgnoreCase(MarkerAdvanced_Items.get(j).getImageAdvanced_Value())) {
                            setMapImage(MarkerAdvanced_Items.get(j).getImageAdvanced_ImagePath(), DefultMarkerImagePath, temp_location, builder, PopupData, hash_popupdata, i);
                            Matchflag = true;
                            break;
                        }
                    } else if (Operator.equalsIgnoreCase(AppConstants.Conditions_NotEquals)) {
                        if (!ConditionColumn.get(i).equalsIgnoreCase(MarkerAdvanced_Items.get(j).getImageAdvanced_Value())) {
                            setMapImage(MarkerAdvanced_Items.get(j).getImageAdvanced_ImagePath(), DefultMarkerImagePath, temp_location, builder, PopupData, hash_popupdata, i);
                            Matchflag = true;
                            break;
                        }
                    } else if (Operator.equalsIgnoreCase(AppConstants.Conditions_lessThan)) {

                    } else if (Operator.equalsIgnoreCase(AppConstants.Conditions_GreaterThan)) {

                    }
                }
                if (!Matchflag) {
                    setMapImage(null, DefultMarkerImagePath, temp_location, builder, PopupData, hash_popupdata, i);
                }

                if (mapPointsIndex != Points.size() - 1) {
                    mapPointsIndex++;
                    placeMultipleMarkersOnMap(builder, RenderingType, DefultMarkerImagePath, Points, ConditionColumn, Operator, MarkerAdvanced_Items, PopupData, hash_popupdata);
                }

            }/* else if (DefultMarkerImagePath != null) {
                Glide.with(context).asBitmap().load(DefultMarkerImagePath).diskCacheStrategy(DiskCacheStrategy.ALL).into(new CustomTarget<Bitmap>(84, 84) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Log.d(TAG, "onResourceReady_test: " + temp_location);
                        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(temp_location).icon(BitmapDescriptorFactory.fromBitmap(resource)));
//
                        marker.setTag(mapPointsIndex);
                        if (PopupData != null && PopupData.size() > 0) {
                            openBottomSheetOnMarkerClick(PopupData, hash_popupdata, i);
                                   *//* SanCustomInfoWindowAdapter customInfoWindowAdapter = new SanCustomInfoWindowAdapter(context, PopupData, hash_popupdata, i);
                                    mGoogleMap.setInfoWindowAdapter(customInfoWindowAdapter);*//*
//                                                marker.showInfoWindow();
                        }
                        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                ImproveHelper.showToast(context, marker.getPosition().latitude + "," + marker.getPosition().longitude);
                                SelectedGPS = marker.getPosition().latitude + "," + marker.getPosition().longitude;
                                if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                                    if (marker.getTag() != null) {
                                        AppConstants.MAP_MARKER_POSITION = (Integer) marker.getTag();
                                    }
                                    if (AppConstants.EventCallsFrom == 1) {
                                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                                        if (SubformFlag) {
                                            AppConstants.SF_Container_position = SubformPos;
                                            AppConstants.Current_ClickorChangeTagName = SubformName;

                                        }
                                        ((MainActivity) context).ClickEvent(view);
                                    }
                                }
                            }
                        });

                        builder.include(marker.getPosition());

                        if (mapPointsIndex != Points.size() - 1) {
                            mapPointsIndex++;
                            placeMultipleMarkersOnMap(builder, RenderingType, DefultMarkerImagePath, Points, ConditionColumn, Operator, MarkerAdvanced_Items, PopupData, hash_popupdata);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
            }*/ else if (controlObject.getMapIcon() != null && !controlObject.getMapIcon().equalsIgnoreCase("Default") && !controlObject.getMapIcon().trim().equalsIgnoreCase("")) {
                Glide.with(context).asBitmap().load(controlObject.getMapIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(new CustomTarget<Bitmap>(84, 84) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(temp_location).icon(BitmapDescriptorFactory.fromBitmap(resource)));
//
                        marker.setTag(mapPointsIndex);

                        if (PopupData != null && PopupData.size() > 0) {
                            openBottomSheetOnMarkerClick(PopupData, hash_popupdata, i);
/*
                                    SanCustomInfoWindowAdapter customInfoWindowAdapter = new SanCustomInfoWindowAdapter(context, PopupData, hash_popupdata, i);
                                    mGoogleMap.setInfoWindowAdapter(customInfoWindowAdapter);
*/
//                                                marker.showInfoWindow();
                        }
                        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                ImproveHelper.showToast(context, marker.getPosition().latitude + "," + marker.getPosition().longitude);
                                SelectedGPS = marker.getPosition().latitude + "," + marker.getPosition().longitude;
                                if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                                    if (marker.getTag() != null) {
                                        AppConstants.MAP_MARKER_POSITION = (Integer) marker.getTag();
                                    }
                                    if (AppConstants.EventCallsFrom == 1) {
                                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                                        if (SubformFlag) {
                                            AppConstants.SF_Container_position = SubformPos;
                                            AppConstants.Current_ClickorChangeTagName = SubformName;

                                        }
                                        AppConstants.GlobalObjects.setCurrent_GPS("");
                                        ((MainActivity) context).ClickEvent(view);
                                    }
                                }
                            }
                        });
                        builder.include(marker.getPosition());
                        if (mapPointsIndex != Points.size() - 1) {
                            mapPointsIndex++;
                            placeMultipleMarkersOnMap(builder, RenderingType, DefultMarkerImagePath, Points, ConditionColumn, Operator, MarkerAdvanced_Items, PopupData, hash_popupdata);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
            } else {

                Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(temp_location).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Location " + mapPointsIndex));
                marker.setTag(mapPointsIndex);

                if (PopupData != null && PopupData.size() > 0) {
                    openBottomSheetOnMarkerClick(PopupData, hash_popupdata, i);
/*
                    SanCustomInfoWindowAdapter customInfoWindowAdapter = new SanCustomInfoWindowAdapter(context, PopupData, hash_popupdata, i);
                    mGoogleMap.setInfoWindowAdapter(customInfoWindowAdapter);
*/
//                                marker.showInfoWindow();
                }
                mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        ImproveHelper.showToast(context, (hash_popupdata.get(PopupData.get(0))).get(Integer.parseInt(marker.getTag().toString())));
                        SelectedGPS = marker.getPosition().latitude + "," + marker.getPosition().longitude;
                        if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                            if (marker.getTag() != null) {
                                AppConstants.MAP_MARKER_POSITION = (Integer) marker.getTag();
                            }
                            if (AppConstants.EventCallsFrom == 1) {
                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                if (SubformFlag) {
                                    AppConstants.SF_Container_position = SubformPos;
                                    AppConstants.Current_ClickorChangeTagName = SubformName;

                                }
                                AppConstants.GlobalObjects.setCurrent_GPS("");
                                ((MainActivity) context).ClickEvent(view);
                            }
                        }
                    }
                });
                builder.include(marker.getPosition());
                if (mapPointsIndex != Points.size() - 1) {
                    mapPointsIndex++;
                    placeMultipleMarkersOnMap(builder, RenderingType, DefultMarkerImagePath, Points, ConditionColumn, Operator, MarkerAdvanced_Items, PopupData, hash_popupdata);
                }
            }
//                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp_location, 16));
//                            mapFrag.onResume();

        }
        String firstPoint = Points.get(0);
        if (firstPoint.contains("^")) {
            firstPoint = firstPoint.substring(0, firstPoint.indexOf("^"));
        }
        if(firstPoint.contains("$")){
            String[] first_point = firstPoint.split("\\$");
            LatLng first_location = new LatLng(Double.parseDouble(first_point[0]), Double.parseDouble(first_point[1]));
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom( first_location, 16)); // default
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(first_location, 5.5f)); // ev stations
        }



        /*Four*/
        controlObject.setText(getMapPoints());
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));


                if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                    Double dClickLat = latLng.latitude;
                    Double dClickLng = latLng.longitude;
                    SelectedGPS = dClickLat + "$" + dClickLng;
                    Log.d(TAG, "onMapClick: " + dClickLat + "," + dClickLng);
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                        if (SubformFlag) {
                            AppConstants.SF_Container_position = SubformPos;
                            AppConstants.Current_ClickorChangeTagName = SubformName;
                        }
                        ((MainActivity) context).ClickEvent(view);
                    }
                }

            }
        });
        mapFrag.onResume(); // two
    }

    public void setMapImage(String url, String defaultUrl, LatLng temp_location, LatLngBounds.Builder builder, List<String> PopupData, HashMap<String, List<String>> hash_popupdata, int pos) {
        try {
            if (url != null) {
                Glide.with(context).asBitmap().load(url).placeholder(Glide.with(context).load(defaultUrl).getPlaceholderDrawable())
//               .thumbnail(Glide.with(context).load(defaultUrl))
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(new CustomTarget<Bitmap>(84, 84) {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {


                                Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(temp_location).icon(BitmapDescriptorFactory.fromBitmap(resource)));
                                marker.setTag(pos);


                                if (PopupData != null && PopupData.size() > 0) {
                                    openBottomSheetOnMarkerClick(PopupData, hash_popupdata, i);
/*
                                    SanCustomInfoWindowAdapter customInfoWindowAdapter = new SanCustomInfoWindowAdapter(context, PopupData, hash_popupdata, pos);
                                    mGoogleMap.setInfoWindowAdapter(customInfoWindowAdapter);
*/
//                                marker.showInfoWindow();
                                }
                                mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                    @Override
                                    public void onInfoWindowClick(Marker marker) {
//                                   ImproveHelper.showToast(context,(hash_popupdata.get(PopupData.get(0))).get(Integer.parseInt(marker.getTag().toString())));
                                        SelectedGPS = marker.getPosition().latitude + "," + marker.getPosition().longitude;
                                        if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                                            if (marker.getTag() != null) {
                                                AppConstants.MAP_MARKER_POSITION = (Integer) marker.getTag();
                                            }
                                            if (AppConstants.EventCallsFrom == 1) {
                                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                                if (SubformFlag) {
                                                    AppConstants.SF_Container_position = SubformPos;
                                                    AppConstants.Current_ClickorChangeTagName = SubformName;

                                                }
                                                AppConstants.GlobalObjects.setCurrent_GPS("");
                                                ((MainActivity) context).ClickEvent(view);
                                            }
                                        }
                                    }
                                });
                                builder.include(marker.getPosition());
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });
            } else {
                Glide.with(context).asBitmap().load(defaultUrl)
//               .thumbnail(Glide.with(context).load(defaultUrl))
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(new CustomTarget<Bitmap>(84, 84) {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(temp_location).icon(BitmapDescriptorFactory.fromBitmap(resource)));
                                marker.setTag(pos);

                                if (PopupData != null && PopupData.size() > 0) {
                                    openBottomSheetOnMarkerClick(PopupData, hash_popupdata, i);
/*
                                    SanCustomInfoWindowAdapter customInfoWindowAdapter = new SanCustomInfoWindowAdapter(context, PopupData, hash_popupdata, pos);
                                    mGoogleMap.setInfoWindowAdapter(customInfoWindowAdapter);
*/
//                                marker.showInfoWindow();
                                }
                                mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                    @Override
                                    public void onInfoWindowClick(Marker marker) {
//                                    ImproveHelper.showToast(context,(hash_popupdata.get(PopupData.get(0))).get(Integer.parseInt(marker.getTag().toString())));
                                        SelectedGPS = marker.getPosition().latitude + "," + marker.getPosition().longitude;

                                        if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                                            if (marker.getTag() != null) {
                                                AppConstants.MAP_MARKER_POSITION = (Integer) marker.getTag();
                                            }
                                            if (AppConstants.EventCallsFrom == 1) {
                                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                                if (SubformFlag) {
                                                    AppConstants.SF_Container_position = SubformPos;
                                                    AppConstants.Current_ClickorChangeTagName = SubformName;

                                                }
                                                AppConstants.GlobalObjects.setCurrent_GPS("");
                                                ((MainActivity) context).ClickEvent(view);
                                            }
                                        }
                                    }
                                });
                                builder.include(marker.getPosition());
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setMapImage", e);
        }
    }

    public String getSelectedMarkerGPSPosition() {
        return SelectedGPS;
    }

    public List<String> getTransIdsList() {
        return transIdsList;
    }

    public void setTransIdsList(List<String> transIdsList) {
        this.transIdsList = transIdsList;
    }

    public void setVisibility(boolean visible) {
        if (visible) {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        } else {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }
    }

    public String getMapPoints() {
        StringBuilder mapCoordinates = new StringBuilder();
        for (int j = 0; j < mapPoints.size(); j++) {
            mapCoordinates.append(mapPoints.get(j)).append("^");
        }
        if (mapCoordinates.toString().endsWith("^")) {
            mapCoordinates = new StringBuilder(mapCoordinates.substring(0, mapCoordinates.length() - 1));
        }
        return mapCoordinates.toString();

    }

    public String getRenderingType() {

        return controlObject.getMapViewType();
    }

    /* Set Properties*/
    public void setDisplayName(String propertyText) {
        tv_displayName.setText(propertyText);
        controlObject.setDisplayName(propertyText);
    }

    public void setHint(String propertyText) {
        if (propertyText != null && !propertyText.isEmpty()) {
            tv_hint.setVisibility(View.VISIBLE);
            tv_hint.setText(propertyText);
            controlObject.setHint(propertyText);
        } else {
            tv_hint.setVisibility(View.GONE);
        }
    }

    public void setHideDisplayName(Boolean valueOf) {
        if (valueOf) {
            tv_displayName.setVisibility(View.GONE);
            tv_hint.setVisibility(View.GONE);
        }
    }

    public void setVisibilitySP(boolean visibility) {
        if (visibility) {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }
    }

    public void setEnabled(boolean enabled) {
//        setViewDisable(rView, !enabled);
        controlObject.setDisable(!enabled);
        setViewDisableOrEnableDefault(context, view, enabled);
    }

    public void setTextSize(String size) {
        if (size != null) {
            controlObject.setTextSize(size);
            tv_displayName.setTextSize(Float.parseFloat(size));
        }
    }

    public void setTextColor(String color) {
        if (color != null && !color.equalsIgnoreCase("")) {
            controlObject.setTextHexColor(color);
            controlObject.setTextColor(Color.parseColor(color));
            tv_displayName.setTextColor(Color.parseColor(color));
        }
    }

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

    public void setMapViewSP(String propertyText) {
        if (propertyText != null && !propertyText.isEmpty()) {
            controlObject.setMapView(propertyText);
            if (controlObject.getMapView().equalsIgnoreCase("Satellite")) {
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            } else if (controlObject.getMapView().equalsIgnoreCase("Roads")) {
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }

        }
    }

    public void setMapHeight(String propertyText) {
        if (propertyText != null && !propertyText.isEmpty()) {
            ViewGroup.LayoutParams params = ll_mapView_main.getLayoutParams();
// Changes the height and width to the specified *pixels*
//                    params.height = Integer.parseInt(controlObject.getMapHeight());
            params.height = improveHelper.dpToPx(Integer.parseInt(propertyText)); // in dp
//                params.height = 1000;

            ll_mapView_main.setLayoutParams(params);
            controlObject.setMapHeight(propertyText);
        }
    }

    //TO DO setBase map functionality
    public void setBaseMap(String propertyText) {
        controlObject.setBaseMap(propertyText);
    }


    public void setZoomControls(Boolean valueOf) {
        controlObject.setZoomControl(valueOf);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setAllGesturesEnabled(true);

    }

    public void setShowCurrentLocation(Boolean valueOf) {
        controlObject.setShowCurrentLocation(valueOf);
        startCaptureGPS();
    }

    public void setMapIcon(String propertyText) {
        controlObject.setMapIcon(propertyText);
        mapViewTypesCoordinates();
    }

    public void openBottomSheetOnMarkerClick(List<String> PopupData, HashMap<String, List<String>> hash_popupdata, int pos) {
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                if (controlObject.isOnMapMarkerClickEventExists() && !AppConstants.Initialize_Flag) {
                    if (marker.getTag() != null) {
                        AppConstants.MAP_MARKER_POSITION = (Integer) marker.getTag();
                    }
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                        if (SubformFlag) {
                            AppConstants.SF_Container_position = SubformPos;
                            AppConstants.Current_ClickorChangeTagName = SubformName;
                        }
                        ((MainActivity) context).onMakerClickEvent(view);
                    }
                } else {

                    double dLat = marker.getPosition().latitude;
                    double dLng = marker.getPosition().longitude;
                    LinearLayout bottom_sheet_marker = activityView.findViewById(R.id.bottom_sheet_marker);
                    BottomSheetBehavior sheetBehaviorMarker = BottomSheetBehavior.from(bottom_sheet_marker);
                    CustomTextView ctvLocation = bottom_sheet_marker.findViewById(R.id.ctvLocation);
                    ImageView iv_markerCancel = bottom_sheet_marker.findViewById(R.id.iv_markerCancel);
                    ctvLocation.setText("LatLng : " + dLat + "," + dLng);

                    try {
                        final LinearLayout llIndexColumns = activityView.findViewById(R.id.llIndexColumns);
                        llIndexColumns.removeAllViews();

                        int select_pos = Integer.parseInt(marker.getTag().toString());

                        LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        for (int i = 0; i < PopupData.size(); i++) {

                            try {
                                List<String> popdata_list = hash_popupdata.get(PopupData.get(i));

                                View view = lInflater.inflate(R.layout.map_index_column, null);

                                CustomTextView columnName = view.findViewById(R.id.columnName);
                                CustomTextView columnValue = view.findViewById(R.id.columnValue);

                                columnName.setText(PopupData.get(i));

                                columnValue.setText(popdata_list.get(select_pos));

                                llIndexColumns.addView(view);

                            } catch (Exception e) {
                                ImproveHelper.improveException(context, TAG, "inflateViews - inside loop ", e);
                            }

                        }
                    } catch (Exception e) {
                        ImproveHelper.improveException(context, TAG, "inflateViews", e);
                    }
                    SelectedGPS = marker.getPosition().latitude + "," + marker.getPosition().longitude;
                    sheetBehaviorMarker.setState(BottomSheetBehavior.STATE_EXPANDED);
                    iv_markerCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ImproveHelper.showHideBottomSheet(sheetBehaviorMarker);
                        }
                    });
                }
                return false;
            }

        });

    }

    public LinearLayout getLl_main_inside() {
        return ll_main_inside;
    }

    public LinearLayout getLl_control_ui() {
        return ll_control_ui;
    }

    /*ControlUi Settings Start*/

    public CardView getCv_all() {
        return cv_all;
    }

    public MapView getMapView() {
        return mapFrag;
    }

    public CustomTextView getTv_displayName() {
        return tv_displayName;
    }

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {

            try {
                if (location != null) {

                    boolean isMock = (Build.VERSION.SDK_INT >= 18 && location.isFromMockProvider());
                    //  if (isMock) {
                    //      Helper.showToast(GPSActivity.this, "Fake GPS Point Will Not Accept.\nPlease Stop Fake GPS Application ");
                    //      setFailResult();
                    //      finish();
                    //  } else {
                    //                    if (location.getAccuracy() <= accuracy) {

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    accuracy = location.getAccuracy();
                    float accuracylevel = Float.parseFloat(GPS_acuuracy);

                    if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                        gpsData = location.getLatitude() + delimeter + location.getLongitude();
//                        gpsData = location.getLatitude() + delimeter + location.getLongitude();
                    } else {
                        tv_retry.setVisibility(View.VISIBLE);
                    }

//                    AppConstants.GlobalObjects.setCurrent_GPS(gpsData);

                    turnGPSOff();
                    locationManager.removeUpdates(mlistener);
                    countDownTimer.cancel();

                    setMapView(gpsData, "" + accuracy);


//                    }
                    // }


                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "onLocationChangedException: " + e);
            }

        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }

    public class SanCustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;
        private final Context context;
        List<String> PopupData;
        HashMap<String, List<String>> hash_popupdata;
        int pos;

        public SanCustomInfoWindowAdapter(Context context, List<String> PopupData, HashMap<String, List<String>> hash_popupdata, int pos) {
            LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myContentsView = lInflater.inflate(R.layout.map_marker_item, null);
            this.context = context;
            this.PopupData = PopupData;
            this.hash_popupdata = hash_popupdata;
            this.pos = pos;

        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            inflateViews(marker);
            return myContentsView;
        }

        private void inflateViews(Marker marker) {
            try {
                final LinearLayout llIndexColumns = myContentsView.findViewById(R.id.llIndexColumns);
                llIndexColumns.removeAllViews();

                int select_pos = Integer.parseInt(marker.getTag().toString());

                LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                for (int i = 0; i < PopupData.size(); i++) {

                    try {
                        List<String> popdata_list = hash_popupdata.get(PopupData.get(i));

                        View view = lInflater.inflate(R.layout.map_index_column, null);

                        CustomTextView columnName = view.findViewById(R.id.columnName);
                        CustomTextView columnValue = view.findViewById(R.id.columnValue);

                        columnName.setText(PopupData.get(i));

                        columnValue.setText(popdata_list.get(select_pos));

                        llIndexColumns.addView(view);

                    } catch (Exception e) {
                        ImproveHelper.improveException(context, TAG, "inflateViews - inside loop ", e);
                    }

                }
            } catch (Exception e) {
                ImproveHelper.improveException(context, TAG, "inflateViews", e);
            }
        }
    }


    /*ControlUi Settings end*/

    public ControlObject getControlObject() {
        return controlObject;
    }
    public void showMessageBelowControl(String msg) {
        if(msg != null && !msg.isEmpty()) {
            ct_showText.setVisibility(View.VISIBLE);
            ct_showText.setText(msg);
        }else{
            ct_showText.setVisibility(View.GONE);
        }
    }


    public void mapViewTypesCoordinates(){
        if (mapViewType != null && !mapViewType.isEmpty() && controlObject.getRenderingTypeList() != null && controlObject.getRenderingTypeList().size() > 0) {
            List<String> points = new ArrayList<>();
            String checkedNamesList = "";
            points = controlObject.getRenderingTypeList().get(0).getItem();
            if (mapViewType.equalsIgnoreCase(AppConstants.map_Multiple_Marker)) {
            } else if (mapViewType.equalsIgnoreCase(AppConstants.map_Multiple_Polylines) || mapViewType.equalsIgnoreCase(AppConstants.map_Polygon)) {
                for (int i = 0; i < points.size(); i++) {
                    checkedNamesList += points.get(i) + "^";
                }
                if (checkedNamesList != null && checkedNamesList.length() > 0 && checkedNamesList.charAt(checkedNamesList.length() - 1) == '^') {
                    checkedNamesList = checkedNamesList.substring(0, checkedNamesList.length() - 1);
                }
                points.add(checkedNamesList);
            }
            setMapPonitsDynamically(mapViewType, points, controlObject.getMapIcon());
        }

    }

}
