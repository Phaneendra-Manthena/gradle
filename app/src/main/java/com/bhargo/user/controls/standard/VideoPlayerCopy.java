package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.mScreenAwake;
import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import android.content.Context;
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
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;

public class VideoPlayerCopy implements PathVariables, UIVariables {

    private static final String TAG = "VideoPlayer";
    public Context context;
    public ControlObject controlObject;
    public LinearLayout linearLayout,ll_displayName;
    public ImageView imageVideoPlay;
    ImproveHelper improveHelper;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    View view;
    private final int VideoPlayerTAG = 0;
    private CustomTextView tv_displayName, tv_hint;
    private ImageView iv_mandatory;
    private MediaController mediaController;
    private VideoView videoView;
    private String strURlPath;

    public VideoPlayerCopy(Context context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
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
//
//        FrameLayout frameLayout = new FrameLayout(context);
//        LinearLayout llvPlayer = new LinearLayout(context);
//        LinearLayout llImg = new LinearLayout(context);
//
//        imageVideoPlay = new ImageView(context);
//        videoView = new VideoView(context);
//        videoView.setLayoutParams(new FrameLayout.LayoutParams(1000, 1000));
//        //specify the location of media file
////        Uri uri= Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/sample_video.mp4");
//
//        imageVideoPlay.setBackground(context.getResources().getDrawable(R.drawable.ic_play_circle_outline_black_24dp));
//        changeImageViewSizes(imageVideoPlay, 200, 200);
//        imageVideoPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imageVideoPlay.setVisibility(View.GONE);
//                //Set MediaController  to enable play, pause, forward, etc options.
//                MediaController mediaController = new MediaController(context);
//                mediaController.setAnchorView(videoView);
//                //Location of Media File
//                Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.sample_video);
//                //Starting VideView By Setting MediaController and URI
//                videoView.setMediaController(mediaController);
//                videoView.setVideoURI(uri);
//                videoView.requestFocus();
//                videoView.start();
//            }
//        });
//
//        linearLayout.setGravity(Gravity.CENTER);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        llImg.addView(imageVideoPlay);
//        llImg.setGravity(Gravity.CENTER);
//        llvPlayer.setGravity(Gravity.CENTER);
//        llvPlayer.addView(videoView);
//        frameLayout.addView(llvPlayer);
//        frameLayout.addView(llImg);
//
//        linearLayout.addView(frameLayout);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getVideoPlayerView() {
        return linearLayout;
    }


    public void addVideoPlayerStrip(final Context context) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View view = lInflater.inflate(R.layout.control_video_player, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                    view = linflater.inflate(R.layout.control_video_player_six, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {

                    view = linflater.inflate(R.layout.control_video_player_seven, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("8")) {

                    view = linflater.inflate(R.layout.control_video_player_eight, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {

                    view = linflater.inflate(R.layout.layout_video_player_nine, null);
                }
            } else {
//                view = linflater.inflate(R.layout.control_video_player, null);
                view = linflater.inflate(R.layout.control_video_player_default, null);
            }
            view.setTag(VideoPlayerTAG);
            ll_displayName= view.findViewById(R.id.ll_displayName);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);

            videoView = view.findViewById(R.id.videoView);
            final LinearLayout ll_videoPlayer_main = view.findViewById(R.id.ll_videoPlayer_main);
            final LinearLayout ll_videoPlayer = view.findViewById(R.id.ll_videoPlayer);
            imageVideoPlay = view.findViewById(R.id.iv_videoPlay);
            mediaController = new MediaController(context);


            setTags();

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
                    mediaController = new MediaController(context);
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
            setControlValues();
            linearLayout.addView(view);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "addVideoPlayerStrip", e);
        }
    }

    private void setControlValues() {
        try {
            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            if (controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                setHint(controlObject.getHint());
            }else{
                tv_hint.setVisibility(View.GONE);
            }
            if (controlObject.isEnableStayAwake()) {
                mScreenAwake(context, TAG);
                Log.d("isEnableStayAwake", String.valueOf(controlObject.isEnableStayAwake()));
            }
            if(controlObject.isHideDisplayName()){
                ll_displayName.setVisibility(View.GONE);
            }

                setDisplaySettings(context, tv_displayName, controlObject);
                setTextSize(controlObject.getTextSize());
                setTextColor(controlObject.getTextHexColor());
                setTextStyle(controlObject.getTextStyle());



        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    private void setTags() {
        try {
            videoView.setTag(controlObject.getControlName());
            imageVideoPlay.setTag(controlObject.getControlName());
            mediaController.setTag(controlObject.getControlName());
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setTags", e);
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

    /*General*/
    /*displayName,hint,uploadedFilePath*/

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

    public String getUploadedFilePath() {
        return controlObject.getUploadedFilePath();
    }

    public void setUploadedFilePath(String uploadedFilePath) {
        controlObject.setUploadedFilePath(uploadedFilePath);
    }

    /*Options*/
    /*hideDisplayName,enableStayAwake
    invisible/visibility
     */

    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        if(hideDisplayName){
            ll_displayName.setVisibility(View.GONE);
        }else{
            ll_displayName.setVisibility(View.VISIBLE);
        }
    }

    public boolean isEnableStayAwake() {
        return controlObject.isEnableStayAwake();
    }

    public void setEnableStayAwake(boolean enableStayAwake) {
        controlObject.setEnableStayAwake(enableStayAwake);
        if(enableStayAwake){
            mScreenAwake(context, TAG);
        }

    }

    @Override
    public void showMessageBelowControl(String msg) {
    }


}
