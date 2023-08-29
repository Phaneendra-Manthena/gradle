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
import com.bhargo.user.pojos.ContentInfoModel;
import com.bhargo.user.pojos.SpinnerAppsDataModel;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import static com.bhargo.user.utils.AppConstants.editTaskAppDataModelsList;
import static com.bhargo.user.utils.AppConstants.editTaskContentDataModelsList;


public class TaskContentCustomAdapter extends RecyclerView.Adapter<TaskContentCustomAdapter.CadViewHolder> {
    private static final String TAG = "CadAdapter";
    public List<SpinnerAppsDataModel> appsInfoModelList;
    public List<SpinnerAppsDataModel> filteredColumnListApps;
    public List<ContentInfoModel> filteredColumnListContent;
    public List<ContentInfoModel> contentInfoModelsList;
    SessionManager sessionManager;
    Context context;
    //    List<TaskTransDataModel> TaskTransData;
    ImproveHelper improveHelper;
    String strTypeOfList;
    GetServices getServices;
    ImproveDataBase improveDataBase;
    boolean isFilteredColumnApps = false;
    boolean isFilteredColumnContent = false;
    boolean isApps;
//    TaskItemClickListener itemClickListener;


    public TaskContentCustomAdapter(Context context, List<SpinnerAppsDataModel> appsInfoModelList, List<ContentInfoModel> contentInfoModelsList, boolean isApps) {

        this.context = context;
        this.appsInfoModelList = appsInfoModelList;
        this.contentInfoModelsList = contentInfoModelsList;
        this.isApps = isApps;
        if (isApps) {
            isFilteredColumnApps = false;
        } else {
            isFilteredColumnContent = false;
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
    public TaskContentCustomAdapter.CadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cad_rv_item, parent, false);

        return new CadViewHolder(v, isApps);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskContentCustomAdapter.CadViewHolder holder, int position) {

        if (isApps) {
            if (isFilteredColumnApps) {

                if (filteredColumnListApps != null && filteredColumnListApps.size() > 0) {

                    if (filteredColumnListApps.get(position).isSelected()) {


                            if (editTaskAppDataModelsList != null && editTaskAppDataModelsList.size() > 0) {
                                for (int j = 0; j < editTaskAppDataModelsList.size(); j++) {
                                    if (filteredColumnListApps.get(position).getACId().equalsIgnoreCase(editTaskAppDataModelsList.get(j).getACId())
                                            && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("1")) {
                                        holder.itemName.setChecked(true);
                                        holder.itemName.setEnabled(false);
                                        Log.d(TAG, "AppsListItemMatchedAdapter " + filteredColumnListApps.get(position).getACName());
                                    }
                                }

                            }
                        holder.itemName.setChecked(true);



                    }
//                        if (AppConstants.TASK_ATTEMPTS_BY != null && !AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("")
//                                && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("1")) {
//                            holder.itemName.setChecked(true);
//                            holder.itemName.setEnabled(false);
//                        } else {
//                            holder.itemName.setChecked(true);
//                        }
//                    }

                    holder.itemName.setText(filteredColumnListApps.get(position).getACName());

                }


            } else {
                if (appsInfoModelList != null && appsInfoModelList.size() > 0) {


                    if(appsInfoModelList.get(position).isSelected()) {
                        if (editTaskAppDataModelsList != null && editTaskAppDataModelsList.size() > 0) {
                            for (int j = 0; j < editTaskAppDataModelsList.size(); j++) {
                                if (appsInfoModelList.get(position).getACId().equalsIgnoreCase(editTaskAppDataModelsList.get(j).getACId())
                                        && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("1")) {
                                    holder.itemName.setChecked(true);
                                    holder.itemName.setEnabled(false);
                                    Log.d(TAG, "AppsListItemMatchedAdapter " + appsInfoModelList.get(position).getACName());
                                }
                            }

                        }
                        holder.itemName.setChecked(true);
                    }

//                    if (appsInfoModelList.get(position).isSelected()) {
//                        if (AppConstants.TASK_ATTEMPTS_BY != null && !AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("")
//                                && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("1")) {
//                            holder.itemName.setChecked(true);
//                            holder.itemName.setEnabled(false);
//                        } else {
//                            holder.itemName.setChecked(true);
//                        }
//                    }

                    holder.itemName.setText(appsInfoModelList.get(position).getACName());

                }
            }
        } else {
            if (isFilteredColumnContent) {

                if (filteredColumnListContent != null && filteredColumnListContent.size() > 0) {

                    if (filteredColumnListContent.get(position).isSelected()) {
                        if (editTaskContentDataModelsList != null && editTaskContentDataModelsList.size() > 0) {
                            for (int j = 0; j < editTaskContentDataModelsList.size(); j++) {
                                if (filteredColumnListContent.get(position).getACId().equalsIgnoreCase(editTaskContentDataModelsList.get(j).getACId())
                                        && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("1")) {
                                    holder.itemName.setChecked(true);
                                    holder.itemName.setEnabled(false);
                                    Log.d(TAG, "AppsListItemMatchedAdapter " + filteredColumnListContent.get(position).getACName());
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
                    holder.itemName.setText(filteredColumnListContent.get(position).getACName());

                }

            } else {
                if (contentInfoModelsList != null && contentInfoModelsList.size() > 0) {

                    if (contentInfoModelsList.get(position).isSelected()) {

                        if (editTaskContentDataModelsList != null && editTaskContentDataModelsList.size() > 0) {
                            for (int j = 0; j < editTaskContentDataModelsList.size(); j++) {
                                if (contentInfoModelsList.get(position).getACId().equalsIgnoreCase(editTaskContentDataModelsList.get(j).getACId())
                                        && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("1")) {
                                    holder.itemName.setChecked(true);
                                    holder.itemName.setEnabled(false);
                                    Log.d(TAG, "AppsListItemMatchedAdapter " + contentInfoModelsList.get(position).getACName());
                                }
                            }
                        }
                        holder.itemName.setChecked(true);
/*                        if (AppConstants.TASK_ATTEMPTS_BY != null && !AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("")
                                && AppConstants.TASK_ATTEMPTS_BY.equalsIgnoreCase("1")) {
                            holder.itemName.setChecked(true);
                            holder.itemName.setEnabled(false);
                        } else {
                            holder.itemName.setChecked(true);
                        }*/
                    }
                    holder.itemName.setText(contentInfoModelsList.get(position).getACName());

                }
            }
        }

    }

    @Override
    public int getItemCount() {
        if (isApps) {
            if (isFilteredColumnApps) {
                return filteredColumnListApps.size();
            } else {
                return appsInfoModelList.size();
            }
        } else {
            if(isFilteredColumnContent){
                return filteredColumnListContent.size();
            }else {
                return contentInfoModelsList.size();
            }
        }

    }

//    public void setItemClickListener(TaskItemClickListener itemClickListener) {
//        this.itemClickListener = itemClickListener;
//    }

    public List<SpinnerAppsDataModel> getAppsInfoModelList() {

        List<SpinnerAppsDataModel> spinnerAppsDataModelList = new ArrayList<>();
        for (SpinnerAppsDataModel spinnerAppsDataModel : appsInfoModelList) {
            if (spinnerAppsDataModel.isSelected()) {
                spinnerAppsDataModelList.add(spinnerAppsDataModel);
            }
        }

        return spinnerAppsDataModelList;
    }

    public List<ContentInfoModel> getContentInfoModelList() {
        List<ContentInfoModel> contentInfoModelsListReturn = new ArrayList<>();
        for (ContentInfoModel contentInfoModel : contentInfoModelsList) {
            if (contentInfoModel.isSelected()) {
                contentInfoModelsListReturn.add(contentInfoModel);
            }
        }
        return contentInfoModelsListReturn;
    }

    public void filterListApps(List<SpinnerAppsDataModel> filteredNames) {
        this.isFilteredColumnApps = true;
        this.filteredColumnListApps = filteredNames;
        notifyDataSetChanged();
    }

    public void filterListContent(List<ContentInfoModel> filteredNames) {
        this.isFilteredColumnContent = true;
        this.filteredColumnListContent = filteredNames;
        notifyDataSetChanged();
    }

    public class CadViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_circle_tasks, iv_elImage;
        CustomTextView tvPersonName, tvTasksId, ctv_elName;
        LinearLayout ll_elImage;
        RelativeLayout ll_progressbar, rl_customAdapter;
        ProgressBar mProgress;
        CustomCheckBox itemName;


        public CadViewHolder(@NonNull View itemView, boolean typeOfList) {
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
                    if (typeOfList) {
                        if (isFilteredColumnApps) {
                            filteredColumnListApps.get(getAdapterPosition()).setSelected(isChecked);
                        } else {
                            appsInfoModelList.get(getAdapterPosition()).setSelected(isChecked);
                        }
                    } else {
                        if(isFilteredColumnContent){
                            filteredColumnListContent.get(getAdapterPosition()).setSelected(isChecked);
                        }else {
                            contentInfoModelsList.get(getAdapterPosition()).setSelected(isChecked);
                        }
                    }
                }
            });
        }

    }

}