package com.bhargo.user.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomCheckBox;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.TaskDepEmpDataModel;
import com.bhargo.user.pojos.TaskDepGroupDataModel;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import static com.bhargo.user.utils.AppConstants.editTaskGroupList;
import static com.bhargo.user.utils.AppConstants.editTaskIndividualList;


public class TaskDeploymentAdapter extends RecyclerView.Adapter<TaskDeploymentAdapter.CadViewHolder> {
    private static final String TAG = "TaskDeploymentAdapter";
    List<TaskDepGroupDataModel> taskDepGroupDataModelList;
    List<TaskDepGroupDataModel> filteredColumnListGroupsList;
    List<TaskDepEmpDataModel> taskDepEmpDataModelList;
    List<TaskDepEmpDataModel> filteredColumnListEmpList;
    SessionManager sessionManager;
    Context context;
    //    List<TaskTransDataModel> TaskTransData;
    ImproveHelper improveHelper;
    String strTypeOfList;
    GetServices getServices;
    ImproveDataBase improveDataBase;
    boolean isGroup;
    boolean isFilteredColumnGroup = false;
    boolean isFilteredColumnEmp = false;
//    TaskItemClickListener itemClickListener;


    public TaskDeploymentAdapter(Context context, List<TaskDepGroupDataModel> taskDepGroupDataModelList, List<TaskDepEmpDataModel> taskDepEmpDataModelList, boolean isGroup) {

        this.context = context;
        this.taskDepGroupDataModelList = taskDepGroupDataModelList;
        this.taskDepEmpDataModelList = taskDepEmpDataModelList;
        this.isGroup = isGroup;
        if (isGroup) {
            isFilteredColumnGroup = false;
        } else {
            isFilteredColumnEmp = false;
        }

    }

    static <E> List<E> myFilter(List<?> lst, Class<E> cls) {
        List<E> result = new ArrayList<E>();
        for (Object obj : lst) {
            if (cls.isInstance(obj))
                result.add(cls.cast(obj));
        }
        return result;
    }

    @NonNull
    @Override
    public TaskDeploymentAdapter.CadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cad_rv_item, parent, false);

        return new CadViewHolder(v, isGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskDeploymentAdapter.CadViewHolder holder, int position) {

        if (isGroup) {
            if (isFilteredColumnGroup) {
                if (filteredColumnListGroupsList != null && filteredColumnListGroupsList.size() > 0) {
                    if (filteredColumnListGroupsList.get(position).isSelected()) {

                        if (editTaskGroupList != null && editTaskGroupList.size() > 0) {
                            for (int j = 0; j < editTaskGroupList.size(); j++) {
                                if (filteredColumnListGroupsList.get(position).getGroupId().equalsIgnoreCase(editTaskGroupList.get(j).getGroupId())
                                        && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("1")) {
                                    holder.itemName.setChecked(true);
                                    holder.itemName.setEnabled(false);
                                    Log.d(TAG, "ItemMatchedAdapter " + filteredColumnListGroupsList.get(position).getGroupId());
                                }
                            }

                        }
                        holder.itemName.setChecked(true);

/*                        if(AppConstants.TASK_ATTEMPTS_BY != null && !AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("")
                                && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("1")){
                            holder.itemName.setChecked(true);
                            holder.itemName.setEnabled(false);
                        }else {
                            holder.itemName.setChecked(true);
                        }*/
                    }
                    holder.itemName.setText(filteredColumnListGroupsList.get(position).getGroupName());

                }

            } else {


                if (taskDepGroupDataModelList != null && taskDepGroupDataModelList.size() > 0) {
                    if (taskDepGroupDataModelList.get(position).isSelected()) {
                        if (editTaskGroupList != null && editTaskGroupList.size() > 0) {
                            for (int j = 0; j < editTaskGroupList.size(); j++) {
                                if (taskDepGroupDataModelList.get(position).getGroupId().equalsIgnoreCase(editTaskGroupList.get(j).getGroupId())
                                        && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("1")) {
                                    holder.itemName.setChecked(true);
                                    holder.itemName.setEnabled(false);
                                    Log.d(TAG, "ItemMatchedAdapter " + taskDepGroupDataModelList.get(position).getGroupId());
                                }
                            }

                        }
                        holder.itemName.setChecked(true);
                    }
/*                        if (AppConstants.TASK_ATTEMPTS_BY != null && !AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("")
                                && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("1")) {
                            holder.itemName.setChecked(true);
                            holder.itemName.setEnabled(false);
                        } else {
                            holder.itemName.setChecked(true);
                        }
                    }*/
                    holder.itemName.setText(taskDepGroupDataModelList.get(position).getGroupName());

                }
            }
        } else {
            if (isFilteredColumnEmp) {
                if (filteredColumnListEmpList != null && filteredColumnListEmpList.size() > 0) {
                    if (filteredColumnListEmpList.get(position).isSelected()) {
                        if (editTaskIndividualList != null && editTaskIndividualList.size() > 0) {
                            for (int j = 0; j < editTaskIndividualList.size(); j++) {
                                if (filteredColumnListEmpList.get(position).getEmp_Id().equalsIgnoreCase(editTaskIndividualList.get(j).getEmp_Id())
                                        && filteredColumnListEmpList.get(position).getPostId().equalsIgnoreCase(editTaskIndividualList.get(j).getPostId())
                                        && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("1")) {
                                    holder.itemName.setChecked(true);
                                    holder.itemName.setEnabled(false);
                                    Log.d(TAG, "ItemMatchedAdapterEmpId " + filteredColumnListEmpList.get(position).getEmp_Id());
                                    Log.d(TAG, "ItemMatchedAdapterPostId " + filteredColumnListEmpList.get(position).getPostId());
                                }
                            }
                        }
                        holder.itemName.setChecked(true);
/*                        if(AppConstants.TASK_ATTEMPTS_BY != null && !AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("")
                                && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("1")){
                            holder.itemName.setChecked(true);
                            holder.itemName.setEnabled(false);
                        }else {
                            holder.itemName.setChecked(true);
                        }*/
                    }
                    holder.itemName.setText(filteredColumnListEmpList.get(position).getEmpName());
                }

            } else {

                if (taskDepEmpDataModelList != null && taskDepEmpDataModelList.size() > 0) {

                    if (taskDepEmpDataModelList.get(position).isSelected()) {

                        if (editTaskIndividualList != null && editTaskIndividualList.size() > 0) {
                            for (int j = 0; j < editTaskIndividualList.size(); j++) {
                                if (taskDepEmpDataModelList.get(position).getEmp_Id().equalsIgnoreCase(editTaskIndividualList.get(j).getEmp_Id())
                                        && taskDepEmpDataModelList.get(position).getPostId().equalsIgnoreCase(editTaskIndividualList.get(j).getPostId())
                                        && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("1")) {
                                    holder.itemName.setChecked(true);
                                    holder.itemName.setEnabled(false);
                                    Log.d(TAG, "ItemMatchedAdapterEmpId " + taskDepEmpDataModelList.get(position).getEmp_Id());
                                    Log.d(TAG, "ItemMatchedAdapterPostId " + taskDepEmpDataModelList.get(position).getPostId());
                                }
                            }

                        }
                        holder.itemName.setChecked(true);

/*                    if(AppConstants.TASK_ATTEMPTS_BY != null && !AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("")
                            && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("1")){
                        holder.itemName.setChecked(true);
                        holder.itemName.setEnabled(false);
                    }else {
                        holder.itemName.setChecked(true);
                    }*/
                    }
                    holder.itemName.setText(taskDepEmpDataModelList.get(position).getEmpName());
                }
            }
        }
    }

    @Override
    public int getItemCount() {

        if (isGroup) {
            if (isFilteredColumnGroup) {
                return filteredColumnListGroupsList.size();
            } else {
                return taskDepGroupDataModelList.size();
            }
        } else {
            if (isFilteredColumnEmp) {
                return filteredColumnListEmpList.size();
            } else {
                return taskDepEmpDataModelList.size();
            }
        }

    }

//    public void setItemClickListener(TaskItemClickListener itemClickListener) {
//        this.itemClickListener = itemClickListener;
//    }

    public List<TaskDepGroupDataModel> getGroupListFromAdapter() {

        List<TaskDepGroupDataModel> taskDepGroupDataModels = new ArrayList<>();
        for (TaskDepGroupDataModel taskDepGroupDataModel : taskDepGroupDataModelList) {
            if (taskDepGroupDataModel.isSelected()) {
                taskDepGroupDataModels.add(taskDepGroupDataModel);
            }
        }

        return taskDepGroupDataModels;
    }

    public List<TaskDepEmpDataModel> getEmpListFromAdapter() {

        List<TaskDepEmpDataModel> taskDepEmpDataModelListReturn = new ArrayList<>();
        for (TaskDepEmpDataModel taskDepEmpDataModel : taskDepEmpDataModelList) {
            if (taskDepEmpDataModel.isSelected()) {
                taskDepEmpDataModelListReturn.add(taskDepEmpDataModel);
            }
        }

        return taskDepEmpDataModelListReturn;
    }

    public void filterGroups(List<TaskDepGroupDataModel> depGroupDataModels) {
        this.isFilteredColumnGroup = true;
        this.filteredColumnListGroupsList = depGroupDataModels;
        notifyDataSetChanged();

    }

    public void filterIndividuals(List<TaskDepEmpDataModel> taskDepEmpDataModelList) {
        this.isFilteredColumnEmp = true;
        this.filteredColumnListEmpList = taskDepEmpDataModelList;
        notifyDataSetChanged();

    }

    public class CadViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_circle_tasks, iv_elImage;
        CustomTextView tvPersonName, tvTasksId, ctv_elName;
        LinearLayout ll_elImage;
        RelativeLayout ll_progressbar, rl_customAdapter;
        ProgressBar mProgress;
        CustomCheckBox itemName;


        public CadViewHolder(@NonNull View itemView, boolean isGroup) {
            super(itemView);
            this.setIsRecyclable(false);

            rl_customAdapter = itemView.findViewById(R.id.rl_customAdapter);
            iv_circle_tasks = itemView.findViewById(R.id.iv_circle_tasks);
            tvPersonName = itemView.findViewById(R.id.tvPersonName);
            tvTasksId = itemView.findViewById(R.id.tvTasksId);
            rl_customAdapter.setVisibility(View.GONE);
            itemName = itemView.findViewById(R.id.itemName);
            itemName.setVisibility(View.VISIBLE);
            itemName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isGroup) {
                        if (isFilteredColumnGroup) {
                            filteredColumnListGroupsList.get(getAdapterPosition()).setSelected(isChecked);
                        } else {
                            taskDepGroupDataModelList.get(getAdapterPosition()).setSelected(isChecked);
                        }
                    } else {
                        if (isFilteredColumnEmp) {
                            filteredColumnListEmpList.get(getAdapterPosition()).setSelected(isChecked);
                        } else {
                            taskDepEmpDataModelList.get(getAdapterPosition()).setSelected(isChecked);
                        }
                    }
                }
            });

        }

    }

}