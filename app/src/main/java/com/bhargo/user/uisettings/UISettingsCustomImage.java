package com.bhargo.user.uisettings;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.R;
import com.bhargo.user.uisettings.pojos.ControlUIProperties;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RoundedImageView;
import com.bumptech.glide.Glide;

public class UISettingsCustomImage {
    private static final String TAG = "UISettingsCustomImage";

    private final boolean SubformFlag;
    private final int SubformPos;
    private final String SubformName;
    private final ImproveHelper improveHelper;
    Activity context;
    ControlObject controlObject;
    LinearLayout linearLayout, ll_main_inside, ll_control_ui;
    RoundedImageView uiCustomImage;
    private View view;
    private String strImageUrl = null;
    ControlUIProperties controlUIProperties;

    public UISettingsCustomImage(Activity context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName, ControlUIProperties controlUIProperties) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        this.controlUIProperties = controlUIProperties;
        improveHelper = new ImproveHelper(context);

        initView();

    }

    private void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            addCustomImageStrip();
        } catch (Exception e) {
            Log.d(TAG, "initView: " + e);
        }
    }

    private void addCustomImageStrip() {

        final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = lInflater.inflate(R.layout.layout_ui_custom_image, null);

        ll_main_inside = view.findViewById(R.id.ll_main_inside);
        ll_control_ui = view.findViewById(R.id.ll_control_ui);
        uiCustomImage = view.findViewById(R.id.uiCustomImage);
        linearLayout.addView(view);

        setControlValues();

    }

    private void setControlValues() {
        try {
            if (controlObject.getCustomImageURL() != null && !controlObject.getCustomImageURL().isEmpty()) {
                strImageUrl = controlObject.getCustomImageURL().trim();
            } else if (controlObject.getDisplayName() != null && !controlObject.getDisplayName().isEmpty()) {
                strImageUrl = controlObject.getDisplayName().trim();
            }
            if (strImageUrl != null) {
                Glide.with(context).load(strImageUrl.trim()).into(uiCustomImage);
            }
//            uiCustomImage.setScaleType(ImageView.ScaleType.FIT_XY);
//            uiCustomImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            uiCustomImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//            uiCustomImage.setCornerRadius(ImproveHelper.pxToDPControlUI(50));


            if (controlObject.getCustomImageFit() != null && !controlObject.getCustomImageFit().isEmpty()) {
                Log.d(TAG, "applyCUSTOM_IMAGE: "+controlObject.getControlType() +" - "+controlObject.getControlName());
                if (controlObject.getCustomImageFit().equalsIgnoreCase(AppConstants.FILL)) {
                    uiCustomImage.setScaleType(ImageView.ScaleType.FIT_XY);
                } else if (controlObject.getCustomImageFit().equalsIgnoreCase(AppConstants.COVER)) {
                    uiCustomImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                } else if (controlObject.getCustomImageFit().equalsIgnoreCase(AppConstants.CONTAIN)) {
                    uiCustomImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } else {
                    uiCustomImage.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            } else {
                uiCustomImage.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            LinearLayout.LayoutParams lpIView = (LinearLayout.LayoutParams) uiCustomImage.getLayoutParams();
            lpIView.width = -1;
            lpIView.height = Integer.parseInt(controlObject.getValue());
            uiCustomImage.setLayoutParams(lpIView);

            if (controlObject.getCustomImageRadius() != null && !controlObject.getCustomImageRadius().isEmpty()) {
                int r = Integer.parseInt(controlObject.getCustomImageRadius().trim());
                if (r != 0) {
                    uiCustomImage.setCornerRadius(ImproveHelper.pxToDPControlUI(r));
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }

    public LinearLayout getCustomImageLayout() {
        return linearLayout;
    }

    public LinearLayout getLl_main_inside() {
        return ll_main_inside;
    }

    public LinearLayout getLl_control_ui() {
        return ll_control_ui;
    }

    public ImageView getUiCustomImage() {
        return uiCustomImage;
    }

}
