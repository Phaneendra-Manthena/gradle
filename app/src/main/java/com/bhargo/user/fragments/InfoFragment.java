package com.bhargo.user.fragments;

import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.ConversationItemClickListener;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.UserPostDetails;
import com.bhargo.user.pojos.firebase.GetAllMessagesModel;
import com.bhargo.user.pojos.firebase.Group;
import com.bhargo.user.pojos.firebase.GroupsListResponse;
import com.bhargo.user.pojos.firebase.Notification;
import com.bhargo.user.screens.BottomNavigationActivity;
import com.bhargo.user.screens.SearchChatUsers;
import com.bhargo.user.screens.sesssionchat.ConversationListAdapter;
import com.bhargo.user.screens.sesssionchat.SessionChatActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment implements ConversationItemClickListener, BottomNavigationActivity.OnBackPressedListener, AdapterView.OnItemClickListener {


    private static final String TAG = "InfoFragment";

    public ImproveHelper improveHelper;
    FloatingActionButton fab_new_chat;
    Context context;
    SessionManager sessionManager;
    GetServices getServices;
    ConversationListAdapter conversationListAdapter;

    BaseActivity baseActivity;
    CustomTextView tv_licence;
    LinearLayout layout_no_messages;

    String userChatId = null;
    Parcelable mListState;
    List<Notification> notificationList;
    ImproveDataBase improveDataBase;
    LocalBroadcastManager bm;
    int isRefresh = 0;
    String userId = null;
    String postId = null;
    String orgId = null;
    private View rootView;
    private RecyclerView rv_chatsList;

    AutoCompleteTextView et_search_sessions;


    private final BroadcastReceiver onJsonReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String new_group_id = intent.getStringExtra("new_group_id");
                boolean refreshGroups = intent.getBooleanExtra("refreshGroups", false);
                if (refreshGroups) {
                    if (!improveDataBase.checkGroupExists(new_group_id,orgId)) {
                        getGroupsList();
                    } else {
                        loadLocalConversationList();
                    }
                } else {
                    loadLocalConversationList();
                }
            }
        }
    };
    private DatabaseReference firebaseDatabase;
    private LinearLayoutManager linearLayoutManager;

    public InfoFragment() {
        // Required empty public constructor
    }

    public InfoFragment(int refresh) {
        // Required empty public constructor
        isRefresh = refresh;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            //Theme
            setBhargoTheme(getActivity(),AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
            // Inflate the layout for this fragment
            rootView = inflater.inflate(R.layout.fragment_info, container, false);
            setRetainInstance(true);

            layout_no_messages = rootView.findViewById(R.id.layout_no_messages);
            tv_licence = rootView.findViewById(R.id.tv_licence);
            fab_new_chat = rootView.findViewById(R.id.fab_new_chat);
            rv_chatsList = rootView.findViewById(R.id.rv_chat_list);
            rv_chatsList.setVisibility(View.VISIBLE);
            linearLayoutManager = new LinearLayoutManager(getActivity());
            rv_chatsList.setLayoutManager(linearLayoutManager);
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            rv_chatsList.setHasFixedSize(true);

            et_search_sessions = rootView.findViewById(R.id.cet_usersSearch);
            et_search_sessions.setOnItemClickListener(this);

            String[] TabsData=AppConstants.WINDOWS_AVAILABLE.split("\\|");
            BottomNavigationActivity.ct_FragmentTitle.setText(TabsData[1].split("\\^")[2]);
            ((BottomNavigationActivity) getActivity()).setOnBackPressedListener(this);
//        BottomNavigationActivity.ct_FragmentTitle.setText(getActivity().getResources().getString(R.string.info));

            context = getActivity();
            baseActivity = new BaseActivity();
            getServices = RetrofitUtils.getUserService();
            sessionManager = new SessionManager(context);
            improveHelper = new ImproveHelper(context);
            improveDataBase = new ImproveDataBase(context);
            userChatId = sessionManager.getUserChatID();

            userId = sessionManager.getUserDataFromSession().getUserID();
            postId = sessionManager.getPostsFromSession();
            orgId = sessionManager.getOrgIdFromSession();

       /* firebaseDatabase = FirebaseDatabase.getInstance().getReference(sessionManager.getOrgIdFromSession());
        firebaseDatabase.keepSynced(true);*/
//        if(FirebaseApp.getInstance().getOptions().getDatabaseUrl()!=null) {
//
//            firebaseDatabase = FirebaseDatabase.getInstance().getReference(sessionManager.getOrgIdFromSession());
//            firebaseDatabase.keepSynced(true);
//        }


            notificationList = new ArrayList<>();

            conversationListAdapter = new ConversationListAdapter(context, notificationList);
            rv_chatsList.setAdapter(conversationListAdapter);
            conversationListAdapter.setCustomClickListener(this);

            fab_new_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(context, SearchChatUsers.class));

                }
            });

            List<UserPostDetails> userPostDetailsList = sessionManager.getUserPostDetailsFromSession();
            for (UserPostDetails postDetails : userPostDetailsList) {
                if (sessionManager.getPostsFromSessionPostName().equalsIgnoreCase(postDetails.getName())) {
                    if (postDetails.getSessionStatus()!=null && postDetails.getSessionStatus().equalsIgnoreCase("Y")) {
                        fab_new_chat.setVisibility(View.VISIBLE);
                    } else {
                        fab_new_chat.setVisibility(View.GONE);
                    }
                }

            }
            fab_new_chat.setVisibility(View.VISIBLE);

/*
        List<String> postsList = improveDataBase.getPosts("202008081057461671", "SRHU00000012");
        Log.d(TAG, "onCreateView: " + postsList);
        if (postsList == null || postsList.size() == 0) {
            Log.d(TAG, "onCreateView1: " + postsList);
        } else {
            Log.d(TAG, "onCreateView1: " + postsList);
        }*/
            //Refresh Groups

            if (sessionManager.getGroupsStatus().equalsIgnoreCase("0")) {
                getGroupsList();
            } else {
                refreshData();
            }

            et_search_sessions.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().equalsIgnoreCase("")) {
                        conversationListAdapter.updateData(notificationList);
                    }
                }
            });



        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "onCreateView", e);

        }
        return rootView;
    }

    private void refreshData() {
        try {
            notificationList.clear();
            notificationList = improveDataBase.getConversationListForPostId(userId, postId);
            if(notificationList.size() == 0){
                List<Notification> notificationListNoPost =  improveDataBase.getConversationListForNoPost(userId, orgId);
                notificationList.addAll(notificationListNoPost);
            }
//            if ((isRefresh == 0 || isRefresh == 1) && notificationList.size() == 0) {
            if ((isRefresh == 0 && notificationList.size() == 0) || isRefresh == 1) {
                if (ImproveHelper.isNetworkStatusAvialable(context)) {
                    loadMessagesFromServer();
                }
            } else if (notificationList.size() > 0) {
                loadConversationList();
            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "refreshData", e);
        }
    }


    private void loadConversationList() {
        try {
            notificationList.clear();
            notificationList = improveDataBase.getConversationList(userId, postId,orgId);
            if(notificationList.size() == 0){
                List<Notification> notificationListNoPost =  improveDataBase.getConversationListForNoPost(userId, orgId);
                notificationList.addAll(notificationListNoPost);
            }
            if (notificationList.size() > 0) {
                layout_no_messages.setVisibility(View.GONE);
                rv_chatsList.setVisibility(View.VISIBLE);
                conversationListAdapter.setData(notificationList);
            } else {
//                loadMessagesFromServer(postId);
                Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
            }
            listConversationsForSearch();
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "loadConversationList", e);
        }
    }

    private void loadLocalConversationList() {
        try {
            notificationList.clear();
            notificationList = improveDataBase.getConversationList(userId, postId,orgId);
            if(notificationList.size()==0){
                List<Notification> notificationListNoPost =  improveDataBase.getConversationListForNoPost(userId, orgId);
                notificationList.addAll(notificationListNoPost);
            }
            if (notificationList.size() > 0) {
                layout_no_messages.setVisibility(View.GONE);
                rv_chatsList.setVisibility(View.VISIBLE);
                conversationListAdapter.setData(notificationList);
            }else{
               /* List<Notification> notificationListNoPost =  improveDataBase.getConversationListForNoPost(userId, orgId);
                notificationList.addAll(notificationListNoPost);*/
                layout_no_messages.setVisibility(View.VISIBLE);
                rv_chatsList.setVisibility(View.GONE);
            }
            listConversationsForSearch();
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "loadLocalConversationList", e);
        }
    }

    private void listConversationsForSearch() {
        try {
            ArrayAdapter<Notification> sessiondataadapter = new ArrayAdapter<Notification>(
                    context, R.layout.support_simple_spinner_dropdown_item,
                    notificationList);
            sessiondataadapter
                    .setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

            et_search_sessions.setThreshold(1);
            et_search_sessions.setAdapter(sessiondataadapter);
//        et_search_users.setTextColor(Color.RED);


            et_search_sessions.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        // on focus off
                        String str = et_search_sessions.getText().toString();

                        ArrayAdapter<Notification> surveydataadapter = new ArrayAdapter<Notification>(
                                context,
                                android.R.layout.simple_spinner_item, notificationList);
                        for (int i = 0; i < surveydataadapter.getCount(); i++) {
                            String temp = surveydataadapter.getItem(i).toString();
                            if (str.compareTo(temp) == 0) {
                                return;
                            }
                        }

                        et_search_sessions.setText("");
                    }
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "listConversationsForSearch", e);
        }}


    private void loadMessagesFromServer() {
        try {
            improveHelper.showProgressDialog("Loading Data..");
            Map<String, String> data = new HashMap<>();
            String userId = sessionManager.getUserDataFromSession().getUserID();

            data.put("UserId", userId);
            data.put("OrgId", sessionManager.getOrgIdFromSession());
//            data.put("PostId", "");
            data.put("PostId", sessionManager.getPostsFromSession());

            Call<GetAllMessagesModel> getAllAppNamesDataCall = getServices.iGetAllMessagesList(sessionManager.getAuthorizationTokenId(),data);
            getAllAppNamesDataCall.enqueue(new Callback<GetAllMessagesModel>() {
                @Override
                public void onResponse(Call<GetAllMessagesModel> call, Response<GetAllMessagesModel> response) {
                    improveHelper.dismissProgressDialog();
                    if (response.body()!=null&&response.body().getStatus().equalsIgnoreCase("200") && response.body().getMessage().equalsIgnoreCase("Success")) {
                        List<Notification> notificationList = response.body().getTopicsData();
                        if (notificationList.size() > 0) {
                            improveDataBase.deleteUserNotifications(userId, postId,orgId);
                            long result = improveDataBase.insertListIntoNotificationTable(notificationList, userId, orgId);
                            if (result > 0) {
                                loadConversationList();
                            }
                        } else {
                            loadLocalConversationList();
                        }
                    }else{
                        layout_no_messages.setVisibility(View.VISIBLE);
                        rv_chatsList.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<GetAllMessagesModel> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "loadMessagesFromServer", e);
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    public void onPause() {
        super.onPause();

    }

    public String convertDate(String s) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        String date = null;
        String systemDate = null;
        String msgDate = null;
        String previousDayDate = baseActivity.getPreviousDayDate();

        try {
            msgDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(Long.parseLong(s)));

            systemDate = baseActivity.getDateFromDeviceForCal();
            Date dateOne = format.parse(msgDate);
            Date dateTwo = format.parse(systemDate);
            if (dateOne.equals(dateTwo)) {

                date = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date(Long.parseLong(s))).replace("AM", "am").replace("PM", "pm");
            } else if (msgDate.equalsIgnoreCase(previousDayDate)) {
                date = "Yesterday";

            } else {
                date = msgDate;
            }


        } catch (ParseException e) {
            ImproveHelper.improveException(getActivity(), TAG, "convertDate", e);

        }
        return date;

    }

    public final void getTime() {
        try {
            Calendar calendar = Calendar.getInstance();
            long now = calendar.getTimeInMillis();
            Date nowDate = calendar.getTime();

            TimeZone current = calendar.getTimeZone();
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "getTime", e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();


        if (mListState != null) {
            linearLayoutManager.onRestoreInstanceState(mListState);
        }

    }

    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        // Save list state

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        AppConstants.Infoactive = true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bm = LocalBroadcastManager.getInstance(context);
        IntentFilter actionReceiver = new IntentFilter();
        actionReceiver.addAction("sessionListRefresh");

        bm.registerReceiver(onJsonReceived, actionReceiver);


    }

    @Override
    public void onStop() {
        super.onStop();
        AppConstants.Infoactive = false;
        bm.unregisterReceiver(onJsonReceived);
    }

    @Override
    public void onItemIdClick(Context context, String group_icon, String group_id, String group_name, String session_id, String session_name) {

        openChatActivity(context, session_id, session_name, group_name, group_id, group_icon);

    }

    private void openChatActivity(Context context, String sessionId, String sessionName, String groupName, String groupId, String groupIcon) {
        try {
            String write = improveDataBase.checkWritePermission(groupId, sessionManager.getUserDataFromSession().getUserID(),orgId);
            Intent i = new Intent(context, SessionChatActivity.class);

            i.putExtra("SessionID", sessionId);
            i.putExtra("SessionName", sessionName);
            i.putExtra("GroupName", groupName);
            i.putExtra("GroupId", groupId);
            i.putExtra("SessionIcon", groupIcon);
            i.putExtra("Write", Boolean.parseBoolean(write));
            i.putExtra("From", false);
//            i.putExtra("Write", "true");
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(i);

        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            improveHelper.improveException(getActivity(), TAG, "openChatActivity", e);
        }
    }

    private boolean isFileExists(String filename) {
        boolean isFileExist = false;
        try {
            String[] imgUrlSplit = filename.split("/");
            String strUrl = imgUrlSplit[imgUrlSplit.length - 1];
            String fileName = strUrl.replaceAll(" ", "_");

            String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + fileName;
            File file = new File(context.getExternalFilesDir(null), strSDCardUrl);
            isFileExist = file.exists();
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "isFileExists", e);
        }
        return isFileExist;
    }

    public void setImageFromSDCard(String strImagePath, ImageView imageView) {
        try {
            String[] imgUrlSplit = strImagePath.split("/");
            String strUrl = imgUrlSplit[imgUrlSplit.length - 1];
            String fileName = strUrl.replaceAll(" ", "_");

            String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + fileName;
            File imgFile = new File(context.getExternalFilesDir(null), strSDCardUrl);

            if (isFileExists(strSDCardUrl)) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                imageView.setImageBitmap(myBitmap);

            }
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "setImageFromSDCard", e);
        }
    }

    private void getGroupsList() {
        try {
            improveHelper.showProgressDialog("Loading Data..");

            Map<String, String> data = new HashMap<>();
            String userId = sessionManager.getUserDataFromSession().getUserID();
            data.put("UserID", sessionManager.getUserDataFromSession().getUserID());
            data.put("OrgId", sessionManager.getOrgIdFromSession());

            Call<GroupsListResponse> getAllAppNamesDataCall = getServices.iGetAllGroupsList(sessionManager.getAuthorizationTokenId(),data);
            getAllAppNamesDataCall.enqueue(new Callback<GroupsListResponse>() {
                @Override
                public void onResponse(Call<GroupsListResponse> call, Response<GroupsListResponse> response) {
                    improveHelper.dismissProgressDialog();
                    if (response.body() !=null && response.body().getStatus().equalsIgnoreCase("200") && response.body().getMessage().equalsIgnoreCase("Success")) {
                        List<Group> groupList = response.body().getUserGroupsList();
                        if (groupList.size() > 0) {
                            sessionManager.createGroupsStatusSession("1");
                            improveDataBase.deleteGroupTable();
                            long result = improveDataBase.insertListIntoGroupTable(groupList, userId,sessionManager.getOrgIdFromSession());
                            if (result > 0) {
                                loadMessagesFromServer();
                            }
                        } else {
                            loadMessagesFromServer();
                        }
                    } else {
                        loadMessagesFromServer();
                    }

                }

                @Override
                public void onFailure(Call<GroupsListResponse> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    refreshData();
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(getActivity(), TAG, "getGroupsList", e);
        }
    }

    @Override
    public void onFragmentBackPress() {
        ImproveHelper.replaceFragment(rootView, new AppsListFragment(), "AppsListFragment");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            String session_name = et_search_sessions.getText().toString();
            List<Notification> temp = new ArrayList();
            for (Notification d : notificationList) {

                if (d.getSessionName().toLowerCase().equalsIgnoreCase(session_name)) {
                    temp.add(d);
                }
            }
            //update recyclerview
            conversationListAdapter.updateData(temp);
            hideKeyboard(context, getActivity().getCurrentFocus());
            et_search_sessions.clearFocus();
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "onItemClick", e);

        }
    }

    private class DownloadFileFromURLTask extends AsyncTask<String, String, String> {


        private final ImageView imageView;
        String imageName = null;
        private String strFileName;
        private File file;

        public DownloadFileFromURLTask(ImageView v) {
            this.imageView = v;


        }

        /**
         * Downloading file in background thread
         */

        @Override
        protected String doInBackground(String... f_url) {
            Log.i(TAG, "do in background" + f_url[0]);
            imageName = f_url[0];
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
                file = new File(context.getExternalFilesDir(null), "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats");

                if (!file.exists()) {
                    file.mkdirs();
                }
                File outFile = new File(file, strFileName);
                // Output stream to write file
                OutputStream output = new FileOutputStream(outFile);

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

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/

        @Override
        protected void onPostExecute(String file_url) {
            Log.i(TAG, "on post excute!: " + file_url);
            // dismiss progressbars after the file was downloaded

            // Displaying downloaded image into image view
            // Reading image path from sdcard
            String[] imgUrlSplit = imageName.split("/");
            String strUrl = imgUrlSplit[imgUrlSplit.length - 1];
            String fileName = strUrl.replaceAll(" ", "_");

            String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + fileName;
            File imgFile = new File(context.getExternalFilesDir(null), strSDCardUrl);

            if (isFileExists(strSDCardUrl)) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                imageView.setImageBitmap(myBitmap);

            }
        }
    }


    public void hideKeyboard(Context mContext, View view) {
        try {
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(getActivity(), TAG, "hideKeyboard", e);

        }
    }

}