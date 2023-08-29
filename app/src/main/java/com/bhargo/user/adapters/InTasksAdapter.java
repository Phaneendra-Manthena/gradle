package com.bhargo.user.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AppsInfoModel;
import com.bhargo.user.pojos.CommentsInfoModel;
import com.bhargo.user.pojos.ContentInfoModel;
import com.bhargo.user.pojos.InTaskDataModel;
import com.bhargo.user.screens.InTaskDetailsActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class InTasksAdapter extends RecyclerView.Adapter<InTasksAdapter.MyViewHolder> {

    private static final String TAG = "InTasksAdapter";
    private final GetServices getServices;
    Context context;
    List<InTaskDataModel> inTaskDataModels;
    SessionManager sessionManager;
    ImproveHelper improveHelper;
    ImproveDataBase improveDataBase;
    private List<AppsInfoModel> appsInfoModelList;
    private List<ContentInfoModel> contentInfoModelList;
    private List<CommentsInfoModel> commentsInfoModelList;

    public InTasksAdapter(Context context, List<InTaskDataModel> inTaskDataModels) {

        this.context = context;
        this.inTaskDataModels = inTaskDataModels;
//        sessionManager = new SessionManager(context);
        getServices = RetrofitUtils.getUserService();
        improveHelper = new ImproveHelper(context);
        improveDataBase = new ImproveDataBase(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.intasks_list_item, parent, false);
        // set the view's size, margins, paddings and layout_sample_app parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder

        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tvTasks_createdBy.setText(inTaskDataModels.get(position).getCreatedBy());
        holder.tvTasksCreatedDate.setText(inTaskDataModels.get(position).getDistributionDisplayDate());
        holder.tvTasks_name.setText(inTaskDataModels.get(position).getTaskName());
        holder.tvTasksDesc.setText(inTaskDataModels.get(position).getTaskDesc());
        holder.tvTasksStatus.setText(inTaskDataModels.get(position).getTaskStatus());
        Log.d(TAG, "onBindViewHolderTaskId: " + inTaskDataModels.get(position).getTaskID());

    }

    @Override
    public int getItemCount() {
        return inTaskDataModels.size();
    }


    public void filterList(List<InTaskDataModel> inTaskDataModels) {
        this.inTaskDataModels = inTaskDataModels;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        CustomTextView tvTasks_createdBy, tvTasks_name, tvTasksDesc, tvTasksStatus, tvTasksCreatedDate;
//        ImageView iv_modifyInTask;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTasks_createdBy = itemView.findViewById(R.id.tvTasks_createdBy);
            tvTasks_name = itemView.findViewById(R.id.tvTasks_name);
            tvTasksDesc = itemView.findViewById(R.id.tvTasksDesc);
            tvTasksStatus = itemView.findViewById(R.id.tvTasksStatus);
            tvTasksCreatedDate = itemView.findViewById(R.id.tvTasksCreatedDate);
//            iv_modifyInTask = itemView.findViewById(R.id.iv_modify);

            itemView.setOnClickListener(this);
//            iv_modifyInTask.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v == itemView) {

                if(improveHelper.isDateLiesBetweenTwoDates(inTaskDataModels.get(getAdapterPosition()).getStartDate(),inTaskDataModels.get(getAdapterPosition()).getEndDate())
                ||improveHelper.isGivenDateIsEqualsToCurrent(inTaskDataModels.get(getAdapterPosition()).getStartDate())
                ||improveHelper.isGivenDateIsEqualsToCurrent(inTaskDataModels.get(getAdapterPosition()).getEndDate())){
                    Gson gsonApps = new Gson();
                    Gson gsonContent = new Gson();
                    /*List<AppsInfoModel> appsInfoModelList = null;
                    if(inTaskDataModels.get(getAdapterPosition()).getAppInfo() != null && !inTaskDataModels.get(getAdapterPosition()).getAppInfo().equalsIgnoreCase("")){
                    Type collectionType = new TypeToken<Collection<AppsInfoModel>>() {}.getType();
                    appsInfoModelList = gson.fromJson(inTaskDataModels.get(getAdapterPosition()).getAppInfo(), collectionType);
                    if (appsInfoModelList != null && appsInfoModelList.size() > 0) {
                        Log.d(TAG, "AppsInfoList: " + appsInfoModelList.get(0).getACName());
                    }
                    }*/
                    appsInfoModelList = Arrays.asList(gsonApps.fromJson(inTaskDataModels.get(getAdapterPosition()).getAppInfo(), AppsInfoModel[].class));
                    contentInfoModelList = Arrays.asList(gsonContent.fromJson(inTaskDataModels.get(getAdapterPosition()).getFilesInfo(), ContentInfoModel[].class));
                    commentsInfoModelList = Arrays.asList(gsonContent.fromJson(inTaskDataModels.get(getAdapterPosition()).getCommentsInfo(), CommentsInfoModel[].class));
                    Intent intent = new Intent(context, InTaskDetailsActivity.class);
                    intent.putExtra("in_task_created_by", inTaskDataModels.get(getAdapterPosition()).getCreatedBy());
                    intent.putExtra("in_task_id", inTaskDataModels.get(getAdapterPosition()).getTaskID());
                    intent.putExtra("in_task_status_id", inTaskDataModels.get(getAdapterPosition()).getTaskStatusId());
                    intent.putExtra("in_task_is_single_user", inTaskDataModels.get(getAdapterPosition()).getIsSingleUser());
                    intent.putExtra("in_task_single_user_status", inTaskDataModels.get(getAdapterPosition()).getSingleUserStatus());
                    intent.putExtra("in_task_name", inTaskDataModels.get(getAdapterPosition()).getTaskName());
                    intent.putExtra("in_task_desc", inTaskDataModels.get(getAdapterPosition()).getTaskDesc());
                    intent.putExtra("in_task_start_time", inTaskDataModels.get(getAdapterPosition()).getStartDate());
                    intent.putExtra("in_task_end_time", inTaskDataModels.get(getAdapterPosition()).getEndDate());
                    intent.putExtra("in_tasks_priority", inTaskDataModels.get(getAdapterPosition()).getPriority());
                    intent.putExtra("in_task_app_list", (Serializable) appsInfoModelList);
                    intent.putExtra("in_task_content_list", (Serializable) contentInfoModelList);
                    intent.putExtra("in_task_comments", (Serializable) commentsInfoModelList);
                    intent.putExtra("in_task_task_status", inTaskDataModels.get(getAdapterPosition()).getTaskStatus());
                    intent.putExtra("in_task_total_in_progress", inTaskDataModels.get(getAdapterPosition()).getTotalInprogress());
                    intent.putExtra("in_task_total_completed", inTaskDataModels.get(getAdapterPosition()).getTotalCompleted());
                    intent.putExtra("in_task_single_user_info", inTaskDataModels.get(getAdapterPosition()).getSingleUserInfo());
                    intent.putExtra("in_task_distribution_date", inTaskDataModels.get(getAdapterPosition()).getDistributionDate());
                    intent.putExtra("in_task_distribution_id", inTaskDataModels.get(getAdapterPosition()).getDistrubutionID());
                    context.startActivity(intent);

                }else{

                    ImproveHelper.showToast(context,"Task date is not started/expired");

                }

            }
        }
    }

}
