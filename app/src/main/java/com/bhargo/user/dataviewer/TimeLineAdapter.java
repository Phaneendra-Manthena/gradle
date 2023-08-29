package com.bhargo.user.dataviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.MyViewHolder> {

    private static final String TAG = "TimeLineAdapter";

    private final Context context;

    public TimeLineAdapter(Context context) {
        this.context = context;

    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_timeline_item, parent, false);
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_timeline_point_default, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_timeline_profile_default, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        CustomTextView desc1 = new CustomTextView(context);
        desc1.setText("Line 1");
        desc1.setCustomFont(context, context.getResources().getString(R.string.font_satoshi));
        desc1.setTextSize(context.getResources().getDimension(R.dimen._3sdp));
        CustomTextView desc2 = new CustomTextView(context);
        desc2.setText("Line 2");
        desc2.setCustomFont(context, context.getResources().getString(R.string.font_satoshi));
        desc2.setTextSize(context.getResources().getDimension(R.dimen._3sdp));
        CustomTextView desc3 = new CustomTextView(context);
        desc3.setText("Line 3");
        desc3.setCustomFont(context, context.getResources().getString(R.string.font_satoshi));
        desc3.setTextSize(context.getResources().getDimension(R.dimen._3sdp));
        CustomTextView desc4 = new CustomTextView(context);
        desc4.setText("Line 4");
        desc4.setCustomFont(context, context.getResources().getString(R.string.font_satoshi));
        desc4.setTextSize(context.getResources().getDimension(R.dimen._3sdp));

        holder.ll_desc.addView(desc1);
        holder.ll_desc.addView(desc2);
        holder.ll_desc.addView(desc3);
        holder.ll_desc.addView(desc4);
        if (position == 0) {
            holder.iv_dv_gitr.setImageDrawable(context.getDrawable(R.drawable.ic_time_line_success_default));
        } else if (position == 1) {
            holder.iv_dv_gitr.setImageDrawable(context.getDrawable(R.drawable.allapps));
        } else if (position == 2) {
            holder.view_verticalLine.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final CustomTextView tv_timeline_header;
        private final CustomTextView tv_timeline_date;
        private final LinearLayout ll_desc;
        private final View view_verticalLine;
        private final ImageView iv_dv_gitr;


        public MyViewHolder(View itemView) {
            super(itemView);

            iv_dv_gitr = itemView.findViewById(R.id.iv_dv_gitr);
            tv_timeline_header = itemView.findViewById(R.id.tv_timeline_header);
            tv_timeline_date = itemView.findViewById(R.id.tv_timeline_date);
            ll_desc = itemView.findViewById(R.id.ll_Description);
            view_verticalLine = itemView.findViewById(R.id.view_verticalLine);
        }


    }

}
