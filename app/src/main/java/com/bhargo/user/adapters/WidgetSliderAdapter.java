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

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.R;
import com.bhargo.user.pojos.DataViewerModelClass;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.bumptech.glide.Glide;

import java.util.List;

public class WidgetSliderAdapter extends PagerAdapter {
    private Context context;
    private List<DataViewerModelClass> imagesList;
    ImproveHelper improveHelper;
    ControlObject controlObject;

    public WidgetSliderAdapter(Context context, List<DataViewerModelClass> imagesList, ControlObject controlObject) {
        this.context = context;
        this.imagesList = imagesList;
        improveHelper = new ImproveHelper(context);
        this.controlObject=controlObject;
    }

    @Override
    public int getCount() {
        return imagesList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_widget_image, null);
        ImageView iv_sliderImageItem =  view.findViewById(R.id.iv_sliderImageItem);
        if(imagesList.get(position).getImage_Path().trim().contentEquals("")){
            imagesList.get(position).setImage_Path(controlObject.getDataViewer_DefualtImage_path());
        }
        Glide.with(context).load(imagesList.get(position).getImage_Path()).into(iv_sliderImageItem);
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
