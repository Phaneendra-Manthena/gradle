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
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.PermissionResultsInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageCaptureActivity_1 extends PermissionsActivity {

    public static final int CAMERA_REQUEST_CODE = 101;
    public static final int GALLERY_REQUEST_CODE = 201;
    public static final int IMAGE_RESULT_CODE = 9999;
    public static final int DEFAULT_IMAGE_RESULT_CODE = 301;
    public static final String saveLocation = "Improve_Images";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);
        setFinishOnTouchOutside(false);
        try {
            Intent intent = getIntent();
            String menu = intent.getStringExtra("Menu_Icon");
            if (menu.equalsIgnoreCase("Menu_Icon")) {
                CustomTextView ct_Camera = findViewById(R.id.ct_Camera);
                ct_Camera.setVisibility(View.GONE);
            }
        } catch (Exception e) {
        }
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
                Toast.makeText(ImageCaptureActivity_1.this, "Please accept the permission", Toast.LENGTH_LONG).show();
            }

            @Override
            public void permissionForeverDenied() {
                Toast.makeText(ImageCaptureActivity_1.this, "Please accept the permission", Toast.LENGTH_LONG).show();

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
            if (requestCode == CAMERA_REQUEST_CODE) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    f.delete();

                    OutputStream outFile = null;
                    File file = getOutputMediaFile();
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                        setSuccessResult(file.getAbsolutePath());

//                    viewImage.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        setFailResult();
                    } catch (IOException e) {
                        e.printStackTrace();
                        setFailResult();
                    } catch (Exception e) {
                        e.printStackTrace();
                        setFailResult();
                    }
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
                Log.d("GalleryPath", "" + picturePath);
                setSuccessResult(picturePath);
//                viewImage.setImageBitmap(thumbnail);
            } else if (requestCode == DEFAULT_IMAGE_RESULT_CODE) {
                setSuccessResult(data.getStringExtra("image_path"));
            }
        }
    }

    public void onClick_Camera(View view) {
        Intent intentC = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        intentC.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(intentC, CAMERA_REQUEST_CODE);
    }

    public void onClick_Gallery(View view) {
        Log.d("Check_ImageCapture", "Gallery");
        Intent intentG = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentG, GALLERY_REQUEST_CODE);
    }

    public void onClick_GridImages(View view) {
        Intent intent = new Intent(ImageCaptureActivity_1.this, GridImagesActivity.class);
        startActivityForResult(intent, DEFAULT_IMAGE_RESULT_CODE);


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
}
