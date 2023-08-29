package com.bhargo.user.controls.standard;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisable;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;
import static com.bhargo.user.utils.ImproveHelper.showToast;
import static android.content.Intent.ACTION_PICK;
import static android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.core.content.ContextCompat;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.R;
import com.bhargo.user.controls.variables.PathVariables;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.FilePathUtils;
import com.bhargo.user.utils.ImproveHelper;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.iamkdblue.videocompressor.VideoCompress;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class VideoRecording implements PathVariables, UIVariables {

    private static final String TAG = "VideoRecording";
    public static String vpath;
    private final int VideoRecordingTAG = 0;
    public Uri strVideoRecViewUri;
    CustomTextView tv_startVideoRecorder;
    Activity context;
    Context contextVideo;
    ControlObject controlObject;
    LinearLayout linearLayout, ll_displayName, ll_tap_text, ll_videoPlayer_main,ll_main_inside,ll_label,layout_control,ll_recorder_upload;
    ImageView iv_textTypeImage;
    int position;
    String strServerPath;
    ImproveHelper improveHelper;
    private CustomTextView tv_displayName, tv_hint,ct_showText;
    private ImageView iv_mandatory;
    private View view;
    private Uri videoFileNameURI;
    private String saveVideoFormatIn = ".mp4";
    private boolean isVideoRecorded;
    private CustomTextView ct_alertVideoRecording;
    private boolean isCompleted = false;
    private Intent captureVideoIntent;
    VideoView videoView;
    ImageView imageVideoPlay, iv_delete;
    boolean SubformFlag = false;
    int SubformPos;
    String SubformName;
    String fromCameraOrGalley;
    Uri fromCameraOrGalleyURI;
    public VideoRecording(Activity context, ControlObject controlObject, boolean SubformFlag, int SubformPos, String SubformName) {
        this.context = context;
        this.contextVideo = context;
        this.controlObject = controlObject;
        this.SubformFlag = SubformFlag;
        this.SubformPos = SubformPos;
        this.SubformName = SubformName;
        improveHelper = new ImproveHelper(context);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        initView();

    }


    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile1(int type, String saveVideoFormatIn) {

        // Check that the SDCard is mounted
        File root = android.os.Environment.getExternalStorageDirectory();
        File mediaStorageDir = new File(root.getAbsolutePath() + "/ImproveUserVideoRecorder/Videos/");

        // Create the storage directory(MyCameraVideo) if it does not exist
        if (!mediaStorageDir.exists()) {

            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraVideo", "Failed to create directory MyCameraVideo.");
                return null;
            }
        }

        // Create a media file name

        // For unique file name appending current timeStamp with file name
        java.util.Date date = new java.util.Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(date.getTime());

        File mediaFile;

        if (type == MEDIA_TYPE_VIDEO) {
            // For unique video file name appending current timeStamp with file name
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + "." + saveVideoFormatIn);

        } else {
            return null;
        }

        return mediaFile;
    }

    /**
     * Create a file Uri for saving an image or video
     */
    private Uri getOutputMediaFileUri(int type, String saveVideoFormatIn) {

        return Uri.fromFile(getOutputMediaFile(type, saveVideoFormatIn));
    }

    private File getOutputMediaFile(int type, String saveVideoFormatIn) {
        File mediaFile;
        File fileStorage = ImproveHelper.createFolder(context, "ImproveUserFiles/ImproveUser_VideoRecorder");
        String filePath = "Video_" + System.currentTimeMillis() + saveVideoFormatIn;
        mediaFile = new File(fileStorage, filePath);//Create Output file in Main File
        return mediaFile;
    }

    private void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            addVideoRecordingStrip(context);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    public LinearLayout getVideoRecorderView() {
        return linearLayout;
    }

    TextView msg;
    ProgressBar progressBar;
    public Dialog dialog;

    private void showProgressDialog(String message) {
        if (dialog != null && dialog.isShowing()) {
            msg.setText(message);
        } else {
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom_progress_dialog);
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.show();
            msg = dialog.findViewById(R.id.msg);
            msg.setText(message);
            progressBar = dialog.findViewById(R.id.progress_bar);
        }
    }

    private void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void setVideoPathFromOnActivityResult(Uri videoUriPath, String videoFrom) {


        if (controlObject.isEnableCompression()) {
            Uri UriFile = getOutputMediaFileUri(MEDIA_TYPE_VIDEO, saveVideoFormatIn);
            String compressVideoPathStr = UriFile.getPath();
            VideoCompress.compressVideo(videoUriPath.getPath(), compressVideoPathStr, controlObject.getCompressionQuality(),new VideoCompress.CompressListener() {
                @Override
                public void onStart() {
                    showProgressDialog("Please wait compressing...");
                }

                @Override
                public void onSuccess(String compressVideoPath) {
                    dismissProgressDialog();
                    Log.d(TAG, "onActivityResultVideoRec: " + compressVideoPath);
                    File file = new File(compressVideoPath);
                    Uri compressUri = Uri.fromFile(file);
                    //String name = file.getName();
                    //float size = file.length() / 1024f;
                    setVideoRecViewUri(compressUri);
                    setIsVideoRecorded(true);
//                    getControlRealPath().setTag(compressUri);
                    if (videoFrom.equalsIgnoreCase("Gallery")) {
                        FilePathUtils filePathHelper = new FilePathUtils();
                        String path = filePathHelper.getFilePathFromURI(compressUri, context);
                        getControlRealPath().setTag(path);
                    } else {
                        getControlRealPath().setTag(compressUri);
                    }
                    setPath(String.valueOf(compressUri));
                    displayVideoPreview();
                }

                @Override
                public void onFail() {
                    dismissProgressDialog();
                    showToast(context, "Compress Failed!");
                }

                @Override
                public void onProgress(float percent) {
//                    showProgressDialog("Please wait compressing..." + percent + "%");
                    showProgressDialog("Please wait...");
                }
            });
        } else {
            if(controlObject.isEnableVideoFormat() && controlObject.getVideoFormatNames()!=null && controlObject.getVideoFormatNames().size()>0){
                String fileFormat = String.valueOf(videoUriPath).substring(String.valueOf(videoUriPath).lastIndexOf(".") + 1);
                if(!controlObject.getVideoFormatNames().contains(fileFormat.toUpperCase())){
                    Toast.makeText(context, fileFormat+" format not allowed", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if (videoFrom.equalsIgnoreCase("Gallery")) {
                FilePathUtils filePathHelper = new FilePathUtils();
                String path = filePathHelper.getFilePathFromURI(videoUriPath, context);
                getControlRealPath().setTag(path);
//                getControlRealPath().setTag(videoUriPath);
            } else {
                getControlRealPath().setTag(videoUriPath);
            }
            setFromCameraOrGalleyURI(videoUriPath);
            setFromCameraOrGalley(videoFrom);
            setVideoRecViewUri(videoUriPath);
            setIsVideoRecorded(true);
            setPath(String.valueOf(videoUriPath));
            displayVideoPreview();
        }
        controlObject.setItemSelected(isVideoRecorded());
        controlObject.setText(getControlRealPath().getTag().toString());

    }

    public void setVideoRecViewUri(Uri strViewURi) {
        try {

            Log.d("RecViewUri", String.valueOf(strViewURi));
            videoFileNameURI = strViewURi;
            setPath(strViewURi.toString());
            if(strViewURi != null && ImproveHelper.validateUri(context,strViewURi.toString())) {
                getVideoFileToBase64(strViewURi);
            }
            ll_videoPlayer_main.setVisibility(View.VISIBLE);
            if(ll_tap_text != null) {
                ll_tap_text.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setVideoRecViewUri", e);
        }
    }

    public String getVideoFileToBase64(Uri videoFileNameURI) {
        String strFileBase64= "";
        try {
            File file = new File(videoFileNameURI.getPath());
//            File file = new File("file:///storage/emulated/0/Download/ImproveUserFiles/ImproveUser_VideoRecorder/Video_1680517017871.mp4");
            int size = (int) file.length();
            byte[] bytes = new byte[size];
            try {
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(bytes, 0, bytes.length);
//            strFileBase64 = Base64.encodeToString(bytes, Base64.NO_WRAP);
                strFileBase64 = Base64.encodeToString(bytes, Base64.DEFAULT);
                buf.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getVideoFileToBase64", e);
        }
        return strFileBase64;
    }

    public void addVideoRecordingStrip(final Activity context) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = lInflater.inflate(R.layout.control_video_recording, null);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                /*if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("2")) {

                    view = linflater.inflate(R.layout.control_video_recording_two, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {

                    view = linflater.inflate(R.layout.control_video_recording_three, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("5")) {

                    view = linflater.inflate(R.layout.control_video_recording_five, null);
                } else*/ if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {

                    view = linflater.inflate(R.layout.control_video_recording_six, null);
                } else if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {

                    view = linflater.inflate(R.layout.control_video_recording_seven, null);
                }else {

                    //view = linflater.inflate(R.layout.control_video_recording, null);
                    view = linflater.inflate(R.layout.layout_video_recording_default, null);
                }
            } else {

                //view = linflater.inflate(R.layout.control_video_recording, null);
                view = linflater.inflate(R.layout.layout_video_recording_default, null);
            }
            view.setTag(VideoRecordingTAG);

            tv_displayName = view.findViewById(R.id.tv_displayName);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            ct_alertVideoRecording = view.findViewById(R.id.ct_alertTypeText);
            tv_startVideoRecorder = view.findViewById(R.id.tv_startVideoRecorder);
            iv_textTypeImage = view.findViewById(R.id.iv_textTypeImage);
            tv_hint = view.findViewById(R.id.tv_hint);
            iv_mandatory = view.findViewById(R.id.iv_mandatory);
            ll_tap_text = view.findViewById(R.id.ll_tap_text);
            ll_videoPlayer_main = view.findViewById(R.id.ll_videoPlayer_main);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);

            ll_label = view.findViewById(R.id.ll_label);
            layout_control = view.findViewById(R.id.layout_control);
            ll_recorder_upload = view.findViewById(R.id.ll_recorder_upload);
            videoView = view.findViewById(R.id.videoView);
            imageVideoPlay = view.findViewById(R.id.iv_videoPlay);
            iv_delete = view.findViewById(R.id.iv_delete);
            ct_showText = view.findViewById(R.id.ct_showText);
            if(tv_startVideoRecorder != null) {
                tv_startVideoRecorder.setTag(controlObject.getControlName());
            }
            if(iv_textTypeImage != null) {
                iv_textTypeImage.setTag(controlObject.getControlName());
            }
            if(iv_delete != null) {
                iv_delete.setTag(controlObject.getControlName());
            }
            videoView.setTag(controlObject.getControlName());
            imageVideoPlay.setTag(controlObject.getControlName());
            getImageViewVideo().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                    //videoRecording.getVideoPlayPreview(v);
                    playVideo();
                }
            });
            if(iv_delete != null) {
                iv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setIsVideoRecorded(false);
                        AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                        ll_videoPlayer_main.setVisibility(View.GONE);
                        if(ll_tap_text != null) {
                            ll_tap_text.setVisibility(View.VISIBLE);
                        }
                        setPath(null);
                        //getStartVideoRecorderClick(null, REQUEST_VIDEO_RECORDING,REQ_CODE_PICK_ONLY_VIDEO_FILE);

                    }
                });
            }

            setControlValues();

            linearLayout.addView(view);
        } catch (Exception e) {
            Log.getStackTraceString(e);
            ImproveHelper.improveException(context, TAG, "addVideoRecordingStrip", e);
        }
    }


    private void setControlValues() {
        try {
            if (controlObject.getDisplayName() != null) {
                ll_displayName.setVisibility(View.VISIBLE);
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
            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            }
            if (controlObject.isDisable()) {
//                setViewDisable(view, false);
                improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), !controlObject.isDisable(), layout_control, view);
            }
            if (controlObject.isEnableVideoMaximumDuration()) {
                Log.d("XMLVideoRecMaxParam", controlObject.getVideoMaximumDuration());
                Log.d("XMLVideoRecMaxMessage", controlObject.getMaximumDurationError());
            }
            if (controlObject.isEnableVoiceMinimumDuration()) {
                Log.d("XMLVideoRecMinParam", controlObject.getVideoMinimumDuration());
                Log.d("XMLVideoRecMinMessage", controlObject.getMinimumDurationError());
            }

            if (controlObject.getVideoFormatIds() != null) {
//            saveVideoFormatIn = controlObject.getAudioFormatIds();
                saveVideoFormatIn = ".mp4";
            }


            if (controlObject.getControlValue() != null && !controlObject.getControlValue().isEmpty()
                    && !controlObject.getControlValue().equalsIgnoreCase("File not Found")
                    && !controlObject.getControlValue().equalsIgnoreCase(" ")) {
                ll_videoPlayer_main.setVisibility(View.VISIBLE);
                strServerPath = controlObject.getControlValue();
                if(ll_tap_text != null) {
                    ll_tap_text.setVisibility(View.GONE);
                }
                isVideoRecorded = true;
            } else {
                ll_videoPlayer_main.setVisibility(View.GONE);
                if(ll_tap_text != null) {
                    ll_tap_text.setVisibility(View.VISIBLE);
                }
            }

            if(iv_textTypeImage != null) {
                iv_textTypeImage.setVisibility(View.VISIBLE);
                iv_textTypeImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_video_recording));
            }

            setDisplaySettings(context, tv_displayName, controlObject);
            setTextSize(controlObject.getTextSize());
            setTextColor(controlObject.getTextHexColor());
            setTextStyle(controlObject.getTextStyle());
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    public void startVideoRecording(int recCode) {
        try {
            captureVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            Uri UriFile = getOutputMediaFileUri(MEDIA_TYPE_VIDEO, saveVideoFormatIn);
            if (controlObject.isEnableVideoMaximumDuration()) {
                if (controlObject.getVideoMaximumDuration() != null) {
                    Float dur = Float.parseFloat(controlObject.getVideoMaximumDuration()) * 60;
                    int duration = Math.round(dur);
                    captureVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, Math.round(duration));
                }
            }
            captureVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, UriFile);
            context.startActivityForResult(captureVideoIntent, recCode);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "startVideoRecording", e);
        }
    }

    public void displayVideoPreview() {
        try {
            if(ll_tap_text != null) {
                ll_tap_text.setVisibility(View.GONE);
            }
            imageVideoPlay.setVisibility(View.VISIBLE);
            MediaController mediaController = new MediaController(context);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            //Location of Media File

            Log.d("videoFileNameURI", String.valueOf(videoFileNameURI));

            Uri uri = Uri.parse(String.valueOf(videoFileNameURI));
            //Starting VideView By Setting MediaController and URI

            if (controlObject.getControlValue() != null && !controlObject.getControlValue().isEmpty() && !controlObject.getControlValue().equalsIgnoreCase("File not Found")) {
                videoView.setVideoPath(strServerPath);
            } else {
//            uri = Uri.parse(String.valueOf(videoFileNameURI));
                videoView.setVideoURI(uri);
            }
            videoView.requestFocus();

            //videoView.start();
            //   videoView.stopPlayback();
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    isCompleted = true;
                    imageVideoPlay.setVisibility(View.VISIBLE);
                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "displayVideoPreview", e);
        }
    }

    public void setIsVideoRecorded(boolean isVideoRecorded) {
        this.isVideoRecorded = isVideoRecorded;
    }

    public boolean isVideoRecorded() {
        return isVideoRecorded;
    }

    public CustomTextView setAlertVideoRecording() {

        return ct_alertVideoRecording;
    }


    public CustomTextView getControlRealPath() {
        return tv_displayName;
    }


    public CustomTextView getTv_startVideoRecorder() {
        return tv_startVideoRecorder;
    }

    public ImageView getIv_textTypeImage() {
        return iv_textTypeImage;
    }


    public void getStartVideoRecorderClick(View v_bs, int recCode, int uploadRecCode) {
        try {
            ct_alertVideoRecording.setVisibility(View.GONE);
            if(ll_tap_text != null) {
                ll_tap_text.setBackground(ContextCompat.getDrawable(context, R.drawable.control_default_background));
            }

            if (controlObject.isEnableUploadVideoFile()) {
                LinearLayout bottom_sheet = v_bs.findViewById(R.id.bottom_sheet);
                BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
                LinearLayout bs_ll_camera = v_bs.findViewById(R.id.bs_ll_camera);
                LinearLayout bs_ll_gallery = v_bs.findViewById(R.id.bs_ll_gallery);
                CustomTextView ct_Gallery = v_bs.findViewById(R.id.ct_Gallery);
                CustomTextView ct_Camera = v_bs.findViewById(R.id.ct_Camera);
                CustomTextView ct_Cancel = v_bs.findViewById(R.id.ct_Cancel);
                ImageView ivCamera = v_bs.findViewById(R.id.ivCamera);
                ivCamera.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_video_recording));
                ImproveHelper.showHideBottomSheet(sheetBehavior);
                bs_ll_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startVideoRecording(recCode);
                        ImproveHelper.showHideBottomSheet(sheetBehavior);
                    }
                });

                bs_ll_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getVideoUploadFile(v, uploadRecCode);
                        ImproveHelper.showHideBottomSheet(sheetBehavior);
                    }
                });
                ct_Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImproveHelper.showHideBottomSheet(sheetBehavior);
                    }
                });
            } else {
                startVideoRecording(recCode);
                if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
//                    if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("7")) {
//                        if(tv_startVideoRecorder != null) {
//                            tv_startVideoRecorder.setBackgroundDrawable(context.getDrawable(R.drawable.rectangle_border_ui));
//                            tv_startVideoRecorder.setTextColor(ContextCompat.getColor(context, R.color.quantum_grey));
//                            setTextViewDrawableColor(tv_startVideoRecorder, R.color.red);
//                        }
//                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getStartVideoRecorderClick", e);
        }
    }


    public ImageView getImageViewVideo() {
        return imageVideoPlay;
    }


    public void getVideoUploadFile(View v, int reqCode) {
        try {
            Intent intent;
            intent = new Intent(ACTION_PICK, EXTERNAL_CONTENT_URI);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("video/*");

            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra("return-data", true);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            context.startActivityForResult(Intent.createChooser(intent, "select_video_file_title"), reqCode);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getVideoUploadFile", e);
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public void playVideo() {
        try {
            imageVideoPlay.setVisibility(View.GONE);
            videoView.start();
            videoView.requestFocus();
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "playVideo", e);
        }
    }


    private void setTextViewDrawableColor(TextView textView, int color) {
        try {
            for (Drawable drawable : textView.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setTextViewDrawableColor", e);
        }
    }

    @Override
    public String getPath() {
        return controlObject.getVideoRecordPath();
    }

    @Override
    public void setPath(String path) {
        controlObject.setVideoRecordPath(path);

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
//        setViewDisable(view, !enabled);
        controlObject.setDisable(!enabled);
//        setViewDisableOrEnableDefault(context,view, enabled);
        improveHelper.controlEnableDisableBackground(controlObject.getControlType(), controlObject.getDisplayNameAlignment(), enabled, layout_control, view);
    }

    @Override
    public void clean() {
        videoFileNameURI = null;
        controlObject.setVideoRecordPath("");
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
    /*mandatory,mandatoryErrorMessage,enableVideoMinimumDuration,videoMinimumDuration
      enableVideoMaximumDuration,videoMaximumDuration*/
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

    public boolean isEnableVideoMinimumDuration() {
        return controlObject.isEnableVideoMinimumDuration();
    }

    public void setEnableVideoMinimumDuration(boolean enableVideoMinimumDuration) {
        controlObject.setEnableVideoMinimumDuration(enableVideoMinimumDuration);
    }

    public String getVideoMinimumDuration() {
        return controlObject.getVideoMinimumDuration();
    }

    public void setVideoMinimumDuration(String videoMinimumDuration) {
        controlObject.setVideoMinimumDuration(videoMinimumDuration);
    }

    public boolean isEnableVideoMaximumDuration() {
        return controlObject.isEnableVideoMaximumDuration();
    }

    public void setEnableVideoMaximumDuration(boolean enableVideoMaximumDuration) {
        controlObject.setEnableVideoMaximumDuration(enableVideoMaximumDuration);
    }

    public String getVideoMaximumDuration() {
        return controlObject.getVideoMaximumDuration();
    }

    public void setVideoMaximumDuration(String videoMaximumDuration) {
        controlObject.setVideoMaximumDuration(videoMaximumDuration);
    }

    /*Options*/
    /*hideDisplayName,enableVideoFormat,videoFormat,enableUploadVideoFile
    invisible/visibility
     */

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

    public boolean isEnableVideoFormat() {
        return controlObject.isEnableVideoFormat();
    }

    public void setEnableVideoFormat(boolean enableVideoFormat) {
        controlObject.setEnableVideoFormat(enableVideoFormat);
    }

    public String getVideoFormat() {
//        return controlObject.getVideoFormat();
        return saveVideoFormatIn;
    }

    public void setVideoFormat(String videoFormat) {
//        controlObject.setVideoFormat(videoFormat);
        controlObject.setVideoFormat(saveVideoFormatIn);
    }

    public boolean isEnableUploadVideoFile() {
        return controlObject.isEnableUploadVideoFile();
    }

    public void setEnableUploadVideoFile(boolean enableUploadVideoFile) {
        controlObject.setEnableUploadVideoFile(enableUploadVideoFile);
        if (enableUploadVideoFile) {

        } else {

        }
    }

    public LinearLayout getLl_tap_text() { // linear for taptext

        return ll_tap_text;
    }

    public void setVideoRecordingPath(String path) {
        try {
            if (path != null && !path.trim().isEmpty()) {
                strServerPath = path;
                tv_displayName.setTag(strServerPath);
                videoView.setVideoPath(strServerPath);
                controlObject.setItemSelected(true);
                controlObject.setText(strServerPath);
                controlObject.setControlValue(strServerPath);
                ll_videoPlayer_main.setVisibility(View.VISIBLE);
                isVideoRecorded = true;
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
            ImproveHelper.improveException(context, TAG, "getPlayViewVoiceRecording", e);
        }
    }

    public LinearLayout getLl_label(){
        return ll_label;
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

    public LinearLayout getLl_recorder_upload(){
        return ll_recorder_upload;
    }

    public ControlObject getControlObject() {
        return controlObject;
    }


    public String getFromCameraOrGalley() {
        return fromCameraOrGalley;
    }

    public void setFromCameraOrGalley(String fromCameraOrGalley) {
        this.fromCameraOrGalley = fromCameraOrGalley;
    }

    public Uri getFromCameraOrGalleyURI() {
        return fromCameraOrGalleyURI;
    }

    public void setFromCameraOrGalleyURI(Uri fromCameraOrGalleyURI) {
        this.fromCameraOrGalleyURI = fromCameraOrGalleyURI;
    }

    public void clear(){
        controlObject.setText("");
        setIsVideoRecorded(false);
        ll_videoPlayer_main.setVisibility(View.GONE);
        setPath(null);
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