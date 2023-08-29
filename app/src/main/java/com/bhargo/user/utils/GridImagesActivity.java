package com.bhargo.user.utils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.CustomGridAdapter;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppIconData;
import com.bhargo.user.pojos.AppIconModel;
import com.bhargo.user.pojos.GridImagesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GridImagesActivity extends BaseActivity {


    private static final String TAG = "GridImagesActivity";
    GridView gridViewImages;
    List<AppIconModel> gridImagesModelList;
    SessionManager sessionManager;
    GetServices getServices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_images);

        initViews();

        getServices = RetrofitUtils.getUserService();
        sessionManager = new SessionManager(this);

        gridViewImages = findViewById(R.id.gridViewImages);


        AppIconData appIconData = new AppIconData();
        if (sessionManager.getOrgIdFromSession() != null) {
            appIconData.setOrgId(sessionManager.getOrgIdFromSession());
        }

//        mGridImagesAPi(mapData);
        mGridImagesAPi(appIconData);

    }

    //    private void mGridImagesAPi(final Map<String,String> appIconData) {
    private void mGridImagesAPi(AppIconData appIconData) {

        Call<GridImagesModel> call = getServices.getDefaultAppIcons(sessionManager.getAuthorizationTokenId(),appIconData);

        call.enqueue(new Callback<GridImagesModel>() {
            @Override
            public void onResponse(Call<GridImagesModel> call, Response<GridImagesModel> response) {

                if (response.body() != null) {
                    Log.d(TAG, "onResponseGridImagesAPi: " + response.body().getAppIcon());
                    if (response.body().getStatus() != null) {

                        GridImagesModel gridImagesModel = response.body();
                        Log.d(TAG, "onResponseGridImagesAPi: " + gridImagesModel.getMessage());

                        final List<AppIconModel> appIconModelList = gridImagesModel.getAppIcon();

                        if (appIconModelList != null && appIconModelList.size() > 0) {
                            CustomGridAdapter customAdapter = new CustomGridAdapter(getApplicationContext(), appIconModelList);
                            gridViewImages.setAdapter(customAdapter);
                        }else{
                            Log.d(TAG, "onResponseGridImagesAPi: " + "No Default Icons");
                        }
                        // implement setOnItemClickListener event on GridView
                        gridViewImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // set an Intent to Another Activity
                                Log.d(TAG, "GridOnItemClick: " + appIconModelList.get(position).getIcon_Image());
                               /* Intent intent = new Intent(GridImagesActivity.this, DataCollectionActivity.class);
                                intent.putExtra("grid_selected_image", appIconModelList.get(position).getIcon_Image()); // put image data in Intent
                                startActivity(intent); // start Intent*/

                                Intent intent = new Intent();
                                intent.putExtra("image_path", appIconModelList.get(position).getIcon_Image());
                                setResult(RESULT_OK, intent);
                                finish();
//                                PrefManger.putSharedPreferencesString(GridImagesActivity.this,"grid_selected_image",appIconModelList.get(position).getIcon_Image());

                            }
                        });
                    }
                }

            }

            @Override
            public void onFailure(Call<GridImagesModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });


    }

    public void initViews() {
        initializeActionBar();
        title.setText("Default Images");
        ib_settings.setVisibility(View.GONE);
        enableBackNavigation(true);
    }

}
