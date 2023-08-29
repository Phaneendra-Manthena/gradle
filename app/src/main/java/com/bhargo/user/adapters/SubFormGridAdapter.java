package com.bhargo.user.adapters;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUDIO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_GENERATION;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_NUMBER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_BAR_CODE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_BUTTON;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CALENDER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CAMERA;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHECKBOX;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHECK_LIST;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CURRENCY;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DATA_CONTROL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DECIMAL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DROP_DOWN;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DYNAMIC_LABEL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_EMAIL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_FILE_BROWSING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_GPS;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_IMAGE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_LARGE_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_MAP;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_NUMERIC_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PASSWORD;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PERCENTAGE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PHONE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_POST;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_QR_CODE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_RADIO_BUTTON;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_RATING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SIGNATURE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_TEXT_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_URL_LINK;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_USER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_RECORDING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VOICE_RECORDING;
import static com.bhargo.user.utils.AppConstants.SF_FILE_BROWSER_RESULT_CODE;
import static com.bhargo.user.utils.AppConstants.SF_REQUEST_CAMERA_CONTROL_CODE;
import static com.bhargo.user.utils.AppConstants.SF_REQUEST_CURRENT_LOCATION;
import static com.bhargo.user.utils.AppConstants.SF_REQUEST_SPEECH_TO_TEXT;
import static com.bhargo.user.utils.AppConstants.SF_REQUEST_VIDEO_RECORDING;
import static com.bhargo.user.utils.AppConstants.SF_REQ_CODE_PICK_ONLY_VIDEO_FILE;
import static com.bhargo.user.utils.AppConstants.SF_REQ_CODE_PICK_VOICE_REC;
import static com.bhargo.user.utils.ImproveHelper.pxToDPControlUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Expression.ExpressionMainHelper;
import com.bhargo.user.Java_Beans.API_OutputParam_Bean;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.Control_EventObject;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.Java_Beans.Item;
import com.bhargo.user.Java_Beans.New_DataControl_Bean;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.controls.advanced.BarCode;
import com.bhargo.user.controls.advanced.Gps_Control;
import com.bhargo.user.controls.advanced.PostControl;
import com.bhargo.user.controls.advanced.QRCode;
import com.bhargo.user.controls.advanced.UserControl;
import com.bhargo.user.controls.data_controls.DataControl;
import com.bhargo.user.controls.standard.AudioPlayer;
import com.bhargo.user.controls.standard.AutoNumber;
import com.bhargo.user.controls.standard.Button;
import com.bhargo.user.controls.standard.Calendar;
import com.bhargo.user.controls.standard.Camera;
import com.bhargo.user.controls.standard.CheckList;
import com.bhargo.user.controls.standard.Checkbox;
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
import com.bhargo.user.controls.standard.UrlView;
import com.bhargo.user.controls.standard.VideoPlayer;
import com.bhargo.user.controls.standard.VideoRecording;
import com.bhargo.user.controls.standard.VoiceRecording;
import com.bhargo.user.uisettings.ControlUiSettings;
import com.bhargo.user.uisettings.pojos.ControlUIProperties;
import com.bhargo.user.uisettings.pojos.LayoutProperties;
import com.bhargo.user.uisettings.pojos.MappingControlModel;
import com.bhargo.user.uisettings.pojos.UILayoutProperties;
import com.bhargo.user.uisettings.pojos.UIPrimaryLayoutModelClass;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.FilePicker;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.ControlUtils;
import com.bhargo.user.utils.SignaturePad;
import com.google.gson.Gson;
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

public class SubFormGridAdapter extends RecyclerView.Adapter<SubFormGridAdapter.ViewHolder> {

    private static final String TAG = "SubFormGridAdapter";
    private List<ControlObject> controlObjectList;
    private Activity context;
    private List<API_OutputParam_Bean> list_Form_OutParams;
    private  int height;
    private  int width;
    private  int screenWidth;
    public ControlObject controlObject;
    //    GroupClickListener clickListener;
    public LinkedHashMap<String, Control_EventObject> hash_Onchange_EventObjects;
    public LinkedHashMap<String, Control_EventObject> hash_Onfocus_EventObjects;
    public List<LinkedHashMap<String, Object>> subform_List_ControlClassObjects;
    public LinkedHashMap<String, Control_EventObject> hash_Onclick_EventObjects;
    public int SubFormTAG = 0;
    public JSONArray jArrayAutoIncrementControls = new JSONArray();

    int iMinRows, iMaxRows;
    int selectedPos;
    String appLanguage = "en";
    String appname;
    ImproveHelper improveHelper;
    DataCollectionObject dataCollectionObject;
    String strUserLocationStructure;
    String orgId;
    LinearLayout ll_MainSubFormContainer;
    LinkedHashMap<String, Object> New_list_ControlClassObjects = new LinkedHashMap<String, Object>();
    ScrollView uFScrollView;
    UILayoutProperties uiLayoutPropertiesSF;
    ControlUIProperties controlUIProperties;
    HashMap<String, ControlObject> globalControlObjects;
    List<MapControl> subFormMapControls = new ArrayList<>();
    List<List<String>> subFormMappedValues = new ArrayList<>();
    LinkedHashMap<String, List<String>> OutputData;
    JSONArray jsonArrayCheckList = new JSONArray();
    int threshold;
    RecyclerView recyclerView;
    int rowsToBind;
    private LinkedHashMap<String, List<String>> outputData;
    private LinearLayout ll_innerSubFormContainer;
    private boolean isAutoNumbersAvaliable;
    private boolean isAutoNumbeStatusAlreadyAdded;
    private boolean isScreenFit = false;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    private int screenHeight;
    private List<Integer> listOfItems;

    public SubFormGridAdapter(Activity context, LinearLayout ll_MainSubFormContainer, int iMinRows, ControlObject controlObject,
                              List<ControlObject> controlObjectList, String appname, String strUserLocationStructure, String orgId,
                              LinkedHashMap<String, Control_EventObject> hash_Onchange_EventObjects,
                              LinkedHashMap<String, Control_EventObject> hash_Onfocus_EventObjects,
                              List<LinkedHashMap<String, Object>> subform_List_ControlClassObjects,
                              LinkedHashMap<String, Control_EventObject> hash_Onclick_EventObjects,
                              List<API_OutputParam_Bean> list_Form_OutParams,
                              ScrollView uFScrollView,
                              UILayoutProperties uiLayoutProperties,
                              ControlUIProperties controlUIProperties,
                              int layoutHeight,
                              HashMap<String, ControlObject> globalControlObjects,
                              LinkedHashMap<String, List<String>> OutputData, List<Integer> listOfItems, int rowsToBind, int threshold, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.rowsToBind = rowsToBind;
        this.threshold = threshold;
        this.listOfItems = listOfItems;
        this.ll_MainSubFormContainer = ll_MainSubFormContainer;
        this.iMinRows = iMinRows;
        this.controlObject = controlObject;
        this.controlObjectList = controlObjectList;
        this.appname = appname;
        this.strUserLocationStructure = strUserLocationStructure;
        this.orgId = orgId;
        this.hash_Onchange_EventObjects = hash_Onchange_EventObjects;
        this.hash_Onfocus_EventObjects = hash_Onfocus_EventObjects;
        this.subform_List_ControlClassObjects = subform_List_ControlClassObjects;
        this.hash_Onclick_EventObjects = hash_Onclick_EventObjects;
        this.list_Form_OutParams = list_Form_OutParams;
        this.uFScrollView = uFScrollView;
        this.uiLayoutPropertiesSF = uiLayoutProperties;
        this.controlUIProperties = controlUIProperties;
        this.screenHeight = layoutHeight;
        this.globalControlObjects = globalControlObjects;
        this.OutputData = OutputData;
        improveHelper = new ImproveHelper(context);
        appLanguage = ImproveHelper.getLocale(context);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        height = dm.heightPixels;
        width = (dm.widthPixels) / 2;
        if (uiLayoutPropertiesSF != null && uiLayoutPropertiesSF.getHeight() != 0) {
            screenHeight = improveHelper.dpToPx(uiLayoutPropertiesSF.getHeight());
        }
        screenWidth = width;
        Log.d(TAG, "getItemCountAdapterC: " + listOfItems.size());
        setHasStableIds(true);
    }

    public void updateObjs(Activity context, LinearLayout ll_MainSubFormContainer, int iMinRows, ControlObject controlObject,
                            List<ControlObject> controlObjectList, String appname, String strUserLocationStructure, String orgId,
                            LinkedHashMap<String, Control_EventObject> hash_Onchange_EventObjects,
                            LinkedHashMap<String, Control_EventObject> hash_Onfocus_EventObjects,
                            List<LinkedHashMap<String, Object>> subform_List_ControlClassObjects,
                            LinkedHashMap<String, Control_EventObject> hash_Onclick_EventObjects,
                            List<API_OutputParam_Bean> list_Form_OutParams,
                            ScrollView uFScrollView,
                            UILayoutProperties uiLayoutProperties,
                            ControlUIProperties controlUIProperties,
                            int layoutHeight,
                            HashMap<String, ControlObject> globalControlObjects,
                            LinkedHashMap<String, List<String>> OutputData, List<Integer> listOfItems, int rowsToBind, int threshold, RecyclerView recyclerView){

        this.recyclerView = recyclerView;
        this.context = context;
        this.rowsToBind = rowsToBind;
        this.threshold = threshold;
        this.listOfItems = listOfItems;
        this.ll_MainSubFormContainer = ll_MainSubFormContainer;
        this.iMinRows = iMinRows;
        this.controlObject = controlObject;
        this.controlObjectList = controlObjectList;
        this.appname = appname;
        this.strUserLocationStructure = strUserLocationStructure;
        this.orgId = orgId;
        this.hash_Onchange_EventObjects = hash_Onchange_EventObjects;
        this.hash_Onfocus_EventObjects = hash_Onfocus_EventObjects;
        this.subform_List_ControlClassObjects = subform_List_ControlClassObjects;
        this.hash_Onclick_EventObjects = hash_Onclick_EventObjects;
        this.list_Form_OutParams = list_Form_OutParams;
        this.uFScrollView = uFScrollView;
        this.uiLayoutPropertiesSF = uiLayoutProperties;
        this.controlUIProperties = controlUIProperties;
        this.screenHeight = layoutHeight;
        this.globalControlObjects = globalControlObjects;
        this.OutputData = OutputData;
        improveHelper = new ImproveHelper(context);
        appLanguage = ImproveHelper.getLocale(context);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        height = dm.heightPixels;
        width = (dm.widthPixels) / 2;
        if (uiLayoutPropertiesSF != null && uiLayoutPropertiesSF.getHeight() != 0) {
            screenHeight = improveHelper.dpToPx(uiLayoutPropertiesSF.getHeight());
        }
        screenWidth = width;
        Log.d(TAG, "getItemCountAdapterC: " + listOfItems.size());
    }

    public void update( List<Integer> listOfItems) {
        this.listOfItems = listOfItems;
        notifyDataSetChanged();
    }

    public void add(Integer id) {
        this.listOfItems.add(id);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SubFormGridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inner_subform_strip_rvg, parent, false);
        return new SubFormGridAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Log.d(TAG, "getItemCountAdapterPosition: " + listOfItems.get(position));
            if (controlObject.getUiPrimaryLayoutModelClass() != null) {
                UIPrimaryLayoutModelClass uiPrimaryLayoutModelClass = controlObject.getUiPrimaryLayoutModelClass();
                primaryLayoutData(uiPrimaryLayoutModelClass, holder.ll_innerSubFormContainer, New_list_ControlClassObjects, position);
            } else {
                for (int i = 0; i < controlObject.getSubFormControlList().size(); i++) {
                    ControlObject co = (ControlObject) controlObject.getSubFormControlList().get(i).clone();
                    loadControl(co,
                            co.getControlType(),
                            holder.ll_innerSubFormContainer, New_list_ControlClassObjects,
                            position, null, null);

                }
            }
            subform_List_ControlClassObjects.add(New_list_ControlClassObjects);
            LinkedHashMap<String, Object> temp_object = subform_List_ControlClassObjects.get(position);
            ControlUtils.setMultiValueToControlFromCallAPIORGetData(context, position, OutputData,
                    list_Form_OutParams, controlObject.getSubFormControlList(), temp_object, new ArrayList<>(), new ArrayList<>());
        } catch (Exception e) {
            Log.d(TAG, "onBindViewHolderSFGA: " + e);
            ImproveHelper.improveException(context, TAG, "SubFromGridAdapterOnBind", e);
        }

    }

    private String getValue(int pos, ControlObject loadControlObject) {
        String Value = "";
        for (int a = 0; a < list_Form_OutParams.size(); a++) {
            if (!list_Form_OutParams.get(a).isOutParam_Delete()) {
                API_OutputParam_Bean API_OutputParam_Bean = list_Form_OutParams.get(a);

                String MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_ID().trim();
                String ControlID = API_OutputParam_Bean.getOutParam_Name();
                if (loadControlObject.getControlName().equals(ControlID)) {
                    if (MappedControlID.trim().length() > 0) {
                        List<String> MappedValues = OutputData.get(MappedControlID.toLowerCase());
                        if (MappedValues.size() > pos) {
                            Value = MappedValues.get(pos);
                        }
                    } else if (API_OutputParam_Bean.getOutParam_Mapped_Expression() != null && API_OutputParam_Bean.getOutParam_Mapped_Expression().trim().length() > 0) {
                        AppConstants.SF_Container_position = pos;
                        ExpressionMainHelper ehelper = new ExpressionMainHelper();
                        Value = ehelper.ExpressionHelper(context, API_OutputParam_Bean.getOutParam_Mapped_Expression().trim());
                    }
                    break;
                }
            }
        }
        return Value;

    }


    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCountAdapter: " + listOfItems.size());
        return listOfItems.size();

    }







    private void loadControl(final ControlObject loadControlObject, final String controlType,
                             final LinearLayout ll_innerSubFormContainer, LinkedHashMap<String, Object> List_ControlClassObjects,
                             int pos, UILayoutProperties uiLayoutProperties, ControlUIProperties controlUIProperties) {
        try {
            //String val=getValue(pos,loadControlObject);
            Log.d(TAG, "XmlHelperTextInput: " + controlType);
            //loadControlObject.setGridControl(true);
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
                    TextInput textInput = new TextInput(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), textInput);
                    ll_innerSubFormContainer.addView(textInput.getTextInputView());
//                setPadding(textInput.getTextInputView());

                    //setMargin(textInput.getTextInputView());
//                TextInput.REQUEST_SPEECH_TO_TEXT =539;

                    if (loadControlObject.getDefaultValue() != null &&
                            !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE); // taptext
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        if (loadControlObject.isReadFromBarcode()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_barcode_drawable));
                            textInput.getIv_textTypeImage().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));

                        } else if (loadControlObject.isReadFromQRCode()) {
                            textInput.gettap_text().setVisibility(View.VISIBLE);
                            textInput.getCustomEditText().setVisibility(View.GONE);
                            textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_qrcode_drawable));
                            textInput.getIv_textTypeImage().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isVoiceText()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));
                            textInput.getIv_textTypeImage().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isCurrentLocation()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_my_location_black_24dp));
                            textInput.getIv_textTypeImage().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isGoogleLocationSearch()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_current_location));
                            textInput.getIv_textTypeImage().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
//                        textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                                textInput.initAutoCompleteTextView();
//                            }
//                        });
                        }
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getCustomEditText().setText(loadControlObject.getDefaultValue());
                    } else if (loadControlObject.isReadFromBarcode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE);
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_barcode_drawable));
                        textInput.getIv_textTypeImage().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
//                    textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                            textInput.tQRBarCode();
//                        }
//                    });
                    } else if (loadControlObject.isReadFromQRCode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE);
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_qrcode_drawable));
                        textInput.getIv_textTypeImage().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
//                    textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                            textInput.tQRBarCode();
//                        }
//                    });
                    } else if (loadControlObject.isVoiceText() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE);
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));
                        textInput.getIv_textTypeImage().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
//                    textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                            textInput.getSpeechInput(true, SF_REQUEST_SPEECH_TO_TEXT);
//                        }
//                    });
                    } else if (loadControlObject.isCurrentLocation() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE);
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_current_location));
                        textInput.getIv_textTypeImage().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
//                    textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                            textInput.tCurrentLocation(SF_REQUEST_CURRENT_LOCATION);
//
//                        }
//                    });
                    } else if (loadControlObject.isReadFromQRCode()) {

                        textInput.getLl_tap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setText("Tap here to Scan QRCode");
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_qrcode_drawable));
                        textInput.gettap_text().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
                        textInput.getIv_textTypeImage().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));

//                    textInput.gettap_text().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                            textInput.tQRBarCode();
//                        }
//                    });
//                    textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                            textInput.tQRBarCode();
//                        }
//                    });
                    } else if (loadControlObject.isReadFromBarcode()) {
                        textInput.getLl_tap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setText("Tap here to Scan BarCode");
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_barcode_drawable));
                        textInput.gettap_text().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
                        textInput.getIv_textTypeImage().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
//                    textInput.gettap_text().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                            textInput.tQRBarCode();
//                        }
//                    });
//                    textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                            textInput.tQRBarCode();
//                        }
//                    });
                    } else if (loadControlObject.isVoiceText()) {
                        textInput.getLl_tap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setText("Tap here voice to text");
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));
                        textInput.getIv_textTypeImage().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
                        textInput.gettap_text().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
//                    textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                            textInput.getSpeechInput(false, SF_REQUEST_SPEECH_TO_TEXT);
//
//                        }
//                    });
//                    textInput.gettap_text().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                            textInput.getSpeechInput(false, SF_REQUEST_SPEECH_TO_TEXT);
//
//                        }
//                    });
                    } else if (loadControlObject.isCurrentLocation()) {
                        textInput.gettap_text().setText("Tap here to get Current Location");
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_my_location_black_24dp));
                        textInput.gettap_text().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
//                    textInput.gettap_text().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                            textInput.tCurrentLocation(SF_REQUEST_CURRENT_LOCATION);
//                        }
//                    });
                    } else if (loadControlObject.isGoogleLocationSearch()) {

                        textInput.gettap_text().setText("Tap here to Search Location");
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_current_location));

                        textInput.setDefaultValueForSearch();
                        textInput.getIv_textTypeImage().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
//                    textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                            textInput.initAutoCompleteTextView();
//
////                    googleSearchPlaces(v);
//                        }
//                    });
                    }


//                if (loadControlObject.isGoogleLocationSearch()) {
//                    textInput.getLl_tap_text().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            onSearchCalled();
//                        }
//                    });
//
//                    textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            onSearchCalled();
//                        }
//                    });
//                }

                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }

                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }

                    break;
                case CONTROL_TYPE_NUMERIC_INPUT:
                    NumericInput numericInput = new NumericInput(context, loadControlObject, true, pos, controlObject.getControlName().trim());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), numericInput);
                /*if ( ll_preViewContainer != null) {
                    ll_preViewContainer.addView(numericInput.getNumericInputView());
                }*//*if (loadControlObject.isUIFormNeededSubForm() && ll_preViewContainer != null) {
                    ll_preViewContainer.addView(numericInput.getNumericInputView());
//                }*/
//                else {
                    ll_innerSubFormContainer.addView(numericInput.getNumericInputView());
//                }
//                setMargin(numericInput.getNumericInputView());
//
//                setPadding(numericInput.getNumericInputView());
                    if (loadControlObject.isInvisible()) {
                        numericInput.getNumericInputView().setVisibility(View.GONE);
                    } else {
                        numericInput.getNumericInputView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_PHONE:
                    Phone phone = new Phone(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), phone);
//                if(ll_preViewContainer != null){
//                    ll_preViewContainer.addView(phone.getPhoneView());
//                }else {
                    ll_innerSubFormContainer.addView(phone.getPhoneView());
//                setPadding(phone.getPhoneView());
//                }
                    if (loadControlObject.isInvisible()) {
                        phone.getPhoneView().setVisibility(View.GONE);
                    } else {
                        phone.getPhoneView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_EMAIL:
                    Email email = new Email(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), email);
                    ll_innerSubFormContainer.addView(email.getEmailView());
//                setPadding(email.getEmailView());
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_CAMERA:
//                Camera camera = new Camera(context, loadControlObject);
//                List_ControlClassObjects.put(loadControlObject.getControlName(), camera);
//                ll_innerSubFormContainer.addView(camera.getCameraView());
//                if (loadControlObject.isOnChangeEventExists()) {
//                    Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
//                    hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
//                }
//                    final Camera camera = new Camera(context, loadControlObject, true, pos, controlObject.getControlName());
                    final Camera camera = new Camera(context, loadControlObject, true, pos, controlObject.getControlName());
                    camera.setPosition(pos);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), camera);
                    ll_innerSubFormContainer.addView(camera.getCameraView());
//                setPadding(camera.getCameraView());
                    camera.getLLTapTextView().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
                    camera.getReTake().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
//                camera.getLLTapTextView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                        camera.getCameraClickView();
//                    }
//                });
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_LARGE_INPUT:
                    LargeInput largeInput = new LargeInput(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), largeInput);
                    ll_innerSubFormContainer.addView(largeInput.getLargeInputView());
//                setPadding(largeInput.getLargeInputView());
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_CHECKBOX:
                    Checkbox checkbox = new Checkbox(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), checkbox);
                    ll_innerSubFormContainer.addView(checkbox.getCheckbox());
                    if (loadControlObject.isInvisible()) {
                        checkbox.getCheckbox().setVisibility(View.GONE);
                    } else {
                        checkbox.getCheckbox().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;

                case CONTROL_TYPE_FILE_BROWSING:
//                    FileBrowsing fileBrowsing = new FileBrowsing(context, loadControlObject);
                    FileBrowsing fileBrowsing = new FileBrowsing(context, loadControlObject, true, pos, controlObject.getControlName());
                    fileBrowsing.setPosition(pos);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), fileBrowsing);
                    ll_innerSubFormContainer.addView(fileBrowsing.getFileBrowsingView());
                    fileBrowsing.getTapTextViewLayout().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));

                /*fileBrowsing.getTapTextView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


//                        AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();

                    }
                });*/
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_VOICE_RECORDING:
                    VoiceRecording voiceRecording = new VoiceRecording(context, loadControlObject, true, pos, controlObject.getControlName());
                    voiceRecording.setPosition(pos);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), voiceRecording);
                    ll_innerSubFormContainer.addView(voiceRecording.getVoiceRecordingView());
                    if (loadControlObject.isInvisible()) {
                        voiceRecording.getVoiceRecordingView().setVisibility(View.GONE);
                    } else {
                        voiceRecording.getVoiceRecordingView().setVisibility(View.VISIBLE);
                    }

                    voiceRecording.startRecordingClick().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
                    voiceRecording.getVoiceRecordingClick().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
//                    voiceRecording.getImageViewPlay().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
//                    voiceRecording.getImageViewStop().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
                    voiceRecording.getLayoutAudioFileUpload().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));

                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_VIDEO_RECORDING:
                    VideoRecording videoRecording = new VideoRecording(context, loadControlObject, true, pos, controlObject.getControlName());
                    videoRecording.setPosition(pos);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), videoRecording);
                    ll_innerSubFormContainer.addView(videoRecording.getVideoRecorderView());
                    if (loadControlObject.isInvisible()) {
                        videoRecording.getVideoRecorderView().setVisibility(View.GONE);
                    } else {
                        videoRecording.getVideoRecorderView().setVisibility(View.VISIBLE);
                    }

                    videoRecording.getTv_startVideoRecorder().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
//                    videoRecording.getImageViewVideo().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
//                    videoRecording.getTv_videoFileUpload().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));


                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;

                case CONTROL_TYPE_SIGNATURE:
                    final SignatureView signature = new SignatureView(context, loadControlObject);
                    signature.setPosition(pos);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), signature);
                    ll_innerSubFormContainer.addView(signature.getSignature());
                    if (loadControlObject.isInvisible()) {
                        signature.getSignature().setVisibility(View.GONE);
                    } else {
                        signature.getSignature().setVisibility(View.VISIBLE);
                    }

                    signature.getSignaturePad().setOnSignedListener(new SignaturePad.OnSignedListener(){

                        @Override
                        public void onStartSigning() {
                            //Event triggered when the pad is touched
                        }

                        @Override
                        public void onSigned() {
                            //Event triggered when the pad is signed
                            Log.d(TAG, "SFonSigned: " + "SFonSigned");
                            signature.addJpgSignatureToGallery(signature.getSignaturePad().getSignatureBitmap());
                        }

                        @Override
                        public void onClear() {
                            //Event triggered when the pad is cleared
                        }
                    });


                    signature.getLayoutUpload().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
                    signature.getTv_clearSignature().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));

                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;

                case CONTROL_TYPE_CALENDER:
                    Calendar calendar = new Calendar(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), calendar);
                    ll_innerSubFormContainer.addView(calendar.getCalnderView());
                    if (loadControlObject.isInvisible()) {
                        calendar.getCalnderView().setVisibility(View.GONE);
                    } else {
                        calendar.getCalnderView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_AUDIO_PLAYER:
                    AudioPlayer audioPlayer = new AudioPlayer(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), audioPlayer);
                    ll_innerSubFormContainer.addView(audioPlayer.getAudioPlayerView());
                    if (loadControlObject.isInvisible()) {
                        audioPlayer.getAudioPlayerView().setVisibility(View.GONE);
                    } else {
                        audioPlayer.getAudioPlayerView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_VIDEO_PLAYER:
                    VideoPlayer videoPlayer = new VideoPlayer(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), videoPlayer);
                    ll_innerSubFormContainer.addView(videoPlayer.getVideoPlayerView());
                    if (loadControlObject.isInvisible()) {
                        videoPlayer.getVideoPlayerView().setVisibility(View.GONE);
                    } else {
                        videoPlayer.getVideoPlayerView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_PERCENTAGE:
                    Percentage percentage = new Percentage(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), percentage);
                    ll_innerSubFormContainer.addView(percentage.getPercentageView());
                    if (loadControlObject.isInvisible()) {
                        percentage.getPercentageView().setVisibility(View.GONE);
                    } else {
                        percentage.getPercentageView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_RADIO_BUTTON:
                    RadioGroupView radioGroup = new RadioGroupView(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), radioGroup);
                    ll_innerSubFormContainer.addView(radioGroup.getRadioGroupView());
                    if (loadControlObject.isInvisible()) {
                        radioGroup.getRadioGroupView().setVisibility(View.GONE);
                    } else {
                        radioGroup.getRadioGroupView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (list_Form_OutParams.size() > 0) {
                        List<Item> items = getListItems(loadControlObject);
                        if (items.size() > 0) {
                            radioGroup.setnewItemsListDynamically(items);
                        }
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_DROP_DOWN:
                    DropDown dropDown = new DropDown(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), dropDown);
                    ll_innerSubFormContainer.addView(dropDown.getDropdown());
                    if (loadControlObject.isInvisible()) {
                        dropDown.getDropdown().setVisibility(View.GONE);
                    } else {
                        dropDown.getDropdown().setVisibility(View.VISIBLE);
                    }
                    setPadding(dropDown.getDropdown());
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (list_Form_OutParams.size() > 0) {
                        List<Item> items = getListItems(loadControlObject);
                        if (items.size() > 0) {
                            dropDown.setnewItemsListDynamically(items);
                        }
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }

                    break;
                case CONTROL_TYPE_CHECK_LIST:
                    AppConstants.isSubformHasCheckList.put(controlObject.getControlName(), true);
                    try {
                        JSONObject checkListJsonObject = new JSONObject();
                        checkListJsonObject.put("ColumnName", loadControlObject.getControlName());
                        checkListJsonObject.put("InsertType", loadControlObject.getRowSelectionType());
                        if (controlNotExists(controlObject.getControlName(), loadControlObject.getControlName())) {
                           /* JSONArray jsonArray = new JSONArray();
                            jsonArray.put(checkListJsonObject);
                            AppConstants.subformCheckListData.put(controlObject.getControlName(),jsonArray);*/
                            //                            JSONArray jsonArray = new JSONArray();
                            jsonArrayCheckList.put(checkListJsonObject);
                            AppConstants.subformCheckListData.put(controlObject.getControlName(), jsonArrayCheckList);
                        }
                    } catch (Exception e) {
                    }
                    CheckList checkList = new CheckList(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), checkList);
                    ll_innerSubFormContainer.addView(checkList.getCheckList());
                    if (loadControlObject.isInvisible()) {
                        checkList.getCheckList().setVisibility(View.GONE);
                    } else {
                        checkList.getCheckList().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (list_Form_OutParams.size() > 0) {
                        List<Item> items = getListItems(loadControlObject);
                        if (items.size() > 0) {
                            checkList.setnewItemsListDynamically(items);
                        }
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_URL_LINK:
                    UrlView urlView = new UrlView(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), urlView);
                    ll_innerSubFormContainer.addView(urlView.getURL());
                    if (loadControlObject.isInvisible()) {
                        urlView.getURL().setVisibility(View.GONE);
                    } else {
                        urlView.getURL().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_DECIMAL:
                    DecimalView decimalView = new DecimalView(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), decimalView);
                    ll_innerSubFormContainer.addView(decimalView.getDecimal());
                    if (loadControlObject.isInvisible()) {
                        decimalView.getDecimal().setVisibility(View.GONE);
                    } else {
                        decimalView.getDecimal().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_PASSWORD:
                    Password password = new Password(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), password);
                    ll_innerSubFormContainer.addView(password.getPassword());
                    if (loadControlObject.isInvisible()) {
                        password.getPassword().setVisibility(View.GONE);
                    } else {
                        password.getPassword().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_CURRENCY:
                    Currency currency = new Currency(context, loadControlObject, true, pos, controlObject.getControlName().trim());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), currency);
                    ll_innerSubFormContainer.addView(currency.getCurrency());
                    if (loadControlObject.isInvisible()) {
                        currency.getCurrency().setVisibility(View.GONE);
                    } else {
                        currency.getCurrency().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_DYNAMIC_LABEL:
                    DynamicLabel dynamicLabel = new DynamicLabel(context, loadControlObject, true, pos, controlObject.getControlName());
                    Log.d(TAG, "loadControlSGDynamic: " + loadControlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), dynamicLabel);
                    ll_innerSubFormContainer.addView(dynamicLabel.getDynamicLabel());
                    if (loadControlObject.isInvisible()) {
                        dynamicLabel.getDynamicLabel().setVisibility(View.GONE);
                    } else {
                        dynamicLabel.getDynamicLabel().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm() && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        params.width = -1;
                        params.height = -1;
                        if (ll_innerSubFormContainer.getChildCount() == 1) {
                            ll_innerSubFormContainer.getChildAt(0).setLayoutParams(params);
                        } else {
                            ll_innerSubFormContainer.getChildAt(2).setLayoutParams(params);
                        }
//                        if (loadControlObject.isInvisible()) {
//                            ll_innerSubFormContainer.setVisibility(View.GONE);
//                        }

                        dynamicLabel.getLl_main_inside().setLayoutParams(params);
                        controlAlignments(AppConstants.uiLayoutPropertiesStatic, dynamicLabel.getLl_main_inside()); // RedNote
                    }
                    if (loadControlObject.isOnClickEventExists()) {
                        Log.d(TAG, "hash_Onclick_EventObjects: " + loadControlObject.getControlName());
                        Control_EventObject onclick_control_EventObject = loadControlObject.getOnClickEventObject();
                        hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onclick_control_EventObject);
                    }
                    //dynamicLabel.setValue(val);
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_IMAGE:
//                    Image imageView = new Image(context, loadControlObject, false, 0, "", "",null);
                    Log.d(TAG, "subform_pos: " + pos);
                    Image imageView = new Image(context, loadControlObject, true, pos, controlObject.getControlName(), "", null);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), imageView);

//                    ll_innerSubFormContainer.addView(imageView.getImageView());
                    if (controlObject.isUIFormNeededSubForm() && ll_innerSubFormContainer != null) {
                        if (loadControlObject.getDisplayNameAlignment() != null && !loadControlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                            android.view.ViewGroup.LayoutParams layoutParamsMainInside = imageView.getLLMainInside().getLayoutParams();
                            layoutParamsMainInside.width = -1;
                            layoutParamsMainInside.height = -1;
                            imageView.getLLMainInside().setLayoutParams(layoutParamsMainInside);
                            imageView.getLLMainInside().setBackgroundColor(ContextCompat.getColor(context, R.color.transparent_color));
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
                            }
                            android.view.ViewGroup.LayoutParams layoutParamsSingle = imageView.getLl_single_Image().getLayoutParams();
                            layoutParamsSingle.width = -1;
                            layoutParamsSingle.height = -1;

                            imageView.getLl_single_Image().setLayoutParams(layoutParamsSingle);
                            imageView.getLl_single_Image().setBackgroundColor(ContextCompat.getColor(context, R.color.transparent_color));

                            android.view.ViewGroup.LayoutParams layoutParamsImg = imageView.getMainImageView().getLayoutParams();
                            if (loadControlObject.getDisplayNameAlignment().equalsIgnoreCase("10")) {
                                layoutParamsImg.width = pxToDP(40);
                                layoutParamsImg.height = pxToDP(40);
                            } else {
//                                layoutParamsImg.width = -2;
//                                layoutParamsImg.height = -2;
                            }
                            if (controlUIProperties != null && controlUIProperties.getTypeOfWidth() != null
                                    && !controlUIProperties.getTypeOfWidth().isEmpty() && controlUIProperties.getTypeOfWidth().equalsIgnoreCase(context.getString(R.string.Custom_Width))) {
//                                layoutParamsImg.width = improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
//                                layoutParamsImg.height = improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                                layoutParamsImg.width = pxToDP(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                layoutParamsImg.height = pxToDP(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                            }

                            imageView.getMainImageView().setLayoutParams(layoutParamsImg);
//                            imageView.getMainImageView().setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        } else {
                            imageView.getMainImageView().setScaleType(ImageView.ScaleType.CENTER);
                        }
                        ll_innerSubFormContainer.addView(imageView.getImageView());
                    } else {
                        ll_innerSubFormContainer.addView(imageView.getImageView());
                    }
                    if (loadControlObject.isInvisible()) {
                        imageView.getImageView().setVisibility(View.GONE);
                    } else {
                        imageView.getImageView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    //For Set Value To Image
                    //ImproveHelper.loadImage_new(context, val, imageView.mainImageView, false, controlObject.getImageData());
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }

                    break;
                case CONTROL_TYPE_BUTTON:
                    Button button = new Button(context, loadControlObject, true, pos, controlObject.getControlName(), appname);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), button);
//                    ll_innerSubFormContainer.addView(button.getButton());
                    if (controlObject.isUIFormNeededSubForm() && ll_innerSubFormContainer != null) {
                        button.getButtonView().setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        button.getbtn_main().setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        button.getll_button().setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        ll_innerSubFormContainer.addView(button.getButton());
                        button.getButton().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT));
                    } else {
                        ll_innerSubFormContainer.addView(button.getButton());
                    }

                    if (loadControlObject.isInvisible()) {
                        button.getButton().setVisibility(View.GONE);
                    } else {
                        button.getButton().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnClickEventExists()) {
                        Control_EventObject onclick_control_EventObject = loadControlObject.getOnClickEventObject();
                        hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onclick_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_AUTO_NUMBER:
                    AutoNumber autoNumber = new AutoNumber(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), autoNumber);
                    ll_innerSubFormContainer.addView(autoNumber.getAutoNumber());
                    if (loadControlObject.isInvisible()) {
                        autoNumber.getAutoNumber().setVisibility(View.GONE);
                    } else {
                        autoNumber.getAutoNumber().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_AUTO_GENERATION:

                    isAutoNumbersAvaliable = true;

                    try {
                        for (int i = 0; i < jArrayAutoIncrementControls.length(); i++) {
                            JSONObject jsonObject = jArrayAutoIncrementControls.getJSONObject(i);
                            if (jsonObject.getString("ControlName").contentEquals(loadControlObject.getControlName())) {
                                isAutoNumbeStatusAlreadyAdded = true;
                                break;
                            }
                        }
                        if (!isAutoNumbeStatusAlreadyAdded) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("ControlName", loadControlObject.getControlName());
                            jsonObject.put("Prefix", loadControlObject.getPreFixValue());
                            jsonObject.put("Suffix", loadControlObject.getSuffixValue());
                            if (loadControlObject.getSuffix1Value() != null && !loadControlObject.getSuffix1Value().contentEquals("")) {
                                jsonObject.put("Suffix1", loadControlObject.getSuffix1Value());
                            }
                            jArrayAutoIncrementControls.put(jsonObject);
                        }
                    } catch (JSONException e) {
//                    e.printStackTrace();
                        Log.d(TAG, "loadControlAutoGenException: " + e);
                    }
                    LinearLayout linearLayoutANG = new LinearLayout(context);
                    linearLayoutANG.setVisibility(View.GONE);
                    linearLayoutANG.setTag(loadControlObject.getControlName());
                    ll_innerSubFormContainer.addView(linearLayoutANG);
                    break;
                case CONTROL_TYPE_RATING:
                    Rating rating = new Rating(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), rating);
                    ll_innerSubFormContainer.addView(rating.getRatingView());
                    if (loadControlObject.isInvisible()) {
                        rating.getRatingView().setVisibility(View.GONE);
                    } else {
                        rating.getRatingView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm() && ll_innerSubFormContainer != null) {
                       /* rating.getRatingBar().setScaleX(0.5f);
                        rating.getRatingBar().setScaleY(0.5f);*/
                        rating.getLl_rating().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        rating.getLl_control_ui().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        rating.getLl_main_inside().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    }
                    controlAlignments(AppConstants.uiLayoutPropertiesStatic, rating.getLl_rating()); // RedNotice
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_GPS:
                    Gps_Control controlGPS = new Gps_Control(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), controlGPS);
                    ll_innerSubFormContainer.addView(controlGPS.getControlGPSView());
                    if (loadControlObject.isInvisible()) {
                        controlGPS.getControlGPSView().setVisibility(View.GONE);
                    } else {
                        controlGPS.getControlGPSView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_BAR_CODE:
                    BarCode barcode = new BarCode(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), barcode);
                    ll_innerSubFormContainer.addView(barcode.getBarCode());
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_QR_CODE:
                    QRCode qrCode = new QRCode(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), qrCode);
                    ll_innerSubFormContainer.addView(qrCode.getQRCode());
                    if (loadControlObject.isInvisible()) {
                        qrCode.getQRCode().setVisibility(View.GONE);
                    } else {
                        qrCode.getQRCode().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;

                case CONTROL_TYPE_DATA_CONTROL:

                    loadItems(loadControlObject);
                    DataControl dataControl = new DataControl(context, loadControlObject, strUserLocationStructure, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), dataControl);
                    ll_innerSubFormContainer.addView(dataControl.getDataControl());

                    if (loadControlObject.isInvisible()) {
                        dataControl.getDataControl().setVisibility(View.GONE);
                    } else {
                        dataControl.getDataControl().setVisibility(View.VISIBLE);
                    }

                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_MAP:

                    MapControl mapControl = new MapControl(context, loadControlObject, true, pos, controlObject.getControlName(), dataCollectionObject.isUIFormNeeded());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), mapControl);


              /*  if (dataCollectionObject.isUIFormNeeded() && ll_preViewContainer != null) {
                    ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    mapControl.ll_mapView_main().setLayoutParams(params);
                    ll_preViewContainer.addView(mapControl.getMapControlLayout());
                } else {*/
                    ll_innerSubFormContainer.addView(mapControl.getMapControlLayout());

                    //}

                    if (loadControlObject.isInvisible()) {
                        mapControl.getMapControlLayout().setVisibility(View.GONE);
                    } else {
                        mapControl.getMapControlLayout().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnClickEventExists()) {
                        Control_EventObject onclick_control_EventObject = loadControlObject.getOnClickEventObject();
                        hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onclick_control_EventObject);
                    }

                    mapControl.setMap(mapControl.getMapView());
                    mapControl.getMapView().onResume();
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;

                case CONTROL_TYPE_USER:

                    UserControl userControl = new UserControl(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), userControl);

                    ll_innerSubFormContainer.addView(userControl.getUserControlView());


                    if (loadControlObject.isInvisible()) {
                        userControl.getUserControlView().setVisibility(View.GONE);
                    } else {
                        userControl.getUserControlView().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;
                case CONTROL_TYPE_POST:

                    PostControl postControl = new PostControl(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), postControl);

                    ll_innerSubFormContainer.addView(postControl.getPostControlView());

                    if (loadControlObject.isInvisible()) {
                        postControl.getPostControlView().setVisibility(View.GONE);
                    } else {
                        postControl.getPostControlView().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isUIFormNeededSubForm && AppConstants.uiLayoutPropertiesStatic.getAliasName() != null) {
                        ControlUiSettings controlUiSettings = new ControlUiSettings(loadControlObject, AppConstants.uiLayoutPropertiesStatic, controlUIProperties, List_ControlClassObjects, context);
                        controlUiSettings.setControlUiSettings();
                    }
                    break;


            }

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "loadControl", e);
        }
    }

    private List<Item> getListItems(ControlObject loadControlObject) {
        String mappedValue = "", mappedId = "";
        List<String> values = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        try {
            for (int i = 0; i < list_Form_OutParams.size(); i++) {
                if (!list_Form_OutParams.get(i).isOutParam_Delete() &&
                        list_Form_OutParams.get(i).getOutParam_Name().contentEquals(loadControlObject.getControlName())) {

                    if (outputData.containsKey(list_Form_OutParams.get(i).getOutParam_Mapped_ID())) {
                        values = outputData.get(list_Form_OutParams.get(i).getOutParam_Mapped_ID());
                    }

                } else if (!list_Form_OutParams.get(i).isOutParam_Delete() &&
                        (list_Form_OutParams.get(i).getOutParam_Name().contentEquals(loadControlObject.getControlName() + "_id") ||
                                list_Form_OutParams.get(i).getOutParam_Name().contentEquals(loadControlObject.getControlName() + "ID") ||
                                list_Form_OutParams.get(i).getOutParam_Name().contentEquals(loadControlObject.getControlName() + "id"))) {

                    if (outputData.containsKey(list_Form_OutParams.get(i).getOutParam_Mapped_ID())) {
                        ids = outputData.get(list_Form_OutParams.get(i).getOutParam_Mapped_ID());
                    }

                }
            }
            if (values.size() > 0 && ids.size() > 0) {
                for (int i = 0; i < values.size(); i++) {
                    Item item = new Item();
                    item.setId(ids.get(i));
                    item.setValue(values.get(i));
                    items.add(item);

                }
            } else if (values.size() > 0 && ids.size() == 0) {
                for (int i = 0; i < values.size(); i++) {
                    Item item = new Item();
                    item.setId(values.get(i));
                    item.setValue(values.get(i));
                    items.add(item);

                }
            } else if (values.size() == 0 && ids.size() > 0) {
                for (int i = 0; i < ids.size(); i++) {
                    Item item = new Item();
                    item.setId(ids.get(i));
                    item.setValue(ids.get(i));
                    items.add(item);

                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getListItems", e);
        }
        return items;
    }

    private void setPadding(View view) {
      /*  ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams) view.getLayoutParams();

        view.setLayoutParams(params);
        view.requestLayout();*/
        try {
            view.setPadding(0, pxToDP(0), 0, pxToDP(-5));
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setPadding", e);
        }
    }

    public int pxToDP(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics());
    }

    private boolean controlNotExists(String subformName, String controlName) {
        boolean flag = true;
        try {
            JSONArray jsonArray = AppConstants.subformCheckListData.get(subformName);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String key = jsonObject.getString("ColumnName");
                if (key.equalsIgnoreCase(controlName)) {
                    flag = false;
                    break;
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "controlNotExists", e);
        }
        return flag;

    }

    private void loadItems(ControlObject controlObject) {
        try {
            String textFile = "DC_" + controlObject.getDataControlName() + ".txt";

            String line = ImproveHelper.readTextFileFromSD(context, textFile, orgId);

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

                    String jsonDataControlItemsGet = PrefManger.getSharedPreferencesString(context, controlObject.getControlName(), "");

//                Type type = new TypeToken<List<New_DataControl_Bean>>() {
//                }.getType();
//                List<New_DataControl_Bean> dataControl_beans = gson.fromJson(jsonDataControlItemsGet, type);

//                if (dataControl_beans == null) {

                    Log.d(TAG, "loadItems_in: " + "IN");
                    String jsonDataControlItems2 = gson.toJson(dataControlBeanList);
                    PrefManger.putSharedPreferencesString(context, controlObject.getControlName(), jsonDataControlItems2);
//                }

                } catch (JSONException E) {
                    System.out.println("Error==" + E);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "loadItems", e);
        }
    }

    private void primaryLayoutData(UIPrimaryLayoutModelClass uiPrimaryLayoutModelClass, LinearLayout linearLayout, LinkedHashMap<String, Object> new_list_ControlClassObjects, int position) {
        if (uiPrimaryLayoutModelClass != null) {
           /* if(uiPrimaryLayoutModelClass.getSubformName()!=null){
                Log.d(TAG, "primaryLayoutData: "+uiPrimaryLayoutModelClass.getSubformName());
                if(!AppConstants.subformWithUI.contains(uiPrimaryLayoutModelClass.getSubformName())) {
                    AppConstants.subformWithUI.add(uiPrimaryLayoutModelClass.getSubformName());
                }
            }*/
            setScreenType(uiPrimaryLayoutModelClass);
            UILayoutProperties primaryLayoutProperties = uiPrimaryLayoutModelClass.getPrimaryLayoutProperties();

            if (primaryLayoutProperties != null) {

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(pxToDP(primaryLayoutProperties.getMarginLeft()), pxToDP(primaryLayoutProperties.getMarginTop()),
                        pxToDP(primaryLayoutProperties.getMarginRight()), pxToDP(primaryLayoutProperties.getMarginBottom()));
                linearLayout.setLayoutParams(layoutParams);

                GradientDrawable.Orientation gradientAlignment = null;
                linearLayout.setPadding(primaryLayoutProperties.getPaddingLeft(), primaryLayoutProperties.getPaddingTop(),
                        primaryLayoutProperties.getPaddingRight(), primaryLayoutProperties.getPaddingBottom());

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
                            linearLayout.setBackgroundResource(0);
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
                        addLayoutToPrimary(linearLayout, layoutPropertiesList.get(i), uiLayoutProperties,
                                mappingControlLayout, new_list_ControlClassObjects, controlUIProperties, position);
                    }


                    //Inflate LayoutViews
//                        UILayoutProperties uiLayoutProperties = layoutPropertiesList.get(1).getUiLayoutProperties();
//                        MappingControlModel mappingControlLayout = layoutPropertiesList.get(1).getLayoutControl();
//                        addLayoutToPrimary(linearLayout, layoutPropertiesList.get(1), uiLayoutProperties, mappingControlLayout,new_list_ControlClassObjects);

                }
            }
        }
//        dismissProgressDialog();
    }

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

    public void addLayoutToPrimary(LinearLayout ll_innerSubFormContainer, LayoutProperties layoutProperties, UILayoutProperties uiLayoutProperties, MappingControlModel mappingControlModel, LinkedHashMap<String, Object> New_list_ControlClassObjects, ControlUIProperties controlUIProperties, int position) {
        final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rView = lInflater.inflate(R.layout.add_layout, null);
        LinearLayout layout_addMain = rView.findViewById(R.id.layout_addMain);
//        CustomTextView tv_subLayoutId = rView.findViewById(R.id.tv_subLayoutId);
//        tv_subLayoutId.setText(uiLayoutProperties.getAliasName());
        layout_addMain.setTag(layoutProperties.getPosition());
        ll_innerSubFormContainer.addView(rView);
//        LinearLayout.LayoutParams layoutParamsC = new LinearLayout.LayoutParams(screenWidth, screenHeight);
//        llMainContainer.setLayoutParams(layoutParamsC);
        int viewW = LinearLayout.LayoutParams.MATCH_PARENT;
//        int viewW = LinearLayout.LayoutParams.WRAP_CONTENT;
        if (uiLayoutProperties.getWidthFixedVariable() != null && uiLayoutProperties.getWidthFixedVariable().equalsIgnoreCase(context.getString(R.string.fixed_Width))) {
            viewW = screenWidth;
        }
        int viewH = 0;
        if (isScreenFit) {
            viewH = uiLayoutProperties.getHeight() * screenHeight / 100;
        } else {

            if (uiLayoutProperties.getWrap_or_dp() != null && !uiLayoutProperties.getWrap_or_dp().isEmpty()) {
                Log.d(TAG, "LayoutWrapOrDp: " + uiLayoutProperties.getWrap_or_dp());
                if (uiLayoutProperties.getWrap_or_dp().equalsIgnoreCase(context.getString(R.string.both_wrap_dp))) {
//                    viewH = LinearLayout.LayoutParams.WRAP_CONTENT;
//                    MappingControlModel mappingControlModelLayout = layoutProperties.getLayoutControl();
//                    bothWrapContentAndDp(mappingControlModel.getControlType(), mappingControlModel.getControlName());
                } else if (uiLayoutProperties.getWrap_or_dp().equalsIgnoreCase(context.getString(R.string.only_wrap))) {
                    viewH = LinearLayout.LayoutParams.WRAP_CONTENT;
                    Log.d(TAG, "addLayoutToPrimary: ");
                } else if (uiLayoutProperties.getWrap_or_dp().equalsIgnoreCase(context.getString(R.string.only_dp))) {
                    viewH = pxToDP(uiLayoutProperties.getHeight());
                } else {
                    viewH = LinearLayout.LayoutParams.WRAP_CONTENT;
                }
            } else {
                viewH = LinearLayout.LayoutParams.WRAP_CONTENT;
            }

//            viewH = pxToDP(uiLayoutProperties.getHeight());
        }
        viewW = viewW - (pxToDP(uiLayoutProperties.getMarginLeft()) + pxToDP(uiLayoutProperties.getMarginRight()));
        viewH = viewH - (pxToDP(uiLayoutProperties.getMarginTop()) + pxToDP(uiLayoutProperties.getMarginBottom()));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(viewW, viewH);
        layoutParams.setMargins(pxToDP(uiLayoutProperties.getMarginLeft()), pxToDP(uiLayoutProperties.getMarginTop()),
                pxToDP(uiLayoutProperties.getMarginRight()), pxToDP(uiLayoutProperties.getMarginBottom()));
        layout_addMain.setLayoutParams(layoutParams);
//        overFlowHorizontal(uiLayoutProperties, hsOverFlow);

        applyUiProperties(layout_addMain, uiLayoutProperties, mappingControlModel, rView, New_list_ControlClassObjects, false, controlUIProperties, position);

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

            for (int j = 0; j < subLayoutPropertiesList.size(); j++) {
                // Inflate SubLayout Views
                UILayoutProperties uiSubLayoutProperties = subLayoutPropertiesList.get(j).getUiLayoutProperties();
                MappingControlModel mappingControlModelSub = subLayoutPropertiesList.get(j).getLayoutControl();
                ControlUIProperties controlUIPropertiesSL = mappingControlModelSub.getControlUIProperties();
                addSubLayoutToLayout(ll_addSubLayoutContainer, layoutProperties, uiSubLayoutProperties, mappingControlModelSub, viewH, New_list_ControlClassObjects, controlUIPropertiesSL, position);
            }
        }

    }

    public void addSubLayoutToLayout(LinearLayout ll_addSubLayoutContainer, LayoutProperties parentLayoutProperties, UILayoutProperties uiSubLayoutProperties, MappingControlModel mappingControlModelSub, int layoutHeight, LinkedHashMap<String, Object> new_list_ControlClassObjects, ControlUIProperties controlUIProperties, int position) {
        final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rView = lInflater.inflate(R.layout.add_sub_layout, null);
        final LinearLayout ll_subLayoutMain = rView.findViewById(R.id.ll_subLayoutMain);

        ll_addSubLayoutContainer.addView(rView);
        ViewTreeObserver vto = ll_subLayoutMain.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                int w = 0, h = 0;
                ll_subLayoutMain.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int viewW = 0;
                int viewH = 0;
                int w = 0;
                int h = 0;

                if (isScreenFit) {
                    h = layoutHeight;
                    w = screenWidth;
                    viewW = (uiSubLayoutProperties.getWidth() * w) / 100;
                    viewH = (uiSubLayoutProperties.getHeight() * h) / 100;
                } else {
                    UILayoutProperties uiLayoutProperties = parentLayoutProperties.getUiLayoutProperties();
                    if (uiLayoutProperties != null && uiLayoutProperties.getWidthFixedVariable() != null
                            && !uiLayoutProperties.getWidthFixedVariable().isEmpty()
                            && uiLayoutProperties.getWidthFixedVariable().equalsIgnoreCase(context.getString(R.string.fixed_Width))) {
                        h = layoutHeight;
                        w = screenWidth;
                        viewW = (uiSubLayoutProperties.getWidth() * w) / 100;
                        viewH = (uiSubLayoutProperties.getHeight() * h) / 100;
                        Log.d(TAG, "onGlobalLayout1: " + h);
                        Log.d(TAG, "onGlobalLayout2: " + w);
                        Log.d(TAG, "onGlobalLayout3: " + viewW + "-" + uiSubLayoutProperties.getWidth());
                        Log.d(TAG, "onGlobalLayout4: " + viewH + "-" + uiSubLayoutProperties.getHeight());

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
                Log.d(TAG, "addSubLayoutToLayoutWH: " + viewW + "-" + viewH);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(viewW, viewH);
                layoutParams.setMargins(pxToDP(uiSubLayoutProperties.getMarginLeft()), pxToDP(uiSubLayoutProperties.getMarginTop()),
                        pxToDP(uiSubLayoutProperties.getMarginRight()), pxToDP(uiSubLayoutProperties.getMarginBottom()));
                ll_subLayoutMain.setLayoutParams(layoutParams);


                if (uiSubLayoutProperties.getInsideAlignment() != null) {
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
        applyUiProperties(ll_subLayoutMain, uiSubLayoutProperties, mappingControlModelSub, null, new_list_ControlClassObjects, true, controlUIProperties, position);

    }

    public void applyUiProperties(LinearLayout ll_container, UILayoutProperties uiLayoutProperties, MappingControlModel mappingControlModel, View rView, LinkedHashMap<String, Object> New_list_ControlClassObjects, boolean isSubLayout, ControlUIProperties controlUIProperties, int position) {

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

            ll_container.setPadding(pxToDP(uiLayoutProperties.getPaddingLeft()), pxToDP(uiLayoutProperties.getPaddingTop()),
                    pxToDP(uiLayoutProperties.getPaddingRight()), pxToDP(uiLayoutProperties.getPaddingBottom()));


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
                        ll_container.setBackgroundResource(0);
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
            ControlObject controlObject = globalControlObjects.get(mappingControlModel.getControlName());

//            SubformView mainActivity = new SubformView(context,New_list_ControlClassObjects,uiLayoutProperties,controlUIProperties);
//            mainActivity.loadControl(controlObject,
//                    mappingControlModel.getControlType(),
//                    ll_container, New_list_ControlClassObjects,
//                    position, uiLayoutProperties, controlUIProperties);

            loadControl(controlObject, mappingControlModel.getControlType(),
                    ll_container, New_list_ControlClassObjects,
                    position, uiLayoutProperties, controlUIProperties);

        }
    }

    private void controlAlignments(UILayoutProperties uiLayoutProperties, LinearLayout linearLayout) {
        if (uiLayoutProperties != null && linearLayout != null) {
            if (uiLayoutProperties.getControlVerticalAlignment() != null && !uiLayoutProperties.getControlVerticalAlignment().isEmpty()
                    && uiLayoutProperties.getControlHorizontalAlignment() != null && !uiLayoutProperties.getControlHorizontalAlignment().isEmpty()) {
                Log.d(TAG, "controlAlignments: " + uiLayoutProperties.getControlVerticalAlignment() + " - " + uiLayoutProperties.getControlHorizontalAlignment());
                if (uiLayoutProperties.getControlVerticalAlignment().equalsIgnoreCase(context.getString(R.string.top))
                        && uiLayoutProperties.getControlHorizontalAlignment().equalsIgnoreCase(context.getString(R.string.left))) {
                    linearLayout.setGravity(Gravity.TOP | Gravity.LEFT);
                } else if (uiLayoutProperties.getControlVerticalAlignment().equalsIgnoreCase(context.getString(R.string.top))
                        && uiLayoutProperties.getControlHorizontalAlignment().equalsIgnoreCase(context.getString(R.string.right))) {
                    linearLayout.setGravity(Gravity.TOP | Gravity.RIGHT);
                } else if (uiLayoutProperties.getControlVerticalAlignment().equalsIgnoreCase(context.getString(R.string.top))
                        && uiLayoutProperties.getControlHorizontalAlignment().equalsIgnoreCase(context.getString(R.string.align_center))) {
                    linearLayout.setGravity(Gravity.TOP | Gravity.CENTER);
                } else if (uiLayoutProperties.getControlVerticalAlignment().equalsIgnoreCase(context.getString(R.string.bottom))
                        && uiLayoutProperties.getControlHorizontalAlignment().equalsIgnoreCase(context.getString(R.string.left))) {
                    linearLayout.setGravity(Gravity.BOTTOM | Gravity.LEFT);
                } else if (uiLayoutProperties.getControlVerticalAlignment().equalsIgnoreCase(context.getString(R.string.bottom))
                        && uiLayoutProperties.getControlHorizontalAlignment().equalsIgnoreCase(context.getString(R.string.right))) {
                    linearLayout.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
                } else if (uiLayoutProperties.getControlVerticalAlignment().equalsIgnoreCase(context.getString(R.string.bottom))
                        && uiLayoutProperties.getControlHorizontalAlignment().equalsIgnoreCase(context.getString(R.string.align_center))) {
                    linearLayout.setGravity(Gravity.BOTTOM | Gravity.CENTER);
                } else if (uiLayoutProperties.getControlVerticalAlignment().equalsIgnoreCase(context.getString(R.string.middle))
                        && uiLayoutProperties.getControlHorizontalAlignment().equalsIgnoreCase(context.getString(R.string.left))) {
                    linearLayout.setGravity(Gravity.CENTER | Gravity.LEFT);
                } else if (uiLayoutProperties.getControlVerticalAlignment().equalsIgnoreCase(context.getString(R.string.middle))
                        && uiLayoutProperties.getControlHorizontalAlignment().equalsIgnoreCase(context.getString(R.string.right))) {
                    linearLayout.setGravity(Gravity.CENTER | Gravity.RIGHT);
                } else if (uiLayoutProperties.getControlVerticalAlignment().equalsIgnoreCase(context.getString(R.string.middle))
                        && uiLayoutProperties.getControlHorizontalAlignment().equalsIgnoreCase(context.getString(R.string.align_center))) {
                    linearLayout.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                }
            }

        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_main_inside, ll_innerSubForm, ll_innerSubFormContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            ll_main_inside = itemView.findViewById(R.id.ll_main_inside);
            ll_innerSubForm = itemView.findViewById(R.id.ll_innerSubForm);
            ll_innerSubFormContainer = itemView.findViewById(R.id.ll_innerSubFormContainer);
            ll_innerSubFormContainer.setTag(controlObject.getControlName());
            itemView.setTag(controlObject.getControlName());

            if (controlUIProperties != null) {
                setControlUIProperties(ll_innerSubForm);
                controlAlignments(AppConstants.uiLayoutPropertiesStatic, ll_main_inside);
            }

            ll_innerSubFormContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setTag(controlObject.getControlName());
                    if (controlObject.isOnClickEventExists() && !AppConstants.Initialize_Flag) {
                        if (AppConstants.EventCallsFrom == 1) {
                            AppConstants.EventFrom_subformOrNot = false;
                            AppConstants.Current_ClickorChangeTagName = controlObject.getControlName();
                              /*  if (true) {
                                    AppConstants.SF_Container_position = SubformPos;
                                    AppConstants.Current_ClickorChangeTagName = SubformName;
                                }*/
                            if (AppConstants.GlobalObjects != null) {
                                AppConstants.GlobalObjects.setCurrent_GPS("");
                            }
                            ((MainActivity) context).ClickEvent(view);
                        }
                    }
                }
            });


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
        }


    }

    private void setControlUIProperties(LinearLayout ll_innerSubForm) {
        LinearLayout.LayoutParams ll_innerSubFormParams = (LinearLayout.LayoutParams) ll_innerSubForm.getLayoutParams();
        if (controlUIProperties.getTypeOfWidth() != null) {
            switch (controlUIProperties.getTypeOfWidth()) {
                case AppConstants.CUSTOM_WIDTH:
                    ll_innerSubFormParams.width = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                    break;
                case AppConstants.MATCH_PARENT:
                    ll_innerSubFormParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    break;
            }
        }
        if (controlUIProperties.getTypeOfHeight() != null) {
            switch (controlUIProperties.getTypeOfHeight()) {
                case AppConstants.CUSTOM_HEIGHT:
                    ll_innerSubFormParams.height = pxToDPControlUI(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                    break;
                case AppConstants.MATCH_PARENT:
                    ll_innerSubFormParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
                    break;
            }

            ll_innerSubFormParams.setMargins(uiLayoutPropertiesSF.getMarginLeft(), uiLayoutPropertiesSF.getMarginTop(),
                    uiLayoutPropertiesSF.getMarginRight(), uiLayoutPropertiesSF.getMarginBottom());
            ll_innerSubForm.setLayoutParams(ll_innerSubFormParams);
            ll_innerSubForm.setPadding(uiLayoutPropertiesSF.getPaddingLeft(), uiLayoutPropertiesSF.getPaddingTop(), uiLayoutPropertiesSF.getPaddingRight(), uiLayoutPropertiesSF.getPaddingBottom());
        }


    }


    public class fileClick implements View.OnClickListener {
        int pos;
        ControlObject loadControlObject;

        public fileClick(int pos, ControlObject loadControlObject) {
            this.pos = pos;
            this.loadControlObject = loadControlObject;
        }

        @Override
        public void onClick(View v) {
//            Log.d(TAG, "SubFormViewOnClick: " + v.getId());
            switch (loadControlObject.getControlType()) {

                case CONTROL_TYPE_TEXT_INPUT:

                    AppConstants.Current_ClickorChangeTagName = controlObject.getControlName().trim();
                    AppConstants.SF_ClickorChangeTagName = loadControlObject.getControlName().trim();
                    AppConstants.SF_Container_position = pos;

                    final TextInput textInput = (TextInput) subform_List_ControlClassObjects.get(pos).get(loadControlObject.getControlName());

                    //////////////////////////
                    if (loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.VISIBLE); // taptext
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        if (loadControlObject.isReadFromBarcode()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_barcode_drawable));
                            textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                        if (AppConstants.EventCallsFrom == 1) {
                                            ((MainActivity) context).ChangeEvent(v);
                                        }
                                    }

                                    textInput.tQRBarCode();
                                }
                            });
                        } else if (loadControlObject.isReadFromQRCode()) {
                            textInput.gettap_text().setVisibility(View.VISIBLE);
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_qrcode_drawable));
                            textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                        if (AppConstants.EventCallsFrom == 1) {
                                            ((MainActivity) context).ChangeEvent(v);
                                        }
                                    }

                                    textInput.tQRBarCode();
                                }
                            });
                        } else if (loadControlObject.isVoiceText()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));
                            textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                        if (AppConstants.EventCallsFrom == 1) {
                                            ((MainActivity) context).ChangeEvent(v);
                                        }
                                    }

                                    textInput.getSpeechInput(true, SF_REQUEST_SPEECH_TO_TEXT);
                                }
                            });
                        } else if (loadControlObject.isCurrentLocation()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_my_location_black_24dp));
                            textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                        if (AppConstants.EventCallsFrom == 1) {
                                            ((MainActivity) context).ChangeEvent(v);
                                        }
                                    }

//                                context.startActivityForResult(new Intent(context, GPSActivity.class), REQUEST_CURRENT_LOCATION);
                                    textInput.tCurrentLocation(SF_REQUEST_CURRENT_LOCATION);
                                }
                            });
                        } else if (loadControlObject.isGoogleLocationSearch()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_current_location));
                            textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                        if (AppConstants.EventCallsFrom == 1) {
                                            ((MainActivity) context).ChangeEvent(v);
                                        }
                                    }

                                    textInput.initAutoCompleteTextView();
                                }
                            });
                        }
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getCustomEditText().setText(controlObject.getDefaultValue());
                    } else if (loadControlObject.isReadFromBarcode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE);
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_barcode_drawable));
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                ((MainActivity) context).ChangeEvent(v);
                            }
                        }

                        textInput.tQRBarCode();

                    } else if (loadControlObject.isReadFromQRCode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE);
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_qrcode_drawable));
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                ((MainActivity) context).ChangeEvent(v);
                            }
                        }

                        textInput.tQRBarCode();
                    } else if (loadControlObject.isVoiceText() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE);
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                ((MainActivity) context).ChangeEvent(v);
                            }
                        }

                        textInput.getSpeechInput(true, SF_REQUEST_SPEECH_TO_TEXT);
                    } else if (loadControlObject.isCurrentLocation() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE);
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_current_location));
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                ((MainActivity) context).ChangeEvent(v);
                            }
                        }
                        textInput.tCurrentLocation(SF_REQUEST_CURRENT_LOCATION);

                    } else if (loadControlObject.isReadFromBarcode()) {
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                ((MainActivity) context).ChangeEvent(v);
                            }
                        }

                        textInput.tQRBarCode();

/*
                        textInput.getLl_tap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setText("Tap here to Scan BarCode");
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_barcode_drawable));
                        textInput.gettap_text().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                    if (AppConstants.EventCallsFrom == 1) {
                                        ((MainActivity) context).ChangeEvent(v);
                                    }
                                }

                                textInput.tQRBarCode();
                            }
                        });
                        textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                    if (AppConstants.EventCallsFrom == 1) {
                                        ((MainActivity) context).ChangeEvent(v);
                                    }
                                }

                                textInput.tQRBarCode();
                            }
                        });
*/
                    } else if (loadControlObject.isReadFromQRCode()) {
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                ((MainActivity) context).ChangeEvent(v);
                            }
                        }

                        textInput.tQRBarCode();

/*
                        textInput.getLl_tap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setText("Tap here to Scan QRCode");
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_qrcode_drawable));
                        textInput.gettap_text().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                    if (AppConstants.EventCallsFrom == 1) {
                                        ((MainActivity) context).ChangeEvent(v);
                                    }
                                }

                                textInput.tQRBarCode();
                            }
                        });
                        textInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                    if (AppConstants.EventCallsFrom == 1) {
                                        ((MainActivity) context).ChangeEvent(v);
                                    }
                                }

                                textInput.tQRBarCode();
                            }
                        });
*/
                    } else if (loadControlObject.isVoiceText()) {
                        textInput.getLl_tap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setText("Tap here voice to text");
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));

                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                ((MainActivity) context).ChangeEvent(v);
                            }
                        }

                        textInput.getSpeechInput(false, SF_REQUEST_SPEECH_TO_TEXT);

                    } else if (loadControlObject.isCurrentLocation()) {
                        textInput.gettap_text().setText("Tap here to get Current Location");
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_my_location_black_24dp));
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                ((MainActivity) context).ChangeEvent(v);
                            }
                        }

                        textInput.tCurrentLocation(SF_REQUEST_CURRENT_LOCATION);

                    } else if (loadControlObject.isGoogleLocationSearch()) {

                        textInput.gettap_text().setText("Tap here to Search Location");
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_current_location));

                        textInput.setDefaultValueForSearch();

                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                ((MainActivity) context).ChangeEvent(v);
                            }
                        }

                        textInput.initAutoCompleteTextView();
                    }

                    //////////////////////////////////

                    break;

                case CONTROL_TYPE_FILE_BROWSING:

                    AppConstants.Current_ClickorChangeTagName = controlObject.getControlName().trim();
                    AppConstants.SF_ClickorChangeTagName = loadControlObject.getControlName().trim();
                    AppConstants.SF_Container_position = pos;

                    context.startActivityForResult(new Intent(context, FilePicker.class), SF_FILE_BROWSER_RESULT_CODE);

                    if (controlObject.isOnChangeEventExists()) {
                        if (AppConstants.EventCallsFrom == 1) {
//                                ChangeEvent(v);
                        }
                    }
                    break;

                case CONTROL_TYPE_CAMERA:

                    AppConstants.Current_ClickorChangeTagName = controlObject.getControlName().trim();
                    AppConstants.SF_ClickorChangeTagName = loadControlObject.getControlName().trim();
                    AppConstants.SF_Container_position = pos;

                    final Camera camera = (Camera) subform_List_ControlClassObjects.get(pos).get(loadControlObject.getControlName());

                    camera.getCameraClickView(SF_REQUEST_CAMERA_CONTROL_CODE, v);
/*
                    camera.getLLTapTextView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            camera.getCameraClickView(SF_REQUEST_CAMERA_CONTROL_CODE);
                        }
                    });
*/
                    camera.getReTake();
//                    context.startActivityForResult(new Intent(context, FilePicker.class), SF_REQUEST_CAMERA_CONTROL_CODE);

/*
                    camera.getReTake().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            camera.getCameraClickView(SF_REQUEST_CAMERA_CONTROL_CODE);
                        }
                    });
*/

                    if (controlObject.isOnChangeEventExists()) {
                        if (AppConstants.EventCallsFrom == 1) {
//                                ChangeEvent(v);
                        }
                    }
                    break;
                case CONTROL_TYPE_SIGNATURE:
                    AppConstants.Current_ClickorChangeTagName = controlObject.getControlName().trim();
                    AppConstants.SF_ClickorChangeTagName = loadControlObject.getControlName().trim();
                    AppConstants.SF_Container_position = pos;

                    final SignatureView signature = (SignatureView) subform_List_ControlClassObjects.get(pos).get(loadControlObject.getControlName());
                    signature.getLayoutUpload().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));
                    signature.getTv_clearSignature().setOnClickListener(new SubFormGridAdapter.fileClick(pos, loadControlObject));


                    break;
                case CONTROL_TYPE_VOICE_RECORDING:

                    AppConstants.Current_ClickorChangeTagName = controlObject.getControlName().trim();
                    AppConstants.SF_ClickorChangeTagName = loadControlObject.getControlName().trim();
                    AppConstants.SF_Container_position = pos;

                    final VoiceRecording voiceRecording = (VoiceRecording) subform_List_ControlClassObjects.get(pos).get(loadControlObject.getControlName());
                    final CountDownTimer[] yourCountDownTimer = new CountDownTimer[1];
                    final long[] MILLI_SEC = new long[1];
                    final long[] millis = new long[1];
                    voiceRecording.startRecordingClick().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }

                            voiceRecording.getStartVoiceRecording();
                        }
                    });
                    voiceRecording.getVoiceRecordingClick().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }

                            voiceRecording.getStopVoiceRecording();
                        }
                    });

//                    voiceRecording.getImageViewPlay().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
//                                if (AppConstants.EventCallsFrom == 1) {
//                                    AppConstants.GlobalObjects.setCurrent_GPS("");
//                                    ((MainActivity) context).ChangeEvent(v);
//                                }
//                            }
//                            voiceRecording.getPlayViewVoiceRecording();
//                        }
//                    });

//                    voiceRecording.getImageViewStop().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
//                                if (AppConstants.EventCallsFrom == 1) {
//                                    AppConstants.GlobalObjects.setCurrent_GPS("");
//                                    ((MainActivity) context).ChangeEvent(v);
//                                }
//                            }
//                            voiceRecording.getStopViewVoiceRecording();
//                        }
//                    });

                    voiceRecording.getLayoutAudioFileUpload().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }

                            voiceRecording.getVoiceUploadFile(v, SF_REQ_CODE_PICK_VOICE_REC);

                        }
                    });

                    break;

                case CONTROL_TYPE_VIDEO_RECORDING:

                    AppConstants.Current_ClickorChangeTagName = controlObject.getControlName().trim();
                    AppConstants.SF_ClickorChangeTagName = loadControlObject.getControlName().trim();
                    AppConstants.SF_Container_position = pos;

                    final VideoRecording videoRecording = (VideoRecording) subform_List_ControlClassObjects.get(pos).get(loadControlObject.getControlName());
//                    videoRecording.getTv_startVideoRecorder().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            videoRecording.getStartVideoRecorderClick(v, SF_REQUEST_VIDEO_RECORDING);
//                        }
//                    });
//
//                    videoRecording.getImageViewVideo().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            videoRecording.getVideoPlayPreview(v);
//                        }
//                    });
//                    videoRecording.getTv_videoFileUpload().setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            videoRecording.getVideoUploadFile(v, SF_REQ_CODE_PICK_ONLY_VIDEO_FILE);
//                        }
//                    });


                    videoRecording.getTv_startVideoRecorder().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            videoRecording.getStartVideoRecorderClick(v, SF_REQUEST_VIDEO_RECORDING, SF_REQ_CODE_PICK_ONLY_VIDEO_FILE);

                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ChangeEvent(v);

                                }
                            }
                        }
                    });

                    /*videoRecording.getTv_videoFileUpload().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            videoRecording.getVideoUploadFile(v, SF_REQ_CODE_PICK_ONLY_VIDEO_FILE);
                            videoRecording.getVideoUploadFile(v, SF_REQ_CODE_PICK_ONLY_VIDEO_FILE);

                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }
                        }
                    });*/

                    break;

            }
        }
    }


}