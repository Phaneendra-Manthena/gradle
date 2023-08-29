package com.bhargo.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Java_Beans.AppObjects;
import com.bhargo.user.R;
import com.bhargo.user.interfaces.ItemClickListener;

import java.util.ArrayList;


public class OnlyDayNameAdapter extends RecyclerView.Adapter<OnlyDayNameAdapter.MyViewHolder> {

    ArrayList<AppObjects> AppObjects;
    Context context;
    private int row_index;
    private ItemClickListener clickListener;

    public OnlyDayNameAdapter(Context context, ArrayList<AppObjects> AppObjects) {
        this.context = context;
        this.AppObjects = AppObjects;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.only_day_name_item, parent, false);
        // set the view's size, margins, paddings and layout_sample_app parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // set the data in items
        holder.tv_dayName.setText(AppObjects.get(position).getAPP_DayName());

        // implement setOnClickListener event on item view.
        holder.tv_dayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click

                row_index=position;
                notifyDataSetChanged();
                if (clickListener != null) clickListener.onCustomClick(context,view, position,null);
                Toast.makeText(context, AppObjects.get(position).getAPP_DayName(), Toast.LENGTH_SHORT).show();
            }
        });
        if(row_index == position){
            holder.tv_dayName.setTextColor(context.getResources().getColor(R.color.red));
            holder.tv_dayName.setTextSize(25);
        }else{
            holder.tv_dayName.setTextColor(context.getResources().getColor(R.color.black));
            holder.tv_dayName.setTextSize(20);
        }

    }

    @Override
    public int getItemCount() {
        return AppObjects.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_dayName;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_dayName = itemView.findViewById(R.id.tv_dayName);

        }
    }
    public void setCustomClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
