package com.bhargo.user.screens.onetoonechat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.interfaces.APIService;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.firebase.ChatDetails;
import com.bhargo.user.pojos.firebase.Client;
import com.bhargo.user.pojos.firebase.Conversation;
import com.bhargo.user.pojos.firebase.Data;
import com.bhargo.user.pojos.firebase.MyResponse;
import com.bhargo.user.pojos.firebase.Sender;
import com.bhargo.user.pojos.firebase.Token;
import com.bhargo.user.pojos.firebase.UserChatList;
import com.bhargo.user.pojos.firebase.UserDetails;
import com.bhargo.user.screens.ApplicationTypeActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.FilePicker;
import com.bhargo.user.utils.FileUploaderCommunication;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RecyclerSectionItemDecoration;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

public class ChatActivity extends BaseActivity implements View.OnClickListener, ChatUserStatusListener {


    public static final int REQUEST_APP_DISTRIBUTION_CODE = 666;
    public static final int REQUEST_GALLERY_CONTROL_CODE = 222;
    public static final int REQUEST_CAMERA_CONTROL_CODE = 333;
    public static final int REQUEST_AUDIO_CONTROL_CODE = 444;
    public static final int REQUEST_VIDEO_CONTROL_CODE = 555;
    public static final int VIEW_TYPE_USER_MESSAGE = 0;
    public static final int VIEW_TYPE_FRIEND_MESSAGE = 1;
    private static final String TAG = "ChatActivity";
    private static final int FILE_BROWSER_RESULT_CODE = 111;

    public static HashMap<String, Bitmap> bitmapAvataFriend;
    public Bitmap bitmapAvataUser;
    Context context;
    SessionManager sessionManager;
    String receiverUserID, receiverDesignation, receiverID, senderID, image, receiverName, receiverImage, senderImage, senderDesignation, senderName, receiverMobileNo, senderMobileNo;
    Boolean receiverIsActive;
    String USERTYPE = null;
    String msgType = "0";
    String MESSAGES_CHILD;
    DatabaseReference mFirebaseDatabaseReference;
    List<UserDetails> chatUsersList;
    APIService apiService;
    ValueEventListener seenListener;
    CardView rl_Chat;
    LinearLayout rl_send;
    GetServices getServices;
    boolean typingStarted = false;
    String status = null;
    String currentPhotoPath;
    List<String> keyList = new ArrayList<String>();
    ImproveHelper improveHelper;
    private RecyclerView recyclerChat;
    private ListMessageAdapter adapter;
    private Conversation consersation;
    private ImageButton btnSend;
    private EditText editWriteMessage;
    private ImageView iv_attachment;
    private LinearLayoutManager linearLayoutManager;
    private View theMenu;
    private View overlay;
    private View menu_document;
    private View menu_camera;
    private View menu_gallery;
    private View menu_audio;
    private View menu_video;
    private View menu_apps;
    private boolean menuOpen = false;

    public static String getPath(Context context, Uri uri) {
        String result = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                    result = cursor.getString(column_index);
                }
                cursor.close();
            }
            if (result == null) {
                result = "Not found";
            }
        } catch (Exception e) {
            ImproveHelper improveHelper = new ImproveHelper(context);
            improveHelper.improveException(context, TAG, "getPath", e);
        }

        return result;
    }

    @SuppressLint("NewApi")
    public static String getUriRealPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getBase64FromPath(String path) {
        String base64 = "";
        try {
            File file = new File(path);
            byte[] buffer = new byte[(int) file.length() + 100];
            @SuppressWarnings("resource")
            int length = new FileInputStream(file).read(buffer);
            base64 = Base64.encodeToString(buffer, 0, length,
                    Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        context = this;
        sessionManager = new SessionManager(context);
        improveHelper = new ImproveHelper(context);
        getServices = RetrofitUtils.getUserService();
        Intent intent = getIntent();

        chatUsersList = (List<UserDetails>) intent.getSerializableExtra("UsersList");

        receiverIsActive = Boolean.parseBoolean(chatUsersList.get(0).getIsActive());
        receiverID = chatUsersList.get(0).getID();
        receiverDesignation = chatUsersList.get(0).getDesignation();
        receiverUserID = chatUsersList.get(0).getUserid();
        receiverMobileNo = chatUsersList.get(0).getMobile();
        image = chatUsersList.get(0).getImagePath();
        receiverName = chatUsersList.get(0).getName();
        receiverImage = chatUsersList.get(0).getImagePath();
        USERTYPE = "S";
        Log.d(TAG, "onCreates: " + receiverUserID);
        senderID = sessionManager.getUserChatID();
        senderName = sessionManager.getUserDetailsFromSession().getName();
        senderMobileNo = sessionManager.getUserDetailsFromSession().getPhoneNo();
        senderImage = sessionManager.getUserDetailsFromSession().getProfilePIc();
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        initializeActionBar();
        enableBackNavigation(true);
        title.setText(receiverName);

        rl_Chat = findViewById(R.id.rl_chat);
        rl_send = findViewById(R.id.rl_send);
        if (!receiverIsActive) {
            rl_Chat.setVisibility(View.GONE);
            rl_send.setVisibility(View.GONE);
        }

        iv_circle_appIcon.setVisibility(View.VISIBLE);

        if (receiverImage.equals("default")) {
            iv_circle_appIcon.setImageResource(R.drawable.user);

        } else {
            if (isFileExists(receiverImage)) {
                setImageFromSDCard(receiverImage, iv_circle_appIcon);
            } else {

                DownloadFileFromURLTask fileFromURLTask = new DownloadFileFromURLTask(iv_circle_appIcon);
                fileFromURLTask.execute(receiverImage);
            }
        }

        ib_settings.setVisibility(View.GONE);

        consersation = new Conversation();
        theMenu = findViewById(R.id.the_menu);
        overlay = findViewById(R.id.overlay);

        menu_document = findViewById(R.id.menu_document);
        menu_camera = findViewById(R.id.menu_camera);
        menu_gallery = findViewById(R.id.menu_gallery);
        menu_audio = findViewById(R.id.menu_audio);
        menu_video = findViewById(R.id.menu_video);
        menu_apps = findViewById(R.id.menu_apps);

        iv_attachment = findViewById(R.id.iv_attachment);
        btnSend = (ImageButton) findViewById(R.id.btn_send);

        btnSend.setOnClickListener(this);
        iv_attachment.setOnClickListener(this);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance("https://bhargocommunication-8c36e.firebaseio.com").getReference(sessionManager.getOrgIdFromSession());
        mFirebaseDatabaseReference.keepSynced(true);

        BigDecimal bigDecimalReceiver = new BigDecimal(receiverMobileNo);
        BigDecimal bigDecimalSender = new BigDecimal(senderMobileNo);
        if (bigDecimalReceiver.compareTo(bigDecimalSender) == 1) {
            MESSAGES_CHILD = receiverMobileNo + senderMobileNo;
        } else {
            MESSAGES_CHILD = senderMobileNo + receiverMobileNo;
        }

        editWriteMessage = (EditText) findViewById(R.id.text_send);

        editWriteMessage.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(s.toString()) && s.toString().trim().length() >= 1) {
                    //Log.i(TAG, “typing started event…”);
                    typingStarted = true;
                    //send typing started status
                    updateTypingStatus();
                } else if (s.toString().trim().length() == 0 && typingStarted) {
                    //Log.i(TAG, “typing stopped event…”);
                    typingStarted = false;
                    //send typing stopped status
                    updateTypingStatus();
                }
            }
        });

        editWriteMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                boolean typingStarted = false;
                if (!hasFocus) {
                    typingStarted = false;
                    //send typing stopped status
                    updateTypingStatus();
                }
            }
        });
        if (receiverID != null && receiverName != null) {
            Log.d(TAG, "receiverID3: " + receiverID);
            linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerChat = (RecyclerView) findViewById(R.id.recycler_view);
            recyclerChat.setLayoutManager(linearLayoutManager);
            adapter = new ListMessageAdapter(this, MESSAGES_CHILD, consersation, bitmapAvataFriend, bitmapAvataUser, receiverID);

            mFirebaseDatabaseReference.child("Chats").child(MESSAGES_CHILD).addChildEventListener(new ChildEventListener() {

                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getValue() != null) {

                        ChatDetails newMessage = dataSnapshot.getValue(ChatDetails.class);

                        Log.d(TAG, "onChildAdded: " + newMessage.getFilePath());
                        keyList.add(dataSnapshot.getKey());
                        consersation.getListMessageData().add(newMessage);
                        adapter.notifyDataSetChanged();
                        linearLayoutManager.scrollToPosition(consersation.getListMessageData().size() - 1);

                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    ChatDetails newMessage = dataSnapshot.getValue(ChatDetails.class);
                    Log.d(TAG, "onChildChanged: " + newMessage.getFilePath());

                    for (int i = 0; i < consersation.getListMessageData().size(); i++) {

                        if (consersation.getListMessageData().get(i).getMessageID().equalsIgnoreCase(newMessage.getMessageID())) {

                            consersation.getListMessageData().set(i, newMessage);
                        }
                    }

                    adapter.updateChatListItems(consersation);


                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {


                    if (dataSnapshot.getValue() != null) {
                        ChatDetails newMessage = dataSnapshot.getValue(ChatDetails.class);
//
//                        boolean res = consersation.getListMessageData().remove(newMessage);
//                        adapter.updateChatListItems(consersation);

                        int index = keyList.indexOf(dataSnapshot.getKey());
                        consersation.getListMessageData().remove(index);
                        keyList.remove(index);
                        adapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            recyclerChat.setAdapter(adapter);
//            recyclerChat.smoothScrollToPosition(recyclerChat.getBottom());
//            RecyclerSectionItemDecoration sectionItemDecoration =
//                    new RecyclerSectionItemDecoration(context.getResources().getDimensionPixelSize(R.dimen.recycler_section_header_height),
//                            true,
//                            getSectionCallback(consersation.getListMessageData()));
//            recyclerChat.addItemDecoration(sectionItemDecoration);

        }


        checkUserOnlineStatus();


    }

    private void checkUserOnlineStatus() {
        try {
            mFirebaseDatabaseReference.child("Users").child(receiverID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    status = null;
                    if (dataSnapshot.exists()) {

                        UserDetails userDetails = dataSnapshot.getValue(UserDetails.class);
                        status = userDetails.getStatus();

                        if (status != null && status.equalsIgnoreCase("online")) {
                            subTitle.setVisibility(View.VISIBLE);
                            subTitle.setText(status);
                        } else {
                            subTitle.setVisibility(View.GONE);
                            subTitle.setText(status);
                        }
                    } else {
                        subTitle.setVisibility(View.GONE);
                        subTitle.setText(getString(R.string.offline));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            checkUserTypingStatus();


            seenMessage();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "checkUserOnlineStatus", e);
        }
    }

    private void checkUserTypingStatus() {
        try {
            Query query = mFirebaseDatabaseReference.child("Chats").child(MESSAGES_CHILD).limitToFirst(1);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ChatDetails newMessage = snapshot.getValue(ChatDetails.class);

                            mFirebaseDatabaseReference.child("Chats").child(MESSAGES_CHILD).child(newMessage.getMessageID()).child(receiverID).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.exists() && dataSnapshot.getValue() != null && dataSnapshot.getValue().toString().equalsIgnoreCase("true")) {

                                        Log.e("TAG", "onlinestatus" + dataSnapshot.getValue());

                                        subTitle.setVisibility(View.VISIBLE);
                                        subTitle.setText("typing...");

                                    } else if (status != null && status.equalsIgnoreCase("online")) {
                                        Log.d(TAG, "onDataChange_: " + status);
                                        subTitle.setVisibility(View.VISIBLE);
                                        subTitle.setText(status);
                                    } else {
                                        Log.d(TAG, "onDataChange__: " + status);
                                        subTitle.setVisibility(View.GONE);
                                        subTitle.setText(status);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "checkUserTypingStatus", e);
        }

    }

    public void updateTypingStatus() {
        try {
            Query query = mFirebaseDatabaseReference.child("Chats").child(MESSAGES_CHILD).limitToFirst(1);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ChatDetails newMessage = snapshot.getValue(ChatDetails.class);

                            HashMap<String, Object> statusMap = new HashMap<>();

                            statusMap.put(senderID, String.valueOf(typingStarted));

                            mFirebaseDatabaseReference.child("Chats").child(MESSAGES_CHILD).child(newMessage.getMessageID()).updateChildren(statusMap);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "updateTypingStatus", e);
        }
    }

    public void updateStatus(String status) {
        try {
            context = this;
            sessionManager = new SessionManager(context);
            mFirebaseDatabaseReference = FirebaseDatabase.getInstance("https://bhargocommunication-8c36e.firebaseio.com").getReference(sessionManager.getOrgIdFromSession());
            mFirebaseDatabaseReference.child("Users").
                    orderByChild("Mobile").equalTo(sessionManager.
                    getUserDetailsFromSession().getPhoneNo())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                HashMap<String, Object> statusMap = new HashMap<>();
                                statusMap.put("Status", status);
                                mFirebaseDatabaseReference.child("Users").child(sessionManager.getUserChatID()).updateChildren(statusMap);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "updateStatus", e);
        }

    }

    private RecyclerSectionItemDecoration.SectionCallback getSectionCallback(final List<ChatDetails> people) {
        return new RecyclerSectionItemDecoration.SectionCallback() {
            @Override
            public boolean isSection(int position) {

                return position == 0 || people.get(position).getCreatedDate().toString() != people.get(position - 1).getCreatedDate().toString();

            }

            @Override
            public String getSectionHeader(int position) {
                return people.get(position)
                        .getCreatedDate().toString();

//                        .subSequence(0,
//                                23);
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            if (ListMessageAdapter.mediaPlayer != null) {
                ListMessageAdapter.mediaPlayer.stop();
                ListMessageAdapter.mediaPlayer.reset();
            }


            mFirebaseDatabaseReference.child("Chats").child(MESSAGES_CHILD).removeEventListener(seenListener);
            Intent result = new Intent();
            result.putExtra("idFriend", receiverID);
            setResult(RESULT_OK, result);
            this.finish();
//            if (isNetworkStatusAvialable(context)) {
//
//                updateStatus("offline");
//            }


        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ListMessageAdapter.mediaPlayer != null) {
            ListMessageAdapter.mediaPlayer.stop();
            ListMessageAdapter.mediaPlayer.reset();
        }

        mFirebaseDatabaseReference.child("Chats").child(MESSAGES_CHILD).removeEventListener(seenListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        AppConstants.Chatactive = false;
        if (ListMessageAdapter.mediaPlayer != null) {
            ListMessageAdapter.mediaPlayer.stop();
            ListMessageAdapter.mediaPlayer.reset();
        }
        /*if (isNetworkStatusAvialable(context)) {
            Log.d(TAG, "onStop: "+"status");
            updateStatus("offline");
        }*/

        mFirebaseDatabaseReference.child("Chats").child(MESSAGES_CHILD).removeEventListener(seenListener);

    }

    protected void onPause() {
        super.onPause();

        if (ListMessageAdapter.mediaPlayer != null) {
            ListMessageAdapter.mediaPlayer.stop();
            ListMessageAdapter.mediaPlayer.reset();
        }
        if (isNetworkStatusAvialable(context)) {
            Log.d(TAG, "onStop: " + "status");
            updateStatus("offline");
        }
        mFirebaseDatabaseReference.child("Chats").child(MESSAGES_CHILD).removeEventListener(seenListener);

    }

    @Override
    public void onResume() {

        super.onResume();
        if (isNetworkStatusAvialable(context)) {
//            seenMessage();
            updateStatus("online");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppConstants.Chatactive = true;
//        if (isNetworkStatusAvialable(context)) {
//
//            updateStatus("online");
//        }
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.btn_send:
                if (!editWriteMessage.getText().toString().isEmpty()) {
                    insertIntoChats("", "", "Text", editWriteMessage.getText().toString());
                }
                break;

            case R.id.iv_attachment:
                revealMenu();
                break;

        }
    }

    private String insertIntoChats(String filePath, String fileName, String messageType, String msgText) {

        editWriteMessage.setText("");

        msgType = "1";

        String newId = mFirebaseDatabaseReference.push().getKey();
        try {
            ChatDetails chatDetails = new ChatDetails();
            chatDetails.setReceiverID(receiverID);
            chatDetails.setReceiverMobile(receiverMobileNo);
            chatDetails.setSenderName(senderName);
            chatDetails.setCreatedDate(ServerValue.TIMESTAMP);
            chatDetails.setMsgSeen(false);
            chatDetails.setText(msgText);
            chatDetails.setISTestRimage(msgType);
            chatDetails.setSenderID(senderID);
            chatDetails.setReceiverName(receiverName);
            chatDetails.setUserType(USERTYPE);
            chatDetails.setSenderMobile(senderMobileNo);
            chatDetails.setMessageID(newId);
            chatDetails.setFilePath(filePath);
            chatDetails.setFileName(fileName);
            chatDetails.setMsgType(messageType);
            mFirebaseDatabaseReference.child("Chats").child(MESSAGES_CHILD).child(newId).setValue(chatDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    insertIntoUserChatList(chatDetails);

                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "insertIntoChats", e);
        }
        return newId;

    }

    private void insertIntoUserChatList(ChatDetails chatDetails) {
        try {
            UserChatList userChatListSend = new UserChatList();

            userChatListSend.setCreatedDate(chatDetails.getCreatedDate());
            userChatListSend.setLastMessageID(chatDetails.getMessageID());
            userChatListSend.setMessageType("Send");
            userChatListSend.setName(chatDetails.getReceiverName());
            userChatListSend.setReceiverID(chatDetails.getReceiverID());
            userChatListSend.setReceiverMobile(chatDetails.getReceiverMobile());
            userChatListSend.setImage(receiverImage);
            userChatListSend.setSenderID(chatDetails.getSenderID());
            userChatListSend.setSenderMobile(chatDetails.getSenderMobile());
            userChatListSend.setText(chatDetails.getText());
            userChatListSend.setUserType(chatDetails.getUserType());
            userChatListSend.setFilePath(chatDetails.getFilePath());
            userChatListSend.setFileName(chatDetails.getFileName());
            userChatListSend.setMsgType(chatDetails.getMsgType());
            userChatListSend.setUserID(receiverUserID);
            userChatListSend.setUnreadcount(0);

            mFirebaseDatabaseReference.child("UserChatList").child(senderID).child(MESSAGES_CHILD).setValue(userChatListSend);


            mFirebaseDatabaseReference.child("UserChatList").child(receiverID).child(MESSAGES_CHILD).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        UserChatList userChatList = dataSnapshot.getValue(UserChatList.class);
                        long count = userChatList.getUnreadcount();

                        UserChatList userChatListReceive = new UserChatList();

                        userChatListReceive.setCreatedDate(chatDetails.getCreatedDate());
                        userChatListReceive.setLastMessageID(chatDetails.getMessageID());
                        userChatListReceive.setMessageType("Receive");
                        userChatListReceive.setName(chatDetails.getSenderName());
                        userChatListReceive.setImage(senderImage);
                        userChatListReceive.setReceiverID(chatDetails.getReceiverID());
                        userChatListReceive.setReceiverMobile(chatDetails.getReceiverMobile());
                        userChatListReceive.setSenderID(chatDetails.getSenderID());
                        userChatListReceive.setSenderMobile(chatDetails.getSenderMobile());
                        userChatListReceive.setText(chatDetails.getText());
                        userChatListReceive.setUserType(chatDetails.getUserType());
                        userChatListReceive.setFilePath(chatDetails.getFilePath());
                        userChatListReceive.setFileName(chatDetails.getFileName());
                        userChatListReceive.setMsgType(chatDetails.getMsgType());
                        userChatListReceive.setUserID(sessionManager.getUserDataFromSession().getUserID());
                        userChatListReceive.setUnreadcount(++count);
                        mFirebaseDatabaseReference.child("UserChatList").child(receiverID).child(MESSAGES_CHILD).setValue(userChatListReceive);

//                    }
                    } else {
                        UserChatList userChatListReceive = new UserChatList();

                        userChatListReceive.setCreatedDate(chatDetails.getCreatedDate());
                        userChatListReceive.setLastMessageID(chatDetails.getMessageID());
                        userChatListReceive.setMessageType("Receive");
                        userChatListReceive.setName(chatDetails.getSenderName());
                        userChatListReceive.setImage(senderImage);
                        userChatListReceive.setReceiverID(chatDetails.getReceiverID());
                        userChatListReceive.setReceiverMobile(chatDetails.getReceiverMobile());
                        userChatListReceive.setSenderID(chatDetails.getSenderID());
                        userChatListReceive.setSenderMobile(chatDetails.getSenderMobile());
                        userChatListReceive.setText(chatDetails.getText());
                        userChatListReceive.setUserType(chatDetails.getUserType());
                        userChatListReceive.setFilePath(chatDetails.getFilePath());
                        userChatListReceive.setFileName(chatDetails.getFileName());
                        userChatListReceive.setMsgType(chatDetails.getMsgType());
                        userChatListReceive.setUserID(sessionManager.getUserDataFromSession().getUserID());
                        userChatListReceive.setUnreadcount(1);
                        mFirebaseDatabaseReference.child("UserChatList").child(receiverID).child(MESSAGES_CHILD).setValue(userChatListReceive);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            if (chatDetails.getMsgType().equalsIgnoreCase("Text") || (chatDetails.getMsgType().equalsIgnoreCase("Apps"))) {
                sendNotification(receiverID, chatDetails.getSenderName(), chatDetails.getText());
            } else {
                sendNotification(receiverID, chatDetails.getSenderName(), chatDetails.getFileName());
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "insertIntoUserChatList", e);
        }
    }

    public void openDocuments(View view) {
        try {
            hideMenu();

            startActivityForResult(new Intent(context, FilePicker.class), FILE_BROWSER_RESULT_CODE);
//        Toast.makeText(this, view.getTag() + " Clicked", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "openDocuments", e);
        }
    }

    public void openAudio(View view) {
        try {
            hideMenu();


            Intent intent;
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("audio/*");
            startActivityForResult(Intent.createChooser(intent, ""), REQUEST_AUDIO_CONTROL_CODE);

//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
//
//        startActivityForResult(galleryIntent, REQUEST_AUDIO_CONTROL_CODE);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "openAudio", e);
        }

    }

    public void openVideos(View view) {
        try {
            hideMenu();
//
            Intent intent;
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("video/*");
            startActivityForResult(Intent.createChooser(intent, ""), REQUEST_VIDEO_CONTROL_CODE);

//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//
//        startActivityForResult(galleryIntent, REQUEST_VIDEO_CONTROL_CODE);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "openVideos", e);
        }

    }

    public void openCamera(View view) {
        try {
            hideMenu();

//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(photoCaptureIntent, REQUEST_CAMERA_CONTROL_CODE);

            dispatchTakePictureIntent();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "openCamera", e);
        }

    }

    public void openGallery(View view) {
        try {
            hideMenu();
            Intent intentG = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentG, REQUEST_GALLERY_CONTROL_CODE);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "openGallery", e);
        }
    }

    public void revealMenu() {
        try {
            menuOpen = true;

            theMenu.setVisibility(View.INVISIBLE);
            int cx = theMenu.getRight() - 200;
            int cy = theMenu.getTop();
            int finalRadius = Math.max(theMenu.getWidth(), theMenu.getHeight());
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(theMenu, cx, cy, 0, finalRadius);
            anim.setDuration(600);
            theMenu.setVisibility(View.VISIBLE);
            overlay.setVisibility(View.VISIBLE);
            anim.start();


            // Animate The Icons One after the other, I really would like to know if there is any
            // simpler way to do it
            Animation popeup1 = AnimationUtils.loadAnimation(this, R.anim.popup);
            Animation popeup2 = AnimationUtils.loadAnimation(this, R.anim.popup);
            Animation popeup3 = AnimationUtils.loadAnimation(this, R.anim.popup);
            Animation popeup4 = AnimationUtils.loadAnimation(this, R.anim.popup);
            Animation popeup5 = AnimationUtils.loadAnimation(this, R.anim.popup);
            Animation popeup6 = AnimationUtils.loadAnimation(this, R.anim.popup);
            popeup1.setStartOffset(50);
            popeup2.setStartOffset(100);
            popeup3.setStartOffset(150);
            popeup4.setStartOffset(200);
            popeup5.setStartOffset(250);
            popeup6.setStartOffset(300);
            menu_document.startAnimation(popeup1);
            menu_camera.startAnimation(popeup2);
            menu_gallery.startAnimation(popeup3);
            menu_audio.startAnimation(popeup4);
            menu_video.startAnimation(popeup5);
            menu_apps.startAnimation(popeup6);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "revealMenu", e);
        }
    }

    public void hideMenu() {
        try {
            menuOpen = false;
            int cx = theMenu.getRight() - 200;
            int cy = theMenu.getTop();
            int initialRadius = theMenu.getWidth();
            Animator anim = ViewAnimationUtils.createCircularReveal(theMenu, cx, cy, initialRadius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    theMenu.setVisibility(View.INVISIBLE);
                    theMenu.setVisibility(View.GONE);
                    overlay.setVisibility(View.INVISIBLE);
                    overlay.setVisibility(View.GONE);
                }
            });
            anim.start();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "hideMenu", e);
        }
    }

    public void overlayClick(View v) {
        try {
            hideMenu();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "overlayClick", e);
        }
    }

    public void openApps(View view) {
        try {
            Intent intent = new Intent(context, ApplicationTypeActivity.class);
            intent.putExtra("type", "I");
            intent.putExtra("id", receiverUserID);
            intent.putExtra("name", receiverName);
            startActivityForResult(intent, REQUEST_APP_DISTRIBUTION_CODE);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "openApps", e);
        }

    }

    private void sendNotification(String receiver, final String username, final String msg) {
        try {
//        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
            Query query = mFirebaseDatabaseReference.child("Tokens").orderByKey().equalTo(receiver);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Token token = snapshot.getValue(Token.class);
                        Data data = new Data(senderID, R.mipmap.ic_launcher, msg, username, receiverID, "I");

                        Sender sender = new Sender(data, token.getToken());
                        apiService.sendNotification(sender)
                                .enqueue(new Callback<MyResponse>() {
                                    @Override
                                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                        if (response.code() == 200) {
                                            if (response.body().success != 1) {
//                                            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MyResponse> call, Throwable t) {
                                        Log.d(TAG, "onCancelled: " + "Failed");
                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Log.d(TAG, "onCancelled: " + databaseError.toString());
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "sendNotification", e);
        }

    }

//    public String getRealPathFromURI(Uri uri) {
//        String path = "";
//        if (getContentResolver() != null) {
//            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//            if (cursor != null) {
//                cursor.moveToFirst();
//                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//                path = cursor.getString(idx);
//                cursor.close();
//            }
//        }
//        return path;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_BROWSER_RESULT_CODE:
                if (resultCode == RESULT_OK) {

                    File selectedFile = new File(data.getStringExtra(FilePicker.EXTRA_FILE_PATH));

                    Log.d(TAG, "onActivityResult: " + selectedFile.getPath());
                    String filePath = selectedFile.getPath();
                    String[] imgUrlSplit = filePath.split("/");
                    String strSDCardUrl = imgUrlSplit[imgUrlSplit.length - 1];
                    String[] fileNameTemp = strSDCardUrl.replaceAll(" ", "_").split("\\.");

                    uploadFile(filePath, fileNameTemp[1]);
                }
                break;
            case REQUEST_GALLERY_CONTROL_CODE:
                if (resultCode == RESULT_OK) {

                    Uri selectedImage = data.getData();
                    String strGalleryPath = getUriRealPath(context, selectedImage);

                    Log.d(TAG, "onActivityResult: " + strGalleryPath);

                    uploadFile(strGalleryPath, "Image");

                }
                break;

            case REQUEST_CAMERA_CONTROL_CODE:

                if (resultCode == RESULT_OK) {

//                    Bundle extras = data.getExtras();
//                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    File imgFile = new File(currentPhotoPath);
                    if (imgFile.exists()) {
                        imageBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    }

                    Uri tempUri = getImageUri(context, imageBitmap);
                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    String imagePath = getUriRealPath(context, tempUri);

                    Log.d(TAG, "onActivityResult: " + imagePath);

                    uploadFile(imagePath, "Image");

                }

                break;

            case REQUEST_AUDIO_CONTROL_CODE:
                if ((data != null) && (data.getData() != null)) {
                    Uri audioFileUri = data.getData();
//                    String audioPath =    getRealPathFromURIForAudio(audioFileUri);
//                    String audioPath = audioFileUri.getPath();
//                    Log.d(TAG, "onActivityResultOnlyAudio: " + audioPath);

                    String audioPath = getUriRealPath(context, audioFileUri);

                    uploadFile(audioPath, "Audio");
                }

                break;

            case REQUEST_VIDEO_CONTROL_CODE:
                if ((data != null) && (data.getData() != null)) {
                    Uri videoFileUri = data.getData();
//                    String videoPath = videoFileUri.getPath().replace("/raw/","");

//                    Log.d(TAG, "onActivityResultOnlVideo: " + videoPath);

                    String videoPath = getUriRealPath(context, videoFileUri);

                    uploadFile(videoPath, "Video");


                }
                break;
            case REQUEST_APP_DISTRIBUTION_CODE:

                overlayClick(view);
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    //WHAT TO DO TO GET THE BUNDLE VALUES//
                    String appName = bundle.getString("AppName");
                    String strAppType = bundle.getString("AppType");
//                    String msgText = appName + "\n" + strAppType;
                    String msgText = appName;
                    insertIntoChats("Apps", msgText, "Apps", msgText);
                }
                break;

        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        String path = null;
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Image" + getDateandTimeForImage(), null);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getImageUri", e);
        }
        return Uri.parse(path);
    }

    private String getmimeType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    private void sendFileDataToServer(String file, String fileType, String title) {

        String fileString = getBase64FromPath(file);
        Call<JSONObject> call = getServices.sendFileasString("title", ".mp4", fileString);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {

                if (response != null && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body());
//                    FormDataResponse formDataResponse = response.body();
//                    if (formDataResponse.getStatus().equalsIgnoreCase("200") && formDataResponse.getMessage().equalsIgnoreCase("Success")) {
//                        Toast.makeText(context, formDataResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(context, formDataResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

                Toast.makeText(context, getString(R.string.places_try_again), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void uploadFile(String filePath, String msgType) {

        try {
//        showProgressDialog(getString(R.string.please_wait));

            String[] imgUrlSplit = filePath.split("/");
            String strSDCardUrl = imgUrlSplit[imgUrlSplit.length - 1];
            String fileName = strSDCardUrl.replaceAll(" ", "_");

            String msgId = insertIntoChats("", fileName, msgType, "");

            new FileUploaderCommunication(this, fileName, "", "", false, "BHARGO", new FileUploaderCommunication.OnImageUploaded() {
                @Override
                public void response(String url) {


                    String fileurl = url;

                    Log.d(TAG, "response: " + fileurl);
//                dismissProgressDialog();
                    downloadSentFile(url, fileName);

                    updateFilePathInFirebase(msgId, fileurl);

                }
            }).execute(filePath);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "uploadFile", e);
        }
    }

    public void downloadSentFile(String filePath, String fileName) {
        try {
            if (!filePath.equalsIgnoreCase("File not found")) {
                File projDir = new File(context.getExternalFilesDir(null), "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats/Sent");
                if (!projDir.exists())
                    projDir.mkdirs();
                String strSDCardUrl = null;
                strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats/Sent/" + fileName;


                DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                Uri Download_Uri = Uri.parse(filePath);
                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
                //Restrict the types of networks over which this download may proceed.
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                //Set whether this download may proceed over a roaming connection.
                request.setAllowedOverRoaming(false);
                //Set the title of this download, to be displayed in notifications (if enabled).
                request.setTitle(context.getString(R.string.app_name));
                //Set a description of this download, to be displayed in notifications (if enabled)
                request.setDescription(fileName + " is downloading");
                //Set the local destination for the downloaded file to a path within the application's external files directory
                request.setDestinationInExternalFilesDir(context, null, strSDCardUrl);

                //Enqueue a new download and same the referenceId
                downloadManager.enqueue(request);

            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "downloadSentFile", e);
        }
    }

    private void updateFilePathInFirebase(String msgId, String fileurl) {

        try {
            HashMap<String, Object> statusMap = new HashMap<>();

            statusMap.put("filePath", fileurl);

            mFirebaseDatabaseReference.child("Chats").child(MESSAGES_CHILD).child(msgId).updateChildren(statusMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    mFirebaseDatabaseReference.child("UserChatList").child(senderID).child(MESSAGES_CHILD).updateChildren(statusMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFirebaseDatabaseReference.child("UserChatList").child(receiverID).child(MESSAGES_CHILD).updateChildren(statusMap);
                        }
                    });
                }
            });

        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "updateFilePathInFirebase", e);
        }
    }

    private void seenMessage() {
        try {
            seenListener = mFirebaseDatabaseReference.child("Chats").child(MESSAGES_CHILD).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.exists()) {
                            ChatDetails chatDetails = snapshot.getValue(ChatDetails.class);
                            if (chatDetails.getReceiverID().equalsIgnoreCase(senderID)) {
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("IsMsgSeen", true);
                                snapshot.getRef().updateChildren(hashMap);
                            }
                        }

                    }

                    HashMap<String, Object> statusMap = new HashMap<>();

                    statusMap.put("unreadcount", 0);

                    mFirebaseDatabaseReference.child("UserChatList").child(sessionManager.getUserChatID()).child(MESSAGES_CHILD).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                mFirebaseDatabaseReference.child("UserChatList").child(sessionManager.getUserChatID()).child(MESSAGES_CHILD).updateChildren(statusMap);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "seenMessage", e);
        }
    }


    @Override
    public void updateTypingStatus(String appStatus) {
        Log.d(TAG, "updateTypingStatus: " + appStatus);

        typingStarted = false;

        updateTypingStatus();

    }

    private boolean isFileExists(String filename) {
        File file = null;
        try {
            String[] imgUrlSplit = filename.split("/");
            String strUrl = imgUrlSplit[imgUrlSplit.length - 1];
            String fileName = strUrl.replaceAll(" ", "_");

            String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + fileName;
            file = new File(context.getExternalFilesDir(null), strSDCardUrl);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "isFileExists", e);
        }
        return file.exists();
    }

    public void setImageFromSDCard(String strImagePath, ImageView imageView) {
        try {
            String[] imgUrlSplit = strImagePath.split("/");
            String strUrl = imgUrlSplit[imgUrlSplit.length - 1];
            String fileName = strUrl.replaceAll(" ", "_");

            String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + fileName;
            File imgFile = new File(context.getExternalFilesDir(null), strSDCardUrl);
            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                imageView.setImageBitmap(myBitmap);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setImageFromSDCard", e);
        }
    }

    private File createImageFile() throws IOException {
        File image = null;
        try {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = image.getAbsolutePath();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "createImageFile", e);
        }

        return image;
    }

    private void dispatchTakePictureIntent() {
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File

                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.bhargo.user.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_CAMERA_CONTROL_CODE);
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "dispatchTakePictureIntent", e);
        }
    }

    @Override
    public void onBackPressed() {
        mFirebaseDatabaseReference.child("Chats").child(MESSAGES_CHILD).removeEventListener(seenListener);

        if (ListMessageAdapter.mediaPlayer != null) {
            ListMessageAdapter.mediaPlayer.stop();
            ListMessageAdapter.mediaPlayer.reset();
        }

        if (menuOpen) {
            hideMenu();
        } else {
            finishAfterTransition();
        }


        Intent result = new Intent();
        result.putExtra("idFriend", receiverID);
        setResult(RESULT_OK, result);
        this.finish();
    }

    private class DownloadFileFromURLTask extends AsyncTask<String, String, String> {


        private final ImageView imageView;
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
//                Log.d(TAG, "doInBackgroundName: " + strFileName);
                file = new File(context.getExternalFilesDir(null), "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats");
                if (!file.exists())
                    file.mkdirs();
                File outFile = new File(file, strFileName);
                // Output stream to write file
                OutputStream output = new FileOutputStream(outFile);
//                new File(file).delete();
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

            if (file.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);

            }
        }
    }

}


