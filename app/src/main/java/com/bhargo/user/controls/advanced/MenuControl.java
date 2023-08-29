package com.bhargo.user.controls.advanced;

import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.GlobalObjects;
import com.bhargo.user.R;
import com.bhargo.user.adapters.CustomGridRecyclerAdapter;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.GridImagesModel;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;

import java.io.File;
import java.util.List;

public class MenuControl implements UIVariables {

    private static final String TAG = "MenuControl";
    static boolean isIconsDownloaded;
    private final int MenuControlTAG = 0;
    Activity context;
    ImageView imageView;
    ControlObject controlObject;
    SessionManager sessionManager;
    CustomTextView tv_displayName;
    int noOfRows, noOfColumns;
    String strTypeOfButton, orgId, appName;
    LinearLayout layout_container;
    LinearLayout.LayoutParams param_rowItems;
    LinearLayout.LayoutParams param_other;
    LinearLayout.LayoutParams param_view;
    FrameLayout fl_main_rv;
    //    private List<TypeOfButtonModel> typeofButtonsList;
    int coMarginleft = 0, coMarginTop = 0, coMarginRight = 0, coMarginBottom = 0;
    int coPaddingleft = 0, coPaddingTop = 0, coPaddingRight = 0, coPaddingBottom = 0;
    GlobalObjects globalObjects;
    ImproveHelper improveHelper;
    private LinearLayout linearLayout, ll_displayName, ll_inner_border;
    //    private List<AppIconModel> gridImagesModelList;
    private List<GridImagesModel> gridImagesModelList;
    private GetServices getServices;
    //        private rvGridView rvGridView;
    private RecyclerView rvGridView;
    private String viewType;
    private int iTypeOfBorder = -1;
    View view;

    public MenuControl(Activity context, ControlObject controlObject, String orgId, String appName) {
        this.context = context;
        this.controlObject = controlObject;
        this.orgId = orgId;
        this.appName = appName;
        improveHelper = new ImproveHelper(context);
        initView();

    }


    private void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
//        linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            globalObjects = new GlobalObjects();

            addMenuControlStrip(context);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initView", e);
        }
    }

    private void addMenuControlStrip(Context context) {
        try {
            final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              view = lInflater.inflate(R.layout.control_menu, null);
            view.setTag(MenuControlTAG);

            sessionManager = new SessionManager(context);
            getServices = RetrofitUtils.getUserService();

            rvGridView = view.findViewById(R.id.rvGridViewControl);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            fl_main_rv = view.findViewById(R.id.fl_main_rv);
            ll_inner_border = view.findViewById(R.id.ll_inner_border);
            tv_displayName = view.findViewById(R.id.tv_displayName);


            setMenuControlAllValues();

            linearLayout.addView(view);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "addMenuControlStrip", e);
        }
    }

    @SuppressLint("NewApi")
    private void setMenuControlAllValues() {
        try {
            if (controlObject.getControlName() != null) {
                tv_displayName.setTag(controlObject.getControlName());
            }
            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            } else {
                ll_displayName.setVisibility(View.VISIBLE);
            }
            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            if (controlObject.getTypeOfMenu() != null) {
                viewType = controlObject.getTypeOfMenu();
//            viewType = "Linear";
//            viewType = "Grid";
                Log.d(TAG, "setMenuControlValuesC: " + controlObject.getTypeOfMenu());
            }

            if (controlObject.getNoOfRows() != null) {
                noOfRows = Integer.parseInt(controlObject.getNoOfRows());
            }
            if (controlObject.getNoofColumns() != null) {
                noOfColumns = Integer.parseInt(controlObject.getNoofColumns());
            }
            if (controlObject.getTypeOfBorder() != -1) {
                iTypeOfBorder = controlObject.getTypeOfBorder();
//            iTypeOfBorder = 4;
                Log.d(TAG, "getTypeOfBorder: " + iTypeOfBorder);
            }
            if (controlObject.getTypeOfButton() != null) {
                strTypeOfButton = controlObject.getTypeOfButton();
//            iTypeOfBorder = 4;
                Log.d(TAG, "getTypeOfButton: " + strTypeOfButton);


            }

            rvGridView.setHasFixedSize(true);

            if (controlObject.getBorderMarginLeft() != null || !controlObject.getBorderMarginLeft().equals("null")) {
                coMarginleft = Integer.parseInt(controlObject.getBorderMarginLeft());
                Log.d(TAG, "ItemMarginPixel: " + coMarginleft);
            }
            if (controlObject.getBorderMarginTop() != null || !controlObject.getBorderMarginTop().equals("null")) {
                coMarginTop = Integer.parseInt(controlObject.getBorderMarginTop());
                Log.d(TAG, "ItemMarginPixel: " + coMarginTop);
            }
            if (controlObject.getBorderMarginRight() != null || !controlObject.getBorderMarginRight().equals("null")) {
                coMarginRight = Integer.parseInt(controlObject.getBorderMarginRight());
                Log.d(TAG, "ItemMarginPixel: " + coMarginRight);
            }
            if (controlObject.getBorderMarginBottom() != null || !controlObject.getBorderMarginBottom().equals("null")) {
                coMarginBottom = Integer.parseInt(controlObject.getBorderMarginBottom());
                Log.d(TAG, "ItemMarginPixel: " + coMarginBottom);
            }
            LinearLayout.LayoutParams co_LayoutMargin = (LinearLayout.LayoutParams) fl_main_rv.getLayoutParams();
            co_LayoutMargin.setMargins(coMarginleft, coMarginTop, coMarginRight, coMarginBottom);
//            co_LayoutMargin.setMargins(10,10,10,10);
            fl_main_rv.setLayoutParams(co_LayoutMargin);
            Log.d(TAG, "onBindViewHolderMargin: " + coMarginleft + coMarginTop + coMarginRight + coMarginBottom);
            // set margins for item end

            // Set Padding for item from Control Object
            if (controlObject.getBorderPaddingLeft() != null || !controlObject.getBorderPaddingLeft().equals("null")) {

                coPaddingleft = Integer.parseInt(controlObject.getBorderPaddingLeft());
            }

            if (controlObject.getBorderPaddingTop() != null) {

                coPaddingTop = Integer.parseInt(controlObject.getBorderPaddingTop());
            }
            if (controlObject.getBorderPaddingRight() != null || !controlObject.getBorderPaddingRight().equalsIgnoreCase("null")) {

                coPaddingRight = Integer.parseInt(controlObject.getBorderPaddingRight());
            }
            if (controlObject.getBorderPaddingBottom() != null || !controlObject.getBorderPaddingBottom().equalsIgnoreCase("null")) {
                coPaddingBottom = Integer.parseInt(controlObject.getBorderPaddingBottom());
            }
            fl_main_rv.setPadding(coPaddingleft, coPaddingTop, coPaddingRight, coPaddingBottom);
//            holder.ll_grid_item.setPadding(10,10,10,10);
            Log.d(TAG, "onBindViewHolderPadding: " + coPaddingleft + coPaddingTop + coPaddingRight + coPaddingBottom);

            if (viewType.equalsIgnoreCase("Grid")) {
                if (iTypeOfBorder == 2) {
                    rvGridView.setBackground(context.getDrawable(R.drawable.recyclerview_border));
                } else if (iTypeOfBorder == 3) {

                    ll_inner_border.setBackgroundColor(context.getColor(R.color.red));
                    int paddingDp = 3;
                    float density = context.getResources().getDisplayMetrics().density;
                    int paddingPixel = (int) (paddingDp * density);
                    Log.d(TAG, "PaddingPixel: " + paddingPixel);
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ll_inner_border.getLayoutParams();
                    params.setMargins(paddingPixel, paddingPixel, paddingPixel, paddingPixel);
                    ll_inner_border.setLayoutParams(params);

                } else if (iTypeOfBorder == 4) {
                    fl_main_rv.setBackground(context.getDrawable(R.drawable.menu_full_rect_border));

                }

                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, noOfColumns); //  no of columns
                rvGridView.setLayoutManager(gridLayoutManager);
            } else {
                LinearLayoutManager linearLayoutManager = new GridLayoutManager(context, LinearLayoutManager.VERTICAL);
                rvGridView.setLayoutManager(linearLayoutManager);
            }

            if (controlObject.getMenuControlObjectList() != null && controlObject.getMenuControlObjectList().size() > 0) {
                CustomGridRecyclerAdapter customAdapter = new CustomGridRecyclerAdapter
                        (context, controlObject, viewType, noOfRows, noOfColumns,
                                controlObject.getMenuControlObjectList(), iTypeOfBorder, strTypeOfButton, orgId, appName.replaceAll(" ", "_"), coMarginleft, coMarginRight, coMarginTop, coMarginBottom);// noOfRows-2 & noOfColumns-3
                rvGridView.setAdapter(customAdapter);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setMenuControlAllValues", e);
        }
    }

    public LinearLayout getMenuContorlLayout() {
        return linearLayout;
    }

    private boolean isFileExists(String filename) {
        File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
        return folder1.exists();
    }

    public void downloadMenuIcons(String filePath, String strSDCardUrl) {
        try {

            if (!filePath.contains(" ")) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(filePath));
                if (request != null) {
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                    request.setTitle(context.getResources().getString(R.string.app_name));
                    request.setDescription("Downloading menu icons...");
                    request.allowScanningByMediaScanner();
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//            request.setDestinationInExternalFilesDir(getContext(), Environment.getExternalStorageDirectory().getAbsolutePath(), strSDCardUrl);
//            request.setDestinationInExternalPublicDir("", strSDCardUrl);
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        request.setDestinationInExternalFilesDir(context, Environment.getExternalStorageDirectory().getAbsolutePath(), strSDCardUrl);
                    } else {
                        request.setDestinationInExternalPublicDir("", strSDCardUrl);
                    }
                    DownloadManager downloadManagerFiles = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    long downloaId = downloadManagerFiles.enqueue(request);

                    Log.d(TAG, "startDownload: " + downloadManagerFiles);
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "downloadMenuIcons", e);
        }
    }
    public boolean getVisibility() {
        controlObject.setInvisible(linearLayout.getVisibility() != View.VISIBLE);
        return controlObject.isInvisible();
    }


    public void setVisibility(boolean visibility) {

        if (visibility) {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        } else {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }
    }


    public boolean isEnabled() {

        return !controlObject.isDisable();
    }


    public void setEnabled(boolean enabled) {
//        setViewDisable(view, !enabled);
        controlObject.setDisable(!enabled);
        setViewDisableOrEnableDefault(context,view, enabled);
    }



    @Override
    public String getTextSize() {
        return controlObject.getTextSize();
    }

    @Override
    public void setTextSize(String size) {
        if (size != null) {
            controlObject.setTextSize(size);
            tv_displayName.setTextSize(Float.parseFloat(size));
        }

    }

    @Override
    public String getTextStyle() {
        return controlObject.getTextStyle();
    }

    @Override
    public void setTextStyle(String style) {
        if (style != null && style.equalsIgnoreCase("Bold")) {
            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
            tv_displayName.setTypeface(typeface_bold);
            controlObject.setTextStyle(style);
        } else if (style != null && style.equalsIgnoreCase("Italic")) {
            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
            tv_displayName.setTypeface(typeface_italic);
            controlObject.setTextStyle(style);
        }
    }

    @Override
    public String getTextColor() {
        return controlObject.getTextHexColor();
    }

    @Override
    public void setTextColor(String color) {
        if (color != null && !color.equalsIgnoreCase("")) {
            controlObject.setTextHexColor(color);
            controlObject.setTextColor(Color.parseColor(color));
            tv_displayName.setTextColor(Color.parseColor(color));
        }
    }


}
