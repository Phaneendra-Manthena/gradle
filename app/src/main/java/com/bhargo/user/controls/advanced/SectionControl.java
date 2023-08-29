package com.bhargo.user.controls.advanced;

import static android.content.Intent.ACTION_PICK;
import static android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
import static android.provider.MediaStore.Video.Media.INTERNAL_CONTENT_URI;
import static com.bhargo.user.MainActivity.initImageLoader;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUDIO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_COMPLETION;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_GENERATION;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_NUMBER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_BAR_CODE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_BUTTON;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CALENDAR_EVENT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CALENDER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CAMERA;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHART;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHECKBOX;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHECK_LIST;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_COUNT_DOWN_TIMER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_COUNT_UP_TIMER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CURRENCY;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DATA_CONTROL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DATA_TABLE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DECIMAL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DROP_DOWN;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DYNAMIC_LABEL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_EMAIL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_FILE_BROWSING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_GPS;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_GRID_CONTROL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_IMAGE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_LARGE_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_LiveTracking;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_MAP;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_NUMERIC_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PASSWORD;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PERCENTAGE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PHONE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_POST;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PROGRESS;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_QR_CODE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_RADIO_BUTTON;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_RATING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SIGNATURE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SUBFORM;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_TEXT_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_TIME;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_URL_LINK;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_USER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_RECORDING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIEWFILE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VOICE_RECORDING;
import static com.bhargo.user.utils.AppConstants.MATCH_PARENT;
import static com.bhargo.user.utils.AppConstants.REQUEST_VIDEO_RECORDING;
import static com.bhargo.user.utils.AppConstants.REQ_CODE_PICK_ONLY_VIDEO_FILE;
import static com.bhargo.user.utils.AppConstants.REQ_CODE_PICK_VOICE_REC;
import static com.bhargo.user.utils.AppConstants.SECTION_FILE_BROWSER_RESULT_CODE;
import static com.bhargo.user.utils.AppConstants.SECTION_REQUEST_CAMERA_CONTROL_CODE;
import static com.bhargo.user.utils.AppConstants.SECTION_REQUEST_CURRENT_LOCATION;
import static com.bhargo.user.utils.AppConstants.SECTION_REQUEST_DOCUMENT_SCANNER;
import static com.bhargo.user.utils.AppConstants.SECTION_REQUEST_SIGNATURE_CONTROL_CODE;
import static com.bhargo.user.utils.AppConstants.SECTION_REQUEST_SPEECH_TO_TEXT;
import static com.bhargo.user.utils.AppConstants.SECTION_REQUEST_VIDEO_RECORDING;
import static com.bhargo.user.utils.AppConstants.SECTION_REQ_CODE_PICK_ONLY_VIDEO_FILE;
import static com.bhargo.user.utils.AppConstants.SECTION_REQ_CODE_PICK_VOICE_REC;
import static com.bhargo.user.utils.AppConstants.currentMultiForm;
import static com.bhargo.user.utils.ImproveHelper.pxToDPControlUI;
import static com.bhargo.user.utils.ImproveHelper.setDisplaySettings;
import static com.bhargo.user.utils.ImproveHelper.setViewDisable;
import static com.bhargo.user.utils.ImproveHelper.showToast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.Control_EventObject;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.Java_Beans.New_DataControl_Bean;
import com.bhargo.user.Java_Beans.VisibilityManagementOptions;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.data_controls.DataControl;
import com.bhargo.user.controls.standard.AudioPlayer;
import com.bhargo.user.controls.standard.AutoNumber;
import com.bhargo.user.controls.standard.Button;
import com.bhargo.user.controls.standard.Calendar;
import com.bhargo.user.controls.standard.CalendarEventControl;
import com.bhargo.user.controls.standard.Camera;
import com.bhargo.user.controls.standard.CheckList;
import com.bhargo.user.controls.standard.Checkbox;
import com.bhargo.user.controls.standard.CountDownTimerControl;
import com.bhargo.user.controls.standard.CountUpTimerControl;
import com.bhargo.user.controls.standard.Currency;
import com.bhargo.user.controls.standard.DecimalView;
import com.bhargo.user.controls.standard.DropDown;
import com.bhargo.user.controls.standard.DynamicLabel;
import com.bhargo.user.controls.standard.Email;
import com.bhargo.user.controls.standard.FileBrowsing;
import com.bhargo.user.controls.standard.Image;
import com.bhargo.user.controls.standard.LargeInput;
import com.bhargo.user.controls.standard.MapControl;
import com.bhargo.user.controls.standard.NumericInput;
import com.bhargo.user.controls.standard.Password;
import com.bhargo.user.controls.standard.Percentage;
import com.bhargo.user.controls.standard.Phone;
import com.bhargo.user.controls.standard.RadioGroupView;
import com.bhargo.user.controls.standard.Rating;
import com.bhargo.user.controls.standard.SignatureView;
import com.bhargo.user.controls.standard.TextInput;
import com.bhargo.user.controls.standard.Time;
import com.bhargo.user.controls.standard.UrlView;
import com.bhargo.user.controls.standard.VideoPlayer;
import com.bhargo.user.controls.standard.VideoRecording;
import com.bhargo.user.controls.standard.ViewFileControl;
import com.bhargo.user.controls.standard.VoiceRecording;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.EditOrViewColumns;
import com.bhargo.user.uisettings.ControlUiSettings;
import com.bhargo.user.uisettings.pojos.ControlUIProperties;
import com.bhargo.user.uisettings.pojos.LayoutProperties;
import com.bhargo.user.uisettings.pojos.MappingControlModel;
import com.bhargo.user.uisettings.pojos.UILayoutProperties;
import com.bhargo.user.uisettings.pojos.UIPrimaryLayoutModelClass;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.ControlManagement;
import com.bhargo.user.utils.FilePicker;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.SessionManager;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nk.doc.scanner.DocumentScannerActivity;

public class SectionControl implements UIVariables {

    private static final String TAG = "SectionControl";
    private final int height, width;
    public JSONArray jArrayAutoIncrementControls = new JSONArray();
    private String app_edit = "New";
    //=======Load_Onchange_EventObjects================
  /*  public LinkedHashMap<String, Control_EventObject> hash_Onchange_EventObjects = new LinkedHashMap<String, Control_EventObject>();
    //=======Load_Onfocus_EventObjects================
    public LinkedHashMap<String, Control_EventObject> hash_Onfocus_EventObjects = new LinkedHashMap<String, Control_EventObject>();
    // LinkedHashMap<String, Object> List_ControlClassObjects = new LinkedHashMap<String, Object>();
    //=======Load_OnClick_EventObjects================
    public LinkedHashMap<String, Control_EventObject> hash_Onclick_EventObjects = new LinkedHashMap<String, Control_EventObject>();*/
    Activity context;
    DataCollectionObject dataCollectionObject;
    public ControlObject controlObject;
    ImproveHelper improveHelper;
    boolean sectionFlag = false;
    int sectionPos;
    String sectionName;
    String appLanguage = "en";
    String strAppName = "";
    String strUserLocationStructure;
    SessionManager sessionManager;
    public LinkedHashMap<String, Object> New_list_ControlClassObjects = new LinkedHashMap<String, Object>();
    int screenHeight = -2;
    Gson gson = new Gson();
    boolean formLoad = false;
    boolean formPreLoad = false;
    HashMap<String, ControlObject> globalControlObjects = new HashMap<>();
    HashMap<String, ControlObject> controlObjectMapSection = new HashMap<>();
    int layoutHeight;
    UILayoutProperties uiLayoutPropertiesSection;
    ControlUIProperties controlUIPropertiesSection;
    private LinearLayout linearLayout;
    private CustomTextView tv_displayName, tv_hint;
    CustomButton btn_expand, btn_collapse;
    private View view;
    private LinearLayout ll_main_inside, ll_displayName, ll_sectionContainer, ll_layoutBackgroundColor, ll_label;
    private CardView cardView;
    private boolean isAutoNumbersAvaliable;
    private ImageLoader imageLoader;
    private boolean isScreenFit;
    private int toolBarheight;
    private int screenWidth = -1;
    private ScrollView uFScrollView;
    List<ControlObject> sectionControlList = new ArrayList<>();
    public View activityView;
    private int subFormPos;
    private LinkedHashMap<String, String> subFormTableSettingsTypeMap = new LinkedHashMap<>();
    private  LinkedHashMap<String, Boolean> subFormsAutoNumberStatusMap = new LinkedHashMap<>();
    public  LinkedHashMap<String, JSONArray> subFormsAutoNumberArraysMap = new LinkedHashMap<>();
    List<EditOrViewColumns> editColumns;
    VisibilityManagementOptions visibilityManagementOptions;

    public SectionControl(Activity context, DataCollectionObject dataCollectionObject,
                          ControlObject controlObject, String strUserLocationStructure,
                          boolean SectionFlag, int SectionPos, String SectionName,
                          String strAppName, LinkedHashMap<String, Object> New_list_ControlClassObjects,
                          int layoutHeight, UILayoutProperties uiLayoutProperties,
                          ControlUIProperties controlUIProperties,
                          HashMap<String, ControlObject> globalControlObjects, View activityView,
                          LinkedHashMap<String, String> subFormTableSettingsTypeMap,
                          LinkedHashMap<String, Boolean> subFormsAutoNumberStatusMap,
                          LinkedHashMap<String, JSONArray> subFormsAutoNumberArraysMap,
                          List<EditOrViewColumns> editColumns,
    VisibilityManagementOptions visibilityManagementOptions,String app_edit) {

        this.context = context;
        this.dataCollectionObject = dataCollectionObject;
        this.controlObject = controlObject;
        this.sectionFlag = SectionFlag;
        this.sectionPos = SectionPos;
        this.sectionName = SectionName;
        this.strAppName = strAppName;
        this.strUserLocationStructure = strUserLocationStructure;
        this.New_list_ControlClassObjects = New_list_ControlClassObjects;
        this.screenHeight = layoutHeight;
        this.uiLayoutPropertiesSection = uiLayoutProperties;
        this.controlUIPropertiesSection = controlUIProperties;
        this.globalControlObjects = globalControlObjects;
        this.activityView = activityView;
        this.subFormTableSettingsTypeMap = subFormTableSettingsTypeMap;
        this.subFormsAutoNumberStatusMap = subFormsAutoNumberStatusMap;
        this.subFormsAutoNumberArraysMap = subFormsAutoNumberArraysMap;
        this.editColumns = editColumns;
        this.visibilityManagementOptions = visibilityManagementOptions;
        this.app_edit = app_edit;
        improveHelper = new ImproveHelper(context);
        toolBarheight = improveHelper.dpToPx(56);
        appLanguage = ImproveHelper.getLocale(context);
        sessionManager = new SessionManager(context);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        height = dm.heightPixels;
        width = dm.widthPixels;
        screenWidth = width;
        initImageLoader(context);
        initView();

    }

    public void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            final LayoutInflater linflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//            view = linflater.inflate(R.layout.control_section, null);
            view = linflater.inflate(R.layout.control_section_default, null);

            uFScrollView = view.findViewById(R.id.uFScrollView);
            ll_main_inside = view.findViewById(R.id.ll_main_inside);
            ll_displayName = view.findViewById(R.id.ll_displayName);
            ll_layoutBackgroundColor = view.findViewById(R.id.ll_layoutBackgroundColor);
            ll_label = view.findViewById(R.id.ll_label);
            cardView = view.findViewById(R.id.card);
            ll_sectionContainer = view.findViewById(R.id.ll_sectionContainer);
            tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            btn_expand = view.findViewById(R.id.btn_expand);
            btn_collapse = view.findViewById(R.id.btn_collapse);


            btn_collapse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uFScrollView.setVisibility(View.VISIBLE);
                    btn_collapse.setVisibility(View.GONE);
                    btn_expand.setVisibility(View.VISIBLE);
                    ll_displayName.setBackground(context.getDrawable(R.drawable.top_left_right_rounded_corners_default_gray_bg));
                }
            });
            btn_expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uFScrollView.setVisibility(View.GONE);
                    btn_collapse.setVisibility(View.VISIBLE);
                    btn_expand.setVisibility(View.GONE);
                    ll_displayName.setBackground(context.getDrawable(R.drawable.rounded_corners_default_gray_bg));
                }
            });


            setControlValues();

            linearLayout.addView(view);

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "initView", e);
        }
    }

    private void setControlValues() {
        try {

            if (controlObject.getDisplayName() != null) {
                tv_displayName.setText(controlObject.getDisplayName());
            }
            if (controlObject.getHint() != null && !controlObject.getHint().isEmpty()) {
                tv_hint.setVisibility(View.VISIBLE);
                tv_hint.setText(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }
            if (controlObject.isEnableCollapseOrExpand()) {
                btn_expand.setVisibility(View.VISIBLE);
            } else {
                btn_expand.setVisibility(View.GONE);
            }

            if (controlObject.isDisable()) {
//                setViewDisable(view, false);
            }
            if (controlObject.isHideDisplayName()) {
                ll_displayName.setVisibility(View.GONE);
            }else{
                ll_displayName.setVisibility(View.VISIBLE);
            }
            setDisplaySettings(context, tv_displayName, controlObject);

//            sectionControlList = controlObject.getSectionControlList();
          /*  if (controlObject.isMakeItAsPopup()) {
                ll_main_inside.setVisibility(View.GONE);
            } else {*/
                ll_main_inside.setVisibility(View.VISIBLE);
                sectionControlList = controlObject.getSubFormControlList();
                if (sectionControlList != null && sectionControlList.size() > 0) {

                    globalControlObjectsSection(sectionControlList);

                    if (controlObject.isSectionUIFormNeeded()) { // SectionCheck
                        Log.d(TAG, "SectionControlOne: " + "Two");
                        sectionControlUISettings(controlUIPropertiesSection,ll_sectionContainer);
                        UIPrimaryLayoutModelClass uiPrimaryLayoutModelClass = controlObject.getUiPrimaryLayoutModelClass();
                        primaryLayoutData(uiPrimaryLayoutModelClass, ll_sectionContainer);

                    } else {
                        for (int i = 0; i < sectionControlList.size(); i++) {
                            loadControl(sectionControlList.get(i),
                                    sectionControlList.get(i).getControlType(),
                                    ll_sectionContainer, null, null, 0, New_list_ControlClassObjects);
                        }
                    }
                }

            //}
//            controlManagementOptions();

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setControlValues", e);
        }
    }

    private HashMap<String, ControlObject> globalControlObjectsSection(List<ControlObject> sectionControlList) {

        for (int k = 0; k < sectionControlList.size(); k++) {
            if (!sectionControlList.get(k).getControlName().equals("")) {
                globalControlObjects.put(sectionControlList.get(k).getControlName(), sectionControlList.get(k));
            }
        }
        return globalControlObjects;
    }
    private HashMap<String, ControlObject> globalControlObjectsSectionOld(List<ControlObject> sectionControlList) {

        for (int k = 0; k < sectionControlList.size(); k++) {
            if (!sectionControlList.get(k).getControlName().equals("")) {
                controlObjectMapSection.put(sectionControlList.get(k).getControlName(), sectionControlList.get(k));
            }
        }
        return controlObjectMapSection;
    }

    private void loadControl(ControlObject loadControlObject, String controlType, LinearLayout ll_sectionContainer, UILayoutProperties uiLayoutProperties, ControlUIProperties controlUIProperties, int layoutHeight, LinkedHashMap<String, Object> List_ControlClassObjects) {
        LinearLayout linearLayout = ll_sectionContainer;
        try {
            ((MainActivity) context).list_Control.add(loadControlObject);
            /*  if(!keepSession) {*/
            Log.d(TAG, "XmlHelperTextInputSection: " + controlType + loadControlObject.isEnableMinCharacters());
            if (!appLanguage.contentEquals("en") && loadControlObject.getTranslationsMappingObject() != null) {
           /* LinkedHashMap<String,String> map = loadControlObject.getTranslationsMap();
            loadControlObject.setDisplayName(map.get(appLanguage));*/
                LinkedHashMap<String, ControlObject> map = loadControlObject.getTranslationsMappingObject();
                ControlObject langObject = map.get(appLanguage);
                if (langObject != null) {
                    if (langObject.getDisplayName() != null && !langObject.getDisplayName().contentEquals("")) {
                        loadControlObject.setDisplayName(langObject.getDisplayName());
                    }
                    if (langObject.getHint() != null && !langObject.getHint().contentEquals("")) {
                        loadControlObject.setHint(langObject.getHint());
                    }
                    if (langObject.getMandatoryFieldError() != null && !langObject.getMandatoryFieldError().contentEquals("")) {
                        loadControlObject.setMandatoryFieldError(langObject.getMandatoryFieldError());
                    }
                    if (langObject.getUniqueFieldError() != null && !langObject.getUniqueFieldError().contentEquals("")) {
                        loadControlObject.setUniqueFieldError(langObject.getUniqueFieldError());
                    }
                    if (langObject.getUpperLimitErrorMesage() != null && !langObject.getUpperLimitErrorMesage().contentEquals("")) {
                        loadControlObject.setUpperLimitErrorMesage(langObject.getUpperLimitErrorMesage());
                    }
                    if (langObject.getLowerLimitErrorMesage() != null && !langObject.getLowerLimitErrorMesage().contentEquals("")) {
                        loadControlObject.setLowerLimitErrorMesage(langObject.getLowerLimitErrorMesage());
                    }
                    if (langObject.getCappingError() != null && !langObject.getCappingError().contentEquals("")) {
                        loadControlObject.setCappingError(langObject.getCappingError());
                    }

                    if (langObject.getMaxCharError() != null && !langObject.getMaxCharError().contentEquals("")) {
                        loadControlObject.setMaxCharError(langObject.getMaxCharError());
                    }
                    if (langObject.getMinCharError() != null && !langObject.getMinCharError().contentEquals("")) {
                        loadControlObject.setMinCharError(langObject.getMinCharError());
                    }
                    if (langObject.getMaxUploadError() != null && !langObject.getMaxUploadError().contentEquals("")) {
                        loadControlObject.setMaxUploadError(langObject.getMaxUploadError());
                    }
                    if (langObject.getMinUploadError() != null && !langObject.getMinUploadError().contentEquals("")) {
                        loadControlObject.setMinUploadError(langObject.getMinUploadError());
                    }
                    if (langObject.getBetweenStartAndEndDateError() != null && !langObject.getBetweenStartAndEndDateError().contentEquals("")) {
                        loadControlObject.setBetweenStartAndEndDateError(langObject.getBetweenStartAndEndDateError());
                    }

                    if (langObject.getBeforeCurrentDateError() != null && !langObject.getBeforeCurrentDateError().contentEquals("")) {
                        loadControlObject.setBeforeCurrentDateError(langObject.getBeforeCurrentDateError());
                    }
                    if (langObject.getAfterCurrentDateError() != null && !langObject.getAfterCurrentDateError().contentEquals("")) {
                        loadControlObject.setAfterCurrentDateError(langObject.getAfterCurrentDateError());
                    }

                    if (langObject.getMaximumDurationError() != null && !langObject.getMaximumDurationError().contentEquals("")) {
                        loadControlObject.setMaximumDurationError(langObject.getMaximumDurationError());
                    }
                    if (langObject.getMinimumDurationError() != null && !langObject.getMinimumDurationError().contentEquals("")) {
                        loadControlObject.setMinimumDurationError(langObject.getMinimumDurationError());
                    }

                    if (langObject.getMaxAmountError() != null && !langObject.getMaxAmountError().contentEquals("")) {
                        loadControlObject.setMaxAmountError(langObject.getMaxAmountError());
                    }
                    if (langObject.getMinAmountError() != null && !langObject.getMinAmountError().contentEquals("")) {
                        loadControlObject.setMinAmountError(langObject.getMinAmountError());
                    }

                    if (langObject.getMaximumRowsError() != null && !langObject.getMaximumRowsError().contentEquals("")) {
                        loadControlObject.setMaximumRowsError(langObject.getMaximumRowsError());
                    }
                    if (langObject.getMinimumRowsError() != null && !langObject.getMinimumRowsError().contentEquals("")) {
                        loadControlObject.setMinimumRowsError(langObject.getMinimumRowsError());
                    }

                    if (loadControlObject.getControlType().contentEquals(CONTROL_TYPE_DYNAMIC_LABEL) && loadControlObject.isMakeAsSection()) {
                        loadControlObject.setValue(langObject.getDisplayName());
                    }


                    if (langObject.getItemsList() != null && langObject.getItemsList().size() > 0) {
                        loadControlObject.setItemsList(langObject.getItemsList());

                        List<String> items = new ArrayList<>();
                        for (int i = 0; i < loadControlObject.getItemsList().size(); i++) {

                            items.add(loadControlObject.getItemsList().get(i).getValue());
                        }
                        loadControlObject.setItems(items);
                    }
                }

            }

            switch (controlType) {

                case CONTROL_TYPE_TEXT_INPUT:
                    Log.d(TAG, "SectionTextInputMinChar: " + controlType + " " +loadControlObject.isGoogleLocationSearch());
                    final TextInput textInput = new TextInput(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), textInput);
                    linearLayout.addView(textInput.getTextInputView());

                    if (loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE); // taptext
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        if (loadControlObject.isReadFromBarcode()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_barcode_drawable));
                            textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                    textInput.tQRBarCode();
                                }
                            });
                        } else if (loadControlObject.isReadFromQRCode()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_qrcode_drawable));
                            textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                    textInput.tQRBarCode();
                                }
                            });
                        } else if (loadControlObject.isVoiceText()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));
                            textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                    textInput.getSpeechInput(true, SECTION_REQUEST_SPEECH_TO_TEXT);
                                }
                            });
                        } else if (loadControlObject.isCurrentLocation()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_my_location_black_24dp));
                            textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                                context.startActivityForResult(new Intent(context, GPSActivity.class), REQUEST_CURRENT_LOCATION);
                                    textInput.tCurrentLocation(SECTION_REQUEST_CURRENT_LOCATION);
                                }
                            });
                        } else if (loadControlObject.isGoogleLocationSearch()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_current_location));
                            textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                    textInput.initAutoCompleteTextView();
                                }
                            });
                        }
//                    textInput.getCustomEditText().setVisibility(View.VISIBLE);
//                    textInput.getCustomEditText().setText(loadControlObject.getDefaultValue());
                    } else if (loadControlObject.isReadFromBarcode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE);
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_barcode_drawable));
                        textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                textInput.tQRBarCode();
                            }
                        });
                    } else if (loadControlObject.isReadFromQRCode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE);
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_qrcode_drawable));
                        textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                textInput.tQRBarCode();
                            }
                        });
                    } else if (loadControlObject.isVoiceText() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE);
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));
                        textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                textInput.getSpeechInput(true, SECTION_REQUEST_SPEECH_TO_TEXT);
                            }
                        });
                    } else if (loadControlObject.isCurrentLocation() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE);
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_current_location));
                        textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                textInput.tCurrentLocation(SECTION_REQUEST_CURRENT_LOCATION);

                            }
                        });
                    } else if (loadControlObject.isReadFromBarcode()) {
                        textInput.getLl_tap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setText(R.string.tap_here_to_scan_bar_code);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_barcode_drawable));
                        textInput.gettap_text().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                textInput.tQRBarCode();
                            }
                        });
                        textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                textInput.tQRBarCode();
                            }
                        });
                    } else if (loadControlObject.isReadFromQRCode()) {
                        textInput.getLl_tap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setText(R.string.tap_here_to_scan_qr_code);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_qrcode_drawable));
                        textInput.gettap_text().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                textInput.tQRBarCode();
                            }
                        });
                        textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                textInput.tQRBarCode();
                            }
                        });
                    } else if (loadControlObject.isVoiceText()) {
                        textInput.getLl_tap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setText("Tap here voice to text");
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));

                        textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                textInput.getSpeechInput(false, SECTION_REQUEST_SPEECH_TO_TEXT);

                            }
                        });
                        textInput.gettap_text().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                textInput.getSpeechInput(false, SECTION_REQUEST_SPEECH_TO_TEXT);

                            }
                        });
                    } else if (loadControlObject.isCurrentLocation()) {
                        //nk gps DataCollectionControlsValidation,MapExistingControlsValidation
                        textInput.gettap_text().setText("Tap here to get Current Location");
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_current_location));
                        textInput.gettap_text().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                textInput.tCurrentLocation(SECTION_REQUEST_CURRENT_LOCATION);
                            }
                        });
                        textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                textInput.tCurrentLocation(SECTION_REQUEST_CURRENT_LOCATION);
                            }
                        });
                    } else if (loadControlObject.isGoogleLocationSearch()) {

                        textInput.gettap_text().setText("Tap here to Search Location");
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_current_location));

                        textInput.setDefaultValueForSearch();

                        textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                textInput.initAutoCompleteTextView();

//                    googleSearchPlaces(v);
                            }
                        });
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        ((MainActivity) context).hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    if (controlObject.isSectionUIFormNeeded() && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        android.view.ViewGroup.LayoutParams layoutParamsImg = textInput.getLl_tap_text().getLayoutParams();
                        if (loadControlObject.getDisplayNameAlignment() != null && loadControlObject.getDisplayNameAlignment().equalsIgnoreCase("4")) {
                            if (controlUIProperties != null && controlUIProperties.getTypeOfWidth() != null
                                    && !controlUIProperties.getTypeOfWidth().isEmpty() && controlUIProperties.getTypeOfWidth().equalsIgnoreCase(context.getString(R.string.Custom_Width))) {
//                                layoutParamsImg.width = pxToDP(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
//                                layoutParamsImg.height = pxToDP(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                textInput.getLl_tap_text().setLayoutParams(layoutParamsImg);
//                                controlAlignments(AppConstants.uiLayoutPropertiesStatic, textInput.getLl_tap_text());
                            }
                        }
                        if (linearLayout.getChildCount() == 1) {
                            linearLayout.getChildAt(0).setLayoutParams(params);
                        } else {
                            linearLayout.getChildAt(2).setLayoutParams(params);
                        }

                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                        textInput.getLl_main_inside().setLayoutParams(params);
                    }
                    if (controlObject.isSectionUIFormNeeded && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject,AppConstants.uiLayoutPropertiesStatic,controlUIProperties,List_ControlClassObjects,context);
                        controlUiSettings.setControlUiSettings();
                    }

                    break;
                case CONTROL_TYPE_NUMERIC_INPUT:
                    NumericInput numericInput = new NumericInput(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), numericInput);
                    linearLayout.addView(numericInput.getNumericInputView());
                    if (loadControlObject.isInvisible()) {
                        numericInput.getNumericInputView().setVisibility(View.GONE);
                    } else {
                        numericInput.getNumericInputView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        ((MainActivity) context).hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    if (controlObject.isSectionUIFormNeeded() && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        if (linearLayout.getChildCount() == 1) {
                            linearLayout.getChildAt(0).setLayoutParams(params);
                        } else {
                            linearLayout.getChildAt(2).setLayoutParams(params);
                        }
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                        numericInput.getLl_main_inside().setLayoutParams(params);
//                        controlAlignments(AppConstants.uiLayoutPropertiesStatic, numericInput.getLl_main_inside());

                    }

                    break;
                case CONTROL_TYPE_PHONE:
                    Phone phone = new Phone(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), phone);

                    linearLayout.addView(phone.getPhoneView());

                    if (loadControlObject.isInvisible()) {
                        phone.getPhoneView().setVisibility(View.GONE);
                    } else {
                        phone.getPhoneView().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        ((MainActivity) context).hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_EMAIL:
                    Email email = new Email(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), email);
                    linearLayout.addView(email.getEmailView());
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        ((MainActivity) context).hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_CAMERA:
//                    final Camera camera = new Camera(context, loadControlObject, false, 0, "");
                    final Camera camera = new Camera(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), camera);

                    linearLayout.addView(camera.getCameraView());

                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    camera.getLLTapTextView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();

                            camera.getCameraClickView(SECTION_REQUEST_CAMERA_CONTROL_CODE, activityView);

                            if (controlObject.isOnChangeEventExists()) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }

                        }
                    });
                    camera.getFileNameTextView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();

                            camera.getCameraClickView(SECTION_REQUEST_CAMERA_CONTROL_CODE, activityView);

                            if (controlObject.isOnChangeEventExists()) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }

                        }
                    });
                    camera.getReTake().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();

                            camera.getCameraClickView(SECTION_REQUEST_CAMERA_CONTROL_CODE, activityView);

                            if (controlObject.isOnChangeEventExists()) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }
                        }
                    });
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    break;
                case CONTROL_TYPE_LARGE_INPUT:
                    LargeInput largeInput = new LargeInput(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), largeInput);

                    linearLayout.addView(largeInput.getLargeInputView());

                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        ((MainActivity) context).hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }

                    break;
                case CONTROL_TYPE_CHECKBOX:
                    Checkbox checkbox = new Checkbox(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), checkbox);

                    linearLayout.addView(checkbox.getCheckbox());

                    if (loadControlObject.isInvisible()) {
                        checkbox.getCheckbox().setVisibility(View.GONE);
                    } else {
                        checkbox.getCheckbox().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        ((MainActivity) context).hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_FILE_BROWSING:
//                    FileBrowsing fileBrowsing = new FileBrowsing(context, loadControlObject);
                    FileBrowsing fileBrowsing = new FileBrowsing(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), fileBrowsing);

                    linearLayout.addView(fileBrowsing.getFileBrowsingView());

                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    fileBrowsing.getFileBrowseImage().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                            fileBrowsing.onClick(v, SECTION_FILE_BROWSER_RESULT_CODE);
//                            Intent intent = new Intent(context, FilePicker.class);
//                            ArrayList<String> extensions = new ArrayList<>();
//                            if (controlObject.isEnableExtensions()) {
//                                for (int i = 0; i < controlObject.getExtensionsListNames().size(); i++) {
//                                    extensions.add("." + controlObject.getExtensionsListNames().get(i).toLowerCase());
//                                }
//                                intent.putStringArrayListExtra(FilePicker.EXTRA_ACCEPTED_FILE_EXTENSIONS, extensions);
//                            }
//
//                            context.startActivityForResult(intent, SECTION_FILE_BROWSER_RESULT_CODE);

                            if (controlObject.isOnChangeEventExists()) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }
                            fileBrowsing.setAlertFileBrowser().setVisibility(View.GONE);
                            fileBrowsing.setAlertFileBrowser().setText("");
                        }
                    });

                    fileBrowsing.getTapTextViewLayout().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //nk: ScannerOrFileManager
                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                            startActivityForResult(new Intent(MainActivity.this, FilePicker.class), FILE_BROWSER_RESULT_CODE);


                            if (loadControlObject.isOnChangeEventExists()) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }
                            fileBrowsing.setAlertFileBrowser().setVisibility(View.GONE);
                            fileBrowsing.setAlertFileBrowser().setText("");

                            if (loadControlObject.isEnableScan()) {
                                Intent intentDocScanner = new Intent(context, DocumentScannerActivity.class);
                                context.startActivityForResult(intentDocScanner, SECTION_REQUEST_DOCUMENT_SCANNER);
                            } else {
                                fileBrowsing.onClick(v, SECTION_FILE_BROWSER_RESULT_CODE);
                                //nk pending
//                                showFileChooser();
//                                ((MainActivity) context).showFileChooser(loadControlObject, SECTION_FILE_BROWSER_RESULT_CODE);
                            }


                        }
                    });
                    fileBrowsing.getTapTextView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //nk: ScannerOrFileManager
                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                            startActivityForResult(new Intent(MainActivity.this, FilePicker.class), FILE_BROWSER_RESULT_CODE);


                            if (loadControlObject.isOnChangeEventExists()) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }
                            fileBrowsing.setAlertFileBrowser().setVisibility(View.GONE);
                            fileBrowsing.setAlertFileBrowser().setText("");

                            if (loadControlObject.isEnableScan()) {
                                Intent intentDocScanner = new Intent(context, DocumentScannerActivity.class);
                                context.startActivityForResult(intentDocScanner, SECTION_REQUEST_DOCUMENT_SCANNER);
                            } else {
                                fileBrowsing.onClick(v, SECTION_FILE_BROWSER_RESULT_CODE);
                                //nk pending
//                                showFileChooser();
//                                ((MainActivity) context).showFileChooser(loadControlObject, SECTION_FILE_BROWSER_RESULT_CODE);
                            }


                        }
                    });
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_VOICE_RECORDING:
                    final VoiceRecording voiceRecording = new VoiceRecording(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), voiceRecording);
                    final CountDownTimer[] yourCountDownTimer = new CountDownTimer[1];
                    final long[] MILLI_SEC = new long[1];
                    final long[] millis = new long[1];

                    linearLayout.addView(voiceRecording.getVoiceRecordingView());

                    if (loadControlObject.isInvisible()) {
                        voiceRecording.getVoiceRecordingView().setVisibility(View.GONE);
                    } else {
                        voiceRecording.getVoiceRecordingView().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }

                    if(voiceRecording.startRecordingClick() != null) {
                        voiceRecording.startRecordingClick().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();


                                if (loadControlObject.isEnableVoiceMaximumDuration() && loadControlObject.getVoiceMaximumDuration() != null) {
                                    String maxDuration = "0";
                                    if (loadControlObject.getVoiceMaximumDuration().contains(".")) {
                                        if (loadControlObject.getVoiceMaximumDuration().startsWith(".")) {
                                            maxDuration = "0" + loadControlObject.getVoiceMaximumDuration();
                                        } else {
                                            maxDuration = loadControlObject.getVoiceMaximumDuration();
                                        }
                                    } else {
                                        maxDuration = loadControlObject.getVoiceMaximumDuration() + ".0";
                                    }
                                    int bD = Integer.parseInt(maxDuration.split("\\.")[0]);
                                    int aD = Integer.parseInt(maxDuration.split("\\.")[1].substring(0, 1));
                                    int afterDecimal = aD * 6;

                                    MILLI_SEC[0] = bD * 60000 + afterDecimal * 1000;

                                } else {
                                    MILLI_SEC[0] = 3600000;
                                }
                                voiceRecording.getStartVoiceRecording();


                                yourCountDownTimer[0] = new CountDownTimer(MILLI_SEC[0], 1000) { // adjust the milli seconds here

                                    public void onTick(long millisUntilFinished) {
                                        millis[0] = millisUntilFinished;
                                        Log.d(TAG, "onTickVoiceRecodingTime: " + String.format("%02d:%02d",
                                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                                    }

                                    public void onFinish() {

                                        voiceRecording.getStopVoiceRecording();

                                    }
                                }.start();

                            }
                        });
                    }


                    if(voiceRecording.getVoiceRecordingClick() != null) {
                        voiceRecording.getVoiceRecordingClick().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int recorderState = voiceRecording.getRecorderState();
                                if (recorderState == 0) {

                                    AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                    if (loadControlObject.isEnableVoiceMaximumDuration() && loadControlObject.getVoiceMaximumDuration() != null) {
                                        String maxDuration = "0";
                                        if (loadControlObject.getVoiceMaximumDuration().contains(".")) {
                                            if (loadControlObject.getVoiceMaximumDuration().startsWith(".")) {
                                                maxDuration = "0" + loadControlObject.getVoiceMaximumDuration();
                                            } else {
                                                maxDuration = loadControlObject.getVoiceMaximumDuration();
                                            }
                                        } else {
                                            maxDuration = loadControlObject.getVoiceMaximumDuration() + ".0";
                                        }
                                        int bD = Integer.parseInt(maxDuration.split("\\.")[0]);
                                        int aD = Integer.parseInt(maxDuration.split("\\.")[1].substring(0, 1));
                                        int afterDecimal = aD * 6;

                                        MILLI_SEC[0] = bD * 60000 + afterDecimal * 1000;

                                    } else {
                                        MILLI_SEC[0] = 3600000;
                                    }
                                    voiceRecording.getStartVoiceRecording();


                                    yourCountDownTimer[0] = new CountDownTimer(MILLI_SEC[0], 1000) { // adjust the milli seconds here

                                        public void onTick(long millisUntilFinished) {
                                            millis[0] = millisUntilFinished;
                                            Log.d(TAG, "onTickVoiceRecodingTime: " + String.format("%02d:%02d",
                                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                                        }

                                        public void onFinish() {

                                            voiceRecording.getStopVoiceRecording();

                                        }
                                    }.start();
                                }
                                else if (recorderState == 1) {
                                    AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();

                                    if (loadControlObject.isEnableVoiceMinimumDuration() && loadControlObject.getVoiceMinimumDuration() != null) {
                                        long lMillis = MILLI_SEC[0] - millis[0];

                                        String minDuration = "0.0";
                                        if (loadControlObject.getVoiceMinimumDuration().contains(".")) {
                                            if (loadControlObject.getVoiceMinimumDuration().startsWith(".")) {
                                                minDuration = "0" + loadControlObject.getVoiceMinimumDuration();
                                            } else {
                                                minDuration = loadControlObject.getVoiceMinimumDuration();
                                            }
                                        } else {
                                            minDuration = loadControlObject.getVoiceMinimumDuration() + ".0";
                                        }
                                        int beforeDecimal = Integer.parseInt(minDuration.split("\\.")[0]);
                                        int aD = Integer.parseInt(minDuration.split("\\.")[1].substring(0, 1));
                                        int afterDecimal = aD * 6;
                                        long minDurationInMills = beforeDecimal * 60000 + afterDecimal * 1000;

                                        if (lMillis >= minDurationInMills) {
                                            voiceRecording.getStopVoiceRecording();
                                            yourCountDownTimer[0].cancel();
                                        } else {
                                            showToast(context, loadControlObject.getMinimumDurationError());
                                        }

                                    } else {
                                        voiceRecording.getStopVoiceRecording();
                                        yourCountDownTimer[0].cancel();
                                    }
                                } else if (recorderState == 2) {
                                    AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                    voiceRecording.getPlayViewVoiceRecording();
                                }

                            }
                        });
                    }
//                    voiceRecording.getImageViewPlay().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                            voiceRecording.getPlayViewVoiceRecording();
//                        }
//                    });
//
//                    voiceRecording.getImageViewStop().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                            voiceRecording.getStopViewVoiceRecording();
//                        }
//                    });
                    if(voiceRecording.getLayoutAudioFileUpload() != null) {
                        voiceRecording.getLayoutAudioFileUpload().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                voiceRecording.getVoiceUploadFile(v, SECTION_REQ_CODE_PICK_VOICE_REC);
                            }
                        });
                    }

                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_VIDEO_RECORDING:
                    final VideoRecording videoRecording = new VideoRecording(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), videoRecording);

                    linearLayout.addView(videoRecording.getVideoRecorderView());

                    if (loadControlObject.isInvisible()) {
                        videoRecording.getVideoRecorderView().setVisibility(View.GONE);
                    } else {
                        videoRecording.getVideoRecorderView().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if(videoRecording.getTv_startVideoRecorder() != null) {
                        videoRecording.getTv_startVideoRecorder().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                videoRecording.getStartVideoRecorderClick(activityView, SECTION_REQUEST_VIDEO_RECORDING, SECTION_REQ_CODE_PICK_ONLY_VIDEO_FILE);

                            }
                        });
                    }
                    if(videoRecording.getIv_textTypeImage() != null) {
                        videoRecording.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                                videoRecording.getStartVideoRecorderClick(activityView, SECTION_REQUEST_VIDEO_RECORDING, SECTION_REQ_CODE_PICK_ONLY_VIDEO_FILE);

                            }
                        });
                    }




                   /* videoRecording.getTv_videoFileUpload().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                            videoRecording.getVideoUploadFile(v, REQ_CODE_PICK_ONLY_VIDEO_FILE);
                        }
                    });*/


                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_SIGNATURE:
                    final SignatureView signature = new SignatureView(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), signature);

                    linearLayout.addView(signature.getSignature());

                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    /*Clear Signature*/
                    signature.getTv_clearSignature().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();

                            signature.Clear();

                            if (controlObject.isOnChangeEventExists()) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }
                            signature.getClearSignature();
                        }
                    });

                    signature.getLayoutUpload().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            context.startActivityForResult(intent, SECTION_REQUEST_SIGNATURE_CONTROL_CODE);

                        }
                    });

                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    break;

                case CONTROL_TYPE_CALENDER:
                    Calendar calendar = new Calendar(context, loadControlObject, false, 0, sectionName);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), calendar);

                    linearLayout.addView(calendar.getCalnderView());

                    if (loadControlObject.isInvisible()) {
                        calendar.getCalnderView().setVisibility(View.GONE);
                    } else {
                        calendar.getCalnderView().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;

                case CONTROL_TYPE_AUDIO_PLAYER:
                    AudioPlayer audioPlayer = new AudioPlayer(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), audioPlayer);

                    linearLayout.addView(audioPlayer.getAudioPlayerView());

                    if (loadControlObject.isInvisible()) {
                        audioPlayer.getAudioPlayerView().setVisibility(View.GONE);
                    } else {
                        audioPlayer.getAudioPlayerView().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_VIDEO_PLAYER:
                    VideoPlayer videoPlayer = new VideoPlayer(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), videoPlayer);

                    linearLayout.addView(videoPlayer.getVideoPlayerView());

                    if (loadControlObject.isInvisible()) {
                        videoPlayer.getVideoPlayerView().setVisibility(View.GONE);
                    } else {
                        videoPlayer.getVideoPlayerView().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    break;
                case CONTROL_TYPE_PERCENTAGE:
                    Percentage percentage = new Percentage(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), percentage);

                    linearLayout.addView(percentage.getPercentageView());


                    if (loadControlObject.isInvisible()) {
                        percentage.getPercentageView().setVisibility(View.GONE);
                    } else {
                        percentage.getPercentageView().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        ((MainActivity) context).hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_RADIO_BUTTON:
                    RadioGroupView radioGroup = new RadioGroupView(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), radioGroup);

                    linearLayout.addView(radioGroup.getRadioGroupView());


                    if (loadControlObject.isInvisible()) {
                        radioGroup.getRadioGroupView().setVisibility(View.GONE);
                    } else {
                        radioGroup.getRadioGroupView().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_DROP_DOWN:
                    DropDown dropDown = new DropDown(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), dropDown);

                    linearLayout.addView(dropDown.getDropdown());

                    if (loadControlObject.isInvisible()) {
                        dropDown.getDropdown().setVisibility(View.GONE);
                    } else {
                        dropDown.getDropdown().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isSectionUIFormNeeded() && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        if (linearLayout.getChildCount() == 1) {
                            linearLayout.getChildAt(0).setLayoutParams(params);
                        } else {
                            linearLayout.getChildAt(2).setLayoutParams(params);
                        }
                        dropDown.getLl_main_inside().setLayoutParams(params);
//                        controlAlignments(AppConstants.uiLayoutPropertiesStatic, dropDown.getLl_main_inside());
                    }
                    break;
                case CONTROL_TYPE_CHECK_LIST:
                    AppConstants.hasCheckList = true;
                    try {
                        JSONObject checkListJsonObject = new JSONObject();
                        checkListJsonObject.put("ColumnName", loadControlObject.getControlName());
                        checkListJsonObject.put("InsertType", loadControlObject.getRowSelectionType());
                        AppConstants.checkListData.put(checkListJsonObject);
                    } catch (Exception e) {
                    }
                    CheckList checkList = new CheckList(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), checkList);

                    linearLayout.addView(checkList.getCheckList());


                    if (loadControlObject.isInvisible()) {
                        checkList.getCheckList().setVisibility(View.GONE);
                    } else {
                        checkList.getCheckList().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    break;
                case CONTROL_TYPE_URL_LINK:
                    UrlView urlView = new UrlView(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), urlView);

                    linearLayout.addView(urlView.getURL());

                    if (loadControlObject.isInvisible()) {
                        urlView.getURL().setVisibility(View.GONE);
                    } else {
                        urlView.getURL().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    break;
                case CONTROL_TYPE_DECIMAL:
                    DecimalView decimalView = new DecimalView(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), decimalView);

                    linearLayout.addView(decimalView.getDecimal());


                    if (loadControlObject.isInvisible()) {
                        decimalView.getDecimal().setVisibility(View.GONE);
                    } else {
                        decimalView.getDecimal().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        ((MainActivity) context).hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_PASSWORD:
                    Password password = new Password(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), password);


                    linearLayout.addView(password.getPassword());


                    if (loadControlObject.isInvisible()) {
                        password.getPassword().setVisibility(View.GONE);
                    } else {
                        password.getPassword().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        ((MainActivity) context).hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_CURRENCY:
                    Currency currency = new Currency(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), currency);

                    linearLayout.addView(currency.getCurrency());


                    if (loadControlObject.isInvisible()) {
                        currency.getCurrency().setVisibility(View.GONE);
                    } else {
                        currency.getCurrency().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        ((MainActivity) context).hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_DYNAMIC_LABEL:
                    DynamicLabel dynamicLabel = new DynamicLabel(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), dynamicLabel);

                    linearLayout.addView(dynamicLabel.getDynamicLabel());

                    if (loadControlObject.isInvisible()) {
                        dynamicLabel.getDynamicLabel().setVisibility(View.GONE);
                    } else {
                        dynamicLabel.getDynamicLabel().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isSectionUIFormNeeded() && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        if (linearLayout.getChildCount() == 1) {
                            linearLayout.getChildAt(0).setLayoutParams(params);
                        } else {
                            linearLayout.getChildAt(2).setLayoutParams(params);
                        }

                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                        dynamicLabel.getLl_main_inside().setLayoutParams(params);
//                        controlAlignments(AppConstants.uiLayoutPropertiesStatic, dynamicLabel.getLl_main_inside());
                    }
                    if (loadControlObject.isOnClickEventExists()) {
                        Control_EventObject onclick_control_EventObject = loadControlObject.getOnClickEventObject();
                        ((MainActivity) context).hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onclick_control_EventObject);
                    }

                    break;
                case CONTROL_TYPE_IMAGE:
                    Image imageView = new Image(context, loadControlObject, false, 0, "", strAppName, controlUIProperties);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), imageView);

                    if (controlObject.isSectionUIFormNeeded() && linearLayout != null) {
                        Log.d(TAG, "loadControlDefaultImgCtrl: " + loadControlObject.getDisplayName());
                        Log.d(TAG, "loadControlDefaultImgCtrl1: " + loadControlObject.getDisplayName() + "-" + loadControlObject.getControlType());

                        if (loadControlObject.getDisplayNameAlignment() != null && !loadControlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                            android.view.ViewGroup.LayoutParams layoutParamsMainInside = imageView.getLLMainInside().getLayoutParams();
                            layoutParamsMainInside.width = -1;
                            layoutParamsMainInside.height = -1;
                            imageView.getLLMainInside().setLayoutParams(layoutParamsMainInside);
                            imageView.getLLMainInside().setBackgroundColor(ContextCompat.getColor(context, R.color.transparent_color));
                            Log.d(TAG, "loadControlDefaultImgCtrl2: " + loadControlObject.getDisplayName() + "-" + loadControlObject.getControlType());
                            if (loadControlObject.getDisplayNameAlignment() == null) {
                                imageView.getViewUI().setVisibility(View.INVISIBLE);
                                LinearLayout.LayoutParams layoutParamscard = (LinearLayout.LayoutParams)
                                        imageView.getCardView().getLayoutParams();
                                layoutParamscard.height = -1;
                                layoutParamscard.width = -1;
                                imageView.getCardView().setLayoutParams(layoutParamscard);
                                imageView.getCardView().setCardBackgroundColor(ContextCompat.getColor(context, R.color.transparent_color));

                                android.view.ViewGroup.LayoutParams layoutParamsMInSideCard = imageView.getLl_insideCard().getLayoutParams();
                                layoutParamsMInSideCard.width = -1;
                                layoutParamsMInSideCard.height = -1;
                                imageView.getLl_insideCard().setLayoutParams(layoutParamsMInSideCard);
                                imageView.getLl_insideCard().setBackgroundColor(ContextCompat.getColor(context, R.color.transparent_color));
                                Log.d(TAG, "loadControlDefaultImgCtrl3: " + loadControlObject.getDisplayName() + "-" + loadControlObject.getControlType());
                            }
                            boolean galleryView = false;
                            if (loadControlObject.getImagesArrangementType() != null && loadControlObject.getImagesArrangementType().equalsIgnoreCase("Gallery View")) {
                                galleryView = true;
                                ViewGroup.LayoutParams layoutParamsGallery = imageView.getGalleryImageLayout().getLayoutParams();
                                layoutParamsGallery.width = -1;
                                layoutParamsGallery.height = -1;
                                imageView.getGalleryImageLayout().setLayoutParams(layoutParamsGallery);
                                imageView.getGalleryImageLayout().setBackgroundColor(ContextCompat.getColor(context, R.color.transparent_color));
                                imageView.getGalleryViewImageView().setScaleType(ImageView.ScaleType.CENTER);
                                imageView.getGalleryViewImageView().setAdjustViewBounds(true);


                            } else {
                                android.view.ViewGroup.LayoutParams layoutParamsSingle = imageView.getLl_single_Image().getLayoutParams();
                                layoutParamsSingle.width = -1;
                                layoutParamsSingle.height = -1;
                                imageView.getLl_single_Image().setLayoutParams(layoutParamsSingle);
                                imageView.getLl_single_Image().setBackgroundColor(ContextCompat.getColor(context, R.color.transparent_color));
                                Log.d(TAG, "loadControlDefaultImgCtrl4: " + loadControlObject.getDisplayName() + "-" + loadControlObject.getControlType());
                            }
                            android.view.ViewGroup.LayoutParams layoutParamsImg;
                            if (galleryView) {
                                layoutParamsImg = imageView.getGalleryImageView().getLayoutParams();
                            } else {
                                layoutParamsImg = imageView.getMainImageView().getLayoutParams();
                                Log.d(TAG, "loadControlDefaultImgCtrl5: " + loadControlObject.getDisplayName() + "-" + loadControlObject.getControlType());
                            }

                            if (loadControlObject.getDisplayNameAlignment().equalsIgnoreCase("10")) {
                                layoutParamsImg.width = pxToDP(40);
                                layoutParamsImg.height = pxToDP(40);
                            }
//                            else {
//                                layoutParamsImg.width = -2;
//                                layoutParamsImg.height = -2;
//                            }
                            if (controlUIProperties != null && controlUIProperties.getTypeOfWidth() != null
                                    && !controlUIProperties.getTypeOfWidth().isEmpty() && controlUIProperties.getTypeOfWidth().equalsIgnoreCase(context.getString(R.string.Custom_Width))) {
                                layoutParamsImg.width = pxToDP(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                layoutParamsImg.height = pxToDP(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                            }
                            if (galleryView) {
                                imageView.getGalleryImageView().setLayoutParams(layoutParamsImg);
                            } else {
                                Log.d(TAG, "loadControlDefaultImgCtrl6: " + loadControlObject.getDisplayName() + "-" + loadControlObject.getControlType());
                                imageView.getMainImageView().setLayoutParams(layoutParamsImg);
                            }
//                            imageView.getMainImageView().setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            if (loadControlObject.isInvisible()) {
                                linearLayout.setVisibility(View.GONE);
                            }
                        } else {
                            imageView.getMainImageView().setScaleType(ImageView.ScaleType.CENTER);
                            Log.d(TAG, "loadControlDefaultImgCtrl7: " + loadControlObject.getDisplayName() + "-" + loadControlObject.getControlType());
                        }

                        linearLayout.addView(imageView.getImageView());

                        if (loadControlObject.isZoomImageEnable() && imageView.getMainImageView() != null) {
                            ((MainActivity) context).zoomImage(imageView.getMainImageView());
                        }
//                        controlAlignments(AppConstants.uiLayoutPropertiesStatic, imageView.getLLMainInside());
                    } else {
                        linearLayout.addView(imageView.getImageView());
                    }

                    if (loadControlObject.isInvisible()) {
                        imageView.getImageView().setVisibility(View.GONE);
                    } else {
                        imageView.getImageView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_BUTTON:
                    Button button = new Button(context, loadControlObject, false, 0, sectionName, strAppName);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), button);

                    if (controlObject.isSectionUIFormNeeded() && linearLayout != null) {
                        button.getButtonView().setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        button.getbtn_main().setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                        button.getll_button().setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        android.view.ViewGroup.LayoutParams layoutParamsll_button = button.getll_button().getLayoutParams();
                        layoutParamsll_button.width = -1;
                        layoutParamsll_button.height = -1;
                        if (controlUIProperties != null && controlUIProperties.getTypeOfWidth() != null
                                && !controlUIProperties.getTypeOfWidth().isEmpty() && controlUIProperties.getTypeOfWidth().equalsIgnoreCase(context.getString(R.string.Custom_Width))) {
                            layoutParamsll_button.width = pxToDP(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                            layoutParamsll_button.height = pxToDP(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                        }
                        linearLayout.addView(button.getButton());
                        button.getButton().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT));
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    } else {
                        linearLayout.addView(button.getButton());
                    }
                    if (loadControlObject.isInvisible()) {
                        button.getButton().setVisibility(View.GONE);
                    } else {
                        button.getButton().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnClickEventExists()) {
                        Control_EventObject onclick_control_EventObject = loadControlObject.getOnClickEventObject();
                        ((MainActivity) context).hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onclick_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_AUTO_NUMBER:
                    AutoNumber autoNumber = new AutoNumber(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), autoNumber);
                    linearLayout.addView(autoNumber.getAutoNumber());


                    if (loadControlObject.isInvisible()) {
                        autoNumber.getAutoNumber().setVisibility(View.GONE);
                    } else {
                        autoNumber.getAutoNumber().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    break;
                case CONTROL_TYPE_RATING:
                    Rating rating = new Rating(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), rating);

                    linearLayout.addView(rating.getRatingView());


                    if (loadControlObject.isInvisible()) {
                        rating.getRatingView().setVisibility(View.GONE);
                    } else {
                        rating.getRatingView().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_GPS:
                    Gps_Control controlGPS = new Gps_Control(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), controlGPS);

                    linearLayout.addView(controlGPS.getControlGPSView());


                    if (loadControlObject.isInvisible()) {
                        controlGPS.getControlGPSView().setVisibility(View.GONE);
                    } else {
                        controlGPS.getControlGPSView().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    break;

                case CONTROL_TYPE_BAR_CODE:
                    BarCode barcode = new BarCode(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), barcode);

                    linearLayout.addView(barcode.getBarCode());

                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_QR_CODE:
                    QRCode qrCode = new QRCode(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), qrCode);

                    linearLayout.addView(qrCode.getQRCode());

                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isInvisible()) {
                        qrCode.getQRCode().setVisibility(View.GONE);
                    } else {
                        qrCode.getQRCode().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;

                case CONTROL_TYPE_DATA_CONTROL:
                    loadItems(loadControlObject);
                    DataControl dataControl = new DataControl(context, loadControlObject, strUserLocationStructure, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), dataControl);

                    linearLayout.addView(dataControl.getDataControl());

                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    break;

                case CONTROL_TYPE_MAP:
                    MapControl mapControl = new MapControl(context, loadControlObject, false, 0, "", controlObject.isSectionUIFormNeeded());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), mapControl);


                    if (controlObject.isSectionUIFormNeeded() && linearLayout != null) {

                        mapControl.ll_mapView_main().setVisibility(View.VISIBLE);
                        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        ViewGroup.LayoutParams paramsMap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        mapControl.ll_mapView_main().setLayoutParams(params);
//                        if(isScreenFit){
//                            int height=0;
////                            ViewGroup.LayoutParams param2s = new LinearLayout.LayoutParams(-1, (uiLayoutProperties.getHeight()*screenHeight/100));
//                            if (uiLayoutProperties!=null && uiLayoutProperties.getHeight() != 0) {
//                                Log.d(TAG, "layoutheight_inkaemaina: "+(uiLayoutProperties.getHeight()*screenHeight/100));
//                                 height = (uiLayoutProperties.getHeight()*screenHeight/100)-pxToDP(14);
//                            }
//
//                            ViewGroup.LayoutParams param2s = new LinearLayout.LayoutParams(-1, -2);
//                           linearLayout.setLayoutParams(param2s);
//                            ViewGroup.LayoutParams param1s = new LinearLayout.LayoutParams(-1, -1);
//                            mapControl.getMapView().setLayoutParams(param1s);
//                        }
                        mapControl.getMapView().setLayoutParams(paramsMap);

                        linearLayout.addView(mapControl.getMapControlLayout());

                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }

                    } else {
                        mapControl.ll_mapView_main().setVisibility(View.VISIBLE);
                        linearLayout.addView(mapControl.getMapControlLayout());
                    }
                 /*   mapControl.ll_mapView_main().setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    ViewGroup.LayoutParams paramsMap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    mapControl.ll_mapView_main().setLayoutParams(params);
                    mapControl.getMapView().setLayoutParams(paramsMap);

                    linearLayout.addView(mapControl.getMapControlLayout());*/

                    if (loadControlObject.isInvisible()) {
                        mapControl.getMapControlLayout().setVisibility(View.GONE);
                    } else {
                        mapControl.getMapControlLayout().setVisibility(View.VISIBLE);
                    }

                    if (loadControlObject.isOnClickEventExists()) {
                        Control_EventObject onclick_control_EventObject = loadControlObject.getOnClickEventObject();
                        ((MainActivity) context).hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onclick_control_EventObject);
                    }
                    if (loadControlObject.isOnMapMarkerClickEventExists()) {
                        Control_EventObject onMarkerClick_control_EventObject = loadControlObject.getOnMapMarkerClickEventObject();
                        ((MainActivity) context).hash_OnMarkerclick_EventObjects.put(loadControlObject.getControlName(), onMarkerClick_control_EventObject);
                    }


                    mapControl.getMapView().onResume();


                    break;

                case CONTROL_TYPE_CALENDAR_EVENT:
                    CalendarEventControl calendarEventControl = new CalendarEventControl(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), calendarEventControl);


                    linearLayout.addView(calendarEventControl.getCalendarEventLayout());
                    if (loadControlObject.isInvisible()) {
                        calendarEventControl.getCalendarEventLayout().setVisibility(View.GONE);
                    } else {
                        calendarEventControl.getCalendarEventLayout().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnClickEventExists()) {
                        Control_EventObject onclick_control_EventObject = loadControlObject.getOnClickEventObject();
                        ((MainActivity) context).hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onclick_control_EventObject);
                    }

                    break;
                case AppConstants.CONTROL_TYPE_DATA_VIEWER:
                    ScrollView scrollViewDataViewerControl = new ScrollView(context);
                    DataViewer DataViewer = new DataViewer(context, loadControlObject, linearLayout, dataCollectionObject, scrollViewDataViewerControl);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), DataViewer);
                    /*if (dataCollectionObject.isUIFormNeeded() && linearLayout != null) {
                        scrollViewDataViewerControl.addView(DataViewer.getDataViewer());
                        linearLayout.addView(scrollViewDataViewerControl);
//                        bothWrapContentAndDp(loadControlObject.getControlType(), loadControlObject.getControlName());
//                        linearLayout.addView(DataViewer.getDataViewer());
                    } else if (section == 0) {
                        linearLayout.addView(DataViewer.getDataViewer());
                    } else {
                        linearLayoutSection.addView(DataViewer.getDataViewer());
                    }*/
                    linearLayout.addView(DataViewer.getDataViewer());
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    DataViewer.setCustomClickListener((MainActivity) context);
                    if (loadControlObject.isOnClickEventExists()) {
                        Control_EventObject onclick_control_EventObject = loadControlObject.getOnClickEventObject();
                        ((MainActivity) context).hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onclick_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_LiveTracking:
                    LiveTracking ControlLiveTracking = new LiveTracking(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), ControlLiveTracking);

                    linearLayout.addView(ControlLiveTracking.getMapControlLayout());


                    if (loadControlObject.isInvisible()) {
                        ControlLiveTracking.getMapControlLayout().setVisibility(View.GONE);
                    } else {
                        ControlLiveTracking.getMapControlLayout().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    ControlLiveTracking.getMapView().onResume();
                    break;
                case CONTROL_TYPE_USER:

                    UserControl userControl = new UserControl(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), userControl);


                    linearLayout.addView(userControl.getUserControlView());


                    if (loadControlObject.isInvisible()) {
                        userControl.getUserControlView().setVisibility(View.GONE);
                    } else {
                        userControl.getUserControlView().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    break;
                case CONTROL_TYPE_POST:

                    PostControl postControl = new PostControl(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), postControl);

                    linearLayout.addView(postControl.getPostControlView());


                    if (loadControlObject.isInvisible()) {
                        postControl.getPostControlView().setVisibility(View.GONE);
                    } else {
                        postControl.getPostControlView().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    break;
                case CONTROL_TYPE_CHART:
                    ChartControl chartControl = new ChartControl(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), chartControl);

                    linearLayout.addView(chartControl.getChartLayout());


                    if (loadControlObject.isInvisible()) {
                        chartControl.getChartLayout().setVisibility(View.GONE);
                    } else {
                        chartControl.getChartLayout().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    break;

                case CONTROL_TYPE_PROGRESS:
                    ProgressControl progressControl = new ProgressControl(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), progressControl);

                    linearLayout.addView(progressControl.getProgressLayout());


                    if (loadControlObject.isInvisible()) {
                        progressControl.getProgressLayout().setVisibility(View.GONE);
                    } else {
                        progressControl.getProgressLayout().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }

                    if (loadControlObject.isOnClickEventExists()) {
                        Control_EventObject onclick_control_EventObject = loadControlObject.getOnClickEventObject();
                        ((MainActivity) context).hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onclick_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_VIEWFILE:
                    ViewFileControl viewFileControl = new ViewFileControl(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), viewFileControl);

                    if (controlObject.isSectionUIFormNeeded() && linearLayout != null) {

                        viewFileControl.getViewFileLayout().setVisibility(View.VISIBLE);
                        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        ViewGroup.LayoutParams paramsMap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        viewFileControl.getViewFileMainLayout().setLayoutParams(params);
                        viewFileControl.getViewFileLayout().setLayoutParams(paramsMap);
                        ViewGroup.LayoutParams paramsFrame = viewFileControl.getViewFileFrameLayout().getLayoutParams();
                        paramsFrame.width = -1;
                        paramsFrame.height = -1;
                        viewFileControl.getViewFileFrameLayout().setLayoutParams(paramsFrame);
                        ViewGroup.LayoutParams paramsWV = viewFileControl.getViewFileWebView().getLayoutParams();
                        paramsWV.width = -1;
                        paramsWV.height = -1;
                        viewFileControl.getViewFileWebView().setLayoutParams(paramsWV);

                        linearLayout.addView(viewFileControl.getViewFileLayout());

                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }

                    } else {
                        viewFileControl.getViewFileLayout().setVisibility(View.VISIBLE);
                        linearLayout.addView(viewFileControl.getViewFileLayout());
                    }


                    if (loadControlObject.isInvisible()) {
                        viewFileControl.getViewFileLayout().setVisibility(View.GONE);
                    } else {
                        viewFileControl.getViewFileLayout().setVisibility(View.VISIBLE);
                    }


                    break;
                case CONTROL_TYPE_COUNT_UP_TIMER:
                    CountUpTimerControl countUpTimerControl = new CountUpTimerControl(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), countUpTimerControl);

                    linearLayout.addView(countUpTimerControl.getCountUpTimerLayout());


                    if (loadControlObject.isInvisible()) {
                        countUpTimerControl.getCountUpTimerLayout().setVisibility(View.GONE);
                    } else {
                        countUpTimerControl.getCountUpTimerLayout().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }

                    if (loadControlObject.isOnClickEventExists()) {
                        Control_EventObject onclick_control_EventObject = loadControlObject.getOnClickEventObject();
                        ((MainActivity) context).hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onclick_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_COUNT_DOWN_TIMER:
                    CountDownTimerControl countDownTimerControl = new CountDownTimerControl(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), countDownTimerControl);

                    linearLayout.addView(countDownTimerControl.getCountDownTimerLayout());


                    if (loadControlObject.isInvisible()) {
                        countDownTimerControl.getCountDownTimerLayout().setVisibility(View.GONE);
                    } else {
                        countDownTimerControl.getCountDownTimerLayout().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnClickEventExists()) {
                        Control_EventObject onclick_control_EventObject = loadControlObject.getOnClickEventObject();
                        ((MainActivity) context).hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onclick_control_EventObject);
                    }
                    break;


                case CONTROL_TYPE_AUTO_GENERATION:

                    MainActivity.isAutoNumbersAvaliable = true;

                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("ControlName", loadControlObject.getControlName());
                        jsonObject.put("Prefix", loadControlObject.getPreFixValue());
                        jsonObject.put("Suffix", loadControlObject.getSuffixValue());
                        if (loadControlObject.getSuffix1Value() != null && !loadControlObject.getSuffix1Value().contentEquals("")) {
                            jsonObject.put("Suffix1", loadControlObject.getSuffix1Value());
                        }
                        MainActivity.jArrayAutoIncementControls.put(jsonObject);
                    } catch (JSONException e) {
//                    e.printStackTrace();
                        Log.d(TAG, "loadControlAutoGenException: " + e);
                    }
                    LinearLayout linearLayoutANG = new LinearLayout(context);
                    linearLayoutANG.setVisibility(View.GONE);
                    linearLayoutANG.setTag(loadControlObject.getControlName());
                    linearLayout.addView(linearLayoutANG);
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    break;
                case CONTROL_TYPE_DATA_TABLE:
                    DataTableControl dataTableControl = new DataTableControl(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), dataTableControl);

                    linearLayout.addView(dataTableControl.getDataTableLayout());


                    if (loadControlObject.isInvisible()) {
                        dataTableControl.getDataTableLayout().setVisibility(View.GONE);
                    } else {
                        dataTableControl.getDataTableLayout().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnClickEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnClickEventObject();
                        ((MainActivity) context).hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnClickEventExists()) {
                        Control_EventObject onclick_control_EventObject = loadControlObject.getOnClickEventObject();
                        ((MainActivity) context).hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onclick_control_EventObject);
                    }

                    break;


                case CONTROL_TYPE_TIME:

                    Time timeControl = new Time(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), timeControl);

                    linearLayout.addView(timeControl.getTimeControlLayout());


                    if (loadControlObject.isInvisible()) {
                        timeControl.getTimeControlLayout().setVisibility(View.GONE);
                    } else {
                        timeControl.getTimeControlLayout().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    break;
                case CONTROL_TYPE_AUTO_COMPLETION:

                    AutoCompletionControl autoCompletionControla = new AutoCompletionControl(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), autoCompletionControla);

                    linearLayout.addView(autoCompletionControla.getAutoCompletionControlView());


                    if (loadControlObject.isInvisible()) {
                        autoCompletionControla.getAutoCompletionControlView().setVisibility(View.GONE);
                    } else {
                        autoCompletionControla.getAutoCompletionControlView().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isSectionUIFormNeeded()) {
                        if (loadControlObject.isInvisible()) {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnFocusEventObject();
                        ((MainActivity) context).hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        ((MainActivity) context).hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_SUBFORM:
                    ScrollView scrollViewSubForm = new ScrollView(context);
                    SubformView subformView = new SubformView(context, loadControlObject, strUserLocationStructure,
                            List_ControlClassObjects, false, strAppName, linearLayout, globalControlObjects,
                            controlObject.isSectionUIFormNeeded(), scrollViewSubForm, uiLayoutProperties, layoutHeight, app_edit,
                            dataCollectionObject.getDataManagementOptions().getEditColumns(), activityView,controlUIProperties,
                            null,visibilityManagementOptions);
                    subFormTableSettingsTypeMap.put(loadControlObject.getControlName(), loadControlObject.getTableSettingsType());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), subformView);
                    if (controlObject.isSectionUIFormNeeded() && linearLayout != null) {
                        scrollViewSubForm.addView(subformView.getSubFormView());
                        linearLayout.addView(scrollViewSubForm);
//                        linearLayout.addView(subformView.getSubFormView());
                    } else {
                        linearLayout.addView(subformView.getSubFormView());
                    }
//                    subformView.setCustomClickListener(this);
//
                    subformView.setTag(String.valueOf(subFormPos));
                    List<String> subControlNames = new ArrayList<>();
                    for (int i = 0; i < loadControlObject.getSubFormControlList().size(); i++) {
                        subControlNames.add(loadControlObject.getSubFormControlList().get(i).getControlName());
                    }

                    subFormsAutoNumberStatusMap.put(loadControlObject.getControlName(), subformView.autoNumbersAvailable());
                    subFormsAutoNumberArraysMap.put(loadControlObject.getControlName(), subformView.autoNumbersArray());

                    subFormPos++;

//                    bothWrapContentAndDp(controlType, controlObject.getControlName(), scrollViewSubForm);

                    if (loadControlObject.isOnAddRowEventExists()) {
                        Control_EventObject onAddRow_control_EventObject = loadControlObject.getOnAddRowEventObject();
                        ((MainActivity) context).hash_OnAddRow_EventObjects.put(loadControlObject.getControlName(), onAddRow_control_EventObject);
                    }
                    if (loadControlObject.isOnDeleteRowEventExists()) {
                        Control_EventObject onDeleteRow_control_EventObject = loadControlObject.getOnDeleteRowEventObject();
                        ((MainActivity) context).hash_OnDeleteRow_EventObjects.put(loadControlObject.getControlName(), onDeleteRow_control_EventObject);
                    }
                    if (controlObject.isSectionUIFormNeeded() && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject,AppConstants.uiLayoutPropertiesStatic,controlUIProperties,List_ControlClassObjects,context);
                        controlUiSettings.setControlUiSettings();

                    }
                    break;
                case CONTROL_TYPE_GRID_CONTROL:

                    ScrollView scrollViewGridControl = new ScrollView(context);
                    GridControl gridView = new GridControl(context, loadControlObject,
                            strUserLocationStructure, List_ControlClassObjects, true, strAppName,
                            linearLayout, globalControlObjects, dataCollectionObject.isUIFormNeeded(),
                            scrollViewGridControl, uiLayoutProperties, controlUIProperties, 0,
                            app_edit, dataCollectionObject.getDataManagementOptions().getEditColumns(),
                            activityView,null,visibilityManagementOptions);
                    subFormTableSettingsTypeMap.put(loadControlObject.getControlName(), loadControlObject.getTableSettingsType());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), gridView);

                    if (dataCollectionObject.isUIFormNeeded() && linearLayout != null) {
                        scrollViewGridControl.addView(gridView.getSubFormView());
                        linearLayout.addView(scrollViewGridControl);
                    } else {
                        linearLayout.addView(gridView.getSubFormView());
                    }
//                    gridView.setCustomClickListener(this);
                    gridView.btn_addRow().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (loadControlObject.isOnAddRowEventExists() && !AppConstants.Initialize_Flag) {
                                AppConstants.EventFrom_subformOrNot = false;
                                AppConstants.SF_Container_position = gridView.getTableLayoutview().getChildCount();
                                if (AppConstants.EventCallsFrom == 1) {
                                    if (AppConstants.GlobalObjects != null) {
                                        AppConstants.GlobalObjects.setCurrent_GPS("");
                                    }
                                    if (loadControlObject.isValidateFormFields()) {
                                        v.setTag(R.string.form_validated, true);
                                    } else {
                                        v.setTag(R.string.form_validated, false);
                                    }
                                    ((MainActivity) context).addGridRowEvent(v, loadControlObject.getControlName());
                                }
                            } else {
                                gridView.addRowClick(gridView.getll_grid_view());
//                                bothWrapContentAndDp(controlType, controlObject.getControlName(), scrollViewGridControl);
                            }

                        }
                    });
                    gridView.deleteRow().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (loadControlObject.isOnDeleteRowEventExists() && !AppConstants.Initialize_Flag) {

                                if (AppConstants.EventCallsFrom == 1) {
                                    if (AppConstants.GlobalObjects != null) {
                                        AppConstants.GlobalObjects.setCurrent_GPS("");
                                    }
                                    if (loadControlObject.isValidateFormFields()) {
                                        v.setTag(R.string.form_validated, true);
                                    } else {
                                        v.setTag(R.string.form_validated, false);
                                    }
                                    ((MainActivity) context).deleteGridRowEvent(v, loadControlObject.getControlName());
                                }
                            } else {
                                gridView.deleteRowClick(gridView.getll_grid_view());
//                                bothWrapContentAndDp(controlType, controlObject.getControlName(), scrollViewGridControl);
                            }

                        }
                    });
                    gridView.setTag(String.valueOf(subFormPos));
                    subFormsAutoNumberStatusMap.put(loadControlObject.getControlName(), gridView.autoNumbersAvailable());
                    subFormsAutoNumberArraysMap.put(loadControlObject.getControlName(), gridView.autoNumbersArray());
                    subFormPos++;

                    if (loadControlObject.isOnAddRowEventExists()) {
                        Control_EventObject onAddRow_control_EventObject = loadControlObject.getOnAddRowEventObject();
                        ((MainActivity) context).hash_OnAddRow_EventObjects.put(loadControlObject.getControlName(), onAddRow_control_EventObject);
                    }
                    if (loadControlObject.isOnDeleteRowEventExists()) {
                        Control_EventObject onDeleteRow_control_EventObject = loadControlObject.getOnDeleteRowEventObject();
                        ((MainActivity) context).hash_OnDeleteRow_EventObjects.put(loadControlObject.getControlName(), onDeleteRow_control_EventObject);
                    }
                    if (dataCollectionObject.isUIFormNeeded() && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        // ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject,AppConstants.uiLayoutPropertiesStatic,controlUIProperties,List_ControlClassObjects,context);
                        // controlUiSettings.setControlUiSettings();
                    }

//                    bothWrapContentAndDp(controlType, controlObject.getControlName(), scrollViewGridControl);

                    break;

            }
            if(controlObject.isMakeItAsPopup()){
                linearLayout.removeViewAt(linearLayout.getChildCount()-1);
                ll_main_inside.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
            ImproveHelper.improveException(context, TAG, "loadControl", e);
        }
    }

    public void showFileChooser() {


        Intent intent;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            intent = new Intent(ACTION_PICK, EXTERNAL_CONTENT_URI);
        } else {
            intent = new Intent(ACTION_PICK, INTERNAL_CONTENT_URI);
        }

        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra("return-data", true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        context.startActivityForResult(Intent.createChooser(intent, "Choose a file"), SECTION_FILE_BROWSER_RESULT_CODE);


    }

    public LinearLayout getSectionLayout() {
        return linearLayout;
    }


    private void loadItems(ControlObject controlObject) {
        try {
            String textFile = "DC_" + controlObject.getDataControlName() + ".txt";

            String line = ImproveHelper.readTextFileFromSD(context, textFile, sessionManager.getOrgIdFromSession());

            if (!line.equals("")) {

                try {
                    JSONObject jobj_textfile = new JSONObject(line);
                    String ControlName = jobj_textfile.getString("ControlName");
                    String KeyID = jobj_textfile.getString("KeyID");
                    String KeyName = jobj_textfile.getString("KeyName");
                    String keyName1 = jobj_textfile.getString("KeyName");
                    JSONArray jarr_Data = jobj_textfile.getJSONArray("Data");
                    List<New_DataControl_Bean> dataControlBeanList = new ArrayList<>();
                    if (!ImproveHelper.getLocale(context).equalsIgnoreCase("en")) {
                        KeyName = KeyName + "_" + ImproveHelper.getLocale(context);
                    }
                    for (int i = 0; i < jarr_Data.length(); i++) {

                        New_DataControl_Bean dataControl_bean = new New_DataControl_Bean();
                        dataControl_bean.setDc_id(jarr_Data.getJSONObject(i).getString(KeyID));
                        dataControl_bean.setDc_value(jarr_Data.getJSONObject(i).getString(KeyName));
                        dataControl_bean.setDc_name(ControlName);
                        dataControl_bean.setDc_KeyId(KeyID);
                        dataControl_bean.setDc_KeyName(KeyName);

                        if (!controlObject.getDependentControl().isEmpty()) {
                            LinkedHashMap<String, String> depen_hash = new LinkedHashMap<String, String>();
                            Iterator<String> dataobj_keys = jarr_Data.getJSONObject(i).keys();

                            while (dataobj_keys.hasNext()) {
                                String tempkey = dataobj_keys.next();
                                if (!KeyID.equalsIgnoreCase(tempkey) && !keyName1.equalsIgnoreCase(tempkey)) {
                                    if (!tempkey.startsWith(keyName1 + "_")) {
                                        depen_hash.put(tempkey, jarr_Data.getJSONObject(i).getString(tempkey));
                                    }
                                }
                            }

                            dataControl_bean.setDc_DependencyValues(depen_hash);

                            dataControl_bean.setDc_dependency(controlObject.getDependentControl());

                        }
                        dataControlBeanList.add(dataControl_bean);
                    }
                    Gson gson = new Gson();
                    String jsonDataControlItems2 = gson.toJson(dataControlBeanList);
                    PrefManger.putSharedPreferencesString(context, controlObject.getControlName(), jsonDataControlItems2);

                } catch (JSONException E) {
                    System.out.println("Error==" + E);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "loadItems", e);
        }
    }

    private void primaryLayoutData(UIPrimaryLayoutModelClass uiPrimaryLayoutModelClass, LinearLayout linearLayout) {

        Log.d(TAG, "SectionControlOne: " + "Three");
        if (uiPrimaryLayoutModelClass != null) {
            setScreenType(uiPrimaryLayoutModelClass);
            /*if (isScreenFit) {


//                        int navH = getNavigationBarHeight();
                int navH = navigationBarHeight();
                int staH = getStatusBarHeight();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (isEdgeToEdgeEnabled(context) == 0) {
                        // 99% sure there's a navigation bar

//                        screenHeight = screenHeight - (toolbar.getHeight() + navH + staH);// Sanjay
                        screenHeight = screenHeight - (pxToDP(56) + navH + staH);// Note

                    } else { //
                        // no navigation bar, unless it is enabled in the settings
//                        screenHeight = screenHeight - (toolbar.getHeight() + navH + staH);
                        screenHeight = screenHeight - (pxToDP(56) + navH + staH); // Noted
                        Log.d(TAG, "primaryLayoutDataCT: " + "NormalToolbar No  Buttons " + navH + " " + navH + " " + staH);
//                                ImproveHelper.showToastAlert(context,"IsEdgetoEgde Defatult toolbar "+isEdgeToEdgeEnabled(context));
                    }

                } else {
                    screenHeight = screenHeight - (toolBarheight + staH);
                }

            }*/
            UILayoutProperties primaryLayoutProperties = uiPrimaryLayoutModelClass.getPrimaryLayoutProperties();

            if (primaryLayoutProperties != null) {

                if (primaryLayoutProperties.getInsideAlignment() != null && !primaryLayoutProperties.getInsideAlignment().isEmpty()) {
                    if (primaryLayoutProperties.getInsideAlignment().equalsIgnoreCase(context.getString(R.string.top))) {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        linearLayout.setGravity(Gravity.TOP);
                        linearLayout.setLayoutParams(layoutParams);
                    } else if (primaryLayoutProperties.getInsideAlignment().equalsIgnoreCase(context.getString(R.string.bottom))) {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        linearLayout.setGravity(Gravity.BOTTOM);
                        linearLayout.setLayoutParams(layoutParams);
                    } else if (primaryLayoutProperties.getInsideAlignment().equalsIgnoreCase(context.getString(R.string.middle))) {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        linearLayout.setGravity(Gravity.CENTER);
                        linearLayout.setLayoutParams(layoutParams);
                    }
                } else {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    linearLayout.setGravity(Gravity.CENTER);
                    linearLayout.setLayoutParams(layoutParams);
                }


                GradientDrawable.Orientation gradientAlignment = null;
                setMarginToLinearLayout(linearLayout, pxToDP(primaryLayoutProperties.getMarginLeft()), pxToDP(primaryLayoutProperties.getMarginTop()),
                        pxToDP(primaryLayoutProperties.getMarginRight()), pxToDP(primaryLayoutProperties.getMarginBottom()), -1, -1);

                linearLayout.setPadding(pxToDP(primaryLayoutProperties.getPaddingLeft()), pxToDP(primaryLayoutProperties.getPaddingTop()),
                        pxToDP(primaryLayoutProperties.getPaddingRight()), pxToDP(primaryLayoutProperties.getPaddingBottom()));

                if (primaryLayoutProperties.getBackgroundType() != null && !primaryLayoutProperties.getBackgroundType().isEmpty()) {

                    if (primaryLayoutProperties.getBackgroundType().equalsIgnoreCase(context.getString(R.string.image))) {
                        if (primaryLayoutProperties.getBackGroundImage().contains("http")) {
                            imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
                            imageLoader.loadImage(primaryLayoutProperties.getBackGroundImage(), new SimpleImageLoadingListener() {
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    linearLayout.setBackground(new BitmapDrawable(context.getResources(), loadedImage));
                                }
                            });
                        } else {
                            File imgFile = new File(primaryLayoutProperties.getBackGroundImage());
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            linearLayout.setBackground(new BitmapDrawable(context.getResources(), myBitmap));
                        }
                    } else if (primaryLayoutProperties.getBackgroundType() != null && primaryLayoutProperties.getBackgroundType().equalsIgnoreCase(context.getString(R.string.color))) {

                        if (primaryLayoutProperties.getColorType() != null && primaryLayoutProperties.getColorType().equalsIgnoreCase(context.getString(R.string.transparent))) {

                            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.transparent_color));
                        } else if (primaryLayoutProperties.getColorType() != null && primaryLayoutProperties.getColorType().equalsIgnoreCase(context.getString(R.string.plain))) {
                            if (primaryLayoutProperties.getBackGroundColorHex() != null) {
                                linearLayout.setBackgroundColor(Color.parseColor(primaryLayoutProperties.getBackGroundColorHex()));
                            }
                        } else if (primaryLayoutProperties.getColorType() != null && primaryLayoutProperties.getColorType().equalsIgnoreCase(context.getString(R.string.gradient))) {
                            if (primaryLayoutProperties.getGradientType() != null && !primaryLayoutProperties.getGradientType().isEmpty()) {

                                if (primaryLayoutProperties.getGradientType().equalsIgnoreCase(context.getString(R.string.top_to_bottom))) {
                                    gradientAlignment = GradientDrawable.Orientation.TOP_BOTTOM;
                                } else if (primaryLayoutProperties.getGradientType().equalsIgnoreCase(context.getString(R.string.bottom_to_top))) {
                                    gradientAlignment = GradientDrawable.Orientation.BOTTOM_TOP;
                                } else if (primaryLayoutProperties.getGradientType().equalsIgnoreCase(context.getString(R.string.left_to_right))) {
                                    gradientAlignment = GradientDrawable.Orientation.LEFT_RIGHT;
                                } else if (primaryLayoutProperties.getGradientType().equalsIgnoreCase(context.getString(R.string.right_to_left))) {
                                    gradientAlignment = GradientDrawable.Orientation.RIGHT_LEFT;
                                }
                            }
                            String gradientOneColorHex = "#FFFFFF";
                            if (primaryLayoutProperties.getGradientOneColorHex() != null
                                    && !primaryLayoutProperties.getGradientOneColorHex().isEmpty()) {

                                gradientOneColorHex = primaryLayoutProperties.getGradientOneColorHex();

                            }
                            String gradientTwoColorHex = "#FFFFFF";
                            if (primaryLayoutProperties.getGradientTwoColorHex() != null
                                    && !primaryLayoutProperties.getGradientTwoColorHex().isEmpty()) {

                                gradientTwoColorHex = primaryLayoutProperties.getGradientTwoColorHex();

                            }

                            String borderColorHex = "#FFFFFF";
                            if (primaryLayoutProperties.getBorderColorHex() != null && !primaryLayoutProperties.getBorderColorHex().isEmpty()) {
                                borderColorHex = primaryLayoutProperties.getBorderColorHex();
                            }
                            int borderRadiusValue = 0;
                            if (primaryLayoutProperties.getBorderRadius() != null && !primaryLayoutProperties.getBorderRadius().isEmpty()) {
                                String borderRadius = primaryLayoutProperties.getBorderRadius();
                                borderRadiusValue = pxToDP(Integer.valueOf(borderRadius));

                            }
                            int borderStrokeValue = 0;
                            if (primaryLayoutProperties.getBorderStroke() != null && !primaryLayoutProperties.getBorderStroke().isEmpty()) {
                                String borderStroke = primaryLayoutProperties.getBorderStroke();
                                borderStrokeValue = pxToDP(Integer.valueOf(borderStroke));
                            }

                            GradientDrawable gd = new GradientDrawable(
                                    gradientAlignment,
                                    new int[]{Color.parseColor(gradientOneColorHex), Color.parseColor(gradientTwoColorHex)});
                            gd.setCornerRadius(borderRadiusValue);
                            gd.setStroke(borderStrokeValue, Color.parseColor(borderColorHex));
                            linearLayout.setBackground(gd);

                        }
                    }
                }
                // Sanjay
                List<LayoutProperties> layoutPropertiesList = uiPrimaryLayoutModelClass.getLayoutPropertiesList();

                if (layoutPropertiesList != null && layoutPropertiesList.size() > 0) {

                    //Inflate LayoutViews
                    for (int i = 0; i < layoutPropertiesList.size(); i++) {
                        //Inflate LayoutViews
                        UILayoutProperties uiLayoutProperties = layoutPropertiesList.get(i).getUiLayoutProperties();
                        MappingControlModel mappingControlLayout = layoutPropertiesList.get(i).getLayoutControl();
                        ControlUIProperties controlUIProperties = mappingControlLayout.getControlUIProperties();
                        addLayoutToPrimary(linearLayout, layoutPropertiesList.get(i), uiLayoutProperties, mappingControlLayout, controlUIProperties);
                    }

                    if (((MainActivity) context).dataCollectionObject.getOnLoadEventObject() != null) {
                        if (AppConstants.GlobalObjects != null) {
                            AppConstants.GlobalObjects.setCurrent_GPS("");
                        }

                        FormLoad();
                    } else {
//                        dismissProgressDialog();
                    }
                } else {
//                    dismissProgressDialog();

                }
            }
        }
    }

    public void addLayoutToPrimary(LinearLayout llMainContainer, LayoutProperties layoutProperties, UILayoutProperties uiLayoutProperties, com.bhargo.user.uisettings.pojos.MappingControlModel mappingControlModel, ControlUIProperties controlUIProperties) {
        Log.d(TAG, "SectionControlOne: " + "Four");
        final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rView = lInflater.inflate(R.layout.add_layout, null);
        LinearLayout layout_addMain = rView.findViewById(R.id.layout_addMain);
        HorizontalScrollView hsOverFlow = rView.findViewById(R.id.hsOverFlow);
//        CustomTextView tv_subLayoutId = rView.findViewById(R.id.tv_subLayoutId);
//        tv_subLayoutId.setText(uiLayoutProperties.getAliasName());
        layout_addMain.setTag(layoutProperties.getPosition());
        llMainContainer.addView(rView);

        int viewW = LinearLayout.LayoutParams.MATCH_PARENT;
        if (uiLayoutProperties.getWidthFixedVariable() != null && uiLayoutProperties.getWidthFixedVariable().equalsIgnoreCase(context.getString(R.string.fixed_Width))) {
//            viewW = screenWidth;
            viewW = -1;//Note
        }
//        int viewW = screenWidth;
        int viewH = 0;

        if (isScreenFit) {
            if (layoutProperties.isLayoutToolBar()) {
                viewH = improveHelper.dpToPx(56);
            } else {
//                viewH = (uiLayoutProperties.getHeight() * screenHeight) / 100; //Note
                viewH = ((uiLayoutProperties.getHeight() * screenHeight) / 100); //Note
            }
//            viewH = (uiLayoutProperties.getHeight() * screenHeight) / 100;
            Log.d(TAG, "addLayoutToPrimaryViewWSF " + viewW);
            Log.d(TAG, "addLayoutToPrimaryViewHSF " + viewH);

        } else {

            if (uiLayoutProperties.getWrap_or_dp() != null && !uiLayoutProperties.getWrap_or_dp().isEmpty()) {
                Log.d(TAG, "LayoutWrapOrDp: " + uiLayoutProperties.getWrap_or_dp());
                if (uiLayoutProperties.getWrap_or_dp().equalsIgnoreCase(context.getString(R.string.both_wrap_dp))) {
//                    viewH = LinearLayout.LayoutParams.WRAP_CONTENT;
//                    MappingControlModel mappingControlModelLayout = layoutProperties.getLayoutControl();
//                    bothWrapContentAndDp(mappingControlModel.getControlType(), mappingControlModel.getControlName());
                } else if (uiLayoutProperties.getWrap_or_dp().equalsIgnoreCase(context.getString(R.string.only_wrap))) {
                    viewH = LinearLayout.LayoutParams.WRAP_CONTENT;
                } else if (uiLayoutProperties.getWrap_or_dp().equalsIgnoreCase(context.getString(R.string.only_dp))) {
                    viewH = pxToDP(uiLayoutProperties.getHeight());
                } else {
                    viewH = LinearLayout.LayoutParams.WRAP_CONTENT;
                }
            } else {
                viewH = LinearLayout.LayoutParams.WRAP_CONTENT;
            }
//            viewH = pxToDP(uiLayoutProperties.getHeight());
            Log.d(TAG, "addLayoutToPrimaryViewWSc" + viewW);
            Log.d(TAG, "addLayoutToPrimaryViewHSc" + viewH);

        }

        viewW = viewW - (pxToDP(uiLayoutProperties.getMarginLeft()) + pxToDP(uiLayoutProperties.getMarginRight()));
        viewH = viewH - (pxToDP(uiLayoutProperties.getMarginTop()) + pxToDP(uiLayoutProperties.getMarginBottom()));
        if (isScreenFit && mappingControlModel != null && mappingControlModel.getControlType().equalsIgnoreCase(CONTROL_TYPE_MAP)) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, viewH);
            layout_addMain.setLayoutParams(layoutParams);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(viewW, viewH);
        layoutParams.setMargins(pxToDP(uiLayoutProperties.getMarginLeft()), pxToDP(uiLayoutProperties.getMarginTop()),
                pxToDP(uiLayoutProperties.getMarginRight()), pxToDP(uiLayoutProperties.getMarginBottom()));
        if (mappingControlModel == null) {
            layout_addMain.setLayoutParams(layoutParams);
        } else if (isScreenFit) {
            layout_addMain.setLayoutParams(layoutParams);
        } else if (mappingControlModel != null && !mappingControlModel.getControlType().equalsIgnoreCase(CONTROL_TYPE_MAP)) {
//            layout_addMain.setLayoutParams(layoutParams);
        }
        overFlowHorizontal(uiLayoutProperties, hsOverFlow);

        applyUiProperties(layout_addMain, uiLayoutProperties, mappingControlModel, rView, false, controlUIProperties, viewH);

        LinearLayout ll_addSubLayoutContainer = rView.findViewById(R.id.ll_addSubLayoutContainer);

        if (layoutProperties != null && layoutProperties.getSubLayoutPropertiesList() != null && layoutProperties.getSubLayoutPropertiesList().size() > 0) {
            if (layoutProperties.getUiLayoutProperties().getOrientation().equalsIgnoreCase(context.getString(R.string.horizontal))) {
                ll_addSubLayoutContainer.setOrientation(LinearLayout.HORIZONTAL);
            }

            List<LayoutProperties> subLayoutPropertiesList = layoutProperties.getSubLayoutPropertiesList();
            int subLayoutsTotalWidth = screenWidth - (pxToDP(layoutProperties.getUiLayoutProperties().getPaddingLeft()) + pxToDP(layoutProperties.getUiLayoutProperties().getPaddingRight()));
            int subLayoutsTotalHeight = viewH - (pxToDP(layoutProperties.getUiLayoutProperties().getPaddingTop()) + pxToDP(layoutProperties.getUiLayoutProperties().getPaddingBottom()));
            if (uiLayoutProperties.getInsideAlignment() != null && !uiLayoutProperties.getInsideAlignment().isEmpty()) {
                if (uiLayoutProperties.getInsideAlignment().equalsIgnoreCase(context.getString(R.string.top))) {
                    ll_addSubLayoutContainer.setLayoutParams(new FrameLayout.LayoutParams(subLayoutsTotalWidth,
                            subLayoutsTotalHeight, Gravity.TOP));
                } else if (uiLayoutProperties.getInsideAlignment().equalsIgnoreCase(context.getString(R.string.middle))) {
                    ll_addSubLayoutContainer.setLayoutParams(new FrameLayout.LayoutParams(subLayoutsTotalWidth,
                            subLayoutsTotalHeight, Gravity.CENTER));
                } else if (uiLayoutProperties.getInsideAlignment().equalsIgnoreCase(context.getString(R.string.bottom))) {
                    ll_addSubLayoutContainer.setLayoutParams(new FrameLayout.LayoutParams(subLayoutsTotalWidth,
                            subLayoutsTotalHeight, Gravity.BOTTOM));
                } else if (uiLayoutProperties.getInsideAlignment().equalsIgnoreCase(context.getString(R.string.left))) {
                    ll_addSubLayoutContainer.setLayoutParams(new FrameLayout.LayoutParams(subLayoutsTotalWidth,
                            subLayoutsTotalHeight, Gravity.LEFT));
                } else if (uiLayoutProperties.getInsideAlignment().equalsIgnoreCase(context.getString(R.string.right))) {
                    ll_addSubLayoutContainer.setLayoutParams(new FrameLayout.LayoutParams(subLayoutsTotalWidth,
                            subLayoutsTotalHeight, Gravity.RIGHT));
                }
            }

//            ll_addSubLayoutContainer.setLayoutParams(new FrameLayout.LayoutParams(subLayoutsTotalWidth,
//                    subLayoutsTotalHeight, Gravity.CENTER));
            for (int j = 0; j < subLayoutPropertiesList.size(); j++) {
                // Inflate SubLayout Views

                UILayoutProperties uiSubLayoutProperties = subLayoutPropertiesList.get(j).getUiLayoutProperties();
                MappingControlModel mappingControlModelSub = subLayoutPropertiesList.get(j).getLayoutControl();
                ControlUIProperties controlUIProperties1 = mappingControlModelSub.getControlUIProperties();

                addSubLayoutToLayout(ll_addSubLayoutContainer, layoutProperties, uiSubLayoutProperties,
                        mappingControlModelSub, subLayoutsTotalHeight, subLayoutsTotalWidth, controlUIProperties1);

            }
        }

    }

    public void addSubLayoutToLayout(LinearLayout ll_addSubLayoutContainer, LayoutProperties parentLayoutProperties,
                                     UILayoutProperties uiSubLayoutProperties, MappingControlModel mappingControlModelSub,
                                     int layoutHeight, int subLayoutsTotalWidth, ControlUIProperties controlUIProperties) {
        final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rView = lInflater.inflate(R.layout.add_sub_layout, null);
        final LinearLayout ll_subLayoutMain = rView.findViewById(R.id.ll_subLayoutMain);

        ll_addSubLayoutContainer.addView(rView);
        ViewTreeObserver vto = ll_subLayoutMain.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                ll_subLayoutMain.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int viewW = 0;
                int viewH = 0;
                int w = 0;
                int h = 0;
                if (isScreenFit) {
                    h = layoutHeight;
                    w = subLayoutsTotalWidth;
                    viewW = (uiSubLayoutProperties.getWidth() * w) / 100;
                    viewH = (uiSubLayoutProperties.getHeight() * h) / 100;
                    Log.d(TAG, "onGlobalLayoutVWVH: " + viewW + "-" + viewH);
                    Log.d(TAG, "onGlobalLayoutVWVH: " + subLayoutsTotalWidth);
                    Log.d(TAG, "onGlobalLayoutVWVH: " + screenWidth);
                } else {
                    UILayoutProperties uiLayoutProperties = parentLayoutProperties.getUiLayoutProperties();
                    if (uiLayoutProperties != null && uiLayoutProperties.getWidthFixedVariable() != null
                            && !uiLayoutProperties.getWidthFixedVariable().isEmpty()
                            && uiLayoutProperties.getWidthFixedVariable().equalsIgnoreCase(context.getString(R.string.fixed_Width))) {
                        h = layoutHeight;
                        w = screenWidth;
                        viewW = (uiSubLayoutProperties.getWidth() * w) / 100;
                        viewH = (uiSubLayoutProperties.getHeight() * h) / 100;
//                        Log.d(TAG, "onGlobalLayout1: "+h);
//                        Log.d(TAG, "onGlobalLayout2: "+w);
//                        Log.d(TAG, "onGlobalLayout3: "+viewW+"-"+uiSubLayoutProperties.getWidth());
//                        Log.d(TAG, "onGlobalLayout4: "+viewH+"-"+uiSubLayoutProperties.getHeight());

                    } else {
                        if (uiSubLayoutProperties.getWidth() != 0) {
                            viewW = pxToDP(uiSubLayoutProperties.getWidth());
                        } else {
                            viewW = LinearLayout.LayoutParams.WRAP_CONTENT;
                        }
                        if (uiSubLayoutProperties.getWrap_or_dp() != null && !uiSubLayoutProperties.getWrap_or_dp().isEmpty()) {
                            Log.d(TAG, "LayoutWrapOrDp: " + uiSubLayoutProperties.getWrap_or_dp());
                            if (uiSubLayoutProperties.getWrap_or_dp().equalsIgnoreCase(context.getString(R.string.both_wrap_dp))) {
//                            viewH = LinearLayout.LayoutParams.WRAP_CONTENT;
//                            bothWrapContentAndDp(mappingControlModelSub.getControlType(), mappingControlModelSub.getControlName());
                            } else if (uiSubLayoutProperties.getWrap_or_dp().equalsIgnoreCase(context.getString(R.string.only_wrap))) {
                                viewH = LinearLayout.LayoutParams.WRAP_CONTENT;
                            } else if (uiSubLayoutProperties.getWrap_or_dp().equalsIgnoreCase(context.getString(R.string.only_dp))) {
                                viewH = pxToDP(uiSubLayoutProperties.getHeight());
                            } else {
                                viewH = LinearLayout.LayoutParams.WRAP_CONTENT;
                            }
                        } else {
                            viewH = LinearLayout.LayoutParams.WRAP_CONTENT;
                        }

                    }
                }
                Log.d(TAG, "test1width: " + viewW);
                Log.d(TAG, "test1Height: " + viewH);
                viewW = viewW - (pxToDP(uiSubLayoutProperties.getMarginLeft()) + pxToDP(uiSubLayoutProperties.getMarginRight()));
                viewH = viewH - (pxToDP(uiSubLayoutProperties.getMarginTop()) + pxToDP(uiSubLayoutProperties.getMarginBottom()));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(viewW, viewH);

                layoutParams.setMargins(pxToDP(uiSubLayoutProperties.getMarginLeft()), pxToDP(uiSubLayoutProperties.getMarginTop()),
                        pxToDP(uiSubLayoutProperties.getMarginRight()), pxToDP(uiSubLayoutProperties.getMarginBottom()));

                ll_subLayoutMain.setLayoutParams(layoutParams);

                if (uiSubLayoutProperties.getInsideAlignment() != null && !uiSubLayoutProperties.getInsideAlignment().isEmpty()) {

                    if (uiSubLayoutProperties.getInsideAlignment().equalsIgnoreCase(context.getString(R.string.top))) {
                        ll_subLayoutMain.setGravity(Gravity.TOP);
                    } else if (uiSubLayoutProperties.getInsideAlignment().equalsIgnoreCase(context.getString(R.string.middle))) {
                        ll_subLayoutMain.setWeightSum(1);
                        ll_subLayoutMain.setGravity(Gravity.CENTER);
                    } else if (uiSubLayoutProperties.getInsideAlignment().equalsIgnoreCase(context.getString(R.string.bottom))) {
                        ll_subLayoutMain.setGravity(Gravity.BOTTOM);
                    }

                }
            }
        });

        applyUiProperties(ll_subLayoutMain, uiSubLayoutProperties, mappingControlModelSub, null, true, controlUIProperties, layoutHeight);

    }

    public void applyUiProperties(LinearLayout ll_container, UILayoutProperties uiLayoutProperties, MappingControlModel mappingControlModel, View rView, boolean isSubLayout, ControlUIProperties controlUIProperties, int layoutHeight) {
        Log.d(TAG, "SectionControlOne: " + "Five");
        if (uiLayoutProperties != null) {
            AppConstants.uiLayoutPropertiesStatic = uiLayoutProperties;
            if (!isSubLayout) {
                if (uiLayoutProperties.getInsideAlignment() != null) {
                    if (uiLayoutProperties.getInsideAlignment().equalsIgnoreCase(context.getString(R.string.top))) {
                        ll_container.setGravity(Gravity.TOP);
                    } else if (uiLayoutProperties.getInsideAlignment().equalsIgnoreCase(context.getString(R.string.middle))) {
                        ll_container.setGravity(Gravity.CENTER);
                    } else if (uiLayoutProperties.getInsideAlignment().equalsIgnoreCase(context.getString(R.string.bottom))) {
                        ll_container.setGravity(Gravity.BOTTOM);
                    }
                }
            }

            ll_container.setPadding(pxToDP(uiLayoutProperties.getPaddingLeft()), pxToDP(uiLayoutProperties.getPaddingTop()), pxToDP(uiLayoutProperties.getPaddingRight()), pxToDP(uiLayoutProperties.getPaddingBottom()));

            GradientDrawable.Orientation gradientAlignment = null;

            if (uiLayoutProperties.getBackgroundType() != null && !uiLayoutProperties.getBackgroundType().isEmpty()) {

                if (uiLayoutProperties.getBackgroundType().equalsIgnoreCase(context.getString(R.string.image))) {
                    if (uiLayoutProperties.getBackGroundImage().contains("http")) {
                        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
                        imageLoader.loadImage(uiLayoutProperties.getBackGroundImage(), new SimpleImageLoadingListener() {
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                ll_container.setBackground(new BitmapDrawable(context.getResources(), loadedImage));
                            }
                        });
                    } else {
                        File imgFile = new File(uiLayoutProperties.getBackGroundImage());
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        ll_container.setBackground(new BitmapDrawable(context.getResources(), myBitmap));
                    }
                } else if (uiLayoutProperties.getBackgroundType() != null && uiLayoutProperties.getBackgroundType().equalsIgnoreCase(context.getString(R.string.color))) {
                    if (uiLayoutProperties.getColorType() != null && uiLayoutProperties.getColorType().equalsIgnoreCase(context.getString(R.string.transparent))) {

                        ll_container.setBackgroundColor(context.getResources().getColor(R.color.transparent_color));
                    } else if (uiLayoutProperties.getColorType() != null && uiLayoutProperties.getColorType().equalsIgnoreCase(context.getString(R.string.plain))) {
                        if (uiLayoutProperties.getBackGroundColorHex() != null) {
                            ll_container.setBackgroundColor(Color.parseColor(uiLayoutProperties.getBackGroundColorHex()));
                        }
                    } else if (uiLayoutProperties.getColorType() != null && uiLayoutProperties.getColorType().equalsIgnoreCase(context.getString(R.string.gradient))) {
                        if (uiLayoutProperties.getGradientType() != null && !uiLayoutProperties.getGradientType().isEmpty()) {
                            if (uiLayoutProperties.getGradientType().equalsIgnoreCase(context.getString(R.string.top_to_bottom))) {
                                gradientAlignment = GradientDrawable.Orientation.TOP_BOTTOM;
                            } else if (uiLayoutProperties.getGradientType().equalsIgnoreCase(context.getString(R.string.bottom_to_top))) {
                                gradientAlignment = GradientDrawable.Orientation.BOTTOM_TOP;
                            } else if (uiLayoutProperties.getGradientType().equalsIgnoreCase(context.getString(R.string.left_to_right))) {
                                gradientAlignment = GradientDrawable.Orientation.LEFT_RIGHT;
                            } else if (uiLayoutProperties.getGradientType().equalsIgnoreCase(context.getString(R.string.right_to_left))) {
                                gradientAlignment = GradientDrawable.Orientation.RIGHT_LEFT;
                            }
                        }
                        String gradientOneColorHex = "#FFFFFF";
                        if (uiLayoutProperties.getGradientOneColorHex() != null
                                && !uiLayoutProperties.getGradientOneColorHex().isEmpty()) {
                            gradientOneColorHex = uiLayoutProperties.getGradientOneColorHex();
                        }
                        String gradientTwoColorHex = "#FFFFFF";
                        if (uiLayoutProperties.getGradientTwoColorHex() != null
                                && !uiLayoutProperties.getGradientTwoColorHex().isEmpty()) {
                            gradientTwoColorHex = uiLayoutProperties.getGradientTwoColorHex();

                        }

                        String borderColorHex = "#FFFFFF";
                        if (uiLayoutProperties.getBorderColorHex() != null && !uiLayoutProperties.getBorderColorHex().isEmpty()) {
                            borderColorHex = uiLayoutProperties.getBorderColorHex();
                            Log.d(TAG, "applyUiPropertiesborderColorHex: " + uiLayoutProperties.getBorderColorHex());
                        }

                        int borderRadiusValue = 0;
                        if (uiLayoutProperties.getBorderRadius() != null && !uiLayoutProperties.getBorderRadius().isEmpty()) {
                            String borderRadius = uiLayoutProperties.getBorderRadius();
                            borderRadiusValue = pxToDP(Integer.valueOf(borderRadius));

                        }
                        int borderStrokeValue = 0;
                        if (uiLayoutProperties.getBorderStroke() != null && !uiLayoutProperties.getBorderStroke().isEmpty()) {
                            String borderStroke = uiLayoutProperties.getBorderStroke();
                            borderStrokeValue = pxToDP(Integer.valueOf(borderStroke));
                        }

                        GradientDrawable gd = new GradientDrawable(
                                gradientAlignment,
                                new int[]{Color.parseColor(gradientOneColorHex), Color.parseColor(gradientTwoColorHex)});
                        gd.setCornerRadius(borderRadiusValue);
                        gd.setStroke(borderStrokeValue, Color.parseColor(borderColorHex));
                        ll_container.setBackground(gd);

                    }
                }
            }
        }

        if (mappingControlModel != null && !mappingControlModel.getControlName().isEmpty()) {
            Log.d(TAG, "SectionControlOne: " + "Six");
            Log.d(TAG, "MappingCtrlNamesSection: " + mappingControlModel.getControlName());

            ControlObject controlObject = globalControlObjects.get(mappingControlModel.getControlName());
            if (controlObject != null) {
                loadControl(controlObject, mappingControlModel.getControlType(), ll_container,
                        uiLayoutProperties, controlUIProperties, layoutHeight, New_list_ControlClassObjects);
            }
        }
    }

    public void setMarginToLinearLayout(LinearLayout linearLayout, int marginLeft, int marginTop, int marginRight, int marginBottom, int viewH, int viewW) {
        try {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, viewH);
            layoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom);
            linearLayout.setLayoutParams(layoutParams);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setMarginToLinearLayout", e);
        }
    }

    public int pxToDP(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics());
    }

    public void FormLoad() {

        AppConstants.EventFrom_subformOrNot = false;
        formLoad = true;
        ((MainActivity) context).event("FormLoadEvent", null);

    }

//    public void setScreenType(UIPrimaryLayoutModelClass primaryLayoutModelClass) {
//        try {
//            if (primaryLayoutModelClass != null && primaryLayoutModelClass.getScreenType() != null && !primaryLayoutModelClass.getScreenType().isEmpty()) {
//                if (primaryLayoutModelClass.getScreenType().equalsIgnoreCase(context.getString(R.string.screen_fit))) {
//                    isScreenFit = true;
//
//                } else if (primaryLayoutModelClass.getScreenType().equalsIgnoreCase(context.getString(R.string.scrollable))) {
//                    isScreenFit = false;
//                }
//            }
//            Log.d(TAG, "primaryLayoutDataScreenFit: " + isScreenFit);
//        } catch (Exception e) {
//            Log.d(TAG, "primaryLayoutDataScreenFitEx: " + e.toString());
//            ImproveHelper.improveException(context, TAG, "setScreenType", e);
//        }
//    }

    public void setScreenType(UIPrimaryLayoutModelClass primaryLayoutModelClass) {
        try {

            if (primaryLayoutModelClass != null && primaryLayoutModelClass.getScreenType() != null && !primaryLayoutModelClass.getScreenType().isEmpty()) {
                if (primaryLayoutModelClass.getScreenType().equalsIgnoreCase(context.getString(R.string.screen_fit))) {
                    uFScrollView.setFillViewport(true);
                    isScreenFit = true;

                } else if (primaryLayoutModelClass.getScreenType().equalsIgnoreCase(context.getString(R.string.scrollable))) {
                    uFScrollView.setFillViewport(false);
                    isScreenFit = false;
                }
            }

            uFScrollView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return isScreenFit;
                }
            });

           /* uFScrollViewHorizontal.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return isScreenFit;
                }
            });*/

            Log.d(TAG, "primaryLayoutDataScreenFitSF: " + isScreenFit);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setScreenType", e);
        }
    }


    public int navigationBarHeight() {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int isEdgeToEdgeEnabled(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("config_navBarInteractionMode", "integer", "android");
        if (resourceId > 0) {
            return resources.getInteger(resourceId);
        }

        return 0;
    }

    public void getRealContentSize2() {
        WindowManager windowManager =
                (WindowManager) context.getApplication().getSystemService(Context.
                        WINDOW_SERVICE);


        final Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        if (Build.VERSION.SDK_INT >= 19) {
            // There may be virtual keys
            display.getRealSize(outPoint);
        } else {
            // No virtual key
            display.getSize(outPoint);
        }
        int mRealSizeWidth;//Real width of mobile screen
        int mRealSizeHeight;//Real height of mobile screen
        int height = outPoint.y;
        screenWidth = outPoint.x;
        toolBarheight = improveHelper.dpToPx(56);

        if (currentMultiForm != null && currentMultiForm.getBottomNavigation() != null) {
            screenHeight = height - (toolBarheight);
        } else {
            screenHeight = height;
        }
    }

    public void overFlowHorizontal(UILayoutProperties uiLayoutProperties, HorizontalScrollView hsOverFlow) {
        boolean isOverFlowCheck = true;
        if (uiLayoutProperties != null && uiLayoutProperties.isOverflow()
                && uiLayoutProperties.getWidthFixedVariable() != null
                && !uiLayoutProperties.getWidthFixedVariable().isEmpty()
                && uiLayoutProperties.getWidthFixedVariable().equalsIgnoreCase(context.getString(R.string.variable))) {

            isOverFlowCheck = !uiLayoutProperties.isOverflow();
        }
        boolean finalIsOverFlow = isOverFlowCheck;
        hsOverFlow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return finalIsOverFlow;
            }
        });


    }

    public List<ControlObject> getSectionControlList() {
        return sectionControlList;
    }

    public boolean getVisibility() {
        controlObject.setInvisible(linearLayout.getVisibility() != View.VISIBLE);
        return controlObject.isInvisible();
    }


    public void setVisibility(boolean visibility) {

        if (visibility) {
            linearLayout.setVisibility(View.VISIBLE);
            controlObject.setInvisible(false);
        } else {
            linearLayout.setVisibility(View.GONE);
            controlObject.setInvisible(true);
        }
    }

    public ControlObject getControlObject() {
        return controlObject;
    }
    public boolean isEnabled() {

        return !controlObject.isDisable();
    }


    public void setEnabled(boolean enabled) {
        setViewDisable(view, !enabled);
        controlObject.setDisable(!enabled);
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

    public void setDisplayName(String displayName) {
        tv_displayName.setText(displayName);
        controlObject.setDisplayName(displayName);
    }

    public String getHint() {
        return controlObject.getHint();
    }

    public void setHint(String hint) {
        controlObject.setHint(hint);
        if (hint != null && !hint.isEmpty()) {
            tv_hint.setVisibility(View.VISIBLE);
            tv_hint.setText(hint);
        } else {
            tv_hint.setVisibility(View.GONE);
        }

    }


    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }

    public void setHideDisplayName(boolean hideDisplayName) {
        if (hideDisplayName) {
            ll_displayName.setVisibility(View.GONE);
        } else {
            ll_displayName.setVisibility(View.VISIBLE);
        }
        controlObject.setHideDisplayName(hideDisplayName);
    }

    public boolean isInvisible() {
        return controlObject.isInvisible();
    }

    public void setInvisible(boolean invisible) {
        controlObject.setInvisible(invisible);
        if (invisible) {
            linearLayout.setVisibility(View.GONE);
        }
    }

    public boolean isEnableCollapseOrExpand() {
        return controlObject.isEnableCollapseOrExpand();
    }

    public void setEnableCollapseOrExpand(boolean isEnableCollapseOrExpand) {
        controlObject.setEnableCollapseOrExpand(isEnableCollapseOrExpand);
        if (isEnableCollapseOrExpand) {
            btn_expand.setVisibility(View.VISIBLE);
        }

    }

    public void setMakeItAsPopup(boolean isMakeItAsPopUp) {
        controlObject.setMakeItAsPopup(isMakeItAsPopUp);
        if (isMakeItAsPopUp) {
            ll_main_inside.setVisibility(View.GONE);
        }

    }

    public LinearLayout getLl_sectionContainer() {
        return ll_sectionContainer;
    }

    public void setLl_sectionContainer(LinearLayout ll_sectionContainer) {
        this.ll_sectionContainer = ll_sectionContainer;
    }

    // Sanjay
    public void sectionControlUISettings(ControlUIProperties controlUIProperties,LinearLayout ll_sectionContainer) {

        try {
            if (controlUIProperties != null && controlUIProperties.getTypeOfWidth() != null && controlUIProperties.getTypeOfHeight() != null
                    && !controlUIProperties.getTypeOfWidth().equalsIgnoreCase(AppConstants.DEFAULT_WIDTH)
                    && !controlUIProperties.getTypeOfHeight().equalsIgnoreCase(AppConstants.DEFAULT_HEIGHT)) {
                String strWidthType = controlUIProperties.getTypeOfWidth();
                String strHeightType = controlUIProperties.getTypeOfHeight();

                LinearLayout.LayoutParams lpContainer = (LinearLayout.LayoutParams) ll_sectionContainer.getLayoutParams();

                if (strWidthType.equalsIgnoreCase(AppConstants.CUSTOM_WIDTH) && strHeightType.equalsIgnoreCase(AppConstants.CUSTOM_HEIGHT)) {
                    lpContainer.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                    lpContainer.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));

                } else if (strWidthType.equalsIgnoreCase(MATCH_PARENT) && strHeightType.equalsIgnoreCase(AppConstants.MATCH_PARENT)) {
                    lpContainer.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    lpContainer.height = LinearLayout.LayoutParams.MATCH_PARENT;
                }
                if (lpContainer != null) {
                    ll_sectionContainer.setLayoutParams(lpContainer);
                }
            }
        }catch (Exception e){
            Log.getStackTraceString(e);
        }
    }
    public void controlManagementOptions() {
        if (visibilityManagementOptions != null && visibilityManagementOptions.getVisibilityOffColumns() != null && visibilityManagementOptions.getVisibilityOffColumns().size() > 0) {
            SetVisibleOff(visibilityManagementOptions.getVisibilityOffColumns());
        }
        if (visibilityManagementOptions != null && visibilityManagementOptions.getVisibilityOnColumns() != null && visibilityManagementOptions.getVisibilityOnColumns().size() > 0) {
            SetVisibleOn(visibilityManagementOptions.getVisibilityOnColumns());
        }
        if (visibilityManagementOptions != null && visibilityManagementOptions.getDisableColumns() != null && visibilityManagementOptions.getDisableColumns().size() > 0) {
            new ControlManagement(context,controlObject.getSubFormControlList(),New_list_ControlClassObjects,visibilityManagementOptions.getDisableColumns(),2);
        }
        if (visibilityManagementOptions != null && visibilityManagementOptions.getEnableColumns() != null && visibilityManagementOptions.getEnableColumns().size() > 0) {
            new ControlManagement(context,controlObject.getSubFormControlList(),New_list_ControlClassObjects,visibilityManagementOptions.getEnableColumns(),3);
        }

        List<String> editColumnsOfSubForm = new ArrayList<>();
        editColumnsOfSubForm = improveHelper.getEditColumns(editColumns, AppConstants.SUB_CONTROL, controlObject.getControlName());
        if (editColumnsOfSubForm != null && editColumnsOfSubForm.size() > 0) {
            if (AppConstants.EDIT_MODE) {
                new ControlManagement(context,sectionControlList,New_list_ControlClassObjects,editColumnsOfSubForm,1);
            }
        }


    }

    public void SetVisibleOn(List<String> VisibleOn_ControlNames) {
        Log.d(TAG, "SetVisibleOn: " + VisibleOn_ControlNames);
        try {
            for (int i = 0; i < controlObject.getSubFormControlList().size(); i++) {
                ControlObject temp_controlObj = controlObject.getSubFormControlList().get(i);
                if (VisibleOn_ControlNames.contains(temp_controlObj.getControlName())) {
                    ImproveHelper.setVisible(true, temp_controlObj,New_list_ControlClassObjects);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "SetVisibleOn", e);
        }
    }
    public void SetVisibleOff(List<String> Visibleoff_ControlNames) {
        Log.d(TAG, "SetVisibleOff: " + Visibleoff_ControlNames);
        try {
            for (int i = 0; i < controlObject.getSubFormControlList().size(); i++) {
                ControlObject temp_controlObj = controlObject.getSubFormControlList().get(i);
                if (Visibleoff_ControlNames.contains(temp_controlObj.getControlName())) {
                    Log.d(TAG, "SetVisibleOff_: " + temp_controlObj.getControlName());
                    ImproveHelper.setVisible(false, temp_controlObj,New_list_ControlClassObjects);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "SetVisibleOff", e);
        }
    }


}
