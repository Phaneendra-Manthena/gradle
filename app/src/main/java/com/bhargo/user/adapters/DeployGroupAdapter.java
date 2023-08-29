package com.bhargo.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;

import java.util.List;


public class DeployGroupAdapter extends RecyclerView.Adapter<DeployGroupAdapter.MyViewHolder> {

    List<String> AppsList;
    Context context;
    String strFromWhere;


    public DeployGroupAdapter(Context context, List<String> AppsList) {
        this.context = context;
        this.AppsList = AppsList;
    }

    public DeployGroupAdapter(Context context, List<String> AppsList, String strFromWhere) {
        this.context = context;
        this.AppsList = AppsList;
        this.strFromWhere = strFromWhere;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_individual, parent, false);
        // set the view's size, margins, paddings and layout_sample_app parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items

        holder.ct_personName.setText(AppsList.get(position));
        if(strFromWhere != null) {
            if (strFromWhere.equalsIgnoreCase("TaskApps")) {
                holder.circleImageView.setImageResource(R.drawable.allapps);
            } else if (strFromWhere.equalsIgnoreCase("TaskContent")) {
                holder.circleImageView.setImageResource(R.drawable.icon_study);
            }else {
                holder.circleImageView.setImageResource(R.drawable.icon_group);
            }
        }else {
            holder.circleImageView.setImageResource(R.drawable.icon_group);
        }
        //Glide.with(context).load(AppsDetails.getAppIcon()).into(holder.iv_circle);

//        Glide.with(context).load("")
//
//                .placeholder(context.getResources().getDrawable(R.drawable.allapps)).into(holder.iv_circle);


    }

    @Override
    public int getItemCount() {
        return AppsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //        TextView name;
        ImageView circleImageView;
        CustomTextView ct_personName, ct_designation;// init the item view's


        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            circleImageView = itemView.findViewById(R.id.iv_circle_d);
            ct_personName = itemView.findViewById(R.id.ct_personName);
            ct_designation = itemView.findViewById(R.id.ct_designation);

            ct_designation.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(context, DeploymentSecondActivity.class);
//            intent.putExtra("AppNameDeploymentSecond",name.getText().toString());
//            context.startActivity(intent);
        }
    }
}
