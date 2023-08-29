package com.bhargo.user.utils;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUDIO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_COMPLETION;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_BAR_CODE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CALENDER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CAMERA;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHECKBOX;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CHECK_LIST;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_COUNT_DOWN_TIMER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_COUNT_UP_TIMER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CURRENCY;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DATA_CONTROL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DECIMAL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DROP_DOWN;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_DYNAMIC_LABEL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_EMAIL;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_FILE_BROWSING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_GPS;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_GRID_CONTROL;
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
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_SUBFORM;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_TEXT_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_TIME;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_URL_LINK;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_USER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIDEO_RECORDING;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VIEWFILE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_VOICE_RECORDING;
import static com.bhargo.user.utils.ImproveHelper.isNetworkStatusAvialable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.bhargo.user.Expression.ExpressionMainHelper;
import com.bhargo.user.Java_Beans.API_OutputParam_Bean;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.DataCollectionObject;
import com.bhargo.user.Java_Beans.ImageAdvanced_Mapped_Item;
import com.bhargo.user.Java_Beans.Item;
import com.bhargo.user.Java_Beans.LanguageMapping;
import com.bhargo.user.MainActivity;
import com.bhargo.user.controls.advanced.AutoCompletionControl;
import com.bhargo.user.controls.advanced.BarCode;
import com.bhargo.user.controls.advanced.Gps_Control;
import com.bhargo.user.controls.advanced.GridControl;
import com.bhargo.user.controls.advanced.PostControl;
import com.bhargo.user.controls.advanced.QRCode;
import com.bhargo.user.controls.advanced.SubformView;
import com.bhargo.user.controls.advanced.UserControl;
import com.bhargo.user.controls.data_controls.DataControl;
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
import com.bhargo.user.controls.standard.Time;
import com.bhargo.user.controls.standard.UrlView;
import com.bhargo.user.controls.standard.VideoRecording;
import com.bhargo.user.controls.standard.ViewFileControl;
import com.bhargo.user.controls.standard.VoiceRecording;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.ControlData;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import nk.mobileapps.spinnerlib.SearchableSpinner;

public class ControlUtils {
    static String TAG = "ControlUtils ";

    public static void setValueToPopupSectionForEditCase(Context context, List<ControlObject> sectionControlList, HashMap<String, Object> selectedPopUpData, LinkedHashMap<String, Object> List_ControlClassObjects) {
        for (int k = 0; k < sectionControlList.size(); k++) {
            sectionControlList.get(k).setSectionControl(false);
            String controlName = sectionControlList.get(k).getControlName();
            String controlType = sectionControlList.get(k).getControlType();
            Object editDataObj = null; //SectionMainControl :ControlData, SectionSubFormControl:ArrayList
            if (selectedPopUpData != null) {
                editDataObj = selectedPopUpData.get(sectionControlList.get(k).getControlName());
            }
            switch (controlType) {
                case CONTROL_TYPE_SUBFORM:
                    if (editDataObj != null) {
                        List<HashMap<String, Object>> lRowData = (List<HashMap<String, Object>>) editDataObj;
                        if (lRowData != null && lRowData.size() > 0) {
                            SubformView subformView = (SubformView) List_ControlClassObjects.get(controlName);
                            List<ControlObject> subFormControlList = subformView.controlObject.getSubFormControlList();
                            List<LinkedHashMap<String, Object>> List_ControlClassObjects_sub = subformView.subform_List_ControlClassObjects;
                            for (int totRows = 0; totRows < List_ControlClassObjects_sub.size(); totRows++) {
                                HashMap<String, Object> subFormRowEditData = lRowData.get(totRows);
                                LinkedHashMap<String, Object> subFormRowViews = List_ControlClassObjects_sub.get(totRows);
                                for (int i = 0; i < subFormControlList.size(); i++) {
                                    Object rowEditDataObj = subFormRowEditData.get(subFormControlList.get(i).getControlName());
                                    setDataToControl(context, subFormControlList.get(i).getControlType(), subFormControlList.get(i).getControlName(), subFormRowViews, rowEditDataObj);
                                }
                            }
                        }
                    }
                    //key:subFormControlName=Value: List of RowData(HashMap<String, Object>) : RowData Key:controlNameinsubform Value:ControlData
                    break;
                case CONTROL_TYPE_GRID_CONTROL:
                    if (editDataObj != null) {
                        List<HashMap<String, Object>> lRowData = (List<HashMap<String, Object>>) editDataObj;
                        if (lRowData != null && lRowData.size() > 0) {
                            GridControl gridControl = (GridControl) List_ControlClassObjects.get(controlName);
                            List<ControlObject> gridFormControlList = gridControl.controlObject.getSubFormControlList();
                            List<LinkedHashMap<String, Object>> l_gridformrows = gridControl.gridControl_List_ControlClassObjects;
                            for (int totRows = 0; totRows < l_gridformrows.size(); totRows++) {
                                HashMap<String, Object> gridFormRowEditData = lRowData.get(totRows);
                                LinkedHashMap<String, Object> gridFormRowViews = l_gridformrows.get(totRows);
                                for (int i = 0; i < gridFormControlList.size(); i++) {
                                    Object rowEditDataObj = gridFormRowEditData.get(gridFormControlList.get(i).getControlName());
                                    setDataToControl(context, gridFormControlList.get(i).getControlType(), gridFormControlList.get(i).getControlName(), gridFormRowViews, rowEditDataObj);
                                }
                            }
                        }
                    }
                    //key:subFormControlName=Value: List of RowData(HashMap<String, Object>) : RowData Key:controlNameinsubform Value:ControlData
                    break;
                default://MainForm Control
                    setDataToControl(context, controlType, controlName, List_ControlClassObjects, editDataObj);
                    //Key:mainformcontrolName=Value:ControlData
            }

        }
    }


    private static void setDataToControl(Context context, String strControlType, String strControlName, LinkedHashMap<String, Object> list_ControlClassObjects, Object editDataObj) {
        try {
            switch (strControlType) {

                case CONTROL_TYPE_TEXT_INPUT:
                    TextInput textInput = (TextInput) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().contentEquals("")) {
                            textInput.getCustomEditText().setText(cd.getControlValue());
                        }
                    }
                    break;
                case CONTROL_TYPE_NUMERIC_INPUT:
                    NumericInput numericInput = (NumericInput) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().contentEquals("")) {
                            numericInput.getNumericTextView().setText(cd.getControlValue());
                        }
                    }
                    break;
                case CONTROL_TYPE_PHONE:
                    Phone phone = (Phone) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().contentEquals("")) {
                            phone.getCustomEditText().setText(cd.getControlValue());
                        }
                    }
                    break;
                case CONTROL_TYPE_EMAIL:
                    Email email = (Email) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        email.getCustomEditText().setText(cd.getControlValue());
                    }
                    break;
                case CONTROL_TYPE_LARGE_INPUT:
                    LargeInput largeInput = (LargeInput) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {
                            String value = cd.getControlValue();
                            if (largeInput.isEditorModeEnabled()) {
                                largeInput.getTextEditor().setHtml(value);
                            } else if (largeInput.isHTMLViewerEnabled()) {
                                largeInput.getTextEditor().setHtml(value);
                            } else {
                                largeInput.getCustomEditText().setText(value);
                            }
                        }
                    }
                    break;
                case CONTROL_TYPE_CAMERA:
                    Camera camera = (Camera) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {
                            String[] strCamData = cd.getControlValue().split(",");
                            String strImageName = !strCamData[0].equalsIgnoreCase("") ? strCamData[0] : "";
                            String strImgBipMap = !strCamData[1].equalsIgnoreCase("") ? strCamData[1] : "";
                                if (ImproveHelper.validateUri(context, strImgBipMap)) {
                                    Uri uri = Uri.parse(strImgBipMap);
                                    camera.setUploadedImage(camera.getLLImageView(), null, strImgBipMap, uri, 1);
                                } else {
                                    if (strImageName != null && !strImageName.isEmpty()) {
                                        camera.getFileNameTextView().setText(strImageName);
                                        Bitmap imageBitmap = BitmapFactory.decodeFile(strImgBipMap);
                                        camera.setImage(imageBitmap);
                                        camera.setPath(strImgBipMap);
                                        camera.getFileNameTextView().setTag(strImgBipMap);
                                    }
                                }
                        }
                    }
                    break;
                case CONTROL_TYPE_FILE_BROWSING:
                    FileBrowsing fileBrowsing = (FileBrowsing) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {
                            fileBrowsing.setFileBrowsing(cd.getControlValue());
                        }
                    }
                    break;
                case CONTROL_TYPE_CALENDER:
                    Calendar calendar = (Calendar) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {
                            calendar.setSelectedDate(cd.getControlValue());
                        }
                    }
                    break;
                case CONTROL_TYPE_CHECKBOX:
                    Checkbox checkbox = (Checkbox) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        String value = cd.getControlValue();
                        if (value != null && !value.isEmpty()) {
                            checkbox.setCheckBoxValues(jsonStringConvertToHashMap(value));
                        }
                    }
                    break;
                case CONTROL_TYPE_RADIO_BUTTON:
                    RadioGroupView radioGroupView = (RadioGroupView) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {
                            radioGroupView.check(cd.getControlValue());
                        }
                    }
                    break;
                case CONTROL_TYPE_DROP_DOWN:
                    DropDown dropDown = (DropDown) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {
                            String[] strDropDownData = cd.getControlValue().split(",");
                            String strId = !strDropDownData[0].equalsIgnoreCase("") ? strDropDownData[0] : "";
                            String strSelectedItem = !strDropDownData[1].equalsIgnoreCase("") ? strDropDownData[1] : "";
                            dropDown.setItemId(strId, strSelectedItem);
                        }
                    }
                    break;
                case CONTROL_TYPE_CHECK_LIST:
                    CheckList checkList = (CheckList) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {
                            checkList.setCheckListSelectedItemIDs(cd.getControlValue());
                        }
                    }
                    break;
                case CONTROL_TYPE_RATING:
                    Rating rating = (Rating) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {
                            rating.setRating(cd.getControlValue());
                        }
                    }
                    break;
                case CONTROL_TYPE_VOICE_RECORDING:
                    VoiceRecording voiceRecording = (VoiceRecording) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty() && !cd.getControlValue().equalsIgnoreCase("Start Recording")) {
                            voiceRecording.setUploadedFile(cd.getControlValue());
                        }
                    }
                    break;
                case CONTROL_TYPE_VIDEO_RECORDING:
                    VideoRecording videoRecording = (VideoRecording) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {

                            String[] strSplitCap = cd.getControlValue().split("\\^");
                            Uri uri = Uri.parse(strSplitCap[0]);
                            if (strSplitCap[1] != null && !strSplitCap[1].isEmpty() && strSplitCap[1].equalsIgnoreCase("Camera")) {
                                if (ImproveHelper.validateUri(context, strSplitCap[0])) {
                                    videoRecording.setVideoPathFromOnActivityResult(uri, strSplitCap[1]);
                                }
                            } else if (strSplitCap[1] != null && !strSplitCap[1].isEmpty() && strSplitCap[1].equalsIgnoreCase("Gallery")) {
//
                                videoRecording.setVideoPathFromOnActivityResult(uri, strSplitCap[1]);
                            }
//                            videoRecording.setVideoPathFromOnActivityResult(uri, strSplitCap[1]);
                        }
                    }
                    break;
                case CONTROL_TYPE_AUDIO_PLAYER:
                    break;
                case CONTROL_TYPE_VIDEO_PLAYER:
                    break;
                case CONTROL_TYPE_PERCENTAGE:
                    Percentage percentage = (Percentage) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {
                            percentage.getCustomEditText().setText(cd.getControlValue());
                        }
                    }
                    break;
                case CONTROL_TYPE_SIGNATURE:
                    SignatureView signatureView = (SignatureView) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {
                            Uri uri = Uri.parse(cd.getControlValue());
                            signatureView.setSelectedSignature(uri);
                        }
                    }
                    break;
                case CONTROL_TYPE_URL_LINK:
                    UrlView urlView = (UrlView) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {
                            urlView.getCe_TextType().setText(cd.getControlValue());
                        }
                    }
                    break;
                case CONTROL_TYPE_DATA_CONTROL:
                    DataControl dataControl = (DataControl) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().contentEquals("")) {
                            AppConstants.DataControl_Edit_Flag = true;
                            dataControl.getControlObject().setDefaultValue(cd.getControlValue());
                            SearchableSpinner searchableSpinner = dataControl.getSpinner();
                            searchableSpinner.setItemID(cd.getControlValue());
                        }
                    }
                    break;
                case CONTROL_TYPE_DECIMAL:
                    DecimalView decimalView = (DecimalView) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {
                            decimalView.getCustomEditText().setText(cd.getControlValue());
                        }
                    }
                    break;
                case CONTROL_TYPE_PASSWORD:
                    Password password = (Password) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {
                            password.getCustomEditText().setText(cd.getControlValue());
                        }
                    }
                    break;
                case CONTROL_TYPE_CURRENCY:
                    Currency currency = (Currency) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {
                            currency.getCustomEditText().setText(cd.getControlValue());
                        }
                    }
                    break;
                case CONTROL_TYPE_DYNAMIC_LABEL:
                    break;
                case CONTROL_TYPE_IMAGE:
                    break;
                case CONTROL_TYPE_TIME:
                    Time timeControl = (Time) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {
                            timeControl.setTimeData(cd.getControlValue());
                        }
                    }
                    break;
                case CONTROL_TYPE_COUNT_DOWN_TIMER:
                    break;
                case CONTROL_TYPE_COUNT_UP_TIMER:
                    break;
                case CONTROL_TYPE_VIEWFILE:
                    break;
                case CONTROL_TYPE_POST:
                    PostControl postControl = (PostControl) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {
                            String[] splitCaps = cd.getControlValue().split("\\^");
                            postControl.setEditValues(splitCaps[0], splitCaps[1]);
                        }
                    }
                    break;
                case CONTROL_TYPE_USER:
                    UserControl userControl = (UserControl) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getControlValue() != null && !cd.getControlValue().isEmpty()) {
                            String[] splitCaps = cd.getControlValue().split("\\^");
                            userControl.setEditValues(splitCaps[0], splitCaps[1]);
                        }
                    }
                    break;
                case CONTROL_TYPE_AUTO_COMPLETION:
                    break;
                case CONTROL_TYPE_MAP:
                    break;
                case CONTROL_TYPE_GPS:
                    Gps_Control gps_control = (Gps_Control) list_ControlClassObjects.get(strControlName);
                    if (editDataObj != null) {
                        ControlData cd = (ControlData) editDataObj;
                        if (cd.getGpsCoordinates() != null && !cd.getGpsCoordinates().contentEquals("")) {
                            gps_control.getControlObject().setControlValue(cd.getGpsCoordinates());
                            String coordinates = cd.getGpsCoordinates();
                            String type = cd.getGpsType();
                            List<String> Points = ImproveHelper.getGpsCoordinates(coordinates);
                            gps_control.setMapPointsDynamically(type, Points, null);
                        }
                    }


            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }

    public static void setValueToGridControlForEditAfterEvents(Context context, List<ControlObject> list_Controls, LinkedHashMap<String, Object> ViewObj, JSONObject jsonObject, int subFormRow, ImproveHelper improveHelper) {
        try {
            for (int sc = 0; sc < list_Controls.size(); sc++) {
                ControlObject temp_controlObj = list_Controls.get(sc);
                String controlName = temp_controlObj.getControlID();
                String controlType = temp_controlObj.getControlType();

                HashMap<String, String> controlValue = improveHelper.getEditValuesFromJSONObject(jsonObject, controlType, controlName);

                ControlObject subObj = ImproveHelper.getControlObject(list_Controls, controlName);
                if (subObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_DATA_CONTROL)) {
                    subObj.setDefaultValue(controlValue.get("Value_id"));
                } else if (subObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_DROP_DOWN)) {
                    subObj.setDefaultValue(controlValue.get("Value_id"));
                } else if (subObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_CHECK_LIST)) {
                    subObj.setDefaultValue(controlValue.get("Value_id"));
                } else if (subObj.getControlType().equalsIgnoreCase(CONTROL_TYPE_GPS)) {
                    subObj.setControlValue(controlValue.get("Coordinates"));
                }
                Log.d(TAG, "eventexecutescontrolName: " + controlName);
                Log.d(TAG, "eventexecutescontrolName_: " + controlValue.get("Value"));
                // linearLayout.setTag(jsonObject.getString("Trans_ID"));
                if (controlValue.size() > 0) {
                    switch (controlType) {
                        case CONTROL_TYPE_CHECKBOX:
                            Checkbox CheckBoxView = (Checkbox) ViewObj.get(controlName);
                            CheckBoxView.setCheckBoxValues(controlValue);
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(CheckBoxView.getCheckbox(), false);
//                            }
                            break;
                        case CONTROL_TYPE_TEXT_INPUT:
                            TextInput clearTextView = (TextInput) ViewObj.get(controlName);
                            clearTextView.getCustomEditText().setText(controlValue.get("Value"));
                            Log.d(TAG, "setSubForm_v: " + controlValue.get("Value"));
                            CustomTextView tv_tapTextType = clearTextView.gettap_text();
                            tv_tapTextType.setVisibility(View.GONE);
                            clearTextView.getCustomEditText().setVisibility(View.VISIBLE);
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(clearTextView.getTextInputView(), false);
//                            }
                            break;
                        case CONTROL_TYPE_NUMERIC_INPUT:
                            NumericInput numverTextView = (NumericInput) ViewObj.get(controlName);
                            numverTextView.getNumericTextView().setText(controlValue.get("Value"));
                            CustomTextView tv_numtapTextType = numverTextView.gettap_text();
                            tv_numtapTextType.setVisibility(View.GONE);
                            numverTextView.getNumericTextView().setVisibility(View.VISIBLE);
                            numverTextView.gettap_text().setVisibility(View.GONE);
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(numverTextView.getNumericInputView(), false);
//                            }
                            break;
                        case CONTROL_TYPE_PHONE:
                            Phone PhoneView = (Phone) ViewObj.get(controlName);
                            PhoneView.getCustomEditText().setText(controlValue.get("Value"));
                            CustomTextView tv_phonetapTextType = PhoneView.gettap_text();
                            tv_phonetapTextType.setVisibility(View.GONE);
                            PhoneView.getCustomEditText().setVisibility(View.VISIBLE);
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(PhoneView.getPhoneView(), false);
//                            }
                            break;
                        case CONTROL_TYPE_EMAIL:
                            Email EmailView = (Email) ViewObj.get(controlName);
                            EmailView.getCustomEditText().setText(controlValue.get("Value"));
                            CustomTextView tv_emailtapTextType = EmailView.gettap_text();
                            tv_emailtapTextType.setVisibility(View.GONE);
                            EmailView.getCustomEditText().setVisibility(View.VISIBLE);
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(EmailView.getEmailView(), false);
//                            }
                            break;
                        case CONTROL_TYPE_CAMERA:
                            Camera camera = (Camera) ViewObj.get(controlName);
//                            if (controlValue.get("Value").startsWith("http")) {
                            camera.setImageForEdit(controlValue.get("Value"));
//                            }
                            // Sanjay
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(camera.getCameraView(), false);
//                            }
                            break;
                        case CONTROL_TYPE_IMAGE:
                            Image ImageView = (Image) ViewObj.get(controlName);
                            if (controlValue != null && controlValue.get("Value").startsWith("http")) {
                                String Value = controlValue.get("Value").split("\\,")[0];
                                ImageView.getControlObject().setImageData(Value);
                                ImageView.setPath(Value);
                                if (isNetworkStatusAvialable(context)) {
                                    Glide.with(context).load(Value).into(ImageView.mainImageView);
                                }
                            }
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(ImageView.getImageView(), false);
//                            }
                            break;
                        case CONTROL_TYPE_LARGE_INPUT:
                            LargeInput LargeInputView = (LargeInput) ViewObj.get(controlName);
                            CustomTextView tv_largetapTextType = LargeInputView.gettap_text();
                            tv_largetapTextType.setVisibility(View.GONE);
                            if (LargeInputView.isEditorModeEnabled()) {
                                LargeInputView.getCustomEditText().setVisibility(View.GONE);
                                LargeInputView.getEditorLayout().setVisibility(View.VISIBLE);
                                LargeInputView.getEditorToolBar().setVisibility(View.VISIBLE);
                                LargeInputView.getTextEditor().setHtml(controlValue.get("Value"));
                            } else if (LargeInputView.isHTMLViewerEnabled()) {
                                LargeInputView.getCustomEditText().setVisibility(View.GONE);
                                LargeInputView.getEditorLayout().setVisibility(View.VISIBLE);
                                LargeInputView.getEditorToolBar().setVisibility(View.GONE);
                                LargeInputView.getTextEditor().setHtml(controlValue.get("Value"));
                            } else {
                                LargeInputView.getCustomEditText().setVisibility(View.VISIBLE);
                                LargeInputView.getCustomEditText().setText(controlValue.get("Value"));
                            }
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(LargeInputView.getLargeInputView(), false);
//                            }
                            break;

                        case CONTROL_TYPE_FILE_BROWSING:

                            FileBrowsing fileBrowsing = (FileBrowsing) ViewObj.get(controlName);
                            if (controlValue.get("Value") != null && !controlValue.get("Value").trim().isEmpty()) {
                                fileBrowsing.setFileBrowsing(controlValue.get("Value"));
                                fileBrowsing.setPath(controlValue.get("Value"));
                            }
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(fileBrowsing.getFileBrowsingView(), false);
//                            }
                            break;
                        case CONTROL_TYPE_CALENDER:
                            Calendar calendar = (Calendar) ViewObj.get(controlName);
                            String strCalendarValue = controlValue.get("Value");
                            if (strCalendarValue != null && !strCalendarValue.isEmpty()) {
//                            if(strCalendarValue.length()>9 &&strCalendarValue.charAt(10) == 'T'){
//                                String[] defaultValue = strCalendarValue.split("T");
//                                calendar.setCalendarDate(defaultValue[0]);
//                            }else{
                                calendar.setSelectedDate(strCalendarValue);
//                            }
                            }

//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(calendar.getCalnderView(), false);
//                            }
                            break;
                        case CONTROL_TYPE_AUDIO_PLAYER:

                            break;
                        case CONTROL_TYPE_VIDEO_PLAYER:

                            break;
                        case CONTROL_TYPE_PERCENTAGE:
                            Percentage PercentageView = (Percentage) ViewObj.get(controlName);
                            PercentageView.getCustomEditText().setText(controlValue.get("Value"));
                            CustomTextView tv_pertapTextType = PercentageView.gettap_text();
                            tv_pertapTextType.setVisibility(View.GONE);
                            PercentageView.getCustomEditText().setVisibility(View.VISIBLE);
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(PercentageView.getPercentageView(), false);
//                            }
                            break;
                        case CONTROL_TYPE_RADIO_BUTTON:
                            RadioGroupView radioGroupView = (RadioGroupView) ViewObj.get(controlName);
                            if (radioGroupView != null) {
                                radioGroupView.setEditValue(controlValue);
                            }
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(radioGroupView.getRadioGroupView(), false);
//                            }
                            break;
                        case CONTROL_TYPE_DROP_DOWN:
                            Log.d(TAG, "setValueToControlForEditAfterEvents: " + controlName);
                            DropDown dropDown = (DropDown) ViewObj.get(controlName);
//                            dropDown.getControlObject().setDefaultValue(controlValue.get("Value_id"));
                            if (dropDown != null && controlValue.get("Value_id") != null) {
                                dropDown.setItemId(controlValue.get("Value_id"), controlValue.get("Value"));
                            }
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(dropDown.getDropdown(), false);
//                            }
                            break;
                        case CONTROL_TYPE_CHECK_LIST:
                            CheckList checklist = (CheckList) ViewObj.get(controlName);
//                            checklist.getControlObject().setDefaultValue(controlValue.get("Value_id"));
                            boolean CheckListflag = true;
//                            List<Item> itemListC = new ArrayList<>();
                           /* if (!controlValue.get("Value").contentEquals("")) {
                                String[] valuesC = controlValue.get("Value").split("\\^");
                                if (!controlValue.get("Value_id").contentEquals("")) {
                                    String[] ids = controlValue.get("Value_id").split("\\^");
                                    for (int j = 0; j < valuesC.length; j++) {
                                        Item item = new Item();
                                        item.setId(ids[j]);
                                        item.setValue(valuesC[j]);
                                        itemListC.add(item);
                                    }
                                } else {
                                    for (int j = 0; j < valuesC.length; j++) {
                                        Item item = new Item();
                                        item.setId(valuesC[j]);
                                        item.setValue(valuesC[j]);
                                        itemListC.add(item);
                                    }
                                }
                            }*/
                            String[] ids = controlValue.get("Value_id").split("\\^");
                            checklist.addNewItemsListDynamically(ids);

//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(checklist.getCheckList(), false);
//                            }
                            break;
                        case CONTROL_TYPE_SIGNATURE:
                            SignatureView signatureView = (SignatureView) ViewObj.get(controlName);
                            signatureView.setSignatureForEdit(controlValue.get("Value"));
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(signatureView.getSignature(), false);
//                            }
                            break;
                        case CONTROL_TYPE_URL_LINK:
                            break;
                        case CONTROL_TYPE_DECIMAL:
                            DecimalView Decimalview = (DecimalView) ViewObj.get(controlName);
                            Decimalview.getCustomEditText().setText(controlValue.get("Value"));
                            CustomTextView tv_DectapTextType = Decimalview.gettap_text();
                            tv_DectapTextType.setVisibility(View.GONE);
                            Decimalview.getCustomEditText().setVisibility(View.VISIBLE);
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(Decimalview.getDecimal(), false);
//                            }
                            break;
                        case CONTROL_TYPE_PASSWORD:
                            Password Passwordview = (Password) ViewObj.get(controlName);
                            Passwordview.getCustomEditText().setText(controlValue.get("Value"));
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(Passwordview.getPassword(), false);
//                            }
                            break;
                        case CONTROL_TYPE_CURRENCY:
                            Currency Currencyview = (Currency) ViewObj.get(controlName);
                            Currencyview.getCustomEditText().setText(controlValue.get("Value"));
                            CustomTextView tv_CurtapTextType = Currencyview.gettap_text();
                            tv_CurtapTextType.setVisibility(View.GONE);
                            Currencyview.getCustomEditText().setVisibility(View.VISIBLE);
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(Currencyview.getCurrency(), false);
//                            }
                            break;
                        case CONTROL_TYPE_RATING:
                            Rating rating = (Rating) ViewObj.get(controlName);
                            rating.setRating(controlValue.get("Value"));
                           /* if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
                                setViewDisable(rating.getRatingView(), false);
                            }*/
                            break;
                        case CONTROL_TYPE_DYNAMIC_LABEL:
                            DynamicLabel DynamicLabeliew = (DynamicLabel) ViewObj.get(controlName);
                            CustomTextView tv_dynamicLabel = DynamicLabeliew.getValueView();
//                            tv_dynamicLabel.setText(Value);
                            DynamicLabeliew.setValue(controlValue.get("Value"));

                            break;
                        case CONTROL_TYPE_QR_CODE:
                            QRCode qrCode = (QRCode) ViewObj.get(controlName);
                            if (!controlValue.get("Value").contentEquals("")) {
                                qrCode.createQrCodeDynamically(controlValue.get("Value"));
                            }

                            break;
                        case CONTROL_TYPE_BAR_CODE:
                            BarCode barCode = (BarCode) ViewObj.get(controlName);
                            if (!controlValue.get("Value").contentEquals("")) {
                                barCode.createBarCodeDynamically(controlValue.get("Value"));
                            }
                            break;
                        case CONTROL_TYPE_DATA_CONTROL:
                            DataControl dataControl = (DataControl) ViewObj.get(controlName);
                            dataControl.getControlObject().setDefaultValue(controlValue.get("Value_id"));
                            dataControl.setEditValue(controlValue, subFormRow);
                           /* if (!controlValue.get("Value_id").contentEquals("")) {
                                SearchableSpinner searchableSpinner = dataControl.getSpinner();
                                searchableSpinner.setItemID(controlValue.get("Value_id"));
                            }*/
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(dataControl.getDataControl(), false);
//                            }
                            break;
                        case CONTROL_TYPE_GPS:

                            Gps_Control gps_control = (Gps_Control) ViewObj.get(controlName);
                            gps_control.getControlObject().setControlValue(controlValue.get("Coordinates"));
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void run() {
                                    //Do something after delay
                                    if (!controlValue.get("Coordinates").trim().contentEquals("")) {
                                        String coordinates = controlValue.get("Coordinates");
                                        String type = controlValue.get("Type");
                                        List<String> Points = ImproveHelper.getGpsCoordinates(coordinates);
                                        gps_control.setMapPointsDynamically(type, Points, null);
                                    }
//                                    if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlId)) {
//                                        setViewDisable(gps_control.getControlGPSView(), false);
//                                    }
                                }
                            }, 1000);

                         /*   if (!controlValue.get("Coordinates").trim().contentEquals("")) {
                                String coordinates = controlValue.get("Coordinates");
                                String type = controlValue.get("Type");
                                List<String> Points = getGpsCoordinates(coordinates);
                                gps_control.setMapPointsDynamically(type, Points, null);
                            }
                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlId)) {
                                setViewDisable(gps_control.getControlGPSView(), false);
                            }*/

                            break;
                        case CONTROL_TYPE_TIME:

                            Time time_control = (Time) ViewObj.get(controlName);
                            if (!controlValue.get("Value").contentEquals("")) {
                                time_control.setTimeData(controlValue.get("Value"));
                            }
//                            if (AppConstants.EDIT_COLUMNS != null && AppConstants.EDIT_COLUMNS.size() > 0 && !AppConstants.EDIT_COLUMNS.contains(controlName)) {
//                                setViewDisable(time_control.getTimeControlLayout(), false);
//                            }
                            break;
                        case CONTROL_TYPE_VOICE_RECORDING:
                            if (!controlValue.get("Value").contentEquals("")) {
                                String Value = controlValue.get("Value");
                                VoiceRecording voiceRecording = (VoiceRecording) ViewObj.get(controlName);
                                if (!Value.contains("File not found") && !Value.trim().isEmpty()) {
//                                    voiceRecording.setVoiceRecordingPath(Value);
                                    voiceRecording.setUploadedFile(Value);
                                }
                            }
                            break;
                        case CONTROL_TYPE_VIDEO_RECORDING:
                            if (!controlValue.get("Value").contentEquals("")) {
                                String Value = controlValue.get("Value");
                                VideoRecording videoRecording = (VideoRecording) ViewObj.get(controlName);
                                if (!Value.contains("File not found") && !Value.trim().isEmpty()) {
                                    videoRecording.setVideoRecordingPath(Value);
                                }
                            }
                            break;
                        case CONTROL_TYPE_USER:
                            if (!controlValue.get("Value_id").contentEquals("")) {
                                String Value_id = controlValue.get("Value_id");
                                String Value = controlValue.get("Value");
                                UserControl userControl = (UserControl) ViewObj.get(controlName);
                                userControl.setEditValues(Value, Value_id);
                            }
                            break;
                        case CONTROL_TYPE_POST:
                            if (!controlValue.get("Value_id").contentEquals("")) {
                                String Value_id = controlValue.get("Value_id");
                                String Value = controlValue.get("Value");
                                PostControl postControl = (PostControl) ViewObj.get(controlName);
                                postControl.setEditValues(Value, Value_id);
                            }
                            break;
                    }
                }

            }
        } catch (Exception e) {
            Log.d(TAG, "setValueToControl_BP: " + "test");
            ImproveHelper.improveException(context, TAG, "setValueToControlForEditAfterEvents", e);
        }
    }


    public static boolean getGridControlRowValuesToCheck(Context context, List<ControlObject> list_Controls, LinkedHashMap<String, Object> ViewObj, JSONObject jsonObject) {

        boolean rowValuesMatch = false;
        boolean allControlsEmpty = true;
        try {
            List<Boolean> rowValuesMatchBooleans = new ArrayList<>();
            for (int j = 0; j < list_Controls.size(); j++) {
                try {
                    rowValuesMatch = false;
                    ControlObject temp_controlObj = list_Controls.get(j);
                    String controlName = temp_controlObj.getControlID();
                    String controlType = temp_controlObj.getControlType();
                    switch (controlType) {
                        case CONTROL_TYPE_CHECKBOX:
                            Checkbox CheckBoxView = (Checkbox) ViewObj.get(controlName);
                            String text = CheckBoxView.getSelectedId().trim();
                            if (!text.isEmpty()) {
                                allControlsEmpty = false;
                            }
                            rowValuesMatch = jsonObject.getString(controlName).trim().equalsIgnoreCase(text);
                            rowValuesMatchBooleans.add(rowValuesMatch);
                            break;
                        case CONTROL_TYPE_TEXT_INPUT:
                            TextInput clearTextView = (TextInput) ViewObj.get(controlName);
                            String strTextInputValue = clearTextView.getTextInputValue().trim();
                            if (strTextInputValue != null && !strTextInputValue.isEmpty()) {
                                allControlsEmpty = false;
                                Log.d(TAG, "setSubForm_vd: " + jsonObject.getString(controlName));
                                Log.d(TAG, "setSubForm_vs: " + strTextInputValue);
                                if (jsonObject.getString(controlName).equalsIgnoreCase(strTextInputValue)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                        case CONTROL_TYPE_NUMERIC_INPUT:
                            NumericInput numverTextView = (NumericInput) ViewObj.get(controlName);
                            String strNumericValue = numverTextView.getNumericValue();
                            if (strNumericValue != null && !strNumericValue.isEmpty()) {
                                allControlsEmpty = false;
                                if (jsonObject.getString(controlName).equalsIgnoreCase(strNumericValue)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                        case CONTROL_TYPE_PHONE:
                            Phone PhoneView = (Phone) ViewObj.get(controlName);
                            String strPhoneValue = PhoneView.getPhoneValue();
                            if (strPhoneValue != null && !strPhoneValue.isEmpty()) {
                                allControlsEmpty = false;
                                Log.d(TAG, "setSubForm_vd: " + jsonObject.getString(controlName));
                                Log.d(TAG, "setSubForm_vs: " + strPhoneValue);
                                if (jsonObject.getString(controlName).equalsIgnoreCase(strPhoneValue)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                        case CONTROL_TYPE_EMAIL:
                            Email EmailView = (Email) ViewObj.get(controlName);
                            String strEmailValue = EmailView.getEmailValue();
                            if (strEmailValue != null && !strEmailValue.isEmpty()) {
                                allControlsEmpty = false;
                                if (jsonObject.getString(controlName).equalsIgnoreCase(strEmailValue)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                        case CONTROL_TYPE_IMAGE:
//                    Image ImageView = (Image) ViewObj.get(controlName);
                            break;
                        case CONTROL_TYPE_LARGE_INPUT:
                            LargeInput LargeInputView = (LargeInput) ViewObj.get(controlName);
                            String strLargeInput = LargeInputView.getCustomEditText().getText().toString();
                            if (strLargeInput != null && !strLargeInput.isEmpty()) {
                                allControlsEmpty = false;
                                Log.d(TAG, "setSubForm_vd: " + jsonObject.getString(controlName));
                                Log.d(TAG, "setSubForm_vs: " + strLargeInput);
                                if (jsonObject.getString(controlName).equalsIgnoreCase(strLargeInput)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                        case CONTROL_TYPE_CAMERA:
                            Camera camera = (Camera) ViewObj.get(controlName);
                            String strCameraValue = camera.getControlRealPath().getTag().toString();
                            if (strCameraValue != null && !strCameraValue.isEmpty()) {
                                allControlsEmpty = false;
                                if (jsonObject.getString(controlName).equalsIgnoreCase(strCameraValue)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                        case CONTROL_TYPE_FILE_BROWSING:
                            FileBrowsing fileBrowsing = (FileBrowsing) ViewObj.get(controlName);
                            String strfileBrowsingValue = fileBrowsing.getControlRealPath().getTag().toString();
                            if (strfileBrowsingValue != null && !strfileBrowsingValue.isEmpty()) {
                                allControlsEmpty = false;
                                if (jsonObject.getString(controlName).equalsIgnoreCase(strfileBrowsingValue)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                        case CONTROL_TYPE_CALENDER:
                            Calendar calendar = (Calendar) ViewObj.get(controlName);
                            String strCalendarValue = calendar.getCalendarValue();
                            if (!strCalendarValue.isEmpty()) {
                                allControlsEmpty = false;
                            }
                            /*if (strCalendarValue != null && !strCalendarValue.isEmpty()) {
                                String[] enteredValueTemp = jsonObject.getString(controlName).split("T");
                                String enteredValue = parseDateToddMMyyyy(enteredValueTemp[0]);
                                if (enteredValue.equalsIgnoreCase(strCalendarValue)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }*/
                            String enteredValue = jsonObject.getString(controlName);
//                                if(enteredValue.length()>9 && enteredValue.charAt(10) == 'T'){
//                                    String[] defaultValue = enteredValue.split("T");
//                                    enteredValue=defaultValue[0];
//                                }
                            if (enteredValue.equalsIgnoreCase(strCalendarValue)) {
                                rowValuesMatch = true;
                                rowValuesMatchBooleans.add(rowValuesMatch);
                            } else {
                                rowValuesMatch = false;
                                rowValuesMatchBooleans.add(rowValuesMatch);
                            }
                            break;
                        case CONTROL_TYPE_AUDIO_PLAYER:

                            break;
                        case CONTROL_TYPE_VIDEO_PLAYER:

                            break;
                        case CONTROL_TYPE_PERCENTAGE:
                            Percentage PercentageView = (Percentage) ViewObj.get(controlName);
                            String strPercentage = PercentageView.getCustomEditText().getText().toString();
                            if (strPercentage != null && !strPercentage.isEmpty()) {
                                allControlsEmpty = false;
                                if (jsonObject.getString(controlName).equalsIgnoreCase(strPercentage)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                        case CONTROL_TYPE_RADIO_BUTTON:
                            RadioGroupView radioGroupView = (RadioGroupView) ViewObj.get(controlName);
                            String strRadioButtonText = radioGroupView.getSelectedRadioButtonText();
                            String strRadioButtonId = radioGroupView.getSelectedRadioButtonID();
                            if (strRadioButtonId != null && !strRadioButtonId.isEmpty()) {
                                allControlsEmpty = false;
                                if (jsonObject.getString(controlName + "id").equalsIgnoreCase(strRadioButtonId)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                        case CONTROL_TYPE_DROP_DOWN:
                            DropDown dropDown = (DropDown) ViewObj.get(controlName);
                            String strDropdownValue = dropDown.getSelectedDropDownItem();
                            String strDropdownId = dropDown.getSelectedDropDownItemID();
                            if (strDropdownId != null && !strDropdownId.isEmpty()) {
                                allControlsEmpty = false;
                                if (jsonObject.getString(controlName + "id").equalsIgnoreCase(strDropdownId)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            } else if (dropDown.getnewItemsListDynamically() != null && dropDown.getnewItemsListDynamically().size() > 0) {
                                rowValuesMatch = true;
                                rowValuesMatchBooleans.add(rowValuesMatch);
                            }
                            break;
                        case CONTROL_TYPE_CHECK_LIST:
                            CheckList checklist = (CheckList) ViewObj.get(controlName);
                            String strCheckListValue = checklist.getSelectedNameCheckListString();
                            String strCheckListId = checklist.getSelectedIDCheckListString();
                            if (strCheckListId != null && !strCheckListId.isEmpty()) {
                                allControlsEmpty = false;
                                if (jsonObject.getString(controlName + "id").equalsIgnoreCase(strCheckListId)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                        case CONTROL_TYPE_SIGNATURE:
                            SignatureView signatureView = (SignatureView) ViewObj.get(controlName);
                            String strSignatureValue = signatureView.getControlRealPath().getTag().toString();
                            if (strSignatureValue != null && !strSignatureValue.isEmpty()) {
                                allControlsEmpty = false;
                                if (jsonObject.getString(controlName).equalsIgnoreCase(strSignatureValue)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                        case CONTROL_TYPE_URL_LINK:
                            break;
                        case CONTROL_TYPE_DECIMAL:
                            DecimalView Decimalview = (DecimalView) ViewObj.get(controlName);
                            String strDecimalValue = Decimalview.getCustomEditText().getText().toString();
                            if (strDecimalValue != null && !strDecimalValue.isEmpty()) {
                                allControlsEmpty = false;
                                if (jsonObject.getString(controlName).equalsIgnoreCase(strDecimalValue)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                        case CONTROL_TYPE_PASSWORD:
                            Password Passwordview = (Password) ViewObj.get(controlName);
                            String strPasswordValue = Passwordview.getCustomEditText().getText().toString();
                            if (strPasswordValue != null && !strPasswordValue.isEmpty()) {
                                allControlsEmpty = false;
                                if (jsonObject.getString(controlName).equalsIgnoreCase(strPasswordValue)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                        case CONTROL_TYPE_CURRENCY:
                            Currency Currencyview = (Currency) ViewObj.get(controlName);
                            String strCurrencyValue = Currencyview.getCustomEditText().getText().toString();
                            if (strCurrencyValue != null && !strCurrencyValue.isEmpty()) {
                                allControlsEmpty = false;
                                if (jsonObject.getString(controlName).equalsIgnoreCase(strCurrencyValue)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                        case CONTROL_TYPE_RATING:
                            Rating rating = (Rating) ViewObj.get(controlName);
                            String strRatingValue = rating.getRatingValue();
                            if (strRatingValue != null && !strRatingValue.isEmpty()) {
                                allControlsEmpty = false;
                                if (jsonObject.getString(controlName).equalsIgnoreCase(strRatingValue)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                        case CONTROL_TYPE_USER:
                            UserControl userControl = (UserControl) ViewObj.get(controlName);
                            String strUserId = userControl.getSelectedUserId();
                            String strUserName = userControl.getSelectedUserName();
                            if (strUserId != null && !strUserId.isEmpty()) {
                                if (jsonObject.getString(controlName + "_id").equalsIgnoreCase(strUserId)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                        case CONTROL_TYPE_POST:
                            PostControl postControl = (PostControl) ViewObj.get(controlName);
                            String strPostId = postControl.getSelectedPostId();
                            String strPostName = postControl.getSelectedPostName();
                            if (strPostId != null && !strPostId.isEmpty()) {
                                if (jsonObject.getString(controlName + "_id").equalsIgnoreCase(strPostId)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                        case CONTROL_TYPE_DYNAMIC_LABEL:
                            DynamicLabel DynamicLabeliew = (DynamicLabel) ViewObj.get(controlName);
                            break;
                        case CONTROL_TYPE_QR_CODE:
                            QRCode qrCode = (QRCode) ViewObj.get(controlName);
                            break;
                        case CONTROL_TYPE_BAR_CODE:
                            BarCode barCode = (BarCode) ViewObj.get(controlName);
                            break;
                        case CONTROL_TYPE_DATA_CONTROL:
                            DataControl dataControl = (DataControl) ViewObj.get(controlName);
                            String strDataControlId = dataControl.getSelectedDropDownItemId();
                            String strDataControlName = dataControl.getSelectedDropDownItemName();
                            if (strDataControlId != null && !strDataControlId.isEmpty()) {
                                allControlsEmpty = false;
                                if (jsonObject.getString(controlName + "_id").equalsIgnoreCase(strDataControlId)) {
                                    rowValuesMatch = true;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                } else {
                                    rowValuesMatch = false;
                                    rowValuesMatchBooleans.add(rowValuesMatch);
                                }
                            }
                            break;
                    }
                } catch (Exception e) {
                    Log.d(TAG, "setSubForm_e_get: " + rowValuesMatch);
                }
            }

            if (allControlsEmpty) {
                rowValuesMatch = true;
            } else {
                rowValuesMatch = !rowValuesMatchBooleans.contains(false);
            }

            Log.d(TAG, "setSubForm_get: " + rowValuesMatch);
        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "getSubformRowValuesToCheck", e);
        }
        return rowValuesMatch;
    }

    public static void setSingleValuetoControlFromCallAPIORGetData(Context context, LinkedHashMap<String, List<String>> OutputData,
                                                                   API_OutputParam_Bean API_OutputParam_Bean,
                                                                   DataCollectionObject dataCollectionObject,
                                                                   LinkedHashMap<String, Object> List_ControlClassObjects) {
        try {

            String Value = "", ControlID = "";
            String MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_ID().trim();
            ControlID = API_OutputParam_Bean.getOutParam_Name();

            if (API_OutputParam_Bean.getOutParam_Mapped_Expression() == null || API_OutputParam_Bean.getOutParam_Mapped_Expression().length() == 0) {
                if (MappedControlID.trim().length() > 0) {
                    if(OutputData.containsKey(MappedControlID.toLowerCase())){
                        List<String> MappedValues = OutputData.get(MappedControlID.toLowerCase());
                        Value = MappedValues.get(0);
                    }
                } else if (API_OutputParam_Bean.getList_OutParam_Languages() != null && API_OutputParam_Bean.getList_OutParam_Languages().size() > 0) {
                    String appLanguage = ImproveHelper.getLocale(context);
                    List<LanguageMapping> languageMappings = API_OutputParam_Bean.getList_OutParam_Languages();
                    for (int i = 0; i < languageMappings.size(); i++) {
                        LanguageMapping languageMapping = languageMappings.get(i);
                        if (languageMapping.getOutParam_Lang_Name().equalsIgnoreCase(appLanguage)) {
                            List<String> MappedValues = OutputData.get(languageMapping.getOutParam_Lang_Mapped().toLowerCase());
                            Value = MappedValues.get(0);
                            break;
                        }


                    }
                }
            } else {
                MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_Expression().trim();
                ExpressionMainHelper ehelper = new ExpressionMainHelper();
                Value = ehelper.ExpressionHelper(context, API_OutputParam_Bean.getOutParam_Mapped_Expression().trim());
            }
            setSingleOrMultiValueToControlWithOutCtrl(context, List_ControlClassObjects, Value, OutputData,
                    ControlID, MappedControlID, API_OutputParam_Bean);



        } catch (Exception e) {
            ImproveHelper.improveException(context, TAG, "SetSingleValuetoControlForCallAPIorForm", e);
        }
    }

    public static void setSingleOrMultiValueToControlWithOutCtrl(Context context,
                                                        LinkedHashMap<String, Object> List_ControlClassObjects,
                                                        String Value,
                                                        LinkedHashMap<String, List<String>> OutputData,
                                                        String ControlID, String MappedControlID,
                                                        API_OutputParam_Bean API_OutputParam_Bean) {
        String updatedControlID=ControlID;
        if(ControlID.endsWith("_id")){
            updatedControlID=ControlID.substring(0,ControlID.lastIndexOf("_id"));
        }

        if (List_ControlClassObjects.get(ControlID) instanceof TextInput) {
            TextInput textInput = (TextInput) List_ControlClassObjects.get(ControlID);
            Value = Value.split("\\,")[0];
            textInput.setEditValue(Value);
            CustomTextView tv_tapTextType = textInput.gettap_text();
            tv_tapTextType.setVisibility(View.GONE);
            textInput.getCustomEditText().setVisibility(View.VISIBLE);
        } else if (List_ControlClassObjects.get(ControlID) instanceof NumericInput) {
            NumericInput numverTextView = (NumericInput) List_ControlClassObjects.get(ControlID);
            Value = Value.split("\\,")[0];
            numverTextView.getNumericTextView().setText(Value);
            CustomTextView tv_numtapTextType = numverTextView.gettap_text();
            tv_numtapTextType.setVisibility(View.GONE);
            numverTextView.getNumericTextView().setVisibility(View.VISIBLE);
            numverTextView.gettap_text().setVisibility(View.GONE);
        } else if (List_ControlClassObjects.get(ControlID) instanceof Phone) {
            Phone PhoneView = (Phone) List_ControlClassObjects.get(ControlID);
            Value = Value.split("\\,")[0];
            PhoneView.getCustomEditText().setText(Value);
            CustomTextView tv_phonetapTextType = PhoneView.gettap_text();
            tv_phonetapTextType.setVisibility(View.GONE);
            PhoneView.getCustomEditText().setVisibility(View.VISIBLE);
        } else if (List_ControlClassObjects.get(ControlID) instanceof Email) {
            Email EmailView = (Email) List_ControlClassObjects.get(ControlID);
            Value = Value.split("\\,")[0];
            EmailView.getCustomEditText().setText(Value);
            CustomTextView tv_emailtapTextType = EmailView.gettap_text();
            tv_emailtapTextType.setVisibility(View.GONE);
            EmailView.getCustomEditText().setVisibility(View.VISIBLE);
        } else if (List_ControlClassObjects.get(ControlID) instanceof Camera) {
            Camera camera = (Camera) List_ControlClassObjects.get(ControlID);
            Value = Value.split("\\,")[0];
            camera.setImageForEdit(Value);
        } else if (List_ControlClassObjects.get(ControlID) instanceof Image) {
            Image ImageView = (Image) List_ControlClassObjects.get(ControlID);
            String Advance_ImageSource = "", Advance_ConditionColumn = "";
            if (API_OutputParam_Bean.getOutParam_Mapped_ID() != null && API_OutputParam_Bean.getOutParam_Mapped_ID().length() > 0) {
                Value =OutputData.size()>0? OutputData.get(API_OutputParam_Bean.getOutParam_Mapped_ID().toLowerCase()).get(0):"";
                if (ImageView.controlObject.getImageDataType().equalsIgnoreCase("Base64")) {
                    ImageView.mainImageView.setImageBitmap(ImproveHelper.Base64StringToBitmap(Value));
                } else {
                    ImproveHelper.loadImage_new(context, Value, ImageView.mainImageView, false, ImageView.getControlObject().getImageData());
                }
            } else if (API_OutputParam_Bean.getList_OutParam_ImageAdvanced_Items() != null && API_OutputParam_Bean.getList_OutParam_ImageAdvanced_Items().size() > 0) {
                if (API_OutputParam_Bean.getOutParam_ImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                    Advance_ImageSource = OutputData.size()>0?OutputData.get(API_OutputParam_Bean.getOutParam_ImageAdvanced_ImageSource().toLowerCase()).get(0):"";
                }
                Advance_ConditionColumn = OutputData.size()>0?OutputData.get(API_OutputParam_Bean.getOutParam_ImageAdvanced_ConditionColumn().toLowerCase()).get(0):"";

                if (Advance_ImageSource != null && Advance_ImageSource.length() != 0) {
                    List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = API_OutputParam_Bean.getList_OutParam_ImageAdvanced_Items();
                    for (int x = 0; x < list_IA_Map_Item.size(); x++) {
                        if (list_IA_Map_Item.get(x).getImageAdvanced_Value().equalsIgnoreCase(Advance_ConditionColumn)) {
                            if (ImageView.controlObject.getImageDataType().equalsIgnoreCase("Base64")) {
                                ImageView.mainImageView.setImageBitmap(ImproveHelper.Base64StringToBitmap(Advance_ImageSource));
                            } else {
                                ImproveHelper.loadImage_new(context, Advance_ImageSource, ImageView.mainImageView, false, list_IA_Map_Item.get(x).getImageAdvanced_ImagePath());
                            }
                            break;
                        } else {
                            if (ImageView.controlObject.getImageDataType().equalsIgnoreCase("Base64")) {
                                ImageView.mainImageView.setImageBitmap(ImproveHelper.Base64StringToBitmap(Advance_ImageSource));
                            } else {
                                ImproveHelper.loadImage_new(context, Advance_ImageSource, ImageView.mainImageView, false, "null");
                            }
                            break;
                        }
                    }
                } else {
                    List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = API_OutputParam_Bean.getList_OutParam_ImageAdvanced_Items();
                    for (int x = 0; x < list_IA_Map_Item.size(); x++) {
                        if (list_IA_Map_Item.get(x).getImageAdvanced_Value().equalsIgnoreCase(Advance_ConditionColumn)) {
                            if (ImageView.controlObject.getImageDataType().equalsIgnoreCase("Base64")) {
                                ImageView.mainImageView.setImageBitmap(ImproveHelper.Base64StringToBitmap(list_IA_Map_Item.get(x).getImageAdvanced_Value()));
                            } else {
                                ImproveHelper.loadImage_new(context, list_IA_Map_Item.get(x).getImageAdvanced_Value(), ImageView.mainImageView, false, ImageView.getControlObject().getImageData());
                            }
                        }
                    }
                }
            }
        } else if (List_ControlClassObjects.get(ControlID) instanceof LargeInput) {
            LargeInput LargeInputView = (LargeInput) List_ControlClassObjects.get(ControlID);
            LargeInputView.setDefaultValue(Value);
        } else if (List_ControlClassObjects.get(ControlID) instanceof MapControl) {
            MapControl mapControl = (MapControl) List_ControlClassObjects.get(ControlID);
            if (API_OutputParam_Bean.getOutParam_Mapped_ID() != null && API_OutputParam_Bean.getOutParam_Mapped_ID().length() > 0) {
                Value = OutputData.size()>0?OutputData.get(API_OutputParam_Bean.getOutParam_Mapped_ID().toLowerCase()).get(0):"";
                List<String> MappedValues1 = new ArrayList<>();
                MappedValues1.add(Value);
                HashMap<String, List<String>> hash_popupdata = new LinkedHashMap<String, List<String>>();
                if (MainActivity.getInstance() != null) {
                    MainActivity.getInstance().subFormMapControls.add(mapControl);
                    MainActivity.getInstance().subFormMappedValues.add(MappedValues1);
                }

                String DefultMarker = API_OutputParam_Bean.getOutParam_Marker_defultImage();
                String RenderingType = API_OutputParam_Bean.getOutParam_Marker_RenderingType();
                List<String> ConditionColumn = new ArrayList<>();
                if (API_OutputParam_Bean.getOutParam_MarkerAdvanced_ConditionColumn() != null) {
                    if(OutputData.size()>0){
                        ConditionColumn.add(OutputData.get(API_OutputParam_Bean.getOutParam_MarkerAdvanced_ConditionColumn().toLowerCase()).get(0));
                    }
                }
                String Operator = API_OutputParam_Bean.getOutParam_MarkerAdvanced_Operator();
                if (API_OutputParam_Bean.getOutParam_Marker_popupData() != null && API_OutputParam_Bean.getOutParam_Marker_popupData().size() > 0) {
                    for (int x = 0; x < API_OutputParam_Bean.getOutParam_Marker_popupData().size(); x++) {
                        List<String> outDatapopupData = new ArrayList<>();
                        if(OutputData.size()>0){
                            outDatapopupData.add(OutputData.get(API_OutputParam_Bean.getOutParam_Marker_popupData().get(x).toLowerCase()).get(0));
                        }

                        hash_popupdata.put(API_OutputParam_Bean.getOutParam_Marker_popupData().get(x), outDatapopupData);
                    }
                }
                if (API_OutputParam_Bean.getList_OutParam_MarkerAdvanced_Items() != null && API_OutputParam_Bean.getList_OutParam_MarkerAdvanced_Items().size() > 0) {
                    mapControl.setMapMarkersDynamically(RenderingType, DefultMarker, MappedValues1, ConditionColumn, Operator, API_OutputParam_Bean.getList_OutParam_MarkerAdvanced_Items(), API_OutputParam_Bean.getOutParam_Marker_popupData(), hash_popupdata,true);
                } else {
                    mapControl.setMapMarkersDynamically(RenderingType, DefultMarker, MappedValues1, null, null, null, API_OutputParam_Bean.getOutParam_Marker_popupData(), hash_popupdata,true);
                }

//                                mapControl.setMapMarkersDynamically(RenderingType,DefultMarker,MappedValues1,null,null,null);

            }
        } else if (List_ControlClassObjects.get(ControlID) instanceof Checkbox) {
            Checkbox CheckBoxView = (Checkbox) List_ControlClassObjects.get(ControlID);
            CheckBoxView.setValueToCheckBoxItem(Value);
            //CheckBoxView.setCheckBoxValues(Value);
        } else if (List_ControlClassObjects.get(ControlID) instanceof FileBrowsing) {
            FileBrowsing fileBrowsing = (FileBrowsing) List_ControlClassObjects.get(ControlID);
            if (Value != null && !Value.trim().isEmpty()) {
                fileBrowsing.setFileBrowsing(Value);
                fileBrowsing.setPath(Value);
            }
        } else if (List_ControlClassObjects.get(ControlID) instanceof Calendar) {
            Calendar calendar = (Calendar) List_ControlClassObjects.get(ControlID);
            calendar.getCe_TextType().setVisibility(View.VISIBLE);
            String strCalendarValue = Value;
            if (strCalendarValue != null && !strCalendarValue.isEmpty()) {
                calendar.setSelectedDate(strCalendarValue);
            }
        } else if (List_ControlClassObjects.get(ControlID) instanceof Percentage) {
            Percentage PercentageView = (Percentage) List_ControlClassObjects.get(ControlID);
            PercentageView.getCustomEditText().setText(Value);
            CustomTextView tv_pertapTextType = PercentageView.gettap_text();
            tv_pertapTextType.setVisibility(View.GONE);
            PercentageView.getCustomEditText().setVisibility(View.VISIBLE);
        } else if (List_ControlClassObjects.get(updatedControlID) instanceof RadioGroupView) {
            RadioGroupView RGroup = (RadioGroupView) List_ControlClassObjects.get(updatedControlID);
                           /* if (RGroup != null) {
                                RGroup.setEditValue(RGroup);
                            }*/
            boolean foundflag = true;
            List<Item> AppendValues_list_rg = new ArrayList<>();
            if (ControlID.equalsIgnoreCase(RGroup.getControlObject().getControlName())) {
//                                List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                List<String> Values = new ArrayList<>();
                if (API_OutputParam_Bean.getOutParam_Mapped_Expression() == null || API_OutputParam_Bean.getOutParam_Mapped_Expression().length() == 0) {
                    if (MappedControlID.trim().length() > 0) {
                        if(OutputData.size()>0){
                            Values = OutputData.get(MappedControlID.toLowerCase());
                        }

                    } else if (API_OutputParam_Bean.getList_OutParam_Languages() != null && API_OutputParam_Bean.getList_OutParam_Languages().size() > 0) {
                        String appLanguage = ImproveHelper.getLocale(context);
                        List<LanguageMapping> languageMappings = API_OutputParam_Bean.getList_OutParam_Languages();
                        for (int j = 0; j < languageMappings.size(); j++) {
                            LanguageMapping languageMapping = languageMappings.get(j);
                            if (languageMapping.getOutParam_Lang_Name().equalsIgnoreCase(appLanguage)) {
                                if(OutputData.size()>0){
                                    Values = OutputData.get(languageMapping.getOutParam_Lang_Mapped().toLowerCase());
                                }
                            }
                        }
                    }
                } else {
                    MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_Expression().trim();
                    ExpressionMainHelper ehelper = new ExpressionMainHelper();
                    Value = ehelper.ExpressionHelper(context, API_OutputParam_Bean.getOutParam_Mapped_Expression().trim());
                    String[] valarr = Value.split("\\,");
                    Values.addAll(Arrays.asList(valarr));
                }

                for (int j = 0; j < Values.size(); j++) {
                    Item item = new Item();
                    item.setValue(Values.get(j).trim());
                    item.setId(Values.get(j).trim());
                    AppendValues_list_rg.add(item);
                }
//                                }
            } else if (ControlID.equalsIgnoreCase(RGroup.getControlObject().getControlName() + "_ID")) {
                AppendValues_list_rg = RGroup.getnewItemsListDynamically();
                if (AppendValues_list_rg.size() > 0) {
//                                    List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                    List<String> Values = new ArrayList<>();

                    if (API_OutputParam_Bean.getOutParam_Mapped_Expression() == null || API_OutputParam_Bean.getOutParam_Mapped_Expression().length() == 0) {
                        if (MappedControlID.trim().length() > 0) {
                            if(OutputData.size()>0){
                                Values = OutputData.get(MappedControlID.toLowerCase());
                            }

                        }
                    } else {
                        MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_Expression().trim();
                        ExpressionMainHelper ehelper = new ExpressionMainHelper();
                        Value = ehelper.ExpressionHelper(context, API_OutputParam_Bean.getOutParam_Mapped_Expression().trim());
                        String[] valarr = Value.split("\\,");
                        Values.addAll(Arrays.asList(valarr));
                    }


                    for (int j = 0; j < AppendValues_list_rg.size(); j++) {
                        Item item = AppendValues_list_rg.get(j);
                        item.setId(Values.get(j).trim());
                        AppendValues_list_rg.set(j, item);
                    }
                } else {
                    List<String> Values = OutputData.size()>0?OutputData.get(MappedControlID.toLowerCase()):new ArrayList<>();
                    for (int j = 0; j < Values.size(); j++) {
                        Item item = new Item();
                        item.setValue(Values.get(j).trim());
                        item.setId(Values.get(j).trim());
                        AppendValues_list_rg.add(item);
                    }
                }
            }

            if (foundflag) {
                RGroup.setnewItemsListDynamically(AppendValues_list_rg);
            }
        } else if (List_ControlClassObjects.get(updatedControlID) instanceof DropDown) {
            DropDown dropDown = (DropDown) List_ControlClassObjects.get(updatedControlID);
                            /*if (dropDown != null) {
                                if (controlValue.get("Value_id").startsWith("0")) {
//                                dropDown.setItemId(controlValue.get("Value_id").substring(1),controlValue.get("Value"));
                                    dropDown.setItemId(controlValue.get("Value_id"), controlValue.get("Value"));
                                } else {
                                    dropDown.setItemId(controlValue.get("Value_id"), controlValue.get("Value"));
                                }
                            }*/
            boolean dropfoundflag = true;
            List<Item> AppendValues_list = new ArrayList<>();


            if (ControlID.equalsIgnoreCase(dropDown.getControlObject().getControlName())) {
//                                List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                List<String> Values = new ArrayList<>();
                if (API_OutputParam_Bean.getOutParam_Mapped_Expression() == null || API_OutputParam_Bean.getOutParam_Mapped_Expression().length() == 0) {
                    if (MappedControlID.trim().length() > 0) {
                        if(OutputData.size()>0){
                            Values = OutputData.get(MappedControlID.toLowerCase());
                        }

                    } else if (API_OutputParam_Bean.getList_OutParam_Languages() != null && API_OutputParam_Bean.getList_OutParam_Languages().size() > 0) {
                        String appLanguage = ImproveHelper.getLocale(context);
                        List<LanguageMapping> languageMappings = API_OutputParam_Bean.getList_OutParam_Languages();
                        for (int j = 0; j < languageMappings.size(); j++) {
                            LanguageMapping languageMapping = languageMappings.get(j);
                            if (languageMapping.getOutParam_Lang_Name().equalsIgnoreCase(appLanguage)) {
                                if(OutputData.size()>0){
                                    Values = OutputData.get(languageMapping.getOutParam_Lang_Mapped().toLowerCase());
                                }
                            }
                        }
                    }
                } else {
                    MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_Expression().trim();
                    ExpressionMainHelper ehelper = new ExpressionMainHelper();
                    Value = ehelper.ExpressionHelper(context, API_OutputParam_Bean.getOutParam_Mapped_Expression().trim());
                    String[] valarr = Value.split("\\,");
                    Values.addAll(Arrays.asList(valarr));
                }

                for (int j = 0; j < Values.size(); j++) {
                    Item item = new Item();
                    item.setValue(Values.get(j).trim());
                    item.setId(Values.get(j).trim());
                    AppendValues_list.add(item);
                }
//                                }
            } else if (ControlID.equalsIgnoreCase(dropDown.getControlObject().getControlName() + "_ID")) {
                AppendValues_list = dropDown.getnewItemsListDynamically();
                if (AppendValues_list.size() > 0) {
//                                    List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                    List<String> Values = new ArrayList<>();

                    if (API_OutputParam_Bean.getOutParam_Mapped_Expression() == null || API_OutputParam_Bean.getOutParam_Mapped_Expression().length() == 0) {
                        if (MappedControlID.trim().length() > 0) {
                            Values = OutputData.get(MappedControlID.toLowerCase());
                        }
                    } else {
                        MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_Expression().trim();
                        ExpressionMainHelper ehelper = new ExpressionMainHelper();
                        Value = ehelper.ExpressionHelper(context, API_OutputParam_Bean.getOutParam_Mapped_Expression().trim());
                        String[] valarr = Value.split("\\,");
                        Values.addAll(Arrays.asList(valarr));
                    }


                    for (int j = 0; j < AppendValues_list.size(); j++) {
                        Item item = AppendValues_list.get(j);
                        item.setId(Values.get(j).trim());
                        AppendValues_list.set(j, item);
                    }
                } else {
                    List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                    for (int j = 0; j < Values.size(); j++) {
                        Item item = new Item();
                        item.setValue(Values.get(j).trim());
                        item.setId(Values.get(j).trim());
                        AppendValues_list.add(item);
                    }
                }
            }

            if (dropfoundflag) {
                dropDown.setnewItemsListDynamically(AppendValues_list);
            }
        } else if (List_ControlClassObjects.get(updatedControlID) instanceof CheckList) {
            CheckList checklist = (CheckList) List_ControlClassObjects.get(updatedControlID);
            boolean CheckListflag = true;

            List<Item> AppendValues_check_list = new ArrayList<>();


            if (ControlID.equalsIgnoreCase(checklist.getControlObject().getControlName())) {
//                                List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                List<String> Values = new ArrayList<>();
                if (API_OutputParam_Bean.getOutParam_Mapped_Expression() == null || API_OutputParam_Bean.getOutParam_Mapped_Expression().length() == 0) {
                    if (MappedControlID.trim().length() > 0) {
                        Values = OutputData.get(MappedControlID.toLowerCase());
                    } else if (API_OutputParam_Bean.getList_OutParam_Languages() != null && API_OutputParam_Bean.getList_OutParam_Languages().size() > 0) {
                        String appLanguage = ImproveHelper.getLocale(context);
                        List<LanguageMapping> languageMappings = API_OutputParam_Bean.getList_OutParam_Languages();
                        for (int j = 0; j < languageMappings.size(); j++) {
                            LanguageMapping languageMapping = languageMappings.get(j);
                            if (languageMapping.getOutParam_Lang_Name().equalsIgnoreCase(appLanguage)) {
                                Values = OutputData.get(languageMapping.getOutParam_Lang_Mapped().toLowerCase());
                            }
                        }
                    }
                } else {
                    MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_Expression().trim();
                    ExpressionMainHelper ehelper = new ExpressionMainHelper();
                    Value = ehelper.ExpressionHelper(context, API_OutputParam_Bean.getOutParam_Mapped_Expression().trim());
                    String[] valarr = Value.split("\\,");
                    Values.addAll(Arrays.asList(valarr));
                }

                for (int j = 0; j < Values.size(); j++) {
                    Item item = new Item();
                    item.setValue(Values.get(j).trim());
                    item.setId(Values.get(j).trim());
                    AppendValues_check_list.add(item);
                }
//                                }
            } else if (ControlID.equalsIgnoreCase(checklist.getControlObject().getControlName() + "_ID")) {
                AppendValues_check_list = checklist.getListOfSelectedItems();
//                                AppendValues_check_list = checklist.getnewItemsListDynamically();
                if (AppendValues_check_list.size() > 0) {
//                                    List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                    List<String> Values = new ArrayList<>();

                    if (API_OutputParam_Bean.getOutParam_Mapped_Expression() == null || API_OutputParam_Bean.getOutParam_Mapped_Expression().length() == 0) {
                        if (MappedControlID.trim().length() > 0) {
                            Values = OutputData.get(MappedControlID.toLowerCase());
                        }
                    } else {
                        MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_Expression().trim();
                        ExpressionMainHelper ehelper = new ExpressionMainHelper();
                        Value = ehelper.ExpressionHelper(context, API_OutputParam_Bean.getOutParam_Mapped_Expression().trim());
                        String[] valarr = Value.split("\\,");
                        Values.addAll(Arrays.asList(valarr));
                    }


                    for (int j = 0; j < AppendValues_check_list.size(); j++) {
                        Item item = AppendValues_check_list.get(j);
                        item.setId(Values.get(j).trim());
                        AppendValues_check_list.set(j, item);
                    }
                } else {
                    List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                    for (int j = 0; j < Values.size(); j++) {
                        Item item = new Item();
                        item.setValue(Values.get(j).trim());
                        item.setId(Values.get(j).trim());
                        AppendValues_check_list.add(item);
                    }
                }
            }

            if (CheckListflag) {
                checklist.setnewItemsListDynamically(AppendValues_check_list);
            }
        } else if (List_ControlClassObjects.get(ControlID) instanceof SignatureView) {
            SignatureView signatureView = (SignatureView) List_ControlClassObjects.get(ControlID);
            signatureView.setSignatureForEdit(Value);
        } else if (List_ControlClassObjects.get(ControlID) instanceof UrlView) {
            UrlView urlView = (UrlView) List_ControlClassObjects.get(ControlID);
            urlView.setText(Value);
        } else if (List_ControlClassObjects.get(ControlID) instanceof DecimalView) {
            DecimalView Decimalview = (DecimalView) List_ControlClassObjects.get(ControlID);
            Decimalview.getCustomEditText().setText(Value);
            CustomTextView tv_DectapTextType = Decimalview.gettap_text();
            tv_DectapTextType.setVisibility(View.GONE);
            Decimalview.getCustomEditText().setVisibility(View.VISIBLE);
        } else if (List_ControlClassObjects.get(ControlID) instanceof Password) {
            Password Passwordview = (Password) List_ControlClassObjects.get(ControlID);
            Passwordview.getCustomEditText().setText(Value);
            Passwordview.getTv_tapTextType().setVisibility(View.GONE);
            LinearLayout ll_tap_text = Passwordview.gettap_text();
            ll_tap_text.setVisibility(View.VISIBLE);
            Passwordview.getTil_password().setVisibility(View.VISIBLE);
//                            Passwordview.getCustomEditText().setVisibility(View.VISIBLE);
        } else if (List_ControlClassObjects.get(ControlID) instanceof Currency) {
            Currency Currencyview = (Currency) List_ControlClassObjects.get(ControlID);
            Currencyview.getCustomEditText().setText(Value);
            CustomTextView tv_CurtapTextType = Currencyview.gettap_text();
            tv_CurtapTextType.setVisibility(View.GONE);
            Currencyview.getCustomEditText().setVisibility(View.VISIBLE);
        } else if (List_ControlClassObjects.get(ControlID) instanceof Rating) {
            Rating ratingView = (Rating) List_ControlClassObjects.get(ControlID);
            ratingView.setRating(Value);
        } else if (List_ControlClassObjects.get(ControlID) instanceof DynamicLabel) {
            DynamicLabel DynamicLabeliew = (DynamicLabel) List_ControlClassObjects.get(ControlID);
            CustomTextView tv_dynamicLabel = DynamicLabeliew.getValueView();
//                            tv_dynamicLabel.setText(Value);
            DynamicLabeliew.setValue(Value);
        } else if (List_ControlClassObjects.get(ControlID) instanceof QRCode) {
            QRCode qrCode = (QRCode) List_ControlClassObjects.get(ControlID);
            qrCode.createQrCodeDynamically(Value);
        } else if (List_ControlClassObjects.get(ControlID) instanceof BarCode) {
            BarCode barCode = (BarCode) List_ControlClassObjects.get(ControlID);
            barCode.createBarCodeDynamically(Value);
        } else if (List_ControlClassObjects.get(ControlID) instanceof Time) {
            Time time_control = (Time) List_ControlClassObjects.get(ControlID);
            if (Value != null) {
                Value = Value.split("\\,")[0];
                if (!Value.contentEquals("")) {
                    time_control.setTimeData(Value);
                }
            }
        } else if (List_ControlClassObjects.get(ControlID) instanceof VoiceRecording) {
            VoiceRecording voiceRecording = (VoiceRecording) List_ControlClassObjects.get(ControlID);
            Value = Value.split("\\,")[0];
            if ((!Value.contentEquals("")) || (!Value.contentEquals(" "))) {
                if (!Value.contains("File not found")) {
                    voiceRecording.setUploadedFile(Value);
                }
            } else {
                voiceRecording.setUploadedFile("");
            }
        } else if (List_ControlClassObjects.get(ControlID) instanceof AutoCompletionControl) {
            AutoCompletionControl autoCompletionControl = (AutoCompletionControl) List_ControlClassObjects.get(ControlID);
            List<String> MappedValues = OutputData.get(MappedControlID.toLowerCase());
            autoCompletionControl.setList_ControlItems(MappedValues);

        } else if (List_ControlClassObjects.get(ControlID) instanceof ViewFileControl) {
            ViewFileControl viewFileControl = (ViewFileControl) List_ControlClassObjects.get(ControlID);
            viewFileControl.setFileLink(Value);
        } else if (List_ControlClassObjects.get(ControlID) instanceof VideoRecording) {
            VideoRecording videoRecording = (VideoRecording) List_ControlClassObjects.get(ControlID);
            Value = Value.split("\\,")[0];
            if (!Value.contains("File not found")) {
                videoRecording.setVideoRecordingPath(Value);
                videoRecording.displayVideoPreview();
            }
        } else if (List_ControlClassObjects.get(ControlID) instanceof PostControl) {
            //no code
                           /* if (!controlValue.get("Value_id").contentEquals("")) {
                                String Value_id = controlValue.get("Value_id");
                                String Value = controlValue.get("Value");
                                PostControl postControl = (PostControl) viewObj.get(controlName);
                                postControl.setEditValues(Value, Value_id);
                            }*/
        } else if (List_ControlClassObjects.get(ControlID) instanceof UserControl) {
            //no code
                          /*  if (!controlValue.get("Value_id").contentEquals("")) {
                                String Value_id = controlValue.get("Value_id");
                                String Value = controlValue.get("Value");
                                UserControl userControl = (UserControl) viewObj.get(controlName);
                                userControl.setEditValues(Value, Value_id);
                            }*/
        } else if (List_ControlClassObjects.get(ControlID) instanceof Gps_Control) {
            //no code
            Gps_Control gps_control = (Gps_Control) List_ControlClassObjects.get(ControlID);
/* if (!controlValue.get("Coordinates").trim().contentEquals("")) {
                        String coordinates = controlValue.get("Coordinates");
                        String type = controlValue.get("Type");
                        List<String> Points = ImproveHelper.getGpsCoordinates(coordinates);
                        gps_control.setMapPointsDynamically(type, Points, null);
                    }*/
            //                            if (!Value.contains("File not found")) {
//                                final Handler handler = new Handler();
//                                handler.postDelayed(new Runnable() {
//                                    @RequiresApi(api = Build.VERSION_CODES.M)
//                                    @Override
//                                    public void run() {
//                                        //Do something after delay
///*
//                                        if (!controlValue.get("Coordinates").trim().contentEquals("")) {
//                                            String coordinates = controlValue.get("Coordinates");
//                                            String type = controlValue.get("Type");
//                                            List<String> Points = ImproveHelper.getGpsCoordinatesFromAction(coordinates);
//                                            gps_control.setMapPointsDynamically(type, Points, null);
//                                        }
//*/
//                                    }
//                                }, 1000);
//
//
//
//                            }
        } else if (List_ControlClassObjects.get(ControlID) instanceof DataControl) {
            //no code
            DataControl dataControl = (DataControl) List_ControlClassObjects.get(ControlID);
                           /* if (!controlValue.get("Value_id").contentEquals("")) {
                                SearchableSpinner searchableSpinner = dataControl.getSpinner();
                                searchableSpinner.setItemID(controlValue.get("Value_id"));
                            }*/
        }

    }


    private static void setSingleOrMultiValueToControl(Context context, ControlObject temp_controlObj,
                                                       LinkedHashMap<String, Object> List_ControlClassObjects,
                                                       String Value,
                                                       LinkedHashMap<String, List<String>> OutputData,
                                                       String ControlID, String MappedControlID,
                                                       API_OutputParam_Bean API_OutputParam_Bean) {


        switch (temp_controlObj.getControlType()) {
            case CONTROL_TYPE_TEXT_INPUT:
                TextInput textInput = (TextInput) List_ControlClassObjects.get(temp_controlObj.getControlName());
                Value = Value.split("\\,")[0];
                textInput.setEditValue(Value);
                CustomTextView tv_tapTextType = textInput.gettap_text();
                tv_tapTextType.setVisibility(View.GONE);
                textInput.getCustomEditText().setVisibility(View.VISIBLE);

                break;
            case CONTROL_TYPE_NUMERIC_INPUT:
                NumericInput numverTextView = (NumericInput) List_ControlClassObjects.get(temp_controlObj.getControlName());
                Value = Value.split("\\,")[0];
                numverTextView.getNumericTextView().setText(Value);
                CustomTextView tv_numtapTextType = numverTextView.gettap_text();
                tv_numtapTextType.setVisibility(View.GONE);
                numverTextView.getNumericTextView().setVisibility(View.VISIBLE);
                numverTextView.gettap_text().setVisibility(View.GONE);
                break;
            case CONTROL_TYPE_PHONE:
                Phone PhoneView = (Phone) List_ControlClassObjects.get(temp_controlObj.getControlName());
                Value = Value.split("\\,")[0];
                PhoneView.getCustomEditText().setText(Value);
                CustomTextView tv_phonetapTextType = PhoneView.gettap_text();
                tv_phonetapTextType.setVisibility(View.GONE);
                PhoneView.getCustomEditText().setVisibility(View.VISIBLE);
                break;
            case CONTROL_TYPE_EMAIL:
                Email EmailView = (Email) List_ControlClassObjects.get(temp_controlObj.getControlName());
                Value = Value.split("\\,")[0];
                EmailView.getCustomEditText().setText(Value);
                CustomTextView tv_emailtapTextType = EmailView.gettap_text();
                tv_emailtapTextType.setVisibility(View.GONE);
                EmailView.getCustomEditText().setVisibility(View.VISIBLE);
                break;
            case CONTROL_TYPE_CAMERA:
                Camera camera = (Camera) List_ControlClassObjects.get(temp_controlObj.getControlName());
                Value = Value.split("\\,")[0];
                camera.setImageForEdit(Value);
                break;
            case CONTROL_TYPE_IMAGE:
                Image ImageView = (Image) List_ControlClassObjects.get(temp_controlObj.getControlName());
                String Advance_ImageSource = "", Advance_ConditionColumn = "";
                if (API_OutputParam_Bean.getOutParam_Mapped_ID() != null && API_OutputParam_Bean.getOutParam_Mapped_ID().length() > 0) {
                    Value = OutputData.get(API_OutputParam_Bean.getOutParam_Mapped_ID().toLowerCase()).get(0);
                    if (ImageView.controlObject.getImageDataType().equalsIgnoreCase("Base64")) {
                        ImageView.mainImageView.setImageBitmap(ImproveHelper.Base64StringToBitmap(Value));
                    } else {
                        ImproveHelper.loadImage_new(context, Value, ImageView.mainImageView, false, temp_controlObj.getImageData());
                    }
                } else if (API_OutputParam_Bean.getList_OutParam_ImageAdvanced_Items() != null && API_OutputParam_Bean.getList_OutParam_ImageAdvanced_Items().size() > 0) {
                    if (API_OutputParam_Bean.getOutParam_ImageAdvanced_ImageorNot().equalsIgnoreCase("Yes")) {
                        Advance_ImageSource = OutputData.get(API_OutputParam_Bean.getOutParam_ImageAdvanced_ImageSource().toLowerCase()).get(0);
                    }
                    Advance_ConditionColumn = OutputData.get(API_OutputParam_Bean.getOutParam_ImageAdvanced_ConditionColumn().toLowerCase()).get(0);

                    if (Advance_ImageSource != null && Advance_ImageSource.length() != 0) {
                        List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = API_OutputParam_Bean.getList_OutParam_ImageAdvanced_Items();
                        for (int x = 0; x < list_IA_Map_Item.size(); x++) {
                            if (list_IA_Map_Item.get(x).getImageAdvanced_Value().equalsIgnoreCase(Advance_ConditionColumn)) {
                                if (ImageView.controlObject.getImageDataType().equalsIgnoreCase("Base64")) {
                                    ImageView.mainImageView.setImageBitmap(ImproveHelper.Base64StringToBitmap(Advance_ImageSource));
                                } else {
                                    ImproveHelper.loadImage_new(context, Advance_ImageSource, ImageView.mainImageView, false, list_IA_Map_Item.get(x).getImageAdvanced_ImagePath());
                                }
                                break;
                            } else {
                                if (ImageView.controlObject.getImageDataType().equalsIgnoreCase("Base64")) {
                                    ImageView.mainImageView.setImageBitmap(ImproveHelper.Base64StringToBitmap(Advance_ImageSource));
                                } else {
                                    ImproveHelper.loadImage_new(context, Advance_ImageSource, ImageView.mainImageView, false, "null");
                                }
                                break;
                            }
                        }
                    } else {
                        List<ImageAdvanced_Mapped_Item> list_IA_Map_Item = API_OutputParam_Bean.getList_OutParam_ImageAdvanced_Items();
                        for (int x = 0; x < list_IA_Map_Item.size(); x++) {
                            if (list_IA_Map_Item.get(x).getImageAdvanced_Value().equalsIgnoreCase(Advance_ConditionColumn)) {
                                if (ImageView.controlObject.getImageDataType().equalsIgnoreCase("Base64")) {
                                    ImageView.mainImageView.setImageBitmap(ImproveHelper.Base64StringToBitmap(list_IA_Map_Item.get(x).getImageAdvanced_Value()));
                                } else {
                                    ImproveHelper.loadImage_new(context, list_IA_Map_Item.get(x).getImageAdvanced_Value(), ImageView.mainImageView, false, temp_controlObj.getImageData());
                                }
                            }
                        }
                    }
                }
                break;
            case CONTROL_TYPE_LARGE_INPUT:
                LargeInput LargeInputView = (LargeInput) List_ControlClassObjects.get(temp_controlObj.getControlName());
                LargeInputView.setDefaultValue(Value);
                break;
            case CONTROL_TYPE_MAP:
                MapControl mapControl = (MapControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                if (API_OutputParam_Bean.getOutParam_Mapped_ID() != null && API_OutputParam_Bean.getOutParam_Mapped_ID().length() > 0) {
                    Value = OutputData.get(API_OutputParam_Bean.getOutParam_Mapped_ID().toLowerCase()).get(0);
                    List<String> MappedValues1 = new ArrayList<>();
                    MappedValues1.add(Value);
                    HashMap<String, List<String>> hash_popupdata = new LinkedHashMap<String, List<String>>();
                    if (MainActivity.getInstance() != null) {
                        MainActivity.getInstance().subFormMapControls.add(mapControl);
                        MainActivity.getInstance().subFormMappedValues.add(MappedValues1);
                    }

                    String DefultMarker = API_OutputParam_Bean.getOutParam_Marker_defultImage();
                    String RenderingType = API_OutputParam_Bean.getOutParam_Marker_RenderingType();
                    List<String> ConditionColumn = new ArrayList<>();
                    if (API_OutputParam_Bean.getOutParam_MarkerAdvanced_ConditionColumn() != null) {
                        ConditionColumn.add(OutputData.get(API_OutputParam_Bean.getOutParam_MarkerAdvanced_ConditionColumn().toLowerCase()).get(0));
                    }
                    String Operator = API_OutputParam_Bean.getOutParam_MarkerAdvanced_Operator();
                    if (API_OutputParam_Bean.getOutParam_Marker_popupData() != null && API_OutputParam_Bean.getOutParam_Marker_popupData().size() > 0) {
                        for (int x = 0; x < API_OutputParam_Bean.getOutParam_Marker_popupData().size(); x++) {
                            List<String> outDatapopupData = new ArrayList<>();
                            outDatapopupData.add(OutputData.get(API_OutputParam_Bean.getOutParam_Marker_popupData().get(x).toLowerCase()).get(0));
                            hash_popupdata.put(API_OutputParam_Bean.getOutParam_Marker_popupData().get(x), outDatapopupData);
                        }
                    }
                    if (API_OutputParam_Bean.getList_OutParam_MarkerAdvanced_Items() != null && API_OutputParam_Bean.getList_OutParam_MarkerAdvanced_Items().size() > 0) {
                        mapControl.setMapMarkersDynamically(RenderingType, DefultMarker, MappedValues1, ConditionColumn, Operator, API_OutputParam_Bean.getList_OutParam_MarkerAdvanced_Items(), API_OutputParam_Bean.getOutParam_Marker_popupData(), hash_popupdata,true);
                    } else {
                        mapControl.setMapMarkersDynamically(RenderingType, DefultMarker, MappedValues1, null, null, null, API_OutputParam_Bean.getOutParam_Marker_popupData(), hash_popupdata,true);
                    }

//                                mapControl.setMapMarkersDynamically(RenderingType,DefultMarker,MappedValues1,null,null,null);

                }
                break;

            case CONTROL_TYPE_CHECKBOX:
                Checkbox CheckBoxView = (Checkbox) List_ControlClassObjects.get(temp_controlObj.getControlName());
                CheckBoxView.setValueToCheckBoxItem(Value);
                //CheckBoxView.setCheckBoxValues(Value);

                break;
            case CONTROL_TYPE_FILE_BROWSING:
                FileBrowsing fileBrowsing = (FileBrowsing) List_ControlClassObjects.get(temp_controlObj.getControlName());
                if (Value != null && !Value.trim().isEmpty()) {
                    fileBrowsing.setFileBrowsing(Value);
                    fileBrowsing.setPath(Value);
                }
                break;
            case CONTROL_TYPE_CALENDER:
                Calendar calendar = (Calendar) List_ControlClassObjects.get(temp_controlObj.getControlName());
                calendar.getCe_TextType().setVisibility(View.VISIBLE);
                String strCalendarValue = Value;
                if (strCalendarValue != null && !strCalendarValue.isEmpty()) {
                    calendar.setSelectedDate(strCalendarValue);
                }
                break;
            case CONTROL_TYPE_AUDIO_PLAYER:

                break;
            case CONTROL_TYPE_VIDEO_PLAYER:

                break;
            case CONTROL_TYPE_PERCENTAGE:
                Percentage PercentageView = (Percentage) List_ControlClassObjects.get(temp_controlObj.getControlName());
                PercentageView.getCustomEditText().setText(Value);
                CustomTextView tv_pertapTextType = PercentageView.gettap_text();
                tv_pertapTextType.setVisibility(View.GONE);
                PercentageView.getCustomEditText().setVisibility(View.VISIBLE);
                break;
            case CONTROL_TYPE_RADIO_BUTTON:
                RadioGroupView RGroup = (RadioGroupView) List_ControlClassObjects.get(temp_controlObj.getControlName());
                           /* if (RGroup != null) {
                                RGroup.setEditValue(RGroup);
                            }*/
                boolean foundflag = true;
                List<Item> AppendValues_list_rg = new ArrayList<>();
                if (ControlID.equalsIgnoreCase(temp_controlObj.getControlName())) {
//                                List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                    List<String> Values = new ArrayList<>();
                    if (API_OutputParam_Bean.getOutParam_Mapped_Expression() == null || API_OutputParam_Bean.getOutParam_Mapped_Expression().length() == 0) {
                        if (MappedControlID.trim().length() > 0) {
                            Values = OutputData.get(MappedControlID.toLowerCase());
                        } else if (API_OutputParam_Bean.getList_OutParam_Languages() != null && API_OutputParam_Bean.getList_OutParam_Languages().size() > 0) {
                            String appLanguage = ImproveHelper.getLocale(context);
                            List<LanguageMapping> languageMappings = API_OutputParam_Bean.getList_OutParam_Languages();
                            for (int j = 0; j < languageMappings.size(); j++) {
                                LanguageMapping languageMapping = languageMappings.get(j);
                                if (languageMapping.getOutParam_Lang_Name().equalsIgnoreCase(appLanguage)) {
                                    Values = OutputData.get(languageMapping.getOutParam_Lang_Mapped().toLowerCase());
                                }
                            }
                        }
                    } else {
                        MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_Expression().trim();
                        ExpressionMainHelper ehelper = new ExpressionMainHelper();
                        Value = ehelper.ExpressionHelper(context, API_OutputParam_Bean.getOutParam_Mapped_Expression().trim());
                        String[] valarr = Value.split("\\,");
                        Values.addAll(Arrays.asList(valarr));
                    }

                    for (int j = 0; j < Values.size(); j++) {
                        Item item = new Item();
                        item.setValue(Values.get(j).trim());
                        item.setId(Values.get(j).trim());
                        AppendValues_list_rg.add(item);
                    }
//                                }
                } else if (ControlID.equalsIgnoreCase(temp_controlObj.getControlName() + "_ID")) {
                    AppendValues_list_rg = RGroup.getnewItemsListDynamically();
                    if (AppendValues_list_rg.size() > 0) {
//                                    List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                        List<String> Values = new ArrayList<>();

                        if (API_OutputParam_Bean.getOutParam_Mapped_Expression() == null || API_OutputParam_Bean.getOutParam_Mapped_Expression().length() == 0) {
                            if (MappedControlID.trim().length() > 0) {
                                Values = OutputData.get(MappedControlID.toLowerCase());
                            }
                        } else {
                            MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_Expression().trim();
                            ExpressionMainHelper ehelper = new ExpressionMainHelper();
                            Value = ehelper.ExpressionHelper(context, API_OutputParam_Bean.getOutParam_Mapped_Expression().trim());
                            String[] valarr = Value.split("\\,");
                            Values.addAll(Arrays.asList(valarr));
                        }


                        for (int j = 0; j < AppendValues_list_rg.size(); j++) {
                            Item item = AppendValues_list_rg.get(j);
                            item.setId(Values.get(j).trim());
                            AppendValues_list_rg.set(j, item);
                        }
                    } else {
                        List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                        for (int j = 0; j < Values.size(); j++) {
                            Item item = new Item();
                            item.setValue(Values.get(j).trim());
                            item.setId(Values.get(j).trim());
                            AppendValues_list_rg.add(item);
                        }
                    }
                }

                if (foundflag) {
                    RGroup.setnewItemsListDynamically(AppendValues_list_rg);
                }
                break;
            case CONTROL_TYPE_DROP_DOWN:
                DropDown dropDown = (DropDown) List_ControlClassObjects.get(temp_controlObj.getControlName());
                            /*if (dropDown != null) {
                                if (controlValue.get("Value_id").startsWith("0")) {
//                                dropDown.setItemId(controlValue.get("Value_id").substring(1),controlValue.get("Value"));
                                    dropDown.setItemId(controlValue.get("Value_id"), controlValue.get("Value"));
                                } else {
                                    dropDown.setItemId(controlValue.get("Value_id"), controlValue.get("Value"));
                                }
                            }*/
                boolean dropfoundflag = true;
                List<Item> AppendValues_list = new ArrayList<>();


                if (ControlID.equalsIgnoreCase(temp_controlObj.getControlName())) {
//                                List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                    List<String> Values = new ArrayList<>();
                    if (API_OutputParam_Bean.getOutParam_Mapped_Expression() == null || API_OutputParam_Bean.getOutParam_Mapped_Expression().length() == 0) {
                        if (MappedControlID.trim().length() > 0) {
                            Values = OutputData.get(MappedControlID.toLowerCase());
                        } else if (API_OutputParam_Bean.getList_OutParam_Languages() != null && API_OutputParam_Bean.getList_OutParam_Languages().size() > 0) {
                            String appLanguage = ImproveHelper.getLocale(context);
                            List<LanguageMapping> languageMappings = API_OutputParam_Bean.getList_OutParam_Languages();
                            for (int j = 0; j < languageMappings.size(); j++) {
                                LanguageMapping languageMapping = languageMappings.get(j);
                                if (languageMapping.getOutParam_Lang_Name().equalsIgnoreCase(appLanguage)) {
                                    Values = OutputData.get(languageMapping.getOutParam_Lang_Mapped().toLowerCase());
                                }
                            }
                        }
                    } else {
                        MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_Expression().trim();
                        ExpressionMainHelper ehelper = new ExpressionMainHelper();
                        Value = ehelper.ExpressionHelper(context, API_OutputParam_Bean.getOutParam_Mapped_Expression().trim());
                        String[] valarr = Value.split("\\,");
                        Values.addAll(Arrays.asList(valarr));
                    }

                    for (int j = 0; j < Values.size(); j++) {
                        Item item = new Item();
                        item.setValue(Values.get(j).trim());
                        item.setId(Values.get(j).trim());
                        AppendValues_list.add(item);
                    }
//                                }
                } else if (ControlID.equalsIgnoreCase(temp_controlObj.getControlName() + "_ID")) {
                    AppendValues_list = dropDown.getnewItemsListDynamically();
                    if (AppendValues_list.size() > 0) {
//                                    List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                        List<String> Values = new ArrayList<>();

                        if (API_OutputParam_Bean.getOutParam_Mapped_Expression() == null || API_OutputParam_Bean.getOutParam_Mapped_Expression().length() == 0) {
                            if (MappedControlID.trim().length() > 0) {
                                Values = OutputData.get(MappedControlID.toLowerCase());
                            }
                        } else {
                            MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_Expression().trim();
                            ExpressionMainHelper ehelper = new ExpressionMainHelper();
                            Value = ehelper.ExpressionHelper(context, API_OutputParam_Bean.getOutParam_Mapped_Expression().trim());
                            String[] valarr = Value.split("\\,");
                            Values.addAll(Arrays.asList(valarr));
                        }


                        for (int j = 0; j < AppendValues_list.size(); j++) {
                            Item item = AppendValues_list.get(j);
                            item.setId(Values.get(j).trim());
                            AppendValues_list.set(j, item);
                        }
                    } else {
                        List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                        for (int j = 0; j < Values.size(); j++) {
                            Item item = new Item();
                            item.setValue(Values.get(j).trim());
                            item.setId(Values.get(j).trim());
                            AppendValues_list.add(item);
                        }
                    }
                }

                if (dropfoundflag) {
                    dropDown.setnewItemsListDynamically(AppendValues_list);
                }
                break;

            case CONTROL_TYPE_CHECK_LIST:
                CheckList checklist = (CheckList) List_ControlClassObjects.get(temp_controlObj.getControlName());
                boolean CheckListflag = true;

                List<Item> AppendValues_check_list = new ArrayList<>();


                if (ControlID.equalsIgnoreCase(temp_controlObj.getControlName())) {
//                                List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                    List<String> Values = new ArrayList<>();
                    if (API_OutputParam_Bean.getOutParam_Mapped_Expression() == null || API_OutputParam_Bean.getOutParam_Mapped_Expression().length() == 0) {
                        if (MappedControlID.trim().length() > 0) {
                            Values = OutputData.get(MappedControlID.toLowerCase());
                        } else if (API_OutputParam_Bean.getList_OutParam_Languages() != null && API_OutputParam_Bean.getList_OutParam_Languages().size() > 0) {
                            String appLanguage = ImproveHelper.getLocale(context);
                            List<LanguageMapping> languageMappings = API_OutputParam_Bean.getList_OutParam_Languages();
                            for (int j = 0; j < languageMappings.size(); j++) {
                                LanguageMapping languageMapping = languageMappings.get(j);
                                if (languageMapping.getOutParam_Lang_Name().equalsIgnoreCase(appLanguage)) {
                                    Values = OutputData.get(languageMapping.getOutParam_Lang_Mapped().toLowerCase());
                                }
                            }
                        }
                    } else {
                        MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_Expression().trim();
                        ExpressionMainHelper ehelper = new ExpressionMainHelper();
                        Value = ehelper.ExpressionHelper(context, API_OutputParam_Bean.getOutParam_Mapped_Expression().trim());
                        String[] valarr = Value.split("\\,");
                        Values.addAll(Arrays.asList(valarr));
                    }

                    for (int j = 0; j < Values.size(); j++) {
                        Item item = new Item();
                        item.setValue(Values.get(j).trim());
                        item.setId(Values.get(j).trim());
                        AppendValues_check_list.add(item);
                    }
//                                }
                } else if (ControlID.equalsIgnoreCase(temp_controlObj.getControlName() + "_ID")) {
                    AppendValues_check_list = checklist.getListOfSelectedItems();
//                                AppendValues_check_list = checklist.getnewItemsListDynamically();
                    if (AppendValues_check_list.size() > 0) {
//                                    List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                        List<String> Values = new ArrayList<>();

                        if (API_OutputParam_Bean.getOutParam_Mapped_Expression() == null || API_OutputParam_Bean.getOutParam_Mapped_Expression().length() == 0) {
                            if (MappedControlID.trim().length() > 0) {
                                Values = OutputData.get(MappedControlID.toLowerCase());
                            }
                        } else {
                            MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_Expression().trim();
                            ExpressionMainHelper ehelper = new ExpressionMainHelper();
                            Value = ehelper.ExpressionHelper(context, API_OutputParam_Bean.getOutParam_Mapped_Expression().trim());
                            String[] valarr = Value.split("\\,");
                            Values.addAll(Arrays.asList(valarr));
                        }


                        for (int j = 0; j < AppendValues_check_list.size(); j++) {
                            Item item = AppendValues_check_list.get(j);
                            item.setId(Values.get(j).trim());
                            AppendValues_check_list.set(j, item);
                        }
                    } else {
                        List<String> Values = OutputData.get(MappedControlID.toLowerCase());
                        for (int j = 0; j < Values.size(); j++) {
                            Item item = new Item();
                            item.setValue(Values.get(j).trim());
                            item.setId(Values.get(j).trim());
                            AppendValues_check_list.add(item);
                        }
                    }
                }

                if (CheckListflag) {
                    checklist.setnewItemsListDynamically(AppendValues_check_list);
                }

                break;
            case CONTROL_TYPE_SIGNATURE:
                SignatureView signatureView = (SignatureView) List_ControlClassObjects.get(temp_controlObj.getControlName());
                signatureView.setSignatureForEdit(Value);
                break;
            case CONTROL_TYPE_URL_LINK:
                UrlView urlView = (UrlView) List_ControlClassObjects.get(temp_controlObj.getControlName());
                urlView.setText(Value);

                break;
            case CONTROL_TYPE_DECIMAL:
                DecimalView Decimalview = (DecimalView) List_ControlClassObjects.get(temp_controlObj.getControlName());
                Decimalview.getCustomEditText().setText(Value);
                CustomTextView tv_DectapTextType = Decimalview.gettap_text();
                tv_DectapTextType.setVisibility(View.GONE);
                Decimalview.getCustomEditText().setVisibility(View.VISIBLE);
                break;
            case CONTROL_TYPE_PASSWORD:
                Password Passwordview = (Password) List_ControlClassObjects.get(temp_controlObj.getControlName());
                Passwordview.getCustomEditText().setText(Value);
                Passwordview.getTv_tapTextType().setVisibility(View.GONE);
                LinearLayout ll_tap_text = Passwordview.gettap_text();
                ll_tap_text.setVisibility(View.VISIBLE);
                Passwordview.getTil_password().setVisibility(View.VISIBLE);
//                            Passwordview.getCustomEditText().setVisibility(View.VISIBLE);

                break;
            case CONTROL_TYPE_CURRENCY:
                Currency Currencyview = (Currency) List_ControlClassObjects.get(temp_controlObj.getControlName());
                Currencyview.getCustomEditText().setText(Value);
                CustomTextView tv_CurtapTextType = Currencyview.gettap_text();
                tv_CurtapTextType.setVisibility(View.GONE);
                Currencyview.getCustomEditText().setVisibility(View.VISIBLE);
                break;
            case CONTROL_TYPE_RATING:
                Rating ratingView = (Rating) List_ControlClassObjects.get(temp_controlObj.getControlName());
                ratingView.setRating(Value);

                break;
            case CONTROL_TYPE_DYNAMIC_LABEL:
                DynamicLabel DynamicLabeliew = (DynamicLabel) List_ControlClassObjects.get(temp_controlObj.getControlName());
                CustomTextView tv_dynamicLabel = DynamicLabeliew.getValueView();
//                            tv_dynamicLabel.setText(Value);
                DynamicLabeliew.setValue(Value);
                break;
            case CONTROL_TYPE_QR_CODE:
                QRCode qrCode = (QRCode) List_ControlClassObjects.get(temp_controlObj.getControlName());
                qrCode.createQrCodeDynamically(Value);
                break;
            case CONTROL_TYPE_BAR_CODE:
                BarCode barCode = (BarCode) List_ControlClassObjects.get(temp_controlObj.getControlName());
                barCode.createBarCodeDynamically(Value);
                break;
            case CONTROL_TYPE_DATA_CONTROL:
                //no code
                DataControl dataControl = (DataControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                           /* if (!controlValue.get("Value_id").contentEquals("")) {
                                SearchableSpinner searchableSpinner = dataControl.getSpinner();
                                searchableSpinner.setItemID(controlValue.get("Value_id"));
                            }*/

                break;
            case CONTROL_TYPE_GPS:
                //no code
                Gps_Control gps_control = (Gps_Control) List_ControlClassObjects.get(temp_controlObj.getControlName());
/* if (!controlValue.get("Coordinates").trim().contentEquals("")) {
                        String coordinates = controlValue.get("Coordinates");
                        String type = controlValue.get("Type");
                        List<String> Points = ImproveHelper.getGpsCoordinates(coordinates);
                        gps_control.setMapPointsDynamically(type, Points, null);
                    }*/
                //                            if (!Value.contains("File not found")) {
//                                final Handler handler = new Handler();
//                                handler.postDelayed(new Runnable() {
//                                    @RequiresApi(api = Build.VERSION_CODES.M)
//                                    @Override
//                                    public void run() {
//                                        //Do something after delay
///*
//                                        if (!controlValue.get("Coordinates").trim().contentEquals("")) {
//                                            String coordinates = controlValue.get("Coordinates");
//                                            String type = controlValue.get("Type");
//                                            List<String> Points = ImproveHelper.getGpsCoordinatesFromAction(coordinates);
//                                            gps_control.setMapPointsDynamically(type, Points, null);
//                                        }
//*/
//                                    }
//                                }, 1000);
//
//
//
//                            }
                break;
            case CONTROL_TYPE_TIME:
                Time time_control = (Time) List_ControlClassObjects.get(temp_controlObj.getControlName());
                if (Value != null) {
                    Value = Value.split("\\,")[0];
                    if (!Value.contentEquals("")) {
                        time_control.setTimeData(Value);
                    }
                }
                break;
            case CONTROL_TYPE_VOICE_RECORDING:
                VoiceRecording voiceRecording = (VoiceRecording) List_ControlClassObjects.get(temp_controlObj.getControlName());
                Value = Value.split("\\,")[0];
                if ((!Value.contentEquals("")) || (!Value.contentEquals(" "))) {
                    if (!Value.contains("File not found")) {
                        voiceRecording.setUploadedFile(Value);
                    }
                } else {
                    voiceRecording.setUploadedFile("");
                }
                break;

            case CONTROL_TYPE_AUTO_COMPLETION:
                AutoCompletionControl autoCompletionControl = (AutoCompletionControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                List<String> MappedValues = OutputData.get(MappedControlID.toLowerCase());
                autoCompletionControl.setList_ControlItems(MappedValues);

                break;
            case CONTROL_TYPE_VIEWFILE:
                ViewFileControl viewFileControl = (ViewFileControl) List_ControlClassObjects.get(temp_controlObj.getControlName());
                viewFileControl.setFileLink(Value);
                break;
            case CONTROL_TYPE_VIDEO_RECORDING:
                VideoRecording videoRecording = (VideoRecording) List_ControlClassObjects.get(temp_controlObj.getControlName());
                Value = Value.split("\\,")[0];
                if (!Value.contains("File not found")) {
                    videoRecording.setVideoRecordingPath(Value);
                    videoRecording.displayVideoPreview();
                }
                break;
            case CONTROL_TYPE_USER:
                //no code
                          /*  if (!controlValue.get("Value_id").contentEquals("")) {
                                String Value_id = controlValue.get("Value_id");
                                String Value = controlValue.get("Value");
                                UserControl userControl = (UserControl) viewObj.get(controlName);
                                userControl.setEditValues(Value, Value_id);
                            }*/
                break;
            case CONTROL_TYPE_POST:
                //no code
                           /* if (!controlValue.get("Value_id").contentEquals("")) {
                                String Value_id = controlValue.get("Value_id");
                                String Value = controlValue.get("Value");
                                PostControl postControl = (PostControl) viewObj.get(controlName);
                                postControl.setEditValues(Value, Value_id);
                            }*/
                break;


        }
    }


    public static void setMultiValueToControlFromCallAPIORGetData(Context context, int pos,
                                                                  LinkedHashMap<String, List<String>> OutputData, List<API_OutputParam_Bean> List_API_OutParams, List<ControlObject> ControlObj,
                                                                  LinkedHashMap<String, Object> List_ControlClassObjects,
                                                                  List<MapControl> subFormMapControls, List<List<String>> subFormMappedValues) {
        try {

            for (int a = 0; a < List_API_OutParams.size(); a++) {
                if (!List_API_OutParams.get(a).isOutParam_Delete()) {

                    API_OutputParam_Bean API_OutputParam_Bean = List_API_OutParams.get(a);

                    String Value = "", ControlID = "";
                    String MappedControlID = API_OutputParam_Bean.getOutParam_Mapped_ID().trim();
                    ControlID = API_OutputParam_Bean.getOutParam_Name();

                    if (MappedControlID.trim().length() > 0) {
                        List<String> MappedValues=new ArrayList<>();
                        if(OutputData.size()>0){
                            MappedValues = OutputData.get(MappedControlID.toLowerCase());
                        }
                        if (MappedValues.size() > pos) {
                            Value = MappedValues.get(pos);
                        }
                    } else if (API_OutputParam_Bean.getOutParam_Mapped_Expression() != null && API_OutputParam_Bean.getOutParam_Mapped_Expression().trim().length() > 0) {
                        AppConstants.SF_Container_position = pos;
                        ExpressionMainHelper ehelper = new ExpressionMainHelper();
                        Value = ehelper.ExpressionHelper(context, API_OutputParam_Bean.getOutParam_Mapped_Expression().trim());
                    }
                    setSingleOrMultiValueToControlWithOutCtrl(context, List_ControlClassObjects, Value, OutputData,
                            ControlID, MappedControlID, API_OutputParam_Bean);
                }
            }
        } catch (Exception e) {
            ImproveHelper.improveException(context, "SetValueToMultiControls", "SetValuetoMultiControlInCallAPIFormUsedbyControlObject", e);
        }
    }

    public static HashMap<String, String> jsonStringConvertToHashMap(String jsonString) {
        HashMap<String, String> myHashMap = new HashMap<String, String>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String strId = jsonObject.getString("Value_id");
            String strValue = jsonObject.getString("Value");
            myHashMap.put("Value_id", strId);
            myHashMap.put("Value", strValue);


//            JSONArray jArray = new JSONArray(jsonString);
//            JSONObject jObject = null;
//            String keyString=null;
//            for (int i = 0; i < jArray.length(); i++) {
//                jObject = jArray.getJSONObject(i);
//                // beacuse you have only one key-value pair in each object so I have used index 0
//                keyString = (String)jObject.names().get(0);
//                myHashMap.put(keyString, jObject.getString(keyString));
//            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return myHashMap;
    }


}
