package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImageCaptureActivity.IMAGE_RESULT_CODE;
import static com.bhargo.user.utils.ImageCaptureActivity.SF_IMAGE_RESULT_CODE;
import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bhargo.user.BuildConfig;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.PathVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.GPSActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import nk.bluefrog.library.camera.CameraActivity;

public class Camera implements PathVariables, UIVariables {


    public static final int REQUEST_GALLERY_CONTROL_CODE = 553;
    public static final int SF_REQUEST_GALLERY_CONTROL_CODE = 519;
    public static final String saveLocation = "ImproveCameraImages";
    private static final String TAG = "CameraView";
    private static int orientation;
    private static int orientationDegress;
    private final int iCameraTAG = 0;
    public byte[] camByteArray;
    private final ControlObject controlObject;
    public String currentPhotoPath_Main;
    Activity context;
    LinearLayout linearLayout;
    Double latitude = 0.0, longitude = 0.0;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    View ifView;
    ImproveHelper improveHelper;
    SessionManager sessionManager;
    private CircleImageView iv_CapturedImage;
    private boolean isCamera = true;
    private CustomTextView tv_displayName, tv_hint, ct_alertTypeCamera, tv_file_name,ct_showText;
    private ImageView iv_mandatory, iv_delete_image;
    private Bitmap bitmapSetValues;
    private LinearLayout ll_tap_text;
    private LinearLayout ll_label, ll_displayName, layout_camera_or_gallery, layout_new_camera_default, ll_control_ui, layout_control, ll_main_inside, llDisplayRequired;
    private int position;
    private Intent intent;


    public Camera(Activity context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        improveHelper = new ImproveHelper(context);
        sessionManager = new SessionManager(context);

        initView();

    }

    public static Bitmap markText(Bitmap src, String watermark, Point location, int color, int alpha, int size, boolean underline) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());

        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 10, 10, null);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAlpha(alpha);
        paint.setTextSize(size);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setUnderlineText(underline);
        canvas.drawText(watermark, location.x, location.y, paint);

        return result;
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public void setBitmapFromSource(Bitmap bitmap) {
        this.bitmapSetValues = bitmap;
    }

    private Bitmap writeTextOnImage(Bitmap bitmapSetValues, String text) {
        Bitmap bm = bitmapSetValues;
        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(150);
        Rect textRect = new Rect();
        paint.getTextBounds(text, 0, text.length(), textRect);
        Canvas canvas = new Canvas(bm);

        paint.getTextBounds(text, 0, text.length(), textRect);

        //If the text is bigger than the canvas , reduce the font size
        if (textRect.width() >= (canvas.getWidth() - 4))     //the padding on either sides is considered as 4, so as to appropriately fit in the text
            paint.setTextSize(pxToDp(7));        //Scaling needs to be used for different dpi's

        //Calculate the positions
        int xPos = (canvas.getWidth() / 2) - 2;     //-2 is for regulating the x position offset

        //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));

        canvas.drawText(text, xPos, yPos, paint);

        return bm;

    }

    private Bitmap setTextToImage(Context gContext, Bitmap bitmap, String gText) {
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;


        Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.rgb(61, 61, 61));
        // text size in pixels
        paint.setTextSize((int) (14 * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 2;

        canvas.drawText(gText, x, y, paint);

        return bitmap;
    }

    public void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            addCameraStrip();
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getCameraView() {
        return linearLayout;
    }

    public void addCameraStrip() {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        final View ifView = ifLinflater.inflate(R.layout.control_camera, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                    ifView = linflater.inflate(R.layout.layout_camera_six, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {

                    ifView = linflater.inflate(R.layout.layout_camera_seven, null);
                } else {

                    ifView = linflater.inflate(R.layout.layout_camera_default, null);
                }
            } else {

//                ifView = linflater.inflate(R.layout.control_camera, null);
                ifView = linflater.inflate(R.layout.layout_camera_default, null);
            }
            if (ifView == null) {
//                ifView = linflater.inflate(R.layout.control_camera, null);
                ifView = linflater.inflate(R.layout.layout_camera_default, null);
            }
            ifView.setTag(iCameraTAG);

//            layout_new_camera_default = ifView.findViewById(R.id.layout_new_camera_default);
            layout_camera_or_gallery = ifView.findViewById(R.id.layout_camera_or_gallery);
            ll_tap_text = ifView.findViewById(R.id.ll_tap_text);
            ll_label = ifView.findViewById(R.id.ll_label);
            ll_main_inside = ifView.findViewById(R.id.ll_main_inside);
            ll_control_ui = ifView.findViewById(R.id.ll_control_ui);
            layout_control = ifView.findViewById(R.id.layout_control);
            llDisplayRequired = ifView.findViewById(R.id.llDisplayRequired);
            ll_main_inside.setTag(controlObject.getControlName());
            ll_tap_text.setTag(controlObject.getControlName());
            ll_control_ui.setTag(controlObject.getControlName());
            layout_control.setTag(controlObject.getControlName());
            llDisplayRequired.setTag(controlObject.getControlName());
            ll_label.setTag(controlObject.getControlName());
            ll_displayName = ifView.findViewById(R.id.ll_displayName);
            layout_camera_or_gallery.setTag(controlObject.getControlName());
            ll_displayName.setTag(controlObject.getControlName());
            tv_displayName = ifView.findViewById(R.id.tv_displayName);
            ct_alertTypeCamera = ifView.findViewById(R.id.ct_alertTypeText);
            tv_hint = ifView.findViewById(R.id.tv_hint);
            iv_mandatory = ifView.findViewById(R.id.iv_mandatory);
            iv_CapturedImage = ifView.findViewById(R.id.iv_Image);
            iv_CapturedImage.setTag(controlObject.getControlName());
//            tv_reTake = ifView.findViewById(R.id.tv_reTake);
            tv_file_name = ifView.findViewById(R.id.tv_file_name);
            tv_file_name.setTag(controlObject.getControlName());
            iv_delete_image = ifView.findViewById(R.id.iv_delete_image);
            ct_showText = ifView.findViewById(R.id.ct_showText);

//            tv_camera.setTag(controlObject.getControlName());
//            tv_gallery.setTag(controlObject.getControlName());
            layout_camera_or_gallery.setTag(controlObject.getControlName());
            setTvTapText(View.VISIBLE);

            if (controlObject.getDisplayNameAlignment() == null) {
//                layout_new_camera_default.setVisibility(View.VISIBLE);
                layout_control.setVisibility(View.VISIBLE);
            }
            iv_delete_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_delete_image.setVisibility(View.GONE);
                    iv_CapturedImage.setImageBitmap(null);
                    improveHelper.controlFocusBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), false, llDisplayRequired, ifView);
//                    ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.rectangle_border_camera_default));
                    layout_camera_or_gallery.setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_default));
                    tv_file_name.setText(context.getString(R.string.file_name));
                    tv_file_name.setTag("");
                    controlObject.setText(null);
                }
            });

            setControlValues();
            linearLayout.addView(ifView);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addCameraStrip", e);
        }
    }

    /*public CustomTextView getReTake() {
        return tv_reTake;
    }*/

    public LinearLayout getReTake() {
        return layout_camera_or_gallery;
    }


    public boolean isImageWithGPSEnabledResults() {
        return controlObject.isEnableImageWithGps();
    }


    public void mOpenCamera(int cameraRequestCodeMain) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

//            dispatchTakePictureIntent(cameraRequestCodeMain);
        intent = new Intent(context, CameraActivity.class);
        if (controlObject.getCaptureOrientation() != null && !controlObject.getCaptureOrientation().isEmpty()) {
            if (controlObject.getCaptureOrientation().equalsIgnoreCase(context.getString(R.string.only_lanscape))) {
                intent.putExtra(CameraActivity.CAM_ORIENTATION, CameraActivity.CAM_LANDSCAPE);
            } else if (controlObject.getCaptureOrientation().equalsIgnoreCase(context.getString(R.string.only_potrait))) {
                intent.putExtra(CameraActivity.CAM_ORIENTATION, CameraActivity.CAM_PORTRATE);
            }
        }
        intent.putExtra(CameraActivity.SAVE_LOCATION, sessionManager.getOrgIdFromSession() + " IMAGES");
        context.startActivityForResult(intent, cameraRequestCodeMain);
        ct_alertTypeCamera.setVisibility(View.GONE);
        ct_alertTypeCamera.setText("");

    }


    private void dispatchTakePictureIntent(int cameraRequestCodeMain) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
//    if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
        // Create the File where the photo should go
        File photoFile = null;
        try {
            photoFile = createBhargoImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File

        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
//            Uri photoURI = FileProvider.getUriForFile(context,
//                    context.getPackageName() + ".provider",
//                    photoFile);
            Uri photoURI = FileProvider.getUriForFile(Objects.requireNonNull(context), BuildConfig.APPLICATION_ID + ".fileprovider", photoFile);
//            takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            context.startActivityForResult(takePictureIntent, cameraRequestCodeMain);
        }
//    }
    }

    public String CapturedImagePath() {
        return currentPhotoPath_Main;
    }


    private File createBhargoImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath_Main = image.getAbsolutePath();
        return image;
    }

    private void setControlValues() {
        try {
//        if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
//            if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
//                tv_tapTextType.setText(controlObject.getDisplayName());
//            }
//        }
            if (controlObject.isNullAllowed()) {
                iv_mandatory.setVisibility(View.VISIBLE);
            } else {
                iv_mandatory.setVisibility(View.GONE);
            }
            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            if (controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                tv_hint.setText(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }

            if (controlObject.isDisable()) {
                improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), !controlObject.isDisable(), llDisplayRequired, ifView);
            }


            if (controlObject.isEnableMaxUploadSize()) {
                Log.d("ControlCameraGET", controlObject.isEnableMaxUploadSize() + "," + controlObject.getMaxUploadSize()
                        + "," + controlObject.getMaxUploadError());
            }
            if (controlObject.isEnableMinUploadSize()) {
                Log.d("ControlCameraGET", controlObject.isEnableMinUploadSize() + "," + controlObject.getMinUploadSize()
                        + "," + controlObject.getMinUploadError());
            }
            if (controlObject.isCaptureFromCamera() && controlObject.isCaptureFromFile()) {


                Log.d(TAG, "setControlValuesCapture: " + "Both");
            } else if (controlObject.isCaptureFromCamera()) {


                Log.d(TAG, "setControlValuesCapture: " + "CaptureCamera");
            } else if (controlObject.isCaptureFromFile()) {


                Log.d(TAG, "setControlValuesCapture: " + "CaptureFile");
            }
            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            } else {
                ll_displayName.setVisibility(View.VISIBLE);
            }

//            if (controlObject.isEnableFilePhoto()) {
//                tv_tapTextType.setText(R.string.tap_to_camera_gallery);
//            }

            if (controlObject.isInvisible()) {
                linearLayout.setVisibility(View.GONE);
            } else {
                linearLayout.setVisibility(View.VISIBLE);
            }

            if (controlObject.getControlValue() != null && !controlObject.getControlValue().isEmpty()
                    && !controlObject.getControlValue().equalsIgnoreCase("File not found")) {

//            Glide.with(context)
//                    .load(controlObject.getControlValue().trim())
//                    .placeholder(context.getResources().getDrawable(R.drawable.icons_image))
//                    .into(iv_CapturedImage);
//            ll_tap_text.setVisibility(View.GONE);
                setLLTapText(View.GONE);
                iv_CapturedImage.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(controlObject.getControlValue())
                        .placeholder(R.drawable.icons_image)
                        .dontAnimate()
                        .into(iv_CapturedImage);
                setPath(controlObject.getControlValue());
                Log.d(TAG, "CameraSetControlValues: " + controlObject.getControlValue());
                getReTake().setVisibility(View.VISIBLE);

            }

        /*if(AppConstants.EDIT_COLUMNS!= null && !AppConstants.EDIT_COLUMNS.contains(controlObject.getControlName())){
            getReTake().setVisibility(View.GONE);
        }*/
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
//                    setDisplaySettings(context, tv_tapTextType, controlObject);
                } else {
                    setDisplaySettings(context, tv_displayName, controlObject);
                    setTextSize(controlObject.getTextSize());
                    setTextColor(controlObject.getTextHexColor());
                    setTextStyle(controlObject.getTextStyle());
                }
            } else {
                setDisplaySettings(context, tv_displayName, controlObject);
                setTextSize(controlObject.getTextSize());
                setTextColor(controlObject.getTextHexColor());
                setTextStyle(controlObject.getTextStyle());
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    public CustomTextView setAlertCamera() {

        return ct_alertTypeCamera;
    }

    public Bitmap getBitmapSetValues() {
        return bitmapSetValues;
    }

    public ImageView getLLImageView() {
        return iv_CapturedImage;
    }

    public void setCameraByteArray(byte[] byteArray) {
        camByteArray = byteArray;
    }

    public byte[] getCamByteArray() {
        return camByteArray;
    }

    public CustomTextView getControlRealPath() {
        return tv_displayName;
    }

    public CustomTextView getFileNameTextView() {
        return tv_file_name;
    }

    public CustomTextView getGPSOfImage() {
        return tv_hint;
    }

    /**
     * Putting in place a listener so we can get the sensor data only when something changes.
     */
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                if (event.values[0] < 4 && event.values[0] > -4) {
                    if (event.values[1] > 0 && orientation != ExifInterface.ORIENTATION_ROTATE_90) {
                        // UP
                        orientation = ExifInterface.ORIENTATION_ROTATE_90;
                        orientationDegress = 90;
                    } else if (event.values[1] < 0 && orientation != ExifInterface.ORIENTATION_ROTATE_270) {
                        // UP SIDE DOWN
                        orientation = ExifInterface.ORIENTATION_ROTATE_270;
                        orientationDegress = 270;
                    }
                } else if (event.values[1] < 4 && event.values[1] > -4) {
                    if (event.values[0] > 0 && orientation != ExifInterface.ORIENTATION_NORMAL) {
                        // LEFT
                        orientation = ExifInterface.ORIENTATION_NORMAL;
                        orientationDegress = 0;
                    } else if (event.values[0] < 0 && orientation != ExifInterface.ORIENTATION_ROTATE_180) {
                        // RIGHT
                        orientation = ExifInterface.ORIENTATION_ROTATE_180;
                        orientationDegress = 180;
                    }
                }
            }

        }
    }

    public void setImage(Bitmap bitmap) {
        try {
            if (getControlRealPath().getTag() != null) {
            int filesize = ImproveHelper.getFileSize(getControlRealPath().getTag().toString());
            if (controlObject.isEnableMaxUploadSize() && (filesize > (Integer.parseInt(controlObject.getMaxUploadSize())) * 1000)) {
//                Toast.makeText(context, controlObject.getMaxUploadError(), Toast.LENGTH_SHORT).show();
                ImproveHelper.showToast(context, controlObject.getMaxUploadError());
                if (iv_delete_image != null) {
                    iv_delete_image.setVisibility(View.GONE);
                }
            }
            else if (controlObject.isEnableMinUploadSize() && (filesize < (Integer.parseInt(controlObject.getMinUploadSize())) * 1000)) {
//                Toast.makeText(context, controlObject.getMinUploadError(), Toast.LENGTH_SHORT).show();
                ImproveHelper.showToast(context, controlObject.getMinUploadError());
                if (iv_delete_image != null) {
                    iv_delete_image.setVisibility(View.GONE);
                }
            }else{
                this.bitmapSetValues = bitmap;
                iv_delete_image.setVisibility(View.VISIBLE);
                improveHelper.controlFocusBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), true, llDisplayRequired, ifView);
                if(getControlRealPath().getTag() != null &&  ! getControlRealPath().getTag().toString().isEmpty()) {
                    controlObject.setText(getControlRealPath().getTag().toString());
                }
                iv_CapturedImage.setImageBitmap(bitmapSetValues);
                if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                        if (SubformFlag) {
                            AppConstants.SF_Container_position = SubformPos;
                            AppConstants.Current_ClickorChangeTagName = SubformName;

                        }
                        AppConstants.GlobalObjects.setCurrent_GPS("");
//                    ((MainActivity) context).ChangeEvent(ll_tap_text);
                        ((MainActivity) context).ChangeEvent(layout_camera_or_gallery);
                    }
                }
            }
        }else {
                this.bitmapSetValues = bitmap;
                iv_delete_image.setVisibility(View.VISIBLE);
                improveHelper.controlFocusBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), true, llDisplayRequired, ifView);
                if(getControlRealPath().getTag() != null &&  ! getControlRealPath().getTag().toString().isEmpty()) {
                    controlObject.setText(getControlRealPath().getTag().toString());
                }
                iv_CapturedImage.setImageBitmap(bitmapSetValues);
                if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                        if (SubformFlag) {
                            AppConstants.SF_Container_position = SubformPos;
                            AppConstants.Current_ClickorChangeTagName = SubformName;

                        }
                        AppConstants.GlobalObjects.setCurrent_GPS("");
//                    ((MainActivity) context).ChangeEvent(ll_tap_text);
                        ((MainActivity) context).ChangeEvent(layout_camera_or_gallery);
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setImage", e);
        }
    }

    public int pxToDP(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics());
    }

    public void setImageWithGPSValues(Double latitude, Double longitude) {
        try {
            this.latitude = latitude;
            this.longitude = longitude;
            setImageGPS(latitude + "$" + longitude);
            if (latitude != 0.0 && longitude != 0.0) {
                tv_hint.setTag(latitude + "$" + longitude);
                if (bitmapSetValues != null) {
                    Bitmap setBitmap = drawTextToBitmap(context, bitmapSetValues, latitude + "," + longitude);
                    Bitmap overRidedImage = overRideImage(getControlRealPath().getTag().toString(), setBitmap);
                    iv_CapturedImage.setImageBitmap(overRidedImage);
                }
            }
            controlObject.setText(getControlRealPath().getTag().toString());
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setImageWithGPSValues", e);
        }
    }

    public Bitmap drawTextToBitmap(Context gContext,
                                   Bitmap bitmap,
                                   String gText) {
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;

        Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // text color - #3D3D3D
        paint.setColor(Color.WHITE);
        // text size in pixels
        paint.setTextSize((int) (15 * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // draw text to the Canvas center
        Rect bounds = new Rect();

        int noOfLines = 0;
        for (String line : gText.split("\n")) {
            noOfLines++;
        }

        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = 20;
        int y = (bitmap.getHeight() - bounds.height() * noOfLines);

        Paint mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(context, R.color.transparentBlack));
        int left = 0;
        int top = (bitmap.getHeight() - bounds.height() * (noOfLines + 1));
        int right = bitmap.getWidth();
        int bottom = bitmap.getHeight();
        canvas.drawRect(left, top, right, bottom, mPaint);

        for (String line : gText.split("\n")) {
            canvas.drawText(line, x, y, paint);
            y += paint.descent() - paint.ascent();
        }

        return bitmap;
    }

    public void setImageWithGps(int reCode) {
        try {
            Log.d(TAG, "setImageWithGpsCheck: " + reCode);
            context.startActivityForResult(new Intent(context, GPSActivity.class), reCode);
//            setImageWithGPSValues(17.8243,83.3564); // for Testing static Gps madhuravada Pm Palem,
            setImageWithGPSValues(latitude, longitude); // for Testing static Gps madhuravada Pm Palem,
        } catch (Exception e) {
            Log.d(TAG, "setImageWithGpsCheck: " + e);
            ImproveHelper.improveException(context, TAG, "setImageWithGps", e);
        }
    }

    public void setImageBitMapBNull() {
        iv_CapturedImage.setVisibility(View.GONE);
        setImageGPS(latitude + "$" + longitude);
    }

    public LinearLayout getLLTapTextView() {

        return layout_camera_or_gallery;
    }

    public void getCameraClickView(int cameraRequestCode, View activityView) {
        try {
            if (SubformFlag) {
                AppConstants.SF_Container_position = SubformPos;
                AppConstants.Current_ClickorChangeTagName = SubformName;
            }

            if (controlObject.isCaptureFromCamera() && controlObject.isCaptureFromFile()) {
                setLLTapText(View.GONE);
                LinearLayout bottom_sheet = activityView.findViewById(R.id.bottom_sheet);
                BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
                LinearLayout bs_ll_camera = activityView.findViewById(R.id.bs_ll_camera);
                LinearLayout bs_ll_gallery = activityView.findViewById(R.id.bs_ll_gallery);
                CustomTextView ct_Gallery = activityView.findViewById(R.id.ct_Gallery);
                CustomTextView ct_Camera = activityView.findViewById(R.id.ct_Camera);
                CustomTextView ct_Cancel = activityView.findViewById(R.id.ct_Cancel);
//                if (SubformFlag) {
////                    context.startActivityForResult(new Intent(context, ImageCaptureActivity.class), SF_IMAGE_RESULT_CODE);
//                } else {

                ImproveHelper.showHideBottomSheet(sheetBehavior);
                bs_ll_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SubformFlag) {
                            mOpenCamera(SF_IMAGE_RESULT_CODE);
                        } else {
                            mOpenCamera(IMAGE_RESULT_CODE);
                        }
                        ImproveHelper.showHideBottomSheet(sheetBehavior);
                    }
                });

                bs_ll_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentG = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        if (SubformFlag) {
                            AppConstants.SF_Container_position = SubformPos;
                            AppConstants.Current_ClickorChangeTagName = SubformName;
                            context.startActivityForResult(intentG, SF_REQUEST_GALLERY_CONTROL_CODE);
                        } else {
                            context.startActivityForResult(intentG, REQUEST_GALLERY_CONTROL_CODE);
                        }
                        ImproveHelper.showHideBottomSheet(sheetBehavior);
                    }
                });
                ct_Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImproveHelper.showHideBottomSheet(sheetBehavior);
                    }
                });


//                    context.startActivityForResult(new Intent(context, ImageCaptureActivity.class), IMAGE_RESULT_CODE);
//                }
                isCamera = true;
            } else if (controlObject.isCaptureFromFile()) {
                Intent intentG = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (SubformFlag) {
                    AppConstants.SF_Container_position = SubformPos;
                    AppConstants.Current_ClickorChangeTagName = SubformName;
                    context.startActivityForResult(intentG, SF_REQUEST_GALLERY_CONTROL_CODE);
                } else {
                    context.startActivityForResult(intentG, REQUEST_GALLERY_CONTROL_CODE);
                }
                if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                        if (SubformFlag) {
                            AppConstants.SF_Container_position = SubformPos;
                            AppConstants.Current_ClickorChangeTagName = SubformName;

                        }
                        AppConstants.GlobalObjects.setCurrent_GPS("");
                    }
                }

                ct_alertTypeCamera.setVisibility(View.GONE);
                ct_alertTypeCamera.setText("");

            } else {

                mOpenCamera(cameraRequestCode);
                setLLTapText(View.GONE);
                isCamera = false;

            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getCameraClickView", e);
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setUploadedImageOld(ImageView iv_capturedImage, Bitmap thumbnail, String filePath, Uri uri, int flag) {
        try {
            int filesize = ImproveHelper.getFileSize(filePath);

            if (controlObject.isEnableMaxUploadSize() && (filesize > (Integer.parseInt(controlObject.getMaxUploadSize())) * 1000)) {
                Toast.makeText(context, controlObject.getMaxUploadError(), Toast.LENGTH_SHORT).show();
            } else if (controlObject.isEnableMinUploadSize() && (filesize < (Integer.parseInt(controlObject.getMinUploadSize())) * 1000)) {
                Toast.makeText(context, controlObject.getMinUploadError(), Toast.LENGTH_SHORT).show();
            } else {
                if (flag == 0) {
                    iv_capturedImage.setImageBitmap(thumbnail);
                } else {


//                    ll_tap_text.setVisibility(View.GONE);
                    if (controlObject.isCaptureFromCamera() && controlObject.isCaptureFromFile()) {
                        Log.d(TAG, "setControlValuesCapture: " + "Both");
                    } else if (controlObject.isCaptureFromCamera()) {

                        Log.d(TAG, "setControlValuesCapture: " + "CaptureCamera");
                    } else if (controlObject.isCaptureFromFile()) {

                    }
                    iv_CapturedImage.setImageURI(uri);


                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setUploadedImage", e);
        }
//        iv_capturedImage.setImageBitmap(thumbnail);
    }

    public void setUploadedImage(ImageView iv_capturedImage, Bitmap thumbnail, String filePath, Uri uri, int flag) {
        try {
            int filesize = ImproveHelper.getFileSize(filePath);
            iv_delete_image.setVisibility(View.VISIBLE);
            if (controlObject.isEnableMaxUploadSize() && (filesize > (Integer.parseInt(controlObject.getMaxUploadSize())) * 1000)) {
//                Toast.makeText(context, controlObject.getMaxUploadError(), Toast.LENGTH_SHORT).show();
                ImproveHelper.showToast(context, controlObject.getMaxUploadError());
                if (iv_delete_image != null) {
                    iv_delete_image.setVisibility(View.GONE);
                }
            } else if (controlObject.isEnableMinUploadSize() && (filesize < (Integer.parseInt(controlObject.getMinUploadSize())) * 1000)) {
//                Toast.makeText(context, controlObject.getMinUploadError(), Toast.LENGTH_SHORT).show();
                ImproveHelper.showToast(context, controlObject.getMinUploadError());
                if (iv_delete_image != null) {
                    iv_delete_image.setVisibility(View.GONE);
                }
            } else {
                setFileName(filePath);
                getControlRealPath().setTag(filePath);
                setPath(filePath);
                if (flag == 0) {
                    iv_capturedImage.setImageBitmap(thumbnail);
                } else {

//                    ll_camera_gallery.setVisibility(View.VISIBLE);
//                    ll_tap_text.setVisibility(View.GONE);
//                    ll_imageCaptureTypes.setVisibility(View.VISIBLE);
                    if (controlObject.isCaptureFromCamera() && controlObject.isCaptureFromFile()) {
                        Log.d(TAG, "setControlValuesCapture: " + "Both");
                    } else if (controlObject.isCaptureFromCamera()) {

                        Log.d(TAG, "setControlValuesCapture: " + "CaptureCamera");
                    } else if (controlObject.isCaptureFromFile()) {

                    }
                    Log.d(TAG, "setUploadedImageCheck: " + uri);
                    iv_CapturedImage.setImageURI(uri);
                    tv_file_name.setTag(uri);
                    controlObject.setText(getControlRealPath().getTag().toString());

                }
            }
        } catch (Exception e) {
            Log.d(TAG, "setUploadedImageCheck: " + "NO");
            ImproveHelper.improveException(context, TAG, "setUploadedImage", e);
        }
    }

    public boolean isImageViewNull() {
        boolean return_flag = false;
        try {
            return_flag = iv_CapturedImage.getDrawable() == null;
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "isImageViewNull", e);
        }
        return return_flag;
    }

    public LinearLayout getTv_tapText() {
        return layout_camera_or_gallery;
    }

    public void setImageForEdit(String imageUrl) {
        try {
            if (imageUrl != null && !imageUrl.trim().isEmpty()
                    && !imageUrl.equalsIgnoreCase("File not found")) {
                String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                tv_file_name.setText(fileName);
                iv_delete_image.setVisibility(View.VISIBLE);
                improveHelper.controlFocusBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), true, llDisplayRequired, ifView);
//                ll_tap_text.setBackground(ContextCompat.getDrawable(context,R.drawable.rectangle_border_camera_active));
                layout_camera_or_gallery.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_for_image));
                iv_CapturedImage.setVisibility(View.VISIBLE);
                if (imageUrl.startsWith("http")) {
                    Glide.with(context)
                            .load(imageUrl)
                            .placeholder(R.drawable.icons_image)
                            .dontAnimate()
                            .into(iv_CapturedImage);
                } else {
                    Bitmap imageBitmap = BitmapFactory.decodeFile(imageUrl);
                    iv_CapturedImage.setImageBitmap(imageBitmap);
                }
                Log.d(TAG, "CameraSetControlValues: " + imageUrl);
                getReTake().setVisibility(View.VISIBLE);
                getControlRealPath().setTag(imageUrl);
                controlObject.setText(imageUrl);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setImageForEdit", e);
        }
    }

    public void setTvTapText(int visibility) {
        try {
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (!controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {

//                    tv_tapTextType.setVisibility(visibility);

                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setTvTapText", e);
        }
    }

    public void setLLTapText(int visibility) {
        try {
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (!controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

//                    ll_tap_text.setVisibility(visibility);

                } else {
//                    ll_tap_text.setVisibility(visibility);
                }
            } else {
//                ll_tap_text.setVisibility(visibility);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setLLTapText", e);
        }
    }

    public void showOrSetTapText(String path) {
        if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
            if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

//                tv_tapTextType.setVisibility(View.VISIBLE);
//                tv_tapTextType.setText(path);

            } else {
//                ll_tap_text.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public String getPath() {
        return controlObject.getImagePath();
    }

    @Override
    public void setPath(String path) {
        controlObject.setImagePath(path);
        tv_displayName.setTag(path);

    }


    public String getImageGPS() {
        return controlObject.getImageGPS();
    }


    public void setImageGPS(String imageGPS) {
        controlObject.setImageGPS(imageGPS);
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

        return controlObject.isDisable();
    }

    @Override
    public void setEnabled(boolean enabled) {
//        setViewDisable(ifView, !enabled);
        controlObject.setDisable(!enabled);
//        setViewDisableOrEnableDefault(context,ifView, enabled);
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), enabled, llDisplayRequired, ifView);
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
    /*mandatory,mandatoryErrorMessage,
     , enableMaxUploadSize,maxUploadSize,maxUploadError,enableMinUploadSize,minUploadSize,minUploadError*/
    public boolean isMandatory() {
        return controlObject.isNullAllowed();
    }

    public void setMandatory(boolean mandatory) {
        iv_mandatory.setVisibility(mandatory ? View.VISIBLE : View.GONE);
        controlObject.setNullAllowed(mandatory);
    }

    public String getMandatoryErrorMessage() {
        return controlObject.getMandatoryFieldError();
    }

    public void setMandatoryErrorMessage(String mandatoryErrorMessage) {
        controlObject.setMandatoryFieldError(mandatoryErrorMessage);
    }

    public boolean isEnableMaxUploadSize() {
        return controlObject.isEnableMaxUploadSize();
    }

    public void setEnableMaxUploadSize(boolean enableMaxUploadSize) {
        controlObject.setEnableMaxUploadSize(enableMaxUploadSize);
    }

    public String getMaxUploadSize() {
        return controlObject.getMaxUploadSize();
    }

    public void setMaxUploadSize(String maxUploadSize) {
        controlObject.setMaxUploadSize(maxUploadSize);
    }

    public String getMaxUploadError() {
        return controlObject.getMaxUploadError();
    }

    public void setMaxUploadError(String maxUploadError) {
        controlObject.setMaxUploadError(maxUploadError);
    }

    public boolean isEnableMinUploadSize() {
        return controlObject.isEnableMinUploadSize();
    }

    public void setEnableMinUploadSize(boolean enableMinUploadSize) {
        controlObject.setEnableMinUploadSize(enableMinUploadSize);
    }

    public String getMinUploadSize() {
        return controlObject.getMinUploadSize();
    }

    public void setMinUploadSize(String minUploadSize) {
        controlObject.setMinUploadSize(minUploadSize);
    }

    public String getMinUploadError() {
        return controlObject.getMinUploadError();
    }

    public void setMinUploadError(String minUploadError) {
        controlObject.setMinUploadError(minUploadError);
    }

    /*Options*/
    /*hideDisplayName,enableImageWithGps,zoomImageEnable,enableCapture,captureFromCamera,captureFromFile,
    captureOrientation
    invisible/visibility,disable/enabled
     */

    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        ll_displayName.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
    }

    public boolean isEnableImageWithGps() {
        return controlObject.isEnableImageWithGps();
    }

    public void setEnableImageWithGps(boolean enableImageWithGps) {
        controlObject.setEnableImageWithGps(enableImageWithGps);
//        if (SubformFlag) {
//            AppConstants.SF_Container_position = SubformPos;
//            AppConstants.Current_ClickorChangeTagName = SubformName;
//            mOpenCamera(SF_REQUEST_CAMERA_CONTROL_CODE);
//        } else {
//            mOpenCamera(REQUEST_CAMERA_CONTROL_CODE);
//        }
    }

    public boolean isZoomImageEnable() {
        return controlObject.isZoomImageEnable();
    }

    public void setZoomImageEnable(boolean zoomImageEnable) {
        controlObject.setZoomImageEnable(zoomImageEnable);
    }

    public boolean isEnableCapture() {
        return controlObject.isEnableCapture();
    }

    public void setEnableCapture(boolean enableCapture) {
        controlObject.setEnableCapture(enableCapture);
    }

    public boolean isCaptureFromCamera() {
        return controlObject.isCaptureFromCamera();
    }

    public void setCaptureFromCamera(boolean captureFromCamera) {
        controlObject.setCaptureFromCamera(captureFromCamera);
    }

    public boolean isCaptureFromFile() {
        return controlObject.isCaptureFromFile();
    }

    public void setCaptureFromFile(boolean captureFromFile) {
        controlObject.setCaptureFromFile(captureFromFile);
    }

    public String getCaptureOrientation() {
        return controlObject.getCaptureOrientation();
    }

    public void setCaptureOrientation(String captureOrientation) {
        controlObject.setCaptureOrientation(captureOrientation);
    }

    public void setFileName(String filePath) {
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        tv_file_name.setText(fileName);
        tv_file_name.setTag(filePath);
    }

    public String getFileName() {
        return tv_file_name.getText().toString();
    }

    public LinearLayout getLl_tap_text() { // linear for taptext

        return ll_tap_text;
    }

    /*ControlUI SettingsLayouts Start */
    public LinearLayout getLl_control_ui() {
        return ll_control_ui;
    }

    public CustomTextView getTv_displayName() {
        return tv_displayName;
    }

    public LinearLayout getLl_main_inside() {
        return ll_main_inside;
    }

    public LinearLayout getLayout_control() {
        return layout_control;
    }

    public LinearLayout getLlDisplayRequired() {
        return llDisplayRequired;
    }

    public ControlObject getControlObject() {
        return controlObject;
    }

    /*ControlUI SettingsLayouts End*/

    /* OverrideImage With existing TextwithImage*/
    private Bitmap overRideImage(String fileLocation, Bitmap resultBitmap) throws IOException {

        Bitmap bitmap = resultBitmap.copy(resultBitmap.getConfig(), true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileLocation);
            fos.write(byteArray);
            fos.close();
        } catch (IOException e) {
            Log.w("CameraActivity", "Cannot write to " + fileLocation, e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }

        return bitmap;
    }

public void clear(){
    bitmapSetValues = null;
    iv_delete_image.setVisibility(View.GONE);
    improveHelper.controlFocusBackground(controlObject.getControlType(),controlObject.getDisplayNameAlignment(),false,llDisplayRequired,ifView);
    iv_CapturedImage.setImageBitmap(bitmapSetValues);
    controlObject.setText(null);
    layout_camera_or_gallery.setBackground(ContextCompat.getDrawable(context,R.drawable.circular_bg_default));
    tv_file_name.setText(context.getString(R.string.file_name));
    tv_file_name.setTag("");
    this.latitude = 0.0;
    this.longitude = 0.0;
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
