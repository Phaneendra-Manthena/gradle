package com.bhargo.user.controls.standard;

import static com.bhargo.user.utils.ImproveHelper.mScreenAwake;
import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisable;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
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

public class AudioPlayer implements PathVariables, UIVariables {

    private static final String TAG = "AudioPlayer";
    private final int forwardTime = 5000;
    private final int backwardTime = 5000;
    private final int AudioPlayerTAG = 0;
    private final SeekBarUpdater seekBarUpdater;
    Context context;
    ControlObject controlObject;
    LinearLayout linearLayout, ll_displayName, ll_label,ll_main_inside,ll_control_ui,ll_tap_text,layout_audio_player;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    ImproveHelper improveHelper;
    CustomTextView tvMaxTime, tvStartTime;
    private MediaPlayer mediaPlayer_;
    private SeekBar seekBar_;
    private CustomTextView tv_displayName, tv_hint,ct_showText;
    private ImageView iv_mandatory;
    private View view;
    private ImageView ivPlayAudio, ivPauseAudio, ivForwardAudio, ivBackwardAudio;
    private Intent svc;

    public AudioPlayer(Context context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
        this.context = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        improveHelper = new ImproveHelper(context);
        seekBarUpdater = new SeekBarUpdater();
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
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getAudioPlayerView() {
        return linearLayout;
    }

    public void addAudioPlayerStrip(final Context context) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = linflater.inflate(R.layout.control_audio_player_default, null);

            view.setTag(AudioPlayerTAG);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            ll_label = view.findViewById(R.id.ll_label);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            ll_tap_text = view.findViewById(R.id.ll_tap_text);
            layout_audio_player = view.findViewById(R.id.layout_audio_player);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);

            seekBar_ = view.findViewById(R.id.seekBar_audioPlayer);
            ivPlayAudio = view.findViewById(R.id.ivPlayAudio);
            ivPauseAudio = view.findViewById(R.id.ivPauseAudio);
            ivPauseAudio.setVisibility(View.GONE);
            ivForwardAudio = view.findViewById(R.id.ivForwardAudio);
            ivBackwardAudio = view.findViewById(R.id.ivBackwardAudio);
            tvMaxTime = view.findViewById(R.id.tvMaxTime);
            tvStartTime = view.findViewById(R.id.tvStartTime);
            ct_showText = view.findViewById(R.id.ct_showText);
            setTags();
            seekBar_.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mediaPlayer_.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });


            ivPlayAudio.setOnClickListener(new View.OnClickListener() {
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
                    if (mediaPlayer_ != null) {
                        mediaPlayer_.start();
                    } else {
                        initMediaPlayer();
                        if (controlObject.getControlValue() != null && !controlObject.getControlValue().trim().isEmpty() && !controlObject.getControlValue().equalsIgnoreCase("File not found")) {
                            controlObject.setText(controlObject.getControlValue());
                            if (mediaPlayer_ != null) {
                                updateNonPlayingView();
                            }
                            ivPlayAudio.setVisibility(View.GONE);
                            ivPauseAudio.setVisibility(View.VISIBLE);
                            Toast.makeText(context, "Playing audio", Toast.LENGTH_SHORT).show();

                            try {
                                mediaPlayer_.setDataSource(controlObject.getControlValue());
                                mediaPlayer_.prepare();
                                mediaPlayer_.start();
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d("exc", e.getMessage());
                            }
                        } else if (controlObject.getAudioData() != null && !controlObject.getAudioData().trim().isEmpty()) {
                            controlObject.setText(controlObject.getAudioData());
                            if (mediaPlayer_ != null) {
                                updateNonPlayingView();
                            }
                            ivPlayAudio.setVisibility(View.GONE);
                            ivPauseAudio.setVisibility(View.VISIBLE);
                            Toast.makeText(context, "Playing audio", Toast.LENGTH_SHORT).show();
                            try {
                                mediaPlayer_.setDataSource(controlObject.getAudioData());
                                mediaPlayer_.prepare();
                                mediaPlayer_.start();
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d("exc", e.getMessage());
                            }
                        } else {
                            Toast.makeText(context, "No audio file.", Toast.LENGTH_SHORT).show();
                        }

                    }
                    if (mediaPlayer_ != null)
                        updatePlayingView();
                }
            });

            ivPauseAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mediaPlayer_ != null && mediaPlayer_.isPlaying()) {
                        mediaPlayer_.pause();
                        ivPlayAudio.setVisibility(View.VISIBLE);
                        ivPauseAudio.setVisibility(View.GONE);
                        Toast.makeText(context, "Pausing Audio", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ivForwardAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer_ != null) {
                        int tempCurrentPos = (int) mediaPlayer_.getCurrentPosition();
                        int tempDuration = (int) mediaPlayer_.getDuration();

                        if ((tempCurrentPos + forwardTime) <= tempDuration) {
                            tempCurrentPos = tempCurrentPos + forwardTime;
                            mediaPlayer_.seekTo((int) tempCurrentPos);
                            Toast.makeText(context, R.string.audio_formard_5, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, R.string.audio_cannot_forward_5, Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });

            ivBackwardAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer_ != null) {
                        int temp = (int) mediaPlayer_.getCurrentPosition();
                        if ((temp - backwardTime) > 0) {
                            temp = temp - backwardTime;
                            mediaPlayer_.seekTo((int) temp);
                            Toast.makeText(context, R.string.audio_formard_5, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, R.string.audio_cannot_forward_5, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


            setControlValues(view);

            linearLayout.addView(view);

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addAudioPlayerStrip", e);
        }
    }

    private void updateNonPlayingView() {
        seekBar_.removeCallbacks(seekBarUpdater);
        seekBar_.setEnabled(false);
        seekBar_.setProgress(0);
        ivPlayAudio.setVisibility(View.VISIBLE);
        ivPauseAudio.setVisibility(View.GONE);
    }

    private String getAudioTime(long duration) {
        long minutes
                = TimeUnit.MILLISECONDS.toMinutes(duration);
        long seconds
                = (TimeUnit.MILLISECONDS.toSeconds(duration)
                % 60);
        String min = minutes + "";
        min = min.length() == 1 ? "0" + min : min;
        String sec = seconds + "";
        sec = sec.length() == 1 ? "0" + sec : sec;

        return min + ":" + sec;

    }

    private void updatePlayingView() {
        tvMaxTime.setText(getAudioTime(mediaPlayer_.getDuration()));
        seekBar_.setMax(mediaPlayer_.getDuration());
        seekBar_.setProgress(mediaPlayer_.getCurrentPosition());
        seekBar_.setEnabled(true);
        if (mediaPlayer_.isPlaying()) {
            seekBar_.postDelayed(seekBarUpdater, 100);
            ivPlayAudio.setVisibility(View.GONE);
            ivPauseAudio.setVisibility(View.VISIBLE);
        } else {
            seekBar_.removeCallbacks(seekBarUpdater);
            ivPlayAudio.setVisibility(View.VISIBLE);
            ivPauseAudio.setVisibility(View.GONE);
        }
    }

    private void initMediaPlayer() {
        mediaPlayer_ = new MediaPlayer();
        mediaPlayer_.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer_.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                seekBar_.removeCallbacks(seekBarUpdater);
                seekBar_.setEnabled(false);
                seekBar_.setProgress(0);
                ivPlayAudio.setVisibility(View.VISIBLE);
                ivPauseAudio.setVisibility(View.GONE);

                mediaPlayer_.release();
                mediaPlayer_ = null;
                tvStartTime.setText("00:00");
            }
        });
    }

    public void stopPlaying() {
        if (mediaPlayer_ != null && mediaPlayer_.isPlaying()) {
            seekBar_.removeCallbacks(seekBarUpdater);
            mediaPlayer_.stop();
            mediaPlayer_.release();
        }
    }

    private void releaseMediaPlayer() {
        updateNonPlayingView();
        mediaPlayer_.release();
        mediaPlayer_ = null;
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
            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            }

            if (controlObject.isEnablePlayBackground()) {
                if (mediaPlayer_ != null && mediaPlayer_.isPlaying()) {
                    svc = new Intent(context, PlayBackgroundService.class);
                    context.startService(svc);
                }
            } else {
                if (mediaPlayer_!=null && mediaPlayer_.isPlaying()) {
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
                    if(mediaPlayer_ != null){
                        mediaPlayer_.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer_.setDataSource(controlObject.getControlValue());
                        mediaPlayer_.prepare();
                        controlObject.setText(controlObject.getControlValue().trim());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (controlObject.getAudioData() != null && !controlObject.getAudioData().isEmpty()) {
                controlObject.setText(controlObject.getAudioData().trim());
            }
            setDisplaySettings(context, tv_displayName, controlObject);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    private void setTags() {
        try {
            ivPlayAudio.setTag(controlObject.getControlName());
            ivPauseAudio.setTag(controlObject.getControlName());
            ivForwardAudio.setTag(controlObject.getControlName());
            ivBackwardAudio.setTag(controlObject.getControlName());
            seekBar_.setTag(controlObject.getControlName());
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setTags", e);
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

    public String getDisplayName() {
        return controlObject.getDisplayName();
    }

    /*General*/
    /*displayName,hint,uploadedFilePath*/

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

    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    /*Options*/
    /*hideDisplayName,enableStayAwake,enablePlayBackground
    invisible/visibility
     */

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        ll_displayName.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
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

    public boolean isEnablePlayBackground() {
        return controlObject.isEnablePlayBackground();
    }

    public void setEnablePlayBackground(boolean enablePlayBackground) {
        controlObject.setEnablePlayBackground(enablePlayBackground);
        if (enablePlayBackground) {
            if (mediaPlayer_ != null && mediaPlayer_.isPlaying()) {
                if (svc != null) {
                    svc = new Intent(context, PlayBackgroundService.class);
                    context.startService(svc);
                }
            }
        } else {
            if (mediaPlayer_ != null && mediaPlayer_.isPlaying()) {
                if (svc != null) {
                    context.stopService(svc);
                }
            }
        }
    }

    private class SeekBarUpdater implements Runnable {
        @Override
        public void run() {
            if (null != seekBar_ && null != mediaPlayer_) {
                seekBar_.setProgress(mediaPlayer_.getCurrentPosition());
                seekBar_.postDelayed(this, 100);
                tvStartTime.setText(getAudioTime(mediaPlayer_.getCurrentPosition()));
            }
        }
    }

    public LinearLayout getLl_main_inside(){
        return ll_main_inside;
    }
    public LinearLayout getLl_control_ui(){
        return ll_control_ui;
    }
    public LinearLayout getLl_tap_text(){
        return ll_tap_text;
    }
    public LinearLayout getLayout_audio_player(){
        return layout_audio_player;
    }
    public CustomTextView getTv_displayName(){
        return tv_displayName;
    }
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
