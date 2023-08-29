package com.bhargo.user.navigation;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NavSubMenuAdapter extends RecyclerView.Adapter<NavSubMenuAdapter.ViewHolder>{

    private final SubMenuClickListener subMenuClickListener;
    private final List<NavMenuItem> subMenuItemList;
    private final Context context;

    public NavSubMenuAdapter(List<NavMenuItem> subMenuItemList, Context context,SubMenuClickListener subMenuClickListener) {
        this.subMenuItemList = subMenuItemList;
        this.context = context;
        this.subMenuClickListener = subMenuClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navigation_new, parent, false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navigation_old, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NavMenuItem subMenu = subMenuItemList.get(position);
        holder.menu_text.setText(subMenu.getDisplayName());
        if(subMenu.getMenuIcon()!=null&&!subMenu.getMenuIcon().contentEquals("")) {
            Glide.with(context).load(subMenu.getMenuIcon()).into(holder.menu_icon);
        }else {
            holder.menu_icon.setVisibility(View.GONE);
        }

        if(AppConstants.ITEM_SUB_MENU_POSITION !=-1){
            if(AppConstants.ITEM_SUB_MENU_POSITION== position) {
                itemBG(holder.row_linearlayout, holder.menu_text);
            }
        }


        holder.itemView.setOnClickListener(view -> {
            AppConstants.ITEM_SUB_MENU_POSITION = position;
            if(holder.getAdapterPosition()==position){
                itemBG(holder.row_linearlayout,holder.menu_text);
            } else {
                holder.row_linearlayout.setBackgroundColor(Color.parseColor("#ffffff"));
                Typeface typeface_normal = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi));
                holder.menu_text.setTypeface(typeface_normal);
            }
            subMenuClickListener.onSubMenuClick(holder.menu_text, subMenuItemList, holder.getAdapterPosition());
        });

    }

    private void itemBG(LinearLayout rowLinearLayout, CustomTextView menuText) {
        rowLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.item_selected_bg));
        Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_bold));
        menuText.setTypeface(typeface_bold);
        menuText.setTextColor(context.getResources().getColor(R.color.otp_blue));
    }

    @Override
    public int getItemCount() {
        return subMenuItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView menu_icon;
        LinearLayout row_linearlayout;
        ImageView btn_sub_menu;
        CustomTextView menu_text;
        RecyclerView rv_list;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            row_linearlayout = itemView.findViewById(R.id.row_linearlayout);
            menu_icon = itemView.findViewById(R.id.iv_menu_icon);
            btn_sub_menu = itemView.findViewById(R.id.btn_sub_menu);
            menu_text = itemView.findViewById(R.id.ct_menu);
            rv_list = itemView.findViewById(R.id.rv_list);

            btn_sub_menu.setVisibility(View.GONE);
        }
    }

    public interface SubMenuClickListener{
        void onSubMenuClick(View view,List<NavMenuItem> menuItemList,int subMenuPos);
    }
}
