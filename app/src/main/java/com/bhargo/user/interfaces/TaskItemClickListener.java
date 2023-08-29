package com.bhargo.user.interfaces;

import android.content.Context;
import android.view.View;

import com.bhargo.user.pojos.SpinnerAppsDataModel;

import java.util.List;

public interface TaskItemClickListener {

    void onTaskCustomClick(Context context, View view, int position, List<SpinnerAppsDataModel> appsObjectList);

}
