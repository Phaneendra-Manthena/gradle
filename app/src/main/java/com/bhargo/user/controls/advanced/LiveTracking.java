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
import android.view.ViewGroup;
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
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.FormDataResponse;
import com.bhargo.user.pojos.LiveTracking_Records;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.utils.AppConstants.REQUEST_GPS_ENABLE;
import static com.bhargo.user.utils.ImproveHelper.setViewDisable;

public class LiveTracking extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener, UIVariables {


    private static final String TAG = "MapControl";
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(20);
    private final int MapControlTAG = 0;
    private final GetServices getServices;
    public String gpsData = "";
    public long maxTime = 20000;
    public GoogleMap mGoogleMap;
    public List<LatLng> list_latlong = new ArrayList<LatLng>();
    public List<String> list_gpsStr = new ArrayList<String>();
    public String Submit_livegpsstr = "";
    public String LiveStatus = "Not Started";
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
    ImageView iv_fullview, iv_live;
    CustomButton cb_start, cb_end;
    boolean gpsflag = false, livegps = false;
    LiveTracking_Records liveTracking_Records = new LiveTracking_Records();
    Marker livemarker;
    ImproveHelper improveHelper;
    private CustomTextView tv_displayName,tv_hint, tv_retry,ct_showText;
    private boolean isFirst = true;
    //    private MapControl.LocCaptured locCaptured;
    private int status = 0;
    private View view;
    private CountDownTimer countDownTimer;
    private ProgressDialog progressDialog;
    private List<PatternItem> PATTERN_POLYLINE_DOTTED;

    public LiveTracking(Activity context, ControlObject controlObject) {
        this.context = context;
        this.controlObject = controlObject;
        getServices = RetrofitUtils.getUserService();
        improveHelper = new ImproveHelper(context);
        initViews();

    }

    private void initViews() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            addMapControlStrip(context);

       /* Intent svc = new Intent(context, MyLocationService.class);
        Bundle object = new Bundle();
        object.putSerializable("ControlObject", (Serializable) controlObject);
        svc.putExtra("Object",object);
        context.startService(svc);*/
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initViews", e);
        }
    }

    private void addMapControlStrip(Activity context) {
        try {
            final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = lInflater.inflate(R.layout.control_livetracking, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                    view = lInflater.inflate(R.layout.control_livetracking_six, null);
                }
            } else {
                view = lInflater.inflate(R.layout.control_livetracking, null);
            }
            view.setTag(MapControlTAG);

            tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);

            tv_retry = view.findViewById(R.id.tv_retry);
            ll_mapView_main = view.findViewById(R.id.ll_mapView_main);
            ct_showText = view.findViewById(R.id.ct_showText);


            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }

            if (controlObject.getBaseMap() != null) {
                mapFrag = view.findViewById(R.id.map);
                mapFrag.onEnterAmbient(null);


                if (controlObject.isShowCurrentLocation()) {
                    startCaptureGPS();
                } else {
                    ll_mapView_main.setVisibility(View.VISIBLE);
                }
                if (controlObject.getMapHeight() != null && !controlObject.getMapHeight().isEmpty()) {

                    ViewGroup.LayoutParams params = ll_mapView_main.getLayoutParams();
                    params.height = Integer.parseInt(controlObject.getMapHeight());
//                params.height = 1000;

                    ll_mapView_main.setLayoutParams(params);
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

            iv_fullview = view.findViewById(R.id.iv_fullview);
            iv_fullview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppConstants.mapFrag = mapFrag;
                    Intent FullViewIntent = new Intent(context, LiveTracking_FullView.class);
                    Bundle args = new Bundle();
                    args.putSerializable("Object", controlObject);
                    FullViewIntent.putExtra("Data", args);
                    context.startActivity(FullViewIntent);
                }
            });

            iv_live = view.findViewById(R.id.iv_live);
            ImproveHelper.BlinkAnimation(iv_live);

            cb_start = view.findViewById(R.id.cb_start);
            cb_end = view.findViewById(R.id.cb_end);
            cb_end.setEnabled(false);

            cb_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv_live.setVisibility(View.VISIBLE);
                    cb_start.setBackgroundColor(context.getColor(R.color.DarkGray));
                    cb_end.setBackgroundColor(context.getColor(R.color.buttonBlue));
                    cb_start.setEnabled(false);
                    cb_end.setEnabled(true);

                    ((MainActivity) context).LivetrackStart(controlObject.getControlName());
                }
            });
            cb_end.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv_live.setVisibility(View.GONE);
                    LiveStatus = "Ended";
                    cb_start.setBackgroundColor(context.getColor(R.color.buttonBlue));
                    cb_end.setBackgroundColor(context.getColor(R.color.DarkGray));
                    cb_start.setEnabled(true);
                    cb_end.setEnabled(false);
                    gpsflag = true;
                    livegps = false;
                    LiveStatus = "Ended";
                    Submit_livegpsstr = Submit_livegpsstr + "," + gpsData;
                    Submit_liveTracking();


                }
            });

            linearLayout.addView(view);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "addMapControlStrip", e);
        }

    }

    public void StartLiveTracking() {
        try {
            gpsflag = false;
            livegps = true;
            LiveStatus = "Started";
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "StartLiveTracking", e);
        }
    }

    public void startTracking() {
        try {
            cb_start.performClick();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "startTracking", e);
        }

    }

    public void stopTracking() {
        try {
            cb_end.performClick();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "stopTracking", e);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(context.getApplicationContext());
        mGoogleMap = googleMap;
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

    public LinearLayout getMapControlLayout() {
        return linearLayout;

    }

    public MapView getMapView() {
        return mapFrag;
    }

    public void setMapView(String gpsString) {
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
                    livemarker = mGoogleMap.addMarker(new MarkerOptions()
                            .position(current_location)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_vehicle))
                            .title("Current Location")
                            .snippet(current_location.latitude + "," + current_location.longitude));
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_location, 15));
                }

            }

            mGoogleMap.setOnPolylineClickListener(this);
            mGoogleMap.setOnPolygonClickListener(this);

            ll_mapView_main.setVisibility(View.VISIBLE);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.resetMinMaxZoomPreference();
            PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);


            mapFrag.performClick();
            ll_mapView_main.performClick();

            if (!livegps) {
                gpsflag = true;
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setMapView", e);
        }

    }

    private void startCaptureGPS() {
        try {
            if (isGPSON()) {
                showProgress(view);
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

            if (provider!=null&&provider.contains("gps")) { // if gps is enabled
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

    public void DrawPolyLine(LatLng point1, LatLng point2) {
        try {
            if (LiveStatus.equalsIgnoreCase("Started")) {
                LiveStatus = "Running";
            }

            if (point1.latitude != 0.0 && point1.longitude != 0.0) {


                mGoogleMap.addPolyline(new PolylineOptions()
                        .add(point1, point2)
                        .width(5)
                        .color(Color.BLACK));

                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point2, 15));
                livemarker.setPosition(point2);


            }

            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
            mGoogleMap.setMyLocationEnabled(true);
            //mGoogleMap.resetMinMaxZoomPreference();

            mapFrag.performContextClick();

        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "DrawPolyLine", e);
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

    public void Submit_liveTracking() {

        try {
            if (Submit_livegpsstr.startsWith(",")) {
                Submit_livegpsstr = Submit_livegpsstr.substring(1);
            }
//BLUE00000003_live tracking_tracking
            String TabelName = ((MainActivity) context).strCreatedBy + "_" + ImproveHelper.getTableNameWithOutSpace(((MainActivity) context).strAppName) + "_" + controlObject.getControlName();
            JSONObject jobj = new JSONObject();
            jobj.put("OrgId", ((MainActivity) context).strOrgId);
            jobj.put("Ref_TransID", AppConstants.LiveTrack_Trans_ID);
            jobj.put("LTTableName", TabelName);
            jobj.put("SubmittedUserID", ((MainActivity) context).strUserId);
            jobj.put("LT_Coordinates", Submit_livegpsstr);
            jobj.put("LT_Status", LiveStatus);


            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(jobj.toString());
            System.out.println("LiveTracking Submit ==" + jo.toString());

            Call<FormDataResponse> call = getServices.sendLiveTrackingData(jo);
            call.enqueue(new Callback<FormDataResponse>() {
                @Override
                public void onResponse(Call<FormDataResponse> call, Response<FormDataResponse> response) {

                    try {

                        if (response.body() != null) {


                            Log.d(TAG, "liveTracking Response: " + response.body().getMessage());
                            String resultStatus = response.body().getStatus();
                            response.body();
                            if (resultStatus.equalsIgnoreCase("200")) {
                                Submit_livegpsstr = "";
                                if (gpsflag && !livegps) {
                                    ((MainActivity) context).clearFormEdit();
                                }
//                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else if (resultStatus.equalsIgnoreCase("100")) {
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(context, "Data Not Submitted", Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<FormDataResponse> call, Throwable t) {
                    Log.d(TAG, "sendDataToServerResponse: " + t.toString());

                }
            });

        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "Submit_liveTracking", e);
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


                    turnGPSOff();
                    countDownTimer.cancel();

//                    String TabelName=((MainActivity) context).strCreatedBy+"_"+((MainActivity) context).strAppName+"_"+controlObject.getControlName();
//                    liveTracking_Records.setLiveStatus(LiveStatus);
//                    liveTracking_Records.setList_latlong(list_latlong);
//                    PrefManger.putSharedPreferencesObject(context,TabelName,liveTracking_Records);

                    if (!gpsflag && !livegps) {
                        setMapView(gpsData);
                    } else if (livegps) {

                        LatLng current_location = new LatLng(location.getLatitude(), location.getLongitude());
                        if (!list_gpsStr.contains(gpsData)) {
                            Submit_livegpsstr = Submit_livegpsstr + "," + gpsData;
                            list_gpsStr.add(gpsData);
                            list_latlong.add(current_location);
                            if (list_latlong.size() > 1) {
                                DrawPolyLine(list_latlong.get(list_latlong.size() - 2), list_latlong.get(list_latlong.size() - 1));
                            }

                            Submit_liveTracking();

                        }
                    }
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

    /* SetProperties*/
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
        if(disable){
            setViewDisable(view, !disable);
        }
    }

    public void setVisibility(boolean visibility) {
        if (visibility) {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        } else {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }
    }
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

}
