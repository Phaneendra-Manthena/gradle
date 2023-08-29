package com.bhargo.user.screens;

import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.AppsAdapter;
import com.bhargo.user.adapters.SearchAppsAdapter;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppDetails;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class SearchApps extends BaseActivity {

    private static final String TAG = "SearchApps";

    Context context;
    CustomEditText et_search_apps;
    RecyclerView rv_apps;
//    SearchAppsAdapter searchAppsAdapter;
    AppsAdapter searchAppsAdapter;
    SessionManager sessionManager;
    CustomTextView tv_no_records;
    ImproveDataBase improveDataBase;
    ImproveHelper improveHelper;
    GetServices getServices;
    String userId,strOrgId,strPostId;
    List<AppDetails> appsList;
    CustomTextView ct_alNoRecords;
    String displayAs = "Application";
    ImageView iv_search;

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setBhargoTheme(this, AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_apps);

        initViews();

    }

    private void initViews() {

        context = this;

        sessionManager = new SessionManager(context);
        improveDataBase = new ImproveDataBase(context);
        improveHelper = new ImproveHelper(context);

        initializeActionBar();
        enableBackNavigation(true);
        ib_settings.setVisibility(View.GONE);

        tv_no_records = findViewById(R.id.tv_noRecords);
        ct_alNoRecords = findViewById(R.id.ct_alNoRecords);
        et_search_apps = findViewById(R.id.cet_usersSearch);
        iv_search = findViewById(R.id.iv_search);

        rv_apps = findViewById(R.id.rv_apps);
        rv_apps.setHasFixedSize(true);
        rv_apps.setNestedScrollingEnabled(false);
        rv_apps.setLayoutManager(new LinearLayoutManager(context));

        userId = sessionManager.getUserDataFromSession().getUserID();
        strOrgId = sessionManager.getOrgIdFromSession();
        strPostId =  sessionManager.getPostsFromSession();

        appsList = new ArrayList<>();

//        searchAppsAdapter = new SearchAppsAdapter(SearchApps.this, appsList,true,false);
        searchAppsAdapter = new AppsAdapter(SearchApps.this, appsList,true,false);
        rv_apps.setAdapter(searchAppsAdapter);


        et_search_apps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equalsIgnoreCase("") && s.length() > 0) {
                        iv_search.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_icon_close));
                        getAppsForSearch(s.toString());
                    }else{
                        ct_alNoRecords.setVisibility(View.VISIBLE);
                        rv_apps.setVisibility(View.GONE);
                        searchAppsAdapter.updateList(new ArrayList<>());
                    }

            }
        });

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search_apps.setText("");
                et_search_apps.setHint(getString(R.string.search));
                iv_search.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_icon_search_grey));
                ct_alNoRecords.setVisibility(View.VISIBLE);
                rv_apps.setVisibility(View.GONE);
                searchAppsAdapter.updateList(new ArrayList<>());
            }
        });
        getServices = RetrofitUtils.getUserService();


    }

    private void getAppsForSearch(String appName) {
        try {
            appsList = improveDataBase.getAppsListForSearch(strOrgId, sessionManager.getUserDataFromSession().getUserID(), strPostId,sessionManager.getUserTypeIdsFromSession(), displayAs,appName);
            if (appsList != null && appsList.size() > 0) {
                ct_alNoRecords.setVisibility(View.GONE);
                rv_apps.setVisibility(View.VISIBLE);
                searchAppsAdapter.updateList(appsList);
            }else{
                ct_alNoRecords.setVisibility(View.VISIBLE);
                rv_apps.setVisibility(View.GONE);
                searchAppsAdapter.updateList(new ArrayList<>());
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "populateGroups", e);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                navigateBack();
                return true;
            default:
                return false;
        }
    }
//    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
////        navigateBack();
//    }

    public void navigateBack() {
        try {
            hideKeyboard(SearchApps.this, view);
           /* ImproveHelper.alertDialogWithRoundShapeMaterialTheme(SearchApps.this, getString(R.string.are_you_sure),
                    getString(R.string.yes), getString(R.string.d_no), new ImproveHelper.IL() {
                        @Override
                        public void onSuccess() {
                            finish();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });*/
            finish();

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "mainActivityOnBackPressAlertDialog", e);
        }
    }


}
