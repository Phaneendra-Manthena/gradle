package com.bhargo.user.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.firebase.UserDetails;
import com.bhargo.user.screens.groupchat.GroupChatActivity;
import com.bhargo.user.screens.onetoonechat.ChatActivity;
import com.bumptech.glide.Glide;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    private static final String TAG = "UserAdapter";
    private Context context;
    private List<UserDetails> mUsers;
    int selectedPos;

    public PeopleAdapter(Context context, List<UserDetails> mUsers) {
        this.context = context;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);

        return new PeopleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        UserDetails user = mUsers.get(position);

        Log.d(TAG, "onBindViewHolder: " + user.getID());
        holder.name.setText(user.getName());
        if (user.getImagePath().equals("default")) {
            holder.iv_circle.setImageResource(R.drawable.user);

        } else {
            Glide.with(context).load(user.getImagePath()).into(holder.iv_circle);
        }

        if (position == mUsers.size() - 1) {
            holder.viewDivider.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CustomTextView name, desc;// init the item view's
        ImageView iv_circle;
        View viewDivider;

        public ViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = itemView.findViewById(R.id.tvUserName);
            desc = itemView.findViewById(R.id.tvAppDesc);
            iv_circle = itemView.findViewById(R.id.iv_circle);
            viewDivider = itemView.findViewById(R.id.viewDivider);
            desc.setVisibility(View.GONE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                List<UserDetails> userDetailsList = new ArrayList<>();
                userDetailsList.add(mUsers.get(getAdapterPosition()));
                    openChatActivity(userDetailsList,"","");
                }
            });
        }


    }

    public void updateUserData(List<UserDetails> userDetailsList) {
        mUsers = userDetailsList;
        notifyDataSetChanged();
    }

    private void openChatActivity(List<UserDetails> chatUsersList, String name, String id) {

        if (chatUsersList.size() > 1) {
            Intent i = new Intent(context, GroupChatActivity.class);

            i.putExtra("UsersList", (Serializable) chatUsersList);
            i.putExtra("GroupName", name);
            i.putExtra("GroupId", id);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(i);
            ((Activity)context).finish();

        } else {
            Intent i = new Intent(context, ChatActivity.class);

            i.putExtra("UsersList", (Serializable) chatUsersList);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(i);
            ((Activity)context).finish();
        }


    }

}
