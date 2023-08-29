package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.PathVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.ImproveHelper;
import com.bumptech.glide.Glide;


import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import  com.bhargo.user.utils.SignaturePad;


public class SignatureView implements PathVariables, UIVariables {


    private static final String TAG = "SignatureView";
    private final int SignatureTAG = 0;
    Activity context;
    ControlObject controlObject;
    CustomTextView tv_clearSignature;
    SignaturePad mSignaturePad;
    View divider;
    int position;
    View view;
    ImproveHelper improveHelper;
    private LinearLayout linearLayout,  ll_gallery,  ll_displayName,layout_upload_file,ll_tap_text,layout_camera_or_gallery_main,
            ll_main_inside,ll_control_ui,ll_signature;
    FrameLayout fl_uploaded_image;
    private CustomTextView tv_displayName, tv_hint, ct_alertSignature,ct_showText;
    private ImageView iv_mandatory, iv_SelectedImage;

    public SignatureView(Activity context, ControlObject controlObject) {
        this.context = context;
        this.controlObject = controlObject;
        improveHelper = new ImproveHelper(context);
        initView();

    }

    public void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            addSignatureStrip(context);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getSignature() {

        return linearLayout;
    }

    public void addSignatureStrip(final Activity context) {
        try {
            final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = lInflater.inflate(R.layout.control_signature, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                    view = lInflater.inflate(R.layout.layout_signature_default, null);
//                    view = lInflater.inflate(R.layout.control_signature_six, null);
                }
            } else {
//                view = lInflater.inflate(R.layout.control_signature, null);
                view = lInflater.inflate(R.layout.layout_signature_default, null);
            }
            view.setTag(SignatureTAG);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            ll_tap_text = view.findViewById(R.id.ll_tap_text);
            ll_signature = view.findViewById(R.id.ll_signature);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            mSignaturePad = view.findViewById(R.id.signature_pad);
            mSignaturePad.setTag(controlObject.getControlName());
            tv_clearSignature = view.findViewById(R.id.tv_clearSignature);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            ct_alertSignature = view.findViewById(R.id.ct_alertTypeText);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            layout_upload_file = view.findViewById(R.id.layout_camera_or_gallery);
            if(layout_upload_file != null) {
                layout_upload_file.setTag(controlObject.getControlName());
                layout_upload_file.setTag(R.string.signature_view_click,context.getString(R.string.upload_my_signature));
            }
            ll_gallery = view.findViewById(R.id.ll_gallery);
            layout_camera_or_gallery_main = view.findViewById(R.id.layout_camera_or_gallery_main);
            iv_SelectedImage = view.findViewById(R.id.iv_SelectedImage);
            divider = view.findViewById(R.id.divider);
            ct_showText = view.findViewById(R.id.ct_showText);

            tv_clearSignature.setTag(controlObject.getControlName());
            tv_clearSignature.setTag(R.string.signature_view_click,context.getString(R.string.clear));
            if(ll_tap_text != null) {
                ll_tap_text.setTag(controlObject.getControlType());
            }
            mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
                @Override
                public void onStartSigning() {
                    //Event triggered when the pad is touched
                    if(ll_tap_text != null && ll_tap_text.isEnabled()){
                        ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rectangle_active));
                        if(layout_upload_file != null) {
                            layout_upload_file.setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_default));
                        }
                    }
                }

                @Override
                public void onSigned() {
                    //Event triggered when the pad is signed
                    Log.d(TAG, "onSigned: " + "onSigned");
                    addJpgSignatureToGallery(mSignaturePad.getSignatureBitmap());

                    ct_alertSignature.setVisibility(View.GONE);
                    ct_alertSignature.setText("");
                }

                @Override
                public void onClear() {
                    //Event triggered when the pad is cleared
                    if(ll_tap_text != null && ll_tap_text.isEnabled()){
                        ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rectangle_default));
                    }
                }
            });
//            tv_clearSignature.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mSignaturePad.clearCanvas();
//                    controlObject.setText(null);
//
//                }
//            });


            setControlValues();

            linearLayout.addView(view);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public void Clear() {
        mSignaturePad.clear();
        controlObject.setText(null);
//        setSignatureWidthAndHeight(1);
    }

    private void setSignatureWidthAndHeight(int flag) {
        LinearLayout.LayoutParams layoutParams = null;
        if(flag==0){
            layoutParams = new LinearLayout.LayoutParams(1,1);
        }else if(controlObject.isEnableUploadSignature()){
            layoutParams = new LinearLayout.LayoutParams(0
                    ,ImproveHelper.pxToDPControlUI(120),0.8f);
        }else{
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                ,ImproveHelper.pxToDPControlUI(120),1f);
    }
        ll_tap_text.setLayoutParams(layoutParams);


    }
    private void setSignatureWidthAndHeightCopy(int flag) {
        LinearLayout.LayoutParams layoutParams = null;
        if(flag==0){
            layoutParams = new LinearLayout.LayoutParams(1,1);
        }else{
            layoutParams = new LinearLayout.LayoutParams(0
                    ,ImproveHelper.pxToDPControlUI(120),0.8f);
        }
        ll_tap_text.setLayoutParams(layoutParams);


    }

    private void setControlValues() {
        try {
            //controlObject.setNullAllowed(false);

            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            if (controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                tv_hint.setText(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }
            if (controlObject.isNullAllowed()) {
                iv_mandatory.setVisibility(View.VISIBLE);
            } else {
                iv_mandatory.setVisibility(View.GONE);
            }
            if (controlObject.isInvisible()) {
                linearLayout.setVisibility(View.GONE);
            } else {
                linearLayout.setVisibility(View.VISIBLE);
            }
            if(controlObject.isHideDisplayName()){
                ll_displayName.setVisibility(View.GONE);
            }
            if (controlObject.isEnableUploadSignature()) {
                if(layout_camera_or_gallery_main != null) {
                    layout_camera_or_gallery_main.setVisibility(View.VISIBLE);
                }
                if(layout_upload_file != null) {
                    layout_upload_file.setVisibility(View.VISIBLE);
                }

            } else {
                if(layout_upload_file != null) {
                    layout_upload_file.setVisibility(View.GONE);
                }
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, improveHelper.dpToPx(120));
//                if(ll_tap_text != null) {
//                    ll_tap_text.setLayoutParams(layoutParams);
//                }
//                if(layout_camera_or_gallery_main != null) {
//                    layout_camera_or_gallery_main.setLayoutParams(layoutParams);
//                }
//                LinearLayout.LayoutParams layoutParamsClear = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                tv_clearSignature.setLayoutParams(layoutParamsClear);
            }

            if (controlObject.isEnableSignatureOnScreen()) {
                tv_clearSignature.setVisibility(View.VISIBLE);
            } else {
                mSignaturePad.setVisibility(View.VISIBLE);
                ll_gallery.setVisibility(View.GONE);
               /* LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ImproveHelper.pxToDp(1), ImproveHelper.pxToDp(1));
                if(ll_tap_text != null) {
                    ll_tap_text.setLayoutParams(layoutParams);
                }*/
                /*tv_clearSignature.setVisibility(View.GONE);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0f
                );
                layout_upload_file.setLayoutParams(param);*/
            }


            if (controlObject.getControlValue() != null && !controlObject.getControlValue().isEmpty()) {
                Log.d(TAG, "setControlValuesSignature: " + controlObject.getControlValue());

                mSignaturePad.setVisibility(View.GONE);
                ll_gallery.setVisibility(View.VISIBLE);
                Glide.with(context).load(controlObject.getControlValue())
                        .placeholder(R.drawable.icons_image_update)
                        .dontAnimate().into(iv_SelectedImage);
                controlObject.setText(controlObject.getControlValue());
            }

            if(controlObject.isDisable()){
                improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), !controlObject.isDisable(), ll_tap_text, view);
            }

            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    public void setSelectedSignature(Uri selectedSignature) {
        try {
            if(layout_camera_or_gallery_main != null) {
                layout_camera_or_gallery_main.setVisibility(View.VISIBLE);
            }

            if(ll_tap_text != null) {
                ll_tap_text.setVisibility(View.GONE);
//                setSignatureWidthAndHeight(0);
            }
//            mSignaturePad.setVisibility(View.VISIBLE);
            if(ll_gallery != null) {
                ll_gallery.setVisibility(View.VISIBLE);

                iv_SelectedImage.setImageURI(selectedSignature);
                getControlRealPath().setTag(getRealPathFromURI(selectedSignature));
                controlObject.setText(getControlRealPath().getTag().toString());
//                controlObject.setText(getControlRealPath().getTag().toString());
            }

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setSelectedSignature", e);
        }
    }

    private void uriToBitmap(Uri selectedFileUri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    context.getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);

            parcelFileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isSignatureView() {
        return mSignaturePad.isEmpty() ;
    }

    public boolean isSignatureUploaded() {
        if(controlObject.isEnableUploadSignature() && controlObject.getText()!=null && !controlObject.getText().isEmpty()){
            return true;
        }
       return false;
    }
/*
    public boolean isSignatureUploaded() {
        if(controlObject.isEnableUploadSignature() && controlObject.getSignaturePath()!=null){
            return true;
        }else if(getControlRealPath()!=null
        ){
            return true;
        }
       return false;
    }
*/

    public Bitmap isSignatureBitMap() {

        return mSignaturePad.getSignatureBitmap();
    }

    public CustomTextView setAlertSignature() {

        return ct_alertSignature;
    }

    public File getAlbumStorageDir(String albumName) {
        File file = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath(), albumName);
        try {
            // Get the directory for the user's public pictures directory.
            Log.e("SignaturePad", file.toString());
            if (!file.mkdirs()) {
                Log.e("SignaturePad", "Directory not created");
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getAlbumStorageDir", e);
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        try {
            Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bitmap, 0, 0, null);
            OutputStream stream = new FileOutputStream(photo);
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
            stream.close();
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "saveBitmapToJPG", e);
        }
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
//            File photo = new File(getAlbumStorageDir("/ImproveUser/Signature"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            File photo = getOutputMediaFile();
            Log.d(TAG, "addJpgSignatureToGallery: " + photo);
            tv_displayName.setTag(photo);
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true;
            controlObject.setText(tv_displayName.getTag().toString());
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addJpgSignatureToGallery", e);
        }
        return result;
    }
    private File getOutputMediaFile() {

//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES), "Signature");
        File mediaStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("Signature", context.getString(nk.bluefrog.library.R.string.failed_to_create_directory));
                return null;
            }
        }
//        File mediaFile = new File(mediaStorageDir.getPath() + File.separator , "Signature_"+System.currentTimeMillis()+".jpg");
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator , System.currentTimeMillis()+"_Signature_"+controlObject.getControlName()+".jpg");

        return mediaFile;
    }

    private void scanMediaFile(File photo) {
        try {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(photo);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "scanMediaFile", e);
        }
    }

    public boolean addSvgSignatureToGallery(String signatureSvg) {
        boolean result = false;
        try {
            File svgFile = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.svg", System.currentTimeMillis()));
            OutputStream stream = new FileOutputStream(svgFile);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(signatureSvg);
            writer.close();
            stream.flush();
            stream.close();
            scanMediaFile(svgFile);
            result = true;
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "scanMediaFile", e);
        }
        return result;
    }


    public CustomTextView getControlRealPath() {
        return tv_displayName;
    }

    public SignaturePad getSignaturePad() {

        return mSignaturePad;
    }

    public LinearLayout getLayoutUpload() {
        return layout_upload_file;
    }

    public CustomTextView getTv_clearSignature() {
        return tv_clearSignature;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public void getClearSignature() {
        try {
            if (ll_gallery.getVisibility()==View.GONE) {
                mSignaturePad.clear();
            } else {
                iv_SelectedImage.setImageURI(null);
                if(ll_tap_text != null) {
                    ll_tap_text.setVisibility(View.VISIBLE);
                }
                mSignaturePad.setVisibility(View.VISIBLE);
                ll_gallery.setVisibility(View.GONE);
            }
            if(controlObject.isEnableUploadSignature()){
                layout_camera_or_gallery_main.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getClearSignature", e);
        }
    }


    public boolean isSignatureImage() {
        boolean returnFlag = false;
        returnFlag = iv_SelectedImage.getDrawable() != null;
        return returnFlag;
    }


    public void setSignatureForEdit1(String value) {


        try {
            if (value != null && !value.isEmpty()) {
                if(layout_upload_file != null) {
                    layout_upload_file.setVisibility(View.GONE);
                }
                if(ll_tap_text != null) {
                    ll_tap_text.setVisibility(View.GONE);
                }
                mSignaturePad.setVisibility(View.GONE);
                ll_gallery.setVisibility(View.VISIBLE);

                Glide.with(context).load(value)
                        .placeholder(R.drawable.icons_image_update)
                        .dontAnimate().into(iv_SelectedImage);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setSignatureForEditCopy(String value) {


        try {
            if (value != null && !value.isEmpty()) {
                if(layout_camera_or_gallery_main != null) {
                    layout_camera_or_gallery_main.setVisibility(View.GONE);
                }
                if(ll_tap_text != null) {
                    ll_tap_text.setVisibility(View.GONE);
                }
                mSignaturePad.setVisibility(View.GONE);
                ll_gallery.setVisibility(View.VISIBLE);

                Glide.with(context).load(value)
                        .placeholder(R.drawable.icons_image_update)
                        .dontAnimate().into(iv_SelectedImage);
                controlObject.setText(value);
                controlObject.setSignaturePath(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setSignatureForEdit(String value) {


        try {
            tv_displayName.setTag(value);
            if (value != null && !value.isEmpty()) {
                if(layout_camera_or_gallery_main != null) {
                    layout_camera_or_gallery_main.setVisibility(View.GONE);
                }
                if(ll_tap_text != null) {
                    ll_tap_text.setVisibility(View.GONE);
                }
                mSignaturePad.setVisibility(View.GONE);
                ll_gallery.setVisibility(View.VISIBLE);

                Glide.with(context).load(value)
                        .placeholder(R.drawable.icons_image_update)
                        .dontAnimate().into(iv_SelectedImage);
                getControlRealPath().setTag(value);
                setPath(value);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPath() {
        return controlObject.getSignaturePath();
    }

    @Override
    public void setPath(String path) {
        controlObject.setSignaturePath(path);
        controlObject.setText(path);
    }

    @Override
    public boolean getVisibility() {
        controlObject.setInvisible(linearLayout.getVisibility() != View.VISIBLE);
        return controlObject.isInvisible();
    }

    @Override
    public void setVisibility(boolean visibility) {

        if (visibility) {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        } else {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {
        controlObject.setDisable(!enabled);
//        setViewDisableOrEnableDefault(context,view, enabled);
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), !controlObject.isDisable(), ll_tap_text, view);
    }

    @Override
    public void clean() {

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

    /*General*/
    /*displayName,hint*/

    public String getDisplayName() {
        return controlObject.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        tv_displayName.setText(displayName);
        controlObject.setDisplayName(displayName);
    }

    public String getHint() {
        return controlObject.getHint();
    }

    public void setHint(String hint) {
        if (hint != null && !hint.isEmpty()) {
            tv_hint.setVisibility(View.VISIBLE);
            tv_hint.setText(hint);
        } else {
            tv_hint.setVisibility(View.GONE);
        }
        controlObject.setHint(hint);
    }

    /*Validations*/
    /*mandatory,mandatoryErrorMessagee*/
    public boolean isMandatory() {
        return controlObject.isNullAllowed();
    }

    public void setMandatory(boolean mandatory) {
        iv_mandatory.setVisibility(mandatory == true ? View.VISIBLE : View.GONE);
        controlObject.setNullAllowed(mandatory);
    }

    public String getMandatoryErrorMessage() {
        return controlObject.getMandatoryFieldError();
    }

    public void setMandatoryErrorMessage(String mandatoryErrorMessage) {
        controlObject.setMandatoryFieldError(mandatoryErrorMessage);
    }

    /*Options*/
    /*hideDisplayName,enableUploadSignature,enableSignatureOnScreen
    invisible/visibility
     */
    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        if (hideDisplayName) {
            ll_displayName.setVisibility(View.GONE);
        }else{
            ll_displayName.setVisibility(View.VISIBLE);
        }
    }

    public boolean isEnableUploadSignature() {
        return controlObject.isEnableUploadSignature();
    }

    public void setEnableUploadSignature(boolean enableUploadSignature) {
        controlObject.setEnableUploadSignature(enableUploadSignature);
        if (enableUploadSignature) {
            if(layout_camera_or_gallery_main != null && layout_upload_file != null) {
                layout_camera_or_gallery_main.setVisibility(View.VISIBLE);
                layout_upload_file.setVisibility(View.VISIBLE);
            }
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, improveHelper.dpToPx(120));
////            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(improveHelper.dpToPx(300), improveHelper.dpToPx(80));
//            if(ll_tap_text != null) {
//                ll_tap_text.setLayoutParams(layoutParams);
//            }
//            if(layout_camera_or_gallery_main != null) {
//                LinearLayout.LayoutParams layoutParamss = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, improveHelper.dpToPx(120));
//                layout_camera_or_gallery_main.setLayoutParams(layoutParamss);
//            }
//            LinearLayout.LayoutParams layoutParamsClear = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
////            LinearLayout.LayoutParams layoutParamsClear = new LinearLayout.LayoutParams(improveHelper.dpToPx(300), ViewGroup.LayoutParams.WRAP_CONTENT);
//            tv_clearSignature.setLayoutParams(layoutParamsClear);
        } else {
            if(layout_camera_or_gallery_main != null) {
                layout_camera_or_gallery_main.setVisibility(View.VISIBLE);
            }
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, improveHelper.dpToPx(80));
//            if(ll_tap_text != null) {
//                ll_tap_text.setLayoutParams(layoutParams);
//            }
//            if(layout_camera_or_gallery_main != null) {
//                layout_camera_or_gallery_main.setLayoutParams(layoutParams);
//            }
//            LinearLayout.LayoutParams layoutParamsClear = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            tv_clearSignature.setLayoutParams(layoutParamsClear);
//            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
//                    0,
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    1.0f
//            );
//            tv_clearSignature.setLayoutParams(param);
        }

    }

    public boolean isEnableSignatureOnScreen() {
        return controlObject.isEnableSignatureOnScreen();
    }

    public void setEnableSignatureOnScreen(boolean enableSignatureOnScreen) {
        if (enableSignatureOnScreen) {
            tv_clearSignature.setVisibility(View.VISIBLE);
        } else {
            iv_SelectedImage.setImageURI(null);
            mSignaturePad.setVisibility(View.GONE);
            ll_gallery.setVisibility(View.GONE);
           /* tv_clearSignature.setVisibility(View.GONE);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            );
            layout_upload_file.setLayoutParams(param);*/
        }
        controlObject.setEnableSignatureOnScreen(enableSignatureOnScreen);
    }

    public LinearLayout getLl_tap_text() { // linear for taptext

        return ll_tap_text;
    }
    public LinearLayout getLl_main_inside() {

        return ll_main_inside;
    }
    public LinearLayout getLl_signature() {

        return ll_signature;
    }
    public LinearLayout getLl_control_ui() {

        return ll_control_ui;
    }
    public SignaturePad getmSignaturePad() {

        return mSignaturePad;
    }


    public ControlObject getControlObject() {
        return controlObject;
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        try {
            if (context.getContentResolver() != null) {
                Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    path = cursor.getString(idx);
                    cursor.close();
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getRealPathFromURI", e);
        }
        return path;
    }

    public LinearLayout getLl_gallery(){
        return ll_gallery;
    }
    @Override
    public void showMessageBelowControl(String msg) {
        if(msg != null && !msg.isEmpty()) {
            ct_showText.setVisibility(View.VISIBLE);
            ct_showText.setText(msg);
        }else{
            ct_showText.setVisibility(View.GONE);
        }
    }

}
