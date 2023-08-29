package com.bhargo.user.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.bhargo.user.controls.standard.VideoRecording;
//import com.iceteck.silicompressorr.SiliCompressor;

import java.io.File;

public class CompressVideo extends AsyncTask<String, String, String> {

    Dialog dialog;
    Context context;
    VideoRecording videoRecording;

    public CompressVideo(Context context, VideoRecording videoRecording) {
        this.context = context;
        this.videoRecording = videoRecording;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "", "Please Wait! Compressing...");
    }

    @Override
    protected String doInBackground(String... strings) {
        // Initialize video path
        String videoPath = null;

        try {
            // Initialize uri
            Uri uri = Uri.parse(strings[1]);
            // Compress video
            //videoPath = SiliCompressor.with(context).compressVideo(uri, strings[2]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Return Video path
        return videoPath;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.dismiss();
        if (s != null) {
            // Initialize file
            File file = new File(s);
            // Initialize uri
            Uri compressUri = Uri.fromFile(file);
            // set video uri
            // videoView2.setVideoURI(uri);
            // Compress video size
            String name = file.getName();
            float size = file.length() / 1024f;
            System.out.println("====Video After Size : %.2f KB " + size + "\n" + name);
            System.out.println("====Video Created Path" + file.getAbsolutePath());
            videoRecording.setVideoRecViewUri(compressUri);
            videoRecording.setIsVideoRecorded(true);
            videoRecording.getControlRealPath().setTag(compressUri);
            videoRecording.setPath(String.valueOf(compressUri));
            videoRecording.displayVideoPreview();
        } else {
            ImproveHelper.showToast(context, "Failed To Compress Video!");
        }


    }
}
