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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.RenderingType;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.UIVariables;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bhargo.user.utils.AppConstants.REQUEST_GPS_ENABLE;

public class LiveTracking_FullView extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener , UIVariables {


    private static final String TAG = "MapControl";
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(20);
    private final int MapControlTAG = 0;
    public String gpsData = "";
    public long maxTime = 20000;
    public GoogleMap mGoogleMap;
    Activity context;
    ControlObject controlObject;
    LinearLayout linearLayout, ll_mapView_main;
    MapView mapFrag;
    //SupportMapFragment gMap;
    String GPS_Mode = AppConstants.LOCATION_MODE_HYBRID, GPS_acuuracy = "10", interval_Type = "", interval = "500";
    String providerType = LocationManager.GPS_PROVIDER;
    String delimeter = "$";
    boolean stop_progressbar = false;
    ProgressBar progressBar;
    LocationManager locationManager = null;
    MyLocationListener mlistener;
    ImproveHelper improveHelper;
    ImageView iv_fullview, iv_live;
    ImageView iv_back;
    private CustomTextView tv_displayName, tv_retry;
    private boolean isFirst = true;
    //    private MapControl.LocCaptured locCaptured;
    private int status = 0;
    //    public MapControl(Activity context, ControlObject controlObject) {
//        this.context = context;
//        this.controlObject = controlObject;
//
//        initViews();
//
//    }
    private CountDownTimer countDownTimer;
    private ProgressDialog progressDialog;
    private List<PatternItem> PATTERN_POLYLINE_DOTTED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_livetracking_fullview);

        Bundle Data = getIntent().getBundleExtra("Data");
        controlObject = (ControlObject) Data.getSerializable("Object");
        this.context = LiveTracking_FullView.this;
        improveHelper = new ImproveHelper(this);
        initViews();

    }

    private void initViews() {
        try {
            tv_displayName = findViewById(R.id.tv_displayName);

            tv_retry = findViewById(R.id.tv_retry);
            ll_mapView_main = findViewById(R.id.ll_mapView_main);

            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            iv_back = findViewById(R.id.iv_back);
            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });


            mapFrag = findViewById(R.id.map);
            mapFrag.onEnterAmbient(null);

            if (controlObject.isShowCurrentLocation()) {
                startCaptureGPS();
            } else {
                ll_mapView_main.setVisibility(View.VISIBLE);
            }

//        mapFrag=AppConstants.mapFrag;

            if (mapFrag != null) {
                mapFrag.onCreate(null);
                mapFrag.getMapAsync(this);
            }


            iv_live = findViewById(R.id.iv_live);
            ImproveHelper.BlinkAnimation(iv_live);

        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initViews", e);
        }
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
    }


    public MapView getMapView() {
        return mapFrag;
    }

    private void startCaptureGPS() {
        try {
            if (isGPSON()) {
//            AppConstants.Current_ClickorChangeTagName = controlObject.getControlName();

                showProgress();

                gpsFinding();

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

    private void showProgress() {
        try {
            final Handler handler = new Handler();
            progressBar = findViewById(R.id.progress_horizontal);
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
            improveHelper.improveException(context, TAG, "showProgress", e);
        }
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

            locationManager.requestLocationUpdates(providerType, 500, 0, mlistener);
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

    private void turnGPSOn() {
        try {
            String provider = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (!provider.contains("gps")) { // if gps is disabled
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
            improveHelper.improveException(context, TAG, "gpsFailedCase", e);
        }
    }

    private void turnGPSOff() {
        try {
            String provider = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

            if (provider.contains("gps")) { // if gps is enabled
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

    public void setMapView(String gpsString, String accuracy) {
        try {
            stopprogress();

            String[] loc = gpsString.split("\\$");
            LatLng current_location = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));

            if (controlObject.isShowCurrentLocation()) {

                if (controlObject.getMapIcon() != null && !controlObject.getMapIcon().equalsIgnoreCase("Default")) {
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
                                            .title("Location")
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

            }

/*
        Glide.with(context).load(typeOfButtonModel.get(position).getIconPath())
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .dontAnimate().into(holder.icon);
*/

            List<String> pointsLocationList = new ArrayList<>();
            if (controlObject.getRenderingTypeList() != null && controlObject.getRenderingTypeList().size() > 0) {

                List<RenderingType> renderingTypesList = controlObject.getRenderingTypeList();

                for (int i = 0; i < renderingTypesList.size(); i++) {

                    String strRenderTypes = renderingTypesList.get(i).getType();
                    Log.d(TAG, "setMapViewRenderPoint: " + strRenderTypes);
//                if (renderingTypesList.get(i).getType().equalsIgnoreCase("Point")) {
                    Log.d(TAG, "setMapViewRenderItems: " + renderingTypesList.get(i).getItem());
                    pointsLocationList = renderingTypesList.get(i).getItem();
//                }
                }


                for (int i = 0; i < pointsLocationList.size(); i++) {

                    String[] strPoints = pointsLocationList.get(i).split("\\,");
                    Log.d(TAG, "setMapViewLocationSplit: " + strPoints[0] + "-" + strPoints[1]);

                    if (strPoints[0] != null && strPoints[1] != null) {

                        LatLng latLngLocations = new LatLng(Double.parseDouble(strPoints[0]), Double.parseDouble(strPoints[1]));

                        if (controlObject.getMapIcon() != null && !controlObject.getMapIcon().equalsIgnoreCase("Default")) {
                            Glide.with(context)
                                    .asBitmap()
                                    .load(controlObject.getMapIcon())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                            mGoogleMap.addMarker(new MarkerOptions()
                                                    .position(latLngLocations)
                                                    .icon(BitmapDescriptorFactory.fromBitmap(resource))
                                                    .title("Location")
                                                    .snippet(latLngLocations.latitude + "," + latLngLocations.longitude));

                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {
                                        }
                                    });
                        } else { // default marker
                            mGoogleMap.addMarker(new MarkerOptions()
                                    .position(latLngLocations)
                                    .title("Location")
                                    .snippet(latLngLocations.latitude + "," + latLngLocations.longitude));
                        }


                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngLocations, 15));
                    }

                }

            }
            if (accuracy != null) {
                String str_accuracy = "Accuracy: " + accuracy;
//            tv_acccuracy.setText(str_accuracy);
            }

            mGoogleMap.setOnPolylineClickListener(this);
            mGoogleMap.setOnPolygonClickListener(this);
//        Polyline line = mGoogleMap.addPolyline(new PolylineOptions()
//                .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
//                .width(5)
//                .color(Color.RED));

            ll_mapView_main.setVisibility(View.VISIBLE);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.resetMinMaxZoomPreference();
            PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);
            mapFrag.performClick();
            ll_mapView_main.performClick();

            mapFrag.onResume();


//        sucess();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setMapView", e);
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

    @Override
    public void onPause() {
        super.onPause();
        mapFrag.onPause();
    }

    class MyLocationListener implements LocationListener {

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
                    float accuracy = location.getAccuracy();
                    float accuracylevel = Float.parseFloat(GPS_acuuracy);

                    if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                        gpsData = location.getLatitude() + delimeter + location.getLongitude();
                    } else {
                        tv_retry.setVisibility(View.VISIBLE);
                    }

//                    AppConstants.GlobalObjects.setCurrent_GPS(gpsData);

                    turnGPSOff();
                    countDownTimer.cancel();

                    setMapView(gpsData, "" + accuracy);


//                    }
                    // }


                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "onLocationChangedException: " + e.toString());
            }

        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }

  /*  @Override
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
    }*/

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mapFrag.onStart();
//    }


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

}
