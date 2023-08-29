package com.bhargo.user.controls.standard;

import static android.content.Intent.ACTION_PICK;
import static android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.PathVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PermissionsActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisable;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

public class VoiceRecording implements PathVariables, UIVariables {

    private static final String TAG = "VoiceRecording";
    private final int VoiceRecordingTAG = 0;
    Activity context;
    ControlObject controlObject;
    LinearLayout linearLayout,layout_control,layout_audio_player,layout_upload_file,ll_tap_text,layout_delete,ll_displayName,
            ll_main_inside,ll_control_ui,ll_cg,ll_recorder_upload;
    boolean isVoiceRecorded = false;
    int position;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    ImproveHelper improveHelper;
    private PermissionsActivity permissionsActivity;
    private ImageView  iv_textTypeImage;
    private CustomTextView tv_startRecording;
    private MediaRecorder mRecorder;
    private String fileName = null;
    private String saveAudioFormatIn = "mp3";
    private CustomTextView tv_displayName,tv_hint,ct_showText;
    private ImageView iv_mandatory;
    private View view;
    private CustomTextView ct_alertVoiceRecording;
    private Chronometer chronometerRecord;
    private int recorderState=0;//0:initial state,1:recording,2:ready to play
    private MediaPlayer mediaPlayer;
    private boolean isPause;
    private double startTime = 0;
    private double finalTime = 0;
    private SeekBar seekBar;
    private ImageView ivPlayAudio, ivPauseAudio, ivForwardAudio, ivBackwardAudio;
    public static int oneTimeOnly = 0;
    private final Handler myHandler = new Handler();
    private final int forwardTime = 5000;
    private final int backwardTime = 5000;

    private final Runnable UpdateAudioTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            seekBar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    public VoiceRecording(final Activity context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
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
            addVoiceRecordingStrip(context);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    private void stopRecording() {
        try {
            mRecorder.stop();
            mRecorder.release();
            isVoiceRecorded = true;
            mRecorder = null;
            //starting the chronometer
            chronometerRecord.setVisibility(View.VISIBLE);
//            chronometerRecord.setBase(SystemClock.elapsedRealtime());
            chronometerRecord.stop();
            //showing the play button
            Toast.makeText(context, "Recording saved successfully.", Toast.LENGTH_SHORT).show();
            getPlayViewVoiceRecording();
            controlObject.setItemSelected(isVoiceRecorded);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "stopRecording", e);
        }
    }

    private void startRecording() {
        try {
            //we use the MediaRecorder class to record
            recorderState = 1;
            seekBar.setProgress(0);
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            /**In the lines below, we create a directory VoiceRecorderSimplifiedCoding/Audios in the phone storage
             * and the audios are being stored in the Audios folder **/
//            File root = android.os.Environment.getExternalStorageDirectory();
//            File file = new File(root.getAbsolutePath() + "/ImproveUser_VoiceRecorder/Audios");
            File fileStorage = ImproveHelper.createFolder(context, "ImproveUserFiles/ImproveUser_VoiceRecorder");
            String filePath = "Audio_"+System.currentTimeMillis() + "." + saveAudioFormatIn;
            File file = new File(fileStorage, filePath);//Create Output file in Main File
            fileName = file.getAbsolutePath();
           /* fileName = root.getAbsolutePath() + "/ImproveUser_VoiceRecorder/Audios/" +
                    System.currentTimeMillis() + "." + saveAudioFormatIn;*/


            Log.d("SavedePathfilename", fileName);
            mRecorder.setOutputFile(fileName);
            tv_displayName.setTag(fileName);
            setPath(fileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                mRecorder.prepare();
                mRecorder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //starting the chronometer
            chronometerRecord.setVisibility(View.VISIBLE);
            chronometerRecord.setBase(SystemClock.elapsedRealtime());
            chronometerRecord.start();
            isVoiceRecorded = true;
            controlObject.setItemSelected(isVoiceRecorded);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "startRecording", e);
        }
    }



    public LinearLayout getVoiceRecordingView() {
        return linearLayout;
    }



    public void addVoiceRecordingStrip(final Context context) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = lInflater.inflate(R.layout.control_voice_recording, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                    view = linflater.inflate(R.layout.control_voice_recording_six, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
                    view = linflater.inflate(R.layout.control_voice_recording_seven, null);
                } else {
                    view = linflater.inflate(R.layout.layout_voice_recording_default, null);
//                    view = linflater.inflate(R.layout.control_voice_recording, null);
                }
            } else {
//                view = linflater.inflate(R.layout.control_voice_recording, null);
                view = linflater.inflate(R.layout.layout_voice_recording_default, null);
            }
            view.setTag(VoiceRecordingTAG);

            layout_control = view.findViewById(R.id.layout_control);
            layout_audio_player = view.findViewById(R.id.layout_audio_player);

            iv_textTypeImage = view.findViewById(R.id.iv_textTypeImage);
            tv_startRecording = view.findViewById(R.id.tv_tapTextType);
            chronometerRecord = view.findViewById(R.id.chronometerRecord);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_control_ui = view.findViewById(R.id.ll_control_ui);
            ll_cg = view.findViewById(R.id.ll_cg);
            ll_recorder_upload = view.findViewById(R.id.ll_recorder_upload);

            ct_alertVoiceRecording = view.findViewById(R.id.ct_alertTypeText);


            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            mediaPlayer = new MediaPlayer();
            seekBar = view.findViewById(R.id.seekBar_audioPlayer);
            ivPlayAudio = view.findViewById(R.id.ivPlayAudio);
            ivPauseAudio = view.findViewById(R.id.ivPauseAudio);
            if (ivPauseAudio != null) {
                ivPauseAudio.setVisibility(View.GONE);
            }
            ivForwardAudio = view.findViewById(R.id.ivForwardAudio);
            ivBackwardAudio = view.findViewById(R.id.ivBackwardAudio);
            ll_tap_text = view.findViewById(R.id.ll_tap_text);
            layout_upload_file = view.findViewById(R.id.layout_camera_or_gallery);
            layout_delete = view.findViewById(R.id.layout_delete);
            ct_showText = view.findViewById(R.id.ct_showText);
            if (layout_upload_file != null) {
                layout_upload_file.setTag(controlObject.getControlName());
            }
            if (ll_tap_text != null){
                ll_tap_text.setTag(controlObject.getControlType());
            }

            tv_startRecording.setTag(R.string.voice_recording_view_click,context.getString(R.string.start_recording_click));
            iv_textTypeImage.setTag(R.string.voice_recording_view_click,context.getString(R.string.voice_recording_click));
            layout_upload_file.setTag(R.string.voice_recording_view_click,context.getString(R.string.audio_fileupload_click));

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(ivPlayAudio != null) {
                        ivPlayAudio.setVisibility(View.VISIBLE);
                    }
                    if(ivPauseAudio != null) {
                        ivPauseAudio.setVisibility(View.GONE);
                        ivPauseAudio.setEnabled(false);
                    }
                    if(ivPlayAudio != null) {
                        ivPlayAudio.setEnabled(true);
                    }
                    mediaPlayer.reset();
                    seekBar.setProgress(0);
                }
            });
            if(ivPlayAudio != null) {
                ivPlayAudio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ivPlayAudio != null) {
                            ivPlayAudio.setVisibility(View.GONE);
                        }
                        if (ivPauseAudio != null) {
                            ivPauseAudio.setVisibility(View.VISIBLE);
                        }

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
                            if (ivPauseAudio != null) {
                                ivPauseAudio.setEnabled(true);
                            }
                            if (ivPlayAudio != null) {
                                ivPlayAudio.setEnabled(false);
                            }

                        } else if (controlObject.getAudioData() != null && !controlObject.getAudioData().isEmpty()) {
                            Toast.makeText(context, "Playing audio", Toast.LENGTH_SHORT).show();
                            try {
                                if (!isPause) {
                                    Uri myUri = Uri.parse(fileName); // initialize Uri here
                                    mediaPlayer.setAudioAttributes(
                                            new AudioAttributes.Builder()
                                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                                    .build()
                                    );
                                    mediaPlayer.setDataSource(context, myUri);
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

                        } else if (controlObject.getVoiceRecordPath() != null && !controlObject.getVoiceRecordPath().isEmpty()) {
                            Toast.makeText(context, "Playing audio", Toast.LENGTH_SHORT).show();
                            try {
                                if (!isPause) {
                                    mediaPlayer.stop();
                                    mediaPlayer.reset();
                                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                    mediaPlayer.setDataSource(controlObject.getVoiceRecordPath());
                                    mediaPlayer.prepare();
                                } else {
                                    isPause = false;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            mediaPlayer.start();
                            if (ivPauseAudio != null) {
                                ivPauseAudio.setEnabled(true);
                            }
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
            }
            if(ivPauseAudio != null) {
                ivPauseAudio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        isPause = true;
                        mediaPlayer.pause();
//                ivPauseAudio.setEnabled(false);
                        if (ivPlayAudio != null) {
                            ivPlayAudio.setVisibility(View.VISIBLE);
                        }
                        ivPauseAudio.setVisibility(View.GONE);


                        Toast.makeText(context, "Pausing Audio", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if(ivForwardAudio != null) {
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
            }
            if(ivBackwardAudio != null) {
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
            }

            viewSetTags();
            setControlValues();

            linearLayout.addView(view);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addVoiceRecordingStrip", e);
        }
    }

    private void viewSetTags() {
        try {
            iv_textTypeImage.setTag(controlObject.getControlName());
            if(tv_startRecording != null) {
                tv_startRecording.setTag(controlObject.getControlName());
            }
            if(ivPlayAudio != null) {
                ivPlayAudio.setTag(controlObject.getControlName());
            }
            if(ivPauseAudio != null) {
                ivPauseAudio.setTag(controlObject.getControlName());
            }
            ivForwardAudio.setTag(controlObject.getControlName());
            ivBackwardAudio.setTag(controlObject.getControlName());

            seekBar.setTag(controlObject.getControlName());
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "viewSetTags", e);
        }
    }

    public CustomTextView startRecordingClick() {

        return tv_startRecording;
    }


    public ImageView getVoiceRecordingClick() {
        return iv_textTypeImage;
    }

    public TextView getImageViewPlay() {
        return tv_displayName;
//        return tv_startRecording;
    }
//
    public TextView getImageViewStop() {
        return tv_displayName;
//        return tv_startRecording;
    }

    public LinearLayout getLayoutAudioFileUpload() {

        return layout_upload_file;
    }

    private void setControlValues() {
        try {
            /*if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
                    if(tv_startRecording != null) {
                        tv_startRecording.setText(controlObject.getDisplayName());
                    }
                }
            }*/
            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            if (controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                tv_hint.setVisibility(View.VISIBLE);
                tv_hint.setText(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }
            if (controlObject.isNullAllowed()) {
                iv_mandatory.setVisibility(View.VISIBLE);
            } else {
                iv_mandatory.setVisibility(View.GONE);
            }
            if(controlObject.isHideDisplayName()){
                ll_displayName.setVisibility(View.GONE);
            }
            if (controlObject.isEnableVoiceMaximumDuration()) {
                Log.d("XMLVoiceRecMaxParam", String.valueOf(controlObject.isEnableVoiceMaximumDuration()));
                Log.d("XMLVoiceRecMaxParam", controlObject.getVoiceMaximumDuration());
                Log.d("XMLVoiceRecMaxMessage", controlObject.getMaximumDurationError());
            }
            if (controlObject.isEnableVoiceMinimumDuration()) {
                Log.d("XMLVoiceRecMinParam", String.valueOf(controlObject.isEnableVoiceMinimumDuration()));
                Log.d("XMLVoiceRecMinParam", controlObject.getVoiceMinimumDuration());
                Log.d("XMLVoiceRecMinMessage", controlObject.getMinimumDurationError());
            }
            if (controlObject.getAudioFormatIds() != null) {
                List<String> stringList = controlObject.getAudioFormatIds();
                Log.d("VoiceAudioFormatsIds", String.valueOf(controlObject.getAudioFormatIds()));
                saveAudioFormatIn = "WMA";
            }
            if (controlObject.isEnableUploadAudioFile()) {
                layout_upload_file.setVisibility(View.VISIBLE);
            }else{
                if(ll_tap_text != null) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, improveHelper.dpToPx(40));
                    ll_tap_text.setLayoutParams(layoutParams);
                }
            }
            layout_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mRecorder = new MediaRecorder();
                    mediaPlayer.stop();
                    chronometerRecord.setVisibility(View.GONE);
                    if(tv_startRecording != null) {
                        tv_startRecording.setVisibility(View.VISIBLE);
                    }
//                    iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_icon_voice_input));
                    fileName = null;
                    tv_displayName.setTag(fileName);
                    seekBar.setProgress(0);
                    controlObject.setAudioData(fileName);
                    layout_control.setVisibility(View.VISIBLE);
                    if(layout_audio_player != null) {
                        layout_audio_player.setVisibility(View.GONE);
                    }
                    if(ivPlayAudio != null) {
                        ivPlayAudio.setVisibility(View.VISIBLE);
                    }
                    if(ivPauseAudio != null) {
                        ivPauseAudio.setVisibility(View.GONE);
                    }
//                    displayAlignmentAndSettings();
                    isVoiceRecorded = false;
                    recorderState = 0;
                    controlObject.setItemSelected(isVoiceRecorded);
                    iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_voice_input));
                }
            });

// jay
            if (controlObject.getControlValue() != null && !controlObject.getControlValue().equalsIgnoreCase(" ")) {

                isVoiceRecorded = true;
            } else {

            }


            iv_textTypeImage.setVisibility(View.VISIBLE);
            displayAlignmentAndSettings();
            if (controlObject.isDisable()) {
//                setViewDisable(view, false);
                improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), !controlObject.isDisable(), ll_tap_text, view);
            }

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    public boolean setIsVoiceRecorded() {
        Log.d(TAG, "setIsVoiceRecorded: " + isVoiceRecorded);
        return isVoiceRecorded;
    }

    public CustomTextView setAlertVoiceRecording() {

        return ct_alertVoiceRecording;
    }

    public void fileToBase64() {
        File imgFile = new File(fileName);
        if (imgFile.exists() && imgFile.length() > 0) {
            Bitmap bm = BitmapFactory.decodeFile(fileName);
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bOut);
            String base64Image = Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT);

        }
    }

    public CustomTextView getControlRealPath() {
        return tv_displayName;
    }


    public void getStartVoiceRecording() {
        try {

            if(recorderState==0){
                getLayoutAudioFileUpload().setBackground(ContextCompat.getDrawable(context,R.drawable.circular_bg_file_default));
//                if(getLl_tap_text() != null &&) {
//                    getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle_default));
//                }
                ct_alertVoiceRecording.setVisibility(View.GONE);
                 if(tv_startRecording != null) {
                    tv_startRecording.setVisibility(View.GONE);
                }
                chronometerRecord.setVisibility(View.VISIBLE);
                iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_icon_stop));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.RECORD_AUDIO}, 375);

                } else {

                    startRecording();

                }

            }else if(recorderState==1){
                getStopVoiceRecording();
            }else if(recorderState==2){
                getPlayViewVoiceRecording();
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getStartVoiceRecording", e);
        }
    }

    public void getStopVoiceRecording() {
        try {
            if(recorderState==1){
            recorderState=2;
            stopRecording();
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getStopVoiceRecording", e);
        }
    }

    public void getPlayViewVoiceRecording() {
        try {
            layout_control.setVisibility(View.GONE);
            if(layout_audio_player != null) {
                layout_audio_player.setVisibility(View.VISIBLE);
            }
            //Play Audio
            try {
                if (!isPause) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                    FileDescriptor fd = null;
                    FileInputStream fis = new FileInputStream(fileName);
                    fd = fis.getFD();

                    mediaPlayer.setDataSource(fd);
                    Log.d(TAG, "onClickAudioFileName: " + controlObject.getControlValue());
                    mediaPlayer.prepare();
                } else {
                    isPause = false;
                }
                controlObject.setItemSelected(isVoiceRecorded);
                controlObject.setText(getControlRealPath().getTag().toString());

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("exc", e.getMessage());
            }
//            mediaPlayer.start();

            finalTime = mediaPlayer.getDuration();
            startTime = mediaPlayer.getCurrentPosition();

            if (oneTimeOnly == 0) {
                seekBar.setMax((int) finalTime);
                oneTimeOnly = 1;
            }

            seekBar.setProgress((int) startTime);
            myHandler.postDelayed(UpdateAudioTime, 100);
            if(ivPauseAudio != null) {
                ivPauseAudio.setEnabled(true);
            }
//            ivPlayAudio.setEnabled(false);

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getPlayViewVoiceRecording", e);
        }
    }

    public void getStopViewVoiceRecording() {

    }

    public void getVoiceUploadFile1(View v, int reqCode) {
        try {
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
            Intent intent;
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("audio/*");
            context.startActivityForResult(Intent.createChooser(intent, "select_audio_file_title"), reqCode);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getVoiceUploadFile", e);
        }
    }
    public void getVoiceUploadFile(View v, int reqCode) {
        try {
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
            Intent intent;
            intent = new Intent(ACTION_PICK, EXTERNAL_CONTENT_URI);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("audio/*");

            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra("return-data", true);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            context.startActivityForResult(Intent.createChooser(intent, "select_audio_file_title"), reqCode);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getVoiceUploadFile", e);
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public int getRecorderState() {
        return recorderState;
    }



    public void setAudioPath(String audioPath) {
        if (!audioPath.isEmpty()) {
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                    if (tv_startRecording != null) {
                        tv_startRecording.setText(audioPath);
                    }
                }
            }
            if (tv_startRecording != null) {
                tv_startRecording.setText(audioPath);
            }
        }
    }



    @Override
    public String getPath() {
        return controlObject.getVoiceRecordPath();
    }

    @Override
    public void setPath(String path) {
        controlObject.setVoiceRecordPath(path);

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

        return controlObject.isDisable();
    }

    @Override
    public void setEnabled(boolean enabled) {
        controlObject.setDisable(!enabled);
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), enabled, ll_tap_text, view);
//        if(layout_audio_player.getVisibility() == View.VISIBLE){
//            improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), !controlObject.isDisable(), layout_audio_player, view);
//        }
    }

    @Override
    public void clean() {
        fileName=null;
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
    /*displayName,hint*/

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
    /*Validations*/
    /*mandatory,mandatoryErrorMessage,enableVoiceMinimumDuration,voiceMinimumDuration
     enableVoiceMaximumDuration,voiceMaximumDuration*/
    public boolean isMandatory() {
        return controlObject.isNullAllowed();
    }

    public void setMandatory(boolean mandatory) {
        iv_mandatory.setVisibility(mandatory ? View.VISIBLE : View.GONE);
        controlObject.setNullAllowed(mandatory);
    }

    public String getMandatoryErrorMessage() {
        return controlObject.getMandatoryFieldError();
    }

    public void setMandatoryErrorMessage(String mandatoryErrorMessage) {
        controlObject.setMandatoryFieldError(mandatoryErrorMessage);
    }

    public boolean isEnableVoiceMinimumDuration() {
        return controlObject.isEnableVoiceMinimumDuration();
    }

    public void setEnableVoiceMinimumDuration(boolean enableVoiceMinimumDuration) {
        controlObject.setEnableVoiceMinimumDuration(enableVoiceMinimumDuration);
    }

    public String getVoiceMinimumDuration() {
        return controlObject.getVoiceMinimumDuration();
    }

    public void setVoiceMinimumDuration(String voiceMinimumDuration) {
        controlObject.setVoiceMinimumDuration(voiceMinimumDuration);
    }

    public boolean isEnableVoiceMaximumDuration() {
        return controlObject.isEnableVoiceMaximumDuration();
    }

    public void setEnableVoiceMaximumDuration(boolean enableVoiceMaximumDuration) {
        controlObject.setEnableVoiceMaximumDuration(enableVoiceMaximumDuration);
    }

    public String getVoiceMaximumDuration() {
        return controlObject.getVoiceMaximumDuration();
    }

    public void setVoiceMaximumDuration(String voiceMaximumDuration) {
        controlObject.setVoiceMaximumDuration(voiceMaximumDuration);
    }

    /*Options*/
    /*hideDisplayName,enableAudioFormat,audioFormat,enableUploadAudioFile
    invisible/visibility
     */

    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        controlObject.setHideDisplayName(hideDisplayName);
        ll_displayName.setVisibility(hideDisplayName ? View.GONE : View.VISIBLE);
        if(hideDisplayName) {
            tv_hint.setVisibility(View.GONE);
        }
    }

    public boolean isEnableAudioFormat() {
        return controlObject.isEnableAudioFormat();
    }

    public void setEnableAudioFormat(boolean enableAudioFormat) {
        controlObject.setEnableAudioFormat(enableAudioFormat);
    }

    public String getAudioFormat() {
        return controlObject.getAudioFormat();
    }

    public void setAudioFormat(String audioFormat) {
//        controlObject.setAudioFormat(audioFormat);
        controlObject.setAudioFormat("WMA");
        saveAudioFormatIn = "WMA";
    }

    public boolean isEnableUploadAudioFile() {
        return controlObject.isEnableUploadAudioFile();
    }

    public void setEnableUploadAudioFile(boolean enableUploadAudioFile) {
        controlObject.setEnableUploadAudioFile(enableUploadAudioFile);
        if (controlObject.isEnableUploadAudioFile()) {
            if(ll_tap_text != null) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, improveHelper.dpToPx(40));
                layoutParams.weight = 0.8f;
                ll_tap_text.setLayoutParams(layoutParams);
            }
            layout_upload_file.setVisibility(View.VISIBLE);
        }else{
            layout_upload_file.setVisibility(View.GONE);
        }
    }


    public void setVoiceRecordingPath(String path) {
        try {
            if(path!=null && !path.trim().isEmpty()){
                fileName = path;
                tv_displayName.setTag(fileName);
                isVoiceRecorded = true;
                controlObject.setItemSelected(isVoiceRecorded);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getPlayViewVoiceRecording", e);
        }
    }

    public void resetRecorder(){
        recorderState = 0;
        mRecorder = new MediaRecorder();
        fileName = null;
        tv_startRecording.setText(context.getString(R.string.start_recording));
        tv_displayName.setTag(fileName);
        controlObject.setVoiceRecordPath(null);
        chronometerRecord.setVisibility(View.VISIBLE);
        if(layout_audio_player != null) {
            layout_audio_player.setVisibility(View.GONE);
        }
        layout_control.setVisibility(View.VISIBLE);
        isVoiceRecorded = false;
        controlObject.setItemSelected(isVoiceRecorded);
        controlObject.setText(null);
    }

    public LinearLayout getLl_tap_text() { // linear for taptext

        return ll_tap_text;
    }

    public void setfilePath(String filePath) {
        controlObject.setText(filePath);
        controlObject.setItemSelected(true);
        fileName= filePath;
    }

    public void setUploadedFile(String path) {
        try {
            if(!path.trim().equalsIgnoreCase("")) {
                isVoiceRecorded = true;
                controlObject.setItemSelected(isVoiceRecorded);
                controlObject.setText(path);
                if (controlObject.getAudioFormatNames() != null) {
                    String fileFormat = path.substring(path.lastIndexOf(".") + 1);
                    if (controlObject.getAudioFormatNames()!=null && !controlObject.getAudioFormatNames().contains(fileFormat.toUpperCase())) {
                        Toast.makeText(context, fileFormat + " format not allowed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                setfilePath(path);
                tv_displayName.setTag(fileName);
                controlObject.setAudioData(fileName);
                layout_control.setVisibility(View.GONE);
                if (layout_audio_player != null) {
                    layout_audio_player.setVisibility(View.VISIBLE);
                }
            }else{
                isVoiceRecorded = false;
                controlObject.setItemSelected(isVoiceRecorded);
                if (layout_audio_player != null) {
                    layout_audio_player.setVisibility(View.GONE);
                }
                displayAlignmentAndSettings();
            }


        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getPlayViewVoiceRecording", e);
        }
    }

    public LinearLayout getLl_control_ui(){
        return ll_control_ui;
    }
    public LinearLayout getLl_cg(){
        return ll_cg;
    }

    public LinearLayout getLl_main_inside(){
        return ll_main_inside;
    }
    public LinearLayout getLayout_control(){
        return layout_control;
    }
    public CustomTextView getTv_displayName(){
        return tv_displayName;
    }
    public LinearLayout getLl_displayName(){
        return ll_displayName;
    }

    public LinearLayout getLl_recorder_upload(){
        return ll_recorder_upload;
    }

    public void displayAlignmentAndSettings(){
        try {
            if (controlObject.getDisplayNameAlignment() != null && (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6") ||
                    controlObject.getDisplayNameAlignment().equalsIgnoreCase("7"))) {
                iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_voice_input_white));
                ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.camera_button_bg));

            } else {
                iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_voice_input));
                if (ll_tap_text != null) {
//                    ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle_default));
                    ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.control_default_background));
                }
            }
            if (layout_upload_file != null && !controlObject.isDisable()) {
                layout_upload_file.setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_file_default));
            }
            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        }catch (Exception e){
            Log.getStackTraceString(e);
        }
    }


    public ControlObject getControlObject() {
        return controlObject;
    }

    public void clear(){
        mRecorder = new MediaRecorder();
        mediaPlayer.stop();
        chronometerRecord.setVisibility(View.GONE);
        if(tv_startRecording != null) {
            tv_startRecording.setVisibility(View.VISIBLE);
        }
        fileName = null;
        tv_displayName.setTag(fileName);
        seekBar.setProgress(0);
        controlObject.setAudioData(fileName);
        layout_control.setVisibility(View.VISIBLE);
        if(layout_audio_player != null) {
            layout_audio_player.setVisibility(View.GONE);
        }
        if(ivPlayAudio != null) {
            ivPlayAudio.setVisibility(View.VISIBLE);
        }
        if(ivPauseAudio != null) {
            ivPauseAudio.setVisibility(View.GONE);
        }
        isVoiceRecorded = false;
        controlObject.setItemSelected(isVoiceRecorded);
        controlObject.setText("");
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