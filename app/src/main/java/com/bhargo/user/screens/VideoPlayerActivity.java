package com.bhargo.user.screens;

import android.app.PictureInPictureParams;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Rational;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.bhargo.user.BuildConfig;
import com.bhargo.user.R;
import com.bhargo.user.utils.ImproveHelper;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoPlayerActivity extends YouTubeBaseActivity {

//    private YouTubePlayerView youtube_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);

        String url = getIntent().getStringExtra("URL");
        System.out.println("url==="+url);

        WebView webView = findViewById(R.id.webView);
        VideoView videoView = findViewById(R.id.video_view);
        YouTubePlayerView youtube_view = findViewById(R.id.youtube_view);
        ImageView video_close = findViewById(R.id.video_close);
        ImageView video_minimize = findViewById(R.id.video_minimize);
        ImageView video_pause = findViewById(R.id.video_pause);
        ImageView video_resume = findViewById(R.id.video_resume);

        if(url.contains("www.youtube.com")){
            webView.setVisibility(View.GONE);
            videoView.setVisibility(View.GONE);
            video_pause.setVisibility(View.GONE);
            video_resume.setVisibility(View.GONE);
            youtube_view.setVisibility(View.VISIBLE);
            youtube_view.initialize(BuildConfig.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    if(!b){
                        youTubePlayer.loadVideo(ImproveHelper.getYoutubeID(url));
                    }
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                }
            });
/*
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient() {


            } );

            webView.loadUrl(url);*/

        }else{
            webView.setVisibility(View.GONE);
            youtube_view.setVisibility(View.GONE);

            videoView.setVisibility(View.VISIBLE);

            video_resume.setVisibility(View.GONE);
            video_pause.setVisibility(View.GONE);

            videoView.setVideoPath(url);
            videoView.start();

            MediaController vidControl = new MediaController(this);
            vidControl.setAnchorView(videoView);
            videoView.setMediaController(vidControl);


            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                   /* LinearLayout viewGroupLevel1 = (LinearLayout)  vidControl.getChildAt(0);
                    LinearLayout viewGroupLevel2 = (LinearLayout) viewGroupLevel1.getChildAt(0);
                    View view1 = viewGroupLevel2.getChildAt(1);
                    View view2 = viewGroupLevel2.getChildAt(2);
                    View view3 = viewGroupLevel2.getChildAt(3);
                    view1.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);*/
                }
            });

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {

                    mediaPlayer.seekTo(1);
                    video_resume.setVisibility(View.VISIBLE);

                }
            });





            video_minimize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(video_pause.getVisibility()==View.GONE){
                        video_pause.setVisibility(View.VISIBLE);
                    }else if(video_pause.getVisibility()==View.VISIBLE){
                        video_pause.setVisibility(View.GONE);
                    }
                }
            });


            video_pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    video_pause.setVisibility(View.GONE);
                    video_resume.setVisibility(View.VISIBLE);
                    videoView.pause();
                }
            });

            video_resume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    video_resume.setVisibility(View.GONE);
                    videoView.start();
                }
            });

        }
        video_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }

    /*
      Picture in picture mode
    */
    private void openVideoInPIP(){
        Display d = getWindowManager()
                .getDefaultDisplay();
        Point p = new Point();
        d.getSize(p);
        int width = 300;
        int height = 200;

        Rational ratio
                = new Rational(width, height);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PictureInPictureParams.Builder
                    pip_Builder
                    = new PictureInPictureParams
                    .Builder();
            pip_Builder.setAspectRatio(ratio).build();
            enterPictureInPictureMode(pip_Builder.build());
        }
    }


}
