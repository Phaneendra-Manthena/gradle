package com.bhargo.user.screens.sesssionchat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.interfaces.ConversationItemClickListener;
import com.bhargo.user.pojos.firebase.Notification;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.SessionManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class ConversationListAdapter extends RecyclerView.Adapter<ConversationListAdapter.ChatsViewHolder> {

    private static final String TAG = "UserAdapter";
    private final Context context;
    SessionManager sessionManager;
    BaseActivity baseActivity;
    ConversationItemClickListener clickListener;
    ImproveHelper improveHelper;
    private List<Notification> notificationList;


    public ConversationListAdapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
        sessionManager = new SessionManager(context);
        improveHelper = new ImproveHelper(context);
        baseActivity = new BaseActivity();
    }

    @NonNull
    @Override
    public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_list_item, parent, false);

        return new ChatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsViewHolder holder, int position) {

        Notification notification = notificationList.get(position);

        holder.txtTitle.setText(notification.getTitle());
        if (notification.getMessageType().equalsIgnoreCase("Text") || notification.getMessageType().equalsIgnoreCase("Apps") || notification.getMessageType().equalsIgnoreCase("System")) {
            holder.txtDesc.setText(notification.getMessage());
        } else {
            holder.txtDesc.setText(notification.getFileName());
        }
        holder.txtDate.setText(convertDate(notification.getTimeStamp()));
        if (!notification.getUnreadCount().equalsIgnoreCase("0")) {
            holder.layout_unread.setVisibility(View.VISIBLE);
            holder.txtUnreadMessages.setText(notification.getUnreadCount());
        } else {
            holder.layout_unread.setVisibility(View.INVISIBLE);
        }
        if (notification.getGroupIcon() != null) {
            if (notification.getGroupIcon().equals("default")) {
                holder.iv_profile_pic.setImageResource(R.drawable.user);

            } else {
                if (isFileExists(notification.getGroupIcon())) {
                    setImageFromSDCard(notification.getGroupIcon(), holder.iv_profile_pic);
                } else {

                    DownloadFileFromURLTask fileFromURLTask = new DownloadFileFromURLTask(holder.iv_profile_pic);
                    fileFromURLTask.execute(notification.getGroupIcon());
                }
            }
        }
        /*String tempICon =  "http://182.18.157.124/improveUploadFiles/GroupIcon/groupicon.png";
        if (tempICon.equals("default")) {
            holder.iv_profile_pic.setImageResource(R.drawable.user);

        } else {
            if (isFileExists(tempICon)) {
                setImageFromSDCard(tempICon, holder.iv_profile_pic);
            } else {

                DownloadFileFromURLTask fileFromURLTask = new DownloadFileFromURLTask(holder.iv_profile_pic);
                fileFromURLTask.execute(tempICon);
            }
        }*/

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }


    public void setCustomClickListener(ConversationItemClickListener conversationItemClickListener) {
        this.clickListener = conversationItemClickListener;
    }

    public void updateData(List<Notification> notificationList) {
//        this.notificationList.addAll(notificationList);//Previous

        this.notificationList=notificationList;//now changes for search
        notifyDataSetChanged();
    }

    public void setData(List<Notification> notificationList) {
        this.notificationList = notificationList;
        notifyDataSetChanged();
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
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "convertDate", e);
        }

        return date;

    }

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
            improveHelper.improveException(context, TAG, "isFileExists", e);
        }
        return isFileExists;
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
            improveHelper.improveException(context, TAG, "setImageFromSDCard", e);
        }
    }

    public class ChatsViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout root;
        public TextView txtTitle;
        public TextView txtDesc;
        public TextView txtDate;
        public TextView txtUnreadMessages;
        public LinearLayout layout_unread;
        public ImageView iv_profile_pic;

        public ChatsViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.list_root);
            iv_profile_pic = itemView.findViewById(R.id.iv_circle);
            txtTitle = itemView.findViewById(R.id.list_title);
            txtDesc = itemView.findViewById(R.id.list_desc);
            txtDate = itemView.findViewById(R.id.list_date);
            txtUnreadMessages = itemView.findViewById(R.id.tv_unread_msgs);
            layout_unread = itemView.findViewById(R.id.layout_unread);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (clickListener != null)
                        clickListener.onItemIdClick(context, notificationList.get(getAdapterPosition()).getGroupIcon(), notificationList.get(getAdapterPosition()).getGroupId(), notificationList.get(getAdapterPosition()).getGroupName(), notificationList.get(getAdapterPosition()).getSessionId(), notificationList.get(getAdapterPosition()).getSessionName());
                }
            });

        }

        public void setTxtTitle(String string) {
            txtTitle.setText(string);
        }


        public void setTxtDesc(String string) {
            txtDesc.setText(string);
        }

        public void setTxtDate(String string) {
            txtDate.setText(string);
        }

        public void showUnread(String string) {
            layout_unread.setVisibility(View.VISIBLE);
            txtUnreadMessages.setText(string);
        }

        public void hideUnread(String string) {
            layout_unread.setVisibility(View.GONE);
            txtUnreadMessages.setText(string);
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
}
