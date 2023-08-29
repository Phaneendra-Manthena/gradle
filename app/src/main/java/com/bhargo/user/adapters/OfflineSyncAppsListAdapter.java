package com.bhargo.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.ItemClickListener;
import com.bhargo.user.pojos.OfflineDataSync;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class OfflineSyncAppsListAdapter extends RecyclerView.Adapter<OfflineSyncAppsListAdapter.MyViewHolder> {

    List<OfflineDataSync> AppsList;
    Context context;
    private ItemClickListener clickListener;

    public OfflineSyncAppsListAdapter(Context context, List<OfflineDataSync> AppsList) {
        this.context = context;
        this.AppsList = AppsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offline_sync_apps_list_item, parent, false);
        // set the view's size, margins, paddings and layout_sample_app parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    public void setCustomClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        final OfflineDataSync AppsDetails = AppsList.get(position);
        holder.name.setText(AppsDetails.getApp_name());
        holder.rec_count.setText(AppsDetails.getRec_count());

        if(AppsDetails.getSync_Type().equalsIgnoreCase("App")){
            holder.ctv_type.setText("App Name");
        }else{
            holder.ctv_type.setText("API/Form/SQL Name");
        }

    }

    @Override
    public int getItemCount() {
        return AppsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //        TextView name;
        CustomTextView name, rec_count,tvRequestlist,ctv_type;// init the item view's
        ImageView iv_options;
        LinearLayout ll_requests;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = itemView.findViewById(R.id.tvAppname);
            rec_count = itemView.findViewById(R.id.tvAppRecordCount);
            iv_options = itemView.findViewById(R.id.iv_options);
            tvRequestlist=itemView.findViewById(R.id.tvRequestlist);
            ll_requests=itemView.findViewById(R.id.ll_requests);
            ctv_type=itemView.findViewById(R.id.ctv_type);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            if (clickListener != null)
//                clickListener.onCustomClick(context,v, getAdapterPosition(), null);
            if (AppsList.get(getAdapterPosition()).getSync_Type().equalsIgnoreCase("App")) {
                String formString = AppsList.get(getAdapterPosition()).getPrepared_json_string();
                String formStringFiles = AppsList.get(getAdapterPosition()).getPrepared_files_json_string();
                String subformString = AppsList.get(getAdapterPosition()).getPrepared_json_string_subform();
                String subformStringFiles = AppsList.get(getAdapterPosition()).getPrepared_files_json_string_subform();
                if ((formString != null && !formString.isEmpty()) ||
                        (formStringFiles != null && !formStringFiles.isEmpty()) ||
                        (subformString != null && !subformString.isEmpty()) ||
                        (subformStringFiles != null && !subformStringFiles.isEmpty())) {

                    if (clickListener != null)
                        clickListener.onCustomClick(context, v, getAdapterPosition(), null);

                }
            }else  if (AppsList.get(getAdapterPosition()).getSync_Type().equalsIgnoreCase("Request")) {
                if (clickListener != null)
                clickListener.onCustomClick(context, v, getAdapterPosition(), null);
            }
        }
    }

    public void updateData(List<OfflineDataSync> AppsList){

        this.AppsList = AppsList;
        notifyDataSetChanged();
    }


}
