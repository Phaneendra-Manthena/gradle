package com.bhargo.user.utils;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.bhargo.user.Java_Beans.Control_EventObject;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

public class LocHelper {

    public static final String LOC_DATA = "gps_data";
    public static final String LOC_TIME_IN_MILLIS = "gps_time";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1234;
    public static final int REQUEST_CHECK_SETTINGS = 9876;
    private static final String TAG = "LocationHelper";
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location finalLocation;
    public boolean mRequestingLocationUpdates;
    private LocationCallback mLocationCallback;
    private final Context context;
    private final LocationListener locationListener;
    private Control_EventObject control_EventObject;

    public interface LocationListener{
        void onLocationUpdate(String data);
    }

    public LocHelper(Context context){
        this.context=context;
        locationListener = (LocationListener) context;
       /* init();*/

    }

    public void init(){

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mLocationRequest = LocationRequest.create();
        if (requiredPermission()) {
            requestPermissions();
        } else {
            checkGpsSettings();
        }

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {

                for (Location location : locationResult.getLocations()) {
                    if (location != null) {

                        finalLocation = location;
                        break;
                    }
                }
                mRequestingLocationUpdates = false;
                startLocationUpdates();
                locationListener.onLocationUpdate(finalLocation.getLatitude() + "$" + finalLocation.getLongitude());
            }
        };

    }

    public void returnIntent(Location location) {
        try {
            Intent intent = new Intent();
            String delimiter = "$";
            intent.putExtra(LOC_DATA, location.getLatitude() + delimiter + location.getLongitude());
            intent.putExtra(LOC_TIME_IN_MILLIS, location.getTime());
            ((Activity)context).setResult(RESULT_OK, intent);
            ((Activity)context).finish();
        } catch (Exception e) {
            ImproveHelper.improveException(context
                    , TAG, "returnIntent", e);
        }
    }

    public boolean requiredPermission() {
        boolean isRequiredPermission = false;
        try {
            int permissionState = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            isRequiredPermission = permissionState != PackageManager.PERMISSION_GRANTED;
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "requiredPermission", e);
        }
        return isRequiredPermission;
    }

    public void requestPermissions() {
        try {
            boolean shouldProvideRationale =
                    ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                            Manifest.permission.ACCESS_FINE_LOCATION);

            if (shouldProvideRationale) {
                //showSimpleToast(this, "Location permission is needed for core functionality");
            }
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "requestPermissions", e);
        }
    }

    public void checkGpsSettings() {
        try {
            createLocationRequest();

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest);
            SettingsClient client = LocationServices.getSettingsClient(context);
            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

            task.addOnSuccessListener((Activity) context, locationSettingsResponse -> {
                mRequestingLocationUpdates = true;
                startLocationUpdates();
            });

            task.addOnFailureListener((Activity) context, e -> {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult((Activity) context,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            });

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "checkGpsSettings", e);
        }
    }

    public void createLocationRequest() {
        try {
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            int LOCATION_ACCURACY = LocationRequest.PRIORITY_HIGH_ACCURACY;
            mLocationRequest.setPriority(LOCATION_ACCURACY);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "createLocationRequest", e);
        }
    }

    public void startLocationUpdates() {
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback,
                    null /* Looper */);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "startLocationUpdates", e);
        }
    }

    public void stopLocationUpdates() {
        try {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "stopLocationUpdates", e);
        }
    }

    public Control_EventObject getControl_EventObject() {
        return control_EventObject;
    }

    public void setControl_EventObject(Control_EventObject control_EventObject) {
        this.control_EventObject = control_EventObject;
    }
}
