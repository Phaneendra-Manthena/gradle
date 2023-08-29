package com.bhargo.user.screens.sesssionchat;

import static android.content.Intent.ACTION_PICK;
import static android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
import static android.provider.MediaStore.Video.Media.INTERNAL_CONTENT_URI;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.firebase.ChatDetails;
import com.bhargo.user.pojos.firebase.GroupsListResponse;
import com.bhargo.user.screens.ApplicationTypeActivity;
import com.bhargo.user.screens.BottomNavigationActivity;
import com.bhargo.user.screens.onetoonechat.ListMessageAdapter;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.FilePathUtils;
import com.bhargo.user.utils.FileUploaderCommunication;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import nk.bluefrog.library.camera.CameraActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionChatActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQUEST_APP_DISTRIBUTION_CODE = 666;
    public static final int REQUEST_GALLERY_CONTROL_CODE = 222;
    public static final int REQUEST_CAMERA_CONTROL_CODE = 333;
    public static final int REQUEST_AUDIO_CONTROL_CODE = 444;
    public static final int REQUEST_VIDEO_CONTROL_CODE = 555;
    public static final int VIEW_TYPE_USER_MESSAGE = 0;
    public static final int VIEW_TYPE_FRIEND_MESSAGE = 1;
    private static final String TAG = "SessionChatActivity";
    private static final int FILE_BROWSER_RESULT_CODE = 111;
    //Topic message upstream
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAv3GoZ8M:APA91bEON6K2-GhJWBjWDXvljKFYBMpeWSBaT2CICmvPXgVitXK0EzjNlOlQrvZ8ZCVEPZUawiCmz0QuWDug_CH8Ew82Ohj57ncL1LuGoO-O4hrOsFCoziQ4pbrbQN90KcbRZCP9mZEI";
    final private String contentType = "application/json";
    Context context;
    SessionManager sessionManager;
    ImproveDataBase improveDataBase;
    ImproveHelper improveHelper;
    List<ChatDetails> chatDetailsList;
    String groupName = null;
    String sessionIcon = null;
    String groupId = null;
    String sessionId = null;
    String sessionName = null;
    String userId = null;
    String postId = null;
    String userTypeId = null;
    String orgId = null;
    Boolean write = null;
    Boolean from = null;
    CardView layout_writemsg;
    LinearLayout layout_send;
    LocalBroadcastManager bm;
    String currentPhotoPath;
    GetServices getServices;
    String strFromNotification = "";
    private ImageButton btnSend;
    private EditText editWriteMessage;
    private RecyclerView recyclerChat;
    private SessionMessagesAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private final BroadcastReceiver onJsonReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
if(intent.getStringExtra("session_id").equalsIgnoreCase(sessionId)){
    showNewMessage();
            }}
        }
    };
    private ImageView iv_attachment;
    private View theMenu;
    private View overlay;
    private View menu_document;
    private View menu_camera;
    private View menu_gallery;
    private View menu_audio;
    private View menu_video;
    private View menu_apps;
    private boolean menuOpen = false;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppConstants.THEME.equalsIgnoreCase("THEME2")) {
            setTheme(R.style.AppTheme2);
        }else if (AppConstants.THEME.equalsIgnoreCase("THEME1")) {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_chat);

        context = this;
        sessionManager = new SessionManager(context);
        improveDataBase = new ImproveDataBase(context);
        improveHelper = new ImproveHelper(context);

        try {
            Intent intentData = getIntent();
            if (intentData != null) {
                groupName = intentData.getStringExtra("GroupName");
                groupId = intentData.getStringExtra("GroupId");
                sessionIcon = intentData.getStringExtra("SessionIcon");
                sessionName = intentData.getStringExtra("SessionName");
                sessionId = intentData.getStringExtra("SessionID");
                write = intentData.getBooleanExtra("Write", false);
                from = intentData.getBooleanExtra("From", false);

                strFromNotification = intentData.getStringExtra(AppConstants.SESSIONCHAT_NOTIFICATION);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "onCreate", e);
        }
        if (!sessionManager.isLoggedIn()) {
            System.out.println("sessionManager.isLoggedIn()=="+sessionManager.isLoggedIn());
            sessionManager.logoutUser();
        }

        getServices = RetrofitUtils.getUserService();

        userId = sessionManager.getUserDataFromSession().getUserID();
        postId = sessionManager.getPostsFromSession();
        userTypeId = sessionManager.getUserTypeIdsFromSession();
        orgId = sessionManager.getOrgIdFromSession();

        initializeActionBar();
        enableBackNavigation(true);
        title.setText(sessionName);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ImproveHelper.isNetworkStatusAvialable(context)) {
                    Intent intent = new Intent(context, GroupInfoActivity.class);

                    intent.putExtra("GroupName", groupName);
                    intent.putExtra("GroupId", groupId);
                    intent.putExtra("SessionID", sessionId);
                    intent.putExtra("SessionIcon", sessionIcon);
                    intent.putExtra("SessionName", sessionName);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, getString(R.string.pls_check_network), Toast.LENGTH_SHORT).show();
                }

            }
        });
        subTitle.setText(groupName);
        iv_circle_appIcon.setVisibility(View.VISIBLE);
        if (sessionIcon == null) {
            sessionIcon = "default";
        }
        if (sessionIcon.equals("default")) {
            iv_circle_appIcon.setImageResource(R.drawable.user);

        } else {
            if (isFileExists(sessionIcon)) {
                setImageFromSDCard(sessionIcon, iv_circle_appIcon);
            } else {

                DownloadFileFromURLTask fileFromURLTask = new DownloadFileFromURLTask(iv_circle_appIcon);
                fileFromURLTask.execute(sessionIcon);
            }
        }
        ib_settings.setVisibility(View.GONE);

        //Clear all Notifications
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).
                cancelAll();

        initViews();

    }

    private void initViews() {
        try {
            linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerChat = (RecyclerView) findViewById(R.id.recycler_view);
            recyclerChat.setLayoutManager(linearLayoutManager);
            chatDetailsList = new ArrayList<>();
            adapter = new SessionMessagesAdapter(this, chatDetailsList);
            recyclerChat.setAdapter(adapter);

            layout_writemsg = findViewById(R.id.rl_chat);
            layout_send = findViewById(R.id.rl_send);

            //write msg
            if (write) {
                showWriteMessage();
            } else {
                hideWriteMessage();
            }

            theMenu = findViewById(R.id.the_menu);
            overlay = findViewById(R.id.overlay);

            menu_document = findViewById(R.id.menu_document);
            menu_camera = findViewById(R.id.menu_camera);
            menu_gallery = findViewById(R.id.menu_gallery);
            menu_audio = findViewById(R.id.menu_audio);
            menu_video = findViewById(R.id.menu_video);
            menu_apps = findViewById(R.id.menu_apps);

            editWriteMessage = (EditText) findViewById(R.id.text_send);
            iv_attachment = findViewById(R.id.iv_attachment);
            btnSend = (ImageButton) findViewById(R.id.btn_send);

            btnSend.setOnClickListener(this);
            iv_attachment.setOnClickListener(this);

            loadMessageList();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "initViews", e);
        }

    }

    private void hideWriteMessage() {
        try {
            layout_writemsg.setVisibility(View.GONE);
            layout_send.setVisibility(View.GONE);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "hideWriteMessage", e);
        }
    }

    private void showWriteMessage() {
        try {
            layout_writemsg.setVisibility(View.VISIBLE);
            layout_send.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "showWriteMessage", e);
        }
    }

    private void loadMessageList() {
        try {
            chatDetailsList = improveDataBase.getSessionChatMessagesList(groupName, sessionId, userId, postId);
            if (chatDetailsList.size() > 0) {
                improveDataBase.updateUnreadCount(groupName, sessionId, userId, postId);
                recyclerChat.setVisibility(View.VISIBLE);
                adapter.setData(chatDetailsList);
                linearLayoutManager.scrollToPosition(chatDetailsList.size() - 1);
            } else if (from && write) {
                String firstMessage = "This Session is created by  " + sessionManager.getUserDataFromSession().getUserName();
                prepareTopicMessage("", "", firstMessage, "System");
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "loadMessageList", e);
        }
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
            improveHelper.improveException(this, TAG, "setImageFromSDCard", e);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_send:

                if (!editWriteMessage.getText().toString().isEmpty()) {
//                    sendTopicMessage("", "", editWriteMessage.getText().toString(), "Text");
                    prepareTopicMessage("", "", editWriteMessage.getText().toString(), "Text");
                }
                break;
            case R.id.iv_attachment:
                revealMenu();
                break;

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
            improveHelper.improveException(this, TAG, "revealMenu", e);
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
            improveHelper.improveException(this, TAG, "hideMenu", e);
        }

    }

    public void overlayClick(View v) {
        try {
            hideMenu();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "overlayClick", e);
        }
    }

    public void openDocuments(View view) {
        try {
            hideMenu();
//            startActivityForResult(new Intent(context, FilePicker.class), FILE_BROWSER_RESULT_CODE);
            showFileChooser();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "openDocuments", e);
        }
    }


    public void showFileChooser() {


        Intent intent;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            intent = new Intent(ACTION_PICK, EXTERNAL_CONTENT_URI);
        } else {
            intent = new Intent(ACTION_PICK, INTERNAL_CONTENT_URI);
        }

        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra("return-data", true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


        //Intent intent = new Intent();
        // intent.setType("*/*");
        // intent.setType("application/pdf");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose a file"), FILE_BROWSER_RESULT_CODE);


    }

    public void openAudio(View view) {
        try {
            hideMenu();
            Intent intent;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                intent = new Intent(ACTION_PICK, EXTERNAL_CONTENT_URI);
            } else {
                intent = new Intent(ACTION_PICK, INTERNAL_CONTENT_URI);
            }

            intent.setType("audio/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra("return-data", true);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivityForResult(Intent.createChooser(intent, "Choose a file"), REQUEST_AUDIO_CONTROL_CODE);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "openAudio", e);
        }
       /* try {
            hideMenu();

            Intent intent;
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("audio/*");
            startActivityForResult(Intent.createChooser(intent, ""), REQUEST_AUDIO_CONTROL_CODE);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "openAudio", e);
        }*/

    }


    public void openVideos(View view) {
        hideMenu();
        Intent intent;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            intent = new Intent(ACTION_PICK, EXTERNAL_CONTENT_URI);
        } else {
            intent = new Intent(ACTION_PICK, INTERNAL_CONTENT_URI);
        }

        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra("return-data", true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivityForResult(Intent.createChooser(intent, "Choose a file"), REQUEST_VIDEO_CONTROL_CODE);

        /*Intent intent;
        intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(Intent.createChooser(intent, ""), REQUEST_VIDEO_CONTROL_CODE);
*/
    }

 /*  private void sendNotificationTopic(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                        Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }*/

    public void openCamera(View view) {
        try {
            hideMenu();
//            dispatchTakePictureIntent();
            mOpenCamera(REQUEST_CAMERA_CONTROL_CODE);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "openCamera", e);
        }
    }
    public void mOpenCamera(int cameraRequestCodeMain) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intent = new Intent(context, CameraActivity.class);
        intent.putExtra(CameraActivity.SAVE_LOCATION, sessionManager.getOrgIdFromSession() + " IMAGES");
        startActivityForResult(intent, cameraRequestCodeMain);


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
            improveHelper.improveException(this, TAG, "dispatchTakePictureIntent", e);
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        File image = null;
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
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
            improveHelper.improveException(this, TAG, "createImageFile", e);
        }

        return image;
    }

    public void openGallery(View view) {
        try {
            hideMenu();

            Intent intentG = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentG, REQUEST_GALLERY_CONTROL_CODE);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "openGallery", e);
        }
    }

    public void openApps(View view) {
        try {
            Intent intent = new Intent(context, ApplicationTypeActivity.class);
            intent.putExtra("type", "I");
            intent.putExtra("id", groupId);
            intent.putExtra("name", groupName);

            startActivityForResult(intent, REQUEST_APP_DISTRIBUTION_CODE);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "openApps", e);
        }
    }

  /*  private void sendTopicMessage(String fileName, String filePath, String message, String messageType) {
        editWriteMessage.setText("");
        String TOPIC = "/topics/14"; //topic must match with what the receiver subscribed to
        String messageId = "";

        String senderId = sessionManager.getUserDataFromSession().getUserID();
        String senderName = sessionManager.getUserDetailsFromSession().getName();
        String timestamp = String.valueOf(getTimeFromDeviceInMilliSc());

        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();

        try {
            notifcationBody.put("UserId", senderId);
            notifcationBody.put("messageId", messageId);
            notifcationBody.put("senderId", senderId);
            notifcationBody.put("senderName", senderName);
            notifcationBody.put("groupId", groupId);
            notifcationBody.put("groupIcon", sessionIcon);
            notifcationBody.put("groupName", groupName);
            notifcationBody.put("sessionId", sessionId);
            notifcationBody.put("sessionName", sessionName);
            notifcationBody.put("title", sessionName);
            notifcationBody.put("message", message);
            notifcationBody.put("messageType", messageType);
            notifcationBody.put("fileName", fileName);
            notifcationBody.put("filePath", filePath);
            notifcationBody.put("timestamp", timestamp);


            notification.put("to", TOPIC);
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }

        //insert sender
        String status = "Sent";
        String unreadCount = "0";
        improveDataBase.insertIntoNotificationTable(messageId, senderId, senderName, groupId, sessionIcon, groupName, sessionId, sessionName, sessionName, message, messageType, fileName, filePath, timestamp, status, unreadCount, "", "","");
//        AddNewMessage();

//        sendNotificationTopic(notification);
    }*/

    private boolean isFileExists(String filename) {
        boolean isFileExists = false;
        try {
            String[] imgUrlSplit = filename.split("/");
            String strUrl = imgUrlSplit[imgUrlSplit.length - 1];
            String fileName = strUrl.replaceAll(" ", "_");

            String strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + fileName;
            File file = new File(context.getExternalFilesDir(null), strSDCardUrl);
            isFileExists = file.exists();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "isFileExists", e);
        }
        return isFileExists;
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppConstants.SessionChatactive = true;
        bm = LocalBroadcastManager.getInstance(context);
        IntentFilter actionReceiver = new IntentFilter();
        actionReceiver.addAction("sessionChatRefresh");
        bm.registerReceiver(onJsonReceived, actionReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bm.unregisterReceiver(onJsonReceived);
        AppConstants.SessionChatactive = false;
        if (ListMessageAdapter.mediaPlayer != null) {
            ListMessageAdapter.mediaPlayer.stop();
            ListMessageAdapter.mediaPlayer.reset();
        }

    }

    private void showNewMessage() {
        try {
            List<ChatDetails> newNotification = improveDataBase.getNewMessage(groupId, sessionId, userId, postId,userTypeId);
            if (newNotification.size() > 0) {
                recyclerChat.setVisibility(View.VISIBLE);
                adapter.updateData(newNotification);
                linearLayoutManager.scrollToPosition(chatDetailsList.size() - 1);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "AddNewMessage", e);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_BROWSER_RESULT_CODE:
                /*if (resultCode == RESULT_OK) {

                    File selectedFile = new File(data.getStringExtra(FilePicker.EXTRA_FILE_PATH));

                    Log.d(TAG, "onActivityResult: " + selectedFile.getPath());
                    String filePath = selectedFile.getPath();
                    String[] imgUrlSplit = filePath.split("/");
                    String strSDCardUrl = imgUrlSplit[imgUrlSplit.length - 1];
                    String[] fileNameTemp = strSDCardUrl.replaceAll(" ", "_").split("\\.");

                    uploadFile(filePath, fileNameTemp[1]);
                }*/
                if (resultCode == RESULT_OK) {

                    Uri filePathUri = data.getData();
                    FilePathUtils filePathHelper = new FilePathUtils();
                    String filePath = filePathHelper.getFilePathFromURI(filePathUri, this);
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

                /*if (resultCode == RESULT_OK) {

                    File imgFile = new File(currentPhotoPath);
                    if (imgFile.exists()) {
                        imageBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    }

                    Uri tempUri = getImageUri(context, imageBitmap);
                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    String imagePath = getUriRealPath(context, tempUri);

                    Log.d(TAG, "onActivityResult: " + imagePath);

                    uploadFile(imagePath, "Image");

                }*/
                if (resultCode == RESULT_OK) {
                    File photoFile = null;
                    photoFile = new File(data.getStringExtra(CameraActivity.RESULT_IMG_PATH));
                    if (photoFile.exists()) {
                        imageBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    }

                    Uri tempUri = getImageUri(context, imageBitmap);
                    String imagePath = getUriRealPath(context, tempUri);
                    Log.d(TAG, "onActivityResult: " + imagePath);
                    uploadFile(imagePath, "Image");


                } else {
                    Toast.makeText(context, "No image captured", Toast.LENGTH_LONG).show();

                }

                break;

            case REQUEST_AUDIO_CONTROL_CODE:
               /* if ((data != null) && (data.getData() != null)) {
                    Uri audioFileUri = data.getData();

                    String audioPath = getUriRealPath(context, audioFileUri);

                    uploadFile(audioPath, "Audio");
                }*/
                if (resultCode == RESULT_OK) {

                    Uri filePathUri = data.getData();
                    FilePathUtils filePathHelper = new FilePathUtils();
                    String filePath = filePathHelper.getFilePathFromURI(filePathUri, this);
                    String[] audioUrlSplit = filePath.split("/");
                    String strSDCardUrl = audioUrlSplit[audioUrlSplit.length - 1];
                    String[] fileNameTemp = strSDCardUrl.replaceAll(" ", "_").split("\\.");

                    uploadFile(filePath, fileNameTemp[1]);
                }

                break;

            case REQUEST_VIDEO_CONTROL_CODE:
               /* if ((data != null) && (data.getData() != null)) {
                    Uri videoFileUri = data.getData();

                    String videoPath = getUriRealPath(context, videoFileUri);


                    uploadFile(videoPath, "Video");
                }*/
                if (resultCode == RESULT_OK) {

                    Uri filePathUri = data.getData();
                    FilePathUtils filePathHelper = new FilePathUtils();
                    String filePath = filePathHelper.getFilePathFromURI(filePathUri, this);
                    String[] videoUrlSplit = filePath.split("/");
                    String strSDCardUrl = videoUrlSplit[videoUrlSplit.length - 1];
                    String[] fileNameTemp = strSDCardUrl.replaceAll(" ", "_").split("\\.");

                    uploadFile(filePath, fileNameTemp[1]);
                }
                break;
            case REQUEST_APP_DISTRIBUTION_CODE:

                overlayClick(view);
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    //WHAT TO DO TO GET THE BUNDLE VALUES//
                    String appName = bundle.getString("AppName");
                    String strAppType = bundle.getString("AppType");
                    String msgText = appName;
//                    insertIntoChats("Apps", msgText, "Apps", msgText);
                }
                break;
        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        String path = null;
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Image" + getDateandTimeForImage(), null);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getImageUri", e);
        }
        return Uri.parse(path);
    }

    private void uploadFile(String filePath, String msgType) {
        try {
            if (ImproveHelper.isNetworkStatusAvialable(context)) {
                showProgressDialog(getString(R.string.please_wait));
                String[] imgUrlSplit = filePath.split("/");
                String strSDCardUrl = imgUrlSplit[imgUrlSplit.length - 1];
                String fileName = strSDCardUrl.replaceAll(" ", "_");
//            String fileName = strSDCardUrl.replaceAll("\\W", "_");

                new FileUploaderCommunication(this, fileName, "", "", false, "BHARGO", new FileUploaderCommunication.OnImageUploaded() {
                    @Override
                    public void response(String url) {


//                sendTopicMessage(fileName, url, "", msgType);
                        dismissProgressDialog();
                        downloadSentFile(url, fileName);
                        prepareTopicMessage(fileName, url, "", msgType);


                    }
                }).execute(filePath);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "uploadFile", e);
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
            improveHelper.improveException(this, TAG, "downloadSentFile", e);
        }
    }

    private void prepareTopicMessage(String fileName, String filePath, String message, String messageType) {
        try {
            if (ImproveHelper.isNetworkStatusAvialable(context)) {
                editWriteMessage.setText("");

                String senderId = sessionManager.getUserDataFromSession().getUserID();
                String senderName = sessionManager.getUserDataFromSession().getUserName();
                String timestamp = String.valueOf(getTimeFromDeviceInMilliSc());

                Map<String, String> notifcationBody = new HashMap<>();


                try {
                    notifcationBody.put("OrgId", sessionManager.getOrgIdFromSession());
                    notifcationBody.put("UserId", userId);
//            notifcationBody.put("messageId", messageId);
                    notifcationBody.put("senderId", senderId);
                    notifcationBody.put("senderName", senderName);
                    notifcationBody.put("groupId", groupId);
                    notifcationBody.put("groupIcon", sessionIcon);
                    notifcationBody.put("groupName", groupName);
                    notifcationBody.put("sessionId", sessionId);
                    notifcationBody.put("sessionName", sessionName);
                    notifcationBody.put("title", sessionName);
                    notifcationBody.put("message", message);
                    notifcationBody.put("messageType", messageType);
                    notifcationBody.put("fileName", fileName);
                    notifcationBody.put("filePath", filePath);
                    notifcationBody.put("timestamp", timestamp);


                } catch (Exception e) {
                    Log.e(TAG, "onCreate: " + e.getMessage());
                }


                sendTopicMessageToServer(notifcationBody);
//                Gson gson = new Gson();
//                Log.d(TAG, "prepareTopicMessage: "+ gson.toJson(notifcationBody));
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "prepareTopicMessage", e);
        }

    }

    private void sendTopicMessageToServer(Map<String, String> messageObj) {
        try {
            Call<GroupsListResponse> call = getServices.sendTopicMessageToServer(messageObj);
            call.enqueue(new Callback<GroupsListResponse>() {
                @Override
                public void onResponse(Call<GroupsListResponse> call, Response<GroupsListResponse> response) {
                    if (response != null) {
                        try {

                            if (response.body().getStatus().equalsIgnoreCase("200") && response.body().getMessage().equalsIgnoreCase("Success")) {
                                //insert sender
                                String status = "Sent";
                                String unreadCount = "0";
                                long res = improveDataBase.insertIntoNotificationTable(messageObj, userId, postId, status, unreadCount);
                                if (res > 0) {
                                    showNewMessage();
                                }
                            } else {
                                toast(response.body().getMessage(), context);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GroupsListResponse> call, Throwable t) {
                    dismissProgressDialog();
                }
            });

        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "sendTopicMessageToServer", e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                    Intent intent = new Intent(context, BottomNavigationActivity.class);
                    intent.putExtra("FromChatScreen", "FromChatScreen");
                    startActivity(intent);


                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {

            Intent intent = new Intent(context, BottomNavigationActivity.class);
            intent.putExtra("FromChatScreen", "FromChatScreen");
            startActivity(intent);


    }

    @Override
    protected void onPause() {
        super.onPause();
        AppConstants.SessionChatBackground = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppConstants.SessionChatBackground = false;
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


}