package com.bhargo.user.screens.onetoonechat;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.pojos.firebase.Conversation;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.ReadMoreOption;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.bhargo.user.utils.AppConstants.FromNotification;
import static com.bhargo.user.utils.AppConstants.Notification_PageName;


public class ListMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ListMessageAdapter";
    public static MediaPlayer mediaPlayer;
    private final Handler mHandler;
    private final int mInterval = 10;
    private final Context context;
    private final HashMap<String, Bitmap> bitmapAvata;
    private final HashMap<String, DatabaseReference> bitmapAvataDB;
    private final Bitmap bitmapAvataUser;
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
                Log.d(TAG, "onReceivefile: " + "check");
//                Toast.makeText(context, "File Downloaded", Toast.LENGTH_SHORT).show();

            }
        }
    };
    public BroadcastReceiver downloadReceiverAudio = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadReference.contains(referenceId)) {

                int index = downloadReference.indexOf(referenceId);
                LinearLayout iv_downlaod = viewDownload.get(index).findViewById(R.id.iv_audiofiledownload);
                ProgressBar pb_downlaodstatus = viewDownload.get(index).findViewById(R.id.pb_audio_download_status);
                pb_downlaodstatus.setVisibility(View.GONE);
                ImageButton ib_playpause = viewDownload.get(index).findViewById(R.id.btnPlay);
                ib_playpause.setTag("DOWNLOADED");
                ib_playpause.setImageDrawable(context.getDrawable(R.drawable.ic_play_arrow_black_24dp));
                Log.d(TAG, "onReceivefile: " + "check");
//                Toast.makeText(context, "File Downloaded", Toast.LENGTH_SHORT).show();
            }
        }
    };
    int currentPlayingPosition;
    String receiverID;
    BaseActivity baseActivity;
    long downloadID = 0;
    SessionManager sessionManager;
    ReadMoreOption readMoreOption;
    String message_child = null;
    ImproveHelper improveHelper;
    private Runnable mRunnable;
    private Conversation consersation;
    private DownloadManager downloadManager;

    public ListMessageAdapter(Context context, String message_child, Conversation consersation, HashMap<String, Bitmap> bitmapAvata, Bitmap bitmapAvataUser, String receiverID) {
        this.context = context;
        this.consersation = consersation;
        this.bitmapAvata = bitmapAvata;
        this.bitmapAvataUser = bitmapAvataUser;
        this.message_child = message_child;
        bitmapAvataDB = new HashMap<>();
        this.receiverID = receiverID;
        baseActivity = new BaseActivity();
        improveHelper = new ImproveHelper(context);

        downloadReference = new ArrayList<>();
        viewDownload = new ArrayList<>();
        Log.d(TAG, "receiverID2: " + receiverID);

        readMoreOption = new ReadMoreOption.Builder(context).build();
        sessionManager = new SessionManager(context);
        // Initialize the handler
        mHandler = new Handler();
        this.currentPlayingPosition = -1;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ChatActivity.VIEW_TYPE_FRIEND_MESSAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_send, parent, false);

            return new ItemMessageUserHolder(view);

        } else if (viewType == ChatActivity.VIEW_TYPE_USER_MESSAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_receive, parent, false);

            return new ItemMessageFriendHolder(view);
        }


        return null;
    }

    public void updateChatListItems(Conversation consersationMessages) {
        this.consersation = consersationMessages;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof ItemMessageFriendHolder) {

            /*((ItemMessageFriendHolder) holder).txtContent.setText(consersation.getListMessageData().get(position).getText());
            ((ItemMessageFriendHolder) holder).txtDate.setText(convertDate(consersation.getListMessageData().get(position).getCreatedDate().toString()));*/


            if (consersation.getListMessageData() != null) {
                if (consersation.getListMessageData().get(position).getMsgType() != null) {
                    if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Text")) {
//                        ((ItemMessageFriendHolder) holder).layout_audio.setVisibility(View.GONE);
//                        ((ItemMessageFriendHolder) holder).layout_others.setVisibility(View.VISIBLE);
                        ((ItemMessageFriendHolder) holder).layout_text_item.setVisibility(View.VISIBLE);
                        ((ItemMessageFriendHolder) holder).layout_file_item.setVisibility(View.GONE);
                        ((ItemMessageFriendHolder) holder).txtContent.setText(consersation.getListMessageData().get(position).getText());
                        readMoreOption.addReadMoreTo(((ItemMessageFriendHolder) holder).txtContent, consersation.getListMessageData().get(position).getText());
                        ((ItemMessageFriendHolder) holder).txtDate.setText(getTime(consersation.getListMessageData().get(position).getCreatedDate().toString()));

                    } else {
                        ((ItemMessageFriendHolder) holder).layout_text_item.setVisibility(View.GONE);
                        ((ItemMessageFriendHolder) holder).layout_file_item.setVisibility(View.VISIBLE);

                        if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Image")) {
                            ((ItemMessageFriendHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_img_chat));
//                            ((ItemMessageFriendHolder) holder).layout_others.setVisibility(View.VISIBLE);
//                            ((ItemMessageFriendHolder) holder).layout_audio.setVisibility(View.GONE);
                        } else if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Video")) {
//                            ((ItemMessageFriendHolder) holder).layout_others.setVisibility(View.VISIBLE);
//                            ((ItemMessageFriendHolder) holder).layout_audio.setVisibility(View.GONE);
                            ((ItemMessageFriendHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_video_chat));
                        } else if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Audio")) {
//                            ((ItemMessageFriendHolder) holder).layout_audio.setVisibility(View.VISIBLE);
//                            ((ItemMessageFriendHolder) holder).layout_others.setVisibility(View.GONE);
//                            ((ItemMessageFriendHolder) holder).txtFileName.setText(consersation.getListMessageData().get(position).getFileName());
                            ((ItemMessageFriendHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_audio_chat));


                        } else if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("txt") || consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("pdf")) {
//                            ((ItemMessageFriendHolder) holder).layout_others.setVisibility(View.VISIBLE);
//                            ((ItemMessageFriendHolder) holder).layout_audio.setVisibility(View.GONE);
                            ((ItemMessageFriendHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_file_chat));
                        } else if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Apps")) {
//                            ((ItemMessageFriendHolder) holder).layout_others.setVisibility(View.VISIBLE);
//                            ((ItemMessageFriendHolder) holder).layout_audio.setVisibility(View.GONE);
                            ((ItemMessageFriendHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_apps_chat));
                            ((ItemMessageFriendHolder) holder).iv_filedownload.setVisibility(View.GONE);
                        }

                        ((ItemMessageFriendHolder) holder).tv_file_name.setText(consersation.getListMessageData().get(position).getFileName());
                        ((ItemMessageFriendHolder) holder).txtDate.setText(getTime(consersation.getListMessageData().get(position).getCreatedDate().toString()));

                        ((ItemMessageFriendHolder) holder).setIClickListener(new ItemMessageFriendHolder.IClickListener() {
                            @Override
                            public void onItemClickedAudio(int position, View v) {

//                                ImageButton ib_play = v.findViewById(R.id.btnPlay);
//                                ProgressBar pb_download = v.findViewById(R.id.pb_download_status);
//                                SeekBar seekBar = v.findViewById(R.id.seekBarAudio);
//
//                                if (ib_play.getTag().toString().equalsIgnoreCase("DOWNLOAD")) {
//                                    if (consersation.getListMessageData().get(position).getFilePath().equalsIgnoreCase("File not found")) {
//                                        Toast.makeText(context, consersation.getListMessageData().get(position).getFilePath(), Toast.LENGTH_SHORT).show();
//                                    } else {
//
//                                        pb_download.setVisibility(View.VISIBLE);
//
//                                        downloadFile1(v, consersation.getListMessageData().get(position).getFilePath(), consersation.getListMessageData().get(position).getFileName(), downloadReceiverAudio);
//
//                                    }
//
//                                } else {
//
//
//                                    String audioSDCardUrl = "/storage/emulated/0/Improve_User/" + "Chats" + "/" + consersation.getListMessageData().get(position).getFileName();
//                                    if (currentPlayingPosition != position) {
//                                        Log.d(TAG, "onClick: " + "in");
//                                        currentPlayingPosition = position;
//                                        if (mediaPlayer != null) {
//                                            mediaPlayer.stop();
//                                            mediaPlayer.release();
//                                            mediaPlayer = null;
//                                        }
//                                    }
////                                        v.setTag("PAUSE");
//                                    playAudio(audioSDCardUrl, seekBar, ib_play);
//                                    // ((ItemMessageFriendHolder) holder).seekBarAudio.setTag(position);
//                                }


                            }

                            @Override
                            public void onItemClickedImage(int position) {

                                String strSDCardUrl = null;
                                String strSDCardUrlExists = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + consersation.getListMessageData().get(position).getFileName();
                                strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + consersation.getListMessageData().get(position).getFileName();

                                if (isFileExists(strSDCardUrlExists)) {
//                                    if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Image")) {
////                                        openPreview(consersation.getListMessageData().get(position).getSenderName(), consersation.getListMessageData().get(position).getMsgType(), strSDCardUrl);
//                                        openFile(strSDCardUrl);
//                                    } else if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Video")) {
//                                        openFile(strSDCardUrl);
//                                    } else if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("txt") || consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("pdf")) {
//                                        openFile(strSDCardUrl);
//                                    }
                                    openFile(strSDCardUrl);
                                } else {
                                    if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Apps")) {
                                        String[] tempTxt = consersation.getListMessageData().get(position).getText().split("\n");
                                        String appName = tempTxt[0];
                                        String appType = tempTxt[1];

                                        Intent intent = new Intent(context, MainActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.putExtra(Notification_PageName, appName);
                                        intent.putExtra(FromNotification, FromNotification);
                                        context.startActivity(intent);
                                    } else {
                                        if (consersation.getListMessageData().get(position).getFilePath().equalsIgnoreCase("")) {
                                            Toast.makeText(context, context.getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
                                        } else {
                                            downloadSentFile(consersation.getListMessageData().get(position).getFilePath(), consersation.getListMessageData().get(position).getFileName(), downloadReceiver);
                                        }
                                    }
                                }

                            }
                        });
//

                    }
                    String strSDCardUrl = null;
                    strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + consersation.getListMessageData().get(position).getFileName();

                    if (isFileExists(strSDCardUrl)) {
                        ((ItemMessageFriendHolder) holder).iv_filedownload.setVisibility(View.GONE);

                        ((ItemMessageFriendHolder) holder).ib_playpause.setTag("DOWNLOADED");
                        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                            ((ItemMessageFriendHolder) holder).ib_playpause.setImageDrawable(context.getDrawable(R.drawable.ic_play_arrow_black_24dp));
                        }


                    } else {
                        if (!consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Apps")) {
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
                        if (!consersation.getListMessageData().get(position).getFilePath().equalsIgnoreCase("")) {

                            strSDCardUrlExists = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + consersation.getListMessageData().get(position).getFileName();
                            strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + consersation.getListMessageData().get(position).getFileName();
                        }
                        if (consersation.getListMessageData().get(position).getFilePath().equalsIgnoreCase("File not found")) {
                            Toast.makeText(context, consersation.getListMessageData().get(position).getFilePath(), Toast.LENGTH_SHORT).show();
                        } else if (isFileExists(strSDCardUrlExists)) {
                            openFile(strSDCardUrl);

//                                Toast.makeText(context, context.getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
                        } else {
                            ((ItemMessageFriendHolder) holder).pb_download_status.setVisibility(View.VISIBLE);
                            downloadFile1(v, consersation.getListMessageData().get(position).getFilePath(), consersation.getListMessageData().get(position).getFileName(), downloadReceiver);

                        }
                    }
                });


              /*  ((ItemMessageFriendHolder) holder).iv_filedownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (consersation.getListMessageData().get(position).getFilePath().equalsIgnoreCase("File not found")) {
                            Toast.makeText(context, consersation.getListMessageData().get(position).getFilePath(), Toast.LENGTH_SHORT).show();
                        } else {


//                        downloadFile(v, consersation.getListMessageData().get(position).getFilePath(), consersation.getListMessageData().get(position).getFileName());
                            if (consersation.getListMessageData().get(position).getFilePath().equalsIgnoreCase("")) {
                                Toast.makeText(context, context.getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
                            } else {
                                ((ItemMessageFriendHolder) holder).pb_download_status.setVisibility(View.VISIBLE);
                                downloadFile1(v, consersation.getListMessageData().get(position).getFilePath(), consersation.getListMessageData().get(position).getFileName(), downloadReceiver);
                            }
                        }
                    }
                });*/

                ((ItemMessageFriendHolder) holder).tv_stick_date.setText(convertDateForSticky(consersation.getListMessageData().get(position).getCreatedDate().toString()));
                if (position > 0) {
                    if (convertDate(consersation.getListMessageData().get(position).getCreatedDate().toString()).equalsIgnoreCase(convertDate(consersation.getListMessageData().get(position - 1).getCreatedDate().toString()))) {
                        ((ItemMessageFriendHolder) holder).layout_stick_date.setVisibility(View.GONE);
                    } else {
                        ((ItemMessageFriendHolder) holder).layout_stick_date.setVisibility(View.VISIBLE);
                    }
                } else {
                    ((ItemMessageFriendHolder) holder).layout_stick_date.setVisibility(View.VISIBLE);
                }

            }

//            ((ItemMessageFriendHolder) holder).layout_file_item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (((ItemMessageFriendHolder) holder).iv_filedownload.getVisibility() != View.VISIBLE) {
//                        if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Audio")) {
//                            String strSDCardUrl = null;
//                            String fileName = consersation.getListMessageData().get(position).getFileName();
//                            strSDCardUrl = "/storage/emulated/0/Improve_User/" + "Chats" + "/" + fileName;
////                            playAudio(strSDCardUrl,fileName);
//                        } else if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Video")) {
//                        } else if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("pdf")) {
//
//                        } else {
//                            openPreview(consersation.getListMessageData().get(position).getSenderName(), consersation.getListMessageData().get(position).getMsgType(), consersation.getListMessageData().get(position).getFilePath());
//                        }
//                    }
//                }
//            });


        } else if (holder instanceof ItemMessageUserHolder) {
            if (consersation.getListMessageData() != null) {
                if (consersation.getListMessageData().get(position).getMsgType() != null) {
                    if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Text")) {
                        ((ItemMessageUserHolder) holder).layout_text_item.setVisibility(View.VISIBLE);
                        ((ItemMessageUserHolder) holder).layout_file_item.setVisibility(View.GONE);
                        ((ItemMessageUserHolder) holder).txtContent.setText(consersation.getListMessageData().get(position).getText());
                        readMoreOption.addReadMoreTo(((ItemMessageUserHolder) holder).txtContent, consersation.getListMessageData().get(position).getText());
                        ((ItemMessageUserHolder) holder).txtDate.setText(getTime(consersation.getListMessageData().get(position).getCreatedDate().toString()));
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

                        if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Image")) {
                            ((ItemMessageUserHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_img_chat));
                        } else if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Video")) {
                            ((ItemMessageUserHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_video_chat));
                        } else if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Audio")) {
                            ((ItemMessageUserHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_audio_chat));
                        } else if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("txt") || consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("pdf")) {
                            ((ItemMessageUserHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_file_chat));
                        } else if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Apps")) {
                            ((ItemMessageUserHolder) holder).iv_filetype.setImageDrawable(context.getDrawable(R.drawable.icns_apps_chat));
                        }
                        ((ItemMessageUserHolder) holder).tv_file_name.setText(consersation.getListMessageData().get(position).getFileName());
                        ((ItemMessageUserHolder) holder).txtDate.setText(getTime(consersation.getListMessageData().get(position).getCreatedDate().toString()));

                        if (consersation.getListMessageData().get(position).getFilePath().equalsIgnoreCase("")) {
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

                                String strSDCardUrlExists = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats/Sent" + "/" + consersation.getListMessageData().get(position).getFileName();
                                strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats/Sent" + "/" + consersation.getListMessageData().get(position).getFileName();

                                if (isFileExists(strSDCardUrlExists)) {
//                                    if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Image")) {
////                                        openPreview(consersation.getListMessageData().get(position).getSenderName(), consersation.getListMessageData().get(position).getMsgType(), strSDCardUrl);
//                                        openFile(strSDCardUrl);
//                                    } else if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Video")) {
////                                        playVideo(strSDCardUrl);
//                                        openFile(strSDCardUrl);
//                                    } else if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("txt") || consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("pdf")) {
//                                        openFile(strSDCardUrl);
//                                    }
                                    openFile(strSDCardUrl);

                                } else {
                                    if (!consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Apps")) {
                                        if (consersation.getListMessageData().get(position).getFilePath().equalsIgnoreCase("")) {
                                            Toast.makeText(context, context.getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
                                        } else {
                                            downloadSentFile(consersation.getListMessageData().get(position).getFilePath(), consersation.getListMessageData().get(position).getFileName(), downloadReceiver);
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

                ((ItemMessageUserHolder) holder).tv_stick_date.setText(convertDateForSticky(consersation.getListMessageData().get(position).getCreatedDate().toString()));
                if (position > 0) {
                    if (convertDate(consersation.getListMessageData().get(position).getCreatedDate().toString()).equalsIgnoreCase(convertDate(consersation.getListMessageData().get(position - 1).getCreatedDate().toString()))) {
                        ((ItemMessageUserHolder) holder).layout_stick_date.setVisibility(View.GONE);
                    } else {
                        ((ItemMessageUserHolder) holder).layout_stick_date.setVisibility(View.VISIBLE);
                    }
                } else {
                    ((ItemMessageUserHolder) holder).layout_stick_date.setVisibility(View.VISIBLE);
                }

            }


//            ((ItemMessageUserHolder) holder).layout_file_item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Audio")) {
//                        String strSDCardUrl = null;
//                        String fileName = consersation.getListMessageData().get(position).getFileName();
//                        strSDCardUrl = "/storage/emulated/0/Improve_User/" + "Chats" + "/" + fileName;
////                        playAudio(strSDCardUrl,fileName);
//                    } else if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("Video")) {
//                    } else if (consersation.getListMessageData().get(position).getMsgType().equalsIgnoreCase("pdf")) {
//
//                    } else {
//                        openPreview(consersation.getListMessageData().get(position).getReceiverName(), consersation.getListMessageData().get(position).getMsgType(), consersation.getListMessageData().get(position).getFilePath());
//                    }
//
////                    Toast.makeText(context, consersation.getListMessageData().get(position).getFilePath(),Toast.LENGTH_SHORT).show();
//                }
//            });

            if (position == consersation.getListMessageData().size() - 1 && ImproveHelper.isNetworkStatusAvialable(context)) {
                ((ItemMessageUserHolder) holder).tv_seen.setVisibility(View.VISIBLE);

                if (consersation.getListMessageData().get(position).isMsgSeen()) {
                    ((ItemMessageUserHolder) holder).tv_seen.setText("Seen");
                } else {
                    ((ItemMessageUserHolder) holder).tv_seen.setText("Delivered");
                }
            } else {

                ((ItemMessageUserHolder) holder).tv_seen.setVisibility(View.GONE);
            }
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
        try {
            DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance("https://bhargocommunication-8c36e.firebaseio.com").getReference(sessionManager.getOrgIdFromSession());

            mFirebaseDatabaseReference.child("Chats").child(message_child).child(consersation.getListMessageData().get(position).getMessageID()).removeValue();
            updateData(position);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "deleteMessage", e);
        }
    }

    private void openFile(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);

            File file = new File(context.getExternalFilesDir(null), url);
            Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
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

    private void playVideo(String filePath) {

        File file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "video/*");
        context.startActivity(intent);


    }

    private void playAudio(String audioSDCardUrl, SeekBar seekBar, View view) {
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                ImageButton ibPlay = view.findViewById(R.id.btnPlay);
                ibPlay.setImageDrawable(context.getDrawable(R.drawable.ic_play_arrow_black_24dp));

                mediaPlayer.pause();
            } else if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                ImageButton ibPlay = view.findViewById(R.id.btnPlay);
                ibPlay.setImageDrawable(context.getDrawable(R.drawable.ic_pause_black_24dp));
                mediaPlayer.start();

            } else {
                ImageButton ibPlay = view.findViewById(R.id.btnPlay);
                ibPlay.setImageDrawable(context.getDrawable(R.drawable.ic_pause_black_24dp));

                Uri myUri = Uri.parse(audioSDCardUrl); // initialize Uri here
                mediaPlayer = new MediaPlayer();


                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                mediaPlayer.setDataSource(context, myUri);

                mediaPlayer.prepare();

                seekBar.setMax(mediaPlayer.getDuration());

                mediaPlayer.start();
            }


            new Thread(new Runnable() {
                @Override
                public void run() {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    int total = mediaPlayer.getDuration();


                    while (mediaPlayer != null && mediaPlayer.isPlaying() && currentPosition < total) {
                        try {
                            Thread.sleep(1000);
                            currentPosition = mediaPlayer.getCurrentPosition();
                        } catch (InterruptedException e) {
                            return;
                        } catch (Exception e) {
                            return;
                        }

                        seekBar.setProgress(currentPosition);

                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updateData(int position) {
        try {
            consersation.getListMessageData().remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, consersation.getListMessageData().size());
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "updateData", e);
        }

    }

//    protected void stopPlaying() {
//        // If media player is not null then try to stop it
//        if (mPlayer != null) {
//            mPlayer.stop();
//            mPlayer.release();
//            mPlayer = null;
////            Toast.makeText(context,"Stop playing.",Toast.LENGTH_SHORT).show();
//            if (mHandler != null) {
//                mHandler.removeCallbacks(mRunnable);
//            }
//        }
//    }


    private void openPreview(String senderName, String msgType, String filePath) {

        final Dialog nagDialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nagDialog.setCancelable(false);
        nagDialog.setContentView(R.layout.layout_image_preview);

        ImageView ivPreview = nagDialog.findViewById(R.id.iv_preview_image);

        TextView title = nagDialog.findViewById(R.id.toolbar_title);
        ImageView iv_back = nagDialog.findViewById(R.id.iv_back);
        ImageView iv_circle_appIcon = nagDialog.findViewById(R.id.iv_circle_appIcon);

        title.setText(senderName);
        iv_circle_appIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user));

        if (msgType.equalsIgnoreCase("Image") || msgType.equalsIgnoreCase("jpg") || msgType.equalsIgnoreCase("png")) {
            ivPreview.setVisibility(View.VISIBLE);
            Glide.with(context).load(filePath).dontAnimate()
                    .into(ivPreview);


        }

        nagDialog.show();


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nagDialog.dismiss();
            }
        });

    }

//    public void playAudio(String path, String fileName) {
//
//        try {
//            MediaPlayer player = new MediaPlayer();
//            if(player.isPlaying()){
//                player.stop();
//            }else{
//            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            player.setDataSource(path);
//            player.prepare();
//            player.start();}
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//        }


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

    public void downloadFile(View view, String filePath, String fileName) {

        try {
            String strSDCardUrl = null;
            strSDCardUrl = "/Improve_User/" + sessionManager.getOrgIdFromSession() + "/Chats" + "/" + fileName;


            if (!filePath.contains(" ")) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(filePath));
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setTitle("Improve User");
                request.setDescription("Downloading Files....");
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir("", strSDCardUrl);

                DownloadManager downloadManagerDataControl = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                long downloaId = downloadManagerDataControl.enqueue(request);

                Log.d(TAG, "startDownload: " + downloadManagerDataControl);

                ImageView iv_downlaod = view.findViewById(R.id.iv_filedownload);
                iv_downlaod.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "downloadFile", e);
        }

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

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG, "receiverID331: " + receiverID);
        Log.d(TAG, "receiverID221: " + consersation.getListMessageData().get(position).getSenderID());
        return consersation.getListMessageData().get(position).getSenderID().equalsIgnoreCase(receiverID) ? ChatActivity.VIEW_TYPE_USER_MESSAGE : ChatActivity.VIEW_TYPE_FRIEND_MESSAGE;
    }

    @Override
    public int getItemCount() {
        return consersation.getListMessageData().size();
    }

    public String getTime(String s) {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date(Long.parseLong(s))).replace("AM", "am").replace("PM", "pm");
    }

    public String convertDate(String s) {

//        String date = null;

//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//
//        String systemDate = null;
//        String msgDate = null;
//        String previousDayDate = baseActivity.getPreviousDayDate();
//
//
//        Log.d(TAG, "convertDate: " + baseActivity.getPreviousDayDate());
//
//
//        try {
//            msgDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(Long.parseLong(s)));
//
//            systemDate = baseActivity.getDateFromDeviceForCal();
//            Date dateOne = format.parse(msgDate);
//            Date dateTwo = format.parse(systemDate);
//            if (dateOne.equals(dateTwo)) {
//
//                date = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date(Long.parseLong(s))).replace("AM", "am").replace("PM", "pm");
//            } else if (msgDate.equalsIgnoreCase(previousDayDate)) {
//                date = "Yesterday";
//
//            } else {
//                date = msgDate;
//            }
//
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        String conToStr = null;
        try {
            conToStr = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(Long.parseLong(s)));
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "convertDate", e);
        }
        return conToStr;

    }

    public String convertDateForSticky(String s) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        String date = null;
        String systemDate = null;
        String msgDate = null;
        String previousDayDate = baseActivity.getPreviousDayDate();
        Log.d(TAG, "convertDate: " + baseActivity.getPreviousDayDate());

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


}
