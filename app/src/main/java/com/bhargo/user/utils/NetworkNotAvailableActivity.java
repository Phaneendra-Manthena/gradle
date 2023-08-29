package com.bhargo.user.utils;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bhargo.user.R;

public class NetworkNotAvailableActivity extends BaseActivity {

    Context context;
    ImageView iv_no_internet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_not_available);

        context =  NetworkNotAvailableActivity.this;
        initializeActionBar();
        enableBackNavigation(true);

        iv_no_internet = findViewById(R.id.iv_no_internet);
        iv_no_internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ImproveHelper.isNetworkStatusAvialable(context)){
                    finish();
                }
            }
        });

    }


}