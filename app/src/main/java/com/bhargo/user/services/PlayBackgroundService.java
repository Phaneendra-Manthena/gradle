package com.bhargo.user.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.bhargo.user.Java_Beans.ControlObject;

import java.io.IOException;

public class PlayBackgroundService extends Service {
    private static final String TAG = null;
    MediaPlayer mediaPlayer;

    public IBinder onBind(Intent arg0) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            ControlObject controlObject = new ControlObject();
            mediaPlayer.setDataSource(controlObject.getAudioData());
            mediaPlayer.prepare();
            mediaPlayer.start();
            System.out.println("onClickAudioFileNameBG: " + controlObject.getAudioData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("PlayBackgroundService: " + "onCreate");

    }

    @SuppressLint("WrongConstant")
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        System.out.println("PlayBackgroundService: " + "onStartCommand");
        return START_STICKY;
    }

    public void onStart(Intent intent, int startId) {
        // TO DO
        System.out.println("PlayBackgroundService: " + "onStart");
    }

    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {

    }

    public void onPause() {

    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        System.out.println("PlayBackgroundService: " + "onDestroy");
    }

    @Override
    public void onLowMemory() {

    }
}