package com.bhargo.user.controls.advanced;

import static com.bhargo.user.utils.ImproveHelper.pxToDPControlUI;
import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.uisettings.pojos.ControlUIProperties;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class BarCode implements UIVariables {
    private static final String TAG = "BarCode";
    private final int ImageTAG = 0;
    Context context;
    ImageView imageView;
    ControlObject controlObject;
    String barcodeValue = "123456789";
    CardView cv_all;
    View view;
    ImproveHelper improveHelper;
    private LinearLayout linearLayout, ll_main_inside, ll_displayName,ll_label,ll_control_ui;
    private CustomTextView tv_displayName, tv_hint,ct_showText;
    public ImageView iv_mandatory, mainImageView;
    static String barCodeValue = null;

    public BarCode(Context context, ControlObject controlObject) {
        this.context = context;
        this.controlObject = controlObject;
        improveHelper = new ImproveHelper(context);

        initView();

    }

    public static void createBarCode(String barcodeValue, ImageView imageView) {
        barCodeValue = barcodeValue;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(barcodeValue, BarcodeFormat.CODE_128, 400, 100);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    public static void createBarCode(String barcodeValue, ImageView imageView, ControlUIProperties controlUIProperties) {
        barCodeValue = barcodeValue;
        int  imgWidth = 400;
        int  imgHeight = 100;
        if (controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
            String strWidthType = controlUIProperties.getTypeOfWidth();
            String strHeightType = controlUIProperties.getTypeOfHeight();
            if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                imgWidth = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                imgHeight = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
            }
            else if (strWidthType.equalsIgnoreCase(AppConstants.MATCH_PARENT) && strHeightType.equalsIgnoreCase(AppConstants.MATCH_PARENT)) {
                imgWidth = LinearLayout.LayoutParams.MATCH_PARENT;
                imgHeight = LinearLayout.LayoutParams.MATCH_PARENT;
            }
        }
            barCodeValue = barcodeValue;
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(barcodeValue, BarcodeFormat.CODE_128, imgWidth, imgHeight);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imageView.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }


    }


    public void createBarCodeDynamically(String barcodeValue) {
        controlObject.setText(barcodeValue);
        createBarCode(barcodeValue, mainImageView);
    }

    public void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            addImageStrip(context);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initViews", e);
        }
    }

    public LinearLayout getBarCode() {

        return linearLayout;
    }

    public void addImageStrip(final Context context) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View view = lInflater.inflate(R.layout.control_barcode, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                    view = linflater.inflate(R.layout.layout_barcode_default, null);
//                    view = linflater.inflate(R.layout.control_barcode_six, null);
                }
            } else {
//                view = linflater.inflate(R.layout.control_barcode, null);
                view = linflater.inflate(R.layout.layout_barcode_default, null);
            }
            view.setTag(ImageTAG);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            ll_label = view.findViewById(R.id.ll_label);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            cv_all = view.findViewById(R.id.cv_all);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_displayName.setText("BarCode");
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);

            mainImageView = view.findViewById(R.id.iv_barcode);
            ct_showText = view.findViewById(R.id.ct_showText);

//        mainImageView.setImageBitmap(createBarCode(barcodeValue));

            setControlValues();

            linearLayout.addView(view);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "addImageStrip", e);
        }

    }

    private void setControlValues() {
        try {
            if (controlObject.isHideDisplayName()) {
                tv_hint.setVisibility(View.GONE);
                ll_displayName.setVisibility(View.GONE);
                removeCardViewPadding();
            } else {
                tv_hint.setVisibility(View.VISIBLE);
                ll_displayName.setVisibility(View.VISIBLE);
            }
            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            if (controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                tv_hint.setText(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }
            if (controlObject.getDefaultValue() != null && !controlObject.getDefaultValue().isEmpty()) {
                createBarCode(controlObject.getDefaultValue(), mainImageView);
                controlObject.setText(controlObject.getDefaultValue());
            }
            if (controlObject.isInvisible()) {
                linearLayout.setVisibility(View.GONE);
            } else {
                linearLayout.setVisibility(View.VISIBLE);
            }
/*            if (controlObject.isHideDisplayName()) {
                tv_displayName.setVisibility(View.GONE);
                tv_hint.setVisibility(View.GONE);
            } else {
                tv_displayName.setVisibility(View.VISIBLE);
            }*/
            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setControlValues", e);
        }

    }

    public void removeCardViewPadding() {
        try {
            if (cv_all != null) {
                cv_all.setUseCompatPadding(false);
                cv_all.setCardElevation(0);
                cv_all.setRadius(0);
                ll_main_inside.setPadding(0, 0, 0, 0);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "removeCardViewPadding", e);
        }
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

    public String getDisplayName() {
        return controlObject.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        controlObject.setDisplayName(displayName);
        tv_displayName.setText(displayName);
    }

    public String getHint() {
        return controlObject.getHint();
    }

    public void setHint(String hint) {
        controlObject.setHint(hint);
        tv_hint.setVisibility(View.VISIBLE);
        tv_hint.setText(hint);
    }

    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        if(hideDisplayName){
            tv_hint.setVisibility(View.GONE);
            ll_displayName.setVisibility(View.GONE);
            removeCardViewPadding();
        }else{
            ll_displayName.setVisibility(View.VISIBLE);
            if(tv_hint != null && !tv_hint.getText().toString().isEmpty()){
                tv_hint.setVisibility(View.VISIBLE);
            }else{
                tv_hint.setVisibility(View.GONE);
            }

        }
    }


    public boolean getVisibility() {
        controlObject.setInvisible(linearLayout.getVisibility() != View.VISIBLE);
        return controlObject.isInvisible();
    }

    public void setVisibility(boolean visibility) {
        if (visibility) {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        }
    }

    public String getBarcodeValue(){
        return barCodeValue;
    }
    public boolean isInvisible() {
        return controlObject.isInvisible();
    }

    public void setInvisible(boolean invisible) {
        controlObject.setInvisible(invisible);
        if(invisible){
            linearLayout.setVisibility(View.GONE);
        }
    }

    public LinearLayout getLl_main_inside(){
        return ll_main_inside;
    }
    public CustomTextView getTv_displayName(){
        return tv_displayName;
    }
    public LinearLayout getLl_control_ui(){
        return ll_control_ui;
    }
    public LinearLayout getLl_label(){
        return ll_label;
    }
    public ImageView getMainImageView(){
        return mainImageView;
    }
    public ControlObject getControlObject() {
        return controlObject;
    }

    public void showMessageBelowControl(String msg) {
        if(msg != null && !msg.isEmpty()) {
            ct_showText.setVisibility(View.VISIBLE);
            ct_showText.setText(msg);
        }else{
            ct_showText.setVisibility(View.GONE);
        }
    }

}