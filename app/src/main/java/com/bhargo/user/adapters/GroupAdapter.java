package com.bhargo.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GroupClickListener;
import com.bhargo.user.pojos.firebase.Group;
import com.bhargo.user.utils.SessionManager;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    DatabaseReference mFirebaseDatabaseReferencePeople;
    private static final String TAG = "UserAdapter";
    private Context context;
    private List<Group> mUsers;
    int selectedPos;
    SessionManager sessionManager;
    GroupClickListener clickListener;

    public GroupAdapter(Context context, List<Group> mUsers) {
        this.context = context;
        this.mUsers = mUsers;
        sessionManager = new SessionManager(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Group group = mUsers.get(position);

        holder.name.setText(group.getGroupName());

        if (group.getGroupIcon() != null) {
            if (group.getGroupIcon().equals("default")) {
                holder.iv_circle.setImageResource(R.drawable.user);
            } else {
                Glide.with(context).load(group.getGroupIcon()).into(holder.iv_circle);
            }
        } else {
            holder.iv_circle.setImageResource(R.drawable.user);
        }


    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void setCustomClickListener(GroupClickListener groupClickListener) {
        this.clickListener = groupClickListener;
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

//                    getUsersOfGroupFromGroup(mUsers.get(getAdapterPosition()).getID(), mUsers.get(getAdapterPosition()).getName(), mUsers.get(getAdapterPosition()).getImagePath());
                    getUsersOfGroupFromUser(mUsers.get(getAdapterPosition()).getGroupID(), mUsers.get(getAdapterPosition()).getGroupName(), mUsers.get(getAdapterPosition()).getGroupIcon());
                }
            });
        }


    }

    private void getUsersOfGroupFromUser(String id, String name, String group_icon) {

        if (clickListener != null)
            clickListener.onGroupIdClick(context, group_icon, id, name);
    }

    public void updateUserData(List<Group> userDetailsList) {
        mUsers = userDetailsList;
        notifyDataSetChanged();
    }


}
