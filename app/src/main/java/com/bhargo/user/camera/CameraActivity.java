package com.bhargo.user.camera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bhargo.user.R;
import com.bhargo.user.utils.ImproveHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;


public class CameraActivity extends AppCompatActivity implements AppConstants, OnClickListener, SensorEventListener {
    private static final String TAG = "CameraActivity";
    private static int orientation;
    private static int orientationDegress;
    private final double PREVIEW_SIZE_FACTOR = 3.00;
    boolean isFlashSupport = false;
    ImproveHelper improveHelper;
    private Camera mCamera;
    private final PictureCallback mPicture = new PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            mCamera.stopPreview();
            BitmapSaveTask task = new BitmapSaveTask(data);
            task.execute();

        }
    };
    private CameraPreview mPreview;
    private SensorManager sensorManager = null;
    private ExifInterface exif;
    private int deviceHeight;
    private TextView mRetakePicture;
    private TextView mUsePicture;
    private ImageView mTakePicture;
    private FrameLayout mCameraSnapOptions;
    private LinearLayout mCameraUseOptions;
    private String fileName;
    private ImageView mChangeCamera;
    private boolean isCameraFacingFront = false;
    private ProgressBar mProgressBar;
    private ImageView flashToggle;
    private boolean isFlashOn = false;

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance(int cameraId) {
        Camera c = null;
        try {
            // attempt to get a Camera instance
            c = Camera.open(cameraId);
            Method rotateMethod = null;
            try {
                rotateMethod = Camera.class.getMethod("setDisplayOrientation", int.class);
                rotateMethod.invoke(c, 90);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }

        // returns null if camera is unavailable
        return c;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        improveHelper = new ImproveHelper(this);
        initViews();
        initListeners();
        initObjects();

    }

    public void isFlashAvailable() {
        try {
            if (isFlashSupport) {
                flashToggle.setVisibility(View.VISIBLE);
            } else {
                flashToggle.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "isFlashAvailable", e);
        }
    }

    private void initViews() {
        try {
            mProgressBar = (ProgressBar) findViewById(R.id.cam_loadingbar);
            mChangeCamera = (ImageView) findViewById(R.id.change_camera);
            mRetakePicture = (TextView) findViewById(R.id.retake_picture);
            mUsePicture = (TextView) findViewById(R.id.use_picture);
            mTakePicture = (ImageView) findViewById(R.id.take_picture);
            flashToggle = (ImageView) findViewById(R.id.flash);
            mCameraSnapOptions = (FrameLayout) findViewById(R.id.camera_snap_options);
            mCameraUseOptions = (LinearLayout) findViewById(R.id.camera_use_options);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "initViews", e);
        }
    }

    private void initListeners() {
        try {
            mTakePicture.setOnClickListener(this);
            mUsePicture.setOnClickListener(this);
            mRetakePicture.setOnClickListener(this);
            mChangeCamera.setOnClickListener(this);
            flashToggle.setOnClickListener(this);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "initListeners", e);
        }
    }

    private void toggleFlash() {
        try {
            if (!isFlashOn) {
                flashToggle.setSelected(true);
                isFlashOn = true;
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH); // flash on
                mCamera.setParameters(parameters);

            } else {
                flashToggle.setSelected(false);
                isFlashOn = false;
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF); // flash on
                mCamera.setParameters(parameters);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "toggleFlash", e);
        }

    }

    private void initObjects() {
        try {
            // Getting the sensor service.
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            isFlashSupport = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
            // Selecting the resolution of the Android device so we can create a
            // proportional preview
            Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            deviceHeight = display.getHeight();
            isFlashAvailable();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "initObjects", e);
        }

    }

    private void createCamera(int cameraId) {

        mCameraSnapOptions.setVisibility(LinearLayout.VISIBLE);
        mCameraUseOptions.setVisibility(LinearLayout.GONE);
        mTakePicture.setEnabled(true);
        if (mCamera != null) {
            releaseCamera();
        }
        // Create an instance of Camera
        mCamera = getCameraInstance(cameraId);
        try {
            if (mCamera != null) {
                // Setting the right parameters in the camera
                Camera.Parameters params = mCamera.getParameters();
                params.setPictureFormat(PixelFormat.JPEG);
                params.setJpegQuality(100);
                int mode = getSuppourtedFocusedModes(mCamera);
                switch (mode) {
                    case 1:
                        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                        break;
                    case 2:
                        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                        break;
                    default:
                        break;
                }
                final Size size = getOptimalSize(mCamera);
                if (size != null) {
                    params.setPreviewSize(size.width, size.height);
                }
                try {
                    params.setPictureSize(size.width, size.height);
                    mCamera.setParameters(params);
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        final Size pictureSize = getPictureSizes(mCamera);
                        if (pictureSize != null) {
                            params.setPictureSize(pictureSize.width, pictureSize.height);
                        }
                        mCamera.setParameters(params);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                mCamera.setParameters(params);
                if (Camera.getNumberOfCameras() > 1) {
                    mChangeCamera.setVisibility(View.VISIBLE);
                } else {
                    mChangeCamera.setVisibility(View.GONE);
                }

                // Create our Preview view and set it as the content of our activity.
                mPreview = new CameraPreview(this, mCamera);
                FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);

                // Calculating the width of the preview so it is proportional.
                float widthFloat = (float) (deviceHeight) * 4 / 3;
                int width = Math.round(widthFloat);

                // Resizing the LinearLayout so we can make a proportional preview. This
                // approach is not 100% perfect because on devices with a really small
                // screen the the image will still be distorted - there is place for
                // improvment.
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, deviceHeight);
                preview.setLayoutParams(layoutParams);

                // Adding the camera preview after the FrameLayout and before the button
                // as a separated element.
                preview.addView(mPreview, 0);
            } else {
                Toast.makeText(CameraActivity.this, "can't open camera", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "createCamera", e);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Creating the camera
        createCamera(CameraInfo.CAMERA_FACING_BACK);

        // Register this class as a listener for the accelerometer sensor
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // release the camera immediately on pause event
        releaseCamera();

    }

    private void releaseCamera() {
        try {
            if (mCamera != null) {
                mCamera.release(); // release the camera for other applications
                mCamera = null;
            }
            // removing the inserted view - so when we come back to the app we
            // won't have the views on top of each other.
            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            if (preview != null && preview.getChildCount() > 0) {
                preview.removeViewAt(0);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "releaseCamera", e);
        }
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.change_camera:
                if (!isCameraFacingFront) {
                    isCameraFacingFront = true;
                    createCamera(CameraInfo.CAMERA_FACING_FRONT);
                } else {
                    isCameraFacingFront = false;
                    createCamera(CameraInfo.CAMERA_FACING_BACK);
                }
                break;
            case R.id.take_picture:
                mTakePicture.setEnabled(false);
                mCamera.takePicture(null, null, mPicture);
                break;
            case R.id.retake_picture:
                mTakePicture.setEnabled(true);
                resetCamera();
                break;
            case R.id.use_picture:
                File pictureFile = new File(sample_DIRCTORY + File.separator + fileName);
                //String caption = mCaptionText.getText().toString();
                Intent returnIntent = getIntent();
                //returnIntent.putExtra(KEY_CHAT_IMAGE_CAPTION, caption);
                returnIntent.putExtra(KEY_CHAT_IMAGE_PATH, pictureFile.getAbsolutePath());
                //Log.d("picture details : ", caption+" :::path : "+ pictureFile.getAbsolutePath());
                setResult(RESULT_OK, returnIntent);
                finish();
                //doCrop(pictureFile.getAbsolutePath(), pictureFile);
                break;
            case R.id.flash:
                toggleFlash();
                break;
            default:
                break;
        }

    }

    private void resetCamera() {
        try {
            // Deleting the image from the SD card/
            File discardedPhoto = new File(sample_IMAGE_SENT_DIRECTORY + File.separator + fileName);
            discardedPhoto.delete();

            // Restart the camera preview.
            mCamera.startPreview();

            // Reorganize the buttons on the screen
            mCameraSnapOptions.setVisibility(LinearLayout.VISIBLE);
            mCameraUseOptions.setVisibility(LinearLayout.GONE);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "resetCamera", e);
        }
    }

    @Override
    public void onBackPressed() {
        if (mCameraUseOptions.isShown()) {
            mTakePicture.setEnabled(true);
            resetCamera();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private Size getOptimalSize(Camera camera) {
        Display display = getWindowManager().getDefaultDisplay();
        Point windowSize = new Point();
        display.getSize(windowSize);
        Size result = null;
        try{
        final Camera.Parameters parameters = camera.getParameters();
        Log.d(CameraActivity.class.getSimpleName(), "window width: " + windowSize.x + ", height: " + windowSize.y);
        for (final Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= windowSize.x * PREVIEW_SIZE_FACTOR && size.height <= windowSize.y * PREVIEW_SIZE_FACTOR) {
                if (result == null) {
                    result = size;
                } else {
                    final int resultArea = result.width * result.height;
                    final int newArea = size.width * size.height;

                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }
        if (result == null) {
            result = parameters.getSupportedPreviewSizes().get(0);
        }
        Log.i(CameraActivity.class.getSimpleName(), "Using PreviewSize: " + result.width + " x " + result.height);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getOptimalSize", e);
        }
        return result;
    }

    private Size getPictureSizes(Camera camera) {
        Display display = getWindowManager().getDefaultDisplay();
        Point windowSize = new Point();
        display.getSize(windowSize);
        Size result = null;
        try{
        final Camera.Parameters parameters = camera.getParameters();
        Log.d(CameraActivity.class.getSimpleName(), "window width: " + windowSize.x + ", height: " + windowSize.y);
        for (final Size size : parameters.getSupportedPictureSizes()) {
            if (size.width <= windowSize.x * PREVIEW_SIZE_FACTOR && size.height <= windowSize.y * PREVIEW_SIZE_FACTOR) {
                if (result == null) {
                    result = size;
                } else {
                    final int resultArea = result.width * result.height;
                    final int newArea = size.width * size.height;

                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }
        if (result == null) {
            result = parameters.getSupportedPreviewSizes().get(0);
        }
        Log.i(CameraActivity.class.getSimpleName(), "Using PreviewSize: " + result.width + " x " + result.height);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getPictureSizes", e);
        }

        return result;
    }

    private int getSuppourtedFocusedModes(Camera camera) {
        final Camera.Parameters parameters = camera.getParameters();
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        if (supportedFocusModes.contains("continuous-picture")) {
            return 1;
        } else if (supportedFocusModes.contains("auto")) {
            return 2;
        }
        return 0;
    }

    private void setSuccessResult(String strImagePath) {
        Intent intent = new Intent();
        intent.putExtra("image_path", strImagePath);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
        mCamera = null;
        mPreview = null;
        sensorManager = null;
        System.gc();

    }

    class BitmapSaveTask extends AsyncTask<String, Void, Bitmap> {
        public byte[] data = null;
        private Context mContext;

        public BitmapSaveTask(byte[] bitmapData) {
            data = bitmapData;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                Matrix m = new Matrix();
                if (isCameraFacingFront) {
                    m.postRotate(360 - orientationDegress);
                } else {
                    m.postRotate(orientationDegress);
                }
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inDither = true;
                opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
                opt.inSampleSize = 2;
                Bitmap thePicture = BitmapFactory.decodeByteArray(data, 0, data.length, opt);
                thePicture = Bitmap.createBitmap(thePicture, 0, 0, thePicture.getWidth(), thePicture.getHeight(), m, true);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                thePicture.compress(CompressFormat.JPEG, 100, bos);
                // thePicture.compress(CompressFormat.PNG, 0 , bos);
                byte[] pictureBytes = bos.toByteArray();

                // File name of the image that we just took.
                fileName = System.currentTimeMillis() / 1000 + ".jpeg";
//				long time =  System.currentTimeMillis() / 1000;
//				fileName = "Swachata_" + "FacilityPhoto" + String.valueOf(time)+ ".png";

                File folder = new File(Environment.getExternalStorageDirectory() + "/ImproveUser");
                if (!folder.exists()) {
                    folder.mkdir();
                }

                // Main file where to save the data that we recive from the camera
                File pictureFile = new File(sample_DIRCTORY + File.separator + fileName);


                FileOutputStream purge = new FileOutputStream(pictureFile);
                purge.write(pictureBytes);
                purge.close();
                thePicture.recycle();

//				setSuccessResult(pictureFile.getAbsolutePath());


                // Adding Exif data for the orientation. For some strange reason the
                // ExifInterface class takes a string instead of a file.
                /*
                 * exif = new ExifInterface(pictureFile.getAbsolutePath()); exif.setAttribute(ExifInterface.TAG_ORIENTATION, "" + orientation); exif.saveAttributes();
                 */
            } catch (IOException e) {
                Log.d("IOException", e.getMessage());
            } catch (OutOfMemoryError e) {
                Log.d("OutOfMemory", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mProgressBar.setVisibility(View.GONE);
            // Replacing the button after a photho was taken.
            mCameraSnapOptions.setVisibility(View.GONE);
            mCameraUseOptions.setVisibility(View.VISIBLE);

            //mCaptionText.requestFocus();
        }
    }
}