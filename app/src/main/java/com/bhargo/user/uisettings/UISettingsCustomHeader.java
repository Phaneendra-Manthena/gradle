package com.bhargo.user.uisettings;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.ImproveHelper;

public class UISettingsCustomHeader {
    private static final String TAG = "UISettingsCustomHeader";

    private final boolean SubformFlag;
    private final int SubformPos;
    private final String SubformName;
    private final ImproveHelper improveHelper;
    Activity context;
    ControlObject controlObject;
    LinearLayout linearLayout, ll_main_inside, ll_control_ui;
    CustomTextView tv_custom_header;
    private View view;

    public UISettingsCustomHeader(Activity context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
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

            addCustomHeaderStrip();
        } catch (Exception e) {
            Log.d(TAG, "initView: " + e);
        }
    }

    private void addCustomHeaderStrip() {
        try {
            final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = lInflater.inflate(R.layout.layout_ui_custom_header, null);

            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            tv_custom_header = view.findViewById(R.id.tv_custom_header);
            tv_custom_header.setText(controlObject.getDisplayName());
            Log.d(TAG, "addCustomHeaderStripControlName: " + controlObject.getDisplayName());
            linearLayout.addView(view);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }

    public LinearLayout getCustomHeaderLayout() {
        return linearLayout;
    }

    public LinearLayout getLl_main_inside() {
        return ll_main_inside;
    }

    public LinearLayout getLl_control_ui() {
        return ll_control_ui;
    }
    public CustomTextView getTv_custom_header() {
        return tv_custom_header;
    }

}
