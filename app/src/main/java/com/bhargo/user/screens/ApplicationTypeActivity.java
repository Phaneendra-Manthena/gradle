package com.bhargo.user.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.bhargo.user.R;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveHelper;

import static com.bhargo.user.screens.onetoonechat.ChatActivity.REQUEST_APP_DISTRIBUTION_CODE;

public class ApplicationTypeActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ApplicationTypeActivity";
    ImageView iv_dc, iv_qf, iv_cf, iv_re, iv_db, iv_module;
    LinearLayout ll_dc, ll_qf, ll_cf, ll_re, ll_db, ll_module;

    String type, id, name;
    ImproveHelper improveHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_type);
        improveHelper = new ImproveHelper(this);
        initViews();

        Intent intent = getIntent();

        if (intent != null) {
            type = intent.getStringExtra("type");
            id = intent.getStringExtra("id");
            name = intent.getStringExtra("name");
        }

        ll_dc = findViewById(R.id.ll_dc);
        ll_qf = findViewById(R.id.ll_qf);
        ll_cf = findViewById(R.id.ll_cf);
        ll_re = findViewById(R.id.ll_re);
        ll_db = findViewById(R.id.ll_db);
        ll_module = findViewById(R.id.ll_module);

        ll_dc.setOnClickListener(this);
        ll_qf.setOnClickListener(this);
        ll_cf.setOnClickListener(this);
        ll_re.setOnClickListener(this);
        ll_db.setOnClickListener(this);
        ll_module.setOnClickListener(this);


    }

    private void initViews() {
        try {
            initializeActionBar();
            title.setText(getResources().getString(R.string.app_type_selection));
            ib_settings.setVisibility(View.GONE);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "initViews", e);
        }
    }


    @Override
    public void onClick(View v) {

        Intent intent = new Intent(ApplicationTypeActivity.this, AppTypeListActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("id", id);
        intent.putExtra("name", name);

        switch (v.getId()) {
            case R.id.ll_dc:
                intent.putExtra("AppType", "Datacollection");

                Log.d(TAG, "onClickAppType: " + "Datacollection");
                break;
            case R.id.ll_qf:
                intent.putExtra("AppType", "QueryForm");
                Log.d(TAG, "onClickAppType: " + "QueryForm");
                break;
            case R.id.ll_cf:
                intent.putExtra("AppType", "ChildForm");
                Log.d(TAG, "onClickAppType: " + "ChildForm");
                break;
            case R.id.ll_re:
                intent.putExtra("AppType", "Reports");
                Log.d(TAG, "onClickAppType: " + "Reports");
                break;
            case R.id.ll_db:
                intent.putExtra("AppType", "DashBoard");
                Log.d(TAG, "onClickAppType: " + "DashBoard");
                break;
            case R.id.ll_module:
                intent.putExtra("AppType", "Module");
                Log.d(TAG, "onClickAppType: " + "Module");
                break;
        }
        startActivityForResult(intent, REQUEST_APP_DISTRIBUTION_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_APP_DISTRIBUTION_CODE:

                if (data != null) {
                    Bundle bundle = data.getExtras();
                    //WHAT TO DO TO GET THE BUNDLE VALUES//
                    String appName = bundle.getString("AppName");
                    String strAppType = bundle.getString("AppType");

                    Bundle bundleSend = data.getExtras();
                    bundleSend.putString("AppName", appName);
                    bundleSend.putString("AppType", strAppType);

                    Intent returnIntent = new Intent();
                    returnIntent.putExtras(bundleSend);

                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
                break;
        }
    }
}
