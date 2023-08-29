package com.bhargo.user.utils;

import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUDIO_PLAYER;
import static com.bhargo.user.utils.AppConstants.CONTROL_TYPE_AUTO_COMPLETION;
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

public class DataCollectionControlsValidationCopy {

    //=======Load_Onchange_EventObjects================

    private static final String TAG = "ControlsValidation";
    private final String emailPatternMain = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public List<String> subFormMainAppendString = new ArrayList<>();
    Context context;
    LinkedHashMap<String, Object> List_ControlClassObjects;
    AllControls allControls;
    List<String> stringListSubmit = new ArrayList<>();
    List<HashMap<String, String>> stringListFiles = new ArrayList<>();
    List<String> list_TableColums;
    List<String> mandatoryColumns;
    ImproveHelper improveHelper;
    String tableSettingsType;


    public DataCollectionControlsValidationCopy(Context context, LinkedHashMap<String, Object> List_ControlClassObjects, List<String> list_TableColums, String tableSettingsType, List<String> mandatoryColumns) {
        this.context = context;
        this.tableSettingsType = tableSettingsType;
        this.List_ControlClassObjects = List_ControlClassObjects;
        this.list_TableColums = list_TableColums;
        this.mandatoryColumns = mandatoryColumns;

    }


    /*@RequiresApi(api = Build.VERSION_CODES.O)*/
    public boolean controlSubmitValidation(ControlObject loadControlObject, boolean isSubForm, List<String> sfString, List<HashMap<String, String>> sfFilesString, String subFormId, int subFormRowId, int controlPos) {

        boolean validationFlag = true;
        Log.d(TAG, "XmlHelperControlObject: " + loadControlObject.getControlType());

        if (List_ControlClassObjects.get(loadControlObject.getControlName()) == null) {

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
                if (isSubForm) {
                    sfString.add(loadControlObject.getControlName() + "|" + "");
                } else {
                    stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                }
            }

        } else {
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
                        if (textInput != null) {
                            if (textInput.getTextInputView().getVisibility() == View.VISIBLE) {
//                            if (textInput.visable == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                                if (loadControlObject.isNullAllowed()) {
                                    if (textInput.getTextInputValue() != null && !textInput.getTextInputValue().isEmpty()) {
                                        strTextInputValue = textInput.getTextInputValue();
                                        if (strTextInputValue.contentEquals(context.getResources().getString(R.string.tap_here_to_scan_qr_code))
                                                || strTextInputValue.contentEquals(context.getResources().getString(R.string.tap_here_to_scan_bar_code))) {
                                            strTextInputValue = "";
                                        }
                                        if (strTextInputValue.length() > 0) {
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
                                            textInput.setAlertTextInput().setVisibility(View.VISIBLE);
                                            textInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            textInput.setAlertTextInput().setText(loadControlObject.getMandatoryFieldError());
                                            ImproveHelper.setFocus(textInput.getTextInputView());
                                            validationFlag = false;
                                        }
                                    } else if (!textInput.gettap_text().getText().toString().isEmpty() && ((loadControlObject.isCurrentLocation() || loadControlObject.isReadFromQRCode() || loadControlObject.isReadFromQRCode()) && !textInput.gettap_text().getText().toString().contentEquals(context.getResources().getString(R.string.tap_here_to_scan_qr_code)) && !textInput.gettap_text().getText().toString().contentEquals(context.getResources().getString(R.string.tap_here_to_scan_bar_code)))) {
                                        if (checkTableColumns(loadControlObject.getControlName())) {
                                            if (isSubForm) {
                                                sfString.add(loadControlObject.getControlName() + "|" + textInput.gettap_text().getText().toString());
                                            } else {
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + textInput.gettap_text().getText().toString().trim());
                                            }
                                        }
                                    } else {
                                        textInput.setAlertTextInput().setVisibility(View.VISIBLE);
                                        textInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                        textInput.setAlertTextInput().setText(loadControlObject.getMandatoryFieldError());
                                        ImproveHelper.setFocus(textInput.getTextInputView());
                                        validationFlag = false;
                                    }
                                } else {
                                    if (textInput.getTextInputValue() != null && !textInput.getTextInputValue().isEmpty()) {
                                        strTextInputValue = textInput.getTextInputValue();
                                        if (checkTableColumns(loadControlObject.getControlName())) {
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
                            } else if (textInput.getTextInputView().getVisibility() == View.GONE) { // Text Input Invisible Case
                                if (textInput.getTextInputValue() != null && !textInput.getTextInputValue().isEmpty()) {
                                    strTextInputValue = textInput.getTextInputValue();
                                    if (strTextInputValue.length() > 0) {
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
                        if (textInput.getTextInputValue() != null && !textInput.getTextInputValue().isEmpty() && !textInput.getTextInputValue().trim().matches("")) {
                            textInput.setAlertTextInput().setVisibility(View.GONE);
                            validationFlag = true;
                        } else {
                            textInput.setAlertTextInput().setVisibility(View.VISIBLE);
                            String alertMessage = loadControlObject.getControlName() + " is required";
                            textInput.setAlertTextInput().setText(alertMessage);
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
                        if (numericInput.getNumericInputView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                            if (loadControlObject.isNullAllowed()) {

                                if (numericInput.getNumericValue() != null && !numericInput.getNumericValue().isEmpty()) {
                                    strNumericValue = numericInput.getNumericValue();
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

                                        } else {

                                            if (loadControlObject.isEnableLowerLimit() && Integer.parseInt(strNumericValue) < Integer.parseInt(loadControlObject.getLowerLimit())/* && (new BigDecimal(loadControlObject.getLowerLimit()).compareTo(bInput) == 1)*/) {
                                                numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                                numericInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                                numericInput.setAlertNumeric().setText(loadControlObject.getLowerLimitErrorMesage());
                                                validationFlag = false;
                                                ImproveHelper.setFocus(numericInput.getNumericInputView());
                                                Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                            } else {

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
                                if (numericInput.getNumericValue() != null && !numericInput.getNumericValue().isEmpty()) {
                                    strNumericValue = numericInput.getNumericValue();
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

                                    } else {

                                        if (loadControlObject.isEnableLowerLimit() && Integer.parseInt(strNumericValue) < Integer.parseInt(loadControlObject.getLowerLimit())/*&& (new BigDecimal(loadControlObject.getLowerLimit()).compareTo(bInput) == 1)*/) {
                                            numericInput.setAlertNumeric().setVisibility(View.VISIBLE);
                                            numericInput.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                            numericInput.setAlertNumeric().setText(loadControlObject.getLowerLimitErrorMesage());
                                            validationFlag = false;
                                            ImproveHelper.setFocus(numericInput.getNumericInputView());
                                            Log.e(TAG, "controlSubmitValidation: No NumericInput Value ");
                                        } else {

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
                                        }
                                    }
                                } else {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                        }
                                    }
                                }
                            }
                        } else if (numericInput.getNumericInputView().getVisibility() == View.GONE) {
                            if (numericInput.getNumericValue() != null && !numericInput.getNumericValue().isEmpty()) {
                                strNumericValue = numericInput.getNumericValue();
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
                        if (numericInput.getNumericValue() != null && !numericInput.getNumericValue().isEmpty() && !numericInput.getNumericValue().trim().matches("")) {
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
                        if (phoneInput.getPhoneView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                            if (loadControlObject.isNullAllowed()) {
                                if (phoneInput.getPhoneValue() != null && !phoneInput.getPhoneValue().isEmpty()) {
                                    strPhoneValue = phoneInput.getPhoneValue();
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
                                if (phoneInput.getPhoneValue() != null && !phoneInput.getPhoneValue().isEmpty()) {
                                    strPhoneValue = phoneInput.getPhoneValue();
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
                        } else if (phoneInput.getPhoneView().getVisibility() == View.GONE) {
                            if (phoneInput.getPhoneValue() != null && !phoneInput.getPhoneValue().isEmpty()) {
                                strPhoneValue = phoneInput.getPhoneValue();
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
                        if (phoneInput.getPhoneValue() != null && !phoneInput.getPhoneValue().isEmpty() && !phoneInput.getPhoneValue().trim().matches("")) {
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
                    if (email.getEmailView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {
                            if (email.getEmailValue() != null && !email.getEmailValue().isEmpty()) {
                                strEmailValue = email.getEmailValue();
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
                            if (email.getEmailValue() != null && !email.getEmailValue().isEmpty()) {
                                strEmailValue = email.getEmailValue();
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

                    } else if (email.getEmailView().getVisibility() == View.GONE) {
                        if (email.getEmailValue() != null && !email.getEmailValue().isEmpty()) {
                            strEmailValue = email.getEmailValue();
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
                        if (email.getEmailValue() != null && !email.getEmailValue().isEmpty() && !email.getEmailValue().trim().matches("")) {
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
                    if (largeInput.getLargeInputView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {
                            if (!loadControlObject.isHtmlEditorEnabled() && largeInput.getCustomEditText().getText().toString().length() > 0) {
                                strLargeInput = largeInput.getCustomEditText().getText().toString();

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
                            } else if ((loadControlObject.isHtmlEditorEnabled() || loadControlObject.isHtmlViewerEnabled()) && !largeInput.getTextEditor().getHtml().contentEquals("")) {
                                strLargeInput = largeInput.getTextEditor().getHtml().replaceAll("\u200B", "");
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
                            if (!loadControlObject.isHtmlEditorEnabled() && largeInput.getCustomEditText().getText().toString().length() > 0) {
                                strLargeInput = largeInput.getCustomEditText().getText().toString();
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
                            } else if ((loadControlObject.isHtmlEditorEnabled() || loadControlObject.isHtmlViewerEnabled()) && !largeInput.getTextEditor().getHtml().contentEquals("")) {
                                strLargeInput = largeInput.getTextEditor().getHtml().replaceAll("\u200B", "");
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
                    } else if (largeInput.getLargeInputView().getVisibility() == View.GONE) {
                        if (!loadControlObject.isHtmlEditorEnabled() && largeInput.getCustomEditText().getText() != null && !largeInput.getCustomEditText().getText().toString().isEmpty()) {
                            strLargeInput = largeInput.getCustomEditText().getText().toString();
                            if (strLargeInput.length() > 0) {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + strLargeInput.trim());
                                    }
                                }
                            }

                        } else if ((loadControlObject.isHtmlEditorEnabled() || loadControlObject.isHtmlViewerEnabled()) && !largeInput.getTextEditor().getHtml().contentEquals("")) {
                            strLargeInput = largeInput.getTextEditor().getHtml().replaceAll("\u200B", "");
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
                        if (largeInput.getCustomEditText() != null && !largeInput.getCustomEditText().getText().toString().isEmpty() && !largeInput.getCustomEditText().getText().toString().trim().matches("")) {
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
                    if (camera == null) {
                        if (isSubForm) {
                            sfString.add(loadControlObject.getControlName() + "|" + "");
                        } else {
                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                        }
                    } else {

                    }
                    if (camera.getCameraView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {
                            if (!camera.isImageViewNull()) {
                                camera.setAlertCamera().setVisibility(View.GONE);
                                Log.e(TAG, "controlSubmitValidation:" + loadControlObject.getControlName() + " | " + "NA");
//                            stringListSubmit.add(loadControlObject.getControlName() + " | " + "NA");
//                            stringListFiles.add(camera.getControlRealPath().getTag().toString());
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, camera.getControlRealPath().getTag().toString());
                                        sfFilesString.add(cam_hash);
                                        if (loadControlObject.isEnableImageWithGps()) {
                                            sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + camera.getGPSOfImage().getTag().toString());
                                            sfString.add(loadControlObject.getControlName() + "_Type" + "|" + "Single point GPS");
                                        }
                                    } else {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        if (camera.getControlRealPath().getTag() != null) {
                                            cam_hash.put(loadControlObject.getControlName(), camera.getControlRealPath().getTag().toString());
                                            stringListFiles.add(cam_hash);
                                        } else {
                                            cam_hash.put(loadControlObject.getControlName(), loadControlObject.getControlValue());
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getControlValue());
                                        }
                                        if (loadControlObject.isEnableImageWithGps()) {
                                            stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + camera.getGPSOfImage().getTag().toString());
                                            stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + "Single point GPS");
                                        }
                                    }
                                }

                                validationFlag = true;
                            } else {
                                camera.setAlertCamera().setVisibility(View.VISIBLE);
                                camera.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.rectangle_border_camera_error));
                                camera.setAlertCamera().setText(loadControlObject.getMandatoryFieldError());
                                Log.e(TAG, "controlSubmitValidation: No camera Value ");
                                validationFlag = false;
                                ImproveHelper.setFocus(camera.getCameraView());
                            }
                        } else {
                            if (camera.getBitmapSetValues() != null || loadControlObject.getControlValue() != null || camera.getControlRealPath().getTag() != null) {
                                Log.e(TAG, "controlSubmitValidation: " + loadControlObject.getControlName() + "|" + "NA");
//                            stringListSubmit.add(loadControlObject.getControlName() + " | " + "NA");
//                            stringListFiles.add(camera.getControlRealPath().getTag().toString());
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, camera.getControlRealPath().getTag().toString());
                                        sfFilesString.add(cam_hash);
                                        if (loadControlObject.isEnableImageWithGps() && camera.getGPSOfImage().getTag() != null) {
                                            sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + camera.getGPSOfImage().getTag().toString());
                                            sfString.add(loadControlObject.getControlName() + "_Type" + "|" + "Single point GPS");
                                        }
                                    } else {

                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        if (camera.getControlRealPath().getTag() != null) {
                                            cam_hash.put(loadControlObject.getControlName(), camera.getControlRealPath().getTag().toString());
                                            stringListFiles.add(cam_hash);
                                        } else {
                                            cam_hash.put(loadControlObject.getControlName(), loadControlObject.getControlValue());
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getControlValue());
                                        }

                                        if (loadControlObject.isEnableImageWithGps() && camera.getGPSOfImage().getTag() != null) {
                                            stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + camera.getGPSOfImage().getTag().toString());
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
                    } else if (camera.getCameraView().getVisibility() == View.GONE) {
                        if (!camera.isImageViewNull()) {
                            if (checkTableColumns(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    HashMap<String, String> cam_hash = new HashMap<>();
                                    cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, camera.getControlRealPath().getTag().toString());
                                    sfFilesString.add(cam_hash);
                                    if (loadControlObject.isEnableImageWithGps() && camera.getGPSOfImage().getTag() != null) {
                                        sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + camera.getGPSOfImage().getTag().toString());
                                        sfString.add(loadControlObject.getControlName() + "_Type" + "|" + "Single point GPS");
                                    }
                                } else {
                                    HashMap<String, String> cam_hash = new HashMap<>();
                                    if (camera.getControlRealPath().getTag() != null) {
                                        cam_hash.put(loadControlObject.getControlName(), camera.getControlRealPath().getTag().toString());
                                        stringListFiles.add(cam_hash);
                                    } else {
                                        cam_hash.put(loadControlObject.getControlName(), loadControlObject.getControlValue());
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getControlValue());
                                    }
                                    if (loadControlObject.isEnableImageWithGps() && camera.getGPSOfImage().getTag() != null) {
                                        stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + camera.getGPSOfImage().getTag().toString());
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
                        if (!camera.isImageViewNull()) {
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
                    if (fileBrowsing.getFileBrowsingView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {
//                        if (!fileBrowsing.getTapTextView().getText().toString().equalsIgnoreCase(context.getString(R.string.tap_here_to_select_file))
//                                && fileBrowsing.getTapTextView().getText().toString().length() > 0) {
                            if (fileBrowsing.getFileBrowsingPath() != null) {
                                strFilePath = fileBrowsing.getFileBrowsingPath();
                                Log.e(TAG, "controlSubmitValidation:" + CONTROL_TYPE_FILE_BROWSING + "-" + strFilePath);

                                fileBrowsing.setAlertFileBrowser().setVisibility(View.GONE);
//                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                                stringListFiles.add(fileBrowsing.getControlRealPath().getTag().toString());
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, fileBrowsing.getControlRealPath().getTag().toString());

                                        sfFilesString.add(cam_hash);
                                    } else {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        if (fileBrowsing.getControlRealPath().getTag() != null) {
                                            cam_hash.put(loadControlObject.getControlName(), fileBrowsing.getControlRealPath().getTag().toString());
                                            stringListFiles.add(cam_hash);
                                        } else {
                                            cam_hash.put(loadControlObject.getControlName(), loadControlObject.getDefaultValue());
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getDefaultValue());
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
                            if (fileBrowsing.getFileBrowsingPath() != null) {
                                strFilePath = fileBrowsing.getFileBrowsingPath();
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListFiles.add(fileBrowsing.getControlRealPath().getTag().toString());
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, fileBrowsing.getControlRealPath().getTag().toString());

                                        sfFilesString.add(cam_hash);
                                    } else {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        if (fileBrowsing.getControlRealPath().getTag() != null) {
                                            cam_hash.put(loadControlObject.getControlName(), fileBrowsing.getControlRealPath().getTag().toString());
                                            stringListFiles.add(cam_hash);
                                        } else {
                                            cam_hash.put(loadControlObject.getControlName(), loadControlObject.getDefaultValue());
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getDefaultValue());
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

                    } else if (fileBrowsing.getFileBrowsingView().getVisibility() == View.GONE) { // Text Input Invisible Case
                   /* if (!fileBrowsing.getTapTextView().getText().toString().equalsIgnoreCase(context.getString(R.string.tap_here_to_select_file))
                            && fileBrowsing.getTapTextView().getText().toString().length() > 0) {*/
                        if (fileBrowsing.getFileBrowsingPath() != null) {
                            if (checkTableColumns(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    HashMap<String, String> cam_hash = new HashMap<>();
                                    cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, fileBrowsing.getControlRealPath().getTag().toString());
                                    sfFilesString.add(cam_hash);
                                } else {
                                    HashMap<String, String> cam_hash = new HashMap<>();
                                    if (fileBrowsing.getControlRealPath().getTag() != null) {
                                        cam_hash.put(loadControlObject.getControlName(), fileBrowsing.getControlRealPath().getTag().toString());
                                        stringListFiles.add(cam_hash);
                                    } else {
                                        cam_hash.put(loadControlObject.getControlName(), loadControlObject.getDefaultValue());
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getDefaultValue());
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
                        if (fileBrowsing.getFileBrowsingPath() != null) {
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
                    if (calendar.getCalnderView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {
                            if (calendar.getFinalDateWithServerFormat() != null/*calendar.getCalendarValue() != null && !calendar.getCalendarValue().isEmpty()*/) {
//                            strCalendarValue = calendar.getCalendarValue();
                                strCalendarValue = calendar.getFinalDateWithServerFormat();
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
                            if (calendar.getFinalDateWithServerFormat() != null/*calendar.getCalendarValue() != null*/) {
//                            strCalendarValue = calendar.getCalendarValue();
                                strCalendarValue = calendar.getFinalDateWithServerFormat();
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

                    } else if (calendar.getCalnderView().getVisibility() == View.GONE) {
                        if (calendar.getFinalDateWithServerFormat() != null) {/*calendar.getCalendarValue() != null*/
//                        strCalendarValue = calendar.getCalendarValue();
                            strCalendarValue = calendar.getFinalDateWithServerFormat();
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
                        if (calendar.getFinalDateWithServerFormat() != null) {
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
                    if (checkBox.getCheckbox().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {
                            if (!checkBox.isCheckBoxListEmpty()) {
                                checkBox.setAlertCheckbox().setVisibility(View.GONE);
                                if (loadControlObject.getCheckbox_ValueType().equalsIgnoreCase("Boolean")) {
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        String cb_val = checkBox.getSelectedNameCheckboxString();
                                        String cb_id = checkBox.getSelectedId();
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
                                        checkBox.getSelectedNameCheckbox() != null) {
                                    Log.d(TAG, "CB_Values: " + CONTROL_TYPE_CHECKBOX + "-" + checkBox.getSelectedNameCheckboxString());
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            if (checkBox.getSelectedNameCheckboxString() != null && checkBox.getSelectedNameCheckboxString().length() > 0) {
                                                sfString.add(loadControlObject.getControlName() + "|" + checkBox.getSelectedNameCheckboxString());
//                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + ImproveHelper.getSelectedId(checkBox.getSelectedNameCheckboxString(), loadControlObject));
                                                sfString.add(loadControlObject.getControlName() + "id" + "|" + checkBox.getSelectedId());
                                            } else {
                                                sfString.add(loadControlObject.getControlName() + "|" + null);
                                            }
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + checkBox.getSelectedNameCheckboxString());
//                                        stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + ImproveHelper.getSelectedId(checkBox.getSelectedNameCheckboxString(), loadControlObject));
//                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + checkBox.getSelectedId());
                                            stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + checkBox.getSelectedId());
                                        }
                                    }
                                    validationFlag = true;
                                }

                            } else {
                                checkBox.setAlertCheckbox().setVisibility(View.VISIBLE);
                                checkBox.setAlertCheckBoxError(true);
                                checkBox.setAlertCheckbox().setText(loadControlObject.getMandatoryFieldError());
                                validationFlag = false;
                                ImproveHelper.setFocus(checkBox.getCheckbox());
                            }
                        } else {
                            if (checkBox.getSelectedNameCheckboxString() != null && !checkBox.getSelectedNameCheckboxString().isEmpty()) {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (loadControlObject.getCheckbox_ValueType().equalsIgnoreCase("Boolean")) {
                                        String cb_val = checkBox.getSelectedNameCheckboxString();
                                        String cb_id = checkBox.getSelectedId();
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
                                            if (checkBox.getSelectedNameCheckboxString() != null && checkBox.getSelectedNameCheckboxString().length() > 0) {
                                                sfString.add(loadControlObject.getControlName() + "|" + checkBox.getSelectedNameCheckboxString());
                                                sfString.add(loadControlObject.getControlName() + "id" + "|" + checkBox.getSelectedId());
                                            } else {
                                                sfString.add(loadControlObject.getControlName() + "|" + "");
                                                sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                            }

                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + checkBox.getSelectedNameCheckboxString());
//                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + ImproveHelper.getSelectedId(checkBox.getSelectedNameCheckboxString(), loadControlObject));
//                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + checkBox.getSelectedId());
                                            stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + checkBox.getSelectedId());
                                        }
                                    }

                                }
                                Log.e(TAG, "controlSubmitValidation: " + CONTROL_TYPE_CHECKBOX + "-" + checkBox.getSelectedNameCheckboxString());
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


                    } else if (checkBox.getCheckbox().getVisibility() == View.GONE) {
                        if (checkBox.getSelectedNameCheckboxString() != null && !checkBox.getSelectedNameCheckboxString().isEmpty()) {
                            if (checkTableColumns(loadControlObject.getControlName())) {
                                if (loadControlObject.getCheckbox_ValueType().equalsIgnoreCase("Boolean")) {
                                    String cb_val = checkBox.getSelectedNameCheckboxString();
                                    String cb_id = checkBox.getSelectedId();
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
                                        if (checkBox.getSelectedNameCheckboxString() != null && checkBox.getSelectedNameCheckboxString().length() > 0) {
                                            sfString.add(loadControlObject.getControlName() + "|" + checkBox.getSelectedNameCheckboxString());
//                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + ImproveHelper.getSelectedId(checkBox.getSelectedNameCheckboxString(), loadControlObject));
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + checkBox.getSelectedId());
                                        } else {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                        }
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + checkBox.getSelectedNameCheckboxString());
//                                stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + ImproveHelper.getSelectedId(checkBox.getSelectedNameCheckboxString(), loadControlObject));
                                        stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + checkBox.getSelectedId());
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
                    if (radioGroupView.getRadioGroupView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {
                            if (radioGroupView.isRadioGroupViewSelected()) {
                                radioGroupView.setAlertRadioGroup().setVisibility(View.GONE);
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        if (radioGroupView.getSelectedRadioButtonText() != null && radioGroupView.getSelectedRadioButtonText().length() > 0) {
                                            sfString.add(loadControlObject.getControlName() + "|" + radioGroupView.getSelectedRadioButtonText());
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + radioGroupView.getSelectedRadioButtonID());
                                        } else {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                        }
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName().trim() + "|" + radioGroupView.getSelectedRadioButtonText());
                                        stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + radioGroupView.getSelectedRadioButtonID());
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
                            if (radioGroupView.isRadioGroupViewSelected()) {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        if (radioGroupView.getSelectedRadioButtonText() != null && radioGroupView.getSelectedRadioButtonText().length() > 0) {
                                            sfString.add(loadControlObject.getControlName() + "|" + radioGroupView.getSelectedRadioButtonText());
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + radioGroupView.getSelectedRadioButtonID());

                                        } else {
                                            sfString.add(loadControlObject.getControlName() + "|" + "");
                                            sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                        }
//                                sfString.add(loadControlObject.getControlName() + "|" + radioGroupView.getSelectedRadioButtonText());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName().trim() + "|" + radioGroupView.getSelectedRadioButtonText());
                                        stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + radioGroupView.getSelectedRadioButtonID());
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
                    } else if (radioGroupView.getRadioGroupView().getVisibility() == View.GONE) {
                        if (radioGroupView.isRadioGroupViewSelected()) {
                            if (checkTableColumns(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    if (radioGroupView.getSelectedRadioButtonText() != null && radioGroupView.getSelectedRadioButtonText().length() > 0) {
                                        sfString.add(loadControlObject.getControlName() + "|" + radioGroupView.getSelectedRadioButtonText());
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + radioGroupView.getSelectedRadioButtonID());

                                    } else {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + "");
                                    }
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "|" + radioGroupView.getSelectedRadioButtonText());
                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + radioGroupView.getSelectedRadioButtonID());

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
                        if (radioGroupView.isRadioGroupViewSelected()) {
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
                    if (dropDown.getDropdown().getVisibility() == View.VISIBLE) {
                        /* if (!loadControlObject.isInvisible()) {*/
                        if (loadControlObject.isNullAllowed()) {
                            if (dropDown.dropdownItemIsSelected()) {
                                dropDown.setAlertDropDown().setVisibility(View.GONE);
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + dropDown.getSelectedDropDownItem());
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + dropDown.getSelectedDropDownItemID());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + dropDown.getSelectedDropDownItem());
                                        stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + dropDown.getSelectedDropDownItemID());
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
                                    Log.d(TAG, "dd_value: " + CONTROL_TYPE_DROP_DOWN + "-" + dropDown.getSelectedDropDownItem());
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
                            if (dropDown.dropdownItemIsSelected()) {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + dropDown.getSelectedDropDownItem());
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + dropDown.getSelectedDropDownItemID());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + dropDown.getSelectedDropDownItem());
                                        stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + dropDown.getSelectedDropDownItemID());
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

                    } else if (dropDown.getDropdown().getVisibility() == View.GONE) {
                        if (dropDown.dropdownItemIsSelected()) {
                            if (checkTableColumns(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + dropDown.getSelectedDropDownItem());
                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + dropDown.getSelectedDropDownItemID());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + dropDown.getSelectedDropDownItem());
                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + dropDown.getSelectedDropDownItemID());
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
                        if (dropDown.dropdownItemIsSelected()) {
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
                    if (checkList.getCheckList().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {
                            if (checkList.checkListItemIsSelected()) {
                                checkList.setAlertCheckList().setVisibility(View.GONE);
                                validationFlag = true;
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + checkList.getSelectedNameCheckListString());
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + checkList.getSelectedIDCheckListString());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + checkList.getSelectedNameCheckListString());
                                        stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + checkList.getSelectedIDCheckListString());
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
                            if (checkList.checkListItemIsSelected() && !checkList.getSelectedNameCheckListString().isEmpty()) {
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + checkList.getSelectedNameCheckListString());
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + checkList.getSelectedIDCheckListString());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + checkList.getSelectedNameCheckListString());
                                        stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + checkList.getSelectedIDCheckListString());
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
                    } else if (checkList.getCheckList().getVisibility() == View.GONE) {
                        if (checkList.checkListItemIsSelected() && !checkList.getSelectedNameCheckListString().isEmpty()) {
                            if (checkTableColumns(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + checkList.getSelectedNameCheckListString());
                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + checkList.getSelectedIDCheckListString());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + checkList.getSelectedNameCheckListString());
                                    stringListSubmit.add(loadControlObject.getControlName().trim() + "id" + "|" + checkList.getSelectedIDCheckListString());
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
                        if (checkList.checkListItemIsSelected()) {
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
                    if (rating.getRatingView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {
                            if (rating.getTotalRating() != null && rating.getTotalRating().equalsIgnoreCase("0.0")) {
                                rating.setAlertRating().setVisibility(View.VISIBLE);
                                rating.setRatingError();
                                rating.setAlertRating().setText(loadControlObject.getMandatoryFieldError());
                                validationFlag = false;
                                ImproveHelper.setFocus(rating.getRatingView());
                            } else {
//                                Log.d(TAG, "CONTROL_TYPE_RATING: " + rating.getTotalRating());
//                                Log.d(TAG, "CONTROL_TYPE_RATING: " + rating.getRatingMain());
                                rating.setAlertRating().setVisibility(View.GONE);
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + rating.getTotalRating());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + rating.getTotalRating());
                                    }
                                }
                                validationFlag = true;
                            }
                        } else {
                            if (rating.getTotalRating() != null && !rating.getTotalRating().isEmpty()) {
                                Log.d(TAG, "CONTROL_TYPE_RATING: " + rating.getTotalRating());
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + rating.getTotalRating());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + rating.getTotalRating());
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

                    } else if (rating.getRatingView().getVisibility() == View.GONE) {
                        if (rating.getTotalRating() != null && !rating.getTotalRating().isEmpty()) {
                            if (checkTableColumns(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + rating.getTotalRating());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + rating.getTotalRating());
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
                        if (rating.getTotalRating().equalsIgnoreCase("0.0")
                                || rating.getTotalRating().equalsIgnoreCase("0")
                                || rating.getTotalRating().equalsIgnoreCase("")) {

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
                    if (voiceRecording.getVoiceRecordingView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {
                            if (!voiceRecording.setIsVoiceRecorded()) {

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
                                            cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, voiceRecording.getControlRealPath().getTag().toString());
                                            sfFilesString.add(cam_hash);
                                        } else {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, loadControlObject.getControlValue());
                                            sfFilesString.add(cam_hash);
                                        }
                                    } else {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        if (voiceRecording.getControlRealPath().getTag() != null) {
                                            cam_hash.put(loadControlObject.getControlName(), voiceRecording.getControlRealPath().getTag().toString());
                                            stringListFiles.add(cam_hash);
                                        } else {
                                            cam_hash.put(loadControlObject.getControlName(), loadControlObject.getControlValue());
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getControlValue());
                                        }

                                    }
                                }

                                validationFlag = true;
                            }

                        } else {
                            if (voiceRecording.setIsVoiceRecorded()) {
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListVideoAudio.add(voiceRecording.getControlRealPath().getTag().toString());
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, voiceRecording.getControlRealPath().getTag().toString());
                                        sfFilesString.add(cam_hash);
                                    } else {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        if (voiceRecording.getControlRealPath().getTag() != null) {
                                            cam_hash.put(loadControlObject.getControlName(), voiceRecording.getControlRealPath().getTag().toString());
                                            stringListFiles.add(cam_hash);
                                        } else {
                                            cam_hash.put(loadControlObject.getControlName(), loadControlObject.getControlValue());
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getControlValue());
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

                    } else if (voiceRecording.getVoiceRecordingView().getVisibility() == View.GONE) {
                        if (voiceRecording.setIsVoiceRecorded()) {
                            if (checkTableColumns(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    HashMap<String, String> cam_hash = new HashMap<>();
                                    cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, voiceRecording.getControlRealPath().getTag().toString());
                                    sfFilesString.add(cam_hash);
                                } else {
                                    HashMap<String, String> cam_hash = new HashMap<>();
                                    if (voiceRecording.getControlRealPath().getTag() != null) {
                                        cam_hash.put(loadControlObject.getControlName(), voiceRecording.getControlRealPath().getTag().toString());
                                        stringListFiles.add(cam_hash);
                                    } else {
                                        cam_hash.put(loadControlObject.getControlName(), loadControlObject.getControlValue());
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getControlValue());
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
                        if (voiceRecording.setIsVoiceRecorded()) {
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
                    if (videoRecording.getVideoRecorderView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {
                            if (!videoRecording.isVideoRecorded()) {
                                if(videoRecording.getLl_tap_text() != null) {
                                    videoRecording.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                }
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
                                        String filePath = videoRecording.getControlRealPath().getTag().toString().replace("file:///", "/");
                                        cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, filePath);
                                        sfFilesString.add(cam_hash);
                                    } else {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        if (videoRecording.getControlRealPath().getTag() != null) {
                                            String filePath = videoRecording.getControlRealPath().getTag().toString().replace("file:///", "/");
                                            cam_hash.put(loadControlObject.getControlName(), filePath);
                                            stringListFiles.add(cam_hash);
                                        } else {
                                            cam_hash.put(loadControlObject.getControlName(), loadControlObject.getControlValue());
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getControlValue());
                                        }

                                    }
                                }

                                Log.d(TAG, "controlSubmitValidation" + " - " + strVideobase64);
                                validationFlag = true;
                            }
                        } else {
                            if (videoRecording.isVideoRecorded()) {
//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
//                            stringListVideoAudio.add(videoRecording.getControlRealPath().getTag().toString());
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        String filePath = videoRecording.getControlRealPath().getTag().toString().replace("file:///", "/");
                                        cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, filePath);
                                        sfFilesString.add(cam_hash);
                                    } else {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        if (videoRecording.getControlRealPath().getTag() != null) {
                                            String filePath = videoRecording.getControlRealPath().getTag().toString().replace("file:///", "/");
                                            cam_hash.put(loadControlObject.getControlName(), filePath);
                                            stringListFiles.add(cam_hash);
                                        } else {
                                            cam_hash.put(loadControlObject.getControlName(), loadControlObject.getControlValue());
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getControlValue());
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

                    } else if (videoRecording.getVideoRecorderView().getVisibility() == View.GONE) {
                        if (videoRecording.isVideoRecorded()) {
                            if (checkTableColumns(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    HashMap<String, String> cam_hash = new HashMap<>();
                                    String filePath = videoRecording.getControlRealPath().getTag().toString().replace("file:///", "/");
                                    cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, filePath);
                                    sfFilesString.add(cam_hash);
                                } else {
                                    HashMap<String, String> cam_hash = new HashMap<>();
                                    if (videoRecording.getControlRealPath().getTag() != null) {
                                        String filePath = videoRecording.getControlRealPath().getTag().toString().replace("file:///", "/");
                                        cam_hash.put(loadControlObject.getControlName(), filePath);
                                        stringListFiles.add(cam_hash);
                                    } else {
                                        cam_hash.put(loadControlObject.getControlName(), loadControlObject.getControlValue());
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getControlValue());
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
                        if (videoRecording.isVideoRecorded()) {
                            videoRecording.setAlertVideoRecording().setVisibility(View.GONE);
                            validationFlag = true;
                        } else {
                            String alertMessage = loadControlObject.getControlName() + " is required";
                            videoRecording.setAlertVideoRecording().setVisibility(View.VISIBLE);
                            videoRecording.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                            videoRecording.setAlertVideoRecording().setText(alertMessage);
                            validationFlag = false;
                            ImproveHelper.setFocus(videoRecording.getVideoRecorderView());
                        }
                    }
                    break;

                case CONTROL_TYPE_AUDIO_PLAYER:
                    AudioPlayer audioPlayer = (AudioPlayer) List_ControlClassObjects.get(loadControlObject.getControlName());
                    if (checkTableColumns(loadControlObject.getControlName())) {
                        if (audioPlayer != null && audioPlayer.getPath() != null) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" +  audioPlayer.getPath() .trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" +  audioPlayer.getPath() .trim());
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
                        if (videoPlayer != null && videoPlayer.getPath() != null) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" +  videoPlayer.getPath() .trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" +  videoPlayer.getPath() .trim());
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
                    if (percentage.getPercentageView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {
                            if (percentage.getCustomEditText().getText().toString() != null && !percentage.getCustomEditText().getText().toString().isEmpty()) {
                                percentage.setAlertPercentage().setVisibility(View.GONE);
                                strPercentage = percentage.getCustomEditText().getText().toString();
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
                            if (!percentage.getCustomEditText().getText().toString().equalsIgnoreCase("")) {
                                strPercentage = percentage.getCustomEditText().getText().toString();
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

                    } else if (percentage.getPercentageView().getVisibility() == View.GONE) {
                        if (!percentage.getCustomEditText().getText().toString().equalsIgnoreCase("")) {
                            strPercentage = percentage.getCustomEditText().getText().toString();
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
                        if (percentage.getCustomEditText().getText().toString() != null && !percentage.getCustomEditText().getText().toString().isEmpty()) {
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
                    Bitmap bitmapSignature = null;
                    if (signatureView.getSignature().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {
                            if (signatureView.isSignatureView() && !signatureView.isSignatureImage()) {
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
                                        cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, signatureView.getControlRealPath().getTag().toString());


                                        sfFilesString.add(cam_hash);
                                    } else {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        if (signatureView.getControlRealPath().getTag() != null) {
                                            cam_hash.put(loadControlObject.getControlName(), signatureView.getControlRealPath().getTag().toString());
                                            stringListFiles.add(cam_hash);
                                        } else {
                                            cam_hash.put(loadControlObject.getControlName(), loadControlObject.getControlValue());
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getControlValue());
                                        }

                                    }
                                }

//                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
                                validationFlag = true;
                                Log.d(TAG, "controlSubmitValidation" + " - " + bitmapSignature);
                            }
                        } else {
                            if (!signatureView.isSignatureView() || signatureView.isSignatureUploaded()) {
                                if (signatureView.isSignatureBitMap() != null || signatureView.isSignatureUploaded()) {
                                    bitmapSignature = signatureView.isSignatureBitMap();
//                                stringListFiles.add(signatureView.getControlRealPath().getTag().toString());
                                    if (checkTableColumns(loadControlObject.getControlName())) {
                                        if (isSubForm) {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, signatureView.getControlRealPath().getTag().toString());


                                            sfFilesString.add(cam_hash);
                                        } else {
                                            HashMap<String, String> cam_hash = new HashMap<>();
                                            if (signatureView.getControlRealPath().getTag() != null) {
                                                cam_hash.put(loadControlObject.getControlName(), signatureView.getControlRealPath().getTag().toString());
                                                stringListFiles.add(cam_hash);
                                            } else {
                                                cam_hash.put(loadControlObject.getControlName(), loadControlObject.getControlValue());
                                                stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getControlValue());
                                            }

                                        }
                                    }
//                                stringListSubmit.add(loadControlObject.getControlName() + "|" + "NA");
                                    Log.d(TAG, "controlSubmitValidation" + " - " + bitmapSignature);
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
                    } else if (signatureView.getSignature().getVisibility() == View.GONE) {
                        if (!signatureView.isSignatureView() || signatureView.isSignatureUploaded()) {
                            if (signatureView.isSignatureBitMap() != null || signatureView.isSignatureUploaded()) {
                                bitmapSignature = signatureView.isSignatureBitMap();
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        cam_hash.put(loadControlObject.getControlName() + "$" + subFormId + "$" + subFormRowId + "$" + controlPos, signatureView.getControlRealPath().getTag().toString());
                                        sfFilesString.add(cam_hash);
                                    } else {
                                        HashMap<String, String> cam_hash = new HashMap<>();
                                        if (signatureView.getControlRealPath().getTag() != null) {
                                            cam_hash.put(loadControlObject.getControlName(), signatureView.getControlRealPath().getTag().toString());
                                            stringListFiles.add(cam_hash);
                                        } else {
                                            cam_hash.put(loadControlObject.getControlName(), loadControlObject.getControlValue());
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + loadControlObject.getControlValue());
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
                        if (signatureView.isSignatureView() && !signatureView.isSignatureImage()) {
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
                    if (urlView != null && urlView.getURLViewStringValue() != null) {
                        if (isSubForm) {
                            sfString.add(loadControlObject.getControlName() + "|" + urlView.getURLViewStringValue().trim());
                        } else {
                            stringListSubmit.add(loadControlObject.getControlName() + "|" + urlView.getURLViewStringValue().trim());
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
                    if (decimalView.getDecimal().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {
                            if (decimalView.getCustomEditText().getText().toString().isEmpty()) {
                                decimalView.setAlertDecimalView().setVisibility(View.VISIBLE);
                                decimalView.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                decimalView.setAlertDecimalView().setText(loadControlObject.getMandatoryFieldError());
                                validationFlag = false;
                                ImproveHelper.setFocus(decimalView.getDecimal());
                            } else {
                                decimalView.setAlertDecimalView().setVisibility(View.GONE);
                                strDecimalValue = decimalView.getCustomEditText().getText().toString();
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
                            if (decimalView.getCustomEditText().getText().toString() != null && !decimalView.getCustomEditText().getText().toString().isEmpty()) {
                                strDecimalValue = decimalView.getCustomEditText().getText().toString();
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

                    } else if (decimalView.getDecimal().getVisibility() == View.GONE) {
                        if (decimalView.getCustomEditText().getText().toString() != null && !decimalView.getCustomEditText().getText().toString().isEmpty()) {
                            strDecimalValue = decimalView.getCustomEditText().getText().toString();
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
                        if (decimalView.getCustomEditText().getText().toString().isEmpty()) {
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
                    if (password.getPassword().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {
                            if (!password.getCustomEditText().getText().toString().equalsIgnoreCase("")) {
                                strPasswordValue = password.getCustomEditText().getText().toString();
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
                                            strPasswordValue = getEncryptedPassword(strPasswordValue, loadControlObject.getEncryptionTypeId());
                                        }
                                        if (isSubForm) {
                                            sfString.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                                        } else {
                                            stringListSubmit.add(loadControlObject.getControlName() + "|" + strPasswordValue.trim());
                                        }
                                    }

                                    validationFlag = true;
                                }
                            } else if (password.getCustomEditText().getText().toString().isEmpty()) {
                                password.setAlertPasswordView().setVisibility(View.VISIBLE);
                                password.gettap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                password.setAlertPasswordView().setText(loadControlObject.getMandatoryFieldError());
                                validationFlag = false;
                                ImproveHelper.setFocus(password.getPassword());
                            } else {
                                password.setAlertPasswordView().setVisibility(View.GONE);
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (loadControlObject.isEnableEncryption()) {
                                        strPasswordValue = getEncryptedPassword(strPasswordValue, loadControlObject.getEncryptionTypeId());
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
                            if (!password.getCustomEditText().getText().toString().equalsIgnoreCase("")) {
                                strPasswordValue = password.getCustomEditText().getText().toString();
                                if (checkTableColumns(loadControlObject.getControlName())) {
                                    if (loadControlObject.isEnableEncryption()) {
                                        strPasswordValue = getEncryptedPassword(strPasswordValue, loadControlObject.getEncryptionTypeId());
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
                                        strPasswordValue = getEncryptedPassword(strPasswordValue, loadControlObject.getEncryptionTypeId());
                                    }
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + "");
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
                                    }
                                }
                            }
                        }

                    } else if (password.getPassword().getVisibility() == View.GONE) {
                        if (!password.getCustomEditText().getText().toString().equalsIgnoreCase("")) {
                            strPasswordValue = password.getCustomEditText().getText().toString();
                            if (checkTableColumns(loadControlObject.getControlName())) {
                                if (loadControlObject.isEnableEncryption()) {
                                    strPasswordValue = getEncryptedPassword(strPasswordValue, loadControlObject.getEncryptionTypeId());
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
                                    strPasswordValue = getEncryptedPassword(strPasswordValue, loadControlObject.getEncryptionTypeId());
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
                        if (password.getCustomEditText().getText().toString().isEmpty()) {
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
                    if (currency.getCurrency().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {

                            if (currency.getCustomEditText().getText() != null && !currency.getCustomEditText().getText().toString().isEmpty()) {
                                strCurrencyValue = currency.getCustomEditText().getText().toString();
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
                            if (currency.getCustomEditText().getText() != null
                                    && !currency.getCustomEditText().getText().toString().isEmpty()) {
                                strCurrencyValue = currency.getCustomEditText().getText().toString();
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


                    } else if (currency.getCurrency().getVisibility() == View.GONE) {
                        if (currency.getCustomEditText().getText() != null
                                && !currency.getCustomEditText().getText().toString().isEmpty()) {
                            strCurrencyValue = currency.getCustomEditText().getText().toString();
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
                        if (currency.getCustomEditText().getText() != null
                                && !currency.getCustomEditText().getText().toString().isEmpty()) {
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
                    if (dynamicLabel != null && dynamicLabel.getTextValue() != null) {
                        if (isSubForm) {
                            sfString.add(loadControlObject.getControlName() + "|" + dynamicLabel.getTextValue().trim());
                        } else {
                            stringListSubmit.add(loadControlObject.getControlName() + "|" + dynamicLabel.getTextValue().trim());
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
                    if (image != null && image.getImageUrl() != null) {
                        if (isSubForm) {
                            sfString.add(loadControlObject.getControlName() + "|" + image.getImageUrl().trim());
                        } else {
                            stringListSubmit.add(loadControlObject.getControlName() + "|" + image.getImageUrl().trim());
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
                    if (button != null && button.getButtonText() != null) {
                        if (isSubForm) {
                            sfString.add(loadControlObject.getControlName() + "|" + button.getButtonText().trim());
                        } else {
                            stringListSubmit.add(loadControlObject.getControlName() + "|" + button.getButtonText().trim());
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
                    if (calendarEventControl != null && calendarEventControl.getEventDates() != null) {
                        if (isSubForm) {
                            sfString.add(loadControlObject.getControlName() + "|" + calendarEventControl.getEventDates().trim());
                        } else {
                            stringListSubmit.add(loadControlObject.getControlName() + "|" + calendarEventControl.getEventDates().trim());
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
                    if (mapControl != null && mapControl.getMapPoints() != null) {
                        if (isSubForm) {
                            sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + mapControl.getMapPoints().trim());
                            sfString.add(loadControlObject.getControlName() + "_Type" + "| " + mapControl.getRenderingType());
                        } else {
                            stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + mapControl.getMapPoints().trim());
                            stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "| " + mapControl.getRenderingType());
                        }
                    } else {
                        if (isSubForm) {
                            sfString.add(loadControlObject.getControlName() + "|" + "");
                        } else {
                            stringListSubmit.add(loadControlObject.getControlName() + "|" + "");
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
                    if (controlGPS.getControlGPSView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {
                            if (controlGPS.getLatLngList().size() > 0) {
                                controlGPS.setAlertGPS().setVisibility(View.GONE);
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName() + "_Coordinates")) {
                                    if (isSubForm) {
                                        String GPSSTR = "";
                                        List<LatLng> latLngList = controlGPS.getLatLngList();
                                        if (loadControlObject.getGpsType().equals("Single point GPS")) {
                                            LatLng latlang = latLngList.get(0);
                                            GPSSTR = latlang.latitude + "$" + latlang.longitude;
                                        } else {
                                            for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                                                LatLng latlang = latLngList.get(i);
                                                GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                            }
                                        }
                                        GPSSTR = GPSSTR.startsWith("^") ? GPSSTR.substring(1) : GPSSTR;
                                        sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                        sfString.add(loadControlObject.getControlName() + "_Type" + "| " + controlGPS.getGPS_Type());

                                    } else {
                                        String GPSSTR = "";
                                        List<LatLng> latLngList = controlGPS.getLatLngList();
                                        if (loadControlObject.getGpsType().equals("Single point GPS")) {
                                            LatLng latlang = latLngList.get(0);
                                            GPSSTR = latlang.latitude + "$" + latlang.longitude;
                                        } else {
                                            for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                                                LatLng latlang = latLngList.get(i);
                                                GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                            }
                                        }
                                        GPSSTR = GPSSTR.startsWith("^") ? GPSSTR.substring(1) : GPSSTR;

                                        stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                        stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + controlGPS.getGPS_Type());
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
                            if (controlGPS.getLatLngList().size() != 0) {
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName() + "_Coordinates")) {
                                    if (isSubForm) {
                                        String GPSSTR = "";
                                        List<LatLng> latLngList = controlGPS.getLatLngList();
                                        if (loadControlObject.getGpsType().equals("Single point GPS")) {
                                            LatLng latlang = latLngList.get(0);
                                            GPSSTR = latlang.latitude + "$" + latlang.longitude;
                                        } else {
                                            for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                                                LatLng latlang = latLngList.get(i);
                                                GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                            }
                                        }
                                        GPSSTR = GPSSTR.startsWith("^") ? GPSSTR.substring(1) : GPSSTR;
                                        sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                        sfString.add(loadControlObject.getControlName() + "_Type" + "|" + controlGPS.getGPS_Type());

                                    } else { //nk gps
                                        String GPSSTR = "";
                                        List<LatLng> latLngList = controlGPS.getLatLngList();
                                        if (loadControlObject.getGpsType().equals("Single point GPS")) {
                                            LatLng latlang = latLngList.get(0);
                                            GPSSTR = latlang.latitude + "$" + latlang.longitude;
                                        } else {
                                            for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                                                LatLng latlang = latLngList.get(i);
                                                GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                            }
                                        }

                                        GPSSTR = GPSSTR.startsWith("^") ? GPSSTR.substring(1) : GPSSTR;

                                        stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                        stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + controlGPS.getGPS_Type());
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
                    } else if (controlGPS.getControlGPSView().getVisibility() == View.GONE) {
                        if (controlGPS.getLatLngList().size() != 0) {
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName() + "_Coordinates")) {
                                if (isSubForm) {
                                    String GPSSTR = "";
                                    List<LatLng> latLngList = controlGPS.getLatLngList();
                                    if (loadControlObject.getGpsType().equals("Single point GPS")) {
                                        LatLng latlang = latLngList.get(0);
                                        GPSSTR = latlang.latitude + "$" + latlang.longitude;
                                    } else {
                                        for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                                            LatLng latlang = latLngList.get(i);
                                            GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                        }
                                    }
                                    GPSSTR = GPSSTR.startsWith("^") ? GPSSTR.substring(1) : GPSSTR;
                                    sfString.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                    sfString.add(loadControlObject.getControlName() + "_Type" + "|" + controlGPS.getGPS_Type());
                                } else {
                                    String GPSSTR = "";
                                    List<LatLng> latLngList = controlGPS.getLatLngList();
                                    if (loadControlObject.getGpsType().equals("Single point GPS")) {
                                        LatLng latlang = latLngList.get(0);
                                        GPSSTR = latlang.latitude + "$" + latlang.longitude;
                                    } else {
                                        for (int i = 0; i < controlGPS.getLatLngList().size(); i++) {
                                            LatLng latlang = latLngList.get(i);
                                            GPSSTR = GPSSTR + "^" + latlang.latitude + "$" + latlang.longitude;
                                        }
                                    }
                                    GPSSTR = GPSSTR.startsWith("^") ? GPSSTR.substring(1) : GPSSTR;
                                    stringListSubmit.add(loadControlObject.getControlName() + "_Coordinates" + "|" + GPSSTR);
                                    stringListSubmit.add(loadControlObject.getControlName() + "_Type" + "|" + controlGPS.getGPS_Type());
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
                        if (controlGPS.getLatLngList().size() == 0) {
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
                    if (barCode != null && barCode.getBarcodeValue() != null) {
                        if (isSubForm) {
                            sfString.add(loadControlObject.getControlName() + "|" + barCode.getBarcodeValue().trim());
                        } else {
                            stringListSubmit.add(loadControlObject.getControlName() + "|" + barCode.getBarcodeValue().trim());
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
                    if (qrCode != null && qrCode.getQrcodeValue() != null) {
                        if (isSubForm) {
                            sfString.add(loadControlObject.getControlName() + "|" + qrCode.getQrcodeValue().trim());
                        } else {
                            stringListSubmit.add(loadControlObject.getControlName() + "|" + qrCode.getQrcodeValue().trim());
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
                    if (dataControl.getDataControl().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {

                            if (!dataControl.dropdownItemIsSelected()) {
                                dataControl.setAlertDataControl().setVisibility(View.VISIBLE);
                                dataControl.getLl_tap_text().setBackground(ContextCompat.getDrawable(context, R.drawable.control_error_background));
                                dataControl.setAlertDataControl().setText(loadControlObject.getMandatoryFieldError());
                                validationFlag = false;
                                ImproveHelper.setFocus(dataControl.getDataControl());
                            } else {
                                validationFlag = true;
                                dataControl.setAlertDataControl().setVisibility(View.GONE);
                                strDataControlId = dataControl.getSelectedDropDownItemId();
                                strDataControlName = dataControl.getSelectedDropDownItemName();
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
                            if (dataControl.dropdownItemIsSelected()) {
                                strDataControlId = dataControl.getSelectedDropDownItemId();
                                strDataControlName = dataControl.getSelectedDropDownItemName();
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
                    } else if (dataControl.getDataControl().getVisibility() == View.GONE || dataControl.getDataControl().getVisibility() == View.INVISIBLE) {
                        if (dataControl.dropdownItemIsSelected()) {
                            strDataControlId = dataControl.getSelectedDropDownItemId();
                            strDataControlName = dataControl.getSelectedDropDownItemName();
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
                        if (dataControl.dropdownItemIsSelected()) {
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
                    improveHelper = new ImproveHelper(context);
                    Time time = (Time) List_ControlClassObjects.get(loadControlObject.getControlName());
                    String strTimeInputValue = null;
                    if (time.getTimeControlLayout().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                        if (loadControlObject.isNullAllowed()) {
                            if (time.getEditTextTimeValue() != null && !time.getEditTextTimeValue().isEmpty()) {
                                strTimeInputValue = time.getEditTextTimeValue();
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

                                } else if (loadControlObject.isAfterCurrentTime()) {

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

                                } else if (loadControlObject.isBetweenStartEndTime()) {

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
                                } else {
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

                            if (time.getEditTextTimeValue() != null && !time.getEditTextTimeValue().isEmpty()) {
                                strTimeInputValue = time.getEditTextTimeValue();
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

                                } else if (loadControlObject.isAfterCurrentTime()) {

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

                                } else if (loadControlObject.isBetweenStartEndTime()) {


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
                                } else {
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
/*
                        if (time.getEditTextTimeValue() != null && !time.getEditTextTimeValue().isEmpty()) {
                            strTimeInputValue = time.getEditTextTimeValue();
                            if (checkTableColumns(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + strTimeInputValue.trim());
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
                        }*/
                        }
                    } else if (time.getTimeControlLayout().getVisibility() == View.GONE) { // Text Input Invisible Case
                        if (time.getEditTextTimeValue() != null && !time.getEditTextTimeValue().isEmpty()) {
                            strTimeInputValue = time.getEditTextTimeValue();
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


                            } else if (loadControlObject.isAfterCurrentTime()) {

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

                            } else if (loadControlObject.isBetweenStartEndTime()) {

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

// end


        /*        //Sanjay Changes
                Time timeControl = (Time) List_ControlClassObjects.get(loadControlObject.getControlName());

                if (loadControlObject != null) {

                    if (loadControlObject.isMandatoryTime() && timeControl.getEt_timeDisplayOne().getText().toString().length() == 0) {
                        validationFlag = false;
                        timeControl.getTimeErrorMessageText().setVisibility(View.VISIBLE);
                        timeControl.getTimeErrorMessageText().setText(loadControlObject.getMandatoryTimeErrorMessage());
                        timeControl.getEt_timeDisplayOne().setText("00:00");
                    }

                    if (loadControlObject.isBeforeCurrentTime()) {
                        String strBeforeTime = timeControl.getEt_timeDisplayOne().getText().toString();
                        try {

                            Date date = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat(loadControlObject.getTimeFormatOptions());
                            dateFormat.format(date);
                            System.out.println(dateFormat.format(date));
                            Log.d(TAG, "ValidationBeforeDate: " + dateFormat.format(date));
                            if (dateFormat.parse(dateFormat.format(date)).before(dateFormat.parse(strBeforeTime))) {

                                validationFlag = false;
                                timeControl.getTimeErrorMessageText().setVisibility(View.VISIBLE);
                                timeControl.getTimeErrorMessageText().setText(loadControlObject.getBeforeCurrentTimeErrorMessage());
                                Log.d(TAG, "BC_TimeFromBuilderFalse: " + dateFormat.parse(dateFormat.format(date)) + " TimeFromUser: " + dateFormat.parse(strBeforeTime));

                            } else {
                                Log.d(TAG, "BC_TimeFromBuilderTrue: " + dateFormat.parse(dateFormat.format(date)) + " TimeFromUser: " + dateFormat.parse(strBeforeTime));
                                validationFlag = true;
                            }

                        } catch (Exception exception) {
                            validationFlag = false;
                            Log.d(TAG, "ValidationBeforeTimeException: " + exception.toString());
                        }
                    }
                    else if (loadControlObject.isAfterCurrentTime()) {
                        String strAfterTime = timeControl.getEt_timeDisplayOne().getText().toString();
                        try {

                            Date date = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat(loadControlObject.getTimeFormatOptions());
                            dateFormat.format(date);
                            System.out.println(dateFormat.format(date));
                            Log.d(TAG, "ValidationBeforeDate: " + dateFormat.format(date));
                            if (dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse(strAfterTime))) {
//                            System.out.println("Current time is greater than selected");

                                validationFlag = false;
                                timeControl.getTimeErrorMessageText().setVisibility(View.VISIBLE);
                                timeControl.getTimeErrorMessageText().setText(loadControlObject.getAfterCurrentTimeErrorMessage());
                                System.out.println("Current time is less than" + loadControlObject.getAfterCurrentTimeErrorMessage());
                                Log.d(TAG, "AC_TimeFromBuilderFalse: " + dateFormat.parse(dateFormat.format(date)) + " TimeFromUser: " + dateFormat.parse(strAfterTime));

                            } else {
                                validationFlag = true;
                                Log.d(TAG, "AC_TimeFromBuilderTrue: " + dateFormat.parse(dateFormat.format(date)) + " TimeFromUser: " + dateFormat.parse(strAfterTime));
                            }

                        } catch (Exception exception) {
                            validationFlag = false;
                            Log.d(TAG, "ValidationBeforeTimeException: " + exception.toString());
                        }

                    } else if (loadControlObject.isBetweenStartEndTime()) {

                        boolean isTimeLiesBetween = betweenTimeValidation(loadControlObject.getTimeFormatOptions(), loadControlObject.getBetweenStartTime(),
                                loadControlObject.getBetweenEndTime(), timeControl.getEt_timeDisplayOne().getText().toString());

                        if (isTimeLiesBetween) {
                            validationFlag = true;
                        } else {
                            validationFlag = false;
                            timeControl.getTimeErrorMessageText().setVisibility(View.VISIBLE);
                            timeControl.getTimeErrorMessageText().setText(loadControlObject.getBetweenStartEndTimeErrorMessage());

                        }
                    }
                }
*/
                    if (tableSettingsType.equalsIgnoreCase(context.getString(R.string.map_existing_table)) && mandatoryColumns.contains(loadControlObject.getControlName())) {
                        if (time.getEditTextTimeValue() != null && !time.getEditTextTimeValue().isEmpty()) {
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
                    if (userControl != null && userControl.getUserControlView().getVisibility() == View.VISIBLE) {
                        if (loadControlObject.isNullAllowed()) {
                            if (userControl.getAutoCompleteTextView().getText() != null && !userControl.getAutoCompleteTextView().getText().toString().isEmpty() && userControl.getSelectedUserName() != null) {
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + userControl.getSelectedUserName());
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + userControl.getSelectedUserId());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + userControl.getSelectedUserName());
                                        stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + userControl.getSelectedUserId());
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
                            if (userControl.getAutoCompleteTextView().getText() != null && !userControl.getAutoCompleteTextView().getText().toString().isEmpty() && userControl.getSelectedUserName() != null) {
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + userControl.getSelectedUserName());
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + userControl.getSelectedUserId());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + userControl.getSelectedUserName());
                                        stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + userControl.getSelectedUserId());
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
                    } else if (userControl.getUserControlView().getVisibility() == View.GONE) {
                        if (userControl.getAutoCompleteTextView().getText() != null && !userControl.getAutoCompleteTextView().getText().toString().isEmpty() && userControl.getSelectedUserName() != null) {
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + userControl.getSelectedUserName());
                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + userControl.getSelectedUserId());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + userControl.getSelectedUserName());
                                    stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + userControl.getSelectedUserId());
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
                        if (userControl.getAutoCompleteTextView().getText() != null && !userControl.getAutoCompleteTextView().getText().toString().isEmpty() && userControl.getSelectedUserName() != null) {
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
                    if (postControl != null && postControl.getPostControlView().getVisibility() == View.VISIBLE) {
                        if (loadControlObject.isNullAllowed()) {
                            if (postControl.getAutoCompleteTextView().getText() != null && !postControl.getAutoCompleteTextView().getText().toString().isEmpty() && postControl.getSelectedPostName() != null) {
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + postControl.getSelectedPostName());
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + postControl.getSelectedPostId());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + postControl.getSelectedPostName());
                                        stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + postControl.getSelectedPostId());
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
                            if (postControl.getAutoCompleteTextView().getText() != null && !postControl.getAutoCompleteTextView().getText().toString().isEmpty() && postControl.getSelectedPostName() != null) {
                                if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                    if (isSubForm) {
                                        sfString.add(loadControlObject.getControlName() + "|" + postControl.getSelectedPostName());
                                        sfString.add(loadControlObject.getControlName() + "id" + "|" + postControl.getSelectedPostId());
                                    } else {
                                        stringListSubmit.add(loadControlObject.getControlName() + "|" + postControl.getSelectedPostName());
                                        stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + postControl.getSelectedPostId());
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
                    } else if (postControl.getPostControlView().getVisibility() == View.GONE) {
                        if (postControl.getAutoCompleteTextView().getText() != null && !postControl.getAutoCompleteTextView().getText().toString().isEmpty() && postControl.getSelectedPostName() != null) {
                            if (list_TableColums != null && list_TableColums.contains(loadControlObject.getControlName())) {
                                if (isSubForm) {
                                    sfString.add(loadControlObject.getControlName() + "|" + postControl.getSelectedPostName());
                                    sfString.add(loadControlObject.getControlName() + "id" + "|" + postControl.getSelectedPostId());
                                } else {
                                    stringListSubmit.add(loadControlObject.getControlName() + "|" + postControl.getSelectedPostName());
                                    stringListSubmit.add(loadControlObject.getControlName() + "id" + "|" + postControl.getSelectedPostId());
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
                        if (postControl.getAutoCompleteTextView().getText() != null && !postControl.getAutoCompleteTextView().getText().toString().isEmpty() && postControl.getSelectedPostName() != null) {
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
                        //if (textInput.getTextInputView().getVisibility() == View.VISIBLE) {
                        if (autoCompletionControl.getAutoCompletionControlView().getVisibility() == View.VISIBLE) {
//                if (!loadControlObject.isInvisible()) {
                            if (loadControlObject.isNullAllowed()) {
                                if (autoCompletionControl.getAutoCompleteInputValue() != null && !autoCompletionControl.getAutoCompleteInputValue().isEmpty()) {
                                    strACValue = autoCompletionControl.getAutoCompleteInputValue();
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
                                if (autoCompletionControl.getAutoCompleteInputValue() != null && !autoCompletionControl.getAutoCompleteInputValue().isEmpty()) {
                                    strACValue = autoCompletionControl.getAutoCompleteInputValue();
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
                        } else if (autoCompletionControl.getAutoCompletionControlView().getVisibility() == View.GONE) { // AutoComplete Input Invisible Case
                            if (autoCompletionControl.getAutoCompleteInputValue() != null && !autoCompletionControl.getAutoCompleteInputValue().isEmpty()) {
                                strACValue = autoCompletionControl.getAutoCompleteInputValue();
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
                        if (autoCompletionControl.getAutoCompleteInputValue() != null && !autoCompletionControl.getAutoCompleteInputValue().isEmpty()) {
                            strACValue = autoCompletionControl.getAutoCompleteInputValue();
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
                        if (countDownTimerControl != null && countDownTimerControl.getValue() != null) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + countDownTimerControl.getValue().trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + countDownTimerControl.getValue().trim());
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
                        if (countUpTimerControl != null && countUpTimerControl.getValue() != null) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + countUpTimerControl.getValue().trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + countUpTimerControl.getValue().trim());
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
                        if (viewFileControl != null && viewFileControl.getFileLink() != null) {
                            if (isSubForm) {
                                sfString.add(loadControlObject.getControlName() + "|" + viewFileControl.getFileLink().trim());
                            } else {
                                stringListSubmit.add(loadControlObject.getControlName() + "|" + viewFileControl.getFileLink().trim());
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
                //checkes whether the current time is between 14:49:00 and 20:11:13.
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
