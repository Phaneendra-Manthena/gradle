package com.bhargo.user.adapters;

import static com.bhargo.user.utils.AppConstants.VIEW_TYPE_ITEM;
import static com.bhargo.user.utils.AppConstants.VIEW_TYPE_LOADING;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
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

import com.bhargo.user.Java_Beans.DataManagementOptions;
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


public class AppsAdvancedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private static final String TAG = "AppsAdvancedAdapter";
    Context context;
    SessionManager sessionManager;
    private ItemClickListenerAdvanced clickListener;
    private JSONArray array;
    private JSONArray appDataArray;
    boolean  imageExists;


    String strAppName;
    boolean searchFilter = false;
    String tableName = "";
    String appMode = "";
    DataManagementOptions dataManagementOptions;
    int style = 0;

    public AppsAdvancedAdapter(Context context, JSONArray array,  String strAppName, String tableName, String appMode, DataManagementOptions dataManagementOptions) {

        this.strAppName = strAppName;
        this.context = context;
        this.array = array;
        this.tableName = tableName;
        this.appMode = appMode;
        sessionManager = new SessionManager(context);
        appDataArray = array;
        this.dataManagementOptions = dataManagementOptions;
        style = dataManagementOptions.getIndexPageDetails().getIndexPageTemplateId();
        this.imageExists = checkImageExists();
    }

    private boolean checkImageExists() {
        boolean imageExists = false;
        if(dataManagementOptions.getIndexPageDetails().getProfileImage() != null && !dataManagementOptions.getIndexPageDetails().getProfileImage().isEmpty()||
                dataManagementOptions.getIndexPageDetails().getImage() != null && !dataManagementOptions.getIndexPageDetails().getImage().isEmpty()){
            return true;
        }
        return imageExists;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == VIEW_TYPE_ITEM) {
            if (style == 1) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_page_style_five, parent, false);
            } else if (style == 2) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_page_style_six, parent, false);
            } else if (style == 3) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_page_style_two, parent, false);
            } else if (style == 4) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_page_style_four, parent, false);
            } else if (style == 5) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_page_style_one, parent, false);
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
            tvThirdControlValue.setMaxLines(2);
            iv_edit = itemView.findViewById(R.id.iv_edit);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            iv_circle = itemView.findViewById(R.id.iv_circle);
            rl_delete = itemView.findViewById(R.id.rl_delete);
            rl_edit = itemView.findViewById(R.id.rl_edit);

            if (dataManagementOptions.isEnableEditData()) {
                rl_edit.setVisibility(View.VISIBLE);
            } else {
                rl_edit.setVisibility(View.GONE);
            }

            if (dataManagementOptions.isEnableDeleteData()) {
                rl_delete.setVisibility(View.VISIBLE);
            } else {
                rl_delete.setVisibility(View.GONE);
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


//            if(style == 3){
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
//                layoutParams.weight = 0.9f;
//                layout_data.setLayoutParams(layoutParams);
//            }
            if (imageExists) {
                layout_image.setVisibility(View.VISIBLE);
            } else {
                layout_image.setVisibility(View.GONE);
                if (style == 1|| style==2) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.weight = 0.8f;
                    layout_data.setLayoutParams(layoutParams);
                }  else if (style == 3) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.weight = 0.75f;
                    layout_data.setLayoutParams(layoutParams);
                }
            }

            if (style == 6) {
                if (!imageExists) {
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

            setUISettings(tvfirstControlValue,tvSecondControlValue,tvThirdControlValue);
        }
    }

    private void setUISettings(CustomTextView tvfirstControlValue, CustomTextView tvSecondControlValue, CustomTextView tvThirdControlValue) {

        tvfirstControlValue.setTextSize(Integer.parseInt(dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().getHeaderFontSize()));
        tvfirstControlValue.setTextColor(Color.parseColor(dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().getHeaderFontColor()));
        setFontStyle(dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().getHeaderFontStyle(), tvfirstControlValue);

        tvSecondControlValue.setTextSize(Integer.parseInt(dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().getSubHeaderFontSize()));
        tvSecondControlValue.setTextColor(Color.parseColor(dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().getSubHeaderFontColor()));
        setFontStyle(dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().getSubHeaderFontStyle(), tvSecondControlValue);

        if(dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().getDescriptionFontSize()!=null){
        tvThirdControlValue.setTextSize(Integer.parseInt(dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().getDescriptionFontSize()));
        }
        if(dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().getDescriptionFontColor()!=null){
        tvThirdControlValue.setTextColor(Color.parseColor(dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().getDescriptionFontColor()));}
        if(dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().getDescriptionFontStyle()!=null){
        setFontStyle(dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().getDescriptionFontStyle(), tvThirdControlValue);
        }

    }

    public void setFontStyle(String style, TextView textView) {
        if (style.equalsIgnoreCase("Bold")) {
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "Satoshi-Bold.otf");
            textView.setTypeface(tf);
        } else if (style.equalsIgnoreCase("Italic")) {
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "Satoshi-Italic.otf");
            textView.setTypeface(tf);
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
            String imagePath = getImagePath(object);
            if (imagePath!=null && !imagePath.isEmpty()) {
                if (isNetworkStatusAvialable(context)) {
                    Glide.with(context).load(imagePath).placeholder(ContextCompat.getDrawable(context, R.drawable.icons_image)).into(holder.iv_circle);
                } else {
                    setImageFromSDCard(holder.iv_circle, (imagePath));
                }
            }

            if (dataManagementOptions.getIndexPageDetails().getHeader()!=null && !dataManagementOptions.getIndexPageDetails().getHeader().isEmpty()) {
                setText(holder.tvfirstControlValue, object.getString(dataManagementOptions.getIndexPageDetails().getHeader()));
            } else {
                holder.tvfirstControlValue.setVisibility(View.GONE);
            }
            if (dataManagementOptions.getIndexPageDetails().getSubHeader()!=null && !dataManagementOptions.getIndexPageDetails().getSubHeader().isEmpty()) {
                setText(holder.tvSecondControlValue, object.getString(dataManagementOptions.getIndexPageDetails().getSubHeader()));
            } else {
                holder.tvSecondControlValue.setVisibility(View.GONE);
            }
            if (dataManagementOptions.getIndexPageDetails().getDescriptionList()!=null && dataManagementOptions.getIndexPageDetails().getDescriptionList().size()>0) {
                setDescriptionText(holder.tvThirdControlValue, object,dataManagementOptions.getIndexPageDetails().getDescriptionList());
            } else {
                holder.tvThirdControlValue.setVisibility(View.GONE);
            }


        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

    private String getImagePath(JSONObject object) {
        String imagePath = null;
        try {
            if(style==5){
                if(object.has(dataManagementOptions.getIndexPageDetails().getImage())){
                    imagePath =  object.getString(dataManagementOptions.getIndexPageDetails().getImage());
                }

            }else{
                if(object.has(dataManagementOptions.getIndexPageDetails().getProfileImage())){
                    imagePath =  object.getString(dataManagementOptions.getIndexPageDetails().getProfileImage());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imagePath;
    }

    private void setDescriptionText(CustomTextView view, JSONObject object, List<String> descriptionList) {
        try {
            StringBuilder txt = new StringBuilder();
            for(String descriptionText:descriptionList){
                 txt.append(object.getString(descriptionText)).append(" ");
               /* StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(object.getString(descriptionText));*/

        }
            view.setText(replaceSpecialCharacters(String.valueOf(txt)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setText(TextView view, String value) {
        view.setText(replaceSpecialCharacters(value));


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
                            String key = item.getString(dataManagementOptions.getIndexPageDetails().getHeader()) + item.getString(dataManagementOptions.getIndexPageDetails().getSubHeader());
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
                        String key = item.getString(dataManagementOptions.getIndexPageDetails().getHeader()) + item.getString(dataManagementOptions.getIndexPageDetails().getSubHeader());
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
