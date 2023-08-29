package com.bhargo.user.utils;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bhargo.user.R;
import com.bhargo.user.interfaces.PermissionResultsInterface;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import nk.bluefrog.library.camera.CameraActivity;

public class ImageCaptureActivity extends PermissionsActivity {

    public static final int CAMERA_REQUEST_CODE = 101;
    public static final int GALLERY_REQUEST_CODE = 201;
    public static final int IMAGE_RESULT_CODE = 9999;
    public static final int SF_IMAGE_RESULT_CODE = 9991;
    public static final String saveLocation = "Improve_Images";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);
        setFinishOnTouchOutside(false);

        sessionManager = new SessionManager(this);
        try {
            Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
            m.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        askCompactPermissions(new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        }, new PermissionResultsInterface() {
            @Override
            public void permissionGranted() {

            }

            @Override
            public void permissionDenied() {
                Toast.makeText(ImageCaptureActivity.this, "Please accept the permission", Toast.LENGTH_LONG).show();
            }

            @Override
            public void permissionForeverDenied() {
                Toast.makeText(ImageCaptureActivity.this, "Please accept the permission", Toast.LENGTH_LONG).show();

            }
        });

    }


    /**
     * Create a File for saving an image
     */
    private File getOutputMediaFile() {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), saveLocation);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(saveLocation, getString(R.string.failed_to_create_directory));
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
/*            if(data!=null){
                setResult(RESULT_OK,data);
            }else {
                setResult(RESULT_OK, getIntent());
            }
            finish();*/
            if (requestCode == CAMERA_REQUEST_CODE) {




                File photoFile = null;
                try {
                    photoFile = new File(data.getStringExtra(CameraActivity.RESULT_IMG_PATH));
                    String uriSting = photoFile.getAbsolutePath();
                    setSuccessResult(uriSting);
                } catch (Exception e) {
                    e.printStackTrace();
                    setFailResult();
                }

            } else if (requestCode == GALLERY_REQUEST_CODE) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                setSuccessResult(picturePath);
                Log.d("GalleryPath", "" + picturePath);
//                viewImage.setImageBitmap(thumbnail);
            }
        }else{
            setFailResult();
        }
    }

    public void onClick_Camera(View view) {
//        Intent intentC = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
//        intentC.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//        startActivityForResult(intentC, CAMERA_REQUEST_CODE);
//        startActivityForResult(intentC, getIntent().getIntExtra("requestCode",0));
        mOpenCamera(CAMERA_REQUEST_CODE);
    }

    public void onClick_Gallery(View view) {
        Log.d("Check_ImageCapture", "Gallery");
        Intent intentG = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentG, GALLERY_REQUEST_CODE);
//        startActivityForResult(intentG,  getIntent().getIntExtra("requestCode",0));
    }

    public void onClick_Cancle(View view) {
        setFailResult();
    }

    private void setSuccessResult(String strImagePath) {
        Intent intent = new Intent();
        intent.putExtra("image_path", strImagePath);
        setResult(RESULT_OK, intent);
        finish();
    }


    private void setFailResult() {
        setResult(RESULT_CANCELED, null);
        finish();
    }

    @Override
    public void onBackPressed() {
        setFailResult();
    }

//    private ByteArrayOutputStream convertImage() {
//        Bitmap imageBitmap = decodeFile(uriSavedImage.getPath());
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        return stream;
//    }

    public void mOpenCamera(int cameraRequestCodeMain) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

//            dispatchTakePictureIntent(cameraRequestCodeMain);
        Intent intent = new Intent(ImageCaptureActivity.this, CameraActivity.class);
        intent.putExtra(CameraActivity.SAVE_LOCATION, sessionManager.getOrgIdFromSession() + " IMAGES");
        startActivityForResult(intent, cameraRequestCodeMain);

    }


}
