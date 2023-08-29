package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.adapters.ImageSliderAdapter;
import com.bhargo.user.adapters.VerticalImageSliderAdapter;
import com.bhargo.user.controls.variables.PathVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.uisettings.pojos.ControlUIProperties;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.CustomViewPager;
import com.bhargo.user.utils.GalleryViewActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RoundedImageView;
import com.bhargo.user.utils.SessionManager;
import com.bhargo.user.utils.TouchImageView;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Image implements PathVariables, UIVariables {

    private static final String TAG = "Image";
    private final int ImageTAG = 0;
    TouchImageView mainImageViewZoom;
    public ImageView imageView;
    public RoundedImageView mainImageView;
//    public CircleImageView mainImageViewCircle;
    public ImageView galleryViewImageView;
    public String ImagePath = "", strDisplayUrlImage, strAppName = null;
    SessionManager sessionManager;

    public ControlObject getControlObject() {
        return controlObject;
    }

    public ControlObject controlObject;
    //    List<String> colorName;
    public LinearLayout linearLayout, ll_single_Image, ll_displayName, ll_insideCard, ll_sliderVertical,
            ll_main_inside,ll_control_ui,layout_control,ll_leftRightWeight,ll_tap_text;
    public CustomTextView tv_displayName, tv_hint, ct_alertImageView, tv_images_count,ct_showText;
    View viewUI;
    Activity context;
    String sampleImage = "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";
    List<Integer> color;
    //    List<Drawable> imagesList;
    List<String> stringListImages = new ArrayList<String>();
    CardView cv_all, cv_circle;
    ImproveHelper improveHelper;
    FrameLayout ll_Gallery_View;
    RelativeLayout rl_sliderHorizontal;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    ControlUIProperties controlUIProperties;
    boolean roundedCorners = false;
    private ImageView iv_mandatory;
    private View view;
    private CustomViewPager viewPager;
    private TabLayout indicator;
    private RecyclerView rv_verticalAlignment;


    public Image(Activity context, ControlObject controlObject,
                 boolean SubformFlag, int SubformPos, String SubformName, String strAppName,
                 ControlUIProperties controlUIProperties) {
        this.context = context;
        this.controlObject = controlObject;
        this.strAppName = strAppName;
        improveHelper = new ImproveHelper(context);
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        this.controlUIProperties = controlUIProperties;
        sessionManager = new SessionManager(context);
        initView();
    }


    public void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());

            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
//        linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            addImageStrip(context);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }


    public LinearLayout getImageView() {

        return linearLayout;
    }

    public ImageView getGalleryImageView() {

        return galleryViewImageView;
    }

    public FrameLayout getGalleryImageLayout() {

        return ll_Gallery_View;
    }

    public LinearLayout getLl_insideCard() {

        return ll_insideCard;
    }


    public ImageView getGalleryViewImageView() {

        return galleryViewImageView;
    }

    public CardView getCardView() {

        return cv_all;
    }

    public View getViewUI() {

        return viewUI;
    }

    public void addImageStrip(final Context context) {
        try {

            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = lInflater.inflate(R.layout.control_image, null);
            if (controlObject.isEnableMultipleImages() && AppConstants.CurrentAppObject.isUIFormNeeded()
                    && controlObject.getImagesArrangementType() != null && !controlObject.getImagesArrangementType().equalsIgnoreCase("Gallery View")) {
                view = linflater.inflate(R.layout.control_image_ui_slider, null);
            } else if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                    view = linflater.inflate(R.layout.layout_image_six, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {

                    view = linflater.inflate(R.layout.layout_image_seven, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("8")) {

                    view = linflater.inflate(R.layout.layout_image_eight, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {

                    view = linflater.inflate(R.layout.layout_image_nine, null);

                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("10")) {

                    view = linflater.inflate(R.layout.layout_image_ten, null);
//                    view = linflater.inflate(R.layout.layout_image_default, null);


                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("11")) {

                    view = linflater.inflate(R.layout.layout_image_default, null);

                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("12")) {
                    roundedCorners = true;
                    view = linflater.inflate(R.layout.layout_image_twelve, null);
                } else {
//                    view = linflater.inflate(R.layout.control_image, null);
                    view = linflater.inflate(R.layout.layout_image_default, null);
                }
            } else {
//                view = linflater.inflate(R.layout.control_image, null);
                view = linflater.inflate(R.layout.layout_image_default, null);
            }
            view.setTag(ImageTAG);
            Log.d(TAG, "addImageStrip: " + roundedCorners);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            layout_control = view.findViewById(R.id.layout_control);
            ll_leftRightWeight = view.findViewById(R.id.ll_leftRightWeight);
            ll_tap_text = view.findViewById(R.id.ll_tap_text);
            ll_single_Image = view.findViewById(R.id.ll_single_Image);
            ll_Gallery_View = view.findViewById(R.id.ll_Gallery_View);
            ll_insideCard = view.findViewById(R.id.ll_insideCard);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            cv_all = view.findViewById(R.id.cv_all);
            cv_circle = view.findViewById(R.id.cv_circle);
            galleryViewImageView = view.findViewById(R.id.galleryViewImageView);
            mainImageView = view.findViewById(R.id.mainImageView);
//            mainImageViewCircle = view.findViewById(R.id.mainImageViewCircle);
            mainImageViewZoom = view.findViewById(R.id.mainImageViewZoom);
            mainImageView.setTag(controlObject.getControlName());
//        mainImageView.setImageBitmap(Base64StringToBitmap(sampleImage));
            tv_images_count = view.findViewById(R.id.tv_images_count);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            ct_alertImageView = view.findViewById(R.id.ct_alertTypeText);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            rl_sliderHorizontal = view.findViewById(R.id.rl_sliderHorizontal);
            ll_sliderVertical = view.findViewById(R.id.ll_sliderVertical);
            rv_verticalAlignment = view.findViewById(R.id.rv_verticalAlignment);
            viewUI = view.findViewById(R.id.viewUI);
            ct_showText = view.findViewById(R.id.ct_showText);

            linearLayout.setTag(controlObject.getControlName());
            ll_main_inside.setTag(controlObject.getControlName());
            ll_single_Image.setTag(controlObject.getControlName());
            ll_Gallery_View.setTag(controlObject.getControlName());


            ll_main_inside.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
//                        if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("9") && controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                        if (AppConstants.EventCallsFrom == 1) {
                            AppConstants.EventFrom_subformOrNot = SubformFlag;
                            if (SubformFlag) {
                                AppConstants.SF_Container_position = SubformPos;
                                AppConstants.Current_ClickorChangeTagName = SubformName;
                            }
                            if (AppConstants.GlobalObjects != null) {
                                AppConstants.GlobalObjects.setCurrent_GPS("");
                            }
                            ((MainActivity) context).ChangeEvent(view);
                        }
                    }

//                    backgroundColorToImage();

                }
            });


            setControlValues();

            linearLayout.addView(view);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addImageStrip", e);
        }
    }


    private void backgroundColorToImage() {
        ll_main_inside.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
    }

    private void setControlValues() {
        try {
            if (controlObject.getControlName().equalsIgnoreCase("bhargo_icon_back")) {
                ll_single_Image.setVisibility(View.VISIBLE);
                ll_main_inside.setGravity(Gravity.CENTER);
//                mainImageView.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_back_black_24dp_toolbar));
                if (controlObject.getTextHexColor() != null && !controlObject.getTextHexColor().isEmpty()) {
                    Drawable d = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_arrow_back_black_24dp_toolbar, null);
                    d = DrawableCompat.wrap(d);
                    DrawableCompat.setTint(d, Color.parseColor(controlObject.getTextHexColor()));
                    mainImageView.setImageDrawable(d);
                    Log.d(TAG, "setControlValuesColorFilter: " + controlObject.getTextHexColor());
                } else {
                    mainImageView.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_back_black_24dp_toolbar));
                }
            }
            if(mainImageView != null && controlUIProperties != null && controlUIProperties.getTintColorHex() != null && !controlUIProperties.getTintColorHex().isEmpty()){
                mainImageView.setColorFilter(Color.parseColor(controlUIProperties.getTintColorHex()));
            }
            if (controlObject.getControlName().equalsIgnoreCase("bhargo_icon")) {
//                ll_single_Image.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
                ll_main_inside.setLayoutParams(layoutParams);
                ll_main_inside.setGravity(Gravity.CENTER_VERTICAL);
//                mainImageView.setLayoutParams(layoutParams);
//                mainImageView.setBackgroundDrawable(context.getDrawable(R.drawable.icon_bhargo_user_rounded));
                mainImageView.setBackgroundDrawable(context.getDrawable(R.drawable.allapps));
                android.view.ViewGroup.LayoutParams layoutParamsImg = mainImageView.getLayoutParams();
                layoutParamsImg.width = pxToDP(40);
                layoutParamsImg.height = pxToDP(40);
                mainImageView.setLayoutParams(layoutParamsImg);
            }
            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            } else {
                tv_displayName.setVisibility(View.GONE);
            }
            if (controlObject.getHint() != null && controlObject.getHint().trim().length() > 0) {
                tv_hint.setText(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }
            if (controlObject.isNullAllowed()) {
                iv_mandatory.setVisibility(View.VISIBLE);
            } else {
                iv_mandatory.setVisibility(View.GONE);
            }
            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            }


            if (controlObject.isDisable()) {
//                setViewDisable(view, false);
                setViewDisableOrEnableDefault(context,view, false);
            }
            System.out.println("controlObject.getImageData()=====" + controlObject.getImageData());
            if (controlObject.getImageData() != null || (controlObject.getControlValue() != null && controlObject.getImagesArrangementType() == null && !controlObject.getControlValue().isEmpty()
                    && !controlObject.getControlValue().equalsIgnoreCase("File not found"))) {

                ll_single_Image.setVisibility(View.VISIBLE);
                strDisplayUrlImage = controlObject.getImageData();
//                Log.d("ImageDataTypeALLOne: ", strDisplayUrlImage);
                if (strDisplayUrlImage == null || strDisplayUrlImage.contentEquals("")) {
                    strDisplayUrlImage = controlObject.getControlValue();
                }
                controlObject.setText(controlObject.getImageData());
                if (isNetworkStatusAvialable(context)) {


//linearLayout.addView(progressBar);
                    System.out.println("AppConstants.CurrentAppObject.isUIFormNeeded()==" + AppConstants.CurrentAppObject.isUIFormNeeded());
                    if (AppConstants.CurrentAppObject.isUIFormNeeded() && strDisplayUrlImage != null) {

//                        RequestOptions options = new RequestOptions()
//                                .centerCrop()
//                                .error(R.drawable.image_not_found)
//                                .priority(Priority.HIGH);
//                        ProgressBar progressBar = new ProgressBar(context);
//                        new GlideImageLoader(mainImageView,
//                                progressBar).load(strDisplayUrlImage,options);

                        /////////
/*                        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
                        circularProgressDrawable.setStrokeWidth(5f);
                        circularProgressDrawable.setCenterRadius(30f);
                        circularProgressDrawable.start();
                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions.placeholder(circularProgressDrawable);
                        requestOptions.error(R.drawable.image_not_found);
                        requestOptions.skipMemoryCache(true);
                        requestOptions.fitCenter();
                        Glide.with(context).load(strDisplayUrlImage).*//*thumbnail(0.25f).*//*apply(requestOptions).into(mainImageView);*/


                        Log.d("ImageUrlInUISettings: ", strDisplayUrlImage);
                        setPath(strDisplayUrlImage);
                        Glide.with(context)
                                .load(strDisplayUrlImage)
                                .thumbnail(0.25f)
                                .into(mainImageView);
                        if(AppConstants.DefultAPK_OrgID == "SELE20210719175221829" && controlObject.getControlName().equalsIgnoreCase("notifications")){
                            AppConstants.NOTIFICATION_IMAGE = mainImageView;
                        }

//                                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) // resizes width to 100, preserves original height, does not respect aspect ratio
//                                .listener(new RequestListener<Drawable>() {
//                                    @Override
//                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//
//                                        return false;
//                                    }
//
//                                    @Override
//                                    public boolean onResourceReady(Drawable resource,
//                                                                   Object model, Target<Drawable> target,
//                                                                   DataSource dataSource, boolean isFirstResource) {
//                                        Log.d(TAG, "setControlValuesProgress2: "+"Loaded");
//                                        showToastAlert(context,"Loaded");
//                                        return false;
//                                    }
//
//                                })

                        /*Glide.with(context).load(strDisplayUrlImage)
                                .placeholder(R.color.transparent)
                                .into(mainImageView);*/
//                        Glide.with(context).load("https://www.gstatic.com/webp/gallery/1.jpg").apply(new RequestOptions().override(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels)).placeholder(R.drawable.icons_image_update).dontAnimate().into(mainImageView);
//                        Glide.with(context).load(strDisplayUrlImage).apply(new RequestOptions().override(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels)).placeholder(R.drawable.icons_image_update).dontAnimate().into(mainImageView);
                    } else {

/*                        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
                        circularProgressDrawable.setStrokeWidth(5f);
                        circularProgressDrawable.setCenterRadius(30f);
                        circularProgressDrawable.start();

                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions.placeholder(circularProgressDrawable);
                        requestOptions.error(R.drawable.icons_image_update);
                        requestOptions.skipMemoryCache(true);
                        requestOptions.fitCenter();
                        Glide.with(context).load(strDisplayUrlImage).apply(requestOptions).into(mainImageView);*/
                        setPath(strDisplayUrlImage);
                        if(controlObject.isZoomImageEnable()){
                            Glide.with(context).load(strDisplayUrlImage).placeholder(R.drawable.icons_image_update).dontAnimate().into(mainImageViewZoom);
                        }else{
                            Glide.with(context).load(strDisplayUrlImage).placeholder(R.drawable.icons_image_update).dontAnimate().into(mainImageView);
                        }



                    }

                    System.out.println("strDisplayUrlImage at download====" + strDisplayUrlImage);
                    DownloadFileFromURLTask fromURLTask = new DownloadFileFromURLTask();
                    fromURLTask.execute(strDisplayUrlImage);
                } else {

                    Log.d(TAG, "setControlValuesInternet: " + "No Internet");
                    String[] imgUrlSplit = strDisplayUrlImage.split("/");
                    String replaceWithUnderscore = strAppName.replaceAll(" ", "_");

                    String strSDCardUrl = "/Improve_User/" + replaceWithUnderscore + "/" +
                            imgUrlSplit[imgUrlSplit.length - 1];
                    Log.d(TAG, "onCreateSDCardPathCheck: " + strSDCardUrl);

                    setImageFromSDCard(strSDCardUrl);

                }
            }
            if (controlObject.isEnableMultipleImages()) {
                ll_single_Image.setVisibility(View.GONE);
                mainImageView.setVisibility(View.GONE);
                if (controlObject.getImageData() != null) {
                    String[] strSplitMultipleImages = controlObject.getImageData().split(",");
                    if (strSplitMultipleImages.length != 0) {
                        for (int i = 0; i < strSplitMultipleImages.length; i++) {
                            if (!strSplitMultipleImages[i].isEmpty())
                                stringListImages.add(strSplitMultipleImages[i]);
                        }
                    }
                }

                Log.d("ImagesArrangementType", controlObject.getImagesArrangementType());

                if (controlObject.getImagesArrangementType() != null) {
                    if (controlObject.getImagesArrangementType().equalsIgnoreCase("Vertical Alignment")) {
                        ll_sliderVertical.setVisibility(View.VISIBLE);
                        mImageSliderVertical(stringListImages, "1");
                    } else if (controlObject.getImagesArrangementType().equalsIgnoreCase("Horizontal Alignment")) {
                        ll_sliderVertical.setVisibility(View.VISIBLE);
                        mImageSliderVertical(stringListImages, "2");
                    } else if (controlObject.getImagesArrangementType().equalsIgnoreCase("Slider")) {
                        rl_sliderHorizontal.setVisibility(View.VISIBLE);
                        mImageSliderHorizontal(stringListImages);
//                    ll_sliderVertical.setVisibility(View.VISIBLE);
//                    mImageSliderVertical(stringListImages, "2");
                    } else if (controlObject.getImagesArrangementType().equalsIgnoreCase("Gallery View")) {
                        Log.d("loadCOGalleryView", stringListImages.get(0));
                        ll_Gallery_View.setVisibility(View.VISIBLE);
                        tv_images_count.setText(String.valueOf(stringListImages.size()));
                        if (isNetworkStatusAvialable(context)) {
                            Glide.with(context).load(stringListImages.get(0)).placeholder(R.drawable.icons_image_update).dontAnimate().into(galleryViewImageView);
                        }
                    }
                }
            }

            if(controlObject.isZoomImageEnable()){
                mainImageView.setVisibility(View.GONE);
                mainImageViewZoom.setVisibility(View.VISIBLE);
            }

//            if (controlObject.getImageDataType() != null) {
//                String strImageDataType = controlObject.getImageDataType();
//                Log.d("ImageDataType: ", strImageDataType);
//                strDisplayUrlImage = controlObject.getImageData();
//                Log.d("ImageDataType: ", strDisplayUrlImage);
//                Glide.with(view.getContext()).load(strDisplayUrlImage)
//                        .placeholder(R.drawable.icons_image_update)
//                        .dontAnimate().into(mainImageView);
//            }



            /*For Sample Slide Images*/
//        imagesList = new ArrayList<>();
//        imagesList.add(context.getResources().getDrawable(R.drawable.sample1));
//        imagesList.add(context.getResources().getDrawable(R.drawable.sample2));
//        imagesList.add(context.getResources().getDrawable(R.drawable.sample3));
//        imagesList.add(context.getResources().getDrawable(R.drawable.sample4));

//        if (controlObject.getImagesArrangementType() != null) {
//            if (controlObject.getImagesArrangementType().equalsIgnoreCase("vertical")) {
//                ll_sliderVertical.setVisibility(View.VISIBLE);
//                mImageSliderVertical();
//            } else {
//                rl_sliderHorizontal.setVisibility(View.VISIBLE);
//                mImageSliderHorizontal();
//            }
//        }
//        setDisplaySettings(context,ce_TextType,controlObject);

/*
            if (controlUIProperties != null && controlUIProperties.getFontColorHex() != null && !controlUIProperties.getFontColorHex().isEmpty()) {
                Log.d(TAG, "setControlValuesHexaColor: " + controlUIProperties.getFontColorHex());
//                controlObject.setTextHexColor(controlUIProperties.getFontColorHex());
            }
            Log.d(TAG, "setDisplaySettings: " + controlObject.getControlName());
            Log.d(TAG, "setDisplaySettings: " + controlObject.getTextHexColor());
            Log.d(TAG, "setDisplaySettings: " + controlObject.getTextColor());
*/

                setDisplaySettings(context, tv_displayName, controlObject);
                setTextSize(controlObject.getTextSize());
                setTextColor(controlObject.getTextHexColor());
                setTextStyle(controlObject.getTextStyle());


            ll_Gallery_View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (stringListImages.size() > 0) {
                        Intent intent = new Intent(context, GalleryViewActivity.class);
                        intent.putExtra("displayName", controlObject.getDisplayName());
                        intent.putStringArrayListExtra("stringListImages", (ArrayList<String>)stringListImages);
//                        intent.putExtra("stringListImages", controlObject.getImageData());
                        context.startActivity(intent);

                    } else {
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                AppConstants.EventFrom_subformOrNot = SubformFlag;
                                if (SubformFlag) {
                                    AppConstants.SF_Container_position = SubformPos;
                                    AppConstants.Current_ClickorChangeTagName = SubformName;
                                }
                                if (AppConstants.GlobalObjects != null) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                }
                                ((MainActivity) context).ChangeEvent(view);
                            }
                        }
                    }
                }
            });

            ll_single_Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                        if (AppConstants.EventCallsFrom == 1) {
                            AppConstants.EventFrom_subformOrNot = SubformFlag;
                            if (SubformFlag) {
                                AppConstants.SF_Container_position = SubformPos;
                                AppConstants.Current_ClickorChangeTagName = SubformName;
//                                AppConstants.Current_ClickorChangeTagName = "variants_subform";
                            }
                            if (AppConstants.GlobalObjects != null) {
                                AppConstants.GlobalObjects.setCurrent_GPS("");
                            }
                            ((MainActivity) context).ChangeEvent(view);
                        }
                    }
                }
            });

            if (controlObject.getDisplayNameAlignment()!=null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("8")) {
                tv_displayName.setPadding(10,0,0,0);
                ll_displayName.setBackground(ContextCompat.getDrawable(context,R.drawable.bottom_left_right_rounded_corners_semi_trans_bg));
            }

        } catch (Exception e) {
            System.out.println("error at image ====" + e);
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }


    public void mImageSliderVertical(List<String> imagesList, String viewType) {
        try {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            if (viewType.equalsIgnoreCase("1")) {
                rv_verticalAlignment.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true));
            } else {
                rv_verticalAlignment.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true));
            }
            VerticalImageSliderAdapter verticalImageSliderAdapter = new VerticalImageSliderAdapter(context, imagesList, viewType);
            rv_verticalAlignment.setAdapter(verticalImageSliderAdapter);
//        rv_verticalAlignment.getLayoutManager().scrollToPosition(0);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "mImageSliderVertical", e);
        }
    }

//    public void mImageHorizontal(List<String> imagesList) {
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        rv_verticalAlignment.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true));
////        rv_verticalAlignment.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true));
//        VerticalImageSliderAdapter verticalImageSliderAdapter = new VerticalImageSliderAdapter(context, imagesList, "2");
//        rv_verticalAlignment.setAdapter(verticalImageSliderAdapter);
//
//    }

    public void mImageSliderHorizontal(List<String> imagesList) {
        Log.d(TAG, "addImageStripIn: " + roundedCorners);
        if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("12")) {
            roundedCorners = true;
        }
        try {
            viewPager = view.findViewById(R.id.viewPagerSlider);
            indicator = view.findViewById(R.id.indicator);


//                colorName = new ArrayList<>();
//                colorName.add("RED");
//                colorName.add("GREEN");
//                colorName.add("BLUE");

//                viewPager.setAdapter(new ImageSliderAdapter(context, color, colorName));
            Log.d(TAG, "mImageSliderHorizontalExce: " + "Check");
            int height = 200;
            if (controlUIProperties != null && !controlUIProperties.getCustomHeightInDP().equalsIgnoreCase("") && controlUIProperties.getCustomHeightInDP() != null) {
                height = Integer.parseInt(controlUIProperties.getCustomHeightInDP());
            }
            viewPager.setAdapter(new ImageSliderAdapter(context, imagesList, roundedCorners, height));
            indicator.setupWithViewPager(viewPager, true);

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);
        } catch (Exception e) {
            Log.d(TAG, "mImageSliderHorizontalExce: " + e);
            ImproveHelper.improveException(context, TAG, "mImageSliderHorizontal", e);
        }
    }

    public CustomTextView setAlertImageView() {

        return ct_alertImageView;
    }

    public void setImageFromSDCard(String strImagePath) {
        try {
            File imgFile = new File(Environment.getExternalStorageDirectory(), strImagePath);
            Log.d(TAG, "setImageFromSDCard: " + imgFile);
            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ImagePath = imgFile.getAbsolutePath();
                mainImageView.setImageBitmap(myBitmap);
                setPath(ImagePath);

            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setImageFromSDCard", e);
        }
    }

    public void removeCardViewPadding() {
        try {
            if (cv_all != null) {
                cv_all.setUseCompatPadding(false);
                cv_all.setForeground(null);
                cv_all.setCardElevation(0);
                cv_all.setRadius(0);
                ll_main_inside.setPadding(0, 0, 0, 0);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "removeCardViewPadding", e);
        }
    }

    public String getImageUrl() {
        controlObject.setText(strDisplayUrlImage);
        return strDisplayUrlImage;
    }

    public int pxToDP(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics());
    }

    @SuppressLint("NewApi")
    public void SetImagesDynamically(List<String> stringListImages) {
        this.stringListImages = stringListImages;


        controlObject.setImageData(String.join(",", stringListImages));

        if (controlObject.isEnableMultipleImages()) {
            if (controlObject.getImagesArrangementType() != null) {
                if (controlObject.getImagesArrangementType().equalsIgnoreCase("Vertical Alignment")) {
                    ll_sliderVertical.setVisibility(View.VISIBLE);
                    mImageSliderVertical(stringListImages, "1");
                } else if (controlObject.getImagesArrangementType().equalsIgnoreCase("Horizontal Alignment")) {
                    ll_sliderVertical.setVisibility(View.VISIBLE);
                    mImageSliderVertical(stringListImages, "2");
                } else if (controlObject.getImagesArrangementType().equalsIgnoreCase("Slider")) {
                    rl_sliderHorizontal.setVisibility(View.VISIBLE);
                    mImageSliderHorizontal(stringListImages);
                } else if (controlObject.getImagesArrangementType().equalsIgnoreCase("Gallery View")) {
                    ll_Gallery_View.setVisibility(View.VISIBLE);
                    tv_images_count.setText(String.valueOf(stringListImages.size()));
                    if (isNetworkStatusAvialable(context)) {
                        Glide.with(context).load(stringListImages.get(0)).placeholder(R.drawable.icons_image_update).dontAnimate().into(galleryViewImageView);
                    }
                }
            }
        } else {
            if (isNetworkStatusAvialable(context)) {
                Glide.with(context).load(stringListImages.get(0)).placeholder(R.drawable.icons_image_update).dontAnimate().into(mainImageView);
                setPath(stringListImages.get(0));
            }
        }
//        ll_Gallery_View.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (stringListImages.size() > 0) {
//                    Intent intent = new Intent(context, GalleryViewActivity.class);
//                    intent.putExtra("displayName", controlObject.getDisplayName());
//                    intent.putExtra("stringListImages", controlObject.getImageData());
//                    context.startActivity(intent);
//                } else {
//                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
//                        if (AppConstants.EventCallsFrom == 1) {
//                            AppConstants.EventFrom_subformOrNot = SubformFlag;
//                            if (SubformFlag) {
//                                AppConstants.SF_Container_position = SubformPos;
//                                AppConstants.Current_ClickorChangeTagName = SubformName;
//                            }
//                            if (AppConstants.GlobalObjects != null) {
//                                AppConstants.GlobalObjects.setCurrent_GPS("");
//                            }
//                            ((MainActivity) context).ChangeEvent(view);
//                        }
//                    }
//                }
//            }
//        });

    }

    @Override
    public String getPath() {
        return controlObject.getImagePath();
    }

    @Override
    public void setPath(String path) {
        controlObject.setImagePath(path);
        Glide.with(context)
                .load(path)
                .thumbnail(0.25f)
                .into(mainImageView);
    }

    @Override
    public boolean getVisibility() {
        controlObject.setInvisible(linearLayout.getVisibility() != View.VISIBLE);
        return controlObject.isInvisible();
    }

    @Override
    public void setVisibility(boolean visibility) {

        if (visibility) {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        }
    }

    @Override
    public boolean isEnabled() {

        return !controlObject.isDisable();
    }

    @Override
    public void setEnabled(boolean enabled) {
//        setViewDisable(view, !enabled);
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

    /*Options*/
    /*hideDisplayName,filePath(pending URL/base64/UploadImage),enableMultipleImages,noOfImages(pending)
    zoomImageEnable
    invisible/visibility
     */
    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        if (hideDisplayName) {
            ll_displayName.setVisibility(View.GONE);
        }
        controlObject.setHideDisplayName(hideDisplayName);
    }

    public String getFilePath() {
        return controlObject.getFilePath();
    }

    public void setFilePath(String filePath) {
        controlObject.setFilePath(filePath);
    }

    public boolean isEnableMultipleImages() {
        return controlObject.isEnableMultipleImages();
    }

    public void setEnableMultipleImages(boolean enableMultipleImages) {
        controlObject.setEnableMultipleImages(enableMultipleImages);
    }

/*
    public String getImageData() {
        return controlObject.getImageData();
    }

    public void setImageData(String imageData) {
        controlObject.setImageData(imageData);
        List<String> listImages = new ArrayList<String>(Arrays.asList(imageData.split(",")));
        SetImagesDynamically(listImages);
*/
/*
        if (controlObject.isEnableMultipleImages()) {
            if (controlObject.getImagesArrangementType() != null) {
                if (controlObject.getImagesArrangementType().equalsIgnoreCase("0")) { //Vertical Alignment
                    ll_sliderVertical.setVisibility(View.VISIBLE);
                    mImageSliderVertical(stringListImages, "1");
                } else if (controlObject.getImagesArrangementType().equalsIgnoreCase("1")) { //Horizontal Alignment
                    ll_sliderVertical.setVisibility(View.VISIBLE);
                    mImageSliderVertical(stringListImages, "2");
                } else if (controlObject.getImagesArrangementType().equalsIgnoreCase("2")) { //Slider
                    rl_sliderHorizontal.setVisibility(View.VISIBLE);
                    mImageSliderHorizontal(stringListImages);
                } else if (controlObject.getImagesArrangementType().equalsIgnoreCase("3")) { //Gallery View
                    ll_Gallery_View.setVisibility(View.VISIBLE);
                    tv_images_count.setText(String.valueOf(stringListImages.size()));
                    if (isNetworkStatusAvialable(context)) {
                        Glide.with(context).load(stringListImages.get(0)).placeholder(R.drawable.icons_image_update).dontAnimate().into(galleryViewImageView);
                    }
                }
            }
        }
*//*

    }
*/

    public boolean isZoomImageEnable() {
        return controlObject.isZoomImageEnable();
    }

    public void setZoomImageEnable(boolean zoomImageEnable) {
        controlObject.setZoomImageEnable(zoomImageEnable);
        if(zoomImageEnable) {
            mainImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity mainActivity = new MainActivity();
                    mainActivity.zoomImage(mainImageView);
                }
            });
        }

    }

    public String getImagesArrangementType() {
        return controlObject.getImagesArrangementType();
    }

    public void setImagesArrangementType(String imagesArrangementType) {
        controlObject.setImagesArrangementType(imagesArrangementType);
        if (controlObject.isEnableMultipleImages()) {
            if (controlObject.getImagesArrangementType() != null) {
                if (controlObject.getImagesArrangementType().equalsIgnoreCase(AppConstants.VERTICAL_ALIGNMENT)|| controlObject.getImagesArrangementType().equalsIgnoreCase("0")) { //Vertical Alignment
                    ll_sliderVertical.setVisibility(View.VISIBLE);
                    rl_sliderHorizontal.setVisibility(View.GONE);
                    ll_Gallery_View.setVisibility(View.GONE);
                    mImageSliderVertical(stringListImages, "1");
                } else if (controlObject.getImagesArrangementType().equalsIgnoreCase(AppConstants.HORIZONTAL_ALIGNMENT)|| controlObject.getImagesArrangementType().equalsIgnoreCase("1")) { //Horizontal Alignment
                    ll_sliderVertical.setVisibility(View.VISIBLE);
                    rl_sliderHorizontal.setVisibility(View.GONE);
                    ll_Gallery_View.setVisibility(View.GONE);
                    mImageSliderVertical(stringListImages, "2");
                } else if (controlObject.getImagesArrangementType().equalsIgnoreCase(AppConstants.SLIDER)|| controlObject.getImagesArrangementType().equalsIgnoreCase("2")) { //Slider
                    rl_sliderHorizontal.setVisibility(View.VISIBLE);
                    ll_sliderVertical.setVisibility(View.GONE);
                    ll_Gallery_View.setVisibility(View.GONE);
                    mImageSliderHorizontal(stringListImages);
                } else if (controlObject.getImagesArrangementType().equalsIgnoreCase(AppConstants.GALLERY_VIEW)|| controlObject.getImagesArrangementType().equalsIgnoreCase("3")) { //Gallery View
                    ll_Gallery_View.setVisibility(View.VISIBLE);
                    ll_sliderVertical.setVisibility(View.GONE);
                    rl_sliderHorizontal.setVisibility(View.GONE);
                    tv_images_count.setText(String.valueOf(stringListImages.size()));
                    if (isNetworkStatusAvialable(context)) {
                        Glide.with(context).load(stringListImages.get(0)).placeholder(R.drawable.icons_image_update).dontAnimate().into(galleryViewImageView);
                    }
                }
            }
        }

    }

    public class SliderTimer extends TimerTask {

        @Override
        public void run() {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < stringListImages.size() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    public class DownloadFileFromURLTask extends AsyncTask<String, String, String> {

        String strFileName;
        File saveFilePath;

        /**
         * Downloading file in background thread
         */


        @SuppressLint("SdCardPath")
        @Override
        protected String doInBackground(String... f_url) {
            Log.i(TAG, "do in background" + f_url[0]);
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                String[] strsplit = f_url[0].split("/");
                strFileName = strsplit[strsplit.length - 1];
                String strFileNameUnderscore = strFileName.replaceAll(" ", "_");
                String strSDCardUrl = AppConstants.SD_CARD_ORG_NAME_FOLDER + sessionManager.getOrgIdFromSession() + "/" + strAppName.replaceAll(" ", "_") + "/";
                File cDir = context.getExternalFilesDir(strSDCardUrl);
                saveFilePath = new File(cDir.getAbsolutePath(), strFileNameUnderscore);
                Log.d(TAG, "setControlValues: " + saveFilePath);
                // Output stream to write file
                OutputStream output = new FileOutputStream(saveFilePath);

                byte[] data = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();


            } catch (Exception e) {

                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @SuppressLint("SdCardPath")
        @Override
        protected void onPostExecute(String file_url) {
            Log.i(TAG, "on post excute!: " + file_url);

            // dismiss progressbars after the file was downloaded
          /*  if (saveFilePath.exists()) {
                ImproveHelper.showToastAlert(context, strFileName + "\n" + " Download successfully!");
            } else {
                ImproveHelper.showToastAlert(context, "No File Exist");
            }*/


        }
    }

    public String getControlValue() {
        return controlObject.getControlValue();
    }

    public void setControlValue(String controlValue) {
        controlObject.setControlValue(controlValue);
        strDisplayUrlImage = controlObject.getControlValue();
        setPath(strDisplayUrlImage);
        Glide.with(context).load(strDisplayUrlImage).placeholder(R.drawable.icons_image_update).dontAnimate().into(mainImageView);
        controlObject.setText(controlValue);
    }

    /*ControlUI SettingsLayouts Start */

    public ImageView getMainImageView() {

        return mainImageView;
    }
//    public CircleImageView getMainImageViewCircle() {
//
//        return mainImageViewCircle;
//    }
    public LinearLayout getLLMainInside() {

        return ll_main_inside;
    }
    public LinearLayout getLl_control_ui() {
        return ll_control_ui;
    }
    public LinearLayout getLayout_control() {
        return layout_control;
    }

    public CustomTextView getTv_displayName() {
        return tv_displayName;
    }

    public LinearLayout getLl_leftRightWeight() {
        return ll_leftRightWeight;
    }
    public LinearLayout getLl_tap_text() {
        return ll_tap_text;
    }
    public LinearLayout getLl_displayName() {
        return ll_displayName;
    }

    public LinearLayout getLl_single_Image() {

        return ll_single_Image;
    }

    /*ControlUI SettingsLayouts End*/

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