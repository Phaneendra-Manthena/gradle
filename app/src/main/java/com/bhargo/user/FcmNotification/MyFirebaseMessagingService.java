/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bhargo.user.FcmNotification;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.notifications.NotificationsActivity;
import com.bhargo.user.screens.BottomNavigationActivity;
import com.bhargo.user.screens.ELearningListActivity;
import com.bhargo.user.screens.sesssionchat.SessionChatActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.SessionManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.bhargo.user.utils.AppConstants.FromNotification;
import static com.bhargo.user.utils.AppConstants.FromNotificationOnlyInTask;
import static com.bhargo.user.utils.AppConstants.Notification_PageName;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "MyFirebaseMsgService";
    private static final int NOTOIFICATION_REQUEST = 1410;
    private static int count = 0;
    SessionManager sessionManager;
    private SharedPreferences sp1;
    private String str_type;
    private String strPageName, strAppType, messageBody, str_EL_DistributionId, title;
    Intent intent = null;
    ImproveDataBase improveDataBase = new ImproveDataBase(this);
    boolean inAppNotification= false;

    /* Called when message is received.

      @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

//        Log.d(TAG, "onMessageReceived: " + "test");
        Map<String, String> data = remoteMessage.getData();
        Log.d(TAG, "onMessageReceivedGD: " + remoteMessage.getData());

        try {
            if (data.size()>0 && data.containsKey("NotificationType")) {
                if (data.containsKey("messageType")) {
                Log.d(TAG, "onMessageReceived6: " + "test");
                JSONObject jsondata = new JSONObject(remoteMessage.getData());
                String unreadCount = "1";
                String messageId = "";
                String senderId = jsondata.getString("senderId");
                String senderName = jsondata.getString("senderName");
                String groupId = jsondata.getString("groupId");
                String groupIcon = jsondata.getString("groupIcon");
                String groupName = jsondata.getString("groupName");
                String sessionId = jsondata.getString("sessionId");
                String sessionName = jsondata.getString("sessionName");
                String title = jsondata.getString("title");
                String message = jsondata.getString("message");
                String messageType = jsondata.getString("messageType");
                String fileName = jsondata.getString("fileName");
                String filePath = jsondata.getString("filePath");
                String timestamp = jsondata.getString("timestamp");
                String userId = ImproveHelper.getUserDetails(getApplicationContext()).getUserID();
                String orgId = PrefManger.getSharedPreferencesString(getApplicationContext(), AppConstants.SP_ORGANISATION_ID, AppConstants.Default_OrgID);
                String status = "Received";
                if (AppConstants.SessionChatactive) {
                    unreadCount = "0";
                }
                List<String> postsList = new ArrayList<>();
                if (!senderId.equalsIgnoreCase(userId)) {
                    postsList = improveDataBase.getPosts(groupId, userId);
                    if (postsList == null || postsList.size() == 0) {
                        improveDataBase.insertIntoNotificationTable(messageId, senderId, senderName, groupId, groupIcon, groupName, sessionId, sessionName, title, message, messageType, fileName, filePath, timestamp, status, unreadCount, userId, null);
                    } else {
                        for (String postId : postsList) {
                            improveDataBase.insertIntoNotificationTable(messageId, senderId, senderName, groupId, groupIcon, groupName, sessionId, sessionName, title, message, messageType, fileName, filePath, timestamp, status, unreadCount, userId, postId);
                        }
                    }
                }

                //Refresh list
                if (AppConstants.Infoactive) {
                    if (!senderId.equalsIgnoreCase(userId)) {
                        if (postsList != null && postsList.size() == 0) {
                            notifyFragment(groupId, true);
                        } else {
                            notifyFragment(groupId, false);
                        }
                    }
                }
                //Refresh Msgs
                if (AppConstants.SessionChatactive || AppConstants.SessionChatBackground) {
                    if (!senderId.equalsIgnoreCase(userId)) {
                        notifySessionChat(sessionId);
                    }
                }

                if ((!AppConstants.SessionChatactive && !AppConstants.GroupChatactive & !AppConstants.Infoactive)) {
                    if (!senderId.equalsIgnoreCase(userId)) {
                        if (!(messageType.equalsIgnoreCase("Text")) && !(messageType.equalsIgnoreCase("Apps"))) {
                            message = fileName;
                        }
                        String write = improveDataBase.checkWritePermission(groupId, userId, orgId);
                        intent = new Intent(getApplicationContext(), SessionChatActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("SessionID", sessionId);
                        intent.putExtra("SessionName", sessionName);
                        intent.putExtra("GroupName", groupName);
                        intent.putExtra("GroupId", groupId);
                        intent.putExtra("SessionIcon", groupIcon);
                        intent.putExtra("Write", Boolean.parseBoolean(write));
                        intent.putExtra("From", false);
                        intent.putExtra(AppConstants.SESSIONCHAT_NOTIFICATION, AppConstants.SESSIONCHAT_NOTIFICATION);
                        sendNotification(title, senderName, message);
                    }
                }
                }else {
                    JSONObject jsondata = new JSONObject(remoteMessage.getData());
                    String NotificationType = jsondata.getString("NotificationType");
                    if(NotificationType.equalsIgnoreCase("Notification")){
                        inAppNotification =true;
                        String senderId = data.get("registration_id");
                        String deviceId =PrefManger.getSharedPreferencesString(getApplicationContext(), AppConstants.Login_Device_Id, "");
                        String strTitle = data.get("title");
                        String strMessageBody = data.get("body");
                        String userId = "";
                        String postId = "";
                        if(AppConstants.DefultAPK){
                         userId = ImproveHelper.getUserDetails(getApplicationContext()).getUserID();
                            postId = data.get("Post_ID");
                        }
                        String orgId = PrefManger.getSharedPreferencesString(getApplicationContext(), AppConstants.SP_ORGANISATION_ID, AppConstants.Default_OrgID);
                        String status = "Received";

                        if(strTitle != null && !strTitle.isEmpty()){
                            title =  strTitle;
                        }else{
                        title = getResources().getString(R.string.app_name);
                        }
                        String timestamp = jsondata.getString("time");
                        improveDataBase.insertIntoAllNotificationsTable("", title, strMessageBody, "", "", "", timestamp, status, "0", userId, postId,orgId);
                        sendNotificationData(data);
                    }else  if(NotificationType.equalsIgnoreCase("Sharing Notification")){
                        String orgId = PrefManger.getSharedPreferencesString(getApplicationContext(), AppConstants.SP_ORGANISATION_ID, AppConstants.Default_OrgID);
                        String strMessageBody = data.get("body");
                        JSONObject jsonObject = new JSONObject(strMessageBody);
                        strMessageBody = jsonObject.getString("DisplayName")+jsonObject.getString("body");
                        String userId = jsonObject.getString("UserID");
                        String postId = jsonObject.getString("PostID");
                        String status = "Received";
                        String timestamp = jsondata.getString("time");
                        improveDataBase.insertIntoAllNotificationsTable("", title, strMessageBody, "", "", "", timestamp, status, "0", userId, postId,orgId);
                        sendNotificationData(data);
                    }
                }
            } else {
                sendNotification(remoteMessage.getNotification().getTitle(), "", remoteMessage.getNotification().getBody());
            }
        } catch (Exception e) {
            Log.d(TAG, "onMessageReceived_e: " + e.toString());

        }
    }


    @Override
    public void onNewToken(@NonNull String s) {

        Log.d(TAG, "onNewToken: " + s);

        sendRegistrationToServer(s);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public boolean isAppOnScreen(String strPackageName) {

        Boolean isAppOnScreen;
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        ActivityManager.RunningTaskInfo task = tasks.get(0); // current task
        ComponentName rootActivity = task.baseActivity;

        String currentPackageName = rootActivity.getPackageName();
        if (currentPackageName.equals(strPackageName)) {
            //App is Running On Screen
            isAppOnScreen = true;
            Log.d(TAG, "isAppOnScreen: " + isAppOnScreen);
        } else {
            //App is Not Running On Screen
            isAppOnScreen = false;
            Log.d(TAG, "isAppOnScreen: " + isAppOnScreen);
        }
        return isAppOnScreen;
    }

    public String mSplitPageName(String message) {
        String strAp1 = null;
        if (message != null) {
            String[] separated = message.split("Form Distribution");
            if (separated != null) {
                strAp1 = separated[0]; // this will contain "Fruit"
                String strAp2 = separated[1];

                Log.d(TAG, "mSplitPageName: " + strAp1 + "\n" + strAp2);
            }
        }
        return strAp1.trim();

    }

    private void sendNotificationData(Map<String, String> data) {

        PrefManger.putSharedPreferencesString(getApplicationContext(), AppConstants.Notification_Back_Press, "1");
        Log.d("NotificationBPN", PrefManger.getSharedPreferencesString(getApplicationContext(), AppConstants.Notification_Back_Press, ""));
        intent = null;

        String strMessageBody = data.get("body");
        Log.d(TAG, "onMessageReceived5: " + strMessageBody);
        if (strMessageBody!=null && strMessageBody.contains("\"Application\":")) {
            try {
                JSONObject jsonObject = new JSONObject(strMessageBody);

                if (jsonObject != null) {
                    strPageName = jsonObject.getString("Application");
                    strAppType = jsonObject.getString("AppType");
                    messageBody = jsonObject.getString("body");
                    if (strAppType.equalsIgnoreCase(AppConstants.TASK_MANAGEMENT)) {
                        intent = new Intent(getApplicationContext(), NotificationsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("FromNormalTask", "FromNormalTask");
                        intent.putExtra(FromNotificationOnlyInTask, "OnlyInTask");
                    } else if (strAppType.equalsIgnoreCase(AppConstants.E_LEARNING_NOTIFICATION)) {
                        str_EL_DistributionId = jsonObject.getString("DistributionId");
                        intent = new Intent(getApplicationContext(), ELearningListActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("DistributionId", str_EL_DistributionId);
                        intent.putExtra(AppConstants.E_LEARNING_NOTIFICATION, AppConstants.E_LEARNING_NOTIFICATION);
                    } else if (strAppType.equalsIgnoreCase(AppConstants.DASHBOARD)) {
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(AppConstants.WEB_VIEW, AppConstants.DASHBOARD);
                    } else if (strAppType.equalsIgnoreCase(AppConstants.REPORTS)) {
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(AppConstants.WEB_VIEW, AppConstants.REPORTS);
                    } else if (strAppType.equalsIgnoreCase(AppConstants.E_LEARNING_NOTIFICATION)) { // ELearning
                        str_EL_DistributionId = jsonObject.getString("DistributionId");
                        intent = new Intent(getApplicationContext(), ELearningListActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("DistributionId", str_EL_DistributionId);
                        intent.putExtra(AppConstants.E_LEARNING_NOTIFICATION, AppConstants.E_LEARNING_NOTIFICATION);
                    } else {
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("app_edit", "New");
                    }
                    intent.putExtra(Notification_PageName, strPageName);
                    intent.putExtra(FromNotification, FromNotification);
                    Log.d(TAG, "sendNotificationDataAppType: " + FromNotification + " " + strAppType + " - " + strPageName + " - " + messageBody);
                }
                showNotification();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (data.size()>0 && data.get("chat_type") != null && !data.get("chat_type").isEmpty()) {
            strPageName = "";
            messageBody = data.get("body");
            title = data.get("title");
            if ("I".equals(data.get("chat_type"))) {
                Log.d("NotificationType", data.get("chat_type"));
                intent = new Intent(getApplicationContext(), BottomNavigationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if ((!AppConstants.Chatactive && !AppConstants.GroupChatactive & !AppConstants.Infoactive)) {
                    showChatNotification();
                }
            } else if ("G".equals(data.get("chat_type"))) {
                Log.d("NotificationType", data.get("chat_type"));
                intent = new Intent(getApplicationContext(), BottomNavigationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if ((!AppConstants.Chatactive && !AppConstants.GroupChatactive & !AppConstants.Infoactive)) {
                    showChatNotification();
                }
            }

        }else {
            sendNotification(getString(R.string.app_name), "", strMessageBody);
        }
    }

    private void showNotification() {

        int multiNotifications = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        PendingIntent resultIntent = PendingIntent.getActivity(this, multiNotifications, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        String channelId = "";
        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.icon_small_notfication)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(strPageName + " " + messageBody)
                .setAutoCancel(true)
                .setGroup(AppConstants.GROUP_KEY_BHARGO)
                .setGroupSummary(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_bhargo_user_rounded))
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Since android Oreo notification channel is needed.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(multiNotifications + 2, mNotificationBuilder.build());
        Log.d("MultiNotifications", String.valueOf(multiNotifications));
    }

    private void showChatNotification() {

//        if (wasInBackground) {

        int multiNotifications = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        PendingIntent resultIntent = PendingIntent.getActivity(this, multiNotifications, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        String channelId = "";
        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.icon_small_notfication)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setGroup(AppConstants.GROUP_KEY_BHARGO)
                .setGroupSummary(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_bhargo_user_rounded))
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Since android Oreo notification channel is needed.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(channelId,

                    "Channel human readable title",

                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(channel);

        }

        notificationManager.notify(multiNotifications + 2, mNotificationBuilder.build());


    }

    private void notifyFragment(String groupId, boolean refreshGroups) {
        Intent intent = new Intent("sessionListRefresh");
        intent.putExtra("new_group_id", groupId);
        intent.putExtra("refreshGroups", refreshGroups);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void notifySessionChat(String sessionId) {
        Intent intent = new Intent("sessionChatRefresh");
        intent.putExtra("session_id",sessionId);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendNotification(String title, String senderName, String messageBody) {
        Log.d(TAG, "onMessageReceived3: " + messageBody);
        String messageBodyStr;
        if (senderName != null && !senderName.equalsIgnoreCase("")) {
             messageBodyStr = "<b>" + senderName + ":" + "</b> " + messageBody;
        } else {
            messageBodyStr = messageBody;
        }
        if(AppConstants.DefultAPK_OrgID.equalsIgnoreCase("SELE20210719175221829") || inAppNotification){
            intent = new Intent(getApplicationContext(), NotificationsActivity.class);
            intent.putExtra("FromAction", "1");
            intent.putExtra("FromCallWindowAction", true);
        }else {
            intent = new Intent(getApplicationContext(), BottomNavigationActivity.class);
            intent.putExtra("FromAction", "7");
            AppConstants.FromNotificationOnlyCommunication= "OnlyCommunication";
        }
        PrefManger.putSharedPreferencesString(getApplicationContext(), AppConstants.Notification_Back_Press, "1");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        String GROUP_KEY_WORK_EMAIL = "com.bhargo.user.session";
        String channelId = "Session Notifications";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.icon_small_notfication)
                        .setColor(ContextCompat.getColor(getApplicationContext(),
                                R.color.colorPrimaryDark))
                        .setContentTitle(title)
                        .setContentText(Html.fromHtml(messageBodyStr))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setGroup(GROUP_KEY_WORK_EMAIL)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Session Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        int id = (int) System.currentTimeMillis();
        notificationManager.notify(id /* ID of notification */, notificationBuilder.build());
        Log.d(TAG, "onMessageReceived4: " + messageBody);
        notifyUIElement();
    }
    private void notifyUIElement() {
        Intent intent = new Intent("SelectEV_Notifications");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}