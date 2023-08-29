package com.bhargo.user.utils;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment implements LocationListener {

    Context context;
    private View rootView;
    private ViewGroup viewGroup;
    public static ImageView ib_done;
    static Calendar c;
    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    static SimpleDateFormat df_for_cal = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    public View view;
    public TextView title;
    public TextView subTitle;
    public CircleImageView iv_circle_appIcon;
    public ImageView ib_settings,iv_info,ib_timer, ib_logout, iv_sync,iv_profile,iv_assessment_reports;


    public int year, month, day;
    public Bitmap imageBitmap;
    public boolean isCameraOpen = false;
    public int backReq;
    public Uri uriSavedImage;
    public ProgressDialog progressDialog;
    public int READ_STATE_CODE = 5;
    public double latitude, longitude;
    public String mobile_pattern = "([0,1,2,3,4,5]{1}([0-9]{9}|([9|8|7|6]{9})))";
    public Toolbar toolbar;
    protected LocationManager locationManager;
    String device_Date, device_Time, device_Date_Time;
    SimpleDateFormat tf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
    SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault());
    SimpleDateFormat dtfImage = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
    private int REQUEST_CAMERA = 1;
    private int REQUEST_CODE = 2;
    private int NETWORK_CODE = 3;
    private int LOCATION_CODE = 4;
    public RelativeLayout ll_countDownTimer;
    public CustomTextView tv_countDown,tv_AssessmentSubmit;
    public BaseFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_view_map, container, false);
        viewGroup = container;
context = getActivity();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        return rootView;

    }

    public static void hideKeyboard(Context mContext, View view) {
        try {
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        } catch (Exception e) {
        }
    }


    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public static void changeImageViewSizes(ImageView imageView, int width, int height) {
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        if (params != null) {
            params.width = width;
            params.height = height;
        } else {
            params = new ViewGroup.LayoutParams(width, height);
        }
        imageView.setLayoutParams(params);

    }
    public void enableBackNavigation(boolean isEnable) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(isEnable);

    }

    public void initializeActionBar() {

        toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        title = toolbar.findViewById(R.id.toolbar_title);
        subTitle = toolbar.findViewById(R.id.toolbar_sub_title);
        ll_countDownTimer = toolbar.findViewById(R.id.ll_countDownTimer);
        ib_timer = toolbar.findViewById(R.id.ib_timer);
        tv_countDown = toolbar.findViewById(R.id.tv_countDown);
        iv_info = toolbar.findViewById(R.id.iv_info);
        tv_AssessmentSubmit = toolbar.findViewById(R.id.tv_AssessmentSubmit);
        ib_settings = toolbar.findViewById(R.id.ib_settings);
        iv_circle_appIcon = toolbar.findViewById(R.id.iv_circle_appIcon);
        ib_logout = toolbar.findViewById(R.id.ib_logout);
        iv_sync = toolbar.findViewById(R.id.iv_sync);
        iv_profile = toolbar.findViewById(R.id.iv_profile);
        iv_assessment_reports = toolbar.findViewById(R.id.iv_assessment_reports);
        ib_done = toolbar.findViewById(R.id.ib_done);


        final Drawable upArrow = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(ContextCompat.getColor(context, R.color.red), PorterDuff.Mode.SRC_ATOP);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((AppCompatActivity)getActivity()).getWindow().setNavigationBarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                View view = getActivity().getCurrentFocus();
                hideKeyboard(context, view);
                AlertDialog alertDialog = new AlertDialog.Builder(context)
//set icon

//set titl
                        .setTitle(R.string.are_you_sure)
//set messag
//set positive button
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                                getActivity().finish();
                            }
                        })
//set negative button
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked

                            }
                        })
                        .show();
                //finish();
                break;
        }
        return true;
    }

    public void disableActionBar() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    public String setCurrentDate() {
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + month + 1 + "-" + day;
    }

    public void disableEditText(EditText editText) {
        editText.setFocusableInTouchMode(false);
        editText.setFocusable(false);
    }

    public void startCamera(int req, String pic_name) {
        if (hasCamera()) {
            isCameraOpen = true;
            backReq = req;
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            File imagesFolder = new File(Environment.getExternalStorageDirectory(), "CCRC");
            imagesFolder.mkdirs();
            File image = new File(imagesFolder, "odf_" + pic_name + timeStamp + ".png");
            uriSavedImage = Uri.fromFile(image);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
            startActivityForResult(cameraIntent, req);
        } else {
            Toast.makeText(context, "Sorry, your phone does not have a camera!", Toast.LENGTH_LONG).show();
        }
    }

    public void initPermissions() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            REQUEST_CAMERA);
                } else {
                    if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                    } else {
                        if (context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CODE);
                        } else {
                            if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, READ_STATE_CODE);
                            } else {

                                if (context.checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, NETWORK_CODE);
                                } else {

                                }
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
        }
    }

    private boolean hasCamera() {
        //check if the device has camera
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            final int REQUIRED_SIZE = 200;
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    //    private Bitmap timestampItAndSave(Bitmap toEdit){
//        Bitmap dest = Bitmap.createBitmap(toEdit.getWidth(), toEdit.getHeight(), Bitmap.Config.ARGB_8888);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        String dateTime = sdf.format(Calendar.getInstance().getTime()); // reading local time in the system
//
//        Canvas cs = new Canvas(dest);
//        Paint tPaint = new Paint();
//        tPaint.setTextSize(35);
//        tPaint.setColor(Color.BLUE);
//        tPaint.setStyle(Paint.Style.FILL);
//        float height = tPaint.measureText("yY");
//        cs.drawText(dateTime, 20f, height+15f, tPaint);
//        try {
//            dest.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/timestamped")));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return dest;
//    }
    public void exitApplication(Context context) {
        //Intent intent = new Intent(context, ExitActivity.class);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);

        if (Build.VERSION.SDK_INT >= 21) {
            getActivity().finishAndRemoveTask();
        } else {
            getActivity().finish();
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
            e.printStackTrace();
        }
    }


//    public void showCustomDialog() {
//        final Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout_sample_app.custom_popup_layout);
//        dialog.show();
//        TextView ok = (TextView) dialog.findViewById(R.id.cancel_btn);
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//    }

    public void dismissProgressDialog() {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initPermissions();
            } else {
                initPermissions();
            }
        } else if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                initPermissions();
            } else {
                initPermissions();
            }
        } else if (requestCode == NETWORK_CODE) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                initPermissions();
            } else {
                initPermissions();
            }
        } else if (requestCode == LOCATION_CODE) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                initPermissions();
            } else {
                initPermissions();
            }
        } else if (requestCode == READ_STATE_CODE) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initPermissions();
            } else {
                initPermissions();
            }
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public String getDateandTimeFromDevice() {


        c = Calendar.getInstance();

        device_Date_Time = dtf.format(c.getTime());


        return device_Date_Time;
    }

    public String getDateandTimeForImage() {


        c = Calendar.getInstance();

        device_Date_Time = dtfImage.format(c.getTime());


        return device_Date_Time;
    }

    public String getDateFromDevice() {


        Calendar c = Calendar.getInstance();

        device_Date = df.format(c.getTime());

        return device_Date;
    }

    public String getDateFromDeviceForCal() {


        Calendar c = Calendar.getInstance();

        device_Date = df_for_cal.format(c.getTime());

        return device_Date;
    }

    public String getTimeFromDevice() {

        Calendar c = Calendar.getInstance();

        device_Time = tf.format(c.getTime());

        return device_Time;
    }

    public Long getTimeFromDeviceInMilliSc() {

     /*   Calendar c = Calendar.getInstance();

         int seconds = c.get(Calendar.SECOND);
        int minutes = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR);
//        String time = hour + ":" + minutes + ":" + seconds;
//        Log.d("device_time_", time);
        int duration = 3600 * hour + 60 * minutes + seconds;
        return duration + "";*/

        Calendar calendar = Calendar.getInstance();
        //Returns current time in millis
        return  calendar.getTimeInMillis();
    }

    public void toast(String msg, Context ctx) {

        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public String getAndroidVersion() {

        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        return "Android SDK: " + sdkVersion + " (" + release + ")";

    }

    public void alertDialogError(String displayMsg) {

        String msg = displayMsg;

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);

        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        //Creating dialog box
        androidx.appcompat.app.AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("");
        alert.show();
    }

    public void alertDialogInternet(String displayMsg) {

        String msg = displayMsg;

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);

        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        //Creating dialog box
        androidx.appcompat.app.AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("");
        alert.show();
    }

    public String getAppVersion(Context context) {
        String result = "";

        try {
            result = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .versionName;
            result = result.replaceAll("[a-zA-Z]|-", "");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("APP_VERSION", e.getMessage());
        }

        return result;
    }

    public String compareTwoValues(String s, String s1) {

        MathContext mc = new MathContext(4);
        BigDecimal bd1 = new BigDecimal(s, mc);
        BigDecimal bd2 = new BigDecimal(s1, mc);
        int res = bd1.compareTo(bd2);

        String str1 = "Both values are equal";
        String str2 = "First Value is greater";
        String str3 = "Second value is greater";

        if (res == 0)
            return str1;
        else if (res > 0)
            return str2;
        else return str3;

    }

    public void hideSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public String getPreviousDayDate() {

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, -1);
        return df_for_cal.format(c.getTime());
    }
}
