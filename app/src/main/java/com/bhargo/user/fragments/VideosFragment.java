package com.bhargo.user.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.adapters.VideosAdapter;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.ELGetUserDistributionRequestData;
import com.bhargo.user.pojos.ELGetUserDistributionsRefreshResponse;
import com.bhargo.user.pojos.ELGetUserDistributionsResponse;
import com.bhargo.user.pojos.EL_GetUserDistributions;
import com.bhargo.user.pojos.GetUserDistributionsResponse;
import com.bhargo.user.pojos.RefreshELearning;
import com.bhargo.user.pojos.firebase.UserDetails;
import com.bhargo.user.screens.BottomNavigationActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.screens.BottomNavigationActivity.iv_appListRefresh;
import static com.bhargo.user.screens.BottomNavigationActivity.iv_appListSearch;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideosFragment extends Fragment implements BottomNavigationActivity.OnBackPressedListener {

    private static final String TAG = "VideosFragment";

    public ImproveHelper improveHelper;
    CustomEditText cet_videosSearch;
    FrameLayout fl_videosSearch;
    CustomTextView ct_alNoRecords;
    ImageView iv_no_data;
    RecyclerView rv_videos_list;
    GetServices getServices;
    SessionManager sessionManager;
    ImproveDataBase improveDataBase;
    int refresh;
    DatabaseReference mFirebaseDatabaseReference;
    private View rootView;
    private ViewGroup viewGroup;
    private List<GetUserDistributionsResponse> gudMainResponseList = new ArrayList<>();
    private List<GetUserDistributionsResponse> gudMainResponseListR = new ArrayList<>();
    private List<GetUserDistributionsResponse> getGudr_DBList;
    private VideosAdapter videosAdapter;
    private Context context;
    private boolean fromCallAction;

    public VideosFragment(int refresh) {
        this.refresh = refresh;
    }

    public static String[] GetStringArray(List<String> arr) {

        // declaration and initialise String Array
        String[] str = new String[arr.size()];

        // Convert ArrayList to object array
        Object[] objArr = arr.toArray();

        // Iterating and converting to String
        int i = 0;
        for (Object obj : objArr) {
            str[i++] = (String) obj;
        }

        return str;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            //Theme
            setBhargoTheme(getActivity(),AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
            // Inflate the layout for this fragment
//        rootView = inflater.inflate(R.layout.fragment_videos, container, false);
            rootView = inflater.inflate(R.layout.fragment_videos_copy, container, false);
            viewGroup = container;
            context = getActivity();
            if(getArguments()!=null&&getArguments().containsKey("fromCallAction")) {
                fromCallAction = getArguments().getBoolean("fromCallAction");
            }
            ((BottomNavigationActivity) getActivity()).setOnBackPressedListener(this);
            improveDataBase = new ImproveDataBase(getActivity());
            improveHelper = new ImproveHelper(getActivity());
            sessionManager = new SessionManager(getActivity());
            mFirebaseDatabaseReference = FirebaseDatabase.getInstance("https://bhargocommunication-8c36e.firebaseio.com/").getReference(sessionManager.getOrgIdFromSession());
//        mFirebaseDatabaseReference = FirebaseDatabase.getInstance("https://improvecommunication-c08c9.firebaseio.com").getReference(sessionManager.getOrgIdFromSession());

            fl_videosSearch = rootView.findViewById(R.id.fl_videosSearch);
            cet_videosSearch = rootView.findViewById(R.id.cet_videosSearch);
            ct_alNoRecords = rootView.findViewById(R.id.ct_alNoRecords);
            iv_no_data = rootView.findViewById(R.id.iv_no_data);
            rv_videos_list = rootView.findViewById(R.id.rv_videos_list);

//        BottomNavigationActivity.ct_FragmentTitle.setText(getActivity().getResources().getString(R.string.e_learning));
            String[] TabsData=AppConstants.WINDOWS_AVAILABLE.split("\\|");
            BottomNavigationActivity.ct_FragmentTitle.setText(TabsData[4].split("\\^")[2]);

            long timeSec = 84561;// Json output
            int hours = (int) timeSec / 3600;
            int temp = (int) timeSec - hours * 3600;
            int mins = temp / 60;
            temp = temp - mins * 60;
            int secs = temp;

            String requiredFormat = hours + ": " + mins + ": " + secs;//hh:mm:ss formatted string
            Log.d(TAG, "onCreateViewTimeCheck: " + requiredFormat);
//        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
            GridLayoutManager llm=new GridLayoutManager(getActivity(),2);
            rv_videos_list.setLayoutManager(llm); // set LayoutManager to RecyclerView// set LayoutManager to RecyclerView
//        videosAdapter = new VideosAdapter(getActivity(), new ArrayList<GetUserDistributionsResponse>());
            getServices = RetrofitUtils.getUserService();
//        improveHelper.showProgressDialog(getActivity().getResources().getString(R.string.please_wait));
            getGudr_DBList = new ArrayList<>();
            getGudr_DBList.clear();
//        if(AppConstants.EL_SINGLE_TIME) {
//            getActivity().startActivity(new Intent(getActivity(), ELearningListActivityNew.class));
//            AppConstants.EL_SINGLE_TIME = true;
//        }
            improveHelper.showProgressDialog(context.getResources().getString(R.string.please_wait));
            if (refresh == 0) {
                mELearningFromDB();
            } else {
    //            improveHelper.showProgressDialog(context.getResources().getString(R.string.please_wait));
                if(ImproveHelper.isNetworkStatusAvialable(getActivity())) {
                    mPrepareRefreshData();
                }
            }


            cet_videosSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    // filter your list from your input
                    filter(s.toString());
                }
            });
            firebaseAppsListener();

        } catch (Resources.NotFoundException e) {
            ImproveHelper.improveException(getActivity(), TAG, "onCreateView", e);

        }
        return rootView;
    }

    private void mPrepareRefreshData() {
        try {
            List<EL_GetUserDistributions> responses = new ArrayList<>();
            List<GetUserDistributionsResponse> gudResponseListDB = new ArrayList<>();
            gudResponseListDB = improveDataBase.getDataFromE_LearningTable(sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(), "",sessionManager.getUserTypeIdsFromSession());

            RefreshELearning refreshELearning = new RefreshELearning();
            refreshELearning.setUserId(sessionManager.getUserDataFromSession().getUserID());
            refreshELearning.setPostId(sessionManager.getPostsFromSession());
            if (gudResponseListDB != null && gudResponseListDB.size() > 0) {
                for (int i = 0; i < gudResponseListDB.size(); i++) {
                    EL_GetUserDistributions distributions = new EL_GetUserDistributions();
                    distributions.setDistributionId(gudResponseListDB.get(i).getDistributionId());
                    distributions.setDistributionDate(gudResponseListDB.get(i).getDistributionDate());

                    responses.add(distributions);
                }
            }

        refreshELearning.setGetUserDistributionsResponseList(ImproveHelper.removeDuplicates(responses));
        Gson gson = new Gson();
        String jsonObjectRefresh = gson.toJson(refreshELearning);
        Log.d(TAG, "RefreshELearning: " + jsonObjectRefresh);
            mELearningRefreshApi(refreshELearning);
        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            improveHelper.improveException(getActivity(), TAG, "mPrepareRefreshData", e);
        }
    }

    @Override
    public void onFragmentBackPress() {

//        exitAlertDialog();

        ImproveHelper.replaceFragment(rootView, new AppsListFragment(), "AppsListFragment");

    }

    /*ELearning Refresh API*/

    private void mELearningRefreshApi(RefreshELearning refreshELearning) {
        try {
            Call<ELGetUserDistributionsRefreshResponse> listCall = getServices.getUserDistributionsRefresh(refreshELearning);
            listCall.enqueue(new Callback<ELGetUserDistributionsRefreshResponse>() {
                @Override
                public void onResponse(Call<ELGetUserDistributionsRefreshResponse> call, Response<ELGetUserDistributionsRefreshResponse> response) {
                    if (response.body() != null) {
                        improveHelper.dismissProgressDialog();
                        gudMainResponseListR = new ArrayList<>();
                        gudMainResponseListR.clear();
                        gudMainResponseListR = response.body().getNewRegistrations();

                        if (gudMainResponseListR != null && gudMainResponseListR.size() > 0) {
                            for (int i = 0; i < gudMainResponseListR.size(); i++) {

                                if (gudMainResponseListR.get(i).getDistributionStatus().equalsIgnoreCase("2")) { //Update
                                    String resDistributionId = gudMainResponseListR.get(i).getDistributionId();
                                    List<GetUserDistributionsResponse> responsesListUpdate = new ArrayList<>();
                                    responsesListUpdate.add(gudMainResponseListR.get(i));
                                    improveDataBase.UpdateELearningTable(gudMainResponseListR.get(i), resDistributionId, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
//                                updateStatus();
                                } else if (gudMainResponseListR.get(i).getDistributionStatus().equalsIgnoreCase("0")) { //Deleted or deActive
                                    String resDistributionId = gudMainResponseListR.get(i).getDistributionId();
                                    improveDataBase.UpdateELearningTable(gudMainResponseListR.get(i), resDistributionId, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
                                } else if (gudMainResponseListR.get(i).getDistributionStatus().equalsIgnoreCase("1")) { // New or Active
                                    List<GetUserDistributionsResponse> responseListNew = new ArrayList<>();
                                    responseListNew.add(gudMainResponseListR.get(i));
                                    improveDataBase.insertIntoELearningTable(responseListNew, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
                                }
                            }
//                        ImproveHelper.showToastAlert(getActivity(), "E-Learning Updated.");
                        }
                    }

//                    mELearningFromDB();
                    getELDataFromDB();
                    //
                    updateFirebaseStatus();
                    AppConstants.PROGRESS_E_LEARNING = 0;
                }

                @Override
                public void onFailure(Call<ELGetUserDistributionsRefreshResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.toString());
                    improveHelper.dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            improveHelper.improveException(getActivity(), TAG, "mELearningRefreshApi", e);
        }
    }


    //    public void mGetUserDistributions(String separator, String groupIds) {
    public void mGetUserDistributions() {
        try {

            ELGetUserDistributionRequestData data = new ELGetUserDistributionRequestData();
            data.setUserId(sessionManager.getUserDataFromSession().getUserID());
            data.setPostId(sessionManager.getPostsFromSession());
            Call<ELGetUserDistributionsResponse> listCall = getServices.getUserDistributionsResponseCall(data);

            listCall.enqueue(new Callback<ELGetUserDistributionsResponse>() {
                @Override
                public void onResponse(Call<ELGetUserDistributionsResponse> call, Response<ELGetUserDistributionsResponse> response) {

                    ELGetUserDistributionsResponse distributionsResponse = response.body();
                    if (distributionsResponse != null) {
                        if(distributionsResponse.getStatus().equalsIgnoreCase("200")){
                            gudMainResponseList.clear();
                            gudMainResponseList = distributionsResponse.getUserDistributions();

                            if (gudMainResponseList != null && gudMainResponseList.size() > 0) {

                                // Insert E-Learning Data into Database
                                improveDataBase.insertIntoELearningTable(gudMainResponseList, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());

                                for (int i = 0; i < gudMainResponseList.size(); i++) {
                                    String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + gudMainResponseList.get(i).getTopicName().replaceAll(" ", "_") + "/";
                                    DownloadFileFromURLTask urlTask = new DownloadFileFromURLTask(context, gudMainResponseList.get(i).getTopicName(), strSDCardUrl);
                                    urlTask.execute(gudMainResponseList.get(i).getTopicCoverImage());
                                }

//                                mELearningFromDB();
                                getELDataFromDB();
                                updateFirebaseStatus();
                        }else{
                                iv_no_data.setVisibility(View.VISIBLE);
                                ct_alNoRecords.setVisibility(View.VISIBLE);
                                rv_videos_list.setVisibility(View.GONE);
                                cet_videosSearch.setVisibility(View.GONE);
                                iv_appListRefresh.setVisibility(View.GONE);
                                iv_appListSearch.setVisibility(View.GONE);
                                improveHelper.dismissProgressDialog();
                            }
                        } else {

                            iv_no_data.setVisibility(View.VISIBLE);
                            ct_alNoRecords.setVisibility(View.VISIBLE);
                            iv_appListRefresh.setVisibility(View.GONE);
                            iv_appListSearch.setVisibility(View.GONE);
                            rv_videos_list.setVisibility(View.GONE);
                            fl_videosSearch.setVisibility(View.GONE);
                            cet_videosSearch.setVisibility(View.GONE);
                            improveHelper.dismissProgressDialog();
                        }
                    } else {
                        iv_no_data.setVisibility(View.VISIBLE);
                        ct_alNoRecords.setVisibility(View.VISIBLE);
                        iv_appListRefresh.setVisibility(View.GONE);
                        iv_appListSearch.setVisibility(View.GONE);
                        rv_videos_list.setVisibility(View.GONE);
                        fl_videosSearch.setVisibility(View.GONE);
                        cet_videosSearch.setVisibility(View.GONE);
                        improveHelper.dismissProgressDialog();
                    }
                }

                @Override
                public void onFailure(Call<ELGetUserDistributionsResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.toString());
                    improveHelper.dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            improveHelper.improveException(getActivity(), TAG, "mGetUserDistributions", e);
        }
    }
//    public void mGetUserDistributionsOld() {
//        try {
////        Log.d(TAG, "mGetUserDistributions: " + sessionManager.getUserDataFromSession().getUserID() + separator + groupIds);
//            Call<List<GetUserDistributionsResponse>> listCall = getServices.getUserDistributionsResponseCall(
//                    sessionManager.getUserDataFromSession().getUserID(), sessionManager.getOrgIdFromSession(), "");
////        Call<List<GetUserDistributionsResponse>> listCall = getServices.getUserDistributionsResponseCall(
////                sessionManager.getUserDataFromSession().getUserID(), sessionManager.getOrgIdFromSession(),sessionManager.getPostsFromSession());
////        Call<List<GetUserDistributionsResponse>> listCall = getServices.getUserDistributionsResponseCall(sessionManager.getUserDataFromSession().getUserID() + separator + groupIds, sessionManager.getOrgIdFromSession(),sessionManager.getPostsFromSession());
//
//            listCall.enqueue(new Callback<List<GetUserDistributionsResponse>>() {
//                @Override
//                public void onResponse(Call<List<GetUserDistributionsResponse>> call, Response<List<GetUserDistributionsResponse>> response) {
//
//                    if (response.body() != null) {
//
//
//                        gudMainResponseList.clear();
////                    gudMainResponseList.clear();
//                        gudMainResponseList = response.body();
//
//                        if (gudMainResponseList != null && gudMainResponseList.size() > 0) {
//                            // Insert E-Learning Data into Database
////                        getGudr_DBList = gudMainResponseList;
////                        improveDataBase.insertIntoELearningTable(gudMainResponseList, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(),sessionManager.getPostsFromSession());
//                            improveDataBase.insertIntoELearningTable(gudMainResponseList, sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID());
//
//                            for (int i = 0; i < gudMainResponseList.size(); i++) {
//                                String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + gudMainResponseList.get(i).getTopicName().replaceAll(" ", "_") + "/";
//                                DownloadFileFromURLTask urlTask = new DownloadFileFromURLTask(context, gudMainResponseList.get(i).getTopicName(), strSDCardUrl);
//                                urlTask.execute(gudMainResponseList.get(i).getTopicCoverImage());
//                            }
//
//                            mELearningFromDB();
//
//                            updateFirebaseStatus();
//                        } else {
//
//                            ct_alNoRecords.setVisibility(View.VISIBLE);
//                            rv_videos_list.setVisibility(View.GONE);
//                            cet_videosSearch.setVisibility(View.GONE);
//                            improveHelper.dismissProgressDialog();
//                        }
//                    } else {
//                        ct_alNoRecords.setVisibility(View.VISIBLE);
//                        rv_videos_list.setVisibility(View.GONE);
//                        cet_videosSearch.setVisibility(View.GONE);
//                        improveHelper.dismissProgressDialog();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<GetUserDistributionsResponse>> call, Throwable t) {
//                    Log.d(TAG, "onFailure: " + t.toString());
//                    improveHelper.dismissProgressDialog();
//                }
//            });
//        } catch (Exception e) {
//            improveHelper.improveException(getActivity(), TAG, "mGetUserDistributions", e);
//        }
//    }

    public void filter(String text) {
        try {
            if (!text.isEmpty()) {
                List<GetUserDistributionsResponse> list = new ArrayList();
                if (getGudr_DBList != null) {
                    for (GetUserDistributionsResponse d : getGudr_DBList) {
                        //or use .equal(text) with you want equal match
                        //use .toLowerCase() for better matches
                        if (d.getTopicName().toLowerCase().contains(text.toLowerCase())) {
                            list.add(d);
                        }
                    }
                }
                //Update recyclerView
                videosAdapter.updateList(list);
                videosAdapter.setFromCallAction(fromCallAction);
            } else {
                List<GetUserDistributionsResponse> newList = new ArrayList<>();
                videosAdapter.updateList(newList);
                videosAdapter.updateList(getGudr_DBList);
                videosAdapter.setFromCallAction(fromCallAction);
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "filter", e);
        }
    }

    public void mELearningFromDB() {
        try {
//        getGudr_DBList.clear();
            getGudr_DBList = improveDataBase.getDataFromE_LearningTable
                    (sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(),
                            sessionManager.getPostsFromSession(),sessionManager.getUserTypeIdsFromSession());
            if (getGudr_DBList != null && getGudr_DBList.size() > 0) {
                rv_videos_list.setVisibility(View.VISIBLE);
                fl_videosSearch.setVisibility(View.VISIBLE);
                cet_videosSearch.setVisibility(View.VISIBLE);
                iv_no_data.setVisibility(View.GONE);
                ct_alNoRecords.setVisibility(View.GONE);
                iv_appListRefresh.setVisibility(View.VISIBLE);
                iv_appListSearch.setVisibility(View.VISIBLE);
                videosAdapter = new VideosAdapter(getActivity(), getGudr_DBList);
                videosAdapter.setFromCallAction(fromCallAction);
                rv_videos_list.setAdapter(videosAdapter);
                improveHelper.dismissProgressDialog();
            } else if (isNetworkStatusAvialable(getActivity())) {

                mGetUserDistributions();

            } else {
                improveHelper.dismissProgressDialog();
                fl_videosSearch.setVisibility(View.GONE);
                rv_videos_list.setVisibility(View.GONE);
                cet_videosSearch.setVisibility(View.GONE);
                iv_no_data.setVisibility(View.VISIBLE);
                ct_alNoRecords.setVisibility(View.VISIBLE);
                iv_appListRefresh.setVisibility(View.GONE);
                iv_appListSearch.setVisibility(View.GONE);
                improveHelper.snackBarAlertFragment(getActivity(), viewGroup);

            }
        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            improveHelper.improveException(getActivity(), TAG, "mELearningFromDB", e);
        }
    }
    public void getELDataFromDB() {
        try {
//        getGudr_DBList.clear();
            getGudr_DBList = improveDataBase.getDataFromE_LearningTable
                    (sessionManager.getOrgIdFromSession(), sessionManager.getUserDataFromSession().getUserID(),
                            sessionManager.getPostsFromSession(),sessionManager.getUserTypeIdsFromSession());
            if (getGudr_DBList != null && getGudr_DBList.size() > 0) {
                rv_videos_list.setVisibility(View.VISIBLE);
                fl_videosSearch.setVisibility(View.VISIBLE);
                cet_videosSearch.setVisibility(View.VISIBLE);
                iv_no_data.setVisibility(View.GONE);
                ct_alNoRecords.setVisibility(View.GONE);
                iv_appListRefresh.setVisibility(View.VISIBLE);
                iv_appListSearch.setVisibility(View.VISIBLE);
                videosAdapter = new VideosAdapter(getActivity(), getGudr_DBList);
                videosAdapter.setFromCallAction(fromCallAction);
                rv_videos_list.setAdapter(videosAdapter);
                improveHelper.dismissProgressDialog();
            }  else {
                improveHelper.dismissProgressDialog();
                fl_videosSearch.setVisibility(View.GONE);
                rv_videos_list.setVisibility(View.GONE);
                cet_videosSearch.setVisibility(View.GONE);
                iv_no_data.setVisibility(View.VISIBLE);
                ct_alNoRecords.setVisibility(View.VISIBLE);
                iv_appListRefresh.setVisibility(View.GONE);
                iv_appListSearch.setVisibility(View.GONE);
//                improveHelper.snackBarAlertFragment(getActivity(), viewGroup);

            }
        } catch (Exception e) {
            improveHelper.dismissProgressDialog();
            improveHelper.improveException(getActivity(), TAG, "mELearningFromDB", e);
        }
    }

    @Override
    public void onResume() {
//        improveHelper.dismissProgressDialog();
        super.onResume();
    }

    public void exitAlertDialog() {

        try {
            new AlertDialog.Builder(getActivity())
    //set icon
    //set title
                    .setIcon(getActivity().getResources().getDrawable(R.drawable.icon_bhargo_user))
                    .setTitle(R.string.are_you_sure)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.P)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what would happen when positive button is clicked
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }
                    })
    //set negative button
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what should happen when negative button is clicked

                        }
                    })
                    .show();
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "exitAlertDialog", e);

        }

    }

    private void firebaseAppsListener() {
        try {
            mFirebaseDatabaseReference.child("Users").child(sessionManager.getUserChatID()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        com.bhargo.user.pojos.firebase.UserDetails userDetails = dataSnapshot.getValue(UserDetails.class);
//                    String status = userDetails.getStatus();

//                    String eLearningStatus = userDetails.geteLearningStatus();
                        int eLearningStatus = userDetails.geteLearningStatus();

                        if (eLearningStatus != 0 && eLearningStatus == 1) {
//                        Toast.makeText(getActivity(),"Update",Toast.LENGTH_SHORT).show();
                            mPrepareRefreshData();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, "onCancelledELearning: " + databaseError.toString());
                    improveHelper.dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "firebaseAppsListener", e);
        }
    }

    public void updateFirebaseStatus() {
        try {
            if (context == null) {
                context = getActivity();
            }
            if (sessionManager == null) {
                sessionManager = new SessionManager(context);
            }
            mFirebaseDatabaseReference = FirebaseDatabase.getInstance("https://bhargocommunication-8c36e.firebaseio.com").getReference(sessionManager.getOrgIdFromSession());
            mFirebaseDatabaseReference.child("Users").
                    orderByChild("Mobile").equalTo(sessionManager.
                    getUserDetailsFromSession().getPhoneNo())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                HashMap<String, Object> statusMap = new HashMap<>();
                                statusMap.put("eLearningStatus", 0);
                                mFirebaseDatabaseReference.child("Users").child(sessionManager.getUserChatID()).updateChildren(statusMap);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            improveHelper.dismissProgressDialog();
                        }
                    });
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "updateFirebaseStatus", e);
        }
    }

    public class DownloadFileFromURLTask extends AsyncTask<String, String, String> {

        String strFileName, strAppName, strSDCardFolderPath;
        File file, saveFilePath;
        Context context;


        public DownloadFileFromURLTask(Context context, String strAppName, String strSDCardFolderPath) {
            this.context = context;
            this.strAppName = strAppName;
            this.strSDCardFolderPath = strSDCardFolderPath;

        }

        /**
         * Downloading file in background thread
         */


        @SuppressLint("SdCardPath")
        @Override
        protected String doInBackground(String... f_url) {
            Log.i(TAG, "do in background" + f_url[0]);
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                String[] strsplit = f_url[0].split("/");
                strFileName = strsplit[strsplit.length - 1];

//                String strSeparatePaths = AppConstants.API_NAME_CHANGE +"/"+strAppName.replaceAll(" ", "_")+"/";
                File cDir = context.getExternalFilesDir(strSDCardFolderPath);
                saveFilePath = new File(cDir.getAbsolutePath(), strFileName.replaceAll(" ", "_"));
                Log.d(TAG, "AppsListFilesSave: " + saveFilePath);
//                outFile = new File(file, strFileName);
                // Output stream to write file
                OutputStream output = new FileOutputStream(saveFilePath);

                byte[] data = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();


            } catch (Exception e) {

                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @SuppressLint("SdCardPath")
        @Override
        protected void onPostExecute(String file_url) {
            Log.i(TAG, "on post execute!: " + file_url);

            // dismiss progressbars after the file was downloaded
//            if (saveFilePath.exists()) {
            Log.d(TAG, "onPostExecuteAppsList: " + strFileName);
//                ImproveHelper.showToastAlert(context, strFileName + "\n" + " Download successfully!");
//            }
            /*else {
//                ImproveHelper.showToastAlert(context, "No File Exist");
            }*/
        }
    }


}
