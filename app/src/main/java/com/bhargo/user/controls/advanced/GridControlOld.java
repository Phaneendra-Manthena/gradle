package com.bhargo.user.controls.advanced;

import static com.bhargo.user.MainActivity.AUTOCOMPLETE_REQUEST_CODE;
import static com.bhargo.user.MainActivity.initImageLoader;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUDIO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_GENERATION;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_NUMBER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_BAR_CODE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_BUTTON;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CALENDAR_EVENT;
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
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_LiveTracking;
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
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_TIME;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_URL_LINK;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_USER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_RECORDING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VOICE_RECORDING;
import static com.bhargo.user.utils.AppConstants.SF_FILE_BROWSER_RESULT_CODE;
import static com.bhargo.user.utils.AppConstants.SF_REQUEST_CAMERA_CONTROL_CODE;
import static com.bhargo.user.utils.AppConstants.SF_REQUEST_CURRENT_LOCATION;
import static com.bhargo.user.utils.AppConstants.SF_REQUEST_SIGNATURE_CONTROL_CODE;
import static com.bhargo.user.utils.AppConstants.SF_REQUEST_SPEECH_TO_TEXT;
import static com.bhargo.user.utils.AppConstants.SF_REQUEST_VIDEO_RECORDING;
import static com.bhargo.user.utils.AppConstants.SF_REQ_CODE_PICK_ONLY_VIDEO_FILE;
import static com.bhargo.user.utils.AppConstants.SF_REQ_CODE_PICK_VOICE_REC;
import static com.bhargo.user.utils.ImproveHelper.setViewDisable;
import static com.bhargo.user.utils.ImproveHelper.setViewDisableOrEnableDefault;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.Expression.ExpressionMainHelper;
import com.bhargo.user.Java_Beans.API_OutputParam_Bean;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.Control_EventObject;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.Java_Beans.GridColumnSettings;
import com.bhargo.user.Java_Beans.Item;
import com.bhargo.user.Java_Beans.New_DataControl_Bean;
import com.bhargo.user.Java_Beans.Param;
import com.bhargo.user.Java_Beans.QueryFilterField_Bean;
import com.bhargo.user.MainActivity;
import com.bhargo.user.R;
import com.bhargo.user.adapters.SubFormGridAdapter;
import com.bhargo.user.controls.data_controls.DataControl;
import com.bhargo.user.controls.standard.AudioPlayer;
import com.bhargo.user.controls.standard.AutoNumber;
import com.bhargo.user.controls.standard.Button;
import com.bhargo.user.controls.standard.Calendar;
import com.bhargo.user.controls.standard.CalendarEventControl;
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
import com.bhargo.user.controls.standard.Time;
import com.bhargo.user.controls.standard.UrlView;
import com.bhargo.user.controls.standard.VideoPlayer;
import com.bhargo.user.controls.standard.VideoRecording;
import com.bhargo.user.controls.standard.VoiceRecording;
import com.bhargo.user.controls.variables.UIVariables;
import com.bhargo.user.custom.CustomButton;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.SubFormDeleteInterface;
import com.bhargo.user.pojos.EditOrViewColumns;
import com.bhargo.user.uisettings.pojos.ControlUIProperties;
import com.bhargo.user.uisettings.pojos.LayoutProperties;
import com.bhargo.user.uisettings.pojos.MappingControlModel;
import com.bhargo.user.uisettings.pojos.UILayoutProperties;
import com.bhargo.user.uisettings.pojos.UIPrimaryLayoutModelClass;
import com.bhargo.user.utils.AppConstants;
import com.bhargo.user.utils.DataCollectionControlsValidation;
import com.bhargo.user.utils.FilePicker;
import com.bhargo.user.utils.ImproveHelper;
import com.bhargo.user.utils.MapExistingControlsValidation;
import com.bhargo.user.utils.PrefManger;
import com.bhargo.user.utils.PropertiesNames;
import com.bhargo.user.utils.SessionManager;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class GridControlOld implements UIVariables {

    private static final String TAG = "GridControl";
    public static List<String> sfString = new ArrayList<>();
    public static List<HashMap<String, String>> sfFileString = new ArrayList<>();
    private final LinkedHashMap<String, Object> list_ControlClassObjects;

    private final int height;
    private final int width;
    private final int screenWidth;
    private final LinearLayout ll_previewContainer;
    public ControlObject controlObject;
    public List<LinkedHashMap<String, Object>> gridControl_List_ControlClassObjects = new ArrayList<>();
    public int iMinRows, iMaxRows;
    //=======Load_Onchange_EventObjects================
    public LinkedHashMap<String, Control_EventObject> hash_Onchange_EventObjects = new LinkedHashMap<String, Control_EventObject>();
    //=======Load_Onfocus_EventObjects================
    public LinkedHashMap<String, Control_EventObject> hash_Onfocus_EventObjects = new LinkedHashMap<String, Control_EventObject>();
    // LinkedHashMap<String, Object> List_ControlClassObjects = new LinkedHashMap<String, Object>();
    //=======Load_OnClick_EventObjects================
    public LinkedHashMap<String, Control_EventObject> hash_Onclick_EventObjects = new LinkedHashMap<String, Control_EventObject>();
    public LinearLayout linearLayout, ll_MainSubFormContainer, ll_innerSubFormContainer1;
    public int SubFormTAG = 0;
    public boolean isGrid;
    public JSONArray jArrayAutoIncrementControls = new JSONArray();
    public CustomButton btn_addSubForm;
    public ProgressDialog pd;
    Activity context;
    List<ControlObject> list_Control;
    List<List<String>> sampleStr = new ArrayList<>();
    List<List<HashMap<String, String>>> sampleFilesStr = new ArrayList<>();
    int checkCount = 1;
    boolean iMaxReached = false;
    List<String> stringListSubmit = new ArrayList<>();
    String strUserLocationStructure;
    SessionManager sessionManager;
    String appname;
    String appLanguage = "en";
    DataCollectionObject dataCollectionObject;
    AlertDialog dialog;
    SubFormDeleteInterface subFormDeleteInterface;
    ImproveHelper improveHelper;
    HashMap<String, ControlObject> globalControlObjects = new HashMap<>();

    boolean isUIFormNeeded;
    ScrollView scrollView;
    RecyclerView rvGrid;
    UILayoutProperties uiLayoutPropertiesSF;
    ControlUIProperties controlUIProperties;
    SubFormGridAdapter subFormGridAdapter;
    CustomEditText et_search;
    ImageView iv_search;
    List<String> searchitems = new ArrayList<>();
    JSONArray jsonArrayCheckList = new JSONArray();
    int gridTitleRowHeight = -1;
    int gridChildPosition = 0;

    String app_edit;
    List<EditOrViewColumns> editColumns;

    View view, activityView;
    LinearLayout ll_table;
    int totalCols = 0, totalRows = 0;
    LinkedHashMap<String, LinkedHashMap<String, View>> sortLHM = new LinkedHashMap<>();
    private int screenHeight;
    private boolean isScreenFit = false;
    private CustomTextView tv_displayName, tv_hint;
    private ImageView iv_mandatory;
    private ImageView ivDeleteSubForm, ivAddSubForm, iv_deleteSubForm;
    private String tag;
    private HorizontalScrollView ll_subFormContainer;
    private LinearLayout ll_displayName;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    private ScrollView uFScrollView;
    //    private HorizontalScrollView uFScrollViewHorizontal;
    private CustomButton dlt_SubForm;
    private TableLayout tl_body, tl_header;
    private CustomButton addRow, deleteRow;
    private boolean isAutoNumbersAvaliable;
    private boolean isAutoNumbeStatusAlreadyAdded;
    private boolean rowContainsRadioGroup;
    private int positionOfRadioGroup;
    private LinkedHashMap<String, List<String>> outputData = new LinkedHashMap<>();
    private List<API_OutputParam_Bean> list_Form_OutParams = new ArrayList<>();


    public GridControlOld(Activity context, ControlObject controlObject, String strUserLocationStructure
            , LinkedHashMap<String, Object> list_ControlClassObjects, boolean isGrid, String appname,
                          LinearLayout ll_previewContainer, HashMap<String,
            ControlObject> globalControlObjects, boolean isUIFormNeeded, ScrollView scrollView, UILayoutProperties uiLayoutProperties, ControlUIProperties controlUIProperties, int layoutHeight, String app_edit, List<EditOrViewColumns> editColumns, View activityView) {

        this.context = context;
        this.controlObject = controlObject;
        this.strUserLocationStructure = strUserLocationStructure;
        this.list_ControlClassObjects = list_ControlClassObjects;
        this.isGrid = isGrid;
        this.appname = appname;
        this.ll_previewContainer = ll_previewContainer;
        this.globalControlObjects = globalControlObjects;
        this.isUIFormNeeded = isUIFormNeeded;
        this.scrollView = scrollView;
        this.uiLayoutPropertiesSF = uiLayoutProperties;
        this.controlUIProperties = controlUIProperties;
        this.screenHeight = layoutHeight;
        this.app_edit = app_edit;
        this.editColumns = editColumns;
        this.activityView = activityView;
        sessionManager = new SessionManager(context);
        improveHelper = new ImproveHelper(context);
        appLanguage = ImproveHelper.getLocale(context);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        height = dm.heightPixels;
        width = dm.widthPixels;

        if (uiLayoutPropertiesSF != null && uiLayoutPropertiesSF.getHeight() != 0) {
            screenHeight = improveHelper.dpToPx(uiLayoutPropertiesSF.getHeight());
        }
        screenWidth = width;
        initImageLoader(context);
        initView();
    }

    private void initView() {
        try {
            linearLayout = new LinearLayout(context);
            linearLayout.setTag(controlObject.getControlName());
            ImproveHelper.layout_params.setMargins(10, 10, 10, 10);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            if (controlObject.getMinimumRows() != null || !controlObject.getMinimumRows().isEmpty()) {
                iMinRows = Integer.parseInt(controlObject.getMinimumRows());
                Log.d(TAG, "initViewMInRows: " + iMinRows);
            }
            if (controlObject.getMaximumRows() != null || !controlObject.getMaximumRows().isEmpty()) {
                iMaxRows = Integer.parseInt(controlObject.getMaximumRows());
                Log.d(TAG, "initViewMaxRows: " + iMaxRows);
            }
            Log.d(TAG, "initViewMinRowsMaxRows: " + iMinRows + "-" + iMaxRows);
            /*Add mainSubForm Strip*/

            if (isGrid) {
                addGridView();
            }
        } catch (Exception e) {
            Log.d(TAG, "initViewSFCheck: " + e);
            ImproveHelper.improveException(context, TAG, "initView", e);
        }

    }

    public void addGridView() {
        try {
            LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (controlObject.getDisplayNameAlignment() != null && !controlObject.getDisplayNameAlignment().isEmpty()) {
                if (controlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                    view = lInflater.inflate(R.layout.control_subform_six, null);
                } else {
                    view = lInflater.inflate(R.layout.control_grid, null);
                }
            } else {
                view = lInflater.inflate(R.layout.control_grid, null);
            }

            tv_displayName = view.findViewById(R.id.tv_displayName);
            tv_hint = view.findViewById(R.id.tv_hint);
            tv_displayName.setText(controlObject.getDisplayName());
            tv_hint.setVisibility(View.GONE);
            if (controlObject.getHint() != null && !controlObject.getHint().contentEquals("")) {
                tv_hint.setVisibility(View.VISIBLE);
                tv_hint.setText(controlObject.getHint());
            } else {
                tv_hint.setVisibility(View.GONE);
            }
            if (controlObject.isHideDisplayName()) {
                tv_hint.setVisibility(View.GONE);
                tv_displayName.setVisibility(View.GONE);
            }
            ll_table = view.findViewById(R.id.ll_table);
            tl_header = view.findViewById(R.id.tl_header);
            tl_body = view.findViewById(R.id.ll_grid_view);


            addRow = view.findViewById(R.id.btn_addSubForm);
            deleteRow = view.findViewById(R.id.dlt_SubForm);

            et_search = view.findViewById(R.id.et_search);
            et_search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence != null) {
                        searchSubForm(charSequence.toString().trim());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            et_search.setVisibility(View.GONE);
            iv_search = view.findViewById(R.id.iv_search);
            iv_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subformSearch();
                }
            });
            if (controlObject.isSearchEnable()) {
                iv_search.setVisibility(View.VISIBLE);

            } else {
                iv_search.setVisibility(View.GONE);
            }

            if (iMinRows == iMaxRows) {
                addRow.setVisibility(View.GONE);
            } else if (iMinRows < iMaxRows) {
                addRow.setVisibility(View.VISIBLE);
            }

            totalCols = 1 + controlObject.getSubFormControlList().size();//Delete Col + Remaining Cols
            totalRows = iMinRows;//total current rows
            //Header functionally
            headerRow();
            //Body functionally
            for (int i = 0; i < iMinRows; i++) {
                addGridRow();
            }

            if (tl_body.getChildCount() == iMinRows) {
                deleteRow.setVisibility(View.GONE);
            }

            deleteRow.setOnClickListener(v -> {

                //deleteRowClick(ll_grid_view);

            });
            if (controlObject.isGridControl_HideAddButton()) {
                btn_addRow().setVisibility(View.GONE);
            } else {
                btn_addRow().setVisibility(View.VISIBLE);
            }
            linearLayout.addView(view);
            ImproveHelper.layout_params.setMargins(0, 10, 0, 10);
            linearLayout.setLayoutParams(ImproveHelper.layout_params);

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addGridView", e);
        }
    }

    private void setColorToParticularCellsWithIndex(int[] x, int[] y, String colorCodes) {
        try {
            TableLayout tableLayout = getTableLayoutview();
            for (int i = 0; i < x.length; i++) {
                TableRow tableRow = (TableRow) tableLayout.getChildAt(x[i]);
                LinearLayout linearLayout = tableRow.getChildAt(y[i]).findViewById(R.id.ll_control_view);
                linearLayout.setBackgroundColor(Color.parseColor(colorCodes));
            }
        } catch (Exception e) {
        }
    }

    public TableLayout getTableLayoutview() {
        return tl_body;
    }

    private void setColorToParticularColCells(int[] y, String colorCodes) {
        try {
            TableLayout tableLayout = getTableLayoutview();
            for (int i = 0; i < tableLayout.getChildCount(); i++) {
                TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
                for (int j = 0; j < y.length; j++) {
                    LinearLayout linearLayout = tableRow.getChildAt(y[j]).findViewById(R.id.ll_control_view);
                    linearLayout.setBackgroundColor(Color.parseColor(colorCodes));
                }
            }
        } catch (Exception e) {
        }
    }

    private void setColorToParticularRowCells(int[] x, String colorCodes) {
        try {
            TableLayout tableLayout = getTableLayoutview();
            for (int i = 0; i < x.length; i++) {
                TableRow tableRow = (TableRow) tableLayout.getChildAt(x[i]);
                for (int j = 0; j < tableRow.getChildCount(); j++) {
                    LinearLayout linearLayout = tableRow.getChildAt(j).findViewById(R.id.ll_control_view);
                    linearLayout.setBackgroundColor(Color.parseColor(colorCodes));
                }
            }
        } catch (Exception e) {
        }
    }

    private void updateRowCellSettings(LinearLayout linearLayout, int rowIndex) {
        //row color
        if (controlObject.getGridControl_rowColorType() != null) {
            //ll_cell.setBackground(ContextCompat.getDrawable(context, R.drawable.border));
            if (controlObject.getGridControl_rowColorType().equalsIgnoreCase("Single Color")) {
                if (controlObject.getGridControl_rowColor1() != null) {
                    linearLayout.setBackgroundColor(Color.parseColor(controlObject.getGridControl_rowColor1()));
                }
            } else {
                if (rowIndex % 2 == 0) {
                    if (controlObject.getGridControl_rowColor1() != null) {
                        linearLayout.setBackgroundColor(Color.parseColor(controlObject.getGridControl_rowColor1()));
                    }
                } else {
                    if (controlObject.getGridControl_rowColor2() != null) {
                        linearLayout.setBackgroundColor(Color.parseColor(controlObject.getGridControl_rowColor2()));
                    }
                }
            }
        }
    }

    private void deleteCell_InRow(TableRow tableRow, int rowIndex, int colIndex) {
        LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View delView = lInflater.inflate(R.layout.item_tablerow_cols, null);
        FrameLayout framelayout = delView.findViewById(R.id.framelayout);
        LinearLayout linearLayout = delView.findViewById(R.id.ll_control_view);
        ImageView iv_del_tablerow = new ImageView(context);
        iv_del_tablerow.setTag(tableRow);
        iv_del_tablerow.setImageDrawable(context.getResources().getDrawable(android.R.drawable.ic_delete));
        iv_del_tablerow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTableLayoutview().removeView((View) v.getTag());
                gridControl_List_ControlClassObjects.remove(rowIndex);
                if (iMinRows < tl_body.getChildCount()) {
                    addRow.setVisibility(View.VISIBLE);
                }
            }
        });
        linearLayout.addView(iv_del_tablerow);
        updateRowCellSettings(linearLayout, rowIndex);
        tableRow.addView(delView);
        //col width & height
        setRowWidthHeight(delView, framelayout, colIndex);
        applyBorderForBody(framelayout, linearLayout, rowIndex, colIndex, false);
    }

    private void headerRow() {
        //Add header cols to tablelayout
        TableRow tr_header = new TableRow(context);
        tr_header.setTag(-1);
        //del col
        if (!controlObject.isGridControl_HideDeleteButton()) {
            LayoutInflater del_lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View del_view = del_lInflater.inflate(R.layout.item_tablerow_header, null);
            FrameLayout framelayout_del = del_view.findViewById(R.id.framelayout);
            CustomTextView tv_del = del_view.findViewById(R.id.tv_label);
            ImageView iv_sorting_del = del_view.findViewById(R.id.iv_sorting);
            tv_del.setText("DEL");
            updateColCellSettings(del_view);
            tr_header.addView(del_view);
            //col width & height
            setHeaderWidthHeight(del_view, framelayout_del, 60);
            applyBorderForHeader(framelayout_del, tv_del, 0, 0, true);
        }
        /*if (controlObject.isGridControl_HideDeleteButton()) {
            del_view.setVisibility(View.GONE);
        }*/
        //add remaining headers
        for (int j = 0; j < controlObject.getSubFormControlList().size(); j++) {
            ControlObject co_forheader = controlObject.getSubFormControlList().get(j);
            LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View headerView = lInflater.inflate(R.layout.item_tablerow_header, null);
            FrameLayout framelayout = headerView.findViewById(R.id.framelayout);
            CustomTextView tv_label = headerView.findViewById(R.id.tv_label);
            ImageView iv_sorting = headerView.findViewById(R.id.iv_sorting);
            iv_sorting.setTag(co_forheader.getControlID());
            tv_label.setText(co_forheader.getDisplayName());
            updateColCellSettings(headerView);
            tr_header.addView(headerView);
            //col width & height
            setHeaderWidthHeight(headerView, framelayout, Integer.parseInt(controlObject.getGridColumnSettings().get(j).getControlWidth()));

            if (controlObject.isGridControl_HideDeleteButton()) {
                applyBorderForHeader(framelayout, tv_label, 0, j, true);
            } else {
                int colIndex = j;
                applyBorderForHeader(framelayout, tv_label, 0, colIndex + 1, true);
            }
            boolean isSortingEnable = controlObject.getGridColumnSettings().get(j).isEnableSorting();
            //col color From GridColumnSettings
            if (controlObject.getGridColumnSettings().get(j).getControlColor() != null) {
                tv_label.setBackgroundColor(Color.parseColor(controlObject.getGridColumnSettings().get(j).getControlColor()));
            }
            if (isSortingEnable) {
                iv_sorting.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_unfold_more_24));
                iv_sorting.setVisibility(View.VISIBLE);
                iv_sorting.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onClick(View v) {
                        if (iv_sorting.getId() == 0) {
                            iv_sorting.setId(1);
                            // iv_sorting.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_unfold_more_24));
                            sortByCol(iv_sorting.getTag().toString(), "Asc");
                        } else {
                            iv_sorting.setId(0);
                            // iv_sorting.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_unfold_more_24));
                            sortByCol(iv_sorting.getTag().toString(), "Dsc");
                        }
                    }
                });
            }
        }
        if (controlObject.isHideColumnNames()) {
            tr_header.setVisibility(View.GONE);
        }
        tl_header.addView(tr_header);
    }

    private void sortByCol(String sortColName, String sortingType) {
        //bodyDataLHM = RealmDBHelper.getTableDataBaseOnDataTableColBeanWithSortInLinkedHashMap(context, actionObject.getActionId(), dataTableColumn_beans, sortColName, sortingType);
        LinkedHashMap<String, List<TableRow>> sort_ = new LinkedHashMap<>();
        for (int i = 0; i < gridControl_List_ControlClassObjects.size(); i++) {
            LinkedHashMap<String, Object> temp_object = gridControl_List_ControlClassObjects.get(i);
            if (temp_object.containsKey(sortColName)) {
                DynamicLabel dynamicLabeliew = (DynamicLabel) temp_object.get(sortColName);
                String val = dynamicLabeliew.getValueView().getText().toString().trim();
                List<TableRow> tableRows;
                if (sort_.containsKey(val)) {
                    tableRows = sort_.get(val);
                } else {
                    tableRows = new ArrayList<>();
                }
                tableRows.add((TableRow) dynamicLabeliew.getTag());
                sort_.put(val, tableRows);
            }
        }
        Map<String, List<TableRow>> mapSort;
        if (sortingType.equals("Asc")) {
            mapSort = new TreeMap<>(sort_);
        } else {
            mapSort = new TreeMap<String, List<TableRow>>(Collections.reverseOrder());
            mapSort.putAll(sort_);
        }
        tl_body.removeAllViews();
        int row = 0;
        for (String sKey : mapSort.keySet()) {
            System.out.println("Asc Key -> " + sKey + ":  Value -> " + sort_.get(sKey));
            List<TableRow> tableRows = sort_.get(sKey);
            for (int r = 0; r < tableRows.size(); r++) {
                TableRow tableRow = tableRows.get(r);
                for (int i = 0; i < tableRow.getChildCount(); i++) {
                    View gridView = tableRow.getChildAt(i);
                    FrameLayout framelayout = gridView.findViewById(R.id.framelayout);
                    LinearLayout linearLayout = gridView.findViewById(R.id.ll_control_view);
                    updateRowCellSettings(linearLayout, row);
                }
                tl_body.addView(tableRow);
                row++;
            }
        }
    }

    private void updateColCellSettings(View view) {

        CustomTextView tv_header = view.findViewById(R.id.tv_label);
        Log.d(TAG, "updateColCellSettingsControlColor: " + tv_header.getText().toString());
        //col text size
        if (controlObject.getGridControl_ColTextSize() != null) {
            tv_header.setTextSize(Float.parseFloat(controlObject.getGridControl_ColTextSize()));
        }
        //col text style
        String style = controlObject.getGridControl_ColTextStyle();
        if (style != null && style.equalsIgnoreCase("Bold")) {
            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_bold));
            tv_header.setTypeface(typeface_bold);
        } else if (style != null && style.equalsIgnoreCase("Italic")) {
            Typeface typeface_italic = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.font_name_italic));
            tv_header.setTypeface(typeface_italic);
        }
        //col text color
        if (controlObject.getGridControl_ColTextColor() != null) {
            tv_header.setTextColor(Color.parseColor(controlObject.getGridControl_ColTextColor()));
        }
        //col text alignment
        if (controlObject.getGridControl_ColTextAlignment() != null) {
            if (controlObject.getGridControl_ColTextAlignment().equalsIgnoreCase("Left")) {
                tv_header.setGravity(Gravity.LEFT);
            } else if (controlObject.getGridControl_ColTextAlignment().equalsIgnoreCase("Center")) {
                tv_header.setGravity(Gravity.CENTER);
            } else {
                tv_header.setGravity(Gravity.RIGHT);
            }
        }
        //col color
        if (controlObject.getGridControl_ColColor() != null) {
            tv_header.setBackgroundColor(Color.parseColor(controlObject.getGridControl_ColColor()));
        }
    }

    private void applyBorderForBody(FrameLayout frameLayout, LinearLayout textView,
                                    int rowIndex, int colIndex, boolean headerRow) {
        //bordertype
        String borderType = controlObject.getGridControl_BorderType() == null ? "Full Border" : controlObject.getGridControl_BorderType();
        String borderColor = controlObject.getGridControl_BorderColor() == null ? "#000000" : controlObject.getGridControl_BorderColor();
        int borderThickness = controlObject.getGridControl_BorderThickness() == null ? 3 : Integer.parseInt(controlObject.getGridControl_BorderThickness());
        String colBorder = controlObject.getGridControl_ColBorder() == null ? "No" : controlObject.getGridControl_ColBorder();
        ll_table.setBackgroundColor(Color.parseColor(borderColor));
        //borderColor="#000000";
        //borderThickness = 8;
        //borderType = "Full Border";
        //borderType = "Horizontal Border";
        //borderType = "Vertical Border";
        //borderType = "Inside Border";
        //borderType = "Outside Border";
        //borderType = "No Border";
        if (borderType.equalsIgnoreCase("Full Border") || borderType.equalsIgnoreCase("1")) {
            //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_bottom));
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.setMargins(borderThickness, borderThickness, borderThickness, borderThickness);
            textView.setLayoutParams(layoutParams);
        } else if (borderType.equalsIgnoreCase("Horizontal Border") || borderType.equalsIgnoreCase("2")) {
            if (rowIndex == totalRows - 1) {
                frameLayout.setBackgroundResource(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                textView.setLayoutParams(layoutParams);
            } else {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_bottom));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, borderThickness);
                textView.setLayoutParams(layoutParams);
            }

        } else if (borderType.equalsIgnoreCase("Vertical Border") || borderType.equalsIgnoreCase("3")) {
            if (colIndex == (totalCols - 1)) {
                frameLayout.setBackgroundResource(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                textView.setLayoutParams(layoutParams);
            } else {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, borderThickness, 0);
                textView.setLayoutParams(layoutParams);
            }
        } else if (borderType.equalsIgnoreCase("Inside Border") || borderType.equalsIgnoreCase("4")) {
            if (rowIndex == totalRows - 1 && colIndex == totalCols - 1) {
                frameLayout.setBackgroundResource(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                textView.setLayoutParams(layoutParams);
            } else if (rowIndex == totalRows - 1) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, borderThickness, 0);
                textView.setLayoutParams(layoutParams);
            } else if (colIndex == totalCols - 1) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_bottom));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, borderThickness);
                textView.setLayoutParams(layoutParams);
            } else {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_bottom));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, borderThickness, borderThickness);
                textView.setLayoutParams(layoutParams);
            }
        } else if (borderType.equalsIgnoreCase("Outside Border") || borderType.equalsIgnoreCase("5")) {
            if (rowIndex == 0 && colIndex == 0 && headerRow) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left_top));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(borderThickness, borderThickness, 0, 0);
                textView.setLayoutParams(layoutParams);
            } else if (rowIndex == 0 && colIndex == totalCols - 1 && headerRow) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_top));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, borderThickness, borderThickness, 0);
                textView.setLayoutParams(layoutParams);
            } else if (rowIndex == 0 && headerRow) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_top));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, borderThickness, 0, 0);
                textView.setLayoutParams(layoutParams);
            } else if (rowIndex == (totalRows - 1) && colIndex == 0) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left_bottom));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(borderThickness, 0, 0, borderThickness);
                textView.setLayoutParams(layoutParams);
            } else if (rowIndex == (totalRows - 1) && colIndex == totalCols - 1) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_bottom));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, borderThickness, borderThickness);
                textView.setLayoutParams(layoutParams);
            } else if (colIndex == 0) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(borderThickness, 0, 0, 0);
                textView.setLayoutParams(layoutParams);
            } else if (colIndex == totalCols - 1) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, borderThickness, 0);
                textView.setLayoutParams(layoutParams);
            } else if (rowIndex == (totalRows - 1)) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_bottom));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, borderThickness);
                textView.setLayoutParams(layoutParams);
            } else {
                frameLayout.setBackgroundResource(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                textView.setLayoutParams(layoutParams);
            }

        } else {
            //No Border
            frameLayout.setBackgroundResource(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            textView.setLayoutParams(layoutParams);
        }

    }


    private void applyBorderForHeader(FrameLayout frameLayout, CustomTextView textView,
                                      int rowIndex, int colIndex, boolean headerRow) {
        //bordertype
        String borderType = controlObject.getGridControl_BorderType() == null ? "Full Border" : controlObject.getGridControl_BorderType();
        String borderColor = controlObject.getGridControl_BorderColor() == null ? "#000000" : controlObject.getGridControl_BorderColor();
        int borderThickness = controlObject.getGridControl_BorderThickness() == null ? 3 : Integer.parseInt(controlObject.getGridControl_BorderThickness());
        String colBorder = controlObject.getGridControl_ColBorder() == null ? "No" : controlObject.getGridControl_ColBorder();

        ll_table.setBackgroundColor(Color.parseColor(borderColor));
        //borderColor="#000000";
        //borderThickness = 8;
        //borderType = "Full Border";
        //borderType = "Horizontal Border";
        //borderType = "Vertical Border";
        //borderType = "Inside Border";
        //borderType = "Outside Border";
        //borderType = "No Border";
        if (borderType.equalsIgnoreCase("Full Border") || borderType.equalsIgnoreCase("1")) {
            //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_bottom));
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.setMargins(borderThickness, borderThickness, borderThickness, borderThickness);
            textView.setLayoutParams(layoutParams);
        } else if (borderType.equalsIgnoreCase("Horizontal Border") || borderType.equalsIgnoreCase("2")) {
            if (rowIndex == totalRows - 1) {
                frameLayout.setBackgroundResource(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                textView.setLayoutParams(layoutParams);
            } else {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_bottom));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, borderThickness);
                textView.setLayoutParams(layoutParams);
            }

        } else if (borderType.equalsIgnoreCase("Vertical Border") || borderType.equalsIgnoreCase("3")) {
            if (colIndex == (totalCols - 1)) {
                frameLayout.setBackgroundResource(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                textView.setLayoutParams(layoutParams);
            } else {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, borderThickness, 0);
                textView.setLayoutParams(layoutParams);
            }
        } else if (borderType.equalsIgnoreCase("Inside Border") || borderType.equalsIgnoreCase("4")) {
            if (rowIndex == totalRows - 1 && colIndex == totalCols - 1) {
                frameLayout.setBackgroundResource(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                textView.setLayoutParams(layoutParams);
            } else if (rowIndex == totalRows - 1) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, borderThickness, 0);
                textView.setLayoutParams(layoutParams);
            } else if (colIndex == totalCols - 1) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_bottom));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, borderThickness);
                textView.setLayoutParams(layoutParams);
            } else {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_bottom));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, borderThickness, borderThickness);
                textView.setLayoutParams(layoutParams);
            }
        } else if (borderType.equalsIgnoreCase("Outside Border") || borderType.equalsIgnoreCase("5")) {
            if (rowIndex == 0 && colIndex == 0 && headerRow) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left_top));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(borderThickness, borderThickness, 0, 0);
                textView.setLayoutParams(layoutParams);
            } else if (rowIndex == 0 && colIndex == totalCols - 1 && headerRow) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_top));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, borderThickness, borderThickness, 0);
                textView.setLayoutParams(layoutParams);
            } else if (rowIndex == 0 && headerRow) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_top));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, borderThickness, 0, 0);
                textView.setLayoutParams(layoutParams);
            } else if (rowIndex == (totalRows - 1) && colIndex == 0) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left_bottom));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(borderThickness, 0, 0, borderThickness);
                textView.setLayoutParams(layoutParams);
            } else if (rowIndex == (totalRows - 1) && colIndex == totalCols - 1) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_bottom));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, borderThickness, borderThickness);
                textView.setLayoutParams(layoutParams);
            } else if (colIndex == 0) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_left));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(borderThickness, 0, 0, 0);
                textView.setLayoutParams(layoutParams);
            } else if (colIndex == totalCols - 1) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, borderThickness, 0);
                textView.setLayoutParams(layoutParams);
            } else if (rowIndex == (totalRows - 1)) {
                //frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border_bottom));
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, borderThickness);
                textView.setLayoutParams(layoutParams);
            } else {
                frameLayout.setBackgroundResource(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                textView.setLayoutParams(layoutParams);
            }

        } else {
            //No Border
            frameLayout.setBackgroundResource(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            textView.setLayoutParams(layoutParams);
        }

        if (colBorder.equalsIgnoreCase("no")) {
            //No Border
            frameLayout.setBackgroundResource(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            textView.setLayoutParams(layoutParams);
        }
    }

    private int viewWidth(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }

    private void setRowWidthHeight(View view, FrameLayout frameLayout, int colIndex) {
        try {
            TableRow.LayoutParams tr_params = new TableRow.LayoutParams();
            ViewGroup.MarginLayoutParams params =
                    (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int screenWidth = displayMetrics.widthPixels;
            //get col width for cell width
            int width = viewWidth(((TableRow) this.tl_header.getChildAt(0)).getChildAt(colIndex));
            params.width = width;
            tr_params.width = width;

            //col height
            if (controlObject.getGridControl_rowHeigthType() != null) {
                if (controlObject.getGridControl_rowHeigthType().equalsIgnoreCase("Wrap Content")) {
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    tr_params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                } else {
                    int colHeightSize = Integer.parseInt(controlObject.getGridControl_rowHeightSize());
                    params.height = pxToDP(colHeightSize);
                    tr_params.height = pxToDP(colHeightSize);
                }
            }
            frameLayout.setLayoutParams(tr_params);
            view.setLayoutParams(params);
            view.requestLayout();
        } catch (Exception e) {
            ImproveHelper.improveException(context, "GridControl", "setWidth", e);
        }
    }

    private void setHeaderWidthHeight(View view, FrameLayout frameLayout, int particularColWidth) {
        try {
            TableRow.LayoutParams tr_params = new TableRow.LayoutParams();
            ViewGroup.MarginLayoutParams params =
                    (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int screenWidth = displayMetrics.widthPixels;

            //Control obj wise width & height for all header cells
            //col width
            if (controlObject.isEnableFixGridWidth()) {
                params.width = particularColWidth * screenWidth / 100;
            } else {
                params.width = pxToDP(particularColWidth);
            }
            tr_params.width = params.width;
            //col height
            if (controlObject.getGridControl_ColHeightType() != null) {
                if (controlObject.getGridControl_ColHeightType().equalsIgnoreCase("Wrap Content")) {
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    tr_params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                } else {
                    int colHeightSize = Integer.parseInt(controlObject.getGridControl_ColHeightSize());
                    params.height = pxToDP(colHeightSize);
                    tr_params.height = pxToDP(colHeightSize);
                }
            }
            frameLayout.setLayoutParams(tr_params);
            view.setLayoutParams(params);
            view.requestLayout();
        } catch (Exception e) {
            ImproveHelper.improveException(context, "GridControl", "setWidth", e);
        }
    }


    public void subformSearch() {
        if (et_search.getVisibility() == View.VISIBLE) {
            et_search.setVisibility(View.GONE);
        } else {
            et_search.setVisibility(View.VISIBLE);
            loadsearchItemValusFromSubForm();
        }
    }


    public void addGridRow() {
        try {
            LinkedHashMap<String, Object> New_list_ControlClassObjects = new LinkedHashMap<String, Object>();
            gridControl_List_ControlClassObjects.add(New_list_ControlClassObjects);
            TableRow tr_grid_row = new TableRow(context);
            tr_grid_row.setTag(SubFormTAG);
            if (!controlObject.isGridControl_HideDeleteButton()) {
                deleteCell_InRow(tr_grid_row, tl_body.getChildCount(), 0);
            }
            String searchStr = "";
            for (int j = 0; j < controlObject.getSubFormControlList().size(); j++) {
                ControlObject co = (ControlObject) controlObject.getSubFormControlList().get(j).clone();
                if (controlObject.getSubFormControlList().get(j).getControlType().contentEquals(CONTROL_TYPE_RADIO_BUTTON)) {
                    rowContainsRadioGroup = true;
                    positionOfRadioGroup = j;
                }
                if (controlObject.getSubFormControlList().get(j).getControlType().equals(CONTROL_TYPE_DYNAMIC_LABEL)) {
                    searchStr = searchStr + controlObject.getSubFormControlList().get(j).getValue();
                }
                if (controlObject.getGridColumnSettings().get(j).getControlName().equals(controlObject.getSubFormControlList().get(j).getControlName())) {

                }
                loadControlInGrid(co
                        , controlObject.getSubFormControlList().get(j).getControlType(),
                        tr_grid_row, New_list_ControlClassObjects,
                        tl_body.getChildCount(), j, tl_body.getChildCount());
            }
            searchitems.add(searchStr);
            if (rowContainsRadioGroup) {
                View grid = tr_grid_row.getChildAt(positionOfRadioGroup);
                LinearLayout linearLayout = grid.findViewById(R.id.ll_control_view);
                ViewTreeObserver vto = linearLayout.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                        int width = linearLayout.getMeasuredWidth();
                        int height = linearLayout.getMeasuredHeight();
                        setHeightOfRow(tr_grid_row, height);
                    }
                });
            }
            //tr_grid_row.setBackgroundColor(Color.WHITE);
            //randomColorForTableRow(tr_grid_row);
            tl_body.addView(tr_grid_row);
            tr_grid_row.requestFocus();
            SubFormTAG++;
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addGridRow", e);
        }
    }

    private void randomColorForTableRow(TableRow tableRow) {
        tableRow.setBackground(ContextCompat.getDrawable(context, R.drawable.border));
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        tableRow.setBackgroundColor(color);
    }

    private void rowHeightListener(LinearLayout ll_grid_row) {
        View grid = ll_grid_row.getChildAt(gridChildPosition);
        LinearLayout linearLayout = grid.findViewById(R.id.ll_control_view);
        ViewTreeObserver vto = linearLayout.getViewTreeObserver();

        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                if (linearLayout.getMeasuredHeight() > gridTitleRowHeight) {
                    gridTitleRowHeight = linearLayout.getMeasuredHeight();
                }
                if (ll_grid_row.getChildCount() == gridChildPosition - 1) {
                    setHeightOfRow(ll_grid_row, gridTitleRowHeight);
                } else {
                    gridChildPosition++;
                    rowHeightListener(ll_grid_row);
                }
            }
        });
    }

    private void setHeightOfRow(LinearLayout gridRow, int height) {
        try {
            for (int i = 0; i < gridRow.getChildCount(); i++) {
                View gridView = gridRow.getChildAt(i);
                LinearLayout linearLayout = gridView.findViewById(R.id.ll_control_view);
                ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
//            params.height = pxToDP(height);
                params.height = height;
                linearLayout.setLayoutParams(params);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setHeightOfRow", e);
        }
    }

    public LinearLayout getll_MainSubFormContainer() {
        return ll_MainSubFormContainer;
    }


    private void loadsearchItemValusFromSubForm() {
        searchitems.clear();
        for (int i = 0; i < gridControl_List_ControlClassObjects.size(); i++) {
            LinkedHashMap<String, Object> temp_object = gridControl_List_ControlClassObjects.get(i);
            String searchStr = "";
            for (int j = 0; j < controlObject.getSearchItemIds().size(); j++) {
                if (temp_object.containsKey(controlObject.getSearchItemIds().get(j))) {
                    if (temp_object.get(controlObject.getSearchItemIds().get(j)).toString().contains(CONTROL_TYPE_DYNAMIC_LABEL)) {
                        DynamicLabel dynamicLabeliew = (DynamicLabel) temp_object.get(controlObject.getSearchItemIds().get(j));
                        searchStr = searchStr + dynamicLabeliew.getValueView().getText().toString().trim();
                    }
                }
            }
            searchitems.add(searchStr);
        }

    }

    public void addInnerSubFormStrip(Activity context, final LinearLayout ll_MainSubFormContainer, boolean isFirst) {
        try {
            LinkedHashMap<String, Object> New_list_ControlClassObjects = new LinkedHashMap<String, Object>();
            final LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view;
            if (controlObject.isUIFormNeededSubForm()) {
                view = lInflater.inflate(R.layout.inner_subform_strip_no_bg, null);
            } else {
//                view = lInflater.inflate(R.layout.inner_subform_strip, null);
                view = lInflater.inflate(R.layout.inner_subform_strip_default, null);
            }
            LinearLayout ll_innerSubForm = view.findViewById(R.id.ll_innerSubForm);
            ll_innerSubFormContainer1 = view.findViewById(R.id.ll_innerSubFormContainer);
            ImageView iv_deleteSubForm = view.findViewById(R.id.iv_deleteSubForm);
            view.setTag(SubFormTAG);

            HorizontalScrollView gridScrollView = view.findViewById(R.id.gridScrollView);
            LinearLayout ll_grid_container = view.findViewById(R.id.ll_grid_container);
            gridScrollView.setVisibility(View.GONE);

            if (isGrid) {
                iv_deleteSubForm.setVisibility(View.GONE);
                ll_innerSubForm.setBackgroundResource(0);
                ll_innerSubForm.setBackgroundColor(Color.WHITE);
                gridScrollView.setVisibility(View.VISIBLE);
                ll_innerSubFormContainer1.setVisibility(View.GONE);
            }


        } catch (Exception e) {
            Log.d(TAG, "initViewSFCheckthree: " + e);
            ImproveHelper.improveException(context, TAG, "addInnerSubFormStrip", e);
        }
    }

    private void searchSubForm(String searchStr) {
        if (isGrid) {
            for (int i = 0; i < tl_body.getChildCount(); i++) {
                View rowView = tl_body.getChildAt(i);
                if (searchitems.get(i).toLowerCase().contains(searchStr.toLowerCase())) {
                    rowView.setVisibility(View.VISIBLE);
                } else {
                    rowView.setVisibility(View.GONE);
                }
            }
        }


    }

    public void iv_deleteSubFormClick(LinearLayout ll_MainSubFormContainer, View view) {
//    public void iv_deleteSubFormClick() {
        try {
            for (int i = 0; i < ll_MainSubFormContainer.getChildCount(); i++) {
                if (ll_MainSubFormContainer.getChildAt(i) == view) {
                    gridControl_List_ControlClassObjects.remove(i);
                    ll_MainSubFormContainer.removeView(view);
                }
            }

            if (ll_MainSubFormContainer.getChildCount() == iMinRows) {
                deleteStripsUpdate(ll_MainSubFormContainer, true);
            }

            if (ll_MainSubFormContainer.getChildCount() != iMaxRows) {
                btn_addSubForm.setVisibility(View.VISIBLE);
            }

            if (subFormDeleteInterface != null) {
                subFormDeleteInterface.bothWrapContentAndDp(controlObject.getControlType(), controlObject.getControlName(), scrollView);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "iv_deleteSubFormClick", e);
        }
    }

    private void removeDelete(LinearLayout ll_MainSubFormContainer) {
        try {
            for (int i = 0; i < ll_MainSubFormContainer.getChildCount(); i++) {
                View rView = ll_MainSubFormContainer.getChildAt(i);
                ImageView iv_deleteSubFormC = rView.findViewById(R.id.iv_deleteSubForm);
                iv_deleteSubFormC.setVisibility(View.GONE);

            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "removeDelete", e);
        }
    }

    private void deleteStripsUpdate(LinearLayout ll_MainSubFormContainer, boolean isLast) {
        try {
            for (int i = 0; i < ll_MainSubFormContainer.getChildCount(); i++) {
                View rView = ll_MainSubFormContainer.getChildAt(i);
                ImageView iv_deleteSubFormC = rView.findViewById(R.id.iv_deleteSubForm);
                if (controlObject.getUiPrimaryLayoutModelClass() != null) {
                    iv_deleteSubFormC.setVisibility(View.GONE);
                } else {
                    if (isLast) {
                        iv_deleteSubFormC.setVisibility(View.GONE);
                    } else if (iMinRows == iMaxRows || ll_MainSubFormContainer.getChildCount() < iMinRows) {
                        iv_deleteSubForm.setVisibility(View.GONE);
                    } else {
                        iv_deleteSubFormC.setVisibility(View.VISIBLE);
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "deleteStripsUpdate", e);
        }
    }

    public HorizontalScrollView getSubFormViewContainer() {

        return ll_subFormContainer;
    }

    public LinearLayout getSubFormView() {

        return linearLayout;
    }

    public CustomTextView getTv_displayName() {

        return tv_displayName;
    }

    public CustomTextView getTv_hint() {

        return tv_hint;
    }

    public boolean formValidated(LinearLayout linearLayout, SubformView subformView, boolean isGrid) {

        boolean flag = true;
        LinearLayout ll_innerSubFormContainer;
        LinearLayout ll_MainSubFormContainer;
        try {
            list_Control = controlObject.getSubFormControlList();

//        View view = linearLayout.getChildAt(0);
            View view = linearLayout;

            if (isGrid) {
                ll_MainSubFormContainer = view.findViewById(R.id.ll_grid_view);
            } else {
                ll_MainSubFormContainer = view.findViewById(R.id.ll_MainSubFormContainer);
            }

            sampleStr.clear();
            sampleFilesStr.clear();

            if (ll_MainSubFormContainer.getChildCount() > 0) {
                for (int i = 0; i < ll_MainSubFormContainer.getChildCount(); i++) {


                    View innerView = ll_MainSubFormContainer.getChildAt(i);

                    if (isGrid) {
                        ll_innerSubFormContainer = (LinearLayout) innerView;
                    } else {
                        ll_innerSubFormContainer = innerView.findViewById(R.id.ll_innerSubFormContainer);
                    }

//--For Edit--//
                    if (ll_innerSubFormContainer.getTag() != null) {
                        if (!AppConstants.transIdsOfSubforms.containsKey(controlObject.getControlName())) {
                            List<String> rowID = new ArrayList<>();
                            rowID.add(ll_innerSubFormContainer.getTag().toString());
                            AppConstants.transIdsOfSubforms.put(controlObject.getControlName(), rowID);
                        } else {
                            List<String> rowID = new ArrayList<>();
                            rowID = AppConstants.transIdsOfSubforms.get(controlObject.getControlName());
                            rowID.add(ll_innerSubFormContainer.getTag().toString());
                            AppConstants.transIdsOfSubforms.put(controlObject.getControlName(), rowID);
                        }
                    } else {
                        if (!AppConstants.transIdsOfSubforms.containsKey(controlObject.getControlName())) {
                            List<String> rowID = new ArrayList<>();
                            rowID.add("");
                            AppConstants.transIdsOfSubforms.put(controlObject.getControlName(), rowID);
                        } else {
                            List<String> rowID = new ArrayList<>();
                            rowID = AppConstants.transIdsOfSubforms.get(controlObject.getControlName());
                            rowID.add("");
                            AppConstants.transIdsOfSubforms.put(controlObject.getControlName(), rowID);
                        }
                    }
                    //--For Edit--//
//            ControlsValidation controlsValidation = new ControlsValidation(context, subform_List_ControlClassObjects.get(i));
           /* DataCollectionControlsValidation dccv = new DataCollectionControlsValidation(context, subform_List_ControlClassObjects.get(i),controlObject.getSubFormList_Table_Columns());
            MapExistingControlsValidation mecv = new MapExistingControlsValidation(context, subform_List_ControlClassObjects.get(i));*/

                    List<String> tblClmns = controlObject.getSubFormList_Table_Columns();
                    if (tblClmns == null && checkmainformHasSubFormColumns()) {
                        tblClmns = ((MainActivity) context).dataCollectionObject.getList_Table_Columns();
                    }

                    if (checkmainformHasSubFormColumns() || controlObject.getTableSettingsType() != null && (controlObject.getTableSettingsType().equalsIgnoreCase("Create New Table") || controlObject.getTableSettingsType().equalsIgnoreCase("Map existing table"))) {
//                DataCollectionControlsValidation dccv = new DataCollectionControlsValidation(context, subform_List_ControlClassObjects.get(i), controlObject.getSubFormList_Table_Columns());
                        DataCollectionControlsValidation dccv = new DataCollectionControlsValidation(context, gridControl_List_ControlClassObjects.get(i), tblClmns, controlObject.getTableSettingsType(), getMandatoryColumnsList());
                        sfString.add(controlObject.getControlName() + "ID" + "|" + "SF_" + (i + 1));

//                        for (int j = 0; j < ll_innerSubFormContainer.getChildCount(); j++) {
                        for (int j = 0; j < list_Control.size(); j++) {

                            Log.d(TAG, "XmlHelperTextInput: " + list_Control.get(j).getControlType());

                            flag = dccv.controlSubmitValidation(list_Control.get(j), true, sfString, sfFileString
                                    , subformView.getTag(), i, j);

                            if (!flag) {
                                break;
                            }
                        }

                        sampleStr.add(sfString);
                        sampleFilesStr.add(sfFileString);
                        sfString = new ArrayList<>();
                        sfFileString = new ArrayList<>();

                    } else if (controlObject.getTableSettingsType() == null) {
                        DataCollectionControlsValidation dccv = new DataCollectionControlsValidation(context, gridControl_List_ControlClassObjects.get(i), ((MainActivity) context).dataCollectionObject.getDefault_Table_Columns(), controlObject.getTableSettingsType(), getMandatoryColumnsList());
                        sfString.add(controlObject.getControlName() + "ID" + "|" + "SF_" + (i + 1));

                        for (int j = 0; j < ll_innerSubFormContainer.getChildCount(); j++) {

                            Log.d(TAG, "XmlHelperTextInput_: " + list_Control.get(j).getControlType());

                            flag = dccv.controlSubmitValidation(list_Control.get(j), true, sfString, sfFileString
                                    , subformView.getTag(), i, j);

                            if (!flag) {
                                break;
                            }
                        }

                        sampleStr.add(sfString);
                        sampleFilesStr.add(sfFileString);
                        sfString = new ArrayList<>();
                        sfFileString = new ArrayList<>();
                    }
                    if (!flag) {
                        break;
                    }

                }
            } else {
                sampleStr.add(sfString);
            }

        } catch (Exception e) {
            Log.d(TAG, "formValidatedEXce: " + e);
            ImproveHelper.improveException(context, TAG, "formValidated", e);
        }

        return flag;
    }

    public boolean formValidatedGrid(LinearLayout linearLayout, GridControlOld subformView, boolean isGrid) {

        boolean flag = true;
        //LinearLayout ll_innerSubFormContainer;
        //LinearLayout ll_MainSubFormContainer; nk grid
        TableLayout tl_view;
        TableRow tr_view;
        try {
            list_Control = controlObject.getSubFormControlList();

            View view = linearLayout;

            tl_view = view.findViewById(R.id.ll_grid_view);


            sampleStr.clear();
            sampleFilesStr.clear();

            if (tl_view.getChildCount() > 0) {
                for (int i = 0; i < tl_view.getChildCount(); i++) {
                    View innerView = tl_view.getChildAt(i);
                    tr_view = (TableRow) innerView;


//--For Edit--//
                    if (tr_view.getTag() != null) {
                        if (!AppConstants.transIdsOfSubforms.containsKey(controlObject.getControlName())) {
                            List<String> rowID = new ArrayList<>();
                            rowID.add(tr_view.getTag().toString());
                            AppConstants.transIdsOfSubforms.put(controlObject.getControlName(), rowID);
                        } else {
                            List<String> rowID = new ArrayList<>();
                            rowID = AppConstants.transIdsOfSubforms.get(controlObject.getControlName());
                            rowID.add(tr_view.getTag().toString());
                            AppConstants.transIdsOfSubforms.put(controlObject.getControlName(), rowID);
                        }
                    } else {
                        if (!AppConstants.transIdsOfSubforms.containsKey(controlObject.getControlName())) {
                            List<String> rowID = new ArrayList<>();
                            rowID.add("");
                            AppConstants.transIdsOfSubforms.put(controlObject.getControlName(), rowID);
                        } else {
                            List<String> rowID = new ArrayList<>();
                            rowID = AppConstants.transIdsOfSubforms.get(controlObject.getControlName());
                            rowID.add("");
                            AppConstants.transIdsOfSubforms.put(controlObject.getControlName(), rowID);
                        }
                    }
                    //--For Edit--//
//            ControlsValidation controlsValidation = new ControlsValidation(context, subform_List_ControlClassObjects.get(i));
           /* DataCollectionControlsValidation dccv = new DataCollectionControlsValidation(context, subform_List_ControlClassObjects.get(i),controlObject.getSubFormList_Table_Columns());
            MapExistingControlsValidation mecv = new MapExistingControlsValidation(context, subform_List_ControlClassObjects.get(i));*/

                    List<String> tblClmns = controlObject.getSubFormList_Table_Columns();
                    if (tblClmns == null && checkmainformHasSubFormColumns()) {
                        tblClmns = ((MainActivity) context).dataCollectionObject.getList_Table_Columns();
                    }

                    if (checkmainformHasSubFormColumns() || controlObject.getTableSettingsType() != null && controlObject.getTableSettingsType().equalsIgnoreCase("Create New Table") || controlObject.getTableSettingsType().equalsIgnoreCase("Map existing table")) {
//                DataCollectionControlsValidation dccv = new DataCollectionControlsValidation(context, subform_List_ControlClassObjects.get(i), controlObject.getSubFormList_Table_Columns());
                        // DataCollectionControlsValidation dccv = new DataCollectionControlsValidation(context, subform_List_ControlClassObjects.get(i), tblClmns);
                        DataCollectionControlsValidation dccv = new DataCollectionControlsValidation(context, gridControl_List_ControlClassObjects.get(i), tblClmns, controlObject.getTableSettingsType(), getMandatoryColumnsList());
                        sfString.add(controlObject.getControlName() + "ID" + "|" + "SF_" + (i + 1));
                        if (linearLayout.getVisibility() == View.VISIBLE) {
                            //for (int j = 0; j < tr_view.getChildCount(); j++) {
                            for (int j = 0; j < list_Control.size(); j++) {
                                Log.d(TAG, "XmlHelperTextInput: " + list_Control.get(j).getControlType());

                                flag = dccv.controlSubmitValidation(list_Control.get(j), true, sfString, sfFileString
                                        , subformView.getTag(), i, j);

                                if (!flag) {
                                    break;
                                }
                            }
                        } else {
                            flag = true;
                        }

                        sampleStr.add(sfString);
                        sampleFilesStr.add(sfFileString);
                        sfString = new ArrayList<>();
                        sfFileString = new ArrayList<>();
                    } else if (controlObject.getTableSettingsType() != null && controlObject.getTableSettingsType().equalsIgnoreCase("Map existing table")) {
                        MapExistingControlsValidation mecv = new MapExistingControlsValidation(context, gridControl_List_ControlClassObjects.get(i));
                        sfString.add(controlObject.getControlName() + "ID" + "|" + "SF_" + (i + 1));

                        for (int j = 0; j < tr_view.getChildCount(); j++) {

                            Log.d(TAG, "XmlHelperTextInput: " + list_Control.get(j).getControlType());

                            flag = mecv.controlSubmitValidation(list_Control.get(j), true, sfString, sfFileString
                                    , subformView.getTag(), i, j, getMandatoryColumnsList());

                            if (!flag) {
                                break;
                            }
                        }

                        sampleStr.add(sfString);
                        sampleFilesStr.add(sfFileString);
                        sfString = new ArrayList<>();
                        sfFileString = new ArrayList<>();
                    } else if (controlObject.getTableSettingsType() == null) {
                        DataCollectionControlsValidation dccv = new DataCollectionControlsValidation(context, gridControl_List_ControlClassObjects.get(i), ((MainActivity) context).dataCollectionObject.getDefault_Table_Columns(), controlObject.getTableSettingsType(), getMandatoryColumnsList());
                        sfString.add(controlObject.getControlName() + "ID" + "|" + "SF_" + (i + 1));

                        for (int j = 0; j < tr_view.getChildCount(); j++) {

                            Log.d(TAG, "XmlHelperTextInput_: " + list_Control.get(j).getControlType());

                            flag = dccv.controlSubmitValidation(list_Control.get(j), true, sfString, sfFileString
                                    , subformView.getTag(), i, j);

                            if (!flag) {
                                break;
                            }
                        }

                        sampleStr.add(sfString);
                        sampleFilesStr.add(sfFileString);
                        sfString = new ArrayList<>();
                        sfFileString = new ArrayList<>();
                    }
                    if (!flag) {
                        break;
                    }

                }
            } else {
                sampleStr.add(sfString);
            }

        } catch (Exception e) {
            Log.d(TAG, "formValidatedEXce: " + e);
            ImproveHelper.improveException(context, TAG, "formValidated", e);
        }

        return flag;
    }


    public List<String> getMandatoryColumnsList() {
        List<String> mandatoryColumns = new ArrayList<>();
        try {
            if (controlObject.getTableSettingsType() != null && controlObject.getTableSettingsType().equalsIgnoreCase("Map existing table")) {
                if (controlObject.getMapExistingType().equalsIgnoreCase("Insert")) {
                    for (QueryFilterField_Bean queryFilterField_bean : controlObject.getSubFormInsertFields()) {
                        if (!queryFilterField_bean.isField_IsDeleted() && queryFilterField_bean.getIsNullAllowed().equalsIgnoreCase("NO")) {
                            mandatoryColumns.add(ImproveHelper.getControlName(queryFilterField_bean.getField_Global_Value()));
                        }
                    }
                } else if (controlObject.getMapExistingType().equalsIgnoreCase("Update")) {
                    for (QueryFilterField_Bean queryFilterField_bean : controlObject.getSubFormUpdateFields()) {
                        if (!queryFilterField_bean.isField_IsDeleted() && queryFilterField_bean.getIsNullAllowed().equalsIgnoreCase("NO")) {
                            mandatoryColumns.add(ImproveHelper.getControlName(queryFilterField_bean.getField_Global_Value()));
                        }
                    }
                } else if (controlObject.getMapExistingType().equalsIgnoreCase("Insert / Update")) {
                    for (QueryFilterField_Bean queryFilterField_bean : controlObject.getSubFormUpdateFields()) {
                        if (!queryFilterField_bean.isField_IsDeleted() && queryFilterField_bean.getIsNullAllowed().equalsIgnoreCase("NO")) {
                            mandatoryColumns.add(ImproveHelper.getControlName(queryFilterField_bean.getField_Global_Value()));
                        }
                    }
                    for (QueryFilterField_Bean queryFilterField_bean : controlObject.getSubFormInsertFields()) {
                        if (!queryFilterField_bean.isField_IsDeleted() && queryFilterField_bean.getIsNullAllowed().equalsIgnoreCase("NO")) {
                            mandatoryColumns.add(ImproveHelper.getControlName(queryFilterField_bean.getField_Global_Value()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getMandatoryColumnsList", e);
        }
        return mandatoryColumns;

    }

    private void setMargin(View view) {
        try {
            ViewGroup.MarginLayoutParams params =
                    (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            params.leftMargin = 10 - 26;
            view.setLayoutParams(params);
            view.requestLayout();
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setMargin", e);
        }
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

    private void setPadding1(View view) {
      /*  ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams) view.getLayoutParams();

        view.setLayoutParams(params);
        view.requestLayout();*/
        try {
            view.setPadding(0, pxToDP(-5), 0, pxToDP(0));
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setPadding1", e);
        }
    }

    private void setTopMargin(View view1, View view2) {
        try {
            ViewGroup.MarginLayoutParams params =
                    (ViewGroup.MarginLayoutParams) view1.getLayoutParams();
            params.topMargin = -20;
            view1.setLayoutParams(params);
            view1.requestLayout();

            ViewGroup.MarginLayoutParams params1 =
                    (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
            params.topMargin = -66;
            view2.setLayoutParams(params);
            view2.requestLayout();
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setTopMargin", e);
        }
    }

    private void setHeight(View view) {
        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.height = 400;
        view.setLayoutParams(params);
        view.requestLayout();

    }

    private void setWidth(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.width = (width) / 3;
        view.setLayoutParams(params);
        view.requestLayout();
    }

    private void setWidthForHeader(View view, int width) {
        try {
            ViewGroup.MarginLayoutParams params =
                    (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int mWidth = displayMetrics.widthPixels;
            params.width = valueInDps(width);
            // params.width = -1;
            params.height = -1;
            view.setMinimumWidth(valueInDps(width));
            view.setLayoutParams(params);
            view.requestLayout();
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setWidth", e);
        }
    }

    private void setWidth(View view, int width, boolean isFixWidth) {
        try {
            ViewGroup.MarginLayoutParams params =
                    (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int mWidth = displayMetrics.widthPixels;
            if (isFixWidth) {
                params.width = width * mWidth / 100;
            } else {
                params.width = valueInDps(width);
            }
            // params.width = -1;
            params.height = -1;
            view.setMinimumWidth(valueInDps(width));
            view.setLayoutParams(params);
            view.requestLayout();
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setWidth", e);
        }
    }

    private int valueInDps(int width) {
        return (int) (width * (context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public void loadControl(final ControlObject loadControlObject, final String controlType,
                            final LinearLayout ll_innerSubFormContainer, LinkedHashMap<String, Object> List_ControlClassObjects,
                            int pos, UILayoutProperties uiLayoutProperties, ControlUIProperties controlUIProperties) {
        try {
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
//                setPadding(textInput.getTextInputView());

                    //setMargin(textInput.getTextInputView());
//                TextInput.REQUEST_SPEECH_TO_TEXT =539;

                    if (loadControlObject.getDefaultValue() != null &&
                            !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE); // taptext
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        if (loadControlObject.isReadFromBarcode()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                            textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));

                        } else if (loadControlObject.isReadFromQRCode()) {
                            textInput.gettap_text().setVisibility(View.VISIBLE);
                            textInput.getCustomEditText().setVisibility(View.GONE);
                            textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                            textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isVoiceText()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));
                            textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isCurrentLocation()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_my_location_black_24dp));
                            textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isGoogleLocationSearch()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_current_location));
                            textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
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
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                        textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
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
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                        textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
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
                        textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
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
                        textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
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
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                        textInput.gettap_text().setOnClickListener(new fileClick(pos, loadControlObject));
                        textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));

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
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                        textInput.gettap_text().setOnClickListener(new fileClick(pos, loadControlObject));
                        textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
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
                        textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                        textInput.gettap_text().setOnClickListener(new fileClick(pos, loadControlObject));
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
                        textInput.gettap_text().setOnClickListener(new fileClick(pos, loadControlObject));
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
                        textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    if (loadControlObject.getDefaultValue() != null &&
                            !loadControlObject.getDefaultValue().isEmpty()) {
                        numericInput.gettap_text().setVisibility(View.GONE); // taptext
                        numericInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        if (loadControlObject.isReadFromBarcode()) {
                            numericInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                            numericInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));

                        } else if (loadControlObject.isReadFromQRCode()) {
                            numericInput.gettap_text().setVisibility(View.VISIBLE);
                            numericInput.getNumericTextView().setVisibility(View.GONE);
                            numericInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                            numericInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                            numericInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isVoiceText()) {
                            numericInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));
                            numericInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isCurrentLocation()) {
                            numericInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_my_location_black_24dp));
                            numericInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isGoogleLocationSearch()) {
                            numericInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_current_location));
                            numericInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
//
                        }
                        numericInput.getNumericTextView().setVisibility(View.VISIBLE);
                        numericInput.getNumericTextView().setText(loadControlObject.getDefaultValue());
                    } else if (loadControlObject.isReadFromBarcode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        numericInput.gettap_text().setVisibility(View.GONE);
                        numericInput.getNumericTextView().setVisibility(View.VISIBLE);
                        numericInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        numericInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                        numericInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
//
                    } else if (loadControlObject.isReadFromQRCode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        numericInput.gettap_text().setVisibility(View.GONE);
                        numericInput.getNumericTextView().setVisibility(View.VISIBLE);
                        numericInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        numericInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                        numericInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
//
                    } else if (loadControlObject.isVoiceText() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        numericInput.gettap_text().setVisibility(View.GONE);
                        numericInput.getNumericTextView().setVisibility(View.VISIBLE);
                        numericInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        numericInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));
                        numericInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
//
                    } else if (loadControlObject.isReadFromQRCode()) {

                        numericInput.getLl_tap_text().setVisibility(View.VISIBLE);
                        numericInput.gettap_text().setVisibility(View.VISIBLE);
                        numericInput.gettap_text().setText("Tap here to Scan QRCode");
                        numericInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        numericInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                        numericInput.gettap_text().setOnClickListener(new fileClick(pos, loadControlObject));
                        numericInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));

                    } else if (loadControlObject.isReadFromBarcode()) {
                        numericInput.getLl_tap_text().setVisibility(View.VISIBLE);
                        numericInput.gettap_text().setVisibility(View.VISIBLE);
                        numericInput.gettap_text().setText("Tap here to Scan BarCode");
                        numericInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        numericInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                        numericInput.gettap_text().setOnClickListener(new fileClick(pos, loadControlObject));
                        numericInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    if (loadControlObject.getDefaultValue() != null &&
                            !loadControlObject.getDefaultValue().isEmpty()) {
                        phone.gettap_text().setVisibility(View.GONE); // taptext
                        phone.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        if (loadControlObject.isReadFromBarcode()) {
                            phone.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                            phone.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));

                        } else if (loadControlObject.isReadFromQRCode()) {
                            phone.gettap_text().setVisibility(View.VISIBLE);
                            phone.getCustomEditText().setVisibility(View.GONE);
                            phone.getIv_textTypeImage().setVisibility(View.VISIBLE);
                            phone.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                            phone.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isVoiceText()) {
                            phone.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));
                            phone.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isCurrentLocation()) {
                            phone.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_my_location_black_24dp));
                            phone.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isGoogleLocationSearch()) {
                            phone.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_current_location));
                            phone.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
//
                        }
                        phone.getCustomEditText().setVisibility(View.VISIBLE);
                        phone.getCustomEditText().setText(loadControlObject.getDefaultValue());
                    } else if (loadControlObject.isReadFromBarcode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        phone.gettap_text().setVisibility(View.GONE);
                        phone.getCustomEditText().setVisibility(View.VISIBLE);
                        phone.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        phone.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                        phone.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
//
                    } else if (loadControlObject.isReadFromQRCode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        phone.gettap_text().setVisibility(View.GONE);
                        phone.getCustomEditText().setVisibility(View.VISIBLE);
                        phone.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        phone.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                        phone.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
//
                    } else if (loadControlObject.isVoiceText() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        phone.gettap_text().setVisibility(View.GONE);
                        phone.getCustomEditText().setVisibility(View.VISIBLE);
                        phone.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        phone.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));
                        phone.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
//
                    } else if (loadControlObject.isReadFromQRCode()) {

                        phone.getLl_tap_text().setVisibility(View.VISIBLE);
                        phone.gettap_text().setVisibility(View.VISIBLE);
                        phone.gettap_text().setText("Tap here to Scan QRCode");
                        phone.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        phone.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                        phone.gettap_text().setOnClickListener(new fileClick(pos, loadControlObject));
                        phone.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));

                    } else if (loadControlObject.isReadFromBarcode()) {
                        phone.getLl_tap_text().setVisibility(View.VISIBLE);
                        phone.gettap_text().setVisibility(View.VISIBLE);
                        phone.gettap_text().setText("Tap here to Scan BarCode");
                        phone.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        phone.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                        phone.gettap_text().setOnClickListener(new fileClick(pos, loadControlObject));
                        phone.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                    }
                    break;
                case CONTROL_TYPE_EMAIL:
                    Email email = new Email(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), email);
                    ll_innerSubFormContainer.addView(email.getEmailView());
//                setPadding(email.getEmailView());
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    if (loadControlObject.getDefaultValue() != null &&
                            !loadControlObject.getDefaultValue().isEmpty()) {
                        email.gettap_text().setVisibility(View.GONE); // taptext
                        email.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        if (loadControlObject.isReadFromBarcode()) {
                            email.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                            email.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));

                        } else if (loadControlObject.isReadFromQRCode()) {
                            email.gettap_text().setVisibility(View.VISIBLE);
                            email.getCustomEditText().setVisibility(View.GONE);
                            email.getIv_textTypeImage().setVisibility(View.VISIBLE);
                            email.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                            email.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isVoiceText()) {
                            email.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));
                            email.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isCurrentLocation()) {
                            email.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_my_location_black_24dp));
                            email.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isGoogleLocationSearch()) {
                            email.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_current_location));
                            email.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
//
                        }
                        email.getCustomEditText().setVisibility(View.VISIBLE);
                        email.getCustomEditText().setText(loadControlObject.getDefaultValue());
                    } else if (loadControlObject.isReadFromBarcode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        email.gettap_text().setVisibility(View.GONE);
                        email.getCustomEditText().setVisibility(View.VISIBLE);
                        email.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        email.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                        email.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
//
                    } else if (loadControlObject.isReadFromQRCode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        email.gettap_text().setVisibility(View.GONE);
                        email.getCustomEditText().setVisibility(View.VISIBLE);
                        email.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        email.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                        email.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
//
                    } else if (loadControlObject.isVoiceText() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        email.gettap_text().setVisibility(View.GONE);
                        email.getCustomEditText().setVisibility(View.VISIBLE);
                        email.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        email.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));
                        email.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
//
                    } else if (loadControlObject.isReadFromQRCode()) {

                        email.getLl_tap_text().setVisibility(View.VISIBLE);
                        email.gettap_text().setVisibility(View.VISIBLE);
                        email.gettap_text().setText("Tap here to Scan QRCode");
                        email.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        email.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                        email.gettap_text().setOnClickListener(new fileClick(pos, loadControlObject));
                        email.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));

                    } else if (loadControlObject.isReadFromBarcode()) {
                        email.getLl_tap_text().setVisibility(View.VISIBLE);
                        email.gettap_text().setVisibility(View.VISIBLE);
                        email.gettap_text().setText("Tap here to Scan BarCode");
                        email.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        email.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                        email.gettap_text().setOnClickListener(new fileClick(pos, loadControlObject));
                        email.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
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
                    camera.getLLTapTextView().setOnClickListener(new fileClick(pos, loadControlObject));
                    camera.getReTake().setOnClickListener(new fileClick(pos, loadControlObject));
//                camera.getLLTapTextView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        AppConstants.Current_ClickorChangeTagName = v.getTag().toString().trim();
//                        camera.getCameraClickView();
//                    }
//                });
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_LARGE_INPUT:
                    LargeInput largeInput = new LargeInput(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), largeInput);
                    ll_innerSubFormContainer.addView(largeInput.getLargeInputView());
//                setPadding(largeInput.getLargeInputView());
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    break;

                case CONTROL_TYPE_FILE_BROWSING:
//                    FileBrowsing fileBrowsing = new FileBrowsing(context, loadControlObject);
                    FileBrowsing fileBrowsing = new FileBrowsing(context, loadControlObject, true, pos, controlObject.getControlName());
                    fileBrowsing.setPosition(pos);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), fileBrowsing);
                    ll_innerSubFormContainer.addView(fileBrowsing.getFileBrowsingView());
                    fileBrowsing.getTapTextViewLayout().setOnClickListener(new fileClick(pos, loadControlObject));
                    fileBrowsing.getFileBrowseImage().setOnClickListener(new fileClick(pos, loadControlObject));
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }

                    fileBrowsing.getFileBrowseImage().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppConstants.Current_ClickorChangeTagName = controlObject.getControlName();
                            AppConstants.SF_ClickorChangeTagName = v.getTag().toString().trim();
                            fileBrowsing.onClick(v, SF_FILE_BROWSER_RESULT_CODE);
                        }
                    });

                    fileBrowsing.getTapTextViewLayout().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //nk: ScannerOrFileManager
                            AppConstants.Current_ClickorChangeTagName = controlObject.getControlName();
                            AppConstants.SF_ClickorChangeTagName = v.getTag().toString().trim();
                            fileBrowsing.onClick(v, SF_FILE_BROWSER_RESULT_CODE);

                        }
                    });

                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    voiceRecording.startRecordingClick().setOnClickListener(new fileClick(pos, loadControlObject));
                    voiceRecording.getVoiceRecordingClick().setOnClickListener(new fileClick(pos, loadControlObject));
                    voiceRecording.getImageViewPlay().setOnClickListener(new fileClick(pos, loadControlObject));
                    voiceRecording.getImageViewStop().setOnClickListener(new fileClick(pos, loadControlObject));
                    voiceRecording.getLayoutAudioFileUpload().setOnClickListener(new fileClick(pos, loadControlObject));

                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    videoRecording.getTv_startVideoRecorder().setOnClickListener(new fileClick(pos, loadControlObject));
//                    videoRecording.getTv_videoFileUpload().setOnClickListener(new fileClick(pos, loadControlObject));


                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    signature.getLayoutUpload().setOnClickListener(new fileClick(pos, loadControlObject));
                    signature.getTv_clearSignature().setOnClickListener(new fileClick(pos, loadControlObject));

                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
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


                    break;
                case CONTROL_TYPE_CHECK_LIST:
//                    AppConstants.isSubformHasCheckList = true;
                    AppConstants.isSubformHasCheckList.put(controlObject.getControlName(), true);
                    try {
                        JSONObject checkListJsonObject = new JSONObject();
                        checkListJsonObject.put("ColumnName", loadControlObject.getControlName());
                        checkListJsonObject.put("InsertType", loadControlObject.getRowSelectionType());
//                        if (controlNotExists(loadControlObject.getControlName())) {
//                            AppConstants.subformCheckListData.put(checkListJsonObject);
//                        }
                        if (controlNotExists(controlObject.getControlName(), loadControlObject.getControlName())) {
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_DYNAMIC_LABEL:
                    DynamicLabel dynamicLabel = new DynamicLabel(context, loadControlObject, true, pos, controlObject.getControlName());
                    Log.d(TAG, "loadControlVisibilityD: " + loadControlObject.getControlName());
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
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                        dynamicLabel.getLl_main_inside().setLayoutParams(params);
                        controlAlignments(AppConstants.uiLayoutPropertiesStatic, dynamicLabel.getLl_main_inside());
                    }
                    if (loadControlObject.isOnClickEventExists()) {
                        Log.d(TAG, "hash_Onclick_EventObjects: " + loadControlObject.getControlName());
                        Control_EventObject onclick_control_EventObject = loadControlObject.getOnClickEventObject();
                        hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onclick_control_EventObject);
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
                            ViewGroup.LayoutParams layoutParamsMainInside = imageView.getLLMainInside().getLayoutParams();
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

                                ViewGroup.LayoutParams layoutParamsMInSideCard = imageView.getLl_insideCard().getLayoutParams();
                                layoutParamsMInSideCard.width = -1;
                                layoutParamsMInSideCard.height = -1;
                                imageView.getLl_insideCard().setLayoutParams(layoutParamsMInSideCard);
                                imageView.getLl_insideCard().setBackgroundColor(ContextCompat.getColor(context, R.color.transparent_color));
                            }
                            ViewGroup.LayoutParams layoutParamsSingle = imageView.getLl_single_Image().getLayoutParams();
                            layoutParamsSingle.width = -1;
                            layoutParamsSingle.height = -1;

                            imageView.getLl_single_Image().setLayoutParams(layoutParamsSingle);
                            imageView.getLl_single_Image().setBackgroundColor(ContextCompat.getColor(context, R.color.transparent_color));

                            ViewGroup.LayoutParams layoutParamsImg = imageView.getMainImageView().getLayoutParams();
                            if (loadControlObject.getDisplayNameAlignment().equalsIgnoreCase("10")) {
                                layoutParamsImg.width = pxToDP(40);
                                layoutParamsImg.height = pxToDP(40);
                            } else {
//                                layoutParamsImg.width = -2;
//                                layoutParamsImg.height = -2;
                            }
                            if (controlUIProperties != null && controlUIProperties.getTypeOfWidth() != null
                                    && !controlUIProperties.getTypeOfWidth().isEmpty() && controlUIProperties.getTypeOfWidth().equalsIgnoreCase(context.getString(R.string.Custom_Width))) {
                                layoutParamsImg.width = improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomWidthInDP()));
                                layoutParamsImg.height = improveHelper.dpToPx(Integer.parseInt(controlUIProperties.getCustomHeightInDP()));
                            }

                            imageView.getMainImageView().setLayoutParams(layoutParamsImg);
//                            imageView.getMainImageView().setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        } else {
                            imageView.getMainImageView().setScaleType(ImageView.ScaleType.CENTER);
                        }
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                        controlAlignments(AppConstants.uiLayoutPropertiesStatic, imageView.getLLMainInside());
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

                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
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
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    controlAlignments(AppConstants.uiLayoutPropertiesStatic, rating.getLl_rating());
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_BAR_CODE:
                    BarCode barcode = new BarCode(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), barcode);
                    ll_innerSubFormContainer.addView(barcode.getBarCode());
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }

                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    if (loadControlObject.isOnClickEventExists()) {
                        Control_EventObject onclick_control_EventObject = loadControlObject.getOnClickEventObject();
                        hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onclick_control_EventObject);
                    }

                    mapControl.setMap(mapControl.getMapView());
                    mapControl.getMapView().onResume();

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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
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
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    break;
                case CONTROL_TYPE_TIME:

                    Time timeControl = new Time(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), timeControl);

                    ll_innerSubFormContainer.addView(timeControl.getTimeControlLayout());

                    if (loadControlObject.isInvisible()) {
                        timeControl.getTimeControlLayout().setVisibility(View.GONE);
                    } else {
                        timeControl.getTimeControlLayout().setVisibility(View.VISIBLE);
                    }
                    if (controlObject.isUIFormNeededSubForm()) {
                        if (loadControlObject.isInvisible()) {
                            ll_innerSubFormContainer.setVisibility(View.GONE);
                        }
                    }
                    break;

            }

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "loadControl", e);
        }
    }

    private boolean controlNotExists(String subformName, String controlName) {
        boolean flag = true;
        try {
            if (AppConstants.subformCheckListData.containsKey(subformName)) {
                JSONArray jsonArray = AppConstants.subformCheckListData.get(subformName);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String key = jsonObject.getString("ColumnName");
                    if (key.equalsIgnoreCase(controlName)) {
                        flag = false;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "controlNotExists", e);
        }
        return flag;

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

    public int getMaxRows() {
        return iMaxRows;
    }

    public void addNewRows(Activity context, int newRowsCount) {
        try {
            LinearLayout ll_MainSubFormContainer = linearLayout.getChildAt(0).findViewById(R.id.ll_MainSubFormContainer);
            if (ll_MainSubFormContainer == null) {
                tl_body = linearLayout.getChildAt(0).findViewById(R.id.ll_grid_view);
                iMaxRows = tl_body.getChildCount() + newRowsCount;
                addGridRow();
                if (tl_body.getChildCount() > 1) {
                    deleteRow.setVisibility(View.VISIBLE);
                }
            } else {
                iMaxRows = ll_MainSubFormContainer.getChildCount() + newRowsCount;
                if (ll_MainSubFormContainer.getChildCount() == iMaxRows - 1) {
                    addInnerSubFormStrip(context, ll_MainSubFormContainer, true);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addNewRows", e);
        }
    }

    public void addNewRowsFromEdit(Activity context, int newRowsCount, boolean deleteOption) {
        try {
            iMaxRows += newRowsCount;
            LinearLayout ll_MainSubFormContainer = linearLayout.getChildAt(0).findViewById(R.id.ll_MainSubFormContainer);
//        if (ll_MainSubFormContainer.getChildCount() == iMaxRows - 1) {
            if (ll_MainSubFormContainer != null) {
                addInnerSubFormStrip(context, ll_MainSubFormContainer, true);
            } else {
                tl_body = linearLayout.getChildAt(0).findViewById(R.id.ll_grid_view);
                iMaxRows = tl_body.getChildCount() + newRowsCount;
                addGridRow();
                if (tl_body.getChildCount() > 1) {
                    deleteRow.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addNewRowsFromEdit", e);
        }
//        }
    }

    private void deleteRowExpectHeader(TableLayout tableLayout) {
        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            tableLayout.removeView(tableRow);
        }
    }

    public void replaceRows(Activity context, int newRowsCount, boolean isGridWithTwoColumnsR, LinkedHashMap<String, List<String>> OutputData) {
        try {
            tl_header.removeAllViews();
            headerRow();

                LinearLayout ll_MainSubFormContainer = linearLayout.getChildAt(0).findViewById(R.id.ll_MainSubFormContainer);
                if (ll_MainSubFormContainer == null) {
                    Log.d(TAG, "GRID_DATA: " + "replaceRowsGRIDDATA");

                    tl_body = linearLayout.getChildAt(0).findViewById(R.id.ll_grid_view);
                    //deleteRowExpectHeader(tl_grid_view);
                    tl_body.removeAllViews();
                    SubFormTAG = 0;
                    gridControl_List_ControlClassObjects.clear();

                    for (int i = 0; i < newRowsCount; i++) {
                        addGridRow();
                    }
                    // buildOldSchool(tl_grid_view,10);
                    // testTable(tl_grid_view,10);
                    //setColorToParticularCellsWithIndex(new int[]{0, 2, 4}, new int[]{4, 4, 4}, "#ABEBC6");
                    //setColorToParticularCellsWithIndex(new int[]{0, 2, 4}, new int[]{1, 1, 1}, "#FFBF00");
                    //setColorToParticularColCells(new int[]{2,4},"#FFBF00");
                    //setColorToParticularRowCells(new int[]{1, 2, 4}, "#ABEBC6");


                } else {

                    ll_MainSubFormContainer.removeAllViews();
                    gridControl_List_ControlClassObjects.clear();
                    for (int i = 0; i < newRowsCount; i++) {
                        addInnerSubFormStrip(context, ll_MainSubFormContainer, false);
                    }
                }

        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "replaceRows", e);
        }
    }
/* // Working
    public void replaceRows(Activity context, int newRowsCount) {
        try {
            LinearLayout ll_MainSubFormContainer = linearLayout.getChildAt(0).findViewById(R.id.ll_MainSubFormContainer);
            if (ll_MainSubFormContainer == null) {
                ll_grid_view = linearLayout.getChildAt(0).findViewById(R.id.ll_grid_view);
                ll_grid_view.removeAllViews();
                subform_List_ControlClassObjects.clear();
                for (int i = 0; i < newRowsCount; i++) {
                    addGridRow();
                }
            } else {
                ll_MainSubFormContainer.removeAllViews();
                subform_List_ControlClassObjects.clear();
                for (int i = 0; i < newRowsCount; i++) {

                        addInnerSubFormStrip(context, ll_MainSubFormContainer, false);


                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "replaceRows", e);
        }
    }
*/

    public void appendRows(Activity context, int newRowsCount) {
        try {
            tl_header.removeAllViews();
            headerRow();
            LinearLayout ll_MainSubFormContainer = linearLayout.getChildAt(0).findViewById(R.id.ll_MainSubFormContainer);
            if (ll_MainSubFormContainer == null) {
                Log.d(TAG, "GRIDDATA: " + "AppendRowsGRIDDATA");
                tl_body = linearLayout.getChildAt(0).findViewById(R.id.ll_grid_view);
                for (int i = 0; i < newRowsCount; i++) {
                    addGridRow();
                }
                //setColorToParticularCellsWithIndex(new int[]{0, 2, 4}, new int[]{4, 4, 4}, "#ABEBC6");

               /* if(iMinRows<ll_grid_view.getChildCount()){
                    CustomButton dlt_SubForm = linearLayout.getChildAt(0).findViewById(R.id.dlt_SubForm);
                    dlt_SubForm.setVisibility(View.VISIBLE);

                }else{
                    CustomButton dlt_SubForm = linearLayout.getChildAt(0).findViewById(R.id.dlt_SubForm);
                    dlt_SubForm.setVisibility(View.GONE);
                }*/
            } else {
                for (int i = 0; i < newRowsCount; i++) {
                    addInnerSubFormStrip(context, ll_MainSubFormContainer, true);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "appendRows", e);
        }
    }

    public void updateRows(Activity context, int updateRows) {
        try {
            LinearLayout ll_MainSubFormContainer = linearLayout.getChildAt(0).findViewById(R.id.ll_MainSubFormContainer);
            if (ll_MainSubFormContainer == null) {
                Log.d(TAG, "GRIDDATA: " + "UpdateRowsGRIDDATA");
                tl_body = linearLayout.getChildAt(0).findViewById(R.id.ll_grid_view);
                int rows = tl_body.getChildCount();
                if (updateRows > rows) {
                    for (int i = rows; i < updateRows; i++) {
                        addGridRow();
                    }
                }
            } else {
                int rows = ll_MainSubFormContainer.getChildCount();
                if (updateRows > rows) {
                    for (int i = rows; i < updateRows; i++) {
                        addInnerSubFormStrip(context, ll_MainSubFormContainer, true);
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "updateRows", e);
        }
    }

    public int getSubFormRows() {
        int childCount = 0;
        try {
            LinearLayout ll_MainSubFormContainer = linearLayout.getChildAt(0).findViewById(R.id.ll_MainSubFormContainer);
            if (ll_MainSubFormContainer == null) {
                tl_body = linearLayout.getChildAt(0).findViewById(R.id.ll_grid_view);
                childCount = tl_body.getChildCount();
            } else {
                childCount = ll_MainSubFormContainer.getChildCount();
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getSubFormRows", e);
        }
        return childCount;
    }

    public void setMaxRows() {
        try {
            LinearLayout ll_MainSubFormContainer = linearLayout.getChildAt(0).findViewById(R.id.ll_MainSubFormContainer);
            if (ll_MainSubFormContainer == null) {
                ll_MainSubFormContainer = linearLayout.getChildAt(0).findViewById(R.id.ll_grid_view);
            }
            if (ll_MainSubFormContainer.getChildCount() > 0) {
                iMaxRows = ll_MainSubFormContainer.getChildCount();
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setMaxRows", e);
        }
    }

    private void loadControlInGrid(final ControlObject loadControlObject, final String controlType,
                                   final TableRow tr_grid_row, LinkedHashMap<String, Object> List_ControlClassObjects,
                                   int pos, int index, int rowIndex) {
        try {
            View labelView = null;
            CustomTextView hint = null;
            Log.d(TAG, "XmlHelperTextInput: " + controlType);
            LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View gridView = lInflater.inflate(R.layout.item_tablerow_cols, null);
            gridView.setTag(loadControlObject);
            FrameLayout framelayout = gridView.findViewById(R.id.framelayout);
            LinearLayout linearLayout = gridView.findViewById(R.id.ll_control_view);
            if (loadControlObject.isInvisible()) {
                gridView.setVisibility(View.GONE);
            }
            if (!appLanguage.contentEquals("en") && loadControlObject.getTranslationsMappingObject() != null) {
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
                    labelView = textInput.getTextInputView().getChildAt(0).findViewById(R.id.ll_label);
                    hint = textInput.getTextInputView().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(textInput.getTextInputView());
                    textInput.getTapTextType().setVisibility(View.GONE);
                    textInput.getCustomEditText().setVisibility(View.VISIBLE);

                    if (loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE); // taptext
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        if (loadControlObject.isReadFromBarcode()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                            textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));

                        } else if (loadControlObject.isReadFromQRCode()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                            textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isVoiceText()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));
                            textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isCurrentLocation()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_my_location_black_24dp));
                            textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                        } else if (loadControlObject.isGoogleLocationSearch()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_current_location));
                            textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));

                        }
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getCustomEditText().setText(controlObject.getDefaultValue());
                    } else if (loadControlObject.isReadFromBarcode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE);
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                        textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));

                    } else if (loadControlObject.isReadFromQRCode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE);
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                        textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));

                    } else if (loadControlObject.isVoiceText() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE);
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));
                        textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));

                    } else if (loadControlObject.isCurrentLocation() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.GONE);
                        textInput.getCustomEditText().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_current_location));
                        textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));

                    } else if (loadControlObject.isReadFromBarcode()) {
                        textInput.getLl_tap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setText("Tap here to Scan BarCode");
                        textInput.gettap_text().performClick();
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                        textInput.gettap_text().setOnClickListener(new fileClick(pos, loadControlObject));
                        textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));

                    } else if (loadControlObject.isVoiceText()) {
                        textInput.getLl_tap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setVisibility(View.VISIBLE);
                        textInput.gettap_text().setText("Tap here voice to text");
                        textInput.gettap_text().performClick();
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.icons_voice_recording));
                        textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));
                        textInput.gettap_text().setOnClickListener(new fileClick(pos, loadControlObject));

                    } else if (loadControlObject.isCurrentLocation()) {
                        textInput.gettap_text().setText("Tap here to get Current Location");
                        textInput.gettap_text().performClick();
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_my_location_black_24dp));
                        textInput.gettap_text().setOnClickListener(new fileClick(pos, loadControlObject));

                    } else if (loadControlObject.isGoogleLocationSearch()) {

                        textInput.gettap_text().setText("Tap here to Search Location");
                        textInput.gettap_text().performClick();
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_current_location));

                        textInput.setDefaultValueForSearch();
                        textInput.getIv_textTypeImage().setOnClickListener(new fileClick(pos, loadControlObject));

                    }


                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }

                    break;
                case CONTROL_TYPE_NUMERIC_INPUT:
                    NumericInput numericInput = new NumericInput(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), numericInput);

                    labelView = numericInput.getNumericInputView().getChildAt(0).findViewById(R.id.ll_label);
                    hint = numericInput.getNumericInputView().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(numericInput.getNumericInputView());
                    numericInput.gettap_text().setVisibility(View.GONE);
                    numericInput.getNumericTextView().setVisibility(View.VISIBLE);

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
                    break;
                case CONTROL_TYPE_PHONE:
                    Phone phone = new Phone(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), phone);

                    labelView = phone.getPhoneView().getChildAt(0).findViewById(R.id.ll_label);
                    hint = phone.getPhoneView().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(phone.getPhoneView());
                    phone.gettap_text().setVisibility(View.GONE);
                    phone.getCustomEditText().setVisibility(View.VISIBLE);

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

                    break;
                case CONTROL_TYPE_EMAIL:
                    Email email = new Email(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), email);

                    labelView = email.getEmailView().getChildAt(0).findViewById(R.id.ll_label);
                    hint = email.getEmailView().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(email.getEmailView());
                    email.gettap_text().setVisibility(View.GONE);
                    email.getCustomEditText().setVisibility(View.VISIBLE);

                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }

                    break;
                case CONTROL_TYPE_LARGE_INPUT:
                    LargeInput largeInput = new LargeInput(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), largeInput);

                    labelView = largeInput.getLargeInputView().getChildAt(0).findViewById(R.id.ll_label);
                    hint = largeInput.getLargeInputView().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(largeInput.getLargeInputView());
                    largeInput.gettap_text().setVisibility(View.GONE);
                    largeInput.getCustomEditText().setVisibility(View.VISIBLE);

                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    if (loadControlObject.isOnFocusEventExists()) {
                        Control_EventObject onFocus_control_EventObject = loadControlObject.getOnFocusEventObject();
                        hash_Onfocus_EventObjects.put(loadControlObject.getControlName(), onFocus_control_EventObject);
                    }

                    break;
                case CONTROL_TYPE_URL_LINK:
                    UrlView urlView = new UrlView(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), urlView);

                    labelView = urlView.getURL().getChildAt(0).findViewById(R.id.ll_label);
                    hint = urlView.getURL().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(urlView.getURL());

                    if (loadControlObject.isInvisible()) {
                        urlView.getURL().setVisibility(View.GONE);
                    } else {
                        urlView.getURL().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_DECIMAL:
                    DecimalView decimalView = new DecimalView(context, loadControlObject, true
                            , pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), decimalView);

                    labelView = decimalView.getDecimal().getChildAt(0).findViewById(R.id.ll_label);
                    hint = decimalView.getDecimal().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(decimalView.getDecimal());
                    decimalView.gettap_text().setVisibility(View.GONE);
                    decimalView.getCustomEditText().setVisibility(View.VISIBLE);
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
                    break;
                case CONTROL_TYPE_PASSWORD:
                    Password password = new Password(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), password);

                    labelView = password.getPassword().getChildAt(0).findViewById(R.id.ll_label);
                    hint = password.getPassword().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(password.getPassword());
                    password.gettap_text().setVisibility(View.GONE);
                    password.getCustomEditText().setVisibility(View.VISIBLE);

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


                    break;
                case CONTROL_TYPE_CURRENCY:
                    Currency currency = new Currency(context, loadControlObject, true, pos, controlObject.getControlName().trim());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), currency);

                    labelView = currency.getCurrency().getChildAt(0).findViewById(R.id.ll_label);
                    hint = currency.getCurrency().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(currency.getCurrency());
                    currency.gettap_text().setVisibility(View.GONE);
                    currency.getCustomEditText().setVisibility(View.VISIBLE);

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
                    break;
                case CONTROL_TYPE_CHECKBOX:
                    Checkbox checkbox = new Checkbox(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), checkbox);

                    labelView = checkbox.getCheckbox().getChildAt(0).findViewById(R.id.ll_label);
                    hint = checkbox.getCheckbox().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(checkbox.getCheckbox());

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
                    break;

                case CONTROL_TYPE_CAMERA:

//                    final Camera camera = new Camera(context, loadControlObject, true, pos, controlObject.getControlName());
                    final Camera camera = new Camera(context, loadControlObject, true, pos, controlObject.getControlName());
                    camera.setPosition(pos);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), camera);

                    View customLayout_cam = context.getLayoutInflater().inflate(R.layout.dummy_view, null);

                    ImageButton imageButton_cam = customLayout_cam.findViewById(R.id.dummy_button);
                    imageButton_cam.setBackgroundResource(0);
                    imageButton_cam.setBackgroundResource(R.drawable.icons_camera_drawable);
                    imageButton_cam.setOnClickListener(v -> {
                        showControlDialogCameraOrFile(camera.getCameraView(), camera.getTv_tapText(), true);
                    });

                    linearLayout.addView(customLayout_cam);

                    camera.getLLTapTextView().setOnClickListener(new fileClick(pos, loadControlObject));
                    camera.getReTake().setOnClickListener(new fileClick(pos, loadControlObject));


                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }


                    break;

                case CONTROL_TYPE_FILE_BROWSING:

//                    FileBrowsing fileBrowsing = new FileBrowsing(context, loadControlObject);
                    FileBrowsing fileBrowsing = new FileBrowsing(context, loadControlObject, true, pos, controlObject.getControlName());
                    fileBrowsing.setPosition(pos);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), fileBrowsing);

                    View customLayout_file = context.getLayoutInflater().inflate(R.layout.dummy_view, null);

                    ImageButton imageButton_file = customLayout_file.findViewById(R.id.dummy_button);
                    imageButton_file.setBackgroundResource(0);
                    imageButton_file.setBackgroundResource(R.drawable.icons_filebrowse_drawable);
                    imageButton_file.setOnClickListener(v -> {
                        showControlDialogCameraOrFile(fileBrowsing.getFileBrowsingView(), fileBrowsing.getTapTextViewLayout(), false);
                    });

                    linearLayout.addView(customLayout_file);

                    fileBrowsing.getTapTextViewLayout().setOnClickListener(new fileClick(pos, loadControlObject));

                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_VOICE_RECORDING:

                    VoiceRecording voiceRecording = new VoiceRecording(context, loadControlObject, true, pos, controlObject.getControlName());
                    voiceRecording.setPosition(pos);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), voiceRecording);

                    View customLayout_voice = context.getLayoutInflater().inflate(R.layout.dummy_view, null);

                    ImageButton imageButton_voice = customLayout_voice.findViewById(R.id.dummy_button);
                    imageButton_voice.setBackgroundResource(0);
                    imageButton_voice.setBackgroundResource(R.drawable.icons_voice_recording);
                    imageButton_voice.setOnClickListener(v -> {
                        showControlDialog(voiceRecording.getVoiceRecordingView(), null, false);
                    });

                    linearLayout.addView(customLayout_voice);


                    if (loadControlObject.isInvisible()) {
                        voiceRecording.getVoiceRecordingView().setVisibility(View.GONE);
                    } else {
                        voiceRecording.getVoiceRecordingView().setVisibility(View.VISIBLE);
                    }

                    voiceRecording.startRecordingClick().setOnClickListener(new fileClick(pos, loadControlObject));
                    voiceRecording.getVoiceRecordingClick().setOnClickListener(new fileClick(pos, loadControlObject));
                    voiceRecording.getImageViewPlay().setOnClickListener(new fileClick(pos, loadControlObject));
                    voiceRecording.getImageViewStop().setOnClickListener(new fileClick(pos, loadControlObject));
                    voiceRecording.getLayoutAudioFileUpload().setOnClickListener(new fileClick(pos, loadControlObject));

                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    break;
                case CONTROL_TYPE_VIDEO_RECORDING:

                    VideoRecording videoRecording = new VideoRecording(context, loadControlObject, true, pos, controlObject.getControlName());
                    videoRecording.setPosition(pos);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), videoRecording);

                    View customLayout_vr = context.getLayoutInflater().inflate(R.layout.dummy_view, null);

                    ImageButton imageButton_vr = customLayout_vr.findViewById(R.id.dummy_button);
                    imageButton_vr.setBackgroundResource(0);
                    imageButton_vr.setBackgroundResource(R.drawable.icons_video_recording_96x96);
                    imageButton_vr.setOnClickListener(v -> {
                        showControlDialog(videoRecording.getVideoRecorderView(), null, false);
                    });

                    linearLayout.addView(customLayout_vr);

                    if (loadControlObject.isInvisible()) {
                        videoRecording.getVideoRecorderView().setVisibility(View.GONE);
                    } else {
                        videoRecording.getVideoRecorderView().setVisibility(View.VISIBLE);
                    }

                    videoRecording.getTv_startVideoRecorder().setOnClickListener(new fileClick(pos, loadControlObject));
//                    videoRecording.getTv_videoFileUpload().setOnClickListener(new fileClick(pos, loadControlObject));


                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    break;

                case CONTROL_TYPE_SIGNATURE:

                    final SignatureView signature = new SignatureView(context, loadControlObject);
                    signature.setPosition(pos);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), signature);
                    View customLayout_sign = context.getLayoutInflater().inflate(R.layout.dummy_view, null);

                    ImageButton imageButton_sign = customLayout_sign.findViewById(R.id.dummy_button);
                    imageButton_sign.setBackgroundResource(0);
                    imageButton_sign.setBackgroundResource(R.drawable.icons_sig_96x96);
                    imageButton_sign.setOnClickListener(v -> {
                        showControlDialog(signature.getSignature(), null, false);
                    });

                    linearLayout.addView(customLayout_sign);

                    if (loadControlObject.isInvisible()) {
                        signature.getSignature().setVisibility(View.GONE);
                    } else {
                        signature.getSignature().setVisibility(View.VISIBLE);
                    }
                    signature.getSignaturePad().setOnSignedListener(new com.bhargo.user.utils.SignaturePad.OnSignedListener(){

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

                    signature.getLayoutUpload().setOnClickListener(new fileClick(pos, loadControlObject));
                    signature.getTv_clearSignature().setOnClickListener(new fileClick(pos, loadControlObject));

                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }


                    break;

                case CONTROL_TYPE_CALENDER:

                    Calendar calendar = new Calendar(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), calendar);

                    labelView = calendar.getCalnderView().getChildAt(0).findViewById(R.id.ll_label);
                    hint = calendar.getCalnderView().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(calendar.getCalnderView());
                    calendar.getTv_tapTextType().setVisibility(View.GONE);
                    calendar.getCe_TextType().setVisibility(View.VISIBLE);

                    if (loadControlObject.isInvisible()) {
                        calendar.getCalnderView().setVisibility(View.GONE);
                    } else {
                        calendar.getCalnderView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }


                    break;
                case CONTROL_TYPE_AUDIO_PLAYER:
                    AudioPlayer audioPlayer = new AudioPlayer(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), audioPlayer);

                    View customLayout_audio = context.getLayoutInflater().inflate(R.layout.dummy_view, null);

                    ImageButton imageButton_audio = customLayout_audio.findViewById(R.id.dummy_button);
                    imageButton_audio.setBackgroundResource(0);
                    imageButton_audio.setBackgroundResource(R.drawable.icons_voice_recording);
                    imageButton_audio.setOnClickListener(v -> {
                        showControlDialog(audioPlayer.getAudioPlayerView(), null, false);
                    });

                    linearLayout.addView(customLayout_audio);


                    if (loadControlObject.isInvisible()) {
                        audioPlayer.getAudioPlayerView().setVisibility(View.GONE);
                    } else {
                        audioPlayer.getAudioPlayerView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_VIDEO_PLAYER:

                    VideoPlayer videoPlayer = new VideoPlayer(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), videoPlayer);

                    View customLayout_video = context.getLayoutInflater().inflate(R.layout.dummy_view, null);

                    ImageButton imageButton_video = customLayout_video.findViewById(R.id.dummy_button);
                    imageButton_video.setBackgroundResource(0);
                    imageButton_video.setBackgroundResource(R.drawable.icons_videoplayer_drawable);
                    if (loadControlObject.getVideoData() != null) {
                        ImproveHelper improveHelper = new ImproveHelper(context);
                        improveHelper.setVideoThumbNail(loadControlObject.getVideoData(), imageButton_video);
                    }
                    imageButton_video.setOnClickListener(v -> {
                        showControlDialog(videoPlayer.getVideoPlayerView(), null, false);
                    });

                    linearLayout.addView(customLayout_video);

                    if (loadControlObject.isInvisible()) {
                        videoPlayer.getVideoPlayerView().setVisibility(View.GONE);
                    } else {
                        videoPlayer.getVideoPlayerView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    break;
                case CONTROL_TYPE_PERCENTAGE:
                    Percentage percentage = new Percentage(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), percentage);

                    labelView = percentage.getPercentageView().getChildAt(0).findViewById(R.id.ll_label);
                    hint = percentage.getPercentageView().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(percentage.getPercentageView());
                    percentage.gettap_text().setVisibility(View.GONE);
                    percentage.getCustomEditText().setVisibility(View.VISIBLE);


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

                    break;
                case CONTROL_TYPE_RADIO_BUTTON:

                    RadioGroupView radioGroup = new RadioGroupView(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), radioGroup);

                    labelView = radioGroup.getRadioGroupView().getChildAt(0).findViewById(R.id.ll_label);
                    hint = radioGroup.getRadioGroupView().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(radioGroup.getRadioGroupView());

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
                    break;
                case CONTROL_TYPE_DROP_DOWN:
                    DropDown dropDown = new DropDown(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), dropDown);

                    labelView = dropDown.getDropdown().getChildAt(0).findViewById(R.id.ll_label);
                    hint = dropDown.getDropdown().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(dropDown.getDropdown());

                    if (loadControlObject.isInvisible()) {
                        dropDown.getDropdown().setVisibility(View.GONE);
                    } else {
                        dropDown.getDropdown().setVisibility(View.VISIBLE);
                    }
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
                    break;
                case CONTROL_TYPE_CHECK_LIST:
                    AppConstants.isSubformHasCheckList.put(controlObject.getControlName(), true);
                    try {
                        JSONObject checkListJsonObject = new JSONObject();
                        checkListJsonObject.put("ColumnName", loadControlObject.getControlName());
                        checkListJsonObject.put("InsertType", loadControlObject.getRowSelectionType());
                        /*if (controlNotExists(loadControlObject.getControlName())) {
                            AppConstants.subformCheckListData.put(checkListJsonObject);
                        }*/
                        if (controlNotExists(controlObject.getControlName(), loadControlObject.getControlName())) {
                            /*JSONArray jsonArray = new JSONArray();
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
                    labelView = checkList.getCheckList().findViewById(R.id.ll_label);
                    hint = checkList.getCheckList().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(checkList.getCheckList());

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
                    break;

                case CONTROL_TYPE_DYNAMIC_LABEL:

                    DynamicLabel dynamicLabel = new DynamicLabel(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), dynamicLabel);
                    dynamicLabel.setTag(tr_grid_row);
                    labelView = dynamicLabel.getDynamicLabel().findViewById(R.id.ll_label);
                    hint = dynamicLabel.getDynamicLabel().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(dynamicLabel.getDynamicLabel());


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
                        if (tr_grid_row.getChildCount() == 1) {
                            tr_grid_row.getChildAt(0).setLayoutParams(params);
                        } else {
                            tr_grid_row.getChildAt(2).setLayoutParams(params);
                        }
                        dynamicLabel.getLl_main_inside().setLayoutParams(params);
                        controlAlignments(AppConstants.uiLayoutPropertiesStatic, tr_grid_row);

                    }

                    break;
                case CONTROL_TYPE_IMAGE:

//                    Image imageView = new Image(context, loadControlObject, false, 0, "", "",null);
                    Image imageView = new Image(context, loadControlObject, true, 0, controlObject.getControlName(), "", null);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), imageView);

                    View customLayout_img = context.getLayoutInflater().inflate(R.layout.dummy_view, null);

                    ImageButton imageButton_img = customLayout_img.findViewById(R.id.dummy_button);
                    imageButton_img.setBackgroundResource(0);
                    imageButton_img.setBackgroundResource(R.drawable.icons_image);

                    ImproveHelper improveHelper = new ImproveHelper(context);
                    if (loadControlObject.getImageData() != null) {
                        improveHelper.setThumbNail(loadControlObject.getImageData(), imageButton_img);
                    }
                    imageButton_img.setOnClickListener(v -> {
                        showControlDialog(imageView.getImageView(), null, false);
                    });
                    if (controlObject.isUIFormNeededSubForm() && linearLayout != null) {
                        if (loadControlObject.getDisplayNameAlignment() != null && !loadControlObject.getDisplayNameAlignment().equalsIgnoreCase("6")) {
                            ViewGroup.LayoutParams layoutParamsMainInside = imageView.getLLMainInside().getLayoutParams();
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

                                ViewGroup.LayoutParams layoutParamsMInSideCard = imageView.getLl_insideCard().getLayoutParams();
                                layoutParamsMInSideCard.width = -1;
                                layoutParamsMInSideCard.height = -1;
                                imageView.getLl_insideCard().setLayoutParams(layoutParamsMInSideCard);
                                imageView.getLl_insideCard().setBackgroundColor(ContextCompat.getColor(context, R.color.transparent_color));
                            }
                            ViewGroup.LayoutParams layoutParamsSingle = imageView.getLl_single_Image().getLayoutParams();
                            layoutParamsSingle.width = -1;
                            layoutParamsSingle.height = -1;

                            imageView.getLl_single_Image().setLayoutParams(layoutParamsSingle);
                            imageView.getLl_single_Image().setBackgroundColor(ContextCompat.getColor(context, R.color.transparent_color));

                            ViewGroup.LayoutParams layoutParamsImg = imageView.getMainImageView().getLayoutParams();
                            if (loadControlObject.getDisplayNameAlignment().equalsIgnoreCase("9")) {
                                layoutParamsImg.width = pxToDP(40);
                                layoutParamsImg.height = pxToDP(40);
                            } else {
                                layoutParamsImg.width = -2;
                                layoutParamsImg.height = -2;
                            }
                            imageView.getMainImageView().setLayoutParams(layoutParamsImg);
                            imageView.getMainImageView().setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        } else {
                            imageView.getMainImageView().setScaleType(ImageView.ScaleType.CENTER);
                        }
                        linearLayout.addView(imageView.getImageView());
//                        imageView.getMainImageView().setScaleType(ImageView.ScaleType.FIT_XY);
                    } else {
                        linearLayout.addView(customLayout_img);
                    }

                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;
                case CONTROL_TYPE_BUTTON:
                    Button button = new Button(context, loadControlObject, true, pos, controlObject.getControlName(), appname);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), button);
                    linearLayout.addView(button.getButton());
                    if (loadControlObject.isInvisible()) {
                        button.getButton().setVisibility(View.GONE);
                    } else {
                        button.getButton().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnClickEventExists()) {
                        Control_EventObject onclick_control_EventObject = loadControlObject.getOnClickEventObject();
                        hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onclick_control_EventObject);
                    }
                    break;

                case CONTROL_TYPE_RATING:

                    Rating rating = new Rating(context, loadControlObject, true, pos, controlObject.getControlName());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), rating);

                    labelView = rating.getRatingView().findViewById(R.id.ll_label);
                    hint = rating.getRatingView().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(rating.getRatingView());

                    if (loadControlObject.isInvisible()) {
                        rating.getRatingView().setVisibility(View.GONE);
                    } else {
                        rating.getRatingView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    break;
                case CONTROL_TYPE_GPS:

                    Gps_Control controlGPS = new Gps_Control(context, loadControlObject);
                    loadControlObject.setGridControl(true);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), controlGPS);

                    View customLayout_gps = context.getLayoutInflater().inflate(R.layout.dummy_view, null);

                    ImageButton imageButton_gps = customLayout_gps.findViewById(R.id.dummy_button);
                    imageButton_gps.setBackgroundResource(R.drawable.ic_gps);
                    imageButton_gps.setOnClickListener(v -> {
                        showControlDialog(controlGPS.getControlGPSView(), controlGPS.getTv_tap_to_start(), false);
                    });

                    linearLayout.addView(customLayout_gps);


                    if (loadControlObject.isInvisible()) {
                        controlGPS.getControlGPSView().setVisibility(View.GONE);
                    } else {
                        controlGPS.getControlGPSView().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    break;
                case CONTROL_TYPE_BAR_CODE:
                    BarCode barcode = new BarCode(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), barcode);

                    labelView = barcode.getBarCode().findViewById(R.id.ll_label);
                    hint = barcode.getBarCode().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(barcode.getBarCode());

                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }

                    break;
                case CONTROL_TYPE_QR_CODE:
                    QRCode qrCode = new QRCode(context, loadControlObject);
                    List_ControlClassObjects.put(loadControlObject.getControlName(), qrCode);

                    labelView = qrCode.getQRCode().findViewById(R.id.ll_label);
                    hint = qrCode.getQRCode().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
                    linearLayout.addView(qrCode.getQRCode());

                    if (loadControlObject.isInvisible()) {
                        qrCode.getQRCode().setVisibility(View.GONE);
                    } else {
                        qrCode.getQRCode().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnChangeEventExists()) {
                        Control_EventObject onchange_control_EventObject = loadControlObject.getOnChangeEventObject();
                        hash_Onchange_EventObjects.put(loadControlObject.getControlName(), onchange_control_EventObject);
                    }
                    break;

                case CONTROL_TYPE_DATA_CONTROL:
                    loadItems(loadControlObject);
                    DataControl dataControl = new DataControl(context, loadControlObject, strUserLocationStructure, true, pos, controlObject.getControlName());
//                dataControl.getSpinner().setHintText("Select");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), dataControl);
                    labelView = dataControl.getDataControl().findViewById(R.id.ll_label);
                    hint = dataControl.getDataControl().getChildAt(0).findViewById(R.id.tv_hint);
                    hint.setVisibility(View.GONE);
                    labelView.setVisibility(View.GONE);
//                dataControl.getSpinner().setHintText("Select");
                    linearLayout.addView(dataControl.getDataControl());
                    break;

                case CONTROL_TYPE_MAP:

                    MapControl mapControl = new MapControl(context, loadControlObject, true, pos, controlObject.getControlName(), dataCollectionObject.isUIFormNeeded());
                    List_ControlClassObjects.put(loadControlObject.getControlName(), mapControl);
                    //linearLayout.addView(mapControl.getMapControlLayout());

                    View customLayout_map = context.getLayoutInflater().inflate(R.layout.dummy_view, null);

                    ImageButton imageButton_map = customLayout_map.findViewById(R.id.dummy_button);
                    imageButton_map.setBackgroundResource(0);
                    imageButton_map.setBackgroundResource(R.drawable.map_red_rounded_white);
                    imageButton_map.setOnClickListener(v -> {
                        showControlDialog(mapControl.getMapControlLayout(), null, false);
                    });

                    linearLayout.addView(customLayout_map);

                    if (loadControlObject.isInvisible()) {
                        mapControl.getMapControlLayout().setVisibility(View.GONE);
                    } else {
                        mapControl.getMapControlLayout().setVisibility(View.VISIBLE);
                    }
                    if (loadControlObject.isOnClickEventExists()) {
                        Control_EventObject onclick_control_EventObject = loadControlObject.getOnClickEventObject();
                        hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onclick_control_EventObject);
                    }
                    mapControl.getMapView().onResume();

                    break;

                case CONTROL_TYPE_CALENDAR_EVENT:

                    CalendarEventControl calendarEventControl = new CalendarEventControl(context, loadControlObject, false, 0, "");
                    List_ControlClassObjects.put(loadControlObject.getControlName(), calendarEventControl);


                    View customLayout_ce = context.getLayoutInflater().inflate(R.layout.dummy_view, null);

                    ImageButton imageButton_ce = customLayout_ce.findViewById(R.id.dummy_button);
                    imageButton_ce.setBackgroundResource(0);
                    imageButton_ce.setBackgroundResource(R.drawable.ic_date_range_red_32dp);
                    imageButton_ce.setOnClickListener(v -> {
                        showControlDialog(calendarEventControl.getCalendarEventLayout(), null, false);
                    });
                    linearLayout.addView(customLayout_ce);


                    if (loadControlObject.isInvisible()) {
                        calendarEventControl.getCalendarEventLayout().setVisibility(View.GONE);
                    } else {
                        calendarEventControl.getCalendarEventLayout().setVisibility(View.VISIBLE);
                    }

                    if (loadControlObject.isOnClickEventExists()) {
                        Control_EventObject onclick_control_EventObject = loadControlObject.getOnClickEventObject();
                        hash_Onclick_EventObjects.put(loadControlObject.getControlName(), onclick_control_EventObject);
                    }

                    break;
                case CONTROL_TYPE_AUTO_GENERATION:

                    isAutoNumbersAvaliable = true;

                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("ControlName", loadControlObject.getControlName());
                        jsonObject.put("Prefix", loadControlObject.getPreFixValue());
                        jsonObject.put("Suffix", loadControlObject.getSuffixValue());
                        if (loadControlObject.getSuffix1Value() != null && !loadControlObject.getSuffix1Value().contentEquals("")) {
                            jsonObject.put("Suffix1", loadControlObject.getSuffix1Value());
                        }
                        jArrayAutoIncrementControls.put(jsonObject);
                    } catch (JSONException e) {
//                    e.printStackTrace();
                        Log.d(TAG, "loadControlAutoGenException: " + e);
                    }
                    LinearLayout linearLayoutANG = new LinearLayout(context);
                    linearLayoutANG.setVisibility(View.GONE);
                    linearLayoutANG.setTag(loadControlObject.getControlName());
                    linearLayout.addView(linearLayoutANG);
                    break;

            }

            updateRowCellSettings(linearLayout, rowIndex);
            tr_grid_row.addView(gridView);
            //col width & height
            if (controlObject.isGridControl_HideDeleteButton()) {
                setRowWidthHeight(gridView, framelayout, index);
                applyBorderForBody(framelayout, linearLayout, rowIndex, index, false);
            } else {
                int colIndex = 1 + index;
                setRowWidthHeight(gridView, framelayout, colIndex);
                applyBorderForBody(framelayout, linearLayout, rowIndex, colIndex, false);
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "loadControlInGrid", e);
        }
    }

/*Log.d(TAG, "doInBackgroun_d: " + dataToServer);
    JSONObject jsonObjectMain = new JSONObject();
            try {
        jsonObjectMain.put("OrgId", strOrgId);
        jsonObjectMain.put("PageName", strAppName);
        jsonObjectMain.put("CreatedUserID", strCreatedBy);
        jsonObjectMain.put("SubmittedUserID", strUserId);
        jsonObjectMain.put("IMEI", "1234");
        jsonObjectMain.put("DistributionID", strDistributionId);
        jsonObjectMain.put("OperationType", "");
        jsonObjectMain.put("TransID", "");
        jsonObjectMain.put("TableName", "");


        JSONArray jsonArrayMain = new JSONArray();
        JSONObject jsonObjectSS = new JSONObject();
        for (int i = 0; i < dataToServer.size(); i++) {
            String strMain = dataToServer.get(i);
            String strSub[] = strMain.split("\\|");
            jsonObjectSS.put(strSub[0], strSub[1]);

        }
        jsonArrayMain.put(jsonObjectSS);

        jsonObjectMain.put("DataFields", jsonArrayMain);
        Log.d(TAG, "doInBackgroundTest: " + jsonObjectMain);

    } catch (JSONException e) {
        e.printStackTrace();
    }*/

    private void showControlDialog(LinearLayout controlView, CustomTextView tapToStart, boolean isCamera) {
        try {
            LinearLayout ll_displayName = controlView.getChildAt(0).findViewById(R.id.ll_displayName);
            ll_displayName.setVisibility(View.GONE);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            if (controlView.getParent() != null) {
                ((ViewGroup) controlView.getParent()).removeView(controlView);
            }
            builder.setView(controlView);

            controlView.getChildAt(0).setPadding(-8, -8, -8, -8);
            if (tapToStart != null) {
                if (isCamera) {
                    ((LinearLayout) tapToStart.getParent()).performClick();
                } else {
                    tapToStart.performClick();
                }

            }

            dialog = builder.create();
            dialog.show();
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "showControlDialog", e);
        }
    }

    private void showControlDialogCameraOrFile(LinearLayout controlView, LinearLayout tapToStart, boolean isCamera) {
        try {
            LinearLayout ll_displayName = controlView.getChildAt(0).findViewById(R.id.ll_displayName);
            ll_displayName.setVisibility(View.GONE);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            if (controlView.getParent() != null) {
                ((ViewGroup) controlView.getParent()).removeView(controlView);
            }
            builder.setView(controlView);

            controlView.getChildAt(0).setPadding(-8, -8, -8, -8);
            if (tapToStart != null) {
                if (isCamera) {
                    ((LinearLayout) tapToStart.getParent()).performClick();
                } else {
                    tapToStart.performClick();
                }

            }

            dialog = builder.create();
            dialog.show();
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "showControlDialog", e);
        }
    }

    public void onSearchCalled() {
        try {
            // Set the fields to specify which types of place data to return.
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
            // Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields).setCountry("IN")
                    .build(context);
            context.startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "onSearchCalled", e);
        }
    }

    public List<List<String>> getSubFormString() {

        return sampleStr;
    }

    public List<List<HashMap<String, String>>> getSubFormFileString() {

        return sampleFilesStr;
    }

    public List<LinkedHashMap<String, Object>> getList_ControlClassObjects() {

        return gridControl_List_ControlClassObjects;
    }

    public void setiMinRows(int iMinRows) {
        this.iMinRows = iMinRows;
    }

    public void setiMaxRows(int iMaxRows) {
        this.iMaxRows = iMaxRows;
    }

    public Activity getContext() {

        return context;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    private void primaryLayoutData(UIPrimaryLayoutModelClass uiPrimaryLayoutModelClass, LinearLayout linearLayout, LinkedHashMap<String, Object> new_list_ControlClassObjects) {
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
                                mappingControlLayout, new_list_ControlClassObjects, controlUIProperties);
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

    public void addLayoutToPrimary(LinearLayout ll_innerSubFormContainer, LayoutProperties layoutProperties, UILayoutProperties uiLayoutProperties, MappingControlModel mappingControlModel,
                                   LinkedHashMap<String, Object> New_list_ControlClassObjects, ControlUIProperties controlUIProperties) {
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

        applyUiProperties(layout_addMain, uiLayoutProperties, mappingControlModel, rView, New_list_ControlClassObjects, false, controlUIProperties);
//        applyUiProperties(ll_innerSubFormContainer, uiLayoutProperties, mappingControlModel, rView, New_list_ControlClassObjects, false);


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
                addSubLayoutToLayout(ll_addSubLayoutContainer, layoutProperties, uiSubLayoutProperties, mappingControlModelSub, viewH, New_list_ControlClassObjects, controlUIPropertiesSL);
            }
        }

    }

    public void applyUiProperties(LinearLayout ll_container, UILayoutProperties uiLayoutProperties,
                                  MappingControlModel mappingControlModel, View rView, LinkedHashMap<String, Object> New_list_ControlClassObjects, boolean isSubLayout, ControlUIProperties controlUIProperties) {

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
//            ControlObject controlObjectSelected = globalControlObjects.get(controlObject.getControlName());
            if (controlObject != null) {
                loadControl(controlObject, mappingControlModel.getControlType(),
                        ll_container, New_list_ControlClassObjects,
                        ll_MainSubFormContainer.getChildCount(), uiLayoutProperties, controlUIProperties);
            }

        }
    }

    public void addSubLayoutToLayout(LinearLayout ll_addSubLayoutContainer, LayoutProperties parentLayoutProperties, UILayoutProperties uiSubLayoutProperties, MappingControlModel mappingControlModelSub, int layoutHeight, LinkedHashMap<String, Object> new_list_ControlClassObjects, ControlUIProperties controlUIProperties) {
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
        applyUiProperties(ll_subLayoutMain, uiSubLayoutProperties, mappingControlModelSub, null, new_list_ControlClassObjects, true, controlUIProperties);

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

    public void setMarginToLinearLayout(LinearLayout linearLayout, int marginLeft, int marginTop, int marginRight, int marginBottom, int viewH) {
        try {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, viewH);
            layoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom);
            linearLayout.setLayoutParams(layoutParams);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "setMarginToLinearLayout", e);
        }
    }

    public void setParamsMatchParent(View rView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rView.setLayoutParams(layoutParams);
    }

    public int pxToDP(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics());
    }

    public CustomButton btn_addSubFormMain() {
        return btn_addSubForm;
    }

    public CustomButton dlt_SubForm() {
        return dlt_SubForm;
    }

    public ImageView iv_deleteSubForm() {
        return iv_deleteSubForm;
    }

    public LinearLayout ll_MainSubFormContainer() {
        return ll_MainSubFormContainer;
    }


    public View getAddStripView() {
        return view;
    }
/*
// Present Working delete
    public void dlt_SubFormOnClick(LinearLayout ll_MainSubFormContainer) {
        if (ll_MainSubFormContainer.getChildCount() > iMinRows) {
            ll_MainSubFormContainer.removeViewAt(ll_MainSubFormContainer.getChildCount() - 1);
            if (ll_MainSubFormContainer.getChildCount() == iMinRows) {
                dlt_SubForm.setVisibility(View.GONE);
                btn_addSubForm.setVisibility(View.VISIBLE);
            }
        }

    }
*/

    public void btn_addSubFormClick(LinearLayout ll_MainSubFormContainer) {
        if (ll_MainSubFormContainer != null && ll_MainSubFormContainer.getChildCount() >= 0) {
            if (ll_MainSubFormContainer.getChildCount() >= iMinRows && isGrid) {
                dlt_SubForm.setVisibility(View.VISIBLE);
            }

            if (ll_MainSubFormContainer.getChildCount() != iMaxRows) {

                addInnerSubFormStrip(context, ll_MainSubFormContainer, false);

            }
        }
        if (subFormDeleteInterface != null) {
            Log.d(TAG, "btn_addSubFormClick: " + "One");
            subFormDeleteInterface.bothWrapContentAndDp(controlObject.getControlType(), controlObject.getControlName(), scrollView);
        }
    }

    public void dlt_SubFormOnClick(LinearLayout ll_MainSubFormContainer) {
        try {
            if (ll_MainSubFormContainer.getChildCount() > iMinRows) {

                for (int i = 0; i < ll_MainSubFormContainer.getChildCount(); i++) {
                    View view = ll_MainSubFormContainer.getChildAt(i);
                    ll_MainSubFormContainer.removeView(view);

                }
                ll_MainSubFormContainer.invalidate();

                if (ll_MainSubFormContainer.getChildCount() == iMinRows) {
                    dlt_SubForm.setVisibility(View.GONE);
                    btn_addSubForm.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "dlt_SubFormOnClick", e);
        }
    }

    public void saveNewRowData(LinkedHashMap<String, List<String>> outputData, List<API_OutputParam_Bean> list_Form_OutParams) {
        try {
            this.outputData = outputData;
            this.list_Form_OutParams = list_Form_OutParams;
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "saveNewRowData", e);
        }
    }

    public TableLayout getll_grid_view() {
        return tl_body;
    }

    public CustomButton btn_addRow() {
        return addRow;
    }

    public CustomButton deleteRow() {
        return deleteRow;
    }

    public void addRowClick(TableLayout tableLayoutView) {
        try {
            if (tableLayoutView.getChildCount() >= iMinRows) {
                deleteRow.setVisibility(View.VISIBLE);
            }
            deleteRow.setVisibility(View.GONE);

            if (tableLayoutView.getChildCount() != iMaxRows) {
                addGridRow();
                if (tableLayoutView.getChildCount() == iMaxRows) {
                    addRow.setVisibility(View.GONE);
                }
                if (subFormDeleteInterface != null && isUIFormNeeded) {
                    subFormDeleteInterface.bothWrapContentAndDp(controlObject.getControlType(), controlObject.getControlName(), scrollView);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "addRowClick", e);
        }
    }

    public void deleteRowClick(TableLayout tableLayoutView) {
        try {
            if (tableLayoutView.getChildCount() > iMinRows) {
                tableLayoutView.removeViewAt(tableLayoutView.getChildCount() - 1);
                if (tableLayoutView.getChildCount() == iMinRows) {
                    deleteRow.setVisibility(View.GONE);

                }
                if (tableLayoutView.getChildCount() < iMaxRows) {
                    addRow.setVisibility(View.VISIBLE);
                }
                if (subFormDeleteInterface != null && isUIFormNeeded) {
                    subFormDeleteInterface.bothWrapContentAndDp(controlObject.getControlType(), controlObject.getControlName(), scrollView);
                }

            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "deleteRowClick", e);
        }
    }

    public void setCustomClickListener(SubFormDeleteInterface subFormDeleteInterface) {
        this.subFormDeleteInterface = subFormDeleteInterface;
    }

    public Boolean autoNumbersAvailable() {
        return isAutoNumbersAvaliable;
    }

    public JSONArray autoNumbersArray() {
        return jArrayAutoIncrementControls;
    }

    public void cornerRadiusWithBGColor(View view) {
        try {
            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(30);
            shape.setColor(Color.parseColor(controlObject.getTextHexColor()));
            // now find your view and add background to it
            View viewG = view.findViewById(R.id.ll_subForm);
            viewG.setBackground(shape);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "cornerRadiusWithBGColor", e);
        }
    }

    public void deleteStripsUpdateAfterApiCall(LinearLayout ll_MainSubFormContainer) {
        try {
            if (ll_MainSubFormContainer.getChildCount() > iMinRows) {
                for (int i = 0; i < ll_MainSubFormContainer.getChildCount(); i++) {
                    View rView = ll_MainSubFormContainer.getChildAt(i);
                    ImageView iv_deleteSubFormC = rView.findViewById(R.id.iv_deleteSubForm);
                    if (iv_deleteSubFormC != null) {
                        iv_deleteSubFormC.setVisibility(View.VISIBLE);
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "deleteStripsUpdateAfterApiCall", e);
        }
    }

    private boolean checkmainformHasSubFormColumns() {
        boolean result = false;
        try {
            for (int j = 0; j < list_Control.size(); j++) {
                if (((MainActivity) context).dataCollectionObject.getList_Table_Columns() != null) {
                    if (((MainActivity) context).dataCollectionObject.getList_Table_Columns().contains(list_Control.get(j).getControlName())) {
                        result = true;
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "checkmainformHasSubFormColumns", e);
        }
        return result;
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


    public boolean isEnabled() {

        return !controlObject.isDisable();
    }


    public void setEnabled(View rView, boolean enabled) {
//        setViewDisable(view, !enabled);
        controlObject.setDisable(!enabled);
        setViewDisableOrEnableDefault(context, view, enabled);
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



    public boolean isGridWithTwoColumns() {
        return controlObject.isGridWithTwoColumns();
    }



    public String getDisplayOrientation() {
        return controlObject.getDisplayOrientation();
    }

    public void setDisplayOrientation(String displayOrientation) {
        if (displayOrientation.equalsIgnoreCase("0")) {
            controlObject.setDisplayNameAlignment("7");
            controlObject.setDisplayOrientation(context.getString(R.string.horizontal));
            ll_MainSubFormContainer().setVisibility(View.GONE);
        }
        linearLayout.removeAllViews();
    }

    public boolean isSearchEnable() {
        return controlObject.isSearchEnable();
    }

    public void setSearchEnable(boolean searchEnable) {
        controlObject.setSearchEnable(searchEnable);
        if (controlObject.isSearchEnable()) {
            iv_search.setVisibility(View.VISIBLE);
        } else {
            iv_search.setVisibility(View.GONE);
        }
    }

    public String getDisplayName() {
        return controlObject.getDisplayName();
    }





    /*General*/
    /*displayName,hint,minimumRows,maximumRows,minimumRowsError,maximumRowsError*/

  /*  public String getDisplayName() {
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

    public String getMinimumRows() {
        return controlObject.getMinimumRows();
    }

    public void setMinimumRows(String minimumRows) {
        controlObject.setMinimumRows(minimumRows);
    }

    public String getMaximumRows() {
        return controlObject.getMaximumRows();
    }

    public void setMaximumRows(String maximumRows) {
        controlObject.setMaximumRows(maximumRows);
    }

    public String getMinimumRowsError() {
        return controlObject.getMinimumRowsError();
    }

    public void setMinimumRowsError(String minimumRowsError) {
        controlObject.setMinimumRowsError(minimumRowsError);
    }

    public String getMaximumRowsError() {
        return controlObject.getMaximumRowsError();
    }

    public void setMaximumRowsError(String maximumRowsError) {
        controlObject.setMaximumRowsError(maximumRowsError);
    }
    /*Options*/
    /*hideDisplayName,enableDisplayOrientation,gridWithTwoColumns,searchEnable,displayOrientation,
    enableDisplayNameOfAddButton,displayNameOfAddButton*/

    public void setDisplayName(String displayName) {
        controlObject.setDisplayName(displayName);
        tv_displayName.setText(displayName);
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

    public void setMin(String min, String typeOfView) {
        int newiMinRows = Integer.parseInt(min);
        if (typeOfView.equalsIgnoreCase("gridview")) {
            int currentRowsCount = tl_body.getChildCount();
            if (currentRowsCount < newiMinRows) {
                for (int i = currentRowsCount; i < newiMinRows; i++) {
                    addGridRow();
                }
            }
        }
        controlObject.setMinimumRows(min);
        iMinRows = Integer.parseInt(min);
    }

    public void setMax(String max, String typeOfView) {
        int newiMaxRows = Integer.parseInt(max);
        if (typeOfView.equalsIgnoreCase("gridview")) {
            int currentRowsCount = tl_body.getChildCount();
            if (currentRowsCount > newiMaxRows) {
                for (int i = currentRowsCount - 1; i > newiMaxRows - 1; i--) {
                    tl_body.removeViewAt(i);
                }
            } else if (currentRowsCount == newiMaxRows) {
                addRow.setVisibility(View.GONE);
            } else {
                if (currentRowsCount == 0) {
                    SubFormTAG = 0;
                }
                for (int i = currentRowsCount; i < newiMaxRows; i++) {
                    addGridRow();
                }
            }
        }
        controlObject.setMaximumRows(max);
        iMaxRows = Integer.parseInt(max);

    }

    public void setMinMax(String min, String max, String typeOfView) {
        int newiMinRows = Integer.parseInt(min);
        int newiMaxRows = Integer.parseInt(max);
        if (typeOfView.equalsIgnoreCase("gridview")) {
            int currentRowsCount = tl_body.getChildCount();
            if (currentRowsCount > newiMaxRows) {
                for (int i = currentRowsCount - 1; i > newiMaxRows - 1; i--) {
                    tl_body.removeViewAt(i);
                }
            }
            if (currentRowsCount < newiMinRows) {
                for (int i = currentRowsCount; i < newiMinRows; i++) {
                    addGridRow();
                }
            }
            if (tl_body.getChildCount() == newiMaxRows) {
                addRow.setVisibility(View.GONE);
            }
        }
        controlObject.setMinimumRows(min);
        controlObject.setMaximumRows(max);
        iMinRows = Integer.parseInt(min);
        iMaxRows = Integer.parseInt(max);

    }

    public boolean isHideDisplayName() {
        return controlObject.isHideDisplayName();
    }













    /*General*/
    /*displayName,hint,minimumRows,maximumRows,minimumRowsError,maximumRowsError*/

  /*  public String getDisplayName() {
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

    public String getMinimumRows() {
        return controlObject.getMinimumRows();
    }

    public void setMinimumRows(String minimumRows) {
        controlObject.setMinimumRows(minimumRows);
    }

    public String getMaximumRows() {
        return controlObject.getMaximumRows();
    }

    public void setMaximumRows(String maximumRows) {
        controlObject.setMaximumRows(maximumRows);
    }

    public String getMinimumRowsError() {
        return controlObject.getMinimumRowsError();
    }

    public void setMinimumRowsError(String minimumRowsError) {
        controlObject.setMinimumRowsError(minimumRowsError);
    }

    public String getMaximumRowsError() {
        return controlObject.getMaximumRowsError();
    }

    public void setMaximumRowsError(String maximumRowsError) {
        controlObject.setMaximumRowsError(maximumRowsError);
    }
    /*Options*/
    /*hideDisplayName,enableDisplayOrientation,gridWithTwoColumns,searchEnable,displayOrientation,
    enableDisplayNameOfAddButton,displayNameOfAddButton*/

    public void setHideDisplayName(boolean hideDisplayName) {
        if (hideDisplayName) {
            tv_displayName.setVisibility(View.GONE);
        } else {
            tv_displayName.setVisibility(View.VISIBLE);
        }
        controlObject.setHideDisplayName(hideDisplayName);
    }

    public boolean isEnableDisplayOrientation() {
        return controlObject.isEnableDisplayOrientation();
    }

    public void setEnableDisplayOrientation(boolean enableDisplayOrientation) {
        controlObject.setEnableDisplayOrientation(enableDisplayOrientation);
    }

    public boolean isEnableDisplayNameOfAddButton() {
        return controlObject.isEnableDisplayNameOfAddButton();
    }

    public void setEnableDisplayNameOfAddButton(boolean enableDisplayNameOfAddButton) {
        controlObject.setEnableDisplayNameOfAddButton(enableDisplayNameOfAddButton);
    }

    public String getDisplayNameOfAddButton() {
        return controlObject.getDisplayNameOfAddButton();
    }

    public void setDisplayNameOfAddButton(String displayNameOfAddButton) {
        controlObject.setDisplayNameOfAddButton(displayNameOfAddButton);
    }

    public void disableSubFormControls(List<ControlObject> subformControlsList, List<String> editColumns) {
        List<String> disablenames = new ArrayList<String>();
        for (int i = 0; i < subformControlsList.size(); i++) {
            if (!subformControlsList.get(i).getControlType().equalsIgnoreCase(CONTROL_TYPE_LiveTracking) && !editColumns.contains(subformControlsList.get(i).getControlName())) {
                disablenames.add(subformControlsList.get(i).getControlName());
            }
        }
        SetDisable(disablenames);
    }

    /*Background*/
    /*Pending*/

    public void SetDisable(List<String> disabledControlNames) {
        HashMap<String, String> controlPositionInUI = new HashMap<>();
        controlPositionInUI = AppConstants.controlPositionInUIAllApps.get(appname);
        LinearLayout temp_ll_innerSubFormContainer = ll_innerSubFormContainer1;
        try {
            for (int i = 0; i < controlObject.getSubFormControlList().size(); i++) {
                ll_innerSubFormContainer1 = temp_ll_innerSubFormContainer;
                ControlObject temp_controlObj = controlObject.getSubFormControlList().get(i);
                if (disabledControlNames.contains(temp_controlObj.getControlName()) && alwaysEnable(temp_controlObj.getControlType())) {
                    if (isUIFormNeeded) {//If form has custom UI
                        if (ll_innerSubFormContainer1.getChildCount() != 0) {
                            String controlPos = controlPositionInUI.get(temp_controlObj.getControlName());
                            if (controlPos != null && controlPos.contains("$")) {//If control is in sublayout
                                Log.d("controlPos: ", controlPos + temp_controlObj.getControlName());
                                String[] positions = controlPos.split("\\$");
                                int layoutPosition = Integer.parseInt(positions[0]);
                                int sublayoutPosition = Integer.parseInt(positions[1]);
                                ll_innerSubFormContainer1 = (LinearLayout) ((LinearLayout) ((HorizontalScrollView) ((LinearLayout) ll_innerSubFormContainer1.getChildAt(layoutPosition)).getChildAt(1)).getChildAt(0)).getChildAt(sublayoutPosition);
                                setViewDisable(ll_innerSubFormContainer1.getChildAt(i), false);
                            } else {
                                ll_innerSubFormContainer1 = (LinearLayout) ((LinearLayout) ll_innerSubFormContainer1.getChildAt(Integer.parseInt(controlPos))).getChildAt(2);
                                setViewDisable(ll_innerSubFormContainer1.getChildAt(i), false);
                            }
                        }
                    } else {
                        setViewDisable(ll_innerSubFormContainer1.getChildAt(i), false);
                        Log.d(TAG, "SetDisableCheck: " + "3");
                    }
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "SetDisable", e);
        }
    }

    public boolean alwaysEnable(String controlType) {
        boolean result = true;
        try {
            if (controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_BUTTON)
                    || controlType.equalsIgnoreCase(CONTROL_TYPE_AUTO_NUMBER)
                    || controlType.equalsIgnoreCase(CONTROL_TYPE_AUTO_GENERATION)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_URL_LINK)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_DYNAMIC_LABEL)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_MENU)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_CALENDAR_EVENT)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_CHART)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_PROGRESS)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIEWFILE)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_COUNT_DOWN_TIMER)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_COUNT_UP_TIMER)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_SUBFORM)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_SECTION)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_AUDIO_PLAYER)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_VIDEO_PLAYER)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_IMAGE)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_DATA_VIEWER)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_MAP)
                    || controlType.equalsIgnoreCase(AppConstants.CONTROL_TYPE_SUBMIT_BUTTON)) {
                result = false;
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "alwaysEnable", e);
        }
        return result;
    }

    public void showProgressDialog(String msg) {
        try {
            pd = new ProgressDialog(context);
//             pd = CustomProgressDialog.ctor(this, msg);
            pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            pd.setMessage(msg);
            pd.setCancelable(false);
            pd.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeProgressDialog() {
        try {
            if (pd != null)
                pd.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gridControlSetPropertiesAction(List<Param> propertiesListMain) {
        try {
            List<Param> propertiesList = new ArrayList<>();
            String propertyText = "";
            ExpressionMainHelper expressionMainHelper = new ExpressionMainHelper();
            propertiesList = propertiesListMain;
            for (int i = 0; i < propertiesList.size(); i++) {

                Param property = propertiesList.get(i);
                if (property.getName().contentEquals("ExpressionBuilder")) {
                    propertyText = expressionMainHelper.ExpressionHelper(context, property.getText());
                } else {
                    propertyText = property.getText();
                }

                if (!propertyText.contentEquals("")) {
//                Log.d(TAG, "gridControlSetPropertiesAction: "+property.getValue()+" - "+propertyText);
                    if (property.getValue().contentEquals(PropertiesNames.DISPLAY_NAME)) {
                        tv_displayName.setText(propertyText);
                        controlObject.setDisplayName(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.HINT)) {
                        setHint(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.MIN_ROW)) {
                        setMin(propertyText, "gridview");
                    } else if (property.getValue().contentEquals(PropertiesNames.MAX_ROW)) {
                        iMaxRows = Integer.parseInt(propertyText);
                        controlObject.setMaximumRows(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.COLUMN_SETTINGS_LIST)) {
                        String propertyArrayString = propertyText;
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(propertyArrayString);
                            List<GridColumnSettings> gridColumnSettingsMain = new ArrayList<>();
                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(j);
                                GridColumnSettings gridColumnSettings = new GridColumnSettings();
                                gridColumnSettings.setControlId(jsonObject.getString("_controlId"));
                                gridColumnSettings.setControlName(jsonObject.getString("_controlName"));
                                gridColumnSettings.setControlWidth(jsonObject.getString("_controlWidth"));
                                gridColumnSettings.setControlColor(jsonObject.getString("_controlColor"));
                                gridColumnSettings.setEnableSorting(jsonObject.getBoolean("_enableSorting"));
                                gridColumnSettingsMain.add(gridColumnSettings);
                                Log.d(TAG, "gridColumnSettingsList: " + gridColumnSettings.getControlColor());
                            }
                            controlObject.setGridColumnSettings(gridColumnSettingsMain);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (property.getValue().contentEquals(PropertiesNames.COLUMN_HEIGHT_TYPE)) {
                        controlObject.setGridControl_ColHeightType(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.COLUMN_HEIGHT_FIXED_SIZE)) {
                        controlObject.setGridControl_ColHeightSize(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.COL_TEXT_SIZE)) {
                        controlObject.setGridControl_ColTextSize(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.COL_TEXT_STYLE)) {
                        controlObject.setGridControl_ColTextStyle(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.COL_TEXT_COLOR)) {
                        controlObject.setGridControl_ColTextColor(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.COL_TEXT_ALIGNMENT)) {
                        controlObject.setGridControl_ColTextAlignment(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.COL_TEXT_ALIGNMENT)) {
                        controlObject.setGridControl_ColTextAlignment(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.COL_COLOR)) {
                        controlObject.setGridControl_ColColor(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.COL_BORDER)) {
                        controlObject.setGridControl_ColBorder(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.HIDE_HEADER_COL)) {
                        controlObject.setHideColumnNames(Boolean.parseBoolean(propertyText));
                    } else if (property.getValue().contentEquals(PropertiesNames.ROW_HEIGHT_TYPE)) {
                        controlObject.setGridControl_rowHeigthType(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.ROW_HEIGHT_FIXED_SIZE)) {
                        controlObject.setGridControl_rowHeightSize(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.ROW_COLOR_TYPE)) {
                        controlObject.setGridControl_rowColorType(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.ROW_COLOR_1)) {
                        controlObject.setGridControl_rowColor1(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.ROW_COLOR_2)) {
                        controlObject.setGridControl_rowColor2(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.CONTROL_BORDER_TYPE)) {
                        controlObject.setGridControl_BorderType(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.CONTROL_BORDER_COLOR)) {
                        controlObject.setGridControl_BorderColor(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.CONTROL_BORDER_THICKNESS)) {
                        controlObject.setGridControl_BorderThickness(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.HIDE_DISPLAY_NAME)) {
                        controlObject.setHideDisplayName(Boolean.parseBoolean(propertyText));
                        if (Boolean.parseBoolean(propertyText)) {
                            tv_displayName.setVisibility(View.GONE);
                        }
                    } else if (property.getValue().contentEquals(PropertiesNames.HIDE_ADD_COLUMN)) {
                        controlObject.setGridControl_HideAddButton(Boolean.parseBoolean(propertyText));
                    } else if (property.getValue().contentEquals(PropertiesNames.HIDE_DELETE_COLUMN)) {
                        controlObject.setGridControl_HideDeleteButton(Boolean.parseBoolean(propertyText));
                    } else if (property.getValue().contentEquals(PropertiesNames.FONT_SIZE)) {
                        controlObject.setTextSize(propertyText);
                    } else if (property.getValue().contentEquals(PropertiesNames.FONT_COLOR)) {
                        controlObject.setTextColor(Color.parseColor(propertyText));
                    } else if (property.getValue().contentEquals(PropertiesNames.FONT_STYLE)) {
                        controlObject.setTextStyle(setStyleFromIndex(propertyText));
                    }
                }
            }

            // HeaderRow Set Properties
           /* tl_header.removeAllViews();
            tl_body.removeAllViews();
            headerRow();
            for (int i = 0; i < iMinRows; i++) {
                addGridRow();
            }*/
            boolean delColExist = false;
            for (int i = 0; i < tl_header.getChildCount(); i++) {
                TableRow tableRow = (TableRow) tl_header.getChildAt(i);
                if (tableRow.getChildCount() != controlObject.getGridColumnSettings().size()) {
                    delColExist = true;
                }
                if (controlObject.isHideColumnNames()) {
                    tableRow.setVisibility(View.GONE);
                } else {
                    for (int row = 0; row < tableRow.getChildCount(); row++) {

                        View headerView = tableRow.getChildAt(row);

                        FrameLayout framelayout = headerView.findViewById(R.id.framelayout);
                        CustomTextView tv_label = headerView.findViewById(R.id.tv_label);
                        updateColCellSettings(headerView);
                        if (delColExist) {
                            //if HideDel_Bt true: in tablerow gone deleteView
                            //if HideDel_Bt false: in tablerow visible deleteView
                            if(row==0){
                                if (controlObject.isGridControl_HideDeleteButton()) {
                                    headerView.setVisibility(View.GONE);
                                } else {
                                    headerView.setVisibility(View.VISIBLE);
                                }
                            }

                            int colIndex = row;
                            if (row > 0) {
                                if (controlObject.getGridColumnSettings().get(colIndex - 1).getControlColor() != null) {
                                    tv_label.setBackgroundColor(Color.parseColor(controlObject.getGridColumnSettings().get(colIndex - 1).getControlColor()));
                                }
                                setHeaderWidthHeight(headerView, framelayout, Integer.parseInt(controlObject.getGridColumnSettings().get(colIndex - 1).getControlWidth()));
                            }
                            applyBorderForHeader(framelayout, tv_label, 0, colIndex + 1, true);
                        } else {//no delete View in Table Row Case
                            //if HideDel_Bt true: no need to add deleteView in tablerow
                            //if HideDel_Bt false: add deleteView in tablerow
                            if (controlObject.isGridControl_HideDeleteButton()) {

                            } else {

                            }
                            if (controlObject.getGridColumnSettings().get(row).getControlColor() != null) {
                                tv_label.setBackgroundColor(Color.parseColor(controlObject.getGridColumnSettings().get(row).getControlColor()));
                            }
                            setHeaderWidthHeight(headerView, framelayout, Integer.parseInt(controlObject.getGridColumnSettings().get(row).getControlWidth()));
                            applyBorderForHeader(framelayout, tv_label, 0, row, true);

                        }
                    }
                }
            }

            // Cell (Row or Child) SetProperties

            for (int i = 0; i < tl_body.getChildCount(); i++) {
                TableRow tableRow = (TableRow) tl_body.getChildAt(i);
                for (int j = 0; j < tableRow.getChildCount(); j++) {
                    View cellView = tableRow.getChildAt(j);
                    LinearLayout linearLayout = cellView.findViewById(R.id.ll_control_view);
                    FrameLayout framelayout = cellView.findViewById(R.id.framelayout);
                    updateRowCellSettings(linearLayout, i);
                    //col width & height
                    if (delColExist) {
                        //if HideDel_Bt true: in tablerow gone deleteView
                        //if HideDel_Bt false: in tablerow visible deleteView
                        if(j==0){
                            if (controlObject.isGridControl_HideDeleteButton()) {
                                cellView.setVisibility(View.GONE);
                            } else {
                                cellView.setVisibility(View.VISIBLE);
                            }
                        }

                        int colIndex = j;
                        setRowWidthHeight(cellView, framelayout, colIndex);
                        applyBorderForBody(framelayout, linearLayout, i, colIndex + 1, false);
                    } else {
                        if (controlObject.isGridControl_HideDeleteButton()) {

                        } else {

                        }
                        setRowWidthHeight(cellView, framelayout, j);
                        applyBorderForBody(framelayout, linearLayout, i, j, false);
                    }
                }
            }
            if (controlObject.isGridControl_HideAddButton()) {
                btn_addRow().setVisibility(View.GONE);
            } else {
                btn_addRow().setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Log.d(TAG, "gridControlSetPropertiesAction: " + e);
        }

    }

    public String setStyleFromIndex(String propertyText) {

        if (propertyText.equalsIgnoreCase("0")) {
            propertyText = "Bold";
        } else if (propertyText.equalsIgnoreCase("1")) {
            propertyText = "Italic";
        }
        return propertyText;
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

                    final TextInput textInput = (TextInput) gridControl_List_ControlClassObjects.get(pos).get(loadControlObject.getControlName());

                    //////////////////////////
                    if (loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        textInput.gettap_text().setVisibility(View.VISIBLE); // taptext
                        textInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        if (loadControlObject.isReadFromBarcode()) {
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
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
                            textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
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
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
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
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
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
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
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
                        textInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
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
                case CONTROL_TYPE_NUMERIC_INPUT:
                    AppConstants.Current_ClickorChangeTagName = controlObject.getControlName().trim();
                    AppConstants.SF_ClickorChangeTagName = loadControlObject.getControlName().trim();
                    AppConstants.SF_Container_position = pos;

                    final NumericInput numericInput = (NumericInput) gridControl_List_ControlClassObjects.get(pos).get(loadControlObject.getControlName());

                    //////////////////////////
                    if (loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        numericInput.gettap_text().setVisibility(View.VISIBLE); // taptext
                        numericInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        if (loadControlObject.isReadFromBarcode()) {
                            numericInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                            numericInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                        if (AppConstants.EventCallsFrom == 1) {
                                            ((MainActivity) context).ChangeEvent(v);
                                        }
                                    }

                                    numericInput.tQRBarCode();
                                }
                            });
                        } else if (loadControlObject.isReadFromQRCode()) {
                            numericInput.gettap_text().setVisibility(View.VISIBLE);
                            numericInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                            numericInput.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                        if (AppConstants.EventCallsFrom == 1) {
                                            ((MainActivity) context).ChangeEvent(v);
                                        }
                                    }

                                    numericInput.tQRBarCode();
                                }
                            });
                        } else if (loadControlObject.isReadFromBarcode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                            numericInput.gettap_text().setVisibility(View.GONE);
                            numericInput.getNumericTextView().setVisibility(View.VISIBLE);
                            numericInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                            numericInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }

                            numericInput.tQRBarCode();

                        } else if (loadControlObject.isReadFromQRCode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                            numericInput.gettap_text().setVisibility(View.GONE);
                            numericInput.getNumericTextView().setVisibility(View.VISIBLE);
                            numericInput.getIv_textTypeImage().setVisibility(View.VISIBLE);
                            numericInput.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }

                            numericInput.tQRBarCode();
                        }
                    } else if (loadControlObject.isReadFromBarcode()) {
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                ((MainActivity) context).ChangeEvent(v);
                            }
                        }

                        numericInput.tQRBarCode();

                    } else if (loadControlObject.isReadFromQRCode()) {
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                ((MainActivity) context).ChangeEvent(v);
                            }
                        }

                        numericInput.tQRBarCode();
                    }
                    break;
                case CONTROL_TYPE_PHONE:
                    AppConstants.Current_ClickorChangeTagName = controlObject.getControlName().trim();
                    AppConstants.SF_ClickorChangeTagName = loadControlObject.getControlName().trim();
                    AppConstants.SF_Container_position = pos;

                    final Phone phone = (Phone) gridControl_List_ControlClassObjects.get(pos).get(loadControlObject.getControlName());

                    //////////////////////////
                    if (loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        phone.gettap_text().setVisibility(View.VISIBLE); // taptext
                        phone.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        if (loadControlObject.isReadFromBarcode()) {
                            phone.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                            phone.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                        if (AppConstants.EventCallsFrom == 1) {
                                            ((MainActivity) context).ChangeEvent(v);
                                        }
                                    }

                                    phone.tQRBarCode();
                                }
                            });
                        } else if (loadControlObject.isReadFromQRCode()) {
                            phone.gettap_text().setVisibility(View.VISIBLE);
                            phone.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                            phone.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                        if (AppConstants.EventCallsFrom == 1) {
                                            ((MainActivity) context).ChangeEvent(v);
                                        }
                                    }

                                    phone.tQRBarCode();
                                }
                            });
                        } else if (loadControlObject.isReadFromBarcode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                            phone.gettap_text().setVisibility(View.GONE);
                            phone.getCustomEditText().setVisibility(View.VISIBLE);
                            phone.getIv_textTypeImage().setVisibility(View.VISIBLE);
                            phone.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }

                            phone.tQRBarCode();

                        } else if (loadControlObject.isReadFromQRCode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                            phone.gettap_text().setVisibility(View.GONE);
                            phone.getCustomEditText().setVisibility(View.VISIBLE);
                            phone.getIv_textTypeImage().setVisibility(View.VISIBLE);
                            phone.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }

                            phone.tQRBarCode();
                        }
                    } else if (loadControlObject.isReadFromBarcode()) {
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                ((MainActivity) context).ChangeEvent(v);
                            }
                        }

                        phone.tQRBarCode();

                    } else if (loadControlObject.isReadFromQRCode()) {
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                ((MainActivity) context).ChangeEvent(v);
                            }
                        }

                        phone.tQRBarCode();
                    }
                    break;
                case CONTROL_TYPE_EMAIL:
                    AppConstants.Current_ClickorChangeTagName = controlObject.getControlName().trim();
                    AppConstants.SF_ClickorChangeTagName = loadControlObject.getControlName().trim();
                    AppConstants.SF_Container_position = pos;

                    final Email email = (Email) gridControl_List_ControlClassObjects.get(pos).get(loadControlObject.getControlName());

                    //////////////////////////
                    if (loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                        email.gettap_text().setVisibility(View.VISIBLE); // taptext
                        email.getIv_textTypeImage().setVisibility(View.VISIBLE);
                        if (loadControlObject.isReadFromBarcode()) {
                            email.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                            email.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                        if (AppConstants.EventCallsFrom == 1) {
                                            ((MainActivity) context).ChangeEvent(v);
                                        }
                                    }

                                    email.tQRBarCode();
                                }
                            });
                        } else if (loadControlObject.isReadFromQRCode()) {
                            email.gettap_text().setVisibility(View.VISIBLE);
                            email.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                            email.getIv_textTypeImage().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                        if (AppConstants.EventCallsFrom == 1) {
                                            ((MainActivity) context).ChangeEvent(v);
                                        }
                                    }

                                    email.tQRBarCode();
                                }
                            });
                        } else if (loadControlObject.isReadFromBarcode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                            email.gettap_text().setVisibility(View.GONE);
                            email.getCustomEditText().setVisibility(View.VISIBLE);
                            email.getIv_textTypeImage().setVisibility(View.VISIBLE);
                            email.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_bar_code));
                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }

                            email.tQRBarCode();

                        } else if (loadControlObject.isReadFromQRCode() && loadControlObject.getDefaultValue() != null && !loadControlObject.getDefaultValue().isEmpty()) {
                            email.gettap_text().setVisibility(View.GONE);
                            email.getCustomEditText().setVisibility(View.VISIBLE);
                            email.getIv_textTypeImage().setVisibility(View.VISIBLE);
                            email.getIv_textTypeImage().setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_qr_code));
                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }

                            email.tQRBarCode();
                        }
                    } else if (loadControlObject.isReadFromBarcode()) {
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                ((MainActivity) context).ChangeEvent(v);
                            }
                        }

                        email.tQRBarCode();

                    } else if (loadControlObject.isReadFromQRCode()) {
                        if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                            if (AppConstants.EventCallsFrom == 1) {
                                ((MainActivity) context).ChangeEvent(v);
                            }
                        }

                        email.tQRBarCode();
                    }
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

                    final Camera camera = (Camera) gridControl_List_ControlClassObjects.get(pos).get(loadControlObject.getControlName());

                    camera.getCameraClickView(SF_REQUEST_CAMERA_CONTROL_CODE, activityView);
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

                    final SignatureView signature = (SignatureView) gridControl_List_ControlClassObjects.get(pos).get(loadControlObject.getControlName());
                    signature.getLayoutUpload().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            signature.setAlertSignature().setVisibility(View.GONE);
                            signature.getLayoutUpload().setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_default));

                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            context.startActivityForResult(intent, SF_REQUEST_SIGNATURE_CONTROL_CODE);

                        }
                    });
                    signature.getTv_clearSignature().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            signature.Clear();
                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }
                            signature.getClearSignature();
                        }
                    });

                    break;
                case CONTROL_TYPE_VOICE_RECORDING:

                    AppConstants.Current_ClickorChangeTagName = controlObject.getControlName().trim();
                    AppConstants.SF_ClickorChangeTagName = loadControlObject.getControlName().trim();
                    AppConstants.SF_Container_position = pos;

                    final VoiceRecording voiceRecording = (VoiceRecording) gridControl_List_ControlClassObjects.get(pos).get(loadControlObject.getControlName());
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

                    voiceRecording.getImageViewPlay().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }
                            voiceRecording.getPlayViewVoiceRecording();
                        }
                    });

                    voiceRecording.getImageViewStop().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (controlObject.isOnChangeEventExists() && !AppConstants.Initialize_Flag) {
                                if (AppConstants.EventCallsFrom == 1) {
                                    AppConstants.GlobalObjects.setCurrent_GPS("");
                                    ((MainActivity) context).ChangeEvent(v);
                                }
                            }
                            voiceRecording.getStopViewVoiceRecording();
                        }
                    });

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

                    final VideoRecording videoRecording = (VideoRecording) gridControl_List_ControlClassObjects.get(pos).get(loadControlObject.getControlName());
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

    public class LoadControlsFromNew extends AsyncTask<String, String, String> {

        LinkedHashMap<String, Object> New_list_ControlClassObjects = new LinkedHashMap<String, Object>();

        public LoadControlsFromNew(LinkedHashMap<String, Object> New_list_ControlClassObjects) {
            this.New_list_ControlClassObjects = New_list_ControlClassObjects;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String searchStr = "";
                        for (int i = 0; i < controlObject.getSubFormControlList().size(); i++) {
                            ControlObject co = (ControlObject) controlObject.getSubFormControlList().get(i).clone();
                            // ControlObject co1=controlObject.getSubFormControlList().get(i).getClass().newInstance();
                            if (controlObject.getDisplayNameAlignment() != null && controlObject.getDisplayNameAlignment().equalsIgnoreCase("3")) {
                                iv_deleteSubForm.setBackground(null);
                            }
                            if (controlObject.getSubFormControlList().get(i).getControlType().equals(CONTROL_TYPE_DYNAMIC_LABEL)) {
                                searchStr = searchStr + controlObject.getSubFormControlList().get(i).getValue();
                            }
                            loadControl(co,
                                    controlObject.getSubFormControlList().get(i).getControlType(),
                                    ll_innerSubFormContainer1, New_list_ControlClassObjects,
                                    ll_MainSubFormContainer.getChildCount(), null, null);

                        }
                        searchitems.add(searchStr);
                    } catch (Exception e) {
                        Log.d(TAG, "LoadControlsFromNew: " + e);
                        ImproveHelper.improveException(context, TAG, "LoadControlsFromNew", e);
                    }

                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: " + "dismiss");
            List<String> editColumnsOfSubForm = new ArrayList<>();
            editColumnsOfSubForm = improveHelper.getEditColumns(editColumns, AppConstants.SUB_CONTROL, controlObject.getControlName());
            if (editColumns != null && editColumnsOfSubForm.size() > 0) {
                if (app_edit != null && app_edit.equalsIgnoreCase("edit")) {
                    disableSubFormControls(controlObject.getSubFormControlList(), editColumnsOfSubForm);
                }
            }
        }
    }


}