package com.bhargo.user.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bhargo.user.R;
import com.bhargo.user.screens.BottomNavigationActivity;
import com.bhargo.user.utils.ImproveHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements BottomNavigationActivity.OnBackPressedListener {

//    private static final String TAG = "ProfileFragment";
//    public ImproveHelper improveHelper;
//    LinearLayout main_profile_bg;
//    CircleImageView iv_circle_Profile;
//    CustomTextView tv_ProfileName, tv_ProfileDesignation, tv_ProfileRole, tv_ProfileDepartment, tv_ProfileEmail, tv_ProfilePhoneNo;
//    CustomButton btn_logout;
//    Context context;
//    File file;
//    nk.mobileapps.spinnerlib.SearchableSpinner sp_post;
//    private SessionManager sessionManager;
    private View rootView;
//    private ViewGroup viewGroup;
//    private Animation animSlideUp;
//    private SearchableSpinner orgSpinner;
//    private ImproveDataBase improveDataBase;
//    private String strOrgName, strSelectedPostId;
//    private String i_OrgId, strFileName, strFileNameURL;
//    private Bundle bundle;
//    private GetServices getServices;
//    private List<OrgList> orgListsAppsList;
//    private ArrayAdapter adapterAllTypes;
//    private ArrayList<SpinnerData> spinnerDataArrayListA;
//    String firebaseURL = "https://improvecommunication-c08c9.firebaseio.com/";

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_profile, container, false);

//        rootView = inflater.inflate(R.layout.activity_profile_check, container, false);
//        BottomNavigationActivity.ct_FragmentTitle.setText(getActivity().getResources().getString(R.string.profile));
//        ((BottomNavigationActivity) getActivity()).setOnBackPressedListener(this);
//        context = getActivity();
//        viewGroup = container;
//
//        improveHelper = new ImproveHelper(getActivity());
//        improveDataBase = new ImproveDataBase(getActivity());
//
//        sessionManager = new SessionManager(context);
//        getServices = RetrofitUtils.getUserService();
//
//        main_profile_bg = rootView.findViewById(R.id.main_profile_bg);
//        iv_circle_Profile = rootView.findViewById(R.id.iv_circle_Profile);
//        tv_ProfileName = rootView.findViewById(R.id.tv_ProfileName);
//        tv_ProfileDesignation = rootView.findViewById(R.id.tv_ProfileDesignation);
//        tv_ProfileRole = rootView.findViewById(R.id.tv_ProfileRole);
//        tv_ProfileDepartment = rootView.findViewById(R.id.tv_ProfileDepartment);
//        tv_ProfileEmail = rootView.findViewById(R.id.tv_ProfileEmail);
//        tv_ProfilePhoneNo = rootView.findViewById(R.id.tv_ProfilePhoneNo);
//
//        btn_logout = rootView.findViewById(R.id.btn_logout);
//        orgSpinner = rootView.findViewById(R.id.sp_ORG);
//        sp_post = rootView.findViewById(R.id.sp_post);
//
//        animSlideUp = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
////        iv_login_icon.setAnimation(animSlideUp);
//
//        main_profile_bg.setAnimation(animSlideUp);
//
//        orgListsAppsList = improveDataBase.getDataFromOrganisationTable(sessionManager.getUserDetailsFromSession().getPhoneNo());
//        adapterAllTypes = new ArrayAdapter(context, android.R.layout.simple_spinner_item, orgListsAppsList);
//        orgSpinner.setAdapter(adapterAllTypes);
//        setSelectedOrganisation(orgListsAppsList);
//        orgSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                strOrgName = orgSpinner.getSelectedItem().toString();
//                i_OrgId = orgListsAppsList.get(i).getOrgID();
//                if (!sessionManager.getOrgIdFromSession().equalsIgnoreCase(i_OrgId)) {
//                    PrefManger.clearSharedPreferences(context);
//                    AppConstants.OrgChange = 1;
////                    mUserDetails(i_OrgId, viewGroup);
//                } else {
//                    AppConstants.OrgChange = 0;
//                }
//                Log.d(TAG, "onItemSelectedORGList: " + i_OrgId);
//                if (i_OrgId != null && !i_OrgId.isEmpty()) {
//                    sessionManager.createOrgSession(i_OrgId);
//                    improveHelper.createImproveUserFolder("Improve_User/" + i_OrgId);
//
//                }
//
//                PrefManger.putSharedPreferencesString(context, AppConstants.SP_ORGANISATION_ID, i_OrgId);
//
//                sessionManager.createOrgSession(i_OrgId);
//
//                changeChatID();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//
//        });

//        sp_post.setItems(loadPosts(), new nk.mobileapps.spinnerlib.SearchableSpinner.SpinnerListener() {
//            @Override
//            public void onItemsSelected(View v, List<SpinnerData> items, int position) {
//                if (sp_post.getSelectedId() != null) {
////                    strSelectedPost = sp_post.getSelectedName();
//                    strSelectedPostId = sp_post.getSelectedId();
////                    Log.d(TAG, "onItemsSelectedPost: " + strSelectedPost);
////                    if (isNetworkStatusAvialable(context)) {
//                    if (!sessionManager.getPostsFromSession().equalsIgnoreCase(strSelectedPostId)) {
//                        AppConstants.PostChange = 1;
//                    } else {
//                        AppConstants.PostChange = 0;
//                    }
//                    sessionManager.createPostsSession(strSelectedPostId);
//                    Log.d(TAG, "onSelectedPostId: "+strSelectedPostId);
////                        mPostsDetailsToServer(sessionManager.getOrgIdFromSession(), sessionManager.getPostsFromSession(), viewGroup);
////                    }
//
//                }
//            }
//        });


//        btn_logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                android.app.AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
////set icon
////set title
//                        .setTitle(R.string.want_to_logout)
//                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //set what would happen when positive button is clicked
//                                improveDataBase.deleteDatabaseTables(sessionManager.getUserDataFromSession().getUserID());
//                                sessionManager.logoutUser();
//                            }
//                        })
////set negative button
//                        .setNegativeButton(R.string.d_no, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //set what should happen when negative button is clicked
//
//                            }
//                        })
//                        .show();
//            }
//        });
//        setProfileValues(sessionManager,viewGroup);
        return rootView;
    }

//    private void changeChatID() {
//
//
//        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance(firebaseURL).getReference(sessionManager.getOrgIdFromSession());
//        firebaseDatabase.child("Users").orderByChild("Mobile").equalTo(sessionManager.getUserDetailsFromSession().getPhoneNo()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                if (dataSnapshot.exists()) {
//
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        com.bhargo.user.pojos.firebase.UserDetails userDetails = snapshot.getValue(com.bhargo.user.pojos.firebase.UserDetails.class);
//                        sessionManager.createUserChatID(userDetails.getID());
//                        Log.d(TAG, "userchatid: "+userDetails.getID());
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

//    private void setSelectedOrganisation(List<OrgList> orgListsAppsList) {
//
//        for (int i = 0; i < orgListsAppsList.size(); i++) {
//
//            if (sessionManager.getOrgIdFromSession().equalsIgnoreCase(orgListsAppsList.get(i).getOrgID())) {
//
//                orgSpinner.setSelection(i);
//            }
//        }
//    }

//    public void setProfileValues(SessionManager sessionManager, ViewGroup rootView) {
//
//        if (!isNetworkStatusAvialable(context)) {
//if(sessionManager.getUserDetailsFromSession().getProfilePIc() !=null){
//            String[] str = sessionManager.getUserDetailsFromSession().getProfilePIc().split("/");
//            strFileNameURL = str[str.length - 1];
//            String strFilepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + "ProfileImage" + "/" + strFileNameURL;
//            File file = new File(strFilepath);
//            if (file.exists()) {
//                Bitmap myBitmap = BitmapFactory.decodeFile(strFilepath);
//                iv_circle_Profile.setImageBitmap(myBitmap);
//            } else {
//                iv_circle_Profile.setImageDrawable(getResources().getDrawable(R.drawable.user));
//            }
//
//            improveHelper.snackBarAlertFragment(getActivity(), rootView);}
//        } else {
//            if (sessionManager.getUserDetailsFromSession().getProfilePIc() != null) {
//                if (!sessionManager.getUserDetailsFromSession().getProfilePIc().equalsIgnoreCase("NA")) {
//                    Glide.with(context).load(sessionManager.getUserDetailsFromSession().getProfilePIc())
//                            .placeholder(R.drawable.user)
//                            .into(iv_circle_Profile);
//                    new DownloadProfileFromURLTask().execute(sessionManager.getUserDetailsFromSession().getProfilePIc());
//
//                } else {
//                    iv_circle_Profile.setImageDrawable(getResources().getDrawable(R.drawable.user));
//                }
//            } else {
//                iv_circle_Profile.setImageDrawable(getResources().getDrawable(R.drawable.user));
//            }
//
//        }
//        tv_ProfileName.setText(sessionManager.getUserDetailsFromSession().getName());
//        tv_ProfileDesignation.setText(sessionManager.getUserDetailsFromSession().getDesignation());
//        tv_ProfileRole.setText(sessionManager.getUserDetailsFromSession().getRole());
//        tv_ProfileDepartment.setText(sessionManager.getUserDetailsFromSession().getDepartment());
//        tv_ProfileEmail.setText(sessionManager.getUserDetailsFromSession().getEmail());
//        tv_ProfilePhoneNo.setText(sessionManager.getUserDetailsFromSession().getPhoneNo());
//
//    }


    @Override
    public void onFragmentBackPress() {
        ImproveHelper.replaceFragment(rootView, new InfoFragment(), "InfoFragment");
//        ImproveHelper.replaceFragment(rootView, new VideosFragment(0), "VideosFragment");
    }

//    public void mGetTokenId(String i_OrgId) {
//        if(getActivity() != null) {
//            FirebaseMessaging.getInstance().getToken()
//                    .addOnCompleteListener(new OnCompleteListener<String>() {
//                        @Override
//                        public void onComplete(@NonNull Task<String> task) {
//                            if (!task.isSuccessful()) {
//                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                                return;
//                            }  // Get new FCM registration token
//
//                            String newToken = task.getResult();
//                            Log.d(TAG, "GetTokenId: " + newToken);
//
//                            DeviceIdSendData deviceIdSendData = new DeviceIdSendData();
//                            deviceIdSendData.setUserId(sessionManager.getUserDataFromSession().getUserID());
//                            deviceIdSendData.setOrgId(i_OrgId);
//                            deviceIdSendData.setDevcieID(newToken);
//
//                            /*Send device id to server*/
//                            if (newToken != null && !newToken.isEmpty()) {
//                                mDeviceIdSendToServerApi(deviceIdSendData);
//                            } else {
////                    improveHelper.dismissProgressDialog();
//                            }}
//                    });
//        }
//    }

    /*Send device id to server*/
//    public void mDeviceIdSendToServerApi(DeviceIdSendData deviceIdSendData) {
//
//        Call<DeviceIdResponse> deviceIdResponseCall = getServices.sendDeviceIdToServer("",null);
//
//        deviceIdResponseCall.enqueue(new Callback<DeviceIdResponse>() {
//            @Override
//            public void onResponse(Call<DeviceIdResponse> call, Response<DeviceIdResponse> response) {
////                improveHelper.dismissProgressDialog();
//                if (response.body() != null) {
//                    Log.d(TAG, "onResponseSuccess: " + response.body().getMessage());
//
//                    sessionManager.createDeviceIdSession(deviceIdSendData.getDevcieID());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<DeviceIdResponse> call, Throwable t) {
//
//                Log.d(TAG, "onResponseFailDeviceId: " + t.getStackTrace());
////                improveHelper.dismissProgressDialog();
//            }
//        });
//    }

    /*User Details API*/
//    private void mUserDetails(String i_OrgId, ViewGroup viewGroup) {
//
////        improveHelper.showProgressDialog(getString(R.string.please_wait));
//
//        UserDetailsData userDetailsData = new UserDetailsData();
//        userDetailsData.setMobileNo(sessionManager.getUserDetailsFromSession().getPhoneNo());
//        userDetailsData.setOrgId(i_OrgId);
//
//        final Call<UserDetailsModel> userDetailsCall = getServices.iUserDetails(userDetailsData);
//
//        userDetailsCall.enqueue(new Callback<UserDetailsModel>() {
//            @Override
//            public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
//
//                if (response.body() != null) {
//
//                    UserDetailsModel userDetailsModel = response.body();
//                    if (userDetailsModel.getStatus().equalsIgnoreCase("200")) {
//                        UserDetails userDetails = userDetailsModel.getUserDeatils();
//                        List<UserPostDetails> userPostDetails = userDetailsModel.getUserPostDetails();
//                        UserDetailsModel.ReportingUserDeatils reportingUserDetails = userDetailsModel.getReportingUserDeatils();
//                        String strUserId = userDetails.getUserId();
//                        Log.d("OTP_UserId", strUserId);
//                        /*Setting User Details Object into SharedPreferences*/
//                        Gson gson = new Gson();
//                        String jsonUserDeatils = gson.toJson(userDetails);
//                        Log.d(TAG, "ProfileOrgChangedUserdDetails: " + jsonUserDeatils);
//                        PrefManger.putSharedPreferencesString(context, SP_USER_DETAILS, jsonUserDeatils);
//
//                        String jsonReportingUserDetails = gson.toJson(reportingUserDetails);
//                        Log.d(TAG, "ProfileOrgChangedPostDetails: " + jsonReportingUserDetails);
//                        PrefManger.putSharedPreferencesString(context, SP_REPORTING_USER_DETAILS, jsonReportingUserDetails);
//
//                        createLoginSession(userDetails, userPostDetails);
//
//                        setProfileValues(sessionManager, viewGroup);
//                        sp_post.setItems(loadPosts(), new nk.mobileapps.spinnerlib.SearchableSpinner.SpinnerListener() {
//                            @Override
//                            public void onItemsSelected(View v, List<SpinnerData> items, int position) {
//                                if (sp_post.getSelectedId() != null) {
//
//                                    strSelectedPostId = sp_post.getSelectedId();
//
//                                    if (!sessionManager.getPostsFromSession().equalsIgnoreCase(strSelectedPostId)) {
//                                        AppConstants.PostChange = 1;
//                                    } else {
//                                        AppConstants.PostChange = 0;
//                                    }
//                                    sessionManager.createPostsSession(strSelectedPostId);
//                                    Log.d(TAG, "onSelectedPostId: "+strSelectedPostId);
//                                }
//                            }
//                        });
//
//                    } else {
//                        ImproveHelper.showToast(context, "No user details found");
//                    }
//
//                } else {
//                    ImproveHelper.showToast(context, "No user details found");
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<UserDetailsModel> call, Throwable t) {
////                improveHelper.dismissProgressDialog();
//                Log.d(TAG, "onFailureUserDetailsData: " + t.toString());
//            }
//        });
//    }
//
//    private void createLoginSession(UserDetails userDetails, List<UserPostDetails> userPostDetailsList) {
//
//        sessionManager.createLoginSession(userDetails);
//        sessionManager.createUserPostDetailsSession(userPostDetailsList);
//        /*Send DeviceId To Server*/
//        mGetTokenId(sessionManager.getOrgIdFromSession());
//
//    }
//
//    public ArrayList<SpinnerData> loadPosts() {
//
//        spinnerDataArrayListA = new ArrayList<>();
//        if (sessionManager.getUserPostDetailsFromSession() != null && sessionManager.getUserPostDetailsFromSession().size() > 0) {
//            for (int i = 0; i < sessionManager.getUserPostDetailsFromSession().size(); i++) {
//
//                SpinnerData spinnerData = new SpinnerData();
//                spinnerData.setName(sessionManager.getUserPostDetailsFromSession().get(i).getName());
//                spinnerData.setId(sessionManager.getUserPostDetailsFromSession().get(i).getPostID());
//                spinnerData.setObject(sessionManager.getUserPostDetailsFromSession());
//
//                spinnerDataArrayListA.add(spinnerData);
//
//                if (sessionManager.getPostsFromSession().equalsIgnoreCase(spinnerData.getId())) {
//                    spinnerData.setSelected(true);
//                }
//            }
//        }
//
//        return spinnerDataArrayListA;
//
//    }

//    public class DownloadProfileFromURLTask extends AsyncTask<String, String, String> {
//
//        /**
//         * Downloading file in background thread
//         */
//
//
//        @SuppressLint("SdCardPath")
//        @Override
//        protected String doInBackground(String... f_url) {
//            Log.i(TAG, "do in background" + f_url[0]);
//            int count;
//            try {
//                URL url = new URL(f_url[0]);
//                URLConnection conection = url.openConnection();
//                conection.connect();
//                // getting file length
//                int lenghtOfFile = conection.getContentLength();
//
//                // input stream to read file - with 8k buffer
//                InputStream input = new BufferedInputStream(url.openStream(), 8192);
//
//                String[] strsplit = f_url[0].split("/");
//                strFileName = strsplit[strsplit.length - 1];
//
//                file = new File(
//                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + "ProfileImage");
//                if (!file.exists()) {
//                    file.mkdirs();
//                }
////                file = new File(context.getExternalFilesDir(null),"/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats");
//                /*file = new File(context.getExternalFilesDir(null),"/" +AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicTitle);
//                if (!file.exists())
//                    file.mkdirs();*/
//                File outFile = new File(file, strFileName);
//                // Output stream to write file
//                OutputStream output = new FileOutputStream(outFile);
//
//                byte[] data = new byte[1024];
//
//                long total = 0;
//
//                while ((count = input.read(data)) != -1) {
//                    total += count;
//                    // publishing the progress....
//                    // After this onProgressUpdate will be called
//                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
//
//                    // writing data to file
//                    output.write(data, 0, count);
//                }
//
//                // flushing output
//                output.flush();
//
//                // closing streams
//                output.close();
//                input.close();
//
//            } catch (Exception e) {
//                Log.e("Error: ", e.getMessage());
//            }
//
//            return null;
//        }
//
//        /**
//         * Updating progress bar
//         */
//        protected void onProgressUpdate(String... progress) {
//            // setting progress percentage
////            mProgress.setProgress(Integer.parseInt(progress[0]));
////            progressBar = v.findViewById(R.id.circularProgressbar);
////            progressBar.setProgress(Integer.parseInt(progress[0]));
////            tv_progStatus.setText(progress[0]);
////            textProgress2.setText(progress[0]);
//        }
//
//        /**
//         * After completing background task Dismiss the progress dialog
//         **/
//        @SuppressLint("SdCardPath")
//        @Override
//        protected void onPostExecute(String file_url) {
//            Log.i(TAG, "on post excute!: " + file_url);
//
//            // dismiss progressbars after the file was downloaded
////            ll_progressbar = v.findViewById(R.id.ll_progressbar);
////            ll_progressbar.setVisibility(View.GONE);
//
//            // Displaying downloaded image into image view
//            // Reading image path from sdcard
//            Log.d(TAG, "doInBackgroundSet: " + file);
//
//            if (file != null && file.exists()) {
//
////                ll_progressbar.setVisibility(View.GONE);
//
////                    ll_progressbar.setVisibility(View.GONE);
////                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
////                    if (filesInfoModelList.get(position).getFileNameExt().equalsIgnoreCase("jpg")) {
////                        imageView.setImageBitmap(myBitmap);
////                    }
////                ImproveHelper.showToastAlert(context, strFileName + "\n" + " Download successfully!");
//                strFileNameURL = strFileName;
//            }
//
//
////            notifyDataSetChanged();
//        }
//    }
}
