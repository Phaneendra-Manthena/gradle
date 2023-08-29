package com.bhargo.user.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.ImageAdvanced_Mapped_Item;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.ItemClickListener;
import com.bhargo.user.pojos.DataViewerModelClass;
import com.bhargo.user.screens.VideoPlayerActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RoundedImageView;
import com.bhargo.user.utils.RoundishImageViewTopLeftRight;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

import static com.bhargo.user.utils.AppConstants.VIEW_TYPE_ITEM;
import static com.bhargo.user.utils.AppConstants.VIEW_TYPE_LOADING;

import de.hdodenhof.circleimageview.CircleImageView;

public class DataViewerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private static final String TAG = "DataViewerAdapter";
    Context context;
    List<DataViewerModelClass> dataViewerModelClassList, dataViewerModelClassDefaultList;
    DataViewerModelClass dataViewerModelClass;
    String UI_Pattern;
    ControlObject controlObject;
    String headerColor = "#000000";
    String ratingColor = "#000000";
    String itemTextColor = "#000000";
    String subHeaderColor = "#000000";
    String descriptionColor = "#707b8a";
    String dateandtimeColor = "#000000";
    DataViewerModelClass SelectedDataViewerModelClass = null;
    ImproveHelper improveHelper;
    CustomTextView tv_NoData;
    String typeOfSearch;
    private GoogleMap gMap;
    private ItemClickListener clickListener;
    private int selectedItemPositionInAdapter;
//Widget
    public DataViewerAdapter(Context context, List<DataViewerModelClass> dataViewerModelClassList, ControlObject controlObject, CustomTextView tv_NoData) {
        this.context = context;
        improveHelper = new ImproveHelper(context);
        this.tv_NoData = tv_NoData;
        this.dataViewerModelClassList = dataViewerModelClassList;
        this.dataViewerModelClassDefaultList = dataViewerModelClassList;
        this.controlObject = controlObject;
        this.UI_Pattern = controlObject.getDataViewer_UI_Pattern();
        if (controlObject.getDataViewer_Header_HexColor() != null && controlObject.getDataViewer_Header_HexColor().contains("#")) {
            headerColor = controlObject.getDataViewer_Header_HexColor();
        }
        if (controlObject.getDataViewer_SubHeader_HexColor() != null && controlObject.getDataViewer_SubHeader_HexColor().contains("#")) {
            subHeaderColor = controlObject.getDataViewer_SubHeader_HexColor();
        }
        if (controlObject.getDataViewer_Description_HexColor() != null && controlObject.getDataViewer_Description_HexColor().contains("#")) {
            descriptionColor = controlObject.getDataViewer_Description_HexColor();
        }
        if (controlObject.getDataViewer_DateTime_HexColor() != null && controlObject.getDataViewer_DateTime_HexColor().contains("#")) {
            dateandtimeColor = controlObject.getDataViewer_DateTime_HexColor();
        }
        if (controlObject.getDataViewer_Item_BGHexColor() != null && controlObject.getDataViewer_Item_BGHexColor().contains("#")) {
            ratingColor = controlObject.getDataViewer_Item_BGHexColor();
        }
        if (controlObject.getDataViewer_Item_HexColor() != null && controlObject.getDataViewer_Item_HexColor().contains("#")) {
            itemTextColor = controlObject.getDataViewer_Item_HexColor();
        }
//        String descColor =  null;
//        if(controlObject.getDataViewer_Description_HexColor() !=null && controlObject.getDataViewer_Description_HexColor().isEmpty()){
//            descColor = controlObject.getDataViewer_Description_HexColor();
//        }

        setTypeofSearch();
    }

    private void setTypeofSearch() {
        if (controlObject.isDataViewer_HeaderSearchEnabled() &&
                controlObject.isDataViewer_SubHeaderSearchEnabled() &&
                controlObject.isDataViewer_DescriptionSearchEnabled() &&
                controlObject.isDataViewer_CornerSearchEnabled()) {
            typeOfSearch = "HSDC"; //   Header,SubHeader,Description,Corner
        } else if (controlObject.isDataViewer_HeaderSearchEnabled() &&
                controlObject.isDataViewer_SubHeaderSearchEnabled() &&
                controlObject.isDataViewer_DescriptionSearchEnabled()) {
            typeOfSearch = "HSD";
        } else if (controlObject.isDataViewer_HeaderSearchEnabled() &&
                controlObject.isDataViewer_SubHeaderSearchEnabled() &&
                controlObject.isDataViewer_CornerSearchEnabled()) {
            typeOfSearch = "HSC";
        } else if (controlObject.isDataViewer_HeaderSearchEnabled() &&
                controlObject.isDataViewer_DescriptionSearchEnabled() &&
                controlObject.isDataViewer_CornerSearchEnabled()) {
            typeOfSearch = "HDC";
        } else if (controlObject.isDataViewer_HeaderSearchEnabled() &&
                controlObject.isDataViewer_SubHeaderSearchEnabled()) {
            typeOfSearch = "HS";
        } else if (controlObject.isDataViewer_HeaderSearchEnabled() &&
                controlObject.isDataViewer_DescriptionSearchEnabled()) {
            typeOfSearch = "HD";
        } else if (controlObject.isDataViewer_HeaderSearchEnabled() &&
                controlObject.isDataViewer_CornerSearchEnabled()) {
            typeOfSearch = "HC";
        } else if (controlObject.isDataViewer_HeaderSearchEnabled()) {
            typeOfSearch = "H";
        } else if (controlObject.isDataViewer_SubHeaderSearchEnabled() &&
                controlObject.isDataViewer_DescriptionSearchEnabled() &&
                controlObject.isDataViewer_CornerSearchEnabled()) {
            typeOfSearch = "SDC"; //   SubHeader,Description,Corner
        } else if (controlObject.isDataViewer_SubHeaderSearchEnabled() &&
                controlObject.isDataViewer_DescriptionSearchEnabled()) {
            typeOfSearch = "SD";
        } else if (controlObject.isDataViewer_SubHeaderSearchEnabled() &&
                controlObject.isDataViewer_CornerSearchEnabled()) {
            typeOfSearch = "SC";
        } else if (controlObject.isDataViewer_SubHeaderSearchEnabled()) {
            typeOfSearch = "S";
        } else if (controlObject.isDataViewer_DescriptionSearchEnabled() &&
                controlObject.isDataViewer_CornerSearchEnabled()) {
            typeOfSearch = "DC"; //   Description,Corner
        } else if (controlObject.isDataViewer_DescriptionSearchEnabled()) {
            typeOfSearch = "D";
        } else if (controlObject.isDataViewer_CornerSearchEnabled()) {
            typeOfSearch = "C"; //   Corner
        }
    }

    public void setCustomClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public void deleteRow(int position){
        dataViewerModelClassList.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null ;
        if(viewType==VIEW_TYPE_ITEM) {
            switch (UI_Pattern) {
                case AppConstants.GridView_With_Image_2_Columns:
//                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_grid_item_two_rows, parent, false);
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_gridview_with_image_two_columns_default, parent, false);
                    break;
                case AppConstants.GridView_With_Image_3_Columns:
//                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_grid_item_three_rows, parent, false);
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_gridview_with_image_three_columns_default, parent, false);
                    break;
                case AppConstants.GridView_With_Image_2_Columns_call:
//                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_grid_item_two_rows_call, parent, false);
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_gridview_with_image_two_columns_call_default, parent, false);
                    break;
                case AppConstants.GridView_With_Image_3_Columns_call:
//                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_grid_item_three_rows_call, parent, false);
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_gridview_with_image_three_columns_call_default, parent, false);
                    break;
                case AppConstants.GridView_With_Video_2_Columns:
//                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_grid_item_two_rows_video, parent, false);
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_grid_item_two_rows_video_default, parent, false);
                    break;
                case AppConstants.GridView_With_Video_3_Columns:
//                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_grid_item_three_rows_video, parent, false);
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_grid_item_three_rows_video_default, parent, false);
                    break;
                case AppConstants.GridView_With_Video_2_Columns_call:
//                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_grid_item_two_rows_call_video, parent, false);
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_grid_item_two_rows_call_video_default, parent, false);
                    break;
                case AppConstants.GridView_With_Video_3_Columns_call:
//                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_grid_item_three_rows_call_video, parent, false);
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_grid_item_three_rows_call_video_default, parent, false);
                    break;
                case AppConstants.ListView_2_Columns:
//                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_list_item_two_rows, parent, false);
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_listview_two_columns_default, parent, false);
                    break;
                case AppConstants.ListView_With_Image_2_Columns:
//                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_list_item_two_rows_image, parent, false);
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_listview_with_image_two_columns_default, parent, false);
                    break;
                case AppConstants.ListView_With_Image_3_Columns:
//                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_list_item_two_rows_image_center, parent, false);
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_with_image_three_columns_default, parent, false);
                    break;
                case AppConstants.ListView_With_Image_3_Columns_call:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_list_item_three_rows_image_center_call, parent, false);
                    break;
                case AppConstants.MapView_Item_Info:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_listitem_map_item_info, parent, false);
                    break;
                case AppConstants.Geo_Spatial_View:
//                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_geo_spatial_view, parent, false);
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_geo_spatial_view_default, parent, false);
                    break;
                case AppConstants.TimeLine_View:
//                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_timeline_point, parent, false);
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_timeline_point_default, parent, false);
                    break;
                case AppConstants.TimeLine_With_Photo_View:
//                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_timeline_profile, parent, false);
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_timeline_profile_default, parent, false);
                    break;
                case AppConstants.LinearView_With_Video:
//                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_linear_item_two_rows_video, parent, false);
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_linear_item_two_rows_video_default, parent, false);
                    break;
                case AppConstants.BlogSpot_View:
//                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_linear_blog_post, parent, false);
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_linear_blog_post_default, parent, false);
                    break;
                case AppConstants.EV_Dashboard_Design_One:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_list_item_ev_one, parent, false);
                    break;
                case AppConstants.EV_Dashboard_Design_Two:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_list_item_ev_two, parent, false);
                    break;
                case AppConstants.EV_Dashboard_Design_Three:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_list_item_ev_three, parent, false);
                    break;
                case AppConstants.EV_News_Design:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_list_item_news_design, parent, false);
                    break;
                case AppConstants.EV_Notifications_Design:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_list_item_notification, parent, false);
                    break;
                case AppConstants.EV_Dealers_Design:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_list_item_dealers, parent, false);
                    break;
                case AppConstants.EV_Jobs_Design:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_list_item_jobs_design, parent, false);
                    break;
                case AppConstants.Button_Group:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_button_group, parent, false);
                    break;
                case AppConstants.GRID_WITH_TWO_COLUMNS:// ev stations accessories
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dv_grid_with_two_columns, parent, false);
                    break;
                case AppConstants.WIDGET_1:// ev stations accessories
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_widget_one, parent, false);
                    break;
                case AppConstants.WIDGET_2:// ev stations accessories
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_widget_two, parent, false);
                    break;
                case AppConstants.WIDGET_3:// ev stations accessories
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_widget_three, parent, false);
                    break;
                case AppConstants.WIDGET_4:// ev stations accessories
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_widget_four, parent, false);
                    break;
                case AppConstants.WIDGET_5:// ev stations accessories
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_widget_five, parent, false);
                    break;
                case AppConstants.WIDGET_6:// ev stations accessories
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_widget_six, parent, false);
                    break;
                case AppConstants.WIDGET_7:// ev stations accessories
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_widget_seven, parent, false);
                    break;
                case AppConstants.WIDGET_8:// ev stations accessories
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_widget_eight, parent, false);
                    break;
                case AppConstants.WIDGET_9:// ev stations accessories
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_widget_nine, parent, false);
                    break;
                case AppConstants.WIDGET_10:// ev stations accessories
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_widget_ten, parent, false);
                    break;
            }

            // set the view's size, margins, paddings and layout_sample_app parameters
            return new MyViewHolder(v); // pass the view to View Holder
        } else if (viewType == VIEW_TYPE_LOADING) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(v);
        }

        return new MyViewHolder(v);
        /* return vh;*/
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        List<String> list_description;
        try {
            if (holder instanceof MyViewHolder) {

                populateItems((MyViewHolder) holder, position);

            } else if (holder instanceof LoadingViewHolder) {

                showLoadingView((LoadingViewHolder) holder, position);

            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "onBindViewHolder", e);
        }
    }

    private void populateItems(MyViewHolder holder, int position) {
        try{
            List<String> list_description;
            if(holder.ll_Description!=null) {
                holder.ll_Description.removeAllViews();
            }

            // FrameBackground
            String strFrameBg =  null;
            if(controlObject.getDataViewer_FrameBG_HexColor() !=null && !controlObject.getDataViewer_FrameBG_HexColor().isEmpty()){
                strFrameBg = controlObject.getDataViewer_FrameBG_HexColor();
                if(holder.ll_item_main != null && holder.ll_item_main.getBackground() != null) {
                    GradientDrawable drawable = (GradientDrawable) holder.ll_item_main.getBackground();
                    drawable.mutate();
                    drawable.setColor(Color.parseColor(strFrameBg));
                }
            }
            if(controlObject.getDataViewer_FrameBG_HexColor() !=null && !controlObject.getDataViewer_FrameBG_HexColor().isEmpty()){
                strFrameBg = controlObject.getDataViewer_FrameBG_HexColor();
                if(holder.rl_dv_gitr != null && holder.rl_dv_gitr.getBackground() != null) {
                    GradientDrawable drawable = (GradientDrawable) holder.rl_dv_gitr.getBackground();
                    drawable.mutate();
                    drawable.setColor(Color.parseColor(strFrameBg));
                }
            }

            switch (UI_Pattern) {
                case AppConstants.GridView_With_Image_2_Columns:
                    if (dataViewerModelClassList.get(position).getImage_Path() != null) {
                        ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getImage_Path(), holder.icon, false, controlObject.getDataViewer_DefualtImage_path());
                    } else {
                        if (dataViewerModelClassList.get(position).getAdvanceImage_Source() != null &&
                                dataViewerModelClassList.get(position).getAdvanceImage_Source().length() != 0) {
                            String Conditionvalue = dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClassList.get(position).getList_Image_Path();
                            for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                                if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)) {
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.icon, false, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath());
                                    break;
                                } else {
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.icon, false, "null");
                                    break;
                                }
                            }
                        } else {
                            String Conditionvalue = dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClassList.get(position).getList_Image_Path();
                            for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                                if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)) {
                                    ImproveHelper.loadImage_new(context, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath(), holder.icon, false, controlObject.getDataViewer_DefualtImage_path());
                                }
                            }
                        }
                    }

               /* if(controlObject.getDataViewer_Shape()!=null&&!controlObject.getDataViewer_Shape().equalsIgnoreCase("None")&&dataViewerModelClassList.get(position).getCornerText()!=null&&dataViewerModelClassList.get(position).getCornerText().length()>0) {
                    holder.ctv_corner.setText(dataViewerModelClassList.get(position).getCornerText());
                }else{
                    holder.ctv_corner.setVisibility(View.GONE);
                }*/
                    if (dataViewerModelClassList.get(position).getCornerText() != null && dataViewerModelClassList.get(position).getCornerText().length() > 0) {
                        holder.ctv_corner.setText(dataViewerModelClassList.get(position).getCornerText());
                    } else {
                        holder.ctv_corner.setVisibility(View.GONE);
                    }

                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    list_description = dataViewerModelClassList.get(position).getDescription();
                    TypedValue typedValue = new TypedValue();
                    Resources.Theme theme = context.getTheme();
                    theme.resolveAttribute(R.attr.bhargo_color_eleven, typedValue, true);
                    @ColorInt int color = typedValue.data;
                    String hexColor = "#" + Integer.toHexString(color);
                    for (int i = 0; i < list_description.size(); i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
//                        tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
                        if(descriptionColor !=null && !descriptionColor.isEmpty()){
                            tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        }else {
                            tv_desc.setTextColor(ColorStateList.valueOf(Color.parseColor(hexColor)));
                        }
                        tv_desc.setTextSize(12);
                        if (list_description.get(i).length() > 30) {
                            tv_desc.setText(list_description.get(i).substring(0, 30) + "...");
                        } else {
                            tv_desc.setText(list_description.get(i));
                        }
                        holder.ll_Description.addView(tv_desc);
                    }
                    break;
                case AppConstants.GridView_With_Image_3_Columns:
                    if (dataViewerModelClassList.get(position).getImage_Path() != null) {
                        ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getImage_Path(), holder.icon, false, controlObject.getDataViewer_DefualtImage_path());
                    } else {
                        if (dataViewerModelClassList.get(position).getAdvanceImage_Source() != null &&
                                dataViewerModelClassList.get(position).getAdvanceImage_Source().length() != 0) {
                            String Conditionvalue = dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClassList.get(position).getList_Image_Path();
                            for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                                if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)) {
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.icon, false, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath());
                                    break;
                                } else {
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.icon, false, "null");
                                    break;
                                }
                            }
                        } else {
                            String Conditionvalue = dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClassList.get(position).getList_Image_Path();
                            for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                                if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)) {
                                    ImproveHelper.loadImage_new(context, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath(), holder.icon, false, controlObject.getDataViewer_DefualtImage_path());
                                }
                            }
                        }
                    }

             /*   if(controlObject.getDataViewer_Shape()!=null&&!controlObject.getDataViewer_Shape().equalsIgnoreCase("None")&&dataViewerModelClassList.get(position).getCornerText()!=null&&dataViewerModelClassList.get(position).getCornerText().length()>0) {
                    holder.ctv_corner.setText(dataViewerModelClassList.get(position).getCornerText());
                }else{
                    holder.ctv_corner.setVisibility(View.GONE);
                }*/

                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tvSubHeading.setTextColor(Color.parseColor(subHeaderColor));
                    list_description = dataViewerModelClassList.get(position).getDescription();
                    for (int i = 0; i < list_description.size(); i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                        if(descriptionColor !=null && !descriptionColor.isEmpty()){
                            tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        }else {
                            tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
//                            tv_desc.setTextColor(ColorStateList.valueOf(Color.parseColor(hexColor)));
                        }
//                        tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
                        tv_desc.setTextSize(12);

                        if (list_description.get(i).length() > 30) {
                            tv_desc.setText(list_description.get(i).substring(0, 30) + "...");
                        } else {
                            tv_desc.setText(list_description.get(i));
                        }
                        holder.ll_Description.addView(tv_desc);
                    }
                    if (dataViewerModelClassList.get(position).getCornerText() != null && dataViewerModelClassList.get(position).getCornerText().length() > 0) {
                        holder.ctv_corner.setText(dataViewerModelClassList.get(position).getCornerText());
                    } else {
                        holder.ctv_corner.setVisibility(View.GONE);
                    }
                    break;
                case AppConstants.GridView_With_Image_2_Columns_call:
                    if (dataViewerModelClassList.get(position).getImage_Path() != null) {
                        ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getImage_Path(), holder.icon, false, controlObject.getDataViewer_DefualtImage_path());
                    } else {
                        if (dataViewerModelClassList.get(position).getAdvanceImage_Source() != null &&
                                dataViewerModelClassList.get(position).getAdvanceImage_Source().length() != 0) {
                            String Conditionvalue = dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClassList.get(position).getList_Image_Path();
                            for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                                if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)) {
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.icon, false, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath());
                                    break;
                                } else {
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.icon, false, "null");
                                    break;
                                }
                            }
                        } else {
                            String Conditionvalue = dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClassList.get(position).getList_Image_Path();
                            for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                                if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)) {
                                    ImproveHelper.loadImage_new(context, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath(), holder.icon, false, controlObject.getDataViewer_DefualtImage_path());
                                }
                            }
                        }
                    }
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    list_description = dataViewerModelClassList.get(position).getDescription();
                    for (int i = 0; i < list_description.size(); i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                        tv_desc.setText(list_description.get(i));
                        if(descriptionColor !=null && !descriptionColor.isEmpty()){
                            tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        }else {
                            tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
//                            tv_desc.setTextColor(ColorStateList.valueOf(Color.parseColor(hexColor)));
                        }

//                        tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
                        tv_desc.setTextSize(12);
                        if (list_description.get(i).length() > 30) {
                            tv_desc.setText(list_description.get(i).substring(0, 30) + "...");
                        } else {
                            tv_desc.setText(list_description.get(i));
                        }
                        holder.ll_Description.addView(tv_desc);
                    }
                    if (dataViewerModelClassList.get(position).getCornerText() != null && dataViewerModelClassList.get(position).getCornerText().length() > 0) {
                        holder.ctv_corner.setText(dataViewerModelClassList.get(position).getCornerText());
                    } else {
                        holder.ctv_corner.setVisibility(View.GONE);
                    }
                    break;
                case AppConstants.GridView_With_Image_3_Columns_call:
                    if (dataViewerModelClassList.get(position).getImage_Path() != null) {
                        ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getImage_Path(), holder.icon, false, controlObject.getDataViewer_DefualtImage_path());
                    } else {
                        if (dataViewerModelClassList.get(position).getAdvanceImage_Source() != null &&
                                dataViewerModelClassList.get(position).getAdvanceImage_Source().length() != 0) {
                            String Conditionvalue = dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClassList.get(position).getList_Image_Path();
                            for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                                if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)) {
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.icon, false, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath());
                                    break;
                                } else {
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.icon, false, "null");
                                    break;
                                }
                            }
                        } else {
                            String Conditionvalue = dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClassList.get(position).getList_Image_Path();
                            if (list_IA_Map_Item != null) {
                                for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                                    if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)) {
                                        ImproveHelper.loadImage_new(context, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath(), holder.icon, false, controlObject.getDataViewer_DefualtImage_path());
                                    }
                                }
                            }
                        }
                    }
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tvSubHeading.setTextColor(Color.parseColor(subHeaderColor));
                    list_description = dataViewerModelClassList.get(position).getDescription();
                    for (int i = 0; i < list_description.size(); i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                        tv_desc.setText(list_description.get(i));
                        if(descriptionColor !=null && !descriptionColor.isEmpty()){
                            tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        }else {
                            tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
//                            tv_desc.setTextColor(ColorStateList.valueOf(Color.parseColor(hexColor)));
                        }
//                        tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
                        tv_desc.setTextSize(12);
                        if (list_description.get(i).length() > 20) {
                            tv_desc.setText(list_description.get(i).substring(0, 20) + "...");
                        } else {
                            tv_desc.setText(list_description.get(i));
                        }

                        holder.ll_Description.addView(tv_desc);
                    }
                    if (dataViewerModelClassList.get(position).getCornerText() != null && dataViewerModelClassList.get(position).getCornerText().length() > 0) {
                        holder.ctv_corner.setText(dataViewerModelClassList.get(position).getCornerText());
                    } else {
                        holder.ctv_corner.setVisibility(View.GONE);
                    }
                    break;
                case AppConstants.GridView_With_Video_2_Columns:
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.icon.setTag(dataViewerModelClassList.get(position).getVideo_Path());
                    holder.icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(view.getTag() != null && !view.getTag().toString().isEmpty()) {
                                Intent intent = new Intent(context, VideoPlayerActivity.class);
                                intent.putExtra("URL", view.getTag().toString());
                                context.startActivity(intent);
                            }
                        }
                    });

                    list_description = dataViewerModelClassList.get(position).getDescription();
                    for (int i = 0; i < list_description.size(); i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                        tv_desc.setText(list_description.get(i));
                        if(descriptionColor !=null && !descriptionColor.isEmpty()){
                            tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        }else {
                            tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
//                            tv_desc.setTextColor(ColorStateList.valueOf(Color.parseColor(hexColor)));
                        }
//                        tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        tv_desc.setTextSize(12);
                        if (list_description.get(i).length() > 100) {
                            tv_desc.setText(list_description.get(i).substring(0, 100) + "...");
                        } else {
                            tv_desc.setText(list_description.get(i));
                        }
                        holder.ll_Description.addView(tv_desc);
                    }
                    if (dataViewerModelClassList.get(position).getCornerText() != null && dataViewerModelClassList.get(position).getCornerText().length() > 0) {
                        holder.ctv_corner.setText(dataViewerModelClassList.get(position).getCornerText());
                    } else {
                        holder.ctv_corner.setVisibility(View.GONE);
                    }
                    break;
                case AppConstants.GridView_With_Video_3_Columns:
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tvSubHeading.setTextColor(Color.parseColor(subHeaderColor));
                    holder.icon.setTag(dataViewerModelClassList.get(position).getVideo_Path());
                    holder.icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(view.getTag() != null && !view.getTag().toString().isEmpty()) {
                                Intent intent = new Intent(context, VideoPlayerActivity.class);
                                intent.putExtra("URL", view.getTag().toString());
                                context.startActivity(intent);
                            }
                        }
                    });
                    list_description = dataViewerModelClassList.get(position).getDescription();
                    for (int i = 0; i < list_description.size(); i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                        tv_desc.setText(list_description.get(i));
                        if(descriptionColor !=null && !descriptionColor.isEmpty()){
                            tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        }else {
                            tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
//                            tv_desc.setTextColor(ColorStateList.valueOf(Color.parseColor(hexColor)));
                        }
//                        tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        tv_desc.setTextSize(12);
                        if (list_description.get(i).length() > 80) {
                            tv_desc.setText(list_description.get(i).substring(0, 80) + "...");
                        } else {
                            tv_desc.setText(list_description.get(i));
                        }
                        holder.ll_Description.addView(tv_desc);
                    }
                    if (dataViewerModelClassList.get(position).getCornerText() != null && dataViewerModelClassList.get(position).getCornerText().length() > 0) {
                        holder.ctv_corner.setText(dataViewerModelClassList.get(position).getCornerText());
                    } else {
                        holder.ctv_corner.setVisibility(View.GONE);
                    }
                    break;
                case AppConstants.GridView_With_Video_2_Columns_call:
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    list_description = dataViewerModelClassList.get(position).getDescription();
                    holder.icon.setTag(dataViewerModelClassList.get(position).getVideo_Path());
                    holder.icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(view.getTag() != null && !view.getTag().toString().isEmpty()) {
                                Intent intent = new Intent(context, VideoPlayerActivity.class);
                                intent.putExtra("URL", view.getTag().toString());
                                context.startActivity(intent);
                            }
                        }
                    });
                    for (int i = 0; i < list_description.size(); i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                        tv_desc.setText(list_description.get(i));
                        if(descriptionColor !=null && !descriptionColor.isEmpty()){
                            tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        }else {
                            tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
//                            tv_desc.setTextColor(ColorStateList.valueOf(Color.parseColor(hexColor)));
                        }
//                        tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        tv_desc.setTextSize(12);
                        if (list_description.get(i).length() > 50) {
                            tv_desc.setText(list_description.get(i).substring(0, 50) + "...");
                        } else {
                            tv_desc.setText(list_description.get(i));
                        }
                        holder.ll_Description.addView(tv_desc);
                    }
                    if (dataViewerModelClassList.get(position).getCornerText() != null && dataViewerModelClassList.get(position).getCornerText().length() > 0) {
                        holder.ctv_corner.setText(dataViewerModelClassList.get(position).getCornerText());
                    } else {
                        holder.ctv_corner.setVisibility(View.GONE);
                    }
                    break;
                case AppConstants.GridView_With_Video_3_Columns_call:
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tvSubHeading.setTextColor(Color.parseColor(subHeaderColor));
                    holder.icon.setTag(dataViewerModelClassList.get(position).getVideo_Path());
                    holder.icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(view.getTag() != null && !view.getTag().toString().isEmpty()) {
                                Intent intent = new Intent(context, VideoPlayerActivity.class);
                                intent.putExtra("URL", view.getTag().toString());
                                context.startActivity(intent);
                            }
                        }
                    });
                    list_description = dataViewerModelClassList.get(position).getDescription();
                    for (int i = 0; i < list_description.size(); i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                        tv_desc.setText(list_description.get(i));
                        if(descriptionColor !=null && !descriptionColor.isEmpty()){
                            tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        }else {
                            tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
//                            tv_desc.setTextColor(ColorStateList.valueOf(Color.parseColor(hexColor)));
                        }
//                        tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        tv_desc.setTextSize(12);
                        if (list_description.get(i).length() > 50) {
                            tv_desc.setText(list_description.get(i).substring(0, 50) + "...");
                        } else {
                            tv_desc.setText(list_description.get(i));
                        }
                        holder.ll_Description.addView(tv_desc);
                    }
                    if (dataViewerModelClassList.get(position).getCornerText() != null && dataViewerModelClassList.get(position).getCornerText().length() > 0) {
                        holder.ctv_corner.setText(dataViewerModelClassList.get(position).getCornerText());
                    } else {
                        holder.ctv_corner.setVisibility(View.GONE);
                    }
                    break;

                case AppConstants.ListView_2_Columns:
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    list_description = dataViewerModelClassList.get(position).getDescription();
                    if (dataViewerModelClassList.get(position).getCornerText() != null && dataViewerModelClassList.get(position).getCornerText().length() > 0) {
                        holder.ctv_corner.setText(dataViewerModelClassList.get(position).getCornerText());
                    } else {
                        holder.ctv_corner.setVisibility(View.GONE);
                    }
//                RelativeLayout.LayoutParams ll_param_desc=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,(list_description.size()*60));
//                ll_param_desc.setMargins(0,60,0,-50);
//                holder.ll_Description.setLayoutParams(ll_param_desc);
                    int emptycountA = 0;
                    for (int i = 0; i < list_description.size(); i++) {
                        if (list_description.get(i).trim().length() > 0) {
                            CustomTextView tv_desc = new CustomTextView(context);
                            tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                            if (list_description.get(i).length() > 40) {
                                tv_desc.setText(list_description.get(i).substring(0, 40) + "...");
                            } else {
                                tv_desc.setText(list_description.get(i));
                            }
                            if(descriptionColor !=null && !descriptionColor.isEmpty()){
                                tv_desc.setTextColor(Color.parseColor(descriptionColor));
                            }else {
                                tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
//                            tv_desc.setTextColor(ColorStateList.valueOf(Color.parseColor(hexColor)));
                            }
//                            tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
                            tv_desc.setTextSize(12);
                            holder.ll_Description.addView(tv_desc);
                        } else {
                            emptycountA++;
                        }
                    }
                    for (int i = 0; i < emptycountA; i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                        tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
                        tv_desc.setTextSize(12);
                        holder.ll_Description.addView(tv_desc);
                    }
                    break;
                case AppConstants.ListView_With_Image_2_Columns:
                    if (dataViewerModelClassList.get(position).getProfileImage_Path() != null) {
                        ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getProfileImage_Path(), holder.icon, false, controlObject.getDataViewer_DefualtImage_path());
                    } else {
                        if (dataViewerModelClassList.get(position).getAdvanceImage_Source() != null &&
                                dataViewerModelClassList.get(position).getAdvanceImage_Source().length() != 0) {
                            String Conditionvalue = dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClassList.get(position).getList_Image_Path();
                            for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                                if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)) {
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.icon, false, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath());
                                    break;
                                } else {
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.icon, false, "null");
                                    break;
                                }
                            }
                        } else {
                            String Conditionvalue = dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClassList.get(position).getList_Image_Path();
                            for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                                if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)) {
                                    ImproveHelper.loadImage_new(context, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath(), holder.icon, false, controlObject.getDataViewer_DefualtImage_path());
                                }
                            }
                        }
                    }
                    if (dataViewerModelClassList.get(position).getCornerText() != null && dataViewerModelClassList.get(position).getCornerText().length() > 0) {
                        holder.ctv_corner.setText(dataViewerModelClassList.get(position).getCornerText());
                    } else {
                        holder.ctv_corner.setVisibility(View.GONE);
                    }
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    list_description = dataViewerModelClassList.get(position).getDescription();

//                RelativeLayout.LayoutParams ll_param_descA=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,(list_description.size()*70));
//                ll_param_descA.setMargins(0,60,0,-50);
//                holder.ll_Description.setLayoutParams(ll_param_descA);

                    int emptycount = 0;
                    for (int i = 0; i < list_description.size(); i++) {
                        if (list_description.get(i).trim().length() > 0) {
                            CustomTextView tv_desc = new CustomTextView(context);
                            tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                            if (list_description.get(i).length() > 40) {
                                tv_desc.setText(list_description.get(i).substring(0, 40) + "...");
                            } else {
                                tv_desc.setText(list_description.get(i));
                            }
                            if(descriptionColor !=null && !descriptionColor.isEmpty()){
                                tv_desc.setTextColor(Color.parseColor(descriptionColor));
                            }else {
                                tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
//                            tv_desc.setTextColor(ColorStateList.valueOf(Color.parseColor(hexColor)));
                            }
//                            tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
                            tv_desc.setTextSize(12);
                            holder.ll_Description.addView(tv_desc);
                        } else {
                            emptycount++;
                        }
                    }
                    for (int i = 0; i < emptycount; i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                        tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
                        tv_desc.setTextSize(12);
                        holder.ll_Description.addView(tv_desc);
                    }
                    break;
                case AppConstants.ListView_With_Image_3_Columns_call:
                    if (dataViewerModelClassList.get(position).getImage_Path() != null) {
                        ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getImage_Path(), holder.icon, false, controlObject.getDataViewer_DefualtImage_path());
                    }else if (dataViewerModelClassList.get(position).getAdvanceImage_Source() != null &&
                            dataViewerModelClassList.get(position).getAdvanceImage_Source().length() != 0) {
                        String Conditionvalue = dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                        List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClassList.get(position).getList_Image_Path();
                        for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                            if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)) {
                                ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.icon, false, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath());
                                break;
                            } else {
                                ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.icon, false, "null");
                                break;
                            }
                        }
                    } else {
                        String Conditionvalue = dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                        List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClassList.get(position).getList_Image_Path();
                        if (list_IA_Map_Item != null) {
                            for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                                if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)) {
                                    ImproveHelper.loadImage_new(context, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath(), holder.icon, false, controlObject.getDataViewer_DefualtImage_path());
                                }
                            }
                        }
                    }

                    if (dataViewerModelClassList.get(position).getCornerText() != null && dataViewerModelClassList.get(position).getCornerText().length() > 0) {
                        holder.ctv_corner.setText(dataViewerModelClassList.get(position).getCornerText());
                    } else {
                        holder.ctv_corner.setVisibility(View.GONE);
                    }
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tvSubHeading.setTextColor(Color.parseColor(subHeaderColor));
                    list_description = dataViewerModelClassList.get(position).getDescription();
                    for (int i = 0; i < list_description.size(); i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                        tv_desc.setText(list_description.get(i).trim());
                        if(descriptionColor !=null && !descriptionColor.isEmpty()){
                            tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        }else {
                            tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
//                            tv_desc.setTextColor(ColorStateList.valueOf(Color.parseColor(hexColor)));
                        }
//                        tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        tv_desc.setTextSize(12);
                        if (list_description.get(i).length() > 50) {
                            tv_desc.setText(list_description.get(i).substring(0, 50) + "...");
                        } else {
                            tv_desc.setText(list_description.get(i));
                        }
                        holder.ll_Description.addView(tv_desc);
                    }
                    break;
                case AppConstants.ListView_With_Image_3_Columns:
                    if (dataViewerModelClassList.get(position).getImage_Path() != null) {
                        ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getImage_Path(), holder.icon, false, controlObject.getDataViewer_DefualtImage_path());
                    }else if (dataViewerModelClassList.get(position).getAdvanceImage_Source() != null &&
                                dataViewerModelClassList.get(position).getAdvanceImage_Source().length() != 0) {
                            String Conditionvalue = dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClassList.get(position).getList_Image_Path();
                            for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                                if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)) {
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.icon, false, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath());
                                    break;
                                } else {
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.icon, false, "null");
                                    break;
                                }
                            }
                        } else {
                            String Conditionvalue = dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClassList.get(position).getList_Image_Path();
                            if (list_IA_Map_Item != null) {
                                for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                                    if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)) {
                                        ImproveHelper.loadImage_new(context, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath(), holder.icon, false, controlObject.getDataViewer_DefualtImage_path());
                                    }
                                }
                            }
                        }

                    if (dataViewerModelClassList.get(position).getCornerText() != null && dataViewerModelClassList.get(position).getCornerText().length() > 0) {
                        holder.ctv_corner.setText(dataViewerModelClassList.get(position).getCornerText());
                    } else {
                        holder.ctv_corner.setVisibility(View.GONE);
                    }
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tvSubHeading.setTextColor(Color.parseColor(subHeaderColor));
                    list_description = dataViewerModelClassList.get(position).getDescription();

//                RelativeLayout.LayoutParams ll_param_descB=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,(list_description.size()*70));
//                ll_param_descB.setMargins(0,60,0,-50);
//                holder.ll_Description.setLayoutParams(ll_param_descB);
                    int emptycountBW = 0;
                    if (list_description != null) {
                        for (int i = 0; i < list_description.size(); i++) {
                            if (list_description.get(i).trim().length() > 0) {
                                CustomTextView tv_desc = new CustomTextView(context);
                                tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
//                                tv_desc.setTextColor(Color.parseColor(descriptionColor));
                                tv_desc.setTextSize(12);
                                if (list_description.get(i).length() > 100) {
                                    tv_desc.setText(list_description.get(i).substring(0, 100) + "...");
                                } else {
                                    tv_desc.setText(list_description.get(i));
                                }
                                if(descriptionColor !=null && !descriptionColor.isEmpty()){
                                    tv_desc.setTextColor(Color.parseColor(descriptionColor));
                                }else {
                                    tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
//                            tv_desc.setTextColor(ColorStateList.valueOf(Color.parseColor(hexColor)));
                                }
//                                tv_desc.setTextColor(Color.parseColor(descriptionColor));
                                holder.ll_Description.addView(tv_desc);
                            } else {
                                emptycountBW++;
                            }
                        }
                    }
                    for (int i = 0; i < emptycountBW; i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                        holder.ll_Description.addView(tv_desc);
                    }
                    break;
                case AppConstants.MapView_Item_Info:


                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tvSubHeading.setTextColor(Color.parseColor(subHeaderColor));
                    holder.tv_distance.setText(dataViewerModelClassList.get(position).getDistance());
                    holder.tv_working_hours.setText(dataViewerModelClassList.get(position).getWorkingHours());
                    if (dataViewerModelClassList.get(position).getItemOneCount().equalsIgnoreCase("")) {
                        holder.tv_l1_item_two.setVisibility(View.GONE);
                    } else {
                        holder.tv_l1_item_two.setText(dataViewerModelClassList.get(position).getItemOneCount());
                    }
                    holder.tv_l2_item_two.setText(dataViewerModelClassList.get(position).getItemTwoCount());

                    break;
                case AppConstants.Geo_Spatial_View:
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    list_description = dataViewerModelClassList.get(position).getDescription();
                    for (int i = 0; i < list_description.size(); i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                        tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                        if (list_description.get(i).length() > 100) {
                            tv_desc.setText(list_description.get(i).substring(0, 100) + "...");
                        } else {
                            tv_desc.setText(list_description.get(i));
                        }
                        holder.ll_Description.addView(tv_desc);
                    }
                    holder.mapView.onCreate(null);
                    holder.mapView.getMapAsync(googleMap -> {
                        MapsInitializer.initialize(context.getApplicationContext());
                        gMap = googleMap;
                        gMap.getUiSettings().setZoomControlsEnabled(false);
                        gMap.getUiSettings().setAllGesturesEnabled(false);
                        gMap.getUiSettings().setMapToolbarEnabled(false);
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();

                        /*List<LatLng> latLngList  = getSampleLatLngList();*/


                        if (dataViewerModelClassList.get(position).getGpsValue() != null) {
                            String gps = dataViewerModelClassList.get(position).getGpsValue();

                            double lat = Double.parseDouble(gps.split("\\$")[0]);
                            double lang = Double.parseDouble(gps.split("\\$")[1]);

                            LatLng latLng = new LatLng(lat, lang);

                            Marker marker = googleMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

                            builder.include(marker.getPosition());

                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                        }

                    });

                    holder.mapView.onResume();
                    break;
                case AppConstants.TimeLine_View:

                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tv_timeline_date.setText(dataViewerModelClassList.get(position).getDateandTime());
                    holder.tv_timeline_date.setTextColor(Color.parseColor(dateandtimeColor));


//                    ImproveHelper.loadImageForDataViewer(context, dataViewerModelClassList.get(position).getProfileImage_Path(), holder.icon,true);

                    list_description = dataViewerModelClassList.get(position).getDescription();
                    for (int i = 0; i < list_description.size(); i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                        tv_desc.setText(list_description.get(i));
                        tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        holder.ll_Description.addView(tv_desc);
                    }
                    if (position != -1 && position == (dataViewerModelClassList.size() - 1)) {
                        holder.view_verticalLine.setVisibility(View.GONE);
                    }
                    break;
                case AppConstants.TimeLine_With_Photo_View:
                    ImproveHelper.loadImageForDataViewer(context, dataViewerModelClassList.get(position).getProfileImage_Path(), holder.icon, true);
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tv_timeline_date.setText(dataViewerModelClassList.get(position).getDateandTime());
                    holder.tv_timeline_date.setTextColor(Color.parseColor(dateandtimeColor));

                    list_description = dataViewerModelClassList.get(position).getDescription();
                    for (int i = 0; i < list_description.size(); i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                        tv_desc.setText(list_description.get(i));
                        tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        holder.ll_Description.addView(tv_desc);
                    }
                    if (position != -1 && position == (dataViewerModelClassList.size() - 1)) {
                        holder.view_verticalLine.setVisibility(View.GONE);
                    }
                    break;
                case AppConstants.LinearView_With_Video:
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    list_description = dataViewerModelClassList.get(position).getDescription();
                    for (int i = 0; i < list_description.size(); i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                        tv_desc.setText(list_description.get(i));
                        tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
                        if (list_description.get(i).length() > 120) {
                            tv_desc.setText(list_description.get(i).substring(0, 120) + "...");
                        } else {
                            tv_desc.setText(list_description.get(i));
                        }
                        holder.ll_Description.addView(tv_desc);
                    }
                    break;
                case AppConstants.BlogSpot_View:
                    ImproveHelper.loadImageForDataViewer(context, dataViewerModelClassList.get(position).getProfileImage_Path(), holder.iv_circle, true);

                        if (dataViewerModelClassList.get(position).getImage_Path().endsWith(".WMA") ||
                                dataViewerModelClassList.get(position).getImage_Path().endsWith(".mp3")) {
                            holder.ll_audio_download.setVisibility(View.VISIBLE);
                            holder.fl_dv.setVisibility(View.GONE);
                            holder.icon.setVisibility(View.GONE);
                        } else if (dataViewerModelClassList.get(position).getImage_Path().endsWith(".jpg") ||
                                dataViewerModelClassList.get(position).getImage_Path().endsWith(".jpeg") ||
                                dataViewerModelClassList.get(position).getImage_Path().endsWith(".png")) {
                            ImproveHelper.loadImageForDataViewer(context, dataViewerModelClassList.get(position).getImage_Path(), holder.icon, false);
                            holder.icon.setVisibility(View.VISIBLE);
                            holder.fl_dv.setVisibility(View.GONE);
                            holder.ll_audio_download.setVisibility(View.GONE);
//                    }else if(dataViewerModelClassList.get(position).getImage_Path().endsWith(".mp4")){
                        } else {
                            holder.iv_dv_gitr_video.setTag(dataViewerModelClassList.get(position).getImage_Path());
                            holder.iv_dv_gitr_video.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(view.getTag() != null && !view.getTag().toString().isEmpty()) {
                                        Intent intent = new Intent(context, VideoPlayerActivity.class);
                                        intent.putExtra("URL", view.getTag().toString());
                                        context.startActivity(intent);
                                    }
                                }
                            });
                            holder.fl_dv.setVisibility(View.VISIBLE);
                            holder.icon.setVisibility(View.GONE);
                            holder.ll_audio_download.setVisibility(View.GONE);
                        }


                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tvSubHeading.setTextColor(Color.parseColor(subHeaderColor));
//                    list_description = dataViewerModelClassList.get(position).getDescription();
//                    for (int i = 0; i < list_description.size(); i++) {
//                        CustomTextView tv_desc = new CustomTextView(context);
//                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
//                        tv_desc.setText(list_description.get(i));
//                        tv_desc.setTextColor(Color.parseColor(descriptionColor));
//                        tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
//                        if (list_description.get(i).length() > 120) {
//                            tv_desc.setText(list_description.get(i).substring(0, 120) + "...");
//                        } else {
//                            tv_desc.setText(list_description.get(i));
//                        }
//                        holder.ll_Description.addView(tv_desc);
//                    }
                    break;
                case AppConstants.EV_Dashboard_Design_One:
                case AppConstants.EV_Dashboard_Design_Three:
                    if(dataViewerModelClassList.get(position).getImage_Path()!=null) {
                        ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getImage_Path(), holder.iv_ev_image, false,controlObject.getDataViewer_DefualtImage_path());
                    }else {
                        if(dataViewerModelClassList.get(position).getAdvanceImage_Source()!=null&&
                                dataViewerModelClassList.get(position).getAdvanceImage_Source().length()!=0){
                            String Conditionvalue= dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item=dataViewerModelClassList.get(position).getList_Image_Path();
                            for (int i = 0; i <list_IA_Map_Item.size() ; i++) {
                                if(list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)){
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.iv_ev_image, false,list_IA_Map_Item.get(i).getImageAdvanced_ImagePath());
                                    break;
                                }else{
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.iv_ev_image, false,"null");
                                    break;
                                }
                            }
                        }else{
                            String Conditionvalue= dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item=dataViewerModelClassList.get(position).getList_Image_Path();
                            for (int i = 0; i <list_IA_Map_Item.size() ; i++) {
                                if(list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)){
                                    ImproveHelper.loadImage_new(context, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath(), holder.iv_ev_image, false,controlObject.getDataViewer_DefualtImage_path());
                                }
                            }
                        }
                    }

                    holder.tv_ev_Heading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tv_ev_SubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tv_rating.setText(dataViewerModelClassList.get(position).getRating());
                    break;
                case AppConstants.EV_Dashboard_Design_Two:
                    if(dataViewerModelClassList.get(position).getImage_Path() != null && !dataViewerModelClassList.get(position).getImage_Path().equalsIgnoreCase("-")) {
                        if (dataViewerModelClassList.get(position).getImage_Path() != null) {
                            ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getImage_Path(), holder.iv_ev_image, false, controlObject.getDataViewer_DefualtImage_path());
                        } else {
                            if (dataViewerModelClassList.get(position).getAdvanceImage_Source() != null &&
                                    dataViewerModelClassList.get(position).getAdvanceImage_Source().length() != 0) {
                                String Conditionvalue = dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                                List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClassList.get(position).getList_Image_Path();
                                for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                                    if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)) {
                                        ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.iv_ev_image, false, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath());

                                        break;
                                    } else {
                                        ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.iv_ev_image, false, "null");
                                        break;
                                    }
                                }
                            } else {
                                String Conditionvalue = dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                                List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClassList.get(position).getList_Image_Path();
                                for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                                    if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)) {
                                        ImproveHelper.loadImage_new(context, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath(), holder.iv_ev_image, false, controlObject.getDataViewer_DefualtImage_path());
                                    }
                                }
                            }
                        }

                        holder.tv_ev_Heading.setText(dataViewerModelClassList.get(position).getHeading());
                        if (controlObject.getImageSpecificationType().equalsIgnoreCase(context.getString(R.string.required_image))) {

                            android.view.ViewGroup.LayoutParams layoutParamsImg = holder.iv_ev_image.getLayoutParams();
                            if (controlObject.getImageWidth() != null && !controlObject.getImageWidth().isEmpty()) {
                                layoutParamsImg.width = pxToDP(Integer.parseInt(controlObject.getImageWidth()));
                                layoutParamsImg.height = pxToDP(Integer.parseInt(controlObject.getImageHeight()));
                                holder.iv_ev_image.setLayoutParams(layoutParamsImg);
                            }

                        }
                    }else{
                        holder.layout_main.setVisibility(View.GONE);
                    }

                    break;
                case AppConstants.EV_News_Design:
                    if(dataViewerModelClassList.get(position).getImage_Path()!=null) {
                        ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getImage_Path(), holder.iv_ev_image, false,controlObject.getDataViewer_DefualtImage_path());
                    }else {
                        if(dataViewerModelClassList.get(position).getAdvanceImage_Source()!=null&&
                                dataViewerModelClassList.get(position).getAdvanceImage_Source().length()!=0){
                            String Conditionvalue= dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item=dataViewerModelClassList.get(position).getList_Image_Path();
                            for (int i = 0; i <list_IA_Map_Item.size() ; i++) {
                                if(list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)){
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.iv_ev_image, false,list_IA_Map_Item.get(i).getImageAdvanced_ImagePath());
                                    break;
                                }else{
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.iv_ev_image, false,"null");
                                    break;
                                }
                            }
                        }else{
                            String Conditionvalue= dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item=dataViewerModelClassList.get(position).getList_Image_Path();
                            for (int i = 0; i <list_IA_Map_Item.size() ; i++) {
                                if(list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)){
                                    ImproveHelper.loadImage_new(context, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath(), holder.iv_ev_image, false,controlObject.getDataViewer_DefualtImage_path());
                                }
                            }
                        }
                    }
                    if(dataViewerModelClassList.get(position).getSource_icon()!=null) {
                        ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getSource_icon(), holder.iv_ev_Source_Icon, false,controlObject.getDataViewer_DefualtImage_path());
                    }else{
                        holder.layout_source.setVisibility(View.GONE);
                    }

                    holder.tv_ev_Heading.setText(dataViewerModelClassList.get(position).getHeading());

                    holder.tv_ev_Source_Name.setText(dataViewerModelClassList.get(position).getSource_name());
                    holder.tv_ev_time.setText(dataViewerModelClassList.get(position).getSource_time());

                    if(dataViewerModelClassList.get(position).getNews_type()!=null && dataViewerModelClassList.get(position).getNews_type().equalsIgnoreCase("VIDEO")){
                        holder.iv_videoPlay.setVisibility(View.VISIBLE);
                    }
                    break;
                case AppConstants.EV_Notifications_Design:
                    holder.tv_ev_Heading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tv_ev_SubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tv_ev_time.setText(dataViewerModelClassList.get(position).getSource_time());
                    break;
                case AppConstants.EV_Dealers_Design:
                    if(dataViewerModelClassList.get(position).getImage_Path()!=null) {
                        ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getImage_Path(), holder.iv_ev_image, false,controlObject.getDataViewer_DefualtImage_path());
                    }else {
                        if(dataViewerModelClassList.get(position).getAdvanceImage_Source()!=null&&
                                dataViewerModelClassList.get(position).getAdvanceImage_Source().length()!=0){
                            String Conditionvalue= dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item=dataViewerModelClassList.get(position).getList_Image_Path();
                            for (int i = 0; i <list_IA_Map_Item.size() ; i++) {
                                if(list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)){
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.iv_ev_image, false,list_IA_Map_Item.get(i).getImageAdvanced_ImagePath());
                                    break;
                                }else{
                                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getAdvanceImage_Source(), holder.iv_ev_image, false,"null");
                                    break;
                                }
                            }
                        }else{
                            String Conditionvalue= dataViewerModelClassList.get(position).getAdvanceImage_ConditionColumn();
                            List<ImageAdvanced_Mapped_Item> list_IA_Map_Item=dataViewerModelClassList.get(position).getList_Image_Path();
                            for (int i = 0; i <list_IA_Map_Item.size() ; i++) {
                                if(list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(Conditionvalue)){
                                    ImproveHelper.loadImage_new(context, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath(), holder.iv_ev_image, false,controlObject.getDataViewer_DefualtImage_path());
                                }
                            }
                        }
                    }
                    holder.tv_ev_Heading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tv_ev_SubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tv_distance.setText(dataViewerModelClassList.get(position).getDistance());
                    holder.tv_working_hours.setText(dataViewerModelClassList.get(position).getWorkingHours());
                    break;
                case AppConstants.EV_Jobs_Design:
                    holder.tv_ev_Heading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tv_ev_SubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tv_job_type.setText(dataViewerModelClassList.get(position).getSource_name());
                    holder.tv_ev_time.setText(dataViewerModelClassList.get(position).getSource_time());
                    list_description = dataViewerModelClassList.get(position).getDescription();
                    for (int i = 0; i < list_description.size(); i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setCustomFont(context,context.getString(R.string.font_satoshi));
                        tv_desc.setText(list_description.get(i));
                        tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        holder.ll_Description.addView(tv_desc);
                    }
                    break;
                case AppConstants.Button_Group:

                    if(dataViewerModelClassList.get(position).getImage_Path()!=null&&!dataViewerModelClassList.get(position).getImage_Path().contentEquals("")) {
//                        ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getImage_Path(), holder.img_btn, false,controlObject.getDataViewer_DefualtImage_path());
                        Glide.with(context).load(dataViewerModelClassList.get(position).getImage_Path())
                                .thumbnail(Glide.with(context).load(dataViewerModelClassList.get(position).getImage_Path())).into(holder.img_btn);
                    }else{
                        holder.img_btn.setVisibility(View.GONE);
                    }
                    if(dataViewerModelClassList.get(position).getHeading()!=null&&!dataViewerModelClassList.get(position).getHeading().contentEquals("")){
                        holder.btn_title.setVisibility(View.VISIBLE);
                        holder.btn_title.setText(dataViewerModelClassList.get(position).getHeading());
                    }else {
                        holder.btn_title.setVisibility(View.GONE);
                    }


                    if(dataViewerModelClassList.get(position).isSelected()){

                     holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                     if(position==selectedItemPositionInAdapter){
                         holder.itemView.performClick();
                     }
                    }else {
                        holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.LightGrey));
                    }

                    break;
                case AppConstants.GRID_WITH_TWO_COLUMNS:
//                    Log.d(TAG, "populateItems_DVNEW: "+dataViewerModelClassList.get(position).getHeading());
                    Log.d(TAG, "populateItems_DVNEW: "+dataViewerModelClassList.get(position).getDailNo());
                    ImproveHelper.loadImage_new(context, dataViewerModelClassList.get(position).getImage_Path(), holder.iv_ev_image, false,controlObject.getDataViewer_DefualtImage_path());
                    if(dataViewerModelClassList.get(position).getHeading()!=null&&!dataViewerModelClassList.get(position).getHeading().contentEquals("")){
                        holder.tvHeading.setVisibility(View.VISIBLE);
                        holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    }else {
                        holder.tvHeading.setVisibility(View.GONE);
                    }
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.ctvItemName.setText(dataViewerModelClassList.get(position).getItemName());
                    list_description = dataViewerModelClassList.get(position).getDescription();
                    Typeface typeface_normal = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_semi_bold));
                    for (int i = 0; i < list_description.size(); i++) {
                        CustomTextView tv_desc = new CustomTextView(context);
                        tv_desc.setText(list_description.get(i));
                        tv_desc.setTextSize(10f);
                        tv_desc.setTypeface(typeface_normal);
                        tv_desc.setTextColor(Color.parseColor(descriptionColor));
                        holder.ll_Description.addView(tv_desc);
                    }
                    holder.ll_callNow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MainActivity)context).setDialNumber(dataViewerModelClassList.get(position).getDailNo(),null);
                        }
                    });
                    holder.ll_watsApp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ImproveHelper.sendPhoneNumberAndMessageWithWhatsApp(context, dataViewerModelClassList.get(position).getWatsAppNo(), "");
                        }
                    });
//                    if(dataViewerModelClassList.get(position).isSelected()){
//
//                        holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
//                        if(position==selectedItemPositionInAdapter){
//                            holder.itemView.performClick();
//                        }
//                    }else {
//                        holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.LightGrey));
//                    }
                    break;
                case AppConstants.WIDGET_1:
                    loadImage(dataViewerModelClassList.get(position),holder.icon);
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tvSubHeading.setTextColor(Color.parseColor(subHeaderColor));
                    holder.btn_item_one.setText(dataViewerModelClassList.get(position).getItemValue());
                    holder.btn_item_one.setTextColor(Color.parseColor(itemTextColor));
                    setDescription(dataViewerModelClassList.get(position).getDescription(),dataViewerModelClassList.get(position),holder.ll_Description);
                    break;
                case AppConstants.WIDGET_2:
                    loadImage(dataViewerModelClassList.get(position),holder.iv_profile_image);
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tvSubHeading.setTextColor(Color.parseColor(subHeaderColor));
                    holder.item_one.setText(dataViewerModelClassList.get(position).getItemValue());
                    setDescription(dataViewerModelClassList.get(position).getDescription(),dataViewerModelClassList.get(position),holder.ll_Description,Color.parseColor(descriptionColor),14);
                    break;
                case AppConstants.WIDGET_3:
                    loadImage(dataViewerModelClassList.get(position),holder.iv_profile_image);
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tvSubHeading.setTextColor(Color.parseColor(subHeaderColor));
                    holder.ratingBar.setRating(Float.parseFloat(dataViewerModelClassList.get(position).getItemValue()));
                    LayerDrawable stars = (LayerDrawable) holder.ratingBar.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(Color.parseColor(ratingColor), PorterDuff.Mode.SRC_ATOP);
                    break;
                case AppConstants.WIDGET_4:
                    break;
                case AppConstants.WIDGET_5:
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tvSubHeading.setTextColor(Color.parseColor(subHeaderColor));
                    holder.item_one.setText(dataViewerModelClassList.get(position).getItemValue());
                    setDescription(dataViewerModelClassList.get(position).getDescription(),dataViewerModelClassList.get(position),holder.ll_Description,Color.parseColor(descriptionColor),24);
                    break;
                case AppConstants.WIDGET_6:
                    loadImage(dataViewerModelClassList.get(position),holder.roundishImageView);
                    loadPImage(dataViewerModelClassList.get(position),holder.iv_profile_image);
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tvSubHeading.setTextColor(Color.parseColor(subHeaderColor));
                    break;
                case AppConstants.WIDGET_7:
                    holder.iv_bg_card.setBackground(getCardBG(controlObject.getDataViewer_FrameBG_HexColor(),controlObject.getDataViewer_FrameBG_HexColorTwo()));
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tvSubHeading.setTextColor(Color.parseColor(subHeaderColor));
                    holder.item_one.setText(dataViewerModelClassList.get(position).getItemValue());
                    setDescription(dataViewerModelClassList.get(position).getDescription(),dataViewerModelClassList.get(position),holder.ll_Description,ContextCompat.getColor(context,R.color.white),16);
                    break;
                case AppConstants.WIDGET_8:
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tvSubHeading.setTextColor(Color.parseColor(subHeaderColor));
                    holder.item_one.setText(dataViewerModelClassList.get(position).getItemValue());
//                    setDescription(dataViewerModelClassList.get(position).getDescription(),dataViewerModelClassList.get(position),holder.ll_Description);
                    break;
                case AppConstants.WIDGET_9:
                    loadImage(dataViewerModelClassList.get(position),holder.iv_profile_image1);
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tvSubHeading.setTextColor(Color.parseColor(subHeaderColor));
                    break;
                case AppConstants.WIDGET_10:
                    loadImage(dataViewerModelClassList.get(position),holder.iv_profile_image1);
                    holder.tvHeading.setText(dataViewerModelClassList.get(position).getHeading());
                    holder.tvHeading.setTextColor(Color.parseColor(headerColor));
                    holder.tvSubHeading.setText(dataViewerModelClassList.get(position).getSubHeading());
                    holder.tvSubHeading.setTextColor(Color.parseColor(subHeaderColor));
                    setDescription(dataViewerModelClassList.get(position).getDescription(),dataViewerModelClassList.get(position),holder.ll_Description,Color.parseColor(descriptionColor),14);
                    break;
            }
        }catch(Exception e){
            improveHelper.improveException(context, TAG, "populateData", e);
        }}

    private Drawable getCardBG(String colorOne, String colorTwo) {
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {Color.parseColor(colorOne),Color.parseColor(colorTwo)});
        gd.setCornerRadius(30f);

        return gd;

    }

    @Override
    public int getItemCount() {
        return  this.dataViewerModelClassList.size();
    }

    public void update(List<DataViewerModelClass>list,int startPos){
        dataViewerModelClassList.addAll(list);
        notifyItemRangeInserted(startPos,list.size());
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dataViewerModelClassList = dataViewerModelClassDefaultList;
                } else {
                    List<DataViewerModelClass> filteredList = new ArrayList<>();
                    for (DataViewerModelClass row : dataViewerModelClassDefaultList) {
                        if(typeOfSearch.equals("HSDC")){
                            if (row.getHeading().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getSubHeading().toLowerCase().contains(charString.toLowerCase())||
                                    getDescriptionsString(row.getDescription()).toLowerCase().contains(charString.toLowerCase())||
                                    row.getCornerText().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }else if(typeOfSearch.equals("HSD")){
                            if (row.getHeading().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getSubHeading().toLowerCase().contains(charString.toLowerCase())||
                                    getDescriptionsString(row.getDescription()).toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }else if(typeOfSearch.equals("HSC")){
                            if (row.getHeading().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getSubHeading().toLowerCase().contains(charString.toLowerCase())||
                                    row.getCornerText().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }else if(typeOfSearch.equals("HDC")){
                            if (row.getHeading().toLowerCase().contains(charString.toLowerCase()) ||
                                    getDescriptionsString(row.getDescription()).toLowerCase().contains(charString.toLowerCase())||
                                    row.getCornerText().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }else if(typeOfSearch.equals("HS")){
                            if (row.getHeading().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getSubHeading().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }else if(typeOfSearch.equals("HD")){
                            if (row.getHeading().toLowerCase().contains(charString.toLowerCase()) ||
                                    getDescriptionsString(row.getDescription()).toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }else if(typeOfSearch.equals("HC")){
                            if (row.getHeading().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getCornerText().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }else if(typeOfSearch.equals("H")){
                            if (row.getHeading().toLowerCase().contains(charString.toLowerCase()) ) {
                                filteredList.add(row);
                            }
                        }else if(typeOfSearch.equals("SDC")){
                            if (  row.getSubHeading().toLowerCase().contains(charString.toLowerCase())||
                                    getDescriptionsString(row.getDescription()).toLowerCase().contains(charString.toLowerCase())||
                                    row.getCornerText().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }else if(typeOfSearch.equals("SD")){
                            if (  row.getSubHeading().toLowerCase().contains(charString.toLowerCase())||
                                    getDescriptionsString(row.getDescription()).toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }else if(typeOfSearch.equals("SC")){
                            if (  row.getSubHeading().toLowerCase().contains(charString.toLowerCase())||
                                    row.getCornerText().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }else if(typeOfSearch.equals("S")){
                            if (  row.getSubHeading().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }else if(typeOfSearch.equals("DC")){
                            if (getDescriptionsString(row.getDescription()).toLowerCase().contains(charString.toLowerCase())||
                                    row.getCornerText().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }else if(typeOfSearch.equals("D")){
                            if (getDescriptionsString(row.getDescription()).toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }else if(typeOfSearch.equals("C")){
                            if (row.getCornerText().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                       }
                    }
                    dataViewerModelClassList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataViewerModelClassList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataViewerModelClassList = (ArrayList<DataViewerModelClass>) filterResults.values;
                if (dataViewerModelClassList.size() > 0) {
                    tv_NoData.setVisibility(View.GONE);
                } else {
                    tv_NoData.setVisibility(View.VISIBLE);
                }

                notifyDataSetChanged();
            }
        };
    }

    private String getDescriptionsString(List<String> description){
        String des="";
        for (int i = 0; i < description.size(); i++) {
            des=des+description.get(i);
        }
        return des;
    }

    public DataViewerModelClass getSelectedDataViewerModelClass() {
        return SelectedDataViewerModelClass;
    }

    public List<DataViewerModelClass> getDataViewerModelClassList() {

        return dataViewerModelClassList;
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {

        viewHolder.progressBar.setVisibility(View.VISIBLE);

    }

    public int getItemViewType(int position) {
        return dataViewerModelClassList.get(position).isNullObject() ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void updateEmptyObject() {
        DataViewerModelClass object = new DataViewerModelClass();
        object.setNullObject(true);
        dataViewerModelClassList.add(object);
        notifyItemInserted(dataViewerModelClassList.size() - 1);
    }

    public void removeEmptyObject() {
        dataViewerModelClassList.remove(dataViewerModelClassList.size() - 1);
        notifyItemRemoved(dataViewerModelClassList.size());
    }

    public int pxToDP(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics());
    }

    private int getWidth() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int pxWidth = displayMetrics.widthPixels;
        return pxWidth;
    }

    public int getSelectedItemPositionInAdapter() {
        return selectedItemPositionInAdapter;
    }

//    public void setSelectedItemPositionInAdapter(int selectedItemPositionInAdapter) {
//        this.selectedItemPositionInAdapter = selectedItemPositionInAdapter;
//        for (int i = 0; i < dataViewerModelClassList.size(); i++) {
//            dataViewerModelClassList.get(i).setSelected(i == selectedItemPositionInAdapter);
//
//        }
//        notifyDataSetChanged();
//    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CustomTextView tvHeading, tvSubHeading, tv_timeline_date,ctv_corner,tv_ev_Source_Name,tv_ev_time,ctvItemName,item_one;
        ImageView icon, iv_circle, iv_dv_gitr_video, iv_dv_gitrAudio,iv_ev_Source_Icon,iv_videoPlay;
        LinearLayout ll_item_main,ll_Description,layout_main,layout_source,ll_callNow,ll_watsApp,ll_audio_download;
        FrameLayout fl_dv, fl_dv_audio;
        MapView mapView;
        RelativeLayout rl_dv_gitr;
        View view_verticalLine;
        //--EV--//
        CustomTextView tv_distance, tv_working_hours, tv_l1_item_two, tv_l2_item_two, tv_ev_Heading, tv_ev_SubHeading, tv_job_type, tv_address, tv_rating;
        ImageView iv_ev_image;
        ImageView img_btn, iv_gitrc, iv_gitrc_whatsapp;
        CustomTextView btn_title;
        CardView card,cv_item_main;

        CircleImageView iv_profile_image;
        ImageView iv_profile_image1;

        RatingBar ratingBar;
        CustomButton btn_item_one;
        RoundishImageViewTopLeftRight roundishImageView;
        ImageView iv_bg_card;
        //--EV--//

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_item_main = itemView.findViewById(R.id.ll_item_main);
            rl_dv_gitr = itemView.findViewById(R.id.rl_dv_gitr);
            switch (UI_Pattern) {
                case AppConstants.GridView_With_Image_2_Columns:
                    ctv_corner = itemView.findViewById(R.id.ctv_corner);
                    icon = itemView.findViewById(R.id.iv_dv_gitr);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    break;
                case AppConstants.GridView_With_Image_3_Columns:
                    icon = itemView.findViewById(R.id.iv_dv_gitr);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    ctv_corner = itemView.findViewById(R.id.ctv_corner);
                    break;
                case AppConstants.GridView_With_Image_2_Columns_call:
                    icon = itemView.findViewById(R.id.iv_dv_gitr);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    ctv_corner = itemView.findViewById(R.id.ctv_corner);
                    break;
                case AppConstants.GridView_With_Image_3_Columns_call:
                    icon = itemView.findViewById(R.id.iv_dv_gitr);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    ctv_corner = itemView.findViewById(R.id.ctv_corner);
                    break;
                case AppConstants.GridView_With_Video_2_Columns:
                    icon = itemView.findViewById(R.id.iv_dv_gitr);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    ctv_corner= itemView.findViewById(R.id.ctv_corner);
                    break;
                case AppConstants.GridView_With_Video_3_Columns:
                    icon = itemView.findViewById(R.id.iv_dv_gitr);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    ctv_corner= itemView.findViewById(R.id.ctv_corner);
                    break;
                case AppConstants.GridView_With_Video_2_Columns_call:
                    icon = itemView.findViewById(R.id.iv_dv_gitr);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    ctv_corner= itemView.findViewById(R.id.ctv_corner);
                    break;
                case AppConstants.GridView_With_Video_3_Columns_call:
                    icon = itemView.findViewById(R.id.iv_dv_gitr);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    ctv_corner= itemView.findViewById(R.id.ctv_corner);
                    break;
                case AppConstants.ListView_2_Columns:
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    ctv_corner= itemView.findViewById(R.id.ctv_corner);
                    break;
                case AppConstants.ListView_With_Image_2_Columns:
                    icon = itemView.findViewById(R.id.iv_dv_gitr);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    ctv_corner= itemView.findViewById(R.id.ctv_corner);
                    break;
                case AppConstants.ListView_With_Image_3_Columns:
                    icon = itemView.findViewById(R.id.iv_dv_gitr);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    ctv_corner= itemView.findViewById(R.id.ctv_corner);
                    break;
                case AppConstants.ListView_With_Image_3_Columns_call:
                    icon = itemView.findViewById(R.id.iv_dv_gitr);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    ctv_corner= itemView.findViewById(R.id.ctv_corner);
                    layout_main= itemView.findViewById(R.id.layout_main);
                    if(controlObject.isEnableHorizontalScroll()){
                        int itemWidth =  (getWidth() / 100) * 80;
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                        layout_main.setLayoutParams(params);
                    }
                    break;
                case AppConstants.MapView_Item_Info:
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    tv_distance = itemView.findViewById(R.id.tv_distance);
                    tv_working_hours = itemView.findViewById(R.id.tv_working_hours);
                    tv_l1_item_two = itemView.findViewById(R.id.tv_l1_item_two);
                    tv_l2_item_two = itemView.findViewById(R.id.tv_l2_item_two);
                    layout_main= itemView.findViewById(R.id.layout_main);
                    if(controlObject.isEnableHorizontalScroll()){
                        int itemWidth =  (getWidth() / 100) * 90;
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                        layout_main.setLayoutParams(params);
                    }
                    break;
                case AppConstants.Geo_Spatial_View:
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    mapView = itemView.findViewById(R.id.mapView);
                    break;
                case AppConstants.TimeLine_View:
                    icon = itemView.findViewById(R.id.iv_dv_gitr);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tv_timeline_date = itemView.findViewById(R.id.tv_timeline_date);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    view_verticalLine = itemView.findViewById(R.id.view_verticalLine);
                    break;
                case AppConstants.TimeLine_With_Photo_View:
                    icon = itemView.findViewById(R.id.iv_dv_gitr);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tv_timeline_date = itemView.findViewById(R.id.tv_timeline_date);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    view_verticalLine = itemView.findViewById(R.id.view_verticalLine);
                    break;
                case AppConstants.LinearView_With_Video:
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    break;
                case AppConstants.BlogSpot_View:
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    iv_circle = itemView.findViewById(R.id.iv_circle);
                    icon = itemView.findViewById(R.id.iv_dv_gitr);
                    fl_dv = itemView.findViewById(R.id.fl_dv);
                    fl_dv_audio = itemView.findViewById(R.id.fl_dv_audio);
                    iv_dv_gitr_video = itemView.findViewById(R.id.iv_dv_gitr_video);
                    ll_audio_download = itemView.findViewById(R.id.ll_audio_download);
//                    iv_play_ctrl = itemView.findViewById(R.id.iv_play_ctrl);
//                    iv_pause_ctrl = itemView.findViewById(R.id.iv_pause_ctrl);

                    tv_timeline_date = itemView.findViewById(R.id.tv_timeline_date);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
//                    fl_dv_audio = itemView.findViewById(R.id.fl_dv_audio);
//                    iv_dv_gitrAudio = itemView.findViewById(R.id.iv_dv_gitrAudio);
                    break;
                case AppConstants.EV_Dashboard_Design_One:
                    iv_ev_image = itemView.findViewById(R.id.iv_ev_image);
                    tv_ev_Heading = itemView.findViewById(R.id.tv_ev_Heading);
                    tv_ev_SubHeading = itemView.findViewById(R.id.tv_ev_SubHeading);
                    tv_rating = itemView.findViewById(R.id.tv_rating);
                    layout_main = itemView.findViewById(R.id.layout_main);
                    if (controlObject.isEnableHorizontalScroll()) {
                        int itemWidth = (getWidth() / 100) * 60;
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                        layout_main.setLayoutParams(params);
                    }
                    break;
                case AppConstants.EV_Dashboard_Design_Three:
                    iv_ev_image = itemView.findViewById(R.id.iv_ev_image);
                    tv_ev_Heading = itemView.findViewById(R.id.tv_ev_Heading);
                    tv_ev_SubHeading = itemView.findViewById(R.id.tv_ev_SubHeading);
                    tv_rating = itemView.findViewById(R.id.tv_rating);
                    layout_main= itemView.findViewById(R.id.layout_main);
                    break;
                case AppConstants.EV_Dashboard_Design_Two:
                    iv_ev_image = itemView.findViewById(R.id.iv_ev_image);
                    tv_ev_Heading = itemView.findViewById(R.id.tv_ev_Heading);
                    layout_main= itemView.findViewById(R.id.layout_main);
                    if(controlObject.isEnableHorizontalScroll()){
                        int itemWidth =  (getWidth() / 100) * 30;
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                        layout_main.setLayoutParams(params);
                    }
                    break;
                case AppConstants.EV_News_Design:
                    iv_ev_image = itemView.findViewById(R.id.iv_ev_image);
                    tv_ev_Heading = itemView.findViewById(R.id.tv_ev_Heading);
                    layout_source = itemView.findViewById(R.id.layout_source);
                    tv_ev_Source_Name = itemView.findViewById(R.id.tv_source_name);
                    tv_ev_time = itemView.findViewById(R.id.tv_time);
                    iv_ev_Source_Icon = itemView.findViewById(R.id.iv_source_icon);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    iv_videoPlay = itemView.findViewById(R.id.iv_videoPlay);
                    break;
                case AppConstants.EV_Notifications_Design:
                    tv_ev_Heading = itemView.findViewById(R.id.tv_ev_Heading);
                    tv_ev_SubHeading = itemView.findViewById(R.id.tv_ev_SubHeading);
                    tv_ev_time = itemView.findViewById(R.id.tv_time);
                    break;
                case AppConstants.EV_Dealers_Design:
                    iv_ev_image = itemView.findViewById(R.id.iv_ev_image);
                    tv_ev_Heading = itemView.findViewById(R.id.tv_ev_Heading);
                    tv_ev_SubHeading = itemView.findViewById(R.id.tv_ev_SubHeading);
                    tv_distance = itemView.findViewById(R.id.tv_distance);
                    tv_working_hours = itemView.findViewById(R.id.tv_working_hours);
                    break;
                case AppConstants.EV_Jobs_Design:
                    tv_ev_Heading = itemView.findViewById(R.id.tv_ev_Heading);
                    tv_ev_SubHeading = itemView.findViewById(R.id.tv_ev_SubHeading);
                    tv_job_type = itemView.findViewById(R.id.tv_job_type);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    tv_ev_time = itemView.findViewById(R.id.tv_ev_time);
                    break;
                case AppConstants.Button_Group:
                    img_btn = itemView.findViewById(R.id.img_btn);
                    btn_title = itemView.findViewById(R.id.btn_title);
                    card = itemView.findViewById(R.id.card);
                    break;
                case AppConstants.GRID_WITH_TWO_COLUMNS:
                    iv_ev_image = itemView.findViewById(R.id.iv_ev_image);
                    ctvItemName = itemView.findViewById(R.id.ctvItemName);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    iv_gitrc = itemView.findViewById(R.id.iv_gitrc);
                    iv_gitrc_whatsapp = itemView.findViewById(R.id.iv_gitrc_whatsapp);
                    ll_callNow = itemView.findViewById(R.id.ll_callNow);
                    ll_watsApp = itemView.findViewById(R.id.ll_watsApp);
                    break;
                case AppConstants.WIDGET_1:
                    icon = itemView.findViewById(R.id.iv_dv_gitr);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    btn_item_one = itemView.findViewById(R.id.btn_item_one);
                    break;
                case AppConstants.WIDGET_2:
                    iv_profile_image = itemView.findViewById(R.id.iv_profile_image);
                    item_one = itemView.findViewById(R.id.item_one);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    break;
                case AppConstants.WIDGET_3:
                    iv_profile_image = itemView.findViewById(R.id.iv_profile_image);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    ratingBar = itemView.findViewById(R.id.ratingStar);
                    break;
                case AppConstants.WIDGET_4:
                    break;
                case AppConstants.WIDGET_5:
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    item_one = itemView.findViewById(R.id.item_one);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    break;
                case AppConstants.WIDGET_6:
                    iv_profile_image = itemView.findViewById(R.id.iv_profile_image);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    roundishImageView = itemView.findViewById(R.id.iv_dv_gitr);
                    break;
                case AppConstants.WIDGET_7:
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    item_one = itemView.findViewById(R.id.item_one);
                    iv_bg_card = itemView.findViewById(R.id.iv_bg_card);
                    break;
                case AppConstants.WIDGET_8:
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    item_one = itemView.findViewById(R.id.item_one);
                    break;
                case AppConstants.WIDGET_9:
                    iv_profile_image1 = itemView.findViewById(R.id.iv_profile_image);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    break;
                case AppConstants.WIDGET_10:
                    iv_profile_image1 = itemView.findViewById(R.id.iv_profile_image);
                    tvHeading = itemView.findViewById(R.id.tv_gitr_Heading);
                    tvSubHeading = itemView.findViewById(R.id.tv_gitr_SubHeading);
                    ll_Description = itemView.findViewById(R.id.ll_Description);
                    break;
            }

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            SelectedDataViewerModelClass = dataViewerModelClassList.get(getAdapterPosition());
//            ((MainActivity) context).ClickEvent(view);

            if (clickListener != null) {
//                view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                for (int i = 0; i <dataViewerModelClassList.size() ; i++) {
                    if(i==getAdapterPosition()){
                        dataViewerModelClassList.get(i).setSelected(true);
                    }else {
                        dataViewerModelClassList.get(i).setSelected(false);
                    }

                }
                clickListener.onCustomClick(context, view, getAdapterPosition(), null);
                notifyDataSetChanged();
            }
        }
    }

    public void setSelectedItemPositionInAdapter(int selectedItemPositionInAdapter) {
        this.selectedItemPositionInAdapter = selectedItemPositionInAdapter;
        for (int i = 0; i <dataViewerModelClassList.size() ; i++) {
            if(i==selectedItemPositionInAdapter){
                dataViewerModelClassList.get(i).setSelected(true);
            }else {
                dataViewerModelClassList.get(i).setSelected(false);
            }

        }
        notifyDataSetChanged();
    }



    private void loadImage(DataViewerModelClass dataViewerModelClass,ImageView imageView){
        if (dataViewerModelClass.getImage_Path() != null) {
            if(dataViewerModelClass.getImage_Path().trim().contentEquals("")){
                dataViewerModelClass.setImage_Path(controlObject.getDataViewer_DefualtImage_path());
            }
            ImproveHelper.loadImage_new(context, dataViewerModelClass.getImage_Path(), imageView, false, controlObject.getDataViewer_DefualtImage_path());
        } else {
            if (dataViewerModelClass.getAdvanceImage_Source() != null &&
                    dataViewerModelClass.getAdvanceImage_Source().length() != 0) {
                String conditionValue = dataViewerModelClass.getAdvanceImage_ConditionColumn();
                List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClass.getList_Image_Path();
                for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                    if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(conditionValue)) {
                        ImproveHelper.loadImage_new(context, dataViewerModelClass.getAdvanceImage_Source(), imageView, false, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath());
                        break;
                    } else {
                        ImproveHelper.loadImage_new(context, dataViewerModelClass.getAdvanceImage_Source(), imageView, false, "null");
                        break;
                    }
                }
            } else {
                String conditionValue = dataViewerModelClass.getAdvanceImage_ConditionColumn();
                List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClass.getList_Image_Path();
                for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                    if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(conditionValue)) {
                        ImproveHelper.loadImage_new(context, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath(), imageView, false, controlObject.getDataViewer_DefualtImage_path());
                    }
                }
            }
        }
    }
    private void loadPImage(DataViewerModelClass dataViewerModelClass,ImageView imageView){
        if (dataViewerModelClass.getProfileImage_Path() != null) {
            ImproveHelper.loadImage_new(context, dataViewerModelClass.getProfileImage_Path(), imageView, false, controlObject.getDataViewer_DefualtImage_path());
        } else {
            if (dataViewerModelClass.getAdvanceImage_Source() != null &&
                    dataViewerModelClass.getAdvanceImage_Source().length() != 0) {
                String conditionValue = dataViewerModelClass.getAdvanceImage_ConditionColumn();
                List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClass.getList_Image_Path();
                for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                    if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(conditionValue)) {
                        ImproveHelper.loadImage_new(context, dataViewerModelClass.getAdvanceImage_Source(), imageView, false, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath());
                        break;
                    } else {
                        ImproveHelper.loadImage_new(context, dataViewerModelClass.getAdvanceImage_Source(), imageView, false, "null");
                        break;
                    }
                }
            } else {
                String conditionValue = dataViewerModelClass.getAdvanceImage_ConditionColumn();
                List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = dataViewerModelClass.getList_Image_Path();
                for (int i = 0; i < list_IA_Map_Item.size(); i++) {
                    if (list_IA_Map_Item.get(i).getImageAdvanced_Value().equalsIgnoreCase(conditionValue)) {
                        ImproveHelper.loadImage_new(context, list_IA_Map_Item.get(i).getImageAdvanced_ImagePath(), imageView, false, controlObject.getDataViewer_DefualtImage_path());
                    }
                }
            }
        }
    }

    private void setDescription(List<String>list_description,DataViewerModelClass dataViewerModelClass,LinearLayout linearLayout){
        list_description = dataViewerModelClass.getDescription();
        for (int i = 0; i < list_description.size(); i++) {
            CustomTextView tv_desc = new CustomTextView(context);
            tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
            tv_desc.setTextColor(ContextCompat.getColor(context, R.color.description_color));
            tv_desc.setTextSize(12);

            if (list_description.get(i).length() > 30) {
                tv_desc.setText(list_description.get(i).substring(0, 30) + "...");
            } else {
                tv_desc.setText(list_description.get(i));
            }
            linearLayout.addView(tv_desc);
        }
    }

    private void setDescription(List<String>list_description,DataViewerModelClass dataViewerModelClass,LinearLayout linearLayout,int color,int size){
        list_description = dataViewerModelClass.getDescription();
        for (int i = 0; i < list_description.size(); i++) {
            CustomTextView tv_desc = new CustomTextView(context);
            tv_desc.setCustomFont(context, context.getString(R.string.font_satoshi));
            tv_desc.setTextColor(color);
            tv_desc.setTextSize(size);
            tv_desc.setGravity(Gravity.START);

            if (list_description.get(i).length() > 30) {
                tv_desc.setText(list_description.get(i).substring(0, 30) + "...");
            } else {
                tv_desc.setText(list_description.get(i));
            }
            linearLayout.addView(tv_desc);
        }
    }
}
