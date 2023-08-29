package com.bhargo.user.notifications;

import static com.bhargo.user.utils.AppConstants.DC_DEPENDENT_LIST;
import static com.bhargo.user.utils.AppConstants.DEPENDENT_DC_NAME;
import static com.bhargo.user.utils.AppConstants.FromFlashScreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.adapters.NotificationsAdapter;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.firebase.Notification;
import com.bhargo.user.screens.BottomNavigationActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends BaseActivity {

    private static final String TAG = "NotificationsActivity";

    GetServices getServices;
    Context context;
    ImproveHelper improveHelper;
    SessionManager sessionManager;
    ImproveDataBase improveDataBase = new ImproveDataBase(this);

    NotificationsAdapter notificationsAdapter;
    LinearLayout layout_no_notifications;
    RecyclerView rv_notifications;
    List<Notification> notificationList;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        context = this;
        initializeActionBar();
        enableBackNavigation(true);

        title.setText(getString(R.string.notifications));

        improveHelper = new ImproveHelper(context);
        sessionManager = new SessionManager(context);
        getServices = RetrofitUtils.getUserService();

        layout_no_notifications = findViewById(R.id.layout_no_notifications);
        rv_notifications = findViewById(R.id.rv_notifications);

        ib_settings.setVisibility(View.GONE);
        iv_circle_appIcon.setVisibility(View.GONE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_notifications.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        notificationList = new ArrayList<>();
        notificationsAdapter = new NotificationsAdapter(context, notificationList);
        rv_notifications.setAdapter(notificationsAdapter);
        getNotificationsFromDB();

    }

    public void getNotificationsFromDB() {
        try {
            String user_id = "";
            String post_id = "";
            if (AppConstants.DefultAPK) {
                user_id = sessionManager.getUserDataFromSession().getUserID();
                post_id = sessionManager.getPostsFromSession();
            }
            String orgId = PrefManger.getSharedPreferencesString(getApplicationContext(), AppConstants.SP_ORGANISATION_ID, AppConstants.Default_OrgID);
            List<Notification> notificationList = improveDataBase.getNotificationsList(user_id, post_id, orgId, AppConstants.DefultAPK);
            if (notificationList.size() == 0) {
                layout_no_notifications.setVisibility(View.VISIBLE);
                rv_notifications.setVisibility(View.GONE);
            } else {
                layout_no_notifications.setVisibility(View.GONE);
                rv_notifications.setVisibility(View.VISIBLE);
                rv_notifications.setAdapter(notificationsAdapter);
                notificationsAdapter.updateNotificationsList(notificationList);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getNotificationsFromDB", e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(!AppConstants.DefultAPK){
                    notificationList.clear();
                    finish();
                }else {
                    navigateAlertDialog();
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {

        if(!AppConstants.DefultAPK){
            notificationList.clear();
            finish();
        }else {
            navigateAlertDialog();
        }
    }


    public void navigateAlertDialog() {
        try {
            hideKeyboard(NotificationsActivity.this, view);
            ImproveHelper.alertDialogWithRoundShapeMaterialTheme(NotificationsActivity.this, getString(R.string.are_you_sure),
                    getString(R.string.yes), getString(R.string.d_no), new ImproveHelper.IL() {
                        @Override
                        public void onSuccess() {

                                finish();
                            }

                        @Override
                        public void onCancel() {

                        }
                    });

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "mainActivityOnBackPressAlertDialog", e);
        }
    }


/*
    private void navigateAlertDialog() {
        new AlertDialog.Builder(NotificationsActivity.this)
//set icon
//set title
                .setTitle(R.string.are_you_sure)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.P)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
//set negative button
                .setNegativeButton(R.string.d_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        dialogInterface.dismiss();
                    }
                })
                .show();

    }
*/
}
