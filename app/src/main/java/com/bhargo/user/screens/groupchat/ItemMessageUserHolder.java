package com.bhargo.user.screens.groupchat;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;


public class ItemMessageUserHolder extends RecyclerView.ViewHolder {
    public TextView txtContent;
    public TextView txtDate;
    public TextView txtType;
    public TextView tv_file_name;
    public TextView tv_stick_date;
    public TextView tv_seen;
    public LinearLayout layout_stick_date;
    public LinearLayout layout_other_file;
    public RelativeLayout layout_file_item;
    public RelativeLayout layout_text_item;
    public FrameLayout layout_image_file;
    public ProgressBar pb_file;
    public ProgressBar pb_imagefile;
    public ImageView iv_filetype;
    public ImageView iv_imagefile;
    private IClickListener iClickListener;

    public ItemMessageUserHolder(View itemView) {
        super(itemView);
        txtContent = itemView.findViewById(R.id.textContentUser);
        txtDate = (TextView) itemView.findViewById(R.id.tv_msg_date);
        txtType = (TextView) itemView.findViewById(R.id.tv_msg_type);
        tv_file_name = (TextView) itemView.findViewById(R.id.tv_file_name);
        layout_file_item = itemView.findViewById(R.id.layout_file_item);
        layout_text_item = itemView.findViewById(R.id.layout_text_item);
        layout_other_file = itemView.findViewById(R.id.ll_otherfiles);
        layout_image_file = itemView.findViewById(R.id.fl_imagefile);
        iv_imagefile = itemView.findViewById(R.id.iv_imagefile);
        pb_imagefile = itemView.findViewById(R.id.pb_imagefile);
        pb_file = itemView.findViewById(R.id.pb_file);
        iv_filetype = itemView.findViewById(R.id.iv_filetype);
        layout_stick_date = itemView.findViewById(R.id.layout_stick_date);
        tv_stick_date = itemView.findViewById(R.id.list_item_section_text);
        tv_seen = itemView.findViewById(R.id.tv_seen);
        tv_seen.setVisibility(View.GONE);
        layout_file_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickListener.onItemClickedImage(getAdapterPosition());
            }
        });


    }

    void setIClickListener(IClickListener iClickListener) {
        this.iClickListener = iClickListener;
    }

    public interface IClickListener {
        void onItemClickedAudio(int position, View v);

        void onItemClickedImage(int position);
    }
}
