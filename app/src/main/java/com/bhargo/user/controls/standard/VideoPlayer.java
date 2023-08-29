package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.mScreenAwake;
import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;
import static com.bhargo.user.utils.ImproveHelper.showToast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.PathVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.screens.ExoPlayerActivity;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;

import wseemann.media.FFmpegMediaMetadataRetriever;


public class VideoPlayer implements PathVariables, UIVariables {

    private static final String TAG = "VideoPlayer";
    private final int VideoPlayerTAG = 0;
    public Activity context;
    public ControlObject controlObject;
    public LinearLayout linearLayout, ll_displayName,ll_main_inside,ll_control_ui,ll_tap_text,ll_videoPlayer_main;
    ImproveHelper improveHelper;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    View view;
    ImageView iv_videothumb;
    ImageView iv_videoplay;
    private CustomTextView tv_displayName, tv_hint,ct_showText;
    private ImageView iv_mandatory;


    private String strURlPath;

    public VideoPlayer(Activity context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        improveHelper = new ImproveHelper(context);

        initView();
    }

    public void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            addVideoPlayerStrip(context);

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getVideoPlayerView() {
        return linearLayout;
    }

    public void addVideoPlayerStrip(final Context context) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                    view = linflater.inflate(R.layout.control_video_player_six, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
                    view = linflater.inflate(R.layout.control_video_player_seven, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("8")) {
                    view = linflater.inflate(R.layout.control_video_player_eight, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {
                    // view = linflater.inflate(R.layout.layout_video_player_nine, null);
                    view = linflater.inflate(R.layout.control_video_player_default, null);
                }
            } else {
                view = linflater.inflate(R.layout.control_video_player_default, null);
            }
            view.setTag(VideoPlayerTAG);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            ll_tap_text = view.findViewById(R.id.ll_tap_text);
            ll_videoPlayer_main = view.findViewById(R.id.ll_videoPlayer_main);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            ct_showText = view.findViewById(R.id.ct_showText);
//            controlObject.setVideoData("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4");
           //controlObject.setVideoData("http://mirrors.standaloneinstaller.com/video-sample/jellyfish-25-mbps-hd-hevc.mkv");
            //defaultVideo();
            exPlayer();

            setTags();
            setControlValues();
            linearLayout.addView(view);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addVideoPlayerStrip", e);
        }
    }

    private void exPlayer() {

        view.findViewById(R.id.fl_video).setVisibility(View.GONE);
        view.findViewById(R.id.fl_videothumb).setVisibility(View.VISIBLE);

        iv_videothumb = view.findViewById(R.id.iv_videothumb);
        iv_videoplay = view.findViewById(R.id.iv_videoplay);

        iv_videoplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                        if (SubformFlag) {
                            AppConstants.SF_Container_position = SubformPos;
                            AppConstants.Current_ClickorChangeTagName = SubformName;

                        }
                        AppConstants.GlobalObjects.setCurrent_GPS("");
                        ((MainActivity) context).ChangeEvent(v);
                    }
                }
                if (controlObject.getVideoData() != null && !controlObject.getVideoData().isEmpty()) {
                    strURlPath = controlObject.getVideoData().trim();
                    controlObject.setText(strURlPath);
                    //strURlPath = "http://techslides.com/demos/sample-videos/small.mp4";
                    //strURlPath="https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4";
                    if (ImproveHelper.isNetworkStatusAvialable(context)) {
                        // Intent intent = new Intent(context, VideoPlayerActivity.class);
                        try{
                            Intent intent = new Intent(context, ExoPlayerActivity.class);
                            intent.putExtra("URL", strURlPath);
                            context.startActivity(intent);
                        }catch (Exception e){
                            showToast(context,e.getMessage());
                        }


                    } else {
                        showToast(context, context.getString(R.string.no_internet_available));
                    }
                } else {
//                    strURlPath = controlObject.getVideoData();
                    strURlPath = "No File";
                    showToast(context, "No File");
                }

            }
        });

        if (controlObject.getVideoData() != null && !controlObject.getVideoData().isEmpty()) {
            strURlPath = controlObject.getVideoData().trim();
            controlObject.setText(strURlPath);
            //strURlPath="http://techslides.com/demos/sample-videos/small.mp4";
            if(controlObject.getVideoData().startsWith("http")){
                /*Bitmap bitmap=createVideoThumbnail(strURlPath);
                if(bitmap!=null){
                    iv_videothumb.setImageBitmap(createVideoThumbnail(strURlPath));
                }*/
           }
        } else {
            iv_videothumb.setImageDrawable(context.getDrawable(R.drawable.video_state));
            strURlPath = "No File";
        }
    }

    private void defaultVideo() {
        MediaController mediaController;
        VideoView videoView;
        ImageView imageVideoPlay;
        view.findViewById(R.id.fl_video).setVisibility(View.VISIBLE);
        view.findViewById(R.id.fl_videothumb).setVisibility(View.GONE);
        final LinearLayout ll_videoPlayer = view.findViewById(R.id.ll_videoPlayer);
        videoView = view.findViewById(R.id.videoView);
        imageVideoPlay = view.findViewById(R.id.iv_videoPlay);
        mediaController = new MediaController(context);
        //defaultPlayer
        videoView.setTag(controlObject.getControlName());
        imageVideoPlay.setTag(controlObject.getControlName());
        mediaController.setTag(controlObject.getControlName());
        imageVideoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                    if (AppConstants.EventCallsFrom == 1) {
                        AppConstants.EventFrom_subformOrNot = SubformFlag;
                        if (SubformFlag) {
                            AppConstants.SF_Container_position = SubformPos;
                            AppConstants.Current_ClickorChangeTagName = SubformName;

                        }
                        AppConstants.GlobalObjects.setCurrent_GPS("");
                        ((MainActivity) context).ChangeEvent(v);
                    }
                }

                ll_videoPlayer.setVisibility(View.GONE);

                mediaController.setAnchorView(videoView);
                //Location of Media File
                //Starting VideView By Setting MediaController and URI

                videoView.setMediaController(mediaController);

//                videoView.setVideoPath("http://techslides.com/demos/sample-videos/small.mp4"); // Sample URL
//
//                if (controlObject.getVideoData() != null) {
//                    strURlPath = controlObject.getVideoData();
//                    videoView.setVideoPath(strURlPath);
//                }
                if (controlObject.getVideoData() != null && !controlObject.getVideoData().isEmpty()) {
                    strURlPath = controlObject.getVideoData().trim();
//                        strURlPath = "http://techslides.com/demos/sample-videos/small.mp4";
                } else {
//                    strURlPath = controlObject.getVideoData();
                    strURlPath = "No File";
                }
//                    videoView.setVideoPath(strURlPath);
                videoView.setVideoURI(Uri.parse(strURlPath));
                videoView.requestFocus();
                videoView.start();
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        ll_videoPlayer.setVisibility(View.VISIBLE);
                        if (controlObject.getVideoData() != null && !controlObject.getVideoData().isEmpty()) {
                            strURlPath = controlObject.getVideoData().trim();
//                                strURlPath = "http://techslides.com/demos/sample-videos/small.mp4";
                        } else {
//                    strURlPath = controlObject.getVideoData();
                            strURlPath = "No File";
                        }
                    }
                });
                Log.d("ServerVideoPath", strURlPath);
                 /*else {
                    Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.sample_video);
                    videoView.setVideoURI(uri);
                    videoView.start();
                    Toast.makeText(context, "No Video File found", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

    private Bitmap createVideoThumbnail(final String video) {
        Bitmap bitmap = null;
        FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
        try {
            mmr.setDataSource(video);
            mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ALBUM);
            mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ARTIST);
            bitmap = mmr.getFrameAtTime();//(2000000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST); // frame at 2 seconds
            byte[] artwork = mmr.getEmbeddedPicture();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                mmr.release();
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        }

        return bitmap;
    }

    private void setControlValues() {
        try {
            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            if (controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                setHint(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }
            if (controlObject.isEnableStayAwake()) {
                mScreenAwake(context, TAG);
                Log.d("isEnableStayAwake", String.valueOf(controlObject.isEnableStayAwake()));
            }
            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            }

            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());


        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    private void setTags() {
        try {
            iv_videothumb.setTag(controlObject.getControlName());
            iv_videoplay.setTag(controlObject.getControlName());

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setTags", e);
        }
    }

    @Override
    public String getPath() {
        return controlObject.getVideoData();
    }

    @Override
    public void setPath(String path) {
        controlObject.setVideoData(path);
    }

    @Override
    public boolean getVisibility() {
        controlObject.setInvisible(linearLayout.getVisibility() != View.VISIBLE);
        return controlObject.isInvisible();
    }

    @Override
    public void setVisibility(boolean visibility) {

        if (visibility) {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        } else {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }
    }

    @Override
    public boolean isEnabled() {

        return !controlObject.isDisable();
    }

    @Override
    public void setEnabled(boolean enabled) {
//        setViewDisable(view, !enabled);
        controlObject.setDisable(!enabled);
        setViewDisableOrEnableDefault(context,view, enabled);
    }

    @Override
    public void clean() {

    }

    @Override
    public String getTextSize() {
        return controlObject.getTextSize();
    }

    @Override
    public void setTextSize(String size) {
        if (size != null) {
            controlObject.setTextSize(size);
            tv_displayName.setTextSize(Float.parseFloat(size));
        }

    }

    @Override
    public String getTextStyle() {
        return controlObject.getTextStyle();
    }

    @Override
    public void setTextStyle(String style) {
        if (style != null && style.equalsIgnoreCase("Bold")) {
            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
            tv_displayName.setTypeface(typeface_bold);
            controlObject.setTextStyle(style);
        } else if (style != null && style.equalsIgnoreCase("Italic")) {
            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
            tv_displayName.setTypeface(typeface_italic);
            controlObject.setTextStyle(style);
        }
    }

    @Override
    public String getTextColor() {
        return controlObject.getTextHexColor();
    }

    @Override
    public void setTextColor(String color) {
        if (color != null && !color.equalsIgnoreCase("")) {
            controlObject.setTextHexColor(color);
            controlObject.setTextColor(Color.parseColor(color));
            tv_displayName.setTextColor(Color.parseColor(color));
        }
    }

    public String getDisplayName() {
        return controlObject.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        tv_displayName.setText(displayName);
        controlObject.setDisplayName(displayName);
    }

    public String getHint() {
        return controlObject.getHint();
    }

    public void setHint(String hint) {
        if (hint != null && !hint.isEmpty()) {
            tv_hint.setVisibility(View.VISIBLE);
            tv_hint.setText(hint);
        } else {
            tv_hint.setVisibility(View.GONE);
        }
        controlObject.setHint(hint);
    }

    /*General*/
    /*displayName,hint,uploadedFilePath*/

    public String getUploadedFilePath() {
        return controlObject.getUploadedFilePath();
    }

    public void setUploadedFilePath(String uploadedFilePath) {
        controlObject.setUploadedFilePath(uploadedFilePath);
    }

    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        if (hideDisplayName) {
            ll_displayName.setVisibility(View.GONE);
        } else {
            ll_displayName.setVisibility(View.VISIBLE);
        }
    }

    public boolean isEnableStayAwake() {
        return controlObject.isEnableStayAwake();
    }

    public void setEnableStayAwake(boolean enableStayAwake) {
        controlObject.setEnableStayAwake(enableStayAwake);
        if (enableStayAwake) {
            mScreenAwake(context, TAG);
        }

    }

    /*Options*/
    /*hideDisplayName,enableStayAwake
    invisible/visibility
     */
    /*ControlUI Settings*/
    public LinearLayout getLl_main_inside(){
        return ll_main_inside;
    }
    public LinearLayout getLl_control_ui(){
        return ll_control_ui;
    }
    public LinearLayout getLl_tap_text(){
        return ll_tap_text;
    }
    public LinearLayout getLl_videoPlayer_main(){
        return ll_videoPlayer_main;
    }
    public CustomTextView getTv_displayName(){
        return tv_displayName;
    }
    /*ControlUI Settings*/
    public ControlObject getControlObject() {
        return controlObject;
    }
    @Override
    public void showMessageBelowControl(String msg) {
        if(msg != null && !msg.isEmpty()) {
            ct_showText.setVisibility(View.VISIBLE);
            ct_showText.setText(msg);
        }else{
            ct_showText.setVisibility(View.GONE);
        }
    }

}
