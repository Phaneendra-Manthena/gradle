package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.mScreenAwake;
import static com.bhargo.user.utils.ImproveHelper.setViewDisable;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.PathVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.services.PlayBackgroundService;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AudioPlayerCopy implements PathVariables , UIVariables {

    private static final String TAG = "AudioPlayer";

    public static int oneTimeOnly = 0;
    private final Handler myHandler = new Handler();
    private final int forwardTime = 5000;
    private final int backwardTime = 5000;
    private final int AudioPlayerTAG = 0;
    Context context;
    ControlObject controlObject;
    LinearLayout linearLayout, ll_actions,ll_displayName,ll_label;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    ImproveHelper improveHelper;
    private MediaPlayer mediaPlayer;
    private double startTime = 0;
    private double finalTime = 0;
    private TextView tx1, tx2, tx3;
    private SeekBar seekBar;
    private final Runnable UpdateAudioTime = new Runnable() {
        public void run() {

            startTime = mediaPlayer.getCurrentPosition();
            tx1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekBar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };
    private CustomTextView tv_displayName, tv_hint;
    private ImageView iv_mandatory;
    private View view;
    private ImageView ivPlayAudio, ivPauseAudio, ivForwardAudio, ivBackwardAudio;
    private Intent svc;
    private boolean isPause;

    public AudioPlayerCopy(Context context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        improveHelper = new ImproveHelper(context);
        initView();

    }

    private void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            addAudioPlayerStrip(context);
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getAudioPlayerView() {
        return linearLayout;
    }

    public void addAudioPlayerStrip(final Context context) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = lInflater.inflate(R.layout.control_audio_player, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {

                    view = linflater.inflate(R.layout.control_audio_player_two, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {

                    view = linflater.inflate(R.layout.control_audio_player_three, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("5")) {

                    view = linflater.inflate(R.layout.control_audio_player_five, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                    view = linflater.inflate(R.layout.control_audio_player_design_six, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {

                    view = linflater.inflate(R.layout.control_audio_player_design_seven, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {

                    view = linflater.inflate(R.layout.layout_audio_player_nine, null);
                }else {

//                view = linflater.inflate(R.layout.control_audio_player, null);
                    view = linflater.inflate(R.layout.control_audio_player_default, null);
                }
            } else {

//                view = linflater.inflate(R.layout.control_audio_player, null);
                view = linflater.inflate(R.layout.control_audio_player_default, null);
            }

            view.setTag(AudioPlayerTAG);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            ll_label = view.findViewById(R.id.ll_label);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);

            tx1 = new TextView(context);
            tx2 = new TextView(context);
            tx3 = new TextView(context);

            seekBar = view.findViewById(R.id.seekBar_audioPlayer);
//        final ImageView ivBackgroundPlay = view.findViewById(R.id.ivBackgroundPlay);
            ivPlayAudio = view.findViewById(R.id.ivPlayAudio);
            ivPauseAudio = view.findViewById(R.id.ivPauseAudio);
            ivPauseAudio.setVisibility(View.GONE);
            ivForwardAudio = view.findViewById(R.id.ivForwardAudio);
            ivBackwardAudio = view.findViewById(R.id.ivBackwardAudio);
            setTags();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    ivPlayAudio.setVisibility(View.VISIBLE);
                    ivPauseAudio.setVisibility(View.GONE);
                    ivPauseAudio.setEnabled(false);
                    ivPlayAudio.setEnabled(true);
                    mediaPlayer.reset();
                    seekBar.setProgress(0);
                }
            });
            ivPlayAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer = new MediaPlayer();
                    ivPlayAudio.setVisibility(View.GONE);
                    ivPauseAudio.setVisibility(View.VISIBLE);
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
                    if (controlObject.getControlValue() != null && !controlObject.getControlValue().isEmpty() && !controlObject.getControlValue().equalsIgnoreCase("File not found")) {
                        Toast.makeText(context, "Playing audio", Toast.LENGTH_SHORT).show();
                        try {
                            if (!isPause) {
                                mediaPlayer.stop();
                                mediaPlayer.reset();
                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                mediaPlayer.setDataSource(controlObject.getControlValue());
                                Log.d(TAG, "onClickAudioFileName: " + controlObject.getControlValue());
                                mediaPlayer.prepare();
                            } else {
                                isPause = false;
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("exc", e.getMessage());
                        }
                        mediaPlayer.start();

                        finalTime = mediaPlayer.getDuration();
                        startTime = mediaPlayer.getCurrentPosition();

                        if (oneTimeOnly == 0) {
                            seekBar.setMax((int) finalTime);
                            oneTimeOnly = 1;
                        }

                        seekBar.setProgress((int) startTime);
                        myHandler.postDelayed(UpdateAudioTime, 100);
                        ivPauseAudio.setEnabled(true);
                        ivPlayAudio.setEnabled(false);

                    } else if (controlObject.getAudioData() != null && !controlObject.getAudioData().isEmpty()) {
                        Toast.makeText(context, "Playing audio", Toast.LENGTH_SHORT).show();
                        try {
                            if (!isPause) {
                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                mediaPlayer.setDataSource(controlObject.getAudioData());

                                mediaPlayer.prepare();
                            } else {
                                isPause = false;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.start();

                        finalTime = mediaPlayer.getDuration();
                        startTime = mediaPlayer.getCurrentPosition();

                        if (oneTimeOnly == 0) {
                            seekBar.setMax((int) finalTime);
                            oneTimeOnly = 1;
                        }
                        seekBar.setProgress((int) startTime);
                        myHandler.postDelayed(UpdateAudioTime, 100);

                    } else {

                        Toast.makeText(context, "No audio file.", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            ivPauseAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    isPause = true;
                    mediaPlayer.pause();
//                ivPauseAudio.setEnabled(false);
                    ivPlayAudio.setVisibility(View.VISIBLE);
                    ivPauseAudio.setVisibility(View.GONE);


                    Toast.makeText(context, "Pausing Audio", Toast.LENGTH_SHORT).show();
                }
            });

            ivForwardAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int temp = (int) startTime;

                    if ((temp + forwardTime) <= finalTime) {
                        startTime = startTime + forwardTime;
                        mediaPlayer.seekTo((int) startTime);
                        Toast.makeText(context, R.string.audio_formard_5, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, R.string.audio_cannot_forward_5, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ivBackwardAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int temp = (int) startTime;

                    if ((temp - backwardTime) > 0) {
                        startTime = startTime - backwardTime;
                        mediaPlayer.seekTo((int) startTime);
                        Toast.makeText(context, R.string.audio_formard_5, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, R.string.audio_cannot_forward_5, Toast.LENGTH_SHORT).show();
                    }
                }
            });

//        ivBackgroundPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent svc=new Intent(context, PlayBackgroundService.class);
//                context.startService(svc);
//            }
//        });

            setControlValues(view);

            linearLayout.addView(view);

        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "addAudioPlayerStrip", e);
        }
    }


    private void setControlValues(View view) {


        try {
            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            if (controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                tv_hint.setText(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }
            if(controlObject.isHideDisplayName()){
                tv_displayName.setVisibility(View.GONE);
            }

            if (controlObject.isEnablePlayBackground()) {
                if (mediaPlayer.isPlaying()) {
                    svc = new Intent(context, PlayBackgroundService.class);
                    context.startService(svc);
                }
            } else {
                if (mediaPlayer.isPlaying()) {
                    if (svc != null) {
                        context.stopService(svc);
                    }
                }
            }

            if (controlObject.isDisable()) {
                setViewDisable(view, false);
            }
            if (controlObject.isEnableStayAwake()) {
                mScreenAwake(context, TAG);
                Log.d("isEnableStayAwake", String.valueOf(controlObject.isEnableStayAwake()));
            }

            if (controlObject.getControlValue() != null && !controlObject.getControlValue().isEmpty()) {
                try {
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(controlObject.getControlValue());
                    Log.d(TAG, "onClickAudioFileNameCV: " + controlObject.getControlValue());
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    private void setTags() {
        try {
            ivPlayAudio.setTag(controlObject.getControlName());
            ivPauseAudio.setTag(controlObject.getControlName());
            ivForwardAudio.setTag(controlObject.getControlName());
            ivBackwardAudio.setTag(controlObject.getControlName());
            seekBar.setTag(controlObject.getControlName());
        } catch (Exception e) {
            improveHelper.improveException(context, TAG, "setTags", e);
        }
    }


    @Override
    public String getPath() {
        return controlObject.getAudioData();
    }

    @Override
    public void setPath(String path) {
        controlObject.setAudioData(path);
    }

    @Override
    public boolean getVisibility() {
        controlObject.setInvisible(linearLayout.getVisibility() != View.VISIBLE);
        return controlObject.isInvisible();
    }

    @Override
    public void setVisibility(boolean visibility) {

        if (!visibility) {
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
        setViewDisable(view, !enabled);
        controlObject.setDisable(!enabled);
    }

    @Override
    public void clean() {
       setPath("");
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
    /*hideDisplayName,enableStayAwake,enablePlayBackground
    invisible/visibility
     */

    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        ll_label.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
        if(hideDisplayName) {
            tv_hint.setVisibility(View.GONE);
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

    public boolean isEnablePlayBackground() {
        return controlObject.isEnablePlayBackground();
    }

    public void setEnablePlayBackground(boolean enablePlayBackground) {
        controlObject.setEnablePlayBackground(enablePlayBackground);
        if (enablePlayBackground) {
            if (mediaPlayer!=null && mediaPlayer.isPlaying()) {
                if (svc != null){
                    svc = new Intent(context, PlayBackgroundService.class);
                    context.startService(svc);
                }
            }
        } else {
            if (mediaPlayer!=null && mediaPlayer.isPlaying()) {
                if (svc != null) {
                    context.stopService(svc);
                }
            }
        }
    }
    @Override
    public void showMessageBelowControl(String msg) {
        if(msg != null && !msg.isEmpty()) {
//            ct_showText.setVisibility(View.VISIBLE);
//            ct_showText.setText(msg);
        }else{
//            ct_showText.setVisibility(View.GONE);
        }
    }

}
