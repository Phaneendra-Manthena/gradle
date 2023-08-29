package com.bhargo.user.utils;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUDIO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_COMPLETION;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_GENERATION;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_BAR_CODE;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_BUTTON;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_CALENDAR_EVENT;
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
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.bhargo.user.Java_Beans.AllControls;
import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.R;
import com.bhargo.user.controls.advanced.AutoCompletionControl;
import com.bhargo.user.controls.advanced.BarCode;
import com.bhargo.user.controls.advanced.Gps_Control;
import com.bhargo.user.controls.advanced.GridControl;
import com.bhargo.user.controls.advanced.PostControl;
import com.bhargo.user.controls.advanced.QRCode;
import com.bhargo.user.controls.advanced.SubformView;
import com.bhargo.user.controls.advanced.UserControl;
import com.bhargo.user.controls.data_controls.DataControl;
import com.bhargo.user.controls.standard.AudioPlayer;
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
import com.bhargo.user.custom.CustomTextView;
import com.google.android.gms.maps.model.LatLng;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DataCollectionControlsValidation {

    //=======Load_Onchange_EventObjects================

    private static final String TAG = "ControlsValidation";
    String emailPatternMain = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
    public List<String> subFormMainAppendString = new ArrayList<>();
    Context context;
    LinkedHashMap<String, Object> List_ControlClassObjects;
    AllControls allControls;
    List<String> stringListSubmit = new ArrayList<>();
    List<HashMap<String, String>> stringListFiles = new ArrayList<>();
    List<String> list_TableColums;
    List<String> mandatoryColumns;

    String tableSettingsType;


    public DataCollectionControlsValidation(Context context, LinkedHashMap<String, Object> List_ControlClassObjects, List<String> list_TableColums, String tableSettingsType, List<String> mandatoryColumns) {
        this.context = context;
        this.tableSettingsType = tableSettingsType;
        this.List_ControlClassObjects = List_ControlClassObjects;
        this.list_TableColums = list_TableColums;
        this.mandatoryColumns = mandatoryColumns;

    }


    /*@RequiresApi(api = Build.VERSION_CODES.O)*/
    public boolean controlSubmitValidation(ControlObject loadControlObject, boolean isSubForm, List<String> sfString, List<HashMap<String, String>> sfFilesString, String subFormId, int subFormRowId, int controlPos) {

        boolean validationFlag = true;
        try {
            Log.d(TAG, "XmlHelperControlObject: " + loadControlObject.getControlType());

            if (List_ControlClassObjects.get(loadControlObject.getControlName()) == null && !loadControlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_AUTO_GENERATION)) {

                if (loadControlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_SUBFORM) || loadControlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_GRID_CONTROL)) {

                } else if (loadControlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_CHECKBOX) ||
                        loadControlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_RADIO_BUTTON) ||
                        loadControlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_DROP_DOWN) ||
                        loadControlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_CHECK_LIST) ||
                        loadControlObject.getControlType().equalsIgnoreCase(CONTROL_TYPE_DATA_CONTROL)) {
                    if (isSubForm) {
                        sfString.add(loadControlObject.getControlName() + "|" + "");
                        sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                    } else {
                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                        stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                    }
                } else {
                    if (isSubForm ) {
                        sfString.add(loadControlObject.getControlName() + "|" + "");
                    } else {
                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                    }
                }

            } else {
                boolean isControlVisible = !loadControlObject.isInvisible();
                boolean isItemSelected = loadControlObject.isItemSelected();
                String strValueFromCtrl = loadControlObject.getText();
                String strValueIdFromCtrl = loadControlObject.getControlValue();

                switch (loadControlObject.getControlType()) {

                    case CONTROL_TYPE_TEXT_INPUT:
                        TextInput textInput = (TextInput) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if (textInput == null) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                        } else {
                            String strTextInputValue = null;
                            String strMinChars = null;
                            if (textInput != null) {
                                if(isSubForm){
                                    isControlVisible  = !textInput.getControlObject().isInvisible();
                                    strValueFromCtrl  = textInput.getControlObject().getText();
                                    if(textInput.getControlObject().isEnableMinCharacters()){
                                        strMinChars  = textInput.getControlObject().getMinCharacters();
                                    }
                                }else{
                                    if(loadControlObject.isEnableMinCharacters()) {
                                        strMinChars = loadControlObject.getMinCharacters();
                                    }
                                }
                                //if (textInput.getTextInputView().getVisibility() == View.VISIBLE) {
//                            if (textInput.visable == View.VISIBLE) {
                                if (isControlVisible) {
//                if (!loadControlObject.isInvisible()) {
                                    if (loadControlObject.isNullAllowed()) {
                                        if(loadControlObject.isGoogleLocationSearch() && (strValueFromCtrl == null || strValueFromCtrl.isEmpty())){
                                            ImproveHelper.showToastAlert(context,loadControlObject.getMandatoryFieldError());
                                            textInput.setAlertTextInput().setVisibility(View.VISIBLE);
                                            textInput.getLl_googlePlacesSearch().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            textInput.setAlertTextInput().setText(loadControlObject.getMandatoryFieldError());
                                            textInput.setAlertTextInput().setTextColor(context.getColor(R.color.delete_icon));
                                            ImproveHelper.setFocus(textInput.getTextInputView());
                                            validationFlag = false;
                                        } else if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                            strTextInputValue = strValueFromCtrl;
                                            if (strTextInputValue.contentEquals(context.getResources().getString(R.string.tap_here_to_scan_qr_code))
                                                    || strTextInputValue.contentEquals(context.getResources().getString(R.string.tap_here_to_scan_bar_code))) {
                                                strTextInputValue = "";
                                            }
                                            if(strMinChars !=null && strTextInputValue.length() < Integer.parseInt(strMinChars) ){
                                                textInput.setAlertTextInput().setVisibility(View.VISIBLE);
                                                textInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                                textInput.setAlertTextInput().setText(loadControlObject.getMinCharError());
                                                textInput.setAlertTextInput().setTextColor(context.getColor(R.color.delete_icon));
                                                ImproveHelper.setFocus(textInput.getTextInputView());
                                                validationFlag = false;
                                            }else if(strMinChars !=null && strTextInputValue.length() >= Integer.parseInt(strMinChars) ){
                                                if (isSubForm) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                                                } else {
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                                                }
                                            } else if (strTextInputValue.length() > 0) {
                                                textInput.setAlertTextInput().setVisibility(View.GONE);
//                                textInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context,R.drawable.control_default_background));
                                                if (checkTableColumns(loadControlObject.getControlName())) {
                                                    if (isSubForm) {
                                                        sfString.add(loadControlObject.getControlName() + "|" + strTextInputValue);
                                                    } else {
                                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                                                    }
                                                }
                                                validationFlag = true;
                                                Log.e(TAG, "controlSubmitValidation: TextInput - " + CONTROL_TYPE_TEXT_INPUT + " - " + strTextInputValue);
                                            } else {
                                                ImproveHelper.showToastAlert(context,loadControlObject.getMandatoryFieldError());
                                                textInput.setAlertTextInput().setVisibility(View.VISIBLE);
                                                textInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                                textInput.setAlertTextInput().setText(loadControlObject.getMandatoryFieldError());
                                                textInput.setAlertTextInput().setTextColor(context.getColor(R.color.delete_icon));
                                                ImproveHelper.setFocus(textInput.getTextInputView());
                                                validationFlag = false;
                                            }
                                        }
                                        else if (!textInput.gettap_text().getText().toString().isEmpty() &&
                                                ((loadControlObject.isCurrentLocation() || loadControlObject.isReadFromQRCode() || loadControlObject.isReadFromQRCode()) && !textInput.gettap_text().getText().toString().contentEquals(context.getResources().getString(R.string.tap_here_to_scan_qr_code)) && !textInput.gettap_text().getText().toString().contentEquals(context.getResources().getString(R.string.tap_here_to_scan_bar_code)))) {
                                            if (checkTableColumns(loadControlObject.getControlName())) {
                                                if (isSubForm) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + strTextInputValue);
                                                } else {
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                                                }
                                            }
                                        } else {
                                            ImproveHelper.showToastAlert(context,loadControlObject.getMandatoryFieldError());
                                            textInput.setAlertTextInput().setVisibility(View.VISIBLE);
                                            textInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            textInput.setAlertTextInput().setText(loadControlObject.getMandatoryFieldError());
                                            textInput.setAlertTextInput().setTextColor(context.getColor(R.color.delete_icon));
                                            ImproveHelper.setFocus(textInput.getTextInputView());
                                            validationFlag = false;
                                        }
                                    } else {
                                        if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                            strTextInputValue = strValueFromCtrl;
                                            if(strMinChars !=null && strTextInputValue.length() < Integer.parseInt(strMinChars) ){
                                                textInput.setAlertTextInput().setVisibility(View.VISIBLE);
                                                textInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                                textInput.setAlertTextInput().setText(loadControlObject.getMinCharError());
                                                textInput.setAlertTextInput().setTextColor(context.getColor(R.color.delete_icon));
                                                ImproveHelper.setFocus(textInput.getTextInputView());
                                                validationFlag = false;
                                            }else if(strMinChars !=null && strTextInputValue.length() >= Integer.parseInt(strMinChars)){
                                                if (isSubForm) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                                                } else {
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                                                }
                                            }else if (checkTableColumns(loadControlObject.getControlName())) {
                                                if (isSubForm) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                                                } else {
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                                                }
                                            }
                                        } else {
                                            if (checkTableColumns(loadControlObject.getControlName())) {
                                                if (isSubForm) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                                } else {
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                                }
                                            }
                                        }
                                    }
                                } else { // Text Input Invisible Case
//                            else if (loadControlObject.isInvisible()) { // Text Input Invisible Case
                                    if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                        strTextInputValue = strValueFromCtrl;
                                        if(strMinChars !=null && strTextInputValue.length() <Integer.parseInt(strMinChars) ){
                                            textInput.setAlertTextInput().setVisibility(View.VISIBLE);
                                            textInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            textInput.setAlertTextInput().setText(loadControlObject.getMinCharError());
                                            textInput.setAlertTextInput().setTextColor(context.getColor(R.color.delete_icon));
                                            ImproveHelper.setFocus(textInput.getTextInputView());
                                            validationFlag = false;
                                        }else if(strMinChars !=null && strTextInputValue.length() >= Integer.parseInt(strMinChars)){
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                                            }
                                        } else if (strTextInputValue.length() > 0) {
                                            if (checkTableColumns(loadControlObject.getControlName())) {
                                                if (isSubForm) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + strTextInputValue);
                                                } else {
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strTextInputValue.trim());
                                                }
                                            }
                                        }

                                    } else {
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty() && !strValueFromCtrl.trim().matches("")) {
                                textInput.setAlertTextInput().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                textInput.setAlertTextInput().setVisibility(View.VISIBLE);
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                textInput.setAlertTextInput().setText(alertMessage);
                                textInput.setAlertTextInput().setTextColor(context.getColor(R.color.delete_icon));
                                textInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                ImproveHelper.setFocus(textInput.getTextInputView());
                                validationFlag = false;
                            }
                        }
                        break;

                    case CONTROL_TYPE_NUMERIC_INPUT:
                        NumericInput numericInput = (NumericInput) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if (numericInput == null) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                        } else {
                            String strNumericValue = null;
                            if(isSubForm){
                                isControlVisible  = !numericInput.getControlObject().isInvisible();
                                strValueFromCtrl  = numericInput.getControlObject().getText();
                            }
//                        if (numericInput.getNumericInputView().getVisibility() == View.VISIBLE) {
                            if (isControlVisible) {
//                if (!loadControlObject.isInvisible()) {
                                if (loadControlObject.isNullAllowed()) {

//                                if (numericInput.getNumericValue() != null && !numericInput.getNumericValue().isEmpty()) {
                                    if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                        strNumericValue = strValueFromCtrl;
                                        if (strNumericValue.length() > 0) {
                                            numericInput.setAlertNumeric().setVisibility(View.GONE);
//                                numericInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context,R.drawable.control_default_background));

                                            if (loadControlObject.isEnableUpperLimit() && Integer.parseInt(strNumericValue) > Integer.parseInt(loadControlObject.getUpperLimit())/*&& bInput.compareTo(new BigDecimal(loadControlObject.getUpperLimit())) == 1*/) {

                                                numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                                numericInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                                numericInput.setAlertNumeric().setText(loadControlObject.getUpperLimitErrorMesage());
                                                ImproveHelper.setFocus(numericInput.getNumericInputView());
                                                validationFlag = false;
                                                Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");

                                            } else if(loadControlObject.isEnableLowerLimit() && Integer.parseInt(strNumericValue) < Integer.parseInt(loadControlObject.getLowerLimit())){
                                                numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                                numericInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                                numericInput.setAlertNumeric().setText(loadControlObject.getLowerLimitErrorMesage());
                                                validationFlag = false;
                                                ImproveHelper.setFocus(numericInput.getNumericInputView());
                                                Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                            } else {

/*                                                if (loadControlObject.isEnableLowerLimit() && Integer.parseInt(strNumericValue) < Integer.parseInt(loadControlObject.getLowerLimit())*//* && (new BigDecimal(loadControlObject.getLowerLimit()).compareTo(bInput) == 1)*//*) {
                                                    numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                                    numericInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                                    numericInput.setAlertNumeric().setText(loadControlObject.getLowerLimitErrorMesage());
                                                    validationFlag = false;
                                                    ImproveHelper.setFocus(numericInput.getNumericInputView());
                                                    Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                                } else {*/

//                                        BigDecimal bstrlength = new BigDecimal(String.valueOf(strNumericValue.length()));

                                                    if (loadControlObject.isEnableCappingDigits() && strNumericValue.length() > Integer.parseInt(loadControlObject.getCappingDigits())/*&& bstrlength.compareTo(new BigDecimal(loadControlObject.getCappingDigits())) == 1*/) {
                                                        numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                                        numericInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                                        numericInput.setAlertNumeric().setText(loadControlObject.getCappingError());
                                                        validationFlag = false;
                                                        ImproveHelper.setFocus(numericInput.getNumericInputView());
                                                        Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                                    } else {
                                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                                            if (isSubForm) {
                                                                sfString.add(loadControlObject.getControlName() + "|" + strNumericValue.trim());
                                                            } else {
                                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strNumericValue.trim());
                                                            }
                                                        }
                                                        validationFlag = true;
                                                        Log.e(TAG, "controlSubmitValidation: Numeric - " + strNumericValue);
                                                    }

                                            }


                                        } else {
                                            numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                            numericInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            numericInput.setAlertNumeric().setText(loadControlObject.getMandatoryFieldError());
                                            validationFlag = false;
                                            ImproveHelper.setFocus(numericInput.getNumericInputView());
                                            Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");

                                        }
                                    } else {
                                        numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                        numericInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                        numericInput.setAlertNumeric().setText(loadControlObject.getMandatoryFieldError());
                                        validationFlag = false;
                                        ImproveHelper.setFocus(numericInput.getNumericInputView());

                                    }
                                } else {
                                    if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                        strNumericValue = strValueFromCtrl;
                                        Log.e(TAG, "controlSubmitValidation: Numeric - " + strNumericValue);
                                        numericInput.setAlertNumeric().setVisibility(View.GONE);
                                        numericInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_active_background));

                                        if (loadControlObject.isEnableUpperLimit() && Integer.parseInt(strNumericValue) > Integer.parseInt(loadControlObject.getUpperLimit())/*&& bInput.compareTo(new BigDecimal(loadControlObject.getUpperLimit())) == 1*/) {

                                            numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                            numericInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            numericInput.setAlertNumeric().setText(loadControlObject.getUpperLimitErrorMesage());
                                            validationFlag = false;
                                            ImproveHelper.setFocus(numericInput.getNumericInputView());
                                            Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");

                                        } else if (loadControlObject.isEnableLowerLimit() && Integer.parseInt(strNumericValue) < Integer.parseInt(loadControlObject.getLowerLimit())/*&& (new BigDecimal(loadControlObject.getLowerLimit()).compareTo(bInput) == 1)*/) {
                                            numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                            numericInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            numericInput.setAlertNumeric().setText(loadControlObject.getLowerLimitErrorMesage());
                                            validationFlag = false;
                                            ImproveHelper.setFocus(numericInput.getNumericInputView());
                                            Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                        }
                                        else {

                                            /*if (loadControlObject.isEnableLowerLimit() && Integer.parseInt(strNumericValue) < Integer.parseInt(loadControlObject.getLowerLimit())*//*&& (new BigDecimal(loadControlObject.getLowerLimit()).compareTo(bInput) == 1)*//*) {
                                                numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                                numericInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                                numericInput.setAlertNumeric().setText(loadControlObject.getLowerLimitErrorMesage());
                                                validationFlag = false;
                                                ImproveHelper.setFocus(numericInput.getNumericInputView());
                                                Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                            } else {*/

//                                    BigDecimal bstrlength = new BigDecimal(String.valueOf(strNumericValue.length()));

                                                if (loadControlObject.isEnableCappingDigits() && strNumericValue.length() > Integer.parseInt(loadControlObject.getCappingDigits())/*&& bstrlength.compareTo(new BigDecimal(loadControlObject.getCappingDigits())) == 1*/) {
                                                    numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                                    numericInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                                    numericInput.setAlertNumeric().setText(loadControlObject.getCappingError());
                                                    validationFlag = false;
                                                    ImproveHelper.setFocus(numericInput.getNumericInputView());
                                                    Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                                } else {
                                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                                        if (isSubForm) {
                                                            sfString.add(loadControlObject.getControlName() + "|" + strNumericValue.trim());
                                                        } else {
                                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strNumericValue.trim());
                                                        }
                                                    }
                                                }
//                                            }
                                        }
                                    } else {
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                             } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                        }
                                    }
                                }
                            } else {
//                        else if (numericInput.getNumericInputView().getVisibility() == View.GONE) {
                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    strNumericValue = strValueFromCtrl;
                                    if (strNumericValue.length() > 0) {
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + strNumericValue.trim());
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strNumericValue.trim());
                                            }
                                        }
                                    }

                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                            }
                        }

                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty() && !strValueFromCtrl.trim().matches("")) {
                                numericInput.setAlertNumeric().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                numericInput.setAlertNumeric().setText(alertMessage);
                                numericInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                ImproveHelper.setFocus(numericInput.getNumericInputView());
                                validationFlag = false;
                            }
                        }
                        break;

                    case CONTROL_TYPE_PHONE:
                        Phone phoneInput = (Phone) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if (phoneInput == null) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                        } else {
                            String strPhoneValue = null;
                            if(isSubForm){
                                isControlVisible  = !phoneInput.getControlObject().isInvisible();
                                strValueFromCtrl  = phoneInput.getControlObject().getText();
                            }
                            if (isControlVisible) {
                                if (loadControlObject.isNullAllowed()) {
                                    if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                        strPhoneValue = strValueFromCtrl;
                                        if (strPhoneValue.length() > 0) {
                                            phoneInput.setAlertPhone().setVisibility(View.GONE);
//                                phoneInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context,R.drawable.control_default_background));
                                            BigDecimal bstrlength = new BigDecimal(String.valueOf(strPhoneValue.length()));

                                            if (loadControlObject.isEnableCappingDigits() && strPhoneValue.length() != Integer.parseInt(loadControlObject.getCappingDigits())/*bstrlength.compareTo(new BigDecimal(loadControlObject.getCappingDigits())) == 1*/) {
                                                phoneInput.setAlertPhone().setVisibility(View.VISIBLE);
                                                phoneInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                                phoneInput.setAlertPhone().setText(loadControlObject.getCappingError());
                                                validationFlag = false;
                                                ImproveHelper.setFocus(phoneInput.getPhoneView());
                                                Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                            } else {
                                                if (checkTableColumns(loadControlObject.getControlName())) {
                                                    if (isSubForm) {
                                                        sfString.add(loadControlObject.getControlName() + "|" + strPhoneValue.trim());
                                                    } else {
                                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strPhoneValue);
                                                    }
                                                }
                                                validationFlag = true;
                                                Log.e(TAG, "controlSubmitValidation: " + CONTROL_TYPE_PHONE + " - " + strPhoneValue);
                                            }
                                        } else {
                                            phoneInput.setAlertPhone().setVisibility(View.VISIBLE);
                                            phoneInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            phoneInput.setAlertPhone().setText(loadControlObject.getMandatoryFieldError());
                                            validationFlag = false;
                                            ImproveHelper.setFocus(phoneInput.getPhoneView());
                                            Log.e(TAG, "controlSubmitValidation: No phoneInput Value ");
                                        }
                                    } else {
                                        phoneInput.setAlertPhone().setVisibility(View.VISIBLE);
                                        phoneInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                        phoneInput.setAlertPhone().setText(loadControlObject.getMandatoryFieldError());
                                        validationFlag = false;
                                        ImproveHelper.setFocus(phoneInput.getPhoneView());
                                    }
                                } else {
                                    if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                        strPhoneValue = strValueFromCtrl;
                                        BigDecimal bstrlength = new BigDecimal(String.valueOf(strPhoneValue.length()));

                                        if (loadControlObject.isEnableCappingDigits() && strPhoneValue.length() != Integer.parseInt(loadControlObject.getCappingDigits())) {
                                            phoneInput.setAlertPhone().setVisibility(View.VISIBLE);
                                            phoneInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            phoneInput.setAlertPhone().setText(loadControlObject.getCappingError());
                                            validationFlag = false;
                                            ImproveHelper.setFocus(phoneInput.getPhoneView());
                                            Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                        } else {
                                            if (checkTableColumns(loadControlObject.getControlName())) {
                                                if (isSubForm) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + strPhoneValue.trim());
                                                } else {
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strPhoneValue);
                                                }
                                            }
                                            Log.e(TAG, "controlSubmitValidation: " + CONTROL_TYPE_PHONE + " - " + strPhoneValue);
                                        }
                                    } else {
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    strPhoneValue = strValueFromCtrl;
                                    if (strPhoneValue.length() > 0) {
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + strPhoneValue.trim());
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strPhoneValue.trim());
                                            }
                                        }
                                    }

                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                            }
                        }

                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty() && !strValueFromCtrl.trim().matches("")) {
                                phoneInput.setAlertPhone().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                phoneInput.setAlertPhone().setVisibility(View.VISIBLE);
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                phoneInput.setAlertPhone().setText(alertMessage);
                                phoneInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                ImproveHelper.setFocus(phoneInput.getPhoneView());
                                validationFlag = false;
                            }
                        }
                        break;
                    case CONTROL_TYPE_EMAIL:
                        Email email = (Email) List_ControlClassObjects.get(loadControlObject.getControlName());
                        String strEmailValue = null;
//                    if (email.getEmailView().getVisibility() == View.VISIBLE) {
                        if(isSubForm){
                            isControlVisible  = !email.getControlObject().isInvisible();
                            strValueFromCtrl  = email.getControlObject().getText();
                        }
                        if (isControlVisible) {
//                if (!loadControlObject.isInvisible()) {
                            if (loadControlObject.isNullAllowed()) {
                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    strEmailValue = strValueFromCtrl;
//                            if(loadControlObject.isRestrictEmailDomain()){
//                                loadControlObject.getRestrictedDomainNames()
//                            }
                                    if (strEmailValue.length() > 0 && strEmailValue.matches(emailPatternMain)) {
                                        Log.e(TAG, "controlSubmitValidationEmail: " + CONTROL_TYPE_EMAIL + " - " + strEmailValue);
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + strEmailValue.trim());
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strEmailValue.trim());
                                            }
                                        }
                                        email.setAlertEmail().setVisibility(View.GONE);
//                                email.getLl_tap_text().setBackground(ContextCompat.getDrawable(context,R.drawable.control_default_background));
                                        validationFlag = true;
                                    } else {
                                        email.setAlertEmail().setVisibility(View.VISIBLE);
                                        email.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                        email.setAlertEmail().setText("Invalid email");
                                        validationFlag = false;
                                        ImproveHelper.setFocus(email.getEmailView());
                                        Log.e(TAG, "controlSubmitValidationEmail: No EmailInput Value ");
                                    }
                                } else {
                                    email.setAlertEmail().setVisibility(View.VISIBLE);
                                    email.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                    email.setAlertEmail().setText(loadControlObject.getMandatoryFieldError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(email.getEmailView());
                                }
                            } else {
                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    strEmailValue = strValueFromCtrl;
                                    if (isSubForm) {
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strEmailValue.trim());
                                        }
                                    } else {
                                        if (strEmailValue.length() > 0 && strEmailValue.trim().matches(emailPatternMain)) {
                                            if (checkTableColumns(loadControlObject.getControlName())) {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strEmailValue.trim());
                                            }
                                        } else {
                                            email.setAlertEmail().setVisibility(View.VISIBLE);
                                            email.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            email.setAlertEmail().setText("Invalid email");
                                            validationFlag = false;
                                            ImproveHelper.setFocus(email.getEmailView());
                                            Log.e(TAG, "controlSubmitValidationEmail: No EmailInput Value ");
                                        }
                                    }
                                    Log.e(TAG, "controlSubmitValidationEmail: " + CONTROL_TYPE_EMAIL + " - " + strEmailValue);
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                            }

                        } else {
//                    else if (email.getEmailView().getVisibility() == View.GONE) {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                strEmailValue = strValueFromCtrl;
                                if (strEmailValue.length() > 0) {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strEmailValue.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strEmailValue.trim());
                                        }
                                    }
                                }

                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty() && !strValueFromCtrl.trim().matches("")) {
                                email.setAlertEmail().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                email.setAlertEmail().setVisibility(View.VISIBLE);
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                email.setAlertEmail().setText(alertMessage);
                                email.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                ImproveHelper.setFocus(email.getEmailView());
                                validationFlag = false;
                            }
                        }
                        break;
                    case CONTROL_TYPE_LARGE_INPUT:
                        LargeInput largeInput = (LargeInput) List_ControlClassObjects.get(loadControlObject.getControlName());
                        String strLargeInput = null;
                        if(isSubForm){
                            isControlVisible  = !largeInput.getControlObject().isInvisible();
                            strValueFromCtrl  = largeInput.getControlObject().getText();
                        }
                        if (isControlVisible) {
                            if (loadControlObject.isNullAllowed()) {
                                if (!loadControlObject.isHtmlEditorEnabled() && strValueFromCtrl != null && strValueFromCtrl.length() > 0) {
                                    strLargeInput = strValueFromCtrl;

                                    largeInput.setAlertLargeInput().setVisibility(View.GONE);
//                            largeInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context,R.drawable.control_default_background));
                                    BigDecimal bInput = new BigDecimal(strLargeInput.length());

                                    if (loadControlObject.isEnableMaxCharacters() && bInput.compareTo(new BigDecimal(loadControlObject.getMaxCharacters())) == 1) {

                                        largeInput.setAlertLargeInput().setVisibility(View.VISIBLE);
                                        largeInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                        largeInput.setAlertLargeInput().setText(loadControlObject.getMaxCharError());
                                        validationFlag = false;
                                        ImproveHelper.setFocus(largeInput.getLargeInputView());
                                        Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");

                                    } else {

                                        if (loadControlObject.isEnableMinCharacters() && (new BigDecimal(loadControlObject.getMinCharacters())).compareTo(bInput) == 1) {
                                            largeInput.setAlertLargeInput().setVisibility(View.VISIBLE);
                                            largeInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            largeInput.setAlertLargeInput().setText(loadControlObject.getMinCharError());
                                            validationFlag = false;
                                            ImproveHelper.setFocus(largeInput.getLargeInputView());
                                            Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                        } else {
                                            if (checkTableColumns(loadControlObject.getControlName())) {
                                                if (isSubForm) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                                } else {
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                                }
                                            }
                                            validationFlag = true;
                                        }
                                    }
                                    Log.e(TAG, "controlSubmitValidation: LargeInput - " + strLargeInput);
                                } else if ((loadControlObject.isHtmlEditorEnabled() || loadControlObject.isHtmlViewerEnabled()) && !strValueFromCtrl.contentEquals("")) {
                                    strLargeInput = strValueFromCtrl;
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                        }
                                    }
                                    validationFlag = true;
                                } else {
                                    Log.e(TAG, "controlSubmitValidation: LargeInput - is mandatory ");
                                    largeInput.setAlertLargeInput().setVisibility(View.VISIBLE);
                                    largeInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                    largeInput.setAlertLargeInput().setText(loadControlObject.getMandatoryFieldError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(largeInput.getLargeInputView());
                                }
                            } else {
                                if (!loadControlObject.isHtmlEditorEnabled() && strValueFromCtrl != null && strValueFromCtrl.length() > 0) {
                                    strLargeInput = strValueFromCtrl;
                                    BigDecimal bInput = new BigDecimal(strLargeInput.length());

                                    if (loadControlObject.isEnableMaxCharacters() && bInput.compareTo(new BigDecimal(loadControlObject.getMaxCharacters())) == 1) {

                                        largeInput.setAlertLargeInput().setVisibility(View.VISIBLE);
                                        largeInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                        largeInput.setAlertLargeInput().setText(loadControlObject.getMaxCharError());
                                        validationFlag = false;
                                        ImproveHelper.setFocus(largeInput.getLargeInputView());
                                        Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");

                                    } else {

                                        if (loadControlObject.isEnableMinCharacters() && (new BigDecimal(loadControlObject.getMinCharacters())).compareTo(bInput) == 1) {
                                            largeInput.setAlertLargeInput().setVisibility(View.VISIBLE);
                                            largeInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            largeInput.setAlertLargeInput().setText(loadControlObject.getMinCharError());
                                            validationFlag = false;
                                            ImproveHelper.setFocus(largeInput.getLargeInputView());
                                            Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                        } else {
                                            if (checkTableColumns(loadControlObject.getControlName())) {
                                                if (isSubForm) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                                } else {
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                                }
                                            }
                                            Log.e(TAG, "controlSubmitValidation:" + CONTROL_TYPE_LARGE_INPUT + " - " + strLargeInput);
                                        }
                                    }
                                } else if ((loadControlObject.isHtmlEditorEnabled() || loadControlObject.isHtmlViewerEnabled()) && strValueFromCtrl != null && !strValueFromCtrl.contentEquals("")) {
                                    strLargeInput = strValueFromCtrl;
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                        }
                                    }
                                    validationFlag = true;
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                            }
                        } else {
//                    else if (largeInput.getLargeInputView().getVisibility() == View.GONE) {
                            if (!loadControlObject.isHtmlEditorEnabled() && strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                strLargeInput = strValueFromCtrl;
                                if (strLargeInput.length() > 0) {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                        }
                                    }
                                }

                            } else if ((loadControlObject.isHtmlEditorEnabled() || loadControlObject.isHtmlViewerEnabled()) && strValueFromCtrl != null && !strValueFromCtrl.contentEquals("")) {
                                strLargeInput = strValueFromCtrl;
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                    }
                                }
                                validationFlag = true;
                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty() && !strValueFromCtrl.trim().matches("")) {
                                largeInput.setAlertLargeInput().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                largeInput.setAlertLargeInput().setVisibility(View.VISIBLE);
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                largeInput.setAlertLargeInput().setText(alertMessage);
                                largeInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                ImproveHelper.setFocus(largeInput.getLargeInputView());
                                validationFlag = false;
                            }
                        }
                        break;
                    case CONTROL_TYPE_CAMERA:
                        Camera camera = (Camera) List_ControlClassObjects.get(loadControlObject.getControlName());
                        String strImgWithGps =  null;
                        if (camera == null) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                        } else { // Initially it is empty

                        }
                        if(isSubForm){
                            isControlVisible  = !camera.getControlObject().isInvisible();
                            strValueFromCtrl  = camera.getControlObject().getText();
                            strValueIdFromCtrl =  camera.getControlObject().getControlValue();
                            strImgWithGps = camera.getControlObject().getImageGPS();
                        }else{
                            strValueIdFromCtrl = loadControlObject.getControlValue(); // ForImageWithGPS
                            strImgWithGps = loadControlObject.getImageGPS(); // ForImageWithGPS
                        }
                        if (isControlVisible) {
                            if (loadControlObject.isNullAllowed()) {
                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    camera.setAlertCamera().setVisibility(View.GONE);
                                    Log.e(TAG, "controlSubmitValidation:" + loadControlObject.getControlName() + " | " + "NA");
//                            stringListSubmit.add(loadControlObject.getControlName() + " | " + "NA");
//                            stringListFiles.add(camera.getControlRealPath().getTag().toString());
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, strValueFromCtrl);
                                            sfFilesString.add(cam_hash);
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                            if (loadControlObject.isEnableImageWithGps()) {
                                                sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + strImgWithGps);
                                                sfString.add(loadControlObject.getControlName() + "_Type" + "|" + "Single point GPS");
//                                            sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + camera.getGPSOfImage().getTag().toString());
//                                            sfString.add(loadControlObject.getControlName() + "_Type" + "|" + "Single point GPS");
                                            }
                                        } else {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            if (strValueFromCtrl != null) {
                                                cam_hash.put(loadControlObject.getControlName(), strValueFromCtrl);
                                                stringListFiles.add(cam_hash);
                                            } else {
                                                cam_hash.put(loadControlObject.getControlName(), strValueIdFromCtrl);
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueIdFromCtrl);
                                            }
                                            if (loadControlObject.isEnableImageWithGps()) {
                                                stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + strImgWithGps);
                                                stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + "Single point GPS");
                                            }
                                        }
                                    }

                                    validationFlag = true;
                                } else {
                                    camera.setAlertCamera().setVisibility(View.VISIBLE);
                                    camera.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.rectangle_border_camera_error));
                                    camera.setAlertCamera().setText(loadControlObject.getMandatoryFieldError());
                                    camera.getCameraView().setBackground(ContextCompat.getDrawable(context, R.drawable.rectangle_border_camera_error));
//                                    CustomTextView tvAlertText = camera.getCameraView().findViewById(R.id.tvAlertText);
//                                    if(tvAlertText != null){
//                                        tvAlertText.setVisibility(View.VISIBLE);
//                                        tvAlertText.setText(loadControlObject.getMandatoryFieldError());
//                                    }
                                    Log.e(TAG, "controlSubmitValidation: No camera Value");
                                    validationFlag = false;
                                    ImproveHelper.setFocus(camera.getCameraView());
                                }
                            } else {
                                if (camera.getBitmapSetValues() != null || strValueIdFromCtrl != null || strValueFromCtrl != null) {
                                    Log.e(TAG, "controlSubmitValidation: " + loadControlObject.getControlName() + "|" + "NA");
//                            stringListSubmit.add(loadControlObject.getControlName() + " | " + "NA");
//                            stringListFiles.add(strValueFromCtrl);
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, strValueFromCtrl);
                                            sfFilesString.add(cam_hash);
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                            if (loadControlObject.isEnableImageWithGps() && strImgWithGps != null && !strImgWithGps.isEmpty()) {
                                                sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + strImgWithGps);
                                                sfString.add(loadControlObject.getControlName() + "_Type" + "|" + "Single point GPS");
                                            }
                                        } else {

                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            if (strValueFromCtrl != null) {
                                                cam_hash.put(loadControlObject.getControlName(), strValueFromCtrl);
                                                stringListFiles.add(cam_hash);
                                            } else {
                                                cam_hash.put(loadControlObject.getControlName(), strValueIdFromCtrl);
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueIdFromCtrl);
                                            }

                                            if (loadControlObject.isEnableImageWithGps() && strImgWithGps != null && !strImgWithGps.isEmpty()) {
                                                stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + strImgWithGps);
                                                stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + "Single point GPS");
                                            }

                                        }
                                    }

                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                            if (loadControlObject.isEnableImageWithGps()) {
                                                sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + "");
                                                sfString.add(loadControlObject.getControlName() + "_Type" + "|" + "Single point GPS");
                                            }
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                            if (loadControlObject.isEnableImageWithGps()) {
                                                stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + "");
                                                stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + "Single point GPS");
                                            }

                                        }
                                    }
                                }
                            }
                        } else {
//                    else if (camera.getCameraView().getVisibility() == View.GONE) {
//                        if (!camera.isImageViewNull()) {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, strValueFromCtrl);
                                        sfFilesString.add(cam_hash);
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                        if (loadControlObject.isEnableImageWithGps() && strImgWithGps != null && !strImgWithGps.isEmpty()) {
                                            sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + strImgWithGps);
                                            sfString.add(loadControlObject.getControlName() + "_Type" + "|" + "Single point GPS");
                                        }
                                    } else {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        if (strValueFromCtrl != null) {
                                            cam_hash.put(loadControlObject.getControlName(), strValueFromCtrl);
                                            stringListFiles.add(cam_hash);
                                        } else {
                                            cam_hash.put(loadControlObject.getControlName(), strValueIdFromCtrl);
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueIdFromCtrl);
                                        }
                                        if (loadControlObject.isEnableImageWithGps() && strImgWithGps != null && !strImgWithGps.isEmpty()) {
                                            stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + strImgWithGps);
                                            stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + "Single point GPS");
                                        }
                                    }
                                }
                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                        if (loadControlObject.isEnableImageWithGps()) {
                                            sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + "");
                                            sfString.add(loadControlObject.getControlName() + "_Type" + "|" + "Single point GPS");
                                        }
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        if (loadControlObject.isEnableImageWithGps()) {
                                            stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + "");
                                            stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + "Single point GPS");
                                        }
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                camera.setAlertCamera().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                camera.setAlertCamera().setVisibility(View.VISIBLE);
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                camera.setAlertCamera().setText(alertMessage);
                                camera.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.rectangle_border_camera_error));
                                ImproveHelper.setFocus(camera.getCameraView());
                                validationFlag = false;

                            }
                        }
                        break;
                    case CONTROL_TYPE_FILE_BROWSING:
                        FileBrowsing fileBrowsing = (FileBrowsing) List_ControlClassObjects.get(loadControlObject.getControlName());

                        String strFilePath = null;
                        if(isSubForm){
                            isControlVisible  = !fileBrowsing.getControlObject().isInvisible();
                            strValueFromCtrl  = fileBrowsing.getControlObject().getText();
                            strValueIdFromCtrl =  fileBrowsing.getControlObject().getDefaultValue();
                        }else{
                            strValueIdFromCtrl = loadControlObject.getDefaultValue();
                        }

//                    if (fileBrowsing.getFileBrowsingView().getVisibility() == View.VISIBLE) {
                        if (isControlVisible) {
//                if (!loadControlObject.isInvisible()) {
                            if (loadControlObject.isNullAllowed()) {
//                        if (!fileBrowsing.getTapTextView().getText().toString().equalsIgnoreCase(context.getString(R.string.tap_here_to_select_file))
//                                && fileBrowsing.getTapTextView().getText().toString().length() > 0) {
                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    strFilePath = strValueFromCtrl;
                                    Log.e(TAG, "controlSubmitValidation:" + CONTROL_TYPE_FILE_BROWSING + "-" + strFilePath);

                                    fileBrowsing.setAlertFileBrowser().setVisibility(View.GONE);
//                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                                stringListFiles.add(fileBrowsing.getControlRealPath().getTag().toString());
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, strValueFromCtrl);

                                            sfFilesString.add(cam_hash);
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                                cam_hash.put(loadControlObject.getControlName(), strValueFromCtrl);
                                                stringListFiles.add(cam_hash);
                                            } else {
                                                cam_hash.put(loadControlObject.getControlName(), strValueIdFromCtrl);
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueIdFromCtrl);
                                            }

                                        }
                                    }

                                    validationFlag = true;

                                } else {
                                    fileBrowsing.setAlertFileBrowser().setVisibility(View.VISIBLE);
                                    fileBrowsing.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.rectangle_border_camera_error));
                                    fileBrowsing.setAlertFileBrowser().setText(loadControlObject.getMandatoryFieldError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(fileBrowsing.getFileBrowsingView());
                                }
                            } else {
                        /*if (!fileBrowsing.getTapTextView().getText().toString().equalsIgnoreCase(context.getString(R.string.tap_here_to_select_file))
                                && fileBrowsing.getTapTextView().getText().toString().length() > 0) {*/
                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    strFilePath = strValueFromCtrl;
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListFiles.add(strValueFromCtrl);
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, strValueFromCtrl);

                                            sfFilesString.add(cam_hash);
                                            sfString.add(loadControlObject.getControlName() + "|" + "");

                                        } else {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                                cam_hash.put(loadControlObject.getControlName(), strValueFromCtrl);
                                                stringListFiles.add(cam_hash);
                                            } else {
                                                cam_hash.put(loadControlObject.getControlName(), strValueIdFromCtrl);
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueIdFromCtrl);
                                            }

                                        }
                                    }
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                            }

                        } else { // Text Input Invisible Case
//                    else if (fileBrowsing.getFileBrowsingView().getVisibility() == View.GONE) { // Text Input Invisible Case
                   /* if (!fileBrowsing.getTapTextView().getText().toString().equalsIgnoreCase(context.getString(R.string.tap_here_to_select_file))
                            && fileBrowsing.getTapTextView().getText().toString().length() > 0) {*/
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, strValueFromCtrl);
                                        sfFilesString.add(cam_hash);
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                    } else {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                            cam_hash.put(loadControlObject.getControlName(), strValueFromCtrl);
                                            stringListFiles.add(cam_hash);
                                        } else {
                                            cam_hash.put(loadControlObject.getControlName(), strValueIdFromCtrl);
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueIdFromCtrl);
                                        }
                                    }
                                }
                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                fileBrowsing.setAlertFileBrowser().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                fileBrowsing.setAlertFileBrowser().setVisibility(View.VISIBLE);
                                fileBrowsing.setAlertFileBrowser().setText(alertMessage);
                                fileBrowsing.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.rectangle_border_camera_error));
                                validationFlag = false;
                                ImproveHelper.setFocus(fileBrowsing.getFileBrowsingView());

                            }
                        }
                        break;
                    case CONTROL_TYPE_CALENDER:
                        Calendar calendar = (Calendar) List_ControlClassObjects.get(loadControlObject.getControlName());
                        String strCalendarValue = null;
                        if(isSubForm){
                            isControlVisible  = !calendar.getControlObject().isInvisible();
                            strValueFromCtrl  = calendar.getControlObject().getSelectedDate();
                        }else{
                            strValueFromCtrl = loadControlObject.getSelectedDate();
                        }
                        if (isControlVisible) {
                            if (loadControlObject.isNullAllowed()) {
                                if (strValueFromCtrl != null) {
                                    strCalendarValue = strValueFromCtrl;
                                    if (strCalendarValue.length() > 0) {
                                        Log.e(TAG, "controlSubmitValidation: Calendar - " + strCalendarValue);

                                        calendar.setAlertCalendar().setVisibility(View.GONE);
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + strCalendarValue.trim());
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strCalendarValue.trim());
                                            }
                                        }
                                        validationFlag = true;

                                    } else {
                                        calendar.setAlertCalendar().setVisibility(View.VISIBLE);
                                        calendar.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                        calendar.setAlertCalendar().setText(loadControlObject.getMandatoryFieldError());
                                        Log.e(TAG, "controlSubmitValidation: No Calendar Value ");
                                        validationFlag = false;
                                        ImproveHelper.setFocus(calendar.getCalnderView());
                                    }
                                } else {
                                    calendar.setAlertCalendar().setVisibility(View.VISIBLE);
                                    calendar.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                    calendar.setAlertCalendar().setText(loadControlObject.getMandatoryFieldError());
                                    Log.e(TAG, "controlSubmitValidation: No Calendar Value ");
                                    validationFlag = false;
                                    ImproveHelper.setFocus(calendar.getCalnderView());
                                }
                            } else {
                                if (strValueFromCtrl != null/*calendar.getCalendarValue() != null*/) {
//                            strCalendarValue = calendar.getCalendarValue();
                                    strCalendarValue = strValueFromCtrl;
                                    if (strCalendarValue.length() > 0) {
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + strCalendarValue.trim());
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strCalendarValue);
                                            }
                                        }
                                        Log.e(TAG, "controlSubmitValidation: Calendar - " + strCalendarValue);
                                    }
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                            }

                        } else {
//                    else if (calendar.getCalnderView().getVisibility() == View.GONE) {
                            if (strValueFromCtrl != null) {/*calendar.getCalendarValue() != null*/
//                        strCalendarValue = calendar.getCalendarValue();
                                strCalendarValue = strValueFromCtrl;
                                if (strCalendarValue.length() > 0) {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strCalendarValue.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strCalendarValue.trim());
                                        }
                                    }
                                }
                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl != null) {
                                calendar.setAlertCalendar().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {

                                String alertMessage = loadControlObject.getControlName() + " is required";
                                calendar.setAlertCalendar().setVisibility(View.VISIBLE);
                                calendar.setAlertCalendar().setText(alertMessage);
                                calendar.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                validationFlag = false;
                                ImproveHelper.setFocus(calendar.getCalnderView());

                            }
                        }
                        break;
                    case CONTROL_TYPE_CHECKBOX:
                        Checkbox checkBox = (Checkbox) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if(isSubForm){
                            isControlVisible  = !checkBox.getControlObject().isInvisible();
                            isItemSelected  = checkBox.getControlObject().isItemSelected();
                            strValueFromCtrl  = checkBox.getControlObject().getText();
                            strValueIdFromCtrl  = checkBox.getControlObject().getControlValue();
                        }
                        if (isControlVisible) {
                            if (loadControlObject.isNullAllowed()) {
//                                if (isItemSelected) {
                                if(checkBox.isAllowOtherTextEmpty()){
                                    checkBox.setAlertCheckbox().setVisibility(View.VISIBLE);
                                    checkBox.setAlertCheckBoxError(true);
                                    checkBox.setAlertCheckbox().setText("Field should not be empty");
                                    validationFlag = false;
                                    ImproveHelper.setFocus(checkBox.getCheckbox());
                                } else if (!checkBox.isCheckBoxListEmpty()) {
                                    checkBox.setAlertCheckbox().setVisibility(View.GONE);
                                    if (loadControlObject.getCheckbox_ValueType().equalsIgnoreCase("Boolean")) {
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            String cb_val = strValueFromCtrl;
                                            String cb_id = strValueIdFromCtrl;
//                                            String cb_val = checkBox.getSelectedNameCheckboxString();
//                                            String cb_id = checkBox.getSelectedId();
                                            if (isSubForm) {
                                                if (cb_val != null && cb_val.length() > 0) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + cb_val);
                                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + cb_id);
                                                } else {
                                                    sfString.add(loadControlObject.getControlName() + "|" + null);
                                                }
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + cb_val);
                                                stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + cb_id);
                                            }
                                        }
                                    } else if (loadControlObject.getCheckbox_ValueType().equalsIgnoreCase("Value") &&
                                            strValueFromCtrl != null) {
                                        Log.d(TAG, "CB_Values: " + CONTROL_TYPE_CHECKBOX + "-" + strValueFromCtrl);
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                if (strValueFromCtrl != null && strValueFromCtrl.length() > 0) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
//                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + ImproveHelper.getSelectedId(strValueFromCtrl, loadControlObject));
                                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                                } else {
                                                    sfString.add(loadControlObject.getControlName() + "|" + null);
                                                }
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
//                                        stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + ImproveHelper.getSelectedId(strValueFromCtrl, loadControlObject));
//                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                                stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                            }
                                        }
                                        validationFlag = true;
                                    }else{
                                        checkBox.setAlertCheckbox().setVisibility(View.VISIBLE);
                                        checkBox.setAlertCheckBoxError(true);
                                        checkBox.setAlertCheckbox().setText(loadControlObject.getMandatoryFieldError());
                                        validationFlag = false;
                                        ImproveHelper.setFocus(checkBox.getCheckbox());

                                    }
                                } else {
                                    checkBox.setAlertCheckbox().setVisibility(View.VISIBLE);
                                    checkBox.setAlertCheckBoxError(true);
                                    checkBox.setAlertCheckbox().setText(loadControlObject.getMandatoryFieldError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(checkBox.getCheckbox());
                                }
                            } else {
                                if(checkBox.isAllowOtherTextEmpty()){
                                    checkBox.setAlertCheckbox().setVisibility(View.VISIBLE);
                                    checkBox.setAlertCheckBoxError(true);
                                    checkBox.setAlertCheckbox().setText("Field should not be empty");
                                    validationFlag = false;
                                    ImproveHelper.setFocus(checkBox.getCheckbox());
                                } else if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (loadControlObject.getCheckbox_ValueType().equalsIgnoreCase("Boolean")) {
                                            String cb_val = strValueFromCtrl;
                                            String cb_id = strValueIdFromCtrl;
                                            if (isSubForm) {
                                                if (cb_val != null && cb_val.length() > 0) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + cb_val);
                                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + cb_id);
                                                } else {
                                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                                }
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + cb_val);
                                                stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + cb_id);
                                            }
                                        } else {//Value
                                                if (isSubForm) {
                                                    if (strValueFromCtrl != null && strValueFromCtrl.length() > 0) {
                                                        sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                                    } else {
                                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                                    }

                                                } else {
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
//                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + ImproveHelper.getSelectedId(strValueFromCtrl, loadControlObject));
//                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                                    stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                                }
                                        }

                                    }
                                    Log.e(TAG, "controlSubmitValidation: " + CONTROL_TYPE_CHECKBOX + "-" + strValueFromCtrl);
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                            stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                                        }
                                    }
                                }
                            }


                        }
                        else {
                            if(checkBox.isAllowOtherTextEmpty()){
                                checkBox.setAlertCheckbox().setVisibility(View.VISIBLE);
                                checkBox.setAlertCheckBoxError(true);
                                checkBox.setAlertCheckbox().setText("Field should not be empty");
                                validationFlag = false;
                                ImproveHelper.setFocus(checkBox.getCheckbox());
                            }
//                        else if (checkBox.getCheckbox().getVisibility() == View.GONE) {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (loadControlObject.getCheckbox_ValueType().equalsIgnoreCase("Boolean")) {
                                        String cb_val = strValueFromCtrl;
                                        String cb_id = strValueIdFromCtrl;
                                        if (isSubForm) {
                                            if (cb_val != null && cb_val.length() > 0) {
                                                sfString.add(loadControlObject.getControlName() + "|" + cb_val);
                                                sfString.add(loadControlObject.getControlName() + "id" + "|" + cb_id);
                                            } else {
                                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                                sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                            }
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + cb_val);
                                            stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + cb_id);
                                        }
                                    } else {//Value
                                        
                                            if (isSubForm) {
                                                if (strValueFromCtrl != null && strValueFromCtrl.length() > 0) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
//                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + ImproveHelper.getSelectedId(strValueFromCtrl, loadControlObject));
                                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                                } else {
                                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                                }
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
//                                stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + ImproveHelper.getSelectedId(strValueFromCtrl, loadControlObject));
                                                stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                            }
                                        }
                                }
                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
//                            if (isItemSelected) {
                            if (!checkBox.isCheckBoxListEmpty()) {
                                checkBox.setAlertCheckbox().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                checkBox.setAlertCheckbox().setVisibility(View.VISIBLE);
                                checkBox.setAlertCheckBoxError(true);
                                checkBox.setAlertCheckbox().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(checkBox.getCheckbox());

                            }
                        }
                        break;
                    case CONTROL_TYPE_RADIO_BUTTON:
                        RadioGroupView radioGroupView = (RadioGroupView) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if(isSubForm){
                            isControlVisible  = !radioGroupView.getControlObject().isInvisible();
                            isItemSelected  = radioGroupView.getControlObject().isItemSelected();
                            strValueFromCtrl  = radioGroupView.getControlObject().getText();
                            strValueIdFromCtrl  = radioGroupView.getControlObject().getControlValue();
                        }

                        if (isControlVisible) {
//                        if (radioGroupView.getRadioGroupView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                            if (loadControlObject.isNullAllowed()) {
                                if(radioGroupView.isAllowOtherTextEmpty()){
                                    radioGroupView.setAlertRadioGroup().setVisibility(View.VISIBLE);
                                    radioGroupView.setAlertRadioButtonError(true);
                                    radioGroupView.setAlertRadioGroup().setText("Field should not be empty");
                                    validationFlag = false;
                                    ImproveHelper.setFocus(radioGroupView.getRadioGroupView());
                                } else if (isItemSelected) {
                                    radioGroupView.setAlertRadioGroup().setVisibility(View.GONE);
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            if (strValueFromCtrl != null && strValueFromCtrl.length() > 0) {
                                                sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                                sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                            } else {
                                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                                sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                            }
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName().trim() + "|" + strValueFromCtrl);
                                            stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + strValueIdFromCtrl);
                                        }
                                    }
                                    validationFlag = true;
                                } else {
                                    radioGroupView.setAlertRadioGroup().setVisibility(View.VISIBLE);
                                    radioGroupView.setAlertRadioButtonError(true);
                                    radioGroupView.setAlertRadioGroup().setText(loadControlObject.getMandatoryFieldError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(radioGroupView.getRadioGroupView());
                                }
                            } else {
                                if(radioGroupView.isAllowOtherTextEmpty()){
                                    radioGroupView.setAlertRadioGroup().setVisibility(View.VISIBLE);
                                    radioGroupView.setAlertRadioButtonError(true);
                                    radioGroupView.setAlertRadioGroup().setText("Field should not be empty");
                                    validationFlag = false;
                                    ImproveHelper.setFocus(radioGroupView.getRadioGroupView());
                                } else if (radioGroupView.isRadioGroupViewSelected()) {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            if (strValueFromCtrl != null && strValueFromCtrl.length() > 0) {
                                                sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                                sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);

                                            } else {
                                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                                sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                            }
//                                sfString.add(loadControlObject.getControlName() + "|" + radioGroupView.getSelectedRadioButtonText());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName().trim() + "|" + strValueFromCtrl);
                                            stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + strValueIdFromCtrl);
                                        }
                                    }
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                            stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                                        }
                                    }
                                }
                            }
                        }
                        else {if(radioGroupView.isAllowOtherTextEmpty()){
                            radioGroupView.setAlertRadioGroup().setVisibility(View.VISIBLE);
                            radioGroupView.setAlertRadioButtonError(true);
                            radioGroupView.setAlertRadioGroup().setText("Field should not be empty");
                            validationFlag = false;
                            ImproveHelper.setFocus(radioGroupView.getRadioGroupView());
                        }
//                        else if (radioGroupView.getRadioGroupView().getVisibility() == View.GONE) {
                           else if (radioGroupView.isRadioGroupViewSelected()) {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        if (strValueFromCtrl != null && strValueFromCtrl.length() > 0) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);

                                        } else {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                        }
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName().trim() + "|" + strValueFromCtrl);
                                        stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + strValueIdFromCtrl);

                                    }
                                }
                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (isItemSelected) {
                                radioGroupView.setAlertRadioGroup().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {

                                String alertMessage = loadControlObject.getControlName() + " is required";
                                radioGroupView.setAlertRadioGroup().setVisibility(View.VISIBLE);
                                radioGroupView.setAlertRadioButtonError(true);
                                radioGroupView.setAlertRadioGroup().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(radioGroupView.getRadioGroupView());
                            }
                        }
                        break;
                    case CONTROL_TYPE_DROP_DOWN:
                        DropDown dropDown = (DropDown) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if(isSubForm){
                            isControlVisible  = !dropDown.getControlObject().isInvisible();
                            isItemSelected  = dropDown.getControlObject().isItemSelected();
                            strValueFromCtrl  = dropDown.getControlObject().getText();
                            strValueIdFromCtrl  = dropDown.getControlObject().getControlValue();
                        }

                        if (isControlVisible) {
//                        if (dropDown.getDropdown().getVisibility() == View.VISIBLE) {
                            /* if (!loadControlObject.isInvisible()) {*/
                            if (loadControlObject.isNullAllowed()) {
                                if (isItemSelected) {
                                    dropDown.setAlertDropDown().setVisibility(View.GONE);
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                            stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + strValueIdFromCtrl);
                                        }
                                    }
                                    validationFlag = true;
                                    if (dropDown.isDropDownOthersEmpty()) {
                                        validationFlag = false;
                                        dropDown.setAlertDropDown().setVisibility(View.VISIBLE);
                                        dropDown.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                        dropDown.setAlertDropDown().setText(loadControlObject.getMandatoryFieldError());
                                        ImproveHelper.setFocus(dropDown.getDropdown());
                                    } else {
                                        Log.d(TAG, "dd_value: " + CONTROL_TYPE_DROP_DOWN + "-" + strValueFromCtrl);
                                    }
                                } else {
                                    dropDown.setAlertDropDown().setVisibility(View.VISIBLE);
//                            dropDown.getSearchableSpinner().setBackground(ContextCompat.getDrawable(context,R.drawable.control_dropdown_background_error));
                                    dropDown.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                    dropDown.setAlertDropDown().setText(loadControlObject.getMandatoryFieldError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(dropDown.getDropdown());
                                }
                            } else {
                                if (isItemSelected) {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                            stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + strValueIdFromCtrl);
                                        }
                                    }
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                            stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                                        }
                                    }
                                }
                            }

                        }
                        else  {
//                        else if (dropDown.getDropdown().getVisibility() == View.GONE) {
                            if (isItemSelected) {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                        stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + strValueIdFromCtrl);
                                    }
                                }
                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (isItemSelected) {
                                dropDown.setAlertDropDown().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                dropDown.setAlertDropDown().setVisibility(View.VISIBLE);
                                dropDown.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                dropDown.setAlertDropDown().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(dropDown.getDropdown());
                            }
                        }
                        break;
                    case CONTROL_TYPE_CHECK_LIST:
                        CheckList checkList = (CheckList) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if(isSubForm){
                            isControlVisible  = !checkList.getControlObject().isInvisible();
                            isItemSelected  = checkList.getControlObject().isItemSelected();
                            strValueFromCtrl  = checkList.getControlObject().getText();
                            strValueIdFromCtrl  = checkList.getControlObject().getControlValue();
                        }

                        if (isControlVisible) {
//                if (!loadControlObject.isInvisible()) {
                            if (loadControlObject.isNullAllowed()) {
                                if (isItemSelected) {
                                    checkList.setAlertCheckList().setVisibility(View.GONE);
                                    validationFlag = true;
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                            stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + strValueIdFromCtrl);
                                        }
                                    }
                                    Log.d(TAG, "controlSubmitValidation: " + checkList.getCheckList());
                                } else {
                                    checkList.setAlertCheckList().setVisibility(View.VISIBLE);
                                    checkList.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                    checkList.setAlertCheckList().setText(loadControlObject.getMandatoryFieldError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(checkList.getCheckList());
                                }
                            } else {
                                if (isItemSelected && strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                            stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + strValueIdFromCtrl);
                                        }
                                    }
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                            stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                                        }
                                    }
                                }
                            }
                        } else {
                            if (isItemSelected && strValueFromCtrl  != null &&!strValueFromCtrl.isEmpty()) {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                        stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + strValueIdFromCtrl);
                                    }
                                }
                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (isItemSelected) {
                                checkList.setAlertCheckList().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {

                                String alertMessage = loadControlObject.getControlName() + " is required";
                                checkList.setAlertCheckList().setVisibility(View.VISIBLE);
                                checkList.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                checkList.setAlertCheckList().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(checkList.getCheckList());
                            }
                        }
                        break;
                    case CONTROL_TYPE_RATING:
                        Rating rating = (Rating) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if(isSubForm){
                            isControlVisible  = !rating.getControlObject().isInvisible();
                            strValueFromCtrl  = rating.getControlObject().getText();
                        }else{
                            strValueFromCtrl  = loadControlObject.getText();
                        }

                        if (isControlVisible) {
//                if (!loadControlObject.isInvisible()) {
                            if (loadControlObject.isNullAllowed()) {
                                if (strValueFromCtrl == null || strValueFromCtrl.equalsIgnoreCase("0.0")) {
                                    rating.setAlertRating().setVisibility(View.VISIBLE);
                                    rating.setRatingError();
                                    rating.setAlertRating().setText(loadControlObject.getMandatoryFieldError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(rating.getRatingView());
                                } else {
                                    Log.d(TAG, "CONTROL_TYPE_RATING: " + strValueFromCtrl);
                                    rating.setAlertRating().setVisibility(View.GONE);
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                        }
                                    }
                                    validationFlag = true;
                                }
                            } else {
                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    Log.d(TAG, "CONTROL_TYPE_RATING: " + strValueFromCtrl);
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                        }
                                    }
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                            }

                        }
                        else {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                    }
                                }
                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl.equalsIgnoreCase("0.0")
                                    || strValueFromCtrl.equalsIgnoreCase("0")
                                    || strValueFromCtrl.equalsIgnoreCase("")) {

                                String alertMessage = loadControlObject.getControlName() + " is required";
                                rating.setAlertRating().setVisibility(View.VISIBLE);
                                rating.setRatingError();
                                rating.setAlertRating().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(rating.getRatingView());
                            } else {
                                rating.setAlertRating().setVisibility(View.GONE);
                                validationFlag = true;
                            }
                        }
                        break;
                    case CONTROL_TYPE_VOICE_RECORDING:
                        VoiceRecording voiceRecording = (VoiceRecording) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if(isSubForm){
                            isControlVisible  = !voiceRecording.getControlObject().isInvisible();
                            isItemSelected  = voiceRecording.getControlObject().isItemSelected();
                            strValueFromCtrl  = voiceRecording.getControlObject().getText();
                            strValueIdFromCtrl  = voiceRecording.getControlObject().getControlValue();
                        }else{
                            strValueIdFromCtrl = loadControlObject.getControlValue();
                        }

                        if (isControlVisible) {
//                if (!loadControlObject.isInvisible()) {
                            if (loadControlObject.isNullAllowed()) {
                                if (!isItemSelected) {

                                    voiceRecording.setAlertVoiceRecording().setVisibility(View.VISIBLE);
                                    voiceRecording.getLayoutAudioFileUpload().setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_file_error));
                                    voiceRecording.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle_error));
                                    voiceRecording.setAlertVoiceRecording().setText(loadControlObject.getMandatoryFieldError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(voiceRecording.getVoiceRecordingView());
                                } else {
                                    voiceRecording.setAlertVoiceRecording().setVisibility(View.GONE);
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListVideoAudio.add(voiceRecording.getControlRealPath().getTag().toString());
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            if (voiceRecording.getControlRealPath().getTag() != null) {
                                                HashMap<String, String> cam_hash = new HashMap<>();
                                                cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, strValueFromCtrl);
                                                sfFilesString.add(cam_hash);
                                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                            } else {
                                                HashMap<String, String> cam_hash = new HashMap<>();
                                                cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, strValueIdFromCtrl);
                                                sfFilesString.add(cam_hash);
                                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                            }
                                        } else {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            if (voiceRecording.getControlRealPath().getTag() != null) {
                                                cam_hash.put(loadControlObject.getControlName(), strValueFromCtrl);
                                                stringListFiles.add(cam_hash);
                                            } else {
                                                cam_hash.put(loadControlObject.getControlName(), strValueIdFromCtrl);
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueIdFromCtrl);
                                            }

                                        }
                                    }

                                    validationFlag = true;
                                }

                            } else {
                                if (isItemSelected) {
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListVideoAudio.add(voiceRecording.getControlRealPath().getTag().toString());
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, strValueFromCtrl);
                                            sfFilesString.add(cam_hash);
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            if (voiceRecording.getControlRealPath().getTag() != null) {
                                                cam_hash.put(loadControlObject.getControlName(), strValueFromCtrl);
                                                stringListFiles.add(cam_hash);
                                            } else {
                                                cam_hash.put(loadControlObject.getControlName(), strValueIdFromCtrl);
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueIdFromCtrl);
                                            }

                                        }
                                    }

                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                            }

                        } else {
                            if (isItemSelected) {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, strValueFromCtrl);
                                        sfFilesString.add(cam_hash);
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                    } else {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        if (voiceRecording.getControlRealPath().getTag() != null) {
                                            cam_hash.put(loadControlObject.getControlName(), strValueFromCtrl);
                                            stringListFiles.add(cam_hash);
                                        } else {
                                            cam_hash.put(loadControlObject.getControlName(), strValueIdFromCtrl);
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueIdFromCtrl);
                                        }
                                    }
                                }
                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                    }
                                }
                            }
                        }

                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (isItemSelected) {
                                voiceRecording.setAlertVoiceRecording().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                voiceRecording.setAlertVoiceRecording().setVisibility(View.VISIBLE);
                                voiceRecording.getLayoutAudioFileUpload().setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_file_error));
                                voiceRecording.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle_error));
                                voiceRecording.setAlertVoiceRecording().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(voiceRecording.getVoiceRecordingView());
                            }
                        }

                        break;

                    case CONTROL_TYPE_VIDEO_RECORDING:

                        VideoRecording videoRecording = (VideoRecording) List_ControlClassObjects.get(loadControlObject.getControlName());
                        String strVideobase64 = null;
                        if(isSubForm){
                            isControlVisible  = !videoRecording.getControlObject().isInvisible();
                            isItemSelected  = videoRecording.getControlObject().isItemSelected();
                            strValueFromCtrl  = videoRecording.getControlObject().getText();
                            strValueIdFromCtrl  = videoRecording.getControlObject().getControlValue();
                        }else{
                            strValueIdFromCtrl = loadControlObject.getControlValue();
                        }
                        if (isControlVisible) {
//                if (!loadControlObject.isInvisible()) {
                            if (loadControlObject.isNullAllowed()) {
                                if (!isItemSelected) {
                                    videoRecording.setAlertVideoRecording().setVisibility(View.VISIBLE);
                                    videoRecording.getLayout_control().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                    videoRecording.setAlertVideoRecording().setText(loadControlObject.getMandatoryFieldError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(videoRecording.getVideoRecorderView());
                                } else {
                                    videoRecording.setAlertVideoRecording().setVisibility(View.GONE);
                                    strVideobase64 = videoRecording.getVideoFileToBase64(videoRecording.getFromCameraOrGalleyURI());
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListVideoAudio.add(videoRecording.getControlRealPath().getTag().toString());
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            String filePath = strValueFromCtrl.replace("file:///", "/");
                                            cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, filePath);
                                            sfFilesString.add(cam_hash);
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            if (videoRecording.getControlRealPath().getTag() != null) {
                                                String filePath = strValueFromCtrl.replace("file:///", "/");
                                                cam_hash.put(loadControlObject.getControlName(), filePath);
                                                stringListFiles.add(cam_hash);
                                            } else {
                                                cam_hash.put(loadControlObject.getControlName(), strValueIdFromCtrl);
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueIdFromCtrl);
                                            }

                                        }
                                    }

                                    Log.d(TAG, "controlSubmitValidation" + " - " + strVideobase64);
                                    validationFlag = true;
                                }
                            } else {
                                if (isItemSelected) {
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListVideoAudio.add(videoRecording.getControlRealPath().getTag().toString());
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            String filePath = strValueFromCtrl.replace("file:///", "/");
                                            cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, filePath);
                                            sfFilesString.add(cam_hash);
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            if (videoRecording.getControlRealPath().getTag() != null) {
                                                String filePath = strValueFromCtrl.replace("file:///", "/");
                                                cam_hash.put(loadControlObject.getControlName(), filePath);
                                                stringListFiles.add(cam_hash);
                                            } else {
                                                cam_hash.put(loadControlObject.getControlName(), strValueIdFromCtrl);
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueIdFromCtrl);
                                            }

                                        }
                                    }

                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                            }

                        } else  {
                            if (isItemSelected) {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        String filePath = strValueFromCtrl.replace("file:///", "/");
                                        cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, filePath);
                                        sfFilesString.add(cam_hash);
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                    } else {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        if (videoRecording.getControlRealPath().getTag() != null) {
                                            String filePath = strValueFromCtrl.replace("file:///", "/");
                                            cam_hash.put(loadControlObject.getControlName(), filePath);
                                            stringListFiles.add(cam_hash);
                                        } else {
                                            cam_hash.put(loadControlObject.getControlName(), strValueIdFromCtrl);
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueIdFromCtrl);
                                        }

                                    }
                                }
                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (isItemSelected) {
                                videoRecording.setAlertVideoRecording().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                videoRecording.setAlertVideoRecording().setVisibility(View.VISIBLE);
                                videoRecording.getLayout_control().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                videoRecording.setAlertVideoRecording().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(videoRecording.getVideoRecorderView());
                            }
                        }
                        break;

                    case CONTROL_TYPE_AUDIO_PLAYER:
                        AudioPlayer audioPlayer = (AudioPlayer) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if (checkTableColumns(loadControlObject.getControlName())) {
//                        strValueFromCtrl = audioPlayer.getPath();
                            if(isSubForm){
                                if(audioPlayer.getControlObject().getAudioData() !=null && !audioPlayer.getControlObject().getAudioData().isEmpty()) {
                                    strValueFromCtrl = audioPlayer.getControlObject().getAudioData();
                                }else if(audioPlayer.getControlObject().getControlValue() !=null && !audioPlayer.getControlObject().getControlValue().isEmpty()) {
                                    strValueFromCtrl = audioPlayer.getControlObject().getControlValue();
                                }
                            }
                            if (audioPlayer != null &&  strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" +  strValueFromCtrl.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" +  strValueFromCtrl.trim());
                                }
                            } else {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }}
                        break;
                    case CONTROL_TYPE_VIDEO_PLAYER:
                        VideoPlayer videoPlayer = (VideoPlayer) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if (checkTableColumns(loadControlObject.getControlName())) {
//                        strValueFromCtrl = videoPlayer.getPath();
                            if(isSubForm){
                                strValueFromCtrl = videoPlayer.getControlObject().getText();
                            }
                            if (videoPlayer != null && strValueFromCtrl != null) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" +  strValueFromCtrl.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" +  strValueFromCtrl.trim());
                                }
                            } else {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }}
                        break;
                    case CONTROL_TYPE_PERCENTAGE:
                        Percentage percentage = (Percentage) List_ControlClassObjects.get(loadControlObject.getControlName());
                        String strPercentage = null;
                        if(isSubForm){
                            isControlVisible  = !percentage.getControlObject().isInvisible();
                            strValueFromCtrl  = percentage.getControlObject().getText();
                        }
                        if (isControlVisible) {

//                if (!loadControlObject.isInvisible()) {
                            if (loadControlObject.isNullAllowed()) {
                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    percentage.setAlertPercentage().setVisibility(View.GONE);
                                    strPercentage = strValueFromCtrl;
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strPercentage.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strPercentage.trim());
                                        }
                                    }
                                    validationFlag = true;
                                    Log.d(TAG, "controlSubmitValidation" + " - " + strPercentage);
                                } else {
                                    percentage.setAlertPercentage().setVisibility(View.VISIBLE);
                                    percentage.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                    percentage.setAlertPercentage().setText(loadControlObject.getMandatoryFieldError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(percentage.getPercentageView());
                                }
                            } else {
                                if (strValueFromCtrl != null && !strValueFromCtrl.equalsIgnoreCase("")) {
                                    strPercentage = strValueFromCtrl;
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strPercentage.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strPercentage.trim());
                                        }
                                    }
                                    Log.d(TAG, "controlSubmitValidation" + " - " + strPercentage);
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                            }

                        } else  {
                            if (strValueFromCtrl != null && !strValueFromCtrl.equalsIgnoreCase("")) {
                                strPercentage = strValueFromCtrl;
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strPercentage.trim());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strPercentage.trim());
                                    }
                                }
                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                percentage.setAlertPercentage().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                percentage.setAlertPercentage().setVisibility(View.VISIBLE);
                                percentage.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                percentage.setAlertPercentage().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(percentage.getPercentageView());
                            }
                        }
                        break;
                    case CONTROL_TYPE_SIGNATURE:
                        SignatureView signatureView = (SignatureView) List_ControlClassObjects.get(loadControlObject.getControlName());
//                        Bitmap bitmapSignature = null;
                        if(isSubForm){
                            isControlVisible  = !signatureView.getControlObject().isInvisible();
                            strValueFromCtrl  = signatureView.getControlObject().getText();
                            strValueIdFromCtrl  = signatureView.getControlObject().getControlValue();
                        }else{
                            strValueIdFromCtrl = loadControlObject.getControlValue();
                        }
                        if (isControlVisible) {
//                if (!loadControlObject.isInvisible()) {
                            if (loadControlObject.isNullAllowed()) {
//                                if (signatureView.isSignatureView() && !signatureView.isSignatureImage()) {
                                if (strValueFromCtrl == null || strValueFromCtrl.isEmpty()) {
                                    signatureView.setAlertSignature().setVisibility(View.VISIBLE);
                                    signatureView.getLayoutUpload().setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_error));
                                    if (signatureView.getLl_tap_text() != null) {
                                        signatureView.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle_error));
                                    }
                                    signatureView.setAlertSignature().setText(loadControlObject.getMandatoryFieldError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(signatureView.getSignature());
                                } else {
                                    signatureView.setAlertSignature().setVisibility(View.GONE);
//                            bitmapSignature = signatureView.isSignatureBitMap();
//                            stringListFiles.add(signatureView.getControlRealPath().getTag().toString());
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, strValueFromCtrl);
                                            sfFilesString.add(cam_hash);
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            if (signatureView.getControlRealPath().getTag() != null) {
                                                cam_hash.put(loadControlObject.getControlName(), strValueFromCtrl);
                                                stringListFiles.add(cam_hash);
                                            } else {
                                                cam_hash.put(loadControlObject.getControlName(), strValueIdFromCtrl);
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueIdFromCtrl);
                                            }

                                        }
                                    }

//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
                                    validationFlag = true;
//                                    Log.d(TAG, "controlSubmitValidation" + " - " + bitmapSignature);
                                    Log.d(TAG, "controlSubmitValidation" + " - " + strValueFromCtrl);
                                }
                            } else {
//                                if (!signatureView.isSignatureView() || signatureView.isSignatureUploaded()) {
                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    if (signatureView.isSignatureBitMap() != null || signatureView.isSignatureUploaded()) {
//                                        bitmapSignature = signatureView.isSignatureBitMap();
//                                stringListFiles.add(signatureView.getControlRealPath().getTag().toString());
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                HashMap<String, String> cam_hash = new HashMap<>();
                                                cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, strValueFromCtrl);
                                                sfFilesString.add(cam_hash);
                                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                            } else {
                                                HashMap<String, String> cam_hash = new HashMap<>();
                                                if (signatureView.getControlRealPath().getTag() != null) {
                                                    cam_hash.put(loadControlObject.getControlName(), strValueFromCtrl);
                                                    stringListFiles.add(cam_hash);
                                                } else {
                                                    cam_hash.put(loadControlObject.getControlName(), strValueIdFromCtrl);
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueIdFromCtrl);
                                                }

                                            }
                                        }
//                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                                        Log.d(TAG, "controlSubmitValidation" + " - " + bitmapSignature);
                                        Log.d(TAG, "controlSubmitValidation" + " - " + strValueFromCtrl);
                                    }
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            if (!signatureView.isSignatureView() || signatureView.isSignatureUploaded()) {
                                if (signatureView.isSignatureBitMap() != null || signatureView.isSignatureUploaded()) {
//                                    bitmapSignature = signatureView.isSignatureBitMap();
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, strValueFromCtrl);
                                            sfFilesString.add(cam_hash);
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            if (signatureView.getControlRealPath().getTag() != null) {
                                                cam_hash.put(loadControlObject.getControlName(), strValueFromCtrl);
                                                stringListFiles.add(cam_hash);
                                            } else {
                                                cam_hash.put(loadControlObject.getControlName(), strValueIdFromCtrl);
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueIdFromCtrl);
                                            }

                                        }
                                    }
                                }
                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl != null) {
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                signatureView.setAlertSignature().setVisibility(View.VISIBLE);
                                signatureView.getLayoutUpload().setBackground(ContextCompat.getDrawable(context, R.drawable.circular_bg_error));
                                signatureView.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle_error));
                                signatureView.setAlertSignature().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(signatureView.getSignature());
                            } else {
                                signatureView.setAlertSignature().setVisibility(View.GONE);
                                validationFlag = true;
                            }
                        }
                        break;
                    case CONTROL_TYPE_URL_LINK:
                        UrlView urlView = (UrlView) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if(isSubForm){
                            isControlVisible  = !urlView.getControlObject().isInvisible();
                            strValueFromCtrl  = urlView.getControlObject().getText();
                        }

                        if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                            }
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                        }

                        break;

                    case CONTROL_TYPE_DECIMAL:

                        DecimalView decimalView = (DecimalView) List_ControlClassObjects.get(loadControlObject.getControlName());
                        String strDecimalValue = null;
                        if(isSubForm){
                            isControlVisible  = !decimalView.getControlObject().isInvisible();
                            strValueFromCtrl  = decimalView.getControlObject().getText();
                        }

                        if (isControlVisible) {
//                if (!loadControlObject.isInvisible()) {
                            if (loadControlObject.isNullAllowed()) {
                                if (strValueFromCtrl == null || strValueFromCtrl.isEmpty()) {
                                    decimalView.setAlertDecimalView().setVisibility(View.VISIBLE);
                                    decimalView.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                    decimalView.setAlertDecimalView().setText(loadControlObject.getMandatoryFieldError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(decimalView.getDecimal());
                                } else {
                                    decimalView.setAlertDecimalView().setVisibility(View.GONE);
                                    strDecimalValue = strValueFromCtrl;
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strDecimalValue.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strDecimalValue.trim());
                                        }
                                    }

                                    validationFlag = true;
                                    Log.d(TAG, "controlSubmitValidation" + " - " + strDecimalValue);
                                }
                            } else {
                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    strDecimalValue = strValueFromCtrl;
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strDecimalValue.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strDecimalValue.trim());
                                        }
                                    }
                                    Log.d(TAG, "controlSubmitValidation" + " - " + strDecimalValue);
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                            }

                        } else {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                strDecimalValue = strValueFromCtrl;
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strDecimalValue.trim());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strDecimalValue.trim());
                                    }
                                }
                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl == null || strValueFromCtrl.isEmpty()) {
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                decimalView.setAlertDecimalView().setVisibility(View.VISIBLE);
                                decimalView.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                decimalView.setAlertDecimalView().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(decimalView.getDecimal());
                            } else {
                                decimalView.setAlertDecimalView().setVisibility(View.GONE);
                                validationFlag = true;
                            }
                        }
                        break;
                    case CONTROL_TYPE_PASSWORD:

                        Password password = (Password) List_ControlClassObjects.get(loadControlObject.getControlName());
                        String strPasswordValue = null;
                        if(isSubForm){
                            isControlVisible  = !password.getControlObject().isInvisible();
                            strValueFromCtrl  = password.getControlObject().getText();
                            strValueIdFromCtrl  = password.getControlObject().getEncryptionTypeId();
                        }else{
                            strValueIdFromCtrl = loadControlObject.getEncryptionTypeId();
                        }
                        if (isControlVisible) {
//                if (!loadControlObject.isInvisible()) {
                            if (loadControlObject.isNullAllowed()) {
                                if (strValueFromCtrl != null && !strValueFromCtrl.equalsIgnoreCase("")) {
                                    strPasswordValue = strValueFromCtrl;
                                    if (loadControlObject.isEnablePasswordLength() && strPasswordValue.length() != Integer.parseInt(loadControlObject.getPasswordLength())) {
                                        password.setAlertPasswordView().setVisibility(View.VISIBLE);
                                        password.gettap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                        password.setAlertPasswordView().setText(loadControlObject.getPasswordLengthError());
                                        validationFlag = false;
                                        ImproveHelper.setFocus(password.getPassword());
                                    } else if (loadControlObject.isEnablePasswordPolicy() && !strPasswordValue.equalsIgnoreCase("")
                                            && checkPasswordPolicy(strPasswordValue, loadControlObject) != null) {
                                        password.setAlertPasswordView().setVisibility(View.VISIBLE);
                                        password.gettap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                        password.setAlertPasswordView().setText(checkPasswordPolicy(strPasswordValue, loadControlObject));
                                        validationFlag = false;
                                        ImproveHelper.setFocus(password.getPassword());
                                    } else {
                                        password.setAlertPasswordView().setVisibility(View.GONE);
                                        password.gettap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_active_background));
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (loadControlObject.isEnableEncryption()) {
                                                strPasswordValue = getEncryptedPassword(strPasswordValue, strValueIdFromCtrl);
                                            }
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                                            }
                                        }

                                        validationFlag = true;
                                    }
                                } else if (strValueFromCtrl == null || strValueFromCtrl.isEmpty()) {
                                    password.setAlertPasswordView().setVisibility(View.VISIBLE);
                                    password.gettap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                    password.setAlertPasswordView().setText(loadControlObject.getMandatoryFieldError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(password.getPassword());
                                } else {
                                    password.setAlertPasswordView().setVisibility(View.GONE);
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (loadControlObject.isEnableEncryption()) {
                                            strPasswordValue = getEncryptedPassword(strPasswordValue, strValueIdFromCtrl);
                                        }
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                                        }
                                    }

                                    validationFlag = true;
                                    Log.d(TAG, "controlSubmitValidation" + " - " + strPasswordValue);
                                }
                            } else {
                                if (!strValueFromCtrl.equalsIgnoreCase("")) {
                                    strPasswordValue = strValueFromCtrl;
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (loadControlObject.isEnableEncryption()) {
                                            strPasswordValue = getEncryptedPassword(strPasswordValue, strValueIdFromCtrl);
                                        }
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                                        }
                                    }
                                    Log.d(TAG, "controlSubmitValidation" + " - " + strPasswordValue);
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (loadControlObject.isEnableEncryption()) {
                                            strPasswordValue = getEncryptedPassword(strPasswordValue, strValueIdFromCtrl);
                                        }
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                            }

                        } else {
                            if (!strValueFromCtrl.equalsIgnoreCase("")) {
                                strPasswordValue = strValueFromCtrl;
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (loadControlObject.isEnableEncryption()) {
                                        strPasswordValue = getEncryptedPassword(strPasswordValue, strValueIdFromCtrl);
                                    }
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                                    }
                                }
                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (loadControlObject.isEnableEncryption()) {
                                        strPasswordValue = getEncryptedPassword(strPasswordValue, strValueIdFromCtrl);
                                    }
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl == null || strValueFromCtrl.isEmpty()) {
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                password.setAlertPasswordView().setVisibility(View.VISIBLE);
                                password.gettap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                password.setAlertPasswordView().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(password.getPassword());
                            } else {
                                password.setAlertPasswordView().setVisibility(View.GONE);
                                validationFlag = true;
                            }
                        }
                        break;
                    case CONTROL_TYPE_CURRENCY:
                        Currency currency = (Currency) List_ControlClassObjects.get(loadControlObject.getControlName());
                        String strCurrencyValue = null;
                        if(isSubForm){
                            isControlVisible  = !currency.getControlObject().isInvisible();
                            strValueFromCtrl  = currency.getControlObject().getText();
                        }
                        if (isControlVisible) {
//                if (!loadControlObject.isInvisible()) {
                            if (loadControlObject.isNullAllowed()) {

                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    strCurrencyValue = strValueFromCtrl;
                                    if (strCurrencyValue.length() > 0) {
                                        currency.setAlertCurrency().setVisibility(View.GONE);
                                        BigDecimal bInput = new BigDecimal(strCurrencyValue);

                                        if (loadControlObject.isEnableMaximumAmount() && bInput.compareTo(new BigDecimal(loadControlObject.getMaxAmount())) == 1) {

                                            currency.setAlertCurrency().setVisibility(View.VISIBLE);
                                            currency.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            currency.setAlertCurrency().setText(loadControlObject.getMaxAmountError());
                                            validationFlag = false;
                                            ImproveHelper.setFocus(currency.getCurrency());
                                            Log.e(TAG, "controlSubmitValidation: No Currency Value ");

                                        } else {

                                            if (loadControlObject.isEnableMinimumAmount() && (new BigDecimal(loadControlObject.getMinAmount()).compareTo(bInput) == 1)) {
                                                currency.setAlertCurrency().setVisibility(View.VISIBLE);
                                                currency.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                                currency.setAlertCurrency().setText(loadControlObject.getMinAmountError());
                                                validationFlag = false;
                                                ImproveHelper.setFocus(currency.getCurrency());
                                                Log.e(TAG, "controlSubmitValidation: No Currency Value ");
                                            } else {
                                                if (checkTableColumns(loadControlObject.getControlName())) {
                                                    if (isSubForm) {
                                                        sfString.add(loadControlObject.getControlName() + "|" + strCurrencyValue.trim());
                                                    } else {
                                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strCurrencyValue.trim());
                                                    }
                                                }
                                                validationFlag = true;
                                                Log.e(TAG, "controlSubmitValidation: Currency - " + strCurrencyValue);
                                            }
                                        }


                                    } else {
                                        currency.setAlertCurrency().setVisibility(View.VISIBLE);
                                        currency.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                        currency.setAlertCurrency().setText(loadControlObject.getMandatoryFieldError());
                                        validationFlag = false;
                                        ImproveHelper.setFocus(currency.getCurrency());
                                        Log.e(TAG, "controlSubmitValidation: No Currency Value ");

                                    }
                                } else {
                                    currency.setAlertCurrency().setVisibility(View.VISIBLE);
                                    currency.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                    currency.setAlertCurrency().setText(loadControlObject.getMandatoryFieldError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(currency.getCurrency());

                                }
                            } else {
                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    strCurrencyValue = strValueFromCtrl;
                                    Log.e(TAG, "controlSubmitValidation: Currency - " + strCurrencyValue);
                                    currency.setAlertCurrency().setVisibility(View.GONE);
                                    BigDecimal bInput = new BigDecimal(strCurrencyValue);

                                    if (loadControlObject.isEnableMaximumAmount() && bInput.compareTo(new BigDecimal(loadControlObject.getMaxAmount())) == 1) {

                                        currency.setAlertCurrency().setVisibility(View.VISIBLE);
                                        currency.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                        currency.setAlertCurrency().setText(loadControlObject.getMaxAmountError());
                                        validationFlag = false;
                                        ImproveHelper.setFocus(currency.getCurrency());
                                        Log.e(TAG, "controlSubmitValidation: No Currency Value ");

                                    } else {

                                        if (loadControlObject.isEnableMinimumAmount() && (new BigDecimal(loadControlObject.getMinAmount()).compareTo(bInput) == 1)) {
                                            currency.setAlertCurrency().setVisibility(View.VISIBLE);
                                            currency.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            currency.setAlertCurrency().setText(loadControlObject.getMinAmountError());
                                            validationFlag = false;
                                            ImproveHelper.setFocus(currency.getCurrency());
                                            Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");

                                        } else {
                                            if (checkTableColumns(loadControlObject.getControlName())) {
                                                if (isSubForm) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + strCurrencyValue.trim());
                                                } else {
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strCurrencyValue.trim());
                                                }
                                            }

                                        }
                                    }
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                            }
                        } else {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                strCurrencyValue = strValueFromCtrl;
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strCurrencyValue.trim());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strCurrencyValue.trim());
                                    }
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                currency.setAlertCurrency().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                currency.setAlertCurrency().setVisibility(View.VISIBLE);
                                currency.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                currency.setAlertCurrency().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(currency.getCurrency());

                            }
                        }
                        break;

                    case CONTROL_TYPE_DYNAMIC_LABEL:
                        DynamicLabel dynamicLabel = (DynamicLabel) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if(isSubForm){
                            isControlVisible  = !dynamicLabel.getControlObject().isInvisible();
                            strValueFromCtrl  = dynamicLabel.getControlObject().getText();
                        }
                        if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                            }
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                        }

                        break;
                    case CONTROL_TYPE_IMAGE:
                        Image image = (Image) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if(isSubForm){
                            isControlVisible  = !image.getControlObject().isInvisible();
                            strValueFromCtrl  = image.getControlObject().getText();
                        }

                        if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                            }
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                        }

                        break;
                    case CONTROL_TYPE_BUTTON:
                        Button button = (Button) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if(isSubForm){
                            isControlVisible  = !button.getControlObject().isInvisible();
                            strValueFromCtrl  = button.getControlObject().getText();
                        }
                        if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                            }
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                        }

                        break;
                    case CONTROL_TYPE_CALENDAR_EVENT:
                        CalendarEventControl calendarEventControl = (CalendarEventControl) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if(isSubForm){
                            isControlVisible  = !calendarEventControl.getControlObject().isInvisible();
                            strValueFromCtrl  = calendarEventControl.getControlObject().getText();
                        }

                        if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                            }
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                        }

                        break;
                    case CONTROL_TYPE_MAP:
                        MapControl mapControl = (MapControl) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if(isSubForm){
                            isControlVisible  = !mapControl.getControlObject().isInvisible();
                            strValueFromCtrl  = mapControl.getControlObject().getText();
                            strValueIdFromCtrl  = mapControl.getControlObject().getMapViewType();
                        }else{
                            strValueIdFromCtrl  = loadControlObject.getMapViewType();
                        }

                        if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                            validationFlag = true;
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + strValueFromCtrl.trim());
                                sfString.add(loadControlObject.getControlName() + "_Type" + "| " + strValueIdFromCtrl);
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + strValueFromCtrl.trim());
                                stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "| " + strValueIdFromCtrl);
                            }
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + "");
                                sfString.add(loadControlObject.getControlName() + "_Type" + "| " + "");
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + "");
                                stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "| " + "");
                            }
                        }

                        break;
                    case CONTROL_TYPE_GRID_CONTROL:
                        GridControl gridView = (GridControl) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if (gridView == null) {

                        } else {
                            validationFlag = gridView.formValidatedGrid(gridView.getSubFormView(), gridView, true);
                            gridView.getSubFormString();
                            Log.d(TAG, "controlSubmitValidationSubForm: " + gridView.getSubFormString());
                        }


                        break;
                    case CONTROL_TYPE_SUBFORM:

                        SubformView subformView = (SubformView) List_ControlClassObjects.get(loadControlObject.getControlName());

                        if (subformView == null) {

                        } else {
                            validationFlag = subformView.formValidated(subformView.getSubFormView(), subformView, false);
                            subformView.getSubFormString();
                            Log.d(TAG, "controlSubmitValidationSubForm: " + subformView.getSubFormString());
                        }


                        break;

                    case CONTROL_TYPE_GPS:

                        Gps_Control controlGPS = (Gps_Control) List_ControlClassObjects.get(loadControlObject.getControlName());
                        List<LatLng> latLngListMain =  null;
                        if(isSubForm){
                            isControlVisible  = !controlGPS.getControlObject().isInvisible();
                            strValueFromCtrl  = controlGPS.getControlObject().getGpsType();
                            latLngListMain  = controlGPS.getControlObject().getLatLngListItems();
                        }else{
                            strValueFromCtrl  = loadControlObject.getGpsType();
                            latLngListMain = loadControlObject.getLatLngListItems();
                        }
                        if (isControlVisible) {
//                if (!loadControlObject.isInvisible()) {
                            if (loadControlObject.isNullAllowed()) {
//                                if (controlGPS.getLatLngList().size() > 0) {
                                if (latLngListMain != null && latLngListMain.size() > 0) {
                                    controlGPS.setAlertGPS().setVisibility(View.GONE);
                                    if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName() + "_Coordinates")) {
                                        if (isSubForm) {
                                            String GPSSTR = "";
                                            List<LatLng> latLngList = latLngListMain;
                                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty() && strValueFromCtrl.equals("Single point GPS")) {
                                                LatLng latlang = latLngList.get(0);
                                                GPSSTR = latlang.latitude + "$" + latlang.longitude;
                                            } else {
                                                for (int i = 0; i < latLngListMain.size(); i++) {
                                                    LatLng latlang = latLngList.get(i);
                                                    GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                                }
                                            }
                                            GPSSTR = GPSSTR.startsWith("^") ? GPSSTR.substring(1) : GPSSTR;
                                            sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
//                                            sfString.add(loadControlObject.getControlName() + "_Type" + "| " + controlGPS.getGPS_Type());
                                            sfString.add(loadControlObject.getControlName() + "_Type" + "|" + strValueFromCtrl);

                                        } else {
                                            String GPSSTR = "";
                                            List<LatLng> latLngList = latLngListMain;
                                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty() && strValueFromCtrl.equals("Single point GPS")) {
                                                LatLng latlang = latLngList.get(0);
                                                GPSSTR = latlang.latitude + "$" + latlang.longitude;
                                            } else {
                                                for (int i = 0; i < latLngListMain.size(); i++) {
                                                    LatLng latlang = latLngList.get(i);
                                                    GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                                }
                                            }
                                            GPSSTR = GPSSTR.startsWith("^") ? GPSSTR.substring(1) : GPSSTR;

                                            stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                            stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + strValueFromCtrl);
                                        }
                                    }

                                    validationFlag = true;
                                } else {
                                    if (!controlGPS.isFromQueryFilter()) {
                                        controlGPS.setAlertGPS().setVisibility(View.VISIBLE);
                                        controlGPS.setAlertGPS().setText(loadControlObject.getMandatoryFieldError());
                                        validationFlag = false;
                                        ImproveHelper.setFocus(controlGPS.getControlGPSView());
                                    } else {
                                        validationFlag = true;
                                    }

                                }
                            } else {
                                if (latLngListMain != null && latLngListMain.size() != 0) {
                                    if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName() + "_Coordinates")) {
                                        if (isSubForm) {
                                            String GPSSTR = "";
                                            List<LatLng> latLngList = latLngListMain;
                                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty() && strValueFromCtrl.equals("Single point GPS")) {
                                                LatLng latlang = latLngList.get(0);
                                                GPSSTR = latlang.latitude + "$" + latlang.longitude;
                                            } else {
                                                for (int i = 0; i < latLngListMain.size(); i++) {
                                                    LatLng latlang = latLngList.get(i);
                                                    GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                                }
                                            }
                                            GPSSTR = GPSSTR.startsWith("^") ? GPSSTR.substring(1) : GPSSTR;
                                            sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                            sfString.add(loadControlObject.getControlName() + "_Type" + "|" + strValueFromCtrl);

                                        } else { //nk gps
                                            String GPSSTR = "";
                                            List<LatLng> latLngList = latLngListMain;
                                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty() && strValueFromCtrl.equals("Single point GPS")) {
                                                LatLng latlang = latLngList.get(0);
                                                GPSSTR = latlang.latitude + "$" + latlang.longitude;
                                            } else {
                                                for (int i = 0; i < latLngListMain.size(); i++) {
                                                    LatLng latlang = latLngList.get(i);
                                                    GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                                }
                                            }

                                            GPSSTR = GPSSTR.startsWith("^") ? GPSSTR.substring(1) : GPSSTR;

                                            stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                            stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + strValueFromCtrl);
                                        }
                                    }
                                } else {
                                    if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName() + "_Coordinates")) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + "");
                                            sfString.add(loadControlObject.getControlName() + "_Type" + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + "");
                                            stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + "");
                                        }
                                    }
                                }
                            }
                        } else {
                            if (latLngListMain.size() != 0) {
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName() + "_Coordinates")) {
                                    if (isSubForm) {
                                        String GPSSTR = "";
                                        List<LatLng> latLngList = latLngListMain;
                                        if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty() && strValueFromCtrl.equals("Single point GPS")) {
                                            LatLng latlang = latLngList.get(0);
                                            GPSSTR = latlang.latitude + "$" + latlang.longitude;
                                        } else {
                                            for (int i = 0; i < latLngListMain.size(); i++) {
                                                LatLng latlang = latLngList.get(i);
                                                GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                            }
                                        }
                                        GPSSTR = GPSSTR.startsWith("^") ? GPSSTR.substring(1) : GPSSTR;
                                        sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                        sfString.add(loadControlObject.getControlName() + "_Type" + "|" + strValueFromCtrl);
                                    } else {
                                        String GPSSTR = "";
                                        List<LatLng> latLngList = latLngListMain;
                                        if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty() && strValueFromCtrl.equals("Single point GPS")) {
                                            LatLng latlang = latLngList.get(0);
                                            GPSSTR = latlang.latitude + "$" + latlang.longitude;
                                        } else {
                                            for (int i = 0; i < latLngListMain.size(); i++) {
                                                LatLng latlang = latLngList.get(i);
                                                GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                            }
                                        }
                                        GPSSTR = GPSSTR.startsWith("^") ? GPSSTR.substring(1) : GPSSTR;
                                        stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                        stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + strValueFromCtrl);
                                    }
                                }
                            } else {
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName() + "_Coordinates")) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + "");
                                        sfString.add(loadControlObject.getControlName() + "_Type" + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + "");
                                        stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + "");
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (latLngListMain.size() == 0) {
                                controlGPS.setAlertGPS().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                controlGPS.setAlertGPS().setVisibility(View.VISIBLE);
                                controlGPS.setAlertGPS().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(controlGPS.getControlGPSView());

                            }
                        }
                        break;
                    case CONTROL_TYPE_BAR_CODE:
                        BarCode barCode = (BarCode) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if(isSubForm){
                            isControlVisible  = !barCode.getControlObject().isInvisible();
                            strValueFromCtrl  = barCode.getControlObject().getText();
                        }
                        if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                            }
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                        }
                        break;
                    case CONTROL_TYPE_QR_CODE:
                        QRCode qrCode = (QRCode) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if(isSubForm){
                            isControlVisible  = !qrCode.getControlObject().isInvisible();
                            strValueFromCtrl  = qrCode.getControlObject().getText();
                        }
                        if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                            }
                        } else {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + "");
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                            }
                        }
                        break;
                    case CONTROL_TYPE_DATA_CONTROL:
                        DataControl dataControl = (DataControl) List_ControlClassObjects.get(loadControlObject.getControlName());
                        String strDataControlId = null;
                        String strDataControlName = null;
                        if(isSubForm){
                            isControlVisible  = !dataControl.getControlObject().isInvisible();
                            isItemSelected  = dataControl.getControlObject().isItemSelected();
                            strValueFromCtrl  = dataControl.getControlObject().getText();
                            strValueIdFromCtrl  = dataControl.getControlObject().getControlValue();
                        }
                        if (isControlVisible) {
                            if (loadControlObject.isNullAllowed()) {

                                if (!isItemSelected) {
                                    dataControl.setAlertDataControl().setVisibility(View.VISIBLE);
                                    dataControl.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                    dataControl.setAlertDataControl().setText(loadControlObject.getMandatoryFieldError());
                                    validationFlag = false;
                                    ImproveHelper.setFocus(dataControl.getDataControl());
                                } else {
                                    validationFlag = true;
                                    dataControl.setAlertDataControl().setVisibility(View.GONE);
                                    strDataControlId = strValueIdFromCtrl;
                                    strDataControlName = strValueFromCtrl;
//                            Log.d(TAG, "controlSubmitValidation" + " - " + strDataControlValue);
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName().trim() + "|" + strDataControlName);
                                            sfString.add(loadControlObject.getControlName().trim() + "_id" + "|" + strDataControlId);

                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName().trim() + "|" + strDataControlName);
                                            stringListSubmit.add(loadControlObject.getControlName().trim() + "_id" + "|" + strDataControlId);
                                        }
                                    }
                                }
                            } else {
                                if (isItemSelected) {
                                    strDataControlId = strValueIdFromCtrl;
                                    strDataControlName = strValueFromCtrl;
//                            Log.d(TAG, "controlSubmitValidation" + " - " + strDataControlValue);
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName().trim() + "|" + strDataControlName);
                                            sfString.add(loadControlObject.getControlName().trim() + "_id" + "|" + strDataControlId);

                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName().trim() + "|" + strDataControlName);
                                            stringListSubmit.add(loadControlObject.getControlName().trim() + "_id" + "|" + strDataControlId);
                                        }
                                    }
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                            sfString.add(loadControlObject.getControlName() + "_id" + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                            stringListSubmit.add(loadControlObject.getControlName() + "_id" + "|" + "");
                                        }
                                    }
                                }
                            }
                        } else {
                            if (isItemSelected) {
                                strDataControlId = strValueIdFromCtrl;
                                strDataControlName = strValueFromCtrl;
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName().trim() + "|" + strDataControlName);
                                        sfString.add(loadControlObject.getControlName().trim() + "_id" + "|" + strDataControlId);
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName().trim() + "|" + strDataControlName);
                                        stringListSubmit.add(loadControlObject.getControlName().trim() + "_id" + "|" + strDataControlId);
                                    }
                                }
                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                        sfString.add(loadControlObject.getControlName() + "_id" + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        stringListSubmit.add(loadControlObject.getControlName() + "_id" + "|" + "");
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (isItemSelected) {
                                dataControl.setAlertDataControl().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                dataControl.setAlertDataControl().setVisibility(View.VISIBLE);
                                dataControl.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                dataControl.setAlertDataControl().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(dataControl.getDataControl());

                            }
                        }
                        break;
                    case CONTROL_TYPE_LiveTracking:
                        if (checkTableColumns(loadControlObject.getControlName())) {
                            stringListSubmit.add(loadControlObject.getControlName() + "|NA");
                        }
                        break;
                    case CONTROL_TYPE_TIME:
                        Time time = (Time) List_ControlClassObjects.get(loadControlObject.getControlName());
                        String strTimeInputValue = null;
                        if(isSubForm){
                            isControlVisible  = !time.getControlObject().isInvisible();
                            strValueFromCtrl  = time.getControlObject().getSelectedTime();
                        }else {
                            strValueFromCtrl = loadControlObject.getSelectedTime();
                        }
                        if (isControlVisible) {
                            if (loadControlObject.isNullAllowed()) {
                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    strTimeInputValue = strValueFromCtrl;
                                    if (loadControlObject.isBeforeCurrentTime()) {
                                        try {

                                            Date date = new Date();
                                            SimpleDateFormat dateFormat = new SimpleDateFormat(loadControlObject.getTimeFormatOptions());
                                            dateFormat.format(date);
                                            System.out.println(dateFormat.format(date));
                                            Log.d(TAG, "ValidationBeforeDate: " + dateFormat.format(date));
                                            if (dateFormat.parse(dateFormat.format(date)).before(dateFormat.parse(strTimeInputValue))) {

                                                validationFlag = false;
                                                time.getTimeErrorMessageText().setVisibility(View.VISIBLE);
                                                time.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                                time.getTimeErrorMessageText().setText(loadControlObject.getBeforeCurrentTimeErrorMessage());
                                                ImproveHelper.setFocus(time.getTimeControlLayout());
                                                Log.d(TAG, "BC_TimeFromBuilderFalse: " + dateFormat.parse(dateFormat.format(date)) + " TimeFromUser: " + dateFormat.parse(strTimeInputValue));

                                            } else {
                                                Log.d(TAG, "BC_TimeFromBuilderTrue: " + dateFormat.parse(dateFormat.format(date)) + " TimeFromUser: " + dateFormat.parse(strTimeInputValue));
                                                validationFlag = true;
                                                if (checkTableColumns(loadControlObject.getControlName())) {
                                                    if (isSubForm) {
                                                        sfString.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                                    } else {
                                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                                    }
                                                }

                                            }

                                        } catch (Exception exception) {
                                            validationFlag = false;
                                            Log.d(TAG, "ValidationBeforeTimeException: " + exception);
                                        }

                                    }
                                    else if (loadControlObject.isAfterCurrentTime()) {

                                        try {

                                            Date date = new Date();
                                            SimpleDateFormat dateFormat = new SimpleDateFormat(loadControlObject.getTimeFormatOptions());
                                            dateFormat.format(date);
                                            System.out.println(dateFormat.format(date));
                                            Log.d(TAG, "ValidationBeforeDate: " + dateFormat.format(date));
                                            if (dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse(strTimeInputValue))) {
//                            System.out.println("Current time is greater than selected");

                                                validationFlag = false;
                                                time.getTimeErrorMessageText().setVisibility(View.VISIBLE);
                                                time.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                                time.getTimeErrorMessageText().setText(loadControlObject.getAfterCurrentTimeErrorMessage());
                                                ImproveHelper.setFocus(time.getTimeControlLayout());
                                                System.out.println("Current time is less than" + loadControlObject.getAfterCurrentTimeErrorMessage());
                                                Log.d(TAG, "AC_TimeFromBuilderFalse: " + dateFormat.parse(dateFormat.format(date))
                                                        + " TimeFromUser: " + dateFormat.parse(strTimeInputValue));

                                            } else {
                                                validationFlag = true;
                                                Log.d(TAG, "AC_TimeFromBuilderTrue: " + dateFormat.parse(dateFormat.format(date))
                                                        + " TimeFromUser: " + dateFormat.parse(strTimeInputValue));

                                                if (checkTableColumns(loadControlObject.getControlName())) {
                                                    if (isSubForm) {
                                                        sfString.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                                    } else {
                                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                                    }
                                                }

                                            }

                                        } catch (Exception exception) {
                                            validationFlag = false;
                                            Log.d(TAG, "ValidationBeforeTimeException: " + exception);
                                        }

                                    }
                                    else if (loadControlObject.isBetweenStartEndTime()) {

//                                boolean isTimeLiesBetween = checkTimeLiesBetween(
//                                        loadControlObject.getBetweenStartTime(),
//                                        loadControlObject.getBetweenEndTime(), strTimeInputValue,loadControlObject.getTimeFormatOptions());
                                        boolean isTimeLiesBetween = betweenTimeValidation(loadControlObject.getTimeFormatOptions(),
                                                loadControlObject.getBetweenStartTime(),
                                                loadControlObject.getBetweenEndTime(), strTimeInputValue);

                                        if (isTimeLiesBetween) {
                                            validationFlag = true;
                                            if (checkTableColumns(loadControlObject.getControlName())) {
                                                if (isSubForm) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                                } else {
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                                }
                                            }

                                        } else {
                                            time.getTimeErrorMessageText().setVisibility(View.VISIBLE);
                                            time.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            time.getTimeErrorMessageText().setText(loadControlObject.getBetweenStartEndTimeErrorMessage());
                                            ImproveHelper.setFocus(time.getTimeControlLayout());
                                            validationFlag = false;
                                        }
                                    }
                                    else {
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + strTimeInputValue);
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strTimeInputValue);
                                            }
                                        }
                                    }
                                } else {
                                    time.getTimeErrorMessageText().setVisibility(View.VISIBLE);
                                    time.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                    time.getTimeErrorMessageText().setText(loadControlObject.getMandatoryTimeErrorMessage());
                                    ImproveHelper.setFocus(time.getTimeControlLayout());
                                    validationFlag = false;
                                }
                            } else {

                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    strTimeInputValue = strValueFromCtrl;
                                    if (loadControlObject.isBeforeCurrentTime()) {
//
                                        try {

                                            Date date = new Date();
                                            SimpleDateFormat dateFormat = new SimpleDateFormat(loadControlObject.getTimeFormatOptions());
                                            dateFormat.format(date);
                                            System.out.println(dateFormat.format(date));
                                            Log.d(TAG, "ValidationBeforeDate: " + dateFormat.format(date));
                                            if (dateFormat.parse(dateFormat.format(date)).before(dateFormat.parse(strTimeInputValue))) {

                                                validationFlag = false;
                                                time.getTimeErrorMessageText().setVisibility(View.VISIBLE);
                                                time.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                                time.getTimeErrorMessageText().setText(loadControlObject.getBeforeCurrentTimeErrorMessage());
                                                ImproveHelper.setFocus(time.getTimeControlLayout());
                                                Log.d(TAG, "BC_TimeFromBuilderFalse: " + dateFormat.parse(dateFormat.format(date)) + " TimeFromUser: " + dateFormat.parse(strTimeInputValue));

                                            }
                                            else {
                                                Log.d(TAG, "BC_TimeFromBuilderTrue: " + dateFormat.parse(dateFormat.format(date)) + " TimeFromUser: " + dateFormat.parse(strTimeInputValue));
                                                validationFlag = true;
                                                if (checkTableColumns(loadControlObject.getControlName())) {
                                                    if (isSubForm) {
                                                        sfString.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                                    } else {
                                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                                    }
                                                }
                                            }
                                        } catch (Exception exception) {
                                            validationFlag = false;
                                            Log.d(TAG, "ValidationBeforeTimeException: " + exception);
                                        }

                                    }
                                    else if (loadControlObject.isAfterCurrentTime()) {

                                        try {

                                            Date date = new Date();
                                            SimpleDateFormat dateFormat = new SimpleDateFormat(loadControlObject.getTimeFormatOptions());
                                            dateFormat.format(date);
                                            System.out.println(dateFormat.format(date));
                                            Log.d(TAG, "ValidationBeforeDate: " + dateFormat.format(date));
                                            if (dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse(strTimeInputValue))) {
//                            System.out.println("Current time is greater than selected");

                                                validationFlag = false;
                                                time.getTimeErrorMessageText().setVisibility(View.VISIBLE);
                                                time.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                                time.getTimeErrorMessageText().setText(loadControlObject.getAfterCurrentTimeErrorMessage());
                                                ImproveHelper.setFocus(time.getTimeControlLayout());
                                                System.out.println("Current time is less than" + loadControlObject.getAfterCurrentTimeErrorMessage());
                                                Log.d(TAG, "AC_TimeFromBuilderFalse: " + dateFormat.parse(dateFormat.format(date))
                                                        + " TimeFromUser: " + dateFormat.parse(strTimeInputValue));

                                            } else {
                                                validationFlag = true;
                                                Log.d(TAG, "AC_TimeFromBuilderTrue: " + dateFormat.parse(dateFormat.format(date))
                                                        + " TimeFromUser: " + dateFormat.parse(strTimeInputValue));

                                                if (checkTableColumns(loadControlObject.getControlName())) {
                                                    if (isSubForm) {
                                                        sfString.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                                    } else {
                                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                                    }
                                                }

                                            }

                                        } catch (Exception exception) {
                                            validationFlag = false;
                                            Log.d(TAG, "ValidationBeforeTimeException: " + exception);
                                        }

                                    }
                                    else if (loadControlObject.isBetweenStartEndTime()) {


                                        boolean isTimeLiesBetween = betweenTimeValidation(loadControlObject.getTimeFormatOptions(),
                                                loadControlObject.getBetweenStartTime(),
                                                loadControlObject.getBetweenEndTime(), strTimeInputValue);

                                        if (isTimeLiesBetween) {
                                            validationFlag = true;
                                            if (checkTableColumns(loadControlObject.getControlName())) {
                                                if (isSubForm) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                                } else {
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                                }
                                            }

                                        } else {
                                            time.getTimeErrorMessageText().setVisibility(View.VISIBLE);
                                            time.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            time.getTimeErrorMessageText().setText(loadControlObject.getBetweenStartEndTimeErrorMessage());
                                            ImproveHelper.setFocus(time.getTimeControlLayout());
                                            validationFlag = false;
                                        }
                                    }
                                    else {
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + strTimeInputValue);
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strTimeInputValue);
                                            }
                                        }
                                    }
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                                //
                            }
                        }
                        else { // Time Invisible Case
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                strTimeInputValue = strValueFromCtrl;
                                if (loadControlObject.isBeforeCurrentTime()) {
//
                                    try {

                                        Date date = new Date();
                                        SimpleDateFormat dateFormat = new SimpleDateFormat(loadControlObject.getTimeFormatOptions());
                                        dateFormat.format(date);
                                        System.out.println(dateFormat.format(date));
                                        Log.d(TAG, "ValidationBeforeDate: " + dateFormat.format(date));
                                        if (dateFormat.parse(dateFormat.format(date)).before(dateFormat.parse(strTimeInputValue))) {

                                            validationFlag = false;
                                            time.getTimeErrorMessageText().setVisibility(View.VISIBLE);
                                            time.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            time.getTimeErrorMessageText().setText(loadControlObject.getBeforeCurrentTimeErrorMessage());
                                            ImproveHelper.setFocus(time.getTimeControlLayout());
                                            Log.d(TAG, "BC_TimeFromBuilderFalse: " + dateFormat.parse(dateFormat.format(date)) + " TimeFromUser: " + dateFormat.parse(strTimeInputValue));

                                        } else {
                                            Log.d(TAG, "BC_TimeFromBuilderTrue: " + dateFormat.parse(dateFormat.format(date)) + " TimeFromUser: " + dateFormat.parse(strTimeInputValue));
                                            validationFlag = true;
                                            if (checkTableColumns(loadControlObject.getControlName())) {
                                                if (isSubForm) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                                } else {
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                                }
                                            }

                                        }

                                    } catch (Exception exception) {
                                        validationFlag = false;
                                        Log.d(TAG, "ValidationBeforeTimeException: " + exception);
                                    }


                                }
                                else if (loadControlObject.isAfterCurrentTime()) {

                                    try {

                                        Date date = new Date();
                                        SimpleDateFormat dateFormat = new SimpleDateFormat(loadControlObject.getTimeFormatOptions());
                                        dateFormat.format(date);
                                        System.out.println(dateFormat.format(date));
                                        Log.d(TAG, "ValidationBeforeDate: " + dateFormat.format(date));
                                        if (dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse(strTimeInputValue))) {
//                            System.out.println("Current time is greater than selected");

                                            validationFlag = false;
                                            time.getTimeErrorMessageText().setVisibility(View.VISIBLE);
                                            time.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            time.getTimeErrorMessageText().setText(loadControlObject.getAfterCurrentTimeErrorMessage());
                                            ImproveHelper.setFocus(time.getTimeControlLayout());
                                            System.out.println("Current time is less than" + loadControlObject.getAfterCurrentTimeErrorMessage());
                                            Log.d(TAG, "AC_TimeFromBuilderFalse: " + dateFormat.parse(dateFormat.format(date))
                                                    + " TimeFromUser: " + dateFormat.parse(strTimeInputValue));

                                        } else {
                                            validationFlag = true;
                                            Log.d(TAG, "AC_TimeFromBuilderTrue: " + dateFormat.parse(dateFormat.format(date))
                                                    + " TimeFromUser: " + dateFormat.parse(strTimeInputValue));

                                            if (checkTableColumns(loadControlObject.getControlName())) {
                                                if (isSubForm) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                                } else {
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                                }
                                            }

                                        }

                                    } catch (Exception exception) {
                                        validationFlag = false;
                                        Log.d(TAG, "ValidationBeforeTimeException: " + exception);
                                    }

                                }
                                else if (loadControlObject.isBetweenStartEndTime()) {

                                    boolean isTimeLiesBetween = betweenTimeValidation(loadControlObject.getTimeFormatOptions(),
                                            loadControlObject.getBetweenStartTime(),
                                            loadControlObject.getBetweenEndTime(), strTimeInputValue);

                                    if (isTimeLiesBetween) {
                                        validationFlag = true;
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                            }
                                        }

                                    } else {
                                        time.getTimeErrorMessageText().setVisibility(View.VISIBLE);
                                        time.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                        time.getTimeErrorMessageText().setText(loadControlObject.getBetweenStartEndTimeErrorMessage());
                                        ImproveHelper.setFocus(time.getTimeControlLayout());
                                        validationFlag = false;
                                    }
                                }
                            } else {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strTimeInputValue);
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strTimeInputValue);
                                    }
                                }
                            }
                        }

                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                time.getTimeErrorMessageText().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                time.getTimeErrorMessageText().setVisibility(View.VISIBLE);
                                time.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                time.getTimeErrorMessageText().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(time.getTimeControlLayout());
                            }
                        }
                        break;
                    case CONTROL_TYPE_USER:
                        UserControl userControl = (UserControl) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if(isSubForm){
                            isControlVisible  = !userControl.getControlObject().isInvisible();
                            strValueFromCtrl  = userControl.getControlObject().getText();
                            strValueIdFromCtrl  = userControl.getControlObject().getControlValue();
                        }
                        if (isControlVisible) {
                            if (loadControlObject.isNullAllowed()) {
                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty() ) {
                                    if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                            stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                        }
                                    }

                                } else {
                                    userControl.setAlertText().setVisibility(View.VISIBLE);
                                    userControl.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                    userControl.setAlertText().setText(loadControlObject.getMandatoryFieldError());
                                    ImproveHelper.setFocus(userControl.getUserControlView());
                                    validationFlag = false;
                                }
                            } else {
                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty() ) {
                                    if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                            stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                        }
                                    }
                                } else {
                                    if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                            stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                                        }
                                    }
                                }
                            }
                        } else {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                        stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                    }
                                }
                            } else {
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                                    }
                                }
                            }

                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                userControl.setAlertText().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                userControl.setAlertText().setVisibility(View.VISIBLE);
                                userControl.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                userControl.setAlertText().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(userControl.getUserControlView());
                            }
                        }

                        break;
                    case CONTROL_TYPE_POST:
                        PostControl postControl = (PostControl) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if(isSubForm){
                            isControlVisible  = !postControl.getControlObject().isInvisible();
                            strValueFromCtrl  = postControl.getControlObject().getText();
                            strValueIdFromCtrl  = postControl.getControlObject().getControlValue();
                        }
                        if (isControlVisible) {
                            if (loadControlObject.isNullAllowed()) {
                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                            stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                        }
                                    }
                                } else {
                                    postControl.setAlertText().setVisibility(View.VISIBLE);
                                    postControl.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                    postControl.setAlertText().setText(loadControlObject.getMandatoryFieldError());
                                    ImproveHelper.setFocus(postControl.getPostControlView());
                                    validationFlag = false;
                                }
                            } else {
                                if (strValueFromCtrl != null &&  !strValueFromCtrl.isEmpty() ) {
                                    if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                            stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                        }
                                    }
                                } else {
                                    if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                            stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                                        }
                                    }
                                }
                            }
                        } else {
                            if (  strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl);
                                        stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + strValueIdFromCtrl);
                                    }
                                }
                            } else {
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + "");
                                    }
                                }
                            }
                        }
                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl != null &&  !strValueFromCtrl.isEmpty() ) {
                                postControl.setAlertText().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                postControl.setAlertText().setVisibility(View.VISIBLE);
                                postControl.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                postControl.setAlertText().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(postControl.getPostControlView());
                            }
                        }
                        break;
                    case CONTROL_TYPE_AUTO_COMPLETION:

                        AutoCompletionControl autoCompletionControl = (AutoCompletionControl) List_ControlClassObjects.get(loadControlObject.getControlName());
                        String strACValue = null;

                        if (autoCompletionControl != null) {
                            if(isSubForm){
                                isControlVisible  = !autoCompletionControl.getControlObject().isInvisible();
                                strValueFromCtrl  = autoCompletionControl.getControlObject().getText();
                            }

                            //if (textInput.getTextInputView().getVisibility() == View.VISIBLE) {
                            if (isControlVisible) {
//                if (!loadControlObject.isInvisible()) {
                                if (loadControlObject.isNullAllowed()) {
                                    if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                        strACValue = strValueFromCtrl;
                                        if (strACValue.contentEquals(context.getResources().getString(R.string.tap_here))) {
                                            strACValue = "";
                                        }
                                        if (strACValue.length() > 0) {
                                            autoCompletionControl.setAlertAutoCompleteText().setVisibility(View.GONE);
                                            if (checkTableColumns(loadControlObject.getControlName())) {
                                                if (isSubForm) {
                                                    sfString.add(loadControlObject.getControlName() + "|" + strACValue);
                                                } else {
                                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strACValue.trim());
                                                }
                                            }
                                            validationFlag = true;
                                            Log.e(TAG, "controlSubmitValidation: AutoCompleteText - " + CONTROL_TYPE_AUTO_COMPLETION + " - " + strACValue);
                                        } else {
                                            autoCompletionControl.setAlertAutoCompleteText().setVisibility(View.VISIBLE);
                                            autoCompletionControl.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            autoCompletionControl.setAlertAutoCompleteText().setText(loadControlObject.getMandatoryFieldError());
                                            ImproveHelper.setFocus(autoCompletionControl.getAutoCompletionControlView());
                                            validationFlag = false;
                                        }
                                    } else {
                                        autoCompletionControl.setAlertAutoCompleteText().setVisibility(View.VISIBLE);
                                        autoCompletionControl.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                        autoCompletionControl.setAlertAutoCompleteText().setText(loadControlObject.getMandatoryFieldError());
                                        ImproveHelper.setFocus(autoCompletionControl.getAutoCompletionControlView());
                                        validationFlag = false;
                                    }
                                } else {
                                    if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                        strACValue = strValueFromCtrl;
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + strACValue.trim());
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strACValue.trim());
                                            }
                                        }
                                    } else {
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                            }
                                        }
                                    }
                                }
                            } else { // AutoComplete Input Invisible Case
                                if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                    strACValue = strValueFromCtrl;
                                    if (strACValue.length() > 0) {
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + strACValue);
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + strACValue.trim());
                                            }
                                        }
                                    }

                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                            }
                        }

                        if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                            if (strValueFromCtrl != null && !strValueFromCtrl.isEmpty()) {
                                strACValue = strValueFromCtrl;
                                if (strACValue.contentEquals(context.getResources().getString(R.string.tap_here))) {
                                    strACValue = "";
                                }
                            }
                            if (strACValue.length() > 0) {
                                autoCompletionControl.setAlertAutoCompleteText().setVisibility(View.GONE);
                                validationFlag = true;
                            } else {
                                String alertMessage = loadControlObject.getControlName() + " is required";
                                autoCompletionControl.setAlertAutoCompleteText().setVisibility(View.VISIBLE);
                                autoCompletionControl.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                autoCompletionControl.setAlertAutoCompleteText().setText(alertMessage);
                                validationFlag = false;
                                ImproveHelper.setFocus(autoCompletionControl.getAutoCompletionControlView());
                            }
                        }

                        break;
                    case CONTROL_TYPE_COUNT_DOWN_TIMER:
                        CountDownTimerControl countDownTimerControl = (CountDownTimerControl) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if (checkTableColumns(loadControlObject.getControlName())) {
                            strValueFromCtrl = countDownTimerControl.getValue();
                            if (countDownTimerControl != null && strValueFromCtrl != null) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                                }
                            } else {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                }}
                        }

                        break;
                    case CONTROL_TYPE_COUNT_UP_TIMER:
                        CountUpTimerControl countUpTimerControl = (CountUpTimerControl) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if (checkTableColumns(loadControlObject.getControlName())) {
                            strValueFromCtrl = countUpTimerControl.getValue();
                            if (countUpTimerControl != null && strValueFromCtrl != null) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                                }
                            } else {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }}

                        break;
                    case CONTROL_TYPE_VIEWFILE:
                        ViewFileControl viewFileControl = (ViewFileControl) List_ControlClassObjects.get(loadControlObject.getControlName());
                        if (checkTableColumns(loadControlObject.getControlName())) {
                            if(isSubForm){
                                strValueFromCtrl = viewFileControl.getControlObject().getText();
                            }
//                        strValueFromCtrl = viewFileControl.getFileLink();
                            if (viewFileControl != null && strValueFromCtrl != null) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strValueFromCtrl.trim());
                                }
                            } else {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + "");
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                }
                            }}

                        break;
                }
            }
        }catch (Exception e){
            Log.getStackTraceString(e);
        }

        return validationFlag;
    }

    private String getEncryptedPassword(String strPasswordValue, String encryptionTypeId) {
        String encryptedPassword = "";
        try {

            String salt = ImproveHelper.getSalt();
            if (encryptionTypeId.equalsIgnoreCase("1")) {
//                encryptedPassword = ImproveHelper.get_SHA_1_SecurePassword(strPasswordValue,salt);
                encryptedPassword = ImproveHelper.get_SHA_1_SecurePassword(strPasswordValue);
            } else if (encryptionTypeId.equalsIgnoreCase("2")) {
//                encryptedPassword =  ImproveHelper.get_SHA_256_SecurePassword(strPasswordValue,salt);
                encryptedPassword = ImproveHelper.get_SHA_256_SecurePassword(strPasswordValue);
            } else if (encryptionTypeId.equalsIgnoreCase("3")) {
                encryptedPassword = ImproveHelper.get_MD5_SecurePassword(strPasswordValue);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedPassword;
    }

    private String checkPasswordPolicy(String password, ControlObject controlObject) {
        String validPassword = null;
        String specialCharRegex = ".*[@#!$%^&+=_*].*";
        String UpperCaseRegex = ".*[A-Z].*";
        String LowerCaseRegex = ".*[a-z].*";
        String NumberRegex = ".*[0-9].*";
        List<String> passwordPolicyIds = controlObject.getPasswordPolicyIds();
        List<String> passwordPolicy = controlObject.getPasswordPolicy();

        if (passwordPolicyIds.contains("1") && !password.matches(LowerCaseRegex)) {
            validPassword = "Password should contain a minimum of 1 lower case letter [a-z]";
        } else if (passwordPolicyIds.contains("2") && !password.matches(UpperCaseRegex)) {
            validPassword = "Password should contain a minimum of 1 upper case letter [A-Z]";
        } else if (passwordPolicyIds.contains("3") && !password.matches(NumberRegex)) {
            validPassword = "Password should contain a minimum of 1 numeric character [0-9]";
        } else if (passwordPolicyIds.contains("4") && !password.matches(specialCharRegex)) {
            validPassword = "Password should contain a minimum of 1 special character";
        }
        return validPassword;
    }

    private boolean checkTableColumns(String columnName) {
        if (tableSettingsType != null && tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table))) {
            return true;
        } else {
            return list_TableColums != null && list_TableColums.contains(columnName);
        }

    }

    public List<String> getDataCollectionString() {

        return stringListSubmit;
    }

    public List<HashMap<String, String>> getStringListFiles() {
        return stringListFiles;
    }

    public boolean betweenTimeValidation(String strTimeFormat, String strStartTime, String endTime, String fromSomeRandomTime) {
        boolean isTimeLiesBetween = false;
        try {
//            String string1 = "20:11:13";
            String string1 = strStartTime;
            Date time1 = new SimpleDateFormat(strTimeFormat).parse(string1);
            java.util.Calendar calendar1 = java.util.Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(java.util.Calendar.DATE, 1);

//            String string2 = "14:49:00";
            String string2 = endTime;
            Date time2 = new SimpleDateFormat(strTimeFormat).parse(string2);
            java.util.Calendar calendar2 = java.util.Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(java.util.Calendar.DATE, 1);

//            String someRandomTime = "01:00:00";
            String someRandomTime = fromSomeRandomTime;
//            Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);
            Date d = new SimpleDateFormat(strTimeFormat).parse(someRandomTime);
            java.util.Calendar calendar3 = java.util.Calendar.getInstance();
            calendar3.setTime(d);
            calendar3.add(java.util.Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                //check whether the current time is between 14:49:00 and 20:11:13.
                isTimeLiesBetween = true;
                System.out.println(true);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isTimeLiesBetween;
    }


   /* @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkTimeLiesBetween(String startTime, String endTime, String checkTime,String strFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(strFormat, Locale.US);
        LocalTime startLocalTime = LocalTime.parse(startTime, formatter);
        LocalTime endLocalTime = LocalTime.parse(endTime, formatter);
        LocalTime checkLocalTime = LocalTime.parse(checkTime, formatter);

        boolean isInBetween = false;
        if (endLocalTime.isAfter(startLocalTime)) {
            if (startLocalTime.isBefore(checkLocalTime) && endLocalTime.isAfter(checkLocalTime)) {
                isInBetween = true;
            }
        } else if (checkLocalTime.isAfter(startLocalTime) || checkLocalTime.isBefore(endLocalTime)) {
            isInBetween = true;
        }

        if (isInBetween) {
            System.out.println("Is in between!");
        } else {
            System.out.println("Is not in between!");
        }
        return isInBetween;
    }
*/

}
