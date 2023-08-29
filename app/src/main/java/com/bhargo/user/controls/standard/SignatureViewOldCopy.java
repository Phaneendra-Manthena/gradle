package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.PathVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.SignaturePad;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


public class SignatureViewOldCopy implements PathVariables, UIVariables {


    private static final String TAG = "SignatureView";
    private final int SignatureTAG = 0;
    Activity context;
    ControlObject controlObject;
    CustomTextView tv_clearSignature, tv_upload;
    SignaturePad mSignaturePad;
    View divider;
    int position;
    View view;
    ImproveHelper improveHelper;
    private LinearLayout linearLayout, ll_signatureCaptureTypes, ll_gallery, ll_signature, ll_displayName;
    private CustomTextView tv_displayName, tv_hint, ct_alertSignature;
    private ImageView iv_mandatory, iv_SelectedImage;

    public SignatureViewOldCopy(Activity context, ControlObject controlObject) {
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

                    view = lInflater.inflate(R.layout.control_signature_six, null);
                }
            } else {
                view = lInflater.inflate(R.layout.control_signature, null);
            }
            view.setTag(SignatureTAG);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            mSignaturePad = view.findViewById(R.id.signature_pad);
            mSignaturePad.setTag(controlObject.getControlName());
            tv_clearSignature = view.findViewById(R.id.tv_clearSignature);
            tv_upload = view.findViewById(R.id.tv_upload);
            tv_upload.setTag(controlObject.getControlName());
            tv_displayName = view.findViewById(R.id.tv_displayName);
            ct_alertSignature = view.findViewById(R.id.ct_alertTypeText);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            ll_signature = view.findViewById(R.id.ll_signature);
            ll_signatureCaptureTypes = view.findViewById(R.id.ll_signatureCaptureTypes);
            ll_gallery = view.findViewById(R.id.ll_gallery);
            iv_SelectedImage = view.findViewById(R.id.iv_SelectedImage);
            divider = view.findViewById(R.id.divider);

            tv_clearSignature.setTag(controlObject.getControlName());

            mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
                //
                @Override
                public void onStartSigning() {
                    //Event triggered when the pad is touched
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
                }
            });
            tv_clearSignature.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSignaturePad.clear();

                }
            });

//        tv_upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

            setControlValues();

            linearLayout.addView(view);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public void Clear() {
        mSignaturePad.clear();
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
                tv_displayName.setVisibility(View.GONE);
                tv_hint.setVisibility(View.GONE);
            }
            if (controlObject.isEnableUploadSignature()) {
                tv_upload.setVisibility(View.VISIBLE);
            } else {
                tv_upload.setVisibility(View.GONE);
//                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
//                        0,
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        1.0f
//                );
//                tv_clearSignature.setLayoutParams(param);
            }

            if (controlObject.isEnableSignatureOnScreen()) {
                tv_clearSignature.setVisibility(View.VISIBLE);
            } else {
                ll_signature.setVisibility(View.GONE);
                ll_gallery.setVisibility(View.GONE);
                tv_clearSignature.setVisibility(View.GONE);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0f
                );
                tv_upload.setLayoutParams(param);
            }


            if (controlObject.getControlValue() != null && !controlObject.getControlValue().isEmpty()) {
                Log.d(TAG, "setControlValuesSignature: " + controlObject.getControlValue());

                ll_signature.setVisibility(View.GONE);
                ll_gallery.setVisibility(View.VISIBLE);
                Glide.with(context).load(controlObject.getControlValue())
                        .placeholder(R.drawable.icons_image_update)
                        .dontAnimate().into(iv_SelectedImage);

                tv_clearSignature.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(context,R.drawable.icons_signature_24), null, null);
                tv_clearSignature.setText(context.getString(R.string.signature));

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
            ll_signature.setVisibility(View.GONE);
            ll_gallery.setVisibility(View.VISIBLE);
            iv_SelectedImage.setImageURI(selectedSignature);

            tv_clearSignature.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(context,R.drawable.icons_signature_24), null, null);
            tv_clearSignature.setText(context.getString(R.string.signature));
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setSelectedSignature", e);
        }
    }


    public boolean isSignatureView() {
        return mSignaturePad.isEmpty();
    }

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
            File photo = new File(getAlbumStorageDir("/ImproveUser/Signature"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            Log.d(TAG, "addJpgSignatureToGallery: " + photo);
            tv_displayName.setTag(photo);
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true;
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addJpgSignatureToGallery", e);
        }
        return result;
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

    public CustomTextView getTv_upload() {
        return tv_upload;
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
            if (tv_clearSignature.getText().toString().equalsIgnoreCase(context.getString(R.string.clear))) {
                mSignaturePad.clear();
            } else {
                ll_signature.setVisibility(View.VISIBLE);
                ll_gallery.setVisibility(View.GONE);
                tv_clearSignature.setText(context.getString(R.string.clear));
                tv_clearSignature.setCompoundDrawablesWithIntrinsicBounds(null,ContextCompat.getDrawable(context,R.drawable.ic_refresh_black_24dp), null, null);
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

    public LinearLayout getSignaturePadLayout() {
        return ll_signature;
    }

    public void setSignatureForEdit(String value) {
        if (value != null && !value.isEmpty()) {
            Log.d(TAG, "setControlValuesSignature: " + value);

            ll_signature.setVisibility(View.GONE);
            ll_gallery.setVisibility(View.VISIBLE);
            Glide.with(context).load(value)
                    .placeholder(R.drawable.icons_image_update)
                    .dontAnimate().into(iv_SelectedImage);

            tv_clearSignature.setCompoundDrawablesWithIntrinsicBounds(null, context.getResources().getDrawable(R.drawable.icons_signature_24), null, null);
            tv_clearSignature.setText(context.getString(R.string.signature));

        }
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public void setPath(String path) {

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
        setViewDisableOrEnableDefault(context,view, enabled);
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
            tv_upload.setVisibility(View.VISIBLE);
        } else {
            tv_upload.setVisibility(View.GONE);
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
            ll_signature.setVisibility(View.GONE);
            ll_gallery.setVisibility(View.GONE);
            tv_clearSignature.setVisibility(View.GONE);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            );
            tv_upload.setLayoutParams(param);
        }
        controlObject.setEnableSignatureOnScreen(enableSignatureOnScreen);
    }

    @Override
    public void showMessageBelowControl(String msg) {
        if(msg != null && !msg.isEmpty()) {
//            ct_showText.setVisibility(View.VISIBLE);
//            ct_showText.setText(msg);
        }else{
//            ct_showText.setVisibility(View.GONE);
        }
    }

}
