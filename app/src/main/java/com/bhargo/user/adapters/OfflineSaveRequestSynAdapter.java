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
import com.bhargo.user.pojos.OfflineSaveRequestPojo;
import com.bhargo.user.utils.ImproveDataBase;

import java.util.ArrayList;
import java.util.List;


public class OfflineSaveRequestSynAdapter extends RecyclerView.Adapter<OfflineSaveRequestSynAdapter.MyViewHolder> implements Filterable {

    List<OfflineSaveRequestPojo> list;
    List<OfflineSaveRequestPojo> listFilter;
    Activity context;
    ImproveDataBase improveDataBase;
    CustomTextView tv_nodata;
    OfflineSaveRequestSynAdapterListener offlineSaveRequestSynAdapterListener;

    public OfflineSaveRequestSynAdapter(List<OfflineSaveRequestPojo> list, Activity context, CustomTextView tv_nodata,
                                        OfflineSaveRequestSynAdapterListener offlineSaveRequestSynAdapterListener) {
        this.list = list;
        this.listFilter = list;
        this.context = context;
        this.tv_nodata = tv_nodata;
        this.offlineSaveRequestSynAdapterListener = offlineSaveRequestSynAdapterListener;
        improveDataBase = new ImproveDataBase(context);

    }

    public List<OfflineSaveRequestPojo> getListFilter() {
        return listFilter;
    }

    public void setListFilter(List<OfflineSaveRequestPojo> listFilter) {
        this.listFilter = listFilter;
    }

    @Override
    public OfflineSaveRequestSynAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.offline_saverequest_sync_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OfflineSaveRequestSynAdapter.MyViewHolder holder, int position) {

        final OfflineSaveRequestPojo model = listFilter.get(position);
        holder.ctv_ActionType.setText(model.getActionType());
        if (model.getQueryType().equals("DirectSQL")) {
            holder.tv_TypeOfInput.setText(model.getQueryType().trim() + "(" + model.getActionName() + ")");
        } else {
            if (model.getTypeOfInput() != null && model.getTypeOfInput().trim().equals("")) {
                holder.tv_TypeOfInput.setText(model.getQueryType().trim());
            } else {
                holder.tv_TypeOfInput.setText(model.getQueryType().trim() + "(" + model.getTypeOfInput() + ")");
            }

        }

        holder.tv_tableName.setText(model.getExistingTableName());
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
                    List<OfflineSaveRequestPojo> filteredList = new ArrayList<>();
                    for (OfflineSaveRequestPojo row : list) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getActionType().toLowerCase().contains(charString.toLowerCase())) {
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
                listFilter = (ArrayList<OfflineSaveRequestPojo>) filterResults.values;
                if (listFilter.size() > 0) {
                    tv_nodata.setVisibility(View.GONE);
                } else {
                    tv_nodata.setVisibility(View.VISIBLE);
                }
                notifyDataSetChanged();
            }
        };
    }

    public interface OfflineSaveRequestSynAdapterListener {
        void onSelectedItem(OfflineSaveRequestPojo offlineSaveRequestPojo, int selectedPostion);

        void onSelectedDeleteItem(OfflineSaveRequestPojo offlineSaveRequestPojo, int selectedPostion);

        void onSelectedSendItem(OfflineSaveRequestPojo offlineSaveRequestPojo, int selectedPostion);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomTextView ctv_ActionType, tv_TypeOfInput;
        ImageView iv_delete, iv_send;
        TextView tv_tableName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ctv_ActionType = itemView.findViewById(R.id.ctv_ActionType);
            tv_TypeOfInput = itemView.findViewById(R.id.tv_TypeOfInput);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            iv_send = itemView.findViewById(R.id.iv_send);
            tv_tableName = itemView.findViewById(R.id.tv_tableName);

            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    offlineSaveRequestSynAdapterListener.onSelectedDeleteItem(listFilter.get(getAdapterPosition()), getAdapterPosition());
                }
            });

            iv_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    offlineSaveRequestSynAdapterListener.onSelectedSendItem(listFilter.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
