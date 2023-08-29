package com.bhargo.user.screens;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfRenderer;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.bhargo.user.BuildConfig;
import com.bhargo.user.R;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.FilesTimeSpentModel;
import com.bhargo.user.pojos.InsertUserFileVisitsModel;
import com.bhargo.user.pojos.InsertUserFileVisitsResponse;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bhargo.user.utils.ZoomableImageView;
//import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;
import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

public class ELearningViewActivity extends BaseActivity {

    private static final String TAG = "ELearningViewActivity";
    public static int oneTimeOnly = 0;
    private final Handler myHandler = new Handler();
    private final Handler mHandler = new Handler();
    private final int forwardTime = 5000;
    private final int backwardTime = 5000;
    LinearLayout ll_webViewELearning, ll_ElImageMain, ll_videoPlayer_main, ll_videoPlayer, ll_elAudioFile, ll_audioPlayer;
    RelativeLayout ll_pdfViewer_default;
    FrameLayout fl_video, fl_container;
    ImageView iv_ELearning, iv_videoPlay, ivBackwardAudio, ivPlayAudio, ivPauseAudio, ivForwardAudio;
    ZoomableImageView imageViewPdf;
    VideoView el_videoView;
    SeekBar seekBar_audioPlayer;
    String distributionId, parentID, selected_Node_Id, categoryFileID, strTopicName, strFileName, strFileNameURl, strFileNameExt;
    String strDirect, StartDate, StartDisplayTime, EndDate, EndDisplayTime, StartTime, EndTime, strExamDuration, strTopicTitle;
    String hQuestions, mQuestions, lQuestions, tQuestions, Is_Compexcity, isAssessment;
    String NoOfAttempts, NoOfUserAttempts;
    /*ExoPlayer Start*/
    PlayerView playerView;
    //    File file;
    //    PDFView pdfView;
    FloatingActionButton prePageButton, nextPageButton;
    ImproveDataBase improveDataBase;
    ImproveHelper improveHelper;
    private TextView tx1, tx2;
    private WebView webViewELearning;
    private MediaPlayer mediaPlayer;
    private double startTime = 0;
    private final Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tx1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekBar_audioPlayer.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };
    private double finalTime = 0;
    private SessionManager sessionManager;
    private GetServices getServices;
    private int eLearning_FilesInfoPosition;
    private String strELearning_Type, FromInTasksContent;
    private ExoPlayer player;

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    //    private File strFilepath;
    private String strFilepath;
    private ParcelFileDescriptor parcelFileDescriptor;
    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;
    private int pageIndex;
    private File cDir;
    private File appSpecificExternalDir;
    Context context;

    /*ExoPlayer End*/

    public static int convertToPixels(Context context, int nDP) {
        float conversionScale = 0;
        try {
            conversionScale = context.getResources().getDisplayMetrics().density;
        } catch (Exception e) {
            ImproveHelper improveHelper = new ImproveHelper(context);
            improveHelper.improveException(context, TAG, "convertToPixels", e);
        }
        return (int) ((nDP * conversionScale) + 0.5f);

    }

    private BitmapDrawable writeTextOnDrawable(Context mContext, int drawableId, String text) {

        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId)
                .copy(Bitmap.Config.ARGB_8888, true);

        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(convertToPixels(mContext, 11));

        Rect textRect = new Rect();
        paint.getTextBounds(text, 0, text.length(), textRect);

        Canvas canvas = new Canvas(bm);

        //If the text is bigger than the canvas , reduce the font size
        if (textRect.width() >= (canvas.getWidth() - 4))     //the padding on either sides is considered as 4, so as to appropriately fit in the text
            paint.setTextSize(convertToPixels(mContext, 7));        //Scaling needs to be used for different dpi's

        //Calculate the positions
        int xPos = (canvas.getWidth() / 2) - 2;     //-2 is for regulating the x position offset

        //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));

        canvas.drawText(text, xPos, yPos, paint);

        return new BitmapDrawable(getResources(), bm);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Theme
        setBhargoTheme(this,AppConstants.THEME,AppConstants.IS_FORM_THEME,"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_learning_view);

        initializeActionBar();
        enableBackNavigation(true);
        context = ELearningViewActivity.this;
        improveHelper = new ImproveHelper(this);
        improveDataBase = new ImproveDataBase(ELearningViewActivity.this);
        ib_settings.setVisibility(View.GONE);
        iv_circle_appIcon.setVisibility(View.GONE);

        sessionManager = new SessionManager(this);
        getServices = RetrofitUtils.getUserService();

        /*PDF*/
        ll_webViewELearning = findViewById(R.id.ll_webViewELearning);
        webViewELearning = findViewById(R.id.webViewELearning);
        ll_ElImageMain = findViewById(R.id.ll_ElImageMain);
        iv_ELearning = findViewById(R.id.iv_ELearning);
        /*Video*/
        playerView = findViewById(R.id.video_view);
        ll_videoPlayer_main = findViewById(R.id.ll_videoPlayer_main);
        fl_video = findViewById(R.id.fl_video);
        el_videoView = findViewById(R.id.el_videoView);
        ll_videoPlayer = findViewById(R.id.ll_videoPlayer);
        iv_videoPlay = findViewById(R.id.iv_videoPlay);
        /*Audio*/
        ll_elAudioFile = findViewById(R.id.ll_elAudioFile);
        ll_audioPlayer = findViewById(R.id.ll_audioPlayer);
        seekBar_audioPlayer = findViewById(R.id.seekBar_audioPlayer);
        ivBackwardAudio = findViewById(R.id.ivBackwardAudio);
        ivPlayAudio = findViewById(R.id.ivPlayAudio);
        ivPauseAudio = findViewById(R.id.ivPauseAudio);
        ivForwardAudio = findViewById(R.id.ivForwardAudio);

//        fl_container = findViewById(R.id.fl_container);
//        ll_pdfViewer = findViewById(R.id.ll_pdfViewer);
        ll_pdfViewer_default = findViewById(R.id.ll_pdfViewer_default);
//        pdfView = findViewById(R.id.pdfView);

        imageViewPdf = findViewById(R.id.pdf_image);
        prePageButton = findViewById(R.id.button_pre_doc);
        nextPageButton = findViewById(R.id.button_next_doc);

        tx1 = findViewById(R.id.ctv1);
        tx2 = findViewById(R.id.ctv2);
//        tx1 = new TextView(this);
//        tx2 = new TextView(this);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
// get data via the key
        FromInTasksContent = extras.getString("FromInTasksContent");
        strELearning_Type = extras.getString("ELearning_Type");
        String strELearning_URL = extras.getString("ELearning_URL");

        String strDocsUrl = "http://docs.google.com/gview?embedded=true&url=" + strELearning_URL;

        eLearning_FilesInfoPosition = extras.getInt("ELearning_FilesInfoPosition");
        if(extras.getString("distributionIdView") != null && !extras.getString("distributionIdView").isEmpty()){
            distributionId = extras.getString("distributionIdView");
        }else if(extras.getString("DistributionId") != null && !extras.getString("DistributionId").isEmpty()){
            distributionId = extras.getString("DistributionId");
        }
        parentID = extras.getString("ParentID");
        selected_Node_Id = extras.getString("Selected_Node_Id");
        categoryFileID = extras.getString("CategoryFileID");

        strTopicName = extras.getString("TopicName");
        strFileName = extras.getString("FileName");
        strFileNameExt = extras.getString("FileNameExt");

        strDirect = extras.getString("Direct");
        StartDate = extras.getString("StartDate");
        StartDisplayTime = extras.getString("StartDisplayTime");
        EndDate = extras.getString("EndDate");
        EndDisplayTime = extras.getString("EndDisplayTime");
        StartTime = extras.getString("StartTime");
        EndTime = extras.getString("EndTime");
//        distributionId = extras.getString("DistributionId");

        strExamDuration = extras.getString("ExamDuration");
        strTopicTitle = extras.getString("EL_TopicName");

        hQuestions = extras.getString("hQuestions");
        mQuestions = extras.getString("mQuestions");
        lQuestions = extras.getString("lQuestions");
        tQuestions = extras.getString("tQuestions");
        Is_Compexcity = extras.getString("Is_Compexcity");
        isAssessment = extras.getString("Is_Assessment");
        NoOfAttempts = extras.getString("NoOfAttempts");
        NoOfUserAttempts = extras.getString("NoOfUserAttempts");


        title.setText(strFileName);
        Log.d(TAG, "ELearning_URL_V: " + strELearning_URL);

        String[] strsplit = strELearning_URL.split("/");
        strFileNameURl = strsplit[strsplit.length - 1];

//        strFilepath = AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" +AppConstants.E_LEARNING_FILES+ "/" + strTopicName;
        strFilepath = AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicName;

        cDir = getApplicationContext().getExternalFilesDir(strFilepath); // Directory of the file
        appSpecificExternalDir = new File(cDir.getAbsolutePath(), strFileNameURl); // directory includes file (Android/data/.../filename.png)
        Log.d(TAG, "FileExitsINELV: " + appSpecificExternalDir);

        if (cDir.exists()) {

            if (isFileExistsInPackage(strFilepath, strFileNameURl)) {
//                strELearning_Type =  "png";
                if (strELearning_Type != null && !strELearning_Type.isEmpty()) {

                    Bitmap myBitmap = BitmapFactory.decodeFile(appSpecificExternalDir.getAbsolutePath()); // Images

//                    Uri uri = FileProvider.getUriForFile(ELearningViewActivity.this, getPackageName() + ".provider", appSpecificExternalDir); //videos
                    Uri uri = FileProvider.getUriForFile
                            (Objects.requireNonNull(ELearningViewActivity.this),
                            BuildConfig.APPLICATION_ID + ".fileprovider", appSpecificExternalDir);

                    // For PPts,xls,text... formats
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setAction(Intent.ACTION_VIEW);

                    switch (strELearning_Type.toLowerCase()) {

                        case "jpg":
                            ll_ElImageMain.setVisibility(View.VISIBLE);
                            iv_ELearning.setImageBitmap(myBitmap);

                            break;
                        case "png":
                            ll_ElImageMain.setVisibility(View.VISIBLE);
                            iv_ELearning.setImageBitmap(myBitmap);

                            break;
                        case "jpeg":
                            ll_ElImageMain.setVisibility(View.VISIBLE);
                            iv_ELearning.setImageBitmap(myBitmap);
                            break;
                        case "mp3":

                            initializePlayer(appSpecificExternalDir.toString());
                            break;
                        case "3gp":
                            initializePlayer(appSpecificExternalDir.toString());
                            break;
                        case "mp4":
                            initializePlayer(appSpecificExternalDir.toString());

                            hideSystemUi();
                            break;
                        case "mov":
                            initializePlayer(appSpecificExternalDir.toString());
                            break;
                        case "avi":
                            initializePlayer(strFilepath);
                            break;
                        case "ogg":
                            initializePlayer(appSpecificExternalDir.toString());
                            break;
                        case "webm":
                            initializePlayer(appSpecificExternalDir.toString());
                            break;
                        case "bmp":
                            initializePlayer(appSpecificExternalDir.toString());
                            break;
                        case "gif":
                            initializePlayer(appSpecificExternalDir.toString());
                            break;
                        case "mpeg":
                            initializePlayer(appSpecificExternalDir.toString());
                            break;
                        case "ogv":
                            initializePlayer(appSpecificExternalDir.toString());
                            break;
                        case "flv":
                            initializePlayer(appSpecificExternalDir.toString());
                            break;
                        case "wmv":
                            initializePlayer(appSpecificExternalDir.toString());
                            break;
                        case "youtube":
                            webViewURLData(strELearning_URL);
//                            holder.ll_progressbar.setVisibility(View.GONE);
//                            holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.youtube));
//                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            /*intent.setData(Uri.parse(strELearning_URL));
                            intent.setPackage("com.google.android.youtube");
                            startActivity(intent);
                            finish();*/
                            break;
                        case "pdf":
                            // Working

                            ll_webViewELearning.setVisibility(View.GONE);
                            playerView.setVisibility(View.GONE);
//                            ll_pdfViewer.setVisibility(View.GONE);
                            ll_pdfViewer_default.setVisibility(View.VISIBLE);

                            imageViewPdf.setVisibility(View.VISIBLE);

                            try {
                                pageIndex = 0;
                                openRenderer(getApplicationContext(), strFileNameURl);
                                showPage(pageIndex);

                                prePageButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showPage(currentPage.getIndex() - 1);
                                    }
                                });
                                nextPageButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showPage(currentPage.getIndex() + 1);
                                    }
                                });

                            } catch (IOException e) {

                                Log.d(TAG, "onCreatePDFException: " + e.toString());
                            }

                         /*   ll_webViewELearning.setVisibility(View.GONE);
                            playerView.setVisibility(View.GONE);
                            ll_pdfViewer.setVisibility(View.VISIBLE);
                            pdfView.fromFile(file)
                                    .enableSwipe(true)
                                    .enableAnnotationRendering(true)
                                    .scrollHandle(new DefaultScrollHandle(this))
                                    .load();*/

//                            fl_container.setVisibility(View.VISIBLE);

//                            webViewURLData(strDocsUrl);
//                            webViewURLDataDocs("");
//                            openFragment(new PdfRendererBasicFragment(strFileNameURl, strTopicName), "PdfRendererBasicFragment");
//                            getSupportFragmentManager().beginTransaction()
//                                    .add(R.id.fl_container, new PdfRendererBasicFragment(strFileNameURl,strTopicName),
//                                            "PdfRendererBasicFragment")
//                                    .commit();


//                            try {
//                                Log.d(TAG, "onCreatePDF: PDF StartActivity");
//                                intent.setDataAndType(uri, "application/pdf");
//                                startActivity(intent);
//                            } catch (Exception e) {
////                                // TODO: handle exception

////                            webViewURLDataDocs("");
//                            webViewURLData(strDocsUrl);
////                            webViewURLDataDocs(strELearning_URL);
//                                Log.d(TAG, "onCreatePDFException: " + e.getMessage());
//                            }

                            break;
                        case "ppt":
                            //working
                            if (!isNetworkStatusAvialable(ELearningViewActivity.this)) {
                                try {
                                    intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
                                    startActivity(intent);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    webViewURLData(strDocsUrl);
                                    Log.d(TAG, "onCreateDOC: " + e.getMessage());
                                }
                            } else {
                                webViewURLData(strDocsUrl);
                            }

                            /*try {
                                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
                                startActivity(intent);
                            } catch (Exception e) {
                                // TODO: handle exception
                                webViewURLData(strDocsUrl);
                                Log.d(TAG, "onCreateDOC: " + e.getMessage());
                            }*/
                            break;
                        case "pptx":

                            if (!isNetworkStatusAvialable(ELearningViewActivity.this)) {
                                try {
                                    intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
                                    startActivity(intent);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    webViewURLData(strDocsUrl);
                                    Log.d(TAG, "onCreateDOC: " + e.getMessage());
                                }
                            } else {
                                webViewURLData(strDocsUrl);
                            }

                            break;
                        case "doc":
                            if (!isNetworkStatusAvialable(ELearningViewActivity.this)) {
                                try {
                                    intent.setDataAndType(uri, "application/msword");
                                    startActivity(intent);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    webViewURLData(strDocsUrl);
                                    Log.d(TAG, "onCreateDOC: " + e.getMessage());
                                }
                            } else {
                                webViewURLData(strDocsUrl);
                            }
                            break;
                        case "docx":
                            if (!isNetworkStatusAvialable(ELearningViewActivity.this)) {
                                try {
                                    intent.setDataAndType(uri, "application/msword");
                                    startActivity(intent);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    webViewURLData(strDocsUrl);
                                    Log.d(TAG, "onCreateDOC: " + e.getMessage());
                                }
                            } else {
                                webViewURLData(strDocsUrl);
                            }
                            break;
                        case "txt":
                            if (!isNetworkStatusAvialable(ELearningViewActivity.this)) {
                                try {
                                    intent.setDataAndType(uri, "text/plain");
                                    startActivity(intent);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    webViewURLData(strELearning_URL);
                                    Log.d(TAG, "onCreateDOC: " + e.getMessage());
                                }
                            } else {
                                webViewURLData(strELearning_URL);
                            }
                            break;
                        case "html":
                            if (!isNetworkStatusAvialable(ELearningViewActivity.this)) {
                                try {
                                    intent.setDataAndType(uri, "text/plain");
                                    startActivity(intent);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    webViewURLData(strELearning_URL);
                                    Log.d(TAG, "onCreateDOC: " + e.getMessage());
                                }
                            } else {
                                webViewURLData(strELearning_URL);
                            }
                            break;
                        case "xl":
                            if (!isNetworkStatusAvialable(ELearningViewActivity.this)) {
                                try {
                                    intent.setDataAndType(uri, "application/vnd.ms-excel");
                                    startActivity(intent);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    webViewURLData(strDocsUrl);
                                    Log.d(TAG, "onCreateDOC: " + e.getMessage());
                                }
                            } else {
                                webViewURLData(strDocsUrl);
                            }

                            break;
                        case "xls":
                            if (!isNetworkStatusAvialable(ELearningViewActivity.this)) {
                                try {
                                    intent.setDataAndType(uri, "application/vnd.ms-excel");
                                    startActivity(intent);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    webViewURLData(strDocsUrl);
                                    Log.d(TAG, "onCreateDOC: " + e.getMessage());
                                }
                            } else {
                                webViewURLData(strDocsUrl);
                            }

                            break;
                        case "xlsx":
                            if (!isNetworkStatusAvialable(ELearningViewActivity.this)) {
                                try {
                                    intent.setDataAndType(uri, "application/vnd.ms-excel");
                                    startActivity(intent);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    webViewURLData(strDocsUrl);
                                    Log.d(TAG, "onCreateDOC: " + e.getMessage());
                                }
                            } else {
                                webViewURLData(strDocsUrl);
                            }

                            break;
                        case "js":
                            if (!isNetworkStatusAvialable(ELearningViewActivity.this)) {
                                try {
                                    intent.setDataAndType(uri, "application/vnd.ms-excel");
                                    startActivity(intent);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    webViewURLData(strELearning_URL);
                                    Log.d(TAG, "onCreateDOC: " + e.getMessage());
                                }
                            } else {
                                webViewURLData(strELearning_URL);
                            }

                            break;
                        case "css":

                            webViewURLData(strELearning_URL);
                            break;
                        case "zip":
                            if (!isNetworkStatusAvialable(ELearningViewActivity.this)) {
                                try {
                                    intent.setDataAndType(uri, "application/x-wav");
                                    startActivity(intent);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    webViewURLData(strELearning_URL);
                                    Log.d(TAG, "onCreateDOC: " + e.getMessage());
                                }
                            } else {
                                webViewURLData(strELearning_URL);
                            }
                            break;
                        case "rar":
                            if (!isNetworkStatusAvialable(ELearningViewActivity.this)) {
                                try {
                                    intent.setDataAndType(uri, "application/x-wav");
                                    startActivity(intent);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    webViewURLData(strELearning_URL);
                                    Log.d(TAG, "onCreateDOC: " + e.getMessage());
                                }
                            } else {
                                webViewURLData(strELearning_URL);
                            }
                            break;
                        case "exe":
                            webViewURLData(strELearning_URL);

                            break;
                        default:
                            webViewURLData(strELearning_URL);
                    }

                }
                else {
                    webViewURLData(strELearning_URL);
                }

            } else if (strELearning_Type.equalsIgnoreCase("youtube")) {
                title.setText(strFileName);
                webViewURLData(strELearning_URL);
//                            holder.ll_progressbar.setVisibility(View.GONE);
//                            holder.iv_elImage.setImageDrawable(context.getDrawable(R.drawable.youtube));
//                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            /*intent.setData(Uri.parse(strELearning_URL));
                            intent.setPackage("com.google.android.youtube");
                            startActivity(intent);
                            finish();*/

            } else {
                webViewURLData(strELearning_URL);
//                webViewURLData("http://182.18.157.124/BhargoUploadFiles/Bhargo_BhargoInnovations/BHAR00000001/Elearning/ContentCoverPageFiles/download_(2)_20230421171138662.jpg");
            }
        }else {
            webViewURLData(strELearning_URL);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openRenderer(Context context, String FILENAME) throws IOException {
        try {
            File file = new File(getCacheDir(), FILENAME);
//        File file = new File(Environment.getExternalStorageDirectory() + "/" + AppConstants.API_NAME_CHANGE + "/" + sessionManager.getOrgIdFromSession() + "/" + strTopicName, FILENAME);

            FileInputStream fileInputStream = null;
            FileOutputStream output = null;
            try {
                fileInputStream = new FileInputStream(appSpecificExternalDir.getAbsolutePath());
                output = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final byte[] buffer = new byte[1024];
            int size;
            while ((size = fileInputStream.read(buffer)) != -1) {
                output.write(buffer, 0, size);
            }
            fileInputStream.close();
            output.close();
            parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            // This is the PdfRenderer we use to render the PDF.
//        if (parcelFileDescriptor != null) {
            pdfRenderer = new PdfRenderer(parcelFileDescriptor);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "openRenderer", e);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void closeRenderer() throws IOException {
        try {
            if (null != currentPage) {
                currentPage.close();
            }
            pdfRenderer.close();
            parcelFileDescriptor.close();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "closeRenderer", e);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showPage(int index) {
        try {
            if (pdfRenderer.getPageCount() <= index) {
                return;
            }
            // Make sure to close the current page before opening another one.
            if (null != currentPage) {
                currentPage.close();
            }
            // Use `openPage` to open a specific page in PDF.
            currentPage = pdfRenderer.openPage(index);
            // Important: the destination bitmap must be ARGB (not RGB).
            Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(),
                    Bitmap.Config.ARGB_8888);
            // Here, we render the page onto the Bitmap.
            // To render a portion of the page, use the second and third parameter. Pass nulls to get
            // the default result.
            // Pass either RENDER_MODE_FOR_DISPLAY or RENDER_MODE_FOR_PRINT for the last parameter.
            currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            // We are ready to show the Bitmap to user.
            imageViewPdf.setImageBitmap(bitmap);
            updateUi();
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "showPage", e);
        }

    }

    /**
     * Updates the state of 2 control buttons in response to the current page index.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateUi() {
        try {
            int index = currentPage.getIndex();
            int pageCount = pdfRenderer.getPageCount();
            prePageButton.setEnabled(0 != index);
            nextPageButton.setEnabled(index + 1 < pageCount);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "updateUi", e);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int getPageCount() {
        return pdfRenderer.getPageCount();
    }

    public void webViewURLData(String url) {
        try {
            if (isNetworkStatusAvialable(ELearningViewActivity.this)) {
                ll_webViewELearning.setVisibility(View.VISIBLE);
                playerView.setVisibility(View.GONE);
                ImproveHelper.showToastAlert(this, "Please wait while loading...");

                webViewELearning.setVisibility(View.VISIBLE);
                WebSettings webSettings = webViewELearning.getSettings();
                webViewELearning.getSettings().setPluginState(WebSettings.PluginState.ON);
                webViewELearning.setWebViewClient(new Callback());
                webViewELearning.getSettings().setJavaScriptEnabled(true);
//            String mainUrl = "http://docs.google.com/gview?embedded=true&url=" + url;
                webSettings.setJavaScriptEnabled(true);
                webViewELearning.loadUrl(url);

                Log.d(TAG, "webViewURLData: " + url);

            } else {
                ImproveHelper.showToastAlert(ELearningViewActivity.this, "Please check internet connection and refresh page!");
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "webViewURLData", e);
        }

    }


    public void videoViewPlay(String strELearning_URL) {
        //        Uri videoUri = getMedia(strELearning_URL);
//        el_videoView.setVideoURI(videoUri);
        ll_videoPlayer_main.setVisibility(View.VISIBLE);
        ll_videoPlayer.setVisibility(View.GONE);
        Uri path = Uri.fromFile(new File(strELearning_URL));
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(el_videoView);
        el_videoView.setVideoURI(path);
        el_videoView.start();
        el_videoView.setMediaController(mediaController);
        el_videoView.requestFocus();
        el_videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                ll_videoPlayer.setVisibility(View.VISIBLE);
            }
        });
        iv_videoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_videoPlayer.setVisibility(View.GONE);
                videoViewPlay(strELearning_URL);
            }
        });

    }

    public void audio(File file) {
        try {
            ll_elAudioFile.setVisibility(View.VISIBLE);
            mediaPlayer = new MediaPlayer();
//        mediaPlayer = MediaPlayer.create(this, R.raw.song);

            Uri uri = Uri.fromFile(file);
            mediaPlayer = MediaPlayer.create(ELearningViewActivity.this, uri);
//            mediaPlayer.start();
//        try {
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mediaPlayer.setDataSource(strELearning_URL);
//            mediaPlayer.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

            seekBar_audioPlayer.setClickable(false);
            ivPauseAudio.setEnabled(false);

            ivPlayAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Playing sound", Toast.LENGTH_SHORT).show();
                    mediaPlayer.start();

                    finalTime = mediaPlayer.getDuration();
                    startTime = mediaPlayer.getCurrentPosition();

                    if (oneTimeOnly == 0) {
                        seekBar_audioPlayer.setMax((int) finalTime);
                        oneTimeOnly = 1;
                    }

                    tx2.setText(String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                            finalTime)))
                    );

                    tx1.setText(String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                            startTime)))
                    );

                    seekBar_audioPlayer.setProgress((int) startTime);
                    myHandler.postDelayed(UpdateSongTime, 100);
                    ivPauseAudio.setEnabled(true);
                    ivPlayAudio.setEnabled(false);

                }
            });

            ivPauseAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Pausing sound", Toast.LENGTH_SHORT).show();
                    mediaPlayer.pause();
                    ivPauseAudio.setEnabled(false);
                    ivPlayAudio.setEnabled(true);
                }
            });

            ivForwardAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int temp = (int) startTime;

                    if ((temp + forwardTime) <= finalTime) {
                        startTime = startTime + forwardTime;
                        mediaPlayer.seekTo((int) startTime);
                        Toast.makeText(getApplicationContext(), "You have Jumped forward 5 seconds", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "You have Jumped backward 5 seconds", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Cannot jump backward 5 seconds", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "audio", e);
        }

    }

    private Uri getVideoUriMedia(String mediaName) {
        Uri uriV = null;
        try {
            if (URLUtil.isValidUrl(mediaName)) {
                // Media name is an external URL.
                uriV = Uri.parse(mediaName);
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "getVideoUriMedia", e);
        }
        return uriV;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                assessmentSessionAlertDialog();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        //finish();
        assessmentSessionAlertDialog();
    }

    public void assessmentSessionAlertDialogOld() {
        try {
            if (FromInTasksContent != null && !FromInTasksContent.equalsIgnoreCase("") && FromInTasksContent.equalsIgnoreCase("FromInTasksContent")) {
                finish();
            } else {

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

                new AlertDialog.Builder(ELearningViewActivity.this)
//set icon
//set title
                        .setIcon(getApplicationContext().getResources().getDrawable(R.drawable.icon_bhargo_user))
                        .setTitle(R.string.are_you_sure)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.P)
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked

                      /*  ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
                        ActivityManager.RunningTaskInfo task = tasks.get(0); // current task
                        ComponentName rootActivity = task.baseActivity;

                        String currentPackageName = rootActivity.getPackageName();
                        if (currentPackageName.equals("com.bhargo.bameti_training")) {
                            //Do whatever here

                        }*/

//                        if (strELearning_Type.equalsIgnoreCase("mp3") && mediaPlayer != null && mediaPlayer.isPlaying()) {
//
//                            mediaPlayer.stop();
//
//                        }
                                releasePlayer();

                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                                String setAssessmentSessionEnd = sdf.format(new Date());
                                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                                String strDate = sdfDate.format(new Date());
                                PrefManger.putSharedPreferencesString(ELearningViewActivity.this, "AssessmentSessionEnd", setAssessmentSessionEnd);
//                            ImproveHelper.showToastAlert(ELearningViewActivity.this, setAssessmentSessionEnd);
                                String strStartTime = PrefManger.getSharedPreferencesString(ELearningViewActivity.this, "AssessmentSessionStart", "");
                                /*Start and Stop Time*/
                                Log.d(TAG, "AssessmentSessionEnd: " + setAssessmentSessionEnd);

                                mInsertUserFileVisits(strDate, strStartTime, setAssessmentSessionEnd);

                            }
                        })
//set negative button
                        .setNegativeButton(R.string.d_no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked

                            }
                        })
                        .show();
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "assessmentSessionAlertDialog", e);
        }

    }
    public void assessmentSessionAlertDialog() {
        try {
            if (FromInTasksContent != null && !FromInTasksContent.equalsIgnoreCase("") && FromInTasksContent.equalsIgnoreCase("FromInTasksContent")) {
                finish();
            } else {
                try {
                    hideKeyboard(context, view);
                    ImproveHelper.alertDialogWithRoundShapeMaterialTheme(context, getString(R.string.are_you_sure),
                            getString(R.string.yes), getString(R.string.d_no), new ImproveHelper.IL() {
                                @Override
                                public void onSuccess() {
                                    releasePlayer();

                                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                                    String setAssessmentSessionEnd = sdf.format(new Date());
                                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                                    String strDate = sdfDate.format(new Date());
                                    PrefManger.putSharedPreferencesString(ELearningViewActivity.this, "AssessmentSessionEnd", setAssessmentSessionEnd);
//                            ImproveHelper.showToastAlert(ELearningViewActivity.this, setAssessmentSessionEnd);
                                    String strStartTime = PrefManger.getSharedPreferencesString(ELearningViewActivity.this, "AssessmentSessionStart", "");
                                    /*Start and Stop Time*/
                                    Log.d(TAG, "AssessmentSessionEnd: " + setAssessmentSessionEnd);

                                    mInsertUserFileVisits(strDate, strStartTime, setAssessmentSessionEnd);


                                }

                                @Override
                                public void onCancel() {

                                }
                            });

                } catch (Exception e) {
                    ImproveHelper.improveException(this, TAG, "navigateOnBackPressAlertDialog", e);
                }
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "assessmentSessionAlertDialog", e);
        }

    }

    private void mInsertUserFileVisits(String srtDate, String strStartTime, String strEndTime) {
        try {
            String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

            InsertUserFileVisitsModel userFileVisitsModel = new InsertUserFileVisitsModel();
            userFileVisitsModel.setUserID(sessionManager.getUserDataFromSession().getUserID());
            userFileVisitsModel.setDBNAME(sessionManager.getOrgIdFromSession());
            userFileVisitsModel.setParent_Id(parentID);
            userFileVisitsModel.setSelected_Node_Id(selected_Node_Id);
            userFileVisitsModel.setCategoryFileID(categoryFileID);
            userFileVisitsModel.setDistributionId(distributionId);
            userFileVisitsModel.setDeviceID(android_id);
            userFileVisitsModel.setMobileDate(srtDate);
            userFileVisitsModel.setStartTime(strStartTime);
            userFileVisitsModel.setEndTime(strEndTime);
            userFileVisitsModel.setGPS("");
            userFileVisitsModel.setIs_Visited_Through("1");


            if (!isNetworkStatusAvialable(ELearningViewActivity.this)) {
                List<InsertUserFileVisitsModel> insertUserFileVisitsModelsDB = new ArrayList<>();
                insertUserFileVisitsModelsDB.add(userFileVisitsModel);

                improveDataBase.insertIntoELearningFilesTimeSpentOffLine(insertUserFileVisitsModelsDB, "NotUploaded");

            } else {

                List<InsertUserFileVisitsModel> fileVisitsModels = new ArrayList<>();
                fileVisitsModels.add(userFileVisitsModel);
                FilesTimeSpentModel filesTimeSpentModel = new FilesTimeSpentModel();
                filesTimeSpentModel.setUserFileVisitsList(fileVisitsModels);
                Gson gson = new Gson();
            String str = gson.toJson(filesTimeSpentModel);
//                String str = gson.toJson(userFileVisitsModel);
                Log.d(TAG, "mInsertUserFileVisits: " + str);
                Call<InsertUserFileVisitsResponse> responseCall = getServices.getInsertUserFileVisits(filesTimeSpentModel);
                responseCall.enqueue(new retrofit2.Callback<InsertUserFileVisitsResponse>() {
                    @Override
                    public void onResponse(Call<InsertUserFileVisitsResponse> call, Response<InsertUserFileVisitsResponse> response) {
                        dismissProgressDialog();
                        improveHelper.dismissProgressDialog();
                        if (response.body() != null) {
                            if (response.body().getMessage() != null && !response.body().getMessage().isEmpty() && response.body().getMessage().equalsIgnoreCase("Success")) {
                                timeSpentOnFile(strFileName, strStartTime, strEndTime);
                                exit();
                            }else {
                                exit();
                            }
                        }else{
                            exit();
                        }
                    }

                    @Override
                    public void onFailure(Call<InsertUserFileVisitsResponse> call, Throwable t) {
                        Log.d(TAG, "onUserFileVisitsException: " + t.getMessage());
                        exit();
                    }
                });
            }
            /*if (strDirect != null && strDirect.equalsIgnoreCase("Direct")) {
                Intent intent = new Intent(ELearningViewActivity.this, BottomNavigationActivity.class);
                intent.putExtra("ELearning_NotificationBack", "ELearning_NormalBack");
                startActivity(intent);
            } else if (FromInTasksContent != null && FromInTasksContent.equalsIgnoreCase("FromInTasksContent")) {
                Intent intent = new Intent(ELearningViewActivity.this, BottomNavigationActivity.class);
                intent.putExtra("FromNormalTask", "FromNormalTask");
                startActivity(intent);
            }else if (strDirect != null && strDirect.equalsIgnoreCase("fromCallAction")) {
                dismissProgressDialog();
                improveHelper.dismissProgressDialog();
            }
            finish();*/
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "mInsertUserFileVisits", e);
        }

    }

    private void exit(){
        if (strDirect != null && strDirect.equalsIgnoreCase("Direct")) {
            Intent intent = new Intent(ELearningViewActivity.this, BottomNavigationActivity.class);
            intent.putExtra("ELearning_NotificationBack", "ELearning_NormalBack");
            startActivity(intent);
        } else if (FromInTasksContent != null && FromInTasksContent.equalsIgnoreCase("FromInTasksContent")) {
            Intent intent = new Intent(ELearningViewActivity.this, BottomNavigationActivity.class);
            intent.putExtra("FromNormalTask", "FromNormalTask");
            startActivity(intent);
        }else if (strDirect != null && strDirect.equalsIgnoreCase("fromCallAction")) {
            dismissProgressDialog();
            improveHelper.dismissProgressDialog();
        }
        finish();
    }

    public void CheckViews(String url) {
        Uri uri = getVideoUriMedia(url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "*/*");
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult_EVA: " + requestCode + " - " + resultCode + " - " + data);
    }

    private void initializePlayer(String strFilePath) {
        try {
            playerView.setVisibility(View.VISIBLE);
            webViewELearning.setVisibility(View.GONE);
            player = new ExoPlayer.Builder(this)
                    .setSeekBackIncrementMs(5000)
                    .setSeekForwardIncrementMs(5000)
                    .build();
           // player = ExoPlayerFactory.newSimpleInstance(this);
            playerView.setPlayer(player);

            Uri uri = Uri.parse(strFilePath);
            MediaSource mediaSource = buildMediaSource(uri);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
            player.prepare(mediaSource, false, false);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "initializePlayer", e);
        }

    }

    private void releasePlayer() {
        try {
            if (player != null) {
                playWhenReady = player.getPlayWhenReady();
                playbackPosition = player.getCurrentPosition();
                currentWindow = player.getCurrentWindowIndex();
//            if(player.isPlaying()) {
                player.release();
                player = null;
//            }
            }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "releasePlayer", e);
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        try {
            playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "hideSystemUi", e);
        }

    }

    private MediaSource buildMediaSource(Uri uri) {
        MediaItem media = MediaItem.fromUri(uri);
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(media);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            hideSystemUi();
        }
    }

    public void timeSpentOnFile(String fileName, String startTime, String StopTime) {
        try {
            String startDate = startTime;
            String stopDate = StopTime;

// Custom date format
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

            Date d1 = null;
            Date d2 = null;
            try {
                d1 = format.parse(startDate);
                d2 = format.parse(stopDate);
            } catch (Exception e) {
                improveHelper.improveException(this, TAG, "timeSpentOnFile-format", e);
            }

// Get msec from each, and subtract.
            long diff = d2.getTime() - d1.getTime();
            long diffSeconds = diff / 1000;
            long diffMinutes = diff / (60 * 1000);
            long diffHours = diff / (60 * 60 * 1000);

            ImproveHelper.showToastAlert(ELearningViewActivity.this, "You spent \n\n" + String.format("%02d:%02d:%02d", diffHours, diffMinutes, diffSeconds) + "\n\n" + " on \n\n" + fileName);

//        System.out.println("Time in seconds: " + diffSeconds + " seconds.");
//        System.out.println("Time in minutes: " + diffMinutes + " minutes.");
//        System.out.println("Time in hours: " + diffHours + " hours.");
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "timeSpentOnFile", e);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStartELV: onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void openAllDocs(String strFilepath) {
        Uri pptUri = Uri.parse(strFilepath);
        /*Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setText("open ppt")
                .setType("application/vnd.ms-powerpoint")
                .setStream(pptUri)
                .getIntent()
                .setPackage("com.microsoft.office.powerpoint");
        startActivity(shareIntent);*/
        File file = new File("path_to_the_file.ppt");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(pptUri, "application/vnd.ms-powerpoint");
        intent.setFlags(FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    private void openPPT(Context context, final String path) {
        File file = new File(path);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
        } else {
            uri = Uri.fromFile(file);
        }

        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .setDataAndType(uri, "application/vnd.ms-powerpoint")
                .setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                .setPackage("com.microsoft.office.powerpoint");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Application not found", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isFileExistsInPackage(String sdcardUrl, String filename) {
        File appSpecificExternalDir = null;
        try {
            File cDir = getApplicationContext().getExternalFilesDir(sdcardUrl);
            appSpecificExternalDir = new File(cDir.getAbsolutePath(), filename);
            Log.d(TAG, "FileExitsELV: " + appSpecificExternalDir);
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "isFileExistsInPackage", e);
        }

        return appSpecificExternalDir.exists();

    }

    @Override
    protected void onPause() {
        super.onPause();
        releasePlayer();
    }

    public class AppWebViewClients extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            Log.d(TAG, "webViewURLDataDoc: " + url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

        }
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}