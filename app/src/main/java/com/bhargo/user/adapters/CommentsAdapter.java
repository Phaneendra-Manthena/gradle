package com.bhargo.user.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.CommentsInfoModel;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CadViewHolder> {

    private static final String TAG = "CommentsAdapter";
    Context context;
    List<CommentsInfoModel> commentsInfoModelList;
    RecyclerView rv_comments;
    String strInTaskCreatedBy;

    public CommentsAdapter(Context context, List<CommentsInfoModel> commentsInfoModelList, RecyclerView rv_comments, String strInTaskCreatedBy) {
        this.context = context;
        this.commentsInfoModelList = commentsInfoModelList;
        this.rv_comments = rv_comments;
        this.strInTaskCreatedBy = strInTaskCreatedBy;
    }

    @NonNull
    @Override
    public CommentsAdapter.CadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_list_item, parent, false);
        // set the view's size, margins, paddings and layout_sample_app parameters
        CommentsAdapter.CadViewHolder vh = new CommentsAdapter.CadViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.CadViewHolder holder, int position) {
        if (commentsInfoModelList.get(position).getImagePath() != null && !commentsInfoModelList.get(position).getImagePath().isEmpty()) {
            Log.d(TAG, "onBindViewHolderImg: "+commentsInfoModelList.get(position).getImagePath());
            Glide.with(context).load(commentsInfoModelList.get(position).getImagePath()).into(holder.iv_circle);
        } else {
            holder.iv_circle.setImageDrawable((context.getDrawable(R.drawable.default_comment_img)));
        }
        if(position == 0){
            holder.tvTasks_name.setText(strInTaskCreatedBy);
        }else{

            holder.tvTasks_name.setText(commentsInfoModelList.get(position).getEmpName());
        }
        holder.tvTasksDesc.setText(commentsInfoModelList.get(position).getTaskComments());
        holder.tvTasksStatus.setText(commentsInfoModelList.get(position).getTaskStatus());
        holder.tvTasksDate.setText(commentsInfoModelList.get(position).getDisplayDate());
    }

    @Override
    public int getItemCount() {

        return commentsInfoModelList.size();

    }

    public void refreshItems(List<CommentsInfoModel> toDoList) {
        this.commentsInfoModelList.addAll(toDoList);
        notifyDataSetChanged();
        rv_comments.smoothScrollToPosition(commentsInfoModelList.size() - 1);
    }


    public class CadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView iv_circle;
        CustomTextView tvTasks_name, tvTasksDesc, tvTasksStatus, tvTasksDate;

        public CadViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_circle = itemView.findViewById(R.id.iv_circle);
            tvTasks_name = itemView.findViewById(R.id.tvTasks_name);
            tvTasksDesc = itemView.findViewById(R.id.tvTasksDesc);
            tvTasksStatus = itemView.findViewById(R.id.tvTasksStatus);
            tvTasksDate = itemView.findViewById(R.id.tvTasksDate);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {


        }
    }
//public void refreshItems(List<TaskCmtDataModel> todolist){
//        this.taskCmtDataModelList.addAll(todolist);
////        Collections.reverse(taskCmtDataModelList);
//        notifyDataSetChanged();
//    }


}