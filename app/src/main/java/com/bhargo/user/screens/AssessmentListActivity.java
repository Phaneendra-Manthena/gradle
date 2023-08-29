package com.bhargo.user.screens;

import static com.bhargo.user.utils.AppConstants.ASSESSMENT_ANSWER_LIST;
import static com.bhargo.user.utils.ImproveHelper.setBhargoTheme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bhargo.user.R;
import com.bhargo.user.adapters.InfoAdapter;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomRadioButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.GetServices;
import com.bhargo.user.pojos.AnswersModel;
import com.bhargo.user.pojos.AssessmentAnswer;
import com.bhargo.user.pojos.AssessmentAnswersData;
import com.bhargo.user.pojos.FormDataResponse;
import com.bhargo.user.pojos.MatchTheFollowingModel;
import com.bhargo.user.pojos.QuestionState;
import com.bhargo.user.pojos.QuestionsModel;
import com.bhargo.user.pojos.ResultsModel;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.BaseActivity;
import com.bhargo.user.utils.ImproveDataBase;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.RetrofitUtils;
import com.bhargo.user.utils.SessionManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssessmentListActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "AssessmentListActivity";
    public static String VISITED = "VISITED";
    public static String NOT_VISITED = "NOT_VISITED";
    public static String ANSWERED = "ANSWERED";
    public static String NOT_ANSWERED = "NOT_ANSWERED";
    Context context;
    LinearLayout linearLayoutMain, ll_questionAnswerContainer;
    long MILLI_SEC, millisUntil;
    String distributionId = null, strExamDuration = "", strAssessmentId = "";
    int noOfAttempts = 0;
    SessionManager sessionManager;
    ImproveHelper improveHelper;
    CustomTextView tv_qno;
    int currentQuestionIndex = 0;
    List<AnswersModel> answersModels;
    Gson gson = new Gson();
    HashMap<String, String> answersMap = new HashMap<>();
    HashMap<String, AssessmentAnswer> answersMapCheck = new HashMap<>();
    List<String> stringListQuestionsCheck = new ArrayList<>();
    List<String> stringListQuestionsParagraphIds = new ArrayList<>();
    int qpIds = 0;
    ImproveDataBase improveDataBase;
    //    int questionSerialNo;
    private LinearLayout linearLayout;
    private List<QuestionsModel> questionsMainList;
    private List<MatchTheFollowingModel> matchTheFollowing;
    private CountDownTimer yourCountDownTimer;
    private GetServices getServices;
    private Button button_previous, button_next;
    private LinearLayout ll_mtf_partAContainer, ll_mtf_partBContainer;
    private int noOfUserAttemptsCount;
    LinearLayout bottom_sheet;
    BottomSheetBehavior sheetBehavior;

    public static void serializeObject(Gson gson, List<QuestionsModel> questionsMainList) {

        FileOutputStream fout = null;
        try {

            //Creating stream and writing the object
//            Log.d("SerializeObjectPath", Environment.getExternalStorageDirectory() + "/ImproveFiles/" + "ControlObject.txt");
            fout = new FileOutputStream(Environment.getExternalStorageDirectory() + "/ImproveFiles/" + "Test.txt");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(gson.toJson(questionsMainList));
            out.flush();
            //closing the stream
            out.close();
            System.out.println("ControlObject text success");
//            Toast.makeText(get, "No image captured", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int countFreq(String pat, String txt) {
        int M = pat.length();
        int N = txt.length();
        int res = 0;
        try {
            /* A loop to slide pat[] one by one */
            for (int i = 0; i <= N - M; i++) {
            /* For current index i, check for
        pattern match */
                int j;
                for (j = 0; j < M; j++) {
                    if (txt.charAt(i + j) != pat.charAt(j)) {
                        break;
                    }
                }

                // if pat[0...M-1] = txt[i, i+1, ...i+M-1]
                if (j == M) {
                    res++;
                    j = 0;
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "countFreq: " + e);
        }

        return res;


    }

    public static String removeTrailingSpaces(String param) {

        if (param == null)
            return null;
        int len = param.length();
        for (; len > 0; len--) {
            if (!Character.isWhitespace(param.charAt(len - 1)))
                break;
        }

        return param.substring(0, len);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Theme
        setBhargoTheme(this, AppConstants.THEME, AppConstants.IS_FORM_THEME, "");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        initializeActionBar();
        enableBackNavigation(true);
        context = AssessmentListActivity.this;
        sessionManager = new SessionManager(context);
        improveDataBase = new ImproveDataBase(context);
        improveHelper = new ImproveHelper(context);
        answersModels = new ArrayList<>();

        bottom_sheet = findViewById(R.id.bottom_sheet_palette);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            distributionId = getIntent().getStringExtra("DistributionId");
            strExamDuration = getIntent().getStringExtra("ExamDuration");
            strAssessmentId = getIntent().getStringExtra("AssessmentId");
            noOfAttempts = getIntent().getIntExtra("NoOfAttempts", -1);
            if (strExamDuration.equalsIgnoreCase("") || strExamDuration.equalsIgnoreCase("0")) {
//                tv_countDown.setVisibility(View.GONE);
            } else {
                MILLI_SEC = Integer.parseInt(strExamDuration) * 60000;
                Log.d(TAG, "strExamDuration: " + strExamDuration + "-" + MILLI_SEC);
                yourCountDownTimer = new CountDownTimer(MILLI_SEC, 1000) { // adjust the milli seconds here

                    public void onTick(long millisUntilFinished) {
                        millisUntil = millisUntilFinished;
                        tv_countDown.setText("" + String.format("%02d:%02d",
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    }

                    public void onFinish() {

                        tv_countDown.setText("Done!");
                        String strTimeSpent = onAssessmentTimeSpent(MILLI_SEC, millisUntil);
                        Log.d(TAG, "onFinishTimeSpent: " + strTimeSpent);

                        improveHelper.showProgressDialog("Please wait while submitting. . .");
                        prepareJsonToSubmit(strTimeSpent);


                    }
                }.start();

            }
            questionsMainList = (List<QuestionsModel>) getIntent().getSerializableExtra("AssessmentQuestionsMain");
            Gson gson = new Gson();
            Log.d(TAG, "onCreateAssessmentList: " + gson.toJson(questionsMainList));
            if (questionsMainList != null && questionsMainList.size() > 0) {
                for (QuestionsModel questionsModel : questionsMainList) {
                    if (!stringListQuestionsParagraphIds.contains(questionsModel.getQuestionParagraphId())) {
                        if (questionsModel.getQuestionParagraphId() != null && !questionsModel.getQuestionParagraphId().equalsIgnoreCase("")) {
                            stringListQuestionsParagraphIds.add(questionsModel.getQuestionParagraphId());
                        }

                        qpIds++;

                    }
                }


                initViews();
            } else {
                ImproveHelper.showToast(context, "No questions available");
                finish();
            }
        }

    }

    private void showCurrentHideOthers(int position) {
        try {
            Log.d(TAG, "showCurrentHideOthers: " + qpIds);
            int cur_qno = position + 1;
            String qNo = cur_qno + "/" + qpIds;
            tv_qno.setText(qNo);


            for (int i = 0; i < linearLayoutMain.getChildCount(); i++) {

                if (i == position) {

                    View view = linearLayoutMain.getChildAt(i);

                    CustomTextView customTextView = view.findViewById(R.id.tv_question);

                    if (!customTextView.getTag().toString().equalsIgnoreCase("2")) {
                        customTextView.setTag("1");
                    }

                    linearLayoutMain.getChildAt(i).setVisibility(View.VISIBLE);
                } else {
                    linearLayoutMain.getChildAt(i).setVisibility(View.GONE);
                }
            }

            if (position == 0) {
                button_previous.setVisibility(View.GONE);
            } else {
                button_previous.setVisibility(View.VISIBLE);
            }
            if (position + 1 == qpIds) {
                button_next.setVisibility(View.GONE);
            } else {
                button_next.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "showCurrentHideOthers", e);
        }
    }

    public void LoadImageQuestion(LinearLayout linearLayoutChanges, QuestionsModel mQuestion, int questionNo, int questionCategory) {
        try {
            if (!stringListQuestionsCheck.contains(mQuestion.getQuestionParagraphId())) {
                stringListQuestionsCheck.add(mQuestion.getQuestionParagraphId());
                final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rView = linflater.inflate(R.layout.item_image_question, null);


                CustomTextView tv_question = rView.findViewById(R.id.tv_question);
                tv_question.setTag("0");
                ImageView iv_questionParagraph = rView.findViewById(R.id.iv_questionParagraph);

//            Glide.with(context).load(mQuestion.getQuestionParagraph()).into(iv_questionParagraph);
                Glide.with(context)
                        .asBitmap()
                        .load(mQuestion.getQuestionParagraph())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap bitmap,
                                                        Transition<? super Bitmap> transition) {
                                int w = bitmap.getWidth();
                                int h = bitmap.getHeight();
                                iv_questionParagraph.setImageBitmap(bitmap);
                                iv_questionParagraph.setMinimumHeight(h + 50);
                                iv_questionParagraph.setMinimumWidth(w + 50);
                            }
                        });
                ll_questionAnswerContainer = rView.findViewById(R.id.ll_questionAnswerImage);
                LinearLayout ll_onlyAnswers = rView.findViewById(R.id.ll_onlyAnswers);

                Log.d(TAG, "LoadImageQuestionCheck: " + mQuestion.getQuestion());

                String questionParagraphId = mQuestion.getQuestionParagraphId();
                int questionNew = 1;
                if (questionsMainList != null && questionsMainList.size() > 0) {

                    for (QuestionsModel questionsModel : questionsMainList) {
                        if (!questionsModel.getQuestionParagraphId().isEmpty()
                                && questionsModel.getQuestionParagraphId().equalsIgnoreCase(questionParagraphId)) {
                            if (questionsModel.getQuestionTypeId().equalsIgnoreCase("3")) {
                                LoadMatchTheFollowing(ll_questionAnswerContainer, questionsModel, questionNew++, 1);
                            } else if (questionsModel.getQuestionTypeId().equalsIgnoreCase("2")) {
                                LoadRadioGroup(ll_questionAnswerContainer, questionsModel, questionNew++, 1);
                            } else if (questionsModel.getQuestionTypeId().equalsIgnoreCase("1")) {
                                LoadEditText(ll_questionAnswerContainer, questionsModel, questionNew++, 1, tv_question);
                            } else if (questionsModel.getQuestionTypeId().equalsIgnoreCase("4")) {
                                LoadParagraph(ll_questionAnswerContainer, questionsModel, questionNew++, 1);
                            } else {
                                withInParagraphStrip(ll_questionAnswerContainer, questionsModel, questionNew++, 1);
                            }
                        }
                    }
                }

                linearLayoutChanges.addView(rView);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "LoadImageQuestion", e);
        }
    }

    public void LoadParagraph(LinearLayout linearLayoutChanges, QuestionsModel mQuestion, int questionNo, int questionCategory) {
        try {
            if (!stringListQuestionsCheck.contains(mQuestion.getQuestionParagraphId())) {
                stringListQuestionsCheck.add(mQuestion.getQuestionParagraphId());
                final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rView = linflater.inflate(R.layout.item_paragraph, null);
                CustomTextView tv_question = rView.findViewById(R.id.tv_question);
                ImageView iv_questionParagraph = rView.findViewById(R.id.iv_questionParagraph);
                tv_question.setTag("0");

                LinearLayout ll_questionParagraphContainer = rView.findViewById(R.id.ll_questionParagraphContainer);
                ImageView iv_question = rView.findViewById(R.id.iv_question);
                LinearLayout ll_onlyAnswers = rView.findViewById(R.id.ll_onlyAnswers);
//            tv_question.setText(mQuestion.getQuestionParagraph());
                spiltQuestion(ll_questionParagraphContainer, mQuestion.getQuestionParagraph(), iv_questionParagraph, tv_question);

                LinearLayout ll_questionAnswerContainer = rView.findViewById(R.id.ll_questionAnswerContainer);


                String questionParagraphId = mQuestion.getQuestionParagraphId();

                int questionNew = 1;
                if (questionsMainList != null && questionsMainList.size() > 0) {

                    for (QuestionsModel questionsModel : questionsMainList) {
                        if (questionsModel.getQuestionParagraphId() != null && !questionsModel.getQuestionParagraphId().isEmpty() &&
                                questionsModel.getQuestionParagraphId().equalsIgnoreCase(questionParagraphId)) {
                            if (questionsModel.getQuestionTypeId().equalsIgnoreCase("1")) {
                                LoadEditText(ll_questionAnswerContainer, questionsModel, questionNew++, 1, tv_question);
                            } else if (questionsModel.getQuestionTypeId().equalsIgnoreCase("2")) {
                                LoadRadioGroup(ll_questionAnswerContainer, questionsModel, questionNew++, 1);
                            } else if (questionsModel.getQuestionTypeId().equalsIgnoreCase("3")) {
                                LoadMatchTheFollowing(ll_questionAnswerContainer, questionsModel, questionNew++, 1);
                            } else if (questionsModel.getQuestionTypeId().equalsIgnoreCase("5")) {
                                LoadImageQuestion(ll_questionAnswerContainer, questionsModel, questionNew++, 1);
                            } else {
                                withInParagraphStrip(ll_questionAnswerContainer, questionsModel, questionNew++, 1);
                            }
                        }
                    }
                }

                linearLayoutChanges.addView(rView);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "LoadParagraph", e);
        }

    }

    private void withInParagraphStrip(LinearLayout ll_questionAnswerContainer, QuestionsModel mQuestion, int questionNo, int questionCategory) {
        try {
            if (!stringListQuestionsCheck.contains(mQuestion.getQuestionParagraphId())) {
                stringListQuestionsCheck.add(mQuestion.getQuestionParagraphId());
                final LayoutInflater linflaterQA = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v_QA = linflaterQA.inflate(R.layout.item_questions_answers, null);

                ImageView iv_question = v_QA.findViewById(R.id.iv_question);
                CustomTextView tv_question = v_QA.findViewById(R.id.tv_question);
                LinearLayout ll_onlyAnswers = v_QA.findViewById(R.id.ll_onlyAnswers);
//        RadioGroup rg_main = v_QA.findViewById(R.id.rg_container);
                String question = null;
                if (questionCategory == 0) {
                    question = mQuestion.getQuestion();
                } else {
                    question = questionNo + ". " + mQuestion.getQuestion();
                }
                LinearLayout ll_questionParagraphContainer = v_QA.findViewById(R.id.ll_questionParagraphContainer);

                spiltQuestion(ll_questionParagraphContainer, question, iv_question, tv_question);

                answersModels = Arrays.asList(gson.fromJson(mQuestion.getAnswers(), AnswersModel[].class));
                for (int count = 0; count < answersModels.size(); count++) {

                    answersStrip(ll_onlyAnswers, answersModels.get(count).getAnswer(),
                            answersModels.get(count).getAnswerID() + "@" + mQuestion.getMarks(), count, mQuestion);

                }

                ll_questionAnswerContainer.addView(v_QA);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "withInParagraphStrip", e);
        }

    }
//    }

    private void answersStrip(LinearLayout linearLayoutAnswers, String answer, String answerId, int id, QuestionsModel questionsModel) {
        try {
            final LayoutInflater linflaterQA = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v_QA = linflaterQA.inflate(R.layout.item_radiobutton_answers, null);
            v_QA.setTag(id);

            CustomRadioButton radioButton = v_QA.findViewById(R.id.radioButton);
            ImageView iv_answer = v_QA.findViewById(R.id.iv_answer);
            CustomTextView tv_answer = v_QA.findViewById(R.id.tv_answer);
            radioButton.setId(id);
            radioButton.setTag(answerId);
            LinearLayout ll_questionParagraphContainer = v_QA.findViewById(R.id.ll_questionParagraphContainer);
            spiltQuestion(ll_questionParagraphContainer, answer, iv_answer, tv_answer);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View parentView = linearLayoutMain.getChildAt(currentQuestionIndex);
//                parentView.setTag(ANSWERED);
                    CustomTextView customTextView = parentView.findViewById(R.id.tv_question);
                    customTextView.setTag("2");
                    int index = (int) v_QA.getTag();
                    for (int i = 0; i < linearLayoutAnswers.getChildCount(); i++) {
                        if (i != index) {
                            View view = linearLayoutAnswers.getChildAt(i);
                            CustomRadioButton customRadioButton = (CustomRadioButton) ((LinearLayout) ((LinearLayout) view).getChildAt(0)).getChildAt(0);
                            customRadioButton.setChecked(false);
                        }
                        if (answerId != null && !answerId.isEmpty()) {

                            //Peparing Json

                            AssessmentAnswer assessmentAnswer = new AssessmentAnswer();

                            String[] marksAndAnswerID = radioButton.getTag().toString().split("@");

                            assessmentAnswer.setAnswer(answer);
                            assessmentAnswer.setAnswerId(marksAndAnswerID[0]);
                            assessmentAnswer.setMarks(marksAndAnswerID[1]);
                            assessmentAnswer.setQuestionID(questionsModel.getQuestionID());
                            answersMapCheck.put(questionsModel.getQuestionID(), assessmentAnswer);
                            Log.d(TAG, "onClickAnswerIdCheck: " + questionsModel.getQuestionID());
                            Log.d(TAG, "onClickAnswerIdCheckSize: " + answersMapCheck.size());
                        }
                    }
                }
            });

            linearLayoutAnswers.addView(v_QA);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "answersStrip", e);
        }

    }
//    }

    public void LoadRadioGroup(LinearLayout linearLayoutChanges, QuestionsModel mQuestion, int questionNo, int questionCategory) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rView = linflater.inflate(R.layout.item_radiogroup_for_assesment, null);

            CustomTextView tv_question = rView.findViewById(R.id.tv_question);
            ImageView iv_question = rView.findViewById(R.id.iv_question);
            LinearLayout ll_onlyAnswers = rView.findViewById(R.id.ll_onlyAnswers);
            tv_question.setTag("0");

            String question = null;
            if (questionCategory == 0) {
                question = mQuestion.getQuestion();
            } else {
                question = questionNo + ". " + mQuestion.getQuestion();
            }

            LinearLayout ll_questionParagraphContainer = rView.findViewById(R.id.ll_questionParagraphContainer);

            spiltQuestion(ll_questionParagraphContainer, question, iv_question, tv_question);

            answersModels = Arrays.asList(gson.fromJson(mQuestion.getAnswers(), AnswersModel[].class));
            for (int count = 0; count < answersModels.size(); count++) {

                answersStrip(ll_onlyAnswers, answersModels.get(count).getAnswer(),
                        answersModels.get(count).getAnswerID() + "@" + mQuestion.getMarks(), count, mQuestion);
            }

            linearLayoutChanges.addView(rView);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "LoadRadioGroup", e);
        }

//        linearLayoutMain.addView(rView);
    }

    public void LoadMatchTheFollowing(LinearLayout ll_questionAnswerContainer, QuestionsModel mQuestion, int questionNo, int questionCategory) {
        try {
            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rView = linflater.inflate(R.layout.item_match_the_following, null);

            CustomTextView tv_question = rView.findViewById(R.id.tv_question);
            tv_question.setTag("0");
            ImageView iv_question = rView.findViewById(R.id.iv_question);

            String question = null;
            if (questionCategory == 0) {
                question = mQuestion.getQuestion();
            } else {
                question = questionNo + ". " + mQuestion.getQuestion();
            }
            LinearLayout ll_questionParagraphContainer = rView.findViewById(R.id.ll_questionParagraphContainer);
            spiltQuestion(ll_questionParagraphContainer, question, iv_question, tv_question);

            ll_mtf_partAContainer = rView.findViewById(R.id.ll_mtf_partAContainer);
            ll_mtf_partBContainer = rView.findViewById(R.id.ll_mtf_partBContainer);
            LinearLayout ll_onlyAnswers = rView.findViewById(R.id.ll_onlyAnswers);


            matchTheFollowing = Arrays.asList(gson.fromJson(mQuestion.getMatchTheFollowing(), MatchTheFollowingModel[].class));

            for (int i = 0; i < matchTheFollowing.size(); i++) {

                withInPartStrip(ll_mtf_partAContainer, i, matchTheFollowing.get(i).getPartA(), "PartA");

            }

            for (int i = 0; i < matchTheFollowing.size(); i++) {

                withInPartStrip(ll_mtf_partBContainer, i + 1, matchTheFollowing.get(i).getPartB(), "PartB");

            }

            answersModels = Arrays.asList(gson.fromJson(mQuestion.getAnswers(), AnswersModel[].class));
            for (int count = 0; count < answersModels.size(); count++) {

                answersStrip(ll_onlyAnswers, answersModels.get(count).getAnswer(),
                        answersModels.get(count).getAnswerID() + "@" + mQuestion.getMarks(), count, mQuestion);
            }

            ll_questionAnswerContainer.addView(rView);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "LoadMatchTheFollowing", e);
        }

    }


    private void withInPartStrip(LinearLayout ll_mtf_partContainer, int position, String strMatchText, String partFrom) {
        try {
            final LayoutInflater linflaterMTF = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v_mtf_part = linflaterMTF.inflate(R.layout.item_custom_text_view, null);

            CustomTextView tv_question_serial, tv_question_text;

            tv_question_serial = v_mtf_part.findViewById(R.id.tv_question_serial);
            tv_question_text = v_mtf_part.findViewById(R.id.tv_question_text);
            if (partFrom.equalsIgnoreCase("PartA")) {

                tv_question_serial.setText(getAlphabetsByPosition(position) + ". ");
            } else {
                tv_question_serial.setText(position + ". ");
            }

//        tv_question_text.setText(strMatchText);
            LinearLayout ll_questionParagraphContainer = v_mtf_part.findViewById(R.id.ll_questionParagraphContainer);
            spiltQuestion(ll_questionParagraphContainer, strMatchText, null, tv_question_text);
            ll_mtf_partContainer.addView(v_mtf_part);

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "withInPartStrip", e);
        }

    }

    public void LoadEditText(LinearLayout linearLayoutChanges, QuestionsModel mQuestion, int questionNo, int questionCategory, CustomTextView tvImageTextView) {
        try {
            linearLayout = new LinearLayout(context);

            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rView = linflater.inflate(R.layout.item_textview_for_assesment, null);

            ImageView iv_question = rView.findViewById(R.id.iv_question);

            CustomTextView tv_question = rView.findViewById(R.id.tv_question);
            tv_question.setTag("0");


            String question = null;
            if (questionCategory == 0) {
                question = mQuestion.getQuestion();
            } else {
                question = questionNo + ". " + mQuestion.getQuestion();
            }
            LinearLayout ll_questionParagraphContainer = rView.findViewById(R.id.ll_questionParagraphContainer);

            spiltQuestion(ll_questionParagraphContainer, question, iv_question, tv_question);


            CustomEditText editText_answer = rView.findViewById(R.id.editText_answer);

            answersModels = Arrays.asList(gson.fromJson(mQuestion.getAnswers(), AnswersModel[].class));

            editText_answer.setTag(answersModels.get(0).getAnswerID() + "@" + mQuestion.getMarks());

            linearLayout.addView(rView);

            editText_answer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (questionCategory == 0) {
                        if (!s.toString().isEmpty()) {
                            tv_question.setTag("2");
                        } else {
                            tv_question.setTag("1");
                        }
                    } else {
                        if (!s.toString().isEmpty()) {
                            tvImageTextView.setTag("2");
                        } else {
                            tvImageTextView.setTag("1");
                        }
                    }
                    AssessmentAnswer assessmentAnswer = new AssessmentAnswer();
//                                String[] marksAndAnswerID = etAnswer.getTag().toString().split("@");
                    String[] marksAndAnswerID = editText_answer.getTag().toString().split("@");

                    assessmentAnswer.setAnswer(editText_answer.getText().toString());
                    assessmentAnswer.setAnswerId(marksAndAnswerID[0]);
                    assessmentAnswer.setMarks(marksAndAnswerID[1]);
                    assessmentAnswer.setQuestionID(mQuestion.getQuestionID());
                    answersMapCheck.put(mQuestion.getQuestionID(), assessmentAnswer);

                }
            });

            linearLayoutChanges.addView(linearLayout);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "LoadEditText", e);
        }

    }

    private void setRadioGroupItems(RadioGroup rg_main, String answer, String answerId, int id) {
        try {
            final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = lInflater.inflate(R.layout.item_radiobutton, null);
//        ImageView iv_answer = view.findViewById(R.id.iv_answer);
            CustomRadioButton radioButton = view.findViewById(R.id.radioButton);
            radioButton.setId(id);
            radioButton.setTag(answerId);
            rg_main.addView(radioButton);
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "setRadioGroupItems", e);
        }

    }

    private void initViews() {
        try {
            initializeActionBar();
            title.setVisibility(View.GONE);
            enableBackNavigation(true);

            iv_circle_appIcon.setVisibility(View.GONE);
            ib_settings.setVisibility(View.GONE);
            ll_countDownTimer.setVisibility(View.VISIBLE);

            iv_info.setVisibility(View.VISIBLE);
            tv_countDown.setVisibility(View.VISIBLE);
            tv_AssessmentSubmit.setVisibility(View.VISIBLE);

            sessionManager = new SessionManager(context);
            getServices = RetrofitUtils.getUserService();
            ASSESSMENT_ANSWER_LIST = new HashMap<>();

            button_previous = findViewById(R.id.button_previous);
            button_next = findViewById(R.id.button_next);

            iv_info.setOnClickListener(this);
            button_previous.setOnClickListener(this);
            button_next.setOnClickListener(this);
            tv_AssessmentSubmit.setOnClickListener(this);

            linearLayoutMain = findViewById(R.id.ll_main);
            tv_qno = findViewById(R.id.qno);

            if (strExamDuration.equalsIgnoreCase("") || strExamDuration.equalsIgnoreCase("0")) {
                ib_timer.setVisibility(View.GONE);
                tv_countDown.setVisibility(View.GONE);
            }


            for (int i = 0; i < questionsMainList.size(); i++) {

                separateQuestionTypes(questionsMainList.get(i), questionsMainList.get(i).getParagraphImageId());
            }

            showCurrentHideOthers(currentQuestionIndex);

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initViews", e);
        }
    }

    private void separateQuestionTypes(QuestionsModel mQuestion, String paragraphImageId) {
        try {
            if (paragraphImageId.equalsIgnoreCase("1")) { // Fill in the blank
                LoadEditText(linearLayoutMain, mQuestion, 0, 0, null);
            } else if (paragraphImageId.equalsIgnoreCase("2")) { // choose the correct Radio button
                LoadRadioGroup(linearLayoutMain, mQuestion, 0, 0);
            } else if (paragraphImageId.equalsIgnoreCase("3")) { // match the following
                LoadMatchTheFollowing(linearLayoutMain, mQuestion, 0, 0);
            } else if (paragraphImageId.equalsIgnoreCase("4")) { // Paragraph
                LoadParagraph(linearLayoutMain, mQuestion, 0, 0);
            } else if (paragraphImageId.equalsIgnoreCase("5")) { // Image
                LoadImageQuestion(linearLayoutMain, mQuestion, 0, 0);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "separateQuestionTypes", e);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_AssessmentSubmit:

                String strTimeSpent = onAssessmentTimeSpent(MILLI_SEC, millisUntil);
                if (yourCountDownTimer != null) {
                    yourCountDownTimer.cancel();
                }
                prepareJsonToSubmit(strTimeSpent);
                break;
            case R.id.button_previous:
                showCurrentHideOthers(--currentQuestionIndex);
                break;
            case R.id.button_next:
                showCurrentHideOthers(++currentQuestionIndex);
                break;
            case R.id.iv_info:
                getInfoData();
                break;
        }
    }

    private void getInfoData() {
        try {
            List<QuestionState> infoList = new ArrayList<>();
            for (int i = 0; i < linearLayoutMain.getChildCount(); i++) {

                View view = linearLayoutMain.getChildAt(i);
                CustomTextView customTextView = view.findViewById(R.id.tv_question);
                Log.d(TAG, "getInfoDataTextCheck: " + customTextView.getText().toString() + "-" + i);
                QuestionState questionState = new QuestionState();
                int qNo = i + 1;
                questionState.setQuestionNumber(String.valueOf(qNo));
                questionState.setQuestionState(customTextView.getTag().toString());
                infoList.add(questionState);
                if (infoList.size() == linearLayoutMain.getChildCount()) {
                    ImproveHelper.showHideBottomSheet(sheetBehavior);
                    openInfoDialog(infoList);
                }

            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getInfoData", e);
        }

    }


    private void prepareJsonToSubmit(String strTimeSpent) {
        try {
            improveHelper.showProgressDialog("Please wait while submitting. . . ");
//        try {
            List<AssessmentAnswer> assessmentAnswersList = new ArrayList<>();
            for (int i = 0; i < questionsMainList.size(); i++) {

                String strQuestionId = questionsMainList.get(i).getQuestionID();
                if (answersMapCheck.containsKey(strQuestionId)) {
                    assessmentAnswersList.add(answersMapCheck.get(strQuestionId));
                } else {

                    AssessmentAnswer assessmentAnswer = new AssessmentAnswer();
                    assessmentAnswer.setAnswer("");
                    assessmentAnswer.setAnswerId("");
                    assessmentAnswer.setMarks(questionsMainList.get(i).getMarks());
                    assessmentAnswer.setQuestionID(questionsMainList.get(i).getQuestionID());

                    assessmentAnswersList.add(assessmentAnswer);
                }

            }

            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
            String strDate = sdfDate.format(new Date());

            AssessmentAnswersData assessmentAnswersData = new AssessmentAnswersData();
            assessmentAnswersData.setDBNAME(sessionManager.getOrgIdFromSession());
            assessmentAnswersData.setDistributionId(distributionId);
            assessmentAnswersData.setPostID(sessionManager.getPostsFromSession());
            assessmentAnswersData.setUserID(sessionManager.getUserDataFromSession().getUserID());
            assessmentAnswersData.setTiemSpent(strTimeSpent);
            assessmentAnswersData.setDeviceID(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
            assessmentAnswersData.setMobileDate(strDate);
            assessmentAnswersData.setAssessmentId(strAssessmentId);
            assessmentAnswersData.setAnswers(assessmentAnswersList);

//        for (AssessmentAnswer answersModel : assessmentAnswersData.getAnswers()) {
//            Log.d(TAG, "prepareJsonToSubmit_: " + answersModel.getAnswer());
//            Log.d(TAG, "prepareJsonToSubmit__: " + answersModel.getAnswerId());
//        }
            Gson gson = new Gson();
            Log.d(TAG, "prepareJsonToGson: " + gson.toJson(assessmentAnswersData));
            if (ImproveHelper.isNetworkStatusAvialable(context)) {
                sendDataToServer(assessmentAnswersData);
            } else {
                improveHelper.dismissProgressDialog();
                Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "prepareJsonToSubmit", e);
        }

    }

    private void sendDataToServer(AssessmentAnswersData mainObject) {
        try {
            Call<FormDataResponse> call = getServices.sendAssessmentAnswersData(mainObject);
            call.enqueue(new Callback<FormDataResponse>() {
                @Override
                public void onResponse(Call<FormDataResponse> call, Response<FormDataResponse> response) {
                    improveHelper.dismissProgressDialog();

                    if (response.body() != null) {
                        FormDataResponse formDataResponse = response.body();
                        if (formDataResponse.getStatus().equalsIgnoreCase("200")
                                && formDataResponse.getMessage().equalsIgnoreCase("Success")) {
//                        Log.d(TAG, "onResponseAssessment: " + formDataResponse.getMessage());
                            Log.d(TAG, "onResponseAssessment: " + "Success");
                            if (formDataResponse.getResults() != null && formDataResponse.getResults().size() > 0) {
                                List<ResultsModel> resultsModelList = formDataResponse.getResults();
                                if (resultsModelList != null && resultsModelList.size() > 0) {
                                    Log.d(TAG, "onResponseAssessment: " + "Success" + "- " + resultsModelList.size());
                                    Intent intent = new Intent(context, AssessmentCompleteActivity.class);
                                    intent.putExtra("MarksObtained", resultsModelList.get(0).getMarks());
                                    intent.putExtra("MarksObtained_status", resultsModelList.get(0).getResult());
                                    startActivity(intent);

                                    noOfUserAttemptsCount++;
                                    improveDataBase.updateNoOfUserAttemptsInELearningTable(noOfUserAttemptsCount, distributionId, sessionManager.getUserDataFromSession().getUserID());
                                    finish();
                                } else {

                                    improveHelper.dismissProgressDialog();
                                    Log.d(TAG, "onResponseAssessment: " + "Failure" + "- " + resultsModelList.size());
                                    finish();
                                }
                            } else {
                                improveHelper.dismissProgressDialog();
                                Log.d(TAG, "onResponseAssessment: " + "Failure Results Size " + "- " + formDataResponse.getResults().size());
                                finish();
                            }
                        } else {
                            improveHelper.dismissProgressDialog();
                            Log.d(TAG, "onResponseAssessment: " + "Failure");
//                        Log.d(TAG, "onResponseAssessment: " + formDataResponse.getMessage());
                            ImproveHelper.showToast(context, formDataResponse.getMessage());
                            finish();
                        }
                    } else {
                        improveHelper.dismissProgressDialog();
                        Log.d(TAG, "onResponseAssessment: " + "No response");
                    }
                }

                @Override
                public void onFailure(Call<FormDataResponse> call, Throwable t) {
                    improveHelper.dismissProgressDialog();
                    Log.d(TAG, "onFailureAssessment: " + t.getMessage());

                }
            });
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "sendDataToServer", e);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                assessmentAlertDialog(onAssessmentTimeSpent(MILLI_SEC, millisUntil));
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {

        assessmentAlertDialog(onAssessmentTimeSpent(MILLI_SEC, millisUntil));
    }

    public String onAssessmentTimeSpent(long MILLI_SEC, long millisUntil) {
        String strTimeSpent = null;
        try {
            long timeCompare = MILLI_SEC - millisUntil;
            strTimeSpent = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millisUntil),
                    TimeUnit.MILLISECONDS.toMinutes(timeCompare),
                    TimeUnit.MILLISECONDS.toSeconds(timeCompare) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeCompare)));
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "onAssessmentTimeSpent", e);
        }

        return strTimeSpent;
    }


    public void assessmentAlertDialog(String strTimeSpent) {

        try {
            ImproveHelper.alertDialogWithRoundShapeMaterialTheme(AssessmentListActivity.this, getString(R.string.are_you_sure),
                    getString(R.string.yes), getString(R.string.no), new ImproveHelper.IL() {
                        @Override
                        public void onSuccess() {
                            if (yourCountDownTimer != null) {
                                yourCountDownTimer.cancel();
                            }
                            improveHelper.showProgressDialog(getString(R.string.pls_wait_while_submitting));

                            prepareJsonToSubmit(strTimeSpent);
                        }

                        @Override
                        public void onCancel() {

                        }
                    });

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "assessmentAlertDialog", e);
        }
                /*try {
            new AlertDialog.Builder(context)
//set icon
//set title
                    .setIcon(getApplicationContext().getResources().getDrawable(R.drawable.icon_bhargo_user))
                    .setTitle(R.string.assesment)
                    .setMessage(R.string.quit_assessment)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what would happen when positive button is clicked
                            if (yourCountDownTimer != null) {
                                yourCountDownTimer.cancel();
                            }
                            improveHelper.showProgressDialog(getString(R.string.pls_wait_while_submitting));

                            prepareJsonToSubmit(strTimeSpent);
                        }
                    })
//set negative button
                    .setNegativeButton(R.string.d_no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what should happen when negative button is clicked
                            dialogInterface.dismiss();
                        }
                    })
                    .show();

//        }
        } catch (Exception e) {
            improveHelper.improveException(this, TAG, "assessmentAlertDialog", e);
        }*/


    }

    //    public void openInfoDialogOld(List<QuestionState> infoList) {
//        try {
//            final Dialog dialog = new Dialog(context, R.style.DialogTheme);
//            dialog.setCancelable(true);
//            dialog.setCanceledOnTouchOutside(true);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(R.layout.dialog_info);
//
//            GridView gridView = dialog.findViewById(R.id.info_gridView);
//
//            gridView.setAdapter(new InfoAdapter(context, currentQuestionIndex, infoList));
//
//            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                    currentQuestionIndex = position;
//                    showCurrentHideOthers(currentQuestionIndex);
//                    dialog.dismiss();
//
//                }
//            });
//
//
//            dialog.show();
//        } catch (Exception e) {
//            improveHelper.improveException(this, TAG, "openInfoDialog", e);
//        }
//
//    }
    public void openInfoDialog(List<QuestionState> infoList) {
        try {


            GridView gridView = findViewById(R.id.info_gridView);

            gridView.setAdapter(new InfoAdapter(context, currentQuestionIndex, infoList));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    currentQuestionIndex = position;
                    showCurrentHideOthers(currentQuestionIndex);
                    ImproveHelper.showHideBottomSheet(sheetBehavior);

                }
            });

        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "openInfoDialog", e);
        }

    }

    public void spiltQuestion(LinearLayout ll_questionParagraphContainer, String question, ImageView imageView, CustomTextView customTextView) {
        try {
            if (!question.isEmpty()) {
                Log.d(TAG, "Check_All_Questions: " + question);
                if (question.contains("$_")) {

                    int strCount = countFreq("$_", question);
                    if (strCount == 1 && imageView != null) {
                        if (question.contains("http")) {
                            Log.d(TAG, "QuestionsAllCheckD: " + question);

                            String[] questionDetailsList = question.split("\\$\\_");
                            for (String tempQus : questionDetailsList) {

                                if (tempQus.contains("http")) {
                                    Log.d(TAG, "spiltQuestionIfDI: " + tempQus);
//                                Glide.with(context).load(tempQus.replaceAll("\\$\\_", "")).into(imageView);
                                    imageView.setVisibility(View.VISIBLE);
//                            Glide.with(context).load(tempQus).into(imageView);
                                    String trimmedString = tempQus.replaceAll("\\s+", "");
                                    Glide.with(context)
                                            .asBitmap()
                                            .load(trimmedString)
                                            .into(new SimpleTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(Bitmap bitmap,
                                                                            Transition<? super Bitmap> transition) {
                                                    int w = bitmap.getWidth();
                                                    int h = bitmap.getHeight();
                                                    imageView.setImageBitmap(bitmap);
                                                    imageView.setMinimumHeight(h + 50);
                                                    imageView.setMinimumWidth(w + 50);
                                                }
                                            });
                                } else {
                                    Log.d(TAG, "spiltQuestionElseDT: " + tempQus);
                                    imageView.setVisibility(View.GONE);
                                    customTextView.setVisibility(View.VISIBLE);
                                    customTextView.setText(tempQus.trim());

                                }
                            }

                        } else {
//                    imageView.setVisibility(View.GONE);
                            Log.d(TAG, "spiltQuestionMainElseDT: " + question);
                            customTextView.setText(question);
                        }
                    } else {
                        ll_questionParagraphContainer.setVisibility(View.VISIBLE);
                        if (imageView != null)
                            imageView.setVisibility(View.GONE);
                        customTextView.setVisibility(View.GONE);


                        String[] arrOfStr = question.split("\\$\\_", strCount);
                        for (String Qst : arrOfStr) {
                            String[] questionDetailsList = Qst.split("\\$\\_");
                            for (String tempQus_ : questionDetailsList) {
                                final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View rView = linflater.inflate(R.layout.item_multiple_views, null);

                                ImageView ivMView = rView.findViewById(R.id.iv_question);
                                CustomTextView tvMView = rView.findViewById(R.id.tv_question);
                                if (tempQus_.contains("http")) {
                                    Log.d(TAG, "reqUrl1: " + tempQus_);
                                    ivMView.setVisibility(View.VISIBLE);


                                    String trimmedString = tempQus_.replaceAll("\\s+", "");
                                    Log.d(TAG, "reqUrl2: " + trimmedString);
//                                Glide.with(context).load(trimmedString).into(ivMView);
                                    Glide.with(context)
                                            .asBitmap()
                                            .load(trimmedString)
                                            .into(new SimpleTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(Bitmap bitmap,
                                                                            Transition<? super Bitmap> transition) {
                                                    int w = bitmap.getWidth();
                                                    int h = bitmap.getHeight();
                                                    ivMView.setImageBitmap(bitmap);
                                                    ivMView.setMinimumHeight(h + 50);
                                                    ivMView.setMinimumWidth(w + 50);
                                                }
                                            });

                                } else {
                                    tvMView.setVisibility(View.VISIBLE);
                                    tvMView.setText(tempQus_.trim());
                                }
                                ll_questionParagraphContainer.addView(rView);
                            }
                        }
                    }
                } else {
                    if (question.contains("http")) {
                        imageView.setVisibility(View.VISIBLE);
//                    Glide.with(context).load(question).into(imageView);
                        String trimmedString = question.replaceAll("\\s+", "");
                        Glide.with(context)
                                .asBitmap()
                                .load(trimmedString)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap bitmap,
                                                                Transition<? super Bitmap> transition) {
                                        int w = bitmap.getWidth();
                                        int h = bitmap.getHeight();
                                        imageView.setImageBitmap(bitmap);
                                        imageView.setMinimumHeight(h + 50);
                                        imageView.setMinimumWidth(w + 50);
                                    }
                                });
                    } else {
//                    imageView.setVisibility(View.GONE);
                        customTextView.setVisibility(View.VISIBLE);
                        customTextView.setText(question.trim());


                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "spiltQuestion", e);
        }

    }

    public char getAlphabetsByPosition(int position) {

        char ch = 0;
        try {
            String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            for (int i = 0; i < alphabet.length(); i++) {
                ch = alphabet.charAt(position);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(this, TAG, "getAlphabetsByPosition", e);
        }

        return ch;
    }

}