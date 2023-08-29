package com.bhargo.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.PostsMasterModel;
import com.bhargo.user.utils.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class PostChangeAdapter extends RecyclerView.Adapter<PostChangeAdapter.LngViewHolder> {

    private static final String TAG = "CommentsAdapter";
    Context context;
//    List<Post> postList;
    List<PostsMasterModel> postList;
    SessionManager sessionManager;
    BottomSheetBehavior sheetBehavior;
    String postID = null;
    String userTypeId = null;
//    Post post = null;
    PostsMasterModel post = null;
     ItemClickListenerPostChange clickListener;

//    public PostChangeAdapter(Context context, List<Post> postList, BottomSheetBehavior sheetBehavior) {
    public PostChangeAdapter(Context context, List<PostsMasterModel> postList,
                             BottomSheetBehavior sheetBehavior,SessionManager sessionManager) {
        this.context = context;
        this.postList = postList;
        this.sheetBehavior = sheetBehavior;
        this.sessionManager = sessionManager;
        postID = sessionManager.getPostsFromSession();
        userTypeId = sessionManager.getUserTypeIdsFromSession();
    }

    @NonNull
    @Override
    public PostChangeAdapter.LngViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.language_settings_item, parent, false);
        // set the view's size, margins, paddings and layout_sample_app parameters
        PostChangeAdapter.LngViewHolder vh = new PostChangeAdapter.LngViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PostChangeAdapter.LngViewHolder holder, int position) {
        holder.ctvPost.setText(postList.get(position).getName());
        if(postID != null && postID.equalsIgnoreCase(postList.get(position).getID())){
            holder.iv_postSelected.setVisibility(View.VISIBLE);
        }else {
            holder.iv_postSelected.setVisibility(View.GONE);
        }
    }

    private boolean verifyUserTypes(String id,String userTypeId,String tag) {

        if(id.equalsIgnoreCase("-1")){
            if(userTypeId != null && userTypeId.equalsIgnoreCase(tag)){
                return true;
            }
        }else if(!id.equalsIgnoreCase("-1")){
            if(id != null && id.equalsIgnoreCase(tag)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {

        return postList.size();

    }
    public void updateList(List<PostsMasterModel> list) {
        postList = new ArrayList<>();
        postList = list;
        notifyDataSetChanged();
    }



    public class LngViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_postSelected;
        CustomTextView ctvPost,ctvLanguageHint;

        public LngViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_postSelected = itemView.findViewById(R.id.iv_languageSelected);
            ctvPost = itemView.findViewById(R.id.ctvLanguage);
            ctvLanguageHint = itemView.findViewById(R.id.ctvLanguageHint);
            ctvLanguageHint.setVisibility(View.GONE);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            postID =   postList.get(getAdapterPosition()).getID();
            post =   postList.get(getAdapterPosition());
            notifyDataSetChanged();
            changePost();

        }
    }

    private void changePost() {
        if (post != null &&  sessionManager.getPostsFromSession()!=null && !sessionManager.getPostsFromSession().equalsIgnoreCase(post.getID())) {
            if (clickListener != null){
                clickListener.onCustomClick(1,post);
            }
        } else {
            if (clickListener != null){
                clickListener.onCustomClick(0,post);
            }
        }
    }

    public PostsMasterModel getPostID(){
       return post;
    }

    public String getItemType(int id) {
        return postList.get(id).getUserType();
    }

    public String getItemTypeId(int id) {
        return postList.get(id).getID();
    }


//    public PostsMasterModel getPostID(){
//       return post;
//    }
/*
    public Post getPostID(){
       return post;
    }
*/

    public interface ItemClickListenerPostChange {

        void onCustomClick(int value,PostsMasterModel post);
    }

    public void setCustomClickListener(ItemClickListenerPostChange itemClickListener) {
        this.clickListener = itemClickListener;
    }


}