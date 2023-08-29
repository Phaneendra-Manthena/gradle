package com.bhargo.user.utils;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUDIO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_COMPLETION;
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
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_NUMERIC_INPUT;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PASSWORD;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PERCENTAGE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_PHONE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_POST;
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

import android.content.Context;
import android.util.Log;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.controls.advanced.Gps_Control;
import com.bhargo.user.controls.advanced.GridControl;
import com.bhargo.user.controls.advanced.PostControl;
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
import com.bhargo.user.controls.standard.Email;
import com.bhargo.user.controls.standard.FileBrowsing;
import com.bhargo.user.controls.standard.LargeInput;
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
import com.bhargo.user.controls.standard.VoiceRecording;
import com.bhargo.user.custom.CustomEditText;
import com.bhargo.user.pojos.ControlData;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SaveControlValues {

    public String sectionControlName;
    public List<ControlObject> sectionControlObjectList;
    public LinkedHashMap<String, Object> List_ControlClassObjects;
    private final Gson gson;
    Context context;

    public SaveControlValues(Context context,String sectionControlName, List<ControlObject> sectionControlObjectList, LinkedHashMap<String, Object> List_ControlClassObjects) {
        this.context = context;
        this.sectionControlName = sectionControlName;
        this.sectionControlObjectList = sectionControlObjectList;
        this.List_ControlClassObjects = List_ControlClassObjects;
        gson = new Gson();
    }

   /* private HashMap<String, HashMap<String, Object>> convertJsonToObjForSectionPopup(JSONObject jsonObjChildForm) throws JSONException {
        HashMap<String, HashMap<String, Object>> sectionPopData = new HashMap<>();
        HashMap<String, Object> popData = new HashMap<>();
        for (int i = 0; i < sectionControlObjectList.size(); i++) {
            ControlObject controlObject = sectionControlObjectList.get(i);
            String controlType = controlObject.getControlType();
            String controlName = controlObject.getControlName();

            switch (controlType) {
                case CONTROL_TYPE_SUBFORM:
                    String subFormControlName = controlName;
                    JSONArray subForms=jsonObjChildForm.getJSONArray("SubForm");
                    for (int sf = 0; sf < subForms.length(); sf++) {
                        if(subForms.getJSONObject(sf).has(subFormControlName)){
                            JSONArray subFormObjA=subForms.getJSONObject(sf).getJSONArray(subFormControlName);
                            List<HashMap<String,Object>> lRowData=new ArrayList<>();
                            for (int obj = 0; obj < subFormObjA.length(); obj++) {
                                lRowData.add(new HashMap<>());
                            }
                            editDataObj=lRowData;
                            break;
                        }
                    }
                    List<ControlObject> subFormControlList = subformView.controlObject.getSubFormControlList();
                    List<LinkedHashMap<String, Object>> List_ControlClassObjects_sub = subformView.subform_List_ControlClassObjects;
                    List<HashMap<String, Object>> lSubFormRowData = new ArrayList<>();
                    for (int totRows = 0; totRows < List_ControlClassObjects_sub.size(); totRows++) {
                        LinkedHashMap<String, Object> subFormRowData = List_ControlClassObjects_sub.get(totRows);
                        HashMap<String, Object> rowData = new HashMap<>();
                        for (int j = 0; j < subFormControlList.size(); j++) {
                            ControlData controlData = getControlData(subFormControlList.get(j).getControlType(), subFormControlList.get(j).getControlName(), subFormRowData);
                            rowData.put(subFormControlList.get(j).getControlName(), controlData);
                        }
                        lSubFormRowData.add(rowData);
                    }
                    popData.put(subFormControlName, lSubFormRowData);
                    //key:subFormControlName=Value: List of RowData(HashMap<String, Object>) : RowData Key:controlNameinsubform Value:ControlData
                    break;
                case CONTROL_TYPE_GRID_CONTROL:
                    String gridFormControlName = strControlName;
                    GridControl gridControl = (GridControl) List_ControlClassObjects.get(gridFormControlName);
                    List<ControlObject> gridFormControlList = gridControl.controlObject.getSubFormControlList();
                    List<LinkedHashMap<String, Object>> lrow_gridforms = gridControl.gridControl_List_ControlClassObjects;
                    List<HashMap<String, Object>> lGridFormRowData = new ArrayList<>();
                    for (int totRows = 0; totRows < lrow_gridforms.size(); totRows++) {
                        LinkedHashMap<String, Object> gridFormRowData = lrow_gridforms.get(totRows);
                        HashMap<String, Object> rowData = new HashMap<>();
                        for (int j = 0; j < gridFormControlList.size(); j++) {
                            ControlData controlData = getControlData(gridFormControlList.get(j).getControlType(), gridFormControlList.get(j).getControlName(),
                                    gridFormRowData);
                            rowData.put(gridFormControlList.get(j).getControlName(), controlData);
                        }
                        lGridFormRowData.add(rowData);
                    }
                    popData.put(gridFormControlName, lGridFormRowData);
                    //key:subFormControlName=Value: List of RowData(HashMap<String, Object>) : RowData Key:controlNameinsubform Value:ControlData
                    break;
                default://MainForm Control
                    ControlData controlData = getControlData(strControlType, strControlName, List_ControlClassObjects);
                    popData.put(strControlName, controlData);
                    //Key:mainformcontrolName=Value:ControlData
            }
        }
        sectionPopData.put(sectionControlName, popData);
        //Key:SectionPopName=Value:FormData with HashMap<String, Object>

        return sectionPopData;
    }*/

    public HashMap<String, HashMap<String, Object>> sectionInPopUpSaveValues() {
        HashMap<String, HashMap<String, Object>> sectionPopData = new HashMap<>();
        HashMap<String, Object> popData = new HashMap<>();
        for (int i = 0; i < sectionControlObjectList.size(); i++) {
            String strControlType = sectionControlObjectList.get(i).getControlType();
            String strControlName = sectionControlObjectList.get(i).getControlName();

            switch (strControlType) {
                case CONTROL_TYPE_SUBFORM:
                    String subFormControlName = strControlName;
                    SubformView subformView = (SubformView) List_ControlClassObjects.get(subFormControlName);
                    List<ControlObject> subFormControlList = subformView.controlObject.getSubFormControlList();
                    List<LinkedHashMap<String, Object>> List_ControlClassObjects_sub = subformView.subform_List_ControlClassObjects;
                    List<HashMap<String, Object>> lSubFormRowData = new ArrayList<>();
                    for (int totRows = 0; totRows < List_ControlClassObjects_sub.size(); totRows++) {
                        LinkedHashMap<String, Object> subFormRowData = List_ControlClassObjects_sub.get(totRows);
                        HashMap<String, Object> rowData = new HashMap<>();
                        for (int j = 0; j < subFormControlList.size(); j++) {
                            ControlData controlData = getControlData(subFormControlList.get(j).getControlType(), subFormControlList.get(j).getControlName(), subFormRowData);
                            rowData.put(subFormControlList.get(j).getControlName(), controlData);
                        }
                        lSubFormRowData.add(rowData);
                    }
                    popData.put(subFormControlName, lSubFormRowData);
                    //key:subFormControlName=Value: List of RowData(HashMap<String, Object>) : RowData Key:controlNameinsubform Value:ControlData
                    break;
                case CONTROL_TYPE_GRID_CONTROL:
                    String gridFormControlName = strControlName;
                    GridControl gridControl = (GridControl) List_ControlClassObjects.get(gridFormControlName);
                    List<ControlObject> gridFormControlList = gridControl.controlObject.getSubFormControlList();
                    List<LinkedHashMap<String, Object>> lrow_gridforms = gridControl.gridControl_List_ControlClassObjects;
                    List<HashMap<String, Object>> lGridFormRowData = new ArrayList<>();
                    for (int totRows = 0; totRows < lrow_gridforms.size(); totRows++) {
                        LinkedHashMap<String, Object> gridFormRowData = lrow_gridforms.get(totRows);
                        HashMap<String, Object> rowData = new HashMap<>();
                        for (int j = 0; j < gridFormControlList.size(); j++) {
                            ControlData controlData = getControlData(gridFormControlList.get(j).getControlType(), gridFormControlList.get(j).getControlName(),
                                    gridFormRowData);
                            rowData.put(gridFormControlList.get(j).getControlName(), controlData);
                        }
                        lGridFormRowData.add(rowData);
                    }
                    popData.put(gridFormControlName, lGridFormRowData);
                    //key:subFormControlName=Value: List of RowData(HashMap<String, Object>) : RowData Key:controlNameinsubform Value:ControlData
                    break;
                default://MainForm Control
                    ControlData controlData = getControlData(strControlType, strControlName, List_ControlClassObjects);
                    popData.put(strControlName, controlData);
                    //Key:mainformcontrolName=Value:ControlData
            }
        }
        sectionPopData.put(sectionControlName, popData);
        //Key:SectionPopName=Value:FormData with HashMap<String, Object>

        return sectionPopData;
    }

    public ControlData  getControlData(String strControlType, String strControlName, LinkedHashMap<String, Object> list_ControlClassObjects) {
        ControlData controlData = new ControlData();
        try {
            String value = "";
            switch (strControlType) {
                case CONTROL_TYPE_TEXT_INPUT:
                    TextInput textInput = (TextInput) list_ControlClassObjects.get(strControlName);
                    value = textInput.getTextInputValue();
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_NUMERIC_INPUT:
                    NumericInput numericInput = (NumericInput) list_ControlClassObjects.get(strControlName);
                    value = numericInput.getNumericValue();
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_PHONE:
                    Phone phone = (Phone) list_ControlClassObjects.get(strControlName);
                    value = phone.getPhoneValue();
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_EMAIL:
                    Email email = (Email) list_ControlClassObjects.get(strControlName);
                    value = email.getEmailValue();
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_LARGE_INPUT:
                    LargeInput largeInput = (LargeInput) list_ControlClassObjects.get(strControlName);

                    if (largeInput.isEditorModeEnabled()) {
                        value = largeInput.getTextEditor().getHtml();
                    } else if (largeInput.isHTMLViewerEnabled()) {
                        value = largeInput.getTextEditor().getHtml();
                    } else {
                        value = largeInput.getTextValue();
                    }
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_CAMERA:
                    Camera camera = (Camera) list_ControlClassObjects.get(strControlName);
                    if (camera.getFileNameTextView() != null && camera.getFileNameTextView().getTag() != null) {
                        String strFileName = !camera.getFileNameTextView().getText().toString().equalsIgnoreCase("") ?
                                camera.getFileNameTextView().getText().toString() : "";
                        String strBitMapURI = !camera.getFileNameTextView().getTag().toString().equalsIgnoreCase("") ?
                                camera.getFileNameTextView().getTag().toString() : "";
                        if (!strBitMapURI.isEmpty()) {
                            value = strFileName + "," + strBitMapURI;
                        }
                    }
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);

                    break;
                case CONTROL_TYPE_FILE_BROWSING:
                    FileBrowsing fileBrowsing = (FileBrowsing) list_ControlClassObjects.get(strControlName);
                    if (fileBrowsing.getControlRealPath().getTag() != null && !fileBrowsing.getControlRealPath().getTag().toString().isEmpty()) {
                        value = fileBrowsing.getControlRealPath().getTag().toString();
                        fileBrowsing.setPath(value);
                    }
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_CALENDER:
                    Calendar calendar = (Calendar) list_ControlClassObjects.get(strControlName);
                    value = calendar.getCalendarValue();
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_CHECKBOX:
                    Checkbox checkbox = (Checkbox) list_ControlClassObjects.get(strControlName);
//                    value = checkbox.getSelectedNameCheckboxString();
                    String strSelectedIds = checkbox.getSelectedId();
                    String strSelectedString = checkbox.getSelectedNameCheckboxString();
                    HashMap<String, String> controlValue =  new HashMap<>();
                    controlValue.put("Value_id",strSelectedIds);
                    controlValue.put("Value",strSelectedString);
                    value = new Gson().toJson(controlValue);
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_RADIO_BUTTON:
                    RadioGroupView radioGroupView = (RadioGroupView) list_ControlClassObjects.get(strControlName);
                    value = radioGroupView.getSelectedRadioButtonID();
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_DROP_DOWN:
                    DropDown dropDown = (DropDown) list_ControlClassObjects.get(strControlName);
                    value = dropDown.getSelectedDropDownString();
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_CHECK_LIST:
                    CheckList checkList = (CheckList) list_ControlClassObjects.get(strControlName);
                    value = checkList.getSelectedIDCheckListString();
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_RATING:
                    Rating rating = (Rating) list_ControlClassObjects.get(strControlName);
                    value = rating.getTotalRating();
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_VOICE_RECORDING:
                    VoiceRecording voiceRecording = (VoiceRecording) list_ControlClassObjects.get(strControlName);
                    if (voiceRecording.getTv_displayName().getTag() != null &&
                            !voiceRecording.getTv_displayName().getTag().toString().isEmpty()) {
                        value = voiceRecording.getTv_displayName().getTag().toString();
                    }
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_VIDEO_RECORDING:
                    VideoRecording videoRecording = (VideoRecording) list_ControlClassObjects.get(strControlName);
/*
                    if (videoRecording.getControlRealPath() != null && !videoRecording.getControlRealPath().toString().isEmpty()) {
                        if(ImproveHelper.validateUri(context,videoRecording.getControlRealPath().toString())) {
                            value = videoRecording.getFromCameraOrGalleyURI().toString() + "^" + videoRecording.getFromCameraOrGalley();
                        }else{
                            value = videoRecording.getControlRealPath().getTag().toString() + "^" + videoRecording.getFromCameraOrGalley();
                        }
                    }
*/
                    if (videoRecording.getFromCameraOrGalleyURI() != null && !videoRecording.getFromCameraOrGalleyURI().toString().isEmpty()) {
                        value = videoRecording.getFromCameraOrGalleyURI().toString() + "^" + videoRecording.getFromCameraOrGalley();
                        videoRecording.setPath(videoRecording.getFromCameraOrGalleyURI().toString());
                    }
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_AUDIO_PLAYER:
                    // Pending
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_VIDEO_PLAYER:
                    // Pending
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_PERCENTAGE:
                    Percentage percentage = (Percentage) list_ControlClassObjects.get(strControlName);
                    value = percentage.getPercentageValue();
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_SIGNATURE:
                    SignatureView signatureView = (SignatureView) list_ControlClassObjects.get(strControlName);
                    if (signatureView.getControlRealPath().getTag() != null) {
                        value = signatureView.getControlRealPath().getTag().toString();
                        signatureView.setPath(value);
                    }
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_URL_LINK:
                    UrlView urlView = (UrlView) list_ControlClassObjects.get(strControlName);
                    value = urlView.getURLViewStringValue();
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_DECIMAL:
                    DecimalView decimalView = (DecimalView) list_ControlClassObjects.get(strControlName);
                    value = decimalView.getDecimalValue();
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_PASSWORD:
                    Password password = (Password) list_ControlClassObjects.get(strControlName);
                    value = password.getTextValue();
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_CURRENCY:
                    Currency currency = (Currency) list_ControlClassObjects.get(strControlName);
                    value = currency.getTextValue();
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_DYNAMIC_LABEL:
                    break;
                case CONTROL_TYPE_IMAGE:
//                Image image = (Image) list_ControlClassObjects.get(strControlName);
//                controlData.setControlValue(value);
//                controlData.setControlName(strControlName);
//                controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_TIME:
                    Time time = (Time) list_ControlClassObjects.get(strControlName);
                    value = time.getEditTextTimeValue();
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_COUNT_DOWN_TIMER:
                    break;
                case CONTROL_TYPE_COUNT_UP_TIMER:
                    break;
                case CONTROL_TYPE_VIEWFILE:
                    break;
                case CONTROL_TYPE_GPS:
                    Gps_Control controlGPS = (Gps_Control) list_ControlClassObjects.get(strControlName);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    if (controlGPS.getLatLngList() != null && controlGPS.getLatLngList().size() > 0) {
                        String GPSSTR = "";
                        List<LatLng> latLngList = controlGPS.getLatLngList();
                        for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                            LatLng latlang = latLngList.get(i);
                            GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                        }
                        GPSSTR = GPSSTR.substring(GPSSTR.indexOf("^") + 1);
                        controlData.setGpsCoordinates(GPSSTR);
                        controlData.setGpsType(controlGPS.getGPS_Type());
                    }
                    break;
                case CONTROL_TYPE_POST:
                    PostControl postControl = (PostControl) list_ControlClassObjects.get(strControlName);
                    if (postControl.getSelectedPostId() != null && !postControl.getSelectedPostId().isEmpty()) {
                        value = postControl.getSelectedPostName() + "^" + postControl.getSelectedPostId();
                    }
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);

                    break;
                case CONTROL_TYPE_USER:
                    UserControl userControl = (UserControl) list_ControlClassObjects.get(strControlName);
                    if (userControl.getSelectedUserId() != null && !userControl.getSelectedUserId().isEmpty()) {
                        value = userControl.getSelectedUserName() + "^" + userControl.getSelectedUserId();
                    }
                    controlData.setControlValue(value);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    break;
                case CONTROL_TYPE_AUTO_COMPLETION:
                    break;
                case CONTROL_TYPE_DATA_CONTROL:
                    DataControl dataControl = (DataControl) list_ControlClassObjects.get(strControlName);
                    controlData.setControlName(strControlName);
                    controlData.setControlType(strControlType);
                    if (dataControl.dropdownItemIsSelected()) {
                        // value = dataControl.getSelectedDropDownItemId() + "," + dataControl.getSelectedDropDownItemName();
                        controlData.setControlValue(dataControl.getSelectedDropDownItemId());
                    }
                    break;

            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        return controlData;
    }
}
