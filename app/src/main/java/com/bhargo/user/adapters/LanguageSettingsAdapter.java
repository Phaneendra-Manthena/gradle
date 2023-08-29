package com.bhargo.user.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.LanguageTypeModel;
import com.bhargo.user.screens.LoginActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

public class LanguageSettingsAdapter extends RecyclerView.Adapter<LanguageSettingsAdapter.LngViewHolder> {

    private static final String TAG = "CommentsAdapter";
    public ItemClickListenerL clickListener;
    Context context;
    List<LanguageTypeModel> languageTypeModelsList;
    SessionManager sessionManager;
    int selecteditem = 0;
    BottomSheetBehavior sheetBehavior;
    String appLanguage = "en";


    public LanguageSettingsAdapter(Context context, List<LanguageTypeModel> languageTypeModelsList, BottomSheetBehavior sheetBehavior) {
        this.context = context;
        this.languageTypeModelsList = languageTypeModelsList;
        this.sheetBehavior = sheetBehavior;
        sessionManager = new SessionManager(context);
        appLanguage = ImproveHelper.getLocale(context);
    }

    @NonNull
    @Override
    public LanguageSettingsAdapter.LngViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.language_settings_item, parent, false);
        // set the view's size, margins, paddings and layout_sample_app parameters
        LanguageSettingsAdapter.LngViewHolder vh = new LanguageSettingsAdapter.LngViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageSettingsAdapter.LngViewHolder holder, int position) {

        if (appLanguage != null) {
            if (appLanguage.contentEquals("en")) {
                selecteditem = 0;
            } else if (appLanguage.contentEquals("hi")) {
                selecteditem = 1;
            } else if (appLanguage.contentEquals("te")) {
                selecteditem = 2;
            } else if (appLanguage.contentEquals("ta")) {
                selecteditem = 3;
            } else if (appLanguage.contentEquals("mr")) {
                selecteditem = 4;
            }  else if (appLanguage.contentEquals("kn")) {
                selecteditem = 5;
            }else if (appLanguage.contentEquals("si")) {
                selecteditem = 6;
            } else {
                selecteditem = 0;
            }
        } else {
            selecteditem = 0;
        }

        holder.ctvLanguage.setText(languageTypeModelsList.get(position).getLanguage());
        holder.ctvLanguageHint.setText(languageTypeModelsList.get(position).getLanguageHint());
        if (selecteditem == position) {
            holder.iv_languageSelected.setVisibility(View.VISIBLE);
        } else {
            holder.iv_languageSelected.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {

        return languageTypeModelsList.size();

    }

    public void setClickListenerL(LanguageSettingsAdapter.ItemClickListenerL itemClickListener) {
        this.clickListener = itemClickListener;
    }
//public void refreshItems(List<TaskCmtDataModel> todolist){
//        this.taskCmtDataModelList.addAll(todolist);
////        Collections.reverse(taskCmtDataModelList);
//        notifyDataSetChanged();
//    }

    public interface ItemClickListenerL {
        void onItemClickL(View view, int position);
    }

    public class LngViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_languageSelected;
        CustomTextView ctvLanguage, ctvLanguageHint;

        public LngViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_languageSelected = itemView.findViewById(R.id.iv_languageSelected);
            ctvLanguage = itemView.findViewById(R.id.ctvLanguage);
            ctvLanguageHint = itemView.findViewById(R.id.ctvLanguageHint);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            selecteditem = getAdapterPosition();
            notifyDataSetChanged();
            if (selecteditem == 0) {
                ImproveHelper.saveLocale(context, AppConstants.LANG_ENGLISH);
                ImproveHelper.changeLanguage(context, AppConstants.LANG_ENGLISH);
            } else if (selecteditem == 1) {
                ImproveHelper.saveLocale(context, AppConstants.LANG_HINDI);
                ImproveHelper.changeLanguage(context, AppConstants.LANG_HINDI);
            } else if (selecteditem == 2) {
                ImproveHelper.saveLocale(context, AppConstants.LANG_TELUGU);
                ImproveHelper.changeLanguage(context, AppConstants.LANG_TELUGU);
            } else if (selecteditem == 3) {
                ImproveHelper.saveLocale(context, AppConstants.LANG_TAMIL);
                ImproveHelper.changeLanguage(context, AppConstants.LANG_TAMIL);
            } else if (selecteditem == 4) {
                ImproveHelper.saveLocale(context, AppConstants.LANG_MARATHI);
                ImproveHelper.changeLanguage(context, AppConstants.LANG_MARATHI);
            } else if (selecteditem == 5) {
                ImproveHelper.saveLocale(context, AppConstants.LANG_KANNADA);
                ImproveHelper.changeLanguage(context, AppConstants.LANG_KANNADA);
            } else if (selecteditem == 6) {
                ImproveHelper.saveLocale(context, AppConstants.LANG_SINHALA);
                ImproveHelper.changeLanguage(context, AppConstants.LANG_SINHALA);
            } else {
                ImproveHelper.saveLocale(context, AppConstants.LANG_ENGLISH);
                ImproveHelper.changeLanguage(context, AppConstants.LANG_ENGLISH);
            }
            sessionManager.languageChanged(true);
            sessionManager.activeFragment("A");
            ImproveHelper.showHideBottomSheet(sheetBehavior);
            context.startActivity(new Intent(context, LoginActivity.class));
            ((Activity) context).finish();

        }
    }

}