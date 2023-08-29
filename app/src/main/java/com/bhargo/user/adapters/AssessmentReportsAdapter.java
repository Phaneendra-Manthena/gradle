package com.bhargo.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.AssessmentInfoModel;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.List;


public class AssessmentReportsAdapter extends RecyclerView.Adapter<AssessmentReportsAdapter.MyViewHolder> {

    private static final String TAG = "VideosAdapter";
    public JSONArray jsonArray;
    //    public FilesInfoModel filesInfoModel;
    public List<AssessmentInfoModel> filesInfoModelList;
    Context context;
    Gson gson = new Gson();

    public AssessmentReportsAdapter(Context context, List<AssessmentInfoModel> filesInfoModelList) {

        this.context = context;
        this.filesInfoModelList = filesInfoModelList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_report_list_item, parent, false);
        // set the view's size, margins, paddings and layout_sample_app parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tvAssessment_Attempt.setText(filesInfoModelList.get(position).getAssessment());
        holder.tvAssessment_date_time_taken.setText(filesInfoModelList.get(position).getAssessmentDate());
        holder.tvAssessment_marks.setText(filesInfoModelList.get(position).getMarksObtained() + " out of " + filesInfoModelList.get(position).getTotalMarks());
        holder.tvAssessment_Duration.setText(filesInfoModelList.get(position).getNoOfMinutsTimeSpent() + " Mins");
        holder.tvAssessment_Result.setText(filesInfoModelList.get(position).getResults());

        if(filesInfoModelList.get(position).getResults().equalsIgnoreCase("Not qualified")){
            holder.tvAssessment_Result.setTextColor(context.getResources().getColor(R.color.red));
        }else{
            holder.tvAssessment_Result.setTextColor(context.getResources().getColor(R.color.buttonSuccess));
        }

    }

    @Override
    public int getItemCount() {
        return filesInfoModelList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CustomTextView tvAssessment_Attempt, tvAssessment_date_time_taken, tvAssessment_marks, tvAssessment_Result, tvAssessment_Duration;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvAssessment_Attempt = itemView.findViewById(R.id.tvAssessment_Attempt);
            tvAssessment_date_time_taken = itemView.findViewById(R.id.tvAssessment_date_time_taken);
            tvAssessment_marks = itemView.findViewById(R.id.tvAssessment_marks);
            tvAssessment_Result = itemView.findViewById(R.id.tvAssessment_Result);
            tvAssessment_Duration = itemView.findViewById(R.id.tvAssessment_Duration);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

//            ImproveHelper.showToastAlert(context,gudList.get(getAdapterPosition()).getTopicName());
//            filesInfoModel = gudList.get(getAdapterPosition()).getFilesInfo());
//            TypeToken<ArrayList<FilesInfoModel>> token = new TypeToken<ArrayList<FilesInfoModel>>() {};
//            filesInfoModel = gson.fromJson(gudList.get(getAdapterPosition()).getFilesInfo(), token.getType());

//            filesInfoModel = Arrays.asList(gson.fromJson(gudList.get(getAdapterPosition()).getFilesInfo(), AssessmentInfoModel[].class));
//
//            Intent intent = new Intent(context, ELearningListActivity.class);
//            intent.putExtra("DistributionId", gudList.get(getAdapterPosition()).getDistributionId());
//            intent.putExtra("ExamDuration", gudList.get(getAdapterPosition()).getExamDuration());
//            intent.putExtra("FilesInfoModelList", (Serializable) filesInfoModel);
////            intent.putExtra("FilesInfoModelList", gudList.get(getAdapterPosition()).getFilesInfo());
//            intent.putExtra("EL_TopicName", gudList.get(getAdapterPosition()).getTopicName());
//            intent.putExtra("Is_Assessment", gudList.get(getAdapterPosition()).getIs_Assessment());
//            intent.putExtra("NoOfAttempts", gudList.get(getAdapterPosition()).getNoOfAttempts());
//            intent.putExtra("NoOfUserAttempts", gudList.get(getAdapterPosition()).getNoOfUserAttempts());
//            intent.putExtra("hQuestions", gudList.get(getAdapterPosition()).gethQuestions());
//            intent.putExtra("mQuestions", gudList.get(getAdapterPosition()).getmQuestions());
//            intent.putExtra("lQuestions", gudList.get(getAdapterPosition()).getlQuestions());
//            intent.putExtra("tQuestions", gudList.get(getAdapterPosition()).gettQuestions());
//            intent.putExtra("Is_Compexcity", gudList.get(getAdapterPosition()).getIs_Compexcity());
//            intent.putExtra("StartDate", gudList.get(getAdapterPosition()).getStartDate());
//            intent.putExtra("StartDisplayTime", gudList.get(getAdapterPosition()).getStartDisplayTime());
//            intent.putExtra("EndDate", gudList.get(getAdapterPosition()).getEndDate());
//            intent.putExtra("EndDisplayTime", gudList.get(getAdapterPosition()).getEndDisplayTime());
//            intent.putExtra("StartTime", gudList.get(getAdapterPosition()).getStartTime());
//            intent.putExtra("EndTime", gudList.get(getAdapterPosition()).getEndTime());
//            intent.putExtra("GetUserDistributionsList", (Serializable) gudList);
//
//            context.startActivity(intent);

        }
    }
}
