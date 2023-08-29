package com.bhargo.user.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.pojos.PostsMasterModel;

import java.util.List;

public class PostSelectionAdapter extends RecyclerView.Adapter<PostSelectionAdapter.ViewHolder> {

    private final List<PostsMasterModel> mData;
//        private List<UserPostDetails> mData;
        private LayoutInflater mInflater;
        private ItemClickListener mClickListener;

        // data is passed into the constructor
        public PostSelectionAdapter(Context context, List<PostsMasterModel> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        // inflates the row layout from xml when needed
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_post, parent, false);
            return new ViewHolder(view);
        }



    // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            PostsMasterModel spinnerData = mData.get(position);
            holder.myTextView.setText(spinnerData.getName());
        }

        // total number of rows
        @Override
        public int getItemCount() {
            if(mData==null){
                return 0;}else{
            return mData.size();}
        }

    // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView myTextView;

            ViewHolder(View itemView) {
                super(itemView);
                myTextView = itemView.findViewById(R.id.tvPostName);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        // convenience method for getting data at click position
       public String getItem(int id) {
            return mData.get(id).getID();
        }


    // convenience method for getting data at click position
    public String getItemName(int id) {
        return mData.get(id).getName();
    }
    public String getItemType(int id) {
        return mData.get(id).getUserType();
    }
    public String getItemTypeId(int id) {
        return mData.get(id).getID();
    }
    public String getItemLoginTypeId(int id) {
        return mData.get(id).getLoginTypeID();
    }


    // allows clicks events to be caught
        public void setClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }

        // parent activity will implement this method to respond to click events
        public interface ItemClickListener {
            void onItemClick(View view, int position);
        }
    }
