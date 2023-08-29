package com.bhargo.user.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.OutTasksAdapter;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.OutTaskDataModel;
import com.bhargo.user.pojos.OutTaskRefreshSendData;
import com.bhargo.user.pojos.RefreshTaskDetails;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.utils.ImproveDataBase.OUT_TASK_TABLE;
import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;


/**
 * A simple {@link Fragment} subclass.
 */

public class OutTasksFragment extends Fragment {

    private static final String TAG = "OutTasksFragment";
    public ImproveHelper improveHelper;
    CustomTextView ct_NoRecords;
    GetServices getServices;
    RecyclerView rv_tasks;
    CustomEditText cet_searchTasks;
    SessionManager sessionManager;
    OutTasksAdapter outTasksAdapter;
    List<OutTaskDataModel> outTaskDataModelsList;
    ImproveDataBase improveDataBase;
    String refreshData;
    private View rootView;
    private ViewGroup viewGroup;

    public OutTasksFragment() {

        // Required empty public constructor

    }

    public OutTasksFragment(String refreshData) {
        this.refreshData = refreshData;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Theme
        setBhargoTheme(getActivity(),AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_in_tasks, container, false);

        viewGroup = container;

        improveHelper = new ImproveHelper(getActivity());
        sessionManager = new SessionManager(getActivity());
        improveDataBase = new ImproveDataBase(getActivity());
        if (ImproveHelper.isNetworkStatusAvialable(getActivity())) {

            improveDataBase.deleteAllRowsInTable(OUT_TASK_TABLE);
        }

        getServices = RetrofitUtils.getUserService();
        cet_searchTasks = rootView.findViewById(R.id.cet_searchTasks);
        rv_tasks = rootView.findViewById(R.id.rv_tasks);
        ct_NoRecords = rootView.findViewById(R.id.ct_NoRecords);
//        outTaskDataModelsList = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_tasks.setLayoutManager(llm);

//        outTaskApi();

        if ((ImproveHelper.isNetworkStatusAvialable(getActivity()))
                && ((refreshData != null && refreshData.equalsIgnoreCase("1") && AppConstants.TASK_REFRESH.equalsIgnoreCase("OutTaskRefresh"))
                || AppConstants.OUT_TASK_REFRESH_BOOLEAN)) {
            mPrepareRefreshData();
        } else {
            setOutTasks();
        }

        cet_searchTasks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // filter your list from your input
                if (s.toString() != null && !s.toString().equalsIgnoreCase("") && !s.toString().isEmpty()) {
                    filter(s.toString());
                } else {
                    outTasksAdapter.updateList(outTaskDataModelsList);
                }
            }
        });

        return rootView;
    }


    private void outTaskApi() {
        try {
            if (ImproveHelper.isNetworkStatusAvialable(getActivity())) {
//            improveHelper.showProgressDialog(getActivity().getResources().getString(R.string.please_wait));
                Call<List<OutTaskDataModel>> responseCall = getServices.getOutTasks(sessionManager.getUserDataFromSession().getUserID(), sessionManager.getOrgIdFromSession());
                responseCall.enqueue(new Callback<List<OutTaskDataModel>>() {
                    @Override
                    public void onResponse(Call<List<OutTaskDataModel>> call, Response<List<OutTaskDataModel>> response) {
                        if (response.body() != null) {

                            List<OutTaskDataModel> outTaskDataModelListResponse = response.body();

                            if (outTaskDataModelListResponse != null && outTaskDataModelListResponse.size() > 0) {
//                            improveDataBase.deleteAllRowsInTable(OUT_TASK_TABLE);
                                List<OutTaskDataModel> deleteDuplicate = improveDataBase.getDataFromOutTaskTable(sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession());
                                if (deleteDuplicate != null && deleteDuplicate.size() > 0) {
                                    for (int i = 0; i < deleteDuplicate.size(); i++) {
                                        for (int j = 0; j < outTaskDataModelListResponse.size(); j++) {
                                            if (deleteDuplicate.get(i).getTaskName().equalsIgnoreCase(outTaskDataModelListResponse.get(j).getTaskName())) {
                                                improveDataBase.deleteRowsByPostIdInTable(OUT_TASK_TABLE, sessionManager.getPostsFromSession());
                                            }
                                        }
                                    }
                                }

                                improveDataBase.insertIntoOutTaskTable(outTaskDataModelListResponse, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
                                outTaskDataModelsList = new ArrayList<>();
                                outTaskDataModelsList.clear();
                                outTaskDataModelsList = improveDataBase.getDataFromOutTaskTable(sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession());
                                if (outTaskDataModelsList != null && outTaskDataModelsList.size() == 0) {
                                    noRecords();
                                } else {
                                    setOutTasks();
                                }
                            } else {
                                noRecords();
                            }
                        } else {
                            noRecords();
                        }
                        improveHelper.dismissProgressDialog();
                    }

                    @Override
                    public void onFailure(Call<List<OutTaskDataModel>> call, Throwable t) {
                        Log.d(TAG, "onFailureTasks: " + t.toString());
                        improveHelper.dismissProgressDialog();
                    }
                });


            } else {

                improveHelper.dismissProgressDialog();
                improveHelper.snackBarAlertFragment(getContext(), viewGroup);

            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "outTaskApi", e);
        }
    }


    public void filter(String text) {
        try {
            List<OutTaskDataModel> filteredNames = new ArrayList<>();

            //looping through existing elements
            for (OutTaskDataModel outTaskDataModel : outTaskDataModelsList) {
                //if the existing elements contains the search input
                if (outTaskDataModel.getTaskName().toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filteredNames.add(outTaskDataModel);
                }
            }

            //calling a method of the adapter class and passing the filtered list
            outTasksAdapter.filterList(filteredNames);
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "filter", e);
        }
    }

    private void setOutTasks() {
        try {
            improveHelper.dismissProgressDialog();
            outTaskDataModelsList = improveDataBase.getDataFromOutTaskTable(sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession());

            if (outTaskDataModelsList != null && outTaskDataModelsList.size() > 0) {
                Collections.reverse(outTaskDataModelsList);
                outTasksAdapter = new OutTasksAdapter(getActivity(), outTaskDataModelsList);
                rv_tasks.setAdapter(outTasksAdapter);
                outTasksAdapter.notifyDataSetChanged();
                cet_searchTasks.setVisibility(View.VISIBLE);
                rv_tasks.setVisibility(View.VISIBLE);
                ct_NoRecords.setVisibility(View.GONE);
                improveHelper.dismissProgressDialog();
            } else if (outTaskDataModelsList != null && outTaskDataModelsList.size() == 0 && !ImproveHelper.isNetworkStatusAvialable(getActivity())) {

                cet_searchTasks.setVisibility(View.GONE);
                rv_tasks.setVisibility(View.GONE);
                ct_NoRecords.setVisibility(View.VISIBLE);
                improveHelper.dismissProgressDialog();

            } else if (ImproveHelper.isNetworkStatusAvialable(getActivity())) {
                outTaskApi();
            } else {
                cet_searchTasks.setVisibility(View.GONE);
                rv_tasks.setVisibility(View.GONE);
                ct_NoRecords.setVisibility(View.VISIBLE);
                improveHelper.dismissProgressDialog();
            }
        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            improveHelper.improveException(getActivity(), TAG, "setOutTasks", e);
        }
    }

    public void mPrepareRefreshData() {
        try {
//        ImproveHelper.showToastAlert(getActivity(), "OutTask refresh in progress");
            List<OutTaskDataModel> modelsListData = improveDataBase.getDataFromOutTaskTable(sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession());

            List<RefreshTaskDetails> refreshTaskDetailsList = new ArrayList<RefreshTaskDetails>();
            try {
                for (OutTaskDataModel outTaskDataModel : modelsListData) {

                    RefreshTaskDetails refreshTaskDetails = new RefreshTaskDetails();
                    refreshTaskDetails.setTaskId(outTaskDataModel.getTaskID());
                    refreshTaskDetails.setTaskDate(outTaskDataModel.getTaskUpdationDate());
                    refreshTaskDetailsList.add(refreshTaskDetails);

                }

                OutTaskRefreshSendData outTaskRefreshSendData = new OutTaskRefreshSendData();
                outTaskRefreshSendData.setDBNAME(sessionManager.getOrgIdFromSession());
                outTaskRefreshSendData.setUserId(sessionManager.getUserDataFromSession().getUserID());
                outTaskRefreshSendData.setPostId(sessionManager.getPostsFromSession());
                outTaskRefreshSendData.setInOutTaskResponseList(refreshTaskDetailsList);

                Gson gson = new Gson();
                Log.d(TAG, "mPrepareRefreshDataOutTask: " + gson.toJson(outTaskRefreshSendData));

                mOutTaskRefreshApi(outTaskRefreshSendData);

            } catch (Exception e) {
                Log.d(TAG, "mPrepareRefreshDataException: " + e.toString());
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "mPrepareRefreshData", e);
        }
    }

    private void mOutTaskRefreshApi(OutTaskRefreshSendData outTaskRefreshSendData) {
        try {
            improveHelper.showProgressDialog(getActivity().getResources().getString(R.string.please_wait));
            Call<List<OutTaskDataModel>> responseCall = getServices.getOutTaskRefreshData(outTaskRefreshSendData);
            responseCall.enqueue(new Callback<List<OutTaskDataModel>>() {
                @Override
                public void onResponse(Call<List<OutTaskDataModel>> call, Response<List<OutTaskDataModel>> response) {
                    long insertCount = 0;
                    if (response.body() != null) {
                        List<OutTaskDataModel> outTaskDataModelsList = response.body();
                        if (outTaskDataModelsList != null && outTaskDataModelsList.size() > 0) {

                            for (OutTaskDataModel outTaskDataModel : outTaskDataModelsList) {

                                if (outTaskDataModel.getDistributionStatus().equalsIgnoreCase("1")) {
//                                if(AppConstants.FromNotificationOnlyInTask != null && !AppConstants.FromNotificationOnlyInTask.isEmpty()
//                                        && improveDataBase.doesTableExist(OUT_TASK_TABLE) && !improveDataBase.isTableEmpty(OUT_TASK_TABLE)){
//                                    improveDataBase.deleteAllRowsInTable(OUT_TASK_TABLE);
////                                    AppConstants.FromNotificationOnlyInTask = "";
//                                }
                                    List<OutTaskDataModel> outTaskDataModelListNew = new ArrayList<>();
                                    outTaskDataModelListNew.clear();
                                    outTaskDataModelListNew.add(outTaskDataModel);

                                    List<OutTaskDataModel> deleteDuplicate = improveDataBase.getDataFromOutTaskTable(sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession());
                                    if (deleteDuplicate != null && deleteDuplicate.size() > 0) {
                                        for (int i = 0; i < deleteDuplicate.size(); i++) {
                                            for (int j = 0; j < outTaskDataModelListNew.size(); j++) {
                                                if (deleteDuplicate.get(i).getTaskName().equalsIgnoreCase(outTaskDataModelListNew.get(j).getTaskName())) {
                                                    improveDataBase.deleteRowsByPostIdInTable(OUT_TASK_TABLE, sessionManager.getPostsFromSession());
                                                }
                                            }
                                        }
/*
                                for (int i = 0; i < deleteDuplicate.size(); i++) {
                                    for (int j = 0; j < outTaskDataModelListResponse.size(); j++) {
                                        if (!deleteDuplicate.contains(outTaskDataModelListResponse.get(j).getTaskName())) {
                                            improveDataBase.insertIntoOutTaskTable(outTaskDataModelListResponse, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
                                        }
                                    }
                                }
*/
                                    }
                                    improveDataBase.insertIntoOutTaskTable(outTaskDataModelListNew, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
//                                insertCount = improveDataBase.insertIntoOutTaskTable(outTaskDataModelsList, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());

                               /* if (insertCount != -1) {
                                    setOutTasks();
                                }*/

                                } else if (outTaskDataModel.getDistributionStatus().equalsIgnoreCase("0")
                                        || outTaskDataModel.getDistributionStatus().equalsIgnoreCase("2")) {
                                    Log.d(TAG, "RefreshRChecked" + "RefreshRChecked");

                                    improveDataBase.UpdateOutTaskTable(outTaskDataModel, outTaskDataModel.getTaskID(), sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession());
//                                insertCount = improveDataBase.UpdateOutTaskTable(outTaskDataModel, outTaskDataModel.getTaskID(), sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(),sessionManager.getPostsFromSession());

                            /*    if (insertCount != -1) {
                                    setOutTasks();
                                }*/


//                                List<OutTaskDataModel> deleteAndInsertData = new ArrayList<>();
//                                deleteAndInsertData.add(outTaskDataModel);
//                                Gson gson = new Gson();
//                                List<TaskDepEmpDataModel> editTaskIndividualListCheck = new ArrayList<>();
//                                editTaskIndividualListCheck.addAll(Arrays.asList(gson.fromJson(outTaskDataModel.getEmpInfo(), TaskDepEmpDataModel[].class)));
//                                Log.d(TAG, "OutTaskDataR: "+new Gson().toJson(editTaskIndividualListCheck) );
//                                improveDataBase.insertIntoOutTaskTable(deleteAndInsertData, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
                                }
                            }
//                        showToastAlert(getActivity(), "Refreshed Successfully");
                        } /*else {
                        improveHelper.dismissProgressDialog();
//                        showToastAlert(getActivity(), "No records!");
//                        mPrepareRefreshData();
                    }*/
                    }
                    improveHelper.dismissProgressDialog();
                    AppConstants.OUT_TASK_REFRESH_BOOLEAN = false;
                    AppConstants.PROGRESS_TASK = 0;
                    setOutTasks();

                }

                @Override
                public void onFailure(Call<List<OutTaskDataModel>> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "onFailureOutTaskRefresh: " + t.toString());
                }
            });
        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            improveHelper.improveException(getActivity(), TAG, "mOutTaskRefreshApi", e);
        }
    }

    public void noRecords() {
        try {
            cet_searchTasks.setVisibility(View.GONE);
            rv_tasks.setVisibility(View.GONE);
            ct_NoRecords.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "noRecords", e);
        }
    }

}
