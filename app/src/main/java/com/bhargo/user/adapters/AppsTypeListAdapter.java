package com.bhargo.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.PageNamesList;
import com.bhargo.user.screens.AppTypeListActivity;

import com.bumptech.glide.Glide;

import java.util.List;


public class AppsTypeListAdapter extends RecyclerView.Adapter<AppsTypeListAdapter.MyViewHolder> {

    private static final String TAG = "AppsTypeListAdapter";
    List<PageNamesList> AppsList;
    Context context;
    IClickListenerApp iClickListener;
    int selectedPos;
    String type,id;
    public AppsTypeListAdapter(Context context, List<PageNamesList> AppsList,String type,String id) {
        this.context = context;
        this.AppsList = AppsList;
        this.type = type;
        this.id = id;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.apps_list_item_deploy, parent, false);
        // set the view's size, margins, paddings and layout_sample_app parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        final PageNamesList AppsDetails = AppsList.get(position);
        holder.name.setText(AppsDetails.getPageName());
        holder.desc.setText(AppsDetails.getCreatedBy());
//        holder.iv_circle.set(AppsDetails.getCreatedBy());
        Glide.with(context).load(AppsDetails.getAppIcon())
                .dontAnimate()
                .placeholder(context.getResources()
                        .getDrawable(R.drawable.round_rect_shape_ds))
                .into(holder.iv_circle);
//
//        Glide.with(context).load("")
//
//                .placeholder(context.getResources().getDrawable(R.drawable.allapps)).into(holder.iv_circle);

        if(selectedPos == position){
            holder.iv_check.setVisibility(View.VISIBLE);
        }

        holder.bind(AppsDetails, AppsList);

    }

    @Override
    public int getItemCount() {
        return AppsList.size();
    }

    public void updateList(List<PageNamesList> temp) {
        AppsList = temp;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //        TextView name;
        CustomTextView name, desc;// init the item view's

        ImageView iv_circle;
        ImageView iv_check;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = itemView.findViewById(R.id.tvAppname);
            desc = itemView.findViewById(R.id.tvAppDesc);
            iv_check = itemView.findViewById(R.id.iv_check);
            iv_circle = itemView.findViewById(R.id.iv_circle);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(context, DeploymentSecondActivity.class);
//            intent.putExtra("AppNameDeploymentSecond", name.getText().toString());
//            context.startActivity(intent);

            iClickListener.onItemClickedAPP(name.getText().toString(),type,id);
        }

        void bind(final PageNamesList pageNamesList, List<PageNamesList> pageNamesLists) {
            iv_check.setVisibility(pageNamesList.isChecked() ? View.VISIBLE : View.GONE);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppTypeListActivity.appName = null;
                    notifyItemChanged(selectedPos);
                    selectedPos = getLayoutPosition();
                    notifyItemChanged(selectedPos);
                    for (PageNamesList pageNamesList : pageNamesLists) {
                        pageNamesList.setChecked(false);
                    }
                    pageNamesList.setChecked(!pageNamesList.isChecked());
                    iClickListener.onItemClickedAPP(name.getText().toString(),type,id);
//                    iv_check.setVisibility(employee.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    public void setIClickListener(IClickListenerApp iClickListener) {
        this.iClickListener = iClickListener;
    }

    public interface IClickListenerApp {
        void onItemClickedAPP(String app_name,String type,String id);


    }

}
