package com.bhargo.user.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.firebase.UserList;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

    private static final String TAG = "UsersAdapter";
    List<UserList> userLists;
    Context context;
    SessionManager sessionManager;
int selectedPos;

    public UsersAdapter(Context context, List<UserList> userLists) {
        this.context = context;
        this.userLists = userLists;
        sessionManager = new SessionManager(context);



    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        // set the view's size, margins, paddings and layout_sample_app parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        final UserList userList = userLists.get(position);
        holder.name.setText(userList.getName());


        if (userList.getType().equalsIgnoreCase("G")) {
            Glide.with(context).load(userList.getIcon()).placeholder(context.getResources().getDrawable(R.drawable.ic_group_black_24dp)).into(holder.iv_circle);
        } else {
            holder.desc.setText(userList.getDesignation());
            Glide.with(context).load(userList.getIcon()).placeholder(context.getResources().getDrawable(R.drawable.ic_person_black_36dp)).into(holder.iv_circle);
        }

       /* if (AppsDetails.getAppIcon() != null) {

            if (!AppsDetails.getAppIcon().equalsIgnoreCase("NA")) {
                if (isNetworkStatusAvialable(context)) {
                    Glide.with(context).load(AppsDetails.getAppIcon()).placeholder(context.getResources().getDrawable(R.drawable.allapps)).into(holder.iv_circle);


                } else {
                    String appName = AppsDetails.getAppName();
                    String replaceWithUnderscore = appName.replaceAll(" ", "_");
                    String[] imgUrlSplit = AppsDetails.getAppIcon().split("/");
                    String strSDCardUrl = "/Improve_User/" + replaceWithUnderscore + "/" + imgUrlSplit[imgUrlSplit.length - 1];
                    setImageFromSDCard(holder, strSDCardUrl);
                }
            } else {
                Glide.with(context).clear(holder.iv_circle);
                // remove the placeholder (optional); read comments below
                holder.iv_circle.setImageDrawable(null);
            }
        }*/

       if(selectedPos == position){
           holder.iv_check.setVisibility(View.VISIBLE);
       }

        holder.bind(userList, userLists);
    }

    @Override
    public int getItemCount() {
        return userLists.size();
    }

    public void setImageFromSDCard(MyViewHolder holder, String strImagePath) {

        File imgFile = new File(Environment.getExternalStorageDirectory(), strImagePath);

        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            holder.iv_circle.setImageBitmap(myBitmap);

        }
    }

    public void updateList(List<UserList> list) {
        userLists = list;
        notifyDataSetChanged();
    }

    public List<UserList> getAll() {
        return userLists;
    }

    public List<UserList> getSelected() {
        List<UserList> selected = new ArrayList<>();
        for (int i = 0; i < userLists.size(); i++) {
            if (userLists.get(i).isChecked()) {
                selected.add(userLists.get(i));
            }
        }
        return selected;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //        TextView name;
        CustomTextView name, desc;// init the item view's
        ImageView iv_check, iv_circle;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = itemView.findViewById(R.id.tvUserName);
            desc = itemView.findViewById(R.id.tvAppDesc);
            iv_check = itemView.findViewById(R.id.iv_check);
            iv_circle = itemView.findViewById(R.id.iv_circle);


        }


        void bind(final UserList employee, List<UserList> userLists) {
            iv_check.setVisibility(employee.isChecked() ? View.VISIBLE : View.GONE);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifyItemChanged(selectedPos);
                    selectedPos = getLayoutPosition();
                    notifyItemChanged(selectedPos);
                    for (UserList userList : userLists) {
                        userList.setChecked(false);
                    }
                    employee.setChecked(!employee.isChecked());
//                    iv_check.setVisibility(employee.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }

    }


}
