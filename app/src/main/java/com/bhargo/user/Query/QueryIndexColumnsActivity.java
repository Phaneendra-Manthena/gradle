package com.bhargo.user.Query;

import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.Create_Query_Object;
import com.bhargo.user.Java_Beans.QueryFilterField_Bean;
import com.bhargo.user.Java_Beans.QuerySelectField_Bean;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QueryIndexColumnsActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String TAG = "QueryIndexColumnsActivi";
    public TextView title;
    public ImageView ib_settings;
    private String response;
    private JSONObject resObj;
    private List<String> List_Index_Columns;
    private List<QueryFilterField_Bean> List_Query_Columns;
    private List<QuerySelectField_Bean> List_Final_Columns;
    private Create_Query_Object create_query_object;
    private RecyclerView rvIndexColumns;
    private Context context;
    private IndexColumnsAdapter adapter;
    private String appName, strChildName;
    private Toolbar toolbar;
    private CustomButton cbList;
    private CustomButton cbMap;
    private FrameLayout mapLayout;
    private SupportMapFragment mapFragment;
    private LinearLayout llDisplayView;
    private String gpsColumnName;
    private List<String> gpsValues = new ArrayList<>();
    private JSONArray outputArray;
    private GoogleMap gMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Theme
        setBhargoTheme(this,AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_index_columns);

        appName = getIntent().getStringExtra("appName");
        strChildName = getIntent().getStringExtra("s_childForm");

        initializeActionBar();

        context = QueryIndexColumnsActivity.this;

        rvIndexColumns = findViewById(R.id.rvIndexColumns);

        mapLayout = findViewById(R.id.mapLayout);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);

        response = getIntent().getStringExtra("response");
        create_query_object = (Create_Query_Object) getIntent().getSerializableExtra("create_query_object");

        if (create_query_object.getQuery_Title() != null) {
            title.setText(create_query_object.getQuery_Title());
        } else {
            title.setText(appName);
        }

        if (!create_query_object.getQuery_Index_Heading().equals("")) {
            title.setText(create_query_object.getQuery_Index_Heading());
        }

        List_Index_Columns = new ArrayList<>();
        List_Index_Columns = create_query_object.getList_Index_Columns();

        List_Query_Columns = new ArrayList<>();
        List_Query_Columns = create_query_object.getList_FormAPIQuery_FilterFields();
        List_Final_Columns = create_query_object.getList_Form_DisplayFields();


        /*for (int i = 0; i < List_Query_Columns.size(); i++) {

            ControlObject controlObject = new ControlObject();

            if (List_Query_Columns.get(i).getExist_Field_ControlObject() != null) {
                controlObject = List_Query_Columns.get(i).getExist_Field_ControlObject();
            } else {
                controlObject = List_Query_Columns.get(i).getField_ControlObject();
            }

            if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {

                llDisplayView.setVisibility(View.VISIBLE);
                cbList.setVisibility(View.GONE);
                gpsColumnName = controlObject.getControlName();
            }

        } */

        for (int i = 0; i < List_Final_Columns.size(); i++) {

            ControlObject controlObject = new ControlObject();

            controlObject = List_Final_Columns.get(i).getField_ControlObject();

            if (controlObject.getControlType().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GPS)) {
                llDisplayView.setVisibility(View.VISIBLE);
                cbList.setVisibility(View.GONE);
                gpsColumnName = controlObject.getControlName();
            }
        }


        try {
            resObj = new JSONObject(response);

            outputArray = resObj.getJSONArray("output");

            adapter = new IndexColumnsAdapter(outputArray);

            rvIndexColumns.setLayoutManager(new LinearLayoutManager(context));

            rvIndexColumns.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        cbMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cbList.setVisibility(View.VISIBLE);
                cbMap.setVisibility(View.GONE);
                initilizeMap();
            }
        });

        cbList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cbList.setVisibility(View.GONE);
                cbMap.setVisibility(View.VISIBLE);
                initilizeList();
            }
        });

    }

    private void initilizeList() {

        rvIndexColumns.setVisibility(View.VISIBLE);
        mapLayout.setVisibility(View.GONE);

    }

    private void initilizeMap() {
        rvIndexColumns.setVisibility(View.GONE);
        mapLayout.setVisibility(View.VISIBLE);
        mapFragment.getMapAsync(this);
    }

    public void initializeActionBar() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title = toolbar.findViewById(R.id.toolbar_title);
        ib_settings = toolbar.findViewById(R.id.ib_settings);
        ib_done = toolbar.findViewById(R.id.ib_done);
        llDisplayView = toolbar.findViewById(R.id.llDisplayView);
        cbList = toolbar.findViewById(R.id.cbList);
        cbMap = toolbar.findViewById(R.id.cbMap);

        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.red), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        ib_settings.setVisibility(View.GONE);

        title.setText(getIntent().getStringExtra("appName"));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;

        gMap.getUiSettings().setZoomControlsEnabled(true);

        gMap.getUiSettings().setAllGesturesEnabled(true);

        gMap.getUiSettings().setMapToolbarEnabled(false);

        gMap.setOnMarkerClickListener(this);

        final LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (int i = 0; i < gpsValues.size(); i++) {

            String Lat = gpsValues.get(i).split("\\$")[0];
            String Lon = gpsValues.get(i).split("\\$")[1];

            LatLng latLng = new LatLng(Double.parseDouble(Lat), Double.parseDouble(Lon));

            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

            dropPinEffect(marker);
            marker.setTag(i);
            builder.include(marker.getPosition());
/*
            try {
                CustomInfoWindowAdapter customInfoWindowAdapter = new CustomInfoWindowAdapter(this,outputArray.getJSONObject(i));
                googleMap.setInfoWindowAdapter(customInfoWindowAdapter);

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {

                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }*/


        }

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                final LatLngBounds bounds = builder.build();
                int padding = 100; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                gMap.moveCamera(cu);
                gMap.animateCamera(cu);
            }
        });


    }

    private void dropPinEffect(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 3500;

        final Interpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed
                                / duration), 0);
                marker.setAnchor(0.5f, 1.0f + 14 * t);

                if (t > 0.0) {
                    // Post again 15ms later.
                    handler.postDelayed(this, 15);
                } else {
                    marker.showInfoWindow();

                }
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {


        int position = Integer.parseInt(marker.getTag().toString());

        Intent in = new Intent(context, QueryDetailsActivity.class);

        try {
            in.putExtra("jsonObject", outputArray.getJSONObject(position).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        in.putExtra("create_query_object", create_query_object);

        in.putExtra("appName", appName);

        startActivity(in);

        return false;
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        if (PrefManger.getSharedPreferencesString(QueryIndexColumnsActivity.this, AppConstants.Notification_Back_Press, "") != null) {
//            String strNBP_child = PrefManger.getSharedPreferencesString(QueryIndexColumnsActivity.this, AppConstants.Notification_Back_Press, "");
//            Log.d(TAG, "onBackPressedQCA: " + strNBP_child);
//            if (PrefManger.getSharedPreferencesString(QueryIndexColumnsActivity.this, AppConstants.Notification_Back_Press, "").equalsIgnoreCase(strNBP_child)) {
//                Intent intent = new Intent(QueryIndexColumnsActivity.this, BottomNavigationActivity.class);
//                intent.putExtra("NotifRefreshAppsList", "1");
//                startActivity(intent);
//                finish();
//            }
//        } else {
//            finish();
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                if (PrefManger.getSharedPreferencesString(QueryIndexColumnsActivity.this, AppConstants.Notification_Back_Press, "") != null) {
//                    String strNBP_child = PrefManger.getSharedPreferencesString(QueryIndexColumnsActivity.this, AppConstants.Notification_Back_Press, "");
//                    Log.d(TAG, "onBackPressedQCA: " + strNBP_child);
//                    if (PrefManger.getSharedPreferencesString(QueryIndexColumnsActivity.this, AppConstants.Notification_Back_Press, "").equalsIgnoreCase(strNBP_child)) {
//                        Intent intent = new Intent(QueryIndexColumnsActivity.this, BottomNavigationActivity.class);
//                        intent.putExtra("NotifRefreshAppsList", "1");
//                        startActivity(intent);
//                        finish();
//                    }
//                } else {
////                    intent.putExtra("NotifRefreshAppsList", "2");
//                    finish();
//                }
//                return true;
//            default:
//                return false;
//        }
//    }

    private class IndexColumnsAdapter extends RecyclerView.Adapter<IndexColumnsAdapter.ViewHolder> {

        private JSONArray array;
        String strControlTypeView;

        public IndexColumnsAdapter(JSONArray array) {

            this.array = array;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.row_item_index, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            try {

                final JSONObject object = array.getJSONObject(position);

                LinearLayout linearLayout = holder.llIndexColumns;

                LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                for (int i = 0; i < object.names().length(); i++) {

                    if (gpsColumnName != null && object.names().getString(i).contains(gpsColumnName) && object.names().getString(i).contains("_Coordinates")) {

                        gpsValues.add(object.getString(object.names().getString(i)));

                    }

                    if (List_Index_Columns.contains(object.names().getString(i))) {

                        View view = lInflater.inflate(R.layout.index_column, null);

                        CustomTextView columnName = view.findViewById(R.id.columnName);
                        columnName.setVisibility(View.GONE);
                        CustomTextView columnValue = view.findViewById(R.id.columnValue);

                        if (List_Index_Columns.get(i).equalsIgnoreCase(List_Final_Columns.get(i).getField_ControlObject().getControlName())) {
                            String strControlType = List_Final_Columns.get(i).getField_ControlObject().getControlType();
                            Log.d(TAG, "onCreateControlType: "+strControlType);

                            if(strControlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA)){
                                Log.d(TAG, "onBindViewHolderQI: "+object.getString(object.names().getString(i)));
                                Glide.with(view.getContext()).load(object.getString(object.names().getString(i)))
                                        .placeholder(R.drawable.icons_image_update)
                                        .dontAnimate().into(holder.iv_imageControl);
                                columnValue.setVisibility(View.GONE);
                            }
                        }

                        columnName.setText(object.names().getString(i));

                        columnValue.setText(object.getString(object.names().getString(i)));
                        linearLayout.addView(view);

                    } else if (object.names().getString(i).contains("_Coordinates") && List_Index_Columns.contains(object.names().getString(i).split("_")[0])) {
                        View view = lInflater.inflate(R.layout.index_column, null);

                        CustomTextView columnName = view.findViewById(R.id.columnName);
                        CustomTextView columnValue = view.findViewById(R.id.columnValue);

                        columnName.setText(object.names().getString(i));

                        columnValue.setText(object.getString(object.names().getString(i)));

                        linearLayout.addView(view);
                    }

                }


                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        // Jay prem
                        if (strChildName != null && strChildName.equals("ChildForm")) {
//                            Toast.makeText(getApplicationContext(),"Child",Toast.LENGTH_SHORT).show();

                            Intent in = new Intent(context, MainActivity.class);

                            in.putExtra("jsonObject", object.toString());

                            in.putExtra("create_query_object", create_query_object);

                            in.putExtra("appName", appName);

                            in.putExtra("s_childForm", strChildName);

                            context.startActivity(in);

                        } else {
                            Intent in = new Intent(context, QueryDetailsActivity.class);

                            in.putExtra("jsonObject", object.toString());

                            in.putExtra("create_query_object", create_query_object);

                            in.putExtra("appName", appName);

                            context.startActivity(in);

                        }
                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        public int getItemCount() {
            return array.length();
        }


        private class ViewHolder extends RecyclerView.ViewHolder {

            ImageView iv_imageControl;
            LinearLayout llIndexColumns;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                iv_imageControl = itemView.findViewById(R.id.iv_imageControl);
                llIndexColumns = itemView.findViewById(R.id.llIndexColumns);


            }
        }
    }

    public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private View myContentsView;
        private Context context;
        private JSONObject object;

        public CustomInfoWindowAdapter(Context context, JSONObject object) {
            myContentsView = getLayoutInflater().inflate(R.layout.row_item_index, null);
            this.context = context;
            this.object = object;
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

            final LinearLayout llIndexColumns = myContentsView.findViewById(R.id.llIndexColumns);

            LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for (int i = 0; i < object.names().length(); i++) {

                try {
                    if (List_Index_Columns.contains(object.names().getString(i))) {

                        View view = lInflater.inflate(R.layout.index_column, null);

                        CustomTextView columnName = view.findViewById(R.id.columnName);
                        CustomTextView columnValue = view.findViewById(R.id.columnValue);

                        columnName.setText(object.names().getString(i));

                        columnValue.setText(object.getString(object.names().getString(i)));

                        llIndexColumns.addView(view);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
