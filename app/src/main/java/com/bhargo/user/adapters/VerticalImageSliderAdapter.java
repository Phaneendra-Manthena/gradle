package com.bhargo.user.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.interfaces.ItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

public class VerticalImageSliderAdapter extends RecyclerView.Adapter<VerticalImageSliderAdapter.OperatorsHolder> {

    private static final String TAG = "VerticalImageSliderAdap";
    public String ViewType;
    Context context;
    List<String> verticalImagesList;
    private ItemClickListener clickListener;

    public VerticalImageSliderAdapter(Context context, List<String> verticalImagesList, String ViewType) {
        this.context = context;
        this.verticalImagesList = verticalImagesList;
        this.ViewType = ViewType;

    }

    @NonNull
    @Override
    public VerticalImageSliderAdapter.OperatorsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_vertical_slider, parent, false);
        // set the view's size, margins, paddings and layout_sample_app parameters
        VerticalImageSliderAdapter.OperatorsHolder vh = new VerticalImageSliderAdapter.OperatorsHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalImageSliderAdapter.OperatorsHolder holder, int position) {

        if (ViewType.equalsIgnoreCase("1")) {
            holder.ll_horizontal_type.setVisibility(View.GONE);
            holder.ll_vertical_type.setVisibility(View.VISIBLE);
            Glide.with(context).load(verticalImagesList.get(position))
                    .dontAnimate().into(holder.imageVerticalSlider);
            Log.d(TAG, "onBindViewHolderVI: " + verticalImagesList.get(position));
        } else {

            holder.ll_vertical_type.setVisibility(View.GONE);
            holder.ll_horizontal_type.setVisibility(View.VISIBLE);
            holder.ll_item_horizontal.getLayoutParams().width = 1100;
            Glide.with(context).load(verticalImagesList.get(position))
                    .dontAnimate().into(holder.imageHorizontalSlider);
            Log.d(TAG, "onBindViewHolderHI: " + verticalImagesList.get(position));
        }

//        holder.imageVerticalSlider.setImageDrawable(verticalImagesList.get(position));
    }

    @Override
    public int getItemCount() {
        return verticalImagesList.size();
    }

    public void setCustomClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class OperatorsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout ll_item_horizontal, ll_vertical_type, ll_horizontal_type;
        ImageView imageVerticalSlider, imageHorizontalSlider;

        public OperatorsHolder(@NonNull View itemView) {
            super(itemView);

            ll_item_horizontal = itemView.findViewById(R.id.ll_item_horizontal);
            ll_vertical_type = itemView.findViewById(R.id.ll_vertical_type);
            ll_horizontal_type = itemView.findViewById(R.id.ll_horizontal_type);
            imageVerticalSlider = itemView.findViewById(R.id.imageVerticalSlider);
            imageHorizontalSlider = itemView.findViewById(R.id.imageHorizontalSlider);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.onCustomClick(context, v, getAdapterPosition(), null);
        }
    }

}
