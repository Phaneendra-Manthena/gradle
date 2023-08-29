package com.bhargo.user.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.OfflineHybridAppListPojo;
import com.bhargo.user.utils.ImproveDataBase;

import java.util.ArrayList;
import java.util.List;


public class OfflineHybridSynAdapter extends RecyclerView.Adapter<OfflineHybridSynAdapter.MyViewHolder> implements Filterable {

    List<OfflineHybridAppListPojo> list;

    public List<OfflineHybridAppListPojo> getListFilter() {
        return listFilter;
    }

    public void setListFilter(List<OfflineHybridAppListPojo> listFilter) {
        this.listFilter = listFilter;
    }

    List<OfflineHybridAppListPojo> listFilter;
    Activity context;
    ImproveDataBase improveDataBase;
    CustomTextView tv_nodata;
    OfflineHybridSynAdapterListener offlineHybridSynAdapterListener;

    public OfflineHybridSynAdapter(List<OfflineHybridAppListPojo> list, Activity context, CustomTextView tv_nodata,
                                   OfflineHybridSynAdapterListener offlineHybridSynAdapterListener) {
        this.list = list;
        this.listFilter = list;
        this.context = context;
        this.tv_nodata = tv_nodata;
        this.offlineHybridSynAdapterListener = offlineHybridSynAdapterListener;
        improveDataBase = new ImproveDataBase(context);

    }

    @Override
    public OfflineHybridSynAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.offline_hybrid_sync_apps_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OfflineHybridSynAdapter.MyViewHolder holder, int position) {

        final OfflineHybridAppListPojo model = listFilter.get(position);

        holder.tvAppname.setText(model.getAppName().trim());

        if (improveDataBase.tableExists(model.getTableName())) {
            int count = improveDataBase.getCountByValue(model.getTableName(), "Bhargo_SyncStatus", "0");

            if (count == 0) {
                holder.iv_sync.setVisibility(View.VISIBLE);
                holder.iv_deletelist.setVisibility(View.GONE);
                holder.iv_send.setVisibility(View.GONE);
            } else {
                holder.iv_sync.setVisibility(View.GONE);
                holder.iv_deletelist.setVisibility(View.VISIBLE);
                holder.iv_send.setVisibility(View.VISIBLE);
            }
            holder.tvAppRecordCount.setText(count + "");
            listFilter.get(position).setTableNameRecordsCount(count+"");
            int syncount = improveDataBase.getCountByValue(model.getTableName(), "Bhargo_SyncStatus", "2");
            if (syncount == 0) {
                holder.tv_sync_records.setVisibility(View.GONE);
            } else {
                holder.tv_sync_records.setVisibility(View.VISIBLE);
                holder.tv_sync_records.setText(""+syncount);
            }
        } else {
            holder.tvAppRecordCount.setText("-");
            listFilter.get(position).setTableNameRecordsCount("-");
            holder.iv_sync.setVisibility(View.VISIBLE);
            holder.iv_deletelist.setVisibility(View.GONE);
            holder.iv_send.setVisibility(View.GONE);
            holder.tv_sync_records.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return listFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listFilter = list;
                } else {
                    List<OfflineHybridAppListPojo> filteredList = new ArrayList<>();
                    for (OfflineHybridAppListPojo row : list) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getAppName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    listFilter = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listFilter = (ArrayList<OfflineHybridAppListPojo>) filterResults.values;
                if (listFilter.size() > 0) {
                    tv_nodata.setVisibility(View.GONE);
                } else {
                    tv_nodata.setVisibility(View.VISIBLE);
                }
                notifyDataSetChanged();
            }
        };
    }

    public interface OfflineHybridSynAdapterListener {
        void onSelectedItem(OfflineHybridAppListPojo offlineHybridAppListPojo, int selectedPostion);

        void onSelectedDeleteItem(OfflineHybridAppListPojo offlineHybridAppListPojo, int selectedPostion);

        void onSelectedSendItem(OfflineHybridAppListPojo offlineHybridAppListPojo, int selectedPostion);

        void onSelectedSyncItem(OfflineHybridAppListPojo offlineHybridAppListPojo, int selectedPostion);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomTextView tvAppname, tvAppRecordCount;
        ImageView iv_deletelist, iv_send, iv_sync;
        TextView tv_sync_records;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvAppname = itemView.findViewById(R.id.tvAppname);
            tvAppRecordCount = itemView.findViewById(R.id.tvAppRecordCount);
            iv_deletelist = itemView.findViewById(R.id.iv_deletelist);
            iv_send = itemView.findViewById(R.id.iv_send);
            iv_sync = itemView.findViewById(R.id.iv_sync);
            tv_sync_records = itemView.findViewById(R.id.tv_sync_records);
            iv_deletelist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    offlineHybridSynAdapterListener.onSelectedDeleteItem(listFilter.get(getAdapterPosition()), getAdapterPosition());
                }
            });

            iv_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    offlineHybridSynAdapterListener.onSelectedSendItem(listFilter.get(getAdapterPosition()), getAdapterPosition());
                }
            });

            iv_sync.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    offlineHybridSynAdapterListener.onSelectedSyncItem(listFilter.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
