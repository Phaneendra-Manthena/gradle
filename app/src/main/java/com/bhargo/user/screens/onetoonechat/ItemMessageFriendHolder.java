package com.bhargo.user.screens.onetoonechat;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;

class ItemMessageFriendHolder extends RecyclerView.ViewHolder {

    public TextView txtContent;
    public TextView txtDate;
    public TextView txtType;
    public TextView tv_file_name;
    public TextView tv_stick_date;
    public TextView tv_seen;
    public CustomTextView txtFileName;
    public ImageButton ib_playpause;
    public SeekBar seekBarAudio;
    public LinearLayout layout_others;
    public LinearLayout layout_audio;
    public LinearLayout layout_message;
    public LinearLayout layout_stick_date;
    public RelativeLayout layout_file_item;
    public RelativeLayout layout_text_item;
    public FrameLayout iv_filedownload;
    public LinearLayout iv_audiofiledownload;
    public ImageView iv_filetype;
    public ProgressBar pb_download_status;
    public ProgressBar pb_audio_download_status;

    private ItemMessageFriendHolder.IClickListener iClickListener;


    public ItemMessageFriendHolder(View itemView) {
        super(itemView);
        txtContent = itemView.findViewById(R.id.textContentFriend);
        txtDate = (TextView) itemView.findViewById(R.id.tv_msg_date);
        txtType = (TextView) itemView.findViewById(R.id.tv_msg_type);
        tv_file_name = (TextView) itemView.findViewById(R.id.tv_file_name);
        layout_file_item = itemView.findViewById(R.id.layout_file_item);
        layout_text_item = itemView.findViewById(R.id.layout_text_item);
        iv_filedownload = itemView.findViewById(R.id.iv_filedownload);
        iv_filetype = itemView.findViewById(R.id.iv_filetype);
        layout_stick_date = itemView.findViewById(R.id.layout_stick_date);
        layout_others = itemView.findViewById(R.id.layout_others);
        layout_audio = itemView.findViewById(R.id.layout_audio);
        tv_stick_date = itemView.findViewById(R.id.list_item_section_text);
        txtFileName = itemView.findViewById(R.id.txtFileName);
        ib_playpause = itemView.findViewById(R.id.btnPlay);
        seekBarAudio = itemView.findViewById(R.id.seekBarAudio);
        layout_message = itemView.findViewById(R.id.layout_message_click);
        // seekBarAudio.setTag("New");
        pb_download_status = itemView.findViewById(R.id.pb_download_status);
        iv_audiofiledownload = itemView.findViewById(R.id.iv_audiofiledownload);
        pb_audio_download_status = itemView.findViewById(R.id.pb_audio_download_status);
        tv_seen = itemView.findViewById(R.id.tv_seen);
        ib_playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickListener.onItemClickedAudio(getAdapterPosition(), itemView);
            }
        });

        layout_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickListener.onItemClickedImage(getAdapterPosition());
            }
        });


    }

    void setIClickListener(ItemMessageFriendHolder.IClickListener iClickListener) {
        this.iClickListener = iClickListener;
    }

    public interface IClickListener {
        void onItemClickedAudio(int position, View v);

        void onItemClickedImage(int position);
    }
}
