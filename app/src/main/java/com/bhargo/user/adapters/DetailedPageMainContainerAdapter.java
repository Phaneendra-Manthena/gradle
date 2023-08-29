package com.bhargo.user.adapters;

import static com.bhargo.user.utils.AppConstants.VIEW_TYPE_ITEM;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.MainContainerUISettings;
import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;

import java.util.List;

public class DetailedPageMainContainerAdapter extends RecyclerView.Adapter<DetailedPageMainContainerAdapter.ViewHolder> {


    Context context;
    MainContainerUISettings mainContainerUISettings;
    int mainContainerTemplateID;
    List<ControlObject> controlsList;


    public DetailedPageMainContainerAdapter(Context context, int mainContainerTemplateID, MainContainerUISettings mainContainerUISettings, List<ControlObject> controlsList) {
        this.context = context;
        this.mainContainerUISettings = mainContainerUISettings;
        this.mainContainerTemplateID = mainContainerTemplateID;
        this.controlsList = controlsList;

    }

    @NonNull
    @Override
    public DetailedPageMainContainerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = null;
        if (viewType == VIEW_TYPE_ITEM) {
            switch (mainContainerTemplateID) {
                case 1:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detailed_page_main_container_item_one, parent, false);
                    break;
                case 2:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detailed_page_main_container_item_two, parent, false);
                    break;
                case 3:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detailed_page_main_container_item_three, parent, false);
                    break;
                case 4:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detailed_page_main_container_item_four, parent, false);
                    break;
                case 5:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detailed_page_main_container_item_five, parent, false);
                    break;
            }
        }

        // set the view's size, margins, paddings and layout_sample_app parameters
        return new DetailedPageMainContainerAdapter.ViewHolder(v); // pass the view to View Holder

    }

    @Override
    public void onBindViewHolder(@NonNull DetailedPageMainContainerAdapter.ViewHolder holder, int position) {

        holder.tv_display_name.setText(controlsList.get(position).getDisplayName());
        holder.tv_value.setText(controlsList.get(position).getControlValue());
        if ((controlsList.size() - 1) == position && mainContainerTemplateID == 2) {
            holder.iv_divider.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {

        return controlsList.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        CustomTextView tv_display_name, tv_value;
        ImageView iv_divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_display_name = itemView.findViewById(R.id.tv_displayName);
            tv_value = itemView.findViewById(R.id.tv_value);

            if (mainContainerTemplateID == 2) {
                iv_divider = itemView.findViewById(R.id.iv_divider);
            }
            setProperties(itemView, mainContainerTemplateID, mainContainerUISettings, tv_display_name, tv_value);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {


        }

        public void setProperties(View view, int mainContainerStyle, MainContainerUISettings mainContainerUISettings, CustomTextView tvDisplayName, CustomTextView tvValue) {

            switch (mainContainerStyle) {
                case 1:
                    tvDisplayName.setTextSize(mainContainerUISettings.getLabelFontSize());
                    tvDisplayName.setTextColor(Color.parseColor(mainContainerUISettings.getLabelFontColor()));
                    tvValue.setTextSize(mainContainerUISettings.getValueFontSize());
                    tvValue.setTextColor(Color.parseColor(mainContainerUISettings.getValueFontColor()));
                    break;
                case 2:
                    ImageView iv_divider = view.findViewById(R.id.iv_divider);
                    tvDisplayName.setTextSize(mainContainerUISettings.getLabelFontSize());
                    tvDisplayName.setTextColor(Color.parseColor(mainContainerUISettings.getLabelFontColor()));
                    tvValue.setTextSize(mainContainerUISettings.getValueFontSize());
                    tvValue.setTextColor(Color.parseColor(mainContainerUISettings.getValueFontColor()));
                    if (mainContainerUISettings.getBorderStyle().equalsIgnoreCase("Dash")) {//dash
                        GradientDrawable drawableDash = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.index_page_dotted_horizontal);
                        drawableDash.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()),1,5);
                        iv_divider.setBackground(drawableDash);
                    } else if (mainContainerUISettings.getBorderStyle().equalsIgnoreCase("Dotted")) {//dotted
                        GradientDrawable drawableDotted = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.index_page_dotted_horizontal);
                        drawableDotted.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()), 5, 5);
                        iv_divider.setBackground(drawableDotted);
                    } else {//Solid
                        GradientDrawable drawableDotted = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.index_page_dotted_horizontal);
                        drawableDotted.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()), 10, 0);
                        iv_divider.setBackground(drawableDotted);
                    }
/*
                    ImageView iv_divider = view.findViewById(R.id.iv_divider);
                    tvDisplayName.setTextSize(mainContainerUISettings.getLabelFontSize());
                    tvDisplayName.setTextColor(Color.parseColor(mainContainerUISettings.getLabelFontColor()));
                    tvValue.setTextSize(mainContainerUISettings.getValueFontSize());
                    tvValue.setTextColor(Color.parseColor(mainContainerUISettings.getValueFontColor()));
                    if (mainContainerUISettings.getBorderStyle().equalsIgnoreCase("Dash")) {//dash
//                        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.dash_line_bg);
//                        drawable.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()));
//                        iv_divider.setBackground(drawable);
                    } else if (mainContainerUISettings.getBorderStyle().equalsIgnoreCase("Dotted")) {//dotted
//                        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.dotted_line_bg);
//                        drawable.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()));
//                        iv_divider.setBackground(drawable);
                    } else {//Solid
                        iv_divider.setColorFilter(Color.parseColor(mainContainerUISettings.getBorderColor()));
                    }
*/

                    break;
                case 3:
                    LinearLayout layout_item_bg_three = view.findViewById(R.id.layout_item_bg);
                    tvDisplayName.setTextSize(mainContainerUISettings.getLabelFontSize());
                    tvDisplayName.setTextColor(Color.parseColor(mainContainerUISettings.getLabelFontColor()));
                    tvValue.setTextSize(mainContainerUISettings.getValueFontSize());
                    tvValue.setTextColor(Color.parseColor(mainContainerUISettings.getValueFontColor()));
                    if (mainContainerUISettings.getBorderStyle().equalsIgnoreCase("Dash")) {//dash
                        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_bg_dash_border_radius_5);
                        drawable.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()));
                        drawable.setColor(Color.parseColor(mainContainerUISettings.getBackgroundColor()));
                        layout_item_bg_three.setBackground(drawable);
                    } else if (mainContainerUISettings.getBorderStyle().equalsIgnoreCase("Dotted")) {//dotted
                        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_bg_dotted_border_radius_5);
                        drawable.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()));
                        drawable.setColor(Color.parseColor(mainContainerUISettings.getBackgroundColor()));
                        layout_item_bg_three.setBackground(drawable);
                    } else {//solid
                        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_bg_radius_5);
                        drawable.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()));
                        drawable.setColor(Color.parseColor(mainContainerUISettings.getBackgroundColor()));
                        layout_item_bg_three.setBackground(drawable);
                    }
                    break;
                case 4:
                    LinearLayout layout_item_bg_four = view.findViewById(R.id.layout_item_bg);
                    tvDisplayName.setTextSize(mainContainerUISettings.getLabelFontSize());
                    tvDisplayName.setTextColor(Color.parseColor(mainContainerUISettings.getLabelFontColor()));
                    if (mainContainerUISettings.getAlignment().equalsIgnoreCase("LEFT")) {
                        tvDisplayName.setGravity(Gravity.START);
                        tvValue.setGravity(Gravity.START);
                    } else {
                        tvDisplayName.setGravity(Gravity.CENTER);
                        tvValue.setGravity(Gravity.CENTER);
                    }
                    tvValue.setTextSize(mainContainerUISettings.getValueFontSize());
                    tvValue.setTextColor(Color.parseColor(mainContainerUISettings.getValueFontColor()));
                    if (mainContainerUISettings.getBorderStyle().equalsIgnoreCase("Dash")) {//dash
                        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_bg_dash_border_radius_5);
                        drawable.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()));
                        drawable.setColor(Color.parseColor(mainContainerUISettings.getBackgroundColor()));
                        layout_item_bg_four.setBackground(drawable);
                    } else if (mainContainerUISettings.getBorderStyle().equalsIgnoreCase("Dotted")) {//dotted
                        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_bg_dotted_border_radius_5);
                        drawable.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()));
                        drawable.setColor(Color.parseColor(mainContainerUISettings.getBackgroundColor()));
                        layout_item_bg_four.setBackground(drawable);
                    } else {//Solid
                        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_bg_radius_5);
                        drawable.setStroke(1, Color.parseColor(mainContainerUISettings.getBorderColor()));
                        drawable.setColor(Color.parseColor(mainContainerUISettings.getBackgroundColor()));
                        layout_item_bg_four.setBackground(drawable);
                    }
                    break;
                case 5:
                    LinearLayout layout_item_bg_five = view.findViewById(R.id.layout_item_bg);
                    LinearLayout layout_active_bg = view.findViewById(R.id.layout_active_bg);
                    tvDisplayName.setTextSize(mainContainerUISettings.getLabelFontSize());
                    tvDisplayName.setTextColor(Color.parseColor(mainContainerUISettings.getLabelFontColor()));
                    tvValue.setTextSize(mainContainerUISettings.getValueFontSize());
                    tvValue.setTextColor(Color.parseColor(mainContainerUISettings.getValueFontColor()));
                    GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_bg_radius_5);
                    drawable.setColor(Color.parseColor(mainContainerUISettings.getBackgroundColor()));
                    layout_item_bg_five.setBackground(drawable);
                    GradientDrawable drawableActiveBG = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_bg_radius_5_primary_color);
                    drawableActiveBG.setColor(Color.parseColor(mainContainerUISettings.getActiveColor()));
                    layout_active_bg.setBackground(drawableActiveBG);
                    break;
            }

        }
    }

}