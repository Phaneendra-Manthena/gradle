package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.AppConstants.REQUEST_CAMERA_CONTROL_CODE;
import static com.bhargo.user.utils.AppConstants.SF_REQUEST_CAMERA_CONTROL_CODE;
import static com.bhargo.user.utils.ImageCaptureActivity.IMAGE_RESULT_CODE;
import static com.bhargo.user.utils.ImageCaptureActivity.SF_IMAGE_RESULT_CODE;
import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisable;

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
import com.bhargo.user.utils.ImageCaptureActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import nk.bluefrog.library.camera.CameraActivity;

public class CameraOldCopy implements PathVariables, UIVariables {


    public static final int REQUEST_GALLERY_CONTROL_CODE = 553;
    public static final int SF_REQUEST_GALLERY_CONTROL_CODE = 519;
    public static final String saveLocation = "ImproveCameraImages";
    private static final String TAG = "CameraView";
    private static int orientation;
    private static int orientationDegress;
    private final int iCameraTAG = 0;
    public byte[] camByteArray;
    public ControlObject controlObject;
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
    private ImageView iv_CapturedImage;
    private boolean isCamera = true;
    private CustomTextView tv_displayName, tv_hint, ct_alertTypeCamera, tv_camera, tv_gallery, tv_reTake;
    private ImageView iv_mandatory;
    private Bitmap bitmapSetValues;
    private LinearLayout ll_tap_text;
    private CustomTextView tv_tapTextType;
    private LinearLayout ll_camera_gallery, ll_imageCaptureTypes,ll_label;
    private int position;
    private Intent intent;


    public CameraOldCopy(Activity context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
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


        android.graphics.Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
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

                    ifView = linflater.inflate(R.layout.control_camera_two, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {

                    ifView = linflater.inflate(R.layout.control_camera_seven, null);
                } else {

                    ifView = linflater.inflate(R.layout.control_camera, null);
                }
            } else {

                ifView = linflater.inflate(R.layout.control_camera, null);
            }
            if (ifView == null) {
//                ifView = linflater.inflate(R.layout.control_camera, null);
                ifView = linflater.inflate(R.layout.layout_camera_default, null);
            }
            ifView.setTag(iCameraTAG);

            ll_tap_text = ifView.findViewById(R.id.ll_tap_text);
            ll_label = ifView.findViewById(R.id.ll_label);
            ll_tap_text.setTag(controlObject.getControlName());
            ll_label.setTag(controlObject.getControlName());
            ll_camera_gallery = ifView.findViewById(R.id.ll_camera_gallery);
            tv_tapTextType = ifView.findViewById(R.id.tv_tapTextType);
            tv_displayName = ifView.findViewById(R.id.tv_displayName);
            ll_imageCaptureTypes = ifView.findViewById(R.id.ll_imageCaptureTypes);
            tv_camera = ifView.findViewById(R.id.tv_camera);
            tv_gallery = ifView.findViewById(R.id.tv_gallery);
            ct_alertTypeCamera = ifView.findViewById(R.id.ct_alertTypeText);
            tv_hint = ifView.findViewById(R.id.tv_hint);
            iv_mandatory = ifView.findViewById(R.id.iv_mandatory);
            iv_CapturedImage = ifView.findViewById(R.id.iv_CapturedImage);
            iv_CapturedImage.setTag(controlObject.getControlName());
            tv_reTake = ifView.findViewById(R.id.tv_reTake);

            tv_camera.setTag(controlObject.getControlName());
            tv_gallery.setTag(controlObject.getControlName());
            tv_reTake.setTag(controlObject.getControlName());

            tv_tapTextType.setText(R.string.tap_to_open_camera);

//        tv_tapTextType.setVisibility(View.VISIBLE);
            setTvTapText(View.VISIBLE);

            tv_gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View View) {
                    AppConstants.Current_ClickorChangeTagName = View.getTag().toString().trim();
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
                            ((MainActivity) context).ChangeEvent(View);
                        }
                    }

                    ct_alertTypeCamera.setVisibility(android.view.View.GONE);
                    ct_alertTypeCamera.setText("");
                }
            });

            tv_camera.setOnClickListener(view -> {
                AppConstants.Current_ClickorChangeTagName = view.getTag().toString().trim();
                if (SubformFlag) {
                    AppConstants.SF_Container_position = SubformPos;
                    AppConstants.Current_ClickorChangeTagName = SubformName;
                    mOpenCamera(SF_REQUEST_CAMERA_CONTROL_CODE);
                } else {
                    mOpenCamera(REQUEST_CAMERA_CONTROL_CODE);
                }
                if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                        if (SubformFlag) {
                            AppConstants.SF_Container_position = SubformPos;
                            AppConstants.Current_ClickorChangeTagName = SubformName;

                        }
                        AppConstants.GlobalObjects.setCurrent_GPS("");
                        ((MainActivity) context).ChangeEvent(view);
                    }
                }

                ct_alertTypeCamera.setVisibility(android.view.View.GONE);
                ct_alertTypeCamera.setText("");
            });

            setControlValues();
            linearLayout.addView(ifView);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addCameraStrip", e);
        }
    }

    public CustomTextView getReTake() {
        return tv_reTake;
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
        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
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
            takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoURI);
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
            if (controlObject.isEnableMaxUploadSize()) {
                Log.d("ControlCameraGET", controlObject.isEnableMaxUploadSize() + "," + controlObject.getMaxUploadSize()
                        + "," + controlObject.getMaxUploadError());
            }
            if (controlObject.isEnableMinUploadSize()) {
                Log.d("ControlCameraGET", controlObject.isEnableMinUploadSize() + "," + controlObject.getMinUploadSize()
                        + "," + controlObject.getMinUploadError());
            }
            if (controlObject.isCaptureFromCamera() && controlObject.isCaptureFromFile()) {
                tv_tapTextType.setText(R.string.tap_to_camera_gallery);
                tv_camera.setVisibility(View.VISIBLE);
                Log.d(TAG, "setControlValuesCapture: " + "Both");
            } else if (controlObject.isCaptureFromCamera()) {
                tv_tapTextType.setText(R.string.tap_to_open_camera);
                tv_gallery.setVisibility(View.GONE);
                Log.d(TAG, "setControlValuesCapture: " + "CaptureCamera");
            } else if (controlObject.isCaptureFromFile()) {
                tv_tapTextType.setText(R.string.tap_to_open_gallery);
                tv_camera.setVisibility(View.GONE);

                Log.d(TAG, "setControlValuesCapture: " + "CaptureFile");
            }
            if (controlObject.isHideDisplayName()) {
                ll_label.setVisibility(View.GONE);
            } else {
                ll_label.setVisibility(View.VISIBLE);
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
                ll_camera_gallery.setVisibility(View.VISIBLE);
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
                    setDisplaySettings(context, tv_tapTextType, controlObject);
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
            this.bitmapSetValues = bitmap;
            iv_CapturedImage.setImageBitmap(bitmapSetValues);
            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                if (AppConstants.EventCallsFrom == 1) {
                    AppConstants.EventFrom_subformOrNot = SubformFlag;
                    if (SubformFlag) {
                        AppConstants.SF_Container_position = SubformPos;
                        AppConstants.Current_ClickorChangeTagName = SubformName;

                    }
                    AppConstants.GlobalObjects.setCurrent_GPS("");
                    ((MainActivity) context).ChangeEvent(ll_tap_text);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setImage", e);
        }
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
                    iv_CapturedImage.setImageBitmap(setBitmap);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setImageWithGPSValues", e);
        }
    }

    public Bitmap drawTextToBitmap(Context gContext,
                                   Bitmap bitmap,
                                   String gText) {
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;

        android.graphics.Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
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
        paint.setTextSize((int) (25 * scale));
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
            Log.d(TAG, "setImageWithGpsCheck: " + e.toString());
            ImproveHelper.improveException(context, TAG, "setImageWithGps", e);
        }
    }

    public void setImageBitMapBNull() {
        iv_CapturedImage.setVisibility(View.GONE);
        setImageGPS(latitude + "$" + longitude);
    }

    public LinearLayout getLLTapTextView() {

        return ll_tap_text;
    }

    public void getCameraClickView(int cameraRequestCode) {
        try {
//                if (isCamera) {
            if (SubformFlag) {
                AppConstants.SF_Container_position = SubformPos;
                AppConstants.Current_ClickorChangeTagName = SubformName;

            }

            if (controlObject.isCaptureFromCamera() && controlObject.isCaptureFromFile()) {
                tv_tapTextType.setText(R.string.tap_to_camera_gallery);
//            ll_tap_text.setVisibility(View.GONE);
                setLLTapText(View.GONE);
/*                Intent intent = new Intent(context, ImageCaptureActivity.class);
                intent.putExtra("requestCode",cameraRequestCode);
                context.startActivityForResult(intent,cameraRequestCode);*/
//                context.startActivityForResult(new Intent(context, ImageCaptureActivity.class), cameraRequestCode);
                if (SubformFlag) {
                    context.startActivityForResult(new Intent(context, ImageCaptureActivity.class), SF_IMAGE_RESULT_CODE);
                } else {
                    context.startActivityForResult(new Intent(context, ImageCaptureActivity.class), IMAGE_RESULT_CODE);
                }

                ll_camera_gallery.setVisibility(View.VISIBLE);
                ll_imageCaptureTypes.setVisibility(View.VISIBLE);
                tv_reTake.setVisibility(View.GONE);
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

                ct_alertTypeCamera.setVisibility(android.view.View.GONE);
                ct_alertTypeCamera.setText("");

            } else {

                mOpenCamera(cameraRequestCode);

                tv_tapTextType.setText(R.string.tap_to_open_camera);
                ll_camera_gallery.setVisibility(View.VISIBLE);
                tv_reTake.setVisibility(View.VISIBLE);
                setLLTapText(View.GONE);
                ll_imageCaptureTypes.setVisibility(View.GONE);
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

    public void setUploadedImage(ImageView iv_capturedImage, Bitmap thumbnail, String filePath, Uri uri, int flag) {
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

                    ll_camera_gallery.setVisibility(View.VISIBLE);
                    ll_tap_text.setVisibility(View.GONE);
                    ll_imageCaptureTypes.setVisibility(View.VISIBLE);
                    if (controlObject.isCaptureFromCamera() && controlObject.isCaptureFromFile()) {
                        Log.d(TAG, "setControlValuesCapture: " + "Both");
                    } else if (controlObject.isCaptureFromCamera()) {
                        tv_gallery.setVisibility(View.GONE);
                        Log.d(TAG, "setControlValuesCapture: " + "CaptureCamera");
                    } else if (controlObject.isCaptureFromFile()) {
                        tv_camera.setVisibility(View.GONE);
                    }
                    iv_CapturedImage.setImageURI(uri);


                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setUploadedImage", e);
        }
//        iv_capturedImage.setImageBitmap(thumbnail);
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

    public CustomTextView getTv_tapText() {
        return tv_tapTextType;
    }

    public void setImageForEdit(String imageUrl) {
        try {
            if (imageUrl != null && !imageUrl.trim().isEmpty()
                    && !imageUrl.equalsIgnoreCase("File not found")) {

//            ll_tap_text.setVisibility(View.GONE);
                setLLTapText(View.GONE);
                ll_camera_gallery.setVisibility(View.VISIBLE);
                iv_CapturedImage.setVisibility(View.VISIBLE);
                if (imageUrl.startsWith("http")) {
                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.icons_image)
                        .dontAnimate()
                        .into(iv_CapturedImage);}else{
                    Bitmap imageBitmap = BitmapFactory.decodeFile(imageUrl);
                    iv_CapturedImage.setImageBitmap(imageBitmap);
                }
                Log.d(TAG, "CameraSetControlValues: " + imageUrl);
                getReTake().setVisibility(View.VISIBLE);
                ll_camera_gallery.setTag(imageUrl);
                getControlRealPath().setTag(imageUrl);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setImageForEdit", e);
        }
    }

    public void setTvTapText(int visibility) {
        try {
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (!controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {

                    tv_tapTextType.setVisibility(visibility);

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

                    ll_tap_text.setVisibility(visibility);

                } else {
                    ll_tap_text.setVisibility(visibility);
                }
            } else {
                ll_tap_text.setVisibility(visibility);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setLLTapText", e);
        }
    }

    public void showOrSetTapText(String path) {
        if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
            if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                tv_tapTextType.setVisibility(View.VISIBLE);
                tv_tapTextType.setText(path);

            } else {
                ll_tap_text.setVisibility(View.GONE);
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
        setViewDisable(ifView, !enabled);
        controlObject.setDisable(!enabled);
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
        ll_label.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
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
