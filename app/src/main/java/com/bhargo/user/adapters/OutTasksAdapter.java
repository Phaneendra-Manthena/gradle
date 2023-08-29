package com.bhargo.user.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.OutTaskDataModel;
import com.bhargo.user.screens.CreateNewTaskActivity;
import com.bhargo.user.screens.OutTaskCommentsActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OutTasksAdapter extends RecyclerView.Adapter<OutTasksAdapter.MyViewHolder> {

    private static final String TAG = "InTasksAdapter";

    Context context;
    List<OutTaskDataModel> outTaskDataModels;
    ImproveHelper improveHelper;


    public OutTasksAdapter(Context context, List<OutTaskDataModel> outTaskDataModels) {

        this.context = context;
        this.outTaskDataModels = outTaskDataModels;

        improveHelper = new ImproveHelper(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.out_tasks_list_item, parent, false);
        // set the view's size, margins, paddings and layout_sample_app parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder

        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tvTasks_name.setText(outTaskDataModels.get(position).getTaskName());
        holder.tvTasksDesc.setText(outTaskDataModels.get(position).getTaskDesc());
        holder.tvTasksStatus.setText(outTaskDataModels.get(position).getTaskStatus());
        holder.tvTasksCreatedDate.setText(outTaskDataModels.get(position).getDistributionDate());

        Log.d(TAG, "onBindViewHolderEndDateCheck: " + ImproveHelper.getCurrentDateFromHelper() + "- " + outTaskDataModels.get(position).getEndDate());


    }

    @Override
    public int getItemCount() {
        return outTaskDataModels.size();
    }

    public void updateList(List<OutTaskDataModel> list) {
        this.outTaskDataModels = list;
        notifyDataSetChanged();
    }

    public void filterList(List<OutTaskDataModel> outTaskDataModels) {
        this.outTaskDataModels = outTaskDataModels;
        notifyDataSetChanged();
    }

    private void GetTaskAttempts(Context context, String userId, String orgId, String postId, String taskId, int getAdapterPosition) {
        ImproveHelper improveHelper = new ImproveHelper(context);
        improveHelper.showProgressDialog("Please wait...!");
        GetServices getServices = RetrofitUtils.getUserService();
        Call<ResponseBody> responseCall = getServices.getTaskAttempts(userId, orgId, "", taskId);
        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String json = null;
                try {
                    json = response.body().string();
                    JSONArray jsonArray = new JSONArray(json.trim());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        AppConstants.TASK_ATTEMPTS_BY = jsonObject.getString("Attempts");
                        Log.d(TAG, "onResponseAttempts: " + jsonObject.getString("Attempts"));
                    }
//                    clearAllLists();
                    improveHelper.dismissProgressDialog();

                    Intent intent = new Intent(context, CreateNewTaskActivity.class);
                    intent.putExtra("TaskEdit", "TaskEdit");
                    intent.putExtra("TaskId", outTaskDataModels.get(getAdapterPosition).getTaskID());
                    intent.putExtra("TaskName", outTaskDataModels.get(getAdapterPosition).getTaskName());
                    intent.putExtra("TaskDesc", outTaskDataModels.get(getAdapterPosition).getTaskDesc());
                    intent.putExtra("TaskStartDate", outTaskDataModels.get(getAdapterPosition).getStartDate());
                    intent.putExtra("TaskEndDate", outTaskDataModels.get(getAdapterPosition).getEndDate());
                    intent.putExtra("TaskPriority", outTaskDataModels.get(getAdapterPosition).getPriority());
                    intent.putExtra("TaskAttemptedBy", outTaskDataModels.get(getAdapterPosition).getIsSingleUser());
                    intent.putExtra("TaskAppsInfo", outTaskDataModels.get(getAdapterPosition).getAppInfo());
                    intent.putExtra("TaskContentInfo", outTaskDataModels.get(getAdapterPosition).getFilesInfo());
                    intent.putExtra("TaskGroupInfo", outTaskDataModels.get(getAdapterPosition).getGroupInfo());
                    intent.putExtra("TaskIndividualInfo", outTaskDataModels.get(getAdapterPosition).getEmpInfo());
                    context.startActivity(intent);


                } catch (Exception e) {
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "onResponse: " + e.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                improveHelper.dismissProgressDialog();
                Log.d(TAG, "onResponseFailureTaskAttempt: " + t.toString());
            }
        });

    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CustomTextView tvTasks_name, tvTasksDesc, tvTasksStatus, tvTasksCreatedDate;
        ImageView iv_edit, iv_comments;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTasks_name = itemView.findViewById(R.id.tvTasks_name);
            tvTasksDesc = itemView.findViewById(R.id.tvTasksDesc);
            tvTasksStatus = itemView.findViewById(R.id.tvTasksStatus);
            tvTasksCreatedDate = itemView.findViewById(R.id.tvTasksCreatedDate);
            iv_edit = itemView.findViewById(R.id.iv_edit);
            iv_comments = itemView.findViewById(R.id.iv_comments);

            iv_edit.setOnClickListener(this);
            iv_comments.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.iv_edit:
                    AppConstants.TASK_ATTEMPTS_BY = "0";
//                    if(improveHelper.isDateLiesBetweenTwoDates(outTaskDataModels.get(getAdapterPosition()).getStartDate(),outTaskDataModels.get(getAdapterPosition()).getEndDate())
//                     || improveHelper.isGivenDateIsEqualsToCurrent(outTaskDataModels.get(getAdapterPosition()).getStartDate())
//                     || improveHelper.isGivenDateIsEqualsToCurrent(outTaskDataModels.get(getAdapterPosition()).getEndDate())) {
//                        Log.d(TAG, "onBindViewHolderEndDate: "+ImproveHelper.getCurrentDateFromHelper()+"- "+outTaskDataModels.get(getAdapterPosition).getEndDate()
//                                + " - " +improveHelper.isDateExpiredComparedToCurrentDate(outTaskDataModels.get(getAdapterPosition).getEndDate()));
                    if (improveHelper.isGivenDateIsAfterOrGraterThanCurrent(outTaskDataModels.get(getAdapterPosition()).getEndDate())) {
                        ImproveHelper.showToast(context, "Task date expired");
                    } else {

                        if (ImproveHelper.isNetworkStatusAvialable(context)) {
                            SessionManager sessionManager = new SessionManager(context);
                            GetTaskAttempts(context, sessionManager.getUserDataFromSession().getUserID(),
                                    sessionManager.getOrgIdFromSession(), sessionManager.getPostsFromSession(),
                                    outTaskDataModels.get(getAdapterPosition()).getTaskID(),
                                    getAdapterPosition());
                        } else {
                            ImproveHelper.showToast(context, "Please check internet connection ");
                        }
                    }
                    break;

                case R.id.iv_comments:

//                    if(improveHelper.isDateLiesBetweenTwoDates(outTaskDataModels.get(getAdapterPosition()).getStartDate(),outTaskDataModels.get(getAdapterPosition()).getEndDate())
//                            || improveHelper.isGivenDateIsEqualsToCurrent(outTaskDataModels.get(getAdapterPosition()).getStartDate())
//                            || improveHelper.isGivenDateIsEqualsToCurrent(outTaskDataModels.get(getAdapterPosition()).getEndDate())) {
                    if (improveHelper.isGivenDateIsAfterOrGraterThanCurrent(outTaskDataModels.get(getAdapterPosition()).getEndDate())) {

                        ImproveHelper.showToast(context, "Task date expired");

                    } else {
                        Intent intent = new Intent(context, OutTaskCommentsActivity.class);
                        intent.putExtra("out_task_created_by", outTaskDataModels.get(getAdapterPosition()).getCreatedBy());
                        intent.putExtra("out_task_id", outTaskDataModels.get(getAdapterPosition()).getTaskID());
                        intent.putExtra("out_task_name", outTaskDataModels.get(getAdapterPosition()).getTaskName());
                        intent.putExtra("out_task_desc", outTaskDataModels.get(getAdapterPosition()).getTaskDesc());
                        intent.putExtra("out_task_status", outTaskDataModels.get(getAdapterPosition()).getTaskStatus());
                        intent.putExtra("out_task_distributed_date", outTaskDataModels.get(getAdapterPosition()).getDistributionDate());
                        context.startActivity(intent);
                    }


                    break;

            }

        }
    }

}
