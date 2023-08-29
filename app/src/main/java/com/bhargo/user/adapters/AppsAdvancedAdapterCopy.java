package com.bhargo.user.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Query.QueryGetDataActivity;
import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.ItemClickListenerAdvanced;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CAMERA;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_FILE_BROWSING;
import static com.bhargo.user.utils.AppConstants.VIEW_TYPE_ITEM;
import static com.bhargo.user.utils.AppConstants.VIEW_TYPE_LOADING;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;


public class AppsAdvancedAdapterCopy extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private static final String TAG = "AppsAdvancedAdapter";
    Context context;
    SessionManager sessionManager;
    private ItemClickListenerAdvanced clickListener;
    private JSONArray array;
    private JSONArray appDataArray;
    List<String> List_Index_Columns;
    boolean edit, delete, image;
    String file_type = null;

    String strAppName;
    boolean searchFilter = false;
    String tableName = "";
    String appMode = "";
    int style = 0;

    public AppsAdvancedAdapterCopy(Context context, JSONArray array, List<String> List_Index_Columns, boolean edit,
                                   boolean delete, boolean image, String strAppName, String file_type, String tableName, String appMode, int style) {
        this.strAppName = strAppName;
        this.context = context;
        this.array = array;
        this.edit = edit;
        this.delete = delete;
        this.image = image;
        this.List_Index_Columns = List_Index_Columns;
        this.file_type = file_type;
        this.tableName = tableName;
        this.appMode = appMode;
        this.style = style;
        sessionManager = new SessionManager(context);
        appDataArray = array;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == VIEW_TYPE_ITEM) {
            if (style == 1) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_page_style_two, parent, false);
            } else if (style == 2) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_page_style_one, parent, false);
            } else if (style == 3) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_page_style_six, parent, false);
            } else if (style == 4) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_page_style_four, parent, false);
            } else if (style == 5) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_page_style_five, parent, false);
            } else if (style == 6 ) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_page_style_two, parent, false);
            }

        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return new MyViewHolder(v);

    }

    public void setCustomClickListener(ItemClickListenerAdvanced itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MyViewHolder) {

            populateItems((MyViewHolder) holder, position);

        } else if (holder instanceof LoadingViewHolder) {

            showLoadingView((LoadingViewHolder) holder, position);

        }


    }


    @Override
    public int getItemCount() {
//        if(array==null){
////            array = new JSONArray();
////        }

        return array.length();
    }

    public void setImageFromSDCard(ImageView imageView, String strImagePath) {
        try {
//        File imgFile = new File(Environment.getExternalStorageDirectory(), strImagePath);
            File imgFile = new File(strImagePath);

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                imageView.setImageBitmap(myBitmap);

            }
        } catch (Exception e) {

        }
    }

    public void updateList(JSONArray list) {
        if (list.length() > 0) {
            this.array = list;
//        checkViewColumnsCount();
            notifyDataSetChanged();
            appDataArray = list;
        }
    }

    public void updateList(JSONArray updatesList, int startPos) {

        for (int i = 0; i < updatesList.length(); i++) {
            try {
                array.put(updatesList.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        appDataArray = array;
        notifyItemRangeChanged(startPos, updatesList.length());


    }

    public void updateEmptyObject() {
        array.put(new JSONObject());
        notifyItemInserted(array.length() - 1);
        appDataArray = array;
    }

    public void removeEmptyObject() {
        array.remove(array.length() - 1);
        notifyItemRemoved(array.length());
        appDataArray = array;
    }


    public void intentData(Context context, String QueryName, String strChildDesignFormat) {

        Intent intent = new Intent(context, QueryGetDataActivity.class);
        intent.putExtra("s_childForm", "ChildForm");
        intent.putExtra("s_app_name", QueryName);
        intent.putExtra("s_design_format", strChildDesignFormat);
        intent.putExtra("s_org_id", sessionManager.getOrgIdFromSession());
        intent.putExtra("s_user_id", sessionManager.getUserDataFromSession().getUserID());
        context.startActivity(intent);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        //        TextView name;
        CustomTextView tvfirstControlValue, tvSecondControlValue, tvThirdControlValue;// init the item view's
        ImageView iv_edit, iv_delete, iv_circle;
        RelativeLayout llIndexColumns, rl_delete, rl_edit;
        LinearLayout layout_image, layout_data, layout_options, layout_main;

        CardView cv_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's

            cv_item = itemView.findViewById(R.id.cv_item);
            layout_image = itemView.findViewById(R.id.layout_image);
            layout_data = itemView.findViewById(R.id.layout_data);
            layout_options = itemView.findViewById(R.id.layout_options);
            layout_main = itemView.findViewById(R.id.layout_main);

            llIndexColumns = itemView.findViewById(R.id.llIndexColumns);
            tvfirstControlValue = itemView.findViewById(R.id.tvfirstControlValue);
            tvSecondControlValue = itemView.findViewById(R.id.tvSecondControlValue);
            tvThirdControlValue = itemView.findViewById(R.id.tvThirdControlValue);
            iv_edit = itemView.findViewById(R.id.iv_edit);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            iv_circle = itemView.findViewById(R.id.iv_circle);
            rl_delete = itemView.findViewById(R.id.rl_delete);
            rl_edit = itemView.findViewById(R.id.rl_edit);

            if (edit) {
                rl_edit.setVisibility(View.VISIBLE);
            } else {
                rl_edit.setVisibility(View.GONE);

            }


            if (delete) {
                rl_delete.setVisibility(View.VISIBLE);
            } else {

                rl_delete.setVisibility(View.GONE);

            }

            if (image) {
                layout_image.setVisibility(View.VISIBLE);
            } else {
                layout_image.setVisibility(View.GONE);
                if (style == 2) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.weight = 0.8f;
                    layout_data.setLayoutParams(layoutParams);
                } else if (style == 5 || style == 6) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.weight = 0.9f;
                    layout_data.setLayoutParams(layoutParams);
                }
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        try {
                            String status = "3";
                            if (appMode != null && !appMode.equalsIgnoreCase("Online")) {
                                final JSONObject object = array.getJSONObject(getAdapterPosition());
                                status = object.getString("Bhargo_SyncStatus");
                            }
                            clickListener.onCustomClick(context, v, getAdapterPosition(), "View", status);
                        } catch (Exception e) {
                            Log.d(TAG, "onClick: " + Log.getStackTraceString(e));
                        }
                    }
                }
            });

            iv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        try {
                            String status = "3";
                            if (appMode != null && !appMode.equalsIgnoreCase("Online")) {
                                final JSONObject object = array.getJSONObject(getAdapterPosition());
                                status = object.getString("Bhargo_SyncStatus");
                            }
                            clickListener.onCustomClick(context, v, getAdapterPosition(), "Edit", status);
                        } catch (Exception e) {
                            Log.d(TAG, "onClick: " + Log.getStackTraceString(e));
                        }
                    }
                }
            });

            rl_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        try {
                            String status = "3";
                            if (appMode != null && !appMode.equalsIgnoreCase("Online")) {
                                final JSONObject object = array.getJSONObject(getAdapterPosition());
                                status = object.getString("Bhargo_SyncStatus");
                            }
                            clickListener.onCustomClick(context, v, getAdapterPosition(), "Edit", status);
                        } catch (Exception e) {
                            Log.d(TAG, "onClick: " + Log.getStackTraceString(e));
                        }
                    }
                }
            });

            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (clickListener != null) {
                        try {
                            String status = "3";
                            if (appMode != null && !appMode.equalsIgnoreCase("Online")) {
                                final JSONObject object = array.getJSONObject(getAdapterPosition());
                                status = object.getString("Bhargo_SyncStatus");
                            }
                            clickListener.onCustomClick(context, v, getAdapterPosition(), "Delete", status);
                        } catch (Exception e) {
                            Log.d(TAG, "onClick: " + Log.getStackTraceString(e));
                        }
                    }
                }
            });
            rl_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (clickListener != null) {
                        try {
                            String status = "3";
                            if (appMode != null && !appMode.equalsIgnoreCase("Online")) {
                                final JSONObject object = array.getJSONObject(getAdapterPosition());
                                status = object.getString("Bhargo_SyncStatus");
                            }
                            clickListener.onCustomClick(context, v, getAdapterPosition(), "Delete", status);
                        } catch (Exception e) {
                            Log.d(TAG, "onClick: " + Log.getStackTraceString(e));
                        }
                    }
                }
            });

           /* if (List_Index_Columns.size() == 2) {
                tvThirdControlValue.setVisibility(View.INVISIBLE);

            }*/
            if (List_Index_Columns.size() == 1) {
//                tvThirdControlValue.setVisibility(View.INVISIBLE);
                tvSecondControlValue.setVisibility(View.INVISIBLE);
            }

           /* if (style == 3) {
                LinearLayout layout_main = itemView.findViewById(R.id.layout_main);
                int itemWidth = (getWidth() / 100) * 85;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(10, 0, 10, 0);
                layout_main.setLayoutParams(params);
            }*/
            if(style == 3){
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.weight = 0.9f;
                layout_data.setLayoutParams(layoutParams);
            }
            if (style == 6) {
                if (!image) {
                    LinearLayout.LayoutParams layoutParamsOptions = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParamsOptions.weight = 0.3f;
                    layout_options.setLayoutParams(layoutParamsOptions);
                    LinearLayout.LayoutParams layoutParamsData = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParamsData.weight = 0.7f;
                    layout_data.setLayoutParams(layoutParamsData);
                }
                int itemWidth = (getWidth() / 100) * 85;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                layout_main.setPadding(5, 0, 0, 0);
                layoutParams.setMargins(10, 0, 0, 0);
                layout_main.setLayoutParams(layoutParams);
            }
        }
    }

    private int getWidth() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int pxWidth = displayMetrics.widthPixels;
        return pxWidth;
    }

    public String getCalendarValue(String calendarStr) {
        String defaultValue[] = calendarStr.split("T");
        return defaultValue[0];
    }

    @Override
    public int getItemViewType(int position) {
        try {
            return ((JSONObject) array.get(position)).names() == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return VIEW_TYPE_ITEM;
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {

        viewHolder.progressBar.setVisibility(View.VISIBLE);

    }


    private void populateItems(MyViewHolder holder, int position) {
        try {

            final JSONObject object = array.getJSONObject(position);
            if (!List_Index_Columns.get(3).equalsIgnoreCase("image_control_holder")) {
                if (file_type != null && file_type.equalsIgnoreCase(CONTROL_TYPE_CAMERA) && isNetworkStatusAvialable(context)) {
//                    Utils.loadImage(context, holder.iv_circle, object.getString(List_Index_Columns.get(3)));
                    Glide.with(context).load(object.getString(List_Index_Columns.get(3))).placeholder(ContextCompat.getDrawable(context, R.drawable.ic_icon_image)).into(holder.iv_circle);
                    if (strAppName.equalsIgnoreCase("Labour Employment Register")) {
                        Glide.with(context).load(object.getString(List_Index_Columns.get(3))).placeholder(ContextCompat.getDrawable(context, R.drawable.ic_icon_user)).into(holder.iv_circle);
                    } else {
                        Glide.with(context).load(object.getString(List_Index_Columns.get(3))).placeholder(ContextCompat.getDrawable(context, R.drawable.icons_image)).into(holder.iv_circle);
                    }
                } else if (file_type != null && file_type.equalsIgnoreCase(CONTROL_TYPE_CAMERA)) {
                    setImageFromSDCard(holder.iv_circle, (object.getString(List_Index_Columns.get(3))));
                } else if (file_type != null && file_type.equalsIgnoreCase(CONTROL_TYPE_FILE_BROWSING)) {
                    holder.iv_circle.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icons_filebrowse_drawable));
                }
            }

            if (!List_Index_Columns.get(0).equalsIgnoreCase("column_one_holder")) {
//                holder.tvfirstControlValue.setText(object.getString(List_Index_Columns.get(0)));
                setText(holder.tvfirstControlValue, List_Index_Columns.get(0), object.getString(List_Index_Columns.get(0)));
            } else {
                holder.tvfirstControlValue.setVisibility(View.GONE);
            }
            if (!List_Index_Columns.get(1).equalsIgnoreCase("column_two_holder")) {
//                holder.tvSecondControlValue.setText(object.getString(List_Index_Columns.get(1)));
                setText(holder.tvSecondControlValue, List_Index_Columns.get(1), object.getString(List_Index_Columns.get(1)));
            } else {
                holder.tvSecondControlValue.setVisibility(View.GONE);
            }
            if (!List_Index_Columns.get(2).equalsIgnoreCase("column_three_holder")) {
//                holder.tvThirdControlValue.setText(object.getString(List_Index_Columns.get(2)));
                setText(holder.tvThirdControlValue, List_Index_Columns.get(2), object.getString(List_Index_Columns.get(2)));
            } else {
                holder.tvThirdControlValue.setVisibility(View.GONE);
            }


        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

    public void setText(TextView view, String name, String value) {
        view.setText(replaceSpecialCharacters(value));
//        view.setText(value);


    }

    private String replaceSpecialCharacters(String controlValue) {

        return controlValue.replace("^", ",");
    }

    public String parseDateToddMMyyyy(String strdate) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.getDefault());

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(strdate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public JSONArray getData() {
        return array;
    }

    public Filter getFilter() {
        array = appDataArray;
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                try {
                    JSONArray jsonArray = new JSONArray();
                    // Add the filter code here
                    if (constraint != null && array != null) {
                        int length = array.length();
                        int i = 0;
                        while (i < length) {
                            JSONObject item = array.getJSONObject(i);
                            //do whatever you wanna do here
                            //adding result set output array
                            String key;
                            if (List_Index_Columns.contains("column_three_holder")) {
                                key = item.getString(List_Index_Columns.get(0)) + item.getString(List_Index_Columns.get(1));
                            } else {

                                key = item.getString(List_Index_Columns.get(0)) + item.getString(List_Index_Columns.get(1)) + item.getString(List_Index_Columns.get(2));
                            }
                            //item.name is user.name cause i want to search on name
                            if (key.toLowerCase().contains(constraint.toString().toLowerCase())) { // Add check here, and fill the tempList which shows as a result
                                jsonArray.put(item);
                            }
                            i++;
                        }
                        //following two lines is very important
                        //as publish result can only take FilterResults users
                        filterResults.values = jsonArray;
                        filterResults.count = jsonArray.length();
                    }

                } catch (Exception e) {
                    Log.d(TAG, "performFiltering: " + e.toString());
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence contraint, FilterResults results) {
                array = (JSONArray) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
//                notifyDataSetInvalidated();
                }
            }
        };
    }

    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            try {
                JSONArray jsonArray = new JSONArray();
                // Add the filter code here
                if (constraint != null && array != null) {
                    int length = array.length();
                    int i = 0;
                    while (i < length) {
                        JSONObject item = array.getJSONObject(i);
                        //do whatever you wanna do here
                        //adding result set output array
                        String key = item.getString(List_Index_Columns.get(0)) + item.getString(List_Index_Columns.get(0)) + item.getString(List_Index_Columns.get(0));
                        //item.name is user.name cause i want to search on name
                        if (key.contains(constraint.toString().toLowerCase())) { // Add check here, and fill the tempList which shows as a result
                            jsonArray.put(item);
                        }
                        i++;
                    }
                    //following two lines is very important
                    //as publish result can only take FilterResults users
                    filterResults.values = jsonArray;
                    filterResults.count = jsonArray.length();
                }

            } catch (Exception e) {
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {
            array = (JSONArray) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
//                notifyDataSetInvalidated();
            }
        }
    };


}
