package com.bhargo.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.QuestionState;

import java.util.List;

public class InfoAdapter extends BaseAdapter {
    private static final String TAG = "InfoAdapter";
    List<QuestionState> infoList;
    int currentQuestionIndex = 0;
    private final Context context;

    public InfoAdapter(Context context, int currentQuestionIndex, List<QuestionState> infoList) {
        this.context = context;
        this.infoList = infoList;
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Holder holder = new Holder();

        View gridView;

//        if (convertView == null) {

        gridView = new View(context);

        // get layout from mobile.xml
        gridView = inflater.inflate(R.layout.item_info, null);

        // set value into textview
        holder.textView = gridView.findViewById(R.id.tv_question_no);
        holder.layoutInfo = gridView.findViewById(R.id.layout_bg);
        holder.textView.setText(infoList.get(position).getQuestionNumber());


        if (infoList.get(position).getQuestionState().equalsIgnoreCase("0")) {
            holder.layoutInfo.setBackground(context.getDrawable(R.drawable.bg_circle_not_visited));
            holder.textView.setTextColor(context.getColor(R.color.black));
        } else if (infoList.get(position).getQuestionState().equalsIgnoreCase("1")) {
//                holder.layoutInfo.setBackground(context.getDrawable(R.drawable.bg_circle_not_answered));
            holder.layoutInfo.setBackground(context.getDrawable(R.drawable.bg_circle_visited));
            holder.textView.setTextColor(context.getColor(R.color.white));
        } else if (infoList.get(position).getQuestionState().equalsIgnoreCase("2")) {
//                holder.layoutInfo.setBackground(context.getDrawable(R.drawable.bg_circle_answered));
            holder.layoutInfo.setBackground(context.getDrawable(R.drawable.bg_circle_not_answered));
            holder.textView.setTextColor(context.getColor(R.color.white));
        }

        if (position == currentQuestionIndex) {
            holder.layoutInfo.setBackground(context.getDrawable(R.drawable.bg_circle_visited));
            holder.textView.setTextColor(context.getColor(R.color.white));
        }
//
        /*gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onItemClick: "+position);
                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();

            }
        });*/


//        } else {
//            gridView = (View) convertView;
//        }

        return gridView;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class Holder {
        CustomTextView textView;
        LinearLayout layoutInfo;
    }


}
