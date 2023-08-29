package com.bhargo.user.screens;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bhargo.user.R;
import com.bhargo.user.camera.crop.CropImage;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.FormDataResponse;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.FileUploader_ML;
import com.bhargo.user.utils.ImageCaptureActivity_1;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nk.mobileapps.spinnerlib.SearchableSpinner;
import nk.mobileapps.spinnerlib.SpinnerData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.utils.ImageCaptureActivity.IMAGE_RESULT_CODE;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

public class ML_Training_Activity extends BaseActivity {

    public static final int CROP_PHOTO_SELECTED = 1006;
    public static final int PICK_IMAGE_CAMERA = 1005;
    private static final String TAG = "ML_Training_Activity";
    public String strAppName, FromNormalTask, strUserId, strorgID;
    ImageView iv_image,iv_CapturedImage;
    LinearLayout ll_tap_text;
    SearchableSpinner sp_Label;
    String iconpath = "NA";
    CustomButton cb_create_wl;
    SessionManager sessionManager;
    ImproveHelper improveHelper;
    LinearLayout ll_SCmain;
    String appIconPath = null;
    Uri mImageCaptureUri;
    private String strAppDesign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ml_view);
        initializeActionBar();
        enableBackNavigation(true);
        sessionManager = new SessionManager(ML_Training_Activity.this);
        improveHelper = new ImproveHelper(ML_Training_Activity.this);

        Intent getIntent = getIntent();

        if (getIntent != null) {
            strAppDesign = getIntent.getStringExtra("s_app_design");
            strAppName = getIntent.getStringExtra("s_app_name");
            if (getIntent.hasExtra("FromNormalTask")) {
                FromNormalTask = getIntent.getStringExtra("FromNormalTask");
            }
            strUserId = getIntent.getStringExtra("s_user_id");
            appIconPath = getIntent.getStringExtra("AppIcon");
        }
        strorgID = sessionManager.getOrgIdFromSession();
        if (strAppName != null) {
            title.setText(strAppName);
        }
        ll_SCmain = findViewById(R.id.ll_SCmain);
        iv_circle_appIcon.setVisibility(View.VISIBLE);

        cb_create_wl = findViewById(R.id.cb_create_wl);
        sp_Label = findViewById(R.id.sp_Label);
        sp_Label.setItems(LoadLabels());
        ll_tap_text=findViewById(R.id.ll_tap_text);
        ll_tap_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "ImproveUser_" + System.currentTimeMillis() + ".png"));

                startActivityForResult(new Intent(ML_Training_Activity.this, ImageCaptureActivity_1.class), IMAGE_RESULT_CODE);
            }
        });
        iv_CapturedImage=findViewById(R.id.iv_CapturedImage);
        iv_CapturedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "ImproveUser_" + System.currentTimeMillis() + ".png"));

                startActivityForResult(new Intent(ML_Training_Activity.this, ImageCaptureActivity_1.class), IMAGE_RESULT_CODE);

//                startActivityForResult(new Intent(ML_Training_Activity.this,
//                        CameraActivity.class), PICK_IMAGE_CAMERA);
            }
        });

        iv_image = findViewById(R.id.iv_image);
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "ImproveUser_" + System.currentTimeMillis() + ".png"));

                startActivityForResult(new Intent(ML_Training_Activity.this, ImageCaptureActivity_1.class), IMAGE_RESULT_CODE);

//                startActivityForResult(new Intent(ML_Training_Activity.this,
//                        CameraActivity.class), PICK_IMAGE_CAMERA);
            }
        });

        cb_create_wl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sp_Label.isSelected()) {
                    ImproveHelper.showToast(ML_Training_Activity.this, "Please Select Label.");
                    ImproveHelper.setFocus(sp_Label);
                } else if (iconpath.equalsIgnoreCase("NA") || iconpath.trim().length() == 0) {
                    ImproveHelper.showToast(ML_Training_Activity.this, "Please Capture Image.");
                    ImproveHelper.setFocus(iv_image);
                } else {
                    SendFile();
                }
            }
        });
        loadAppIcon(appIconPath);

    }

    public void loadAppIcon(String appIcon) {

        try {
            if (appIcon != null) {

                if (isNetworkStatusAvialable(ML_Training_Activity.this)) {
                    Glide.with(ML_Training_Activity.this).load(appIcon).into(iv_circle_appIcon);
                } else {

                    String[] imgUrlSplit = appIcon.split("/");
                    String replaceWithUnderscore = strAppName.replaceAll(" ", "_");

                    String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + replaceWithUnderscore + "/" + imgUrlSplit[imgUrlSplit.length - 1];
                    Log.d(TAG, "onCreateSDCardPathCheck: " + strSDCardUrl);

                    setImageFromSDCard(strSDCardUrl);
                    improveHelper.snackBarAlertActivities(ML_Training_Activity.this, ll_SCmain);
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "loadAppIcon", e);
        }

    }

    public void setImageFromSDCard(String strImagePath) {
        try {
            File imgFile = new File(Environment.getExternalStorageDirectory(), strImagePath);
            Log.d(TAG, "setImageFromSDCard: " + imgFile);
            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                iv_circle_appIcon.setImageBitmap(myBitmap);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "setImageFromSDCard", e);
        }

    }

    /*
     * crop the image
     */
    public void doCrop(String imagePath) {
        try {
            Uri uri = null;
            Intent intent = new Intent(this, CropImage.class);
            File f = new File(getExternalCacheDir() + File.separator + "temporary_holder.png");
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Log.e("io", ex.getMessage());
            }
            uri = Uri.fromFile(f);
            intent.setData(mImageCaptureUri);
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 256);
            intent.putExtra("aspectX", 4);
            intent.putExtra("aspectY", 5);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            intent.putExtra("crop", "true");
            intent.putExtra("image-path", imagePath);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

            startActivityForResult(intent, CROP_PHOTO_SELECTED);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "doCrop", e);
        }

    }

    public void SendFile() {
        try {
            String fileName = iconpath.substring(iconpath.lastIndexOf("/") + 1);
            String pageName = strAppName.replace(" ", "_");


            new FileUploader_ML(this, fileName, strUserId, strorgID, strAppName, false,
                    "BHARGO", sp_Label.getSelectedId(), false, new FileUploader_ML.OnImageUploaded() {
                @Override
                public void response(String url) {
                    if (!url.equalsIgnoreCase(getResources().getString(R.string.file_not_found))) {
                        System.out.println("URL===" + url);
                        SubmitData(url);
                    }

                }
            }).execute(iconpath);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "SendFile", e);
        }

    }

    public List<SpinnerData> LoadLabels() {
        List<SpinnerData> spinnerDataList = new ArrayList<>();
        try {
            String[] Labelsarr = strAppDesign.split("\\,");
            for (int i = 0; i < Labelsarr.length; i++) {
                SpinnerData spinnerData = new SpinnerData();
                spinnerData.setId(Labelsarr[i]);
                spinnerData.setName(Labelsarr[i]);
                spinnerDataList.add(spinnerData);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "LoadLabels", e);
        }
        return spinnerDataList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                hideKeyboard(this, view);

                Intent intent = new Intent(ML_Training_Activity.this, BottomNavigationActivity.class);
                if (FromNormalTask != null && !FromNormalTask.isEmpty()) {
                    intent.putExtra("FromNormalTask", "FromNormalTask");
                } else {
                    if (PrefManger.getSharedPreferencesString(ML_Training_Activity.this, AppConstants.Notification_Back_Press, "") != null) {
                        String onBackFrom = PrefManger.getSharedPreferencesString(ML_Training_Activity.this, AppConstants.Notification_Back_Press, "");
                        if (onBackFrom.equalsIgnoreCase("0")) {
                            intent.putExtra("NotifRefreshAppsList", "0");
                        } else {
                            intent.putExtra("NotifRefreshAppsList", "0");
                        }
                    }
                }
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        try {
            hideKeyboard(this, view);
            AlertDialog alertDialog = new AlertDialog.Builder(this)

                    .setTitle("Are you sure to Exit")

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                      finish();
                            Intent intent = new Intent(ML_Training_Activity.this, BottomNavigationActivity.class);
                            if (FromNormalTask != null && !FromNormalTask.isEmpty()) {
                                intent.putExtra("FromNormalTask", "FromNormalTask");
                            } else {
                                if (PrefManger.getSharedPreferencesString(ML_Training_Activity.this, AppConstants.Notification_Back_Press, "") != null) {
                                    String onBackFrom = PrefManger.getSharedPreferencesString(ML_Training_Activity.this, AppConstants.Notification_Back_Press, "");
                                    if (onBackFrom.equalsIgnoreCase("0")) {
                                        intent.putExtra("NotifRefreshAppsList", "0");
                                    } else {
                                        intent.putExtra("NotifRefreshAppsList", "0");
                                    }
                                }
                            }
                            startActivity(intent);
                        }
                    })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what should happen when negative button is clicked
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "onBackPressed", e);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String mediaFilePath = "";
        if (resultCode == Activity.RESULT_OK) {
            try {

                if (requestCode == PICK_IMAGE_CAMERA) {
                    if (data.getExtras().containsKey(com.bhargo.user.camera.AppConstants.KEY_CHAT_IMAGE_PATH)) {
                        mediaFilePath = data.getStringExtra(com.bhargo.user.camera.AppConstants.KEY_CHAT_IMAGE_PATH);
                        mImageCaptureUri = Uri.fromFile(new File(mediaFilePath));
                        if (mImageCaptureUri != null) {
                            doCrop(mediaFilePath);
                        }
                    }
                } else if (requestCode == CROP_PHOTO_SELECTED) {
                    File f = new File(mediaFilePath);
                    Bitmap croppedImage = data.getExtras().getParcelable("data");
                    iconpath = data.getExtras().getString("Path");
                    System.out.println("iconpath====" + iconpath);
                    iv_CapturedImage.getLayoutParams().width = 600;
                    iv_CapturedImage.getLayoutParams().height = 600;
                    iv_CapturedImage.setImageBitmap(croppedImage);

                    iv_CapturedImage.setVisibility(View.VISIBLE);
                    ll_tap_text.setVisibility(View.GONE);
//                    iconpath=f.getAbsolutePath();
//                    if (!iconpath.contains("http")) {
//                        Bitmap thumbnail = (BitmapFactory.decodeFile(data.getStringExtra("image_path")));
//                        iv_image.getLayoutParams().width = 600;
//                        iv_image.getLayoutParams().height = 600;
//                        iv_image.setImageBitmap(thumbnail);
//                    }else {
//                        Glide.with(ML_Training_Activity.this).load(iconpath)
//                                .placeholder(R.drawable.round_rect_shape)
//                                .dontAnimate().into(iv_image)
//                                .onLoadFailed(getDrawable(R.drawable.round_rect_shape));
//                    }

                } else if (requestCode == IMAGE_RESULT_CODE) {
                    iconpath = data.getStringExtra("image_path");

                    mImageCaptureUri = Uri.fromFile(new File(iconpath));
                    if (mImageCaptureUri != null) {
                        doCrop(iconpath);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(ML_Training_Activity.this, "No image captured", Toast.LENGTH_LONG).show();
        }
//        switch (requestCode) {
//            case IMAGE_RESULT_CODE:
//
//                if (resultCode == RESULT_OK) {
//                    iconpath = data.getStringExtra("image_path");
//                    if (!iconpath.contains("http")) {
//                        Bitmap thumbnail = (BitmapFactory.decodeFile(data.getStringExtra("image_path")));
//                        iv_image.getLayoutParams().width = 600;
//                        iv_image.getLayoutParams().height = 600;
//                        iv_image.setImageBitmap(thumbnail);
//                    }else {
//                        Glide.with(ML_Training_Activity.this).load(iconpath)
//                                .placeholder(R.drawable.round_rect_shape)
//                                .dontAnimate().into(iv_image)
//                                .onLoadFailed(getDrawable(R.drawable.round_rect_shape));
//                    }
//
//                } else {
//                    Toast.makeText(ML_Training_Activity.this, "No image captured", Toast.LENGTH_LONG).show();
//                }
//                break;
//            case  PICK_IMAGE_CAMERA:
//
//                break;
//        }
    }

    private void SubmitData(String url) {

        try {
            showProgressDialog("Please Wait");
            GetServices getServices = RetrofitUtils.getUserService();

            String pageName = strAppName.replace(" ", "_");

            JSONObject data = new JSONObject();
            try {
                data.put("UserId", ImproveHelper.getUserDetails(this).getUserID());
                data.put("OrgID", strorgID);
                data.put("ProjectName", pageName);
                data.put("Lable", sp_Label.getSelectedId());
                data.put("ImagePath", url);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(data.toString());

            Call<FormDataResponse> appNamesCall = getServices.InsertMLTrainng(jo);

            appNamesCall.enqueue(new Callback<FormDataResponse>() {
                @Override
                public void onResponse(Call<FormDataResponse> call, Response<FormDataResponse> response) {
                    try {
                        dismissProgressDialog();

                        try {

                            if (response.body() != null) {
                                String resultStatus = response.body().getStatus();
                                if (resultStatus.equalsIgnoreCase("200")) {
                                    Toast.makeText(ML_Training_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ML_Training_Activity.this, BottomNavigationActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if (resultStatus.equalsIgnoreCase("100")) {
                                    dismissProgressDialog();
                                    Toast.makeText(ML_Training_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else if (resultStatus.equalsIgnoreCase("104")) {
                                    dismissProgressDialog();
                                    Toast.makeText(ML_Training_Activity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                } else if (resultStatus.equalsIgnoreCase("500")) {
                                    dismissProgressDialog();
                                    Toast.makeText(ML_Training_Activity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            } else {

                                Toast.makeText(ML_Training_Activity.this, "server data is null", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } catch (Exception e) {
                        ImproveHelper.showToast(ML_Training_Activity.this, "Server Error..");
                    }

                }

                @Override
                public void onFailure(Call<FormDataResponse> call, Throwable t) {
                    dismissProgressDialog();


                }
            });
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "SubmitData", e);
        }
    }
}
