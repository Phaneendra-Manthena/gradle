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
import com.bhargo.user.adapters.InTasksAdapter;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.InTaskDataModel;
import com.bhargo.user.pojos.InTaskRefreshSendData;
import com.bhargo.user.pojos.RefreshTaskDetails;
import com.bhargo.user.screens.BottomNavigationActivity;
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

import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;


/**
 * A simple {@link Fragment} subclass.
 */
public class InTasksFragment extends Fragment {

    private static final String TAG = "InTasksFragment";
    public ImproveHelper improveHelper;
    CustomTextView ct_NoRecords;
    CustomEditText cet_searchTasks;
    GetServices getServices;
    RecyclerView rv_tasks;
    CustomEditText cet_tasks;
    SessionManager sessionManager;
    String strTaskStatusFilter = "0", refreshData = "0";
    //    List<InTaskDataModel> inTaskDataModelsList;
    ImproveDataBase improveDataBase;
    private View rootView;
    private ViewGroup viewGroup;
    private InTasksAdapter inTasksAdapter;
    private List<InTaskDataModel> ModelsList;

    public InTasksFragment(String strTaskStatus, String refreshData) {

        this.strTaskStatusFilter = strTaskStatus;
        this.refreshData = refreshData;


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Theme
        setBhargoTheme(getActivity(),AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_in_tasks, container, false);
        viewGroup = container;
        improveHelper = new ImproveHelper(getActivity());
        initViews();


        if ((refreshData != null && refreshData.equalsIgnoreCase("1") && AppConstants.TASK_REFRESH.equalsIgnoreCase("InTaskRefresh"))
                || AppConstants.IN_TASK_REFRESH_BOOLEAN) {
            if (isNetworkStatusAvialable(getActivity())) {
                mPrepareRefreshData();
            } else {
                //                setInTasks("0");
                setInTasks(strTaskStatusFilter);
            }
        } else {
            setInTasks(strTaskStatusFilter);
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
                    inTasksAdapter.filterList(ModelsList);
                }
            }
        });

        return rootView;

    }

    private void initViews() {
        try {
            sessionManager = new SessionManager(getContext());
            improveDataBase = new ImproveDataBase(getContext());
            getServices = RetrofitUtils.getUserService();
            BottomNavigationActivity.iv_tasksFilter.setVisibility(View.VISIBLE);
            BottomNavigationActivity.iv_appListRefresh.setVisibility(View.VISIBLE);

            cet_searchTasks = rootView.findViewById(R.id.cet_searchTasks);
            rv_tasks = rootView.findViewById(R.id.rv_tasks);
            ct_NoRecords = rootView.findViewById(R.id.ct_NoRecords);

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rv_tasks.setLayoutManager(llm);
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "initViews", e);
        }
    }

    private void inTasksApi() {
        try {
            if (isNetworkStatusAvialable(getActivity())) {

                improveHelper.showProgressDialog(getActivity().getResources().getString(R.string.please_wait));

                Call<List<InTaskDataModel>> responseCall = getServices.getInTasks(sessionManager.getUserDataFromSession().getUserID(), sessionManager.getOrgIdFromSession());

                responseCall.enqueue(new Callback<List<InTaskDataModel>>() {
                    @Override
                    public void onResponse(Call<List<InTaskDataModel>> call, Response<List<InTaskDataModel>> response) {

                        if (response.body() != null) {

                            List<InTaskDataModel> inTaskDataModelsResponse = response.body();

                            if (inTaskDataModelsResponse != null && inTaskDataModelsResponse.size() > 0) {

                                improveDataBase.insertIntoInTaskTable(inTaskDataModelsResponse, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
                                ModelsList = new ArrayList<>();
                                ModelsList = improveDataBase.getDataFromInTaskTable(sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(), "0", sessionManager.getPostsFromSession());
                                if (ModelsList != null && ModelsList.size() == 0) {
                                    improveHelper.dismissProgressDialog();
                                    ct_NoRecords.setVisibility(View.VISIBLE);
                                    rv_tasks.setVisibility(View.GONE);
                                    cet_searchTasks.setVisibility(View.GONE);

                                } else {
                                    setInTasks("0");
                                }

                            } else {
                                improveHelper.dismissProgressDialog();
                                ct_NoRecords.setVisibility(View.VISIBLE);
                                rv_tasks.setVisibility(View.GONE);
                                cet_searchTasks.setVisibility(View.GONE);
                            }

                        } else {

                            improveHelper.dismissProgressDialog();
                            cet_searchTasks.setVisibility(View.GONE);
                            rv_tasks.setVisibility(View.GONE);
                            ct_NoRecords.setVisibility(View.VISIBLE);

                        }
                        improveHelper.dismissProgressDialog();
                        //                    setInTasks("0");
                    }

                    @Override
                    public void onFailure(Call<List<InTaskDataModel>> call, Throwable t) {
                        Log.d(TAG, "onFailureTasks: " + t.toString());
                        improveHelper.dismissProgressDialog();
                    }
                });

            } else {

                improveHelper.dismissProgressDialog();
                cet_searchTasks.setVisibility(View.GONE);
                rv_tasks.setVisibility(View.GONE);
                ct_NoRecords.setVisibility(View.VISIBLE);
                BottomNavigationActivity.iv_tasksFilter.setVisibility(View.GONE);
                if (getActivity() != null && !isNetworkStatusAvialable(getActivity())) {
                    improveHelper.snackBarAlertFragment(getContext(), viewGroup);
                }

            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "inTasksApi", e);
        }
    }

    public void filter(String text) {
        try {
            List<InTaskDataModel> filteredNames = new ArrayList<>();

            //looping through existing elements
            for (InTaskDataModel inTaskDataModel : ModelsList) {
                //if the existing elements contains the search input
                if (inTaskDataModel.getTaskName().toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filteredNames.add(inTaskDataModel);
                }
            }

            //calling a method of the adapter class and passing the filtered list
            inTasksAdapter.filterList(filteredNames);
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "filter", e);
        }
    }

    public void setInTasks(String strTaskStatusFilter) {
        try {
            improveHelper.dismissProgressDialog();
            ModelsList = improveDataBase.getDataFromInTaskTable(sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(), strTaskStatusFilter, sessionManager.getPostsFromSession());
            if (ModelsList != null && ModelsList.size() > 0) {
                Collections.reverse(ModelsList);
                inTasksAdapter = new InTasksAdapter(getActivity(), ImproveHelper.removeDuplicates(ModelsList));
                rv_tasks.setAdapter(inTasksAdapter);
                inTasksAdapter.notifyDataSetChanged();
                rv_tasks.setVisibility(View.VISIBLE);
                cet_searchTasks.setVisibility(View.VISIBLE);
                ct_NoRecords.setVisibility(View.GONE);
                improveHelper.dismissProgressDialog();
            } else if (ModelsList != null && ModelsList.size() == 0 && !strTaskStatusFilter.equalsIgnoreCase("0")) {

                ct_NoRecords.setVisibility(View.VISIBLE);
                cet_searchTasks.setVisibility(View.GONE);
                rv_tasks.setVisibility(View.GONE);
                BottomNavigationActivity.iv_tasksFilter.setVisibility(View.GONE);
                improveHelper.dismissProgressDialog();

            } else if (isNetworkStatusAvialable(getActivity())) {
                improveHelper.dismissProgressDialog();
                inTasksApi();
            } else {
                ct_NoRecords.setVisibility(View.VISIBLE);
                cet_searchTasks.setVisibility(View.GONE);
                rv_tasks.setVisibility(View.GONE);
                BottomNavigationActivity.iv_tasksFilter.setVisibility(View.GONE);
                improveHelper.dismissProgressDialog();
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "setInTasks", e);
        }
    }

    public void mPrepareRefreshData() {
        try {
            //        ImproveHelper.showToastAlert(getActivity(), "InTask refresh in progress");
            List<InTaskDataModel> modelsListData = improveDataBase.getDataFromInTaskTable(sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(), "0", sessionManager.getPostsFromSession());

            List<RefreshTaskDetails> refreshTaskDetailsList = new ArrayList<>();
            try {
                for (InTaskDataModel inTaskDataModel : modelsListData) {
                    RefreshTaskDetails refreshTaskDetails = new RefreshTaskDetails();
                    refreshTaskDetails.setTaskId(inTaskDataModel.getTaskID());
                    refreshTaskDetails.setTaskDate(inTaskDataModel.getTaskUpdationDate());
                    refreshTaskDetailsList.add(refreshTaskDetails);
                }

                InTaskRefreshSendData inTaskRefreshSendData = new InTaskRefreshSendData();
                inTaskRefreshSendData.setDBNAME(sessionManager.getOrgIdFromSession());
                inTaskRefreshSendData.setUserId(sessionManager.getUserDataFromSession().getUserID());
                inTaskRefreshSendData.setPostId(sessionManager.getPostsFromSession());
                inTaskRefreshSendData.setInOutTaskResponseList(refreshTaskDetailsList);

                Gson gson = new Gson();
                Log.d(TAG, "mPrepareRefreshDataInTask: " + gson.toJson(inTaskRefreshSendData));

                mInTaskRefreshApi(inTaskRefreshSendData);

            } catch (Exception e) {
                Log.d(TAG, "mPrepareRefreshDataException: " + e.toString());
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "mPrepareRefreshData", e);
        }
    }

    private void mInTaskRefreshApi(InTaskRefreshSendData data) {
        try {
            improveHelper.showProgressDialog(getActivity().getResources().getString(R.string.please_wait));
            Call<List<InTaskDataModel>> responseCall = getServices.getInTaskRefreshData(data);
            responseCall.enqueue(new Callback<List<InTaskDataModel>>() {
                @Override
                public void onResponse(Call<List<InTaskDataModel>> call, Response<List<InTaskDataModel>> response) {
                    if (response.body() != null) {

                        List<InTaskDataModel> inTaskDataModelsList = response.body();

                        if (inTaskDataModelsList != null && inTaskDataModelsList.size() > 0) {

                            for (InTaskDataModel inTaskDataModel : inTaskDataModelsList) {
                                if (inTaskDataModel.getDistributionStatus().equalsIgnoreCase("1")) {

                                    List<InTaskDataModel> insertLatestInTaskData = new ArrayList<>();
                                    insertLatestInTaskData.add(inTaskDataModel);
                                    improveDataBase.insertIntoInTaskTable(insertLatestInTaskData, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
                                } else if (inTaskDataModel.getDistributionStatus().equalsIgnoreCase("2") ||
                                        inTaskDataModel.getDistributionStatus().equalsIgnoreCase("0")) {
                                    improveDataBase.UpdateInTaskTable(inTaskDataModel, inTaskDataModel.getTaskID(), sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(), sessionManager.getPostsFromSession());
                                }
                            }
                        }
                    }
                    improveHelper.dismissProgressDialog();

                    AppConstants.IN_TASK_REFRESH_BOOLEAN = false;
                    AppConstants.PROGRESS_TASK = 0;
                    setInTasks("0");
                }

                @Override
                public void onFailure(Call<List<InTaskDataModel>> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "onFailureInTaskRefresh: " + t.toString());
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "mInTaskRefreshApi", e);
        }
    }


}
