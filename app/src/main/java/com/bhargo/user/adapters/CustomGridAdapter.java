package com.bhargo.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bhargo.user.R;
import com.bhargo.user.pojos.AppIconModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class CustomGridAdapter extends BaseAdapter {

    Context context;
    List<AppIconModel> gridImagesModelList;
    LayoutInflater inflater;

    public CustomGridAdapter(Context context, List<AppIconModel> gridImagesModelList) {
        this.context = context;
        this.gridImagesModelList = gridImagesModelList;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return gridImagesModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_grid_images, null); // inflate the layout
        ImageView icon = convertView.findViewById(R.id.icon); // get the reference of ImageView
//        gridImagesModelList.get(position).

        Glide.with(context).load(gridImagesModelList.get(position).getIcon_Image())
                .placeholder(R.drawable.round_rect_shape)
                .dontAnimate().into(icon)
                .onLoadFailed(context.getDrawable(R.drawable.round_rect_shape));

//        icon.setImageResource(Integer.parseInt(gridImagesModelList.get(position).getIcon_Image()));

//        icon.setImageResource(logos[i]); // set logo images
        return convertView;
    }
}


