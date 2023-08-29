package com.bhargo.user.interfaces;

import android.content.Context;
import android.view.View;

public interface AssessmentItemClickListener {

    void onCustomClick(Context context, View view, int position, String strQuestionID,String strAnswerId,String strAnswer);

}
