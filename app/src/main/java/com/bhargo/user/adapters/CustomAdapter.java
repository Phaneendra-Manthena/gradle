package com.bhargo.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bhargo.user.R;

public class CustomAdapter extends BaseAdapter {

    Context context;
    int logos[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, int[] logos) {
        this.context = applicationContext;
        this.logos = logos;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return logos.length;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.dv_grid_item_two_rows, null); // inflate the layout
//        view = inflter.inflate(R.layout.dv_grid_item_three_rows, null); // inflate the layout
//        view = inflter.inflate(R.layout.dv_grid_item_two_rows_call, null); // inflate the layout
//        view = inflter.inflate(R.layout.dv_grid_item_three_rows_call, null); // inflate the layout
//        view = inflter.inflate(R.layout.dv_grid_item_two_rows_video, null); // inflate the layout
//        view = inflter.inflate(R.layout.dv_grid_item_three_rows_video, null); // inflate the layout
//        view = inflter.inflate(R.layout.dv_grid_item_two_rows_call_video, null); // inflate the layout
//        view = inflter.inflate(R.layout.dv_grid_item_three_rows_call_video, null); // inflate the layout
        ImageView icon = (ImageView) view.findViewById(R.id.iv_dv_gitr); // get the reference of ImageView
        icon.setImageResource(logos[i]); // set logo images
        return view;
    }
}