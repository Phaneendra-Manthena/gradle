package com.bhargo.user.utils;

import static com.bhargo.user.utils.ImproveHelper.showToast;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.bhargo.user.R;

import java.util.Timer;
import java.util.TimerTask;


public class GPSActivity extends BaseActivity {

    public static final String LOC_DATA = "gps_data";
    public static final String LOC_ALTITUDE = "gps_altitude";
    public static final String LOC_ACCURACY = "gps_accuracy";
    public static final String LOC_TIME_IN_MILLIS = "gps_time";
    public static final String MAX_ACCURACY = "max_accuracy";
    public static final String MAX_TIME_IN_MILLIS = "max_time";
    public static final String PROVIDER = "PROVIDER";

    public static final String GPS_PROVIDER = LocationManager.GPS_PROVIDER;
    public static final String NETWORK_PROVIDER = LocationManager.NETWORK_PROVIDER;
    private final String delimeter = "$";
    public int accuracy = 50;
    public long maxTime = 20000;
    public long time = 0;
    private String providerType = LocationManager.GPS_PROVIDER;
    private String gpsData = "";
    private LocationManager locationManager = null;
    private MyLocationListener mlistener;

    private TextView tvCountDown;
    private String acuuracy = "";
    private String altitude = "";
    private CountDownTimer countDownTimer;

    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gps_helper);
        this.setFinishOnTouchOutside(false);
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Intent gpsInten = getIntent();

        if (gpsInten != null) {

            if (gpsInten.hasExtra("GET_GPS_LOCATION")) {
                maxTime = 30000;
                String acc=gpsInten.getStringExtra(MAX_ACCURACY);
                if(acc==null || acc.trim().equals("")|| acc.trim().equals("0")){

                }else{
                    accuracy = Integer.parseInt(gpsInten.getStringExtra(MAX_ACCURACY));
                }

                if (gpsInten.getStringExtra(PROVIDER).equals("Network")) {
                    providerType = NETWORK_PROVIDER;
                } else if (gpsInten.getStringExtra(PROVIDER).equals("Satellite")) {
                    providerType = GPS_PROVIDER;
                } else {
                    providerType = GPS_PROVIDER; //Hybrid
                }

            } else {
                accuracy = gpsInten.getIntExtra(MAX_ACCURACY, accuracy);
                maxTime = gpsInten.getLongExtra(MAX_TIME_IN_MILLIS, maxTime);
           /* if (gpsInten.hasExtra(PROVIDER))
                providerType = gpsInten.getStringExtra(PROVIDER);*/
                providerType = NETWORK_PROVIDER;
            }


        }

        initView();

        startSearchForGps();
    }

    private void initView() {

        tvCountDown = findViewById(R.id.tvCountDown);
        ImageView iv_gps= findViewById(R.id.iv_gps);
        ImageView iv_gps_one= findViewById(R.id.iv_gps_one);
        ImageView iv_gps2= findViewById(R.id.iv_gps2);

// Animation
        Animation animUpDown;

        // load the animation
        animUpDown = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.up_down);



        // start the animation
        ImageView iv_rotate= findViewById(R.id.iv_rotate);
        ImageView iv_rotate1= findViewById(R.id.iv_rotate1);
        ImageView iv_rotate2= findViewById(R.id.iv_rotate2);

        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(5000);
        rotate.setRepeatCount(Animation.INFINITE);
        iv_rotate.startAnimation(rotate);
        iv_rotate1.startAnimation(rotate);
        iv_rotate2.startAnimation(rotate);
       /* RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f,          Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setInterpolator(new LinearInterpolator());
        iv_rotate.startAnimation(rotate);*/


        TimerTask  timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        iv_gps.startAnimation(animUpDown);
                        iv_gps_one.startAnimation(animUpDown);
                        iv_gps2.startAnimation(animUpDown);
                    }
                });
            }

        };
        Timer   timerup = new Timer();
        timerup.scheduleAtFixedRate(timerTask, 0 ,1000);

    }

    private void initialiseTimer(long totalTime, long interval) {
        countDownTimer = new CountDownTimer(totalTime, interval) {

            public void onTick(long millisUntilFinished) {
                tvCountDown.setText("Using " + providerType.toUpperCase() + " Countdown : " + (millisUntilFinished / 1000));
            }

            public void onFinish() {
                if (TextUtils.isEmpty(gpsData)) {
                    if (isFirst) {
                        isFirst = false;
                        if (mlistener != null)
                            locationManager.removeUpdates(mlistener);

                        if (getIntent().getStringExtra(PROVIDER) != null && getIntent().getStringExtra(PROVIDER).equals("Satellite")) {
                            Toast.makeText(GPSActivity.this, "unable_to_get_location", Toast.LENGTH_SHORT).show();
                           // setFailResult();// getLastKnownLocation
                            setSuccessResultFromLastKnownLocation();
                        } else {
                            if (isSimSupport()) {
                                providerType = LocationManager.NETWORK_PROVIDER;
                                gpsFinding();
                            } else {
//                            Helper.showToast(GPSActivity.this, getString(R.string.sim_card_not_available));
                                Toast.makeText(GPSActivity.this, "sim_card_not_available", Toast.LENGTH_SHORT).show();
                                setFailResult();
                            }
                        }

                    } else {
//                        Helper.showToast(GPSActivity.this, getString(R.string.unable_to_get_location));
                        Toast.makeText(GPSActivity.this, "unable_to_get_location", Toast.LENGTH_SHORT).show();
                        // setFailResult(); // getLastKnownLocation
                        setSuccessResultFromLastKnownLocation();
                    }

                }
            }

        }.start();
    }

    private void setSuccessResult() {
        Intent intent = new Intent();
        intent.putExtra(LOC_DATA, gpsData);
        intent.putExtra(LOC_ACCURACY, acuuracy);
        intent.putExtra(LOC_ALTITUDE, altitude);
        intent.putExtra(LOC_TIME_IN_MILLIS, time);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setFailResult() {
        setResult(RESULT_CANCELED, null);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void startSearchForGps() {
        if (isGPSON()) {
            gpsFinding();
        } else {
            confirmGPS();
        }
    }

    @Override
    public void onBackPressed() {
    }

    public void gpsFinding() {
        initialiseTimer(maxTime, 1000);
        turnGPSOn();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mlistener = new MyLocationListener();
        //getCurrentPosition();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(providerType, 50, 0, mlistener);
    }

    public boolean isSimSupport() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);  //gets the current TelephonyManager
        return !(tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT);

    }


    private void turnGPSOn() {

        String provider = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (provider!=null && !provider.contains("gps")) { // if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings",
                    "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    private void turnGPSOff() {
        String provider = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (provider.contains("gps")) { // if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings",
                    "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }

    }

    public void confirmGPS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.enable_location));
        builder.setPositiveButton(getString(R.string.enable), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Helper.showToast(GPSActivity.this, getString(R.string.toast_enable_gps));
                Toast.makeText(GPSActivity.this, "Enable GPS", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, AppConstants.REQUEST_GPS_ENABLE);
            }
        });
        builder.setCancelable(false);
        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1234) {
            if (resultCode == RESULT_CANCELED) {
                if (isGPSON()) {
                    gpsFinding();
                } else {
                    confirmGPS();
                }

            }
        }
    }

    public boolean isGPSON() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void getCurrentPosition() {
        //getting last known position and broadcasting it for MainActivity
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (locationManager.getLastKnownLocation(providerType) != null) {
                double latitude = locationManager.getLastKnownLocation(providerType).getLatitude();
                double longitude = locationManager.getLastKnownLocation(providerType).getLongitude();
                double altitude = locationManager.getLastKnownLocation(providerType).getAltitude();
                System.out.println("latitude ---" + latitude + "longitude :" + longitude + "altitude:" + altitude + "providerType:" + providerType);

            }
        }
    }

    private void setSuccessResultFromLastKnownLocation() {

        try {
            //getting last known position and broadcasting it for MainActivity
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (locationManager.getLastKnownLocation(providerType) != null) {
                    double latitude = locationManager.getLastKnownLocation(providerType).getLatitude();
                    double longitude = locationManager.getLastKnownLocation(providerType).getLongitude();
                    altitude = locationManager.getLastKnownLocation(providerType).getAltitude() + "";
                    acuuracy = locationManager.getLastKnownLocation(providerType).getAccuracy() + "";
                    time = locationManager.getLastKnownLocation(providerType).getTime();
                    gpsData = latitude + delimeter + longitude;
                    locationManager.removeUpdates(mlistener);
                    turnGPSOff();
                    countDownTimer.cancel();
                    System.out.println("FromLastKnownLocation latitude ---" + latitude + "longitude :" + longitude + "\naltitude:" + altitude + "providerType:" + providerType);
                    setSuccessResult();
                } else {
                    setFailResult();
                }
            } else {
                setFailResult();
            }
        }catch (Exception e){
            e.printStackTrace();
            showToast(GPSActivity.this, e.getMessage());
            setFailResult();
        }



    }

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            try {
                if (location != null) {
                    boolean isMock = (Build.VERSION.SDK_INT >= 18 && location.isFromMockProvider());
                    if (isMock) {
                        showToast(GPSActivity.this, "Fake GPS Point Will Not Accept.\nPlease Stop Fake GPS Application ");
                        setFailResult();
                    } else {
                        if (location.getAccuracy() <= accuracy) {
                            if (ActivityCompat.checkSelfPermission(GPSActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GPSActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            locationManager.removeUpdates(mlistener);
                            acuuracy = "" + location.getAccuracy();
                            altitude = "" + location.getAltitude();
                            gpsData = location.getLatitude() + delimeter + location.getLongitude();
                            time = location.getTime();

                            turnGPSOff();
                            countDownTimer.cancel();
                            setSuccessResult();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                showToast(GPSActivity.this, e.getMessage());
                setFailResult();
            }

        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }

}
