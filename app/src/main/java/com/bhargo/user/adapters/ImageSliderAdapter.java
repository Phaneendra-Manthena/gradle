package com.bhargo.user.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bhargo.user.R;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.bumptech.glide.Glide;

import java.util.List;

public class ImageSliderAdapter extends PagerAdapter {
    boolean roundedCorners = false;
    private Context context;
    private List<Integer> color;
    private List<String> imagesList;
    private List<String> colorName;
    int height;
    ImproveHelper improveHelper;

//    public ImageSliderAdapter(Context context, List<Integer> color, List<String> colorName) {
//        this.context = context;
//        this.color = color;
//        this.colorName = colorName;
//    }

    public ImageSliderAdapter(Context context, List<String> imagesList, boolean roundedCorners,int height) {
        this.context = context;
        this.imagesList = imagesList;
        this.roundedCorners = roundedCorners;
        this.height = height;
        improveHelper = new ImproveHelper(context);
    }

    @Override
    public int getCount() {
//        return color.size();
        return imagesList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if (AppConstants.CurrentAppObject.isUIFormNeeded()) {
            if (roundedCorners) {
                view = inflater.inflate(R.layout.item_image_slider_ui_rounded, null);
                Log.d("instantiateItemSlider", "instantiateItemSlider1");
            } else {
                view = inflater.inflate(R.layout.item_image_slider_ui, null);
                Log.d("instantiateItemSlider", "instantiateItemSlider2");
            }
        } else if (roundedCorners) {
            view = inflater.inflate(R.layout.item_image_slider_ui_rounded, null);
            Log.d("instantiateItemSlider", "instantiateItemSlider1");
        } else {
            view = inflater.inflate(R.layout.item_image_slider, null);
            Log.d("instantiateItemSlider", "instantiateItemSlider3");
        }


        ImageView iv_sliderImageItem =  view.findViewById(R.id.iv_sliderImageItem);
        LinearLayout ll_slider =  view.findViewById(R.id.ll_slider);
//        if (AppConstants.CurrentAppObject.isUIFormNeeded() && height!=-2) {
            iv_sliderImageItem.getLayoutParams().height = improveHelper.dpToPx(height);
//            ViewGroup.LayoutParams layoutParamsSlider = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, improveHelper.dpToPx(height));
//            ll_slider.setLayoutParams(layoutParamsSlider);
//        }


//        Glide.with(context).load(imagesList.get(position))
//                .dontAnimate().into(iv_sliderImageItem);

        Glide.with(context).load(imagesList.get(position)).into(iv_sliderImageItem);

//        Glide.with(context).load(imagesList.get(position))
//                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                .dontAnimate().into(iv_sliderImageItem);


//        iv_sliderImageItem.setImageDrawable(imagesList.get(position));
//        linearLayout.setBackgroundColor(color.get(position));

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}