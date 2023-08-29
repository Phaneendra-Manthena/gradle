package com.bhargo.user.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bhargo.user.R;
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

/**
 * Created by Rajendra on 13/05/2019.
 */

public class LocationHelper extends AppCompatActivity {

    public static final String LOC_DATA = "gps_data";
    public static final String LOC_TIME_IN_MILLIS = "gps_time";
    public static final String RETURN_ACTION = "Action_Type";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1234;
    private static final int REQUEST_CHECK_SETTINGS = 9876;
    private static final String TAG = "LocationHelper";
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location finalLocation;
    private boolean mRequestingLocationUpdates;
    private LocationCallback mLocationCallback;
    private ProgressBar progressBar;

    public static void showSimpleToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_fused);
        setTitle("");

        progressBar = findViewById(R.id.progressBar);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationRequest = new LocationRequest();

        if (requiredPermission()) {
            requestPermissions();
        } else {

            checkGpsSettings();
        }

        mLocationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    if (location != null) {

                        finalLocation = location;
                        break;
                    }
                }

//                progressBar.setVisibility(View.GONE);
                mRequestingLocationUpdates = false;
                startLocationUpdates();

                returnIntent(finalLocation);
            }
        };

    }

    private void returnIntent(Location location) {
        try {
            Intent intent = new Intent();
            String delimiter = "$";
            intent.putExtra(LOC_DATA, location.getLatitude() + delimiter + location.getLongitude());
            intent.putExtra(LOC_TIME_IN_MILLIS, location.getTime());
            if(getIntent().hasExtra(RETURN_ACTION)){
                intent.putExtra(RETURN_ACTION, getIntent().getStringExtra(RETURN_ACTION));
            }
            setResult(RESULT_OK, intent);
            finish();
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "returnIntent", e);
        }
    }

    private void returnFailedIntent() {
        try {
            setResult(RESULT_CANCELED, null);
            finish();
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "returnFailedIntent", e);
        }
    }

    protected void createLocationRequest() {
        try {
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            int LOCATION_ACCURACY = LocationRequest.PRIORITY_HIGH_ACCURACY;
            mLocationRequest.setPriority(LOCATION_ACCURACY);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "createLocationRequest", e);
        }
    }

    private void checkGpsSettings() {
        try {
            createLocationRequest();

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest);
            SettingsClient client = LocationServices.getSettingsClient(this);
            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

            task.addOnSuccessListener(this, locationSettingsResponse -> {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                mRequestingLocationUpdates = true;
                startLocationUpdates();

            });

            task.addOnFailureListener(this, e -> {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(LocationHelper.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "checkGpsSettings", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CHECK_SETTINGS) {
            mRequestingLocationUpdates = true;
            startLocationUpdates();
        } else {
            returnFailedIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                showSimpleToast(this, "User interaction cancelled");
                returnFailedIntent();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkGpsSettings();
            } else {
                //showSimpleToast(this, "Permission denied");
                showApplicationDialog();
            }


        }
    }

    public void showApplicationDialog() {
        try {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(this);
            builder.setTitle("Attention");
            builder.setMessage("Permission is necessary");
            builder.setPositiveButton("Ok", (dialogInterface, i) -> returnFailedIntent());
            builder.setNegativeButton("Cancel", null);
            builder.show();
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "showApplicationDialog", e);
        }
    }

    private boolean requiredPermission() {
        boolean isRequiredPermission = false;
        try {
            int permissionState = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            isRequiredPermission = permissionState != PackageManager.PERMISSION_GRANTED;
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "requiredPermission", e);
        }
        return isRequiredPermission;
    }

    private void requestPermissions() {
        try {
            boolean shouldProvideRationale =
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION);

            if (shouldProvideRationale) {
                showSimpleToast(this, "Location permission is needed for core functionality");
            }
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "requestPermissions", e);
        }
    }

    private void startLocationUpdates() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            ImproveHelper.improveException(this, TAG, "startLocationUpdates", e);
        }
    }

    private void stopLocationUpdates() {
        try {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "stopLocationUpdates", e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }
}
