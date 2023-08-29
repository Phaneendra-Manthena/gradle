package com.bhargo.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GroupClickListener;
import com.bhargo.user.pojos.firebase.Notification;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private static final String TAG = "NotificationsAdapter";
    private Context context;
    private List<Notification> notificationList;
    SessionManager sessionManager;
    GroupClickListener clickListener;
    BaseActivity baseActivity;
    ImproveHelper improveHelper;

    public NotificationsAdapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
        sessionManager = new SessionManager(context);
        improveHelper = new ImproveHelper(context);
        baseActivity = new BaseActivity();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Notification notification = notificationList.get(position);

        holder.title.setText(notification.getTitle());
        holder.message.setText(notification.getMessage());
        holder.time.setText(convertDate(notification.getTimeStamp()));



    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public void setCustomClickListener(GroupClickListener groupClickListener) {
        this.clickListener = groupClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CustomTextView title,message,time;// init the item view's
        View viewDivider;

        public ViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            title = itemView.findViewById(R.id.tv_title);
            message = itemView.findViewById(R.id.tv_message);
            time = itemView.findViewById(R.id.tv_time);
        }
    }


    public void updateNotificationsList(List<Notification> notificationList) {
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

}
