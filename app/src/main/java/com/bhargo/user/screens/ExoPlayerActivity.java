package com.bhargo.user.screens;

import android.app.Activity;
import android.app.PictureInPictureParams;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhargo.user.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.ui.PlayerView;

public class ExoPlayerActivity extends AppCompatActivity {
    ExoPlayer exoPlayer;
    ImageView bt_fullscreen;
    boolean isFullScreen = false;
    boolean isLock = false;
    Handler handler;

    boolean isInPipMode = false;
    boolean isPIPModeeEnabled = true;
    String mUrl;
    //SimpleExoPlayer player;
    long videoPosition = 0;
    PlayerView playerView;
    String ARG_VIDEO_POSITION = "ExoPlayerActivity.POSITION";
    ImageView exo_eixt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);
        if (getIntent().getExtras() == null || !getIntent().hasExtra("URL")) {
            finish();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        handler = new Handler(Looper.getMainLooper());

        playerView = findViewById(R.id.player);
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        exo_eixt= findViewById(R.id.exo_eixt);
        bt_fullscreen = findViewById(R.id.bt_fullscreen);
        ImageView bt_lockscreen = findViewById(R.id.exo_lock);
        //toggle button with change icon fullscreen or exit fullscreen
        //the screen can rotate base on you angle direction sensor
        bt_fullscreen.setOnClickListener(view ->
        {
            if (!isFullScreen) {
                bt_fullscreen.setImageDrawable(
                        ContextCompat
                                .getDrawable(getApplicationContext(), R.drawable.ic_baseline_fullscreen_exit));
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            } else {
                bt_fullscreen.setImageDrawable(ContextCompat
                        .getDrawable(getApplicationContext(), R.drawable.ic_baseline_fullscreen));
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            isFullScreen = !isFullScreen;
        });
        exo_eixt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_lockscreen.setOnClickListener(view ->
        {
            //change icon base on toggle lock screen or unlock screen
            if (!isLock) {
                bt_lockscreen.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_lock));
            } else {
                bt_lockscreen.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_outline_lock_open));
            }
            isLock = !isLock;
            //method for toggle will do next
            lockScreen(isLock);
        });
        //instance the player with skip back duration 5 second or forward 5 second
        //5000 millisecond = 5 second
        exoPlayer = new ExoPlayer.Builder(this)
                .setSeekBackIncrementMs(5000)
                .setSeekForwardIncrementMs(5000)
                .build();
        playerView.setPlayer(exoPlayer);
        //screen alway active
        playerView.setKeepScreenOn(true);
        //track state
        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                //when data video fetch stream from internet
                if (playbackState == Player.STATE_BUFFERING) {
                    progressBar.setVisibility(View.VISIBLE);

                } else if (playbackState == Player.STATE_READY) {
                    //then if streamed is loaded we hide the progress bar
                    progressBar.setVisibility(View.GONE);
                }

                if (!exoPlayer.getPlayWhenReady()) {
                    handler.removeCallbacks(updateProgressAction);
                } else {
                    onProgress();
                }
            }

            @Override
            public void onPlayerError(PlaybackException error) {
                setResult(Activity.RESULT_CANCELED);
                finishAndRemoveTask();
            }
        });

        //Use Media Session Connector from the EXT library to enable MediaSession Controls in PIP.
        MediaSessionCompat mediaSession = new MediaSessionCompat(this, getPackageName());
        MediaSessionConnector mediaSessionConnector = new MediaSessionConnector(mediaSession);
        mediaSessionConnector.setPlayer(exoPlayer);
        mediaSession.setActive(true);

        //pass the video link and play

        String videoPath = getIntent().getStringExtra("URL");

        Uri videoUrl = Uri.parse(videoPath);
        // videoUrl = Uri.parse("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4");
        MediaItem media = MediaItem.fromUri(videoUrl);
        exoPlayer.setMediaItem(media);
        exoPlayer.prepare();
        exoPlayer.play();
    }

    private void onProgress() {
        ExoPlayer player = exoPlayer;
        long position = player == null ? 0 : player.getCurrentPosition();
        handler.removeCallbacks(updateProgressAction);
        int playbackState = player == null ? Player.STATE_IDLE : player.getPlaybackState();
        if (playbackState != Player.STATE_IDLE && playbackState != Player.STATE_ENDED) {
            long delayMs;
            if (player.getPlayWhenReady() && playbackState == Player.STATE_READY) {
                delayMs = 1000 - position % 1000;
                if (delayMs < 200) {
                    delayMs += 1000;
                }
            } else {
                delayMs = 1000;
            }
            handler.postDelayed(updateProgressAction, delayMs);
        }
    }

    void lockScreen(boolean lock) {
        //just hide the control for lock screen and vise versa
        LinearLayout sec_mid = findViewById(R.id.sec_controlvid1);
        LinearLayout sec_bottom = findViewById(R.id.sec_controlvid2);
        if (lock) {
            sec_mid.setVisibility(View.INVISIBLE);
            sec_bottom.setVisibility(View.INVISIBLE);
        } else {
            sec_mid.setVisibility(View.VISIBLE);
            sec_bottom.setVisibility(View.VISIBLE);
        }
    }    private final Runnable updateProgressAction = () -> onProgress();

    //when is in lock screen we not accept for backpress button
    @Override
    public void onBackPressed() {
        //on lock screen back press button not work
        if (isLock) return;

        //if user is in landscape mode we turn to portriat mode first then we can exit the app.
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            bt_fullscreen.performClick();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
                && isPIPModeeEnabled) {
            enterPIPMode();
        } else super.onBackPressed();


    }

    // pause or release the player prevent memory leak
    @Override
    protected void onStop() {
        super.onStop();
        //exoPlayer.stop();

        playerView.setPlayer(null);
        exoPlayer.release();
        //PIPmode activity.finish() does not remove the activity from the recents stack.
        //Only finishAndRemoveTask does this.
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                && getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) {
            finishAndRemoveTask();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoPosition > 0L && !isInPipMode) {
            exoPlayer.seekTo(videoPosition);
        }
        //Makes sure that the media controls pop up on resuming and when going between PIP and non-PIP states.
        playerView.setUseController(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.release();
    }

    @Override
    protected void onPause() {
        videoPosition = exoPlayer.getCurrentPosition();
        super.onPause();
        exoPlayer.pause();


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(ARG_VIDEO_POSITION, exoPlayer.getCurrentPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        videoPosition = savedInstanceState.getLong(ARG_VIDEO_POSITION);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        if (newConfig != null) {
            videoPosition = exoPlayer.getCurrentPosition();
            isInPipMode = !isInPictureInPictureMode;
        }
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
    }

    //Called when the user touches the Home or Recents button to leave the app.
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        enterPIPMode();
    }

    void enterPIPMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) {
            videoPosition = exoPlayer.getCurrentPosition();
            playerView.setUseController(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PictureInPictureParams.Builder params = new PictureInPictureParams.Builder();
                this.enterPictureInPictureMode(params.build());
            } else {
                this.enterPictureInPictureMode();
            }
            /* We need to check this because the system permission check is publically hidden for integers for non-manufacturer-built apps
               https://github.com/aosp-mirror/platform_frameworks_base/blob/studio-3.1.2/core/java/android/app/AppOpsManager.java#L1640

               ********* If we didn't have that problem *********
                val appOpsManager = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
                if(appOpsManager.checkOpNoThrow(AppOpManager.OP_PICTURE_IN_PICTURE, packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA).uid, packageName) == AppOpsManager.MODE_ALLOWED)

                30MS window in even a restricted memory device (756mb+) is more than enough time to check, but also not have the system complain about holding an action hostage.
             */
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkPIPPermission();
                }
            }, 30);
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    void checkPIPPermission() {
        isPIPModeeEnabled = isInPictureInPictureMode();
        if (!isInPictureInPictureMode()) {
            onBackPressed();
        }
    }




}