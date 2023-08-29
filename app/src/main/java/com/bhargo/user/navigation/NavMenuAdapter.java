package com.bhargo.user.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NavMenuAdapter extends RecyclerView.Adapter<NavMenuAdapter.ViewHolder> {

    private static final String TAG = "NavMenuAdapter";
    private final MenuClickListener menuClickListener;
    private final List<NavMenuItem> menuItemList;
    private final Context context;

    public NavMenuAdapter(List<NavMenuItem> menuItemList, Context context, MenuClickListener menuClickListener) {
        this.menuItemList = menuItemList;
        this.context = context;
        this.menuClickListener = menuClickListener;
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

        if ((menuItemList.size() - 1) == position) {
            holder.navItemDividerView.setVisibility(View.GONE);
        }
        NavMenuItem menu = menuItemList.get(position);

        holder.menu_text.setText(menu.getDisplayName());

        if (menu.getSubMenuItems() != null && menu.getSubMenuItems().size() > 0) {
            holder.btn_sub_menu.setVisibility(View.VISIBLE);
        } else {
            holder.btn_sub_menu.setVisibility(View.GONE);
        }

        if (AppConstants.ITEM_MAIN_MENU_POSITION != -1) {
            if (AppConstants.ITEM_MAIN_MENU_POSITION == position) {
                subMenuIconWidthHeightBG(holder, true);
                if (AppConstants.ITEM_SUB_MENU_POSITION != -1) {
                    subMenuViews(holder.rv_list, menu, holder, AppConstants.ITEM_MAIN_MENU_POSITION);
                }
            }
        }

        // MainMenu Items Click
        holder.itemView.setOnClickListener(view -> {
            try {
                AppConstants.ITEM_MAIN_MENU_POSITION = holder.getAdapterPosition();
                if (holder.getAdapterPosition() == position) {
                    subMenuIconWidthHeightBG(holder, true);
                    for (int i = 0; i < menuItemList.size(); i++) {
                        if (i != AppConstants.ITEM_MAIN_MENU_POSITION) {
                            subMenuIconWidthHeightBG(holder, false);
                        }
                    }

                } else {
                    subMenuIconWidthHeightBG(holder, false);
                }
                if (menu.getOnClickEventObject() != null) {
                    menuClickListener.onMenuClick(holder.menu_text, menuItemList, holder.getAdapterPosition());
                } else {
                    subMenuIconWidthHeightBG(holder, true);
                    // SubMenu Views Visible
                    subMenuViews(holder.rv_list, menu, holder, holder.getAdapterPosition());
                }
            } catch (Exception e) {
                Log.d(TAG, "onBindViewHolder: " + e.getStackTrace());
            }
        });

        // Icon Click
        holder.btn_sub_menu.setOnClickListener(view -> {
            subMenuIconWidthHeightBG(holder, holder.getAdapterPosition() == position);
            // SubMenu Views Visible
            subMenuViews(holder.rv_list, menu, holder, holder.getAdapterPosition());

        });

        if (menu.getMenuIcon() != null && !menu.getMenuIcon().contentEquals("")) {
            Glide.with(context).load(menu.getMenuIcon()).into(holder.menu_icon);
        } else {
            holder.menu_icon.setVisibility(View.GONE);
        }

    }

    private void subMenuViews(RecyclerView rv_list, NavMenuItem menu, ViewHolder holder, int adapterPosition) {
        if (rv_list.getVisibility() == View.GONE) {
            List<NavMenuItem> subMenuItemsList = new ArrayList<>();
            if (menu.getSubMenuItems() != null && menu.getSubMenuItems().size() > 0) {
                subMenuItemsList = menu.getSubMenuItems();

                rv_list.setVisibility(View.VISIBLE);

                rv_list.setLayoutManager(new LinearLayoutManager(context));

                NavSubMenuAdapter adapter = new NavSubMenuAdapter(subMenuItemsList, context, (view1, menuItemList, subMenuPos) -> menuClickListener.onSubMenuClick(view1, menuItemList, subMenuPos, adapterPosition));

                rv_list.setAdapter(adapter);
                rv_list.setHasFixedSize(true);
            }
        } else {
            rv_list.setVisibility(View.GONE);
            subMenuIconWidthHeightBG(holder, false);

        }
    }

    @Override
    public int getItemCount() {
        return menuItemList.size();
    }

    public void subMenuIconWidthHeightBG(ViewHolder holder, Boolean isItemClicked) {

        android.view.ViewGroup.LayoutParams layoutParamsImg = holder.btn_sub_menu.getLayoutParams();
        layoutParamsImg.width = pxToDP(20);
        layoutParamsImg.height = pxToDP(20);
        holder.btn_sub_menu.setLayoutParams(layoutParamsImg);
        if (isItemClicked) {
            holder.row_linearlayout.setBackgroundColor(context.getResources().getColor(R.color.item_selected_bg));
            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi_bold));
            holder.menu_text.setTypeface(typeface_bold);
            holder.menu_text.setTextColor(context.getResources().getColor(R.color.otp_blue));
            holder.btn_sub_menu.setBackground(context.getResources().getDrawable(R.drawable.ic_up_arrow_default));
            holder.btn_sub_menu.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.otp_blue)));

        } else {
            holder.row_linearlayout.setBackgroundColor(context.getResources().getColor(R.color.white));
            Typeface typeface_normal = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_satoshi));
            holder.menu_text.setTypeface(typeface_normal);
            holder.menu_text.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.btn_sub_menu.setBackground(context.getResources().getDrawable(R.drawable.ic_down_arrow_default));
            holder.btn_sub_menu.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.text_color)));
        }

    }

    public int pxToDP(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics());
    }

    public interface MenuClickListener {
        void onMenuClick(View view, List<NavMenuItem> menuItemList, int pos);

        void onSubMenuClick(View view, List<NavMenuItem> menuItemList, int subMenuPos, int menuPos);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout row_linearlayout;
        View navItemDividerView;
        CircleImageView menu_icon;
        ImageView btn_sub_menu;
        CustomTextView menu_text;
        RecyclerView rv_list;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            row_linearlayout = itemView.findViewById(R.id.row_linearlayout);
            navItemDividerView = itemView.findViewById(R.id.navItemDividerView);
            menu_icon = itemView.findViewById(R.id.iv_menu_icon);
            btn_sub_menu = itemView.findViewById(R.id.btn_sub_menu);
            menu_text = itemView.findViewById(R.id.ct_menu);
            rv_list = itemView.findViewById(R.id.rv_list);

        }
    }

}
