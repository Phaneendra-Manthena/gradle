package com.bhargo.user.screens.sesssionchat;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.interfaces.GroupClickListener;
import com.bhargo.user.pojos.firebase.ChatDetails;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.ReadMoreOption;
import com.bhargo.user.utils.SessionManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.bhargo.user.utils.AppConstants.FromNotification;
import static com.bhargo.user.utils.AppConstants.Notification_PageName;

public class SessionMessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "UserAdapter";
    public static MediaPlayer mediaPlayer;
    private final Context context;
    private final List<Long> downloadReference;
    private final List<View> viewDownload;
    public BroadcastReceiver downloadReceiver = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadReference.contains(referenceId)) {

                int index = downloadReference.indexOf(referenceId);
                FrameLayout iv_downlaod = viewDownload.get(index).findViewById(R.id.iv_filedownload);
                ProgressBar pb_downlaodstatus = viewDownload.get(index).findViewById(R.id.pb_download_status);
                pb_downlaodstatus.setVisibility(View.GONE);
                iv_downlaod.setVisibility(View.GONE);
//                Toast.makeText(context, "File Downloaded", Toast.LENGTH_SHORT).show();

            }
        }
    };
    SessionManager sessionManager;
    GroupClickListener clickListener;
    BaseActivity baseActivity;
    ReadMoreOption readMoreOption;
    ImproveHelper improveHelper;
    private List<ChatDetails> chatList;
    private DownloadManager downloadManager;

    public SessionMessagesAdapter(Context context, List<ChatDetails> chatList) {
        this.context = context;
        this.chatList = chatList;
        sessionManager = new SessionManager(context);
        readMoreOption = new ReadMoreOption.Builder(context).build();
        baseActivity = new BaseActivity();
        improveHelper = new ImproveHelper(context);

        downloadReference = new ArrayList<>();
        viewDownload = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SessionChatActivity.VIEW_TYPE_FRIEND_MESSAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_send, parent, false);

            return new ItemMessageUserHolder(view);

        } else if (viewType == SessionChatActivity.VIEW_TYPE_USER_MESSAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_receive_group, parent, false);
            return new ItemMessageFriendHolder(view);
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return chatList.get(position).getSenderID().equalsIgnoreCase(sessionManager.getUserDataFromSession().getUserID()) ? SessionChatActivity.VIEW_TYPE_FRIEND_MESSAGE : SessionChatActivity.VIEW_TYPE_USER_MESSAGE;
//        return ChatActivity.VIEW_TYPE_FRIEND_MESSAGE;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatDetails notification = chatList.get(position);
        if (holder instanceof ItemMessageFriendHolder) {

            if (notification != null) {
                if (notification.getMsgType() != null) {
                    ((ItemMessageFriendHolder) holder).txtSenderName.setText(notification.getSenderName());
                    if (notification.getMsgType().equalsIgnoreCase("Text")) {
                        ((ItemMessageFriendHolder) holder).layout_text_item.setVisibility(View.VISIBLE);
                        ((ItemMessageFriendHolder) holder).layout_file_item.setVisibility(View.GONE);
                        ((ItemMessageFriendHolder) holder).txtContent.setText(notification.getText());
                        readMoreOption.addReadMoreTo(((ItemMessageFriendHolder) holder).txtContent, notification.getText());
                        ((ItemMessageFriendHolder) holder).txtDate.setText(getTime(notification.getCreatedDate().toString()));

                    } else {
                        ((ItemMessageFriendHolder) holder).layout_text_item.setVisibility(View.GONE);
                        ((ItemMessageFriendHolder) holder).layout_file_item.setVisibility(View.VISIBLE);

                        if (notification.getMsgType().equalsIgnoreCase("Image")) {
                            ((ItemMessageFriendHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_img_chat));
                        } else if (notification.getMsgType().equalsIgnoreCase("Video")) {
                            ((ItemMessageFriendHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_video_chat));
                        } else if (notification.getMsgType().equalsIgnoreCase("Audio")) {
                            ((ItemMessageFriendHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_audio_chat));


                        } else if (notification.getMsgType().equalsIgnoreCase("txt") || notification.getMsgType().equalsIgnoreCase("pdf")) {
                            ((ItemMessageFriendHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_file_chat));
                        } else if (notification.getMsgType().equalsIgnoreCase("Apps")) {
                            ((ItemMessageFriendHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_apps_chat));
                            ((ItemMessageFriendHolder) holder).iv_filedownload.setVisibility(View.GONE);
                        }

                        ((ItemMessageFriendHolder) holder).tv_file_name.setText(notification.getFileName());
                        ((ItemMessageFriendHolder) holder).txtDate.setText(getTime(notification.getCreatedDate().toString()));

                        ((ItemMessageFriendHolder) holder).setIClickListener(new ItemMessageFriendHolder.IClickListener() {
                            @Override
                            public void onItemClickedAudio(int position, View v) {

                            }

                            @Override
                            public void onItemClickedImage(int position) {

                                String strSDCardUrl = null;
                                String strSDCardUrlExists = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + notification.getFileName();
                                strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + notification.getFileName();

                                if (isFileExists(strSDCardUrlExists)) {
                                    openFile(strSDCardUrl);
                                } else {
                                    if (notification.getMsgType().equalsIgnoreCase("Apps")) {
                                        String[] tempTxt = notification.getText().split("\n");
                                        String appName = tempTxt[0];
                                        String appType = tempTxt[1];

                                        Intent intent = new Intent(context, MainActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.putExtra(Notification_PageName, appName);
                                        intent.putExtra(FromNotification, FromNotification);
                                        context.startActivity(intent);
                                    } else {
                                        if (notification.getFilePath().equalsIgnoreCase("")) {
                                            Toast.makeText(context, context.getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
                                        } else {
                                            downloadSentFile(notification.getFilePath(), notification.getFileName(), downloadReceiver);
                                        }
                                    }
                                }

                            }
                        });
                    }
                    String strSDCardUrl = null;
                    strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + notification.getFileName();

                    if (isFileExists(strSDCardUrl)) {
                        ((ItemMessageFriendHolder) holder).iv_filedownload.setVisibility(View.GONE);

                        ((ItemMessageFriendHolder) holder).ib_playpause.setTag("DOWNLOADED");
                        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                            ((ItemMessageFriendHolder) holder).ib_playpause.setImageDrawable(context.getDrawable(R.drawable.ic_play_arrow_black_24dp));
                        }


                    } else {
                        if (!notification.getMsgType().equalsIgnoreCase("Apps")) {
                            ((ItemMessageFriendHolder) holder).iv_filedownload.setVisibility(View.VISIBLE);
                        }
                        ((ItemMessageFriendHolder) holder).ib_playpause.setTag("DOWNLOAD");
                        ((ItemMessageFriendHolder) holder).ib_playpause.setImageDrawable(context.getDrawable(R.drawable.ic_file_download_black_24dp));

                    }
                }

                ((ItemMessageFriendHolder) holder).layout_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strSDCardUrl = null;
                        String strSDCardUrlExists = null;
                        if (!notification.getFilePath().equalsIgnoreCase("")) {

                            strSDCardUrlExists = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + notification.getFileName();
                            strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + notification.getFileName();
                        }
                        if (notification.getFilePath().equalsIgnoreCase("File not found")) {
                            Toast.makeText(context, notification.getFilePath(), Toast.LENGTH_SHORT).show();
                        } else if (isFileExists(strSDCardUrlExists)) {
                            openFile(strSDCardUrl);

//                                Toast.makeText(context, context.getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
                        } else {
                            ((ItemMessageFriendHolder) holder).pb_download_status.setVisibility(View.VISIBLE);
                            downloadFile1(v, notification.getFilePath(), notification.getFileName(), downloadReceiver);

                        }
                    }
                });


                if (notification.getMsgType().equalsIgnoreCase("System")) {
                    ((ItemMessageFriendHolder) holder).layout_message.setVisibility(View.GONE);
                    ((ItemMessageFriendHolder) holder).tv_stick_date.setText(notification.getText());
                } else {
                    ((ItemMessageFriendHolder) holder).layout_message.setVisibility(View.VISIBLE);
                    ((ItemMessageFriendHolder) holder).tv_stick_date.setText(convertDateForSticky(notification.getCreatedDate().toString()));
                }


                if (position > 0) {
                    if (!notification.getMsgType().equalsIgnoreCase("System") && convertDate(notification.getCreatedDate().toString()).equalsIgnoreCase(convertDate(chatList.get(position - 1).getCreatedDate().toString()))) {
                        ((ItemMessageFriendHolder) holder).layout_stick_date.setVisibility(View.GONE);
                    } else {
                        ((ItemMessageFriendHolder) holder).layout_stick_date.setVisibility(View.VISIBLE);
                    }
                } else {
                    ((ItemMessageFriendHolder) holder).layout_stick_date.setVisibility(View.VISIBLE);
                }

            }

        } else if (holder instanceof ItemMessageUserHolder) {
            if (notification != null) {
                if (notification.getMsgType() != null) {
                    if (notification.getMsgType().equalsIgnoreCase("Text")) {
                        ((ItemMessageUserHolder) holder).layout_text_item.setVisibility(View.VISIBLE);
                        ((ItemMessageUserHolder) holder).layout_file_item.setVisibility(View.GONE);
                        ((ItemMessageUserHolder) holder).txtContent.setText(notification.getText());
                        readMoreOption.addReadMoreTo(((ItemMessageUserHolder) holder).txtContent, notification.getText());
                        ((ItemMessageUserHolder) holder).txtDate.setText(getTime(notification.getCreatedDate().toString()));
                        ((ItemMessageUserHolder) holder).setIClickListener(new ItemMessageUserHolder.IClickListener() {
                            @Override
                            public void onItemClickedAudio(int position, View v) {

                            }

                            @Override
                            public void onItemClickedImage(int position) {

                            }

                            @Override
                            public void onItemClickedItem(int position) {
                                alertDialogDelete(position, "Delete message?");
                            }
                        });

                    } else {
                        ((ItemMessageUserHolder) holder).layout_text_item.setVisibility(View.GONE);
                        ((ItemMessageUserHolder) holder).layout_file_item.setVisibility(View.VISIBLE);

                        if (notification.getMsgType().equalsIgnoreCase("Image")) {
                            ((ItemMessageUserHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_img_chat));
                        } else if (notification.getMsgType().equalsIgnoreCase("Video")) {
                            ((ItemMessageUserHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_video_chat));
                        } else if (notification.getMsgType().equalsIgnoreCase("Audio")) {
                            ((ItemMessageUserHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_audio_chat));
                        } else if (notification.getMsgType().equalsIgnoreCase("txt") || notification.getMsgType().equalsIgnoreCase("pdf")) {
                            ((ItemMessageUserHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_file_chat));
                        } else if (notification.getMsgType().equalsIgnoreCase("Apps")) {
                            ((ItemMessageUserHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_apps_chat));
                        }
                        ((ItemMessageUserHolder) holder).tv_file_name.setText(notification.getFileName());
                        ((ItemMessageUserHolder) holder).txtDate.setText(getTime(notification.getCreatedDate().toString()));

                        if (notification.getFilePath().equalsIgnoreCase("")) {
                            ((ItemMessageUserHolder) holder).pb_file.setVisibility(View.VISIBLE);
                        } else {
                            ((ItemMessageUserHolder) holder).pb_file.setVisibility(View.GONE);
                        }

                        ((ItemMessageUserHolder) holder).setIClickListener(new ItemMessageUserHolder.IClickListener() {
                            @Override
                            public void onItemClickedAudio(int position, View v) {

                            }

                            @Override
                            public void onItemClickedImage(int position) {
                                String strSDCardUrl = null;

                                String strSDCardUrlExists = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats/Sent" + "/" + notification.getFileName();
                                strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats/Sent" + "/" + notification.getFileName();

                                if (isFileExists(strSDCardUrlExists)) {
                                    openFile(strSDCardUrl);

                                } else {
                                    if (!notification.getMsgType().equalsIgnoreCase("Apps")) {
                                        if (notification.getFilePath().equalsIgnoreCase("")) {
                                            Toast.makeText(context, context.getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
                                        } else {
                                            downloadSentFile(notification.getFilePath(), notification.getFileName(), downloadReceiver);
                                        }
                                    }
                                }


                            }

                            @Override
                            public void onItemClickedItem(int position) {

                                alertDialogDelete(position, "Delete message?");
                            }
                        });
                    }
                }

                if (notification.getMsgType().equalsIgnoreCase("System")) {
                    ((ItemMessageUserHolder) holder).layout_message.setVisibility(View.GONE);
                    ((ItemMessageUserHolder) holder).tv_stick_date.setText(notification.getText());
                } else {
                    ((ItemMessageUserHolder) holder).layout_message.setVisibility(View.VISIBLE);
                    ((ItemMessageUserHolder) holder).tv_stick_date.setText(convertDateForSticky(notification.getCreatedDate().toString()));
                }
                if (position > 0) {
                    if (!notification.getMsgType().equalsIgnoreCase("System") && convertDate(notification.getCreatedDate().toString()).equalsIgnoreCase(convertDate(chatList.get(position - 1).getCreatedDate().toString()))) {
                        ((ItemMessageUserHolder) holder).layout_stick_date.setVisibility(View.GONE);
                    } else {
                        ((ItemMessageUserHolder) holder).layout_stick_date.setVisibility(View.VISIBLE);
                    }
                } else {
                    ((ItemMessageUserHolder) holder).layout_stick_date.setVisibility(View.VISIBLE);
                }

            }


        }
    }

    /*@Override
    public void onBindViewHolder(@NonNull ChatsViewHolder holder, int position) {

        Notification notification = notificationList;

       holder.txtTitle.setText(notification.getTitle());
       holder.txtDesc.setText(notification.getMessage());
       holder.txtDate.setText(notification.getDate());
       holder.txtUnreadMessages.setText(notification.getUnreadcount());


    }*/

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public void setCustomClickListener(GroupClickListener groupClickListener) {
        this.clickListener = groupClickListener;
    }


    public void updateData(List<ChatDetails> chatList) {
        try {
            this.chatList.addAll(chatList);
            notifyDataSetChanged();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "updateData", e);
        }
    }

    public void setData(List<ChatDetails> chatList) {
        try {
            this.chatList = chatList;
            notifyDataSetChanged();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setData", e);
        }

    }


    public String getTime(String s) {
        String strGetTime = null;
        try {
            strGetTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date(Long.parseLong(s))).replace("AM", "am").replace("PM", "pm");
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "getTime", e);
        }
        return strGetTime;
    }

    public String convertDate(String s) {

        String strConvertDate = null;
        try {
            strConvertDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(Long.parseLong(s)));
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "convertDate", e);
        }
        return strConvertDate;

    }

    private boolean isFileExists(String filename) {
        boolean isFileExists = false;
        try {
            File file = new File(context.getExternalFilesDir(null), filename);
            isFileExists = file.exists();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "isFileExists", e);
        }
        return isFileExists;
    }

    public String convertDateForSticky(String s) {

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

//                date = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date(Long.parseLong(s))).replace("AM", "am").replace("PM", "pm");
                date = "TODAY";
            } else if (msgDate.equalsIgnoreCase(previousDayDate)) {
                date = "YESTERDAY";

            } else {
                date = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date(Long.parseLong(s)));
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "convertDateForSticky", e);
        }
        return date;

    }

    public void downloadSentFile(String filePath, String fileName, BroadcastReceiver broadcastReceiver) {
        try {
            if (!filePath.equalsIgnoreCase("File not found")) {
                File projDir = new File(context.getExternalFilesDir(null), "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats/Sent");
                if (!projDir.exists())
                    projDir.mkdirs();
                String strSDCardUrl = null;
                strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats/Sent" + "/" + fileName;


                downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                Uri Download_Uri = Uri.parse(filePath);
                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

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
                downloadReference.add(downloadManager.enqueue(request));

            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "downloadSentFile", e);
        }
    }

    public void alertDialogDelete(int position, String displayMsg) {
        try {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);

            builder.setMessage(displayMsg)
                    .setCancelable(false)
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("DELETE FOR ME", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            deleteMessage(position);
                        }
                    });


            //Creating dialog box
            androidx.appcompat.app.AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("");
            alert.show();
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "alertDialogDelete", e);
        }
    }


    private void deleteMessage(int position) {

     /*   DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance("https://bhargocommunication-8c36e.firebaseio.com").getReference(sessionManager.getOrgIdFromSession());

        mFirebaseDatabaseReference.child("Chats").child(message_child).child(consersation.getListMessageData().get(position).getMessageID()).removeValue();
        updateData(position);*/

    }

    private void openFile(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);

            File file = new File(context.getExternalFilesDir(null), url);
            Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", file);
            if (url.contains(".doc") || url.contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (url.contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (url.contains(".ppt") || url.contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.contains(".xls") || url.contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (url.contains(".zip") || url.contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav");
            } else if (url.contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (url.contains(".wav") || url.contains(".mp3") || url.contains(".WMA") || url.contains(".ogg")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (url.contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (url.contains(".PNG") || url.contains(".JPEG") || url.contains(".jpg") || url.contains(".jpeg") || url.contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (url.contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (url.contains(".3gp") || url.contains(".mpg") || url.contains(".mpeg") || url.contains(".mpe") || url.contains(".mp4") || url.contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                //if you want you can also define the intent type for any other file

                //additionally use else clause below, to manage other unknown extensions
                //in this case, Android will show all applications installed on the device
                //so you can choose which application to use
                intent.setDataAndType(uri, "*/*");
            }
            context.startActivity(intent);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "openFile", e);
        }
    }

    public void downloadFile1(View view, String filePath, String fileName, BroadcastReceiver broadcastReceiver) {
        try {
            if (!filePath.equalsIgnoreCase("File not found")) {
//            File fileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/" + "Chats");
//            if (!fileDir.exists()) {
//                fileDir.mkdirs();
//            }
                File projDir = new File(context.getExternalFilesDir(null), "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats");
                if (!projDir.exists())
                    projDir.mkdirs();

                String strSDCardUrl = null;
                strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + fileName;

                downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
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
                downloadReference.add(downloadManager.enqueue(request));

                viewDownload.add(view);
                IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                context.registerReceiver(broadcastReceiver, filter);

            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "downloadFile1", e);
        }
    }
}

